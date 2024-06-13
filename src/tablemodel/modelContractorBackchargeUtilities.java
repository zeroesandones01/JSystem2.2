package tablemodel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class modelContractorBackchargeUtilities extends DefaultTableModel {
	
private static final long serialVersionUID = -8738810102880371204L;
	
	private boolean editable = false;
	boolean[] COLUMN_EDITABLE;
	
	
	public modelContractorBackchargeUtilities() {
		initThis();
	}

	public modelContractorBackchargeUtilities(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelContractorBackchargeUtilities(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelContractorBackchargeUtilities(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelContractorBackchargeUtilities(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelContractorBackchargeUtilities(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {	
			
			"Charge To",		// 4	
			"Charge To Payee Type",	// 5
			"Gross Amount",		// 2
			"JV No.",			// 3
			"Remarks"		    // 6
			};
	
	/**
	 * String.class = Alignment.CENTER
	 * Object.class = Alignment.LEFT
	 * BigDecimal.class = Alignment.RIGHT
	 * Integer.class = Alignment.CENTER
	 * Timestamp.class
	 */
	public Class[] CLASS_TYPES = new Class[] {
			Object.class,		//Charge To
			String.class, 		//Type
			BigDecimal.class,	//Gross Amount
			String.class, 		//JV No.
			String.class, 		//Remarks
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
		COLUMN_EDITABLE = new boolean[] {
				false,	//Charge To
				false, 	//Type
				false, 	//Gross Amount
				false,	//JV No.
				false, 	//Remarks
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
					false,	//Charge To
					false, 	//Type
					false, 	//Gross Amount
					false,	//JV No.
					false, 	//Remarks
			};
		}else{
			COLUMN_EDITABLE = new boolean[] {
					false,	//Charge To
					false, 	//Type
					false, 	//Gross Amount
					false,	//JV No.
					false, 	//Remarks
			};
		}
	}

}
