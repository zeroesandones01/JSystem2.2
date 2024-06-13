package tablemodel;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelMBTC_2 extends DefaultTableModel {

	private static final long serialVersionUID = -4039657810583889911L;
	private boolean editable = false;
	boolean[] COLUMNS_EDITABLE;	
	
	public modelMBTC_2() {
		InitGUI();
	}

	public modelMBTC_2(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		
	}

	public modelMBTC_2(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		
	}

	public modelMBTC_2(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		
	}

	public modelMBTC_2(Vector data, Vector columnNames) {
		super(data, columnNames);
		
	}

	public modelMBTC_2(Object[][] data, Object[] columnNames) {
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
			false,
			false,
			false
		};
	}
	
	public String [] COLUMNS = new String[] {
		"Name",
		"Project",
		"Ph/Blk/Lt",
		"Sales Group",
		"Particular",
		"Branch",
		"Debit Date",
		"Due Amt"
	};
	
	public Class [] CLASS_TYPES = new Class[] {
		String.class,
		String.class,
		String.class,
		String.class,
		String.class,
		String.class,
		Date.class,
		BigDecimal.class
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
