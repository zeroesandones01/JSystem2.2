package tablemodel;

import java.math.BigDecimal;

import javax.swing.table.DefaultTableModel;

import Lookup._JLookup;

public class modelCRBAccountEntries extends DefaultTableModel {

	private boolean editable = false;
	private static final long serialVersionUID = 1L;
	boolean[] COLUMN_EDITABLE;

	public modelCRBAccountEntries() {
		initThis();
	}
	
	static String[] columns = new String[] { 
		
		"Part.", //1
		"Account ID", //2
		"Account Name", //3
		"Debit", //4
		"Credit", //5
		"Remarks", //6
		"Rec. ID"}; //7

	/*public modelCRBAccountEntries() {
		super(new Object[][]{}, columns);
	}*/

	public Class[] types = new Class[] {
		
			String.class, 	//Particulars
			_JLookup.class, //Account ID
			Object.class, 	//Account Name
			BigDecimal.class, //Debit
			BigDecimal.class, //Credit
			Object.class, 	//Remarks
			Integer.class	//Rec. ID
	};

	private void initThis() {
		setColumnIdentifiers(columns);
		COLUMN_EDITABLE = new boolean[] {
				
				false, //Particulars
				false, //Account ID
				false, //Account Name
				false, //Debit
				false, //Credit
				false, //Remarks
				false //Rec. ID
		};
	}
	
	@Override
	public Class getColumnClass(int columnIndex) {
		return types[columnIndex];
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return COLUMN_EDITABLE[columnIndex];
	}
	
	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
		if(editable){
			COLUMN_EDITABLE = new boolean[] {
					
					false, //Particulars
					false, //Account ID
					false, //Account Name
					true, //Debit
					true, //Credit
					true, //Remarks
					false //Rec. ID
			};
		}else{
			COLUMN_EDITABLE = new boolean[] {
					
					false, //Particulars
					false, //Account ID
					false, //Account Name
					false, //Debit
					false, //Credit
					false, //Remarks
					false //Rec. ID
			};
		}
	}

}
