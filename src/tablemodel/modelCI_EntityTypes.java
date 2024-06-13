package tablemodel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelCI_EntityTypes extends DefaultTableModel {

	private static final long serialVersionUID = 1L;

	private boolean editable = false;
	//boolean[] COLUMN_EDITABLE;

	public modelCI_EntityTypes() {
		initThis();
	}

	public modelCI_EntityTypes(boolean editable) {
		initThis();
	}

	public modelCI_EntityTypes(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelCI_EntityTypes(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelCI_EntityTypes(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelCI_EntityTypes(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelCI_EntityTypes(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}

	String[] COLUMNS = new String[] {
			"Select", // 0
			"ID", // 1
			"Name", // 2
			"WTAX ID", // 3
			"WTAX Description", // 4
			"WTAX Rate" // 5
	};

	Class[] CLASS_TYPES = new Class[] {
			Boolean.class, // Client ID
			String.class, // Client Name
			Object.class, // Project ID
			String.class, // WTAX ID
			Object.class, // WTAX Description
			BigDecimal.class // WTAX Rate
	};

	Boolean[] COLUMN_EDITABLE = new Boolean[]{
			true, // Select
			false, //Client Name 
			false, //Project ID
			false, //WTAX ID
			false, //WTAX Description
			false  //WTAX Rate
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
