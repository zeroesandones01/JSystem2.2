/**
 * 
 */
package tablemodel;

import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

/**
 * @author John Lester Fatallo
 */
public class modelPagibigStatusMonitoring_AccountQuery_StatusHistory extends
		DefaultTableModel {
	
	private static final long serialVersionUID = 6829406850540951900L;

	private boolean editable = false;

	public modelPagibigStatusMonitoring_AccountQuery_StatusHistory() {
		initThis();
	}

	public modelPagibigStatusMonitoring_AccountQuery_StatusHistory(int rowCount,
			int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelPagibigStatusMonitoring_AccountQuery_StatusHistory(
			Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelPagibigStatusMonitoring_AccountQuery_StatusHistory(
			Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelPagibigStatusMonitoring_AccountQuery_StatusHistory(Vector data,
			Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelPagibigStatusMonitoring_AccountQuery_StatusHistory(
			Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}
	
	String [] COLUMNS = new String []{
		
		"Date", //0
		"Status", //1 
		"Tagged By" //2
		
	};
	
	Class [] CLASS_TYPES = new Class[]{
		
		Timestamp.class, //Date
		String.class, //Status
		String.class //Tagged By
		
	};
	
	Boolean [] COLUMNS_EDITABLE = new Boolean []{
		
		false, //Date 
		false, //Status
		false //Tagged by
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
