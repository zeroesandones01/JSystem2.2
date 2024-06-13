package tablemodel;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;
import Lookup._JLookup;

public class modelMC_cv	 extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8738810102880371204L;
	
	private boolean editable = false;
	boolean[] COLUMN_EDITABLE;
	
	
	public modelMC_cv() {
		initThis();
	}

	public modelMC_cv(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelMC_cv(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelMC_cv(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelMC_cv(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelMC_cv(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
		"Tag",   	  		//0
		"PV No.", 			//1
		"MC Number", 		//2
	};

	public Class[] CLASS_TYPES = new Class[] {
		Boolean.class,     	//0
		_JLookup.class, 	//Account ID	
		String.class,		//MC Number
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
		COLUMN_EDITABLE = new boolean[] {
				true,
				false, 	//Account ID
				true,	//MC Number
		};
	}

	public Class getColumnClass(int columnIndex) {
		return CLASS_TYPES[columnIndex];
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return COLUMN_EDITABLE[columnIndex];
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
		if(editable){
			COLUMN_EDITABLE = new boolean[] {
					true,
					false, 	//Account ID
					true,	//MC Number
			};
		}else{
			COLUMN_EDITABLE = new boolean[] {
					true,
					false, 	//Account ID
					true,	//MC Number
			};
		}
	}

	public void clear() {
		FncTables.clearTable(this);
	}
	
	
}
