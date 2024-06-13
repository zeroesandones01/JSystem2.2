package tablemodel;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class modelDirectDepositIssuance extends DefaultTableModel {
	
	private static final long serialVersionUID = 6617333319999252646L;
	
	private boolean editable = false;
	boolean[] COLUMN_EDITABLE;

	public modelDirectDepositIssuance() {
		initThis();
	}

	public modelDirectDepositIssuance(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		// TODO Auto-generated constructor stub
	}

	public modelDirectDepositIssuance(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		// TODO Auto-generated constructor stub
	}

	public modelDirectDepositIssuance(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		// TODO Auto-generated constructor stub
	}

	public modelDirectDepositIssuance(Vector data, Vector columnNames) {
		super(data, columnNames);
		// TODO Auto-generated constructor stub
	}

	public modelDirectDepositIssuance(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		// TODO Auto-generated constructor stub
	}

	
	public String[] COLUMNS = new String[] {
			"Client Seq No",
			"Part Type",
			"Receipt Type", 
			"Receipt No", 
			"Deposit Date",
			"Client", 
			"Proj.",
			"Unit", 
			"Bank",
			"Account No", 
			"Amount"
			
	};

	public Class[] CLASS_TYPES = new Class[] {
			
			String.class, //ClientSeqno
			String.class, //Part Type
			String.class, //Receipt Type
			String.class, //Receipt No
			Date.class, //Deposit Date
			String.class, //Client
			String.class, //Proj
			String.class, //Unit
			String.class, //Bank
			String.class, //Account No
			BigDecimal.class //Amount
			
			
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
		COLUMN_EDITABLE = new boolean[] {
				false,
				false,	
				false,
				false,
				false,
				false,
				false,
				false,
				false,
				false,
				false
				
				
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
					false,
					false,	
					false,
					false,
					false,
					false,
					false,
					false,
					false,
					false
			};
		}else{
			COLUMN_EDITABLE = new boolean[] {
					false,
					false,	
					false,
					false,
					false,
					false,
					false,
					false,
					false,
					false
			};
		}
	}
}
