package tablemodel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import javax.swing.table.DefaultTableModel;

import Lookup._JLookup;

public class model_SpecialProjMonitoring extends DefaultTableModel {

	private static final long serialVersionUID = 6734452004087882000L;
	private boolean editable = false;
	boolean[] COLUMN_EDITABLE;

 public model_SpecialProjMonitoring() {
	 initThis();
 }


	String[] Columns = new String[]{
			"Activities",
			"<html>Original<br>Schedule<br>(Start)</html>",
			"<html>Original<br>Schedule<br>(End)</html>",
			"<html>Duration<br>(Days)</html>",
			"<html>Actual<br>Schedule<br>(Start)</html>",
			"<html>Actual<br>Schedule<br>(End)</html>",
			"<html>Duration<br>(Days)</html>",
			"<html>Dependency<br>(Activity No.)</html>",
			"<html>Activity<br>In-Charge(AIC)</html>",
			"Feedback Date",
			"<html>Contact Person<br>(Whom to get feedback)<html/>",
			"Feedback Details",
			"<html>Remarks/<br>ImportantNotes</html>"

	};

//	Boolean[] COLUMNS_EDITABLE = new Boolean[]{
//
//			true,
//			true,
//			false,
//			true,
//			true,
//			false,
//			true,
//			true,
//			true,
//			true,
//			true,
//			true,
//			true,
//
//
//
//	};

	Class[] classType = new Class[]{
			String.class, //Activities
			Timestamp.class, //Orig. Sched. Start
			Timestamp.class, // Orig. Sched. End
			String.class, // Duration
			Timestamp.class, //Actual Sched. Start
			Timestamp.class, //Actual Sched. End
			String.class, //Duration
			_JLookup.class, // Dependency
			_JLookup.class, //Activity In charge
			Timestamp.class, //Feedback Date
			_JLookup.class, //Contact Person
			String.class, // Feedback Details
			String.class //Remarks


	};

	private void initThis() {
		setColumnIdentifiers(Columns);

	}
	public Class getColumnClass(int columnIndex){
		return classType[columnIndex];
	}

	public boolean isCellEditable(int rowIndex,int columnIndex){

		return COLUMN_EDITABLE[columnIndex];
	}
	public void setEditable(boolean editable) {
		this.editable = editable;
		if(editable){
			COLUMN_EDITABLE = new boolean[] {
					true, //Activities
					false, //Orig. Sched. Start
					false, // Orig. Sched. End
					true, // Duration
					false, //Actual Sched. Start
					false, //Actual Sched. End
					true, //Duration
					false, // Dependency
					false, //Activity In charge
					false, //Feedback Date
					false, //Contact Person
					true, // Feedback Details
					true //Remarks
			};
		}else{
			COLUMN_EDITABLE = new boolean[] {
					false, //Activities
					false, //Orig. Sched. Start
					false, // Orig. Sched. End
					false, // Duration
					false, //Actual Sched. Start
					false, //Actual Sched. End
					false, //Duration
					false, // Dependency
					false, //Activity In charge
					false, //Feedback Date
					false, //Contact Person
					false, // Feedback Details
					false //Remarks
					
			};
		}
	}
	
	

}
