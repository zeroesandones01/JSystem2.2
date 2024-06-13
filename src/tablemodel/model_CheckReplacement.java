package tablemodel;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class model_CheckReplacement extends DefaultTableModel {

	private static final long serialVersionUID = -4437451003818206474L;
	private boolean editable = false;
	boolean[] COLUMNS_EDITABLE;	
	
	public model_CheckReplacement() {
		InitGUI();
	}

	public model_CheckReplacement(int rowCount, int columnCount) {
		super(rowCount, columnCount);

	}

	public model_CheckReplacement(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);

	}

	public model_CheckReplacement(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);

	}

	public model_CheckReplacement(Vector data, Vector columnNames) {
		super(data, columnNames);

	}

	public model_CheckReplacement(Object[][] data, Object[] columnNames) {
		super(data, columnNames);

	}

	private void InitGUI() {
		setColumnIdentifiers(COLUMNS);
		
		COLUMNS_EDITABLE = new boolean[] {
			false,
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
			false
		};
	}
	
	public String [] COLUMNS = new String[] {
		"Check No",
		"Tag",
		"Trans. Date", 
		"Particulars", 
		"Bank",
		"Branch",
		"Check Date",
		"Account No", 
		"BRSTN", 
		"Amount",
		"Current Status",
		"Remarks", 
		"Rec ID", 
		"Sequence No."
	};
	
	public Class [] CLASS_TYPES = new Class[] {
		String.class,
		Boolean.class,
		Date.class,
		String.class,
		String.class,
		String.class,
		Date.class,
		String.class,
		String.class,
		BigDecimal.class,
		String.class,
		String.class,
		String.class,
		String.class
	};

	public Class getColumnClass(int columnIndex) {
		return CLASS_TYPES[columnIndex];
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return COLUMNS_EDITABLE[columnIndex];
	}
	
	public boolean isCellEditable(int columnIndex) {
		return COLUMNS_EDITABLE[columnIndex];
	}
	
	public void clear() {
		FncTables.clearTable(this);
	}
	
	public boolean isEditable(){
		return editable;
	}
	
	public void setEditable(boolean editable){
		this.editable = editable;
	}
}
