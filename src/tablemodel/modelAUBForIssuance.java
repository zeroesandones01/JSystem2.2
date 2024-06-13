package tablemodel;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelAUBForIssuance extends DefaultTableModel {

	private static final long serialVersionUID = -907548850828505313L;

	public modelAUBForIssuance() {
		initThis();
	}

	public modelAUBForIssuance(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		// TODO Auto-generated constructor stub
	}

	public modelAUBForIssuance(Vector<?> columnNames, int rowCount) {
		super(columnNames, rowCount);
		// TODO Auto-generated constructor stub
	}

	public modelAUBForIssuance(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		// TODO Auto-generated constructor stub
	}

	public modelAUBForIssuance(Vector<? extends Vector> data, Vector<?> columnNames) {
		super(data, columnNames);
		// TODO Auto-generated constructor stub
	}

	public modelAUBForIssuance(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		// TODO Auto-generated constructor stub
	}
	
	public String[] COLUMNS = new String[] {
			"Tag",					//0
			"Client Seq No.",		//1
			"Client Name",			//2
			"Particular",			//
			"Amount",				//3
			"Trans. Date",			//4
			"Unit Description",		//5
			"Project",				//6
	};
	
	public Class[] CLASS_TYPES = new Class[] {
			Boolean.class,		//0
			String.class,		//1
			String.class,		//2
			String.class,
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
				false,
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
