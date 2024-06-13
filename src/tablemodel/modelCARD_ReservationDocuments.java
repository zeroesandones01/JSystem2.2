package tablemodel;

import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class modelCARD_ReservationDocuments extends DefaultTableModel {

	private static final long serialVersionUID = 1L;

	public modelCARD_ReservationDocuments() {
		initThis();
	}

	public modelCARD_ReservationDocuments(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelCARD_ReservationDocuments(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelCARD_ReservationDocuments(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelCARD_ReservationDocuments(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelCARD_ReservationDocuments(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}
	
	private void initThis() {
		setColumnIdentifiers(new String[] {
				"ID", // 0
				"By", //1
				"Doc Desc", // 2 
				"Date Issued", //3
				"Validity", //4
				"Docs In", // 5
				"Docs Out", // 6
				"Notarized", // 7
				"Promisory Due", // 8
				"Days Granted" // 9
				
		});
	}

	Class[] types = new Class[] {
			
			String.class, //ID
			String.class, //By
			Object.class, //Document
			String.class, //Additional Info
			String.class, //Validity
			Timestamp.class, //Docs In
			Timestamp.class, //Docs Out
			Timestamp.class, //Notarized
			Timestamp.class, //Promisory Due
			Integer.class //Days Granted
					
	};

	public Class getColumnClass(int columnIndex) {
		return types[columnIndex];
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

}
