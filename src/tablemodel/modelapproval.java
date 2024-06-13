package tablemodel;

import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;


import Functions.FncTables;

public class modelapproval extends DefaultTableModel {

	private static final long serialVersionUID = 1L;
	
	private boolean editable = false;
	//boolean[] COLUMN_EDITABLE;

	public modelapproval() {
		initThis();
	}

	public modelapproval(boolean editable) {
		initThis();
	}

	public modelapproval(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelapproval(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelapproval(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelapproval(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelapproval(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}
	
	String[] COLUMNS = new String[] {
			"Select",
			"Asset no.",
			"Movement No.", 
			"Asset Code",
			"From",
			"To",
			"Date Transfered",
			"Reason",
			"Remarks",
			"approvedby_dept_head",
			"date_approvedby_dept_head",
			"approvedby_had",
			"date_approvedby_had"
	};
	
	Class[] CLASS_TYPES = new Class[] {
			Boolean.class,	//select
			Integer.class, //asset no.
			Integer.class, //Movement No.
			Object.class, // Asset Code
			Object.class, //From
			Object.class, //To
			Timestamp.class, //Date Transfered 
			Object.class, //Reason
			Object.class, //Remarks
			Integer.class,//dept_head
			Timestamp.class,//date_approvedby_dept_head
			Integer.class,//approvedby_had
			Timestamp.class//date_approvedby_HAD
		
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
			false, //9
			false, //10
			false, //11
			false, //12
			
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
					true, //9
					true, //10
					true, //11
					true, //12
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
					false, //9
					false, //10
					false, //11
					false, //12
			};
	}
	}

}
