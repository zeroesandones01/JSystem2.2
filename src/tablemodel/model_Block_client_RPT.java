package tablemodel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class model_Block_client_RPT extends DefaultTableModel {

	private static final long serialVersionUID = 1L;

	private boolean editable = false;
	
	public model_Block_client_RPT() {
		initThis();
	}

	public model_Block_client_RPT(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public model_Block_client_RPT(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public model_Block_client_RPT(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public model_Block_client_RPT(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public model_Block_client_RPT(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}
	private void initThis() {
		setColumnIdentifiers(new String[] {

			"Tag",			//0
			"Client",		//1
			"Project",		//2
			"PBL",			//3
			"RPT Lot",		//4
			"Year ",		//5
			"Remarks-Lot",	//6
			"RPT House",	//7
			"Year",			//8
			"Remarks-House",//9
			"RPT OR",		//10
			"Total",		//11
			"Description",	//12
			"Client Id",	//13
			"Seq No",		//14
			"Unit Id",		//15
			"Pbl Id",		//16
			"Code",			//17
			"Rec Id"		//18
			
			
			
			
			
	});
	}
	
	/**
	 * String.class = Alignment.CENTER
	 * Object.class = Alignment.LEFT
	 * BigDecimal.class = Alignment.RIGHT
	 * Integer.class = Alignment.CENTER
	 * 
	 */
	Class[] CLASS_TYPES = new Class[] {
			Boolean.class,		//Tag
			String.class, 		//Client
			String.class, 		//Project
			String.class, 		//PBL
			BigDecimal.class, 	//RPT Lot
			Integer.class, 		//Year
			String.class, 		//Remarks-Lot
			BigDecimal.class, 	//RPT House
			Integer.class, 		//Year
			String.class,		//Remarks-House
			String.class,		//RPT OR
			BigDecimal.class,	//Total
			String.class,		//description
			String.class,		//client_id		
			Integer.class,		//seq_no
			String.class,		//unit_id
			String.class,		//pbl_id
			String.class,		//Code
			String.class,		//Rec Id
	};
	Boolean[] COLUMN_EDITABLE = new Boolean[] {
			true,	//Tag
			false,	//Client
			false,	//Project 
			false,	//PBL 
			false,	//RPT Lot
			false, 	//Year
			false,	//Remarks-Lot
			false, 	//RPT House
			false,	//Year
			false,	//Remarks-House
			false,	//RPT OR
			false,	//Total
			false,	//description
			false,	//client_id	
			false,	//seq_no
			false,	//unit_id
			false,	//pbl_id
			false,	//Code
			false,	//Rec Id
	};


	public Class getColumnClass(int columnIndex) {
		return CLASS_TYPES[columnIndex];
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return COLUMN_EDITABLE[columnIndex];
	}

	public void clear() {
		FncTables.clearTable(this);
		
	}
	
	public void setEditable(boolean editable) {
		if(editable){
			COLUMN_EDITABLE = new Boolean[]{
					true,  //0
					false, //1
					false, //2
					false, //3
					true, //4
					true, //5
					true, //6
					true, //7
					true, //8
					true, //9
					true, //10	
					false,//11
					false, //12 
					false, //13
					false, //14
					false, //15
					false, //16
					false, //17
					false, //18
			};
		}else{
			COLUMN_EDITABLE = new Boolean[]{
					false, //0
					false, //1
					false, //2
					false, //3
					false, //4
					false, //5
					false, //6
					false, //7
					false, //8
					false, //9
					false, //10
					false, //11
					false, //12
					false, //13
					false, //14
					false, //15
					false, //16
					false, //17
					false, //18
			};
		}	
	}
}
