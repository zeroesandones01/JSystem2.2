package tablemodel;

import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelProcStatus extends DefaultTableModel {

	private static final long serialVersionUID = 1L;
	
	private boolean editable = false;
	boolean[] COLUMN_EDITABLE;

	public modelProcStatus() {
		initThis();
	}

	public modelProcStatus(boolean editable) {
		initThis();
	}

	public modelProcStatus(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelProcStatus(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelProcStatus(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelProcStatus(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelProcStatus(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}
	
	String[] COLUMNS = new String[] {
			"Processing Status", 
			"Status Date",
			"Done By",
			"Recipient",
			"Doc. Location",
			"Remarks",
			"Status ID",
			"Done/Accomplish By ID",
			"Recipient ID",
			"Doc ID",
			"Rec ID"

	};
	
	Class[] CLASS_TYPES = new Class[] {
			Object.class, //Bond No
			Timestamp.class, //Insurance Co. ID
			String.class, //From
			String.class,
			String.class,
			String.class,
			String.class,
			String.class,
			String.class,
			String.class,
			Integer.class
	};
	
	private void initThis() {
		setColumnIdentifiers(COLUMNS);
		COLUMN_EDITABLE = new boolean[] {
				false, //No.
				false, //Work Item
				false, //Quantity
				false,
				false,
				false,
				false,
				false,
				false,
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
	
	public void clear() {
		FncTables.clearTable(this);
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
		if(editable){
			COLUMN_EDITABLE = new boolean[] {
					false, //No.
					false, //Work Item
					false, //Quantity
					false,
					false,
					false,
					false,
					false,
					false,
					false,
					false
			};
		}else{
			COLUMN_EDITABLE = new boolean[] {
					false, //No.
					false, //Work Item
					false, //Quantity
					false,
					false,
					false,
					false,
					false,
					false,
					false,
					false
			};
		}
	}

}
