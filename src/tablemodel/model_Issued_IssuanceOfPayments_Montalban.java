package tablemodel;

import java.math.BigDecimal;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class model_Issued_IssuanceOfPayments_Montalban extends DefaultTableModel {

	public model_Issued_IssuanceOfPayments_Montalban() {
		initThis();
	}

	public model_Issued_IssuanceOfPayments_Montalban(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public model_Issued_IssuanceOfPayments_Montalban(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public model_Issued_IssuanceOfPayments_Montalban(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public model_Issued_IssuanceOfPayments_Montalban(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public model_Issued_IssuanceOfPayments_Montalban(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			
			"Sequence", //0
			"Entity ID", //1
			"Proj. ID", //2
			"Unit ID", //3
			"Seq. No",
			"Client", 
			"Unit", 
			"Amount",
			"Receipt NO"
			
	};
	
	public Class[] CLASS_TYPES = new Class[] {
			
			String.class, //Sequence
			String.class, //Entity ID
			String.class, //Proj. ID
			String.class, //Unit ID
			Integer.class, //Seq No.
			String.class, //Client
			String.class, //Unit
			BigDecimal.class, //Amount
			String.class //Receipt NO
			
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
