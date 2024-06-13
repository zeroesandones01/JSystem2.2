package Buyers.CreditandCollections;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import Database.pgSelect;
import Database.pgUpdate;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.UserInfo;

import java.util.*;

import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

import com.sun.mail.imap.protocol.FLAGS;

public class _RealTimeDebit {
	
	public static String Padme(String str, Integer int_i) {
		String strPad = "";
		Integer i = int_i - str.length();
		
		while (i > 0) {
			strPad = strPad + "0";
			i--;
		}
		strPad = strPad + str;
		return strPad;
	}
	
	public static String peruser(String strDir) {
		String strRAM = "";
		String strName = "";
		
		String strID = "";
		String strProject = "";
		String strUnit = "";
		String strSequence = "";
		String strBranchC = "";
		String strAcctC = "";
		String strBranchD = "";
		String strAcctD = "";
		String strSubs = "";
		String strDebit = "";
		String strReason = "";
		
    	File peruse = null;
    	String strStatus = "";
    	
    	System.out.println("Reading");
    	
    	//_RealTimeDebit.Delete();
    	
    	try {
    		peruse = new File(strDir);
    	}
    	catch (NullPointerException e) {
    		JOptionPane.showMessageDialog(null, "No directory was selected.");
    	}
    	
    	Scanner scan = null;
    	
		try {
			scan = new Scanner(peruse);
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
    	
    	while (scan.hasNextLine()) {
			String strReader = scan.nextLine();
			
			if (strReader.substring(strReader.length() - (Integer)20).trim().equals("")) {
				System.out.println("Last 20 Characters: " + strReader.substring(strReader.length() - (Integer)20));
				strStatus = "Posted";
				RealTimeDebitUpload.btnUpload.setEnabled(true);
				RealTimeDebitUpload.btnSave.setEnabled(true);
			}
			else {
				System.out.println("Last 20 Characters: " + strReader.substring(strReader.length() - (Integer)20));
				strStatus = "---";
				RealTimeDebitUpload.btnUpload.setEnabled(false);
				RealTimeDebitUpload.btnSave.setEnabled(false);
			}
			
			System.out.println("");
			System.out.println("Reason: " + "'" + strReader.substring(strReader.length() - 20).trim() + "'");
			
			strReason = strReader.substring(strReader.length() - 20).trim();
			
			strBranchC = strReader.substring(0, strReader.trim().length() - (strReader.trim().length() - 3));
			strRAM = strReader.substring(3);
			
			strAcctC = strRAM.substring(0, strRAM.length() - (strRAM.length() - 10));
			strRAM = strRAM.substring(10);
			
			strBranchD  = strRAM.substring(0, strRAM.length() - (strRAM.length() - 3));
			strRAM = strRAM.substring(3);
			
			strAcctD = strRAM.substring(0, strRAM.length() - (strRAM.length() - 10));
			strRAM = strRAM.substring(10);
			
			strSubs = strRAM.substring(0, strRAM.length() - (strRAM.length() - 15));
			strRAM = strRAM.substring(15);
	
			strName = strRAM.substring(0, strRAM.length() - (strRAM.length() - 15));
			strRAM = strRAM.substring(15);
			
			strID = strRAM.substring(0, strRAM.length() - (strRAM.length() - 15));
			strRAM = strRAM.substring(15);
			
			strDebit = strRAM.substring(0, strRAM.length() - (strRAM.length() - 13));
			strRAM = strRAM.substring(13);
	
			strReason = strRAM.substring(0, strRAM.length());
			
			RealTimeDebitUpload.txtReason.setText(strReason);
			
			System.out.println("");
			System.out.println("Branch Code: " + strBranchC);
			System.out.println("Credit Account Number: " + strAcctC);
			System.out.println("Debit Account Number: " + strAcctD);
			System.out.println("Subscriber Number: " + strSubs);
			System.out.println("Customer Store: " + strName);
			System.out.println("Customer Code: " + strID);
			System.out.println("Debit Amount: " + strDebit);
			System.out.println("Filler: " + strReason);

			strUnit = _RealTimeDebit.RemovePad(strID.substring(10));
			strID = strID.substring(0, 10);
			strProject = _RealTimeDebit.GetValue("SELECT projcode FROM rf_sold_unit WHERE entity_id = '"+strID+"' and pbl_id = '"+strUnit+"' and status_id = 'A'");
			strSequence = _RealTimeDebit.GetValue("SELECT seq_no::CHAR(1) FROM rf_sold_unit WHERE entity_id = '"+strID+"' and pbl_id = '"+strUnit+"' and status_id = 'A'");
			strDebit = _RealTimeDebit.GetAmount(strDebit);
			
			System.out.println("");
			System.out.println("Client ID: " + strID);
			System.out.println("Project ID: " + strProject);
			System.out.println("Unit ID: " + strUnit);
			System.out.println("Sequence No.: " + strSequence);
			System.out.println("Debit Amount: " + _RealTimeDebit.GetAmount(strDebit));
			
			Insert(strID, strProject, strUnit, strSequence, strBranchC,	strAcctC, strBranchD, strAcctD, strSubs, strDebit, strReason, UserInfo.EmployeeCode, "");

			if (strStatus.equals("Posted")) {
				//CreateOP(strID, strProject, strUnit, strSequence, strDebit);
			}
    	}
    	scan.close();
    	
    	return strStatus;
    }
	
	public static void Insert(String strID, String strProject, String strUnit, String strSequence, String strBranchC,	String strAcctC, String strBranchD,	String strAcctD, String strSubs, String strDebit, String strReason,	String strUser, String strFile) {
		pgUpdate db_Upd = new pgUpdate();
		
		String SQL = "INSERT INTO rf_rtd_values\n" +
		"(entity_id, projcode, pbl_id, seq_no, c_branch_code, c_acct_no, d_branch_code, d_acct_no, subscriber_no, debit_amt, reason, user_id, status_id)\n" +
		"VALUES\n" +
		"('"+strID+"', '"+strProject+"', '"+strUnit+"', "+strSequence+", '"+strBranchC+"', '"+strAcctC+"', \n"+
		"'"+strBranchD+"', '"+strAcctD+"', '"+strSubs+"', '"+strDebit+"', '"+strReason+"', '"+strUser+"', 'I')";
		
		db_Upd.executeUpdate(SQL, false);
		db_Upd.commit();
	}
	
	public static void Delete() {
		pgUpdate db_Del = new pgUpdate();
		String SQL_Rev = "UPDATE rf_rtd_values SET status_id = 'P' WHERE status_id = 'A'";
		db_Del.executeUpdate(SQL_Rev, false);
		db_Del.commit();	
	}
	
	public static void Select() {
		
	}
	
	public static String RemovePad(String strUnit) {
		String strRet = "";
		Integer intLen = 0;
		
		intLen = strUnit.length();
		
		for (intLen = strUnit.length(); intLen > 0; intLen--) {
			if (strUnit.substring(0, 1).equals("0")) {
				strUnit = strUnit.substring(1);
			}
		}
		
		strRet = strUnit;
		return strRet;
	}
	
	public static String GetValue(String SQL) {
		String strValue = "";
		pgSelect sqlExec = new pgSelect();
		
		sqlExec.select(SQL);

		if (sqlExec.isNotNull()) {
			strValue = (String) sqlExec.getResult()[0][0];
		} else {
			strValue = "";
		}
		
		return strValue;
	}
	
	public static String GetAmount(String strAmt) {
		String strRet = "";
		String strReal = "";
		String strDecimal = "";
		
		strRet = RemovePad(strAmt);
		
		strReal = strRet.substring(0, strRet.length() - 2);
		strDecimal = strRet.substring(strReal.length());
		
		if (strDecimal.substring(1).equals(0)) {
			strDecimal = strDecimal.substring(0, 1) + "1";
		}
		
		strRet = strReal + "." + strDecimal;
		
		System.out.println("");
		System.out.println("strReal: " + strReal);
		System.out.println("strDecimal: " + strDecimal);
		System.out.println("strRet: " + strRet);
		
		return strRet;
	}
	
	public static void rtdDisplay(DefaultTableModel modelMain, JList rowHeader){
		try {
			/*
			String strSQL = "SELECT TRIM(B.entity_name) AS Name, false, D.proj_name, C.Description, A.d_acct_no,\n" +
					"(SELECT x.c_scheddate FROM view_card_dues(A.entity_id, A.projcode, A.pbl_id, A.seq_no, Now()::TIMESTAMP, FALSE) x LIMIT 1) as DueDate,\n" + 
					"(SELECT SUM(x.c_amount) FROM view_card_dues(A.entity_id, A.projcode, A.pbl_id, A.seq_no, Now()::TIMESTAMP, FALSE) x) as DueAmt,\n" +
					"(SELECT x.c_part_desc FROM view_card_dues(A.entity_id, A.projcode, A.pbl_id, A.seq_no, Now()::TIMESTAMP, FALSE) x ORDER BY x.c_scheddate ASC LIMIT 1) as Particular\n" +
					"FROM rf_rtd_values A\n" +
					"INNER JOIN rf_entity B ON A.entity_id = B.entity_id\n" +
					"INNER JOIN mf_unit_info C ON A.projcode = C.proj_id AND A.pbl_id = C.pbl_id\n" +
					"INNER JOIN mf_project D ON A.projcode = D.proj_id\n" +
					"WHERE A.user_id = '"+UserInfo.EmployeeCode+"'\n" +
					"ORDER BY B.entity_name";
			*/
			String strSQL = "SELECT DISTINCT TRIM(B.entity_name) AS Name,D.proj_name, C.Description, '' as SalesGroup,\n" +
					"'DP' as PaymentParticular, 'WACK-WACK' as BankBranch, Now()::Date as DebitDate,\n" +
					/*"(SELECT SUM(x.c_amount) FROM view_card_dues(A.entity_id, A.projcode, A.pbl_id, A.seq_no, Now()::TIMESTAMP, FALSE) x) as DueAmt\n" +*/
					"TRIM(debit_amt::CHAR(10))::DECIMAL as DueAmt, B.entity_name\n" +
					"FROM rf_rtd_values A\n" +
					"INNER JOIN rf_entity B ON A.entity_id = B.entity_id\n" +
					"INNER JOIN mf_unit_info C ON A.projcode = C.proj_id AND A.pbl_id = C.pbl_id\n" +
					"INNER JOIN mf_project D ON A.projcode = D.proj_id\n" +
					"WHERE A.user_id = '"+UserInfo.EmployeeCode+"' AND A.status_id = 'I'\n" +
					"ORDER BY B.entity_name";
			
			try {
				FncTables.clearTable(modelMain);
			}
			catch (IndexOutOfBoundsException e) {
				System.out.println("Out of bounds exception occured.");
			}
			
			DefaultListModel listModel = new DefaultListModel();
			rowHeader.setModel(listModel);
			
			pgSelect db = new pgSelect();
			db.select(strSQL);
			if (db.isNotNull()) {
				for (int x = 0; x < db.getRowCount(); x++) {
					modelMain.addRow(db.getResult()[x]);
					listModel.addElement(modelMain.getRowCount());
				}
			}
			else {
				JOptionPane.showMessageDialog(null, "No records were returned for posting.");
			};
		}
		catch (NullPointerException e) {
			JOptionPane.showMessageDialog(null, "Some parameters may not have been supplied.");
		}
	}
	
	public void Issue(String strID, String strProject, String strUnit, String strSequence, String strAmt) {
		ArrayList<String> listPartID = new ArrayList<String>();
		
		String type = "null";
		String check_type = "null";
		String check_no = "null";
		String check_date = "null";
		String account_no = "null";
		String bank_id = "null";
		String bank_branch_id = "null";
		String receipt_no = "null";
		String brstn = "null";
		String credit = "null";
		
		String strPart = GetValue("SELECT Y.pay_part_id::CHAR(3)\n" + 
				"FROM view_card_dues('"+strID+"', '"+strProject+"', '"+strUnit+"', "+strSequence+", Now()::TIMESTAMP, FALSE) X\n" +
				"INNER JOIN mf_client_ledger_part Y ON Y.part_id = X.c_part_id\n" +
				"ORDER BY c_scheddate ASC LIMIT 1");
		
		System.out.println(strPart);
		listPartID.add(String.format("'%s'", strPart));
		String part_id = listPartID.toString().replaceAll("\\[|\\]", "");
		
		String SQL = "SELECT sp_ir_post_new('"+ strID +"', '"+ strProject +"', '"+ strUnit +"', "+ strSequence +", "+ strAmt +", \n" + 
				"     ARRAY["+ part_id +"]::VARCHAR[], \n" + 
				"     ARRAY["+ strAmt +"]::NUMERIC[], \n" + 
				"     ARRAY["+ type +"]::VARCHAR[], \n" + 
				"     ARRAY["+ (check_no.equals("'null'") ? "null":check_no) +"]::VARCHAR[], \n" + 
				"     ARRAY["+ (check_type.equals("'null'") ? "null":check_type) +"]::VARCHAR[], \n" + 
				"     ARRAY["+ (check_date.equals("'null'") ? "null":check_date) +"]::TIMESTAMP[],\n" + 
				"     ARRAY["+ (account_no.equals("'null'") ? "null":account_no) +"]::VARCHAR[], \n" + 
				"     ARRAY["+ (bank_id.equals("'null'") ? "null":bank_id) +"]::VARCHAR[], \n" + 
				"     ARRAY["+ (bank_branch_id.equals("'null'") ? "null":bank_branch_id) +"]::VARCHAR[], \n" + 
				"     ARRAY["+ (receipt_no.equals("'null'") ? "null":receipt_no) +"]::VARCHAR[], \n" + 
				"     NULL, \n" + 
				"     ARRAY["+ credit +"]::BOOLEAN[], \n" + 
				"     '"+ "02" +"', '"+ UserInfo.Branch +"', '"+ UserInfo.EmployeeCode +"');";

		FncSystem.out("Issuance of Receipt", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL, "Save", true);
	}
	
	public static String CreateOP(String strName, String Unit) {
		String strOPNo = "";
		ArrayList<String> listPartID = new ArrayList<String>();
		String strID = "";
		String strProject = "";
		String strUnitID = "";
		String strSequence = "";
		String strDebit = "";
		
		strID = GetValue("SELECT entity_id FROM rf_entity WHERE entity_name = '"+strName+"'");
		strUnitID = GetValue("SELECT a.pbl_id FROM rf_sold_unit A \n" +
				"INNER JOIN mf_unit_info B ON a.projcode = b.proj_id and a.pbl_id = b.pbl_id \n" +
				"INNER JOIN rf_entity C ON a.entity_id = c.entity_id \n" +
				"WHERE c.entity_name = '"+strName+"' and b.description = '"+Unit+"'");
		strProject = GetValue("SELECT projcode FROM rf_sold_unit WHERE entity_id = '"+strID+"' and pbl_id = '"+strUnitID+"' and status_id = 'A'");
		strSequence = GetValue("SELECT seq_no::CHAR(1) FROM rf_sold_unit WHERE entity_id = '"+strID+"' and pbl_id = '"+strUnitID+"' and status_id = 'A'");
		/*	Modified by Mann2x; Date Modified: March 29, 2017; Causes the wrong amount being posted in the OP;	*/
		strDebit = GetValue("SELECT TRIM(debit_amt::CHAR(10)) FROM rf_rtd_values WHERE entity_id = '"+strID+"' and projcode = '"+strProject+"' and pbl_id = '"+strUnitID+"' and seq_no = "+strSequence+" order by debit_date::date desc limit 1;");
		
		String strPart = GetValue("SELECT Y.pay_part_id::CHAR(3)\n" + 
				"FROM view_card_dues('"+strID+"', '"+strProject+"', '"+strUnitID+"', "+strSequence+", Now()::TIMESTAMP, FALSE) X\n" +
				"INNER JOIN mf_client_ledger_part Y ON Y.part_id = X.c_part_id\n" +
				"ORDER BY c_scheddate ASC LIMIT 1");
		
		System.out.println(strPart);
		listPartID.add(String.format("'%s'", strPart));
		String part_id = listPartID.toString().replaceAll("\\[|\\]", "");
		
		String type = "null";
		String check_type = "null";
		String check_no = "null";
		String check_date = "null";
		String account_no = "null";
		String bank_id = "null";
		String bank_branch_id = "null";
		String receipt_no = "null";
		String brstn = "null";
		String credit = "null";
		
		String strOP = "SELECT * from sp_op_post_new(" +
				"'"+ UserInfo.Branch +"', " +
				"'"+ strID +"', " +
				"'"+ strProject +"', " +
				"'"+ strUnitID +"', " +
				""+ strSequence +", " + 
				""+ strDebit +",\n" +
				"  ARRAY["+ part_id +"]::VARCHAR[],\n" + 
				"  ARRAY["+ strDebit +"]::NUMERIC[],\n" + 
				"  ARRAY["+ type +"]::VARCHAR[],\n" + 
				"  ARRAY["+ (check_type.equals("'null'") ? "null":check_type) +"]::VARCHAR[],\n" + 
				"  ARRAY["+ (check_no.equals("'null'") ? "null":check_no) +"]::VARCHAR[],\n" + 
				"  ARRAY["+ (check_date.equals("'null'") ? "null":check_date) +"]::TIMESTAMP[],\n" + 
				"  ARRAY["+ (account_no.equals("'null'") ? "null":account_no) +"]::VARCHAR[],\n" + 
				"  ARRAY["+ (bank_id.equals("'null'") ? "null":bank_id) +"]::VARCHAR[],\n" + 
				"  ARRAY["+ (bank_branch_id.equals("'null'") ? "null":bank_branch_id) +"]::VARCHAR[],\n" + 
				"  ARRAY["+ (receipt_no.equals("'null'") ? "null":receipt_no) +"]::VARCHAR[],\n" + 
				"  NULL,\n" + 
				"  ARRAY["+ (brstn.equals("'null'") ? "null":brstn) +"]::VARCHAR[],\n" + 
				"  ARRAY["+ credit +"]::BOOLEAN[], '"+ UserInfo.EmployeeCode +"');";
		
		FncSystem.out("SQL: ", strOP);
		pgSelect db = new pgSelect();
		db.select(strOP);
		
		if (db.isNotNull()) {
			strOPNo = (String)db.getResult()[0][1];
			System.out.print("Client sequence number: " + (String)db.getResult()[0][1]);
		} else {
			strOPNo = "";
			System.out.print("OP was not saved!");
		}
		
		return strOPNo;
	}
	
	public static String sql_MainAddress(String strID) {
		return "SELECT (CASE WHEN COALESCE(A.addr_no, '') = '' THEN '' ELSE TRIM(A.addr_no) END) || \n" +
			   "(CASE WHEN COALESCE(A.street, '') = '' THEN '' ELSE TRIM(A.street) END) || \n" +
			   "(CASE WHEN ((CASE WHEN COALESCE(A.addr_no, '') = '' THEN '' ELSE TRIM(A.addr_no) END) || (CASE WHEN COALESCE(A.street, '') = '' THEN '' ELSE TRIM(A.street) END)) = '' THEN '' ELSE ', ' END) || \n" +
			   "(CASE WHEN COALESCE(A.barangay, '') = '' THEN '' ELSE TRIM(A.barangay) END) || \n" +
			   "(CASE WHEN COALESCE(B.city_name, '') = '' THEN '' ELSE ', ' END) || COALESCE(B.city_name, '') || \n" +
			   "(CASE WHEN COALESCE(C.municipality_name, '') = '' THEN '' ELSE ', ' END) || COALESCE(C.municipality_name, '') || \n" +
			   "(CASE WHEN COALESCE(D.province_name, '') = '' THEN '' ELSE ', ' END) || COALESCE(D.province_name, '') || '	Zip Code: ' || A.zip_code \n" +
			   "FROM rf_entity_address A \n" +
			   "LEFT JOIN mf_city B ON A.city_id = B.city_id \n" +
			   "LEFT JOIN mf_municipality C ON A.municipality_id = C.municipality_id \n" +
			   "LEFT JOIN mf_province D ON A.province_id = D.province_id \n" +
			   "WHERE A.pref_cts_address = true AND A.entity_id = '"+strID+"'";
	}
	
	public static String sql_OtherAddress(String strID) {
		return "SELECT (CASE WHEN COALESCE(A.addr_no, '') = '' THEN '' ELSE TRIM(A.addr_no) END) || \n" +
			   "(CASE WHEN COALESCE(A.street, '') = '' THEN '' ELSE TRIM(A.street) END) || \n" +
			   "(CASE WHEN ((CASE WHEN COALESCE(A.addr_no, '') = '' THEN '' ELSE TRIM(A.addr_no) END) || (CASE WHEN COALESCE(A.street, '') = '' THEN '' ELSE TRIM(A.street) END)) = '' THEN '' ELSE ', ' END) || \n" +
			   "(CASE WHEN COALESCE(A.barangay, '') = '' THEN '' ELSE TRIM(A.barangay) END) || \n" +
			   "(CASE WHEN COALESCE(B.city_name, '') = '' THEN '' ELSE ', ' END) || COALESCE(B.city_name, '') || \n" +
			   "(CASE WHEN COALESCE(C.municipality_name, '') = '' THEN '' ELSE ', ' END) || COALESCE(C.municipality_name, '') || \n" +
			   "(CASE WHEN COALESCE(D.province_name, '') = '' THEN '' ELSE ', ' END) || COALESCE(D.province_name, '') || '	Zip Code: ' || A.zip_code \n" +
			   "FROM rf_entity_address A \n" +
			   "LEFT JOIN mf_city B ON A.city_id = B.city_id \n" +
			   "LEFT JOIN mf_municipality C ON A.municipality_id = C.municipality_id \n" +
			   "LEFT JOIN mf_province D ON A.province_id = D.province_id \n" +
			   "WHERE A.pref_cts_address = false AND A.entity_id = '"+strID+"'";
	}
	
	public static String sql_Contact(String strID) {
		return "SELECT REPLACE(REPLACE(REPLACE(REPLACE(REPLACE((CASE WHEN char_length(trim(both ' ' from mobile::text)) > 2 THEN trim(both ' ' from mobile::text) ELSE trim(both ' ' from telephone::text) END),'{',''),'}', ''), ',', '/'), '"+""+"', ''), ' ', '') \n" +
			   "FROM rf_contacts \n" +
			   "WHERE entity_id = '"+strID+"' AND status_id = 'A'";
	}
	
	public static String sql_Branch(String strID) {
		return "SELECT branch_code, bank_accttype_desc\n" +
			   "FROM mf_bank_account_type\n" +
			   "WHERE (bank_accttype_id = '"+strID+"' OR '"+strID+"' = '"+strID+"') AND status_id = 'A' AND COALESCE(branch_code, '') <> ''\n" +
			   "ORDER BY bank_accttype_desc";
	}
	
	public static String sqlCo(){
		return "SELECT TRIM(co_id)::VARCHAR as \"ID\", TRIM(company_name) as \"Company Name\", " +
			   "TRIM(company_alias)::VARCHAR as \"Alias\", company_logo as \"Logo\" FROM mf_company order by co_id ";
	}
	
	public static String sqlProject(String strCo){
		return "SELECT proj_id, proj_alias, proj_name\n" +
			   "FROM mf_project\n" +
			   "WHERE (co_id = '"+strCo+"' OR '"+strCo+"' = '"+strCo+"')\n" +
			   "ORDER BY proj_name";
	}
	
	public static String OpenDir(String strType) {
		String strDir = "";
		
		JFileChooser chooser = new JFileChooser(); 
		chooser.setCurrentDirectory(new java.io.File("."));
		chooser.setDialogTitle("Select Folder");
		
		if (strType.equals("Folder")) {
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		}
		else {
			chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		}
		
		chooser.setAcceptAllFileFilterUsed(false);
    
		if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){ 
			System.out.println(chooser.getSelectedFile().toString());
			strDir = chooser.getSelectedFile().toString();
		}
		else{
			System.out.println("No Selection ");
		}
		
		return strDir;
	}
	
	public static String sqlBatch(){
		return "SELECT DISTINCT batch_no, date_created::DATE, status_id FROM rf_rtd_enrolled WHERE status_id = 'A' ORDER BY batch_no";
	}
	
	public static String GetNextBatch(){
		String strBatch = "";
		
		String SQL = "SELECT trim(to_char(max(COALESCE(batch_no::int,0))+1,'000000')) FROM rf_rtd_enrolled";

		pgSelect db = new pgSelect();
		db.select(SQL);
		
		if(db.isNotNull()){
			strBatch = (String)db.getResult()[0][0];
			
			if(strBatch==null){
				strBatch = "000001";
			}
		}
		else{
			strBatch = "000001";
		}
		
		System.out.println("");
		System.out.println("Batch No.: " + strBatch);
		
		return strBatch;
	}
	
	public static String GetName(String emp_code){
		String entityid = "";

		String SQL = "SELECT B.entity_name\n" + 
		"FROM em_employee A\n" + 
		"INNER JOIN rf_entity B ON A.entity_id = B.entity_id\n" + 
		"WHERE a.emp_code = '"+emp_code+"'";
		
		pgSelect sqlExec = new pgSelect();
		sqlExec.select(SQL);

		if(sqlExec.isNotNull()){
			entityid = (String) sqlExec.getResult()[0][0];
		}else{
			entityid = "";
		}

		return entityid;
	}
	
	public static String Details(String strBatch) {
		
		return "SELECT A.c_last_name, A.c_first_name, A.c_middle_name, A.c_mother as Mother, A.c_marital as MaritalStatus, A.c_gender as Gender,\n" +
			"A.c_permaaddress as PermanentAddress, A.c_city as City, A.c_zipcode as ZIPCode, A.c_residence as ResidenceNo, A.c_mobile as MobileNo,\n" +
			"'5342237' as OfficeNo, '5333155' as FaxNo, A.c_email as EmailAddress,\n" +
			"UPPER((CASE WHEN COALESCE((SELECT X.city_name FROM mf_city X WHERE X.city_id = B.birth_place), '') = '' THEN\n" +
			"(CASE WHEN COALESCE((SELECT x.municipality_name FROM mf_municipality X WHERE X.municipality_id = B.birth_place), '') = ''\n" +
			"THEN COALESCE((SELECT X.province_name FROM mf_province X WHERE X.province_id = B.birth_place), '')\n" +
			"ELSE COALESCE((SELECT x.municipality_name FROM mf_municipality X WHERE X.municipality_id = B.birth_place), '')\n" +
			"END) ELSE COALESCE((SELECT X.city_name FROM mf_city X WHERE X.city_id = B.birth_place), '') END)) as c_birthplace,\n" +
			"to_char(A.c_birthdate, 'mm/dd/yyyy') as BirthDate,\n" +
			"(CASE WHEN A.c_nationality = 'Fil' THEN 'Fil' ELSE A.c_nationality END) as Nationality,\n" +
			"'BUYER' as Job, 'CENQHOMES DEVELOPMENT CORPORATION' as EmployerName, A.c_tin as TIN, A.c_sss as SSS_GSIS,\n" +
			"A.c_driver as DriverLicense, A.c_passport as Passport, A.c_entity_id as CompanyID, '254' as Branch\n" +
			"FROM view_rtd_enrollment('', '') A\n" +
			"INNER JOIN rf_entity B ON A.c_last_name = B.last_name AND A.c_first_name = B.first_name AND A.c_middle_name = B.middle_name\n" +
			"INNER JOIN mf_project C ON A.c_project = C.proj_name\n" +
			"INNER JOIN mf_unit_info D ON C.proj_id = D.proj_id AND TRIM(A.c_unit) = TRIM(D.description)\n" +
			"INNER JOIN rf_sold_unit E ON B.entity_id = E.entity_id AND C.proj_id = E.projcode AND D.pbl_id = E.pbl_id\n" +
			"LEFT JOIN rf_rtd_enrolled F ON B.entity_id = F.c_entity_id AND C.proj_id = F.c_proj_id AND D.pbl_id = F.c_pbl_id AND E.seq_no::INT = F.c_seq_no::INT\n" +
			"WHERE (COALESCE(F.batch_no, '') = '"+strBatch+"' OR '"+strBatch+"' = '') \n" +
			"and A.c_last_name || ', ' || A.c_first_name || ' ' || A.c_middle_name in (select * from tmpmbtcenrollees) \n" +
			"ORDER BY A.c_last_name, A.c_first_name, A.c_middle_name";
		
		/*
		return "SELECT \n" + 
		"'METROBANK DIRECT - WACK-WACK' as branch_name, \n" + 
		"'254' as Branch, \n" + 
		"c_account_no, \n" + 
		"'CENQHOMES DEVELOPMENT CORPORATION' as company_name, \n" + 
		"A.c_last_name, \n" + 
		"A.c_first_name, \n" + 
		"A.c_middle_name, \n" + 
		"A.c_presentaddress, \n" + 
		"A.c_permaaddress as PermanentAddress, \n" + 
		"(case when c_mobile = '' or c_mobile is null then c_mobile || ' / 5342237' else '5342237' end) as contact_details, \n" + 
		"UPPER((CASE WHEN COALESCE((SELECT X.city_name FROM mf_city X WHERE X.city_id = B.birth_place), '') = '' THEN\n" + 
		"(CASE WHEN COALESCE((SELECT x.municipality_name FROM mf_municipality X WHERE X.municipality_id = B.birth_place), '') = ''\n" + 
		"THEN COALESCE((SELECT X.province_name FROM mf_province X WHERE X.province_id = B.birth_place), '')\n" + 
		"ELSE COALESCE((SELECT x.municipality_name FROM mf_municipality X WHERE X.municipality_id = B.birth_place), '')\n" + 
		"END) ELSE COALESCE((SELECT X.city_name FROM mf_city X WHERE X.city_id = B.birth_place), '') END)) as c_birthplace,\n" + 
		"to_char(A.c_birthdate, 'mm/dd/yyyy') as BirthDate, \n" + 
		"(CASE WHEN A.c_nationality = 'FIL' THEN 'FIL' ELSE A.c_nationality END) as Nationality, \n" + 
		"(case when a.c_tin = '' or a.c_tin is null then A.c_sss else a.c_tin end) as TIN_SSS_GSIS, \n" + 
		"c_source_of_funds, \n" + 
		"c_nature_of_employment, \n" + 
		"'BUYER' as Job, \n" + 
		"\n" + 
		"A.c_mother as Mother, \n" + 
		"A.c_marital as MaritalStatus, \n" + 
		"A.c_gender as Gender, \n" + 
		"A.c_city as City, \n" + 
		"A.c_zipcode as ZIPCode, \n" + 
		"A.c_residence as ResidenceNo, \n" + 
		"A.c_mobile as MobileNo,\n" + 
		"'5342237' as OfficeNo, \n" + 
		"'5333155' as FaxNo, \n" + 
		"A.c_email as EmailAddress,\n" + 
		"'CENQHOMES DEVELOPMENT CORPORATION' as EmployerName, \n" + 
		"A.c_tin as TIN, \n" + 
		"A.c_sss as SSS_GSIS, \n" + 
		"A.c_driver as DriverLicense, \n" + 
		"A.c_passport as Passport, \n" + 
		"A.c_entity_id as CompanyID, \n" + 
		"'254' as Branch\n" + 
		"FROM view_rtd_enrollment('', '') A\n" + 
		"INNER JOIN rf_entity B ON A.c_last_name = B.last_name AND A.c_first_name = B.first_name AND A.c_middle_name = B.middle_name\n" + 
		"INNER JOIN mf_project C ON A.c_project = C.proj_name\n" + 
		"INNER JOIN mf_unit_info D ON C.proj_id = D.proj_id AND TRIM(A.c_unit) = TRIM(D.description)\n" + 
		"INNER JOIN rf_sold_unit E ON B.entity_id = E.entity_id AND C.proj_id = E.projcode AND D.pbl_id = E.pbl_id\n" + 
		"LEFT JOIN rf_rtd_enrolled F ON B.entity_id = F.c_entity_id AND C.proj_id = F.c_proj_id AND D.pbl_id = F.c_pbl_id AND E.seq_no::INT = F.c_seq_no::INT\n" + 
		"WHERE (COALESCE(F.batch_no, '') = '"+strBatch+"' OR '"+strBatch+"' = '') \n" + 
		"and A.c_last_name || ', ' || A.c_first_name || ' ' || A.c_middle_name in (select * from tmpmbtcenrollees) \n" + 
		"ORDER BY A.c_last_name, A.c_first_name, A.c_middle_name"; 
		*/
	}
	
	public static Boolean rtdForPosting(DefaultTableModel modelMain, JList rowHeader, Boolean blnSuper){
		Boolean blnWith = false;
		
		try {
			/*
			String strSQL = "SELECT TRIM(B.entity_name) AS Name, false, D.proj_name, C.Description, A.d_acct_no,\n" +
					"(SELECT x.c_scheddate FROM view_card_dues(A.entity_id, A.projcode, A.pbl_id, A.seq_no, Now()::TIMESTAMP, FALSE) x LIMIT 1) as DueDate,\n" + 
					"(SELECT SUM(x.c_amount) FROM view_card_dues(A.entity_id, A.projcode, A.pbl_id, A.seq_no, Now()::TIMESTAMP, FALSE) x) as DueAmt,\n" +
					"(SELECT x.c_part_desc FROM view_card_dues(A.entity_id, A.projcode, A.pbl_id, A.seq_no, Now()::TIMESTAMP, FALSE) x ORDER BY x.c_scheddate ASC LIMIT 1) as Particular\n" +
					"FROM rf_rtd_values A\n" +
					"INNER JOIN rf_entity B ON A.entity_id = B.entity_id\n" +
					"INNER JOIN mf_unit_info C ON A.projcode = C.proj_id AND A.pbl_id = C.pbl_id\n" +
					"INNER JOIN mf_project D ON A.projcode = D.proj_id\n" +
					"WHERE A.user_id = '"+UserInfo.EmployeeCode+"'\n" +
					"ORDER BY B.entity_name";
			*/
			String strSQL = "";
			if (blnSuper) {
				strSQL = "SELECT DISTINCT TRIM(B.entity_name) AS Name,D.proj_name, C.Description, '' as SalesGroup,\n" +
						"'DP' as PaymentParticular, 'WACK-WACK' as BankBranch, Now()::Date as DebitDate,\n" +
						/*"(SELECT SUM(x.c_amount) FROM view_card_dues(A.entity_id, A.projcode, A.pbl_id, A.seq_no, Now()::TIMESTAMP, FALSE) x) as DueAmt\n" +*/
						"TRIM(debit_amt::CHAR(10))::DECIMAL as DueAmt, B.entity_name\n" +
						"FROM rf_rtd_values A\n" +
						"INNER JOIN rf_entity B ON A.entity_id = B.entity_id\n" +
						"INNER JOIN mf_unit_info C ON A.projcode = C.proj_id AND A.pbl_id = C.pbl_id\n" +
						"INNER JOIN mf_project D ON A.projcode = D.proj_id\n" +
						"WHERE A.status_id IN ('I', 'O')\n" +
						"ORDER BY B.entity_name";	
			} else {
				strSQL = "SELECT DISTINCT TRIM(B.entity_name) AS Name,D.proj_name, C.Description, '' as SalesGroup,\n" +
						"'DP' as PaymentParticular, 'WACK-WACK' as BankBranch, Now()::Date as DebitDate,\n" +
						/*"(SELECT SUM(x.c_amount) FROM view_card_dues(A.entity_id, A.projcode, A.pbl_id, A.seq_no, Now()::TIMESTAMP, FALSE) x) as DueAmt\n" +*/
						"TRIM(debit_amt::CHAR(10))::DECIMAL as DueAmt, B.entity_name\n" +
						"FROM rf_rtd_values A\n" +
						"INNER JOIN rf_entity B ON A.entity_id = B.entity_id\n" +
						"INNER JOIN mf_unit_info C ON A.projcode = C.proj_id AND A.pbl_id = C.pbl_id\n" +
						"INNER JOIN mf_project D ON A.projcode = D.proj_id\n" +
						"WHERE A.status_id in ('I', 'O')\n" +
						"ORDER BY B.entity_name";
			}
			
			try {
				FncTables.clearTable(modelMain);
			}
			catch (IndexOutOfBoundsException e) {
				System.out.println("Out of bounds exception occured.");
			}
			
			DefaultListModel listModel = new DefaultListModel();
			rowHeader.setModel(listModel);
			
			pgSelect db = new pgSelect();
			db.select(strSQL);
			if (db.isNotNull()) {
				blnWith = true;
				for (int x = 0; x < db.getRowCount(); x++) {
					modelMain.addRow(db.getResult()[x]);
					listModel.addElement(modelMain.getRowCount());
				}
			}
		}
		catch (NullPointerException e) {
			JOptionPane.showMessageDialog(null, "Some parameters may not have been supplied.");
		}
		
		return blnWith;
	}
	
	public static Boolean rtdPosting(DefaultTableModel modelMain, JList rowHeader) {
		Boolean blnWith = false;
		
		try {
			/*
			String strSQL = "SELECT TRIM(B.entity_name) AS Name, false, D.proj_name, C.Description, A.d_acct_no,\n" +
					"(SELECT x.c_scheddate FROM view_card_dues(A.entity_id, A.projcode, A.pbl_id, A.seq_no, Now()::TIMESTAMP, FALSE) x LIMIT 1) as DueDate,\n" + 
					"(SELECT SUM(x.c_amount) FROM view_card_dues(A.entity_id, A.projcode, A.pbl_id, A.seq_no, Now()::TIMESTAMP, FALSE) x) as DueAmt,\n" +
					"(SELECT x.c_part_desc FROM view_card_dues(A.entity_id, A.projcode, A.pbl_id, A.seq_no, Now()::TIMESTAMP, FALSE) x ORDER BY x.c_scheddate ASC LIMIT 1) as Particular\n" +
					"FROM rf_rtd_values A\n" +
					"INNER JOIN rf_entity B ON A.entity_id = B.entity_id\n" +
					"INNER JOIN mf_unit_info C ON A.projcode = C.proj_id AND A.pbl_id = C.pbl_id\n" +
					"INNER JOIN mf_project D ON A.projcode = D.proj_id\n" +
					"WHERE A.user_id = '"+UserInfo.EmployeeCode+"'\n" +
					"ORDER BY B.entity_name";
			*/
			String strSQL = "SELECT DISTINCT TRIM(B.entity_name) AS Name,D.proj_name, C.Description, '' as SalesGroup,\n" +
					"'DP' as PaymentParticular, 'WACK-WACK' as BankBranch, COALESCE(A.debit_date::DATE, Now()::DATE) as DebitDate,\n" +
					/*"(SELECT SUM(x.c_amount) FROM view_card_dues(A.entity_id, A.projcode, A.pbl_id, A.seq_no, Now()::TIMESTAMP, FALSE) x) as DueAmt\n" +*/
					"TRIM(debit_amt::CHAR(10))::DECIMAL as DueAmt, B.entity_name\n" +
					"FROM rf_rtd_values A\n" +
					"INNER JOIN rf_entity B ON A.entity_id = B.entity_id\n" +
					"INNER JOIN mf_unit_info C ON A.projcode = C.proj_id AND A.pbl_id = C.pbl_id\n" +
					"INNER JOIN mf_project D ON A.projcode = D.proj_id\n" +
					"WHERE A.status_id = 'A'\n" +
					"ORDER BY B.entity_name";
			
			try {
				FncTables.clearTable(modelMain);
			}
			catch (IndexOutOfBoundsException e) {
				System.out.println("Out of bounds exception occured.");
			}
			
			DefaultListModel listModel = new DefaultListModel();
			rowHeader.setModel(listModel);
			
			pgSelect db = new pgSelect();
			db.select(strSQL);
			if (db.isNotNull()) {
				blnWith = true;
				for (int x = 0; x < db.getRowCount(); x++) {
					modelMain.addRow(db.getResult()[x]);
					listModel.addElement(modelMain.getRowCount());
				}
			}
		}
		catch (NullPointerException e) {
			JOptionPane.showMessageDialog(null, "Some parameters may not have been supplied.");
		}
		
		return blnWith;
	}
	
	public static String getComputerName() {
		Map<String, String> env = System.getenv();
		if (env.containsKey("COMPUTERNAME")) 
			return env.get("COMPUTERNAME");
		else if (env.containsKey("HOSTNAME"))
			return env.get("HOSTNAME");
		else 
			return "Unknown Computer";
	}
	
	public static String GetAccount(String strBranch){
		String Acct = "";

		String SQL = "SELECT TRIM(COALESCE(branch_code, '')) || debit_account_no \n" +
					 "FROM mf_bank_account_type \n" +
					 "WHERE co_id = '"+strBranch+"'";
		
		pgSelect sqlExec = new pgSelect();
		sqlExec.select(SQL);

		if(sqlExec.isNotNull()){
			Acct = (String) sqlExec.getResult()[0][0];
		}else{
			Acct = "";
		}

		return Acct;
	}
	
	public static String White(Integer i) {
		String strSpace = "";
		
		while (i > 0) {
			strSpace = strSpace + " ";
			i--;
		}
		
		return strSpace;
	}
	
	public static void peruser_alpha(String strDir, DefaultTableModel modelMain, JList rowHeader) {
		String strRAM = "";
		String strName = "";
		String strID = "";
		String strUnitID = "";
		String strReason = "";
		
		pgUpdate dbDelete = new pgUpdate();
		dbDelete.executeUpdate("delete from tmp_rtd_unposted", false);
		dbDelete.commit();
		
    	File peruse = null;
    	
    	System.out.println("");
    	System.out.println("Reading");
    	
    	try {
    		peruse = new File(strDir);
    	}
    	catch (NullPointerException e) {
    		JOptionPane.showMessageDialog(null, "No directory was selected.");
    	}
    	
    	Scanner scan = null;
    	
		try {
			scan = new Scanner(peruse);
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
    	
    	while (scan.hasNextLine()) {
			String strReader = scan.nextLine();
			
			System.out.println("");
			System.out.println("Line String: " + strReader);
			System.out.println("Name: " + strReader.substring(41, 56));
			System.out.println("ID: " + strReader.substring(56, 66));
			System.out.println("Unit ID: " + strReader.substring(56, 66));
			System.out.println("Reason: " + strReader.substring(84, strReader.trim().length()));
			
			strName = strReader.substring(41, 56);
			strID = strReader.substring(56, 66);
			strUnitID = strReader.substring(66, 71);
			strReason = strReader.substring(84, strReader.trim().length());
			
			String strSQL = "";
			strSQL = "INSERT INTO tmp_rtd_unposted (entity_name, entity_id, pbl_id, reason) \n" +
					"VALUES ('"+strName+"', '"+strID+"', '"+strUnitID+"'::INT::VARCHAR(5), '"+strReason+"')";
			
			pgUpdate dbExec = new pgUpdate();
			dbExec.executeUpdate(strSQL, false);
			dbExec.commit();
    	}
    	scan.close();
    }
	
	public static void sendFromGMail(final String from, final String pass, String[] to, String subject, String body, String file, String fileName) {
        Properties props = System.getProperties();
        String host = "smtp.gmail.com";
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", pass);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        //Session session = Session.getDefaultInstance(props);
        
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, pass);
            }
        });
        
        MimeMessage message = new MimeMessage(session);
        
        try {
            message.setFrom(new InternetAddress(from));
            InternetAddress[] toAddress = new InternetAddress[to.length];

            // To get the array of addresses
            for( int i = 0; i < to.length; i++ ) {
                toAddress[i] = new InternetAddress(to[i]);
            }

            for( int i = 0; i < toAddress.length; i++) {
                message.addRecipient(Message.RecipientType.TO, toAddress[i]);
            }

            message.setSubject(subject);
            message.setText(body);
            
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            Multipart multipart = new MimeMultipart();
            messageBodyPart = new MimeBodyPart();
            
            System.out.println("");
            System.out.println("file: " + file);
            System.out.println("fileName: " + fileName);

            DataSource source = new FileDataSource(file);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(fileName);
            multipart.addBodyPart(messageBodyPart);
            
            message.setContent(multipart);
            
            Transport transport = session.getTransport("smtp");
            transport.connect(host, from, pass);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        }
        catch (AddressException ae) {
            ae.printStackTrace();
        }
        catch (MessagingException me) {
            me.printStackTrace();
        }
    }
	
	public static Boolean sendFromMail(final String from, final String pass, String to, String subject, StringBuffer sb, String file, String fileName) {
		Boolean blnSent = true;
        Properties props = System.getProperties();
        String host = "smtp.mail.yahoo.com";
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", pass);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.imap.ssl.enable", "true");
        
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
        	protected PasswordAuthentication getPasswordAuthentication() {
        		return new PasswordAuthentication(from, pass);
        	}
        });
        
        MimeMessage message = new MimeMessage(session);
        
        try {
            message.setFrom(new InternetAddress(from));
            InternetAddress toAddress = new InternetAddress();
            toAddress = new InternetAddress(to);
            
            System.out.println("");
            System.out.println(sb.toString());
            
            message.addRecipient(Message.RecipientType.TO, toAddress);
            message.setSubject(subject);
            /**	message.setText(sb.toString());	**/
            
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            Multipart multipart = new MimeMultipart();
            messageBodyPart = new MimeBodyPart();

            BodyPart textPart = new MimeBodyPart();
            textPart.setText(sb.toString());
            
            DataSource source = new FileDataSource(file);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(fileName);
            multipart.addBodyPart(textPart);
            multipart.addBodyPart(messageBodyPart);
            
            message.setContent(multipart);
            
            Transport transport = session.getTransport("smtp");
            
            System.out.println("");
            System.out.println("file: " + file);
            System.out.println("fileName: " + fileName);
            System.out.println("body: " + sb.toString());
            
            try {
            	transport.connect(host, from, pass);
                transport.sendMessage(message, message.getAllRecipients());
            } catch (MessagingException ex) {
            	blnSent = false;
            	JOptionPane.showMessageDialog(null, "Could not connect to mail.yahoo.com.");
            }
            
            /***	***/
            Store store = session.getStore("imap");
            store.connect("imap.mail.yahoo.com", from, pass);
            Folder folder = (Folder) store.getFolder("Sent");
            
            if (!folder.exists()) {
                folder.create(Folder.HOLDS_MESSAGES);
            }
            
            folder.open(Folder.READ_WRITE);
            System.out.println("appending...");
            
            try {
                folder.appendMessages(new Message[] {
                		message
                });
                message.setFlag(FLAGS.Flag.RECENT, true);
            } catch (Exception ignore) {
                System.out.println("error processing message " + ignore.getMessage());
            } finally {
                store.close();
            }
            /***	***/
            
            transport.close();
        }
        catch (AddressException ae) {
            ae.printStackTrace();
        }
        catch (MessagingException me) {
            me.printStackTrace();
        }
        
        return blnSent;
    }
	
	public static void rtdDisplayLoanReleased(DefaultTableModel modelMain, JList rowHeader){
		try {
			String strSQL = "SELECT DISTINCT TRIM(B.entity_name) AS Name,D.proj_name, C.Description, '' as SalesGroup,\n" +
					"'MA' as PaymentParticular, 'WACK-WACK' as BankBranch, Now()::Date as DebitDate,\n" +
					"TRIM(debit_amt::CHAR(10))::DECIMAL as DueAmt, B.entity_name\n" +
					"FROM rf_rtd_values_loanreleased A\n" +
					"INNER JOIN rf_entity B ON A.entity_id = B.entity_id\n" +
					"INNER JOIN mf_unit_info C ON A.projcode = C.proj_id AND A.pbl_id = C.pbl_id\n" +
					"INNER JOIN mf_project D ON A.projcode = D.proj_id\n" +
					"WHERE A.user_id = '"+UserInfo.EmployeeCode+"' AND A.status_id = 'I'\n" +
					"ORDER BY B.entity_name";
			
			try {
				FncTables.clearTable(modelMain);
			}
			catch (IndexOutOfBoundsException e) {
				System.out.println("Out of bounds exception occured.");
			}
			
			DefaultListModel listModel = new DefaultListModel();
			rowHeader.setModel(listModel);
			
			pgSelect db = new pgSelect();
			db.select(strSQL);
			if (db.isNotNull()) {
				for (int x = 0; x < db.getRowCount(); x++) {
					modelMain.addRow(db.getResult()[x]);
					listModel.addElement(modelMain.getRowCount());
				}
			}
			else {
				JOptionPane.showMessageDialog(null, "No records were returned for posting.");
			};
		}
		catch (NullPointerException e) {
			JOptionPane.showMessageDialog(null, "Some parameters may not have been supplied.");
		}
	}
	
	public static void InsertLoanReleased(String strID, String strProject, String strUnit, String strSequence, String strBranchC,	String strAcctC, String strBranchD,	String strAcctD, String strSubs, String strDebit, String strReason,	String strUser, String strFile) {
		pgUpdate db_Upd = new pgUpdate();
		
		String SQL = "INSERT INTO rf_rtd_values_loanreleased \n" +
		"(entity_id, projcode, pbl_id, seq_no, c_branch_code, c_acct_no, d_branch_code, d_acct_no, subscriber_no, debit_amt, reason, user_id, status_id)\n" +
		"VALUES\n" +
		"('"+strID+"', '"+strProject+"', '"+strUnit+"', "+strSequence+", '"+strBranchC+"', '"+strAcctC+"', \n"+
		"'"+strBranchD+"', '"+strAcctD+"', '"+strSubs+"', '"+strDebit+"', '"+strReason+"', '"+strUser+"', 'I')";
		
		db_Upd.executeUpdate(SQL, false);
		db_Upd.commit();
	}
	
	public static String peruserLoanReleased(String strDir) {
		String strRAM = "";
		String strName = "";
		
		String strID = "";
		String strProject = "";
		String strUnit = "";
		String strSequence = "";
		String strBranchC = "";
		String strAcctC = "";
		String strBranchD = "";
		String strAcctD = "";
		String strSubs = "";
		String strDebit = "";
		String strReason = "";
		
    	File peruse = null;
    	String strStatus = "";
    	
    	System.out.println("Reading");
   
    	
    	try {
    		peruse = new File(strDir);
    	}
    	catch (NullPointerException e) {
    		JOptionPane.showMessageDialog(null, "No directory was selected.");
    	}
    	
    	Scanner scan = null;
    	
		try {
			scan = new Scanner(peruse);
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
    	
    	while (scan.hasNextLine()) {
			String strReader = scan.nextLine();
			
			if (strReader.substring(strReader.length() - (Integer)20).trim().equals("")) {
				System.out.println("Last 20 Characters: " + strReader.substring(strReader.length() - (Integer)20));
				strStatus = "Posted";
				RealTimeDebitUpload_LoanReleased.btnUpload.setEnabled(true);
				RealTimeDebitUpload_LoanReleased.btnSave.setEnabled(true);
			}
			else {
				System.out.println("Last 20 Characters: " + strReader.substring(strReader.length() - (Integer)20));
				strStatus = "---";
				RealTimeDebitUpload_LoanReleased.btnUpload.setEnabled(false);
				RealTimeDebitUpload_LoanReleased.btnSave.setEnabled(false);
			}
			
			System.out.println("");
			System.out.println("Reason: " + "'" + strReader.substring(strReader.length() - 20).trim() + "'");
			
			strReason = strReader.substring(strReader.length() - 20).trim();
			
			strBranchC = strReader.substring(0, strReader.trim().length() - (strReader.trim().length() - 3));
			strRAM = strReader.substring(3);
			
			strAcctC = strRAM.substring(0, strRAM.length() - (strRAM.length() - 10));
			strRAM = strRAM.substring(10);
			
			strBranchD  = strRAM.substring(0, strRAM.length() - (strRAM.length() - 3));
			strRAM = strRAM.substring(3);
			
			strAcctD = strRAM.substring(0, strRAM.length() - (strRAM.length() - 10));
			strRAM = strRAM.substring(10);
			
			strSubs = strRAM.substring(0, strRAM.length() - (strRAM.length() - 15));
			strRAM = strRAM.substring(15);
	
			strName = strRAM.substring(0, strRAM.length() - (strRAM.length() - 15));
			strRAM = strRAM.substring(15);
			
			strID = strRAM.substring(0, strRAM.length() - (strRAM.length() - 15));
			strRAM = strRAM.substring(15);
			
			strDebit = strRAM.substring(0, strRAM.length() - (strRAM.length() - 13));
			strRAM = strRAM.substring(13);
	
			strReason = strRAM.substring(0, strRAM.length());
			
			RealTimeDebitUpload_LoanReleased.txtReason.setText(strReason);
			
			System.out.println("");
			System.out.println("Branch Code: " + strBranchC);
			System.out.println("Credit Account Number: " + strAcctC);
			System.out.println("Debit Account Number: " + strAcctD);
			System.out.println("Subscriber Number: " + strSubs);
			System.out.println("Customer Store: " + strName);
			System.out.println("Customer Code: " + strID);
			System.out.println("Debit Amount: " + strDebit);
			System.out.println("Filler: " + strReason);

			strUnit = _RealTimeDebit.RemovePad(strID.substring(10));
			strID = strID.substring(0, 10);
			strProject = _RealTimeDebit.GetValue("SELECT projcode FROM rf_sold_unit WHERE entity_id = '"+strID+"' and pbl_id = '"+strUnit+"' and status_id = 'A'");
			strSequence = _RealTimeDebit.GetValue("SELECT seq_no::CHAR(1) FROM rf_sold_unit WHERE entity_id = '"+strID+"' and pbl_id = '"+strUnit+"' and status_id = 'A'");
			strDebit = _RealTimeDebit.GetAmount(strDebit);
			
			System.out.println("");
			System.out.println("Client ID: " + strID);
			System.out.println("Project ID: " + strProject);
			System.out.println("Unit ID: " + strUnit);
			System.out.println("Sequence No.: " + strSequence);
			System.out.println("Debit Amount: " + _RealTimeDebit.GetAmount(strDebit));
			
			InsertLoanReleased(strID, strProject, strUnit, strSequence, strBranchC,	strAcctC, strBranchD, strAcctD, strSubs, strDebit, strReason, UserInfo.EmployeeCode, "");

			if (strStatus.equals("Posted")) {
				//CreateOP(strID, strProject, strUnit, strSequence, strDebit);
			}
    	}
    	scan.close();
    	
    	return strStatus;
    }
	
	public static Boolean rtdForPostingLoanReleased(DefaultTableModel modelMain, JList rowHeader, Boolean blnSuper){
		Boolean blnWith = false;
		
		try {
			String strSQL = "";
			if (blnSuper) {
				strSQL = "SELECT DISTINCT TRIM(B.entity_name) AS Name,D.proj_name, C.Description, '' as SalesGroup,\n" +
						"'DP' as PaymentParticular, 'WACK-WACK' as BankBranch, Now()::Date as DebitDate,\n" +
						"TRIM(debit_amt::CHAR(10))::DECIMAL as DueAmt, B.entity_name\n" +
						"FROM rf_rtd_values_loanreleased A\n" +
						"INNER JOIN rf_entity B ON A.entity_id = B.entity_id\n" +
						"INNER JOIN mf_unit_info C ON A.projcode = C.proj_id AND A.pbl_id = C.pbl_id\n" +
						"INNER JOIN mf_project D ON A.projcode = D.proj_id\n" +
						"WHERE A.status_id IN ('I', 'O')\n" +
						"ORDER BY B.entity_name";	
			} else {
				strSQL = "SELECT DISTINCT TRIM(B.entity_name) AS Name,D.proj_name, C.Description, '' as SalesGroup,\n" +
						"'DP' as PaymentParticular, 'WACK-WACK' as BankBranch, Now()::Date as DebitDate,\n" +
						"TRIM(debit_amt::CHAR(10))::DECIMAL as DueAmt, B.entity_name\n" +
						"FROM rf_rtd_values_loanreleased A\n" +
						"INNER JOIN rf_entity B ON A.entity_id = B.entity_id\n" +
						"INNER JOIN mf_unit_info C ON A.projcode = C.proj_id AND A.pbl_id = C.pbl_id\n" +
						"INNER JOIN mf_project D ON A.projcode = D.proj_id\n" +
						"WHERE A.status_id in ('I', 'O')\n" +
						"ORDER BY B.entity_name";
			}
			
			try {
				FncTables.clearTable(modelMain);
			}
			catch (IndexOutOfBoundsException e) {
				System.out.println("Out of bounds exception occured.");
			}
			
			DefaultListModel listModel = new DefaultListModel();
			rowHeader.setModel(listModel);
			
			pgSelect db = new pgSelect();
			db.select(strSQL);
			if (db.isNotNull()) {
				blnWith = true;
				for (int x = 0; x < db.getRowCount(); x++) {
					modelMain.addRow(db.getResult()[x]);
					listModel.addElement(modelMain.getRowCount());
				}
			}
		}
		catch (NullPointerException e) {
			JOptionPane.showMessageDialog(null, "Some parameters may not have been supplied.");
		}
		
		return blnWith;
	}
	
	public static Boolean rtdPostingLoanReleased(DefaultTableModel modelMain, JList rowHeader) {
		Boolean blnWith = false;
		
		try {
			String strSQL = "SELECT DISTINCT TRIM(B.entity_name) AS Name,D.proj_name, C.Description, '' as SalesGroup,\n" +
					"'MA' as PaymentParticular, 'WACK-WACK' as BankBranch, COALESCE(A.debit_date::DATE, Now()::DATE) as DebitDate,\n" +
					"TRIM(debit_amt::CHAR(10))::DECIMAL as DueAmt, B.entity_name\n" +
					"FROM rf_rtd_values_loanreleased A\n" +
					"INNER JOIN rf_entity B ON A.entity_id = B.entity_id\n" +
					"INNER JOIN mf_unit_info C ON A.projcode = C.proj_id AND A.pbl_id = C.pbl_id\n" +
					"INNER JOIN mf_project D ON A.projcode = D.proj_id\n" +
					"WHERE A.status_id = 'A'\n" +
					"ORDER BY B.entity_name";
			
			try {
				FncTables.clearTable(modelMain);
			}
			catch (IndexOutOfBoundsException e) {
				System.out.println("Out of bounds exception occured.");
			}
			
			DefaultListModel listModel = new DefaultListModel();
			rowHeader.setModel(listModel);
			
			pgSelect db = new pgSelect();
			db.select(strSQL);
			if (db.isNotNull()) {
				blnWith = true;
				for (int x = 0; x < db.getRowCount(); x++) {
					modelMain.addRow(db.getResult()[x]);
					listModel.addElement(modelMain.getRowCount());
				}
			}
		}
		catch (NullPointerException e) {
			JOptionPane.showMessageDialog(null, "Some parameters may not have been supplied.");
		}
		
		return blnWith;
	}
	
	public static void Delete_LoanReleased() {
		pgUpdate db_Del = new pgUpdate();
		String SQL_Rev = "UPDATE rf_rtd_values_loanreleased SET status_id = 'P' WHERE status_id = 'A'";
		db_Del.executeUpdate(SQL_Rev, false);
		db_Del.commit();	
	}
	
	public static String CreateOPLoanReleased(String strName, String Unit) {
		String strOPNo = "";
		ArrayList<String> listPartID = new ArrayList<String>();
		String strID = "";
		String strProject = "";
		String strUnitID = "";
		String strSequence = "";
		String strDebit = "";
		
		strID = GetValue("SELECT entity_id FROM rf_entity WHERE entity_name = '"+strName+"'");
		strUnitID = GetValue("SELECT a.pbl_id FROM rf_sold_unit A \n" +
				"INNER JOIN mf_unit_info B ON a.projcode = b.proj_id and a.pbl_id = b.pbl_id \n" +
				"INNER JOIN rf_entity C ON a.entity_id = c.entity_id \n" +
				"WHERE c.entity_name = '"+strName+"' and b.description = '"+Unit+"'");
		strProject = GetValue("SELECT projcode FROM rf_sold_unit WHERE entity_id = '"+strID+"' and pbl_id = '"+strUnitID+"' and status_id = 'A'");
		strSequence = GetValue("SELECT seq_no::CHAR(1) FROM rf_sold_unit WHERE entity_id = '"+strID+"' and pbl_id = '"+strUnitID+"' and status_id = 'A'");
		/*	Modified by Mann2x; Date Modified: March 29, 2017; Causes the wrong amount being posted in the OP;	*/
		strDebit = GetValue("SELECT TRIM(debit_amt::CHAR(10)) FROM rf_rtd_values_loanreleased WHERE entity_id = '"+strID+"' and projcode = '"+strProject+"' and pbl_id = '"+strUnitID+"' and seq_no = "+strSequence+" order by debit_date::date desc limit 1;");
		
		String strPart = GetValue("SELECT Y.pay_part_id::CHAR(3)\n" + 
				"FROM view_card_dues('"+strID+"', '"+strProject+"', '"+strUnitID+"', "+strSequence+", Now()::TIMESTAMP, FALSE) X\n" +
				"INNER JOIN mf_client_ledger_part Y ON Y.part_id = X.c_part_id\n" +
				"ORDER BY c_scheddate ASC LIMIT 1");
		
		System.out.println(strPart);
		listPartID.add(String.format("'%s'", strPart));
		String part_id = listPartID.toString().replaceAll("\\[|\\]", "");
		
		String type = "null";
		String check_type = "null";
		String check_no = "null";
		String check_date = "null";
		String account_no = "null";
		String bank_id = "null";
		String bank_branch_id = "null";
		String receipt_no = "null";
		String brstn = "null";
		String credit = "null";
		
		String strOP = "SELECT * from sp_op_post_new(" +
				"'"+ UserInfo.Branch +"', " +
				"'"+ strID +"', " +
				"'"+ strProject +"', " +
				"'"+ strUnitID +"', " +
				""+ strSequence +", " + 
				""+ strDebit +",\n" +
				"  ARRAY["+ part_id +"]::VARCHAR[],\n" + 
				"  ARRAY["+ strDebit +"]::NUMERIC[],\n" + 
				"  ARRAY["+ type +"]::VARCHAR[],\n" + 
				"  ARRAY["+ (check_type.equals("'null'") ? "null":check_type) +"]::VARCHAR[],\n" + 
				"  ARRAY["+ (check_no.equals("'null'") ? "null":check_no) +"]::VARCHAR[],\n" + 
				"  ARRAY["+ (check_date.equals("'null'") ? "null":check_date) +"]::TIMESTAMP[],\n" + 
				"  ARRAY["+ (account_no.equals("'null'") ? "null":account_no) +"]::VARCHAR[],\n" + 
				"  ARRAY["+ (bank_id.equals("'null'") ? "null":bank_id) +"]::VARCHAR[],\n" + 
				"  ARRAY["+ (bank_branch_id.equals("'null'") ? "null":bank_branch_id) +"]::VARCHAR[],\n" + 
				"  ARRAY["+ (receipt_no.equals("'null'") ? "null":receipt_no) +"]::VARCHAR[],\n" + 
				"  NULL,\n" + 
				"  ARRAY["+ (brstn.equals("'null'") ? "null":brstn) +"]::VARCHAR[],\n" + 
				"  ARRAY["+ credit +"]::BOOLEAN[], '"+ UserInfo.EmployeeCode +"');";
		
		FncSystem.out("SQL: ", strOP);
		pgSelect db = new pgSelect();
		db.select(strOP);
		
		if (db.isNotNull()) {
			strOPNo = (String)db.getResult()[0][1];
			System.out.print("Client sequence number: " + (String)db.getResult()[0][1]);
		} else {
			strOPNo = "";
			System.out.print("OP was not saved!");
		}
		
		return strOPNo;
	}
}