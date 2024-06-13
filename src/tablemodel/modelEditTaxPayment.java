package tablemodel;

import java.math.BigDecimal;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelEditTaxPayment extends DefaultTableModel{

	private static final long serialVersionUID = 1L;
	
	public static String [] columns = new String[]{
			"Entity ID",				//0
			"Name",						//1
			"PBL",						//2
			"Unit",						//3
			"Description",				//4
			"Amount",					//5
			"OR No",					//6
			"Year",						//7
			"Projcode",					//8
			"ID"						//9
			
	};
	
	public modelEditTaxPayment(){
		super(new Object [][]{}, columns);
	}
	
	public Class [] type = new Class[]{
			String.class,				//0
			String.class,				//1
			String.class,				//2
			String.class,				//3
			String.class,				//4
			BigDecimal.class,			//5
			String.class,				//6
			String.class,				//7
			String.class,				//8
			Integer.class				//9
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
