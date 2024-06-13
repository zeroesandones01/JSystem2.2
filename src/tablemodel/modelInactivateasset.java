package tablemodel;

import java.security.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelInactivateasset extends DefaultTableModel {

	private static final long serialVersionUID = 1L;
	
	private boolean editable = false;

	public modelInactivateasset() {
		initThis();
	}

	public modelInactivateasset(boolean editable) {
		initThis();
	}

	public modelInactivateasset(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelInactivateasset(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelInactivateasset(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelInactivateasset(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelInactivateasset(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}
	
	String[] COLUMNS = new String[] {
			"Selected",
			"Asset No.", 
			"Asset Code",
			"Asset Name",
			"Custodian",
	};
	
	Class[] CLASS_TYPES = new Class[] {
			Boolean.class,//checkbox
			String.class, //Asset No.
			String.class, //Asset Code
			Object.class, //Asset Name
			String.class, //Custodian 

	};
	
	private void initThis() {
		setColumnIdentifiers(COLUMNS);
	}
	
	Boolean[] COLUMNS_EDITABLE = new Boolean[] {
			 true, //0
			false, //1
			false, //2
			false, //3
			false, //4

	};
	
	
	
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
		if(editable){
			COLUMNS_EDITABLE = new Boolean[]{
					true, //0
					true, //1
					true, //2
					true, //3
					true, //4

			};
		}else{
			COLUMNS_EDITABLE = new Boolean[]{
					false, //0
					false, //1
					false, //2
					false, //3
					false, //4

			};
	}
		
	}
}

