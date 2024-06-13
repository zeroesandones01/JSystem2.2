package Projects.BiddingandAwarding;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.swing.DefaultListModel;
import javax.swing.JList;

import Database.pgSelect;
import Database.pgUpdate;
import tablemodel.modelNTPSuretyBond;
import Functions.UserInfo;
import components._DB;

public class _SuretyBond extends _DB {

	public _SuretyBond() {
		// TODO Auto-generated constructor stub
	}

	public static String InsuranceCompany() {
		String sql = "select trim(a.entity_id) as \"ID\", trim(b.entity_name) as \"Name\", trim(b.entity_kind) as \"Kind\"\n" + 
				"from mf_insurance_company a\n" + 
				"left join rf_entity b on b.entity_id = a.entity_id\n" + 
				"where b.entity_kind = 'C'\n" + 
				"and b.status_id = 'A'\n" + 
				"group by trim(a.entity_id), trim(b.entity_name), trim(b.entity_kind)\n" + 
				"order by trim(b.entity_name);";
		return sql;
	}
	
	public static String InsuranceCompany2() {
		String sql = "select trim(entity_id) as \"ID\", trim(entity_name) as \"Name\", trim(entity_kind) as \"Kind\"\n" + 
				"from rf_entity \n" + 
				"where entity_kind = 'C'\n" + 
				"and status_id = 'A'\n" + 
				//"group by trim(a.entity_id), trim(b.entity_name), trim(b.entity_kind)\n" + 
				"order by trim(entity_name);";
		return sql;
	}

	public static String DepartmentConcerned() {
		String sql = "select trim(dept_code) as \"ID\", trim(dept_name) as \"Name\", trim(dept_alias) as \"Alias\"\n" + 
				"from mf_department\n" + 
				"where co_id = '02'\n" + 
				"and dept_NAME !~* '-BDG'\n" + 
				"and dept_alias !~* '/D'\n" + 
				"group by trim(dept_code), trim(dept_name), trim(dept_alias)\n" + 
				"order by trim(dept_name);";
		return sql;
	}

	public static String PresentLocation() {

		return "";
	}

	public static void displaySuretyBondDetails(modelNTPSuretyBond model, JList rowHeader, String contract_no) {
		model.clear();
		DefaultListModel listModel = new DefaultListModel();
		rowHeader.setModel(listModel);

		String sql = "select\n" + 
				"trim(a.bond_no) as bond_no,\n" + 
				"trim(a.insurance_co) as insurance_co_id,\n" + 
				"trim(b.entity_name) as insurance_co,\n" + 
				"a.from_date,\n" + 
				"a.to_date,\n" + 
				"a.amount,\n" + 
				"trim(a.dept_concerned) as dept_concerned_id,\n" + 
				"trim(c.dept_name) as dept_concerned,\n" + 
				"trim(a.present_loc) as present_location_id,\n" + 
				"null as present_location,a.rec_id\n\n" + 

				"from co_surety_bond a\n" + 
				"left join rf_entity b on b.entity_id = a.insurance_co\n" + 
				"left join mf_department c on c.dept_code = a.dept_concerned\n\n" + 

				"where a.contract_no = '"+ contract_no +"'\n" + 
				"and a.status_id = 'A';";

		System.out.printf("SQL: %s%n%n", sql);
		pgSelect db = new pgSelect();
		db.select(sql);

		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
				listModel.addElement(model.getRowCount());
			}
		}
	}

	public static boolean isBondExisting(String bond_no) {
		pgSelect db = new pgSelect();
		db.select("select * from co_surety_bond where trim(bond_no) = '"+ bond_no +"'");
		return db.isNotNull();
	}

	public static void saveSuretyBonds(Boolean isExisting, String contract_no, String bond_no, String insurance_co_id, Timestamp from_date, Timestamp to_date,
			BigDecimal amount,String dept_concerned_id, String present_loc_id,Integer rec_id) {
		
		System.out.println("UPDATE co_surety_bond SET\n" +
					"insurance_co='"+ insurance_co_id +"',\n" +
					"contract_no='"+ contract_no +"',\n" +
					"from_date='"+ from_date +"',\n" +
					"to_date='"+ to_date +"',\n" +
					"amount='"+ amount +"',\n" +
					"dept_concerned=nullif('"+ dept_concerned_id +"', 'null'),\n" +
					"present_loc=nullif('"+ present_loc_id +"', 'null'),\n" +
					"edited_by='"+ UserInfo.EmployeeCode +"',\n" + 
					"date_edited=now(),\n" +
					"bond_no='"+ bond_no +"'\n" +
					//"status_id=?\n" +
					"WHERE rec_id = '"+rec_id+"';");
		pgUpdate dbPgUpdate = new pgUpdate();

		String pgSQL = "";
		
		if(isExisting){
			pgSQL = "UPDATE co_surety_bond SET\n" +
					"insurance_co='"+ insurance_co_id +"',\n" +
					"contract_no='"+ contract_no +"',\n" +
					"from_date='"+ from_date +"',\n" +
					"to_date='"+ to_date +"',\n" +
					"amount='"+ amount +"',\n" +
					"dept_concerned=nullif('"+ dept_concerned_id +"', 'null'),\n" +
					"present_loc=nullif('"+ present_loc_id +"', 'null'),\n" +
					"edited_by='"+ UserInfo.EmployeeCode +"',\n" + 
					"date_edited=now(),\n" +
					"bond_no='"+ bond_no +"'\n" +
					//"status_id=?\n" +
					"WHERE rec_id = '"+rec_id+"';";
		}else{
			pgSQL = "INSERT INTO co_surety_bond(\n" + 
					"            bond_no, insurance_co, contract_no, from_date, to_date, amount, \n" + 
					"            dept_concerned, present_loc, status_id, created_by, date_created)\n" + 
					"    VALUES ('"+ bond_no +"', '"+ insurance_co_id +"', '"+ contract_no +"', '"+ from_date +"', '"+ to_date +"', "+ amount +", \n" + 
					"            nullif('"+ dept_concerned_id +"', 'null'), nullif('"+ present_loc_id +"', 'null'), 'A', '"+ UserInfo.EmployeeCode +"', now());";
		}
		
		dbPgUpdate.executeUpdate(pgSQL, false);
		dbPgUpdate.commit();
	}

	public static void deleteSuretyBond(String contract_no, String bond_no) {
		String sql = "UPDATE co_surety_bond SET status_id = 'I', edited_by = '"+ UserInfo.EmployeeCode +"', date_edited = now() WHERE contract_no = '"+ contract_no +"' AND bond_no = '"+ bond_no +"';";

		pgUpdate db = new pgUpdate();
		db.executeUpdate(sql, false);
		db.commit();
	}

}
