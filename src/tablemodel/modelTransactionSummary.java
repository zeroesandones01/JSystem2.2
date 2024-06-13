package tablemodel;

import java.security.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelTransactionSummary extends DefaultTableModel {
		
		private static final long serialVersionUID = 1L;
		
//		public model_AccessMain_Modules(int rowCount, int columnCount) {
//			super(rowCount, columnCount);
//		}
//
//		public model_AccessMain_Modules(Vector columnNames, int rowCount) {
//			super(columnNames, rowCount);
//		}
//
//		public model_AccessMain_Modules(Object[] columnNames, int rowCount) {
//			super(columnNames, rowCount);
//		}
//
//		public model_AccessMain_Modules(Vector data, Vector columnNames) {
//			super(data, columnNames);
//		}
//
//		public model_AccessMain_Modules(Object[][] data, Object[] columnNames) {
//			super(data, columnNames);
//		}
		
		static String [] columns = new String []{
				"Tag",   	  		//0
				"Batch No.",     	//1
				"Requested By",    	//2
				"RPLF No.",		  	//3
				"Status",		  	//4
				"Date Created",		//5
				"Returned Check Number" //6
		};

		public modelTransactionSummary() {
			super(new Object[][]{}, columns);
		}
		
		public Class [] type = new Class[] {
				Boolean.class,     	//0
				String.class,     	//1
				String.class,  	  	//2
				String.class,	  	//3
				String.class,	   	//4
				Timestamp.class,	//5
				String.class		//6
		};
		
		Boolean[] COLUMN_EDITABLE = new Boolean[] {
				true,
				false, 				//batch no.
				false, 				//req by
				false, 				//rplf no
				false, 				//status
				false, 				//date created
				true,				//returned check number
		};
		
		@Override
		public Class getColumnClass(int columnIndex) {
			return type[columnIndex];
		}

		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			return COLUMN_EDITABLE[columnIndex];
		}
		
		public void clear() {
			FncTables.clearTable(this);
		}
	}
