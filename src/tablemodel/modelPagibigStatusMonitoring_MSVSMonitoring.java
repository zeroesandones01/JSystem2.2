/**
 * 
 */
package tablemodel;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

/**
 * @author John Lester Fatallo
 */
public class modelPagibigStatusMonitoring_MSVSMonitoring extends
		DefaultTableModel {

	private static final long serialVersionUID = -3252249366682815956L;
	private boolean editable = false;

	public modelPagibigStatusMonitoring_MSVSMonitoring() {
		initThis();
	}

	public modelPagibigStatusMonitoring_MSVSMonitoring(int rowCount,
			int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelPagibigStatusMonitoring_MSVSMonitoring(Vector columnNames,
			int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelPagibigStatusMonitoring_MSVSMonitoring(Object[] columnNames,
			int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelPagibigStatusMonitoring_MSVSMonitoring(Vector data,
			Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelPagibigStatusMonitoring_MSVSMonitoring(Object[][] data,
			Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}
	
	String [] COLUMNS = new String []{
		"Tag", 
		"Phase", 
		"Block", 
		"Lot", 
		"Project", 
		"Last Name", 
		"First Name", 
		"Middle Name", 
		"EXT", 
		"Maiden", 
		"Loanable", 
		"OR", 
		"Completion", 
		"%", 
		"NTC", 
		"NTP", 
		"Compliance", 
		"Findings", 
		"entity_id", 
		"projcode", 
		"pbl_id", 
		"seq_no"
	};
	
	Class [] CLASS_TYPES = new Class []{
		Boolean.class, 
		String.class, 
		String.class, 
		String.class, 
		String.class, 
		String.class, 
		String.class, 
		String.class, 
		String.class, 
		String.class, 
		String.class, 
		Date.class, 
		Date.class, 
		String.class, 
		Date.class, 
		Date.class, 
		Date.class, 
		String.class, 
		String.class, 
		String.class, 
		String.class, 
		Integer.class
	};
	
	Boolean [] COLUMNS_EDITABLE = new Boolean []{
		true, 
		false, 
		false, 
		false, 
		false, 
		false, 
		false, 
		false, 
		false, 
		false, 
		false, 
		false, 
		false, 
		false, 
		false, 
		false, 
		false, 
		false, 
		false, 
		false, 
		false, 
		false
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
