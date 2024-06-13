package tablemodel;

import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelTOverMoveIn extends DefaultTableModel {
	
	private static final long serialVersionUID = 1L;
	
	public modelTOverMoveIn() {
		initThis();
	}

	public modelTOverMoveIn(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelTOverMoveIn(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelTOverMoveIn(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelTOverMoveIn(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelTOverMoveIn(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}
	private void initThis() {
		setColumnIdentifiers(new String[] {
			"Particulars", // 3
			"Trans. Date", // 1
			"Actual Date",
			"Project", // 7
			"Unit", // 7
			"Refund Date" // 7
	});
	}
	
	/**
	 * String.class = Alignment.CENTER
	 * Object.class = Alignment.LEFT
	 * BigDecimal.class = Alignment.RIGHT
	 * Integer.class = Alignment.CENTER
	 * 
	 */
	Class[] CLASS_TYPES = new Class[] {
			Object.class, //Phase-Block-Lot
			Timestamp.class, //Remarks
			Timestamp.class,
			String.class, //Remarks
			String.class, //Unit ID
			Timestamp.class //Refund Date
	};
	
	Boolean[] COLUMN_EDITABLE = new Boolean[] {
			false, //Trans Date
			false, //Remarks
			false,
			false, //Remarks
			false, //Unit ID
			false //Refund Date
	};


	public Class getColumnClass(int columnIndex) {
		return CLASS_TYPES[columnIndex];
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return COLUMN_EDITABLE[columnIndex];
	}

	public void clear() {
		FncTables.clearTable(this);
		
	}

}
