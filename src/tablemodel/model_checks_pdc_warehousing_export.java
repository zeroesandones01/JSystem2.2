package tablemodel;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class model_checks_pdc_warehousing_export extends DefaultTableModel {

	private static final long serialVersionUID = -7362089553132374142L;
	private boolean editable = false;
	boolean[] COLUMNS_EDITABLE;	

	public model_checks_pdc_warehousing_export() {
		InitGUI(); 
	}

	public model_checks_pdc_warehousing_export(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		
	}

	public model_checks_pdc_warehousing_export(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		
	}

	public model_checks_pdc_warehousing_export(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		
	}

	public model_checks_pdc_warehousing_export(Vector data, Vector columnNames) {
		super(data, columnNames);
		
	}

	public model_checks_pdc_warehousing_export(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		
	}

	private void InitGUI() {
		setColumnIdentifiers(COLUMNS);
		
		COLUMNS_EDITABLE = new boolean[] {
			true,
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
		"Tag", 
		"BRSTN",
		"Account No.",
		"Check No.",
		"Amount",
		"Check Date",
		"Due Date",
		"Remarks 1",
		"Remarks 2"
	};
	
	public Class [] CLASS_TYPES = new Class[] {
		Boolean.class,
		String.class,
		String.class,
		String.class,
		BigDecimal.class,
		Date.class, 
		Date.class, 
		Object.class, 
		String.class,
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
