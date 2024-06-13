package tablemodel;

import java.sql.Timestamp;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

/**
 * @author John Lester Fatallo
 */

public class modelReleasedDocs extends DefaultTableModel {

	private static final long serialVersionUID = -7722785453608859895L;
	private boolean editable = false;

	public modelReleasedDocs() {
		initThis();
	}

	public modelReleasedDocs(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelReleasedDocs(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelReleasedDocs(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelReleasedDocs(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelReleasedDocs(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}
	
	String[] COLUMNS = new String[] {
			
			"Request No.",
			"Request Date", 
			"ID", // 1
			"Doc Alias", // 2
			"Doc Name", //3
			"Dept. Alias", // 4
			"Released By",
			"Released Date"
	};
	
	Class[] CLASS_TYPES = new Class[] {
			
			Object.class,
			Timestamp.class, 
			Object.class, //ID
			Object.class, //Doc Alias
			Object.class, //Doc Name
			Object.class, //Dept. Alias
			Object.class, //Released by
			Timestamp.class// Released date
	};
	
	Boolean[] COLUMNS_EDITABLE = new Boolean[] {
			
			false, //0
			false, //1
			false, // 2
			false, // 3
			false, // 4
			false, //5
			false, //6
			false, 
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
