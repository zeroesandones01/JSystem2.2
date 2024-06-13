package Accounting.Collections;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import Buyers.CreditandCollections._RealTimeDebit;
import Database.pgSelect;
import Database.pgUpdate;
import Functions.FncGlobal;
import Functions.UserInfo;
import tablemodel.modelCheckStatus;

public class _CheckStatusMonitoring {

	private static DefaultTableModel model; 

	public void saveCheckStatus(String checkstat_id, String bnc_rsn, String pbl_id, String seq_no, String check_no, String remarks) {
		String strSQL = "update rf_payments \n" + 
				"set check_stat_id = '"+checkstat_id+"', \n" + 
				"bounce_reason_id =  '"+bnc_rsn+"', \n" + 
				"remarks = '"+remarks+"' \n" + 
				"where pbl_id = '"+pbl_id+"' and seq_no = '"+seq_no+"' and check_no = '"+check_no+"'; ";

		pgUpdate dbExec = new pgUpdate();
		dbExec.executeUpdate(strSQL, false);
		dbExec.commit();
	}

	public void removeCheckDeposit(String strCheck, Integer intPayID) {
		String strSQL = "update cs_dp_chk_detail \n" + 
				"set edited_by = '"+UserInfo.EmployeeCode+"', \n" +
				"date_edited = now(), status_id = 'I' \n" + 
				"where check_no = '"+strCheck+"' and pay_rec_id::int = '"+intPayID+"'::int; ";

		pgUpdate db = new pgUpdate();
		db.executeUpdate(strSQL, false);
		db.commit();
	}

	public void removeLedger(String strUnit, String strSeq, String strCheck) {
		pgUpdate dbUpdate = new pgUpdate();
		pgSelect dbSelect = new pgSelect();

		System.out.println("");
		System.out.println("Ledger Removal Method");
		System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");

		System.out.println("");
		System.out.println("strUnit: " + strUnit);
		System.out.println("strSeq: " + strSeq);
		System.out.println("strCheck: " + strCheck);

		String strAllRec = "";
		String strID = _RealTimeDebit.GetValue("SELECT entity_id FROM rf_payments WHERE pbl_id = '" + strUnit
				+ "' AND seq_no = '" + strSeq + "' AND trim(check_no) = '" + strCheck + "'");
		String strProject = _RealTimeDebit.GetValue("SELECT proj_id FROM rf_payments WHERE pbl_id = '" + strUnit
				+ "' AND seq_no = '" + strSeq + "' AND trim(check_no) = '" + strCheck + "'");
		String strRec = _RealTimeDebit.GetValue("SELECT TRIM(pay_rec_id::CHAR(20)) FROM rf_payments WHERE pbl_id = '"
				+ strUnit + "' AND seq_no = '" + strSeq + "' AND trim(check_no) = '" + strCheck + "'");

		System.out.println("");
		System.out.println("strID: " + strID);
		System.out.println("strProject: " + strProject);
		System.out.println("strRec: " + strRec);

		/***
		 * Select all the proceeding pay_rec_id(s) and the pay_rec_id of the withdrawn
		 * check itself
		 ***/
		String SQL_1 = "SELECT DISTINCT pay_rec_id \n" + "FROM rf_client_ledger \n" + "WHERE entity_id = '" + strID
				+ "' AND proj_id = '" + strProject + "' AND pbl_id = '" + strUnit + "' \n" + "AND seq_no = " + strSeq
				+ " AND status_id = 'A' AND pay_rec_id::INTEGER >= '" + strRec + "'::INTEGER \n"
				+ "ORDER BY pay_rec_id";

		System.out.println("");
		System.out.println(SQL_1);
		dbSelect = new pgSelect();
		dbSelect.select(SQL_1);

		System.out.println("");
		System.out.println("Row Count: " + dbSelect.getRowCount());

		if (dbSelect.getRowCount() > 0) {
			/*** Put all the pay_rec_id(s) in a variable ***/
			for (Integer x = 0; x < dbSelect.getRowCount(); x++) {
				strAllRec = strAllRec + dbSelect.getResult()[x][0] + ", ";
			}

			strAllRec = strAllRec.substring(0, strAllRec.length() - 2);
			System.out.println("");
			System.out.println("strAllRec: " + strAllRec);

			String SQL_2 = "";

			Boolean blnReapp = false;
			if (dbSelect.getRowCount() > 1) {
				blnReapp = true;
			} else {
				blnReapp = false;
			}

			System.out.println("");
			System.out.println("blnReapp: " + blnReapp);
			System.out.println("dbSelect.getRowCount(): " + dbSelect.getRowCount());

			if (blnReapp) {
				System.out.println("");
				System.out.println("X Update");

				/*** Deactivate all these pay_rec_id(s); Set temporary status; ***/
				SQL_2 = "UPDATE rf_client_ledger \n" + "SET status_id = 'X' \n" + "where pay_rec_id::INTEGER IN ("
						+ strAllRec + ")";

				System.out.println("");
				System.out.println("SQL_2: " + SQL_2);
				dbUpdate.executeUpdate(SQL_2, false);
				dbUpdate.commit();

				reapplyLedger(strUnit, strSeq, strCheck);
			} else {
				System.out.println("");
				System.out.println("I Update");

				/*** Deactivate all these pay_rec_id(s); ***/
				SQL_2 = "UPDATE rf_client_ledger \n" + "SET status_id = 'I' \n" + "where pay_rec_id::INTEGER IN ("
						+ strAllRec + ")";

				System.out.println("");
				System.out.println("SQL_2: " + SQL_2);
				dbUpdate.executeUpdate(SQL_2, false);
				dbUpdate.commit();
			}
		}
	}

	public void reapplyLedger(String strUnit, String strSeq, String strCheck) {
		pgUpdate dbUpdate = new pgUpdate();
		pgSelect dbSelect = new pgSelect();

		System.out.println("");
		System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
		System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");

		String strAllRec = "";
		String strID = _RealTimeDebit.GetValue("SELECT entity_id FROM rf_payments WHERE pbl_id = '" + strUnit
				+ "' AND seq_no = '" + strSeq + "' AND trim(check_no) = '" + strCheck + "'");
		String strProject = _RealTimeDebit.GetValue("SELECT proj_id FROM rf_payments WHERE pbl_id = '" + strUnit
				+ "' AND seq_no = '" + strSeq + "' AND trim(check_no) = '" + strCheck + "'");
		String strRec = _RealTimeDebit.GetValue("SELECT TRIM(pay_rec_id::CHAR(20)) FROM rf_payments WHERE pbl_id = '"
				+ strUnit + "' AND seq_no = '" + strSeq + "' AND trim(check_no) = '" + strCheck + "'");

		System.out.println("");
		System.out.println("strID: " + strID);
		System.out.println("strProject: " + strProject);
		System.out.println("strRec: " + strRec);

		/*** Reselect the pay_rec_id(s) proceeding the check's pay_rec_id ***/
		String SQL_3 = "SELECT DISTINCT pay_rec_id \n" + "FROM rf_client_ledger \n" + "WHERE entity_id = '" + strID
				+ "' AND proj_id = '" + strProject + "' AND pbl_id = '" + strUnit + "' \n" + "AND seq_no = " + strSeq
				+ " AND status_id = 'X' AND pay_rec_id::INTEGER > '" + strRec + "'::INTEGER \n" + "ORDER BY pay_rec_id";

		dbSelect = new pgSelect();
		dbSelect.select(SQL_3);

		System.out.println("");
		System.out.println(SQL_3);

		System.out.println("");
		System.out.println("SQL_3 Row Count: " + dbSelect.getRowCount());

		if (dbSelect.getRowCount() > 0) {
			/*** Put all the pay_rec_id(s) in a variable ***/
			for (Integer x = 0; x < dbSelect.getRowCount(); x++) {
				strAllRec = strAllRec + dbSelect.getResult()[x][0] + ", ";
			}

			strAllRec = strAllRec.substring(0, strAllRec.length() - 2);
			System.out.println("");
			System.out.println("strAllRec: " + strAllRec);

			/*** Reapply these pay_rec_id(s) ***/
			String SQL_4 = "SELECT 'SELECT sp_apply_to_ledger (''' || A.entity_id || ''', ''' || A.proj_id || ''', ''' || A.pbl_id || ''', ' || A.seq_no || ', ''' || \n"
					+ "A.unit_id || ''', ''' || A.pay_part_id || ''', ' || A.amount || ', ''' || A.pay_rec_id || ''', ''' || A.created_by || ''', ''' || \n"
					+ "(CASE WHEN A.pymnt_type = 'A' THEN A.actual_date::DATE ELSE CASE \n"
					+ "WHEN (SELECT COUNT(*) FROM rf_check_history X WHERE X.new_checkstat_id = '04' AND X.status_id = 'A' AND X.pay_rec_id::INT = A.pay_rec_id::INT) > 0 \n"
					+ "THEN (SELECT X.trans_date::DATE FROM rf_check_history X WHERE X.new_checkstat_id = '02' AND X.status_id = 'A' AND X.pay_rec_id::INT = A.pay_rec_id::INT LIMIT 1) \n"
					+ "ELSE (SELECT X.trans_date::DATE FROM rf_check_history X WHERE X.new_checkstat_id = '05' AND X.status_id = 'A' AND X.pay_rec_id::INT = A.pay_rec_id::INT LIMIT 1) \n"
					+ "END END) || '''::TIMESTAMP, ' || 'false);'  \n" + "FROM rf_payments A \n"
					+ "WHERE A.pay_rec_id::INTEGER IN (" + strAllRec + ") AND status_id = 'A' \n"
					+ "ORDER BY A.actual_date, A.client_seqno";

			System.out.println("");
			System.out.println(SQL_4);
			dbSelect = new pgSelect();
			dbSelect.select(SQL_4);

			System.out.println("");
			for (Integer x = 0; x < dbSelect.getRowCount(); x++) {
				pgSelect dbExec = new pgSelect();

				System.out.println("");
				System.out.println(dbSelect.getResult()[x][0]);

				String strExec = (String) dbSelect.getResult()[x][0];
				dbExec.select(strExec);
			}
		}

		String SQL_5 = "UPDATE rf_client_ledger \n" + "SET status_id = 'I' \n" + "where (pay_rec_id::INTEGER IN ("
				+ strAllRec + ") OR pay_rec_id::INTEGER = '" + strRec + "'::INTEGER) AND status_id = 'X'";

		dbUpdate.executeUpdate(SQL_5, false);
		dbUpdate.commit();

		System.out.println("");
		System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
		System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
	}

	public void insertCheckStatus(String checkstat_id, String bnc_rsn, String pbl_id, String seq_no,
			String check_no, Integer rec_id, String prev_checkstat_id, String dep_no) {

		if (prev_checkstat_id.equals(checkstat_id)) {

		} else {
			String strSQL = "insert into rf_check_history (pay_rec_id, prev_checkstat_id, new_checkstat_id, bounce_reason_id, trans_date, dep_no, status_id, created_by, date_created) \n" +
					"values ('"+rec_id+"', '"+prev_checkstat_id+"', '"+checkstat_id + "', '"+bnc_rsn+"', now(), '"+dep_no+ "', 'A', '"+ UserInfo.EmployeeCode+"', now()); ";

			pgUpdate dbExec = new pgUpdate(); 
			dbExec.executeUpdate(strSQL, false);
			dbExec.commit();
		}
	}

	public void create_jv_bounced(String strPayID) {
		pgUpdate dbExec = new pgUpdate();
		String strSQL = "call sp_create_jv_check_bounced("+strPayID+", '"+ UserInfo.EmployeeCode+"');";
		dbExec.executeUpdate(strSQL, false);
		dbExec.commit();
	}

	public void create_jv_withdrawn(String strPayID) {
		pgUpdate dbExec = new pgUpdate();
		String strSQL = "call sp_create_jv_check_withdrawn("+strPayID+", '"+ UserInfo.EmployeeCode+"');";
		dbExec.executeUpdate(strSQL, false);
		dbExec.commit();
	}
	

	public void create_jv_returned(String strPayID) {
		pgUpdate dbExec = new pgUpdate();
		String strSQL = "call sp_create_jv_check_returned("+strPayID+", '"+ UserInfo.EmployeeCode+"');";
		dbExec.executeUpdate(strSQL, false);
		dbExec.commit();
	}
}
