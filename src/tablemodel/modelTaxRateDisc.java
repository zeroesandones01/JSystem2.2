package tablemodel;

import java.math.BigDecimal;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class modelTaxRateDisc extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8738810102880371204L;
	
	private boolean editable = false;
	boolean[] COLUMN_EDITABLE;
	
	
	public modelTaxRateDisc() {
		initThis();
	}

	public modelTaxRateDisc(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelTaxRateDisc(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelTaxRateDisc(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelTaxRateDisc(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelTaxRateDisc(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			"Entity ID" ,		// 1
			"Entity Name" ,		// 2
			"Gross Amount" ,	// 3
			"BIR Code" ,		// 4
			"<html><center>WTax Rate<html><br><html><center>Actual<html>", 		// 5
			"<html><center>WTax Rate<html><br><html><center>Should-be<html>" 		// 6
			};

	
	/**
	 * String.class = Alignment.CENTER
	 * Object.class = Alignment.LEFT
	 * BigDecimal.class = Alignment.RIGHT
	 * Integer.class = Alignment.CENTER
	 * Timestamp.class
	 */
	public Class[] CLASS_TYPES = new Class[] {
			String.class, 		//Entity ID
			Object.class,		//Entity Name
			BigDecimal.class, 	//Gross Amount
			String.class, 		//BIR Code
			String.class,		//Wtax Rate (Actual)
			String.class		//Wtax Rate (Should-be)
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
		COLUMN_EDITABLE = new boolean[] {
				false, 		//Entity ID
				false,		//Entity Name
				false, 		//Gross Amount
				false, 		//BIR Code
				false,		//Wtax Rate (Actual)
				false		//Wtax Rate (Should-be)
		};
	}

	public Class getColumnClass(int columnIndex) {
		return CLASS_TYPES[columnIndex];
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return COLUMN_EDITABLE[columnIndex];
	}

	public boolean isEditable() {
		return editable;
	}

	

	
	
	
}
