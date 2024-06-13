package tablemodel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Lookup._JLookup;

public class modelUnidentified_deposits	 extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8738810102880371204L;
	
	private boolean editable = false;
	boolean[] COLUMN_EDITABLE;
	
	
	public modelUnidentified_deposits() {
		initThis();
	}

	public modelUnidentified_deposits(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelUnidentified_deposits(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelUnidentified_deposits(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelUnidentified_deposits(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelUnidentified_deposits(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {		
			"*Deposit Date",		// 1
			"*Amount",			// 2
			"*Bank Acct. ID",	// 3
			"Account No.",		// 4
			"Bank Alias",		// 5	
			"Record No.",		// 6
			"Created By",		// 7
			"Created Date",		// 8
			"Tagged By",		// 9
			"Tagged Date",		// 10
			"Edited By",		// 11
			"Edited Date"		// 12
			};

	
	/**
	 * String.class = Alignment.CENTER
	 * Object.class = Alignment.LEFT
	 * BigDecimal.class = Alignment.RIGHT
	 * Integer.class = Alignment.CENTER
	 * Timestamp.class
	 */
	public Class[] CLASS_TYPES = new Class[] {			
			Timestamp.class,	//Deposit Date
			BigDecimal.class, 	//Amount
			_JLookup.class, 	//Account ID
			String.class,		//Account No.
			String.class,		//Bank Alias
			String.class,		//Record No.
			String.class,		//Created By
			Timestamp.class,	//Created Date
			String.class,		//Tagged By
			Timestamp.class,	//Tagged Date
			String.class,		//Edited By
			Timestamp.class		//Edited Date
			
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
		COLUMN_EDITABLE = new boolean[] {				
				false,	//Deposit Date
				false, 	//Amount
				false, 	//Account ID
				false, 	//Account No.
				false,	//Bank Alias
				false,	//Record No.
				false,	//Created By
				false,	//Created Date
				false,	//Tagged By
				false,	//Tagged Date
				false,	//Edited By
				false	//Edited Date
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
					false,	//Deposit Date
					true, 	//Amount
					false, 	//Account ID
					false, 	//Account No.
					false,	//Bank Alias
					false,	//Record No.
					false,	//Created By
					false,	//Created Date
					false,	//Tagged By
					false,	//Tagged Date
					false,	//Edited By
					false	//Edited Date
			};
		}else{
			COLUMN_EDITABLE = new boolean[] {
					false,	//Deposit Date
					false, 	//Amount
					false, 	//Account ID
					false, 	//Account No.
					false,	//Bank Alias
					false,	//Record No.
					false,	//Created By
					false,	//Created Date
					false,	//Tagged By
					false,	//Tagged Date
					false,	//Edited By
					false	//Edited Date
			};
		}
	}

	
	
	
}
