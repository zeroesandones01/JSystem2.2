package tablemodel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class model_spotcash_lateor extends DefaultTableModel {

	private static final long serialVersionUID = -3900798827056819105L;
	private boolean editable = false;
	boolean[] COLUMNS_EDITABLE;

	public model_spotcash_lateor() {
		initGUI(); 
	}

	public model_spotcash_lateor(int rowCount, int columnCount) {
		super(rowCount, columnCount);

	}

	public model_spotcash_lateor(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);

	}

	public model_spotcash_lateor(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);

	}

	public model_spotcash_lateor(Vector data, Vector columnNames) {
		super(data, columnNames);

	}

	public model_spotcash_lateor(Object[][] data, Object[] columnNames) {
		super(data, columnNames);

	}
	
	private void initGUI() {
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
				false, 
				false, 
				false, 
				false, 
				false, 
				false
		};
	}
	
	public String[] COLUMNS = new String[] {
			"Tag", 
			"OR No", 
			"OR Date", 
			"Client Name", 
			"Unit", 

			"Particulars", 
			"Type", 
			"Amount", 
			"AR No", 
			"Officially Res.",
			
			"Bank", 
			"Branch", 
			"Date Cleared", 
			"Check No.", 
			"Check Date", 
			"Pay Rec ID", 
			"JV#"
	}; 

	public Class[] CLASS_TYPES = new Class[] {
			Boolean.class, 
			String.class,
			Date.class, 
			Object.class,
			String.class, 

			String.class, 
			String.class, 
			BigDecimal.class,  
			String.class, 
			Date.class,
			String.class,
			Object.class,
			Date.class, 
			String.class,  
			Date.class,  
			Integer.class, 
			String.class
	};
		
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
