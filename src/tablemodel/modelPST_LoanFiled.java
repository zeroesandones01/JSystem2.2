/**
 * 
 */
package tablemodel;

import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

/**
 * @author John Lester Fatallo
 */

public class modelPST_LoanFiled extends
		DefaultTableModel {
	
	private static final long serialVersionUID = -2986240756542413729L;

	private boolean editable = false;

	public modelPST_LoanFiled() {
		initThis();
	}

	public modelPST_LoanFiled(int rowCount,
			int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelPST_LoanFiled(Vector columnNames,
			int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelPST_LoanFiled(Object[] columnNames,
			int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelPST_LoanFiled(Vector data,
			Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelPST_LoanFiled(Object[][] data,
			Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}
	
	String [] COLUMNS = new String []{
		
		"Select", //0
		"Unit", //1
		"Name", //2
		"Date Completed", //3
		"% Constructed", //4
		"DP Term", //5
		"Pmt Stage", //6
		"Entity ID", //7
		"Proj. ID", //8
		"PBL ID", //9
		"Seq. No" //10
		
	};
	
	Class [] CLASS_TYPES = new Class []{
		
		Boolean.class, //Select
		String.class, //Unit
		String.class, //Name
		Timestamp.class, //Date Completed
		String.class, //% Constructed
		Integer.class, //DP Term
		String.class, //Pmt Stage
		Object.class, //Entity ID
		String.class, //Proj. ID
		String.class, //PBL ID
		Integer.class //Seq No
		
	};
	
	Boolean [] COLUMNS_EDITABLE = new Boolean []{
			
		true, //Select
		false, //Unit
		false, //Name
		false, //Date Completed
		false, //% Constructed
		false, //Dp term
		false, //Pmt Stage
		false, //Entity ID
		false, //Proj. ID
		false, //PBL ID
		false  //Seq No
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
