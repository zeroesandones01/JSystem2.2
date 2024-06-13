package tablemodel;

import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelsupplier extends DefaultTableModel {

	private static final long serialVersionUID = 1L;
	
	private boolean editable = false;
	//boolean[] COLUMN_EDITABLE;

	public modelsupplier() {
		initThis();
	}

	public modelsupplier(boolean editable) {
		initThis();
	}

	public modelsupplier(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelsupplier(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelsupplier(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelsupplier(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelsupplier(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}
	
	String[] COLUMNS = new String[] {
			"SUPPLIER ID",
			"SUPPLIER NAME", 
			"ADDRESS",
			"CONTACT NO",
			
	};
	
	Class[] CLASS_TYPES = new Class[] {
			Integer.class, //
			Object.class, //
			Object.class, //
			String.class //
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


