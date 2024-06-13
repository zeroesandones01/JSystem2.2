package tablemodel;

import java.math.BigDecimal;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class modelListof_ReleasedComm_perEntity extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8738810102880371204L;
	
	private boolean editable = false;
	boolean[] COLUMN_EDITABLE;
	
	
	public modelListof_ReleasedComm_perEntity() {
		initThis();
	}

	public modelListof_ReleasedComm_perEntity(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelListof_ReleasedComm_perEntity(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelListof_ReleasedComm_perEntity(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelListof_ReleasedComm_perEntity(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelListof_ReleasedComm_perEntity(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			"PV No." ,			// 1
			"PV Date" ,			// 2
			"BIR Tax Code" ,	// 3
			"WTax Rate" ,		// 4
			"Gross Amt.", 		// 5
			"WTax Amt.", 		// 6
			"CA Liq. Amt.", 	// 7
			"Net Amt." 			// 8
			};

	
	/**
	 * String.class = Alignment.CENTER
	 * Object.class = Alignment.LEFT
	 * BigDecimal.class = Alignment.RIGHT
	 * Integer.class = Alignment.CENTER
	 * Timestamp.class
	 */
	public Class[] CLASS_TYPES = new Class[] {
			String.class, 		//PV No.
			String.class,		//PV Date
			String.class, 		//BIR Tax Code
			String.class, 		//WTax Rate
			BigDecimal.class, 	//Gross Amt.
			BigDecimal.class,	//WTax Amt.
			BigDecimal.class,	//CA Liq. Amt.
			BigDecimal.class	//Net Amt.
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
		COLUMN_EDITABLE = new boolean[] {
				false, 	//PV No.
				false,	//PV Date
				false, 	//BIR Tax Code
				false, 	//WTax Rate
				false, 	//Gross Amt.
				false,	//WTax Amt.
				false,	//CA Liq. Amt.
				false	//Net Amt.
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
