package tablemodel;

import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class model_Sales_Contract extends DefaultTableModel {

	private static final long serialVersionUID = 1L;

	public model_Sales_Contract() {
		initThis();
	}

	public model_Sales_Contract(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public model_Sales_Contract(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public model_Sales_Contract(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public model_Sales_Contract(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public model_Sales_Contract(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	private void initThis() {
		setColumnIdentifiers(new String[] {
				"Select", 			//0
				"Agent Name", 	//1
				"<html><div align=center><font color=blue>Date Contract<br>Submitted</font></div></html>",		//9
				"BDT", 			//2
				"Group", 		//3
				"Type", 		//4
				"Position", 	//5
				"Accredit From",//6
				"Accredit To", 	//7
				"PRC No.", 		//8
				"HLURB No."		//9
				//"<html><div align=center>Date<br>Cancelled</div></html>", // 3
				//"<html><font color=red>*<i> Status Date</i></html>", //6
				
		});
	}

	Class[] COLUMN_TYPES = new Class[] {
			Boolean.class,//"Tag", // 0
			Object.class,//"Agent Name", // 1
			Timestamp.class,//
			String.class,//"BDT", // 2
			String.class,//"Group", // 3
			String.class,//"Type", //4
			String.class,//"Position", //5
			Timestamp.class,//"Accredit From", //6
			Timestamp.class,//"Accredit To", //7
			String.class,//"PRC No.", //8
			String.class//"HLURB No.",// 9
			
	};

	Boolean[] COLUMN_EDITABLE = new Boolean[] {
			true,//"Tag", // 0
			false,//"Agent Name", // 1
			false,
			false,//"BDT", // 2
			false,//"Group", // 3
			false,//"Type", //4
			false,//"Position", //5
			false,//"Accredit From", //6
			false,//"Accredit To", //7
			false,//"PRC No.", //8
			false//"HLURB No.",// 9
			
	};

	public Class getColumnClass(int columnIndex) {
		return COLUMN_TYPES[columnIndex];
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return COLUMN_EDITABLE[columnIndex];
	}
	
	public void clear() {
		FncTables.clearTable(this);
	}

}
