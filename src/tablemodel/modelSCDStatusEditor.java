package tablemodel;

import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelSCDStatusEditor extends DefaultTableModel {

	private static final long serialVersionUID = 1L;

	public modelSCDStatusEditor() {
		initThis();
	}

	public modelSCDStatusEditor(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelSCDStatusEditor(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelSCDStatusEditor(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelSCDStatusEditor(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelSCDStatusEditor(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	private void initThis() {
		setColumnIdentifiers(new String[] {
				
				"Select", // 0
				"Status ID", //1
				"Status", //2
				"Tran Date", //3
				"Status Description" //4
				
		});
	}

	Class[] COLUMN_TYPES = new Class[] {
			
			Boolean.class, //Select
			String.class, //Status ID
			String.class, //Status
			Timestamp.class, //Tran Date
			String.class //Status Description
			
	};

	Boolean[] COLUMN_EDITABLE = new Boolean[] {
			
			true, //Select
			false, //Status ID
			false, //Status
			false, //Tran Date
			false //Status Description
			
	};

	public Class getColumnClass(int columnIndex) {
		return COLUMN_TYPES[columnIndex];
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return COLUMN_EDITABLE[columnIndex];
	}
	
	public void clear() {
		FncTables.clearTable(this);
	}

}
