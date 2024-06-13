package tablemodel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelAcctgPeriod extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8738810102880371204L;

	public modelAcctgPeriod() {
		initThis();
	}

	public modelAcctgPeriod(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelAcctgPeriod(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelAcctgPeriod(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelAcctgPeriod(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelAcctgPeriod(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			"No.", 		// 0
			"Year", 	//1
			"Month", 	//2
			"Status", 	//3 
			"Date Opened", // 4
			"Date Closed", // 5
			"Opened by", //6
			"Closed by"  //7
			
	};
	
	public Class[] CLASS_TYPES = new Class[] {
			Integer.class,//No.
			String.class, //Year
			String.class, //Month
			String.class, //Status
			Timestamp.class,//Date Opened
			Timestamp.class,//Date Closed
			Object.class,    //Opened by
			Object.class    //Closed by
			
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
	}

	public Class getColumnClass(int columnIndex) {
		return CLASS_TYPES[columnIndex];
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}
	public void clear(){
		FncTables.clearTable(this);
	}

}
