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

public class modelBFST_LOGExt extends
		DefaultTableModel {
	
	private static final long serialVersionUID = -3244766219958890078L;

	private boolean editable = false;

	public modelBFST_LOGExt() {
		initThis();
	}

	public modelBFST_LOGExt(int rowCount,
			int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelBFST_LOGExt(Vector columnNames,
			int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelBFST_LOGExt(Object[] columnNames,
			int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelBFST_LOGExt(Vector data,
			Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelBFST_LOGExt(Object[][] data,
			Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}
	
	String [] COLUMNS = new String []{
		
		"Select", //0
		"Name", //1
		"Unit", //2
		"Date Filed", //3
		"Status", //4
		"LOG Validity", //5
		"Entity ID", //6
		"Proj. ID", //7
		"PBL ID", //8
		"Seq. No" //9
		
	};
	
	Class [] CLASS_TYPES = new Class []{
		
		Boolean.class, //Select
		Object.class, //Name
		String.class, //Unit
		Timestamp.class, //Date Filed
		String.class, //Status
		Timestamp.class, //LOG Validity
		String.class, //Entity ID
		String.class, //Proj. ID
		String.class, //PBL ID
		Integer.class //Seq No
		
	};
	
	Boolean [] COLUMNS_EDITABLE = new Boolean []{
		
		true, //Select
		false, //Name
		false, //Unit
		false, //Date Filed
		false, //Status
		false, //LOG Validity
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
