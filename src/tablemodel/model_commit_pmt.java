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
public class model_commit_pmt extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
 
	private boolean editable = false;
	
	public model_commit_pmt() {
		 initThis();
	}

	public model_commit_pmt(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		 initThis();
	}

	public model_commit_pmt(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		 initThis();
	}

	public model_commit_pmt(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		 initThis();
	}

	public model_commit_pmt(Vector data, Vector columnNames) {
		super(data, columnNames);
		 initThis();
	}

	public model_commit_pmt(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		 initThis();
	}

	String[] COLUMNS = new String[] {
			"Tag", //0
			"Committed Date", // 1
			"Committed Amount", // 2
			"Date Paid", // 3
			"Amount Paid", // 4
			"Balance" // 5
	};
	Class[] CLASS_TYPES = new Class[] {
			
			Boolean.class,//"Tag", //0
			Timestamp.class,//"Committed Date", // 1
			BigDecimal.class,//"Committed Amount", // 2
			Timestamp.class,//"Date Paid", // 3
			BigDecimal.class,//"Amount Paid", // 4
			BigDecimal.class//"Balance" // 5
			
			
			
	};
	boolean[] COLUMN_EDITABLE = new boolean[] {
			
			false,//"Tag", //0
			false,//"Committed Date", // 1
			false,//"Committed Amount", // 2
			false,//"Date Paid", // 3
			false,//"Amount Paid", // 4
			false,//"Balance" // 5
	
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

					true,//"Tag", //0
					false,//"Committed Date", // 1
					false,//"Committed Amount", // 2
					true,//"Date Paid", // 3
					true,//"Amount Paid", // 4
					false,//"Balance" // 5
			};
			
		}else{
			COLUMN_EDITABLE = new boolean[] {
					
					false,//"Tag", //0
					false,//"Committed Date", // 1
					false,//"Committed Amount", // 2
					false,//"Date Paid", // 3
					false,//"Amount Paid", // 4
					false,//"Balance" // 5
			
			};
		}
	}
	
	public void clear() {
		FncTables.clearTable(this);
	}
}
