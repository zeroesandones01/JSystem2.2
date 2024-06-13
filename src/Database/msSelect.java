package Database;

import java.awt.Dimension;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import Functions.FncGlobal;
import Functions.FncMessagePane;


public class msSelect {

	private ArrayList<Object[]> Result;
	private int rowCount;
	private int columnCount;
	
	private Connection con;
	
	String connectionURL = "jdbc:jtds:sqlserver://177.177.177.178:1433/Cenqhomes_Summit;";
	String connectionUsername = "sa";
	String connectionPassword = "sp3c1@l1stm3";
	
	public msSelect() {
		try {
			Class.forName("net.sourceforge.jtds.jdbc.Driver");
			con = DriverManager.getConnection(connectionURL, connectionUsername, connectionPassword);
			con.setAutoCommit(false);
		} catch (SQLException e) {
			showException(e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public msSelect(String connectionURL) {
		try {
			Class.forName("net.sourceforge.jtds.jdbc.Driver");
			con = DriverManager.getConnection(connectionURL, connectionUsername, connectionPassword);
			con.setAutoCommit(false);
		} catch (SQLException e) {
			showException(e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public Connection execute(String SQL) {
		Connection con = null;
		Statement st = null;

		try {
			con = DriverManager.getConnection(connectionURL, connectionUsername, connectionPassword);

			st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			st.execute(SQL);
			
		} catch (SQLException sE) {
			sE.printStackTrace();
			FncMessagePane.showException(sE);
		} finally {
			/*try {
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
				ex.printStackTrace();
				FncMessagePane.showException(ex);
				return;
				//JOptionPane.showMessageDialog(null, ex.getMessage(), ex.getMessage(), JOptionPane.WARNING_MESSAGE);
				//Logger lgr = Logger.getLogger(Version.class.getName());
				//lgr.log(Level.WARNING, ex.getMessage(), ex);
			}*/
		}
		return con;
	}
	
	public void execute(Connection con, String SQL) {
		//Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			//con = DriverManager.getConnection(connectionURL, connectionUsername, connectionPassword);

			st = con.createStatement(/*ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY*/);
			
			st.execute(SQL);
			
		} catch (SQLException sE) {
			sE.printStackTrace();
			FncMessagePane.showException(sE);
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
				ex.printStackTrace();
				FncMessagePane.showException(ex);
				return;
				//JOptionPane.showMessageDialog(null, ex.getMessage(), ex.getMessage(), JOptionPane.WARNING_MESSAGE);
				//Logger lgr = Logger.getLogger(Version.class.getName());
				//lgr.log(Level.WARNING, ex.getMessage(), ex);
			}
		}
	}

	public void select(String SQL) {
		//Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			//con = DriverManager.getConnection(connectionURL, connectionUsername, connectionPassword);
			st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = st.executeQuery(SQL);

			ResultSetMetaData rsMetaData = rs.getMetaData();
			setColumnCount(rsMetaData.getColumnCount());

			setResult(new ArrayList<Object[]>());

			while (rs.next()) {

				ArrayList alRowData = new ArrayList();

				ResultSetMetaData rsmd = rs.getMetaData();
				int numberOfColumns = rsmd.getColumnCount();

				for(int columnIndex = 1; columnIndex <= numberOfColumns; columnIndex ++){
					alRowData.add(rs.getObject(columnIndex));
				}

				getResult().add(alRowData.toArray());
			}

			setRowCount(getResult().size());
		} catch (SQLException sE) {
			System.out.printf("SQL: %s%n", SQL);
			
			sE.printStackTrace();
			FncMessagePane.showException(sE);
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
				ex.printStackTrace();
				FncMessagePane.showException(ex);
				return;
				//JOptionPane.showMessageDialog(null, ex.getMessage(), ex.getMessage(), JOptionPane.WARNING_MESSAGE);
				//Logger lgr = Logger.getLogger(Version.class.getName());
				//lgr.log(Level.WARNING, ex.getMessage(), ex);
			}
		}
	}

	public void select(String connectionURL, String SQL) {
		//Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			con = DriverManager.getConnection(connectionURL, connectionUsername, connectionPassword);
			st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = st.executeQuery(SQL);

			ResultSetMetaData rsMetaData = rs.getMetaData();
			setColumnCount(rsMetaData.getColumnCount());

			setResult(new ArrayList<Object[]>());

			while (rs.next()) {

				ArrayList alRowData = new ArrayList();

				ResultSetMetaData rsmd = rs.getMetaData();
				int numberOfColumns = rsmd.getColumnCount();

				for(int columnIndex = 1; columnIndex <= numberOfColumns; columnIndex ++){
					alRowData.add(rs.getObject(columnIndex));
				}

				getResult().add(alRowData.toArray());
			}

			setRowCount(getResult().size());
		} catch (SQLException sE) {
			sE.printStackTrace();
			FncMessagePane.showException(sE);
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
				ex.printStackTrace();
				FncMessagePane.showException(ex);
				return;
				//JOptionPane.showMessageDialog(null, ex.getMessage(), ex.getMessage(), JOptionPane.WARNING_MESSAGE);
				//Logger lgr = Logger.getLogger(Version.class.getName());
				//lgr.log(Level.WARNING, ex.getMessage(), ex);
			}
		}
	}

	public void select(Connection con, String SQL) {
		//Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			//con = DriverManager.getConnection(connectionURL, connectionUsername, connectionPassword);

			st = con.createStatement(/*ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY*/);
			
			//st.execute(SQL);
			rs = st.executeQuery(SQL);

			ResultSetMetaData rsMetaData = rs.getMetaData();
			setColumnCount(rsMetaData.getColumnCount());

			setResult(new ArrayList<Object[]>());

			while (rs.next()) {
				ArrayList alRowData = new ArrayList();

				ResultSetMetaData rsmd = rs.getMetaData();
				int numberOfColumns = rsmd.getColumnCount();

				for(int columnIndex = 1; columnIndex <= numberOfColumns; columnIndex ++){
					alRowData.add(rs.getObject(columnIndex));
				}

				getResult().add(alRowData.toArray());
			}

			setRowCount(getResult().size());
		} catch (SQLException sE) {
			sE.printStackTrace();
			FncMessagePane.showException(sE);
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
				ex.printStackTrace();
				FncMessagePane.showException(ex);
				return;
				//JOptionPane.showMessageDialog(null, ex.getMessage(), ex.getMessage(), JOptionPane.WARNING_MESSAGE);
				//Logger lgr = Logger.getLogger(Version.class.getName());
				//lgr.log(Level.WARNING, ex.getMessage(), ex);
			}
		}
	}

	public void select3(String SQL) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DriverManager.getConnection(connectionURL, connectionUsername, connectionPassword);
			
			ps = con.prepareStatement(SQL);
			ps.executeUpdate();

			rs = ps.getGeneratedKeys();
			
			setResult(new ArrayList<Object[]>());

			while (rs.next()) {

				ArrayList alRowData = new ArrayList();

				ResultSetMetaData rsmd = rs.getMetaData();
				int numberOfColumns = rsmd.getColumnCount();

				for(int columnIndex = 1; columnIndex <= numberOfColumns; columnIndex ++){
					alRowData.add(rs.getObject(columnIndex));
				}

				getResult().add(alRowData.toArray());
			}

			ResultSetMetaData rsMetaData = rs.getMetaData();
			setColumnCount(rsMetaData.getColumnCount());
			
			setRowCount(getResult().size());
		} catch (SQLException sE) {
			sE.printStackTrace();
			FncMessagePane.showException(sE);
			return;
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
				FncMessagePane.showException(ex);
				return;
				//JOptionPane.showMessageDialog(null, ex.getMessage(), ex.getMessage(), JOptionPane.WARNING_MESSAGE);
				//Logger lgr = Logger.getLogger(Version.class.getName());
				//lgr.log(Level.WARNING, ex.getMessage(), ex);
			}
		}
	}

	public void select4(String SQL) {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			con = DriverManager.getConnection(connectionURL, connectionUsername, connectionPassword);
			
			st = con.createStatement();

			rs = st.executeQuery(SQL);
			
			//setResult(new ArrayList<Object[]>());

			while (rs.next()) {

				/*ArrayList alRowData = new ArrayList();

				ResultSetMetaData rsmd = rs.getMetaData();
				int numberOfColumns = rsmd.getColumnCount();

				for(int columnIndex = 1; columnIndex <= numberOfColumns; columnIndex ++){
					alRowData.add(rs.getObject(columnIndex));
				}

				getResult().add(alRowData.toArray());*/
			}

			//ResultSetMetaData rsMetaData = rs.getMetaData();
			//setColumnCount(rsMetaData.getColumnCount());
			
			//setRowCount(getResult().size());
		} catch (SQLException sE) {
			sE.printStackTrace();
			FncMessagePane.showException(sE);
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
				ex.printStackTrace();
				FncMessagePane.showException(ex);
				return;
				//JOptionPane.showMessageDialog(null, ex.getMessage(), ex.getMessage(), JOptionPane.WARNING_MESSAGE);
				//Logger lgr = Logger.getLogger(Version.class.getName());
				//lgr.log(Level.WARNING, ex.getMessage(), ex);
			}
		}
	}
	
	public void close() {
		
	}

	/**
	 * @return the result
	 */
	public ArrayList<Object[]> getResult() {
		return Result;
	}

	/**
	 * @return the result
	 */
	public Object[] result(int column) {
		return Result.get(column);
	}

	/**
	 * @param result the result to set
	 */
	public void setResult(ArrayList<Object[]> result) {
		Result = result;
	}

	public boolean isNotNull() {
		return (Result != null && Result.size() > 0);
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
