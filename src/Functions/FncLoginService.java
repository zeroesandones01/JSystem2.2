package Functions;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.jdesktop.swingx.auth.LoginService;

import Database.pgSelect;
import Lookup._JSearch;

public class FncLoginService extends LoginService {

	@Override
	public boolean authenticate(String username, char[] password, String server) throws Exception {
		System.out.println("1st Authentication");
		boolean isSuccessful = login(username, new String(password), server);
		//System.out.printf("Value of successful before postgresq: %s%n", isSuccessful);
		System.out.println("2nd Authentication");
		
		if(username.equals("postgres")){
			//JOptionPane.showMessageDialog(this.getTop, message, title, messageType);
			isSuccessful = false;
			JOptionPane.showMessageDialog(null, "User does not exist", "Log-in", JOptionPane.WARNING_MESSAGE);
			//System.out.printf("Value of is successful after postgres: %s%n", isSuccessful);
		}
		
		System.out.printf("Login success: %s%n", isSuccessful);
		
		if(isSuccessful){
			//FncGlobal.initializeMaps();
			//System.out.printf("Display Value of server:%s%n", server);
			
			UserInfo.initializeEmployeeInfo(username);

			try {
				Home.Home_JSystem home = new Home.Home_JSystem(FncGlobal.ORIGINAL_TITLE);

				FncGlobal.homeMDI = home;
				FncGlobal.search = new _JSearch(FncGlobal.homeMDI, "Search");
				FncGlobal.homeMDI.setResizable(true);
				FncGlobal.homeMDI.setExtendedState(JFrame.MAXIMIZED_BOTH);
				home.setLocationRelativeTo(null);
				home.setVisible(true);

				/**
				 * @author Alvin Gonzales (2016-03-10) - Setting of Employees Look and Feel
				 */
				String SQL = "SELECT * FROM em_look_and_feel WHERE emp_code = '"+ UserInfo.EmployeeCode +"';";
				//System.out.println(SQL);

				pgSelect db = new pgSelect();
				db.select(SQL);
				if(db.isNotNull()){
					Integer red = (Integer) db.getResult()[0][4];
					Integer green = (Integer) db.getResult()[0][5];
					Integer blue = (Integer) db.getResult()[0][6];

					if((red == 0) && (green == 93) && (blue == 168)){
						FncLookAndFeel.setDefaultColor();
					}else{
						FncLookAndFeel.setColor(red, green, blue);
					}
				}else{
					FncLookAndFeel.setDefaultColor();
				}
				
				String SQL3 = "SELECT * FROM mf_audit_trail where activity = 'LOGGED IN' AND user_code = '"+UserInfo.EmployeeCode+"'";
				db.select(SQL3);
				
				if(db.isNull()) {
					System.out.println("Dumaan dito sa Open");
					openBeginnersManual();
				}
				
				String SQL2 = "SELECT sp_audit_log_details('"+UserInfo.EmployeeCode+"', true)";
				db.select(SQL2);
				//XXX PUT AUDIT TRAIL HERE FOR LOG IN 

			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		return isSuccessful;
	}
	
	private void openBeginnersManual() {
		if (Desktop.isDesktopSupported()) {
			InputStream jarPdf = getClass().getClassLoader().getResourceAsStream("File/JSystem Beginners Guide.pdf");

			try {
				File pdfTemp = File.createTempFile("User_Guide", ".pdf");
				pdfTemp.deleteOnExit();

				FileOutputStream fos = new FileOutputStream(pdfTemp);
				while (jarPdf.available() > 0) {
					fos.write(jarPdf.read());
				} // while (pdfInJar.available() > 0)
				fos.close();
				Desktop.getDesktop().open(pdfTemp);
			} // try

			catch (IOException ex) {
			} // catch (IOException e)
		}
	}

	private boolean login(String username, String password, String server) {
		try {
			FncGlobal.server = server;

			FncGlobal.connectionDriver = FncGlobal.DRIVER;
			FncGlobal.connectionUsername = username;
			FncGlobal.connectionPassword = password;
			FncGlobal.connectionURL = FncGlobal.mapURL.get(server);
			//System.out.printf("PAssword: %s%n", FncGlobal.connectionPassword);
			Class.forName(FncGlobal.DRIVER);
			FncGlobal.connection = DriverManager.getConnection(FncGlobal.mapURL.get(server), username, password);
			  DatabaseMetaData metaData = FncGlobal.connection.getMetaData();

	            // Retrieving and printing JDBC version
	            int majorVersion = metaData.getJDBCMajorVersion();
	            int minorVersion = metaData.getJDBCMinorVersion();
	            System.out.println("JDBC Version: " + majorVersion + "." + minorVersion);
			
			pgSelect db = new pgSelect();
			db.select("SELECT current_schema(), inet_server_addr();");
			System.out.printf("Current Schema: %s%n", db.getResult()[0][0]);

			String schema_date = ((String) db.getResult()[0][0]).replace("openhouse_", "");
			Object ip_address = db.getResult()[0][1];

			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
			try {
				Date sDate = formatter.parse(schema_date);

				FncGlobal.ORIGINAL_TITLE = String.format("%s @ %s (%s)", FncGlobal.ORIGINAL_TITLE, FncGlobal.server, new SimpleDateFormat("yyyy-MM-dd").format(sDate));
			} catch (ParseException e) {
				FncGlobal.ORIGINAL_TITLE = String.format("%s @ %s (%s)", FncGlobal.ORIGINAL_TITLE, FncGlobal.server, ip_address);
			}

			return true;
		} catch (SQLException e) {
			//e.printStackTrace();
			System.out.printf("Error Code: %s%n%n%n", e.getSQLState());

			if(e.getSQLState().equals("08001")){ // SQL client unable to establish SQL connection
				FncMessagePane.showSQLException(e);
			}else if (e.getSQLState().equals("28P01")){
				//FncMessagePane.showSQLException(e);
			}else{
				System.out.println("Dumaan dito sa exception");
				FncMessagePane.showException(e);
			}

			return false;
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
			return false;
		}
	}

}
