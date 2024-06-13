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
public class modelApprovedMRIRenewal extends DefaultTableModel {
	
	private boolean editable = false;

	private static final long serialVersionUID = 5880897629294568224L;

	public modelApprovedMRIRenewal() {
		initThis();
	}

	public modelApprovedMRIRenewal(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelApprovedMRIRenewal(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelApprovedMRIRenewal(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelApprovedMRIRenewal(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelApprovedMRIRenewal(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}
	
	String [] COLUMNS = new String []{
			
		"Select", //0
		"Applicant No.", //1
		"Proj. ID", //2
		"Unit ID", //3
		"PBL ID", //4
		"Seq. No", //5
		"Unit Desc", //6
		"Entity ID", //7
		"Client Name", //8
		"Balance", //9
		"*Amount Insured", //10 
		"*Premium", //11
		"*Term", //12
		"Stage", //13
		"Insurance Co. ID", //14 
		"Insurance Line", //15
		"Insurance Company", //16
		"Insurance Broker", //17
		"Last Date Covered" //18
			
	};
	
	Class [] CLASS_TYPES = new Class []{
		
		Boolean.class, //Select
		String.class, //Applicant No.
		String.class, //Proj. ID
		String.class, //Unit ID
		Integer.class, //PBL ID
		Integer.class, //Seq. No
		String.class, //Unit Desc
		String.class, //Enttiy ID
		String.class, //Client Name
		BigDecimal.class, //Balance
		BigDecimal.class, //Amount Insured
		BigDecimal.class, //Premium
		Integer.class, //Term
		String.class, //Stage
		String.class, //Insurance Co. ID
		String.class, //Insurance Line
		String.class, //Insurance Company
		String.class, //Insurance Broker
		Timestamp.class //Last Date Covered
			
	};
	
	Boolean [] COLUMNS_EDITABLE = new Boolean []{
		
		true, //Select
		false, //Applicant No.
		false, //Proj. ID
		false, //Unit ID
		false, //Unit Desc
		false, //PBL ID
		false, //Seq. No
		false, //Entity ID
		false, //Client Name
		false, //Balance
		true, //Amount Insured
		true, //Premium
		true, //Term
		false, //Stage
		false, //Insurance Co. ID
		false, //Insurance Line
		false, //Insurance Company
		false, //Insurance Broker
		false //Last Date Covered
			
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
