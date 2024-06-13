package tablemodel;

import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;
import Lookup._JLookup;

public class modelPricelistPaymentScheme extends DefaultTableModel {

	private static final long serialVersionUID = 1L;

	private boolean editable = false;
	//boolean[] COLUMN_EDITABLE;

	public modelPricelistPaymentScheme() {
		initThis();
	}

	public modelPricelistPaymentScheme(boolean editable) {
		initThis();
	}

	public modelPricelistPaymentScheme(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelPricelistPaymentScheme(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelPricelistPaymentScheme(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelPricelistPaymentScheme(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelPricelistPaymentScheme(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}

	String[] COLUMNS = new String[] {
			"Scheme ID", // 0
			"Scheme Description", // 1
			"Comm. Scheme ID (Internal)", // 2
			"Comm. Scheme ID (External)", // 3
			"Added By", // 4
			"Added Date" // 5
	};

	Class[] CLASS_TYPES = new Class[] {
			_JLookup.class, // Scheme ID
			Object.class,   // Scheme Description
			_JLookup.class, // Comm. Scheme ID (Internal)
			_JLookup.class, // Comm. Scheme ID (External)
			String.class,   // Added By
			Timestamp.class // Added Date
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

		COLUMNS_EDITABLE = new Boolean[] {
				false, // 0
				false, // 1
				false, // 2
				false, // 3
				false, // 4
				false // 5
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
					false // 5
			};
		}else{
			COLUMNS_EDITABLE = new Boolean[] {
					false, // 0
					false, // 1
					false, // 2
					false, // 3
					false, // 4
					false // 5
			};
		}
	}

}
