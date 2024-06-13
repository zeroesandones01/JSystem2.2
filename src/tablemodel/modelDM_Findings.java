package tablemodel;

import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelDM_Findings extends DefaultTableModel {

	private static final long serialVersionUID = 1L;

	private boolean editable = false;
	//boolean[] COLUMN_EDITABLE;

	public modelDM_Findings() {
		initThis();
	}

	public modelDM_Findings(boolean editable) {
		initThis();
	}

	public modelDM_Findings(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelDM_Findings(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelDM_Findings(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelDM_Findings(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelDM_Findings(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}

	String[] COLUMNS = new String[] {
			"Complied", // 0
			"Complied", //
			"Findings", // 1
			"Eval. Date", // 2
			"Evaluator", // 3
			"Date OK", // 4
			"OK By", // 5
			"Rec. ID" // 6
	};

	Class[] CLASS_TYPES = new Class[] {
			Boolean.class, // Complied
			Boolean.class, //
			Object.class, // Findings
			Timestamp.class, // Eval. Date
			Object.class, // Evaluator
			Timestamp.class, // Date OK
			Object.class, // OK By
			String.class // Rec. ID
	};

	Boolean[] COLUMN_EDITABLE = new Boolean[]{
			false, // Complied
			false, //
			false, // Findings
			false, // Eval. Date
			false, // Evaluator
			false, // Date OK
			false, // OK By
			false // Rec. ID
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
	}

	public Class getColumnClass(int columnIndex) {
		return CLASS_TYPES[columnIndex];
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		Timestamp complied = (Timestamp) this.getValueAt(rowIndex, 5);
		
		if(complied != null){
			return false;
		}else{
			return COLUMN_EDITABLE[columnIndex];
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
				editable, // Complied
				false, //
				editable, // Findings
				false, // Eval. Date
				false, // Evaluator
				false, // Date OK
				false, // OK By
				false // Rec. ID
		};

		this.editable = editable;
	}

}
