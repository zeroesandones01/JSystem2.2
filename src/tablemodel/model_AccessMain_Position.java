package tablemodel;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class model_AccessMain_Position extends DefaultTableModel {
	
	private static final long serialVersionUID = 1L;
	
	static String [] columns = new String []{
			"Position No.",   //0
			"Position Desc.", //1
	};

	public model_AccessMain_Position() {
		super(new Object[][]{}, columns);
	}
	
	public Class [] type = new Class[] {
			Integer.class,     //0
			String.class,      //1
	};
	
	@Override
	public Class getColumnClass(int columnIndex) {
		return type[columnIndex];
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}
	
	public void clear() {
		FncTables.clearTable(this);
	}
}
