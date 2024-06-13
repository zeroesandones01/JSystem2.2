package tablemodel;

import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelHouseConstructedHistory extends DefaultTableModel {

	public modelHouseConstructedHistory() {
		initThis();
	}

	public modelHouseConstructedHistory(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		// TODO Auto-generated constructor stub
	}

	public modelHouseConstructedHistory(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		// TODO Auto-generated constructor stub
	}

	public modelHouseConstructedHistory(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		// TODO Auto-generated constructor stub
	}

	public modelHouseConstructedHistory(Vector data, Vector columnNames) {
		super(data, columnNames);
		// TODO Auto-generated constructor stub
	}

	public modelHouseConstructedHistory(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		// TODO Auto-generated constructor stub
	}
	
	public String[] COLUMNS = new String[] {
			
		"NTP No.", 
		"Contractor", 
		"Contract No.",
		"Duration",
		"Extension",
		"NTP Date", 
		"Work Desc", 
		"Status"
			
	};
	
	public Class[] CLASS_TYPES = new Class[] {
			
		Object.class, 
		Object.class, 
		Object.class,
		Object.class,
		Timestamp.class, 
		Timestamp.class, 
		Object.class, 
		Object.class
		
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

}
