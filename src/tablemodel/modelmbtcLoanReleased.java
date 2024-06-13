package tablemodel;

import java.util.Date;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelmbtcLoanReleased extends DefaultTableModel {

	private static final long serialVersionUID = -7129289200394320665L;
	private boolean editable = false;
	boolean[] COLUMNS_EDITABLE;	

	public modelmbtcLoanReleased() {
		InitGUI();
	}

	public modelmbtcLoanReleased(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		
	}

	public modelmbtcLoanReleased(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		
	}

	public modelmbtcLoanReleased(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		
	}

	public modelmbtcLoanReleased(Vector data, Vector columnNames) {
		super(data, columnNames);
		
	}

	public modelmbtcLoanReleased(Object[][] data, Object[] columnNames) {
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
			false
		};
	}
	
	public String [] COLUMNS = new String[] {
		"Name",						
		"Tag",						
		"Project",					
		"Unit",						
		"Account #",				
		"Post Approval",
		"Loan Released"
	};
	
	public Class [] CLASS_TYPES = new Class[] {
		String.class,
		Boolean.class,
		String.class,
		String.class,
		String.class,
		Date.class,
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
