package tablemodel;

import java.sql.Time;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelUser_Queue extends DefaultTableModel {
	
	private static final long serialVersionUID = 1L;
	
	static String [] columns = new String []{
			"Queue No.",		//0
			"Time of Arrival",	//1
			"Time Finished",	//2
			"Process"			//3
	};

	public modelUser_Queue() {
		super(new Object[][]{}, columns);
	}
	
	public Class [] type = new Class[] {
			String.class,		//0
			Time.class,			//1
			Time.class,			//2
			String.class		//3
	};
	
	Boolean[] COLUMN_EDITABLE = new Boolean[] {
			false,				//0
			false,				//1
			false,				//2
			false				//3
	};
	
	@Override
	public Class getColumnClass(int columnIndex) {
		return type[columnIndex];
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return COLUMN_EDITABLE[columnIndex];
	}
	
	public void clear() {
		FncTables.clearTable(this);
	}
}
