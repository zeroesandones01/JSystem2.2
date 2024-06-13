package tablemodel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelRefundofPayment extends DefaultTableModel {

	private static final long serialVersionUID = -8738810102880371204L;

	public modelRefundofPayment() {
		initThis();
	}

	public modelRefundofPayment(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelRefundofPayment(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelRefundofPayment(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelRefundofPayment(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelRefundofPayment(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			"Select", //0
			"Actual Date", //0
			"Pay Part ID", // 1
			"Particular", //2
			"Amount", // 3
			"Pay Rec ID", //4
			"Receipt No", //5
			"RPLF No", //6
			"Released By", //9
			"Released Date", //10
			"Received By", //11
			"Received Date" //12
	};
	
	public Class[] CLASS_TYPES = new Class[] {
			
			Boolean.class, //Select
			Timestamp.class, //Actual Date
			String.class, //Pay Part ID
			Object.class, //Particular
			BigDecimal.class, //Amount
			Integer.class, //Pay rec ID
			Object.class, //Receipt No
			String.class, //RPLF No 
			String.class, //Released By
			Timestamp.class, //Released Date
			String.class, //Received By
			Timestamp.class //Received Date
			
	};
	
	Boolean[] COLUMNS_EDITABLE = new Boolean[] {
			
			true, //0
			false, //1
			false, //2
			false, //3
			false, //4
			false, //5
			false, //6
			false, //7
			false, //8
			false, //9
			false, //10
			false, //11
			false, //12
			false  //13
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

}
