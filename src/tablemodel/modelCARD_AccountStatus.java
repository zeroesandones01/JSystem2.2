package tablemodel;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.swing.table.DefaultTableModel;

public class modelCARD_AccountStatus extends DefaultTableModel {

	private static final long serialVersionUID = 1L;

	static String[] columns = new String[] {
		"ID", 					//0
		"Status", 				//1
		"Trans. Date", 			//2
		"Actual Date", 			//3
		"Stat",					//4
		"Gross SP", 			//5
		"Discount", 			//6
		"VAT", 					//7
		"Net SP", 				//8
		"CWT Remitted", 		//9
		"Request No." 			//10
		 					
	};

	public modelCARD_AccountStatus() {
		super(new Object[][]{}, columns);
	}

	public Class[] types = new Class[] {
			String.class, //ID
			Object.class, //Status
			Timestamp.class, //Trans. Date
			Timestamp.class, //Actual Date
			String.class,    //Stat
			BigDecimal.class, //Gross SP
			BigDecimal.class, //Discount
			BigDecimal.class, //VAT
			BigDecimal.class, //Net SP
			BigDecimal.class, //CWT
			Object.class, //Request No.
	};

	@Override
	public Class getColumnClass(int columnIndex) {
		return types[columnIndex];
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

}
