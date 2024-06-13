/**
 * 
 */
package Buyers.ClientServicing;

import java.math.BigDecimal;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.table.DefaultTableModel;

import Database.pgSelect;
import Database.pgUpdate;
import tablemodel.modelROP_Allocation;
import tablemodel.modelRefundofPayment;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.UserInfo;

/**
 * @author John Lester Fatallo
 */
public class _RefundofPayment{

	public static String sqlClients(Boolean from_holding){//CHANGE LOOKUP HERE BASED ON THE SELECTED REFUND TYPE
		String sql = "";
		
		if(from_holding){
			sql = "SELECT b.entity_id as \"Entity ID\", get_client_name(b.entity_id) as \"Name\", \n" + 
					"get_unit_id(b.proj_id, b.pbl_id) as \"Unit ID\", b.proj_id as \"Proj. ID\", COALESCE(b.seq_no::VARCHAR, '') as \"Seq. No\", \n" + 
					"get_unit_description(b.proj_id, b.pbl_id) as \"Description\",\n" + 
					"get_current_status(b.entity_id, b.proj_id, b.pbl_id, b.seq_no::int) as \"Unit Status\", \n" + 
					"get_project_name(b.proj_id) as \"Proj. Name\", '' as \"Payment Scheme\"\n" + 
					"FROM rf_tra_detail a\n" + 
					"LEFT JOIN rf_tra_header b on b.client_seqno = a.client_seqno and b.tra_header_id = a.header_id\n" + 
					"LEFT JOIN mf_pay_particular c on c.pay_part_id = a.part_id\n" + 
					"WHERE a.status_id = 'A'\n" + 
					"and b.status_id = 'I'\n" + 
					"and a.amount > 0\n" + 
					"and not exists (select *\n" + 
					"		from rf_pay_header c\n" + 
					"		LEFT JOIN rf_pay_detail d on d.client_seqno = c.client_seqno\n" + 
					"		WHERE c.client_seqno = a.client_seqno\n" + 
					"		AND d.ar_no = b.receipt_no)\n" + 
					"and not exists (select *\n" + 
					"		from rf_sold_unit \n" + 
					"		where entity_id = b.entity_id\n" + 
					"		and projcode = b.proj_id\n" + 
					"		and pbl_id = b.pbl_id\n" + 
					"		and seq_no = b.seq_no)";
		}else{
			sql = "select trim(a.entity_id) as \"Entity ID\", trim(get_client_name(a.entity_id)) as  \"Name\", \n" + 
					"trim(b.unit_id) as \"Unit ID\", trim(a.projcode) as \"Proj. Code\",\n" + 
					"a.seq_no::VARCHAR as \"Seq. No.\", trim(b.description) as \"Description\",\n" + 
					"trim(c.status_desc) as \"Unit Status\", trim(d.proj_name) as \"Project Name\", \n" + 
					"trim(e.pmt_scheme_desc) as \"Payment Scheme\" \n" + 
					"from rf_sold_unit a\n" + 
					"left join mf_unit_info b on b.pbl_id = a.pbl_id and b.proj_id = a.projcode\n" + 
					"left join mf_buyer_status c on c.byrstatus_id = a.currentstatus\n" + 
					"left join mf_project d on d.proj_id = a.projcode\n" + 
					"left join mf_payment_scheme e on e.pmt_scheme_id = a.pmt_scheme_id\n";
		}
		
		return 	sql;
	}
	
	public static String sqlOtherPmtClients(){
		return "SELECT trim(a.entity_id) as \"Entity ID\", " +
			   "(CASE WHEN a.proj_id = '015' AND a.pbl_id = '2424' THEN 'SAMANTE, VANESSA ANNE TEODORO' ELSE TRIM(get_client_name(a.entity_id))END) as \"Name\", \n" + //MODIFIED BY MONIQUE DTD 2023-03-10; REFER TO DCRF #2500
			   "trim(COALESCE(a.or_no, a.ar_no)) as \"Receipt No\", /*TRIM(get_unit_description(a.proj_id, a.pbl_id))*/ \n"+
			   "get_merge_unit_desc_v3(a.entity_id, a.proj_id, a.pbl_id, a.seq_no) as \"Unit\",\n" + 
			   "TRIM(get_project_name(a.proj_id)) AS \"Project\", TRIM(get_unit_id(a.proj_id, a.pbl_id)) as \"Unit ID\", \n" + 
			   "TRIM(a.proj_id) as \"Proj. ID\", TRIM((a.seq_no::VARCHAR)) AS \"Seq\", \n" + 
			   "TRIM(get_pmt_scheme_desc(get_pmt_scheme_id(a.entity_id, a.pbl_id, a.seq_no, a.proj_id))) as \"Pmt Scheme\", \n" + 
			   "TRIM(get_current_status(a.entity_id, a.proj_id, a.pbl_id, a.seq_no)) as \"Unit Status\"\n" + 
			   "FROM rf_payments a\n" + 
			   "left join mf_pay_particular b on b.pay_part_id = a.pay_part_id \n" + 
			   "WHERE b.apply_ledger\n" + 
			   "AND a.pay_part_id NOT IN ('203', '168') \n"+
			   //"AND CASE WHEN "+refund_from+" = 0  "
			   "AND a.refund_date is null\n" + 
			   "AND a.status_id = 'A';\n";
	}
	
	public static String sqlOtherPmtClientsItsReal(){
		return "SELECT trim(a.entity_id) as \"Entity ID\", " +
			   "(CASE WHEN a.proj_id = '015' AND a.pbl_id = '2424' THEN 'SAMANTE, VANESSA ANNE TEODORO' ELSE TRIM(get_client_name(a.entity_id))END) as \"Name\", \n" + //MODIFIED BY MONIQUE DTD 2023-03-10; REFER TO DCRF #2500
			   "trim(COALESCE(a.or_no, a.ar_no)) as \"Receipt No\", /*TRIM(get_unit_description(a.proj_id, a.pbl_id))*/ \n"+
			   "get_merge_unit_desc_v3(a.entity_id, a.proj_id, a.pbl_id, a.seq_no) as \"Unit\",\n" + 
			   "TRIM(get_project_name(a.proj_id)) AS \"Project\", TRIM(get_unit_id(a.proj_id, a.pbl_id)) as \"Unit ID\", \n" + 
			   "TRIM(a.proj_id) as \"Proj. ID\", TRIM((a.seq_no::VARCHAR)) AS \"Seq\", \n" + 
			   "TRIM(get_pmt_scheme_desc(get_pmt_scheme_id(a.entity_id, a.pbl_id, a.seq_no, a.proj_id))) as \"Pmt Scheme\", \n" + 
			   "TRIM(get_current_status(a.entity_id, a.proj_id, a.pbl_id, a.seq_no)) as \"Unit Status\"\n" + 
			   "FROM rf_payments a\n" + 
			   "left join mf_pay_particular b on b.pay_part_id = a.pay_part_id \n" + 
			   "WHERE b.apply_ledger\n" + 
			   "AND a.pay_part_id NOT IN ('203', '168') \n"+
			   //"AND CASE WHEN "+refund_from+" = 0  "
			   "AND a.refund_date is null\n" +
			   "AND a.server_id IS NOT NULL \n"+
			   "AND TRIM(a.status_id) IN ('A', 'P');\n";
	}
	
	
	public static String sqlMiscConsbondItsreal() {
		
		return "SELECT TRIM(a.entity_id) as \"Entity ID\", \n" + 
				//"TRIM(get_client_name(a.entity_id)) as \"Name\", \n" + 
				"(CASE WHEN a.proj_id = '019' AND a.pbl_id = '10661' THEN 'ESON, ROWELYN ANTARAN' ELSE TRIM(get_client_name(a.entity_id))END) as \"Name\", \n" + //MODIFIED BY MONIQUE DTD 2023-03-10; REFER TO DCRF #2500
				"TRIM(COALESCE(NULLIF(trim(a.or_no), ''), NULLIF(trim(a.ar_no), ''))) as \"Receipt No\", " + 
				"TRIM(d.description) as \"Unit Desc\", \n" + 
				"TRIM(c.proj_name) as \"Project\", \n" + 
				"TRIM(d.unit_id) as \"Unit ID\", \n" + 
				"TRIM(a.proj_id) as \"Proj. ID\", \n" + 
				"TRIM((a.seq_no::VARCHAR)) AS \"Seq\", \n" + 
				"TRIM(get_pmt_scheme_desc(get_pmt_scheme_id(a.entity_id, a.pbl_id, a.seq_no, a.proj_id))) as \"Pmt Scheme\", \n"+ //ADDED BY MONIQUE DTD 2023-08-17
				"TRIM(get_current_status(a.entity_id, a.proj_id, a.pbl_id, a.seq_no)) as \"Unit Status\" \n" + 
				"FROM rf_payments a \n" + 
				"LEFT JOIN mf_pay_particular b on b.pay_part_id = trim(a.pay_part_id)::VARCHAR \n" + 
				"LEFT JOIN mf_project c on c.proj_id = TRIM(a.proj_id)::VARCHAR \n" + 
				"LEFT JOIN mf_unit_info d on d.proj_id = TRIM(a.proj_id)::vARCHAR and d.pbl_id = TRIM(a.pbl_id)::VARCHAR \n" + 
				"WHERE b.apply_ledger = FALSE \n" + 
				"AND trim(a.pay_part_id)::VARCHAR IN ('180', '197') \n" + 
				"--and a.server_id IS NOT NULL \n" + 
				"--AND a.actual_date::dATE <= '2022-07-01'::Date \n" + 
				"AND NOT EXISTS(Select * from issued_garbage_fee where client_seqno = a.client_seqno)\n" + 
				"and CASE WHEN a.pay_part_id = '180' THEN a.created_by = '900876' ELSE TRUE END\n" + 
				"AND a.refund_date is null\n" + 
				"--and exists (select * from acerhomes.rf_payments_refund WHERE TRIM(client_seqno)::VARCHAR = a.client_seqno)\n" + 
				"and not EXISTS (SELECT *\n" + 
				"			    FROM rf_pay_header\n" + 
				"			    where client_seqno = trim(a.client_seqno)::VARCHAR\n" + 
				"			    and entity_id = TRIM(a.entity_id)::VARCHAR\n" + 
				"			    AND proj_id = TRIM(a.proj_id)::VARCHAR\n" + 
				"			    AND pbl_id = TRIM(a.pbl_id)::VARCHAR\n" + 
				"			    AND seq_no = a.seq_no::INT) \n"+
				"and a.entity_id = '3780576312' "; //REPLACE FOR SPECIFIC CLIENT; FOR FAST RETRIEVAL; 
		 
	}
	
	public static String sqlMiscPmtClients(){
		
		return /*"SELECT trim(a.entity_id) as \"Entity ID\", TRIM(get_client_name(a.entity_id)) as \"Name\", \n" + 
				"trim(COALESCE(a.or_no, a.ar_no)) as \"Receipt No\", TRIM(get_unit_description(a.proj_id, a.pbl_id)) \n"+
				"get_merge_unit_desc_v3(a.entity_id, a.proj_id, a.pbl_id, a.seq_no) as \"Unit\",\n" + 
				"TRIM(get_project_name(a.proj_id)) AS \"Project\", TRIM(get_unit_id(a.proj_id, a.pbl_id)) as \"Unit ID\", \n" + 
				"TRIM(a.proj_id) as \"Proj. ID\", TRIM((a.seq_no::VARCHAR)) AS \"Seq\", \n" + 
				"TRIM(get_pmt_scheme_desc(get_pmt_scheme_id(a.entity_id, a.pbl_id, a.seq_no, a.proj_id))) as \"Pmt Scheme\", \n" + 
				"TRIM(get_current_status(a.entity_id, a.proj_id, a.pbl_id, a.seq_no)) as \"Unit Status\"\n" + 
				"FROM rf_payments a\n" + 
				"left join mf_pay_particular b on b.pay_part_id = a.pay_part_id \n" + 
				"WHERE b.apply_ledger = FALSE\n" + 
				"AND trim(a.pay_part_id) NOT IN ('203', '168', '269') \n"+
				"AND a.refund_date is null\n" + 
				"AND TRIM(a.status_id) = 'A';";*/
				
				"SELECT TRIM(a.entity_id) as \"Entity ID\", \n"
						//+ "TRIM(get_client_name(a.entity_id)) as \"Name\", \n"
						+ "(CASE WHEN a.proj_id = '015' AND a.pbl_id = '2424' THEN 'SAMANTE, VANESSA ANNE TEODORO' ELSE TRIM(get_client_name(a.entity_id))END) as \"Name\", \n"  //MODIFIED BY MONIQUE DTD 2023-03-10; REFER TO DCRF #2500
						+ "TRIM(COALESCE(a.or_no, a.ar_no)) as \"Receipt No\",\n" 
						+ "TRIM(d.description) as \"Unit Desc\", \n"
						+ "TRIM(c.proj_name) as \"Project\", \n"
						+ "TRIM(d.unit_id) as \"Unit ID\", \n"
						+ "TRIM(a.proj_id) as \"Proj. ID\", \n"
						+ "TRIM((a.seq_no::VARCHAR)) AS \"Seq\", \n"
						+ "TRIM(get_pmt_scheme_desc(get_pmt_scheme_id(a.entity_id, a.pbl_id, a.seq_no, a.proj_id))) as \"Pmt Scheme\", \n" //UNCOMMENT BY MONIQUE DTD 2023-08-17
						+ "TRIM(get_current_status(a.entity_id, a.proj_id, a.pbl_id, a.seq_no)) as \"Unit Status\" \n"
						+ "FROM rf_payments a \n"
						+ "LEFT JOIN mf_pay_particular b on b.pay_part_id = a.pay_part_id \n"
						+ "LEFT JOIN mf_project c on c.proj_id = a.proj_id \n" 
						+ "LEFT JOIN mf_unit_info d on d.proj_id = a.proj_id and d.pbl_id = a.pbl_id \n"
						+ "WHERE b.apply_ledger = FALSE \n"
						+ "AND a.pay_part_id in ('180', '247')\n" //--UNCOMMENT THIS FOR FAST GENERATION OF ACCOUNTS FOR REFUND OF CONSBOND
						//+ "AND a.co_id = '02' \n"
						+ "AND trim(a.pay_part_id) NOT IN ('203', '168', '269') \n"
						+ "AND NOT EXISTS(Select * from issued_garbage_fee where client_seqno = a.client_seqno) \n"
						+ "AND a.refund_date is null \n"
						+ "AND TRIM(a.status_id) = 'A'";			
	}
	
	public static String sqlClubhousePmts(){
		return "SELECT trim(a.entity_id) as \"Entity ID\", TRIM(get_client_name(a.entity_id)) as \"Name\", \n" + 
				"trim(COALESCE(a.or_no, a.ar_no)) as \"Receipt No\", /*TRIM(get_unit_description(a.proj_id, a.pbl_id))*/ \n"+
				"get_merge_unit_desc_v3(a.entity_id, a.proj_id, a.pbl_id, a.seq_no) as \"Unit\",\n" + 
				"TRIM(get_project_name(a.proj_id)) AS \"Project\", TRIM(get_unit_id(a.proj_id, a.pbl_id)) as \"Unit ID\", \n" + 
				"TRIM(a.proj_id) as \"Proj. ID\", TRIM((a.seq_no::VARCHAR)) AS \"Seq\", \n" + 
				"TRIM(get_pmt_scheme_desc(get_pmt_scheme_id(a.entity_id, a.pbl_id, a.seq_no, a.proj_id))) as \"Pmt Scheme\", \n" + 
				"TRIM(get_current_status(a.entity_id, a.proj_id, a.pbl_id, a.seq_no)) as \"Unit Status\"\n" + 
				"FROM rf_payments a\n" + 
				"left join mf_pay_particular b on b.pay_part_id = a.pay_part_id \n" + 
				"WHERE b.apply_ledger = FALSE\n" + 
				"AND a.pay_part_id IN ('208','206', '207','209','271', '269', '268', '267', '266', '265') \n"+
				"AND a.refund_date is null\n" +
				//"and a.entity_id = '4455591091' \n"+ //UNCOMMENT ME IF SLOW LOADING OF LIST OF PAYMENTS
				"AND a.status_id = 'A';";
	}
	
	public static String sqlHoldingClients(){
		return "select * from (select a.entity_id as \"Entity ID\", get_client_name(a.entity_id) as \"Name\",\n" + 
				"a.receipt_no as \"Receipt No\", get_unit_description(a.proj_id, a.pbl_id) as \"Unit\", \n" + 
				"get_project_name(a.proj_id) as \"Project\", get_unit_id(a.proj_id, a.pbl_id) as \"Unit ID\", \n" + 
				"a.proj_id as \"Proj. ID\", a.seq_no::VARCHAR as \"Seq\", '' as \"Pmt Scheme\", '' as \"Unit Status\"\n" + 
				"from rf_tra_header a\n" + 
				"where a.status_id = 'P'\n" + 
				"And a.client_seqno != ''\n" + 
				"AND exists (select *\n" + 
				"	    from rf_tra_detail \n" + 
				"	    where part_id in ('203', '168', '229')\n" + 
				"	    AND client_seqno = a.client_seqno\n" + 
				"	    AND amount > 0\n" + 
				"	    and COALESCE(refund, false) = false)\n" + 
				"and NOT exists (select * \n" + 
				"	        from rf_payments \n" + 
				"	        where receipt_id = a.receipt_no)	        \n" + 
				"union all\n" + 
				"SELECT b.entity_id as \"Entity ID\", get_client_name(b.entity_id) as \"Name\",\n" + 
				"COALESCE(b.or_no, b.ar_no) as \"Receipt No\",get_unit_description(b.proj_id, b.pbl_id) as \"Unit\", \n" + 
				"get_project_name(b.proj_id) as \"Project\", get_unit_id(b.proj_id, b.pbl_id) as \"Unit ID\", \n" + 
				"b.proj_id as \"Proj. ID\", b.seq_no::VARCHAR as \"Seq\", \n" + 
				"'' as \"Pmt Scheme\", '' as \"Unit Status\"\n" + 
				"from rf_payments b \n" + 
				"where b.pay_part_id IN ('203', '229')\n" + 
				"AND b.amount > 0\n" + 
				"AND b.receipt_id IS NULL \n"+
				/*"AND not exists (select *\n" + 
				"		from rf_payments \n" + 
				"		where receipt_id is not null)\n" + */
				"and b.status_id != 'I') x\n" + 
				"/*group by x.\"Entity ID\", x.\"Name\", x.\"Unit\", x.\"Project\", x.\"Unit ID\", \n" + 
				"x.\"Proj. ID\", x.\"Seq\", x.\"Pmt Scheme\", x.\"Unit Status\"*/\n" + 
				"order by x.\"Name\";\n";
	}
	
	public static String sqlRequest(){
		
		return "select trim(a.request_no) as \"Request No.\", \n" + 
				"trim(a.entity_id) as \"Entity ID\", trim(get_client_name(a.entity_id)) as \"Name\",\n" + 
				"trim(a.unit_id) as \"Unit ID\", a.seq_no as \"Seq. No\",\n" + 
				"trim(a.proj_id) as \"Proj. ID\", trim(d.proj_name) as \"Project Name\" ,\n" + 
				"/*trim(b.description)*/ get_merge_unit_desc_v3(a.entity_id, a.proj_id, a.pbl_id, a.seq_no) as \"Description\", trim(c.req_status_desc) as \"Status\", a.request_date as \"Request Date\", \n" + 
				"g.date_needed as \"Date Needed\" , f.status_desc as \"Unit Status\", \n" + 
				"trim(a.refund_type) as \"Refund Type\", a.amt_refund as \"Amount Refund\", \n"+
				"trim(a.request_remarks) as \"Remarks\", a.request_by as \"Request By\" \n" + 
				"from req_refund_hd a\n" + 
				"left join mf_unit_info b on b.pbl_id = a.pbl_id and b.proj_id = a.proj_id\n" + 
				"left join mf_request_status c on c.req_status_id = a.request_status\n" + 
				"left join mf_project d on d.proj_id = a.proj_id\n" + 
				"left join rf_sold_unit e on e.entity_id = a.entity_id and e.pbl_id = a.pbl_id and e.projcode = a.proj_id and e.seq_no = a.seq_no \n" + 
				"left join mf_buyer_status f on f.byrstatus_id = e.currentstatus and f.server_id = e.server_id \n" + 
				"left join rf_request_header g on g.rplf_no = a.rplf_no and g.entity_id1 = a.entity_id \n"+
				//"WHERE a.request_status = 'S'\n"+
				"ORDER BY a.request_date desc\n";
	}
	
	public static void displayHoldingRefundDetails(modelRefundofPayment model, JList rowHeader, String request_no){
		if(model != null && rowHeader != null){
			FncTables.clearTable(model);
			
			DefaultListModel listModel = new DefaultListModel();
			rowHeader.setModel(listModel);
			pgSelect db = new pgSelect();
			
			String SQL = "SELECT true,COALESCE(f.actual_date, e.actual_date), a.pay_part_id, get_pay_particular_name(a.pay_part_id), \n" + 
						 "a.amount, a.pay_rec_id, a.receipt_no, b.rplf_no, get_employee_name(h.posted_by), \n" + 
						 "h.date_posted, get_client_name(h.entity_id1) as received_by, \n" + 
						 "i.date_paid as received_date\n" + 
						 "FROM req_refund_dl a\n" + 
						 "LEFT JOIN req_refund_hd b on b.request_no = a.request_no\n" + 
						 "left join mf_project c on c.proj_id = b.proj_id\n" + 
						 "LEFT JOIN req_rt_request_header d on d.request_no = a.request_no\n" + 
						 "LEFT JOIN rf_tra_detail e on e.tra_detail_id = a.pay_rec_id\n" + 
						 "LEFT join rf_payments f on f.entity_id = b.entity_id and f.pay_rec_id = a.pay_rec_id\n" + 
						 "left join rf_request_header g on g.rplf_no = b.rplf_no and g.co_id = c.co_id\n" + 
						 "left join rf_pv_header h on h.pv_no = g.rplf_no and h.co_id = c.co_id \n" + 
						 "LEFT JOIN rf_cv_header i on i.cv_no = h.cv_no and i.co_id = c.co_id \n" + 
						 "WHERE a.request_no = '"+request_no+"';";
			db.select(SQL);
			
			FncSystem.out("Display Refund Details for Holding", SQL);
			
			if(db.isNotNull()){
				for(int x = 0; x<db.getRowCount(); x++){
					model.addRow(db.getResult()[x]);
					listModel.addElement(model.getRowCount());
				}
			}
			
		}
	}
	
	public static void displaySubdivision_Amenities(modelRefundofPayment model, JList rowHeader, String request_no){
		if(model != null && rowHeader != null){
			FncTables.clearTable(model);
			
			DefaultListModel listModel = new DefaultListModel();
			rowHeader.setModel(listModel);
			pgSelect db = new pgSelect();
			
			String SQL = "SELECT true,COALESCE(f.actual_date, e.actual_date), a.pay_part_id, get_pay_particular_name(a.pay_part_id), \n" + 
						 "a.amount, a.pay_rec_id, a.receipt_no, b.rplf_no, get_employee_name(h.posted_by), \n" + 
						 "h.date_posted, get_client_name(h.entity_id1) as received_by, \n" + 
						 "i.date_paid as received_date\n" + 
						 "FROM req_refund_dl a\n" + 
						 "LEFT JOIN req_refund_hd b on b.request_no = a.request_no\n" + 
						 "left join mf_project c on c.proj_id = b.proj_id\n" + 
						 "LEFT JOIN req_rt_request_header d on d.request_no = a.request_no\n" + 
						 "LEFT JOIN rf_tra_detail e on e.tra_detail_id = a.pay_rec_id\n" + 
						 "LEFT join rf_payments f on f.entity_id = b.entity_id and f.pay_rec_id = a.pay_rec_id\n" + 
						 "left join rf_request_header g on g.rplf_no = NULLIF(b.rplf_no, '') and g.co_id = c.co_id\n" + 
						 "left join rf_pv_header h on h.pv_no = g.rplf_no and h.co_id = c.co_id\n" + 
						 "LEFT JOIN rf_cv_header i on i.cv_no = h.cv_no and i.co_id = c.co_id\n" + 
						 "WHERE a.request_no = '"+request_no+"';";
			db.select(SQL);
			
			FncSystem.out("Display Refund Details for Holding", SQL);
			
			if(db.isNotNull()){
				for(int x = 0; x<db.getRowCount(); x++){
					model.addRow(db.getResult()[x]);
					listModel.addElement(model.getRowCount());
				}
			}
			
		}
	}
	
	public static void displayOtherPmtRefundDetails(modelRefundofPayment model, JList rowHeader, String request_no){
		if(model != null && rowHeader != null){
			FncTables.clearTable(model);
			
			DefaultListModel listModel = new DefaultListModel();
			rowHeader.setModel(listModel);
			pgSelect db = new pgSelect();
			
			String SQL = "";
			db.select(SQL);
			
			FncSystem.out("Display Refund Details for Other Pmt", SQL);
			
			if(db.isNotNull()){
				for(int x = 0; x<db.getRowCount(); x++){
					model.addRow(db.getResult()[x]);
					listModel.addElement(model.getRowCount());
				}
			}
		}
	}
	
	public static void displayMiscPmtDetails(modelRefundofPayment model, JList rowHeader, String request_no){
		if(model != null && rowHeader != null){
			FncTables.clearTable(model);
			
			DefaultListModel listModel = new DefaultListModel();
			rowHeader.setModel(listModel);
			
			pgSelect db = new pgSelect();
			
			String SQL = "";
			db.select(SQL);
			
			if(db.isNotNull()){
				for(int x= 0; x<db.getRowCount(); x++){
					model.addRow(db.getResult()[x]);
					listModel.addElement(model.getRowCount());
				}
			}
			
		}
	}
	
	public static void displayLedgerPayment(modelRefundofPayment model,JList rowHeader, String entity_id, String proj_id, String unit_id, String seq_no, String request_no, Boolean search){ //ADD PROJECT ID AND SEQ NO
		System.out.println("Before If Statement");
		if(model != null && rowHeader != null){
			
			FncTables.clearTable(model);

			DefaultListModel listModel = new DefaultListModel();
			rowHeader.setModel(listModel);
			System.out.println("After Clear of model and listModel");
			/*String sql ="SELECT a.actual_date::DATE, a.pay_part_id, b.partdesc, \n"+
					"a.amount - get_amount_credited(a.pay_rec_id) , \n"+
					"a.pay_rec_id, coalesce(a.or_no, a.ar_no)\n" + 
					"FROM rf_payments a\n" + 
					"LEFT JOIN mf_pay_particular b on b.pay_part_id = a.pay_part_id \n" + 
					"where a.entity_id = '"+entity_id+"'\n" + 
					"and a.unit_id = '"+unit_id+"'\n" + 
					"and a.proj_id = '"+proj_id+"'\n" + 
					"and a.seq_no = "+seq_no+ "\n" + 
					"and b.apply_ledger = true\n" + 
					"and a.status_id = 'A'\n" + 
					"order by pay_rec_id \n";*/
			
			String SQL = "SELECT CASE WHEN "+search+" THEN TRUE ELSE FALSE END,\n"+
						 "a.actual_date::DATE, a.pay_part_id, b.partdesc, \n" + 
						 "a.amount - get_amount_credited(a.pay_rec_id) , \n" + 
						 "a.pay_rec_id, coalesce(a.or_no, a.ar_no), c.rplf_no, \n" + 
						 "get_employee_name(f.posted_by), f.date_posted, \n" + 
						 "get_client_name(f.entity_id1), f.date_paid\n" + 
						 "FROM rf_payments a\n" + 
						 "LEFT JOIN mf_pay_particular b on b.pay_part_id = a.pay_part_id\n" + 
						 "LEFT JOIN req_refund_hd c on c.request_no = a.request_no \n" + 
						 "left join rf_request_header d on d.rplf_no = c.rplf_no and d.co_id = a.co_id\n" + 
						 "left join rf_pv_header e on e.pv_no = d.rplf_no AND e.co_id = a.co_id\n" + 
						 "LEFT JOIN rf_cv_header f on f.cv_no = e.cv_no and f.co_id = a.co_id \n \n"+
						 "LEFT JOIN rf_payments g ON (TRIM(a.receipt_id) = TRIM(g.or_no) OR TRIM(a.receipt_id) = TRIM(g.ar_no)) and g.status_id = 'A' \n" + 
						 "	  and (a.entity_id = g.entity_id and (case when a.pymnt_type = 'B' then a.check_no = g.check_no end))" + 
						 "where a.entity_id = '"+entity_id+"'\n" + 
						 "and a.pbl_id = getinteger('"+unit_id+"')::VARCHAR \n" + 
						 "and a.proj_id = '"+proj_id+"'\n" + 
						 "and a.seq_no = "+seq_no+"\n" + 
						 "and b.apply_ledger = true \n"+
						 "AND (CASE WHEN a.pymnt_type = 'B' then (CASE WHEN g.pay_rec_id is not null then g.check_stat_id = '01' else a.check_stat_id = '01' end) ELSE TRUE END)\n" + //ADDED BY LESTER 2017-10-03 for check payments good only
						"and TRIM(a.status_id) IN ('A', 'P')\n" +
						"and a.pay_rec_id not in (75946, 75956, 78849, 78860, 74422, 74423) \n"+ 
						"and case when false then exists (select *\n" + 
						"				from req_refund_dl\n" + 
						"				where request_no = '"+request_no+"'\n" + 
						"				and amount = a.amount - get_amount_credited(a.pay_rec_id)\n" + 
						"				and pay_rec_id = a.pay_rec_id\n" + 
						"				and pay_part_id = a.pay_part_id \n" + 
						"				and receipt_no = coalesce(a.or_no, a.ar_no))\n" + 
						"				else true end \n"+
						"order by (case when a.pay_part_id = '260' then a.trans_date else a.actual_date::DATE end) ,b.apply_order, COALESCE(get_ledger_apply_date(a.pay_rec_id), a.check_date),a.amount desc, a.pay_rec_id";
						
//						"ORDER BY (case when a.pymnt_type = 'A' THEN a.actual_date \n" + 
//						"			    else ((case when a.check_date::date <= a.actual_date then a.actual_date \n" + 
//						"					else (select date_created from rf_check_history where new_checkstat_id = '02' and status_id = 'A' and pay_rec_id::int = a.pay_rec_id::int order by date_created desc limit 1) END)) end)::Date, a.client_seqno, a.pymnt_type ,a.pay_rec_id";
			
			FncSystem.out("Display Ledger Payment", SQL);	
			pgSelect db = new pgSelect();
			db.select(SQL);
			
			if(db.isNotNull()){
				for(int x=0; x < db.getRowCount(); x++){
					model.addRow(db.getResult()[x]);
					listModel.addElement(model.getRowCount());
				}
			}
		}
	}

	public static void displayOtherPayment(modelRefundofPayment model, JList rowHeader, String entity_id,  String proj_id, String unit_id, String seq_no, String request_no ,Boolean search){
		if(model != null && rowHeader != null){
			FncTables.clearTable(model);

			DefaultListModel listModel = new DefaultListModel(); 
			rowHeader.setModel(listModel);

			String sql = "select CASE WHEN "+search+" then true else false end, \n"+
					"* from (select a.actual_date::DATE, a.pay_part_id, b.partdesc,\n" + 
					"a.amount-get_amount_credited(a.pay_rec_id) as applied_amt, a.pay_rec_id,\n" + 
					"coalesce(nullif(trim(or_no), ''), '')||coalesce(nullif(trim(ar_no), ''),'')\n" + 
					"from rf_payments a\n" + 
					"left join mf_pay_particular b on b.pay_part_id = a.pay_part_id \n" + 
					"where a.entity_id = '"+entity_id+"' \n" + 
					"and a.pbl_id = getinteger('"+unit_id+"')::VARCHAR \n" + 
					"and a.proj_id = '"+proj_id+"' \n" + 
					"and a.seq_no = "+seq_no+" \n" + 
					"and b.apply_ledger = false \n"+
					//"and b.refundable \n"+
					"and TRIM(a.status_id) = 'A' \n"+
					//"and a.pay_part_id not in ('197', '268')\n"+ 
					"AND case when "+search+" then EXISTS (SELECT *\n" + 
					"	    FROM req_refund_dl\n" + 
					"	    where request_no = '"+request_no+"'\n" + 
					"	    AND amount = a.amount-get_amount_credited(a.pay_rec_id)\n" + 
					"	    and pay_rec_id = a.pay_rec_id\n" + 
					"	    AND pay_part_id = a.pay_part_id\n" + 
					"	    AND receipt_no = TRIM(coalesce(a.or_no, '')||coalesce(a.ar_no,'')))\n" + 
					"	    else true end \n"+
					//"and a.reversed != true \n" + 
					"order by a.actual_date) c \n" + 
					"where c.applied_amt != 0;";
			
			FncSystem.out("Display Other Payment", sql);
			
			pgSelect db = new pgSelect();
			db.select(sql);
			
			if(db.isNotNull()){
				for(int x = 0; x < db.getRowCount(); x++){
					model.addRow(db.getResult()[x]);
					listModel.addElement(model.getRowCount());
				}
			}
		}
	}
	
	/*public static void displayHoldingPayments(modelRefundofPayment model, JList rowHeader, String entity_id){
		FncTables.clearTable(model);
		
		DefaultListModel listModel = new DefaultListModel();
		rowHeader.setModel(listModel);
		
		pgSelect db = new pgSelect();
		
		String sql = "SELECT a.actual_date, a.part_id, c.partdesc, \n" + 
					 "a.amount, a.tra_detail_id::integer, a.receipt_no \n" + 
					 "FROM rf_tra_detail a\n" + 
					 "LEFT JOIN rf_tra_header b on b.client_seqno = a.client_seqno and b.tra_header_id = a.header_id\n" + 
					 "LEFT JOIN mf_pay_particular c on c.pay_part_id = a.part_id\n" + 
					 "WHERE a.status_id = 'A'\n" + 
					 "and b.status_id = 'I'\n" + 
					 "AND entity_id = '"+entity_id+"'\n" + 
					 "ORDER BY a.tra_detail_id;";
		db.select(sql);
		
		FncSystem.out("Display sql for Holding Payments", sql);
		
		if(db.isNotNull()){
			for(int x = 0; x<db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
				listModel.addElement(model.getRowCount());
			}
		}
	}*/
	
	public static void displayHoldingPayments(modelRefundofPayment model, JList rowHeader, String entity_id, String request_no, Boolean search){
		model.clear();
		
		DefaultListModel listModel = new DefaultListModel();
		rowHeader.setModel(listModel);
		
		pgSelect db = new pgSelect();
		String SQL = "SELECT CASE WHEN "+search+" THEN TRUE ELSE FALSE END ,\n"+
					 "* FROM (select a.actual_date as actual_date, a.part_id as part_id, \n" + 
					 "get_pay_particular_name(a.part_id) as particular, \n" + 
					 "a.amount as amount, a.tra_detail_id::integer as rec_id, a.receipt_no as receipt_no \n" + 
					 "FROM rf_tra_detail a\n" + 
					 "LEFT JOIN rf_tra_header b on b.client_seqno = a.client_seqno\n" + 
					 "WHERE a.status_id = 'P'\n" + 
					 "AND b.entity_id = '"+entity_id+"'\n" + 
					 "and a.part_id in ('203', '168', '229')\n" + 
					 "and not exists (SELECT * \n" + 
					 "		FROM rf_payments \n" + 
						 "		where receipt_id = a.receipt_no) \n" + 
					"AND COALESCE(a.refund, false) = false\n" + 
					"union all\n" + 
					"select d.actual_date as actual_date, d.pay_part_id as part_id, \n" + 
					"get_pay_particular_name(d.pay_part_id) as particular,\n" + 
					"d.amount as amount, d.pay_rec_id as rec_id, coalesce(d.or_no, d.ar_no) as receipt_no\n" + 
					"from rf_payments d\n" + 
					"where d.pay_part_id IN ('203', '229') \n" + 
					"AND d.amount > 0\n" + 
					"AND d.refund_date is null\n" + 
					"and d.entity_id = '"+entity_id+"'\n" + 
					"and d.status_id != 'I') e\n" +
					"where case when "+search+" THEN EXISTS (SELECT *\n" + 
					"										 FROM req_refund_dl\n" + 
					"	    								 where request_no = '"+request_no+"'\n" + 
					"	    								 AND amount = e.amount\n" + 
					"	    								 and pay_rec_id = e.rec_id\n" + 
					"	    								 AND pay_part_id = e.part_id\n" + 
					"	    								 AND receipt_no = e.receipt_no) \n"+
					"	  ELSE TRUE END \n"+
					"order by e.actual_date";
		
		db.select(SQL);
		
		FncSystem.out("Display SQL for Holding Payments", SQL);
		
		if(db.isNotNull()){
			for(int x = 0 ; x<db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
				listModel.addElement(model.getRowCount());
			}
		}
	}
	
	public static void displaySubdivisionPmts(modelRefundofPayment model, JList rowHeader, String entity_id,  String proj_id, String unit_id, String seq_no, String request_no ,Boolean search){
		System.out.println("Before If Statement");
		if(model != null && rowHeader != null){
			FncTables.clearTable(model);

			DefaultListModel listModel = new DefaultListModel(); 
			rowHeader.setModel(listModel);
			
			System.out.println("Before SQL Query");

			String sql = "select CASE WHEN "+search+" then true else false end, \n"+
					"* from (select a.actual_date::DATE, a.pay_part_id, b.partdesc,\n" + 
					"a.amount-get_amount_credited(a.pay_rec_id) as applied_amt, a.pay_rec_id,\n" + 
					"coalesce(or_no, '')||coalesce(ar_no,'')\n" + 
					"from rf_payments a\n" + 
					"left join mf_pay_particular b on b.pay_part_id = a.pay_part_id \n" + 
					"where a.entity_id = '"+entity_id+"' \n" + 
					"and (CASE WHEN '"+unit_id+"' = '' THEN TRUE ELSE a.unit_id = '"+unit_id+"' END)\n" + 
					"and (CASE WHEN '"+proj_id+"' = '' THEN TRUE ELSE a.proj_id = '"+proj_id+"' END) \n" + 
					"and (CASE WHEN '"+seq_no+"' = '' THEN TRUE ELSE a.seq_no = NULLIF('"+seq_no+"', '')::int END)\n" + 
					"and b.apply_ledger = false \n"+
					"and b.refundable \n"+
					"and a.status_id = 'A' \n"+
					"and a.pay_part_id in ('208','206', '207','209','271', '269', '268', '267', '266', '265')\n"+ //WATER AND GARBAGE SHOULD NOT BE REFUNDED
					"AND case when "+search+" then EXISTS (SELECT *\n" + 
					"	    FROM req_refund_dl\n" + 
					"	    where request_no = '"+request_no+"'\n" + 
					"	    AND amount = a.amount-get_amount_credited(a.pay_rec_id)\n" + 
					"	    and pay_rec_id = a.pay_rec_id\n" + 
					"	    AND pay_part_id = a.pay_part_id\n" + 
					"	    AND receipt_no = TRIM(coalesce(a.or_no, '')||coalesce(a.ar_no,'')))\n" + 
					"	    else true end \n"+
					//"and a.reversed != true \n" + 
					"order by a.actual_date) c \n" + 
					"where c.applied_amt != 0;";
			
			FncSystem.out("Display Other Payment", sql);
			
			pgSelect db = new pgSelect();
			db.select(sql);
			
			if(db.isNotNull()){
				for(int x = 0; x < db.getRowCount(); x++){
					model.addRow(db.getResult()[x]);
					listModel.addElement(model.getRowCount());
				}
			}
		}
	}
	
	public static void displayRefundAllocation(modelROP_Allocation model, String proj_id){
		FncTables.clearTable(model);
		
		pgSelect db = new pgSelect();
		
		String sql = "SELECT false, pay_part_id, \n" + 
					 "particulars, partdesc, 0.00, apply_ledger, '', '', null, '', null\n" + 
					 "from mf_pay_particular \n" + 
					 "where pay_part_id in ('180', '198', '193', '182', '197', '185', '223', '224', '268')\n" +
					 "AND CASE WHEN '"+proj_id+"' = '015' THEN pay_part_id NOT IN ('268') ELSE TRUE END\n"+
					 "ORDER BY partdesc;";
		db.select(sql);
		
		FncSystem.out("Display Refund Allocation", sql);
		
		if(db.isNotNull()){
			for(int x= 0; x<db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
			}
		}
	}
	
	public static void displayAllocatedRefund(String request_no, modelROP_Allocation model){
		FncTables.clearTable(model);
		
		pgSelect db = new pgSelect();
		
		/*String sql = "select TRUE, a.apply_to_part_id, b.particulars, \n" + 
					 "b.partdesc, a.amount_credited, a.applied_to_ledger\n" + 
					 "from rf_credit_payments a\n" + 
					 "LEFT JOIN mf_pay_particular b on b.pay_part_id = a.apply_to_part_id \n"+
					 //"LEFT JOIN rf_request_header c on c.request_no = a.request_no \n" + 
					 "WHERE a.request_no = '"+request_no+"'";*/
		
		String sql = "SELECT * FROM view_rop_allocation('"+request_no+"')";
		
		db.select(sql);
		
		FncSystem.out("Display Credited Particulars", sql);
		
		if(db.isNotNull()){
			for(int x = 0; x<db.getRowCount(); x++){
				Boolean isSelected = (Boolean) db.getResult()[x][0];
				if(isSelected){
					model.addRow(db.getResult()[x]);
				}
			}
		}
	}
	
	public static String sqlUnit(String unit_id){
		
		return "select trim(a.entity_id) as \"Entity ID\", trim(get_client_name(a.entity_id)) as \"Entity Name\",\n" + 
			   "trim(get_unit_id(a.projcode, a.pbl_id)) as \"Unit ID\", trim(get_unit_description(a.projcode, a.pbl_id)) as \"Unit Desc.\", \n" + 
			   "trim(a.projcode) as \"Proj. ID\", trim(get_project_name(a.projcode)) as \"Proj. Name\", a.seq_no as \"Seq\",\n" + 
			   "trim(a.pmt_scheme_id) as \"Pmt. ID\", trim(b.pmt_scheme_desc) as \"Pmt. Desc\"\n" + 
			   "from rf_sold_unit a\n" + 
			   "left join mf_payment_scheme b on b.pmt_scheme_id = a.pmt_scheme_id\n" + 
			   "where a.pbl_id != getinteger('"+unit_id+"')::VARCHAR;";
		
	}
	
	public static void saveROP_Header(String request_no, String entity_id, String proj_id, String unit_id, String seq_no, String entity_name, BigDecimal refund_amt, BigDecimal deduction ,String refund_type, String remarks, String request_by){
		
		pgSelect db = new pgSelect();
		
		String sql = "SELECT sp_save_rop_header('"+request_no+"', '"+entity_id+"', '"+proj_id+"', '"+unit_id+"', '"+seq_no+"', '"+entity_name+"', "+refund_amt+", "+deduction+" ,'"+refund_type+"', '"+request_by+"' ,'"+remarks+"' ,'"+UserInfo.EmployeeCode+"', '"+UserInfo.Branch+"')";
		db.select(sql);
		
		FncSystem.out("Display Saving of ROP Header", sql);
	}
	
	public static void saveROP_Detail(DefaultTableModel model, String request_no){
		
		ArrayList<String> listPayPartID = new ArrayList<String>();
		ArrayList<BigDecimal> listAmount = new ArrayList<BigDecimal>();
		ArrayList<Integer> listPayRecId = new ArrayList<Integer>();
		ArrayList<String> listReceiptNo = new ArrayList<String>();
		
		for(int x= 0; x<model.getRowCount(); x++){
			Boolean isSelected = (Boolean) model.getValueAt(x, 0);
			String pay_part_id = (String) model.getValueAt(x, 2);
			BigDecimal amount = (BigDecimal) model.getValueAt(x, 4);
			System.out.printf("Display pay part_id: %s%n", pay_part_id);
			System.out.printf("Display amount: %s%n", amount);
			System.out.printf("Display pay rec id: %s%n", model.getValueAt(x, 5));
			Integer pay_rec_id = (Integer) model.getValueAt(x, 5);
			String receipt_no = (String) model.getValueAt(x, 6);
			
			if(isSelected){
				listPayPartID.add(String.format("'%s'", pay_part_id));
				listAmount.add(amount);
				listPayRecId.add(pay_rec_id);
				listReceiptNo.add(String.format("'%s'", receipt_no));
			}
		}
		
		String pay_part_id = listPayPartID.toString().replaceAll("\\[|\\]", "");
		String amount = listAmount.toString().replaceAll("\\[|\\]", "");
		String pay_rec_id = listPayRecId.toString().replaceAll("\\[|\\]", "");
		String receipt_no = listReceiptNo.toString().replaceAll("\\[|\\]", "");
		
		pgSelect db = new pgSelect();
		
		String sql = "SELECT sp_save_rop_detail('"+request_no+"', ARRAY["+pay_part_id+"]::VARCHAR[], ARRAY["+amount+"]::NUMERIC[], ARRAY["+pay_rec_id+"]::INTEGER[] ,ARRAY["+receipt_no+"]::VARCHAR[])";
		db.select(sql);
		
		FncSystem.out("Display saving of Refund detail", sql);
	}

	public static void saveRtReqHeader(String req_no, String req_by, String credit_entity, String credit_proj, String credit_unit, String credit_seq, String remarks){
		pgUpdate db = new pgUpdate();

		String sql = "INSERT INTO req_rt_request_header(rec_id, request_no, request_date, request_status, request_by, request_remarks ,new_entity_id, new_proj_id, new_unit_id, new_seq_no ,add_by, add_date, req_type_id, isdocorrefund)\n" + 
					 "VALUES ((select coalesce(max(rec_id), 0) + 1 from req_rt_request_header), '"+req_no+"', now(), 'S', '"+req_by+"', '"+remarks+"' ,NULLIF('"+credit_entity+"', 'null'), NULLIF('"+credit_proj+"', 'null'), \n"+
					 "NULLIF('"+credit_unit+"', 'null'), NULLIF('"+credit_seq+"', '')::INT, '"+UserInfo.EmployeeCode+"', now(), 'Refund of Payment', TRUE)";
		
		db.executeUpdate(sql, true);
		db.commit();
	}
	
	public static Boolean isRequestExisting(String request_no){
		pgSelect db = new pgSelect();
		
		String SQL = "SELECT * FROM req_rt_request_header WHERE request_no = '"+request_no+"'";
		db.select(SQL);
		
		if(db.isNotNull()){
			return true;
		}else{
			return false;
		}
	}
	
	public static void saveCreditAmt(String request_no, modelROP_Allocation model){
		pgSelect db = new pgSelect();
		
		ArrayList<String> listPayPartID = new ArrayList<String>();
		ArrayList<BigDecimal> listCrediAmt = new ArrayList<BigDecimal>();
		ArrayList<Boolean> listApplyLedger = new ArrayList<Boolean>();
		
		for(int x = 0; x<model.getRowCount(); x++){
			Boolean isSelected = (Boolean) model.getValueAt(x, 0);
			
			if(isSelected){
				String pay_part_id = (String) model.getValueAt(x, 1);
				BigDecimal credit_amt = (BigDecimal) model.getValueAt(x, 4);
				Boolean apply_ledger = (Boolean) model.getValueAt(x, 5);
				
				listPayPartID.add(String.format("'%s'", pay_part_id));
				listCrediAmt.add(credit_amt);
				listApplyLedger.add(apply_ledger);
			}
		}
		
		String pay_part_id = listPayPartID.toString().replaceAll("\\[|\\]", "");
		String credit_amount = listCrediAmt.toString().replaceAll("\\[|\\]", "");
		String apply_ledger = listApplyLedger.toString().replaceAll("\\[|\\]", "");
		
		String sql = "SELECT sp_request_credit_pmt('"+request_no+"', ARRAY["+pay_part_id+"]::VARCHAR[], ARRAY["+credit_amount+"]::NUMERIC[], ARRAY["+apply_ledger+"]::BOOLEAN[], '"+UserInfo.EmployeeCode+"')";
		db.select(sql);
		
		FncSystem.out("Display Saving Of Credit Pmt", sql);
	}
	
	public static void postCreditPayments(String req_no){
		
		pgSelect db = new pgSelect();
		
		String sql = "select sp_credit_from_refund('"+req_no+"', '"+UserInfo.EmployeeCode+"', '"+UserInfo.Branch+"')";
		db.select(sql);
		
		FncSystem.out("Display Credit From Refund", sql);
	}
	
	public static void postRefundOfPaymnent(String request_no){
		pgSelect db = new pgSelect();
		
		String sql = "SELECT sp_post_refund_of_payment('"+request_no+"', '"+UserInfo.EmployeeCode+"', '"+UserInfo.Branch+"');";
		db.select(sql);
		
		FncSystem.out("Display Posting of Refund of Payment", sql);
	}

	public static void cancelReqRefundHD(String req_no){//Cancel
		pgUpdate db = new pgUpdate();

		String sql = "UPDATE req_refund_hd SET request_status= 'I' WHERE request_no = '"+req_no+"'";

		db.executeUpdate(sql, true);
		db.commit();
	}

	public static void cancelReqHeader(String req_no){
		pgUpdate db = new pgUpdate();

		String sql = "UPDATE req_rt_request_header SET request_status= 'I' WHERE request_no = '"+req_no+"'";

		db.executeUpdate(sql, true);
		db.commit();
	}

	public static void cancelReqRefundDL(String req_no){
		pgUpdate db = new pgUpdate();

		String sql = "UPDATE req_refund_dl SET status_id= 'I' WHERE request_no = '"+req_no+"'";

		db.executeUpdate(sql, true);
		db.commit();
	}
	
	public static void postReqRefundHD(String req_no){
		pgUpdate db = new pgUpdate();

		String sql = "UPDATE req_refund_hd SET request_status= 'P', approved_date = now() WHERE request_no = '"+req_no+"'";

		db.executeUpdate(sql, true);
		db.commit();
	}

	public static void postReqHeader(String req_no){
		pgUpdate db = new pgUpdate();

		String sql = "UPDATE req_rt_request_header SET request_status = 'P', approved_by = '"+UserInfo.EmployeeCode+"' WHERE request_no = '"+req_no+"'";

		db.executeUpdate(sql, true);
		db.commit();
	}
	
	public static void postCreditLedger(String entity_id ,String proj_id ,String unit_id , String seq_no ,String req_no){
		pgSelect db = new pgSelect();
		
		String sql = "select sp_post_refund_ledger('"+entity_id+"', '"+unit_id+"', '"+proj_id+"', "+seq_no+", '"+req_no+"', '"+UserInfo.EmployeeCode+"')";
		db.select(sql);
		
		FncSystem.out("Display sql for Refund Credit Ledger", sql);
	}

	//REMOVE IF TURNED INTO function
	public static void postLedgerCrdtTemp(String entity_id, String unit_id, String proj_id, String seq_no ,BigDecimal refund_amount, String req_no,String rplf_no,String proj_name){
		pgUpdate db = new pgUpdate();
		pgSelect dbledger = new pgSelect(); 

		String ledger = "select paid_date,sched_date, part_id,partdesc,amount,pay_rec_id, client_ledger_id from (select max(a.date_paid::date) as paid_date,a.sched_date::date, \n" + 
				"max(a.part_id)::character(3) as part_id,max(b.part_desc) as partdesc, sum(a.amount) as amount ,a.pay_rec_id, max(a.client_ledger_id) as client_ledger_id \n" + 
				"from rf_client_ledger a left join mf_client_ledger_part b on (a.part_id  = b.part_id)  \n" + 
				"where a.entity_id  = '"+entity_id+"' and a.unit_id  = '"+unit_id+"' and a.proj_id = '"+proj_id+"' and a.seq_no = "+seq_no+"\n" + 
				"AND a.status_id = 'A'\n" + 
				"group by a.part_id, a.pay_rec_id,a.sched_date\n" + 
				"order by a.sched_date, max(a.client_ledger_id))  c\n" + 
				"where amount !=0\n" + 
				"order by sched_date, client_ledger_id";

		FncSystem.out("Display Ledger Amt", ledger);
		dbledger.select(ledger);
		if(dbledger.isNotNull()){

			//CHECK IF DELETE HERE
			String sqltruncatecrdttemp = "DELETE FROM req_rt_payments_credit_temp \n";
			db.executeUpdate(sqltruncatecrdttemp, true);

			for (int x= 0; x< dbledger.getRowCount(); x++){
				BigDecimal ledger_amount = (BigDecimal) dbledger.getResult()[x][4];
				Integer pay_rec_id = (Integer) dbledger.getResult()[x][5];
				String pay_part_id = (String) dbledger.getResult()[x][2];
				
				System.out.printf("Display x: %s%n", x);
				System.out.printf("Display pay rec id: %s%n", pay_rec_id);
				System.out.printf("Display pay part_id: %s%n", pay_part_id);

				String sqlclientledger = "INSERT INTO rf_client_ledger(\n" +
						"client_ledger_id, entity_id, unit_id, proj_id, pbl_id, seq_no, \n" + 
						"date_paid, appl_date, sched_date, pmt_amount, part_id, pay_rec_id, \n" + 
						"amount, balance, rate, date_posted, remarks, request_no, inactive_date, \n" + 
						"oi_vat, status_id, created_by, date_created, edited_by, date_edited)\n" + 
						"VALUES ((select max(client_ledger_id) + 1 FROM rf_client_ledger), \n"+
						"'"+entity_id+"' ,'"+unit_id+"','"+proj_id+"', getinteger('"+unit_id+"')::VARCHAR, "+seq_no+", "+
						"now(), now(), \n" + 
						"(select sched_date from rf_client_ledger where client_ledger_id = \n" + 
						"(select max(client_ledger_id) FROM rf_client_ledger where entity_id = '"+entity_id+"' and unit_id = '"+unit_id+"' and proj_id = '"+proj_id+"' and seq_no = "+seq_no+")), "+
						"(select pmt_amount from rf_client_ledger where client_ledger_id = \n" + 
						"(select max(client_ledger_id) FROM rf_client_ledger where entity_id = '"+entity_id+"' and unit_id = '"+unit_id+"' and proj_id = '"+proj_id+"' and seq_no = "+seq_no+")), "+
						"(select part_id from rf_client_ledger where client_ledger_id = \n" + 
						"(select max(client_ledger_id) FROM rf_client_ledger where entity_id = '"+entity_id+"' and unit_id = '"+unit_id+"' and proj_id = '"+proj_id+"' and seq_no = "+seq_no+")), "+
						"(select pay_rec_id from rf_client_ledger where client_ledger_id = \n" + 
						"(select max(client_ledger_id) FROM rf_client_ledger where entity_id = '"+entity_id+"' and unit_id = '"+unit_id+"' and proj_id = '"+proj_id+"' and seq_no = "+seq_no+")), "+
						"(case when "+refund_amount+" > "+ledger_amount+" then "+ledger_amount+" else "+refund_amount+" end) * -1, "+
						"(select balance from rf_client_ledger where client_ledger_id = \n" + 
						"(select max(client_ledger_id) FROM rf_client_ledger where entity_id = '"+entity_id+"' and unit_id = '"+unit_id+"' and proj_id = '"+proj_id+"' and seq_no = "+seq_no+")), null, \n" + 
						"(select date_posted from rf_client_ledger where client_ledger_id = \n" + 
						"(select max(client_ledger_id) FROM rf_client_ledger where entity_id = '"+entity_id+"' and unit_id = '"+unit_id+"' and proj_id = '"+proj_id+"' and seq_no = "+seq_no+")), "+
						"'reverse ledger', '', null, null , 'A', \n" + 
						"'"+UserInfo.EmployeeCode+"', now(), '', null);\n";

				//FncSystem.out("Display sql for insert in reverse ledger", sqlclientledger);
				db.executeUpdate(sqlclientledger, true);

				//SETS THE PAYMENS TO REVERSED
				String sqlrfPayments = "update rf_payments set reversed = true where pay_rec_id = "+pay_rec_id+" \n";
				db.executeUpdate(sqlrfPayments, true);

				String sqlreqrtpaymentscrdttemp = "INSERT INTO req_rt_payments_credit_temp(\n" + 
						"rec_id, entity_id, projcode, pbl_id, seq_no, actualdate, transdate, \n" + 
						"pay_part_id, particulars, pymnttype, amount, statusdate, \n" + 
						"orno, requestno, appliedamt, ordate, remarks,client_seqno, status_id, reversed, \n" + 
						"creditofpayment, receipt_id, co_id, unit_id)\n" + //ADD pay_rec_id 
						"VALUES ("+pay_rec_id+", '"+entity_id+"' ,'"+proj_id+"', "+
						"getinteger('"+unit_id+"')::VARCHAR, "+seq_no+", "+
						"now(), now(), '"+pay_part_id+"', \n" + 
						"(select particulars from mf_pay_particular where pay_part_id = '"+pay_part_id+"'), 'A', "+
						"(case when "+refund_amount+" > "+ledger_amount+" then "+ledger_amount+" else "+refund_amount+" end), \n" + 
						"now(), (select or_no from rf_payments where pay_rec_id = "+pay_rec_id+"), '"+req_no+"', "+
						"(case when "+refund_amount+" > "+ledger_amount+" then "+ledger_amount+" else "+refund_amount+" end), "+
						"(select or_date from rf_payments where pay_rec_id = "+pay_rec_id+"), \n" + 
						"'Credit of Payment', (select client_seqno from rf_payments where pay_rec_id = "+pay_rec_id+"), "+
						"'A', false, true, (select receipt_id from rf_payments where pay_rec_id = "+pay_rec_id+") , "+
						"(select co_id from rf_payments where pay_rec_id = "+pay_rec_id+"), '"+unit_id+"') \n";
				db.executeUpdate(sqlreqrtpaymentscrdttemp, true);
			}

			String sqlRfReqHeader = "INSERT INTO rf_request_header(co_id, busunit_id, rplf_no, rplf_date, date_needed, date_liq_ext, " + 
					"rplf_type_id, entity_id1, entity_type_id, doc_id, proc_id, justification, remarks, status_id, " + 
					"created_by, date_created)\n" + 
					"VALUES ('02','02','"+rplf_no+"', now(), now() + interval '7 days' , null, " + 
					"'01', '"+entity_id+"' , (select entity_type_id from rf_entity_assigned_type where entity_id = '"+entity_id+"'), '09', '1', " + 
					"'', 'Refund of Payment', 'A', '"+UserInfo.EmployeeCode+"', now()) \n";

			//CHECK IF CLIENT LANG YUNG ENTITY TYPE ID NA KELANGAN DITO

			db.executeUpdate(sqlRfReqHeader, true);

			String sqlRfReqDetail = "INSERT INTO rf_request_detail(co_id, busunit_id, rplf_no, line_no, ref_doc_id, ref_doc_no, " + 
					"ref_doc_date, with_budget, part_desc, acct_id, remarks, amount, " + 
					"entity_id, entity_type_id, project_id, sub_projectid, div_id, " + 
					"dept_id, sect_id, inter_busunit_id, inter_co_id, is_vatproject, " + 
					"is_vatentity, is_taxpaidbyco, is_gross, wtax_id, wtax_rate, wtax_amt, " + 
					"vat_acct_id, vat_rate, vat_amt, exp_amt, pv_amt, sd_no, item_id, " + 
					"asset_no, old_acct_id, status_id, created_by, date_created)\n" + 
					"VALUES ('02', '02', '"+rplf_no+"', " + 
					"'1', null, null, null, false, 'Refund of Payment','03-02-16-000' ,'', "+refund_amount+", '"+entity_id+"', " + 
					"(select entity_type_id from rf_entity_assigned_type where entity_id = '"+entity_id+"'), '"+proj_id+"', " + 
					"(select sub_proj_id from mf_sub_project where proj_id = '"+proj_id+"' and phase = (select phase from mf_unit_info where pbl_id = getinteger('"+unit_id+"')::VARCHAR) AND status_id ='A'), \n"+//ADDED STATUS ID BY LESTER DCRF 2718
					"'"+UserInfo.Department+"', '"+UserInfo.Division+"', null, null,null, false, false, false, false, null, null, null, null, null, " + 
					"null, "+refund_amount+", "+refund_amount+", null, null, null,null, 'A', '"+UserInfo.EmployeeCode+"', now())";
			db.executeUpdate(sqlRfReqDetail, true);

			//dbledger.select("SELECT reload_req_rt_payments_credit_temp();");
			db.commit();
		}
	}
}