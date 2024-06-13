package tablemodel;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelUtilities_SubStage extends DefaultTableModel{
	
	private static final long serialVersionUID = 1L;
	
	static String [] columns = new String []{
			"SubStage No.", //0
			"Sub-Stage Description", //1
			"Percent Accomp.", //2
			"Status", //3
			"Created by", //4
			"Created Date", //5
			"Set No.", //6
			"Stage No."  //7
	};

	public modelUtilities_SubStage() {
		super(new Object[][]{}, columns);
	}
	
	public Class [] type = new Class[] {
			Integer.class,     //0
			String.class,      //1
			BigDecimal.class,  //2
			String.class,      //3
			String.class,      //4
			Timestamp.class,   //5
			Integer.class,     //6
			Integer.class      //7
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
