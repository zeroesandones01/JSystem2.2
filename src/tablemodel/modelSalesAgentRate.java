package tablemodel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Lookup._JLookup;

public class modelSalesAgentRate extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8738810102880371204L;
	
	private boolean editable = false;
	boolean[] COLUMN_EDITABLE;
	
	
	public modelSalesAgentRate() {
		initThis();
	}

	public modelSalesAgentRate(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelSalesAgentRate(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelSalesAgentRate(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelSalesAgentRate(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelSalesAgentRate(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			"Record ID",	// 1
			"Project",		// 2
			"Phase" ,		// 3
			"House Model" ,	// 4
			"Rate" ,		// 5		
			"Effect. Date" ,// 6	
			"Status" ,		// 7
			"Created By", 	// 8
			"Created Date" 	// 9
			};

	
	/**
	 * String.class = Alignment.CENTER
	 * Object.class = Alignment.LEFT
	 * BigDecimal.class = Alignment.RIGHT
	 * Integer.class = Alignment.CENTER
	 * Timestamp.class
	 */
	public Class[] CLASS_TYPES = new Class[] {
			String.class, 		//Record ID
			String.class, 		//Project
			String.class, 		//Phase
			String.class, 		//House Model
			BigDecimal.class, 	//Rate
			Timestamp.class,	//Effectivity Date
			_JLookup.class,		//Status			
			String.class,		//Created By
			Timestamp.class		//Created Date
			
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
		COLUMN_EDITABLE = new boolean[] {
				false, 		//Record ID
				false, 		//Project
				false, 		//Phase
				false, 		//House Model
				false, 		//Rate
				false, 		//Effectivity Date
				false,		//Status				
				false,		//Created By
				false		//Created Date
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
					false, 		//Record ID
					false, 		//Project
					false, 		//Phase
					false, 		//House Model
					true, 		//Rate
					true, 		//Effectivity Date			
					false,		//Status							
					false,		//Created By
					false		//Created Date
			};
		}else{
			COLUMN_EDITABLE = new boolean[] {
					false, 		//Record ID
					false, 		//Project
					false, 		//Phase
					false, 		//House Model
					false, 		//Rate
					false, 		//Effectivity Date
					false,		//Status
					false,		//Created By
					false		//Created Date
			};
		}
	}

	
	
	
}
