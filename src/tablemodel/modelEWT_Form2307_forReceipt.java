package tablemodel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class modelEWT_Form2307_forReceipt extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8738810102880371204L;
	
	private boolean editable = false;
	boolean[] COLUMN_EDITABLE;
	
	
	public modelEWT_Form2307_forReceipt() {
		initThis();
	}

	public modelEWT_Form2307_forReceipt(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelEWT_Form2307_forReceipt(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelEWT_Form2307_forReceipt(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelEWT_Form2307_forReceipt(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelEWT_Form2307_forReceipt(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {	
			"PV No.", 			//2
			"PV Creation Date", //3
			"PV Posting Date", 	//4
			"Payee Name", 		//5
			"Net Exp. Amt.", 	//6
			"Tax Amount",   	//7			
			"Date Remitted", 	//8		
			"Remitted by", 		//9
			"Rem. RPLF No.",	//10
			"Sent by", 			//11
			"Date Received"		//12
			
			};

	
	/**
	 * String.class = Alignment.CENTER
	 * Object.class = Alignment.LEFT
	 * BigDecimal.class = Alignment.RIGHT
	 * Integer.class = Alignment.CENTER
	 * Timestamp.class
	 */
	public Class[] CLASS_TYPES = new Class[] {		
			String.class, 		//PV No.
			Timestamp.class, 	//PV Creation Date
			Timestamp.class, 	//PV Posting Date
			String.class, 		//Payee Name
			BigDecimal.class, 	//Gross Income
			BigDecimal.class, 	//Tax Amount
			Timestamp.class, 	//Date Remitted
			String.class, 		//Remitted by
			String.class, 		//Rem. RPLF No.
			String.class, 		//Sent by
			Timestamp.class		//Date Received
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
		COLUMN_EDITABLE = new boolean[] {
				false, 		//PV No.
				false, 		//PV Creation Date
				false, 		//PV Posting Date
				false, 		//Payee Name
				false, 		//Gross Income
				false, 		//Tax Amount
				false, 		//Date Remitted
				false, 		//Remitted by
				false,		//Rem. RPLF No.
				false, 		//Sent by
				false 		//Date Received
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
					false, 		//PV No.
					false, 		//PV Creation Date
					false, 		//PV Posting Date
					false, 		//Payee Name
					false, 		//Gross Income
					false, 		//Tax Amount
					false, 		//Date Remitted
					false, 		//Remitted by
					false, 		//Rem. RPLF No.
					false, 		//Sent by
					false 		//Date Received
			};
		}else{
			COLUMN_EDITABLE = new boolean[] {
					false, 		//PV No.
					false, 		//PV Creation Date
					false, 		//PV Posting Date
					false, 		//Payee Name
					false, 		//Gross Income
					false, 		//Tax Amount
					false, 		//Date Remitted
					false, 		//Remitted by
					false, 		//Rem. RPLF No.
					false, 		//Sent by
					false 		//Date Received
			};
		}
	}

	
	
	
}
