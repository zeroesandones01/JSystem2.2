package tablemodel;

import java.math.BigDecimal;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Lookup._JLookup;

public class modelJV_acct_entries	 extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8738810102880371204L;
	
	private boolean editable = false;
	boolean[] COLUMN_EDITABLE;
	
	
	public modelJV_acct_entries() {
		initThis();
	}

	public modelJV_acct_entries(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelJV_acct_entries(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelJV_acct_entries(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelJV_acct_entries(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelJV_acct_entries(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			" Account ID  " ,	// 0
			"Div",				// 1
			"Dept",				// 2
			"Sec",				// 3
			"Proj",				// 4
			"Sub",       		// 5	
			"IBU",       		// 6
			"Account Desc", 	// 7
			"Debit",			// 8
			"Credit",			// 9
			"Reference No.",		// 10
			"div",				// 11
			"dep",				// 12
			"proj",				// 13
			"sub",				// 14
			"Entry No.",		// 15
			"Entity ID",		// 16
			"PBL ID",			// 17
			"ENTITY NAME"
			};

	
	/**
	 * String.class = Alignment.CENTER
	 * Object.class = Alignment.LEFT
	 * BigDecimal.class = Alignment.RIGHT
	 * Integer.class = Alignment.CENTER
	 * Timestamp.class
	 */
	public Class[] CLASS_TYPES = new Class[] {
			_JLookup.class, 	//Account ID
			String.class,		//Div
			String.class, 	//Dept
			String.class, 		//Sec
			String.class,		//Proj
			String.class, 	//Sub	
			_JLookup.class,     //IBU
			Object.class, 		//Account Desc
			BigDecimal.class,	//Debit
			BigDecimal.class,	//Credit
			String.class, 		//Reference No.
			String.class, 		//Reference No.
			String.class, 		//Reference No.
			String.class, 		//Reference No.
			String.class, 		//Reference No.
			Integer.class,
			_JLookup.class, 		//Entity ID
			_JLookup.class, 		//PBL ID
			String.class, 		//Entity Name
			
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
		COLUMN_EDITABLE = new boolean[] {
				false, 	//Account ID
				false,	//Div
				false, 	//Dept
				false, 	//Sec
				false,	//Proj
				false, 	//Sub	
				false,   //IBU
				false, 	//Account Desc
				false,	//Debit
				false,	//Credit
				false,   //IBU
				false, 	//Account Desc
				false,	//Debit
				false,	//Credit
				false, 	//Reference No.
				false, 	//Reference No.
				false, 	//Entity ID
				false, 	//PBL ID
				false 	//ENTITY NAME
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

	public void setEditable(boolean editable) {
		this.editable = editable;
		if(editable){
			COLUMN_EDITABLE = new boolean[] {
					false, 	//Account ID
					true,	//Div
					true, 	//Dept
					false, 	//Sec
					true,	//Proj
					true, 	//Sub	
					false,  //IBU
					false, 	//Account Desc
					true,	//Debit
					true,	//Credit
					true, 	//Reference No.
					false,   //IBU
					false, 	//Account Desc
					false,	//Debit
					false,	//Credit
					false, 	//Reference No.
					false, 	//Entity ID
					false, 	//PBL ID
					false 	//ENTITY NAME
			};
		}else{
			COLUMN_EDITABLE = new boolean[] {
					false, 	//Account ID
					false,	//Div
					false, 	//Dept
					false, 	//Sec
					false,	//Proj
					false, 	//Sub	
					false,  //IBU
					false, 	//Account Desc
					false,	//Debit
					false,	//Credit
					false,   //IBU
					false, 	//Account Desc
					false,	//Debit
					false,	//Credit
					false, 	//Reference No.
					false, 	//Reference No.
					false, 	//Entity ID
					false, 	//PBL ID
					false 	//ENTITY NAME
			};
		}
	}

	
	
	
}
