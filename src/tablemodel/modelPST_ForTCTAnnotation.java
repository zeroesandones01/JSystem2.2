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
public class modelPST_ForTCTAnnotation extends
		DefaultTableModel {

	private static final long serialVersionUID = -8014906334303263861L;
	private boolean editable = false;

	public modelPST_ForTCTAnnotation() {
		initThis();
	}

	public modelPST_ForTCTAnnotation(int rowCount,
			int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelPST_ForTCTAnnotation(Vector columnNames,
			int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelPST_ForTCTAnnotation(Object[] columnNames,
			int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelPST_ForTCTAnnotation(Vector data,
			Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelPST_ForTCTAnnotation(Object[][] data,
			Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}
	
	String [] COLUMNS= new String [] {
			
		"Select", //0
		"Proj. Name", //1
		"Unit", //2
		"Entity ID", //3
		"Client", //4
		"Proj. ID", //5
		"PBL ID", //6
		"Seq", //7
		"NSP", //8
		"Loan Amt", //9
		"Aff. Ratio", //10
		"House Model", //11
		"% Constructed", //12
		"DP Term", //13
		"DP Paid", //14
		" DP Remaining ", //15
		" Acct Status " //16
		
		
	};
	
	Class [] CLASS_TYPES = new Class []{
		
		Boolean.class, //Select
		String.class, //Proj. Name
		String.class, //Unit
		String.class, //Entity ID
		String.class, //Client
		String.class, //Proj. ID
		String.class, //PBL ID
		Integer.class, //Seq
		BigDecimal.class, //NSP
		BigDecimal.class, //Loan Amt
		BigDecimal.class, //Aff. Ratio
		String.class, //House Model
		String.class, //% Constructed
		Integer.class, //DP Term
		Integer.class, //DP paid
		String.class, //Acct term
		String.class //Acct Status
		
	};
	
	Boolean [] COLUMNS_EDITABLE = new Boolean []{
		
		true, //Select
		false, //Proj. Name
		false, //Unit
		false, //Entity ID
		false, //Client
		false, //Proj. ID
		false, //PBL ID
		false, //Seq
		false, //NSP
		false, //Loan Amt
		false, //Aff. Ratio
		false, //House Model
		false, //% Constructed
		false, //DP Term
		false, //DP Paid
		false, //DP Remaining
		false //Acct Status

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
