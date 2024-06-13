package tablemodel;

import java.math.BigDecimal;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelRealEstateOwned extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8738810102880371204L;

	public modelRealEstateOwned() {
		initThis();
	}

	public modelRealEstateOwned(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelRealEstateOwned(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelRealEstateOwned(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelRealEstateOwned(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelRealEstateOwned(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			"Rec. ID", //0
			"Location", // 0
			"Type of Property", // 1
			"TCT No.", // 2
			"Acquisition", // 3
			"Market Value", // 4
			"Mortgage Balance", // 5
			"Rental Income", //6
			"Status" //7
			
	};
	
	public Class[] CLASS_TYPES = new Class[] {
			
			Integer.class, //rec ID
			String.class, //Location
			String.class, //Type Of Property
			String.class, //TCT No.
			BigDecimal.class, //Acquisition
			BigDecimal.class, //Market Value
			BigDecimal.class, //Mortgage Balance
			BigDecimal.class, //Rental Income
			String.class //Status
			
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
	public void clear(){
		FncTables.clearTable(this);
	}

}
