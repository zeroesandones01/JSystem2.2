package tablemodel;

import java.util.Date;
import java.util.Vector;

import javax.swing.GroupLayout.Alignment;
import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class model_hdmf_FirstFiling extends DefaultTableModel {

	private static final long serialVersionUID = 8415006587348786336L;
	private boolean editable = false;
	boolean[] COLUMNS_EDITABLE;	
	
	public model_hdmf_FirstFiling() {
		InitGUI();
	}

	public model_hdmf_FirstFiling(int rowCount, int columnCount) {
		super(rowCount, columnCount);

	}

	public model_hdmf_FirstFiling(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);

	}

	public model_hdmf_FirstFiling(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);

	}

	public model_hdmf_FirstFiling(Vector data, Vector columnNames) {
		super(data, columnNames);

	}

	public model_hdmf_FirstFiling(Object[][] data, Object[] columnNames) {
		super(data, columnNames);

	}

	private void InitGUI() {
		setColumnIdentifiers(COLUMNS);

		COLUMNS_EDITABLE = new boolean[] {
			false,
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
			false
		};
	}
	
	public String [] COLUMNS = new String[] {
		"Name",	
		"Tag",			
		"Project",
		"Phase",
		"Block",
		"Lot",
		"Class Type",
		"DP Stage",
		"DP Term",
		//"Pay Status",
		"SCD Prep", 
		"House %",
		"Findings",
		"NTP",
		"Last Complied",
		"Docs Complete", 
		"House Model", 
		"Remaining DP",
		"Complied First Filing",
		"Complied MSVS",
		"entity_id", 
		"pbl_id",
		"proj_id",
		"seq_no"
	};
	
	public Class [] CLASS_TYPES = new Class[] {
		Object.class,
		Boolean.class,
		String.class,
		String.class,
		String.class,
		String.class,
		String.class,
		String.class,
		Integer.class,
		//String.class,
		Date.class, 
		String.class,
		Object.class,
		Date.class,
		Date.class,
		Date.class,				//	Docs Complete
		String.class, 
		String.class, 
		Date.class,
		Date.class, 
		String.class,			//	entity
		String.class,
		String.class,			
		Integer.class
	};
	
	public Alignment [] align_meth = new Alignment[] {
			Alignment.LEADING,		/***	Name		***/	
			Alignment.CENTER,		/***	Tag			***/	
			Alignment.CENTER,		/***	Project		***/
			Alignment.CENTER,		/***	Phase		***/
			Alignment.CENTER,		/***	Block		***/
			Alignment.CENTER,		/***	Lot			***/
			Alignment.CENTER,		/***	Class Type	***/
			Alignment.CENTER,		/***	DP Stage	***/
			Alignment.CENTER,		/***	DP Term		***/
			Alignment.CENTER,		/***	Pay Status	***/
			Alignment.CENTER,		/***	House %		***/
			Alignment.LEADING,		/***	Findings	***/
			Alignment.CENTER,		/***	NTP			***/
			Alignment.CENTER,		/***	Last Complied***/
			Alignment.CENTER		/***	Docs Complete***/
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
