/*package tablemodel;

public class modelassetdisbursement {

	public modelassetdisbursement() {
		// TODO Auto-generated constructor stub
	}

}*/
package tablemodel;

import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelassetdisbursement extends DefaultTableModel  {

	private static final long serialVersionUID = 1L;
	
	private boolean editable = false;
	//boolean[] COLUMN_EDITABLE;

	public modelassetdisbursement() {
		initThis();
	}

	public modelassetdisbursement(boolean editable) {
		initThis();
	}

	public modelassetdisbursement(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelassetdisbursement(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelassetdisbursement(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelassetdisbursement(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelassetdisbursement(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}
	
	String[] COLUMNS = new String[] {
			"Request_ID",
			"Asset No.", 
			"RPLF No",
			"Asset Name",
			"Date Requested",
			"ID",
	};
	
	Class[] CLASS_TYPES = new Class[] {
			String.class,//Request_ID
			String.class, //Asset No.
			String.class, //RPLF
			Object.class, //Asset Name
			Timestamp.class, //Date Acquired
			String.class,//ID
			
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
					
			};
		}else{
			COLUMNS_EDITABLE = new Boolean[]{
					false, //0
					false, //1
					false, //2
					false, //3
					false, //4
					false, //5
			};
		}
		
	}
}


