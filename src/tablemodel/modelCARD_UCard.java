package tablemodel;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelCARD_UCard extends DefaultTableModel {

	private static final long serialVersionUID = 1159825708850221362L;
	private boolean editable = false;
	boolean[] COLUMNS_EDITABLE;	
	
	public modelCARD_UCard() {
		InitGUI();
	}

	public modelCARD_UCard(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		InitGUI();
	}

	public modelCARD_UCard(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		InitGUI();
	}

	public modelCARD_UCard(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		InitGUI();
	}

	public modelCARD_UCard(Vector data, Vector columnNames) {
		super(data, columnNames);
		InitGUI();
	}

	public modelCARD_UCard(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		InitGUI();
	}

	private void InitGUI() {
		setColumnIdentifiers(COLUMNS);
		
		COLUMNS_EDITABLE = new boolean[] {
				false, 
				false, 
				false, 
				false
			};
	}
	
	public String[] COLUMNS = new String[] {
		"Date Paid", //0
		"Particular", //1
		"Amount", //2
		"AR No" //3
	};
	
	public Class [] CLASS_TYPES = new Class[] { 
		Date.class, //
		Object.class, //
		BigDecimal.class, // 
		Object.class //
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
