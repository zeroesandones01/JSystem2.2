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

public class modelUnitStatusListing extends
		DefaultTableModel {

	private static final long serialVersionUID = -8213580288852769233L;

	private boolean editable = false;

	public modelUnitStatusListing() {
		initThis();
	}

	public modelUnitStatusListing(int rowCount,
			int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelUnitStatusListing(Vector columnNames,
			int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelUnitStatusListing(Object[] columnNames,
			int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelUnitStatusListing(Vector data,
			Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelUnitStatusListing(Object[][] data,
			Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}
	
	String [] COLUMNS = new String []{
		
		"Status Desc", //1
		"Condition", //2
		"Date" //3
		
	};
	
	Class [] CLASS_TYPES = new Class []{
		
		String.class, //Status Desc
		String.class, //Condition
		Timestamp.class //Date
		
	};
	
	Boolean [] COLUMNS_EDITABLE = new Boolean []{
			
		false, //Status Desc
		false, //Condition
		false //Date
		
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
