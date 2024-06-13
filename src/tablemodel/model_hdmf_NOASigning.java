package tablemodel;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import Functions.FncTables;

public class model_hdmf_NOASigning extends DefaultTableModel {

	private static final long serialVersionUID = 6088418569910151364L;
	private boolean editable = false;
	
	public model_hdmf_NOASigning() {
		initThis();
	}

	public model_hdmf_NOASigning(int rowCount, int columnCount) {
		super(rowCount, columnCount);

	}

	public model_hdmf_NOASigning(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);

	}

	public model_hdmf_NOASigning(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);

	}

	public model_hdmf_NOASigning(Vector data, Vector columnNames) {
		super(data, columnNames);

	}

	public model_hdmf_NOASigning(Object[][] data, Object[] columnNames) {
		super(data, columnNames);

	}

	String [] COLUMNS = new String [] {
			"Tag", 					// 0
			"Name", 				// 1
			"Unit", 				// 2
			"Loan Amt", 			// 3
			"NOA Released", 		// 4
			"NOA Actual Date",		// 5
			"entity_id", 			// 6
			"proj_id", 				// 7
			"pbl_id", 				// 8
			"seq_no" 				// 9
		};
			
	Class [] CLASS_TYPES = new Class []{
			Boolean.class, 			// Tag
			Object.class, 			// Name
			String.class, 			// Unit
			BigDecimal.class, 		// Loan Amt
			Date.class,				// NOA Released
			Date.class,				// NOA Actual Date
			String.class, 			// entity_id
			String.class, 			// proj_id
			String.class, 			// pbl_id
			Integer.class			// seq_no
	};
	
	Boolean [] COLUMNS_EDITABLE = new Boolean []{
			true,					// Tag
			false,					// Name
			false,					// Unit
			false,					// Loan Amt
			false,					// NOA Released
			false,					// NOA Actual Date
			false,					// entity_id
			false,					// proj_id
			false,					// pbl_id
			false					// seq_no
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
