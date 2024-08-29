package tablemodel;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import DateChooser._JDateChooser;
import Functions.FncTables;

public class modelRetentionFeeIssuance extends DefaultTableModel {

	private static final long serialVersionUID = 1L;
	private boolean editable = false;

	public modelRetentionFeeIssuance() {
		initThis();
	}
	public modelRetentionFeeIssuance(boolean editable) {
		initThis();
	}

	public modelRetentionFeeIssuance(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelRetentionFeeIssuance(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelRetentionFeeIssuance(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelRetentionFeeIssuance(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelRetentionFeeIssuance(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}
	

	String[] COLUMNS = new String[] {
			"Select", 			// 0
			"Client's Name",	// 1
			"Project",			// 2
			"Unit", 			// 3
			"Part ID",			// 4
			"Particular", 		// 5
			"Actual Date",		// 6
			"Amount", 			// 7
			"Receipt No.",		// 8
			"Client Seq. No.",	// 9
			"Entity ID", 		// 10
			"Proj. ID", 		// 11
			"PBL ID", 			// 12
			"Seq No."			// 13
	};

	Class[] CLASS_TYPES = new Class[] {
			Boolean.class,		// Select
			Object.class,		// Client's Name
			Object.class,		// Project
			Object.class,		// Unit
			String.class,		// Part ID
			String.class,		// Particular
			_JDateChooser.class,// Actual Date
			BigDecimal.class,	// Amount
			String.class,		// Receipt No
			String.class,		// Client Seq. No.
			String.class, 		//Entity ID
			String.class, 		//Proj ID
			String.class, 		//Pbl ID
			Integer.class		//Seq No

	};
	
	Boolean [] COLUMNS_EDITABLE = new Boolean[]{
			true,		//Select
			false,		//Client's Name
			false,		//Project
			false,		//Unit
			false,		// Part ID
			false,		// Particular
			true,		// Actual Date
			true,		// Amount
			false,		// Receipt No
			false,		// Client Seq. No.
			false, 		//Entity ID
			false, 		//Proj ID
			false, 		//Pbl ID
			false		//Seq No
		
			
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
