package tablemodel;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class modelComm_addAgent_list	 extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8738810102880371204L;
	
	private boolean editable = false;
	boolean[] COLUMN_EDITABLE;
	
	
	public modelComm_addAgent_list() {
		initThis();
	}

	public modelComm_addAgent_list(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelComm_addAgent_list(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelComm_addAgent_list(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelComm_addAgent_list(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelComm_addAgent_list(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			"Agent ID" ,		// 1
			"Agent Name", 		// 2
			"Sales Div.", 		// 3
			"Sales Group", 		// 4
			"Position" ,		// 5
			"Type" ,			// 6			
			"Status"			// 7
			};

	
	/**
	 * String.class = Alignment.CENTER
	 * Object.class = Alignment.LEFT
	 * BigDecimal.class = Alignment.RIGHT
	 * Integer.class = Alignment.CENTER
	 * Timestamp.class
	 */
	public Class[] CLASS_TYPES = new Class[] {
			String.class, 		//Agent ID
			Object.class,		//Agent Name
			String.class, 		//Sales Div.
			String.class, 		//Sales Group
			String.class, 		//Position
			String.class,		//Type
			String.class 		//Status
			
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
		COLUMN_EDITABLE = new boolean[] {
				false, 		//Agent ID
				false,		//Agent Name
				false, 		//Sales Div.
				false, 		//Sales Group
				false, 		//Position
				false,		//Type
				false 		//Status
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
					false, 		//Agent ID
					false,		//Agent Name
					false, 		//Sales Div.
					false, 		//Sales Group
					false, 		//Position
					false,		//Type
					false 		//Status
			};
		}else{
			COLUMN_EDITABLE = new boolean[] {
					false, 		//Agent ID
					false,		//Agent Name
					false, 		//Sales Div.
					false, 		//Sales Group
					false, 		//Position
					false,		//Type
					false 		//Status
			};
		}
	}

	
	
	
}
