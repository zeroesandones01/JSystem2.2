package tablemodel;

import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;


import Functions.FncTables;

public class modelMovement extends DefaultTableModel {

	private static final long serialVersionUID = 1L;
	
	private boolean editable = false;
	//boolean[] COLUMN_EDITABLE;

	public modelMovement() {
		initThis();
	}

	public modelMovement(boolean editable) {
		initThis();
	}

	public modelMovement(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelMovement(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelMovement(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelMovement(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelMovement(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}
	
	String[] COLUMNS = new String[] {
			"Movement No.", 
			"Asset Code",
			"From",
			"To",
			"Date Transfered",
			"Reason",
			"Remarks",
			"Old Location" // added by jari cruz as of july 8 2022
			//"Approved by dept. head",
			//"Date approved by dept. head",
			//"Approved by had",
			//"Date approved by HAD"
	};
	
	Class[] CLASS_TYPES = new Class[] {
			Integer.class, //Movement No.
			Object.class, //Old Asset Code
			Object.class, //From
			Object.class, //To
			Timestamp.class, //Date Transfered 
			Object.class, //Reason
			Object.class, //Remarks
			Object.class //Old Location added by jari cruz as of july 8 2022
			//Integer.class,//dept_head
			//Timestamp.class,//date_approvedby_dept_head
			//Integer.class,//approvedby_had
			//Timestamp.class//date_approvedby_HAD
		
	};
	
	private void initThis() {
		setColumnIdentifiers(COLUMNS);
	}
	
	public Class getColumnClass(int columnIndex) {
		return CLASS_TYPES[columnIndex];
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}
	
	public void clear() {
		FncTables.clearTable(this);
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

}
