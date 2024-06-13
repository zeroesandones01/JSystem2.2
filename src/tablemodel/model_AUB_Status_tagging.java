package tablemodel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class model_AUB_Status_tagging	 extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8738810102880371204L;
	
	private boolean editable = false;
	boolean[] COLUMN_EDITABLE;
	
	
	public model_AUB_Status_tagging() {
		initThis();
	}

	public model_AUB_Status_tagging(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public model_AUB_Status_tagging(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public model_AUB_Status_tagging(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public model_AUB_Status_tagging(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public model_AUB_Status_tagging(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			"Document No.  " ,		// 0
			"With MC",				// 5
			"Payee",				// 1
			"Check Date",			// 2
			"Check No.",			// 3
			"Amount",				// 4			
			"Date Released"			// 6
			};

	
	/**
	 * String.class = Alignment.CENTER
	 * Object.class = Alignment.LEFT
	 * BigDecimal.class = Alignment.RIGHT
	 * Integer.class = Alignment.CENTER
	 * Timestamp.class
	 */
	public Class[] CLASS_TYPES = new Class[] {
			String.class, 		//Document No.
			Boolean.class, 		//With MC
			Object.class,		//Payee
			Timestamp.class,	//Check Date
			String.class,		//Check No.
			BigDecimal.class, 	//Amount			
			Timestamp.class		//Date Released
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
		COLUMN_EDITABLE = new boolean[] {
				false, 		//Document No.
				true, 		//With MC
				false,		//Payee
				false,		//Check Date
				false,		//Check No.
				false, 		//Amount				
				false		//Date Released
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
					false, 		//Document No.
					true, 		//With MC
					false,		//Payee
					false,		//Check Date
					false,		//Check No.
					false, 		//Amount					
					false		//Date Released
			};
		}else{
			COLUMN_EDITABLE = new boolean[] {
					false, 		//Document No.
					false, 		//With MC
					false,		//Payee
					false,		//Check Date
					false,		//Check No.
					false, 		//Amount					
					false		//Date Released
			};
		}
	}

	
	
	
}
