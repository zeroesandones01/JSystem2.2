package tablemodel;

import java.math.BigDecimal;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Lookup._JLookup;

public class modelPVaccount_entries	 extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8738810102880371204L;
	
	private boolean editable = false;
	boolean[] COLUMN_EDITABLE;
	
	
	public modelPVaccount_entries() {
		initThis();
	}

	public modelPVaccount_entries(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelPVaccount_entries(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelPVaccount_entries(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelPVaccount_entries(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelPVaccount_entries(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			" Account ID  " ,	// 0
			"Div",				// 1
			"Dept",				// 2
			"Sec",				// 3
			"Proj",				// 4
			"Sub",       		// 5	
			"Account Desc", 	// 6
			"Debit",			// 7
			"Credit",			// 8
			"div",	// 9
			"dep",	// 10
			"proj",	// 11
			"sub",	// 12
			"availer"	// 13
			};

	
	/**
	 * String.class = Alignment.CENTER
	 * Object.class = Alignment.LEFT
	 * BigDecimal.class = Alignment.RIGHT
	 * Integer.class = Alignment.CENTER
	 * Timestamp.class
	 */
	public Class[] CLASS_TYPES = new Class[] {
			String.class, 		//Account ID
			_JLookup.class,		//Div
			_JLookup.class, 	//Dept
			String.class, 		//Sec
			_JLookup.class,		//Proj
			_JLookup.class, 	//Sub	
			Object.class, 		//Account Desc
			BigDecimal.class,	//Debit
			BigDecimal.class,	//Credit
			String.class, 		// Ref No.
			String.class, 		// Ref No.
			String.class, 		// Ref No.
			String.class, 		// Ref No.
			String.class 		// Ref No.
			
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
				false, 	//Account Desc
				false,	//Debit
				false,	//Proj
				false, 	//Sub	
				false, 	//Account Desc
				false,	//Debit
				false,	//Credit
				false	//Credit
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
					false,	//Div
					false, 	//Dept
					false, 	//Sec
					false,	//Proj
					false, 	//Sub	
					false, 	//Account Desc
					false,	//Debit
					false, 	//Sec
					false,	//Proj
					false, 	//Sub	
					false, 	//Account Desc
					false,	//Debit
					false	//Credit
			};
		}else{
			COLUMN_EDITABLE = new boolean[] {
					false, 	//Account ID
					false,	//Div
					false, 	//Dept
					false, 	//Sec
					false,	//Proj
					false, 	//Sub	
					false, 	//Account Desc
					false,	//Debit
					false, 	//Sec
					false,	//Proj
					false, 	//Sub	
					false, 	//Account Desc
					false,	//Debit
					false	//Credit
			};
		}
	}

	
	
	
}
