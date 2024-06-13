package tablemodel;

import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;


import Functions.FncTables;

public class modelRepair extends DefaultTableModel {

	private static final long serialVersionUID = 1L;
	
	private boolean editable = false;
	//boolean[] COLUMN_EDITABLE;

	public modelRepair() {
		initThis();
	}

	public modelRepair(boolean editable) {
		initThis();
	}

	public modelRepair(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelRepair(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelRepair(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelRepair(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelRepair(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}
	
	String[] COLUMNS = new String[] {
			"Repair Date", 
			"Transacted by",
			"Old ULM",
			"Repair Cost",
			"Old Book Value",
			"Old Monthly Dep",
			"Old Cost",
			"Details"
	};
	
	Class[] CLASS_TYPES = new Class[] {
			Integer.class, //Repair Date
			Object.class, //Transacted by
			Object.class, //Old ULM
			Object.class, //Repair Cost
			Timestamp.class, //Old Book Value
			Object.class, //Old Monthly Dep
			Object.class, //Old Cost
			String.class //Details
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
