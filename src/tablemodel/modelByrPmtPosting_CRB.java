package tablemodel;

import java.math.BigDecimal;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Lookup._JLookup;

public class modelByrPmtPosting_CRB extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8738810102880371204L;
	
	private boolean editable = false;
	boolean[] COLUMN_EDITABLE;
	
	
	public modelByrPmtPosting_CRB() {
		initThis();
	}

	public modelByrPmtPosting_CRB(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelByrPmtPosting_CRB(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelByrPmtPosting_CRB(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelByrPmtPosting_CRB(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelByrPmtPosting_CRB(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			"Account ID",	// 1
			"Account Name",	// 2
			"Debit",		// 3
			"Credit",		// 4
			"Description"
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
			Object.class, 		//Account Name
			BigDecimal.class,	//Debit
			BigDecimal.class,	//Credit
			Object.class		//Description
			
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
		COLUMN_EDITABLE = new boolean[] {
				false, 		//Account ID
				false, 		//Account Name
				false,		//Debit
				false,		//Credit
				false		//Description
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
					false, 		//Account Name
					true,		//Debit
					true,		//Credit
					true		//Description
			};
		}else{
			COLUMN_EDITABLE = new boolean[] {
					true, 		//Account ID
					false, 		//Account Name
					false,		//Debit
					false,		//Credit
					false		//Description
			};
		}
	}

	
	
	
}
