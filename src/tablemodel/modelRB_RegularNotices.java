package tablemodel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelRB_RegularNotices extends DefaultTableModel {

	private static final long serialVersionUID = 1L;

	private boolean editable = false;
	//boolean[] COLUMN_EDITABLE;

	public modelRB_RegularNotices() {
		initThis();
	}

	public modelRB_RegularNotices(boolean editable) {
		initThis();
	}

	public modelRB_RegularNotices(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelRB_RegularNotices(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelRB_RegularNotices(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelRB_RegularNotices(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelRB_RegularNotices(Object[][] data, Object[] columnNames) {
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
			"Address", // 6
			"Loan Released Date", // 7
			"Approved Amount", // 8
			"Contract Price" // 9
	};

	Class[] CLASS_TYPES = new Class[] {
			Boolean.class, // Select
			String.class, // Client ID
			Object.class, // Client Name
			String.class, // PBL ID
			Integer.class, // Seq.
			Object.class, // Description
			Object.class, // Address
			Timestamp.class, // Loan Released Date
			BigDecimal.class, // Approved Amount
			BigDecimal.class // Contract Price
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
			false, // Due Date
			false // Amount Due
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
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
		COLUMN_EDITABLE = new Boolean[]{
				editable, // Select
				false, // Client ID
				false, // Client Name
				false, // PBL ID
				false, // Seq.
				false, // Description
				false, // Part. ID
				false, // Particular
				false, // Due Date
				false // Amount Due
		};
		this.editable = editable;
	}

}
