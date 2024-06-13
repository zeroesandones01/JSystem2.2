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
public class model_SalesGroups extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
 
	private boolean editable = false;
	
	public model_SalesGroups() {
		 initThis();
	}

	public model_SalesGroups(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		 initThis();
	}

	public model_SalesGroups(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		 initThis();
	}

	public model_SalesGroups(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		 initThis();
	}

	public model_SalesGroups(Vector data, Vector columnNames) {
		super(data, columnNames);
		 initThis();
	}

	public model_SalesGroups(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		 initThis();
	}

	String[] COLUMNS = new String[] {
			"Group ID", // 0
			"Group Name", // 1
			"Amount", // 2
			"Div. ID" // 2
			
	};
	
	Class[] CLASS_TYPES = new Class[] {
			
			String.class,//"Group ID", // 0
			String.class,//"Group Name", // 1
			BigDecimal.class,//"Amount", // 2
			String.class,//"Group Name", // 1
			
	};
	
	boolean[] COLUMN_EDITABLE = new boolean[] {
			
			false,//"Group ID", // 0
			false,//"Group Name", // 1
			false,//"Group Name", // 1
			false//"Amount", // 2
	
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

					false,//"Group ID", // 0
					false,//"Group Name", // 1
					true,//"Amount", // 2
					false//"Group Name", // 1
			};
			
		}else{
			COLUMN_EDITABLE = new boolean[] {
					
					false,//"Group ID", // 0
					false,//"Group Name", // 1
					false,//"Amount", // 2
					false//"Group Name", // 1
			
			};
		}
	}
	
	public void clear() {
		FncTables.clearTable(this);
	}
}
