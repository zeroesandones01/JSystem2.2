
package tablemodel;

import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

/**
 * @author John Lester Fatallo
 */

public class modelPST_With_Annotated_TCT extends
		DefaultTableModel {
	
	private static final long serialVersionUID = 8235853137605087330L;

	private boolean editable = false;

	public modelPST_With_Annotated_TCT() {
		initThis();
	}

	public modelPST_With_Annotated_TCT(int rowCount,
			int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelPST_With_Annotated_TCT(Vector columnNames,
			int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelPST_With_Annotated_TCT(Object[] columnNames,
			int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelPST_With_Annotated_TCT(Vector data,
			Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelPST_With_Annotated_TCT(Object[][] data,
			Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}
	
	String [] COLUMNS = new String []{
		
		"Select", //0
		"Unit", //1
		"Name", //2
		"TCT Forwarded to RD", //3
		"Proj. Alias", //4
		"Proj. Name", //5
		"Entity ID", //6
		"Proj. ID", //7
		"PBL ID", //8
		"Seq" //9
		
	};
	
	Class [] CLASS_TYPES = new Class []{
		
		Boolean.class, //Select
		String.class, //Unit
		String.class, //Name
		Timestamp.class, //TCT Forwarded to RD
		String.class, //Proj. Alias
		String.class, //Proj. Name
		String.class, //Entity ID
		String.class, //Proj. ID
		String.class, //PBL ID
		Integer.class //Seq
		
	};
	
	Boolean [] COLUMNS_EDITABLE = new Boolean []{
			
		true, //Select
		false, //Unit
		false, //name
		false, //TCT Forwarder to RD
		false, //Proj. Alias
		false, //Proj. Name
		false, //Entity ID
		false, //Proj. ID
		false, //PBL ID
		false  //Seq
		
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
