/**
 * 
 */
package tablemodel;

import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

/**
 * @author John Lester Fatallo
 */
public class modelPagibigStatusMonitoring_HouseInspection extends
		DefaultTableModel {

	
	private static final long serialVersionUID = 1783559702696501950L;
	private boolean editable = false;

	public modelPagibigStatusMonitoring_HouseInspection() {
		initThis();
	}

	public modelPagibigStatusMonitoring_HouseInspection(int rowCount,
			int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelPagibigStatusMonitoring_HouseInspection(Vector columnNames,
			int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelPagibigStatusMonitoring_HouseInspection(Object[] columnNames,
			int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelPagibigStatusMonitoring_HouseInspection(Vector data,
			Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelPagibigStatusMonitoring_HouseInspection(Object[][] data,
			Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}
	
	String [] COLUMNS = new String []{
		
		"Select", //0
		"Proj ID", //1
		"Proj", //2
		"Unit ID", //3
		"PBL", //4
		"Seq", //5
		"Ph", //6
		"Blk", //7
		"Lt", //8
		"Entity ID", //9
		"Client Name", //10
		"Requested for Inspection", //11
		"Inspected", //12
		"W/ Finding", //13
		"For Re-Inspection", //14
		"COA Release", //15
		"HDMF Finding", //16
		"Contractor" //17
		
	};
	
	Class [] CLASS_TYPES = new Class[]{
		
		Boolean.class, //Select
		String.class, //Proj ID
		String.class, //Proj
		String.class, //Unit ID
		String.class, //PBL
		Integer.class, //Seq
		String.class, //Ph
		String.class, //Blk
		String.class, //Lt
		String.class, //Entity ID
		String.class, //Client Name
		Timestamp.class, //Requested for Inspection
		Timestamp.class, //Inspected
		Timestamp.class, //W/ Finding
		Timestamp.class, //For Reinspection
		Timestamp.class, //COA Release
		Timestamp.class, //HDMF Finding
		String.class //Contractor
		
	};
	
	Boolean [] COLUMNS_EDITABLE = new Boolean[]{
		
		true, //Select
		false, //Proj ID
		false, //Proj
		false, //Unit ID
		false, //PBL
		false, //Seq
		false, //Ph
		false, //Blk
		false, //Lt
		false, //Entity ID
		false, //Client Name
		false, //Requested for Inspection
		false, //Inspected
		false, //w/ Finding
		false, //For Reinspection
		false, //COA Release
		true, //HDMF Finding
		false //Contractor
		
	};
	
	private void initThis(){
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

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}
	

}
