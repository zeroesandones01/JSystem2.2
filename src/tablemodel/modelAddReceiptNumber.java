package tablemodel;

import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelAddReceiptNumber extends DefaultTableModel {

	private static final long serialVersionUID = -8738810102880371204L;

	public modelAddReceiptNumber() {
		initThis();
	}

	public modelAddReceiptNumber(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelAddReceiptNumber(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelAddReceiptNumber(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelAddReceiptNumber(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelAddReceiptNumber(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			"Receipt ID", 	//0
			"Co. ID", 		//1
			"Company Name",	//2
			"Company",		//3
			"Branch ID", 	//4
			"Branch Name",	//5
			"Branch", 		//6
			"Cashier", 		//7
			"Doc. ID", 		//8
			"Doc. Name", 	//9
			"Doc. Alias", 	//10
			"First No.", 	//11
			"Last No.", 	//12
			"Last No. Used", //13
			"Pagibig ID", 	//14
			"Status", 		//15
			"Logged Date" 	//16
	};

	public Class[] CLASS_TYPES = new Class[] {
			Integer.class, //Receipt ID
			String.class, //Co. ID
			Object.class, //Company Name
			String.class, //Company Alias
			String.class, //Branch ID
			Object.class, //Branch Name
			String.class, //Branch Alias
			Object.class, //Cashier
			String.class, //Doc. ID
			Object.class, //Doc. Name
			String.class, //Doc. Alias
			Integer.class, //First No
			Integer.class, //Last No
			Integer.class, //Last No Used
			String.class, //Pag-ibig ID
			String.class, //Status
			Timestamp.class //Logged Date
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
