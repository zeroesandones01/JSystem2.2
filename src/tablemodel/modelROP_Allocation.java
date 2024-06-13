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
public class modelROP_Allocation extends DefaultTableModel {

	private static final long serialVersionUID = -4177802523598438729L;
	
	private boolean editable = false;

	public modelROP_Allocation() {
		initThis();
	}

	public modelROP_Allocation(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelROP_Allocation(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelROP_Allocation(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelROP_Allocation(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelROP_Allocation(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}
	
	String [] COLUMNS = new String []{
		"Select", //0
		"Pay Part ID", //1
		"Particulars", //2
		"Part Desc", //3
		"Amount", //4
		"Apply to Ledger", //5
		"RPLF No", // 6
		"Released By", //7
		"Released Date", //8
		"Received by", //9
		"Received Date" //10
	};
	
	Class[] CLASS_TYPE = new Class []{
			
		Boolean.class, //Select
		Object.class, //Pay Part ID
		Object.class, //Particulars
		Object.class, //Part Desc
		BigDecimal.class, //Amount
		Boolean.class, //Apply to Ledger
		Object.class, //RPLF No
		Object.class, //Released By
		Timestamp.class, //Released Date
		Object.class, //Received By
		Timestamp.class //Received Date
	};
	
	Boolean [] COLUMNS_EDITABLE = new Boolean[]{
		true, //Select 
		false, //Pay Part ID
		false, //Partculars
		false, //part Desc
		true, //Amount
		false, //Apply to Ledger
		false, //RPLF No 
		false, //Released by
		false, //Released Date
		false, //Received by
		false //Received Date
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
