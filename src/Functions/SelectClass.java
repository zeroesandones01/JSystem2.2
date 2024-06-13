package Functions;

// do not delete function for client request
// recto

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Date;

import javax.swing.JOptionPane;

public class SelectClass extends Functions.FncSelectRecords {

	public String[][] Result = new String [][]{};
	public int getRowCount;
	public int getColumnCount;
	public Date getDate;
	public String [] columnNames; // var to save name of columns;
	private String sql;

	public String getQuerySql() {
		return sql;
	}

	public void close(){
		disconnect();
	}

	public boolean setQuerySql(String SQL){
		boolean ret = false;
		try {
			connectThis(SQL, false, null, true, FncGlobal.connectionURL);
			ret = true;
		} catch (SQLException e) {
			ret = false;
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "An Error Occured During Processing your Transaction.\nError : \n"+e.getMessage());
		}
		return ret;
	}

	//03/16/12 - added new parameter boolean for system.out.print purposes
	public boolean setQuerySql(String SQL, Boolean toPrint){
		boolean ret = false;
		try {
			connectThis(SQL, toPrint, null, true, FncGlobal.connectionURL);
			ret  = true;
		} catch (SQLException e) {
			ret  = false;
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "An Error Occured During Processing your Transaction.\nError : \n"+e.getMessage());
		}
		return ret;
	}

	//04/20/12 - added new parameter boolean for system.out.print purposes
	public boolean setQuerySql(String SQL, Boolean toPrint, Boolean toDisconnect){
		boolean ret = false;
		try {
			connectThis(SQL, toPrint, null, toDisconnect, FncGlobal.connectionURL);
			ret  = true;
		} catch (SQLException e) {
			ret  = false;
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "An Error Occured During Processing your Transaction.\nError : \n"+e.getMessage());
		}
		return ret;
	}

	//03/16/12 - added new parameter boolean for system.out.print purposes
	public boolean setQuerySql(String SQL, Boolean toPrint, String errorMessage, String titleMessage, Integer typeMessage){
		boolean ret = false;
		try {
			connectThis(SQL, toPrint, errorMessage, true, FncGlobal.connectionURL);
			ret  = true;
		} catch (SQLException e) {
			ret  = false;
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, errorMessage, titleMessage, typeMessage);
		}
		return ret;
	}

	//10/10/12 - added new parameter boolean for system.out.print purposes
	public boolean setQuerySql(String SQL, Boolean toPrint, String connectionUrl){
		boolean ret = false;
		try {
			connectThis(SQL, toPrint, null, true, connectionUrl);
			ret  = true;
		} catch (SQLException e) {
			ret  = false;
			e.printStackTrace();
			//JOptionPane.showMessageDialog(null, null, titleMessage, typeMessage);
		}
		return ret;
	}

	public Date getCurrentDate(){
		Date date = null;
		try {
			select("select current_date", false);

			ResultSetMetaData rsMetaData;			
			rsMetaData = rs.getMetaData();
			getColumnCount = rsMetaData.getColumnCount(); // store number of columns to getColumnCount
			rs.last();		
			getRowCount = rs.getRow();
			rs.beforeFirst();

			while (rs.next()){
				date = rs.getDate(1);
			}

			disconnect();	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return date;
	}

	public boolean isNull(){
		if (getRowCount==0)
			return true;
		return false;
	}

	protected void connectThis(String SQL, Boolean toPrint, String errorMessage, Boolean toDisconnect, String connectionUrl) throws SQLException {
		this.sql = SQL;

		if(FncGlobal.connectionURL.equals(connectionUrl)){
			select(SQL, toPrint);
		}else{
			select(SQL, toPrint, connectionUrl);
		}

		ResultSetMetaData rsMetaData = rs.getMetaData();
		getColumnCount = rsMetaData.getColumnCount(); // store number of columns to getColumnCount
		rs.last();		
		getRowCount = rs.getRow();
		rs.beforeFirst();

		//countSQL(SQL);

		Result = new String [getRowCount][getColumnCount];
		int x =0;

		if (getRowCount==0){
			//cinoment q muna,, actually , dpat pala,, wala tong laman pag zero ang rows
			Result = new String [1][getColumnCount];

			for (int y = 0;y < getColumnCount;y++){
				Result [x][y] = "";
			}
			//Result = null;
		}else{
			columnNames = new String[getColumnCount];

			for (int i =1;i<=getColumnCount;i++){
				columnNames[i-1] = rsMetaData.getColumnName(i);
			}

			while (rs.next()){
				try{
					getDate = rs.getDate(1);
				} catch (Exception e){
				}

				for (int y = 0;y < getColumnCount;y++){
					if (rs.getString(y+1)==null) {
						Result[x][y] = "";
					}else{
						Result[x][y] = rs.getString(y+1).trim();
					}
				}
				x++;	
			}
			if(toDisconnect){
				disconnect();	
			}
		} 
	}

	/*private void countSQL(String SQl1){
		select(SQl1);

		ResultSetMetaData rsMetaData;
		try {
			rsMetaData = rs.getMetaData();
			getColumnCount = rsMetaData.getColumnCount();

		} catch (SQLException e1) {
			e1.printStackTrace();
		}catch(NullPointerException ne) {
			System.out.println("error : lage : " + SQl1);
			ne.printStackTrace();
		}

		int count = 0;
		try {
			while (rs.next()){
				count++;
			}
			disconnect();
		} catch (SQLException e) {
			e.printStackTrace();
		}catch (NullPointerException ne){
			ne.printStackTrace();
		}
		getRowCount = count;
	}*/
}
