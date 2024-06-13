/**
 * 
 */
package tablemodel;

import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

/**
 * @author 
 */
public class modelCARD_ReservationDocs extends DefaultTableModel {

	private static final long serialVersionUID = 4465175722837767200L;
	private boolean editable = false;
	
	public modelCARD_ReservationDocs() {
		initThis();
	}

	public modelCARD_ReservationDocs(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelCARD_ReservationDocs(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelCARD_ReservationDocs(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelCARD_ReservationDocs(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelCARD_ReservationDocs(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}
	
	String[] COLUMNS = new String[] {
			"ID", // 0
			"By", //1
			"Doc Desc", // 2 
			"Docs In", // 3
			"Docs Out", // 4
			"Notarized", // 5
			"Docket #", // 6
			"Copies", // 7
			"Remarks", // 8
			"Promisory Due", // 9
			"Days Granted" // 10
	};
	
	Class[] CLASS_TYPES = new Class[] {

			String.class, //ID
			Object.class, //By
			Object.class, //Document
			Timestamp.class, //Docs In
			Timestamp.class, //Docs Out
			Timestamp.class, //Notarized
			String.class, //Docket #
			Integer.class, //Copies
			Object.class, //Remarks
			Timestamp.class, //Promisory Due
			Integer.class //Days Granted

	};
	
	Boolean[] COLUMN_EDITABLE = new Boolean[]{
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
	
	private void initThis() {
		setColumnIdentifiers(COLUMNS);
	}


	public Class getColumnClass(int columnIndex) {
		return CLASS_TYPES[columnIndex];
	}
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return COLUMN_EDITABLE[columnIndex];
	}

	public boolean isEditable() {
		return editable;
	}
	
	public void setEditable(boolean editable) {
		this.editable = editable;
		if(editable){
			COLUMN_EDITABLE = new Boolean[]{
					true, // Tag
					true, // Plate No.
					true, // Make/Brand
					true, // Status
					//false,// "Rec_ID" // 4
			};
		}else{
			COLUMN_EDITABLE = new Boolean[]{
					false, // Tag
					false, // Plate No.
					false, // Make/Brand
					false, // Status
					//false,// "Rec_ID" // 4
			};
		}
	}


	public void clear() {
		FncTables.clearTable(this);
	}

}
