package tablemodel;

import java.util.Vector;

import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelDM_AllDocuments extends DefaultTableModel {

	private static final long serialVersionUID = 1L;

	private boolean editable = false;
	//boolean[] COLUMN_EDITABLE;

	public modelDM_AllDocuments() {
		initThis();
	}

	public modelDM_AllDocuments(boolean editable) {
		initThis();
	}

	public modelDM_AllDocuments(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelDM_AllDocuments(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelDM_AllDocuments(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelDM_AllDocuments(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelDM_AllDocuments(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}

	String[] COLUMNS = new String[] {
			"Select", // 0
			"Copy", // 1
			"ID", // 2
			"Documents", // 3
			"Alias", // 4
			"Required", // 5
			"Mandatory", // 7
			"Details", // 7
			"Doc Details" //8
	};

	Class[] CLASS_TYPES = new Class[] {
			Boolean.class, // Select
			Integer.class, // Copies
			String.class, // ID
			Object.class, // Documents
			Object.class, // Alias
			Boolean.class, // Required
			Boolean.class, // Mandatory
			JButton.class, // Details
			Object.class //Additional Info.
	};

	Boolean[] COLUMN_EDITABLE = new Boolean[]{
			true, // Select
			true, // Copies
			false, // ID
			false, // Documents
			false, // Alias
			false, // Required
			false, // Mandatory
			true, // Details
			false //Additional Info.
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
					false, // Mandatory
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
					false, // Mandatory
					true, // Details
					false //Additional Info.
			};
		}
	}

}
