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
public class model_Assigned_Meeting_Place extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private boolean editable = false;


	public model_Assigned_Meeting_Place() {
		initThis();
	}

	public model_Assigned_Meeting_Place(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public model_Assigned_Meeting_Place(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public model_Assigned_Meeting_Place(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public model_Assigned_Meeting_Place(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public model_Assigned_Meeting_Place(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}

	String[] COLUMNS = new String[] {
			"Tag", // 0
			"Meeting Place", // 1
			"Priority" ,// 2
			"Code" // 3


	};
	Class[] CLASS_TYPES = new Class[] {


			Boolean.class,	//	"Tag", // 0
			String.class,	//	"Meeting Place", // 1
			Integer.class,	//	"Priority", // 2
			String.class,	//	"Code", // 3


	};
	
	Boolean[] COLUMN_EDITABLE = new Boolean[]{
			true, // Tag
			false, // Meeting Place
			true, // Priority
			false, // Code
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
			COLUMN_EDITABLE = new Boolean[]{
					true,//	"Tag", // 0
					false,//	"Meeting Place", // 1
					true,//	"Priority", // 2
					false, // Code 3
			};
		}else{
			COLUMN_EDITABLE = new Boolean[]{
					false,//	"Tag", // 0
					false,//	"Meeting Place", // 1
					false,//	"Priority", // 2
					false, // Code // 3
			};
		}
	}


	public void clear() {
		FncTables.clearTable(this);
	}
}
