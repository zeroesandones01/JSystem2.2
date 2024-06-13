/**
 * 
 */
package tablemodel;

import java.math.BigDecimal;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

/**
 * @author John Lester Fatallo
 */

public class modelBFST_LOGFiled extends
		DefaultTableModel {
	
	private static final long serialVersionUID = -2986240756542413729L;

	private boolean editable = false;

	public modelBFST_LOGFiled() {
		initThis();
	}

	public modelBFST_LOGFiled(int rowCount,
			int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelBFST_LOGFiled(Vector columnNames,
			int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelBFST_LOGFiled(Object[] columnNames,
			int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelBFST_LOGFiled(Vector data,
			Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelBFST_LOGFiled(Object[][] data,
			Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}
	
	String [] COLUMNS = new String []{
		
		"Select", //0
		"Name", //1
		"Unit", //2
		"Loan Amt", //3
		"DP Term", //4
		//"Approved Bank Loan Term", //5
		"DP Paid", //6
		"Entity ID", //7
		"Proj. ID", //8
		"PBL ID", //9
		"Seq. No" //10
		
	};
	
	Class [] CLASS_TYPES = new Class []{
		
		Boolean.class, //Select
		Object.class, //Name
		String.class, //Unit
		BigDecimal.class, //Loan Amt
		Integer.class, //DP Term
		//Integer.class, ///Approved Bank Loan Term
		String.class, //DP Paid
		String.class, //Entity ID
		String.class, //Proj. ID
		String.class, //PBL ID
		Integer.class //Seq No
		
	};
	
	Boolean [] COLUMNS_EDITABLE = new Boolean []{
		
		true, //Select
		false, //Name
		false, //Unit
		false, //Loan Amt
		false, //DP Term
		//false, //Approved 
		false, //DP Paid
		false, //Entity ID
		false, //Proj. ID
		false, //PBL ID
		false //Seq No
		
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
