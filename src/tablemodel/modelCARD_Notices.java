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
public class modelCARD_Notices extends DefaultTableModel {

	private static final long serialVersionUID = 2869714932235496590L;
	
	private boolean editable = false;
	
	public modelCARD_Notices() {
		initThis();
	}

	public modelCARD_Notices(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelCARD_Notices(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelCARD_Notices(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelCARD_Notices(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelCARD_Notices(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}
	
	String [] COLUMNS = new String[]{
			
			"Notice ID", //0
			"Notice Name", //1
			"Stage", //2
			"Batch No", //3
			"Date Prepared", //4
			"Prepared by", //5
			"Date Sent", //6
			"Addr Type", //7
			"Received by", //8
			"Relationship to Buyer", //9
			"Date Received", //10
			"Reason for Return", //11
			"RTS Date", //12
			"Mailed thru", //13
			"Orientation Date", //14
			"Orientation Time", //15
			"Orientation Venue", //16
			"Remarks", //17
			"RTS Created by", //18
			"RTS Date Created" //19
			
			
	};
	
	Class [] CLASS_TYPES = new Class[]{
			
			String.class, //Notice ID
			String.class, //Notice Name
			String.class, //Stage
			String.class, //Batch No
			Timestamp.class, //Date Prepared
			String.class, //Prepared by
			Timestamp.class, //Date Sent
			String.class, //Addr Type
			String.class, //Received By
			String.class, //Relationship to Buyer
			Timestamp.class, //Date Received
			String.class, //Reason for Return
			Timestamp.class, //RTS Date
			String.class, //Mailed thru
			Timestamp.class, //Orientation Date
			String.class, //Orientation Time
			String.class, //Orientation Venue
			String.class, //Remarks
			String.class, //RTS Created by
			Timestamp.class //RTS Date Created
			
		
	};
	
	Boolean [] COLUMNS_EDITABLE = new Boolean[]{
			
			false, //Notice ID
			false, //Notice Name
			false, //Stage
			false, //Batch No
			false, //Date Prepared
			false, //Prepared by
			false, //Date Sent
			false, //Addr Type
			false, //Received by
			false, //Relationship to Buyer
			false,  //Date Received
			false, //Reason for Return
			false, //RTS Date
			false, //Mailed thru
			false, //Orientation Date
			false, //Orientation Time
			false, //Orientation Venue
			false, //Remarks
			false, //RTS Created by
			false //RTS Date Created
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
