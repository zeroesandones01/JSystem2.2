package tablemodel;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelMBTC_pisoDebit extends DefaultTableModel {

	private static final long serialVersionUID = 2121122352690600746L;
	private boolean editable = false;
	boolean[] COLUMNS_EDITABLE;

	public modelMBTC_pisoDebit() {
		InitGUI();
	}

	public modelMBTC_pisoDebit(int rowCount, int columnCount) {
		super(rowCount, columnCount);

	}

	public modelMBTC_pisoDebit(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);

	}

	public modelMBTC_pisoDebit(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);

	}

	public modelMBTC_pisoDebit(Vector data, Vector columnNames) {
		super(data, columnNames);

	}

	public modelMBTC_pisoDebit(Object[][] data, Object[] columnNames) {
		super(data, columnNames);

	}
	
	private void InitGUI() {
		setColumnIdentifiers(COLUMNS);
		
		COLUMNS_EDITABLE = new boolean[] {
				true, 
				false,
				
				false,				
				false,
				false,
				false,
				
				false,
				false,
				true,
				
				false,
				false,
				false,
				false
			};
	}
	
	public String [] COLUMNS = new String[] {
			"Tag", 
			"Name", 					//	1
			
			"Project", 
			"Phase", 					//	3
			"Block", 					//	4
			"Lot", 						//	5
			
			"Loan Rlsd", 
			"Account No.", 				//	7
			"Amount", 
			
			"entity_id", 	
			"proj_id", 
			"pbl_id", 
			"seq_no"
		};
		
	public Class [] CLASS_TYPES = new Class[] {
		Boolean.class,
		Object.class, 
		
		String.class, 
		String.class, 
		String.class, 
		String.class, 
		
		Date.class, 
		String.class,  
		BigDecimal.class, 
		
		String.class, 
		String.class, 
		String.class, 
		String.class
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
