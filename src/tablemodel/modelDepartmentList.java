package tablemodel;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelDepartmentList extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8738810102880371204L;

	public modelDepartmentList() {
		initThis();
	}

	public modelDepartmentList(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelDepartmentList(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelDepartmentList(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelDepartmentList(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelDepartmentList(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			"ID", //0
			"Department Name", // 1
			"Alias", //2
			"CO ID", //3
			"Company Name", //4
			"Div. ID", //5
			"Division Name"
	};
	
	public Class[] CLASS_TYPES = new Class[] {
			String.class, //Dept. ID
			Object.class, //Dept. Name
			String.class, //Dept. Alias
			String.class, //Company ID
			Object.class, //Company Name
			String.class, //Div. ID
			Object.class //Division Name
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
