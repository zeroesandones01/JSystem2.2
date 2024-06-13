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

public class modelPST_DOA_Signed extends
		DefaultTableModel {

	private static final long serialVersionUID = -2905833852587301189L;
	private boolean editable = false;

	public modelPST_DOA_Signed() {
		initThis();
	}

	public modelPST_DOA_Signed(int rowCount,
			int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelPST_DOA_Signed(Vector columnNames,
			int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelPST_DOA_Signed(Object[] columnNames,
			int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelPST_DOA_Signed(Vector data,
			Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelPST_DOA_Signed(Object[][] data,
			Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}
	
	String [] COLUMNS = new String []{
					
		"Select", //0
		"Proj. Name", //1
		"Unit Desc", //2
		"Entity ID", //3
		"Client", //4
		"Proj. ID", //5
		"PBL ID", //6
		"Seq. No", //7
		"NOA Released Date" //8

	};
	
	Class [] CLASS_TYPES = new Class []{
			
		Boolean.class, //Select
		String.class, //Proj. Name
		String.class, //Unit Desc
		String.class, //Entity ID
		String.class, //Entity Name
		String.class, //Proj. ID
		String.class, //PBL ID
		Integer.class, //Seq No
		Timestamp.class //NOA Released Date
		
	};
	
	Boolean [] COLUMNS_EDITABLE = new Boolean []{
			
		true, //Select 
		false, //Proj. Name
		false, //Unit Desc
		false, //Entity ID
		false, //Entity Name
		false, //Proj. ID
		false, //PBL ID
		false, //Seq No
		false //NOA Released Date
		
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
