package tablemodel;

import java.math.BigDecimal;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Lookup._JLookup;

public class modelJV_SubLedger2	 extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8738810102880371204L;
	
	private boolean editable = false;
	boolean[] COLUMN_EDITABLE;
	
	
	public modelJV_SubLedger2() {
		initThis();
	}

	public modelJV_SubLedger2(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelJV_SubLedger2(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelJV_SubLedger2(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelJV_SubLedger2(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelJV_SubLedger2(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	/*public String[] COLUMNS = new String[] {
			"Entity ID  " ,	// 0
			"Entity Type" ,		// 1
			"Tran Amount" ,		// 2
			"VAT Amount" ,		// 3
			"WTax Amount" ,		// 4
			"Sundry" ,			// 5
			"Proj" ,			// 6
			"SProj",			// 7
			"Div ID" ,			// 8
			"Dept ID" ,			// 9			
			"Tax Paid by Co.",	// 10
			"Account Desc", 	// 11
			"Project Name",		// 12
			"Entity Name",		// 13
			"Entity Type Description"		// 14
			};*/ //Original
	public String[] COLUMNS = new String[] {
			"Entity ID  " ,	// 0
			"Entity Type" ,		// 1
			"Wtax Rate",
			"Vatable Entity",
			"Tran Amount" ,		// 2
			"Net Amount",
			"VAT Amount" ,		// 3
			"WTax Amount" ,		// 4
			"Sundry" ,			// 5
			"Proj" ,			// 6
			"SProj",			// 7
			"Div ID" ,			// 8
			"Dept ID" ,			// 9			
			"Tax Paid by Co.",	// 10
			"Account Desc", 	// 11
			"Project Name",		// 12
			"Entity Name",		// 13
			"Entity Type Description"		// 14
			};

	
	/**
	 * String.class = Alignment.CENTER
	 * Object.class = Alignment.LEFT
	 * BigDecimal.class = Alignment.RIGHT
	 * Integer.class = Alignment.CENTER
	 * Timestamp.class
	 */
	public Class[] CLASS_TYPES = new Class[] {
			_JLookup.class, 	//Entity ID
			_JLookup.class,		//Entity Type
			BigDecimal.class,	//Wtax Rate
			Boolean.class, 		// Vatable Entity
			BigDecimal.class, 	//Tran Amount
			BigDecimal.class, 	//Net Amount
			BigDecimal.class, 	//VAT Amount
			BigDecimal.class,	//WTax Amount
			_JLookup.class, 	//Sundry	
			String.class, 		//Proj
			String.class,		//SubProj ID
			String.class,		//Div ID
			String.class,		//Dept ID
			Boolean.class,		//Tax Paid by Co.
			String.class,		//Account Desc
			String.class,		//Project Name
			String.class,		//Entity Name
			String.class		//Entity Type Description
			
			
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
		COLUMN_EDITABLE = new boolean[] {
				false, 		//Entity ID
				false,		//Entity Type
				false,		//Wtax Rate
				false,		//Vatable Entity
				false, 		//Tran Amount
				false,		//Net Amount
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
				false		//Entity Type Description
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
					false,		//Wtax Rate
					true,		//Vatable Entity
					true, 		//Tran Amount
					false,		//Net Amount
					false, 		//VAT Amount
					false,		//WTax Amount
					false, 		//Sundry	
					true, 		//Proj
					true,		//Subproj ID
					true,		//Div ID
					true,		//Dept ID
					false,		//Tax Paid by Co.
					false,		//Account Desc
					false,		//Project Name
					false,		//Entity Name
					false		//Entity Type Description
			};
		}else{
			COLUMN_EDITABLE = new boolean[] {
					false, 		//Entity ID
					false,		//Entity Type
					false,		//wtax rate
					false,		//Vatable Entity
					false, 		//Tran Amount
					false,		//Net Amount
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
					false		//Entity Type Description
			};
		}
	}

	
	
	
}