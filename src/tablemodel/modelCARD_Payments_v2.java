package tablemodel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelCARD_Payments_v2 extends DefaultTableModel  {



	public modelCARD_Payments_v2() {
		initThis();
	}

	public modelCARD_Payments_v2(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelCARD_Payments_v2(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelCARD_Payments_v2(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelCARD_Payments_v2(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelCARD_Payments_v2(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	/*private void initThis() {
		setColumnIdentifiers(new String[] {
				"Trans. Date", // 0
				"Ledger Date", //1 //Added by John Lester Fatallo 2016-06-23
				"Actual Date", // 2 
				"Particulars", // 3
				"Type", // 4
				"**", // 5
				"Amount", // 6
				"%", // 7
				"Bank", // 8
				"Branch", // 9
				"Account #", // 10
				"Check #", // 11
				"Check Date", // 12
				"Check Status", // 13
				"Deposit Date", // 14
				"OR No.", // 15
				"OR Date", // 16
				"AR No.", // 17
				"Remarks", // 18
				"OR Pending", // 19
				"Branch", // 20
				"Rec ID", // 21
				"Client Seq. No.", //21
				"Refund Date", //22 //Added by John Lester Fatallo 2016-04-01
				"Applied Amt" //23 //Added by John Lester Fatallo 2016-04-01
		});
	}*/
	
	private void initThis() {
		setColumnIdentifiers(new String[] {
				"",
				"Trans. Date", // 0
				"Ledger Date", //1 //Added by John Lester Fatallo 2016-06-23
				"Actual Date", // 2 
				"Particulars", // 3
				"Amount", //4
				"Type", //5
				"Check Type", //6
				"**", //6
				"%", //7
				"Check #", //8
				"Check Date", //9
				"Check Status", //10
				"Deposit Date", //11
				"Bank", //12
				"Bank Branch", //13
				"Account #", //14
				"OR No.", //15
				"OR Date", //16
				"AR No", //17
				"Remarks", // 18
				"OR Pending", // 19
				"Branch", // 20
				"Rec ID", // 21
				"Client Seq. No.", //22
				"Refund Date", //23 //Added by John Lester Fatallo 2016-04-01
				"Applied Amt", //24 //Added by John Lester Fatallo 2016-04-01
				"Request No" //25 //Added by Lester 2016-11-18 for reference in displaying of Credit Payment History in CARD 
				
		});
	}
	
	Class[] types = new Class[] {
			Boolean.class,
			Timestamp.class, //Trans Date
			Timestamp.class, //Ledger Date
			Timestamp.class, //Actual Date
			String.class, //Particulars
			BigDecimal.class, //Amount
			String.class, //Type
			String.class, //Check Type
			String.class, //**
			BigDecimal.class, //%
			String.class, //Check #
			Timestamp.class, //Check Date
			String.class, //Check Status
			Timestamp.class, //Deposit Date
			Object.class, //Bank
			Object.class, //Bank Branch
			Object.class, //Account #
			String.class, //OR No.
			Timestamp.class, //OR Date
			String.class, //AR No
			Object.class, //Remarks
			Boolean.class, //OR Pending
			String.class, //Branch
			Integer.class, //Rec ID
			Object.class, //Client Seq No
			Timestamp.class, //Refund Date
			BigDecimal.class, //Applied Amt
			String.class //Request No
			
	};

	/*Class[] types = new Class[] {
			Timestamp.class, //Trans. Date
			Timestamp.class, //Ledger Date Added by JLF 2016-06-23
			Timestamp.class, //Actual Date
			String.class, //Particulars
			String.class, //Payment Type
			String.class, //**
			BigDecimal.class, //Amount
			BigDecimal.class, // %
			Object.class, //Bank
			Object.class, //Branch
			Object.class, //Account #
			String.class, //Check #
			Timestamp.class, //Check Date
			String.class, //Check Status
			Timestamp.class, //Date Deposited
			String.class, //OR No.
			Timestamp.class, //OR Date
			String.class, //AR No.
			Object.class, //Remarks
			Boolean.class, //OR Pending
			String.class, //Branch
			Integer.class, //Rec ID
			Object.class, //Client Seq. No.
			Timestamp.class, //Refund Date //Added by John Lester Fatallo 04-01-16
			BigDecimal.class //Applied Amt //Added by John Lester Fatallo 04-01-16
	};*/
	Boolean[] COLUMNS_EDITABLE = new Boolean[]{
			true,
			false,
			false,
			false,
			false,
			false,
			false,
			false,
			false,
			false,
			false,
			false,
			false,
			false,
			false,
			false,
			false,
			false,
			false,
			false,
			false,
			false,
			false,
			false,
			false,
			false
		
	};
	public Class getColumnClass(int columnIndex) {
		return types[columnIndex];
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return COLUMNS_EDITABLE[columnIndex];
	}

	public void clear() {
		FncTables.clearTable(this);
	}


}
