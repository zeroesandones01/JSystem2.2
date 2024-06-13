package tablemodel;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;
import components._JTextAreaRenderer;
import components._JTextAreaRenderer2;

public class modelClientFollowup extends DefaultTableModel {
	
	private static final long serialVersionUID = -8738810102880371204L;
	
	private boolean editable = false;

	public modelClientFollowup() {
		initThis();
	}

	public modelClientFollowup(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelClientFollowup(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelClientFollowup(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelClientFollowup(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelClientFollowup(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			"Rec. ID", //0
			"Purpose of Call", // 1
			"Conversation Details", //8
			"Date Created", // //4
			"Contact Person", //2
			"Contact No.", //3
			"Follow-On Call Date", //5 
			"E-Mail", //6
			"Call Start", //7
			"Call End", //8
			"Emp. ID", //9
			"Caller Name" //10
	};
	
	public Class[] CLASS_TYPES = new Class[] {
			
			Integer.class, //Rec. ID
			Object.class, //Purpose of Call
			Object.class, //Conversation Details
			Timestamp.class, //Date Created
			Object.class, //Contact Person
			Object.class, //Contact No.
			Timestamp.class, //Follow on Call Date
			Object.class, //Email
			Time.class, //Call Start
			Time.class, //Call End
			Object.class, //Emp. ID
			Object.class //Caller Name
			
	};
	
	Boolean[] COLUMN_EDITABLE = new Boolean[]{
			
			false, // Rec. ID
			false, // Purposer of Call
			true, // Conversation Details
			false, // Contact Person
			false, // Contact No.
			false, // Date Created
			false, // Follow on Call Date
			false, // Email
			false, // Call Start
			false, 
			false, 
			false 
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
	}
	
	public Class getColumnClass(int columnIndex) {
		return CLASS_TYPES[columnIndex];
	}

	/*public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}*/
	
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return COLUMN_EDITABLE[columnIndex];
	}
	
	public void clear() {
		FncTables.clearTable(this);
	}

}
