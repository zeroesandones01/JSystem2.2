package tablemodel;

import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import Functions.FncTables;

public class model_cwt_bir extends DefaultTableModel {

	private static final long serialVersionUID = -3980912379141832535L;
	private boolean editable = false;

	public model_cwt_bir() {
		initGUI(); 
	}

	public model_cwt_bir(int arg0, int arg1) {
		super(arg0, arg1);

	}

	public model_cwt_bir(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);

	}

	public model_cwt_bir(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);

	}

	public model_cwt_bir(Vector data, Vector columnNames) {
		super(data, columnNames);

	}

	public model_cwt_bir(Object[][] data, Object[] columnNames) {
		super(data, columnNames);

	}

	String [] COLUMNS = new String [] {
			"Bank", 
			"Bank Branch", 
			"Account Number", 
			"bank_id", 
			"bank_branch_id"
	};

	Class [] CLASS_TYPES = new Class []{
			String.class, 
			Object.class, 
			String.class,
			String.class,
			String.class,
	};

	Boolean [] COLUMNS_EDITABLE = new Boolean []{
			false,
			false, 
			false, 
			false, 
			false
	};

	private void initGUI(){
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
