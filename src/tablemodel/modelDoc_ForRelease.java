/**
 * 
 */
package tablemodel;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

/**
 * @author JLF
 */
public class modelDoc_ForRelease extends DefaultTableModel {

	private static final long serialVersionUID = -5644644886998148461L;
	private boolean editable = false;

	public modelDoc_ForRelease() {
		initThis();
	}

	public modelDoc_ForRelease(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelDoc_ForRelease(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelDoc_ForRelease(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelDoc_ForRelease(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelDoc_ForRelease(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}

	String[] COLUMNS = new String[] {
			"Select", // 0
			"ID", // 1
			"Doc Alias", // 2
			"Doc Name", //3
			"Dept. Alias",  // 4
			"Photocopy", //5
			"Rec. ID" //6
	};

	Class[] CLASS_TYPES = new Class[] {
			Boolean.class, //Select 
			Object.class, //ID
			Object.class, //Doc Alias
			Object.class, //Doc Name
			Object.class, //Dept. Alias
			Boolean.class, //Photocopy
			Integer.class //Rec ID
	};

	Boolean[] COLUMNS_EDITABLE = new Boolean[] {
			true, // 0
			false, // 1
			false, // 2
			false, // 3
			false, //4
			false, //5
			false //6
	};

	private void initThis() {
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
