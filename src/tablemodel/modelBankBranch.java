package tablemodel;

import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class modelBankBranch extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8738810102880371204L;
	
	private boolean editable = false;
	boolean[] COLUMN_EDITABLE;
	
	
	public modelBankBranch() {
		initThis();
	}

	public modelBankBranch(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelBankBranch(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelBankBranch(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelBankBranch(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelBankBranch(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			"Bank Branch ID",		// 1
			"Branch Location" ,		// 2
			"Created By" ,			// 3
			"Created Date" ,		// 4
			"Status" 				// 5
			};

	
	/**
	 * String.class = Alignment.CENTER
	 * Object.class = Alignment.LEFT
	 * BigDecimal.class = Alignment.RIGHT
	 * Integer.class = Alignment.CENTER
	 * Timestamp.class
	 */
	public Class[] CLASS_TYPES = new Class[] {
			String.class, 		//Bank Branch ID
			String.class, 		//Branch Location
			Object.class, 		//Created By
			Timestamp.class,	//Created Date
			String.class		//Status
			
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
		COLUMN_EDITABLE = new boolean[] {
				false, 		//Bank Branch ID
				false, 		//Branch Name
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
					false, 		//Bank Branch ID
					false, 		//Branch Name
					false, 		//Created By
					false,		//Created Date
					false		//Status
			};
		}else{
			COLUMN_EDITABLE = new boolean[] {
					false, 		//Bank Branch ID
					false, 		//Branch Name
					false, 		//Created By
					false,		//Created Date
					false		//Status
			};
		}
	}

	
	
	
}
