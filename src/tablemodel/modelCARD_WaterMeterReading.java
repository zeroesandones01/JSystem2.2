package tablemodel;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelCARD_WaterMeterReading extends DefaultTableModel {

	private static final long serialVersionUID = -9028975856268127598L;

	private boolean editable = false;
	boolean[] COLUMNS_EDITABLE;	
	
	public modelCARD_WaterMeterReading() {
		InitGUI();
	}

	public modelCARD_WaterMeterReading(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		InitGUI();
	}

	public modelCARD_WaterMeterReading(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		InitGUI();
	}

	public modelCARD_WaterMeterReading(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		InitGUI();
	}

	public modelCARD_WaterMeterReading(Vector data, Vector columnNames) {
		super(data, columnNames);
		InitGUI();
	}

	public modelCARD_WaterMeterReading(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		InitGUI();
	}

	private void InitGUI() {
		setColumnIdentifiers(COLUMNS);
		
		COLUMNS_EDITABLE = new boolean[] {
				false, 
				false, 
				false, 
				false, 
				false, 
				false, 
				false
			};
	}
	
	public String[] COLUMNS = new String[] {
		"Reading Date", //0
		"Previous Reading", //2
		"Present Reading", //
		"Consumption", //3
		"Water Cost", //4
		"Amount Due", //5
		"Remarks" //6
	};
	
	public Class [] CLASS_TYPES = new Class[] { 
		Date.class, //Reading Date
		BigDecimal.class, //
		BigDecimal.class, 
		BigDecimal.class, 
		BigDecimal.class,
		BigDecimal.class,
		Object.class
	};

	public Class getColumnClass(int columnIndex) {
		return CLASS_TYPES[columnIndex];
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return COLUMNS_EDITABLE[columnIndex];
	}
	
	public boolean isCellEditable(int columnIndex) {
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
