package tablemodel;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class model_BC_table	 extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8738810102880371204L;
	
	private boolean editable = false;
	boolean[] COLUMN_EDITABLE;
	
	
	public model_BC_table() {
		initThis();
	}

	public model_BC_table(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public model_BC_table(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public model_BC_table(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public model_BC_table(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public model_BC_table(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {			
			"RPLF No" ,		// 0
			"RPLF Date", 	// 1
			"Remarks", 		// 2
			"Status", 		// 3
			"Date Paid" 	// 4
			};

	
	/**
	 * String.class = Alignment.CENTER
	 * Object.class = Alignment.LEFT
	 * BigDecimal.class = Alignment.RIGHT
	 * Integer.class = Alignment.CENTER
	 * Timestamp.class
	 */
	public Class[] CLASS_TYPES = new Class[] {
			String.class, 	//RPLF No
			Timestamp.class,//RPLF Date
			String.class,	//Particular
			String.class, 	//Status
			Timestamp.class //Date Paid
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
		COLUMN_EDITABLE = new boolean[] {
				false, 	//RPLF No
				false,	//RPLF Date
				false,	//Particular
				false, 	//Status	
				false   //Date Paid
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
					false, 	//RPLF No
					false,	//RPLF Date
					false,	//Particular
					false, 	//Status	
					false   //Date Paid
			};
		}else{
			COLUMN_EDITABLE = new boolean[] {
					false, 	//RPLF No
					false,	//RPLF Date
					false,	//Particular
					false, 	//Status	
					false   //Date Paid			
			};
		}
	}

	
	
	
}
