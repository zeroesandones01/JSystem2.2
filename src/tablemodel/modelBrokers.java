package tablemodel;

import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class modelBrokers extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8738810102880371204L;
	
	private boolean editable = false;
	boolean[] COLUMN_EDITABLE;
	
	
	public modelBrokers() {
		initThis();
	}

	public modelBrokers(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelBrokers(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelBrokers(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelBrokers(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelBrokers(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			"Agent Name",	// 1
			"PRC No." ,		// 2
			"PRC Validity",	// 3
			"HLRUB No." ,	// 4		
			"HLURB Validity",// 5	
			"Status",		// 6	
			"Added by",		// 7	
			"Added Date",   // 8	
			"Default Check Payee"  //9
			};

	
	/**
	 * String.class = Alignment.CENTER
	 * Object.class = Alignment.LEFT
	 * BigDecimal.class = Alignment.RIGHT
	 * Integer.class = Alignment.CENTER
	 * Timestamp.class
	 */
	public Class[] CLASS_TYPES = new Class[] {			
			Object.class, 		//Agent Name
			String.class, 		//PRC No.
			Timestamp.class,	//PRC Validity	
			String.class, 		//HLRUB
			Timestamp.class,	//HLURB Validity	
			String.class, 		//Status
			String.class, 		//Added By
			Timestamp.class, 	//Added Date
			Boolean.class 		//Default Check Payee
			
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
		COLUMN_EDITABLE = new boolean[] {
				false, 		//Agent Name
				false, 		//PRC No.
				false,		//PRC Validity	
				false, 		//HLRUB
				false,		//HLURB Validity	
				false,		//Status
				false, 		//Added By
				false, 	    //Added Date
				false 	    //Default Check Payee
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
					false, 		//Agent Name
					false, 		//PRC No.
					false,		//PRC Validity	
					false, 		//HLRUB
					false,		//HLURB Validity
					false,		//Status
					false, 		//Added By
					false, 	    //Added Date
					false 	    //Default Check Payee
			};
		}else{
			COLUMN_EDITABLE = new boolean[] {
					false, 		//Agent Name
					false, 		//PRC No.
					false,		//PRC Validity	
					false, 		//HLRUB
					false,		//HLURB Validity	
					false,		//Status
					false, 		//Added By
					false, 	    //Added Date
					false 	    //Default Check Payee
			};
		}
	}

	
	
	
}
