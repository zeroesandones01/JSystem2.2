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
public class modelSORApprovedAccounts extends DefaultTableModel {

	private static final long serialVersionUID = -2599658537915105698L;
	private boolean editable = false;

	public modelSORApprovedAccounts() {
		initThis();
	}

	public modelSORApprovedAccounts(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelSORApprovedAccounts(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelSORApprovedAccounts(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelSORApprovedAccounts(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelSORApprovedAccounts(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}
	
	String [] COLUMNS = new String [] {
		
		"Select", //0 
		"Rec. ID", //1
		"Proj. ID", //2
		"Unit ID", //3
		"Seq", //4
		"Ph", //5
		"Blk.", //6
		"Lot", //7
		"Entity ID", //8
		"Client Name", //9
		"NSP", //10
		"Sold Amount", //11
		"Terms", //12
		"Premium", //13
		"Batch No.", //14
		"Int. Rate", //15
		"MA Date" //16
		
	};
	
	Class [] CLASS_TYPES = new Class []{
		
		Boolean.class, //Select
		Integer.class, //Rec ID
		String.class, //Proj. ID
		String.class, //Unit ID
		Integer.class, //Seq No
		String.class, //Ph
		String.class, //Blk.
		String.class, //Lot
		String.class, //Entity ID
		String.class, //Cliet Name
		BigDecimal.class, //NSP
		BigDecimal.class, //Sold Amount
		Integer.class, //Terms
		BigDecimal.class, //Premium
		String.class, //Batch No
		BigDecimal.class, //Int Rate
		Timestamp.class //MA Date
		
	};
	
	Boolean [] COLUMNS_EDITABLE = new Boolean []{
		true, //Select
		false, //Rec ID
		false, //Proj ID
		false, //Unit ID
		false, //Seq
		false, //PH
		false, //Blk
		false, //Lot
		false, //Entity ID
		false, //Client Name
		false, //NSP
		true, //Sold Amount
		true, //Terms
		true, //Premium
		false, //Batch No
		false, //Int Rate
		false //MA Date
		
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
