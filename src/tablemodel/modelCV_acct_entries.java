package tablemodel;

import java.math.BigDecimal;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class modelCV_acct_entries	 extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8738810102880371204L;
	
	private boolean editable = false;
	boolean[] COLUMN_EDITABLE;
	
	
	public modelCV_acct_entries() {
		initThis();
	}

	public modelCV_acct_entries(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelCV_acct_entries(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelCV_acct_entries(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelCV_acct_entries(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelCV_acct_entries(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			" Account ID  " ,	// 1
			"Account Desc", 	// 2
			"Debit",			// 3
			"Credit"			// 4
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
			String.class,		//Account Desc
			BigDecimal.class, 	//Debit
			BigDecimal.class 	//Credit
			
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
		COLUMN_EDITABLE = new boolean[] {
				false, 		//Account ID
				false,		//Account Desc
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
					false,		//Account Desc
					false, 		//Debit
					false 		//Credit
			};
		}else{
			COLUMN_EDITABLE = new boolean[] {
					false, 		//Account ID
					false,		//Account Desc
					false, 		//Debit
					false 		//Credit
			};
		}
	}

	
	
	
}
