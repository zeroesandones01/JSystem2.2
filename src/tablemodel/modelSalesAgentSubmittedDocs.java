package tablemodel;

import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Lookup._JLookup;

public class modelSalesAgentSubmittedDocs extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8738810102880371204L;
	
	private boolean editable = false;
	boolean[] COLUMN_EDITABLE;
	
	
	public modelSalesAgentSubmittedDocs() {
		initThis();
	}

	public modelSalesAgentSubmittedDocs(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelSalesAgentSubmittedDocs(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelSalesAgentSubmittedDocs(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelSalesAgentSubmittedDocs(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelSalesAgentSubmittedDocs(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			"Rec ID",		// 1
			"Doc ID",		// 2
			"Doc Name",		// 3
			"Date Submitted" ,	// 4
			"Received By" ,	// 5
			"Remarks" ,		// 6
			"Status" 		// 7	
			};

	
	/**
	 * String.class = Alignment.CENTER
	 * Object.class = Alignment.LEFT
	 * BigDecimal.class = Alignment.RIGHT
	 * Integer.class = Alignment.CENTER
	 * Timestamp.class
	 */
	public Class[] CLASS_TYPES = new Class[] {
			String.class, 		//Rec ID
			_JLookup.class, 	//Doc ID
			Object.class, 		//Doc Name
			Timestamp.class, 	//Date Submitted
			String.class, 		//Received By
			String.class, 		//Remarks
			_JLookup.class		//Status
			
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
		COLUMN_EDITABLE = new boolean[] {
				false, 		//Rec ID
				false, 		//Doc ID
				false, 		//Doc Name
				false, 		//Date Submitted
				false, 		//Received By
				false, 		//Remarks
				false		//Status
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
					false, 		//Rec ID
					false, 		//Doc ID
					false, 		//Doc Name
					false, 		//Date Submitted
					false, 		//Received By
					true, 		//Remarks
					false		//Status
			};
		}else{
			COLUMN_EDITABLE = new boolean[] {
					false, 		//Rec ID
					false, 		//Doc ID
					false, 		//Doc Name
					false, 		//Date Submitted
					false, 		//Received By
					false, 		//Remarks
					false		//Status
			};
		}
	}

	
	
	
}
