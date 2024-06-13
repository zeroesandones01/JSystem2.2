package tablemodel;

import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class model_PF_entries extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8738810102880371204L;
	
	private boolean editable = false;
	boolean[] COLUMN_EDITABLE;
	
	
	public model_PF_entries() {
		initThis();
	}

	public model_PF_entries(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public model_PF_entries(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public model_PF_entries(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public model_PF_entries(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public model_PF_entries(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			"PF No.",			// 1
			"Description" ,		// 2
			"Payment Particular",// 3
			"Cost Particular" ,	// 4
			"Receipt Type" ,	// 5
			"Status", 			// 6
			"Created by", 	    // 7
			"Date Created", 	// 8
			"Edited by" ,		// 9
			"Date Edited", 		// 10
			"Canceled by" ,		// 11
			"Date Canceled" 	// 12
			};

	
	/**
	 * String.class = Alignment.CENTER
	 * Object.class = Alignment.LEFT
	 * BigDecimal.class = Alignment.RIGHT
	 * Integer.class = Alignment.CENTER
	 * Timestamp.class
	 */
	public Class[] CLASS_TYPES = new Class[] {
			String.class, 		//PF No.
			Object.class, 		//Description
			String.class, 		//Payment Particular
			String.class, 		//Cost Particular
			String.class,		//Receipt Type
			String.class,		//Status
			String.class,		//Created by
			Timestamp.class,	//Date Created
			String.class,		//Edited by
			Timestamp.class,	//Date Edited
			String.class,		//Canceled by
			Timestamp.class		//Date Canceled
			
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
		COLUMN_EDITABLE = new boolean[] {
				false, 		//PF No.
				false, 		//Description
				false, 		//Payment Particular
				false, 		//Cost Particular
				false,		//Receipt Type
				false,		//Status
				false,		//Created by
				false,		//Date Created
				false,		//Edited by
				false,		//Date Edited
				false,		//Canceled by
				false		//Date Canceled
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
					false, 		//PF No.
					false, 		//Description
					false, 		//Payment Particular
					false, 		//Cost Particular
					false,		//Receipt Type
					false,		//Status
					false,		//Created by
					false,		//Date Created
					false,		//Edited by
					false,		//Date Edited
					false,		//Canceled by
					false		//Date Canceled
			};
		}else{
			COLUMN_EDITABLE = new boolean[] {
					false, 		//PF No.
					false, 		//Description
					false, 		//Payment Particular
					false, 		//Cost Particular
					false,		//Receipt Type
					false,		//Status
					false,		//Created by
					false,		//Date Created
					false,		//Edited by
					false,		//Date Edited
					false,		//Canceled by
					false		//Date Canceled
			};
		}
	}

	
	
	
}
