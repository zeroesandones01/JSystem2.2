package tablemodel;

import java.util.Date;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import DateChooser._JDateChooser;
import Functions.FncTables;

public class model_hdmf_msvs_reverified extends DefaultTableModel {

	private static final long serialVersionUID = -2942010768193493193L;
	private boolean editable = false;

	public model_hdmf_msvs_reverified() {
		initThis();
	}

	public model_hdmf_msvs_reverified(int rowCount, int columnCount) {
		super(rowCount, columnCount);

	}

	public model_hdmf_msvs_reverified(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);

	}

	public model_hdmf_msvs_reverified(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);

	}

	public model_hdmf_msvs_reverified(Vector data, Vector columnNames) {
		super(data, columnNames);

	}

	public model_hdmf_msvs_reverified(Object[][] data, Object[] columnNames) {
		super(data, columnNames);

	}

	String [] COLUMNS = new String [] {
		"Tag", 
		
		"Phase", 
		"Block",  
		"Lot", 
		"Project",
		
		"Last Name", 
		"First Name", 
		"Middle Name", 
		
		"Approved Date",
		"Received Date", 
		
		"entity_id", 
		"projcode", 
		"pbl_id", 
		"seq_no"
	};
		
	Class [] CLASS_TYPES = new Class []{
		Boolean.class, 

		String.class, 
		String.class, 
		String.class, 
		String.class,
		
		String.class, 
		String.class, 
		String.class,
		
		_JDateChooser.class,   
		_JDateChooser.class,  

		String.class, 
		String.class, 
		String.class, 
		Integer.class
	};
	
	Boolean [] COLUMNS_EDITABLE = new Boolean []{
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
		false
	};
	
	private void initThis(){
		setColumnIdentifiers(COLUMNS);
	}
	
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
