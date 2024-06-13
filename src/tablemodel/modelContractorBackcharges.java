package tablemodel;

import java.math.BigDecimal;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class modelContractorBackcharges extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8738810102880371204L;
	
	private boolean editable = false;
	boolean[] COLUMN_EDITABLE;
	
	
	public modelContractorBackcharges() {
		initThis();
	}

	public modelContractorBackcharges(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelContractorBackcharges(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelContractorBackcharges(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelContractorBackcharges(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelContractorBackcharges(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			"RPLF/JV",		// 1
			"Date",			// 2
			"BC Amt",		// 3
			"Liquidated",	// 4
			"For Liqui.",	// 5
			"OB",			// 6
			"Type",			// 7
			"Comp"			// 8
			
			};

	
	/**
	 * String.class = Alignment.CENTER
	 * Object.class = Alignment.LEFT
	 * BigDecimal.class = Alignment.RIGHT
	 * Integer.class = Alignment.CENTER
	 * Timestamp.class
	 */
	public Class[] CLASS_TYPES = new Class[] {
			String.class, 		//RPLF/JV
			String.class,		//Date
			BigDecimal.class,	//BC Amt
			BigDecimal.class, 	//Liquidated
			BigDecimal.class, 	//For Liqui.
			BigDecimal.class,	//OB	
			String.class, 		//Type
			String.class 		//Comp
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
		COLUMN_EDITABLE = new boolean[] {
				false, 		//RPLF/JV
				false,		//Date
				false,		//BC Amt
				false, 		//Liquidated
				false, 		//For Liqui.
				false,		//OB	
				false, 		//Type
				false 		//Comp
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
					false, 		//RPLF/JV
					false,		//Date
					false,		//BC Amt
					false, 		//Liquidated
					true, 		//For Liqui.
					false,		//OB	
					false, 		//Type
					false 		//Comp
			};
		}else{
			COLUMN_EDITABLE = new boolean[] {
					false, 		//RPLF/JV
					false,		//Date
					false,		//BC Amt
					false, 		//Liquidated
					false, 		//For Liqui.
					false,		//OB	
					false, 		//Type
					false 		//Comp
			};
		}
	}

	
	
	
}
