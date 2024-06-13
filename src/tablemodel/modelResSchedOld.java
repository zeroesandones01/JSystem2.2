package tablemodel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelResSchedOld extends DefaultTableModel {

	private static final long serialVersionUID = -8738810102880371204L;

	public modelResSchedOld() {
		initThis();
	}

	public modelResSchedOld(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelResSchedOld(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelResSchedOld(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelResSchedOld(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelResSchedOld(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			"Sched. Date", //0
			"Amount", //1
			"Princpal", // 2
			"Balance", //3
			"Paid" //4
	};
	
	public Class[] CLASS_TYPES = new Class[] {
			Timestamp.class, //Sched Date
			BigDecimal.class, //Amount
			BigDecimal.class, //Principal
			BigDecimal.class, //Balance
			String.class //Paid
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
