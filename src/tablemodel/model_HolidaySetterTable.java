package tablemodel;

import java.sql.Timestamp;

import javax.swing.table.DefaultTableModel;





public class model_HolidaySetterTable extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8597484058751149358L;
	public model_HolidaySetterTable(){
		initThis();
		
	}
	String [] Columns = new String[]{
			"REC ID",
			" DATE ",
			" NAME ",
			" TYPE ",
			"HALFDAY ",
			"CREATED BY ",
			"HO OTCODE ",
			"DATE CREATED"
	};
	Boolean[] COLUMNS_EDITABLE = new Boolean[]{
			false,
			false,
			false,
			false,
			false,
			false,
			false,
			false
		
	};
	Class[] classType = new Class[]{
			Integer.class,
			Timestamp.class,
			String.class,
			String.class,
			Boolean.class,
			String.class,
			String.class,
			Timestamp.class
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
	

}
