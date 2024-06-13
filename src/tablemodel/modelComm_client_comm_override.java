package tablemodel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class modelComm_client_comm_override	 extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8738810102880371204L;
	
	private boolean editable = false;
	boolean[] COLUMN_EDITABLE;
	
	
	public modelComm_client_comm_override() {
		initThis();
	}

	public modelComm_client_comm_override(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelComm_client_comm_override(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelComm_client_comm_override(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelComm_client_comm_override(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelComm_client_comm_override(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			"Tag",					// 1
			"Selling Agent/Broker" ,// 2
			"Rate", 				// 3
			"Pstn", 				// 4
			"Schedule", 			// 5
			"<html><center>Prev<html><br><html><Comm.>Amount<html>", 		// 6
			"<html><center>Sched.<html><br><html><Comm.>Amount<html>", 		// 7
			"Adjust.", 				// 8
			"<html><center>Gross<html><br><html><Comm.>Amount<html>", 		// 9
			"WTax", 				// 10
			"CA Liq.", 				// 11
			"Net Amt.", 			// 12
			"Status", 				// 13
			"Release Sched.", 		// 14
			"Actual Release", 		// 15
			"CPF No.", 				// 16
			"RPLF No.", 			// 17
			"CV No.", 				// 18
			"Remarks", 				// 19		
			"Account Code"  		// 20
			};

	
	/**
	 * String.class = Alignment.CENTER
	 * Object.class = Alignment.LEFT
	 * BigDecimal.class = Alignment.RIGHT
	 * Integer.class = Alignment.CENTER
	 * Timestamp.class
	 */
	public Class[] CLASS_TYPES = new Class[] {
			Boolean.class, 		//Tag
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
			String.class 		//Account Code
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
		COLUMN_EDITABLE = new boolean[] {
				false, 		//Tag
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
				false 		//Account Code
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
					true, 		//Tag
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
					false 		//Account Code
			};
		}else{
			COLUMN_EDITABLE = new boolean[] {
					false, 		//Tag
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
					false 		//Account Code
			};
		}
	}

	
	
	
}
