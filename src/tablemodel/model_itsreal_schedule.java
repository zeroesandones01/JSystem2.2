package tablemodel;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class model_itsreal_schedule extends DefaultTableModel {

	private static final long serialVersionUID = -6400644423094440718L;
	private boolean editable = false;
	boolean[] COLUMNS_EDITABLE;	

	public model_itsreal_schedule() {
		InitGUI();
	}

	public model_itsreal_schedule(int arg0, int arg1) {
		super(arg0, arg1);
		InitGUI();
	}

	public model_itsreal_schedule(Vector arg0, int arg1) {
		super(arg0, arg1);
		InitGUI();
	}

	public model_itsreal_schedule(Object[] arg0, int arg1) {
		super(arg0, arg1);
		InitGUI();
	}

	public model_itsreal_schedule(Vector data, Vector columnNames) {
		super(data, columnNames);
		InitGUI();
	}

	public model_itsreal_schedule(Object[][] data, Object[] columnNames) {
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
				false
		};
	}

	public String[] COLUMNS = new String[] {
			"Date",	
			"Amount", 
			"Proc. Fee", 
			"MRI", 
			"Fire", 
			"VAT", 
			"Interest", 
			"Principal", 
			"Balance"
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
