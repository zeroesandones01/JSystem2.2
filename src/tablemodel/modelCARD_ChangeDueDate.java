package tablemodel;

import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelCARD_ChangeDueDate extends DefaultTableModel {

	public modelCARD_ChangeDueDate() {
		initThis();
	}

	public modelCARD_ChangeDueDate(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		// TODO Auto-generated constructor stub
	}

	public modelCARD_ChangeDueDate(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		// TODO Auto-generated constructor stub
	}

	public modelCARD_ChangeDueDate(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		// TODO Auto-generated constructor stub
	}

	public modelCARD_ChangeDueDate(Vector data, Vector columnNames) {
		super(data, columnNames);
		// TODO Auto-generated constructor stub
	}

	public modelCARD_ChangeDueDate(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		// TODO Auto-generated constructor stub
	}
	
	public String[] COLUMNS = new String[] {
			
			"Particulars", 
			"Old Schedule", 
			"New Schedule"
			
	};
	
	public Class[] CLASS_TYPES = new Class[] {
			
			Object.class, 
			Timestamp.class, 
			Timestamp.class
			
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
