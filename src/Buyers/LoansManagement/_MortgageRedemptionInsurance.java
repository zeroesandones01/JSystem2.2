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
import tablemodel.modelApproveQualifiedMRI;
import tablemodel.modelMRI_ForPaymentInsurance;
import tablemodel.modelQualifiedMRIEnroll;
import tablemodel.modelQualifiedMRIRenew;
import tablemodel.modelQualifiedMRITerminate;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.UserInfo;

/**
 * @author John Lester Fatallo
 */
public class _MortgageRedemptionInsurance {

	public _MortgageRedemptionInsurance() {
		// TODO Auto-generated constructor stub
	}
	
	//SQL FOR LOOKUP OF PROJECT
	public static String sqlProject(){
		return "SELECT TRIM(proj_id) AS \"ID\", proj_name AS \"Proj. Name\" \n" + 
			   "FROM mf_project \n" + 
			   "WHERE status_id = 'A' \n" + 
			   "ORDER BY proj_id;";
	}
	
	//SQL FOR LOOKUP OF INSURANCE COMPANY
	public static String sqlInsuranceCompany(){
		return "SELECT TRIM(entity_id) AS \"ID\", \n" + 
			   "get_client_name(entity_id) AS \"Insurance Company\"\n" + 
			   "FROM mf_insurance_company \n" + 
			   "WHERE status_id = 'A' \n" + 
			   "ORDER BY \"Insurance Company\";";
	}
	
	//SQL FOR LOOKUP OF COMPANY
	public static String sqlCompany(){
		return "SELECT \n" + 
			   "trim(co_id) as \"ID\",\n" + 
			   "trim(company_name) as \"Company Name\"\n" + 
			   "from mf_company";
	}
	
	//GENERATES A NEW BATCH NO
	public static String newBatchNo(){
		String batch_no = "";
		pgSelect db = new pgSelect();
		
		String sql = "SELECT TRIM(to_char(coalesce(MAX(batch_no),'0')::INT + 1,'000000')) FROM rf_mri_header";
		db.select(sql);
		//FncSystem.out("Display New Bacth No", sql);
		
		if(db.isNotNull()){
			batch_no = (String) db.getResult()[0][0];
		}
		return batch_no;
	}
	
	//GENERATES A NEW REFERENCE NO
	public static String newReferenceNo(){
		String ref_no = "";
		pgSelect db = new pgSelect();
		
		String sql = "select get_new_mri_ref_no()";
		db.select(sql);
		//FncSystem.out("Display New Referece No", sql);
		
		if(db.isNotNull()){
			ref_no = (String) db.getResult()[0][0];
		}
		return ref_no;
	}
	
	//GENERATE A NEW RPLF NO
	public static String newApprovedRPLFNo(String co_id){
		String rpfl_no = "";
		
		pgSelect db = new pgSelect();
		//**MODIFIED BY MONIQUE DTD 11-24-2022
		String sql = "SELECT * from fn_get_rplf_no ('"+co_id+"');";
		db.select(sql);
		//FncSystem.out("Display New Approved RPLF No", sql);
		
		if(db.isNotNull()){
			rpfl_no = (String) db.getResult()[0][0];
		}
		return rpfl_no;
	}
	
	//GENERATES A NEW BATCH TERMINATION NO
	public static String newTerminationNo(){
		String termination_no = "";
		
		pgSelect db = new pgSelect();
		
		String sql = "SELECT TRIM(to_char(COALESCE(max(termination_no),'0')::INT + 1,'000000')) FROM rf_mri_details\n";
		db.select(sql);
		//FncSystem.out("DIsplay New Termination No", sql);
		
		if(db.isNotNull()){
			termination_no = (String) db.getResult()[0][0];
		}
		return termination_no;
	}
	
	//SQL FOR ENROLLMENT BATCH NO
	public static String sqlEnrollmentBatchNo(){
		return "SELECT DISTINCT a.batch_no as \"Batch No\" \n" + 
			   "FROM rf_mri_header a\n" + 
			   "WHERE a.batch_no IS NOT NULL\n" + 
			   "AND a.status_id = 'A'\n" + 
			   "AND NOT EXISTS (SELECT *\n" + 
			   "			    FROM rf_mri_details\n" + 
			   "				WHERE applicant_no = a.applicant_no);";
	}
	
	//SQL FOR REFERENCE NO
	public static String sqlRenewalBatchNo(){
		
		return 
			
				/*"SELECT DISTINCT TRIM(b.reference_no) as \"Reference_No\" \n" + 
			   "FROM rf_mri_header a\n" + 
			   "LEFT JOIN rf_mri_details b on b.applicant_no = a.applicant_no\n" + 
			   "WHERE b.date_terminated IS NULL\n" + 
			   "AND b.termination_no IS NULL \n" +
			   "AND b.reference_no IS NOT NULL \n" + 
			   "ORDER BY Reference_No DESC;";
			   //"AND b.date_approved IS NULL"; //XXX UNCOMMENT WHEN DONE QUALIFYING ACCOUNTS FOR RENEWAL*/ 
				
				// MODIFIED BY MONIQUE DTD 2022-11-18
				"SELECT TRIM(b.reference_no) as \"Reference_No\" \n" +
				"FROM rf_mri_header a \n" + 
				"LEFT JOIN rf_mri_details b on b.applicant_no = a.applicant_no \n" +
				"WHERE b.date_terminated IS NULL \n" +
				"AND b.termination_no IS NULL \n" +
				"AND b.reference_no IS NOT NULL \n" +
				"GROUP BY  b.reference_no \n" +
				"ORDER BY b.reference_no DESC;";
	}
	
	//SQL FOR LOOKUP OF TERMINATION NO
	public static String sqlTerminationBatchNo(){
		
		return "SELECT DISTINCT b.termination_no as \"Termination No\"\n" + 
			   "FROM rf_mri_header a\n" + 
			   "LEFT JOIN rf_mri_details b on b.applicant_no = a.applicant_no\n" + 
			   "WHERE b.date_terminated IS NOT NULL\n" + 
			   "AND b.termination_no IS NOT NULL;";
	}
	
	//SQL FOR LOOKUP OF QUALIFIED APPROVAL ENROLLMENT
	public static String sqlApproveQualifiedEnrollment(){
		
		return "SELECT DISTINCT TRIM(b.reference_no) as \"Reference_No\" \n" + 
			   "FROM rf_mri_header a\n" + 
			   "LEFT JOIN rf_mri_details b on b.applicant_no = a.applicant_no\n" + 
			   "WHERE b.date_terminated IS NULL\n" + 
			   "AND b.termination_no IS NULL \n" + 
			   "AND b.date_approved IS NULL \n" + 
			   "AND b.reference_no IS NOT NULL \n" + 
			   "ORDER BY TRIM(b.reference_no) DESC;";
		
	}
	
	//SQL FOR LOOKUP OF REFERENCE NO OF APPROVED MRI ACCOUNTS
	public static String sqlRefenceNo_ApprovedMRI(){
		
		return "SELECT DISTINCT TRIM(b.reference_no) as \"Reference_No\" \n" + 
			   "FROM rf_mri_header a\n" + 
			   "LEFT JOIN rf_mri_details b on b.applicant_no = a.applicant_no\n" + 
			   "WHERE b.date_terminated IS NULL\n" + 
			   "AND b.termination_no IS NULL \n" + 
			   "AND b.date_approved IS NULL \n" + 
			   "AND b.reference_no IS NOT NULL \n" + 
			   "ORDER BY Reference_No DESC;";
	}
	
	//SQL FOR LOOKUP OF RPLF NO
	public static String sqlRPLFNo(){
		
		return "SELECT DISTINCT ON (rplf_no) rplf_no as \"RPLF No\" \n" + 
				"FROM rf_mri_details\n" + 
				"WHERE date_approved IS NOT NULL\n" + 
				"AND date_terminated IS NULL\n" + 
				"AND termination_no IS NULL \n" + 
				"ORDER BY rplf_no, date_approved;";
	}
	
	//SQL FOR LOOKUP OF INVOICE NO
	public static String sqlInvoiceNo(){
		
		return "SELECT DISTINCT ON (invoice_no) invoice_no as \"Invoice No\" \n" + 
			   "FROM rf_mri_details\n" + 
			   "WHERE date_approved IS NOT NULL\n" + 
			   "AND date_terminated IS NULL\n" + 
			   "AND termination_no IS NULL \n" + 
			   "ORDER BY invoice_no, date_approved;";
		
	}
	
	//CHECKS IF THE ARE SELECTED ACCOUNTS TO QUALIFY
	public static Integer getSelectedQualifiedMRIEnroll(modelQualifiedMRIEnroll model){
		Integer count = 0;
		
		for(int x = 0; x<model.getRowCount(); x++){
			Boolean isSelected = (Boolean) model.getValueAt(x, 0);
			if(isSelected){
				count ++;
			}
		}
		return count;
	}
	
	//CHECKS IF THERE ARE SELECTED ACCOUNTS TO QUALIFY FOR RENEWAL
	public static Integer getSelectedQualifiedMRIRenew(modelQualifiedMRIRenew model){
		Integer count = 0;
		
		for(int x= 0; x<model.getRowCount(); x++){
			Boolean isSelected = (Boolean) model.getValueAt(x, 0);
			if(isSelected){
				count ++;
			}
		}
		return count;
	}
	
	//CHECKS IF THERE ARE SELECTED QUALIFIED ACCOUNTS TO BE APPROVED
	public static Integer getSelectedApproveQualified(modelApproveQualifiedMRI model){
		Integer count = 0;
		
		for(int x= 0; x<model.getRowCount(); x++){
			Boolean isSelected = (Boolean) model.getValueAt(x, 0);
			if(isSelected){
				count ++;
			}
		}
		
		return count;
	}
	
	//CHECKS IF THERE ARE SELECTED ACCOUNTS TO QUALIFY FOR TERMINATION
	public static Integer getSelectedQualifiedMRITerminate(modelQualifiedMRITerminate model){
		Integer count = 0;
		for(int x = 0; x < model.getRowCount(); x++){
			Boolean isSelected = (Boolean) model.getValueAt(x, 0);
			if(isSelected){
				count ++;
			}
		}
		return count;
	}
	
	//CHECKS FOR THE REASON FOR TERMINATION OF ACCOUNTS
	public static String reasonForTermination(modelQualifiedMRITerminate model){
		String remarks = "";
		
		for(int x = 0; x<model.getRowCount(); x++){
			Boolean isSelected = (Boolean) model.getValueAt(x, 0);
			if(isSelected){
				remarks = (String) model.getValueAt(x, 18);
			}
		}
		
		return remarks;
	}
	
	//DISPLAYS THE QUALIFIED ACCOUNTS FOR ENROLLMENT
	public static void displayQualifyMRIEnrollment(String insurance_co,String proj_id ,modelQualifiedMRIEnroll model, JList rowHeader){
		if(model != null && rowHeader != null){
			FncTables.clearTable(model);
			
			DefaultListModel listModel = new DefaultListModel();
			rowHeader.setModel(listModel);
			
			pgSelect db = new pgSelect();
						 
			String sql = "SELECT * from sp_generate_mri_qualified_enrollment('"+proj_id+"');\n";
			
			db.select(sql);
			
			FncSystem.out("Display Accounts to Qualify for Enrollment", sql);
			
			if(db.isNotNull()){
				for(int x =0; x<db.getRowCount(); x++){
					model.addRow(db.getResult()[x]);
					listModel.addElement(model.getRowCount());
				}
			}
		}
	}
	
	//DISPLAYS THE QUALIFIED MRI ACCOUNT FOR RENEWAL
	public static void displayQualifyMRIRenewal(Date date_cut_off, String proj_id, String insurance_co_id, modelQualifiedMRIRenew model, JList rowHeader){
		if(model != null && rowHeader != null){
			FncTables.clearTable(model);
			
			DefaultListModel listModel = new DefaultListModel();
			rowHeader.setModel(listModel);
			
			pgSelect db = new pgSelect();
			
			//String sql = "SELECT * FROM sp_generate_mri_qualified_renewal('"+date_cut_off+"', '"+insurance_co_id+"', '"+proj_id+"')"; 
			//**MODIFIED BY MONIQUE DTD 11-24-2022
			String sql = "SELECT * FROM sp_generate_mri_qualified_renewal_v2('"+date_cut_off+"', '"+insurance_co_id+"', '"+proj_id+"')"; 
			
			db.select(sql);
			
			FncSystem.out("Display Qualified MRI for Renewal", sql);
			
			if(db.isNotNull()){
				for(int x= 0; x<db.getRowCount(); x++){
					model.addRow(db.getResult()[x]);
					listModel.addElement(model.getRowCount());
				}
			}
		}
	}
	
	//DISPLAYS THE QUALIFIED ACCOUNTS FOR TERMINATION
	public static void displayQualifyMRITermination(Date date_cut_off, String insurance_co_id, String proj_id, modelQualifiedMRITerminate model, JList rowHeader){
		if(model != null && rowHeader != null){
			FncTables.clearTable(model);
			
			DefaultListModel listModel = new DefaultListModel();
			rowHeader.setModel(listModel);
			
			pgSelect db = new pgSelect();
				
			//String sql = "SELECT * FROM sp_generate_mri_qualified_termination('"+insurance_co_id+"', '"+proj_id+"')"; **MODIFIED BY MONIQUE DTD 11-24-2022
			String sql = "SELECT * FROM sp_generate_mri_qualified_termination_v2('"+date_cut_off+"', '"+insurance_co_id+"', '"+proj_id+"')";
			
			db.select(sql);
			
			FncSystem.out("Display Qualified MRI For Termination", sql);
			
			if(db.isNotNull()){
				for(int x= 0; x<db.getRowCount(); x++){
					model.addRow(db.getResult()[x]);
					listModel.addElement(model.getRowCount());
				}
			}
		}
	}
	//DISPLAYS QUALIFIED TERMINATED ACCOUNTS
	public static void displayQualifiedTerminated_BatchNo(String termination_no, modelQualifiedMRITerminate model, JList rowHeader){
		if(model != null && rowHeader != null){
			FncTables.clearTable(model);
			
			DefaultListModel listModel = new DefaultListModel();
			rowHeader.setModel(listModel);
			
			pgSelect db = new pgSelect();
			
			/*String sql = "SELECT TRUE, a.applicant_no, a.unit_id, a.pbl_id, a.seq_no,\n" + 
						 "c.phase, c.block, c.lot, a.entity_id, get_client_name(a.entity_id), \n" + 
						 "get_ob(a.entity_id, a.proj_id, a.pbl_id, a.seq_no), b.amt_insured, \n" + 
						 "b.premium, b.term, b.insurance_line, get_client_name(b.insurance_line),\n" + 
						 "d.byrstatus_id, get_buyer_status(d.byrstatus_id), b.remarks, b.premium_refund\n" + 
						 "FROM rf_mri_header a\n" + 
						 "LEFT JOIN rf_mri_details b on b.applicant_no = a.applicant_no\n" + 
						 "LEFT JOIN mf_unit_info c on c.proj_id = a.proj_id AND c.pbl_id = a.pbl_id\n" + 
						 "INNER JOIN (SELECT * FROM get_fullsettled_accounts()\n" + 
						 "	    UNION\n" + 
						 "	    SELECT * FROM get_cancelled_accounts()\n" + 
						 "	    UNION\n" + 
						 "	    SELECT * FROM rf_buyer_status WHERE byrstatus_id = '09') d on d.entity_id = a.entity_id AND d.proj_id = a.proj_id AND d.pbl_id = a.pbl_id AND d.seq_no = a.seq_no\n" + 
						 "WHERE b.termination_no = '"+termination_no+"';";*/
			
			/*String sql = "SELECT TRUE, b.rec_id ,a.applicant_no, a.unit_id, a.pbl_id, a.seq_no,\n" + 
						"c.phase, c.block, c.lot, a.entity_id, get_client_name(a.entity_id), \n" + 
						"get_ob(a.entity_id, a.proj_id, a.pbl_id, a.seq_no), b.amt_insured, \n" + 
						"b.premium, b.term, b.insurance_line, get_client_name(b.insurance_line),\n" + 
						"d.byrstatus_id, get_buyer_status(d.byrstatus_id), b.remarks, b.premium_refund, \n" + 
						"b.date_to, b.invoice_no, '', b.date_from, b.date_to\n" + 
						"FROM rf_mri_header a\n" + 
						"LEFT JOIN rf_mri_details b on b.applicant_no = a.applicant_no\n" + 
						"LEFT JOIN mf_unit_info c on c.proj_id = a.proj_id AND c.pbl_id = a.pbl_id\n" + 
						"INNER JOIN (SELECT * FROM get_fullsettled_accounts()\n" + 
						"	    UNION\n" + 
						"	    SELECT * FROM get_cancelled_accounts()\n" + 
						"	    UNION\n" + 
						"	    SELECT * FROM rf_buyer_status WHERE byrstatus_id = '09') d on d.entity_id = a.entity_id AND d.proj_id = a.proj_id AND d.pbl_id = a.pbl_id AND d.seq_no = a.seq_no\n" + 
						"WHERE b.termination_no = '"+termination_no+"';";*/
			
		//MODIFIED BY MONIQUE DTD 11-24-2022	
		String sql = 	"SELECT TRUE, b.rec_id ,a.applicant_no, c.unit_id, a.pbl_id, a.seq_no, \n" +
						"c.phase, c.block, c.lot, a.entity_id, get_client_name(a.entity_id), \n" +
						"get_ob(a.entity_id, a.proj_id, a.pbl_id, a.seq_no), b.amt_insured, \n" +
						"b.premium, b.term, b.insurance_line, get_client_name(b.insurance_line), \n" +
						"get_current_status_id(a.entity_id, a.proj_id, a.pbl_id, a.seq_no) AS buyer_status_id, b.remarks, '', b.premium_refund, \n" +
						"b.date_to, b.invoice_no, '', b.date_from, b.date_to \n" +
						"FROM rf_mri_header a \n" +
						"LEFT JOIN rf_mri_details b on b.applicant_no = a.applicant_no and coalesce(b.proj_server, '') = coalesce(a.proj_server, '') and coalesce(b.server_id, '') = coalesce(a.server_id, '') \n" +
						"LEFT JOIN mf_unit_info c on c.proj_id = a.proj_id AND c.pbl_id = a.pbl_id \n" +
						"LEFT JOIN rf_sold_unit d on a.entity_id = d.entity_id and a.proj_id = d.projcode and a.pbl_id = d.pbl_id and a.seq_no = d.seq_no \n" +
						"WHERE TRIM(b.termination_no) = '"+termination_no+"' \n" +
						"ORDER BY get_client_name(a.entity_id);"; 
			
			db.select(sql);
			
			FncSystem.out("Display sql for Terminated Accounts Batch No", sql);
			
			if(db.isNotNull()){
				for(int x= 0; x<db.getRowCount(); x++){
					model.addRow(db.getResult()[x]);
					listModel.addElement(model.getRowCount());
				}
			}
		}
	}
	
	//DISPLAYS QUALIFIED ACCOUNTS FOR ENROLLMENT BASED ON BATCH NO
	public static void displayQualifiedEnrollment_BatchNo(String reference_no, modelQualifiedMRIEnroll model, JList rowHeader){
		if(model != null && rowHeader != null){
			FncTables.clearTable(model);
			
			DefaultListModel listModel = new DefaultListModel();
			rowHeader.setModel(listModel);
			
			pgSelect db = new pgSelect();
			
			/*String sql = "SELECT TRUE, a.unit_id, a.pbl_id, a.seq_no, get_unit_info(a.unit_id),\n" + 
						 "a.entity_id, get_client_name(a.entity_id), b.net_tcp, b.part_id, \n" + 
						 "mri_doc_sub_date(a.unit_id, a.seq_no), b.insurance_comp, get_client_name(b.insurance_comp) \n" +
						 //"get_client_name(mri_doc_insco(a.entity_id, a.proj_id, a.pbl_id, a.seq_no))\n" + 
						 "FROM rf_mri_header a\n" + 
						 "LEFT JOIN rf_mri_details b on b.applicant_no = a.applicant_no\n" + 
						 "WHERE TRIM(b.reference_no) = '"+reference_no+"';";*/
			
			
			String sql = "SELECT TRUE, a.unit_id, a.pbl_id, a.seq_no, c.phase, c.block, \n" + 
						 "c.lot, a.entity_id, get_client_name(a.entity_id), b.net_tcp, b.part_id, \n" + 
					     "mri_doc_sub_date(a.unit_id, a.seq_no), b.insurance_comp, get_client_name(b.insurance_comp), \n" + 
					     "b.amt_insured, b.amt_insured, \n" + 
					     "(CASE WHEN b.term = 6 then '6 mos.' ELSE '1 year' END), b.premium ,b.date_to, '',\n" + 
					     "b.invoice_no, e.class_name\n" + 
					     "FROM rf_mri_header a\n" + 
					     "LEFT JOIN rf_mri_details b on b.applicant_no = a.applicant_no\n" + 
					     "left join mf_unit_info c on c.proj_id = a.proj_id and c.pbl_id = a.pbl_id\n" + 
					     "LEFT JOIN rf_corp_type d on d.entity_id = a.entity_id \n" + 
					     "LEFT JOIN mf_business_class e on e.class_id = d.business_class_id\n" + 
					     "WHERE TRIM(b.reference_no) = '"+reference_no+"' \n"+
					     "ORDER BY e.class_name";
			db.select(sql);
			
			FncSystem.out("Display sql for Qualified Enrollment Batch No", sql);
			
			if(db.isNotNull()){
				for(int x = 0; x<db.getRowCount(); x++){
					model.addRow(db.getResult()[x]);
					listModel.addElement(model.getRowCount());
				}
			}
		}
	}
	
	//DISPLAY QUALIFIED ACCOUNTS FOR ENROLLMENT
	public static void displayApproveQualifiedMRI(String insurance_co_id , modelApproveQualifiedMRI model, JList rowHeader){
		if(model != null && rowHeader != null){
			FncTables.clearTable(model);
			
			DefaultListModel listModel = new DefaultListModel();
			rowHeader.setModel(listModel);
			
			pgSelect db = new pgSelect();
			
			/*String sql = "SELECT FALSE ,a.applicant_no, a.proj_id, a.unit_id, a.pbl_id, a.seq_no,\n" + 
						 "get_unit_info(a.unit_id), a.entity_id, get_client_name(a.entity_id), \n"+
						 "b.insurance_comp, get_client_name(b.insurance_comp), \n"+
						 "b.net_tcp, b.amt_insured, b.premium, b.term, b.part_id, TRIM(b.reference_no), \n"+
						 "b.invoice_no, b.policy_no \n" + 
						 "FROM rf_mri_header a\n" + 
						 "LEFT JOIN rf_mri_details b on b.applicant_no = a.applicant_no\n" + 
						 "WHERE b.insurance_comp = '"+insurance_co_id+"' \n" +
						 "AND date_approved IS NULL;";*/
			
			String sql = "SELECT * FROM sp_generate_mri_issuance_policy('"+insurance_co_id+"')";
			
			db.select(sql);
			
			FncSystem.out("Display qualified accounts for approval", sql);
			
			if(db.isNotNull()){
				for(int x = 0; x<db.getRowCount(); x++){
					model.addRow(db.getResult()[x]);
					//model.addRow(db.getResult()[x]);
					listModel.addElement(model.getRowCount());
				}
			}
		}
	}
	
	//DISPLAYS RENEWED ACCOUNTS BASED ON BATCH NO
	public static void displayQualifiedRenewal_BatchNo(String reference_no, String proj_id, modelQualifiedMRIRenew model, JList rowHeader){
		if(model != null && rowHeader != null){
			FncTables.clearTable(model);
			
			DefaultListModel listModel = new DefaultListModel();
			rowHeader.setModel(listModel);
			
			pgSelect db = new pgSelect();
			
			String sql = "SELECT TRUE, a.applicant_no, a.unit_id, a.pbl_id, a.seq_no, TRIM(c.phase), \n" + 
						 "TRIM(c.block), TRIM(c.lot), a.entity_id, get_client_name(a.entity_id), \n" + 
						 "get_ob(a.entity_id, a.proj_id, a.pbl_id, a.seq_no), b.amt_insured ,b.premium, \n" + 
						 "b.term, get_client_age(a.entity_id, current_date), e.date_of_birth, \n" + 
						 "b.part_id, b.insurance_line, get_client_name(b.insurance_line), \n" + 
						 "b.date_to, d.byrstatus_id, get_buyer_status(d.byrstatus_id), b.amt_insured, \n" + 
						 "b.date_to, '', b.invoice_no, g.class_name\n" + 
						 "FROM rf_mri_header a \n" + 
						 "LEFT JOIN rf_mri_details b on b.applicant_no = a.applicant_no and coalesce(b.proj_server, '') = coalesce(a.proj_server, '') and coalesce(b.server_id, '') = coalesce(a.server_id, '')\n" + 
						 "LEFT JOIN mf_unit_info c on c.proj_id = a.proj_id AND c.pbl_id = a.pbl_id\n" + 
						 "LEFT JOIN get_active_accounts() d on d.entity_id = a.entity_id AND d.proj_id = a.proj_id AND d.pbl_id = a.pbl_id AND d.seq_no = a.seq_no\n" + 
						 "LEFT JOIN rf_entity e on e.entity_id = a.entity_id\n" + 
						 "LEFT JOIN rf_corp_type f on f.entity_id = a.entity_id\n" + 
						 "LEFT JOIN mf_business_class g on g.class_id = f.business_class_id\n" + 
						 "WHERE b.reference_no = '"+reference_no+"' AND a.proj_id = '"+proj_id+"';";
			db.select(sql);
			FncSystem.out("Display sql For Retrieval of Posted Qualified Enrollment", sql);
			
			if(db.isNotNull()){
				for(int x = 0; x<db.getRowCount(); x++){
					model.addRow(db.getResult()[x]);
					listModel.addElement(model.getRowCount());
				}
			}
			
			
		}
	}
	
	//DISPLAYS APPROVED MRI ACCOUNTS BASE ON RPLF OR INVOICE NO
	public static void displayMRIApprovedAccounts(Integer search_by, String search_value, modelMRI_ForPaymentInsurance model ,JList rowHeader){
		if(model != null && rowHeader != null){
			FncTables.clearTable(model);
			
			DefaultListModel listModel = new DefaultListModel();
			rowHeader.setModel(listModel);
			
			pgSelect db = new pgSelect();
			
			String sql = "SELECT get_project_alias(a.proj_id), c.description, \n" + 
						 "get_client_name(a.entity_id), b.date_enrolled, b.date_approved, \n" + 
						 "b.policy_no, b.reference_no, TRIM(b.invoice_no), b.amt_insured, b.premium\n" + 
						 "FROM rf_mri_header a\n" + 
						 "LEFT JOIN rf_mri_details b on b.applicant_no = a.applicant_no\n" + 
						 "LEFT JOIN mf_unit_info c on c.proj_id = a.proj_id AND c.pbl_id = a.pbl_id \n" + 
						 "WHERE CASE WHEN "+search_by+" = 0 THEN b.invoice_no = '"+search_value+"' ELSE b.rplf_no = '"+search_value+"' END;";
			db.select(sql);
			
			FncSystem.out("Display sql for Approved MRI Accounts", sql);
			
			if(db.isNotNull()){
				for(int x = 0; x<db.getRowCount(); x++){
					model.addRow(db.getResult()[x]);
					listModel.addElement(model.getRowCount());
				}
			}
		}
	}
	
	public static void displayForPaymentInsuranceCompany(modelMRI_ForPaymentInsurance model, JList rowHeader){
		
		FncTables.clearTable(model);
		
		DefaultListModel listModel = new DefaultListModel();
		rowHeader.setModel(listModel);
		
		pgSelect db = new pgSelect();
		
		String sql = /*"select distinct on (a.invoice_no)\n" + 
					 "get_client_name(a.insurance_comp), \n" + 
					 "a.invoice_no, a.rplf_no\n" + 
					 "from rf_mri_details a\n" + 
					 "LEFT JOIN rf_request_header b on b.rplf_no = a.rplf_no\n" + 
					 "left join rf_pv_header c on c.pv_no = b.rplf_no\n" + 
					 "left join rf_cv_header d on d.cv_no = c.cv_no\n" + 
					 "where a.rplf_no is not null\n" + 
					 "and a.date_terminated is null\n" + 
					 "and d.date_paid is null;";*/
					
				//MODIFIED BY MONIQUE DTD 11-23-2022	
					"select \n" +
					"get_client_name(a.insurance_comp), \n" + 
					"a.invoice_no, a.rplf_no, TRIM(a.reference_no) \n" +
					"from rf_mri_details a \n" +
					"LEFT JOIN rf_request_header b on b.rplf_no = a.rplf_no \n" +
					"left join rf_pv_header c on c.pv_no = b.rplf_no and c.co_id = b.co_id \n" +
					"left join rf_cv_header d on d.cv_no = c.cv_no and d.co_id = b.co_id \n" +
					"where a.rplf_no is not null \n" +
					"and a.date_terminated is null \n" +
					"and d.date_paid is null \n" +
					"AND b.rplf_date IS NOT NULL \n" +
					"GROUP BY get_client_name(a.insurance_comp), a.invoice_no, a.rplf_no, b.rplf_date, TRIM(a.reference_no) \n" +
					"ORDER by b.rplf_date::DATE DESC;";
			
		
		db.select(sql);
		
		FncSystem.out("Display sql For Payment to Insurance Co", sql);
		
		if(db.isNotNull()){
			for(int x= 0; x<db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
				listModel.addElement(model.getRowCount());
			}
		}
	}
	
	//QUALIFY ACCOUNTS FOR ENROLLMENT
	public static void post_Qualify_Enrollment(String reference_no,String proj_id, modelQualifiedMRIEnroll model){
		
		ArrayList<String> listEntityID = new ArrayList<String>();
		ArrayList<String> listUnitID = new ArrayList<String>();
		ArrayList<Integer> listSeqNo = new ArrayList<Integer>();
		ArrayList<String> listInsuranceCo = new ArrayList<String>();
		
		for(int x = 0; x<model.getRowCount(); x++){
			Boolean isSelected = (Boolean) model.getValueAt(x, 0);
			
			if(isSelected){
				String unit_id = (String) model.getValueAt(x, 1);
				Integer seq_no = (Integer) model.getValueAt(x, 3);
				String entity_id = (String) model.getValueAt(x, 7);
				String insurance_co = (String) model.getValueAt(x, 12);
				
				listUnitID.add(String.format("'%s'", unit_id));
				listEntityID.add(String.format("'%s'", entity_id));
				listSeqNo.add(seq_no);
				listInsuranceCo.add(String.format("'%s'", insurance_co));
			}
		}
		
		String unit_id = listUnitID.toString().replaceAll("\\[|\\]", "");
		String seq_no = listSeqNo.toString().replaceAll("\\[|\\]", "");
		String entity_id = listEntityID.toString().replaceAll("\\[|\\]", "");
		String insurance_co = listInsuranceCo.toString().replaceAll("\\[|\\]", "");
		
		pgSelect db = new pgSelect();
		
		String sql = "SELECT sp_post_mri_qualified_enrollment(ARRAY["+entity_id+"]::VARCHAR[], ARRAY["+unit_id+"]::VARCHAR[], \n"+
					 "ARRAY["+seq_no+"]::INT[], ARRAY["+insurance_co+"]::VARCHAR[] ,'"+reference_no+"', '"+proj_id+"')";
		db.select(sql);
		
		FncSystem.out("Display Posting of Qualified for Enrollment", sql);
	}
	
	//QUALIFY ACCOUNTS FOR RENEWAL
	public static void post_Qualify_Renewal(String reference_no, String insurance_co, String proj_id, modelQualifiedMRIRenew model){
		
		ArrayList<String> listApplicantNo = new ArrayList<String>();
		ArrayList<String> listEntityID = new ArrayList<String>();
		ArrayList<String> listUnitID = new ArrayList<String>();
		ArrayList<Integer> listSeqNo = new ArrayList<Integer>();
		ArrayList<BigDecimal> listAmtInsured = new ArrayList<BigDecimal>();
		ArrayList<Integer> listInsuranceTerm = new ArrayList<Integer>();
		ArrayList<BigDecimal> listInsurancePremium = new ArrayList<BigDecimal>();
		
		for(int x = 0; x<model.getRowCount(); x++){
			Boolean isSelected = (Boolean) model.getValueAt(x, 0);
			
			if(isSelected){
				String applicant_no = (String) model.getValueAt(x, 1);
				String unit_id = (String) model.getValueAt(x, 2);
				Integer seq_no = (Integer) model.getValueAt(x, 4);
				String entity_id = (String) model.getValueAt(x, 8);
				BigDecimal insurance_amt = (BigDecimal) model.getValueAt(x, 11);
				BigDecimal insurance_premium = (BigDecimal) model.getValueAt(x, 12);
				Integer insurance_term = (Integer) model.getValueAt(x, 13);
				
				listApplicantNo.add(String.format("'%s'", applicant_no));
				listUnitID.add(String.format("'%s'", unit_id));
				listSeqNo.add(seq_no);
				listEntityID.add(String.format("'%s'", entity_id));
				listAmtInsured.add(insurance_amt);
				listInsurancePremium.add(insurance_premium);
				listInsuranceTerm.add(insurance_term);
				
			}
		}
		
		String applicant_no = listApplicantNo.toString().replaceAll("\\[|\\]", "");
		String unit_id = listUnitID.toString().replaceAll("\\[|\\]", "");
		String seq_no = listSeqNo.toString().replaceAll("\\[|\\]", "");
		String entity_id = listEntityID.toString().replaceAll("\\[|\\]", "");
		String insurance_amt = listAmtInsured.toString().replaceAll("\\[|\\]", "");
		String insurance_premium = listInsurancePremium.toString().replaceAll("\\[|\\]", "");
		String insurance_term = listInsuranceTerm.toString().replaceAll("\\[|\\]", "");
		
		pgSelect db = new pgSelect();
		
		String sql = "SELECT sp_post_mri_qualified_renewal(ARRAY["+entity_id+"]::VARCHAR[], ARRAY["+unit_id+"]::VARCHAR[], \n"+
					 "ARRAY["+seq_no+"]::INT[], ARRAY["+applicant_no+"]::VARCHAR[] ,ARRAY["+insurance_amt+"]::NUMERIC[] , \n"+
					 "ARRAY["+insurance_premium+"]::NUMERIC[], ARRAY["+insurance_term+"]::INT[],'"+reference_no+"', \n"+
					 "'"+insurance_co+"' ,'"+proj_id+"')";
		
		db.select(sql);
		
		FncSystem.out("Display Posting of Qualfied for renewal", sql);
	}
	
	//TERMINATE THE QUALIFIED MRI ACCOUNTS
	public static void postQualifiedMRITerminate(String termination_no, modelQualifiedMRITerminate model){
		pgUpdate db = new pgUpdate();
		
		for(int x = 0; x<model.getRowCount(); x++){
			Boolean isSelected = (Boolean) model.getValueAt(x, 0);
			if(isSelected){
				Integer rec_id = (Integer) model.getValueAt(x, 1);
				String applicant_no = (String) model.getValueAt(x, 2);
				BigDecimal refund = (BigDecimal) model.getValueAt(x, 20);
				String remarks = "";
				String buyer_status = (String) model.getValueAt(x, 18);
				
				if(buyer_status.equals("")){
					remarks = ((String) model.getValueAt(x, 19)).replace("'", "''");
				}
				
				if(buyer_status.equals("") == false){
					if(model.getValueAt(x, 19).equals("")){
						remarks = buyer_status;
					}
					if(model.getValueAt(x, 19).equals("") == false){
						remarks = model.getValueAt(x, 17) + "::" +model.getValueAt(x, 19).toString().replace("'", "'");
					}
				}
				
				String sql = "UPDATE rf_mri_details \n" + 
							 "SET date_terminated = now(), termination_no = '"+termination_no+"', \n" + 
							 "premium_refund = "+refund+", remarks = '"+remarks+"' \n" + 
							 "WHERE applicant_no = '"+applicant_no+"'\n" + 
							 //"AND rec_id in (SELECT rec_id FROM rf_mri_details where applicant_no = '"+applicant_no+"'\n" +
							 "AND rec_id = "+rec_id+"";
							 //"ORDER BY date_enrolled DESC, rec_id DESC LIMIT 1); \n"; 
				db.executeUpdate(sql, true);
			}
		}
		db.commit();
	}
	
	//Approved Qualified Accounts for Enrollment
	public static void post_Qualified_Approval(String co_id, /*String rplf_no,*/ String insurance_co, String insurance_line, modelApproveQualifiedMRI model){
			
		ArrayList<String> listEntityID = new ArrayList<String>();
		ArrayList<String> listProjID = new ArrayList<String>();
		ArrayList<String> listUnitID = new ArrayList<String>();
		ArrayList<Integer> listSeqNo = new ArrayList<Integer>();
		ArrayList<String> listApplicantNo = new ArrayList<String>();
		ArrayList<BigDecimal> listPremium = new ArrayList<BigDecimal>();
		ArrayList<String> listRefNo = new ArrayList<String>();
		ArrayList<Integer> listTerm = new ArrayList<Integer>();
		ArrayList<BigDecimal> listAmtInsured = new ArrayList<BigDecimal>();
		ArrayList<String> listPolicyNo	= new ArrayList<String>();
		ArrayList<String> listInvoiceNo = new ArrayList<String>();
		ArrayList<String> listDateApproved = new ArrayList<String>();
		
		for(int x= 0; x<model.getRowCount(); x++){
			Boolean isSelected = (Boolean) model.getValueAt(x, 0);
			
			if(isSelected){
				
				String applicant_no = (String) model.getValueAt(x, 1);
				String entity_id = (String) model.getValueAt(x, 8);
				String proj_id = (String) model.getValueAt(x, 2);
				String unit_id = (String) model.getValueAt(x, 4);
				Integer seq_no = (Integer) model.getValueAt(x, 6);
				
				BigDecimal amt_insured = (BigDecimal) model.getValueAt(x, 13);
				BigDecimal premium = (BigDecimal) model.getValueAt(x, 14);
				Integer term = (Integer) model.getValueAt(x, 15);
				String reference_no = (String) model.getValueAt(x, 19);
				String invoice_no = (String) model.getValueAt(x, 20);
				String policy_no = (String) model.getValueAt(x, 21);
				Date date_approved = (Date) model.getValueAt(x, 22);
				
				listUnitID.add(String.format("'%s'", unit_id));
				listEntityID.add(String.format("'%s'", entity_id));
				listSeqNo.add(seq_no);
				listProjID.add(String.format("'%s'", proj_id));
				listApplicantNo.add(String.format("'%s'", applicant_no));
				listPremium.add(premium);
				listRefNo.add(String.format("'%s'", reference_no));
				listTerm.add(term);
				listAmtInsured.add(amt_insured);
				listInvoiceNo.add(String.format("'%s'", invoice_no));
				listPolicyNo.add(String.format("'%s'", policy_no));
				listDateApproved.add(String.format("'%s'", date_approved.toString()));
				
			}
		}
		
		String unit_id = listUnitID.toString().replaceAll("\\[|\\]", "");
		String seq_no = listSeqNo.toString().replaceAll("\\[|\\]", "");
		String entity_id = listEntityID.toString().replaceAll("\\[|\\]", "");
		String proj_id = listProjID.toString().replaceAll("\\[|\\]", "");
		String applicant_no = listApplicantNo.toString().replaceAll("\\[|\\]", "");
		String premium = listPremium.toString().replaceAll("\\[|\\]", "");
		String reference_no = listRefNo.toString().replaceAll("\\[|\\]", "");
		String term = listTerm.toString().replaceAll("\\[|\\]", "");
		String amt_insured = listAmtInsured.toString().replaceAll("\\[|\\]", "");
		String invoice_no = listInvoiceNo.toString().replaceAll("\\[|\\]", "");
		String policy_no = listPolicyNo.toString().replaceAll("\\[|\\]", "");
		String date_approved = listDateApproved.toString().replaceAll("\\[|\\]", "");
		String rplf_no = ""; 
		
		pgSelect db = new pgSelect();
		
		String sql = "SELECT sp_approve_mri_qualified(ARRAY["+entity_id+"]::VARCHAR[], ARRAY["+proj_id+"]::VARCHAR[], ARRAY["+unit_id+"]::VARCHAR[], \n"+
				 	 "ARRAY["+seq_no+"]::INT[], ARRAY["+applicant_no+"]::VARCHAR[], ARRAY["+amt_insured+"]::NUMERIC[], ARRAY["+premium+"]::NUMERIC[], \n"+
				 	 "ARRAY["+term+"]::INT[], ARRAY["+reference_no+"]::VARCHAR[], ARRAY["+invoice_no+"]::VARCHAR[] , ARRAY["+policy_no+"]::VARCHAR[], \n"+
				 	 "ARRAY["+date_approved+"]::TIMESTAMP WITHOUT TIME ZONE[] ,'"+co_id+"', '"+rplf_no+"', '"+insurance_co+"', '"+insurance_line+"', \n"+
				 	 "'"+UserInfo.EmployeeCode+"', '"+UserInfo.Branch+"')";
		db.select(sql);
		
		FncSystem.out("Display Approval of qualified accounts", sql);
	}
}
