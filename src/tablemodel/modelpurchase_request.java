package tablemodel;

import java.lang.invoke.MethodHandles.Lookup;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;
import Lookup._JLookup;

public class modelpurchase_request extends DefaultTableModel {
private static final long serialVersionUID = 1L;
	
	private boolean editable = false;
	//boolean[] COLUMN_EDITABLE;

	public modelpurchase_request() {
		initThis();
	}

	public modelpurchase_request(boolean editable) {
		initThis();
	}

	public modelpurchase_request(int rowCount, int columnCount) { 
		super(rowCount, columnCount);
		initThis();
	}

	public modelpurchase_request(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelpurchase_request(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelpurchase_request(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelpurchase_request(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}
	
	String[] COLUMNS = new String[] {
			"ITEM ID",
			"DESCRIPTION", 
			//"UNIT",
			"QTY",
			"PURPOSE"
	};
	
	Class[] CLASS_TYPES = new Class[] {
			
			_JLookup.class, //ITEM ID
			String.class, //DESCRIPTION
			//_JLookup.class, //UNIT
			String.class, //QTY
			String.class, //PURPOSE
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
					false, //0
					false, //1
					//false, //2
					true, //3
					true, //4
					
			};
		}else{
			COLUMNS_EDITABLE = new Boolean[]{
					false, //0
					false, //1
					//false, //2
					false, //3
					false, //4
					
			};
	}
		
	}
}
