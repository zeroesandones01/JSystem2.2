package tablemodel;

import java.math.BigDecimal;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;

import Lookup._JLookup;

public class modelContractorOtherDeductions extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8738810102880371204L;
	
	private boolean editable = false;
	boolean[] COLUMN_EDITABLE;
	
	
	public modelContractorOtherDeductions() {
		initThis();
	}

	public modelContractorOtherDeductions(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelContractorOtherDeductions(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelContractorOtherDeductions(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelContractorOtherDeductions(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelContractorOtherDeductions(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			"Reference No.",// 0
			"Acct ID",		// 1
			"Acct Desc",	// 2
			//"For Liqui",	// 3
			"Amount",		// 3 Updated by Erick Bituen DCRF 1107
			"Remarks",		// 4
			//"Succeeding DP Recoup"	// 5
			"Bill No.",		// 5 Updated by Erick Bituen DCRF 1107
			"Type", 			//6 Added by Erick DCRF 1107
			"Contract No." 		//7 Added by Erick DCRF 1107
			
			};

	
	/**
	 * String.class = Alignment.CENTER
	 * Object.class = Alignment.LEFT
	 * BigDecimal.class = Alignment.RIGHT
	 * Integer.class = Alignment.CENTER
	 * Timestamp.class
	 */
	public Class[] CLASS_TYPES = new Class[] {
			_JLookup.class,     //Reference No.
			String.class, 		//Acct ID
			Object.class,		//Acct Desc
			BigDecimal.class,	//Amount
			String.class, 	//Remarks
			String.class, 	//Bill No.
			_JLookup.class,	//Type
			_JLookup.class	//Contract No.
			
			
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
		COLUMN_EDITABLE = new boolean[] {
				false, 		//Reference No.
				false, 		//Acct ID
				false,		//Acct Desc
				false,		//Amount
				false, 		//Remarks
				false, 		//Bill No.
				false,		//type
				false		//Contract No.
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
					false, 		//Reference No.
					false, 		//Acct ID
					false,		//Acct Desc
					true,		//Amount
					true, 		//Remarks
					false, 		//Bill No.p
					true,		//type
					true		//Contract No.
			};
		}else{
			COLUMN_EDITABLE = new boolean[] {
					false, 		//Reference No.
					false, 		//Acct ID
					false,		//Acct Desc
					false,		//Amount
					false, 		//Remarks
					false, 		//Bill No.
					false,		//type
					false		//Contract No.
			};
		}
	}

	
	
	
}
