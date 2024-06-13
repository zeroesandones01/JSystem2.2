package tablemodel;

import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class model_accesories extends DefaultTableModel {
private static final long serialVersionUID = 1L;
	
	private boolean editable = false;
	//boolean[] COLUMN_EDITABLE;

	public model_accesories() {
		initThis();
	}

	public model_accesories(boolean editable) {
		initThis();
	}

	public model_accesories(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public model_accesories(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public model_accesories(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public model_accesories(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public model_accesories(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}
	
	String[] COLUMNS = new String[] {
			"Selected",
			"Asset No.", 
			"Sub_asset_no",
			"Item_name",
			"Item_brand",
			"Serial_Model_No.",
			"Status ID",
			"Date_Created",
			"Created_by",
			"Date_retired",
			"Date_Dispose",
			"Rec_Id"
	};
	
	Class[] CLASS_TYPES = new Class[] {
			Boolean.class,//selected
			String.class, //Asset No.
			String.class,//sub_asset_no
			Object.class,//Item_name
			String.class,//item_brand
			String.class,//serial_model_no
			String.class,//status_id
			Timestamp.class,//date_created
			String.class, //created_by
			Timestamp.class,//date_retired
			Timestamp.class,//date_dispose
			Integer.class,//Rec Id
	};
	
	private void initThis() {
		setColumnIdentifiers(COLUMNS);
	}
	
	Boolean[] COLUMNS_EDITABLE = new Boolean[] {
			true, //0 selected
			false, //1 Asset No.
			false, //2 sub_asset_no
			true, //3 Item_name
			true, //4 item_brand
			true, //5 serial_model_no
			false, //6 status_id
			false, //7 
			false, //8
			false, //9
			false, //10	
			false, //11
			
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
					true, //3
					true, //4
					true, //5
					false, //6
					false, //7
					false, //8
					false, //9
					false, //10
					false, //11
					
					
			};
		}else{
			COLUMNS_EDITABLE = new Boolean[]{
					true, //0
					false, //1
					false, //2
					false, //3
					false, //4
					false, //5
					false, //6
					false, //7
					false, //8
					false, //9
					false, //10
					false, //11
					
			};
	}
		
	
}

}
