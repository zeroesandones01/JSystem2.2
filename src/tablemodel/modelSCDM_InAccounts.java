package tablemodel;

import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelSCDM_InAccounts extends DefaultTableModel {

	private static final long serialVersionUID = 1L;

	public modelSCDM_InAccounts() {
		initThis();
	}

	public modelSCDM_InAccounts(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelSCDM_InAccounts(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelSCDM_InAccounts(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelSCDM_InAccounts(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelSCDM_InAccounts(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	private void initThis() {
		setColumnIdentifiers(new String[] {
				
				"Select", // 0
				"Client's Name", // 1
				"Phase-Block-Lot", // 2
				"Date Out", // 3
				"Age (No. of Days)", // 4
				"Sales Division", //5
				"Sales Agent", //6
				"Client's ID", // 7
				"Project ID", // 8
				"PBL ID", // 9
				"Sequence" // 10
				
		});
	}

	Class[] COLUMN_TYPES = new Class[] {
			
			Boolean.class, //Select
			Object.class, //Client's Name
			String.class, //Phase-Block-Lot
			Timestamp.class, //Date Out
			Integer.class, //Age (No. of Days)
			String.class, //Sales Division
			String.class, //Sales Agent
			String.class, //Client's ID
			String.class, //Project ID
			String.class, //PBL ID
			Integer.class //Sequence
			
	};

	Boolean[] COLUMN_EDITABLE = new Boolean[] {
			
			true, //Select
			false, //Client's Name
			false, //Phase-Block-Lot
			false, //Date Out
			false, //Age (No. of Days)
			false, //Sales Division
			false, //Sales Agent
			false, //Client's ID
			false, //Project ID
			false, //PBL ID
			false //Sequence
			
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
