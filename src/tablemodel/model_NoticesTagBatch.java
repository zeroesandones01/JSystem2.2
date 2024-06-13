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
public class model_NoticesTagBatch extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
 
	private boolean editable = false;
	boolean[] COLUMN_EDITABLE;
	
	
	public model_NoticesTagBatch() {
		 initThis();
	}

	public model_NoticesTagBatch(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		 initThis();
	}

	public model_NoticesTagBatch(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		 initThis();
	}

	public model_NoticesTagBatch(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		 initThis();
	}

	public model_NoticesTagBatch(Vector data, Vector columnNames) {
		super(data, columnNames);
		 initThis();
	}

	public model_NoticesTagBatch(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		 initThis();
	}

	String[] COLUMNS = new String[] {
			"Tag", // 0
			"Batch No.", // 1
			"Status", // 1
			"Date Prepared"

			
	};
	Class[] CLASS_TYPES = new Class[] {
			
			
			Boolean.class,	//	"Tag", // 0
			String.class,	//	"Batch No.", // 1
			String.class,	//	"Batch No.", // 1
			Timestamp.class
			
			
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
					true, //	"Tag", // 0
					false,//	"Batch No.", // 1
					false,//	"Batch No.", // 1
					false//	"Batch No.", // 1
					
			};
			
		}else{
			COLUMN_EDITABLE = new boolean[] {
					false,//	"Tag", // 0
					false,//	"Batch No.", // 1
					false,
					false//	"Batch No.", // 1//	"Batch No.", // 1
					
				
			};
		}
	}
	
	
	public void clear() {
		FncTables.clearTable(this);
	}
}
