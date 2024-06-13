package tablemodel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.sql.Date;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class model_epaymentForAUB extends DefaultTableModel {

	public model_epaymentForAUB() {
		initThis();
	}

	public model_epaymentForAUB(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public model_epaymentForAUB(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public model_epaymentForAUB(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public model_epaymentForAUB(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public model_epaymentForAUB(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}
	private void initThis() {
		setColumnIdentifiers(new String[] {
			"Tag", // 0	
			"Invoice number", //1
			"Check date", //2
			"Account number", //3
			"Vendor number", //4
			"Vendor", //5
			"Invoice amount", //6
			"Tax amount", //7
			"Reference", //8
			"Doc no", //9
			"Invdoc", //10
			"Fiscal year", //11
			"Compcode", //12
			"Currency", //13
			"Check no", //14
			"TIN no.", //15
			"Address", //16
			"Tax desc", //17
			"Tax base", //18
			"Tax code", //19
			"Ins key", //20
			"Vat", //21
			"Remarks1", //22
			"Remarks2", //23
			"Remarks3", //24
			"Remarks4", //25
			"Remarks5", //26
			"OR No", //27
			"MC No/ Credit Account No", //28
			"Invoice Date", //29
			"Email Address" //30

			
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
			Boolean.class,
			Object.class, //Unit
			Date.class, //Model
			String.class, //Buyer's Name
			String.class, //Addr_no
			Object.class, //Street
			BigDecimal.class, //City
			BigDecimal.class, //Province
			String.class, //Entity ID
			String.class, //Company Alias
			String.class, //Batch no
			String.class, //Model
			String.class, //Model
			String.class, //Buyer's Name
			Object.class, //Addr_no
			String.class, //Street
			String.class, //City
			String.class, //Province
			Integer.class, //Entity ID
			String.class, //Company Alias
			String.class, //Batch no
			BigDecimal.class, //Buyer's Name
			Object.class, //Addr_no
			String.class, //Street
			String.class, //City
			String.class, //Province
			String.class, //Entity ID
			String.class, //Company Alias
			String.class, //Batch no
			Date.class, //Model
			String.class //Batch no
	};
	Boolean[] COLUMN_EDITABLE = new Boolean[] {
			true,
			false, //Unit
			false, //Model
			false, //Buyer's Name
			false, //City
			false, //Province
			false, //Contact Person
			false, //Mobile No.
			false, //Email Address
			false, //Ref 2
			false, //Ref 2
			false, //Ref 2
			false, //Model
			false, //Buyer's Name
			false, //City
			false, //Province
			false, //Contact Person
			false, //Mobile No.
			false, //Email Address
			false, //Ref 2
			false, //Ref 2
			false, //Model
			false, //Buyer's Name
			false, //City
			false, //Province
			false, //Contact Person
			false, //Mobile No.
			false, //Email Address
			false, //Ref 2
			false, //Ref 2
			false //Ref 2
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

}
