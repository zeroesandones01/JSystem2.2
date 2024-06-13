package tablemodel;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import Functions.FncTables;
import Lookup._JLookup;

public class model_unidentified_deposit_issuance extends DefaultTableModel {

	private static final long serialVersionUID = -1850397566103307355L;
	private boolean editable = false; 
	private boolean[] columns_editable; 
	
	private static String[] columns = new String[] {
		"tag", 
		"name", 
		"project",
		"unit",
		"bank",
		"account",
		"deposit date", 
		"particular", 
		"amount", 
		"entity_id", 
		"proj_id", 
		"pbl_id", 
		"seq_no", 
		"ud_id", 
		"pay_part_id", 
		"co_id",
		"unit_id" 
	}; 
	
	private static Class[] types = new Class[] {
			Boolean.class, 
			Object.class, 
			String.class,
			String.class, 
			String.class, 
			String.class, 
			JDateChooser.class, 
			_JLookup.class, 
			BigDecimal.class, 
			String.class,
			String.class,
			String.class,
			Integer.class, 
			Integer.class,
			String.class,
			String.class,
			String.class
	}; 

	public model_unidentified_deposit_issuance() {
		initGUI(); 
	}

	public model_unidentified_deposit_issuance(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initGUI(); 
	}

	public model_unidentified_deposit_issuance(Vector<?> columnNames, int rowCount) {
		super(columnNames, rowCount);
		initGUI(); 
	}

	public model_unidentified_deposit_issuance(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initGUI(); 
	}

	public model_unidentified_deposit_issuance(Vector<? extends Vector> data, Vector<?> columnNames) {
		super(data, columnNames);
		initGUI(); 
	}

	public model_unidentified_deposit_issuance(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initGUI(); 
	}
	
	private void initGUI() {
		setColumnIdentifiers(columns);
		
		columns_editable = new boolean[] {
				true, 
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
				false, 
				false, 
				false, 
				false
		}; 
	}

	public Class getColumnClass(int columnIndex) {
		return types[columnIndex];
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return columns_editable[columnIndex];
	}

	public boolean isCellEditable(int columnIndex) {
		return columns_editable[columnIndex];
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
