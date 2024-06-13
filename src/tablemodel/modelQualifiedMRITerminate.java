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
public class modelQualifiedMRITerminate extends DefaultTableModel {
	
	private static final long serialVersionUID = -3196224770903286849L;
	
	private boolean editable = false;

	public modelQualifiedMRITerminate() {
		initThis();
	}

	public modelQualifiedMRITerminate(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelQualifiedMRITerminate(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelQualifiedMRITerminate(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelQualifiedMRITerminate(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelQualifiedMRITerminate(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}
	
	String [] COLUMNS = new String[]{
			
		 "Select", //0
		 "Rec. ID", //1
		 "Applicant No", //2
		 "Unit ID", //3
		 "PBL ID", //4
		 "Seq. No", //5
		 "Ph",  //6
		 "Blk.", //7
		 "Lot", //8
		 "Entity ID", //9
		 "Client Name", //10
		 "Balance", //11
		 "Amount Insured", //12
		 "Premium", //13
		 "Term", //14
		 "Insurance Line", //15
		 "Insurance Broker", //16
		 "Buyer Status ID", //17
		 "Buyer Status", //18
		 "Remarks \nReason", //19 EDITABLE
		 "Refund", //20 EDITABLE
		 "Effectivity Date", //21
		 "Invoice No", //22
		 "Cert. No", //23
		 "Date From", //24
		 "Date To" //25
		 
	};

	Class [] CLASS_TYPES = new Class[]{
		 
		 Boolean.class, //Select
		 Integer.class, //Rec ID
		 String.class, //Applicant No.
		 String.class, //Unit ID
		 Integer.class, //PBL ID
		 Integer.class, //Seq No
		 String.class, //Ph
		 String.class, //Blk.
		 String.class, //Lot
		 String.class, //Entity ID
		 String.class, //CLient Name
		 BigDecimal.class, //Balance
		 BigDecimal.class, //Amount Insured
		 BigDecimal.class, //Premium
		 Integer.class, //Term
		 String.class, //Insurance Line
		 String.class, //Insurance Broker
		 String.class, //Buyer Status ID
		 String.class, //Buyer Status
		 String.class, //Remarks Reason
		 BigDecimal.class, //Refund
		 Timestamp.class, //Effectivity Date
		 String.class, //Invoice NO
		 String.class, //Cert. No
		 Timestamp.class, //Date From
		 Timestamp.class, //Date To
		 
	};
	
	Boolean [] COLUMNS_EDITABLE = new Boolean []{
		
		true, //Select
		false, //Rec ID
		false, //Applicant No.
		false, //Unit ID
		false, // PBL ID
		false, //Seq. No
		false, //Ph
		false, //Blk.
		false, //Lt
		false, //Entity ID
		false, //Client NAme
		false, //Balance
		false, //Amount Insured
		false, //Premium
		false, //Term
		false, //Insurance Line
		false, //Insurance Broker
		false, //Buyer Status ID
		false, //Buyer Status
		false, //Remarks Reason
		false,  //Refund
		false, //Effectivity Date
		false, //Invoice No
		false, //Cert. No
		false, //Date From
		false //Date To
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
