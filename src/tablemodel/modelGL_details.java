package tablemodel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class modelGL_details extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8738810102880371204L;
	
	private boolean editable = false;
	boolean[] COLUMN_EDITABLE;
	
	
	public modelGL_details() {
		initThis();
	}

	public modelGL_details(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelGL_details(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelGL_details(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelGL_details(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelGL_details(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			"Tran Date" ,	// 1
			"Description" ,	// 2
			"Div",
			"Dept",
			"Proj",
			"Sub",
			"Debit" ,		// 3
			"Credit" ,		// 4
			"Running Balance " ,	// 5
			"JV No." ,		// 6
			"CV No." ,		// 7
			"PV No." ,		// 8
			"OR No.", 		// 9
			"AR No.", 		// 10
			"PFR No.", 		// 11
			"SI No.", 		// 12
			"Remarks", 		// 13
			"Status",		// 14
			"Payee"
			};

	
	/**
	 * String.class = Alignment.CENTER
	 * Object.class = Alignment.LEFT
	 * BigDecimal.class = Alignment.RIGHT
	 * Integer.class = Alignment.CENTER
	 * Timestamp.class
	 */
	public Class[] CLASS_TYPES = new Class[] {
			Timestamp.class, 	//Tran Date
			String.class,		//Description
			String.class, 		//div
			String.class, 		//dept
			String.class, 		//proj
			String.class, 		//subproj
			BigDecimal.class, 	//Debit
			BigDecimal.class, 	//Credit
			BigDecimal.class,	//Running Balance
			String.class, 		//JV No.
			String.class, 		//CV No.
			String.class, 		//PV No.
			String.class,		//OR No.
			String.class,		//AR No.
			String.class,		//PFR No.
			String.class,		//SI No.
			Object.class,		//Remarks
			String.class,		//Status
			Object.class
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
		COLUMN_EDITABLE = new boolean[] {
				false, 		//Tran Date
				false,		//Description
				false, 		//div
				false, 		//dept
				false, 		//proj
				false, 		//subproj
				false, 		//Debit
				false, 		//Credit
				false,		//Running Balance
				false, 		//JV No.
				false, 		//CV No.
				false, 		//PV No.
				false,		//OR No.
				false,		//AR No.
				false,		//PFR No.
				false,		//SI No.
				false,		//Remarks
				false,		//Status
				false
		};
	}

	public Class getColumnClass(int columnIndex) {
		return CLASS_TYPES[columnIndex];
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return COLUMN_EDITABLE[columnIndex];
	}

	public boolean isEditable() {
		return editable;
	}

	

	
	
	
}