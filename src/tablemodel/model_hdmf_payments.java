package tablemodel;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class model_hdmf_payments extends DefaultTableModel {

	private static final long serialVersionUID = -9028975856268127598L;

	private boolean editable = false;
	boolean[] COLUMNS_EDITABLE;	
	
	public model_hdmf_payments() {
		InitGUI();
	}

	public model_hdmf_payments(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		InitGUI();
	}

	public model_hdmf_payments(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		InitGUI();
	}

	public model_hdmf_payments(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		InitGUI();
	}

	public model_hdmf_payments(Vector data, Vector columnNames) {
		super(data, columnNames);
		InitGUI();
	}

	public model_hdmf_payments(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		InitGUI();
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
				false,
				false,
				false
			};
	}
	
	public String[] COLUMNS = new String[] {
		"Trans. Date",			//01
		"Actual Date",			//02
		"Amount",				//03
		"Receipt No.",			//04
		"Remitted Date",		//05
		"RPLF No.",				//06
		"Type",					//07
		"Branch",				//08
		"Remarks",				//09
		"Ref. No.",				//00
	};
	
	public Class [] CLASS_TYPES = new Class[] { 
		Date.class,
		Date.class,
		BigDecimal.class,
		String.class,
		Date.class,
		String.class,
		String.class,
		String.class,
		String.class,
		String.class
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
