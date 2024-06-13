package tablemodel;

import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class model_accomplishment extends DefaultTableModel {

	public model_accomplishment() {
		initThis();
	}

	public model_accomplishment(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public model_accomplishment(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public model_accomplishment(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public model_accomplishment(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public model_accomplishment(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}
	private void initThis() {
		setColumnIdentifiers(new String[] {
			"As of", // 1
			"Percentage", // 2
			"Remarks" // 3
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
			Timestamp.class,
			String.class, //Unit
			Object.class //Model
	};
	Boolean[] COLUMN_EDITABLE = new Boolean[] {
			false,
			false, //Unit
			false //Model
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
