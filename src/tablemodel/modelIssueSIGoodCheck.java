package tablemodel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class modelIssueSIGoodCheck extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8738810102880371204L;
	
	private boolean editable = false;
	boolean[] COLUMN_EDITABLE;
	
	
	public modelIssueSIGoodCheck() {
		initThis();
	}

	public modelIssueSIGoodCheck(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelIssueSIGoodCheck(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelIssueSIGoodCheck(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelIssueSIGoodCheck(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelIssueSIGoodCheck(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			"Client Name" ,			// 0
			"Project" ,				// 1
			"PBL ID" ,				// 2
			"Seq No." ,				// 3
			"PBL", 					// 4
			"Particulars" ,			// 5
			"Amount", 				// 6
			"AR Number", 			// 7
			"SI Number", 			// 8
			"SI Date", 				// 9
			"Bank", 				// 10
			"Branch", 				// 11
			"Check No.",			// 12		
			"Check Date",			// 13	
			"Check Status",			// 14	
			"Tag", 					// 15
			"Date Cleared", 		// 16
			"Pay Rec ID" 			// 17
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
			String.class, 		//PBL ID
			String.class, 		//Seq No
			String.class, 		//PBL
			String.class,		//Particulars
			BigDecimal.class,	//Amount
			String.class,		//AR No.
			String.class, 		//SI No.
			Timestamp.class, 	//SI Date
			String.class,		//Bank
			String.class, 		//Branch
			String.class,		//Check No.
			Timestamp.class,	//Check Date
			String.class,		//Check Status
			Boolean.class, 		//Tag
			Timestamp.class, 	//Date Cleared
			String.class 		//Pay Rec ID
			
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
		COLUMN_EDITABLE = new boolean[] {
				false, 		//Client Name
				false, 		//Project
				false, 		//PBL ID
				false, 		//Seq No
				false, 		//PBL
				false,		//Particulars
				false,		//Amount
				false,		//AR No.
				false,		//SI No.
				false,		//SI Date				
				false,		//Bank
				false, 		//Branch
				false,		//Check No.
				false,		//Check Date
				false,		//Check Status
				true, 		//Tag
				false, 		//Date Cleared
				false, 		//Pay Rec ID
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
					false, 		//PBL ID
					false, 		//Seq No
					false, 		//PBL
					false,		//Particulars
					false,		//Amount
					false,		//AR No.
					false,		//SI No.
					false,		//SI Date					
					false,		//Bank
					false, 		//Branch
					false,		//Check No.
					false,		//Check Date
					false,		//Check Status
					true, 		//Tag
					false, 		//Date Cleared
					false, 		//Pay Rec ID
			};
		}else{
			COLUMN_EDITABLE = new boolean[] {
					false, 		//Client Name
					false, 		//Project
					false, 		//PBL ID
					false, 		//Seq No
					false, 		//PBL
					false,		//Particulars
					false,		//Amount
					false,		//AR No.
					false,		//SI No.
					false,		//SI Date					
					false,		//Bank
					false, 		//Branch
					false,		//Check No.
					false,		//Check Date
					false,		//Check Status
					false, 		//Tag
					false, 		//Date Cleared
					false, 		//Pay Rec ID
			};
		}
	}

	
	
	
}
