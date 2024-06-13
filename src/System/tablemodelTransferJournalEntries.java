package System;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.swing.table.DefaultTableModel;

public class tablemodelTransferJournalEntries extends DefaultTableModel {

	private static final long serialVersionUID = 1L;

	static String[] columns = new String[] {
		"Account ID", // 0
		"Account Name", // 1
		"Debit", // 2
		"Credit", // 3
		"Date Created", // 4
		"Created by", // 5
		"Doc #", // 6
		"References", // 7
		"Remarks",// 8
		"Book Type",// 9
		"Book Number",// 10
		"Project"// 11
	};

	public tablemodelTransferJournalEntries() {
		super(new Object[][]{}, columns);
	}

	public Class[] types = new Class[] {
			String.class, // Account ID
			Object.class, // Account Name
			BigDecimal.class, // Debit
			BigDecimal.class, // Credit
			Timestamp.class, // Date Created
			String.class, // Created by
			String.class, // Doc #
			Object.class, // References
			Object.class, // Remarks
			String.class, // Book Type
			String.class, // Book Number
			String.class // Project
	};

	@Override
	public Class getColumnClass(int columnIndex) {
		return types[columnIndex];
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

}
