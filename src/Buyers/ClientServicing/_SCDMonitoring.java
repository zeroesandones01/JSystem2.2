package Buyers.ClientServicing;

import java.util.Date;

import Database.pgSelect;
import Database.pgUpdate;
import tablemodel.modelSCDM_Accounts;
import tablemodel.modelSCDM_InAccounts;
import tablemodel.modelSCDM_OutAccounts;
import tablemodel.modelSCDStatusEditor;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.UserInfo;

public class _SCDMonitoring {

	public _SCDMonitoring() {
		// TODO Auto-generated constructor stub
	}

	public static void displayPreparation(modelSCDM_Accounts model, Boolean filterProject, String proj_id, Date or_from, Date or_to) {
		FncTables.clearTable(model);

		String SQL = "SELECT * FROM generate_scd_prep("+ filterProject +", '"+ proj_id +"', NULLIF('"+ or_from +"', 'null')::TIMESTAMP, NULLIF('"+ or_to +"', 'null')::TIMESTAMP);";

		FncSystem.out("SCD Preparation", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);
		if(db.isNotNull()){
			for(int x=0; x<db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
			}
		}
	}

	public static void displayOut(modelSCDM_OutAccounts model, Boolean filterProject, String proj_id, Date prep_from, Date prep_to) {
		FncTables.clearTable(model);

		String SQL = "SELECT * FROM generate_scd_out("+ filterProject +", '"+ proj_id +"', NULLIF('"+ prep_from +"', 'null')::TIMESTAMP, NULLIF('"+ prep_to +"', 'null')::TIMESTAMP);";

		FncSystem.out("SCD Out", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);
		if(db.isNotNull()){
			for(int x=0; x<db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
			}
		}
	}

	public static void displayIn(modelSCDM_InAccounts model, Boolean filterProject, String proj_id) {
		FncTables.clearTable(model);
		
		//String SQL = "SELECT * FROM generate_scd_in()";
		
		String SQL = "SELECT * FROM generate_scd_in_v2("+filterProject+", '"+proj_id+"')";

		/*String SQL = "SELECT false, get_client_name(a.entity_id), FORMAT('%s-%s-%s', b.phase, b.block, b.lot)::VARCHAR as description, c.tran_date, c.tran_date::DATE - CURRENT_DATE, a.entity_id, a.projcode, a.pbl_id, a.seq_no\n" + 
				"FROM rf_sold_unit a\n" + 
				"LEFT JOIN mf_unit_info b ON b.proj_id = a.projcode AND b.pbl_id = a.pbl_id\n" + 
				"LEFT JOIN (SELECT * FROM rf_buyer_status p\n" + 
				"           WHERE byrstatus_id = '40'\n" + 
				"           AND NOT EXISTS(SELECT * FROM rf_buyer_status WHERE entity_id = p.entity_id AND proj_id = p.proj_id AND pbl_id = p.pbl_id AND seq_no = p.seq_no AND byrstatus_id = '41' AND status_id = 'A')\n" + 
				"           AND status_id = 'A') c ON c.entity_id = a.entity_id AND c.proj_id = a.projcode AND c.pbl_id = a.pbl_id AND c.seq_no = a.seq_no\n" + 
				//"WHERE a.projcode = '"+ proj_id +"'\n" + 
				"WHERE a.currentstatus != '02'\n" + 
				"AND a.buyertype IN (SELECT type_id FROM mf_buyer_type WHERE type_group_id = '04')\n" + 
				"AND c.tran_date IS NOT NULL\n" + 
				"ORDER BY get_client_name(a.entity_id), getinteger(b.phase), gettext(b.phase), getinteger(b.block), gettext(b.block), getinteger(b.lot), gettext(b.lot);";*/

		FncSystem.out("SCD In", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);
		if(db.isNotNull()){
			for(int x=0; x<db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
			}
		}
	}
	
	/*
	 * public static String sqlClients(){ return
	 * "SELECT a.entity_id as \"Entity ID\", b.entity_name as \"Name\", \n" +
	 * "a.projcode as \"Proj. ID\", c.proj_name as \"Proj. Name\", \n" +
	 * "d.unit_id as \"Unit ID\", a.seq_no as \"Seq. No\",\n" +
	 * "get_merge_unit_desc(d.unit_id) as \"Unit\", \n" +
	 * "a.model_id as \"Model ID\", get_model_desc(a.model_id) as \"Model\"\n" +
	 * "from rf_sold_unit a\n" +
	 * "LEFT JOIN rf_entity b on b.entity_id = a.entity_id\n" +
	 * "LEFT JOIN mf_project c on c.proj_id = a.projcode \n" +
	 * "LEFT JOIN mf_unit_info d on d.proj_id = a.projcode and d.pbl_id = a.pbl_id\n"
	 * + "where a.currentstatus != '02'\n" + "AND a.status_id = 'A' \n"+
	 * "AND EXISTS (SELECT * \n"+ " 			 FROM rf_buyer_status \n"+
	 * "			 where entity_id = a.entity_id \n"+
	 * "			 and proj_id = a.projcode \n"+
	 * "			 and pbl_id = a.pbl_id \n"+
	 * "			 and seq_no = a.seq_no \n"+
	 * "			 and byrstatus_id in ('40', '41', '42') \n"+
	 * " 			 and status_id = 'A' )"+ //"and a.pbl_id = '2931' \n"+
	 * "order by b.entity_name;"; }
	 */
	
	
	//09-13-2021 Added by Monique Boriga - Updated for faster generation of accounts 
	public static String sqlClients(){
		return "Select x.entity_id as \"ENTITY ID\", a.entity_name as \"CLIENT\", z.proj_id as \"PROJ_ID\", z.proj_name as \"PROJ_NAME\", b.unit_id as \"UNIT ID\", x.seq_no as \"SEQ NO\", b.description as \"UNIT\", \n" + 
		"		c.model_id as \"MODEL ID\", c.model_desc as \"MODEL\"\n" + 
		"		from rf_sold_unit x\n" + 
		"		LEFT JOIN rf_entity a on a.entity_id = x.entity_id\n" + 
		"		LEFT JOIN mf_project z on z.proj_id = x.projcode\n" + 
		"		LEFT JOIN mf_unit_info b on b.proj_id = x.projcode and b.pbl_id = x.pbl_id\n" + 
		"		LEFT JOIN mf_product_model c on c.model_id = x.model_id\n" + 
		"		where x.currentstatus != '02'\n" + 
		"	 	and x.status_id = 'A' \n" + 
		"		and EXISTS\n" + 
		"					( Select * from rf_buyer_status where entity_id = x.entity_id\n" + 
		"					 and proj_id = x.projcode \n" + 
		"					 and pbl_id = x.pbl_id\n" + 
		"					 and seq_no = x.seq_no\n" + 
		"					 and byrstatus_id in ('40','41', '42')\n" + 
		"					 and status_id = 'A')\n" + 
		"		order by a.entity_name; ";
		

	}
	
	public static void displayStatusHistory(modelSCDStatusEditor model, String entity_id, String proj_id, String unit_id, Integer seq_no){
		FncTables.clearTable(model);
		
		String SQL = "SELECT FALSE, a.byrstatus_id, a.status_id, a.tran_date, b.status_desc \n"+
					 "FROM rf_buyer_status a \n"+
					 "LEFT JOIN mf_buyer_status b on b.byrstatus_id = a.byrstatus_id \n"+
				     "WHERE a.entity_id = '"+entity_id+"' \n"+
					 "AND a.proj_id = '"+proj_id+"' \n"+
				     "AND a.pbl_id= getinteger('"+unit_id+"')::VARCHAR \n"+
					 "AND a.seq_no = "+seq_no+" \n"+
				     "AND a.byrstatus_id IN ('40', '41', '42') \n"+
					 "AND a.status_id = 'A'";
		
		pgSelect db = new pgSelect();
		db.select(SQL);
		
		if(db.isNotNull()){
			for(int x= 0; x<db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
			}
		}
	}

	public static void savePrep(modelSCDM_Accounts model) {
		pgUpdate db = new pgUpdate();
		for(int x=0; x < model.getRowCount(); x++){
			Boolean selected = (Boolean) model.getValueAt(x, 0);
			String entity_id = (String) model.getValueAt(x, 10);
			String proj_id = (String) model.getValueAt(x, 11);
			String pbl_id = (String) model.getValueAt(x, 12);
			Integer seq_no = (Integer) model.getValueAt(x, 13);

			if(selected){
				String SQL = "INSERT INTO rf_buyer_status(\n" + 
						"            entity_id, proj_id, pbl_id, seq_no, byrstatus_id, tran_date, \n" + 
						"            actual_date, status_id, branch_id, created_by)\n" + 
						"    VALUES ('"+ entity_id +"', '"+ proj_id +"', '"+ pbl_id +"', '"+ seq_no +"', '42', now(), \n" + 
						"            now(), 'A', '"+ UserInfo.Branch +"', '"+ UserInfo.EmployeeCode +"');";

				db.executeUpdate(SQL, true);
			}
		}
		db.commit();
	}

	public static void saveOut(modelSCDM_OutAccounts model) {
		pgUpdate db = new pgUpdate();
		for(int x=0; x < model.getRowCount(); x++){
			Boolean selected = (Boolean) model.getValueAt(x, 0);
			String entity_id = (String) model.getValueAt(x, 10);
			String proj_id = (String) model.getValueAt(x, 11);
			String pbl_id = (String) model.getValueAt(x, 12);
			Integer seq_no = (Integer) model.getValueAt(x, 13);

			if(selected){
				String SQL = "INSERT INTO rf_buyer_status(\n" + 
						"            entity_id, proj_id, pbl_id, seq_no, byrstatus_id, tran_date, \n" + 
						"            actual_date, status_id, branch_id, created_by)\n" + 
						"    VALUES ('"+ entity_id +"', '"+ proj_id +"', '"+ pbl_id +"', '"+ seq_no +"', '40', now(), \n" + 
						"            now(), 'A', '"+ UserInfo.Branch +"', '"+ UserInfo.EmployeeCode +"');";

				db.executeUpdate(SQL, true);
			}
		}
		db.commit();
	}

	public static void saveIn(modelSCDM_InAccounts model) {
		pgUpdate db = new pgUpdate();
		for(int x=0; x < model.getRowCount(); x++){
			Boolean selected = (Boolean) model.getValueAt(x, 0);
			String entity_id = (String) model.getValueAt(x, 7);
			String proj_id = (String) model.getValueAt(x, 8);
			String pbl_id = (String) model.getValueAt(x, 9);
			Integer seq_no = (Integer) model.getValueAt(x, 10);

			if(selected){
				String SQL = "INSERT INTO rf_buyer_status(\n" + 
						"            entity_id, proj_id, pbl_id, seq_no, byrstatus_id, tran_date, \n" + 
						"            actual_date, status_id, branch_id, created_by)\n" + 
						"    VALUES ('"+ entity_id +"', '"+ proj_id +"', '"+ pbl_id +"', '"+ seq_no +"', '41', now(), \n" + 
						"            now(), 'A', '"+ UserInfo.Branch +"', '"+ UserInfo.EmployeeCode +"');";

				db.executeUpdate(SQL, true);
			}
		}
		db.commit();
	}
	
	public static void saveInactiveStatus(modelSCDStatusEditor model, String entity_id, String proj_id, String unit_id, String seq_no, String status_id){
		pgUpdate db = new pgUpdate();
		for(int x= 0; x<model.getRowCount(); x++){
			Boolean isSelected = (Boolean) model.getValueAt(x, 0);
			String byrstatus_id = (String) model.getValueAt(x, 1);
			
			if(isSelected){
				String SQL = "UPDATE rf_buyer_status SET status_id = '"+status_id+"' \n"+
							 "WHERE entity_id = '"+entity_id+"' \n"+
							 "AND proj_id = '"+proj_id+"' \n"+
							 "AND pbl_id = getinteger('"+unit_id+"')::VARCHAR \n"+
							 "AND seq_no = "+seq_no+" \n"+
							 "AND byrstatus_id = '"+byrstatus_id+"' \n"+
							 "AND status_id = 'A'";
				db.executeUpdate(SQL, true);
				FncSystem.out("Display Update SQL", SQL);
			}
		}
		db.commit();
	}

}
