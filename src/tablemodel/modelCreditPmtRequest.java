/**
 * 
 */
package tablemodel;

import java.math.BigDecimal;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;
import Functions.FncTables;

/**
 * @author John Lester Fatallo
 */
public class modelCreditPmtRequest extends DefaultTableModel {

	private static final long serialVersionUID = -4177802523598438729L;
	
	private boolean editable = false;

	public modelCreditPmtRequest() {
		initThis();
	}

	public modelCreditPmtRequest(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelCreditPmtRequest(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelCreditPmtRequest(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelCreditPmtRequest(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelCreditPmtRequest(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}
	
	String [] COLUMNS = new String []{
		"Pay Part", //0
		"Part ID", //1
		//"Part Abbrevation",
		"Particulars", //2
		"Amount", //3
		"Apply to Ledger" //4
	};
	
	Class[] CLASS_TYPE = new Class []{
		String.class, //Pay Part
		//String.class,
		String.class, //Part ID
		String.class, //Particulars
		BigDecimal.class, //AMount
		Boolean.class //Apply to Ledger
	};
	
	Boolean [] COLUMNS_EDITABLE = new Boolean[]{
		false, //Pay Part ID
		//false,
		false, //Part ID
		false, //Particulars
		true, //Amount
		false //Apply to Ledger
	};
	
	public void initThis(){
		setColumnIdentifiers(COLUMNS);
	}
	
	public Class getColumnClass(int columnIndex){
		return CLASS_TYPE[columnIndex];
	}
	
	public boolean isCellEditable(int rowIndex, int columnIndex){
		return COLUMNS_EDITABLE[columnIndex];
	}
	
	public void clear(){
		FncTables.clearTable(this);
	}
	
	public boolean isEditable(){
		return editable;
	}
	
	public void setEditable(boolean editable){
		this.editable = editable;
	}
}
