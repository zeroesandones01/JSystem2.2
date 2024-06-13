package tablemodel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelDPSchedNew extends DefaultTableModel {

	private static final long serialVersionUID = -8738810102880371204L;

	public modelDPSchedNew() {
		initThis();
	}

	public modelDPSchedNew(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelDPSchedNew(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelDPSchedNew(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelDPSchedNew(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelDPSchedNew(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			"Sched. Date", //0
			"Amount", //1
			"Proc. Fee.", // 2
			"Principal", //3
			"VAT", //4
			"Balance", //5
			"Paid", //6
			"Amt. Paid" //7
	};
	
	public Class[] CLASS_TYPES = new Class[] {
			Timestamp.class, //Sched Date
			BigDecimal.class, //Amount
			BigDecimal.class, //Proc Fee
			BigDecimal.class, //Principal
			BigDecimal.class, //VAT
			BigDecimal.class, //Balance
			String.class, //Paid
			BigDecimal.class //Amt Paid
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
