package tablemodel;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelClientComplaints extends DefaultTableModel {
	
	private static final long serialVersionUID = -8738810102880371204L;

	public modelClientComplaints() {
		initThis();
	}

	public modelClientComplaints(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelClientComplaints(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelClientComplaints(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelClientComplaints(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelClientComplaints(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}

	public String[] COLUMNS = new String[] {
			"Complaint No", //0
			"Category Code", // 1
			"Category", //2
			"Source Code", //3
			"Source", //4
			"Addressed to Code", //5
			"Addressed to", //6
			"Contact No.", //7
			"Complaint Details" //8
	};
	
	public Class[] CLASS_TYPES = new Class[] {
			
			String.class, //Complaint No
			String.class, //Category Code
			String.class, //Category
			String.class, //Source Code
			String.class, //Source
			String.class, //Addressed To Code
			String.class, //Addressed To
			String.class, //Contact No.
			String.class //Complaint Details
			
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

}
