package tablemodel;

import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelActionPlan extends DefaultTableModel {

	private static final long serialVersionUID = -800977269370899008L;

	public modelActionPlan() {
		initThis();
	}

	public modelActionPlan(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelActionPlan(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelActionPlan(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelActionPlan(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelActionPlan(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			"Rec. ID", //0
			"Complaint No", //1
			"Target Date", //2
			"Done Date", //3
			"In Charge Code", // 3
			"In Charge", //4
			"Person Contacted", //5
			"Feedback Thru", //6
			"Action Plan"  //7
			
	};
	
	public Class[] CLASS_TYPES = new Class[] {
			Integer.class, // Rec. ID
			String.class, //Complaint No.
			Timestamp.class, //Target Date
			Timestamp.class, //Done Date
			Object.class, //In Charge Code
			String.class, //In Charge
			String.class, //Person Contacted
			String.class, //Feedback Thru
			Object.class //Action Plan
			
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
