package tablemodel;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;
import Lookup._JLookup;

public class model_buyback extends DefaultTableModel {
	String type = null;

	public model_buyback() {
		initThis();
	}
	
	public model_buyback(String total) {
		type = total;
		initThis_total();
	}

	public model_buyback(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public model_buyback(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public model_buyback(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public model_buyback(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public model_buyback(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			"Tag",				//0
			"Status",			//1
			"Project",			//2
			"Ph-Blk-Lt",		//3
			"Client's Name",	//4
			"Entity ID",		//5
			"Proj ID",			//6
			"Pbl ID",			//7
			"Seq No",			//8
			"Out Balance",		//9
			"Interest",			//10
			"Int Rate",			//11
			"Total",			//12
			"Reason",			//13
			"Ledger Date",		//14
	};
	
	public Class[] CLASS_TYPES = new Class[] {
			Boolean.class,		//0
			String.class,		//1
			String.class,		//2
			String.class,		//3
			String.class,		//4
			String.class,		//5
			String.class,		//6
			String.class,		//7
			String.class,		//8
			BigDecimal.class,	//9
			BigDecimal.class,	//10
			BigDecimal.class,	//11
			BigDecimal.class,	//12
			_JLookup.class,		//13
			Date.class,			//14
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
				true,			//9
				true,			//10
				true,			//11
				false,			//12
				false,			//13
				false,			//14
		};
	}
	
	//

	public String[] COLUMNS_TOTAL = new String[] {
			"Total",			//0
			"Status",			//1
			"Project",			//2
			"Ph-Blk-Lt",		//3
			"Client's Name",	//4
			"Entity ID",		//5
			"Proj ID",			//6
			"Pbl ID",			//7
			"Seq No",			//8
			"Out Balance",		//9
			"Interest",			//10
			"Int Rate",			//11
			"Total",			//12
			"Reason",			//13
			"Ledger Date",		//14
	};
	
	public Class[] CLASS_TYPES_TOTAL = new Class[] {
			String.class,		//0
			String.class,		//1
			String.class,		//2
			String.class,		//3
			String.class,		//4
			String.class,		//5
			String.class,		//6
			String.class,		//7
			String.class,		//8
			BigDecimal.class,	//9
			BigDecimal.class,	//10
			BigDecimal.class,	//11
			BigDecimal.class,	//12
			_JLookup.class,		//13
			Date.class,			//14
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
				true,			//9
				true,			//10
				true,			//11
				false,			//12
				false,			//13
				false,			//14
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
