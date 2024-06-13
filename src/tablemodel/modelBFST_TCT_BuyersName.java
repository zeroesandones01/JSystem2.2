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

public class modelBFST_TCT_BuyersName extends
		DefaultTableModel {
	

	private static final long serialVersionUID = -382000912854590358L;

	private boolean editable = false;

	public modelBFST_TCT_BuyersName() {
		initThis();
	}

	public modelBFST_TCT_BuyersName(int rowCount,
			int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelBFST_TCT_BuyersName(Vector columnNames,
			int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelBFST_TCT_BuyersName(Object[] columnNames,
			int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelBFST_TCT_BuyersName(Vector data,
			Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelBFST_TCT_BuyersName(Object[][] data,
			Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}
	
	String [] COLUMNS = new String []{
		
		"Select", //0
		"Name", //1
		"Unit", //2
		"Pmt Stage", //3
		"TCT Date", //4
		"LOG Date", //5
		"DOAS Signed", //6
		"Entity ID", //7
		"Proj. ID", //8
		"PBL ID", //9
		"Seq. No" //10
		
	};
	
	Class [] CLASS_TYPES = new Class []{
		
		Boolean.class, //Select
		Object.class, //Name
		String.class, //Unit
		String.class, //Pmt Stage
		Timestamp.class, //TCT Date
		Timestamp.class, //LOG Date
		Timestamp.class, //DOAS Signed
		String.class, //Entity ID
		String.class, //Proj. ID
		String.class, //PBL ID
		Integer.class //Seq No
		
	};
	
	Boolean [] COLUMNS_EDITABLE = new Boolean []{
		
		true, //Select
		false, //Name
		false, //Unit
		false, //Pmt Stage
		false, //TCT Date
		false, //LOG Date
		false, //DOAS Signed
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
