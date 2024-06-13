package tablemodel;

import java.math.BigDecimal;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class model_RelOR extends DefaultTableModel {

	private static final long serialVersionUID = -7222450940005252695L;
	private boolean editable = false;
	boolean[] COLUMNS_EDITABLE;
	
	public model_RelOR() {
		initCWT();
	}

	public model_RelOR(int rowCount, int columnCount) {
		super(rowCount, columnCount);

	}

	public model_RelOR(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);

	}

	public model_RelOR(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);

	}

	public model_RelOR(Vector data, Vector columnNames) {
		super(data, columnNames);

	}

	public model_RelOR(Object[][] data, Object[] columnNames) {
		super(data, columnNames);

	}

	private void initCWT() {
		setColumnIdentifiers(COLUMNS);
		COLUMNS_EDITABLE = new boolean[] {
			false, 				//	OR#				00
			true, 				//	Mark			01
			false,				//	Name			02
			false,				//	Unit			03
			false, 				//	Type			04
			false, 				//	Particulars		05
			false, 				//	Amount			06
			false, 				//	OR Date			07
			false, 				//	Doc Alias		08
			false, 				//	pay_rec_id		09
			false, 				//	ID				10
			false, 				//	Project ID		11
			false, 				//	Unit ID			12
			false, 				//	Lot Seq#		13
			false, 				//	Date Released	14
		};
	}
	
	public String [] COLUMNS = new String[] {
		"OR#", 
		"Mark", 
		"Name", 
		"Unit", 
		"Type", 
		"Particulars", 
		"Amount",
		"OR Date", 
		"Doc Alias",
		"Pay ID", 
		"ID", 
		"Project ID", 
		"Unit ID", 
		"Seq#",
		"Date Released" 
	};
	
	public Class [] ALIGNMENT = new Class [] {
		
	};
	
	public Class [] CLASS_TYPES = new Class[] {
		String.class, 				//	OR#				00
		Boolean.class, 				//	Mark			01
		String.class,				//	Name			02
		String.class,				//	Unit			03
		String.class,				//	Type			04
		String.class,				//	Particulars		05
		BigDecimal.class,			//	Amount			06
		String.class,				//	OR Date			07
		String.class,				//	Doc Alias		08
		String.class,				//	pay_rec_id		09
		String.class,				//	ID				10
		String.class,				//	Project ID		11
		String.class,				//	Unit ID			12
		String.class,				//	Lot Seq#		13
		String.class				//	Date Released	14
	};

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
