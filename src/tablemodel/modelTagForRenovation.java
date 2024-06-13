package tablemodel;

import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelTagForRenovation extends DefaultTableModel {
	
	private static final long serialVersionUID = 1L;
	
	public modelTagForRenovation() {
		initThis();
	}

	public modelTagForRenovation(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelTagForRenovation(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelTagForRenovation(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelTagForRenovation(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelTagForRenovation(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}
	private void initThis() {
		setColumnIdentifiers(new String[] {
			"Tag", // 0
			"Trans Date", // 1
			"Buyer's Name", // 2
			"Phase-Block-Lot", // 3
			"Model", //4
			"Entity ID",
			"Proj. ID",
			"PBL ID", // 5
			"Seq No" //7
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
			Timestamp.class, //Trans Date
			Object.class, //Buyer's Name
			String.class, //PBL
			String.class, //Model
			String.class, //Entity ID
			String.class, //Proj. ID
			String.class, //PBL ID
			Integer.class //PBL ID
	};
	
	Boolean[] COLUMN_EDITABLE = new Boolean[] {
			true, //Tag
			false, //transname
			false, //Buyer Name
			false, //PBL
			false, //Model
			false, //Entity ID
			false, //Proj. ID
			false, //PBL ID
			false //Seq, No
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
