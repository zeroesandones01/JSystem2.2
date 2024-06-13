package tablemodel;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class model_Comm_ViewClientRequest	 extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8738810102880371204L;
	
	private boolean editable = false;
	boolean[] COLUMN_EDITABLE;
	
	
	public model_Comm_ViewClientRequest() {
		initThis();
	}

	public model_Comm_ViewClientRequest(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public model_Comm_ViewClientRequest(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public model_Comm_ViewClientRequest(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public model_Comm_ViewClientRequest(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public model_Comm_ViewClientRequest(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {			
			"Status ID" ,	// 0
			"Status Desc.", // 1
			"Trans. Date", 	// 2
			"Actual Date", 	// 3
			"Request No.", 	// 4
			"Status ID" 	// 5
			};

	
	/**
	 * String.class = Alignment.CENTER
	 * Object.class = Alignment.LEFT
	 * BigDecimal.class = Alignment.RIGHT
	 * Integer.class = Alignment.CENTER
	 * Timestamp.class
	 */
	public Class[] CLASS_TYPES = new Class[] {
			String.class, 	//Status ID
			String.class,	//Status Desc.
			Timestamp.class,//Trans. Date
			Timestamp.class,//Actual Date
			String.class,	//Request No.
			String.class 	//Status ID
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
		COLUMN_EDITABLE = new boolean[] {
				false, 	//Status ID
				false,	//Status Desc.
				false,	//Trans. Date
				false,	//Actual Date
				false,	//Request No.
				false 	//Status ID			
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
					false, 	//Status ID
					false,	//Status Desc.
					false,	//Trans. Date
					false,	//Actual Date
					false,	//Request No.
					false 	//Status ID		
			};
		}else{
			COLUMN_EDITABLE = new boolean[] {
					false, 	//Status ID
					false,	//Status Desc.
					false,	//Trans. Date
					false,	//Actual Date
					false,	//Request No.
					false 	//Status ID				
			};
		}
	}

	
	
	
}
