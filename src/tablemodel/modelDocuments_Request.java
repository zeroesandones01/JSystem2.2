package tablemodel;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelDocuments_Request extends DefaultTableModel {

	private static final long serialVersionUID = 1L;
	
	private boolean editable = false;

	public modelDocuments_Request() {
		initThis();
	}

	public modelDocuments_Request(boolean editable) {
		initThis();
	}

	public modelDocuments_Request(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelDocuments_Request(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelDocuments_Request(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelDocuments_Request(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelDocuments_Request(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}
	
	String[] COLUMNS = new String[] {
			"Select", // 0
			"ID", // 1
			"Doc Alias", // 2
			"Doc Name", //3
			"Dept. Alias",  // 4
			"Photocopy" //5
	};
	
	Class[] CLASS_TYPES = new Class[] {
			Boolean.class, //Select 
			Object.class, //ID
			Object.class, //Doc Alias
			Object.class, //Doc Name
			Object.class, //Dept. Alias
			Boolean.class //Photocopy
	};
	
	Boolean[] COLUMNS_EDITABLE = new Boolean[] {
			true, // 0
			false, // 1
			false, // 2
			false, // 3
			false, //4
			true //5
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
