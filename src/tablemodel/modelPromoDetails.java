package tablemodel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class modelPromoDetails extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8738810102880371204L;
	
	private boolean editable = false;
	boolean[] COLUMN_EDITABLE;
	
	
	public modelPromoDetails() {
		initThis();
	}

	public modelPromoDetails(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public modelPromoDetails(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelPromoDetails(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public modelPromoDetails(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public modelPromoDetails(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			"Promo No.",		// 0
			"Promo Desc." ,		// 1
			"Date From" ,		// 2
			"Date To" ,			// 3
			"Acct. ID", 		// 4
			"Acct. Name", 		// 5
			"Amount", 			// 6
			"Project", 			// 7
			"Frequency" ,		// 8
			"Type" ,			// 9
			"<html><center>Release<html><br><html><center>Group<html>", 	// 10
			"<html><center>Release<html><br><html><center>To<html>", 	// 11
			"Status", 		    // 12
			"Created by", 		// 13
			"<html><center>Created<html><br><html><center>Date<html>", 	// 14
			};

	
	/**
	 * String.class = Alignment.CENTER
	 * Object.class = Alignment.LEFT
	 * BigDecimal.class = Alignment.RIGHT
	 * Integer.class = Alignment.CENTER
	 * Timestamp.class
	 */
	public Class[] CLASS_TYPES = new Class[] {
			String.class, 		//Promo No.
			String.class, 		//Promo Desc.
			Timestamp.class, 	//Date From
			Timestamp.class, 	//Date To
			String.class,		//Acct. ID
			Object.class,		//Acct. Name
			BigDecimal.class,	//Amount
			String.class,		//Project
			String.class,		//Frequency
			String.class,		//Type
			String.class,		//Mode
			String.class,		//Release To
			String.class,		//Status
			Object.class,		//Created by
			Timestamp.class		//Created Date
			
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
		COLUMN_EDITABLE = new boolean[] {
				false, 		//Promo No.
				false, 		//Promo Desc.
				false, 		//Date From
				false, 		//Date To
				false,		//Acct. ID
				false,		//Acct. Name
				false,		//Amount
				false,		//Project
				false,		//Frequency
				false,		//Type
				false,		//Mode
				false,		//Release To
				false,		//Status
				false,		//Created by
				false		//Created Date
		};
	}

	public Class getColumnClass(int columnIndex) {
		return CLASS_TYPES[columnIndex];
	}

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
					false, 		//Promo No.
					false, 		//Promo Desc.
					false, 		//Date From
					false, 		//Date To
					false,		//Acct. ID
					false,		//Acct. Name
					false,		//Amount
					false,		//Project
					false,		//Frequency
					false,		//Type
					false,		//Mode
					false,		//Release To
					false,		//Status
					false,		//Created by
					false		//Created Date
			};
		}else{
			COLUMN_EDITABLE = new boolean[] {
					false, 		//Promo No.
					false, 		//Promo Desc.
					false, 		//Date From
					false, 		//Date To
					false,		//Acct. ID
					false,		//Acct. Name
					false,		//Amount
					false,		//Project
					false,		//Frequency
					false,		//Type
					false,		//Mode
					false,		//Release To
					false,		//Status
					false,		//Created by
					false		//Created Date
			};
		}
	}

	
	
	
}
