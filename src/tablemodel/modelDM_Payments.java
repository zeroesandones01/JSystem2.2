package tablemodel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelDM_Payments extends DefaultTableModel {

	private static final long serialVersionUID = 1L;

	public modelDM_Payments() {
		initThis();
	}

	public modelDM_Payments(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelDM_Payments(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelDM_Payments(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelDM_Payments(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelDM_Payments(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	private void initThis() {
		setColumnIdentifiers(new String[] {
				"Trans. Date", // 0
				"Particulars", // 1
				"Amount", // 2
				"Type", // 3
				"Check #", // 4
				"Check Date", // 5
				"Check Status", // 6
				"Bank", // 7
				"Branch", // 8
				"Account #", // 9
				"OR No.", // 10
				"AR No.", // 11
				"Rec ID" // 12
		});
	}

	Class[] types = new Class[] {
			Timestamp.class, //Trans. Date
			String.class, //Particulars
			BigDecimal.class, //Amount
			String.class, //Payment Type
			String.class, //Check #
			Timestamp.class, //Check Date
			String.class, //Check Status
			Object.class, //Bank
			Object.class, //Branch
			Object.class, //Account #
			String.class, //OR No.
			String.class, //AR No.
			Integer.class //Rec ID
	};

	public Class getColumnClass(int columnIndex) {
		return types[columnIndex];
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}
	
	public void clear() {
		FncTables.clearTable(this);
	}

}
