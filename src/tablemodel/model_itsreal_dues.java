package tablemodel;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class model_itsreal_dues extends DefaultTableModel {

	private static final long serialVersionUID = 7305568870033704236L;
	private boolean editable = false;
	boolean[] COLUMNS_EDITABLE;	

	public model_itsreal_dues() {
		InitGUI();
	}

	public model_itsreal_dues(int arg0, int arg1) {
		super(arg0, arg1);
		InitGUI();
	}

	public model_itsreal_dues(Vector arg0, int arg1) {
		super(arg0, arg1);
		InitGUI();
	}

	public model_itsreal_dues(Object[] arg0, int arg1) {
		super(arg0, arg1);
		InitGUI();
	}

	public model_itsreal_dues(Vector arg0, Vector arg1) {
		super(arg0, arg1);
		InitGUI();
	}

	public model_itsreal_dues(Object[][] arg0, Object[] arg1) {
		super(arg0, arg1);
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
				
				false,
				false,
				false,
				false,
				false
		};
	}

	public String[] COLUMNS = new String[] {
			"Sched Date", 	
			"Proc. Fee", 
			"RPT", 
			"Res", 
			"DP", 
			"MRI", 
			"Fire", 
			"VAT", 
			"SOI", 
			"SOP", 
			"DPP", 
			"Interest", 
			"Principal",  
			"Balance",
			"Amount"
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
