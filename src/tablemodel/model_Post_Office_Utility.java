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
public class model_Post_Office_Utility extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
 
	private boolean editable = false;
	boolean[] COLUMN_EDITABLE;
	
	
	public model_Post_Office_Utility() {
		 initThis();
	}

	public model_Post_Office_Utility(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		 initThis();
	}

	public model_Post_Office_Utility(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		 initThis();
	}

	public model_Post_Office_Utility(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		 initThis();
	}

	public model_Post_Office_Utility(Vector data, Vector columnNames) {
		super(data, columnNames);
		 initThis();
	}

	public model_Post_Office_Utility(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		 initThis();
	}

	String[] COLUMNS = new String[] {
			"Tag", // 0
			//"Batch No.", // 1
			"Client Name", // 2
			"Project Alias", // 3
			"Ph-Blk-Lt", // 4
			"EntityID", // 5
			"PBLID", // 6
			"SeqNo", // 7

			
	};
	Class[] CLASS_TYPES = new Class[] {
			
			
			Boolean.class,	//	"Tag", // 0
			//String.class,	//	"Batch No.", // 1
			String.class,	//	"Client Name", // 2
			String.class,	//	"Project Alias", // 3
			String.class,	//	"Ph-Blk-Lt", // 4
			String.class,	//	"EntityID", // 5
			String.class,	//	"PBLID", // 6
			Integer.class,	//	"SeqNo", // 7
			
			
			
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
					true,//	"Tag", // 0
					//false,//	"Batch No.", // 1
					false,//	"Client Name", // 2
					false,//	"Project Alias", // 3
					false,//	"Ph-Blk-Lt", // 4
					false,//	"EntityID", // 5
					false,//	"PBLID", // 6
					false //	"SeqNo", // 7

					
			};
			
		}else{
			COLUMN_EDITABLE = new boolean[] {
					false,//	"Tag", // 0
					//false,//	"Batch No.", // 1
					false,//	"Client Name", // 2
					false,//	"Project Alias", // 3
					false,//	"Ph-Blk-Lt", // 4
					false,//	"EntityID", // 5
					false,//	"PBLID", // 6
					false //	"SeqNo", // 7

				
			};
		}
	}
	
	
	public void clear() {
		FncTables.clearTable(this);
	}
}
