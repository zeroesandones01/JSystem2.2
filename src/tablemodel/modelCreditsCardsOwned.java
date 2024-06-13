package tablemodel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelCreditsCardsOwned extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8738810102880371204L;

	public modelCreditsCardsOwned() {
		initThis();
	}

	public modelCreditsCardsOwned(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelCreditsCardsOwned(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelCreditsCardsOwned(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelCreditsCardsOwned(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelCreditsCardsOwned(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			"Rec. ID",
			"Card Company", // 0
			"Bank ID", //1
			"Bank", // 2
			"Credit Limit", // 3
			"Card No.", // 4
			"Expiry Date", // 5
			"Average Balance", // 6
			"Status" //7
			
	};
	
	public Class[] CLASS_TYPES = new Class[] {
			Integer.class, //Rec. ID
			String.class, //Card Company
			String.class, //Bank ID
			String.class, //Bank
			BigDecimal.class, //Credit Limit
			String.class, //Card. No
			Timestamp.class, //Expiry Date
			BigDecimal.class, //Average Balance
			String.class //Status
			
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
	public void clear(){
		FncTables.clearTable(this);
	}

}
