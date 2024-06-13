package tablemodel;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class model_sms_pool extends DefaultTableModel {

	private static final long serialVersionUID = -9093959447126104612L;
	private boolean editable = false;
	boolean[] COLUMNS_EDITABLE;	
	
	public model_sms_pool() {
		InitGUI(); 
	}

	public model_sms_pool(int rowCount, int columnCount) {
		super(rowCount, columnCount);

	}

	public model_sms_pool(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);

	}

	public model_sms_pool(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);

	}

	public model_sms_pool(Vector data, Vector columnNames) {
		super(data, columnNames);

	}

	public model_sms_pool(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		// TODO Auto-generated constructor stub
	}
	
	private void InitGUI() {
		setColumnIdentifiers(COLUMNS);
		
		COLUMNS_EDITABLE = new boolean[] {
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
		"#",
		"ID",
		"From",
		"Recipient",
		"Mobile No.",
		"Status", 
		"Time", 
		"Date"
	};
	
	public Class [] CLASS_TYPES = new Class[] {
		String.class,
		String.class,
		String.class,
		Object.class,
		String.class,
		String.class,
		Timestamp.class,
		Date.class
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
