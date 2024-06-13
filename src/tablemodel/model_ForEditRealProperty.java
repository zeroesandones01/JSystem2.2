package tablemodel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;


public class model_ForEditRealProperty extends DefaultTableModel {

	private static final long serialVersionUID = 1L;
	
	public model_ForEditRealProperty() {
		initThis();
	}

	public model_ForEditRealProperty(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public model_ForEditRealProperty(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public model_ForEditRealProperty(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public model_ForEditRealProperty(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public model_ForEditRealProperty(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}
	private void initThis() {
		setColumnIdentifiers(new String[] {
		    "TAG",//0
		    "Batch No",//1
		    "RPLF No",//2
		    "Client ID",//3
			"Client Name", //4
			"Phase",//5
			"Block",//6
			"Lot", // 7
			"RPT LOT", // 8
			"RPT LOT YEAR", // 9
			"RPT LOT OR NO", //10	
			"RPT LOT AMOUNT PAID", // 11
			"RPT HOUSE", // 12
			"RPT HOUSE YEAR", //13
			"RPT HOUSE OR NO", //14
			"RPT HOUSE AMOUNT PAID", // 15
			"RPT LOT PROC ID", // 16
			"RPT HOUSE PROC ID", // 17

	});
	}
	
	Class[] CLASS_TYPES = new Class[] {
			Boolean.class,//0
			String.class,//1
			String.class,//2
			String.class,//3
			String.class,//4
			String.class,//5
			String.class,//6
			String.class,//7
			BigDecimal.class,//8
			Integer.class,//9
			String.class,//10
			BigDecimal.class,//11 
			String.class,//12 
			Integer.class,//13
			String.class,//14 
			BigDecimal.class,//15
			Integer.class,//16
			Integer.class,//17


	};
	Boolean[] COLUMN_EDITABLE = new Boolean[] {
			true,//0
			false,//1
			false,//2 
			false,//3
			false,//4 
			false,//5 
			false,//6 
			false,//7
			true,//8
			false,//9 
			true,//10
			false,//11
			true,//12
			false,//13 
			true,//14 
			false,//15 
			false,//16
			false,//17

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