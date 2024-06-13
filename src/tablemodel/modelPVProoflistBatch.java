package tablemodel;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelPVProoflistBatch extends DefaultTableModel{

	private static final long serialVersionUID = 1L;

	static String[] columns = new String[] {
		"Tag", 				//0
		"PV No.", 			//1
		"PV Date", 			//2
		"Amount",			//3
		"Payee", 			//4
		"Availer",			//5
		"Payment Type",		//6
		"Status",			//7
		"Project"			//8
		
	};

	public modelPVProoflistBatch() {
		super(new Object[][]{}, columns);
	}

	public Class[] types = new Class[] {
			Boolean.class, 		//Tag
			String.class, 		//PV No
			Timestamp.class, 	//PV Date
			BigDecimal.class, 	//Amount
			String.class,		//Payee
			String.class,		//Availer
			String.class,		//Payment Type
			String.class,		//Status
			String.class		//Project
	};
	
	Boolean[] COLUMN_EDITABLE = new Boolean[] {
			true,
			false,		 		//PV No
			false, 				//PV Date
			false, 				//Amount
			false,				//Payee
			false,				//Availer
			false,				//Payment Type
			false,				//Status
			false				//Project
	};

	@Override
	public Class getColumnClass(int columnIndex) {
		return types[columnIndex];
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return COLUMN_EDITABLE[columnIndex];
	}
	
	public void clear() {
		FncTables.clearTable(this);
	}
}
