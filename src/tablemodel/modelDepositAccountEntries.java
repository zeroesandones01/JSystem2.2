package tablemodel;

import java.math.BigDecimal;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class modelDepositAccountEntries extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8738810102880371204L;
	
	private boolean editable = false;
	boolean[] COLUMN_EDITABLE;
	
	
	public modelDepositAccountEntries() {
		initThis();
	}

	public modelDepositAccountEntries(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelDepositAccountEntries(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelDepositAccountEntries(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelDepositAccountEntries(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelDepositAccountEntries(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			"Account ID",			// 1
			"Div",					// 2
			"Dept",					// 3
			"Sect",					// 4
			"Proj",					// 5
			"SProj",				// 6
			"Account Description",	// 7
			"Debit",				// 8
			"Credit"				// 9
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
			String.class,		//Div
			String.class,		//Dept
			String.class,		//Sect
			String.class,		//Proj
			String.class,		//SProj
			Object.class,		//Account Description
			BigDecimal.class,	//Debit
			BigDecimal.class	//Credit
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
		COLUMN_EDITABLE = new boolean[] {				
				false, 		//Account ID
				false,		//Div
				false, 		//Dept
				false,		//Sect
				false, 		//Proj
				false, 		//SProj
				false, 		//Account Description
				false, 		//Debit
				false 		//Credit
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
					false, 		//Account ID
					false,		//Div
					false, 		//Dept
					false,		//Sect
					false, 		//Proj
					false, 		//SProj
					false, 		//Account Description
					false, 		//Debit
					false 		//Credit
			};
		}else{
			COLUMN_EDITABLE = new boolean[] {
					false, 		//Account ID
					false,		//Div
					false, 		//Dept
					false,		//Sect
					false, 		//Proj
					false, 		//SProj
					false, 		//Account Description
					false, 		//Debit
					false 		//Credit
			};
		}
	}

	
	
	
}
