package tablemodel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

/**
 * @author JOHN LESTER FATALLO
 */

public class modelROP_OtherPmt extends DefaultTableModel {

	private static final long serialVersionUID = -8738810102880371204L;

	static String[] columns = new String[] { 
		"Paid Date", //0
		"Pay Part ID", // 1
		"Particular", //2
		"Amount", // 3
		"Pay Rec ID",
		"Receipt No"};//15

	public modelROP_OtherPmt() {
		super(new Object[][]{}, columns);
	}
	
	public Class[] types = new Class[] {
			Timestamp.class, //Paid Date
			String.class, //Sched Date
			Object.class, //Particular
			BigDecimal.class, //Amount
			String.class, 
			String.class//Pay ID
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
