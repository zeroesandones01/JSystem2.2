/**
 * 
 */
package tablemodel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

/**
 * @author John Lester Fatallo
 */
public class modelQualifiedFITerminate extends DefaultTableModel {

	private static final long serialVersionUID = -8742716859483131361L;
	
	private boolean editable = false;

	public modelQualifiedFITerminate() {
		initThis();
	}

	public modelQualifiedFITerminate(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelQualifiedFITerminate(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelQualifiedFITerminate(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelQualifiedFITerminate(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelQualifiedFITerminate(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}
	
	String [] COLUMNS = new String []{
		
		"Select", //0
		"Rec ID", //1
		"Proj ID", //2
		"Unit ID", //3
		"PBL ID", //4
		"Seq", //5
		"Proj Alias", //6
		"Ph", //7
		"Blk", //8
		"Lt", //9
		"Entity ID", //10
		"Client Name", //11
		"Insurance Amt", //12
		"Insurance Term", //13
		"Effectivity Date", //14
		"Policy No", //15
		"Date From", //16
		"Date To", //17
		"Buyer Status", //18
			
	};
	
	Class [] CLASS_TYPES = new Class [] {
		
		Boolean.class, //Select
		Integer.class, //Rec ID
		String.class, //Proj ID
		String.class, //Unit ID
		String.class, //PBL ID
		Integer.class, //Seq
		String.class, //Proj Alias
		String.class, //Ph
		String.class, //Blk
		String.class, //Lt
		String.class, //Entity ID
		String.class, //Client Name
		BigDecimal.class, //Insurance Amt
		String.class, //Insurance Term
		Timestamp.class, //Effectivity Date
		String.class, //Policy No
		Timestamp.class, //Date From
		Timestamp.class, //Date TO
		String.class //Buyer Status
		
			
	};
	
	Boolean [] COLUMNS_EDITABLE = new Boolean []{
		
		true, //Select
		false, //Rec ID
		false, //Proj ID
		false, //Unit ID
		false, //PBL ID
		false, //Seq
		false, //Proj Alias
		false, //Ph 
		false, //Blk
		false, //Lt
		false, //Entity ID
		false, //Client Name
		false, //Insurance Amt
		false, //Insurance Term
		false, //Effectivity Date
		false, //Policy No
		false, //Date From
		false, //Date to
		false //Buyer Status
			
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
