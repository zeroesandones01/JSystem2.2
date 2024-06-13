package tablemodel;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class modelSearch extends DefaultTableModel {

	private static final long serialVersionUID = 1L;

	public modelSearch() {
		initThis();
	}

	public modelSearch(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelSearch(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelSearch(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelSearch(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelSearch(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			"Client ID", // 1
			"Client Name", // 2 
			"Unit Description", // 3
			"PBL ID", // 4
			"Seq.", // 5
			"Buyer Type", // 6
			"Project ID", // 7
			"Project Name" // 8
	};
	
	private void initThis() {
		setColumnIdentifiers(COLUMNS);
	}
	
	Class[] CLASS_TYPES = new Class[] {
			String.class, //Client ID
			Object.class, //Client Name
			Object.class, //Unit Description
			String.class, //PBL ID
			Integer.class, //Sequence
			Object.class, //Buyer Type
			String.class, //Project ID
			String.class //Project Name
	};

	public Class getColumnClass(int columnIndex) {
		return CLASS_TYPES[columnIndex];
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

}
