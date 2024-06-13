/*package tablemodel;

import javax.swing.table.DefaultTableModel;

public class modelscrap_monitoring extends DefaultTableModel {

}*/



package tablemodel;

import java.util.Vector;
import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelscrap_monitoring extends DefaultTableModel {
private static final long serialVersionUID = 1L;
	
	private boolean editable = false;
	//boolean[] COLUMN_EDITABLE;

	public modelscrap_monitoring() {
		initThis();
	}

	public modelscrap_monitoring(boolean editable) {
		initThis();
	}

	public modelscrap_monitoring(int rowCount, int columnCount) { 
		super(rowCount, columnCount);
		initThis();
	}

	public modelscrap_monitoring(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelscrap_monitoring(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelscrap_monitoring(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelscrap_monitoring(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}
	
	String[] COLUMNS = new String[] {
			"SCRAP ID",
			"SCRAP NAME", 
			"SCRAP DESCRIPTION."
			
			
	};
	
	Class[] CLASS_TYPES = new Class[] {
			
			String.class, //SCRAP ID
			String.class, //SCRAP NAME
			String.class //ASSET NO.
	
			
			
	};
	
	private void initThis() {
		setColumnIdentifiers(COLUMNS);
	}
	
	Boolean[] COLUMNS_EDITABLE = new Boolean[] {
			false, //0
			false, //1
			false, //2
	
			
			
	};
	
	
	
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
		if(editable){
			COLUMNS_EDITABLE = new Boolean[]{
					true, //0
					true, //1
					true, //2
				
					
					
			};
		}else{
			COLUMNS_EDITABLE = new Boolean[]{
					false, //0
					false, //1
					false, //2
				
					
					
			};
	}
		
	}
}


