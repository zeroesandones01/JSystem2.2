package tablemodel;

import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelMeralcoIndividualEnergization extends DefaultTableModel {

	private static final long serialVersionUID = -6204471854045588340L;

	public modelMeralcoIndividualEnergization() {
		initThis();
	}
	
	public modelMeralcoIndividualEnergization(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}
	
	public modelMeralcoIndividualEnergization(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}
	
	public modelMeralcoIndividualEnergization(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}
	
	public modelMeralcoIndividualEnergization(Vector data, Vector columnNames) {
		super(data, columnNames);
	}
	
	public modelMeralcoIndividualEnergization(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}
	
	/*static String[] columns = new String[] { 
			"Select", //0
			"Unit",   // 1
			"Client", //2
			"CSE Cont", //3
			"CSE NTP", //4
			"CSE Inst", //5
			"% Accomp.", //6
			"CEI Recv", //7
			"Meralco Payment", //8
			"Check Rel", //9
			"MBASE Issued", //10
			"MER Ener", //11
			"MER Liq", //12
			"DOB", //13
			"Contact #", //14
			"Model", //15
			"Entity ID", //16
			"Proj. ID", //17
			"PBL ID", //18
			"Seq. No" //19
		};
	
	public modelMeralcoIndividualEnergization() {
		super(new Object[][]{}, columns);
	}*/
	
	private void initThis() {
		setColumnIdentifiers(new String[] {
				"Select", //0
				"Unit",   // 1
				"Client", //2
				"CSE Cont", //3
				"CSE NTP", //4
				"CSE Inst", //5
				"% Accomp.", //6
				"CEI No", //7
				"Meralco Payment", //8
				"Check Rel", //9
				"MBASE Issued", //10
				"MER Ener", //11
				"MER Liq", //12
				"DOB", //13
				"ID Submitted", //14
				"Contact #", //15
				"Model", //16
				"Entity ID", //17
				"Proj. ID", //18
				"PBL ID", //19
				"Seq. No" //20
		});
	}

	Class[] CLASS_TYPES = new Class[] {

			Boolean.class, //Select
			String.class,  //Unit
			Object.class,  //Client
			Timestamp.class, //CSE Cont
			Timestamp.class, //CSE NTP
			Timestamp.class, //CSE Inst
			String.class, //% Accomplish
			String.class, //CEI Recv
			Timestamp.class, //Meralco Payment 
			Timestamp.class, //Check Rel
			Timestamp.class, //MBASE Issued
			Timestamp.class, //Mer Ener
			Timestamp.class, //Mer Liq
			Timestamp.class, //DOB
			Object.class, //ID Submitted
			Object.class, //Contact #
			String.class, //Model
			String.class, //Entity ID
			String.class, //Proj. ID
			String.class, //PBL ID
			Integer.class //Seq No

	};

	Boolean[] COLUMN_EDITABLE = new Boolean[] {
			
			true,//Select 
			false, //Unit
			false, //Client
			false, //CSE Cont
			false, //CSE NTP
			false, //CSE Inst
			false, //% Accomplish
			false, //CEI Recv
			false, //Meralco Payment, 
			false, // Check Rel
			false, //MBase Issued
			false, //Mer Ener
			false, //Mer Liq
			false, //DOB
			false, //ID Submitted
			false, //Contact #
			false, //Model
			false, //Entity ID 
			false, //Proj. ID
			false, //PBL ID
			false //Seq No
			
	};

	public Class getColumnClass(int columnIndex) {
		return CLASS_TYPES[columnIndex];
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return COLUMN_EDITABLE[columnIndex];
	}
	
	/*public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}*/

	public void clear() {
		FncTables.clearTable(this);
	}

}
