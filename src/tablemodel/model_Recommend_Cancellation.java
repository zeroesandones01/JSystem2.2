/**
 * 
 */
package tablemodel;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

/**
 * @author Christian Paquibot
 *
 */

@SuppressWarnings({"unchecked","rawtypes"})
public class model_Recommend_Cancellation extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
 
	private boolean editable = false;
	boolean[] COLUMN_EDITABLE;
	
	
	public model_Recommend_Cancellation() {
		 initThis();
	}

	public model_Recommend_Cancellation(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		 initThis();
	}

	public model_Recommend_Cancellation(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		 initThis();
	}

	public model_Recommend_Cancellation(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		 initThis();
	}

	public model_Recommend_Cancellation(Vector data, Vector columnNames) {
		super(data, columnNames);
		 initThis();
	}

	public model_Recommend_Cancellation(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		 initThis();
	}

	String[] COLUMNS = new String[] {
			"Tag",//0
			"Unit", // 1
			"Client Name", // 2
			"Buyer Type", // 3
			"Current Status",//4
			"Month PD", // 5
			"Payments Stage", // 6
			"Entity ID",// 7
			"Project ID",// 8
			"PBL ID",// 9
			"Seq ID",// 10
			"PartID",// 11
			"<html><font color = \"red\">*</font> &nbsp;<i>Cancellation Type</i>",// 12
			"<html><font color = \"red\">*</font> &nbsp;<i>Reason</i>",// 13
			"<html>&nbsp;<i>Remarks</i>",// 14

			
	};
	Class[] CLASS_TYPES = new Class[] {
			
			Boolean.class,
			String.class,	//	"Unit", // 1
			Object.class,	//	"Client Name", // 2
			String.class,	//	"Buyer Type",//3
			Object.class,	//	"Current Status",//4
			Integer.class,	//	"Month PD", // 15
			String.class,	//	"Payments Stage", // 16
			String.class,	//	"Entity ID",// 20
			String.class,	//	"Project ID",// 21
			String.class,	//	"PBL ID",// 22
			Integer.class,	//	"Seq ID",// 23
			String.class,	//	"PartID",// 22
			Object.class,	//  "<html><font color = \"red\">*</font> &nbsp;<i>Cancellation Type</i>",// 28
			String.class,	//	"<html><font color = \"red\">*</font> &nbsp;<i>Reason</i>",// 29
			Object.class	//	"<html><font color = \"red\">*</font> &nbsp;<i>Remarks</i>",// 30
			
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
					true,
					false,//"Unit", // 0
					false,//"Client Name", // 1
					false,//"Buyer Type", // 2
					false,//"Payments Stage", // 4
					false,//"Month PD", // 3
					false,//"Payments Stage", // 4
					false,//"Entity ID",// 5
					false,//"Project ID",// 6
					false,//"PBL ID",// 7
					false,//"Seq ID",// 8
					false,//"Seq ID",// 8
					true,//"<html><font color = \"red\">*</font> &nbsp;<i>Cancellation Type</i>",// 13
					true,//"<html><font color = \"red\">*</font> &nbsp;<i>Reason</i>",// 14
					true //"<html><font color = \"red\">*</font> &nbsp;<i>Remarks</i>",// 15 
				
			};
			
		}else{
			COLUMN_EDITABLE = new boolean[] {
					true,
					false,//"Unit", // 0
					false,//"Client Name", // 1
					false,//"Buyer Type", // 2
					false,//"Payments Stage", // 4
					false,//"Month PD", // 3
					false,//"Payments Stage", // 4
					false,//"Entity ID",// 5
					false,//"Project ID",// 6
					false,//"PBL ID",// 7
					false,//"Seq ID",// 8
					false,//"Seq ID",// 8
					true,//"<html><font color = \"red\">*</font> &nbsp;<i>Cancellation Type</i>",// 13
					true,//"<html><font color = \"red\">*</font> &nbsp;<i>Reason</i>",// 14
					true //"<html><font color = \"red\">*</font> &nbsp;<i>Remarks</i>",// 15 
				
			};
		}
	}
	
	
	public void clear() {
		FncTables.clearTable(this);
	}
}
