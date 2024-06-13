package tablemodel;
import java.util.Date;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;


public class model_water_reading_adjmt  extends DefaultTableModel {

	private static final long serialVersionUID = -5630252945650501967L;
	private boolean editable = false;
	boolean[] COLUMNS_EDITABLE;	

	public model_water_reading_adjmt() {
		InitGUI(); 
	}

	public model_water_reading_adjmt(int rowCount, int columnCount) {
		super(rowCount, columnCount);

	}

	public model_water_reading_adjmt(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);

	}

	public model_water_reading_adjmt(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);

	}

	public model_water_reading_adjmt(Vector data, Vector columnNames) {
		super(data, columnNames);

	}

	public model_water_reading_adjmt(Object[][] data, Object[] columnNames) {
		super(data, columnNames);

	}

	private void InitGUI() {
		setColumnIdentifiers(COLUMNS);

		COLUMNS_EDITABLE = new boolean[] {
				false, 

				false,
				false,
				false,
				false,
				
				false,
				false
		};
	}

	public String [] COLUMNS = new String[] {		
			"Reading Date",
			
			"Previous Reading",
			"Current Reading",
			"  Water Cost  ",
			"Amount Due",
			
			"     Remarks     ",
			"Water Meter No."
 
	};

	public Class [] CLASS_TYPES = new Class[] {
			Date.class,
			
			Integer.class,
			Integer.class,
			Integer.class,
			Integer.class,
			
			String.class,
			String.class

	};

	public Class getColumnClass(int columnIndex) {
		return CLASS_TYPES[columnIndex];
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return COLUMNS_EDITABLE[columnIndex];
	}

	public boolean isCellEditable(int columnIndex) {
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


