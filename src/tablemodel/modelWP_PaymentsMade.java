package tablemodel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelWP_PaymentsMade extends DefaultTableModel {

	private static final long serialVersionUID = 1L;

	private boolean editable = false;
	//boolean[] COLUMN_EDITABLE;

	public modelWP_PaymentsMade() {
		initThis();
	}

	public modelWP_PaymentsMade(boolean editable) {
		initThis();
	}

	public modelWP_PaymentsMade(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelWP_PaymentsMade(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelWP_PaymentsMade(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelWP_PaymentsMade(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelWP_PaymentsMade(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}

	String[] COLUMNS = new String[] {
		"Select", //0	
		"Date Paid", //1
		"Particular", //2
		"Amount", //3
		"Receipt No", //4
		"Remarks", //5
		"Rec. ID" //6
	};

	Class[] CLASS_TYPES = new Class[] {
		
		Boolean.class, //Select
		Timestamp.class, //Date Paid
		String.class, //Particular
		BigDecimal.class, //Amount
		String.class, //Receipt
		String.class, //Remarks
		Integer.class //Rec. ID
		
	};

	Boolean[] COLUMN_EDITABLE = new Boolean[]{
			
		true, //Select
		false, //Date Paid
		false, //Particular
		false, //Amount
		false, //Receipt No
		false, //Remarks
		false //Rec. ID
		
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
	}

	public Class getColumnClass(int columnIndex) {
		return CLASS_TYPES[columnIndex];
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return COLUMN_EDITABLE[columnIndex];
		//return false;
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
