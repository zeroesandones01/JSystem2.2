package tablemodel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelTCostCompu extends DefaultTableModel {

	private static final long serialVersionUID = 1L;
	
	private boolean editable = false;
	boolean[] COLUMN_EDITABLE;

	public modelTCostCompu() {
		initThis();
	}

	public modelTCostCompu(boolean editable) {
		initThis();
	}

	public modelTCostCompu(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelTCostCompu(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelTCostCompu(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelTCostCompu(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelTCostCompu(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}
	
	String[] COLUMNS = new String[] {
			"Tag", 
			"Particular",
			"Setup Amount",
			"Applied Amount",
			"Remarks"
	};
	
	Class[] CLASS_TYPES = new Class[] {
			Boolean.class, //Bond No
			Object.class, //Insurance Co. ID
			BigDecimal.class, //From
			BigDecimal.class,
			Object.class //Insurance Co. ID
	};
	
	private void initThis() {
		setColumnIdentifiers(COLUMNS);
		COLUMN_EDITABLE = new boolean[] {
				true, //No.
				false, //Work Item
				false, //Quantity
				false,
				false
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
					true, //No.
					false, //Work Item
					false, //Quantity
					false,
					false
			};
		}else{
			COLUMN_EDITABLE = new boolean[] {
					false, //No.
					false, //Work Item
					false, //Quantity
					false,
					false
			};
		}
	}

}
