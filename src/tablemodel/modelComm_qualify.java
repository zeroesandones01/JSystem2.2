package tablemodel;

import java.math.BigDecimal;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class modelComm_qualify	 extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8738810102880371204L;
	
	private boolean editable = false;
	boolean[] COLUMN_EDITABLE;
	
	
	public modelComm_qualify() {
		initThis();
	}

	public modelComm_qualify(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelComm_qualify(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelComm_qualify(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelComm_qualify(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelComm_qualify(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			"Qual." ,				// 0
			"Selling Agent/Broker" ,// 1
			"Pstn", 				// 2
			"Group" ,				// 3
			"Proj", 				// 4
			"Unit", 				// 5
			"Tran. Type", 			// 6
			"Comm. Type/Promo Name",// 7			
			"Gross Amt.", 			// 8
			"VAT", 					// 9
			"WTax", 				// 10
			"For Liq.", 			// 11
			"ATM Fee", 				// 12
			"Net Amt.", 			// 13
			"Remarks", 				// 14
			"Buyer Type" ,			// 15		
			"PBL ID", 				// 16
			"Seq No",				// 17
			"Agent Code", 			// 18
			"Comp ID", 				// 19
			"Acct Code", 			// 20
			"On Hold",				// 21
			"Rec ID",				// 22
			"TIN",					// 23
			"ATM Ded.",				// 24
			"For Disb.",			// 25
			"Client"				// 26
			};

	
	/**
	 * String.class = Alignment.CENTER
	 * Object.class = Alignment.LEFT
	 * BigDecimal.class = Alignment.RIGHT
	 * Integer.class = Alignment.CENTER
	 * Timestamp.class
	 */
	public Class[] CLASS_TYPES = new Class[] {
			Boolean.class, 		//Qual.
			String.class, 		//Selling Agent/Broker
			String.class, 		//Pstn
			String.class,		//Group
			String.class,		//Proj
			String.class,		//Unit
			String.class,		//Tran. Type
			String.class,		//Comm. Type/Promo Name
			BigDecimal.class, 	//Gross Amt.
			BigDecimal.class, 	//VAT
			BigDecimal.class, 	//WTax
			BigDecimal.class, 	//For Liq.
			BigDecimal.class, 	//ATM Fee
			BigDecimal.class, 	//Net Amt.			
			String.class, 		//Remarks
			String.class, 		//Buyer Type
			String.class, 		//PBL ID
			String.class, 		//Seq_No
			String.class, 		//Agent Code
			String.class, 		//Comp ID
			String.class, 		//Acct Code
			Boolean.class ,		//On Hold
			String.class, 		//Rec ID
			String.class, 		//TIN
			Boolean.class, 		//ATM
			Boolean.class, 		//For Disb. Proc.
			String.class 		//Client
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
		COLUMN_EDITABLE = new boolean[] {
				false, 		//Qual.
				false, 		//Selling Agent/Broker
				false, 		//Pstn
				false,		//Group
				false,		//Proj
				false,		//Unit
				false,		//Tran. Type
				false,		//Comm. Type/Promo Name
				false, 		//Gross Amt.
				false, 		//VAT
				false, 		//WTax
				false, 		//For Liq.
				false, 		//ATM Fee
				false, 		//Net Amt.
				false, 		//Remarks
				false, 		//Buyer Type
				false, 		//PBL ID
				false, 		//Seq No
				false, 		//Agent Code
				false, 		//Comp ID
				false, 		//Acct Code
				false, 		//On Hold
				false, 		//Rec ID
				false,		//TIN
				false,		//ATM 
				false,		//For Disb. Processing
				false		//Client
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
					true, 		//Qual.
					false, 		//Selling Agent/Broker
					false, 		//Pstn
					false,		//Group
					false,		//Proj
					false,		//Unit
					false,		//Tran. Type
					false,		//Comm. Type/Promo Name
					false, 		//Gross Amt.
					false, 		//VAT
					false, 		//WTax
					false, 		//For Liq.
					false, 		//ATM Fee
					false, 		//Net Amt.
					false, 		//Remarks
					false, 		//Buyer Type
					false, 		//PBL ID
					false, 		//Seq No
					false, 		//Agent Code
					false, 		//Comp ID
					false, 		//Acct Code
					false, 		//On Hold
					false, 		//Rec ID
					false,		//TIN
					false,		//ATM 
					true,		//For Disb. Processing
					false		//Client
			};
		}else{
			COLUMN_EDITABLE = new boolean[] {
					false, 		//Qual.
					false, 		//Selling Agent/Broker
					false, 		//Pstn
					false,		//Group
					false,		//Proj
					false,		//Unit
					false,		//Tran. Type
					false,		//Comm. Type/Promo Name
					false, 		//Gross Amt.
					false, 		//VAT
					false, 		//WTax
					false, 		//For Liq.
					false, 		//ATM Fee
					false, 		//Net Amt.
					false, 		//Remarks
					false, 		//Buyer Type
					false, 		//PBL ID
					false, 		//Seq No
					false, 		//Agent Code
					false, 		//Comp ID
					false,		//Acct Code
					false, 		//On Hold
					false, 		//Rec ID
					false,		//TIN
					false,		//ATM 
					false,		//For Disb. Processing
					false		//Client
			};
		}
	}

	
	
	
}