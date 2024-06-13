package Buyers.ClientServicing;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.table.DefaultTableModel;

import Database.pgSelect;
import tablemodel.modelOrderOfPayments_CreditPayments;
import tablemodel.modelOrderOfPayments_NewPayments;
import tablemodel.modelOrderOfPayments_PaymentDetails;
import Functions.FncGlobal;
import Functions.FncSystem;
import Functions.UserInfo;

public class _OrderOfPayment {

	public _OrderOfPayment() {

	}

	public static String getReservation() {
		String SQL = 
				"select a.client_seqno as \"Client Seq. No\",\n" + 
						"trim(a.entity_id) as \"Client ID\", " +
						"trim(b.entity_name) as \"Client Name\",\n" + 
						"a.proj_id as \"Project ID\", " +
						"trim(c.proj_name) as \"Project Name\",\n" + 
						"d.unit_id as \"Unit ID\", " +
						"trim(d.description) as \"Unit Description\", " +
						"a.seq_no::int as \"Sequence No.\", \n" + 
						"a.model_id as \"Model ID\", trim(e.model_desc) as \"Model Name\",\n" + 
						"a.selling_price as \"Selling Price\", COALESCE(a.new_reserved, 'n') = 'Y' as \"New Reserved\", \n" +
						"a.pbl_id as \"PBL ID\" \n" + 
						"from rf_pay_header a\n" + 
						"inner join rf_entity b on b.entity_id = a.entity_id\n" + 
						"left join mf_project c on c.proj_id = a.proj_id\n" + 
						"inner join mf_unit_info d on coalesce(d.proj_id, '') = coalesce(a.proj_id, '') and d.pbl_id = a.pbl_id\n" + 
						"inner join mf_product_model e on e.model_id = a.model_id and coalesce(e.server_id, '') = COALESCE(d.server_id, '') and coalesce(e.proj_server, '') = coalesce(d.proj_server, '') \n" + 
						"where a.trans_date::date = now()::date\n" + 
						//"and a.new_reserved = 'Y'\n" + 
						"and a.op_status != 'P'\n" +
						"and a.total_amt_paid is null;";
		System.out.println(SQL);
		return SQL;
	}

	public static Object[] getNewPaymentsData(String entity_id, String proj_id, String pbl_id, Integer seq_no) {
		String SQL = "SELECT TRIM(a.entity_id) as client_id, \n"+ 
				"(Case when a.projcode = '015' and a.pbl_id = '2424' THEN 'SAMANTE, VANESSA ANNE TEODORO' ELSE get_client_name(a.entity_id) END) as client_name, \n" + //MODIFIED BY MONIQUE DTD 2023-03-09; REFER TO DCRF#2500
				"TRIM(a.projcode) as project_id, \n" + 
				"get_project_name(a.projcode) as project_name, TRIM(b.unit_id) as unit_id, TRIM(b.description) as unit_description, \n" + 
				"a.seq_no as sequence_no, TRIM(a.model_id) as model_id, get_model_name(a.model_id) as model_name, \n" + 
				"a.sellingprice as selling_price, (offresdATE IS NULL) as new_reserved, \n" +
				"(select y.lot FROM hs_sold_other_lots x LEFT JOIN mf_unit_info y on y.proj_id = x.proj_id and y.pbl_id = x.oth_pbl_id \n" +
				"where x.entity_id = a.entity_id and x.proj_id = a.projcode and x.pbl_id = a.pbl_id and x.seq_no::int = a.seq_no::int and x.status_id = 'A') as other_unit \n" + 
				"FROM rf_sold_unit a\n" + 
				"LEFT JOIN mf_unit_info b ON b.proj_id = a.projcode AND b.pbl_id = a.pbl_id \n" + 
				"WHERE TRIM(a.entity_id) = '"+  entity_id+"' AND TRIM(a.projcode) = '"+ proj_id +"' AND TRIM(a.pbl_id) = '"+ pbl_id +"' AND a.seq_no = "+ seq_no +" \n" + 
				"ORDER BY get_client_name(a.entity_id), getinteger(b.phase), gettext(b.phase), getinteger(b.block), gettext(b.block), getinteger(b.lot), gettext(b.lot);";

		pgSelect db = new pgSelect();
		db.select(SQL);
		if(db.isNotNull()){
			return db.getResult()[0];
		}else{
			return null;
		}
	}
	public static Object[] getNewPaymentsData_itsreal(String entity_id, String proj_id, String pbl_id, Integer seq_no) {
		String SQL = "SELECT TRIM(a.entity_id) as client_id, \n" +
				" get_client_name(a.entity_id) as client_name, \n" +
//				"(Case when a.projcode = '019' and a.pbl_id = '10661' THEN 'ESON, ROWELYN ANTARAN' ELSE get_client_name(a.entity_id) END) as client_name, \n" + //MODIFIED BY MONIQUE DTD 2023-03-09; REFER TO DCRF#2500 -- Commented by Monique dtd 03-20-2024; With Req. of Change Civil Status
				" TRIM(a.projcode) as project_id, \n" + 
				"get_project_name(a.projcode) as project_name, TRIM(b.unit_id) as unit_id, TRIM(b.description) as unit_description, \n" + 
				"a.seq_no as sequence_no, TRIM(a.model_id) as model_id, get_model_name_itsreal(a.model_id, a.projcode) as model_name, \n" + 
				"a.sellingprice as selling_price, (offresdATE IS NULL) as new_reserved, \n" +
				"(select y.lot FROM hs_sold_other_lots x LEFT JOIN mf_unit_info y on y.proj_id = x.proj_id and y.pbl_id = x.oth_pbl_id \n" +
				"where x.entity_id = a.entity_id and x.proj_id = a.projcode and x.pbl_id = a.pbl_id and x.seq_no::int = a.seq_no::int and x.status_id = 'A') as other_unit \n" + 
				"FROM rf_sold_unit a\n" + 
				"LEFT JOIN mf_unit_info b ON b.proj_id = a.projcode AND TRIM(b.pbl_id) = a.pbl_id  and b.server_id is not null\n" + 
				"WHERE TRIM(a.entity_id) = '"+  entity_id+"' AND TRIM(a.projcode) = '"+ proj_id +"' AND TRIM(a.pbl_id) = '"+ pbl_id +"' AND a.seq_no = "+ seq_no +" and a.server_id is not null \n" + 
				"ORDER BY get_client_name(a.entity_id), getinteger(b.phase), gettext(b.phase), getinteger(b.block), gettext(b.block), getinteger(b.lot), gettext(b.lot);";

		pgSelect db = new pgSelect();
		db.select(SQL);
		if(db.isNotNull()){
			return db.getResult()[0];
		}else{
			return null;
		}
	}

	public static String getBank() {
		
		String SQL = "SELECT trim(bank_id) as \"ID\", trim(bank_name) as \"Name\", trim(bank_alias) as \"Alias\" FROM mf_bank WHERE status_id = 'A' "
				+ " and proj_server is null and server_id is null ORDER BY bank_name;"; //ADDED BY MONIQUE DTD 2022-09-21; FOR LIST OF BANKS (JSYSTEM) ONLY
		return SQL;
	}

	public static String getBankBranch(String bank_id) {
		String SQL = "SELECT trim(bank_branch_id) as \"ID\", trim(bank_branch_location) as \"Name\" FROM mf_bank_branch WHERE bank_id = '"+ bank_id +"' AND status_id = 'A' ORDER BY bank_branch_location;";
		return SQL;
	}

	public static String getParticulars(String entity_id, String proj_id, String pbl_id, String seq_no, Boolean isTR, String recType) {
		//recType = "01";	
		if(isTR){
			String SQL = 
					"SELECT TRIM(c_part_id)::VARCHAR as \"ID\", TRIM(c_part_desc) as \"Name\", TRIM(c_part_alias) as \"Alias\", c_apply_ledger as \"Apply Ledger\", c_or_receipt as \"OR Receipt\", TRIM(c_receipt_id)::VARCHAR as \"Receipt ID\",\n" + 
							"TRIM(c_receipt_type) as \"Receipt Type\", TRIM(c_receipt_alias)::VARCHAR as \"Receipt Alias\"\n" +
							"FROM view_particulars('"+ entity_id +"', '"+ proj_id +"', " +
							"getinteger('"+ pbl_id +"')::VARCHAR, "+ (seq_no.equals("") ? "null":seq_no) +")\n" +
							"WHERE c_part_id = '106' \n" ;
			if (recType.equals("")){}
			else {SQL = SQL + "AND c_receipt_type = '"+recType+"' ";}					
			return SQL;
		}else{
			String SQL = "SELECT TRIM(c_part_id)::VARCHAR as \"ID\", TRIM(c_part_desc) as \"Name\", TRIM(c_part_alias) as \"Alias\", c_apply_ledger as \"Apply Ledger\", c_or_receipt as \"OR Receipt\", TRIM(c_receipt_id)::VARCHAR as \"Receipt ID\",\n" + 
					"  TRIM(c_receipt_type) as \"Receipt Type\", TRIM(c_receipt_alias)::VARCHAR as \"Receipt Alias\"\n" +
					"FROM view_particulars('"+ entity_id +"', '"+ proj_id +"', getinteger('"+ pbl_id +"')::VARCHAR, " +
					""+ (seq_no.equals("") ? "null":seq_no) +") \n" ;
			if (recType.equals("") || recType.equals(null)){}
			else {SQL = SQL + "/*WHERE c_receipt_type = '"+recType+"' */";}	
			System.out.printf("Display value of SQL: %s%n", SQL);

			return SQL;
		}
	}
	

	public static String getCheckType() {
		String SQL = "SELECT check_id as \"ID\", TRIM(check_desc) as \"Description\" FROM mf_check_type;";
		return SQL;
	}

	public static Boolean displayHoldingAsReservation(modelOrderOfPayments_PaymentDetails model, String client_seqno) {
		model.clear();

		String SQL = "SELECT '106' as part_id, 'RESERVATION' as part_desc, b.amount, case when bank is null then 'CASH' else 'CHECK' end as pmt_type, b.check_no,\n" + 
				"/*b.check_type*/null as check_type_id, /*trim(d.check_desc)*/null as check_type_desc, b.check_date, b.acct_no, trim(b.bank) as bank_id, trim(e.bank_name) as bank_name,\n" + 
				"trim(b.branch) as branch_id, trim(f.branch_name) as branch_name, trim(b.receipt_no) as receipt_no, trim(b.receipt_type) as receipt_id, trim(g.doc_alias) as receipt_type,\n" + 
				"null as brstn, true as credit\n" + 
				"\n" + 
				"FROM rf_pay_header a\n" + 
				"--INNER JOIN rf_pay_detail b ON b.client_seqno = a.client_seqno AND b.entity_id = a.entity_id\n" + 
				"LEFT JOIN rf_tra_header h ON h.entity_id = a.entity_id and h.proj_id =a.proj_id and h.pbl_id = a.pbl_id --and h.seq_no = a.seq_no\n" + 
				"LEFT JOIN rf_tra_detail b ON b.header_id = h.tra_header_id\n" + 
				"LEFT JOIN mf_pay_particular c ON c.pay_part_id = b.part_id\n" + 
				"--LEFT JOIN mf_check_type d ON d.check_id = b.check_type\n" + 
				"LEFT JOIN mf_bank e ON e.bank_id = b.bank\n" + 
				"LEFT JOIN mf_office_branch f ON f.branch_id = b.branch\n" + 
				"LEFT JOIN mf_doc_details g ON g.doc_id = b.receipt_type\n" + 
				"\n" + 
				"WHERE a.entity_id = (SELECT entity_id FROM rf_pay_header WHERE client_seqno = '"+ client_seqno +"')\n" + 
				"AND a.proj_id = (SELECT proj_id FROM rf_pay_header WHERE client_seqno = '"+ client_seqno +"')\n" + 
				"AND a.pbl_id = (SELECT pbl_id FROM rf_pay_header WHERE client_seqno = '"+ client_seqno +"')\n" + 
				"AND a.status_id = 'A'\n" + 
				"AND h.tran_type != '1' --AND h.status_id != 'I'\n" + 
				"AND NOT EXISTS(SELECT * FROM rf_pay_detail WHERE client_seqno = a.client_seqno)\n" + 
				"AND b.part_id = '168';";

		FncSystem.out("Holding Payments", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
			}
		}

		return true;
	}

	public static Boolean saveReservation(modelOrderOfPayments_PaymentDetails model, String client_seqno, BigDecimal totalAmount, Boolean isTR, Boolean credit_itsreal) {
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
		ArrayList<String> listReceiptID = new ArrayList<String>();
		ArrayList<String> listBRSTN = new ArrayList<String>();
		/*	Added by Mann2x; Date Added: June 06, 2017;	*/
		ArrayList<String> listpayRecID = new ArrayList<String>();

		for(int x=0; x < model.getRowCount(); x++){
			String check_no = (String) model.getValueAt(x, 4);
			String check_type = (String) model.getValueAt(x, 5);
			Date check_date = (Date) model.getValueAt(x, 7);
			String account_no = (String) model.getValueAt(x, 8);
			String bank_id = (String) model.getValueAt(x, 9);
			String bank_branch_id = (String) model.getValueAt(x, 11);
			String receipt_no = (String) model.getValueAt(x, 13);
			String receipt_id = (String) model.getValueAt(x, 14);
			String brstn = (String) model.getValueAt(x, 17);

			String payrecid = "";

			try {
				payrecid = (String) model.getValueAt(x, 19).toString();
			} catch (NullPointerException ex) {
				payrecid = "null";
			}

			listPartID.add(String.format("'%s'", (String) model.getValueAt(x, 0)));
			listAmount.add((BigDecimal) model.getValueAt(x, 2));
			listPymntTypeID.add(String.format("'%s'", (String) model.getValueAt(x, 3)));

			if(check_no != null){listCheckNo.add(String.format("'%s'", check_no));}
			else{listCheckNo.add("null");}

			if(check_type != null){listCheckTypeID.add(String.format("'%s'", check_type));}
			else{listCheckTypeID.add("null");}

			if(check_date != null){listCheckDate.add(String.format("'%s'", check_date));}
			else{listCheckDate.add("null");}

			if(account_no != null){listAccountNo.add(String.format("'%s'", account_no));}
			else{listAccountNo.add("null");}

			if(bank_id != null){listBankID.add(String.format("'%s'", bank_id));}
			else{listBankID.add("null");}

			if(bank_branch_id != null){listBankBranchID.add(String.format("'%s'", bank_branch_id));}
			else{listBankBranchID.add("null");}

			if(receipt_no != null){listReceiptNo.add(String.format("'%s'", receipt_no));}
			else{listReceiptNo.add("null");}

			if(receipt_id != null){listReceiptID.add(String.format("'%s'", receipt_id));}
			else{listReceiptID.add("null");}

			if(brstn != null){listBRSTN.add(String.format("'%s'", brstn));}
			else{listBRSTN.add("null");}

			if(payrecid != null){listpayRecID.add(String.format("'%s'", payrecid));}
			else{listpayRecID.add("null");}
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
		String receipt_id = listReceiptID.toString().replaceAll("\\[|\\]", "");
		String brstn = listBRSTN.toString().replaceAll("\\[|\\]", "");
		String payrecid = listpayRecID.toString().replaceAll("\\[|\\]", "");

		String SQL = "";
		if(isTR){
			/* Modified by Mann2x; Date Modified: February 9, 2017; Modified so that the branch will 
			 * be determined depending on which server the user logs in to.
			SQL = "SELECT sp_op_post_tr('"+ UserInfo.Branch +"', '"+ client_seqno +"', "+ totalAmount +",\n" +
			 */
			SQL = "SELECT sp_op_post_tr_v2('"+ UserInfo.Branch +"', '"+ client_seqno +"', "+ totalAmount +",\n" +
					"  ARRAY["+ part_id +"]::VARCHAR[],\n" + 
					"  ARRAY["+ amount +"]::NUMERIC[],\n" + 
					"  ARRAY["+ pymnttype +"]::VARCHAR[],\n" + 
					"  ARRAY["+ (check_type.equals("'null'") ? "null":check_type) +"]::VARCHAR[],\n" + 
					"  ARRAY["+ (check_no.equals("'null'") ? "null":check_no) +"]::VARCHAR[],\n" + 
					"  ARRAY["+ (check_date.equals("'null'") ? "null":check_date) +"]::TIMESTAMP[],\n" + 
					"  ARRAY["+ (account_no.equals("'null'") ? "null":account_no) +"]::VARCHAR[],\n" + 
					"  ARRAY["+ (bank_id.equals("'null'") ? "null":bank_id) +"]::VARCHAR[], \n" +
					"  ARRAY["+ (bank_branch_id.equals("'null'") ? "null":bank_branch_id) +"]::VARCHAR[], \n" +
					"  ARRAY["+ (receipt_no.equals("'null'") ? "null":receipt_no) +"]::VARCHAR[],\n" + 
					"  ARRAY["+ (receipt_id.equals("'null'") ? "null":receipt_id) +"]::VARCHAR[],\n" + 
					"  ARRAY["+ (brstn.equals("'null'") ? "null":brstn) +"]::VARCHAR[],\n" + 
					"  '"+ UserInfo.EmployeeCode +"', \n" +
					"  ARRAY["+ (payrecid.equals("'null'") ? "null":payrecid) +"]::VARCHAR[], "+credit_itsreal+");";
		}
		FncSystem.out("SQL", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			return (Boolean) db.getResult()[0][0];
		}else{
			return false;
		}
	}

	public static Object [] saveNewPayment(modelOrderOfPayments_PaymentDetails model, String entity_id, String proj_id, String unit_id, String seq_no, BigDecimal totalAmount, Boolean credit_itsreal) {

		String a = "";
		String b = "";
		Object op_search_result[] = null;
		String receiptTypeID [] = {"01","03","08", "307"};	
		int w = 0;
		/*System.out.println("EntityID: " + entity_id);
		System.out.println("proj_id: " + proj_id);
		System.out.println("unit_id: " + unit_id);
		System.out.println("seq_no: " + seq_no);
		System.out.println("totalAmount: " + totalAmount);*/

		while (w<=3)

		{	
			
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
			ArrayList<String> listBRSTN = new ArrayList<String>();
			ArrayList<Boolean> listCredit = new ArrayList<Boolean>();
			ArrayList<String> listLot = new ArrayList<String>();
			ArrayList<String> listDueType = new ArrayList<String>();

			int y = 0;
			Double amount_tot = 0.00;

			for (int x=0; x < model.getRowCount(); x++) {

				String receiptType_ID = (String) model.getValueAt(x, 14);				
				/*System.out.println("receiptType_ID: " + receiptType_ID);
				 */
				if (receiptTypeID[w].equals(receiptType_ID)) {		
					System.out.println();
					amount_tot	= amount_tot + Double.parseDouble(model.getValueAt(x,2).toString());	
					String check_no = (String) model.getValueAt(x, 4);
					String check_type = (String) model.getValueAt(x, 5);
					Date check_date = (Date) model.getValueAt(x, 7);
					String account_no = (String) model.getValueAt(x, 8);
					String bank_id = (String) model.getValueAt(x, 9);
					String bank_branch_id = (String) model.getValueAt(x, 11);
					String receipt_no = (String) model.getValueAt(x, 13);
					String brstn = (String) model.getValueAt(x, 17);
					Boolean credit = (Boolean) model.getValueAt(x, 18);
					listPartID.add(String.format("'%s'", (String) model.getValueAt(x, 0)));
					listAmount.add((BigDecimal) model.getValueAt(x, 2));
					listPymntTypeID.add(String.format("'%s'", (String) model.getValueAt(x, 3)));
					String Lot = (String) model.getValueAt(x, 20);
					String due_type = (String) model.getValueAt(x, 21);

					if (check_no != null) {
						listCheckNo.add(String.format("'%s'", check_no));
					} else {
						listCheckNo.add("null");
					}

					if (check_type != null) {
						listCheckTypeID.add(String.format("'%s'", check_type));
					} else {
						listCheckTypeID.add("null");
					}

					if (check_date != null) {
						listCheckDate.add(String.format("'%s'", check_date));
					} else {
						listCheckDate.add("null");
					}

					if (account_no != null) {
						listAccountNo.add(String.format("'%s'", account_no));
					} else {
						listAccountNo.add("null");
					}

					if (bank_id != null) {
						listBankID.add(String.format("'%s'", bank_id));
					} else {
						listBankID.add("null");
					}

					if (bank_branch_id != null) {
						listBankBranchID.add(String.format("'%s'", bank_branch_id));
					} else {
						listBankBranchID.add("null");
					}

					if (receipt_no != null) {
						listReceiptNo.add(String.format("'%s'", receipt_no));
					} else {
						listReceiptNo.add("null");
					}

					if (brstn != null) {
						listBRSTN.add(String.format("'%s'", brstn));
					} else {
						listBRSTN.add("null");
					}

					if (Lot != null) {
						listLot.add(String.format("'%s'", Lot));
					} else {
						listLot.add("null");
					}

					if (due_type != null) {
						listDueType.add(String.format("'%s'", due_type));
					} else {
						listDueType.add("null");
					}

					y++;
				} else {

				}
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
			String brstn = listBRSTN.toString().replaceAll("\\[|\\]", "");
			String credit = listCredit.toString().replaceAll("\\[|\\]", "");
			String lot = listLot.toString().replaceAll("\\[|\\]", ""); 
			String duetype = listDueType.toString().replaceAll("\\[|\\]", "");

			if (y>0)
			{
				String SQL = "SELECT * from sp_op_post_new_v2(" +
						/* Modified by Mann2x; Date Modified: February 9, 2017; Modified so that the branch will 
						 * be determined depending on which server the user logs in to.
				"'"+ UserInfo.Branch +"', " +
						 */
						 "'"+ UserInfo.Branch +"', " +
						 "'"+ entity_id +"', " +
						 "'"+ proj_id +"', " +
						 "'"+ unit_id +"', " +
						 ""+ seq_no +", " +
						 //""+ totalAmount +",\n" + 
						 ""+ amount_tot +",\n" + //-changed by Del Gonzales 06/02/2016
						 "  ARRAY["+ part_id +"]::VARCHAR[],\n" + 
						 "  ARRAY["+ amount +"]::NUMERIC[],\n" + 
						 "  ARRAY["+ pymnttype +"]::VARCHAR[],\n" + 
						 "  ARRAY["+ (check_type.equals("'null'") ? "null":check_type) +"]::VARCHAR[],\n" + 
						 "  ARRAY["+ (check_no.equals("'null'") ? "null":check_no) +"]::VARCHAR[],\n" + 
						 "  ARRAY["+ (check_date.equals("'null'") ? "null":check_date) +"]::TIMESTAMP[],\n" + 
						 "  ARRAY["+ (account_no.equals("'null'") ? "null":account_no) +"]::VARCHAR[],\n" + 
						 "  ARRAY["+ (bank_id.equals("'null'") ? "null":bank_id) +"]::VARCHAR[],\n" + 
						 "  ARRAY["+ (bank_branch_id.equals("'null'") ? "null":bank_branch_id) +"]::VARCHAR[],\n" + 
						 "  ARRAY["+ (receipt_no.equals("'null'") ? "null":receipt_no) +"]::VARCHAR[],\n" + 
						 "  NULL, \n" + 
						 "  ARRAY["+ (brstn.equals("'null'") ? "null":brstn) +"]::VARCHAR[],\n" + 
						 "  ARRAY["+ credit +"]::BOOLEAN[], \n" +
						 "  ARRAY["+ lot +"]::VARCHAR[], \n" +
						 "  ARRAY["+ duetype +"]::VARCHAR[], \n" +
						 "  '"+ UserInfo.EmployeeCode +"', "+credit_itsreal+");";

				FncSystem.out("SQL", SQL);
				pgSelect db = new pgSelect();
				db.select(SQL);

				if(db.isNotNull()){
					op_search_result = db.getResult()[0];
				}else{
					op_search_result = null;
				}	

				a = db.getResult()[0][0].toString();
				b = b + ": " + db.getResult()[0][1].toString();
			}
			else 
			{}

			w++;
		}

		op_search_result[0] = a;
		op_search_result[1] = b;

		return op_search_result;
	}

	public static Boolean updateNewPayment(modelOrderOfPayments_PaymentDetails model, String client_seqno, BigDecimal totalAmount) {
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
		ArrayList<String> listBRSTN = new ArrayList<String>();

		for(int x=0; x < model.getRowCount(); x++){

			String check_no = (String) model.getValueAt(x, 4);
			String check_type = (String) model.getValueAt(x, 5);
			Date check_date = (Date) model.getValueAt(x, 7);
			String account_no = (String) model.getValueAt(x, 8);
			String bank_id = (String) model.getValueAt(x, 9);
			String bank_branch_id = (String) model.getValueAt(x, 11);
			String receipt_no = (String) model.getValueAt(x, 13);
			String brstn = (String) model.getValueAt(x, 17);

			listPartID.add(String.format("'%s'", (String) model.getValueAt(x, 0)));
			listAmount.add((BigDecimal) model.getValueAt(x, 2));
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
			if(brstn != null){
				listBRSTN.add(String.format("'%s'", brstn));
			}else{
				listBRSTN.add("null");
			}
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
		String brstn = listBRSTN.toString().replaceAll("\\[|\\]", "");

		/* Modified by Mann2x; Date Modified: February 9, 2017; Modified so that the branch will 
		 * be determined depending on which server the user logs in to.
		String SQL = "SELECT sp_op_update_new('"+ UserInfo.Branch +"', '"+ client_seqno +"', "+ totalAmount +", \n" +
		 */
		String SQL = "SELECT sp_op_update_new('"+ UserInfo.Branch +"', '"+ client_seqno +"', "+ totalAmount +", \n" +
				"  ARRAY["+ part_id +"]::VARCHAR[], \n" +
				"  ARRAY["+ amount +"]::NUMERIC[],\n" + 
				"  ARRAY["+ pymnttype +"]::VARCHAR[], \n" +
				"  ARRAY["+ (check_type.equals("'null'") ? "null":check_type) +"]::VARCHAR[], \n" +
				"  ARRAY["+ (check_no.equals("'null'") ? "null":check_no) +"]::VARCHAR[], \n" +
				"  ARRAY["+ (check_date.equals("'null'") ? "null":check_date) +"]::TIMESTAMP[],\n" + 
				"  ARRAY["+ (account_no.equals("'null'") ? "null":account_no) +"]::VARCHAR[], \n" +
				"  ARRAY["+ (bank_id.equals("'null'") ? "null":bank_id) +"]::VARCHAR[], \n" +
				"  ARRAY["+ (bank_branch_id.equals("'null'") ? "null":bank_branch_id) +"]::VARCHAR[], \n" +
				"  ARRAY["+ (receipt_no.equals("'null'") ? "null":receipt_no) +"]::VARCHAR[], \n" + 
				"  NULL, \n" +
				"  ARRAY["+ (brstn.equals("'null'") ? "null":brstn) +"]::VARCHAR[], \n" + 
				"  NULL, '"+ UserInfo.EmployeeCode +"');";

		FncSystem.out("SQL", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			return (Boolean) db.getResult()[0][0];			
		}else{
			return false;
		}
	}

	public static void displayNewPayments(modelOrderOfPayments_NewPayments model) {
		model.clear();

		String SQL = 
				"SELECT * from ( \n" +
						"SELECT a.client_seqno as \"Sequence\", " +
						"a.entity_id as \"Client ID\", " +
						"get_client_name(a.entity_id) as \"Client\", " +
						"a.unit_id as \"Unit ID\", " +
						"get_unit_description(a.unit_id) as \"Unit\", \n" + 
						"a.total_amt_paid as \"Amount\", " +
						"a.rs_time_out as \"Time-In\", " +
						"get_status_description(a.op_status) as \"Status\", " +
						"get_employee_name(a.created_by) as \"Transact By\", \n" + 
						"a.proj_id as \"Project ID\", " +
						"get_project_name(a.proj_id) as \"Project\"\n" + 
						"FROM rf_pay_header a\n" + 
						"WHERE a.trans_date::date = now()::date\n" +
						"AND a.branch_id = '"+UserInfo.Branch+"' \n" +
						"AND a.total_amt_paid IS NOT NULL\n" + 
						"AND a.branch_id = '"+UserInfo.Branch+"' \n" +
						//"ORDER BY trans_date DESC;" +

			"UNION ALL\r\n" + 

			"\r\n" + 
			"SELECT a.client_seqno , \r\n" + 
			"a.entity_id , \r\n" + 
			"get_client_name(a.entity_id), \r\n" + 
			"a.unit_id , \r\n" + 
			"get_unit_description(a.unit_id), \r\n" + 
			"a.total_amt_paid , \r\n" + 
			"to_char(a.trans_date,'hh:mm:ss'), \r\n" + 
			"'FROM HOLDING', \r\n" + 
			"get_employee_name(a.op_user_id) , \r\n" + 
			"a.proj_id , \r\n" + 
			"get_project_name(a.proj_id) \r\n" + 
			"	FROM rf_tra_header a\r\n" + 
			"	WHERE a.trans_date::date = now()::date\r\n" + 
			"	AND a.total_amt_paid IS NOT NULL\r\n" + 
			"	AND a.branch_id = '"+UserInfo.Branch+"' \n" +
			"\r\n" + 
			" 	AND a.branch_id = '"+UserInfo.Branch+"' \n" +
			") a order by \"Sequence\" desc";

		pgSelect db = new pgSelect();
		db.select(SQL);
		FncSystem.out("Display SQL for Payments", SQL);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
			}
		}
	}

	/*	public static void displayPastPayments(modelOrderOfPayments_NewPayments model, String strDate) {
		model.clear();

		String SQL = "SELECT * from (SELECT a.client_seqno as Sequence, a.entity_id as ClientID, get_client_name(a.entity_id) as Client, \n" +
					"a.unit_id as Unit ID, get_unit_description(a.unit_id) as Unit, a.total_amt_paid as Amount, a.rs_time_out as Time-In, \n" +
					"get_status_description(a.op_status) as Status, get_employee_name(a.created_by) as Transact By, a.proj_id as Project ID, \n" +
					"get_project_name(a.proj_id) as Project \n" + 
					"FROM rf_pay_header a \n" + 
					"WHERE a.trans_date::date = '"+strDate+"'::date \n" +
					"AND a.total_amt_paid IS NOT NULL\n" + 
					"ORDER BY trans_date DESC;" +
					"UNION ALL \n" + 
					"SELECT a.client_seqno, a.entity_id, get_client_name(a.entity_id), a.unit_id, get_unit_description(a.unit_id), \n" + 
					"a.total_amt_paid, to_char(a.trans_date,'hh:mm:ss'), 'FROM HOLDING', get_employee_name(a.op_user_id), a.proj_id, \n" + 
					"get_project_name(a.proj_id) \n" + 
					"FROM rf_tra_header a \n" + 
					"WHERE a.trans_date::date = '"+strDate+"'::date \n" + 
					"AND a.total_amt_paid IS NOT NULL) a order by Sequence desc";

		pgSelect db = new pgSelect();
		db.select(SQL);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
			}
		}
	}*/

	public static void displayPastPayments(modelOrderOfPayments_NewPayments model, Date tran_date) {
		model.clear();

		String SQL = 
				"SELECT * from ( \n" +
						"SELECT a.client_seqno as \"Sequence\", " +
						"a.entity_id as \"Client ID\", " +
						"get_client_name(a.entity_id) as \"Client\", " +
						"a.unit_id as \"Unit ID\", " +
						"get_unit_description(a.unit_id) as \"Unit\", \n" + 
						"a.total_amt_paid as \"Amount\", " +
						"a.rs_time_out as \"Time-In\", " +
						"get_status_description(a.op_status) as \"Status\", " +
						"get_employee_name(a.created_by) as \"Transact By\", \n" + 
						"a.proj_id as \"Project ID\", " +
						/*	Modified by Mann2x; Date Modified: January 17, 2017; As requested by Ma'am Malou, the project alias should instead be displayed;	*/
						/*	"get_project_name(a.proj_id) as \"Project\"\n" +	*/
						"get_project_alias(a.proj_id) as \"Project\"\n" + 
						"FROM rf_pay_header a\n" + 
						"WHERE a.trans_date::date = '"+tran_date+"' \n" +
						"AND a.total_amt_paid IS NOT NULL\n" +
						"AND a.branch_id = '"+UserInfo.Branch+"' \n" + 
						//"ORDER BY trans_date DESC;" +

			"UNION ALL\r\n" + 

			"\r\n" + 
			"SELECT a.client_seqno , \r\n" + 
			"a.entity_id , \r\n" + 
			"get_client_name(a.entity_id), \r\n" + 
			"a.unit_id , \r\n" + 
			"get_unit_description(a.unit_id), \r\n" + 
			"a.total_amt_paid , \r\n" + 
			"to_char(a.trans_date,'hh:mm:ss'), \r\n" + 
			"'FROM HOLDING', \r\n" + 
			"get_employee_name(a.op_user_id) , \r\n" + 
			"a.proj_id , \r\n" + 
			/*	Modified by Mann2x; Date Modified: January 17, 2017; As requested by Ma'am Malou, the project alias should instead be displayed;	*/
			/*	"get_project_name(a.proj_id) \r\n" +	*/ 
			"get_project_alias(a.proj_id) \r\n" +
			"	FROM rf_tra_header a\r\n" + 
			"   WHERE a.trans_date::date = '"+tran_date+"' \n" +
			"	AND a.total_amt_paid IS NOT NULL\r\n" + 
			"	AND a.branch_id = '"+UserInfo.Branch+"' \n" +
			"\r\n" + 

			") a order by \"Sequence\" desc";

		pgSelect db = new pgSelect();
		db.select(SQL);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
			}
		}
	}

	public static void totalPayments(DefaultTableModel modelMain, DefaultTableModel modelTotal) {
		BigDecimal amount = new BigDecimal(0.00);

		for(int x=0; x < modelMain.getRowCount(); x++){
			try {
				amount = amount.add(((BigDecimal) modelMain.getValueAt(x, 2)));
			} catch (NullPointerException e1) {
				amount = amount.add(new BigDecimal(0.00));
			}
		}
		modelTotal.setValueAt(amount, 0, 2);
	}

	public static Object[] getTransactionDetails(String client_seqno) {
		String SQL = 
				"SELECT TRIM(a.client_seqno), " +
						"TRIM(a.entity_id), " +
						"TRIM(b.entity_name), " +
						"TRIM(a.proj_id), " +
						"TRIM(c.proj_name), " +
						"TRIM(a.unit_id), " +
						"TRIM(d.description), " +
						"a.seq_no::INT, " +
						"TRIM(a.model_id), " +
						"TRIM(e.model_desc), " +
						"a.selling_price\n" + 
						"FROM rf_pay_header a\n" + 
						"LEFT JOIN rf_entity b ON b.entity_id = a.entity_id\n" + 
						"LEFT JOIN mf_project c ON c.proj_id = a.proj_id\n" + 
						"LEFT JOIN mf_unit_info d ON d.proj_id = a.proj_id AND d.pbl_id = a.pbl_id\n" + 
						"LEFT JOIN mf_product_model e ON e.model_id = a.model_id\n" + 
						"WHERE client_seqno = '"+ client_seqno +"';";

		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			return db.getResult()[0];
		}else{
			return null;
		}
	}

	public static void displayReturnedPaymentList(modelOrderOfPayments_PaymentDetails model, String client_seqno) {
		model.clear();

		String SQL = 
				"SELECT TRIM(a.part_type), " +
						"TRIM(b.partdesc), " +
						"a.amount, NULL, " +
						"TRIM(a.check_no), " +
						"TRIM(a.check_type), " +
						"TRIM(e.check_desc), " +
						"a.check_date, " +
						"TRIM(a.acct_no), " +
						"TRIM(a.bank),\n" + 
						"TRIM(c.bank_name), " +
						"TRIM(a.branch), " +
						"TRIM(d.bank_branch_location), " +
						"TRIM(a.ar_no), " +
						"TRIM(a.receipt_type), " +
						"f.doc_alias, \n" + 
						"null, " +
						"null," +
						"(case when ar_no is not null then true else false end), \r\n" +
						"null as pay_rec_id, a.pay_for_lot, a.due_type \n"+
						"FROM rf_pay_detail a\n" + 
						"LEFT JOIN mf_pay_particular b ON b.pay_part_id = a.part_type\n" + 
						"LEFT JOIN mf_bank c ON c.bank_id = a.bank\n" + 
						"LEFT JOIN mf_bank_branch d ON d.bank_branch_id = a.branch\n" + 
						"LEFT JOIN mf_check_type e ON e.check_id = a.check_type\n" +
						"LEFT JOIN (SELECT TRIM(doc_id) as doc_id, TRIM(doc_alias) as doc_alias \n" +
						"	FROM mf_doc_details WHERE doc_id IN ('01', '03', '08')) f on a.receipt_type = f.doc_id  " + 
						"WHERE client_seqno = '"+ client_seqno +"'\n" +
						"ORDER BY pay_detail_id;";

		pgSelect db = new pgSelect();
		db.select(SQL);
		FncSystem.out("SQL for Return PMT", SQL);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);

				if(db.getResult()[x][8] == null){
					model.setValueAt("CASH", x, 3);
				}else{
					model.setValueAt("CHECK", x, 3);
				}
			}
		}
	}

	/**
	 * added by Alvin Gonzales (2017-07-16)
	 * 
	 */
	public static String sqlCreditPayments(String client_seqno) {
		String SQL = "";
		if(client_seqno != null){
			SQL = "SELECT * FROM view_op_credit_v3('"+ client_seqno +"') ORDER BY \"Client Name\",\"Transaction Date\", \"Unit\" ;";
		}else{
			SQL = "SELECT * FROM view_op_credit_v2() ORDER BY \"Client Name\",\"Transaction Date\", \"Unit\";";
		}

		System.out.println("Particular Query");
		System.out.println("SQL: " + SQL);
		FncSystem.out("Credit", SQL);
		return SQL;
	}

	/**
	 * added by Alvin Gonzales (2017-07-16)
	 * 
	 */
	public static void displayCreditPayments(modelOrderOfPayments_CreditPayments model, String entity_id) {
		model.clear();
		String SQL = 
				"SELECT false as \"Select\", " +
						"TRIM(a.receipt_no) as \"Receipt No.\", " +
						"a.total_amt_paid as \"Amount\", " +
						"a.trans_date as \"Transaction Date\", " +
						"a.entity_id \n" + 
						"FROM rf_tra_header a\n" + 
						"LEFT JOIN mf_unit_info b ON b.proj_id = a.proj_id AND b.pbl_id = a.pbl_id\n" + 
						"WHERE a.tran_type = '1' AND a.forfeit_date IS NOT NULL AND a.credit_date IS NULL\n" + 
						"AND NOT EXISTS(SELECT * FROM rf_pay_header WHERE entity_id = a.entity_id AND proj_id = a.proj_id AND pbl_id = a.pbl_id AND seq_no = a.seq_no)\n" + 
						"AND NOT EXISTS(SELECT * FROM rf_sold_unit WHERE entity_id = a.entity_id AND projcode = a.proj_id AND pbl_id = a.pbl_id AND seq_no = a.seq_no)\n" + 
						"AND a.entity_id = '"+ entity_id +"'\n" + 
						"ORDER BY get_client_name(a.entity_id);";
		pgSelect db = new pgSelect();
		db.select(SQL);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
			}
		}
	}

	public static String sql_getEntityName(String entity_id) {

		String a = "";

		String SQL = 
				"select upper(entity_name) from rf_entity where entity_id = '"+entity_id+"'\r\n"  ;

		System.out.printf("SQL #1: sql_getEntityName", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			if((String) db.getResult()[0][0]==null||db.getResult()[0][0].equals("null")) {a = "";}
			else {a = (String) db.getResult()[0][0]; }

		}else{
			a = "";
		}

		return a;
	}



	//SQL
	public static Object [] getPmtDtls(String orNo, Date tran_date, String pay_rec_id) {

		String strSQL = 
				"\r\n" + 
						"select \r\n" + 
						"a.pymnt_type, \r\n" + 
						"a.bank_id, \r\n" + 
						"a.bank_branch_id,\r\n" + 
						"a.acct_no, \r\n" + 
						"a.check_no, \r\n" + 
						"a.check_date, \r\n" + 
						"a.brstn,   \r\n" + 
						"a.check_type,\r\n" + 
						"b.check_desc,\r\n" + 
						"c.bank_name,\r\n" + 
						"d.bank_branch_location\r\n" + 
						"from rf_payments a\r\n" + 
						"left join mf_check_type b on a.check_type = b.check_id\r\n" + 
						"left join mf_bank c on a.bank_id = c.bank_id\r\n" + 
						"left join mf_bank_branch d on a.bank_branch_id = d.bank_branch_id \r\n" + 
						"where a.or_no = '"+orNo+"' \r\n" + 
						"and a.trans_date::date = '"+tran_date+"'::date \r\n" + 
						"and a.pay_rec_id = '"+pay_rec_id+"' \n " +
						"and a.status_id != 'I'" ;

		System.out.printf("SQL #1 getDRFdetails: %s", strSQL);
		pgSelect db = new pgSelect();
		db.select(strSQL);		

		if(db.isNotNull()){
			return db.getResult()[0];
		}else{
			return null;
		}
	}

	public static Boolean CheckIfDated(String strCheckDate, String strDate) {
		Boolean blnDated = false;

		String SQL = "SELECT (CASE WHEN '"+strCheckDate+"'::date <= get_next_bank_day('"+strDate+"'::date) THEN true ELSE false END)";

		System.out.println("");
		System.out.println("CheckIfDated");
		System.out.println(SQL);

		pgSelect sqlExec = new pgSelect();
		sqlExec.select(SQL);

		if(sqlExec.isNotNull()){
			blnDated = (Boolean) sqlExec.getResult()[0][0];
		} 

		System.out.println("");
		System.out.println("is dated: " + blnDated);

		return blnDated;
	}

	public static Boolean withRemainingMoratorium(String strEntityID, String strProjID, String strPBL, String strSeqNo) {
		return FncGlobal.GetBoolean("select sp_with_remaining_moratorium('"+strEntityID+"', '"+strProjID+"', '"+strPBL+"', "+strSeqNo+"); "); 
	}

	public static Boolean withRemainingRegular(String strEntityID, String strProjID, String strPBL, String strSeqNo) {
		return FncGlobal.GetBoolean("select sp_with_remaining_regular('"+strEntityID+"', '"+strProjID+"', '"+strPBL+"', "+strSeqNo+"); "); 
	}
}