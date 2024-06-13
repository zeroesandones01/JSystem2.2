package tablemodel;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelMBTC_3 extends DefaultTableModel {

	private static final long serialVersionUID = 1364080118922434706L;
	private boolean editable = false;
	boolean[] COLUMNS_EDITABLE;	
	
	public modelMBTC_3() {
		InitGUI();
	}

	public modelMBTC_3(int rowCount, int columnCount) {
		super(rowCount, columnCount);

	}

	public modelMBTC_3(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);

	}

	public modelMBTC_3(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);

	}

	public modelMBTC_3(Vector data, Vector columnNames) {
		super(data, columnNames);

	}

	public modelMBTC_3(Object[][] data, Object[] columnNames) {
		super(data, columnNames);

	}

	private void InitGUI() {
		setColumnIdentifiers(COLUMNS);
		
		COLUMNS_EDITABLE = new boolean[] {
			true,
			false,
			false,
			false,
			false,
			false,
			false,
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
		"Tag", 
		"Reason", 
		"Name",
		"Project",
		"Ph/Blk/Lt",
		"Sales Group",
		"Account Number",
		"Branch",
		"Last Debit Date",
		"Last Debit Amt", 
		"Status",
		
		"entity_id", 
		"proj_id", 
		"pbl_id", 
		"seq_no"
	};
		
	public Class [] CLASS_TYPES = new Class[] {
		Boolean.class,
		Object.class,
		Object.class,
		String.class,
		String.class,
		String.class,
		String.class,
		String.class,
		Date.class,
		BigDecimal.class,
		String.class,
		String.class,
		String.class,
		String.class,
		String.class
		
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
