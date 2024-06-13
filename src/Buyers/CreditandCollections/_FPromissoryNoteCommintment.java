package Buyers.CreditandCollections;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import Database.pgSelect;
import Functions.FncGlobal;
import Functions.FncSystem;
import tablemodel.model_PN_History;
import tablemodel.model_PastDue;

public class _FPromissoryNoteCommintment {

	public _FPromissoryNoteCommintment() {
	}



	public String getClient() {
		return "SELECT \n" + 
				"a.entity_id AS \"Entity ID\",  \n" + 
				"_get_client_name(a.entity_id) AS \"Client Name\",  \n" + 
				"get_unit_description(a.proj_id,a.pbl_id) AS \"Unit Description\",\n" + 
				"get_project_name(a.proj_id) AS \"Project Name\", \n" + 
				"_get_client_telephoneno(a.entity_id) AS \"Telephone No.\",\n" + 
				"_get_client_mobileNo(a.entity_id) AS \"Mobile No.\",\n" + 
				"TRIM(a.proj_id) AS \"Project ID\",\n" + 
				"TRIM(a.pbl_id) AS \"PBL ID\", \n" + 
				"_get_client_buyertype_desc(buyertype) AS \"Buyer Desc.\",\n" + 
				"a.seq_no AS \"Seq No.\"\n" + 
				"FROM get_active_accounts() a \n" + 
				"JOIN rf_sold_unit b ON a.pbl_id = b.pbl_id AND a.seq_no = b.seq_no\n" + 
				"ORDER BY \"Client Name\",\"Unit Description\"\n" + 
				"\n" + 
				"";
		
		
	}
	
	public String getClient_new(Boolean ifPD, String co_id) {
		
		System.out.println("***********" + ifPD);
		String SQL = "select get_client_name(a.entity_id) as \"Client Name\",\n" + 
				"get_unit_description(a.proj_id,a.pbl_id) as \"Unit\",\n" + 
				"_get_client_buyertype_desc(a.buyertype),\n" + 
				"get_project_name(a.proj_id) AS \"Project Name\",\n" + 
				"nullif(_get_client_telephoneno(a.entity_id),'') as \"Tel. No.\",\n" + 
				"NULlif(_get_client_mobileNo(a.entity_id),'') AS \"Mobile No.\",\n" + 
				"a.entity_id as \"Entity ID\",\n" + 
				"a.proj_id AS \"Proj ID\", \n" + 
				"a.pbl_id as \"PBL ID\", \n" + 
				"a.seq_no as \"Seq No.\",\n" + 
				"get_group_id(a.buyertype) as \"Buyer ID\",\n" + 
				"b.pmt_stage as \"Payment Stage\"\n" + 
				"FROM (select distinct on (entity_id,projcode,pbl_id,seq_no) \n" + 
				"\n" + 
				"		entity_id,\n" + 
				"		projcode as proj_id, \n" + 
				"		pbl_id, \n" + 
				"		seq_no,\n" + 
				"		get_unit_id(projcode,pbl_id) as unit_id, \n" + 
				"		get_group_id(buyertype) as buyertype_id,\n" + 
				"		buyertype\n" + 
				"		\n" + 
				"		from rf_sold_unit a\n" +
				" 		LEFT JOIN mf_project b on b.proj_id = a.projcode \n"+
				"		where a.status_id = 'A'\n" +
				"		and a.currentstatus != '02' \n"+
				"		and get_group_id(a.buyertype) is not null\n" +
				"		AND b.co_id = '"+co_id+"' \n"+
				"		and not exists(select * from rf_cancellation where entity_id = a.entity_id and proj_id = a.projcode and pbl_id = a.pbl_id and seq_no = a.seq_no and status_id = 'A')\n" + 
				"		--AND get_current_status(entity_id,projcode,pbl_id,seq_no) <> 'Cancelled'\n" + 
				(ifPD == true? "" : "--" ) + "		and exists (select * from rf_card_pmt_status where entity_id = a.entity_id and proj_id = a.projcode and pbl_id = a.pbl_id and seq_no = a.seq_no and pmt_status = 'PAST DUE') \n" +
				"		---and get_payment_stage_id(entity_id,projcode,pbl_id,seq_no) <> '012' \n" + 
				"		--and not exists (select * from rf_buyer_status where entity_id = a.entity_id and proj_id = a.projcode and pbl_id = a.pbl_id and seq_no = a.seq_no and status_id = 'A' AND byrstatus_id IN ('27', '32'))\n" + 
				"		--and projcode = '015'\n" + 
				") a \n" + 
				"LEFT JOIN rf_card_pmt_status b on b.entity_id = a.entity_id and b.proj_id = a.proj_id and b.pbl_id = a.pbl_id and b.seq_no = a.seq_no \n" + 
				"order by \"Client Name\",\"Unit\"\n" + 
				"";
		System.out.println("SQL***********" + SQL);
		return SQL;
		
	}
	public  String getPN_no(){
		pgSelect db = new pgSelect();
		db.select("SELECT to_char(COALESCE(MAX(pn_no::INT), 0) + 1, 'FM0000000000') FROM rf_pn_header");
		if(db.isNotNull()){
			return (String) db.getResult()[0][0];
		}else{
			return null;
		}
	}
	public  String getBuyerType(String _entity_id, String _proj_id, String _pbl_id, Integer _seq_no){
		pgSelect db = new pgSelect();
		db.select("SELECT _get_client_buyertype( '"+_entity_id+"' , '"+_proj_id+"' , '"+_pbl_id+"' , '"+_seq_no+"' ) ");
		if(db.isNotNull()){
			return (String) db.getResult()[0][0];
		}else{
			return null;
		}

	}
	public String get_Active_PN(String buyertype){
		return"select\n" + 
				"a.entity_id, \n" + 
				"_get_client_name(a.entity_id) AS \"Client Name\",  \n" + 
				"get_unit_description(a.proj_id,a.pbl_id) AS \"Unit Description\", \n" + 
				"get_project_name(a.proj_id) AS \"Project Name\",  \n" + 
				"_get_client_telephoneno(a.entity_id) AS \"Telephone No.\", \n" + 
				"_get_client_mobileNo(a.entity_id) AS \"Mobile No.\", \n" + 
				"\n" + 
				"TRIM(a.proj_id) AS \"Project ID\", \n" + 
				"TRIM(a.pbl_id) AS \"PBL ID\",  \n" + 
				"a.seq_no AS \"Seq No.\" \n" + 
				"\n" + 
				"FROM \n" + 
				"(SELECT DISTINCT ON (unit_id) * FROM rf_pn_header \n" + 
				"WHERE status_id = 'A' \n" + 
				"ORDER BY unit_id, seq_no DESC, pn_id DESC) a \n" + 
				"\n" + 
				"where current_date <= due_date -- PN that not exceeded the due date \n" + 
				"AND _get_client_buyertype(a.entity_id,a.proj_id,a.pbl_id,a.seq_no) in ('"+buyertype+"') \n" + 
				"\n" + 
				"ORDER BY \"Client Name\" \n" + 
				"";
	}

	public String getPNCommitmentStatus(String _entity_id, String _proj_id, String _pbl_id, Integer _seq_no){
		return"SELECT \n" + 
				"(CASE WHEN date_committed_2 IS NULL THEN date_committed_1 ELSE date_committed_2 END )::DATE AS \"Date Committed\",\n" + //0
				"date_paid AS \"Date Paid\",\n" + //1
				"committed_amt_1 + COALESCE(committed_amt_2,0) AS \"Total Committed Amount\",\n" +  //2
				"COALESCE(total_amount_committed,0.00) AS \"Total Amount Paid\"\n" + //3

				"FROM rf_pn_header \n" + 
				"WHERE entity_id = '"+_entity_id+"'  \n" + 
				"AND proj_id = '"+_proj_id+"' \n" + 
				"AND pbl_id = '"+_pbl_id+"' \n" + 
				"AND seq_no = "+_seq_no+" \n" + 
				"AND status_id = 'A' ";
	}

	public String getSignedBy(){
		return "SELECT emp_code AS \"Employee Code\",\n" + 
				"	trim(entity_name) AS \"Employee Name\"  \n" + 
				"	FROM em_employee a\n" + 
				"	LEFT JOIN rf_entity b ON a.entity_id = b.entity_id\n" + 
				"	WHERE upper(trim(emp_rank)) IN ('SSUP', 'AM', 'M', 'SAM', 'SM', 'SVP', 'AVP', 'EVP', 'VP','SP1')\n" + 
				"	AND terminate_date IS NULL\n" + 
				"	ORDER BY entity_name";
	}

	public String getDetails(String _entity_id, String _proj_id, String _pbl_id, Integer _seq_no ){
		String SQL  = "";

		System.out.printf("SQL: %s%n%n", SQL);
		return SQL;

	}
	
	
	public String displayPastDue(String _entity_id, String _proj_id, String _pbl_id, Integer _seq_no ,String table_name){
		String SQL  = "\n" + 
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
		System.out.printf("SQL: %s%n%n", SQL);
		return SQL;

	}
	public static void displayPnHistory(model_PN_History model, JList rowHeader, String _entity_id, String _proj_id, String _pbl_id, Integer _seq_no ) {
		model.clear();
		String SQL = "SELECT * FROM sp_get_pn_history('"+_entity_id+"','"+_proj_id+"', '"+_pbl_id+"', "+_seq_no+") order by c_status_id ";


		FncSystem.out("Client Schedule", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			ArrayList<Object[]> listData = new ArrayList<Object[]>();
			for(int x=0; x<db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
				listData.add(db.getResult()[x]);
			}
		}else{

			///JOptionPane.showMessageDialog(FncGlobal.homeMDI, "No Records to Show!", "Empty Resultset", JOptionPane.INFORMATION_MESSAGE);
		}

	}

	public String PostingSQL(String _entity_id, String _proj_id, String _unit_id, String _pbl_id, Integer _seq_no, String _part_id, String _lastpaydate, String _default_date, Timestamp _due_date, Integer _monthspd , 
			Integer _dayspd, BigDecimal _amountdue, Date _datecommitted1, String _committed_amt1,Date _datecommitted2, String _committed_amt2,
			Date _datesubmitted, Date _dategranted, String _signedby, String _remarks, String _postedby,String _pn_no,String months_update_1,String months_update_2){

		return"INSERT INTO rf_pn_header(\n" + 
				"            entity_id, \n" + 
				"            proj_id, \n" + 
				"            unit_id, \n" + 
				"            pbl_id, \n" + 
				"            seq_no, \n" + 
				"            part_id, \n" + 
				"            last_paydate, \n" + 
				"            default_date, \n" + 
				"            due_date, \n" + 
				"            months_pd, \n" + 
				"            days_pd, \n" + 
				"            amount_due, \n" + 
				"            date_committed_1, \n" + 
				"            committed_amt_1, \n" + 
				"            date_committed_2, \n" + 
				"            committed_amt_2, \n" + 
				"            date_submitted, \n" + 
				"            date_granted, \n" + 
				"            signed_by, \n" + 
				"            remarks, \n" + 
				"            date_posted, \n" + 
				"            posted_by, \n" + 
				"            status_id, \n" + 
				"            pn_no,months_update_1,months_update_2)\n" + 
				"    VALUES (\n" + 
				"			'"+_entity_id+"',												---entity_id, \n" + 
				"			'"+_proj_id+"',													---proj_id, \n" + 
				"			'"+_unit_id+"',													---unit_id, \n" + 
				"			'"+_pbl_id+"',													---pbl_id, \n" + 
				"			"+_seq_no+",													---seq_no, \n" +
				"			NULLIF('"+_part_id+"',null),  									---part_id, \n" + 
				"			'"+_lastpaydate+"'::TIMESTAMP,  								---last_paydate, \n" + 
				"			'"+_default_date+"'::TIMESTAMP,  								---default_date, \n" + 
				"			'"+_due_date+"'::TIMESTAMP,  									---due_date, \n" + 
				"			'"+_monthspd+"',  												---months_pd, \n" + 
				"			'"+_dayspd+"',  												---days_pd, \n" + 
				"			'"+_amountdue+"', 												---amount_due, \n" + 
				"			NULLIF('"+_datecommitted1+"','null')::TIMESTAMP,  				---date_committed_1, \n" + 
				"			NULLIF('"+_committed_amt1+"','')::NUMERIC,  					---committed_amt_1, \n" + 
				"			NULLIF('"+_datecommitted2+"','null')::TIMESTAMP,  				---date_committed_2, \n" + 
				"			NULLIF('"+_committed_amt2+"','')::NUMERIC,  					---committed_amt_2, \n" + 
				"			'"+_datesubmitted+"'::TIMESTAMP,								---date_submitted, \n" + 
				"			'"+_dategranted+"'::TIMESTAMP,  								---date_granted, \n" + 
				"			'"+_signedby+"',  												---signed_by, \n" + 
				"			'"+_remarks+"', 												---remarks, \n" + 
				"			current_date::TIMESTAMP,  										---date_posted, \n" + 
				"			'"+_postedby+"',  												---posted_by, \n" + 
				"			'A',					   										---status_id,\n" + 
				"			'"+_pn_no+"', '"+months_update_1+"', '"+months_update_2+"'		---pn_no\n" + 
				"			);\n" + 
				"";

	}

	public String SaveEdit(String _pn_no, Date _datecommitted1, String _committed_amt1,Date _datecommitted2, String _committed_amt2,
			Date _datesubmitted, Date _dategranted, String _signedby, String _remarks,String _postedby,String mupdate1,String mupdate2){
		return "UPDATE rf_pn_header\n" + 
				"   SET (date_committed_1, \n" + 
				"	committed_amt_1, \n" +
				"	months_update_1, \n" + 
				"	date_committed_2, \n" + 
				"	committed_amt_2, \n" + 
				"	months_update_2, \n" + 
				"	date_submitted, \n" + 
				"	date_granted, \n" + 
				"	signed_by, \n" + 
				"	remarks, \n" + 
				"	date_posted,\n" + 
				"	posted_by\n" + 
				"	)\n" + 
				"	=\n" + 
				"	(	NULLIF('"+_datecommitted1+"','null')::TIMESTAMP,		 			---date_committed_1, \n" + 
				"       NULLIF('"+_committed_amt1+"','')::NUMERIC,  						---committed_amt_1, \n" +
				"       '"+mupdate1+"',  													---months_update_1, \n" + 
				"       NULLIF('"+_datecommitted2+"','null')::TIMESTAMP,  					---date_committed_2, \n" + 
				"       NULLIF('"+_committed_amt2+"','')::NUMERIC,  						---committed_amt_2, \n" + 
				"       '"+mupdate2+"',  													---months_update_2, \n" + 
				"       '"+_datesubmitted+"'::TIMESTAMP,  									---date_submitted, \n" + 
				"       '"+_dategranted+"'::TIMESTAMP,  									---date_granted, \n" + 
				"       '"+_signedby+"',  													---signed_by, \n" + 
				"       '"+_remarks+"', 													---remarks, \n" + 
				"		 current_date::TIMESTAMP, 											---date_posted,\n" + 
				"       '"+_postedby+"'  													---posted_by, \n" + 
				"	)\n" + 
				" WHERE pn_no = '"+_pn_no+"' \n" + 
				" AND status_id = 'A';\n" + 
				"";
	}

	public  String getCompany() {//XXX Company

		String SQL = "SELECT co_id as \"ID\", trim(company_name) as \"Company Name\", trim(company_alias) as \"Alias\", company_logo as \"Logo\" FROM mf_company";
		return SQL;
	}

	public String LookupPn_No(){

		String SQL ="SELECT\n" + 
				"pn_no AS \"PN No. \", \n" + 
				"entity_id AS \"Entity ID \",\n" + 
				"_get_client_name(a.entity_id) AS \"Client Name\",  \n" + 
				"get_unit_description(a.proj_id,a.pbl_id) AS \"Unit Description\", \n" + 
				"get_project_name(a.proj_id) AS \"Project Name\",  \n" + 
				"_get_particular_alias(part_id)AS \"Stage\", \n" +
				"b.co_id as \"CO ID\", \n"+
				"c.company_name as \"Company Name\", \n"+
				"c.company_logo as \"Logo\" \n"+
				"FROM  rf_pn_header a\n" +
				"LEFT JOIN mf_project b on b.proj_id = a.proj_id \n"+
				"LEFT JOIN mf_company c on c.co_id = b.co_id \n"+
				"WHERE pn_no IS NOT NULL \n" + 
				"AND date_posted IS NULL \n" +
				"ORDER BY pn_no \n" + 
				"";

		System.out.printf("SQL: %s%n%n", SQL);
		return SQL;
	}

	public String LookupSettled(){

		String SQL ="SELECT\n" + 
				"pn_no, \n" + 
				"entity_id,\n" + 
				"_get_client_name(a.entity_id) AS \"Client Name\",  \n" + 
				"get_unit_description(a.proj_id,a.pbl_id) AS \"Unit Description\", \n" + 
				"get_project_name(a.proj_id) AS \"Project Name\",  \n" + 
				"_get_particular_alias(part_id)AS \"Stage\" \n" + 
				"\n" + 
				"FROM  rf_pn_header a\n" + 
				"WHERE pn_no IS NOT NULL \n" + 
				"";

		System.out.printf("SQL: %s%n%n", SQL);
		return SQL;
	}

	public String  getEditDetails(String _pn_no){

		/*String SQL = "SELECT\n" + 
				"pn_no, ---0 \n" + 
				"entity_id, ---1\n" + 
				"_get_client_name(a.entity_id) AS \"Client Name\",  ---2 \n" + 
				"get_unit_description(a.proj_id,a.pbl_id) AS \"Unit Description\", ---3\n" + 
				"get_project_name(a.proj_id) AS \"Project Name\",  ---4\n" + 
				"_get_client_telephoneno(a.entity_id) AS \"Telephone No.\", ---5\n" + 
				"_get_client_mobileNo(a.entity_id) AS \"Mobile No.\", ---6\n" + 
				"TRIM(a.proj_id) AS \"Project ID\", ---7\n" + 
				"TRIM(a.pbl_id) AS \"PBL ID\",  ---8\n" + 
				"a.seq_no AS \"Seq No.\" ,---9\n" + 
				"months_pd AS \"Months PD\" ,---10\n" + 
				"days_pd AS \"Days PD\" , ---11\n" + 
				"_get_particular_alias(part_id)AS \"Stage\" , ---12\n" + 
				"amount_due AS \"Amount Due\" , ---13\n" + 
				"last_paydate::DATE AS \"Last Paid Date\" , ---14\n" + 
				"default_date::DATE AS \"Default Date\" , ---15\n" + 
				"date_committed_1 AS \"Date Committed 1\" , ---16\n" + 
				"committed_amt_1 AS \"Amount Committed 1\" ,---17\n" + 
				"date_committed_2 AS \"Date Committed 2\" ,---18\n" + 
				"committed_amt_2 AS \"Amount Committed 2\" ,---19\n" + 
				"date_submitted AS \"Date Submitted\" ,---20\n" + 
				"date_granted AS \"Date Granted\" ,---21\n" + 
				"signed_by AS \"Signed By\" ,---22\n" + 
				"remarks AS \"Remarks\" ---23\n" + 
				"FROM  rf_pn_header a \n" + 
				"WHERE pn_no IS NOT NULL \n" + 
				"AND pn_no = '"+_pn_no+"' \n" +
				//	"AND  date_paid IS NULL \n" + 
				"";
		 */

		String SQL = "SELECT\n" + 
				"pn_no AS \"PN No. \", \n" + 
				"entity_id AS \"Entity ID \",\n" + 
				"_get_client_name(a.entity_id) AS \"Client Name\",  ---2 \n" + 
				"get_unit_description(a.proj_id,a.pbl_id) AS \"Unit Description\", ---3\n" + 
				"get_project_name(a.proj_id) AS \"Project Name\",  ---4\n" + 
				"_get_client_telephoneno(a.entity_id) AS \"Telephone No.\", ---5\n" + 
				"_get_client_mobileNo(a.entity_id) AS \"Mobile No.\", ---6\n" + 
				"TRIM(a.proj_id) AS \"Project ID\", ---7\n" + 
				"TRIM(a.pbl_id) AS \"PBL ID\",  ---8\n" + 
				"a.seq_no AS \"Seq No.\" ,---9\n" + 
				"months_pd AS \"Months PD\" ,---10\n" + 
				"days_pd AS \"Days PD\" , ---11\n" + 
				"_get_particular_alias(part_id)AS \"Stage\" , ---12\n" + 
				"amount_due AS \"Amount Due\" , ---13\n" + 
				"last_paydate::DATE AS \"Last Paid Date\" , ---14\n" + 
				"default_date::DATE AS \"Default Date\" , ---15\n" + 
				"date_submit AS \"Date Submitted\" ,---16\n" + 
				"signed_by AS \"Signed By\" ,---17\n" + 
				"remarks AS \"Remarks\", ---18\n" + 
				"approved AS \"Approved ID\", ---19\n" + 
				"get_employee_name(approved) AS \"Approved by\", ---20\n" +
				"amount_to_pay AS \"Amount to Pay\", ---21\n" +
				"payment_amount AS \"Payment Amount\", ---22\n" +
				"payment_date AS \"Payment Date\", ---23\n" +
				"relationwclient , ---24\n" +
				"contact_no_rwc , ---25\n" +
				"email_add_rwc  ---26\n" +
				"FROM  rf_pn_header a \n" + 
				"WHERE pn_no IS NOT NULL \n" + 
				"AND pn_no = '"+_pn_no+"' \n" +
				"";


		System.out.printf("SQL: %s%n%n", SQL);
		return SQL;

	}

	public String  getSettledtDetails(String _pn_no){

		String SQL = "SELECT\n" + 
				"pn_no AS \"PN No. \", \n" + 
				"entity_id AS \"Entity ID \",\n" + 
				"_get_client_name(a.entity_id) AS \"Client Name\",  ---2 \n" + 
				"get_unit_description(a.proj_id,a.pbl_id) AS \"Unit Description\", ---3\n" + 
				"get_project_name(a.proj_id) AS \"Project Name\",   ---4\n" + 
				"_get_client_telephoneno(a.entity_id) AS \"Telephone No.\", ---5\n" + 
				"_get_client_mobileNo(a.entity_id) AS \"Mobile No.\", ---6\n" + 
				"TRIM(a.proj_id) AS \"Project ID\", ---7\n" + 
				"TRIM(a.pbl_id) AS \"PBL ID\",  ---8\n" + 
				"a.seq_no AS \"Seq No.\" ,---9\n" + 
				"months_pd AS \"Months PD\" ,---10\n" + 
				"days_pd AS \"Days PD\" , ---11\n" + 
				"_get_particular_alias(part_id)AS \"Stage\" , ---12\n" + 
				"amount_due AS \"Amount Due\" , ---13\n" + 
				"last_paydate::DATE AS \"Last Paid Date\" , ---14\n" + 
				"default_date::DATE AS \"Default Date\" , ---15\n" + 
				"date_committed_1 AS \"Date Committed 1\" , ---16\n" + 
				"committed_amt_1 AS \"Amount Committed 1\" ,---17\n" + 
				"date_committed_2 AS \"Date Committed 2\" ,---18\n" + 
				"committed_amt_2 AS \"Amount Committed 2\" ,---19\n" + 
				"date_submitted AS \"Date Submitted\" ,---20\n" + 
				"date_granted AS \"Date Granted\" ,---21\n" + 
				"signed_by AS \"Signed By\" ,---22\n" + 
				"remarks AS \"Remarks\" ---23\n" + 
				"FROM  rf_pn_header a \n" + 
				"WHERE pn_no IS NOT NULL \n" + 
				"AND pn_no = '"+_pn_no+"' \n" + 
				"";

		System.out.printf("SQL: %s%n%n", SQL);
		return SQL;

	}

	public String displayPastDues(String entity_id, String proj_id, String unit_id, Integer seq_no) {

		//String SQL = "SELECT c_amount, c_scheddate FROM view_past_dues('"+ entity_id +"', '"+ proj_id +"', getinteger('"+ unit_id +"')::VARCHAR, "+ seq_no +", NOW()::TIMESTAMP, FALSE) ORDER BY c_scheddate LIMIT 1;";
		String SQL = "SELECT sum(c_amount), (SELECT c_scheddate FROM view_card_dues('"+ entity_id +"', '"+ proj_id +"', getinteger('"+ unit_id +"')::VARCHAR, "+ seq_no +", NOW()::TIMESTAMP, FALSE) order by c_scheddate desc limit 1) FROM view_card_dues('"+ entity_id +"', '"+ proj_id +"', getinteger('"+ unit_id +"')::VARCHAR, "+ seq_no +", NOW()::TIMESTAMP, FALSE)\n" + 
				"";
		return SQL;
	}

	public String getDetailsSettled(String _pn_no){

		String SQL = "SELECT \n" + 
				"pn_no AS \"PN No. \", \n" + 
				"entity_id AS \"Entity ID \",\n" + 
				"_get_client_name(a.entity_id) AS \"Client Name\",   \n" + // 2 
				"get_unit_description(a.proj_id,a.pbl_id) AS \"Unit Description\",  \n" + //3  
				"get_project_name(a.proj_id) AS \"Project Name\",   \n" +  //4
				"_get_client_telephoneno(a.entity_id) AS \"Telephone No.\",   \n" + //5 
				"_get_client_mobileNo(a.entity_id) AS \"Mobile No.\",  \n" +  //6
				"TRIM(a.proj_id) AS \"Project ID\",  \n" + //7  
				"TRIM(a.pbl_id) AS \"PBL ID\",   \n" + // 8
				"a.seq_no AS \"Seq No.\" ,\n" +  //9
				"months_pd AS \"Months PD\" ,\n" + //10 
				"days_pd AS \"Days PD\" , \n" + //11 
				"_get_particular_alias(part_id)AS \"Stage\" , \n" + //12 
				"amount_due AS \"Amount Due\" , \n" +  //13
				"last_paydate::DATE AS \"Last Paid Date\" ,\n" + //14 
				"default_date::DATE AS \"Default Date\" ,\n" +  //15
				"(CASE WHEN date_committed_2 IS NULL THEN date_committed_1 ELSE date_committed_2 END )::DATE AS \"Date Committed\",\n" + //16 
				"date_paid AS \"Date Paid\",\n" + //17
				"committed_amt_1 + COALESCE(committed_amt_2,0) AS \"Total Committed Amount\",\n" +  //18
				"COALESCE(total_amount_committed,0.00) AS \"Total Amount Paid\"\n" + //19 
				"FROM rf_pn_header a\n" +   
				"WHERE  pn_no = '"+_pn_no+"' \n" + 
				"AND status_id = 'A';";

		System.out.printf("SQL: %s%n%n", SQL); 
		return SQL;
	}

	public String SaveSettledAccount(String _pn_no, Date _date_paid,String _total_amount, Date _date_settled,String approved){

		return "UPDATE rf_pn_header\n" + 
				"SET (date_paid,total_amount_committed,date_settled,approved) = ( NULLIF('"+_date_paid+"','null')::TIMESTAMP,  NULLIF('"+_total_amount+"','')::NUMERIC,NULLIF('"+_date_settled+"',null)::TIMESTAMP,'"+approved+"')\n" + 
				"WHERE  pn_no = '"+_pn_no+"' \n" + 
				" AND status_id = 'A' ;\n" +  
				"";

	} 
	
	public  void totalCommitAmount(DefaultTableModel modelMain, DefaultTableModel modelTotal) {
		BigDecimal amount = new BigDecimal(0.00);

		for(int x=0; x < modelMain.getRowCount(); x++){
			try {
				amount = amount.add(((BigDecimal) modelMain.getValueAt(x, 1)));
			} catch (NullPointerException e1) {
				amount = amount.add(new BigDecimal(0.00));
			}
		}
		modelTotal.setValueAt(amount, 0, 1);
	}

	public Date dateFormat(String dates){

		DateFormat formatter ; 
		Date date = null ;  
		formatter = new SimpleDateFormat("yyyy-MM-dd");
		try {
			date = (Date)formatter.parse(dates);
		} catch (ParseException e) {
		}

 
		return date;
	}
	public String dateFormat_day(Date date){
		String strdate = null;
		DateFormat df = new SimpleDateFormat("dd");
		if (!(date == null)) {
			strdate = df.format(date); 
		}

		return strdate;
	}
	
	public String getIfPasDue(String entityID, String projID, String pblID, Integer seqNo, String buyerID){
		
		
		pgSelect db = new pgSelect();
		String SQL= "";
		
		System.out.println("select get_card_pmt_status('"+entityID+"', '"+projID+"', '"+pblID+"', '"+seqNo+"') = 'PAST DUE' ");
		
		db.select("select get_card_pmt_status('"+entityID+"', '"+projID+"', '"+pblID+"', '"+seqNo+"') = 'PAST DUE' ");
		
		Boolean ifPD = (Boolean) db.Result[0][0];
		
		/*
		if (ifPD) { 
			 SQL= "select \n" + 
					" sp_months_past_due('"+entityID+"', '"+projID+"', '"+pblID+"', '"+seqNo+"',current_date,false ),\n" + 
					" sp_days_past_due('"+entityID+"', '"+projID+"', '"+pblID+"', '"+seqNo+"',current_date,false),\n" + 
					" ROUND(sp_compute_amount_due('"+entityID+"', '"+projID+"', '"+pblID+"', '"+seqNo+"',current_date,false),2),\n" + 
					" to_char(_get_ledger_last_paiddate('"+entityID+"', '"+projID+"', '"+pblID+"', '"+seqNo+"',current_date),'yyyy-mm-dd') ,\n" + 
					"to_char(_get_default_date('"+entityID+"', '"+projID+"', '"+pblID+"', '"+seqNo+"',current_date),'yyyy-mm-dd')\n" + 
					"";
			
		}
		*/

		 SQL= "select \n" + 
				" sp_months_past_due('"+entityID+"', '"+projID+"', '"+pblID+"', '"+seqNo+"',current_date,false ),\n" + 
				" sp_days_past_due('"+entityID+"', '"+projID+"', '"+pblID+"', '"+seqNo+"',current_date,false),\n" + 
				" ROUND(sp_compute_amount_due('"+entityID+"', '"+projID+"', '"+pblID+"', '"+seqNo+"',current_date,false),2),\n" + 
				" to_char(_get_ledger_last_paiddate('"+entityID+"', '"+projID+"', '"+pblID+"', '"+seqNo+"',current_date),'yyyy-mm-dd') ,\n" + 
				"to_char(_get_default_date('"+entityID+"', '"+projID+"', '"+pblID+"', '"+seqNo+"',current_date),'yyyy-mm-dd')\n" + 
				"";
		
		return SQL;
		
	}
}
