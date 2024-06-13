package tablemodel;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import Buyers.LegalandLiaisoning.RevolvingFund;
import Functions.FncTables;
import components._JTableMain;

public class model_RevolvingFunds extends DefaultTableModel {


	private static final long serialVersionUID = -8530210315434960039L;
	private boolean editable = false;

	private Map<String, Boolean> editableMap = new HashMap<>();
	//private boolean[][] editable_cells; 
	/*ListSelectionModel lsm = myTable.getSelectionModel();
			int rowSelected = lsm.getMinimumSelectionIndex();*/

	public model_RevolvingFunds (){
		initThis();	
	}

	String[] Columns = new String[]{
			"Select",
			"PCost ID",
			"PCost Desc",
			"Amount",
			"Record ID",
			"Remarks"

	};

	Boolean[] COLUMNS_EDITABLE = new Boolean[]{

			true,
			false,
			false,
			false,
			false,
			false



	};

	//	Boolean[][] COLUMNS_EDITABLE = new Boolean[][]{
	//		
	//			{true,true},
	//			{false,false},
	//			{false,false},
	//			{false,false},
	//			{false,false}

	//	};


	Class[] classType = new Class[]{
			Boolean.class,
			String.class,
			Object.class,
			BigDecimal.class,
			Integer.class,
			String.class


	};

	private void initThis() {
		setColumnIdentifiers(Columns);

	}
	public Class getColumnClass(int columnIndex){
		return classType[columnIndex];
	}

	public boolean isCellEditable(int rowIndex,int columnIndex){

		return COLUMNS_EDITABLE[columnIndex];
	}







//		public boolean isCellEditable(int row, int column) { // custom isCellEditable function
//			return this.COLUMNS_EDITABLE[row][column];
	//	}
	//
	//	public void setCellEditable(int row, int col, boolean value) {
	//		this.COLUMNS_EDITABLE[row][col] = value; // set cell true/false
	//		this.fireTableCellUpdated(row, col);
	//	}

	/*public boolean isCellEditable(int row){
		if(row >3) {
			return true;
		}else {
			return false;  
		}
	}*/

	/*public void setCellEditable(int row, int column, boolean editable) {
	    editableMap.put(row + ":" + column, editable);
	}
>>>>>>> 1.2

	 */
	/*@Override
	public boolean isCellEditable(int row, int column) {
	    return Boolean.FALSE != editableMap.get(row + ":" + column);
	}*/

	/*public boolean isCellEditable(int row, int column) { // custom isCellEditable function
		//this.ge
		if(column == 3) {
			return true;
		}else {
			return false;
		}
	}*/

	/*public void setCellEditable(int row, int col, boolean value) {

		System.out.println("Set CellEditable");

		if(col == 3) {
			this.COLUMNS_EDITABLE[row][col] = value; // set cell true/false
			this.fireTableCellUpdated(row, col);
		}
	}*/

	/*@Override
	public boolean isCellEditable(int row, int column) { // custom isCellEditable function
		return this.COLUMNS_EDITABLE[row][column];
	}*/

	public void clear() {
		FncTables.clearTable(this);
	}


	public boolean isEditable() {
		return editable;
	}
//
//	public void setEditable(boolean editable,int rowIndex) {
//		this.editable = editable;
//		if(editable){
//			COLUMNS_EDITABLE = new Boolean[]{
//					true,
//					false,
//					false,
//					true,
//					false
//
//			}
//		}else{
//			COLUMNS_EDITABLE = new Boolean[]{
//					true,
//					false,
//					false,
//					false,
//					false
//			};
		

		//		public void setEditable(boolean editable,int rowIndex) {
		//			this.editable = editable;
		//			if(editable){
		//				COLUMNS_EDITABLE = new Boolean[][]{
		//					{true,true},
		//					{false,false},
		//					{false,false},
		//					{true,true},
		//					{false,false}
		//				};
		//			}else{
		//				COLUMNS_EDITABLE = new Boolean[][]{
		//					{true,true},
		//					{false,false},
		//					{false,false},
		//					{true,true},
		//					{false,false}
		//				};
		//			}
		//		}


		/*public void setEditable(boolean editable,int rowIndex) {
		this.editable = editable;
		if(editable){
			COLUMNS_EDITABLE = new Boolean[]{
				{true,true}, 
				{false,false}, 
				{false,false}, 
				{editable,false},
				{false,false}
			};
		}else{
			COLUMNS_EDITABLE = new Boolean[]{
				{true,true}, 
				{false,false}, 
				{false,false}, 
				{false,false},
				{false,false}
			};
		}
	}*/

	}


