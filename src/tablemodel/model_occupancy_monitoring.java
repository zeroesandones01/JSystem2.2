package tablemodel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;
import Lookup._JLookup;

public class model_occupancy_monitoring extends DefaultTableModel {

	private static final long serialVersionUID = 1L;
	
	public model_occupancy_monitoring() {
		initThis();
	}

	public model_occupancy_monitoring(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public model_occupancy_monitoring(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public model_occupancy_monitoring(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public model_occupancy_monitoring(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public model_occupancy_monitoring(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}
	private void initThis() {
		setColumnIdentifiers(new String[] {
			"Tag", // 0
			"Client Name",//1
			"Phase",//2
			"Block", // 3
			"Lot", // 4
			"NTC", // 5
			"House Model", //6	/**ADDED BY JED 2019-04-08 DCRF NO. 1012 : DISPLAY HOUSE MODEL NAME**/
			"Building Permit No.", // 7
			"Building Permit Date", // 8
			"Tax Dec House No", //9
			"Tax Dec Lot No", //10
			"CEI No.", // 11
			"CEI Date", // 12
			"Occupancy", // 13
			"Occupancy Date", // 14
			"Entity ID", // 15
			"PBL" // 16
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
			Object.class,
			String.class,
			String.class, //Batch #
			String.class, //Buyer's Name
			Timestamp.class, //OR Date
			String.class, //House Model
			String.class, //Phase
			Timestamp.class, //Block
			Object.class, //Tax Dec House No
			Object.class, //Tax Dec Lot No
			String.class, //Lot
			Timestamp.class, //Model
			String.class, //Color Scheme
			Timestamp.class, //Stage
			String.class,
			String.class
	};
	Boolean[] COLUMN_EDITABLE = new Boolean[] {
			true, //Tag
			false, //Batch #
			false, //Buyer's Name
			false, //Batch #
			false, //Buyer's Name
			false, //OR Date
			false,
			true, //Phase
			false, //Block
			false, 
			false,
			true, //Lot
			false, //Model
			true, //Color Scheme
			false, //Stage
			false, //Stage
			false //Stage
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