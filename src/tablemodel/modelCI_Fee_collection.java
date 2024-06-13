package tablemodel;

import java.math.BigDecimal;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class modelCI_Fee_collection extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8738810102880371204L;
	
	private boolean editable = false;
	boolean[] COLUMN_EDITABLE;
	
	
	public modelCI_Fee_collection() {
		initThis();
	}

	public modelCI_Fee_collection(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelCI_Fee_collection(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelCI_Fee_collection(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelCI_Fee_collection(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelCI_Fee_collection(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			"Tag",				// 0
			"Client ID",		// 1
			"Client Name",		// 2
			"PBL",				// 3
			"Amount",			// 4
			"Date Collected",	// 5
			"Rec ID",			// 6
			"Project",			// 7
			"Phase",			// 8
			"AR No.",			// 9
			
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
			String.class, 		//Entity ID
			Object.class, 		//Client Name
			String.class,		//PBL
			BigDecimal.class,	//Amount
			String.class,		//Date Collected
			String.class,		//Rec ID
			String.class,		//Project
			String.class,		//Phase
			String.class		//AR No.
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
		COLUMN_EDITABLE = new boolean[] {
				true, 		//Tag
				false, 		//Entity ID
				false, 		//Client Name
				false,		//PBL
				false,		//Amount
				false,		//Date Collected
				false,		//Rec ID
				false,		//Project
				false,		//Phase
				false		//AR No.
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
					false, 		//Entity ID
					false, 		//Client Name
					false,		//PBL
					false,		//Amount
					false,		//Date Collected
					false,		//Rec ID
					false,		//Project
					false,		//Phase
					false		//AR No.
			};
		}else{
			COLUMN_EDITABLE = new boolean[] {
					true, 		//Tag
					false, 		//Entity ID
					false, 		//Client Name
					false,		//PBL
					false,		//Amount
					false,		//Date Collected
					false,		//Rec ID
					false,		//Project
					false,		//Phase
					false		//AR No.
			};
		}
	}

	
	
	
}
