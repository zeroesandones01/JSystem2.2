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

public class modelPST_With_EPEB extends DefaultTableModel {

	private static final long serialVersionUID = 6656868250027369133L;

	private boolean editable = false;

	public modelPST_With_EPEB() {
		initThis();
	}

	public modelPST_With_EPEB(int rowCount,
			int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelPST_With_EPEB(Vector columnNames,
			int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelPST_With_EPEB(Object[] columnNames,
			int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelPST_With_EPEB(Vector data,
			Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelPST_With_EPEB(Object[][] data,
			Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}
	
	String [] COLUMNS = new String []{
		
		"Select", //0
		"Unit", //1
		"Name", //2
		"EPEB No.", //3 
		"EPEB Date", //4
		"Proj. Alias", //5
		"Proj. Name", //6
		"Entity ID", //7
		"Proj. ID", //8
		"PBL ID", //9
		"Seq" //10
		
	};
	
	Class [] CLASS_TYPES = new Class []{
		
		Boolean.class, //Select
		String.class, //Unit 
		String.class, //Name
		String.class, //EPEB No
		Timestamp.class, //EPEB Date
		String.class, //Proj. Alias
		String.class, //Proj. Name
		String.class, //Entity ID
		String.class, //Proj. ID
		String.class, //PBL ID
		Integer.class, //Seq
		
	};
	
	Boolean [] COLUMNS_EDITABLE = new Boolean []{
			
			true, //Select
			false, //Unit
			false, //Name
			true, //EPEB No 
			true, //EPEB Date
			false, //Prpj. Alias
			false, //Proj. Name
			false, //Entity ID
			false, //Proj. ID
			false, //PBL ID
			false //Seq
		
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
