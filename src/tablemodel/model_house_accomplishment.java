package tablemodel;

import java.math.BigDecimal;
import java.security.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class model_house_accomplishment extends DefaultTableModel {
	
	private static final long serialVersionUID = 1L;

	public model_house_accomplishment() {
		initThis();
	}

	public model_house_accomplishment(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		// TODO Auto-generated constructor stub
	}

	public model_house_accomplishment(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		// TODO Auto-generated constructor stub
	}

	public model_house_accomplishment(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		// TODO Auto-generated constructor stub
	}

	public model_house_accomplishment(Vector data, Vector columnNames) {
		super(data, columnNames);
		// TODO Auto-generated constructor stub
	}

	public model_house_accomplishment(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		// TODO Auto-generated constructor stub
	}
	public String[] COLUMNS = new String[] {
			"UNIT", // 0
			"LOT", // 1
			"BLK", // 2
			"PHASE", // 3
			"CONTRACTOR", // 4
			"CONTRACT #", // 5
			"PROJECT", // 6
			"INSPECTOR", // 7
			"ACCOMPLISHMENT %", // 8
			"WORKER", // 9
			"REMARKS" // 10
	};
	public Class[] CLASS_TYPES = new Class[] {
			String.class, //UNIT
			String.class, //LOT
			String.class, //BLK
			String.class, //PHASE
			String.class, //CONTRACTOR
			String.class, //CONTRACT #
			String.class, //PROJECT
			String.class, //INSPECTOR
			String.class, //ACCOMPLISHMENT %
			String.class, //WORKER
			String.class //REMARKS
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
	}

	public Class getColumnClass(int columnIndex) {
		return CLASS_TYPES[columnIndex];
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		Boolean to = null;
		if(columnIndex==0) to = true;
		else to = false;
		return to;
	}

}
