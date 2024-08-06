package tablemodel;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelG2GTcostTagging extends DefaultTableModel {

	public modelG2GTcostTagging() {
		initThis();
	}

	public modelG2GTcostTagging(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelG2GTcostTagging(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelG2GTcostTagging(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelG2GTcostTagging(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelG2GTcostTagging(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}
	
	public String[] COLUMNS = new String[] {
			"Tag",					//0
			"Client Name",			//1
			"Unit Description",		//2
			"Amount",				//3
			"JV No",				//4
			"Entity ID", 			//5
			"Proj ID",				//6
			"PBL ID",				//7
			"Seq No"				//8
	};
	
	public Class[] CLASS_TYPES = new Class[] {
			Boolean.class,		//0
			String.class,		//1
			String.class,		//2
			BigDecimal.class,	//3
			String.class,		//4 
			String.class,		//5
			String.class, 		//6
			String.class,		//7
			Integer.class		//8
	};

	private boolean[] COLUMNS_EDITABLE;

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
		COLUMNS_EDITABLE = new boolean[] {
				true,			//0
				false,			//1
				false,			//2
				true,			//3
				false, 			//4
				false, 			//5
				false, 			//6
				false, 			//7
				false			//8
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
