package tablemodel;

import java.math.BigDecimal;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelOrderOfPayments_NewPayments extends DefaultTableModel {

	private static final long serialVersionUID = 1L;
	
	private boolean editable = false;
	//boolean[] COLUMN_EDITABLE;

	public modelOrderOfPayments_NewPayments() {
		initThis();
	}

	public modelOrderOfPayments_NewPayments(boolean editable) {
		initThis();
	}

	public modelOrderOfPayments_NewPayments(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelOrderOfPayments_NewPayments(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelOrderOfPayments_NewPayments(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelOrderOfPayments_NewPayments(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelOrderOfPayments_NewPayments(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}
	
	String[] COLUMNS = new String[] {
			"Sequence", // 0
			"Client ID", // 1
			"Client", // 2
			"Unit ID", // 3
			"Unit", // 4
			"Amount", // 5
			"Time-In", // 6
			"Status", // 7
			"Transacted By", // 8
			"Project ID", // 9
			"Project" // 10
	};
	
	Class[] CLASS_TYPES = new Class[] {
			String.class, //Sequence
			String.class, //Client ID
			Object.class, //Client
			String.class, //Unit ID
			Object.class, //Unit
			BigDecimal.class, //Amount
			String.class, //Time-In
			String.class, //Status
			Object.class, //Transact By
			String.class, //Project ID
			Object.class //Project
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

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

}
