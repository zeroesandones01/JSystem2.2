package tablemodel;

import java.math.BigDecimal;
import java.security.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class modelSample1 extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8738810102880371204L;

	public modelSample1() {
		initThis();
	}

	public modelSample1(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelSample1(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelSample1(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelSample1(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelSample1(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			"ID", // 0
			"Description", // 1
			"Debit", // 2
			"Credit", // 3
			"Date", // 4
			"Remarks" // 5
	};

	
	/**
	 * String.class = Alignment.CENTER
	 * Object.class = Alignment.LEFT
	 * BigDecimal.class = Alignment.RIGHT
	 * Integer.class = Alignment.CENTER
	 * 
	 */
	public Class[] CLASS_TYPES = new Class[] {
			String.class, //ID
			Object.class, //Description
			BigDecimal.class, //Debit
			BigDecimal.class, //Credit
			Timestamp.class, //Date
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
