package tablemodel;

import java.sql.Timestamp;

import javax.swing.table.DefaultTableModel;

public class model_UploadEmp extends DefaultTableModel {


	/**
	 * 
	 */
	private static final long serialVersionUID = 8597484058751149358L;
	public model_UploadEmp(){
		initThis();
		
	}
	String [] Columns = new String[]{
			"",
			"Emp. Code",
			"First Name",
			"Middle Name",
			"Last Name",
			"Birth. Date",
			"Sex",
			"Civil Status",
			"Dependent No.",
			"Emp. Status",
			"Hired Date",
			"Regular Date",
			"Emp. Rank",
			"Emp. Pos.",
			"Group Code",
			"Div. Code",
			"Dept_code",
			"Address",
			"Tel. Nos.",
			"Mobile No.",
			"Email",
			"Entity ID",
			"Rec ID"
	};
	Boolean[] COLUMNS_EDITABLE = new Boolean[]{
			true,
			false,
			false,
			false,
			false,
			false,
			false,
			false,
			false,
			false,
			false,
			false,
			false,
			false,
			false,
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
			Boolean.class,
			String.class,
			String.class,
			String.class,
			String.class,
			Timestamp.class,
			String.class,
			String.class,
			Integer.class,
			String.class,
			Timestamp.class,
			Timestamp.class,
			String.class,
			String.class,
			String.class,
			String.class,
			String.class,
			String.class,
			String.class,
			String.class,
			String.class,
			String.class,
			Integer.class

			
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
