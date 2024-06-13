package tablemodel;

import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class modelUpdateEmployee extends DefaultTableModel {


	/**
	 * 
	 */
	private static final long serialVersionUID = 8597484058751149358L;
	public modelUpdateEmployee(){
		initThis();
		
	}
	String [] Columns = new String[]{
			"",						//0
			"Emp. Code",			//1
			"Full Name",			//2
			"First Name",			//3
			"Middle Name",			//4
			"Last Name",			//5
			"Birth. Date",			//6
			"Sex",					//7
			"Civil Status",			//8
			"Dependent No.",		//9
			"Emp. Status",			//10
			"Hired Date",			//11
			"Regular Date",			//12
			"Terminate Date",		//13
			"Emp. Rank",			//14
			"Emp. Pos.",			//15
			"Group Code",			//16
			"Div. Code",			//17
			"Dept_code",			//18
			"Address",				//19
			"Tel. Nos.",			//20
			"Mobile No.",			//21
			"Email",				//22
			"Entity ID",			//23
			"Rec ID"				//24
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
			String.class,
			Timestamp.class,
			String.class,
			String.class,
			Integer.class,
			String.class,
			Timestamp.class,
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
