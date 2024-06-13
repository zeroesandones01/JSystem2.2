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
public class modelBankREMRelease extends DefaultTableModel {
	
	private static final long serialVersionUID = -1487965172003913272L;
	private boolean editable = false;

	public modelBankREMRelease() {
		initThis();
	}

	public modelBankREMRelease(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelBankREMRelease(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelBankREMRelease(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelBankREMRelease(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelBankREMRelease(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}
	
	String [] COLUMNS = new String[]{
			
		"Loan Release Date", //0
		"Undertaking Expiry Date", //1
		"DOAS Transmitted to LLD", //2
		"Transfer Cost Paid" //3
		
	};
	
	Class [] CLASS_TYPES = new Class []{
		Timestamp.class, //Loan Release Date
		Timestamp.class, //Undertaking Expiry Date
		Timestamp.class, //DOAS Transmitted to LLD
		BigDecimal.class //Transfer Cost Paid
	};
	
	Boolean [] COLUMNS_EDITABLE = new Boolean []{
		false, //Loan Release Date
		false, //Undertaking Expiry Date
		false, //DOAS Transmitted to LLD
		false //Transfer Cost Paid
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
