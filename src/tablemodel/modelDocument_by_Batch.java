package tablemodel;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelDocument_by_Batch extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	static String [] columns = new String []{
			"Tag", 		//0
			"Doc No.", 	//1
			"Project", 	//2
			"Ph", 		//3
			"Blk", 		//4
			"Lt", 		//5
			"PBL", 		//6
	};

	public modelDocument_by_Batch() {
		super(new Object[][]{}, columns);
	}
	
	public Class [] type = new Class[] {
			Boolean.class,		//0
			String.class,		//1
			String.class,		//2
			String.class,		//3
			String.class,		//4
			String.class,		//5
			String.class,		//6
	};
	
	Boolean[] COLUMN_EDITABLE = new Boolean[] {
			true,  //0
			false, //1
			false, //2
			false, //3
			false, //4
			false, //5
			false, //6
	};
	
	@Override
	public Class getColumnClass(int columnIndex) {
		return type[columnIndex];
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return COLUMN_EDITABLE[columnIndex];
	}
	
	public void clear() {
		FncTables.clearTable(this);
	}

}
