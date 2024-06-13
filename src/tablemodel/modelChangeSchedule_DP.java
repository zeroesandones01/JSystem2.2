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

public class modelChangeSchedule_DP extends DefaultTableModel {

	
	private static final long serialVersionUID = -8109759391189685614L;
	private boolean editable = false;

	public modelChangeSchedule_DP() {
		initThis();
	}

	public modelChangeSchedule_DP(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelChangeSchedule_DP(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelChangeSchedule_DP(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelChangeSchedule_DP(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelChangeSchedule_DP(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}
	
	String [] COLUMNS = new String []{
		
		"Sched ID", //0
		"Part ID", //1
		"Old Sched", //2
		"New Sched", //3
		"Amount", //4
		"VAT", //5
		"Proc. Fee", //6 
		"Balance", //7
		"Status" //8
		
	};

	Class [] CLASS_TYPES = new Class []{
		
		String.class, //Sched ID
		String.class, //Part ID
		Timestamp.class, //Old Sched
		Timestamp.class, //New Sched
		BigDecimal.class, //Amount
		BigDecimal.class, //Vat
		BigDecimal.class, // Proc Fee
		BigDecimal.class, //Balance
		String.class //Status
		
	};
	
	Boolean [] COLUMNS_EDITABLE = new Boolean[]{
		
		false, //Sched ID
		false, //Part Id
		false, //Old Sched
		true, // New Sched
		false, //Amount
		false, // Vat
		true,  //Proc Fee
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
