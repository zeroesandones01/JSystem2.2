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
 * @author PC-112l
 *
 */
public class model_Cancellation_Hold extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
 
	private boolean editable = false;
	boolean[] COLUMN_EDITABLE;
	
	
	public model_Cancellation_Hold() {
		 initThis();
	}

	public model_Cancellation_Hold(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		 initThis();
	}

	public model_Cancellation_Hold(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		 initThis();
	}

	public model_Cancellation_Hold(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		 initThis();
	}

	public model_Cancellation_Hold(Vector data, Vector columnNames) {
		super(data, columnNames);
		 initThis();
	}

	public model_Cancellation_Hold(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		 initThis();
	}
	
	
	String[] COLUMNS = new String[] {
			"Tag", // 0
			"Unit", // 1
			"Client Name", // 2
			"Contact No.", // 3
			"Model", //4
			"NSP", // 5
			"Amount Paid", //6
			"Actual Date", //7
			"Valid Until", //8
			"<html><font color = \"red\">*</font> &nbsp;<i>Reason</i>",// 9
			"<html><font color = \"red\">*</font> &nbsp;<i>Remarks</i>",// 10
			"Entity ID",// 11
			"Project ID",// 12
			"PBL ID",// 13
			"Seq ID",// 14
			"Unit ID",// 15
			"TRA Header ID",// 16
			
			
	};
	Class[] CLASS_TYPES = new Class[] {
			
			
			Boolean.class,//"Tag", // 0
			String.class,//"Unit", // 1
			Object.class,//"Client Name", // 2
			String.class,//"Contact No.", // 3
			String.class,//"Model", //4
			BigDecimal.class,//"NSP", //5
			BigDecimal.class,//"Amount Paid", //6
			Timestamp.class,//"Actual Date", //7
			Timestamp.class,//"Valid Until", //8
			Object.class,//"<html><font color = \"red\">*</font> &nbsp;<i>Reason</i>",// 9
			Object.class,//"<html><font color = \"red\">*</font> &nbsp;<i>Remarks</i>",// 10
			String.class,//"Entity ID",// 11
			String.class,//"Project ID",// 12
			String.class,//"PBL ID",// 13
			String.class,//"Seq ID",// 14
			String.class,//"Unit ID",// 15
			String.class,//"TRA Header ID",// 16
			
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
					false,//"Unit", // 1
					false,//"Client Name", // 2
					false,//"Contact No.", // 3
					false,//"Model", //4
					false,//"NSP", // 5
					false,//"Amount Paid", //6
					false,//"Actual Date", //7
					false,//"Valid Until", //8
					true,//"<html><font color = \"red\">*</font> &nbsp;<i>Reason</i>",// 9
					true,//"<html><font color = \"red\">*</font> &nbsp;<i>Remarks</i>",// 10
					false,//"Entity ID",// 11
					false,//"Project ID",// 12
					false,//"PBL ID",// 13
					false,//"Seq ID",// 14
					false,//"Unit ID",// 15
					false,//"TRA Header ID",// 16
			};
			
		}else{
			COLUMN_EDITABLE = new boolean[] {
					true,//"Tag", // 0
					false,//"Unit", // 1
					false,//"Client Name", // 2
					false,//"Contact No.", // 3
					false,//"Model", //4
					false,//"NSP", // 5
					false,//"Amount Paid", //6
					false,//"Actual Date", //7
					false,//"Valid Until", //8
					false,//"<html><font color = \"red\">*</font> &nbsp;<i>Reason</i>",// 9
					false,//"<html><font color = \"red\">*</font> &nbsp;<i>Remarks</i>",// 10
					false,//"Entity ID",// 11
					false,//"Project ID",// 12
					false,//"PBL ID",// 13
					false,//"Seq ID",// 14
					false,//"Unit ID",// 15
					false,//"TRA Header ID",// 16
			
			};
		}
	}
	
	
	public void clear() {
		FncTables.clearTable(this);
	}
}
