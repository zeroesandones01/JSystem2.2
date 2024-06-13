package tablemodel;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelCARD_SubdivisionDues extends DefaultTableModel {

	private static final long serialVersionUID = 1993663481057667787L;
	
	private boolean editable = false;
	boolean[] COLUMNS_EDITABLE;	
	
	public modelCARD_SubdivisionDues() {
		InitGUI();
	}

	public modelCARD_SubdivisionDues(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		InitGUI();
	}

	public modelCARD_SubdivisionDues(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		InitGUI();
	}

	public modelCARD_SubdivisionDues(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		InitGUI();
	}

	public modelCARD_SubdivisionDues(Vector data, Vector columnNames) {
		super(data, columnNames);
		InitGUI();
	}

	public modelCARD_SubdivisionDues(Object[][] data, Object[] columnNames) {
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
		"Due for", 
		"Amount Due", 
		"Amount Paid", 
		"Date Paid"
	};
	
	public Class [] CLASS_TYPES = new Class[] { 
			
		Date.class, 
		BigDecimal.class, 
		BigDecimal.class, 
		Date.class
		
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
