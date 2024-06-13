package tablemodel;


import java.sql.Date;
import java.sql.Timestamp;
import java.util.Vector;


import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class model_HouseRepair  extends DefaultTableModel {

	/**
	 * 
	 */
		private static final long serialVersionUID = -8738810102880371204L;

		public model_HouseRepair() {
			initThis();
		}

		public model_HouseRepair(int rowCount, int columnCount) {
			super(rowCount, columnCount);
		}

		public model_HouseRepair(Vector columnNames, int rowCount) {
			super(columnNames, rowCount);
		}

		public model_HouseRepair(Object[] columnNames, int rowCount) {
			super(columnNames, rowCount);
		}

		public model_HouseRepair(Vector data, Vector columnNames) {
			super(data, columnNames);
		}

		public model_HouseRepair(Object[][] data, Object[] columnNames) {
			super(data, columnNames);
		}

		public String [] COLUMNS = new String[] {
				"Repair No.",
				"     Date     ",
				
				"Amount",
				"          Repair Description          ",
				
				"RPLF No.",
				"     Repair Status     ",
				"         Payee          ",
				
				"         Charged To          ",
				"Charge Amount"
				
		};
		
		public Class [] CLASS_TYPES = new Class[] {
				String.class,
				Date.class,
				
				Integer.class,
				String.class,
				
				String.class,	
				String.class,
				String.class,
				
				String.class,
				Integer.class

		};

		private void initThis() {
			setColumnIdentifiers(COLUMNS);
		}

		public Class getColumnClass(int columnIndex) {
			return CLASS_TYPES[columnIndex];
		}

		public boolean isCellEditable(int rowIndex, int columnIndex) {
			return false;
		}

		
		public void clear() {
			FncTables.clearTable(this);
		}

}
