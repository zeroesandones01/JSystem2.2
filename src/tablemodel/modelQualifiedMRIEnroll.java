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
 *
 */
public class modelQualifiedMRIEnroll extends DefaultTableModel {

	private static final long serialVersionUID = -2991619106744603773L;
	
	private boolean editable = false;

	public modelQualifiedMRIEnroll() {
		initThis();
	}

	public modelQualifiedMRIEnroll(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelQualifiedMRIEnroll(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelQualifiedMRIEnroll(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelQualifiedMRIEnroll(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelQualifiedMRIEnroll(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}
	
	String[] COLUMNS = new String[] {
			"Select", //0
			"Unit ID", //1
			"PBL", //2
			"Seq.", //3
			"Ph", //4
			"Blk", //5
			"Lt", //6
			"Entity ID", //7
			"Client Name", //8
			"NSP", //9
			"Stage", //10
			"MRI Form Submission", //11
			"Insurance Co ID", //12
			"Insurance Company", //13
			"Amt of Loan", //14
			"Amt Insured", //15
			"Term", //16
			"Premium", //17
			"Expected Date of Insurance", //18
			"Certificate No.", //19
			"Invoice No", //20
			"Business Class" //21
	};
	
	Class [] CLASS_TYPES = new Class[]{
			
			Boolean.class, //Select
			String.class,  //Unit ID
			String.class, //PBL
			Integer.class, //Seq No
			String.class, //Ph
			String.class, //Blk
			String.class, //Lt
			String.class, //Entity ID
			String.class, //Client Name
			BigDecimal.class, //NSP
			String.class, //Stage
			Timestamp.class, //Doc. Submitted Date
			String.class, //Insurance Co ID
			String.class, //Insurance Co.
			BigDecimal.class, //Amt of Loan
			BigDecimal.class, //Amt Insured
			String.class, //Term
			BigDecimal.class, //Premium
			Timestamp.class, //Expected Date of Insurance
			String.class, //Certificate No
			String.class, //Invoice No
			String.class //Business Class
			
	};
	
	Boolean [] COLUMNS_EDITABLE = new Boolean[]{
			
			true,  //Select
			false, //Unit ID
			false, //PBL ID
			false, //Seq No
			false, //Ph
			false, //Blk
			false, //Lt
			false, //Entity ID
			false, //Client Name
			false, //NSP
			false, //Stage
			false, //MRI Form Submitted Date
			false, //Insurance Co ID
			false, //Insurance Co.
			false, 
			false, 
			false,
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
		
		/*if(editable){
			COLUMNS_EDITABLE = new Boolean []{
					true, //Select
					false, //Unit ID
					false, //PBL ID
					false, //Seq No
					false, //Unit Desc
					false, //Entity ID
					false, //Client Name
					true, //NSP
					false, //Stage
					false, //Doc. Submitted Date
					false //Insurance Co.
			};
		}else{
			COLUMNS_EDITABLE = new Boolean []{
					true, //Select
					false, //Unit ID
					false, //PBL ID
					false, //Seq No
					false, //Unit Desc
					false, //Entity ID
					false, //Client Name
					true, //NSP
					false, //Stage
					false, //Doc. Submitted Date
					false //Insurance Co.
			};
		}*/
	}
}
