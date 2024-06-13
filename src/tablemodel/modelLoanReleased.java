package tablemodel;

import java.math.BigDecimal;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelLoanReleased extends DefaultTableModel {

	private static final long serialVersionUID = 1L;

	private boolean editable = false;
	//boolean[] COLUMN_EDITABLE;

	public modelLoanReleased() {
		initThis();
	}

	public modelLoanReleased(boolean editable) {
		initThis();
	}

	public modelLoanReleased(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelLoanReleased(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelLoanReleased(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelLoanReleased(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelLoanReleased(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}

	String[] COLUMNS = new String[] {
			"Receipt No.", 		// 0
			"Project", 			// 1
			"Client's Name", 	// 2
			"Unit", 			// 3
			"Net Proceeds", 	// 4
			"Loanable Amount", 	// 5
			"SRI % Doc", 		// 6
			"Fire", 			// 7
			"Proc. Fee", 		// 8
			"Appraisal Fee", 	// 9
			"Interim MRI", 		// 10
			"Retention Fee", 	// 11
			"1st MA", 			// 12
			"Refiling Fee", 	// 13
			"10% Retention", 	// 14
			"Right of Way",		// 15
			"Water Supply",		// 16
			"Tax Dec.", 		// 17
			"Service Fee"		//18
	};

	Class[] CLASS_TYPES = new Class[] {
			String.class,		//Receipt No.
			Object.class,		//Project
			Object.class,		//Client's Name
			Object.class,		//Unit
			BigDecimal.class,	//Net Proceeds
			BigDecimal.class,	//Loanable Amount
			BigDecimal.class,	//SRI % Doc
			BigDecimal.class,	//Fire
			BigDecimal.class,	//Proc. Fee
			BigDecimal.class,	//Appraisal Fee
			BigDecimal.class,	//Interim MRI
			BigDecimal.class,	//Retention Fee
			BigDecimal.class,	//1st MA
			BigDecimal.class,	//Refiling Fee
			BigDecimal.class,	//10% Retention
			BigDecimal.class,	//Right of way
			BigDecimal.class,	//Water Supply
			BigDecimal.class,	//Tax Declaration
			BigDecimal.class //Service Fee
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
	}

	public Class getColumnClass(int columnIndex) {
		return CLASS_TYPES[columnIndex];
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	public void clear() {
		FncTables.clearTable(this);
	}

	public boolean isEditable() {
		return editable;
	}

}
