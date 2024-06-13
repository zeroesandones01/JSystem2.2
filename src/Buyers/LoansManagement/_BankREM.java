/**
 * 
 */
package Buyers.LoansManagement;

import java.math.BigDecimal;
import java.util.Date;

import javax.swing.DefaultListModel;
import javax.swing.JList;

import Database.pgSelect;
import Database.pgUpdate;
import tablemodel.modelBankREMLOG;
import tablemodel.modelBankREMQualifiedAccounts;
import tablemodel.modelBankREMRelease;
import tablemodel.modelBankREMStatus;
import Functions.FncTables;
import Functions.FncSystem;
import Functions.UserInfo;

/**
 * @author John Lester Fatallo
 */
public class _BankREM {

	public _BankREM() {
		// TODO Auto-generated constructor stub
	}
	
	public static String sqlLoanStatusClients(){
		return "SELECT \n" + 
			   "TRIM(a.entity_id) AS \"Entity ID\", get_client_name(a.entity_id) AS \"Client Name\", \n" + 
			   "TRIM(a.proj_id) AS \"Proj. ID\", get_project_name(a.proj_id) AS \"Proj. Name\", \n" + 
			   "TRIM(c.unit_id) AS \"Unit ID\", c.description AS \"Unit\"\n, b.seq_no as \"Seq\" \n" + 
			   "FROM rf_bankrem_header a\n" + 
			   "LEFT JOIN rf_sold_unit b on b.entity_id = a.entity_id AND b.projcode = a.proj_id AND b.pbl_id = a.pbl_id AND b.seq_no = a.seq_no\n" + 
			   "LEFT JOIN mf_unit_info c on c.proj_id = a.proj_id and c.pbl_id = a.pbl_id and c.unit_id = a.unit_id";
	}
	
	public static String sqlREMLoanStatus(){
		return "SELECT TRIM(status_code) AS \"Status ID\", \n" + 
			   "status_desc AS \"Status Description\", \n" + 
			   "with_undertaking AS \"W/ Undertaking\" \n" + 
			   "FROM rem_buyer_status ORDER BY status_desc;";
	}
	
	public static String sqlBank(){
		return "SELECT \n" + 
			   "bank_id AS \"Bank ID\", \n" + 
			   "bank_name AS \"Bank Name\", \n" + 
			   "bank_alias AS \"Bank Alias\"\n" + 
			   "FROM mf_bank \n" + 
			   "ORDER BY bank_name;";
	}
	
	public static void displayQualifiedAccounts(Integer searchBy, String search, modelBankREMQualifiedAccounts model, JList rowHeader){
		if(model != null && rowHeader != null){
			FncTables.clearTable(model);
			
			DefaultListModel listModel = new DefaultListModel();
			rowHeader.setModel(listModel);
			
			pgSelect db = new pgSelect();
			
			String sql = "SELECT FALSE, \n" + 
						 "a.entity_id,get_client_name(a.entity_id) as \"Client Name\", \n" + 
						 "TRIM(b.phase)||'-'||TRIM(b.block)||'-'||TRIM(b.lot) as \"Ph-Blk-Lt\", \n" + 
						 "a.projcode, get_project_name(a.projcode), b.unit_id, a.pbl_id, a.seq_no\n" + 
						 "FROM rf_sold_unit a\n" + 
						 "LEFT JOIN mf_unit_info b on b.proj_id = a.projcode and b.pbl_id = a.pbl_id\n" + 
						 "WHERE --a.buyertype = '10'\n" + //XXX UNCOMMENT HERE IF THERE IS A BUYER TYPE PAGIBIG FINANCING
						 //"b.house_constructed IS NULL\n" + //MODIFIED BY MONIQUE DTD 2022-12-27 
						 "(CASE WHEN b.house_constructed IS NULL THEN TRUE WHEN (b.server_id IS NOT NULL AND b.proj_server IS NOT NULL) THEN TRUE ELSE FALSE END) \n" + 
						 "AND NOT EXISTS (SELECT * \n" + 
						 "		FROM rf_bankrem_header\n" + 
						 "		WHERE entity_id = a.entity_id\n" + 
						 "		AND proj_id = a.projcode\n" + 
						 "		AND pbl_id = a.pbl_id\n" + 
						 "		AND seq_no = a.seq_no)\n" + 
						 "AND (CASE WHEN "+searchBy+" = 0 THEN get_client_name(a.entity_id) ~*'"+search+"'\n" + 
						 "	  WHEN "+searchBy+" = 1 THEN (TRIM(b.phase)||'-'||TRIM(b.block)||'-'||TRIM(b.lot)) ~*'"+search+"'\n" + 
						 "	  WHEN "+searchBy+" = 2 THEN get_project_name(a.projcode) ~*'"+search+"'\n" + 
						 "	  ELSE b.house_constructed IS NULL END)\n" + 
						 "ORDER BY get_client_name(a.entity_id);\n";
			db.select(sql);
			
			FncSystem.out("Display Qualified Accounts", sql);
			if(db.isNotNull()){
				for(int x = 0; x<db.getRowCount(); x++){
					model.addRow(db.getResult()[x]);
					listModel.addElement(model.getRowCount());
				}
			}
		}
	}
	
	public static void displayLoanStatus(String entity_id, String proj_id, String unit_id, modelBankREMStatus model, JList rowHeader){
		if(model != null && rowHeader != null){
			FncTables.clearTable(model);
			
			DefaultListModel listModel = new DefaultListModel();
			rowHeader.setModel(listModel);
			
			pgSelect db = new pgSelect();
			String sql = "SELECT b.status_desc, a.tran_date,\n" + 
						 "0.00 , a.status_id, a.status_code, a.rec_id\n" + 
						 "FROM rf_rem_accounts_status a\n" + 
						 "LEFT JOIN rem_buyer_status b on b.status_code = a.status_code \n"+
						 "WHERE a.entity_id = '"+entity_id+"' \n"+
						 "AND a.proj_id = '"+proj_id+"' \n"+
						 "AND a.pbl_id = getinteger('"+unit_id+"')::VARCHAR ";
			db.select(sql);
			
			FncSystem.out("Display sql Loan Status", sql);
			if(db.isNotNull()){
				for(int x = 0; x<db.getRowCount(); x++){
					model.addRow(db.getResult()[x]);
					listModel.addElement(model.getRowCount());
				}
			}
		}
	}
	
	//Displays the LOG Validity Extensions
	public static void displayLOG(String entity, String proj_id, String unit_id, modelBankREMLOG model, JList rowHeader){
		
		if(model != null && rowHeader != null){
			FncTables.clearTable(model);
			
			DefaultListModel listModel = new DefaultListModel();
			rowHeader.setModel(listModel);
			
			pgSelect db = new pgSelect();
			
			String sql = "select\n" + 
						 "b.bank_name, a.log_date, a.log_amt, a.effectivity, \n" + 
						 "a.rem_fees, a.date_paid, mri_fire_fees, a.date_created, \n" + 
						 "get_employee_name(a.created_by), a.bank_id, a.created_by, \n" + 
						 "a.rec_id\n" + 
						 "FROM rf_rem_log_effectivity a\n" + 
						 "left join mf_bank b on b.bank_id = a.bank_id\n" + 
						 "WHERE a.entity_id = '"+entity+"'\n" +
						 "and a.proj_id = '"+proj_id+"' \n"+
						 "AND a.pbl_id = getinteger('"+unit_id+"')::VARCHAR;";
			db.select(sql);
			FncSystem.out("Display sql for LOG", sql);
			
			if(db.isNotNull()){
				for(int x = 0; x<db.getRowCount(); x++){
					model.addRow(db.getResult()[x]);
					listModel.addElement(model.getRowCount());
				}
			}
		}
	}
	
	//Displays Released Undertaking
	public static void displayReleasedUndertaking(String entity_id, String unit_id,modelBankREMRelease model ,JList rowHeader){
		if(model != null && rowHeader != null){
			FncTables.clearTable(model);
			
			DefaultListModel listModel = new DefaultListModel();
			rowHeader.setModel(listModel);
		
			pgSelect db = new pgSelect();
			String sql = "SELECT \n" + 
						 "loan_rlsd_date, utaking_expiry_date, \n" + 
						 "transmitted_doas, transfer_fees \n" + 
						 "FROM rf_bankrem_header \n" + 
						 "WHERE entity_id = '"+entity_id+"' \n" + 
						 "AND unit_id= '"+unit_id+"';\n";
			
			db.select(sql);
			FncSystem.out("Display sql for Released Undertaking", sql);
			
			if(db.isNotNull()){
				for(int x = 0; x<db.getRowCount(); x++){
					model.addRow(db.getResult()[x]);
					listModel.addElement(model.getRowCount());
				}
			}
		}
	}
	
	public static String newBatchNo(){
		String batch_no = "";
		
		pgSelect db = new pgSelect();
		String sql = "select to_char(coalesce(max(batch_no::integer), 0) + 1, 'FM0000000000') from rf_bankrem_header;\n";
		db.select(sql);
		
		if(db.isNotNull()){
			batch_no = (String) db.getResult()[0][0];
		}
		
		return batch_no; 
	}
	
	//Counts the selected accounts to be qualified
	public static Integer getSelectedQualifiedAcct(modelBankREMQualifiedAccounts model){
		Integer count = 0;
		
		for(int x = 0; x<model.getRowCount(); x++){
			Boolean isSelected = (Boolean) model.getValueAt(x, 0);
			if(isSelected){
				count ++;
			}
		}
		return count;
	}
	
	//Save the qualified accounts
	public static void saveQualifiedAccounts(modelBankREMQualifiedAccounts model){
		String batch_no = newBatchNo();
		
		pgUpdate db = new pgUpdate();
		
		for(int x = 0; x < model.getRowCount(); x++){
			Boolean isSelected = (Boolean) model.getValueAt(x, 0);
			
			if(isSelected){
				String entity_id = (String) model.getValueAt(x, 1);
				String proj_id = (String) model.getValueAt(x, 4);
				String unit_id = (String) model.getValueAt(x, 6);
				String pbl_id = (String) model.getValueAt(x, 7);
				Integer seq_no = (Integer) model.getValueAt(x, 8);
				
				String sql = "INSERT INTO rf_bankrem_header(rec_id, entity_id, proj_id, pbl_id, seq_no, date_qualified, batch_no, unit_id)\n" + 
							 "VALUES ((select coalesce(max(rec_id), 0) + 1 from rf_bankrem_header), '"+entity_id+"', '"+proj_id+"', '"+pbl_id+"', \n"+
							 ""+seq_no+", now(),\n" + 
							 "'"+batch_no+"', '"+unit_id+"');\n";
				db.executeUpdate(sql, true);
			}
		}
		db.commit();
	}
	
	//Saves the Client REM Status
	public static void saveREMStatus(String entity_id, String proj_id, String unit_id, String seq_no, String loan_stat_id, Date loan_stat_date){
		pgUpdate db = new pgUpdate();
		
//		String sql = "INSERT INTO rf_rem_accounts_status(rec_id, unit_id, entity_id, status_id, status_date, tagged_by)\n" + 
//					 "VALUES ((SELECT COALESCE(max(rec_id), 0) + 1 FROM rf_bankrem_status), '"+unit_id+"', '"+entity_id+"', \n"+
//					 "'"+loan_stat_id+"', '"+loan_stat_date+"', '"+UserInfo.EmployeeCode+"')";
		String SQL = "INSERT INTO public.rf_rem_accounts_status(entity_id, proj_id, pbl_id, seq_no, status_code, tran_date, bank_id, created_by, date_created, edited_by, date_edited, status_id, is_old_status, orig_status_id)\n" + 
				"	VALUES ('"+entity_id+"', '"+proj_id+"', getinteger('"+unit_id+"')::VARCHAR, "+seq_no+", '"+loan_stat_id+"', '"+loan_stat_date+"', null, '"+UserInfo.EmployeeCode+"', now(), null, null, 'A', NULL, NULL);";
		
		db.executeUpdate(SQL, true);
		db.commit();
	}
	
	//Updates the Client REM Status
	public static void updateREMStatus(String loan_stat_id, Date loan_stat_date, Integer rec_id){
		pgUpdate db = new pgUpdate();
		
		String sql = "UPDATE rf_rem_accounts_status\n" + 
					 "SET status_code = '"+loan_stat_id+"', \n" + 
					 "tran_date = '"+loan_stat_date+"', \n" + 
					 "edited_by = '"+UserInfo.EmployeeCode+"', date_edited = now() \n" + 
					 "WHERE rec_id = "+rec_id+"";
		db.executeUpdate(sql, true);
		db.commit();
	}
	
	//Saves the REM LOG Validity
	public static void saveREMLOGValidity(String entity_id, String proj_id, String unit_id, String bank_id, BigDecimal log_amt, 
		Date date_released,Date effectivity, BigDecimal rem_fees, BigDecimal mri_fire_fees, Date date_paid){
		pgUpdate db = new pgUpdate();
		
		String sql = "INSERT INTO rf_bankrem_log_effectivity(\n" + 
					 "bankrem_log_id, entity_id, projcode, bank_id, log_amt, log_date, effectivity, \n"+
					 "rem_fees, mri_fire_fees, date_paid, update_by, update_date, unit_id)\n" + 
					 "VALUES ((SELECT COALESCE(max(bankrem_log_id), 0) +1 FROM rf_bankrem_log_effectivity), \n"+
					 "'"+entity_id+"', '"+proj_id+"', '"+bank_id+"', "+log_amt+", '"+date_released+"', '"+effectivity+"', \n"+
					 ""+rem_fees+", "+mri_fire_fees+", '"+date_paid+"', '"+UserInfo.EmployeeCode+"', now(), '"+unit_id+"');\n";
		db.executeUpdate(sql, true);
		db.commit();
	}
	
	//Saves the Released Undertaking
	public static void saveREMRelease(String entity_id, String unit_id, Date loan_released_date, Date undertaking_exp_date, Date doas_transmitted, BigDecimal transfer_cost_paid){
		pgUpdate db = new pgUpdate();
		
		String sql = "UPDATE rf_bankrem_header\n" + 
					 "SET loan_rlsd_date = '"+loan_released_date+"',\n" + 
					 "transfer_fees = "+transfer_cost_paid+", \n" + 
					 "utaking_expiry_date = '"+undertaking_exp_date+"',\n" + 
					 "transmitted_doas = '"+doas_transmitted+"'\n" + 
					 "WHERE entity_id = '"+entity_id+"'\n" + 
					 "AND unit_id = '"+unit_id+"'";
		db.executeUpdate(sql, true);
		db.commit();
		
	}
	
}
