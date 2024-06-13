package tablemodel;

import java.math.BigDecimal;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Lookup._JLookup;

public class modelPV_SubLedger	 extends DefaultTableModel {

	/**
	 * 
	 */
	//**EDITED BY JED 2020-08-03 : ADDITIONAL COLUMN FOR DISPLAYING TAG DETAILS IN SL**//
	private static final long serialVersionUID = -8738810102880371204L;
	
	private boolean editable = false;
	boolean[] COLUMN_EDITABLE;
	
	
	public modelPV_SubLedger() {
		initThis();
	}

	public modelPV_SubLedger(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelPV_SubLedger(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelPV_SubLedger(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelPV_SubLedger(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelPV_SubLedger(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			" Entity ID  " ,	// 1
			"Entity Type" ,		// 2
			"Tran Amount" ,		// 3
			"VAT Amount" ,		// 4
			"WTax Amount" ,		// 5
			"Sundry" ,			// 6
			"Proj" ,			// 7
			"SProj",			// 8
			"Div ID" ,			// 9
			"Dept ID" ,			// 10			
			"Tax Paid by Co.",	// 11
			"Account Desc", 	// 12
			"Project Name",		// 13
			"Entity Name",		// 14
			"Entity Type Description",		// 15
			"div",	// 16
			"dep",	// 17
			"proj",	// 18
			"sub",	// 19
			};

	
	/**
	 * String.class = Alignment.CENTER
	 * Object.class = Alignment.LEFT
	 * BigDecimal.class = Alignment.RIGHT
	 * Integer.class = Alignment.CENTER
	 * Timestamp.class
	 */
	public Class[] CLASS_TYPES = new Class[] {
			String.class, 		//Entity ID
			String.class,		//Entity Type
			BigDecimal.class, 	//Tran Amount
			BigDecimal.class, 	//VAT Amount
			BigDecimal.class,	//WTax Amount
			String.class, 		//Sundry	
			_JLookup.class, 	//Proj
			_JLookup.class,		//SubProj ID
			_JLookup.class,		//Div ID
			_JLookup.class,		//Dept ID
			Boolean.class,		//Tax Paid by Co.
			String.class,		//Account Desc
			String.class,		//Project Name
			String.class,		//Entity Name
			String.class,		//Entity Type Description
			String.class,		//div
			String.class,		//dep
			String.class,		//proj
			String.class		//sub
			
			
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
		COLUMN_EDITABLE = new boolean[] {
				false, 		//Entity ID
				false,		//Entity Type
				false, 		//Tran Amount
				false, 		//VAT Amount
				false,		//WTax Amount
				false, 		//Sundry	
				false, 		//Proj
				false,		//Subproj ID
				false,		//Div ID
				false,		//Dept ID
				false,		//Tax Paid by Co.
				false,		//Account Desc
				false,		//Project Name
				false,		//Entity Name
				false,		//Entity Type Description
				false,		//div
				false,		//dep
				false,		//proj
				false		//sub
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
					false, 		//Entity ID
					false,		//Entity Type
					false, 		//Tran Amount
					false, 		//VAT Amount
					false,		//WTax Amount
					false, 		//Sundry	
					false, 		//Proj
					false,		//Subproj ID
					false,		//Div ID
					false,		//Dept ID
					false,		//Tax Paid by Co.
					false,		//Account Desc
					false,		//Project Name
					false,		//Entity Name
					false,		//Entity Type Description
					false,		//div
					false,		//dep
					false,		//proj
					false		//sub
			};
		}else{
			COLUMN_EDITABLE = new boolean[] {
					false, 		//Entity ID
					false,		//Entity Type
					false, 		//Tran Amount
					false, 		//VAT Amount
					false,		//WTax Amount
					false, 		//Sundry	
					false, 		//Proj
					false,		//Subproj ID
					false,		//Div ID
					false,		//Dept ID
					false,		//Tax Paid by Co.
					false,		//Account Desc
					false,		//Project Name
					false,		//Entity Name
					false,		//Entity Type Description
					false,		//div
					false,		//dep
					false,		//proj
					false		//sub
			};
		}
	}

	
	
	
}
