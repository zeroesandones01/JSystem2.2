package tablemodel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelSCDM_Accounts extends DefaultTableModel {

	private static final long serialVersionUID = 1L;

	public modelSCDM_Accounts() {
		initThis();
	}

	public modelSCDM_Accounts(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelSCDM_Accounts(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelSCDM_Accounts(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelSCDM_Accounts(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelSCDM_Accounts(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	private void initThis() {
		setColumnIdentifiers(new String[] {
				
				"Select", // 0
				"Unit Desc", //1
				"Client's Name", // 2
				"Model", //3
				"TR Date", //4
				"OR Date", // 5
				"Sales Group", // 6
				"Network", // 7
				"Sales Person", // 8
				"Batch No.", // 9
				"Client's ID", // 10
				"Project ID", // 11
				"PBL ID", // 12
				"Sequence", // 13
				"Coapplicant", //14
				"Loan Amt", //15
				"Company Name", //16
				"Company Alias", //17
				"Proj. Name", //18
				"Coordinator", //19
				"Sales Dept." //20
				
		});
	}

	Class[] COLUMN_TYPES = new Class[] {
			
			Boolean.class, //Select
			String.class, //Unit Desc
			Object.class, //Client's Name
			String.class, //House Model
			Timestamp.class, //TR Date
			Timestamp.class, //OR Date
			Object.class, //Sales Group
			Object.class, //Network
			Object.class, //Sales Person
			Object.class, //Batch No.
			String.class, //Client's ID
			String.class, //Project ID
			String.class, //PBL ID
			Integer.class, //Sequence
			String.class, //Coapplicant
			BigDecimal.class, //Loan Amt
			String.class, //Company Name
			String.class, //Company Alias
			String.class, //Proj. Name
			String.class, //Coordinator
			String.class //Sales Dept
			
	};

	Boolean[] COLUMN_EDITABLE = new Boolean[] {
			
			true, //Select
			false, //Unit Desc
			false, //Client's Name
			false, //House Model
			false, //TR Date
			false, //OR Date
			false, //Sales Group
			false, //Network
			false, //Sales Person
			false, //Batch No.
			false, //Client's ID
			false, //Project ID
			false, //PBL ID
			false, //Sequence
			false, //Coapplicant
			false, //Loan Amt
			false, //Company Name
			false, //Company Alias
			false, //Proj. Name
			false, //Coordinator
			false //Sales Dept
			
	};

	public Class getColumnClass(int columnIndex) {
		return COLUMN_TYPES[columnIndex];
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return COLUMN_EDITABLE[columnIndex];
	}
	
	public void clear() {
		FncTables.clearTable(this);
	}

}
