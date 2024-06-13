package tablemodel;

import java.util.Date;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import Functions.FncTables;

public class model_rem_conversion extends DefaultTableModel {

	private static final long serialVersionUID = -4265748556000935233L;
	private boolean editable = false;
	boolean[] COLUMNS_EDITABLE;	

	public model_rem_conversion() {
		InitGUI(); 
	}

	public model_rem_conversion(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		
	}

	public model_rem_conversion(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		
	}

	public model_rem_conversion(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		
	}

	public model_rem_conversion(Vector data, Vector columnNames) {
		super(data, columnNames);
		
	}

	public model_rem_conversion(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		
	}

	private void InitGUI() {
		setColumnIdentifiers(COLUMNS);
		
		COLUMNS_EDITABLE = new boolean[] {
			false, 
			false, 
			false, 
			false
		};
	}
	
	public String [] COLUMNS = new String[] {
		"Converion Status",
		"Date",
		"Remarks",
		"Accomplished by"
	};
		
	public Class [] CLASS_TYPES = new Class[] {
		Object.class, 
		Date.class, 
		Object.class, 
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
