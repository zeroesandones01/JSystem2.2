package tablemodel;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;
import Lookup._JLookup;

public class modelRetFeeOnline extends DefaultTableModel {

	private static final long serialVersionUID = 3229976349677378295L;
	private boolean editable = false;

	public modelRetFeeOnline() {
		initThis();
	}

	public modelRetFeeOnline(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		// TODO Auto-generated constructor stub
	}

	public modelRetFeeOnline(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		// TODO Auto-generated constructor stub
	}

	public modelRetFeeOnline(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		// TODO Auto-generated constructor stub
	}

	public modelRetFeeOnline(Vector data, Vector columnNames) {
		super(data, columnNames);
		// TODO Auto-generated constructor stub
	}

	public modelRetFeeOnline(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		// TODO Auto-generated constructor stub
	}
	
	String [] COLUMNS = new String [] {
			"Name", 
			"Unit", 
			"Amount", 
			"Payable EPEB", 
			"Balance EPEB", 
			"Payable RetFee1", 
			"Balance RetFee1", 
			"Payable RetFee2", 
			"Balance RetFee2", 
			"Total Retention", 
			"Entity ID", 
			"Proj. ID", 
			"PBL ID", 
			"Seq No."
		};
			
	Class [] CLASS_TYPES = new Class []{
			_JLookup.class, 
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
			Integer.class
	};
	
	Boolean [] COLUMNS_EDITABLE = new Boolean [] {
			false, 
			false, 
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
