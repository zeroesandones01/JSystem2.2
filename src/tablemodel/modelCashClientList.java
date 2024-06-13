package tablemodel;

import java.math.BigDecimal;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class modelCashClientList extends DefaultTableModel {

	private static final long serialVersionUID = -4365076050876747978L;
	private boolean editable = false;
	boolean[] COLUMN_EDITABLE;

	public modelCashClientList() {
		initThis();
	}

	public modelCashClientList(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		// TODO Auto-generated constructor stub
	}

	public modelCashClientList(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		// TODO Auto-generated constructor stub
	}

	public modelCashClientList(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		// TODO Auto-generated constructor stub
	}

	public modelCashClientList(Vector data, Vector columnNames) {
		super(data, columnNames);
		// TODO Auto-generated constructor stub
	}

	public modelCashClientList(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		// TODO Auto-generated constructor stub
	}
	
	public String[] COLUMNS = new String[] {
			"Select",		// 1
			"Client",	// 2
			"Proj Alias",			// 3
			"Unit",
			"Amount",
			"Particular", 
			"Pay Rec ID"
			};

	
	/**
	 * String.class = Alignment.CENTER
	 * Object.class = Alignment.LEFT
	 * BigDecimal.class = Alignment.RIGHT
	 * Integer.class = Alignment.CENTER
	 * Timestamp.class
	 */
	public Class[] CLASS_TYPES = new Class[] {
			Boolean.class, 	//Select
			String.class, 	//Client
			String.class,	//Proj Alias
			String.class,	//Unit	
			BigDecimal.class, //Amount
			String.class,	//Particular
			Integer.class //Pay Rec ID
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
		COLUMN_EDITABLE = new boolean[] {
				false, 		//Select
				false, 		//Client
				false,		//Proj Alias
				false, 		//Unit	
				false,		//Amount
				false, 		//Particular
				false		//Pay Rec ID
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
					true, 		//Select
					false, 		//Client
					false,		//Proj Alias
					false, 		//Unit	
					false,		//Amount
					false, 		//Particular
					false
			};
		}else{
			COLUMN_EDITABLE = new boolean[] {
					true, 		//Select
					false, 		//Client
					false,		//Proj Alias
					false, 		//Unit	
					false,		//Amount
					false, 		//Particular
					false
			};
		}
	}

}
