package tablemodel;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class model_retention_details extends DefaultTableModel {

	private static final long serialVersionUID = -5799274505664837383L;
	private boolean editable = false;

	public model_retention_details() {
		initThis();
	}

	public model_retention_details(int arg0, int arg1) {
		super(arg0, arg1);

	}

	public model_retention_details(Vector arg0, int arg1) {
		super(arg0, arg1);

	}

	public model_retention_details(Object[] arg0, int arg1) {
		super(arg0, arg1);

	}

	public model_retention_details(Vector arg0, Vector arg1) {
		super(arg0, arg1);

	}

	public model_retention_details(Object[][] arg0, Object[] arg1) {
		super(arg0, arg1);

	}
	
	String [] COLUMNS = new String [] {
			"Type", 				// 0
			"Receipt No.", 			// 1
			"Particular", 			// 2
			"Amount",  				// 4
			"Account No.",			// 5
			"Bank", 				// 6
			"Branch", 				// 7
			"Check No.", 			// 8
			"Check Date", 			// 9
			"Sequence"				// 10
		};
			
	Class [] CLASS_TYPES = new Class []{
			String.class,			// Receipt Type
			String.class,			// Receipt No.
			String.class,			// Particular
			BigDecimal.class,		// Amount
			String.class,			// Account No.
			String.class,			// Bank
			String.class,			// Branch
			String.class,			// Check No.
			Date.class,				// Check Date
			String.class			// Sequence
	};
	
	Boolean [] COLUMNS_EDITABLE = new Boolean [] {
			false,					// Receipt Type
			false,					// Receipt No.
			false,					// Check No.
			false,					// Check Date
			false,					// Account No.
			false,					// Bank
			false,					// Branch
			false,					// Particular
			false,					// Amount
			false					// Sequence
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
