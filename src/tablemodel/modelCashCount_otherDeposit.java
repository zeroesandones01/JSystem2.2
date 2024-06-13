package tablemodel;

import java.math.BigDecimal;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class modelCashCount_otherDeposit extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8738810102880371204L;
	
	private boolean editable = false;
	boolean[] COLUMN_EDITABLE;
	
	
	public modelCashCount_otherDeposit() {
		initThis();
	}

	public modelCashCount_otherDeposit(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelCashCount_otherDeposit(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelCashCount_otherDeposit(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelCashCount_otherDeposit(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelCashCount_otherDeposit(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			"Particular",		// 1
			"Amount"			// 2
			};

	
	/**
	 * String.class = Alignment.CENTER
	 * Object.class = Alignment.LEFT
	 * BigDecimal.class = Alignment.RIGHT
	 * Integer.class = Alignment.CENTER
	 * Timestamp.class
	 */
	public Class[] CLASS_TYPES = new Class[] {
			Object.class, 		//Particular
			BigDecimal.class,	//Amount
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
		COLUMN_EDITABLE = new boolean[] {
				false, 		//Particular
				true		//Amount
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
					false, 		//Particular
					true		//Amount
			};
		}else{
			COLUMN_EDITABLE = new boolean[] {
					false, 		//Particular
					false		//Amount
			};
		}
	}

	
	
	
}
