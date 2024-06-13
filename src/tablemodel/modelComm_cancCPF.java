package tablemodel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class modelComm_cancCPF	 extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8738810102880371204L;
	
	private boolean editable = false;
	boolean[] COLUMN_EDITABLE;
	
	
	public modelComm_cancCPF() {
		initThis();
	}

	public modelComm_cancCPF(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelComm_cancCPF(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelComm_cancCPF(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelComm_cancCPF(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelComm_cancCPF(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			"Unit",  		// 1
			"Client ",		// 2
			"Type" ,		// 3
			"Prev Comm" ,	// 4
			"Sched Amt." ,	// 5
			"Adjustment" ,	// 6
			"Gross Amt." ,	// 7
			"WTax" ,		// 8
			"VAT" ,			// 9
			"CA Liq." ,		// 10
			"Net Amt.", 	// 11
			"Status", 		// 12
			"Rel Sched.", 	// 13			
			"CPF No.", 		// 14
			"RPLF No.", 	// 15
			"CV No.", 		// 16
			"Remarks", 		// 17
			"Canceled By",  // 18
			"Canceled Date" // 19  
			};

	
	/**
	 * String.class = Alignment.CENTER
	 * Object.class = Alignment.LEFT
	 * BigDecimal.class = Alignment.RIGHT
	 * Integer.class = Alignment.CENTER
	 * Timestamp.class
	 */
	public Class[] CLASS_TYPES = new Class[] {
			String.class, 		//Unit
			Object.class, 		//Client
			String.class, 		//Type
			BigDecimal.class,	//Prev Comm
			BigDecimal.class, 	//Sched Amt.
			BigDecimal.class, 	//Adjustment
			BigDecimal.class,	//Gross Amt.
			BigDecimal.class, 	//WTax
			BigDecimal.class, 	//VAT
			BigDecimal.class,	//CA Liq.
			BigDecimal.class,	//Net Amt.
			String.class,		//Status
			Timestamp.class,	//Rel Sched.
			String.class,		//CPF No.
			String.class,		//RPLF No.
			String.class,		//CV No.
			String.class,		//Remarks
			String.class,		//Canceled By
			Timestamp.class		//Canceled Date
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
		COLUMN_EDITABLE = new boolean[] {
				false, 	//Unit
				false, 	//Client
				false, 	//Type
				false,	//Prev Comm
				false, 	//Sched Amt.
				false, 	//Adjustment
				false,	//Gross Amt.
				false, 	//WTax
				false, 	//VAT
				false,	//CA Liq.
				false,	//Net Amt.
				false,	//Status
				false,	//Rel Sched.
				false,	//CPF No.
				false,	//RPLF No.
				false,	//CV No.
				false,	//Remarks
				false,	//Canceled By
				false	//Canceled Date
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
					false, 	//Unit
					false, 	//Client
					false, 	//Type
					false,	//Prev Comm
					false, 	//Sched Amt.
					false, 	//Adjustment
					false,	//Gross Amt.
					false, 	//WTax
					false, 	//VAT
					false,	//CA Liq.
					false,	//Net Amt.
					false,	//Status
					false,	//Rel Sched.
					false,	//CPF No.
					false,	//RPLF No.
					false,	//CV No.
					false,	//Remarks
					false,	//Canceled By
					false	//Canceled Date
			};
		}else{
			COLUMN_EDITABLE = new boolean[] {
					false, 	//Unit
					false, 	//Client
					false, 	//Type
					false,	//Prev Comm
					false, 	//Sched Amt.
					false, 	//Adjustment
					false,	//Gross Amt.
					false, 	//WTax
					false, 	//VAT
					false,	//CA Liq.
					false,	//Net Amt.
					false,	//Status
					false,	//Rel Sched.
					false,	//CPF No.
					false,	//RPLF No.
					false,	//CV No.
					false,	//Remarks
					false,	//Canceled By
					false	//Canceled Date
			};
		}
	}

	
	
	
}
