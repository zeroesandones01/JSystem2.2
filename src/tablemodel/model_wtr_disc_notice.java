package tablemodel;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;
public class model_wtr_disc_notice extends DefaultTableModel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8738810102880371204L;
	
	
	public model_wtr_disc_notice() {
		initThis();
	}

	public model_wtr_disc_notice(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public model_wtr_disc_notice(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public model_wtr_disc_notice(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public model_wtr_disc_notice(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public model_wtr_disc_notice(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	private void initThis() {
		setColumnIdentifiers(new String[] {
			"Select" ,				    // 0
			"PH-BLK-LT" ,			// 1
			"Homeowner Name" ,		// 2
			"Amount Due", 			// 3
			"Due Date" ,			// 4
			"Last Payment Date" ,	// 5
			"Entity ID", 			//6
			"Proj. ID", 		    //7
			"PBL ID",				//8
			"Seq. No"				//9
			
		});
	}
	
	/**
	 * String.class = Alignment.CENTER
	 * Object.class = Alignment.LEFT
	 * BigDecimal.class = Alignment.RIGHT
	 * Integer.class = Alignment.CENTER
	 * Timestamp.class
	 */
	
	public Class[] CLASS_TYPES = new Class[] {
			Boolean.class,		//checkbox
			String.class, 		//Ph-Blk-Lt
			String.class, 		//Name
			BigDecimal.class,	//Amount
			Date.class,			//Due Date
			Date.class,			//Last Payment Date
			String.class,		//PBL ID
			String.class, 		//Proj. ID
			String.class, 		//PBL ID
			Integer.class		//Seq. No.

		
	};

	Boolean[] COLUMN_EDITABLE = new Boolean[] {

			true,		//checkbox
			false, 		//Ph-Blk-Lt
			false, 		//Name
			false,		//Amount
			false,		//Due Date
			false,		//Last Payment Date
			false,		//PBL ID
			false, 		//Proj. ID
			false, 		//PBL ID
			false,		//Seq. No
	
	};

	public Class getColumnClass(int columnIndex) {
		return CLASS_TYPES[columnIndex];
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return COLUMN_EDITABLE[columnIndex];
	}
	public void clear() {
		FncTables.clearTable(this);
		
	}
	
	
}