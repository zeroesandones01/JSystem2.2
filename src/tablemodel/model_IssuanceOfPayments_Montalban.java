package tablemodel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class model_IssuanceOfPayments_Montalban extends DefaultTableModel {

	public model_IssuanceOfPayments_Montalban() {
		initThis();
	}

	public model_IssuanceOfPayments_Montalban(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public model_IssuanceOfPayments_Montalban(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public model_IssuanceOfPayments_Montalban(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public model_IssuanceOfPayments_Montalban(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public model_IssuanceOfPayments_Montalban(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			
			"Part ID", //0
			"Particulars", //1
			"Amout", //2
			"Lot", //3
			"Type", 		// 3
			"Check No.", 	// 4
			"Check Type ID",// 5
			"Check Type", 	// 6
			"Check Date", 	// 7
			"Account No.", 	// 8
			"Bank ID", 		// 9
			"Bank Name", 	// 10
			"Branch ID", 	// 11
			"Branch Name", 	// 12
			"Receipt No.", 	// 13
			"Receipt ID", 	// 14
			"Receipt Type", // 15
			"Trans Date", 	// 16
			"BRSTN", 		// 17
			"Credit", 		// 18
			"pay_rec_id", 	// 19
			"due type"	// 20
			
			
	};
	
	public Class[] CLASS_TYPES = new Class[] {
			
			String.class, //Part ID
			String.class, //
			BigDecimal.class, 
			String.class, 
			String.class,		//Payment Type
			Object.class,		//Check No.
			String.class,		//Check Type ID
			Object.class,		//Check Type
			Timestamp.class,	//Check Date
			Object.class,		//Account No.
			String.class,		//Bank ID
			Object.class,		//Bank Name
			String.class,		//Branch ID
			Object.class,		//Branch Name
			String.class,		//Receipt No.
			String.class,		//Receipt ID
			String.class,		//Receipt Name
			Timestamp.class,	//Trans Date
			String.class,		//BRSTN
			Boolean.class, 
			Integer.class, 
			String.class
			
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
