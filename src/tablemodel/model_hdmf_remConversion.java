package tablemodel;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import DateChooser._JDateChooser;
import Functions.FncTables;

public class model_hdmf_remConversion extends DefaultTableModel {

	private static final long serialVersionUID = -4717053077747400600L;
	private boolean editable = false;
	boolean[] COLUMNS_EDITABLE;	
	
	public model_hdmf_remConversion() {
		InitGUI(); 
	}

	public model_hdmf_remConversion(int rowCount, int columnCount) {
		super(rowCount, columnCount);

	}

	public model_hdmf_remConversion(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);

	}

	public model_hdmf_remConversion(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);

	}

	public model_hdmf_remConversion(Vector data, Vector columnNames) {
		super(data, columnNames);

	}

	public model_hdmf_remConversion(Object[][] data, Object[] columnNames) {
		super(data, columnNames);

	}


	private void InitGUI() {
		setColumnIdentifiers(COLUMNS);
		
		COLUMNS_EDITABLE = new boolean[] {
				
				true,	//Tag
				false,	//Name
				false,	//Unit
				false,	//Loan Amount
				false,	//Loan Released
				false,	//REM Notification
				false,	//MA Count
				false,	//Circular No.
				false,	//entity_id
				false,	//proj_id
				false,	//pbl_id
				false,	//seq_no
				false,	//HLID
				false,	//MA Paid
				false,	//RetFee 1
				false,	//RetFee 2-1
				false,	//RetFee 2-2
				false,	//CAR
				true,	//epeb date,
				
				false,	//CAR
				false,	//CAR
				false,	//CAR
				false,	//CAR
				false,	//CAR
				false,	//CAR
				false,	//CAR
				false,	//CAR
				false,	//CAR
				
				true	//TCT Pages
			};
	}
	
	public String [] COLUMNS = new String[] {

			"Tag",
			"Name",
			"Unit",
			"Loan Amount",	
			"Loan Released",
			"REM Notification", 
			"MA Count",
			"Circular No.",
			"entity_id",	
			"proj_id",	
			"pbl_id",	
			"seq_no", 
			"HLID", 
			"MA Paid", 
			"RetFee1",
			"RetFee2-1", 
			"RetFee2-2", 
			"CAR", 
			"EPEB Date",
			
			"DOAS",	 
			"Selling Price",  
			"Lot Area",	
			"Model", 
			"ECR", 
			"EPEB",  
			"First Name",  
			"Middle Name",  
			"Last Name", 
			
			"TCT Pages"
		};
		
	public Class [] CLASS_TYPES = new Class[] {
		
		Boolean.class,		//Tag
		Object.class,		//Name
		String.class,		//Unit
		BigDecimal.class,	//Loan Amount
		Date.class,			//Loan Released
		Date.class,			//REM Notification
		Integer.class,		//MA Count
		String.class,		//Circular No.
		String.class,		//entity_id
		String.class,		//proj_id
		String.class,		//pbl_id
		Integer.class,		//seq_no
		String.class,		//HLID
		Integer.class,		//MA Paid
		BigDecimal.class,	//RetFee1
		BigDecimal.class,	//RetFee2-1
		BigDecimal.class,	//RetFee2-2
		Date.class,			//CAR
		_JDateChooser.class,			//EPEB Date
		
		Date.class,			//c_doasrpt 
		BigDecimal.class,	//c_selling_price 
		Integer.class,		//c_lot_area
		String.class,		//c_model
		String.class,		//c_ecr
		String.class,		//c_epeb_no 
		String.class,		//c_first_name 
		String.class,		//c_middle_name 
		String.class,		//c_last_name
		
		Integer.class
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
