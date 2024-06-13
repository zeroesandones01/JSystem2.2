
package tablemodel;

import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

/**
 * @author John Lester Fatallo
 */
public class modelCARD_ReqDocDetails extends DefaultTableModel {
	
	private static final long serialVersionUID = 8309504099238659766L;

	private boolean editable = false;
	
	public modelCARD_ReqDocDetails() {
		initThis();
	}

	public modelCARD_ReqDocDetails(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelCARD_ReqDocDetails(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelCARD_ReqDocDetails(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelCARD_ReqDocDetails(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelCARD_ReqDocDetails(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}
	
	String [] COLUMNS = new String[]{
			
			"Request No", //0
			"Request Date", //1
			"Requested by", //2
			"Request Received By", //3
			"Tel. No", //4
			"Remarks", //5
			"Released By", //6
			"Released Date", //7
			"Received By", //8
			"Received Date" //9
			
	};
	
	Class [] CLASS_TYPES = new Class[]{
			
			Object.class, //Request No
			Timestamp.class, //Requested Date
			Object.class, //Requested By
			Object.class, //Request Received by
			Object.class, //"Tel. No"
			Object.class, //Remarks
			Object.class, //Released By
			Timestamp.class, //Released Date
			Object.class, //Received By
			Timestamp.class //Received Date
			
	};
	
	Boolean [] COLUMNS_EDITABLE = new Boolean[]{
			
			false, //Request No
			false, //Request Date
			false, //Request By
			false, //Request Received by
			false,  //Tel. No
			false, //Remarks
			false, //Released By
			false, //Released Date
			false, //Received By
			false //Received Date
			
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
