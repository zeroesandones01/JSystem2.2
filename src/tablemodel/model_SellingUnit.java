/**
 * 
 */
package tablemodel;

import java.math.BigDecimal;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

/**
 * @author Christian Paquibot
 *
 */

@SuppressWarnings({"unchecked","rawtypes"})
public class model_SellingUnit extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
 
	private boolean editable = false;
	
	public model_SellingUnit() {
		 initThis();
	}

	public model_SellingUnit(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		 initThis();
	}

	public model_SellingUnit(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		 initThis();
	}

	public model_SellingUnit(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		 initThis();
	}

	public model_SellingUnit(Vector data, Vector columnNames) {
		super(data, columnNames);
		 initThis();
	}

	public model_SellingUnit(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		 initThis();
	}

	String[] COLUMNS = new String[] {
			"Dept. Name",
			"Selling Unit", // 0
			"Amount", // 2
			"Entity_ID",
			"Div.ID",
			"Dept.ID"
			
	};
	
	Class[] CLASS_TYPES = new Class[] {

			String.class,//"Commit Date", // 0
			String.class,//"Commit Date", // 0
			BigDecimal.class,//"Months to Update", // 2
			String.class,//"Commit Date", // 0
			String.class,//"Commit Date", // 0
			String.class//"Commit Date", // 0
			
	};
	
	boolean[] COLUMN_EDITABLE = new boolean[] {
			false,//"Amount", // 1
			false,//"Amount", // 1
			false,//"Amount", // 1
			false,//"Amount", // 1
			false,//"Amount", // 1
			false//"Months to Update", // 2
	
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
					false,//"Amount", // 1
					false,//"Amount", // 1
					true,//"Months to Update", // 2
					false,//"Amount", // 1
					false,//"Amount", // 1
					false//"Amount", // 1
			};
			
		}else{
			COLUMN_EDITABLE = new boolean[] {
					false,//"Amount", // 1
					false,//"Amount", // 1
					false,//"Months to Update", // 2
					false,//"Months to Update", // 2
					false,//"Months to Update", // 2
					false//"Months to Update", // 2
			
			};
		}
	}
	
	public void clear() {
		FncTables.clearTable(this);
	}
}
