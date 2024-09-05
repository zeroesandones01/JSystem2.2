package tablemodel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class modelEPEBG2G extends DefaultTableModel {
	
	private static final long serialVersionUID = -8738810102880371204L;
	
	private boolean editable = false;
	boolean[] COLUMN_EDITABLE;

	public modelEPEBG2G() {
		initThis();
	}

	public modelEPEBG2G(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelEPEBG2G(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelEPEBG2G(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelEPEBG2G(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelEPEBG2G(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}
	public String[] COLUMNS = new String[] {
			"Rec. ID", //0
			"Description", 	// 1
			"Trans Date", 	// 2
			"Amount", 		// 3
			"Remarks", 		// 4
			"JV No", 		// 5
			"Batch no"  	// 6
	};
	
	/**
	 * String.class = Alignment.CENTER
	 * Object.class = Alignment.LEFT
	 * BigDecimal.class = Alignment.RIGHT
	 * Integer.class = Alignment.CENTER
	 * 
	 */
	private Class[] CLASS_TYPES = new Class[] {
			Integer.class, //Rec ID
			Object.class, 	 //Unit
			Timestamp.class, //Model
			BigDecimal.class,//Buyer's Name
			Object.class, 	 //Addr_no
			String.class, 	 //Street
			String.class  //Batch no
	};
	private void initThis() {
		setColumnIdentifiers(COLUMNS);
		COLUMN_EDITABLE = new boolean[] {
				false,
				false, 		//RPLF/JV
				false,		//Date
				false,		//BC Amt
				false, 		//Liquidated
				false, 		//For Liqui.
				false
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
					false,
					false, 		//RPLF/JV
					false,		//Date
					false,		//BC Amt
					false, 		//Liquidated
					false,
					false
			};
		}else{
			COLUMN_EDITABLE = new boolean[] {
					false,
					false, 		//RPLF/JV
					false,		//Date
					false,		//BC Amt
					false, 		//Liquidated
					false, 		//For Liqui.
					false
			};
		}
	}

	public int getSelectedRow() {
		// TODO Auto-generated method stub
		return 0;
	}

}
