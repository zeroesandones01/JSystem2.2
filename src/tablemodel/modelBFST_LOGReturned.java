package tablemodel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

/**
 * @author John Lester Fatallo
 */

public class modelBFST_LOGReturned extends
		DefaultTableModel {
	

	private static final long serialVersionUID = -7533438656583953290L;

	private boolean editable = false;

	public modelBFST_LOGReturned() {
		initThis();
	}

	public modelBFST_LOGReturned(int rowCount,
			int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelBFST_LOGReturned(Vector columnNames,
			int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelBFST_LOGReturned(Object[] columnNames,
			int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelBFST_LOGReturned(Vector data,
			Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelBFST_LOGReturned(Object[][] data,
			Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}
	
	String [] COLUMNS = new String []{
		
		"Select", //0
		"Name", //1
		"Unit", //2
		"Loan Amt", //3
		"Date Filed", //4
		"Entity ID", //5
		"Proj. ID", //6
		"PBL ID", //7
		"Seq. No" //8
		
	};
	
	Class [] CLASS_TYPES = new Class []{
		
		Boolean.class, //Select
		Object.class, //Name
		String.class, //Unit
		BigDecimal.class, //Loan Amt
		Timestamp.class, //Date Filed
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
		false, //Date Filed
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
