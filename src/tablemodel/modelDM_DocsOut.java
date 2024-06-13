package tablemodel;

import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelDM_DocsOut extends DefaultTableModel {

	private static final long serialVersionUID = 1L;
	
	private boolean editable = false;
	//boolean[] COLUMN_EDITABLE;

	public modelDM_DocsOut() {
		initThis();
	}

	public modelDM_DocsOut(boolean editable) {
		initThis();
	}

	public modelDM_DocsOut(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelDM_DocsOut(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelDM_DocsOut(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelDM_DocsOut(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelDM_DocsOut(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}
	
	String[] COLUMNS = new String[] {
			"Select", // 0
			"Copies", // 1
			"ID", // 2
			"Documents", // 3
			"Docs-IN Date", // 4
			"Docs-OUT Date", // 5
			"Date Evaluated", // 6
			"Evaluation", // 7
			"Status" // 8
	};
	
	Class[] CLASS_TYPES = new Class[] {
			Boolean.class, // Select
			Integer.class, // Copies
			String.class, // ID
			Object.class, // Documents
			Timestamp.class, // Docs-IN Date
			Object.class, // Docs-OUT Date
			Object.class, // Date Evaluated
			Object.class, // Evaluation
			Object.class // Status
	};
	
	Boolean[] COLUMN_EDITABLE = new Boolean[]{
			true, // Select
			false, // Printed By
			false, // ID
			false, // Documents
			false, // Docs-IN Date
			false, // Docs-OUT Date
			false, // Date Evaluated
			false, // Evaluation
			false // Status
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
