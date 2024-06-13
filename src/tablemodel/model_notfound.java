package tablemodel;

import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class model_notfound extends DefaultTableModel  {

	private static final long serialVersionUID = 1L;
	
	private boolean editable = false;
	//boolean[] COLUMN_EDITABLE;

	public model_notfound() {
		initThis();
	}

	public model_notfound(boolean editable) {
		initThis();
	}

	public model_notfound(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public model_notfound(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public model_notfound(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public model_notfound(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public model_notfound(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}
	
	String[] COLUMNS = new String[] {
			"Asset No.", 
			"Asset Name",
			"category_id",
			"category_name",
			"Date Acquired",
			"Custodian ID",
			"Custodian",
			"status"
	};
	
	Class[] CLASS_TYPES = new Class[] {
			String.class, //Asset No.
			Object.class, //Asset Name
			String.class, //category id
			String.class, //category name
			Timestamp.class, //Date Acquired
			String.class, //Custodian ID
			Object.class, //Custodian
			String.class //status
	};
	
	private void initThis() {
		setColumnIdentifiers(COLUMNS);
	}
	
	Boolean[] COLUMNS_EDITABLE = new Boolean[] {
			false, //0
			false, //1
			false, //2
			false, //3
			false, //4
			false, //5
			false, //6
			false  //7
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
					true, //3
					true, //4
					true, //5
					true, //6
					true  //7
			};
		}else{
			COLUMNS_EDITABLE = new Boolean[]{
					false, //0
					false, //1
					false, //2
					false, //3
					false, //4
					false, //5
					false, //6
					false  //7
			};
	}
		
	}
}
