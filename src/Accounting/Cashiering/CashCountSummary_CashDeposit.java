package Accounting.Cashiering;

import java.awt.BorderLayout;
import java.awt.LayoutManager;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.util.Date;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.table.NumberEditorExt;

import Database.pgSelect;
import Database.pgUpdate;
import Functions.FncGlobal;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup._JLookupTable;
import components._JScrollPaneMain;
import components._JScrollPaneTotal;
import components._JTableMain;
import components._JTableTotal;
import tablemodel.model_CashCount_check;

public class CashCountSummary_CashDeposit extends JPanel {

	private static final long serialVersionUID = -2664916680644597928L;

	private _JScrollPaneMain scrollCashDeposit;
	private _JScrollPaneTotal scrollCashDepositTotal;
	
	private static model_CashCount_check modelCashDeposit;
	private model_CashCount_check modelCashDepositTotal;
	
	private _JTableTotal tblCashDepositTotal;	
	private static _JTableMain tblCashDeposit;
	
	private JList rowHeaderCashDeposit;
	
	
	public CashCountSummary_CashDeposit() {
		initGUI(); 
	}

	public CashCountSummary_CashDeposit(LayoutManager layout) {
		super(layout);
		initGUI(); 
	}

	public CashCountSummary_CashDeposit(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		
	}

	public CashCountSummary_CashDeposit(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		
	}

	public static void setState(Boolean blnRev) {
		modelCashDeposit.setEditable(blnRev);
	}
	
	private void initGUI() {
		JPanel panMain = new JPanel(new BorderLayout(5, 5));
		add(panMain, BorderLayout.CENTER);
		{
			{
				scrollCashDeposit = new _JScrollPaneMain();
				panMain.add(scrollCashDeposit, BorderLayout.CENTER);
				{
					modelCashDeposit = new model_CashCount_check();

					tblCashDeposit = new _JTableMain(modelCashDeposit);
					scrollCashDeposit.setViewportView(tblCashDeposit);

					tblCashDeposit.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
					tblCashDeposit.getColumnModel().getColumn(0).setMaxWidth(200);
					tblCashDeposit.getColumnModel().getColumn(1).setMaxWidth(150);
					tblCashDeposit.getColumnModel().getColumn(2).setMaxWidth(150);
					tblCashDeposit.getColumnModel().getColumn(3).setMaxWidth(150);

					tblCashDeposit.addMouseListener(new MouseAdapter() {
						public void mousePressed(MouseEvent e) {
							if (tblCashDeposit.rowAtPoint(e.getPoint())==-1) {
								tblCashDepositTotal.clearSelection();
							} else{
								tblCashDeposit.setCellSelectionEnabled(true);
							}

						}
						
						public void mouseReleased(MouseEvent e) {
							if (tblCashDeposit.rowAtPoint(e.getPoint())==-1) {
								tblCashDepositTotal.clearSelection();
							} else {
								tblCashDeposit.setCellSelectionEnabled(true);
							}
						}
						
						public void mouseClicked(MouseEvent e) {
							if ((e.getClickCount()>=2)) {
								clickCashDeposit();
							}
						}
					});

					tblCashDeposit.addPropertyChangeListener("tableCellEditor", new PropertyChangeListener() {
						public void propertyChange(PropertyChangeEvent arg0) {	

							_JTableMain table = (_JTableMain) arg0.getSource();
							Object oldValue = null;
							try {oldValue = ((NumberEditorExt) arg0.getOldValue()).getCellEditorValue();} 
							catch (NullPointerException e) { }

							if(oldValue != null){
								int row = table.getEditingRow();		
								Double check_amt = Double.parseDouble(modelCashDeposit.getValueAt(row,3).toString());
								BigDecimal debit_bd = new BigDecimal(check_amt);									
								modelCashDeposit.setValueAt(debit_bd, row, 3);									
								totalCashDeposit(modelCashDeposit, modelCashDepositTotal);
							}

						}
					});					

				}
			}
			{
				scrollCashDepositTotal = new _JScrollPaneTotal(scrollCashDeposit);
				panMain.add(scrollCashDepositTotal, BorderLayout.SOUTH);
				{
					modelCashDepositTotal = new model_CashCount_check();
					modelCashDepositTotal.addRow(
							new Object[] {
									"Total", 
									null, 
									null, 
									new BigDecimal(0.00)
									}
							);
					
					tblCashDepositTotal = new _JTableTotal(modelCashDepositTotal, tblCashDeposit);
					
					tblCashDepositTotal.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
					tblCashDepositTotal.getColumnModel().getColumn(0).setMaxWidth(200);
					tblCashDepositTotal.getColumnModel().getColumn(1).setMaxWidth(150);
					tblCashDepositTotal.getColumnModel().getColumn(2).setMaxWidth(150);
					tblCashDepositTotal.getColumnModel().getColumn(3).setMaxWidth(150);
					
					scrollCashDepositTotal.setViewportView(tblCashDepositTotal);
					((_JTableTotal) tblCashDepositTotal).setTotalLabel(0);
				}
			}
		}
	}
	
	public static void totalCashDeposit(DefaultTableModel modelMain, DefaultTableModel modelTotal) {
		BigDecimal total_cash_amt = new BigDecimal(0.00);
		FncTables.clearTable(modelTotal);
		
		for(int x=0; x < modelMain.getRowCount(); x++){
			try {
				total_cash_amt = total_cash_amt.add(((BigDecimal) modelMain.getValueAt(x, 3)));
			} catch (NullPointerException e) {
				total_cash_amt = total_cash_amt.add(new BigDecimal(0.00));
			}
		}

		modelTotal.addRow(
			new Object[] {
					"Total", 
					null, 
					null, 
					total_cash_amt
			}
		);
	}
	
	private void createCashDeposit(DefaultTableModel modelMain, DefaultTableModel modelTotal, String company, String branch, Date date) {
		FncTables.clearTable(modelMain); 
		DefaultListModel listModel = new DefaultListModel(); 

		String strSQL = "select * from view_cash_count_CashDeposit('"+company+"', '"+branch+"','"+date+"', '"+UserInfo.EmployeeCode+"')";

		System.out.println();
		System.out.println("strSQL: "+strSQL);
		
		pgSelect db = new pgSelect();
		db.select(strSQL);
		
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());				
			}	

			totalCashDeposit(modelMain, modelTotal);			
		}	
	}
	
	public void reload(String company, String branch, Date date) {
		createCashDeposit(modelCashDeposit, modelCashDepositTotal, company, branch, date);
	}
	
	public void clear() {
		FncTables.clearTable(modelCashDeposit); 
	}
	
	public void insertCashDeposit(Integer cash_count) {
		pgUpdate dbExec = new pgUpdate(); 
		
		String strSQL = ""; 
		String bank_acct = "";
		String proj_id = "";
		String phase = "";
		
		Double cash_dep_amt = 0.00;
		
		for (int x=0; x<tblCashDeposit.getRowCount(); x++) {
			try {
				bank_acct = tblCashDeposit.getValueAt(x, 0).toString();
			} catch (NullPointerException e) {
				bank_acct  = "";
			}
			
			try {
				proj_id = FncGlobal.GetString("select proj_id from mf_project where proj_alias = '"+tblCashDeposit.getValueAt(x, 1).toString()+"'; "); 
			} catch (NullPointerException e) {
				proj_id = "";
			}
			
			try {
				phase = tblCashDeposit.getValueAt(x, 2).toString();
			} catch (NullPointerException e) {
				phase  = "";
			}
			
			try {
				cash_dep_amt = Double.parseDouble(tblCashDeposit.getValueAt(x, 3).toString());
			} catch (NullPointerException e) {
				cash_dep_amt = 0.00;
			}

			strSQL = "insert into rf_cash_deposit_summary (trans_no, bank_acct_no, projcode, phase, amount, status_id, created_by, created_date) \n" +
					"values ("+cash_count+", '"+bank_acct+"', '"+proj_id+"', '"+phase+"', "+cash_dep_amt+", 'A', '"+UserInfo.EmployeeCode+"', now()); ";
			dbExec.executeUpdate(strSQL, false);
		}

		dbExec.commit();
	}
	
	private String getBankAccount(){

		return "select a.bank_acct_id as \"Bank Acct.\", a.acct_desc as \"Bank Desc.\", " +
			"a.bank_acct_no as \"Bank Acct. No.\", coalesce(trim(substr(b.acct_name,15)),'') as \"Account\" " +
			"from mf_bank_account a " +
			"left join mf_boi_chart_of_accounts b on a.acct_id = b.acct_id\r\n" + 
			"order by a.bank_acct_id";
	}
	
	private void clickCashDeposit() {
		int column = tblCashDeposit.getSelectedColumn();
		int row = tblCashDeposit.getSelectedRow();		

		Integer x[] = {0,1};
		
		String sql[] = {
				getBankAccount(),
				""
				};
		
		String lookup_name[] = {
				"Bank Account",
				""
				};				

		if (column == x[column] && modelCashDeposit.isEditable() && sql[column]!="") {  
			_JLookupTable dlg = new _JLookupTable(FncGlobal.homeMDI, null, lookup_name[column], sql[column], false);
			dlg.setLocationRelativeTo(FncGlobal.homeMDI);
			dlg.setVisible(true);

			Object[] data = dlg.getReturnDataSet();
			if (data != null && column == 0) {
				modelCashDeposit.setValueAt(data[3], row, column);
				tblCashDeposit.packAll();
			}	
		}
	}
}
