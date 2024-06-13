package tablemodel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelNTPSuretyBond extends DefaultTableModel {

	private static final long serialVersionUID = 1L;
	
	private boolean editable = false;
	boolean[] COLUMN_EDITABLE;

	public modelNTPSuretyBond() {
		initThis();
	}

	public modelNTPSuretyBond(boolean editable) {
		initThis();
	}

	public modelNTPSuretyBond(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelNTPSuretyBond(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelNTPSuretyBond(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelNTPSuretyBond(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelNTPSuretyBond(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}
	
	String[] COLUMNS = new String[] { 
			"Bond No", 
			"Insurance Co. ID",
			"Insurance Co.",
			"From",
			"To",
			"Amount",
			"Dept. Concerned ID",
			"Dept. Concerned",
			"Present Location ID",
			"Present Location",
			"Rec ID"
	};
	
	Class[] CLASS_TYPES = new Class[] {
			Object.class, //Bond No
			String.class, //Insurance Co. ID
			Object.class, //Insurance Co.
			Timestamp.class, //From
			Timestamp.class, //To
			BigDecimal.class, //Amount
			String.class, //Dept. Concerned ID
			Object.class, //Dept. Concerned
			String.class, //Present Location ID
			Object.class, //Present Location
			Integer.class//Rec Id
	};
	
	private void initThis() {
		setColumnIdentifiers(COLUMNS);
		COLUMN_EDITABLE = new boolean[] {
				false, //No.
				false, //Work Item
				false, //UOM
				false, //Quantity
				false, //Lump Sum/Other
				false, //Total Unit Cost
				false, //Total Cost
				false, //Unit ID
				false, //Unit Description
				false, //% Work
				false //rec.
		};
	}
	
	public Class getColumnClass(int columnIndex) {
		return CLASS_TYPES[columnIndex];
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return COLUMN_EDITABLE[columnIndex];
	}
	
	public void clear() {
		FncTables.clearTable(this);
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
		if(editable){
			COLUMN_EDITABLE = new boolean[] {
					false, //No.
					true, //Work Item
					false, //UOM
					true, //Quantity
					true, //Lump Sum/Other
					true, //Total Unit Cost
					true, //Total Cost
					false, //Unit ID
					false, //Unit Description
					false, //% Work
					false, //P.E. No.
					false //rec.
			};
		}else{
			COLUMN_EDITABLE = new boolean[] {
					false, //No.
					false, //Work Item
					false, //UOM
					false, //Quantity
					false, //Lump Sum/Other
					false, //Total Unit Cost
					false, //Total Cost
					false, //Unit ID
					false, //Unit Description
					false, //% Work
					false, //P.E. No.
					false //rec.
			};
		}
	}

}
