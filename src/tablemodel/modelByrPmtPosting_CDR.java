package tablemodel;

import java.math.BigDecimal;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class modelByrPmtPosting_CDR extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8738810102880371204L;
	
	private boolean editable = false;
	boolean[] COLUMN_EDITABLE;
	
	
	public modelByrPmtPosting_CDR() {
		initThis();
	}

	public modelByrPmtPosting_CDR(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelByrPmtPosting_CDR(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelByrPmtPosting_CDR(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelByrPmtPosting_CDR(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelByrPmtPosting_CDR(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			"Type",			// 1
			"Receipt No.",	// 2
			"PBL",			// 3
			"PBL ID",		// 4
			"Seq. No.",		// 5
			"Client Name",	// 6
			"Particular",	// 7
			"Bank",			// 8
			"Check No.",	// 9
			"Check Amt.",	// 10
			"Cash Amt."	,	// 11
			"Other Amt.",	// 12
			"Total Amt.",	// 13
			"Pay Rec ID",	// 14
			"Tra Rec ID",	// 15
			"Status ID",	// 16
			"Entity ID"		// 17
			};

	
	/**
	 * String.class = Alignment.CENTER
	 * Object.class = Alignment.LEFT
	 * BigDecimal.class = Alignment.RIGHT
	 * Integer.class = Alignment.CENTER
	 * Timestamp.class
	 */
	public Class[] CLASS_TYPES = new Class[] {
			String.class, 		//Type
			String.class, 		//Receipt No.
			String.class,		//PBL
			String.class,		//PBL ID
			String.class,		//Seq No.
			Object.class,		//Client Name
			String.class,		//Particular
			String.class,		//Bank
			String.class,		//Check No.
			BigDecimal.class,	//Check Amt.
			BigDecimal.class,	//Cash Amt.
			BigDecimal.class,	//Others Amt.
			BigDecimal.class,	//Total Amt.
			Integer.class,		//Pay Rec ID
			Integer.class,		//TRA Rec ID
			String.class,		//Status ID
			String.class		//Entity ID
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
		COLUMN_EDITABLE = new boolean[] {
				false, 		//Type
				false, 		//Receipt No.
				false,		//PBL
				false,		//PBL ID
				false,		//Seq No.
				false,		//Client Name
				false,		//Particular
				false,		//Bank
				false,		//Check No.
				false,		//Check Amt.
				false,		//Cash Amt.
				false,		//Others Amt.
				false,		//Total Amt.
				false,		//Pay Rec ID
				false,		//TRA Rec ID
				false,		//Status ID
				false		//Entity ID
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
					false, 		//Type
					false, 		//Receipt No.
					false,		//PBL
					false,		//PBL ID
					false,		//Seq No.
					false,		//Client Name
					false,		//Particular
					false,		//Bank
					false,		//Check No.
					false,		//Check Amt.
					false,		//Cash Amt.
					false,		//Others Amt.
					false,		//Total Amt.
					false,		//Pay Rec ID
					false,		//TRA Rec ID
					false,		//Status ID
					false		//Entity ID
			};
		}else{
			COLUMN_EDITABLE = new boolean[] {
					false, 		//Type
					false, 		//Receipt No.
					false,		//PBL
					false,		//PBL ID
					false,		//Seq No.
					false,		//Client Name
					false,		//Particular
					false,		//Bank
					false,		//Check No.
					false,		//Check Amt.
					false,		//Cash Amt.
					false,		//Others Amt.
					false,		//Total Amt.
					false,		//Pay Rec ID
					false,		//TRA Rec ID
					false,		//Status ID
					false		//Entity ID
			};
		}
	}

	
	
	
}
