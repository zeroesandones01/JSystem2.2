package tablemodel;

import java.math.BigDecimal;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelCARD_PmtsWaived extends DefaultTableModel {

	private static final long serialVersionUID = 1L;

	private boolean editable = false;
	//boolean[] COLUMN_EDITABLE;

	public modelCARD_PmtsWaived() {
		initThis();
	}

	public modelCARD_PmtsWaived(boolean editable) {
		initThis();
	}

	public modelCARD_PmtsWaived(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelCARD_PmtsWaived(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelCARD_PmtsWaived(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelCARD_PmtsWaived(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelCARD_PmtsWaived(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}

	String[] COLUMNS = new String[] {
		//"%", //0	
		"Amount", //0
		"Particulars", //1
		"Remarks" //2
	};

	Class[] CLASS_TYPES = new Class[] {
		
		//Object.class, //%
		BigDecimal.class, //Amount
		String.class, //Particulars
		Object.class //Remarks
		
	};

	Boolean[] COLUMN_EDITABLE = new Boolean[]{
			
		//false, //% 
		false, //Amount
		false, //Particulars
		false //Remarks
		
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
	}

	public Class getColumnClass(int columnIndex) {
		return CLASS_TYPES[columnIndex];
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return COLUMN_EDITABLE[columnIndex];
		//return false;
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
