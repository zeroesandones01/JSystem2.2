package tablemodel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelMASchedNew extends DefaultTableModel {

	private static final long serialVersionUID = -8738810102880371204L;

	public modelMASchedNew() {
		initThis();
	}

	public modelMASchedNew(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelMASchedNew(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelMASchedNew(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelMASchedNew(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelMASchedNew(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			"Sched. Date", //0
			"Amount", //1
			"Proc. Fee.", // 2
			"MRI", //3
			"Fire", //4
			"Interest", //5
			"Principal", //6
			"VAT", //7
			"Balance", //8
			"Paid", //9
			"Amt. Paid" //10
	};
	
	public Class[] CLASS_TYPES = new Class[] {
			Timestamp.class, //Sched Date
			BigDecimal.class, //Amount
			BigDecimal.class, //Proc Fee
			BigDecimal.class, //MRI
			BigDecimal.class, //Fire
			BigDecimal.class, //Interest
			BigDecimal.class, //Principal
			BigDecimal.class, //VAT
			BigDecimal.class, //Balance
			String.class, //Paid
			BigDecimal.class //Amt. Paid
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
	
	public void clear() {
		FncTables.clearTable(this);
	}

}
