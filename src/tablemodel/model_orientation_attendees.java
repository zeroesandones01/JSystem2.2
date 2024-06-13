package tablemodel;

import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class model_orientation_attendees extends DefaultTableModel {
	
	private static final long serialVersionUID = 1L;
	
	public model_orientation_attendees() {
		initThis();
	}

	public model_orientation_attendees(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public model_orientation_attendees(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public model_orientation_attendees(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public model_orientation_attendees(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public model_orientation_attendees(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}
	private void initThis() {
		setColumnIdentifiers(new String[] {
			"Tag", // 0
			"Tran Date", // 2
			"Buyer's Name", // 3
			"Phase-Block-Lot", // 1
			"Model", // 7
			"Batch", // 7
			"PBL" // 7
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
			Boolean.class, //Tag
			Timestamp.class, //Trans Date
			Object.class, //Phase-Block-Lot
			String.class, //Remarks
			String.class, //Remarks
			String.class, //Remarks
			String.class //Unit ID
	};
	
	Boolean[] COLUMN_EDITABLE = new Boolean[] {
			true, //Tag
			false, //Phase-Block-Lot
			false, //Trans Date
			false, //Remarks
			false, //Remarks
			false, //Remarks
			false //Unit ID
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
