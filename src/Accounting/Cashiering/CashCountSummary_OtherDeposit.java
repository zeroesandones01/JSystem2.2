package Accounting.Cashiering;

import java.awt.BorderLayout;
import java.awt.LayoutManager;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;

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
import Functions.FncTables;
import Functions.UserInfo;
import components._JScrollPaneMain;
import components._JScrollPaneTotal;
import components._JTableMain;
import components._JTableTotal;
import tablemodel.modelCashCount_otherDeposit;

public class CashCountSummary_OtherDeposit extends JPanel {

	private static final long serialVersionUID = -3811831267802782545L;

	private _JScrollPaneMain scrollOtherDeposit;
	private _JScrollPaneTotal scrollOtherDepositTotal;
	
	private static modelCashCount_otherDeposit modelOtherDeposit;
	private modelCashCount_otherDeposit modelOtherDepositTotal;
	
	private _JTableTotal tblOtherDepositTotal;
	private _JTableMain tblOtherDeposit;
	
	private JList rowHeaderOtherDeposit;
	
	public CashCountSummary_OtherDeposit() {
		initGUI(); 
	}

	public CashCountSummary_OtherDeposit(LayoutManager layout) {
		super(layout);
		initGUI(); 
	}

	public CashCountSummary_OtherDeposit(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		
	}

	public CashCountSummary_OtherDeposit(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		
	}

	public static void setState(Boolean blnRev) {
		modelOtherDeposit.setEditable(blnRev);
	}
	
	private void initGUI() {
		JPanel panMain = new JPanel(new BorderLayout(5, 5));
		add(panMain, BorderLayout.CENTER);
		{
			{
				scrollOtherDeposit = new _JScrollPaneMain();
				panMain.add(scrollOtherDeposit, BorderLayout.CENTER);
				{
					modelOtherDeposit = new modelCashCount_otherDeposit();
					tblOtherDeposit = new _JTableMain(modelOtherDeposit);
					scrollOtherDeposit.setViewportView(tblOtherDeposit);
					
					tblOtherDeposit.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
					tblOtherDeposit.getColumnModel().getColumn(0).setMaxWidth(300);
					tblOtherDeposit.getColumnModel().getColumn(1).setMaxWidth(200);
					
					tblOtherDeposit.addMouseListener(new MouseAdapter() {
						public void mousePressed(MouseEvent e) {
							if (tblOtherDeposit.rowAtPoint(e.getPoint())==-1) {
								tblOtherDeposit.clearSelection();
							} else {
								tblOtherDeposit.setCellSelectionEnabled(true);
							}
						}
						
						public void mouseReleased(MouseEvent e) {
							if (tblOtherDeposit.rowAtPoint(e.getPoint())==-1) {
								tblOtherDeposit.clearSelection();
							} else {
								tblOtherDeposit.setCellSelectionEnabled(true);
							}

						}
					});

					tblOtherDeposit.addPropertyChangeListener("tableCellEditor", new PropertyChangeListener() {
						public void propertyChange(PropertyChangeEvent arg0) {	

							_JTableMain table = (_JTableMain) arg0.getSource();

							Object oldValue = null;
							try {oldValue = ((NumberEditorExt) arg0.getOldValue()).getCellEditorValue();} 
							catch (NullPointerException e) { }

							if(oldValue != null){
								int row = table.getEditingRow();		
								Double check_amt = Double.parseDouble(modelOtherDeposit.getValueAt(row,1).toString());
								BigDecimal debit_bd = new BigDecimal(check_amt);									
								modelOtherDeposit.setValueAt(debit_bd, row, 1);									
								totalOtherDeposit(modelOtherDeposit, modelOtherDepositTotal);
							}

						}
					});					

				}
			}
			{
				scrollOtherDepositTotal = new _JScrollPaneTotal(scrollOtherDeposit);
				panMain.add(scrollOtherDepositTotal, BorderLayout.SOUTH);
				{
					modelOtherDepositTotal = new modelCashCount_otherDeposit();
					modelOtherDepositTotal.addRow(new Object[] { "Total", new BigDecimal(0.00)  });

					tblOtherDepositTotal = new _JTableTotal(modelOtherDepositTotal, tblOtherDeposit);
					scrollOtherDepositTotal.setViewportView(tblOtherDepositTotal);
					((_JTableTotal) tblOtherDepositTotal).setTotalLabel(0);
				}
			}
		}
	}
	
	private void totalOtherDeposit(DefaultTableModel modelMain, DefaultTableModel modelTotal) {

		BigDecimal total_other_amt = new BigDecimal(0.00);

		FncTables.clearTable(modelTotal);
		for(int x=0; x < modelMain.getRowCount(); x++){
			try {
				total_other_amt = total_other_amt.add(((BigDecimal) modelMain.getValueAt(x, 1)));
			} catch (NullPointerException e) {
				total_other_amt = total_other_amt.add(new BigDecimal(0.00));
			}
		}

		modelTotal.addRow(new Object[] { "Total", total_other_amt});
		tblOtherDeposit.packAll();
		tblOtherDeposit.getColumnModel().getColumn(0).setPreferredWidth(250);
	}
	
	private void reload(DefaultTableModel modelMain, DefaultTableModel modelTotal) {
		FncTables.clearTable(modelMain); 
		DefaultListModel listModel = new DefaultListModel(); 

		String sql = "select * from view_cashcount_otherdeposit; " ;

		pgSelect db = new pgSelect();
		db.select(sql);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());				
			}	

			totalOtherDeposit(modelMain, modelTotal);			
		}	
		
		tblOtherDeposit.packAll();
	}
	
	public void reload() {
		reload(modelOtherDeposit, modelOtherDepositTotal);
	}
	
	public void clear() {
		FncTables.clearTable(modelOtherDeposit); 
	}
	
	public void insertOtherDeposit(Integer cash_count) {
		pgUpdate dbExec = new pgUpdate(); 
		
		String strSQL = ""; 
		String particular = "";
		BigDecimal other_dep_amt = null; 
		
		for (int x=0; x<tblOtherDeposit.getRowCount(); x++) {
			particular = tblOtherDeposit.getValueAt(x,0).toString();	
			other_dep_amt = new BigDecimal(tblOtherDeposit.getValueAt(x, 1).toString());	

			if (other_dep_amt.compareTo(new BigDecimal("0"))==1) {
				strSQL = "insert into rf_other_deposit_summary (trans_no, particular, amount, status_id, created_by, created_date) \n" +
						"values ("+cash_count+", '"+particular+"', "+other_dep_amt+"::numeric(19, 2), 'A', '"+UserInfo.EmployeeCode+"', now()); " ;
				dbExec.executeUpdate(strSQL, true);	
			}	
		}

		try {
			dbExec.commit();	
		} catch (Exception e) {
			
		}
	}
}
