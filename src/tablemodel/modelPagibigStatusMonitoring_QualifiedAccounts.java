/**
 * 
 */
package tablemodel;

import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

/**
 * @author John Lester Fatallo
 */
public class modelPagibigStatusMonitoring_QualifiedAccounts extends
		DefaultTableModel {

	private static final long serialVersionUID = -8014906334303263861L;
	private boolean editable = false;

	public modelPagibigStatusMonitoring_QualifiedAccounts() {
		initThis();
	}

	public modelPagibigStatusMonitoring_QualifiedAccounts(int rowCount,
			int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelPagibigStatusMonitoring_QualifiedAccounts(Vector columnNames,
			int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelPagibigStatusMonitoring_QualifiedAccounts(Object[] columnNames,
			int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelPagibigStatusMonitoring_QualifiedAccounts(Vector data,
			Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelPagibigStatusMonitoring_QualifiedAccounts(Object[][] data,
			Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}
	
	String [] COLUMNS= new String [] {
			
		"Select", //0
		"Proj. Name", //1
		"Unit Desc", //2
		"Entity ID", //3
		"Client Name", //4
		"Proj. ID", //5
		"PBL ID", //6
		"Seq", //7
		"OR Date", //8
		"House %", //9
		"SCD", //10
		"Business Class", //11
		"DP Term", //12
		"Pmt Stage", //13
		"DP %", //14
		"Pmt Status", //15
		"CEC", //16
		"CTE", //17
		"ITR", //20
		"Job Contract", //18 
		"AFS", //19
		"Payslip", //21
		"MSVS", //22
		"ESAV", //23
		"Pagibig OR", //24
		"BSL", //25
		"HDMF OR w/ P2-4", //26
		"CI Form",//27
		"Verified Docs"
		
	};
	
	Class [] CLASS_TYPES = new Class []{
		
		Boolean.class, //Select
		String.class, //Proj. Name
		String.class, //Unit Desc
		String.class, //Entity ID
		String.class, //Client Name
		String.class, //Proj. ID
		String.class, //PBL ID
		Integer.class, //Seq
		Timestamp.class, //OR Date
		String.class, //House Perc
		Timestamp.class, //SCD
		String.class, //Business Class
		Integer.class, //DP Term
		String.class, //Pmt Stage
		String.class, //DP %
		String.class, //Pmt Status
		Timestamp.class, //CEC
		Timestamp.class, //CTE 
		Timestamp.class, //ITR
		Timestamp.class, //Job Contract
		Timestamp.class, //AFS
		Timestamp.class, //Payslip
		Timestamp.class, //MSVS
		Timestamp.class, //ESAV
		Timestamp.class, //Pagibig OR
		Timestamp.class, //BSL
		Timestamp.class, //HDMF OR /w P2-4
		Timestamp.class, //CI Form
		Timestamp.class //Verified Docs
		
	};
	
	Boolean [] COLUMNS_EDITABLE = new Boolean []{
		
		true, //Select
		false, //Proj. Name
		false, //Unit Desc
		false, //Entity ID
		false, //Client Name
		false, //Proj. ID
		false, //PBL ID
		false, //Seq
		false, //OR Date
		false, //House Perc
		false, //SCD
		false, //Business Class
		false, //DP Term
		false, //Pmt Stage
		false, //
		false, //Pmt Status
		false, //CEC
		false, //CTE
		false, //ITR
		false, //Job Contract
		false, //AFS
		false, //Payslip
		false, //MSVS
		false, //ESAV
		false, //Pagibig OR
		false, //BSL
		false, //HDMF OR w/ P2-4
		false, //CI Form
		false //Verified Docs

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
