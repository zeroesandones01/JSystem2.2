package tablemodel;

import java.lang.invoke.MethodHandles.Lookup;
import java.math.BigDecimal;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;
import Lookup._JLookup;

public class modelpurchase_receiving extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean editable = false;
	//boolean[] COLUMN_EDITABLE;

	public modelpurchase_receiving() {
		initThis();
	}

	public modelpurchase_receiving(boolean editable) {
		initThis();
	}

	public modelpurchase_receiving(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelpurchase_receiving(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelpurchase_receiving(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelpurchase_receiving(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelpurchase_receiving(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}
	
	String[] COLUMNS = new String[] {
			"Selected",
			"Supplier No.", 
			"PO No.",
			//"RS No.",
			"ID",
			"Description",
			"Unit",
			"QTY",
			"Price",
			"RPLF",
			"DR_ID"
	};
	
	Class[] CLASS_TYPES = new Class[] {
			Boolean.class, //Selected
			_JLookup.class, //Supplier No. ID
			String.class, //PO No.
			String.class, //RS No.
			String.class, //ID
			String.class, //Description
			String.class, //unit
			String.class, //QTY
			BigDecimal.class, //Price
			String.class, //RPLF
			String.class, //DR_ID
	};
	
	private void initThis() {
		setColumnIdentifiers(COLUMNS);
	}
	
	Boolean[] COLUMNS_EDITABLE = new Boolean[] {
			false, //0
			false, //1
			false, //2
			false, //3
			//false, //4
			false, //5
			false, //6
			false, //7
			false, //8
			false, //9
			false, //10
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
					false, //3
					//true, //4
					false, //5
					false, //6
					true, //7
					false, //8
					false, //9
					false, //10
					
			};
		}else{
			COLUMNS_EDITABLE = new Boolean[]{
					false, //0
					false, //1
					false, //2
					false, //3
					//false, //4
					false, //5
					false, //6
					false, //7
					false, //8
					false, //9
					false, //10
			};
	}
		
	}

}
