package tablemodel;

import java.util.Date;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class model_hdmf_postInspection_card extends DefaultTableModel {

	private static final long serialVersionUID = -476669410434654451L;

	private boolean editable = false;
	boolean[] COLUMNS_EDITABLE;	
	
	public model_hdmf_postInspection_card() {
		InitGUI();
	}

	public model_hdmf_postInspection_card(int rowCount, int columnCount) {
		super(rowCount, columnCount);

	}

	public model_hdmf_postInspection_card(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);

	}

	public model_hdmf_postInspection_card(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);

	}

	public model_hdmf_postInspection_card(Vector data, Vector columnNames) {
		super(data, columnNames);

	}

	public model_hdmf_postInspection_card(Object[][] data, Object[] columnNames) {
		super(data, columnNames);

	}

	private void InitGUI() {
		setColumnIdentifiers(COLUMNS);
		
		COLUMNS_EDITABLE = new boolean[] {
				false,
				false,
				false,
				false,
				false
			};
	}
	
	public String [] COLUMNS = new String[] {
		"Transaction Date",	
		"Status", 
		"Date Inspected", 
		"PAGIBIG Representative", 
		"Remarks"
	};
	
	public Class [] CLASS_TYPES = new Class[] { 
		Date.class, 
		String.class,
		Date.class,
		String.class,
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
