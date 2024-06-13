package tablemodel;

import java.math.BigDecimal;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class model_proforma_entries extends DefaultTableModel {

	private static final long serialVersionUID = -7487394930924888531L;
	private boolean editable = false;
	boolean[] columns_editable;	
	
	public model_proforma_entries() {
		InitGUI(); 
	}

	public model_proforma_entries(int rowCount, int columnCount) {
		super(rowCount, columnCount);

	}

	public model_proforma_entries(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);

	}

	public model_proforma_entries(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);

	}

	public model_proforma_entries(Vector data, Vector columnNames) {
		super(data, columnNames);

	}

	public model_proforma_entries(Object[][] data, Object[] columnNames) {
		super(data, columnNames);

	}

	
	private void InitGUI() {
		setColumnIdentifiers(COLUMNS);
		
		columns_editable = new boolean[] {
			false, 
			false,
			false,
			false
		};
	}
	
	public String [] COLUMNS = new String[] {
		"Bal. Side",
		"Account No",
		"Account Name",
		"Amount"
	};
	
	public Class [] CLASS_TYPES = new Class[] {
		String.class,
		String.class,
		Object.class,
		BigDecimal.class
	};

	public Class getColumnClass(int columnIndex) {
		return CLASS_TYPES[columnIndex];
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
