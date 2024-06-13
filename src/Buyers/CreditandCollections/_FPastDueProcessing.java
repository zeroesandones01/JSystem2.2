package Buyers.CreditandCollections;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JList;
import javax.swing.JOptionPane;

import Database.pgSelect;
import Database.pgUpdate;
import tablemodel._model_PastDue;
import tablemodel.model_PastDue;
import Functions.FncGlobal;
import Functions.FncSystem;
import Functions.UserInfo;

public class _FPastDueProcessing {

	public _FPastDueProcessing() {
	}
	public  String lookupProjects(){

		return "----Query Project \n" +
				"select proj_id as \" Project ID\", trim(proj_name) as \" Project Name\" from mf_project where status_id = 'A' order by proj_id";
	}

	public void displayPastDue_New(_model_PastDue model, JList rowHeader,  String proj_id,String monthdue,Integer tr_stage,String p_buyer_status,String buyer_type, Date due_date) {
		model.clear();

		String SQL = "";
		
		//SQL = "SELECT false,* FROM sp_generate_past_due_final('"+table_name+"', '"+ proj_id +"' ,'"+monthdue+"','"+p_buyer_status+"','"+buyer_type+"',"+tr_stage+",'"+due_date+"')";
		SQL = "SELECT false,* FROM sp_generate_past_due_final('"+ proj_id +"' ,'"+buyer_type+"','"+monthdue+"',"+tr_stage+",'"+p_buyer_status+"','"+due_date+"') order by c_phase,c_buyer_type,c_client_name";

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

			JOptionPane.showMessageDialog(FncGlobal.homeMDI, "No Records to Show!", "Empty Resultset", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	@SuppressWarnings("rawtypes")
	public static void displayPastDue(model_PastDue model, JList rowHeader, String table_name, String proj_id,String monthdue,Integer tr_stage,String p_buyer_status,String buyer_type) {
		model.clear();

		String SQL = "";
		/*	if(buyertype_id.equals("04")){
			 SQL = "select \n" + 
					"' ',\n" + // 0
					"trim(trim(proj_alias) ||' - '||trim(b.description)),\n" + // 1 
					"_get_client_name(a.entity_id),\n" + // 2
					"_get_sales_div(a.entity_id,a.pbl_id,a.seq_no),\n" + // 3 
					"_get_client_contactno(a.entity_id),\n" + // 4
					"_get_house_model_alias(a.pbl_id),\n" + // 5
					"_get_stb_date(a.entity_id,a.proj_id,a.pbl_id,a.seq_no),\n" +// 6 
					"_get_bb_date(a.entity_id,a.proj_id,a.pbl_id,a.seq_no),\n" + // 7
					"_get_moved_in_date(a.entity_id,a.proj_id,a.pbl_id,a.seq_no), \n" + // 8  
					"_get_particular_alias(a.part_id),\n" +  // 9
					"a.default_date,\n" + // 10
					"a.last_paid_date,\n" + // 11
					"months_past_due,\n" + // 12
					"days_past_due,\n" + // 13
					"_payments_made(a.entity_id,a.proj_id,a.pbl_id,a.seq_no), \n" + // 14 
					"coalesce(a.amount_due,0.00) as amount_due, \n" + // 15
					"null as last_follow_up \n" +  // 16
					"from \n" + 
					"(select * from temp_pagibig_due  \n" + 
					"where proj_id = '"+ proj_id +"'  \n" +  
					 "and "+monthdue+"   \n" + 
					"and date_updated = (select max(date_updated) from temp_pagibig_due)) a \n" + 
					"left join mf_unit_info b on a.pbl_id = b.pbl_id \n" + 
					"left join mf_project c on c.proj_id = a.proj_id;   ";
		}else{
			SQL = "select \n" + 
					"' ',\n" + // 0
					"trim(trim(proj_alias) ||' - '||trim(b.description)),\n" + // 1 
					"_get_client_name(a.entity_id),\n" + // 2
					"_get_sales_div(a.entity_id,a.pbl_id,a.seq_no),\n" + // 3 
					"_get_client_contactno(a.entity_id),\n" + // 4
					"_get_house_model_alias(a.pbl_id),\n" + // 5
					"_get_stb_date(a.entity_id,a.proj_id,a.pbl_id,a.seq_no),\n" +// 6 
					"_get_bb_date(a.entity_id,a.proj_id,a.pbl_id,a.seq_no),\n" + // 7
					"_get_moved_in_date(a.entity_id,a.proj_id,a.pbl_id,a.seq_no), \n" + // 8  
					"_get_particular_alias(a.part_id),\n" +  // 9
					"a.default_date,\n" + // 10
					"a.last_paid_date,\n" + // 11
					"months_past_due,\n" + // 12
					"days_past_due,\n" + // 13
					"_payments_made(a.entity_id,a.proj_id,a.pbl_id,a.seq_no), \n" + // 14 
					"coalesce(a.amount_due,0.00) as amount_due, \n" + // 15
					"null as last_follow_up \n" +  // 16
					"from \n" + 
					"(select * from temp_ihf_due  \n" + 
					"where proj_id = '"+ proj_id +"'  \n" + 
					 "and "+monthdue+"   \n" + 
					"and date_updated = (select max(date_updated) from temp_ihf_due)) a \n" + 
					"left join mf_unit_info b on a.pbl_id = b.pbl_id \n" + 
					"left join mf_project c on c.proj_id = a.proj_id;   ";
		}*/

		/*SQL = "select \n" + 
				"false,\n" + // 0
				"trim(trim(proj_alias) ||' - '||trim(b.description)),\n" + // 1 
				"_get_client_name(a.entity_id),\n" + // 2
				"_get_sales_div(a.entity_id,a.pbl_id,a.seq_no),\n" + // 3 
				"_get_client_contactno(a.entity_id),\n" + // 4
				"_get_house_model_alias(a.pbl_id),\n" + // 5
				"_get_stb_date(a.entity_id,a.proj_id,a.pbl_id,a.seq_no),\n" +// 6 
				"_get_bb_date(a.entity_id,a.proj_id,a.pbl_id,a.seq_no),\n" + // 7
				"_get_moved_in_date(a.entity_id,a.proj_id,a.pbl_id,a.seq_no), \n" + // 8  
				"_get_particular_alias(a.part_id),\n" +  // 9
				"a.default_date,\n" + // 10
				"a.last_paid_date,\n" + // 11
				"months_past_due,\n" + // 12
				"days_past_due,\n" + // 13
				"_payments_made(a.entity_id,a.proj_id,a.pbl_id,a.seq_no), \n" + // 14 
				"coalesce(a.amount_due,0.00) as amount_due, \n" + // 15
				"null as last_follow_up, \n" +  // 16
				"a.entity_id, \n" +  // 17
				"a.proj_id, \n" +  // 18
				"a.pbl_id, \n" +  // 19
				"a.seq_no, \n" +  // 20
				"a.unit_id, \n" +  // 21
				"a.part_id, \n" +  // 22 
				"a.last_due_date \n" +  // 23
				"from \n" + 
				"(select * from  "+table_name+" \n" + 
				"where proj_id = '"+ proj_id +"'  \n" + 
				"and "+monthdue+"   \n" + 
				"and date_updated = (select max(date_updated) from "+table_name+")) a \n" + 
				"left join mf_unit_info b on a.pbl_id = b.pbl_id \n" + 
				"left join mf_project c on c.proj_id = a.proj_id;   ";*/
		
		SQL = "SELECT false,* FROM sp_generate_past_due('"+table_name+"', '"+ proj_id +"' ,'"+monthdue+"','"+p_buyer_status+"','"+buyer_type+"',"+tr_stage+")";


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

			JOptionPane.showMessageDialog(FncGlobal.homeMDI, "No Records to Show!", "Empty Resultset", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	@SuppressWarnings("rawtypes")
	public void displayBuyBank_new(_model_PastDue model, JList rowHeader,  String proj_id,Date due_date) {
		model.clear();

		String SQL  = "SELECT false, *  FROM sp_generate_buyback_final('"+proj_id+"','"+due_date+"')";
		
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

			JOptionPane.showMessageDialog(FncGlobal.homeMDI, "No Records to Show!", "Empty Resultset", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	
	@SuppressWarnings("rawtypes")
	public static void displayBuyBank(model_PastDue model, JList rowHeader,  String proj_id) {
		model.clear();

		/*String SQL = "select \n" + 
				"false,\n" + // 0
				"trim(trim(proj_alias) ||' - '||trim(b.description)),\n" +  // 1
				"_get_client_name(a.entity_id),\n" +  //2
				"_get_sales_div(a.entity_id,a.pbl_id,a.seq_no),\n" + //3  
				"_get_client_contactno(a.entity_id),\n" + //4 
				"_get_house_model_alias(a.pbl_id),\n" + //5 
				"_get_stb_date(a.entity_id,a.proj_id,a.pbl_id,a.seq_no),\n" + //6 
				"_get_bb_date(a.entity_id,a.proj_id,a.pbl_id,a.seq_no),\n" +  //7
				"_get_moved_in_date(a.entity_id,a.proj_id,a.pbl_id,a.seq_no), \n" +//8 
				"_get_particular_alias(a.part_id),\n" + //9
				"a.default_date,\n" +//10 
				"a.last_paid_date,\n" + //11 
				"months_past_due,\n" + //12
				"days_past_due,\n" + //13
				"_payments_made(a.entity_id,a.proj_id,a.pbl_id,a.seq_no), \n" +//14 
				"coalesce(a.amount_due,0.00) as amount_due, \n" + //15
				"null as last_follow_up, \n" +//16
				"a.entity_id, \n" +  // 17
				"a.proj_id, \n" +  // 18
				"a.pbl_id, \n" +  // 19
				"a.seq_no, \n" +  // 20
				"a.unit_id, \n" +  // 21
				"a.part_id, \n" +  // 22 
				"a.last_due_date \n" +  // 23
				"from \n" + 
				"(select * from pagibig_due_accounts  \n" + 
				"where proj_id ='"+ proj_id +"'  \n" + 
				"and months_past_due >=  2  \n" + 
				"and date_updated = (select max(date_updated) from pagibig_due_accounts)) a \n" + 
				"left join mf_unit_info b on a.pbl_id = b.pbl_id \n" + 
				"left join mf_project c on c.proj_id = a.proj_id\n" + 
				" join get_soldtobank_accounts('"+ proj_id +"') d on d.entity_id = a.entity_id and d.pbl_id = a.pbl_id\n" + 
				"\n" + 
				"\n" + 
				"UNION \n" + 
				"\n" + 
				"\n" + 
				"select \n" + 
				"false,\n" + // 0
				"trim(trim(proj_alias) ||' - '||trim(b.description)),\n" + 
				"_get_client_name(a.entity_id),\n" + 
				"_get_sales_div(a.entity_id,a.pbl_id,a.seq_no),\n" + 
				"_get_client_contactno(a.entity_id),\n" + 
				"_get_house_model_alias(a.pbl_id),\n" + 
				"_get_stb_date(a.entity_id,a.proj_id,a.pbl_id,a.seq_no),\n" + 
				"_get_bb_date(a.entity_id,a.proj_id,a.pbl_id,a.seq_no),\n" + 
				"_get_moved_in_date(a.entity_id,a.proj_id,a.pbl_id,a.seq_no), \n" + 
				"_get_particular_alias(a.part_id),\n" + 
				"a.default_date,\n" + 
				"a.last_paid_date,\n" + 
				"months_past_due,\n" + 
				"days_past_due,\n" + 
				"_payments_made(a.entity_id,a.proj_id,a.pbl_id,a.seq_no), \n" + 
				"coalesce(a.amount_due,0.00) as amount_due, \n" + 
				"null as last_follow_up, \n" +
				"a.entity_id, \n" +  // 17
				"a.proj_id, \n" +  // 18
				"a.pbl_id, \n" +  // 19
				"a.seq_no, \n" +  // 20
				"a.unit_id, \n" +  // 21
				"a.part_id, \n" +  // 22 
				"a.last_due_date \n" +  // 23
				"from \n" + 
				"(select * from ihf_due_accounts  \n" + 
				"where proj_id = '"+ proj_id +"'  \n" + 
				"and months_past_due >=  2   \n" + 
				"and date_updated = (select max(date_updated) from ihf_due_accounts )) a \n" + 
				"left join mf_unit_info b on a.pbl_id = b.pbl_id \n" + 
				"left join mf_project c on c.proj_id = a.proj_id\n" + 
				"join get_soldtobank_accounts('"+ proj_id +"') d on d.entity_id = a.entity_id and d.pbl_id = a.pbl_id;";
*/

		String SQL  = "SELECT false, *  FROM sp_generate_buyback('"+proj_id+"')";
		
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

			JOptionPane.showMessageDialog(FncGlobal.homeMDI, "No Records to Show!", "Empty Resultset", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	public  String getCompany() {//XXX Company
		String SQL = "SELECT co_id as \"ID\", trim(company_name) as \"Company Name\", trim(company_alias) as \"Alias\", company_logo as \"Logo\" FROM mf_company";
		return SQL;
	}
	
	@SuppressWarnings("rawtypes")
	public static void displayCancellation(model_PastDue model, JList rowHeader,  String proj_id,String table_name ,String days_pd,String sales_div,String buyer_status,String buyer_type) {
		model.clear();

		/*String SQL = "select \n" + 
				"false,\n" + // 0
				"trim(trim(proj_alias) ||' - '||trim(b.description)),\n" + // 1 
				"_get_client_name(a.entity_id),\n" + // 2
				"_get_sales_div(a.entity_id,a.pbl_id,a.seq_no),\n" + // 3 
				"_get_client_contactno(a.entity_id),\n" + // 4
				"_get_house_model_alias(a.pbl_id),\n" + // 5
				"_get_stb_date(a.entity_id,a.proj_id,a.pbl_id,a.seq_no),\n" +// 6 
				"_get_bb_date(a.entity_id,a.proj_id,a.pbl_id,a.seq_no),\n" + // 7
				"_get_moved_in_date(a.entity_id,a.proj_id,a.pbl_id,a.seq_no), \n" + // 8  
				"_get_particular_alias(a.part_id),\n" +  // 9
				"a.default_date,\n" + // 10
				"a.last_paid_date,\n" + // 11
				"months_past_due,\n" + // 12
				"days_past_due,\n" + // 13
				"_payments_made(a.entity_id,a.proj_id,a.pbl_id,a.seq_no), \n" + // 14 
				"coalesce(a.amount_due,0.00) as amount_due, \n" + // 15
				"null as last_follow_up, \n" +  // 16
				"a.entity_id, \n" +  // 17
				"a.proj_id, \n" +  // 18
				"a.pbl_id, \n" +  // 19
				"a.seq_no, \n" +  // 20
				"a.unit_id, \n" +  // 21
				"a.part_id, \n" +  // 22 
				"a.last_due_date \n" +  // 22
				"from \n" + 
				"(select * from "+table_name+"   \n" + 
				"where proj_id = '"+ proj_id +"'  \n" + 
				"and "+days_pd+"   \n" + 
				"and date_updated = (select max(date_updated) from "+table_name+")) a \n" + 
				"left join mf_unit_info b on a.pbl_id = b.pbl_id \n" + 
				"left join mf_project c on c.proj_id = a.proj_id \n" + 
				"where a.unit_id not in ( select distinct on(unit_id) unit_id \n" + 
				"from rf_cancellation  \n" + 
				"where batch_no is null  \n" + 
				"and proj_id = '"+ proj_id +"' \n" + 
				"order by unit_id,seq_no desc, cancel_rec_id desc);   ";*/
		
		FncSystem.out("***************************************DUMAAN DITO","");
		String SQL = "SELECT false, * FROM _sp_generate_proj_cancellation_final('"+table_name+"','"+proj_id+"','"+days_pd+"','"+sales_div+"', '"+buyer_status+"','"+buyer_type+"')";

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

			JOptionPane.showMessageDialog(FncGlobal.homeMDI, "No Records to Show!", "Empty Resultset", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	@SuppressWarnings("rawtypes")
	public static void displayCancellation_new(_model_PastDue model, JList rowHeader,  String proj_id,String table_name ,String days_pd,String sales_div,String buyer_status,String buyer_type, Date due_date) {
		model.clear();

		FncSystem.out("***************************************DUMAAN DITO","");
		String SQL = "SELECT false, * FROM _sp_generate_proj_cancellation_final_new('"+table_name+"','"+proj_id+"','"+days_pd+"','"+sales_div+"', '"+buyer_status+"','"+buyer_type+"','"+due_date+"')";

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

			JOptionPane.showMessageDialog(FncGlobal.homeMDI, "No Records to Show!", "Empty Resultset", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	@SuppressWarnings("rawtypes")
	public  void _displayCancellation_new(_model_PastDue model, JList rowHeader,  String proj_id,Integer sales_div, Integer account_status, Integer buyer_type, Date due_date,String p_user_id,Integer cancel_type) {
		model.clear();

		FncSystem.out("***************************************DUMAAN DITO","");
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
		
		String  v_buyertype = "";
		
		
		if (buyer_type == 1) {
			v_buyertype = "IHF";
		}
		
		if (buyer_type == 2) {
			v_buyertype = "PAGIBIG";
		}
		
		if (buyer_type == 3) {
			v_buyertype = "CASH";
		}
		
		if (buyer_type == 4) {
			v_buyertype = "BANK FINANCE";
		}
		//String SQL = "SELECT false, * FROM _sp_generate_proj_cancellation_final_new('"+table_name+"','"+proj_id+"','"+days_pd+"','"+sales_div+"', '"+buyer_status+"','"+buyer_type+"','"+due_date+"')";
		String SQL ="SELECT false,* FROM sp_generate_proj_cancellation_final('"+proj_id+"','"+due_date+"' ,'"+p_user_id+"', "+cancel_type+") \n" +
					"WHERE c_proj_id ='"+proj_id+"' \n" +
					(buyer_type == 0  ? "AND c_buyer_type IN ('PAGIBIG', 'IHF','CASH','BANK FINANCE')" : "AND c_buyer_type IN ('"+v_buyertype+"')" ) +
					(sales_div == 0  ? "" : "AND c_sale_div ~* '"+v_sales_div+"'") +
					"\n" ;
		
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

			JOptionPane.showMessageDialog(FncGlobal.homeMDI, "No Records to Show!", "Empty Resultset", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	public static String getBatchNo(){
		String SQL = "select trim(to_char(coalesce(max(batch_no)::int,0) + 1,'FM0000000000')) from rf_client_notices ";
		String batchno = "";
		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			batchno = db.Result[0][0].toString();
		}

		return batchno;
	}

	public static void forPosting(String entityID, String projID,String pblID,Integer seqID, String unitID,String partID,BigDecimal amountDue ,String partAlias,String bathcno, Timestamp defaultDate,Timestamp lastpayDate,Timestamp lastschedDate ,pgUpdate dbU ){
		String SQLDetail= ""; 
	
		
		pgSelect dbS = new pgSelect();

		dbS.select("select  *  from rf_client_notices where entity_id ='"+entityID+"' and status_id = 'A' ");

		if(dbS.isNotNull()){ // if have data in rf_client_notices
			
			String SQLUpdate = "UPDATE rf_client_notices set ( \n" +   
					"batch_no, \n" + 
					"notice_id, \n" + 
					"part_id, \n" +  
					"stage_id, \n" + 
					"dateprep, \n" + 
					"preparedby, \n" + 
					"billingduedate, \n" + 
					"duedate, \n" + 
					"amtdue, \n" + 
					"default_date, \n" + 
					"last_paydate, \n" + 
					"dues_lastsked, \n" + 
					"amt_to_update, \n" + 
					"status_id) = ('"+bathcno+ "', \n" + 
					" '83', \n" + 
					"'"+partID+ "', \n" + 
					"(select case when '"+partAlias+"'='MA' then 14 else 13 end), \n" + 
					"current_date, \n" + 
					"'"+UserInfo.EmployeeCode.trim()+ "', \n" + 
					"(select _get_next_scheddate('"+entityID+"', '"+projID+"', '"+pblID+"', '"+seqID+"',current_date))::TIMESTAMP, \n" + 
					"(select _get_next_scheddate('"+entityID+"', '"+projID+"', '"+pblID+"', '"+seqID+"',current_date))::TIMESTAMP, \n" + 
					"'"+amountDue+"', \n" + 
					"'"+defaultDate+"', \n" + 
					"'" +lastpayDate+ "' , \n" + 
					"'" +lastschedDate+"', \n" + 
					"'"+amountDue+"', \n" + 
					"'A'  ) where entity_id = '"+entityID+"' and status_id = 'A'  ";
			
			dbU.executeUpdate(SQLUpdate, true);

		}else{ // if no data in rf_client_notices	
			/*
			SQLDetail = "INSERT INTO rf_client_notices(\n" + 
					"            entity_id, projcode, pbl_id, seq_no, part_id, notice_id, \n" + 
					"            stage_id, dateprep, preparedby, datesent, addr_type_id, noofaddr, \n" + 
					"            receivedby, relationtobuyer, datecomplied, noofempl, remark_id, \n" + 
					"            rtsreason, mailedthru, docsnok, remarks, batch_no, billingno, \n" + 
					"            billingduedate, duemon, duedate, amtdue, effdate, stage_part, \n" + 
					"            grace_period, default_date, orientation_batchno, orientation_date, \n" + 
					"            orientation_time, venue_id, rts_date, bank_id, netsellprice, \n" + 
					"            dpamount, dpstart, dpend, maamount, mastart, maend, last_paydate, \n" + 
					"            to_fullsettle_part, to_fullsettle_amt, cancel_date, dues_lastsked, \n" + 
					"            gen_findings_rec_id, amt_to_update, doc_non_comp, no_of_mos_pastdue, \n" + 
					"            pm_insp_date, pm_insp_from_time, pm_insp_to_time, msvs_counsel_date, \n" + 
					"            msvs_counsel_date_new, is_under_maceda_law, leger_ob, pri_int, \n" + 
					"            status_id, received_date, has_moved_in)\n" + 
					"    VALUES ( '"+entityID+"', '"+projID+"', '"+pblID+"', '"+seqID+"', '"+partID+"', '83', \n" + 
					"            (select case when '"+partAlias+"'='MA' then 14 else 13 end), current_date, '"+UserInfo.EmployeeCode+ " ', null, null, null, \n" + 
					"            null, null,null, null, null, \n" + 
					"            null, null, null, null, '"+bathcno+"',null, \n" + 
					"            (select _get_next_scheddate('"+entityID+"', '"+projID+"', '"+pblID+"', '"+seqID+"',current_date))::TIMESTAMP, null, (select _get_next_scheddate('"+entityID+"', '"+projID+"', '"+pblID+"', '"+seqID+"',current_date))::TIMESTAMP, '"+amountDue+"', null, null, \n" + 
					"            null,'"+defaultDate+"', null, null, \n" + 
					"            null, null, null, null, null, \n" + 
					"            null, null, null, null,null, null, '" +lastpayDate+ "' , \n" + 
					"            null, null, null, '" +lastschedDate+"' , \n" + 
					"            null, '"+amountDue+"', null, null, \n" + 
					"            null, null, null, null, \n" + 
					"            null, null, null, null, \n" +
					"            'A', null, null);\n" + 
					"";*/
			
			SQLDetail = "INSERT INTO rf_client_notices(\n" + 
					"				            --rec_id, \n" + 
					"				            entity_id, \n" + 
					"				            projcode, \n" + 
					"				            pbl_id, \n" + 
					"				            seq_no, \n" + 
					"				            part_id, \n" + 
					"				            notice_id, --'83' \n" + 
					"				            stage_id, \n" + 
					"				            dateprep, \n" + 
					"				            preparedby, \n" + 
					"				            batch_no, \n" + 
					"				            billingduedate, \n" + 
					"				            duedate, \n" + 
					"				            amtdue, \n" + 
					"				            default_date, \n" + 
					"				            last_paydate, \n" + 
					"				            dues_lastsked, \n" + 
					"				            amt_to_update, \n" + 
					"				            status_id \n" + 
					"		            )\n" + 
					"		   VALUES ( --rec_id, \n" + 
					"		           '"+entityID+"', -- entity_id, \n" + 
					"		           '"+projID+"',  -- projcode, \n" + 
					"		           '"+pblID+"', -- pbl_id, \n" + 
					"		           '"+seqID+"',--seq_no, \n" + 
					"		           '"+partID+"', -- part_id, \n" + 
					"		           '83',---notice_id, '83'\n" + 
					"		           (select case when '"+partAlias+"'='MA' then 14 else 13 end),--stage_id, \n" + 
					"		           current_date, --dateprep, \n" + 
					"		           '"+UserInfo.EmployeeCode+ " ', -- preparedby, \n" + 
					"		            '"+bathcno+"',--batch_no, \n" + 
					"		           (select _get_next_scheddate('"+entityID+"', '"+projID+"', '"+pblID+"', '"+seqID+"',current_date))::TIMESTAMP,-- billingduedate, \n" + 
					"		           (select _get_next_scheddate('"+entityID+"', '"+projID+"', '"+pblID+"', '"+seqID+"',current_date))::TIMESTAMP, --duedate, \n" + 
					"		           '"+amountDue+"',--amtdue, \n" + 
					"		           '"+defaultDate+"',-- default_date, \n" + 
					"		           '" +lastpayDate+ "',-- last_paydate, \n" + 
					"		           '" +lastschedDate+"' , --dues_lastsked,\n" + 
					"		           '"+amountDue+"',--amt_to_update,\n" + 
					"		           'A'--status_id\n" + 
					"		            );";
			
			dbU.executeUpdate(SQLDetail, true);

		}
		
		/*SQLDetail = "INSERT INTO rf_cancellation(\n" +  
				"            batch_no, entity_id, proj_id, pbl_id, seq_no, \n" + 
				"            unit_id, part_id, last_paid_date, default_date, due_date, months_past_due, \n" + 
				"            days_past_due, amount_due, under_maceda_law, reason, remarks, \n" + 
				"            date_posted, posted_by, date_cancelled, cancelled_by, date_approved, \n" + 
				"            approved_by, csv_id, csv_amount, csv_rplf_no, csv_approved_by, \n" + 
				"            months_paid)\n" + 
				"    VALUES ( null,  '"+entityID+"', '"+projID+"', '"+pblID+"', '"+seqID+"', \n" + 
				"            '"+unitID+"', '"+partID+"', '"+lastpayDate+"', '"+defaultDate+"', (select _get_next_scheddate('"+entityID+"', '"+projID+"', '"+pblID+"', '"+seqID+"',current_date))::TIMESTAMP, null, \n" + 
				"            null, '"+amountDue+"', null, null, null, \n" + 
				"            now(), '"+UserInfo.EmployeeCode.trim()+"', null, null, null, \n" + 
				"            null, null, null, null, null, \n" + 
				"            null);\n" + 
				"";
*/
		SQLDetail = "INSERT INTO rf_cancellation( \n" + 
				"			entity_id, \n" + 
				"			proj_id, \n" + 
				"			pbl_id, \n" + 
				"			seq_no, \n" + 
				"			unit_id, \n" + 
				"			part_id, \n" + 
				"			last_paid_date, \n" + 
				"			default_date, \n" + 
				"			due_date, \n" + 
				"			days_past_due, \n" + 
				"			amount_due, \n" +  
				"			date_posted, \n" + 
				"			posted_by\n" + 
				"			)\n" + 
				"		VALUES (\n" +
				"			'"+entityID+"', --entity_id, \n" + 
				"			'"+projID+"', --proj_id, \n" + 
				"			'"+pblID+"', --pbl_id, \n" + 
				"			'"+seqID+"', --seq_no, \n" + 
				"			'"+unitID+"', --unit_id, \n" + 
				"			'"+partID+"', --part_id, \n" + 
				"			'"+lastpayDate+"', --last_paid_date, \n" + 
				"			'"+defaultDate+"', --default_date, \n" + 
				"			(select _get_next_scheddate('"+entityID+"', '"+projID+"', '"+pblID+"', '"+seqID+"',current_date))::TIMESTAMP, --due_date, \n" + 
				"			'"+amountDue+"', --amount_due, \n" + 
				"			now(), --date_posted, \n" + 
				"			'"+UserInfo.EmployeeCode.trim()+"' --posted_by\n" + 
				"			);";
		
		
		
		dbU.executeUpdate(SQLDetail, true); 

	} 
	
	public String dateFormat_day(Date date){
		String strdate = null;
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		if (!(date == null)) { 
			strdate = df.format(date);
		}

		return strdate;
	}
}
