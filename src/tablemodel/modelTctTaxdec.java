package tablemodel;

import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelTctTaxdec extends DefaultTableModel {

	private static final long serialVersionUID = 1L;
	
	private boolean editable = false;
	boolean[] COLUMN_EDITABLE;

	public modelTctTaxdec() {
		initThis();
	}

	public modelTctTaxdec(boolean editable) {
		initThis();
	}

	public modelTctTaxdec(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelTctTaxdec(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelTctTaxdec(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelTctTaxdec(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelTctTaxdec(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}
	
	String[] COLUMNS = new String[] {
			"Doc No", 
			"Doc Name",
			"Mother Doc No",
			"Date Created",
			"Facility", 
			"Serial No",
			"Book No",
			"Page No"
	};
	
	Class[] CLASS_TYPES = new Class[] {
			String.class, //Bond No
			Object.class, //Insurance Co. ID
			String.class, //From
			Timestamp.class,
			Object.class, //Bond No
			String.class, //Insurance Co. ID
			String.class, //From
			String.class
	};
	
	private void initThis() {
		setColumnIdentifiers(COLUMNS);
		COLUMN_EDITABLE = new boolean[] {
				false, //No.
				false, //Work Item
				false, //Quantity
				false, //No.
				false, //Work Item
				false,
				false,
				false
		};
	}
	
	public Class getColumnClass(int columnIndex) {
		return CLASS_TYPES[columnIndex];
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return COLUMN_EDITABLE[columnIndex];
	}
	
	public void clear() {
		FncTables.clearTable(this);
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
		if(editable){
			COLUMN_EDITABLE = new boolean[] {
					false, //No.
					false, //Work Item
					false, //Quantity
					false, //No.
					false, //Work Item
					false,
					false,
					false
			};
		}else{
			COLUMN_EDITABLE = new boolean[] {
					false, //No.
					false, //Work Item
					false, //Quantity
					false, //No.
					false, //Work Item
					false,
					false,
					false
			};
		}
	}

}
