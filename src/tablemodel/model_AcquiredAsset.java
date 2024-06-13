package tablemodel;

import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class model_AcquiredAsset extends DefaultTableModel{
private static final long serialVersionUID = 1L;
	
	public model_AcquiredAsset() {
		initThis();
	}

	public model_AcquiredAsset(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public model_AcquiredAsset(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public model_AcquiredAsset(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public model_AcquiredAsset(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public model_AcquiredAsset(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}
	private void initThis() {
		setColumnIdentifiers(new String[] {
			"Tag", // 0
			"Trans Date", // 1
			"Buyer's Name", // 2
			"Phase-Block-Lot", // 3
			"Model", //4
			"PBL ID", // 5
			"Entity ID", //6
			"Seq No" //7
			//no
	});
	}
	
	/**
	 * String.class = Alignment.CENTER
	 * Object.class = Alignment.LEFT
	 * BigDecimal.class = Alignment.RIGHT
	 * Integer.class = Alignment.CENTER
	 * 
	 */
	Class[] CLASS_TYPES = new Class[] {
			Boolean.class, //Tag
			Timestamp.class, //Trans Date
			Object.class, //Remarks
			String.class, //Unit ID
			String.class, //Unit ID
			String.class, //Unit ID
			String.class, //Unit ID
			Integer.class //Unit ID
	};
	
	Boolean[] COLUMN_EDITABLE = new Boolean[] {
			true, //Tag
			false, //Phase-Block-Lot
			false, //Trans Date
			false, //Docs #
			false, //Unit ID
			false, //JV #
			false, //Remarks
			false //Unit ID
	};


	public Class getColumnClass(int columnIndex) {
		return CLASS_TYPES[columnIndex];
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return COLUMN_EDITABLE[columnIndex];
	}

	public void clear() {
		FncTables.clearTable(this);
		
	}


}
