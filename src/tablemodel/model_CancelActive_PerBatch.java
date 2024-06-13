/**
 * 
 */
package tablemodel;

import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

/**
 * @author Christian Paquibot
 *
 */

@SuppressWarnings({"unchecked","rawtypes"})
public class model_CancelActive_PerBatch extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
 
	private boolean editable = false;
	boolean[] COLUMN_EDITABLE;
	
	
	public model_CancelActive_PerBatch() {
		 initThis();
	}

	public model_CancelActive_PerBatch(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		 initThis();
	}

	public model_CancelActive_PerBatch(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		 initThis();
	}

	public model_CancelActive_PerBatch(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		 initThis();
	}

	public model_CancelActive_PerBatch(Vector data, Vector columnNames) {
		super(data, columnNames);
		 initThis();
	}

	public model_CancelActive_PerBatch(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		 initThis();
	}

	String[] COLUMNS = new String[] {
			"Tag", // 0
			"Unit", // 1
			"Client Name", // 2
			"Buyer Type", // 3
			"Current Status",//4
			"Sales Division", // 5
			"Contact No.", // 6
			"House Model", //7
			"Sold to Bank", //8
			"Buyback", //9
			"Moved In", //10
			"Move Out",// 11
			"Turn Over",// 12
			"TR Date",// 13
			"OR Date", //14
			"Default Date", //15
			"Last Date Paid", //16 
			"Month PD", // 17
			"Payments Stage", // 18
			"Agent Name",// 19
			"With NTC",// 20
			"Last Phone Follow up", //21
			"Entity ID",// 22
			"Project ID",// 23
			"PBL ID",// 24
			"Seq ID",// 25
			"Unit ID",// 26
			"PartID",// 27
			"Phase",// 28
			"Project Name",// 29
			"<html><font color = \"red\">*</font> &nbsp;<i>Cancellation Type</i>",// 30
			"<html><font color = \"red\">*</font> &nbsp;<i>Reason</i>",// 31
			"<html> &nbsp;<i>Remarks</i>",// 32

			
	};
	Class[] CLASS_TYPES = new Class[] {
			
			
			Boolean.class,	//	"Tag", // 0
			String.class,	//	"Unit", // 1
			Object.class,	//	"Client Name", // 2
			String.class,	//	"Buyer Type",//3
			Object.class,	//	"Current Status",//4
			String.class,	//	"Sales Division", // 5
			Object.class,	//	"Contact No.", // 6
			String.class,	//	"House Model", //7
			Timestamp.class,//	"Sold to Bank", //8
			Timestamp.class,//	"Buyback", //9
			Timestamp.class,//	"Moved In", //10
			Timestamp.class,//	"Move Out",// 11
			Timestamp.class,//	"Turn Over",// 12
			Timestamp.class,//	"TR Date",// 11
			Timestamp.class,//	"OR Date",// 12
			Timestamp.class,//	"Default Date", //13
			Timestamp.class,//	"Last Date Paid", //14 
			Integer.class,	//	"Month PD", // 15
			String.class,	//	"Payments Stage", // 16
			String.class,	//	"Agent Name",// 17
			String.class,	//	"With NTC",// 18
			Timestamp.class,//	"Last Phone Follow up", //19
			String.class,	//	"Entity ID",// 20
			String.class,	//	"Project ID",// 21
			String.class,	//	"PBL ID",// 22
			String.class,	//	"Seq ID",// 23
			String.class,	//	"Unit ID",// 24
			String.class,	//	"PartID",// 25
			String.class,	//	"Phase",// 26
			String.class,	//	"Project Name",// 27
			Object.class,	//  "<html><font color = \"red\">*</font> &nbsp;<i>Cancellation Type</i>",// 28
			String.class,	//	"<html><font color = \"red\">*</font> &nbsp;<i>Reason</i>",// 29
			Object.class,	//	"<html><font color = \"red\">*</font> &nbsp;<i>Remarks</i>",// 30
			
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
					true,//"Tag", // 0
					false, //"Unit", // 1
					false, //"Client Name", // 2
					false, //"Buyer Type", // 3
					false, //"Current Status",//4
					false, //"Sales Division", // 5
					false, //"Contact No.", // 6
					false, //"House Model", //7
					false, //"Sold to Bank", //8
					false, //"Buyback", //9
					false, //"Moved In", //10
					false, //"Move Out",// 11
					false, //"Turn Over",// 12
					false,//	"TR Date",// 11
					false,//	"OR Date",// 12
					false, //"Default Date", //13
					false, //"Last Date Paid", //14 
					false, //"Month PD", // 15
					false, //"Payments Stage", // 16
					false, //"Agent Name",// 17
					false, //"With NTC",// 18
					false, //"Last Phone Follow up", //19
					false, //"Entity ID",// 20
					false, //"Project ID",// 21
					false, //"PBL ID",// 22
					false, //"Seq ID",// 23
					false, //"Unit ID",// 24
					false, //"PartID",// 25
					false, //"Phase",// 26
					false, //"Project Name",// 27
					true,//"<html><font color = \"red\">*</font> &nbsp;<i>Cancellation Type</i>",// 28
					true,//"<html><font color = \"red\">*</font> &nbsp;<i>Reason</i>",// 29
					true//"<html><font color = \"red\">*</font> &nbsp;<i>Remarks</i>",// 30
				
			};
			
		}else{
			COLUMN_EDITABLE = new boolean[] {
					false,//"Tag", // 0
					false, //"Unit", // 1
					false, //"Client Name", // 2
					false, //"Buyer Type", // 3
					false, //"Current Status",//4
					false, //"Sales Division", // 5
					false, //"Contact No.", // 6
					false, //"House Model", //7
					false, //"Sold to Bank", //8
					false, //"Buyback", //9
					false, //"Moved In", //10
					false, //"Move Out",// 11
					false, //"Turn Over",// 12
					false,//	"TR Date",// 11
					false,//	"OR Date",// 12
					false, //"Default Date", //13
					false, //"Last Date Paid", //14 
					false, //"Month PD", // 15
					false, //"Payments Stage", // 16
					false, //"Agent Name",// 17
					false, //"With NTC",// 18
					false, //"Last Phone Follow up", //19
					false, //"Entity ID",// 20
					false, //"Project ID",// 21
					false, //"PBL ID",// 22
					false, //"Seq ID",// 23
					false, //"Unit ID",// 24
					false, //"PartID",// 25
					false, //"Phase",// 26
					false, //"Project Name",// 27
					false, //"<html><font color = \"red\">*</font> &nbsp;<i>Cancellation Type</i>",// 28
					false, //"<html><font color = \"red\">*</font> &nbsp;<i>Reason</i>",// 29
					false  //"<html><font color = \"red\">*</font> &nbsp;<i>Remarks</i>",// 30
				
			};
		}
	}
	
	
	public void clear() {
		FncTables.clearTable(this);
	}
}
