package tablemodel;

import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelFixedHousingAwardingCost extends DefaultTableModel {

	private static final long serialVersionUID = 1L;
	
	private boolean editable = false;
	//boolean[] COLUMN_EDITABLE;

	public modelFixedHousingAwardingCost() {
		initThis();
	}

	public modelFixedHousingAwardingCost(boolean editable) {
		initThis();
	}

	public modelFixedHousingAwardingCost(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelFixedHousingAwardingCost(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelFixedHousingAwardingCost(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelFixedHousingAwardingCost(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelFixedHousingAwardingCost(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}
	
	String[] COLUMNS = new String[] {
			"Cost", 
			"Model Description",
			"Updated By",
			"Date Created",
			"Time Created"
	};
	
	Class[] CLASS_TYPES = new Class[] {
			BigDecimal.class, //Cost
			String.class, //Model Description
			String.class, //Updated By
			Timestamp.class, //Date Created
			Time.class //Date Created
	};
	
	private void initThis() {
		setColumnIdentifiers(COLUMNS);
		/*COLUMN_EDITABLE = new boolean[] {
				false, //Cost
				false, //Updated By
				false //Date Updated
		};*/
	}
	
	public Class getColumnClass(int columnIndex) {
		return CLASS_TYPES[columnIndex];
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}
	
	public void clear() {
		FncTables.clearTable(this);
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
		/*if(editable){
			COLUMN_EDITABLE = new boolean[] {
					false, //Cost
					false, //Updated By
					false //Date Updated
			};
		}else{
			COLUMN_EDITABLE = new boolean[] {
					false, //Cost
					false, //Updated By
					false //Date Updated
			};
		}*/
	}

}
