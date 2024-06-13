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
 * @author JLF
 */
public class modelQualifiedMRIRenew extends DefaultTableModel {
	
	private static final long serialVersionUID = 5616624423892285390L;
	
	private boolean editable = false;

	public modelQualifiedMRIRenew() {
		initThis();
	}

	public modelQualifiedMRIRenew(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelQualifiedMRIRenew(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelQualifiedMRIRenew(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelQualifiedMRIRenew(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelQualifiedMRIRenew(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}
	
	String [] COLUMNS = new String[]{
			
			"Select", //0
			"Applicant No.", //1
			"Unit ID", //2
			"PBL ID", //3
			"Seq.", //4
			"Ph", //5
			"Blk.", //6
			"Lot", //7
			"Entity ID", //8
			"Client Name", //9
			"Balance", //10
			"*Amount Insured", //EDITABLE //11
			"*Premium", //EDITABLE //12
			"*Term", //EDITABLE //13
			"Client Age", //14
			"Date of Birth", //15
			"Stage", //16
			"Insurance Line", //17
			"Insurance Broker", //18
			"Last Date Covered", //19
			"Buyer Status ID", //20
			"Buyer Status", //21
			"Amt of Loan", //22
			"Expected Date of Insurance", //23
			"Certificate No", //24
			"Invoice No", //25
			"Business Class" //26
			
			
	};
	
	Class[] CLASS_TYPES = new Class[] {
			
			Boolean.class, //Select
			String.class, //Applicant No.
			String.class, //Unit ID
			String.class, //PBL ID
			Integer.class, //Seq. No
			String.class, //Ph
			String.class, //Blk.
			String.class, //Lot
			String.class, //Entity ID
			String.class, //Entity Name
			BigDecimal.class, //Balance
			BigDecimal.class, //Amount Insured
			BigDecimal.class, //Premium
			Integer.class, //Term
			Integer.class, //Age
			Timestamp.class, //Date of Birth
			String.class, //Stage
			String.class, //Insurance LIne
			String.class, //Insurance Broker
			Timestamp.class,  //Last Date Covered
			String.class, //Buyer Status ID
			String.class, //Buyer Status
			BigDecimal.class, //Loan Amt
			Timestamp.class, //Expected Date of Insurance
			String.class, //Certificate No
			String.class, //Invoice No
			String.class //Business Class
			
	};
	
	Boolean [] COLUMNS_EDITABLE = new Boolean[]{
			
			true, //Select
			false, //APplicant no
			false, //Unit ID
			false, //PBL ID
			false, //Seq No.
			false, //Ph
			false, //Blk.
			false, //Lot
			false, //Entity ID
			false, //Entity Name
			false, //Balance
			true, //Amount Insured
			true, //Premium
			true, //term
			false, //Age
			false, //Date of Birth
			false, //Stage
			false, //Insurance Line
			false, //Insurance Broker
			false, //last Date COverd
			false, //Buyer Status ID
			false,  // Buyer Status
			false, 
			false, 
			false, 
			false, 
			false
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
