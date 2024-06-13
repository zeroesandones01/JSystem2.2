/**
 * 
 */
package tablemodel;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

/**
 * @author Christian Paquibot
 *
 */

@SuppressWarnings({"unchecked","rawtypes"})
public class model_NoticesTaggedBatch extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    private ArrayList<model_NoticesTaggedBatch> data;
	private boolean editable = false;
	boolean[] COLUMN_EDITABLE;
	
	
	public model_NoticesTaggedBatch() {
		 initThis();
	}

	public model_NoticesTaggedBatch(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		 initThis();
	}

	public model_NoticesTaggedBatch(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		 initThis();
	}

	public model_NoticesTaggedBatch(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		 initThis();
	}

	public model_NoticesTaggedBatch(Vector data, Vector columnNames) {
		super(data, columnNames);
		 initThis();
	}

	public model_NoticesTaggedBatch(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		 initThis();
	}

	String[] COLUMNS = new String[] {
			"Tag", // 0
			"Batch No.", // 1
			"Tagged By", // 2
			"Status", // 3
			"Tagged Date" // 4


			
	};
	Class[] CLASS_TYPES = new Class[] {
			
			
			Boolean.class,	//	"Tag", // 0
			String.class,	//	"Batch No.", // 1
			String.class,	//	"Tagged By", // 2
			String.class,	//	"Status", // 3
			Timestamp.class,	//	"Tagged Date", // 4
			
			
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
					false,//	"Tagged By", // 2
					false,//	"Status", // 3
					false,//	"Tagged Date", // 4
					
			};
			
		}else{
			COLUMN_EDITABLE = new boolean[] {
					false,//	"Tag", // 0
					false,//	"Batch No.", // 1
					false,//	"Tagged By", // 2
					false,//	"Status", // 3
					false,//	"Tagged Date", // 4
					
				
			};
		}
	}
	
	
	public void clear() {
		FncTables.clearTable(this);
	}
	
	
}
