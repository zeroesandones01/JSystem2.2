package tablemodel;

import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelPrintAssetSticker extends DefaultTableModel  {

	private static final long serialVersionUID = 1L;
	
	private boolean editable = false;
	//boolean[] COLUMN_EDITABLE;

	public modelPrintAssetSticker() {
		initThis();
	}

	public modelPrintAssetSticker(boolean editable) {
		initThis();
	}

	public modelPrintAssetSticker(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelPrintAssetSticker(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelPrintAssetSticker(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelPrintAssetSticker(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelPrintAssetSticker(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}
	
	String[] COLUMNS = new String[] {
			"Selected",
			"Asset No.", 
			"Asset Code",
			"Asset Name",
			"Date Acquired",
			"Custodian ID",
			"Custodian",
			"Reference No.",
			"status",
			"Company Name",
			"Company Logo",
	};
	
	Class[] CLASS_TYPES = new Class[] {
			Boolean.class,//checkbox
			String.class, //Asset No.
			String.class, //Asset Code
			Object.class, //Asset Name
			Timestamp.class, //Date Acquired
			String.class, //Custodian ID
			Object.class, //Custodian
			Object.class, //Reference No.
			String.class, //status
			Object.class, //Co_name
			Object.class, //Co_logo
	};
	
	private void initThis() {
		setColumnIdentifiers(COLUMNS);
	}
	
	Boolean[] COLUMNS_EDITABLE = new Boolean[] {
			 true, //0
			false, //1
			false, //2
			false, //3
			false, //4
			false, //5
			false, //6
			false, //7
			false, //8
			false, //9
			false, //10
	};
	
	
	
	public Class getColumnClass(int columnIndex) {
		return CLASS_TYPES[columnIndex];
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return COLUMNS_EDITABLE[columnIndex];
	}
	
	public void clear() {
		FncTables.clearTable(this);
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
		if(editable){
			COLUMNS_EDITABLE = new Boolean[]{
					true, //0
					true, //1
					true, //2
					true, //3
					true, //4
					true, //5
					true, //6
					true, //7
					true, //8
					true, //9
					true, //10
			};
		}else{
			COLUMNS_EDITABLE = new Boolean[]{
					false, //0
					false, //1
					false, //2
					false, //3
					false, //4
					false, //5
					false, //6
					false, //7
					false, //8
					false, //9
					false, //10
			};
	}
		
	}
}
