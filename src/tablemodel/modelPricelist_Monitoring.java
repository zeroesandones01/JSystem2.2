package tablemodel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;
import Lookup._JLookup;

public class modelPricelist_Monitoring extends DefaultTableModel {

	private static final long serialVersionUID = 1L;

	private boolean editable = false;
	//boolean[] COLUMN_EDITABLE;

	public modelPricelist_Monitoring() {
		initThis();
	}

	public modelPricelist_Monitoring(boolean editable) {
		initThis();
	}

	public modelPricelist_Monitoring(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelPricelist_Monitoring(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelPricelist_Monitoring(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelPricelist_Monitoring(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelPricelist_Monitoring(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}

	String[] COLUMNS = new String[] {
			"Unit ID", // 0
			"PBL ID", // 1
			"Description", // 2
			"Model", // 3
			"VAT", // 4
			"Lot Area", // 5
			"Floor Area", // 6
			"Selling Price", // 7
			"Date Opened", // 8
			"House Constructed" // 9
	};

	Class[] CLASS_TYPES = new Class[] {
			String.class, // Unit ID 
			String.class, // PBL ID
			Object.class, // Description
			_JLookup.class, // Model
			Boolean.class, // VAT
			BigDecimal.class, // Lot Area
			BigDecimal.class, // Floor Area
			BigDecimal.class, // Selling Price
			Timestamp.class, // Date Opened
			Timestamp.class // House Constructed
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
			false // 9
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
				false // 9
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
					true, // 4
					true, // 5
					true, // 6
					true, // 7
					true, // 8
					true // 9
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
					false // 9
			};
		}
	}

}
