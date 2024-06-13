package tablemodel;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class model_hdmf_ci extends DefaultTableModel {

	private static final long serialVersionUID = -1784972535499951503L;
	private boolean editable = false;
	boolean[] COLUMNS_EDITABLE;	
	
	public model_hdmf_ci() {
		InitGUI();
	}

	public model_hdmf_ci(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		InitGUI();
	}

	public model_hdmf_ci(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		InitGUI();
	}

	public model_hdmf_ci(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		InitGUI();
	}

	public model_hdmf_ci(Vector data, Vector columnNames) {
		super(data, columnNames);
		InitGUI();
	}

	public model_hdmf_ci(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		InitGUI();
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
		"Name",
		
		"PFR No.",
		"PFR Date",
		
		"Employer",
		"Employer's Address",
		"Block",
		"Lot",
		"Loan Amount",	
		"Remarks",
		"Civil Status", 
		"Maiden Name", 
		"entity_id", 
		"proj_id", 
		"pbl_id", 
		"seq_no"
	};
		
	public Class [] CLASS_TYPES = new Class[] {
		Boolean.class, 
		
		Object.class, 
		
		String.class, 
		Date.class,
		Object.class, 
		Object.class, 
		String.class, 
		String.class,
		
		BigDecimal.class, 
		Object.class, 
		String.class, 
		String.class, 
		String.class, 
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
