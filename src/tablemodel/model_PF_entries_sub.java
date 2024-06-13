package tablemodel;

import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class model_PF_entries_sub extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8738810102880371204L;
	
	private boolean editable = false;
	boolean[] COLUMN_EDITABLE;
	
	
	public model_PF_entries_sub() {
		initThis();
	}

	public model_PF_entries_sub(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public model_PF_entries_sub(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public model_PF_entries_sub(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public model_PF_entries_sub(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public model_PF_entries_sub(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			"Debit" ,			// 1
			"Credit" ,			// 2
			"Account Name" ,	// 3
			"Remarks", 			// 4
			"Status" 			// 5
			};

	
	/**
	 * String.class = Alignment.CENTER
	 * Object.class = Alignment.LEFT
	 * BigDecimal.class = Alignment.RIGHT
	 * Integer.class = Alignment.CENTER
	 * Timestamp.class
	 */
	public Class[] CLASS_TYPES = new Class[] {
			String.class, 		//Debit
			String.class, 		//Credit
			Object.class,		//Account Name
			Object.class,		//Remarks
			String.class, 		//Status
			
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
		COLUMN_EDITABLE = new boolean[] {
				false, 		//Debit
				false, 		//Credit
				false,		//Account Name
				false,		//Remarks
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
					false, 		//Debit
					false, 		//Credit
					false,		//Account Name
					false,		//Remarks
					false		//Status
			};
		}else{
			COLUMN_EDITABLE = new boolean[] {
					false, 		//Debit
					false, 		//Credit
					false,		//Account Name
					false,		//Remarks
					false		//Status
			};
		}
	}

	
	
	
}
