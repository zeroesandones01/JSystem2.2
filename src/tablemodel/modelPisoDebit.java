package tablemodel;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelPisoDebit extends DefaultTableModel {

	private static final long serialVersionUID = 6943208951212336241L;
	private boolean editable = false;
	boolean[] COLUMNS_EDITABLE;	
	
	public modelPisoDebit() {
		InitGUI();
	}

	public modelPisoDebit(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		
	}

	public modelPisoDebit(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		
	}

	public modelPisoDebit(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		
	}

	public modelPisoDebit(Vector data, Vector columnNames) {
		super(data, columnNames);
		
	}

	public modelPisoDebit(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		
	}

	private void InitGUI() {
		setColumnIdentifiers(COLUMNS);
		
		COLUMNS_EDITABLE = new boolean[] {
			false,
			true,
			false,
			false,
			false,
			false,
			true
		};
	}
	
	public String [] COLUMNS = new String[] {
		"Name",						//		0
		"Tag",						//		1
		"Project",					//		2
		"Unit",						//		3
		"Account #",				//		4
		"Activated",			//		5
		"Debit Amt",				//		6
	};
	
	public Class [] CLASS_TYPES = new Class[] {
		String.class,
		Boolean.class,
		String.class,
		String.class,
		String.class,
		Date.class,
		BigDecimal.class,
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
