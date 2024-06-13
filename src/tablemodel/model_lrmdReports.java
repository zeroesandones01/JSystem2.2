package tablemodel;

import java.util.Date;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class model_lrmdReports extends DefaultTableModel {

	private static final long serialVersionUID = -2449045441991696225L;
	private boolean editable = false;
	boolean[] COLUMNS_EDITABLE;	

	public model_lrmdReports() {
		InitGUI();
	}

	public model_lrmdReports(int rowCount, int columnCount) {
		super(rowCount, columnCount);

	}

	public model_lrmdReports(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);

	}

	public model_lrmdReports(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);

	}

	public model_lrmdReports(Vector data, Vector columnNames) {
		super(data, columnNames);

	}

	public model_lrmdReports(Object[][] data, Object[] columnNames) {
		super(data, columnNames);

	}
	
	private void InitGUI() {
		setColumnIdentifiers(COLUMNS);
		
		COLUMNS_EDITABLE = new boolean[] {
			true, 
			false 
			
		};
	}
	
	public String [] COLUMNS = new String[] {
		"Tag", 
		"Document Name" 
	};
	
	public Class [] CLASS_TYPES = new Class[] {
		Boolean.class, 
		Object.class 
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
