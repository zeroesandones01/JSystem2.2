package tablemodel;

import java.security.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelDocStampMortgage extends DefaultTableModel {
		
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
				"Entity ID",     	//1
				"Client",    		//2
				"PBL",		  		//3
				"Unit",				//4		
				"Seq",				//5
				"Tag Date"			//6
		};

		public modelDocStampMortgage() {
			super(new Object[][]{}, columns);
		}
		
		public Class [] type = new Class[] {
				Boolean.class,     	//0
				String.class,     	//1
				String.class,  	  	//2
				String.class,	  	//3
				String.class,	   	//4
				Integer.class,	   	//5
				Timestamp.class	   	//6
		};
		
		Boolean[] COLUMN_EDITABLE = new Boolean[] {
				true,
				false, 				//entity id
				false, 				//entity name
				false, 				//pbl
				false, 				//unit
				false,				//seq
				false 				//date created
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
