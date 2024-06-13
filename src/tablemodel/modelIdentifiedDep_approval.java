package tablemodel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class modelIdentifiedDep_approval	 extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8738810102880371204L;
	
	private boolean editable = false;
	boolean[] COLUMN_EDITABLE;
	
	
	public modelIdentifiedDep_approval() {
		initThis();
	}

	public modelIdentifiedDep_approval(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelIdentifiedDep_approval(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelIdentifiedDep_approval(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelIdentifiedDep_approval(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelIdentifiedDep_approval(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			"Tag" ,					// 1
			"Client" ,				// 2
			"Project", 				// 3
			"Unit", 				// 4
			"Sales Div-Grp", 		// 5
			"Payment", 				// 6
			"Amount", 				// 7
			"Bank", 				// 8
			"Account No.", 			// 9
			"Date Deposited", 		// 10
			"Date Identified", 		// 11
			"Created by", 			// 12
			"Identified by",		// 13
			"Record ID",  			// 14
			"Entity ID", 
			"PBL ID", 
			"Seq No", 
			"Co. ID", 
			"Proj. ID", 
			"Unit ID"
			
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
			Object.class, 		//Client
			String.class, 		//Project
			String.class,		//Unit
			String.class,		//Sales Div-Grp
			String.class,		//Payment
			BigDecimal.class,	//Amount
			String.class,		//Bank
			String.class, 		//Account No.
			Timestamp.class, 	//Date Deposited 
			Timestamp.class, 	//Date Identified
			String.class, 		//Created by
			String.class, 		//Identified by
			String.class,  		//Record ID
			String.class, 		//Entity ID
			String.class, 		// PBL ID
			Integer.class, 		//Seq no
			String.class, 		//CO ID
			String.class,		//Proj ID
			String.class		//Unit ID
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
		COLUMN_EDITABLE = new boolean[] {
				true, 		//Tag
				false, 		//Client
				false, 		//Project
				false,		//Unit
				false,		//Sales Div-Grp
				false,		//Payment
				false,		//Amount
				false,		//Bank
				false, 		//Account No.
				false, 		//Date Deposited 
				false, 		//Date Identified
				false, 		//Created by
				false, 		//Identified by
				false, 		//Record ID
				false, 		//entity_id
				false, 		//pbl id
				false, 		//seq no
				false, 		//co id
				false, 		//proj_id
				false, 		//unit_id
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
					false, 		//Client
					false, 		//Project
					false,		//Unit
					false,		//Sales Div-Grp
					false,		//Payment
					false,		//Amount
					false,		//Bank
					false, 		//Account No.
					false, 		//Date Deposited 
					false, 		//Date Identified
					false, 		//Created by
					false, 		//Identified by
					false 		//Record ID
			};
		}else{
			COLUMN_EDITABLE = new boolean[] {
					false, 		//Tag
					false, 		//Client
					false, 		//Project
					false,		//Unit
					false,		//Sales Div-Grp
					false,		//Payment
					false,		//Amount
					false,		//Bank
					false, 		//Account No.
					false, 		//Date Deposited 
					false, 		//Date Identified
					false, 		//Created by
					false, 		//Identified by
					false 		//Record ID
			};
		}
	}

	
	
	
}
