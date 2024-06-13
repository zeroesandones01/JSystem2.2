/**
 * 
 */
package tablemodel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

/**
 * @author JLF
 */
public class modelROP_Holding extends DefaultTableModel {

	private static final long serialVersionUID = 4983722246500676225L;
	private boolean editable = false;

	public modelROP_Holding() {
		initThis();
	}

	public modelROP_Holding(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelROP_Holding(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelROP_Holding(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelROP_Holding(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelROP_Holding(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}
	
	String[] COLUMNS = new String[] {
			"Actual Date", // 0
			"Pay Part ID", //1
			"Particular", // 2
			"Amount", //3
			"Rec ID", //4
			"Receipt No"// 5
	};
	
	Class[] CLASS_TYPES = new Class[] {
			Timestamp.class, //Actual Date
			String.class, //Pay Part ID
			String.class, //Particular
			BigDecimal.class, //Amount
			Integer.class, //Rec ID
			String.class //Receipt No
	};
	
	Boolean[] COLUMNS_EDITABLE = new Boolean[] {
			
			false, // 1
			false, // 2
			false, // 3
			false, //4
			false, //5
			false //6
	};
	
	private void initThis() {
		setColumnIdentifiers(COLUMNS);
	}
	
	public Class getColumnClass(int columnIndex) {
		return CLASS_TYPES[columnIndex];
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return COLUMNS_EDITABLE[columnIndex];
	}
	
	public void clear() {
		FncTables.clearTable(this);
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

}
