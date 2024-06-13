/**
 * 
 */
package Buyers.ClientServicing;

import java.math.BigDecimal;

import javax.swing.DefaultListModel;
import javax.swing.JList;

import Database.pgSelect;
import Database.pgUpdate;
import tablemodel.modelCreditofPayment_CrdtPymntOther;
import tablemodel.modelCreditofPayment_CrdtToLedger;
import tablemodel.modelROP_OtherPmt;
import tablemodel.modelROP_Miscellaneous;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.UserInfo;

/**
 * @author John Lester Fatallo
 */
public class _CreditOfPayment {

	public static String sqlCreditFrom(){ //SQL FOR LOOKUP OF CLIENTS IN THE CREDIT FROM ACCOUNT
		
		return "select trim(a.entity_id) as \"Entity ID\", trim(get_client_name(a.entity_id)) as \"Name\", \n" + 
				"trim(b.unit_id) as \"Unit ID\", trim(a.projcode) as \"Proj. Code\", \n" + 
				"a.seq_no as \"Seq. No.\", trim(b.description) as \"Description\", \n" + 
				"trim(c.status_desc) as \"Unit Status\", trim(d.proj_name) as \"Project Name\"/*, trim(e.pmt_scheme_desc) as \"Payment Scheme\"*/\n" + 
				"from rf_sold_unit a\n" + 
				"left join mf_unit_info b on b.pbl_id = a.pbl_id and b.proj_id = a.projcode\n" + 
				"left join mf_buyer_status c on c.byrstatus_id = a.currentstatus and coalesce(c.server_id, '') = coalesce(a.server_id, '') \n" + 
				"left join mf_project d on d.proj_id = a.projcode\n" + 
				//"left join mf_payment_scheme e on e.pmt_scheme_id = a.pmt_scheme_id\n" + 
				//"left join rf_cancellation f on f.entity_id = a.entity_id and f.proj_id = a.projcode and f.pbl_id = a.pbl_id and f.seq_no = a.seq_no\n" + 
				"where /*a.currentstatus = '02'*/ \n"+
				//"where a.pbl_id::INT in (select pbl_id from canceled_accounts)\n" + 
				//"AND a.seq_no in (SELECT seq_no from canceled_accounts) \n" +
				"/*AND(*/ (select sum(amount) from rf_client_ledger where entity_id = a.entity_id and pbl_id = a.pbl_id and proj_id = a.projcode and seq_no = a.seq_no) != 0 \n";
				//"AND _payments_made(a.entity_id, a.projcode,a.pbl_id,a.seq_no) >= 24  AND f.csv_id is not null"; //XXX UNCOMMENT ME AFTER DONE TESTING
	}
	
	public static String sqlCreditTo(String unit_id){ //SQL FOR THE LOOKUP OF THE CLIENTS IN THE CREDIT TO UNIT PANEL
		
		return "select trim(a.entity_id) as \"Entity ID\", trim(get_client_name(a.entity_id)) as \"Name\", \n" + 
				"trim(b.unit_id) as \"Unit ID\", trim(a.projcode) as \"Proj. Code\", \n" + 
				"a.seq_no as \"Seq. No.\", trim(b.description) as \"Description\", \n" + 
				"trim(c.status_desc) as \"Unit Status\", trim(d.proj_name) as \"Project Name\", \n"+
				"trim(a.pmt_scheme_id) as \"Pmt. ID\",trim(e.pmt_scheme_desc) as \"Payment Scheme\"\n" + 
				"from rf_sold_unit a\n" + 
				"left join mf_unit_info b on b.pbl_id = a.pbl_id and b.proj_id = a.projcode\n" + 
				"left join mf_buyer_status c on c.byrstatus_id = a.currentstatus\n" + 
				"left join mf_project d on d.proj_id = a.projcode\n" + 
				"left join mf_payment_scheme e on e.pmt_scheme_id = a.pmt_scheme_id\n" +
				"where a.status_id = 'A' \n"+
				"AND a.currentstatus != '02'\n";
				//"and a.entity_id = '"+entity_id+"'\n" + 
				//"and a.pbl_id != getinteger('"+unit_id+"')::VARCHAR";
	}

	public static void displayLedgerPayment(modelROP_OtherPmt model,JList rowHeader, String entity_id, String unit_id, String proj_id, String seq_no){//Displays Data for the legder payments add seq_no and proj_id
		//if(model != null && rowHeader != null){
			//FncTables.clearTable(model);
			model.clear();

			DefaultListModel listModel = new DefaultListModel();
			rowHeader.setModel(listModel);

			/*String sql =  "select paid_date,sched_date,partdesc,amount,pay_rec_id from (select max(a.date_paid::date) as paid_date,a.sched_date::date,\n" + 
					"max(b.part_desc) as partdesc, sum(a.amount) as amount ,pay_rec_id from rf_client_ledger a left join mf_client_ledger_part b on (a.part_id  = b.part_id)  \n" + 
					"where a.entity_id  = '"+entity_id+"' and a.unit_id  = '"+unit_id+"'\n" + 
					"AND a.status_id = 'A'\n" + 
					"group by a.part_id, a.pay_rec_id,a.sched_date\n" + 
					"order by a.sched_date, max(a.client_ledger_id))  c\n" + 
					"where amount !=0\n" + 
					"order by sched_date";*/
			
			String sql ="SELECT a.actual_date::DATE, a.pay_part_id, b.partdesc, a.amount, a.pay_rec_id, coalesce(a.or_no, a.ar_no)\n" + 
						"FROM rf_payments a\n" + 
						"LEFT JOIN mf_pay_particular b on b.pay_part_id = a.pay_part_id \n" + 
						"where a.entity_id = '"+entity_id+"'\n" + 
						"and a.unit_id = '"+unit_id+"'\n" + 
						"and a.proj_id = '"+proj_id+"'\n" + 
						"and a.seq_no = "+seq_no+ "\n" + 
						"and a.refund_date is null \n"+
						"and b.apply_ledger = true\n" + 
						"and a.status_id = 'A'\n" + 
						"order by actual_date \n";

			//FncSystem.out("Display Ledger Payment", sql);	
			pgSelect db = new pgSelect();
			db.select(sql);
			if(db.isNotNull()){
				for(int x=0; x < db.getRowCount(); x++){
					model.addRow(db.getResult()[x]);
					listModel.addElement(model.getRowCount());
				}
			}
		//}
	}
	
	public static void displayLedgerPaymentSpecialCase(modelROP_OtherPmt model,JList rowHeader, String entity_id, String unit_id, String proj_id, String seq_no){//Displays Data for the legder payments add seq_no and proj_id
		//if(model != null && rowHeader != null){
			//FncTables.clearTable(model);
			model.clear();

			DefaultListModel listModel = new DefaultListModel();
			rowHeader.setModel(listModel);
			
			String sql ="SELECT a.actual_date::DATE, a.pay_part_id, b.partdesc, a.amount, a.pay_rec_id, coalesce(a.or_no, a.ar_no)\n" + 
						"FROM tmp_rf_payments_gaw_albert a\n" + 
						"LEFT JOIN mf_pay_particular b on b.pay_part_id = a.pay_part_id \n" + 
						"order by actual_date \n";

			FncSystem.out("Display Ledger Payment", sql);	
			pgSelect db = new pgSelect();
			db.select(sql);
			if(db.isNotNull()){
				for(int x=0; x < db.getRowCount(); x++){
					model.addRow(db.getResult()[x]);
					listModel.addElement(model.getRowCount());
				}
			}
		//}
	}

	public static void displayOtherPayment(modelROP_Miscellaneous model, JList rowHeader, String entity_id, String proj_id ,String unit_id, String seq_no){ //add seq_no and proj_id
		if(model != null && rowHeader != null){
			FncTables.clearTable(model);

			DefaultListModel listModel = new DefaultListModel();
			rowHeader.setModel(listModel);

			String sql = "select * from (select a.actual_date::date ,a.pay_part_id ,b.partdesc, \n" + 
					"a.amount - get_amount_credited(a.pay_rec_id) as applied_amt, \n" + 
					"a.pay_rec_id ,coalesce(or_no,'')||coalesce(ar_no,'') \n" + 
					"from rf_payments a \n" + 
					"left join mf_pay_particular b on (a.pay_part_id = b.pay_part_id) \n" + 
					"where a.entity_id  = '"+entity_id+"' \n" +
					"and a.proj_id = '"+proj_id+"' \n"+
					"and a.pbl_id  = getinteger('"+unit_id+"')::VARCHAR \n"+
					"and a.seq_no = "+seq_no+" \n" + 
					"and b.apply_ledger  = false \n"+
					//"and a.reversed != true \n" +  //check if need to add b before apply_ledger
					"order by a.actual_date) c \n"+
					"where c.applied_amt != 0";
			
			//XXX UNCOMMENT ME IF DATA IS RIGHT
			/*String sql = "select a.actual_date::date, a.pay_part_id ,b.partdesc, a.amount, a.pay_rec_id, coalesce(a.or_no, a.ar_no)\n" + 
					  "from rf_payments a\n" + 
					  "left join mf_pay_particular b on b.pay_part_id = a.pay_part_id\n" + 
					  "where a.entity_id  = '"+entity_id+"' \n" + 
					  "and a.unit_id  = '"+unit_id+"' \n" + 
					  "and a.proj_id = '"+proj_id+"' \n" + 
					  "and a.seq_no = "+seq_no+"\n" + 
					  "and b.apply_ledger  = false \n" + 
					  "order by actual_date;";*/

			//FncSystem.out("Display Other Payment", sql);
			
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

	public static void displayLedgerCrdtTo(modelCreditofPayment_CrdtToLedger model,JList rowHeader, String entity_id, String unit_id, String proj_id, String seq_no){//Displays Data for the legder payments
		if(model != null && rowHeader != null){// TRY ADDING seq_no
			FncTables.clearTable(model);

			DefaultListModel listModel = new DefaultListModel();
			rowHeader.setModel(listModel);

			/*String sql =  "select paid_date,sched_date,partdesc,amount,pay_rec_id from (select max(a.date_paid::date) as paid_date,a.sched_date::date,\n" + 
					"max(b.part_desc) as partdesc, sum(a.amount) as amount ,pay_rec_id from rf_client_ledger a left join mf_client_ledger_part b on (a.part_id  = b.part_id)  \n" + 
					"where a.entity_id  = '"+entity_id+"' and a.unit_id  = '"+unit_id+"'\n" + 
					"AND a.status_id = 'A'\n" + 
					"group by a.part_id, a.pay_rec_id,a.sched_date\n" + 
					"order by a.sched_date, max(a.client_ledger_id))  c\n" + 
					"where amount !=0\n" + 
					"order by sched_date";*/
			
			
			
			  String sql = "select pay_part_id, partdesc, 0.00, null, null\n" +
			  "from mf_pay_particular \n" +
			  "where pay_part_id in ('106', '033', '040') \n"+ "order by apply_order"; 
			 
			
			/*
			 * String sql =
			 * "SELECT a.pay_part_id, b.partdesc, 0.00, a.pay_rec_id, coalesce(a.or_no, a.ar_no)\n"
			 * + "FROM rf_payments a\n" +
			 * "LEFT JOIN mf_pay_particular b on b.pay_part_id = a.pay_part_id \n" +
			 * "where a.entity_id = '"+entity_id+"'\n" + "and a.proj_id = '015'\n" +
			 * "and a.pbl_id = getinteger('"+unit_id+"')::VARCHAR \n"+
			 * "and a.seq_no = "+seq_no+"\n" + "and a.refund_date is null \n" +
			 * "and b.apply_ledger = true\n" +
			 * "and a.amount - COALESCE(a.applied_amt, 0.00) != 0 \n"+
			 * "and a.status_id = 'A'\n" + "order by actual_date;";
			 */

			FncSystem.out("Display Credit To Payment", sql);	
			pgSelect db = new pgSelect();
			db.select(sql);
			if(db.isNotNull()){
				for(int x=0; x < db.getRowCount(); x++){
					model.addRow(db.getResult()[x]);
					listModel.addElement(model.getRowCount());
				}
			}
		}
	}
	
	// ADDED BY MONIQUE DTD 2023-03-03 TO AUTOMATE COP PARTICULARS FOR SPOTCASH	
	public static void displayLedgerCrdtToSpotCash(modelCreditofPayment_CrdtToLedger model,JList rowHeader, String entity_id, String unit_id, String proj_id, String seq_no){//Displays Data for the legder payments
		if(model != null && rowHeader != null){
			FncTables.clearTable(model);

			DefaultListModel listModel = new DefaultListModel();
			rowHeader.setModel(listModel);

			  String sql = "select pay_part_id, partdesc, 0.00, null, null\n" +
			  "from mf_pay_particular \n" +
			  "where pay_part_id in ('106','262', '040') \n"+ "order by apply_order"; 

			FncSystem.out("Display Credit To Payment", sql);	
			pgSelect db = new pgSelect();
			db.select(sql);
			if(db.isNotNull()){
				for(int x=0; x < db.getRowCount(); x++){
					model.addRow(db.getResult()[x]);
					listModel.addElement(model.getRowCount());
				}
			}
		}
	}
	
	public static void displaySavedCrdtToLedger(modelCreditofPayment_CrdtToLedger model, JList rowHeader, String req_no){
		if(model != null && rowHeader != null){
			FncTables.clearTable(model);
			DefaultListModel listModel = new DefaultListModel();
			rowHeader.setModel(listModel);
			
			String sql = "select a.apply_to_part_id, b.partdesc ,a.applied_amount, a.apply_from_pay_rec_id, b.apply_ledger\n" + 
						 "from rf_credit_dl a\n" + 
						 "left join mf_pay_particular b on b.pay_part_id = a.apply_to_part_id\n" + 
						 "where a.request_no = '"+req_no+"'";
			
			pgSelect db = new pgSelect();
			db.select(sql);
			
			FncSystem.out("Display Credit To Ledger", sql);
			if(db.isNotNull()){
				for (int x= 0; x<db.getRowCount(); x++){
					model.addRow(db.getResult()[x]);
					listModel.addElement(model.getRowCount());
				}
			}
		}
	}

	public static void displayCrdtToOther(modelCreditofPayment_CrdtPymntOther model, JList rowHeader){
		if(model != null && rowHeader != null){
			FncTables.clearTable(model);

			DefaultListModel listModel = new DefaultListModel();
			rowHeader.setModel(listModel);

			String sql = "select false, pay_part_id as \"Part ID\", particulars as \"Particular\", 0.00 as \"Amount\" from mf_pay_particular where apply_ledger = false AND status_id = 'A' order by particulars";

			FncSystem.out("Display Credit Payment To Others", sql);
			pgSelect db = new pgSelect();
			db.select(sql);
			if(db.isNotNull()){
				for(int x = 0; x<db.getRowCount(); x++){
					model.addRow(db.getResult()[x]);
					listModel.addElement(model.getRowCount());
				}
			}
		}
	}
	
	public static void displayCrdtToLedger(modelCreditofPayment_CrdtToLedger model, JList rowHeader, String req_no){
		model.clear();
		DefaultListModel listModel = new DefaultListModel();
		
		rowHeader.setModel(listModel);
		
		pgSelect db = new pgSelect();
		
		String sql = "";
		db.select(sql);
		
		if(db.isNotNull()){
			
		}
		
		
	}

	public static void displaySavedCrdtToOther(modelCreditofPayment_CrdtPymntOther model, JList rowHeader, String req_no){
		if(model != null && rowHeader!= null){
			FncTables.clearTable(model);

			DefaultListModel listModel = new DefaultListModel();//OK NA DITO
			rowHeader.setModel(listModel);

			String sql = "select true, a.apply_to_part_id, b.particulars, a.applied_amount \n" + 
					"from rf_credit_dl a \n" + 
					"left join mf_pay_particular b on b.pay_part_id = a.apply_to_part_id\n"+
					"where request_no = '"+req_no+"'";

			FncSystem.out("Display Credit to Other", sql);
			pgSelect db = new pgSelect();
			db.select(sql);
			if(db.isNotNull()){
				for(int x = 0; x<db.getRowCount(); x++){
					model.addRow(db.getResult()[x]);
					listModel.addElement(model.getRowCount());

				}
			}
		}
	}

	public static void displayCrdtFrmOther(modelROP_Miscellaneous model, JList rowHeader, String req_no){//XXX DISPLAY ME IN CREDIT OF PAYMENT WHEN DATA IS AVAILABLE
		if(model != null && rowHeader != null){
			FncTables.clearTable(model);

			DefaultListModel listModel = new DefaultListModel();
			rowHeader.setModel(listModel);

			String sql = "select apply_from_pay_rec_id as \"Pay Rec ID\", b.pay_part_id as \"Part ID\", \n" + 
					"c.particulars as \"Particulars\",applied_amount as \"Amount\"\n" + 
					"from rf_credit_dl a \n" + 
					"left join rf_payments b on a.apply_from_pay_rec_id = a.rec_id\n" + 
					"left join mf_pay_particular c on c.pay_part_id = b.pay_part_id \n" + 
					"where request_no = '"+req_no+"'\n"+
					"order by a.rec_id";

			FncSystem.out("Display Credit From Other", sql);
			pgSelect db = new pgSelect();
			db.select(sql);
			if(db.isNotNull()){
				for(int x= 0; x<db.getRowCount(); x++){
					model.addRow(db.getResult()[x]);
					listModel.addElement(model.getRowCount());
				}
			}
		}
	}

	//CHECK IF THIS WILL BE REMOVED
	public static String setsqlCreditType(String crdt_type, String entity_id, String unit_id, String proj_id, String seq_no){//Setting of sql for the Credit to Unit Lookup after selecting credit type XXX ADD SEQ_NO AND PROJ_ID
		String sql = "";
		if(crdt_type.equals("A")){//FROM LEDGER TO OTHER PAYMENT
			sql = "select trim(a.entity_id) as \"Entity ID\", trim(get_client_name(a.entity_id)) as  \"Name\", \n" + 
					"trim(b.unit_id) as \"Unit ID\", trim(a.projcode) as \"Proj. Code\",\n" + 
					"a.seq_no as \"Seq. No.\", trim(b.description) as \"Description\", \n" + 
					"trim(d.proj_name) as \"Project Name\", trim(c.status_desc) as \"Unit Status\", \n" + 
					"trim(e.pmt_scheme_desc) as \"Payment Scheme\"\n" + 
					"from rf_sold_unit a\n" + 
					"left join mf_unit_info b on b.pbl_id = a.pbl_id and b.proj_id = a.projcode\n" + 
					"left join mf_buyer_status c on c.byrstatus_id = a.currentstatus\n" + 
					"left join mf_project d on d.proj_id = a.projcode\n" + 
					"left join mf_payment_scheme e on e.pmt_scheme_id = a.pmt_scheme_id\n" + 
					"where a.entity_id = '"+entity_id+"' and c.status_desc != 'Cancelled' \n";
			FncSystem.out("SQL CREDIT TYPE A", sql);
		}
		if(crdt_type.equals("B")){//FROM LEDGER TO LEDGER PAYMENT //XXX CHECK IF PROJ_ID AND SEQ_NO INCLUDED HERE
			sql = "select trim(a.entity_id) as \"Entity ID\", trim(get_client_name(a.entity_id)) as  \"Name\", \n" + 
					"trim(b.unit_id) as \"Unit ID\", trim(a.projcode) as \"Proj. Code\",\n" + 
					"a.seq_no as \"Seq. No.\",trim(b.description) as \"Description\", \n" + 
					"trim(d.proj_name) as \"Project Name\",trim(c.status_desc) as \"Unit Status\",  \n"+
					"trim(e.pmt_scheme_desc) as \"Payment Scheme\"\n" + 
					"from rf_sold_unit a\n" + 
					"left join mf_unit_info b on b.pbl_id = a.pbl_id and b.proj_id = a.projcode\n" + 
					"left join mf_buyer_status c on c.byrstatus_id = a.currentstatus\n" + 
					"left join mf_project d on d.proj_id = a.projcode\n" + 
					"left join mf_payment_scheme e on e.pmt_scheme_id = a.pmt_scheme_id\n" + 
					"where a.entity_id = '"+entity_id+"' \n"+
					"and a.unit_id = '"+unit_id+"' \n"+
					//"and a.projcode = '"+proj_id+"' \n"+
					//"and a.seq_no = "+seq_no+" \n"+
					"and c.status_desc != 'Cancelled'\n";
			FncSystem.out("SQL CREDIT TYPE B", sql);
			//XXX !unit_id
		}
		if(crdt_type.equals("C")){//FROM OTHER TO LEDGER PAYMENT
			sql = "select trim(a.entity_id) as \"Entity ID\", trim(get_client_name(a.entity_id)) as  \"Name\", \n" + 
					"trim(b.unit_id) as \"Unit ID\",trim(a.projcode) as \"Proj. Code\", \n" + 
					"a.seq_no as \"Seq. No.\", trim(d.proj_name) as \"Project Name\",\n" + 
					"trim(b.description) as \"Description\", trim(c.status_desc) as \"Unit Status\",  \n" + 
					"trim(e.pmt_scheme_desc) as \"Payment Scheme\"\n" + 
					"from rf_sold_unit a\n" + 
					"left join mf_unit_info b on b.pbl_id = a.pbl_id and b.proj_id = a.projcode\n" + 
					"left join mf_buyer_status c on c.byrstatus_id = a.currentstatus\n" + 
					"left join mf_project d on d.proj_id = a.projcode\n" + 
					"left join mf_payment_scheme e on e.pmt_scheme_id = a.pmt_scheme_id\n" + 
					"where a.entity_id = '"+entity_id+"' and c.status_desc != 'Cancelled' \n";
			FncSystem.out("SQL CREDIT TYPE C", sql);
		}
		if(crdt_type.equals("D")){//FROM OTHER TO OTHER PAYMENT
			sql = null;
			FncSystem.out("SQL CREDIT TYPE D", sql);
		}
		return sql;
	}

	public static String sqlSearch(){//SQL FOR THE SEARCH BUTTON
		
		return "select trim(a.request_no) as \"Request No\", \n" + 
				"trim(a.entity_id) as \"Entity ID\", trim(get_client_name(a.entity_id)) as \"Name\", \n" + 
				"trim(a.unit_id) as \"Unit ID\", trim(a.proj_id) as \"Proj. ID\", \n" + 
				"a.seq_no as \"Seq. No\", trim(get_unit_description(a.unit_id)) as \"Description\", \n" + 
				"trim(get_project_name(a.proj_id)) as \"Proj. Name\", trim(c.status_desc) as \"Status Desc\",\n" + 
				"trim(d.req_status_desc) as \"Request Status\", a.request_date as \"Request date\", \n" + 
				"trim(a.credit_type) as \"Credit Type\", a.credit_amount as \"Credit Amount\", \n"+
				"e.request_by as \"Request By\", e.request_remarks as \"Remarks\" " + 
				"from rf_credit_hd a\n" + 
				"left join rf_sold_unit b on b.entity_id = a.entity_id and b.pbl_id = getinteger(a.unit_id)::VARCHAR and b.projcode = a.proj_id and b.seq_no = a.seq_no\n" + 
				"left join mf_buyer_status c on c.byrstatus_id = b.currentstatus and coalesce(b.server_id, '') = coalesce(c.server_id, '')\n" + 
				"left join mf_request_status d on d.req_status_id = a.request_status\n"+
				"left join req_rt_request_header e on e.request_no = a.request_no";
	}
	
	public static void saveRtReqHeader(String req_no, String req_by, String entity_id, String from_proj_id, String from_unit_id, String from_seq_no, 
									   String to_entity_id, String to_proj_id, String to_unit_id, String to_seq_no, String remarks){//Saving into the req_rt_req_header table
		pgUpdate db = new pgUpdate();

		String sql = "INSERT INTO req_rt_request_header(rec_id, request_no, request_date, request_status, request_by, request_remarks , \n"+
					 "old_entity_id, old_proj_id, old_unit_id, old_seq_no, new_entity_id, new_proj_id, new_unit_id, new_seq_no, add_by, add_date, \n"+
					 "req_type_id, isdocorrefund)\n" + 
					 "VALUES ((select coalesce(max(rec_id), 0) + 1 from req_rt_request_header), '"+req_no+"', now(), 'S', '"+req_by+"', " + 
					 "'"+remarks+"', '"+entity_id+"', '"+from_proj_id+"', '"+from_unit_id+"', "+from_seq_no+", \n"+
					 "'"+to_entity_id+"', '"+to_proj_id+"', '"+to_unit_id+"', "+to_seq_no+", \n"+
					 "'"+UserInfo.EmployeeCode+"', now(), 'Credit of Payment', true) \n";
		db.executeUpdate(sql, true);
		db.commit();
	
	}

	public static void saveReqCreditHD(String req_no, String entity_id, String unit_id, String proj_id, String seq_no , String crdtto_unit_id, String crdt_type ,BigDecimal amt_to_credit){
		pgUpdate db = new pgUpdate();
		//Saving Into the rf_credit_hd table
		String sql = "INSERT INTO rf_credit_hd(request_no, request_date, request_status, " + 
					 "entity_id, unit_id, proj_id, seq_no, credit_to_unit, credit_type, credit_amount)\n" + 
					 "VALUES ('"+req_no+"', now(), 'S', '"+entity_id+"', '"+unit_id+"', " + 
					 "'"+proj_id+"', "+seq_no+", '"+crdtto_unit_id+"', '"+crdt_type+"', "+amt_to_credit+") \n";

		db.executeUpdate(sql, true);
		db.commit();
	}

	public static void saveReqCreditDL(String req_no, String crdt_type, modelROP_Miscellaneous modelOther, modelCreditofPayment_CrdtToLedger modelCrdtToLedger, modelCreditofPayment_CrdtPymntOther modelCrdtToOther, BigDecimal amt_to_crdt){
		pgUpdate db = new pgUpdate();
		//Saving into the rf_credit_dl table based on the credit type

		if(crdt_type.equals("A")){//LEDGER TO OTHER
			for (int x = 0; x<modelCrdtToOther.getRowCount() ; x++){
				Boolean isSelected = (Boolean) modelCrdtToOther.getValueAt(x, 0);
				
				if(isSelected){
					String apply_to_part_id = (String) modelCrdtToOther.getValueAt(x, 1);
					BigDecimal applied_amt = (BigDecimal) modelCrdtToOther.getValueAt(x, 3);
					Integer apply_from_pay_rec_id = (Integer) modelCrdtToOther.getValueAt(x, 4);
					
					String sql = "INSERT INTO rf_credit_dl(rec_id, request_no, ,apply_to_part_id, applied_amount)\n" + 
								 "VALUES ((select coalesce(max(rec_id),0) +1  from rf_credit_dl), '"+req_no+"', "+apply_from_pay_rec_id+" ,'"+apply_to_part_id+"', "+applied_amt+");";
					db.executeUpdate(sql, true);
				}
			}
		}
		
		if(crdt_type.equals("B")){ //LEDGER TO LEDGER
			for (int x =0; x<modelCrdtToLedger.getRowCount(); x++){
				//Boolean isSelected = (Boolean) modelCrdtToLedger.getValueAt(x, 0);
				String apply_to_part_id = (String) modelCrdtToLedger.getValueAt(x, 0);
				BigDecimal applied_amt = (BigDecimal) modelCrdtToLedger.getValueAt(x, 2);
				Integer pay_rec_id = (Integer) modelCrdtToLedger.getValueAt(x, 3);
				
				if(applied_amt.doubleValue() != 0){
					String sql = "INSERT INTO rf_credit_dl(rec_id, request_no, apply_from_pay_rec_id , apply_to_part_id ,applied_amount)\n" + 
								 "VALUES ((select coalesce(max(rec_id),0) +1  from rf_credit_dl), '"+req_no+"', "+pay_rec_id+" ,'"+apply_to_part_id+"' ,"+applied_amt+")";
					db.executeUpdate(sql, true);
				}
			}
		}
		
		if(crdt_type.equals("C")){ //OTHER TO LEDGER

			for(int x = 0; x<modelCrdtToLedger.getRowCount(); x++){
				//String apply_to_part_id = (String) modelOther.getValueAt(x, 0);
				
				String apply_to_part_id = (String) modelCrdtToLedger.getValueAt(x, 0);
				BigDecimal applied_amt = (BigDecimal) modelCrdtToLedger.getValueAt(x, 2);
				
				if(applied_amt.doubleValue() != 0){
					String sql = "INSERT INTO rf_credit_dl(rec_id, request_no, apply_to_part_id,applied_amount)\n" + 
							 	 "VALUES ((select coalesce(max(rec_id),0) +1  from rf_credit_dl), '"+req_no+"', '"+apply_to_part_id+"' ,"+applied_amt+");";
					db.executeUpdate(sql, true);
				}
			}
		}
		
		if(crdt_type.equals("D")){//OTHER TO OTHER
			for (int x = 0; x<modelCrdtToOther.getRowCount() ; x++){
				Boolean isSelected = (Boolean) modelCrdtToOther.getValueAt(x, 0);
				if(isSelected){
					String apply_to_part_id = (String) modelCrdtToOther.getValueAt(x, 1);
					BigDecimal applied_amt = (BigDecimal) modelCrdtToOther.getValueAt(x, 3);
					
					String sql = "INSERT INTO rf_credit_dl(rec_id, request_no, apply_to_part_id, applied_amount)\n" + 
								 "VALUES ((select coalesce(max(rec_id),0) +1  from rf_credit_dl), '"+req_no+"', '"+apply_to_part_id+"', "+applied_amt+")";
					db.executeUpdate(sql, true);
				}
			}
		}
		db.commit();
	}

	public static void cancelCOP(String req_no){
		pgUpdate db = new pgUpdate();

		String sqlRtReqHeader = "UPDATE req_rt_request_header SET request_status= 'X' WHERE request_no = '"+req_no+"'";

		db.executeUpdate(sqlRtReqHeader, true);

		String sql = "UPDATE rf_credit_hd SET request_status= 'X' WHERE request_no = '"+req_no+"'";

		db.executeUpdate(sql, true);

		db.commit();
	}
	
	public static boolean sqlApplyCreditRequest(String req_no){
		pgSelect db = new pgSelect();
		String sqlApplyCreditRequest = "select sp_apply_credit_request('"+req_no+"', '"+UserInfo.EmployeeCode+"')";
		FncSystem.out("Display Applying of Credit Request", sqlApplyCreditRequest);
		db.select(sqlApplyCreditRequest);
		
		Boolean isApplyCredit = (Boolean) db.getResult()[0][0];
		
		return isApplyCredit;
	}
	
	public static void postCreditOfPayment(String req_no, String credit_type){
		pgUpdate db = new pgUpdate();

		String sqlCreditType = "select credit_type from rf_credit_hd where request_no = '"+req_no+"'";
		FncSystem.out("Display 1st", sqlCreditType);
		pgSelect dbcrdttype = new pgSelect();
		dbcrdttype.select(sqlCreditType);
		
		if(dbcrdttype.isNotNull()){
			String req_type = (String) dbcrdttype.getResult()[0][0];
			
			/*if(sqlApplyCreditRequest(req_no)){
				if(req_type.equals("B") || req_type.equals("C")){//APPLY TO LEDGER 
					CreditLegderToLedger(req_no); //XXX PAG MAY ERROR CHECK VALUE OF PAY REC ID in the function
				}
				String sqlRfCreditPayments = "UPDATE rf_credit_payments set added_by = '"+UserInfo.EmployeeCode+"' where request_no = '"+req_no+"'";
				db.executeUpdate(sqlRfCreditPayments, true);
			}*/
			
			if(req_type.equals("B") || req_type.equals("D")){
				pgSelect dbSelect = new pgSelect();
				String SQL = "SELECT sp_create_credit_of_payment_op('"+req_no+"', '"+UserInfo.EmployeeCode+"', '"+UserInfo.Branch+"')";
				dbSelect.select(SQL);
			}
		}
		db.commit();
	}
	
	private static void CreditLegderToLedger(String req_no){//XXX CHECK VALUE OF PAY_REC_ID
		
		String sql = "SELECT amount,entity_id, co_id, proj_id,pbl_id,seq_no, unit_id, pay_part_id ,\n" + 
					 "(select (case when vat = true then 12.00 else 0.00 end) from mf_unit_info where unit_id = a.unit_id),\n" + 
					 "pay_rec_id , or_no, '00005' \n" + 
					 "FROM rf_payments a\n"+
					 "WHERE request_no = '"+req_no+"'\n" + 
					 "ORDER BY pay_rec_id desc";
		
		FncSystem.out("Display 2nd", sql);
		pgSelect db = new pgSelect();
		db.select(sql);
	
		for(int x= 0; x<db.getRowCount(); x++){
			BigDecimal amount = (BigDecimal) db.getResult()[x][0];
			String entity_id = (String) db.getResult()[x][1];
			String co_id = (String) db.getResult()[x][2];
			String proj_id = (String) db.getResult()[x][3];
			String pbl_id = (String) db.getResult()[x][4];
			Integer seq_no = (Integer) db.getResult()[x][5];
			String unit_id = (String) db.getResult()[x][6];
			String pay_part_id = (String) db.getResult()[x][7];
			BigDecimal vat = (BigDecimal) db.getResult()[x][8];
			Integer pay_rec_id = (Integer) db.getResult()[x][9];
			String or_no = (String) db.getResult()[x][10];
			String str = (String) db.getResult()[x][11];
			
			System.out.printf("Display pay_rec_id: %s%n", pay_rec_id);
			
			// ERROR IN THIS FUNCTION (more than one row returned by a subquery used as an expression) ok na dito
			String sqlSaveToLedger = "select sp_apply_to_ledger('"+entity_id+"', '"+proj_id+"', '"+pbl_id+"', '"+seq_no+"', '"+unit_id+"', '"+pay_part_id+"', '"+amount+"', '"+pay_rec_id+"', '"+UserInfo.EmployeeCode+"', false)";
			FncSystem.out("Display Saving of Ledger", sqlSaveToLedger);
			pgSelect dbSaveLedger = new pgSelect();
			dbSaveLedger.select(sqlSaveToLedger);
			
		}
	}
	
}
