/**
 * 
 */
package Buyers.LoansManagement;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.DefaultListModel;
import javax.swing.JList;

import Database.pgSelect;
import Database.pgUpdate;
import tablemodel.modelFI_ForPmtInsurance;
import tablemodel.modelQualifiedFIEnroll;
import tablemodel.modelQualifiedFIRenew;
import tablemodel.modelQualifiedFITerminate;
import tablemodel.modelApproveQualified;
import tablemodel.modelFire_ForIssuancePolicy;
import Functions.FncTables;
import Functions.FncSystem;
import Functions.UserInfo;

/**
 * @author John Lester Fatallo
 */
public class _FireInsurance {

	public _FireInsurance() {
		// TODO Auto-generated constructor stub
	}
	
	//SQL FOR PROJECT
	public static String sqlProject(String co_id){
		return "SELECT \n" + 
			   "proj_id as \"ID\", proj_name as \"Proj. Name\"\n" + 
			   "FROM mf_project\n" + 
			   "WHERE status_id = 'A' \n"+
			   "AND co_id = '"+co_id+"'\n" + 
			   "ORDER BY proj_id ";
	}
	
	//SQL FOR COMPANY
	public static String sqlCompany(){
		return "SELECT \n" + 
				"co_id AS \"ID\", TRIM(company_name) AS \"Name\", TRIM(company_alias) AS \"Alias\"\n" + 
				"FROM mf_company ";
	}
	
	//SQL INSURANCE COMPANY
	public static String sqlInsuranceCompany(){
		return "SELECT \n" + 
			   "entity_id AS \"ID\", get_client_name(entity_id) AS \"Insurance Company\"\n" + 
			   "FROM mf_insurance_company \n" + 
			   "WHERE status_id = 'A'\n" + 
			   "ORDER BY get_client_name(entity_id);";
	}
	
	//SQL FOR LOOKUP OF ENROLLMENT BATCH NO
	public static String sqlBatchNoEnrollment(){
		return "SELECT DISTINCT enrollment_batch as \"Batch No\"\n" + 
			   "FROM rf_fire_header a\n" + 
			   "WHERE a.status_id = 'I'\n" + 
		       "AND NOT EXISTS (SELECT *\n" + 
		       "		FROM get_cancelled_accounts()\n" + 
		       "		WHERE entity_id = a.entity_id \n" + 
		       "		AND proj_id = a.proj_id\n" + 
		       "		AND pbl_id = a.pbl_id\n" + 
		       "		AND seq_no = a.seq_no)\n" + 
		       "AND NOT EXISTS (SELECT *\n" + 
		       "		FROM get_fullsettled_accounts()\n" + 
		       "		WHERE entity_id = a.entity_id\n" + 
		       "		AND proj_id = a.proj_id\n" + 
		       "		AND pbl_id = a.pbl_id\n" + 
		       "		AND seq_no = a.seq_no)\n" + 
		       "AND _get_client_buyertype(a.entity_id, a.proj_id, a.pbl_id, a.seq_no) = '02'\n" + 
		       "AND a.enrollment_batch IS NOT NULL \n"+
		       "AND rplf_no IS NULL\n"+
		       "ORDER BY a.enrollment_batch DESC";
	}
	
	//SQL FOR LOOKUP OF RENEWAL BATCH NO
	public static String sqlBatchNoRenewal(){
		
		return "SELECT DISTINCT renewal_batch AS \"Batch No\"\n" + 
			   "FROM rf_fire_header \n" + 
			   "WHERE status_id = 'A'\n" + 
			   "AND renewal_batch IS NOT NULL \n"+
			   "AND rplf_no IS NULL \n"+
			   "ORDER BY renewal_batch DESC\n";
				//"AND date_approved IS NULL;";
	}
	
	//SQL FOR LOOKUP OF TERMINATION BATCH NO
	public static String sqlBatchNoTermination(){
		
		return "SELECT DISTINCT a.termination_batch AS \"Batch No\"\n" + 
			   "FROM rf_fire_header a\n" + 
			   "WHERE a.status_id = 'A'\n" + 
			   "AND a.termination_batch IS NOT NULL\n" + 
			   "AND EXISTS (SELECT *\n" + 
			   "	    FROM get_fullsettled_accounts()\n" + 
			   "	    WHERE entity_id = a.entity_id\n" + 
			   "	    AND proj_id = a.proj_id \n" + 
			   "	    AND pbl_id = a.pbl_id\n" + 
			   "	    AND seq_no = a.seq_no)\n" + 
			   "ORDER BY a.termination_batch DESC;";
	};
	
	//SQL FOR LOOKUP OF RPLF NO
	public static String sqlRPLFNo(){
		return "SELECT DISTINCT rplf_no AS \"RPLF No\" \n" + 
			   "FROM rf_fire_header \n" + 
			   "WHERE termination_batch IS NULL\n" + 
			   "AND rplf_no IS NOT NULL\n" + 
			   "AND status_id = 'A'\n" + 
			   "ORDER BY rplf_no DESC";
	}
	
	//SQL FOR LOOKUP OF INVOICE NO
	public static String sqlInvoiceNo(){
		return "SELECT DISTINCT invoice_no AS \"Invoice No\" \n"+
			   "FROM rf_fire_header \n"+
			   "WHERE termination_batch IS NULL \n"+
			   "AND invoice_no IS NOT NULL \n"+
			   "AND status_id = 'A' \n"+
			   "ORDER BY invoice_no DESC";
	}
	
	//generates a new enrollment batch no
	public static String newQualifiedEnrollmentBatchNo(){
		String batch_no = "";
		pgSelect db = new pgSelect();
		
		String sql = "select get_new_enrollment_batch_no()";
		db.select(sql);
		FncSystem.out("Display New Enrollment Batch No.", sql);
		if(db.isNotNull()){
			batch_no = (String) db.getResult()[0][0];
		}
		return batch_no;
	}
	
	//generates a new renewal batch no
	public static String newQualifiedRenewalBatchNo(){
		String renewal_batch_no = "";
		pgSelect db = new pgSelect();
		
		String sql = "SELECT get_new_renewal_batch_no()";
		db.select(sql);
		
		FncSystem.out("Display New Renewal Batch No.", sql);
		if(db.isNotNull()){
			renewal_batch_no = (String) db.getResult()[0][0];
		}
		return renewal_batch_no;
	}
	
	//generates a new batch termination no
	public static String newQualifiedTerminationBatchNo(){
		String termination_batch_no = "";
		pgSelect db = new pgSelect();
		
		String sql = "SELECT TRIM(to_char(COALESCE(max(termination_batch),'0')::int + 1,'000000')) FROM rf_fire_header;\n";
		db.select(sql);
		FncSystem.out("Display New Termination Batch No.", sql);
		if(db.isNotNull()){
			termination_batch_no = (String) db.getResult()[0][0];
		}
		return termination_batch_no;
	}
	
	//GENERATES A NEW RPLF NO
	public static String newRPLFNo(String co_id, String proj_id, Boolean qualified){
		String rplf_no = "";
		
		pgSelect db = new pgSelect();
//		if(qualified){
//			String sql = "SELECT TRIM(to_char(COALESCE(max(rplf_no),'0')::int + 1,'000000000')) FROM rf_request_header where co_id =  get_proj_co_id('"+proj_id+"')\n";
//			db.select(sql);
//			FncSystem.out("DIsplay New RPLF No", sql);
//		}else{
//			String sql = "SELECT TRIM(to_char(COALESCE(max(rplf_no),'0')::int + 1,'000000000')) FROM rf_request_header where co_id =  '"+co_id+"'\n";
//			db.select(sql);
//			FncSystem.out("Display New RPLF No", sql);
//		}
		
		if(qualified){
			String sql = "select * from fn_get_rplf_no('"+co_id+"') \n";
			db.select(sql);
			FncSystem.out("DIsplay New RPLF No", sql);
		}else{
			String sql = "select * from fn_get_rplf_no('"+co_id+"') \n";
			db.select(sql);
			FncSystem.out("Display New RPLF No", sql);
		}
		if(db.isNotNull()){
			rplf_no = (String) db.getResult()[0][0];
		}
		System.out.printf("Display rplf no: %s%n", rplf_no);
		return rplf_no;
	}
	
	//CHECKS FOR SELECTED ACCOUNTS TO QUALIFY FOR ENROLLMENT
	public static Integer getSelectedAccountsEnrollment(modelQualifiedFIEnroll model){
		Integer count = 0;
		
		for(int x =0; x<model.getRowCount(); x++){
			Boolean isSelected = (Boolean) model.getValueAt(x, 0);
			
			if(isSelected){
				count ++;
			}
		}
		return count;
	}
	
	//CHECKS FOR SELECTED ACCOUNTS TO QUALIFY FOR RENEWAL
	public static Integer getSelectedAccountsRenewal(modelQualifiedFIRenew model){
		Integer count = 0;
		
		for(int x= 0; x<model.getRowCount(); x++){
			Boolean isSelected = (Boolean) model.getValueAt(x, 0);
			
			if(isSelected){
				count ++;
			}
		}
		return count;
	}
	
	//CHECKS FOR SELECTED ACCOUNTS FOR TERMINATION
	public static Integer getSelectedAccountsTermination(modelQualifiedFITerminate model){
		Integer count = 0;
		
		for(int x = 0; x<model.getRowCount(); x++){
			Boolean isSelected = (Boolean) model.getValueAt(x, 0);
			
			if(isSelected){
				count ++;
			}
		}
		return count;
	}
	
	//CHECKS FOR SELECTED ACCOUNTS QUALIFIED FOR APPROVAL
	public static Integer getSelectedAccountsApproval(modelFire_ForIssuancePolicy model){
		Integer count = 0; 
		
		for(int x= 0; x<model.getRowCount(); x++){
			Boolean isSelected = (Boolean) model.getValueAt(x, 0);
			
			if(isSelected){
				count ++;
			}
		}
		return count;
	}
	
	//CHECKS IF THERE ARE SELECTED ACCOUNTS
	public static Integer getSelectedApprovedAddAccountsEnrollment(modelApproveQualified model){
		Integer count = 0;
		
		for(int x = 0; x<model.getRowCount(); x++){
			Boolean isSelected = (Boolean) model.getValueAt(x, 0);
			if(isSelected){
				count ++;
			}
		}
		return count;
	}
	
	//CHECKS IF THERE ARE ACCOUNTS TO TAG
	public static Integer getSelectedApprovedAddAccountsTransfer(modelFire_ForIssuancePolicy model){
		Integer count = 0;
		
		for(int x = 0; x<model.getRowCount(); x++){
			Boolean isSelected = (Boolean) model.getValueAt(x, 0);
			if(isSelected){
				count ++;
			}
		}
		return count;
	}
	
	public static void displayQualifiedFIEnroll(String proj_id, modelQualifiedFIEnroll model, JList rowHeader){
		if(model != null && rowHeader != null){
			FncTables.clearTable(model);
			
			DefaultListModel listmodel = new DefaultListModel();
			rowHeader.setModel(listmodel);
			
			pgSelect db = new pgSelect();
			String sql = "Select false as tag, \n" + 
					"trim(proj_alias) as proj_alias, \n" + 
					"null,\n" + 
					"a.pbl_id, \n" + 
					"a.seq_no, \n" + 
					"trim(d.phase) as phase,  \n" + 
					"trim(d.block) as block, \n" + 
					"trim(d.lot) as lot, \n" + 
					"b.entity_id,\n"+
					"b.entity_name as buyer,\n" + 
					"f.model_id, \n" + 
					"f.model_desc as model, f.model_cost, null, null\n" + 
					"FROM rf_sold_unit a  \n" + 
					"LEFT JOIN rf_entity b ON b.entity_id = a.entity_id\n" + 
					"LEFT JOIN mf_project c ON c.proj_id = a.projcode \n" + 
					"LEFT JOIN mf_unit_info d  ON d.proj_id = a.projcode AND d.pbl_id = a.pbl_id  AND d.status_id not in ('X') and coalesce(d.server_id,'')=coalesce(a.server_id,'') and coalesce(d.proj_server,'')= coalesce(a.proj_server,'')\n" + 
					"LEFT JOIN rf_pagibig_computation e  on e.entity_id = a.entity_id AND e.proj_id = a.projcode AND e.pbl_id = a.pbl_id AND e.seq_no = a.seq_no  AND e.status_id = 'A' and coalesce(e.server_id,'')= coalesce(a.server_id,'') and coalesce(e.proj_server,'')= coalesce(a.proj_server,'')\n" + 
					"LEFT JOIN mf_product_model f ON f.model_id = a.model_id and coalesce(f.server_id,'')=coalesce(a.server_id,'') and coalesce(f.proj_server,'')= coalesce(a.proj_server,'')\n" + 
					"LEFT JOIN mf_buyer_type g ON a.BuyerType = g.type_id \n" + 
					"WHERE a.projcode = '"+proj_id+"'\n" + 
					"AND a.status_id = 'A'\n" + 
					"and (a.entity_id,a.projcode, a.pbl_id,a.seq_no) not in (select entity_id, proj_id, pbl_id, seq_no from rf_buyer_status  where TRIM(byrstatus_id) in ('02','27','32','72','79','80','78') and proj_id = '"+proj_id+"' and status_id = 'A')	 \n" + 
					"and (a.entity_id,a.projcode, a.pbl_id,a.seq_no) not in (select a.entity_id,a.proj_id, a.pbl_id,a.seq_no from rf_fire_header a)\n"+
					"AND a.buyertype not in ('07','09','04')\n" + 
					"AND a.model_id not in('009','017') \n" + 
					"ORDER BY get_client_name(a.entity_id)";
			
			/*String sql = "Select false as tag, trim(d.phase) as phase, trim(d.block) as block, trim(d.lot) as lot, b.entity_name as buyer, f.model_desc as model,\n" + 
					"0 as rec_id, a.entity_id, a.projcode, a.pbl_id, a.seq_no, d.release_batch as batch,\n" + 
					"NULLIF((SELECT fire_ins_comp FROM mf_system_parameters limit 1),'4884483204') as ins_comp, \n" + 
					"NULLIF((SELECT fire_ins_line FROM mf_system_parameters limit 1),'6859608888') as ins_line,\n" + 
					"NULLIF(get_model_cost(d.model_id),0) as amt_insured, '' as trxfer ,g.type_alias as pay_scheme \n" + 
					"FROM rf_sold_unit a  \n" + 
					"LEFT JOIN rf_entity b ON b.entity_id = a.entity_id\n" + 
					"LEFT JOIN mf_project c ON c.proj_id = a.projcode \n" + 
					"LEFT JOIN mf_unit_info d  ON d.proj_id = a.projcode AND d.pbl_id = a.pbl_id  AND d.status_id not in ('X') and coalesce(d.server_id,'')=coalesce(a.server_id,'') and coalesce(d.proj_server,'')= coalesce(a.proj_server,'')\n" + 
					"LEFT JOIN rf_pagibig_computation e  on e.entity_id = a.entity_id AND e.proj_id = a.projcode AND e.pbl_id = a.pbl_id AND e.seq_no = a.seq_no  AND e.status_id = 'A' and coalesce(e.server_id,'')= coalesce(a.server_id,'') and coalesce(e.proj_server,'')= coalesce(a.proj_server,'')\n" + 
					"LEFT JOIN mf_product_model f ON f.model_id = a.model_id and coalesce(f.server_id,'')=coalesce(a.server_id,'') and coalesce(f.proj_server,'')= coalesce(a.proj_server,'')\n" + 
					"LEFT JOIN mf_buyer_type g ON a.BuyerType = g.type_id \n" + 
					"WHERE a.projcode = '"+proj_id+"'\n" + 
					"AND a.status_id = 'A'\n" + 
					"and (a.entity_id,a.projcode, a.pbl_id,a.seq_no) not in (select entity_id, proj_id, pbl_id, seq_no from rf_buyer_status  where byrstatus_id in ('02','27','32','72','79','80','78') and proj_id = '"+proj_id+"')	\n" + 
					"AND a.buyertype not in ('07','09','04')\n" + 
					"AND a.model_id not in('009','017') \n" + 
					"order by buyer";*/
						//"AND e.percent_accomplish =1;\n"; //XXX UNCOMMENT ME IF co_ntp_accomplishment has unit with 100 percent accomplished
			db.select(sql);
			FncSystem.out("Display Qualified FI For Enrollment", sql);
			
			if(db.isNotNull()){
				for(int x = 0;x< db.getRowCount(); x++){
					model.addRow(db.getResult()[x]);
					listmodel.addElement(model.getRowCount());
				}
			}
		}
	}
	
	public static void displayEnrolledAccountsBatchNo(String batch_no, modelQualifiedFIEnroll model, JList rowHeader){
		
		if(model != null && rowHeader != null){
			FncTables.clearTable(model);
			
			DefaultListModel listModel = new DefaultListModel();
			rowHeader.setModel(listModel);
			
			pgSelect db = new pgSelect();
			
			String sql = "SELECT TRUE, get_project_alias(a.proj_id), c.unit_id, a.pbl_id, a.seq_no, \n" + 
						 "c.phase, c.block, c.lot, a.entity_id, get_client_name(a.entity_id), \n" + 
						 "b.model_id, get_model_desc(b.model_id), a.amt_insured, '100 %', '1 year'\n" + 
						 "FROM rf_fire_header a\n" + 
						 "LEFT JOIN rf_sold_unit b on b.entity_id = a.entity_id AND b.projcode = a.proj_id AND b.pbl_id = a.pbl_id AND b.seq_no = a.seq_no\n" + 
						 "LEFT JOIN mf_unit_info c on c.proj_id = a.proj_id AND c.pbl_id = a.pbl_id\n" + 
						 "WHERE a.enrollment_batch = '"+batch_no+"';\n";
			
			db.select(sql);
			
			FncSystem.out("Display retrieve enrollment", sql);
			
			if(db.isNotNull()){
				for(int x = 0; x<db.getRowCount(); x++){
					model.addRow(db.getResult()[x]);
					listModel.addElement(model.getRowCount());
				}
			}
		}
	}
	
	public static void displayQualifiedFIRenew(Date cut_off, String insurace_co_id1, String proj_id, modelQualifiedFIRenew model, JList rowHeader){
		if(model != null && rowHeader != null){
			FncTables.clearTable(model);
			
			DefaultListModel listmodel = new DefaultListModel();
			rowHeader.setModel(listmodel);
			
			pgSelect db = new pgSelect();
			
			String sql = "(SELECT FALSE, a.rec_id, get_project_alias(a.proj_id), \n"+
						 "c.unit_id, c.phase, c.block, c.lot, \n"+
						 "a.pbl_id AS \"PBL\", a.seq_no AS \"Seq.\", \n" + 
						 "a.entity_id, get_client_name(a.entity_id) as client, \n" + 
						 "get_model_desc(b.model_id), get_model_cost(b.model_id) , '100 %' ,a.amt_insured, \n" + 
						 "a.premium, a.insurance_line, get_client_name(a.insurance_line),\n" + 
						 "a.date_to as  \"Last Date Covered\",  \n" + 
						 "_months_between(a.date_to::DATE, a.date_from::DATE) as \"Term\",\n"+
						 "a.policy_no, a.date_from, a.date_to \n" + 
						 "FROM rf_fire_header a\n" + 
						 "LEFT JOIN rf_sold_unit b on b.entity_id = a.entity_id and b.projcode = a.proj_id and b.pbl_id = a.pbl_id and b.seq_no = a.seq_no\n" + 
						 "LEFT JOIN mf_unit_info c on c.proj_id = a.proj_id AND c.pbl_id = a.pbl_id\n" + 
						 "WHERE a.status_id = 'A'\n" + 
						 "AND a.proj_id = '"+proj_id+"'\n" + 
						 "AND a.date_terminated IS NULL\n" + 
						 "AND (a.termination_batch IS NULL OR a.termination_batch = '') \n" + 
						 "AND get_group_id(b.buyertype) IN ('02', '03')\n" + 
						 "and (a.entity_id,a.proj_id, a.pbl_id,a.seq_no) not in (select entity_id, proj_id, pbl_id, seq_no from rf_buyer_status  where TRIM(byrstatus_id) in ('27','32','72','79','80','78') and proj_id = '"+proj_id+"') \n"+
						 "AND a.insurance_comp = '"+insurace_co_id1+"' \n"+
						 /*"AND a.renewal_batch IS NULL\n"+*/ //MODIFIED BY MONIQUE DTD 11-21-2023; 
						 //"AND date_part('year', a.date_to::DATE) = date_part('year', CURRENT_DATE)  \n"+
						 "AND a.date_to <= '"+cut_off+"' \n"+ //MODIFIED BY MONIQUE 01-11-2024
						 "AND a.date_to IS NOT NULL\n"+
						 "order by get_client_name(a.entity_id))\n"+
						 "\n"
						 +"UNION \n"
						 +"\n"
						 +"(SELECT FALSE, a.rec_id, get_project_alias(a.proj_id), \n"
						 + "c.unit_id, c.phase, c.block, c.lot, \n"
						 + "a.pbl_id AS \"PBL\", a.seq_no AS \"Seq.\", \n"
						 + "a.entity_id, get_client_name(a.entity_id) as client, \n"
						 + "get_model_desc(c.model_id), get_model_cost(c.model_id) , '100 %' ,a.amt_insured, \n"
						 + "a.premium, a.insurance_line, get_client_name(a.insurance_line),\n"
						 + "a.date_to as  \"Last Date Covered\",  \n"
						 + "_months_between(a.date_to::DATE, a.date_from::DATE) as \"Term\",\n"
						 + "a.policy_no, a.date_from, a.date_to \n"
						 + "FROM rf_fire_header a\n"
						 + "LEFT JOIN mf_unit_info c on c.proj_id = a.proj_id AND c.pbl_id = a.pbl_id\n"
						 + "WHERE a.status_id = 'A'\n"
						 + "AND a.proj_id = '"+proj_id+"'\n"
						 + "AND a.date_terminated IS NULL\n"
						 + "AND (a.termination_batch IS NULL OR a.termination_batch = '') \n"
						 + "and (a.proj_id, a.pbl_id,a.seq_no) not in (select proj_id, pbl_id, seq_no from rf_buyer_status  where TRIM(byrstatus_id) in ('27','32','72','79','80','78') and proj_id = '"+proj_id+"') \n"
						 + "AND a.insurance_comp = '"+insurace_co_id1+"' \n"
						 /*+ "AND a.renewal_batch IS NULL\n"*/
						 + "AND date_to IS NOT NULL\n"
						 + "AND is_under_company is true\n"
						 + "order by get_client_name(a.entity_id))\n"
						 + "\n"
						 + "order by client";
					
			// MODIFIED BY MONIQUE DTD 2023-07-14; TO INCLUDE CANCELLED ACCTS UNDER COMPANY AS POLICY HOLDER
//			String sql = "SELECT FALSE, a.rec_id, get_project_alias(a.proj_id), \n"+
//						 "c.unit_id, c.phase, c.block, c.lot, \n"+
//						 "a.pbl_id AS \"PBL\", a.seq_no AS \"Seq.\", \n" + 
//						 "a.entity_id, get_client_name(a.entity_id), \n" + 
//						 "get_model_desc(b.model_id), get_model_cost(b.model_id) , '100 %' ,a.amt_insured, \n" + 
//						 "a.premium, a.insurance_line, get_client_name(a.insurance_line),\n" + 
//						 "a.date_to as  \"Last Date Covered\",  \n" + 
//						 "_months_between(a.date_to::DATE, a.date_from::DATE) as \"Term\",\n"+
//						 "a.policy_no, a.date_from, a.date_to \n" + 
//						 "FROM rf_fire_header a\n" + 
//						 "LEFT JOIN rf_sold_unit b on b.entity_id = a.entity_id and b.projcode = a.proj_id and b.pbl_id = a.pbl_id and b.seq_no = a.seq_no\n" + 
//						 "LEFT JOIN mf_unit_info c on c.proj_id = a.proj_id AND c.pbl_id = a.pbl_id\n" + 
//						 "WHERE a.status_id = 'A'\n" + 
//						 "AND a.proj_id = '"+proj_id+"'\n" + 
//						 "AND a.date_terminated IS NULL\n" + 
//						 //"AND a.auto_terminate_date IS NULL\n" + 
//						 "AND (a.termination_batch IS NULL OR a.termination_batch = '') \n" + 
//						 //"AND a.date_to::DATE <= '"+cut_off+"'::DATE + INTERVAL '1 Months'\n" + 
//						 "AND get_group_id(b.buyertype) IN ('02', '03')\n" + 
//						 "and (a.entity_id,a.proj_id, a.pbl_id,a.seq_no) not in (select entity_id, proj_id, pbl_id, seq_no from rf_buyer_status  where TRIM(byrstatus_id) in ('27','32','72','79','80','78') and proj_id = '"+proj_id+"') \n"+ //Added by Erick 2022-11-07
//						 //Comment by Erick 2022-11-07
//						 /*"AND NOT EXISTS (SELECT * \n" + 
//						 "		FROM get_fullsettled_accounts('"+proj_id+"')\n" + 
//						 "		WHERE entity_id = a.entity_id \n" + 
//						 "		AND proj_id = a.proj_id\n" + 
//						 "		AND pbl_id = a.pbl_id\n" + 
//						 "		AND seq_no = a.seq_no)\n" + 
//						 "AND NOT EXISTS (SELECT *\n" + 
//						 "		FROM get_cancelled_accounts('015')\n" + 
//						 "		WHERE entity_id = a.entity_id\n" + 	
//						 "		AND proj_id = a.proj_id \n" + 
//						 "		AND pbl_id = a.pbl_id\n" + 
//						 "		AND seq_no = a.seq_no)\n" + *///
//						 "AND a.insurance_comp = '"+insurace_co_id1+"' \n"+
//						 "AND a.renewal_batch IS NULL\n"+
//						 "order by get_client_name(a.entity_id)";
						
			db.select(sql);
			FncSystem.out("Display Qualified FI For Renewal", sql);
			
			if(db.isNotNull()){
				for(int x = 0; x < db.getRowCount(); x++){
					model.addRow(db.getResult()[x]);
					listmodel.addElement(model.getRowCount());
				}
			}
		}
	}
	
	public static void displayRenewedAccountsBatchNo(String batch_no, modelQualifiedFIRenew model, JList rowHeader){
		
		if(model != null && rowHeader != null){
			FncTables.clearTable(model);
			
			DefaultListModel listModel = new DefaultListModel();
			rowHeader.setModel(listModel);
			
			pgSelect db = new pgSelect();
			String sql = "SELECT TRUE, a.rec_id, get_project_alias(a.proj_id),\n" + 
						 "c.unit_id, c.phase, c.block, c.lot, a.pbl_id, a.seq_no,\n" + 
						 "a.entity_id, get_client_name(a.entity_id), get_model_desc(b.model_id), \n" + 
						 "get_model_cost(b.model_id), '100 %', \n" + 
						 "a.amt_insured, a.premium, a.insurance_line, get_client_name(a.insurance_line), a.date_to, \n" + 
						 "_months_between(a.date_to::DATE, a.date_from::DATE), a.policy_no, a.date_from, a.date_to\n" + 
						 "FROM rf_fire_header a\n" + 
						 "LEFT JOIN rf_sold_unit b on b.entity_id = a.entity_id AND b.projcode = a.proj_id AND b.pbl_id = a.pbl_id AND b.seq_no = a.seq_no\n" + 
						 "LEFT JOIN mf_unit_info c on c.proj_id = a.proj_id AND c.pbl_id = a.pbl_id\n" + 
						 "WHERE a.renewal_batch = '"+batch_no+"';\n";
			db.select(sql);
			
			FncSystem.out("Display sql for Retrieving of Renewed Accounts", sql);
			
			if(db.isNotNull()){
				for(int x = 0; x<db.getRowCount(); x++){
					model.addRow(db.getResult()[x]);
					listModel.addElement(model.getRowCount());
				}
			}
		}
	}
	
	//OK NA DITO
	public static void displayQualifiedFITerminate(String insurance_co_id, String proj_id ,Date cut_off_date, modelQualifiedFITerminate model, JList rowHeader){
		if(model != null && rowHeader != null){
			FncTables.clearTable(model);
			
			DefaultListModel listModel = new DefaultListModel();
			rowHeader.setModel(listModel);
			
			pgSelect db = new pgSelect();
			
			
			String sql = "SELECT FALSE,a.rec_id, a.proj_id, b.unit_id, a.pbl_id, a.seq_no, get_project_alias(a.proj_id) as proj_alias ,b.phase, b.block, b.lot, a.entity_id,\n" + 
					"get_client_name(a.entity_id), a.amt_insured, age(a.date_to, a.date_from)::VARCHAR, \n" + 
					"(SELECT tran_date FROM rf_buyer_status where entity_id = a.entity_id and proj_id = a.proj_id and pbl_id = a.pbl_id and seq_no = a.seq_no and TRIM(byrstatus_id) IN ('27','32','72','79','80','78') and status_id = 'A' ORDER BY tran_date DESC, actual_date DESC LIMIT 1) as effectivity_date, \n" + 
					"a.policy_no ,a.date_from, a.date_to, \n" + 
					"get_buyer_status((SELECT byrstatus_id FROM rf_buyer_status where entity_id = a.entity_id and proj_id = a.proj_id and pbl_id = a.pbl_id and seq_no = a.seq_no and TRIM(byrstatus_id) IN ('27','32','72','79','80','78') AND status_id = 'A' ORDER BY tran_date DESC, actual_date DESC LIMIT 1))\n" + 
					"\n" + 
					"FROM rf_fire_header a\n" + 
					"LEFT JOIN mf_unit_info b on b.proj_id = a.proj_id and b.pbl_id = a.pbl_id\n" + 
					"WHERE a.status_id = 'A'\n" + 
					"AND a.proj_id = '"+proj_id+"' \n" + 
					"AND a.insurance_comp = '"+insurance_co_id+"'\n" + 
					"and EXISTS (SELECT *\n" + 
					"		    FROM rf_buyer_status \n" + 
					"		    where entity_id = a.entity_id \n" + 
					"		    and proj_id = a.proj_id \n" + 
					"		    and pbl_id = a.pbl_id \n" + 
					"		    and seq_no = a.seq_no \n" + 
					"		    and TRIM(byrstatus_id) IN ('27','32','1F','25','26','27','72','1C','51','1D','79','80','78')\n" + 
					"		    AND status_id = 'A' \n"+
					"			AND actual_date::dATE >= '"+cut_off_date+"'::DATE)\n" +

					"AND a.date_terminated IS NULL";
			
			db.select(sql);
			FncSystem.out("Display Qualified FI For Termination", sql);
			
			if(db.isNotNull()){
				for(int x = 0; x < db.getRowCount(); x++){
					model.addRow(db.getResult()[x]);
					listModel.addElement(model.getRowCount());
				}
			}
		}
	}
	
	public static void displayTerminatedAccountsBatchNo(String batch_no, modelQualifiedFITerminate model, JList rowHeader){
		if(model != null && rowHeader != null){
			FncTables.clearTable(model);
			
			DefaultListModel listModel = new DefaultListModel();
			rowHeader.setModel(listModel);
			
			pgSelect db = new pgSelect();
			
			String sql = "SELECT TRUE, a.rec_id, a.proj_id, b.unit_id, a.pbl_id, a.seq_no, get_project_alias(a.proj_id), b.phase, b.block, b.lot,\n" + 
						 "a.entity_id, get_client_name(a.entity_id), a.amt_insured, age(a.date_to, a.date_from)::VARCHAR, c.tran_date, a.policy_no, \n" + 
						 "a.date_from, a.date_to, get_buyer_status(c.byrstatus_id)\n" + 
						 "FROM rf_fire_header a\n" + 
						 "LEFT JOIN mf_unit_info b on b.proj_id = a.proj_id AND b.pbl_id = a.pbl_id\n" + 
						 "INNER JOIN get_fullsettled_accounts() c on c.entity_id = a.entity_id AND c.proj_id = a.proj_id AND c.pbl_id = a.pbl_id AND c.seq_no = a.seq_no\n" + 
						 "WHERE a.termination_batch = '"+batch_no+"';";
			db.select(sql);
			
			FncSystem.out("Display sql for retrieving of terminated accounts", sql);
			if(db.isNotNull()){
				for(int x= 0; x<db.getRowCount(); x++){
					model.addRow(db.getResult()[x]);
					listModel.addElement(model.getRowCount());
				}
			}
		}
	}
	
	public static void displayEnrolledAccounts(Integer search_by,String search_value, modelFI_ForPmtInsurance model, JList rowHeader){
		if(model != null && rowHeader != null){
			FncTables.clearTable(model);
			
			DefaultListModel listModel = new DefaultListModel();
			rowHeader.setModel(listModel);
			
			pgSelect db = new pgSelect();
			
			/*String sql = "SELECT c.unit_id, a.pbl_id, a.seq_no, c.description, a.entity_id, get_client_name(a.entity_id), \n" + 
						 "get_model_name(b.model_id), get_payment_stage(a.entity_id, a.proj_id, a.pbl_id, a.seq_no), \n" + 
						 "a.amt_insured, a.fire_lightning, a.full_earthquake, a.typhoon, a.flood, a.ext_cover, a.rsmd, \n" + 
						 "a.fire_lightning + a.full_earthquake + a.typhoon + a.flood + a.ext_cover + a.rsmd,\n" + 
						 "a.doc_stamps, a.evat, a.fire_srvc_tax, a.loc_govt_tax, a.premium\n" + 
						 "FROM rf_fire_header a\n" + 
						 "LEFT JOIN rf_sold_unit b ON b.entity_id = a.entity_id AND b.projcode = a.proj_id AND b.pbl_id = a.pbl_id AND b.seq_no = a.seq_no\n" + 
						 "LEFT JOIN mf_unit_info c ON c.proj_id = a.proj_id AND c.pbl_id = a.pbl_id\n" + 
						 "WHERE a.rplf_no = '"+rplf_no+"';";
			db.select(sql);*/
			
			String sql = "SELECT c.unit_id, a.pbl_id, a.seq_no, c.description, a.entity_id, get_client_name(a.entity_id), \n" + 
						 "get_model_name(b.model_id), get_payment_stage(a.entity_id, a.proj_id, a.pbl_id, a.seq_no), \n" + 
						 "a.amt_insured, a.fire_lightning, a.full_earthquake, a.typhoon, a.flood, a.ext_cover, a.rsmd, \n" + 
						 "a.fire_lightning + a.full_earthquake + a.typhoon + a.flood + a.ext_cover + a.rsmd,\n" + 
						 "a.doc_stamps, a.evat, a.fire_srvc_tax, a.loc_govt_tax, a.premium\n" + 
						 "FROM rf_fire_header a\n" + 
						 "LEFT JOIN rf_sold_unit b ON b.entity_id = a.entity_id AND b.projcode = a.proj_id AND b.pbl_id = a.pbl_id AND b.seq_no = a.seq_no\n" + 
						 "LEFT JOIN mf_unit_info c ON c.proj_id = a.proj_id AND c.pbl_id = a.pbl_id\n" + 
						 "WHERE CASE WHEN "+search_by+" = 0 THEN a.invoice_no = '"+search_value+"' ELSE a.rplf_no = '"+search_value+"' END;\n";
			db.select(sql);
			FncSystem.out("Display retrieving of enrolled accounts by rplf no", sql);
			
			if(db.isNotNull()){
				for(int x= 0; x<db.getRowCount(); x++){
					model.addRow(db.getResult()[x]);
					listModel.addElement(model.getRowCount());
				}
			}
		}
	}
	
	public static void displayForPaymentInsurance(modelFI_ForPmtInsurance model, JList rowHeader){
		
		FncTables.clearTable(model);
		DefaultListModel listModel = new DefaultListModel();
		rowHeader.setModel(listModel);
		
		pgSelect db = new pgSelect();
		
		String sql = "SELECT distinct on (a.rplf_no) \n" + 
					 "get_client_name(a.insurance_comp), \n" + 
					 "a.invoice_no, a.rplf_no, d.date_due, d.chk_amt\n" + 
					 "from rf_fire_header a\n" + 
					 "LEFT JOIN rf_request_header b on b.rplf_no = a.rplf_no\n" + 
					 "LEFT JOIN rf_pv_header c on c.pv_no = b.rplf_no\n" + 
					 "LEFT JOIN rf_check d on d.cv_no = c.cv_no\n" + 
					 "where a.termination_batch is null\n" + 
					 "and a.rplf_no is not null;";
		db.select(sql);
		FncSystem.out("Display sql For Payment to Insurance", sql);
		
		if(db.isNotNull()){
			for(int x = 0; x<db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
				listModel.addElement(model.getRowCount());
			}
		}
	}
	
	public static void displayQualifiedFIEnrollment(String co_id, String batch_no, String insurance_co, modelFire_ForIssuancePolicy model, JList rowHeader){
		if(model != null && rowHeader != null){
			FncTables.clearTable(model);
			
			DefaultListModel listModel = new DefaultListModel();
			rowHeader.setModel(listModel);
			
			pgSelect db = new pgSelect();
			
			String sql = "SELECT * FROM sp_generate_fire_qualified_enrollment('"+co_id+"', '"+batch_no+"', '"+insurance_co+"')";
			db.select(sql);
			
			FncSystem.out("Display generate fire qualified enrollment", sql);
			
			if(db.isNotNull()){
				for(int x=  0; x<db.getRowCount(); x++){
					model.addRow(db.getResult()[x]);
					listModel.addElement(model.getRowCount());
				}
			}
		}
	}
	
	public static void displayQualifiedFIRenewal(String co_id, String batch_no, String insurance_co, modelFire_ForIssuancePolicy model, JList rowHeader){
		if(model != null && rowHeader != null){
			FncTables.clearTable(model);
			
			DefaultListModel listModel = new DefaultListModel();
			rowHeader.setModel(listModel);
			
			pgSelect db = new pgSelect();
			
			String sql = "SELECT * FROM sp_generate_fire_qualified_renewal('"+co_id+"', '"+batch_no+"', '"+insurance_co+"')";
			db.select(sql);
			
			FncSystem.out("Display generate fire qualified renewal", sql);
			
			if(db.isNotNull()){
				for(int x= 0; x<db.getRowCount(); x++){
					model.addRow(db.getResult()[x]);
					listModel.addElement(model.getRowCount());
				}
			}
		}
	}
	
	public static void displayApprovedFIEnrollment(String co_id ,String batch_no, modelApproveQualified model, JList rowHeader){
		if(model != null && rowHeader != null){
			FncTables.clearTable(model);
			
			DefaultListModel listModel = new DefaultListModel();
			rowHeader.setModel(listModel);
			
			pgSelect db = new pgSelect();
			
			String sql = "SELECT FALSE, b.rec_id as \"ID\", \n" + 
					"c.unit_id as \"Unit ID\",a.pbl_id as \"PBL\", a.seq_no as \"Seq.\", \n" + 
					"get_unit_info(c.unit_id) as \"Unit Desc\", \n" + 
					"a.entity_id as \"Entity ID\", get_client_name(a.entity_id) as \"Client Name\",\n" + 
					"get_model_desc(a.model_id) as \"Model Desc\", get_model_cost(a.model_id) as \"Model Cost\"\n" + 
					//"12 as \"Term\" \n" + 
					"FROM rf_sold_unit a\n" + 
					"LEFT JOIN rf_fire_header b on b.entity_id = a.entity_id AND b.proj_id = a.projcode AND b.pbl_id = a.pbl_id AND b.seq_no = a.seq_no\n" + 
					"LEFT JOIN mf_unit_info c on c.proj_id = a.projcode AND c.pbl_id = a.pbl_id\n" + 
					"WHERE get_proj_co_id(a.projcode) = '"+co_id+"'\n" + 
					"AND b.status_id = 'I'\n" + 
					"AND NOT EXISTS (SELECT *\n" + 
					"		FROM get_cancelled_accounts(a.projcode)\n" + 
					"		WHERE entity_id = a.entity_id \n" + 
					"		AND proj_id = a.projcode\n" + 
					"		AND pbl_id = a.pbl_id\n" + 
					"		AND seq_no = a.seq_no)\n" + 
					"AND NOT EXISTS (SELECT *\n" + 
					"		FROM get_fullsettled_accounts(a.projcode)\n" + 
					"		WHERE entity_id = a.entity_id\n" + 
					"		AND proj_id = a.projcode \n" + 
					"		AND pbl_id = a.pbl_id\n" + 
					"		AND seq_no = a.seq_no)\n" + 
					"AND get_group_id(a.buyertype) = '02'\n" + 
					"AND b.enrollment_batch = '"+batch_no+"' \n" + 
					"ORDER BY \"Client Name\", \"Unit ID\";";
			db.select(sql);
			
			FncSystem.out("Display Qualifed Enrolled Accounts for Approval", sql);
			
			if(db.isNotNull()){
				for(int x= 0; x<db.getRowCount(); x++){
					model.addRow(db.getResult()[x]);
					listModel.addElement(model.getRowCount());
				}
			}
		}
	}
	
	public static void postQualify_Enrollment(String proj_id,String enrollment_batch_no, modelQualifiedFIEnroll model){
		pgUpdate db = new pgUpdate();
		
		for(int x = 0; x < model.getRowCount(); x++){
			Boolean isSelected = (Boolean) model.getValueAt(x, 0);
			if(isSelected){
				String entity_id = (String) model.getValueAt(x, 8);
				String unit_id = (String) model.getValueAt(x, 2);
				String pbl_id = (String) model.getValueAt(x, 3);
				Integer seq_no = (Integer) model.getValueAt(x, 4);
				BigDecimal insurance_amt = (BigDecimal) model.getValueAt(x, 12);
				
				String sql = "INSERT INTO rf_fire_header(/*rec_id,*/ entity_id, proj_id, pbl_id, seq_no, enrollment_batch, \n" + 
							 "term, amt_insured, date_from, date_to, fire_lightning, full_earthquake, typhoon, flood, \n"+
							 "ext_cover, rsmd, doc_stamps, evat, fire_srvc_tax, loc_govt_tax, premium, date_enrolled, \n"+
							 "date_approved, policy_no, invoice_no, insurance_comp, insurance_line, date_terminated, \n" + 
							 "renewal_batch, termination_batch, remarks, refund, transfer_unit, status_id, \n"+
							 "auto_terminate_date, auto_terminate_reason, is_under_company)\n" + 
							 "VALUES (/*(select coalesce(max(rec_id),0) + 1 from rf_fire_header),*/ \n" + 
							 "'"+entity_id+"', '"+proj_id+"', '"+pbl_id+"', /*getinteger('"+unit_id+"')::VARCHAR,*/ "+seq_no+", '"+enrollment_batch_no+"', \n" + 
							 "NULL, "+insurance_amt+", NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, now(), \n"+
							 "NULL, NULL, NULL, NULL, NULL, NULL, NULL , NULL, NULL, NULL, NULL, 'A', NULL, NULL, NULL);\n";
				db.executeUpdate(sql, true);
			}
		}
		db.commit();
	}
	
	public static void postQualify_Renewal(String batch_no ,String insurance_co, String proj_id ,modelQualifiedFIRenew model){
		pgUpdate db = new pgUpdate();
		
		String termination_batch = newQualifiedTerminationBatchNo();
		
		for(int x= 0; x<model.getRowCount(); x++){
			Boolean isSelected = (Boolean) model.getValueAt(x, 0);
			
			if(isSelected){
				Integer rec_id = (Integer) model.getValueAt(x, 1);
				String unit_id = (String) model.getValueAt(x, 3);
				Integer seq_no = (Integer) model.getValueAt(x, 8);
				String entity_id = (String) model.getValueAt(x, 9);
				BigDecimal insurance_amt = (BigDecimal) model.getValueAt(x, 14);
				
				String sqlterminate = "UPDATE rf_fire_header \n" + 
									  "SET auto_terminate_date = now(),\n" + 
									  "termination_batch = '"+termination_batch+"',\n" + 
									  "auto_terminate_reason = '	', \n" + 
									  "refund = 0.00 \n" + 
									  "WHERE rec_id = "+rec_id+"\n" +
									  "AND entity_id = '"+entity_id+"' \n"+
									  "AND proj_id = '"+proj_id+"' \n"+
									  "AND pbl_id = getinteger('"+unit_id+"')::VARCHAR\n" + 
									  "AND seq_no = "+seq_no+" \n"; 
				db.executeUpdate(sqlterminate, true);
				
				String sqlrenew = "INSERT INTO rf_fire_header(rec_id, entity_id, proj_id, pbl_id, seq_no, enrollment_batch, \n" + 
								  "term, amt_insured, date_from, date_to, fire_lightning, full_earthquake, typhoon, \n" + 
								  "flood, ext_cover, rsmd, doc_stamps, evat, fire_srvc_tax, loc_govt_tax, premium, \n" + 
								  "date_enrolled, date_approved, policy_no, invoice_no, insurance_comp, insurance_line, \n" + 
								  "date_terminated, renewal_batch, termination_batch, remarks, refund, transfer_unit, \n" + 
								  "status_id, auto_terminate_date, auto_terminate_reason, is_under_company)\n" + 
								  "VALUES ((SELECT COALESCE(max(rec_id),0) + 1 FROM rf_fire_header), \n" + 
								  "'"+entity_id+"', (SELECT proj_id FROM mf_unit_info WHERE unit_id = '"+unit_id+"'), \n" + 
								  "getinteger('"+unit_id+"')::VARCHAR, "+seq_no+", NULL, NULL, "+insurance_amt+", NULL, NULL, NULL, NULL, NULL, NULL, NULL, \n" + 
								  "NULL, NULL, NULL, NULL, NULL, NULL, now(), NULL, NULL, NULL, '"+insurance_co+"', '"+insurance_co+"', \n" + 
								  "NULL, '"+batch_no+"', NULL, NULL, NULL, NULL, 'A', NULL, NULL, NULL); \n";
				
				db.executeUpdate(sqlrenew, true);
			}
		}
		
		db.commit();
	}
	
	public static void postQualifiedEnrollment(String co_id, String insurance_co, String insurance_broker, /*String rplf_no,*/ 
			String policy_no, String invoice_no, Date date_from, Date date_to, modelFire_ForIssuancePolicy model){
		
		ArrayList<Integer> listRecID = new ArrayList<Integer>();
		ArrayList<String> listEntityID = new ArrayList<String>();
		ArrayList<String> listUnitID = new ArrayList<String>();
		ArrayList<Integer> listSeqNo = new ArrayList<Integer>();
		ArrayList<BigDecimal> listInsuranceAmt = new ArrayList<BigDecimal>();
		
		for(int x= 0; x<model.getRowCount(); x++){
			Boolean isSelected = (Boolean) model.getValueAt(x, 0);
			
			if(isSelected){
				Integer rec_id = (Integer) model.getValueAt(x, 1);
				String entity_id = (String) model.getValueAt(x, 7);
				String unit_id = (String) model.getValueAt(x, 3);
				Integer seq_no = (Integer) model.getValueAt(x, 5);
				BigDecimal insurance_amt = (BigDecimal) model.getValueAt(x, 11);
				
				listRecID.add(rec_id);
				listUnitID.add(String.format("'%s'", unit_id));
				listEntityID.add(String.format("'%s'", entity_id));
				listSeqNo.add(seq_no);
				listInsuranceAmt.add(insurance_amt);
			}
		}
		
		String rec_id = listRecID.toString().replaceAll("\\[|\\]", "");
		String unit_id = listUnitID.toString().replaceAll("\\[|\\]", "");
		String seq_no = listSeqNo.toString().replaceAll("\\[|\\]", "");
		String entity_id = listEntityID.toString().replaceAll("\\[|\\]", "");
		String insurance_amt = listInsuranceAmt.toString().replaceAll("\\[|\\]", "");
		String rplf_no = ""; 
		
		
		pgSelect db = new pgSelect();
		
		String sql = "SELECT sp_post_fire_qualified_enrollment('"+co_id+"', '"+insurance_co+"', '"+insurance_broker+"', \n"+
					 "'"+rplf_no+"', '"+policy_no+"', '"+invoice_no+"', '"+date_from+"', '"+date_to+"', ARRAY["+rec_id+"]::INT[], \n"+
					 "ARRAY["+entity_id+"]::VARCHAR[], ARRAY["+unit_id+"]::VARCHAR[], ARRAY["+seq_no+"]::INT[], ARRAY["+insurance_amt+"]::NUMERIC[], \n"+
					 "'"+UserInfo.EmployeeCode+"', '"+UserInfo.Branch+"')";
		db.select(sql);
		
		FncSystem.out("Display sql for posting of qualified enrollment", sql);
		
	}
	
	public static void postQualifiedFIRenewal(String co_id, String batch_no, String insurance_co, /*String rplf_no,*/ String policy_no, 
			String invoice_no, Date date_from, Date date_to, modelFire_ForIssuancePolicy model){
		
		ArrayList<Integer> listRecID = new ArrayList<Integer>();
		ArrayList<String> listEntityID = new ArrayList<String>();
		ArrayList<String> listUnitID = new ArrayList<String>();
		ArrayList<Integer> listSeqNo = new ArrayList<Integer>();
		ArrayList<BigDecimal> listInsuranceAmt = new ArrayList<BigDecimal>();
		ArrayList<BigDecimal> listDocStamps = new ArrayList<BigDecimal>();
		ArrayList<BigDecimal> listEVAT = new ArrayList<BigDecimal>();
		ArrayList<BigDecimal> listFST = new ArrayList<BigDecimal>();
		ArrayList<BigDecimal> listLGT = new ArrayList<BigDecimal>();
		
		
		for(int x= 0; x<model.getRowCount(); x++){
			Boolean isSelected = (Boolean) model.getValueAt(x, 0);
			
			if(isSelected){
				Integer rec_id = (Integer) model.getValueAt(x, 1);
				String entity_id = (String) model.getValueAt(x, 7);
				String unit_id = (String) model.getValueAt(x, 3);
				Integer seq_no = (Integer) model.getValueAt(x, 5);
				BigDecimal insurance_amt = (BigDecimal) model.getValueAt(x, 11);
				BigDecimal doc_stamps = (BigDecimal) model.getValueAt(x, 19);
				BigDecimal evat = (BigDecimal) model.getValueAt(x, 20);
				BigDecimal fst = (BigDecimal) model.getValueAt(x, 21);
				BigDecimal lgt = (BigDecimal) model.getValueAt(x, 22);
				
				listRecID.add(rec_id);
				listUnitID.add(String.format("'%s'", unit_id));
				listEntityID.add(String.format("'%s'", entity_id));
				listSeqNo.add(seq_no);
				listInsuranceAmt.add(insurance_amt);
				listDocStamps.add(doc_stamps);
				listEVAT.add(evat);
				listFST.add(fst);
				listLGT.add(lgt);
			}
		}
		
		String rec_id = listRecID.toString().replaceAll("\\[|\\]", "");
		String unit_id = listUnitID.toString().replaceAll("\\[|\\]", "");
		String seq_no = listSeqNo.toString().replaceAll("\\[|\\]", "");
		String entity_id = listEntityID.toString().replaceAll("\\[|\\]", "");
		String insurance_amt = listInsuranceAmt.toString().replaceAll("\\[|\\]", "");
		String doc_stamps = listDocStamps.toString().replaceAll("\\[|\\]", "");
		String evat = listEVAT.toString().replaceAll("\\[|\\]", "");
		String fst = listFST.toString().replaceAll("\\[|\\]", "");
		String lgt = listLGT.toString().replaceAll("\\[|\\]", "");
		String rplf_no = ""; 
		
		pgSelect db = new pgSelect();
		
		String sql = "SELECT sp_post_fire_qualified_renewal('"+co_id+"', '"+batch_no+"', '"+insurance_co+"', '"+rplf_no+"', '"+policy_no+"', '"+invoice_no+"', \n"+
					 "'"+date_from+"', '"+date_to+"', ARRAY["+rec_id+"]::INT[], ARRAY["+unit_id+"]::VARCHAR[], ARRAY["+seq_no+"]::INT[], ARRAY["+entity_id+"]::VARCHAR[], \n"+
					 "ARRAY["+insurance_amt+"]::NUMERIC[],ARRAY["+doc_stamps+"]::NUMERIC[],ARRAY["+evat+"]::NUMERIC[],ARRAY["+fst+"]::NUMERIC[],ARRAY["+lgt+"]::NUMERIC[], '"+UserInfo.EmployeeCode+"', '"+UserInfo.Branch+"')";
		db.select(sql);
		
		FncSystem.out("Display sql for posting of qualified renewal", sql);
		
		
	}
	
	/*public static void postQualifiedFIRenewal(String insurance_co_id, String proj_id, String policy_no, String invoice_no, 
			Date date_approved, String batch_no,String rplf_no, modelQualifiedFIRenew model){
		
		//XXX REMOVE THIS
		
		//POSTING OF QUALIFIED ACCOUNTS FOR RENEWAL
		ArrayList<Integer> listRecID = new ArrayList<Integer>();
		ArrayList<String> listUnitID = new ArrayList<String>();
		ArrayList<String> listPBL = new ArrayList<String>();
		ArrayList<Integer> listSeqNo = new ArrayList<Integer>();
		ArrayList<String> listEntityID = new ArrayList<String>();
		ArrayList<BigDecimal> listInsuranceAmt = new ArrayList<BigDecimal>();
		ArrayList<String> listInsuranceLine = new ArrayList<String>();
		ArrayList<String> listLastDateCovered = new ArrayList<String>();
		ArrayList<Integer> listTerm = new ArrayList<Integer>();
		
		for(int x = 0; x<model.getRowCount(); x++){
			Boolean isSelected = (Boolean) model.getValueAt(x, 0);
			Integer rec_id = (Integer) model.getValueAt(x, 1);
			String unit_id = (String) model.getValueAt(x, 2);
			String pbl_id = (String) model.getValueAt(x, 3);
			Integer seq_no = (Integer) model.getValueAt(x, 4);
			String entity_id = (String) model.getValueAt(x, 6);
			BigDecimal amt_insured = (BigDecimal) model.getValueAt(x, 9);
			String insurance_line = (String) model.getValueAt(x, 11);
			Date last_date_covered = (Date) model.getValueAt(x, 13);
			Integer term = (Integer) model.getValueAt(x, 14);
			
			if(isSelected){
				
				listRecID.add(rec_id);
				listUnitID.add(String.format("'%s'", unit_id));
				listPBL.add(String.format("'%s'", pbl_id));
				listSeqNo.add(seq_no);
				listEntityID.add(String.format("'%s'", entity_id));
				listInsuranceAmt.add(amt_insured);
				listInsuranceLine.add(String.format("'%s'", insurance_line));
				listLastDateCovered.add(String.format("'%s'", last_date_covered.toString()));
				listTerm.add(term);
				
			}
		}
		
		String rec_id = listRecID.toString().replaceAll("\\[|\\]", "");
		String unit_id = listUnitID.toString().replaceAll("\\[|\\]", "");
		String pbl_id = listPBL.toString().replaceAll("\\[|\\]", "");
		String seq_no = listSeqNo.toString().replaceAll("\\[|\\]", "");
		String entity_id = listEntityID.toString().replaceAll("\\[|\\]", "");
		String insurance_amt = listInsuranceAmt.toString().replaceAll("\\[|\\]", "");
		String insurance_line = listInsuranceLine.toString().replaceAll("\\[|\\]", "");
		String last_date_covered = listLastDateCovered.toString().replaceAll("\\[|\\]", "");
		String term = listTerm.toString().replaceAll("\\[|\\]", "");
		
		System.out.printf("Display last date covered: %s%n", listLastDateCovered.toString());
		System.out.printf("Display last date covered: %s%n", last_date_covered);
		
		pgSelect db = new pgSelect();
		
		String sql = "SELECT sp_post_fire_qualified_renewal(ARRAY["+rec_id+"]::INT[], ARRAY["+unit_id+"]::VARCHAR[], ARRAY["+pbl_id+"]::VARCHAR[], \n"+
					 "ARRAY["+seq_no+"]::INT[], ARRAY["+entity_id+"]::VARCHAR[], ARRAY["+insurance_amt+"]::NUMERIC[], ARRAY["+insurance_line+"]::VARCHAR[], \n"+
					 "ARRAY["+last_date_covered+"]::TIMESTAMP WITHOUT TIME ZONE[], ARRAY["+term+"]::INT[], '"+insurance_co_id+"', \n"+
					 "'"+proj_id+"', '"+policy_no+"', '"+invoice_no+"', '"+date_approved+"', '"+batch_no+"', '"+rplf_no+"', \n"+
					 "'"+UserInfo.Branch+"', '"+UserInfo.EmployeeCode+"')";
		db.select(sql);
		
		//FncSystem.out("Display sql for Posting of Qualified Accounts for Renewal", sql);
	}*/
	
	// Posting of Qualified Accounts for Renewal
	/*public static void postQualifiedFIRenewal(String proj_id, String insurance_co_id ,String renewal_batch_no, String policy_no, 
			String invoice_no, Date date_from, Date date_to ,modelApproveQualifiedFI model){//OK NA DITO
		
		ArrayList<Integer> listRecID = new ArrayList<Integer>();
		ArrayList<String> listEntityID = new ArrayList<String>();
		ArrayList<String> listUnitID = new ArrayList<String>();
		ArrayList<String> listPBL = new ArrayList<String>();
		ArrayList<Integer> listSeq = new ArrayList<Integer>();
		ArrayList<BigDecimal> listInsuranceAmt = new ArrayList<BigDecimal>();
		
		
		for(int x = 0; x<model.getRowCount(); x++){
			Boolean isSelected = (Boolean) model.getValueAt(x, 0);
			
			if(isSelected){
				Integer rec_id = (Integer) model.getValueAt(x, 1);
				String unit_id = (String) model.getValueAt(x, 2);
				String pbl_id = (String) model.getValueAt(x, 3);
				Integer seq_no = (Integer) model.getValueAt(x, 4);
				String entity_id = (String) model.getValueAt(x, 6);
				
				String insurance_line = (String) model.getValueAt(x, 7);
				BigDecimal insurance_amt = (BigDecimal) model.getValueAt(x, 9);
				Date last_date_covered = (Date) model.getValueAt(x, 13);
				
				listRecID.add(rec_id);
				listEntityID.add(String.format("'%s'", entity_id));
				listUnitID.add(String.format("'%s'", unit_id));
				listPBL.add(String.format("'%s'", pbl_id));
				listSeq.add(seq_no);
				listInsuranceAmt.add(insurance_amt);
			}
		}
		
		String rec_id = listRecID.toString().replaceAll("\\[|\\]", "");
		String unit_id = listUnitID.toString().replaceAll("\\[|\\]", "");
		String pbl_id = listPBL.toString().replaceAll("\\[|\\]", "");
		String seq_no = listSeq.toString().replaceAll("\\[|\\]", "");
		String entity_id = listEntityID.toString().replaceAll("\\[|\\]", "");
		String insurance_amt = listInsuranceAmt.toString().replaceAll("\\[|\\]", "");
		
		pgSelect db = new pgSelect();
		String sql = "SELECT sp_post_fire_qualified_renewal(ARRAY["+rec_id+"]::INT[], ARRAY["+unit_id+"]::VARCHAR[], ARRAY["+pbl_id+"]::VARCHAR[], \n"+
					 "ARRAY["+seq_no+"]::INT[], ARRAY["+entity_id+"]::VARCHAR[], ARRAY["+insurance_amt+"]::NUMERIC[], '"+insurance_co_id+"' ,'"+renewal_batch_no+"')";
		
		String sql = "SELECT sp_post_fire_qualified_renewal('"+proj_id+"', '"+renewal_batch_no+"', '"+insurance_co_id+"', '"+policy_no+"', \n"+
					 "'"+invoice_no+"', '"+date_from+"', '"+date_to+"', ARRAY["+rec_id+"]::INT[], ARRAY["+unit_id+"]::VARCHAR[], ARRAY["+seq_no+"]::INT[], \n"+
					 "ARRAY["+entity_id+"]::VARCHAR[], ARRAY["+insurance_amt+"]::NUMERIC[], '"+UserInfo.EmployeeCode+"', '"+UserInfo.Branch+"')";
		db.select(sql);
		
		FncSystem.out("Display sql for posting of Qualified Accounts for Renewal", sql);
	}*/
	
	//POSTING OF QUALIFIED FIR ACCOUNTS FOR TERMINATION
	public static void postQualifiedFITerminate(String termination_batch_no ,modelQualifiedFITerminate model){//OK NA DITO
		pgUpdate db = new pgUpdate();
		
		for(int x = 0; x<model.getRowCount(); x++){
			Boolean isSelected = (Boolean) model.getValueAt(x, 0);
			if(isSelected){
				
				Integer rec_id = (Integer) model.getValueAt(x, 1);
				String proj_id = (String) model.getValueAt(x, 2);
				String pbl_id = (String) model.getValueAt(x, 4);
				Integer seq_no = (Integer) model.getValueAt(x, 5);
				String entity_id = (String) model.getValueAt(x, 10);
				String buyer_status = (String) model.getValueAt(x, 18);
				
				String sql = "UPDATE rf_fire_header \n"+
						 	 "SET date_terminated = now(), termination_batch = '"+termination_batch_no+"', \n"+
						 	 "auto_terminate_reason = '"+buyer_status+"' \n"+
						 	 "WHERE rec_id = "+rec_id+" \n"+
						 	 "AND entity_id = '"+entity_id+"'\n"+
						 	 "AND proj_id = '"+proj_id+"' \n"+
						 	 "AND pbl_id = '"+pbl_id+"'\n"+
						 	 "AND seq_no = "+seq_no+"";
				db.executeUpdate(sql, true);
			
			}
		}
		
		db.commit();
	}
	
	//POSTING OF APPROVAL OF FIRE INSURANCE ENROLLED ACCOUNTS //ADD date From and date to here
	public static void postApprovedFIEnrollment(String co_id, String insurance_co_id2, String insurance_broker_id, String policy_no, String invoice_no, 
			 Date date_from, Date date_to ,String rplf_no, modelApproveQualified model){
		
		ArrayList<Integer> listRecID = new ArrayList<Integer>();
		ArrayList<String> listUnitID = new ArrayList<String>();
		ArrayList<String> listPBL = new ArrayList<String>();
		ArrayList<Integer> listSeqNo = new ArrayList<Integer>();
		ArrayList<String> listEntityID = new ArrayList<String>();
		ArrayList<BigDecimal> listInsuranceAmt = new ArrayList<BigDecimal>();
		//ArrayList<Integer> listTerm = new ArrayList<Integer>();
		
		for(int x = 0; x<model.getRowCount(); x++){
			Boolean isSelected = (Boolean) model.getValueAt(x, 0);
			Integer rec_id = (Integer) model.getValueAt(x, 1);
			String unit_id = (String) model.getValueAt(x, 2);
			String pbl_id = (String) model.getValueAt(x, 3);
			Integer seq_no = (Integer) model.getValueAt(x, 4);
			String entity_id = (String) model.getValueAt(x, 6);
			BigDecimal insurance_amt = (BigDecimal) model.getValueAt(x, 9);
			//Integer term = (Integer) model.getValueAt(x, 10);
			
			if(isSelected){
				
				listRecID.add(rec_id);
				listUnitID.add(String.format("'%s'", unit_id));
				listPBL.add(String.format("'%s'", pbl_id));
				listSeqNo.add(seq_no);
				listEntityID.add(String.format("'%s'", entity_id));
				listInsuranceAmt.add(insurance_amt);
				//listTerm.add(term);
				
			}
		}
		
		String rec_id = listRecID.toString().replaceAll("\\[|\\]", "");
		String unit_id = listUnitID.toString().replaceAll("\\[|\\]", "");
		String pbl_id = listPBL.toString().replaceAll("\\[|\\]", "");
		String seq_no = listSeqNo.toString().replaceAll("\\[|\\]", "");
		String entity_id = listEntityID.toString().replaceAll("\\[|\\]", "");
		String insurance_amt = listInsuranceAmt.toString().replaceAll("\\[|\\]", "");
		//String term = listTerm.toString().replaceAll("\\[|\\]", "");
		
		/*System.out.printf("Display ArrayList for unit ID: %s%n", listUnitID.toString());
		System.out.printf("Display unit_id: %s%n", unit_id);*/
		
		pgSelect db = new pgSelect();
		String sql = "SELECT sp_post_fire_approved_enrollment(ARRAY["+rec_id+"]::INT[], ARRAY["+unit_id+"]::VARCHAR[], \n"+
				     "ARRAY["+pbl_id+"]::VARCHAR[], ARRAY["+seq_no+"]::INT[], ARRAY["+entity_id+"]::VARCHAR[], \n"+
					 "ARRAY["+insurance_amt+"]::NUMERIC[], '"+co_id+"', '"+insurance_co_id2+"', \n"+
					 "'"+insurance_broker_id+"', '"+policy_no+"', '"+invoice_no+"', '"+date_from+"', '"+date_to+"' , \n"+
					 "'"+rplf_no+"', '"+UserInfo.EmployeeCode+"', '"+UserInfo.Branch+"')";
		db.select(sql);
		
		FncSystem.out("Display sql for posting of approved FI Enrollment", sql);
		
	}
	
	//POSTING OF APPROVAL OF IF RENEWED FI ENROLLED ACCOUNTS
	public static void postApprovedFIRenew(String co_id, String insurance_co_id, String batch_no,String policy_no, String invoice_no,
		String rplf_no, Date date_from, Date date_to ,modelFI_ForPmtInsurance model){
		
		ArrayList<Integer> listRecID = new ArrayList<Integer>();
		ArrayList<String> listUnitID = new ArrayList<String>();
		ArrayList<String> listPBL = new ArrayList<String>();
		ArrayList<Integer> listSeqNo = new ArrayList<Integer>();
		ArrayList<String> listEntityID = new ArrayList<String>();
		ArrayList<BigDecimal> listInsuranceAmt = new ArrayList<BigDecimal>();
		//ArrayList<Integer> listTerm = new ArrayList<Integer>();
		
		for(int x = 0; x<model.getRowCount(); x++){
			Boolean isSelected = (Boolean) model.getValueAt(x, 0);
			if(isSelected){
				Integer rec_id = (Integer) model.getValueAt(x, 1);
				String unit_id = (String) model.getValueAt(x, 2);
				String pbl_id = (String) model.getValueAt(x, 3);
				Integer seq_no = (Integer) model.getValueAt(x, 4);
				String entity_id = (String) model.getValueAt(x, 6);
				BigDecimal insurance_amt = (BigDecimal) model.getValueAt(x, 10);
				//Integer term = (Integer) model.getValueAt(x, 11);
				
				listRecID.add(rec_id);
				listUnitID.add(String.format("'%s'", unit_id));
				listPBL.add(String.format("'%s'", pbl_id));
				listSeqNo.add(seq_no);
				listEntityID.add(String.format("'%s'", entity_id));
				listInsuranceAmt.add(insurance_amt);
				//listTerm.add(term);
				
			}
		}
		
		String rec_id = listRecID.toString().replaceAll("\\[|\\]", "");
		String unit_id = listUnitID.toString().replaceAll("\\[|\\]", "");
		String pbl_id = listPBL.toString().replaceAll("\\[|\\]", "");
		String seq_no = listSeqNo.toString().replaceAll("\\[|\\]", "");
		String entity_id = listEntityID.toString().replaceAll("\\[|\\]", "");
		String insurance_amt = listInsuranceAmt.toString().replaceAll("\\[|\\]", "");
		//String term = listTerm.toString().replaceAll("\\[|\\]", "");
		
		pgSelect db = new pgSelect();
		
		String sql = "SELECT sp_post_fire_approved_renewal(ARRAY["+rec_id+"]::INT[], ARRAY["+unit_id+"]::VARCHAR[], ARRAY["+pbl_id+"]::VARCHAR[], \n"+
					 "ARRAY["+seq_no+"]::INT[], ARRAY["+entity_id+"]::VARCHAR[], ARRAY["+insurance_amt+"]::NUMERIC[], '"+co_id+"','"+insurance_co_id+"', \n"+
					 "'"+policy_no+"', '"+invoice_no+"', '"+date_from+"'::DATE::TIMESTAMP, '"+date_to+"'::DATE::TIMESTAMP, \n"+
					 "'"+batch_no+"', '"+rplf_no+"', '"+UserInfo.Branch+"', '"+UserInfo.EmployeeCode+"')";
		db.select(sql);
		
		FncSystem.out("Display sql for posting of approved FI Renewal", sql);
	}
	
	//POSTING OF APPROVED MRI ACCOUNTS FOR TRANSFER
	public static void postApprovedFITransfer(String co_id, String insurance_co_id1,String insurance_co_id2, String insurance_broker, 
		String invoice_no, String policy_no, Date date_approved,String batch_no ,String rplf_no ,modelFire_ForIssuancePolicy model){
		
		pgUpdate db = new pgUpdate();
		//XXX MAKE THIS A FUNCTION
		int y = 0;
		for(int x = 0; x<model.getRowCount(); x++){
			Boolean isSelected = (Boolean) model.getValueAt(x, 0);
			
			if(isSelected){
				Integer rec_id = (Integer) model.getValueAt(x, 1);
				String unit_id = (String) model.getValueAt(x, 2);
				String pbl_id = (String) model.getValueAt(x, 3);
				Integer seq_no = (Integer) model.getValueAt(x, 4);
				
				String entity_id = (String) model.getValueAt(x, 6);
				BigDecimal insurance_amt = (BigDecimal) model.getValueAt(x, 9);
				BigDecimal premium = (BigDecimal) model.getValueAt(x, 10);
				Integer term = (Integer) model.getValueAt(x, 11);
				
				String sqlRfFIHD = "UPDATE rf_fire_header\n" + 
							 	   "SET auto_terminate_date = '"+date_approved+"', termination_batch = '"+newQualifiedTerminationBatchNo()+"', \n"+
							 	   "auto_terminate_reason = 'Accounts Transferred', refund = 0.00\n" + 
							 	   "WHERE rec_id = "+rec_id+"\n" + 
							 	   "AND entity_id = '"+entity_id+"'\n" + 
							 	   "AND pbl_id = '"+pbl_id+"'\n" + 
							 	   "AND seq_no = "+seq_no+"\n";
				db.executeUpdate(sqlRfFIHD, true);
				
				//XXX CHECK VALUE OF LGT, FST, EVAT AND DOC STAMPS
				String sqlRfFIHD2 = "INSERT INTO rf_fire_header(\n" + 
									"rec_id, entity_id, proj_id, pbl_id, seq_no, enrollment_batch, \n" + 
									"term, amt_insured, date_from, date_to, fire_lightning, full_earthquake, \n" + 
									"typhoon, flood, ext_cover, rsmd, doc_stamps, evat, fire_srvc_tax, \n" + 
									"loc_govt_tax, premium, date_enrolled, date_approved, policy_no, \n" + 
									"invoice_no, insurance_comp, insurance_line, date_terminated, \n" + 
									"renewal_batch, termination_batch, remarks, refund, transfer_unit, \n" + 
									"status_id, auto_terminate_date, auto_terminate_reason, is_under_company)\n" + 
									"VALUES ((SELECT COALESCE(max(rec_id),0) + 1 FROM rf_fire_header), '"+entity_id+"',\n"+
									"(SELECT proj_id FROM mf_unit_info WHERE unit_id = '"+unit_id+"'), '"+pbl_id+"', \n"+
									""+seq_no+", NULL, NULL, "+insurance_amt+", '"+date_approved+"', \n"+
									"(SELECT '"+date_approved+"'::TIMESTAMP + INTERVAL '"+term+" Months'), ("+insurance_amt+" * 0.00038), \n"+
									"("+insurance_amt+" * 0.001), ("+insurance_amt+" * 0.00025), ("+insurance_amt+" * 0.00025), \n"+
									"("+insurance_amt+" * 0.00002375), ("+insurance_amt+" * 0.00002375), ("+insurance_amt+" *0.000241), \n"+
									"("+insurance_amt+" * 0.0002313), ("+insurance_amt+" * 0.00002855), ("+insurance_amt+" * 0.00000212),\n" + 
									""+premium+", now(), '"+date_approved+"', '"+policy_no+"', '"+invoice_no+"', '"+insurance_co_id2+"', \n"+
									"'"+insurance_broker+"', NULL, '"+batch_no+"', NULL, NULL, NULL, NULL, 'A', NULL, NULL, NULL);\n";
				db.executeUpdate(sqlRfFIHD2, true);
				
				String sqlRfReqDL = "INSERT INTO rf_request_detail(\n" + 
									"co_id, busunit_id, rplf_no, line_no, ref_doc_id, ref_doc_no, \n" + 
									"ref_doc_date, with_budget, part_desc, acct_id, remarks, amount, \n" + 
									"entity_id, entity_type_id, project_id, sub_projectid, div_id, \n" + 
									"dept_id, sect_id, inter_busunit_id, inter_co_id, is_vatproject, \n" + 
									"is_vatentity, is_taxpaidbyco, is_gross, wtax_id, wtax_rate, wtax_amt, \n" + 
									"vat_acct_id, vat_rate, vat_amt, exp_amt, pv_amt, sd_no, item_id, \n" + 
									"asset_no, old_acct_id, status_id, created_by, date_created, edited_by, date_edited)\n" + 
									"VALUES (get_company_id('"+unit_id+"'), get_company_id('"+unit_id+"'), \n"+
									"'"+rplf_no+"', "+y+"+1, '09', '"+batch_no+"', NULL, FALSE, 'Payment for FI', \n"+
									"'03-02-08-000', null, "+premium+", '"+entity_id+"', '36', \n"+
									"(SELECT proj_id FROM mf_unit_info WHERE unit_id = '"+unit_id+"'), \n"+
									"(SELECT sub_proj_id FROM mf_unit_info WHERE unit_id = '"+unit_id+"'), \n"+
									"NULL, NULL, NULL, NULL, NULL, FALSE, FALSE, FALSE, FALSE, '18', 0.00, 0.00, \n" + 
									"NULL, 0.00, 0.00, "+premium+", "+premium+", NULL, NULL, NULL, NULL, 'A', '"+UserInfo.EmployeeCode+"', now(), NULL, NULL);\n";
				db.executeUpdate(sqlRfReqDL, true);
				
			}
		}
		
		String sqlRfReqHD = "INSERT INTO rf_request_header(\n" + 
							"co_id, busunit_id, rplf_no, rplf_date, date_needed, date_liq_ext, \n" + 
							"rplf_type_id, entity_id1, entity_id2, entity_type_id, sd_no, \n" + 
							"doc_id, proc_id, branch_id, justification, remarks, status_id, \n" + 
							"created_by, date_created, edited_by, date_edited, pay_type_id)\n" + 
							"VALUES ('"+co_id+"', '"+co_id+"', '"+rplf_no+"', \n"+
							"now(), now(), NULL, '01', '"+insurance_co_id2+"', '"+insurance_co_id2+"', \n"+
							"'36', NULL, '09', '1', '"+UserInfo.Branch+"', NULL, 'Payment for FI', 'A', \n" + 
							"'"+UserInfo.EmployeeCode+"', now(), NULL, NULL, NULL);\n";
		db.executeUpdate(sqlRfReqHD, true);
		db.commit();
	}

}
