package tablemodel;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelOtherRequest_RequestType extends DefaultTableModel {

	
	private static final long serialVersionUID = -9189911507535210647L;

	private boolean editable = false;
	//boolean[] COLUMN_EDITABLE;

	public modelOtherRequest_RequestType() {
		initThis();
	}

	public modelOtherRequest_RequestType(boolean editable) {
		initThis();
	}

	public modelOtherRequest_RequestType(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelOtherRequest_RequestType(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelOtherRequest_RequestType(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelOtherRequest_RequestType(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelOtherRequest_RequestType(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}
	
	String[] COLUMNS = new String[] {
			
			//"Select", // 0
			"Req ID", // 1
			"Request Type", // 2
			"Buyer ID" //3
			
	};
	
	Class[] CLASS_TYPES = new Class[] {
			//Boolean.class, //Select 
			String.class, //Req ID
			String.class, //Request Type	
			String.class //Status ID
			
	};
	
	Boolean[] COLUMNS_EDITABLE = new Boolean[] {
			//true, // 0
			false, // 1
			false, // 2
			false //3
	};
	
	private void initThis() {
		setColumnIdentifiers(COLUMNS);
	}
	
	public Class getColumnClass(int columnIndex) {
		return CLASS_TYPES[columnIndex];
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return COLUMNS_EDITABLE[columnIndex];
	}
	
	public void clear() {
		FncTables.clearTable(this);
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}
	
}
