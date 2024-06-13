package tablemodel;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelCARD_Schedule extends DefaultTableModel {

	private static final long serialVersionUID = 1L;

	static String[] columns = new String[] { 
		"Part ID", //0
		"Particular", //1
		"Sched. Date", //2
		"Amount", //3
		"Proc. Fee", //4
		"RPT", //
		"UPICO", //5
		"MRI", //6
		"Fire", //7
		"VAT", //8
		"Interest", //9
		"Principal", //10
		"Balance", //11
		"% Paid", //12
		"Settled" //13
	};

	public modelCARD_Schedule() {
		super(new Object[][]{}, columns);
	}

	public Class[] types = new Class[] {
			String.class, //Part ID
			String.class, //Particular
			Timestamp.class, //Schedule Date //XXX CHECK DATE FORMAT HERE
			//Object.class, //Schedule Date
			BigDecimal.class, //Amount
			BigDecimal.class, //Processing Fee
			BigDecimal.class, //RPT
			BigDecimal.class, //UPICO
			BigDecimal.class, //MRI
			BigDecimal.class, //Fire
			BigDecimal.class, //VAT
			BigDecimal.class, //Interest
			BigDecimal.class, //Principal
			BigDecimal.class, //Balance
			BigDecimal.class, //% Paid
			Boolean.class //Settled
	};

	@Override
	public Class getColumnClass(int columnIndex) {
		return types[columnIndex];
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}
	
	public void clear() {
		FncTables.clearTable(this);
	}

}
