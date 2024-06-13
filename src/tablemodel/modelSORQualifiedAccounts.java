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
public class modelSORQualifiedAccounts extends DefaultTableModel {

	private static final long serialVersionUID = 898430820687984150L;
	private boolean editable = false;

	public modelSORQualifiedAccounts() {
		initThis();
	}

	public modelSORQualifiedAccounts(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelSORQualifiedAccounts(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelSORQualifiedAccounts(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelSORQualifiedAccounts(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelSORQualifiedAccounts(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}
	
	String [] COLUMNS = new String []{
		"Select", //0
		"Proj. ID", //1
		"Unit ID", //11
		"Seq.", //13
		"Ph", //1 
		"Blk.", //2
		"Lot", //3
		"Entity ID", //4, 
		"Client Name", //5
		"NSP", //6
		"Availed Amt", //7
		"OB", //8
		"Terms", //9
		"Int Rate" //10

	};
	
	Class [] CLASS_TYPES = new Class []{
			
		Boolean.class, //Select
		String.class, //Proj. ID
		String.class, //Unit ID
		Integer.class, //Seq
		String.class, //Ph
		String.class, //Blk
		String.class, //Lot
		String.class, //Entity ID
		String.class, //Client Name
		BigDecimal.class, //NSP
		BigDecimal.class, //Availed Amt
		BigDecimal.class, //OB
		Integer.class, //Terms
		BigDecimal.class, //Int. Rate
		
	};
	
	Boolean [] COLUMNS_EDITABLE = new Boolean []{
			
		true, //Select
		false, //Ph
		false, //Blk
		false, //Lot
		false, //Entity ID
		false, //CLient Name
		false, //NSP
		false, //Availed AMt
		false, //OB
		false, //Terms
		false, //Int. Rate
		false, //Unit ID
		false, //Proj. ID
		false //Seq
		
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
