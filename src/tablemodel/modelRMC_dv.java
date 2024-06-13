package tablemodel;

import java.math.BigDecimal;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;
import Lookup._JLookup;

public class modelRMC_dv extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8738810102880371204L;
	
	private boolean editable = false;
	boolean[] COLUMN_EDITABLE;
	
	
	public modelRMC_dv() {
		initThis();
	}

	public modelRMC_dv(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelRMC_dv(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelRMC_dv(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelRMC_dv(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelRMC_dv(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
		"Tag",   	  		//0
		"RPLF No.", 		//1
		"Client ID", 		//2
		"Client Name", 		//3
		"PBL", 				//4
		"Amount", 			//5
		"PV No.", 			//6
		"DV No.", 			//7
	};

	public Class[] CLASS_TYPES = new Class[] {
		Boolean.class,     	//0
		String.class, 		//RPLF no.
		String.class, 		//Client ID
		String.class, 		//Client Name
		String.class, 		//PBL	
		BigDecimal.class,		//Amount
		String.class,		//PV no.
		String.class,		//DV no.
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
		COLUMN_EDITABLE = new boolean[] {
				true,
				false, 	   //RPLF no.
				false,    //Client Name
				false,    //Client Name
				false,   //PBL	
				false,  //Amount
				false, //PV no.
				false, //DV no.
		};
	}

	public Class getColumnClass(int columnIndex) {
		return CLASS_TYPES[columnIndex];
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return COLUMN_EDITABLE[columnIndex];
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
		if(editable){
			COLUMN_EDITABLE = new boolean[] {
					true,
					false, 	   //RPLF no.
					false,    //Client Name
					false,    //Client Name
					false,   //PBL	
					false,  //Amount
					false, //PV no.
					false, //DV no.
			};
		}else{
			COLUMN_EDITABLE = new boolean[] {
					true,
					false, 	   //RPLF no.
					false,    //Client Name
					false,    //Client Name
					false,   //PBL	
					false,  //Amount
					false, //PV no.
					false, //DV no.
			};
		}
	}

	public void clear() {
		FncTables.clearTable(this);
	}
	
	
}
