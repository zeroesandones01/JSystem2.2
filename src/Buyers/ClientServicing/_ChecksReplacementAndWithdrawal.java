package Buyers.ClientServicing;

import Database.pgSelect;

public class _ChecksReplacementAndWithdrawal {

	public static String Client() {
		return "SELECT a.entity_id, a.entity_name, c.proj_name, d.description, c.proj_id, d.pbl_id, b.seq_no \n" +
			"FROM rf_entity A \n" +
			"INNER JOIN rf_sold_unit B ON a.entity_id = b.entity_id \n" +
			"INNER JOIN mf_project C ON b.projcode = c.proj_id \n" +
			"INNER JOIN mf_unit_info D ON b.projcode = d.proj_id and b.pbl_id = d.pbl_id \n" +
			"WHERE b.status_id = 'A' \n" +
			"ORDER BY a.entity_name";
	}
	
	public static String GetNo() {
		pgSelect dbWith = new pgSelect();
		String strWith = "";
		String strSQL = "";
		
		System.out.println("");
		System.out.println("Inspection");
		
		strSQL = "SELECT TRIM(to_char(max(COALESCE(withdraw_no::INT, 0)) + 1, '000000000')) FROM rf_check_forwithdrawal";
		dbWith.select(strSQL);
		
		if (dbWith.isNotNull()) {
			strWith = (String) dbWith.getResult()[0][0];
		} else {
			strWith = "000000001";
		}
		
		if (strWith==null) {
			strWith = "000000001";
		}
		
		return strWith;
	}
	
	public static String GetRec() {
		pgSelect dbWith = new pgSelect();
		String strWith = "";
		String strSQL = "";
		
		System.out.println("");
		System.out.println("Inspection");
		
		strSQL = "SELECT trim(((max(COALESCE(withdraw_rec_id::INT, 0)) + 1)::varchar(5))) FROM rf_check_forwithdrawal";
		dbWith.select(strSQL);
		
		if (dbWith.isNotNull()) {
			strWith = (String) dbWith.getResult()[0][0];
		} else {
			strWith = "1";
		}
		
		if (strWith==null) {
			strWith = "1";
		}
		
		return strWith;
	}

}
