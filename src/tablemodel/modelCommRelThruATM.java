package tablemodel;

import java.math.BigDecimal;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class modelCommRelThruATM	 extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8738810102880371204L;
	
	private boolean editable = false;
	boolean[] COLUMN_EDITABLE;
	
	
	public modelCommRelThruATM() {
		initThis();
	}

	public modelCommRelThruATM(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelCommRelThruATM(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelCommRelThruATM(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelCommRelThruATM(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelCommRelThruATM(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			"CPF No." ,				// 1
			"RPLF No." ,			// 2
			"Selling Agent/Broker", // 3
			"ATM No." ,				// 4
			"Net Comm. Amt." 		// 5
			};

	
	/**
	 * String.class = Alignment.CENTER
	 * Object.class = Alignment.LEFT
	 * BigDecimal.class = Alignment.RIGHT
	 * Integer.class = Alignment.CENTER
	 * Timestamp.class
	 */
	public Class[] CLASS_TYPES = new Class[] {			
			String.class, 		//CPF No.
			String.class, 		//RPLF No.
			Object.class,		//Selling Agent/Broker
			String.class,		//ATM No.
			BigDecimal.class		//Net Comm. Amt.			
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
		COLUMN_EDITABLE = new boolean[] {
				false, 		//CPF No.
				false, 		//RPLF No.
				false,		//Selling Agent/Broker
				false,		//ATM No.
				false		//Net Comm. Amt.		
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
					false, 		//CPF No.
					false, 		//RPLF No.
					false,		//Selling Agent/Broker
					false,		//ATM No.
					false		//Net Comm. Amt.	
			};
		}else{
			COLUMN_EDITABLE = new boolean[] {
					false, 		//CPF No.
					false, 		//RPLF No.
					false,		//Selling Agent/Broker
					false,		//ATM No.
					false		//Net Comm. Amt.	
			};
		}
	}

	
	
	
}
