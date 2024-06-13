package tablemodel;

import java.math.BigDecimal;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;
import Lookup._JLookup;

public class modelNTPWorkItems extends DefaultTableModel {

	private static final long serialVersionUID = 1L;
	
	private boolean editable = false;
	boolean[] COLUMN_EDITABLE;

	public modelNTPWorkItems() {
		initThis();
	}

	public modelNTPWorkItems(boolean editable) {
		initThis();
	}

	public modelNTPWorkItems(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelNTPWorkItems(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelNTPWorkItems(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelNTPWorkItems(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelNTPWorkItems(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}
	
	String[] COLUMNS = new String[] {
			"No.", 
			"Work Item",
			"UOM",
			"Qty.",
			"Lump Sum/Other",
			"Total Unit Cost",
			"Total Cost",
			"Unit ID",
			"Unit Description",
			"Color Scheme",
			"% Work",
			"P.E. No."
	};
	
	Class[] CLASS_TYPES = new Class[] {
			Integer.class, //No.
			Object.class, //Work Item
			_JLookup.class, //UOM
			BigDecimal.class, //Quantity
			BigDecimal.class, //Lump Sum/Other
			BigDecimal.class, //Total Unit Cost
			BigDecimal.class, //Total Cost
			Integer.class, //Unit ID
			String.class, //Unit Description
			String.class, //Unit Description
			Integer.class, //% Work
			String.class //P.E. No.
	};
	
	private void initThis() {
		setColumnIdentifiers(COLUMNS);
		COLUMN_EDITABLE = new boolean[] {
				false, //No.
				false, //Work Item
				false, //UOM
				false, //Quantity
				false, //Lump Sum/Other
				false, //Total Unit Cost
				false, //Total Cost
				false, //Unit ID
				false, //Unit Description
				false, //Unit Description
				false, //% Work
				false //P.E. No.
		};
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
			COLUMN_EDITABLE = new boolean[] {
					false, //No.-
					true, //Work Item
					false, //UOM
					true, //Quantity
					true, //Lump Sum/Other
					false, //Total Unit Cost
					false, //Total Cost
					false, //Unit ID
					false, //Unit Description
					false, //Unit Description
					false, //% Work
					false //P.E. No.
			};
		}else{
			COLUMN_EDITABLE = new boolean[] {
					false, //No.
					false, //Work Item
					false, //UOM
					false, //Quantity
					false, //Lump Sum/Other
					false, //Total Unit Cost
					false, //Total Cost
					false, //Unit ID
					false, //Unit Description
					false, //Unit Description
					false, //% Work
					false //P.E. No.
			};
		}
	}

}
