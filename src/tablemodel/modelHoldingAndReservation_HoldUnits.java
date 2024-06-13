package tablemodel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelHoldingAndReservation_HoldUnits extends DefaultTableModel {

	private static final long serialVersionUID = 1L;
	
	private boolean editable = false;
	//boolean[] COLUMN_EDITABLE;

	public modelHoldingAndReservation_HoldUnits() {
		initThis();
	}

	public modelHoldingAndReservation_HoldUnits(boolean editable) {
		initThis();
	}

	public modelHoldingAndReservation_HoldUnits(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelHoldingAndReservation_HoldUnits(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelHoldingAndReservation_HoldUnits(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelHoldingAndReservation_HoldUnits(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelHoldingAndReservation_HoldUnits(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}
	
	String[] COLUMNS = new String[] {
			"Select", // 0
			"Client Seq. No.", // 1
			"Client ID", // 2
			"Client Name", // 3
			"Unit ID", // 4
			"Unit", // 5
			"Sequence", // 6
			"TCP", // 7
			"Hold Date", // 8
			"Hold Until", // 9
			"Payment", // 10
			"Remarks", // 10
			"Status" // 11
	};
	
	Class[] CLASS_TYPES = new Class[] {
			Boolean.class, // Select
			String.class, // Client Seq. No.
			String.class, // Client ID
			Object.class, // Client Name
			String.class, // Unit ID
			String.class, // Unit
			Integer.class, // Sequence
			BigDecimal.class, // TCP
			Timestamp.class, // Hold Date
			Timestamp.class, // Hold Until
			BigDecimal.class, // Payment
			Object.class, // Remarks
			String.class // Status
	};
	
	Boolean[] COLUMNS_EDITABLE = new Boolean[] {
			true, // 0
			false, // 1
			false, // 2
			false, // 3
			false, // 4
			false, // 5
			false, // 6
			false, // 7
			false, // 8
			false, // 9
			false, // 10
			false, // 11
			false // 12
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
