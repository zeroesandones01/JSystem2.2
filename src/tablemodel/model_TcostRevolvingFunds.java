package tablemodel;

import java.math.BigDecimal;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class model_TcostRevolvingFunds extends DefaultTableModel{

	private static final long serialVersionUID = -895341478567700134L;
	private  boolean editable = false;
	
	
	
	public model_TcostRevolvingFunds(){
		initThis();
		
	}
	
	String[] Columns = new String[]{
			"Select",
			"TCost ID",
			"TCost Desc",
			"Amount",
			"Record ID",	
	};
	
	Boolean[] COLUMNS_EDITABLE = new Boolean[]{

			true,
			false,
			false,
			false,
			false,
			

	};
	Class[] classType = new Class[]{
			Boolean.class,
			String.class,
			Object.class,
			BigDecimal.class,
			Integer.class,
			


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


	public boolean isEditable() {
		return editable;
	}

}
