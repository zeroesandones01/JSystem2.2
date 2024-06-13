package tablemodel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class modelComm_agentCA_process	 extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8738810102880371204L;
	
	private boolean editable = false;
	boolean[] COLUMN_EDITABLE;
	
	
	public modelComm_agentCA_process() {
		initThis();
	}

	public modelComm_agentCA_process(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelComm_agentCA_process(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelComm_agentCA_process(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelComm_agentCA_process(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelComm_agentCA_process(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			"Co" ,			// 1
			"CA RPLF No.", 	// 2
			"Line No.", 	// 3
			"Payee", 		// 4
			"RPLF Date", 	// 5
			"CA Amt.", 		// 6
			"Liq. Amt.", 	// 7
			"for Liq. Amt.",// 8
			"O/S CA", 		// 9
			"Type", 		// 10
			"Remarks", 		// 11
			"Exp Acct ID", 	// 12
			"CA Acct ID", 	// 13
			"Tax Amt", 		// 14
			"CV No." 		// 15
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
			Integer.class,		//Line No.
			String.class,		//Payee
			Timestamp.class, 	//Date
			BigDecimal.class, 	//CA
			BigDecimal.class, 	//CA Liq
			BigDecimal.class, 	//For Liq
			BigDecimal.class, 	//O/S CA
			String.class, 		//Type
			String.class, 		//Remarks
			String.class, 		//Exp Acct ID
			String.class, 		//CA Acct ID
			BigDecimal.class, 	//Tax
			String.class		//CV No.
			
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
		COLUMN_EDITABLE = new boolean[] {
				false, 	//Co
				false,	//CA
				false,	//Line
				false,	//Payee
				false, 	//Date
				false, 	//CA
				false, 	//CA Liq
				false, 	//for Liq
				false, 	//O/S CA
				false, 	//Type
				false, 	//Remarks
				false, 	//Exp Acct ID
				false, 	//CA Acct ID
				false, 	//tax
				false 	//CV No.
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
					false,	//Line
					false,	//Payee
					false, 	//Date
					false, 	//CA
					false, 	//CA Liq
					true, 	//for Liq
					false, 	//O/S CA
					false, 	//Type
					false, 	//Remarks
					false, 	//Exp Acct ID
					false, 	//CA Acct ID
					false, 	//tax
					false 	//CV No.
			};
		}else{
			COLUMN_EDITABLE = new boolean[] {
					false, 	//Co
					false,	//CA
					false,	//Line
					false,	//Payee
					false, 	//Date
					false, 	//CA
					false, 	//CA Liq
					false, 	//for Liq
					false, 	//O/S CA
					false, 	//Type
					false, 	//Remarks
					false, 	//Exp Acct ID
					false, 	//CA Acct ID
					false, 	//tax
					false 	//CV No.
			};
		}
	}

	
	
	
}
