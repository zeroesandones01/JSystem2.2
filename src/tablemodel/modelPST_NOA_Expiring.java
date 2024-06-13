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

public class modelPST_NOA_Expiring extends
		DefaultTableModel {

	private static final long serialVersionUID = 3664530836963114887L;

	private boolean editable = false;

	public modelPST_NOA_Expiring() {
		initThis();
	}

	public modelPST_NOA_Expiring(int rowCount,
			int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelPST_NOA_Expiring(Vector columnNames,
			int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelPST_NOA_Expiring(Object[] columnNames,
			int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelPST_NOA_Expiring(Vector data,
			Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelPST_NOA_Expiring(Object[][] data,
			Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}
	
	String [] COLUMNS = new String []{
		
		"No.", //0
		"Proj. Alias", //1
		"Proj. Name", //2
		"Unit", //3
		"Entity ID", //4
		"Name", //5
		"Proj. ID",//6 
		"PBL ID", //7
		"Seq", //8
		"Loanable Amt", //9
		"Birthyear", //10
		"Age", //11
		"NOA Released Date" //12

	};
	
	Class [] CLASS_TYPES = new Class []{
		
		Integer.class, //No.
		String.class, //Proj. Alias
		String.class, //Proj. Name
		String.class, //Unit 
		String.class, //Entity ID
		String.class, //Name
		String.class, //Proj. ID
		String.class, //PBL ID
		Integer.class, //Seq
		BigDecimal.class, //Loanable Amt
		String.class, //BirthYear
		Integer.class, //Age
		Timestamp.class //NOA Released Date
		
	};
	
	Boolean [] COLUMNS_EDITABLE = new Boolean []{
			
			false, //No.
			false, //Proj. Alias
			false, //Proj. Name
			false, //Unit 
			false, //Entity ID
			false, //Name
			false, //Proj. ID
			false, //PBL ID
			false, //Seq
			false, //Loanable Amt
			false, //BirthYear
			false, //Age
			false //NOA Released Date
		
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
