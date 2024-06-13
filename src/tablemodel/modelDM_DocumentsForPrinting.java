package tablemodel;

import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelDM_DocumentsForPrinting extends DefaultTableModel {

	private static final long serialVersionUID = 1L;
	
	private boolean editable = false;
	//boolean[] COLUMN_EDITABLE;

	public modelDM_DocumentsForPrinting() {
		initThis();
	}

	public modelDM_DocumentsForPrinting(boolean editable) {
		initThis();
	}

	public modelDM_DocumentsForPrinting(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelDM_DocumentsForPrinting(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelDM_DocumentsForPrinting(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelDM_DocumentsForPrinting(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelDM_DocumentsForPrinting(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}
	
	String[] COLUMNS = new String[] {
			"Select", // 1
			"ID", // 2
			"Documents", // 3
			"Date Printed", // 4
			"Printed By", // 5
			"Report Name" // 6
	};
	
	Class[] CLASS_TYPES = new Class[] {
			Boolean.class, // Select
			String.class, // ID
			Object.class, // Documents
			Timestamp.class, // Date Printed
			Object.class, // Printed By
			Object.class // URL
	};
	
	Boolean[] COLUMN_EDITABLE = new Boolean[]{
			true, // Select
			false, // ID
			false, // Documents
			false, // Date Printed
			false, // Printed By
			false // URL
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
