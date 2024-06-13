package tablemodel;

import java.math.BigDecimal;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class modelLiquidationSOA_2 extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8738810102880371204L;
	
	private boolean editable = false;
	boolean[] COLUMN_EDITABLE;
	
	
	public modelLiquidationSOA_2() {
		initThis();
	}

	public modelLiquidationSOA_2(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelLiquidationSOA_2(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelLiquidationSOA_2(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelLiquidationSOA_2(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelLiquidationSOA_2(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			"Tag", //0
			"Emp. Code", //1
			"Emp. Name", //2
			"No. of Unliquidated CA", //3
			"Total Unliquidated CA"   //4
			};

	
	/**
	 * String.class = Alignment.CENTER
	 * Object.class = Alignment.LEFT
	 * BigDecimal.class = Alignment.RIGHT
	 * Integer.class = Alignment.CENTER
	 * Timestamp.class
	 */
	public Class[] CLASS_TYPES = new Class[] {
			Boolean.class,
			String.class, 		//Emp. Code
			Object.class, 		//Emp. Name
			Integer.class, 		//No. of Unliquidated CA
			BigDecimal.class 	//Total Unliquidated CA			
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
		COLUMN_EDITABLE = new boolean[] {
				true,
				false, 		//Emp. Code
				false, 		//Emp. Name
				false, 		//No. of Unliquidated CA
				false 		//Total Unliquidated CA
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
					true,
					false, 		//Emp. Code
					false, 		//Emp. Name
					false, 		//No. of Unliquidated CA
					false 		//Total Unliquidated CA
			};
		}else{
			COLUMN_EDITABLE = new boolean[] {
					false,
					false, 		//Emp. Code
					false, 		//Emp. Name
					false, 		//No. of Unliquidated CA
					false 		//Total Unliquidated CA
			};
		}
	}

	
	
	
}
