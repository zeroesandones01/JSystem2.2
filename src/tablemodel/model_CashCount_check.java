package tablemodel;

import java.math.BigDecimal;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Lookup._JLookup;

public class model_CashCount_check extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8738810102880371204L;
	
	private boolean editable = false;
	boolean[] COLUMN_EDITABLE;
	
	
	public model_CashCount_check() {
		initThis();
	}

	public model_CashCount_check(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public model_CashCount_check(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public model_CashCount_check(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public model_CashCount_check(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public model_CashCount_check(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			"Account No.",		// 1
			"Project",			// 2
			"Phase",			// 3
			"Amount"			// 4
			};

	
	/**
	 * String.class = Alignment.CENTER
	 * Object.class = Alignment.LEFT
	 * BigDecimal.class = Alignment.RIGHT
	 * Integer.class = Alignment.CENTER
	 * Timestamp.class
	 */
	public Class[] CLASS_TYPES = new Class[] {
			_JLookup.class, 	//Account No.
			String.class,		//Project
			String.class,		//Phase
			BigDecimal.class,	//Amount
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
		COLUMN_EDITABLE = new boolean[] {
				false, 		//Account No.
				false,		//Project
				false,		//Phase
				false, 		//Amount
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
					false, 		//Account No.
					false,		//Project
					false,		//Phase
					true, 		//Amount
			};
		}else{
			COLUMN_EDITABLE = new boolean[] {
					false, 		//Account No.
					false,		//Project
					false,		//Phase
					false, 		//Amount
			};
		}
	}

	
	
	
}
