package tablemodel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelCARD_LOG_Details extends DefaultTableModel {

	private static final long serialVersionUID = 1757805243787606949L;

	public modelCARD_LOG_Details() {
		initThis();
	}

	public modelCARD_LOG_Details(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelCARD_LOG_Details(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelCARD_LOG_Details(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelCARD_LOG_Details(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelCARD_LOG_Details(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			
			"Bank Name", //0,
			"Approved Amt", //1
			"Date Released", //2
			"Valid Until" //3
			
	};
	
	public Class[] CLASS_TYPES = new Class[] {
			
			String.class, //Bank Name
			BigDecimal.class, //Approved Amt
			Timestamp.class, //Date Released
			Timestamp.class //Effectivity
			
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
	}

	public Class getColumnClass(int columnIndex) {
		return CLASS_TYPES[columnIndex];
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	public void clear() {
		FncTables.clearTable(this);
	}

}
