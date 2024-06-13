package tablemodel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class model_CWTtable extends DefaultTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1804617471115998809L;
	
	private boolean editable = false;
	boolean[] COLUMNS_EDITABLE;
	
	public model_CWTtable() {
		// TODO Auto-generated constructor stub
		initCWT();
	}

	public model_CWTtable(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		// TODO Auto-generated constructor stub
	}

	public model_CWTtable(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		// TODO Auto-generated constructor stub
	}

	public model_CWTtable(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		// TODO Auto-generated constructor stub
	}

	public model_CWTtable(Vector data, Vector columnNames) {
		super(data, columnNames);
		// TODO Auto-generated constructor stub
	}

	public model_CWTtable(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		// TODO Auto-generated constructor stub
	}
	
	//fixed index number by Jari Cruz, July 20 2022
	private void initCWT() {
		setColumnIdentifiers(COLUMNS);
		COLUMNS_EDITABLE = new boolean[] {
			true, 				//	Mark					00
			true, 				//	OR No.					01
			false, 				//	Name					02
			false,				//	Type					03
			true,				//	wLog					04
			true,				//	Taxable					05
			false, 				//	Project					06
			false, 				//	Status					07
			false, 				//	Release Batch			08
			false, 				//	Phase					09
			false, 				//	Block					10
			false, 				//	Lot						11
			false, 				//	Vatable					12
			false, 				//	Lot Area				13
			false, 				//	Model					14
			false, 				//	Gross					15
			false, 				//	Discount				16
			false, 				//	Net						17
			false, 				//	Rate					18
			false, 				//	Amount					19
			false, 				//	Entity					20
			false, 				//	Projcode				21
			false, 				//	Unit					22
			false, 				//	Sequence				23
			false, 				//	ComTin1					24
			false, 				//	ComTin2					25
			false, 				//	ComTin3					26
			false, 				//	ComTin4					27
			false, 				//	Company					28
			false, 				//	Company Address			29
			false, 				//	ClientTin1				30
			/*
			false, 				//	ClientTin2				29
			false, 				//	ClientTin3				30
			false, 				//	ClientTin4				31
			*/
			false, 				//	Client Name				31
			false, 				//	Client Address			32
			false, 				//	Zipcode					33
			false, 				//	IPSEWT					34
			false, 				//	ATC						35
			false, 				//	1stQtr					36
			false, 				//	2ndQtr					37
			false, 				//	3rdQtr					38
			false, 				//	4thQtr					39
			false, 				//	TCT						40
			false, 				//	RPLF					41
		};
	}
	
	public String [] COLUMNS = new String[] {
		"Mark",						//	0
		"OR No.",					//	1
		"Name",						//	2
		"Type",						//	3
		"w/LOG",					//	4
		"Taxable",					//	5
		"Project",					//	6
		"Status",					//	7
		"Release Batch",			//	8
		"Phase",					//	9
		"Block",					//	10 
		"Lot",						//	11
		"Vatable", 					//	12
		"Lot Area", 				//	13
		"Model", 					//	14
		"Gross", 					//	15
		"Discount", 				//	16
		"Net", 						//	17
		"Rate", 					//	18
		"Amount",					//	19
		"Client ID",				//	20
		"Project ID", 				//	21
		"Unit ID", 					//	22
		"Sequence No.", 			//	23
		"com_tin1", 				//	24
		/*
		"com_tin2", 				//	24
		"com_tin3", 				//	25
		"com_tin4", 				//	26
		*/
		"Company", 					//	25
		"CompanyAddress", 			//	26
		"cl_tin1",					//	27
		"cl_tin2", 					//	28
		"cl_tin3", 					//	29
		"cl_tin4", 					//	30
		"Payor", 					//	31
		"ClientAddress", 			//	32
		"Zip Code", 				//	33
		"IPSEWT", 					//	34
		"ATC",						//	35
		"1st_qtr",					//	36
		"2nd_qtr",					//	37
		"3rd_qtr",					//	38
		"4th_qtr",					//	39
		"TCT", 						//	40
		"RPLF"						//	41
	};
	
	public Class [] ALIGNMENT = new Class [] {
		
	};
	
	public Class [] CLASS_TYPES = new Class[] {
		Boolean.class,				//Mark						00
		String.class,				//OR NO.					01
		String.class,				//Name						02
		String.class,				//Type						03
		Boolean.class,				//wLog						04
		Boolean.class,				//Taxable					05
		String.class,				//Project					06
		String.class,				//status					07
		String.class,				//release_batch				08
		String.class,				//phase						09
		String.class,				//block						10
		String.class,				//lot						11
		Boolean.class,				//Vat						12
		BigDecimal.class,			//Lot Area					13
		String.class,				//model_alias				14
		BigDecimal.class,			//gross_tcp					15
		BigDecimal.class,			//discount					16
		BigDecimal.class,			//net_price					17
		BigDecimal.class,			//rate						18
		BigDecimal.class,			//amount					19
		String.class,				//entity_id					20
		String.class,				//projcode					21
		String.class,				//pbl_id					22
		Integer.class,				//seq_no					23
		String.class,				//com_tin1					24
		String.class,				//com_tin2					25
		String.class,				//com_tin3					26
		String.class,				//com_tin4					27
		String.class,				//co_name					28
		String.class,				//co_address				29
		String.class,				//cl_tin1					30
		/*
		String.class,				//cl_tin2					29
		String.class,				//cl_tin3					30
		String.class,				//cl_tin4					31
		*/
		String.class,				//Client					31
		String.class,				//Client Address			32
		String.class,				//Zipcode					33
		String.class,				//IPSEWT					34
		String.class,				//ATC						35
		BigDecimal.class,			//1st_qtr					36
		BigDecimal.class,			//2nd_qtr					37
		BigDecimal.class,			//3rd_qtr					38
		BigDecimal.class,			//4th_qtr					39
		String.class,				//TCT						40
		String.class,				//RPLF						41
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
