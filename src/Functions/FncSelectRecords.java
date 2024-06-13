package Functions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class FncSelectRecords {

	public static Connection con = null;
	public static String SQL;
	public static ResultSet rs = null;
	public static Statement stmt;
	public static boolean AUTO_DISCONNECT = true;

	public static void select(String SQL) {
		try {
			if (con == null) {
				con = DriverManager.getConnection(FncGlobal.connectionURL);
			}

			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			//stmt2 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

			System.out.println(SQL);

			rs = stmt.executeQuery(SQL);
			// rs2 = stmt2.executeQuery(SQL);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("SQL Exception: " + e.toString());
			//JOptionPane.showMessageDialog(null, e.toString());
		}
	}

	//03/16/12 - added new parameter boolean for system.out.print purposes
	public static void select(String SQL, Boolean toPrint) {
		try {
			if (con == null ) {
				con = DriverManager.getConnection(FncGlobal.connectionURL);
			}

			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

			if(toPrint)
				System.out.println(SQL);

			rs = stmt.executeQuery(SQL);
			// rs2 = stmt2.executeQuery(SQL);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("SQL Exception: " + e.toString());
			//JOptionPane.showMessageDialog(null, e.toString());
		}
	}
	
	public static void selectV2(String SQL, Boolean toPrint) {
		try {
			if (con == null ) {
				con = DriverManager.getConnection(FncGlobal.connectionURL, FncGlobal.connectionUsername, FncGlobal.connectionPassword);
			}

			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

			if(toPrint)
				System.out.println(SQL);

			rs = stmt.executeQuery(SQL);
			// rs2 = stmt2.executeQuery(SQL);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("SQL Exception: " + e.toString());
			//JOptionPane.showMessageDialog(null, e.toString());
		}
	}

	//10/10/12 - added new parameter boolean for system.out.print purposes
	public static void select(String SQL, Boolean toPrint, String connectionUrl) {
		try {
			if (con == null ) {
				con = DriverManager.getConnection(connectionUrl);
			}else{
				con = DriverManager.getConnection(connectionUrl);
			}

			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

			if(toPrint)
				System.out.println(SQL);

			rs = stmt.executeQuery(SQL);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("SQL Exception: " + e.toString());
		}
	}

	public static void disconnect() {
		if (!AUTO_DISCONNECT)
			return;

		try {
			rs.close();
			rs = null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			con.close();
			con = null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

