package tablemodel;

import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelDM_SubmittedDocuments extends DefaultTableModel {

	private static final long serialVersionUID = 1L;

	private boolean editable = false;
	//boolean[] COLUMN_EDITABLE;

	public modelDM_SubmittedDocuments() {
		initThis();
	}

	public modelDM_SubmittedDocuments(boolean editable) {
		initThis();
	}

	public modelDM_SubmittedDocuments(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelDM_SubmittedDocuments(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelDM_SubmittedDocuments(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelDM_SubmittedDocuments(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelDM_SubmittedDocuments(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}

	String[] COLUMNS = new String[] {
			"Select", //
			"Copies", // 1
			"ID", // 2
			"Documents", // 3
			"Received From", // 4
			"Received Date", // 5
			"Docs-OUT", // 6
			"Docs-IN", // 7
			"Remarks", // 8
			"Status", // 9
			"Details", //10
			"Doc Details", //11
			"Date Issued", // 12
			"Validity" //13
	};

	Class[] CLASS_TYPES = new Class[] {
			Boolean.class, //Select
			Integer.class, // Copies
			String.class, // ID
			Object.class, // Documents
			Object.class, // Received From
			Timestamp.class, // Received Date
			Timestamp.class, // Release
			Timestamp.class, // Return
			Object.class, // Remarks
			String.class, // Status
			JButton.class, //Edit Details
			Object.class, //Doc Details
			Object.class, //Date Issued
			Object.class //Validity
	};

	Boolean[] COLUMN_EDITABLE = new Boolean[]{
			true, // Select
			true, // Copies
			false, // ID
			false, // Documents
			false, // Received From
			false, // Received Date
			false, // Release
			false, //Return
			false, //Remarks
			false, //Status
			true, //Details
			false, //Doc Details
			false, //Date Issued
			false //Validity
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
	}

	public Class getColumnClass(int columnIndex) {
		return CLASS_TYPES[columnIndex];
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return COLUMN_EDITABLE[columnIndex];
		//return false;
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
