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
public class modelChangeSchedule extends DefaultTableModel {

	
	private static final long serialVersionUID = 1564857051913631870L;
	private boolean editable = false;

	public modelChangeSchedule() {
		initThis();
	}

	public modelChangeSchedule(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelChangeSchedule(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelChangeSchedule(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelChangeSchedule(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelChangeSchedule(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}
	
	String [] COLUMNS = new String []{
		
		"Part ID", 
		"Part Desc",
		"New Sched"
		
	};
	
	Class [] CLASS_TYPES = new Class[]{
		
		String.class, //Part ID
		String.class, //Part Desc
		Timestamp.class //New Sched
		
	};
	
	Boolean [] COLUMNS_EDITABLE = new Boolean[]{
		
		false, //Part ID
		false, //Part Desc
		false //New Sched
		
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
