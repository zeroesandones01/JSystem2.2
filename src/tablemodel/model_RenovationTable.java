package tablemodel;

import java.sql.Timestamp;

import javax.swing.table.DefaultTableModel;

import components._JInternalFrame;

public class model_RenovationTable extends DefaultTableModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1944025254336064388L;
	public  model_RenovationTable(){
		initThis();
	}
	
		
		String [] Columns = new String []{	
			"SELECT",
			"                  UNIT             ",
			"                       CLIENT                          ",
			"  RENOVATION DATE ",
			"ENTITY ID",
			"PROJECT ID",
			"PBL ID",
			"SEQ. NO."
				
		};
		Boolean [] COLUMNS_EDITABLE = new Boolean[]{
				true,
				false, 
				false, 
				false, 
				false,
				false,
				false
			};
		Class [] classType = new Class[]{
			Boolean.class,
			String.class,
			String.class,
			Timestamp.class,
			String.class,
			String.class,
			String.class,
			Integer.class
		
		};
		private void initThis(){
			setColumnIdentifiers(Columns);
		}
		
		public Class getColumnClass(int columnIndex) {
			return classType[columnIndex];
		}

		public boolean isCellEditable(int rowIndex, int columnIndex) {
			return COLUMNS_EDITABLE[columnIndex];
		}
		
		
	}





