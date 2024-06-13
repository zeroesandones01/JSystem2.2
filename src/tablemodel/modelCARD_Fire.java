package tablemodel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelCARD_Fire extends DefaultTableModel {

	private static final long serialVersionUID = -748279826690178592L;

	public modelCARD_Fire() {
		initThis();
	}

	public modelCARD_Fire(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelCARD_Fire(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelCARD_Fire(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelCARD_Fire(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelCARD_Fire(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
		
		"Rec. ID", //0
		"Policy Holder", //1
		"Policy No", //2
		"Amt Insured", //3
		"From", //4
		"To", //5
		"F & L", //6
		"FE", //7 
		"TYP", //8
		"FLD", //9
		"EC", //10
		"RSMD", //11
		"Sub-Total", //12
		"Doc Stamps", //13
		"EVAT", //14
		"FST", //15
		"LGT", //16
		"Premium", //17
		"Batch No", //18
		"Term", //19
		"Date Enrolled", //20
		"Date Approved", //21
		"Invoice No", //22
		"Insurance Co ID", //23
		"Insurance Co", //24
		"Insurance Line ID", //25
		"Insurance Line", //26
		"Date Terminated", //27
		"Termination Batch", //28
		"Remarks", //29
		"Status ID", //30
		"Renewal Batch", //31
		"Refund Amt" //32
			
	};
	
	public Class[] CLASS_TYPES = new Class[] {
			
		Integer.class, //Rec ID
		String.class, //Policy Holder
		String.class, //Policy No
		BigDecimal.class, //Amt Insured
		Timestamp.class, //Date From
		Timestamp.class, //Date To
		BigDecimal.class, //FL
		BigDecimal.class, //FE
		BigDecimal.class, //Typhoon
		BigDecimal.class, //Flood
		BigDecimal.class, //Ext Cover
		BigDecimal.class, //RSMD
		BigDecimal.class, //SUbtotal
		BigDecimal.class, //Doc Stamps
		BigDecimal.class, //EVAT
		BigDecimal.class, //FST
		BigDecimal.class, //LGT
		BigDecimal.class, //Premium
		String.class, //Batch No
		Integer.class, //Term
		Timestamp.class, //Date Enrolled
		Timestamp.class, //Date Approved
		String.class, //Invoice No
		String.class, //Insurance Co ID
		String.class, //Insurance Co
		String.class, //Insurance Line ID
		String.class, //Insurance Line
		Timestamp.class, //Date Terminated
		String.class, //Termination Batch
		String.class, //Remarks
		String.class, //Status ID
		String.class, //Renewal Batch
		BigDecimal.class //Refund Amt
		
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
	}

	public Class getColumnClass(int columnIndex) {
		return CLASS_TYPES[columnIndex];
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}
	public void clear() {
		FncTables.clearTable(this);
	}

}

