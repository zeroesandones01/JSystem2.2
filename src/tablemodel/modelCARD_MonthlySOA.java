package tablemodel;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelCARD_MonthlySOA extends DefaultTableModel {

	private static final long serialVersionUID = 2188418488675343528L;
	
	private boolean editable = false;
	boolean[] COLUMNS_EDITABLE;	
	
	public modelCARD_MonthlySOA() {
		InitGUI();
	}

	public modelCARD_MonthlySOA(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		InitGUI();
	}

	public modelCARD_MonthlySOA(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		InitGUI();
	}

	public modelCARD_MonthlySOA(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		InitGUI();
	}

	public modelCARD_MonthlySOA(Vector data, Vector columnNames) {
		super(data, columnNames);
		InitGUI();
	}

	public modelCARD_MonthlySOA(Object[][] data, Object[] columnNames) {
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
	}
	
	public String[] COLUMNS = new String[] {
		
		"Period Covered", //0
		"Encoded Date", //1
		"Due for", //0
		"Water", //1
		"Garbage", 
		"Electricity", //2
		"Subdivision Dues", //3
		"Other Dues", //4
		"Adjustment", //5
		"Amount Due", //6
		"Amount Paid", //7
		"SOA No", //8
		"Adjusted by",//9
		"Adjust Date", //10
		"Other dues added by", //11
		"Other Dues add date", //12
		"Remarks" //13
		
	};
	
	public Class [] CLASS_TYPES = new Class[] {
		
		String.class, 
		Date.class,
		Date.class, //Due for
		BigDecimal.class, //Water
		BigDecimal.class, //Garbage
		BigDecimal.class, //Electricity
		BigDecimal.class, //Subdivision Dues
		BigDecimal.class, //Other Dues
		BigDecimal.class, //Adjustment
		BigDecimal.class, //Amount Due
		BigDecimal.class, //Amount Paid
		Object.class, //SOA No
		Object.class, //Adjusted by
		Date.class, //Adjust Date
		Object.class, //Other due added by
		Date.class, //Other dues add date
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
