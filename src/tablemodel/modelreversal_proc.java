package tablemodel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class modelreversal_proc	 extends DefaultTableModel {

	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	
	private boolean editable = false;
	boolean[] COLUMN_EDITABLE;
	
	
	public modelreversal_proc() {
		initThis();
	}

	public modelreversal_proc(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelreversal_proc(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelreversal_proc(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelreversal_proc(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelreversal_proc(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			"Select",			//1
			"Document No.",		//2
			"Amount",			//3
			"Created by",		//4
			"Doc. Date",		//5
			"Date Posted",		//6
			"Payee"				//7
			
			};

	
	/**
	 * String.class = Alignment.CENTER
	 * Object.class = Alignment.LEFT
	 * BigDecimal.class = Alignment.RIGHT
	 * Integer.class = Alignment.CENTER
	 * Timestamp.class
	 */
	public Class[] CLASS_TYPES = new Class[] {
			Boolean.class, 		//
			String.class,		//Document No.
			BigDecimal.class,	//Amount
			Object.class, 		//Created by
			Timestamp.class, 	//Doc. Date
			Timestamp.class,	//Date Posted
			Object.class		//Payee
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
		COLUMN_EDITABLE = new boolean[] {
				false, 	//
				false,	//Document No.
				false, 	//Amount
				false,	//Created by
				false, 	//Doc. Date
				false, 	//Date Posted
				false	//Payee
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
					true, 	//
					false,	//Document No.
					false, 	//Amount
					false,	//Created by
					false, 	//Doc. Date
					false, 	//Date Posted
					false	//Payee
			};
		}else{
			COLUMN_EDITABLE = new boolean[] {
					false, 	//
					false,	//Document No.
					false, 	//Amount
					false,	//Created by
					false, 	//Doc. Date
					false, 	//Date Posted
					false	//Payee
			};
		}
	}
}