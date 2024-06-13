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
import tablemodel.modelCashCount_unusedReceipt;

public class CashCountSummary_UnusedReceipt extends JPanel {

	private static final long serialVersionUID = 3143397854903836916L;

	private _JScrollPaneMain scrollUnusedRcpt;
	private _JScrollPaneTotal scrollUnusedRcptTotal;
	
	private static modelCashCount_unusedReceipt modelUnusedRcpt;
	private modelCashCount_unusedReceipt modelUnusedRcptTotal;
	
	private _JTableTotal tblUnusedRcptTotal;
	private _JTableMain tblUnusedRcpt;	
	
	private JList rowHeaderUnusedRcpt;
	
	
	public CashCountSummary_UnusedReceipt() {
		initGUI(); 
	}

	public CashCountSummary_UnusedReceipt(LayoutManager layout) {
		super(layout);
		initGUI(); 
	}

	public CashCountSummary_UnusedReceipt(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		
	}

	public CashCountSummary_UnusedReceipt(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		
	}

	public static void setState(Boolean blnRev) {
		modelUnusedRcpt.setEditable(blnRev);
	}
	
	private void initGUI() {
		JPanel panMain = new JPanel(new BorderLayout(5, 5));
		add(panMain, BorderLayout.CENTER);
		{
			{
				scrollUnusedRcpt = new _JScrollPaneMain();
				panMain.add(scrollUnusedRcpt, BorderLayout.CENTER);
				{
					modelUnusedRcpt = new modelCashCount_unusedReceipt();
					tblUnusedRcpt = new _JTableMain(modelUnusedRcpt);
					scrollUnusedRcpt.setViewportView(tblUnusedRcpt);
					
					tblUnusedRcpt.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
					tblUnusedRcpt.getColumnModel().getColumn(0).setMaxWidth(99);
					tblUnusedRcpt.getColumnModel().getColumn(1).setMaxWidth(200);
					tblUnusedRcpt.getColumnModel().getColumn(2).setMaxWidth(100);
					tblUnusedRcpt.getColumnModel().getColumn(3).setMaxWidth(100);
					tblUnusedRcpt.getColumnModel().getColumn(4).setMaxWidth(113);
					tblUnusedRcpt.getColumnModel().getColumn(5).setMaxWidth(113);
					tblUnusedRcpt.getColumnModel().getColumn(6).setMaxWidth(75);

					tblUnusedRcpt.addMouseListener(new MouseAdapter() {
						public void mousePressed(MouseEvent e) {
							if(tblUnusedRcpt.rowAtPoint(e.getPoint()) == -1){tblUnusedRcpt.clearSelection();}
							else{tblUnusedRcpt.setCellSelectionEnabled(true);}

						}
						public void mouseReleased(MouseEvent e) {
							if(tblUnusedRcpt.rowAtPoint(e.getPoint()) == -1){tblUnusedRcpt.clearSelection();}
							else{tblUnusedRcpt.setCellSelectionEnabled(true);}

						}
					});

					tblUnusedRcpt.addPropertyChangeListener("tableCellEditor", new PropertyChangeListener() {
						public void propertyChange(PropertyChangeEvent arg0) {	

							_JTableMain table = (_JTableMain) arg0.getSource();

							Object oldValue = null;
							try {oldValue = ((NumberEditorExt) arg0.getOldValue()).getCellEditorValue();} 
							catch (NullPointerException e) { }

							if(oldValue != null){
								int row = table.getEditingRow();		
								Double check_amt = Double.parseDouble(modelUnusedRcpt.getValueAt(row,1).toString());
								BigDecimal debit_bd = new BigDecimal(check_amt);									
								modelUnusedRcpt.setValueAt(debit_bd, row, 1);									
							}

						}
					});					

				}
			}
		}
	}
	
	private void reload(DefaultTableModel modelMain, DefaultTableModel modelTotal, String branch, Date date) {

		FncTables.clearTable(modelMain); 	
		DefaultListModel listModel = new DefaultListModel(); 

		String sql = "SELECT a.rec_id, TRIM(b.doc_alias) AS receipt_type, a.first_no, a.last_no, \n" +
				"(CASE WHEN a.last_no_used = a.first_no THEN a.first_no WHEN a.last_no_used = 0 THEN a.first_no ELSE a.last_no_used + 1 END) AS first_no_notused, \n" +
				"(CASE WHEN a.last_no_used = a.first_no THEN a.last_no ELSE a.last_no END) AS last_no_unused, a.suffix \n" +
				"FROM cs_receipt_book a \n" +
				"LEFT JOIN mf_doc_details b on a.doc_id = b.doc_id \n" +
				"WHERE A.logged_date::DATE = ( \n" +
				"SELECT logged_date::DATE \n" +
				"FROM cs_receipt_book \n" +
				"WHERE logged_date::DATE <= '"+date+"'::DATE \n" +
				"ORDER BY logged_date::DATE DESC LIMIT 1) \n" +
				"AND A.branch_id = '"+branch+"' \n" +
				"AND A.emp_code = '"+UserInfo.EmployeeCode+"' \n" +
				"AND A.status_id = 'A' \n" +
				"ORDER BY A.logged_date, B.doc_alias, A.first_no DESC, A.status_id";

		System.out.printf("SQL #1: %s", sql);

		pgSelect db = new pgSelect();
		db.select(sql);
		
		if (db.isNotNull()) {
			for (int x=0; x < db.getRowCount(); x++) {
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());				
			}				
		}
	}
	
	public void reload(String branch, Date date) {
		reload(modelUnusedRcpt, modelUnusedRcptTotal, branch, date);
	}
	
	public void insertUnusedReceipt(Integer cash_count, Date date, String company, String branch) {
		pgUpdate dbExec = new pgUpdate(); 
		
		for (int x=0; x<tblUnusedRcpt.getRowCount(); x++) {

			String strSQL = "insert into rf_unused_receipts (trans_no, trans_date, branch_id, doc_id, rec_id, first_no, last_no, first_no_unused, last_no_unused, co_id, status_id, created_by, created_date, suffix) \n" +
				"values (" +
				""+cash_count.toString()+", '"+date.toString()+"'::timestamp, '"+branch+"', \n" + 
				"'"+FncGlobal.GetString("select doc_id from mf_doc_details where doc_alias = '"+tblUnusedRcpt.getValueAt(x, 1).toString()+"'; ")+"', \n" + 
				""+tblUnusedRcpt.getValueAt(x, 0).toString()+", \n" + 
				""+tblUnusedRcpt.getValueAt(x, 2).toString()+", \n" + 
				""+tblUnusedRcpt.getValueAt(x, 3).toString()+", \n" + 
				""+tblUnusedRcpt.getValueAt(x, 4).toString()+", \n" + 
				""+tblUnusedRcpt.getValueAt(x, 5).toString()+", \n" + 
				"'"+company+"', 'A', '"+UserInfo.EmployeeCode+"', now(), '"+tblUnusedRcpt.getValueAt(x, 6).toString()+"'); " ;
			dbExec.executeUpdate(strSQL, true);	
		}

		dbExec.commit(); 
	}
}
