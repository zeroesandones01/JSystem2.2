package tablemodel;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelMBTC extends DefaultTableModel {

	private static final long serialVersionUID = -1793654472004111140L;
	private boolean editable = false;
	boolean[] COLUMNS_EDITABLE;	
	
	public modelMBTC() {
		InitGUI();
	}

	public modelMBTC(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		
	}

	public modelMBTC(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		
	}

	public modelMBTC(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		
	}

	public modelMBTC(Vector data, Vector columnNames) {
		super(data, columnNames);
		
	}

	public modelMBTC(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		
	}

	private void InitGUI() {
		setColumnIdentifiers(COLUMNS);
		
		COLUMNS_EDITABLE = new boolean[] {
			false,
			true,
			false,
			false,
			false,
			false,
			true,
			false,
			false
		};
	}
	
	public String [] COLUMNS = new String[] {
		"Name",						//		0
		"Tag",						//		1
		"Project",					//		2
		"Unit",						//		3
		"Account #",				//		4
		"Due Date",					//		5
		"Due Amt",					//		6
		"Particular",				//		7
		"OR Date"					//		8
	};
	
	public Class [] CLASS_TYPES = new Class[] {
		String.class,
		Boolean.class,
		String.class,
		String.class,
		String.class,
		Date.class,
		BigDecimal.class,
		String.class,
		Date.class
	};

	public Class getColumnClass(int columnIndex) {
		return CLASS_TYPES[columnIndex];
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return COLUMNS_EDITABLE[columnIndex];
	}
	
	public boolean isCellEditable(int columnIndex) {
		return COLUMNS_EDITABLE[columnIndex];
	}
	
	public void clear() {
		FncTables.clearTable(this);
	}
	
	public boolean isEditable(){
		return editable;
	}
	
	public void setEditable(boolean editable){
		this.editable = editable;
	}
}
