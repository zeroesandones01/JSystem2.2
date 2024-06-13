
package tablemodel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelcanvassing_itemrequested extends DefaultTableModel {
private static final long serialVersionUID = 1L;
	
	private boolean editable = false;
	//boolean[] COLUMN_EDITABLE;

	public modelcanvassing_itemrequested() {
		initThis();
	}

	public modelcanvassing_itemrequested(boolean editable) {
		initThis();
	}

	public modelcanvassing_itemrequested(int rowCount, int columnCount) { 
		super(rowCount, columnCount);
		initThis();
	}

	public modelcanvassing_itemrequested(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelcanvassing_itemrequested(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelcanvassing_itemrequested(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelcanvassing_itemrequested(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}
	
	String[] COLUMNS = new String[] {
			"SELECTED",
			"ITEM_ID", 
			"ITEM",
			"UNIT",
			"QUANTITY "
	};
	
	Class[] CLASS_TYPES = new Class[] {
			
			Boolean.class, //SELECTED
			String.class, //item_id
			String.class, //item
			String.class, //unit
			String.class //quantity 
			
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

