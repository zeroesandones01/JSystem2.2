package tablemodel;

import java.math.BigDecimal;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class modelComm_agentPromo_general	 extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8738810102880371204L;
	
	private boolean editable = false;
	boolean[] COLUMN_EDITABLE;
	
	
	public modelComm_agentPromo_general() {
		initThis();
	}

	public modelComm_agentPromo_general(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelComm_agentPromo_general(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelComm_agentPromo_general(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelComm_agentPromo_general(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelComm_agentPromo_general(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			"Promo Name" ,		// 1
			"Release Schedule", // 2
			"Gross Amt.", 		// 3
			"WTax", 			// 4
			"CA Liq", 			// 5
			"Net Amount", 		// 6
			"Status", 			// 7
			"CPF No.", 			// 8
			"RPLF No.", 		// 9
			"CV No.", 			// 10
			"In Kind Promo Particular" 	// 11
			};

	
	/**
	 * String.class = Alignment.CENTER
	 * Object.class = Alignment.LEFT
	 * BigDecimal.class = Alignment.RIGHT
	 * Integer.class = Alignment.CENTER
	 * Timestamp.class
	 */
	public Class[] CLASS_TYPES = new Class[] {
			String.class, 		//Promo Name
			String.class, 		//Release Schedule
			BigDecimal.class, 	//Gross Amt.
			BigDecimal.class, 	//WTax
			BigDecimal.class, 	//CA Liq
			BigDecimal.class, 	//Net Amount
			String.class, 		//Status
			String.class, 		//CPF No.
			String.class, 		//RPLF No.
			String.class, 		//CV No.
			String.class 		//In Kind Promo Particular
			
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
		COLUMN_EDITABLE = new boolean[] {
				false, 		//Account Name
				false,		//Promo Name
				false, 		//Gross Amt.
				false, 		//WTax
				false, 		//CA Liq
				false, 		//Net Amount
				false, 		//Status
				false, 		//CPF No.
				false, 		//RPLF No.
				false, 		//CV No.
				false 		//In Kind Promo Particular
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
					false, 		//Account Name
					false,		//Promo Name
					false, 		//Gross Amt.
					false, 		//WTax
					false, 		//CA Liq
					false, 		//Net Amount
					false, 		//Status
					false, 		//CPF No.
					false, 		//RPLF No.
					false, 		//CV No.
					false 		//In Kind Promo Particular
			};
		}else{
			COLUMN_EDITABLE = new boolean[] {
					false, 		//Account Name
					false,		//Promo Name
					false, 		//Gross Amt.
					false, 		//WTax
					false, 		//CA Liq
					false, 		//Net Amount
					false, 		//Status
					false, 		//CPF No.
					false, 		//RPLF No.
					false, 		//CV No.
					false 		//In Kind Promo Particular
			};
		}
	}

	
	
	
}
