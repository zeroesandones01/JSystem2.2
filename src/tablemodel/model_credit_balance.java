package tablemodel;

import java.math.BigDecimal;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class model_credit_balance extends DefaultTableModel {

	private static final long serialVersionUID = 2715656326843296227L;
	private boolean editable = false;
	boolean[] COLUMNS_EDITABLE;	

	public model_credit_balance() {
		initGUI();
	}

	public model_credit_balance(int rowCount, int columnCount) {
		super(rowCount, columnCount);

	}

	public model_credit_balance(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);

	}

	public model_credit_balance(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);

	}

	public model_credit_balance(Vector data, Vector columnNames) {
		super(data, columnNames);

	}

	public model_credit_balance(Object[][] data, Object[] columnNames) {
		super(data, columnNames);

	}
	
	
	private void initGUI() {
		setColumnIdentifiers(COLUMNS);
		
		COLUMNS_EDITABLE = new boolean[] {
			true, 
			false, 
			true,
			true,
			true,
			true,
			false
			
		};
	}
	
	public String [] COLUMNS = new String[] {
		"Tag", 
		"Contractor",
		"Other",
		"FS",
		"New",
		"Equity",
		"entity_id"
	};
	
	public Class [] CLASS_TYPES = new Class[] {
		Boolean.class, 
		Object.class, 
		BigDecimal.class, 
		String.class,
		Boolean.class, 
		BigDecimal.class,
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
