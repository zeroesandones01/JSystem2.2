package tablemodel;

import java.math.BigDecimal;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class modelContractorChangeOrder extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8738810102880371204L;
	
	private boolean editable = false;
	boolean[] COLUMN_EDITABLE;
	
	
	public modelContractorChangeOrder() {
		initThis();
	}

	public modelContractorChangeOrder(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelContractorChangeOrder(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelContractorChangeOrder(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelContractorChangeOrder(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelContractorChangeOrder(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			"Tran",				// 0
			"Date",				// 1
			"Change Order No.",	// 2
			"Type",				// 3
			"Accomp. %",  		// 4
			"Amount",			// 5
			"VAT Amount",		// 6
			"WTax Amount",		// 7
			"Ret. Amount",		// 8
			"DP Recoup",		// 9
			"Net Amount",		// 10
			"RPLF No.",			// 11
			"Date Released",	// 12
			"Status",			// 13	
			"Rec ID"			// 14
			};
	
	/**
	 * String.class = Alignment.CENTER
	 * Object.class = Alignment.LEFT
	 * BigDecimal.class = Alignment.RIGHT
	 * Integer.class = Alignment.CENTER
	 * Timestamp.class
	 */
	public Class[] CLASS_TYPES = new Class[] {
			String.class, 		//Tran
			String.class,		//Date
			String.class,		//No.
			String.class, 		//Type
			String.class, 		//Accomplishment
			BigDecimal.class, 	//Amount
			BigDecimal.class, 	//VAT Amount
			BigDecimal.class,	//WTax Amount
			BigDecimal.class, 	//Ret. Amount
			BigDecimal.class, 	//DP Recoup.
			BigDecimal.class, 	//Net Amount
			String.class, 		//RPLF No.
			String.class, 		//Date Released
			String.class, 		//Status
			String.class 		//Rec ID
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
		COLUMN_EDITABLE = new boolean[] {
				false, 		//Tran
				false,		//Date
				false,		//No.
				false, 		//Type
				false,		//Accomplishment
				false, 		//Amount
				false, 		//VAT Amount
				false,		//WTax Amount
				false, 		//Ret. Amount
				false, 		//DP Recoup.
				false, 		//Net Amount
				false, 		//RPLF No.
				false, 		//Date Released
				false, 		//Status
				false 		//Rec ID
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
					false, 		//Tran
					false,		//Date
					false,		//No.
					false, 		//Type
					false,		//Accomplishment
					false, 		//Amount
					false, 		//VAT Amount
					false,		//WTax Amount
					false, 		//Ret. Amount
					false, 		//DP Recoup.
					false, 		//Net Amount
					false, 		//RPLF No.
					false, 		//Date Released
					false, 		//Status
					false 		//Rec ID
			};
		}else{
			COLUMN_EDITABLE = new boolean[] {
					false, 		//Tran
					false,		//Date
					false,		//No.
					false, 		//Type
					false,		//Accomplishment
					false, 		//Amount
					false, 		//VAT Amount
					false,		//WTax Amount
					false, 		//Ret. Amount
					false, 		//DP Recoup.
					false, 		//Net Amount
					false, 		//RPLF No.
					false, 		//Date Released
					false, 		//Status
					false 		//Rec ID
			};
		}
	}

	
	
	
}
