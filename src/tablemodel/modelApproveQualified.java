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
public class modelApproveQualified extends DefaultTableModel {

	
	private static final long serialVersionUID = 6900675162175413038L;
	
	private boolean editable = false; 

	public modelApproveQualified() {
		initThis();
	}

	public modelApproveQualified(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelApproveQualified(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelApproveQualified(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelApproveQualified(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelApproveQualified(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}
	
	String [] COLUMNS = new String[]{
		
		"Select", //0
		"Rec ID", //1
		"Unit ID", //2
		"PBL ID", //3
		"Seq.", //4
		"Unit Desc", //5
		"Entity ID", //6
		"Client Name", //7
		"House Model", //8
		"Amount of Insurance" //9 
		//"Premium", //10
		//"Term" //11
		
	};
	
	Class [] CLASS_TYPES = new Class []{
		
		Boolean.class, //Select
		Integer.class, //Rec ID
		String.class, //Unit ID
		String.class, //PBL ID
		Integer.class, //Seq. No
		String.class, //Unit Desc
		String.class, //Entity ID
		String.class, //Client Name
		String.class, //House Model
		BigDecimal.class //Insurance Amount
		//BigDecimal.class, //Premium
		//Integer.class //Term
		
	};
	
	Boolean [] COLUMNS_EDITABLE = new Boolean [] {
		
		true, //Select
		false, //Rec ID
		false, //Unit ID
		false, //PBL ID
		false, //Seq. No
		false, //Unit Desc
		false, //Entity ID
		false, //Client Name
		false,// House Model
		false, //Insurance Amount
		//false, //Premium
		//false //Term
		
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
