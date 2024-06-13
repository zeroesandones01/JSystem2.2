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
public class model_List_Vehicles extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private boolean editable = false;


	public model_List_Vehicles() {
		initThis();
	}

	public model_List_Vehicles(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public model_List_Vehicles(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public model_List_Vehicles(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public model_List_Vehicles(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public model_List_Vehicles(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}

	String[] COLUMNS = new String[] {
			"Tag", // 0
			"Plate No.", // 1
			"Make/Brand" ,// 2
			"Status", // 3
			//"Rec_ID" // 4


	};
	Class[] CLASS_TYPES = new Class[] {


			Boolean.class,	//	"Tag", // 0
			String.class,	//	"Plate No.", // 1
			String.class,	//	"Make/Brand", // 2
			Object.class,	//	"Status", // 3
			//Integer.class	//	"Rec_id", // 4


	};
	
	Boolean[] COLUMN_EDITABLE = new Boolean[]{
			true, // Tag
			false, // Plate No.
			false, // Make/Brand
			false, // Status
			//false,// "Rec_ID" // 4
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
					true, // Tag
					true, // Plate No.
					true, // Make/Brand
					true, // Status
					//false,// "Rec_ID" // 4
			};
		}else{
			COLUMN_EDITABLE = new Boolean[]{
					false, // Tag
					false, // Plate No.
					false, // Make/Brand
					false, // Status
					//false,// "Rec_ID" // 4
			};
		}
	}


	public void clear() {
		FncTables.clearTable(this);
	}
}
