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
 * @author Christian Paquibot
 *
 */

@SuppressWarnings({"unchecked","rawtypes"})
public class model_RequestPaymenTrip extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
 
	private boolean editable = false;
	
	public model_RequestPaymenTrip() {
		 initThis();
	}

	public model_RequestPaymenTrip(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		 initThis();
	}

	public model_RequestPaymenTrip(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		 initThis();
	}

	public model_RequestPaymenTrip(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		 initThis();
	}

	public model_RequestPaymenTrip(Vector data, Vector columnNames) {
		super(data, columnNames);
		 initThis();
	}

	public model_RequestPaymenTrip(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		 initThis();
	}

	String[] COLUMNS = new String[] {
			"Tag", // 0
			"Ticket No.", // 1
			"Date", // 2
			"Request By", // 3
			"Cost", // 4
			"Driver Name", // 5
			"ATM No.", // 6
			"Purpose", // 7
			"Entity ID", // 8
			"Driver ID", // 9
			"Div ID", // 10
			"Dept ID", // 11
			"Charge Acct. ID", // 12
			"Charge Proj. ID", // 13
			"Co ID", // 14
			"BusUnit ID" // 15
			
	};
	
	Class[] CLASS_TYPES = new Class[] {
			
			Boolean.class,//"Tag", // 0
			String.class,//"Ticket No.", // 1
			Timestamp.class,//"Date", // 3
			String.class,//"Request By", // 4
			BigDecimal.class,//"Cost", // 5
			String.class,//"Driver Name", // 6
			String.class,//"ATM No.", // 7
			String.class,//"Purpose", // 8
			String.class,//"Entity ID", // 9
			String.class,//"Driver ID", // 10
			String.class,//"Div ID", // 10
			String.class,//"Dept ID", // 11
			String.class,//"Charge Acct. ID", // 12
			String.class,//"Charge Proj. ID", // 13
			String.class,//"Co ID", // 15
			String.class//"BusUnit ID" // 16
			
	};
	
	boolean[] COLUMN_EDITABLE = new boolean[] {
			
			
			false,//"Tag", // 0
			false,//"Ticket No.", // 1
			false,//"Date", // 3
			false,//"Request By", // 4
			false,//"Cost", // 5
			false,//"Driver Name", // 6
			false,//"ATM No.", // 7
			false,//"Purpose", // 8
			false,//"Entity ID", // 9
			false,//"Driver ID", // 10
			false,//"Dept ID", // 11
			false,//"Charge Acct. ID", // 12
			false,//"Charge Proj. ID", // 13
			false,//"Proj. ID", // 14
			false,//"Co ID", // 15
			false//"BusUnit ID" // 16
	
	};
	
	private void initThis() {
		setColumnIdentifiers(COLUMNS);
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
	
	public void setEditable(boolean editable) {
		this.editable = editable;
		if(editable){
			COLUMN_EDITABLE = new boolean[] {

					true,//"Tag", // 0
					false,//"Ticket No.", // 1
					false,//"Date", // 3
					false,//"Request By", // 4
					false,//"Cost", // 5
					false,//"Driver Name", // 6
					false,//"ATM No.", // 7
					false,//"Purpose", // 8
					false,//"Entity ID", // 9
					false,//"Driver ID", // 10
					false,//"Dept ID", // 11
					false,//"Charge Acct. ID", // 12
					false,//"Charge Proj. ID", // 13
					false,//"Proj. ID", // 14
					false,//"Co ID", // 15
					false//"BusUnit ID" // 16
			};
			
		}else{
			COLUMN_EDITABLE = new boolean[] {
					
					false,//"Tag", // 0
					false,//"Ticket No.", // 1
					false,//"Date", // 3
					false,//"Request By", // 4
					false,//"Cost", // 5
					false,//"Driver Name", // 6
					false,//"ATM No.", // 7
					false,//"Purpose", // 8
					false,//"Entity ID", // 9
					false,//"Driver ID", // 10
					false,//"Dept ID", // 11
					false,//"Charge Acct. ID", // 12
					false,//"Charge Proj. ID", // 13
					false,//"Proj. ID", // 14
					false,//"Co ID", // 15
					false//"BusUnit ID" // 16
			
			};
		}
	}
	
	public void clear() {
		FncTables.clearTable(this);
	}
}
