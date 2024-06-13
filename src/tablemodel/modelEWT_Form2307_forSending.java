package tablemodel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class modelEWT_Form2307_forSending extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8738810102880371204L;
	
	private boolean editable = false;
	boolean[] COLUMN_EDITABLE;
	
	
	public modelEWT_Form2307_forSending() {
		initThis();
	}

	public modelEWT_Form2307_forSending(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelEWT_Form2307_forSending(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelEWT_Form2307_forSending(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelEWT_Form2307_forSending(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelEWT_Form2307_forSending(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {		
			"tag", 				//1
			"PV No.", 			//2
			"PV Creation Date", //3
			"PV Posting Date", 	//4
			"CV No.", 			//5
			"Payee Name", 		//6
			"Net Exp. Amt.", 	//7
			"Tax Amount",   	//8			
			"Date Remitted", 	//9		
			"Remitted by", 		//10
			"Rem. RPLF No.",		//11
			"Entity ID"		//12
			
			};

	
	/**
	 * String.class = Alignment.CENTER
	 * Object.class = Alignment.LEFT
	 * BigDecimal.class = Alignment.RIGHT
	 * Integer.class = Alignment.CENTER
	 * Timestamp.class
	 */
	public Class[] CLASS_TYPES = new Class[] {			
			Boolean.class, 		//tag
			String.class, 		//PV No.
			Timestamp.class, 	//PV Creation Date
			Timestamp.class, 	//PV Posting Date
			String.class, 		//CV No.
			Object.class, 		//Payee Name
			BigDecimal.class, 	//Gross Income
			BigDecimal.class, 	//Tax Amount
			Timestamp.class, 	//Date Remitted
			Object.class, 		//Remitted by
			String.class, 		//Rem. RPLF No.
			String.class 		//
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
		COLUMN_EDITABLE = new boolean[] {
				false, 		//tag
				false, 		//PV No.
				false, 		//PV Creation Date
				false, 		//PV Posting Date
				false, 		//CV No.
				false, 		//Payee Name
				false, 		//Gross Income
				false, 		//Tax Amount
				false, 		//Date Remitted
				false, 		//Remitted by
				false, 		//Rem. RPLF No.
				false 		//
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
					true, 		//tag
					false, 		//PV No.
					false, 		//PV Creation Date
					false, 		//PV Posting Date
					false, 		//CV No.
					false, 		//Payee Name
					false, 		//Gross Income
					false, 		//Tax Amount
					false, 		//Date Remitted
					false, 		//Remitted by
					false, 		//Rem. RPLF No.
					false 		//
			};
		}else{
			COLUMN_EDITABLE = new boolean[] {
					false, 		//tag
					false, 		//PV No.
					false, 		//PV Creation Date
					false, 		//PV Posting Date
					false, 		//CV No.
					false, 		//Payee Name
					false, 		//Gross Income
					false, 		//Tax Amount
					false, 		//Date Remitted
					false, 		//Remitted by
					false, 		//Rem. RPLF No.
					false 		//
			};
		}
	}

	
	
	
}
