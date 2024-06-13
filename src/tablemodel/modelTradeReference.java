package tablemodel;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelTradeReference extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8738810102880371204L;

	public modelTradeReference() {
		initThis();
	}

	public modelTradeReference(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelTradeReference(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelTradeReference(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelTradeReference(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelTradeReference(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			
			"Rec. ID", //0
			"Name of Supplier", // 1
			"Address", // 2
			"Contact No./s", // 3
			"Status" // 4
			
	};

	
	public Class[] CLASS_TYPES = new Class[] {
			
			Integer.class, //Rec ID
			String.class, //Name of Supplier
			String.class, //Address
			String.class, //Contact No./s
			String.class //Status
		
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
	public void clear(){
		FncTables.clearTable(this);
	}

}
