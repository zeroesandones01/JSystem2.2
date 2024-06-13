package tablemodel;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class model_garbage_fee_for_issuance extends DefaultTableModel {

	public model_garbage_fee_for_issuance() {
		initThis();
	}

	public model_garbage_fee_for_issuance(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public model_garbage_fee_for_issuance(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public model_garbage_fee_for_issuance(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public model_garbage_fee_for_issuance(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public model_garbage_fee_for_issuance(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			"Tag",					//0
			"Client Seq No.",		//1
			"Client Name",			//2
			"Amount",				//3
			"Trans. Date",			//4
			"Unit Description",		//5
			"Project",				//6
	};
	
	public Class[] CLASS_TYPES = new Class[] {
			Boolean.class,		//0
			String.class,		//1
			String.class,		//2
			BigDecimal.class,	//3
			Date.class,			//4
			String.class,		//5
			String.class,		//6
	};

	private boolean[] COLUMNS_EDITABLE;

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
		COLUMNS_EDITABLE = new boolean[] {
				true,			//0
				false,			//1
				false,			//2
				false,			//3
				false,			//4
				false,			//5
				false,			//6
		};
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

}
