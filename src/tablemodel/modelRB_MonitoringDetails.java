package tablemodel;

import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelRB_MonitoringDetails extends DefaultTableModel {

	private static final long serialVersionUID = 1L;

	private boolean editable = false;
	//boolean[] COLUMN_EDITABLE;

	public modelRB_MonitoringDetails() {
		initThis();
	}

	public modelRB_MonitoringDetails(boolean editable) {
		initThis();
	}

	public modelRB_MonitoringDetails(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelRB_MonitoringDetails(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelRB_MonitoringDetails(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelRB_MonitoringDetails(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelRB_MonitoringDetails(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}

	String[] COLUMNS = new String[] {
			"Stage ID", // 0
			"Stage", // 1
			"Notice ID", // 2
			"Notice", // 3
			"Rec ID", // 4
			"Date Sent", // 5
			"Date Received", // 6
			"Client ID", // 7
			"Proj_ID", // 8
			"PBL_ID", // 9
			"Seq_No" // 10
			
			
	};

	Class[] CLASS_TYPES = new Class[] {
			String.class, // Stage ID
			String.class, // Stage
			String.class, // Notice ID
			Object.class, // Notice
			String.class, // Rec ID
			Timestamp.class,
			Timestamp.class,
			String.class, // Entity ID
			String.class, // Proj ID
			String.class, // PBL ID
			Integer.class // Seq No
			
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
	}

	public Class getColumnClass(int columnIndex) {
		return CLASS_TYPES[columnIndex];
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
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
