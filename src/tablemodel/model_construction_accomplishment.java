package tablemodel;

import java.math.BigDecimal;
import java.security.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class model_construction_accomplishment extends DefaultTableModel {

	public model_construction_accomplishment() {
		initThis();
	}

	public model_construction_accomplishment(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		
	}

	public model_construction_accomplishment(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		
	}

	public model_construction_accomplishment(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		
	}

	public model_construction_accomplishment(Vector data, Vector columnNames) {
		super(data, columnNames);
		
	}

	public model_construction_accomplishment(Object[][] data,
			Object[] columnNames) {
		super(data, columnNames);
	
	}
	public String[] COLUMNS = new String[] {
			"NTP No.", // 0
			"No.", // 1
			"Work Item", // 2
			"Unit Description", // 3
			"% Accomplishment", // 4
			"# Manpower", // 5
			"Form Status", // 6
			"Remarks" // 7
	};

	
	/**
	 * String.class = Alignment.CENTER
	 * Object.class = Alignment.LEFT
	 * BigDecimal.class = Alignment.RIGHT
	 * Integer.class = Alignment.CENTER
	 * 
	 */
	public Class[] CLASS_TYPES = new Class[] {
			String.class, //NTP No.
			Object.class, //No.
			BigDecimal.class, //Work Item
			BigDecimal.class, //Unit Descrition
			Timestamp.class, //% Accomplishment
			Object.class, //# Manpower
			Object.class, //Form Status
			Object.class //Remarks
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


}
