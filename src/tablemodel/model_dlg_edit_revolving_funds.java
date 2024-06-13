package tablemodel;

import javax.swing.table.DefaultTableModel;

public class model_dlg_edit_revolving_funds extends DefaultTableModel {

	private static final long serialVersionUID = -5519564542532367888L;
	public  model_dlg_edit_revolving_funds(){
		initThis();
	}
	
		
		String [] Columns = new String []{
			"Select",
			"Rplf No."
				
		};
		Boolean [] COLUMNS_EDITABLE = new Boolean[]{

				true, 
				false
			};
		Class [] classType = new Class[]{
			Boolean.class,
			String.class
	
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
