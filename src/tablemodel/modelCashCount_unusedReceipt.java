package tablemodel;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;


public class modelCashCount_unusedReceipt extends DefaultTableModel {
	
	private static final long serialVersionUID = -8738810102880371204L;
	private boolean editable = false;
	boolean[] COLUMN_EDITABLE;


	public modelCashCount_unusedReceipt() {
		initThis();
	}

	public modelCashCount_unusedReceipt(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelCashCount_unusedReceipt(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelCashCount_unusedReceipt(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelCashCount_unusedReceipt(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelCashCount_unusedReceipt(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			"Record ID", 
			"Receipt Type", 
			"First No. ", 
			"Last No. ", 
			"First Unused",	
			"Last Unused", 
			"Character",
			"Company", 
			"co_id"
	};

	public Class[] CLASS_TYPES = new Class[] {
			String.class, 
			String.class, 
			String.class, 
			String.class, 
			String.class, 
			String.class, 
			String.class, 
			String.class, 
			String.class

	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
		COLUMN_EDITABLE = new boolean[] {
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
		return COLUMN_EDITABLE[columnIndex];
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
		if(editable){
			COLUMN_EDITABLE = new boolean[] {
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
		}else{
			COLUMN_EDITABLE = new boolean[] {
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
	}




}
