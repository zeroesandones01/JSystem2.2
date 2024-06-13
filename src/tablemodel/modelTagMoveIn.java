package tablemodel;

import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelTagMoveIn extends DefaultTableModel {
	
	private static final long serialVersionUID = 1L;
	
	public modelTagMoveIn() {
		initThis();
	}

	public modelTagMoveIn(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelTagMoveIn(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelTagMoveIn(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelTagMoveIn(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelTagMoveIn(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}
	private void initThis() {
		setColumnIdentifiers(new String[] {
			"Tag", // 0
			"Trans Date", // 1
			"Buyer's Name", // 2
			"Phase-Block-Lot", // 3
			"Model", //4
			"Entity ID", //5
			"Proj. ID", //6
			"PBL ID", //7
			"Seq No" //8
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
			Object.class, //Buyer Name
			String.class, //PBL
			String.class, //Model
			String.class, //Entity ID
			String.class, //Proj. ID
			String.class, //PBL ID
			Integer.class //Unit ID
	};
	
	Boolean[] COLUMN_EDITABLE = new Boolean[] {
			true, //Tag
			false, //Phase-Block-Lot
			false, //Buyer Name
			false, //PBL
			false, // Model ID
			false, //Entity ID
			false, //Proj. ID
			false, //Unit ID
			false //Seq No
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
