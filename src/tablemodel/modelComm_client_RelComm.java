package tablemodel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class modelComm_client_RelComm	 extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8738810102880371204L;
	
	private boolean editable = false;
	boolean[] COLUMN_EDITABLE;
	
	
	public modelComm_client_RelComm() {
		initThis();
	}

	public modelComm_client_RelComm(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelComm_client_RelComm(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelComm_client_RelComm(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelComm_client_RelComm(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelComm_client_RelComm(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			"Selling Agent/Broker" ,// 1
			"Rate", 				// 2
			"Pstn", 				// 3
			"Schedule", 			// 4
			"Gross Amt.", 			// 5
			"WTax", 				// 6
			"CA Liq.", 				// 7
			"Net Amt.", 			// 8
			"Date Released", 		// 9
			"CPF No.", 				// 10
			"Remarks" 				// 11			
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
			String.class, 		//Rate
			String.class, 		//Pstn			
			String.class, 		//Schedule
			BigDecimal.class, 	//Gross Amt.
			BigDecimal.class, 	//WTax
			BigDecimal.class, 	//CA Liq.
			BigDecimal.class, 	//Net Amt.
			Timestamp.class, 	//Date Released
			String.class, 		//CPF No.
			String.class 		//Remarks
			
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
		COLUMN_EDITABLE = new boolean[] {
				false, 		//Selling Agent/Broker
				false, 		//Rate
				false, 		//Pstn			
				false, 		//Schedule
				false, 		//Gross Amt.
				false, 		//WTax
				false, 		//CA Liq.
				false, 		//Net Amt.
				false, 		//Date Released
				false, 		//CPF No.
				false 		//Remarks
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
					false, 		//Rate
					false, 		//Pstn			
					false, 		//Schedule
					false, 		//Gross Amt.
					false, 		//WTax
					false, 		//CA Liq.
					false, 		//Net Amt.
					false, 		//Date Released
					false, 		//CPF No.
					false 		//Remarks
			};
		}else{
			COLUMN_EDITABLE = new boolean[] {
					false, 		//Selling Agent/Broker
					false, 		//Rate
					false, 		//Pstn			
					false, 		//Schedule
					false, 		//Gross Amt.
					false, 		//WTax
					false, 		//CA Liq.
					false, 		//Net Amt.
					false, 		//Date Released
					false, 		//CPF No.
					false 		//Remarks
			};
		}
	}

	
	
	
}
