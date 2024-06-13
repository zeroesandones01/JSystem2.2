package tablemodel;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class model_checks_pdc_warehousing extends DefaultTableModel {

	private static final long serialVersionUID = 6389661872444561346L;
	private boolean editable = false;
	boolean[] COLUMNS_EDITABLE;	
	
	public model_checks_pdc_warehousing() {
		InitGUI();
	}

	public model_checks_pdc_warehousing(int rowCount, int columnCount) {
		super(rowCount, columnCount);

	}

	public model_checks_pdc_warehousing(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);

	}

	public model_checks_pdc_warehousing(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);

	}

	public model_checks_pdc_warehousing(Vector data, Vector columnNames) {
		super(data, columnNames);

	}

	public model_checks_pdc_warehousing(Object[][] data, Object[] columnNames) {
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
			false,
			false, 
			false, 
			true, 
			false
		};
	}
	
	public String [] COLUMNS = new String[] {
		"Tag", 
		"Name",
		"Unit", 
		"Check No",
		"Particulars", 
		"Amount",
		"Date", 
		
		"Bank", 
		"Branch", 
		"Check Date", 
		"Account No", 
		"BRSTN", 
		"ref_no"
	};
	
	public Class [] CLASS_TYPES = new Class[] {
		Boolean.class, 
		Object.class,
		String.class,
		String.class,
		String.class,
		BigDecimal.class,
		Date.class, 
		
		String.class,
		String.class,
		Date.class, 
		String.class,
		String.class,
		Integer.class
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
