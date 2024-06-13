package tablemodel;

import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

/**
 *  @author John Lester Fatallo
 * 
 */

public class modelClientSubmittedID extends DefaultTableModel {

	private static final long serialVersionUID = 1L;
	
	private boolean editable = false;
	//boolean[] COLUMN_EDITABLE;

	public modelClientSubmittedID() {
		initThis();
	}

	public modelClientSubmittedID(boolean editable) {
		initThis();
	}

	public modelClientSubmittedID(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelClientSubmittedID(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelClientSubmittedID(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelClientSubmittedID(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelClientSubmittedID(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}
	
	String[] COLUMNS = new String[] {
			"Rec. ID", // 0
			"ID Code", //1
			"ID Type", // 2
			"ID No", // 3
			"Expiration", //4
			"Status", //5
			"Default" //6
	};
	
	Class[] CLASS_TYPES = new Class[] {
			Integer.class, //Rec. ID
			String.class, //ID Code
			String.class, //ID Type
			String.class, //ID No
			Timestamp.class, //Expiration
			String.class, //Status ID\
			Boolean.class //Default
	};
	
	Boolean[] COLUMNS_EDITABLE = new Boolean[] {
			false, // 0
			false, // 1
			false, // 2
			false, // 3
			false, // 4
			false, //5
			false //6
	};
	
	private void initThis() {
		setColumnIdentifiers(COLUMNS);
	}
	
	public Class getColumnClass(int columnIndex) {
		return CLASS_TYPES[columnIndex];
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return COLUMNS_EDITABLE[columnIndex];
	}
	
	public void clear() {
		FncTables.clearTable(this);
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

}
