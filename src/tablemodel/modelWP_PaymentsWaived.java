package tablemodel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelWP_PaymentsWaived extends DefaultTableModel {

	private static final long serialVersionUID = 1L;

	private boolean editable = false;
	//boolean[] COLUMN_EDITABLE;

	public modelWP_PaymentsWaived() {
		initThis();
	}

	public modelWP_PaymentsWaived(boolean editable) {
		initThis();
	}

	public modelWP_PaymentsWaived(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelWP_PaymentsWaived(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelWP_PaymentsWaived(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelWP_PaymentsWaived(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelWP_PaymentsWaived(Object[][] data, Object[] columnNames) {
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
