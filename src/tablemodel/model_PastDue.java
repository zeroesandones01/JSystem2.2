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
 * @author Christian Paquibot
 *
 */	@SuppressWarnings({"unchecked","rawtypes"})
public class model_PastDue extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private boolean editable = false;
	public model_PastDue() {
		initThis();
	}

	/**
	 * @param rowCount
	 * @param columnCount
	 */
	public model_PastDue(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	/**
	 * @param columnNames
	 * @param rowCount
	 */
	public model_PastDue(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	/**
	 * @param columnNames
	 * @param rowCount
	 */
	public model_PastDue(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	/**
	 * @param data
	 * @param columnNames
	 */
	public model_PastDue(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	/**
	 * @param data
	 * @param columnNames
	 */
	
	public model_PastDue(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}
	
	String[] COLUMNS = new String[] {
			"Tag", // 0
			"Unit", // 1
			"Client Name", // 2
			"Buyer Type", //3
			"Sales Division", // 4
			"Contact No.", // 5
			"House Model", //6
			//-- MERRGE THIS TWO COLUMN INTO ONE COLUMN "ACCT. STATUS" AS PER KIM
			"Sold to Bank", //7 
			"Buyback", //8
			//---
			"Moved in", //9 -- CHANGE TO HOUSE STATUS AS PER KIM. 
			"Stage", // 10
			"TR Date", // 11
			"OR Date", // 12
			"Default Date", //13
			"Last Date Paid", //14 
			"Month PD", // 15
			"Days PD", // 16
			"Payments Stage", // 17
			"Amount Due", // 18 // --- REMOVE THIS COLUMN AS PER KIM 
			"Last Phone Follow up", //19
			"Email Add.", // 20
			"PN No.", //21 -- ADD FUNCTION RIGHT CLICK TO SEE THE PN HISTORY AS PER KIM
			"Notice of Default", //22
			"Notice of Cancellation", //23
			"NSP", // 24
			
		  /*"Last Phone Follow up", //15
			"PN Date", //16
			"Notice of Default", //17 
			"Notice of Final Demand", //18
			"Notice of Cancel", // 19
			"Notice of Cancellation", //20
			"CSV Date Prepared", //21
			"CSV Date Deposited" // 22*/	
			
			"Entity ID", // 25
			"Project ID", // 26
			"PBL ID", // 27
			"Seq ID", // 28
			"Unit ID", //29
			"Part ID", // 30
			"LastSchedDate", //31
			"Phase", // 32
			"Project Name", // 33
			"OB - Company", // 34
			"OB - Bank", // 35
			"Due Date (Bank)", // 36
			"TCT No.", // 37
			"Reason for Buyback", // 38
			"Remarks",//39
			"With NTC", //40
			"CTS Notarized" //41
	};
	Class[] CLASS_TYPES = new Class[] {
			Boolean.class,		//"", // 0 
			String.class,		//"Unit", // 1
			Object.class,		//"Client Name", // 2
			String.class,		//"Buyer Type,	// 
			String.class,		//"Sales Division,	// 3
			String.class,		//"Contact No.", // 4
			String.class,		//"House Model", //5
			Timestamp.class,	//"Sold to Bank", //6
			Timestamp.class,	//"Buyback", //7
			Timestamp.class,	//"Moved in", //8
			String.class,		//"Stage", // 9
			Timestamp.class,	//"TR Date", // 10
			Timestamp.class,	//"OR Date", // 11
			Timestamp.class,	//"Default Date", //12
			Timestamp.class,	//"Last Date Paid", //13 
			String.class,		//"Month PD", // 14
			String.class,		//"Days PD", // 15
			String.class,		//"Payments Made", // 16
			BigDecimal.class,	//"Amount Due", // 17
			String.class,		//"Last Phone Follow up", //18 
			String.class,		//"Email Add.", // 19
			String.class,		//"PN No.", //20
			Timestamp.class,	//"Notice of Default", //21
			Timestamp.class,	//"Notice of Cancellation", //22
			BigDecimal.class,	//"NSP", // 23
			String.class,		//"Entity ID", // 24
			String.class,		//"Project ID", //25
			String.class,		//"PBL ID", //26
			String.class,		//"Seq ID", //27
			String.class,		//"Unit ID", //28
			String.class,		//"Part ID", //29
			Timestamp.class, 	//"LastSchedDate", //30 
			String.class,		//"Phase", //31
			String.class,		//"Project Name", //32
			BigDecimal.class,	//"OB - Company", // 33
			BigDecimal.class,	//"OB - Bank", // 34
			Timestamp.class, 	//"Due Date (Bank)", // 35
			String.class,		//"TCT No.", // 36
			String.class,		//"Reason for Buyback" // 37
			String.class,		//"Remarks"//38
			String.class,		//"With NTC", //40
			Timestamp.class		//"CTS Notarized" //41
	};
	
	boolean[] COLUMN_EDITABLE = new boolean[] {
			
			true,		//"", // 0 
			false,		//"Unit", // 1
			false,		//"Client Name", // 2
			false,		//"Buyer Type,	// 3
			false,		//"Sales Division,	// 3
			false,		//"Contact No.", // 4
			false,		//"House Model", //5
			false,	//"Sold to Bank", //6
			false,	//"Buyback", //7
			false,	//"Moved in", //8
			false,		//"Stage", // 9
			false,	//"TR Date", // 10
			false,	//"OR Date", // 11
			false,	//"Default Date", //12
			false,	//"Last Date Paid", //13 
			false,		//"Month PD", // 14
			false,		//"Days PD", // 15
			false,		//"Payments Made", // 16
			false,	//"Amount Due", // 17
			false,		//"Last Phone Follow up", //18 
			false,		//"Email Add.", // 19
			false,		//"PN No.", //20
			false,	//"Notice of Default", //21
			false,	//"Notice of Cancellation", //22
			false,	//"NSP", // 23
			false,		//"Entity ID", // 24
			false,		//"Project ID", //25
			false,		//"PBL ID", //26
			false,		//"Seq ID", //27
			false,		//"Unit ID", //28
			false,		//"Part ID", //29
			false, 	//"LastSchedDate", //30 
			false,		//"Phase", //31
			false,		//"Project Name", //32
			false,	//"OB - Company", // 33
			false,	//"OB - Bank", // 34
			false, 	//"Due Date (Bank)", // 35
			false,		//"TCT No.", // 36
			false,		//"Reason for Buyback" // 37
			false,		//"Remarks"//38
			false,		//"With NTC" // 37
			false		//"CTS Notarized"//38
			
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

	public boolean isEditable() {
		return editable;
	}
	
	public void setEditable(boolean editable) {
	
		this.editable = editable;
		if(editable){
			COLUMN_EDITABLE = new boolean[] {
					
					true,		//"", // 0 
					false,		//"Unit", // 1
					false,		//"Client Name", // 2
					false,		//"Buyer Type,	// 3
					false,		//"Sales Division,	// 3
					false,		//"Contact No.", // 4
					false,		//"House Model", //5
					false,	//"Sold to Bank", //6
					false,	//"Buyback", //7
					false,	//"Moved in", //8
					false,		//"Stage", // 9
					false,	//"TR Date", // 10
					false,	//"OR Date", // 11
					false,	//"Default Date", //12
					false,	//"Last Date Paid", //13 
					false,		//"Month PD", // 14
					false,		//"Days PD", // 15
					false,		//"Payments Made", // 16
					false,	//"Amount Due", // 17
					false,		//"Last Phone Follow up", //18 
					false,		//"Email Add.", // 19
					false,		//"PN No.", //20
					false,	//"Notice of Default", //21
					false,	//"Notice of Cancellation", //22
					false,	//"NSP", // 23
					false,		//"Entity ID", // 24
					false,		//"Project ID", //25
					false,		//"PBL ID", //26
					false,		//"Seq ID", //27
					false,		//"Unit ID", //28
					false,		//"Part ID", //29
					false, 	//"LastSchedDate", //30 
					false,		//"Phase", //31
					false,		//"Project Name", //32
					false,	//"OB - Company", // 33
					false,	//"OB - Bank", // 34
					false, 	//"Due Date (Bank)", // 35
					false,		//"TCT No.", // 36
					true,		//"Reason for Buyback" // 37
					true,		//"Remarks"//38
					false,		//"With NTC" // 37
					false		//"CTS Notarized"//38
					
			};
		}else{
			
			COLUMN_EDITABLE = new boolean[] {
					
					false,		//"", // 0 
					false,		//"Unit", // 1
					false,		//"Client Name", // 2
					false,		//"Buyer Type,	// 3
					false,		//"Sales Division,	// 3
					false,		//"Contact No.", // 4
					false,		//"House Model", //5
					false,	//"Sold to Bank", //6
					false,	//"Buyback", //7
					false,	//"Moved in", //8
					false,		//"Stage", // 9
					false,	//"TR Date", // 10
					false,	//"OR Date", // 11
					false,	//"Default Date", //12
					false,	//"Last Date Paid", //13 
					false,		//"Month PD", // 14
					false,		//"Days PD", // 15
					false,		//"Payments Made", // 16
					false,	//"Amount Due", // 17
					false,		//"Last Phone Follow up", //18 
					false,		//"Email Add.", // 19
					false,		//"PN No.", //20
					false,	//"Notice of Default", //21
					false,	//"Notice of Cancellation", //22
					false,	//"NSP", // 23
					false,		//"Entity ID", // 24
					false,		//"Project ID", //25
					false,		//"PBL ID", //26
					false,		//"Seq ID", //27
					false,		//"Unit ID", //28
					false,		//"Part ID", //29
					false, 	//"LastSchedDate", //30 
					false,		//"Phase", //31
					false,		//"Project Name", //32
					false,	//"OB - Company", // 33
					false,	//"OB - Bank", // 34
					false, 	//"Due Date (Bank)", // 35
					false,		//"TCT No.", // 36
					false,		//"Reason for Buyback" // 37
					false,		//"Remarks"//38
					false,		//"With NTC" // 37
					false		//"CTS Notarized"//38
			};
		}
	}
	
	
	public void clear() {
		FncTables.clearTable(this);
	}
}
