package tablemodel;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class model_proforma_payments extends DefaultTableModel {

	private static final long serialVersionUID = -4510182851060835992L;
	private boolean editable = false;
	boolean[] COLUMN_EDITABLE;
	
	public model_proforma_payments() {
		initThis(); 
	}

	public model_proforma_payments(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		
	}

	public model_proforma_payments(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);

	}

	public model_proforma_payments(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);

	}

	public model_proforma_payments(Vector data, Vector columnNames) {
		super(data, columnNames);

	}

	public model_proforma_payments(Object[][] data, Object[] columnNames) {
		super(data, columnNames);

	}

	public String[] COLUMNS = new String[] {
		"PF#", 
		"Description", 
		"Part.",
		
		"OR/AR",
		
		"Cash/Check",
		
		"Dated/PDC",
		
		"TR/OR",
		
		"LTS",
		"BOI",
		"25%",
		"Realized",
		"Applied"
	};
	
	public Class[] CLASS_TYPES = new Class[] {
			String.class, 
			Object.class, 
			String.class, 
			
			String.class,
			String.class,
			
			String.class,
			String.class,
			
			Boolean.class,
			Boolean.class,
			
			Boolean.class,
			Boolean.class,
			
			Boolean.class
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
		COLUMN_EDITABLE = new boolean[] {
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

	public void setEditable(boolean editable) {
		this.editable = editable;
		if (editable) {
			COLUMN_EDITABLE = new boolean[] {
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
		} else {
			COLUMN_EDITABLE = new boolean[] {
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
}
