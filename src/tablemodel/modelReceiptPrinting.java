package tablemodel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelReceiptPrinting extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8208500932326962074L;

	private boolean editable = false;

	public modelReceiptPrinting() {
		initThis();
	}

	public modelReceiptPrinting(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelReceiptPrinting(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelReceiptPrinting(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelReceiptPrinting(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelReceiptPrinting(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}

	String[] COLUMNS = new String[] {
			"Type", // 0
			"Particulars", // 1
			"Amount", // 2
			"OR No.", // 3
			"AR No.", // 4
			"SI No.", //5
			"PFR No.", // 6
			"Receipt Type", // 7
			"Trans. Date", // 8
			"Actual Date", // 9
			"Client ID", // 10
			"Client Name", // 11
			"PBL ID", // 12
			"Description", // 13
			"Seq.", // 14
			"Client Seq. No." // 15
	};

	Class[] CLASS_TYPES = new Class[] {
			String.class, // Type
			Object.class, // Particulars
			BigDecimal.class, // Amount
			String.class, // OR No.
			String.class, // AR No.
			String.class, // SI No.
			String.class, // AR No.
			String.class, // Receipt Type
			Timestamp.class, // Trans. Date
			Timestamp.class, // Actual Date
			String.class, // Client ID
			Object.class, // Client Name
			String.class, // PBL ID
			Object.class, // Description
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
