package tablemodel;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class model_hdmf_schedule extends DefaultTableModel {

	private static final long serialVersionUID = -2642793030321335910L;

	private boolean editable = false;
	boolean[] COLUMNS_EDITABLE;	
	
	public model_hdmf_schedule() {
		InitGUI();
	}

	public model_hdmf_schedule(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		InitGUI();
	}

	public model_hdmf_schedule(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		InitGUI();
	}

	public model_hdmf_schedule(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		InitGUI();
	}

	public model_hdmf_schedule(Vector data, Vector columnNames) {
		super(data, columnNames);
		InitGUI();
	}

	public model_hdmf_schedule(Object[][] data, Object[] columnNames) {
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
				false,
				false
			};
	}
	
	public String[] COLUMNS = new String[] {
		"Date",				//0
		"Amount",			//1
		"Proc. Fee",		//2
		"UPICO",			//3
		"MRI",				//4
		"Fire",				//5
		"VAT",				//6
		"Interest",			//7
		"Principal", 		//8
		"Balance",			//9
		"Int. Rate"		//10
	};
		
	public Class [] CLASS_TYPES = new Class[] { 
		Date.class, 
		BigDecimal.class,
		BigDecimal.class,
		BigDecimal.class,
		BigDecimal.class,
		BigDecimal.class,
		BigDecimal.class,
		BigDecimal.class,
		BigDecimal.class,
		BigDecimal.class,
		BigDecimal.class
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
