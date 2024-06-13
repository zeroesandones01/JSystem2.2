/**
 * 
 */
package tablemodel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

/**
 * @author John Lester Fatallo
 */
public class modelBankFinLoanTakeout_tagged extends DefaultTableModel {

	private static final long serialVersionUID = 898430820687984150L;
	private boolean editable = false;

	public modelBankFinLoanTakeout_tagged() {
		initThis();
	}

	public modelBankFinLoanTakeout_tagged(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelBankFinLoanTakeout_tagged(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelBankFinLoanTakeout_tagged(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelBankFinLoanTakeout_tagged(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelBankFinLoanTakeout_tagged(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}
	
	String [] COLUMNS = new String []{
		"Client Seq. No.",	//0
		"OR No.",			//1
		"Payment",			//2
		"Payment Date",		//3
		"Project", 			//4
		"Unit", 			//5
		"Client", 			//6		
		"Total Loan Amt.", 	//7
		"Principal Amt.", 	//8
		"Processing Fee", 	//9
		"Transfer Fee",		//10 
		"Other Income", 	//11
		"PBL ID", 			//12
		"Seq No." 			//13 
		

	};
	
	Class [] CLASS_TYPES = new Class []{			
		
		String.class, 		//Client Seq. No.
		String.class, 		//OR No.
		String.class, 		//Payment
		Timestamp.class, 	//Payment Date
		String.class, 		//Project
		String.class, 		//Unit
		Object.class, 		//Client		
		BigDecimal.class, 	//Total Loan Amt.
		BigDecimal.class, 	//Principal Amt.
		BigDecimal.class, 	//Processing Fee
		BigDecimal.class, 	//Transfer Fee
		BigDecimal.class, 	//Other Income
		String.class, 		//PBL ID
		String.class 		//Seq No.
		
	};
	
	Boolean [] COLUMNS_EDITABLE = new Boolean []{
			
			false, 	//Client Seq. No.
			false, 	//OR No.
			false, 	//Payment
			false, 	//Project
			false, 	//Unit
			false, 	//Client
			true, 	//Payment Date
			false, 	//Total Loan Amt.
			false, 	//Principal Amt.
			false, 	//Processing Fee
			false, 	//Transfer Fee
			true, 	//Transfer Fee
			false, 	//PBL ID
			false 	//Seq No.
		
	};

	private void initThis(){
		setColumnIdentifiers(COLUMNS);
	}
	
	public Class getColumnClass(int columnIndex) {
		return CLASS_TYPES[columnIndex];
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return COLUMNS_EDITABLE[columnIndex];
	}
	
	public void clear() {
		FncTables.clearTable(this);
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}
}
