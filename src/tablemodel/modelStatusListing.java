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

public class modelStatusListing extends
		DefaultTableModel {
	
	private static final long serialVersionUID = -512393680803487641L;

	private boolean editable = false;

	public modelStatusListing() {
		initThis();
	}

	public modelStatusListing(int rowCount,
			int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelStatusListing(Vector columnNames,
			int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelStatusListing(Object[] columnNames,
			int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelStatusListing(Vector data,
			Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelStatusListing(Object[][] data,
			Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}
	
	String [] COLUMNS = new String []{
		
		"ID", //0
		"Status Desc", //1
		"Condition", //2
		"Date" //3
		
	};
	
	Class [] CLASS_TYPES = new Class []{
		
		String.class, //Buyer Status ID
		String.class, //Status Desc
		String.class, //Condition
		Timestamp.class //Date
		
	};
	
	Boolean [] COLUMNS_EDITABLE = new Boolean []{
			
		false, //Buyer Status ID
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
