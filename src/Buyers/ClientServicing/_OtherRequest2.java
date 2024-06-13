package Buyers.ClientServicing;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import Database.pgSelect;
import Database.pgUpdate;
import Functions.FncSystem;
import Functions.UserInfo;

public class _OtherRequest2 {

	public _OtherRequest2() {

	}

	public static Object[] listRequest(){//RETURNS THE REQUEST TYPES TO BE SELECTED

		String	SQL = "SELECT FORMAT('%s - %s (%s)', TRIM(request_id), TRIM(request_desc), TRIM(byrstatus_id))\n" + 
				"FROM mf_request_type\n" + 
				"WHERE request_id IN ('01','02', '03', '04', '05', '06', '07' ,'08', '12' ,'13' ,'15', '16', '25')\n" + 
				"ORDER BY rqst_seq;";

		pgSelect db = new pgSelect();
		db.select(SQL);
		//FncSystem.out("Display listRequest %S%n", SQL);
		
		ArrayList<Object> listRequest = new ArrayList<Object>();
		if(db.isNotNull()){
			listRequest.add("PLEASE SELECT REQUEST");
			for(int x=0; x < db.getRowCount(); x++){
				listRequest.add(db.getResult()[x][0]);
			}
			return listRequest.toArray();
		}else{
			return null;
		}
	}

	public static String selectedReq(String req_no){//RETURNS THE SELECTED REQUEST BASED ON THE REQUEST NO
		String sql = "select FORMAT('%s - %s (%s)', trim(a.req_type_id), trim(b.request_desc), trim(b.byrstatus_id))\n" + 
				"from req_rt_request_header a\n" + 
				"left join mf_request_type b on b.request_id = a.req_type_id\n" + 
				"where a.request_no = '"+req_no+"' \n";
		//"and a.old_entity_id = '0000000004'";
		pgSelect db = new pgSelect();
		db.select(sql);
		FncSystem.out("Display SQL for Selected Request", sql);

		return (String) db.getResult()[0][0];
	}

	/**SQL'S FOR THE LOOKUPS***/
	public static String sqlEntityLookUp(){//SQL FOR THE ENTITY LOOKUP IN THE OLD DATA

		return "select trim(a.entity_id) as \"Entity ID\", get_client_name(a.entity_id) as  \"Name\", \n" + 
		"trim(a.pbl_id) as \"PBL\", a.seq_no as \"Seq. No\", \n" + 
		"trim(a.projcode) as \"Proj. Code\", trim(e.proj_name) as \"Project Name\",\n" + 
		"trim(c.unit_id) as \"Unit ID\", trim(c.description) as \"Description\",\n" + 
		"trim(a.currentstatus) as \"Status ID\", trim(d.status_desc) as \"Unit Status\",\n" + 
		"trim(a.pmt_scheme_id) as \"Pmt ID\" ,trim(f.pmt_scheme_desc) as \"Payment Scheme\"\n" + 
		"from rf_sold_unit a\n" + 
		"left join rf_entity b on b.entity_id = a.entity_id\n" + 
		"left join mf_unit_info c on c.pbl_id = a.pbl_id and c.proj_id = a.projcode \n" + 
		"left join mf_buyer_status d on d.byrstatus_id = a.currentstatus\n" + 
		"left join mf_project e on e.proj_id = a.projcode\n" + 
		"left join mf_payment_scheme f on f.pmt_scheme_id = a.pmt_scheme_id\n" + 
		"left join mf_product_model g on g.model_id = a.model_id\n" + 
		"left join mf_buyer_type h on h.type_id = a.buyertype\n" + 
		//"where d.status_desc != 'Cancelled'\n" + 
		"order by get_client_name(a.entity_id)";

		/*return "select trim(a.entity_id) as \"Entity ID\", get_client_name(a.entity_id) as \"Name\",\n" + 
				"trim(a.pbl_id) as \"PBL\", a.seq_no as \"Seq. No\",\n" + 
				"trim(a.projcode) as \"Proj. Code\", trim(get_project_name(a.projcode)) as \"Project Name\",\n" + 
				"trim(get_unit_id(a.projcode, a.pbl_id)) as \"Unit ID\", trim(get_unit_description(a.projcode, a.pbl_id)) as \"Description\",\n" + 
				"trim(c.byrstatus_id) as \"Status ID\", trim(d.status_desc) as \"Status Desc\", \n" + 
				"trim(a.pmt_scheme_id) as \"Pmt ID\", trim(b.pmt_scheme_desc) as \"Payment Scheme\"\n" + 
				"from rf_sold_unit a\n" + 
				"left join mf_payment_scheme b on b.pmt_scheme_id = a.pmt_scheme_id\n" + 
				"left join rf_buyer_status c on c.entity_id = a.entity_id and c.pbl_id = a.pbl_id and c.proj_id = a.projcode and c.seq_no = a.seq_no and c.tran_date = (SELECT max(tran_date) FROM rf_buyer_status where entity_id = a.entity_id and pbl_id = a.pbl_id and proj_id = a.projcode and seq_no = a.seq_no)\n" + 
				"left join mf_buyer_status d on d.byrstatus_id = c.byrstatus_id\n" + 
				"order by get_client_name(a.entity_id)";*/
	}

	public static String sqlSearch(){ //SQL FOR THE SEARCH OF REQUESTS IN THE SEARCH BUTTON

		return "select trim(a.request_no) as \"Req. No\",\n" + 
		"trim(a.old_entity_id) as \"Entity ID\",\n" + 
		"trim(a.old_entity_name) as \"Entity Name\",\n" + 
		"trim(a.old_proj_id) as \"Proj. ID\", \n" + 
		"trim(getinteger(a.old_unit_id)::VARCHAR) as \"PBL\",\n" + 
		"trim(a.old_unit_id) as \"Unit ID\",\n" + 
		"trim(b.description) as \"Unit Description\",\n" + 
		"a.old_seq_no as \"Seq. No\",\n" + 
		"trim(c.req_status_desc) as \"Req Status\",\n" + 
		"a.request_date as \"Req Date\"\n" + 
		"from req_rt_request_header a\n" + 
		"left join mf_unit_info b on b.unit_id = a.old_unit_id\n" + 
		"left join mf_request_status c on c.req_status_id = a.request_status\n" + 
		"where a.old_entity_id != '' \n"+
		"and req_type_id not in ('Credit of Payment', 'Refund of Payment', 'TECHNICAL DOCUMENTS')\n" + 
		"order by a.old_entity_id";
	}

	public static String sqlOpenUnits(Object [] data1){//SQL FOR THE LOOKUP OF NEW UNIT//XXX FIX ME
		String proj_id = (String) data1[4];
		String unit_id = (String) data1[5];

		return "SELECT TRIM(a.unit_id) AS \"Unit ID\", TRIM(a.description) AS \"Unit Description\",\n" + 
			   "TRIM(get_project_name(a.proj_id)) AS \"Project\", TRIM(a.model_id) AS \"Model ID\", \n" + 
			   "TRIM(get_model_desc(a.model_id)) AS \"Model Name\", (a.lotarea)::VARCHAR AS \"Lot Area\"\n" + 
			   "FROM mf_unit_info a\n" + 
			   "LEFT JOIN rf_marketing_scheme_main b ON b.proj_id = a.proj_id AND b.sub_proj_id = a.sub_proj_id\n" + 
			   "WHERE a.status_id = 'A'\n" + 
			   "AND a.proj_id = '"+proj_id+"' \n" + 
			   "AND a.unit_id != '"+unit_id+"' \n" + 
			   "AND b.status_id = 'P' \n"+
			   "AND a.pbl_id not in (select pbl_id from rf_sold_unit where projcode = '"+proj_id+"' and currentstatus != '02')\n" + 
			   "ORDER BY a.unit_id;";
	}

	public static String sqlClientClass(String req_id, Object []data1, String new_unit_id){//SQL FOR THE CLIENT CLASS BASED ON OTHER SELECTED REQUEST TYPES
		String sqlClientClass = ""; 
		String old_entity_class = (String) data1[2];
		String unit_id = (String) data1[5];

		pgSelect db = new pgSelect();
		if(req_id.equals("06")){
			String sql = "select proj_id, model_id from mf_unit_info where unit_id = '"+new_unit_id+"'";
			db.select(sql);
		}else{
			String sql = "select proj_id, model_id from mf_unit_info where unit_id = '"+unit_id+"'";
			db.select(sql);
		}

		if(db.isNotNull()){
			String proj_code = (String) db.getResult()[0][0];
			String model_id = (String) db.getResult()[0][1];

			if(req_id.equals("06")){//if transfer of unit is the request
				sqlClientClass = "select distinct(a.unnest) as \"Type ID\", trim(b.type_desc) as \"Description\" \n" + 
						"from(select unnest(type_id) \n" + 
						"from mf_payment_scheme\n" + 
						"where status_id = 'A'\n" + 
						"and '"+proj_code+"' = ANY(proj_id)\n" + 
						"and '"+model_id+"' = ANY(model_id)) a\n" + 
						"left join mf_buyer_type b on b.type_id = a.unnest\n" + 
						"where a.unnest = b.type_id";
			}else{
				sqlClientClass = "select distinct(a.unnest) as \"Type ID\", trim(b.type_desc) as \"Description\"\n" + 
						"from (select unnest(type_id) from \n" + 
						"mf_payment_scheme\n" + 
						"where status_id = 'A'\n" + 
						"and '"+proj_code+"' = ANY(proj_id)\n" + 
						"and '"+model_id+"' = ANY(model_id)) a\n" + 
						"left join mf_buyer_type b on b.type_id = a.unnest\n" + 
						"where a.unnest != '"+old_entity_class+"'";
			}
		}
		FncSystem.out("Display the sql for the client class", sqlClientClass);
		return sqlClientClass;
	}

	public static String sqlChangePrincipalApp(Object []data1){//SQL FOR LOOKUP OF CHANGE PRINCIPAL APPLICANT
		String old_entity_id = (String) data1[0];
		return "select trim(entity_id) as \"Entity ID\", trim(entity_name) as \"Name\" from rf_entity where entity_id != '"+old_entity_id+"'";
	}

	public static String sqlChangeAgent(String req_id ,Object [] data1){ //SET THE SALES GROUP HERE TO BE
		String old_agent = (String) data1[3];//33 

		String sqlAgent = "";

		if(req_id.equals("07") || req_id.equals("12")){ //For Reapplication
			sqlAgent = "select division_code as \"Div. Code\", trim(division_name) as \"Div. Name\" from mf_division where division_code in ('06', '07', '08', '09', '29')\n";
		}else{ //For Change of Selling Agent
			sqlAgent = "select trim(a.entity_id) as \"Agent ID\", trim(a.entity_name) as \"Agent Name\", trim(c.division_code) as \"Division ID\", trim(c.division_name) as \"Division Name\", \n" + 
					"trim(c.division_alias) as \"Division Alias\" \n" + 
					"from rf_entity a\n" + 
					"inner join rf_sellingagent_info b on b.agent_id = a.entity_id\n" + 
					"inner join mf_division c on c.division_code = b.div_code\n" + 
					"where a.entity_id in (select entity_id from rf_entity_assigned_type where entity_type_id in \n" + 
					"(select entity_type_id from mf_entity_type where entity_type_desc ~*'agent'))\n"+
					"and b.agent_id != '"+old_agent+"'";
		}

		return sqlAgent;
	}

	public static String sqlPmtScheme(String req_id , String new_unit_id, String new_entity_class, Object []data1){///SQL FOR THE LOOKUP OF PAYMENT SCHEME WHERE IT IS BASED ON THE UNIT ID
		String old_entity_class = (String) data1[2];
		String old_unit_id = (String) data1[5];

		pgSelect db = new pgSelect();
		if(req_id.equals("06")){
			String sql = "select proj_id,sub_proj_id, model_id from mf_unit_info where unit_id  = '"+new_unit_id+"'";
			db.select(sql);
		}else{
			String sql = "select proj_id,sub_proj_id, model_id from mf_unit_info where unit_id  = '"+old_unit_id+"'";
			db.select(sql);
			//FncSystem.out("Display Me", sql);
		}

		String proj_id = (String) db.getResult()[0][0];
		String sub_proj_id = (String) db.getResult()[0][1];
		String model_id = (String) db.getResult()[0][2];

		return  "select trim(pmt_scheme_id) as \"ID\", trim(pmt_scheme_desc) as \"Description\" " +
		"from mf_payment_scheme " +
		"where '"+proj_id+"' = ANY(proj_id) " + 
		"and '"+model_id+"' = ANY(model_id) " +
		"and (case when '"+req_id+"' in('06', '02') then '"+new_entity_class+"' else '"+old_entity_class+"' end) = ANY(type_id) " + //TRY FINAL ENTITY CLASS HERE
		"and '"+sub_proj_id+"' = ANY(phase) \n"+
		"and status_id = 'A' \n"+
		"and pmt_scheme_id != '028' \n"+
		"order by pmt_scheme_id";
	}

	public static String sqlHouseModel(Object []data1){//SQL FOR THE LOOKUP OF HOUSE MODELS BASED ON UNIT ID

		String old_unit_id = (String) data1[5];

		return "select distinct on(model_desc) model_id as \"Model ID\", model_desc as \"Model\", "+
		"model_cost as \"Model Cost\", fire_insurance as \"Fire Insurance\", floor_area as \"Floor Area\" "+
		"from mf_product_model\n" + 
		"where status_id  = 'A' and floor_area <= (select lotarea from mf_unit_info where unit_id = '"+old_unit_id+"') order by model_desc\n";

	}

	/***END OF SQL'S FOR THE LOOKUPS****/

	public static String getRequestNo(){//Get the next request no from req_rt_request_header 
		//Kelangan parehas lahat ng Date and time sa PC ng lahat ng magcreate ng request para hindi mag double yung request no

		pgSelect db = new pgSelect();
		String req_no = "";

		String sql = "select trim(get_new_request_no())";

		db.select(sql);
		//FncSystem.out("Display Next request No", sql);
		if(db.isNotNull()){
			req_no = (String) db.getResult()[0][0];
		}
		return req_no;
	}

	public static Date getReserDate(String entity_id, String unit_id, Integer limit){ //SETTING OF RESERVATION DATE

		String sql = "select scheddate::date from \n" + 
					 "(select * from rf_client_schedule where entity_id = '"+entity_id+"'\n" + 
					 "and unit_id  = '"+unit_id+"'\n" + 
					 "and seq_no  = (select seq_no from rf_sold_unit where entity_id  = '"+entity_id+"' and pbl_id = getinteger('"+unit_id+"')::VARCHAR limit 1) \n" + 
					 "and part_id  = '012'\n" + 
					 "order by scheddate \n" + 
					 "limit "+limit+")a order by scheddate desc\n" + 
					 "limit 1\n";

		pgSelect db = new pgSelect();
		db.select(sql);
		FncSystem.out("Display get reservation", sql);
		if(db.isNotNull()){
			return (Date) db.getResult()[0][0];
		}else{
			return null;
		} //TRY get_reservation_date(entity_id, pbl_id, seq_no, proj_id, limit) here
	}

	public static Object[] displayNewData(String req_no){//DISPLAYS THE NEW DATA FROM THE REQUEST based on the request no
		String sql = "select\n" + 
				"a.request_by, \n" + 
				"a.request_remarks, \n" + 
				"a.new_dp_duedate, \n" + 
				"a.new_ma_duedate, \n" + 
				"a.new_entity_id, \n" + 
				"get_client_name(a.new_entity_id), \n" + 
				"a.new_last_name, \n" + 
				"a.new_first_name, \n" + 
				"a.new_mi_name, \n" + 
				"a.new_entity_class, \n" + 
				"b.type_desc, \n" + 
				"a.new_agent, \n" + 
				"coalesce(get_client_name(a.new_agent), (select division_name from mf_division where division_code = a.new_agent)), \n" + 
				"a.new_proj_id, \n" + 
				"get_project_name(a.new_proj_id),\n" + 
				"a.new_unit_id, \n" + 
				"get_unit_description(a.new_unit_id),\n" + 
				"a.new_sprice, \n" + 
				"a.new_pmt_scheme_id, \n" + 
				"c.pmt_scheme_desc,\n" + 
				"a.new_model, \n" + 
				"d.model_desc, \n" + 
				"coalesce(a.new_disc, 0.00), \n" + 
				"a.new_ma_term, \n" + 
				"a.new_dp_term, \n" + 
				"a.new_dp_amount, \n" + 
				"a.new_availed, \n" + 
				"a.new_dp_rate, \n" + 
				"a.new_availed_rate, \n" + 
				"a.new_disc_rate, \n"+
				"trim(a.new_lotarea::char(20)), \n"+
				"a.new_fire, \n"+
				"a.old_entity_class, \n"+
				"e.civil_status_desc , a.req_purpose \n"+
				"from req_rt_request_header a\n" + 
				"left join mf_buyer_type b on b.type_id = a.new_entity_class \n" + 
				"left join mf_payment_scheme c on c.pmt_scheme_id = a.new_pmt_scheme_id \n" + 
				"left join mf_product_model d on d.model_id = a.new_model \n"+
				"left join mf_civil_status e on e.civil_status_code = a.new_civil_status \n" + 
				"where request_no = '"+req_no+"'";
		pgSelect db = new pgSelect();
		FncSystem.out("Display New Data", sql);
		db.select(sql);
		//if(db.isNotNull()){
		String req_by = (String) db.getResult()[0][0];
		String req_remarks = (String) db.getResult()[0][1];
		Date new_dp_duedate = (Date) db.getResult()[0][2];
		Date new_ma_duedate = (Date) db.getResult()[0][3];
		String new_entity_id = (String) db.getResult()[0][4];
		String new_entity_name = (String) db.getResult()[0][5];
		String new_lname = (String) db.getResult()[0][6];
		String new_fname = (String) db.getResult()[0][7];
		String new_mname = (String) db.getResult()[0][8];
		String new_entity_class = (String) db.getResult()[0][9];
		String new_entity_class_desc = (String) db.getResult()[0][10];
		String new_agent = (String) db.getResult()[0][11];
		String new_agent_name = (String) db.getResult()[0][12];
		String new_proj_id = (String) db.getResult()[0][13];
		String new_proj_name = (String) db.getResult()[0][14];
		String new_unit_id = (String) db.getResult()[0][15];
		String new_unit_desc = (String) db.getResult()[0][16];
		BigDecimal new_sprice = (BigDecimal) db.getResult()[0][17];
		String new_pmt_scheme_id = (String) db.getResult()[0][18];
		String new_pmt_scheme_name = (String) db.getResult()[0][19];
		String new_model_id = (String) db.getResult()[0][20];
		String new_model_desc = (String) db.getResult()[0][21];
		BigDecimal new_disc_amt = (BigDecimal) db.getResult()[0][22];
		Integer new_materm = (Integer) db.getResult()[0][23];
		Integer new_dpterm = (Integer) db.getResult()[0][24];
		BigDecimal new_dp_amt = (BigDecimal) db.getResult()[0][25];
		BigDecimal new_availed_amt = (BigDecimal) db.getResult()[0][26];
		BigDecimal new_dp_rate = (BigDecimal) db.getResult()[0][27];
		BigDecimal new_availed_rate = (BigDecimal) db.getResult()[0][28];
		BigDecimal new_disc_rate = (BigDecimal) db.getResult()[0][29];
		String new_lotarea = (String) db.getResult()[0][30];
		BigDecimal new_fire_amt = (BigDecimal) db.getResult()[0][31];
		String old_entity_class = (String) db.getResult()[0][32];
		String new_civil_status = (String) db.getResult()[0][33];
		String purpose = (String) db.getResult()[0][34];

		return new Object[]{req_by, req_remarks, new_dp_duedate, new_ma_duedate, new_entity_id, new_entity_name, new_lname,
				new_fname, new_mname, new_entity_class, new_entity_class_desc, new_agent, new_agent_name, new_proj_id,
				new_proj_name, new_unit_id, new_unit_desc, new_sprice, new_pmt_scheme_id, new_pmt_scheme_name, new_model_id,
				new_model_desc, new_disc_amt, new_materm, new_dpterm, new_dp_amt, new_availed_amt, new_dp_rate, new_availed_rate, 
				new_disc_rate, new_lotarea, new_fire_amt, new_civil_status, purpose};

	}

	/***START OF SAVING**/
	//SAVING OF THE REQUEST with the selected request type
	public static void saveRtRequestHeader(String req_no, String req_client, String remarks, Object[] data3){ 
		String old_entity_id = (String) data3[30];
		String old_entity_name = (String) data3[31];
		String old_entity_class = (String) data3[32];
		String old_agent_id = (String) data3[33];
		String old_proj_id = (String) data3[34]; 
		String old_unit_id = (String) data3[35];
		BigDecimal old_sprice = (BigDecimal) data3[36]; 
		String old_pmt_schme_id = (String) data3[37];
		/*String old_dp_due_date = (String) data3[38]; 
		String old_ma_due_date = (String) data3[39]; */
		Date old_dp_due_date = (Date) data3[38]; 
		Date old_ma_due_date = (Date) data3[39]; 
		String old_model_id = (String) data3[40]; 
		BigDecimal old_disc_amt = (BigDecimal) data3[41];
		//Integer old_materm = (Integer) data3[42]; 
		String old_materm = (String) data3[42];
		//Integer old_dpterm = (Integer) data3[43];
		String old_dpterm = (String) data3[43];
		BigDecimal old_dp_amt = (BigDecimal) data3[44];
		BigDecimal old_availed_amt = (BigDecimal) data3[45]; 
		Date start_date = (Date) data3[50];
		Date end_date = (Date) data3[51];
		BigDecimal old_fire_amt = (BigDecimal) data3[54];
		String req_id = (String) data3[60];
		String new_lname = (String) data3[62];
		String new_fname = (String) data3[63];
		String new_mname = (String) data3[64]; 
		String new_agent = (String) data3[65];
		BigDecimal new_dp_rate = (BigDecimal) data3[75];
		BigDecimal new_availed_rate = (BigDecimal) data3[78];
		String new_civil_status = (String) data3[90];
		String purpose = (String) data3[91];

		//System.out.printf("Display civil stat %s%n", new_civil_status);

		Date new_dp_duedate = null;
		Date new_ma_duedate = null;

		if(req_id.equals("01")){ //XXX FOR CHANGE OF DUE DATE
			new_dp_duedate = (Date) data3[80];
			new_ma_duedate = (Date) data3[81]; 
		}

		String final_entity_id = (String) data3[0];
		String final_unit_id = (String) data3[5];
		String final_model_id = (String) data3[6];
		String final_agent_id = (String) data3[7];
		String final_entity_class = (String) data3[8];
		String final_pmt_scheme = (String) data3[9];
		BigDecimal final_sprice = (BigDecimal) data3[10];
		BigDecimal final_disc_amt = (BigDecimal) data3[11];
		BigDecimal final_disc_rate = (BigDecimal) data3[12];
		BigDecimal final_dp_amt = (BigDecimal) data3[16];
		BigDecimal final_availed_amt = (BigDecimal) data3[18];
		//Integer final_dpterm = (Integer) data3[20];
		String final_dpterm = (String) data3[20];
		//Integer final_materm = (Integer) data3[21];
		String final_materm = (String) data3[21];
		String final_merge_unit_id = (String) data3[24];
		String final_seq_no = (String) data3[26];
		BigDecimal final_fire_amt = (BigDecimal) data3[27];
		String old_seq_no = (String) data3[56];

		pgUpdate db = new pgUpdate();

		if(hasMergeUnit(old_unit_id) == false){
			final_merge_unit_id = "";
		}

		if(data3[20].equals("")){
			final_dpterm = null;
		}
		if(data3[21].equals("")){
			final_materm = null;
		}

		//System.out.printf("Display final_disc_amt: %s%n", final_disc_amt);

		String sql = "INSERT INTO req_rt_request_header \n"+
				"VALUES (coalesce((select max(rec_id) + 1 from req_rt_request_header),1), \n"+ //rec_id
				"'"+req_no+"', \n"+ //req_no
				"now(), \n"+ //request_date
				"'S', \n"+ //req_status
				"null, \n"+//approved_date
				"'"+req_client+"' , \n" +//req_client 
				"'"+remarks+"', \n"+ //remarks
				"'"+old_entity_id+"', \n"+ //old_entity_id
				"'"+old_entity_name+"', \n"+ //old_entity_name
				"'"+old_entity_class+"' , \n" + //old_entity_class
				"(select dept_id from rf_sellingagent_info where agent_id = '"+old_agent_id+"'), \n"+ //old_mktgarm
				"'"+old_agent_id+"', \n"+ //old_agent
				"'"+old_proj_id+"', \n" + //old_proj_id
				"'"+old_unit_id+"', \n"+ //old_unit_id
				""+old_seq_no+", \n" + //old_seq_no 
				""+old_sprice+", \n"+ //old_sprice
				"'"+old_pmt_schme_id+"', \n" + //old_pmt_scheme_id
				"(case when is_full_dp_paid('"+old_entity_id+"', '"+old_unit_id+"', "+old_seq_no+", '"+old_proj_id+"') = true then nullif('"+start_date+"', 'null')::TIMESTAMP else nullif('"+old_dp_due_date+"', 'null')::TIMESTAMP end), \n"+ //old_dp_duedate
				"(case when is_full_settled('"+old_entity_id+"', '"+old_unit_id+"', "+old_seq_no+", '"+old_proj_id+"') = true then nullif('"+end_date+"', 'null')::TIMESTAMP else nullif('"+old_ma_due_date+"', 'null')::TIMESTAMP end), \n"+//old_ma_duedate
				"0.00, \n" + //old_amt_reqd
				"nullif('"+new_dp_duedate+"', 'null')::TIMESTAMP, \n"+ //final_dp_duedate 
				"nullif('"+new_ma_duedate+"', 'null')::TIMESTAMP, \n"+ //final_ma_duedate 
				"'"+old_model_id+"', \n"+//old_model
				"(select lotarea from mf_unit_info where unit_id = '"+old_unit_id+"'), \n"+ //old_lot_area
				"(select floor_area from mf_product_model where model_id = '"+old_model_id+"'), \n" + //old_flooarea
				""+old_disc_amt+", \n"+ //old_disc_amt
				"'"+final_entity_id+"' ,\n"+
				"(case when '"+req_id+"' in ('03', '25') then '"+new_lname+"' when '"+req_id+"' = '05' then (select last_name from rf_entity where entity_id = '"+final_entity_id+"') else (select last_name from rf_entity where entity_id = '"+old_entity_id+"') end), \n"+ //final_last_name
				"(case when '"+req_id+"' in ('03', '25') then '"+new_fname+"' when '"+req_id+"' = '05' then (select first_name from rf_entity where entity_id = '"+final_entity_id+"') else (select first_name from rf_entity where entity_id = '"+old_entity_id+"') end), \n"+ //final_first_name
				"(case when '"+req_id+"' in ('03', '25') then '"+new_mname+"' when '"+req_id+"' = '05' then (select middle_name from rf_entity where entity_id = '"+final_entity_id+"') else (select middle_name from rf_entity where entity_id = '"+old_entity_id+"') end), \n" +//final_middle_name 
				"'"+final_entity_class+"', \n"+ //final_entity_class
				"(select dept_id from rf_sellingagent_info where agent_id = '"+final_agent_id+"'), \n"+ //final_mktgarm
				"'"+final_agent_id+"', \n"+//final_agent
				"'"+old_proj_id+"', \n"+ //old_proj_id
				"'"+final_unit_id+"', \n" +//final_unit_id 
				""+final_seq_no+", \n"+ //final_seq no //XXX CHECK ME IF TO ADD 1 FOR REAPPLICATION REQUEST
				""+final_sprice+", \n"+ 
				"'"+final_pmt_scheme+"', \n"+ //new_pmt_schme_id
				"null, \n"+ //new_start_date 
				"null, \n"+ //new_due_day
				"null, \n"+ //new_amt_reqd
				"'"+final_model_id+"', \n"+ //final_model_id
				""+final_disc_amt+", \n"+ //final_disc_amt
				"(select lotarea from mf_unit_info where unit_id = '"+final_unit_id+"'), \n"+ //new_lotarea
				"(select floor_area from mf_product_model where model_id = '"+final_model_id+"'), \n" + //new_floorarea
				"null, \n"+ //xchange_unit
				"null, \n"+ //new_cts_no
				"null, \n"+ //jv_no
				"null, \n"+ //pagibig_cont
				"null, \n" + //client_seqno
				""+old_materm+", \n"+ //old_ma_term 
				""+final_materm+", \n"+ //new_ma_term
				""+old_dpterm+", \n"+ //old_dp_term
				""+final_dpterm+", \n"+ //new_dp_term 
				""+old_dp_amt+", \n" +//old_dp_amt 
				""+final_dp_amt+", \n"+ //new_dp_amt
				""+old_availed_amt+", \n"+ //old_availed
				""+final_availed_amt+", \n"+ //final_availed
				"null, \n"+ //start_pi_cont
				"null, \n"+ //rplf_no
				"null, \n"+ //sold_unit_id
				"'"+UserInfo.EmployeeCode+"', \n"+ //add_by
				"now(), \n"+ //add_date
				"null, \n"+ //approved_by
				""+old_fire_amt+", \n" + //old_fire_amt
				""+final_fire_amt+", \n" +//final fire amt
				"'"+req_id+"', \n" +//selected request type
				""+new_dp_rate+", \n"+ //new_dp_rate
				""+new_availed_rate+", \n"+ //new_availed_rate
				"'"+affectSched(req_id)+"', \n"+
				""+final_disc_rate+", \n" + 
				"(select civil_status_code from rf_entity where entity_id = '"+old_entity_id+"'), \n"+
				"(case when '"+req_id+"' != '25' then (select civil_status_code from rf_entity where entity_id = '"+old_entity_id+"') else '"+new_civil_status+"' end) ,\n"+
				"'"+hasMergeUnit(old_unit_id)+"', \n"+//with merge unit
				"'"+final_merge_unit_id+"', \n"+
				"false, '"+purpose+"') \n";

		db.executeUpdate(sql, true);
		db.commit();
		//FncSystem.out("Insert to Request Header", sql);
	}

	public static void saveRequestDetails(){//XXX CHECK IF WORKING
		pgSelect db = new pgSelect();
		String sql ="select sp_save_request_details('0000000145', 'TVR0000024', '015', 1, get_new_request_no(), '"+UserInfo.EmployeeCode+"')";
		db.select(sql, "Save", true);
	}

	public static void updateRequestDetails(Object [] data3, String req_no){//UPDATES THE REQUEST DETAILS BASED ON THE REQUEST NO
		String req_id = (String) data3[60];  //XXX updates the PURPOSE 
		String new_lname = (String) data3[62];
		String new_fname = (String) data3[63];
		String new_mname = (String) data3[64]; 
		BigDecimal new_dp_rate = (BigDecimal) data3[75];
		BigDecimal new_availed_rate = (BigDecimal) data3[78];
		String old_proj_id = (String) data3[34];
		String new_civil_status = (String) data3[90];
		String purpose = (String) data3[91];

		Date new_dp_duedate = null;
		Date new_ma_duedate = null;

		if(req_id.equals("01")){
			new_dp_duedate = (Date) data3[80];
			new_ma_duedate = (Date) data3[81]; 
		}

		String final_entity_id = (String) data3[0];
		String final_unit_id = (String) data3[5];
		String final_model_id = (String) data3[6];
		String final_agent_id = (String) data3[7];
		String final_entity_class = (String) data3[8];
		String final_pmt_scheme = (String) data3[9];
		BigDecimal final_sprice = (BigDecimal) data3[10];
		BigDecimal final_disc_amt = (BigDecimal) data3[11];
		BigDecimal final_disc_rate = (BigDecimal) data3[12];
		BigDecimal final_dp_amt = (BigDecimal) data3[16];
		BigDecimal final_availed_amt = (BigDecimal) data3[18];
		//Integer final_dpterm = (Integer) data3[20];
		String final_dpterm = (String) data3[20];
		//Integer final_materm = (Integer) data3[21];
		String final_materm = (String) data3[21];
		String final_merge_unit_id = (String) data3[24];
		String final_seq_no = (String) data3[26];
		BigDecimal final_fire_amt = (BigDecimal) data3[27];
		String old_seq_no = (String) data3[56];

		if(data3[20].equals("")){
			final_dpterm = null;
		}
		if(data3[21].equals("")){
			final_materm = null;
		}

		pgSelect db = new pgSelect();

		String sql = "select sp_update_request_details(nullif('"+new_dp_duedate+"', 'null')::TIMESTAMP, "+
				"nullif('"+new_ma_duedate+"', 'null')::TIMESTAMP, '"+final_entity_id+"', '"+new_lname+"', '"+new_fname+"', "+
				"'"+new_mname+"', '"+final_entity_class+"', '"+old_proj_id+"', '"+final_model_id+"' ,'"+final_unit_id+"', "+
				""+final_seq_no+", "+final_sprice+", '"+final_pmt_scheme+"' , "+final_disc_amt+","+final_materm+", "+final_dpterm+", "+final_dp_amt+", "+
				""+final_availed_amt+", "+final_fire_amt+", "+new_dp_rate+","+new_availed_rate+" ,"+final_disc_rate+", '"+new_civil_status+"', '"+final_agent_id+"', '"+purpose+"','"+req_no+"')";
		db.select(sql);
		FncSystem.out("Display Updating of request", sql);

	}

	public static boolean isRequestExisting(String request_no){//CHECKS IF THE REQUEST IS ALREADY EXISTING BASED ON REQ NO
		pgSelect db = new pgSelect();
		String sql = "select * from req_rt_request_header where request_no = '"+request_no+"'";
		db.select(sql);
		return db.isNotNull();
	}

	public static void createNewSchedule(Object [] data3, String req_no){//XXX NEW CREATING OF SCHEDULE BASED ON THE SELECTED REQUEST (CHECK ME)
		String final_entity_id = (String) data3[0];
		String final_unit_id = (String) data3[5];
		String final_model_id = (String) data3[6];
		String final_entity_class = (String) data3[8];
		String final_pmt_scheme = (String) data3[9];
		BigDecimal final_sprice = (BigDecimal) data3[10];
		BigDecimal final_disc_amt = (BigDecimal) data3[11];
		String final_dp_term = (String) data3[20];
		String final_ma_term = (String) data3[21];
		String final_seq_no = (String) data3[26];
		String proj_id = (String) data3[34];
		String new_sched_date = (String) data3[89];
		String req_id = (String) data3[60];
		BigDecimal disc_amt = new BigDecimal("0.00");
		Date final_dp_due_date = (Date) data3[22];

		if(req_id.equals("08")){//SETS THE DISCOUNT AMOUNT IF REQUEST IS RECONSIDERATION OF DISCOUNT OR PROMO
			disc_amt = final_disc_amt;
		}
		
		pgSelect db = new pgSelect();

		if(getGroupID(final_entity_class).equals("02")){//INHOUSE SCHEDULE WITH NEW SCHEDDATE
			
				String sql = "select * from get_new_inhouse_schedule('"+final_entity_id+"', '"+proj_id+"', \n"+
						"getinteger('"+final_unit_id+"')::VARCHAR, "+final_seq_no+", "+final_disc_amt+", \n"+
						"'"+final_entity_class+"', '"+final_pmt_scheme+"', now()::DATE::TIMESTAMP, \n"+
						"'"+final_dp_due_date+"',"+final_dp_term+", "+final_ma_term+", '"+final_model_id+"' ,"+final_sprice+", \n"+
						"'"+req_id+"','"+UserInfo.EmployeeCode+"') order by part_id";
				db.select(sql);
				FncSystem.out("Display In House Schedule", sql);
		}

		if(getGroupID(final_entity_class).equals("03")){//XXX CASH SCHEDULE PUT CODE HERE FOR CASH SCHEDULE (CHECK ULET)

				String sql = "select * from get_new_cash_schedule('"+final_entity_id+"', '"+proj_id+"', getinteger('"+final_unit_id+"')::VARCHAR, \n"+
						""+final_seq_no+", "+final_disc_amt+", '"+final_entity_class+"', '"+final_pmt_scheme+"', now()::DATE::TIMESTAMP, "+final_dp_term+", \n"+
						""+final_ma_term+", '"+final_model_id+"', "+final_sprice+" ,'"+UserInfo.EmployeeCode+"') order by part_id";

				db.select(sql);
				FncSystem.out("Display New Cash Schedule", sql);
		}

		if(getGroupID(final_entity_class).equals("04")){//PAGIBIG SCHEDULE
			

				String sql = "select * from get_new_pagibig_schedule('"+final_entity_id+"', '"+proj_id+"', \n"+
						"getinteger('"+final_unit_id+"')::VARCHAR, "+final_seq_no+", "+final_disc_amt+", \n"+
						"'"+final_entity_class+"', '"+final_pmt_scheme+"', now()::DATE::TIMESTAMP, \n"+
						""+final_dp_term+", "+final_ma_term+",'"+final_model_id+"', "+final_sprice+",'"+UserInfo.EmployeeCode+"') order by scheddate, part_id";

				db.select(sql);
				FncSystem.out("Display Pagibig Schedule", sql);
				System.out.println("Dumaan dito");
		}

		//COMMENTED BY JOHN LESTER FATALLO 06-15-15
		pgUpdate dbUpdate = new pgUpdate();

		/*if(isRequestExisting(req_no)){
			String sql = "Delete from rf_req_client_schedule where request_no = '"+req_no+"'";
			System.out.println("Dumaan dito sa delete ng schedule");
			dbUpdate.executeUpdate(sql, true);
		}*/

		System.out.printf("Display Row Count %s%n", db.getRowCount());
		for(int x=0; x< db.getRowCount(); x++){

			String entity_id = (String) db.getResult()[x][0];
			String unit_id = (String) db.getResult()[x][1];
			String projid = (String) db.getResult()[x][2];
			String pbl_id = (String) db.getResult()[x][3];
			Integer seq_no = (Integer) db.getResult()[x][4];
			String part_id = (String) db.getResult()[x][5];
			Date scheddate = (Date) db.getResult()[x][6];
			BigDecimal amount = (BigDecimal) db.getResult()[x][7];
			BigDecimal proc_fee = (BigDecimal) db.getResult()[x][8];
			BigDecimal mri = (BigDecimal) db.getResult()[x][9];
			BigDecimal fire = (BigDecimal) db.getResult()[x][10];
			BigDecimal interest = (BigDecimal) db.getResult()[x][12];
			BigDecimal principal = (BigDecimal) db.getResult()[x][13];
			BigDecimal vat = (BigDecimal) db.getResult()[x][14];
			BigDecimal balance = (BigDecimal) db.getResult()[x][15];
			BigDecimal interest_rate = (BigDecimal) db.getResult()[x][18];

			//INSERT THE NEW SCHEDULE IN THE TABLE BEFORE POSTING FOR APPROVAL 
			System.out.println("*************Creating New Schedule");
			String sql = "INSERT INTO rf_req_client_schedule(\n" + 
					"client_sched_id, request_no, entity_id, projcode, pbl_id, seq_no, \n" + 
					"part_id, scheddate, amount, proc_fee, mri, fire, interest, principal, \n" + 
					"vat, balance, request_status, interest_rate, unit_id, created_by, \n" + 
					"date_created, edited_by, date_edited)\n" + 
					"VALUES ((select count(client_sched_id) +  1 from rf_req_client_schedule), '"+req_no+"', \n"+
					"'"+entity_id+"', '"+projid+"', trim('"+pbl_id+"') ,"+seq_no+", '"+part_id+"', \n"+
					"'"+scheddate+"', "+amount+", "+proc_fee+", "+mri+", "+fire+", "+interest+", "+principal+", \n"+
					""+vat+", "+balance+", 'S', "+interest_rate+", '"+unit_id+"', '"+UserInfo.EmployeeCode+"', now(), null, null) \n";
			dbUpdate.executeUpdate(sql, false);

			System.out.printf("Display x: %s%n", x);
			//System.out.printf("Display Scheddate: %s%n", scheddate);
		}
		dbUpdate.commit();
	}

	public static void saveCreditPmt(Object [] data3){
		String final_entity_id = (String) data3[0];
		String final_unit_id = (String) data3[5];
		String final_seq_no = (String) data3[26];
		String proj_id = (String) data3[34];
		String old_entity_id = "";
		String old_unit_id = "";
		String old_proj_id = "";
		String old_seq_no ="";

		pgUpdate db = new pgUpdate();

		String sql = "INSERT INTO req_rt_payments_credit_temp(\n" + 
				"rec_id, entity_id, projcode, pbl_id, seq_no, actualdate, transdate, \n" + 
				"pay_part_id, particulars, pymnttype, bank_id, bank_branch_id, \n" + 
				"amount, acctno, checkno, checkdate, checkstat_id, statusdate, \n" + 
				"bouncereasonid, orno, prno, arno, debitmemono, refundno, refunddate, \n" + 
				"requestno, appliedamt, ordate, cancelled, remarks, replacementno, \n" + 
				"datereplaced, branch_id, post_date, client_seqno, or_doc_id, \n" + 
				"pr_doc_id, status_id, wdraw_stat, date_wdrawn, wdraw_no, wdraw_reason, \n" + 
				"repl_wdraw_by, date_remitted, remit_batch, date_bankreport, reversed, \n" + 
				"creditofpayment, pay_rec_id, lumpsum, forfeited, check_type, \n" + 
				"receipt_id, co_id, pre_ar, or_pending, unit_id, total_ar_amt)\n" + 
				"VALUES (select (select max(coalesce(rec_id, 1)) from req_rt_payments_credit_temp), \n"+
				"'"+final_entity_id+"', '"+proj_id+"', getinteger('"+final_unit_id+"')::VARCHAR , "+final_seq_no+" \n"+
				"actual_date, trans_date, pay_part_id, pymnt_type, bank_id, bank_branch_id, amount, acct_no, \n" + 
				"check_no, check_date, check_stat_id, bounce_reason_id, or_no, \n" + 
				"or_date, ar_no, refund_no, request_no, applied_amt, cancelled, \n" + 
				"remarks, branch_id, post_date, client_seqno, or_doc_id, pr_doc_id, \n" + 
				"status_id, wdraw_stat, date_wdrawn, wdraw_no, wdraw_reason, repl_wdraw_by, \n" + 
				"date_remitted, remit_batch, reversed, pay_rec_id, check_type, \n" + 
				"receipt_id, co_id, unit_id, total_ar_amt, created_by, date_created\n" + 
				"FROM rf_payments where entity_id = '"+old_entity_id+"' and proj_id = '"+old_proj_id+"' \n"+
				"and proj_id = '"+old_proj_id+"' and seq_no = "+old_seq_no+"\n";
		db.executeUpdate(sql, true);
		db.commit();

	}

	/**********CANCELATION OF THE REQUEST***********/
	public static void cancelReq(String req_no){ 

		pgSelect dbSelect = new pgSelect();
		String sql = "select sp_cancel_request('"+req_no+"', '"+UserInfo.EmployeeCode+"')";
		dbSelect.select(sql);
		FncSystem.out("Display Cancellation of Request", sql);
	}

	/***********POSTING OF THE REQUEST************/ //ONLY THE CSD AND ASD DEPT HEAD CAN POST REQUESTS

	public static void postRequest(Object [] data3, String req_no, String remarks){ 
		String final_entity_id = (String) data3[0];
		String final_unit_id = (String) data3[5];
		String final_entity_class = (String) data3[8];
		BigDecimal final_nsp = (BigDecimal) data3[15];
		String final_seq_no = (String) data3[26];
		String req_id = (String) data3[60];
		String old_entity_id = (String) data3[30];
		String old_unit_id = (String) data3[35];
		String old_entity_class = (String) data3[32];
		String proj_id = (String) data3[34];
		String old_seq_no = (String) data3[56];

		pgUpdate dbUpdate = new pgUpdate();
		System.out.println("**************Posting Client Request");
		if(affectSched(req_id)){
			//System.out.println("**************Dumaan dito");
			if(req_id.equals("01")){//FOR CHANGE OF DUE DATE REQUESTS //PUT CONTROL HERE IF CHANGE OF DUE DATE ONLY
				moveSchedule(data3, req_no);
				copyNewSchedule(req_no);
				createBuyerHistory(req_no, req_id, proj_id, final_entity_id, final_unit_id, final_seq_no);
				updateReqStat(req_no);
				return ;
			}
			System.out.println("******Dumaan dito pagkatapos ng Change of Due date");
			if(getGroupID(old_entity_class).equals("02")){//UPDATES THE DATA IN THE OLD INHOUSE SCHEDULES
				String sqlIHFold = "update rf_ihf_computation set status_id = 'I', edited_by = '"+UserInfo.EmployeeCode+"', date_edited = now() \n"+
						"where entity_id = '"+old_entity_id+"' \n"+
						"and unit_id = '"+old_unit_id+"' \n"+
						"and proj_id = '"+proj_id+"' \n"+
						"and seq_no = "+old_seq_no+" \n\n";
				dbUpdate.executeUpdate(sqlIHFold, true);
				//FncSystem.out("Display Update old", sqlIHFold);
			}else if(getGroupID(old_entity_class).equals("04")){//FOR PAGIBIG SCHEDULE
				String sqlPagibigOld = "update rf_pagibig_computation set status_id = 'I' \n"+
						"where entity_id = '"+old_entity_id+"' \n"+
						"and pbl_id = getinteger('"+old_unit_id+"')::VARCHAR \n"+
						"and proj_id = '"+proj_id+"'\n"+
						"and seq_no = "+old_seq_no+" \n"+
						"\n\n";
				//FncSystem.out("Display update old pagibig", sqlPagibigOld);
				dbUpdate.executeUpdate(sqlPagibigOld, true);
			}

			String sqlClientPriceHist = "update rf_client_price_history set status_id = 'I' \n"+
					"where entity_id = '"+old_entity_id+"' \n"+
					"and pbl_id = getinteger('"+old_unit_id+"')::VARCHAR \n"+
					"and proj_id = '"+proj_id+"'\n"+
					"and seq_no = "+old_seq_no+" \n\n";
			//FncSystem.out("Display Update Client Price History", sqlClientPriceHist);
			dbUpdate.executeUpdate(sqlClientPriceHist, true);

			if(getGroupID(final_entity_class).equals("02")){//SAVES THE NEW DATA FOR IN HOUSE SCHEDULES
				saveToIhfComputation(data3);
			}else if(getGroupID(final_entity_class).equals("04")){//SAVES DATA FOR THE NEW PAGIBIG SCHEDULES
				saveToPagibigComputation(data3);
			}

			if(req_id.equals("06") || req_id.equals("07")){//FOR TRANSFER OF UNIT REQUESTS //CHECK IF REAPPLICATION IS APPLICABLE HERE
				updateUnitCancelled(data3);
				insertToSoldUnit(data3, req_no);
			}else{//UPDATES THE UNIT INFO ON OTHER REQUESTS BESIDES TRANSFER OF UNIT
				updateSoldUnit(data3);
			}

			//UPDATES THE OLD UNIT IN THE mf_unit_info
			String sqlUpdateOld = "UPDATE mf_unit_info set status_id = 'Q', date_edited = now(), edited_by = '"+UserInfo.EmployeeCode+"' where unit_id = '"+old_unit_id+"' \n";
			dbUpdate.executeUpdate(sqlUpdateOld, true);
			//FncSystem.out("Display Update old Unit", sqlUpdateOld);

			//UPDATES THE NEW UNIT IN THE mf_unit_info  
			String sqlUpdateNew = "UPDATE mf_unit_info set status_id = 'R',date_edited = now(), edited_by = '"+UserInfo.EmployeeCode+"' where unit_id = '"+final_unit_id+"' \n";
			dbUpdate.executeUpdate(sqlUpdateNew, true);
			//FncSystem.out("Display Update new Unit ", sqlUpdateNew);

			saveToClientPriceHistory(data3, remarks);
			moveSchedule(data3, req_no);
			copyNewSchedule(req_no);

			if(req_id.equals("06")){//FOR TRANSFER OF UNIT REQUESTS
				createBuyerHistoryCancelled(data3, req_no);
				insertNewHistory(data3, req_no);
				if(hasMergeUnit(old_unit_id)){
					updatePreviousMergeUnit(data3, req_no);
				}
			}else{//FOR REQUEST OTHER THAN TRANSFER OF UNIT
				createBuyerHistory(req_no, req_id, proj_id, final_entity_id, final_unit_id, final_seq_no);
			}
			dbUpdate.commit();
		}
		//POSTS REQUEST THAT HAS NO EFFECT ON CLIENT'S SCHEDULE
		postRequestOthers(req_id, req_no);
		//UPDATES THE REQUEST STATUS TO POSTED
		updateReqStat(req_no);
		//XXX TRY HERE sp_post_request if finished
	}

	public static Boolean postRequest2(String request_no){//POSTING OF REQUEST THAT ARE ACTIVE 
		pgSelect db = new pgSelect();
		String sql = "select sp_post_request('"+request_no+"', '"+UserInfo.EmployeeCode+"', '"+UserInfo.Branch+"')";

		db.select(sql);

		Boolean post = (Boolean) db.getResult()[0][0];

		FncSystem.out("Display posting of the request", sql);
		return post;
	}

	public static void postRequestOthers(String req_id, String req_no){//POST REQUEST WITH NO EFFECT ON THE CLIENTS SCHEDULE

		pgSelect db = new pgSelect();
		//POSTING OF CORRECTION OF NAME
		if(req_id.equals("03")){
			String sql = "select sp_post_correction_name('"+req_no+"', '"+UserInfo.EmployeeCode+"')";
			FncSystem.out("Display posting of correction of name", sql);
			db.select(sql);
		}
		//POSTING OF CHANGE OF PRINCIPAL APPLICANT
		if(req_id.equals("05")){
			String sql = "select sp_post_change_principal('"+req_no+"', '"+UserInfo.EmployeeCode+"')";
			FncSystem.out("Display Posting of Change of Principal Applicant", sql);
			db.select(sql);
			//postChangePrincipal(data3, req_no);
		}
		if(req_id.equals("13")){
			String sql = "select sp_post_change_selling_agent('"+req_no+"', '"+UserInfo.EmployeeCode+"')";
			FncSystem.out("Display Posting of Change Selling Agent", sql);
			db.select(sql);
		}
		//POSTING OF CHANGE OF CIVIL STATUS
		if(req_id.equals("25")){
			String sql = "select sp_post_change_civil_status('"+req_no+"', '"+UserInfo.EmployeeCode+"')";
			FncSystem.out("Display Posting of Change Civil Status", sql);
			db.select(sql);
		}
	}

	public static void moveSchedule(Object [] data3, String req_no){//DELETES THE PREVIOUS SCHEDULE AFTER MOVING IT TO rf_req_schedule_old
		String old_entity_id = (String) data3[30]; 
		String old_proj_id = (String) data3[34];
		String old_unit_id = (String) data3[35];
		String old_seq_no = (String) data3[56];
		String req_id = (String) data3[60];

		pgSelect db = new pgSelect();
		String sql = "select sp_move_schedule('"+old_entity_id+"', '"+old_unit_id+"', "+old_seq_no+", '"+old_proj_id+"', '"+req_id+"', '"+UserInfo.EmployeeCode+"', '"+req_no+"')";
		FncSystem.out("Display Move Schedule", sql);
		db.select(sql);
	}

	public static void copyNewSchedule(String req_no){//COPY THE NEW SCHEDULE INTO rf_client_schedule to replace the deleted previous schedule

		pgSelect db = new pgSelect();
		String sql = "select sp_copy_new_schedule('"+req_no+"', '"+UserInfo.EmployeeCode+"')";
		FncSystem.out("Display Copy New Schedule", sql);
		db.select(sql);
	}

	public static void createBuyerHistory(String req_no, String req_id, String proj_id, String final_entity_id, String final_unit_id, String final_seq_no){
		System.out.println("****************Create Buyer History");
		pgUpdate db = new pgUpdate();//IF MULTIPLE REQUEST THIS SHOULD LOOP WITH INSERTING BASED ON HOW MANY REQUESTS ARE TO BE APPROVED
		String sql = "INSERT INTO rf_buyer_status(\n" + 
				"entity_id, proj_id, pbl_id, seq_no, byrstatus_id, tran_date, \n" + 
				"actual_date, request_no, inactive_date, status_id, trans_no, \n" + 
				"jv_no, to_mi_mo_projcode, to_mi_mo_pbl_id, branch_id, created_by)\n" + 
				"VALUES ('"+final_entity_id+"', '"+proj_id+"', \n" + 
				"getinteger('"+final_unit_id+"')::VARCHAR, "+final_seq_no+", \n"+
				"(select byrstatus_id from mf_request_type where request_id = '"+req_id+"'), \n"+
				"now(), now(), '"+req_no+"', \n" + 
				"null, 'A', null, null, \n" + 
				"null,null, null, '"+UserInfo.EmployeeCode+"'); \n\n";
		//FncSystem.out("Display RfBUyerStatus", sql);

		db.executeUpdate(sql, true);
		db.commit();
		//XXX TRY TO PUT sp_create_buyer_history here;

		/*String sql "";*/
	}

	public static void createBuyerHistoryCancelled(Object [] data3, String req_no){
		String old_entity_id = (String) data3[30];
		String proj_id = (String) data3[34];
		String old_unit_id = (String) data3[35];
		String old_seq_no = (String) data3[56];

		System.out.println("****************Create Buyer History Cancelled");

		pgUpdate db = new pgUpdate();

		String sql = "INSERT INTO rf_buyer_status(\n" + 
				"entity_id, proj_id, pbl_id, seq_no, byrstatus_id, tran_date, \n" + 
				"actual_date, request_no, inactive_date, status_id, trans_no, \n" + 
				"jv_no, to_mi_mo_projcode, to_mi_mo_pbl_id, branch_id, created_by)\n" + 
				"VALUES ('"+old_entity_id+"', '"+proj_id+"', \n"+
				"getinteger('"+old_unit_id+"')::VARCHAR, \n"+
				""+old_seq_no+", '02', now(), \n" + 
				"now(), '"+req_no+"', null, 'A', null, \n" + 
				"null, null, null, null, '"+UserInfo.EmployeeCode+"');";
		db.executeUpdate(sql, true);
		db.commit();
	}

	public static void insertNewHistory(Object [] data3, String req_no){
		String final_entity_id = (String) data3[0];
		String final_unit_id = (String) data3[5];
		String final_seq_no = (String) data3[26];
		String old_entity_id = (String) data3[30];
		String proj_id = (String) data3[34];
		String old_unit_id = (String) data3[35];
		String old_seq_no = (String) data3[56];

		pgSelect db = new pgSelect();
		String sql = "select sp_insert_new_history('"+old_entity_id+"', '"+old_unit_id+"', "+old_seq_no+", '"+proj_id+"', '"+final_entity_id+"', '"+final_unit_id+"', "+final_seq_no+", '"+req_no+"', '"+UserInfo.EmployeeCode+"');";
		FncSystem.out("Insert New History", sql);
		db.select(sql);

	}

	public static void saveToIhfComputation(Object [] data3){//OK NA TO;
		String final_entity_id = (String) data3[0];
		String proj_id = (String) data3[34];
		String final_unit_id = (String) data3[5];
		String final_entity_class = (String) data3[8];
		String final_pmt_scheme = (String) data3[9];
		String final_seq_no = (String) data3[26];
		BigDecimal final_int_rate = (BigDecimal) data3[28];
		//Integer final_dpterm = (Integer) data3[20];
		String final_dpterm = (String) data3[20];
		//Integer final_materm = (Integer) data3[21];
		String final_materm = (String) data3[21];
		BigDecimal final_availed_amt = (BigDecimal) data3[18];
		BigDecimal final_dp_amt = (BigDecimal) data3[16];
		BigDecimal final_fire_amt = (BigDecimal) data3[27];
		BigDecimal final_disc_rate = (BigDecimal) data3[12];
		BigDecimal final_disc_amt = (BigDecimal) data3[11];
		BigDecimal final_vat_rate = (BigDecimal) data3[14];
		BigDecimal final_dp_rate = (BigDecimal) data3[17];

		System.out.println("****************Save to IHF Computation");

		pgUpdate db = new pgUpdate();
		String sql = "INSERT INTO rf_ihf_computation(\n" + 
				"entity_id, proj_id, pbl_id, seq_no, pmt_scheme_id, int_rate, \n" + 
				"res_term, dp_term, ma_term, comp_factor, availed_amount, dp, \n" + 
				"total_ma, ma, vat, mri, fire, pico, disc_rate, disc_amount, dp_rate, \n" + 
				"status_id, inactive_date, ihf_loan_applied, pagibig_term, circular_no, \n" + 
				"unit_id, created_by, date_created, edited_by, date_edited)\n" + 
				"VALUES ('"+final_entity_id+"', '"+proj_id+"', \n"+
				"getinteger('"+final_unit_id+"')::VARCHAR, \n"+ 
				""+final_seq_no+", '"+final_pmt_scheme+"', "+final_int_rate+", 2, \n" + 
				""+final_dpterm+", "+final_materm+", \n"+
				"sp_compute_factor_ihf("+final_materm+", "+final_int_rate+"), "+
				""+final_availed_amt+", "+final_dp_amt+", \n"+
				"round("+getMAPerMonth(data3, getMAProcfee(final_entity_class, final_entity_id, final_availed_amt))+", 2), \n" +  //XXX check function get_ma_amount_per_month
				"round("+getMortgage(data3)+", 2), \n"+
				"round("+getMortgage(data3).doubleValue() * final_vat_rate.doubleValue()/100+", 2), "+ 	
				"round("+getMRI(data3)+", 2), "+final_fire_amt+", "+
				"null, "+final_disc_rate+", "+final_disc_amt+", "+final_dp_rate+", 'A', \n" + 
				"null, null, null, null, '"+final_unit_id+"', \n" + 
				"'"+UserInfo.EmployeeCode+"', now(), null, null); \n";

		//FncSystem.out("Display Save to Ihf computation", sql);
		db.executeUpdate(sql, true);
		db.commit();
	}

	public static void saveToPagibigComputation(Object [] data3){//OK NA TO
		String final_entity_id = (String) data3[0];
		String final_unit_id = (String) data3[5];
		String final_model_id = (String) data3[6];
		String final_entity_class = (String) data3[8];
		String final_pmt_scheme = (String) data3[9];
		String proj_id = (String) data3[34];
		String final_seq_no = (String) data3[26];
		BigDecimal final_availed_amt = (BigDecimal) data3[18];
		BigDecimal final_int_rate = (BigDecimal) data3[28];
		//Integer final_dpterm = (Integer) data3[20];
		String final_dpterm = (String) data3[20]; 
		//Integer final_materm = (Integer) data3[21];
		String final_materm = (String) data3[21];
		BigDecimal final_dp_amt = (BigDecimal) data3[16];
		BigDecimal final_fire_amt = (BigDecimal) data3[27];

		System.out.println("****************Save to Pagbig Computation");

		pgUpdate db = new pgUpdate();
		String sql = "INSERT INTO rf_pagibig_computation(\n" + 
				"entity_id, proj_id, pbl_id, seq_no, pmt_scheme_id, house_appraisal, \n" + 
				"lot_appraisal, loan_applied, fund_salary, int_rate, res_term, \n" + 
				"dp_term, term, comp_factor, loanable_amount, dp, total_ma, ma, \n" + 
				"mri, fire, pico, annual_fire, annual_mri, \n" + 
				"status_id, created_by, date_created, \n" + 
				"edited_by, date_edited)\n" + 
				"VALUES ('"+final_entity_id+"', '"+proj_id+"', \n"+
				"getinteger('"+final_unit_id+"')::VARCHAR, \n"+ 
				""+final_seq_no+", '"+final_pmt_scheme+"', \n"+
				/*"(select appraised_value from rf_product_pricing where model_id = '"+final_model_id+"' \n"+
				"and effectivity = (select max(effectivity) from rf_product_pricing where model_id = '"+final_model_id+"')), \n"+*/
				"(select appraised_value from mf_unit_info where unit_id = '"+final_unit_id+"') ,\n"+
				"(select lot_appraisal from mf_unit_pricing where pbl_id = getinteger('"+final_unit_id+"')::VARCHAR), \n" + 
				""+final_availed_amt+", 0.00, "+final_int_rate+", 2, "+final_dpterm+", "+final_materm+", \n"+
				"(select sp_compute_factor("+final_materm+", "+final_int_rate+")), \n"+
				""+final_availed_amt+", "+final_dp_amt+", \n"+
				""+getMAPerMonth(data3, getMAProcfee(final_entity_class, final_entity_id, final_availed_amt))+", \n"+ //XXX CHECK FUNCTION get_ma_amount_per_month
				""+getMortgage(data3)+", "+getMRI(data3)+", "+final_fire_amt+", 0.00,"+final_fire_amt+" * 12 ,\n"+
				""+getMRI(data3).doubleValue() * 12+", 'A', '"+UserInfo.EmployeeCode+"', now(), null, null) \n\n";

		//FncSystem.out("Display Save to PAgibig Computation", sql);
		db.executeUpdate(sql, true);
		db.commit();
	}

	public static void saveToClientPriceHistory(Object [] data3, String remarks){//SAVING 
		String final_entity_id = (String) data3[0];
		String final_unit_id = (String) data3[5];
		String final_seq_no = (String) data3[26];
		String proj_id = (String) data3[34];
		BigDecimal final_sprice = (BigDecimal) data3[10];
		BigDecimal final_disc_amt = (BigDecimal) data3[11];
		BigDecimal final_vat_amt = (BigDecimal) data3[13];
		BigDecimal final_net_sell_price = (BigDecimal) data3[15];
		BigDecimal final_vat_rate = (BigDecimal) data3[14];
		BigDecimal old_nsp = (BigDecimal) data3[47];

		System.out.println("****************Save to Client Price History");

		pgUpdate db = new pgUpdate();
		String sql = "INSERT INTO rf_client_price_history(\n" + 
				"entity_id, proj_id, pbl_id, seq_no, byrstatus_id, tran_date, \n" + 
				"gross_sprice, discount, vat, net_sprice, vat_rate, remarks, status_id, \n" + 
				"created_by, date_created)\n" + 
				"VALUES ('"+final_entity_id+"', '"+proj_id+"', \n" + 
				"getinteger('"+final_unit_id+"')::VARCHAR, \n" + 
				""+final_seq_no+", '17', now(), \n" + 
				""+final_sprice+", "+final_disc_amt+", "+final_vat_amt+", "+final_net_sell_price+", \n" + 
				""+final_vat_rate+", '"+remarks+"', 'A', '"+UserInfo.EmployeeCode+"', now()); \n\n";

		//FncSystem.out("DIsplay to CLient Price History", sql);
		db.executeUpdate(sql, true);
		db.commit();
	}

	public static void updateUnitCancelled(Object [] data3){//XXX OK NA DITO
		String old_entity_id = (String) data3[30];
		String old_unit_id = (String) data3[35];
		String proj_id = (String) data3[34];
		String old_seq_no = (String) data3[56];

		System.out.println("****************Update Unit Cancelled");

		pgUpdate db = new pgUpdate();

		String sql = "UPDATE rf_sold_unit SET status_id = 'I', currentstatus = '02' \n"+
				"where entity_id = '"+old_entity_id+"' and pbl_id = getinteger('"+old_unit_id+"')::VARCHAR \n"+
				"and seq_no = "+old_seq_no+" and projcode = '"+proj_id+"' \n\n";
		db.executeUpdate(sql, true);
		db.commit();
	}

	public static void insertToSoldUnit(Object [] data3, String req_no){//DELETES THE PREVIOUS SOLD UNIT AND INSERTS THE NEW SOLD UNIT
		String final_entity_id = (String) data3[0];
		String final_unit_id = (String) data3[5];
		String proj_id = (String) data3[34];
		String final_seq_no = (String) data3[26];
		String old_entity_id = (String) data3[30];
		String old_unit_id = (String) data3[35];

		System.out.println("****************Insert to Sold Unit the new unit and deletes the old cancelled unit");
		pgUpdate db = new pgUpdate();


		if(isCancelled(final_entity_id, final_unit_id)){//XXX check if need to add seq no and proj_id here (CHECK IF THIS IS FOR THE REAPPLICATION)
			String sqlDeleteRfSoldUnit = "Delete from rf_sold_unit where pbl_id = getinteger('"+final_unit_id+"')::VARCHAR \n"+
					"and entity_id = '"+final_entity_id+"' and seq_no = '"+final_seq_no+"' and projcode = '"+proj_id+"' \n"+
					"and status_id = 'I' \n";
			db.executeUpdate(sqlDeleteRfSoldUnit, true);

			String sqlInsertByrStat = "INSERT INTO rf_buyer_status(\n" + 
					"entity_id, proj_id, pbl_id, seq_no, byrstatus_id, tran_date, \n" + 
					"actual_date, request_no, inactive_date, status_id, trans_no, \n" + 
					"jv_no, to_mi_mo_projcode, to_mi_mo_pbl_id, branch_id, created_by)\n" + 
					"VALUES ('"+final_entity_id+"', '"+proj_id+"', \n" + 
					"getinteger('"+final_unit_id+"')::VARCHAR, \n" + 
					""+final_seq_no+", '08', now(), \n" + 
					"now(), '"+req_no+"', null, 'A', null, \n" + 
					"null, null, null, null, '"+UserInfo.EmployeeCode+"'); \n\n";
			db.executeUpdate(sqlInsertByrStat, true);
		}

		String sqlInsertSoldUnit = "INSERT INTO rf_sold_unit (select new_entity_id, new_proj_id,\n" + 
				"(select pbl_id from mf_unit_info where unit_id = new_unit_id),\n" + 
				"new_seq_no, new_sprice, new_pmt_scheme_id, new_lotarea, new_model,\n" + 
				"new_floorarea, now(), '17',\n" + //try function get_reservation_date here //CHECK get reservartion date here
				"now(), new_mktgarm, new_agent, new_entity_class, \n" + 
				"coalesce((select vat from mf_unit_info where unit_id = new_unit_id), false),\n" + 
				"null,now(), '01', now(), null, null, null, null, null, null, null, \n" + 
				"false, null, null, null, null, null, null, null, null,\n" + 
				"false, null, null, null, null, null, null, new_dp_term, new_ma_term,\n" + 
				"false, null, false, null, null, null, false, 'override', 'cat', \n" + 
				"null, null, null, null, false, null, false, null, 'A',\n" + 
				"'"+UserInfo.EmployeeCode+"', now(), null, null from req_rt_request_header where request_no = '"+req_no+"') \n\n";

		db.executeUpdate(sqlInsertSoldUnit, true);
		db.commit(); //TRY sp_insert_to_sold_unit_here
	}

	public static void updatePreviousMergeUnit(Object [] data3, String req_no){//WALA PANG MERGE UNIT 
		String old_unit_id = (String) data3[35];
		System.out.println("****************Update Previous Merge Unit");

		pgUpdate db = new pgUpdate();
		String sqlUpdateOld = "Update mf_unit_info set status_id = 'A', edited_by = '"+UserInfo.EmployeeCode+"', \n"+
				"date_edited = now() where unit_id = '"+getMergeUnitID(old_unit_id)+"'";
		db.executeUpdate(sqlUpdateOld, true);

		String sqlUpdateNew = "Update mf_unit_info set status_id = 'A', edited_by = '"+UserInfo.EmployeeCode+"', date_edited = now() \n"+
				"where unit_id = (select merge_unit_id from req_rt_request_header where request_no = '"+req_no+"')";
		db.executeUpdate(sqlUpdateNew, true);

		String sqlInsertSoldOther = "INSERT INTO hs_sold_other_lots \n"+
				"select (select coalesce(max(merge_lot_id),0) + 1 from hs_sold_other_lots), \n"+
				"true, new_entity_id, new_proj_id, \n"+
				"(select pbl_id from mf_unit_info where unit_id = new_unit_id) \n"+
				"new_seq_no, (select pbl_id from mf_unit_info where unit_id = merge_unit_id) \n"+
				"new_sprice, new_lotarea, 'A', now(), null \n"+
				"from req_rt_request_header where request_no = '"+req_no+"'";
		db.executeUpdate(sqlInsertSoldOther, true);

		String updateSoldOtherLot = "Update hs_sold_other_lots set status_id = 'A' where pbl_id = getinteger('"+old_unit_id+"')::VARCHAR";
		db.executeUpdate(updateSoldOtherLot, true);

		db.commit();
	}

	public static void updateSoldUnit(Object [] data3){//UPDATES THE OLD UNIT WITH THE NEW SOLD UNIT
		String final_entity_id = (String) data3[0];
		String final_unit_id = (String) data3[5];
		String final_model_id = (String) data3[6];
		String final_agent_id = (String) data3[7];
		String final_entity_class = (String) data3[8];
		String final_pmt_scheme = (String) data3[9];
		BigDecimal final_sprice = (BigDecimal) data3[10];
		BigDecimal final_vat_rate = (BigDecimal) data3[14];
		BigDecimal final_nsp = (BigDecimal) data3[15];
		String final_seq_no = (String) data3[26];
		String final_lot_area = (String) data3[29];
		String old_entity_id = (String) data3[30];
		String proj_code = (String) data3[34];
		String old_unit_id = (String) data3[35];
		String old_seq_no = (String) data3[56];

		System.out.println("****************Update Sold Unit");

		System.out.printf("Display new model id", final_model_id);

		pgUpdate db = new pgUpdate();
		String sql = "UPDATE rf_sold_unit SET entity_id='"+final_entity_id+"', projcode='"+proj_code+"', \n" + 
				"pbl_id= getinteger('"+final_unit_id+"')::VARCHAR, seq_no = "+final_seq_no+", sellingprice="+final_nsp+", \n" + 
				"pmt_scheme_id= '"+final_pmt_scheme+"' , lotarea= "+final_lot_area+" , model_id= '"+final_model_id+"', \n" + 
				"floorarea= (select floorarea from mf_unit_info where unit_id = '"+final_unit_id+"'), statusdate=now(), \n" + 
				"mktgarm= (select dept_id from rf_sellingagent_info where agent_id = '"+final_agent_id+"'), \n" + 
				"sellingagent= '"+final_agent_id+"', buyertype= '"+final_entity_class+"', \n" + 
				"vat= (select coalesce(vat, false) from mf_unit_info where unit_id = '"+final_unit_id+"'), \n" + 
				"branch_id= '"+UserInfo.Branch+"', vat_rate= "+final_vat_rate+", status_id= 'A', edited_by= '"+UserInfo.EmployeeCode+"', date_edited= now(), unit_id='"+final_unit_id+"'\n" + 
				"WHERE entity_id = '"+old_entity_id+"' and pbl_id = getinteger('"+old_unit_id+"')::VARCHAR and projcode = '"+proj_code+"'\n" + 
				"and seq_no = "+old_seq_no+" \n\n";
		db.executeUpdate(sql, true);
		db.commit();
	}

	public static void updateMfUnitInfo(Object []data3){ //UPDATES THE STATUS IN THE UNIT INFO WHEN THE REQUEST IS TRANSFER OF UNIT
		String final_unit_id = (String) data3[5];
		String old_unit_id = (String) data3[35];
		String final_merge_unit_id = (String) data3[24];

		System.out.println("************Update mf_unit_info");
		pgUpdate db = new pgUpdate();
		String sql = "update mf_unit_info set status_id = 'Q' where unit_id = '"+final_unit_id+"' \n";
		db.executeUpdate(sql, true);
		if(hasMergeUnit(old_unit_id)){
			String sqlMerge = "update mf_unit_info set status_id = 'Q' where unit_id = '"+final_merge_unit_id+"' \n";
			db.executeUpdate(sqlMerge, true);
		}
		db.commit();
	}

	public static void updateReqStat(String req_no){//updates the request status based on the request no
		pgUpdate db = new pgUpdate();
		System.out.println("**********UPDATING NG REQUEST NO");

		String sql = "Update req_rt_request_header set request_status = 'P', approved_date = now(),  approved_by = '"+UserInfo.EmployeeCode+"' where request_no = '"+req_no+"'";
		db.executeUpdate(sql, true);
		db.commit();
	}
	
	/*********END OF POSTING ********/

	/***************VALIDATION**********************/
	//Checks if the the unit is officially reserved
	public static boolean isOfficiallyReserved(String old_entity_id, String old_unit_id,String old_seq_no, String old_proj_id){
		pgSelect db = new pgSelect();
		String sql = "select case when currentstatus = '01' then true else false end \n"+
				"from rf_sold_unit \n"+
				"where entity_id = '"+old_entity_id+"' \n"+
				"and pbl_id = getinteger('"+old_unit_id+"')::VARCHAR \n"+
				"and seq_no = "+old_seq_no+" \n"+
				"and projcode = '"+old_proj_id+"'";
		db.select(sql);
		FncSystem.out("Is Officially Reserved", sql);
		return (Boolean) db.getResult()[0][0];
	}

	public static boolean isFirstDPPaid(String entity_id, String unit_id, String seq_no, String proj_id){ //CHECKS IF THE FIRST DP AMOUNT HAS BEEM PAID
		pgSelect db = new pgSelect();
		String sql = "select case when count(*) = 0 then false else true end\n" + 
				"from rf_client_ledger \n" + 
				"where entity_id = '"+entity_id+"'\n" + 
				"and pbl_id = getinteger('"+unit_id+"')::VARCHAR\n" + 
				"and proj_id = '"+proj_id+"'\n" + 
				"and seq_no = "+seq_no+"\n" + 
				"and part_id = '013'\n";
		db.select(sql);
		FncSystem.out("Is First DP Paid", sql);
		return (Boolean) db.getResult()[0][0];
	}

	public static boolean isSoldToBank(String entity_id, String unit_id, String seq_no, String proj_id){//CHECK IF ACCOUNT STATUS IS SOLD TO BANK
		pgSelect db = new pgSelect();

		String sql = "select case when currentstatus in ('106', '25', '52', '67', '99') \n" + 
				"then true else false end \n" + 
				"from rf_sold_unit \n" + 
				"where entity_id = '"+entity_id+"'\n" + 
				"and projcode = '"+proj_id+"'\n" + 
				"and pbl_id = getinteger('"+unit_id+"')::VARCHAR\n" + 
				"and seq_no = "+seq_no+"";
		db.select(sql);

		FncSystem.out("Is Sold to Bank", sql);
		return (Boolean) db.getResult()[0][0];
	}

	//CHECKS IF THE COMMISSION OF THE AGENT IS ALREADY RELEASED
	public static Boolean isCommissionReleased(String agent_id, String unit_id, String proj_id, String seq_no){
		pgSelect db = new pgSelect();

		String sql = "select case when count(*) = 0 then false else true end\n" + 
				"from cm_cdf_hd a\n" + 
				"left join cm_cdf_dl b on b.cdf_no = a.cdf_no\n" + 
				"where a.agent_code = '"+agent_id+"'\n" + 
				"and b.pbl_id = getinteger('"+unit_id+"')::VARCHAR\n" + 
				"and b.projcode = '"+proj_id+"'\n" + 
				"and b.seq_no = "+seq_no+";";
		db.select(sql);
		FncSystem.out("Display is Commission Released", sql);
		return (Boolean) db.getResult()[0][0];
	}

	public static Boolean isFullComm(String unit_id){//CHECK IF THE COMMISSION OF THE AGENT IS FULLY RELEASED
		pgSelect db = new pgSelect();
		
		String sql = "select case when sum(a.comm_amt) != 0.00 and sum(a.comm_amt) > (sum(b.applied_amt) + sum(b.wtax_amt)) then false else true end\n" + 
					 "from cm_schedule_dl a\n" + 
					 "left join cm_cdf_dl b on b.pbl_id = a.pbl_id\n" + 
					 "where a.pbl_id = getinteger('"+unit_id+"')::VARCHAR \n"+
					 "and a.tran_type = 'AA' \n"+
					 "and b.tran_type = 'AA' \n"+
					 "and a.status not in ('I', 'D', 'X')";
		db.select(sql);

		return (Boolean) db.getResult()[0][0];
	}

	//CHECKS IF THE UNIT TO BE REAPPLIED IS ALREADY TAKEN
	public static Boolean isUnitTaken(String unit_id){
		pgSelect db = new pgSelect();
		String sql = "select case when status_id != 'A' then true else false end from mf_unit_info where unit_id = '"+unit_id+"'";
		db.select(sql);
		FncSystem.out("Display if unit is Already Taken", sql);
		return (Boolean) db.getResult()[0][0];
	}

	public static boolean checkDeptHead(){//CHECKS IF THE USER LOGGED IN IS DEPT HEAD OF CSD AND ASD FOR APPROVING OF THE REQUEST
		pgSelect db = new pgSelect();

		String sql = "select case when '"+UserInfo.EmployeeCode+"' in ((SELECT dept_head_code from mf_department where dept_code = '02'), \n"+
				"(select div_head_code from mf_division where division_code = '31'), '900876', '900449') then true else false end";
		db.select(sql);
		//FncSystem.out("Check Dept Head", sql);
		return (Boolean) db.getResult()[0][0];
	}

	public static String getGroupID(String buyertype){//DETERMINES WHETHER THE BUYER TYPE IS PAGIBIG, INHOUSE OR CASH
		pgSelect db = new pgSelect();
		String sql = "select get_group_id('"+buyertype+"')";
		db.select(sql);
		//FncSystem.out("Display get Group Id", sql);
		return (String) db.getResult()[0][0];
	}

	public static Boolean affectSched(String req_id){ //CHECKS IF THE REQUEST WILL AFFECT THE OLD CLIENT SCHEDULE
		Boolean ret = false;
		//REQUESTS THAT DO NOT HAVE AN EFFECT ON THE CURRENT PAYMENT SCHEDULE OF THE CLIENT 
		if(req_id.equals("03") || req_id.equals("13") || req_id.equals("25")){
			ret = false;
		}
		//REQUESTS THAT REQUIRE ALTERING THE SCHEDULE OF THE CLIENT BASED ON THE NEW DATA ENTERED
		if(req_id.equals("05") || req_id.equals("08") || req_id.equals("15") || req_id.equals("02") || req_id.equals("16")
				|| req_id.equals("06") || req_id.equals("04") || req_id.equals("01") || req_id.equals("07") || req_id.equals("18") || req_id.equals("12")){ //XXX CHECK IF THE TRANSFER REAPPLY HAS EFFECT ON CLIENT SCHEDULE
			ret = true;
		}
		return ret;
	}

	//CHECKS FOR REQUESTS WITH CREDIT PAYMENTS
	public static Boolean forCredit(String req_id){//CHEKC IF KASAMA TUNG TRASFER REAPP SAKA YUNG REAPPLICATION
		if(req_id.equals("02") || req_id.equals("04") || req_id.equals("06") || req_id.equals("07")
				|| req_id.equals("08") ||req_id.equals("12") || req_id.equals("15") || req_id.equals("16")){
			return true;
		}else{
			return false;
		}
	}

	//CHECKS IF THE PAYMENTS OF THE REQUEST IS ALREADY CREDITED
	public static Boolean isCredited(String req_no){
		pgSelect db = new pgSelect();
		String sql = "select * from rf_credit_payments where request_no = '"+req_no+"'";
		db.select(sql);
		FncSystem.out("Display is credited", sql);
		return db.isNotNull();
	}

	public static Boolean isTotalPaymentsZero(String req_no){
		pgSelect db = new pgSelect();

		String sql = "select a.request_no, coalesce(sum(b.amount), 0.00)\n" + 
				"from req_rt_request_header a\n" + 
				"left join rf_client_ledger b on b.entity_id = a.old_entity_id and b.proj_id = a.old_proj_id and b.pbl_id = getinteger(a.old_unit_id)::VARCHAR and b.seq_no = a.old_seq_no\n" + 
				"where a.request_no = '"+req_no+"' \n"+
				//"and case when a.req_type_id ~*'(07|12)' then b.status_id = 'I' else status_id = 'A' end \n" + 
				"group by a.request_no;";

		db.select(sql);
		FncSystem.out("Display Total Payments", sql);
		BigDecimal total_pmt = (BigDecimal) db.getResult()[0][1];
		if(total_pmt.doubleValue() != 0){
			return false;
		}else{
			return true;
		}
	}

	/*****************END OF VALIDATION*******************/

	public static BigDecimal getMRI(Object [] data3){//COMPUTES FOR THE MRI AMOUNT
		BigDecimal final_availed_amt = (BigDecimal) data3[18];
		String final_entity_class = (String) data3[8];
		pgSelect db = new pgSelect();
		String sql = "select get_mri("+final_availed_amt+", '"+final_entity_class+"', get_group_id('"+final_entity_class+"'), 'SRI')";
		db.select(sql);
		//FncSystem.out("Display MRI", sql);
		return (BigDecimal) db.getResult()[0][0];
	}

	//COMPUTES THE REMAINING DP TERMS
	public static Integer computeRemainDPTerms(String old_entity_id, String old_unit_id, String final_dpterm , String req_id, BigDecimal final_dp_amt){
		Integer dp_term_to_add = new Integer("0");
		Integer paid_terms = new Integer("0");

		pgSelect db = new pgSelect();

		if(req_id.equals("06") || hasChangeClientClass(req_id) || hasChangedPrincipal(req_id)){
			dp_term_to_add = Integer.parseInt(final_dpterm);
		}else{
			String sqlpaidterms = "select count(scheddate)::INTEGER \n" + 
					"from rf_client_schedule \n" + 
					"where entity_id  = '"+old_entity_id+"' \n" + 
					"and unit_id  = '"+old_unit_id+"'\n" + 
					"and part_id= '013' and get_dp_paid_date(entity_id, unit_id, scheddate)!= 0.00 ";
			FncSystem.out("Display paid term", sqlpaidterms);
			db.select(sqlpaidterms);
			paid_terms = (Integer) db.getResult()[0][0];

			dp_term_to_add = Integer.parseInt(final_dpterm) - paid_terms;

			String sql = "select coalesce(sum(principal),0.00) \n"+
					"from rf_client_schedule where entity_id  = '"+old_entity_id+"' \n"+
					"and unit_id  = '"+old_unit_id+"' \n"+
					"and part_id= '013' and get_dp_paid_date(entity_id, unit_id, scheddate)!= 0.00";
			FncSystem.out("Display Sum Principal", sql);
			db.select(sql);
			BigDecimal sum_pricipal = (BigDecimal) db.getResult()[0][0];
			if(sum_pricipal.compareTo(final_dp_amt) >= 1 && req_id.equals("06") == false){
				final_dpterm = paid_terms.toString();
			}
		}
		return dp_term_to_add;
	}

	public static Object [] computeDefaultAmts(Object [] data3){//REMOVE THIS IF COMPUTATIONS ARE CORRECT 
		String final_unit_id = (String) data3[5];
		String final_model_id = (String) data3[6];
		String final_merge_unit_id = (String) data3[24];
		String final_merge_model_id = (String) data3[25];
		String final_entity_class = (String) data3[8];
		BigDecimal final_vat_rate = (BigDecimal) data3[14];
		BigDecimal final_dp_amt = (BigDecimal) data3[16];
		BigDecimal final_dp_rate = (BigDecimal) data3[17];
		BigDecimal final_availed_amt = (BigDecimal) data3[18];
		String old_entity_id = (String) data3[30];
		String old_unit_id = (String) data3[35];
		BigDecimal final_sprice = (BigDecimal) data3[10];
		String old_model_id = (String) data3[40];
		String req_id = (String) data3[60];
		BigDecimal new_disc_amt = (BigDecimal) data3[71];

		Double availed_amt = 0.00;
		Double dp_paid_total = 0.00;
		Double net_sell_price = 0.00;
		Double vat_amt = 0.00;
		Double dp_amt = 0.00;
		Double dp_rate = 0.00;
		Double dp_rate_amt = 0.00;
		Double availed_rate = 0.00;

		net_sell_price = ((final_sprice.doubleValue() - new_disc_amt.doubleValue()) / (1+(final_vat_rate.doubleValue()/100))) ;
		/*System.out.printf("Display final sell price %s%n", final_sprice);
		System.out.printf("Display final disc amt %s%n", new_disc_amt);
		System.out.printf("Display final vat rate %s%n", final_vat_rate);
		System.out.printf("Display net sell price %s%n", net_sell_price);*/
		vat_amt = final_sprice.doubleValue() - (net_sell_price + new_disc_amt.doubleValue());

		pgSelect db = new pgSelect();
		if(getGroupID(final_entity_class).equals("04")){
			if(hasMergeUnit(old_unit_id)){
				String sqlloanableamtmerge = "select get_hdmf_loanable_amt_merge('"+final_unit_id+"','"+final_model_id+"', '"+final_merge_unit_id+"', '"+final_merge_model_id+"')";
				db.select(sqlloanableamtmerge);
				availed_amt = ((BigDecimal) db.getResult()[0][0]).doubleValue();
			}else{
				String sqlhdmfloanableamt = "select get_hdmf_loanable_amt('"+old_unit_id+"', '"+old_model_id+"', "+net_sell_price+")";
				db.select(sqlhdmfloanableamt);
				//FncSystem.out("Display Availed amt", sqlhdmfloanableamt);
				availed_amt = ((BigDecimal) db.getResult()[0][0]).doubleValue();
				//System.out.printf("Display availed amt %s%n", availed_amt);
			}
			dp_amt = net_sell_price - availed_amt.doubleValue();

			String sqlpaidtotal = "select coalesce(sum(amount),'0.00') \n" + 
					"from rf_client_schedule where entity_id  = '"+old_entity_id+"' \n" + 
					"and unit_id  = '"+old_unit_id+"'\n" + 
					"and part_id= '013' and get_dp_paid_date(entity_id, unit_id, scheddate)!= 0.00";
			db.select(sqlpaidtotal);
			dp_paid_total = ((BigDecimal) db.getResult()[0][0]).doubleValue();
			if(dp_paid_total >= dp_amt && (req_id.equals("06") || hasChangeClientClass(req_id) || hasChangedPrincipal(req_id)) == false){
				dp_amt = dp_paid_total.doubleValue();
				availed_amt = net_sell_price - dp_amt;
				//System.out.printf("Dumaan dito 2 (dpamt) %s%n", dp_amt);
			}
			dp_rate = (dp_amt/net_sell_price) * 100;
			availed_rate = 100 - dp_rate;
		}else{
			dp_rate = final_dp_rate.doubleValue();
			dp_rate_amt = final_dp_amt.doubleValue();
			//System.out.printf("Dumaan dito 3 (dpamt) %s%n", final_dp_amt);
			if(dp_rate>0){
				dp_amt = net_sell_price * (dp_rate/100);
				//System.out.printf("Dumaan dito 4 (dpamt) %s%n", dp_amt);
			}else{
				dp_amt = dp_rate_amt;
				//System.out.printf("Dumaan dito 5 (dpamt) %s%n", dp_amt);
			}

			availed_amt = net_sell_price - dp_amt;
			availed_rate = 100 - dp_rate;

			String sqlpaidtotal = "select coalesce(sum(amount),'0.00') \n" + 
					"from rf_client_schedule where entity_id  = '"+old_entity_id+"' \n" + 
					"and unit_id  = '"+old_unit_id+"'\n" + 
					"and part_id= '013' and get_dp_paid_date(entity_id, unit_id, scheddate)!= 0.00";
			db.select(sqlpaidtotal);
			dp_paid_total = ((BigDecimal) db.getResult()[0][0]).doubleValue();
			if(dp_paid_total>= dp_amt && (req_id.equals("06") || hasChangeClientClass(req_id) || hasChangedPrincipal(req_id)) == false){
				dp_amt = dp_paid_total;
				availed_amt = net_sell_price - dp_amt;
				dp_rate = (dp_amt/net_sell_price) * 100;
				availed_rate = 100 - dp_rate;
			}
		}
		//System.out.printf("Display Availed Amt %s%n", availed_amt);
		//System.out.printf("Display final availed amt %s%n", final_availed_amt);
		//System.out.printf("Display final availed amt %s%n", final_ava)
		return new Object []{BigDecimal.valueOf(net_sell_price), BigDecimal.valueOf(vat_amt), final_vat_rate, BigDecimal.valueOf(availed_amt), BigDecimal.valueOf(dp_amt), BigDecimal.valueOf(dp_rate), BigDecimal.valueOf(availed_rate)};
		//return new Object [] {net_sell_price, vat_amt, final_vat_rate.doubleValue(), availed_amt, dp_amt, dp_rate, availed_rate};
	}

	//GET THE PROCESSCING FEE FOR THE MONTHLY AMORTIZATION
	public static BigDecimal getMAProcfee(String final_entity_class, String final_entity_id, BigDecimal final_availed_amt){
		BigDecimal proc_fee_ma = new BigDecimal("0.00");
		pgSelect db = new pgSelect();
		if(getGroupID(final_entity_class).equals("04")){//CHECK ME
			String sqlprocfee = "select get_pagibig_additional('"+final_entity_id+"',"+final_availed_amt+")";
			db.select(sqlprocfee);
			proc_fee_ma = (BigDecimal) db.getResult()[0][0];
		}
		return proc_fee_ma;
	}

	public static BigDecimal getDPBalanceFinal(BigDecimal new_principal_total, String req_id, String old_entity_id, String old_unit_id){

		BigDecimal balance = new_principal_total.subtract(getDPPrincipalTotalPaid(req_id, old_entity_id, old_unit_id));
		return balance;
	}

	public static BigDecimal getDPPrincipalTotalPaid(String req_id, String old_entity_id, String old_unit_id){
		pgSelect db = new pgSelect();
		if(req_id.equals("06") || hasChangeClientClass(req_id) || hasChangedPrincipal(req_id)){
			return new BigDecimal("0.00");
		}else{	
			String sql = "select coalesce(sum(amount)- sum(coalesce(proc_fee,0.00)), 0.00) \n" + //try to add project id here
					"from rf_client_schedule \n" + 
					"where entity_id  = '"+old_entity_id+"' \n" + 
					"and unit_id  = '"+old_unit_id+"'\n" + 
					"and part_id  = '013'  \n" + 
					"and get_dp_paid_date(entity_id, unit_id, scheddate)!= 0.00 ";
			db.select(sql);
			FncSystem.out("Display dp principal total paid", sql);
			if(db.isNotNull()){
				return (BigDecimal) db.getResult()[0][0];
			}else{
				return new BigDecimal("0.00");
			}
		}
	}

	public static BigDecimal getDpFinalPrincipal(Integer dp_term_to_add, String req_id, BigDecimal new_principal_total, String old_entity_id, String old_unit_id){
		BigDecimal dp_balance_final = getDPBalanceFinal(new_principal_total, req_id, old_entity_id, old_unit_id);

		if(dp_balance_final.compareTo(new BigDecimal("0")) == 0 || dp_term_to_add == 0){
			return new BigDecimal("0.00");
		}
		pgSelect db = new pgSelect();
		String sql = "select "+dp_balance_final.doubleValue()/+dp_term_to_add+"";
		db.select(sql);
		return (BigDecimal) db.getResult()[0][0];
	}

	public static String dateFormat(Date date){
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String report_date = df.format(date);
		return report_date;
	}

	public static BigDecimal getGrossDPPerMonth(BigDecimal dp_principal, BigDecimal final_vat_rate, BigDecimal proc_fee){
		BigDecimal dpprincipal = new BigDecimal("0.00");
		dpprincipal = dp_principal.add((dp_principal.multiply(final_vat_rate))).add(proc_fee);
		return dpprincipal;
	}

	public static BigDecimal getGrossDPPerMonthFinal(BigDecimal proc_fee, BigDecimal final_vat_rate, Integer dp_term_to_add, String req_id, 
			BigDecimal new_principal_total, String old_entity_id, String old_unit_id){
		BigDecimal dp_final_principal = getDpFinalPrincipal(dp_term_to_add, req_id, new_principal_total, old_entity_id, old_unit_id);
		BigDecimal dpgross = proc_fee.add(dp_final_principal).add((dp_final_principal.multiply(final_vat_rate)).divide(new BigDecimal("100")));
		return dpgross;
	}

	public static BigDecimal getDPVat(BigDecimal dp_principal, BigDecimal final_vat_rate){
		BigDecimal dp_vat = dp_principal.multiply(final_vat_rate.multiply(new BigDecimal("100")));
		return dp_vat;
	}

	public static BigDecimal getDPVatFinal(Integer dp_term_to_add, String req_id, BigDecimal new_principal_total, String old_entity_id, String old_unit_id, BigDecimal final_vat_rate){
		BigDecimal dp_final_principal = getDpFinalPrincipal(dp_term_to_add, req_id, new_principal_total, old_entity_id, old_unit_id);
		BigDecimal dpvat = dp_final_principal.multiply(final_vat_rate.divide(new BigDecimal("100")));
		return dpvat;
	}

	public static BigDecimal getMAPerMonth(Object [] data3, BigDecimal proc_fee_ma){
		BigDecimal final_vat_rate = (BigDecimal) data3[14];
		BigDecimal final_fire_amt = (BigDecimal) data3[27];
		BigDecimal mri_amt = getMRI(data3);
		BigDecimal mortgage = getMortgage(data3);
		BigDecimal ma_per_month = BigDecimal.valueOf(mortgage.doubleValue() + 
				(mortgage.doubleValue() * final_vat_rate.doubleValue() /100) + mri_amt.doubleValue() + 
				final_fire_amt.doubleValue() + proc_fee_ma.doubleValue());

		//System.out.printf("Display ma per month %s%n", ma_per_month);
		return ma_per_month;
	}

	public static BigDecimal getMortgage(Object [] data3){//COMPUTES THE MORTGAGE
		String final_entity_class = (String) data3[8];
		BigDecimal final_availed_amt = (BigDecimal) data3[18];
		BigDecimal final_int_rate = (BigDecimal) data3[28];
		String final_materm = (String) data3[21];
		BigDecimal comp_factor = new BigDecimal("0.00");
		String sql = "";
		pgSelect db = new pgSelect();

		if(getGroupID(final_entity_class).equals("04")){//FOR PAGIBIG
			sql = "select sp_compute_factor("+final_materm+", "+final_int_rate+")";
			db.select(sql);
			comp_factor = (BigDecimal) db.getResult()[0][0];
			FncSystem.out("Display comp facto pagibigr", sql);
		}else{//FOR INHOUSE
			sql = "select sp_compute_factor_ihf("+final_materm+", "+final_int_rate+")";
			db.select(sql);
			comp_factor = (BigDecimal) db.getResult()[0][0];
			FncSystem.out("Display Comp factor for in House", sql);
		}

		BigDecimal mortgage = BigDecimal.valueOf(final_availed_amt.doubleValue() * comp_factor.doubleValue());
		return mortgage;
	}

	public static BigDecimal getMABalanceFinal(Object [] data3){
		BigDecimal ma_balance_final = new BigDecimal("0.00");
		BigDecimal total_principal_paid = new BigDecimal("0.00");
		BigDecimal final_availed_amt = (BigDecimal) data3[18];
		String old_entity_id = (String) data3[30];
		String old_unit_id = (String) data3[35];
		String req_id = (String) data3[60];

		String sql = "select coalesce(sum(principal), 0.00)\n" + 
				"from rf_client_schedule \n" + 
				"where entity_id  = '"+old_entity_id+"' \n" + 
				"and unit_id  = '"+old_unit_id+"'\n" + 
				"and part_id  = '014' \n" + 
				"and get_ma_principal_paid_date (entity_id, unit_id,scheddate) != 0.00";
		pgSelect db = new pgSelect();
		db.select(sql);
		total_principal_paid = (BigDecimal) db.getResult()[0][0];

		if(req_id.equals("06") || hasChangeClientClass(req_id) || hasChangedPrincipal(req_id))
			total_principal_paid = new BigDecimal("0.00");

		ma_balance_final = final_availed_amt.subtract(total_principal_paid);

		return ma_balance_final;
	}

	public static BigDecimal getMAPerMonthFinal(Object [] data3, BigDecimal mri_amt, BigDecimal proc_fee){
		BigDecimal final_vat_rate = (BigDecimal) data3[14];
		BigDecimal final_fire_amt = (BigDecimal) data3[27];
		Double ma_per_month = getMortgage(data3).doubleValue() + 
				(getMortgage(data3).doubleValue() * final_vat_rate.doubleValue()/100) + mri_amt.doubleValue() 
				+ final_fire_amt.doubleValue() + proc_fee.doubleValue();
		return BigDecimal.valueOf(ma_per_month);
	}

	//

	public static Boolean hasChangeClientClass(String req_id){
		if(req_id.equals("02") || req_id.equals("15")){
			return true;
		}else{
			return false;
		}
	}

	public static Boolean hasChangedPrincipal(String req_id){
		if(req_id.equals("05")){
			return true;
		}else{
			return false;
		}
	}

	public static Boolean hasMergeUnit(String old_unit_id){
		pgSelect db = new pgSelect();
		String sql = "select is_with_merge_unit('"+old_unit_id+"')";
		db.select(sql);
		if(db.getResult()[0][0].equals("t")){
			return true;
		}else{
			return false;
		}
	}

	public static Boolean isCancelled(String final_entity_id, String final_unit_id){//CHECKS IF THE UNIT IS CANCELLED
		pgSelect db = new pgSelect(); //TRY THIS WITH NO UNIT ID
		Boolean isCancelled = false;
		String sql = "select case when currentstatus = '02' then true else false end\n" + 
				"from rf_sold_unit where entity_id  =  '"+final_entity_id+"'\n" + 
				"and pbl_id = getinteger('"+final_unit_id+"')::VARCHAR \n"+
				"and seq_no = (select seq_no from rf_sold_unit where pbl_id = getinteger('"+final_unit_id+"')::VARCHAR and entity_id = '"+final_entity_id+"') ";
		//""+final_seq_no+" and projcode = '"+proj_id+"'";

		/*String sql = "select case when byrstatus_id = '02' then true else false end \n"+
				 	 "from rf_buyer_status where entity_id = '"+final_entity_id+"' \n"+
				 	 "and pbl_id = getinteger('"+final_unit_id+"')::VARCHAR \n" +
				 	 "and seq_no = (select seq_no from rf_sold_unit where entity_id = '"+final_entity_id+"' and pbl_id = getinteger('"+final_unit_id+"')::VARCHAR)";
					 //"and tran_date = ()"*/

		db.select(sql);
		if(db.isNotNull()){
			isCancelled = (Boolean) db.getResult()[0][0];
			return isCancelled;
		}else{
			return false;
		}
	}

	public static BigDecimal setNewIntRate(String final_pmt_scheme){//SETS THE NEW INT RATE BASED ON THE PAYMENT SCHEME  
		pgSelect db = new pgSelect();

		String sql = "select int_rate from mf_payment_scheme where pmt_scheme_id = '"+final_pmt_scheme+"'";
		db.select(sql);
		FncSystem.out("Display New Int Rate", sql);
		return (BigDecimal) db.getResult()[0][0];
	}

	//SETS THE NEW FIRE AMOUNT
	public static BigDecimal setNewFire(String new_unit_id, String final_entity_class, String final_unit_id, String final_model_id){
		pgSelect db = new pgSelect();
		if(new_unit_id == null || new_unit_id.equals("")){//gets the fire amount of the unit based on the model id
			String sql = "select get_fire_of_model('"+final_model_id+"', '"+getGroupID(final_entity_class)+"')";
			db.select(sql);
			FncSystem.out("Display fire model %s%n", sql);
			//System.out.println("Dumaan dito sa Change House Model");
			return (BigDecimal) db.getResult()[0][0];
		}else{//gets the fire amount of the unit based on the unit id
			String sql2 = "select get_fire_of_unit('"+final_unit_id+"', '"+getGroupID(final_entity_class)+"')";
			db.select(sql2);
			FncSystem.out("Display fire of unit", sql2);
			//System.out.println("Dumaan dito sa Trasnfer of unit");
			return (BigDecimal) db.getResult()[0][0];
		}
	}

	public static String getMergeUnitID(String unit_id){//GETS THE UNIT ID OF THE MERGE UNIT
		pgSelect db = new pgSelect();
		String sql = "select get_other_merge_unit('"+unit_id+"')";
		db.select(sql);

		if(db.isNotNull()){
			return (String) db.getResult()[0][0];
		}else{
			return "";
		}
	}

	public static String getMergeModelID(String unit_id){//GETS THE MODEL ID OF THE OTHER MERGE UNIT
		pgSelect db = new pgSelect();
		String sql = "select model_id from mf_unit_info where unit_id = get_other_merge_unit('"+unit_id+"')";
		db.select(sql);
		if(db.isNotNull()){
			return (String) db.getResult()[0][0];
		}else{
			return "";
		}
	}

	public static Object[] setFinalVariables(Object []data1, Object[]data2) {//SETTING OF THE FINAL VARIABLES
		pgSelect db = new pgSelect();

		//FINAL DATA (data3)
		String final_entity_id = "";
		String final_entity_name = "";
		String final_lname = "";
		String final_fname = "";
		String final_mname = "";
		String final_unit_id = "";
		String final_model_id = "";
		String final_agent_id = "";
		String final_entity_class = "";
		String final_pmt_scheme = "";
		BigDecimal final_sprice = new BigDecimal("0.00");
		BigDecimal final_disc_amt = new BigDecimal("0.00");
		BigDecimal final_disc_rate = new BigDecimal("0.00");
		BigDecimal final_vat_amt = new BigDecimal("0.00");
		BigDecimal final_vat_rate = new BigDecimal("0.00");
		BigDecimal final_net_sell_price = new BigDecimal("0.00");
		BigDecimal final_dp_amt = new BigDecimal("0.00");
		BigDecimal final_dp_rate = new BigDecimal("0.00");
		BigDecimal final_availed_amt = new BigDecimal("0.00");
		BigDecimal final_availed_rate = new BigDecimal("0.00");
		//Integer final_dpterm = new Integer("0");
		String final_dpterm = "";
		//Integer final_materm = new Integer("0");
		String final_materm = "";
		Date final_dp_duedate = null;
		Date final_ma_duedate = null;
		String final_merge_unit_id = "";
		String final_merge_model_id = "";
		String final_seq_no = "";
		BigDecimal final_fire_amt = new BigDecimal("0.00");
		BigDecimal final_int_rate = new BigDecimal("0.00");
		String final_lot_area = "";

		//OLD DATA (data1)
		String old_entity_id = (String) data1[0];
		String old_entity_name = (String) data1[1];
		String old_entity_class = (String) data1[2];
		String old_agent_id = (String) data1[3];
		String old_proj_id = (String) data1[4];
		String old_unit_id = (String) data1[5];
		BigDecimal old_sprice = (BigDecimal) data1[6];
		String old_pmt_scheme = (String) data1[7];
		/*String old_dp_duedate = (String) data1[8];
		String old_ma_duedate = (String) data1[9];*/
		Date old_dp_duedate = (Date) data1[8];
		Date old_ma_duedate = (Date) data1[9];
		String old_model_id = (String) data1[10];
		BigDecimal old_disc_amt = (BigDecimal) data1[11];
		//Integer old_materm = (Integer) data1[12];
		String old_materm = (String) data1[12];
		//Integer old_dpterm = (Integer) data1[13];
		String old_dpterm = (String) data1[13];
		BigDecimal old_dp_amt = (BigDecimal) data1[14];
		BigDecimal old_availed_amt = (BigDecimal) data1[15];
		BigDecimal old_disc_rate = (BigDecimal) data1[16];
		BigDecimal old_net_sell_price = (BigDecimal) data1[17];
		BigDecimal old_vat_amt = (BigDecimal) data1[18];
		BigDecimal old_vat_rate = (BigDecimal) data1[19];
		Date start_date = (Date) data1[20];
		Date end_date = (Date) data1[21];
		BigDecimal old_dp_rate = (BigDecimal) data1[22];
		BigDecimal old_availed_rate = (BigDecimal) data1[23];
		BigDecimal old_fire_amt = (BigDecimal) data1[24];
		BigDecimal old_int_rate = (BigDecimal) data1[25];
		String old_seq_no = (String) data1[26];
		String old_lot_area = (String) data1[27];
		String old_merge_unit_id = getMergeUnitID(old_unit_id);
		String old_merge_model_id = getMergeModelID(old_unit_id);

		//NEW DATA (data2)

		String req_id = (String) data2[0];
		String new_entity_id = (String) data2[1];
		String new_lname = (String) data2[2];
		String new_fname = (String) data2[3];
		String new_mname = (String) data2[4];
		String new_unit_id = (String) data2[5];
		String new_model_id = (String) data2[6];
		String new_agent_id = (String) data2[7];
		String new_entity_class = (String) data2[8];
		String new_pmt_scheme = (String) data2[9];
		BigDecimal new_sprice = (BigDecimal) data2[10];
		BigDecimal new_disc_amt = (BigDecimal) data2[11];
		BigDecimal new_disc_rate = (BigDecimal) data2[12];
		BigDecimal new_net_sell_price = (BigDecimal) data2[13];
		BigDecimal new_dp_amt = (BigDecimal) data2[14];
		BigDecimal new_dp_rate = (BigDecimal) data2[15];
		//Integer new_dpterm = (Integer) data2[16];
		String new_dpterm = (String) data2[16];
		BigDecimal new_availed_amt = (BigDecimal) data2[17];
		BigDecimal new_availed_rate = (BigDecimal) data2[18];
		//Integer new_materm = (Integer) data2[19];
		String new_materm = (String) data2[19];
		Date new_dp_duedate = (Date) data2[20];
		Date new_ma_duedate = (Date) data2[21];
		String new_lot_area = (String) data2[22];
		BigDecimal new_fire_amt = (BigDecimal) data2[23];
		BigDecimal new_int_rate = (BigDecimal) data2[24];
		BigDecimal new_vat_amt = (BigDecimal) data2[25];
		BigDecimal new_vat_rate = (BigDecimal) data2[26]; 
		String new_merge_model_id = getMergeModelID(new_unit_id);
		String new_merge_unit_id = getMergeUnitID(new_unit_id);
		String new_sched_date = (String) data2[27];
		String new_civil_status = (String) data2[28];
		String purpose = (String) data2[29]; //PURPOSE IN CHANGING THE PRINCIPAL APPLICANT

		/*******FOR DP DUE DATE******/
		if(new_dp_duedate == null){
			final_dp_duedate = old_dp_duedate;
		}else{
			final_dp_duedate = new_dp_duedate;
		}
		/*******FOR MA DUE DATE*******/
		if(new_ma_duedate == null){
			final_ma_duedate = old_ma_duedate;
		}else{
			final_ma_duedate = new_ma_duedate;
		}
		/******FOR ENTITY ID******/
		if(new_entity_id == null){
			final_entity_id = old_entity_id;
		}else{
			final_entity_id = new_entity_id;
		}

		/*******FOR the UNIT*********/
		if(new_unit_id == null){ 
			final_unit_id = old_unit_id;
			final_lot_area = old_lot_area;
			final_seq_no = old_seq_no;
			final_merge_model_id = old_merge_model_id;
			final_merge_unit_id = old_merge_unit_id;
		}else{
			final_unit_id = new_unit_id;
			final_lot_area = new_lot_area;
			final_merge_model_id = new_merge_model_id;
			final_merge_unit_id = new_merge_unit_id;

			if(isCancelled(final_entity_id, final_unit_id)){
				String sql = "select seq_no::char from rf_sold_unit where entity_id = '"+final_entity_id+"' and pbl_id = (getinteger('"+final_unit_id+"')::VARCHAR) and status_id = 'I'";
				db.select(sql);
				final_seq_no  = (String) db.getResult()[0][0];
				System.out.println("Dumaan dito sa cancelled");
			}else{ //XXX CHECK ME
				String sql = "select (coalesce(max(seq_no),0)+ 1)::char\n" + 
						"from rf_sold_unit \n" + 
						"where pbl_id  = getinteger('"+final_unit_id+"')::VARCHAR";
				db.select(sql);
				final_seq_no = (String) db.getResult()[0][0];
				System.out.println("Dumaan dito sa add ng seq no");
			}
			System.out.printf("Display final seq no %s%n", final_seq_no);
			new_fire_amt = setNewFire(new_unit_id, final_entity_class, final_unit_id, final_model_id);
		}
		/*******FOR AGENT********/
		if(new_agent_id == null){ 
			final_agent_id = old_agent_id;
			//XXX FINAL MKT
		}else{
			final_agent_id = new_agent_id;
			//XXX FINAL MKT
		}
		/********FOR BUYER TYPE*******/
		if(new_entity_class == null){//ok na
			final_entity_class = old_entity_class;
		}else{
			final_entity_class = new_entity_class;
		}
		/******FOR HOUSE MODEL AND FIRE*******/
		if(new_model_id == null){ //OK NA
			final_model_id = old_model_id;
			new_fire_amt = setNewFire(new_unit_id, final_entity_class, final_unit_id, final_model_id);
			if(hasMergeUnit(old_unit_id)){
				if(final_model_id != null){
					String sql = "select get_fire_of_merge2('"+final_model_id+"', '"+final_merge_model_id+"')/12";
					db.select(sql);
					final_fire_amt = (BigDecimal) db.getResult()[0][0]; 
				}else{
					String sql = "select get_fire_of_merge2('"+old_model_id+"', '"+final_merge_model_id+"')/12";
					db.select(sql);
					final_fire_amt = (BigDecimal) db.getResult()[0][0];

				}
			}else{
				if(new_entity_class == null){
					final_fire_amt = old_fire_amt;
				}else{
					final_fire_amt = new_fire_amt;
				}
			}
		}else{
			/*System.out.printf("Display new_unit_id: %s%n", new_unit_id);
			System.out.printf("Display final_entity_class: %s%n", final_entity_class);
			System.out.printf("Display final_unit_id: %s%n", final_unit_id);
			System.out.printf("Display final_model_id: %s%n", final_model_id);*/
			
			new_fire_amt = setNewFire(new_unit_id, final_entity_class, final_unit_id, final_model_id);
			//System.out.println("Dumaan dito sa setFire ng FInal Variables");
			final_model_id = new_model_id;

			if(hasMergeUnit(old_unit_id)){
				if(final_model_id != null){
					String sql = "select get_fire_of_merge2('"+final_model_id+"', '"+final_merge_model_id+"')/12";
					db.select(sql);
					final_fire_amt = (BigDecimal) db.getResult()[0][0];
				}
				String sql = "select get_fire_of_merge2('"+old_model_id+"', '"+final_merge_model_id+"')/12";
				db.select(sql);
				final_fire_amt = (BigDecimal) db.getResult()[0][0];

			}else{
				final_fire_amt = new_fire_amt;
			}
		}

		/********FOR SELLING PRICE*******/ //OK NA
		if(new_sprice.doubleValue() == 0){
			final_sprice = old_sprice;
		}else{
			final_sprice = new_sprice;
		}
		/*********FOR DISC RATE********/ //OK NA
		if(new_disc_rate.doubleValue() == 0){
			final_disc_rate = old_disc_rate;
			final_disc_amt = old_disc_amt;
		}else{
			final_disc_rate = new_disc_rate;
			final_disc_amt = new_disc_amt;
		}
		/******FOR NET SELLING PRICE*****/ //OK NA
		if(new_net_sell_price.doubleValue() == 0){
			final_net_sell_price = old_net_sell_price;
		}else{
			final_net_sell_price = new_net_sell_price;
		}

		/********FOR DP AMOUNT********/ //OK NA
		if(new_dp_amt.doubleValue() == 0){
			final_dp_amt = old_dp_amt;
		}else{
			final_dp_amt = new_dp_amt;
		}
		/******FOR DP TERM*****/ //OK NA
		if(new_dpterm.equals("")){ 
			final_dpterm = old_dpterm;
		}else{
			final_dpterm = new_dpterm;
		}
		/********FOR AVAILED AMT*******/ //OK NA
		if(new_availed_amt.compareTo(new BigDecimal("0.00")) == 0 || new_availed_amt == null){
			final_availed_amt = old_availed_amt;
		}else{
			final_availed_amt = new_availed_amt;
		}
		/********FOR MA TERM******/ //OK NA
		if(new_materm.equals("")){
			final_materm = old_materm;
		}else{
			final_materm = new_materm;
		}
		/******FOR PAYMENT SCHEME******/ //OK NA
		if(new_pmt_scheme == null){
			final_pmt_scheme = old_pmt_scheme;
			final_int_rate = old_int_rate;
		}else{
			final_pmt_scheme = new_pmt_scheme;
			final_int_rate = new_int_rate;
		}

		/*****FOR VAT AMT****/ //OK NA
		if(new_vat_amt.doubleValue() == 0){
			final_vat_amt = old_vat_amt;
		}else{
			final_vat_amt = new_vat_amt;
		}
		/******FOR VAT RATE*******/ //OK NA
		if(new_vat_rate.doubleValue() == 0){
			final_vat_rate = old_vat_rate;
		}else{
			final_vat_rate = new_vat_rate;
		}
		/*******FOR DP RATE********/
		if(new_dp_rate.doubleValue() == 0){
			final_dp_rate = old_dp_rate;
		}else{
			final_dp_rate = new_dp_rate;
		}
		/********FOR AVAILEDRATE******/
		if(new_availed_rate.doubleValue() == 0){
			final_availed_rate = old_availed_rate;
		}else{
			final_availed_rate = new_availed_rate;
		}
		
		//System.out.printf("Display final_sprice:: %s%n", final_sprice);
		//System.out.printf("Display final_dp_due_date: %s%n", final_dp_duedate);
		System.out.printf("Display Final Seq. No: %s%n", final_seq_no);

		return new Object []{final_entity_id, final_entity_name,
				final_lname, final_mname,final_fname, final_unit_id, final_model_id, final_agent_id, final_entity_class,
				final_pmt_scheme, final_sprice, final_disc_amt, final_disc_rate, final_vat_amt, final_vat_rate, 
				final_net_sell_price, final_dp_amt, final_dp_rate, final_availed_amt, final_availed_rate, final_dpterm, 
				final_materm, final_dp_duedate, final_ma_duedate, final_merge_unit_id, final_merge_model_id, final_seq_no, 
				final_fire_amt, final_int_rate, final_lot_area,
				old_entity_id, old_entity_name, old_entity_class, old_agent_id, old_proj_id, old_unit_id, old_sprice, 
				old_pmt_scheme, old_dp_duedate, old_ma_duedate, old_model_id, old_disc_amt, old_materm, old_dpterm, old_dp_amt, 
				old_availed_amt, old_disc_rate, old_net_sell_price, old_vat_amt, old_vat_rate, start_date, end_date, old_dp_rate, 
				old_availed_rate, old_fire_amt, old_int_rate, old_seq_no, old_lot_area, old_merge_unit_id, old_merge_model_id,
				req_id,new_entity_id, new_lname, new_fname, new_mname, new_unit_id, new_model_id, new_agent_id, new_entity_class, 
				new_pmt_scheme, new_sprice, new_disc_amt, new_disc_rate, new_net_sell_price, new_dp_amt, new_dp_rate, new_dpterm, 
				new_availed_amt, new_availed_rate, new_materm, new_dp_duedate, new_ma_duedate, new_lot_area, new_fire_amt, 
				new_int_rate, new_vat_amt, new_vat_rate, new_merge_model_id, new_merge_unit_id, new_sched_date, new_civil_status, purpose};	

	}	
}
