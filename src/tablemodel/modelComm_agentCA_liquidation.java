package tablemodel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class modelComm_agentCA_liquidation	 extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8738810102880371204L;
	
	private boolean editable = false;
	boolean[] COLUMN_EDITABLE;
	
	
	public modelComm_agentCA_liquidation() {
		initThis();
	}

	public modelComm_agentCA_liquidation(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelComm_agentCA_liquidation(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelComm_agentCA_liquidation(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelComm_agentCA_liquidation(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelComm_agentCA_liquidation(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			"CDF No." ,		// 1
			"Liq No.", 		// 2
			"JV No.", 		// 3
			"Liq Amt.", 	// 4
			"Date" 			// 5
			};

	
	/**
	 * String.class = Alignment.CENTER
	 * Object.class = Alignment.LEFT
	 * BigDecimal.class = Alignment.RIGHT
	 * Integer.class = Alignment.CENTER
	 * Timestamp.class
	 */
	public Class[] CLASS_TYPES = new Class[] {
			String.class, 		//CDF No.
			String.class,		//Liq No.
			String.class, 		//JV No.
			BigDecimal.class, 	//Liq Amt.
			Timestamp.class 	//Date
			
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
		COLUMN_EDITABLE = new boolean[] {
				false, 	//CDF No.
				false,	//Liq No.
				false, 	//JV No.
				false, 	//Liq Amt.
				false 	//Date
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
					false, 	//CDF No.
					false,	//Liq No.
					false, 	//JV No.
					false, 	//Liq Amt.
					false 	//Date
			};
		}else{
			COLUMN_EDITABLE = new boolean[] {
					false, 	//CDF No.
					false,	//Liq No.
					false, 	//JV No.
					false, 	//Liq Amt.
					false 	//Date
			};
		}
	}

	
	
	
}
