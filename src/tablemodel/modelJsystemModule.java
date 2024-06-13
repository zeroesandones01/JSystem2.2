package tablemodel;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelJsystemModule extends DefaultTableModel {

	private static final long serialVersionUID = 4452608538540881329L;

	public modelJsystemModule() {
		initThis();
	}

	public modelJsystemModule(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		// TODO Auto-generated constructor stub
	}

	public modelJsystemModule(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		// TODO Auto-generated constructor stub
	}

	public modelJsystemModule(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		// TODO Auto-generated constructor stub
	}

	public modelJsystemModule(Vector data, Vector columnNames) {
		super(data, columnNames);
		// TODO Auto-generated constructor stub
	}

	public modelJsystemModule(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		// TODO Auto-generated constructor stub
	}
	
	String [] Columns = new String[]{
			"Rec ID",
			"Select",
			"Module Name", 
			"In-charge",
			"w/ User Manual",
			"Status"
	};
	
	Boolean[] COLUMNS_EDITABLE = new Boolean[]{
			false,
			true,
			false,
			false,
			false,
			false
		
	};
	
	Class[] classType = new Class[]{
			Integer.class,
			Boolean.class,
			String.class, 
			String.class,
			Boolean.class,
			String.class
	};
	
	private void initThis() {
		setColumnIdentifiers(Columns);
	}
	
	public Class getColumnClass(int columnIndex){
		return classType[columnIndex];
	}
	
	public boolean isCellEditable(int rowIndex,int columnIndex){
		return COLUMNS_EDITABLE[columnIndex];
	}
	
	public void clear() {
		FncTables.clearTable(this);
	}

}
