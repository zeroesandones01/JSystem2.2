package tablemodel;

import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class modelBank extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8738810102880371204L;
	
	private boolean editable = false;
	boolean[] COLUMN_EDITABLE;
	
	
	public modelBank() {
		initThis();
	}

	public modelBank(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelBank(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelBank(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelBank(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelBank(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			"Bank ID",			// 1
			"Bank Name" ,		// 2
			"Bank Alias" ,		// 3
			"Created By" ,		// 4
			"Created Date" ,	// 5
			"Status" 			// 6
			};

	
	/**
	 * String.class = Alignment.CENTER
	 * Object.class = Alignment.LEFT
	 * BigDecimal.class = Alignment.RIGHT
	 * Integer.class = Alignment.CENTER
	 * Timestamp.class
	 */
	public Class[] CLASS_TYPES = new Class[] {
			String.class, 		//Bank ID
			String.class, 		//Bank Name
			String.class, 		//Bank Alias
			Object.class, 		//Created By
			Timestamp.class,	//Created Date
			String.class		//Status
			
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
		COLUMN_EDITABLE = new boolean[] {
				false, 		//Bank ID
				false, 		//Bank Name
				false, 		//Bank Alias
				false, 		//Created By
				false,		//Created Date
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
					false, 		//Bank ID
					false, 		//Bank Name
					false, 		//Bank Alias
					false, 		//Created By
					false,		//Created Date
					false		//Status
			};
		}else{
			COLUMN_EDITABLE = new boolean[] {
					false, 		//Bank ID
					false, 		//Bank Name
					false, 		//Bank Alias
					false, 		//Created By
					false,		//Created Date
					false		//Status
			};
		}
	}

	
	
	
}
