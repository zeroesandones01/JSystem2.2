package tablemodel;

import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import Functions.FncTables;

public class model_agentbroker_branch extends DefaultTableModel {

	private static final long serialVersionUID = 8893204839630276481L;
	private boolean editable = false;
	boolean[] COLUMNS_EDITABLE;	
	
	public model_agentbroker_branch() {
		InitGUI(); 
	}

	public model_agentbroker_branch(int rowCount, int columnCount) {
		super(rowCount, columnCount);

	}

	public model_agentbroker_branch(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);

	}

	public model_agentbroker_branch(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);

	}

	public model_agentbroker_branch(Vector data, Vector columnNames) {
		super(data, columnNames);

	}

	public model_agentbroker_branch(Object[][] data, Object[] columnNames) {
		super(data, columnNames);

	}

	
	private void InitGUI() {
		setColumnIdentifiers(COLUMNS);
		
		COLUMNS_EDITABLE = new boolean[] {
				false, 
				false,
				false
			};
	}
	
	public String [] COLUMNS = new String[] {
		"ID",
		"Agent/Broker",
		"Branch"
	};
	
	public Class [] CLASS_TYPES = new Class[] {
		String.class,
		Object.class,
		String.class
	};

	public Class getColumnClass(int columnIndex) {
		return CLASS_TYPES[columnIndex];
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return COLUMNS_EDITABLE[columnIndex];
	}
	
	public boolean isCellEditable(int columnIndex) {
		return COLUMNS_EDITABLE[columnIndex];
	}
	
	public void clear() {
		FncTables.clearTable(this);
	}
	
	public boolean isEditable(){
		return editable;
	}
	
	public void setEditable(boolean editable){
		this.editable = editable;
	}
}
