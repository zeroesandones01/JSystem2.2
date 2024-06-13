package tablemodel;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelAmountToUpdate extends DefaultTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean editable = false;
	boolean[] COLUMNS_EDITABLE;	
	
	public modelAmountToUpdate() {
		InitGUI();
	}

	public modelAmountToUpdate(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		
	}

	public modelAmountToUpdate(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		
	}

	public modelAmountToUpdate(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		
	}

	public modelAmountToUpdate(Vector data, Vector columnNames) {
		super(data, columnNames);
		
	}

	public modelAmountToUpdate(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		
	}

	private void InitGUI() {
		setColumnIdentifiers(COLUMNS);
		
		COLUMNS_EDITABLE = new boolean[] {
			false,
			false,
			false,
			false,
			false,
			false

		};
	}
	
	public String [] COLUMNS = new String[] {
		"Name",						//		0
		"ID",						//		1
		"Project",					//		2
		"Unit",						//		3
		"Due Date",					//		5
		"Due Amt"					//		6

	};
	
	public Class [] CLASS_TYPES = new Class[] {
		Object.class,
		String.class,
		String.class,
		String.class,
		String.class,
		BigDecimal.class,

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
