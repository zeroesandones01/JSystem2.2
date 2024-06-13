package tablemodel;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class model_AccessMain_Modules extends DefaultTableModel {
		
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
				"Select",   	  //0
				"Module No.",     //1
				"Module Name",    //2
				"Module",		  //3
				"Parent"		  //4
		};

		public model_AccessMain_Modules() {
			super(new Object[][]{}, columns);
		}
		
		public Class [] type = new Class[] {
				Boolean.class,     //0
				Integer.class,     //1
				String.class,  	   //2
				String.class,	   //3
				String[].class	   //4
		};
		
		Boolean[] COLUMN_EDITABLE = new Boolean[] {
				true,
				false, //module no.
				false, //module name
				false, //module
				false, //parent
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
