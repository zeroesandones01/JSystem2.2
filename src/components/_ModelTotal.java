package components;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class _ModelTotal extends DefaultTableModel {

	private static final long serialVersionUID = 1L;

	public _ModelTotal() {
		initThis();
	}

	public _ModelTotal(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public _ModelTotal(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public _ModelTotal(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public _ModelTotal(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public _ModelTotal(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	private void initThis() {
		setColumnIdentifiers(new String[] {
				"Rec ID", 
				"PBL ID",
				"Seq. No", 
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
			Object.class, //Rec ID
			Object.class, //PBL ID
			Object.class, //Seq. No
			Object.class, //Description
			Object.class, //Client Name
			Object.class, //Particulars
			String.class, //Payment Type
			
			String.class, //Check #
			Date.class, //Check Date
			String.class, //Check Status
			Date.class, //Date Deposited
			
			BigDecimal.class, //Amount
			Object.class, //Trans. Date
			Object.class, //Assumed OR
			Object.class, //AR No.
			Object.class, //OR Pending
			Object.class, //CTS Date
			Object.class, //OR Date
			Object.class,//Remarks
			Object.class, //New OR No.
			Date.class, //Full DP Date
			Object.class //Branch
	};

	public Class getColumnClass(int columnIndex) {
		return types[columnIndex];
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

}
