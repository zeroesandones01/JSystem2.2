package tablemodel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class modelComm_agentCA_list	 extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8738810102880371204L;
	
	private boolean editable = false;
	boolean[] COLUMN_EDITABLE;
	
	
	public modelComm_agentCA_list() {
		initThis();
	}

	public modelComm_agentCA_list(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelComm_agentCA_list(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelComm_agentCA_list(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelComm_agentCA_list(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelComm_agentCA_list(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			"Co" ,			// 1
			"CA RPLF No.", 			// 2
			"RPLF Date", 		// 3
			"CA Amt.", 			// 4
			"Liq. Amt.", 	// 5
			"O/S CA", 		// 6
			"Type", 		// 7
			"Remarks" 		// 8
			};

	
	/**
	 * String.class = Alignment.CENTER
	 * Object.class = Alignment.LEFT
	 * BigDecimal.class = Alignment.RIGHT
	 * Integer.class = Alignment.CENTER
	 * Timestamp.class
	 */
	public Class[] CLASS_TYPES = new Class[] {
			String.class, 		//Co
			String.class,		//CA
			Timestamp.class, 	//Date
			BigDecimal.class, 	//CA
			BigDecimal.class, 	//CA Liq
			BigDecimal.class, 	//O/S CA
			String.class, 		//Type
			String.class 		//Remarks
			
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
		COLUMN_EDITABLE = new boolean[] {
				false, 	//Co
				false,	//CA
				false, 	//Date
				false, 	//CA
				false, 	//CA Liq
				false, 	//O/S CA
				false, 	//Type
				false 	//Remarks
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
					false, 	//Co
					false,	//CA
					false, 	//Date
					false, 	//CA
					false, 	//CA Liq
					false, 	//O/S CA
					false, 	//Type
					false 	//Remarks
			};
		}else{
			COLUMN_EDITABLE = new boolean[] {
					false, 	//Co
					false,	//CA
					false, 	//Date
					false, 	//CA
					false, 	//CA Liq
					false, 	//O/S CA
					false, 	//Type
					false 	//Remarks
			};
		}
	}

	
	
	
}
