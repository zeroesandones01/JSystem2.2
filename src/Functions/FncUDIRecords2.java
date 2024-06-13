package Functions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class FncUDIRecords2 {
	static Connection con = null;   
	static Statement stmt;
	public static boolean iscommitted= false;

	public static void udirec(String strSQL) throws SQLException {
		try {
			Class.forName(FncGlobal.connectionDriver);
			if(con == null) {
				con = DriverManager.getConnection(FncGlobal.connectionURL);
				con.setAutoCommit(false);
				stmt = null;
				stmt = con.createStatement();
			}
			System.out.println(strSQL);
			stmt.executeUpdate(strSQL);
			iscommitted = true;
		} catch (SQLException e) {
			iscommitted  = false;
			con.rollback();
			con.close();
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "SQL Exception: " + e.toString());
		} catch (ClassNotFoundException cE) {
			iscommitted  = false;
			JOptionPane.showMessageDialog(null, "Class Not Found Exception: " + cE.toString());
		}
	}

	public static void udirec(String strSQL, Boolean toPrint) throws SQLException {
		try {
			Connection con = null;   
			Statement stmt = null;
			
			Class.forName(FncGlobal.connectionDriver);
			if(con == null && stmt == null) {
				
				System.out.printf("%s, %s, %s%n%n", FncGlobal.connectionURL, FncGlobal.connectionUsername, FncGlobal.connectionPassword);
				
				con = DriverManager.getConnection(FncGlobal.connectionURL, FncGlobal.connectionUsername, FncGlobal.connectionPassword);
				con.setAutoCommit(false);
				stmt = null;
				stmt = con.createStatement();
			}

			if(toPrint){
				System.out.println(strSQL);
			}

			stmt.executeUpdate(strSQL);
			iscommitted = true;
		} catch (SQLException e) {
			FncSystem.out("FS BOOKS", strSQL);
			
			iscommitted  = false;
			con.rollback();
			con.close();
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "SQL Exception: " + e.toString());
		} catch (ClassNotFoundException cE) {
			FncSystem.out("FS BOOKS", strSQL);
			
			iscommitted  = false;
			JOptionPane.showMessageDialog(null, "Class Not Found Exception: " + cE.toString());
		}
	}

	public static void udirec(String strSQL, Boolean toPrint, String connectionUrl) throws SQLException {
		try {
			Class.forName(FncGlobal.connectionDriver);
			if(con == null) {
				con = DriverManager.getConnection(connectionUrl);
				con.setAutoCommit(false);
				stmt = null;
				stmt = con.createStatement();
			}else{
				/*con = DriverManager.getConnection(FncGlobal.connectionURL);
            	con.setAutoCommit(false);
            	stmt = null;
            	stmt = con.createStatement();*/
			}

			if(toPrint){
				System.out.println(strSQL);
			}

			stmt.executeUpdate(strSQL);
			iscommitted = true;
		} catch (SQLException e) {
			System.out.println(strSQL);
			
			iscommitted  = false;
			con.rollback();
			con.close();
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "SQL Exception: " + e.toString());
		} catch (ClassNotFoundException cE) {
			System.out.println(strSQL);
			
			iscommitted  = false;
			JOptionPane.showMessageDialog(null, "Class Not Found Exception: " + cE.toString());
		}
	}

	public static void saveImage(String strSQL, byte[] img)  throws SQLException {
		try {
			Class.forName(FncGlobal.connectionDriver);
			if(con == null) {
				con = DriverManager.getConnection(FncGlobal.connectionURL);
				con.setAutoCommit(false);
				stmt = null;
				stmt = con.createStatement();
			}

			PreparedStatement ps = con.prepareStatement(strSQL);
			ps.setBytes(1, img);

			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			iscommitted  = false;
			con.rollback();
			con.close();
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "SQL Exception: " + e.toString());
		} catch (ClassNotFoundException cE) {
			iscommitted  = false;
			JOptionPane.showMessageDialog(null, "Class Not Found Exception: " + cE.toString());
		}
	}

	public static void disconnect() throws SQLException {
		con.close();
		con = null;
	}

	public static void commit() throws SQLException {
		con.commit();
	}

	public static void rollback(){
		try {
			con.rollback();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Connection getCon() {
		return con;
	}
}
