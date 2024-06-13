package tablemodel;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class model_CTSNotarization extends DefaultTableModel {

	private static final long serialVersionUID = -6565714986447238773L;
	private boolean editable = false;
	boolean[] COLUMNS_EDITABLE;	

	public model_CTSNotarization() {
		InitGUI();
	}

	public model_CTSNotarization(int rowCount, int columnCount) {
		super(rowCount, columnCount);

	}

	public model_CTSNotarization(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);

	}

	public model_CTSNotarization(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);

	}

	public model_CTSNotarization(Vector data, Vector columnNames) {
		super(data, columnNames);

	}

	public model_CTSNotarization(Object[][] data, Object[] columnNames) {
		super(data, columnNames);

	}

	private void InitGUI() {
		setColumnIdentifiers(COLUMNS);
		
		COLUMNS_EDITABLE = new boolean[] {
			false,
			true,
			false,
			false,
			false,
			false,
			false,
			false,
			false,
			false,
			false,
			false,
			false
		};
	}
	
	public String [] COLUMNS = new String[] {
		"Name",
		"Tag",
		"Project",
		"Unit",
		"Type",
		"Document",
		"Submitted Date",
		"Notarized Date",
		"entity_id",
		"proj_id",
		"pbl_id",
		"seq_no",
		"doc_id"
	};
	
	public Class [] CLASS_TYPES = new Class[] {
		String.class,
		Boolean.class,
		String.class,
		String.class,
		String.class,
		String.class,
		Date.class,
		Date.class,
		String.class,
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
