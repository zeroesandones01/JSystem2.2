package tablemodel;

import java.math.BigDecimal;
import java.sql.Date;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class model_OffRemAccounts extends DefaultTableModel {
	
	
	public model_OffRemAccounts () {
		initThis();
	}
	
	String [] Columns = new String [] {
			"Select",  // 0      	
			"ID",  		// 1			
			"Client Name",	// 2		
			"Project ID",	// 3
			"Project",	// 4
			"Unit",		// 5
			"PBL ID",	// 6
			"Seq_No",	// 7
			"Buyer Type"	//8
		
			
	};
	
	Boolean [] COLUMNS_EDITABLE = new Boolean [] {
			true,
			false,
			false,
			false,
			false,
			false,
			false,
			false,
			false
	};
	
	Class [] classType = new Class [] {
			
			Boolean.class,
			String.class,
			String.class,
			String.class,
			String.class,
			String.class,
			String.class,
			String.class,
			String.class
			
			
	};


	private void initThis() {
		setColumnIdentifiers(Columns);

	}
	public Class getColumnClass(int columnIndex){
		return classType[columnIndex];
	}
	public boolean isCellEditable(int rowIndex,int columnIndex){
		return COLUMNS_EDITABLE[columnIndex];
	}
	public void clear() {
		FncTables.clearTable(this);
	}

}
