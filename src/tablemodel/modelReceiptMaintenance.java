package tablemodel;

import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelReceiptMaintenance extends DefaultTableModel {

	private static final long serialVersionUID = -8738810102880371204L;
	boolean[] COLUMNS_EDITABLE;	
	
	public modelReceiptMaintenance() {
		initGUI();
	}

	public modelReceiptMaintenance(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelReceiptMaintenance(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelReceiptMaintenance(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelReceiptMaintenance(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelReceiptMaintenance(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			"Type", 
			"First No.", 
			"Last No.", 
			"Last Used", 
			"Cashier",
			"Company ",	
			"Branch",
			"Status", 
			"Logged Date", 
			"co_id", 
			"branch_id",  
			"receipt_id",
			"doc_id", 
			"suffix"
	};

	public Class[] CLASS_TYPES = new Class[] {
			String.class, 
			String.class, 
			String.class, 
			String.class, 
			Object.class, 
			String.class, 
			Object.class, 
			String.class, 
			Timestamp.class, 
			String.class, 
			String.class, 
			String.class, 
			String.class, 
			String.class
	};

	private void initGUI() {
		setColumnIdentifiers(COLUMNS);
		
		COLUMNS_EDITABLE = new boolean[] {
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
