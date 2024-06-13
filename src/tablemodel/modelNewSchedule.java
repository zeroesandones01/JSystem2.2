package tablemodel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelNewSchedule extends DefaultTableModel {

	private static final long serialVersionUID = -8738810102880371204L;
	
	private boolean editable = false;

	public modelNewSchedule() {
		initThis();
	}

	public modelNewSchedule(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelNewSchedule(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelNewSchedule(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelNewSchedule(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelNewSchedule(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	String[] COLUMNS = new String[] {
			
			//"Sched ID", //0
			"Part ID", //1
			"Part Description", //2
			"Sched. Date", //3
			"Amount", // 4
			"Proc. Fee", //5
			"RPT",
			"UPICO", //6
			"MRI", //7
			"Fire", //8
			"Interest", //9
			"Principal", //10
			"VAT", //11
			"Balance", //12
			"Int. Rate" //13 
	};
	
	 Class[] CLASS_TYPES = new Class[] {
			
			//String.class, //Sched ID
			String.class, //Part ID
			String.class, //Part Desc
			Timestamp.class, //Sched Date
			BigDecimal.class, //Amount
			BigDecimal.class, //Proc. Fee
			BigDecimal.class, //RPT
			BigDecimal.class, //UPICO
			BigDecimal.class, //MRI
			BigDecimal.class, //Fire
			BigDecimal.class, //Interest
			BigDecimal.class, //Principal
			BigDecimal.class, //VAT
			BigDecimal.class, //Balance
			BigDecimal.class //Int. rate
			
	};
	
	Boolean [] COLUMNS_EDITABLE = new Boolean[]{
			
			//false, //Sched ID
			false, //Part ID
			false, //Part Desc
			false, //Sched Date
			false, //Amount
			false, //Proc Fee
			false, //UPICO
			false, //MRI
			false, //Fire
			false, //Interest
			false, //Principal
			false, //VAT
			false, //Balance
			false //Int Rate
			
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
	}

	public Class getColumnClass(int columnIndex) {
		return CLASS_TYPES[columnIndex];
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return COLUMNS_EDITABLE[columnIndex];
	}
	
	public void clear() {
		FncTables.clearTable(this);
	}
	
	public boolean isEditable(){
		return editable;
	}
	
	public void setEditable(boolean editable){
		this.editable = editable;
	}

}
