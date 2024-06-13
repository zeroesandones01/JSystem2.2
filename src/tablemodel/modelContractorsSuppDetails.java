package tablemodel;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class modelContractorsSuppDetails extends DefaultTableModel {
	
	private static final long serialVersionUID = 1L;

	static String[] columns = new String[] {
		"CONTRACTOR ID", //0
		"CONTRACTOR NAME", //0
		"NEW?", //1
		"OTHER", //2
		"LATEST YR OF FS REPORT", //3
		"EQUITY AMT", //4
	};

	public modelContractorsSuppDetails() {
		super(new Object[][]{}, columns);
	}

	public Class[] types = new Class[] {
			String.class, //Contractor ID
			Object.class, //Contractor Name
			String.class, //New
			BigDecimal.class, //OTHER
			String.class, //Latest Yr
			BigDecimal.class, //Equity
	};

	@Override
	public Class getColumnClass(int columnIndex) {
		return types[columnIndex];
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}
	
	public void clear() {
		FncTables.clearTable(this);
	}

}

