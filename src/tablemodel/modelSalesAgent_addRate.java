package tablemodel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Lookup._JLookup;

public class modelSalesAgent_addRate extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8738810102880371204L;
	
	private boolean editable = false;
	boolean[] COLUMN_EDITABLE;
	
	
	public modelSalesAgent_addRate() {
		initThis();
	}

	public modelSalesAgent_addRate(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelSalesAgent_addRate(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelSalesAgent_addRate(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelSalesAgent_addRate(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelSalesAgent_addRate(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			"Tag",					// 1
			"House Model ID" ,		// 2
			"House Model Name" ,	// 3
			"Rate" ,				// 4
			"Effectivity" ,			// 5
			"Status"     			// 6
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
			String.class, 		//Project
			String.class, 		//House Model
			BigDecimal.class, 	//Rate
			Timestamp.class ,	//Effectivity 
			_JLookup.class		//Status
			
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
		COLUMN_EDITABLE = new boolean[] {
				false, 		//Tag
				false, 		//Project
				false, 		//House Model
				false, 		//Rate
				false,		//Effectivity 
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
					true, 		//Tag
					false, 		//Project
					false, 		//House Model
					true, 		//Rate
					true,		//Effectivity 
					false		//Status
			};
		}else{
			COLUMN_EDITABLE = new boolean[] {
					false, 		//Tag
					false, 		//Project
					false, 		//House Model
					false, 		//Rate
					false,		//Effectivity 
					false		//Status
			};
		}
	}

	
	
	
}
