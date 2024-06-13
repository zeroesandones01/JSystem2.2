package tablemodel;

import java.util.Date;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class model_sms_batch_sending extends DefaultTableModel {

	private static final long serialVersionUID = -5630252945650501967L;
	private boolean editable = false;
	boolean[] COLUMNS_EDITABLE;	

	public model_sms_batch_sending() {
		InitGUI(); 
	}

	public model_sms_batch_sending(int rowCount, int columnCount) {
		super(rowCount, columnCount);

	}

	public model_sms_batch_sending(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);

	}

	public model_sms_batch_sending(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);

	}

	public model_sms_batch_sending(Vector data, Vector columnNames) {
		super(data, columnNames);

	}

	public model_sms_batch_sending(Object[][] data, Object[] columnNames) {
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
				false,
				false
			};
	}
	
	public String [] COLUMNS = new String[] {
		"Tag",
		"Name",
		"Proj",
		
		"Unit",
		"Mobile No.",
		"Sent",

		"batch", 
		"entity_id",	
		"proj_id",	
		"pbl_id",	
		"seq_no",
		"due_date", 
		"msg_id"
	};
	
	public Class [] CLASS_TYPES = new Class[] {
		Boolean.class,
		Object.class, 
		String.class,
		
		String.class, 
		Object.class, 
		String.class, 
		
		String.class, 
		String.class,
		String.class,
		String.class,
		String.class, 
		Date.class, 
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
