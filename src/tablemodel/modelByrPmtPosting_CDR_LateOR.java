package tablemodel;

import java.math.BigDecimal;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class modelByrPmtPosting_CDR_LateOR extends DefaultTableModel {

	private static final long serialVersionUID = 9093345505498663448L;
	private boolean editable = false;
	boolean[] COLUMN_EDITABLE;
	
	public modelByrPmtPosting_CDR_LateOR() {
		initThis();
	}

	public modelByrPmtPosting_CDR_LateOR(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		// TODO Auto-generated constructor stub
	}

	public modelByrPmtPosting_CDR_LateOR(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		// TODO Auto-generated constructor stub
	}

	public modelByrPmtPosting_CDR_LateOR(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		// TODO Auto-generated constructor stub
	}

	public modelByrPmtPosting_CDR_LateOR(Vector data, Vector columnNames) {
		super(data, columnNames);
		// TODO Auto-generated constructor stub
	}

	public modelByrPmtPosting_CDR_LateOR(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		// TODO Auto-generated constructor stub
	}


	public String[] COLUMNS = new String[] {
			"AR No.",		// 1
			"OR No.",		// 2
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

	public Class[] CLASS_TYPES = new Class[] {
			String.class, 		//AR No.
			String.class, 		//OR No.
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
				false, 		//AR No.
				false, 		//OR No.
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
					false, 		//AR No.
					false, 		//OR No.
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
					false, 		//AR No.
					false, 		//OR No.
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
