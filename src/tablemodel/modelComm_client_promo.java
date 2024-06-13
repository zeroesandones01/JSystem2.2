package tablemodel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class modelComm_client_promo	 extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8738810102880371204L;
	
	private boolean editable = false;
	boolean[] COLUMN_EDITABLE;
	
	
	public modelComm_client_promo() {
		initThis();
	}

	public modelComm_client_promo(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelComm_client_promo(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelComm_client_promo(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelComm_client_promo(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelComm_client_promo(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			"Selling Agent/Broker" ,// 1
			"Pstn", 				// 2
			"Promo Name", 			// 3
			"Gross Amt.", 			// 4
			"WTax", 				// 5
			"CA Liq.", 				// 6
			"Net Amt.", 			// 7
			"Status", 				// 8
			"Actual Release", 		// 9
			"CPF No.", 				// 10
			"RPLF No.", 			// 11
			"CV No.", 				// 12
			"In Kind Promo Particular", 		// 13
			"Remarks" 				// 14			
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
			String.class, 		//Pstn
			String.class, 		//Promo Name
			BigDecimal.class, 	//Gross Amt.
			BigDecimal.class, 	//WTax
			BigDecimal.class, 	//CA Liq.
			BigDecimal.class, 	//Net Amt.
			String.class, 		//Status
			Timestamp.class, 	//Release Sched.
			String.class, 		//CPF No.
			String.class, 		//RPLF No.
			String.class, 		//CV No.
			String.class, 		//In Kind Promo Particular
			String.class 		//Remarks
			
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
		COLUMN_EDITABLE = new boolean[] {
				false, 		//Selling Agent/Broker
				false, 		//Pstn
				false, 		//Promo Name
				false, 		//Gross Amt.
				false, 		//WTax
				false, 		//CA Liq.
				false, 		//Net Amt.
				false, 		//Status
				false, 		//Release Sched.
				false, 		//CPF No.
				false, 		//RPLF No.
				false, 		//CV No.
				false, 		//In Kind Promo Particular
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
					false, 		//Pstn
					false, 		//Promo Name
					false, 		//Gross Amt.
					false, 		//WTax
					false, 		//CA Liq.
					false, 		//Net Amt.
					false, 		//Status
					false, 		//Release Sched.
					false, 		//CPF No.
					false, 		//RPLF No.
					false, 		//CV No.
					false, 		//In Kind Promo Particular
					false 		//Remarks
			};
		}else{
			COLUMN_EDITABLE = new boolean[] {
					false, 		//Selling Agent/Broker
					false, 		//Pstn
					false, 		//Promo Name
					false, 		//Gross Amt.
					false, 		//WTax
					false, 		//CA Liq.
					false, 		//Net Amt.
					false, 		//Status
					false, 		//Release Sched.
					false, 		//CPF No.
					false, 		//RPLF No.
					false, 		//CV No.
					false, 		//In Kind Promo Particular
					false 		//Remarks
			};
		}
	}

	
	
	
}