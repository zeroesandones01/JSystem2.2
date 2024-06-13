package tablemodel;

import javax.swing.table.DefaultTableModel;

public class model_DivHeadDCRF_Checker extends DefaultTableModel {

	private static final long serialVersionUID = -8787822630842155975L;
	public model_DivHeadDCRF_Checker (){
		initthis();
		
	}
	
	String[] Columns = new String[]{
		"DCRF", 
		"Dept.", 
		"Status", 
		"Author"
	};
	
	Boolean[] COLUMNS_EDITABLE = new Boolean[]{
		false,
		false,
		false,
		false
	};
	
	Class[] classType = new Class[]{
			Integer.class, 
			String.class, 
			String.class, 
			Object.class
	};
	
	private void initthis() {
		setColumnIdentifiers(Columns);
	}
	
	public Class getColumnClass(int columnIndex){
		return classType[columnIndex];
		
	}
	
	public boolean isCellEditable(int rowIndex,int columnIndex){
		return COLUMNS_EDITABLE[columnIndex];
		
	}
}
