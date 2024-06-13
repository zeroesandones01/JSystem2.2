package tablemodel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXFormattedTextField;

import Functions.FncTables;
import Lookup._JLookup;

public class model_SummaryReportOfCollectiblesFromContractors extends DefaultTableModel {

	private static final long serialVersionUID = -3584202739559648681L;
	private boolean editable = false;

	public model_SummaryReportOfCollectiblesFromContractors() {
		initThis();
	}

	public model_SummaryReportOfCollectiblesFromContractors(boolean editable) {
		initThis();
	}

	public model_SummaryReportOfCollectiblesFromContractors(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		initThis();
	}

	public model_SummaryReportOfCollectiblesFromContractors(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public model_SummaryReportOfCollectiblesFromContractors(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		initThis();
	}

	public model_SummaryReportOfCollectiblesFromContractors(Vector data, Vector columnNames) {
		super(data, columnNames);
		initThis();
	}

	public model_SummaryReportOfCollectiblesFromContractors(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		initThis();
	}

	String[] COLUMNS = new String[] {
			"NTP#",
			"Contract No.",
			"% Accomp."
	};

	Class[] CLASS_TYPES = new Class[] {
			String.class, 
			String.class, 
			BigDecimal.class
	};

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
	}

	Boolean[] COLUMNS_EDITABLE = new Boolean[] {
			false,
			false, 
			true
	};

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
		if(editable){
			COLUMNS_EDITABLE = new Boolean[]{
					false,
					false, 
					true
			};
		}else{
			COLUMNS_EDITABLE = new Boolean[]{
					false,
					false, 
					false
			};
		}

	}

}
