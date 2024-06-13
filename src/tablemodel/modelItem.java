package tablemodel;

import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelItem extends DefaultTableModel {

	private static final long serialVersionUID = 1L;
	
	private boolean editable = false;
	//boolean[] COLUMN_EDITABLE;

	public modelItem() {
		initThis();
	}

	public modelItem(boolean editable) {
		initThis();
	}

	public modelItem(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelItem(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelItem(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelItem(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelItem(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}
	
	String[] COLUMNS = new String[] {
			"ITEM ID",
			"ITEM NAME", 
			"CATEGORY",
			"ID",
			
	};
	
	Class[] CLASS_TYPES = new Class[] {
			String.class, //item id
			Object.class, //item name
			Object.class, //category
			String.class //id
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

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

}


