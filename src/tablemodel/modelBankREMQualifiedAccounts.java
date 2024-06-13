/**
 * 
 */
package tablemodel;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

/**
 * @author John Lester Fatallo
 */
public class modelBankREMQualifiedAccounts extends DefaultTableModel {

	private static final long serialVersionUID = 7736359282145827942L;
	
	private boolean editable = false;

	public modelBankREMQualifiedAccounts() {
		initThis();
	}

	public modelBankREMQualifiedAccounts(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelBankREMQualifiedAccounts(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelBankREMQualifiedAccounts(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelBankREMQualifiedAccounts(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelBankREMQualifiedAccounts(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}
	
	String [] COLUMNS = new String[]{
			
		"Select", //0
		"Entity ID", //1
		"Client Name", //2
		"Ph-Blk-Lt", //3
		"Proj. ID", //4
		"Proj. Name", //5
		"Unit ID", //6
		"PBL", //7
		"Seq." //8
		
	};
	
	Class [] CLASS_TYPES = new Class []{
			
		Boolean.class, //Select
		String.class, //Entity ID
		String.class, //Client Name
		String.class, //Ph-Blk-Lt
		String.class, //Proj. ID
		String.class, //Proj. Name
		String.class, //Unit ID
		String.class, //PBL
		Integer.class //Seq
		
	};
	
	Boolean [] COLUMNS_EDITABLE = new Boolean[]{
			
		true, //Select
		false, //Entity ID
		false, //Client Name
		false, //Ph-Blk-Lt
		false, //Proj. ID
		false, //Proj. Name
		false, //Unit ID
		false, //PBL
		false //Seq
		
	};

	private void initThis(){
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
