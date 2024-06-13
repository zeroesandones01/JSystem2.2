package tablemodel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class model_LandDev_CostMonitoring	 extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8738810102880371204L;
	
	private boolean editable = false;
	boolean[] COLUMN_EDITABLE;
	
	
	public model_LandDev_CostMonitoring() {
		initThis();
	}

	public model_LandDev_CostMonitoring(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public model_LandDev_CostMonitoring(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public model_LandDev_CostMonitoring(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public model_LandDev_CostMonitoring(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public model_LandDev_CostMonitoring(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {			
			"<html><center>H<html><br><html><center>1<html>", 		// 1
			"<html><center>H<html><br><html><center>2<html>", 		// 2
			"<html><center>H<html><br><html><center>3<html>", 		// 3
			"<html><center>H<html><br><html><center>4<html>", 		// 4
			"<html><center>H<html><br><html><center>5<html>", 		// 5
			"Description" ,	// 6
			"Unit" ,		// 7
			"Qty." ,		// 8
			"<html><center>Material<html><br><html><center>Unit Cost<html>", 	// 9
			"<html><center>Material<html><br><html><center>Total Cost<html>", 	// 10
			"<html><center>Labor<html><br><html><center>Unit Cost<html>", 		// 11
			"<html><center>Labor<html><br><html><center>Total Cost<html>", 		// 12
			"<html><center>Total<html><br><html><center>Unit Cost<html>", 		// 13
			"<html><center>Total<html><br><html><center>Total Cost<html>" 		// 14
			};

	
	/**
	 * String.class = Alignment.CENTER
	 * Object.class = Alignment.LEFT
	 * BigDecimal.class = Alignment.RIGHT
	 * Integer.class = Alignment.CENTER
	 * Timestamp.class
	 */
	public Class[] CLASS_TYPES = new Class[] {
			String.class, 		//H1
			String.class, 		//H2
			String.class,		//H3
			String.class, 		//H4
			String.class, 		//H5
			Object.class, 		//Description
			String.class, 		//Unit
			String.class, 		//Qty
			BigDecimal.class, 	//Material-Unit Cost
			BigDecimal.class, 	//Material-Total Cost
			BigDecimal.class, 	//Labor-Unit Cost
			BigDecimal.class, 	//Labor-Total Cost
			BigDecimal.class, 	//Total-Unit Cost
			BigDecimal.class 	//Total-Total Cost
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
		COLUMN_EDITABLE = new boolean[] {
				false, 		//H1
				false, 		//H2
				false,		//H3
				false, 		//H4
				false, 		//H5
				false, 		//Description
				false, 		//Unit
				false, 		//Qty
				false, 		//Material-Unit Cost
				false, 		//Material-Total Cost
				false, 		//Labor-Unit Cost
				false, 		//Labor-Total Cost
				false, 		//Total-Unit Cost
				false 		//Total-Total Cost
		};
	}

	public Class getColumnClass(int columnIndex) {
		return CLASS_TYPES[columnIndex];
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return COLUMN_EDITABLE[columnIndex];
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
		if(editable){
			COLUMN_EDITABLE = new boolean[] {
					true, 		//H1
					true, 		//H2
					true,		//H3
					true, 		//H4
					true, 		//H5
					true, 		//Description
					true, 		//Unit
					true, 		//Qty
					true, 		//Material-Unit Cost
					false, 		//Material-Total Cost
					true, 		//Labor-Unit Cost
					false, 		//Labor-Total Cost
					true, 		//Total-Unit Cost
					false 		//Total-Total Cost
			};
		}else{
			COLUMN_EDITABLE = new boolean[] {
					false, 		//H1
					false, 		//H2
					false,		//H3
					false, 		//H4
					false, 		//H5
					false, 		//Description
					false, 		//Unit
					false, 		//Qty
					false, 		//Material-Unit Cost
					false, 		//Material-Total Cost
					false, 		//Labor-Unit Cost
					false, 		//Labor-Total Cost
					false, 		//Total-Unit Cost
					false 		//Total-Total Cost
			};
		}
	}

	
	
	
}
