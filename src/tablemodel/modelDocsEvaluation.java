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

public class modelDocsEvaluation extends
		DefaultTableModel {
	
	private static final long serialVersionUID = 7708450437001383309L;

	private boolean editable = false;

	public modelDocsEvaluation() {
		initThis();
	}

	public modelDocsEvaluation(int rowCount,
			int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelDocsEvaluation(Vector columnNames,
			int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelDocsEvaluation(Object[] columnNames,
			int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelDocsEvaluation(Vector data,
			Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelDocsEvaluation(Object[][] data,
			Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}
	
	String [] COLUMNS = new String []{
		
		"Select", //0
		"Rec. ID", //1
		"Proj. ID", //2
		"Proj. Alias", //3
		"Entity ID", //4
		"Client Name", //5
		"Sales Group", //6
		"Date Evaluated", //7
		"Evaluated by" //8
		
	};
	
	Class [] CLASS_TYPES = new Class []{
		
		Boolean.class, //Select
		Integer.class, //Rec. ID
		String.class, //Proj. ID
		String.class, //Proj. Alias
		String.class, //Entity ID
		String.class, //Client Name
		String.class, //Sales Group
		Timestamp.class, //Date Evaluated
		String.class //Evaluated by
		
	};
	
	Boolean [] COLUMNS_EDITABLE = new Boolean []{
		
		true, //Select
		false, //Rec. ID
		false, //Proj. ID
		false, //Proj. Alias
		false, //Entity ID
		false, //Client Name
		false, //Sales Group
		false //Date Evaluated
		
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
