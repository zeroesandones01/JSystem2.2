package tablemodel;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class modelClientList extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8738810102880371204L;
	
	private boolean editable = false;
	boolean[] COLUMN_EDITABLE;
	
	
	public modelClientList() {
		initThis();
	}

	public modelClientList(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelClientList(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelClientList(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelClientList(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelClientList(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			"Tag",				// 1
			"Project" ,			// 2
			"Description" ,		// 3
			"Client Name" ,		// 4
			"Client ID", 		// 5
			"PBL ID", 			// 6
			"Seq ID", 			// 7
			"Status", 			// 8
			"Proj ID" 			// 9
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
			String.class, 		//Description
			String.class,		//Client Name
			String.class,		//Client ID
			String.class,		//PBL ID
			String.class,		//Seq ID
			String.class,		//Status
			String.class		//Proj ID
			
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
		COLUMN_EDITABLE = new boolean[] {
				false, 		//Tag
				false, 		//Project
				false, 		//Description
				false,		//Client Name
				false,		//Client ID
				false,		//PBL ID
				false,		//Seq ID
				false,		//Status
				false		//Proj ID
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
					false, 		//Description
					false,		//Client Name
					false,		//Client ID
					false,		//PBL ID
					false,		//Seq ID
					false,		//Status
					false		//Proj ID
			};
		}else{
			COLUMN_EDITABLE = new boolean[] {
					false, 		//Tag
					false, 		//Project
					false, 		//Description
					false,		//Client Name
					false,		//Client ID
					false,		//PBL ID
					false,		//Seq ID
					false,		//Status
					false		//Proj ID
			};
		}
	}

	
	
	
}
