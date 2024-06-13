
 package tablemodel;
 
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelIssuanceOfSupplies extends DefaultTableModel {
private static final long serialVersionUID = 1L;
	
	private boolean editable = false;
	//boolean[] COLUMN_EDITABLE;

	public modelIssuanceOfSupplies() {
		initThis();
	}

	public modelIssuanceOfSupplies(boolean editable) {
		initThis();
	}

	public modelIssuanceOfSupplies(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelIssuanceOfSupplies(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelIssuanceOfSupplies(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelIssuanceOfSupplies(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelIssuanceOfSupplies(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}
	
	String[] COLUMNS = new String[] {
			"SELECTED",
			"ID",
			"DESCRIPTION", 
			"QTY",
			"UNIT",
			"FOR RELEASE",
			"SUPPLIER"
	};
	
	Class[] CLASS_TYPES = new Class[] {
			Boolean.class, //SELECTED
			String.class, //ID
			String.class, //DESCRIPTION
			String.class, //QTY
			String.class, //UNIT
			String.class, //ACTUAL QTY
			String.class, //SUPPLIER
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
					false, //4
					true, //5
					false, //6
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
					
			};
	}
		
	}
}
