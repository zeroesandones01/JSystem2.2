package tablemodel;

import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class modelCARD_PrintedDocuments extends DefaultTableModel {

	private static final long serialVersionUID = 1L;

	public modelCARD_PrintedDocuments() {
		initThis();
	}

	public modelCARD_PrintedDocuments(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelCARD_PrintedDocuments(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelCARD_PrintedDocuments(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelCARD_PrintedDocuments(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelCARD_PrintedDocuments(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	private void initThis() {
		setColumnIdentifiers(new String[] {
				"Report", // 0
				"Date Printed", // 1 
				"Printed By" // 2
		});
	}

	Class[] types = new Class[] {
			Object.class, //ID
			Timestamp.class, //Document
			Object.class //Docs In
	};

	public Class getColumnClass(int columnIndex) {
		return types[columnIndex];
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

}
