package tablemodel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelLoanCreditRef extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8738810102880371204L;

	public modelLoanCreditRef() {
		initThis();
	}

	public modelLoanCreditRef(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelLoanCreditRef(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelLoanCreditRef(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelLoanCreditRef(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelLoanCreditRef(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			"Rec. ID", //0
			"Bank/Financial Institution", // 1
			"Address", // 2
			"Purpose", // 3
			"Security", // 4
			"Highest Amount Owed", // 5
			"Present Balance", // 6
			"Date Obtained", //7
			"Date Fully Paid", //8
			"Status" //9
	};


	public Class[] CLASS_TYPES = new Class[] {
			
			Integer.class, //Rec ID
			String.class, //Bank/Financial Institution
			String.class, //Address
			String.class, //Purpose
			String.class, //Security
			BigDecimal.class, //Highest Amount Owed
			BigDecimal.class, //Present Balance
			Timestamp.class, //Date Obtained
			Timestamp.class, //Date Fully Paid
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
