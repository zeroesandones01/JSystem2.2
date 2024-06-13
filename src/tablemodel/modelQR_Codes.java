/**
 * 
 */
package tablemodel;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

/**
 * @author John Lester Fatallo
 */

public class modelQR_Codes extends
		DefaultTableModel {
	
	private static final long serialVersionUID = -5067658862682090500L;

	private boolean editable = false;

	public modelQR_Codes() {
		initThis();
	}

	public modelQR_Codes(int rowCount,
			int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelQR_Codes(Vector columnNames,
			int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelQR_Codes(Object[] columnNames,
			int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelQR_Codes(Vector data,
			Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelQR_Codes(Object[][] data,
			Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}
	
	String [] COLUMNS = new String []{
		
		"Select", //0
		"Unit", //1
		"Unit ID", //2
		"Phase", //3
		"Block", //4
		"Lot", //5
		"Proj. Name" //6
		
		
	};
	
	Class [] CLASS_TYPES = new Class []{
		
		Boolean.class, //Select
		String.class, //Unit
		String.class, //Unit ID
		String.class, //Phase
		String.class, //Block
		String.class, //Lot
		String.class, //Proj Name
		
	};
	
	Boolean [] COLUMNS_EDITABLE = new Boolean []{
			
		true, //Select
		false, //Unit
		false, //Unit ID
		false, //Phase 
		false, //Block
		false, //Lot
		false //Proj Name
		
	};
	
	private void initThis(){
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
