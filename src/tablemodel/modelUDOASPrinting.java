package tablemodel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelUDOASPrinting extends DefaultTableModel{

	private static final long serialVersionUID = 1L;

	private boolean editable = false;


	public modelUDOASPrinting() {
		initThis();
	}
	public modelUDOASPrinting(int rowCount,
			int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelUDOASPrinting(Vector columnNames,
			int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelUDOASPrinting(Object[] columnNames,
			int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelUDOASPrinting(Vector data,
			Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelUDOASPrinting(Object[][] data,
			Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}
	
	String [] COLUMNS = new String []{
		
		"Tag", //0
		"Company", //1
		"Buyer Type", //2
		"House Model", //3
		"Client Name", //4
		"Project", //5
		"Unit", //6
		"NSP",  //7
		"Turn Over Date", //8
		"Date Full Settled", //9 
		"TCOST Amt", //10
		"Entity ID", //11
		"Proj. ID", //12
		"PBL ID", //13
		"Seq. No" //14
		
	};
	
	Class [] CLASS_TYPES = new Class []{
		
		Boolean.class, //Tag	
		String.class, //Company
		String.class, //Buyer Type
		String.class, //House Model
		String.class, //Client Name
		String.class, //Project
		String.class, //Unit
		BigDecimal.class, //NSP
		Timestamp.class, //Turn Over Date
		Timestamp.class, //Date Full Settled
		BigDecimal.class, //TCOST Amt
		String.class, //Entity ID
		String.class, //Proj. ID
		String.class, //PBL ID
		Integer.class //Seq. No
	};
	
	Boolean [] COLUMNS_EDITABLE = new Boolean []{
		
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
