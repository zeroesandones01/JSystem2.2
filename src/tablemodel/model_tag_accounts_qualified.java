package tablemodel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;
import Lookup._JLookup;

public class model_tag_accounts_qualified extends DefaultTableModel {

	private static final long serialVersionUID = 1L;
	
	public model_tag_accounts_qualified() {
		initThis();
	}

	public model_tag_accounts_qualified(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public model_tag_accounts_qualified(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public model_tag_accounts_qualified(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public model_tag_accounts_qualified(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public model_tag_accounts_qualified(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}
	private void initThis() {
		setColumnIdentifiers(new String[] {
			"Tag", // 0
			"Batch #", // 1
			"Buyer's Name", // 2
			"OR Date", // 3
			"Project", // 4
			"Phase", // 5
			"Block", // 6
			"Lot", // 7
			"Model", // 8
			"Color Scheme", // 9
			"Stage", // 10
			"Status", // 11
			"Buyer Type", // 12
			"Lot Area", // 13
			"Floor Area", // 14
			"Unit ID", // 15
			"Entity ID", // 16
			"Pmt %", // 17
			"Selling Price", // 18
			"DP Term", // 19
			"PBL ID", // 20
			"LOG Date", // 21
			"Cluster"
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
			Boolean.class, //Tag
			String.class, //Batch #
			Object.class, //Buyer's Name
			Timestamp.class, //OR Date
			String.class, //Project
			String.class, //Phase
			String.class, //Block
			String.class, //Lot
			String.class, //Model
			_JLookup.class, //Color Scheme
			String.class, //Stage
			String.class, //Status Hold
			String.class, //Buyer Type
			BigDecimal.class, //Lot Area
			String.class, //Floor Area
			String.class, //Unit ID
			String.class, //Entity id
			String.class, //Pmt %
			BigDecimal.class, //Pmt %
			String.class, //Model ID
			String.class, //Model ID
			Timestamp.class,  //OR Date
			Integer.class
	};
	Boolean[] COLUMN_EDITABLE = new Boolean[] {
			true, //Tag
			false, //Batch #
			false, //Buyer's Name
			false, //OR Date
			false, //Project
			false, //Phase
			false, //Block
			false, //Lot
			false, //Model
			false, //Color Scheme
			false, //Stage
			false, //Status Hold
			false, //Buyer Type
			false, //Lot Area
			false, //Floor Area
			false, //Unit ID
			false, //Entity id
			false, //Pmt %
			false, //Pmt %
			false, //Pmt %
			false, //Model ID
			false, //Model ID
			false
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
}