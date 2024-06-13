package Projects.ConstructionManagement;

import java.util.ArrayList;
import java.util.Date;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;

import Database.pgSelect;
import Functions.FncGlobal;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.UserInfo;
import tablemodel.modelBankFinanceTO;
import tablemodel.modelCashTO;

public class _GenerateForUnitTurnOverOrientation {

	public static String sqlBatchNo(String notice_id){
		return "select distinct batch_no as \"Batch No.\", dateprep::date as \"Date Prep\" from rf_client_notices where (notice_id = '"+notice_id+"' or '"+notice_id+"' = '') order by batch_no desc;";
	}
	
	public static void displayQualifiedBankFinanceTO(modelBankFinanceTO model, JList rowHeader, String co_id, String proj_id, String phase, String batch_no){
		if(model != null && rowHeader != null){
			FncTables.clearTable(model);

			DefaultListModel listModel = new DefaultListModel();
			rowHeader.setModel(listModel);

			pgSelect db = new pgSelect();

			String SQL = "SELECT * FROM view_qualified_notice_turnover_bank_finance(NULLIF('"+co_id+"', 'null'), NULLIF('"+proj_id+"', 'null'), NULLIF('"+phase+"', 'null'), NULLIF('"+batch_no+"', 'null'));";
			db.select(SQL);

			FncSystem.out("Display SQL for Bank Finance Notice", SQL);

			if(db.isNotNull()){
				for(int x= 0; x<db.getRowCount(); x++){
					model.addRow(db.getResult()[x]);
					listModel.addElement(model.getRowCount());
				}
			}else{
				JOptionPane.showMessageDialog(FncGlobal.homeMDI, "No Qualified Accounts", "First Notice", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}
	public static void displayQualifiedCashTO(modelCashTO model, JList rowHeader, String co_id, String proj_id, String phase, String batch_no){
		if(model != null && rowHeader != null){
			FncTables.clearTable(model);

			DefaultListModel listModel = new DefaultListModel();
			rowHeader.setModel(listModel);

			pgSelect db = new pgSelect();

			String SQL = "SELECT * FROM view_qualified_notice_turnover_cash(NULLIF('"+co_id+"', 'null'), NULLIF('"+proj_id+"', 'null'), NULLIF('"+phase+"', 'null'), NULLIF('"+batch_no+"', 'null'));";
			db.select(SQL);

			FncSystem.out("Display SQL for Cash", SQL);

			if(db.isNotNull()){
				for(int x= 0; x<db.getRowCount(); x++){
					model.addRow(db.getResult()[x]);
					listModel.addElement(model.getRowCount());
				}
			}else{
				JOptionPane.showMessageDialog(FncGlobal.homeMDI, "No Qualified Accounts", "First Notice", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}
	
	public static String saveNoticeTurnover_BankFinance(modelBankFinanceTO model, Date orientation_date, String orientation_time, String venue_id){
		
		String batch_no = "";
		
		ArrayList<String> listEntityID = new ArrayList<String>();
		ArrayList<String> listProjID = new ArrayList<String>();
		ArrayList<String> listPBL = new ArrayList<String>();
		ArrayList<Integer> listSeq = new ArrayList<Integer>();


		for(int x=0; x<model.getRowCount(); x++){
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
		String SQL = "SELECT sp_tag_notice_turnover_bank_finance(ARRAY["+entity_id+"]::VARCHAR[], ARRAY["+proj_id+"]::VARCHAR[], ARRAY["+pbl_id+"]::VARCHAR[], ARRAY["+seq_no+"]::INT[], NULLIF('"+orientation_date+"', 'null')::TIMESTAMP, '"+orientation_time+"', '"+venue_id+"' ,'"+UserInfo.EmployeeCode+"')";
		db.select(SQL);
		
		FncSystem.out("Display SQL for Tagging of Notice Turnover Bank Finance", SQL);
		
		if(db.isNotNull()){
			batch_no = (String) db.getResult()[0][0];
		}
		
		return batch_no;
	}
}
