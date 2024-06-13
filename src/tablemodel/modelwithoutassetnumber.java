
package tablemodel;

import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelwithoutassetnumber extends DefaultTableModel  {

	private static final long serialVersionUID = 1L;
	
	private boolean editable = false;
	//boolean[] COLUMN_EDITABLE;

	public modelwithoutassetnumber() {
		initThis();
	}

	public modelwithoutassetnumber(boolean editable) {
		initThis();
	}

	public modelwithoutassetnumber(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelwithoutassetnumber(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelwithoutassetnumber(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelwithoutassetnumber(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelwithoutassetnumber(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}
	
	String[] COLUMNS = new String[] {
			"Item Name",
			"Batch No",
			"Serial No",
			"Model No",
			"Description",
			"Dept ID",
			"Date Disposed.", 
			"Disposed By",
			"Line No",

	};
	
	Class[] CLASS_TYPES = new Class[] {
			String.class,	//Item Name
			String.class, 	//Status
			String.class, 	//Serial No
			String.class, 	//Model No 
			String.class, 	//Description 
			String.class, 	//Dept ID
			Timestamp.class,//Date Disposed
			String.class, 	// Disposed By
			String.class, 	//Line No

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
			false, //7
			false, //8
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
					true, //7
					true, //8
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
					false, //7
					false, //8
			};
	}
		
	}
}


