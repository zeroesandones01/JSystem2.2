package tablemodel;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelDocumentsMaintenance_BuyerTypes extends DefaultTableModel {

	private static final long serialVersionUID = 1L;

	private boolean editable = false;
	//boolean[] COLUMN_EDITABLE;

	public modelDocumentsMaintenance_BuyerTypes() {
		initThis();
	}

	public modelDocumentsMaintenance_BuyerTypes(boolean editable) {
		initThis();
	}

	public modelDocumentsMaintenance_BuyerTypes(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelDocumentsMaintenance_BuyerTypes(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelDocumentsMaintenance_BuyerTypes(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelDocumentsMaintenance_BuyerTypes(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelDocumentsMaintenance_BuyerTypes(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}

	String[] COLUMNS = new String[] {
			"", 
			"ID", // 0
			"Documents", // 1
			"Alias", // 2
			"Group ID" // 3
	};

	Class[] CLASS_TYPES = new Class[] {
			Boolean.class, // Select
			String.class, // ID
			Object.class, // Documents
			Object.class, // Alias
			String.class // Group ID
	};

	Boolean[] COLUMN_EDITABLE = new Boolean[]{
			true, // Select
			false, // ID
			false, // Documents
			false // Alias
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
