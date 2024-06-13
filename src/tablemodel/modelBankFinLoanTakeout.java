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
public class modelBankFinLoanTakeout extends DefaultTableModel {

	private static final long serialVersionUID = 898430820687984150L;
	private boolean editable = false;

	public modelBankFinLoanTakeout() {
		initThis();
	}

	public modelBankFinLoanTakeout(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelBankFinLoanTakeout(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelBankFinLoanTakeout(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelBankFinLoanTakeout(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelBankFinLoanTakeout(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}
	
	String [] COLUMNS = new String []{
		"Select", 			//0
		"Project", 			//1
		"Unit", 			//2
		"Client", 			//3
		"Release Date",		//4
		"Total Loan Amt.", 	//5
		"Principal Amt.", 	//6
		"Processing Fee", 	//7
		"Transfer Fee",		//8 
		"Other Income", 	//9
		"PBL ID", 			//10
		"Seq No." 			//11 

	};
	
	Class [] CLASS_TYPES = new Class []{
			
		Boolean.class, 		//Select
		String.class, 		//Project
		String.class, 		//Unit
		Object.class, 		//Client
		Timestamp.class, 	//Release Date
		BigDecimal.class, 	//Total Loan Amt.
		BigDecimal.class, 	//Principal Amt.
		BigDecimal.class, 	//Processing Fee
		BigDecimal.class, 	//Transfer Fee
		BigDecimal.class, 	//Other Income
		String.class, 		//PBL ID
		String.class 		//Seq No.
		
	};
	
	Boolean [] COLUMNS_EDITABLE = new Boolean []{
			
			true, 	//Select
			false, 	//Project
			false, 	//Unit
			false, 	//Client
			true, 	//Release Date
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
