package Accounting.Cashiering;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.table.DefaultTableModel;

import Database.pgSelect;
import Database.pgUpdate;
import tablemodel.modelIssuanceCashReturn;
import tablemodel.modelIssuanceOfReceipt_PaymentList;
import Functions.FncReport;
import Functions.FncSystem;
import Functions.UserInfo;

/**
 * @author Alvin Gonzales
 *
 */
public class _IssuanceOfReceipt {

	/**
	 * 
	 */
	public _IssuanceOfReceipt() {
		// TODO Auto-generated constructor stub
	}
	
	public static String sqlClients() {
		return "SELECT * FROM view_ir_new_payment_clients('"+UserInfo.Branch+"');";
	}

	public static String getHolding() {
		String SQL = "select trim(a.client_seqno) as \"Sequence No.\",\n" + 
				"trim(a.entity_id) as \"Client ID\", trim(b.entity_name) as \"Client Name\",\n" + 
				"trim(c.co_id) as \"Company ID\", trim(c.company_name) as \"Company Name\",\n" + 
				"trim(a.unit_id) as \"Unit ID\", trim(d.description) as \"Unit Description\",\n" + 
				"d.lotarea as \"Lot Area\", d.sellingprice as \"Selling Price\", a.trans_date as \"Trans. Date\", a.actual_date as \"Actual Date\"\n" + 
				"\n" + 
				"from rf_tra_header a\n" + 
				"left join rf_entity b on b.entity_id = a.entity_id\n" + 
				"left join mf_company c on c.co_id = a.co_id\n" + 
				"left join mf_unit_info d on d.unit_id = a.unit_id and d.proj_id = a.proj_id and d.pbl_id = a.pbl_id\n" + 
				"\n" + 
				"where a.trans_date::date = current_date\n" + 
				"and d.status_id = 'O'\n" + 
				"and a.client_seqno not in (select client_no from rt_pay_header)\n" + 
				"\n" + 
				"order by a.client_seqno desc;";
		FncSystem.out("Clients", SQL);
		return SQL;
	}

	public static String getReservation() {
		String SQL = "select trim(a.client_seqno) as \"Sequence No.\",\n" + 
				"trim(a.entity_id) as \"Client ID\", trim(b.entity_name) as \"Client Name\",\n" + 
				"trim(c.co_id) as \"Company ID\", trim(c.company_name) as \"Company Name\",\n" + 
				"trim(d.unit_id) as \"Unit ID\", trim(d.description) as \"Unit Description\",\n" + 
				"d.lotarea as \"Lot Area\", d.sellingprice as \"Selling Price\", a.trans_date as \"Trans. Date\", a.booking_date as \"Actual Date\"\n" + 
				"\n" + 
				"from rf_pay_header a\n" + 
				"left join rf_entity b on b.entity_id = a.entity_id\n" + 
				"left join mf_company c on c.co_id = a.co_id\n" + 
				"left join mf_unit_info d on d.pbl_id = a.pbl_id and d.proj_id = a.proj_id and d.pbl_id = a.pbl_id\n" + 
				"\n" + 
				"where a.trans_date::date = current_date\n" + 
				"and a.op_status = 'A'\n" + 
				"and a.client_seqno in (select client_seqno from rf_pay_detail where part_type = '106' and status_id = 'A')\n" + 
				"\n" + 
				"order by a.client_seqno desc;";
		FncSystem.out("Clients", SQL);
		return SQL;
	}

	public static void displayHoldingPayments(modelIssuanceOfReceipt_PaymentList modelMain, String client_seqno) {
		modelMain.clear();

		String SQL = 
			"select trim(a.part_id), " +  //0
			"trim(b.partdesc), " +
			"a.amount,\n" + 
			"(case when a.bank is null then 'CASH' else 'CHECK' end) as pmt_type, " +  //3
			"trim(a.check_no), " +
			"null as check_type_id, " +
			"null as check_type, " +
			"a.check_date, trim(a.acct_no),\n" + //6
			"trim(a.bank), " +
			"trim(c.bank_name), " +
			"trim(a.branch), " +  //9
			"trim(d.branch_name), " +
			"trim(a.receipt_no), " +
			"a.receipt_type, " +  //12
			"trim(e.doc_alias)\n" + 
				"\n" + 
				"from rf_tra_detail a\n" + 
				"left join mf_pay_particular b on b.pay_part_id = a.part_id\n" + 
				"left join mf_bank c on c.bank_id = a.bank\n" + 
				"left join mf_office_branch d on d.branch_id = a.branch\n" + 
				"left join mf_doc_details e on e.doc_id = a.receipt_type\n" + 
				"\n" + 
				"where a.client_seqno = '"+ client_seqno +"';";

		//System.out.printf("SQL: %s%n", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				modelMain.addRow(db.getResult()[x]);
			}
		}
	}

	/*public static void displayHoldingPayments(modelOrderOfPayments_PaymentDetails modelMain, String client_seqno) {
		modelMain.clear();

		String SQL = "select trim(a.part_type), trim(b.partdesc), a.amount,\n" + 
				"(case when a.bank is null then 'CASH' else 'CHECK' end) as pmt_type, trim(a.check_no), a.check_type as check_type, f.check_desc, a.check_date, trim(a.acct_no),\n" + 
				"trim(a.bank), trim(c.bank_name), trim(a.branch), trim(d.branch_name), trim(a.receipt_no), a.receipt_type, trim(e.doc_alias)\n" + 
				"\n" + 
				"from rf_pay_detail a\n" + 
				"left join mf_pay_particular b on b.pay_part_id = a.part_type\n" + 
				"left join mf_bank c on c.bank_id = a.bank\n" + 
				"left join mf_office_branch d on d.branch_id = a.branch\n" + 
				"left join mf_doc_details e on e.doc_id = a.receipt_type\n" + 
				"left join mf_check_type f on f.check_id = a.check_type\n" + 
				"\n" + 
				"where a.client_seqno = '"+ client_seqno +"'\n" + 
				"order by a.pay_detail_id;";

		System.out.printf("HOLDING PAYMENTS: %s%n", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				modelMain.addRow(db.getResult()[x]);
			}
		}
	}*/

	public static void displayReservationPayments(modelIssuanceOfReceipt_PaymentList modelMain, String client_seqno) {
		modelMain.clear();

		String SQL = 
				"select trim(a.part_type), " +
				"trim(b.partdesc), " +
				"a.amount,\n" + 
				"(case when a.bank is null then 'CASH' else 'CHECK' end) as pmt_type, " +
				"trim(a.check_no), " +
				"a.check_type as check_type, " +
				"f.check_desc, " +
				"a.check_date, " +
				"trim(a.acct_no),\n" + 
				"trim(a.bank), " +
				"trim(c.bank_name), " +
				"trim(a.branch), " +
				"trim(g.bank_branch_location), " +
				"trim(a.ar_no), " +
				"a.receipt_type, " +
				"trim(e.doc_alias)\n" + 
				"\n" + 
				"from rf_pay_detail a\n" + 
				"left join mf_pay_particular b on b.pay_part_id = a.part_type\n" + 
				"left join mf_bank c on c.bank_id = a.bank\n" + 
				"left join mf_office_branch d on d.branch_id = a.branch\n" + 
				"left join mf_doc_details e on e.doc_id = a.receipt_type\n" + 
				"left join mf_check_type f on f.check_id = a.check_type\n" + 
				"left join mf_bank_branch g ON g.bank_id = a.bank AND g.bank_branch_id = a.branch\n" + 
				"\n" + 
				"where a.client_seqno = '"+ client_seqno +"'\n" + 
				"order by a.pay_detail_id;";

		System.out.printf("RESERVATION PAYMENTS: %s%n%n", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				modelMain.addRow(db.getResult()[x]);
			}
		}
	}

	public static String sqlNewPayment() {
		String SQL = "SELECT * FROM view_ir_new_payments('"+UserInfo.Branch+"');";
		System.out.printf("New Payment: %s%n%n", SQL);
		return SQL;
	}

	public static Object[] getNewPaymentDetails(String client_seqno) {
		String SQL = 
				"SELECT " +
				"trim(a.client_seqno) as \"Sequence No.\",\n" + //0
				"TRIM(a.entity_id) as \"Client ID\", " +		//1
				"TRIM(b.entity_name) as \"Client Name\",\n" + 	//2
				"TRIM(c.co_id) as \"Company ID\", " +			//3
				"TRIM(c.company_name) as \"Company Name\",\n" + //4
				"TRIM(d.unit_id) as \"Unit ID\", " +			//5
				//"TRIM(d.description) as \"Unit Description\",\n" + //6
				"TRIM(get_merge_unit_desc(d.unit_id)) as \"Unit Description\", \n"+
				"d.lotarea as \"Lot Area\", " +					//7
				"d.sellingprice as \"Selling Price\", " +		//8
				"a.trans_date as \"Trans. Date\", " +			//9
				"a.booking_date as \"Actual Date\",\n" + 		//10
				"d.model_id as \"Model ID\", " +				//11
				//"e.model_desc as \"Model\", " +					//12
				"get_merge_model_desc(d.unit_id) as \"Model\", "+
				"a.proj_id as \"Project ID\", " +				//13
				"get_project_name(a.proj_id) as \"Project\",\n" + //14
				"a.seq_no::int as \"Unit Seq.\", " +			//15
				"(SELECT CASE WHEN array_length(array_agg(particular)) > 1 THEN 'MULTIPLE PAYMENTS' ELSE array_to_string(array_agg(particular), ',') END\n" + 
				"                                 FROM (SELECT DISTINCT get_pay_particular_name(part_type)::VARCHAR as particular\n" + 
				"                                       FROM rf_pay_detail\n" + 
				"                                       WHERE client_seqno = a.client_seqno\n" + 
				"                                       ORDER BY get_pay_particular_name(part_type)::VARCHAR) a) as \"Transaction\"\n" +  //16
				"FROM rf_pay_header a\n" + 
				"LEFT JOIN rf_entity b ON b.entity_id = a.entity_id\n" + 
				"LEFT JOIN mf_company c ON c.co_id = a.co_id\n" + 
				"LEFT JOIN mf_unit_info d ON d.pbl_id = a.pbl_id AND d.proj_id = a.proj_id AND d.pbl_id = a.pbl_id\n" + 
				"LEFT JOIN mf_product_model e ON e.model_id = d.model_id and e.server_id = d.server_id \n" + 
				"--WHERE a.trans_date::date = current_date\n" + //Edited by Alvin - 2015-05-27
				"WHERE a.op_status = 'O'\n" + 
				/*	Modified by Mann2x; Date Modified: February 21, 2017; The list of sequence numbers should be filtered by branch;	*/  
				"AND a.branch_id = '"+UserInfo.Branch+"' \n" + 
				"AND trim(a.client_seqno) = '"+ client_seqno +"'\n" +
				"AND NOT EXISTS (SELECT * \n"+
				"     			 from rf_direct_deposit_for_issuance \n"+
				"  				 where client_seqno = a.client_seqno) \n"+
				"ORDER BY trim(a.client_seqno) DESC;";

		System.out.printf("New Payments: %s%n%n", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);
		if(db.isNotNull()){
			return db.getResult()[0];
		}else{
			return null;
		}
	}

	public static Object[] getHoldingDetails(String client_seqno) {
		
		String SQL = "SELECT " +
				"trim(a.client_seqno) as \"Sequence No.\",\n" + //0
				"TRIM(a.entity_id) as \"Client ID\", " +		//1
				"TRIM(b.entity_name) as \"Client Name\",\n" + 	//2
				"TRIM(c.co_id) as \"Company ID\", " +			//3
				"TRIM(c.company_name) as \"Company Name\",\n" + //4	
				"TRIM(d.unit_id) as \"Unit ID\", " +			//5
				"TRIM(d.description) as \"Unit Description\",\n" + //6
				"d.lotarea as \"Lot Area\", " +				//7
				"d.sellingprice as \"Selling Price\", " +	//8
				"a.trans_date as \"Trans. Date\", " +		//9
				"a.actual_date as \"Actual Date\",\n" + 	//10
				"d.model_id as \"Model ID\", " +			//11
				"e.model_desc as \"Model\", " +				//12
				"a.proj_id as \"Project ID\", " +			//13	
				"get_project_name(a.proj_id) as \"Project\",\n" + //14
				"a.seq_no::INT as \"Unit Seq.\", " +		//15
				"'HOLDING' as \"Transaction\"\n" + 			//16
				"FROM rf_tra_header a\n" + 
				"LEFT JOIN rf_entity b ON b.entity_id = a.entity_id\n" + 
				"LEFT JOIN mf_company c ON c.co_id = a.co_id\n" + 
				"LEFT JOIN mf_unit_info d ON d.pbl_id = a.pbl_id AND d.proj_id = a.proj_id AND d.pbl_id = a.pbl_id\n" + 
				"LEFT JOIN mf_product_model e ON e.model_id = d.model_id\n" + 
				"--WHERE a.trans_date::date = current_date\n" + //Edited by Alvin - 2015-03-27
				"WHERE a.status_id = 'O'\n" + //Edited by Alvin - 2015-04-01 *a.status_id = 'O'
				/*	Added by Mann2x; Date Added: February 21, 2017; The list of sequence numbers should be filtered by branch;	*/
				"AND a.branch_id = '"+UserInfo.Branch+"' \n" +
				"AND trim(a.client_seqno) NOT IN (SELECT client_seqno FROM rf_tra_detail WHERE tran_date::DATE = CURRENT_DATE AND status_id = 'P')\n" + 
				"AND a.receipt_no IS NULL\n" + 
				"AND trim(a.client_seqno) = '"+ client_seqno +"'\n" + 
				"ORDER BY trim(a.client_seqno) DESC;";

		System.out.printf("Holding: %s%n", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);
		if(db.isNotNull()){
			return db.getResult()[0];
		}else{
			return null;
		}
	}

	public static Object[] getReceiptDetails(String client_seqno, String transaction, String entity_id, String proj_id, String pbl_id, Integer seq_no) {
		String SQL = "SELECT get_receipt_details('"+ client_seqno +"', '"+ transaction +"', '"+ entity_id +"', '"+ proj_id +"', '"+ pbl_id +"', "+ seq_no +");";

		FncSystem.out("Receipt", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);
		if(db.isNotNull()){
			Object[] receipt = ((String) db.getResult()[0][0]).split("-");

			return receipt;
		}else{
			return null;
		}
	}

	public static Boolean saveHolding(String client_seqno, String co_id, String unit_id) {
		pgSelect db = new pgSelect();
		/* Modified by Mann2x; Date Modified: February 9, 2017; Modified so that the branch will 
		 * be determined depending on which server the user logs in to.
		db.select("SELECT sp_ir_post_holding('"+ client_seqno +"', '"+ unit_id +"', '"+ co_id +"', '"+ UserInfo.Branch +"', '"+ UserInfo.EmployeeCode +"')", "Save", true);
		*/
		db.select("SELECT sp_ir_post_holding('"+ client_seqno +"', '"+ unit_id +"', '"+ co_id +"', '"+ UserInfo.Branch +"', '"+ UserInfo.EmployeeCode +"')", "Save", true);
		if(db.isNotNull()){
			return (Boolean) db.getResult()[0][0];
		}else{
			return false;
		}
	}

	public static Boolean saveReservation(String co_id, String client_seqno, String part_id, BigDecimal total_amount) {
		/* Modified by Mann2x; Date Modified: February 9, 2017; Modified so that the branch will 
		 * be determined depending on which server the user logs in to.
		String branch_id = UserInfo.Branch;
		*/
		String branch_id = UserInfo.Branch;
		String user_id = UserInfo.EmployeeCode;
		String SQL = "SELECT sp_ir_post_reservation('"+ co_id +"', '"+ branch_id +"', '"+ client_seqno +"', '"+ part_id +"', "+ total_amount +", '"+ user_id +"');";
		FncSystem.out("Issuance of Receipt", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL, "Save", true);	
		
		if(db.isNotNull()){

			//Saving of Commission Schedule added by Alvin Gonzales (2015-04-30)
			/*Object[] data = isOR(client_seqno);//XXX
			if(data != null){
				String agent_id = (String) data[10];
				String pmt_scheme_id = (String) data[12];
				String entity_id = (String) data[1];
				String proj_id = (String) data[5];
				String pbl_id = (String) data[6];
				Integer seq_no = ((Long) data[7]).intValue();
				BigDecimal netSprice = (BigDecimal) data[8];
				String hse_model = (String) data[18];
				Boolean isOR = (Boolean) data[15];
				String date_rsvd = (String) data[42];

				if(isOR){
					CreateCommissionSchedule.createCommHeader(agent_id, pmt_scheme_id, entity_id, proj_id, pbl_id, seq_no, netSprice.doubleValue(), co_id, date_rsvd, hse_model);
				}
			}*/

			return (Boolean) db.getResult()[0][0];
		}else{
			return false;
		}
	}

	public static Boolean saveNewPayment(modelIssuanceOfReceipt_PaymentList model, String entity_id, String proj_id, String pbl_id, Integer seq_no, BigDecimal total_amount, String co_id) {
		
		ArrayList<String> listPartID = new ArrayList<String>();
		ArrayList<BigDecimal> listAmount = new ArrayList<BigDecimal>();
		ArrayList<String> listPymntTypeID = new ArrayList<String>();
		ArrayList<String> listCheckNo = new ArrayList<String>();
		ArrayList<String> listCheckTypeID = new ArrayList<String>();
		ArrayList<String> listCheckDate = new ArrayList<String>();
		ArrayList<String> listAccountNo = new ArrayList<String>();
		ArrayList<String> listBankID = new ArrayList<String>();
		ArrayList<String> listBankBranchID = new ArrayList<String>();
		ArrayList<String> listReceiptNo = new ArrayList<String>();
		ArrayList<Boolean> listCredit = new ArrayList<Boolean>();

		for(int x=0; x < model.getRowCount(); x++){
			String part_id = (String) model.getValueAt(x, 0);
			String check_no = (String) model.getValueAt(x, 4);
			String check_type = (String) model.getValueAt(x, 5);
			Date check_date = (Date) model.getValueAt(x, 7);
			String account_no = (String) model.getValueAt(x, 8);
			String bank_id = (String) model.getValueAt(x, 9);
			String bank_branch_id = (String) model.getValueAt(x, 11);
			String receipt_no = (String) model.getValueAt(x, 13);
			Boolean credit = (Boolean) model.getValueAt(x, 16);

			BigDecimal amount;
			try {
				amount = (BigDecimal) model.getValueAt(x, 2);
			} catch (ClassCastException e) {
				amount = new BigDecimal((Long) model.getValueAt(x, 2));
			}


			if(part_id != null){
				listPartID.add(String.format("'%s'", part_id));
			}else{
				listPartID.add("null");
			}

			listAmount.add(amount);
			listPymntTypeID.add(String.format("'%s'", (String) model.getValueAt(x, 3)));

			if(check_no != null){
				listCheckNo.add(String.format("'%s'", check_no));
			}else{
				listCheckNo.add("null");
			}
			if(check_type != null){
				listCheckTypeID.add(String.format("'%s'", check_type));
			}else{
				listCheckTypeID.add("null");
			}
			if(check_date != null){
				listCheckDate.add(String.format("'%s'", check_date));
			}else{
				listCheckDate.add("null");
			}
			if(account_no != null){
				listAccountNo.add(String.format("'%s'", account_no));
			}else{
				listAccountNo.add("null");
			}
			if(bank_id != null){
				listBankID.add(String.format("'%s'", bank_id));
			}else{
				listBankID.add("null");
			}
			if(bank_branch_id != null){
				listBankBranchID.add(String.format("'%s'", bank_branch_id));
			}else{
				listBankBranchID.add("null");
			}
			if(receipt_no != null){
				listReceiptNo.add(String.format("'%s'", receipt_no));
			}else{
				listReceiptNo.add("null");
			}
			listCredit.add(credit);
		}

		String part_id = listPartID.toString().replaceAll("\\[|\\]", "");
		String amount = listAmount.toString().replaceAll("\\[|\\]", "");
		String pymnttype = listPymntTypeID.toString().replaceAll("\\[|\\]", "");
		String check_no = listCheckNo.toString().replaceAll("\\[|\\]", "");
		String check_type = listCheckTypeID.toString().replaceAll("\\[|\\]", "");
		String check_date = listCheckDate.toString().replaceAll("\\[|\\]", "");
		String account_no = listAccountNo.toString().replaceAll("\\[|\\]", "");
		String bank_id = listBankID.toString().replaceAll("\\[|\\]", "");
		String bank_branch_id = listBankBranchID.toString().replaceAll("\\[|\\]", "");
		String receipt_no = listReceiptNo.toString().replaceAll("\\[|\\]", "");
		String credit = listCredit.toString().replaceAll("\\[|\\]", "");

		String SQL = "SELECT sp_ir_post_new('"+ entity_id +"', '"+ proj_id +"', '"+ pbl_id +"', "+ seq_no +", "+ total_amount +", \n" + 
				"     ARRAY["+ part_id +"]::VARCHAR[], \n" + 
				"     ARRAY["+ amount +"]::NUMERIC[], \n" + 
				"     ARRAY["+ pymnttype +"]::VARCHAR[], \n" + 
				"     ARRAY["+ (check_no.equals("'null'") ? "null":check_no) +"]::VARCHAR[], \n" + 
				"     ARRAY["+ (check_type.equals("'null'") ? "null":check_type) +"]::VARCHAR[], \n" + 
				"     ARRAY["+ (check_date.equals("'null'") ? "null":check_date) +"]::TIMESTAMP[],\n" + 
				"     ARRAY["+ (account_no.equals("'null'") ? "null":account_no) +"]::VARCHAR[], \n" + 
				"     ARRAY["+ (bank_id.equals("'null'") ? "null":bank_id) +"]::VARCHAR[], \n" + 
				"     ARRAY["+ (bank_branch_id.equals("'null'") ? "null":bank_branch_id) +"]::VARCHAR[], \n" + 
				"     ARRAY["+ (receipt_no.equals("'null'") ? "null":receipt_no) +"]::VARCHAR[], \n" + 
				"     NULL, \n" + 
				"     ARRAY["+ credit +"]::BOOLEAN[], \n" + 
				/* Modified by Mann2x; Date Modified: February 9, 2017; Modified so that the branch will 
				 * be determined depending on which server the user logs in to.
				"     '"+ co_id +"', '"+ UserInfo.Branch +"', '"+ UserInfo.EmployeeCode +"');";
				*/
				"     '"+ co_id +"', '"+ UserInfo.Branch +"', '"+ UserInfo.EmployeeCode +"');";

		FncSystem.out("Issuance of Receipt", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL, "Save", true);
		if(db.isNotNull()){
			return (Boolean) db.getResult()[0][0];
		}else{
			return true;
		}
	}

	public static Boolean saveOrderedPayment(modelIssuanceOfReceipt_PaymentList model, String client_seqno, BigDecimal total_amount, String co_id) {
		pgSelect db = new pgSelect();
		/* Modified by Mann2x; Date Modified: February 9, 2017; Modified so that the branch will 
		 * be determined depending on which server the user logs in to.
		String SQL = "SELECT sp_ir_post_ordered('"+ client_seqno +"', '"+ UserInfo.Branch +"', '"+ UserInfo.EmployeeCode +"');";
		*/
		String SQL = "";
		
		if(isSalesInvoice(client_seqno)) {
			SQL = "SELECT sp_ir_post_ordered_v2('"+ client_seqno +"', '"+ UserInfo.Branch +"', '"+ UserInfo.EmployeeCode +"');";
		}else {
			SQL = "SELECT sp_ir_post_ordered('"+ client_seqno +"', '"+ UserInfo.Branch +"', '"+ UserInfo.EmployeeCode +"');";
		}
		
		FncSystem.out("Issuance of Receipt", SQL);
		db.select(SQL, "Save", true);

		if(db.isNotNull()){
			return (Boolean) db.getResult()[0][0];
		}else{	
			return true;
		}
	}
	
	public static Boolean isSalesInvoice(String client_seqno) {
		//String SQL = "SELECT EXISTS (SELECT * FROM rf_pay_detail where client_seqno IN ('"+client_seqno+"') AND client_seqno NOT IN ('010220824003', '010220824004') AND part_type in ('087', '259', '262'))";
		//MODIFIED BY MONIQUE DTD 12-20-2022; TO FILTER OR ACCOUNTS FOR ISSUANCE OF SI 
		String SQL = "SELECT EXISTS (SELECT * FROM rf_pay_detail a \n" +
					 "LEFT JOIN rf_pay_header b ON b.client_seqno = a.client_seqno \n" +
				     "WHERE a.client_seqno IN ('"+client_seqno+"') \n" +
				     "AND a.client_seqno NOT IN ('010220824003', '010220824004') \n" + 
				     "AND a.part_type in ('087', '259', '262') \n" +
				     "AND EXISTS (Select * from rf_buyer_status where entity_id = b.entity_id and proj_id = b.proj_id and pbl_id = b.pbl_id and seq_no = b.seq_no \n" +
					 "		  		and TRIM(byrstatus_id) = '01' and TRIM(status_id) = 'A'))"; 
		pgSelect db = new pgSelect();
		db.select(SQL);
		
		return (Boolean) db.getResult()[0][0];
		
	}
	
	public static Boolean saveManualPayment(modelIssuanceOfReceipt_PaymentList model, String client_seqno, 
			BigDecimal total_amount, String co_id, String receipt_id, String receiptNo) {
		pgSelect db = new pgSelect();
		/* Modified by Mann2x; Date Modified: February 9, 2017; Modified so that the branch will 
		 * be determined depending on which server the user logs in to.
		String SQL = "SELECT sp_ir_post_manual_issue('"+ client_seqno +"', '"+ UserInfo.Branch +"', '"+ UserInfo.EmployeeCode +"', '"+receipt_id+"', '"+receiptNo+"');";
		*/
		String SQL = "SELECT sp_ir_post_manual_issue('"+ client_seqno +"', '"+ UserInfo.Branch +"', '"+ UserInfo.EmployeeCode +"', '"+receipt_id+"', '"+receiptNo+"');";
		FncSystem.out("Issuance of Receipt", SQL);
		db.select(SQL, "Save", true);

		if(db.isNotNull()){
			return (Boolean) db.getResult()[0][0];
		}else{
			return true;
		}
	}

	public static Boolean saveCashReturn(modelIssuanceOfReceipt_PaymentList model, /*String client_seqno, */String entity_id, BigDecimal total_amount, String co_id, String remarks) {
		ArrayList<String> listPartID = new ArrayList<String>();
		ArrayList<BigDecimal> listAmount = new ArrayList<BigDecimal>();
		ArrayList<String> listPymntTypeID = new ArrayList<String>();
		ArrayList<String> listCheckNo = new ArrayList<String>();
		ArrayList<String> listCheckTypeID = new ArrayList<String>();
		ArrayList<String> listCheckDate = new ArrayList<String>();
		ArrayList<String> listAccountNo = new ArrayList<String>();
		ArrayList<String> listBankID = new ArrayList<String>();
		ArrayList<String> listBankBranchID = new ArrayList<String>();
		ArrayList<String> listReceiptNo = new ArrayList<String>();
		ArrayList<Boolean> listCredit = new ArrayList<Boolean>();

		for(int x=0; x < model.getRowCount(); x++){
			String part_id = (String) model.getValueAt(x, 0);
			String check_no = (String) model.getValueAt(x, 4);
			String check_type = (String) model.getValueAt(x, 5);
			Date check_date = (Date) model.getValueAt(x, 7);
			String account_no = (String) model.getValueAt(x, 8);
			String bank_id = (String) model.getValueAt(x, 9);
			String bank_branch_id = (String) model.getValueAt(x, 11);
			String receipt_no = (String) model.getValueAt(x, 13);
			Boolean credit = (Boolean) model.getValueAt(x, 16);

			BigDecimal amount = new BigDecimal("0.00");
			
			try {
				amount = (BigDecimal) model.getValueAt(x, 2);
			} catch (ClassCastException e) {
				//amount = new BigDecimal((Long) model.getValueAt(x, 2));
				
				if(model.getValueAt(x, 2) instanceof Double){
					amount = BigDecimal.valueOf((Double) model.getValueAt(x, 2));
				}
				
				if(model.getValueAt(x, 2) instanceof Long){
					amount = BigDecimal.valueOf((Long) model.getValueAt(x, 2));
				}
				
			}

			if(part_id != null){
				listPartID.add(String.format("'%s'", part_id));
			}else{
				listPartID.add("null");
			}

			listAmount.add(amount);
			listPymntTypeID.add(String.format("'%s'", (String) model.getValueAt(x, 3)));

			if(check_no != null){
				listCheckNo.add(String.format("'%s'", check_no));
			}else{
				listCheckNo.add("null");
			}
			if(check_type != null){
				listCheckTypeID.add(String.format("'%s'", check_type));
			}else{
				listCheckTypeID.add("null");
			}
			if(check_date != null){
				listCheckDate.add(String.format("'%s'", check_date));
			}else{
				listCheckDate.add("null");
			}
			if(account_no != null){
				listAccountNo.add(String.format("'%s'", account_no));
			}else{
				listAccountNo.add("null");
			}
			if(bank_id != null){
				listBankID.add(String.format("'%s'", bank_id));
			}else{
				listBankID.add("null");
			}
			if(bank_branch_id != null){
				listBankBranchID.add(String.format("'%s'", bank_branch_id));
			}else{
				listBankBranchID.add("null");
			}
			if(receipt_no != null){
				listReceiptNo.add(String.format("'%s'", receipt_no));
			}else{
				listReceiptNo.add("null");
			}
			listCredit.add(credit);
		}

		String part_id = listPartID.toString().replaceAll("\\[|\\]", "");
		String amount = listAmount.toString().replaceAll("\\[|\\]", "");
		String pymnttype = listPymntTypeID.toString().replaceAll("\\[|\\]", "");
		String check_no = listCheckNo.toString().replaceAll("\\[|\\]", "");
		String check_type = listCheckTypeID.toString().replaceAll("\\[|\\]", "");
		String check_date = listCheckDate.toString().replaceAll("\\[|\\]", "");
		String account_no = listAccountNo.toString().replaceAll("\\[|\\]", "");
		String bank_id = listBankID.toString().replaceAll("\\[|\\]", "");
		String bank_branch_id = listBankBranchID.toString().replaceAll("\\[|\\]", "");
		String receipt_no = listReceiptNo.toString().replaceAll("\\[|\\]", "");
		String credit = listCredit.toString().replaceAll("\\[|\\]", "");

		String SQL = "SELECT sp_ir_post_cash_return_v2('"+ entity_id +"', "+ total_amount +", \n" + 
				"     ARRAY["+ part_id +"]::VARCHAR[], \n" + 
				"     ARRAY["+ amount +"]::NUMERIC[], \n" + 
				"     ARRAY["+ pymnttype +"]::VARCHAR[], \n" + 
				"     ARRAY["+ (check_no.equals("'null'") ? "null":check_no) +"]::VARCHAR[], \n" + 
				"     ARRAY["+ (check_type.equals("'null'") ? "null":check_type) +"]::VARCHAR[], \n" + 
				"     ARRAY["+ (check_date.equals("'null'") ? "null":check_date) +"]::TIMESTAMP[],\n" + 
				"     ARRAY["+ (account_no.equals("'null'") ? "null":account_no) +"]::VARCHAR[], \n" + 
				"     ARRAY["+ (bank_id.equals("'null'") ? "null":bank_id) +"]::VARCHAR[], \n" + 
				"     ARRAY["+ (bank_branch_id.equals("'null'") ? "null":bank_branch_id) +"]::VARCHAR[], \n" + 
				"     ARRAY["+ (receipt_no.equals("'null'") ? "null":receipt_no) +"]::VARCHAR[], \n" + 
				"     NULL, \n" + 
				"     ARRAY["+ credit +"]::BOOLEAN[], \n" + 
				/* Modified by Mann2x; Date Modified: February 9, 2017; Modified so that the branch will 
				 * be determined depending on which server the user logs in to.
				"     '"+ co_id +"', '"+ UserInfo.Branch +"', '"+ UserInfo.EmployeeCode +"');";
				*/
				"     '"+remarks+"', '"+ co_id +"', '"+ UserInfo.Branch +"', '"+ UserInfo.EmployeeCode +"');";

		FncSystem.out("Issuance of Receipt", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL, "Save", true);
		if(db.isNotNull()){
			return (Boolean) db.getResult()[0][0];
		}else{
			return true;
		}
	}
	
	public static Boolean saveCashReturn_v2(modelIssuanceCashReturn model, /*String client_seqno, */String entity_id, BigDecimal total_amount, String co_id) {
		ArrayList<String> listPartID = new ArrayList<String>();
		ArrayList<BigDecimal> listAmount = new ArrayList<BigDecimal>();
		ArrayList<String> listPymntTypeID = new ArrayList<String>();
		ArrayList<String> listCheckNo = new ArrayList<String>();
		ArrayList<String> listCheckTypeID = new ArrayList<String>();
		ArrayList<String> listCheckDate = new ArrayList<String>();
		ArrayList<String> listAccountNo = new ArrayList<String>();
		ArrayList<String> listBankID = new ArrayList<String>();
		ArrayList<String> listBankBranchID = new ArrayList<String>();
		ArrayList<String> listReceiptNo = new ArrayList<String>();
		ArrayList<Boolean> listCredit = new ArrayList<Boolean>();
		ArrayList<String> listReferenceNo = new ArrayList<String>();

		for(int x=0; x < model.getRowCount(); x++){
			String reference_no = (String) model.getValueAt(x, 0);
			String part_id = (String) model.getValueAt(x, 1);
			String check_no = (String) model.getValueAt(x, 5);
			String check_type = (String) model.getValueAt(x, 6);
			Date check_date = (Date) model.getValueAt(x, 8);
			String account_no = (String) model.getValueAt(x, 9);
			String bank_id = (String) model.getValueAt(x, 10);
			String bank_branch_id = (String) model.getValueAt(x, 12);
			String receipt_no = (String) model.getValueAt(x, 14);
			Boolean credit = (Boolean) model.getValueAt(x, 17);

			BigDecimal amount = new BigDecimal("0.00");
			
			try {
				amount = (BigDecimal) model.getValueAt(x, 3);
			} catch (ClassCastException e) {
				//amount = new BigDecimal((Long) model.getValueAt(x, 2));
				
				if(model.getValueAt(x, 3) instanceof Double){
					amount = BigDecimal.valueOf((Double) model.getValueAt(x, 3));
				}
				
				if(model.getValueAt(x, 3) instanceof Long){
					amount = BigDecimal.valueOf((Long) model.getValueAt(x, 3));
				}
				
			}

			if(part_id != null){
				listPartID.add(String.format("'%s'", part_id));
			}else{
				listPartID.add("null");
			}
			
			if(reference_no != null) {
				listReferenceNo.add(String.format("'%s'", reference_no));
			}else {
				listReferenceNo.add("null");
			}

			listAmount.add(amount);
			listPymntTypeID.add(String.format("'%s'", (String) model.getValueAt(x, 4)));

			if(check_no != null){
				listCheckNo.add(String.format("'%s'", check_no));
			}else{
				listCheckNo.add("null");
			}
			if(check_type != null){
				listCheckTypeID.add(String.format("'%s'", check_type));
			}else{
				listCheckTypeID.add("null");
			}
			if(check_date != null){
				listCheckDate.add(String.format("'%s'", check_date));
			}else{
				listCheckDate.add("null");
			}
			if(account_no != null){
				listAccountNo.add(String.format("'%s'", account_no));
			}else{
				listAccountNo.add("null");
			}
			if(bank_id != null){
				listBankID.add(String.format("'%s'", bank_id));
			}else{
				listBankID.add("null");
			}
			if(bank_branch_id != null){
				listBankBranchID.add(String.format("'%s'", bank_branch_id));
			}else{
				listBankBranchID.add("null");
			}
			if(receipt_no != null){
				listReceiptNo.add(String.format("'%s'", receipt_no));
			}else{
				listReceiptNo.add("null");
			}
			listCredit.add(credit);
		}

		String part_id = listPartID.toString().replaceAll("\\[|\\]", "");
		String amount = listAmount.toString().replaceAll("\\[|\\]", "");
		String pymnttype = listPymntTypeID.toString().replaceAll("\\[|\\]", "");
		String check_no = listCheckNo.toString().replaceAll("\\[|\\]", "");
		String check_type = listCheckTypeID.toString().replaceAll("\\[|\\]", "");
		String check_date = listCheckDate.toString().replaceAll("\\[|\\]", "");
		String account_no = listAccountNo.toString().replaceAll("\\[|\\]", "");
		String bank_id = listBankID.toString().replaceAll("\\[|\\]", "");
		String bank_branch_id = listBankBranchID.toString().replaceAll("\\[|\\]", "");
		String receipt_no = listReceiptNo.toString().replaceAll("\\[|\\]", "");
		String credit = listCredit.toString().replaceAll("\\[|\\]", "");
		String reference_no = listReferenceNo.toString().replaceAll("\\[|\\]", "");

		String SQL = "SELECT sp_ir_post_cash_return_v2('"+ entity_id +"', "+ total_amount +", \n" +
				"	  ARRAY["+reference_no+"]::VARCHAR[], \n"+
				"     ARRAY["+ part_id +"]::VARCHAR[], \n" + 
				"     ARRAY["+ amount +"]::NUMERIC[], \n" + 
				"     ARRAY["+ pymnttype +"]::VARCHAR[], \n" + 
				"     ARRAY["+ (check_no.equals("'null'") ? "null":check_no) +"]::VARCHAR[], \n" + 
				"     ARRAY["+ (check_type.equals("'null'") ? "null":check_type) +"]::VARCHAR[], \n" + 
				"     ARRAY["+ (check_date.equals("'null'") ? "null":check_date) +"]::TIMESTAMP[],\n" + 
				"     ARRAY["+ (account_no.equals("'null'") ? "null":account_no) +"]::VARCHAR[], \n" + 
				"     ARRAY["+ (bank_id.equals("'null'") ? "null":bank_id) +"]::VARCHAR[], \n" + 
				"     ARRAY["+ (bank_branch_id.equals("'null'") ? "null":bank_branch_id) +"]::VARCHAR[], \n" + 
				"     ARRAY["+ (receipt_no.equals("'null'") ? "null":receipt_no) +"]::VARCHAR[], \n" + 
				"     NULL, \n" + 
				"     ARRAY["+ credit +"]::BOOLEAN[], \n" + 
				/* Modified by Mann2x; Date Modified: February 9, 2017; Modified so that the branch will 
				 * be determined depending on which server the user logs in to.
				"     '"+ co_id +"', '"+ UserInfo.Branch +"', '"+ UserInfo.EmployeeCode +"');";
				*/
				"     '"+ co_id +"', '"+ UserInfo.Branch +"', '"+ UserInfo.EmployeeCode +"');";

		FncSystem.out("Issuance of Receipt", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL, "Save", true);
		if(db.isNotNull()){
			return (Boolean) db.getResult()[0][0];
		}else{
			return true;
		}
	}

	public static String getClientSeqNo(Boolean cash_return) {
		pgSelect db = new pgSelect();
		/* Modified by Mann2x; Date Modified: February 9, 2017; Modified so that the branch will 
		 * be determined depending on which server the user logs in to.
		db.select("SELECT get_new_client_seqno('"+ UserInfo.Branch +"', "+ cash_return +");");
		*/
		db.select("SELECT get_new_client_seqno('"+ UserInfo.Branch +"', "+ cash_return +");");
		return (String) db.getResult()[0][0];
	}

	public static BigDecimal totalPayments(DefaultTableModel modelMain, DefaultTableModel modelTotal) {
		BigDecimal amount = new BigDecimal(0.00);

		for(int x=0; x < modelMain.getRowCount(); x++){
			try {
				amount = amount.add(((BigDecimal) modelMain.getValueAt(x, 2)));
			} catch (NullPointerException e1) {
				amount = amount.add(new BigDecimal(0.00));
			} catch (ClassCastException e2) {
				//amount = amount.add(new BigDecimal((Long) modelMain.getValueAt(x, 2))); 
				
				
				if(modelMain.getValueAt(x, 2) instanceof Double){
					amount = amount.add(BigDecimal.valueOf((Double) modelMain.getValueAt(x, 2)));
				}
				
				if(modelMain.getValueAt(x, 2) instanceof Long){
					amount = amount.add(BigDecimal.valueOf((Long) modelMain.getValueAt(x, 2)));
				}
			}
		}
		modelTotal.setValueAt(amount, 0, 2);

		return amount;
	}
	
	public static BigDecimal totalCashReturn(DefaultTableModel modelMain, DefaultTableModel modelTotal) {
		BigDecimal amount = new BigDecimal(0.00);

		for(int x=0; x < modelMain.getRowCount(); x++){
			try {
				amount = amount.add(((BigDecimal) modelMain.getValueAt(x, 3)));
			} catch (NullPointerException e1) {
				amount = amount.add(new BigDecimal(0.00));
			} catch (ClassCastException e2) {
				//amount = amount.add(new BigDecimal((Long) modelMain.getValueAt(x, 2))); 
				
				
				if(modelMain.getValueAt(x, 3) instanceof Double){
					amount = amount.add(BigDecimal.valueOf((Double) modelMain.getValueAt(x, 3)));
				}
				
				if(modelMain.getValueAt(x, 3) instanceof Long){
					amount = amount.add(BigDecimal.valueOf((Long) modelMain.getValueAt(x, 3)));
				}
			}
		}
		modelTotal.setValueAt(amount, 0, 3);

		return amount;
	}

	public static BigDecimal totalPercentage(String entity_id, String proj_id, String unit_id, Integer seq_no, String part_id, BigDecimal total_amount) {
		String SQL = "SELECT sp_compute_payment_percentage_temp('"+ entity_id +"', '"+ proj_id +"', getinteger('"+ unit_id +"')::VARCHAR, "+ seq_no +", '"+ unit_id +"', '"+ part_id +"', "+ total_amount +", '0', '"+ UserInfo.EmployeeCode +"');";

		pgSelect db = new pgSelect();
		//db.select("SELECT sp_compute_payment_percentage('"+ entity_id +"', '"+ proj_id +"', getinteger('"+ unit_id +"')::VARCHAR, "+ seq_no +");");
		db.select(SQL);
		System.out.printf("Total Percentage: %s%n", SQL);
		try {
			return (BigDecimal) db.getResult()[0][0];
		} catch (NullPointerException e) {
			return new BigDecimal(0);
		}
	}

	public static Object[] isOR(String client_seqno) {
		pgSelect db = new pgSelect();
		db.select("SELECT a.*, to_char(b.offresdate, 'YYYY-mm-dd')\n" + 
				"FROM rf_pay_header a\n" + 
				"LEFT JOIN rf_sold_unit b ON b.entity_id = a.entity_id AND b.projcode = a.proj_id AND b.pbl_id = a.pbl_id AND b.seq_no = a.seq_no\n" + 
				"WHERE a.client_seqno = '"+ client_seqno +"'\n" + 
				"AND COALESCE(a.new_reserved, 'N') != 'Y';");

		if(db.isNotNull()){
			return db.getResult()[0];
		}else{
			return null;
		}
	}

	public static Date getRealTransactionDate(Date trans_date) {
		pgSelect db = new pgSelect();
		db.select("SELECT sp_get_cutoff('"+ trans_date +"'::TIMESTAMP);");
		return (Date) db.Result[0][0];
	}

	public static Boolean returnToCSD(String client_seqno) {
		String SQL = "UPDATE rf_pay_header SET op_status = 'L' WHERE client_seqno = '"+ client_seqno +"';";

		try {
			pgUpdate db = new pgUpdate();
			db.executeUpdate(SQL, true);
			db.commit();
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public static void printAR(String client_seqno, String ar_no, String creditedAR_no, String recType, Double credited_amount, String co_id) {
		String company = co_id;
		
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("client_seqno", client_seqno);
		mapParameters.put("ar_no", ar_no);
		mapParameters.put("credit_ar_no", creditedAR_no);
		mapParameters.put("recpt_type", recType);
		mapParameters.put("credited_amount", credited_amount);
		mapParameters.put("prepared_by", UserInfo.Alias.toUpperCase());
		if (company.equals("01")) {
			FncReport.generateReport("/Reports/rptARReceipt_VDC.jasper", "Acknowledgement Receipt", String.format("AR No.: %s", ar_no), mapParameters);
		}else if(company.equals("02")) {
			FncReport.generateReport("/Reports/rptARReceipt_CDC.jasper", "Acknowledgement Receipt", String.format("AR No.: %s", ar_no), mapParameters);
		}else if(company.equals("04")) {
			FncReport.generateReport("/Reports/rptARReceipt_ADC.jasper", "Acknowledgement Receipt", String.format("AR No.: %s", ar_no), mapParameters);
		}else if(company.equals("05")) {
			FncReport.generateReport("/Reports/rptARReceipt_EDC.jasper", "Acknowledgement Receipt", String.format("AR No.: %s", ar_no), mapParameters);
		}
		//FncReport.generateReport("/Reports/rptARReceipt_2.jasper", "Acknowledgement Receipt", String.format("AR No.: %s", ar_no), mapParameters);
	}

	public static void printOR(String client_seqno, String or_no, String creditedAR_no, String recType, Double credited_amount) {

		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("client_seqno", client_seqno);
		mapParameters.put("or_no", or_no);
		mapParameters.put("credit_ar_no", creditedAR_no);
		mapParameters.put("recpt_type", recType);
		mapParameters.put("credited_amount", credited_amount);
		mapParameters.put("prepared_by", UserInfo.Alias.toUpperCase());
		
		if(UserInfo.Branch.equals("10")) {
			FncReport.generateReport("/Reports/rptORReceipt_v2.jasper", "Official Receipt", String.format("OR No.: %s", or_no), mapParameters);
		}else {
			FncReport.generateReport("/Reports/rptORReceipt.jasper", "Official Receipt", String.format("OR No.: %s", or_no), mapParameters);
		}
		
		//FncReport.generateReport("/Reports/rptORReceipt.jasper", "Official Receipt", String.format("OR No.: %s", or_no), mapParameters);
	}

	public static void printPR(String client_seqno, String pagibig_or) {

		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("client_seqno", client_seqno);
		mapParameters.put("or_no", pagibig_or);
		mapParameters.put("prepared_by", UserInfo.Alias.toUpperCase());

		FncReport.generateReport("/Reports/rptPagIBIGReceipt.jasper", "PagIBIG Official Receipt", String.format("PagIBIG OR No.: %s", pagibig_or), mapParameters);
	}
	
	public static void printSIV(String client_seqno, String si_no, String creditedAR_no, String recType, Double credited_amount, String co_id) {
		String company = co_id;
		
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("client_seqno", client_seqno);
		mapParameters.put("si_no", si_no);
		mapParameters.put("credit_ar_no", creditedAR_no);
		mapParameters.put("recpt_type", recType);
		mapParameters.put("credited_amount", credited_amount);
		mapParameters.put("prepared_by", UserInfo.Alias.toUpperCase());
		if(co_id.equals("01")) {
			FncReport.generateReport("/Reports/rptSalesInvoice_VDC.jasper", "Sales Invoice", String.format("SI No.: %s", si_no), mapParameters);
		}else if(co_id.equals("02")) {
			FncReport.generateReport("/Reports/rptSalesInvoice_VDC.jasper", "Sales Invoice", String.format("SI No.: %s", si_no), mapParameters);
		}
	}

	
	public static Object[] getNewPaymentDetails(String entity_id, String proj_id, String pbl_id, Integer seq_no) {
		String SQL = "SELECT TRIM(c.co_id), TRIM(c.company_name), TRIM(b.proj_id), TRIM(b.proj_name), TRIM(d.pbl_id), TRIM(d.description), TRIM(e.model_id), TRIM(e.model_desc), a.seq_no, a.sellingprice--a.*\n" + 
				"FROM rf_sold_unit a\n" + 
				"LEFT JOIN mf_project b ON b.proj_id = a.projcode\n" + 
				"LEFT JOIN mf_company c ON c.co_id = b.co_id\n" + 
				"LEFT JOIN mf_unit_info d ON d.proj_id = a.projcode AND d.pbl_id = a.pbl_id\n" + 
				"LEFT JOIN mf_product_model e ON e.model_id = a.model_id\n" + 
				"WHERE a.entity_id = '"+ entity_id +"' AND a.projcode = '"+ proj_id +"' AND a.pbl_id = '"+ pbl_id +"' AND a.seq_no = "+ seq_no +";";

		pgSelect db = new pgSelect();
		db.select(SQL);
		
		if(db.isNotNull()){
			return db.getResult()[0];
		}else{
			return null;
		}
	}

}
