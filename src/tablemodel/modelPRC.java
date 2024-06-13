package tablemodel;

import java.util.Date;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelPRC extends DefaultTableModel {

	private static final long serialVersionUID = 6463846100929045572L;

	private boolean editable = false;
	boolean[] COLUMNS_EDITABLE;
	
	public modelPRC() {
		InitGUI();
	}

	public modelPRC(int rowCount, int columnCount) {
		super(rowCount, columnCount);

	}

	public modelPRC(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);

	}

	public modelPRC(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);

	}

	public modelPRC(Vector data, Vector columnNames) {
		super(data, columnNames);

	}

	public modelPRC(Object[][] data, Object[] columnNames) {
		super(data, columnNames);

	}
	
	private void InitGUI(){
		setColumnIdentifiers(COLUMNS);
		
		COLUMNS_EDITABLE = new boolean[] {
				false,
				true,
				false,
				false,
				false,
				false,
			};
	}
	
	public String [] COLUMNS = new String[] {
			"Name",						//	0
			"Tag",						//	1
			"Status",					//	2
			"Released",					//	3
			"Submitted",				//	4
			"Overriding"				//	5
		}; 
	
	public Class [] CLASS_TYPES = new Class[] {
			String.class,
			Boolean.class,
			String.class,
			Date.class,
			Date.class,
			String.class
		};
	
	public Class getColumnClass(int columnIndex) {
		return CLASS_TYPES[columnIndex];
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return COLUMNS_EDITABLE[columnIndex];
	}
	
	public boolean isCellEditable(int columnIndex) {
		return COLUMNS_EDITABLE[columnIndex];
	}
	
	public void clear() {
		FncTables.clearTable(this);
	}
	
	public boolean isEditable(){
		return editable;
	}
	
	public void setEditable(boolean editable){
		this.editable = editable;
	}
}