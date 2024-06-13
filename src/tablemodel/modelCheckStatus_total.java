package tablemodel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Lookup._JLookup;

public class modelCheckStatus_total extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8738810102880371204L;
	
	private boolean editable = false;
	boolean[] COLUMN_EDITABLE;
	
	
	public modelCheckStatus_total() {
		initThis();
	}

	public modelCheckStatus_total(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelCheckStatus_total(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelCheckStatus_total(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelCheckStatus_total(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelCheckStatus_total(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			"Client Name", 		// 0
			"Project", 			// 1
			"Unit PBL", 		// 2
			"Seq.", 			// 3
			"Particulars",  	// 4
			"Amount",       	// 5
			"Bank",				// 6
			"Check No",			// 7
			"Check Date",		// 8
			"Tag",				// 9
			"Current Status",	// 10
			"Change Status to",	// 11
			"Reason of Bounce",	// 12
			"Actual Date",		// 13
			"Remarks"			// 14
			};

	
	/**
	 * String.class = Alignment.CENTER
	 * Object.class = Alignment.LEFT
	 * BigDecimal.class = Alignment.RIGHT
	 * Integer.class = Alignment.CENTER
	 * Timestamp.class
	 */
	public Class[] CLASS_TYPES = new Class[] {
			Object.class, 		//Client Name
			String.class, 		//Project
			String.class,		//Unit PBL
			String.class,		//Seq. No.
			String.class,		//Particulars
			BigDecimal.class, 	//Amount
			String.class,		//Bank
			String.class, 		//Check No.
			Timestamp.class,	//Check Date
			String.class, 		//Tag
			String.class,		//Current Status
			_JLookup.class,		//Change Status to
			_JLookup.class,		//Reason of Bounce
			Timestamp.class,	//Actual Date
			String.class,		//Remarks
			
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
		COLUMN_EDITABLE = new boolean[] {
				false, 		//Client Name
				false, 		//Project
				false,		//Unit PBL
				false,		//Sequence
				false,		//Particulars
				false, 		//Amount
				false,		//Bank
				false, 		//Check No.
				false,		//Check Date
				false, 		//Tag
				false,		//Current Status
				false,		//Change Status to
				false,      //Reason of Bounce
				false,		//Actual Date
				false,		//Remarks
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
					false, 		//Client Name
					false, 		//Project
					false,		//Unit PBL
					false,		//Sequence
					false,		//Particulars
					false, 		//Amount
					false,		//Bank
					false, 		//Check No.
					false,		//Check Date
					false, 		//Tag
					false,		//Current Status
					false,		//Change Status to
					false,       //Reason of Bounce
					false,		//Actual Date
					true,		//Remarks
			};
		}else{
			COLUMN_EDITABLE = new boolean[] {
					false, 		//Client Name
					false, 		//Project
					false,		//Unit PBL
					false,		//Sequence
					false,		//Particulars
					false, 		//Amount
					false,		//Bank
					false, 		//Check No.
					false,		//Check Date
					false, 		//Tag
					false,		//Current Status
					false,		//Change Status to
					false,      //Reason of Bounce
					false,		//Actual Date
					false,		//Remarks
			};
		}
	}

	
	
	
}
