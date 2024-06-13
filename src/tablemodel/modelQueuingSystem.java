package tablemodel;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelQueuingSystem extends DefaultTableModel {
	
	private static final long serialVersionUID = 1L;
	
	static String [] columns = new String []{
			"Queue No.",		//0
	};

	public modelQueuingSystem() {
		super(new Object[][]{}, columns);
	}
	
	public Class [] type = new Class[] {
			String.class,		//0
	};
	
	Boolean[] COLUMN_EDITABLE = new Boolean[] {
			false,				//0
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
