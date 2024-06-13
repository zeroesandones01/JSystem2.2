package tablemodel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelOrderOfPayments_CreditPayments extends DefaultTableModel {

	private static final long serialVersionUID = 1L;

	private boolean editable = false;
	//boolean[] COLUMN_EDITABLE;

	public modelOrderOfPayments_CreditPayments() {
		initThis();
	}

	public modelOrderOfPayments_CreditPayments(boolean editable) {
		initThis();
	}

	public modelOrderOfPayments_CreditPayments(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelOrderOfPayments_CreditPayments(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelOrderOfPayments_CreditPayments(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelOrderOfPayments_CreditPayments(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelOrderOfPayments_CreditPayments(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}

	String[] COLUMNS = new String[] {
			"Select", // 0
			"Receipt #", // 1
			"Amount", // 2
			"Trans. Date" // 3
	};

	Class[] CLASS_TYPES = new Class[] {
			Boolean.class, //Select
			Object.class, //Receipt No.
			BigDecimal.class, //Amount
			Timestamp.class //Trans. Date
	};

	Boolean[] COLUMN_EDITABLE = new Boolean[]{
			true, // Select
			false, // Receipt No.
			false, // Amount
			false // Trans. Date
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
	}

	public Class getColumnClass(int columnIndex) {
		return CLASS_TYPES[columnIndex];
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return COLUMN_EDITABLE[columnIndex];
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
