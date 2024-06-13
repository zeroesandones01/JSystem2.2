package tablemodel;

import java.math.BigDecimal;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class modelJV_acct_details extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8738810102880371204L;

	public modelJV_acct_details() {
		initThis();
	}

	public modelJV_acct_details(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelJV_acct_details(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelJV_acct_details(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelJV_acct_details(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelJV_acct_details(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			"Entry No", 	// 0
			"Account ID", 	// 1
			"Account Name", // 2
			"Debit", 		// 3
			"Credit", 		// 4
			"Project", 		// 5
			"Phase",		// 6
			"Div",			// 7
			"Dept.", 		// 8
			"Description ", // 9
			"OR No.", 		// 10
			"AR No.", 		// 11
			"Status ", 		// 12
			"Unit PBL ", 	// 13
			"PCF No.", 		// 14
			"DRF No.", 		// 15
			"CV No.", 		// 16
			"Liq No." 		// 17
	};

	
	/**
	 * String.class = Alignment.CENTER
	 * Object.class = Alignment.LEFT
	 * BigDecimal.class = Alignment.RIGHT
	 * Integer.class = Alignment.CENTER
	 * Timestamp.class
	 */
	public Class[] CLASS_TYPES = new Class[] {
			Integer.class, 		//Line No.
			String.class, 		//Account ID
			Object.class, 		//Account Name
			BigDecimal.class, 	//Debit
			BigDecimal.class, 	//Credit
			String.class, 		//Project
			String.class, 		//Phase
			String.class,		//Div
			String.class,		//Dept.
			String.class,		//Description
			String.class,		//OR No.
			String.class,		//AR No.
			String.class,		//Status
			String.class,		//Unit PBL
			String.class,		//PCF No.
			String.class,		//DRF No.
			String.class,		//CV No.
			String.class		//Liq No.
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

}
