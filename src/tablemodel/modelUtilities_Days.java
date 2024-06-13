package tablemodel;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelUtilities_Days extends DefaultTableModel{
	
	private static final long serialVersionUID = 1L;
	
	static String [] columns = new String []{
			"Day No.", //0
			"Percent Accomp.", //1
			"Status", //2
			"Created by", //3
			"Created Date" //4
	};

	public modelUtilities_Days() {
		super(new Object[][]{}, columns);
	}
	
	public Class [] type = new Class[] {
			Integer.class,     //0
			BigDecimal.class,  //1
			String.class,      //2
			String.class,      //3
			Timestamp.class    //4
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
