package tablemodel;

import java.security.Timestamp;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelbatchwithoutrplf extends DefaultTableModel {
	private static final long serialVersionUID = 1L;
	
	static String [] columns = new String []{
			"Batch No.",   	  		//0
			"Code",     			//1
			"Transaction Date"    	//2
	};

	public modelbatchwithoutrplf() {
		super(new Object[][]{}, columns);
	}
	
	public Class [] type = new Class[] {
			String.class,     		//0
			String.class,     		//1
			Timestamp.class  	  	//2
			
	};
	
	Boolean[] COLUMN_EDITABLE = new Boolean[] {
			false,				//batch no.
			false, 				//Code
			false, 				//tran date
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