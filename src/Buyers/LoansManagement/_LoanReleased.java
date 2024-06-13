package Buyers.LoansManagement;

import java.math.BigDecimal;
import javax.swing.JTextField;
import Database.pgSelect;
import FormattedTextField._JXFormattedTextField;

public class _LoanReleased {

	public _LoanReleased() {

	}

	public static String sqlClients() {
		System.out.println("SELECT * FROM jsearch WHERE EXISTS(SELECT * FROM rf_buyer_status " +
				"WHERE entity_id = \"Client ID\" AND proj_id = \"Project ID\" " +
				"AND pbl_id = \"PBL ID\" AND seq_no = \"Sequence\" AND byrstatus_id = '35'" +
				"AND (pbl_id, seq_no) not in (select pbl_id, seq_no from rf_pagibig_lnrel where status_id = 'A'));"); 
		
		return "SELECT * FROM jsearch WHERE EXISTS(SELECT * FROM rf_buyer_status " +
			"WHERE entity_id = \"Client ID\" AND proj_id = \"Project ID\" " +
			"AND pbl_id = \"PBL ID\" AND seq_no = \"Sequence\" AND byrstatus_id = '35'" +
			"AND (pbl_id, seq_no) not in (select pbl_id, seq_no from rf_pagibig_lnrel where status_id = 'A'));";
	}

	static void displayClientDetails(String entity_id, String proj_id, String pbl_id, Integer seq_no, JTextField txtClientName, JTextField txtProjectID, JTextField txtProjectName, JTextField txtPblID, JTextField txtUnitDescription, JTextField txtseqno, JTextField txtTermRate, _JXFormattedTextField txtLoanableAmount, _JXFormattedTextField txtProcessingFee, _JXFormattedTextField txtRetentionFee) {
		String SQL = "SELECT a.entity_id, get_client_name(a.entity_id), a.projcode, b.proj_name, \n" + 
				"a.pbl_id, get_unit_description(a.projcode, a.pbl_id), a.seq_no, \n" + 
				"c.term, c.int_rate, c.loanable_amount, c.proc_fee, c.retention_fee \n" +  
				"FROM rf_sold_unit a \n" +  
				"LEFT JOIN mf_project b ON b.proj_id = a.projcode \n" + 
				"LEFT JOIN rf_pagibig_computation c ON c.entity_id = a.entity_id AND c.proj_id = a.projcode \n" +
				"AND c.pbl_id = a.pbl_id AND c.seq_no = a.seq_no \n" +  
				"WHERE a.entity_id = '"+ entity_id +"' AND a.projcode = '"+ proj_id +"' \n" +
				"AND a.pbl_id = '"+ pbl_id +"' AND a.seq_no = "+ seq_no +" AND c.status_id = 'A';";

		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			txtClientName.setText((String) db.getResult()[0][1]);
			txtProjectID.setText((String) db.getResult()[0][2]);
			txtProjectName.setText((String) db.getResult()[0][3]);
			txtPblID.setText((String) db.getResult()[0][4]);
			txtUnitDescription.setText((String) db.getResult()[0][5]);
			txtseqno.setText((String) db.getResult()[0][6]);
			txtTermRate.setText(String.format("%s   /   %s", Integer.toString((Integer) db.getResult()[0][7]), ((BigDecimal) db.getResult()[0][8]).toString()));
			txtLoanableAmount.setValue((BigDecimal) db.getResult()[0][9]);
			txtProcessingFee.setValue((BigDecimal) db.getResult()[0][10]);
			txtRetentionFee.setValue((BigDecimal) db.getResult()[0][11]);
		}
	}
}
