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
 */

@SuppressWarnings({"unchecked","rawtypes"})
public class model_Cancellation_Status extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
 
	private boolean editable = false;
	boolean[] COLUMN_EDITABLE;
	
	
	public model_Cancellation_Status() {
		 initThis();
	}

	public model_Cancellation_Status(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		 initThis();
	}

	public model_Cancellation_Status(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		 initThis();
	}

	public model_Cancellation_Status(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		 initThis();
	}

	public model_Cancellation_Status(Vector data, Vector columnNames) {
		super(data, columnNames);
		 initThis();
	}

	public model_Cancellation_Status(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		 initThis();
	}

	String[] COLUMNS = new String[] {
			"Tag", // 0
			"Unit", // 1
			"Client Name", // 2
			"<html><div align=center>Date<br>Cancelled</div></html>", // 3
			"Current Status", // 4
			"<html><font color=red>*</font><i> New Status</i></html>", //5
			"<html><font color=red>*</font><i> Status Date</i></html>", //6
			"<html><font color=red>*</font><i> Remarks</i></html>", //7
			"Status ID",// 8
			"Entity ID",// 9
			"Project ID",// 10
			"PBL ID",// 11
			"Seq ID",// 12
			"Unit ID",// 13
			"Cancel ID"// 14
			
	};
	Class[] CLASS_TYPES = new Class[] {
			
			
			Boolean.class,		//"Tag", // 0
			String.class,		//"Unit", // 1
			Object.class,		//"Client Name", // 2
			Timestamp.class,		//"<html><div align=center>Date<br>Cancelled</div></html>", // 3
			String.class,	//"Current Status", //5
			String.class,	//"<html><font color=red>*</font><i> New Status</i></html>", //6
			Timestamp.class,	//"<html><font color=red>*</font><i> Status Date</i></html>", //7
			Object.class,	//"<html><font color=red>*</font><i> Remarks</i></html>", //8
			String.class,		//"Status ID",// 9
			String.class,		//"Entity ID",// 11
			String.class,		//"Project ID",// 12
			String.class,		//"PBL ID",// 13
			String.class,		//"Seq ID",// 14
			String.class,		//"Unit ID",// 15
			String.class		//"Cancel ID"// 16
			
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
					true,	//"Tag", // 0
					false,	//"Unit", // 1
					false,	//"Client Name", // 2
					false,	//"<html><div align=center>Date<br>Cancelled</div></html>", // 3
					false,	//"Current Status", // 5
					true,	//"<html><font color=red>*</font><i> New Status</i></html>", //6
					true,	//"<html><font color=red>*</font><i> Status Date</i></html>", //7
					true,	//"<html><font color=red>*</font><i> Remarks</i></html>", //8
					false,	//"Status ID",// 9
					false,	//"Entity ID",// 11
					false,	//"Project ID",// 12
					false,	//"PBL ID",// 13
					false,	//"Seq ID",// 14
					false,	//"Unit ID",// 15
					false	//"Cancel ID"// 16
			};
			
		}else{
			COLUMN_EDITABLE = new boolean[] {
					true,	//"Tag", // 0
					false,	//"Unit", // 1
					false,	//"Client Name", // 2
					false,	//"<html><div align=center>Date<br>Cancelled</div></html>", // 3
					false,	//"Current Status", // 5
					false,	//"<html><font color=red>*</font><i> New Status</i></html>", //6
					false,	//"<html><font color=red>*</font><i> Status Date</i></html>", //7
					false,	//"<html><font color=red>*</font><i> Remarks</i></html>", //8
					false,	//"Status ID",// 9
					false,	//"Entity ID",// 11
					false,	//"Project ID",// 12
					false,	//"PBL ID",// 13
					false,	//"Seq ID",// 14
					false,	//"Unit ID",// 15
					false	//"Cancel ID"// 16
			
			};
		}
	}
	
	
	public void clear() {
		FncTables.clearTable(this);
	}
}
