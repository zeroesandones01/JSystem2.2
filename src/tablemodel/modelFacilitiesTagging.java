package tablemodel;

import java.math.BigDecimal;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelFacilitiesTagging extends DefaultTableModel {
	
	private static final long serialVersionUID = -5829982524175426170L;

	private boolean editable = false;

	public modelFacilitiesTagging() {
		initThis();
	}

	public modelFacilitiesTagging(int rowCount,
			int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelFacilitiesTagging(Vector columnNames,
			int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelFacilitiesTagging(Object[] columnNames,
			int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelFacilitiesTagging(Vector data,
			Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelFacilitiesTagging(Object[][] data,
			Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}
	
	String [] COLUMNS = new String []{
		
		"Select", //0
		"Proj.",//1
		"Description", //2
		"Client", //3
		"Prev. Rdng", //4
		"Curr Reading", //5
		"Water Cost", //6
		"Entity ID", //7
		"Proj. ID", //8
		"PBL ID", //9
		"Seq No.", //10
		"Rec. ID" //11
		
	};
	
	Class [] CLASS_TYPES = new Class []{
		
		Boolean.class, //Select
		String.class, //Proj.
		String.class, //Description
		String.class, //Client
		BigDecimal.class, //Prev. Rdng
		BigDecimal.class, //Curr Reading
		BigDecimal.class, //Water Cost
		String.class, //Entity_id
		String.class, //Proj. ID
		String.class, //PBL ID
		Integer.class, //Seq no
		Integer.class //Rec ID
		
	};
	
	Boolean [] COLUMNS_EDITABLE = new Boolean []{
			
		true, //Select
		false, //Proj.
		false, //Description
		false, //Client
		false, //Prev. Rdng
		true, //Curr Rdng
		false, //Water Cost
		false, //Entity_id
		false, //Proj id
		false, //PBL ID
		false, //Seq. No
		false //Rec. ID
		
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
