/*
 * package tablemodel;
 * 
 * import javax.swing.table.DefaultTableModel;
 * 
 * public class modelsupplier_procurement extends DefaultTableModel {
 * 
 * }
 */

 package tablemodel;
 
import java.math.BigDecimal;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;
import Lookup._JLookup;

public class modelsupplier_procurement extends DefaultTableModel {
private static final long serialVersionUID = 1L;
	
	private boolean editable = false;
	//boolean[] COLUMN_EDITABLE;

	public modelsupplier_procurement() {
		initThis();
	}

	public modelsupplier_procurement(boolean editable) {
		initThis();
	}

	public modelsupplier_procurement(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelsupplier_procurement(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelsupplier_procurement(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelsupplier_procurement(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelsupplier_procurement(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}
	
	String[] COLUMNS = new String[] {
			"PRODUCT ID",
			"PRODUCT NAME",
			"ITEM ID",
			"ITEM NAME",
			"CATEGORY ID",
			"CATEGORY NAME",
			"MODEL", 
			"BRAND",
			"UNIT COST",
			"UNIT",
			"ITEM UNIT"
			//"TERMS"
			
	};
	
	Class[] CLASS_TYPES = new Class[] {
			
			String.class, //PRODUCT ID
			String.class,//PRODUCT NAME
			_JLookup.class,//ITEM ID
			String.class,//ITEM NAME
			String.class,//CATEGORY ID
			String.class,//CATEGORY NAME
			String.class, //MODEL
			String.class, //BRAND
			BigDecimal.class, //UNIT COST
			Integer.class, //UNIT
			_JLookup.class, //ITEM UNIT
			//_JLookup.class, //TERMS
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
			false, //9
			false, //10
			//false, //11
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
					false, //2
					true, //3
					true, //4
					true, //5
					true, //6
					true, //7
					true, //8
					true, //9
					false, //10
					//true, //11
					
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
					false, //9
					false, //10
					//false, //11
			};
	}
		
	}
}
