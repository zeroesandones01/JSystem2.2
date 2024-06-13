package tablemodel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelRB_LegalImplication extends DefaultTableModel {

	private static final long serialVersionUID = 1L;

	private boolean editable = false;
	//boolean[] COLUMN_EDITABLE;

	public modelRB_LegalImplication() {
		initThis();
	}

	public modelRB_LegalImplication(boolean editable) {
		initThis();
	}

	public modelRB_LegalImplication(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelRB_LegalImplication(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelRB_LegalImplication(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelRB_LegalImplication(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelRB_LegalImplication(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}

	String[] COLUMNS = new String[] {
			"Select", // 0
			"Client ID", // 1
			"Client Name", // 2
			"PBL ID", // 3
			"Seq.", // 4
			"Description", // 5
			"Part. ID", // 6
			"Particular", // 7
			"Due Date", // 8
			"Amount Due", // 9
			"Default Date", // 10
			"House Model" // 11
	};

	Class[] CLASS_TYPES = new Class[] {
			Boolean.class, // Select
			String.class, // Client ID
			Object.class, // Client Name
			String.class, // PBL ID
			Integer.class, // Seq.
			Object.class, // Description
			String.class, // Part. ID
			Object.class, // Particular
			Timestamp.class, // Due Date
			BigDecimal.class, // Amount Due
			Timestamp.class, // Default Date
			String.class, // House Model
	};

	Boolean[] COLUMN_EDITABLE = new Boolean[]{
			true, // Select
			false, // Client ID
			false, // Client Name
			false, // PBL ID
			false, // Seq.
			false, // Description
			false, // Part. ID
			false, // Particular
			false, // Due Date
			false, // Amount Due
			false, // Default Date
			false // House Model
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
	}

	public Class getColumnClass(int columnIndex) {
		return CLASS_TYPES[columnIndex];
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if(isEditable()){
			return COLUMN_EDITABLE[columnIndex];
		}else{
			return false;
		}
	}

	public void clear() {
		FncTables.clearTable(this);
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		COLUMN_EDITABLE = new Boolean[]{
				editable, // Select
				false, // Client ID
				false, // Client Name
				false, // PBL ID
				false, // Seq.
				false, // Description
				false, // Part. ID
				false, // Particular
				false, // Due Date
				false, // Amount Due
				false, // Default Date
				false // House Model
		};
		this.editable = editable;
	}

}
