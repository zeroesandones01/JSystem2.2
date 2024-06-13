package tablemodel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class model_client extends DefaultTableModel {

	public model_client() {
		initThis();
	}

	public model_client(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public model_client(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public model_client(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public model_client(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public model_client(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}
	private void initThis() {
		setColumnIdentifiers(new String[] {
			"Tag", // 0	
			"Client ID", // 1
			"Client Name", // 2
			"Ph-Blk-Lt", // 3
			"PBL", // 4
			"Seq No", // 5
			"Buyer Type", // 6
			"Project ID", // 7
			"Project Name", // 8
			"Unit ID", // 7
			"Batch No" // 8
	});
	}
	
	/**
	 * String.class = Alignment.CENTER
	 * Object.class = Alignment.LEFT
	 * BigDecimal.class = Alignment.RIGHT
	 * Integer.class = Alignment.CENTER
	 * 
	 */
	Class[] CLASS_TYPES = new Class[] {
			Boolean.class,
			String.class, //Unit
			Object.class, //Model
			String.class, //Buyer's Name
			String.class, //Addr_no
			Integer.class, //Street
			Object.class, //City
			String.class, //Province
			String.class, //Entity ID
			String.class, //Province
			String.class //Entity ID
	};
	Boolean[] COLUMN_EDITABLE = new Boolean[] {
			true,
			false, //Unit
			false, //Model
			false, //Buyer's Name
			false, //City
			false, //Province
			false, //Contact Person
			false, //Mobile No.
			false, //Email Address
			false, //Mobile No.
			false //Email Address
	};


	public Class getColumnClass(int columnIndex) {
		return CLASS_TYPES[columnIndex];
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return COLUMN_EDITABLE[columnIndex];
	}

	public void clear() {
		FncTables.clearTable(this);
		
	}

	public int getSelectedRow() {
		// TODO Auto-generated method stub
		return 0;
	}

}
