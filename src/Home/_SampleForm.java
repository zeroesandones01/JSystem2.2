package Home;

import java.math.BigDecimal;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.table.DefaultTableModel;

import Database.pgSelect;
import Functions.FncTables;

public class _SampleForm {
	
	/**
	 * No need to format columns such as timestamp, numeric into to_char.
	 * The table will automatically format it into a readable format.
	 * 
	 * @param model
	 * @param rowHeader
	 */
	public static void displaySampleQuery1(DefaultTableModel model, JList rowHeader) {
		FncTables.clearTable(model);//Code to clear model.
		DefaultListModel listModel = new DefaultListModel();//Creating listModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of listModel into rowHeader.

		String strSQL = "select '0001'::varchar(4) as \"ID\", 'Account 1'::text as \"Description\", 32000.0000 as \"Debit\", NULL::NUMERIC as \"Credit\", now() as \"Date\", 'Debit 1' as \"Remarks\"\n" +
				"union\n" +
				"select '0002'::varchar(4) as \"ID\", 'Account 2'::text as \"Description\", 644687.0000 as \"Debit\", NULL::NUMERIC as \"Credit\", now() as \"Date\", 'Debit 2' as \"Remarks\"\n" +
				"union\n" +
				"select '0003'::varchar(4) as \"ID\", '    Account 3'::text as \"Description\", NULL::NUMERIC as \"Debit\", 676687.0000 as \"Credit\", now() as \"Date\", '    Credit 1' as \"Remarks\"";

		pgSelect db = new pgSelect();
		db.select(strSQL);

		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				
				//You can only use this kind of adding row in model when you're query and model has the same and exact unmber of columns and column types.
				model.addRow(db.getResult()[x]);
				
				//For every row added in model, the table header will also add the row number.
				listModel.addElement(model.getRowCount());
			}
		}
	}
	
	/**
	 * No need to format columns such as timestamp, numeric into to_char.
	 * The table will automatically format it into a readable format.
	 * 
	 * @param model
	 * @param rowHeader
	 */
	public static void displaySampleQuery2(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal) {
		FncTables.clearTable(modelMain);//Code to clear modelMain.
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.
		
		FncTables.clearTable(modelTotal);//Code to clear modelTotal.
		
		String strSQL = "select '0001'::varchar(4) as \"ID\", 'Account 1'::text as \"Description\", 32000.0000 as \"Debit\", NULL::NUMERIC as \"Credit\", now() as \"Date\", 'Debit 1' as \"Remarks\"\n" +
				"union\n" +
				"select '0002'::varchar(4) as \"ID\", 'Account 2'::text as \"Description\", 644687.0000 as \"Debit\", NULL::NUMERIC as \"Credit\", now() as \"Date\", 'Debit 2' as \"Remarks\"\n" +
				"union\n" +
				"select '0003'::varchar(4) as \"ID\", '    Account 3'::text as \"Description\", NULL::NUMERIC as \"Debit\", 676687.0000 as \"Credit\", now() as \"Date\", '    Credit 1' as \"Remarks\"";
	
		pgSelect db = new pgSelect();
		db.select(strSQL);
		
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				
				//You can only use this kind of adding row in model when you're query and model has the same and exact unmber of columns and column types.
				modelMain.addRow(db.getResult()[x]);
				
				//For every row added in model, the table header will also add the row number.
				listModel.addElement(modelMain.getRowCount());
			}
			
			//This code will compute for the total.
			totalEntries(modelMain, modelTotal);
		}else{
			modelTotal.addRow(new Object[] { null, "Total", 0.00, 0.00, null, null });
		}
	}
	
	public static void totalEntries(DefaultTableModel modelMain, DefaultTableModel modelTotal) {
		BigDecimal amountDebit = new BigDecimal(0.00);
		BigDecimal amountCredit = new BigDecimal(0.00);
		
		for(int x=0; x < modelMain.getRowCount(); x++){
			try {
				amountDebit = amountDebit.add(((BigDecimal) modelMain.getValueAt(x, 2)));
			} catch (NullPointerException e1) {
				amountDebit = amountDebit.add(new BigDecimal(0.00));
			}
			try {
				amountCredit = amountCredit.add(((BigDecimal) modelMain.getValueAt(x, 3)));
			} catch (NullPointerException e) {
				amountCredit = amountCredit.add(new BigDecimal(0.00));
			}
		}
		modelTotal.addRow(new Object[] { null, "Total", amountDebit, amountCredit, null, null });
	}

}
