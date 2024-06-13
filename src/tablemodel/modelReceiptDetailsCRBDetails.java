package tablemodel;

import java.math.BigDecimal;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class modelReceiptDetailsCRBDetails extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8738810102880371204L;

	public modelReceiptDetailsCRBDetails() {
		initThis();
	}

	public modelReceiptDetailsCRBDetails(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelReceiptDetailsCRBDetails(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelReceiptDetailsCRBDetails(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelReceiptDetailsCRBDetails(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelReceiptDetailsCRBDetails(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			"Line No", 		// 0
			"Account ID", 	// 1
			"Account Name", // 2
			"Debit", 		// 3
			"Credit", 		// 4
			"Alias", 		// 5
			"Remarks",		// 6
			"Unit ID",		// 7
			"Reference No." // 8
	};

	
	/**
	 * String.class = Alignment.CENTER
	 * Object.class = Alignment.LEFT
	 * BigDecimal.class = Alignment.RIGHT
	 * Integer.class = Alignment.CENTER
	 * Timestamp.class
	 */
	public Class[] CLASS_TYPES = new Class[] {
			Integer.class, 		//Line No.
			String.class, 		//Account ID
			Object.class, 		//Account Name
			BigDecimal.class, 	//Debit
			BigDecimal.class, 	//Credit
			String.class, 		//Alias
			String.class, 		//Remarks
			String.class,		//Unit ID
			String.class		//Reference No.
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

}
