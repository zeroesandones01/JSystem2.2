package tablemodel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class modelEWT_remitted extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8738810102880371204L;
	
	private boolean editable = false;
	boolean[] COLUMN_EDITABLE;
	
	
	public modelEWT_remitted() {
		initThis();
	}

	public modelEWT_remitted(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelEWT_remitted(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelEWT_remitted(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelEWT_remitted(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelEWT_remitted(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {			
			"Payee ID", 	//1
			"TIN", 			//2
			"Emp. Name", 	//3
			"PV No.", 		//4
			"JV No.", 		//5
			"Trans. Date", 	//6
			"Tax Amount",   //7
			"Net Exp. Amt.", //8
			"Date Remitted", //9
			"Remitted by", 	//10
			"Rem. RPLF No.",//11
			"Check Status"  //12
			
			};

	
	/**
	 * String.class = Alignment.CENTER
	 * Object.class = Alignment.LEFT
	 * BigDecimal.class = Alignment.RIGHT
	 * Integer.class = Alignment.CENTER
	 * Timestamp.class
	 */
	public Class[] CLASS_TYPES = new Class[] {			
			String.class, 		//payee id
			String.class, 		//TIN
			Object.class, 		//Emp. Name
			String.class, 		//PV No.
			String.class, 		//JV No.
			Timestamp.class, 	//Trans. Date
			BigDecimal.class, 	//Tax Amount
			BigDecimal.class, 	//Gross Income	
			Timestamp.class, 	//Date Remitted
			String.class, 		//Remitted by
			String.class, 		//Rem. RPLF No.
			String.class 		//Check Status
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
		COLUMN_EDITABLE = new boolean[] {
				false, 		//Emp. Code
				false, 		//TIN
				false, 		//Emp. Name
				false, 		//RPLF No.
				false, 		//JV No.
				false, 		//Trans. Date
				false, 		//Gross Income	
				false,		//Tax Amount
				false, 		//Date Remitted
				false, 		//Remitted by
				false, 		//Rem. RPLF No.
				false 		//Check Status
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
					false, 		//Emp. Code
					false, 		//TIN
					false, 		//Emp. Name
					false, 		//RPLF No.
					false, 		//JV No.
					false, 		//Trans. Date
					false, 		//Gross Income	
					false,		//Tax Amount
					false, 		//Date Remitted
					false, 		//Remitted by
					false, 		//Rem. RPLF No.
					false 		//Check Status
			};
		}else{
			COLUMN_EDITABLE = new boolean[] {
					false, 		//Emp. Code
					false, 		//TIN
					false, 		//Emp. Name
					false, 		//RPLF No.
					false, 		//JV No.
					false, 		//Trans. Date
					false, 		//Gross Income	
					false,		//Tax Amount
					false, 		//Date Remitted
					false, 		//Remitted by
					false, 		//Rem. RPLF No.
					false 		//Check Status
			};
		}
	}

	
	
	
}
