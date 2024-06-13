
package tablemodel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;
import Lookup._JLookup;

public class modelcanvass extends DefaultTableModel {
	private static final long serialVersionUID = 1L;

	private boolean editable = false;
	//boolean[] COLUMNS_EDITABLE;

	public modelcanvass() {
		initThis();
	}

	public modelcanvass(boolean editable) {
		initThis();
	}

	public modelcanvass(int rowCount, int columnCount) { 
		super(rowCount, columnCount);
		initThis();
	}

	public modelcanvass(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelcanvass(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelcanvass(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelcanvass(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}

	String[] COLUMNS = new String[] {
			"SELECTED",
			"SUPPLIER ID",
			"SUPPLIER", 
			//"TERMS",
			"SPECS",
			"ORIGINAL PRICE",
			"NEGO PRICE",
			"UNIT",
			"ITEM_ID",
			"PRODUCT ID"
	};

	Class[] CLASS_TYPES = new Class[] {

			Boolean.class, //SELECTED
			String.class,  //SUPPLIER_ID
			String.class, //SUPPLIER
			//String.class, //TERMS
			String.class, //SPECS
			BigDecimal.class, //ORIGINAL PRICE
			BigDecimal.class, //NEGO PRICE
			String.class,//UNIT
			String.class, //ITEM_ID
			String.class, //PRODUCT ID
	};


	private void initThis() {
		setColumnIdentifiers(COLUMNS);		 
	}
	
	Boolean[] COLUMNS_EDITABLE = new Boolean[] {
			false, //0
			false, //1
			false, //2
			//false, //3
			false, //4
			false, //5
			false, //6
			false, //7
			false, //8
			false, //9
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
					//true, //3
					false, //4
					false, //5
					true, //6
					false, //7
					false, //8
					false, //9
			};
		}else{
			COLUMNS_EDITABLE = new Boolean[]{
					false, //0
					false, //1
					false, //2
					//false, //3
					false, //4
					false, //5
					false, //6
					false, //7
					false, //8
					false, //9
			};
		}

	}
}
