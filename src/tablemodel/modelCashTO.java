package tablemodel;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

/**
 * @author John Lester Fatallo
 */

public class modelCashTO extends
		DefaultTableModel {

	private static final long serialVersionUID = -6697771362127968900L;

	private boolean editable = false;

	public modelCashTO() {
		initThis();
	}

	public modelCashTO(int rowCount,
			int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelCashTO(Vector columnNames,
			int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelCashTO(Object[] columnNames,
			int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelCashTO(Vector data,
			Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelCashTO(Object[][] data,
			Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}
	
	String [] COLUMNS = new String []{
		
		"Select", //0
		"Name", //1
		"Proj. Alias", //2
		"Unit", //3
		"Notice Type",//4
		"Destination", //5
		"Sent By", //6
		"Entity ID", //7
		"Proj. ID", //8
		"PBL ID", //9
		"Seq No" //10
		
	};
	
	Class [] CLASS_TYPES = new Class []{
		
		Boolean.class, //Select
		String.class, //Name
		String.class, //Proj. Alias
		String.class, //Unit
		String.class, //Notice Type
		String.class, //Destination
		String.class, //Sent By
		String.class, //Entity ID
		String.class, //Proj. ID
		String.class, //PBL ID
		Integer.class //Seq No
		
	};
	
	Boolean [] COLUMNS_EDITABLE = new Boolean []{
			
		true, //Select
		false, //Name
		false, //Proj. Alias
		false, //Unit
		false, //Notice Type
		false, //Destination
		false, //Sent By
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
