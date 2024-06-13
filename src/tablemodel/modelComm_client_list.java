package tablemodel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class modelComm_client_list	 extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8738810102880371204L;
	
	private boolean editable = false;
	boolean[] COLUMN_EDITABLE;
	
	
	public modelComm_client_list() {
		initThis();
	}

	public modelComm_client_list(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelComm_client_list(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelComm_client_list(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelComm_client_list(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelComm_client_list(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			"Client ID" ,	// 1
			"Name" ,		// 2
			"Buyer Type" ,	// 3
			"Proj" ,		// 4
			"Unit" ,		// 5
			"Net Selling Price" ,	// 6
			"Reser. Date" ,	// 7
			"House Model", 	// 8
			"PBL", 			// 9
			"Seq No", 		// 10
			"Comm. Scheme", 	// 11
			"Status", //12
			"Agent Code", //13
			"Proj. ID" //14
			};

	
	/**
	 * String.class = Alignment.CENTER
	 * Object.class = Alignment.LEFT
	 * BigDecimal.class = Alignment.RIGHT
	 * Integer.class = Alignment.CENTER
	 * Timestamp.class
	 */
	public Class[] CLASS_TYPES = new Class[] {
			String.class, 		//Client ID
			Object.class,		//Name
			String.class, 		//Buyer Type
			String.class, 		//Proj
			String.class,		//Unit
			BigDecimal.class, 	//Net Selling Price	
			Timestamp.class, 	//Reser. Date
			String.class,		//House Model
			String.class,		//PBL
			String.class,		//Seq No
			String.class,		//Comm. Scheme
			String.class,		//Status
			String.class,		//Agent Code
			String.class		//Proj ID
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
		COLUMN_EDITABLE = new boolean[] {
				false, 		//Client ID
				false,		//Name
				false, 		//Buyer Type
				false, 		//Proj
				false,		//Unit
				false, 		//Net Selling Price	
				false, 		//Reser. Date
				false,		//House Model
				false,		//PBL
				false,		//Seq No
				false,		//Comm. Scheme
				false,		//PBL
				false,		//Seq No
				false		//Comm. Scheme
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
					false, 		//Client ID
					false,		//Name
					false, 		//Buyer Type
					false, 		//Proj
					false,		//Unit
					false, 		//Net Selling Price	
					false, 		//Reser. Date
					false,		//House Model
					false,		//PBL
					false,		//Seq No
					false		//Comm. Scheme
			};
		}else{
			COLUMN_EDITABLE = new boolean[] {
					false, 		//Client ID
					false,		//Name
					false, 		//Buyer Type
					false, 		//Proj
					false,		//Unit
					false, 		//Net Selling Price	
					false, 		//Reser. Date
					false,		//House Model
					false,		//PBL
					false,		//Seq No
					false		//Comm. Scheme
			};
		}
	}

	
	
	
}
