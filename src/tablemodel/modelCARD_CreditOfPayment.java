package tablemodel;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelCARD_CreditOfPayment extends DefaultTableModel {

	private static final long serialVersionUID = 1L;

	static String[] columns = new String[] {
		"Part", //0
		"Check No.", //1
		"Check Date", //2
		"Amount", //3
		"OR #", //4
		"Acct. #", //5
		"Bank", //6
		"Branch", //7
		"PR #", //8
		"Reason" //9
	};

	public modelCARD_CreditOfPayment() {
		super(new Object[][]{}, columns);
	}

	public Class[] types = new Class[] {
			Object.class, //Part
			Object.class, //Check No.
			Timestamp.class, //Check Date
			BigDecimal.class, //Amount
			Object.class, //OR #
			Object.class, //Acct. #
			Object.class, //Bank
			Object.class, //Branch
			Object.class, //PR #
			Object.class //Reason
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
