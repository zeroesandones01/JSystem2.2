package tablemodel;

import java.math.BigDecimal;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class modelOtherCheckInventory extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8738810102880371204L;
	
	private boolean editable = false;
	boolean[] COLUMN_EDITABLE;
	
	
	public modelOtherCheckInventory() {
		initThis();
	}

	public modelOtherCheckInventory(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelOtherCheckInventory(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelOtherCheckInventory(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelOtherCheckInventory(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelOtherCheckInventory(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			"Bank",			// 1
			"Branch",		// 2
			"Check No.",	// 3
			"Check Date",	// 4
			"Amount"		// 5
			};

	
	/**
	 * String.class = Alignment.CENTER
	 * Object.class = Alignment.LEFT
	 * BigDecimal.class = Alignment.RIGHT
	 * Integer.class = Alignment.CENTER
	 * Timestamp.class
	 */
	public Class[] CLASS_TYPES = new Class[] {
			String.class, 		//Bank
			String.class, 		//Branch
			String.class,		//Check No.
			String.class,		//Check Date
			BigDecimal.class	//Amount
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
		COLUMN_EDITABLE = new boolean[] {
				false, 		//Bank
				false, 		//Branch
				false,		//Check No.
				false, 		//Check Date
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
					false, 		//Bank
					false, 		//Branch
					false,		//Check No.
					false, 		//Check Date
					false		//Amount
			};
		}else{
			COLUMN_EDITABLE = new boolean[] {
					false, 		//Bank
					false, 		//Branch
					false,		//Check No.
					false, 		//Check Date
					false		//Amount
			};
		}
	}

	
	
	
}
