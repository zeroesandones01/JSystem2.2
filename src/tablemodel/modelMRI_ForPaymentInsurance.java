/**
 * 
 */
package tablemodel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

/**
 * @author John Lester Fatallo
 */
public class modelMRI_ForPaymentInsurance extends DefaultTableModel {

	private static final long serialVersionUID = -3624035115588152766L;
	private boolean editable = false;

	public modelMRI_ForPaymentInsurance() {
		initThis();
	}

	public modelMRI_ForPaymentInsurance(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelMRI_ForPaymentInsurance(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelMRI_ForPaymentInsurance(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelMRI_ForPaymentInsurance(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelMRI_ForPaymentInsurance(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}
	
	String [] COLUMNS = new String[]{
			
		"Insurance Co", 
		"Invoice No.", 
		"RPLF No",
		"Reference No"
	};
	
	Class [] CLASS_TYPES = new Class[]{
		
		String.class, //Insurance Co
		String.class, //Invoice No
		String.class, //RPLF No
		String.class //Reference No
	};
	
	Boolean [] COLUMNS_EDITABLE = new Boolean []{
		
		false, //Insurance Co
		false, //Invoice No
		false, //RPLF No
		false //Reference No
		
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
