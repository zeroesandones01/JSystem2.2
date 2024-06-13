package tablemodel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class modelDRF_particulars_total extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8738810102880371204L;
	
	private boolean editable = false;
	boolean[] COLUMN_EDITABLE;
	
	
	public modelDRF_particulars_total() {
		initThis();
	}

	public modelDRF_particulars_total(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelDRF_particulars_total(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelDRF_particulars_total(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelDRF_particulars_total(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelDRF_particulars_total(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			"Particulars",		// 1
			"Account ID",		// 2
			"Div",				// 3
			"Dept",				// 4
			"Sec",				// 5
			"Proj",				// 6
			"Sub",       		// 7		
			"Account Desc", 	// 8
			"Amount",			// 9
			"Witihin",			// 10
			"Availer ID",		// 11
			"Availer Type",		// 12
			"Ref ID",			// 13
			"Ref No.",			// 14
			"Ref Date",			// 15
			"Availer Name",		// 16
			"Item ID",			// 17
			"Item Description",	// 18
			"Asset No.",		// 19
			"Vatable Project",	// 20
			"Vatable Entity",	// 21
			"Tax Shouldered by Co.",// 22
			"Gross Amt.",		// 23
			"VAT Rate (%)",		// 24
			"WTax ID",			// 25
			"WTax Rate (%)",	// 26
			"WTax Amount",		// 27
			"VAT Amount",		// 28
			"<html><center>Expense<html><br><html><center>Amount<html>",  	// 28
			"<html><center>Retention<html><br><html><center>Amount<html>",  // 29			
			"<html><center>DP Recoup.<html><br><html><center>Amount<html>", // 30			
			"<html><center>Payable<html><br><html><center>Amount<html>",  	// 31
			"<html><center>BC Liquidation<html><br><html><center>Amount<html>", // 32		
			"<html><center>Other Liqui.<html><br><html><center>Amount<html>",  	// 33
			"Remarks"			// 34		
			};

	
	/**
	 * String.class = Alignment.CENTER
	 * Object.class = Alignment.LEFT
	 * BigDecimal.class = Alignment.RIGHT
	 * Integer.class = Alignment.CENTER
	 * Timestamp.class
	 */
	public Class[] CLASS_TYPES = new Class[] {
			String.class, 		//Particulars
			String.class,		//Account ID
			String.class,		//Div
			String.class, 		//Dept
			String.class, 		//Sec
			String.class,		//Proj
			String.class, 		//Sub	
			Object.class, 		//Account Desc
			BigDecimal.class,	//Amount
			String.class,		//Within
			String.class,		//Availer ID
			String.class,		//Availer Type
			String.class,		//Ref ID
			String.class,		//Ref No.
			Timestamp.class,	//Ref Date
			String.class,		//Availer Name
			String.class,		//Item ID
			String.class,		//Item Description
			String.class,		//Asset No.
			String.class,		//Vatable Project
			String.class,		//Vatable Entity
			String.class,		//Tax Shouldered by Co.
			BigDecimal.class,	//Gross Amt.
			BigDecimal.class,	//VAT Rate (%)
			String.class,		//WTax ID
			BigDecimal.class,	//WTax Rate (%)
			BigDecimal.class,	//WTax Amount
			BigDecimal.class,	//VAT Amount
			BigDecimal.class,	//Expense Amount
			BigDecimal.class,	//Retention Amount
			BigDecimal.class,	//DP Recoup Amount
			BigDecimal.class,	//BC Liqui. Amount
			BigDecimal.class,	//Other Liqui. Amount
			BigDecimal.class,	//Payable Amount
			String.class		//Remarks
			
			
			
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
		COLUMN_EDITABLE = new boolean[] {
				false, 	//Particulars
				false,	//Account ID
				false,	//Div
				false, 	//Dept
				false, 	//Sec
				false,	//Proj
				false, 	//Sub	
				false, 	//Account Desc
				false,	//Amount
				false,	//Within
				false,	//Availer ID
				false,	//Availer Type
				false,	//Ref ID
				false,	//Ref No.
				false,	//Ref Date
				false,	//Availer Name
				false,	//Item ID
				false,	//Item Description
				false,	//Asset No.
				false,	//Vatable Project
				false,	//Vatable Entity
				false,	//Tax Shouldered by Co.
				false,	//Gross Amt.
				false,	//VAT Rate (%)
				false,	//WTax ID
				false,	//WTax Rate (%)
				false,	//WTax Amount
				false,	//VAT Amount
				false,	//Expense Amount
				false,	//Retention Amount				
				false,	//DP Recoup Amount
				false,	//BC Liqui Amount
				false,	//Other Liqui Amount
				false,	//Payable Amount
				false	//Remarks
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
					false, 	//Particulars
					false,	//Account ID
					false,	//Div
					false, 	//Dept
					false, 	//Sec
					false,	//Proj
					false, 	//Sub	
					false, 	//Account Desc
					false,	//Amount
					false,	//Within
					false,	//Availer ID
					false,	//Availer Type
					false,	//Ref ID
					false,	//Ref No.
					false,	//Ref Date
					false,	//Availer Name
					false,	//Item ID
					false,	//Item Description
					false,	//Asset No.
					false,	//Vatable Project
					false,	//Vatable Entity
					false,	//Tax Shouldered by Co.
					false,	//Gross Amt.
					false,	//VAT Rate (%)
					false,	//WTax ID
					false,	//WTax Rate (%)
					false,	//WTax Amount
					false,	//VAT Amount
					false,	//Expense Amount
					false,	//Retention Amount				
					false,	//DP Recoup Amount
					false,	//BC Liqui Amount
					false,	//Other Liqui Amount
					false,	//Payable Amount
					false	//Remarks
			};
		}else{
			COLUMN_EDITABLE = new boolean[] {
					false, 	//Particulars
					false,	//Account ID
					false,	//Div
					false, 	//Dept
					false, 	//Sec
					false,	//Proj
					false, 	//Sub	
					false, 	//Account Desc
					false,	//Amount
					false,	//Within
					false,	//Availer ID
					false,	//Availer Type
					false,	//Ref ID
					false,	//Ref No.
					false,	//Ref Date
					false,	//Availer Name
					false,	//Item ID
					false,	//Item Description
					false,	//Asset No.
					false,	//Vatable Project
					false,	//Vatable Entity
					false,	//Tax Shouldered by Co.
					false,	//Gross Amt.
					false,	//VAT Rate (%)
					false,	//WTax ID
					false,	//WTax Rate (%)
					false,	//WTax Amount
					false,	//VAT Amount
					false,	//Expense Amount
					false,	//Retention Amount				
					false,	//DP Recoup Amount
					false,	//BC Liqui Amount
					false,	//Other Liqui Amount
					false,	//Payable Amount
					false	//Remarks
			};
		}
	}

	
	
	
}
