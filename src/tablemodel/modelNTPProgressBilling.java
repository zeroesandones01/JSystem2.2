package tablemodel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelNTPProgressBilling extends DefaultTableModel {

	private static final long serialVersionUID = 1L;
	
	private boolean editable = false;
	boolean[] COLUMN_EDITABLE;

	public modelNTPProgressBilling() {
		initThis();
	}

	public modelNTPProgressBilling(boolean editable) {
		initThis();
	}

	public modelNTPProgressBilling(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelNTPProgressBilling(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelNTPProgressBilling(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelNTPProgressBilling(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelNTPProgressBilling(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}
	
	String[] COLUMNS = new String[] {
			"Bill Type", 
			"Tran data",
			"Accomp %",
			"Gross Amt",
			"VAT",
			"WTAX Amt",
			"DP Recoupment",
			"Retention Amt",
			"BC Liquidation",
			"Other Deduction",
			"Net Amt",
			"Bill No.",
			"RPLF No.",
			"JV No.",
			"Date Released",
			"Status"
	};
	
	Class[] CLASS_TYPES = new Class[] {
			String.class, //Bill Type
			Timestamp.class, //Tran data
			Double.class, //Accomp %
			BigDecimal.class, //Gross Amt
			BigDecimal.class, //VAT
			BigDecimal.class, //WTAX Amt
			BigDecimal.class, //DP Recoupment
			BigDecimal.class, //Retention Amt
			BigDecimal.class, //BC Liquidation
			BigDecimal.class, //Other Deduction
			BigDecimal.class, //Net Amt
			String.class, //Bill No.
			String.class, //RPLF No.
			String.class, //JV No.
			Timestamp.class, //Date Released
			String.class //Status
	};
	
	private void initThis() {
		setColumnIdentifiers(COLUMNS);
		COLUMN_EDITABLE = new boolean[] {
				false, //Bill Type
				false, //Tran data
				false, //Accomp %
				false, //Gross Amt
				false, //VAT
				false, //WTAX Amt
				false, //DP Recoupment
				false, //Retention Amt
				false, //BC Liquidation
				false, //Other Deduction
				false, //Net Amt
				false, //Bill No.
				false, //RPLF No.
				false, //JV No.
				false, //Date Released
				false //Status
		};
	}
	
	public Class getColumnClass(int columnIndex) {
		return CLASS_TYPES[columnIndex];
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return COLUMN_EDITABLE[columnIndex];
	}
	
	public void clear() {
		FncTables.clearTable(this);
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
		if(editable){
			COLUMN_EDITABLE = new boolean[] {
					false, //Bill Type
					false, //Tran data
					false, //Accomp %
					false, //Gross Amt
					false, //VAT
					false, //WTAX Amt
					false, //DP Recoupment
					false, //Retention Amt
					false, //BC Liquidation
					false, //Other Deduction
					false, //Net Amt
					false, //Bill No.
					false, //RPLF No.
					false, //JV No.
					false, //Date Released
					false //Status
			};
		}else{
			COLUMN_EDITABLE = new boolean[] {
					false, //Bill Type
					false, //Tran data
					false, //Accomp %
					false, //Gross Amt
					false, //VAT
					false, //WTAX Amt
					false, //DP Recoupment
					false, //Retention Amt
					false, //BC Liquidation
					false, //Other Deduction
					false, //Net Amt
					false, //Bill No.
					false, //RPLF No.
					false, //JV No.
					false, //Date Released
					false //Status
			};
		}
	}

}
