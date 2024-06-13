/**
 * 
 */
package Buyers.ClientServicing;

import javax.swing.DefaultListModel;
import javax.swing.JList;

import Database.pgSelect;
import Database.pgUpdate;
import tablemodel.modelReleasedDocs;
import tablemodel.modelDoc_ForRelease;
import tablemodel.modelPurpose_ForRelease;
import tablemodel.modelDocPurpose_Request;
import tablemodel.modelRequest_ForRelease;
import tablemodel.modelDocuments_Request;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.UserInfo;
/**
 * @author John Lester Fatallo
 */
public class _TechnicalDocumentRequest {

	public static void displayDocsRequest(String entity_id, String proj_id, String unit_id, String seq_no, modelDocuments_Request model, JList rowHeader){
		if(model != null && rowHeader != null){
			FncTables.clearTable(model);

			DefaultListModel listModel = new DefaultListModel();
			rowHeader.setModel(listModel);

			String sql = "select false, trim(doc_id), trim(doc_alias) , \n" + 
						 "trim(a.doc_desc), trim(b.dept_alias), false \n" + 
						 "from mf_buyer_request_docs a\n" + 
						 "left join mf_department b\n" + 
						 "on (a.dept_code  = b.dept_code)\n" + 
						 "where a.status_id  = 'A' \n" + 
						 "and a.doc_id not in ('34', '23', '22', '21')\n" + 
						 
					/*
					 * "and not exists (SELECT *\n" + "		FROM rf_buyer_docs_dl c\n" +
					 * "		left join rf_buyer_docs_hd d on d.request_no = c.request_no\n" +
					 * "		where d.entity_id = '"+entity_id+"'\n" +
					 * "		and d.projcode = '"+proj_id+"'\n" +
					 * "		and d.pbl_id = getinteger('"+unit_id+"')::VARCHAR\n" +
					 * "		and d.seq_no = "+seq_no+" \n" +
					 * "		and NULLIF(c.req_purpose_id, '') IS NULL\n" +
					 * "		AND c.doc_id = a.doc_id \n"+ "		AND NOT EXISTS (SELECT * \n"+
					 * "				FROM req_rt_request_header \n"+
					 * "				WHERE request_no = c.request_no \n"+
					 * "				AND request_status = 'X'))\n" +
					 */ 
					 
						 "ORDER BY a.doc_desc;";

			pgSelect db = new pgSelect();
			db.select(sql);

			FncSystem.out("Display sql for Documents Request", sql);

			if(db.isNotNull()){
				for(int x=0; x < db.getRowCount(); x++){
					model.addRow(db.getResult()[x]);
					listModel.addElement(model.getRowCount());
				}
			}
		}
	}

	public static void displayDocsList(modelDocuments_Request model, JList rowHeader, String req_no){//XXX TODO add remarks to table
		//if(model != null && rowHeader != null){
		FncTables.clearTable(model);

		DefaultListModel listModel = new DefaultListModel();
		rowHeader.setModel(listModel);

		String sql = "select false, a.doc_id as \"ID\", b.doc_alias as \"Doc Alias\", \n" + 
				"b.doc_desc as \"Doc Name\" ,c.dept_alias as \"Dept Alias\" \n" + 
				"from rf_buyer_docs_dl a  \n" + 
				"left join mf_buyer_request_docs b on a.doc_id  = b.doc_id   \n" + 
				"left join mf_department c  on b.dept_code  = c.dept_code \n" + 
				"where request_no  ='"+req_no+"' \n"+
				"and released_by is null \n"+
				"and a.doc_id != ''"+
				"order by a.doc_id";

		FncSystem.out("Display Selected Documents List", sql);

		pgSelect db = new pgSelect();
		db.select(sql);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
				listModel.addElement(model.getRowCount());	
			}
		}
		//}
}

	public static void displayTechDocsRequest(String entity_id, String proj_id, String unit_id, String seq_no, modelRequest_ForRelease model, JList rowHeader){
		FncTables.clearTable(model);

		DefaultListModel listModel = new DefaultListModel();
		rowHeader.setModel(listModel);

		String sql = "SELECT a.request_no, a.request_date, a.remarks \n"+
				"FROM rf_buyer_docs_hd a\n"+
				"WHERE a.entity_id = '"+entity_id+"' \n"+
				"AND a.projcode = '"+proj_id+"' \n"+
				"AND a.pbl_id = getinteger('"+unit_id+"')::VARCHAR \n"+
				"AND a.seq_no = "+seq_no+" \n"+
				"AND a.release_date IS NULL \n"+
				"AND a.released_by IS NULL \n"+
				"AND NOT EXISTS (SELECT * \n"+
				"				 FROM req_rt_request_header \n"+
				"				 WHERE request_no = a.request_no \n"+
				"				 AND request_status = 'X')";

		pgSelect db = new pgSelect();
		db.select(sql);

		FncSystem.out("Display SQL For Technical Documents Request", sql);

		if(db.isNotNull()){
			for(int x= 0; x < db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
				listModel.addElement(model.getRowCount());
			}
		}
	}

	public static void displayDocsForRelease(String request_no, modelDoc_ForRelease model, JList rowHeader){
		FncTables.clearTable(model);

		DefaultListModel listModel = new DefaultListModel();
		rowHeader.setModel(listModel);

		String sql = "SELECT TRUE, a.doc_id, b.doc_alias, b.doc_desc, \n" + 
					 "c.dept_alias, a.photocopy, a.rec_id\n" + 
					 "FROM rf_buyer_docs_dl a\n" + 
					 "LEFT JOIN mf_buyer_request_docs b on b.doc_id = a.doc_id\n" + 
					 "LEFT JOIN mf_department c on c.dept_code = b.dept_code\n" + 
					 "where a.request_no = '"+request_no+"'\n" + 
					 "and a.released_by is null\n" + 
					 "AND a.release_date is null\n" + 
					 "and a.doc_id != '' \n" + 
					 "ORDER BY b.doc_desc;";

		pgSelect db = new pgSelect();
		db.select(sql);

		FncSystem.out("Display sql Docs For Release", sql);

		if(db.isNotNull()){
			for(int x= 0; x<db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
				listModel.addElement(model.getRowCount());
			}
		}
	}

	//added by jari cruz dtd march 14 2023 
	public static void displayDocsReleased(String request_no, modelDoc_ForRelease model, JList rowHeader){
		FncTables.clearTable(model);

		DefaultListModel listModel = new DefaultListModel();
		rowHeader.setModel(listModel);

		String sql = "SELECT TRUE, a.doc_id, b.doc_alias, b.doc_desc, \n" + 
					 "c.dept_alias, a.photocopy, a.rec_id\n" + 
					 "FROM rf_buyer_docs_dl a\n" + 
					 "LEFT JOIN mf_buyer_request_docs b on b.doc_id = a.doc_id\n" + 
					 "LEFT JOIN mf_department c on c.dept_code = b.dept_code\n" + 
					 "where a.request_no = '"+request_no+"'\n" + 
					 "and a.released_by is not null\n" + 
					 "AND a.release_date is not null\n" + 
					 "and a.doc_id != '' \n" + 
					 "ORDER BY b.doc_desc;";

		pgSelect db = new pgSelect();
		db.select(sql);

		FncSystem.out("Display sql Docs For Release", sql);

		if(db.isNotNull()){
			for(int x= 0; x<db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
				listModel.addElement(model.getRowCount());
			}
		}
	}
	
	public static void displayRequestPurposeDocuments(String request_no, modelPurpose_ForRelease model, JList rowHeader){
		
		FncTables.clearTable(model);
		
		DefaultListModel listModel = new DefaultListModel();
		rowHeader.setModel(listModel);
		
		String sql = "select true, a.req_purpose_id, b.pp_desc\n" + 
					 "from rf_buyer_docs_dl a\n" + 
					 "left join mf_buyer_request_purpose b on b.pp_code = a.req_purpose_id\n" + 
					 "where a.request_no = '"+request_no+"' \n" + 
					 "and a.req_purpose_id != ''";
		
		pgSelect db = new pgSelect();
		db.select(sql);
		
		FncSystem.out("Display sql for Document Purpose", sql);
		
		if(db.isNotNull()){
			for(int x= 0; x<db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
				listModel.addElement(model.getRowCount());
			}
		}
	}

	public static void displayReleasedDocs(modelReleasedDocs model, JList rowHeader, String entity_id, String unit_id, String proj_id, String seq_no){
		FncTables.clearTable(model);

		DefaultListModel listModel = new DefaultListModel();
		rowHeader.setModel(listModel);

		String sql = "select a.request_no as \"Request No.\", a.request_date as \"Request Date\" , \n"+
				"b.doc_id as \"ID\", c.doc_alias as \"Doc Alias\",\n" + 
				"c.doc_desc as \"Doc Name\", d.dept_alias as \"Dept. Alias\",\n" + 
				"get_employee_name(b.released_by) as \"Released by\", b.release_date as \"Released Date\"\n" + 
				"from rf_buyer_docs_hd a\n" + 
				"left join rf_buyer_docs_dl b on b.request_no = a.request_no\n" + 
				"left join mf_buyer_request_docs c on c.doc_id = b.doc_id\n" + 
				"left join mf_department d on d.dept_code = c.dept_code\n" + 
				"where a.entity_id = '"+entity_id+"'\n" + 
				"and a.pbl_id = getinteger('"+unit_id+"')::VARCHAR\n" + 
				"and a.projcode = '"+proj_id+"'\n" + 
				"and a.seq_no = "+seq_no+"\n" + 
				"and b.released_by is not null\n" + 
				"and b.doc_id != '' \n"+
				"order by b.release_date desc";

		FncSystem.out("Display Released Documents", sql);
		pgSelect db = new pgSelect();
		db.select(sql);
		if(db.isNotNull()){
			for(int x=0; x<db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
				listModel.addElement(model.getRowCount());
			}
		}
	}

	//
	public static void displayReqList(modelDocPurpose_Request model, JList rowHeader, String req_no){
		if(model != null && rowHeader != null){
			FncTables.clearTable(model);

			DefaultListModel listModel = new DefaultListModel();
			rowHeader.setModel(listModel);

			String sql = "select true, a.req_purpose_id, b.pp_desc\n" + 
					"from rf_buyer_docs_dl a\n" + 
					"left join mf_buyer_request_purpose b on b.pp_code = a.req_purpose_id\n" + 
					"where a.request_no = '"+req_no+"' \n" + 
					"and a.req_purpose_id != ''";
			FncSystem.out("Display Selected Request List", sql);
			pgSelect db = new pgSelect();
			db.select(sql);
			if(db.isNotNull()){
				for(int x = 0; x<db.getRowCount(); x++){
					model.addRow(db.getResult()[x]);
					listModel.addElement(model.getRowCount());
				}
			}
		}
	}

	public static void displayRequestPurpose(modelDocPurpose_Request model, JList rowHeader){ //Displays the Purpose of the Request
		if(model != null && rowHeader != null){
			FncTables.clearTable(model);

			DefaultListModel listModel = new DefaultListModel();
			rowHeader.setModel(listModel);

			String sql = "select false, pp_code, pp_desc from mf_buyer_request_purpose";
			FncSystem.out("Display Purpose of Request", sql);
			pgSelect db = new pgSelect();
			db.select(sql);
			if(db.isNotNull()){
				for(int x = 0; x<db.getRowCount(); x++){
					model.addRow(db.getResult()[x]);
					listModel.addElement(model.getRowCount());
				}
			}
		}
	}

	public static boolean checkDept(){//checks for the logged user department for the releasing of the the technical documents
		pgSelect db = new pgSelect();

		String sql = "select case when '"+UserInfo.Department+"' in ('04', '02') then true when '"+UserInfo.Division+"' = '31' then true else false end\n";
		db.select(sql);
		
		FncSystem.out("Display Module Access", sql);
		return (Boolean) db.getResult()[0][0];
	}

	public static void saveRfBuyerDocsHD(String req_no, String entity_id, String unit_id, String proj_id, String seq_no ,String entity_name , String remarks, String received_by, String relation_to_principal){//XXX add seq no and proj_id in the GUI and in this saving
		pgUpdate db = new pgUpdate();

		String sql = "INSERT INTO rf_buyer_docs_hd(\n" + 
				"rec_id, request_no, entity_id, projcode, pbl_id, seq_no, request_date, \n" + 
				"requested_by, remarks, status_id, add_by, add_date, unit_id, received_by, receive_date, \n"+
				"relation_to_principal)\n" + 
				"VALUES (coalesce((select max(rec_id) + 1 from rf_buyer_docs_hd), 1), "+
				"("+req_no+"), '"+entity_id+"', '"+proj_id+"', getinteger('"+unit_id+"')::VARCHAR, "+seq_no+", "+
				"now(),'"+entity_name+"', '"+remarks+"', 'S', \n" + 
				"'"+UserInfo.EmployeeCode+"', now() ,'"+unit_id+"', NULL, NULL, NULL);\n";
		db.executeUpdate(sql, true);
		db.commit();

	}

	public static void saveRfBuyerDocsDL(modelDocuments_Request model, String req_no, modelDocPurpose_Request model2, String received_by){
		pgUpdate db = new pgUpdate();

		//REQUESTED DOCUMENTS
		for(int x=0; x < model.getRowCount(); x++){

			Boolean isSelected = (Boolean) model.getValueAt(x, 0);
			String doc_id = (String) model.getValueAt(x, 1);
			Boolean photocopy = (Boolean) model.getValueAt(x, 5);

			if(isSelected){
				String sqldetail = "INSERT INTO rf_buyer_docs_dl(rec_id, request_no, doc_id, received_by, receive_date, photocopy) \n" + 
						"VALUES (coalesce((select max(rec_id) + 1 from rf_buyer_docs_dl), 1), ("+req_no+"), "+
						"'"+doc_id+"', '"+received_by+"', now(), "+photocopy+") \n";
				db.executeUpdate(sqldetail, true);
			}
		}

		//DOCUMENTS PURPOSE
		for(int y = 0; y<model2.getRowCount(); y++){	

			Boolean isSelected = (Boolean) model2.getValueAt(y, 0);
			String req_id = (String) model2.getValueAt(y, 1);

			if(isSelected){
				String sqldetail2 = "INSERT INTO rf_buyer_docs_dl(rec_id, request_no, req_purpose_id, received_by, receive_date) \n" + 
						"VALUES (coalesce((select max(rec_id) + 1 from rf_buyer_docs_dl), 1), ("+req_no+"), "+
						"'"+req_id+"', '"+received_by+"', now()) \n";
				db.executeUpdate(sqldetail2, true);
			}
		}
		db.commit();
	}

	public static void saveReqHeader(String req_no, String req_by){
		pgUpdate db = new pgUpdate();

		String sql = "INSERT INTO req_rt_request_header(rec_id, request_no, request_date, request_status, request_by, add_by, add_date, req_type_id, isdocorrefund)\n" + 
				"VALUES (coalesce((select max(rec_id) + 1 from req_rt_request_header),1), ("+req_no+"), now(), 'S', '"+req_by+"', " + 
				"'"+UserInfo.EmployeeCode+"', now(), 'TECHNICAL DOCUMENTS', false) \n";
		db.executeUpdate(sql, true);
		db.commit();
	}

	public static void releaseReqBuyerDocsHD(String req_no){//remove me
		pgUpdate db = new pgUpdate();

		String sql = "UPDATE rf_buyer_docs_hd SET released_by='"+UserInfo.EmployeeCode+"', release_date=now(), status_id='P', " + 
				"update_by= '"+UserInfo.EmployeeCode+"', update_date=now() \n" + 
				"WHERE request_no = '"+req_no+"'";

		db.executeUpdate(sql, true);
		db.commit();
	}

	public static void unTagDocument(modelDoc_ForRelease model){
		pgUpdate db = new pgUpdate();

		for(int x= 0; x<model.getRowCount(); x++){
			Boolean isSelected = (Boolean) model.getValueAt(x, 0);

			if(isSelected){
				Integer rec_id = (Integer) model.getValueAt(x, 6);

				String sql = "DELETE FROM rf_buyer_docs_dl WHERE rec_id = "+rec_id+"";
				db.executeUpdate(sql, true);
			}
		}
		db.commit();
	}

	public static void releaseDocuments(String request_no, modelDoc_ForRelease model, String received_by, String relation){
		pgUpdate db = new pgUpdate();
		int count = 0;
		
		for(int x = 0; x<model.getRowCount(); x++){
			Boolean isSelected = (Boolean) model.getValueAt(x, 0);
			
			if(isSelected){
				count = count + 1;
				
				String doc_id = (String) model.getValueAt(x, 1);
				Integer rec_id = (Integer) model.getValueAt(x, 6); 
				
				String sql = "UPDATE rf_buyer_docs_dl SET released_by = '"+UserInfo.EmployeeCode+"', release_date = now() \n"+
							 "WHERE doc_id = '"+doc_id+"' AND rec_id = "+rec_id+" AND request_no = '"+request_no+"' \n";
				db.executeUpdate(sql, true);
			}
		}
		
		if(model.getRowCount() == count){
			String sql = "UPDATE rf_buyer_docs_hd SET release_date = now(), released_by = '"+UserInfo.EmployeeCode+"', received_by = '"+received_by+"', receive_date = now() WHERE request_no = '"+request_no+"' \n";
			db.executeUpdate(sql, true);
			
			String sql2 = "UPDATE req_rt_request_header SET request_status = 'P' WHERE request_no = '"+request_no+"' \n";
			db.executeUpdate(sql2, true);
		}
		
		db.commit();
	}

	public static void releaseReqBuyerDocsDL(modelDocuments_Request model, String req_no){
		pgUpdate db = new pgUpdate();
		int count = 0;

		for(int x=0; x < model.getRowCount(); x++){

			Boolean isSelected = (Boolean) model.getValueAt(x, 0);
			String doc_id = (String) model.getValueAt(x, 1);
			count = count +1;

			if(isSelected){

				String sql = "UPDATE rf_buyer_docs_dl SET released_by='"+UserInfo.EmployeeCode+"', release_date=now() " + 
						"WHERE request_no = '"+req_no+"' and doc_id = '"+doc_id+"'";
				System.out.println("Dumaan dito sa may released date");

				db.executeUpdate(sql, true);
			}

			if(isSelected && model.getRowCount() == count){//UPDATES THE REQUEST ONLY WHERE ALL THE DISPLAYED REQUEST ARE SELECTED
				String sqlReqDL = "UPDATE rf_buyer_docs_hd SET released_by='"+UserInfo.EmployeeCode+"', release_date=now(), status_id='P', " + 
						"update_by= '"+UserInfo.EmployeeCode+"', update_date=now() WHERE request_no = '"+req_no+"'";
				db.executeUpdate(sqlReqDL, true);

				String sqlReqHeader = "UPDATE req_rt_request_header SET request_status= 'P' WHERE request_no = '"+req_no+"'";
				db.executeUpdate(sqlReqHeader, true);
			}
			//int countSelect  = (int) model.getValueAt(x, 0);
		}
		db.commit();
	}

	public static void releaseReqHeader(String req_no){//REMOVE ME 
		pgUpdate db = new pgUpdate();

		String sql = "UPDATE req_rt_request_header SET request_status= 'P' " + 
				"WHERE request_no = '"+req_no+"'";

		db.executeUpdate(sql, true);
		db.commit();
	}

}
