package tablemodel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelCommSchedule extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8738810102880371204L;

	public modelCommSchedule() {
		initThis();
	}

	public modelCommSchedule(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelCommSchedule(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelCommSchedule(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelCommSchedule(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelCommSchedule(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			"Client ID", // 0
			"Client Name", // 1
			"PBL ID", // 2
			"Seq No.", // 3
			"PBL Desc.", // 4			
			"<html><div align=center>Date<br>Reserved</div></html>", // 5
			"NSP", //6
			"Agent ID", //7
			"Agent Name", //8
			"<html><div align=center>Payment<br>Scheme ID</div></html>", // 10			
			"<html><div align=center>Payment<br>Scheme Desc.</div></html>", // 10
			"Proj Code",// 11
			"Project Name",// 12
			"<html><div align=center>Phase<br>Code</div></html>", // 13
			"Phase",  //14
			"Model ID",// 15
			"House Model"// 16
			
	};
	
	public Class[] CLASS_TYPES = new Class[] {
			String.class,		//Client ID - 0
			Object.class,		//Client Name - 1
			String.class,		//PBL ID - 2			
			Integer.class,		//Seq No. - 3
			String.class, 		//PBL Desc. - 4			
			Timestamp.class,	//Date Reserved - 5
			BigDecimal.class,	//NSP - 6
			String.class,		//Agent ID - 7
			Object.class,		//Agent Name - 8
			String.class,		//Pmt. Scheme ID - 9
			String.class,		//Pmt. Scheme Desc. - 10
			String.class,		//Proj Code - 11	
			String.class,		//Project Name - 12
			String.class,		//Phase Code - 13
			String.class,		//Phase - 14
			String.class,		//Model ID - 15
			String.class		//House Model � 16
			
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
