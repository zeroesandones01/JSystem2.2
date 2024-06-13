/**
 * 
 */
package tablemodel;

import java.math.BigDecimal;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

/**
 * @author John Lester Fatallo
 */
public class modelSORReturnedAccounts extends DefaultTableModel {

	private static final long serialVersionUID = 4581616925789938700L;
	private boolean editable = false;

	public modelSORReturnedAccounts() {
		initThis();
	}

	public modelSORReturnedAccounts(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelSORReturnedAccounts(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelSORReturnedAccounts(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelSORReturnedAccounts(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelSORReturnedAccounts(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}
	
	String [] COLUMNS = new String []{
		
		"Select", //0
		"Rec. ID", //1
		"Proj. ID", //2
		"Unit ID", //3
		"Seq", //4
		"Ph", //5
		"Blk.", //6
		"Lot", //7
		"Entity ID", //8
		"Client Name", //9
		"Sold", //10
		"Reason", //11
		"Batch No" //12
	};
	
	Class [] CLASS_TYPES = new Class []{
		 
		Boolean.class, //Select
		Integer.class, //Rec ID
		String.class, //Proj. ID
		String.class, //Unit ID
		Integer.class, //Seq
		String.class, //Ph
		String.class, //Blk
		String.class, //Lot
		String.class, //Entity ID
		String.class, //Client Name
		BigDecimal.class, //Sold
		String.class, //Reason
		String.class //Batch No
		
	};
	
	Boolean [] COLUMNS_EDITABLE = new Boolean []{
		
		true, //Select 
		false, //Rec ID
		false, //Proj ID
		false, //Unit ID
		false, //Seq
		false, //Ph
		false, //Blk
		false, //Lot
		false, //Entity ID
		false, //Client Name
		false, //Sold
		true, //Reason
		false //Batch No
			
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
