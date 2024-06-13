package tablemodel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelROPSchedule extends DefaultTableModel {

	private static final long serialVersionUID = -8738810102880371204L;

	public modelROPSchedule() {
		initThis();
	}

	public modelROPSchedule(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelROPSchedule(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelROPSchedule(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelROPSchedule(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelROPSchedule(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			
			"Part ID", //0
			"Part Description", //1
			"Sched. Date", //2
			"Amount", // 3
			"Proc. Fee", //4
			"MRI", //5
			"Fire", //6
			"Interest", //7
			"Principal", //8
			"VAT", //9
			"Balance", //10
			"Int. Rate" //11
	};
	
	public Class[] CLASS_TYPES = new Class[] {
			
			String.class, //Part ID
			String.class, //Part Desc
			Timestamp.class, //Sched Date
			BigDecimal.class, //Amount
			BigDecimal.class, //Proc. Fee
			BigDecimal.class, //MRI
			BigDecimal.class, //Fire
			BigDecimal.class, //Interest
			BigDecimal.class, //Principal
			BigDecimal.class, //VAT
			BigDecimal.class, //Balance
			BigDecimal.class //Int. rate
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
