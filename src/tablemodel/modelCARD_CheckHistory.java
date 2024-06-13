package tablemodel;

import java.sql.Timestamp;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelCARD_CheckHistory extends DefaultTableModel {

	private static final long serialVersionUID = 1L;

	static String[] columns = new String[] {
		"Date", //0
		"Status", //1
		"Rec", //2
		"Deposit", //3
		"Reason(for Bounce Checks)" //4
	};

	public modelCARD_CheckHistory() {
		super(new Object[][]{}, columns);
	}

	public Class[] types = new Class[] {
			Timestamp.class, //Date
			Object.class, //Status
			String.class, //Rec
			String.class, //Deposit
			Object.class //Reason(for Bounce Checks)
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
