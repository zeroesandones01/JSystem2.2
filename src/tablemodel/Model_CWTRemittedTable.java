package tablemodel;

import java.math.BigDecimal;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class Model_CWTRemittedTable extends DefaultTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1804617471115998809L;
	
	private boolean editable = false;
	boolean[] COLUMNS_EDITABLE;
	
	public Model_CWTRemittedTable() {
		// TODO Auto-generated constructor stub
		initCWT();
	}

	public Model_CWTRemittedTable(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		// TODO Auto-generated constructor stub
	}

	public Model_CWTRemittedTable(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		// TODO Auto-generated constructor stub
	}

	public Model_CWTRemittedTable(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		// TODO Auto-generated constructor stub
	}

	public Model_CWTRemittedTable(Vector data, Vector columnNames) {
		super(data, columnNames);
		// TODO Auto-generated constructor stub
	}

	public Model_CWTRemittedTable(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		// TODO Auto-generated constructor stub
	}

	private void initCWT() {
		setColumnIdentifiers(COLUMNS);
		COLUMNS_EDITABLE = new boolean[] {
				false,		//"OR No.",			0
				false,		//"Name", 			1
				false,		//"Type", 			2
				true,		//"w/LOG",			3
				true,		//"Taxable", 		4
				false,		//"Project", 		5
				false,		//"Status", 		6
				false,		//"Phase", 			7
				false,		//"BLock", 			8
				false,		//"Lot",			9
				false,		//"Model", 			10
				false,		//"Gross", 			11
				false,		//"Net", 			12
				false,		//"Rate",			13
				false,		//"Amount",			14
				false,		//"Client ID", 		15
				false,		//"Project", 		16
				false,		//"Unit ID",		17
				false,		//"Sequence No.", 	18
				false,		//"IPSEWT", 		19
				false,		//"ATC", 			20
				false,		//"4th_qtr",		21
				false,		//"TCT", 			22
				false		//"RPLF"			23
				
		};
	}
	
	public String [] COLUMNS = new String[] {
			"OR No.", 		//0
			"Name", 		//1
			"Type", 		//2
			"w/LOG",		//3
			"Taxable", 		//4
			"Project", 		//5
			"Status", 		//6
			"Phase", 		//7
			"BLock", 		//8
			"Lot",			//9
			"Model", 		//10
			"Gross", 		//11
			"Net", 			//12
			"Rate",			//13
			"Amount",		//14
			"Client ID", 	//15
			"Project", 		//16
			"Unit ID",		//17
			"Sequence No.", //18
			"IPSEWT", 		//19
			"ATC", 			//20
			"4th_qtr",		//21
			"TCT", 			//22
			"RPLF"			//23
	};
	
	public Class [] ALIGNMENT = new Class [] {
		
	};
	
	public Class [] CLASS_TYPES = new Class[] {
		String.class,				//OR NO.					00
		String.class,				//Name						01
		String.class,				//Type						02
		Boolean.class,				//wLog						03
		Boolean.class,				//Taxable					04
		String.class,				//Project					05
		String.class,				//status					06
		String.class,				//phase						07
		String.class,				//block						08
		String.class,				//lot						09
		String.class,				//model_alias				10
		BigDecimal.class,			//gross_tcp					11
		BigDecimal.class,			//net_price					12
		BigDecimal.class,			//rate						13
		BigDecimal.class,			//amount					14
		String.class,				//entity_id					15
		String.class,				//projcode					16
		String.class,				//pbl_id					17
		Integer.class,				//seq_no					18
		String.class,				//IPSEWT					19
		String.class,				//ATC						20
		BigDecimal.class,			//4th_qtr					21
		String.class,				//TCT						22
		String.class,				//RPLF						23
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
	
	public boolean isEditable(){
		return editable;
	}
	
	public void setEditable(boolean editable){
		this.editable = editable;
	}
}
