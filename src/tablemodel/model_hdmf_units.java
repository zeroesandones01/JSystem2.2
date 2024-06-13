package tablemodel;

import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import Functions.FncTables;

public class model_hdmf_units extends DefaultTableModel {

	private static final long serialVersionUID = 3011605984324388397L;
	private boolean editable = false;
	boolean[] COLUMNS_EDITABLE;	

	public model_hdmf_units() {
		InitGUI();
	}

	public model_hdmf_units(int rowCount, int columnCount) {
		super(rowCount, columnCount);

	}

	public model_hdmf_units(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);

	}

	public model_hdmf_units(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);

	}

	public model_hdmf_units(Vector data, Vector columnNames) {
		super(data, columnNames);

	}

	public model_hdmf_units(Object[][] data, Object[] columnNames) {
		super(data, columnNames);

	}
	
	private void InitGUI() {
		setColumnIdentifiers(COLUMNS);
		
		COLUMNS_EDITABLE = new boolean[] {
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
		"Checked",				
		"Project",	
		"Phase",
		"Block",
		"Lot",
		"proj_id",
		"pbl_id"
	};
	
	public Class [] CLASS_TYPES = new Class[] {
		Boolean.class, 
		String.class, 
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
