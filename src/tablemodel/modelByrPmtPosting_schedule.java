package tablemodel;

import java.math.BigDecimal;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class modelByrPmtPosting_schedule extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8738810102880371204L;
	
	private boolean editable = false;
	boolean[] COLUMN_EDITABLE;
	
	
	public modelByrPmtPosting_schedule() {
		initThis();
	}

	public modelByrPmtPosting_schedule(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelByrPmtPosting_schedule(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelByrPmtPosting_schedule(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelByrPmtPosting_schedule(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelByrPmtPosting_schedule(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			"Particular",	// 1
			"Schedule",		// 2
			"Amount",		// 3
			"Other Fees",	// 4
			"MRI",			// 5
			"FI",			// 6
			"MAF",			// 7
			"VAT",			// 8
			"Interest"	,	// 9
			"Principal",	// 10
			"Balance"		// 11
			};

	
	/**
	 * String.class = Alignment.CENTER
	 * Object.class = Alignment.LEFT
	 * BigDecimal.class = Alignment.RIGHT
	 * Integer.class = Alignment.CENTER
	 * Timestamp.class
	 */
	public Class[] CLASS_TYPES = new Class[] {
			String.class, 		//Particular
			String.class, 		//Schedule
			BigDecimal.class,	//Amount
			BigDecimal.class,	//Other Fees
			BigDecimal.class,	//MRI
			BigDecimal.class,	//FI
			BigDecimal.class,	//MAF
			BigDecimal.class,	//VAT
			BigDecimal.class,	//Interest
			BigDecimal.class,	//Principal
			BigDecimal.class	//Balance
			
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
		COLUMN_EDITABLE = new boolean[] {
				false, 	//Particular
				false, 	//Schedule
				false,	//Amount
				false,	//Other Fees
				false,	//MRI
				false,	//FI
				false,	//MAF
				false,	//VAT
				false,	//Interest
				false,	//Principal
				false	//Balance
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
					false, 	//Particular
					false, 	//Schedule
					false,	//Amount
					false,	//Other Fees
					false,	//MRI
					false,	//FI
					false,	//MAF
					false,	//VAT
					false,	//Interest
					false,	//Principal
					false	//Balance
			};
		}else{
			COLUMN_EDITABLE = new boolean[] {
					false, 	//Particular
					false, 	//Schedule
					false,	//Amount
					false,	//Other Fees
					false,	//MRI
					false,	//FI
					false,	//MAF
					false,	//VAT
					false,	//Interest
					false,	//Principal
					false	//Balance
			};
		}
	}

	
	
	
}
