package tablemodel;

import java.math.BigDecimal;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelNOATagging extends DefaultTableModel {

	private static final long serialVersionUID = 1L;

	public modelNOATagging() {
		initThis();
	}

	public modelNOATagging(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelNOATagging(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelNOATagging(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelNOATagging(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelNOATagging(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	private void initThis() {
		setColumnIdentifiers(new String[] {
				"Select", // 0
				"Client ID", // 1
				"Client Name", // 2
				"Ph-Bl-Lt", // 3
				"PBL ID", // 4
				"Seq.", // 5
				"BuyerType", // 6
				"Proj. ID", // 7
				"Project", // 8
				"Loan Amt", //9
				"Loan Term(in years)", //10
				
		});
	}

	Class[] COLUMN_TYPES = new Class[] {
			Boolean.class, //Select
			String.class, //Client ID
			Object.class, //Client Name
			String.class, //Ph-Bl-Lt
			String.class, //PBL ID
			Integer.class, //Seq.
			Object.class, //BuyerType
			String.class, //Proj. ID
			Object.class, //Project
			BigDecimal.class, //Loan Amt
			Integer.class, //Loan Term(in years)
	};

	Boolean[] COLUMN_EDITABLE = new Boolean[] {
			true, //Select
			false, //Client ID
			false, //Client Name
			false, //Ph-Bl-Lt
			false, //PBL ID
			false, //Seq.
			false, //Sales Group
			false, //BuyerType
			false, //Proj. ID
			false, //Project
			false, //Loan Amt
			false //Loan Term (in years)
	};

	public Class getColumnClass(int columnIndex) {
		return COLUMN_TYPES[columnIndex];
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return COLUMN_EDITABLE[columnIndex];
	}
	
	public void clear() {
		FncTables.clearTable(this);
	}

}
