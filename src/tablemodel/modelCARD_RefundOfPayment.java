package tablemodel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelCARD_RefundOfPayment extends DefaultTableModel {

	private static final long serialVersionUID = 8733709856748328386L;

	public modelCARD_RefundOfPayment() {
		initThis();
	}

	public modelCARD_RefundOfPayment(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelCARD_RefundOfPayment(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelCARD_RefundOfPayment(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelCARD_RefundOfPayment(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelCARD_RefundOfPayment(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			
			"RPLF No.", //0,
			"Status", //1
			"Date Paid", //2
			"Amount", //3
			"Released By", //4
			"Released Date", //5
			"Received by", //6
			"Received Date", //7
			"Remarks" //10
			
	};
	
	public Class[] CLASS_TYPES = new Class[] {
			
			String.class, //RPLF No
			String.class, //Status
			Timestamp.class, //Date Paid
			BigDecimal.class, //Amount
			String.class, //Released By
			Timestamp.class, //Released Date
			String.class, //Received By
			Timestamp.class, //Received Date
			String.class //Remarks
			
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
