package Functions;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.Timestamp;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.jdesktop.swingx.JXFrame;
import org.jdesktop.swingx.auth.UserNameStore;

import Buyers.CreditandCollections._RealTimeDebit;
import Database.pgSelect;
import Home.Home_JSystem;
import Login.Login;
import Lookup._JSearch;
import components.SortableValueMap;
import tablemodel.modelPST_LoanFiled;

public class FncGlobal {

	public static JXFrame parentFrame;
	public static Login login;
	public static _JSearch search;
	
	
	//Changes in Fnc Global hindi ksma sa push
	public static String ORIGINAL_TITLE = "JSystem 2.2"; 
	//public static String ORIGINAL_TITLE = "JSystem 2.2 Anita"; 
	//public static String ORIGINAL_TITLE = "JSystem 2.2 - Beta Testing";
//	public static String ORIGINAL_TITLE = "JSystem 2.2 - Offline Montalban";
	//public static String ORIGINAL_TITLE = "JSystem 2.2 - Beta(Testing)";
	//public static String ORIGINAL_TITLE = "JSystem 2.2 Offline";  p
	public static Properties properties = new Properties();	
	public static UserNameStore usernameStore; 
	public static boolean isRememberPassword;  																																																									
	public static String server; 																																																																																																																																
	public static Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);  
	public static Home_JSystem homeMDI; 
	public static Connection connection; 

	

	public static String connectionDriver = "org.postgresql.Driver";
	
	//Test Update 2022-09-08
	/*********************/ /*********************/ /*********************/
	/**	Server Settings	**/ /**	Server Settings	**/ /**	Server Settings	**/
	/*********************/ /*********************/ /*********************/
	
	public static String connectionURL = "jdbc:postgresql://jsystemdb.cenqhomes.com:5432/terraverde_summit?ApplicationName=JSystem2.2";
//	public static String connectionURL = "jdbc:postgresql://localhost:5432/terraverde_montalban_offline?ApplicationName=JSystem2.2";
	//public static String connectionURL = "jdbc:postgresql://192.168.10.169:5432/terraverde_summit_test?ApplicationName=JSystem2.2 Beta";
	//public static String connectionURL = "jdbc:postgresql://localhost:5432/Daily_debug?ApplicationName=JSystem2.2";
	//public static String connectionURL = "jdbc:postgresql://192.168.10.109:5432/terraverde_summit_lester_latest?ApplicationName=JSystem2.2";
	//public static String connectionURL = "jdbc:postgresql://localhost:5432/terraverde_summit_local?ApplicationName=JSystem2.2";
	//public static String connectionURL = "jdbc:postgresql://jsystemdb.cenqhomes.com/terraverde_summit?ApplicationName=JSystem2.2";
	//public static String connectionURL = "jdbc:postgresql://177.177.180.28:5432/db_terraverde_local?ApplicationName=JSystem2.2";
	//public static String connectionURL = "jdbc:postgresql://192.168.10.169:5432/terraverde_summit_lester?ApplicationName=JSystem2.2";
	//public static String connectionURL = "jdbc:postgresql://192.168.10.156:5432/jervinpogi?ApplicationName=JSystem2.2";
	//public static String connectionURL = "jdbc:postgresql://192.168.10.111:5432/terraverde_summit_jed?ApplicationName=JSystem2.2";
	//public static String connectionURL = "jdbc:postgresql://127.0.0.1:5432/terraverde_summit_local?ApplicationName=JSystem2.2"; 
	//public static String connectionURL = "jdbc:postgresql://192.168.15.10:5432/terraverde_carmona?ApplicationName=JSystem2.2"; 
	//public static String connectionURL = "jdbc:postgresql://localhost:5432/local_terraverde_summit?ApplicationName=JSystem2.2";
	//public static String connectionURL = "jdbc:postgresql://localhost:5432/local_tejrraverde_summit?ApplicationName=JSystem2.2";
	//public static String connectionURL = "jdbc:postgresql://192.168.10.135:5432/terraverde_summit_local?ApplicationName=JSystem2.2";
	//public static String connectionURL = "jdbc:postgresql://192.168.10.172:5434/terraverde_summit_9_21_2022?ApplicationName=JSystem2.2";
	//public static String connectionURL = "jdbc:postgresql://192.168.10.172/terraverde_summit_9_19_2022?ApplicationName=JSystem2.2";
	//public static String connectionURL = "jdbc:postgresql://192.168.10.172/terraverde_summit_9_30_2021?ApplicationName=JSystem2.2";
	//public static String connectionURL = "jdbc:postgresql://192.168.10.172/terraverde_summit_4_4_2023?ApplicationName=JSystem2.2";
//	public static String connectionURL = "jdbc:postgresql://192.168.10.172/terraverde_summit_monique?ApplicationName=JSystem2.2";
	//public static String connectionURL = "jdbc:postgresql://192.168.10.172/terraverde_summit_garbage?ApplicationName=JSystem2.2";
	//public static String connectionURL = "jdbc:postgresql://192.168.10.172/terraverde_summit_12_29_2022?ApplicationName=JSystem2.2";
	//public static String connectionURL = "jdbc:postgresql://192.168.10.1:5434/terraverde_summit_2022_7_1?ApplicationName=JSystem2.2";
	//public static String connectionURL = "jdbc:postgresql://192.168.10.172:5432/terraverde_summit_10_25_2022?ApplicationName=JSystem2.2";
	//public static String connectionURL = "jdbc:postgresql://192.168.10.143/terraverde_summit_almar?ApplicationName=JSystem2.2";

	/*********************/ /*********************/ /*********************/
	/**	Server Settings	**/ /**	Server Settings	**/ /**	Server Settings	**/
	/*********************/ /*********************/ /*********************/

	public static String connectionUsername = "";
	public static String connectionPassword = "";

	
	//public static SortableValueMap<String, String> mapDriver = new SortableValueMap<String, String>();
	public static SortableValueMap<String, String> mapURL = new SortableValueMap<String, String>();

	public static String DRIVER = "org.postgresql.Driver";

	public static SortableValueMap<String, Date> mapHolidays = new SortableValueMap<String, Date>();

	public static List<String> listGroupParticulars = new ArrayList<String>();
	public static List<Date> listHolidays = new ArrayList<Date>();
	public static List<Integer> listYear = new ArrayList<Integer>();

	public static LoggedPrintStream lpsOut;
	public static JTextArea textArea = new JTextArea();
	
	//CVS Update Sample ASter

	public static Map<String, Thread> mapThreads = new HashMap<String, Thread>();
	public static Map<String, ArrayList<String>> mapMenu = new HashMap<String, ArrayList<String>>();
	public static Map<String, ArrayList<String>> mapParent = new HashMap<String, ArrayList<String>>();
	
	public static Font font7 = FncLookAndFeel.systemFont_Plain.deriveFont(7f);
	public static Font font7bold = FncLookAndFeel.systemFont_Bold.deriveFont(7f);

	public static Font font11 = FncLookAndFeel.systemFont_Plain.deriveFont(11f);
	public static Font font11bold = FncLookAndFeel.systemFont_Bold.deriveFont(11f);

	public static Font font9 = FncLookAndFeel.systemFont_Plain.deriveFont(9f);
	public static Font font9bold = FncLookAndFeel.systemFont_Bold.deriveFont(9f);
	public static Font font10 = FncLookAndFeel.systemFont_Plain.deriveFont(10f);
	public static Font font10bold = FncLookAndFeel.systemFont_Bold.deriveFont(10f);
	
	public static Font font12 = FncLookAndFeel.systemFont_Plain.deriveFont(12f);
	public static Font font12bold = FncLookAndFeel.systemFont_Bold.deriveFont(12f);
	public static Font font13 = FncLookAndFeel.systemFont_Plain.deriveFont(13f);
	public static Font font13bold = FncLookAndFeel.systemFont_Bold.deriveFont(13f);

	public static Font font15 = FncLookAndFeel.systemFont_Plain.deriveFont(15f);
	public static Font font15bold = FncLookAndFeel.systemFont_Bold.deriveFont(15f); 
	public static Font font16 = FncLookAndFeel.systemFont_Plain.deriveFont(16f);
	public static Font font16bold = FncLookAndFeel.systemFont_Bold.deriveFont(16f);
	
	public static Font sizeLabel = font15bold; 
	public static Font sizeTextValue = font15;
	public static Font sizeControls = font16bold;
	
	public static ComboBoxModel cmbmodelMonth = new DefaultComboBoxModel(new String[] {
		"January", 
		"February", 
		"March",
		"April", 
		"May", 
		"June", 
		"July", 
		"August", 
		"September", 
		"October", 
		"November", 
		"December"
	});

	public static JMenuBar menuBar;

	public FncGlobal() {

	}

	public static void initialize(boolean isAdmin) {
		
		/**
		 * Paki comment nalang yung ibang servers. Dapat isang server lang pwedeng gamitin (i.e. 6th, Live or Beta)
		 */
		
		/*********************/ /*********************/ /*********************/
		/**	Server Settings	**/ /**	Server Settings	**/ /**	Server Settings	**/
		/*********************/ /*********************/ /*********************/
		//Servers to be deployed in 7th Floor (Live)
		mapURL.put("Summit - Terraverde Live", "jdbc:postgresql://jsystemdb.cenqhomes.com:5432/terraverde_summit?ApplicationName=JSystem2.2");
//		mapURL.put("Summit - Beta Testing", "jdbc:postgresql://localhost:5432/terraverde_montalban_offline?ApplicationName=JSystem2.2");
		//mapURL.put("Summit - Beta Testing", "jdbc:postgresql://localhost:5432/Daily_debug?ApplicationName=JSystem2.2");
		//mapURL.put("Summit - Terraverde Live", "jdbc:postgresql://192.168.10.109:5432/terraverde_summit_lester_latest?ApplicationName=JSystem2.2");
		//mapURL.put("Summit - Terraverde Testing", "jdbc:postgresql://192.168.10.169:5432/terraverde_summit_test?ApplicationName=JSystem2.2 Beta");
		//mapURL.put("Summit - Terraverde Beta", "jdbc:postgresql://jsystemdb.cenqhomes.com:5432/terraverde_summit_latest?ApplicationName=JSystem2.2");
		//mapURL.put("Summit - Terraverde Live", "jdbc:postgresql://192.168.10.73/local_terraverde_summit?ApplicationName=JSystem2.2");
		//mapURL.put("Summit - Terraverde Live", "jdbc:postgresql://177.177.177.179:5432/terraverde_summit?ApplicationName=JSystem2.2");
		//mapURL.put("Summit - Terraverde", "jdbc:postgresql://177.177.180.28:5432/db_terraverde_local?ApplicationName=JSystem2.2");
		//mapURL.put("Summit - Terraverde Beta Testing", "jdbc:postgresql://192.168.10.169:5432/terraverde_summit_lester?ApplicationName=JSystem2.2");
		//mapURL.put("Summit - Terraverde Beta Testing", "jdbc:postgresql://192.168.10.156:5432/jervinpogi?ApplicationName=JSystem2.2");
		//mapURL.put("Summit - Terraverde Beta", "jdbc:postgresql://192.168.10.111:5432/terraverde_summit_jed?ApplicationName=JSystem2.2"); 
		//mapURL.put("Anita - Terraverde Live Local", "jdbc:postgresql://177.177.176.179:5432/terraverde_beta?ApplicationName=JSystem2.2"); 		
		//mapURL.put("Carmona - Terraverde Offline", "jdbc:postgresql://192.168.15.10:5432/terraverde_carmona?ApplicationName=JSystem2.2"); 
		//mapURL.put("Summit - Terraverde Live", "jdbc:postgresql://192.168.10.170:5432/terraverde_summit_lester?ApplicationName=JSystem2.2");
		//mapURL.put("Summit - Terraverde Test", "jdbc:postgresql://192.168.10.135:5432/terraverde_summit_local?ApplicationName=JSystem2.2");
		//mapURL.put("Summit - Terraverde Beta Testing", "jdbc:postgresql://192.168.10.1:5434/terraverde_testing_migration?ApplicationName=JSystem2.2");
		//mapURL.put("Summit - Terraverde Live", "jdbc:postgresql://192.168.10.172/terraverde_summit_9_19_2022?ApplicationName=JSystem2.2");
		//mapURL.put("Summit - Terraverde Live", "jdbc:postgresql://192.168.10.172/terraverde_summit_9_30_2021?ApplicationName=JSystem2.2");
		//mapURL.put("Summit - Terraverde Live", "jdbc:postgresql://192.168.10.172/terraverde_summit_4_4_2023?ApplicationName=JSystem2.2");
		//mapURL.put("Summit - Terraverde Live", "jdbc:postgresql://192.168.10.172/terraverde_summit_garbage?ApplicationName=JSystem2.2");
		//mapURL.put("Summit - Terraverde Live", "jdbc:postgresql://192.168.10.172/terraverde_summit_11_22_2022?ApplicationName=JSystem2.2");
		
		//mapURL.put("Summit - Terraverde Live", "jdbc:postgresql://192.168.10.172/terraverde_summit_12_29_2022?ApplicationName=JSystem2.2");

		
		//mapURL.put("Summit - Terraverde Live", "jdbc:postgresql://192.168.10.1:5434/terraverde_summit_2022_7_1?ApplicationName=JSystem2.2");
//		mapURL.put("Summit - Terraverde Beta Testing", "jdbc:postgresql://192.168.10.172:5432/terraverde_summit_monique?ApplicationName=JSystem2.2");
		//mapURL.put("Summit - Terraverde Live", "jdbc:postgresql://192.168.10.143/terraverde_summit_almar?ApplicationName=JSystem2.2");

		//Servers Local Servers 
		//mapURL.put("Terraverde-Offline", "jdbc:postgresql://localhost:5432/local_terraverde_summit?ApplicationName=JSystem2.2");
		//mapURL.put("Terraverde-Offline", "jdbc:postgresql://localhost:5432/local_terraverde_summit?ApplicationName=JSystem2.2");
		
		//Servers to be deployed in 7th Floor (Beta)
		//mapURL.put("Summit - 7th Floor (BETA)", "jdbc:postgresql://177.177.177.179:5432/terraverde_beta?ApplicationName=JSystem2.2 Beta");
		//Servers to be deployed for Open House 
		//mapURL.put("Site - Open House", "jdbc:postgresql://177.177.176.179:5432/terraverde?ApplicationName=JSystem2.2 (Open House)"); //XXX OPEN HOUSE DATABASE 
		/*********************/ /*********************/ /*********************/
		/**	Server Settings	**/ /**	Server Settings	**/ /**	Server Settings	**/
		/*********************/ /*********************/ /*********************/
		
		InetAddress address;
		boolean reachable = true;
		String ip_address = "";
		//InetAddress inetAddress = InetAddress.getLocalHost();
		try {
			InetAddress inetAddress = InetAddress.getLocalHost();
			//address = InetAddress.getByName("jsystemdb.cenqhomes.com");
			ip_address = inetAddress.getHostAddress();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// TODO Auto-generated catch block
		}
		
		System.out.printf("Display ip_address: %s%n", ip_address);
		
		System.out.println("connectionURL: "+connectionURL);
		
//		if(ip_address.equals("177.177.178.199") || ip_address.equals("177.177.178.19") || ip_address.equals("177.177.178.164") || ip_address.equals("177.177.178.119") || ip_address.equals("177.177.178.144") || ip_address.equals("177.177.178.79") || ip_address.equals("177.177.178.165") || ip_address.equals("177.177.176.90") || ip_address.equals("177.177.177.13") ) {
//			mapURL.put("Summit - Terraverde Live", "jdbc:postgresql://192.168.10.15:5432/terraverde_summit?ApplicationName=JSystem2.2");
//			System.out.println("Not Reachable");
//			connectionURL = "jdbc:postgresql://192.168.10.15:5432/terraverde_summit?ApplicationName=JSystem2.2";
//		}
        
		int currentYear = Calendar.getInstance().get(Calendar.YEAR) + 4;

		System.out.println("connectionURL: "+connectionURL);
		
		for(int x=0; x < 11; x++){
			listYear.add(currentYear - x);
		}
		setDefault();
		login = new Login(new FncLoginService(), isAdmin);
	}

	public static void setDefault() {
		try { 
			Class.forName(connectionDriver); 
			FncGlobal.connection = DriverManager.getConnection(connectionURL, connectionUsername, connectionPassword); 
		} catch (ClassNotFoundException e) { 
			e.printStackTrace(); 
		} catch (SQLException e) {

		}
	}

	/*
	public static void initializeMaps() {
		pgSelect db = new pgSelect();pl
		db.select("select ho_year, ho_month, ho_day, ho_desc, rec_id from dbo.holidays where status_id = 'A' and ho_year = "+ Calendar.getInstance().get(Calendar.YEAR)+"");
		if(db.isNotNull()){//
			for (int x=0; x < db.getRowCount(); x++) {
				int year = (Integer) Math.round((Short) db.getResult()[x][0]);
				int month = (Integer) Math.round((Short) db.getResult()[x][1]);
				int day = (Integer) Math.round((Short) db.getResult()[x][2]);

				listHolidays.add(FncDate.getDate(year, month -1, day));
				mapHolidays.put((String)db.getResult()[x][3], FncDate.getDate(year, month -1, day));
			}
		}

		db.select("select * \n" +
				"from dbo.mf_pay_particular \n" +
				"where OR_receipt = 1 \n" +
				"and group_id in ('02', '03') \n" +
				"and apply_ledger = 1 \n" +
				"and status_id = 'A' \n" +
				"order by apply_order");

		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				listGroupParticulars.add(((String) db.getResult()[x][1]).trim());
			}
		}
	}
	*/

	public static ImageIcon getIcon(String url) {
		return new ImageIcon(FncGlobal.class.getClassLoader().getResource(url));
	}

	public static Thread loadThread(Runnable runThis) {
		Thread newThread = new Thread(runThis);

		return newThread;
	}

	public static void startProgress(String caption) {
		homeMDI.startProgress(caption);
	}

	public static void stopProgress() {
		homeMDI.stopProgress();
	}

	/********** SQL's **********/

	public static String getCompany() {
		return "select co_id as \"ID\", trim(company_name) as \"Company Name\", trim(company_alias) as \"Alias\" from mf_company";
	}

	public static String getProject(String company_id) {
		return "select proj_id as \"ID\", trim(proj_name) as \"Project Name\", trim(proj_alias) as \"Alias\"\n" +
				"from mf_project\n" +
				"where co_id = '"+ company_id +"' and status_id = 'A';";
	}

	public static String getPhase(String proj_id) {
		String phase = "select\n" +
				"getinteger(a.phase) as \"Phase\",\n" +
				"'Phase ' || trim(array_to_string(array_agg(trim(a.phase)), ' & ')) as \"Description\",\n" +
				"b.proj_alias || getinteger(a.phase) as \"Alias\"\n" +
				"from mf_sub_project a\n" +
				"left join mf_project b on a.proj_id = b.proj_id\n" +
				"where a.proj_id = '"+ proj_id +"'\n" +
				"and a.status_id = 'A'\n" +
				"group by getinteger(a.phase), b.proj_alias \n" +
				"order by getinteger(a.phase), b.proj_alias ;";
		//String phase = "SELECT * FROM view_lookup_phase('"+ proj_id +"');";
		//System.out.println("\n********************Phase!\n" + phase + "\n\n");
		return phase;
	}

	public static String getClients() {
		return "SELECT * FROM jsearch;";
	}

	public static String getEmployees() {
		return "SELECT * FROM view_employee;";
	}

	public static Object[] listCountry() {
		ArrayList<String> alistCountry = new ArrayList<String>();
		//int countryID = 0;

		String[] locales = Locale.getISOCountries();
		//pgUpdate db = new pgUpdate();
		for (String countryCode : locales) {
			Locale obj = new Locale("", countryCode);
			String countryName = obj.getDisplayCountry();
			alistCountry.add(countryName);

			//System.out.printf("Country: %s - %s (%s)%n", countryCode, countryName, countryID++);
			/*String SQL = "INSERT INTO mf_country(country_id, country_name, country_abbrev, status_id, created_by, date_created, edited_by, date_edited)\n" + 
					"VALUES (to_char("+ countryID +", 'FM0000'), '"+ countryName.replace("'", "''") +"', '"+ countryCode.replace("'", "''") +"', 'A', '900668', now(), null, null);";

			db.executeUpdate(SQL, false);*/
		}

		Object[] finalList = alistCountry.toArray();
		Arrays.sort(finalList);
		return finalList;
	}

	public static String getReportTitle(String title) {
		return title + " Report";
	}

	public static String getReportTitle(String title, String company) {
		return title + " Report (" + company + ")";
	}

	public static String getReportTitle(String title, String project, String phase) {
		if(!project.equals("")){
			if(!phase.equals("")){
				return title + " Report (" + project + " " + phase + ")";	
			}else{
				return title + " Report (" + project + ")";
			}
		}else{
			return title + " Report";
		}
	}

	public static void loadThread(String title, Runnable run) {
		try {
			if(!FncGlobal.mapThreads.get(title).isAlive()){
				FncGlobal.mapThreads.put(title, new Thread(run));
				FncGlobal.mapThreads.get(title).start();
			}else{
				JOptionPane.showMessageDialog(FncGlobal.homeMDI, title + " is already running.", title, JOptionPane.WARNING_MESSAGE);
			}
		} catch (NullPointerException e1) {
			FncGlobal.mapThreads.put(title, new Thread(run));
			FncGlobal.mapThreads.get(title).start();
		}
	}

	public static String getURLIPAdress() {
		String IPADDRESS_PATTERN = "(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";

		Pattern pattern = Pattern.compile(IPADDRESS_PATTERN);
		Matcher matcher = pattern.matcher(FncGlobal.connectionURL);

		String ip_address = null;
		while (matcher.find()) {
			ip_address = matcher.group();
		}

		return ip_address;
	}

	public static  String getDateSQL(){
		pgSelect db = new pgSelect(); 
		db.select("SELECT CURRENT_DATE"); 

		if (db.getResult()!=null) {
			return db.Result[0][0].toString(); 	
		} else {
			return FncGlobal.getDateSQL(); 
		}		
	}

	public static Date getDateToday(){
		pgSelect db = new pgSelect();
		db.select("SELECT CURRENT_DATE");
		return (Date) db.getResult()[0][0];
	}
	
	public static Date getDateNow() {
		pgSelect db = new pgSelect();
		db.select("SELECT now()");
		return (Date) db.getResult()[0][0];
	}

	public static Date dateFormat(String dates){

		DateFormat formatter ; 
		Date date = null ;

		formatter = new SimpleDateFormat("yyyy-MM-dd");
		try {
			date = (Date)formatter.parse(dates);
		} catch (ParseException e) {
		} 

		return date;
	}

	/*	Created by Mann2x; Created Date: November 8, 2016; For the direct creation of spreadsheets.	*/
	public static void CreateXLS(String strCol [], String strSQL, String strFile) {
		String strFileNo = "";
		String strFileName = "";
		String strDate = "";

		System.out.println("");
		System.out.println("Creating Spreadsheet...");
		System.out.println(strSQL);

		pgSelect db = new pgSelect();
		db.select(strSQL);

		Integer row = db.getRowCount();
		Integer col = db.getColumnCount();

		Workbook wb = new HSSFWorkbook();
		Sheet sheet = wb.createSheet("new sheet");

		SimpleDateFormat sdfTo = new SimpleDateFormat("MMddyy");
		Date dateObj = new Date();
		strDate = (String)sdfTo.format(dateObj);

		/**		Set Directory													**/
		String strDir = _RealTimeDebit.OpenDir("Folder");

		/**		Check if file of the same name exists within the directory		**/
		Integer intSeq = 1;
		strFileNo = _RealTimeDebit.Padme(intSeq.toString(), 2);
		File f = new File(strDir + "/" + strFile + strDate + "(" + strFileNo + ")" + ".xls");

		System.out.println("");
		System.out.println("File Name: " + strFile + strDate + "(" + strFileNo + ")" + ".xls");

		while (f.exists() && f.isFile()) {
			System.out.println("");
			System.out.println("File exists! Number: " + strFileNo);

			intSeq++;
			strFileNo = _RealTimeDebit.Padme(intSeq.toString(), 2);
			f = new File(strDir + "/" + strFile + strDate + "(" + strFileNo + ")" + ".xls");
			System.out.println("New Name: " + strFile + strDate + "(" + strFileNo + ")" + ".xls");
		}

		strFileName = strFile + strDate + "(" + strFileNo + ")" + ".xls";

		/**		Begin Creating Excel Content									**/
		System.out.println("");
		Row rowRow = sheet.createRow((short) 0);
		for (int x = 0; x < col; x++) {
			rowRow.createCell(x).setCellValue(strCol [x]);

			System.out.println(strCol [x]);
		}

		if (db.isNotNull()) {
			System.out.println("");
			System.out.println("Total number of rows: " + row);

			String strValue = "";

			for (Integer intRow = 0; intRow < row; intRow++) {
				Integer shortInt = intRow + 1;
				Short shortRow = Short.parseShort(shortInt.toString());
				Row item_row = sheet.createRow((short)shortRow);

				for (Integer x = 0; x <= col-1; x ++) {
					try {
						item_row.createCell(x).setCellValue((String)db.getResult()[intRow][x]);
					} catch (ClassCastException ex) {
						try {
							BigDecimal bdValue = (BigDecimal) db.getResult()[intRow][x];
							strValue = bdValue.toString();
							//strValue = String.valueOf(bdValue);
							item_row.createCell(x).setCellValue(strValue);
						} catch (ClassCastException ey) {			    		
							try {
								Integer intValue = (Integer) db.getResult()[intRow][x];
								strValue = intValue.toString();
								item_row.createCell(x).setCellValue(strValue);
								System.out.println("Cell value is Integer.");
							} catch (ClassCastException ez) {
								try {
									Timestamp tmeValue = (Timestamp) db.getResult()[intRow][x];
									strValue = tmeValue.toString();
									item_row.createCell(x).setCellValue(strValue);
								} catch (ClassCastException ea) {
									try {
										Date dteValue = (Date) db.getResult()[intRow][x];
										strValue = dteValue.toString();
										item_row.createCell(x).setCellValue(strValue);
									} catch (ClassCastException eb) {

									}
								}
							}

						}
					}
				}
			}

			FileOutputStream fileOut = null;
			try {
				fileOut = new FileOutputStream(strDir + "/" + strFileName);
			} catch (FileNotFoundException e2) {
				System.out.println("");
				System.out.println("Error Line -- " + strFileName);
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

			JOptionPane.showMessageDialog(null, "Export to file successful!");

			Desktop dt = Desktop.getDesktop();
			f = new File(strDir + "/" + strFileName);

			try {
				dt.open(f);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Cannot open file.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(null, "Spreadsheet creation halted.");
		}
	}

	/*	Created by Mann2x; Created Date: January 24, 2016; For the direct creation of spreadsheets with headers.	*/
	public static void CreateXLSwithHeaders(String strCol [], String strSQL, String strFile, Integer intHead, String strHead []) {
		String strFileNo = "";
		String strFileName = "";
		String strDate = "";

		System.out.println("");
		System.out.println("Creating Spreadsheet...");
		System.out.println(strSQL);

		pgSelect db = new pgSelect();
		db.select(strSQL);

		Integer row = db.getRowCount();
		Integer col = db.getColumnCount();
		Integer hd = intHead;

		Workbook wb = new HSSFWorkbook();
		Sheet sheet = wb.createSheet("new sheet");

		SimpleDateFormat sdfTo = new SimpleDateFormat("MMddyy");
		Date dateObj = new Date();
		strDate = (String)sdfTo.format(dateObj);

		/***	Cell Styles		***/
		HSSFFont font = (HSSFFont) wb.createFont();
		font.setBoldweight(font.BOLDWEIGHT_BOLD);

		HSSFCellStyle style1 = (HSSFCellStyle) wb.createCellStyle();
		style1.setFont(font);

		HSSFCellStyle style2 = (HSSFCellStyle) wb.createCellStyle();
		style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style2.setFont(font);
		/***	Cell Styles		***/


		/**		Set Directory													**/
		String strDir = _RealTimeDebit.OpenDir("Folder");

		/**		Check if file of the same name exists within the directory		**/
		Integer intSeq = 1;
		strFileNo = _RealTimeDebit.Padme(intSeq.toString(), 2);
		File f = new File(strDir + "/" + strFile + strDate + "(" + strFileNo + ")" + ".xls");

		System.out.println("");
		System.out.println("File Name: " + strFile + strDate + "(" + strFileNo + ")" + ".xls");

		while (f.exists() && f.isFile()) {
			System.out.println("");
			System.out.println("File exists! Number: " + strFileNo);

			intSeq++;
			strFileNo = _RealTimeDebit.Padme(intSeq.toString(), 2);
			f = new File(strDir + "/" + strFile + strDate + "(" + strFileNo + ")" + ".xls");
			System.out.println("New Name: " + strFile + strDate + "(" + strFileNo + ")" + ".xls");
		}

		strFileName = strFile + strDate + "(" + strFileNo + ")" + ".xls";

		/**		Begin Creating Page Header		**/
		System.out.println("");
		for (int x = 0; x < hd; x++) {
			Row rowHead = sheet.createRow((short) x);
			rowHead.createCell(0).setCellValue(strHead [x]);
			rowHead.getCell(0).setCellStyle(style1);
			System.out.println(strHead [x]);
		}		

		/**		Begin Creating Column Header	**/
		System.out.println("");
		Row rowRow = sheet.createRow((short) (hd + 1));
		rowRow.setHeightInPoints(3 * sheet.getDefaultRowHeightInPoints());
		for (int x = 0; x < col; x++) {
			rowRow.createCell(x).setCellValue(strCol [x]);
			rowRow.getCell(x).setCellStyle(style2);
			System.out.println(strCol [x]);
		}

		/**		Begin Creating Excel Content	**/
		if (db.isNotNull()) {
			System.out.println("");
			System.out.println("Total number of rows: " + row);

			String strValue = "";

			for (Integer intRow = 0; intRow < row; intRow++) {
				Integer shortInt = intRow + hd + 3;
				Short shortRow = Short.parseShort(shortInt.toString());
				Row item_row = sheet.createRow((short)shortRow);

				for (Integer x = 0; x <= col-1; x ++) {
					try {
						item_row.createCell(x).setCellValue((String)db.getResult()[intRow][x]);
					} catch (ClassCastException ex) {
						try {
							BigDecimal bdValue = (BigDecimal) db.getResult()[intRow][x];
							strValue = bdValue.toString();
							item_row.createCell(x).setCellValue(strValue);
						} catch (ClassCastException ey) {			    		
							try {
								Integer intValue = (Integer) db.getResult()[intRow][x];
								strValue = intValue.toString();
								item_row.createCell(x).setCellValue(strValue);
								System.out.println("Cell value is Integer.");
							} catch (ClassCastException ez) {
								try {
									Timestamp tmeValue = (Timestamp) db.getResult()[intRow][x];
									strValue = tmeValue.toString();
									item_row.createCell(x).setCellValue(strValue);
								} catch (ClassCastException ea) {
									try {
										Date dteValue = (Date) db.getResult()[intRow][x];
										strValue = dteValue.toString();
										item_row.createCell(x).setCellValue(strValue);
									} catch (ClassCastException eb) {

									}
								}
							}

						}
					}
				}
			}

			FileOutputStream fileOut = null;
			try {
				fileOut = new FileOutputStream(strDir + "/" + strFileName);
			} catch (FileNotFoundException e2) {
				System.out.println("");
				System.out.println("Error Line -- " + strFileName);
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

			JOptionPane.showMessageDialog(null, "Export to file successful!");

			Desktop dt = Desktop.getDesktop();
			f = new File(strDir + "/" + strFileName);

			try {
				dt.open(f);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Cannot open file.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(null, "Spreadsheet creation halted.");
		}
	}

	public static void CreateXLSFromModel(String strCol [], String strHiddenCols [] ,modelPST_LoanFiled model, String strFile){
		String strFileNo = "";
		String strFileName = "";
		String strDate = "";

		Integer selected_rows = 0;

		for(int z = 0; z<model.getRowCount(); z++){
			Boolean isSelected = (Boolean) model.getValueAt(z, 0);
			if(isSelected){
				selected_rows = selected_rows + 1;
			}
		}
		//Integer row = model.getRowCount() - selected_rows;
		Integer row = selected_rows;
		//Integer col = model.getColumnCount();
		Integer col = model.getColumnCount() - strHiddenCols.length;

		System.out.printf("Display value of selected rows: %s%n", row);
		System.out.printf("Display VALUE of Model Column Count: %s%n", model.getColumnCount());
		System.out.printf("Display Value of Model Row Count: %s%n", model.getRowCount());
		System.out.printf("Display Value of Column Count: %s%n", col);
		System.out.printf("Display Length of Hidden Columns: %s%n", strHiddenCols.length);

		Workbook wb = new HSSFWorkbook();
		Sheet sheet = wb.createSheet("new sheet");

		SimpleDateFormat sdfTo = new SimpleDateFormat("MMddyy");
		Date dateObj = new Date();
		strDate = (String)sdfTo.format(dateObj);

		/**		Set Directory													**/
		String strDir = _RealTimeDebit.OpenDir("Folder");

		/**		Check if file of the same name exists within the directory		**/
		Integer intSeq = 1;
		strFileNo = _RealTimeDebit.Padme(intSeq.toString(), 2);
		File f = new File(strDir + "/" + strFile + strDate + "(" + strFileNo + ")" + ".xls");

		System.out.println("");
		System.out.println("File Name: " + strFile + strDate + "(" + strFileNo + ")" + ".xls");

		while (f.exists() && f.isFile()) {
			System.out.println("");
			System.out.println("File exists! Number: " + strFileNo);

			intSeq++;
			strFileNo = _RealTimeDebit.Padme(intSeq.toString(), 2);
			f = new File(strDir + "/" + strFile + strDate + "(" + strFileNo + ")" + ".xls");
			System.out.println("New Name: " + strFile + strDate + "(" + strFileNo + ")" + ".xls");
		}

		strFileName = strFile + strDate + "(" + strFileNo + ")" + ".xls";

		/**		Begin Creating Excel Content									**/
		//System.out.println("");
		Row rowRow = sheet.createRow((short) 0);
		//System.out.printf("Display Array Count: %s%n", strHiddenCols.length);

		for (int x = 0; x < col; x++) {
			for(int y = 0; y <strHiddenCols.length; y ++){
				if((strHiddenCols[y]).toString().trim().equals(model.getColumnName(x).toString().trim()) == false /*&& model.getColumnName(x).toString().trim().equals("Select") == false*/){
					if(model.getColumnName(x).toString().trim().equals("Select")){
						System.out.println("Dumaan dito sa Added select");
					}
					System.out.printf("Display Setvalue Data: %s%n", strCol[x]);
					System.out.printf("Display value of x: %s%n", x);
					System.out.println("");
					rowRow.createCell(x).setCellValue(strCol[x]);
				}
			}
		}

		System.out.printf("Display Model Row Count: %s%n", model.getRowCount());


		if (model.getRowCount() != 0) {
			System.out.println("");
			System.out.println("Total number of rows: " + row);

			String strValue = "";
			Integer row_count = 0;
			
			
			for (Integer intRow = 0; intRow < model.getRowCount() ; intRow++) {

				Boolean isSelected = (Boolean) model.getValueAt(intRow, 0);
				System.out.println("");

				if(isSelected){
					//if(intRow != 0){\
					row_count = row_count + 1;

					Integer shortInt = intRow;

					//Short shortRow = Short.parseShort(shortInt.toString());
					Short shortRow = Short.parseShort(row_count.toString());
					System.out.printf("Display Value of int Row: %s%n", intRow);
					System.out.printf("Display value of Short Row: %s%n", shortRow);

					Row item_row = sheet.createRow((short)shortRow);
					System.out.println("");
					System.out.println("");

					for (Integer x = 0; x < col + 1; x ++) {
						//for(int y = 0; y <strHiddenCols.length; y ++){
							
							if(x != 0){
								System.out.printf("Display Column Name: %s%n", model.getColumnName(x).toString().trim());
								System.out.printf("Display Data set value: %s%n", model.getValueAt(intRow, x));
								System.out.printf("Display value of x: %s%n", x);
								System.out.println("");
								System.out.println("");
								
								try {
									item_row.createCell(x - 1).setCellValue((String)model.getValueAt(intRow, x));
								} catch (ClassCastException ex) {
									try {
										BigDecimal bdValue = (BigDecimal) model.getValueAt(intRow, x);
										strValue = bdValue.toString();
										item_row.createCell(x - 1).setCellValue(strValue);
									} catch (ClassCastException ey) {			    		
										try {
											Integer intValue = (Integer) model.getValueAt(intRow, x);
											strValue = intValue.toString();
											item_row.createCell(x - 1).setCellValue(strValue);
										} catch (ClassCastException ez) {
											try {
												Timestamp tmeValue = (Timestamp) model.getValueAt(intRow, x);
												strValue = tmeValue.toString();
												item_row.createCell(x - 1).setCellValue(strValue);
											} catch (ClassCastException ea) {
												try {
													Date dteValue = (Date) model.getValueAt(intRow, x);
													strValue = dteValue.toString();
													item_row.createCell(x - 1).setCellValue(strValue);
												} catch (ClassCastException eb) {

												}
											}
										}
									}
								}
							}
							//if(model.getColumnName(x).toString().trim().equals("Select") == false){
								
								
								
								
							//}
						//}
					}
				}
			}

			FileOutputStream fileOut = null;
			try {
				fileOut = new FileOutputStream(strDir + "/" + strFileName);
			} catch (FileNotFoundException e2) {
				System.out.println("");
				System.out.println("Error Line -- " + strFileName);
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

			JOptionPane.showMessageDialog(null, "Export to file successful!");

			Desktop dt = Desktop.getDesktop();
			f = new File(strDir + "/" + strFileName);

			try {
				dt.open(f);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Cannot open file.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(null, "Spreadsheet creation halted.");
		}
	}

	public static String GetString(String SQL) {
		String strValue = "";
		pgSelect sqlExec = new pgSelect();
		
		sqlExec.select(SQL);
		FncSystem.out("Value of SQL", SQL);

		if (sqlExec.isNotNull()) {
			strValue = (String) sqlExec.getResult()[0][0];
		} else {
			strValue = "";
		}
		
		return strValue;
	}
	
	public static BigDecimal GetDecimal(String SQL) {
		BigDecimal bdValue = null;
		pgSelect sqlExec = new pgSelect();
		
		sqlExec.select(SQL);

		if (sqlExec.isNotNull()) {
			bdValue = (BigDecimal) sqlExec.getResult()[0][0];
		} else {
			bdValue = null;
		}
		
		return bdValue;
	}
	
	public static Integer GetInteger(String SQL) {
		Integer intValue = 0;
		pgSelect sqlExec = new pgSelect();
		
		sqlExec.select(SQL);

		if (sqlExec.isNotNull()) {
			intValue = (Integer) sqlExec.getResult()[0][0];
		} else {
			intValue = 0;
		}
		
		return intValue;
	}
	
	public static Boolean GetBoolean(String SQL) {
		Boolean blnValue = false;
		pgSelect sqlExec = new pgSelect();
		
		FncSystem.out("Display SQL for Boolean", SQL);
		sqlExec.select(SQL);

		if (sqlExec.isNotNull() || sqlExec.getResult()[0][0].toString().equals("")) {
			blnValue = (Boolean) sqlExec.getResult()[0][0];
		} else {
			blnValue = false;
		}
		
		return blnValue;
	}
	
	public static Date GetDate(String SQL) {
		Date dteValue = null;
		pgSelect sqlExec = new pgSelect();
		
		sqlExec.select(SQL);

		if (sqlExec.isNotNull() || sqlExec.getResult()[0][0].toString().equals("")) {
			dteValue = (Date) sqlExec.getResult()[0][0];
		} else {
			dteValue = null;
		}
		
		return dteValue;
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
	
	public static Object[] isOneLogin(String dateCheck,String ipCheck){
		
		String SQL = "SELECT login_id,pwd FROM rf_audit_trail WHERE to_char(date_created, 'YYYY-MM-DD') = '"+ dateCheck +"'"
				+ "AND ip_address = '"+ ipCheck +"' AND  process_done = 'OneLogin' AND status_id = 'A' ";
		
		
		pgSelect db = new pgSelect();
		db.select(SQL);
		FncSystem.out("Onelogin", SQL);

		if(db.isNotNull()){
			return db.getResult()[0];
		}else{
			return null;
		}

	}
	
	public static Boolean isItsRealClient(String entity_id, String proj_id, String pbl_id, Integer seq_no) {
		pgSelect db = new pgSelect();
		String SQL = "SELECT EXISTS (SELECT * FROM rf_sold_unit where entity_id = '"+entity_id+"' and projcode = '"+proj_id+"' and pbl_id = '"+pbl_id+"' and seq_no = "+seq_no+" and server_id is not null)";
		db.select(SQL);
		
		
		if(db.isNotNull()) {
			return true;
		}else {
			return false;
		}
	}
	
	public static void CreateXLSwithGroup(String strCol [], String strSQL, String strFile, Integer intHead, String strHead []) {
		String strFileNo = "";
		String strFileName = "";
		String strDate = "";

		System.out.println("");
		System.out.println("Creating Spreadsheet...");

		pgSelect db = new pgSelect();
		db.select(strSQL);

		Integer row = db.getRowCount();
		Integer col = db.getColumnCount();
		Integer hd = intHead;

		Workbook wb = new HSSFWorkbook();
		Sheet sheet = wb.createSheet("new sheet");

		SimpleDateFormat sdfTo = new SimpleDateFormat("MMddyy");
		Date dateObj = new Date();
		strDate = (String)sdfTo.format(dateObj);

		/***	Cell Styles		***/
		HSSFFont font = (HSSFFont) wb.createFont();
		font.setBoldweight(font.BOLDWEIGHT_BOLD);

		HSSFCellStyle style1 = (HSSFCellStyle) wb.createCellStyle();
		style1.setFont(font);

		HSSFCellStyle style2 = (HSSFCellStyle) wb.createCellStyle();
		style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style2.setFont(font);
		/***	Cell Styles		***/


		/**		Set Directory													**/
		String strDir = _RealTimeDebit.OpenDir("Folder");

		/**		Check if file of the same name exists within the directory		**/
		Integer intSeq = 1;
		strFileNo = _RealTimeDebit.Padme(intSeq.toString(), 2);
		File f = new File(strDir + "/" + strFile + strDate + "(" + strFileNo + ")" + ".xls");

		while (f.exists() && f.isFile()) {
			System.out.println("");
			System.out.println("File exists! Number: " + strFileNo);

			intSeq++;
			strFileNo = _RealTimeDebit.Padme(intSeq.toString(), 2);
			f = new File(strDir + "/" + strFile + strDate + "(" + strFileNo + ")" + ".xls");
		}

		strFileName = strFile + strDate + "(" + strFileNo + ")" + ".xls";

		/**		Begin Creating Page Header		**/
		System.out.println("");
		for (int x = 0; x < hd; x++) {
			Row rowHead = sheet.createRow((short) x);
			rowHead.createCell(0).setCellValue(strHead [x]);
			rowHead.getCell(0).setCellStyle(style1);
			System.out.println(strHead [x]);
		}		

		/**		Begin Creating Column Header	**/
		System.out.println("");
		Row rowRow = sheet.createRow((short) (hd + 1));
		rowRow.setHeightInPoints(3 * sheet.getDefaultRowHeightInPoints());
		for (int x = 0; x < col; x++) {
			rowRow.createCell(x).setCellValue(strCol [x]);
			rowRow.getCell(x).setCellStyle(style2);
			System.out.println(strCol [x]);
		}
		
		String strAlpha = "";
		String strBravo = "";
		
		/**		Begin Creating Excel Content	**/
		if (db.isNotNull()) {
			System.out.println("");
			System.out.println("Total number of rows: " + row);

			String strValue = "";
			Integer shortInt = 0;
			Boolean blnPass = false;
			Integer intCell = 0;
			
			for (Integer intRow = 0; intRow < row; intRow++) {
				if (blnPass) {
					if (intRow > 0) {
						intRow = intRow - 1;
					} else {
						intRow = 0;
					}
				}
				
				shortInt = intCell + hd + 3;
				Short shortRow = Short.parseShort(shortInt.toString());
				Row item_row = sheet.createRow((short) shortRow);
				
				System.out.println("");
				System.out.println("strAlpha: " + strAlpha);
				System.out.println("strBravo: " + strBravo);
				System.out.println("shortInt: " + shortInt);
				System.out.println("intRow: " + intRow);
				System.out.println("name: " + (String)db.getResult()[intRow][2]);
				System.out.println("(String) db.getResult()[" + intRow + "][0]: " + (String) db.getResult()[intRow][28]);
				
				if (strAlpha.equals("") || !(Boolean) strAlpha.equals((String) db.getResult()[intRow][27])) {
					item_row.createCell(0).setCellValue((String) db.getResult()[intRow][27]);
					item_row.getCell(0).setCellStyle(style1);
					blnPass = true;
				} else {
					if (strBravo.equals("") || !(Boolean) strBravo.equals((String) db.getResult()[intRow][28])) {
						item_row.createCell(0).setCellValue((String) db.getResult()[intRow][28]);
						item_row.getCell(0).setCellStyle(style1);
						blnPass = true;
					} else {
						blnPass = false;
					}
					strBravo = (String) db.getResult()[intRow][28];
				}
				
				strAlpha = (String) db.getResult()[intRow][27];
				
				if (!blnPass) {
					for (Integer x = 0; x <= col-1; x ++) {
						try {
							item_row.createCell(x).setCellValue((String)db.getResult()[intRow][x]);
						} catch (ClassCastException ex) {
							try {
								BigDecimal bdValue = (BigDecimal) db.getResult()[intRow][x];
								strValue = bdValue.toString();
								item_row.createCell(x).setCellValue(strValue);
							} catch (ClassCastException ey) {			    		
								try {
									Integer intValue = (Integer) db.getResult()[intRow][x];
									strValue = intValue.toString();
									item_row.createCell(x).setCellValue(strValue);
								} catch (ClassCastException ez) {
									try {
										Timestamp tmeValue = (Timestamp) db.getResult()[intRow][x];
										strValue = tmeValue.toString();
										item_row.createCell(x).setCellValue(strValue);
									} catch (ClassCastException ea) {
										try {
											Date dteValue = (Date) db.getResult()[intRow][x];
											strValue = dteValue.toString();
											item_row.createCell(x).setCellValue(strValue);
										} catch (ClassCastException eb) {

										}
									}
								}

							}
						}
					}
				}
				intCell++;
			}

			FileOutputStream fileOut = null;
			try {
				fileOut = new FileOutputStream(strDir + "/" + strFileName);
			} catch (FileNotFoundException e2) {
				System.out.println("");
				System.out.println("Error Line -- " + strFileName);
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

			JOptionPane.showMessageDialog(null, "Export to file successful!");

			Desktop dt = Desktop.getDesktop();
			f = new File(strDir + "/" + strFileName);

			try {
				dt.open(f);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Cannot open file.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(null, "Spreadsheet creation halted.");
		}
	}
	
	public static void AuditLogs(String employee_code, String module_name, String activity) {
		pgSelect db = new pgSelect();
		String SQL = "select sp_user_activities('"+employee_code+"', '"+module_name+"', '"+activity+"', '')";
		db.select(SQL);
	}
}
