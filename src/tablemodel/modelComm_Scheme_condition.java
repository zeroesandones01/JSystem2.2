package tablemodel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class modelComm_Scheme_condition extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8738810102880371204L;
	
	private boolean editable = false;
	boolean[] COLUMN_EDITABLE;
	
	
	public modelComm_Scheme_condition() {
		initThis();
	}

	public modelComm_Scheme_condition(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelComm_Scheme_condition(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelComm_Scheme_condition(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelComm_Scheme_condition(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelComm_Scheme_condition(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			"Condition ID",		// 1
			"Condition Description" ,	// 2
			"Comm Type" ,		// 3
			"Comm Amount" ,		// 4
			"Comm Perc." ,		// 5
			"Override Amt." ,	// 6
			"Override Perc." ,	// 7
			"Days from OR" ,	// 8
			"Status" ,			// 9
			"Created by", 		// 10
			"Created Date" ,	// 11
			"Edited by", 		// 12
			"Edited Date"		// 13
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
			String.class, 		//Comm Type			
			BigDecimal.class, 	//Comm Amount
			BigDecimal.class, 	//Comm Perc.
			BigDecimal.class, 	//Override Amt.
			BigDecimal.class, 	//Override Perc.
			String.class, 		//Days from OR			
			String.class,		//Status
			String.class,		//Created by
			Timestamp.class,	//Created Date
			String.class,		//Edited by
			Timestamp.class		//Edited Date
			
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
		COLUMN_EDITABLE = new boolean[] {
				false, 		//Condition ID
				false, 		//Condition Description
				false, 		//Comm Type
				false, 		//Comm Amount
				false, 		//Comm Perc.
				false, 		//Override Amt.
				false, 		//Override Perc.
				false, 		//Days from OR			
				false,		//Status
				false,		//Created by
				false,		//Created Date
				false,		//Edited by
				false		//Edited Date
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
					false, 		//Comm Type
					false, 		//Comm Amount
					false, 		//Comm Perc.
					false, 		//Override Amt.
					false, 		//Override Perc.
					false, 		//Days from OR		
					false,		//Status
					false,		//Created by
					false,		//Created Date
					false,		//Edited by
					false		//Edited Date
			};
		}else{
			COLUMN_EDITABLE = new boolean[] {
					false, 		//Condition ID
					false, 		//Condition Description
					false, 		//Comm Type
					false, 		//Comm Amount
					false, 		//Comm Perc.
					false, 		//Override Amt.
					false, 		//Override Perc.
					false, 		//Days from OR		
					false,		//Status
					false,		//Created by
					false,		//Created Date
					false,		//Edited by
					false		//Edited Date
			};
		}
	}

	
	
	
}
