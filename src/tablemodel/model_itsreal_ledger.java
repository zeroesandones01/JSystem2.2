package tablemodel;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class model_itsreal_ledger extends DefaultTableModel {

	private static final long serialVersionUID = 7305568870033704236L;
	private boolean editable = false;
	boolean[] COLUMNS_EDITABLE;	

	public model_itsreal_ledger() {
		InitGUI();
	}

	public model_itsreal_ledger(int arg0, int arg1) {
		super(arg0, arg1);
		InitGUI();
	}

	public model_itsreal_ledger(Vector arg0, int arg1) {
		super(arg0, arg1);
		InitGUI();
	}

	public model_itsreal_ledger(Object[] arg0, int arg1) {
		super(arg0, arg1);
		InitGUI();
	}

	public model_itsreal_ledger(Vector arg0, Vector arg1) {
		super(arg0, arg1);
		InitGUI();
	}

	public model_itsreal_ledger(Object[][] arg0, Object[] arg1) {
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
				false,
				
				false,
				false,
				false
		};
	}

	public String[] COLUMNS = new String[] {
			"Trans Date", 
			"Actual Date", 
			"Sched Date", 	
			
			"Amount", 
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
			
			"Due Type"
	};

	public Class [] CLASS_TYPES = new Class[] { 
			Date.class,
			Date.class, 
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
			BigDecimal.class,

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
