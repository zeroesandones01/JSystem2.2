package tablemodel;

import java.math.BigDecimal;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class model_PaymentBreak extends DefaultTableModel {

	private static final long serialVersionUID = 1298181289772718059L;
	private boolean editable = false;
	boolean[] COLUMNS_EDITABLE;
	
	public model_PaymentBreak() {
		initCWT();
	}

	public model_PaymentBreak(int rowCount, int columnCount) {
		super(rowCount, columnCount);

	}

	public model_PaymentBreak(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);

	}

	public model_PaymentBreak(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);

	}

	public model_PaymentBreak(Vector data, Vector columnNames) {
		super(data, columnNames);

	}

	public model_PaymentBreak(Object[][] data, Object[] columnNames) {
		super(data, columnNames);

	}

	private void initCWT() {
		setColumnIdentifiers(COLUMNS);
		COLUMNS_EDITABLE = new boolean[] {
			false, 				//	receipt_no		00
			false, 				//	part_id			01
			true, 				//	amount			02
			false, 				//	status_id		03
			false, 				//	header_id		04
			false, 				//	client_seqno	05
			false 				//	tra_detail_id	06
		};
	}
	
	public String [] COLUMNS = new String[] {
		"Receipt",				//	00
		"Particular",			//	01
		"Amount",				//	02
		"Status",				//	03
		"Header ID",			//	04
		"Sequence No.",			//	05
		"Detail ID"				//	06
	};
	
	public Class [] ALIGNMENT = new Class [] {
		
	};
	
	public Class [] CLASS_TYPES = new Class[] {
			String.class, 		//	receipt_no		00
			String.class, 		//	part_id			01 
			BigDecimal.class, 	//	amount			02
			String.class,		//	status_id		03
			String.class,		//	header_id		04
			String.class,		//	client_seqno	05
			String.class		//	tra_detail_id	06
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
