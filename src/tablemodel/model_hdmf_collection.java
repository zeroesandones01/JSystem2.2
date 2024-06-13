package tablemodel;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class model_hdmf_collection extends DefaultTableModel {

	private static final long serialVersionUID = -2081661130474117365L;
	private boolean editable = false;
	boolean[] COLUMNS_EDITABLE;
	
	public model_hdmf_collection() {
		initGUI();
	}

	public model_hdmf_collection(int arg0, int arg1) {
		super(arg0, arg1);

	}

	public model_hdmf_collection(Vector arg0, int arg1) {
		super(arg0, arg1);

	}

	public model_hdmf_collection(Object[] arg0, int arg1) {
		super(arg0, arg1);

	}

	public model_hdmf_collection(Vector arg0, Vector arg1) {
		super(arg0, arg1);

	}

	public model_hdmf_collection(Object[][] arg0, Object[] arg1) {
		super(arg0, arg1);

	}

	private void initGUI() {
		setColumnIdentifiers(COLUMNS);
		
		COLUMNS_EDITABLE = new boolean[] {
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
				false
		};
	}
	
	public String [] COLUMNS = new String[] {
		"Tag", 
		"Unit",
		"Name",
		"Date Paid",
		"Receipt No.", 
		"Amount",
		"Loan Released", 
		"entity_id", 
		"proj_id", 
		"pbl_id", 
		"seq_no", 
		"hlid_no", 
		"client_seqno"
	};
	
	public Class [] CLASS_TYPES = new Class[] {
		Boolean.class,				//Tag				00
		String.class,				//Unit				01
		Object.class,				//Name				02
		Date.class,					//Date Paid			03
		String.class, 				//Receipt No.		04
		BigDecimal.class,			//Amount			05
		Date.class,					//Loan Released		06
		String.class,
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
