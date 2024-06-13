package tablemodel;

import java.math.BigDecimal;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class modelComm_schedRestructure	 extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8738810102880371204L;
	
	private boolean editable = false;
	boolean[] COLUMN_EDITABLE;
	
	
	public modelComm_schedRestructure() {
		initThis();
	}

	public modelComm_schedRestructure(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelComm_schedRestructure(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelComm_schedRestructure(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelComm_schedRestructure(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelComm_schedRestructure(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			"Selling Agent/Broker" ,// 1
			"Rate", 				// 2
			"Pstn", 				// 3
			"Schedule", 			// 4
			"Prev Comm.", 			// 5
			"Sched. Amt.", 			// 6
			"Status", 				// 7	
			"Remarks",				// 8
			"Record ID", 			// 9
			"Agent Type ID" 		// 10
			};

	
	/**
	 * String.class = Alignment.CENTER
	 * Object.class = Alignment.LEFT
	 * BigDecimal.class = Alignment.RIGHT
	 * Integer.class = Alignment.CENTER
	 * Timestamp.class
	 */
	public Class[] CLASS_TYPES = new Class[] {
			Object.class, 		//Selling Agent/Broker
			String.class,		//Rate
			String.class, 		//Pstn
			String.class, 		//Schedule
			BigDecimal.class, 	//Prev Comm.
			BigDecimal.class, 	//Sched. Amt.
			String.class, 		//Status
			String.class, 		//Remarks
			String.class, 		//Record ID
			String.class 		//Agent Type
			
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
		COLUMN_EDITABLE = new boolean[] {
				false, 		//Selling Agent/Broker
				false,		//Rate
				false, 		//Pstn
				false, 		//Schedule
				false, 		//Prev Comm.
				false, 		//Sched. Amt.
				false, 		//Status
				false, 		//Remarks
				false, 		//Record ID
				false 		//Agent Type
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
					false, 		//Selling Agent/Broker
					false,		//Rate
					false, 		//Pstn
					false, 		//Schedule
					false, 		//Prev Comm.
					false, 		//Sched. Amt.
					false, 		//Status
					false, 		//Remarks
					false, 		//Record ID
					false 		//Agent Type
			};
		}else{
			COLUMN_EDITABLE = new boolean[] {
					false, 		//Selling Agent/Broker
					false,		//Rate
					false, 		//Pstn
					false, 		//Schedule
					false, 		//Prev Comm.
					false, 		//Sched. Amt.
					false, 		//Status
					false, 		//Remarks
					false, 		//Record ID
					false 		//Agent Type
			};
		}
	}

	
	
	
}
