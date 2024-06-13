package tablemodel;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelDocPurpose_Request extends DefaultTableModel {

	private static final long serialVersionUID = 1L;
	
	private boolean editable = false;
	//boolean[] COLUMN_EDITABLE;

	public modelDocPurpose_Request() {
		initThis();
	}

	public modelDocPurpose_Request(boolean editable) {
		initThis();
	}

	public modelDocPurpose_Request(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelDocPurpose_Request(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelDocPurpose_Request(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelDocPurpose_Request(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelDocPurpose_Request(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}
	
	String[] COLUMNS = new String[] {
			"Select", // 0
			"ID", // 1
			"Purpose" // 2
			
	};
	
	Class[] CLASS_TYPES = new Class[] {
			Boolean.class, //Select 
			Object.class, //ID
			Object.class, //Purpose
			
	};
	
	Boolean[] COLUMNS_EDITABLE = new Boolean[] {
			true, // 0
			false, // 1
			false // 2
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
