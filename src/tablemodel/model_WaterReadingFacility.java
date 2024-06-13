package tablemodel;

import java.math.BigDecimal;
import java.sql.Date;

import javax.swing.table.DefaultTableModel;

public class model_WaterReadingFacility extends DefaultTableModel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public model_WaterReadingFacility () {
		initThis();

	}
	String [] Columns = new String [] {
			"Rec_ID",
			"Entity_ID",
			"Client",
			"Proj_ID",
			"Proj_Alias",
			"PBL_ID",
			"Description",
			"Reading_Date",
			"Previous Reading",
			"Present Reading",
			"Consumption", 
			"Water Cost"
	};
	
	Boolean [] COLUMNS_EDITABLE = new Boolean [] {
			false,
			false,
			false,
			false,
			false,
			false,
			false,
			false,
			false,
			false,
			false,
			false
	};
	
	Class [] classType = new Class [] {
			Integer.class,
			Integer.class,
			String.class,
			Integer.class,
			String.class,
			Integer.class,
			String.class,
			Date.class,
			BigDecimal.class,
			BigDecimal.class,
			BigDecimal.class,
			BigDecimal.class
			
	};


	private void initThis() {
		setColumnIdentifiers(Columns);

	}
	public Class getColumnClass(int columnIndex){
		return classType[columnIndex];
	}
	public boolean isCellEditable(int rowIndex,int columnIndex){
		return COLUMNS_EDITABLE[columnIndex];
	}
}
