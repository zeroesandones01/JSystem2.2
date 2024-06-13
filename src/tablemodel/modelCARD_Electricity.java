package tablemodel;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelCARD_Electricity extends DefaultTableModel {

	private static final long serialVersionUID = 8295611722676351894L;
	
	private boolean editable = false;
	boolean[] COLUMNS_EDITABLE;	
	
	public modelCARD_Electricity() {
		InitGUI();
	}

	public modelCARD_Electricity(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		InitGUI();
	}

	public modelCARD_Electricity(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		InitGUI();
	}

	public modelCARD_Electricity(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		InitGUI();
	}

	public modelCARD_Electricity(Vector data, Vector columnNames) {
		super(data, columnNames);
		InitGUI();
	}

	public modelCARD_Electricity(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		InitGUI();
	}

	private void InitGUI() {
		setColumnIdentifiers(COLUMNS);
		
		COLUMNS_EDITABLE = new boolean[] {
				false, //Reading Date
				false, //Previous Reading
				false, //Present Reading
				false, //Consumption
				false, //kwHr Cost
				false, //Amount Due
				false, //Amt Paid(by Buyer)
				false, //Balance
				false, //Amt Paid by ADC
				false //Remarks
			};
	}
	
	public String[] COLUMNS = new String[] {
			"Reading Date", //0
			"Previous Reading", //1
			"Present Reading", //2
			"Consumption", //3
			"kwHr Cost", //4
			"Amount Due", //5
			"Amt Paid(by Buyer)", //6
			"Balance", //7
			"Amt Paid(by ADC)", //8
			"Remarks"
	};
	
	public Class [] CLASS_TYPES = new Class[] {
			Date.class, //Reading Date
			BigDecimal.class, //Previous Reading
			BigDecimal.class, //Present Reading
			BigDecimal.class, //Consumption
			BigDecimal.class, //kwHr Cost
			BigDecimal.class, //Amount Due
			BigDecimal.class, //Amt Paid(by Buyer)
			BigDecimal.class, //Balance
			BigDecimal.class, //Amt Paid(by ADC)
			Object.class //Remarks
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
