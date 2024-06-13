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
public class modelChangeSchedule_MA extends DefaultTableModel {

	private static final long serialVersionUID = 1853340377418381168L;
	private boolean editable = false;

	public modelChangeSchedule_MA() {
		initThis();
	}

	public modelChangeSchedule_MA(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelChangeSchedule_MA(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelChangeSchedule_MA(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelChangeSchedule_MA(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}
	
	public modelChangeSchedule_MA(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}
	
	String [] COLUMNS = new String[]{
		
		"Sched ID", //0
		"Part ID", //1
		"Old Sched", //2
		"New Sched", //3
		"Amount", //4
		"VAT", //5
		"Balance", //6 
		"Status" //7
		
	};
	
	Class [] CLASS_TYPES = new Class[]{
		
		String.class, //Sched ID
		String.class, //Part ID
		Timestamp.class, //Old Sced
		Timestamp.class, //New Sched
		BigDecimal.class, //Amount
		BigDecimal.class, //VAT
		BigDecimal.class, //Balance
		String.class //Status
			
	};
	
	Boolean [] COLUMNS_EDITABLE = new Boolean[]{
		
		false, //Sched ID
		false, //Part ID
		false, //Old Sched
		true,  //New Sched
		false, //Amount
		false, //Vat
		false, //Balance
		false //Status
		
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
	
	public boolean isEditable(){
		return editable;
	}
	
	public void setEditable(boolean editable){
		this.editable = editable;
	}

}
