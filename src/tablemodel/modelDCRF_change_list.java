package tablemodel;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Lookup._JLookup;

public class modelDCRF_change_list extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8738810102880371204L;
	
	private boolean editable = false;
	boolean[] COLUMN_EDITABLE;
	
	
	public modelDCRF_change_list() {
		initThis();
	}

	public modelDCRF_change_list(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelDCRF_change_list(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelDCRF_change_list(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelDCRF_change_list(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelDCRF_change_list(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			"No.",				// 0
			"Module",			// 1
			"Document Name/No." ,// 2
			"Entity Name" ,		// 3
			"Company" ,			// 4
			"Project" ,			// 5
			"Unit", 			// 6
			"Change - From", 	// 7
			"Change - To", 		// 8
			"In-Charge"			// 9
			};

	
	/**
	 * String.class = Alignment.CENTER
	 * Object.class = Alignment.LEFT
	 * BigDecimal.class = Alignment.RIGHT
	 * Integer.class = Alignment.CENTER
	 * Timestamp.class
	 */
	public Class[] CLASS_TYPES = new Class[] {
			Integer.class, 		//No.
			_JLookup.class, 	//Module
			String.class, 		//Document Name/No.
			_JLookup.class, 	//Entity Name
			_JLookup.class,		//Company
			_JLookup.class,		//Project
			_JLookup.class,		//Unit
			String.class,		//Change - From
			String.class,		//Change - To
			String.class		//In-Charge
			
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
		COLUMN_EDITABLE = new boolean[] {
				false, 		//No.
				false, 		//Module
				false, 		//Document Name/No.
				false, 		//Entity Name
				false,		//Company
				false,		//Project
				false,		//Unit
				false,		//Change - From
				false,		//Change - To
				false		//In-Charge
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
					false, 		//No.
					false, 		//Module
					true, 		//Document Name/No.
					false, 		//Entity Name
					false,		//Company
					false,		//Project
					false,		//Unit
					true,		//Change - From
					true,		//Change - To
					false		//In-Charge
			};
		}else{
			COLUMN_EDITABLE = new boolean[] {
					false, 		//No.
					false, 		//Module
					false, 		//Document Name/No.
					false, 		//Entity Name
					false,		//Company
					false,		//Project
					false,		//Unit
					false,		//Change - From
					false,		//Change - To
					false		//In-Charge
			};
		}
	}

	
	
	
}
