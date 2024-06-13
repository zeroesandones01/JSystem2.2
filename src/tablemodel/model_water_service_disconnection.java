package tablemodel;

import java.util.Date;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class model_water_service_disconnection extends DefaultTableModel {

	private static final long serialVersionUID = 6740460160129278615L;
	private boolean editable = false;
	boolean[] COLUMNS_EDITABLE;	
	
	public model_water_service_disconnection() {
		InitGUI(); 
	}

	public model_water_service_disconnection(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		
	}

	public model_water_service_disconnection(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		
	}

	public model_water_service_disconnection(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		
	}

	public model_water_service_disconnection(Vector data, Vector columnNames) {
		super(data, columnNames);
		
	}

	public model_water_service_disconnection(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		
	}

	private void InitGUI() {
		setColumnIdentifiers(COLUMNS);
		
		COLUMNS_EDITABLE = new boolean[] {
				true, 
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
			"Tag",
			"Name",

			"Unit",
			"Model",	
			
			"entity_id",	
			"proj_id",	
			"pbl_id",	
			"seq_no"
	};
	
	public Class [] CLASS_TYPES = new Class[] {
		Boolean.class, 
		Object.class, 
		
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
