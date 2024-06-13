package tablemodel;

import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class modelSalesAgentAccreditationHistory extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8738810102880371204L;
	
	private boolean editable = false;
	boolean[] COLUMN_EDITABLE;
	
	
	public modelSalesAgentAccreditationHistory() {
		initThis();
	}

	public modelSalesAgentAccreditationHistory(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelSalesAgentAccreditationHistory(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelSalesAgentAccreditationHistory(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelSalesAgentAccreditationHistory(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelSalesAgentAccreditationHistory(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			"Division" ,	// 1
			"Department" ,	// 2
			"Position" ,	// 3		
			"Type" ,		// 4	
			"Accredit From",// 5
			"Accredit To" ,	// 6
			"Override", 	// 7
			"Inactive by", 	// 8
			"Inactive Date" // 9
			};

	
	/**
	 * String.class = Alignment.CENTER
	 * Object.class = Alignment.LEFT
	 * BigDecimal.class = Alignment.RIGHT
	 * Integer.class = Alignment.CENTER
	 * Timestamp.class
	 */
	public Class[] CLASS_TYPES = new Class[] {
			String.class, 		//Division
			String.class, 		//Department
			String.class, 		//Position
			String.class, 		//Type			
			Timestamp.class,	//Accredit From			
			Timestamp.class,	//Accredit To
			String.class,		//Override
			String.class, 		//Inactive by
			Timestamp.class 	//Inactive Date		
			
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
		COLUMN_EDITABLE = new boolean[] {
				false, 		//Division
				false, 		//Department
				false, 		//Position
				false, 		//Type			
				false,		//Accredit From			
				false,		//Accredit To
				false,		//Override
				false, 		//Inactive by
				false 		//Inactive Date		
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
					false, 		//Division
					false, 		//Department
					false, 		//Position
					false, 		//Type			
					false,		//Accredit From			
					false,		//Accredit To
					false,		//Override
					false, 		//Inactive by
					false 		//Inactive Date		
			};
		}else{
			COLUMN_EDITABLE = new boolean[] {
					false, 		//Agent ID
					false, 		//Agent Name
					false, 		//Division
					false, 		//Department
					false, 		//Position
					false, 		//Type			
					false,		//Accredit From			
					false,		//Accredit To
					false,		//Override
					false, 		//Inactive by
					false 		//Inactive Date		
			};
		}
	}

	
	
	
}
