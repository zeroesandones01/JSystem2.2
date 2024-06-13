package tablemodel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelLoanCredits extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8738810102880371204L;

	public modelLoanCredits() {
		initThis();
	}

	public modelLoanCredits(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelLoanCredits(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelLoanCredits(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelLoanCredits(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelLoanCredits(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			
			"Rec. ID", //0
			"Creditor Name/Address", // 1
			"Security", // 2
			"Loan Type", // 3
			"Loan Amount", // 4
			"M.A.", // 5
			"Maturity Date", // 6
			"Balance", //7
			"Status" //8
	};

	
	/**
	 * String.class = Alignment.CENTER
	 * Object.class = Alignment.LEFT
	 * BigDecimal.class = Alignment.RIGHT
	 * Integer.class = Alignment.CENTER
	 * 
	 */
	public Class[] CLASS_TYPES = new Class[] {
			
			Integer.class, //Rec ID
			String.class, //Creditor Name/Address
			String.class, //Security
			String.class, //Loan Type
			BigDecimal.class, //Loan Amount
			BigDecimal.class, //M.A.
			Timestamp.class, //Maturity Date
			BigDecimal.class, //Balance
			String.class //Status
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
	public void clear(){
		FncTables.clearTable(this);
	}

}
