package tablemodel;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelPurpose_ForRelease extends DefaultTableModel {

	private static final long serialVersionUID = 7552273896454662033L;
	private boolean editable = false;

	public modelPurpose_ForRelease() {
		initThis();
	}

	public modelPurpose_ForRelease(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelPurpose_ForRelease(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelPurpose_ForRelease(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelPurpose_ForRelease(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelPurpose_ForRelease(Object[][] data, Object[] columnNames) {
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
