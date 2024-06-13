package tablemodel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class modelComm_agentComm_schedule	 extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8738810102880371204L;
	
	private boolean editable = false;
	boolean[] COLUMN_EDITABLE;
	
	
	public modelComm_agentComm_schedule() {
		initThis();
	}

	public modelComm_agentComm_schedule(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelComm_agentComm_schedule(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelComm_agentComm_schedule(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelComm_agentComm_schedule(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelComm_agentComm_schedule(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			"Type" ,		// 1
			"Prev Comm" ,	// 2
			"Sched Amt." ,	// 3
			"Adjustment" ,	// 4
			"Gross Amt." ,	// 5
			"WTax" ,		// 6
			"VAT" ,			// 7
			"CA Liq." ,		// 8
			"Net Amt.", 	// 9
			"Status", 		// 10
			"Rel Sched.", 	// 11
			"Actual Rels.", // 12
			"CPF No.", 		// 13
			"RPLF No.", 	// 14
			"CV No.", 		// 15
			"Remarks" 		// 16
			};

	
	/**
	 * String.class = Alignment.CENTER
	 * Object.class = Alignment.LEFT
	 * BigDecimal.class = Alignment.RIGHT
	 * Integer.class = Alignment.CENTER
	 * Timestamp.class
	 */
	public Class[] CLASS_TYPES = new Class[] {
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
			Timestamp.class,	//Actual Rels.
			String.class,		//CPF No.
			String.class,		//RPLF No.
			String.class,		//CV No.
			String.class		//Remarks
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
		COLUMN_EDITABLE = new boolean[] {
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
				false,	//Actual Rels.
				false,	//CPF No.
				false,	//RPLF No.
				false,	//CV No.
				false	//Remarks
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
					false,	//Actual Rels.
					false,	//CPF No.
					false,	//RPLF No.
					false,	//CV No.
					false	//Remarks
			};
		}else{
			COLUMN_EDITABLE = new boolean[] {
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
					false,	//Actual Rels.
					false,	//CPF No.
					false,	//RPLF No.
					false,	//CV No.
					false	//Remarks
			};
		}
	}

	
	
	
}
