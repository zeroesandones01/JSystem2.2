package tablemodel;

import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelAddSubProject extends DefaultTableModel {

	private static final long serialVersionUID = -8738810102880371204L;

	public modelAddSubProject() {
		initThis();
	}

	public modelAddSubProject(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelAddSubProject(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelAddSubProject(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelAddSubProject(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelAddSubProject(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			"Sub Proj. ID", //0
			"Sub Proj. Name", //1
			"Sub Proj. Alias.", // 2
			"Sub Proj. Phase", //3
			"Co. ID", //4
			"Company Name", //5
			"Proj. ID", //6
			"Proj. Name", //7
			"Release Batch", //8
			"PAG-IBIG Code", //9
			"With Change Model", //10
			"LTS Date", //11
			"BOI Date", //12
			"BOI Reg. No." //13
	};
	
	public Class[] CLASS_TYPES = new Class[] {
			String.class, //Sub Proj. ID
			String.class, //Sub Proj. Name
			String.class, //Sub Proj. Alias
			String.class, //Sub Proj. Phase
			String.class, //CO ID
			String.class, //Company Name
			String.class, //Proj. ID
			String.class, //Proj. Name
			String.class, //Release Batch
			String.class, //Pag-Ibig Code
			Boolean.class, //With Change Model
			Timestamp.class, //LTS Date
			Timestamp.class, //BOI Date
			String.class //BOI Reg. No.
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
