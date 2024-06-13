package Database;

import java.awt.Dimension;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import Functions.FncGlobal;

public class pgSelect {

	public Object[][] Result;

	private int rowCount;
	private int columnCount;

	public void call(String SQL) {
		Connection con = null;
		Statement st = null;
	
		try {
			con = DriverManager.getConnection(FncGlobal.connectionURL, FncGlobal.connectionUsername, FncGlobal.connectionPassword);
			st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			st.executeQuery(SQL); 
			
		} catch (SQLException sE) {

			return;
		} finally {

		}
	}
		
	public void select(String SQL) {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			con = DriverManager.getConnection(FncGlobal.connectionURL, FncGlobal.connectionUsername, FncGlobal.connectionPassword);
			st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			rs = st.executeQuery(SQL);

			ResultSetMetaData rsMetaData = rs.getMetaData();
			int getColumnCount = rsMetaData.getColumnCount(); // store number of columns to getColumnCount

			setColumnCount(getColumnCount);

			rs.last();
			int getRowCount = rs.getRow();
			
			//System.out.printf("Value of rowcount: %s%n", getRowCount);
			setRowCount(getRowCount);

			rs.beforeFirst();

			setResult(new Object[getRowCount][getColumnCount]);

			int x = 0;
			if(getRowCount == 0){
				setResult(null);
			}else{
				String[] columnNames = new String[getColumnCount];

				for (int i=1; i <= getColumnCount; i++){
					columnNames[i-1] = rsMetaData.getColumnName(i);
				}

				while (rs.next()){
					for(int y = 0; y < getColumnCount; y++){
						if(rs.getString(y+1)==null)
							getResult()[x][y] = null;
						else
							getResult()[x][y] = rs.getObject(y+1);
					}
					x++;	
				}
			}
		} catch (SQLException sE) {
			System.out.printf("SQL: %s%n%n", SQL);

			printWarnings(st);

			//SQL_Exception(sE);
			showException(sE);
			return;
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException ex) {
				JOptionPane.showMessageDialog(null, ex.getMessage(), ex.getMessage(), JOptionPane.WARNING_MESSAGE);

				//Logger lgr = Logger.getLogger(Version.class.getName());
				//lgr.log(Level.WARNING, ex.getMessage(), ex);
			}
		}
	}
	
	public void selectForeign(String SQL) {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			con = DriverManager.getConnection(FncGlobal.connectionURL, FncGlobal.connectionUsername, FncGlobal.connectionPassword);
			st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			rs = st.executeQuery(SQL);

			ResultSetMetaData rsMetaData = rs.getMetaData();
			int getColumnCount = rsMetaData.getColumnCount(); // store number of columns to getColumnCount

			setColumnCount(getColumnCount);

			rs.last();
			int getRowCount = rs.getRow();

			setRowCount(getRowCount);

			rs.beforeFirst();

			setResult(new Object[getRowCount][getColumnCount]);

			int x = 0;
			if(getRowCount == 0){
				setResult(null);
			}else{
				String[] columnNames = new String[getColumnCount];

				for (int i=1; i <= getColumnCount; i++){
					columnNames[i-1] = rsMetaData.getColumnName(i);
				}

				while (rs.next()){
					for(int y = 0; y < getColumnCount; y++){
						if(rs.getString(y+1)==null)
							getResult()[x][y] = null;
						else
							getResult()[x][y] = rs.getObject(y+1);
					}
					x++;	
				}
			}
		} catch (SQLException sE) {
			System.out.printf("SQL: %s%n%n", SQL);

			printWarnings(st);

			//SQL_Exception(sE);
			showException(sE);
			return;
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException ex) {
				JOptionPane.showMessageDialog(null, ex.getMessage(), ex.getMessage(), JOptionPane.WARNING_MESSAGE);

				//Logger lgr = Logger.getLogger(Version.class.getName());
				//lgr.log(Level.WARNING, ex.getMessage(), ex);
			}
		}
	}

	public void select(String SQL, String title, Boolean printWarnings) {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			con = DriverManager.getConnection(FncGlobal.connectionURL, FncGlobal.connectionUsername, FncGlobal.connectionPassword);
			st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			rs = st.executeQuery(SQL);

			ResultSetMetaData rsMetaData = rs.getMetaData();
			int getColumnCount = rsMetaData.getColumnCount(); // store number of columns to getColumnCount

			setColumnCount(getColumnCount);

			rs.last();
			int getRowCount = rs.getRow();

			setRowCount(getRowCount);

			rs.beforeFirst();

			setResult(new Object[getRowCount][getColumnCount]);

			int x = 0;
			if(getRowCount == 0){
				setResult(null);
			}else{
				String[] columnNames = new String[getColumnCount];

				for (int i=1; i <= getColumnCount; i++){
					columnNames[i-1] = rsMetaData.getColumnName(i);
				}

				while (rs.next()){
					for(int y = 0; y < getColumnCount; y++){
						if(rs.getString(y+1)==null)
							getResult()[x][y] = null;
						else
							getResult()[x][y] = rs.getObject(y+1);
					}
					x++;	
				}
			}

			if(printWarnings){
				SQLWarning w = st.getWarnings();
				// If a SQLWarning object was returned by the
				// Connection object, the warning messages are displayed.
				// You may have multiple warnings chained together
				if (w != null) {
					while (w != null) {
						String message = w.getMessage();
						Integer sql_state = Integer.parseInt(w.getSQLState());

						if(sql_state > 0){
							JOptionPane.showMessageDialog(FncGlobal.homeMDI, message, title, JOptionPane.WARNING_MESSAGE);
							/*System.out.println("Message:  " + message);
							System.out.println("SQLState: " + sql_state);
							System.out.println("VendorCode:   " + w.getErrorCode());
							System.out.println("");*/
						}
						w = w.getNextWarning();
					}
				}
			}
		} catch (SQLException sE) {
			System.out.printf("SQL: %s%n%n", SQL);

			//SQL_Exception(sE);
			showException(sE);
			return;
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException ex) {
				JOptionPane.showMessageDialog(null, ex.getMessage(), ex.getMessage(), JOptionPane.WARNING_MESSAGE);

				//Logger lgr = Logger.getLogger(Version.class.getName());
				//lgr.log(Level.WARNING, ex.getMessage(), ex);
			}
		}
	}

	public void select(String SQL, String title, Boolean printMessages, Boolean printWarnings) {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			con = DriverManager.getConnection(FncGlobal.connectionURL, FncGlobal.connectionUsername, FncGlobal.connectionPassword);
			st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			rs = st.executeQuery(SQL);

			ResultSetMetaData rsMetaData = rs.getMetaData();
			int getColumnCount = rsMetaData.getColumnCount(); // store number of columns to getColumnCount

			setColumnCount(getColumnCount);

			rs.last();
			int getRowCount = rs.getRow();

			setRowCount(getRowCount);

			rs.beforeFirst();

			setResult(new Object[getRowCount][getColumnCount]);

			int x = 0;
			if(getRowCount == 0){
				setResult(null);
			}else{
				String[] columnNames = new String[getColumnCount];

				for (int i=1; i <= getColumnCount; i++){
					columnNames[i-1] = rsMetaData.getColumnName(i);
				}

				while (rs.next()){
					for(int y = 0; y < getColumnCount; y++){
						if(rs.getString(y+1)==null)
							getResult()[x][y] = null;
						else
							getResult()[x][y] = rs.getObject(y+1);
					}
					x++;	
				}
			}

			if(printMessages){
				SQLWarning w = st.getWarnings();
				// If a SQLWarning object was returned by the
				// Connection object, the warning messages are displayed.
				// You may have multiple warnings chained together
				if (w != null) {
					while (w != null) {
						String message = w.getMessage();
						Integer sql_state = Integer.parseInt(w.getSQLState());

						if(sql_state == 0){
							JOptionPane.showMessageDialog(FncGlobal.homeMDI, message, title, JOptionPane.INFORMATION_MESSAGE);
							/*System.out.println("Message:  " + message);
							System.out.println("SQLState: " + sql_state);
							System.out.println("VendorCode:   " + w.getErrorCode());
							System.out.println("");*/
						}
						w = w.getNextWarning();
					}
				}
			}

			if(printWarnings){
				SQLWarning w = st.getWarnings();
				// If a SQLWarning object was returned by the
				// Connection object, the warning messages are displayed.
				// You may have multiple warnings chained together
				if (w != null) {
					while (w != null) {
						String message = w.getMessage();
						Integer sql_state = Integer.parseInt(w.getSQLState());

						if(sql_state > 0){
							JOptionPane.showMessageDialog(FncGlobal.homeMDI, message, title, JOptionPane.WARNING_MESSAGE);
							/*System.out.println("Message:  " + message);
							System.out.println("SQLState: " + sql_state);
							System.out.println("VendorCode:   " + w.getErrorCode());
							System.out.println("");*/
						}
						w = w.getNextWarning();
					}
				}
			}
		} catch (SQLException sE) {
			System.out.printf("SQL: %s%n%n", SQL);

			//SQL_Exception(sE);
			showException(sE);
			return;
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException ex) {
				JOptionPane.showMessageDialog(null, ex.getMessage(), ex.getMessage(), JOptionPane.WARNING_MESSAGE);

				//Logger lgr = Logger.getLogger(Version.class.getName());
				//lgr.log(Level.WARNING, ex.getMessage(), ex);
			}
		}
	}

	public void select(String SQL, String connectionURL, String connectionUsername, String connectionPassword) {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			con = DriverManager.getConnection(connectionURL, connectionUsername, connectionPassword);
			st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			rs = st.executeQuery(SQL);

			ResultSetMetaData rsMetaData = rs.getMetaData();
			int getColumnCount = rsMetaData.getColumnCount(); // store number of columns to getColumnCount

			setColumnCount(getColumnCount);

			rs.last();
			int getRowCount = rs.getRow();

			setRowCount(getRowCount);

			rs.beforeFirst();

			setResult(new Object[getRowCount][getColumnCount]);

			int x = 0;
			if(getRowCount == 0){
				setResult(null);
			}else{
				String[] columnNames = new String[getColumnCount];

				for (int i=1; i <= getColumnCount; i++){
					columnNames[i-1] = rsMetaData.getColumnName(i);
				}

				while (rs.next()){
					for(int y = 0; y < getColumnCount; y++){
						if(rs.getString(y+1)==null)
							getResult()[x][y] = null;
						else
							getResult()[x][y] = rs.getObject(y+1);
					}
					x++;	
				}
			}

		} catch (SQLException sE) {
			System.out.printf("SQL: %s%n%n", SQL);

			//SQL_Exception(sE);
			showException(sE);
			return;
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException ex) {
				JOptionPane.showMessageDialog(null, ex.getMessage(), ex.getMessage(), JOptionPane.WARNING_MESSAGE);

				//Logger lgr = Logger.getLogger(Version.class.getName());
				//lgr.log(Level.WARNING, ex.getMessage(), ex);
			}
		}
	}

	/**
	 * @author Alvin Gonzales (2016-02-02)
	 */
	public void select(String SQL, String title, String connectionUsername, String connectionPassword, Boolean printWarnings) {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			con = DriverManager.getConnection(FncGlobal.connectionURL, connectionUsername, connectionPassword);
			st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			rs = st.executeQuery(SQL);

			ResultSetMetaData rsMetaData = rs.getMetaData();
			int getColumnCount = rsMetaData.getColumnCount(); // store number of columns to getColumnCount

			setColumnCount(getColumnCount);

			rs.last();
			int getRowCount = rs.getRow();

			setRowCount(getRowCount);

			rs.beforeFirst();

			setResult(new Object[getRowCount][getColumnCount]);

			int x = 0;
			if(getRowCount == 0){
				setResult(null);
			}else{
				String[] columnNames = new String[getColumnCount];

				for (int i=1; i <= getColumnCount; i++){
					columnNames[i-1] = rsMetaData.getColumnName(i);
				}

				while (rs.next()){
					for(int y = 0; y < getColumnCount; y++){
						if(rs.getString(y+1)==null)
							getResult()[x][y] = null;
						else
							getResult()[x][y] = rs.getObject(y+1);
					}
					x++;	
				}
			}

			if(printWarnings){
				SQLWarning w = st.getWarnings();
				// If a SQLWarning object was returned by the
				// Connection object, the warning messages are displayed.
				// You may have multiple warnings chained together
				if (w != null) {
					while (w != null) {
						String message = w.getMessage();
						Integer sql_state = Integer.parseInt(w.getSQLState());

						if(sql_state > 0){
							JOptionPane.showMessageDialog(FncGlobal.homeMDI, message, title, JOptionPane.WARNING_MESSAGE);
							/*System.out.println("Message:  " + message);
							System.out.println("SQLState: " + sql_state);
							System.out.println("VendorCode:   " + w.getErrorCode());
							System.out.println("");*/
						}
						w = w.getNextWarning();
					}
				}
			}
		} catch (SQLException sE) {
			System.out.printf("SQL: %s%n%n", SQL);

			//SQL_Exception(sE);
			showException(sE);
			return;
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException ex) {
				JOptionPane.showMessageDialog(null, ex.getMessage(), ex.getMessage(), JOptionPane.WARNING_MESSAGE);

				//Logger lgr = Logger.getLogger(Version.class.getName());
				//lgr.log(Level.WARNING, ex.getMessage(), ex);
			}
		}
	}

	private void showException(Exception e) {
		StringBuilder sb = new StringBuilder("");
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

		JOptionPane.showMessageDialog(null, jsp, e.getLocalizedMessage(), JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * @return the result
	 */
	public Object[][] getResult() {
		return Result;
	}

	/**
	 * @param result the result to set
	 */
	public void setResult(Object[][] result) {
		Result = result;
	}

	public boolean isNull() {
		return Result == null;
	}

	public boolean isNotNull() {
		return Result != null;
	}




	/*********** New ***********/

	/**
	 * @return the columnCount
	 */
	public int getColumnCount() {
		return columnCount;
	}

	/**
	 * @param columnCount the columnCount to set
	 */
	public void setColumnCount(int columnCount) {
		this.columnCount = columnCount;
	}

	/**
	 * @return the rowCount
	 */
	public int getRowCount() {
		return rowCount;
	}

	/**
	 * @param rowCount the rowCount to set
	 */
	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}


	/**
	 * @Create Christian Paquibot
	 */

	public ResultSet printQuery(String SQL) {		

		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			try {
				Class.forName(FncGlobal.connectionDriver);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (con == null)
				con = DriverManager.getConnection(FncGlobal.connectionURL, FncGlobal.connectionUsername, FncGlobal.connectionPassword);


			ps = con.prepareStatement(SQL);
			rs = ps.executeQuery();

		} catch (SQLException e) {
			showException(e);
		}

		return rs;
	}

	public static void printWarnings(Statement st) {
		try {
			SQLWarning w = st.getWarnings();

			// If a SQLWarning object was returned by the
			// Connection object, the warning messages are displayed.
			// You may have multiple warnings chained together
			if (w != null) {
				while (w != null) {
					//String message = w.getMessage();
					Integer sql_state = Integer.parseInt(w.getSQLState());

					if(sql_state > 0){
						//JOptionPane.showMessageDialog(FncGlobal.homeMDI, message, title, JOptionPane.WARNING_MESSAGE);
						/*System.out.println("Message:  " + message);
						System.out.println("SQLState: " + sql_state);
						System.out.println("VendorCode:   " + w.getErrorCode());
						System.out.println("");*/
					}
					w = w.getNextWarning();
				}
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
