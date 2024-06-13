package tablemodel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Lookup._JLookup;

public class modelLiq_particulars	 extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8738810102880371204L;
	
	private boolean editable = false;
	boolean[] COLUMN_EDITABLE;
		
	public modelLiq_particulars() {
		initThis();
	}

	public modelLiq_particulars(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelLiq_particulars(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelLiq_particulars(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelLiq_particulars(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelLiq_particulars(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {			
			"<html><center>RPLF<html><br><html><center>Line No.<html>", 	// 0
			"Particulars", 		// 1
			"Account ID",		// 2
			"   Div   ",		// 3
			"   Dept  ",		// 4
			"   Sect  ",		// 5
			"   Proj  ",		// 6
			"   Sub   ",		// 7
			"<html><center>Account<html><br><html><center>Desc.<html>", 	// 8
			"<html><center>Trans.<html><br><html><center>Amount<html>", 	// 9
			"<html><center>Return<html><br><html><center>Amount<html>", 	// 10
			"<html><center>Refund<html><br><html><center>Amount<html>", 	// 11
			"Payee ID",			// 12
			"Payee Type",		// 13
			"Ref ID",			// 14
			"Ref No.",			// 15
			"Ref Date",			// 16
			"<html><center>Vatable<html><br><html><center>Project<html>", 	// 17
			"<html><center>Vatable<html><br><html><center>Entity<html>", 	// 18
			"<html><center>Tax Shouldered<html><br><html><center>by Comp.<html>", 	// 19
			"Gross Amt.",		// 20
			"VAT Rate (%)",		// 21
			"WTax ID",			// 22
			"WTax Rate (%)",	// 23
			"WTax Amount",		// 24
			"VAT Amount",		// 25
			"<html><center>Expense<html><br><html><center>Amount<html>", 	// 26
			"CA Amount",		// 27	
			"Orig Tran Amount",	// 28
			"div",	// 29
			"dep",	// 30
			"proj",	// 31
			"sub",	// 32
			"availer"	// 33
			};

	
	/**
	 * String.class = Alignment.CENTER
	 * Object.class = Alignment.LEFT
	 * BigDecimal.class = Alignment.RIGHT
	 * Integer.class = Alignment.CENTER
	 * Timestamp.class
	 */
	public Class[] CLASS_TYPES = new Class[] {
			
			Integer.class, 		// DRF Line No. 
			Object.class, 		// Particulars
			_JLookup.class, 	// Account ID
			String.class, 	//    Div   
			String.class, 	//    Dept 
			_JLookup.class, 	//    Sect 
			String.class, 	//    Proj
			String.class, 	//    Sub   
			Object.class, 		// Account Desc.
			BigDecimal.class, 	// Trans. Amt.
			BigDecimal.class, 	// Return Amt.
			BigDecimal.class, 	// Refund Amt.
			_JLookup.class, 	// Payee ID
			_JLookup.class, 		// Payee Type
			_JLookup.class, 	// Ref ID
			String.class, 		// Ref No.
			Timestamp.class, 	// Ref Date
			Boolean.class, 		// Vatable Project
			Boolean.class, 		// Vatable Entity
			Boolean.class, 		// Tax Shouldered by Co.
			Boolean.class, 		// Gross Amt.
			BigDecimal.class, 	// VAT Rate (%)
			_JLookup.class, 		// WTax ID
			BigDecimal.class, 	// WTax Rate (%)
			BigDecimal.class, 	// WTax Amount
			BigDecimal.class, 	// VAT Amount
			BigDecimal.class, 	// Expense Amount
			BigDecimal.class,  	// Payable Amount
			BigDecimal.class,  	// Orig Trans Amount
			String.class, 		// Ref No.
			String.class, 		// Ref No.
			String.class, 		// Ref No.
			String.class, 		// Ref No.
			String.class 		// Ref No.
			
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
		COLUMN_EDITABLE = new boolean[] {
				false, 		// DRF Line No. 
				false, 		// Particulars
				false, 		// Account ID
				false, 		//    Div   
				false, 		//    Dept 
				false, 		//    Sect 
				false, 		//    Proj
				false, 		//    Sub   
				false, 		// Account Desc.
				false, 		// Trans. Amt.
				false, 		// Return Amt.
				false, 		// Refund Amt.
				false, 		// Payee ID
				false, 		// Payee Type
				false, 		// Ref ID
				false, 		// Ref No.
				false, 		// Ref Date
				false, 		// Vatable Project
				false, 		// Vatable Entity
				false, 		// Tax Shouldered by Co.
				false, 		// Gross Amt.
				false, 		// VAT Rate (%)
				false, 		// WTax ID
				false, 		// WTax Rate (%)
				false, 		// WTax Amount
				false, 		// VAT Amount
				false, 		// Expense Amount
				false, 		// Payable Amount
				false, 		// WTax Rate (%)
				false, 		// WTax Amount
				false, 		// VAT Amount
				false, 		// Expense Amount
				false, 		// Payable Amount
				false  		// Orig Trans Amount
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

	public void setEditable(boolean editable, boolean isAmountEditable) {
		this.editable = editable;
		if(editable){
			COLUMN_EDITABLE = new boolean[] {
					false, 		// DRF Line No. 
					isAmountEditable, 		// Particulars
					false, 		// Account ID
					true, 		//    Div   
					true, 		//    Dept 
					false, 		//    Sect 
					true, 		//    Proj
					true, 		//    Sub   
					false, 		// Account Desc.
					isAmountEditable, 		// Trans. Amt.
					isAmountEditable, 		// Return Amt.
					isAmountEditable, 		// Refund Amt.
					false, 		// Payee ID
					false, 		// Payee Type
					false, 		// Ref ID
					true, 		// Ref No.
					false, 		// Ref Date
					false, 		// Vatable Project
					true, 		// Vatable Entity
					false, 		// Tax Shouldered by Co.
					false, 		// Gross Amt.
					false, 		// VAT Rate (%)
					false, 		// WTax ID
					false, 		// WTax Rate (%)
					false, 		// WTax Amount
					false, 		// VAT Amount
					false, 		// Expense Amount
					false,  	// Payable Amount
					false, 		// WTax Rate (%)
					false, 		// WTax Amount
					false, 		// VAT Amount
					false, 		// Expense Amount
					false, 		// Payable Amount
					false  		// Orig Trans Amount
			};
		}else{
			COLUMN_EDITABLE = new boolean[] {
					false, 		// DRF Line No. 
					false, 		// Particulars
					false, 		// Account ID
					false, 		//    Div   
					false, 		//    Dept 
					false, 		//    Sect 
					false, 		//    Proj
					false, 		//    Sub   
					false, 		// Account Desc.
					false, 		// Trans. Amt.
					false, 		// Return Amt.
					false, 		// Refund Amt.
					false, 		// Payee ID
					false, 		// Payee Type
					false, 		// Ref ID
					false, 		// Ref No.
					false, 		// Ref Date
					false, 		// Vatable Project
					false, 		// Vatable Entity
					false, 		// Tax Shouldered by Co.
					false, 		// Gross Amt.
					false, 		// VAT Rate (%)
					false, 		// WTax ID
					false, 		// WTax Rate (%)
					false, 		// WTax Amount
					false, 		// VAT Amount
					false, 		// Expense Amount
					false, 		// Payable Amount
					false, 		// WTax Rate (%)
					false, 		// WTax Amount
					false, 		// VAT Amount
					false, 		// Expense Amount
					false, 		// Payable Amount
					false  		// Orig Trans Amount
			};
		}
	}
	
	

	
	
	
}
