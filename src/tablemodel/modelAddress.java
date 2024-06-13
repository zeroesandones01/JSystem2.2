package tablemodel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelAddress extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8738810102880371204L;

	public modelAddress() {
		initThis();
	}

	public modelAddress(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelAddress(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelAddress(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelAddress(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelAddress(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			"Rec. ID", //0
			"No/Blk. Lot", // 1
			"Street/Subd.", //2
			"District/Town", //3
			"City/Municipality", //4 
			"Province", //5
			"Country", //6
			"Zip Code", //7
			"Address Type", //8
			"Status", //9
			"Mailing Address",//10
			"Permanent Address", //11
			"Ownership", //12
			"Rental Amount", //13
			"Years Stay", //14
			"Months Stay", //15
			/*"Date Resided", //13
			"Date To", //14 */
			"Return Date", //16
			"Return Reason" //17
	};
	
	public Class[] CLASS_TYPES = new Class[] {
			Integer.class, //Rec ID
			String.class, //No./Blk Lot
			String.class, //Street/Subd.
			String.class, //District/Town
			String.class, //City/Municipality
			String.class, //Province
			String.class, //Country
			String.class, //Zip Code
			String.class, //Address Type
			String.class, //Status
			Boolean.class, //Mailing Address
			Boolean.class, //CTS Address
			String.class, //Ownership
			BigDecimal.class, //Rental Amount
			Integer.class, //Years Stay
			Integer.class, //Months Stay
			/*Timestamp.class, //Date To
			Timestamp.class, //Date Left*/			
			Timestamp.class, //Return Date
			String.class //Return Reason 
			
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
	}

	public Class getColumnClass(int columnIndex) {
		return CLASS_TYPES[columnIndex];
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}
	public void clear() {
		FncTables.clearTable(this);
	}

}
