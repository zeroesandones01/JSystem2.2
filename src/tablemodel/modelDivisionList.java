package tablemodel;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelDivisionList extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8738810102880371204L;

	public modelDivisionList() {
		initThis();
	}

	public modelDivisionList(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelDivisionList(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelDivisionList(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelDivisionList(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelDivisionList(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			"ID", //0
			"Division Name", // 1
			"Alias", //2
			"CO ID", //3
			"Company Name" //4
	};
	
	public Class[] CLASS_TYPES = new Class[] {
			String.class, //Division ID
			Object.class, //Division Name
			String.class, //Division Alias
			String.class, //Company ID
			Object.class //Company Name
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
