
package tablemodel;

import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

/**
 * @author John Lester Fatallo
 */
public class modelCARD_DocFindings extends DefaultTableModel {
	
	private static final long serialVersionUID = 8309504099238659766L;

	private boolean editable = false;
	
	public modelCARD_DocFindings() {
		initThis();
	}

	public modelCARD_DocFindings(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelCARD_DocFindings(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelCARD_DocFindings(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelCARD_DocFindings(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelCARD_DocFindings(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}
	
	String [] COLUMNS = new String[]{
			
			"Date Eval", //0
			"Findings", //1
			"Eval", //2
			"Date", //3
			"Tagged Complied" //4
			
	};
	
	Class [] CLASS_TYPES = new Class[]{
			
			Timestamp.class, //Date Eval
			Object.class, //Findings
			String.class, //Eval
			Timestamp.class, //Date, 
			String.class //Tagged Complied
		
	};
	
	Boolean [] COLUMNS_EDITABLE = new Boolean[]{
			
			false, //Date Eval
			true, //Findings
			false, //Eval
			false,  //Date
			false //Tagged Complied
			
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
