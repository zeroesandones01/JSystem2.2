package tablemodel;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class model_AccessMain_Position_a extends DefaultTableModel {
	
	private static final long serialVersionUID = 1L;
	
	static String [] columns = new String []{
			"Select.",   	  //0
			"Position No.",   //1
			"Position Desc.", //2
	};

	public model_AccessMain_Position_a() {
		super(new Object[][]{}, columns);
	}
	
	public Class [] type = new Class[] {
			Boolean.class,     //0
			Integer.class,     //1
			String.class,      //2
	};
	
	Boolean[] COLUMN_EDITABLE = new Boolean[] {
			true,
			false, //module no.
			false, //module name
			
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
