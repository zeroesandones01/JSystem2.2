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
public class modelQualifiedFIRenew extends DefaultTableModel {

	private static final long serialVersionUID = -8402145752930752463L;
	
	private boolean editable = false;

	public modelQualifiedFIRenew() {
		initThis();
	}

	public modelQualifiedFIRenew(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelQualifiedFIRenew(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelQualifiedFIRenew(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelQualifiedFIRenew(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelQualifiedFIRenew(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}
	
	String [] COLUMNS = new String []{
			
		"Select", //0
		"Rec. ID", //1
		"Proj.", //2
		"Unit ID", //2
		"Ph", //
		"Blk", //
		"Lt", //
		"PBL ID", //3
		"Seq. No", //4
		//"Unit Desc", //5
		"Entity ID", //6
		"Client Name", //7
		"House Model", //8
		"Model Cost", //9
		"% Constructed", //
		"Amount Insured", //9
		"Premium", //10
		"Insurance Line", //11 
		"Insurance Broker", //12
		"Last Date Covered", //13
		"Term", //14
		"Policy No", //15
		"Date From", //16
		"Date To", //17
		
	};
	
	Class [] CLASS_TYPES = new Class [] {
		
		Boolean.class, //Select
		Integer.class, //Rec. ID
		String.class, //Proj. Alias
		String.class, //Unit ID
		String.class, //Ph
		String.class, //Blk
		String.class, //Lt
		String.class, //PBL ID
		Integer.class, //Seq. No
		//String.class, //Unit Desc
		String.class, //Entity ID
		String.class, //Client Name
		String.class, //House Model
		BigDecimal.class, //Model Cost
		String.class, //
		BigDecimal.class, //Amount Insured
		BigDecimal.class, //Premium
		String.class, //Insurance LIne
		String.class, //Insurance Broker
		Timestamp.class, //Last Date Covered
		Integer.class, //Term
		String.class, //Policy No
		Timestamp.class, //Date From
		Timestamp.class //Date To
			
	};
	
	Boolean [] COLUMNS_EDITABLE = new Boolean []{
		
		true, //Select
		false, //Rec. ID
		false, //Proj. Alias
		false, //Unit ID
		false, //Ph
		false, //Blk
		false, //Lt
		false, //PBL ID
		false, //Seq. No
		//false, //Unit Desc
		false, //Entity ID
		false, //CLient Name
		false, //House Model
		false, //Model Cost
		false, //
		false, //Amount Insured
		false, //Premium
		false, //Insurance Line
		false, //Insurance Broker
		false, //Last Date Covered
		false, //Term
		false, //Policy No	 
		false, //Date From
		false //Date To
		
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
