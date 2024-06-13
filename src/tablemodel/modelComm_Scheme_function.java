package tablemodel;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class modelComm_Scheme_function extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8738810102880371204L;
	
	private boolean editable = false;
	boolean[] COLUMN_EDITABLE;
	
	
	public modelComm_Scheme_function() {
		initThis();
	}

	public modelComm_Scheme_function(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelComm_Scheme_function(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelComm_Scheme_function(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelComm_Scheme_function(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelComm_Scheme_function(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			"Function ID",		// 1
			"Function Description", // 2
			"Database Function", 	// 3
			"Status" 			// 4
			};

	
	/**
	 * String.class = Alignment.CENTER
	 * Object.class = Alignment.LEFT
	 * BigDecimal.class = Alignment.RIGHT
	 * Integer.class = Alignment.CENTER
	 * Timestamp.class
	 */
	public Class[] CLASS_TYPES = new Class[] {
			String.class, 		//Condition ID
			Object.class, 		//Condition Description
			Object.class,		//Database Function
			String.class		//Status
			
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
		COLUMN_EDITABLE = new boolean[] {
				false, 		//Condition ID
				false, 		//Condition Description
				false, 		//Database Function
				false		//Status
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
					false, 		//Condition ID
					false, 		//Condition Description
					false, 		//Database Function
					false		//Status
			};
		}else{
			COLUMN_EDITABLE = new boolean[] {
					false, 		//Condition ID
					false, 		//Condition Description
					false, 		//Database Function
					false		//Status
			};
		}
	}

	
	
	
}
