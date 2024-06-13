package tablemodel;

import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelAddEditUser extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8738810102880371204L;

	public modelAddEditUser() {
		initThis();
	}

	public modelAddEditUser(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelAddEditUser(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelAddEditUser(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelAddEditUser(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelAddEditUser(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			"Emp. ID", //0
			"Emp. Status", //1
			"Rank ID", // 2
			"Rank Desc", //3
			"Entity ID", //4
			"Emp. Name", //5
			"Co. ID", //6
			"Company Name", //7
			"Div. ID", //8
			"Div. Name", //9
			"Dept. ID", //10
			"Dept. Name", //11
			"Date Hired" //12
	};
	
	public Class[] CLASS_TYPES = new Class[] {
			String.class, //Emp ID
			Object.class, //Emp Status
			String.class, //Rank ID
			Object.class, //Rank Desc
			String.class, //Entity ID
			Object.class, //Emp Name
			String.class, //Co ID
			Object.class, //Company Name
			String.class, //Div ID
			Object.class, //Division Name
			String.class, //Dept. ID
			Object.class, //Dept Name
			Timestamp.class //Date Hired
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
