/**
 * 
 */
package Buyers.LoansManagement;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import Buyers.CreditandCollections._RealTimeDebit;
import Database.pgSelect;
import Database.pgUpdate;
import DateChooser._JDateChooser;
import tablemodel.modelPST_ForTCTAnnotation;
import tablemodel.modelPST_LoanFiled;
import tablemodel.modelPST_NOA_Expiring;
import tablemodel.modelPST_With_Annotated_TCT;
import tablemodel.modelPST_With_EPEB;
import tablemodel.modelPagibigStatusMonitoring_AccountQuery_HouseInspection;
import tablemodel.modelPagibigStatusMonitoring_AccountQuery_MSVS_StatusHistory;
import tablemodel.modelPagibigStatusMonitoring_AccountQuery_StatusHistory;
import tablemodel.modelPagibigStatusMonitoring_DeficientAccounts;
import tablemodel.modelPagibigStatusMonitoring_HouseInspection;
import tablemodel.modelPagibigStatusMonitoring_MSVSMonitoring;
import tablemodel.modelPagibigStatusMonitoring_QualifiedAccounts;
import tablemodel.modelNOATagging;
import tablemodel.modelNOATagging2;
import tablemodel.modelPST_DOA_Signed;
import tablemodel.modelPST_ForPostApproval;
import tablemodel.model_hdmf_FirstFiling;
import tablemodel.model_hdmf_NOASigning;
import tablemodel.model_hdmf_ci_tagging;
import tablemodel.model_hdmf_email;
import tablemodel.model_hdmf_loanReturned;
import tablemodel.model_hdmf_msvs_reverified;
import tablemodel.model_hdmf_noaextension;
import tablemodel.model_hdmf_postInspection;
import Functions.FncGlobal;
import Functions.FncReport;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup._JLookup;

/**
 * @author John Lester Fatallo
 */
public class _PagibigStatusMonitoring {

	public _PagibigStatusMonitoring() {
		// TODO Auto-generated constructor stub
	}
	
	public static String sqlCompany(){
		return "SELECT co_id as \"ID\", \n" + 
			   "company_name as \"Company Name\", \n" + 
			   "company_alias as \"Alias\" \n" + 
			   "FROM mf_company;";
	}
	
	public static String sqlProject(String co_id){
		return "SELECT \n" + 
			   "proj_id as \"ID\", \n" + 
			   "proj_name as \"Project\", \n" + 
			   "proj_alias as \"Alias\"\n" + 
			   "FROM mf_project\n" + 
			   "WHERE co_id = '"+co_id+"';";
	}
	
	public static String sqlPhase(String proj_id){
		
		return "SELECT sub_proj_id as \"ID\", \n" + 
				"phase as \"Phase\",\n" + 
				"sub_proj_name as \"Phase Name\",\n" + 
				"sub_proj_alias as \"Alias\"\n" + 
				"FROM mf_sub_project\n" + 
				"WHERE proj_id = '"+proj_id+"' AND status_id = 'A'";//ADDED STATUS ID BY LESTER DCRF 2718
	}
	
	public static String sqlCompanyLogo(String co_id){
		String SQL = "SELECT company_logo FROM mf_company WHERE co_id = '"+co_id+"'";
		pgSelect db = new pgSelect();
		db.select(SQL);
		
		if(db.isNotNull()){
			return (String) db.getResult()[0][0];
		}else{
			return "";
		}
	}
	
	public static String sqlCurrentStage(){
		return "SELECT TRIM(status_code) as \"ID\", \n" + 
			   "TRIM(status_desc) as \"Stage\", TRIM(status_alias) as \"Alias\"\n" + 
			   "FROM mf_hdmf_filing_status\n" + 
			   "WHERE status_code NOT IN ('02','03','04','05','11','17','19','22','23','32','34')\n" + 
			   "ORDER BY status_desc;";
	}
	
	public static String sqlNewStage(){
		return "SELECT TRIM(status_code) as \"ID\", \n" + 
				"TRIM(status_desc) as \"Stage\", TRIM(status_alias) as \"Alias\"\n" + 
				"FROM mf_hdmf_filing_status \n" + 
				"WHERE status_code NOT IN ('01','35','36','37','15','12','02','03','04','05','11','17','19','22','23','32','34') \n" + 
				"ORDER BY status_desc;";
	}
	
	public static String sqlBatchNo(){
		return "SELECT DISTINCT ON (a.hdmf_status_batchno) a.hdmf_status_batchno AS \"Batch No\", \n" + 
			   "get_employee_name(a.add_by) AS \"Tagged By\", b.status_desc AS \"Status Desc\"\n" + 
			   "FROM rf_hdmf_filing_detail a\n" + 
			   "LEFT JOIN mf_hdmf_filing_status b on b.status_id = a.filing_status\n" + 
			   "WHERE a.hdmf_status_batchno IS NOT NULL";
	}
	
	public static String sqlClient(){
		return "SELECT \n" + 
				"a.entity_id as \"Entity ID\", get_client_name(a.entity_id) as \"Client Name\", \n" + 
				"get_project_alias(a.projcode) as \"Proj. Alias\", b.description as \"Unit Desc\", \n" + 
				"get_project_name(a.projcode), a.pbl_id, a.seq_no\n" + 
				"FROM rf_sold_unit a\n" + 
				"LEFT JOIN mf_unit_info b on b.proj_id = a.projcode and b.pbl_id = a.pbl_id\n" + 
				"WHERE get_group_id(a.buyertype) = '04'\n" + 
				"ORDER BY get_client_name(a.entity_id)";
	}
	
	
	public static String sqlNewStatus(){
		
		return "SELECT status_id AS \"ID\", \n" + 
			   "status_desc AS \"Status\"\n" + 
			   "FROM mf_house_inspection_status";
		
	}
	
	public static void displayPQA_SCD_Release(String proj_id, String phase,Date date_cut_off ,modelPagibigStatusMonitoring_QualifiedAccounts model, JList rowHeader){
		if(model != null && rowHeader != null){
			FncTables.clearTable(model);
			
			DefaultListModel listModel = new DefaultListModel();
			rowHeader.setModel(listModel);
			
			pgSelect db = new pgSelect();
			
			String sql = "SELECT * FROM sp_generate_pagibig_qualified_scd_release('"+proj_id+"', '"+phase+"', '"+date_cut_off+"')";
			db.select(sql);
			
			FncSystem.out("Display sql for Qualified Accounts SCD Release", sql);
			
			if(db.isNotNull()){
				for(int x= 0; x<db.getRowCount(); x++){
					model.addRow(db.getResult()[x]);
					listModel.addElement(model.getRowCount());
				}
			}
		}
	}
	
	public static void displayPQA_Deficient_Accounts(String proj_id, String phase, Date date_cut_off, modelPagibigStatusMonitoring_DeficientAccounts model, JList rowHeader){
		if(model != null && rowHeader != null){
			FncTables.clearTable(model);
			
			DefaultListModel listModel = new DefaultListModel();
			rowHeader.setModel(listModel);
			
			pgSelect db = new pgSelect();
			
			String sql = "SELECT * FROM sp_generate_pagibig_qualified_deficient_accounts(NULLIF('"+proj_id+"', 'null'), NULLIF('"+phase+"', 'null'), NULLIF('"+date_cut_off+"', 'null')::TIMESTAMP WITHOUT TIME ZONE)";
			db.select(sql);
			
			FncSystem.out("Display sql for Deficient Accounts", sql);
			
			if(db.isNotNull()){
				for(int x= 0; x<db.getRowCount(); x++){
					model.addRow(db.getResult()[x]);
					listModel.addElement(model.getRowCount());
				}
			}
		}
	}

	public static void displayPQA_Docs_Completion(String co_id,String proj_id,String phase, Date date_cut_off, modelPagibigStatusMonitoring_QualifiedAccounts model, JList rowHeader){
		if(model != null && rowHeader != null){
			
			FncTables.clearTable(model);

			DefaultListModel listModel = new DefaultListModel();
			rowHeader.setModel(listModel);

			pgSelect db = new pgSelect();

			String sql = "SELECT * FROM sp_generate_pagibig_qualified_docs_complete(NULLIF('"+co_id+"', 'null'), NULLIF('"+proj_id+"', 'null'),NULLIF('"+phase+"', 'null'), NULLIF('"+date_cut_off+"', 'null')::TIMESTAMP WITHOUT TIME ZONE)";
			db.select(sql);

			FncSystem.out("Display Qualified Accounts for Docs Complete", sql);

			if(db.isNotNull()){
				for(int x= 0; x<db.getRowCount(); x++){
					model.addRow(db.getResult()[x]);
					listModel.addElement(model.getRowCount());
				}
			}
		}
	}
	
	public static void displayPQA_ForTCTAnnotation(String proj_id, String phase, Date date_cut_off, modelPST_ForTCTAnnotation model, JList rowHeader){
		if(model != null && rowHeader != null){
			FncTables.clearTable(model);
			DefaultListModel listModel = new DefaultListModel();
			rowHeader.setModel(listModel);
			
			pgSelect db = new pgSelect();
			String SQL = "SELECT * FROM sp_generate_pagibig_for_tct_annotation(NULLIF('"+proj_id+"', 'null'), NULLIF('"+phase+"', 'null'), NULLIF('"+date_cut_off+"', 'null')::TIMESTAMP WITHOUT TIME ZONE)";
			db.select(SQL);
			
			FncSystem.out("Display Qualified Accounts for TCT Annotation", SQL);
			
			if(db.isNotNull()){
				for(int x= 0; x<db.getRowCount(); x++){
					model.addRow(db.getResult()[x]);
					listModel.addElement(model.getRowCount());
				}
			}
		}
	}
	
	public static void displayPQA_First_Filing(String proj_id, String phase, Date date_cut_off, modelPagibigStatusMonitoring_QualifiedAccounts model, JList rowHeader){
		if(model != null && rowHeader != null){
			
			FncTables.clearTable(model);
			DefaultListModel listModel = new DefaultListModel();
			rowHeader.setModel(listModel);
			
			pgSelect db = new pgSelect();
			
			String SQL = "SELECT * FROM sp_generate_pagibig_qualified_first_filing(NULLIF('"+proj_id+"', 'null'), NULLIF('"+phase+"', 'null'), NULLIF('"+date_cut_off+"', 'null')::TIMESTAMP WITHOUT TIME ZONE)";
			db.select(SQL);
			
			FncSystem.out("Display Qualified Accounts for First Filing", SQL);
			
			if(db.isNotNull()){
				for(int x = 0; x<db.getRowCount(); x++){
					model.addRow(db.getResult()[x]);
					listModel.addElement(model.getRowCount());
				}
			}
		}
	}
	public static void displayPST_LoanField(String co_id, String proj_id, String phase, modelPST_LoanFiled model, JList rowHeader){
		if(model != null && rowHeader != null){
			FncTables.clearTable(model);
			DefaultListModel listModel = new DefaultListModel();
			rowHeader.setModel(listModel);
			
			pgSelect db = new pgSelect();
			String SQL = "SELECT * FROM sp_generate_pagibig_status_loan_filed(NULLIF('"+proj_id+"', 'null'), NULLIF('"+phase+"', 'null'))";
			db.select(SQL);
			
			FncSystem.out("Display SQL For Loan Filed", SQL);
			
			if(db.isNotNull()){
				for(int x= 0; x<db.getRowCount(); x++){
					model.addRow(db.getResult()[x]);
					listModel.addElement(model.getRowCount());
				}
			}
		}
	}
	
	public static void displayPST_LoanField_GtoG(String co_id, String proj_id, String phase, modelPST_LoanFiled model, JList rowHeader){
		if(model != null && rowHeader != null){
			FncTables.clearTable(model);
			DefaultListModel listModel = new DefaultListModel();
			rowHeader.setModel(listModel);
			
			pgSelect db = new pgSelect();
			String SQL = "SELECT * FROM sp_generate_pagibig_status_loan_filed_gtog(NULLIF('"+proj_id+"', 'null'), NULLIF('"+phase+"', 'null'))";
			db.select(SQL);
			
			FncSystem.out("Display SQL For Loan Filed(G to G)", SQL);
			
			if(db.isNotNull()){
				for(int x= 0; x<db.getRowCount(); x++){
					model.addRow(db.getResult()[x]);
					listModel.addElement(model.getRowCount());
				}
			}
		}
	}
	
	public static void displatPST_NOA_Released(String proj_id, String phase, modelNOATagging2 modelNOAReleased, JList rowHeader, String strBat){
		if(modelNOAReleased != null && rowHeader != null){
			FncTables.clearTable(modelNOAReleased);
			
			DefaultListModel listModel = new DefaultListModel();
			rowHeader.setModel(listModel);
			
			pgSelect db = new pgSelect();
			//String SQL = "SELECT *, now()::date as actual_date FROM view_pagibig_noa_qualified()";
			/*MODIFIED:ALMAR 08-29-23 YASMELYN REQUEST FILTER FOR COMPANY/PROJECT/PHASE*/
			//String SQL = "SELECT * FROM view_pagibig_noa_qualified('"+strBat+"')";
			String SQL = "SELECT * FROM view_pagibig_noa_qualified_v2('"+strBat+"','"+PagibigStatusMonitoring_v2.company_id+"','"+PagibigStatusMonitoring_v2.project_id+"','"+PagibigStatusMonitoring_v2.phase_id+"')";
			db.select(SQL);
			
			FncSystem.out("Display SQL For NOA Released", SQL);
			
			if(db.isNotNull()){
				for(int x=0; x<db.getRowCount(); x++){
					modelNOAReleased.addRow(db.getResult()[x]);
					listModel.addElement(modelNOAReleased.getRowCount());
				}
			}
		}
	}
	
	public static void displayPST_WithDOASigned(String proj_id, String phase, Date date_cut_off, modelPST_DOA_Signed model, JList rowHeader){
		if(model != null && rowHeader != null){
			
			FncTables.clearTable(model);
			DefaultListModel listModel = new DefaultListModel();
			rowHeader.setModel(listModel);
			
			pgSelect db = new pgSelect();
			String SQL = "SELECT * FROM sp_generate_pagibig_status_doa_assigned(NULLIF('"+proj_id+"', 'null'), NULLIF('"+phase+"', 'null'), NULLIF('"+date_cut_off+"', 'null')::TIMESTAMP WITHOUT TIME ZONE)";
			db.select(SQL);
			
			FncSystem.out("Display Status Tagging With DOA Signed", SQL);
			
			if(db.isNotNull()){
				for(int x = 0; x<db.getRowCount(); x++){
					model.addRow(db.getResult()[x]);
					listModel.addElement(model.getRowCount());
				}
			}
			
		}
	}
	
	public static void displayPST_ForTCTAnnotation(String proj_id, String phase, Date date_cut_off, modelPST_DOA_Signed model, JList rowHeader){
		if(model != null && rowHeader != null){
			
			FncTables.clearTable(model);
			DefaultListModel listModel = new DefaultListModel();
			rowHeader.setModel(listModel);
			
			pgSelect db = new pgSelect();
			String SQL = "SELECT * FROM sp_generate_pagibig_tct_annotation(NULLIF('"+proj_id+"', 'null'), NULLIF('"+phase+"', 'null'), NULLIF('"+date_cut_off+"', 'null')::TIMESTAMP WITHOUT TIME ZONE)";
			db.select(SQL);
			
			FncSystem.out("Display Status Tagging for TCT Annotation", SQL);
			
			if(db.isNotNull()){
				for(int x= 0; x<db.getRowCount(); x++){
					model.addRow(db.getResult()[x]);
					listModel.addElement(model.getRowCount());
				}
			}
		}
	}
	
	public static void displayPST_NOA_Expiring(String proj_id, String phase, Date date_cut_off, modelPST_NOA_Expiring model, JList rowHeader){
		if(model != null && rowHeader != null){
			
			FncTables.clearTable(model);
			DefaultListModel listModel = new DefaultListModel();
			rowHeader.setModel(listModel);
			
			pgSelect db = new pgSelect();
			
			String SQL = "SELECT * FROM sp_generate_pagibig_status_noa_expiring(NULLIF('"+proj_id+"', 'null'), NULLIF('"+phase+"', 'null'), NULLIF('"+date_cut_off+"', 'null')::TIMESTAMP WITHOUT TIME ZONE)";
			db.select(SQL);
			
			FncSystem.out("Display SQL For Expiring NOA", SQL);
			
			if(db.isNotNull()){
				for(int x = 0; x<db.getRowCount(); x++){
					model.addRow(db.getResult()[x]);
					listModel.addElement(model.getRowCount());
				}
			}
		}
	}
	
	public static void displayPST_WithEPEB(String proj_id, String phase, Date date_cut_off, modelPST_With_EPEB model, JList rowHeader){
		if(model != null && rowHeader != null){
			
			FncTables.clearTable(model);
			DefaultListModel listModel = new DefaultListModel();
			rowHeader.setModel(listModel);
			
			pgSelect db = new pgSelect();
			String SQL = "SELECT * FROM sp_generate_pagibig_status_with_epeb(NULLIF('"+proj_id+"', 'null'), NULLIF('"+phase+"', 'null'), NULLIF('"+date_cut_off+"', 'null')::TIMESTAMP)";
			db.select(SQL);
			
			FncSystem.out("Display SQL With EPEB", SQL);
			
			if(db.isNotNull()){
				for(int x = 0; x<db.getRowCount(); x++){
					model.addRow(db.getResult()[x]);
					listModel.addElement(model.getRowCount());
				}
			}
		}
	}
	
	public static void displayPST_WithAnnotatedTCT(String co_id, String proj_id, String phase, String batch_no, modelPST_With_Annotated_TCT model, JList rowHeader){
		if(model != null && rowHeader != null){
			FncTables.clearTable(model);
			
			DefaultListModel listModel = new DefaultListModel();
			rowHeader.setModel(listModel);
			
			pgSelect db = new pgSelect();
			String SQL = "SELECT * FROM sp_generate_pagibig_status_with_annotated_tct(NULLIF('"+co_id+"', 'null'), NULLIF('"+proj_id+"', 'null'), NULLIF('"+phase+"', 'null'), NULLIF('"+batch_no+"', 'null'))";
			db.select(SQL);
			
			FncSystem.out("Display SQL With EPEB", SQL);
			
			if(db.isNotNull()){
				for(int x = 0; x<db.getRowCount(); x++){
					model.addRow(db.getResult()[x]);
					listModel.addElement(model.getRowCount());
				}
			}
		}
	}
	
	public static void displayPST_ForApproval(String co_id, String proj_id, String phase, String batch_no ,modelPST_ForPostApproval model, JList rowHeader){
		if(model != null && rowHeader != null){
			FncTables.clearTable(model);
			
			DefaultListModel listModel = new DefaultListModel();
			rowHeader.setModel(listModel);
			
			pgSelect db = new pgSelect();
			String SQL = "SELECT * FROM sp_generate_pagibig_status_for_post_approval(NULLIF('"+co_id+"', 'null'), NULLIF('"+proj_id+"', 'null'), NULLIF('"+phase+"', 'null'), NULLIF('"+batch_no+"', 'null'))";
			db.select(SQL);
			
			FncSystem.out("Display SQL With EPEB", SQL);
			
			if(db.isNotNull()){
				for(int x = 0; x<db.getRowCount(); x++){
					model.addRow(db.getResult()[x]);
					listModel.addElement(model.getRowCount());
				}
			}
		}
	}
	
	public static void displayPST_ForApproval_GtoG(String co_id, String proj_id, String phase, String batch_no ,modelPST_ForPostApproval model, JList rowHeader){
		if(model != null && rowHeader != null){
			FncTables.clearTable(model);
			
			DefaultListModel listModel = new DefaultListModel();
			rowHeader.setModel(listModel);
			
			pgSelect db = new pgSelect();
			String SQL = "SELECT * FROM sp_generate_pagibig_status_for_post_approval_gtog(NULLIF('"+co_id+"', 'null'), NULLIF('"+proj_id+"', 'null'), NULLIF('"+phase+"', 'null'), NULLIF('"+batch_no+"', 'null'))";
			db.select(SQL);
			
			FncSystem.out("Display SQL With EPEB", SQL);
			
			if(db.isNotNull()){
				for(int x = 0; x<db.getRowCount(); x++){
					model.addRow(db.getResult()[x]);
					listModel.addElement(model.getRowCount());
				}
			}
		}
	}
	
	
	public static void saveHDMFStatus(Integer selected_stage ,Date date_cutoff, modelPagibigStatusMonitoring_QualifiedAccounts model){
		
		ArrayList<String> listProjID = new ArrayList<String>();
		ArrayList<String> listUnitID = new ArrayList<String>();
		ArrayList<String> listPBL = new ArrayList<String>();
		ArrayList<Integer> listSeq = new ArrayList<Integer>();
		ArrayList<String> listEntityID = new ArrayList<String>();
		ArrayList<BigDecimal> listNSP = new ArrayList<BigDecimal>();
		ArrayList<BigDecimal> listLoanAmt = new ArrayList<BigDecimal>();
		
		for(int x = 0; x<model.getRowCount(); x++){
			Boolean isSelected = (Boolean) model.getValueAt(x, 0);
			
			if(isSelected){
				
				String entity_id = (String) model.getValueAt(x, 3);
				String proj_id = (String) model.getValueAt(x, 5);
				String pbl_id = (String) model.getValueAt(x, 6);
				Integer seq_no = (Integer) model.getValueAt(x, 7);
				
				listProjID.add(String.format("'%S'", proj_id));
				listPBL.add(String.format("'%s'", pbl_id));
				listSeq.add(seq_no);
				listEntityID.add(String.format("'%s'", entity_id));
				
			}
		}
		
		String proj_id = listProjID.toString().replaceAll("\\[|\\]", "");
		//String unit_id = listUnitID.toString().replaceAll("\\[|\\]", "");
		String pbl_id = listPBL.toString().replaceAll("\\[|\\]", "");
		String seq_no = listSeq.toString().replaceAll("\\[|\\]", "");
		String entity_id = listEntityID.toString().replaceAll("\\[|\\]", "");
		
		/*String nsp = listNSP.toString().replaceAll("\\[|\\]", "");
		String loan_amt = listLoanAmt.toString().replaceAll("\\[|\\]", "");*/
		
		pgSelect db = new pgSelect();
		
		String sql = "SELECT sp_post_psm_qualify_accounts(ARRAY["+entity_id+"]::VARCHAR[], ARRAY["+proj_id+"]::VARCHAR[], \n"+
					 "ARRAY["+pbl_id+"]::VARCHAR[], ARRAY["+seq_no+"]::INT[], "+selected_stage+", '"+date_cutoff+"', '"+UserInfo.EmployeeCode+"')";
		db.select(sql);
		
		FncSystem.out("Posting of Qualified Accounts for Pagibig", sql); 
	}
	
	public static void saveForTCTAnnotation(Integer selected_stage, Date date_cutoff, modelPST_ForTCTAnnotation model){
		ArrayList<String> listProjID = new ArrayList<String>();
		ArrayList<String> listUnitID = new ArrayList<String>();
		ArrayList<String> listPBL = new ArrayList<String>();
		ArrayList<Integer> listSeq = new ArrayList<Integer>();
		ArrayList<String> listEntityID = new ArrayList<String>();
		ArrayList<BigDecimal> listNSP = new ArrayList<BigDecimal>();
		ArrayList<BigDecimal> listLoanAmt = new ArrayList<BigDecimal>();
		
		for(int x = 0; x<model.getRowCount(); x++){
			Boolean isSelected = (Boolean) model.getValueAt(x, 0);
			
			if(isSelected){
				
				String entity_id = (String) model.getValueAt(x, 3);
				String proj_id = (String) model.getValueAt(x, 5);
				String pbl_id = (String) model.getValueAt(x, 6);
				Integer seq_no = (Integer) model.getValueAt(x, 7);
				
				listProjID.add(String.format("'%S'", proj_id));
				listPBL.add(String.format("'%s'", pbl_id));
				listSeq.add(seq_no);
				listEntityID.add(String.format("'%s'", entity_id));
				
			}
		}
		
		String proj_id = listProjID.toString().replaceAll("\\[|\\]", "");
		//String unit_id = listUnitID.toString().replaceAll("\\[|\\]", "");
		String pbl_id = listPBL.toString().replaceAll("\\[|\\]", "");
		String seq_no = listSeq.toString().replaceAll("\\[|\\]", "");
		String entity_id = listEntityID.toString().replaceAll("\\[|\\]", "");
		
		/*String nsp = listNSP.toString().replaceAll("\\[|\\]", "");
		String loan_amt = listLoanAmt.toString().replaceAll("\\[|\\]", "");*/
		
		pgSelect db = new pgSelect();
		
		String sql = "SELECT sp_post_psm_qualify_accounts(ARRAY["+entity_id+"]::VARCHAR[], ARRAY["+proj_id+"]::VARCHAR[], \n"+
					 "ARRAY["+pbl_id+"]::VARCHAR[], ARRAY["+seq_no+"]::INT[], "+selected_stage+", '"+date_cutoff+"', '"+UserInfo.EmployeeCode+"')";
		db.select(sql);
		
		FncSystem.out("Posting of Qualified Accounts for Pagibig", sql);
	}
	
	public static void saveLoanFiledAccts(Date date_cut_off,modelPST_LoanFiled model){
		
		ArrayList<String> listEntityID = new ArrayList<String>();
		ArrayList<String> listProjID = new ArrayList<String>();
		ArrayList<String> listPBL = new ArrayList<String>();
		ArrayList<Integer> listSeq = new ArrayList<Integer>();
		
		for(int x= 0; x<model.getRowCount(); x++){
			Boolean isSelected = (Boolean) model.getValueAt(x, 0);
			
			if(isSelected){
				String entity_id = (String) model.getValueAt(x, 7);
				String proj_id = (String) model.getValueAt(x, 8);
				String pbl_id = (String) model.getValueAt(x, 9);
				Integer seq_no = (Integer) model.getValueAt(x, 10);
				
				listProjID.add(String.format("'%S'", proj_id));
				listPBL.add(String.format("'%s'", pbl_id));
				listSeq.add(seq_no);
				listEntityID.add(String.format("'%s'", entity_id));
			}
		}
		String entity_id = listEntityID.toString().replaceAll("\\[|\\]", "");
		String proj_id = listProjID.toString().replaceAll("\\[|\\]", "");
		String pbl_id = listPBL.toString().replaceAll("\\[|\\]", "");
		String seq_no = listSeq.toString().replaceAll("\\[|\\]", "");
		
		pgSelect db = new pgSelect();
		String SQL = "SELECT sp_tag_accts_loan_filed(ARRAY["+entity_id+"]::VARCHAR[], ARRAY["+proj_id+"]::VARCHAR[], ARRAY["+pbl_id+"]::VARCHAR[], \n"+
					 "ARRAY["+seq_no+"]::INT[], NULLIF('"+date_cut_off+"', 'null')::TIMESTAMP, '"+UserInfo.Branch+"' ,'"+UserInfo.EmployeeCode+"')";
		db.select(SQL);
		
		FncSystem.out("Display SQL For DOA Tagging", SQL);
		
	}
	
	public static void saveLoanFiledAccts_GtoG(Date date_cut_off,modelPST_LoanFiled model){
		
		ArrayList<String> listEntityID = new ArrayList<String>();
		ArrayList<String> listProjID = new ArrayList<String>();
		ArrayList<String> listPBL = new ArrayList<String>();
		ArrayList<Integer> listSeq = new ArrayList<Integer>();
		
		for(int x= 0; x<model.getRowCount(); x++){
			Boolean isSelected = (Boolean) model.getValueAt(x, 0);
			
			if(isSelected){
				String entity_id = (String) model.getValueAt(x, 7);
				String proj_id = (String) model.getValueAt(x, 8);
				String pbl_id = (String) model.getValueAt(x, 9);
				Integer seq_no = (Integer) model.getValueAt(x, 10);
				
				listProjID.add(String.format("'%S'", proj_id));
				listPBL.add(String.format("'%s'", pbl_id));
				listSeq.add(seq_no);
				listEntityID.add(String.format("'%s'", entity_id));
			}
		}
		String entity_id = listEntityID.toString().replaceAll("\\[|\\]", "");
		String proj_id = listProjID.toString().replaceAll("\\[|\\]", "");
		String pbl_id = listPBL.toString().replaceAll("\\[|\\]", "");
		String seq_no = listSeq.toString().replaceAll("\\[|\\]", "");
		
		pgSelect db = new pgSelect();
		String SQL = "SELECT sp_tag_accts_loan_filed_gtog(ARRAY["+entity_id+"]::VARCHAR[], ARRAY["+proj_id+"]::VARCHAR[], ARRAY["+pbl_id+"]::VARCHAR[], \n"+
					 "ARRAY["+seq_no+"]::INT[], NULLIF('"+date_cut_off+"', 'null')::TIMESTAMP, '"+UserInfo.Branch+"' ,'"+UserInfo.EmployeeCode+"')";
		db.select(SQL);
		
		FncSystem.out("Display SQL For DOA Tagging", SQL);
		
	}
	
	public static void saveNOAReleasedAccts(Date date_cut_off, modelNOATagging2 model){
		ArrayList<String> listEntityID = new ArrayList<String>();
		ArrayList<String> listProjID = new ArrayList<String>();
		ArrayList<String> listPBL = new ArrayList<String>();
		ArrayList<Integer> listSeq = new ArrayList<Integer>();
		ArrayList<String> listActualDate = new ArrayList<String>();
		ArrayList<String> listLoanable = new ArrayList<String>();
		
		for(int x= 0; x<model.getRowCount(); x++){
			Boolean isSelected = (Boolean) model.getValueAt(x, 0);
			
			if(isSelected){
				String entity_id = (String) model.getValueAt(x, 1);
				String proj_id = (String) model.getValueAt(x, 7);
				String pbl_id = (String) model.getValueAt(x, 4);
				Integer seq_no = (Integer) model.getValueAt(x, 5);
				String actual_date = (String) model.getValueAt(x, 11).toString();
				String loanable_amount = (String) model.getValueAt(x, 9).toString();
				
				listProjID.add(String.format("'%S'", proj_id));
				listPBL.add(String.format("'%s'", pbl_id));
				listSeq.add(seq_no);
				listEntityID.add(String.format("'%s'", entity_id));
				listActualDate.add(String.format("'%s'", actual_date));
				listLoanable.add(String.format("'%s'", loanable_amount));
			}
		}

		pgSelect db = new pgSelect();
		String SQL = "SELECT sp_tag_noa(ARRAY"+ listEntityID +"::VARCHAR[], \n" +
					 "ARRAY"+ listProjID +"::VARCHAR[], \n" +
					 "ARRAY"+ listPBL +"::VARCHAR[], \n" +
					 "ARRAY"+ listSeq +"::INTEGER[], \n" +
					 "NULLIF('"+date_cut_off+"', 'null')::TIMESTAMP, \n"+
					 "ARRAY"+ listActualDate +"::TIMESTAMP[], \n" +
					 "'"+ UserInfo.Branch +"', '"+ UserInfo.EmployeeCode +"', \n" + 
					 "ARRAY"+ listLoanable +"::NUMERIC[]);";
		db.select(SQL);
		
		FncSystem.out("Display SQL For DOA Tagging", SQL);
	}
	
	public static void saveDOASignedAccounts(Date date_cut_off, modelPST_DOA_Signed model){
		
		ArrayList<String> listEntityID = new ArrayList<String>();
		ArrayList<String> listProjID = new ArrayList<String>();
		ArrayList<String> listPBL = new ArrayList<String>();
		ArrayList<Integer> listSeq = new ArrayList<Integer>();
		
		for(int x= 0; x<model.getRowCount(); x++){
			Boolean isSelected = (Boolean) model.getValueAt(x, 0);
			
			if(isSelected){
				String entity_id = (String) model.getValueAt(x, 3);
				String proj_id = (String) model.getValueAt(x, 5);
				String pbl_id = (String) model.getValueAt(x, 6);
				Integer seq_no = (Integer) model.getValueAt(x, 7);
				
				listProjID.add(String.format("'%S'", proj_id));
				listPBL.add(String.format("'%s'", pbl_id));
				listSeq.add(seq_no);
				listEntityID.add(String.format("'%s'", entity_id));
			}
		}
		String entity_id = listEntityID.toString().replaceAll("\\[|\\]", "");
		String proj_id = listProjID.toString().replaceAll("\\[|\\]", "");
		String pbl_id = listPBL.toString().replaceAll("\\[|\\]", "");
		String seq_no = listSeq.toString().replaceAll("\\[|\\]", "");
		
		pgSelect db = new pgSelect();
		String SQL = "SELECT sp_tag_accts_doa_signed(ARRAY["+entity_id+"]::VARCHAR[], ARRAY["+proj_id+"]::VARCHAR[], ARRAY["+pbl_id+"]::VARCHAR[], \n"+
					 "ARRAY["+seq_no+"]::INT[], NULLIF('"+date_cut_off+"', 'null')::TIMESTAMP, '"+UserInfo.Branch+"' ,'"+UserInfo.EmployeeCode+"')";
		db.select(SQL);
		
		FncSystem.out("Display SQL For DOA Tagging", SQL);
	}
	
	public static void saveTCTAnnotationAccounts(Date actual_date, modelPST_ForTCTAnnotation model){
		
		ArrayList<String> listEntityID = new ArrayList<String>();
		ArrayList<String> listProjID = new ArrayList<String>();
		ArrayList<String> listPBL = new ArrayList<String>();
		ArrayList<Integer> listSeq = new ArrayList<Integer>();
		
		for(int x= 0; x<model.getRowCount(); x++){
			Boolean isSelected = (Boolean) model.getValueAt(x, 0);
			
			if(isSelected){
				String entity_id = (String) model.getValueAt(x, 3);
				String proj_id = (String) model.getValueAt(x, 5);
				String pbl_id = (String) model.getValueAt(x, 6);
				Integer seq_no = (Integer) model.getValueAt(x, 7);
				
				listProjID.add(String.format("'%S'", proj_id));
				listPBL.add(String.format("'%s'", pbl_id));
				listSeq.add(seq_no);
				listEntityID.add(String.format("'%s'", entity_id));
			}
		}
		
		String entity_id = listEntityID.toString().replaceAll("\\[|\\]", "");
		String proj_id = listProjID.toString().replaceAll("\\[|\\]", "");
		String pbl_id = listPBL.toString().replaceAll("\\[|\\]", "");
		String seq_no = listSeq.toString().replaceAll("\\[|\\]", "");
		
		pgSelect db = new pgSelect();
		String SQL = "SELECT sp_tag_accts_tct_forwarded(ARRAY["+entity_id+"]::VARCHAR[], ARRAY["+proj_id+"]::VARCHAR[], ARRAY["+pbl_id+"]::VARCHAR[], \n"+
					 "ARRAY["+seq_no+"]::INT[], '"+actual_date+"', '"+UserInfo.Branch+"' ,'"+UserInfo.EmployeeCode+"')";
		db.select(SQL);
		
		FncSystem.out("Display SQL For TCT Forwarded Tagging", SQL);
	}
	
	public static void saveWithEPEB(modelPST_With_EPEB model){
		ArrayList<String> listEntityID = new ArrayList<String>();
		ArrayList<String> listProjID = new ArrayList<String>();
		ArrayList<String> listPBL = new ArrayList<String>();
		ArrayList<Integer> listSeq = new ArrayList<Integer>();
		ArrayList<String> listEPEB_No = new ArrayList<String>();
		ArrayList<String> listEPEB_Date = new ArrayList<String>();
		
		for(int x= 0; x<model.getRowCount(); x++){
			Boolean isSelected = (Boolean) model.getValueAt(x, 0);
			
			if(isSelected){
				String epeb_no = (String) model.getValueAt(x, 3);
				System.out.printf("Display Value of epeb date: %s%n", model.getValueAt(x, 4));
				
				String epeb_date = (String) model.getValueAt(x, 4);
				String entity_id = (String) model.getValueAt(x, 7);
				String proj_id = (String) model.getValueAt(x, 8);
				String pbl_id = (String) model.getValueAt(x, 9);
				Integer seq_no = (Integer) model.getValueAt(x, 10);
				
				listProjID.add(String.format("'%S'", proj_id));
				listPBL.add(String.format("'%s'", pbl_id));
				listSeq.add(seq_no);
				listEntityID.add(String.format("'%s'", entity_id));
				listEPEB_No.add(String.format("'%s'", epeb_no));
				listEPEB_Date.add(String.format("'%s'", epeb_date));
			}
		}
		
		String entity_id = listEntityID.toString().replaceAll("\\[|\\]", "");
		String proj_id = listProjID.toString().replaceAll("\\[|\\]", "");
		String pbl_id = listPBL.toString().replaceAll("\\[|\\]", "");
		String seq_no = listSeq.toString().replaceAll("\\[|\\]", "");
		String epeb_no = listEPEB_No.toString().replaceAll("\\[|\\]", "");
		String epeb_date = listEPEB_Date.toString().replace("\\[|\\]", "");
		System.out.printf("Display value of epeb dates: %s%n", epeb_date);
		
		pgSelect db = new pgSelect();
		String SQL = "SELECT sp_tag_accts_with_epeb(ARRAY["+entity_id+"]::VARCHAR[], ARRAY["+proj_id+"]::VARCHAR[], ARRAY["+pbl_id+"]::VARCHAR[], \n"+
					 "ARRAY["+seq_no+"]::INT[], ARRAY["+epeb_no+"]::VARCHAR[], ARRAY"+epeb_date+"::TIMESTAMP WITHOUT TIME ZONE[] ,'"+UserInfo.EmployeeCode+"')";
		db.select(SQL);
		
		FncSystem.out("Display SQL With EPEB", SQL);
	}
	
	public static String saveWithAnnotatedTCT(modelPST_With_Annotated_TCT model){
		ArrayList<String> listEntityID = new ArrayList<String>();
		ArrayList<String> listProjID = new ArrayList<String>();
		ArrayList<String> listPBL = new ArrayList<String>();
		ArrayList<Integer> listSeq = new ArrayList<Integer>();
		
		for(int x= 0; x<model.getRowCount(); x++){
			Boolean isSelected = (Boolean) model.getValueAt(x, 0);
			
			if(isSelected){
				
				String entity_id = (String) model.getValueAt(x, 6);
				String proj_id = (String) model.getValueAt(x, 7);
				String pbl_id = (String) model.getValueAt(x, 8);
				Integer seq_no = (Integer) model.getValueAt(x, 9);
				
				listProjID.add(String.format("'%S'", proj_id));
				listPBL.add(String.format("'%s'", pbl_id));
				listSeq.add(seq_no);
				listEntityID.add(String.format("'%s'", entity_id));
			}
		}
		
		String entity_id = listEntityID.toString().replaceAll("\\[|\\]", "");
		String proj_id = listProjID.toString().replaceAll("\\[|\\]", "");
		String pbl_id = listPBL.toString().replaceAll("\\[|\\]", "");
		String seq_no = listSeq.toString().replaceAll("\\[|\\]", "");
		
		pgSelect db = new pgSelect();
		String SQL = "SELECT sp_tag_accts_with_annotated_tct(ARRAY["+entity_id+"]::VARCHAR[], ARRAY["+proj_id+"]::VARCHAR[], ARRAY["+pbl_id+"]::VARCHAR[], \n"+
					 "ARRAY["+seq_no+"]::INT[], '"+UserInfo.Branch+"','"+UserInfo.EmployeeCode+"')";
		db.select(SQL);
		FncSystem.out("Display SQL tagging of tct annotated", SQL);
		
		String batch_no = "";
		
		if(db.isNotNull()){
			batch_no = (String) db.getResult()[0][0];
		}
		return batch_no;
	}
	
	public static String saveForPostApproval(modelPST_ForPostApproval model){
		ArrayList<String> listEntityID = new ArrayList<String>();
		ArrayList<String> listProjID = new ArrayList<String>();
		ArrayList<String> listPBL = new ArrayList<String>();
		ArrayList<Integer> listSeq = new ArrayList<Integer>();
		
		for(int x= 0; x<model.getRowCount(); x++){
			Boolean isSelected = (Boolean) model.getValueAt(x, 0);
			
			if(isSelected){
				
				String entity_id = (String) model.getValueAt(x, 7);
				String proj_id = (String) model.getValueAt(x, 8);
				String pbl_id = (String) model.getValueAt(x, 9);
				Integer seq_no = (Integer) model.getValueAt(x, 10);
				
				listProjID.add(String.format("'%S'", proj_id));
				listPBL.add(String.format("'%s'", pbl_id));
				listSeq.add(seq_no);
				listEntityID.add(String.format("'%s'", entity_id));
			}
		}
		
		String entity_id = listEntityID.toString().replaceAll("\\[|\\]", "");
		String proj_id = listProjID.toString().replaceAll("\\[|\\]", "");
		String pbl_id = listPBL.toString().replaceAll("\\[|\\]", "");
		String seq_no = listSeq.toString().replaceAll("\\[|\\]", "");
		
		pgSelect db = new pgSelect();
		String SQL = "SELECT sp_tag_accts_for_post_approval(ARRAY["+entity_id+"]::VARCHAR[], ARRAY["+proj_id+"]::VARCHAR[], ARRAY["+pbl_id+"]::VARCHAR[], \n"+
					 "ARRAY["+seq_no+"]::INT[], '"+UserInfo.Branch+"','"+UserInfo.EmployeeCode+"')";
		db.select(SQL);
		
		FncSystem.out("Display tagging of post approval", SQL);
		
		String batch_no = "";
		
		if(db.isNotNull()){
			batch_no = (String) db.getResult()[0][0];
		}
		
		return batch_no;
	}
	public static String saveForPostApproval_GtoG(Date actual_date, modelPST_ForPostApproval model){
		ArrayList<String> listEntityID = new ArrayList<String>();
		ArrayList<String> listProjID = new ArrayList<String>();
		ArrayList<String> listPBL = new ArrayList<String>();
		ArrayList<Integer> listSeq = new ArrayList<Integer>();
		
		for(int x= 0; x<model.getRowCount(); x++){
			Boolean isSelected = (Boolean) model.getValueAt(x, 0);
			
			if(isSelected){
				
				String entity_id = (String) model.getValueAt(x, 7);
				String proj_id = (String) model.getValueAt(x, 8);
				String pbl_id = (String) model.getValueAt(x, 9);
				Integer seq_no = (Integer) model.getValueAt(x, 10);
				
				listProjID.add(String.format("'%S'", proj_id));
				listPBL.add(String.format("'%s'", pbl_id));
				listSeq.add(seq_no);
				listEntityID.add(String.format("'%s'", entity_id));
			}
		}
		
		String entity_id = listEntityID.toString().replaceAll("\\[|\\]", "");
		String proj_id = listProjID.toString().replaceAll("\\[|\\]", "");
		String pbl_id = listPBL.toString().replaceAll("\\[|\\]", "");
		String seq_no = listSeq.toString().replaceAll("\\[|\\]", "");
		
		pgSelect db = new pgSelect();
		String SQL = "SELECT sp_tag_accts_for_post_approval_gtog(ARRAY["+entity_id+"]::VARCHAR[], ARRAY["+proj_id+"]::VARCHAR[], ARRAY["+pbl_id+"]::VARCHAR[], \n"+
					 "ARRAY["+seq_no+"]::INT[], NULLIF('"+actual_date+"', 'null')::TIMESTAMP,'"+UserInfo.Branch+"','"+UserInfo.EmployeeCode+"')";
		db.select(SQL);
		
		FncSystem.out("Display tagging of post approval", SQL);
		
		String batch_no = "";
		
		if(db.isNotNull()){
			batch_no = (String) db.getResult()[0][0];
		}
		
		return batch_no;
	}
	
	public static void previewQualifiedAccounts(){
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		//mapParameters.put("", value)	
	}
	
	public static void DisplayForFirstFiling(DefaultTableModel modelMain, JList rowHeader, String strCoID, String strProID, String strPhase, String strDate, Boolean blnTag) {
		FncTables.clearTable(modelMain);
		DefaultListModel listModel = new DefaultListModel();
		rowHeader.setModel(listModel); 
		
		String SQL = "";
		
		if (blnTag) {
			SQL = "SELECT * \n" +
				"FROM view_hdmf_first_filing_v2('"+strCoID+"', '"+strProID+"', '"+strPhase+"', '"+strDate+"'::date, "+blnTag+") \n" +
				"--where c_remaining_dp <= 2 and c_complied_firstfiling = true and c_complied_msvs = true;";
		} else {
			SQL = "SELECT * \n" +
				"FROM view_hdmf_first_filing_v2('"+strCoID+"', '"+strProID+"', '"+strPhase+"', '"+strDate+"'::date, "+blnTag+") \n" +
				"--WHERE c_remaining_dp <= 2 AND c_complied_firstfiling = true AND c_complied_msvs = true;";
		}

		System.out.println("");
		System.out.println(SQL);

		pgSelect db = new pgSelect();
		db.select(SQL);
		if (db.isNotNull()) {
			for (int x = 0; x < db.getRowCount(); x++) {
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());
			}
		} else {
			JOptionPane.showMessageDialog(null, "No records were returned.");
		};
	}
	
	public static void DisplayForFirstFiling_GtoG(DefaultTableModel modelMain, JList rowHeader, String strCoID, String strProID, String strPhase, String strDate, Boolean blnTag) {
		FncTables.clearTable(modelMain);
		DefaultListModel listModel = new DefaultListModel();
		rowHeader.setModel(listModel); 
		
		String SQL = "";
		
		if (blnTag) {
			SQL = "SELECT * \n" +
				"FROM view_hdmf_first_filing_gtog('"+strCoID+"', '"+strProID+"', '"+strPhase+"', '"+strDate+"'::date, "+blnTag+") \n" +
				"--where c_remaining_dp <= 2 and c_complied_firstfiling = true and c_complied_msvs = true;";
		} else {
			SQL = "SELECT * \n" +
				"FROM view_hdmf_first_filing_gtog('"+strCoID+"', '"+strProID+"', '"+strPhase+"', '"+strDate+"'::date, "+blnTag+") \n" +
				"--WHERE c_remaining_dp <= 2 AND c_complied_firstfiling = true AND c_complied_msvs = true;";
		}

		System.out.println("");
		System.out.println(SQL);

		pgSelect db = new pgSelect();
		db.select(SQL);
		if (db.isNotNull()) {
			for (int x = 0; x < db.getRowCount(); x++) {
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());
			}
		} else {
			JOptionPane.showMessageDialog(null, "No records were returned.");
		};
	}
	
	public static String SQL_BATCH(String strType) {
		String strSQL = "";
		if (strType=="Inspection") {
			strSQL = "select distinct on (batch_no::int) batch_no, date_filed as Date_Filed, date_created as Date_Created \n" +
				"from rf_hdmf_insp_header order by batch_no::int";	
		} else if (strType=="Validation") {
			strSQL = "SELECT DISTINCT trans_no, tran_date::date as date_created FROM rf_buyer_status WHERE byrstatus_id = '107' AND status_id = 'A' ORDER BY trans_no";
		}
		
		return strSQL;
	}
	
	public static String CLIENT() {
		return "select d.description as unit, trim(c.entity_name) as name, a.entity_id, a.pbl_id \n" +
			"from rf_sold_unit a \n" +
			"inner join mf_buyer_type b on a.buyertype = b.type_id \n" + 
			"inner join rf_entity c on a.entity_id = c.entity_id \n" +
			"inner join mf_unit_info d on a.projcode = d.proj_id and a.pbl_id = d.pbl_id \n" + 
			//"left join co_ntp_accomplishment e on a.pbl_id = e.pbl_id and a.seq_no::int = e.seq_no::int \n" + 
			"left join rf_hdmf_insp_header f on a.entity_id = f.entity_id and a.projcode = f.projcode and a.pbl_id = f.pbl_id and a.seq_no = f.seq_no \n" + 
			"where a.status_id != 'I' and b.type_group_id = '04' and coalesce(f.batch_no, '') <> '' \n" +
			"order by c.entity_name";
	}
	
	public static String GetRec(String strType) {
		String strRec = "";
		String strSQL = "";
		
		if (strType.equals("Inspection")) {
			strSQL = "SELECT CASE WHEN TRIM((max(COALESCE(insp_rec_id::INT, 0)) + 1)::char(6)) is null THEN '1' ELSE trim((max(COALESCE(insp_rec_id::INT, 0)) + 1)::char(6)) END FROM rf_hdmf_insp_header";
			
			pgSelect dbBatch = new pgSelect();
			dbBatch.select(strSQL);
			
			if (dbBatch.isNotNull()) {
				strRec = (String) dbBatch.getResult()[0][0];
			} else {
				strRec = "000001";
			}
		} else if (strType.equals("Inspected")) {
			strSQL = "SELECT CASE WHEN TRIM((max(COALESCE(insp_detail_rec_id::INT, 0)) + 1)::char(6)) is null THEN '1' ELSE trim((max(COALESCE(insp_detail_rec_id::INT, 0)) + 1)::char(6)) END FROM rf_hdmf_insp_detail";
			
			pgSelect dbBatch = new pgSelect();
			dbBatch.select(strSQL);
			
			if (dbBatch.isNotNull()) {
				strRec = (String) dbBatch.getResult()[0][0];
			} else {
				strRec = "000001";
			}			
		}
		
		return strRec;
	}
	
	public static void displayMSVSForApproval(String co_id, String proj_id, String phase, Date date_cut_off, modelPagibigStatusMonitoring_MSVSMonitoring model, JList rowHeader){
		if(model != null && rowHeader != null){
			FncTables.clearTable(model);
			
			DefaultListModel listModel = new DefaultListModel();
			rowHeader.setModel(listModel);
			
			pgSelect db = new pgSelect();
			String SQL = "select * from view_hdmf_msvs_approval('"+co_id+"', '"+proj_id+"', '"+phase+"', '"+date_cut_off+"', true);";
			db.select(SQL);

			System.out.println("");
			System.out.println("Display MSVS for Approval");
			
			if(db.isNotNull()){
				for(int x = 0; x<db.getRowCount(); x++){
					model.addRow(db.getResult()[x]);
					listModel.addElement(model.getRowCount());
				}
			}
		}
	}
	
	public static void displayMSVSReverified(String co_id, String proj_id, String phase, Date date_cut_off, model_hdmf_msvs_reverified model, JList rowHeader, Boolean blnDo){
		if(model != null && rowHeader != null){
			FncTables.clearTable(model);
			
			DefaultListModel listModel = new DefaultListModel();
			rowHeader.setModel(listModel);
			
			pgSelect db = new pgSelect();
			String SQL = "select "+blnDo+", phase, block, lot, proj_alias, last_name, first_name, middle_name, \n" + 
				"CURRENT_DATE::date, CURRENT_DATE::date, entity_id, projcode as proj_id, pbl_id, seq_no \n" + 
				"from view_hdmf_msvs_reverified('"+co_id+"', '"+proj_id+"', '"+phase+"', '"+date_cut_off+"', "+blnDo+");";
			db.select(SQL);
			
			System.out.println("");
			System.out.println(SQL);
			
			if(db.isNotNull()){
				for(int x = 0; x<db.getRowCount(); x++){
					model.addRow(db.getResult()[x]);
					listModel.addElement(model.getRowCount());
				}
			}
		}
	}
	
	private static String sql_getNextBatchNo() {
		String rplf = "";
		String SQL = "select trim(to_char(max(coalesce(trans_no::int,0))+1,'000000')) \n" +
				"from rf_buyer_status \n" +
				"where trans_no is not null and char_length(trans_no) = 6 AND trans_no !~*'(STBAUB|MANUAL|BRIDGE)'";

		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			if ((String) db.getResult()[0][0]==null||db.getResult()[0][0].equals("null")) {
				rplf = "000000001";
			} else {
				rplf = (String) db.getResult()[0][0];
			}
		} else {
			rplf = "000000001";
		}
		return rplf;
	}
	
	public static void saveMSVSApproval(model_hdmf_msvs_reverified model, Date dteDate){
		Boolean blnWith = false;
		pgUpdate db = new pgUpdate();
		
		SimpleDateFormat sdfTo = new SimpleDateFormat("MM-dd-yyyy");
		String strDate = (String) sdfTo.format(dteDate);
		
		for (int x= 0; x < model.getRowCount(); x++) {
			if((Boolean) model.getValueAt(x, 0)){
				blnWith = true;
				
				String entity_id = (String) model.getValueAt(x, 18);
				String proj_id = (String) model.getValueAt(x, 19);
				String pbl_id = (String) model.getValueAt(x, 20);
				Integer seq_no = (Integer) model.getValueAt(x, 21);

				String SQL = "insert into dm_gen_findings (entity_id, proj_id, pbl_id, seq_no, date_eval, eval_by, gen_findings, status_id, created_by) \n" +
					"values ('"+entity_id+"', '"+proj_id+"', '"+pbl_id+"', "+seq_no.toString()+", '"+strDate+"'::date, \n" +
					"'"+UserInfo.Alias+"', 'MSVS Approved', 'A', '"+UserInfo.EmployeeCode+"');";
				
				System.out.println("");
				System.out.println("MSVS Approval");
				
				db.executeUpdate(SQL, false);
				db.commit();
			}
		}
		
		if (blnWith) {
			JOptionPane.showMessageDialog(null, "Approved MSVS successfully posted.");
			
			for (int x= 0; x < model.getRowCount(); x++) {
				if(!(Boolean) model.getValueAt(x, 0)){
					model.removeRow(x);
					x = 0;
				}
			}
		} else {
			JOptionPane.showMessageDialog(null, "No row was selected.");
		}
	}
	
	public static void saveMSVSReverified(model_hdmf_msvs_reverified model, Date dteDate){
		Boolean blnWith = false;
		
		//SimpleDateFormat sdfTo = new SimpleDateFormat("MM-dd-yyyy");
		//String strDate = (String) sdfTo.format(FncGlobal.getDateToday());
		
		for (int x= 0; x < model.getRowCount(); x++) {
			if((Boolean) model.getValueAt(x, 0)){
				blnWith = true;
				pgUpdate db = new pgUpdate();
				
				String entity_id = (String) model.getValueAt(x, 10);
				String proj_id = (String) model.getValueAt(x, 11);
				String pbl_id = (String) model.getValueAt(x, 12);
				Integer seq_no = (Integer) model.getValueAt(x, 13);
				String strActual = (String) model.getValueAt(x, 8).toString();
				String strDate = (String) model.getValueAt(x, 9).toString();

				String SQL = "update rf_buyer_status \n" + 
				"set status_id = 'I' \n" +
				"where byrstatus_id = '96' and entity_id = '"+entity_id+"' and proj_id = '"+proj_id+"' \n" + 
				"and pbl_id = '"+pbl_id+"' and seq_no::int = '"+seq_no+"'::int and status_id = 'A' \n" +
				"and tran_date::date <= now()::date";
				
				System.out.println("");
				System.out.println("Deactivate previous MSVS Reverified");
				System.out.println(SQL);
				
				db.executeUpdate(SQL, false);
				db.commit();
				
				db = new pgUpdate();
				SQL = "insert into rf_buyer_status values (" +
						"'"+entity_id+"', \n" +  						//entity_id
						"'"+proj_id+"', \n" +							//proj_id
						"'"+pbl_id+"', \n" +							//pbl_id
						"'"+seq_no+"', \n" +							//seq_no
						"'96', \n" +									//byrstatus_id
						"'"+strDate+"'::date, \n" +						//tran_date
						"'"+strActual+"'::date, \n" +					//actual_date
						"''," +											//request_no
						"null," +										//inactive_date
						"'A', \n" +										//status_id
						"'"+sql_getNextBatchNo()+"'," +					//trans_no
						"null," +										//jv_no
						"null," +										//to_mi_mo_projcode
						"null," +										//to_mi_mo_pbl_id
						"'01'," +										//branch_id
						"'"+UserInfo.EmployeeCode+"', \n" + 			//created_by
						"null)";										//ntc_batchno
				
				System.out.println("");
				System.out.println("MSVS Reverified");
				System.out.println(SQL);
				
				db.executeUpdate(SQL, false);
				db.commit();
			}
		}
		
		if (blnWith) {
			JOptionPane.showMessageDialog(null, "Reverified MSVS successfully posted.");
			
			for (int x= 0; x < model.getRowCount(); x++) {
				if(!(Boolean) model.getValueAt(x, 0)){
					model.removeRow(x);
					x = 0;
				}
			}
			
			for (int x= 0; x < model.getRowCount(); x++) {
				if(!(Boolean) model.getValueAt(x, 0)){
					model.removeRow(x);
					x = 0;
				}
			}
		} else {
			JOptionPane.showMessageDialog(null, "No row was selected.");
		}
	}
	
	public static void displayForTaggingOfCOA(model_hdmf_postInspection model, JList rowHeader, String strBatch, String strCoID, String strProID, String strPhase) {
		FncTables.clearTable(model);
		DefaultListModel listModel = new DefaultListModel();
		rowHeader.setModel(listModel);
		
		pgSelect db = new pgSelect();
		String SQL = "";
		
		if (strBatch=="") {
			SQL = "select trim(b.entity_name) as name, true as tag, c.phase, c.block, c.lot, \n" + 
				"a.trans_date::date, d.status_desc, a.hdmf_rep, a.remarks, a.pbl_id \n" + 
				"from rf_hdmf_insp_detail a \n" + 
				"inner join rf_entity b on a.entity_id = b.entity_id \n" + 
				"inner join mf_unit_info c on a.pbl_id = c.pbl_id \n" + 
				"inner join mf_house_inspection_status d on a.insp_status_id = d.status_id \n" + 
				"where a.insp_status_id = '03' and not exists (select x.entity_id from rf_buyer_status x where x.entity_id = a.entity_id and x.pbl_id = a.pbl_id and x.byrstatus_id = '58' and x.status_id = 'A') \n" +  
				"and ((select COALESCE(x.trans_no, '') from rf_buyer_status x where x.byrstatus_id = '58' and x.entity_id = a.entity_id and x.pbl_id = a.pbl_id and x.status_id = 'A' order by x.trans_no desc limit 1) = '"+strBatch+"' or '' = '"+strBatch+"') \n" +
				"and (case when nullif('"+strCoID+"','') is null then true else '"+strCoID+"' in (select co_id from mf_project where proj_id = c.proj_id) end) \n" + 
				"and (case when nullif('"+strProID+"','') is null then true else c.proj_id = '"+strProID+"' end) \n" +
				"and (case when nullif(trim('"+strPhase+"'),'') is null then true else c.phase = trim('"+strPhase+"') end) \n" +
				"order by trim(b.entity_name)";
		} else {
			SQL = "select trim(b.entity_name) as name, true as tag, c.phase, c.block, c.lot, \n" + 
				"a.trans_date::date, d.status_desc, a.hdmf_rep, a.remarks, a.pbl_id\n" + 
				"from rf_hdmf_insp_detail a \n" + 
				"inner join rf_entity b on a.entity_id = b.entity_id \n" + 
				"inner join mf_unit_info c on a.pbl_id = c.pbl_id \n" + 
				"inner join mf_house_inspection_status d on a.insp_status_id = d.status_id \n" + 
				"where a.insp_status_id = '03' and exists (select x.entity_id from rf_buyer_status x where x.entity_id = a.entity_id and x.pbl_id = a.pbl_id and x.byrstatus_id = '58' and x.status_id = 'A') \n" +  
				"and ((select COALESCE(x.trans_no, '') from rf_buyer_status x where x.byrstatus_id = '58' and x.entity_id = a.entity_id and x.pbl_id = a.pbl_id and x.status_id = 'A' order by x.trans_no desc limit 1) = '"+strBatch+"') \n" +
				"and (case when nullif('"+strCoID+"','') is null then true else '"+strCoID+"' in (select co_id from mf_project where proj_id = c.proj_id) end) \n" + 
				"and (case when nullif('"+strProID+"','') is null then true else c.proj_id = '"+strProID+"' end) \n" +
				"and (case when nullif(trim('"+strPhase+"'),'') is null then true else c.phase = trim('"+strPhase+"') end) \n" +
				"order by trim(b.entity_name)";
		}
		
		db.select(SQL);
		
		System.out.println("");
		System.out.println(SQL);
		System.out.println("Rowcount: " + db.getRowCount());
		
		if(db.isNotNull()){
			for(int x = 0; x<db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
				listModel.addElement(model.getRowCount());
			}
		}
	}
	
	public static String saveCOA(model_hdmf_postInspection model, Date dteDate) {
		Boolean blnWith = false;
		pgUpdate db = new pgUpdate();
		String strBatch = FncGlobal.GetString("select LPAD((coalesce(MAX(trans_no::int), 0) + 1)::TEXT, 6, '0'::TEXT) as batch_no from rf_buyer_status where byrstatus_id = '58' and nullif(TRIM(trans_no),'') is not null"); 
		
		for (int x= 0; x < model.getRowCount(); x++) {
			if((Boolean) model.getValueAt(x, 1)){
				db = new pgUpdate();
				blnWith = true;
				String strEnt = GetValue("select entity_id from rf_entity where entity_name ~* '"+model.getValueAt(x, 0)+"' and status_id != 'I'");
				String strProj = GetValue("select proj_id from mf_unit_info where pbl_id = '"+model.getValueAt(x, 9)+"' and phase = '"+model.getValueAt(x, 2)+"' and block = '"+model.getValueAt(x, 3)+"' and lot = '"+model.getValueAt(x, 4)+"' and status_id != 'I'");
				String strUnit = GetValue("select pbl_id from mf_unit_info where pbl_id = '"+model.getValueAt(x, 9)+"' and phase = '"+model.getValueAt(x, 2)+"' and block = '"+model.getValueAt(x, 3)+"' and lot = '"+model.getValueAt(x, 4)+"' and status_id != 'I'");
				String strSeq = GetValue("select trim(seq_no::varchar(20)) from rf_sold_unit where entity_id = '"+strEnt+"' and projcode = '"+strProj+"' and pbl_id = '"+strUnit+"' and status_id != 'I' and currentstatus is not null ");
				String strDate = GetValue("select trim(trans_date::date::varchar(20)) from rf_hdmf_insp_detail where entity_id = '"+strEnt+"' and pbl_id = '"+strUnit+"' and insp_status_id = '03'");

				System.out.println("");
				System.out.println("Name: " + model.getValueAt(x, 0));
				System.out.println("ID: " + strEnt);
				System.out.println("Project ID: " + strProj);
				System.out.println("Unit ID: " + strUnit);
				System.out.println("Sequence: " + strSeq);
				System.out.println("Date: " + strDate);
				System.out.println("Batch: " + strBatch);
				
				String SQL = "insert into rf_buyer_status values (" +
						"'"+strEnt+"', \n" +  							//entity_id
						"'"+strProj+"', \n" +							//proj_id
						"'"+strUnit+"', \n" +							//pbl_id
						""+strSeq+", \n" +								//seq_no
						"'58', \n" +									//byrstatus_id
						"CURRENT_DATE::date, \n" +						//tran_date
						"'"+strDate+"'::date, \n" +						//actual_date
						"'', \n" +										//request_no
						"null, \n" +									//inactive_date
						"'A', \n" +										//status_id
						"'"+strBatch+"', \n" +							//trans_no
						"null, \n" +									//jv_no
						"null, \n" +									//to_mi_mo_projcode
						"null, \n" +									//to_mi_mo_pbl_id
						"'01', \n" +									//branch_id
						"'"+UserInfo.EmployeeCode+"', \n" + 			//created_by
						"null)";										//ntc_batchno

				db.executeUpdate(SQL, false);
				db.commit();
			}
		}
		
		if (blnWith) {
			JOptionPane.showMessageDialog(null, "Certificate Of Appraisal successfully posted.");
			
			for (int x= 0; x < model.getRowCount(); x++) {
				if(!(Boolean) model.getValueAt(x, 1)){
					model.removeRow(x);
					x = 0;
				}
			}
			
			for (int x= 0; x < model.getRowCount(); x++) {
				if(!(Boolean) model.getValueAt(x, 1)){
					model.removeRow(x);
					x = 0;
				}
			}
		} else {
			strBatch = "";
			JOptionPane.showMessageDialog(null, "No row was selected.");
		}
		return strBatch;
	}
	
	public static String CreateNotice(model_hdmf_FirstFiling model,String module_batch_no) {
		String strBat = FncGlobal.GetString("select trim(to_char((max(batch_no::int) + 1), '0000000000')) from rf_client_notices");
		
		for (int x = 0; x < model.getRowCount(); x++) {
			Boolean isSelected = (Boolean) model.getValueAt(x, 1);
			if (isSelected) {
				String strLot = FncGlobal.GetString("select (case when position('/' in '"+(String) model.getValueAt(x, 5).toString()+"') > 0 then left('"+(String) model.getValueAt(x, 5).toString()+"', position('/' in '"+(String) model.getValueAt(x, 5).toString()+"') - 1) else left('"+(String) model.getValueAt(x, 5).toString()+"', 3) end)");
				
				String entity_id = GetValue("select entity_id from rf_entity where trim(entity_name) = '"+(String) model.getValueAt(x, 0)+"' and status_id = 'A'");
				String proj_id = GetValue("select proj_id from mf_project where trim(proj_name) = '"+(String) model.getValueAt(x, 2)+"'");
				String pbl_id = GetValue("select pbl_id from mf_unit_info where proj_id = '"+proj_id+"' and phase = '"+(String) model.getValueAt(x, 3)+"' and block = '"+(String) model.getValueAt(x, 4)+"' and lot = '"+strLot+"'");
				String strSeq = GetValue("select trim(seq_no::char(5)) from rf_sold_unit where entity_id = '"+entity_id+"' and projcode = '"+proj_id+"' and pbl_id = '"+pbl_id+"' and status_id = 'A'");
				Integer intRec = FncGlobal.GetInteger("select (max(rec_id::int) + 1) from rf_client_notices");
				
				System.out.println("");
				System.out.println("entity_id: " + entity_id);
				System.out.println("entity_id: " + entity_id);
				System.out.println("proj_id: " + proj_id);
				System.out.println("pbl_id: " + pbl_id);
				System.out.println("strSeq: " + strSeq);
				
				pgUpdate db = new pgUpdate();
				String sql = "insert into rf_client_notices (rec_id, entity_id, projcode, pbl_id, seq_no, part_id, notice_id, stage_id, dateprep, preparedby, batch_no, status_id,module_batch_no) \n" +
						"values ('"+intRec+"'::int, '"+entity_id+"', '"+proj_id+"', '"+pbl_id+"', "+strSeq+"::int, '013', \n" +
						"'139', 1, Now(), '"+UserInfo.EmployeeCode+"', '"+strBat+"', 'A','"+module_batch_no+"');";
				
				System.out.println(sql);
				db.executeUpdate(sql, false);
				db.commit();
			}
		}
		return strBat;
	}

    public static void DeleteRows(DefaultTableModel modelMain, Integer intCol) {
		for (int x = 0; x < modelMain.getRowCount(); x++) {
			if (!(Boolean) modelMain.getValueAt(x, intCol)) {
				modelMain.removeRow(x);
				x = -1;
			}
		}
    }
    
	public static void displayForTaggingOfNOASigned(String co_id, String proj_id, String phase, model_hdmf_NOASigning model, JList rowHeader) {
		FncTables.clearTable(model);
		DefaultListModel listModel = new DefaultListModel();
		rowHeader.setModel(listModel);
		
/*		pgSelect db = new pgSelect();
		String SQL = "select false tag, b.entity_name as name, c.description as unit, \n" + 
				"(select x.loanable_amount::NUMERIC(19, 2) from rf_pagibig_computation x where x.entity_id = a.entity_id \n" + 
				"and x.proj_id = a.proj_id and x.pbl_id = a.pbl_id and x.seq_no::int = a.seq_no::int and x.status_id = 'A') as loanable_amount, \n" + 
				"a.tran_date::date as released_date, a.actual_date::date, a.entity_id, a.proj_id, a.pbl_id, a.seq_no\n" + 
				"from (select * from rf_buyer_status x where x.byrstatus_id = '35' and x.status_id = 'A') a \n" + 
				"inner join rf_entity b on a.entity_id = b.entity_id\n" + 
				"inner join mf_unit_info c on a.proj_id = c.proj_id and a.pbl_id = c.pbl_id\n" +
				"inner join rf_sold_unit d on a.entity_id = d.entity_id and a.proj_id = d.projcode and a.pbl_id = d.pbl_id and a.seq_no = d.seq_no and d.status_id = 'A' \n" +
				"where (select count(*) from rf_buyer_status x where x.entity_id = a.entity_id and x.proj_id = a.proj_id \n" + 
				"and x.pbl_id = a.pbl_id and x.seq_no = a.seq_no and x.byrstatus_id = '49' and x.status_id = 'A') = 0";
		db.select(SQL);*/
		pgSelect db = new pgSelect();
		String SQL = "select * from view_hdmf_noa_signed('"+co_id+"', '"+proj_id+"', '"+phase+"');";
		db.select(SQL);

		System.out.println("");
		System.out.println("Display NOA Signed");
		System.out.println("Rowcount: " + db.getRowCount());
		
		if(db.isNotNull()){
			for(int x = 0; x<db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
				listModel.addElement(model.getRowCount());
			}
		}
	}
	
	public static void saveNOASigned(model_hdmf_NOASigning model, Date dteTrans) {
		ArrayList<String> listEntityID = new ArrayList<String>();
		ArrayList<String> listProjID = new ArrayList<String>();
		ArrayList<String> listPBL = new ArrayList<String>();
		ArrayList<Integer> listSeq = new ArrayList<Integer>();
		
		for(int x= 0; x<model.getRowCount(); x++){
			Boolean isSelected = (Boolean) model.getValueAt(x, 0);
			
			if(isSelected){
				String entity_id = (String) model.getValueAt(x, 6);
				String proj_id = (String) model.getValueAt(x, 7);
				String pbl_id = (String) model.getValueAt(x, 8);
				Integer seq_no = (Integer) model.getValueAt(x, 9);
				
				listProjID.add(String.format("'%S'", proj_id));
				listPBL.add(String.format("'%s'", pbl_id));
				listSeq.add(seq_no);
				listEntityID.add(String.format("'%s'", entity_id));
			}
		}
		
		pgSelect db = new pgSelect();
		String SQL = "SELECT sp_tag_noa_signed(ARRAY"+ listEntityID +"::VARCHAR[], \n" +
					 "ARRAY"+ listProjID +"::VARCHAR[], \n" +
					 "ARRAY"+ listPBL +"::VARCHAR[], \n" +
					 "ARRAY"+ listSeq +"::INTEGER[], \n" +
					 "NULLIF('"+dteTrans+"', 'null')::TIMESTAMP, \n"+
					 "'"+ UserInfo.EmployeeCode +"');";
		
		db.select(SQL);
		
		FncSystem.out("Display SQL For NOA Signing", SQL);
	
	}
	
	public static void displayNOAExtension(String strCoID, String strProID, model_hdmf_noaextension model, JList rowHeader){
		if(model != null && rowHeader != null){
			
			FncTables.clearTable(model);
			DefaultListModel listModel = new DefaultListModel();
			rowHeader.setModel(listModel);
			
			pgSelect db = new pgSelect();
			
			String SQL = "select * from view_hdmf_noa_extension('"+strCoID+"', '"+strProID+"') order by c_noa_released_date; ";
			db.select(SQL);
			
			FncSystem.out("Display SQL For Expiring NOA", SQL);
			
			if(db.isNotNull()){
				for(int x = 0; x<db.getRowCount(); x++){
					model.addRow(db.getResult()[x]);
					listModel.addElement(model.getRowCount());
				}
			}
		}
	}
	
	public static Boolean saveNOAExtension(model_hdmf_noaextension model){
		pgUpdate db = new pgUpdate();
		Boolean blnSaved = false; 
		Boolean blnProceed = true; 
		for (int x = 0; x < model.getRowCount(); x++) {
			if ((model.getValueAt(x, 6)==null) && (Boolean) model.getValueAt(x, 1)==true) {
				blnProceed = false;
			}
		}
		
		System.out.println("");
		System.out.println("blnProceed: " + blnProceed);
		
		if (blnProceed) {
			for(int x = 0; x < model.getRowCount(); x++){
				Boolean isSelected = (Boolean) model.getValueAt(x, 1);
				
				if (isSelected) {
					System.out.println("");
					System.out.println("Date: " + model.getValueAt(x, 6));

					String entity_id = (String) model.getValueAt(x, 7);
					String proj_id = (String) model.getValueAt(x, 8);
					String pbl_id = (String) model.getValueAt(x, 9);
					String strSeq = (String) model.getValueAt(x, 10).toString();
					
					System.out.println("");
					System.out.println("entity_id: " + entity_id);
					System.out.println("proj_id: " + proj_id);
					System.out.println("pbl_id: " + pbl_id);
					System.out.println("strSeq: " + strSeq);
					
					System.out.println("insert into rf_buyer_status \n" +
						"(entity_id, proj_id, pbl_id, seq_no, byrstatus_id, tran_date, actual_date, status_id, created_by) \n" +
						"values ('"+entity_id+"', '"+proj_id+"', '"+pbl_id+"', '"+strSeq+"'::int, '98', now(), '"+model.getValueAt(x, 6)+"'::timestamp, 'A', '"+UserInfo.EmployeeCode+"')");
					
					db = new pgUpdate();
					db.executeUpdate("insert into rf_buyer_status \n" +
							"(entity_id, proj_id, pbl_id, seq_no, byrstatus_id, tran_date, actual_date, status_id, created_by) \n" +
							"values ('"+entity_id+"', '"+proj_id+"', '"+pbl_id+"', '"+strSeq+"'::int, '98', now(), '"+model.getValueAt(x, 6)+"', 'A', '"+UserInfo.EmployeeCode+"')", false);
					db.commit();
				}
			}
			blnSaved = true;
			JOptionPane.showMessageDialog(null, "All accounts are posted.");
		} else {
			blnSaved = false;
			JOptionPane.showMessageDialog(null, "Please set all dates.");
		}
		
		return blnSaved; 
	}
	
	public static void displayForTaggingOfLoanReturnedPAR(model_hdmf_loanReturned model, JList rowHeader) {
		FncTables.clearTable(model);
		DefaultListModel listModel = new DefaultListModel();
		rowHeader.setModel(listModel);
		
		pgSelect db = new pgSelect();
		String SQL = "select false tag, b.entity_name as name, c.description as unit, \n" + 
				"(select x.loanable_amount::NUMERIC(19, 2) from rf_pagibig_computation x where x.entity_id = a.entity_id \n" + 
				"and x.proj_id = a.proj_id and x.pbl_id = a.pbl_id and x.seq_no::int = a.seq_no::int and x.status_id = 'A') as loanable_amount, \n" + 
				"a.tran_date::date as released_date, a.actual_date::date, a.entity_id, a.proj_id, a.pbl_id, a.seq_no \n" + 
				"from (select * from rf_buyer_status x where x.byrstatus_id = '31' and x.status_id = 'A') a \n" + 
				"inner join rf_entity b on a.entity_id = b.entity_id \n" + 
				"INNER JOIN mf_unit_info c on a.proj_id = c.proj_id and a.pbl_id = c.pbl_id \n" + 
				"where (select count(*) from rf_buyer_status x where x.entity_id = a.entity_id and x.proj_id = a.proj_id \n" + 
				"and x.pbl_id = a.pbl_id and x.seq_no = a.seq_no and x.byrstatus_id = '32' and x.status_id = 'A') = 0";

		db.select(SQL);
		
		System.out.println("");
		System.out.println(SQL);
		System.out.println("Rowcount: " + db.getRowCount());
		
		if(db.isNotNull()){
			for(int x = 0; x<db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
				listModel.addElement(model.getRowCount());
			}
		}
	}
	
	public static String CreateNoticeReturnedFromHDMF(model_hdmf_loanReturned model, String strNotice) {
		String strBat = FncGlobal.GetString("select trim(to_char((max(batch_no::int) + 1), '0000000000')) from rf_client_notices");
		
		for (int x = 0; x < model.getRowCount(); x++) {
			Boolean isSelected = (Boolean) model.getValueAt(x, 0);
			if (isSelected) {
				String strLot = FncGlobal.GetString("select left('"+(String) model.getValueAt(x, 5).toString()+"', 2)");
				
				String entity_id = (String) model.getValueAt(x, 5);
				String proj_id = (String) model.getValueAt(x, 6);
				String pbl_id = (String) model.getValueAt(x, 7);
				String strSeq = (String) model.getValueAt(x, 8).toString();
				Integer intRec = FncGlobal.GetInteger("select (max(rec_id::int) + 1) from rf_client_notices");
				
				System.out.println("");
				System.out.println("entity_id: " + entity_id);
				System.out.println("proj_id: " + proj_id);
				System.out.println("pbl_id: " + pbl_id);
				System.out.println("strSeq: " + strSeq);
				
				pgUpdate db = new pgUpdate();
				String sql = "insert into rf_client_notices (rec_id, entity_id, projcode, pbl_id, seq_no, part_id, notice_id, stage_id, dateprep, preparedby, batch_no, status_id) \n" +
						"values ('"+intRec+"'::int, '"+entity_id+"', '"+proj_id+"', '"+pbl_id+"', "+strSeq+"::int, '013', \n" +
						"'"+strNotice+"', 1, Now(), '"+UserInfo.EmployeeCode+"', '"+strBat+"', 'A');";
				
				System.out.println(sql);
				db.executeUpdate(sql, false);
				db.commit();
			}
		}
		return strBat;
	}
	
	static void GenerateHDMFReturnedNotice(String strBat) {
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("batch_no", strBat);
		mapParameters.put("company", "CENQHOMES DEVELOPMENT CORPORATION");
		mapParameters.put("company_address", "");
		mapParameters.put("notice_type_id", "130");
		mapParameters.put("prepared_by", _RealTimeDebit.GetName(UserInfo.EmployeeCode));
		mapParameters.put("prepared_by_code", UserInfo.EmployeeCode);
		FncReport.generateReport("/Reports/rpt_hdmf_ReturnedHDMF_FirstNotice.jasper", "Returned from HDMF First Notice", "", mapParameters);
	}
	
	static void GenerateHDMFReturnedNoticeTransmittal(String strBat) {	
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("01_Company", "CENQHOMES DEVELOPMENT CORPORATION");
		mapParameters.put("02_BatchNo", strBat);
		mapParameters.put("03_UserName", _RealTimeDebit.GetName(UserInfo.EmployeeCode));
		mapParameters.put("04_UserCode", FncGlobal.GetString("select login_id from rf_system_user where emp_code = '"+UserInfo.EmployeeCode+"'"));
		mapParameters.put("05_NoticeType", "First Notice - Returned from HDMF");
		FncReport.generateReport("/Reports/rpt_HDMFReturnedNoticeTransmittal.jasper", "Returned from HDMF Notice Transmittal", "", mapParameters);
	}
	
	static void GenerateHDMFReturnedNoticePhilPostTransmittal(String strBat) {
		pgSelect dbExec = new pgSelect();
		String strSQL = "select distinct region::varchar(155) from view_notice_transmittal('"+strBat+"')";
		dbExec.select(strSQL);
		
		System.out.println("");
		System.out.println("I went here!");
		
		if (dbExec.getRowCount() > 1) {
			for (int x = 0; x < dbExec.getRowCount(); x++) {
				System.out.println("");
				System.out.println("dbExec.getResult()[x]: " + dbExec.getResult()[x][0]);
				
				Map<String, Object> mapParameters = new HashMap<String, Object>();
				mapParameters.put("01_Company", "CENQHOMES DEVELOPMENT CORPORATION");
				mapParameters.put("02_BatchNo", strBat);
				mapParameters.put("03_UserName", _RealTimeDebit.GetName(UserInfo.EmployeeCode));
				mapParameters.put("04_AsOfDate", FncGlobal.getDateSQL().toString());
				mapParameters.put("05_NoticeType", "First Notice - Returned from HDMF");
				mapParameters.put("06_region", dbExec.getResult()[x][0]);
				FncReport.generateReport("/Reports/rpt_HDMFReturnedNoticePhilPostTransmittal.jasper", "Returned from HDMF Notice PhilPost Transmittal - " + dbExec.getResult()[x][0], "", mapParameters);		
			}
		} else {
			Map<String, Object> mapParameters = new HashMap<String, Object>();
			mapParameters.put("01_Company", "CENQHOMES DEVELOPMENT CORPORATION");
			mapParameters.put("02_BatchNo", strBat);
			mapParameters.put("03_UserName", _RealTimeDebit.GetName(UserInfo.EmployeeCode));
			mapParameters.put("04_AsOfDate", FncGlobal.getDateSQL().toString());
			mapParameters.put("05_NoticeType", "First Notice - Returned from HDMF");
			mapParameters.put("06_region", dbExec.getResult()[0][0]);
			FncReport.generateReport("/Reports/rpt_HDMFReturnedNoticePhilPostTransmittal.jasper", "Returned from HDMF Notice PhilPost Transmittal - " + dbExec.getResult()[0][0], "", mapParameters);
		}
	}
	
	static void displayForTaggingOfCI(model_hdmf_ci_tagging model, JList rowHeader, String strBatch) {
		FncTables.clearTable(model);
		DefaultListModel listModel = new DefaultListModel();
		rowHeader.setModel(listModel);
		
		pgSelect db = new pgSelect();
		String SQL = "select false as tag, * from view_hdmf_ci_tagging('' ,'' , '"+strBatch+"'); ";
		db.select(SQL);

		System.out.println("");
		System.out.println(SQL);
		System.out.println("Rowcount: " + db.getRowCount());
		
		if(db.isNotNull()){
			for(int x = 0; x<db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
				listModel.addElement(model.getRowCount());
			}
		}
	}
	
	public static String saveCI(model_hdmf_ci_tagging model, Date dteDate){
		pgUpdate db = new pgUpdate();
		String strBatch = "";  
	
		strBatch = FncGlobal.GetString("select LPAD((coalesce((select trans_no from rf_buyer_status where byrstatus_id = '136' and status_id = 'A' order by trans_no desc limit 1), '0')::int + 1::int)::varchar, 10, '0')"); 
	
		for (int x = 0; x < model.getRowCount(); x++) {
			Boolean isSelected = (Boolean) model.getValueAt(x, 0);
			
			if (isSelected) {
				String entity_id = (String) model.getValueAt(x, 5);
				String proj_id = (String) model.getValueAt(x, 6);
				String pbl_id = (String) model.getValueAt(x, 7);
				String strSeq = (String) model.getValueAt(x, 8).toString();

				db = new pgUpdate();
				db.executeUpdate("update rf_buyer_status \n" + 
						"set status_id = 'I' \n" + 
						"where entity_id = '"+entity_id+"' and proj_id = '"+proj_id+"' and pbl_id = '"+pbl_id+"' \n" + 
						"and seq_no::int = '"+strSeq+"'::int and byrstatus_id = '136' and status_id = 'A' and tran_date < now()", false);
				db.commit();

				db = new pgUpdate();
				db.executeUpdate("insert into rf_buyer_status \n" +
						"(entity_id, proj_id, pbl_id, seq_no, byrstatus_id, tran_date, actual_date, status_id, trans_no, created_by) \n" +
						"values ('"+entity_id+"', '"+proj_id+"', '"+pbl_id+"', '"+strSeq+"'::int, '136', now(), '"+model.getValueAt(x, 4).toString()+"'::timestamp, \n" +
						"'A', '"+strBatch+"', '"+UserInfo.EmployeeCode+"')", false);
				db.commit();
			}
		}

		JOptionPane.showMessageDialog(null, "All accounts are posted.");
		
		return strBatch; 
	}
	
	public static void DisplayFirstFiling(DefaultTableModel modelMain, JList rowHeader, String strCoID, String strProID, String strPhase, String strDate, Boolean blnTag) {
		FncTables.clearTable(modelMain);
		DefaultListModel listModel = new DefaultListModel();
		rowHeader.setModel(listModel); 
		
		String SQL = "SELECT * FROM view_hdmf_first_filing('"+strCoID+"', '"+strProID+"', '"+strPhase+"', '"+strDate+"'::date, "+blnTag+");";	

		System.out.println("");
		System.out.println(SQL);

		pgSelect db = new pgSelect();
		db.select(SQL);
		if (db.isNotNull()) {
			for (int x = 0; x < db.getRowCount(); x++) {
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());
			}
		} else {
			JOptionPane.showMessageDialog(null, "No records were returned.");
		};
	}
	
	public static void DisplayFirstFilingWithBatch(DefaultTableModel modelMain, JList rowHeader, String strBat) {
		FncTables.clearTable(modelMain);
		DefaultListModel listModel = new DefaultListModel();
		rowHeader.setModel(listModel); 
		
		String SQL = "";
		
		SQL = "SELECT * FROM view_hdmf_first_filing('"+strBat+"');";	

		System.out.println("");
		System.out.println(SQL);

		pgSelect db = new pgSelect();
		db.select(SQL);
		if (db.isNotNull()) {
			for (int x = 0; x < db.getRowCount(); x++) {
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());
			}
		} else {
			JOptionPane.showMessageDialog(null, "No records were returned.");
		};
	}
	
	
	/*	Added by Mann2x; Date Added: December 2016; First filing status; Mann2x's Mark	*/
	public static String saveFFStatus(Date date_cutoff, model_hdmf_FirstFiling model){
		String strSQL = ""; 
		String strBatch = FncGlobal.GetString("select concat('QF', right(date_part('year', now())::text, 2), LPAD(date_part('month', now())::text, 2, '0'::text), \n" + 
				"LPAD(date_part('day', now())::text, 2, '0'::text), LPAD((coalesce(max(right(batch_no, 2))::int, 0)+1)::text, 2, '0'::text))\n" + 
				"from rf_hdmf_status_other_values \n" + 
				"where actual_date::date = now()::Date and byrstatus_id = 'QF'"); 
		
		for (int x = 0; x < model.getRowCount(); x++) {
			Boolean isSelected = (Boolean) model.getValueAt(x, 1);
			
			if (isSelected) {
				String strLot = FncGlobal.GetString("select (case when position('/' in '"+(String) model.getValueAt(x, 5).toString()+"') > 0 then left('"+(String) model.getValueAt(x, 5).toString()+"', position('/' in '"+(String) model.getValueAt(x, 5).toString()+"') - 1) else left('"+(String) model.getValueAt(x, 5).toString()+"', 3) end)");
				String entity_id = GetValue("select entity_id from rf_entity where trim(entity_name) = '"+(String) model.getValueAt(x, 0)+"' and status_id = 'A'");
				String proj_id = GetValue("select proj_id from mf_project where trim(proj_name) = '"+(String) model.getValueAt(x, 2)+"'");
				String pbl_id = GetValue("select pbl_id from mf_unit_info where proj_id = '"+proj_id+"' and phase = '"+(String) model.getValueAt(x, 3)+"' and block = '"+(String) model.getValueAt(x, 4)+"' and lot = '"+strLot+"'");
				String strSeq = GetValue("select trim(seq_no::char(5)) from rf_sold_unit where entity_id = '"+entity_id+"' and projcode = '"+proj_id+"' and pbl_id = '"+pbl_id+"' and status_id = 'A' and currentstatus is not null" );

				System.out.println("");
				System.out.println("Name: " + (String) model.getValueAt(x, 0));
				System.out.println("entity_id: " + entity_id);
				System.out.println("proj_id: " + proj_id);
				System.out.println("pbl_id: " + pbl_id);
				System.out.println("strSeq: " + strSeq);
				System.out.println("phase: " + (String) model.getValueAt(x, 3).toString());
				System.out.println("block: " + (String) model.getValueAt(x, 4).toString());
				System.out.println("lot: " + strLot);

				

				pgUpdate db = new pgUpdate();
				strSQL = "insert into dm_gen_findings (entity_id, proj_id, pbl_id, seq_no, date_eval, eval_by, gen_findings, status_id, created_by) \n" +
						"values ('"+entity_id+"', '"+proj_id+"', '"+pbl_id+"', "+strSeq+", '"+date_cutoff+"'::date, '"+UserInfo.Alias+"', '"+(String) model.getValueAt(x, 11).toString().trim()+"', 'A', '"+UserInfo.EmployeeCode+"');";

				System.out.println("");
				System.out.println(strSQL);
				
				db.executeUpdate(strSQL, false);
				db.commit();
				
				strSQL = "insert into rf_hdmf_status_other_values (entity_id, proj_id, pbl_id, seq_no, remarks, byrstatus_id, batch_no, status_id, actual_date) \n" +
						"values ('"+entity_id+"', '"+proj_id+"', '"+pbl_id+"', '"+strSeq+"'::int, '"+(String) model.getValueAt(x, 11).toString().trim()+"', 'QF', '"+strBatch+"', 'A', '"+date_cutoff+"'::timestamp)";
				
				System.out.println("");
				System.out.println(strSQL);
				
				db = new pgUpdate();
				db.executeUpdate(strSQL, false);
				db.commit();
			}
		}
		
		return strBatch; 
	}
	
	public static String saveFFFStatus(Date date_cutoff, model_hdmf_FirstFiling model,_JLookup txtbatch) {
		String strSQL = ""; 
		
		String strBatch = FncGlobal.GetString("select concat('FF', right(date_part('year', now())::text, 2), LPAD(date_part('month', now())::text, 2, '0'::text), \n" + 
				"LPAD(date_part('day', now())::text, 2, '0'::text), LPAD((coalesce(max(right(a.trans_no, 2))::int, 0)+1)::text, 2, '0'::text)) \n" + 
				"from rf_buyer_status a \n" + 
				"where a.byrstatus_id = '31' and a.actual_date::date = now()::date and a.status_id = 'A'"); 
		txtbatch.setValue(strBatch);
		String strEmployee = UserInfo.EmployeeCode;
		
		for(int x = 0; x < model.getRowCount(); x++){
			Boolean isSelected = (Boolean) model.getValueAt(x, 1);
			
			if (isSelected) {
				pgUpdate db = new pgUpdate();
				
				String strLot = FncGlobal.GetString("select (case when position('/' in '"+(String) model.getValueAt(x, 5).toString()+"') > 0 then left('"+(String) model.getValueAt(x, 5).toString()+"', position('/' in '"+(String) model.getValueAt(x, 5).toString()+"') - 1) else '"+(String) model.getValueAt(x, 5).toString()+"' end)");
//				String entity_id = GetValue("select entity_id from rf_entity where trim(entity_name) = '"+(String) model.getValueAt(x, 0)+"' and status_id = 'A'");
//				String proj_id = GetValue("select proj_id from mf_project where trim(proj_name) = '"+(String) model.getValueAt(x, 2)+"'");
//				String pbl_id = GetValue("select pbl_id from mf_unit_info where proj_id = '"+proj_id+"' and phase = '"+(String) model.getValueAt(x, 3)+"' and block = '"+(String) model.getValueAt(x, 4)+"' and lot = '"+strLot+"'");
//				String strSeq = GetValue("select trim(seq_no::char(5)) from rf_sold_unit where entity_id = '"+entity_id+"' and projcode = '"+proj_id+"' and pbl_id = '"+pbl_id+"' and status_id = 'A' order by rec_id desc limit 1");
				String entity_id = (String) model.getValueAt(x, 19).toString();
				String proj_id = (String) model.getValueAt(x, 21).toString();;
				String pbl_id = (String) model.getValueAt(x, 20).toString();
				String strSeq = (String) model.getValueAt(x, 22).toString();;
				
				System.out.println("");
				System.out.println("entity_id: " + entity_id);
				System.out.println("proj_id: " + proj_id);
				System.out.println("pbl_id: " + pbl_id);
				System.out.println("strSeq: " + strSeq);
				System.out.println("strLot: " + strLot);
				System.out.println("strLot: " + "select (case when position('/' in '"+(String) model.getValueAt(x, 5).toString()+"') > 0 then left('"+(String) model.getValueAt(x, 5).toString()+"', position('/' in '"+(String) model.getValueAt(x, 5).toString()+"') - 1) else '"+(String) model.getValueAt(x, 5).toString()+"' end)      select left('"+(String) model.getValueAt(x, 5).toString()+"', position('/' in '"+(String) model.getValueAt(x, 5).toString()+"') - 1)"); 

				db = new pgUpdate();
				strSQL = "update rf_buyer_status \n" + 
						"set status_id = 'I' \n" + 
						"where entity_id = '"+entity_id+"' and proj_id = '"+proj_id+"' and pbl_id = '"+pbl_id+"' \n" + 
						"and seq_no::int = '"+strSeq+"'::int and byrstatus_id = '31' and status_id = 'A' and tran_date < now()";
				db.executeUpdate(strSQL, false);
				db.commit();
				
				db = new pgUpdate();
				strSQL = "insert into rf_buyer_status (entity_id, proj_id, pbl_id, seq_no, byrstatus_id, tran_date, actual_date, status_id, trans_no, created_by) \n" +
						"values ('"+entity_id+"', '"+proj_id+"', '"+pbl_id+"', "+strSeq+", '31', Now(), Now(), 'A', '"+strBatch+"', '"+UserInfo.EmployeeCode+"')";
				db.executeUpdate(strSQL, false);
				db.commit();
				
				/*ADDED BY JED 2021-09-10 DCRF NO. 1726 : AUTOGENERATED TAGGING FOR PCOST (DOAS)*/
				pgSelect db2 = new pgSelect();
				strSQL = "select sp_save_pcost_doas('"+entity_id+"', '"+proj_id+"', '"+pbl_id+"', "+strSeq+", '"+strEmployee+"')";
				db2.select(strSQL);
			}
		}
		
		return strBatch;
	}	
	public static String saveFFFStatus_GtoG(Date date_cutoff, model_hdmf_FirstFiling model,_JLookup txtbatch) {
		String strSQL = ""; 
		
		String strBatch = FncGlobal.GetString("select concat('FF', right(date_part('year', now())::text, 2), LPAD(date_part('month', now())::text, 2, '0'::text), \n" + 
				"LPAD(date_part('day', now())::text, 2, '0'::text), LPAD((coalesce(max(right(a.trans_no, 2))::int, 0)+1)::text, 2, '0'::text)) \n" + 
				"from rf_buyer_status a \n" + 
				"where a.byrstatus_id = '143' and a.actual_date::date = now()::date and a.status_id = 'A'"); 
		txtbatch.setValue(strBatch);
		String strEmployee = UserInfo.EmployeeCode;
		
		for(int x = 0; x < model.getRowCount(); x++){
			Boolean isSelected = (Boolean) model.getValueAt(x, 1);
			
			if (isSelected) {
				pgUpdate db = new pgUpdate();
				
				String strLot = FncGlobal.GetString("select (case when position('/' in '"+(String) model.getValueAt(x, 5).toString()+"') > 0 then left('"+(String) model.getValueAt(x, 5).toString()+"', position('/' in '"+(String) model.getValueAt(x, 5).toString()+"') - 1) else '"+(String) model.getValueAt(x, 5).toString()+"' end)");
				String entity_id = (String) model.getValueAt(x, 19).toString();
				String proj_id = (String) model.getValueAt(x, 21).toString();;
				String pbl_id = (String) model.getValueAt(x, 20).toString();
				String strSeq = (String) model.getValueAt(x, 22).toString();;
				
				System.out.println("");
				System.out.println("entity_id: " + entity_id);
				System.out.println("proj_id: " + proj_id);
				System.out.println("pbl_id: " + pbl_id);
				System.out.println("strSeq: " + strSeq);
				System.out.println("strLot: " + strLot);
				System.out.println("strLot: " + "select (case when position('/' in '"+(String) model.getValueAt(x, 5).toString()+"') > 0 then left('"+(String) model.getValueAt(x, 5).toString()+"', position('/' in '"+(String) model.getValueAt(x, 5).toString()+"') - 1) else '"+(String) model.getValueAt(x, 5).toString()+"' end)      select left('"+(String) model.getValueAt(x, 5).toString()+"', position('/' in '"+(String) model.getValueAt(x, 5).toString()+"') - 1)"); 

				db = new pgUpdate();
				strSQL = "update rf_buyer_status \n" + 
						"set status_id = 'I' \n" + 
						"where entity_id = '"+entity_id+"' and proj_id = '"+proj_id+"' and pbl_id = '"+pbl_id+"' \n" + 
						"and seq_no::int = '"+strSeq+"'::int and byrstatus_id = '143' and status_id = 'A' and tran_date < now()";
				db.executeUpdate(strSQL, false);
				db.commit();
				
				db = new pgUpdate();
				strSQL = "insert into rf_buyer_status (entity_id, proj_id, pbl_id, seq_no, byrstatus_id, tran_date, actual_date, status_id, trans_no, created_by) \n" +
						"values ('"+entity_id+"', '"+proj_id+"', '"+pbl_id+"', "+strSeq+", '143', Now(), Now(), 'A', '"+strBatch+"', '"+UserInfo.EmployeeCode+"')";
				db.executeUpdate(strSQL, false);
				db.commit();
				
				/*ADDED BY JED 2021-09-10 DCRF NO. 1726 : AUTOGENERATED TAGGING FOR PCOST (DOAS)*/
				pgSelect db2 = new pgSelect();
				strSQL = "select sp_save_pcost_doas('"+entity_id+"', '"+proj_id+"', '"+pbl_id+"', "+strSeq+", '"+strEmployee+"')";
				db2.select(strSQL);
			}
		}
		
		return strBatch;
	}
	
	public static void DisplayForFirstFilingWithBatch(DefaultTableModel modelMain, JList rowHeader, String strBatch) {
		FncTables.clearTable(modelMain);
		DefaultListModel listModel = new DefaultListModel();
		rowHeader.setModel(listModel); 
		
		String SQL = "SELECT * FROM view_hdmf_first_filing_v2('"+strBatch+"')";

		System.out.println("");
		System.out.println(SQL);

		pgSelect db = new pgSelect();
		db.select(SQL);
		if (db.isNotNull()) {
			for (int x = 0; x < db.getRowCount(); x++) {
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());
			}
		} else {
			JOptionPane.showMessageDialog(null, "No records were returned.");
		};
	}
	
	public static void DisplayForFirstFilingWithBatch_GtoG(DefaultTableModel modelMain, JList rowHeader, String strBatch) {
		FncTables.clearTable(modelMain);
		DefaultListModel listModel = new DefaultListModel();
		rowHeader.setModel(listModel); 
		
		String SQL = "SELECT * FROM view_hdmf_first_filing_gtog('"+strBatch+"')";

		System.out.println("");
		System.out.println(SQL);

		pgSelect db = new pgSelect();
		db.select(SQL);
		if (db.isNotNull()) {
			for (int x = 0; x < db.getRowCount(); x++) {
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());
			}
		} else {
			JOptionPane.showMessageDialog(null, "No records were returned.");
		};
	}
	
	public static void displayForTaggingOfLoanReturnedFF(model_hdmf_loanReturned model, JList rowHeader, String strBatch) {
		FncTables.clearTable(model);
		DefaultListModel listModel = new DefaultListModel();
		rowHeader.setModel(listModel);
		
		pgSelect db = new pgSelect();
		String SQL = "select * from view_hdmf_loanreturned_firstfiling('' ,'' , '"+strBatch+"'); ";
		db.select(SQL);

		System.out.println("");
		System.out.println(SQL);
		System.out.println("Rowcount: " + db.getRowCount());
		
		if(db.isNotNull()){
			for(int x = 0; x<db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
				listModel.addElement(model.getRowCount());
			}
		}
	}
	
	
	public static void displayForTaggingOfLoanReturnedPF(model_hdmf_loanReturned model, JList rowHeader, String strBatch) {
		FncTables.clearTable(model);
		DefaultListModel listModel = new DefaultListModel();
		rowHeader.setModel(listModel);
		
		pgSelect db = new pgSelect();
		String SQL = "select * from view_hdmf_loanreturned_postfiling('' ,'' , '"+strBatch+"'); ";
		db.select(SQL);

		System.out.println("");
		System.out.println(SQL);
		System.out.println("Rowcount: " + db.getRowCount());
		
		if(db.isNotNull()){
			for(int x = 0; x<db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
				listModel.addElement(model.getRowCount());
			}
		}
	}
	
	
	public static String saveLoanReturned(model_hdmf_loanReturned model, Date dteDate){
		pgUpdate db = new pgUpdate();
		String strBatch = "";  
	
		strBatch = FncGlobal.GetString("select LPAD((coalesce((select trans_no from rf_buyer_status where byrstatus_id = '43' and status_id = 'A' order by trans_no desc limit 1), '0')::int + 1::int)::varchar, 10, '0')"); 
	
		for (int x = 0; x < model.getRowCount(); x++) {
			Boolean isSelected = (Boolean) model.getValueAt(x, 0);
			
			if (isSelected) {
				System.out.println("");
				System.out.println("Date: " + model.getValueAt(x, 6));

				String entity_id = (String) model.getValueAt(x, 5);
				String proj_id = (String) model.getValueAt(x, 6);
				String pbl_id = (String) model.getValueAt(x, 7);
				String strSeq = (String) model.getValueAt(x, 8).toString();
				String strRemarks = (String) model.getValueAt(x, 4).toString();
				
				strRemarks = strRemarks.replace("'", "`"); 
				
				System.out.println("");
				System.out.println("entity_id: " + entity_id);
				System.out.println("proj_id: " + proj_id);
				System.out.println("pbl_id: " + pbl_id);
				System.out.println("strSeq: " + strSeq);

				db = new pgUpdate();
				db.executeUpdate("update rf_buyer_status \n" + 
						"set status_id = 'I' \n" + 
						"where entity_id = '"+entity_id+"' and proj_id = '"+proj_id+"' and pbl_id = '"+pbl_id+"' \n" + 
						"and seq_no::int = '"+strSeq+"'::int and byrstatus_id = '43' and status_id = 'A' and tran_date < now()", false);
				db.commit();

				/*	Added by Mann2x; Date Added: April 1, 2019; DCRF# 996;	*/
				db = new pgUpdate();
				db.executeUpdate("update rf_buyer_status \n" + 
						"set status_id = 'I' \n" + 
						"where entity_id = '"+entity_id+"' and proj_id = '"+proj_id+"' and pbl_id = '"+pbl_id+"' and seq_no::int = '"+strSeq+"'::int \n" + 
						"and status_id = 'A' and byrstatus_id = '31' \n" + 
						"and tran_date::date = (select tran_date::date from rf_buyer_status where entity_id = '"+entity_id+"' and proj_id = '"+proj_id+"' and pbl_id = '"+pbl_id+"' \n" + 
						"and seq_no::int = '"+strSeq+"'::int and byrstatus_id = '31' and status_id = 'A' order by tran_date::date desc limit 1)", false);
				db.commit();
				
				db = new pgUpdate();
				db.executeUpdate("insert into rf_buyer_status \n" +
						"(entity_id, proj_id, pbl_id, seq_no, byrstatus_id, tran_date, actual_date, status_id, trans_no, created_by) \n" +
						"values ('"+entity_id+"', '"+proj_id+"', '"+pbl_id+"', '"+strSeq+"'::int, '43', now(), '"+dteDate.toString()+"', 'A', '"+strBatch+"', '"+UserInfo.EmployeeCode+"')", false);
				db.commit();
				
				db = new pgUpdate();
				db.executeUpdate("insert into rf_hdmf_status_other_values (entity_id, proj_id, pbl_id, seq_no, remarks, byrstatus_id) \n" +
				"values ('"+entity_id+"', '"+proj_id+"', '"+pbl_id+"', '"+strSeq+"'::int, '"+strRemarks+"', '43'); ", false);
				db.commit();
				
				db = new pgUpdate();
				db.executeUpdate("insert into dm_gen_findings (entity_id, proj_id, pbl_id, seq_no, date_eval, eval_by, gen_findings, status_id, created_by) \n" +
						"values ('"+entity_id+"', '"+proj_id+"', '"+pbl_id+"', "+strSeq+", '"+dteDate.toString()+"'::date, '"+UserInfo.Alias+"', '"+strRemarks+"', 'A', '"+UserInfo.EmployeeCode+"');", false);
				db.commit();
			}
		}

		JOptionPane.showMessageDialog(null, "All accounts are posted.");
		
		return strBatch; 
	}
	
	public static String saveLoanReturnedPostFiling(model_hdmf_loanReturned model, Date dteDate){
		pgUpdate db = new pgUpdate();
		String strBatch = "";  
	
		strBatch = FncGlobal.GetString("select LPAD((coalesce((select trans_no from rf_buyer_status where byrstatus_id = '50' and status_id = 'A' order by trans_no desc limit 1), '0')::int + 1::int)::varchar, 10, '0')"); 
	
		for (int x = 0; x < model.getRowCount(); x++) {
			Boolean isSelected = (Boolean) model.getValueAt(x, 0);
			
			if (isSelected) {
				System.out.println("");
				System.out.println("Date: " + model.getValueAt(x, 6));

				String entity_id = (String) model.getValueAt(x, 5);
				String proj_id = (String) model.getValueAt(x, 6);
				String pbl_id = (String) model.getValueAt(x, 7);
				String strSeq = (String) model.getValueAt(x, 8).toString();
				String strRemarks = (String) model.getValueAt(x, 4).toString();
				
				strRemarks = strRemarks.replace("'", "`"); 
				
				System.out.println("");
				System.out.println("entity_id: " + entity_id);
				System.out.println("proj_id: " + proj_id);
				System.out.println("pbl_id: " + pbl_id);
				System.out.println("strSeq: " + strSeq);

				db = new pgUpdate();
				db.executeUpdate("update rf_buyer_status \n" + 
						"set status_id = 'I' \n" + 
						"where entity_id = '"+entity_id+"' and proj_id = '"+proj_id+"' and pbl_id = '"+pbl_id+"' \n" + 
						"and seq_no::int = '"+strSeq+"'::int and byrstatus_id = '50' and status_id = 'A' and tran_date < now()", false);
				db.commit();

				/*	Added by Mann2x; Date Added: April 1, 2019; DCRF# 996;	*/
				db = new pgUpdate();
				db.executeUpdate("update rf_buyer_status \n" + 
						"set status_id = 'I' \n" + 
						"where entity_id = '"+entity_id+"' and proj_id = '"+proj_id+"' and pbl_id = '"+pbl_id+"' and seq_no::int = '"+strSeq+"'::int \n" + 
						"and status_id = 'A' and byrstatus_id in('48','144') \n" + /*modified:Almar 2023-09-05 added byrstatus '144' to inactive for gtog accts*/
						"and tran_date::date = (select tran_date::date from rf_buyer_status where entity_id = '"+entity_id+"' and proj_id = '"+proj_id+"' and pbl_id = '"+pbl_id+"' \n" + 
						"and seq_no::int = '"+strSeq+"'::int and byrstatus_id in('48','144') and status_id = 'A' order by tran_date::date desc limit 1)", false);
				db.commit();
				
				db = new pgUpdate();
				db.executeUpdate("insert into rf_buyer_status \n" +
						"(entity_id, proj_id, pbl_id, seq_no, byrstatus_id, tran_date, actual_date, status_id, trans_no, created_by) \n" +
						"values ('"+entity_id+"', '"+proj_id+"', '"+pbl_id+"', '"+strSeq+"'::int, '50', now(), '"+dteDate.toString()+"', 'A', '"+strBatch+"', '"+UserInfo.EmployeeCode+"')", false);
				db.commit();
				
				db = new pgUpdate();
				db.executeUpdate("insert into rf_hdmf_status_other_values (entity_id, proj_id, pbl_id, seq_no, remarks, byrstatus_id) \n" +
				"values ('"+entity_id+"', '"+proj_id+"', '"+pbl_id+"', '"+strSeq+"'::int, '"+strRemarks+"', '50'); ", false);
				db.commit();
				
				db = new pgUpdate();
				db.executeUpdate("insert into dm_gen_findings (entity_id, proj_id, pbl_id, seq_no, date_eval, eval_by, gen_findings, status_id, created_by) \n" +
						"values ('"+entity_id+"', '"+proj_id+"', '"+pbl_id+"', "+strSeq+", '"+dteDate.toString()+"'::date, '"+UserInfo.Alias+"', '"+strRemarks+"', 'A', '"+UserInfo.EmployeeCode+"');", false);
				db.commit();
			}
		}

		JOptionPane.showMessageDialog(null, "All accounts are posted.");
		
		return strBatch; 
	}
	
	public static void DisplayEmailSent(DefaultTableModel modelMain, JList rowHeader, String strCoID, String strProID, String strPhase, String strDate, String strBatch) {
		FncTables.clearTable(modelMain);
		DefaultListModel listModel = new DefaultListModel();
		rowHeader.setModel(listModel); 
		
		String SQL = "SELECT * \n" +
				"FROM view_hdmf_email_sent('"+strCoID+"', '"+strProID+"', '"+strPhase+"', '"+strDate+"'::date, '"+strBatch+"'); "; 

		System.out.println("");
		System.out.println(SQL);

		pgSelect db = new pgSelect();
		db.select(SQL);
		if (db.isNotNull()) {
			for (int x = 0; x < db.getRowCount(); x++) {
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());
			}
		} else {
			JOptionPane.showMessageDialog(null, "No records were returned.");
		};
	}
	
	public static void DisplayEmailReply(DefaultTableModel modelMain, JList rowHeader, String strCoID, String strProID, String strPhase, String strDate, String strBatch) {
		FncTables.clearTable(modelMain);
		DefaultListModel listModel = new DefaultListModel();
		rowHeader.setModel(listModel); 
		
		String SQL = "SELECT * \n" +
				"FROM view_hdmf_email_reply('"+strCoID+"', '"+strProID+"', '"+strPhase+"', '"+strDate+"'::date, '"+strBatch+"'); "; 

		System.out.println("");
		System.out.println(SQL);

		pgSelect db = new pgSelect();
		db.select(SQL);
		if (db.isNotNull()) {
			for (int x = 0; x < db.getRowCount(); x++) {
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());
			}
		} else {
			JOptionPane.showMessageDialog(null, "No records were returned.");
		};
	}
	
	public static String email_sent(model_hdmf_email model) {
		pgUpdate dbExec = new pgUpdate(); 
		
		String strEntityID = "";
		String strProjID = ""; 
		String strPblID = ""; 
		String strSeqNo = "";
		String strCOE = ""; 
		String strBatch = FncGlobal.GetString("select concat('ES-', LPAD(((coalesce(max(split_part(batch_no, '-', 2)::int), 0))+1)::text, 5, '0'::text)) \n" + 
				"from rf_hdmf_status_other_values \n" + 
				"where remarks ~* 'EMAIL SENT'"); 
		
		for (int x=0; x<model.getRowCount(); x++) {
			if ((Boolean) model.getValueAt(x, 0)) {
				strEntityID = model.getValueAt(x, 6).toString(); 
				strProjID = model.getValueAt(x, 7).toString();
				strPblID = model.getValueAt(x, 8).toString();
				strSeqNo = model.getValueAt(x, 9).toString();
				strCOE = model.getValueAt(x, 5).toString();
				
				dbExec.executeUpdate("insert into rf_hdmf_status_other_values (entity_id, proj_id, pbl_id, seq_no, remarks, batch_no, status_id, actual_date) \n" + 
						"values ('"+strEntityID+"', '"+strProjID+"', '"+strPblID+"', '"+strSeqNo+"'::int, 'EMAIL SENT, "+strCOE+", "+UserInfo.EmployeeCode+"', '"+strBatch+"', 'A', now())", true);	
			}
		}
		
		dbExec.commit();
		
		JOptionPane.showMessageDialog(null, "Successfully marked as `email sent`!");
		return strBatch;
	}
	
	
	public static String email_reply(model_hdmf_email model) {
		pgUpdate dbExec = new pgUpdate(); 
		
		String strEntityID = "";
		String strProjID = ""; 
		String strPblID = ""; 
		String strSeqNo = "";
		String strCOE = ""; 
		String strBatch = FncGlobal.GetString("select concat('ER-', LPAD(((coalesce(max(split_part(batch_no, '-', 2)::int), 0))+1)::text, 5, '0'::text)) \n" + 
				"from rf_hdmf_status_other_values \n" + 
				"where remarks ~* 'EMAIL REPLY'"); 
		
		for (int x=0; x<model.getRowCount(); x++) {
			if ((Boolean) model.getValueAt(x, 0)) {
				strEntityID = model.getValueAt(x, 6).toString(); 
				strProjID = model.getValueAt(x, 7).toString();
				strPblID = model.getValueAt(x, 8).toString();
				strSeqNo = model.getValueAt(x, 9).toString();
				strCOE = model.getValueAt(x, 5).toString();
				
				dbExec.executeUpdate("insert into rf_hdmf_status_other_values (entity_id, proj_id, pbl_id, seq_no, remarks, batch_no, status_id, actual_date) \n" + 
						"values ('"+strEntityID+"', '"+strProjID+"', '"+strPblID+"', '"+strSeqNo+"'::int, 'EMAIL REPLY, "+UserInfo.EmployeeCode+"', '"+strBatch+"', 'A', now())", true);	
			}
		}
		
		dbExec.commit();
		
		JOptionPane.showMessageDialog(null, "Successfully marked as `email reply`!");
		return strBatch;
	}
	public static String GetValue(String SQL) {
		String strValue = "";
		pgSelect sqlExec = new pgSelect();
		
		sqlExec.select(SQL);

		if (sqlExec.isNotNull()) {
			strValue = (String) sqlExec.getResult()[0][0];
		} else {
			strValue = "";
		}
		
		return strValue;
	}
}