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
 public class _model_PastDue extends DefaultTableModel {

	 /**
	  * 
	  */
	 private static final long serialVersionUID = 1L;
	 /**
	  * 
	  */
	 private boolean editable = false;
	 public _model_PastDue() {
		 initThis();
	 }

	 /**
	  * @param rowCount
	  * @param columnCount
	  */
	 public _model_PastDue(int rowCount, int columnCount) {
		 super(rowCount, columnCount);
		 initThis();
	 }

	 /**
	  * @param columnNames
	  * @param rowCount
	  */
	 public _model_PastDue(Vector columnNames, int rowCount) {
		 super(columnNames, rowCount);
		 initThis();
	 }

	 /**
	  * @param columnNames
	  * @param rowCount
	  */
	 public _model_PastDue(Object[] columnNames, int rowCount) {
		 super(columnNames, rowCount);
		 initThis();
	 }

	 /**
	  * @param data
	  * @param columnNames
	  */
	 public _model_PastDue(Vector data, Vector columnNames) {
		 super(data, columnNames);
		 initThis();
	 }

	 /**
	  * @param data
	  * @param columnNames
	  */

	 public _model_PastDue(Object[][] data, Object[] columnNames) {
		 super(data, columnNames);
		 initThis();
	 }

	 String[] COLUMNS = new String[] {
			 "Tag", // 0
			 "Unit", // 1
			 "Client Name", // 2
			 "Buyer Type", // 3
			 "Sales Division", // 4
			 "Contact No.", // 5
			 "House Model", // 6
			 "Acct. Status",// 7
			 "House Status", // 8  
			 "TR Date", // 9
			 "OR Date", // 10
			 "Default Date", // 11
			 "Last Date Paid", // 12 
			 "Days PD", // 13
			 "Months PD", // 13
			 "Payments Stage", // 14
			 "Last Phone Follow up", // 15
			 "Email Add.", // 16
			 "PN No.", // 17 -- ADD FUNCTION RIGHT CLICK TO SEE THE PN HISTORY AS PER KIM
			 "Notice of Default", // 18
			 "Notice of Final Demand", // 19
			 "Notice of Cancel", // 20
			 "Maceda", // 21 -- BOOLEAN- CheckBox
			 "NSP", // 22
			 "Entity ID", // 23
			 "Project ID", // 24
			 "PBL ID", // 25
			 "Seq ID", // 26
			 "Unit ID", //27
			 "Part ID", // 28
			 "LastSchedDate", //29
			 "Phase", // 30
			 "Project Name", // 31
			 "OB - Company", // 32
			 "OB - Bank", // 33
			 "Due Date (Bank)", // 34
			 "TCT No.", // 35
			 "With NTC", //36
			 "CTS Notarized", //37
			 "Reason For Buyback", // 38
			 "Remarks"//39
			// "Reason for Buyback", // 37
			// "Remarks",//38
			 /*"Last Phone Follow up", //15
				"PN Date", //16
				"Notice of Default", //17 
				"Notice of Final Demand", //18
				"Notice of Cancel", // 19
				"Notice of Cancellation", //20
				"CSV Date Prepared", //21
				"CSV Date Deposited" // 22*/	
	 };
	 Class[] CLASS_TYPES = new Class[] {
			 Boolean.class,//"Tag", // 0
			 String.class,//"Unit", // 1
			 String.class,//"Client Name", // 2
			 String.class,//"Buyer Type", // 3
			 String.class,//String.class,//"Sales Division", // 4
			 String.class,//"Contact No.", // 5
			 String.class,//"House Model", // 6
			 String.class,//"Acct. Status",// 7
			 String.class,//"House Status", // 8  
			 Timestamp.class,//"TR Date", // 9
			 Timestamp.class,//"OR Date", // 10
			 Timestamp.class,//"Default Date", // 11
			 Timestamp.class,//"Last Date Paid", // 12 
			 Integer.class,//"Days PD", // 13
			 Integer.class,//"Month PD", // 13
			 String.class,//"Payments Stage", // 14
			 Timestamp.class,//"Last Phone Follow up", // 15
			 String.class,//"Email Add.", // 16
			 String.class,//"PN No.", // 17 -- ADD FUNCTION RIGHT CLICK TO SEE THE PN HISTORY AS PER KIM
			 Timestamp.class,//"Notice of Default", // 18
			 Timestamp.class,//"Notice of Final Demand", // 19
			 Timestamp.class,//"Notice of Cancel", // 20
			 Boolean.class,//"Maceda", // 21 -- BOOLEAN- CheckBox
			 BigDecimal.class,//"NSP", // 22
			 String.class,//"Entity ID", // 23
			 String.class,//String.class,//"Project ID", // 24
			 String.class,//"PBL ID", // 25
			 String.class,//"Seq ID", // 26
			 String.class,//"Unit ID", //27
			 String.class,//"Part ID", // 28
			 Timestamp.class,//"LastSchedDate", //29
			 String.class,//"Phase", // 30
			 String.class,//"Project Name", // 31
			 BigDecimal.class,//"OB - Company", // 32
			 BigDecimal.class,//"OB - Bank", // 33
			 Timestamp.class,//"Due Date (Bank)", // 34
			 String.class,//"TCT No.", // 35
			 String.class,//"With NTC", //36
			 Timestamp.class,//"CTS Notarized" //37
			 String.class,//"Reason for Buyback", // 38
			 String.class//"Remarks"String.class,//39
	 };

	 boolean[] COLUMN_EDITABLE = new boolean[] {

			 true, //"Tag", // 0
			 false, //"Unit", // 1
			 false, //"Client Name", // 2
			 false, //"Buyer Type", // 3
			 false, //"Sales Division", // 4
			 false, //"Contact No.", // 5
			 false, //"House Model", // 6
			 false, //"Acct. Status",// 7
			 false, //"House Status", // 8  
			 false, //"TR Date", // 9
			 false, //"OR Date", // 10
			 false, //"Default Date", // 11
			 false, //"Last Date Paid", // 12 
			 false, //"Month PD", // 13
			 false, //"Month PD", // 13
			 false, //"Payments Stage", // 14
			 false, //"Last Phone Follow up", // 15
			 false, //"Email Add.", // 16
			 false, //"PN No.", // 17 -- ADD FUNCTION RIGHT CLICK TO SEE THE PN HISTORY AS PER KIM
			 false, //"Notice of Default", // 18
			 false, //"Notice of Final Demand", // 19
			 false, //"Notice of Cancel", // 20
			 false, //"Maceda", // 21 -- BOOLEAN- CheckBox
			 false, //"NSP", // 22
			 false, //"Entity ID", // 23
			 false, //"Project ID", // 24
			 false, //"PBL ID", // 25
			 false, //"Seq ID", // 26
			 false, //"Unit ID", //27
			 false, //"Part ID", // 28
			 false, //"LastSchedDate", //29
			 false, //"Phase", // 30
			 false, //"Project Name", // 31
			 false, //"OB - Company", // 32
			 false, //"OB - Bank", // 33
			 false, //"Due Date (Bank)", // 34
			 false, //"TCT No.", // 35
			 false, //"With NTC", //36
			 false,  //"CTS Notarized" //37
			 false, //"Reason for Buyback", // 37
			 false //"Remarks",//38

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

					 true, //"Tag", // 0
					 false, //"Unit", // 1
					 false, //"Client Name", // 2
					 false, //"Buyer Type", // 3
					 false, //"Sales Division", // 4
					 false, //"Contact No.", // 5
					 false, //"House Model", // 6
					 false, //"Acct. Status",// 7
					 false, //"House Status", // 8  
					 false, //"TR Date", // 9
					 false, //"OR Date", // 10
					 false, //"Default Date", // 11
					 false, //"Last Date Paid", // 12 
					 false, //"Month PD", // 13
					 false, //"Month PD", // 13
					 false, //"Payments Stage", // 14
					 false, //"Last Phone Follow up", // 15
					 false, //"Email Add.", // 16
					 false, //"PN No.", // 17 -- ADD FUNCTION RIGHT CLICK TO SEE THE PN HISTORY AS PER KIM
					 false, //"Notice of Default", // 18
					 false, //"Notice of Final Demand", // 19
					 false, //"Notice of Cancel", // 20
					 false, //"Maceda", // 21 -- BOOLEAN- CheckBox
					 false, //"NSP", // 22
					 false, //"Entity ID", // 23
					 false, //"Project ID", // 24
					 false, //"PBL ID", // 25
					 false, //"Seq ID", // 26
					 false, //"Unit ID", //27
					 false, //"Part ID", // 28
					 false, //"LastSchedDate", //29
					 false, //"Phase", // 30
					 false, //"Project Name", // 31
					 false, //"OB - Company", // 32
					 false, //"OB - Bank", // 33
					 false, //"Due Date (Bank)", // 34
					 false, //"TCT No.", // 35
					 false, //"With NTC", //36
					 false,  //"CTS Notarized" //37
					 true, //"Reason for Buyback", // 37
					 true //"Remarks",//38

			 };
		 }else{

			 COLUMN_EDITABLE = new boolean[] {

					 false, //"Tag", // 0
					 false, //"Unit", // 1
					 false, //"Client Name", // 2
					 false, //"Buyer Type", // 3
					 false, //"Sales Division", // 4
					 false, //"Contact No.", // 5
					 false, //"House Model", // 6
					 false, //"Acct. Status",// 7
					 false, //"House Status", // 8  
					 false, //"TR Date", // 9
					 false, //"OR Date", // 10
					 false, //"Default Date", // 11
					 false, //"Last Date Paid", // 12 
					 false, //"Month PD", // 13
					 false, //"Month PD", // 13
					 false, //"Payments Stage", // 14
					 false, //"Last Phone Follow up", // 15
					 false, //"Email Add.", // 16
					 false, //"PN No.", // 17 -- ADD FUNCTION RIGHT CLICK TO SEE THE PN HISTORY AS PER KIM
					 false, //"Notice of Default", // 18
					 false, //"Notice of Final Demand", // 19
					 false, //"Notice of Cancel", // 20
					 false, //"Maceda", // 21 -- BOOLEAN- CheckBox
					 false, //"NSP", // 22
					 false, //"Entity ID", // 23
					 false, //"Project ID", // 24
					 false, //"PBL ID", // 25
					 false, //"Seq ID", // 26
					 false, //"Unit ID", //27
					 false, //"Part ID", // 28
					 false, //"LastSchedDate", //29
					 false, //"Phase", // 30
					 false, //"Project Name", // 31
					 false, //"OB - Company", // 32
					 false, //"OB - Bank", // 33
					 false, //"Due Date (Bank)", // 34
					 false, //"TCT No.", // 35
					 false, //"With NTC", //36
					 false,  //"CTS Notarized" //37
					 true, //"Reason for Buyback", // 37
					 true //"Remarks",//38
			 };
		 }
	 }


	 public void clear() {
		 FncTables.clearTable(this);
	 }
 }
