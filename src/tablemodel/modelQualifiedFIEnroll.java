/**
 * 
 */
package tablemodel;

import java.math.BigDecimal;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

/**
 * @author John Lester Fatallo
 */
public class modelQualifiedFIEnroll extends DefaultTableModel {

	private static final long serialVersionUID = 4389435605121767934L;

	private boolean editable = false;
	
	public modelQualifiedFIEnroll() {
		initThis();
	}

	public modelQualifiedFIEnroll(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelQualifiedFIEnroll(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelQualifiedFIEnroll(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelQualifiedFIEnroll(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelQualifiedFIEnroll(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}
	
	String [] COLUMNS = new String []{
			
		"Select", //0
		"Proj. Alias", //1
		"Unit ID", //2
		"PBL ID", //3
		"Seq. No", //4
		"Ph", //5
		"Blk", //6
		"Lot", //7
		"Entity ID", //9
		"Client Name", //10
		"Model ID", //11
		"Model Name", //12
		"House Cost", //13
		"% Constructed", //14
		"Term" //15
		
	};
	
	Class [] CLASS_TYPES = new Class []{
		
		Boolean.class, //Select
		String.class, //Proj. Alias
		String.class, //Unit ID
		String.class, //PBL
		Integer.class, //Seq
		String.class, //Ph
		String.class, //Blk
		String.class, //Lot
		String.class, //Entity ID
		String.class, //Entity Name
		String.class, //Model ID
		String.class, //Model Name
		BigDecimal.class, //House Cost
		String.class, //% Constructed
		String.class //Term
			
	};
	
	Boolean [] COLUMNS_EDITABLE = new Boolean []{
			
		true, //Select
		false, //Proj Alias
		false, //Unit ID
		false, //PBL
		false, //Seq
		false, //Ph
		false, //Blk
		false, //Lot
		false, //Entity ID
		false, //Entity Name
		false, //Model ID
		false, //Model Name
		false, //House Cost
		false, //% Constructed
		false //Term
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
