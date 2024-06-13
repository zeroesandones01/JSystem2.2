package tablemodel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class modelEWT_forRemittance extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8738810102880371204L;
	
	private boolean editable = false;
	boolean[] COLUMN_EDITABLE;
	
	
	public modelEWT_forRemittance() {
		initThis();
	}

	public modelEWT_forRemittance(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelEWT_forRemittance(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelEWT_forRemittance(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelEWT_forRemittance(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelEWT_forRemittance(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			"Tag", 			//1
			"Payee ID", 	//2
			"TIN", 			//3
			"Emp. Name", 	//4
			"PV No.", 		//5
			"CV No.", 		//6
			"JV No.", 		//7
			"PV Date", 	//8
			"Tax Amount",   //9
			"Net Exp. Amt.", //10
			"Date Released", //11
			"With LTS" 			 //12
			
			};

	
	/**
	 * String.class = Alignment.CENTER
	 * Object.class = Alignment.LEFT
	 * BigDecimal.class = Alignment.RIGHT
	 * Integer.class = Alignment.CENTER
	 * Timestamp.class
	 */
	public Class[] CLASS_TYPES = new Class[] {
			Boolean.class, 		//Tag
			String.class, 		//Emp. Code
			String.class, 		//TIN
			Object.class, 		//Emp. Name
			String.class, 		//RPLF No.
			String.class, 		//CV No.
			String.class, 		//JV No.
			Timestamp.class, 	//Trans. Date
			BigDecimal.class, 	//Gross Income	
			BigDecimal.class, 	//Tax Amount
			Timestamp.class, 	//Date Released
			Boolean.class 		//LTS
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
		COLUMN_EDITABLE = new boolean[] {
				false, 		//Tag
				false, 		//Emp. Code
				false, 		//TIN
				false, 		//Emp. Name
				false, 		//RPLF No.
				false, 		//CV No.
				false, 		//JV No.
				false, 		//Trans. Date
				false, 		//Gross Income	
				false, 		//Tax Amount
				false, 		//Date Released
				false 		//LTS
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
					true, 		//Tag
					false, 		//Emp. Code
					false, 		//TIN
					false, 		//Emp. Name
					false, 		//RPLF No.
					false, 		//CV No.
					false, 		//JV No.
					false, 		//Trans. Date
					false, 		//Gross Income	
					false, 		//Tax Amount
					false, 		//Date Released
					false 		//LTS
			};
		}else{
			COLUMN_EDITABLE = new boolean[] {
					false, 		//Tag
					false, 		//Emp. Code
					false, 		//TIN
					false, 		//Emp. Name
					false, 		//RPLF No.
					false, 		//CV No.
					false, 		//JV No.
					false, 		//Trans. Date
					false, 		//Gross Income	
					false, 		//Tax Amount
					false, 		//Date Released
					false 		//LTS
			};
		}
	}

	
	
	
}
