package tablemodel;

import java.math.BigDecimal;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelPricelist_complete extends DefaultTableModel {

	private static final long serialVersionUID = 1L;

	private boolean editable = false;
	//boolean[] COLUMN_EDITABLE;

	public modelPricelist_complete() {
		initThis();
	}

	public modelPricelist_complete(boolean editable) {
		initThis();
	}

	public modelPricelist_complete(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelPricelist_complete(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelPricelist_complete(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelPricelist_complete(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelPricelist_complete(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}

	String[] COLUMNS = new String[] {
			"Phase", 		// 0
			"Block", 		// 1
			"Lot", 			// 2
			"Lot Area", 	// 3
			"Model Alias", 	// 4
			"Total Factor", // 5
			"House APV", 	// 6
			"Aprraised Value per sqm",// 7
			"Lot APV", 		// 8
			"Total Unit APV", // 9
			"House SP", 	// 10
			"Lot SP", 		// 11
			"Other Factor", // 12
			"TCP", 			// 13
			"Discount", 	// 14
			"TCP (Discounted)", 	// 15
			"Loanable (90%)", 		// 16
			"TCP=LA", 		// 17
			"Loanable Amt. (Final)",// 18
			"% of LA to APV", // 19	
			"% LA to TCP", 	// 20
			"Equity", 		// 21
			"MRI", 			// 22
			"FI", 			// 23
			"Filing Fee (Release)", // 24
			"Doc Stamps", 	// 25
			"Inspection Fee", 		// 26
			"Commitment Fee", 		// 27
			"Add'l HDMF Retention ",// 28
			"Filing Fee (Filing)", 	// 29
			"Interest on CTS 1st",  // 30
			"UPICO", 		// 31
			"Misc. Fees", 	// 32
			"Equity", 		// 33
			"Total Cash Outlay", 	// 34
			"11 Months Cash Outlay",// 35
			"MA (6.5%)", 	// 36
			"MRI (6.5%)", 	// 37
			"FI (6.5%)", 	// 38
			"Addl Premium (6.5%)", 	// 39
			"Total MA (6.5%)", 		// 40
			"Required Income (6.5%)",// 41
			"MA (7.985%)", // 42
			"MRI (7.985%)",// 43
			"FI (7.985%)", // 44
			"Addl Premium (7.985%)",// 45
			"Total MA (7.985%)", 	// 46
			"Required Income (7.985%)" // 47
			
	};

	Class[] CLASS_TYPES = new Class[] {
			
			String.class,   //Phase		
			String.class,   //Block	
			String.class,   //Lot			
			BigDecimal.class,   //Lot Area	
			String.class,   //Model Alias	
			BigDecimal.class, //Total Factor
			BigDecimal.class, //House APV
			BigDecimal.class, //Aprraised Value per sqm
			BigDecimal.class, //Lot APV
			BigDecimal.class, //Total Unit APV
			BigDecimal.class, //House SP
			BigDecimal.class, //Lot SP
			BigDecimal.class, //Other Factor
			BigDecimal.class, //TCP
			BigDecimal.class, //Discount
			BigDecimal.class, //TCP (Discounted)
			BigDecimal.class, //Loanable (90%)
			BigDecimal.class, //TCP=LA
			BigDecimal.class, //Loanable Amt. (Final)
			BigDecimal.class, //% of LA to APV	
			BigDecimal.class, //% LA to TCP
			BigDecimal.class, //Equity
			BigDecimal.class, //MRI
			BigDecimal.class, //FI
			BigDecimal.class, //Filing Fee (Release)
			BigDecimal.class, //Doc Stamps
			BigDecimal.class, //Inspection Fee
			BigDecimal.class, //Commitment Fee
			BigDecimal.class, //Add'l HDMF Retention
			BigDecimal.class, //Filing Fee (Filing)
			BigDecimal.class, //Interest on CTS 1st
			BigDecimal.class, //UPICO
			BigDecimal.class, //Misc. Fees
			BigDecimal.class, //Equity
			BigDecimal.class, //Total Cash Outlay
			BigDecimal.class, //11 Months Cash Outlay
			BigDecimal.class, //MA (6.5%)
			BigDecimal.class, //MRI (6.5%)
			BigDecimal.class, //FI (6.5%)
			BigDecimal.class, //Addl Premium (6.5%)
			BigDecimal.class, //Total MA (6.5%)
			BigDecimal.class, //Required Income (6.5%)
			BigDecimal.class, //MA (7.985%)
			BigDecimal.class, //MRI (7.985%)
			BigDecimal.class, //FI (7.985%)
			BigDecimal.class, //Addl Premium (7.985%)6
			BigDecimal.class, //Total MA (7.985%)
			BigDecimal.class //Required Income (7.985%)
			
	};

	Boolean[] COLUMNS_EDITABLE = new Boolean[] {
			false,   //Phase		
			false,   //Block	
			false,   //Lot			
			true,   //Lot Area	
			true,   //Model Alias	
			true, //Total Factor
			true, //House APV
			true, //Aprraised Value per sqm
			true, //Lot APV
			true, //Total Unit APV
			true, //House SP
			true, //Lot SP
			true, //Other Factor
			true, //TCP
			true, //Discount
			true, //TCP (Discounted)
			true, //Loanable (90%)
			true, //TCP=LA
			true, //Loanable Amt. (Final)
			true, //% of LA to APV	
			true, //% LA to TCP
			true, //Equity
			true, //MRI
			true, //FI
			true, //Filing Fee (Release)
			true, //Doc Stamps
			true, //Inspection Fee
			true, //Commitment Fee
			true, //Add'l HDMF Retention
			true, //Filing Fee (Filing)
			true, //Interest on CTS 1st
			true, //UPICO
			true, //Misc. Fees
			true, //Equity
			true, //Total Cash Outlay
			true, //11 Months Cash Outlay
			true, //MA (6.5%)
			true, //MRI (6.5%)
			true, //FI (6.5%)
			true, //Addl Premium (6.5%)
			true, //Total MA (6.5%)
			true, //Required Income (6.5%)
			true, //MA (7.985%)
			true, //MRI (7.985%)
			true, //FI (7.985%)
			true, //Addl Premium (7.985%)6
			true, //Total MA (7.985%)
			true //Required Income (7.985%)
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);

		COLUMNS_EDITABLE = new Boolean[] {
				false,   //Phase		
				false,   //Block	
				false,   //Lot			
				true,   //Lot Area	
				true,   //Model Alias	
				true, //Total Factor
				true, //House APV
				true, //Aprraised Value per sqm
				true, //Lot APV
				true, //Total Unit APV
				true, //House SP
				true, //Lot SP
				true, //Other Factor
				true, //TCP
				true, //Discount
				true, //TCP (Discounted)
				true, //Loanable (90%)
				true, //TCP=LA
				true, //Loanable Amt. (Final)
				true, //% of LA to APV	
				true, //% LA to TCP
				true, //Equity
				true, //MRI
				true, //FI
				true, //Filing Fee (Release)
				true, //Doc Stamps
				true, //Inspection Fee
				true, //Commitment Fee
				true, //Add'l HDMF Retention
				true, //Filing Fee (Filing)
				true, //Interest on CTS 1st
				true, //UPICO
				true, //Misc. Fees
				true, //Equity
				true, //Total Cash Outlay
				true, //11 Months Cash Outlay
				true, //MA (6.5%)
				true, //MRI (6.5%)
				true, //FI (6.5%)
				true, //Addl Premium (6.5%)
				true, //Total MA (6.5%)
				true, //Required Income (6.5%)
				true, //MA (7.985%)
				true, //MRI (7.985%)
				true, //FI (7.985%)
				true, //Addl Premium (7.985%)6
				true, //Total MA (7.985%)
				true //Required Income (7.985%)
		};
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
		if(editable){
			COLUMNS_EDITABLE = new Boolean[] {
					false,   //Phase		
					false,   //Block	
					false,   //Lot			
					true,   //Lot Area	
					true,   //Model Alias	
					true, //Total Factor
					true, //House APV
					true, //Aprraised Value per sqm
					true, //Lot APV
					true, //Total Unit APV
					true, //House SP
					true, //Lot SP
					true, //Other Factor
					true, //TCP
					true, //Discount
					true, //TCP (Discounted)
					true, //Loanable (90%)
					true, //TCP=LA
					true, //Loanable Amt. (Final)
					true, //% of LA to APV	
					true, //% LA to TCP
					true, //Equity
					true, //MRI
					true, //FI
					true, //Filing Fee (Release)
					true, //Doc Stamps
					true, //Inspection Fee
					true, //Commitment Fee
					true, //Add'l HDMF Retention
					true, //Filing Fee (Filing)
					true, //Interest on CTS 1st
					true, //UPICO
					true, //Misc. Fees
					true, //Equity
					true, //Total Cash Outlay
					true, //11 Months Cash Outlay
					true, //MA (6.5%)
					true, //MRI (6.5%)
					true, //FI (6.5%)
					true, //Addl Premium (6.5%)
					true, //Total MA (6.5%)
					true, //Required Income (6.5%)
					true, //MA (7.985%)
					true, //MRI (7.985%)
					true, //FI (7.985%)
					true, //Addl Premium (7.985%)6
					true, //Total MA (7.985%)
					true //Required Income (7.985%)
			};
		}else{
			COLUMNS_EDITABLE = new Boolean[] {
					false,   //Phase		
					false,   //Block	
					false,   //Lot			
					false,   //Lot Area	
					false,   //Model Alias	
					false, //Total Factor
					false, //Floor Area
					false, //House APV
					false, //Aprraised Value per sqm
					false, //Lot APV
					false, //Total Unit APV
					false, //House SP
					false, //Lot SP
					false, //Other Factor
					false, //TCP
					false, //Discount
					false, //TCP (Discounted)
					false, //Loanable (90%)
					false, //TCP=LA
					false, //Loanable Amt. (Final)
					false, //% of LA to APV	
					false, //% LA to TCP
					false, //Equity
					false, //MRI
					false, //FI
					false, //Filing Fee (Release)
					false, //Doc Stamps
					false, //Inspection Fee
					false, //Commitment Fee
					false, //Add'l HDMF Retention
					false, //Filing Fee (Filing)
					false, //Interest on CTS 1st
					false, //UPICO
					false, //Misc. Fees
					false, //Equity
					false, //Total Cash Outlay
					false, //11 Months Cash Outlay
					false, //MA (6.5%)
					false, //MRI (6.5%)
					false, //FI (6.5%)
					false, //Addl Premium (6.5%)
					false, //Total MA (6.5%)
					false, //Required Income (6.5%)
					false, //MA (7.985%)
					false, //MRI (7.985%)
					false, //FI (7.985%)
					false, //Addl Premium (7.985%)6
					false, //Total MA (7.985%)
					false //Required Income (7.985%)
			};
		}
	}

}
