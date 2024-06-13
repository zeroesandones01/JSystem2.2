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
public class model_PN_Commit extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
 
	private boolean editable = false;
	
	public model_PN_Commit() {
		 initThis();
	}

	public model_PN_Commit(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		 initThis();
	}

	public model_PN_Commit(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		 initThis();
	}

	public model_PN_Commit(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		 initThis();
	}

	public model_PN_Commit(Vector data, Vector columnNames) {
		super(data, columnNames);
		 initThis();
	}

	public model_PN_Commit(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		 initThis();
	}

	String[] COLUMNS = new String[] {
			"Commit Date", // 0
			"Amount", // 1
			"Months to Update", // 2
			"Balance" // 3
			
	};
	Class[] CLASS_TYPES = new Class[] {
			
			Timestamp.class,//"Commit Date", // 0
			BigDecimal.class,//"Amount", // 1
			String.class,//"Months to Update", // 2
			BigDecimal.class//"Balance", // 3
			
	};
	boolean[] COLUMN_EDITABLE = new boolean[] {
			
			false,//"Commit Date", // 0
			false,//"Amount", // 1
			false,//"Months to Update", // 2
			false//"Balance", // 3
	
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

					true,//"Commit Date", // 0
					true,//"Amount", // 1
					true,//"Months to Update", // 2
					true//"Actual Paid", // 3
			};
			
		}else{
			COLUMN_EDITABLE = new boolean[] {
					
					false,//"Commit Date", // 0
					false,//"Amount", // 1
					false,//"Months to Update", // 2
					false//"Actual Paid", // 3
			
			};
		}
	}
	
	public void clear() {
		FncTables.clearTable(this);
	}
}
