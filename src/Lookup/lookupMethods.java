package Lookup;

import java.sql.Date;

import javax.swing.JOptionPane;

import Database.pgUpdate;
import Functions.FncGlobal;
import Functions.UserInfo;

public class lookupMethods {
	
	public static String getJV(String strDate) {
		String strJV = ""; 
		String strYear;
		String strPeriod;
		String strSeries;
		
		strYear = FncGlobal.GetString("select (date_part('year', '"+strDate+"'::date)::INT - 2000::INT)::VARCHAR(2)"); 
		strPeriod = FncGlobal.GetString("select (case when date_part('month', '"+strDate+"'::date)::int > 9 then date_part('month', '"+strDate+"'::date)::VARCHAR(2) ELSE '0' || trim(date_part('month', '"+strDate+"'::date)::VARCHAR(2)) end)::VARCHAR(2)");
		strSeries = FncGlobal.GetString("select (coalesce(max(jv_no), 0)+1)::varchar \n" + 
				"from \n" + 
				"(\n" + 
				"select substring(jv_no, 5)::int as jv_no \n" + 
				"from rf_jv_header \n" + 
				"where fiscal_yr::int = date_part('year', '"+strDate+"'::date)::int \n" + 
				"and right(left(jv_no, 4), 2)::int = date_part('month', '"+strDate+"'::date)::int \n" + 
				"order by substring(jv_no, 5)::int desc \n" + 
				") a"); 
		strSeries = FncGlobal.GetString("select trim(to_char('"+strSeries+"'::int, '0000'))"); 
		strJV = strYear.concat(strPeriod).concat(strSeries); 
		return strJV;
	}
	
	public static String getDepositNo(String strCoID) {
		return "select " +
			"A.dep_no as \"Deposit No.\", " +  			//0
			"A.jv_no as \"JV No\", \r\n" + 				//1
			"A.dep_date as \"Dep. Date\", \r\n" + 		//2
			"A.cash_date as \"Coll. Date\", \r\n" + 	//3
			"coalesce((SELECT SUM(value_id*dep_qty)FROM cs_dp_csh_detail WHERE dep_no = A.dep_no and status_id = 'A' ),0) \n" +
			"	+ coalesce((SELECT SUM(tran_amt) FROM cs_dp_chk_detail WHERE dep_no = A.dep_no and status_id = 'A') ,0) \n" +
			"	as \"Amount\", \r\n" + 			//4
			"D.bank_alias as \"Bank\", \r\n" + 	//5
			"C.bank_branch_location as \"Branch\", \r\n" + //6
			"A.remarks as \"Remarks\"," +		//7
			"H.status_desc  as \"Dep Status\", \r\n" + //8 
			"G.status_desc  as \"JV Status\" \r\n" +   //9

			"FROM (select * from cs_dp_header where status_id != 'I' and dep_no in (select x.dep_no from cs_dp_chk_detail x \n" + 
			"where exists(select * from rf_check_history y where x.pay_rec_id::int = y.pay_rec_id::int and y.new_checkstat_id = '15'))) A \r\n" + 
			"left JOIN mf_bank_account B ON A.bank_acct_id = B.bank_acct_id \r\n" + 
			"left JOIN mf_bank_branch C ON B.bank_branch_id = C.bank_branch_id \r\n" + 
			"left JOIN mf_bank D ON C.bank_id = D.bank_id \r\n" + 
			"left JOIN mf_record_status E ON A.status_id = E.status_id \r\n" + 
			"left JOIN rf_jv_header F ON A.jv_no = F.jv_no and A.co_id = F.co_id \r\n" + 
			"left JOIN mf_record_status G ON f.status_id = G.status_id \r\n" + 
			"left JOIN mf_record_status H ON a.status_id = H.status_id \r\n" + 

			"WHERE A.co_id = '"+strCoID+"' or '"+strCoID+"' = ''" +
			"ORDER by A.dep_no desc";
	}
	
	public static String getPhase(String strProID) {
		return "select a.phase as \"Phase\", 'PHASE ' || trim(array_to_string(array_agg(trim(a.phase)), ' & ')) as \"Description\", \n" + 
				"b.proj_alias || a.phase as \"Alias\" \n" + 
				"from mf_sub_project a \n" + 
				"left join mf_project b on a.proj_id = b.proj_id \n" + 
				"where a.proj_id = '"+strProID+"' and a.status_id = 'A' \n" + 
				"and exists(select * from mf_unit_info x where a.sub_proj_id = x.sub_proj_id) \n" + 
				"group by a.phase, b.proj_alias \n" + 
				"order by a.phase; "; 
	}
	
	public static String getProject(String co_id) {
		return "select trim(proj_id)::VARCHAR as \"ID\", trim(proj_name) as \"Project Name\", \n" +
				"trim(proj_alias)::VARCHAR as \"Alias\" \n" +
				"from mf_project \n" +
				"where (co_id = '"+co_id+"' or '"+co_id+"' = '') and status_id = 'A' \n" +
				"order by proj_id;";
	}
	
	public static String getCPF(String strCOID){
		
		return "select a.cdf_no as \"CPF No.\", a.rplf_no  as \"RPLF No.\", \n" + 
				"dd.pv_no as \"PV No.\", f.cv_no as \"CV No.\", \n" + 
				"(case when upper(trim(b.entity_name)) is null then '' else upper(trim(b.entity_name)) end)  as \"Payee\", \n" + 
				"c.net_comm as \"Net Comm.\", (case when dd.pv_no is not null then 'PROCESSED' else e.status_desc end) as \"RPLF Status\" , \n" + 
				"ee.status_desc as \"PV Status\" , \n" + 
				"(\n" + 
				"	case \n" + 
				"		when f.status_id = 'P' and f.proc_id = 5 \n" + 
				"			then 'PAID OUT' \n" + 
				"		when f.status_id = 'P' and f.proc_id = 3 \n" + 
				"			then 'FOR RELEASE TO PAYEE'\n" + 
				"		when f.status_id = 'P' and f.proc_id = 2 \n" + 
				"			then 'POSTED' \n" + 
				"		else ff.status_desc \n" + 
				"	end\n" + 
				") as \"CV Status\" , \n" + 
				"trim(a.remarks) as \"Remarks\" from cm_cdf_hd a \n" + 
				"left join ( select cdf_no, co_id, sum(applied_amt) as net_comm from cm_cdf_dl group by cdf_no, co_id ) c on a.cdf_no = c.cdf_no and a.co_id = c.co_id \n" + 
				"left join rf_request_header d on a.rplf_no = d.rplf_no and a.co_id = d.co_id \n" + 
				"left join rf_pv_header dd on a.rplf_no = dd.pv_no and a.co_id = dd.co_id \n" + 
				"left join rf_entity b on a.agent_code = b.entity_id \n" + 
				"left join mf_record_status e on d.status_id = e.status_id \n" + 
				"left join mf_record_status ee on dd.status_id = ee.status_id \n" + 
				"left join rf_cv_header f on dd.cv_no = f.cv_no and a.co_id = f.co_id \n" + 
				"left join mf_record_status ff on f.status_id = ff.status_id \n" + 
				"where a.co_id = '"+strCOID+"' or '"+strCOID+"' = 'null'\n" + 
				"order by a.cdf_no desc";
	}
	
	public static String getMSVSBatch(String strType) {
		return "select distinct date_eval::date as date_eval, eval_by \n" + 
			"from dm_gen_findings \n" + 
			"where gen_findings ~* (case when '"+strType+"' = '' then 'MSVS for verification' else '"+strType+"' end) \n" + 
			"order by date_eval::Date desc"; 
	}
	
	public static String getBounceReason() {
		return "select reason_id, reason_desc from mf_check_bounce_reason"; 
	}
	
	public static String getBankAcct () {
		return "select b.bank_acct_id, b.bank_acct_no, \n" + 
			"b.acct_desc, d.bank_name, c.bank_branch_location \n" + 
			"from mf_bank_account b \n" + 
			"inner join mf_bank_branch c on b.bank_branch_id = c.bank_branch_id  \n" + 
			"inner join mf_bank d on c.bank_id = d.bank_id \n" +
			"order by b.bank_acct_id";
	}
	
	public static String getOfficeBranch() {
		return "SELECT trim(branch_id) as \"Branch ID\", trim(branch_name) as \"Branch Name\" \n" +
			"FROM mf_office_branch";
	}
	
	public static String getCompany() {
		return "SELECT TRIM(co_id)::VARCHAR as \"ID\", TRIM(company_name) as \"Company Name\", " +
			"TRIM(company_alias)::VARCHAR as \"Alias\", company_logo as \"Logo\" FROM mf_company order by co_id ";
	}
	
	public static String getAgentCompany(String strAgent) {
		return "select trim(co_id)::varchar as co_id, trim(company_name) as company_name, trim(company_alias)::varchar company_alias \n" + 
				"from mf_company \n" + 
				"where co_id not in (select unnest(co_id) as co_id from mf_sellingagent_info where agent_id = '"+strAgent+"') \n" + 
				"order by co_id"; 
	}
	
	public static String getDivision() {
		return "select trim(b.division_code) as \"Div Code\", " +
			"trim(b.division_name) as \"Div Name\", " +
			"trim(b.division_alias) as \"Div Alias\" " +
			"from ( select distinct on (sales_div_id) sales_div_id " +
			"from mf_sellingagent_info where sales_div_id is not null or sales_div_id != ''  ) a \n" + 
			"left join mf_division b on trim(a.sales_div_id) = trim(b.division_code) " +
			"where b.division_code is not null";
	}	

	public static String getSalesGroup(String strDiv) { 
		return "select a.dept_code as \"Dept. ID\", \n" + 
			"a.dept_alias  as \"Dept. Alias\", \n" + 
			"b.division_name as \"Division\" \n" + 
			"from mf_department a \n" + 
			"left join mf_division b on a.division_code = b.division_code \n" + 
			"where (case when '"+strDiv+"' = '' then a.division_code in ('06','07','08','09','29') else " +
			"a.division_code = '"+strDiv+"' end)";
	}

	public static String getSalesType() { 
		return "select distinct on (salestype_code) salestype_code, \n" + 
			"(case when salestype_code = '001' then 'In-House' else 'External' end) \n" + 
			"from mf_sales_position";
	}
	
	public static String getSalesPosition(String type) { 
		return "select a.position_code as \"Code\", \n" + 
			"a.position_desc as \"Description\", \n" + 
			"a.position_abbv as \"Alias\" \n" + 		
			"from mf_sales_position a \n" +
			"where (case when '"+type+"' = '' then a.position_code is not null " +
			"else a.salestype_code = '"+type+"' end)";
	}

	public static String getStatus() {
		return "select status_id as \"Status ID\"," +
			"status_desc as \"Status Desc\" " +
			"from mf_record_status " +
			"where status_id in ('A','I','D')";
	}

	public static String getParticular() {
		return "select pay_part_id, particulars, partdesc \n" + 
			"from mf_pay_particular \n" + 
			"where status_id = 'A' \n" + 
			"order by particulars"; 
	}

	public static String getReceipt() {
		return "SELECT trim(doc_id) AS \"ID\", \n" + 
			"trim(doc_desc) AS \"Description\", \n" + 
			"trim(doc_alias) AS \"Alias\" \n" + 
			"from mf_doc_details \n" +
			"where doc_id in ('01', '03', '08') \n" +
			"order by doc_id"; 
	}
	
	public static String getEntityType() {
		return "select entity_type_id, entity_type_desc from mf_entity_type"; 
	}

	public static String getChartofAccount() {
		return "select acct_id as \"Acct ID\", trim(acct_name) as \"Acct Name\", bs_is as \"Balance\" \n" + 
			"from mf_boi_chart_of_accounts \n" + 
			"where status_id = 'A' and w_subacct is null or acct_id in ('01-03-07-000', '07-01-00-000') \n" +
			"order by acct_id ";
	}
	
	public static String getParticulars(String strCoID, String strBranch, String strDate) {
		return FncGlobal.GetString("select remarks from rf_crb_special_remarks where coalesce(co_id, '') = '"+strCoID+"' and coalesce(branch_id, '') = '"+strBranch+"' and trans_date::date = '"+strDate+"'::date");
	}
	
	public static void saveParticular(String strCoID, String strProjID, String strBranch, String strPhase, String strDate, String strRemarks) {
		pgUpdate dbExec = new pgUpdate(); 

		if (FncGlobal.GetBoolean("select exists(select * \n" + 
				"from rf_crb_special_remarks \n" + 
				"where coalesce(co_id, '') = '"+strCoID+"' and coalesce(branch_id, '') = '"+strBranch+"' \n" + 
				"and trans_date::date = '"+strDate+"'::date); ")) {
			dbExec.executeUpdate("update rf_crb_special_remarks \n" + 
					"set remarks = '"+strRemarks+"' \n" +
					"where co_id = '"+strCoID+"' and branch_id = '"+strBranch+"' and trans_date = '"+strDate+"'::date; ", false);
		} else {
			dbExec.executeUpdate("insert into rf_crb_special_remarks (co_id, proj_id, branch_id, phase, trans_date, remarks, status_id, date_created, created_by) \n" + 
					"values ('"+strCoID+"', '"+strProjID+"', '"+strBranch+"', '"+strPhase+"', '"+strDate+"', '"+strRemarks+"', 'A', now(), '"+UserInfo.EmployeeCode+"'); ", false);											
		}
		
		dbExec.commit();
		
		JOptionPane.showMessageDialog(null, "Saved!");
	}
	
	public static String getNTPType() {
		return "select distinct type_id as \"ID\", trim(type_desc) as \"Description\", trim(type_alias) as \"Alias\" from mf_ntp_type order by type_id";
	}
	
	public static String getBank() {
		return "SELECT trim(bank_id) as \"ID\", trim(bank_name) as \"Name\", trim(bank_alias) as \"Alias\" FROM mf_bank WHERE status_id = 'A' ORDER BY bank_name;";
	}

	public static String getBankBranch(String bank_id) {
		return "select trim(bank_branch_id) as \"ID\", trim(bank_branch_location) as \"Name\" \n" +
				"from mf_bank_branch \n" +
				"where (bank_id = '"+bank_id+"' or '"+bank_id+"' = '') and status_id = 'A' \n" +
				"order by bank_branch_location;";
	}
}