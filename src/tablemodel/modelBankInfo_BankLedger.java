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

public class modelBankInfo_BankLedger extends DefaultTableModel {

	private static final long serialVersionUID = 3387676990576909787L;
	private boolean editable = false;

	public modelBankInfo_BankLedger() {
		initThis();
	}

	public modelBankInfo_BankLedger(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelBankInfo_BankLedger(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelBankInfo_BankLedger(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelBankInfo_BankLedger(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelBankInfo_BankLedger(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}
	
	String [] COLUMNS = new String[]{
			
		"Date Paid", //0
		"Applied Date", //1
		"Schedule", //2
		"Amount Paid", //3
		"HGC", //4
		"Interest", //5
		"Principal", //6
		"Balance" //7
		
	};
	
	Class [] CLASS_TYPES = new Class[]{
			
		Timestamp.class, //Date Paid
		Timestamp.class, //Applied Date
		Timestamp.class, //Schedule
		BigDecimal.class, //Amount Paid
		BigDecimal.class, //HGC
		BigDecimal.class, //Interest
		BigDecimal.class, //Principal
		BigDecimal.class //Balance
		
	};
	
	Boolean [] COLUMNS_EDITABLE = new Boolean[]{
		
		false, //Date Paid
		false, //Applied Date
		false, //Schedule
		false, //Amount Paid
		false, //HGC
		false, //Interest
		false, //Principal
		false //Balance
		
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
