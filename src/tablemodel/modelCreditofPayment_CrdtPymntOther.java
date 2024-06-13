package tablemodel;

import java.math.BigDecimal;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelCreditofPayment_CrdtPymntOther extends DefaultTableModel {

	private static final long serialVersionUID = 1L;
	
	private boolean editable = false;
	//boolean[] COLUMN_EDITABLE;

	public modelCreditofPayment_CrdtPymntOther() {
		initThis();
	}

	public modelCreditofPayment_CrdtPymntOther(boolean editable) {
		initThis();
	}

	public modelCreditofPayment_CrdtPymntOther(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelCreditofPayment_CrdtPymntOther(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelCreditofPayment_CrdtPymntOther(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelCreditofPayment_CrdtPymntOther(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelCreditofPayment_CrdtPymntOther(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}
	
	String[] COLUMNS = new String[] {
			"Select", // 0
			"Part ID", // 1
			"Particulars", // 2
			"Amount to Pay" //3
	};
	
	Class[] CLASS_TYPES = new Class[] {
			Boolean.class, //Select 
			String.class, //Part ID
			Object.class, //Particulars
			BigDecimal.class //Amount to Pay
	};
	
	Boolean[] COLUMNS_EDITABLE = new Boolean[] {
			true, // 0
			false, // 1
			false, // 2
			true // 3
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
