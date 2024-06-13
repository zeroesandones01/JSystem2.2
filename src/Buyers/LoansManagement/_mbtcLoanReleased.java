package Buyers.LoansManagement;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.sun.mail.imap.protocol.FLAGS;

import Buyers.CreditandCollections._RealTimeDebit;
import Database.pgSelect;
import Database.pgUpdate;
import Functions.FncGlobal;
import Functions.FncTables;

public class _mbtcLoanReleased {

	static Boolean mbtcDisplay(DefaultTableModel modelMain, JList rowHeader, String strCoID, String strProID, String strBatch, String strFrom, String strTo) {
		Boolean blnGen = false;
		String strSQL = "";

		FncTables.clearTable(modelMain);
		DefaultListModel listModel = new DefaultListModel();
		rowHeader.setModel(listModel); 
		
		FncGlobal.startProgress("Displaying list...");
		strSQL = "select * from view_loan_released_mbtc_account('"+strCoID+"', '"+strProID+"', '"+strBatch+"', '"+strFrom+"', '"+strTo+"'); ";
		System.out.println(strSQL);
		
		pgSelect db = new pgSelect();
		db.select(strSQL);
		if (db.isNotNull()) {
			for (int x = 0; x < db.getRowCount(); x++) {
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());
			}
			
			blnGen = true;
		} else {
			JOptionPane.showMessageDialog(null, "No records were returned for batch creation.");
		};
		
		try {
			
		} catch (NullPointerException e) {
			JOptionPane.showMessageDialog(null, "Some parameters may not have been supplied. Check the dates.");
		}
		
		System.out.println("");
		System.out.println("RTD List");
		System.out.println(strSQL);
		
		FncGlobal.stopProgress();
		return blnGen;
	}
	
	static String sqlBatch() {
		return "SELECT DISTINCT batch_no, date_created::DATE, status_id FROM rf_loan_released_mbtc_account WHERE status_id = 'A' ORDER BY batch_no";
	}
	
	static String GetNextBatch(){
		String strBatch = "";
		String SQL = "SELECT trim(to_char(max(COALESCE(batch_no::int,0))+1,'000000')) FROM rf_loan_released_mbtc_account";

		pgSelect db = new pgSelect();
		db.select(SQL);
		
		if (db.isNotNull()) {
			strBatch = (String)db.getResult()[0][0];
			if (strBatch==null) {
				strBatch = "000001";
			}
		} else {
			strBatch = "000001";
		}
		return strBatch;
	}
	
	static String CreateBatch(DefaultTableModel modelMain, JList rowHeader, Integer intAll) {
		String strBatch = "";
		String strID = "";
		String strProjectID = "";
		String strUnitID = "";
		String strSequence = "";
		
		FncGlobal.startProgress("Creating batch...");
		strBatch = GetNextBatch();
		
		for (int i = 0; i < modelMain.getRowCount(); i++) {
			if ((Boolean) modelMain.getValueAt(i, 1) || intAll==1) {
				String strName = (String) modelMain.getValueAt(i, 0);
				String strProject = (String) modelMain.getValueAt(i, 2);
				String strUnit = (String) modelMain.getValueAt(i, 3);
				
				strID = FncGlobal.GetString("SELECT entity_id FROM rf_entity WHERE TRIM(last_name) || ', ' || TRIM(first_name) || ' ' || TRIM(middle_name) = '"+strName+"' AND status_id = 'A'");
				strProjectID = FncGlobal.GetString("SELECT proj_id FROM mf_project WHERE proj_alias = '"+strProject+"' AND status_id = 'A'");
				strUnitID = FncGlobal.GetString("SELECT pbl_id FROM mf_unit_info WHERE proj_id = '"+strProjectID+"' AND TRIM(DESCRIPTION) = '"+strUnit+"'");
				strSequence = FncGlobal.GetString("SELECT TRIM(seq_no::VARCHAR(2)) FROM rf_sold_unit WHERE entity_id = '"+strID+"' and projcode = '"+strProjectID+"' and pbl_id = '"+strUnitID+"' and status_id = 'A'");
				
				System.out.println(""); 
				System.out.println("ID: " + strID); 
				System.out.println("Name: " + strName); 
				System.out.println("Project: " + strProjectID + " - " + strProject); 
				System.out.println("Unit: " + strUnitID + " - " + strUnit); 
				System.out.println("Sequence: " + strSequence); 
				
				String SQL = "INSERT INTO rf_loan_released_mbtc_account (entity_id, proj_id, pbl_id, seq_no, status_id, batch_no, date_created, tobeprinted)\n" +
							 "VALUES ('"+strID+"', '"+strProjectID+"', '"+strUnitID+"', '"+strSequence+"', 'A', '"+strBatch+"', Now()::TIMESTAMP, 0::BIT);";
				
				pgUpdate db = new pgUpdate();
				db.executeUpdate(SQL, false);
				db.commit();	
			}
		}
		
		FncGlobal.stopProgress();
		mbtcDisplay(modelMain, rowHeader, "", "", strBatch, "", "");
		JOptionPane.showMessageDialog(null, "Batch created!");
		return strBatch;
	}
	
	static void CreateXLS(String strCoID, String strProID, String strBatch) {
	    Workbook wb = new HSSFWorkbook();
	    CreationHelper createHelper = wb.getCreationHelper();
	    Sheet sheet = wb.createSheet("new sheet");
	    HSSFFont hSSFFont1 = (HSSFFont) wb.createFont();
	    hSSFFont1.setFontName("Calibri");
	    hSSFFont1.setBoldweight(hSSFFont1.BOLDWEIGHT_BOLD);
	    HSSFFont hSSFFont2 = (HSSFFont) wb.createFont();
	    hSSFFont2.setFontName("Calibri");
	    
	    HSSFCellStyle styleVertCenter = (HSSFCellStyle) wb.createCellStyle();
	    styleVertCenter.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
	    styleVertCenter.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	    styleVertCenter.setBorderLeft(HSSFCellStyle.BORDER_THIN);
	    styleVertCenter.setBorderRight(HSSFCellStyle.BORDER_THIN);
	    styleVertCenter.setBorderTop(HSSFCellStyle.BORDER_THIN);
	    styleVertCenter.setBorderBottom(HSSFCellStyle.BORDER_THIN);
	    styleVertCenter.setFillForegroundColor(IndexedColors.AQUA.getIndex());
	    styleVertCenter.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	    
	    HSSFCellStyle styleVertCenter_1 = (HSSFCellStyle) wb.createCellStyle();
	    styleVertCenter_1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
	    styleVertCenter_1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	    styleVertCenter_1.setBorderLeft(HSSFCellStyle.BORDER_THIN);
	    styleVertCenter_1.setBorderRight(HSSFCellStyle.BORDER_THIN);
	    styleVertCenter_1.setBorderTop(HSSFCellStyle.BORDER_THIN);
	    styleVertCenter_1.setBorderBottom(HSSFCellStyle.BORDER_THIN);
	    styleVertCenter_1.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
	    styleVertCenter_1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	    
	    HSSFCellStyle styleHoriCenter = (HSSFCellStyle) wb.createCellStyle();
	    styleHoriCenter.setAlignment(HSSFCellStyle.VERTICAL_CENTER);
	    
	    HSSFCellStyle styleVertTopBordered = (HSSFCellStyle) wb.createCellStyle();
	    styleVertTopBordered.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);
	    styleVertTopBordered.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	    styleVertTopBordered.setBorderLeft(HSSFCellStyle.BORDER_THIN);
	    styleVertTopBordered.setBorderRight(HSSFCellStyle.BORDER_THIN);
	    styleVertTopBordered.setBorderTop(HSSFCellStyle.BORDER_THIN);
	    styleVertTopBordered.setBorderBottom(HSSFCellStyle.BORDER_THIN);
	    styleVertTopBordered.setFont(hSSFFont1);
	    //styleVertTopBordered.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	    
	    HSSFCellStyle styleVertCenterBordered = (HSSFCellStyle) wb.createCellStyle();
	    styleVertCenterBordered.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
	    styleVertCenterBordered.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	    styleVertCenterBordered.setBorderLeft(HSSFCellStyle.BORDER_THIN);
	    styleVertCenterBordered.setBorderRight(HSSFCellStyle.BORDER_THIN);
	    styleVertCenterBordered.setBorderTop(HSSFCellStyle.BORDER_THIN);
	    styleVertCenterBordered.setBorderBottom(HSSFCellStyle.BORDER_THIN);
	    styleVertCenterBordered.setFont(hSSFFont2);
	    //styleVertCenterBordered.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	    
	    String strName = "";
	    String strNameCo = "";
	    String strNameDate = "";
	    String strNameSequence = "";
	    
	    if (strCoID.equals("")) {
	    	JOptionPane.showMessageDialog(null, "The company field is not set\nand thus will not be included in the filename.");
	    } else {
	    	strNameCo =  FncGlobal.GetString("SELECT company_alias FROM mf_company WHERE co_id = '"+strCoID+"'");
	    }

		SimpleDateFormat sdfTo = new SimpleDateFormat("MMddyy");
		Date dateObj = new Date();
		strNameDate = (String)sdfTo.format(dateObj);

		String strDir = FncGlobal.OpenDir("Folder"); 
		Integer intSeq = 1;
		strNameSequence = _RealTimeDebit.Padme(intSeq.toString(), 2);
		File f = new File(strDir + "/" + strNameCo + strNameDate + strNameSequence + ".xls");

		System.out.println("");
		System.out.println("File Name: " + strNameCo + strNameDate + strNameSequence + ".xls");

		while (f.exists() && f.isFile()) {
			System.out.println("");
			System.out.println("File exists! Number: " + strNameSequence);
			
			intSeq++;
			strNameSequence = _RealTimeDebit.Padme(intSeq.toString(), 2);
			f = new File(strDir + "/" + strNameCo + strNameDate + strNameSequence + ".xls");
			System.out.println("New Name: " + strNameCo + strNameDate + strNameSequence + ".xls");
		}

		strName = strNameCo + strNameDate + strNameSequence + ".xls";
		
		pgSelect sqlExec = new pgSelect();
		sqlExec.select("select c_last_name, c_first_name, c_middle_name, coalesce(c_mother, '') as c_mother, coalesce(c_marital, '') as c_marital, coalesce(c_gender, '') as c_gender, \r\n" + 
				"coalesce(c_permaaddress, '') as c_permaaddress, coalesce(c_city, '') as c_city, coalesce(c_zipcode, '') as c_zipcode, coalesce(c_residence, '') as c_residence, \r\n" + 
				"coalesce(c_mobile, '') as c_mobile, '5342237' as OfficeNo, '5333155' as FaxNo, replace(coalesce(c_email, ''), '\"', '') as EmailAddress, coalesce(c_birthplace, '') as c_birthplace, \r\n" + 
				"to_char(c_birthdate, 'mm/dd/yyyy') as birthdate, c_nationality, 'BUYER' as job, 'CENQHOMES DEVELOPMENT CORPORATION' as employername, coalesce(c_tin, '') as c_tin, \r\n" + 
				"coalesce(c_sss, '') as c_sss, coalesce(c_driver, '') as c_driver, coalesce(c_passport, '') as c_passport, coalesce(c_entity_id, '') as c_entity_id, '254' as branch \n" + 
				"from view_loan_released_mbtc_account_report('', '', '"+strBatch+"')");
		
		System.out.println("");
		System.out.println("select c_last_name, c_first_name, c_middle_name, coalesce(c_mother, '') as c_mother, coalesce(c_marital, '') as c_marital, coalesce(c_gender, '') as c_gender, \r\n" + 
				"coalesce(c_permaaddress, '') as c_permaaddress, coalesce(c_city, '') as c_city, coalesce(c_zipcode, '') as c_zipcode, coalesce(c_residence, '') as c_residence, \r\n" + 
				"coalesce(c_mobile, '') as c_mobile, '5342237' as OfficeNo, '5333155' as FaxNo, replace(coalesce(c_email, ''), '\"', '') as EmailAddress, coalesce(c_birthplace, '') as c_birthplace, \r\n" + 
				"to_char(c_birthdate, 'mm/dd/yyyy') as birthdate, c_nationality, 'BUYER' as job, 'CENQHOMES DEVELOPMENT CORPORATION' as employername, coalesce(c_tin, '') as c_tin, \r\n" + 
				"coalesce(c_sss, '') as c_sss, coalesce(c_driver, '') as c_driver, coalesce(c_passport, '') as c_passport, coalesce(c_entity_id, '') as c_entity_id, '254' as branch \n" + 
				"from view_loan_released_mbtc_account_report('', '', '"+strBatch+"')");
		
	    Row row = sheet.createRow((short)0);
	    row.setHeightInPoints(3 * sheet.getDefaultRowHeightInPoints());
	    
	    /*	This is the Original Style	*/
	    row.createCell(0).setCellValue("Last Name");
	    row.createCell(1).setCellValue("First Name");
	    row.createCell(2).setCellValue("Middle Name");
	    row.createCell(3).setCellValue("Mother's Maiden Name");
	    row.createCell(4).setCellValue("Marital Status");
	    row.createCell(5).setCellValue("Gender");
	    row.createCell(6).setCellValue("Permanent Address");
	    row.createCell(7).setCellValue("City");
	    row.createCell(8).setCellValue("ZIP Code");
	    row.createCell(9).setCellValue("Residence No.");
	    row.createCell(10).setCellValue("Mobile No.");
	    row.createCell(11).setCellValue("Office No.");
	    row.createCell(12).setCellValue("Fax No.");
	    row.createCell(13).setCellValue("Email Address");
	    row.createCell(14).setCellValue("Birth Place");
	    row.createCell(15).setCellValue("Birth Date");
	    row.createCell(16).setCellValue("Nationality");
	    row.createCell(17).setCellValue("Job Title/Occupation/Profession");
	    row.createCell(18).setCellValue("Employer's Name");
	    row.createCell(19).setCellValue("TIN");
	    row.createCell(20).setCellValue("SSS/GSIS No.");
	    row.createCell(21).setCellValue("Driver's License");
	    row.createCell(22).setCellValue("Passport No.");
	    row.createCell(23).setCellValue("Company ID");
	    row.createCell(24).setCellValue("Branch Code");
	    
	    /*	This is the modified style
	    row.createCell(0).setCellValue("Branch Name");
	    row.createCell(1).setCellValue("Branch RC Code");
	    row.createCell(2).setCellValue("Account Number");
	    row.createCell(3).setCellValue("Company Name");
	    row.createCell(4).setCellValue("Last Name");
	    row.createCell(5).setCellValue("First Name");
	    row.createCell(6).setCellValue("Middle Name");
	    row.createCell(7).setCellValue("Present Address");
	    row.createCell(8).setCellValue("Permanent Address");
	    row.createCell(9).setCellValue("Contact Details (Mobile No. or Home/Office No.)");
	    row.createCell(10).setCellValue("Birth Place");
	    row.createCell(11).setCellValue("Birth Date");
	    row.createCell(12).setCellValue("Nationality");
	    row.createCell(13).setCellValue("TIN/SSS/GSIS No.");
	    row.createCell(14).setCellValue("Source of Funds");
	    row.createCell(15).setCellValue("Nature of Employment/Business");
	    row.createCell(16).setCellValue("Job Title/Occupation");
	    
	    row.createCell(17).setCellValue("Mother's Maiden Name");
	    row.createCell(18).setCellValue("Marital Status");
	    row.createCell(19).setCellValue("Gender");
	    row.createCell(20).setCellValue("City");
	    row.createCell(21).setCellValue("ZIP Code");
	    row.createCell(22).setCellValue("Residence No.");
	    row.createCell(23).setCellValue("Mobile No.");
	    row.createCell(24).setCellValue("Office No.");
	    row.createCell(25).setCellValue("Fax No.");
	    row.createCell(26).setCellValue("Email Address");
	    row.createCell(27).setCellValue("Employer's Name");
	    row.createCell(28).setCellValue("TIN");
	    row.createCell(29).setCellValue("SSS/GSIS No.");
	    row.createCell(30).setCellValue("Driver's License");
	    row.createCell(31).setCellValue("Passport No.");
	    row.createCell(32).setCellValue("Company ID");
	    row.createCell(33).setCellValue("Branch Code");
	    
	    row.getCell(0).setCellStyle(styleVertTopBordered);
	    row.getCell(1).setCellStyle(styleVertTopBordered);
	    row.getCell(2).setCellStyle(styleVertTopBordered);
	    row.getCell(3).setCellStyle(styleVertTopBordered);
	    row.getCell(4).setCellStyle(styleVertTopBordered);
	    row.getCell(5).setCellStyle(styleVertTopBordered);
	    row.getCell(6).setCellStyle(styleVertTopBordered);
	    row.getCell(7).setCellStyle(styleVertTopBordered);
	    row.getCell(8).setCellStyle(styleVertTopBordered);
	    row.getCell(9).setCellStyle(styleVertTopBordered);
	    row.getCell(10).setCellStyle(styleVertTopBordered);
	    row.getCell(11).setCellStyle(styleVertTopBordered);
	    row.getCell(12).setCellStyle(styleVertTopBordered);
	    row.getCell(13).setCellStyle(styleVertTopBordered);
	    row.getCell(14).setCellStyle(styleVertTopBordered);
	    row.getCell(15).setCellStyle(styleVertTopBordered);
	    row.getCell(16).setCellStyle(styleVertTopBordered);
	    row.getCell(17).setCellStyle(styleVertTopBordered);
	    row.getCell(18).setCellStyle(styleVertTopBordered);
	    row.getCell(19).setCellStyle(styleVertTopBordered);
	    row.getCell(20).setCellStyle(styleVertTopBordered);
	    row.getCell(21).setCellStyle(styleVertTopBordered);
	    row.getCell(22).setCellStyle(styleVertTopBordered);
	    row.getCell(23).setCellStyle(styleVertTopBordered);
	    row.getCell(24).setCellStyle(styleVertTopBordered);
	    row.getCell(25).setCellStyle(styleVertTopBordered);
	    row.getCell(26).setCellStyle(styleVertTopBordered);
	    row.getCell(27).setCellStyle(styleVertTopBordered);
	    row.getCell(28).setCellStyle(styleVertTopBordered);
	    row.getCell(29).setCellStyle(styleVertTopBordered);
	    row.getCell(30).setCellStyle(styleVertTopBordered);
	    row.getCell(31).setCellStyle(styleVertTopBordered);
	    row.getCell(32).setCellStyle(styleVertTopBordered);
	    row.getCell(33).setCellStyle(styleVertTopBordered);	    
	    */
	    
	    row.getCell(0).setCellStyle(styleVertCenter);
	    row.getCell(1).setCellStyle(styleVertCenter);
	    row.getCell(2).setCellStyle(styleVertCenter);
	    row.getCell(3).setCellStyle(styleVertCenter);
	    row.getCell(4).setCellStyle(styleVertCenter);
	    row.getCell(5).setCellStyle(styleVertCenter);
	    row.getCell(6).setCellStyle(styleVertCenter);
	    row.getCell(7).setCellStyle(styleVertCenter);
	    row.getCell(8).setCellStyle(styleVertCenter);
	    row.getCell(9).setCellStyle(styleVertCenter);
	    row.getCell(10).setCellStyle(styleVertCenter);
	    row.getCell(11).setCellStyle(styleVertCenter);
	    row.getCell(12).setCellStyle(styleVertCenter);
	    row.getCell(13).setCellStyle(styleVertCenter);
	    row.getCell(14).setCellStyle(styleVertCenter);
	    row.getCell(15).setCellStyle(styleVertCenter);
	    row.getCell(16).setCellStyle(styleVertCenter);
	    row.getCell(17).setCellStyle(styleVertCenter);
	    row.getCell(18).setCellStyle(styleVertCenter);
	    row.getCell(19).setCellStyle(styleVertCenter);
	    row.getCell(20).setCellStyle(styleVertCenter);
	    row.getCell(21).setCellStyle(styleVertCenter);
	    row.getCell(22).setCellStyle(styleVertCenter);
	    row.getCell(23).setCellStyle(styleVertCenter);
	    row.getCell(24).setCellStyle(styleVertCenter_1);
	    
		if (sqlExec.isNotNull()) {
			Integer intCount = sqlExec.getRowCount();
			System.out.println("");
			System.out.println("Total number of rows: " + intCount);
			
		    for (Integer intRow = 0; intRow < intCount; intRow++) {
		    	Integer shortInt = intRow + 1;
		    	Short shortRow = Short.parseShort(shortInt.toString());
			    Row item_row = sheet.createRow((short)shortRow);
			    
			    for (Integer intCol = 0; intCol < 25; intCol ++) {
			    //for (Integer intCol = 0; intCol < 34; intCol ++) {
			    	System.out.println("");
			    	System.out.println("Row: " + intRow);
			    	System.out.println("Column: " + intCol);
			    	
			    	item_row.createCell(intCol).setCellValue((String) sqlExec.getResult()[intRow][intCol].toString());
			    	//item_row.getCell(intCol).setCellStyle(styleVertCenterBordered);
			    	
			    	System.out.println("Cell Value[" + intRow + "][" + intCol + "] - " + (String)sqlExec.getResult()[intRow][intCol]);
			    }
		    }
		    
		    for (Integer intCol = 0; intCol <= row.getLastCellNum(); intCol++) {
		    	wb.getSheetAt(0).autoSizeColumn(intCol);
		    }
		} else {
			System.out.print("");
			System.out.print("Procedure halted.");
		}
	    
		/* Test Line	*/
		/*	This is the modified style
		sheet.setColumnHidden(17, true);
		sheet.setColumnHidden(18, true);
		sheet.setColumnHidden(19, true);
		
		sheet.setColumnHidden(20, true);
		sheet.setColumnHidden(21, true);
		sheet.setColumnHidden(22, true);
		sheet.setColumnHidden(23, true);
		sheet.setColumnHidden(24, true);
		sheet.setColumnHidden(25, true);
		sheet.setColumnHidden(26, true);
		
		sheet.setColumnHidden(27, true);
		sheet.setColumnHidden(28, true);
		sheet.setColumnHidden(29, true);
		sheet.setColumnHidden(30, true);
		sheet.setColumnHidden(31, true);
		sheet.setColumnHidden(32, true);
		sheet.setColumnHidden(33, true);
		*/
		
	    FileOutputStream fileOut = null;
	    try {
			fileOut = new FileOutputStream(strDir + "/" + strName);
		} catch (FileNotFoundException e2) {
			System.out.println("");
			System.out.println("Error Line -- " + strName);
		}
		
	    try {
			wb.write(fileOut);
		} catch (IOException e1) {
			System.out.println("Error Line -- wb.write(fileOut)");
		}
	    
	    try {
			fileOut.close();
		} catch (IOException e1) {
			System.out.println("Error Line -- fileOut.close();");
		}
	    
	    JOptionPane.showMessageDialog(null, "Export Successful");
	    
	    Desktop dt = Desktop.getDesktop();
	    f = new File(strDir + "/" + strName);
	    
	    try {
			dt.open(f);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Cannot open file.", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public static Boolean sendFromMailWithoutAttachment(final String from, final String pass, String to, String subject, StringBuffer sb) {
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
            
            message.addRecipient(Message.RecipientType.TO, toAddress);
            message.setSubject(subject);
            message.setContent(sb.toString(), "text/html");
            
            Transport transport = session.getTransport("smtp");

            /*
            try {
            	transport.connect(host, from, pass);
                transport.sendMessage(message, message.getAllRecipients());
            } catch (MessagingException ex) {
            	blnSent = false;
            	JOptionPane.showMessageDialog(null, "Could not connect to mail.yahoo.com.");
            }
            */
            
        	transport.connect(host, from, pass);
            transport.sendMessage(message, message.getAllRecipients());
            
            /*-*-*-*-*-*-*-*-*-*-*-*-*/
            /*	Copy to sent folder	 */
            /*-*-*-*-*-*-*-*-*-*-*-*-*/
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
            /*-*-*-*-*-*-*-*-*-*-*-*-*/
            /*	Copy to sent folder	 */
            /*-*-*-*-*-*-*-*-*-*-*-*-*/
            
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
	
	public static StringBuffer sbActive_preview(String strName, String strProject, String strProjectID, String strUnitID, String strAcctNo, String struser, String strMsg1, String strMsg2) {
		StringBuffer sb = new StringBuffer();
    	sb.append("<p style=\"color: white;background-color: black;\">Dear Ma'am/Sir, </p>");
        sb.append("<br>");
        sb.append("<br>");
        sb.append("<p style=\"color: white;background-color: black;\">"+strMsg1+"</p>");
        sb.append("<br>");
        sb.append("<br>");
        
        sb.append("<table>");
        sb.append("<tr>"); 
        sb.append("<th style=\"color: white;background-color: green;\">Client Name</th>");
        sb.append("<th style=\"color: white;background-color: green;\">Project Name</th>");
        sb.append("<th style=\"color: white;background-color: green;\">Phase</th>");
        sb.append("<th style=\"color: white;background-color: green;\">Block</th>");
	    sb.append("<th style=\"color: white;background-color: green;\">Lot</th>");
	    sb.append("<th style=\"color: white;background-color: green;\">MBTC-DIRECT Account Number</th>");
	    sb.append("</tr>");
	    sb.append("<tr>");
	    
	    sb.append("<td style=\"color: black;background-color: white;\">");
	    sb.append(strName); 
	    sb.append("</td>");
	    
	    sb.append("<td style=\"color: black;background-color: white;\">");
	    sb.append(strProject);
	    sb.append("</td>");

	    sb.append("<td style=\"color: black;background-color: white;\">");
	    sb.append(FncGlobal.GetString("select phase from mf_unit_info where proj_id = '"+strProjectID+"' and pbl_id = '"+strUnitID+"'"));
	    sb.append("</td>");
	    
	    sb.append("<td style=\"color: black;background-color: white;\">");
	    sb.append(FncGlobal.GetString("select block from mf_unit_info where proj_id = '"+strProjectID+"' and pbl_id = '"+strUnitID+"'"));
	    sb.append("</td>");
	    
	    sb.append("<td style=\"color: black;background-color: white;\">");
	    sb.append(FncGlobal.GetString("select lot from mf_unit_info where proj_id = '"+strProjectID+"' and pbl_id = '"+strUnitID+"'"));
	    sb.append("</td>");
	    
	    sb.append("<td style=\"color: black;background-color: white;\">");
	    sb.append(strAcctNo);
	    sb.append("</td>");
	    sb.append("</table>");

	    sb.append("<br>");
	    sb.append("<br>");
	    sb.append("<p style=\"color: white;background-color: black;\">"+strMsg2+"</p>");
	    sb.append("<br>");
	    sb.append("<p style=\"color: white;background-color: black;\">Thank you!</p>");
	    sb.append("<br>");
	    sb.append("<br>");
	    sb.append("<br>");
	    sb.append("<br>");
	    sb.append("<p style=\"color: white;background-color: black;\">"+struser+"</p>");
	    sb.append("<p style=\"color: white;background-color: black;\">Cenqhomes Development Corporation</p>");
	    sb.append("<p style=\"color: white;background-color: black;\">Tel. No. 534-2237 Local 47</p>");	
		
	    System.out.println("Stringbuffer: "+sb.toString());
	    
		return sb;
	}
	
	public static StringBuffer sbCancel_preview(String strName, String strProject, String strProjectID, String strUnitID, String strAcctNo, String struser, String strMsg1, String strMsg2) {
		StringBuffer sb = new StringBuffer();
		/*
		 style="color: white;background-color: black;"
		style="color: white;background-color: green;" 
		style="color: black;background-color: white;"
		*/
    	sb.append("<p style=\"color: white;background-color: black;\">Dear Ma'am/Sir, </p>");
        sb.append("<br>");
        sb.append("<br>");
        sb.append("<p style=\"color: white;background-color: black;\">"+strMsg1+"</p>");
        sb.append("<br>");
        sb.append("<br>");
        
        sb.append("<table>");
        sb.append("<tr>"); 
        sb.append("<th style=\"color: white;background-color: green;\">Client Name</th>");
        sb.append("<th style=\"color: white;background-color: green;\">Project Name</th>");
        sb.append("<th style=\"color: white;background-color: green;\">Phase</th>");
        sb.append("<th style=\"color: white;background-color: green;\">Block</th>");
	    sb.append("<th style=\"color: white;background-color: green;\">Lot</th>");
	    sb.append("<th style=\"color: white;background-color: green;\">MBTC-DIRECT Account Number</th>");
	    sb.append("</tr>");
	    sb.append("<tr>");
	    
	    sb.append("<td style=\"color: black;background-color: white;\">");
	    sb.append(strName); 
	    sb.append("</td>");
	    
	    sb.append("<td style=\"color: black;background-color: white;\">");
	    sb.append(strProject);
	    sb.append("</td>");

	    sb.append("<td style=\"color: black;background-color: white;\">");
	    sb.append(FncGlobal.GetString("select phase from mf_unit_info where proj_id = '"+strProjectID+"' and pbl_id = '"+strUnitID+"'"));
	    sb.append("</td>");
	    
	    sb.append("<td style=\"color: black;background-color: white;\">");
	    sb.append(FncGlobal.GetString("select block from mf_unit_info where proj_id = '"+strProjectID+"' and pbl_id = '"+strUnitID+"'"));
	    sb.append("</td>");
	    
	    sb.append("<td style=\"color: black;background-color: white;\">");
	    sb.append(FncGlobal.GetString("select lot from mf_unit_info where proj_id = '"+strProjectID+"' and pbl_id = '"+strUnitID+"'"));
	    sb.append("</td>");
	    
	    sb.append("<td style=\"color: black;background-color: white;\">");
	    sb.append(strAcctNo);
	    sb.append("</td>");
	    sb.append("</table>");
	    
	    sb.append("<br>");
	    sb.append("<br>");
	    sb.append("<p style=\"color: white;background-color: black;\">"+strMsg2+"</p>");
	    sb.append("<br>");
	    sb.append("<p style=\"color: white;background-color: black;\">Thank you!</p>");
	    sb.append("<br>");
	    sb.append("<br>");
	    sb.append("<br>");
	    sb.append("<br>");
	    sb.append("<p style=\"color: white;background-color: black;\">"+struser+"</p>");
	    sb.append("<p style=\"color: white;background-color: black;\">Cenqhomes Development Corporation</p>");
	    sb.append("<p style=\"color: white;background-color: black;\">Tel. No. 534-2237 Local 47</p>");	
		
	    System.out.println("Stringbuffer: "+sb.toString());
	    
		return sb;
	}
	
	public static StringBuffer sbActive_actual(String strName, String strProject, String strProjectID, String strUnitID, String strAcctNo, String struser, String strMsg1, String strMsg2) {
		StringBuffer sb = new StringBuffer();
		
    	sb.append("<p>Dear Ma'am/Sir, </p>");
        sb.append("<br>");
        sb.append("<br>");
        sb.append("<p>"+strMsg1+"</p>");
        sb.append("<br>");
        sb.append("<br>");
        sb.append("<style>#customers {font-family: \"Trebuchet MS\", Arial, Helvetica, sans-serif;border-collapse: collapse;width: 100%;}");
        sb.append("#customers td, #customers th {border: 1px solid #ddd;padding: 8px;}");
        sb.append("#customers tr:nth-child(even){background-color: #f2f2f2;}");
        sb.append("#customers tr:hover {background-color: #ddd;}");
        sb.append("#customers th {padding-top: 12px;padding-bottom: 12px;text-align: left;background-color: #4CAF50;color: white;}</style>");
        
        sb.append("<table id=\"customers\"><tr><th>Client Name</th><th>Project Name</th><th>Phase</th><th>Block</th>");
	    sb.append("<th>Lot</th><th>MBTC-DIRECT Account Number</th></tr><tr>");
	    
	    sb.append("<td>");
	    sb.append(strName); 
	    sb.append("</td>");
	    
	    sb.append("<td>");
	    sb.append(strProject);
	    sb.append("</td>");

	    sb.append("<td>");
	    sb.append(FncGlobal.GetString("select phase from mf_unit_info where proj_id = '"+strProjectID+"' and pbl_id = '"+strUnitID+"'"));
	    sb.append("</td>");
	    
	    sb.append("<td>");
	    sb.append(FncGlobal.GetString("select block from mf_unit_info where proj_id = '"+strProjectID+"' and pbl_id = '"+strUnitID+"'"));
	    sb.append("</td>");
	    
	    sb.append("<td>");
	    sb.append(FncGlobal.GetString("select lot from mf_unit_info where proj_id = '"+strProjectID+"' and pbl_id = '"+strUnitID+"'"));
	    sb.append("</td>");
	    
	    sb.append("<td>");
	    sb.append(strAcctNo);
	    sb.append("</td>");

	    sb.append("</table>");
	    sb.append("<br>");
	    sb.append("<br>");
	    sb.append("<p>"+strMsg2+"</p>");
	    sb.append("<br>");
	    sb.append("<p>Thank you!</p>");
	    sb.append("<br>");
	    sb.append("<br>");
	    sb.append("<br>");
	    sb.append("<br>");
	    sb.append("<p>"+struser+"</p>");
	    sb.append("<p>Cenqhomes Development Corporation</p>");
	    sb.append("<p>Tel. No. 534-2237 Local 47</p>");	
		
		return sb;
	}

	public static StringBuffer sbCancel_actual(String strName, String strProject, String strProjectID, String strUnitID, String strAcctNo, String struser, String strMsg1, String strMsg2) {
		StringBuffer sb = new StringBuffer();
		
    	sb.append("<p>Dear Ma'am/Sir, </p>");
        sb.append("<br>");
        sb.append("<br>");
        sb.append("<p>"+strMsg1+"</p>");
        sb.append("<br>");
        sb.append("<br>");
        sb.append("<style>#customers {font-family: \"Trebuchet MS\", Arial, Helvetica, sans-serif;border-collapse: collapse;width: 100%;}");
        sb.append("#customers td, #customers th {border: 1px solid #ddd;padding: 8px;}");
        sb.append("#customers tr:nth-child(even){background-color: #f2f2f2;}");
        sb.append("#customers tr:hover {background-color: #ddd;}");
        sb.append("#customers th {padding-top: 12px;padding-bottom: 12px;text-align: left;background-color: #4CAF50;color: white;}</style>");
        
        sb.append("<table id=\"customers\"><tr><th>Client Name</th><th>Project Name</th><th>Phase</th><th>Block</th>");
	    sb.append("<th>Lot</th><th>MBTC-DIRECT Account Number</th></tr><tr>");
	    
	    sb.append("<td>");
	    sb.append(strName); 
	    sb.append("</td>");
	    
	    sb.append("<td>");
	    sb.append(strProject);
	    sb.append("</td>");

	    sb.append("<td>");
	    sb.append(FncGlobal.GetString("select phase from mf_unit_info where proj_id = '"+strProjectID+"' and pbl_id = '"+strUnitID+"'"));
	    sb.append("</td>");
	    
	    sb.append("<td>");
	    sb.append(FncGlobal.GetString("select block from mf_unit_info where proj_id = '"+strProjectID+"' and pbl_id = '"+strUnitID+"'"));
	    sb.append("</td>");
	    
	    sb.append("<td>");
	    sb.append(FncGlobal.GetString("select lot from mf_unit_info where proj_id = '"+strProjectID+"' and pbl_id = '"+strUnitID+"'"));
	    sb.append("</td>");
	    
	    sb.append("<td>");
	    sb.append(strAcctNo);
	    sb.append("</td>");

	    sb.append("</table>");
	    sb.append("<br>");
	    sb.append("<br>");
	    sb.append("<p>"+strMsg2+"</p>");
	    sb.append("<br>");
	    sb.append("<p>Thank you!</p>");
	    sb.append("<br>");
	    sb.append("<br>");
	    sb.append("<br>");
	    sb.append("<br>");
	    sb.append("<p>"+struser+"</p>");
	    sb.append("<p>Cenqhomes Development Corporation</p>");
	    sb.append("<p>Tel. No. 534-2237 Local 47</p>");	
		
		return sb;
	}
}
