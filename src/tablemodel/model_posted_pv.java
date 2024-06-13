package tablemodel;

import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class model_posted_pv extends DefaultTableModel {
	
	public model_posted_pv() {
		initThis();
	}

	public model_posted_pv(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public model_posted_pv(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public model_posted_pv(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public model_posted_pv(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public model_posted_pv(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}
	private void initThis() {
		setColumnIdentifiers(new String[] {
			"Tag", // 0	
			"PV No.", // 1
			"PV Date", // 2
			"Payment Type", // 3
			"Payee" // 4
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
			Timestamp.class, //Model
			String.class, //Buyer's Name
			Object.class //Addr_no
	};
	Boolean[] COLUMN_EDITABLE = new Boolean[] {
			true,
			false, //Unit
			false, //Model
			false, //Buyer's Name
			false //City
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
