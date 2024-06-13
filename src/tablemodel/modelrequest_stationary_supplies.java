/*package tablemodel;

import javax.swing.table.DefaultTableModel;

public class modelrequest_stationary_supplies extends DefaultTableModel {

}*/


package tablemodel;

import java.util.Vector;
import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelrequest_stationary_supplies extends DefaultTableModel {
private static final long serialVersionUID = 1L;
	
	private boolean editable = false;
	//boolean[] COLUMN_EDITABLE;

	public modelrequest_stationary_supplies() {
		initThis();
	}

	public modelrequest_stationary_supplies(boolean editable) {
		initThis();
	}

	public modelrequest_stationary_supplies(int rowCount, int columnCount) { 
		super(rowCount, columnCount);
		initThis();
	}

	public modelrequest_stationary_supplies(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelrequest_stationary_supplies(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelrequest_stationary_supplies(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelrequest_stationary_supplies(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}
	
	String[] COLUMNS = new String[] {
			"SELECTED",
			"DESCRIPTION", 
			"MODEL",
			"BRAND",
			"QTY",
			"UNIT",
			"SUPPLIER",
			"ITEM_ID",
			"NOTES"
			
	};
	
	Class[] CLASS_TYPES = new Class[] {
			
			Boolean.class, //SELECTED
			String.class, //DESCRIPTION
			String.class, //model
			String.class, //brand
			String.class, //item
			String.class, //UNIT
			String.class, //SUPPLIER
			String.class, //ITEM ID
			String.class  //NOTES
			
	};
	
	private void initThis() {
		setColumnIdentifiers(COLUMNS);
	}
	
	Boolean[] COLUMNS_EDITABLE = new Boolean[] {
			false, //0
			false, //1
			false, //2
			false, //3
			false, //4
			false, //5
			false, //6
			false, //7
			false, //8
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
					false, //1
					false, //2
					false, //3
					true, //4
					false, //5
					false, //6
					false, //7
					true, //8
			};
		}else{
			COLUMNS_EDITABLE = new Boolean[]{
					false, //0
					false, //1
					false, //2
					false, //3
					false, //4
					false, //5
					false, //6
					false, //7
					false, //8
			};
	}
		
	}
}


