package tablemodel;

import java.math.BigDecimal;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class modelContractorProgressBilling extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8738810102880371204L;
	
	private boolean editable = false;
	boolean[] COLUMN_EDITABLE;
	
	
	public modelContractorProgressBilling() {
		initThis();
	}

	public modelContractorProgressBilling(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelContractorProgressBilling(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelContractorProgressBilling(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelContractorProgressBilling(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelContractorProgressBilling(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			"Billing Type",	// 0
			"Tran Date",	// 1
			"Accomp %",		// 2
			"<html><center>Gross<html><br><html><center>Amount<html>", 		// 3
			"VAT",			// 4
			"WTax",			// 5
			"<html><center>DP<html><br><html><center>Recoupment<html>", 	// 6
			"<html><center>Retention<html><br><html><center>Amount<html>", 	// 7
			"<html><center>BC<html><br><html><center>Liquidation<html>", 	// 8
			"<html><center>Other<html><br><html><center>Liquidation<html>", // 9
			"Net Amt",		// 10
			"Bill No",		// 11
			"RPLF No",		// 12
			"<html><center>Date<html><br><html><center>Paid<html>", 	// 13
			"Status",		// 14
			"Remarks",		//15
			"Rec Id"
			
			};

	
	/**
	 * String.class = Alignment.CENTER
	 * Object.class = Alignment.LEFT
	 * BigDecimal.class = Alignment.RIGHT
	 * Integer.class = Alignment.CENTER
	 * Timestamp.class
	 */
	public Class[] CLASS_TYPES = new Class[] {
			String.class, 		//Billing Type
			String.class,		//Tran Date
			BigDecimal.class,	//Accomp %
			BigDecimal.class, 	//Gross Amt
			BigDecimal.class, 	//VAT
			BigDecimal.class,	//WTax	
			BigDecimal.class, 	//DP Recoupment		
			BigDecimal.class, 	//Retention Amt
			BigDecimal.class,	//BC Liquidation
			BigDecimal.class,	//Others Liquidation
			BigDecimal.class,	//Net Amt
			String.class,		//Bill No
			String.class,		//RPLF No
			String.class,		//Date Released
			Object.class,		//Status
			Object.class,		//Remarks
			Integer.class,		//Rec Id
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
		COLUMN_EDITABLE = new boolean[] {
				false, 		//Billing Type
				false,		//Tran Date
				false,		//Accomp %
				false, 		//Gross Amt
				false, 		//VAT
				false,		//WTax	
				false, 		//DP Recoupment		
				false, 		//Retention Amt
				false,		//BC Liquidation
				false,		//Others Liquidation
				false,		//Net Amt
				false,		//Bill No
				false,		//RPLF No
				false,		//Date Released
				false,		//Status
				false,		//Remarks
				false,		//Rec Id
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
					false, 		//Billing Type
					false,		//Tran Date
					true,		//Accomp %
					true, 		//Gross Amt
					true, 		//VAT
					true,		//WTax	
					true, 		//DP Recoupment		
					true, 		//Retention Amt
					true,		//BC Liquidation
					true,		//Others Liquidation
					true,		//Net Amt
					false,		//Bill No
					false,		//RPLF No
					false,		//Date Released
					false,		//Status
					true,		//Remarks
					false,		//Rec Id
			};
		}else{
			COLUMN_EDITABLE = new boolean[] {
					false, 		//Billing Type
					false,		//Tran Date
					false,		//Accomp %
					false, 		//Gross Amt
					false, 		//VAT
					false,		//WTax	
					false, 		//DP Recoupment		
					false, 		//Retention Amt
					false,		//BC Liquidation
					false,		//Others Liquidation
					false,		//Net Amt
					false,		//Bill No
					false,		//RPLF No
					false,		//Date Released
					false,		//Status
					false,		//Remarks
					false,		//Rec Id
			};
		}
	}
}