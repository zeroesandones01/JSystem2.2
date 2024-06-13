package tablemodel;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class model_AddEditPosition_PrivilegeList extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static String [] columns = new String[]{
			"Select",					//0
			"Mod. No.",					//1
			"Module Name",				//2
			"Parent",					//3
			"Module"					//4
			
	};
	
	public model_AddEditPosition_PrivilegeList(){
		super(new Object [][]{}, columns);
	}
	
	public Class [] type = new Class[]{
			Boolean.class,				//0
			Integer.class,				//1
			String.class,				//2
			String[].class,				//3
			String.class				//4
			
	};
	
	public void clear(){
		FncTables.clearTable(this);
	}
	
	@Override
	public Class getColumnClass(int columnIndex) {
		return type[columnIndex];
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

}
