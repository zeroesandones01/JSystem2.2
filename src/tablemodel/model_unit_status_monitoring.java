package tablemodel;

import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import Functions.FncTables;

public class model_unit_status_monitoring extends DefaultTableModel {
	
	private static final long serialVersionUID = 1L;
	
	public model_unit_status_monitoring() {
		initThis();
	}

	public model_unit_status_monitoring(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public model_unit_status_monitoring(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public model_unit_status_monitoring(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public model_unit_status_monitoring(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public model_unit_status_monitoring(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}
	private void initThis() {
		setColumnIdentifiers(new String[] {
			"Tag", // 0
			"Phase-Block-Lot", // 1
			"Trans Date", // 2
			"Docs #", // 3
			"OR #", // 4
			"JV #", // 5
			"Remarks", // 6
			"PBL ID", // 7
			"Entity ID",
			"Seq No"
	});
	}
	
	/**
	 * String.class = Alignment.CENTER
	 * Object.class = Alignment.LEFT
	 * BigDecimal.class = Alignment.RIGHT
	 * Integer.class = Alignment.CENTER
	 * 
	 */
	Class[] CLASS_TYPES = new Class[] {
			Boolean.class, //Tag
			Object.class, //Phase-Block-Lot
			Timestamp.class, //Trans Date
			BigDecimal.class, //Docs #
			BigDecimal.class, //OR #
			BigDecimal.class, //JV #
			Object.class, //Remarks
			String.class, //Unit ID
			String.class, //Unit ID
			Integer.class //Unit ID
	};
	
	Boolean[] COLUMN_EDITABLE = new Boolean[] {
			true, //Tag
			false, //Phase-Block-Lot
			false, //Trans Date
			false, //Docs #
			false, //OR #
			false, //JV #
			false, //Remarks
			false, //JV #
			false, //Remarks
			false //Unit ID
	};


	public Class getColumnClass(int columnIndex) {
		return CLASS_TYPES[columnIndex];
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return COLUMN_EDITABLE[columnIndex];
	}

	public void clear() {
		FncTables.clearTable(this);
		
	}

}
