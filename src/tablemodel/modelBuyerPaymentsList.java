package tablemodel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class modelBuyerPaymentsList extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8738810102880371204L;
	
	private boolean editable = false;
	boolean[] COLUMN_EDITABLE;
	
	
	public modelBuyerPaymentsList() {
		initThis();
	}

	public modelBuyerPaymentsList(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelBuyerPaymentsList(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelBuyerPaymentsList(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelBuyerPaymentsList(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelBuyerPaymentsList(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			"Rec. ID",		// 1
			"OR No." ,		// 2
			"AR No." ,		// 3
			"Pmt. Particular" ,		// 4
			"Pmt. Type" ,			// 5
			"Amount", 		// 6
			"Principal", 	// 7
			"Perc. Paid", 	// 8
			"Trans. Date" ,	// 9
			"OR Date" ,		// 10
			"Remarks" ,		// 11
			"Branch" 		// 12
			};

	
	/**
	 * String.class = Alignment.CENTER
	 * Object.class = Alignment.LEFT
	 * BigDecimal.class = Alignment.RIGHT
	 * Integer.class = Alignment.CENTER
	 * Timestamp.class
	 */
	public Class[] CLASS_TYPES = new Class[] {
			String.class, 		//Rec. ID
			String.class, 		//OR No.
			String.class, 		//AR No.
			String.class, 		//Pmt. Particular
			String.class,		//Pmt. Type
			BigDecimal.class,	//Amount
			BigDecimal.class,	//Principal
			BigDecimal.class,	//Perc. Paid
			Timestamp.class,	//Trans. Date
			Timestamp.class,	//OR Date
			Object.class,		//Remarks
			String.class		//Branch
			
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
		COLUMN_EDITABLE = new boolean[] {
				false, 		//Rec. ID
				false, 		//OR No.
				false, 		//AR No.
				false, 		//Pmt. Particular
				false,		//Pmt. Type
				false,		//Amount
				false,		//Principal
				false,		//Perc. Paid
				false,		//Trans. Date
				false,		//OR Date
				false,		//Remarks
				false		//Branch
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
					false, 		//Rec. ID
					false, 		//OR No.
					false, 		//AR No.
					false, 		//Pmt. Particular
					false,		//Pmt. Type
					false,		//Amount
					false,		//Principal
					false,		//Perc. Paid
					false,		//Trans. Date
					false,		//OR Date
					false,		//Remarks
					false		//Branch
			};
		}else{
			COLUMN_EDITABLE = new boolean[] {
					false, 		//Rec. ID
					false, 		//OR No.
					false, 		//AR No.
					false, 		//Pmt. Particular
					false,		//Pmt. Type
					false,		//Amount
					false,		//Principal
					false,		//Perc. Paid
					false,		//Trans. Date
					false,		//OR Date
					false,		//Remarks
					false		//Branch
			};
		}
	}

	
	
	
}
