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
public class modelInsuranceInfo extends DefaultTableModel {

	private static final long serialVersionUID = 5579078062960156695L;
	private boolean editable = false;

	public modelInsuranceInfo() {
		initThis();
	}

	public modelInsuranceInfo(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelInsuranceInfo(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelInsuranceInfo(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelInsuranceInfo(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelInsuranceInfo(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}
	
	String [] COLUMNS = new String[]{
		
		"Reference No.", //0
		"Amount Insured", //1
		"Term", //2
		"Premium", //3
		"Policy No", //4
		"Invoice No", //5
		"Date Approved", //6
		"Date From", //7
		"Date To", //8
		"Insurance Co ID", //9 
		"Insurance Company", //10
		"Insurance Broker ID", //11
		"Insurance Line", //12
		"Rec. ID", //13
		"Date Terminated", //14
		"Premium Refund", //15
		"Applicant No", //16
		"Part ID" //17
			
	};
	
	Class [] CLASS_TYPES = new Class[]{
			
		String.class, //Reference No
		BigDecimal.class, //Amount Insured
		Integer.class, //Term
		BigDecimal.class, //Premium
		String.class, //Policy No
		String.class, //Invoice No
		Timestamp.class, //Date Approved
		Timestamp.class, //Date From
		Timestamp.class, //Date To
		String.class, //Insurance Co ID
		String.class, //Insurance Company
		String.class, //Insurance Broker ID
		String.class, //Insurance Line
		Integer.class, //Rec ID
		Timestamp.class, //Date Terminated
		BigDecimal.class, //Premium Refund
		String.class, //Applicant No
		String.class //Part ID
			
	};
	
	Boolean [] COLUMNS_EDITABLE = new Boolean[]{
		
		false, //Reference No
		false, //Amount Insured
		false, //Term
		false, //Premium
		false, //Policy No
		false, //Invoice No
		false, //Date Approved 
		false, //Date From
		false, //Date To
		false, //Insurance Co ID
		false, //Insurance Company
		false, //Insurance Broker ID
		false, //Insurance Line
		false, //Rec ID
		false, //Date Terminated
		false, //Premium Refund
		false, //Applicant No
		false //Part ID
			
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
