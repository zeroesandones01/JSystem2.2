package tablemodel;

import java.util.Date;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import DateChooser._JDateChooser;
import Functions.FncTables;

public class model_hdmf_email extends DefaultTableModel {

	private static final long serialVersionUID = -2947415146206097113L;
	private boolean editable = false;
	boolean[] COLUMNS_EDITABLE;	
	
	public model_hdmf_email() {
		InitGUI();
	}

	public model_hdmf_email(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		
	}

	public model_hdmf_email(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		
	}

	public model_hdmf_email(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		
	}

	public model_hdmf_email(Vector data, Vector columnNames) {
		super(data, columnNames);
		
	}

	public model_hdmf_email(Object[][] data, Object[] columnNames) {
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
			true,
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
		"Unit",
		"Name",	
		"Class Type",
		"Date Qualified",
		"COE Date",
		"entity_id",
		"proj_id",
		"pbl_id",
		"seq_no",
		"email", 
		"employer", 
		"date hired", 
		"position", 
		"Date Sent"
	};
	
	public Class [] CLASS_TYPES = new Class[] {
			Boolean.class,
			String.class,
			Object.class,
			String.class,
			Date.class,
			_JDateChooser.class, 
			String.class,
			String.class,
			String.class,
			Integer.class, 
			String.class, 
			Object.class,
			Date.class,
			String.class,
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