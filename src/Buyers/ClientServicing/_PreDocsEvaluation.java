package Buyers.ClientServicing;


import java.util.ArrayList;
import java.util.Date;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.table.DefaultTableModel;

import Database.pgSelect;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.UserInfo;

public class _PreDocsEvaluation {

	public _PreDocsEvaluation() {
		// TODO Auto-generated constructor stub
	}

	public static String sqlProject(){
		return "SELECT \n" + 
				"proj_id as \"ID\", \n" + 
				"proj_name as \"Project\", \n" + 
				"proj_alias as \"Alias\"\n" + 
				"FROM mf_project\n" + 
				"WHERE status_id = 'A' \n"+
				"AND proj_id != '016'";
	}
	
	public static String sqlClients(){
		return "SELECT a.entity_id as \"ID\", a.entity_name as \"Name\", a.date_created as \"Date Created\", \n" + 
			   "a.status_id as \"Status ID\", a.last_name as \"Last Name\", a.first_name as \"First Name\",\n" + 
			   "a.middle_name as \"Middle Name\"\n" + 
			   "FROM rf_entity a\n" + 
			   "WHERE a.status_id = 'A'\n" + 
			   "AND EXISTS (SELECT *\n" + 
			   "	    FROM rf_entity_assigned_type \n" + 
			   "	    where entity_id = a.entity_id \n" + 
			   "	    and entity_type_id = '01'\n" + 
			   "	    and status_id = 'A')\n" + 
			   "order by a.entity_name;";
	}

	public static String sqlSalesGroup(){
		return  "SELECT \r\n" + 
				"a.dept_code as \"ID\",\r\n" + 
				"a.dept_name  as \"Dept. Name\",\r\n" + 
				"b.division_name as \"Division\" \r\n" + 
				"FROM mf_department a\r\n" + 
				"LEFT JOIN mf_division b on a.division_code = b.division_code\r\n" + 
				"WHERE a.division_code in ('06','07','08','09', '04') \n"+
				"AND a.status_id = 'A'";
	};
	
	public static String getNewEntityID(){
		String entity_id = "";
		pgSelect db = new pgSelect();
		String SQL = "SELECT get_new_entity_id()";
		db.select(SQL);
		
		entity_id = (String) db.getResult()[0][0];
		
		return entity_id;
	}
	
	public static void displayDocsEval(DefaultTableModel model, JList rowHeader){
		if(model != null && rowHeader != null){
			FncTables.clearTable(model);
			DefaultListModel listModel = new DefaultListModel();
			rowHeader.setModel(listModel);
			
			pgSelect db = new pgSelect();
			String SQL = "SELECT * FROM view_docs_eval()";
			db.select(SQL);
			
			if(db.isNotNull()){
				for(int x= 0; x<db.getRowCount(); x++){
					model.addRow(db.getResult()[x]);
					listModel.addElement(model.getRowCount());
				}
			}
		}
	}
	
	public static Boolean saveDocsEval(String proj_id, String entity_id ,String last_name, String first_name, String middle_name, String sales_group_id,Date date_evaluated){
		
		pgSelect db = new pgSelect();
		String SQL = "SELECT sp_save_new_docs_eval_v2('"+proj_id+"', '"+entity_id+"' ,'"+last_name+"', '"+first_name+"', '"+middle_name+"', '"+sales_group_id+"', NULLIF('"+date_evaluated+"', 'null')::TIMESTAMP WITHOUT TIME ZONE, '"+UserInfo.EmployeeCode+"')";
		db.select(SQL, "Save", true);
		FncSystem.out("Display SQL for Saving: %s%n", SQL);
		
		if(db.isNotNull()){
			return (Boolean) db.getResult()[0][0];
		}else{
			return false;
		}
		
	}
	
	public static void inactiveAccounts(DefaultTableModel model){
		
		ArrayList<String> listEntityID = new ArrayList<String>();
		ArrayList<String> listProjID = new ArrayList<String>();
		ArrayList<Integer> listRecID = new ArrayList<Integer>();
		
		for(int x= 0; x<model.getRowCount(); x++){
			Boolean isSelected = (Boolean) model.getValueAt(x, 0);
			if(isSelected){
				Integer rec_id = (Integer) model.getValueAt(x, 1);
				String proj_id = (String) model.getValueAt(x, 2);
				String entity_id = (String) model.getValueAt(x, 4);
				
				listRecID.add(rec_id);
				listProjID.add(String.format("'%s'", proj_id));
				listEntityID.add(String.format("'%s'", entity_id));
			}
		}
		
		String entity_id = listEntityID.toString().replaceAll("\\[|\\]", "");
		String proj_id = listProjID.toString().replaceAll("\\[|\\]", "");
		String rec_id = listRecID.toString().replaceAll("\\[|\\]", "");
		
		pgSelect db = new pgSelect();
		String SQL = "SELECT sp_inactive_docs_evaluation(ARRAY["+rec_id+"]::INT[], ARRAY["+proj_id+"]::VARCHAR[], ARRAY["+entity_id+"]::VARCHAR[], '"+UserInfo.EmployeeCode+"')";
		db.select(SQL);
		
		FncSystem.out("Display inactive tagging of docs Eval", SQL);
		
	}

}
