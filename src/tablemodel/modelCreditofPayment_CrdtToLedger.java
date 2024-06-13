package tablemodel;

import java.math.BigDecimal;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelCreditofPayment_CrdtToLedger extends DefaultTableModel {

	private static final long serialVersionUID = 1L;
	
	private boolean editable = false;
	//boolean[] COLUMN_EDITABLE;

	public modelCreditofPayment_CrdtToLedger() {
		initThis();
	}

	public modelCreditofPayment_CrdtToLedger(boolean editable) {
		initThis();
	}

	public modelCreditofPayment_CrdtToLedger(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelCreditofPayment_CrdtToLedger(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelCreditofPayment_CrdtToLedger(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelCreditofPayment_CrdtToLedger(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelCreditofPayment_CrdtToLedger(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}
	
	String[] COLUMNS = new String[] {
			"Pay Part ID", // 0
			"Particulars", // 1
			"Amount", //2
			"Pay Rec ID", //3
			"Receipt No" //4
	};
	
	Class[] CLASS_TYPES = new Class[] {
			String.class, //Pay Part ID
			String.class, //Part ID
			BigDecimal.class, //Particulars
			Integer.class, //Pay Rec ID
			String.class //Amount to Pay
	};
	
	Boolean[] COLUMNS_EDITABLE = new Boolean[] {
			false, // 0
			false, // 1
			true, // 2
			false, //3
			false //4
	};
	
	private void initThis() {
		setColumnIdentifiers(COLUMNS);
	}
	
	public Class getColumnClass(int columnIndex) {
		return CLASS_TYPES[columnIndex];
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return COLUMNS_EDITABLE[columnIndex];
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
