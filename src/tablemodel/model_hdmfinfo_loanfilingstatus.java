package tablemodel;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;
import interfaces._GUI;

public class model_hdmfinfo_loanfilingstatus extends DefaultTableModel implements _GUI {

	private static final long serialVersionUID = 6063946791021800236L;
	private boolean editable = false;
	boolean[] COLUMNS_EDITABLE;	
	
	public model_hdmfinfo_loanfilingstatus() {
		initGUI();
	}

	public model_hdmfinfo_loanfilingstatus(int rowCount, int columnCount) {
		super(rowCount, columnCount);

	}

	public model_hdmfinfo_loanfilingstatus(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);

	}

	public model_hdmfinfo_loanfilingstatus(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);

	}

	public model_hdmfinfo_loanfilingstatus(Vector data, Vector columnNames) {
		super(data, columnNames);

	}

	public model_hdmfinfo_loanfilingstatus(Object[][] data, Object[] columnNames) {
		super(data, columnNames);

	}


	public void initGUI() {
		setColumnIdentifiers(COLUMNS);

		COLUMNS_EDITABLE = new boolean[] {
			false,		//Status Description	
			false,		//Transaction Date
			false,		//Actual Date
			false,		//Approved Amount
			false,		//Record Status
			false,		//Recorded By
			false,		//Recorded Date
			false,		//status_sequence
			false,		//entity_id
			false,		//proj_id
			false,		//pbl_id
			false		//seq_no
		};
	}

	public String [] COLUMNS = new String[] {
		"Status Description",	
		"Transaction Date",
		"Actual Date",
		"Appvd. Amt.",
		"Status",
		"Recorded By",
		"Recorded Date",
		"Status Sequence",
		"entity_id",
		"proj_id",
		"pbl_id",
		"seq_no"
	};
		
	public Class [] CLASS_TYPES = new Class[] {
		Object.class,		//Status Description	
		Date.class,			//Transaction Date
		Date.class,			//Actual Date
		BigDecimal.class,	//Approved Amount
		String.class,		//Record Status
		Object.class,		//Recorded By
		Date.class,			//Recorded Date
		Integer.class,		//Status Sequence
		String.class,		//entity_id
		String.class,		//proj_id
		String.class,		//pbl_id
		String.class		//seq_no
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
