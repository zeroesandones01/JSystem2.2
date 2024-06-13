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
public class modelBankInfo_Payments extends DefaultTableModel {

	private static final long serialVersionUID = 7798308207934329396L;
	private boolean editable = false;

	public modelBankInfo_Payments() {
		initThis();
	}

	public modelBankInfo_Payments(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelBankInfo_Payments(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelBankInfo_Payments(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelBankInfo_Payments(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelBankInfo_Payments(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}
	
	String [] COLUMNS = new String []{
		
		"Date Processed", //0
		"Amount", //1
		"LumpSum", //2
		"RPLF No.", //3
		"Date Released", //4
		"Batch No", //5
		"Remarks", //6
		
	};
	
	Class [] CLASS_TYPES = new Class []{
		
		Timestamp.class, //Date Processed
		BigDecimal.class, //Amount
		Boolean.class, //Lumpsum
		String.class, //RPLF No
		Timestamp.class, //Date Released
		String.class, //Batch No
		String.class, //Remarks
		
	};
	
	Boolean [] COLUMNS_EDITABLE = new Boolean []{
		
		false, //Date Processed
		false, //Amount	
		false, //LumpSum
		false, //RPLF No
		false, //Date Released
		false, //batch No
		false //Remarks
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
