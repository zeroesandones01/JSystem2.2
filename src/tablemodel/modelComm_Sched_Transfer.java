package tablemodel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class modelComm_Sched_Transfer	 extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8738810102880371204L;
	
	private boolean editable = false;
	boolean[] COLUMN_EDITABLE;
	
	
	public modelComm_Sched_Transfer() {
		initThis();
	}

	public modelComm_Sched_Transfer(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelComm_Sched_Transfer(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelComm_Sched_Transfer(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelComm_Sched_Transfer(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelComm_Sched_Transfer(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {			
			"Selling Agent/Broker" ,// 0
			"Rate", 				// 1
			"Pstn", 				// 2
			"Sched.", 				// 3
			"<html>Schedule<html><br><html><center>Amount<center><html>", 		// 4
			"<html>Released<html><br><html><center>Amount<center><html>", 		// 5
			"<html>New Schedule<html><br><html><center>Amount<center><html>", 	// 6
			"<html>Release<html><br><html>Date<html>", 		// 7
			"CDF No", 				// 8				
			"Status", 				// 9
			"Remarks",				// 10
			"Rec ID"				// 11
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
			Integer.class,		//Rate
			String.class,		//Pstn
			String.class,		//Sched. 
			BigDecimal.class,	//Sched. Amt.
			BigDecimal.class, 	//Rlsd. Amt.
			BigDecimal.class, 	//Rlsd. Amt.
			Timestamp.class, 	//New Sched. Amt.
			String.class, 		//CDF No
			String.class, 		//Status
			String.class, 		//Remarks			
			Integer.class 		//Rec ID			
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
		COLUMN_EDITABLE = new boolean[] {
				false, 		//Selling Agent/Broker
				false,		//Rate
				false,		//Pstn
				false,		//Sched. 
				false,		//Sched. Amt.
				false, 		//Rlsd. Amt.
				false, 		//Rlsd. Date
				false, 		//New Sched. Amt.
				false, 		//CDF No
				false, 		//Status
				false, 		//Remarks			
				false 		//Rec ID				
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
					false,		//Pstn
					false,		//Sched. 
					false,		//Sched. Amt.
					true, 		//Rlsd. Amt.
					false, 		//New Sched. Amt.
					false, 		//Rlsd. Date
					false, 		//CDF No
					false, 		//Status
					false, 		//Remarks			
					false 		//Rec ID			
			};
		}else{
			COLUMN_EDITABLE = new boolean[] {
					false, 		//Selling Agent/Broker
					false,		//Rate
					false,		//Pstn
					false,		//Sched. 
					false,		//Sched. Amt.
					false, 		//Rlsd. Amt.
					false, 		//New Sched. Amt.
					false, 		//Rlsd. Date
					false, 		//CDF No
					false, 		//Status
					false, 		//Remarks			
					false 		//Rec ID			
			};
		}
	}

	
	
	
}
