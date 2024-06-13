package tablemodel;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelFADProcessAdmin extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8738810102880371204L;

	public modelFADProcessAdmin() {
		initThis();
	}

	public modelFADProcessAdmin(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelFADProcessAdmin(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelFADProcessAdmin(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelFADProcessAdmin(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelFADProcessAdmin(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			"Select", //0
			"Proc. No", //1
			"Process Name" // 2
			
	};
	
	public Class[] CLASS_TYPES = new Class[] {
			String.class, //Emp ID
			String.class, //Emp Status
			Object.class, //Rank ID
			
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
	
	public void clear() {
		FncTables.clearTable(this);
	}

}
