package Utilities;

import javax.swing.JOptionPane;

import Database.pgSelect;
import Database.pgUpdate;
import Functions.UserInfo;

public class UPInsertFromXML {
	public void insertToDetail(
			String client_seqno, 
			String part_type, 
			String bank, 
			String branch, 
			String acct_no, 
			String check_no, 
			String check_date, 
			String amount, 
			String receipt_no, 
			String receipt_type, 
			String status_id) {
		if(client_seqno.trim().equals("")) {
			client_seqno = null;
		}
		if(part_type.trim().equals("")) {
			part_type = null;
		} else {
			part_type = part_type.trim();
		}
		if(bank.trim().equals("")) {
			bank = null;
		} else {
			bank = bank.trim();
		}
		if(branch.trim().equals("")) {
			branch = null;
		} else {
			branch = branch.trim();
		}
		if(acct_no.trim().equals("")) {
			acct_no = null;
		} else {
			acct_no = acct_no.trim();
		}
		if(check_no.trim().equals("")) {
			check_no = null;
		} else {
			check_no = check_no.trim();
		}
		if(check_date.trim().equals("")) {
			check_date = null;
		} else {
			check_date = check_date.trim();
		}
		if(amount.trim().equals("")) {
			amount = null;
		} else {
			amount = amount.trim();
		}
		if(receipt_no.trim().equals("")) {
			receipt_no = null;
		} else {
			receipt_no = receipt_no.trim();
		}
		if(receipt_type.trim().equals("")) {
			receipt_type = null;
		} else {
			receipt_type = receipt_type.trim();
		}
		if(status_id.trim().equals("")) {
			status_id = null;
		} else {
			status_id = status_id.trim();
		}
		pgSelect pg = new pgSelect();
		String SQL = "SELECT sp_insert_rf_pay_detail(nullif("
				+ "'"+client_seqno+"','null'), "
				+ "nullif('"+part_type+"','null'), "
				+ "nullif('"+bank+"','null'), "
				+ "nullif('"+branch+"','null'), "
				+ "nullif('"+acct_no+"','null'), "
				+ "nullif('"+check_no+"','null'), "
				+ "nullif('"+check_date+"','null')::TIMESTAMP WITHOUT TIME ZONE, "
				+ ""+amount+", "
				+ "nullif('"+receipt_no+"','null'), "
				+ "nullif('"+receipt_type+"','null'), "
				+ "nullif('"+status_id+"','null'), "
				+ "'"+UserInfo.EmployeeCode+"')";
		System.out.println(SQL);
		pg.select(SQL, "ERROR", true);
	}

	public void insertToHeader(
			String client_seqno, 
			String trans_date, 
			String proj_id, 
			String pbl_id, 
			String seq_no, 
			String entity_id, 
			String selling_price, 
			String total_amt_paid, 
			String status_id, 
			String co_id) {
		if(client_seqno.trim().equals("")) {
			client_seqno = null;
		}
		if(trans_date.trim().equals("")) {
			trans_date = null;
		} else {
			trans_date = trans_date.trim();
		}
		if(proj_id.trim().equals("")) {
			proj_id = null;
		} else {
			proj_id = proj_id.trim();
		}
		if(pbl_id.trim().equals("")) {
			pbl_id = null;
		} else {
			pbl_id = pbl_id.trim();
		}
		if(seq_no.trim().equals("")) {
			seq_no = null;
		} else {
			seq_no = seq_no.trim();
		}
		if(entity_id.trim().equals("")) {
			entity_id = null;
		} else {
			entity_id = entity_id.trim();
		}
		if(selling_price.trim().equals("")) {
			selling_price = null;
		} else {
			selling_price = selling_price.trim();
		}
		if(total_amt_paid.trim().equals("")) {
			total_amt_paid = null;
		} else {
			total_amt_paid = total_amt_paid.trim();
		}
		if(status_id.trim().equals("")) {
			status_id = null;
		} else {
			status_id = status_id.trim();
		}
//		if(op_status.trim().equals("")) {
//			op_status = null;
//		} else {
//			op_status = op_status.trim();
//		}
		if(co_id.trim().equals("")) {
			co_id = null;
		} else {
			co_id = co_id.trim();
		}
		pgSelect pg = new pgSelect();
		String SQL = "select sp_insert_rf_pay_header("
				+ "'"+client_seqno+"',"
				+ "'"+trans_date+"',"
				+ "'"+proj_id+"',"
				+ "'"+pbl_id+"',"
				+ "'"+seq_no+"',"
				+ "'"+entity_id+"',"
				+ ""+selling_price+","
				+ ""+total_amt_paid+","
				+ "nullif('"+status_id+"','null'),"
				+ "nullif('"+co_id+"','null'),"
				+ "'"+UserInfo.EmployeeCode+"')";
		System.out.println(SQL);
		pg.select(SQL, "ERROR", true);
	}


}
