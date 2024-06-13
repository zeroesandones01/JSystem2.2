package tablemodel;

import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelDocumentsMaintenance_AddendumDocs extends DefaultTableModel {

	private static final long serialVersionUID = 1L;


	public modelDocumentsMaintenance_AddendumDocs() {
		initThis();
	}

	public modelDocumentsMaintenance_AddendumDocs(boolean editable) {
		initThis();
	}

	public modelDocumentsMaintenance_AddendumDocs(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelDocumentsMaintenance_AddendumDocs(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelDocumentsMaintenance_AddendumDocs(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelDocumentsMaintenance_AddendumDocs(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelDocumentsMaintenance_AddendumDocs(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}

	String[] COLUMNS = new String[] {
			"ID", // 0
			"Name", // 1
			"Reg. Emp. (Reg.)", // 2
			"Reg. Emp. (Spe.)", // 3
			"Self Emp. (Reg.)", // 4
			"Self Emp. (Spe.)", // 5
			"OFW Emp. (Reg.)", // 6
			"OFW Emp. (Spe.)", // 7
			"Orig. Copies", // 8
			"Photocopy", // 9
			"Remarks", // 10
			"Created By", // 11
			"Date Created", // 12
			"Updated By", // 13
			"Date Updated" // 14
	};

	Class[] CLASS_TYPES = new Class[] {
			Integer.class, // ID
			Object.class, // Name
			String.class, // Reg. Emp. (Reg.)
			String.class, // Reg. Emp. (Spe.)
			String.class, // Self Emp. (Reg.)
			String.class, // Self Emp. (Spe.)
			String.class, // OFW Emp. (Reg.)
			String.class, // OFW Emp. (Spe.)
			Integer.class, // Orig. Copies
			Integer.class, // Photocopy
			Object.class, // Remarks
			String.class, // Created By
			Timestamp.class, // Date Created
			String.class, // Updated By
			Timestamp.class // Date Updated
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
	}

	public Class getColumnClass(int columnIndex) {
		return CLASS_TYPES[columnIndex];
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	public void clear() {
		FncTables.clearTable(this);
	}

}
