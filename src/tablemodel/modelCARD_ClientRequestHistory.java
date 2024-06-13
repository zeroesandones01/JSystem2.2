/**
 * 
 */
package tablemodel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

/**
 * @author JLF
 */
public class modelCARD_ClientRequestHistory extends DefaultTableModel {

	private static final long serialVersionUID = -2042273135855262760L;
	
	private boolean editable = false;

	public modelCARD_ClientRequestHistory() {
		initThis();
	}

	public modelCARD_ClientRequestHistory(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelCARD_ClientRequestHistory(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelCARD_ClientRequestHistory(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelCARD_ClientRequestHistory(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelCARD_ClientRequestHistory(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}
	
	String [] COLUMNS = new String[]{
			"ID", //0
			"Request No.", //1
			"Description", //2
			"Request Date", //3
			"Request By", //4
			"Remarks", //5
			"Status", //6
			"Encoded by", //7
			"Approved by" //8
			
	};
	
	Class [] CLASS_TYPES = new Class[]{
			
		String.class, //ID
		Object.class, //Request No
		Object.class, //Description
		Timestamp.class, //Request Date
		String.class, //Request by
		String.class, //Remarks
		String.class, //Stat
		String.class, //Encoded by
		String.class //Approved by
		
	};
	
	Boolean [] COLUMNS_EDITABLE = new Boolean[]{
			
		false, //ID
		false, //Request No
		false, //Description
		false, //Request Date
		false, //Request by
		false, //Remarks
		false, //Stat
		false, //Encoded by
		false  //Approved by
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
