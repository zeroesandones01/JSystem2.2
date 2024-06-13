package tablemodel;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import DateChooser._JDateChooser;
import Functions.FncTables;

public class model_hdmf_noaextension extends DefaultTableModel {

	private static final long serialVersionUID = 4711018390374758293L;
	private boolean editable = false;
	
	public model_hdmf_noaextension() {
		 initThis(); 
	}

	public model_hdmf_noaextension(int rowCount, int columnCount) {
		super(rowCount, columnCount);

	}

	public model_hdmf_noaextension(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		 initThis(); 
	}

	public model_hdmf_noaextension(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		 initThis(); 
	}

	public model_hdmf_noaextension(Vector data, Vector columnNames) {
		super(data, columnNames);
		 initThis(); 
	}

	public model_hdmf_noaextension(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		 initThis(); 
	}

	String [] COLUMNS = new String [] {
		"No.", 
		"Tag", 
		"Name", 
		"Unit", 
		"NOA Issuance Date",
		"Account Status", 
		"Actual Date", 
		"entity_id", 
		"projcode", 
		"pbl_id", 
		"seq_no"
	};
		
	Class [] CLASS_TYPES = new Class []{
		Integer.class,			//No. 
		Boolean.class,			//Tag 
		Object.class,			//Name 
		String.class,			//Unit 
		_JDateChooser.class,	//NOA Released Date 
		Object.class,			//Account Status
		_JDateChooser.class,	//Actual Date
		String.class,			//entity_id
		String.class,			//projcode 
		String.class,			//pbl_id 
		String.class			//seq_no
	};
	
	Boolean [] COLUMNS_EDITABLE = new Boolean []{
		false,
		true, 
		false, 
		false, 
		false, 
		false, 
		true,
		false, 
		false, 
		false, 
		false 
	};
	
	private void initThis() {
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
