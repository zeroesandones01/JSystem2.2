package Reports.CreditAndCollections;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import Functions.FncSystem;

public class _FCancellationActiveReport {

	public _FCancellationActiveReport() {
	}
	
	public String CancelledAccounts(String table_name, String proj_id,  String dateFrom , String dateTo,Boolean printall, Integer allaccounts) {

		String SQL = "";

		if (allaccounts == 0) {
			SQL = "SELECT \n" + 
					"false,\n" + 
					"get_unit_description(b.proj_id,b.pbl_id) as unit_pbl,\n" + 
					"_get_client_name(b.entity_id) as client_name,\n" + 
					"get_buyer_type_alias(a.entity_id,a.proj_id,a.pbl_id,a.seq_no) as buyer_type,\n" +
					"_get_sales_div(a.entity_id,a.pbl_id,a.seq_no) as sale_div,\n" + 
					"_get_client_contactno(a.entity_id) as contact_no,\n" + 
					"_get_house_model_alias(a.pbl_id) as house_model,\n" + 
					"_get_stb_date(a.entity_id,a.proj_id,a.pbl_id,a.seq_no) as stb_date,\n" + 
					"_get_bb_date(a.entity_id,a.proj_id,a.pbl_id,a.seq_no) as bb_date,\n" + 
					"_get_movein_date(a.entity_id,a.proj_id,a.pbl_id) as movein_date, \n" + 
					"_get_particular_alias(a.part_id) as stage,\n" + 
					"a.date_cancelled as date_cancelled,\n" + 
					"a.last_paid_date,\n" + 
					"b.months_past_due as months_pd,\n" + 
					"b.days_past_due as days_pd,\n" + 
					"_payments_made(b.entity_id,b.proj_id,b.pbl_id,b.seq_no) as months_paid, \n" + 
					"coalesce(b.amount_due,0.00) as amount_due, \n" + 
					"null as last_follow_up,\n" + 
					"b.entity_id, \n" + 
					"b.proj_id, \n" + 
					"b.pbl_id, \n" + 
					"b.seq_no, \n" + 
					"b.unit_id,\n" + 
					"b.part_id,\n" + 
					"_get_project_phase(b.proj_id,b.pbl_id) AS phase,\n" + 
					"'' as reason,\n" + 
					"'' as remark,\n" + 
					"_get_csv(b.entity_id,b.proj_id,b.pbl_id,b.seq_no),\n" + 
					"get_project_name(b.proj_id) as proj_name \n" + 
					"\n" + 
					"FROM \n" + 
					"(SELECT * FROM (SELECT DISTINCT ON (unit_id) * FROM rf_cancellation \n" + 
					"WHERE proj_id ='"+proj_id+"' ORDER BY unit_id,seq_no desc,cancel_rec_id desc)a WHERE date_cancelled is not null) a \n" + 
					"JOIN\n" + 
					"(SELECT * FROM ihf_due_accounts  WHERE  proj_id = '"+proj_id+"' \n" + 
					"AND date_updated = (select max(date_updated) FROM ihf_due_accounts)) b\n" + 
					"\n" + 
					"ON a.seq_no = b.seq_no AND a.pbl_id = b.pbl_id \n" + 
					(printall ? "--" : "" ) + "WHERE date_cancelled::date  BETWEEN  '"+dateFrom+"'::date AND '"+dateTo+"'::date \n" + 
					"UNION \n" + 
					"SELECT \n" + 
					"false,\n" + 
					"get_unit_description(b.proj_id,b.pbl_id) as unit_pbl,\n" + 
					"_get_client_name(b.entity_id) as client_name,\n" + 
					"get_buyer_type_alias(a.entity_id,a.proj_id,a.pbl_id,a.seq_no) as buyer_type,\n" +
					"_get_sales_div(a.entity_id,a.pbl_id,a.seq_no) as sale_div,\n" + 
					"_get_client_contactno(a.entity_id) as contact_no,\n" + 
					"_get_house_model_alias(a.pbl_id) as house_model,\n" + 
					"_get_stb_date(a.entity_id,a.proj_id,a.pbl_id,a.seq_no) as stb_date,\n" + 
					"_get_bb_date(a.entity_id,a.proj_id,a.pbl_id,a.seq_no) as bb_date,\n" + 
					"_get_movein_date(a.entity_id,a.proj_id,a.pbl_id) as movein_date, \n" + 
					"_get_particular_alias(a.part_id) as stage,\n" + 
					"a.date_cancelled as date_cancelled,\n" + 
					"a.last_paid_date,\n" + 
					"b.months_past_due as months_pd,\n" + 
					"b.days_past_due as days_pd,\n" + 
					"_payments_made(b.entity_id,b.proj_id,b.pbl_id,b.seq_no) as months_paid, \n" + 
					"coalesce(b.amount_due,0.00) as amount_due, \n" + 
					"null as last_follow_up,\n" + 
					"b.entity_id, \n" + 
					"b.proj_id, \n" + 
					"b.pbl_id, \n" + 
					"b.seq_no, \n" + 
					"b.unit_id,\n" + 
					"b.part_id,\n" + 
					"_get_project_phase(b.proj_id,b.pbl_id) AS phase,\n" + 
					"'' as reason,\n" + 
					"'' as remark,\n" + 
					"_get_csv(b.entity_id,b.proj_id,b.pbl_id,b.seq_no),\n" + 
					"get_project_name(b.proj_id) as proj_name \n" + 
					"\n" + 
					"FROM \n" + 
					"(SELECT * FROM (SELECT DISTINCT ON (unit_id) * FROM rf_cancellation \n" + 
					"WHERE proj_id ='"+proj_id+"'ORDER BY unit_id,seq_no desc,cancel_rec_id desc)a WHERE date_cancelled is not null) a \n" + 
					"JOIN\n" + 
					"(SELECT * FROM pagibig_due_accounts  WHERE  proj_id = '"+proj_id+"' \n" + 
					"AND date_updated = (select max(date_updated) FROM pagibig_due_accounts)) b\n" + 
					"\n" + 
					"ON a.seq_no = b.seq_no AND a.pbl_id = b.pbl_id \n" + 
					"\n" + 
					(printall ? "--" : "" ) + "WHERE date_cancelled::date  BETWEEN  '"+dateFrom+"'::date AND '"+dateTo+"'::date \n" + 
					"ORDER BY phase,client_name,unit_pbl;\n" + 
					"";

		}else{
			SQL = "SELECT \n" +  
					"false,\n" + 
					"get_unit_description(b.proj_id,b.pbl_id) as unit_pbl,\n" + 
					"_get_client_name(b.entity_id) as client_name,\n" + 
					"_get_sales_div(a.entity_id,a.pbl_id,a.seq_no) as sale_div,\n" + 
					"_get_client_contactno(a.entity_id) as contact_no,\n" + 
					"_get_house_model_alias(a.pbl_id) as house_model,\n" + 
					"_get_stb_date(a.entity_id,a.proj_id,a.pbl_id,a.seq_no) as stb_date,\n" + 
					"_get_bb_date(a.entity_id,a.proj_id,a.pbl_id,a.seq_no) as bb_date,\n" + 
					"_get_movein_date(a.entity_id,a.proj_id,a.pbl_id) as movein_date, \n" + 
					"_get_particular_alias(a.part_id) as stage,\n" + 
					"a.date_cancelled as date_cancelled,\n" + 
					"a.last_paid_date,\n" +  
					"b.months_past_due as months_pd,\n" + 
					"b.days_past_due as days_pd,\n" + 
					"_payments_made(b.entity_id,b.proj_id,b.pbl_id,b.seq_no) as months_paid, \n" + 
					"coalesce(b.amount_due,0.00) as amount_due, \n" + 
					"null as last_follow_up,\n" + 
					"b.entity_id, \n" + //17
					"b.proj_id, \n" + //18
					"b.pbl_id, \n" + //19
					"b.seq_no, \n" + //20
					"b.unit_id,\n" + //21
					"b.part_id,\n" + //22
					"_get_project_phase(b.proj_id,b.pbl_id) AS phase,\n" +  //23
					"'' as reason,\n" + //24
					"'' as remark,\n" + //25
					"_get_csv(b.entity_id,b.proj_id,b.pbl_id,b.seq_no),\n" + 
					"get_project_name(b.proj_id) as proj_name \n" +
					"\n" + 
					"FROM \n" + 
					"(SELECT * FROM (SELECT DISTINCT ON (unit_id) * FROM rf_cancellation \n" + 
					"WHERE proj_id ='"+proj_id+"' ORDER BY unit_id,seq_no desc,cancel_rec_id desc)a WHERE date_cancelled is not null) a \n" + 
					"JOIN\n" + 
					"(SELECT * FROM "+table_name+"  WHERE  proj_id = '"+proj_id+"' \n" + 
					"AND date_updated = (select max(date_updated) FROM "+table_name+")) b\n" + 
					"\n" + 
					"ON a.seq_no = b.seq_no AND a.pbl_id = b.pbl_id \n" +
					//(cmbprocess ==  1 ? "" : "--" ) + "WHERE _payments_made(b.entity_id, b.proj_id,b.pbl_id,b.seq_no) >= 24  AND a.csv_id is null -- for CSV only \n" +
					//"WHERE date_cancelled::date  BETWEEN  '"+dateFrom+"'::date AND '"+dateTo+"'::date \n" +
					(printall ? "--" : "" ) + "WHERE date_cancelled::date  BETWEEN  '"+dateFrom+"'::date AND '"+dateTo+"'::date \n" +
					
					"ORDER BY phase,client_name,unit_pbl;";
	
		}
		
		
		FncSystem.out("Client ", SQL);
			
		return SQL;
	}
	
	public String dateFormat(Date date){
		String strdate = null;
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		if (!(date == null)) {
			strdate = df.format(date);
		}

		return strdate;
	}
	
}
