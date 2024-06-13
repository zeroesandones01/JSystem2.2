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
public class modelFI_ForPmtInsurance extends DefaultTableModel {

	private static final long serialVersionUID = -7072639371345957991L;

	private boolean editable = false;
	
	public modelFI_ForPmtInsurance() {
		initThis();
	}

	public modelFI_ForPmtInsurance(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelFI_ForPmtInsurance(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelFI_ForPmtInsurance(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelFI_ForPmtInsurance(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelFI_ForPmtInsurance(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}
	
	String [] COLUMNS = new String[]{
		
		"Insurance Co.", //0 
		"Invoice", //1
		"RPLF No", //2
		"Check Date", //3
		"Check Amt" //4
		
	};
	
	Class [] CLASS_TYPES = new Class[]{
		
		String.class, //Insurance Co
		String.class, //Invoice
		String.class, //RPLF No
		Timestamp.class, //Check Date
		BigDecimal.class //Check Amt
		
	};
	
	Boolean [] COLUMNS_EDITABLE = new Boolean[]{
		
		false, //Insurance Co
		false, //Invoice No
		false, //RPLF No
		false, //Check Date
		false //Check Amt
		
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
