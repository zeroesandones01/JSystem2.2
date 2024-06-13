package tablemodel;

import java.math.BigDecimal;

import javax.swing.table.DefaultTableModel;

public class modelLiquidationSOA extends DefaultTableModel {

	private static final long serialVersionUID = 1L;

	static String[] columns = new String[] { 
		//"Entry #", //0
		"Emp. Code", //1
		"Emp. Name", //2
		"No. of Unliquidated CA", //3
		"Total Unliquidated CA"   //4
		}; 

	public modelLiquidationSOA() {
		super(new Object[][]{}, columns);
	}

	public Class[] types = new Class[] {
			
			String.class, 		//Emp. Code
			Object.class, 		//Emp. Name
			Integer.class, 		//No. of Unliquidated CA
			BigDecimal.class 	//Total Unliquidated CA
	};

	@Override
	public Class getColumnClass(int columnIndex) {
		return types[columnIndex];
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

}
