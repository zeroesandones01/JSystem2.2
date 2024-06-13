package tablemodel;

import java.util.Date;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import Functions.FncTables;

public class model_tagging_of_csv extends DefaultTableModel {
	String type = "normal";

	public model_tagging_of_csv() {
		initThis();
	}
	
	public model_tagging_of_csv(String card) {
		this.type = card;
		initThis("");
	}

	public model_tagging_of_csv(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public model_tagging_of_csv(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public model_tagging_of_csv(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public model_tagging_of_csv(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public model_tagging_of_csv(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public String[] COLUMNS = new String[] {
			"Actual Date",				//0
			"Request Type",				//1
			"Remarks",					//2
			"Trans Date",				//3
			"Requested By",				//4
			"Requested No.",			//5
	};
	
	public Class[] CLASS_TYPES = new Class[] {
			Date.class,					//0
			String.class,				//1
			String.class,				//2
			Date.class,					//3
			String.class,				//4
			String.class,				//5
	};

	private boolean[] COLUMNS_EDITABLE;

	private void initThis() {
		setColumnIdentifiers(COLUMNS);
		COLUMNS_EDITABLE = new boolean[] {
				false,			//0
				false,			//1
				false,			//2
				false,			//3
				false,			//4
				false,			//5
		};
	}

	public String[] COLUMNS_CARD = new String[] {
			"Requested No.",			//0
			"Trans Date",				//1
			"Actual Date",				//2
			"Request Type",				//3
			"Remarks",					//2
			"Requested By",				//5
	};
	
	public Class[] CLASS_TYPES_CARD = new Class[] {
			String.class,				//0
			Date.class,					//1
			Date.class,					//2
			String.class,				//3
			String.class,				//4
			String.class,				//5
	};

	private boolean[] COLUMNS_EDITABLE_CARD;

	private void initThis(String card) {
		setColumnIdentifiers(COLUMNS_CARD);
		COLUMNS_EDITABLE_CARD = new boolean[] {
				false,			//0
				false,			//1
				false,			//2
				false,			//3
				false,			//4
				false,			//5
		};
	}

	public Class getColumnClass(int columnIndex) {
		if(this.type.equals("CARD")) {
			return CLASS_TYPES_CARD[columnIndex];
		} else {
			return CLASS_TYPES[columnIndex];
		}
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if(this.type.equals("CARD")) {
			return COLUMNS_EDITABLE_CARD[columnIndex];
		} else {
			return COLUMNS_EDITABLE[columnIndex];
		}
	}
	
	//

	public void clear() {
		FncTables.clearTable(this);
	}

}
