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
public class modelBankREMStatus extends DefaultTableModel {

	private static final long serialVersionUID = 4606864895347622837L;
	private boolean editable = false;

	public modelBankREMStatus() {
		initThis();
	}

	public modelBankREMStatus(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelBankREMStatus(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelBankREMStatus(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelBankREMStatus(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelBankREMStatus(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}
	
	String [] COLUMNS = new String[]{
		"Status Description", //0
		"Status Date", //1
		"F.I.", //2
		"Rec. Status", //3
		"Status ID", //4
		"Rec. ID" //5
	};
	
	Class [] CLASS_TYPES = new Class[]{
		String.class, //Status Description
		Timestamp.class, //Status Date
		BigDecimal.class, //F.I.
		String.class, //Rec. Status
		String.class, //Status ID
		Integer.class //Rec. ID
	};
	
	Boolean [] COLUMNS_EDITABLE = new Boolean[]{
		false, //Status Description
		false, //Status Date
		false, //F.I.
		false, //Rec. Status
		false, //Status ID
		false //Rec ID
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
