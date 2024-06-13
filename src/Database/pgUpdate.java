package Database;

import java.awt.Dimension;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import Functions.FncGlobal;

public class pgUpdate {

	private Connection con;
	private Statement st;

	public pgUpdate() {
		try {
			con = DriverManager.getConnection(FncGlobal.connectionURL, FncGlobal.connectionUsername, FncGlobal.connectionPassword);
			con.setAutoCommit(false);
		} catch (SQLException e) {
			showException(e);
		}
	}

	public void executeUpdate(String strSQL, Boolean toPrint){
		try {
			if(toPrint)
				System.out.println(strSQL);

			st = con.createStatement();
			st.executeUpdate(strSQL);
			st.close();

		} catch (SQLException e) {
			System.out.println(strSQL);
			
			e.printStackTrace();
			showException(e);
		}
	}
	
	public void executeUpdate(String strSQL, Boolean toPrint, String title, Boolean printWarnings) {
		try {
			if(toPrint)
				System.out.println(strSQL);

			st = con.createStatement();
			st.executeUpdate(strSQL);
			
			if(printWarnings){
				SQLWarning warnings = st.getWarnings();

				if(warnings != null){
					JOptionPane.showMessageDialog(FncGlobal.homeMDI, warnings.getMessage(), title, JOptionPane.WARNING_MESSAGE);

					try {
						for(Throwable warning : warnings.getNextWarning()){
							JOptionPane.showMessageDialog(FncGlobal.homeMDI, warning.getMessage(), title, JOptionPane.WARNING_MESSAGE);
						}
					} catch (NullPointerException e) { }
				}
			}
			
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
			showException(e);
		}
	}

	public void executeUpdate(String strSQL, Boolean toPrint, Boolean toCommit) {
		try {
			if(toPrint)
				System.out.println(strSQL);

			st = con.createStatement();
			st.executeUpdate(strSQL);
			st.close();

			if(toCommit){
				commit();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			showException(e);
		}
	}

	public void commit() {
		try {
			con.commit();
			con.close();
		} catch (SQLException e) {
			showException(e);
		}
	}

	public void rollback(){
		try {
			con.rollback();
		} catch (SQLException e) {
			showException(e);
		}
	}

	public static void showException(Exception e) {
		StringBuilder sb = new StringBuilder();
		sb.append(e.getMessage());
		sb.append("\n");

		for (StackTraceElement ste : e.getStackTrace()) {
			sb.append(ste.toString());
			sb.append("\n");
		}

		JTextArea jta = new JTextArea(sb.toString());
		jta.setEditable(false);

		JScrollPane jsp = new JScrollPane(jta);
		jsp.setPreferredSize(new Dimension(500, 250));

		JOptionPane.showMessageDialog(FncGlobal.homeMDI, jsp, e.getLocalizedMessage(), JOptionPane.ERROR_MESSAGE);
	}

}
