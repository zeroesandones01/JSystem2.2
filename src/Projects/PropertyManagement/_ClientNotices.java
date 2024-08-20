package Projects.PropertyManagement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import Buyers.CreditandCollections._RealTimeDebit;
import Database.pgSelect;
import Database.pgUpdate;
import Functions.FncGlobal;
import Functions.FncReport;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.UserInfo;
import tablemodel.modelSpecialNotices;

public class _ClientNotices {

	public Boolean NoticeExists(String strID, String strProject, String strUnit, String strSeq, String strNotice) {
		Boolean blnExst = false;
		Integer intCount = FncGlobal.GetInteger("select count(*)::int \n" +
				"from rf_client_notices \n" +
				"where entity_id = '"+strID+"' and projcode = '"+strProject+"' and pbl_id = '"+strUnit+"' and seq_no = "+strSeq+" \n" +
				"and status_id != 'I' and coalesce(datesent::char(10), '') = '' and notice_id = '"+strNotice+"'");

		System.out.println("");
		System.out.println("Number of notices existing: " + intCount.toString());
		System.out.println("");
		System.out.println("select count(*)::int \n" +
				"from rf_client_notices \n" +
				"where entity_id = '"+strID+"' and projcode = '"+strProject+"' and pbl_id = '"+strUnit+"' and seq_no = "+strSeq+" \n" +
				"and status_id != 'I' and coalesce(datesent::char(10), '') = '' and notice_id = '"+strNotice+"'");

		if (intCount > 0) {
			blnExst = true;
		} else {
			blnExst = false;
		}

		return blnExst;
	}

	public Boolean SubjectToFinalNotice(String strID, String strProject, String strUnit, String strSeq) {
		Boolean blnFin = false;
		Integer intFin = 0;

		intFin = FncGlobal.GetInteger("select count(*)::int \n" +
				"from rf_client_notices \n" +
				"where entity_id = '"+strID+"' and projcode = '"+strProject+"' and pbl_id = '"+strUnit+"' and seq_no = "+strSeq+" \n" + 
				"and status_id != 'I' and notice_id = '127' and coalesce(datesent::char(10), '') <> ''");

		System.out.println("");
		System.out.println("select count(*)::int \n" +
				"from rf_client_notices \n" +
				"where entity_id = '"+strID+"' and projcode = '"+strProject+"' and pbl_id = '"+strUnit+"' and seq_no = "+strSeq+" \n" + 
				"and status_id != 'I' and notice_id = '127' and coalesce(datesent::char(10), '') <> ''");

		if (intFin>0) {
			blnFin = FncGlobal.GetBoolean("select date_part('day', Now() - datesent)>=15 \n" +
					"from rf_client_notices \n" +
					"where entity_id = '"+strID+"' and projcode = '"+strProject+"' and pbl_id = '"+strUnit+"' and seq_no = "+strSeq+" \n" + 
					"and status_id != 'I' and notice_id = '127' and coalesce(datesent::char(10), '') <> ''");

			System.out.println("");
			System.out.println("select date_part('day', Now() - datesent)>=15 \n" +
					"from rf_client_notices \n" +
					"where entity_id = '"+strID+"' and projcode = '"+strProject+"' and pbl_id = '"+strUnit+"' and seq_no = "+strSeq+" \n" + 
					"and status_id != 'I' and notice_id = '127' and coalesce(datesent::char(10), '') <> ''");
		} else {
			blnFin = false;
		}

		return blnFin;
	}

	public static String NoticeSQL() {
		return "select notice_id as \"Notice ID\" , notice_desc as \"Notice\" from mf_notice_type where notice_id in ('62', '63', '69', '134', '101', '143') and status_id = 'A'";
	}

	public static String Batch(String strNoticeID) {
		return "select distinct batch_no as \"Batch No.\", dateprep::date as \"Date Prep\" from rf_client_notices where (notice_id = '"+strNoticeID+"' or '"+strNoticeID+"' = '') order by batch_no desc;";
	}

	/*	Qualified for First Filing First Notice	*/
	public Boolean QualifiedForFirstFilingFirstNotice(String strID, String strProject, String strUnit, String strSeq, Integer intRec) {
		Boolean blnProceed = true;

		return blnProceed;
	}
	/*	Qualified for First Filing First Notice	*/

	/*	Qualified for First Filing Final Notice	*/
	public Boolean QualifiedForFirstFilingFinalNotice(String strID, String strProject, String strUnit, String strSeq, Integer intRec) {
		Boolean blnProceed = false;

		System.out.println("");
		System.out.println("strID: " + strID);
		System.out.println("strProject: " + strProject);
		System.out.println("strUnit: " + strUnit);
		System.out.println("strSeq: " + strSeq);

		if (SubjectToFinalNotice(strID, strProject, strUnit, strSeq)) {
			blnProceed = true;
		} else {
			if (JOptionPane.showConfirmDialog(null, "The client is not yet qualified for final\nnotice. Do you wish to proceed?", 
					"Confirm", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION) {
				blnProceed = true;
			} else {
				blnProceed = false;
			}
		}

		return blnProceed;
	}

	public static void displayNTC_Qualified_First_Notice(modelSpecialNotices model, JList rowHeader,String co_id ,String proj_id, String phase ,String batch_no){
		if(model != null && rowHeader != null){
			FncTables.clearTable(model);

			DefaultListModel listModel = new DefaultListModel();
			rowHeader.setModel(listModel);

			pgSelect db = new pgSelect();

			String SQL = "SELECT * FROM view_qualified_ntc_incomplete_docs_first_notice(NULLIF('"+co_id+"', 'null'), NULLIF('"+proj_id+"', 'null'), NULLIF('"+phase+"', 'null') , NULLIF('"+batch_no+"', 'null'))";
			db.select(SQL);

			FncSystem.out("Display SQL for Display of NTC Qualified First Notice", SQL);

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

	public static void displayNTC_Qualified_Final_Notice(modelSpecialNotices model, JList rowHeader, String co_id ,String proj_id, String batch_no){
		if(model != null && rowHeader != null){
			FncTables.clearTable(model);

			DefaultListModel listModel = new DefaultListModel();
			rowHeader.setModel(listModel);

			pgSelect db = new pgSelect();

			String SQL = "SELECT * FROM view_qualified_ntc_incomplete_docs_final_notice(NULLIF('"+co_id+"', 'null'), NULLIF('"+proj_id+"', 'null'), NULLIF('"+batch_no+"', 'null'))";
			db.select(SQL);

			FncSystem.out("Display SQL for Display of NTC Qualified Final Notice", SQL);

			if(db.isNotNull()){
				for(int x= 0; x<db.getRowCount(); x++){
					model.addRow(db.getResult()[x]);
					listModel.addElement(model.getRowCount());
				}
			}else{
				JOptionPane.showMessageDialog(FncGlobal.homeMDI, "No Qualified Accounts", "Final Notice", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}

	public static void displayNOA_Qualified_FirstNotice(modelSpecialNotices model, JList rowHeader, String co_id ,String proj_id, String batch_no){
		if(model != null && rowHeader != null){
			model.clear();

			DefaultListModel listModel = new DefaultListModel();
			rowHeader.setModel(listModel);

			pgSelect db = new pgSelect();
			String SQL = "SELECT * FROM view_qualified_noa_first_notice(NULLIF('"+co_id+"', 'null'), NULLIF('"+proj_id+"', 'null'), NULLIF('"+batch_no+"', 'null'))";
			db.select(SQL);

			FncSystem.out("Display SQL for Qualified First NOA", SQL);

			if(db.isNotNull()){
				for(int x= 0; x<db.getRowCount(); x++){
					model.addRow(db.getResult()[x]);
					listModel.addElement(model.getRowCount());
				}
			}
		}
	}

	public static void displayNOA_Qualified_FinalNotice(modelSpecialNotices model, JList rowHeader, String co_id ,String proj_id, String batch_no){
		if(model != null && rowHeader != null){
			model.clear();

			DefaultListModel listModel = new DefaultListModel();
			rowHeader.setModel(listModel);

			pgSelect db = new pgSelect();
			String SQL = "SELECT * FROM view_qualified_noa_final_notice(NULLIF('"+co_id+"', 'null'), NULLIF('"+proj_id+"', 'null'), NULLIF('"+batch_no+"', 'null'))";
			db.select(SQL);

			FncSystem.out("Display SQL for Qualified Final NOA", SQL);

			if(db.isNotNull()){
				for(int x= 0; x<db.getRowCount(); x++){
					model.addRow(db.getResult()[x]);
					listModel.addElement(model.getRowCount());
				}
			}
		}
	}

	public static void displayNoticeTurnover_HDMF_Qualified(modelSpecialNotices model, JList rowHeader, String co_id, String proj_id, String phase ,String batch_no){
		if(model != null && rowHeader != null){
			model.clear();

			DefaultListModel listModel = new DefaultListModel();
			rowHeader.setModel(listModel);

			pgSelect db = new pgSelect();
			String SQL = "SELECT * FROM view_qualified_notice_of_turnover_hdmf(NULLIF('"+co_id+"', 'null'), NULLIF('"+proj_id+"', 'null'), NULLIF('"+phase+"', 'null') ,NULLIF('"+batch_no+"', 'null'));";
			db.select(SQL);

			if(db.isNotNull()){
				for(int x= 0; x<db.getRowCount(); x++){
					model.addRow(db.getResult()[x]);
					listModel.addElement(model.getRowCount());
				}
			}
		}
	}

	public static void displayNoticeTurnover_Cash_Qualified(modelSpecialNotices model, JList rowHeader, String co_id, String proj_id, String phase ,String batch_no){
		if(model != null && rowHeader != null){
			model.clear();

			DefaultListModel listModel = new DefaultListModel();
			rowHeader.setModel(listModel);

			pgSelect db = new pgSelect();
			String SQL = "SELECT * FROM view_qualified_notice_of_turnover_cash(NULLIF('"+co_id+"', 'null'), NULLIF('"+proj_id+"', 'null'), NULLIF('"+phase+"', 'null') ,NULLIF('"+batch_no+"', 'null'));";
			db.select(SQL);

			if(db.isNotNull()){
				for(int x= 0; x<db.getRowCount(); x++){
					model.addRow(db.getResult()[x]);
					listModel.addElement(model.getRowCount());
				}
			}
		}
	}
	
	public static void displayNoticeTurnover_LotOnly_Qualified(modelSpecialNotices model, JList rowHeader, String co_id, String proj_id, String phase ,String batch_no){
		if(model != null && rowHeader != null){
			model.clear();

			DefaultListModel listModel = new DefaultListModel();
			rowHeader.setModel(listModel);

			pgSelect db = new pgSelect();
			String SQL = "SELECT * FROM view_qualified_notice_of_turnover_lot_only(NULLIF('"+co_id+"', 'null'), NULLIF('"+proj_id+"', 'null'), NULLIF('"+phase+"', 'null') ,NULLIF('"+batch_no+"', 'null'));";
			db.select(SQL);

			if(db.isNotNull()){
				for(int x= 0; x<db.getRowCount(); x++){
					model.addRow(db.getResult()[x]);
					listModel.addElement(model.getRowCount());
				}
			}
		}
	}
	
	//added by jari cruz asof march 10 2023
	
	public static void displayNoticeTurnover_Ihf_Qualified(modelSpecialNotices model, JList rowHeader, String co_id, String proj_id, String phase ,String batch_no){
		if(model != null && rowHeader != null){
			model.clear();

			DefaultListModel listModel = new DefaultListModel();
			rowHeader.setModel(listModel);

			pgSelect db = new pgSelect();
			String SQL = "SELECT * FROM view_qualified_notice_of_turnover_ihf(NULLIF('"+co_id+"', 'null'), NULLIF('"+proj_id+"', 'null'), NULLIF('"+phase+"', 'null') ,NULLIF('"+batch_no+"', 'null'));";
			db.select(SQL);

			if(db.isNotNull()){
				for(int x= 0; x<db.getRowCount(); x++){
					model.addRow(db.getResult()[x]);
					listModel.addElement(model.getRowCount());
				}
			}
		}
	}
	
	public static void displayNoticeAssumedTurnover(modelSpecialNotices model, JList rowHeader, String co_id, String proj_id, String phase ,String batch_no){
		if(model != null && rowHeader != null){
			model.clear();

			DefaultListModel listModel = new DefaultListModel();
			rowHeader.setModel(listModel);

			pgSelect db = new pgSelect();
			String SQL = "SELECT * FROM view_qualified_notice_of_assumed_turnover(NULLIF('"+co_id+"', 'null'), NULLIF('"+proj_id+"', 'null'), NULLIF('"+phase+"', 'null') ,NULLIF('"+batch_no+"', 'null'));";
			db.select(SQL);

			if(db.isNotNull()){
				for(int x= 0; x<db.getRowCount(); x++){
					model.addRow(db.getResult()[x]);
					listModel.addElement(model.getRowCount());
				}
			}
		}
	}
	
	public static void displayPMD_FD_Dues(modelSpecialNotices model, JList rowHeader, String co_id, String proj_id, String phase, String buyer_type, String hse_model ,String batch_no){
		if(model != null && rowHeader != null){
			model.clear();

			DefaultListModel listModel = new DefaultListModel();
			rowHeader.setModel(listModel);

			pgSelect db = new pgSelect();
			String SQL = "SELECT * FROM view_qualified_pmd_fd_dues(NULLIF('"+co_id+"', 'null'), NULLIF('"+proj_id+"', 'null'), NULLIF('"+phase+"', 'null'), '"+buyer_type+"', '"+hse_model+"' ,NULLIF('"+batch_no+"', 'null'));";
			db.select(SQL);
			
			FncSystem.out("Display SQL for qualified Accts", SQL);

			if(db.isNotNull()){
				for(int x= 0; x<db.getRowCount(); x++){
					model.addRow(db.getResult()[x]);
					listModel.addElement(model.getRowCount());
				}
			}
		}
	}
	
	
	
	public static void displayNoticePostFiling_FirstNotice_Qualified(modelSpecialNotices model, JList rowHeader, String co_id, String proj_id, String phase ,String batch_no){
		if(model != null && rowHeader != null){
			model.clear();
			
			DefaultListModel listModel = new DefaultListModel();
			rowHeader.setModel(listModel);
			
			pgSelect db = new pgSelect();
			String SQL = "SELECT * from view_qualified_post_filing_first_notice(NULLIF('"+co_id+"', 'null'), NULLIF('"+proj_id+"', 'null'), NULLIF('"+phase+"', 'null'), NULLIF('"+batch_no+"','null'))";
			db.select(SQL);
			FncSystem.out("SQL for Post Filing 1st Notice", SQL);
			
			if(db.isNotNull()){
				for(int x= 0; x<db.getRowCount(); x++){
					model.addRow(db.getResult()[x]);
					listModel.addElement(model.getRowCount());
				}
			}
		}
	}
	
	public static void displayNoticePostFiling_FinalNotice_Qualified(modelSpecialNotices model, JList rowHeader, String co_id, String proj_id, String phase, String batch_no){
		if(model != null && rowHeader != null){
			model.clear();
			
			DefaultListModel listModel = new DefaultListModel();
			rowHeader.setModel(listModel);
			
			pgSelect db = new pgSelect();
			String SQL = "SELECT * from view_qualified_post_filing_final_notice(NULLIF('"+co_id+"', 'null'), NULLIF('"+proj_id+"', 'null'), NULLIF('"+phase+"', 'null'), NULLIF('"+batch_no+"','null'))";
			db.select(SQL);
			
			FncSystem.out("SQL for Post Filing Final Notice", SQL);
			
			if(db.isNotNull()){
				for(int x= 0; x<db.getRowCount(); x++){
					model.addRow(db.getResult()[x]);
					listModel.addElement(model.getRowCount());
				}
			}
		}
	}

	public static void displayNoticeTurnover_BankFinance_Qualified(modelSpecialNotices model, JList rowHeader, String co_id, String proj_id, String batch_no){
		if(model != null && rowHeader != null){
			model.clear();

			DefaultListModel listModel = new DefaultListModel();
			rowHeader.setModel(listModel);

			pgSelect db = new pgSelect();
			String SQL = "";
			db.select(SQL);

			if(db.isNotNull()){
				for(int x= 0; x<db.getRowCount(); x++){
					model.addRow(db.getResult()[x]);
					listModel.addElement(model.getRowCount());
				}
			}
		}
	}

	public static String saveNOA_FirstNotice(DefaultTableModel model){
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
		String SQL = "SELECT sp_tag_noa_first_notice(ARRAY["+entity_id+"]::VARCHAR[], ARRAY["+proj_id+"]::VARCHAR[], ARRAY["+pbl_id+"]::VARCHAR[], ARRAY["+seq_no+"]::INT[], '"+UserInfo.EmployeeCode+"')";
		db.select(SQL);

		if(db.isNotNull()){
			batch_no = (String) db.getResult()[0][0];
		}

		return batch_no;
	}

	public static String saveNOA_FinalNotice(DefaultTableModel model){
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
		String SQL = "SELECT sp_tag_noa_final_notice(ARRAY["+entity_id+"]::VARCHAR[], ARRAY["+proj_id+"]::VARCHAR[], ARRAY["+pbl_id+"]::VARCHAR[], ARRAY["+seq_no+"]::INT[], '"+UserInfo.EmployeeCode+"')";
		db.select(SQL);

		FncSystem.out("Display Taggging of NOA Final Notice", SQL);

		if(db.isNotNull()){
			batch_no = (String) db.getResult()[0][0];
		}

		return batch_no;
	}

	public static String saveNoticeOfTurnover_HDMF(DefaultTableModel model){
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
		String SQL = "SELECT sp_tag_notice_of_turnover_hdmf(ARRAY["+entity_id+"]::VARCHAR[], ARRAY["+proj_id+"]::VARCHAR[], ARRAY["+pbl_id+"]::VARCHAR[], ARRAY["+seq_no+"]::INT[], '"+UserInfo.EmployeeCode+"')";
		db.select(SQL);

		FncSystem.out("Display Taggging of NOA Final Notice", SQL);

		if(db.isNotNull()){
			batch_no = (String) db.getResult()[0][0];
		}

		return batch_no;
	}

	public static String saveNTC_IncompleteDocs_FirstNotice(DefaultTableModel model){

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

		String SQL = "SELECT sp_tag_ntc_first_notice(ARRAY["+entity_id+"]::VARCHAR[], ARRAY["+proj_id+"]::VARCHAR[], ARRAY["+pbl_id+"]::VARCHAR[], ARRAY["+seq_no+"]::INT[], '"+UserInfo.EmployeeCode+"')";
		db.select(SQL);
		FncSystem.out("Display SQL for NTC First Notice", SQL);

		if(db.isNotNull()){
			batch_no = (String) db.getResult()[0][0];
		}

		return batch_no;
	}

	/*public static String saveNTC_IncompleteDocs_FirstNotice(DefaultTableModel model){

		String batch_no = _PagibigNotices.getNewBatchNo();


		pgSelect db = new pgSelect();
		for(int x=0; x<model.getRowCount(); x++){
			Boolean isSelected = (Boolean) model.getValueAt(x, 0);

			if(isSelected){
				String entity_id = (String) model.getValueAt(x, 7);
				String proj_id = (String) model.getValueAt(x, 8);
				String pbl_id = (String) model.getValueAt(x, 9);
				Integer seq_no = (Integer) model.getValueAt(x, 10);

				String SQL = "SELECT sp_tag_ntc_first_notice_v2('"+entity_id+"', '"+proj_id+"', '"+pbl_id+"', "+seq_no+", '"+UserInfo.EmployeeCode+"', '"+batch_no+"')";
				db.select(SQL);
				FncSystem.out("Display SQL for NTC First Notice", SQL);
			}
		}

		return batch_no;
	}*/

	public static String saveNTC_IncompleteDocs_FinalNotice(modelSpecialNotices model){

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

		String SQL = "SELECT sp_tag_ntc_final_notice(ARRAY["+entity_id+"]::VARCHAR[], ARRAY["+proj_id+"]::VARCHAR[], ARRAY["+pbl_id+"]::VARCHAR[], ARRAY["+seq_no+"]::INT[], '"+UserInfo.EmployeeCode+"')";
		db.select(SQL);

		FncSystem.out("Display SQL for NTC First Notice", SQL);

		if(db.isNotNull()){
			batch_no = (String) db.getResult()[0][0];
		}

		return batch_no;
	}
	
	public static String savePostFiling_FirstNotice(DefaultTableModel model){
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
		String SQL = "SELECT sp_tag_post_filing_first_notice(ARRAY["+entity_id+"]::VARCHAR[], ARRAY["+proj_id+"]::VARCHAR[], ARRAY["+pbl_id+"]::VARCHAR[], ARRAY["+seq_no+"]::INT[], '"+UserInfo.EmployeeCode+"')";
		db.select(SQL);

		if(db.isNotNull()){
			batch_no = (String) db.getResult()[0][0];
		}

		return batch_no;
	}
	
	public static String savePostFiling_FinalNotice(DefaultTableModel model){
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
		String SQL = "SELECT sp_tag_post_filing_final_notice(ARRAY["+entity_id+"]::VARCHAR[], ARRAY["+proj_id+"]::VARCHAR[], ARRAY["+pbl_id+"]::VARCHAR[], ARRAY["+seq_no+"]::INT[], '"+UserInfo.EmployeeCode+"')";
		db.select(SQL);

		if(db.isNotNull()){
			batch_no = (String) db.getResult()[0][0];
		}

		return batch_no;
	}
	
	public static String saveNoticeTurnover_Cash(DefaultTableModel model){
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
		String SQL = "SELECT sp_tag_notice_turnover_cash(ARRAY["+entity_id+"]::VARCHAR[], ARRAY["+proj_id+"]::VARCHAR[], ARRAY["+pbl_id+"]::VARCHAR[], ARRAY["+seq_no+"]::INT[], '"+UserInfo.EmployeeCode+"')";
		db.select(SQL);

		if(db.isNotNull()){
			batch_no = (String) db.getResult()[0][0];
		}

		return batch_no;
	}
	
	public static String saveNoticeTurnover_LotOnly(DefaultTableModel model){
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
		String SQL = "SELECT sp_tag_notice_turnover_lot_only(ARRAY["+entity_id+"]::VARCHAR[], ARRAY["+proj_id+"]::VARCHAR[], ARRAY["+pbl_id+"]::VARCHAR[], ARRAY["+seq_no+"]::INT[], '"+UserInfo.EmployeeCode+"')";
		db.select(SQL);

		if(db.isNotNull()){
			batch_no = (String) db.getResult()[0][0];
		}

		return batch_no;
	}
	
	public static String saveNoticeAssumed_Turnover(DefaultTableModel model){
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
		String SQL = "SELECT sp_tag_notice_assumed_turnover(ARRAY["+entity_id+"]::VARCHAR[], ARRAY["+proj_id+"]::VARCHAR[], ARRAY["+pbl_id+"]::VARCHAR[], ARRAY["+seq_no+"]::INT[], '"+UserInfo.EmployeeCode+"')";
		db.select(SQL);

		if(db.isNotNull()){
			batch_no = (String) db.getResult()[0][0];
		}

		return batch_no;
	}
	
	public static String saveFinalDemand_DuePmt(DefaultTableModel model){
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
		String SQL = "SELECT sp_tag_notice_pmd_fd_pmts(ARRAY["+entity_id+"]::VARCHAR[], ARRAY["+proj_id+"]::VARCHAR[], ARRAY["+pbl_id+"]::VARCHAR[], ARRAY["+seq_no+"]::INT[], '"+UserInfo.EmployeeCode+"')";
		db.select(SQL);

		if(db.isNotNull()){
			batch_no = (String) db.getResult()[0][0];
		}

		return batch_no;
	}
	
	/*	Qualified for First Filing Final Notice	*/

	public void GenerateFirstFilingNoticeTransmittal(String strBat, String StrCo, String strNotice) {	
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("01_Company", StrCo);
		mapParameters.put("02_BatchNo", strBat);
		mapParameters.put("03_UserName", _RealTimeDebit.GetName(UserInfo.EmployeeCode));
		mapParameters.put("04_UserCode", FncGlobal.GetString("select login_id from rf_system_user where emp_code = '"+UserInfo.EmployeeCode+"'"));
		mapParameters.put("05_NoticeType", strNotice);
		FncReport.generateReport("/Reports/rpt_QualifiedforFirstFilingNoticeTransmittal.jasper", "Qualified for First Filing Notice Transmittal", "", mapParameters);
	}

	public void GenerateFirstFilingNoticePhilPostTransmittal(String strBat, String StrCo, String strNotice) {
		pgSelect dbExec = new pgSelect();
		String strSQL = "select distinct region::varchar(155) \n" +
				"from view_notice_transmittal('"+strBat+"') \n" +
				"where entity_id in (select y.entity_id \n" +
				"from tmp_hdmf x \n" +
				"inner join rf_entity y on x.client_name = y.entity_name \n" +
				"where x.emp_code = '"+UserInfo.EmployeeCode+"')";

		System.out.println("");
		System.out.println("strSQL: " + strSQL);
		dbExec.select(strSQL);
		System.out.println("Row Count: " + strSQL); 

		if (dbExec.getRowCount() > 0) {
			for (int x = 0; x < dbExec.getRowCount(); x++) {
				Map<String, Object> mapParameters = new HashMap<String, Object>();
				mapParameters.put("01_Company", StrCo);
				mapParameters.put("02_BatchNo", strBat);
				mapParameters.put("03_UserName", _RealTimeDebit.GetName(UserInfo.EmployeeCode));
				mapParameters.put("04_AsOfDate", FncGlobal.getDateSQL().toString());
				mapParameters.put("05_NoticeType", strNotice);
				mapParameters.put("06_region", dbExec.getResult()[x][0]);
				FncReport.generateReport("/Reports/rpt_QualifiedforFirstFilingNoticePhilPostTransmittal.jasper", "Qualified for First Filing Notice PhilPost Transmittal - " + dbExec.getResult()[x][0], "", mapParameters);		
			}
		}
	}

	public static void displayQualifiedForFirstFiling_Notice(modelSpecialNotices model, JList rowHeader, String strNotice, String strBatch){
		if(model != null && rowHeader != null){
			FncTables.clearTable(model);
			DefaultListModel listModel = new DefaultListModel();
			rowHeader.setModel(listModel);

			if (strBatch=="null" || strBatch==null) {
				strBatch = ""; 
			}

			pgSelect db = new pgSelect();
			String SQL = "select * from view_qualifiedforfirstfiling('"+strNotice+"', '"+strBatch+"')";
			db.select(SQL);

			System.out.println("");
			System.out.println(SQL);

			if(db.isNotNull()){
				for(int x= 0; x<db.getRowCount(); x++){
					model.addRow(db.getResult()[x]);
					listModel.addElement(model.getRowCount());
				}
			}
		}
	}

	public static String save_QualifiedForFirstFiling_FinalNotice(modelSpecialNotices model) {
		Boolean blnWith = false;
		String strBat = FncGlobal.GetString("select trim(to_char((max(batch_no::int) + 1), '0000000000')) from rf_client_notices");

		if (strBat==null) {
			strBat = "0000000001";
		}

		System.out.println("");
		System.out.println("blnWith: " + blnWith);
		System.out.println("strBat: " + strBat);
		System.out.println("model.getRowCount(): " + model.getRowCount());

		for (int x = 0; x < model.getRowCount(); x++) {
			Boolean isSelected = (Boolean) model.getValueAt(x, 0);

			System.out.println("");
			System.out.println("isSelected: " + isSelected);

			if (isSelected) {
				blnWith = true;

				String entity_id = (String) model.getValueAt(x, 7);
				String proj_id = (String) model.getValueAt(x, 8);
				String pbl_id = (String) model.getValueAt(x, 9);
				Integer intSeq = (Integer) model.getValueAt(x, 10);
				Integer intRec = FncGlobal.GetInteger("select (max(rec_id::int) + 1) from rf_client_notices");

				if (intRec==null) {
					intRec = 1;
				}

				System.out.println("");
				System.out.println("entity_id: " + entity_id);
				System.out.println("proj_id: " + proj_id);
				System.out.println("pbl_id: " + pbl_id);
				System.out.println("strSeq: " + intSeq);

				pgUpdate db = new pgUpdate();
				String sql = "insert into rf_client_notices (rec_id, entity_id, projcode, pbl_id, seq_no, part_id, notice_id, stage_id, dateprep, preparedby, batch_no, status_id) \n" +
						"values ('"+intRec+"'::int, '"+entity_id+"', '"+proj_id+"', '"+pbl_id+"', "+intSeq+"::int, '013', '128', 1, Now(), '"+UserInfo.EmployeeCode+"', '"+strBat+"', 'A');";

				System.out.println(sql);
				db.executeUpdate(sql, false);
				db.commit();
			}
		}

		if (!blnWith) {
			strBat = "";
		}

		return strBat;
	}

	public static String save_QualifiedForFirstFiling_FirstNotice(modelSpecialNotices model) {
		Boolean blnWith = false;
		String strBat = FncGlobal.GetString("select trim(to_char((max(batch_no::int) + 1), '0000000000')) from rf_client_notices");

		if (strBat==null) {
			strBat = "0000000001";
		}

		System.out.println("");
		System.out.println("blnWith: " + blnWith);
		System.out.println("strBat: " + strBat);
		System.out.println("model.getRowCount(): " + model.getRowCount());

		for (int x = 0; x < model.getRowCount(); x++) {
			Boolean isSelected = (Boolean) model.getValueAt(x, 0);

			System.out.println("");
			System.out.println("isSelected: " + isSelected);

			if (isSelected) {
				blnWith = true;

				String entity_id = (String) model.getValueAt(x, 7);
				String proj_id = (String) model.getValueAt(x, 8);
				String pbl_id = (String) model.getValueAt(x, 9);
				Integer intSeq = (Integer) model.getValueAt(x, 10);
				Integer intRec = FncGlobal.GetInteger("select (max(rec_id::int) + 1) from rf_client_notices");

				if (intRec==null) {
					intRec = 1;
				}

				System.out.println("");
				System.out.println("entity_id: " + entity_id);
				System.out.println("proj_id: " + proj_id);
				System.out.println("pbl_id: " + pbl_id);
				System.out.println("strSeq: " + intSeq);

				pgUpdate db = new pgUpdate();
				String sql = "insert into rf_client_notices (rec_id, entity_id, projcode, pbl_id, seq_no, part_id, notice_id, stage_id, dateprep, preparedby, batch_no, status_id) \n" +
						"values ('"+intRec+"'::int, '"+entity_id+"', '"+proj_id+"', '"+pbl_id+"', "+intSeq+"::int, '013', '127', 1, Now(), '"+UserInfo.EmployeeCode+"', '"+strBat+"', 'A');";

				System.out.println(sql);
				db.executeUpdate(sql, false);
				db.commit();
			}
		}

		if (!blnWith) {
			strBat = "";
		}

		return strBat;
	}


	public static String getNewBatchNo(){
		String batch_no = "";
		pgSelect db = new pgSelect();
		String SQL = "(SELECT TRIM(to_char((max(batch_no::int) + 1), '0000000000')) FROM rf_client_notices);";
		db.select(SQL);

		if(db.isNotNull()){
			batch_no = (String) db.getResult()[0][0];
		}
		return batch_no;
	}
	
	public static void displayNoticeLoanReturned_FirstNotice(modelSpecialNotices model, JList rowHeader, String batch_no){
		if(model != null && rowHeader != null){
			FncTables.clearTable(model);
			DefaultListModel listModel = new DefaultListModel();
			rowHeader.setModel(listModel);

			if (batch_no=="null" || batch_no==null) {
				batch_no = ""; 
			}

			pgSelect db = new pgSelect();
			String SQL = "select * from view_qualifiedforloanreturned('130', '"+batch_no+"')";
			db.select(SQL);

			System.out.println("");
			System.out.println(SQL);

			if(db.isNotNull()){
				for(int x= 0; x<db.getRowCount(); x++){
					model.addRow(db.getResult()[x]);
					listModel.addElement(model.getRowCount());
				}
			}
		}
	}
}
