package tablemodel;

import java.math.BigDecimal;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelHoldingAndReservation_PaySchemeDetails extends DefaultTableModel {

	private static final long serialVersionUID = 1L;
	
	private boolean editable = false;
	//boolean[] COLUMN_EDITABLE;

	public modelHoldingAndReservation_PaySchemeDetails() {
		initThis();
	}

	public modelHoldingAndReservation_PaySchemeDetails(boolean editable) {
		initThis();
	}

	public modelHoldingAndReservation_PaySchemeDetails(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelHoldingAndReservation_PaySchemeDetails(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelHoldingAndReservation_PaySchemeDetails(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelHoldingAndReservation_PaySchemeDetails(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelHoldingAndReservation_PaySchemeDetails(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}
	
	String[] COLUMNS = new String[] {
			"Seq.", // 0
			"Part. ID", // 1
			"Particular", // 2
			"Rate", // 3
			"Amount", // 4
			"Term" // 5
	};
	
	Class[] CLASS_TYPES = new Class[] {
			Integer.class, // Seq.
			String.class, // Part. ID
			Object.class, // Particular
			BigDecimal.class, // Rate
			BigDecimal.class, // Amount
			Integer.class // Term
	};
	
	Boolean[] COLUMNS_EDITABLE = new Boolean[] {
			false, // Seq.
			false, // Part. ID
			false, // Particular
			true, // Rate
			false, // Amount
			true // Term
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

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

}
