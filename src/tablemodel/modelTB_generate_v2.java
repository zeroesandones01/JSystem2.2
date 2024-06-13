package tablemodel;

import java.math.BigDecimal;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class modelTB_generate_v2 extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8738810102880371204L;
	
	private boolean editable = false;
	boolean[] COLUMN_EDITABLE;
	
	
	public modelTB_generate_v2() {
		initThis();
	}

	public modelTB_generate_v2(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelTB_generate_v2(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelTB_generate_v2(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelTB_generate_v2(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelTB_generate_v2(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			"Account ID" ,		// 1
			"Account Name" ,	// 2
			"<html><center>Beg. Balance<html><br><html><center>Debit<html>", 	// 3
			"<html><center>Beg. Balance<html><br><html><center>Credit<html>" ,	// 4
			"<html><center>CRB<html><br><html><center>Debit<html>" ,	// 5
			"<html><center>CRB<html><br><html><center>Credit<html>" ,	// 6
			"<html><center>CV<html><br><html><center>Debit<html>" ,		// 7
			"<html><center>CV<html><br><html><center>Credit<html>" ,	// 8
			"<html><center>PV<html><br><html><center>Debit<html>" ,		// 9
			"<html><center>PV<html><br><html><center>Credit<html>" ,	// 10
			"<html><center>JV<html><br><html><center>Debit<html>" ,		// 11
			"<html><center>JV<html><br><html><center>Credit<html>" ,	// 12
			"<html><center>Period Trans.<html><br><html><center>Debit<html>" ,	// 13
			"<html><center>Period Trans.<html><br><html><center>Credit<html>" ,	// 14
			"<html><center>End Bal.<html><br><html><center>Debit<html>" ,	// 15
			"<html><center>End Bal.<html><br><html><center>Credit<html>" 	// 16
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
			BigDecimal.class, 	//Beg. Balance
			BigDecimal.class, 	//CRB
			BigDecimal.class, 	//CRB
			BigDecimal.class,	//CV
			BigDecimal.class,	//CV
			BigDecimal.class,	//PV
			BigDecimal.class,	//PV
			BigDecimal.class,	//JV
			BigDecimal.class,	//JV
			BigDecimal.class,	//Period Trans
			BigDecimal.class,	//Period Trans
			BigDecimal.class,	//End Balance
			BigDecimal.class	//End Balance			
			
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
		COLUMN_EDITABLE = new boolean[] {
				false, 		//Account ID
				false,		//Account Name
				false, 		//Beg. Balance (D)
				false, 		//Beg. Balance (C)
				false, 		//CRB (D) 
				false, 		//CRB (C)
				false,		//CV (D)
				false,		//CV (C)
				false,		//PV (D)
				false,		//PV (C)
				false,		//JV (D)
				false,		//JV (C)
				false,		//Period Trans (D)
				false,		//Period Trans (C)
				false,		//End Balance (D)				
				false		//End Balance (C)
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
