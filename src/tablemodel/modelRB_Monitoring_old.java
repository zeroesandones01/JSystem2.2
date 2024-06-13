package tablemodel;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelRB_Monitoring_old extends DefaultTableModel {

	private static final long serialVersionUID = 1L;

	private boolean editable = false;
	//boolean[] COLUMN_EDITABLE;

	public modelRB_Monitoring_old() {
		initThis();
	}

	public modelRB_Monitoring_old(boolean editable) {
		initThis();
	}

	public modelRB_Monitoring_old(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelRB_Monitoring_old(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelRB_Monitoring_old(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelRB_Monitoring_old(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelRB_Monitoring_old(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}

	String[] COLUMNS = new String[] {
			"Tag", // 0
			"Batch No.", // 1
			"Client ID", // 2
			"Client Name", // 3
			"Project ID", // 4
			"Project", // 5
			"PBL ID", // 6
			"Seq.", // 7
			"Description", // 8
			"Status", // 9
			"Buyer Type" // 10
	};

	Class[] CLASS_TYPES = new Class[] {
			Boolean.class,
			String.class,
			String.class, // Client ID
			Object.class, // Client Name
			String.class, // Project ID
			String.class, // Project Name
			String.class, // PBL ID
			Integer.class, // Seq.
			Object.class, // Description
			Object.class, // Status
			Object.class // Buyer Type
	};

	boolean[] COLUMN_EDITABLE;

	
	private void initThis() {
		setColumnIdentifiers(COLUMNS);
	}

	public Class getColumnClass(int columnIndex) {
		return CLASS_TYPES[columnIndex];
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
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
					true, //"Tag", // 0
					false, //"Batch No.", // 1
					false, //"Client ID", // 2
					false, //"Client Name", // 3
					false, //"Project ID", // 4
					false, //"Project", // 5
					false, //"PBL ID", // 6
					false, //"Seq.", // 7
					false, //"Description", // 8
					false, //"Status", // 9
					false //"Buyer Type" // 10
				
			};
			
		}else{
			COLUMN_EDITABLE = new boolean[] {
					false, //"Tag", // 0
					false, //"Batch No.", // 1
					false, //"Client ID", // 2
					false, //"Client Name", // 3
					false, //"Project ID", // 4
					false, //"Project", // 5
					false, //"PBL ID", // 6
					false, //"Seq.", // 7
					false, //"Description", // 8
					false, //"Status", // 9
					false //"Buyer Type" // 10
				
			};
		}
	}
	

}
