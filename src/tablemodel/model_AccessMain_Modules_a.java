package tablemodel;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class model_AccessMain_Modules_a extends DefaultTableModel {
		
		private static final long serialVersionUID = 1L;
		
		static String [] columns = new String []{
				"Module No.",     //0
				"Module Name",    //1
				"Module",		  //2
				"Parent"		  //3
		};

		public model_AccessMain_Modules_a() {
			super(new Object[][]{}, columns);
		}
		
		public Class [] type = new Class[] {
				Integer.class,     //0
				String.class,  	   //1
				String.class,	   //2
				String[].class	   //3
		};
		
		@Override
		public Class getColumnClass(int columnIndex) {
			return type[columnIndex];
		}

		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			return false;
		}
		
		public void clear() {
			FncTables.clearTable(this);
		}
	}
