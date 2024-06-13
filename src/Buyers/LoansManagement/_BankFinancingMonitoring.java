package Buyers.LoansManagement;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.DefaultListModel;
import javax.swing.JList;

import Database.pgSelect;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.UserInfo;
import tablemodel.modelBFST_LOGReleased;
import tablemodel.modelBFST_LOGReturned;
import tablemodel.modelBFST_LOGSigned;
import tablemodel.modelBFST_PostCompliance;
import tablemodel.modelBFST_TCTForTransfer;
import tablemodel.modelBFST_TCT_BuyersName;
import tablemodel.modelBFST_LOGExt;
import tablemodel.modelBFST_LOGFiled;

public class _BankFinancingMonitoring {

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
	
	public static String sqlBankName(){//SQL FOR THE BANK NAME
		return "select trim(bank_id) as \"ID\", trim(bank_name) as \"Bank Name\", trim(bank_alias) as \"Bank Alias\" \n" + 
				"from mf_bank\n" + 
				"where status_id ='A' \n"+
				"order by bank_name";
	}
	
	public static void displayLOG_Filed(modelBFST_LOGFiled model, JList rowHeader ,String proj_id, String phase){
		if(model != null && rowHeader != null){
			FncTables.clearTable(model);
			
			DefaultListModel listModel = new DefaultListModel();
			rowHeader.setModel(listModel);
			
			pgSelect db = new pgSelect();
			String SQL = "SELECT * FROM sp_generate_bank_finance_status_log_filed('"+proj_id+"', '"+phase+"')";
			db.select(SQL);
			
			FncSystem.out("Display Accounts for LOG Filing", SQL);
			if(db.isNotNull()){
				for(int x= 0; x<db.getRowCount(); x++){
					model.addRow(db.getResult()[x]);
					listModel.addElement(model.getRowCount());
				}
			}
		}
	}
	
	public static void displayLOG_Returned(modelBFST_LOGReturned model, JList rowHeader, String proj_id, String phase){
		if(model != null && rowHeader != null){
			FncTables.clearTable(model);
			
			DefaultListModel listModel = new DefaultListModel();
			rowHeader.setModel(listModel);
			
			pgSelect db = new pgSelect();
			String SQL = "SELECT * FROM sp_generate_bank_finance_status_log_bank_returned('"+proj_id+"', '"+phase+"');";
			db.select(SQL);
			
			FncSystem.out("Display Accounts to Return", SQL);
			
			if(db.isNotNull()){
				for(int x=0 ; x<db.getRowCount(); x++){
					model.addRow(db.getResult()[x]);
					listModel.addElement(model.getRowCount());
				}
			}
		}
	}
	
	public static void displayLOG_Released(modelBFST_LOGReleased model, JList rowHeader, String co_id ,String proj_id, String phase){
		if(model != null && rowHeader != null){
			FncTables.clearTable(model);
			
			DefaultListModel listModel = new DefaultListModel();
			rowHeader.setModel(listModel);
			
			pgSelect db = new pgSelect();
			String SQL = "SELECT * FROM sp_generate_bank_finance_status_log_released('"+co_id+"','"+proj_id+"', '"+phase+"')";
			db.select(SQL);
			
			FncSystem.out("Display Accounts for LOG Approval", SQL);
			if(db.isNotNull()){
				for(int x=0; x<db.getRowCount(); x++){
					model.addRow(db.getResult()[x]);
					listModel.addElement(model.getRowCount());
				}
			}
		}
	}
	
	public static void displayLOG_Signed(modelBFST_LOGSigned model, JList rowHeader, String proj_id, String phase){
		if(model != null && rowHeader != null){
			FncTables.clearTable(model);
			
			DefaultListModel listModel = new DefaultListModel();
			rowHeader.setModel(listModel);
			
			pgSelect db = new pgSelect();
			String SQL = "SELECT * FROM sp_generate_bank_finance_status_log_signed('"+proj_id+"', '"+phase+"');";
			db.select(SQL);
			
			FncSystem.out("Display Accounts for LOG Signing", SQL);
			
			if(db.isNotNull()){
				for(int x= 0; x<db.getRowCount(); x++){
					model.addRow(db.getResult()[x]);
					listModel.addElement(model.getRowCount());
				}
			}
		}
	}
	
	public static void displayLOG_Ext(modelBFST_LOGExt model,JList rowHeader, String co_id ,String proj_id, String phase){
		if(model != null && rowHeader != null){
			FncTables.clearTable(model);


			DefaultListModel listModel = new DefaultListModel();
			rowHeader.setModel(listModel);

			pgSelect db = new pgSelect();
			String SQL = "SELECT * FROM sp_generate_bank_finance_status_log_ext(NULLIF('"+co_id+"', 'null'), NULLIF('"+proj_id+"', 'null'), NULLIF('"+phase+"', 'null'))";
			db.select(SQL);

			FncSystem.out("Display Accounts for LOG Ext", SQL);

			if(db.isNotNull()){
				for(int x=0; x<db.getRowCount(); x++){
					model.addRow(db.getResult()[x]);
					listModel.addElement(model.getRowCount());
				}
			}
		}
	}
	
	public static void displayTCTforTransferBuyer(modelBFST_TCTForTransfer model, JList rowHeader, String proj_id, String phase){
		if(model != null && rowHeader != null){
			FncTables.clearTable(model);
			
			DefaultListModel listModel = new DefaultListModel();
			rowHeader.setModel(listModel);
			
			pgSelect db = new pgSelect();
			String SQL = "SELECT * FROM sp_generate_bank_finance_status_tct_for_trans_buyer_name('"+proj_id+"', '"+phase+"');";
			db.select(SQL);
			
			FncSystem.out("Display Accounts for TCT Transfer", SQL);
			
			if(db.isNotNull()){
				for(int x= 0; x<db.getRowCount(); x++){
					model.addRow(db.getResult()[x]);
					listModel.addElement(model.getRowCount());
				}
			}
		}
	}
	
	public static void displayTCT_UnderBuyersName(modelBFST_TCT_BuyersName model, JList rowHeader, String co_id, String proj_id, String phase){
		if(model != null && rowHeader != null){
			FncTables.clearTable(model);
			
			DefaultListModel listModel = new DefaultListModel();
			rowHeader.setModel(listModel);
			
			pgSelect db = new pgSelect();
			String SQL = "SELECT * FROM sp_generate_bank_finance_status_tct_under_buyers_name(NULLIF('"+co_id+"', 'null'), NULLIF('"+proj_id+"', 'null'), NULLIF('"+phase+"', 'null'))";
			db.select(SQL);
			
			if(db.isNotNull()){
				for(int x=0; x<db.getRowCount(); x++){
					model.addRow(db.getResult()[x]);
					listModel.addElement(model.getRowCount());
				}
			}
		}
	}
	
	public static void displayPost_Compliance_Qualified(modelBFST_PostCompliance model, JList rowHeader, String co_id, String proj_id, String phase){
		if(model != null && rowHeader != null){
			FncTables.clearTable(model);
			
			DefaultListModel listModel = new DefaultListModel();
			rowHeader.setModel(listModel);
			
			pgSelect db = new pgSelect();
			String SQL = "SELECT * FROM sp_generate_bank_finance_status_post_compliance(NULLIF('"+co_id+"', 'null'), NULLIF('"+proj_id+"', 'null'), NULLIF('"+phase+"', 'null'))";
			db.select(SQL);
			
			if(db.isNotNull()){
				for(int x=0; x<db.getRowCount(); x++){
					model.addRow(db.getResult()[x]);
					listModel.addElement(model.getRowCount());
				}
			}
		}
	}
	
	
	public static void saveLOG_FiledAccts(modelBFST_LOGFiled model, Date actual_date, String bank_id){
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
		String SQL = "SELECT sp_tag_accts_log_filed(ARRAY["+entity_id+"]::VARCHAR[], ARRAY["+proj_id+"]::VARCHAR[], ARRAY["+pbl_id+"]::VARCHAR[], \n"+
					 "ARRAY["+seq_no+"]::INT[], '"+actual_date+"', '"+bank_id+"' ,'"+UserInfo.Branch+"','"+UserInfo.EmployeeCode+"')";
		db.select(SQL);
		
		FncSystem.out("Display SQL For LOG Filed Tagging", SQL);
	}
	
	public static void saveLOG_ReturnedAccts(modelBFST_LOGReturned model, Date actual_date){
		ArrayList<String> listEntityID = new ArrayList<String>();
		ArrayList<String> listProjID = new ArrayList<String>();
		ArrayList<String> listPBL = new ArrayList<String>();
		ArrayList<Integer> listSeq = new ArrayList<Integer>();
		
		for(int x= 0; x<model.getRowCount(); x++){
			Boolean isSelected = (Boolean) model.getValueAt(x, 0);
			
			if(isSelected){
				
				String entity_id = (String) model.getValueAt(x, 5);
				String proj_id = (String) model.getValueAt(x, 6);
				String pbl_id = (String) model.getValueAt(x, 7);
				Integer seq_no = (Integer) model.getValueAt(x, 8);
				
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
		String SQL = "SELECT sp_tag_accts_log_bank_returned(ARRAY["+entity_id+"]::VARCHAR[], ARRAY["+proj_id+"]::VARCHAR[], ARRAY["+pbl_id+"]::VARCHAR[], \n"+
					 "ARRAY["+seq_no+"]::INT[], '"+actual_date+"' ,'"+UserInfo.Branch+"','"+UserInfo.EmployeeCode+"')";
		db.select(SQL);
		
		FncSystem.out("Display SQL For LOG Filed Tagging", SQL);
	}
	
	public static void saveLOG_ReleasedAccts(modelBFST_LOGReleased model, Date actual_date){
		ArrayList<String> listEntityID = new ArrayList<String>();
		ArrayList<String> listProjID = new ArrayList<String>();
		ArrayList<String> listPBL = new ArrayList<String>();
		ArrayList<Integer> listSeq = new ArrayList<Integer>();
		ArrayList<BigDecimal> listApprovedAmt = new ArrayList<BigDecimal>();
		ArrayList<Integer> listBankTerm = new ArrayList<Integer>();
		ArrayList<String> listEffectivity = new ArrayList<String>();
		
		for(int x= 0; x<model.getRowCount(); x++){
			Boolean isSelected = (Boolean) model.getValueAt(x, 0);
			
			if(isSelected){
				
				BigDecimal approved_amt = new BigDecimal((Long) model.getValueAt(x, 5));
				Integer bank_term = (Integer) model.getValueAt(x, 6);
				String effectivity = (String) model.getValueAt(x, 7);
				String entity_id = (String) model.getValueAt(x, 8);
				String proj_id = (String) model.getValueAt(x, 9);
				String pbl_id = (String) model.getValueAt(x, 10);
				Integer seq_no = (Integer) model.getValueAt(x, 11);
				
				listProjID.add(String.format("'%S'", proj_id));
				listPBL.add(String.format("'%s'", pbl_id));
				listSeq.add(seq_no);
				listEntityID.add(String.format("'%s'", entity_id));
				listApprovedAmt.add(approved_amt);
				listBankTerm.add(bank_term);
				listEffectivity.add(String.format("'%s'", effectivity));
			}
		}
		
		String entity_id = listEntityID.toString().replaceAll("\\[|\\]", "");
		String proj_id = listProjID.toString().replaceAll("\\[|\\]", "");
		String pbl_id = listPBL.toString().replaceAll("\\[|\\]", "");
		String seq_no = listSeq.toString().replaceAll("\\[|\\]", "");
		String approved_amt = listApprovedAmt.toString().replaceAll("\\[|\\]", "");
		String bank_term = listBankTerm.toString().replaceAll("\\[|\\]", "");
		String effectivity = listEffectivity.toString().replaceAll("\\[|\\]", "");
		
		pgSelect db = new pgSelect();
		String SQL = "SELECT sp_tag_accts_log_released(ARRAY["+entity_id+"]::VARCHAR[], ARRAY["+proj_id+"]::VARCHAR[], ARRAY["+pbl_id+"]::VARCHAR[], ARRAY["+seq_no+"]::INT[], \n"+
					 "ARRAY["+approved_amt+"]::NUMERIC[], ARRAY["+bank_term+"]::INT[] ,ARRAY["+effectivity+"]::TIMESTAMP WITHOUT TIME ZONE[] ,NULLIF('"+actual_date+"', 'null')::TIMESTAMP ,'"+UserInfo.Branch+"', '"+UserInfo.EmployeeCode+"')";
		db.select(SQL, "Post", true);
		
		FncSystem.out("Display SQL for Posting of Approval of LOG Released Accts", SQL);
	}
	
	public static void saveLOG_SignedAccts(modelBFST_LOGSigned model, Date actual_date){
		ArrayList<String> listEntityID = new ArrayList<String>();
		ArrayList<String> listProjID = new ArrayList<String>();
		ArrayList<String> listPBL = new ArrayList<String>();
		ArrayList<Integer> listSeq = new ArrayList<Integer>();
		
		for(int x = 0; x<model.getRowCount(); x++){
			Boolean isSelected = (Boolean) model.getValueAt(x, 0);
			
			if(isSelected){
				String entity_id = (String) model.getValueAt(x, 4);
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
		String SQL = "SELECT sp_tag_accts_log_signed(ARRAY["+entity_id+"]::VARCHAR[], ARRAY["+proj_id+"]::VARCHAR[], ARRAY["+pbl_id+"]::VARCHAR[], ARRAY["+seq_no+"]::INT[], \n"+
				     "NULLIF('"+actual_date+"', 'null')::TIMESTAMP ,'"+UserInfo.Branch+"', '"+UserInfo.EmployeeCode+"')";
		db.select(SQL);
		
		FncSystem.out("Display saving of log signed accounts", SQL);
		
	}
	
	public static void saveLOG_ExtAccts(modelBFST_LOGExt model, Date actual_date){
		ArrayList<String> listEntityID = new ArrayList<String>();
		ArrayList<String> listProjID = new ArrayList<String>();
		ArrayList<String> listPBL = new ArrayList<String>();
		ArrayList<Integer> listSeq = new ArrayList<Integer>();
		
		for(int x = 0; x<model.getRowCount(); x++){
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
		String SQL = "SELECT sp_tag_accts_log_ext(ARRAY["+entity_id+"]::VARCHAR[], ARRAY["+proj_id+"]::VARCHAR[], ARRAY["+pbl_id+"]::VARCHAR[], ARRAY["+seq_no+"]::INT[], \n"+
				     "NULLIF('"+actual_date+"', 'null')::TIMESTAMP ,'"+UserInfo.Branch+"', '"+UserInfo.EmployeeCode+"')";
		db.select(SQL);
		
		FncSystem.out("Display saving of log signed accounts", SQL);
		
	}
	
	public static void saveTCT_for_Transfer(modelBFST_TCTForTransfer model, Date actual_date){
		ArrayList<String> listEntityID = new ArrayList<String>();
		ArrayList<String> listProjID = new ArrayList<String>();
		ArrayList<String> listPBL = new ArrayList<String>();
		ArrayList<Integer> listSeq = new ArrayList<Integer>();
		
		for(int x = 0; x<model.getRowCount(); x++){
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
		String SQL = "SELECT sp_tag_accts_tct_for_transfer(ARRAY["+entity_id+"]::VARCHAR[], ARRAY["+proj_id+"]::VARCHAR[], ARRAY["+pbl_id+"]::VARCHAR[], ARRAY["+seq_no+"]::INT[], \n"+
				     "NULLIF('"+actual_date+"', 'null')::TIMESTAMP ,'"+UserInfo.Branch+"', '"+UserInfo.EmployeeCode+"')";
		db.select(SQL);
		
		FncSystem.out("Display saving of log signed accounts", SQL);
		
	}
	
	public static void saveTCT_UnderBuyersName(modelBFST_TCT_BuyersName model, Date actual_date){
		ArrayList<String> listEntityID = new ArrayList<String>();
		ArrayList<String> listProjID = new ArrayList<String>();
		ArrayList<String> listPBL = new ArrayList<String>();
		ArrayList<Integer> listSeq = new ArrayList<Integer>();
		
		for(int x = 0; x<model.getRowCount(); x++){
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
		String SQL = "SELECT sp_tag_accts_tct_under_buyers_name(ARRAY["+entity_id+"]::VARCHAR[], ARRAY["+proj_id+"]::VARCHAR[], ARRAY["+pbl_id+"]::VARCHAR[], ARRAY["+seq_no+"]::INT[], \n"+
				     "NULLIF('"+actual_date+"', 'null')::TIMESTAMP ,'"+UserInfo.Branch+"', '"+UserInfo.EmployeeCode+"')";
		db.select(SQL);
		
		FncSystem.out("Display saving of log signed accounts", SQL);
		
	}
	
	public static void savePost_Compliance(modelBFST_PostCompliance model, Date actual_date){
		ArrayList<String> listEntityID = new ArrayList<String>();
		ArrayList<String> listProjID = new ArrayList<String>();
		ArrayList<String> listPBL = new ArrayList<String>();
		ArrayList<Integer> listSeq = new ArrayList<Integer>();
		
		for(int x = 0; x<model.getRowCount(); x++){
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
		String SQL = "SELECT sp_tag_accts_post_compliance(ARRAY["+entity_id+"]::VARCHAR[], ARRAY["+proj_id+"]::VARCHAR[], ARRAY["+pbl_id+"]::VARCHAR[], ARRAY["+seq_no+"]::INT[], \n"+
				     "NULLIF('"+actual_date+"', 'null')::TIMESTAMP ,'"+UserInfo.Branch+"', '"+UserInfo.EmployeeCode+"')";
		db.select(SQL);
		
		FncSystem.out("Display saving of Post Compliance accounts", SQL);
		
	}
	
}
