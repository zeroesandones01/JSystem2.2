package tablemodel;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelDocumentsMaintenance_DocsMonitoring extends DefaultTableModel {

	private static final long serialVersionUID = 1L;

	private boolean editable = false;
	//boolean[] COLUMN_EDITABLE;

	public modelDocumentsMaintenance_DocsMonitoring() {
		initThis();
	}

	public modelDocumentsMaintenance_DocsMonitoring(boolean editable) {
		initThis();
	}

	public modelDocumentsMaintenance_DocsMonitoring(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelDocumentsMaintenance_DocsMonitoring(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelDocumentsMaintenance_DocsMonitoring(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelDocumentsMaintenance_DocsMonitoring(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelDocumentsMaintenance_DocsMonitoring(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}

	String[] COLUMNS = new String[] {
			"ID", // 0
			"Name", // 1
			"Alias", // 2
			"In-House", // 3
			"Cash", // 4
			"Pag-IBIG", // 5
			"", // 6
			//"Married", // 7
			"Mandatory", // 8
			"Client Status", // 9
			"Business Class", // 10
			"Report", // 11
			"Business Nature", //12
			"Civil Status", //13
			"Doc Sub" //14
	};

	Class[] CLASS_TYPES = new Class[] {
			Integer.class, // ID
			Object.class, // Name
			Object.class, // Alias
			Boolean.class, // In-House
			Boolean.class, // Cash
			Boolean.class, // Pag-IBIG
			Object.class, // 
			//Boolean.class, // For Married
			Boolean.class, // For OCW
			Object.class, // Client Status
			Object.class, // Business Class
			Object.class, // Report
			Object.class, //Business Nature
			Object.class, //Civil Status
			Object.class //Doc Sub
	};

	Boolean[] COLUMN_EDITABLE = new Boolean[]{
			false, // ID
			false, // Name
			false, // Alias
			false, // In-House
			false, // Cash
			false, // Pag-IBIG
			false, //
			//false, // For Married
			false, // For OCW
			false, // Client Status
			false, // Business Class
			false, // Report
			false, //Business Nature
			false, //Civil Status
			false //Doc Sub
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
		if(editable){
			COLUMN_EDITABLE = new Boolean[]{
					true, // Select
					true, // Copies
					false, // ID
					false, // Documents
					false, // Alias
					false, // Required
					true, // Details
					false //Additional Info.
			};
		}else{
			COLUMN_EDITABLE = new Boolean[]{
					true, // Select
					true, // Copies
					false, // ID
					false, // Documents
					false, // Alias
					false, // Required
					true, // Details
					false //Additional Info.
			};
		}
	}

}
