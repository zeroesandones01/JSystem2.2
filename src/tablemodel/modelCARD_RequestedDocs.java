
package tablemodel;

import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

/**
 * @author John Lester Fatallo
 */
public class modelCARD_RequestedDocs extends DefaultTableModel {
	
	private static final long serialVersionUID = 8309504099238659766L;

	private boolean editable = false;
	
	public modelCARD_RequestedDocs() {
		initThis();
	}

	public modelCARD_RequestedDocs(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelCARD_RequestedDocs(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelCARD_RequestedDocs(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelCARD_RequestedDocs(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelCARD_RequestedDocs(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}
	
	String [] COLUMNS = new String[]{
			
			"Doc. ID", //0
			"Document Name", //1
			"Others (Specified)" //2
			
	};
	
	Class [] CLASS_TYPES = new Class[]{
			
			Object.class, //Doc. ID
			Object.class, //Purpose
			Object.class //Others (Specified)
		
	};
	
	Boolean [] COLUMNS_EDITABLE = new Boolean[]{
			
			false, //Doc ID
			false, //Purpose
			false //Others (Specified)
			
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
	
	public boolean isEditable(){
		return editable;
	}
	
	public void setEditable(boolean editable){
		this.editable = editable;
	}

}
