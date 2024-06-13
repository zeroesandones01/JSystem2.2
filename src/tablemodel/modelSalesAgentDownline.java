package tablemodel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class modelSalesAgentDownline extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8738810102880371204L;
	
	private boolean editable = false;
	boolean[] COLUMN_EDITABLE;
	
	
	public modelSalesAgentDownline() {
		initThis();
	}

	public modelSalesAgentDownline(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelSalesAgentDownline(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelSalesAgentDownline(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelSalesAgentDownline(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelSalesAgentDownline(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			"Agent ID",		// 1
			"Agent Name",	// 2
			"Division" ,	// 3
			"Department" ,	// 4
			"Position" ,	// 5		
			"Type" ,		// 6	
			"Rate" ,		// 7	
			"Accredit From",// 8
			"Accredit To" ,	// 9
			"ATM on Hand" 	// 10
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
			Object.class, 		//Agent Name
			String.class, 		//Division
			String.class, 		//Department
			String.class, 		//Position
			String.class, 		//Type			
			BigDecimal.class,	//Rate			
			Timestamp.class,	//Accredit From			
			Timestamp.class,	//Accredit To
			String.class		//ATM on Hand
			
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
		COLUMN_EDITABLE = new boolean[] {
				false, 		//Agent ID
				false, 		//Agent Name
				false, 		//Division
				false, 		//Department
				false, 		//Position
				false,		//Type
				false,		//Rate
				false,		//Accredit From			
				false,		//Accredit To
				false		//ATM on Hand
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
					false, 		//Agent Name
					false, 		//Division
					false, 		//Department
					false, 		//Position
					false,		//Type
					false,		//Rate
					false,		//Accredit From			
					false,		//Accredit To
					false		//ATM on Hand
			};
		}else{
			COLUMN_EDITABLE = new boolean[] {
					false, 		//Agent ID
					false, 		//Agent Name
					false, 		//Division
					false, 		//Department
					false, 		//Position
					false,		//Type
					false,		//Rate
					false,		//Accredit From			
					false,		//Accredit To
					false		//ATM on Hand
			};
		}
	}

	
	
	
}
