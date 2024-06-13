package tablemodel;

import java.util.Vector;

import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelDM_RRDocuments extends DefaultTableModel {

	private static final long serialVersionUID = 1L;

	private boolean editable = false;
	//boolean[] COLUMN_EDITABLE;

	public modelDM_RRDocuments() {
		initThis();
	}

	public modelDM_RRDocuments(boolean editable) {
		initThis();
	}

	public modelDM_RRDocuments(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelDM_RRDocuments(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelDM_RRDocuments(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelDM_RRDocuments(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelDM_RRDocuments(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}

	String[] COLUMNS = new String[] {
			"Select", // 0
			"Copies", // 1
			"ID", // 2
			"Documents", // 3
			"Doc Alias", //4
			"Required", //5
			"Mandatory", //6
			"Details", //7
			"Additional Info" //8
	};

	Class[] CLASS_TYPES = new Class[] {
			Boolean.class, // Select
			Integer.class, // Copies
			String.class, // ID
			Object.class, // Documents
			Object.class, //Doc. Alias
			Boolean.class, //Required
			Boolean.class, //Mandatory
			JButton.class, //Details
			String.class //Additional Details
	};

	Boolean[] COLUMN_EDITABLE = new Boolean[]{
			true, // Select
			true, // Copies
			false, // ID
			false, // Documents
			false, //Doc. Alias
			false, //Required
			false, //Mandatoy
			true, //Details
			false //Additional Details
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
	}

	public Class getColumnClass(int columnIndex) {
		return CLASS_TYPES[columnIndex];
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return COLUMN_EDITABLE[columnIndex];
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
