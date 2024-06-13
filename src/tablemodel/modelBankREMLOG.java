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
 * @author John Lester Fatallo
 */
public class modelBankREMLOG extends DefaultTableModel {

	private static final long serialVersionUID = 8654375987318910521L;
	
	private boolean editable = false;

	public modelBankREMLOG() {
		initThis();
	}

	public modelBankREMLOG(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelBankREMLOG(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelBankREMLOG(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelBankREMLOG(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelBankREMLOG(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}
	
	String [] COLUMNS = new String []{
		"Bank", //0
		"Date Released", //1
		"Amount", //2
		"Expiry", //3
		"REM Fees", //4
		"Date Paid", //5
		"MRI/Fire Fees", //6 
		"Date Tagged", //7
		"Tagged By", //8
		"Bank ID", //9
		"Update By", //10
		"Bank REM Log ID" //11
	};
	
	Class [] CLASS_TYPES = new Class[]{
		
		String.class, //Bank
		Timestamp.class, //Date Released
		BigDecimal.class, //Amount
		Timestamp.class, //Expiry
		BigDecimal.class, //REM Fees
		Timestamp.class, //Date Paid
		BigDecimal.class, //MRI/Fire Fees
		Timestamp.class, //Date Tagged
		String.class, //Tagged By
		String.class, //Bank ID
		String.class, //Update By
		Integer.class //Bank REM Log ID
		
	};
	
	Boolean [] COLUMNS_EDITABLE = new Boolean []{
		false, //Bank
		false, //Date Released
		false, //Amount
		false, //Expiry
		false, //REM Fees
		false, //Date Paid
		false, //MRI/Fire Fees
		false, //Date Tagged
		false, //Tagged By
		false, //Bank ID
		false, //Update BY
		false //Bank REM Log ID
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
