package tablemodel;

import java.math.BigDecimal;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import DateChooser._JDateChooser;
import Functions.FncTables;
import Lookup._JLookup;

public class modelIssuanceCashReturn extends DefaultTableModel {

	private static final long serialVersionUID = 1L;

	private boolean editable = false;

	public modelIssuanceCashReturn() {
		initThis();
	}

	public modelIssuanceCashReturn(boolean editable) {
		initThis();
	}

	public modelIssuanceCashReturn(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelIssuanceCashReturn(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelIssuanceCashReturn(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelIssuanceCashReturn(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelIssuanceCashReturn(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}

	String[] COLUMNS = new String[] {
			"Reference No",
			"Part ID", // 0
			"Particulars", // 1
			"Amount", // 2
			"Type", // 3
			"Check No.", // 4
			"Check Type ID", // 5
			"Check Type", // 6
			"Check Date", // 7
			"Account No.", // 8
			"Bank ID", // 9
			"Bank Name", // 10
			"Branch ID", // 11
			"Branch Name", // 12
			"Receipt No.", // 13
			"Receipt ID", // 14
			"Receipt Type", // 15
			"Credit"
	};

	Class[] CLASS_TYPES = new Class[] {
			String.class,
			String.class, //Part ID 
			_JLookup.class, //Particulars
			BigDecimal.class, //Amount
			String.class, //Payment Type
			Object.class, //Check No.
			String.class, //Check Type ID
			_JLookup.class, //Check Type
			_JDateChooser.class, //Check Date
			Object.class, //Account No.
			String.class, //Bank ID
			_JLookup.class, //Bank Name
			String.class, //Branch ID
			_JLookup.class, //Branch Name
			String.class, //Receipt No.
			String.class, //Receipt ID
			String.class, //Receipt Name
			Boolean.class
	};

	Boolean[] COLUMN_EDITABLE = new Boolean[] {
			true,
			false, //Part ID 
			false, //Particulars
			false, //Amount
			false, //Payment Type
			false, //Check No.
			false, //Check Type ID
			false, //Check Type
			false, //Check Date
			false, //Account No.
			false, //Bank ID
			false, //Bank Name
			false, //Branch ID
			false, //Branch Name
			false, //Receipt No.
			false, //Receipt ID
			false, //Receipt Name
			false
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
	}

	public Class getColumnClass(int columnIndex) {
		return CLASS_TYPES[columnIndex];
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return COLUMN_EDITABLE[columnIndex];
	}

	public void clear() {
		FncTables.clearTable(this);
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;

		if(editable){
			COLUMN_EDITABLE = new Boolean[] {
					true,
					false, //Part ID 
					false, //Particulars
					true, //Amount
					false, //Payment Type
					true, //Check No.
					false, //Check Type ID
					false, //Check Type
					true, //Check Date
					true, //Account No.
					false, //Bank ID
					false, //Bank Name
					false, //Branch ID
					false, //Branch Name
					true, //Receipt No.
					true, //Receipt ID
					true, //Receipt Name
					false
			};
		}else{
			COLUMN_EDITABLE = new Boolean[] {
					true,
					false, //Part ID 
					false, //Particulars
					false, //Amount
					false, //Payment Type
					false, //Check No.
					false, //Check Type ID
					false, //Check Type
					false, //Check Date
					false, //Account No.
					false, //Bank ID
					false, //Bank Name
					false, //Branch ID
					false, //Branch Name
					false, //Receipt No.
					false, //Receipt ID
					false, //Receipt Name
					false
			};
		}
	}

}
