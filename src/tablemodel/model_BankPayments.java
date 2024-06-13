package tablemodel;

import java.math.BigDecimal;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class model_BankPayments extends DefaultTableModel {
	String type = null;

	public model_BankPayments() {
		initThis();
	}
	
	public model_BankPayments(String total) {
		type = total;
		initThis_total();
	}

	public model_BankPayments(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public model_BankPayments(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public model_BankPayments(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public model_BankPayments(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public model_BankPayments(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			"Tag",				//0
			"Lump",				//1
			"Project",			//2
			"Ph-Blk-Lt",		//3
			"Client's Name",	//4
			"Amount",			//5
			"Entity ID",		//6
			"Proj ID",			//7
			"Pbl ID",			//8
			"Seq No",			//9
			"Balance",			//9
	};
	
	public Class[] CLASS_TYPES = new Class[] {
			Boolean.class,		//0
			String.class,		//1
			String.class,		//2
			String.class,		//3
			String.class,		//4
			BigDecimal.class,	//5
			String.class,		//6
			String.class,		//7
			String.class,		//8
			String.class,		//9
			BigDecimal.class,		//10
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
				false,			//7
				false,			//8
				false,			//9
				false,			//10
		};
	}
	
	//

	public String[] COLUMNS_TOTAL = new String[] {
			"Total",			//0
			"Lump",				//1
			"Project",			//2
			"Ph-Blk-Lt",		//3
			"Client's Name",	//4
			"Total Amount",		//5
			"Entity ID",		//6
			"Proj ID",			//7
			"Pbl ID",			//8
			"Seq No",			//9
			"Balance",			//10
	};
	
	public Class[] CLASS_TYPES_TOTAL = new Class[] {
			String.class,		//0
			String.class,		//1
			String.class,		//2
			String.class,		//3
			String.class,		//4
			BigDecimal.class,	//5
			String.class,		//6
			String.class,		//7
			String.class,		//8
			String.class,		//9
			BigDecimal.class,	//10
	};

	private boolean[] COLUMNS_EDITABLE_TOTAL;

	private void initThis_total() {
		setColumnIdentifiers(COLUMNS_TOTAL);
		COLUMNS_EDITABLE_TOTAL = new boolean[] {
				false,			//0
				false,			//1
				false,			//2
				false,			//3
				false,			//4
				false,			//5
				false,			//6
				false,			//7
				false,			//8
				false,			//9
				false,			//10
		};
	}

	public Class getColumnClass(int columnIndex) {
		if(type != null) {
			return CLASS_TYPES_TOTAL[columnIndex];
		}else {
			return CLASS_TYPES[columnIndex];
		}
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if(type != null) {
			return COLUMNS_EDITABLE_TOTAL[columnIndex];
		}else {
			return COLUMNS_EDITABLE[columnIndex];
		}
	}

	public void clear() {
		FncTables.clearTable(this);
	}

}
