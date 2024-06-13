package tablemodel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Lookup._JLookup;

public class modelCV_pv	 extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8738810102880371204L;
	
	private boolean editable = false;
	boolean[] COLUMN_EDITABLE;
	
	
	public modelCV_pv() {
		initThis();
	}

	public modelCV_pv(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelCV_pv(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelCV_pv(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelCV_pv(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelCV_pv(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
		"PV No.", 
		"PV Date", 
		"Amount", 
		"MC No",
		"Check No", 
		"Check Date"
	};

	
	/**
	 * String.class = Alignment.CENTER
	 * Object.class = Alignment.LEFT
	 * BigDecimal.class = Alignment.RIGHT
	 * Integer.class = Alignment.CENTER
	 * Timestamp.class
	 */
	public Class[] CLASS_TYPES = new Class[] {
		_JLookup.class, 	//Account ID	
		Timestamp.class, 	//PV Date
		BigDecimal.class, 	//Amount
		String.class,		//MC No
		String.class,		//Check No
		Timestamp.class 	//Check Date
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
		COLUMN_EDITABLE = new boolean[] {
				false, 	//Account ID
				false,	// PV Date
				false, 	//Amount
				false, 	//MC No
				false,	//Check No
				false	//Check Date
		};
	}

	public Class getColumnClass(int columnIndex) {
		return CLASS_TYPES[columnIndex];
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return COLUMN_EDITABLE[columnIndex];
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
		if(editable){
			COLUMN_EDITABLE = new boolean[] {
					false, 	//Account ID
					false,	// PV Date
					false, 	//Amount
					false, 	//MC No
					false, 
					false
			};
		}else{
			COLUMN_EDITABLE = new boolean[] {
					false, 	//Account ID
					false,	// PV Date
					false, 	//Amount
					false, 	//MC No
					false, 
					false
			};
		}
	}

	
	
	
}
