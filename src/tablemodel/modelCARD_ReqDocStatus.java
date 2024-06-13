
package tablemodel;

import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

/**
 * @author John Lester Fatallo
 */
public class modelCARD_ReqDocStatus extends DefaultTableModel {
	
	private static final long serialVersionUID = 8309504099238659766L;

	private boolean editable = false;
	
	public modelCARD_ReqDocStatus() {
		initThis();
	}

	public modelCARD_ReqDocStatus(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelCARD_ReqDocStatus(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelCARD_ReqDocStatus(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelCARD_ReqDocStatus(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelCARD_ReqDocStatus(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}
	
	String [] COLUMNS = new String[]{
			
			"Status", //0
			"Date", //1
			"Done By" //2
			
	};
	
	Class [] CLASS_TYPES = new Class[]{
			
			Object.class, //Statuss
			Timestamp.class, //Date
			Object.class //Done By
		
	};
	
	Boolean [] COLUMNS_EDITABLE = new Boolean[]{
			
			false, //Purpose
			false, //Others (Specified)
			false //Done BY
			
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
