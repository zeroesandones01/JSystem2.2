package tablemodel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class modelLiq_particular_total	 extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8738810102880371204L;
	
	private boolean editable = false;
	boolean[] COLUMN_EDITABLE;
	
	
	public modelLiq_particular_total() {
		initThis();
	}

	public modelLiq_particular_total(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelLiq_particular_total(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelLiq_particular_total(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelLiq_particular_total(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelLiq_particular_total(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			"DRF Line No." ,	// 1
			"Particulars", 		// 2
			"Account ID",		// 3
			"   Div   ",		// 4
			"   Dept  ",		// 5
			"   Sect  ",		// 6
			"   Proj  ",		// 7
			"   Sub   ",		// 8
			"Account Desc.",	// 9
			"Trans. Amt.",		// 10
			"Return Amt.",		// 11
			"Refund Amt.",		// 12
			"Payee ID",			// 13
			"Payee Type",		// 14
			"Ref ID",			// 15
			"Ref No.",			// 16
			"Ref Date",			// 17
			"Vatable Project",	// 18
			"Vatable Entity",	// 19
			"Tax Shouldered by Co.",// 20
			"Gross Amt.",		// 21
			"VAT Rate (%)",		// 22
			"WTax ID",			// 23
			"WTax Rate (%)",	// 24
			"WTax Amount",		// 25
			"VAT Amount",		// 26
			"Expense Amount",	// 27
			"CA Amount",		// 28	
			"CA Amount",		// 29
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
			String.class, 		// Particulars
			String.class, 		// Account ID
			String.class, 		//    Div   
			String.class, 		//    Dept 
			String.class, 		//    Sect 
			String.class, 		//    Proj
			String.class, 		//    Sub   
			String.class, 		// Account Desc.
			BigDecimal.class, 	// Trans. Amt.
			BigDecimal.class, 	// Return Amt.
			BigDecimal.class, 	// Refund Amt.
			String.class, 		// Payee ID
			String.class, 		// Payee Type
			String.class, 		// Ref ID
			String.class, 		// Ref No.
			Timestamp.class, 	// Ref Date
			String.class, 		// Vatable Project
			String.class, 		// Vatable Entity
			String.class, 		// Tax Shouldered by Co.
			String.class, 		// Gross Amt.
			BigDecimal.class, 	// VAT Rate (%)
			String.class, 		// WTax ID
			BigDecimal.class, 	// WTax Rate (%)
			BigDecimal.class, 	// WTax Amount
			BigDecimal.class, 	// VAT Amount
			BigDecimal.class, 	// Expense Amount
			BigDecimal.class,  	// Payable Amount
			BigDecimal.class  	// Payable Amount
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
				false, 		// Expense Amount
				false  		// Payable Amount
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
					false, 		// Expense Amount
					false  		// Payable Amount
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
					false, 		// Expense Amount
					false  		// Payable Amount
			};
		}
	}

	
	
	
}
