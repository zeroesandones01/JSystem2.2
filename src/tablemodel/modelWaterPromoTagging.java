package tablemodel;

import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelWaterPromoTagging extends DefaultTableModel {

	
	private boolean editable = false;

	public modelWaterPromoTagging() {
		initThis();
	}

	public modelWaterPromoTagging(int rowCount,
			int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelWaterPromoTagging(Vector columnNames,
			int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelWaterPromoTagging(Object[] columnNames,
			int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelWaterPromoTagging(Vector data,
			Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelWaterPromoTagging(Object[][] data,
			Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}
	
	String [] COLUMNS = new String []{
		
		"Client", //0
		"Proj.", //1
		"Unit", //2
		"Date Availed", //3
		"Pmt Type",
		"Entity ID", 
		"Proj. ID",
		"PBL ID", 
		"Seq. No"
		
	};
	
	Class [] CLASS_TYPES = new Class []{
		
		String.class, //Client
		String.class, //Proj.
		String.class, //Unit
		Timestamp.class, //Date Availed
		String.class, //Pmt Type
		String.class, //Entity ID
		String.class, //Proj. ID
		String.class, //PBL ID
		Integer.class //Seq. No
	};
	
	Boolean [] COLUMNS_EDITABLE = new Boolean []{
			
		false, //Buyer Status ID
		false, //Status Desc
		false, //Condition
		false, //Date
		false, //Buyer Status ID
		false, //Status Desc
		false, //Condition
		false, //Date
		false
		
	};
	
	private void initThis(){
		setColumnIdentifiers(COLUMNS);
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

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}
}
