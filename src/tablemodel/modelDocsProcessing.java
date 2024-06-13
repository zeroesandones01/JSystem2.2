package tablemodel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class modelDocsProcessing	 extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8738810102880371204L;
	
	private boolean editable = false;
	boolean[] COLUMN_EDITABLE;
	
	
	public modelDocsProcessing() {
		initThis();
	}

	public modelDocsProcessing(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelDocsProcessing(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelDocsProcessing(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelDocsProcessing(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelDocsProcessing(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			"Document No.  " ,		// 0
			"Check Date",			// 1
			"Check No.",			// 2
			"Amount",				// 3
			"For Process",			// 4
			"For Return",			// 5
			"Created by",			// 6
			"Last Edited by",		// 7
			"Doc. Date",			// 8
			"Return Message",		// 9
			"Date Posted",		    // 10
			"Payee",		            // 11
			"Authorized Person",
			"Authorized Representative"
			};

	
	/**
	 * String.class = Alignment.CENTER
	 * Object.class = Alignment.LEFT
	 * BigDecimal.class = Alignment.RIGHT
	 * Integer.class = Alignment.CENTER
	 * Timestamp.class
	 */
	public Class[] CLASS_TYPES = new Class[] {
			String.class, 		//Document No.
			Timestamp.class,	//Doc. Date
			String.class,	//Doc. Date
			BigDecimal.class,	//Amount
			Boolean.class, 		//For Process
			Boolean.class, 		//For Return
			Object.class,		//Created by
			Object.class,		//Last Edited by
			Timestamp.class,	//Doc. Date
			Object.class,		//Return Message
			Timestamp.class, 	//Date Posted
			Object.class,		//Payee
			Object.class,		
			Object.class		
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
		COLUMN_EDITABLE = new boolean[] {
				false, 	//Document No.
				false,	//Amount
				false, 	//For Process
				false,	//Amount
				false, 	//For Process
				false, 	//For Return
				false,	//Created by	
				false,	//Last Edited by
				false,	//Doc. Date
				false,	//Return Message
				false,	//Date Posted
				false,	//Payee
				false,	
				false	
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
					false, 	//Document No.
					false,	//Amount
					false, 	//For Process
					false,	//Amount
					true, 	//For Process
					true, 	//For Return
					false,	//Created by	
					false,	//Last Edited by
					false,	//Doc. Date
					true,	//Return Message
					false,	//Date Posted
					false,	//Payee
					false,	
					false
			};
		}else{
			COLUMN_EDITABLE = new boolean[] {
					false, 	//Document No.
					false,	//Amount
					false, 	//For Process
					false,	//Amount
					false, 	//For Process
					false, 	//For Return
					false,	//Created by	
					false,	//Last Edited by
					false,	//Doc. Date	
					false,	//Return Message
					false,	//Date Posted
					false,	//Payee
					false,	
					false
			};
		}
	}

	
	
	
}