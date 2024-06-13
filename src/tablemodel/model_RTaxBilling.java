package tablemodel;

import java.math.BigDecimal;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class model_RTaxBilling extends DefaultTableModel{
	
	public model_RTaxBilling () {
		initThis();
	}
	
	public model_RTaxBilling(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public model_RTaxBilling(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public model_RTaxBilling(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public model_RTaxBilling(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public model_RTaxBilling(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		
	}
	
	String [] Columns = new String [] {		
			"Select", // 0
			"Client Name",	// 1		
			"Project",	// 2
			"Unit",		// 3
			"RTAX Year Covered",	// 4
			"Lot Amt Due",	// 5
			"House Amt Due",	// 6
			"Total RPT Amt Due",	// 7
			"Entity_ID", // 8
			"Proj_ID", // 9
			"PBL_ID", // 10
			"Seq_No" // 11
		
			
	};
	
	Boolean [] COLUMNS_EDITABLE = new Boolean [] {
			true,
			false,
			false,
			false,
			false,
			false,
			false,
			false,
			false,
			false,
			false,
			false
			
	};
	
	Class [] classType = new Class [] {
			Boolean.class,
			String.class,
			String.class,
			String.class,
			String.class,
			BigDecimal.class,
			BigDecimal.class,
			BigDecimal.class,
			String.class,
			String.class,
			String.class,
			String.class
	
			
			
	};
	
	
	private void initThis() {
		setColumnIdentifiers(Columns);

	}
	public Class getColumnClass(int columnIndex){
		return classType[columnIndex];
	}
	public boolean isCellEditable(int rowIndex,int columnIndex){
		return COLUMNS_EDITABLE[columnIndex];
	}
	public void clear() {
		FncTables.clearTable(this);
	}

}
