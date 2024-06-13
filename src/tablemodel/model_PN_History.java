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
public class model_PN_History extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
 
	private boolean editable = false;
	
	public model_PN_History() {
		 initThis();
	}

	public model_PN_History(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		 initThis();
	}

	public model_PN_History(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		 initThis();
	}

	public model_PN_History(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		 initThis();
	}

	public model_PN_History(Vector data, Vector columnNames) {
		super(data, columnNames);
		 initThis();
	}

	public model_PN_History(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		 initThis();
	}

	String[] COLUMNS = new String[] {
			"PN No.", // 0
			"Payment Date", // 1
			"Signed By", // 2
			"Relation W/ Client", // 3
			"Status", // 4
			"Remarks", // 5
			"Approved By"
			
	};
	Class[] CLASS_TYPES = new Class[] {
			
			String.class,//"PN No.", // 0
			Timestamp.class,//"Payment Date", // 1
			String.class,//"Signed By", // 2
			String.class,//"Relation W/ Client", // 3
			String.class,//"Status", // 4
			String.class,//"Remarks" // 5
			String.class//"Remarks" // 5
			
	};
	boolean[] COLUMN_EDITABLE = new boolean[] {
			

			false,//"PN No.", // 0
			false,//"Payment Date", // 1
			false,//"Signed By", // 2
			false,//"Relation W/ Client", // 3
			false,//"Status", // 4
			false,//"Remarks" // 5
			false//"Approved By" // 5
			
	
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


					true,//"PN No.", // 0
					false,//"Payment Date", // 1
					false,//"Signed By", // 2
					false,//"Relation W/ Client", // 3
					false,//"Status", // 4
					false,//"Remarks" // 5
					false//"Remarks" // 5
			};
			
		}else{
			COLUMN_EDITABLE = new boolean[] {
					

					false,//"PN No.", // 0
					false,//"Payment Date", // 1
					false,//"Signed By", // 2
					false,//"Relation W/ Client", // 3
					false,//"Remarks" // 5
					false//"Remarks" // 5
			};
		}
	}
	
	public void clear() {
		FncTables.clearTable(this);
	}
}
