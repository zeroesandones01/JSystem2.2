/**
 * 
 */
package tablemodel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

/**
 * @author John Lester Fatallo
 */
public class modelBankInfo_Schedule extends DefaultTableModel {
	
	private static final long serialVersionUID = -3903932995498438613L;
	private boolean editable = false;

	public modelBankInfo_Schedule() {
		initThis();
	}

	public modelBankInfo_Schedule(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelBankInfo_Schedule(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelBankInfo_Schedule(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelBankInfo_Schedule(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelBankInfo_Schedule(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}
	
	String [] COLUMNS = new String []{
		
		"Part", //0
		"Schedule", //1 
		"Amount", //2
		"Other Fees", //3 
		"MRI", //4
		"Fire", //5
		"VAT", //6
		"HGC", //7
		"Interest", //8 
		"Principal", //9
		"Balance", //10
		"Int. Rate" //11
		
	};
	
	Class [] CLASS_TYPES = new Class[]{
			
		String.class, //Part
		Timestamp.class, //Schedule
		BigDecimal.class, //Amount
		BigDecimal.class, //Other Fees
		BigDecimal.class, //MRI
		BigDecimal.class, //Fire
		BigDecimal.class, //VAT
		BigDecimal.class, //HGC
		BigDecimal.class, //Interest
		BigDecimal.class, //Principal
		BigDecimal.class, //Balance
		BigDecimal.class //Int Rate
		
	};
	
	Boolean [] COLUMNS_EDITABLE = new Boolean[]{
		
		false, //Part
		false, //Schedule
		false, //Amount
		false, //Other Fees
		false, //MRI
		false, //Fire
		false, //VAT
		false, //HGC
		false, //Interest
		false, //Principal
		false, //Balance
		false //Int Rate
		
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
