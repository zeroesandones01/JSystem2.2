package tablemodel;

import java.math.BigDecimal;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class model_water_billing extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8738810102880371204L;
	
	public model_water_billing() {
		initThis();
	}

	public model_water_billing(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public model_water_billing(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public model_water_billing(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public model_water_billing(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public model_water_billing(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}
	private void initThis() {
	setColumnIdentifiers(new String[] {
		"Select",
		"Unit", 	// 0
		"Homeowner's Name", // 3
		"Prev Rdng",// 4
		"Curr Rdng",// 5
		"Water Cost", // 6
		"Garbage Fee", // 7
		"Amount Due", //8
		"Remarks", // 9
		"PBL", 	   // 10
		"Entity", // 11
		"Proj ID", // 12
		"Seq No." // 13
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
		String.class,  //Unit
		Object.class,  //Homeowner's Name
		BigDecimal.class, //Prev Rdng
		Integer.class, //Curr Rdng
		BigDecimal.class, //Water Cost
		BigDecimal.class, //Garbage Fee
		BigDecimal.class, //Amount Due
		Object.class, //Remarks
		String.class, //PBL
		String.class, //Entity
		String.class, //Proj ID
		String.class  //Seq No


	};
	
	Boolean[] COLUMN_EDITABLE = new Boolean[] {
		true,
		false, //Unit
		false, //Homeowner's Name
		true, //Prev Rdng
		true, //Curr Rdng
		false, //Water Cost
		false, //Garbage Fee
		false, //Amount Due
		false, //Remarks
		false, //PBL
		false, //Entity
		false, //Proj ID
		false  //Seq No
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
