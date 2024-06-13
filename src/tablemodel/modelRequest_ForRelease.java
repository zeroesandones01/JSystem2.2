package tablemodel;

import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelRequest_ForRelease extends DefaultTableModel {

	private static final long serialVersionUID = 2752936571944382819L;
	private boolean editable = false;

	public modelRequest_ForRelease() {
		initThis();
	}

	public modelRequest_ForRelease(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelRequest_ForRelease(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelRequest_ForRelease(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelRequest_ForRelease(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelRequest_ForRelease(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}
	
String[] COLUMNS = new String[] {
			
			"Request No.", //0
			"Request Date", //1
			"Remarks" //2
	};
	
	Class[] CLASS_TYPES = new Class[] {
			
			String.class, //Request No
			Timestamp.class, //Request Date
			String.class //Remarks
			
	};
	
	Boolean[] COLUMNS_EDITABLE = new Boolean[] {
			
			false, //0
			false, //1
			false //2
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
