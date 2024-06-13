package tablemodel;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelAddProject extends DefaultTableModel {

	private static final long serialVersionUID = -8738810102880371204L;

	public modelAddProject() {
		initThis();
	}

	public modelAddProject(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelAddProject(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelAddProject(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelAddProject(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelAddProject(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			"Proj. ID", //0
			"Proj. Name", //1
			"Proj. Alias.", // 2
			"Co. ID", //3
			"Company Name", //4
			"Street", //5
			"Barangay", //6
			"Province", //8
			"City/Municipality", //10
			"Vatable" //11
	};
	
	public Class[] CLASS_TYPES = new Class[] {
			String.class, //Proj. ID
			String.class, //Proj. Name
			String.class, //Proj. Alias
			String.class, //Co. ID
			String.class, //Company Name
			String.class, //Street
			String.class, //Barangay
			String.class, //Province
			String.class, //Municipality
			Boolean.class //Vatable
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
