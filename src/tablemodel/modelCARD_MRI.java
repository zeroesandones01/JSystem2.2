package tablemodel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelCARD_MRI extends DefaultTableModel {

	private static final long serialVersionUID = -5136901159844682734L;

	public modelCARD_MRI() {
		initThis();
	}

	public modelCARD_MRI(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelCARD_MRI(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelCARD_MRI(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelCARD_MRI(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelCARD_MRI(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
		
		"Reference No", //0
		"Amt Insured", //1
		"Term", //2
		"Premium", //3
		"Policy No", //4
		"Date Approved", //5
		"Date From", //6
		"Date To", //7
		"Applicant No", //8
		"RPLF NO" //9
		
	};
	
	public Class[] CLASS_TYPES = new Class[] {
			
		String.class, //Reference No
		BigDecimal.class, //Amt Insured
		Integer.class, //Term
		BigDecimal.class, //Premium
		String.class, //POlicy No
		Timestamp.class, //Date Approved
		Timestamp.class, //Date From
		Timestamp.class, //Date To
		String.class, //Applicant No
		String.class //RPLF No
		
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

