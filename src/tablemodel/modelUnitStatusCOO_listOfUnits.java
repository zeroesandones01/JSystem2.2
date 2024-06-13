package tablemodel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelUnitStatusCOO_listOfUnits extends DefaultTableModel {

	private static final long serialVersionUID = 1L;

	private boolean editable = false;
	//boolean[] COLUMN_EDITABLE;

	public modelUnitStatusCOO_listOfUnits() {
		initThis();
	}

	public modelUnitStatusCOO_listOfUnits(boolean editable) {
		initThis();
	}

	public modelUnitStatusCOO_listOfUnits(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelUnitStatusCOO_listOfUnits(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelUnitStatusCOO_listOfUnits(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelUnitStatusCOO_listOfUnits(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelUnitStatusCOO_listOfUnits(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}

	String[] COLUMNS = new String[] {
			"Phase", 	// 0
			"Block", 	// 1
			"Lot", 		// 2
			"Batch", 	// 3
			"Model", 	// 4
			"Payment Scheme", 	// 5
			"Date Opened",  // 6
			"Date TR", 		// 7
			"Date OR", 		// 8
			"Docs Complete",// 9
			"NTC Date", // 10
			"Award Date",  // 11
			"Finish Date"  // 12
	};

	Class[] CLASS_TYPES = new Class[] {
			String.class, // Phase
			String.class, // Block
			String.class, // Lot
			String.class, // Batch
			String.class, // Model
			Object.class, // Payment Scheme
			Timestamp.class, 	// Date Opened
			Timestamp.class, 	// Date TR
			Timestamp.class, 	// Date OR
			Timestamp.class, 	// Docs Complete
			Timestamp.class, 	// Date Opened
			Timestamp.class, 	// NTC Date
			Timestamp.class, 	// NTP Date
			Timestamp.class 	// Finish Date
	};

	Boolean[] COLUMNS_EDITABLE = new Boolean[] {
			false, // 0
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
			false  // 12
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);

		COLUMNS_EDITABLE = new Boolean[] {
				false, // 0
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
				false  // 12
		};
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
		if(editable){
			COLUMNS_EDITABLE = new Boolean[] {
					false, // 0
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
					false  // 12
			};
		}else{
			COLUMNS_EDITABLE = new Boolean[] {
					false, // 0
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
					false  // 12
			};
		}
	}

}
