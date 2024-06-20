package Buyers.CreditandCollections;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

import javax.swing.table.DefaultTableModel;

import Database.pgSelect;
import Database.pgUpdate;
import tablemodel.modelRB_LegalImplication;
import tablemodel.modelRB_Monitoring;
import tablemodel.modelRB_MonitoringDetails;
import tablemodel.modelRB_RegularBilling;
import tablemodel.modelRB_RegularNotices;
import Functions.FncSystem;
import Functions.UserInfo;

public class _RegularBillingAndNotices {

	public _RegularBillingAndNotices() {}

	public static String getNewBathcNo() {
		
		pgSelect db = new pgSelect();
		
		db.select("SELECT to_char(COALESCE(MAX(batch_no::INT), 0) + 1, 'FM0000000000') FROM rf_client_notices;");
		
		if(db.isNotNull()){
			return (String) db.getResult()[0][0];
		}else{
			return null;
		}
	}
	
	public static String getNewNoticeBatchNo(){
		pgSelect db = new pgSelect();
		db.select("SELECT get_new_notice_batch_no()");
		
		if(db.isNotNull()){
			return (String) db.getResult()[0][0];
		}else{
			return null;
		}
	}

	public static Object[] getBatchDetails(String batch_no) {
		String SQL = "select trim(a.projcode) as proj_id, trim(b.proj_name) as proj_name, a.billingduedate \n" + 
				"from rf_client_notices a\n" + 
				"left join mf_project b on b.proj_id = a.projcode\n" + 
				"where batch_no = '"+ batch_no +"'\n" + 
				"group by a.projcode, b.proj_name, a.billingduedate;";

		pgSelect db = new pgSelect();
		db.select(SQL);
		if(db.isNotNull()){
			Object[] details = new Object[db.getColumnCount()];
			for(int x=0; x < db.getColumnCount(); x++){
				details[x] = db.getResult()[0][x];
			}
			return details;
		}else{
			return null;
		}
	}

	public static String getPreviewBatchNo(String notice_type_id) {
		String SQL = "SELECT a.batch_no as \"Batch No.\", get_employee_name(a.preparedby) as \"Prepared By\", a.dateprep::DATE::TIMESTAMP as \"Date Prepared\", TRIM(c.notice_desc) as \"Notice\"\n" + 
				"FROM rf_client_notices a\n" + 
				"LEFT JOIN mf_notice_type c ON c.notice_id = a.notice_id\n" + 
				"WHERE a.notice_id = '"+ notice_type_id +"'\n" + 
				"GROUP BY a.batch_no, get_employee_name(a.preparedby), a.dateprep::DATE::TIMESTAMP, TRIM(c.notice_desc)\n" + 
				"ORDER BY a.batch_no::INT DESC;";
		return SQL;
	}


	public String getSQLPerClient(String notice_id){
		String SQL = "";

		if(notice_id.equals("02") || notice_id.equals("03") || notice_id.equals("83")){// Notice of Default, // Notice of Final Demand,// Notice to Cancel
			SQL = "SELECT c_client_id as \"Client ID\",\n" + 
					"c_client_name as \"Client Name\", \n" + 
					"c_desc_unit as \"Ph-Bl-Lt\", \n" + 
					"c_pbl_id as \"PBL ID\", \n" + 
					"c_seq_no as \"Sequence\",\n" + 
					"c_buyertype as \"BuyerType\",\n" + 
					"c_proj_id as \"Project ID\",\n" + 
					"c_proj_name as \"Project Name\"\n" + 
					"FROM sp_generate_active_OR()"; 
		}
		
		if(notice_id.equals("85") || notice_id.equals("127")){// Notice to Vacate,// Notice to Surrender
			SQL = "SELECT c_client_id as \"Client ID\",\n" + 
					"c_client_name as \"Client Name\", \n" + 
					"c_desc_unit as \"Ph-Bl-Lt\", \n" + 
					"c_pbl_id as \"PBL ID\", \n" + 
					"c_seq_no as \"Sequence\",\n" + 
					"c_buyertype as \"BuyerType\",\n" + 
					"c_proj_id as \"Project ID\",\n" + 
					"c_proj_name as \"Project Name\"\n" + 
					"FROM sp_generate_cancelled_OR() a \n" +
					
					(notice_id.equals("127") ? "where exists(select * from mf_unit_info where model_id  = '009' and pbl_id = a.c_pbl_id and proj_id = a.c_proj_id)" :"")+ ""+
					""; 
		}
		return SQL;

	}


	public static void displayRegularBillingByClient(modelRB_RegularBilling model, String entity_id, String proj_id, String pbl_id, String seq_no, Date dueDate) {
		model.clear();

		String SQL = "SELECT FALSE, * FROM sp_generate_regular_billing(NULLIF('"+ entity_id +"', 'null'), '"+ proj_id +"', '"+ pbl_id +"', "+ (seq_no.equals("") ? "NULL":seq_no) +", '"+ dueDate +"'::timestamp);";

		FncSystem.out("Regular Billing", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
			}
		}
	}

	public static Boolean hasPreviousBillingDueDate(String notice_id, String proj_id, String phase, Date due_date) {
		pgSelect db = new pgSelect();
		db.select("SELECT sp_has_previous_billing_duedate('"+ notice_id +"', '"+ proj_id +"', '"+ phase +"', '"+ due_date +"');");
		return (Boolean) db.getResult()[0][0];
	}

	public static void previewRegularBilling(modelRB_RegularBilling model, String batch_no) {
		model.clear();
		pgSelect db = new pgSelect();
		
		db.select("SELECT TRUE, * FROM view_regular_billing('"+ batch_no +"');");
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
			}
		}
	}

	public static void displayLegalImplicationNotices(modelRB_LegalImplication model, String notice_id, String proj_id, String phase, Date dueDate,
			String entity_id, String pbl_id, String seq_no,Date datefortesting) {
		model.clear();

		String SQL = null;
		
		if(notice_id.equals("02")){// Notice of Default
			SQL = "SELECT FALSE, * FROM sp_dm_generate_notice_of_default('"+ notice_id +"', '"+ proj_id +"', '"+ phase +"', '"+ dueDate +"', '"+ entity_id +"', '"+ pbl_id +"', "+ (seq_no.equals("") ? "null":seq_no) +");";
		}
		if(notice_id.equals("03")){// Notice of Final Demand
			SQL = "SELECT FALSE, * FROM sp_dm_generate_notice_of_final_demand('"+ notice_id +"', '"+ proj_id +"', '"+ phase +"', '"+ dueDate +"', '"+ entity_id +"', '"+ pbl_id +"', "+ (seq_no.equals("") ? "null":seq_no) +",'"+datefortesting+"');";
		}
		if(notice_id.equals("83")){// Notice to Cancel
			SQL = "SELECT FALSE, * FROM sp_dm_generate_notice_of_final_demand('"+ notice_id +"', '"+ proj_id +"', '"+ phase +"', '"+ dueDate +"', '"+ entity_id +"', '"+ pbl_id +"', "+ (seq_no.equals("") ? "null":seq_no) +",'"+datefortesting+"');";
		}
		if(notice_id.equals("85")){// Notice to Vacate
			SQL = "SELECT FALSE, * FROM sp_dm_generate_notice_of_vacate('"+ notice_id +"', '"+ proj_id +"', '"+ phase +"', '"+ dueDate +"', '"+ entity_id +"', '"+ pbl_id +"', "+ (seq_no.equals("") ? "null":seq_no) +");"; 
		}
		if(notice_id.equals("127")){// Notice to Surrender 
			SQL = "SELECT FALSE, * FROM sp_dm_generate_notice_of_surrender('"+ notice_id +"', '"+ proj_id +"', '"+ phase +"', '"+ dueDate +"', '"+ entity_id +"', '"+ pbl_id +"', "+ (seq_no.equals("") ? "null":seq_no) +");"; 
		}
		//**ADDED BY JED 2019-02-20 DCRF NO. 1281 : ADDITIONAL TAGGING FOR HLURB**//
		if(notice_id.equals("135")){// HLURB Requirement 
			SQL = "SELECT * FROM sp_dm_generate_hlurb_req(null, null,  '"+ proj_id +"', '"+ phase +"', null);"; 
		}
		
		if(SQL != null){
			FncSystem.out("Legal Implication Notices", SQL);
			pgSelect db = new pgSelect();
			db.select(SQL);
			if(db.isNotNull()){
				for(int x=0; x < db.getRowCount(); x++){
					model.addRow(db.getResult()[x]);
				}
			}
		}
	}

	public static void viewLegalImplicationNotices(modelRB_LegalImplication model, String batch_no) {
		model.clear();
		pgSelect db = new pgSelect();
		db.select("SELECT TRUE, * FROM view_legal_implication('"+ batch_no +"');");
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
			}
		}
	}

	public static void displayRegularNotices(modelRB_RegularNotices model, String notice_id, String proj_id, String phase, Boolean retrieve, String batch_no) {
		model.clear();

		String SQL = "SELECT "+ retrieve +", * FROM sp_display_regular_notices('"+ notice_id +"', '"+ proj_id +"', '"+ phase +"', "+ retrieve +", '"+ batch_no +"');";

		if(SQL != null){
			FncSystem.out("Regular Notices", SQL);
			pgSelect db = new pgSelect();
			db.select(SQL);
			if(db.isNotNull()){
				for(int x=0; x < db.getRowCount(); x++){
					model.addRow(db.getResult()[x]);
				}
			}
		}
	}

	public static void saveRegularBilling(modelRB_RegularBilling model, String proj_id, String batch_no) {

		for(int x=0; x < model.getRowCount(); x++) {
			
			Boolean selected = (Boolean) model.getValueAt(x, 0);
			String entity_id = (String) model.getValueAt(x, 1);
			String entity_name = (String) model.getValueAt(x, 2);
			String pbl_id = (String) model.getValueAt(x, 3);
			Integer seq_no = (Integer) model.getValueAt(x, 4);
			String description = (String) model.getValueAt(x, 5);
			String part_id = (String) model.getValueAt(x, 6);
			String part_desc = (String) model.getValueAt(x, 7);
			Integer term = (Integer) model.getValueAt(x, 8);
			Timestamp default_date = (Timestamp) model.getValueAt(x, 9);
			Timestamp bill_from = (Timestamp) model.getValueAt(x, 10);
			Timestamp bill_to = (Timestamp) model.getValueAt(x, 11);
			BigDecimal amount_due = (BigDecimal) model.getValueAt(x, 12);
			BigDecimal balance = (BigDecimal) model.getValueAt(x, 13);
			Timestamp last_bill = (Timestamp) model.getValueAt(x, 14);
			Timestamp next_bill = (Timestamp) model.getValueAt(x, 15);
			Boolean past_due = (Boolean) model.getValueAt(x, 16);

			if(selected){
				String SQL = "SELECT sp_save_regular_billing('"+ entity_id +"', '"+ entity_name +"', '"+ proj_id +"', '"+ pbl_id +"', "+ seq_no +", '"+ description +"',\n" + 
						"	'"+ part_id +"', '"+ part_desc +"', "+ term +", '"+ default_date +"', '"+ bill_from +"', '"+ bill_to +"', "+ amount_due +", "+ balance +",\n" + 
						"	'"+ last_bill +"', '"+ next_bill +"', "+ past_due +", '"+ UserInfo.EmployeeCode +"', '"+ batch_no +"')";

				FncSystem.out("Save Regular Billing", SQL);
				pgSelect db = new pgSelect();
				db.select(SQL);
			}
		}
	}

	public static String[] getNoticeType(Map<String, String> mapNotices) {
		String SQL = "SELECT TRIM(notice_id), TRIM(notice_desc), TRIM(report_name) FROM mf_notice_type WHERE notice_id in ('02', '03', '83', '85','127', '135', '142') ORDER BY notice_id::int;";

		pgSelect db = new pgSelect();
		db.select(SQL);
		if(db.isNotNull()){
			String[] notices = new String[db.getRowCount()];
			for(int x=0; x < db.getRowCount(); x++){
				String notice_id = (String) db.getResult()[x][0];
				String notice_desc = (String) db.getResult()[x][1];
				String report_name = (String) db.getResult()[x][2];

				notices[x] = String.format("%s - %s", notice_id, notice_desc);
				mapNotices.put(notice_id, report_name);
			}
			return notices;
		}else{
			return null;
		}
	}

	public static String[] getRegularNoticeType(Map<String, String> mapNotices) {
		
		String SQL = "SELECT TRIM(notice_id), TRIM(notice_desc), TRIM(report_name)\n" + 
				 "FROM mf_notice_type\n" + 
			     "WHERE notice_id in ('31', '11', '88', '57', '62', '63', '69', '47', '126', '144')\n" + 
			     "AND status_id = 'A'\n" + 
			     "ORDER BY notice_desc;";

		pgSelect db = new pgSelect();
		db.select(SQL);
		if(db.isNotNull()){
			String[] notices = new String[db.getRowCount()];
			for(int x=0; x < db.getRowCount(); x++){
				String notice_id = (String) db.getResult()[x][0];
				String notice_desc = (String) db.getResult()[x][1];
				String report_name = (String) db.getResult()[x][2];

				notices[x] = String.format("%s - %s", notice_id, notice_desc);
				mapNotices.put(notice_id, report_name);
			}
			return notices;
		}else{
			return null;
		}
	}

	public static String saveLegalImplicationNotices(DefaultTableModel model, String proj_id, String notice_id, String batch_no) {
		
		String batch_no_notice = getNewNoticeBatchNo();
		
		for(int x=0; x < model.getRowCount(); x++) {
			
			Boolean selected = (Boolean) model.getValueAt(x, 0);
			String entity_id = (String) model.getValueAt(x, 1);
			String entity_name = ((String) model.getValueAt(x, 2)).replace("'", "''");
			String pbl_id = (String) model.getValueAt(x, 3);
			Integer seq_no = (Integer) model.getValueAt(x, 4);
			String description = (String) model.getValueAt(x, 5);
			String part_id = (String) model.getValueAt(x, 6);
			String part_desc = (String) model.getValueAt(x, 7);
			Timestamp due_date = (Timestamp) model.getValueAt(x, 8);
			BigDecimal amount_due = (BigDecimal) model.getValueAt(x, 9);
			Timestamp default_date = (Timestamp) model.getValueAt(x, 10);

			if(selected){
				String SQL = "SELECT sp_save_legal_implication_notices('"+ entity_id +"', '"+ entity_name +"', '"+ proj_id +"', '"+ pbl_id +"',\n" + 
						"	"+ seq_no +", '"+ description +"', '"+ part_id +"', '"+ part_desc +"', NULLIF('"+ due_date +"', 'null')::DATE, "+ amount_due +",\n" + 
						"	NULLIF('"+ default_date +"', 'null')::DATE, '"+ notice_id +"', '"+ UserInfo.EmployeeCode +"', '"+ batch_no_notice +"');";

				FncSystem.out("Save Legal Implication Notices", SQL);
				pgSelect db = new pgSelect();
				db.select(SQL);
			}
		}
		
		return batch_no_notice;
	}

	public static String saveRegularNotices(DefaultTableModel model, String proj_id, String notice_id, String batch_no) {
		
		String batch_no_notice = getNewNoticeBatchNo();
	
		for(int x=0; x < model.getRowCount(); x++) {
			Boolean selected = (Boolean) model.getValueAt(x, 0);
			String entity_id = (String) model.getValueAt(x, 1);
			String entity_name = ((String) model.getValueAt(x, 2)).replace("'", "''");
			String pbl_id = (String) model.getValueAt(x, 3);
			Integer seq_no = (Integer) model.getValueAt(x, 4);

			if(selected){
				String SQL = "SELECT sp_save_regular_notices('"+ entity_id +"', '"+ entity_name +"', '"+ proj_id +"', '"+ pbl_id +"',\n" + 
						"	"+ seq_no +", '"+ notice_id +"', '"+ UserInfo.EmployeeCode +"', '"+ batch_no_notice +"');";

				FncSystem.out("Save Legal Implication Notices", SQL);
				pgSelect db = new pgSelect();
				db.select(SQL);
			}
		}
		
		return batch_no_notice;
	}

	public static String SQL_VENUE() {
		String SQL = "SELECT TRIM(branch_id), TRIM(branch_name) FROM mf_office_branch WHERE status_id = 'A';";
		return SQL;
	}

	public static void displayMonitoring(modelRB_Monitoring model) {
		model.clear();

		String SQL = "SELECT * FROM sp_display_regular_billing_notices_monitoring();";

		if(SQL != null){
			//FncSystem.out("Regular Notices Monitoring", SQL);
			pgSelect db = new pgSelect();
			db.select(SQL);
			if(db.isNotNull()){
				for(int x=0; x < db.getRowCount(); x++){ 
					model.addRow(db.getResult()[x]);
				}
			}
		}
	}

	/**
	 * @author Christian Paquibot - 2015-07-31
	 * added display monitoring for cancelled accounts 
	 *
	 */
	public static void displayMonitoringCancelled(modelRB_Monitoring model) {
		model.clear();

		String SQL = "SELECT * FROM sp_display_regular_billing_notices_monitoring_cancelled();";

		if(SQL != null){
			//FncSystem.out("Regular Notices Monitoring", SQL);
			pgSelect db = new pgSelect();
			db.select(SQL);
			if(db.isNotNull()){
				for(int x=0; x < db.getRowCount(); x++){ 
					model.addRow(db.getResult()[x]);
				}
			}
		}
	}

	public static void displayMonitoringDetails(modelRB_MonitoringDetails model, String entity_id, String proj_id, String pbl_id, Integer seq_no) {
		model.clear();

//		String SQL = "SELECT TRIM(a.part_id), TRIM(b.part_abbv), TRIM(a.notice_id), TRIM(c.notice_desc), a.rec_id, datesent, received_date\n" + 
//				"FROM rf_client_notices a\n" + 
//				"LEFT JOIN mf_client_ledger_part b ON b.part_id = a.part_id\n" + 
//				"LEFT JOIN mf_notice_type c ON c.notice_id = a.notice_id\n" + 
//				"WHERE a.entity_id = '"+ entity_id +"' AND a.projcode = '"+ proj_id +"' AND a.pbl_id = '"+ pbl_id +"' AND a.seq_no = "+ seq_no +"\n" +
//				"AND a.status_id = 'A' \n"+
//				"ORDER BY a.dateprep;";
		
		String SQL = "SELECT TRIM(a.part_id), TRIM(b.part_abbv), TRIM(a.notice_id), TRIM(c.notice_desc), a.rec_id, datesent, received_date, a.entity_id, a.projcode, a.pbl_id, a.seq_no \n" + 
				"FROM rf_client_notices a\n" + 
				"LEFT JOIN mf_client_ledger_part b ON b.part_id = a.part_id\n" + 
				"LEFT JOIN mf_notice_type c ON c.notice_id = a.notice_id\n" + 
				"WHERE a.entity_id = '"+ entity_id +"' AND a.projcode = '"+ proj_id +"' AND a.pbl_id = '"+ pbl_id +"' AND a.seq_no = "+ seq_no +"\n" +
				"AND a.status_id = 'A' \n"+
				"ORDER BY a.dateprep;";

		FncSystem.out("Regular Notices Monitoring Details", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
			}
		}
	}
	
	public static void displayMonitoringDetailsBatch(modelRB_MonitoringDetails model, String batch_no) {
		model.clear();

		String SQL = "SELECT TRIM(a.part_id), TRIM(b.part_abbv), TRIM(a.notice_id), TRIM(c.notice_desc), a.rec_id\n" + 
				"FROM rf_client_notices a\n" + 
				"LEFT JOIN mf_client_ledger_part b ON b.part_id = a.part_id\n" + 
				"LEFT JOIN mf_notice_type c ON c.notice_id = a.notice_id\n" + 
				//"WHERE a.entity_id = '"+ entity_id +"' AND a.projcode = '"+ proj_id +"' AND a.pbl_id = '"+ pbl_id +"' AND a.seq_no = "+ seq_no +"\n" +
				"WHERE a.batch_no = '"+batch_no+"' \n" +
				"and a.status_id = 'A' \n" +
				"ORDER BY a.dateprep limit 1;";

		//FncSystem.out("Regular Notices Monitoring Details", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
			}
		}
	}

	public static Object[] getNoticeDetailsBatch(String batch_no) {
		String SQL = "SELECT TRIM(a.batch_no) as batch_no, TRIM(a.preparedby) as prepared_by_id, get_employee_name(a.preparedby) as prepared_by, a.dateprep, a.datesent, TRIM(a.mailedthru) as mailed_thru,\n" + 
				"  a.billingduedate, a.default_date, TRIM(a.receivedby) as received_by, TRIM(a.relationtobuyer) as relation_to_buyer, TRIM(a.remark_id) as rts_reason_id, TRIM(a.rtsreason) as rts_reason,\n" + 
				"  a.remarks\n" + 
				"FROM rf_client_notices a\n" + 
				
				"WHERE a.batch_no = '"+batch_no+"' and a.status_id = 'A' limit 1; ";

		pgSelect db = new pgSelect();
		db.select(SQL);
		if(db.isNotNull()){
			return db.getResult()[0];
		}else{
			return null;
		}
	}

	public static void displayMonitoringDetailsBatch(modelRB_MonitoringDetails model, String entity_id, String proj_id, String pbl_id, Integer seq_no, String batchno) {
		model.clear();

		String SQL = "SELECT TRIM(a.part_id), TRIM(b.part_abbv), TRIM(a.notice_id), TRIM(c.notice_desc), a.rec_id\n" + 
				"FROM rf_client_notices a\n" + 
				"LEFT JOIN mf_client_ledger_part b ON b.part_id = a.part_id\n" + 
				"LEFT JOIN mf_notice_type c ON c.notice_id = a.notice_id\n" + 
				"WHERE a.entity_id = '"+ entity_id +"' AND a.projcode = '"+ proj_id +"' AND a.pbl_id = '"+ pbl_id +"' AND a.seq_no = "+ seq_no +"\n" + 
				"AND a.batch_no = '"+batchno+"' \n"+
				"and a.status_id = 'A' \n" +
				"ORDER BY a.dateprep;";

		//FncSystem.out("Regular Notices Monitoring Details", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
			}
		}
	}
	
	public static Object[] getNoticeDetails(Integer rec_id, String notice_id) {
		String SQL = "SELECT TRIM(a.batch_no) as batch_no, TRIM(a.preparedby) as prepared_by_id, get_employee_name(a.preparedby) as prepared_by, a.dateprep, a.datesent, TRIM(a.mailedthru) as mailed_thru,\n" + 
				"  a.billingduedate, a.default_date, TRIM(a.receivedby) as received_by, TRIM(a.relationtobuyer) as relation_to_buyer, TRIM(a.remark_id) as rts_reason_id, TRIM(a.rtsreason) as rts_reason, a.rts_date,\n" + 
				"  a.remarks\n" + 
				"FROM rf_client_notices a\n" + 
				"WHERE rec_id = '"+ rec_id +"' and a.status_id = 'A' and notice_id = '"+notice_id+"';";

		pgSelect db = new pgSelect();
		db.select(SQL);
		if(db.isNotNull()){
			return db.getResult()[0];
		}else{
			return null;
		}
	}

//	public static void saveNoticeDetails(Integer rec_id,String entity_id, String proj_id, String pbl_id, Integer seq_no, String notice_id, String prepared_id, Date date_prepared, Date date_sent, String mailed_thru, String received_by, String relation_to_buyer,
//			Date received_date, String rts_reason_id, String rts_reason, Date rts_date, String remarks, String rts_created_by, Date rts_date_created) {
//
//		String SQL = "UPDATE rf_client_notices\n" + 
//				"   SET dateprep=nullif('"+ date_prepared +"', 'null')::timestamp, preparedby='"+ prepared_id +"', datesent=nullif('"+ date_sent +"', 'null')::timestamp, \n" + 
//				"       receivedby='"+ received_by +"', relationtobuyer='"+ relation_to_buyer +"', \n" + 
//				"       remark_id=nullif('"+ rts_reason_id +"', 'null'), rtsreason='"+ rts_reason +"', mailedthru='"+ mailed_thru +"', \n" + 
//				"       remarks='"+ remarks +"', rts_date=nullif('"+ rts_date +"', 'null')::timestamp, \n" + 
//				"       status_id='A', received_date=nullif('"+ received_date +"', 'null')::timestamp, rts_created_by='"+rts_created_by+"', rts_date_created='"+rts_date_created+"'::timestamp \n" + 
//				" WHERE rec_id = '"+ rec_id +"' and entity_id = '"+entity_id+"' and projcode = '"+proj_id+"' and pbl_id = '"+pbl_id+"' and seq_no = '"+seq_no+"' and notice_id = '"+notice_id+"'";
//		
//		pgUpdate db = new pgUpdate();
//		db.executeUpdate(SQL, true);
//		db.commit();
//	}

	public static void saveNoticeDetails(Integer rec_id, String entity_id, String proj_id, String pbl_id, Integer seq_no,  String notice_id, String prepared_id, Date date_prepared, Date date_sent, String mailed_thru, String received_by, String relation_to_buyer,
			Date received_date, String rts_reason_id, String rts_reason, Date rts_date, String remarks, String rts_created_by, Date rts_date_created) {
		String remarks2 = remarks;
		String remarksApos = remarks2.replace("'", "''");
		String SQL = "UPDATE rf_client_notices\n" + 
				"   SET dateprep=nullif('"+ date_prepared +"', 'null')::timestamp, preparedby='"+ prepared_id +"', datesent=nullif('"+ date_sent +"', 'null')::timestamp, \n" + 
				"       receivedby='"+ received_by +"', relationtobuyer='"+ relation_to_buyer +"', \n" + 
				"       remark_id=nullif('"+ rts_reason_id +"', 'null'), rtsreason='"+ rts_reason +"', mailedthru='"+ mailed_thru +"', \n" + 
				"       remarks='"+ remarksApos +"', rts_date=nullif('"+ rts_date +"', 'null')::timestamp, \n" + 
				"       status_id='A', received_date=nullif('"+ received_date +"', 'null')::timestamp, rts_created_by='"+rts_created_by+"', rts_date_created='"+rts_date_created+"'::timestamp \n" + 
				" WHERE rec_id = '"+ rec_id +"' and entity_id = '"+ entity_id +"' and projcode  = '"+proj_id+"' and pbl_id = '"+pbl_id+"' and seq_no  = '"+seq_no+"' and notice_id = '"+notice_id+"'";
		
		pgUpdate db = new pgUpdate();
		db.executeUpdate(SQL, true);
		db.commit();
	}
	
	public static void saveNoticeDetailsBatch(String notice_id, String prepared_id, Date date_prepared, Date date_sent, String mailed_thru, String received_by, String relation_to_buyer,
			Date received_date, String rts_reason_id, String rts_reason, Date rts_date, String remarks,String batch_no) {
		String remarks2 = remarks;
		String remarksApos = remarks2.replace("'", "''");
		String SQL = "UPDATE rf_client_notices\n" + 
				"   SET dateprep=nullif('"+ date_prepared +"', 'null')::timestamp, preparedby='"+ prepared_id +"', datesent=nullif('"+ date_sent +"', 'null')::timestamp, \n" + 
				"       receivedby='"+ received_by +"', relationtobuyer='"+ relation_to_buyer +"', \n" + 
				"       remark_id=nullif('"+ rts_reason_id +"', 'null'), rtsreason='"+ rts_reason +"', mailedthru='"+ mailed_thru +"', \n" + 
				"       remarks='"+ remarksApos +"', rts_date=nullif('"+ rts_date +"', 'null')::timestamp, \n" + 
				"       status_id='A', received_date=nullif('"+ received_date +"', 'null')::timestamp\n" + 
				" WHERE batch_no = '"+ batch_no +"' \n" +
				" AND notice_id = '"+notice_id+"';";

		pgUpdate db = new pgUpdate();
		db.executeUpdate(SQL, true);
		db.commit();
	}

	public static String getPreviewBatchNoMonitoring() {
		String SQL = "SELECT a.batch_no as \"Batch No.\", get_employee_name(a.preparedby) as \"Prepared By\", a.dateprep::DATE::TIMESTAMP as \"Date Prepared\", TRIM(c.notice_desc) as \"Notice\"\n" + 
				"FROM rf_client_notices a\n" + 
				"LEFT JOIN mf_notice_type c ON c.notice_id = a.notice_id\n" + 
				"WHERE a.status_id = 'A'\n" + 
				"AND a.datesent IS NULL \n"+
				"GROUP BY a.batch_no, get_employee_name(a.preparedby), a.dateprep::DATE::TIMESTAMP, TRIM(c.notice_desc)\n" + 
				"ORDER BY a.batch_no::INT DESC;";
		return SQL;
	}

	public static String getPreviewClientMonitoring() {
		String SQL = "SELECT c_client_id as \"Client ID\",\n" + 
				"c_client_name as \"Client Name\",\n" + 
				"c_description as \"Desc.\",\n" + 
				"c_status as \"Status\",\n" + 
				"c_buyer_type as \"Buyer Type\" ,\n" + 
				"c_project_id,\n" + 
				"c_pbl_id,\n" + 
				"c_seq_no FROM sp_display_regular_billing_notices_monitoring_new();";
		return SQL;
	}
	// Added by Monique Boriga; For tagging of RTS notice 10/20/2021
	public static String getRTSReason() {
		String SQL = "SELECT TRIM(rts_reason_id) as \"RTS ID\", TRIM(rts_desc) as \"RTS REASON\"\n" + 
				"FROM mf_notice_rts_reason\n" + 
				"WHERE status_id = 'A'\n";	
		return SQL;
	}

	public static void displayMonitoringBatch(modelRB_Monitoring model,String batch_no) {
		model.clear();

		String SQL = "SELECT * FROM sp_display_regular_billing_notices_monitoring_new('"+batch_no+"');";

		if(SQL != null){
			FncSystem.out("Regular Notices Monitoring", SQL);
			pgSelect db = new pgSelect();
			db.select(SQL);
			if(db.isNotNull()){
				for(int x=0; x < db.getRowCount(); x++){ 
					model.addRow(db.getResult()[x]);
				}
			}
		}
	}

	public static void displayPerClient(modelRB_Monitoring model,String enitityID , String projID, String pblID, Integer seqNo) {
		model.clear();

		String SQL = "SELECT true,  c_batch_no ,c_client_id ,\n" + 
				"    c_client_name ,\n" + 
				"    c_project_id ,\n" + 
				"    c_project_name ,\n" + 
				"    c_pbl_id ,\n" + 
				"    c_seq_no ,\n" + 
				"    c_description ,\n" + 
				"    c_status ,\n" + 
				"    c_buyer_type  FROM sp_display_regular_billing_notices_monitoring_new_active()\n" + 
				"where c_client_id = '"+enitityID+"'\n" + 
				"and c_project_id = '"+projID+"'\n" + 
				"and c_pbl_id = '"+pblID+"'\n" + 
				"and c_seq_no  = "+seqNo+"\n" + 
				";";

		
		if(SQL != null){
			FncSystem.out("Regular Notices Monitoring", SQL);
			pgSelect db = new pgSelect();
			db.select(SQL);
			if(db.isNotNull()){
				for(int x=0; x < db.getRowCount(); x++){ 
					model.addRow(db.getResult()[x]);
				}
			}
		}
	}


}
