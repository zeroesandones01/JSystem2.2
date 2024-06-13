package tablemodel;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelCompanyList extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8738810102880371204L;

	public modelCompanyList() {
		initThis();
	}

	public modelCompanyList(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelCompanyList(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelCompanyList(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelCompanyList(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelCompanyList(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			"ID", //0
			"Name", //1
			"Alias", // 2
			"Address" //3
	};
	
	public Class[] CLASS_TYPES = new Class[] {
			String.class, //ID
			String.class, //Name
			String.class, //Alias
			String.class //Address
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
