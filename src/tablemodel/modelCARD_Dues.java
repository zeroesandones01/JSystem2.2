package tablemodel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelCARD_Dues extends DefaultTableModel {

	private static final long serialVersionUID = 1L;
	
	private boolean editable = false;
	//boolean[] COLUMN_EDITABLE;

	public modelCARD_Dues() {
		initThis();
	}

	public modelCARD_Dues(boolean editable) {
		initThis();
	}

	public modelCARD_Dues(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelCARD_Dues(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelCARD_Dues(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelCARD_Dues(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelCARD_Dues(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}
	
	String[] COLUMNS = new String[] {
			"Part. ID", // 0
			"Particular", // 1
			"Sched. Date", // 2
			"Proc. Fee", // 3
			"RPT", //4
			"MRI", // 4
			"FIRE", // 5
			"MAF", // 6
			"VAT", // 7
			"SOI", // 8
			"SOP", // 9
			"Penalty", //10
			"Adjustments", // 11
			"Interest", // 12
			"Accrued Interest", // 13
			"Principal", // 14
			"Total" // 15
	};
	
	Class[] CLASS_TYPES = new Class[] {
			String.class, //Part. ID
			String.class, //Particular
			Timestamp.class, //Sched. Date
			BigDecimal.class, //Proc. Fee
			BigDecimal.class, //RPT
			BigDecimal.class, //MRI
			BigDecimal.class, //FIRE
			BigDecimal.class, //MAF
			BigDecimal.class, //VAT
			BigDecimal.class, //SOI
			BigDecimal.class, //SOP
			BigDecimal.class, //Penalty
			//BigDecimal.class, //CBP
			BigDecimal.class, //Adjustments
			BigDecimal.class, //Interest
			BigDecimal.class, //Accrued Interest
			BigDecimal.class, //Principal
			BigDecimal.class //Amount
	};
	
	/*Boolean[] COLUMNS_EDITABLE = new Boolean[] {
			false, // 0
			false, // 1
			false, // 2
			false, // 3
			false, // 4
			true // 5
	};*/
	
	private void initThis() {
		setColumnIdentifiers(COLUMNS);
	}
	
	public Class getColumnClass(int columnIndex) {
		return CLASS_TYPES[columnIndex];
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}
	
	public void clear() {
		FncTables.clearTable(this);
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

}
