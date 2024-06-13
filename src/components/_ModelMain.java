package components;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class _ModelMain extends DefaultTableModel {

	private static final long serialVersionUID = 1L;

	public _ModelMain() {
		initThis();
	}

	public _ModelMain(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public _ModelMain(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public _ModelMain(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public _ModelMain(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public _ModelMain(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	private void initThis() {
		setColumnIdentifiers(new String[] {
				"Rec ID", 
				"PBL ID",
				"Seq. #", 
				"Description",
				"Client Name", 
				"Particulars",
				"Payment", 
				
				"Check #",
				"Check Date",
				"Check Status",
				"Deposit Date",
				
				"Amount",
				"Trans. Date", 
				"Assumed OR",
				"AR No.", 
				"OR Pending",
				"CTS Date", 
				"OR Date", 
				"Remarks",
				"OR Batch No.",
				"Full DP Date",
				"Branch"});
	}

	Class[] types = new Class[] {
			Integer.class, //Rec ID
			String.class, //PBL ID
			String.class, //Seq. No
			Object.class, //Description
			Object.class, //Client Name
			String.class, //Particulars
			String.class, //Payment Type
			
			String.class, //Check #
			Date.class, //Check Date
			String.class, //Check Status
			Date.class, //Date Deposited
			
			BigDecimal.class, //Amount
			Date.class, //Trans. Date
			String.class, //Assumed OR
			String.class, //AR No.
			Boolean.class, //OR Pending
			Date.class, //CTS Date
			Date.class, //OR Date
			Object.class, //Remarks
			Integer.class, //New OR No.
			Date.class, //Full DP Date
			String.class //Branch
	};

	public Class getColumnClass(int columnIndex) {
		return types[columnIndex];
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

}
