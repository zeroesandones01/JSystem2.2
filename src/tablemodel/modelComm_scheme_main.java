package tablemodel;

import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class modelComm_scheme_main extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8738810102880371204L;
	
	private boolean editable = false;
	boolean[] COLUMN_EDITABLE;
	
	
	public modelComm_scheme_main() {
		initThis();
	}

	public modelComm_scheme_main(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelComm_scheme_main(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelComm_scheme_main(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelComm_scheme_main(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelComm_scheme_main(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			"Scheme ID",		// 1
			"Scheme Description" ,	// 2
			"Agent Type" ,		// 3
			"Buyer Type" ,		// 4
			"Terms" ,			// 5
			"Scheme Type", 		// 6
			"Status", 			// 7
			"Created by", 		// 8
			"Created Date" ,	// 9
			"Edited by", 		// 10
			"Edited Date"		// 11
			};

	
	/**
	 * String.class = Alignment.CENTER
	 * Object.class = Alignment.LEFT
	 * BigDecimal.class = Alignment.RIGHT
	 * Integer.class = Alignment.CENTER
	 * Timestamp.class
	 */
	public Class[] CLASS_TYPES = new Class[] {
			String.class, 		//Scheme ID
			Object.class, 		//Scheme Description
			String.class, 		//Agent Type
			String.class, 		//Buyer Type
			String.class,		//Terms
			String.class,		//Scheme Type
			String.class,		//Status
			String.class,		//Created by
			Timestamp.class,	//Created Date
			String.class,		//Edited by
			Timestamp.class		//Edited Date
			
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
		COLUMN_EDITABLE = new boolean[] {
				false, 		//Scheme ID
				false, 		//Scheme Description
				false, 		//Agent Type
				false, 		//Buyer Type
				false,		//Terms
				false,		//Scheme Type
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
					false, 		//Scheme ID
					false, 		//Scheme Description
					false, 		//Agent Type
					false, 		//Buyer Type
					false,		//Terms
					false,		//Scheme Type
					false,		//Status
					false,		//Created by
					false,		//Created Date
					false,		//Edited by
					false		//Edited Date
			};
		}else{
			COLUMN_EDITABLE = new boolean[] {
					false, 		//Scheme ID
					false, 		//Scheme Description
					false, 		//Agent Type
					false, 		//Buyer Type
					false,		//Terms
					false,		//Scheme Type
					false,		//Status
					false,		//Created by
					false,		//Created Date
					false,		//Edited by
					false		//Edited Date
			};
		}
	}

	
	
	
}
