package tablemodel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Lookup._JLookup;

public class modelDRF_Particularsv2 extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8738810102880371204L;
	
	private boolean editable = false;
	boolean[] COLUMN_EDITABLE;
	private boolean[][] editableCells;
	
	public String[] COLUMNS = new String[] {
			"        Particulars      " ,		// 0
			"Account ID",		// 1
			"Div",				// 2
			"Dept",				// 3
			"Sec",				// 4			
			"Proj",				// 5			
			"Sub",       		// 6	
			"<html><center>Account<html><br><html><center>Description<html>", // 7
			"  Amount  ",		// 8
			"Within",			// 9			
			"Availer ID",		// 10			
			"Availer Type",		// 11
			"Ref ID",			// 12
			"Ref No.",			// 13
			"Ref Date",			// 14			
			"Availer Name",		// 15			
			"Item ID",			// 16
			"<html><center>Item<html><br><html><center>Description<html>",// 17
			"Asset No.",		// 18
			"<html><center>Vatable<html><br><html><center>Project<html>", // 19			
			"<html><center>Vatable<html><br><html><center>Entity<html>",  // 20			
			"Taxable",			// 21
			"Gross Amt.",		// 22
			"VAT Rate (%)",		// 23
			"WTax ID",			// 24			
			"WTax Rate (%)",	// 25			
			"WTax Amount",		// 26
			"VAT Amount",		// 27
			"<html><center>Expense<html><br><html><center>Amount<html>",  	// 28
			"<html><center>Retention<html><br><html><center>Amount<html>",  // 29			
			"<html><center>DP Recoup.<html><br><html><center>Amount<html>", // 30					
			"<html><center>BC Liquidation<html><br><html><center>Amount<html>", // 31		
			"<html><center>Other Liqui.<html><br><html><center>Amount<html>",  	// 32
			"<html><center>Payable<html><br><html><center>Amount<html>",  	// 33
			"Remarks",			// 34		
			"div",	// 35
			"dep",	// 36
			"proj",	// 37
			"sub",	// 38
			"availer"	// 39
			};
	
	
	public modelDRF_Particularsv2() {
		initThis();
	}

	public modelDRF_Particularsv2(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelDRF_Particularsv2(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelDRF_Particularsv2(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelDRF_Particularsv2(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

//	public modelDRF_Particularsv2(Object[][] data) {
//        super(data, columnNames);
//        this.editableCells = new boolean[data.length][COLUMNS.length];
//    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return editableCells[row][column];
    }

    public void setCellEditable(int row, int column, boolean editable) {
        editableCells[row][column] = editable;
    }
    
    public boolean isCellEditable(int row, int column, Boolean editable) {
    	if(column == 10 && editable) {
    		Object idValue = getValueAt(row, column);
    		
//    		if(idValue ins) {
//    			
//    		}
    		return true;
    	}else {
    		return false;
    	}
    }
	
	/**
	 * String.class = Alignment.CENTER
	 * Object.class = Alignment.LEFT
	 * BigDecimal.class = Alignment.RIGHT
	 * Integer.class = Alignment.CENTER
	 * Timestamp.class
	 */
	public Class[] CLASS_TYPES = new Class[] {
			String.class, 		//Particulars
			_JLookup.class,		//Account ID
			String.class,		//Div
			String.class, 	//Dept
			String.class, 		//Sec			
			String.class,		//Proj
			String.class, 	//Sub	
			Object.class, 		//Account Desc
			BigDecimal.class,	//Amount
			Boolean.class,		//Within			
			_JLookup.class,		//Availer ID
			_JLookup.class,		//Availer Type
			_JLookup.class,		//Ref ID
			String.class,		//Ref No.
			Timestamp.class,	//Ref Date			
			String.class,		//Availer Name
			String.class,		//Item ID
			String.class,		//Item Description
			String.class,		//Asset No.
			Boolean.class,		//Vatable Project			
			Boolean.class,		//Vatable Entity
			Boolean.class,		//Tax Shouldered by Co.
			BigDecimal.class,	//Gross Amt.
			BigDecimal.class,	//VAT Rate (%)
			_JLookup.class,		//WTax ID			
			BigDecimal.class,	//WTax Rate (%)
			BigDecimal.class,	//WTax Amount
			BigDecimal.class,	//VAT Amount
			BigDecimal.class,	//Expense Amount
			BigDecimal.class,	//Retention Amount			
			BigDecimal.class,	//DP Recoup Amount
			BigDecimal.class,	//BC Liqui. Amount
			BigDecimal.class,	//Other Liqui. Amount
			BigDecimal.class,	//Payable Amount
			String.class,		//Remarks
			String.class, 		// Ref No.
			String.class, 		// Ref No.
			String.class, 		// Ref No.
			String.class, 		// Ref No.
			String.class 		// Ref No.

			
			
			
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

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
		if(editable){
			COLUMN_EDITABLE = new boolean[] {
					true, 	//Particulars
					false,	//Account ID
					true,	//Div
					true, 	//Dept
					true, 	//Sec
					true,	//Proj
					true, 	//Sub	
					false, 	//Account Desc
					true,	//Amount
					true,	//Within
					false,	//Availer ID
					false,	//Availer Type
					false,	//Ref ID
					true,	//Ref No.
					false,	//Ref Date
					false,	//Availer Name
					false,	//Item ID
					false,	//Item Description
					false,	//Asset No.
					false,	//Vatable Project
					false,	//Vatable Entity
					true,	//Tax Shouldered by Co.
					false,	//Gross Amt.
					false,	//VAT Rate (%)
					false,	//WTax ID
					false,	//WTax Rate (%)
					false,	//WTax Amount
					false,	//VAT Amount
					false,	//Expense Amount
					true,	//Retention Amount
					true,	//DP Recoup Amount
					true,	//BC Liqui Amount
					false,	//Other Liqui Amount
					false,	//Payable Amount
					true,	//Remarks
					false,	//Retention Amount				
					false,	//DP Recoup Amount
					false,	//BC Liqui Amount
					false,	//Other Liqui Amount
					false	//Payable Amount
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