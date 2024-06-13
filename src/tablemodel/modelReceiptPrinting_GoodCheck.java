package tablemodel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelReceiptPrinting_GoodCheck extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8208500932326962074L;

	private boolean editable = false;

	public modelReceiptPrinting_GoodCheck() {
		initThis();
	}

	public modelReceiptPrinting_GoodCheck(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelReceiptPrinting_GoodCheck(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelReceiptPrinting_GoodCheck(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelReceiptPrinting_GoodCheck(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelReceiptPrinting_GoodCheck(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}

	String[] COLUMNS = new String[] {
			"Type", // 0
			"Particulars", // 1
			"Amount", // 2
			"OR No.", // 3
			"OR Date", // 4
			"Issued By", // 5
			"Date Issued", // 6
			"Time Issued", // 7
			"AR No.", // 8
			"Receipt Type", // 9
			"Trans. Date", // 10
			"Actual Date", // 11
			"Client ID", // 12
			"Client Name", // 13
			"PBL ID", // 14
			"Description", // 15
			"Seq.", // 16
			"Client Seq. No." // 17
	};

	Class[] CLASS_TYPES = new Class[] {
			String.class, // Type
			Object.class, // Particulars
			BigDecimal.class, // Amount
			String.class, // OR No.
			Timestamp.class, // OR Date
			String.class, // Issued By
			Timestamp.class, // Date Issued 
			String.class, // Time Issued
			String.class, // AR No.			
			String.class, // Receipt Type
			Timestamp.class, // Trans. Date
			Timestamp.class, // Actual Date
			String.class, // Client ID
			Object.class, // Client Name
			String.class, // PBL ID
			String.class, // Description
			Integer.class, // Seq.
			String.class // Client Seq. No.
	};

	/*Boolean[] COLUMN_EDITABLE = new Boolean[]{
			true, // Select
			true, // Copies
			false, // ID
			false, // Documents
			false, // Alias
			false, // Required
			true, // Details
			false //Additional Info.
	};*/

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
	}

	public Class getColumnClass(int columnIndex) {
		return CLASS_TYPES[columnIndex];
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		//return COLUMN_EDITABLE[columnIndex];
		return false;
	}

	public void clear() {
		FncTables.clearTable(this);
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

}
