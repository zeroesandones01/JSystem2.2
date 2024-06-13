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
public class modelCARD_DocsHistory extends DefaultTableModel {

	private static final long serialVersionUID = -13718272661304406L;
	
private boolean editable = false;
	
	public modelCARD_DocsHistory() {
		initThis();
	}

	public modelCARD_DocsHistory(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelCARD_DocsHistory(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelCARD_DocsHistory(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelCARD_DocsHistory(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelCARD_DocsHistory(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}
	
	String [] COLUMNS = new String[]{
			
			"Status", //0
			"Received By", //1
			"Received Date", //2
			"Received From" //3
			
	};
	
	Class [] CLASS_TYPES = new Class[]{
			
			String.class, //Status
			String.class, //Received by
			Timestamp.class, //Received Date
			String.class //Received From
		
	};
	
	Boolean [] COLUMNS_EDITABLE = new Boolean[]{
			
			false, //Status
			false, //Received By
			false, //Received Date
			false  //Received From
			
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
