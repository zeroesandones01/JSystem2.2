package tablemodel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelRB_RegularBilling extends DefaultTableModel {

	private static final long serialVersionUID = 1L;

	private boolean editable = false;
	//boolean[] COLUMN_EDITABLE;

	public modelRB_RegularBilling() {
		initThis();
	}

	public modelRB_RegularBilling(boolean editable) {
		initThis();
	}

	public modelRB_RegularBilling(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelRB_RegularBilling(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelRB_RegularBilling(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelRB_RegularBilling(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelRB_RegularBilling(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}

	String[] COLUMNS = new String[] {
			"Select", // 0
			"Client ID", // 1
			"Client Name", // 2
			"PBL ID", // 3
			"Seq.", // 4
			"Description", // 5
			"Part. ID", // 6
			"Particular", // 7
			"Term", // 8
			"Default Date", // 9
			"Bill From", // 10
			"Bill To", // 11
			"Amount Due", // 12
			"Balance", // 13
			"Last Bill", // 14
			"Next Bill", // 15
			"Months PD", // 16
			"Past Due" // 17
	};

	Class[] CLASS_TYPES = new Class[] {
			Boolean.class, // Select
			String.class, // Client ID
			Object.class, // Client Name
			String.class, // PBL ID
			Integer.class, // Seq.
			Object.class, // Description
			String.class, // Part. ID
			Object.class, // Particular
			Integer.class, // Term
			Timestamp.class, // Default Date
			Timestamp.class, // Bill From
			Timestamp.class, // Bill To
			BigDecimal.class, // Amount Due
			BigDecimal.class, // Balance
			Timestamp.class, // Last Bill
			Timestamp.class, // Next Bill
			Integer.class, // Months PD
			Boolean.class, // Past Due
	};

	Boolean[] COLUMN_EDITABLE = new Boolean[]{
			true, // Select
			false, // Client ID
			false, // Client Name
			false, // PBL ID
			false, // Seq.
			false, // Description
			false, // Part. ID
			false, // Particular
			false, // Term
			false, // Default Date
			false, // Bill From
			false, // Bill To
			false, // Amount Due
			false, // Balance
			false, // Last Bill
			false, // Next Bill
			false, // Past Due
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
	}

	public Class getColumnClass(int columnIndex) {
		return CLASS_TYPES[columnIndex];
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if(isEditable()){
			return COLUMN_EDITABLE[columnIndex];
		}else{
			return false;
		}
	}

	public void clear() {
		FncTables.clearTable(this);
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		COLUMN_EDITABLE = new Boolean[]{
				editable, // Select
				false, // Client ID
				false, // Client Name
				false, // PBL ID
				false, // Seq.
				false, // Description
				false, // Part. ID
				false, // Particular
				false, // Term
				false, // Default Date
				false, // Bill From
				false, // Bill To
				false, // Amount Due
				false, // Balance
				false, // Last Bill
				false, // Next Bill
				false, // Past Due
		};
		this.editable = editable;
	}

}
