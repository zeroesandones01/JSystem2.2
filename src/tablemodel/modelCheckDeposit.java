package tablemodel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class modelCheckDeposit extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8738810102880371204L;
	
	private boolean editable = false;
	boolean[] COLUMN_EDITABLE;
	
	
	public modelCheckDeposit() {
		initThis();
	}

	public modelCheckDeposit(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelCheckDeposit(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelCheckDeposit(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelCheckDeposit(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelCheckDeposit(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			"Tag",				// 1
			"Bank",				// 2
			"Bank Branch",		// 3
			"Check No",			// 4
			"PDC",				// 5
			"Check Date",		// 6
			"Amount",       	// 7		
			"Client Name", 		// 8
			"Unit",				// 9
			"Particulars", 		// 10
			"Check Status",		// 11
			"Buyer Status",		// 12
			"Pay Rec ID",		// 13
			};

	
	/**
	 * String.class = Alignment.CENTER
	 * Object.class = Alignment.LEFT
	 * BigDecimal.class = Alignment.RIGHT
	 * Integer.class = Alignment.CENTER
	 * Timestamp.class
	 */
	public Class[] CLASS_TYPES = new Class[] {
			Boolean.class, 		//Tag
			String.class,		//Bank
			String.class,		//Bank Branch
			String.class, 		//Check No.
			String.class, 		//PDC
			Timestamp.class,	//Check Date		
			BigDecimal.class, 	//Amount		
			Object.class, 		//Client Name
			String.class,		//Pay Rec ID
			String.class,		//Pay Rec ID
			String.class,		//Current Status
			String.class,		//Ledger Status ID
			String.class,		//Pay Rec ID
			

	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
		COLUMN_EDITABLE = new boolean[] {
				false, 		//Tag
				false, 		//Bank
				false, 		//Bank Branch
				false,		//Check No.
				false,		//PDC
				false,		//Check Date	
				false, 		//Amount
				false,		//Client Name
				false,		//Unit
				false,		//Particular
				false, 		//Current Status
				false,		//Ledger Status ID
				false		//Pay Rec ID
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
					true, 		//Tag
					false, 		//Bank
					false, 		//Bank Branch
					false,		//Check No.
					false,		//PDC
					false,		//Check Date	
					false, 		//Amount
					false,		//Client Name
					false,		//Unit
					false,		//Particular
					false, 		//Current Status
					false,		//Ledger Status ID
					false		//Pay Rec ID
			};
		}else{
			COLUMN_EDITABLE = new boolean[] {
					false, 		//Tag
					false, 		//Bank
					false, 		//Bank Branch
					false,		//Check No.
					false,		//PDC
					false,		//Check Date	
					false, 		//Amount
					false,		//Client Name
					false,		//Unit
					false,		//Particular
					false, 		//Current Status
					false,		//Ledger Status ID
					false		//Pay Rec ID
			};
		}
	}

	
	
	
}
