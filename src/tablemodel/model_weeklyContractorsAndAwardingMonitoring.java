package tablemodel;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class model_weeklyContractorsAndAwardingMonitoring extends DefaultTableModel {

	private static final long serialVersionUID = 5945975084977574503L;
	private boolean editable = false;
	boolean[] COLUMNS_EDITABLE;	
	
	public model_weeklyContractorsAndAwardingMonitoring() {
		InitGUI();
	}

	public model_weeklyContractorsAndAwardingMonitoring(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		
	}

	public model_weeklyContractorsAndAwardingMonitoring(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		
	}

	public model_weeklyContractorsAndAwardingMonitoring(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		
	}

	public model_weeklyContractorsAndAwardingMonitoring(Vector data, Vector columnNames) {
		super(data, columnNames);
		
	}

	public model_weeklyContractorsAndAwardingMonitoring(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		
	}

	private void InitGUI() {
		setColumnIdentifiers(COLUMNS);
		
		COLUMNS_EDITABLE = new boolean[] {
			false,
			true, 
			true,
			true,
			true,
			false
		};
	}
	
	public String [] COLUMNS = new String[] {
		"Contractor", 
		"Reliable",
		"PCAB",
		"Cap",
		"Other", 
		"entity_id"
	};
	
	public Class [] CLASS_TYPES = new Class[] {
		Object.class,
		Boolean.class,
		String.class,
		String.class,
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
