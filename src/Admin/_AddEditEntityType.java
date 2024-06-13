package Admin;

import tablemodel.modelAddEditEntityType_EntityTypes;

import javax.swing.table.DefaultTableModel;

import Database.pgSelect;
import Database.pgUpdate;
import Functions.UserInfo;

public class _AddEditEntityType {

	public _AddEditEntityType() {
		// TODO Auto-generated constructor stub
	}
	
	public static String getEntitySQL() {
		String SQL = 
			"SELECT TRIM(a.entity_id) AS \"ID\", " +
			"TRIM(a.entity_name) AS \"Name\", " +
			"TRIM(b.tin_no) AS \"TIN No.\", " +
			"vat_registered AS \"VAT\"\n" + 
				"FROM rf_entity a\n" + 
				"LEFT JOIN rf_entity_id_no b ON b.entity_id = a.entity_id\n" + 
				"ORDER BY a.entity_name;";
		
		return SQL;
	}
	
	public static Object[] getEntityDetails(String entity_id) {
		String SQL = "SELECT TRIM(a.entity_name), TRIM(b.tin_no), COALESCE(a.vat_registered, FALSE)\n" + 
				"FROM rf_entity a\n" + 
				"LEFT JOIN rf_entity_id_no b ON b.entity_id = a.entity_id\n" + 
				"WHERE a.entity_id = '"+ entity_id +"'";

		pgSelect db = new pgSelect();
		db.select(SQL);
		if(db.isNotNull()){
			return db.getResult()[0];
		}else{
			return null;
		}
	}
	
	public static void displayEntityTypes(modelAddEditEntityType_EntityTypes model, String entity_id) {
		model.clear();

		String SQL = 
//				"select exists(select * from rf_entity_assigned_type where entity_id = '"+entity_id+"'and status_id = 'A' and entity_type[array_position(co_id, '\"+co_id+\"')] = a.entity_type_id) as tag, \n" + 
				"select exists(select * from rf_entity_assigned_type where entity_id = '"+entity_id+"'and status_id = 'A' and entity_type_id = a.entity_type_id) as tag, \n" +
				"trim(entity_type_id) as type_id, trim(entity_type_desc) as type_desc, trim(a.wtax_id) as wtax_id, trim(b.wtax_desc) as wtax_desc, b.wtax_rate, b.wtax_bir_code \n" + 
				"from mf_entity_type a \n" + 
				"left join rf_withholding_tax b on b.wtax_id = a.wtax_id \n" + 
				"where a.entity_type_id not in ('03', '04', '23', '24', '12', '34', '35', '38', '39', '40', '41') \n" + 
				"ORDER BY entity_type_desc; ";

		pgSelect db = new pgSelect();
		db.select(SQL);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
			}
		}
	}

	public static void displayCSEntityTypes(modelAddEditEntityType_EntityTypes model, String entity_id, String co_id) {
		model.clear();

		String SQL = "select exists(select * from rf_entity_assigned_type where entity_id = '"+entity_id+"'and status_id = 'A' and entity_type[array_position(co_id, '"+co_id+"')] = a.entity_type_id) as tag, \n" + 
				"trim(entity_type_id) as type_id, trim(entity_type_desc) as type_desc, trim(a.wtax_id) as wtax_id, trim(b.wtax_desc) as wtax_desc, b.wtax_rate, b.wtax_bir_code \n" + 
				"from mf_entity_type a \n" + 
				"left join rf_withholding_tax b on b.wtax_id = a.wtax_id \n" + 
				"where a.entity_type_id in ('03', '04', '23', '24', '12', '34', '35', '38', '39', '40', '41') \n" + 
				"ORDER BY entity_type_desc; ";

		pgSelect db = new pgSelect();
		db.select(SQL);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
			}
		}
	}
	
	public static void saveEntityTypes(modelAddEditEntityType_EntityTypes model, String entity_id) {
		for(int x=0; x < model.getRowCount(); x++){
			Boolean selected = (Boolean) model.getValueAt(x, 0);
			String entity_type_id = (String) model.getValueAt(x, 1);

			String SQL = "SELECT sp_update_entity_types('"+ entity_id +"', '"+ entity_type_id +"', "+ selected +", '"+ UserInfo.EmployeeCode +"');";

			pgSelect db = new pgSelect();
			db.select(SQL);
		}
	}

	public static void saveCSEntityTypes(DefaultTableModel model, String id, String company) {
		for (int x=0; x < model.getRowCount(); x++) {
			if ((Boolean) model.getValueAt(x, 0)) {
				pgUpdate dbExec = new pgUpdate();
				dbExec.executeUpdate("call sp_comm_update_entity_type('"+id+"', '"+model.getValueAt(x, 1).toString()+"', '"+company+"', '"+UserInfo.EmployeeCode+"'); ", true);
				dbExec.commit();
			}
		}
	}
}
