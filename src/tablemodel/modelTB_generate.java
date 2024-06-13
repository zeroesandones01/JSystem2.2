package tablemodel;

import java.math.BigDecimal;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class modelTB_generate extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8738810102880371204L;
	
	private boolean editable = false;
	boolean[] COLUMN_EDITABLE;
	
	
	public modelTB_generate() {
		initThis();
	}

	public modelTB_generate(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelTB_generate(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelTB_generate(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelTB_generate(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelTB_generate(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			"Account ID" ,		// 1
			"Account Name" ,	// 2
			"Beg. Balance" ,	// 3
			"CRB" ,				// 4
			"CV " ,				// 5
			"PV " ,				// 6
			"JV" ,				// 7
			"Period Trans" ,	// 8
			"End Balance" 		// 9
			};

	
	/**
	 * String.class = Alignment.CENTER
	 * Object.class = Alignment.LEFT
	 * BigDecimal.class = Alignment.RIGHT
	 * Integer.class = Alignment.CENTER
	 * Timestamp.class
	 */
	public Class[] CLASS_TYPES = new Class[] {
			String.class, 		//Account ID
			Object.class,		//Account Name
			BigDecimal.class, 	//Beg. Balance
			BigDecimal.class, 	//CRB
			BigDecimal.class,	//CV
			BigDecimal.class,	//PV
			BigDecimal.class,	//JV
			BigDecimal.class,	//Period Trans
			BigDecimal.class	//End Balance
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
		COLUMN_EDITABLE = new boolean[] {
				false, 		//Account ID
				false,		//Account Name
				false, 		//Beg. Balance
				false, 		//CRB
				false,		//CV
				false,		//PV
				false,		//JV
				false,		//Period Trans
				false		//End Balance
		};
	}

	public Class getColumnClass(int columnIndex) {
		return CLASS_TYPES[columnIndex];
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return COLUMN_EDITABLE[columnIndex];
	}

	public boolean isEditable() {
		return editable;
	}

	

	
	
	
}
