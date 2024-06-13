package tablemodel;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Lookup._JLookup;

public class model_FAD_process extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8738810102880371204L;
	
	private boolean editable = false;
	boolean[] COLUMN_EDITABLE;
	
	
	public model_FAD_process() {
		initThis();
	}

	public model_FAD_process(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public model_FAD_process(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public model_FAD_process(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public model_FAD_process(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public model_FAD_process(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			"Process No.",	// 1
			"Process Name", // 2
			"Can Add New", 	// 3
			"Can Approve", 	// 4
			"Can Delete",	// 5
			"Can Preview", 	// 6
			"Can Untag" 	// 7
			};

	
	/**
	 * String.class = Alignment.CENTER
	 * Object.class = Alignment.LEFT
	 * BigDecimal.class = Alignment.RIGHT
	 * Integer.class = Alignment.CENTER
	 * Timestamp.class
	 */
	public Class[] CLASS_TYPES = new Class[] {
			_JLookup.class, 	//Process No.
			Object.class, 		//Process Name
			Boolean.class, 		//Can Add New
			Boolean.class, 		//Can Approve
			Boolean.class, 		//Can Delete
			Boolean.class, 		//Can Preview
			Boolean.class 		//Can Untag
			
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
		COLUMN_EDITABLE = new boolean[] {
				false, 		//Process No.
				false,		//Process Name
				false, 		//Can Add New
				false, 		//Can Approve
				false, 		//Can Delete
				false, 		//Can Preview
				false 		//Can Untag
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
					false, 		//Process No.
					false,		//Process Name
					true, 		//Can Add New
					true, 		//Can Approve
					true, 		//Can Delete
					true, 		//Can Preview
					true 		//Can Untag
			};
		}else{
			COLUMN_EDITABLE = new boolean[] {
					false, 		//Process No.
					false,		//Process Name
					false, 		//Can Add New
					false, 		//Can Approve
					false, 		//Can Delete
					false, 		//Can Preview
					false 		//Can Untag
			};
		}
	}

	
	
	
}
