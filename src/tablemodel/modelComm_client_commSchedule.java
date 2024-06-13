package tablemodel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class modelComm_client_commSchedule	 extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8738810102880371204L;
	
	private boolean editable = false;
	boolean[] COLUMN_EDITABLE;
	
	
	public modelComm_client_commSchedule() {
		initThis();
	}

	public modelComm_client_commSchedule(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelComm_client_commSchedule(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelComm_client_commSchedule(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelComm_client_commSchedule(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelComm_client_commSchedule(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			"Selling Agent/Broker" ,// 1
			"Rate", 				// 2
			"Pstn", 				// 3
			"Schedule", 			// 4
			"Prev Comm.", 			// 5
			"Sched. Amt.", 			// 6
			"Adjustment", 			// 7
			"Gross Amt.", 			// 8
			"WTax", 				// 9
			"CA Liq.", 				// 10
			"Net Amt.", 			// 11
			"Status", 				// 12
			"Release Sched.", 		// 13
			"Actual Release", 		// 14
			"CPF No.", 				// 15
			"RPLF No.", 			// 16
			"CV No.", 				// 17
			"Remarks", 				// 18
			"Overrided by", 		// 19
			"Override Date" 		// 20
			
			};

	
	/**
	 * String.class = Alignment.CENTER
	 * Object.class = Alignment.LEFT
	 * BigDecimal.class = Alignment.RIGHT
	 * Integer.class = Alignment.CENTER
	 * Timestamp.class
	 */
	public Class[] CLASS_TYPES = new Class[] {
			String.class, 		//Selling Agent/Broker
			String.class,		//Rate
			String.class, 		//Pstn
			String.class, 		//Schedule
			BigDecimal.class, 	//Prev Comm.
			BigDecimal.class, 	//Sched. Amt.
			BigDecimal.class, 	//Adjustment
			BigDecimal.class, 	//Gross Amt.
			BigDecimal.class, 	//WTax
			BigDecimal.class, 	//CA Liq.
			BigDecimal.class, 	//Net Amt.
			String.class, 		//Status
			Timestamp.class, 	//Release Sched.
			Timestamp.class, 	//Actual Releas
			String.class, 		//CPF No.
			String.class, 		//RPLF No.
			String.class, 		//CV No.
			String.class, 		//Remarks
			String.class, 		//Overrided by
			Timestamp.class 	//Override Date
			
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
				false, 		//Adjustment
				false, 		//Gross Amt.
				false, 		//WTax
				false, 		//CA Liq.
				false, 		//Net Amt.
				false, 		//Status
				false, 		//Release Sched.
				false, 		//Actual Releas
				false, 		//CPF No.
				false, 		//RPLF No.
				false, 		//CV No.
				false, 		//Remarks
				false, 		//Overrided by
				false 		//Override Date
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
					false, 		//Adjustment
					false, 		//Gross Amt.
					false, 		//WTax
					false, 		//CA Liq.
					false, 		//Net Amt.
					false, 		//Status
					false, 		//Release Sched.
					false, 		//Actual Releas
					false, 		//CPF No.
					false, 		//RPLF No.
					false, 		//CV No.
					false, 		//Remarks
					false, 		//Overrided by
					false 		//Override Date
			};
		}else{
			COLUMN_EDITABLE = new boolean[] {
					false, 		//Selling Agent/Broker
					false,		//Rate
					false, 		//Pstn
					false, 		//Schedule
					false, 		//Prev Comm.
					false, 		//Sched. Amt.
					false, 		//Adjustment
					false, 		//Gross Amt.
					false, 		//WTax
					false, 		//CA Liq.
					false, 		//Net Amt.
					false, 		//Status
					false, 		//Release Sched.
					false, 		//Actual Releas
					false, 		//CPF No.
					false, 		//RPLF No.
					false, 		//CV No.
					false, 		//Remarks
					false, 		//Overrided by
					false 		//Override Date
			};
		}
	}

	
	
	
}
