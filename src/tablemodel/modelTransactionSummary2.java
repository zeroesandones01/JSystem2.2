package tablemodel;

import java.security.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelTransactionSummary2 extends DefaultTableModel {
		
		private static final long serialVersionUID = 1L;
		
		static String [] columns = new String []{
				"Tag",   	  		//0
				"Batch No.",     	//1
				"Requested By",    	//2
				"RPLF No.",		  	//3
				"Status",		  	//4
				"Code",				//5
				"Date Created",		//6
				"OR Reference",		//7
				"Returned Check Number", //8
				"Rec ID"			//9
		};

		public modelTransactionSummary2() {
			super(new Object[][]{}, columns);
		}
		
		public Class [] type = new Class[] {
				Boolean.class,     	//0
				String.class,     	//1
				String.class,  	  	//2
				String.class,	  	//3
				String.class,	   	//4
				String.class,	   	//5
				Timestamp.class,	//6
				String.class,	   	//7
				String.class,		//8
				String.class		//9
		};
		
		Boolean[] COLUMN_EDITABLE = new Boolean[] {
				true,
				false, 				//batch no.
				false, 				//req by
				false, 				//rplf no
				false, 				//status
				false, 				//code
				false, 				//date created
				true,				//or reference
				true,				//returned check number
				false, 				//rec id
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