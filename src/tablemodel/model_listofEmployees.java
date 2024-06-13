package tablemodel;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class model_listofEmployees extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8738810102880371204L;
	
	private boolean editable = false;
	boolean[] COLUMN_EDITABLE;
	
	
	public model_listofEmployees() {
		initThis();
	}

	public model_listofEmployees(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public model_listofEmployees(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public model_listofEmployees(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public model_listofEmployees(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public model_listofEmployees(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			"Emp. Code",		// 1
			"Emp Name" ,		// 2
			"Dept. Code" ,		// 3
			"Div. Code" ,		// 4
			"Status" 			// 5
			};

	
	/**
	 * String.class = Alignment.CENTER
	 * Object.class = Alignment.LEFT
	 * BigDecimal.class = Alignment.RIGHT
	 * Integer.class = Alignment.CENTER
	 * Timestamp.class
	 */
	public Class[] CLASS_TYPES = new Class[] {
			String.class, 		//Emp. Code
			Object.class, 		//Emp Name
			String.class, 		//Dept. Code
			String.class, 		//Div. Code
			String.class		//Status
			
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
		COLUMN_EDITABLE = new boolean[] {
				false, 		//Emp. Code
				false, 		//Emp Name
				false, 		//Dept. Code
				false, 		//Div. Code
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
					false, 		//Emp. Code
					false, 		//Emp Name
					false, 		//Dept. Code
					false, 		//Div. Code
					false		//Status
			};
		}else{
			COLUMN_EDITABLE = new boolean[] {
					false, 		//Emp. Code
					false, 		//Emp Name
					false, 		//Dept. Code
					false, 		//Div. Code
					false		//Status
			};
		}
	}

	
	
	
}
