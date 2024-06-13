package tablemodel;

import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class model_wtax_waiver_tagging extends DefaultTableModel {

	private static final long serialVersionUID = 1L;

	public model_wtax_waiver_tagging() {
		initThis();
	}

	public model_wtax_waiver_tagging(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public model_wtax_waiver_tagging(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public model_wtax_waiver_tagging(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public model_wtax_waiver_tagging(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public model_wtax_waiver_tagging(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	private void initThis() {
		setColumnIdentifiers(new String[] {
				"Select", 		//0
				"Entity ID", 	//1
				"Entity Name", 	//2
				//"<html><div align=center><font color=blue>Date Contract<br>Submitted</font></div></html>",		//9
				"TIN", 	//3
				"Entity Type ID",//4
				"Entity Type", 	//5
				"Date Submitted"//6
				
		});
	}

	Class[] COLUMN_TYPES = new Class[] {
			Boolean.class,//Tag
			String.class,//Entity ID
			Object.class,//Entity Name
			String.class,//Entity Type ID
			String.class,//Entity Type
			String.class,//TIN
			Timestamp.class//Date Submitted
			
	};

	Boolean[] COLUMN_EDITABLE = new Boolean[] {
			true,	//Tag
			false,	//Entity ID
			false,	//Entity Name
			false,	//Entity Type ID
			false,	//Entity Type
			false,	//TIN
			true	//Date Submitted
			
	};

	public Class getColumnClass(int columnIndex) {
		return COLUMN_TYPES[columnIndex];
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return COLUMN_EDITABLE[columnIndex];
	}
	
	public void clear() {
		FncTables.clearTable(this);
	}

}
