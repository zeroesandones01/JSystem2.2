package tablemodel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class modelContractorHouseRepair extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8738810102880371204L;
	
	private boolean editable = false;
	boolean[] COLUMN_EDITABLE;
	
	
	public modelContractorHouseRepair() {
		initThis();
	}

	public modelContractorHouseRepair(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelContractorHouseRepair(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelContractorHouseRepair(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelContractorHouseRepair(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelContractorHouseRepair(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {	
			"Repair No.",		// 0
			"Date",				// 1
			"Gross Amount",		// 2
			"VAT Amount",		// 3
			"WTax Amount",		// 4
			"Premium Cost",		// 5
			"Net Amount",		// 6
			"RPLF No.",			// 7
			"Payee",			// 8		
			"Payee Type",		// 9
			"Charge To",		// 10		
			"Charge To Payee Type",	// 11
			"Amt. Charged",		// 12
			"Status",			// 13
			"Date Released",	// 14	
			"Remarks"		    // 15
			};
	
	/**
	 * String.class = Alignment.CENTER
	 * Object.class = Alignment.LEFT
	 * BigDecimal.class = Alignment.RIGHT
	 * Integer.class = Alignment.CENTER
	 * Timestamp.class
	 */
	public Class[] CLASS_TYPES = new Class[] {
			String.class, 		//Repair No.
			String.class, 		//Date
			BigDecimal.class,	//Gross Amount
			BigDecimal.class,	//VAT Amount
			BigDecimal.class, 	//WTax Amount
			BigDecimal.class, 	//Premium Cost
			BigDecimal.class, 	//Net Amount
			String.class, 		//RPLF No.
			Object.class,		//Payee
			String.class, 		//Payee Type
			Object.class,		//Charge To
			String.class, 		//Type
			BigDecimal.class, 	//Amt. Charged
			String.class, 		//Status
			Timestamp.class, 	//Date Released
			String.class, 		//Remarks
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
		COLUMN_EDITABLE = new boolean[] {
				false, 	//Repair No.
				false, 	//Date
				false,	//Gross Amount
				false,	//VAT Amount
				false, 	//WTax Amount
				false, 	//Premium Cost
				false, 	//Net Amount
				false, 	//RPLF No.
				false,	//Payee
				false, 	//Payee Type
				false,	//Charge To
				false, 	//Type
				false, 	//Amt. Charged
				false, 	//Status
				false, 	//Date Released
				false 	//Remarks
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
					false, 	//Date
					false,	//Gross Amount
					false,	//VAT Amount
					false, 	//WTax Amount
					false, 	//Premium Cost
					false, 	//Net Amount
					false, 	//RPLF No.
					false,	//Payee
					false, 	//Payee Type
					false,	//Charge To
					false, 	//Type
					false, 	//Amt. Charged
					false, 	//Status
					false, 	//Date Released
					false 	//Remarks
			};
		}else{
			COLUMN_EDITABLE = new boolean[] {
					false, 	//Repair No.
					false, 	//Date
					false,	//Gross Amount
					false,	//VAT Amount
					false, 	//WTax Amount
					false, 	//Premium Cost
					false, 	//Net Amount
					false, 	//RPLF No.
					false,	//Payee
					false, 	//Payee Type
					false,	//Charge To
					false, 	//Type
					false, 	//Amt. Charged
					false, 	//Status
					false, 	//Date Released
					false 	//Remarks
			};
		}
	}

	
	
	
}
