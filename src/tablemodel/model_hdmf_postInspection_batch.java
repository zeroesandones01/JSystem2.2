package tablemodel;

import java.util.Date;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import DateChooser._JDateChooser;
import Functions.FncTables;
import Lookup._JLookup;

public class model_hdmf_postInspection_batch extends DefaultTableModel {

	private static final long serialVersionUID = -7064973525353264886L;
	private boolean editable = false;
	boolean[] COLUMNS_EDITABLE;	

	public model_hdmf_postInspection_batch() {
		InitGUI();
	}

	public model_hdmf_postInspection_batch(int rowCount, int columnCount) {
		super(rowCount, columnCount);

	}

	public model_hdmf_postInspection_batch(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);

	}

	public model_hdmf_postInspection_batch(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);

	}

	public model_hdmf_postInspection_batch(Vector data, Vector columnNames) {
		super(data, columnNames);

	}

	public model_hdmf_postInspection_batch(Object[][] data, Object[] columnNames) {
		super(data, columnNames);

	}

	private void InitGUI() {
		setColumnIdentifiers(COLUMNS);
		
		COLUMNS_EDITABLE = new boolean[] {
				false,
				true,
				false,
				false,
				true,
				true,
				true,
				true,
				false,
				false,
				false,
				false
			};
	}
	
	public String [] COLUMNS = new String[] {
		"Name",	
		"Tag",			
		"Unit",
		"Status",
		"New Status", 
		"Date",
		"HDMF Representative",
		"Remarks",
		"entity_id",
		"proj_id",
		"pbl_id",
		"seq_no"
	};
	
	public Class [] CLASS_TYPES = new Class[] {
		Object.class,
		Boolean.class,
		String.class,
		String.class,
		_JLookup.class,
		_JDateChooser.class,
		String.class,
		String.class,
		String.class,
		String.class,
		String.class,
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
