package tablemodel;

import java.math.BigDecimal;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;
import Lookup._JLookup;

public class modelRetFeeMultiPayments extends DefaultTableModel {
	
	private boolean editable = false;
	
	public modelRetFeeMultiPayments() {
		initThis();
	}
	
	public modelRetFeeMultiPayments(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		// TODO Auto-generated constructor stub
	}

	public modelRetFeeMultiPayments(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		// TODO Auto-generated constructor stub
	}

	public modelRetFeeMultiPayments(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		// TODO Auto-generated constructor stub
	}

	public modelRetFeeMultiPayments(Vector data, Vector columnNames) {
		super(data, columnNames);
		// TODO Auto-generated constructor stub
	}

	public modelRetFeeMultiPayments(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		// TODO Auto-generated constructor stub
	}
	
	String [] COLUMNS = new String [] {
			"Name", 	
			"Unit", 	
			"Part ID",
			"Particular", 
			"Amount", 
			"Payable RetFee1", 
			"Balance RetFee1", 
			"Payable RetFee2.1", 
			"Balance RetFee2.1", 
			"Payable RetFee2.2", 
			"Balance RetFee2.2", 
			"Total Retention", 
			"Entity ID", 
			"Proj. ID", 
			"PBL ID", 
			"Seq No."
		};
			
	Class [] CLASS_TYPES = new Class []{
			String.class,
			String.class,
			String.class,
			String.class,
			BigDecimal.class,
			BigDecimal.class,
			BigDecimal.class,
			BigDecimal.class,
			BigDecimal.class,
			BigDecimal.class,
			BigDecimal.class,
			BigDecimal.class,
			String.class, 
			String.class, 
			String.class, 
			String.class
	};
	
	Boolean [] COLUMNS_EDITABLE = new Boolean [] {
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
			false,
			false,
			false,
			false,
			false
			
	};

	private void initThis(){
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

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

}
