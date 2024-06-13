package Projects.SalesandMarketing;

import Database.pgSelect;
import Functions.FncSystem;

public class _Trip_Ticket_Entry {

	static pgSelect db = new pgSelect();
	
	public static String getName(String entity_id){
		
		db.select("SELECT get_client_name('"+entity_id+"')");
		return (String) (db.Result[0][0] == null ? "" : db.Result[0][0]);
	}
	
	public static String getTripPurpose(Boolean driverassignor, Boolean div_head, String div_code){
		String SQL = "";
		
		
		
		if (driverassignor) {
			
			SQL = "SELECT purpose_code as \"Purpose Code\", purpose_desc as \"Desc.\", \n" + 
					"        ''::VARCHAR AS \"Approve Code\",\n" + 
					"        acct_id as \"Acct. ID\" FROM mf_tripping_purpose WHERE status_id='A'";
			
		}else if (div_head) {
			
			SQL = "SELECT purpose_code as \"Purpose Code\", purpose_desc as \"Desc.\", \n" + 
					"(select get_trip_purpose_approval(purpose_code,'"+div_code+"') ) AS  \"Approve Code\",\n" + 
					"acct_id as \"Acct. ID\"\n" + 
					"From mf_tripping_purpose \n" + 
					"WHERE allow_div_heads = true\n" + 
					"AND status_id='A'";
		}else{
			
			SQL = "SELECT purpose_code as \"Purpose Code\", purpose_desc as \"Desc.\", \n" + 
					"(select get_trip_purpose_approval(purpose_code,'"+div_code+"') ) AS \"Approve Code\",\n" + 
					"acct_id as \"Acct. ID\"\n" + 
					"From mf_tripping_purpose \n" + 
					"WHERE allow_group_heads = true\n" + 
					"AND status_id='A'\n" + 
					"";
		}
		
		FncSystem.out("SQL Trip Purpose", SQL);
		return SQL;
		
	}
	
	public String getMeetingPlace(){
		
		return "SELECT mp_code AS \"MP Code\", mp_desc AS \"Meeting Place\", tripping_rate_code AS \"Rate Code\" FROM mf_tripping_meeting_place";
	}
	
	public String lookupProjects(){

		return "----Query Project \n" +
				"SELECT proj_id as \" Project ID\", trim(proj_name) as \" Project Name\" from mf_project where status_id = 'A' order by proj_id";
	}

	public String getFinalApproval(){
		pgSelect db = new pgSelect();
		
		db.select("SELECT  tripticket_final_approving_officer FROM mf_system_parameters limit 1");
		
		return db.Result[0][0].toString();
		
	}
	
	public String getTicketNo(){
		pgSelect db = new pgSelect();
		
		db.select("SELECT to_char(COALESCE(MAX(ticket_no::INT), 0) + 1, 'FM0000000000') from rf_tripticket_header");
		return (String) db.Result[0][0];
		
	}
	
	public String getRequested(){
		
		return "select emp_code as \"Emp. Code\", \n" + 
				"entity_id As \"Entity ID\", \n" + 
				"get_client_name(entity_id) AS \"Emp. Name\",\n" + 
				"division_name AS \"Div. Name\",\n" + 
				"dept_name as \"Dept. Name\",\n" + 
				"''::VARCHAR AS \"BNO\",\n" + 
				"div_code AS \"Div. Code\",\n" + 
				"a.dept_code  AS \"Dept. Code\",\n" + 
				"''::VARCHAR AS \"COORD\",\n" + 
				"_get_client_mobileno(entity_id) As \"Mobile\"\n" + 
				"\n" + 
				"\n" + 
				"from em_employee a\n" + 
				"left join mf_division b on b.division_code = a.div_code\n" + 
				"left join mf_department c on c.dept_code = a.dept_code\n" + 
				"where emp_status != 'I'";
	}
	
	public String getAgent(){
		
		String SQL = "";
		
		SQL = "select * from get_tripping_selling_unit()";
			/*
			"SELECT TRIM(a.entity_id)::VARCHAR as \"Entity ID\" , "
				+"TRIM(a.entity_name) AS \"Agent Name\" , "
				+"TRIM(c.division_code)::VARCHAR AS \"Div. Code\", "
				+"TRIM(c.division_name) AS \"Div Name\" ,\n" + 
				"TRIM(c.division_alias) AS \"Div. Alias\", "
				+"TRIM(b.dept_id)::VARCHAR as \"Dept ID\", "
				+"TRIM(d.dept_name)  As \"Dept. Name\",\n" + 
				"_get_client_mobileno(a.entity_id) As \"Mobile\"\n" + 
				"\n" + 
				"FROM rf_entity a\n" + 
				"\n" + 
				"JOIN (select * from mf_sellingagent_info where status_id = 'A' " +
				"and salespos_id not in ('004','009')) b on b.agent_id = a.entity_id\n" + 
				"LEFT  JOIN mf_division c on c.division_code = b.sales_div_id\n" + 
				"LEFT  JOIN mf_department d on b.dept_id = d.dept_code\n" + 
				"where bdo_id is not null\n" + 
				//"and exists (select * from rf_entity_id_no where entity_id = a.entity_id and tin_no is not null and prc_id is not null and hlurb_regist_no is not null )\n" + 
				"ORDER BY TRIM(a.entity_name)\n" + 
				"" ;
			*/
		return SQL;
		
	}
	
	public String getTicketEntry(){
		String SQL = "	select ticket_no AS \"Ticket No.\",\n" + 
				"	get_client_name(requested_by) AS \"Requested By\",\n" + 
				"	add_date as \"Requested Date\", \n" + 
				"	tripping_date AS \"Tripping Date\",\n" + 
				"	get_client_name(driver_code) AS \"Driver Name\"\n" + 
				"	from  rf_tripticket_header \n" + 
				"	where status_id = 'A'\n" + 
				"	and computed_by is null order by ticket_no\n" + 
				"";
		
		return SQL;
	}
	
	public String getTicketEntryEdit(){
		String SQL = "	select ticket_no AS \"Ticket No.\",\n" + 
				"	get_client_name(requested_by) AS \"Requested By\",\n" + 
				"	add_date as \"Requested Date\", \n" + 
				"	tripping_date AS \"Tripping Date\",\n" + 
				"	get_client_name(driver_code) AS \"Driver Name\"\n" + 
				"	from  rf_tripticket_header \n" + 
				"	where status_id = 'A'\n" + 
				"	and computed_by is not null order by ticket_no \n" + 
				"";
		
		return SQL;
	}

}
