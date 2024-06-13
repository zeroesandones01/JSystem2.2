package Accounting.Cashiering;

import java.awt.BorderLayout;
import java.awt.LayoutManager;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
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
import Functions.FncTables;
import Functions.UserInfo;
import components._JScrollPaneMain;
import components._JScrollPaneTotal;
import components._JTableMain;
import components._JTableTotal;
import tablemodel.modelCashDeposit;

public class CashCountSummary_CashCount extends JPanel {

	private static final long serialVersionUID = -143031975482852844L;

	private _JScrollPaneMain scrollCashCount;
	private _JScrollPaneTotal scrollCashCountTotal;	
	
	private static modelCashDeposit modelCashCount; 
	private modelCashDeposit modelCashCountTotal;
	
	private _JTableMain tblCashCount;
	private _JTableTotal tblCashCountTotal;
	
	private JList rowHeaderCashCount;
	
	static NumberFormat nf = new DecimalFormat("###,###,###,##0.00"); 
	
	public CashCountSummary_CashCount() {
		initGUI(); 
	}
	
	public CashCountSummary_CashCount(LayoutManager layout) {
		super(layout);
		initGUI(); 
	}

	public CashCountSummary_CashCount(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		
	}

	public CashCountSummary_CashCount(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		
	}

	public static void setState(Boolean blnRev) {
		modelCashCount.setEditable(blnRev);
	}
	
	private void initGUI() {
		JPanel panMain = new JPanel(new BorderLayout(5, 5));
		add(panMain, BorderLayout.CENTER);
		{
			{
				scrollCashCount = new _JScrollPaneMain();
				panMain.add(scrollCashCount, BorderLayout.CENTER);
				{
					modelCashCount = new modelCashDeposit();
					tblCashCount = new _JTableMain(modelCashCount);
					scrollCashCount.setViewportView(tblCashCount);
					
					tblCashCount.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
					tblCashCount.getColumnModel().getColumn(0).setMaxWidth(150);
					tblCashCount.getColumnModel().getColumn(1).setMaxWidth(150);
					tblCashCount.getColumnModel().getColumn(2).setMaxWidth(150);
					tblCashCount.addMouseListener(new MouseAdapter() {
						public void mousePressed(MouseEvent e) {
							if (tblCashCount.rowAtPoint(e.getPoint())==-1) {
								tblCashCountTotal.clearSelection();
							} else {
								tblCashCount.setCellSelectionEnabled(true);
							}
						}
						
						public void mouseReleased(MouseEvent e) {
							if (tblCashCount.rowAtPoint(e.getPoint())==-1) {
								tblCashCountTotal.clearSelection();
							} else {
								tblCashCount.setCellSelectionEnabled(true);
							}								
						}
					});
					tblCashCount.addPropertyChangeListener("tableCellEditor", new PropertyChangeListener() {
						public void propertyChange(PropertyChangeEvent arg0) {
							_JTableMain table = (_JTableMain) arg0.getSource();
							Object oldValue = null;
							
							try {
								oldValue = ((NumberEditorExt) arg0.getOldValue()).getCellEditorValue();
							} catch (NullPointerException e) {
								
							}

							if (oldValue != null){

								int row = table.getEditingRow();
								int denominationColumn = table.convertColumnIndexToModel(0);
								int amountColumn = table.convertColumnIndexToModel(2);

								BigDecimal denomination = (BigDecimal) table.getValueAt(row, denominationColumn);
								modelCashCount.setValueAt(denomination.multiply(new BigDecimal((Integer)oldValue)), row, amountColumn);
								totalCashCount(modelCashCount, modelCashCountTotal);

							}
						}
					});						

				}
			}
			{
				scrollCashCountTotal = new _JScrollPaneTotal(scrollCashCount);
				panMain.add(scrollCashCountTotal, BorderLayout.SOUTH);
				{
					modelCashCountTotal = new modelCashDeposit();
					modelCashCountTotal.addRow(new Object[] { "Total", null,  new BigDecimal(0.00) });

					tblCashCountTotal = new _JTableTotal(modelCashCountTotal, tblCashCount);
					
					tblCashCountTotal.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
					tblCashCountTotal.getColumnModel().getColumn(0).setMaxWidth(150);
					tblCashCountTotal.getColumnModel().getColumn(1).setMaxWidth(150);
					tblCashCountTotal.getColumnModel().getColumn(2).setMaxWidth(150);
					
					scrollCashCountTotal.setViewportView(tblCashCountTotal);
					((_JTableTotal) tblCashCountTotal).setTotalLabel(0);
				}
			}
		}
	}
	
	public static void totalCashCount(DefaultTableModel modelMain, DefaultTableModel modelTotal) {
		BigDecimal total_cash_amt = new BigDecimal(0.00);
		FncTables.clearTable(modelTotal);

		for (int x=0; x < modelMain.getRowCount(); x++) {
			try {
				total_cash_amt = total_cash_amt.add(((BigDecimal) modelMain.getValueAt(x, 2)));
			} catch (NullPointerException e) {
				total_cash_amt = total_cash_amt.add(new BigDecimal(0.00));
			}
		}
		modelTotal.addRow(new Object[] { "Total" , null, nf.format(total_cash_amt)});
	}
	
	private void reload(DefaultTableModel modelMain, DefaultTableModel modelTotal) {
		FncTables.clearTable(modelMain); 		
		DefaultListModel listModel = new DefaultListModel(); 

		String sql = "select * from view_cashcount_denomination; ";

		pgSelect db = new pgSelect();
		db.select(sql);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());				
			}	

			totalCashCount(modelMain, modelTotal);			
		} 
	}
	
	public void reload() {
		reload(modelCashCount, modelCashCountTotal); 
	}
	
	public void clear() {
		FncTables.clearTable(modelCashCount); 
	}
	
	public void insertCashCount(Integer cash_count, Date date, String company, String branch) {
		pgUpdate dbExec = new pgUpdate(); 
		dbExec.executeUpdate("insert into rf_cash_count_header (trans_no, trans_date, co_id, branch_id, status_id, created_by, date_created)\n" + 
				"values ('"+cash_count.toString()+"'::int, concat('"+date.toString()+"'::date::varchar, ' ', now()::time::varchar)::timestamp, '"+company+"', '"+branch+"', 'A', '"+UserInfo.EmployeeCode+"', now()); ", true);
		dbExec.commit();
		
		dbExec = new pgUpdate();
		for (int x=0; x<modelCashCount.getRowCount(); x++) {
			dbExec.executeUpdate("insert into rf_cash_count_detail (trans_no, denom, quantity, status_id)\n" + 
					"values ('"+cash_count.toString()+"'::int, '"+modelCashCount.getValueAt(x, 0).toString()+"'::numeric(19, 2), '"+modelCashCount.getValueAt(x, 1).toString()+"'::numeric(19, 2), 'A'); ", true);
		}
		dbExec.commit();
	}
}
