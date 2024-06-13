package tablemodel;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class modelATM_process extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8738810102880371204L;
	
	private boolean editable = false;
	boolean[] COLUMN_EDITABLE;
	
	
	public modelATM_process() {
		initThis();
	}

	public modelATM_process(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelATM_process(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelATM_process(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelATM_process(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelATM_process(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			"Tag" ,			// 1
			"Agent ID" ,	// 2
			"Agent Name", 	// 3
			"ATM No.", 		// 4
			"Sales Div" ,	// 5
			"Sales Group", 	// 6
			"Position", 	// 7
			
			};

	
	/**
	 * String.class = Alignment.CENTER
	 * Object.class = Alignment.LEFT
	 * BigDecimal.class = Alignment.RIGHT
	 * Integer.class = Alignment.CENTER
	 * Timestamp.class
	 */
	public Class[] CLASS_TYPES = new Class[] {			
			Boolean.class, 		//Tag
			String.class, 		//Agent ID
			Object.class,		//Agent Name
			String.class,		//ATM No.
			String.class,		//Sales Div
			String.class,		//Sales Group		
			String.class		//Position	
			
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
		COLUMN_EDITABLE = new boolean[] {
				true, 		//Tag
				false, 		//Agent ID
				false,		//Agent Name
				true,		//ATM No.	
				false,		//Sales Div
				false,		//Sales Group		
				false		//Position	
				
		};
	}

	public Class getColumnClass(int columnIndex) {
		return CLASS_TYPES[columnIndex];
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return COLUMN_EDITABLE[columnIndex];
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
		if(editable){
			COLUMN_EDITABLE = new boolean[] {
					true, 		//Tag
					false, 		//Agent ID
					false,		//Agent Name
					true,		//ATM No.	
					false,		//Sales Div
					false,		//Sales Group		
					false		//Position	
					
			};
		}else{
			COLUMN_EDITABLE = new boolean[] {
					false, 		//Tag
					false, 		//Agent ID
					false,		//Agent Name
					false,		//ATM No.	
					false,		//Sales Div
					false,		//Sales Group		
					false		//Position	
					
			};
		}
	}

	
	
	
}
