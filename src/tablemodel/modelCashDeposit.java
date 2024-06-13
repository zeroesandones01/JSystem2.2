package tablemodel;

import java.math.BigDecimal;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class modelCashDeposit extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8738810102880371204L;
	
	private boolean editable = false;
	boolean[] COLUMN_EDITABLE;
	
	
	public modelCashDeposit() {
		initThis();
	}

	public modelCashDeposit(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelCashDeposit(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelCashDeposit(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelCashDeposit(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelCashDeposit(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			"Denomination",		// 1
			"No. of Pieces",	// 2
			"Amount"			// 3
			};

	
	/**
	 * String.class = Alignment.CENTER
	 * Object.class = Alignment.LEFT
	 * BigDecimal.class = Alignment.RIGHT
	 * Integer.class = Alignment.CENTER
	 * Timestamp.class
	 */
	public Class[] CLASS_TYPES = new Class[] {
			BigDecimal.class, 	//Denomination
			Integer.class, 		//No. of Pieces
			BigDecimal.class	//Amount
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
		COLUMN_EDITABLE = new boolean[] {
				false, 		//Denomination
				false, 		//No. of Pieces
				false		//Amount
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
					false, 		//Denomination
					true, 		//No. of Pieces
					false		//Amount
			};
		}else{
			COLUMN_EDITABLE = new boolean[] {
					false, 		//Denomination
					false, 		//No. of Pieces
					false		//Amount
			};
		}
	}

	
	
	
}
