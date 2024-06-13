package tablemodel;

import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelHoldingAndReservation_Requests extends DefaultTableModel {

	private static final long serialVersionUID = 1L;
	
	private boolean editable = false;
	//boolean[] COLUMN_EDITABLE;

	public modelHoldingAndReservation_Requests() {
		initThis();
	}

	public modelHoldingAndReservation_Requests(boolean editable) {
		initThis();
	}

	public modelHoldingAndReservation_Requests(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelHoldingAndReservation_Requests(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelHoldingAndReservation_Requests(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelHoldingAndReservation_Requests(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelHoldingAndReservation_Requests(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}
	
	String[] COLUMNS = new String[] {
			"No.", // 0
			"ID", // 1
			"Description", // 2
			"Date", // 3
			"By", // 4
			"Remarks" // 5
	};
	
	Class[] CLASS_TYPES = new Class[] {
			Object.class, // No.
			String.class, // ID
			Object.class, // Description
			Timestamp.class, // Date
			Object.class, // By
			Object.class // Remarks
	};
	
	Boolean[] COLUMNS_EDITABLE = new Boolean[] {
			false, // 0
			false, // 1
			false, // 2
			false, // 3
			false, // 4
			false // 5
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
