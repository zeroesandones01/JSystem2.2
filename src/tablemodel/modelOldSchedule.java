package tablemodel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelOldSchedule extends DefaultTableModel {

	private static final long serialVersionUID = -8738810102880371204L;

	public modelOldSchedule() {
		initThis();
	}

	public modelOldSchedule(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelOldSchedule(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelOldSchedule(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelOldSchedule(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelOldSchedule(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			
			"Part ID", //0
			"Part Description", //1
			"Sched. Date", //2
			"Amount", // 3
			"Proc. Fee", //4
			"RPT",
			"UPICO", //5
			"MRI", //6
			"Fire", //7
			"Interest", //8
			"Principal", //9
			"VAT", //10
			"Balance", //11
			"Int. Rate" //12
	};
	
	public Class[] CLASS_TYPES = new Class[] {
			
			String.class, //Part ID
			String.class, //Part Desc
			Timestamp.class, //Sched Date
			BigDecimal.class, //Amount
			BigDecimal.class, //Proc. Fee
			BigDecimal.class, //RPT
			BigDecimal.class, //UPICO
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
