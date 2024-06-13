package Buyers.CreditandCollections;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JList;
import javax.swing.JOptionPane;

import Database.pgSelect;
import Functions.FncGlobal;
import Functions.FncSystem;
import tablemodel.model_CancelActive_PerBatch;
import tablemodel.model_Cancellation_Hold;
import tablemodel.model_Cancellation_Status;
import tablemodel.model_Recommend_Cancellation;



/**
 * @author Christian Paquibot
 *
 */

@SuppressWarnings("rawtypes")
public class _FCancellationProcessing {
	public  String printSQL;
	pgSelect db = new pgSelect();

	public _FCancellationProcessing() {
	}

	public String getRemarks(){
		return "select remark_id, remdesc from mf_remarks where remark_id in ('00232','00227','00034','00035','00036','00037','00039') and remtype = '02' order by remdesc";

	}

	public  String lookupProjects(String co_id ){

		return "----Query Project \n" +
				"SELECT proj_id as \" Project ID\", trim(proj_name) as \" Project Name\" from mf_project where status_id = 'A' and co_id = '"+co_id+"' order by proj_id";
	}

	public  String getBatchNo(){
		String SQL = "SELECT TRIM(TO_CHAR(COALESCE(MAX(batch_no)::int,0) + 1,'FM0000000000')) FROM rf_cancellation ";
		String batchno = "";
		pgSelect db = new pgSelect();

		db.select(SQL);

		if(db.isNotNull()){
			batchno = db.Result[0][0].toString();
		}

		return batchno;
	}

	public String getApprovedby(){ 

		return "SELECT emp_code AS \"Employee Code\",\n" + 
				"	trim(entity_name) AS \"Employee Name\"  \n" + 
				"	FROM em_employee a\n" + 
				"	LEFT JOIN rf_entity b ON a.entity_id = b.entity_id\n" + 
				"	WHERE upper(trim(emp_rank)) IN ('SSUP', 'AM', 'M', 'SAM', 'SM', 'SVP', 'AVP', 'EVP', 'VP','SP1', 'S') OR a.emp_code = '900876'\n" + 
				"	AND terminate_date IS NULL\n" + 
				"	ORDER BY entity_name";
	}

	/*public void forPosting(){
	String SQL = "";

	SQL = "UPDATE rf_cancellation\n" + 
			"	SET cancel_rec_id=?, \n" + 
			"		batch_no=?, \n" + 
			"		entity_id=?, \n" + 
			"		proj_id=?, \n" + 
			"		pbl_id=?, \n" + 
			"		seq_no=?, \n" + 
			"		unit_id=?, \n" +  
			"		part_id=?, \n" + 
			"		last_paid_date=?, \n" + 
			"		default_date=?, \n" + 
			"		due_date=?, \n" + 
			"		months_past_due=?, \n" + 
			"		days_past_due=?, \n" + 
			"		amount_due=?, \n" + 
			"		under_maceda_law=?, \n" + 
			"		reason=?, \n" + 
			"		remarks=?, \n" + 
			"		date_posted=?, \n" + 
			"		posted_by=?, \n" + 
			"		date_cancelled=?, \n" + 
			"		cancelled_by=?, \n" + 
			"		date_approved=?, \n" +  
			"		approved_by=?, \n" + 
			"		csv_id=?, \n" + 
			"		csv_amount=?, \n" + 
			"		csv_rplf_no=?, \n" +  
			"		csv_approved_by=?, \n" + 
			"		months_paid=?\n" + 
			"WHERE entity_id = \n" + 
			"AND pbl_id = \n" + 
			"AND seq_no = \n" + 
			"AND proj_id = \n" + 
			"AND status = 'A';\n" + 
			"";
}*/

	public void displayPerBatchRecommended(model_CancelActive_PerBatch modelPerBatchModel, JList rowHeader, String table_name, String proj_id, Integer cmbprocess) {

		modelPerBatchModel.clear();
		String SQL = "";

		SQL = "SELECT false,* FROM sp_generate_recom_cancellation('"+table_name+"','"+proj_id+"',"+cmbprocess+")";
		FncSystem.out("Client ", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){

			ArrayList<Object[]> listData = new ArrayList<Object[]>();

			for(int x=0; x<db.getRowCount(); x++){
				modelPerBatchModel.addRow(db.getResult()[x]);
				listData.add(db.getResult()[x]);
			}

			printSQL = SQL; 

		}else{
			JOptionPane.showMessageDialog(FncGlobal.homeMDI, "No Records to Show!", "Empty Resultset", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	public  String getIHFClient(String proj_id){
		return "SELECT \n" + 
				"a.entity_id, \n" +  													// 0
				"_get_client_name(a.entity_id) AS \"Client Name\", \n" +  				// 1
				"(CASE WHEN _get_cliaent_buyertype(a.entity_id,a.proj_id,a.pbl_id,a.seq_no) = '02' THEN 'IHF' ELSE 'PAG-IBIG' END )as buyertype,	\n" + //2
				"get_unit_description(a.proj_id,a.pbl_id) AS \"Unit Description\",\n" + // 3
				"get_project_name(a.proj_id) AS \"Project Name\",\n" +  				// 4
				"_get_client_telephoneno(a.entity_id) AS \"Telephone No.\",\n" +		// 5 
				"_get_client_mobileNo(a.entity_id) AS \"Mobile No.\",\n" +				// 6
				"trim(a.proj_id) AS \"Project ID\",\n" +  								// 7
				"trim(a.pbl_id) AS \"PBL ID\",\n" +  									// 8 
				"a.seq_no AS \"Seq No.\"\n" + 											// 9
				"FROM get_active_accounts() a \n"+ 
				"JOIN get_ihf_accounts() b ON a.pbl_id = b.pbl_id AND a.seq_no = b.seq_no \n" +
				"WHERE proj_id = '"+proj_id+"' \n"+
				"AND a.entity_id NOT IN (SELECT entity_id FROM rf_cancellation WHERE entity_id = a.entity_id AND proj_id = a.proj_id AND pbl_id = a.pbl_id AND seq_no = a.seq_no AND status_id = 'A' )\n" +
				"ORDER BY \"Client Name\",\"Unit Description\"";
	}

	public  String getHDMFClient(String proj_id){

		return "SELECT \n" + 
				"a.entity_id, \n" +  													// 0 
				"_get_client_name(a.entity_id) AS \"Client Name\", \n" +  				// 1
				"( CASE WHEN _get_client_buyertype(a.entity_id,a.proj_id,a.pbl_id,a.seq_no) = '02' THEN 'IHF' ELSE 'PAG-IBIG' END )as buyertype,	\n" + //2
				"get_unit_description(a.proj_id,a.pbl_id) AS \"Unit Description\",\n" + // 3
				"get_project_name(a.proj_id) AS \"Project Name\",\n" + 					// 4
				"_get_client_telephoneno(a.entity_id) AS \"Telephone No.\",\n" + 		// 5 
				"_get_client_mobileNo(a.entity_id) AS \"Mobile No.\",\n" +				// 6
				"trim(a.proj_id) AS \"Project ID\",\n" +  								// 7
				"trim(a.pbl_id) AS \"PBL ID\",\n" +  									// 8 
				"a.seq_no AS \"Seq No.\"\n" + 											// 9
				"FROM get_active_accounts() a \n"+ 
				"JOIN get_pagibig_accounts() b ON a.pbl_id = b.pbl_id AND a.seq_no = b.seq_no\n" +
				"WHERE proj_id = '"+proj_id+"' \n"+
				"AND a.entity_id NOT IN (SELECT entity_id FROM rf_cancellation WHERE entity_id = a.entity_id AND proj_id = a.proj_id AND pbl_id = a.pbl_id AND seq_no = a.seq_no AND status_id = 'A' )\n" +
				"ORDER BY \"Client Name\",\"Unit Description\"";
	}


	public  String getAllClient(String proj_id){

		return "SELECT  \n" + 
				"a.entity_id,\n" + 
				"_get_client_name(a.entity_id) AS \"Client Name\", \n" + 
				"( CASE WHEN _get_client_buyertype(a.entity_id,a.proj_id,a.pbl_id,a.seq_no) = '02' THEN 'IHF' ELSE 'PAG-IBIG' END )as buyertype,	\n" + 
				"get_unit_description(a.proj_id,a.pbl_id) AS \"Unit Description\", \n" + 
				"get_project_name(a.proj_id) AS \"Project Name\", \n" + 
				"_get_client_telephoneno(a.entity_id) AS \"Telephone No.\", \n" + 
				"_get_client_mobileNo(a.entity_id) AS \"Mobile No.\", \n" + 
				"trim(a.proj_id) AS \"Project ID\", \n" + 
				"trim(a.pbl_id) AS \"PBL ID\", \n" + 
				"a.seq_no AS \"Seq No.\" \n" + 
				"FROM get_active_accounts() a \n" + 
				"JOIN get_ihf_accounts() b ON a.pbl_id = b.pbl_id AND a.seq_no = b.seq_no \n" + 
				"WHERE proj_id = '"+proj_id+"' \n"+
				"AND a.entity_id NOT IN (SELECT entity_id FROM rf_cancellation WHERE entity_id = a.entity_id AND proj_id = a.proj_id AND pbl_id = a.pbl_id AND seq_no = a.seq_no AND status_id = 'A' )\n" +
				"\n" + 
				"UNION\n" + 
				"\n" + 
				"SELECT  \n" + 
				"a.entity_id, \n" + 
				"_get_client_name(a.entity_id) AS \"Client Name\", \n" + 
				"( CASE WHEN _get_client_buyertype(a.entity_id,a.proj_id,a.pbl_id,a.seq_no) = '02' THEN 'IHF' ELSE 'PAG-IBIG' END )as buyertype, \n" + 
				"get_unit_description(a.proj_id,a.pbl_id) AS \"Unit Description\", \n" + 
				"get_project_name(a.proj_id) AS \"Project Name\",\n" + 
				"_get_client_telephoneno(a.entity_id) AS \"Telephone No.\", \n" + 
				"_get_client_mobileNo(a.entity_id) AS \"Mobile No.\",\n" + 
				"trim(a.proj_id) AS \"Project ID\",	\n" +
				"trim(a.pbl_id) AS \"PBL ID\",\n" + 
				"a.seq_no AS \"Seq No.\" \n" + 
				"FROM get_active_accounts() a \n" + 
				"JOIN get_pagibig_accounts() b ON a.pbl_id = b.pbl_id AND a.seq_no = b.seq_no \n" + 
				"WHERE proj_id = '"+proj_id+"' \n"+
				"AND a.entity_id NOT IN (SELECT entity_id FROM rf_cancellation WHERE entity_id = a.entity_id AND proj_id = a.proj_id AND pbl_id = a.pbl_id AND seq_no = a.seq_no AND status_id = 'A' )\n" +
				"ORDER BY \"Client Name\",\"Unit Description\"";

	}

	public String displayPastDue(String _entity_id, String _proj_id, String _pbl_id, Integer _seq_no ,String table_name){

		String SQL = "";

		if (table_name.equals("All")) {

			SQL = "SELECT * FROM (\n" + 
					"SELECT days_past_due,\n" + 
					"months_past_due,  \n" + 
					"COALESCE(amount_due,0.00) AS amount_due,\n" + 
					"last_paid_date::DATE,\n" + 
					"default_date::DATE,\n" + 
					"_get_particular_alias(part_id) AS stage,\n" + 
					"_payments_made(entity_id,proj_id,pbl_id,seq_no),\n" + 
					"(select cancel_rec_id from rf_cancellation where pbl_id::int= '"+_pbl_id+"'::int and seq_no="+_seq_no+" and status_id = 'A') as cancel_id, \n" + 
					"part_id \n" + 
					"FROM ihf_due_accounts \n" + 
					"WHERE entity_id = '"+_entity_id+"' \n" +
					"AND proj_id = '"+_proj_id+"' \n" +
					"AND pbl_id = '"+_pbl_id+"'\n" +
					"AND seq_no = "+_seq_no+" \n" + 
					"AND date_updated = (select max(date_updated) from ihf_due_accounts)\n" + 
					"\n" + 
					"UNION\n" + 
					"\n" + 
					"SELECT days_past_due,\n" + 
					"months_past_due,  \n" + 
					"COALESCE(amount_due,0.00) AS amount_due,\n" + 
					"last_paid_date::DATE,\n" + 
					"default_date::DATE,\n" + 
					"_get_particular_alias(part_id) AS stage,\n" + 
					"_payments_made(entity_id,proj_id,pbl_id,seq_no),\n" + 
					"(select cancel_rec_id from rf_cancellation where pbl_id::int= '"+_pbl_id+"'::int and seq_no="+_seq_no+" and status_id = 'A') as cancel_id, \n" + 
					"part_id \n" + 
					"FROM pagibig_due_accounts \n" + 
					"WHERE entity_id = '"+_entity_id+"' \n" +
					"AND proj_id = '"+_proj_id+"' \n" +
					"AND pbl_id = '"+_pbl_id+"'\n" +
					"AND seq_no = "+_seq_no+" \n" + 
					"AND date_updated = (select max(date_updated) from pagibig_due_accounts)\n" + 
					"\n" + 
					") a\n" + 
					"\n" + 
					"UNION \n" + 
					"SELECT \n" + 
					"0 AS days_past_due,\n" + 
					"0 AS months_past_due,\n" + 
					"0 AS amount_due, \n" + 
					"_get_last_paiddate('"+_entity_id+"','"+_proj_id+"','"+_pbl_id+"',"+_seq_no+") as last_datepaid, \n" + 
					"null as default_date, \n" + 
					"_get_ledger_part_desc('"+_entity_id+"','"+_proj_id+"','"+_pbl_id+"',"+_seq_no+") as stage, \n" + 
					"_payments_made('"+_entity_id+"','"+_proj_id+"','"+_pbl_id+"',"+_seq_no+"), \n" +
					"NULL as cancel_id, \n" + 
					"NULL as part_id \n" + 
					"order by days_past_due DESC LIMIT 1 \n" + 
					"";

		}else{

			SQL  = "\n" + 
					"SELECT days_past_due,\n" + 
					"months_past_due,  \n" + 
					"COALESCE(amount_due,0.00) AS amount_due,\n" + 
					"last_paid_date::DATE,\n" + 
					"default_date::DATE,\n" + 
					"_get_particular_alias(part_id) AS stage,\n" + 
					"_payments_made(entity_id,proj_id,pbl_id,seq_no),\n" + 
					"(select cancel_rec_id from rf_cancellation where pbl_id::int= '"+_pbl_id+"'::int and seq_no="+_seq_no+" and status_id = 'A') as cancel_id, \n" + 
					"part_id \n" + 
					"FROM "+table_name+" \n" +
					"WHERE entity_id = '"+_entity_id+"' \n" +
					"AND proj_id = '"+_proj_id+"' \n" +
					"AND pbl_id = '"+_pbl_id+"'\n" +
					"AND seq_no = "+_seq_no+" \n" + 
					"AND date_updated = (select max(date_updated) from "+table_name+")  \n" +
					"UNION \n" + 
					"SELECT \n" + 
					"0 AS days_past_due,\n" + 
					"0 AS months_past_due,\n" + 
					"0 AS amount_due, \n" + 
					"_get_last_paiddate('"+_entity_id+"','"+_proj_id+"','"+_pbl_id+"',"+_seq_no+") as last_datepaid, \n" + 
					"null as default_date, \n" + 
					"_get_ledger_part_desc('"+_entity_id+"','"+_proj_id+"','"+_pbl_id+"',"+_seq_no+") as stage, \n" + 
					"_payments_made('"+_entity_id+"','"+_proj_id+"','"+_pbl_id+"',"+_seq_no+"), \n" +
					"NULL as cancel_id, \n" + 
					"NULL as part_id \n" + 
					"order by days_past_due DESC LIMIT 1 \n" + 
					"";

		}

		System.out.printf("SQL: %s%n%n", SQL);
		return SQL;

	}

	public static void displayCancellationHold(model_Cancellation_Hold modelCancelHold, JList rowHeader, String proj_id) {

		modelCancelHold.clear();

		String SQL = "";

		/*	SQL = " SELECT false,* FROM\n" + 
			"	(SELECT DISTINCT ON (a.unit_id)\n" + 
			"	get_unit_description_project(b.proj_id,b.pbl_id) AS unitpbl, \n" + 
			"	_get_client_name(a.entity_id) AS entity_name,\n" + 
			"	_get_client_contactno(a.entity_id) AS contact_no, \n" + 
			"	_get_house_model_alias(a.pbl_id) AS model,\n" + 
			"	a.sellingprice, \n" + 
			"	COALESCE(a.total_amt_paid, 0.00) AS total_amt_paid, \n" + 
			"	a.actual_date::DATE, \n" +
			"	a.hold_until::DATE AS valid_until,\n" + 
			"	'' AS reason, \n" + 
			"	'' AS remarks,\n" + 
			"	a.entity_id, \n" + 
			"	a.proj_id, \n" + 
			"	a.unit_id, \n" + 
			"	a.pbl_id, \n" +  
			"	a.seq_no,\n" + 
			"	a.tra_header_id\n" + 
			"	FROM rf_tra_header a\n" + 
			"	LEFT JOIN mf_unit_info b on a.pbl_id = b.pbl_id AND a.unit_id = b.unit_id\n" + 
			"	WHERE a.status_id = 'O'\n" + 
			"	AND TRIM(b.status_id) = 'O' \n" +
				( proj_id == null  ? "" : "	AND a.proj_id = '"+ proj_id +"'\n" ) +
			"	AND a.hold_until::date < current_date ) a\n" + 
			"	ORDER BY entity_name";*/

		SQL =   "SELECT\n" + 
				"false, \n" + 
				"get_unit_description_project(a.proj_id,a.pbl_id) AS unitpbl, \n" + 
				"_get_client_name(a.entity_id) AS entity_name,\n" + 
				"_get_client_contactno(a.entity_id) AS contact_no, \n" + 
				"_get_house_model_alias(a.pbl_id) AS model,\n" + 
				"d.sellingprice,\n" + 
				"COALESCE(a.total_amt_paid, 0.00) AS total_amt_paid, \n" + 
				"a.actual_date, \n" + 
				"(CASE WHEN a.tran_type = '3' THEN (SELECT MIN(hold_until) FROM rf_tra_header WHERE client_seqno = a.client_seqno AND status_id = 'I') ELSE a.hold_until END) AS valid_until,\n" + 
				"'' AS reason, \n" + 
				"'' AS remarks,\n" + 
				"a.entity_id, \n" + 
				"a.proj_id, \n" + 
				"a.unit_id, \n" + 
				"a.pbl_id, \n" + 
				"a.seq_no,\n" + 
				"a.tra_header_id\n" + 
				"FROM rf_tra_header a\n" + 
				"INNER JOIN rf_tra_detail b ON b.client_seqno = a.client_seqno\n" + 
				"INNER JOIN mf_project c ON c.proj_id = a.proj_id\n" + 
				"INNER JOIN mf_unit_info d ON d.proj_id = a.proj_id AND d.pbl_id = a.pbl_id\n" + 
				"INNER JOIN mf_product_model e ON e.model_id = a.model_id\n" + 
				"\n" + 
				"WHERE a.status_id != 'I'\n" + 
				"AND b.part_id = '168'\n" + 
				"AND NOT EXISTS(SELECT * FROM rf_buyer_status WHERE entity_id = a.entity_id AND proj_id = a.proj_id AND pbl_id = a.pbl_id AND seq_no = a.seq_no AND byrstatus_id = '17')\n" + 
				"AND NOT EXISTS(SELECT * FROM rf_pay_header WHERE entity_id = a.entity_id AND proj_id = a.proj_id AND pbl_id = a.pbl_id AND seq_no = a.seq_no AND new_reserved = 'Y' AND status_id = 'A')\n" + 
				"AND (CASE WHEN true THEN d.proj_id = '015' ELSE TRUE END)\n" + 
				"AND (CASE WHEN false THEN d.phase = '1' ELSE TRUE END)\n" + 
				"AND a.receipt_no IS NOT NULL\n" + 
				"and hold_until::date < current_date\n" + 
				"\n" + 
				"ORDER BY c.proj_name, get_client_name(a.entity_id), trim(d.phase), trim(d.block), trim(d.lot);";

		FncSystem.out("Client ", SQL);

		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			ArrayList<Object[]> listData = new ArrayList<Object[]>();
			for(int x=0; x<db.getRowCount(); x++){
				modelCancelHold.addRow(db.getResult()[x]);
				listData.add(db.getResult()[x]);
			}
		}else{

			JOptionPane.showMessageDialog(FncGlobal.homeMDI, "No Records to Show!", "Empty Resultset", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	public static void displayCancellationTag(model_Cancellation_Status modelCancelTag, JList rowHeader, String proj_id,Integer status) {
		modelCancelTag.clear();

		String SQL = "";

		/*if (status == 1 ) {

		SQL = "SELECT false,\n" + 
				"get_unit_description_project(a.proj_id,a.pbl_id) AS unit,\n" + 
				"get_client_name(a.entity_id) AS entity_name,\n" + 
				"a.date_cancelled::timestamp,\n" + 
				"coalesce(a.csv_amount,0.00) AS csv_amount,\n" + 
				"_get_cancel_status(b.csv_status_id) AS current_status,\n" + 
				"'' AS new_status,\n" + 
				"NULL::date AS status_date,\n" + 
				"'' AS remarks,\n" + 
				"'' AS status_id,\n" + 
				"a.csv_id,\n" + 
				"a.entity_id,\n" + 
				"a.proj_id,\n" + 
				"a.pbl_id,\n" + 
				"a.seq_no,\n" + 
				"a.unit_id,\n" + 
				"a.cancel_rec_id,\n" + 
				"(SELECT get_project_name('"+proj_id+"') || ' - "+proj_id+"') AS proj_name\n" + 
				"\n" + 
				"FROM\n" + 
				"\n" + 
				"(SELECT DISTINCT ON (unit_id) * FROM rf_cancellation WHERE proj_id='"+proj_id+"' ORDER BY unit_id,seq_no DESC,cancel_rec_id DESC) a \n" + 
				"LEFT JOIN\n" + 
				"-- csv\n" + 
				"(SELECT DISTINCT on (csv_id) * FROM rf_csv_status ORDER BY csv_id DESC) b\n" + 
				"ON b.csv_id = a.csv_id\n" + 
				"WHERE a.csv_id IS NOT NULL\n" + 
				"ORDER BY entity_name";
	}else{

		SQL = "SELECT false,\n" + 
				"get_unit_description_project(a.proj_id,a.pbl_id) AS unit,\n" + 
				"get_client_name(a.entity_id) AS entity_name,\n" + 
				"a.date_cancelled::timestamp,\n" + 
				"coalesce(a.csv_amount,0.00) AS csv_amount,\n" + 
				"_get_cancel_status(b.cancel_status_id) AS current_status,\n" + 
				"'' AS new_status,\n" + 
				"NULL::date AS status_date,\n" + 
				"'' AS remarks,\n" + 
				"'' AS status_id,\n" + 
				"a.csv_id,\n" + 
				"a.entity_id,\n" + 
				"a.proj_id,\n" + 
				"a.pbl_id,\n" + 
				"a.seq_no,\n" + 
				"a.unit_id,\n" + 
				"a.cancel_rec_id,\n" + 
				"(SELECT get_project_name('"+proj_id+"') || ' - "+proj_id+"') AS proj_name\n" + 
				"\n" + 
				"FROM\n" + 
				"\n" + 
				"(SELECT DISTINCT ON (unit_id) * FROM rf_cancellation WHERE proj_id='"+proj_id+"' ORDER BY unit_id,seq_no DESC,cancel_rec_id DESC) a \n" + 
				"LEFT JOIN\n" + 
				"-- cancelled\n" + 
				"(SELECT DISTINCT on (cancel_id) * FROM rf_cancelled_accts_status ORDER BY cancel_id DESC) b\n" + 
				"ON b.cancel_id = a.cancel_rec_id\n" + 
				"WHERE a.batch_no IS NOT NULL\n" + 
				"ORDER BY entity_name";

	}*/

		SQL = "SELECT false,* FROM sp_generate_cancellation_csv_status('"+proj_id+"',"+status+")";

		FncSystem.out("Client ", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			ArrayList<Object[]> listData = new ArrayList<Object[]>();
			for(int x=0; x<db.getRowCount(); x++){
				modelCancelTag.addRow(db.getResult()[x]);
				listData.add(db.getResult()[x]);
			}
		}else{

			JOptionPane.showMessageDialog(FncGlobal.homeMDI, "No Records to Show!", "Empty Resultset", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	public static void generateCancellationStatus(model_Cancellation_Status modelCancelTag, JList rowHeader, String proj_id) {
		String SQL;

		SQL = "SELECT * FROM sp_generate_cancellation_status('"+proj_id+"')";

		FncSystem.out("Client ", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			ArrayList<Object[]> listData = new ArrayList<Object[]>();
			for(int x=0; x<db.getRowCount(); x++){
				modelCancelTag.addRow(db.getResult()[x]);
				listData.add(db.getResult()[x]);
			}
		}else{

			JOptionPane.showMessageDialog(FncGlobal.homeMDI, "No Records to Show!", "Empty Resultset", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	public String dateFormat(Date date){
		String strdate = null;
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		if (!(date == null)) {
			strdate = df.format(date);
		}

		return strdate;
	}

	public String co_id(String projid) {
		db.select("SELECT co_id FROM mf_project WHERE proj_id = '"+projid+"' ");
		return db.Result[0][0].toString();
	} // co_id

	public  String rplfNo(String co_id) {
		db.select("select trim(to_char(coalesce(max(drf_no),'0')::int + 1,'000000000')) from rf_drf_main where co_id = '"+ co_id +"'");
		return db.Result[0][0].toString();
	} // rplfNo

	public String listBatch(){

		return "SELECT distinct on (batch_no) batch_no AS \"Batch No.\",\n" + 
				"get_client_name_emp_id(requested_by) AS \"Requested by\",\n" + 
				"date_requested AS \"Date Requested\" \n" + 
				"FROM rf_cancellation \n" + 
				"WHERE date_cancelled IS NULL  \n" +
				"AND batch_no IS NOT NULL \n" +
				"AND status_id = 'A'";

	}

	public void displayEditPerBatchRecommended(model_CancelActive_PerBatch modelPerBatchModel, JList rowHeader, String p_batch_no) {

		modelPerBatchModel.clear();
		String SQL = "";

		SQL = "SELECT TRUE,* FROM sp_generate_edit_recom_cancellation('"+p_batch_no+"')";
		FncSystem.out("Client ", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){

			ArrayList<Object[]> listData = new ArrayList<Object[]>();
			for(int x=0; x<db.getRowCount(); x++){
				modelPerBatchModel.addRow(db.getResult()[x]);
				listData.add(db.getResult()[x]);
			}

			printSQL = SQL;

		}else{

			JOptionPane.showMessageDialog(FncGlobal.homeMDI, "No Records to Show!", "Empty Resultset", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	public void displayEditPerBatchRecommended_new(model_Recommend_Cancellation modelRecom, JList rowHeader, String p_batch_no) {

		modelRecom.clear();
		String SQL = "";
		
		SQL = "SELECT false,* FROM sp_generate_req_cancellation_edit('"+p_batch_no+"') ORDER BY _get_sales_div(c_entity_id,c_pbl_id,c_seq_no)"; //XXX ADDED BY LESTER ORDER BY SALES DIVISION TO MAKE SIMILAR WITH TABLE AND REPORT
		FncSystem.out("Client ", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){

			ArrayList<Object[]> listData = new ArrayList<Object[]>();
			for(int x=0; x<db.getRowCount(); x++){
				modelRecom.addRow(db.getResult()[x]);
				listData.add(db.getResult()[x]);
			}

			printSQL = SQL;

		}else{

			JOptionPane.showMessageDialog(FncGlobal.homeMDI, "No Records to Show!", "Empty Resultset", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	public String generatePerAccountRecommended(String proj_id, Integer buyer_type,Boolean DuetoDocs, Boolean DuetoPaymets,Boolean all) {

		String SQL = "";
		String type ="";

		if(buyer_type == 0 ){
			type = "'IHF', 'PAGIBIG', 'BANK FINANCE', 'CASH'";
		}else if (buyer_type == 1){
			type = "'IHF'";
		}else if (buyer_type == 2){
			type = "'PAGIBIG'";
		}else if (buyer_type== 3){
			type = "'BANK FINANCE'";
		}else if (buyer_type ==4){
			type = "'CASH'";
		}


		SQL ="SELECT DISTINCT ON (a.entity_id, a.projcode, TRIM(a.pbl_id), a.seq_no) \n" + 
				//"get_unit_description_project(a.projcode,TRIM(a.pbl_id)) as \"Unit \",\n" +
				"FORMAT('%s - %s', b.proj_alias, get_merge_unit_desc_v3(a.entity_id, a.projcode, a.pbl_id, a.seq_no)) as \"Unit \", \n"+
				"get_client_name(a.entity_id) AS \"Client Name\",\n" + 
				"get_buyer_type_alias(a.entity_id, a.projcode, TRIM(a.pbl_id), a.seq_no) AS \"Buyer Type\",\n" + 
				"get_current_status (a.entity_id, a.projcode, TRIM(a.pbl_id), a.seq_no) AS \"Current Status\",\n" + 
				"a.entity_id as \"Entity ID\", a.projcode as \"Proj. ID\", TRIM(a.pbl_id) AS \"PBL ID\", a.seq_no AS \"Seq. No.\"\n" + 
				"from rf_sold_unit a\n" +
				"LEFT JOIN mf_project b on b.proj_id = a.projcode \n"+
				"where a.projcode IN ('"+proj_id+"')\n" + 
				//"and not exists (select * from rf_buyer_status where entity_id = a.entity_id and proj_id = a.projcode and  pbl_id = TRIM(a.pbl_id) and seq_no = a.seq_no and status_id = 'A' and byrstatus_id IN ('02','27','32')) \n" + 
				"and not exists (SELECT * FROM rf_cancellation WHERE entity_id = a.entity_id AND proj_id = a.projcode AND pbl_id = TRIM(a.pbl_id) AND seq_no = a.seq_no AND status_id = 'A' )\n" + 
				(DuetoDocs ? "AND NOT EXISTS (SELECT * FROM rf_buyer_status where entity_id = a.entity_id and proj_id = a.projcode and  pbl_id = TRIM(a.pbl_id) and seq_no = a.seq_no and status_id = 'A' and byrstatus_id IN ('01')) " : "" ) +
				//(DuetoPaymets ? "AND sp_months_past_due(a.entity_id,a.projcode,TRIM(a.pbl_id),a.seq_no,current_date,false ) > 0" : "" ) +
				"\n" + 
				"and get_buyer_type_alias(a.entity_id, a.projcode, TRIM(a.pbl_id), a.seq_no ) IN("+type+") \n" +
				"order by a.entity_id, a.projcode, TRIM(a.pbl_id), a.seq_no, get_client_name(a.entity_id) ,get_client_name(a.entity_id)";


		/*if (DuetoDocs) {
		SQL = "SELECT \n" +
              "c_entity_id AS \"Entity ID\", \n" +
			  "c_unit_pbl AS \"Unit\", \n" + 
			  "c_client_name AS \"Cliet Name\", \n" +
			  "c_buyer_type AS \"Buyer Type\", \n" +
			  "c_current_status As \"Current Status\" \n" +
			  "c_payment_stage As \"Payment Stage\", \n" +
			  "c_months_pd AS \"Months PD\", \n" +
			  "c_default_date as \"Default Date\", \n" +
			  "c_pbl_id AS \"Pbl ID\", \n" +
			  "c_part_id AS \"Part ID\" \n" +
			  "FROM sp_generate_for_cancellation_new('"+proj_id+"',"+buyer_type+",'"+due_date+"',"+DuetoDocs+","+DuetoPaymets+") \n" +
			  "WHERE c_buyer_type IN ("+type+") \n" +
			  "AND NOT EXISTS (SELECT * FROM rf_buyer_status where entity_id = c_entity_id and proj_id = c_proj_id and  pbl_id = c_pbl_id and seq_no = c_seq_no and status_id = 'A' and byrstatus_id IN ('01')) order by c_client_name";
	}

	if (DuetoPaymets || all){

		SQL = "SELECT \n" +
			  "c_entity_id AS \"Entity ID\", \n" +
			  "c_unit_pbl AS \"Unit\", \n" + 
			  "c_client_name AS \"Cliet Name\", \n" +
			  "c_buyer_type AS \"Buyer Type\", \n" +
			  "c_current_status As \"Current Status\" \n" +
			  "c_payment_stage As \"Payment Stage\", \n" +
			  "c_months_pd AS \"Months PD\", \n" +
			  "c_default_date as \"Default Date\", \n" +
			  "c_pbl_id AS \"Pbl ID\", \n" +
			  "c_part_id AS \"Part ID\" \n" +
			  "FROM sp_generate_for_cancellation_new('"+proj_id+"',"+buyer_type+",'"+due_date+"',"+DuetoDocs+","+DuetoPaymets+") \n" +
			  "WHERE c_buyer_type IN ("+type+") \n" +
		      "ORDER BY c_client_name";

		//SQL = "SELECT * FROM sp_generate_for_cancellation_new('"+proj_id+"',"+buyer_type+",'"+due_date+"',"+DuetoDocs+","+DuetoPaymets+") where  c_buyer_type IN ("+type+") order by c_client_name";

	}*/

		FncSystem.out("Client ", SQL);

		return SQL;

	}

	public void generatePerBatchRecommended(model_CancelActive_PerBatch modelPerBatchModel, JList rowHeader, String proj_id, Date due_date, Integer buyer_type,Boolean DuetoDocs, Boolean DuetoPaymets,Boolean all) {

		modelPerBatchModel.clear();
		String SQL = "";
		String type ="";

		if(buyer_type == 0 ){
			type = "'IHF', 'PAG-IBIG'";
		}else if (buyer_type == 1){
			type = "'IHF'";
		}else{
			type = "'PAG-IBIG'";
		}

		if (DuetoDocs) {
			SQL = "SELECT * FROM sp_generate_for_cancellation_new('"+proj_id+"',"+buyer_type+",'"+due_date+"',"+DuetoDocs+","+DuetoPaymets+") where  c_buyer_type IN ("+type+") \n" +
					"AND NOT EXISTS (SELECT * FROM rf_buyer_status where entity_id = c_entity_id and proj_id = c_proj_id and  pbl_id = c_pbl_id and seq_no = c_seq_no and status_id = 'A' and byrstatus_id IN ('01')) order by c_client_name";
		}

		if (DuetoPaymets || all){
			SQL = "SELECT * FROM sp_generate_for_cancellation_new('"+proj_id+"',"+buyer_type+",'"+due_date+"',"+DuetoDocs+","+DuetoPaymets+") where  c_buyer_type IN ("+type+") order by c_client_name";

		}

		FncSystem.out("Client ", SQL);

		db.select(SQL);

		if(db.isNotNull()){

			ArrayList<Object[]> listData = new ArrayList<Object[]>();

			for(int x=0; x<db.getRowCount(); x++){
				modelPerBatchModel.addRow(db.getResult()[x]);
				listData.add(db.getResult()[x]);
			}

			printSQL = SQL;

		}
	}

	public void generatePerBatchRecommended(model_CancelActive_PerBatch modelPerBatchModel, JList rowHeader, String proj_id, Date due_date, Integer buyer_type, Integer canceltype, Integer sales_div) {

		modelPerBatchModel.clear();
		String SQL = "";
		String type ="";
		String v_sales_div = "";


		if (sales_div == 1){

			v_sales_div = "D1";
		}
		if (sales_div == 2){

			v_sales_div = "D2";
		}
		if (sales_div == 3){

			v_sales_div = "D3";
		}
		if (sales_div == 4){

			v_sales_div = "D4";
		}



		if(buyer_type == 0 ){
			type = "'IHF', 'PAG-IBIG', 'BANK FINANCE', 'CASH'";
		}else if (buyer_type == 1){
			type = "'IHF'";
		}else if (buyer_type == 2){
			type = "'PAG-IBIG'";
		}else if (buyer_type == 3){
			type = "'BANK FINANCE'";
		}else if(buyer_type == 4) {
			type = "'CASH'";
		}

		SQL = "SELECT * FROM sp_generate_for_cancellation_new('"+proj_id+"',"+buyer_type+",'"+due_date+"',"+canceltype+") \n"+
			  "where  c_buyer_type IN ("+type+") \n" +
			  (sales_div == 0  ? "" : "AND c_sale_div ~* '"+v_sales_div+"'") +
			  "\n order by c_client_name";


		FncSystem.out("Client ", SQL);

		db.select(SQL);

		if(db.isNotNull()){

			ArrayList<Object[]> listData = new ArrayList<Object[]>();

			for(int x=0; x<db.getRowCount(); x++){
				modelPerBatchModel.addRow(db.getResult()[x]);
				listData.add(db.getResult()[x]);
			}

			printSQL = SQL;

		}
	}

	public void generatePerBatchRecommended_edit(model_CancelActive_PerBatch modelPerBatchModel, JList rowHeader, String proj_id, Date due_date, Integer buyer_type,String batch_no,Boolean DuetoDocs, Boolean DuetoPaymets,Boolean all) {

		modelPerBatchModel.clear();
		String SQL = "";
		String type ="";

		if(buyer_type == 0 ){
			type = "'IHF', 'PAG-IBIG'";
		}else if (buyer_type == 1){
			type = "'IHF'";
		}else{
			type = "'PAG-IBIG'";
		}

		if (DuetoDocs) {
			SQL = "SELECT * FROM sp_generate_for_cancellation_new('"+proj_id+"',"+buyer_type+",'"+due_date+"',"+DuetoDocs+","+DuetoPaymets+") where  c_buyer_type IN ("+type+") \n" +
					"AND NOT EXISTS (SELECT * FROM rf_buyer_status where entity_id = c_entity_id and proj_id = c_proj_id and  pbl_id = c_pbl_id and seq_no = c_seq_no and status_id = 'A' and byrstatus_id IN ('01'))\n" +
					"AND  EXISTS (SELECT * FROM sp_generate_req_cancellation('"+batch_no+"')) \n"+
					"\n" + 
					"UNION \n" + 
					"\n" +
					"SELECT * FROM sp_generate_req_cancellation('"+batch_no+"')\n" + 
					"ORDER BY c_select desc ,c_client_name ASC";
		}

		if (DuetoPaymets || all){
			SQL = "SELECT * FROM sp_generate_for_cancellation_new('"+proj_id+"',"+buyer_type+",'"+due_date+"',"+DuetoDocs+","+DuetoPaymets+") where  c_buyer_type IN ("+type+")  \n" +
					"AND  EXISTS (SELECT * FROM sp_generate_req_cancellation('"+batch_no+"')) \n"+
					"\n" + 
					"UNION \n" + 
					"\n" +
					"SELECT * FROM sp_generate_req_cancellation('"+batch_no+"')\n" + 
					"ORDER BY c_select desc ,c_client_name ASC";

		}


		FncSystem.out("Client ", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){

			ArrayList<Object[]> listData = new ArrayList<Object[]>();

			for(int x=0; x<db.getRowCount(); x++){
				modelPerBatchModel.addRow(db.getResult()[x]);
				listData.add(db.getResult()[x]);
			}

			printSQL = SQL;

		}
	}


	public void generatePerBatchRecommended_edit(model_CancelActive_PerBatch modelPerBatchModel, JList rowHeader, String proj_id, Date due_date, Integer buyer_type,String batch_no,Integer canceltype) {

		modelPerBatchModel.clear();
		String SQL = "";
		String type ="";

		if(buyer_type == 0 ){
			type = "'IHF', 'PAG-IBIG'";
		}else if (buyer_type == 1){
			type = "'IHF'";
		}else{
			type = "'PAG-IBIG'";
		}

		/*SQL = "SELECT * FROM sp_generate_for_cancellation_new('"+proj_id+"',"+buyer_type+",'"+due_date+"',"+canceltype+") where  c_buyer_type IN ("+type+") \n" +
				"AND NOT EXISTS (SELECT * FROM rf_buyer_status where entity_id = c_entity_id and proj_id = c_proj_id and  pbl_id = c_pbl_id and seq_no = c_seq_no and status_id = 'A' and byrstatus_id IN ('01'))\n" +
				"AND  EXISTS (SELECT * FROM sp_generate_req_cancellation('"+batch_no+"')) \n"+
				"\n" + 
				"UNION \n" + 
				"\n" +
				"SELECT * FROM sp_generate_req_cancellation('"+batch_no+"')\n" + 
				"ORDER BY c_select desc ,c_client_name ASC";*/

		SQL = "SELECT * FROM sp_generate_for_cancellation_new('"+proj_id+"',"+buyer_type+",'"+due_date+"',"+canceltype+") where  c_buyer_type IN ("+type+")  \n" +
				"AND  EXISTS (SELECT * FROM sp_generate_req_cancellation('"+batch_no+"')) \n"+
				"\n" + 
				"UNION \n" + 
				"\n" +
				"SELECT * FROM sp_generate_req_cancellation('"+batch_no+"')\n" + 
				"ORDER BY c_select desc ,c_client_name ASC";



		FncSystem.out("Client ", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){

			ArrayList<Object[]> listData = new ArrayList<Object[]>();

			for(int x=0; x<db.getRowCount(); x++){
				modelPerBatchModel.addRow(db.getResult()[x]);
				listData.add(db.getResult()[x]);
			}

			printSQL = SQL;

		}
	}
	public  String getClientPerAccount(){

		return "select entity_id AS \"Entity ID\",\n" + 
				"_get_client_name(a.entity_id) AS \"Client Name\",  \n" + 
				"get_unit_description(a.proj_id,TRIM(a.pbl_id)) AS \"Unit Desc.\", \n" + 
				"get_project_name(proj_id) AS \"Project Name\",\n" + 
				"unit_id AS \"Unit ID\"\n" + 
				"from rf_cancellation  a\n" + 
				"WHERE batch_no IS NULL\n" + 
				"AND date_requested IS NOT NULL and status_id = 'A'";

	}


	public String getNoticeBatchNo(){
		pgSelect db = new pgSelect();
		db.select("select trim(to_char(coalesce(max(batch_no)::int,0) + 1,'FM0000000000')) from rf_client_notices") ;
		String noticeno = "";
		if (db.isNotNull()) {
			noticeno = db.Result[0][0].toString();
		}
		return noticeno;
	}
	
	public static String sqlPerAcctProjCancel(String co_id, String proj_id, Integer buyertype, Integer cancel_type, Integer sales_div, Date due_date){
		return "SELECT * FROM sp_generate_for_cancellation_new_per_accont(NULLIF('"+co_id+"', 'null'),NULLIF('"+proj_id+"', 'null'),"+buyertype+","+cancel_type+", "+sales_div+",'"+due_date+"'::TIMESTAMP);";
	}
}
