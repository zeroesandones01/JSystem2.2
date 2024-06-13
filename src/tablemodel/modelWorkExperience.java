package tablemodel;

import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelWorkExperience extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8738810102880371204L;

	public modelWorkExperience() {
		initThis();
	}

	public modelWorkExperience(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelWorkExperience(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelWorkExperience(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelWorkExperience(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelWorkExperience(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {//TODO uncomment employer if employer is in the database
			
			"Emp. Rec. ID", //0
			"Employer ID", //1
			"Employer", // 2
			"Employer Address", //3
			"Contact No", //4
			"Division/Dept.", //5
			"Position", //6
			"Rank Code", //7
			"Rank Desc", //8
			"Emp. Status", //9
			"Hired", //10
			"Resigned", //11
			"Basic Salary", //12
			"Allowances", //13
			"Others Monthly Income", //14
			"Deductions", //15
			"Total Gross Income", //16
			"Total Net Income", //17
			"Other Sources", //18
			"Exp. Rec. ID", //19
			"Transportation", //20
			"Food", //21
			"Other Expenses", //22
			"Total Expenses",//23
			"Employer Stat", //24
			"Pref Time Start", //25
			"Pref Time End", //26
			"Pref Day" //27
	};

	
	public Class[] CLASS_TYPES = new Class[] {
			
			Integer.class, //Employer Rec ID
			String.class, //Employer ID
			String.class, //Employer
			String.class, //Employer Address
			String.class, //Contact No
			String.class, //Division/Dept.
			String.class, //Position
			String.class, //Rank Code
			String.class, //Rank Desc
			String.class, //Emp. Status
			Timestamp.class, //Hired
			Timestamp.class, //Resigned
			BigDecimal.class, //Basic Salary
			BigDecimal.class, //Allowances
			BigDecimal.class, //Others Monthly Income
			BigDecimal.class, //Deductions
			BigDecimal.class, //Total Gross Income
			BigDecimal.class, //Total Net Income
			BigDecimal.class, //Other Sources
			Integer.class, //Exp. Rec ID
			BigDecimal.class, //Transportation
			BigDecimal.class, //Food
			BigDecimal.class, //Other Expenses
			BigDecimal.class, //Total Expenses
			String.class, //Employer Stat
			Time.class, //Pref Time Start
			Time.class, //Pref TIme End
			String.class //Pref Day
			
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
	}

	public Class getColumnClass(int columnIndex) {
		return CLASS_TYPES[columnIndex];
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}
	public void clear(){
		FncTables.clearTable(this);
	}

}
