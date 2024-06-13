package tablemodel;

import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelDependentList extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -800977269370899008L;

	/**
	 * 
	 */

	public modelDependentList() {
		initThis();
	}

	public modelDependentList(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelDependentList(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelDependentList(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelDependentList(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelDependentList(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			"Name of Dependent", // 0
			"Birthdate", // 1
			"Occupation", // 2
			"School/Company" // 3
	};

	
	public Class[] CLASS_TYPES = new Class[] {
			String.class, //Name of Dependent
			Timestamp.class, //Birthdate
			String.class, //Occupation
			String.class //School/Company
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
