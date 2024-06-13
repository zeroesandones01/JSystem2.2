package tablemodel;

import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelnonfixedasset extends DefaultTableModel {

	private static final long serialVersionUID = 1L;
	
	private boolean editable = false;
	//boolean[] COLUMN_EDITABLE;

	public modelnonfixedasset() {
		initThis();
	}

	public modelnonfixedasset(boolean editable) {
		initThis();
	}

	public modelnonfixedasset(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelnonfixedasset(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelnonfixedasset(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelnonfixedasset(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelnonfixedasset(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}
	
	String[] COLUMNS = new String[] {
			"Selected",
			"Object No.", 
			"Asset Name",
			"Date Acquired",
			"Custodian ID",
			"Custodian",
			"Status",
			"Rec ID",
			"Asset No"
	};
	
	Class[] CLASS_TYPES = new Class[] {
			Boolean.class,//checkbox
			String.class, //object ID
			Object.class, //object Name
			Timestamp.class, //Date Acquired
			String.class, //Custodian ID
			Object.class, //Custodian
			String.class, //status
			String.class, //rec id
			String.class, //old asset no
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
			};
	}
		
	}
}
