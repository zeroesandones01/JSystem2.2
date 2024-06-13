package tablemodel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class modelCancelCPF extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8738810102880371204L;
	
	private boolean editable = false;
	boolean[] COLUMN_EDITABLE;
	
	
	public modelCancelCPF() {
		initThis();
	}

	public modelCancelCPF(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelCancelCPF(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelCancelCPF(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelCancelCPF(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelCancelCPF(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			"Tag", 			// 0
			"Agent Name", 	// 1
			"Client Name", 	// 2
			"Unit", 		// 3
			"Tran. Type", 	// 4
			"CDF Date", 	// 5
			"Promo Name", 	// 6
			"Amount"		// 7
			};

	
	/**
	 * String.class = Alignment.CENTER
	 * Object.class = Alignment.LEFT
	 * BigDecimal.class = Alignment.RIGHT
	 * Integer.class = Alignment.CENTER
	 * Timestamp.class
	 */
	public Class[] CLASS_TYPES = new Class[] {
			Boolean.class, 		//Tag
			String.class, 		//Agent Name
			String.class, 		//Client Name
			String.class,		//Unit
			String.class, 		//Tran. Type
			Timestamp.class, 	//CDF Date
			String.class,		//Promo Name
			BigDecimal.class	//Amount
			
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
		COLUMN_EDITABLE = new boolean[] {
				false, 		//Tag
				false, 		//Agent Name
				false, 		//Client Name
				false,		//Unit
				false, 		//Tran. Type
				false, 		//CDF Date
				false,		//Promo Name
				false		//Amount
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
					true, 		//Tag
					false, 		//Agent Name
					false, 		//Client Name
					false,		//Unit
					false, 		//Tran. Type
					false, 		//CDF Date
					false,		//Promo Name
					false		//Amount
			};
		}else{
			COLUMN_EDITABLE = new boolean[] {
					false, 		//Tag
					false, 		//Agent Name
					false, 		//Client Name
					false,		//Unit
					false, 		//Tran. Type
					false, 		//CDF Date
					false,		//Promo Name
					false		//Amount
			};
		}
	}

	
	
	
}
