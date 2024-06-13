package tablemodel;

import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class modelBankAccount extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8738810102880371204L;
	
	private boolean editable = false;
	boolean[] COLUMN_EDITABLE;
	
	
	public modelBankAccount() {
		initThis();
	}

	public modelBankAccount(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelBankAccount(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelBankAccount(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelBankAccount(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelBankAccount(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			"Bank Acct. ID",		// 1
			"Bank Acct. No." ,		// 2
			"Bank Acct. Desc." ,	// 3
			"Bank Alias" ,			// 4
			"Bank Branch" ,			// 5
			"Acct. Type", 			// 6
			"Account ID", 			// 7
			"Fund Class", 			// 8
			"Cash Deposit" ,		// 9
			"Created by" ,			// 10
			"Created Date" ,		// 11
			"Last Edited by" ,		// 12
			"Last Edited Date" ,	// 13
			"Status", 				// 14
			"Comm. Disb." 			// 15
			};

	
	/**
	 * String.class = Alignment.CENTER
	 * Object.class = Alignment.LEFT
	 * BigDecimal.class = Alignment.RIGHT
	 * Integer.class = Alignment.CENTER
	 * Timestamp.class
	 */
	public Class[] CLASS_TYPES = new Class[] {
			String.class, 		//Bank Acct. ID
			String.class, 		//Bank Acct. No.
			Object.class, 		//Bank Acct. Desc.
			String.class, 		//Bank Alias
			String.class,		//Bank Branch
			String.class,	//Acct. Type
			String.class,		//Account ID
			String.class,		//Fund Class
			String.class,		//Cash Deposit
			String.class,		//Created by
			Timestamp.class,	//Created Date
			String.class,		//Last Edited by
			Timestamp.class,	//Last Edited Date		
			String.class,		//Status
			String.class		//Comm. Disb.
			
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
		COLUMN_EDITABLE = new boolean[] {
				false, 		//Bank Acct. ID
				false, 		//Bank Acct. No.
				false, 		//Bank Acct. Desc.
				false, 		//Bank Alias
				false,		//Bank Branch
				false,		//Acct. Type
				false,		//Account ID
				false,		//Fund Class
				false,		//Cash Deposit
				false,		//Created by
				false,		//Created Date
				false,		//Last Edited by
				false,		//Last Edited Date		
				false,		//Status
				false		//Comm. Disb.
		};
	}

	public Class getColumnClass(int columnIndex) {
		return CLASS_TYPES[columnIndex];
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return COLUMN_EDITABLE[columnIndex];
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
		if(editable){
			COLUMN_EDITABLE = new boolean[] {
					false, 		//Bank Acct. ID
					false, 		//Bank Acct. No.
					false, 		//Bank Acct. Desc.
					false, 		//Bank Alias
					false,		//Bank Branch
					false,		//Acct. Type
					false,		//Account ID
					false,		//Fund Class
					false,		//Cash Deposit
					false,		//Created by
					false,		//Created Date
					false,		//Last Edited by
					false,		//Last Edited Date		
					false,		//Status
					false		//Comm. Disb.
			};
		}else{
			COLUMN_EDITABLE = new boolean[] {
					false, 		//Bank Acct. ID
					false, 		//Bank Acct. No.
					false, 		//Bank Acct. Desc.
					false, 		//Bank Alias
					false,		//Bank Branch
					false,		//Acct. Type
					false,		//Account ID
					false,		//Fund Class
					false,		//Cash Deposit
					false,		//Created by
					false,		//Created Date
					false,		//Last Edited by
					false,		//Last Edited Date		
					false,		//Status
					false		//Comm. Disb.
			};
		}
	}

	
	
	
}
