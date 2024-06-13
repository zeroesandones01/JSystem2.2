package tablemodel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class model_Block_client extends DefaultTableModel {

	public model_Block_client() {
		initThis();
	}

	public model_Block_client(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public model_Block_client(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public model_Block_client(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public model_Block_client(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public model_Block_client(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}
	private void initThis() {
		setColumnIdentifiers(new String[] {
			"Tag", // 0	
			"PBL", // 1
			"Unit", // 2
			"Description", // 3
			"Entity ID", // 4
			"Projcode", // 5
			"Seq", // 6
			"Unit ID", // 7
			"Client Name" // 8
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
			Boolean.class,
			String.class, //PBL
			String.class, //Unit
			String.class, //Description
			String.class, //Entity ID
			String.class, //Projcode
			Integer.class, //Seq
			String.class, //Unit ID
			String.class //Client Name
	};
	Boolean[] COLUMN_EDITABLE = new Boolean[] {
			true,
			false, //PBL
			false, //Unit
			false, //Description
			false, //Entity ID
			false, //Projcode
			false,  //Seq
			false,  //Unit ID
			false //Client Name
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
