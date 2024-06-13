package tablemodel;

import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class model_TCost2 extends DefaultTableModel {

	private static final long serialVersionUID = 1L;
	
	public model_TCost2() {
		initThis();
	}

	public model_TCost2(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public model_TCost2(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public model_TCost2(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public model_TCost2(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public model_TCost2(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}
	private void initThis() {
		setColumnIdentifiers(new String[] {
			"Tag", // 0
			"TCost ID", // 1
			"TCost Desc", // 2
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
	};
	Boolean[] COLUMN_EDITABLE = new Boolean[] {
			true, //Tag
			false, //Batch #
			false, //Buyer's Name
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
