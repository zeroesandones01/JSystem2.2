package tablemodel;

import java.util.Date;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class model_hdmf_preInspection extends DefaultTableModel {

	private static final long serialVersionUID = -3106074375648889129L;
	private boolean editable = false;
	boolean[] COLUMNS_EDITABLE;	

	public model_hdmf_preInspection() {
		InitGUI();
	}

	public model_hdmf_preInspection(int rowCount, int columnCount) {
		super(rowCount, columnCount);

	}

	public model_hdmf_preInspection(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);

	}

	public model_hdmf_preInspection(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);

	}

	public model_hdmf_preInspection(Vector data, Vector columnNames) {
		super(data, columnNames);

	}

	public model_hdmf_preInspection(Object[][] data, Object[] columnNames) {
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
			false,
			false
		};
	}
	
	public String [] COLUMNS = new String[] {
		"Name",	
		"Tag",			
		"Phase",
		"Block",
		"Lot",
		"Date Filed",
		"NOA Released",
		"House %",
		"to CMD",
		"CMD to PMD",
		"entity_id",
		"proj_id",
		"pbl_id",
		"seq_no"
	};
	
	public Class [] CLASS_TYPES = new Class[] {
		Object.class,
		Boolean.class,
		String.class,
		String.class,
		String.class,
		Date.class, 
		Date.class, 
		String.class,
		Date.class,
		Date.class,
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
