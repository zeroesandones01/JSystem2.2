package tablemodel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelPrintedDocs extends DefaultTableModel {
	
	private boolean editable = false;

	private static final long serialVersionUID = 9069122928516810585L;
	
	public modelPrintedDocs() {
		initThis();
	}

	public modelPrintedDocs(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelPrintedDocs(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelPrintedDocs(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelPrintedDocs(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelPrintedDocs(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}
	
	String [] COLUMNS = new String[]{
			
		"Select", //0
		"Doc ID", //1
		"Doc Desc", //2
		"Date Printed", //3 
		"Printed By", //4
		"Rec. ID" //5
		
	};
	
	Class [] CLASS_TYPES = new Class[]{

		Boolean.class, //Select
		String.class, //Doc ID
		String.class, //Doc Desc
		Timestamp.class, //Date Printed
		String.class, //Printed by
		Integer.class //Rec. ID
	};

	Boolean [] COLUMNS_EDITABLE = new Boolean[]{

		true, //Select
		false, //Doc ID
		false, //Doc Desc
		false, //Date Printed
		false, //Printed By
		false //Rec. ID
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