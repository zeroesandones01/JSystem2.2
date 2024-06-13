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
public class modelPagibigStatusMonitoring_DeficientAccounts extends DefaultTableModel {

	private static final long serialVersionUID = 7380498438514021764L;

	private boolean editable = false;
	
	public modelPagibigStatusMonitoring_DeficientAccounts() {
		initThis();
	}

	public modelPagibigStatusMonitoring_DeficientAccounts(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelPagibigStatusMonitoring_DeficientAccounts(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelPagibigStatusMonitoring_DeficientAccounts(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelPagibigStatusMonitoring_DeficientAccounts(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelPagibigStatusMonitoring_DeficientAccounts(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}
	
	String [] COLUMNS = new String []{
			
		"Sales Div.", //0
		"Sales Dept.", //1
		"Proj. Name", //2
		"Entity ID", //3
		"Client Name", //4
		"Proj. ID", //5
		"PBL ID", //6
		"Seq No", //7
		"Ph-Blk-Lt", //8
		"OR Date", //9
		"Contact No", //10
		"Business Class", //11
		"ESAV", //12
		"BSL", //13
		"MSVS", //14
		"SCD In", //15
		"SCD Out", //16
		"Payslip", //17
		"CEC", //18
		"Job Contract", //19
		"ITR" //20
		//"Findings" //22
		
	};
	
	Class [] CLASS_TYPES = new Class []{
		
		String.class, //Sales Div
		String.class, //Sales Dept
		String.class, //Proj. Name
		String.class, //Entity ID
		String.class, //Client Name
		String.class, //Proj ID
		String.class, //PBL ID
		Integer.class, //Seq No
		String.class, //Ph-Blk-Lt   	
		Timestamp.class, //OR Date
		String.class, //Contact No
		String.class, //Business Class
		String.class, //ESAV
		String.class, //BSL
		String.class, //MSVS
		Timestamp.class, //SCD In
		Timestamp.class, //SCD Out
		String.class, //Payslip
		String.class, //CEC
		String.class, //Job Contract
		String.class //ITR
		//String.class //Findings
		
	};
	
	Boolean [] COLUMNS_EDITABLE = new Boolean [] {
			
		false, //Sales Div
		false, //Sales Dept
		false, //Proj. Name
		false, //Entity ID
		false, //Client Name
		false, //Proj ID
		false, //PBL ID
		false, //Seq No
		false, //Ph-Blk-Lt
		false, //OR Date
		false, //Contact No
		false, //Business Class
		false, //ESAV
		false, //BSL
		false, //MSVS
		false, //SCD In
		false, //SCD Out
		false, //Payslip
		false, //CEC
		false, //Job Contract
		false //ITR
		//false //Findings
		
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
