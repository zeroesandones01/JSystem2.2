package tablemodel;

import java.math.BigDecimal;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class modelByrPmtPosting_ledger extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8738810102880371204L;
	
	private boolean editable = false;
	boolean[] COLUMN_EDITABLE;
	
	
	public modelByrPmtPosting_ledger() {
		initThis();
	}

	public modelByrPmtPosting_ledger(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelByrPmtPosting_ledger(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelByrPmtPosting_ledger(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelByrPmtPosting_ledger(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelByrPmtPosting_ledger(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			"Actual Date",	// 1
			"Sched Date",	// 2
			"Amount Paid",	// 3
			"Other Fees",	// 4
			"Proc. Fees",	// 5
			"RES",			// 6
			"DP",			// 7
			"MRI",			// 8
			"FI"	,		// 9
			"MAF.",			// 10
			"VAT",			// 11
			"SOI",			// 12
			"SOP",			// 13
			"CBP",			// 14
			"Adjustment",	// 15
			"Interest",		// 16
			"Principal",	// 17
			"Balance"		// 18
			};

	
	/**
	 * String.class = Alignment.CENTER
	 * Object.class = Alignment.LEFT
	 * BigDecimal.class = Alignment.RIGHT
	 * Integer.class = Alignment.CENTER
	 * Timestamp.class
	 */
	public Class[] CLASS_TYPES = new Class[] {
			String.class, 		//Actual Date
			String.class, 		//Sched Date
			BigDecimal.class,	//Amount Paid
			BigDecimal.class,	//Other Fees
			BigDecimal.class,	//Proc. Fees
			BigDecimal.class,	//RES
			BigDecimal.class,	//DP
			BigDecimal.class,	//MRI
			BigDecimal.class,	//FI
			BigDecimal.class,	//MAF
			BigDecimal.class,	//VAT
			BigDecimal.class,	//SOI
			BigDecimal.class,	//SOP
			BigDecimal.class,	//CBP
			BigDecimal.class,	//Adjustment
			BigDecimal.class,	//Interest
			BigDecimal.class,	//Principal
			BigDecimal.class	//Balance
			
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
		COLUMN_EDITABLE = new boolean[] {
				false, 	//Actual Date
				false, 	//Sched Date
				false,	//Amount Paid
				false,	//Other Fees
				false,	//Proc. Fees
				false,	//RES
				false,	//DP
				false,	//MRI
				false,	//FI
				false,	//MAF
				false,	//VAT
				false,	//SOI
				false,	//SOP
				false,	//CBP
				false,	//Adjustment
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
					false, 	//Actual Date
					false, 	//Sched Date
					false,	//Amount Paid
					false,	//Other Fees
					false,	//Proc. Fees
					false,	//RES
					false,	//DP
					false,	//MRI
					false,	//FI
					false,	//MAF
					false,	//VAT
					false,	//SOI
					false,	//SOP
					false,	//CBP
					false,	//Adjustment
					false,	//Interest
					false,	//Principal
					false	//Balance
			};
		}else{
			COLUMN_EDITABLE = new boolean[] {
					false, 	//Actual Date
					false, 	//Sched Date
					false,	//Amount Paid
					false,	//Other Fees
					false,	//Proc. Fees
					false,	//RES
					false,	//DP
					false,	//MRI
					false,	//FI
					false,	//MAF
					false,	//VAT
					false,	//SOI
					false,	//SOP
					false,	//CBP
					false,	//Adjustment
					false,	//Interest
					false,	//Principal
					false	//Balance
			};
		}
	}

	
	
	
}
