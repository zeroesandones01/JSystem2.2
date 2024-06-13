package tablemodel;

import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class modelChartofAcctsMain extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8738810102880371204L;
	
	private boolean editable = false;
	boolean[] COLUMN_EDITABLE;
	
	
	public modelChartofAcctsMain() {
		initThis();
	}

	public modelChartofAcctsMain(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelChartofAcctsMain(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelChartofAcctsMain(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelChartofAcctsMain(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelChartofAcctsMain(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			"Account ID",		// 1
			"Account Name" ,	// 2
			"Account Type" ,	// 3
			"Balance" ,			// 4
			"Created by" ,		// 5
			"Date Created", 	// 6
			"Last Edited by", 	// 7
			"W/ Sub-Account", 	// 8
			"Status" ,			// 9
			"Level",			// 10
			"Filtered"			//11
			};

	
	/**
	 * String.class = Alignment.CENTER
	 * Object.class = Alignment.LEFT
	 * BigDecimal.class = Alignment.RIGHT
	 * Integer.class = Alignment.CENTER
	 * Timestamp.class
	 */
	public Class[] CLASS_TYPES = new Class[] {
			String.class, 		//Account ID
			Object.class, 		//Account Name
			String.class, 		//Account Type
			String.class, 		//Balance
			String.class,		//Created by
			Timestamp.class,	//Date Created
			String.class,		//Last Edited by
			Boolean.class,		//W/ Sub-Account
			String.class,		//Status
			String.class,		//Level
			Boolean.class
			
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
		COLUMN_EDITABLE = new boolean[] {
				false, 		//Account ID
				false, 		//Account Name
				false, 		//Account Type
				false, 		//Balance
				false,		//Created by
				false,		//Date Created
				false,		//Last Edited by
				false,		//W/ Sub-Account
				false,		//Status
				false,		//Level
				false
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
					false, 		//Account ID
					false, 		//Account Name
					false, 		//Account Type
					false, 		//Balance
					false,		//Created by
					false,		//Date Created
					false,		//Last Edited by
					false,		//W/ Sub-Account
					false,		//Status
					false,		//Level
					false		//Filtered
			};
		}else{
			COLUMN_EDITABLE = new boolean[] {
					false, 		//Account ID
					false, 		//Account Name
					false, 		//Account Type
					false, 		//Balance
					false,		//Created by
					false,		//Date Created
					false,		//Last Edited by
					false,		//W/ Sub-Account
					false,		//Status
					false,		//Level
					false		//Filtered
			};
		}
	}

	
	
	
}
