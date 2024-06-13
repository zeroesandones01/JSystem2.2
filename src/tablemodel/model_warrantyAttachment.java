/*package tablemodel;

import javax.swing.table.DefaultTableModel;

public class model_warrantyAttachment extends DefaultTableModel {

}*/
package tablemodel;

import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class model_warrantyAttachment extends DefaultTableModel {
private static final long serialVersionUID = 1L;
	
	private boolean editable = false;
	//boolean[] COLUMN_EDITABLE;

	public model_warrantyAttachment() {
		initThis();
	}

	public model_warrantyAttachment(boolean editable) {
		initThis();
	}

	public model_warrantyAttachment(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public model_warrantyAttachment(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public model_warrantyAttachment(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public model_warrantyAttachment(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public model_warrantyAttachment(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}
	
	String[] COLUMNS = new String[] {
			"Selected",
			"Attachment No.", 
			"Attachment Desc."
	};
	
	Class[] CLASS_TYPES = new Class[] {
			Boolean.class,//selected
			String.class, //Asset No.
			String.class,//sub_asset_no
			
	};
	
	private void initThis() {
		setColumnIdentifiers(COLUMNS);
	}
	
	Boolean[] COLUMNS_EDITABLE = new Boolean[] {
			true, //0 selected
			false, //1 Asset No.
			false, //2 sub_asset_no
			
			
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
					false, //1
					false, //2
					
					
					
			};
		}else{
			COLUMNS_EDITABLE = new Boolean[]{
					true, //0
					false, //1
					false, //2
				
					
			};
	}
		
	
}

}
