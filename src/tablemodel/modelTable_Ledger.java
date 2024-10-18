package tablemodel;


	import java.math.BigDecimal;
	import java.sql.Timestamp;
	import java.util.Vector;
	import javax.swing.table.DefaultTableModel;
	import Functions.FncTables;

	/**
	 * @author Allei Anne Beltran
	 *
	 */
	public class modelTable_Ledger extends DefaultTableModel {

		private static final long serialVersionUID = 1L;

		private boolean editable = false;

		public modelTable_Ledger() {
			initThis();
		}

		public modelTable_Ledger(boolean editable) {
			initThis();
		}

		public modelTable_Ledger(int rowCount, int columnCount) {
			super(rowCount, columnCount);
			initThis();
		}

		public modelTable_Ledger(Vector columnNames, int rowCount) {
			super(columnNames, rowCount);
			initThis();
		}

		public modelTable_Ledger(Object[] columnNames, int rowCount) {
			super(columnNames, rowCount);
			initThis();
		}

		public modelTable_Ledger(Vector data, Vector columnNames) {
			super(data, columnNames);
			initThis();
		}

		public modelTable_Ledger(Object[][] data, Object[] columnNames) {
			super(data, columnNames);
			initThis();
		}

		String[] COLUMNS = new String[] {
				"Actual Pmt Date", // 0
				"Payment Due Date", // 1
				"Receipt No.", // 2
				"Amt. Paid", // 3
				"Other Fees", // 4
				"Reservation",
				"Downpayment",
				"MRI",
				"FIRE",
				"VAT",
				"SOI",
				"SOP",
				"Res DP/Penalty", 
				"Int.",
				"PRIN",
				"Bal",
		};

		Class[] CLASS_TYPES = new Class[] {
				Timestamp.class, // Actual Pmt Date
				Timestamp.class, // Payment Due Date
				String.class, // Receipt No.
				BigDecimal.class, // Amount Paid
				BigDecimal.class, // Proc. Fee
				BigDecimal.class, // RES
				BigDecimal.class, // DP
				BigDecimal.class, // MRI
				BigDecimal.class, // FIRE
				BigDecimal.class, // VAT
				BigDecimal.class, // SOI
				BigDecimal.class, // SOP
				BigDecimal.class, //Penalty
				BigDecimal.class, // Interest
				BigDecimal.class, // Principal
				BigDecimal.class, // Balance
		};

		private void initThis() {
			setColumnIdentifiers(COLUMNS);
		}

		public Class getColumnClass(int columnIndex) {
			return CLASS_TYPES[columnIndex];
		}

		public boolean isCellEditable(int rowIndex, int columnIndex) {
			return false;
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


