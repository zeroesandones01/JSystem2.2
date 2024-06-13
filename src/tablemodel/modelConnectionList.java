package tablemodel;

import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelConnectionList extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8738810102880371204L;

	public modelConnectionList() {
		initThis();
	}

	public modelConnectionList(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelConnectionList(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelConnectionList(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelConnectionList(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelConnectionList(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			
			//"Connection ID", //0
			"Rec. ID", //0
			"Connection Name", // 1
			"First Name", //2
			"Middle Name", //3
			"Last Name", //4
			"Date of Birth", //5
			"Gender", //6
			"Mobile No", //7
			"Email", //8
			"Address", //9
			"Type ID", //10
			"Connection Type", // 11
			"Relation ID", //12
			"Relation", // 13
			"Connection Employer", //14
			"Connection Employer Address", //15
			"Status", // 16
			"Citizenship Code", //17
			"Citizenship", //18
			"MID No", //19
			"TIN No", //20
			"Yrs In Srvc", //21
			"Business No.", //22
			"Position", //23
			"Department", //24
			"ID Code", //25
			"ID Desc", //26
			"ID No.", //27
			"ID Expiration"//28
			
	};
	
	public Class[] CLASS_TYPES = new Class[] {
			
			//String.class, //Connection ID
			Integer.class, //rec. ID
			String.class, //Connection Name
			String.class, //First Name
			String.class, //Middle Name
			String.class, //Last Name
			Timestamp.class, //Date of Birth
			String.class, //Gender
			String.class, //Mobile No
			String.class, //Email
			String.class, //Address
			String.class, //Type ID
			String.class, //Conn Type
			String.class, //Relation ID
			String.class, //Relation
			String.class, //Unit ID
			String.class, //Unit
			String.class, //Status
			String.class, 
			String.class, 
			String.class, 
			String.class, 
			String.class, 
			String.class, 
			String.class, 
			String.class, 
			String.class, 
			String.class, 
			String.class, 
			Timestamp.class
			
			
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

}
