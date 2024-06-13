/**
 * 
 */
package tablemodel;

import java.math.BigDecimal;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

/**
 * @author John Lester Fatallo
 */
public class modelFire_ForIssuancePolicy extends DefaultTableModel {

	
	private static final long serialVersionUID = 944101007373669136L;
	private boolean editable = false;

	public modelFire_ForIssuancePolicy() {
		initThis();
	}

	public modelFire_ForIssuancePolicy(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public modelFire_ForIssuancePolicy(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelFire_ForIssuancePolicy(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public modelFire_ForIssuancePolicy(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public modelFire_ForIssuancePolicy(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}
	
	String [] COLUMNS = new String[]{
			
			"Select", //0
			"Rec. ID", //1
			"Proj. Alias", //2
			"Unit ID", // 3
			"PBL", //5
			"Seq", //5
			"Unit Desc", //6
			"Entity ID", //7
			"Client Name", //8
			"House Model", //9
			"Pmt Stage", //10
			"Amount Insured", //11
			//"Term", //11
			"F & L", //12
			"FE", //13
			"TYP", //14
			"FLD", //15
			"EC", //16
			"RSMD", //17
			"Sub Total Premium", //18
			"*Doc Stamps", //19
			"*EVAT", //20
			"*FST", //21
			"*LGT", //22
			"Total Premium" //23
			
		};
		
		Class [] CLASS_TYPES = new Class[]{
			
			Boolean.class, //Select
			Integer.class, //Rec ID
			String.class, //Proj Alias
			String.class, //Unit ID
			String.class, //PBL 
			Integer.class, //Seq
			String.class, // Unit Desc
			String.class, //Entity ID
			String.class, //Client Name
			String.class, //House Model
			String.class, //Pmt Stage
			BigDecimal.class, //Amt Insured
			//Integer.class, //Term
			BigDecimal.class, //F & L
			BigDecimal.class, //FE
			BigDecimal.class, //TYP
			BigDecimal.class, //FLD
			BigDecimal.class, //EC
			BigDecimal.class, //RSMD
			BigDecimal.class, //Sub Total Premium
			BigDecimal.class, //Doc Stamps
			BigDecimal.class, //EVAT
			BigDecimal.class, //FST
			BigDecimal.class, //LGT
			BigDecimal.class, //Total Premium
			
		};
		
		Boolean [] COLUMNS_EDITABLE = new Boolean[]{
			
			true, //Select
			false, //Rec ID
			false, //Unit ID
			false, //PBL
			false, //Seq
			false, //Unit Desc
			false, //Entity ID
			false, //Client Name
			false, //House Model
			false, //Pmt Stage
			false, //AMt Insured
			//false, //Term
			false, //F & L
			false, //FE
			false, //TYP
			false, //FLD
			false, //EC
			false, //RSMD
			false, //Sub Total Premium
			true, //Doc Stamps
			true, //EVAT
			true, //FST
			true, //LGT
			false //Total Premium
			
		};
		
		private void initThis(){
			setColumnIdentifiers(COLUMNS);
		}
		
		public Class getColumnClass(int columnIndex) {
			return CLASS_TYPES[columnIndex];
		}

		public boolean isCellEditable(int rowIndex, int columnIndex) {
			return COLUMNS_EDITABLE[columnIndex];
		}
		
		public void clear() {
			FncTables.clearTable(this);
		}

		public boolean isEditable() {
			return editable;
		}
		
		public void setEditable(boolean editable) {
			this.editable = editable;
		}

}
