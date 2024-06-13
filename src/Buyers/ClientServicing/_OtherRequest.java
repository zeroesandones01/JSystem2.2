/**
 * 
 */
package Buyers.ClientServicing;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

import javax.swing.DefaultListModel;
import javax.swing.JList;

import Database.pgSelect;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.UserInfo;
import tablemodel.modelOtherRequest_RequestType;

/**
 * @author John Lester Fatallo	
 */

public class _OtherRequest{

	/**SQL'S FOR THE LOOKUPS IN THE OTHER REQUEST**/

	//DISPLAYS THE REQUEST TO BE SELECTED IN THE TABLE
	public static String sqlRequest(){ //CHECK IF WILL ADD CHANGE PAGIBIG CONTRIBUTION HERE
		return "SELECT FALSE, request_id as \"Req. ID\", request_desc as \"Request Type\",byrstatus_id as \"Buyer ID\"\n" + 
			   "FROM mf_request_type WHERE request_id IN ('02', '03', '05', '06', '07', '08', '12' ,'13','15', '16', '25', '26','27', '28','01', '18', '04')"+
			   "ORDER BY rqst_seq";
	}

	//RETURN THE CLIENTS TO BE DISPLAYED IN THE OLD DATA
	public static String sqlEntityLookUp(){

		return "SELECT TRIM(a.entity_id) AS \"Entity ID\", get_client_name(a.entity_id) AS  \"Name\", \n" + 
				"FORMAT('%s-%s-%s', c.phase, c.block, c.lot) as \"Ph-Blk-Lt\",\n" + 
				"TRIM(a.pbl_id) AS \"PBL\", a.seq_no AS \"Seq. No\", \n" + 
				"TRIM(a.projcode) AS \"Proj. Code\", TRIM(e.proj_name) AS \"Project Name\",\n" + 
				"TRIM(c.unit_id) AS \"Unit ID\", TRIM(c.description) AS \"Description\",\n" + 
				"TRIM(a.currentstatus) AS \"Status ID\", TRIM(d.status_desc) AS \"Unit Status\",\n" + 
				"TRIM(a.pmt_scheme_id) AS \"Pmt ID\" ,TRIM(f.pmt_scheme_desc) AS \"Payment Scheme\"\n" + 
				"FROM rf_sold_unit a\n" + 
				"LEFT JOIN rf_entity b ON b.entity_id = a.entity_id and coalesce(b.server_id, '') = coalesce(a.server_id, '')\n" + 
				"LEFT JOIN mf_unit_info c ON c.proj_id = a.projcode and c.pbl_id = a.pbl_id and COALESCE(c.server_id, '') = COALESCE(a.server_id, '') AND COALESCE(c.proj_server, '') = COALESCE(c.proj_server, '') \n" + 
				"LEFT JOIN mf_buyer_status d ON d.byrstatus_id = a.currentstatus and coalesce(d.server_id, '') = coalesce(a.server_id, '')\n" + 
				"LEFT JOIN mf_project e ON e.proj_id = a.projcode\n" + 
				"LEFT JOIN mf_payment_scheme f ON f.pmt_scheme_id = a.pmt_scheme_id and coalesce(f.server_id, '') = coalesce(a.server_id, '')\n" + 
				"LEFT JOIN mf_product_model g ON g.model_id = a.model_id and COALESCE(g.server_id, '') = COALESCE(a.server_id, '') AND COALESCE(g.proj_server, '') = COALESCE(a.proj_server, '') \n" + 
				"LEFT JOIN mf_buyer_type h ON h.type_id = a.buyertype\n" + 
				"ORDER BY get_client_name(a.entity_id)";

	}
	
	public static String sqlSearch(){
		return "select trim(a.request_no) as \"Req. No\",\n" + 
				"TRIM(c.req_status_desc) as \"Req Status\",\n" + 
				"a.request_date as \"Request Date\", \n" + 
				"trim(a.old_entity_id) as \"Entity ID\",\n" + 
				"trim(a.old_entity_name) as \"Entity Name\",\n" + 
				"trim(a.old_proj_id) as \"Proj. ID\", \n" + 
				"trim(getinteger(a.old_unit_id)::VARCHAR) as \"PBL\",\n" + 
				"trim(a.old_unit_id) as \"Unit ID\",\n" + 
				"trim(b.description) as \"Unit Description\",\n" + 
				"a.old_seq_no as \"Seq. No\"\n" + 
				"from req_rt_request_header a\n" + 
				"left join mf_unit_info b on b.unit_id = a.old_unit_id\n" + 
				"left join mf_request_status c on c.req_status_id = a.request_status\n" + 
				"where a.old_entity_id != '' \n" + 
				"and req_type_id not in ('Credit of Payment', 'Refund of Payment', 'TECHNICAL DOCUMENTS')\n" + 
				"order by a.request_date DESC;\n";
	}

	//SQL FOR LOOKUP OF CHANGE PRINCIPAL APPLICANT
	public static String sqlChangePrincipalApp(String entity_id){// OK NA DITO
		return "select trim(entity_id) as \"Entity ID\", trim(entity_name) as \"Name\" from rf_entity where entity_id != '"+entity_id+"'";
	}

	//SQL FOR THE LOOKUP OF THE PROJECT
	public static String sqlProject(){//OK NA DITO
		return "select trim(proj_id) as \"ID\", trim(proj_name) as \"Name\" from mf_project where status_id = 'A';\n";
	}

	//SQL FOR THE CHOOSING OF NEW UNIT 
	public static String sqlUnit(String proj_id, String unit_id){// OK NA DITO

		return "SELECT TRIM(a.unit_id) AS \"Unit ID\", TRIM(a.description) AS \"Unit Description\",\n"+
			   "FORMAT('%s-%s-%s', a.phase, a.block, a.lot) AS \"Ph-Blk-Lt\", \n" + 
		   	   "TRIM(get_project_name(a.proj_id)) AS \"Project\", TRIM(a.model_id) AS \"Model ID\", \n" + 
		   	   "TRIM(get_model_desc(a.model_id)) AS \"Model Name\", (a.lotarea)::VARCHAR AS \"Lot Area\"\n" + 
		   	   "FROM mf_unit_info a\n" + 
		   	   "LEFT JOIN rf_marketing_scheme_main b ON b.proj_id = a.proj_id AND b.sub_proj_id = a.sub_proj_id\n" + 
		   	   "WHERE a.status_id = 'A'\n" + 
		   	   "AND a.proj_id = '"+proj_id+"' \n" + 
		   	   //"AND a.unit_id != '"+unit_id+"' \n" + 
		   	   "AND b.status_id = 'P' \n"+
		   	   "AND a.pbl_id not in (select pbl_id from rf_sold_unit where projcode = '"+proj_id+"' and currentstatus != '02' and status_id = 'A')\n" + 
		   	   "ORDER BY a.unit_id;";

	}

	//SQL FOR THE CHOOSING OF NEW HOUSE MODEL
	public static String sqlHouseModel(String unit_id){// OK NA DITO

		return "select distinct on(model_desc) model_id as \"Model ID\", model_desc as \"Model\", "+
		"model_cost as \"Model Cost\", fire_insurance as \"Fire Insurance\", floor_area as \"Floor Area\" "+
		"from mf_product_model\n" + 
		"where status_id  = 'A' and floor_area <= (select lotarea from mf_unit_info where unit_id = '"+unit_id+"') order by model_desc\n";
	}

	//SQL FOR THE LOOKUP OF NEW SELLING AGENT
	public static String sqlSellingAgent(String agent_id, String selected_req){// OK NA DITO
		
		String sql = "";
		
		if(selected_req.contains("17") || selected_req.contains("07")){
			sql = "select trim(division_code) as \"ID\", trim(division_name) as \"Division Name\" from mf_division where division_code in ('06', '07', '08', '09', '29')";
		}else{
			sql = "select trim(a.entity_id) as \"Agent ID\", trim(a.entity_name) as \"Agent Name\", trim(c.division_code) as \"Division ID\", trim(c.division_name) as \"Division Name\", \n" + 
					"trim(c.division_alias) as \"Division Alias\" \n" + 
					"from rf_entity a\n" + 
					//"inner join rf_sellingagent_info b on b.agent_id = a.entity_id\n" +
					"inner join mf_sellingagent_info b on b.agent_id = a.entity_id\n" + 
					"inner join mf_division c on c.division_code = b.sales_div_id\n" + 
					"where a.entity_id in (select entity_id from rf_entity_assigned_type where entity_type_id in ('14', '03', '04', '35', '34', '24', '40', '41', '38')) \n" + 
					//"(select entity_type_id from mf_entity_type where entity_type_desc ~*'agent'))\n"+
					"and b.agent_id != '"+agent_id+"'";
		}
		
		return sql;
	}

	//SQL FOR THE LOOKUP OF NEW BUYERTYPE
	public static String sqlBuyerType(String unit_id, String old_buyer_type , String selected_req){
		String sqlBuyerType = ""; // OK NA DITO

		String sql = "select proj_id, model_id from mf_unit_info where unit_id = '"+unit_id+"'";
		pgSelect db = new pgSelect();
		db.select(sql);
		if(db.isNotNull()){
			String proj_id = (String) db.getResult()[0][0];
			String model_id = (String) db.getResult()[0][1];

			if(selected_req.contains("06")){
				//System.out.println("Dumaan dito sa Transfer of unit \n");

				sqlBuyerType = "select distinct(a.unnest) as \"Type ID\", b.type_desc as \"Description\" \n" + 
						"from(select unnest(type_id) \n" + 
						"from mf_payment_scheme\n" + 
						"where status_id = 'A'\n" + 
						"and '"+proj_id+"' = ANY(proj_id)\n" + 
						"and '"+model_id+"' = ANY(model_id)) a\n" + 
						"left join mf_buyer_type b on b.type_id = a.unnest\n" + 
						"where a.unnest = b.type_id";

			}else{
				//System.out.println("Dumaan dito sa Client Class \n");

				sqlBuyerType = "select distinct(a.unnest) as \"Type ID\", b.type_desc as \"Description\"\n" + 
						"from (select unnest(type_id) from \n" + 
						"mf_payment_scheme\n" + 
						"where status_id = 'A'\n" + 
						"and '"+proj_id+"' = ANY(proj_id)\n" + 
						"and '"+model_id+"' = ANY(model_id)) a\n" + 
						"left join mf_buyer_type b on b.type_id = a.unnest \n"+ 
						"where case when '"+selected_req+"' !~*'12' AND '"+old_buyer_type+"' != '09' then a.unnest != '"+old_buyer_type+"' else true end";
			}
		}
		
		return sqlBuyerType;
	}

	//SQL FOR THE LOOKUP OF THE NEW PAYMENT SCHEME 
	public static String sqlPaymentScheme(String unit_id, String buyer_type){

		String sql = "select proj_id, sub_proj_id, model_id from mf_unit_info where unit_id  = '"+unit_id+"'";
		pgSelect db = new pgSelect();
		db.select(sql);
		String proj_id = (String) db.getResult()[0][0];
		String sub_proj_id = (String) db.getResult()[0][1];
		String model_id = (String) db.getResult()[0][2];

		return  "select trim(pmt_scheme_id) as \"ID\", trim(pmt_scheme_desc) as \"Description\"" +
				"from mf_payment_scheme " +
				"where '"+proj_id+"' = ANY(proj_id) " +
				"and '"+model_id+"' = ANY(model_id) " +
				"and '"+buyer_type+"' = ANY(type_id) " +
				"and '"+sub_proj_id+"' = ANY(phase) "+ 
				"and status_id = 'A' \n"+
				"and pmt_scheme_id != '028' \n" +
				"order by pmt_scheme_id";

	}
	
	/**SQL'S FOR THE LOOKUPS IN THE OTHER REQUEST**/

	/**DISPLAY ITEMS IN THE TABLES**/
	//DISPLAY THE SELECTED REQ ID FROM THE DATABASE BASED ON THE REQUEST NO
	public static void displaySelectedReq(String req_no, modelOtherRequest_RequestType model, JList rowHeader){// OK NA DITO
		if(model != null && rowHeader != null){
			FncTables.clearTable(model);
			DefaultListModel listmodel = new DefaultListModel();
			rowHeader.setModel(listmodel);

			String sql = "select request_id, request_desc,byrstatus_id \n"+
					"from mf_request_type where request_id in (select regexp_split_to_table(req_type_id,',')  \n" + 
					"from req_rt_request_header where request_no  = '"+req_no+"')order by rqst_Seq";
			pgSelect db = new pgSelect();
			db.select(sql);
			FncSystem.out("Display Selected Request", sql);
			
			if(db.isNotNull()){
				for(int x = 0; x<db.getRowCount(); x++){
					model.addRow(db.getResult()[x]);
					listmodel.addElement(model.getRowCount());
				}
			}
		}
	}
	
	public static Object [] displayNewData(String request_no){
		pgSelect db = new pgSelect();
		String SQL = "SELECT * FROM view_request_new_data('"+request_no+")";
		db.select(SQL);
		
		return new Object [] {};
	}

	//DISPLAYS THE REQUEST TYPES TO BE SELECTED IN THE TABLE
	public static void displayRequestType(modelOtherRequest_RequestType model, JList rowHeader){
		FncTables.clearTable(model);
		DefaultListModel listModel = new DefaultListModel();
		rowHeader.setModel(listModel);

		String sql = "select false, request_id, request_desc,byrstatus_id\n" + 
				"from mf_request_type where request_id in ('01', '02', '03', '04', '05', '06', '07', '08', '12' ,'15', '16', '25') --where status_id  = 'A' "+
				"order by rqst_seq";

		//FncSystem.out("Display request type", sql);
		pgSelect db = new pgSelect();
		db.select(sql);

		if(db.isNotNull()){
			for(int x = 0; x<db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
				listModel.addElement(model.getRowCount());
			}
		}
	}

	/**DISPLAY ITEMS IN THE TABLES END**/

	/**GET**/
	
	public static Date getReserVationDate(String entity_id, String proj_id, String unit_id, Integer seq_no){
		String sql = "SELECT get_reservation_date('"+entity_id+"', getinteger('"+unit_id+"')::VARCHAR, "+seq_no+", '"+proj_id+"', 1)";
		
		pgSelect db = new pgSelect();
		db.select(sql);
		FncSystem.out("Display sql for Reservation Date", sql);
		
		return (Date) db.getResult()[0][0];
	}

	public static String getRequestNo(){//Get the next request no from req_rt_request_header 
		pgSelect db = new pgSelect();
		String req_no = "";
		String sql = "SELECT get_new_request_no();";
		db.select(sql);
		//FncSystem.out("Display generation of new request_no", sql);
		
		if(db.isNotNull()){
			req_no = (String) db.getResult()[0][0];
		}
		return req_no;
	}

	//Check setting of Fire amount here when inhouse
	public static BigDecimal setNewFire(String new_unit_id,String final_unit_id ,String final_buyer_type, String final_model_id){
		pgSelect db = new pgSelect();
		// CHECK THE SETTING OF THE FIRE AMOUNT HERE ONCE THE FIRE INSURANCE MODULE IS FINISHED
		String sql = "";

		if(new_unit_id == null){//OK NA YUNG SA PAGIBIG DITO
			//System.out.println("Dumaan dito sa Change of Model");
			sql = "select get_fire_of_model('"+final_model_id+"', get_group_id('"+final_buyer_type+"'))"; 
			db.select(sql);
		}else{
			//System.out.println("Dumaan dito sa Transfer of Unit");
			sql = "select get_fire_of_unit('"+final_unit_id+"', get_group_id('"+final_buyer_type+"'))";
			db.select(sql);
		}
		FncSystem.out("Display SQL For FireAMount", sql);
		
		return (BigDecimal) db.getResult()[0][0];
	}

	public static BigDecimal setNewIntRate(String pmt_scheme_id){//OK NA DITO
		pgSelect db = new pgSelect();

		String sql = "select int_rate from mf_payment_scheme where pmt_scheme_id = '"+pmt_scheme_id+"'";
		db.select(sql);
		//FncSystem.out("Display new int rate: ", sql);
		
		return (BigDecimal) db.getResult()[0][0];
	}
	
	public static String getMergeUnitID(String unit_id){
		pgSelect db = new pgSelect();
		
		String sql = "select get_other_merge_unit('"+unit_id+"')";
		db.select(sql);
		
		if(db.isNotNull()){
			return (String) db.getResult()[0][0];
		}else{
			return "";
		}
	}
	
	public static String getMergeModelID(String unit_id){
		pgSelect db = new pgSelect();
		String sql = "select model_id from mf_unit_info where unit_id = get_other_merge_unit('"+unit_id+"')";
		db.select(sql);
		
		if(db.isNotNull()){
			return (String) db.getResult()[0][0];
		}else{
			return "";
		}
	}
	
	public static BigDecimal getDPPaidTotal(String entity_id, String proj_id ,String unit_id, String seq_no){
		pgSelect db = new pgSelect();
		
		String sql = "select coalesce(sum(amount), 0.00)\n" + 
					 "from rf_client_schedule \n"+
					 "where entity_id = '"+entity_id+"'\n" + 
					 "and proj_id = '"+proj_id+"'\n"+
					 "and pbl_id = getinteger('"+unit_id+"')::VARCHAR \n" + 
					 "and seq_no = "+seq_no+"\n" + 
					 "and get_dp_paid_date(entity_id, proj_id, pbl_id ,seq_no , scheddate) != 0.00;";
		db.select(sql);
		
		//FncSystem.out("Display total paid dp", sql);
		BigDecimal dp_paid_total = (BigDecimal) db.getResult()[0][0];
		return dp_paid_total;
	}
	
	//COMPUTES THE NEW LOANABLE AMOUNT FOR HDMF BASED ON THE DATA FROM THE PRICELIST
	public static BigDecimal getPagibigLoanableAmt(String unit_id, String model_id ,BigDecimal nsp){
		pgSelect db = new pgSelect();
		
		String sql = "select get_hdmf_loanable_amt('"+unit_id+"', '"+model_id+"', "+nsp+")";
		db.select(sql);
		
		FncSystem.out("Display sql for loanable amount", sql);
		
		BigDecimal loan_amt = (BigDecimal) db.getResult()[0][0];
		
		return loan_amt;
	}
	
	public static BigDecimal getFireHouseAPV_Rounded(String new_model_id){
		BigDecimal fire_house_apv = new BigDecimal("0.00");
		
		pgSelect db = new pgSelect();
		String SQL = "SELECT get_fire_house_apv(get_house_appraised_value('"+new_model_id+"'))";
		db.select(SQL);
		
		fire_house_apv = (BigDecimal) db.getResult()[0][0];
		
		BigDecimal fire_house_apv_rounded = fire_house_apv.setScale(2, RoundingMode.UP);
		
		//System.out.printf("Display Fire House apv Not rounded: %s%n", args)
		System.out.printf("Display Fire House APV: %s%n", fire_house_apv_rounded);
		
		return fire_house_apv_rounded;
	}
	
	public static BigDecimal getMiscFees(){
		BigDecimal misc_fees = new BigDecimal("0.00");
		BigDecimal full_misc_fee = new BigDecimal("120604");
		
		misc_fees = (full_misc_fee.multiply(new BigDecimal(".82"))).setScale(-3, RoundingMode.DOWN);
		
		return misc_fees;
	}
	
	public static BigDecimal computeEquityFinance(String unit_id, BigDecimal loanable_amt){
		pgSelect db = new pgSelect();
		
		String SQL = "SELECT sp_compute_equity_bank_finance('"+unit_id+"', "+loanable_amt+")";
		db.select(SQL);
		
		if(db.isNotNull()){
			return (BigDecimal) db.getResult()[0][0];
		}else{
			return new BigDecimal("0.00");
		}
		
	}
	
	public static BigDecimal computeMaximizedLoanableAmt(Object [] data, BigDecimal credit_amt){
		String old_entity_id = (String) data[62];
		String old_proj_id = (String) data[65];
		String old_unit_id = (String) data[66];
		String old_seq_no = (String) data[67];
		String final_entity_id = (String) data[0];
		String final_proj_id = (String) data[2];
		String final_unit_id = (String) data[3];
		String final_seq_no = (String) data[4];
		String final_model_id = (String) data[6];
		BigDecimal final_nsp = (BigDecimal) data[15];
		
		pgSelect db = new pgSelect();
		
		String sql = "SELECT sp_compute_loan_maximization('"+old_entity_id+"', '"+old_proj_id+"', '"+old_unit_id+"', "+old_seq_no+", \n"+
					 "'"+final_entity_id+"', '"+final_proj_id+"', '"+final_unit_id+"', "+final_seq_no+", '"+final_model_id+"', "+final_nsp+", "+credit_amt+")";
		db.select(sql);
		
		FncSystem.out("Display sql for computation of maximum loanable amt", sql);
		
		BigDecimal max_loan_amt = (BigDecimal) db.getResult()[0][0];
		
		return max_loan_amt;
	}
	
	public static String getPBL(String unit_id){
		pgSelect db = new pgSelect();
		
		String sql = "SELECT getinteger('"+unit_id+"')::VARCHAR";
		db.select(sql);
		
		FncSystem.out("Display sql for getinteger", sql);
		return (String) db.getResult()[0][0];
		
	}
	
	public static Integer getFrequencyNo(String entity_id, String proj_id, String unit_id, Integer seq_no){
		pgSelect db = new pgSelect();
		
		Integer frequency_no = null;
		
		String sql = "SELECT (getinteger(COALESCE(frequency, '0'))) + 1\n" + 
					 "FROM cm_schedule_dl \n" + 
					 "WHERE account_code = '"+entity_id+"'\n" + 
					 "AND projcode = '"+proj_id+"' \n" + 
					 "AND pbl_id = getinteger('"+unit_id+"')::VARCHAR \n" + 
					 "AND seq_no = "+seq_no+" \n" + 
					 "LIMIT 1;";
		db.select(sql);
		
		FncSystem.out("Display sql for Frequency No", sql);
		
		if(db.isNotNull()){
			frequency_no = (Integer) db.getResult()[0][0];
		}else{
			frequency_no = 1;
		}
		
		return frequency_no;
	}
	
	public static Boolean isOR(String entity_id, String proj_id, String unit_id, Integer seq_no){
		pgSelect db = new pgSelect();
		
		String SQL = "SELECT * FROM rf_buyer_status where entity_id = '"+entity_id+"' and proj_id = '"+proj_id+"' and pbl_id = getinteger('"+unit_id+"')::VARCHAR and seq_no = "+seq_no+" and byrstatus_id = '01'";
		db.select(SQL);
		
		if(db.isNotNull()){
			return true;
		}else{
			return false;
		}
	}
	
	/**GET END**/

	/****START OF SAVE*****/
	public static Boolean saveRtRequestHeader(String req_no , Object [] data){//OK NA DITO 
		
		String final_entity_id = (String) data[0];
		String final_civil_status = (String) data[1];
		String final_proj_id = (String) data[2];
		String final_unit_id = (String) data[3];
		String final_seq_no = (String) data[4];
		String final_lot_area = (String) data[5];
		String final_model_id = (String) data[6];
		String final_agent_id = (String) data[7];
		String final_buyer_type = (String) data[8];
		String final_pmt_scheme = (String) data[9];
		BigDecimal final_gsp = (BigDecimal) data[10];
		BigDecimal final_disc_amt = (BigDecimal) data[11];
		BigDecimal final_disc_rate = (BigDecimal) data[12];
		BigDecimal final_dp_amt = (BigDecimal) data[16];
		BigDecimal final_dp_rate = (BigDecimal) data[17];
		String final_dp_term = (String) data[18];
		BigDecimal final_availed_amt = (BigDecimal) data[20];
		BigDecimal final_availed_rate = (BigDecimal) data[21];
		String final_ma_term = (String) data[22];
		BigDecimal final_fire_amt = (BigDecimal) data[25];
		String selected_req = (String) data[28];
		String new_first_name = (String) data[30];
		String new_middle_name = (String) data[31];
		String new_last_name = (String) data[32];
		Date new_dp_duedate = (Date) data[50];
		Date new_ma_duedate = (Date) data[54];
		String req_client = (String) data[59];
		String req_remarks = (String) data[60];
		String req_purpose = (String) data[61];
		String old_entity_id = (String) data[62];
		String old_proj_id = (String) data[65];
		String old_unit_id = (String) data[66];
		String old_seq_no = (String) data[67];
		BigDecimal additional_amt = (BigDecimal) data[93];
		BigDecimal pagibig_contri = (BigDecimal) data[94];
		
		System.out.printf("Display Final Fire Amt: %s%n", final_fire_amt);
		
		pgSelect db = new pgSelect();
		
		String sql = "SELECT sp_save_request('"+old_entity_id+"', '"+old_proj_id+"', '"+old_unit_id+"' ,"+old_seq_no+", '"+req_no+"', \n"+
				 	 "'"+selected_req+"','"+req_client+"','"+req_remarks+"', (CASE WHEN '"+selected_req+"' ~*'05' THEN '"+req_purpose+"' ELSE '' END), '"+final_entity_id+"', '"+new_last_name+"', \n"+
				     "'"+new_first_name+"','"+new_middle_name+"', nullif('"+final_civil_status+"', 'null'),'"+final_proj_id+"','"+final_unit_id+"',"+final_seq_no+" ,"+final_lot_area+" , \n"+
				     "'"+final_model_id+"', '"+final_agent_id+"','"+final_buyer_type+"', '"+final_pmt_scheme+"', "+final_gsp+", "+final_disc_amt+","+final_disc_rate+" , \n"+
				     ""+final_dp_amt+" , "+final_dp_rate+" , "+final_dp_term+" ,  NULLIF('"+new_dp_duedate+"', 'null')::TIMESTAMP , "+final_availed_amt+" , "+final_availed_rate+" , \n"+
				     ""+final_ma_term+" , NULLIF('"+new_ma_duedate+"', 'null')::TIMESTAMP , "+final_fire_amt+", "+additional_amt+", "+getFireHouseAPV_Rounded(final_model_id)+" , '"+pagibig_contri+"' ,'"+UserInfo.EmployeeCode+"')";
		db.select(sql);
		FncSystem.out("Display Saving of Request Details", sql);
		
		Boolean existing = (Boolean) db.getResult()[0][0];
		
		//System.out.printf("Display if Existing: %s%n", existing);
		return existing;
	}
	
	/***********END OF SAVING***********/
	
	/******START OF CANCEL******/

	public static void cancelRequest(String req_no){//CANCELATION OF REQUEST //OK NA DITO
		pgSelect db = new pgSelect();

		String sql = "select sp_cancel_request('"+req_no+"', '"+UserInfo.EmployeeCode+"')";
		db.select(sql);
		FncSystem.out("Display cancelation of the request", sql);

	}
	/********END OF CANCEL***********/

	/***************START OF POST*****************/

	public static Boolean postRequest(String req_no){//POSTING OF THE REQUEST 

		pgSelect db = new pgSelect();
		String sql = "select sp_post_request('"+req_no+"', '"+UserInfo.EmployeeCode+"', '"+UserInfo.Branch+"')";
		db.select(sql);
		
		FncSystem.out("Display post request", sql);
		
		return (Boolean) db.getResult()[0][0];
	}

	/***************END OF POSTING****************/

	/***************VALIDATION******************/

	public static Object[] setFinalVariables(Object [] data){//OK NA DITO
		pgSelect db = new pgSelect();
		
		String old_entity_id = (String) data[0];
		String old_entity_name = (String) data[1];
		String old_civil_status = (String) data[2];
		String old_proj_id = (String) data[3];
		String old_unit_id = (String) data[4];
		String old_seq_no = (String) data[5];
		String old_lot_area = (String) data[6];
		String old_model_id = (String) data[7];
		String old_agent = (String) data[8];
		String old_buyer_type = (String) data[9];
		String old_pmt_scheme = (String) data[10];
		BigDecimal old_gsp = (BigDecimal) data[11];
		BigDecimal old_disc_amt = (BigDecimal) data[12];
		BigDecimal old_disc_rate = (BigDecimal) data[13];
		BigDecimal old_vat_amt = (BigDecimal) data[14];
		BigDecimal old_vat_rate = (BigDecimal) data[15];
		BigDecimal old_nsp = (BigDecimal) data[16];
		BigDecimal old_dp_amt = (BigDecimal) data[17];
		BigDecimal old_dp_rate = (BigDecimal) data[18];
		String old_dp_term = (String) data[19];
		Date old_dp_due_date = (Date) data[20];
		BigDecimal old_availed_amt = (BigDecimal) data[21];
		BigDecimal old_availed_rate = (BigDecimal) data[22];
		String old_ma_term = (String) data[23];
		Date old_ma_due_date = (Date) data[24];
		Date start_date = (Date) data[25];
		Date end_date = (Date) data[26];
		BigDecimal old_int_rate = (BigDecimal) data[27];
		BigDecimal old_fire_amt = (BigDecimal) data[28];
		String old_merge_unit_id = getMergeUnitID(old_unit_id);
		String old_merge_model_id = getMergeModelID(old_unit_id);
		
		String selected_req = (String) data[29];
		String new_entity_id = (String) data[30];
		String new_first_name = (String) data[31];
		String new_middle_name = (String) data[32];
		String new_last_name = (String) data[33];
		String new_civil_status = (String) data[34];
		String new_proj_id = (String) data[35];
		String new_unit_id = (String) data[36];
		String new_lot_area = (String) data[38];
		String new_model_id = (String) data[39];
		String new_agent = (String) data[40];
		String new_buyer_type = (String) data[41];
		String new_pmt_scheme = (String) data[42];
		BigDecimal new_gsp = (BigDecimal) data[43];
		BigDecimal new_disc_amt = (BigDecimal) data[44];
		BigDecimal new_disc_rate = (BigDecimal) data[45];
		BigDecimal new_vat_amt = (BigDecimal) data[46];
		BigDecimal new_vat_rate = (BigDecimal) data[47];
		BigDecimal new_nsp = (BigDecimal) data[48];
		BigDecimal new_dp_amt = (BigDecimal) data[49];
		BigDecimal new_dp_rate = (BigDecimal) data[50];
		String new_dp_term = (String) data[51];
		Date new_dp_due_date = (Date) data[52];
		BigDecimal new_availed_amt = (BigDecimal) data[53];
		BigDecimal new_availed_rate = (BigDecimal) data[54];
		String new_ma_term = (String) data[55];
		Date new_ma_due_date = (Date) data[56];
		BigDecimal new_int_rate = (BigDecimal) data[57];
		BigDecimal new_fire_amt = (BigDecimal) data[58];
		String req_client = (String) data[59];
		String req_remarks = (String) data[60];
		String req_purpose = (String) data[61];
		BigDecimal additional_amt = (BigDecimal) data[62];
		BigDecimal pagibig_contri = (BigDecimal) data[63];
		//BigDecimal credit_amt = (BigDecimal) data[63];
		String new_merge_unit_id = getMergeUnitID(new_unit_id);
		String new_merge_model_id = getMergeModelID(new_unit_id);
		String new_scheddate = "";

		String final_entity_id = "";
		String final_civil_status = "";
		String final_unit_id = "";
		String final_proj_id = "";
		String final_seq_no = "";
		String final_lot_area = "";
		String final_model_id = "";
		String final_agent_id = "";
		String final_buyer_type = "";
		String final_pmt_scheme  = "";
		BigDecimal final_gsp = new BigDecimal("0.00");
		BigDecimal final_disc_amt = new BigDecimal("0.00");
		BigDecimal final_disc_rate = new BigDecimal("0.00");
		BigDecimal final_vat_amt = new BigDecimal("0.00");
		BigDecimal final_vat_rate = new BigDecimal("0.00");
		BigDecimal final_nsp = new BigDecimal("0.00");
		BigDecimal final_dp_amt = new BigDecimal("0.00");
		BigDecimal final_dp_rate = new BigDecimal("0.00");
		String final_dp_term = "";
		Date final_dp_due_date = null;
		BigDecimal final_availed_amt = new BigDecimal("0.00");
		BigDecimal final_availed_rate = new BigDecimal("0.00");
		String final_ma_term = "";
		Date final_ma_due_date = null;
		BigDecimal final_int_rate = new BigDecimal("0.00");
		BigDecimal final_fire_amt = new BigDecimal("0.00");
		String final_merge_unit_id = "";
		String final_merge_model_id = "";
		
		if(new_dp_due_date == null){
			final_dp_due_date = old_dp_due_date;
		}else{
			final_dp_due_date = new_dp_due_date;
		}

		if(new_entity_id == null){
			final_entity_id = old_entity_id;
		}else{
			final_entity_id = new_entity_id;
		}

		if(new_civil_status == null){
			final_civil_status = old_civil_status;
		}else{
			final_civil_status = new_civil_status;
		}

		if(new_proj_id == null){
			final_proj_id = old_proj_id;
		}else{
			final_proj_id = new_proj_id;
		}

		if(new_unit_id == null){
			final_unit_id = old_unit_id;
			final_seq_no = old_seq_no;
			final_lot_area = old_lot_area;
			final_merge_unit_id = old_merge_unit_id;
			final_merge_model_id = old_merge_model_id;
			
			//System.out.println("Old Seq No Was Saved");
		}else{
			final_unit_id = new_unit_id;
			final_lot_area = new_lot_area;
			final_merge_unit_id = new_merge_unit_id;
			final_merge_model_id = new_merge_model_id;
			
			/*String sql = "select (coalesce(max(seq_no),0)+ 1)::char\n" + 
					 	 "from rf_sold_unit \n" + 
					 	 "where pbl_id  = getinteger('"+final_unit_id+"')::VARCHAR";
			db.select(sql);
			final_seq_no = (String) db.getResult()[0][0];*/
			
			//System.out.println("New Seq No was Saved");
			
			if(isCancelled(final_entity_id, final_proj_id, final_unit_id)){
				String sql = "select seq_no \n"+
							 "from rf_sold_unit \n"+
							 "where entity_id = '"+final_entity_id+"' \n"+
							 "and pbl_id = getinteger('"+final_unit_id+"')::VARCHAR \n"+
							 "and projcode = '"+final_proj_id+"' \n";
				db.select(sql);
				final_seq_no = db.getResult()[0][0].toString();
			}else{
				String sql = "select (coalesce(max(seq_no),0)+ 1)::char\n" + 
							 "from rf_sold_unit \n" + 
							 "where pbl_id  = getinteger('"+final_unit_id+"')::VARCHAR";
				db.select(sql);
				final_seq_no = (String) db.getResult()[0][0];
			}
			
			final_fire_amt = setNewFire(new_unit_id, final_unit_id, final_buyer_type, final_model_id);
		}
		
		if(new_agent == null){
			final_agent_id = old_agent;
		}else{
			final_agent_id = new_agent;
		}
		
		if(new_buyer_type == null){
			final_buyer_type = old_buyer_type;
		}else{
			final_buyer_type = new_buyer_type;
		}
		
		if(new_model_id == null){
			final_model_id = old_model_id;
			new_fire_amt = setNewFire(new_unit_id, final_unit_id, final_buyer_type, final_model_id);
			
			if(hasMergeUnit(old_unit_id)){
				String sql = "select get_fire_of_merge()";
				//db.select(SQL);
			}else{
				if(new_buyer_type == null){
					final_fire_amt = old_fire_amt;
					//System.out.printf("Display fire amount dito sa 2: %s%n", final_fire_amt);
				}else{
					final_fire_amt = new_fire_amt;
					//System.out.printf("Display fire amount dito sa 3:%s%n", final_fire_amt);
				}
			}
		}else{
			final_model_id = new_model_id;
			new_fire_amt = setNewFire(new_unit_id, final_unit_id, final_buyer_type, final_model_id);
			
			if(hasMergeUnit(old_unit_id)){
				
			}else{
				
				final_fire_amt = new_fire_amt;
				//System.out.printf("Display fire amount dito sa 4: %s%n", final_fire_amt);
			}
		}

		if(new_pmt_scheme == null){
			final_pmt_scheme = old_pmt_scheme;
			final_int_rate = old_int_rate;
		}else{
			final_pmt_scheme = new_pmt_scheme;
			final_int_rate = new_int_rate;
		}

		if(new_gsp.doubleValue() == 0 || new_gsp == null){
			final_gsp = old_gsp;
		}else{
			final_gsp = new_gsp;
		}
		
		if(selected_req.contains("08")){
			final_disc_amt = new_disc_amt;
			final_disc_rate = new_disc_rate;
		}else{
			if(new_disc_amt.doubleValue() == 0 && new_pmt_scheme == null){
				final_disc_amt = old_disc_amt;
				final_disc_rate = old_disc_rate;
				//System.out.println("Dumaan dito sa luma na discount");
			}else{
				final_disc_amt = new_disc_amt;
				final_disc_rate = new_disc_rate;
				//System.out.println("Dumaan dito sa bagong discount");
			}
		}
		
		//COMMENTED 2016-08-18
		//final_disc_amt = new_disc_amt;
		
		if(new_vat_amt.doubleValue() == 0 || new_vat_amt == null){
			final_vat_amt = old_vat_amt;
			final_vat_rate = old_vat_rate;
		}else{
			final_vat_amt = new_vat_amt;
			final_vat_rate = new_vat_rate;
		}
		
		if(new_nsp.doubleValue() == 0 || new_nsp == null){
			final_nsp = old_nsp;
		}else{
			final_nsp = new_nsp;
		}

		if((new_dp_amt.doubleValue() == 0 || new_dp_amt == null)){
			if(selected_req.contains("26") == false && selected_req.contains("12") == false && selected_req.contains("06") == false){
				final_dp_amt = old_dp_amt;
			}else{
				final_dp_amt = new_dp_amt;
			}
			
			System.out.println("Old DP");
		}else{
			final_dp_amt = new_dp_amt;
		}

		if(new_dp_rate.doubleValue() == 0 || new_dp_rate == null){
			final_dp_rate = old_dp_rate;
		}else{
			final_dp_rate = new_dp_rate;
		}
		
		if(new_dp_term.isEmpty() || new_dp_term.equals("")){
			final_dp_term = old_dp_term;
		}else{
			final_dp_term = new_dp_term;
		}
		
		if(new_availed_amt.doubleValue() == 0  && getGroupID(final_buyer_type).equals("03") == false){
			final_availed_amt = old_availed_amt;
		}else{
			final_availed_amt = new_availed_amt;
		}
		
		if(new_availed_rate.doubleValue() == 0 && getGroupID(final_buyer_type).equals("03") == false){
			final_availed_rate = old_availed_rate;
		}else{
			final_availed_rate = new_availed_rate;
		}
		
		if(new_ma_term.isEmpty() || new_ma_term.equals("")){
			final_ma_term = old_ma_term;
		}else{
			final_ma_term = new_ma_term;
		}
		
		return new Object []{final_entity_id, final_civil_status,final_proj_id ,final_unit_id, final_seq_no , final_lot_area ,final_model_id ,final_agent_id ,
				final_buyer_type ,final_pmt_scheme ,final_gsp, final_disc_amt, final_disc_rate, final_vat_amt, final_vat_rate, final_nsp,
				final_dp_amt, final_dp_rate, final_dp_term, final_dp_due_date, final_availed_amt, final_availed_rate, final_ma_term,
				final_ma_due_date, final_int_rate, final_fire_amt, final_merge_unit_id, final_merge_model_id ,selected_req,new_entity_id, new_first_name, 
				new_middle_name, new_last_name, new_civil_status, new_proj_id, new_unit_id, new_lot_area ,new_model_id, new_agent, new_buyer_type, new_pmt_scheme,
				new_gsp, new_disc_amt, new_disc_rate, new_vat_amt, new_vat_rate, new_nsp, new_dp_amt, new_dp_rate, new_dp_term,
				new_dp_due_date, new_availed_amt, new_availed_rate, new_ma_term, new_ma_due_date, new_int_rate, new_fire_amt, new_merge_unit_id, new_merge_model_id,
				req_client, req_remarks , req_purpose,old_entity_id, old_entity_name, old_civil_status, old_proj_id, old_unit_id, old_seq_no, old_lot_area, old_model_id,
				old_agent, old_buyer_type, old_pmt_scheme, old_gsp, old_disc_amt, old_disc_rate, old_vat_amt, old_vat_rate, old_nsp, 
				old_dp_amt, old_dp_rate, old_dp_term, old_dp_due_date, old_availed_amt, old_availed_rate, old_ma_term, old_ma_due_date,
				start_date, end_date ,old_int_rate, old_fire_amt, old_merge_unit_id, old_merge_model_id, additional_amt, pagibig_contri};
	}

	public static boolean isInTable(String req_id, modelOtherRequest_RequestType model){//CHECKS IF THE REQUEST IS ALREADY EXISTING IN THE TABLE
        		
		Boolean isInTable = false;

		for(int x = 0; x<model.getRowCount(); x++){
			if(req_id.equals(model.getValueAt(x, 0))){
				isInTable = true;
			}
		}
		//System.out.printf("Display return: %s%n", isInTable);		
		return isInTable;
	}
		
	//CHECKS IF THE REQUEST PAYMENTS HAS TO BE CREDITED
	public static Boolean forCredit(modelOtherRequest_RequestType model){

		if(isInTable("02", model) || isInTable("04", model) || isInTable("06", model) || isInTable("07", model)
		|| isInTable("08", model) || isInTable("12", model) || isInTable("15", model) || isInTable("16", model)
		|| isInTable("01", model) || isInTable("26", model) || isInTable("27", model) || isInTable("28", model)
		|| isInTable("05", model)){
			return true;
		}else{
			return false;
		}
	}
	
	public static Boolean isCredited(String req_no){ //CHECKS IF THE REQUEST HAS ALREADY SET UP CREDIT OF PAYMENT
		pgSelect db = new pgSelect();
		String sql = "SELECT * FROM rf_credit_payments WHERE request_no = '"+req_no+"'";
		db.select(sql);
		//FncSystem.out("Display If Credited", sql);
		
		return db.isNotNull();
	}
	
	public static Boolean isLoanableValid(Object [] data){
		String entity_id = (String) data[0];
		String pmt_scheme_id = (String) data[9]; 
		BigDecimal loanable_amt = (BigDecimal) data[20];
		String ma_term = (String) data[22];
		
		pgSelect db = new pgSelect();
		
		String sql = "SELECT is_loanable_amt_valid('"+entity_id+"', '"+pmt_scheme_id+"', "+loanable_amt+", "+ma_term+")";
		db.select(sql);
		
		FncSystem.out("Display if Loanable", sql);
		
		return (Boolean) db.getResult()[0][0];
	}
	
	//CHECKS IF THE TOTAL PAYMENTS PAID BY THE CLIENT IS ZERO
	public static Boolean isTotalPaymentsZero(String old_entity_id, String old_proj_id, String old_unit_id, String old_seq_no){
		pgSelect db = new pgSelect();
		
		String sql = "select coalesce(SUM(a.amount), 0.00) as amount\n" + 
					 "from rf_payments a\n" + 
					 "left join mf_pay_particular b on b.pay_part_id = a.pay_part_id\n" + 
					 "where b.apply_ledger\n" + 
					 "and a.entity_id = '"+old_entity_id+"'\n" + 
					 "and a.proj_id = '"+old_proj_id+"'\n" + 
					 "and a.pbl_id = getinteger('"+old_unit_id+"')::VARCHAR \n" + 
					 "and a.seq_no = "+old_seq_no+"\n" + 
					 "and not exists (select from_pay_rec_id from rf_credit_payments where from_pay_rec_id = a.pay_rec_id)\n" +
					 //"and a.request_no is null \n"+
					 "and a.status_id = 'A'\n";
		
		db.select(sql);
		FncSystem.out("Display Total Payments", sql);
		
		if(db.isNotNull()){
			BigDecimal total_pmt = (BigDecimal) db.getResult()[0][0];
			if(total_pmt.doubleValue() != 0){
				return false;
			}else{
				return true;
			}
		}else{
			return true;
		}
	}
	
	public static Boolean isLoanFiled(String old_entity_id, String old_proj_id, String old_unit_id, String old_seq_no){
		pgSelect db = new pgSelect();
		
		String sql = "SELECT CASE WHEN EXISTS (SELECT * \n"+
					 "FROM rf_buyer_status \n"+
					 "where entity_id = '"+old_entity_id+"' \n"+
					 "and proj_id = '"+old_proj_id+"' \n"+
					 "and pbl_id = getinteger('"+old_unit_id+"')::VARCHAR \n"+
					 "and seq_no = "+old_seq_no+ "\n"+
					 "and byrstatus_id = '31' \n"+
					 "and status_id = 'A') \n"+
					 "then true else false end;";
		db.select(sql);
		FncSystem.out("Display sql for Loan Filed", sql);
		
		return (Boolean) db.getResult()[0][0];
	}
	
	//CHECKS IF THE UNIT IS ALREADY TAKEN BEFORE REAPPLY
	public static Boolean isUnitTaken(String unit_id){//OK NA DITO
		pgSelect db = new pgSelect();
		String sql = "select case when status_id != 'A' then true else false end from mf_unit_info where unit_id = '"+unit_id+"'";
		db.select(sql);
		
		FncSystem.out("Display if unit is Already Taken", sql);
		return (Boolean) db.getResult()[0][0];
	}
	
	//CHECKS THE UNIT STATUS OF THE UNIT FROM THE mf_unit_info
	public static String getUnitStatus(String unit_id){
		pgSelect db = new pgSelect();
		
		String sql = "SELECT status_id FROM mf_unit_info WHERE unit_id = '"+unit_id+"'";
		db.select(sql);
		
		return (String) db.getResult()[0][0];
	}
	
	public static Boolean hasNewPriceList(String old_unit_id){
		
		pgSelect db = new pgSelect();
		String sql = "SELECT case when a.date_created > COALESCE(c.date_edited, c.date_created) THEN TRUE ELSE FALSE END, a.version_no\n" + 
					 "FROM rf_marketing_scheme_pricelist a\n" + 
					 "LEFT JOIN mf_unit_info b on b.proj_id = a.proj_id and b.phase = a.phase and b.block = a.block and b.lot = a.lot\n" + 
					 "LEFT JOIN rf_sold_unit c on c.projcode = a.proj_id and c.pbl_id = b.pbl_id\n" + 
					 "where c.pbl_id = getinteger('"+old_unit_id+"')::VARCHAR \n" + 
					 "ORDER BY a.version_no desc limit 1;";
		db.select(sql);
		
		FncSystem.out("Display sql if Unit Has New Pricelist", sql);
		
		return (Boolean) db.getResult()[0][0];
	}
	
	//DETERMINES WHETHER THE BUYER TYPE IS PAGIBIG, INHOUSE OR CASH
	public static String getGroupID(String buyer_type){
		pgSelect db = new pgSelect();
		String sql = "SELECT TRIM(get_group_id('"+buyer_type+"'))::VARCHAR";
		db.select(sql);
		
		//FncSystem.out("Display SQL for get group ID", sql);
		return (String) db.getResult()[0][0];
	}
	
	public static Boolean canApprove(){
		pgSelect db = new pgSelect();
		
		String SQL = "SELECT CASE WHEN '"+UserInfo.EmployeeCode+"' in (SELECT emp_code FROM em_employee where emp_pos ~*'(ASD Head|CSD Head|CCD OIC|PCT Head)') THEN TRUE ELSE FALSE END";
		db.select(SQL);
		
		return (Boolean) db.getResult()[0][0];
	}
	
	//CHECKS IF CLIENT HAS SPOUSE
	public static Boolean checkIfWithSpouse(String entity_id){
		pgSelect db = new pgSelect();
		
		String SQL = "SELECT CASE WHEN EXISTS (SELECT * FROM rf_entity_connect WHERE entity_id = '"+entity_id+"' AND connect_type = 'SP' AND status_id = 'A') THEN TRUE ELSE FALSE END";
		db.select(SQL);
		
		return (Boolean) db.getResult()[0][0];
	}

	//CHECKS IF THE COMMISSION IS ALREADY RELEASED FOR THE OLD SELLING AGENT
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
		//FncSystem.out("Display is Commission Released", sql);
		return (Boolean) db.getResult()[0][0];
	}
	
	public static Boolean isFullComm(String unit_id){//CHECKS IF THE COMMISSION OF THE AGENT HAS BEEN FULLY RELEASED
		pgSelect db = new pgSelect();
		
		String sql = "select case when sum(a.comm_amt) != 0.00 and sum(a.comm_amt) > (sum(b.applied_amt) + sum(b.wtax_amt)) then false else true end\n" + 
				 	 "from cm_schedule_dl a\n" + 
				 	 "left join cm_cdf_dl b on b.pbl_id = a.pbl_id\n" + 
				 	 "where a.pbl_id = getinteger('"+unit_id+"')::VARCHAR \n"+
				 	 "and a.tran_type = 'AA' \n"+
				 	 "and b.tran_type = 'AA' \n"+
				 	 "and a.status not in ('I', 'D', 'X')";
		
		db.select(sql);
		//FncSystem.out("Display if Full Commission", sql);
		return (Boolean) db.getResult()[0][0];
	}

	//RETURNS TRUE IF THE REQUEST WILL AFFECT THE CLIENTS CURRENT PAYMENT SCHEDULE
	public static Boolean affectSched(modelOtherRequest_RequestType model){
		Boolean affect_sched = false;

		if(isInTable("03", model) || isInTable("13", model) || isInTable("25", model)){
			affect_sched = false;
		}
		
		if(isInTable("05", model) || isInTable("08", model) || isInTable("15", model) || isInTable("02", model) || isInTable("16", model) 
				|| isInTable("06", model) || isInTable("04", model) || isInTable("01", model) || isInTable("07", model) || isInTable("18", model) 
				|| isInTable("12", model) || isInTable("26", model) || isInTable("27", model) || isInTable("28", model)){
			affect_sched = true;
		}
		
		return affect_sched;
	}
	
	public static Boolean isValidChangeSched(String old_entity_id, String old_proj_id, String old_unit_id, String old_seq_no){
		pgSelect db = new pgSelect();
		
		String sql = "select case when exists (select * \n" + 
					 "			 from rf_buyer_status \n" + 
					 "			 where entity_id = '"+old_entity_id+"'\n" + 
					 "			 and proj_id = '"+old_proj_id+"'\n" + 
					 "			 and pbl_id = getinteger('"+old_unit_id+"')::VARCHAR \n" + 
					 "			 and seq_no = "+old_seq_no+" \n" + 
					 "			 and byrstatus_id = '01'\n" + 
					 "			 and status_id = 'A') then false else true end;";
		db.select(sql);
		
		//FncSystem.out("Is Valid Change Schedule", sql);
		
		return (Boolean) db.getResult()[0][0];
	}
	
	public static Boolean hasChangedClientClass(modelOtherRequest_RequestType model){
		
		if(isInTable("02", model) || isInTable("15", model)){
			return true;
		}else{
			return false;
		}
	}
	
	public static Boolean hasChangedPrincipal(modelOtherRequest_RequestType model){
		if(isInTable("05", model)){
			return true;
		}else{
			return false;
		}
	}
	
	public static Boolean isCancelled(String entity_id, String proj_id ,String unit_id){
		pgSelect db = new pgSelect();
		String sql = "select case when currentstatus = '02' then true else false end\n"+
					 "from rf_sold_unit \n"+
					 "where entity_id = '"+entity_id+"' \n"+
					 "and projcode = '"+proj_id+"' \n"+
					 "and pbl_id = getinteger('"+unit_id+"')::VARCHAR \n"+
					 "and seq_no = (select seq_no from rf_sold_unit where entity_id = '"+entity_id+"' and projcode = '"+proj_id+"' and pbl_id = getinteger('"+unit_id+"')::VARCHAR)";
		
		db.select(sql);
		
		if(db.isNotNull()){
			return (Boolean) db.getResult()[0][0];
		}else{
			return false;
		}
	}
	
	public static Boolean withExistingChangeLoanable(String entity_id, String proj_id, String unit_id, String seq_no){
		Boolean return_value = false;
		
		pgSelect db = new pgSelect();
		
		String SQL = "SELECT EXISTS (SELECT * FROM rf_buyer_status WHERE entity_id = '"+entity_id+"' AND proj_id = '"+proj_id+"' AND pbl_id = getinteger('"+unit_id+"')::VARCHAR AND seq_no = "+seq_no+" AND byrstatus_id = '119' AND status_id = 'A')";
		db.select(SQL);
		
		if(db.isNotNull()){
			for(int x=0;x<db.getRowCount(); x++){
				return_value = (Boolean) db.getResult()[0][0];
			}
		}else{
			return_value = false;
		}
		
		return return_value;
	}
	
	//
	public static Boolean hasMergeUnit(String unit_id){
		pgSelect db = new pgSelect();
		String sql = "select is_with_merge_unit('"+unit_id+"')";
		db.select(sql);
		if(db.getResult()[0][0].equals("t")){
			return true;
		}else{
			return false;
		}
	}
	
	public static Boolean withPendingRequest(String old_entity_id, String old_proj_id, String old_unit_id, String old_seq_no, String request_no){
		pgSelect db = new pgSelect();
		String SQL = "SELECT EXISTS (SELECT * FROM req_rt_request_header WHERE old_entity_id = '"+old_entity_id+"' AND old_proj_id = '"+old_proj_id+"' AND old_unit_id = '"+old_unit_id+"' AND old_seq_no = "+old_seq_no+" AND request_status = 'S' AND request_no != '"+request_no+"')";
		db.select(SQL);
		
		return (Boolean) db.getResult()[0][0];
	}
	
	public static String requestAddBy(String request_no){
		String add_by = "";
		
		pgSelect db = new pgSelect();
		
		String SQL = "SELECT add_by FROM req_rt_request_header WHERE request_no = '"+request_no+"'";
		db.select(SQL);
		
		//FncSystem.out("Display SQL for Add by Request", SQL);
		
		if(db.isNotNull()){
			add_by = (String) db.getResult()[0][0];
		}
		
		return add_by;
	}
	
	/****************END OF VALIDATION*****************/

}
