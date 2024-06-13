package tablemodel;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class model_AddEditPosition_EmpList extends DefaultTableModel{

	private static final long serialVersionUID = 1L;
	
	public static String [] columns = new String[]{
			"Employee ID",				//0
			"Entity ID",				//1
			"Entity Name",				//2
			"Employee Status",			//3
			"Employee Position",		//4
			"Employee Rank",			//5
			"Div. Code",				//6
			"Dept. Code"				//7
			
	};
	
	public model_AddEditPosition_EmpList(){
		super(new Object [][]{}, columns);
	}
	
	public Class [] type = new Class[]{
			String.class,				//0
			String.class,				//1
			String.class,				//2
			String.class,				//3
			String.class,				//4
			String.class,				//5
			String.class,				//6
			String.class				//7
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
