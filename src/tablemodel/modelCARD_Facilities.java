package tablemodel;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelCARD_Facilities extends DefaultTableModel {

	private static final long serialVersionUID = -7337477918956614314L;
	
	private boolean editable = false;
	boolean[] COLUMNS_EDITABLE;	
	
	public modelCARD_Facilities() {
		InitGUI();
	}

	public modelCARD_Facilities(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		InitGUI();
	}

	public modelCARD_Facilities(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		InitGUI();
	}

	public modelCARD_Facilities(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		InitGUI();
	}

	public modelCARD_Facilities(Vector data, Vector columnNames) {
		super(data, columnNames);
		InitGUI();
	}

	public modelCARD_Facilities(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		InitGUI();
	}

	private void InitGUI() {
		setColumnIdentifiers(COLUMNS);
		
		COLUMNS_EDITABLE = new boolean[] {
				false, //Usage Date
				false, //Facility Used
				false, //Amount Due
				false //Remarks
			};
	}
	
	public String[] COLUMNS = new String[] {
			"Usage Date", //0
			"Facility Used", //1
			"Amount Due", //2
			"Remarks" //3
	};
	
	public Class [] CLASS_TYPES = new Class[] {
			Date.class, //Usage Date
			Object.class, //Facility Used
			BigDecimal.class, //Amount Due
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
