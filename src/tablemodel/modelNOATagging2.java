package tablemodel;

import java.math.BigDecimal;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import DateChooser._JDateChooser;
import Functions.FncTables;

public class modelNOATagging2 extends DefaultTableModel {

	private static final long serialVersionUID = 463705227485649592L;
	private boolean editable = false;

	public modelNOATagging2() {
		initThis();
		// TODO Auto-generated constructor stub
	}

	public modelNOATagging2(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		// TODO Auto-generated constructor stub
	}

	public modelNOATagging2(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		// TODO Auto-generated constructor stub
	}

	public modelNOATagging2(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		// TODO Auto-generated constructor stub
	}

	public modelNOATagging2(Vector data, Vector columnNames) {
		super(data, columnNames);
		// TODO Auto-generated constructor stub
	}

	public modelNOATagging2(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		// TODO Auto-generated constructor stub
	}

	String [] COLUMNS = new String [] {
			"Select", 				// 0
			"Client ID", 			// 1
			"Client Name", 			// 2
			"Ph-Bl-Lt", 			// 3
			"PBL ID", 				// 4
			"Seq.", 				// 5
			"BuyerType", 			// 6
			"Proj. ID", 			// 7
			"Project", 				// 8
			"Loan Amt", 			// 9
			"Loan Term(in years)",	// 10
			"Actual Date"			// 11
		};
			
	Class [] CLASS_TYPES = new Class []{
			Boolean.class, 		//Select
			String.class, 		//Client ID
			Object.class, 		//Client Name
			String.class, 		//Ph-Bl-Lt
			String.class, 		//PBL ID
			Integer.class, 		//Seq.
			Object.class, 		//BuyerType
			String.class, 		//Proj. ID
			Object.class, 		//Project
			BigDecimal.class, 	//Loan Amt
			Integer.class, 		//Loan Term(in years)
			_JDateChooser.class
	};
	
	Boolean [] COLUMNS_EDITABLE = new Boolean []{
			true,	//Select
			false,	//Client ID
			false,	//Client Name
			false,	//Ph-Bl-Lt
			false,	//PBL ID
			false,	//Seq.
			false,	//BuyerType
			false,	//Proj. ID
			false,	//Project
			true,	//Loan Amt
			false,	//Loan Term (in years)
			true
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
