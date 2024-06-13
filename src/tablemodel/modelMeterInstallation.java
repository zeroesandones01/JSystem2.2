package tablemodel;

import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelMeterInstallation extends DefaultTableModel {

	private static final long serialVersionUID = 6440377320338240334L;

	private boolean editable = false;

	public modelMeterInstallation() {
		initThis();
	}

	public modelMeterInstallation(int rowCount,
			int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelMeterInstallation(Vector columnNames,
			int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelMeterInstallation(Object[] columnNames,
			int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelMeterInstallation(Vector data,
			Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelMeterInstallation(Object[][] data,
			Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}
	
	String [] COLUMNS = new String []{
		
		"Select", //0
		"Proj. Alias", //1
		"Unit", //2
		"Meter Install Date", //3 
		"Proj. ID", //4
		"PBL ID" //5
		
	};
	
	Class [] CLASS_TYPES = new Class []{
		
		Boolean.class, //Select
		String.class, //Proj. Alias
		String.class, //Unit
		Timestamp.class, //Meter Install
		String.class, //Proj. ID
		String.class //PBL ID

		
	};
	
	Boolean [] COLUMNS_EDITABLE = new Boolean []{
		
		true, //Select
		false, //Rec. ID
		false, //Proj. ID
		false, //Unit
		false, //Meter Install Date
		false //Proj. Alias
		
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
