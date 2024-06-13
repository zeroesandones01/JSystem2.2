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
public class modelApproveQualifiedMRI extends DefaultTableModel {

	private static final long serialVersionUID = 7520598330114716460L;

	private boolean editable = false;
	
	public modelApproveQualifiedMRI() {
		initThis();
	}

	public modelApproveQualifiedMRI(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelApproveQualifiedMRI(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelApproveQualifiedMRI(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelApproveQualifiedMRI(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelApproveQualifiedMRI(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}
	
	
	String [] COLUMNS = new String []{
			
		"Select", //0
		"Applicant No.",//1
		"Proj. ID",//2
		"Proj. Alias", //3
		"Unit ID",//4
		"PBL ID", //5
		"Seq.",//6
		"Unit Desc", //7
		"Entity ID",//8
		"Client Name", //9
		"Insurance Co ID", //10
		"Insurance Company", //11
		"NSP", //12
		"*Amt Enrolled", //13
		"*Premium", //14
		"*Term", //15
		"Insurance Amt", //16
		"Stage", //17
		"Date Enrolled", //18
		"Reference No", //19
		"Invoice No", //20
		"Policy No", //21
		"Date Approved" //22
		
	};
	
	Class [] CLASS_TYPES = new Class []{
			
		Boolean.class, //Select
		String.class, //Applicant No
		String.class, //Proj. ID
		String.class, //Proj. Alias
		String.class,  //Unit ID
		String.class, //PBL ID
		Integer.class, //Seq. No
		String.class, //Unit Desc
		String.class, //Entity ID
		String.class, // Client Name
		String.class, //Insurance Co ID
		String.class, //Insurance COmpany
		BigDecimal.class, //NSP
		BigDecimal.class, // Insurance
		BigDecimal.class, //Premium
		Integer.class, //Term
		BigDecimal.class, //Insurance Amt
		String.class, //Stage
		Timestamp.class, //Date Enrolled
		String.class, //Reference No
		String.class, //Invoice No 
		String.class, //Policy No
		Timestamp.class //Date Approved
		
	};
	
	Boolean [] COLUMNS_EDITABLE = new Boolean []{
		
			true, //Select
			false, //Applicant No.
			false, //Proj. ID
			false, //Proj. Alias
			false, //Unit ID
			false, //PBL ID
			false, //Seq No
			false, //Unit Desc
			false, //Entity ID
			false, //Client Name
			false, //Insurance Co ID 
			false, //Insurance Company
			false, //NSP
			true, //Insurance
			true, //Premium
			true, //Term
			false, //Insurance Amt
			false, //Stage
			false, //Date Enrolled
			false, //Reference No
			true, //Invoice No 
			false, //Policy No
			false //Date Approved
			
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
