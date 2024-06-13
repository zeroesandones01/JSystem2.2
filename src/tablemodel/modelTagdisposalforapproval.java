/*package tablemodel;

public class modelTagdisposalforapproval {

}*/
//
package tablemodel;

import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelTagdisposalforapproval extends DefaultTableModel  {

	private static final long serialVersionUID = 1L;
	
	private boolean editable = false;
	//boolean[] COLUMN_EDITABLE;

	public modelTagdisposalforapproval() {
		initThis();
	}

	public modelTagdisposalforapproval(boolean editable) {
		initThis();
	}

	public modelTagdisposalforapproval(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelTagdisposalforapproval(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelTagdisposalforapproval(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelTagdisposalforapproval(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelTagdisposalforapproval(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}
	
	String[] COLUMNS = new String[] {
			"Selected",
			"Asset No.", 
			"Asset Code",
			"Asset Name",
			"Date Acquired",
			"Date Disposed",
			"Custodian",
			//"Custodian",
			//"Reference No.",
			//"status",
			//"Company Name",
			//"Company Logo",
			//"Company ID",
	};
	
	Class[] CLASS_TYPES = new Class[] {
			Boolean.class,//checkbox
			String.class, //Asset No.
			String.class, //Asset Code
			Object.class, //Asset Name
			Timestamp.class, //Date Acquired
			Timestamp.class,//Date Disposed
			String.class, //Custodian 
			//Object.class, //Custodian
			//Object.class, //Reference No.
			//String.class, //status
			//Object.class, //Co_name
			//Object.class, //Co_logo
			//String.class, //co_id
	};
	
	private void initThis() {
		setColumnIdentifiers(COLUMNS);
	}
	
	Boolean[] COLUMNS_EDITABLE = new Boolean[] {
			 true, //0
			false, //1
			false, //2
			false, //3
			false, //4
			false, //5
			//false, //6
			//false, //7
			//false, //8
			//false, //9
			//false, //10
			//false, //11
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
					//true, //6
				//	true, //7
					//true, //8
					//true, //9
					//true, //10
					//true, //11
			};
		}else{
			COLUMNS_EDITABLE = new Boolean[]{
					false, //0
					false, //1
					false, //2
					false, //3
					false, //4
					false, //5
				//	false, //6
				//	false, //7
				//	false, //8
				//	false, //9
				//	false, //10
					//false, //11
			};
	}
		
	}
}

