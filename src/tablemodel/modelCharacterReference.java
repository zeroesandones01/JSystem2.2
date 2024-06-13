package tablemodel;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelCharacterReference extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8738810102880371204L;

	public modelCharacterReference() {
		initThis();
	}

	public modelCharacterReference(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelCharacterReference(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelCharacterReference(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelCharacterReference(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelCharacterReference(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			"Rec. ID", //0
			"Name", // 1
			"Relation", // 2
			"Address", // 3
			"Contact No./s", // 4
			"Status" // 5
			
	};

	
	public Class[] CLASS_TYPES = new Class[] {
			
			Integer.class, //Rec ID
			String.class, //Name of Supplier
			String.class, //Relation
			String.class, //Address
			String.class, //Contact No./s
			String.class //Status
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
	}

	public Class getColumnClass(int columnIndex) {
		return CLASS_TYPES[columnIndex];
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}
	public void clear(){
		FncTables.clearTable(this);
	}

}
