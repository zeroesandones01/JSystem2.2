package tablemodel;

import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class model_unit_turnover extends DefaultTableModel {

	public model_unit_turnover() {
		initThis();
	}

	public model_unit_turnover(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public model_unit_turnover(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public model_unit_turnover(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public model_unit_turnover(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public model_unit_turnover(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}
	private void initThis() {
		setColumnIdentifiers(new String[] {
			"Tag", // 0
			"Unit", // 1
			"Model", // 2
			"Buyer's Name", // 3
			"Addr_no", // 4
			"Street", // 5
			"City", // 6
			"Province", // 7
			"Entity ID", // 8
			"Company Alias", // 9
			"Batch no", // 10
			"Notice Alias", // 11
			"Municipality", // 12
			"Zip Code", // 13
			"PBL ID", // 14
			"Seq No", // 15
			"Color Scheme", // 16
			"With NTC", // 17
			"NTC Batch No", //18
			"House Const", // 19
			"Move-in Date", // 20
			"Turn-over Date", // 21
			"Final Orientation", // 22
			"NTC", // 23
			"House constructed", // 24
			"Loan Released Date" // 24
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
			Boolean.class, //Tag
			Object.class, //Unit
			String.class, //Model
			Object.class, //Buyer's Name
			String.class, //Addr_no
			String.class, //Street
			String.class, //City
			String.class, //Province
			String.class, //Entity ID
			String.class, //Company Alias
			String.class, //Batch no
			String.class, //Notice Alias
			String.class, //Notice Alias
			String.class, //Street
			String.class, //City
			String.class, //Province
			String.class, //Entity ID
			String.class, //Company Alias
			String.class, //Batch no
			String.class, //Notice Alias
			String.class, //Notice Alias
			String.class, //Notice Alias
			String.class, //Notice Alias
			Date.class, //Notice Alias
			Date.class, //Notice Alias
			Date.class //Notice Alias
			
	};
	Boolean[] COLUMN_EDITABLE = new Boolean[] {
			true, //Tag
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
			false, //Ref 2
			false, //Province
			false, //Contact Person
			false, //Mobile No.
			false, //Email Address
			false, //Ref 2
			false, //Ref 2
			false, //Ref 2
			false, //Ref 2
			false, //Ref 2
			false, //Ref 2
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
