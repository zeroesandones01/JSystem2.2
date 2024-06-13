package tablemodel;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelLoanReleasedIssuance extends DefaultTableModel {

	private static final long serialVersionUID = 1L;
	private boolean editable = false;

	public modelLoanReleasedIssuance() {
		initThis();
	}
	public modelLoanReleasedIssuance(boolean editable) {
		initThis();
	}

	public modelLoanReleasedIssuance(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelLoanReleasedIssuance(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelLoanReleasedIssuance(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelLoanReleasedIssuance(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelLoanReleasedIssuance(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}
	

	String[] COLUMNS = new String[] {
			"Select", 		// 0
			"Project", 			// 1
			"Client's Name", 	// 2
			"Unit", 			// 3
			"Net Proceeds", 	// 4
			"Loanable Amount", 	// 5
			"SRI % Doc", 		// 6
			"Fire", 			// 7
			"Proc. Fee", 		// 8
			"Appraisal Fee", 	// 9
			"Interim MRI", 		// 10
			"Retention Fee", 	// 11
			"1st MA", 			// 12
			"Refiling Fee", 	// 13
			"10% Retention", 	// 14
			"Right of Way",		// 15
			"Water Supply",		// 16
			"Tax Dec.", 		// 17
			"Service Fee",		//18
			"Rec_ID", 			//19
			"Entity ID", 		//20
			"Proj ID",			//21
			"Pbl ID",			//22
			"Seq no",			//23
			"Actual Date"		//24
	};

	Class[] CLASS_TYPES = new Class[] {
			Boolean.class,		//Select
			Object.class,		//Project
			Object.class,		//Client's Name
			Object.class,		//Unit
			BigDecimal.class,	//Net Proceeds
			BigDecimal.class,	//Loanable Amount
			BigDecimal.class,	//SRI % Doc
			BigDecimal.class,	//Fire
			BigDecimal.class,	//Proc. Fee
			BigDecimal.class,	//Appraisal Fee
			BigDecimal.class,	//Interim MRI
			BigDecimal.class,	//Retention Fee
			BigDecimal.class,	//1st MA
			BigDecimal.class,	//Refiling Fee
			BigDecimal.class,	//10% Retention
			BigDecimal.class,	//Right of way
			BigDecimal.class,	//Water Supply
			BigDecimal.class,	//Tax Declaration
			BigDecimal.class,  //Service Fee
			Integer.class,		//Rec_ID
			String.class, 		//Entity ID
			String.class, 		//Proj ID
			String.class, 		//Pbl ID
			Integer.class,		//Seq No
			Date.class			//Actual Date 
	};
	
	Boolean [] COLUMNS_EDITABLE = new Boolean[]{
			true,		//Select
			false,		//Project
			false,		//Client's Name
			false,		//Unit
			false,	//Net Proceeds
			false,	//Loanable Amount
			true,	//SRI % Doc
			true,	//Fire
			true,	//Proc. Fee
			true,	//Appraisal Fee
			true,	//Interim MRI
			true,	//Retention Fee
			true,	//1st MA
			true,	//Refiling Fee
			true,	//10% Retention
			true,	//Right of way
			true,	//Water Supply
			true,	//Tax Declaration
			true, 	//Service Fee
			false, 	//Rec_ID
			false, 	//Entity ID
			false, 	//Proj ID
			false, 	//Pbl ID
			false, 	//Seq No
			true 	//Actual Date 
			
	}; 
		

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
	}

	public Class getColumnClass(int columnIndex) {
		return CLASS_TYPES[columnIndex];
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return COLUMNS_EDITABLE[columnIndex];
	}

	public void clear() {
		FncTables.clearTable(this);
	}

	public boolean isEditable() {
		return editable;
	}

}
