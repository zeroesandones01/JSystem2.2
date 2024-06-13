package tablemodel;

import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class modelComm_agentCommQualifier	 extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8738810102880371204L;
	
	private boolean editable = false;
	boolean[] COLUMN_EDITABLE;
	
	
	public modelComm_agentCommQualifier() {
		initThis();
	}

	public modelComm_agentCommQualifier(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelComm_agentCommQualifier(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelComm_agentCommQualifier(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelComm_agentCommQualifier(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelComm_agentCommQualifier(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			"Cond No." ,		// 1
			"Description" ,		// 2
			"Qualified" ,		// 3
			"Date Qualified" 	// 4
			};

	
	/**
	 * String.class = Alignment.CENTER
	 * Object.class = Alignment.LEFT
	 * BigDecimal.class = Alignment.RIGHT
	 * Integer.class = Alignment.CENTER
	 * Timestamp.class
	 */
	public Class[] CLASS_TYPES = new Class[] {
			String.class, 		//Cond No.
			String.class, 		//Description
			String.class,		//Qualified
			Timestamp.class, 	//Date Qualified
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
		COLUMN_EDITABLE = new boolean[] {
				false, 	//Cond. no
				false, 	//Description
				false,	//Qualified
				false 	//Date Qualified
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
					false, 	//Cond. no
					false, 	//Description
					false,	//Qualified
					false 	//Date Qualified
			};
		}else{
			COLUMN_EDITABLE = new boolean[] {
					false, 	//Cond. no
					false, 	//Description
					false,	//Qualified
					false 	//Date Qualified
			};
		}
	}

	
	
	
}
