package Accounting.Cashiering;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingWorker;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import org.jdesktop.swingx.table.NumberEditorExt;

import tablemodel.modelCashCount_otherDeposit;
import tablemodel.modelCashCount_unusedReceipt;
import tablemodel.modelCashDeposit;
import tablemodel.model_CashCount_check;
import Accounting.Disbursements.RequestForPayment;
import Database.pgSelect;
import Database.pgUpdate;
import DateChooser._JDateChooser;
import Functions.FncAcounting;
import Functions.FncGlobal;
import Functions.FncReport;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import Lookup._JLookupTable;

import components._JInternalFrame;
import components._JScrollPaneMain;
import components._JScrollPaneTotal;
import components._JTabbedPane;
import components._JTableMain;
import components._JTableTotal;
import components._JTagLabel;

public class CashCountSummary extends _JInternalFrame implements _GUI, ActionListener, MouseListener {

	private static final long serialVersionUID = -3061284418918863916L;
	static String title = "Cash Count";
	static Dimension SIZE = new Dimension(1000, 600);
	
	private JPanel pnlMain;
	private JPanel pnlNorth;
	private JPanel pnlSouth;
	private JPanel pnlNorth_a;
	private JPanel pnlNorth_a1;
	private JPanel pnlNorth_b;
	private JPanel pnlNorth_a3;
	private JPanel pnlNorth_a3_1;
	private JPanel pnlNorth_a3_2;
	private JPanel pnlNorth_a3_fixed;
	private JPanel pnlCashCount;
	private JPanel pnlCashDeposit;
	private JPanel pnlOtherDeposit;
	private JPanel pnlUnusedRcpt;	

	private _JTabbedPane tabCenter;

	private JLabel lblCompany;
	private JLabel lblDepositDate;	
	private JLabel lblTransNo;
	private JLabel lblOfficeBranch;

	private _JScrollPaneMain scrollCashCount;
	private _JScrollPaneMain scrollCashDeposit;
	private _JScrollPaneMain scrollOtherDeposit;	
	private _JScrollPaneMain scrollUnusedRcpt;	

	private _JScrollPaneTotal scrollCashCountTotal;	
	private _JScrollPaneTotal scrollCashDepositTotal;	
	private _JScrollPaneTotal scrollUnusedRcptTotal;
	private _JScrollPaneTotal scrollOtherDepositTotal;

	private modelCashDeposit modelCashCountTotal;
	private modelCashDeposit modelCashCount;	
	private model_CashCount_check modelCashDeposit;
	private model_CashCount_check modelCashDepositTotal;
	private modelCashCount_otherDeposit modelOtherDeposit;
	private modelCashCount_otherDeposit modelOtherDepositTotal;
	private modelCashCount_unusedReceipt modelUnusedRcpt;
	private modelCashCount_unusedReceipt modelUnusedRcptTotal;

	private _JTableTotal tblCashCountTotal;
	private _JTableTotal tblCashDepositTotal;	
	private _JTableTotal tblOtherDepositTotal;
	private _JTableTotal tblUnusedRcptTotal;

	private _JTableMain tblCashCount;	
	private _JTableMain tblCashDeposit;
	private _JTableMain tblOtherDeposit;	
	private _JTableMain tblUnusedRcpt;	

	private _JLookup lookupCompany;
	private _JLookup lookupOfficeBranch;
	private _JLookup lookupTransNo;

	private _JTagLabel tagCompany;
	private _JTagLabel tagBranch;	

	private _JDateChooser dteDeposit;

	private JButton btnCancel;	
	private JButton btnPreview;
	private JButton btnAddNew;
	private JButton btnEdit;
	private JButton btnSave;	

	private JList rowHeaderCashCount;
	private JList rowHeaderCashDeposit;	
	private JList rowHeaderOtherDeposit;
	private JList rowHeaderUnusedRcpt;

	protected static Font dialog11Bold = new Font("DIALOG", Font.BOLD, 11);
	static NumberFormat nf = new DecimalFormat("###,###,###,##0.00"); 
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);	

	String company = "";
	String company_logo;
	String branch = "";
	String branch_id = "";
	String status_id = "";
	String status_name = "";
	Integer trans_no = 0;
	String to_do = "addnew";
	private JPanel pnlNorth_a_sub1;
	private JPanel pnlNorth_a_sub2;
	private _JTagLabel tagStatus;
	private JButton btnPost;	

	public CashCountSummary() {
		super(title, true, true, true, true);
		initGUI();
	}

	public CashCountSummary(String title) {
		super(title);

	}

	public CashCountSummary(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);

	}

	@Override
	public void initGUI() {
		this.setLayout(new BorderLayout(5,5));
		this.setSize(SIZE);
		this.setPreferredSize(new java.awt.Dimension(819, 602));
		this.setBounds(0, 0, 819, 602);

		pnlMain = new JPanel();
		getContentPane().add(pnlMain, BorderLayout.CENTER);
		pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		pnlMain.setLayout(new BorderLayout(5, 5));
		pnlMain.setPreferredSize(new java.awt.Dimension(1134, 645));
		{
			pnlNorth = new JPanel();
			pnlMain.add(pnlNorth, BorderLayout.CENTER);
			pnlNorth.setLayout(new BorderLayout(0,0));
			pnlNorth.setBorder(BorderFactory.createLineBorder(Color.RED));
			pnlNorth.setPreferredSize(new java.awt.Dimension(581, 485));				
			{

			}
			pnlNorth_a = new JPanel(new BorderLayout(0,0));
			pnlNorth.add(pnlNorth_a, BorderLayout.NORTH);			
			pnlNorth_a.setPreferredSize(new java.awt.Dimension(805, 138));	
			{

				pnlNorth_a_sub1 = new JPanel(new BorderLayout(0,0));
				pnlNorth_a.add(pnlNorth_a_sub1, BorderLayout.NORTH);			
				pnlNorth_a_sub1.setPreferredSize(new java.awt.Dimension(805, 69));	
				pnlNorth_a_sub1.setBorder(lineBorder);	

				pnlNorth_a1 = new JPanel(new GridLayout(2, 1, 5, 5));
				pnlNorth_a_sub1.add(pnlNorth_a1, BorderLayout.WEST);	
				pnlNorth_a1.setPreferredSize(new java.awt.Dimension(78, 96));
				pnlNorth_a1.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 5));

				{
					lblCompany = new JLabel("COMPANY", JLabel.TRAILING);
					pnlNorth_a1.add(lblCompany);
					lblCompany.setBounds(8, 11, 62, 12);
					lblCompany.setPreferredSize(new java.awt.Dimension(101, 16));
					lblCompany.setFont(new java.awt.Font("Segoe UI",Font.BOLD,12));
				}	
				{
					lblTransNo = new JLabel("Trans. No.", JLabel.TRAILING);
					pnlNorth_a1.add(lblTransNo);
					lblTransNo.setEnabled(false);	
					lblTransNo.setFont(new java.awt.Font("Segoe UI",Font.BOLD,12));
				}


				pnlNorth_a3 = new JPanel(new BorderLayout(0, 5));
				pnlNorth_a_sub1.add(pnlNorth_a3, BorderLayout.CENTER);	
				pnlNorth_a3.setPreferredSize(new java.awt.Dimension(490, 96));
				pnlNorth_a3.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));

				pnlNorth_a3_fixed = new JPanel(new BorderLayout(0, 5));
				pnlNorth_a3.add(pnlNorth_a3_fixed, BorderLayout.WEST);	
				pnlNorth_a3_fixed.setPreferredSize(new java.awt.Dimension(137, 96));
				pnlNorth_a3_fixed.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));			

				pnlNorth_a3_2 = new JPanel(new GridLayout(2, 1, 5, 5));
				pnlNorth_a3_fixed.add(pnlNorth_a3_2, BorderLayout.WEST);	
				pnlNorth_a3_2.setPreferredSize(new java.awt.Dimension(126, 86));
				pnlNorth_a3_2.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));

				{
					lookupCompany = new _JLookup("", "Company",0,2);
					pnlNorth_a3_2.add(lookupCompany);
					lookupCompany.setLookupSQL(SQL_COMPANY());
					lookupCompany.setReturnColumn(0);
					lookupCompany.addLookupListener(new LookupListener() {
						public void lookupPerformed(LookupEvent event) {
							Object[] data = ((_JLookup)event.getSource()).getDataSet();
							
							if(data != null){

								company = (String) data[1];	
								tagCompany.setTag((String) data[1]);
								company_logo = (String) data[3];							

								String name = (String) data[1];						
								tagCompany.setTag(name);

								lblTransNo.setEnabled(true);
								lookupTransNo.setEnabled(true);
								tagStatus.setEnabled(true);	

								btnAddNew.setEnabled(true);
								
								lookupTransNo.setLookupSQL(getTransNo());
							}
						}
					});
					lookupCompany.addKeyListener(new KeyListener() {

						public void keyTyped(KeyEvent e) {
							if (e.getKeyChar()==KeyEvent.VK_BACK_SPACE) {
								company = "";	
								company_logo = "";

								tagCompany.setTag("");
								tagCompany.setTag("");

								lookupCompany.setValue("");

								lblTransNo.setEnabled(true);
								lookupTransNo.setEnabled(true);
								tagStatus.setEnabled(true);	
								btnAddNew.setEnabled(true);
							}
						}

						public void keyReleased(KeyEvent e) {

						}

						public void keyPressed(KeyEvent e) {

						}
					});
					lookupCompany.setValue("");
				}
				{
					lookupTransNo = new _JLookup(null, "Transaction No.", 0, 2);
					pnlNorth_a3_2.add(lookupTransNo);
					lookupTransNo.setPreferredSize(new java.awt.Dimension(445, 20));
					lookupTransNo.setEnabled(false);	
					lookupTransNo.setLookupSQL(getTransNo());
					lookupTransNo.setReturnColumn(0);
					lookupTransNo.addLookupListener(new LookupListener() {
						public void lookupPerformed(LookupEvent event) {
							Object[] data = ((_JLookup)event.getSource()).getDataSet();
							if(data != null){

								trans_no = (Integer) data[0];
								branch_id = (String) data[2];	
								branch = (String) data[3];	
								Date trans_date = (Date) data[1];	
								status_name = (String) data[4];	

								lookupOfficeBranch.setValue(branch_id);
								lookupCompany.setValue((String) data[5]);
								tagCompany.setText((String) data[6]);

								tagBranch.setTag(branch);
								dteDeposit.setDate(trans_date);
								tagStatus.setTag(status_name);

								createCashList(modelCashCount, rowHeaderCashCount, modelCashCountTotal);	
								displayCashCount();
								displaySavedUnusedReceipt(modelUnusedRcpt, rowHeaderUnusedRcpt, modelUnusedRcptTotal, lookupTransNo.getValue());	
								displayCashDepositList(modelCashDeposit, rowHeaderCashDeposit, modelCashDepositTotal);
								displaySavedOtherDeposit(modelOtherDeposit, rowHeaderOtherDeposit, modelOtherDepositTotal);

								if (status_name.equals("ACTIVE")){enableButtons(false, true, false, true, true, true);}
								else {enableButtons(false, false, false, false, true, true);}
							}
						}
					});
					lookupCompany.setValue("");
				}	

				pnlNorth_a3_1 = new JPanel(new GridLayout(2, 2, 5, 5));
				pnlNorth_a3.add(pnlNorth_a3_1, BorderLayout.CENTER);	
				pnlNorth_a3_1.setPreferredSize(new java.awt.Dimension(430, 96));
				pnlNorth_a3_1.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

				{
					tagCompany = new _JTagLabel("[ ]");
					pnlNorth_a3_1.add(tagCompany);
					tagCompany.setBounds(209, 27, 700, 22);
					tagCompany.setEnabled(true);	
					tagCompany.setPreferredSize(new java.awt.Dimension(27, 33));
				}
				{
					tagStatus = new _JTagLabel("[ ]");
					pnlNorth_a3_1.add(tagStatus);
					tagStatus.setBounds(209, 27, 700, 22);
					tagStatus.setEnabled(false);	
					tagStatus.setPreferredSize(new java.awt.Dimension(27, 33));
				}

			}

			{				
				pnlNorth_a_sub2 = new JPanel(new BorderLayout(0,0));
				pnlNorth_a.add(pnlNorth_a_sub2, BorderLayout.CENTER);			
				pnlNorth_a_sub2.setPreferredSize(new java.awt.Dimension(805, 72));	
				pnlNorth_a_sub2.setBorder(lineBorder);	

				pnlNorth_a1 = new JPanel(new GridLayout(2, 1, 5, 5));
				pnlNorth_a_sub2.add(pnlNorth_a1, BorderLayout.WEST);	
				pnlNorth_a1.setPreferredSize(new java.awt.Dimension(78, 96));
				pnlNorth_a1.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 5));

				{
					lblOfficeBranch = new JLabel("Branch", JLabel.TRAILING);
					pnlNorth_a1.add(lblOfficeBranch);
					lblOfficeBranch.setEnabled(false);	
					lblOfficeBranch.setPreferredSize(new java.awt.Dimension(74, 22));
					lblOfficeBranch.setFont(new java.awt.Font("Segoe UI",Font.BOLD,12));
				}				
				{
					lblDepositDate = new JLabel("Trans. Date", JLabel.TRAILING);
					pnlNorth_a1.add(lblDepositDate);
					lblDepositDate.setEnabled(false);	
					lblDepositDate.setFont(new java.awt.Font("Segoe UI",Font.BOLD,12));
				}


				pnlNorth_a3 = new JPanel(new BorderLayout(0, 5));
				pnlNorth_a_sub2.add(pnlNorth_a3, BorderLayout.CENTER);	
				pnlNorth_a3.setPreferredSize(new java.awt.Dimension(490, 96));
				pnlNorth_a3.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));

				pnlNorth_a3_fixed = new JPanel(new BorderLayout(0, 5));
				pnlNorth_a3.add(pnlNorth_a3_fixed, BorderLayout.WEST);	
				pnlNorth_a3_fixed.setPreferredSize(new java.awt.Dimension(137, 96));
				pnlNorth_a3_fixed.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));			

				pnlNorth_a3_2 = new JPanel(new GridLayout(2, 1, 5, 5));
				pnlNorth_a3_fixed.add(pnlNorth_a3_2, BorderLayout.WEST);	
				pnlNorth_a3_2.setPreferredSize(new java.awt.Dimension(126, 86));
				pnlNorth_a3_2.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));

				{
					lookupOfficeBranch = new _JLookup(null, "Phase", 0, 2);
					pnlNorth_a3_2.add(lookupOfficeBranch);
					lookupOfficeBranch.setPreferredSize(new java.awt.Dimension(445, 20));
					lookupOfficeBranch.setEnabled(false);	
					lookupOfficeBranch.setLookupSQL(SQL_OFFICE_BRANCH());
					lookupOfficeBranch.addLookupListener(new LookupListener() {
						public void lookupPerformed(LookupEvent event) {
							Object[] data = ((_JLookup)event.getSource()).getDataSet();
							if(data != null){

								branch_id = (String) data[0];		
								branch = (String) data[1];						
								tagBranch.setTag(branch);

								KEYBOARD_MANAGER.focusNextComponent();

								createUnusedReceipt(modelUnusedRcpt, rowHeaderUnusedRcpt, modelUnusedRcptTotal);
								createCashDeposit(modelCashDeposit, rowHeaderCashDeposit, modelCashDepositTotal);	
								createUnusedReceipt(modelUnusedRcpt, rowHeaderUnusedRcpt, modelUnusedRcptTotal);	

							}
						}
					});
				}			
				{												
					dteDeposit = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
					pnlNorth_a3_2.add(dteDeposit, BorderLayout.CENTER);
					dteDeposit.setDate(null);
					dteDeposit.setEnabled(false);	
					dteDeposit.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
					dteDeposit.setPreferredSize(new java.awt.Dimension(248, 38));
				}	



				pnlNorth_a3_1 = new JPanel(new GridLayout(2, 2, 5, 5));
				pnlNorth_a3.add(pnlNorth_a3_1, BorderLayout.CENTER);	
				pnlNorth_a3_1.setPreferredSize(new java.awt.Dimension(430, 96));
				pnlNorth_a3_1.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

				{
					tagBranch = new _JTagLabel("[ ]");
					pnlNorth_a3_1.add(tagBranch);
					tagBranch.setBounds(209, 27, 700, 22);
					tagBranch.setEnabled(false);	
					tagBranch.setPreferredSize(new java.awt.Dimension(27, 33));
				}

			}

			//end of header

			pnlNorth_b = new JPanel(new BorderLayout(0, 0));
			pnlNorth.add(pnlNorth_b, BorderLayout.CENTER);				
			pnlNorth_b.setPreferredSize(new java.awt.Dimension(1119, 185));
			pnlNorth_b.setBorder(lineBorder);	

			tabCenter = new _JTabbedPane();
			pnlNorth_b.add(tabCenter, BorderLayout.CENTER);			
			tabCenter.setPreferredSize(new java.awt.Dimension(1192, 433));

			{
				pnlCashCount = new JPanel(new BorderLayout());
				tabCenter.addTab(" Cash Count ", null, pnlCashCount, null);
				pnlCashCount.setPreferredSize(new java.awt.Dimension(1183, 365));

				{
					scrollCashCount = new _JScrollPaneMain();
					pnlCashCount.add(scrollCashCount, BorderLayout.CENTER);
					{
						modelCashCount = new modelCashDeposit();

						tblCashCount = new _JTableMain(modelCashCount);
						scrollCashCount.setViewportView(tblCashCount);
						//tblCashCount.addMouseListener(this);						

						tblCashCount.addMouseListener(new MouseAdapter() {
							public void mousePressed(MouseEvent e) {
								if(tblCashCount.rowAtPoint(e.getPoint()) == -1){tblCashCountTotal.clearSelection();}
								else{tblCashCount.setCellSelectionEnabled(true);}
							}
							public void mouseReleased(MouseEvent e) {
								if(tblCashCount.rowAtPoint(e.getPoint()) == -1){tblCashCountTotal.clearSelection();}
								else{tblCashCount.setCellSelectionEnabled(true);}								
							}
						});

						tblCashCount.addPropertyChangeListener("tableCellEditor", new PropertyChangeListener() {
							public void propertyChange(PropertyChangeEvent arg0) {
								_JTableMain table = (_JTableMain) arg0.getSource();

								Object oldValue = null;
								try {
									oldValue = ((NumberEditorExt) arg0.getOldValue()).getCellEditorValue();
								} catch (NullPointerException e) { }

								if(oldValue != null){

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
					{
						rowHeaderCashCount = tblCashCount.getRowHeader22();
						scrollCashCount.setRowHeaderView(rowHeaderCashCount);
						scrollCashCount.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
					}
				}
				{
					scrollCashCountTotal = new _JScrollPaneTotal(scrollCashCount);
					pnlCashCount.add(scrollCashCountTotal, BorderLayout.SOUTH);
					{
						modelCashCountTotal = new modelCashDeposit();
						modelCashCountTotal.addRow(new Object[] { "Total", null,  new BigDecimal(0.00) });

						tblCashCountTotal = new _JTableTotal(modelCashCountTotal, tblCashCount);
						scrollCashCountTotal.setViewportView(tblCashCountTotal);
						tblCashCountTotal.setFont(dialog11Bold);
						((_JTableTotal) tblCashCountTotal).setTotalLabel(0);
					}
				}
			}
			{
				pnlCashDeposit = new JPanel(new BorderLayout());
				tabCenter.addTab("Cash Deposits", null, pnlCashDeposit, null);
				pnlCashDeposit.setPreferredSize(new java.awt.Dimension(1183, 365));

				{
					scrollCashDeposit = new _JScrollPaneMain();
					pnlCashDeposit.add(scrollCashDeposit, BorderLayout.CENTER);
					{
						modelCashDeposit = new model_CashCount_check();

						tblCashDeposit = new _JTableMain(modelCashDeposit);
						scrollCashDeposit.setViewportView(tblCashDeposit);
						tblCashDeposit.addMouseListener(this);
						tblCashDeposit.getColumnModel().getColumn(0).setPreferredWidth(250);

						tblCashDeposit.addMouseListener(new MouseAdapter() {
							public void mousePressed(MouseEvent e) {
								if(tblCashDeposit.rowAtPoint(e.getPoint()) == -1){tblCashDepositTotal.clearSelection();}
								else{tblCashDeposit.setCellSelectionEnabled(true);}

							}
							public void mouseReleased(MouseEvent e) {
								if(tblCashDeposit.rowAtPoint(e.getPoint()) == -1){tblCashDepositTotal.clearSelection();}
								else{tblCashDeposit.setCellSelectionEnabled(true);}

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
					{
						rowHeaderCashDeposit = tblCashDeposit.getRowHeader22();
						scrollCashDeposit.setRowHeaderView(rowHeaderCashDeposit);
						scrollCashDeposit.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
					}
				}
				{
					scrollCashDepositTotal = new _JScrollPaneTotal(scrollCashDeposit);
					pnlCashDeposit.add(scrollCashDepositTotal, BorderLayout.SOUTH);
					{
						modelCashDepositTotal = new model_CashCount_check();
						modelCashDepositTotal.addRow(new Object[] { "Total", new BigDecimal(0.00)  });

						tblCashDepositTotal = new _JTableTotal(modelCashDepositTotal, tblCashDeposit);
						scrollCashDepositTotal.setViewportView(tblCashDepositTotal);
						tblCashDepositTotal.setFont(dialog11Bold);
						((_JTableTotal) tblCashDepositTotal).setTotalLabel(0);
					}
				}
			}		
			{
				pnlOtherDeposit = new JPanel(new BorderLayout());
				tabCenter.addTab("Other Deposits", null, pnlOtherDeposit, null);
				pnlOtherDeposit.setPreferredSize(new java.awt.Dimension(1183, 365));

				{
					scrollOtherDeposit = new _JScrollPaneMain();
					pnlOtherDeposit.add(scrollOtherDeposit, BorderLayout.CENTER);
					{
						modelOtherDeposit = new modelCashCount_otherDeposit();

						tblOtherDeposit = new _JTableMain(modelOtherDeposit);
						scrollOtherDeposit.setViewportView(tblOtherDeposit);
						tblOtherDeposit.getColumnModel().getColumn(0).setPreferredWidth(250);
						tblOtherDeposit.addMouseListener(new MouseAdapter() {
							public void mousePressed(MouseEvent e) {
								if(tblOtherDeposit.rowAtPoint(e.getPoint()) == -1){tblOtherDeposit.clearSelection();}
								else{tblOtherDeposit.setCellSelectionEnabled(true);}

							}
							public void mouseReleased(MouseEvent e) {
								if(tblOtherDeposit.rowAtPoint(e.getPoint()) == -1){tblOtherDeposit.clearSelection();}
								else{tblOtherDeposit.setCellSelectionEnabled(true);}

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
					{
						rowHeaderOtherDeposit = tblOtherDeposit.getRowHeader22();
						scrollOtherDeposit.setRowHeaderView(rowHeaderOtherDeposit);
						scrollOtherDeposit.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
					}
				}
				{
					scrollOtherDepositTotal = new _JScrollPaneTotal(scrollOtherDeposit);
					pnlOtherDeposit.add(scrollOtherDepositTotal, BorderLayout.SOUTH);
					{
						modelOtherDepositTotal = new modelCashCount_otherDeposit();
						modelOtherDepositTotal.addRow(new Object[] { "Total", new BigDecimal(0.00)  });

						tblOtherDepositTotal = new _JTableTotal(modelOtherDepositTotal, tblOtherDeposit);
						scrollOtherDepositTotal.setViewportView(tblOtherDepositTotal);
						tblOtherDepositTotal.setFont(dialog11Bold);
						((_JTableTotal) tblOtherDepositTotal).setTotalLabel(0);
					}
				}
			}
			{
				pnlUnusedRcpt = new JPanel(new BorderLayout());
				tabCenter.addTab("Unused Receipts", null, pnlUnusedRcpt, null);
				pnlUnusedRcpt.setPreferredSize(new java.awt.Dimension(1183, 365));
				pnlUnusedRcpt.setBorder(new EmptyBorder(5, 5, 5, 5));
				{
					scrollUnusedRcpt = new _JScrollPaneMain();
					pnlUnusedRcpt.add(scrollUnusedRcpt, BorderLayout.CENTER);
					{
						modelUnusedRcpt = new modelCashCount_unusedReceipt();

						tblUnusedRcpt = new _JTableMain(modelUnusedRcpt);
						scrollUnusedRcpt.setViewportView(tblUnusedRcpt);
						//tblUnusedRcpt.addMouseListener(this);

						tblUnusedRcpt.setAutoResizeMode(tblUnusedRcpt.AUTO_RESIZE_ALL_COLUMNS);
						tblUnusedRcpt.getColumnModel().getColumn(0).setMaxWidth(75);
						tblUnusedRcpt.getColumnModel().getColumn(1).setMaxWidth(100);
						tblUnusedRcpt.getColumnModel().getColumn(2).setMaxWidth(100);
						tblUnusedRcpt.getColumnModel().getColumn(3).setMaxWidth(100);
						tblUnusedRcpt.getColumnModel().getColumn(4).setMaxWidth(100);
						tblUnusedRcpt.getColumnModel().getColumn(5).setMaxWidth(100);
						tblUnusedRcpt.getColumnModel().getColumn(6).setMaxWidth(75);
						tblUnusedRcpt.getColumnModel().getColumn(7).setMaxWidth(75);

						tblUnusedRcpt.hideColumns("co_id");

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
									totalCashDeposit(modelUnusedRcpt, modelUnusedRcptTotal);
								}

							}
						});					

					}
					{
						rowHeaderUnusedRcpt = tblUnusedRcpt.getRowHeader22();
						scrollUnusedRcpt.setRowHeaderView(rowHeaderUnusedRcpt);
						scrollUnusedRcpt.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
					}
				}
				{
					scrollUnusedRcptTotal = new _JScrollPaneTotal(scrollUnusedRcpt);
					pnlUnusedRcpt.add(scrollUnusedRcptTotal, BorderLayout.SOUTH);
					{
						modelUnusedRcptTotal = new modelCashCount_unusedReceipt();
						modelUnusedRcptTotal.addRow(new Object[] { "Total", new BigDecimal(0.00)  });

						tblUnusedRcptTotal = new _JTableTotal(modelUnusedRcptTotal, tblUnusedRcpt);
						scrollUnusedRcptTotal.setViewportView(tblUnusedRcptTotal);
						tblUnusedRcptTotal.setFont(dialog11Bold);
						((_JTableTotal) tblUnusedRcptTotal).setTotalLabel(0);
					}
				}
			}
		}
		{
			pnlSouth = new JPanel();
			pnlMain.add(pnlSouth, BorderLayout.SOUTH);
			pnlSouth.setLayout(new BorderLayout());
			pnlSouth.setBorder(BorderFactory.createLineBorder(Color.GRAY));
			pnlSouth.setPreferredSize(new Dimension(55, 30));
			{
				JPanel pnlSouthCenter = new JPanel(new GridLayout(1, 2, 5, 5));
				pnlSouth.add(pnlSouthCenter, BorderLayout.CENTER);
				pnlSouthCenter.setPreferredSize(new Dimension(1000, 30));

				{
					btnAddNew = new JButton("Add New");
					pnlSouthCenter.add(btnAddNew);
					btnAddNew.addActionListener(this);
					btnAddNew.setEnabled(false);
					btnAddNew.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {

							if(FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "7")==true ) {
								
								prime(); 
								
								lblTransNo.setEnabled(false);
								lookupTransNo.setEnabled(false);
								lookupTransNo.setValue("");
								tagStatus.setEnabled(false);	

								lblOfficeBranch.setEnabled(true);	
								lookupOfficeBranch.setEnabled(true);	
								lookupOfficeBranch.setValue("");
								tagBranch.setEnabled(true);	
								tagBranch.setTag("");

								enableFields(true);
								enableButtons(false, false, true, false, false, true);
								createCashList(modelCashCount, rowHeaderCashCount, modelCashCountTotal);								
								createOtherDepositList(modelOtherDeposit, rowHeaderOtherDeposit, modelOtherDepositTotal);	
								//createCashDeposit(modelCashDeposit, rowHeaderCashDeposit, modelCashDepositTotal);	// this will proceed once Branch is selected
								//createUnusedReceipt(modelUnusedRcpt, rowHeaderUnusedRcpt, modelUnusedRcptTotal);	// this will proceed once Branch is selected

								modelCashCount.setEditable(true);
								modelCashDeposit.setEditable(true);
								modelOtherDeposit.setEditable(true);
								to_do = "addnew";
							} else if (FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "7")==false) {
								JOptionPane.showMessageDialog(getContentPane(),"Sorry, you are not authorized to create a new Cash Count summary. \n"
									+ "Please ask for an access from your department head.","Information",JOptionPane.INFORMATION_MESSAGE);
							}				

						}
					});
				}
				{
					btnEdit = new JButton("Edit");
					pnlSouthCenter.add(btnEdit);
					btnEdit.addActionListener(this);
					btnEdit.setEnabled(false);
					btnEdit.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {

							if(FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "7")==true )
							{
								lblTransNo.setEnabled(false);
								lookupTransNo.setEnabled(false);
								enableFields(true);
								enableButtons(false, false, true, false, false, true);														
								modelCashCount.setEditable(true);
								modelCashDeposit.setEditable(true);
								modelOtherDeposit.setEditable(true);
								to_do = "edit";
							}
							else if(FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "7")==false ) 
							{ JOptionPane.showMessageDialog(getContentPane(),"Sorry, you are not authorized to edit Cash Count summary. \n"
									+ "Please ask for an access from your department head.","Information",JOptionPane.INFORMATION_MESSAGE); }				

						}
					});
				}
				{
					btnSave = new JButton("Save");
					pnlSouthCenter.add(btnSave);
					btnSave.addActionListener(this);
					btnSave.setEnabled(false);
					btnSave.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {

							if (FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "7")==true ) {
								if (to_do.equals("addnew")) {
									saveCashCount();	
								} else {
									System.out.println("Edit cash count");
									editCashCount();
								}
							} else if (FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "7")==false) {
								JOptionPane.showMessageDialog(getContentPane(),"Sorry, you are not authorized to save Cash Count summary. \n"
										+ "Please ask for an access from your department head.","Information",JOptionPane.INFORMATION_MESSAGE); }				

						}
					});
				}
				{
					btnPost = new JButton("Post");
					pnlSouthCenter.add(btnPost);
					btnPost.addActionListener(this);
					btnPost.setEnabled(false);
					btnPost.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) { 					
							postCashCount();
						}});
				}
				{
					btnPreview = new JButton("Preview");
					pnlSouthCenter.add(btnPreview);
					btnPreview.setActionCommand("Preview");
					btnPreview.addActionListener(this);
					btnPreview.setEnabled(false);
				}
				{
					btnCancel = new JButton("Cancel");
					pnlSouthCenter.add(btnCancel);
					btnCancel.setActionCommand("Cancel");
					btnCancel.addActionListener(this);
					btnCancel.setEnabled(false);
					btnCancel.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {

							if (btnSave.isEnabled()==true)
							{
								if (JOptionPane.showConfirmDialog(getContentPane(), "Cancel unsaved data?", "Confirmation", 
										JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
									lblTransNo.setEnabled(true);
									lookupTransNo.setEnabled(true);
									enableButtons(true, false, false, false, false, false);
									refresh_tables();
									refreshFields();
									enableFields(false);
									modelCashCount.setEditable(false);
									modelCashDeposit.setEditable(false);
									modelOtherDeposit.setEditable(false);

									lblOfficeBranch.setEnabled(false);	
									lookupOfficeBranch.setEnabled(false);	
									lookupOfficeBranch.setValue("");
									tagBranch.setEnabled(false);	
									tagStatus.setTag("");

								}
								else
								{

								}
							}
							else 
							{
								lblTransNo.setEnabled(true);
								lookupTransNo.setEnabled(true);
								enableButtons(true, false, false, false, false, false);
								refresh_tables();
								refreshFields();
								enableFields(false);
								modelCashCount.setEditable(false);
								modelCashDeposit.setEditable(false);
								modelOtherDeposit.setEditable(false);

								lblOfficeBranch.setEnabled(false);	
								lookupOfficeBranch.setEnabled(false);	
								lookupOfficeBranch.setValue("");
								tagBranch.setEnabled(false);	
								tagStatus.setTag("");
							}

						}	
					});
				}
			}
		}

		initialize_comp();
	}

	private void createCashList(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal) {
		FncTables.clearTable(modelMain); 		
		DefaultListModel listModel = new DefaultListModel(); 
		rowHeader.setModel(listModel); 

		String sql = "select * \n" + 
				"from view_cashcount_denomination; ";

		pgSelect db = new pgSelect();
		db.select(sql);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());				
			}	

			totalCashCount(modelMain, modelTotal);			
		}		

		tblCashCount.getColumnModel().getColumn(0).setPreferredWidth(200);
		tblCashCount.getColumnModel().getColumn(1).setPreferredWidth(100);
		tblCashCount.getColumnModel().getColumn(2).setPreferredWidth(100);
		adjustRowHeight(tblCashCount);
	}		

	private void createCashDeposit(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal) {
		FncTables.clearTable(modelMain); 
		DefaultListModel listModel = new DefaultListModel(); 
		rowHeader.setModel(listModel); 

		String strCo;  

		try {
			strCo = (lookupCompany.getValue()==null)?"":lookupCompany.getValue();
		} catch (NullPointerException e) {
			strCo = ""; 	
		}

		String sql = "select * from view_cash_count_CashDeposit('"+strCo+"', '"+branch_id+"','"+dateFormat.format(dteDeposit.getDate())+"', '"+UserInfo.EmployeeCode+"')";
		System.out.printf("SQL #1: %s", sql);
		FncSystem.out("Display SQL for cash deposit", sql);

		pgSelect db = new pgSelect();
		db.select(sql);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());				
			}	

			totalCashDeposit(modelMain, modelTotal);			
		}	
		adjustRowHeight(tblCashDeposit);
		tblCashDeposit.packAll();		
		tblCashDeposit.getColumnModel().getColumn(0).setPreferredWidth(250);

	}	

	private void createOtherDepositList(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal) {

		FncTables.clearTable(modelMain); 		
		DefaultListModel listModel = new DefaultListModel(); 
		rowHeader.setModel(listModel); 

		String sql = 			
				"select 'SEPARATE DEPOSIT', 0.00   union all " +
						"select 'PCF REPLENISHMENT', 0.00 union all " +
						"select 'TRIPPING REPLENISHMENT', 0.00 union all " +
						"select 'SUNDAY REVOLVING FUND', 0.00 union all " +
						"select 'AFTER CUT-OFF COLLECTIONS', 0.00 " ;

		pgSelect db = new pgSelect();
		db.select(sql);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());				
			}	

			totalOtherDeposit(modelMain, modelTotal);			
		}	
		adjustRowHeight(tblOtherDeposit);
		tblOtherDeposit.packAll();
		tblOtherDeposit.getColumnModel().getColumn(0).setPreferredWidth(250);
	}

	private void createUnusedReceipt(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal) {//ok

		FncTables.clearTable(modelMain); 	
		DefaultListModel listModel = new DefaultListModel(); 
		rowHeader.setModel(listModel); 

		String strSQL = "select a.rec_id, trim(b.doc_alias) as receipt_type, a.first_no, a.last_no, \n" + 
				"(case when a.last_no_used = a.first_no then a.first_no when a.last_no_used = 0 then a.first_no else a.last_no_used + 1 end) as first_no_notused, \n" + 
				"(case when a.last_no_used = a.first_no then a.last_no else a.last_no end) as last_no_unused, a.suffix, c.company_alias, a.co_id \n" + 
				"from cs_receipt_book a \n" + 
				"left join mf_doc_details b on a.doc_id = b.doc_id \n" + 
				"inner join mf_company c on a.co_id = c.co_id \n" +
				"where (a.co_id = '"+lookupCompany.getValue()+"' or '"+lookupCompany.getValue()+"' = '') \n" + 
				"and a.logged_date::DATE = \n" + 
				"( \n" + 
				"	select logged_date::DATE \n" + 
				"	from cs_receipt_book \n" + 
				"	where logged_date::DATE <= '"+dteDeposit.getDate().toString()+"'::date \n" + 
				"	order by logged_date::DATE desc limit 1 \n" + 
				") \n" + 
				"and a.branch_id = '"+lookupOfficeBranch.getText()+"' \n" + 
				"and a.emp_code = '"+UserInfo.EmployeeCode+"' \n" + 
				"and a.status_id = 'A' \n" + 
				"order by a.logged_date, b.doc_alias, a.first_no desc, a.status_id"; 

		System.out.println(strSQL);

		pgSelect db = new pgSelect();
		db.select(strSQL);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());				
			}				
		}		

		else {
			modelUnusedRcptTotal = new modelCashCount_unusedReceipt();
			modelUnusedRcptTotal.addRow(new Object[] { "Total", new BigDecimal(0.00)  });

			tblUnusedRcptTotal = new _JTableTotal(modelUnusedRcptTotal, tblUnusedRcpt);
			scrollUnusedRcptTotal.setViewportView(tblUnusedRcptTotal);
			tblUnusedRcptTotal.setFont(dialog11Bold);
			((_JTableTotal) tblUnusedRcptTotal).setTotalLabel(0);
		}

		modelUnusedRcptTotal.setValueAt(tblUnusedRcpt.getRowCount(), 0, 1);
		adjustRowHeight(tblUnusedRcpt);

	}	

	private void displaySavedUnusedReceipt(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal, String strTransNo) {

		FncTables.clearTable(modelMain);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		String sql = "select a.rec_id, trim(b.doc_alias) as receipt_type, \n" + 
				"a.first_no, a.last_no, a.first_no_unused, a.last_no_unused, a.suffix, c.company_alias, a.co_id \n" + 
				"from rf_unused_receipts a \n" + 
				"left join mf_doc_details b on a.doc_id = b.doc_id \n" +
				"inner join mf_company c on a.co_id = c.co_id \n" +
				"where a.trans_no = "+trans_no+
				"and a.status_id = 'A'  \n" ;

		System.out.printf("SQL #1: %s", sql);

		pgSelect db = new pgSelect();
		db.select(sql);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());				
			}				
		}		

		else {
			modelUnusedRcptTotal = new modelCashCount_unusedReceipt();
			modelUnusedRcptTotal.addRow(new Object[] { "Total", new BigDecimal(0.00)  });

			tblUnusedRcptTotal = new _JTableTotal(modelUnusedRcptTotal, tblUnusedRcpt);
			scrollUnusedRcptTotal.setViewportView(tblUnusedRcptTotal);
			tblUnusedRcptTotal.setFont(dialog11Bold);
			((_JTableTotal) tblUnusedRcptTotal).setTotalLabel(0);
		}

		adjustRowHeight(tblUnusedRcpt);
	}

	private void displaySavedOtherDeposit(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal) {//ok

		FncTables.clearTable(modelMain);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		String sql = 			
				"---------display list of Other Deposits/Releases \r\n" + 
						"select \r\n" + 
						"\r\n" + 
						"trim(particular),\r\n" + 
						"amount\r\n" + 
						"\r\n" + 
						"from rf_other_deposit_summary\r\n" + 
						"\r\n" + 
						"where trans_no = '"+trans_no+"' " +
						"and status_id = 'A' " ;

		System.out.printf("SQL #1: %s", sql);

		pgSelect db = new pgSelect();
		db.select(sql);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());				
			}	
			totalOtherDeposit(modelMain, modelTotal);		
		}		

		else {
			modelOtherDepositTotal = new modelCashCount_otherDeposit();
			modelOtherDepositTotal.addRow(new Object[] { "Total", new BigDecimal(0.00)  });

			tblOtherDepositTotal = new _JTableTotal(modelOtherDepositTotal, tblOtherDeposit);
			scrollOtherDepositTotal.setViewportView(tblOtherDepositTotal);
			tblOtherDepositTotal.setFont(dialog11Bold);
			((_JTableTotal) tblOtherDepositTotal).setTotalLabel(0);
		}

		tblOtherDeposit.packAll();
		adjustRowHeight(tblOtherDeposit);
		tblOtherDeposit.getColumnModel().getColumn(0).setPreferredWidth(250);
	}

	private void displayCashDepositList(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal) {

		FncTables.clearTable(modelMain); 
		DefaultListModel listModel = new DefaultListModel(); 
		rowHeader.setModel(listModel); 

		String sql = 			
				"\r\n" + 
						"select \r\n" + 
						"\r\n" + 
						"trim(a.bank_acct_no),\r\n" + 
						"trim(b.proj_alias),\r\n" + 
						"a.phase,\r\n" + 
						"a.amount\r\n" + 
						"\r\n" + 
						"from rf_cash_deposit_summary a\r\n" + 
						"left join mf_project b on a.projcode = b.proj_id " +
						"where a.trans_no = '"+trans_no+"' " +
						"and a.status_id = 'A' " ;

		pgSelect db = new pgSelect();
		db.select(sql);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());				
			}	

			totalCashDeposit(modelMain, modelTotal);			
		}	
		adjustRowHeight(tblCashDeposit);
		tblCashDeposit.packAll();
		tblCashDeposit.getColumnModel().getColumn(0).setPreferredWidth(250);
	}	

	private void displayCashCount() {	

		int x  = 0;	
		int rw = tblCashCount.getModel().getRowCount();
		String denomination[] = {"1000","500","200","100","50","20",
				"10","5","1","0.50","0.25","0.10","0.01"};
		String denom_sql[] = {"denom_1000","denom_500","denom_200","denom_100","denom_50","denom_20",
				"denom_10","denom_5","denom_1","denom_50c","denom_25c","denom_10c","denom_1c"};

		while(x < rw ) {	

			Double value = 0.00 ;
			Integer piece = 0;

			if (sql_getDenomValue(denom_sql[x]).equals("")){}			
			else 
			{
				value = Double.parseDouble(sql_getDenomValue(denom_sql[x]));
			}

			if (sql_getDenomPieceNo(denom_sql[x], denomination[x]).equals("")){}			
			else 
			{
				piece = Integer.parseInt(sql_getDenomPieceNo(denom_sql[x], denomination[x]));
			}

			modelCashCount.setValueAt(new BigDecimal((denomination[x])), x, 0);
			modelCashCount.setValueAt(piece, x, 1);
			modelCashCount.setValueAt(new BigDecimal(value), x, 2);

			x++;
		}		

		Double total = Double.parseDouble(sql_getSumValue());
		modelCashCountTotal.setValueAt(nf.format(total), 0, 2);

	}	


	//properties
	/*private void enable_fields(){

		lblDepositDate.setEnabled(true);	
		dteDeposit.setEnabled(true);		
		((JTextFieldDateEditor)dteDeposit.getDateEditor()).setEditable(false);
		lblBankAcctNo.setEnabled(true);
		lblBankAcctAlias.setEnabled(true);	
		lookupBankAcctNo.setEnabled(true);	
		lookupBankAcctNo.setEditable(false);	
		lblBankAcct.setEnabled(true);	
		txtBankName.setEnabled(true);
		lblBankAcctAlias.setEnabled(true);	
		txtBankAccount.setEnabled(true);	
		lblBankBranch.setEnabled(true);	
		txtBankBranch.setEnabled(true);	
		lblTotal.setEnabled(true);
	}*/

	private void enableButtons(Boolean a, Boolean b, Boolean c, Boolean d, Boolean e, Boolean f){
		btnAddNew.setEnabled(a);
		btnEdit.setEnabled(b);
		btnSave.setEnabled(c);
		btnPost.setEnabled(d);
		btnPreview.setEnabled(e);
		btnCancel.setEnabled(f);
	}

	private void enableFields(Boolean x){

		tagBranch.setEnabled(x);	
		lblDepositDate.setEnabled(x);	
		dteDeposit.setEnabled(x);	
	}

	private void refreshFields(){

		lookupOfficeBranch.setValue("");		
		lookupTransNo.setValue("");		
		lookupOfficeBranch.setValue("");
		tagBranch.setTag("");
		dteDeposit.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
	}

	private void refresh_tables() {

		//reset table 1
		FncTables.clearTable(modelCashCount);
		FncTables.clearTable(modelCashCountTotal);			
		rowHeaderCashCount = tblCashCount.getRowHeader22();
		scrollCashCount.setRowHeaderView(rowHeaderCashCount);	
		modelCashCountTotal.addRow(new Object[] { "Total", null,  new BigDecimal(0.00) });

		//reset table 2
		FncTables.clearTable(modelCashDeposit);
		FncTables.clearTable(modelCashDepositTotal);			
		rowHeaderCashDeposit = tblCashDeposit.getRowHeader22();
		scrollCashDeposit.setRowHeaderView(rowHeaderCashDeposit);	
		modelCashDepositTotal.addRow(new Object[] { "Total", new BigDecimal(0.00)  });

		//reset table 3
		FncTables.clearTable(modelOtherDeposit);
		FncTables.clearTable(modelOtherDepositTotal);			
		rowHeaderOtherDeposit = tblOtherDeposit.getRowHeader22();
		scrollOtherDeposit.setRowHeaderView(rowHeaderOtherDeposit);	
		modelOtherDepositTotal.addRow(new Object[] { "Total", new BigDecimal(0.00)  });

		//reset table 4
		FncTables.clearTable(modelUnusedRcpt);
		FncTables.clearTable(modelUnusedRcptTotal);			
		rowHeaderUnusedRcpt = tblUnusedRcpt.getRowHeader22();
		scrollUnusedRcpt.setRowHeaderView(rowHeaderUnusedRcpt);	
		modelUnusedRcptTotal.addRow(new Object[] { "Total", new BigDecimal(0.00)  });

	}

	private void initialize_comp(){		
		company		= "CENQHOMES DEVELOPMENT CORPORATION";			
		company_logo = RequestForPayment.sql_getCompanyLogo();	

		lblTransNo.setEnabled(true);
		lookupTransNo.setEnabled(true);
		tagStatus.setEnabled(true);	

		/*lblOfficeBranch.setEnabled(true);	
		lookupOfficeBranch.setEnabled(true);	
		tagBranch.setEnabled(true);	*/

		btnAddNew.setEnabled(true);
	}


	//action performed
	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getActionCommand().equals("Preview")){
			prime(); 
			preview();
		}
		else if(e.getActionCommand().equals("Preview")){
			JOptionPane.showMessageDialog(getContentPane(),"Sorry, you are not authorized to preview cash count summary. \n"
					+ "Please ask for an access from your department head.","Information",JOptionPane.INFORMATION_MESSAGE);
		}
		/*
		if(e.getActionCommand().equals("Preview") && FncAcounting.EmpCanPreview(UserInfo.EmployeeCode, "7")==true){ preview(); }
		else if(e.getActionCommand().equals("Preview") && FncAcounting.EmpCanPreview(UserInfo.EmployeeCode, "7")==false )
		{ JOptionPane.showMessageDialog(getContentPane(),"Sorry, you are not authorized to preview cash count summary. \n"
				+ "Please ask for an access from your department head.","Information",JOptionPane.INFORMATION_MESSAGE); }
		 */		
	}

	public void mouseClicked(MouseEvent evt) {
		if ((evt.getClickCount() >= 2)) {
			clickCashDeposit();
		}
	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {

	}

	public void mousePressed(MouseEvent e) {

	}

	public void mouseReleased(MouseEvent e) {

	}

	private void totalCashCount(DefaultTableModel modelMain, DefaultTableModel modelTotal) {
		BigDecimal total_cash_amt = new BigDecimal(0.00);
		FncTables.clearTable(modelTotal);

		System.out.println("");

		for (int x=0; x < modelMain.getRowCount(); x++) {
			try {
				total_cash_amt = total_cash_amt.add(((BigDecimal) modelMain.getValueAt(x, 2)));
			} catch (NullPointerException e) {
				total_cash_amt = total_cash_amt.add(new BigDecimal(0.00));
			}

			System.out.println("Row: "+total_cash_amt);
		}

		System.out.println("total_cash_amt: "+total_cash_amt);

		modelTotal.addRow(new Object[] { "Total" , null, nf.format(total_cash_amt)});
	}

	private void totalCashDeposit(DefaultTableModel modelMain, DefaultTableModel modelTotal) {

		BigDecimal total_cash_amt = new BigDecimal(0.00);

		FncTables.clearTable(modelTotal);//Code to clear modelMain.
		for(int x=0; x < modelMain.getRowCount(); x++){

			try {
				total_cash_amt = total_cash_amt.add(((BigDecimal) modelMain.getValueAt(x, 3)));//Dont forget to adjust column number
				System.out.printf("Credit: %s%n", total_cash_amt);
			} catch (NullPointerException e) {
				total_cash_amt = total_cash_amt.add(new BigDecimal(0.00));
			}
		}

		modelTotal.addRow(new Object[] { "Total", null, null, total_cash_amt});
		tblCashDeposit.packAll();
		tblCashDeposit.getColumnModel().getColumn(0).setPreferredWidth(250);
	}	

	private void totalOtherDeposit(DefaultTableModel modelMain, DefaultTableModel modelTotal) {

		BigDecimal total_other_amt = new BigDecimal(0.00);

		FncTables.clearTable(modelTotal);//Code to clear modelMain.
		for(int x=0; x < modelMain.getRowCount(); x++){

			try {
				total_other_amt = total_other_amt.add(((BigDecimal) modelMain.getValueAt(x, 1)));//Dont forget to adjust column number
				System.out.printf("Credit: %s%n", total_other_amt);
			} catch (NullPointerException e) {
				total_other_amt = total_other_amt.add(new BigDecimal(0.00));
			}
		}

		modelTotal.addRow(new Object[] { "Total", total_other_amt});
		tblOtherDeposit.packAll();
		tblOtherDeposit.getColumnModel().getColumn(0).setPreferredWidth(250);
	}

	/*private void totalCheckEntries(DefaultTableModel modelMain, DefaultTableModel modelTotal) {
		BigDecimal total_check_amt = new BigDecimal(0.00);		
		int a = 0;
		FncTables.clearTable(modelTotal);//Code to clear modelMain.
		for(int x=0; x < modelMain.getRowCount(); x++){
			try {

				Boolean isTrue = false;
				if(modelMain.getValueAt(x,0) instanceof String){
					isTrue = new Boolean((String)modelMain.getValueAt(x,0));
				}
				if(modelMain.getValueAt(x,0) instanceof Boolean){
					isTrue = (Boolean)modelMain.getValueAt(x,0);
				}

				if(isTrue){ 
					total_check_amt = total_check_amt.add(((BigDecimal) modelMain.getValueAt(x, 6)));//Dont forget to adjust column number
				} else {					
				}		

				System.out.printf("Credit: %s%n", total_check_amt);				
			} catch (NullPointerException e) {
				total_check_amt = total_check_amt.add(new BigDecimal(0.00));
				modelTotal.addRow(new Object[] { null, "Total", null, null, null, null, 0.00, null });	
			}
			a = a+1;
		}		
		modelTotal.addRow(new Object[] { null, "Total", null, null, null, null, total_check_amt, null });
	}	
	 */



	//lookup 
	private String getBankAccount(){

		String sql = 
				"select " +
						"a.bank_acct_id as \"Bank Acct.\", " +
						"a.acct_desc as \"Bank Desc.\", " +
						"a.bank_acct_no as \"Bank Acct. No.\"," +
						"coalesce(trim(substr(b.acct_name,15)),'') as \"Account\" " +
						"from mf_bank_account a " +
						"left join mf_boi_chart_of_accounts b on a.acct_id = b.acct_id\r\n" + 
						"order by a.bank_acct_id";		
		return sql;

	}

	private Integer sql_getNextTransNo() {//ok
		Integer x = 1;
		String SQL = "select max(trans_no)+1 from rf_cash_count_summary" ;

		pgSelect db = new pgSelect();
		db.select(SQL);

		if (db.isNotNull()) {
			if((Integer) db.getResult()[0][0]==null||db.getResult()[0][0].equals("null")) {x = 1;}
			else {x = (Integer) db.getResult()[0][0]; }

		}else{
			x = 1;
		}

		return x;
	}

	private String getTransNo() {
		return "select a.trans_no as \"Trans. No.\", a.trans_date::date as \"Date\", trim(a.branch_id) as \"Branch ID\", \n" + 
				"trim(b.branch_name) as \"Branch Name\", \n" + 
				"(case when a.status_id = 'A' then 'ACTIVE' when a.status_id = 'P' then 'POSTED' else 'INACTIVE' end) as \"Status\", \n" + 
				"a.co_id, c.company_name \n" + 
				"from rf_cash_count_summary a \n" + 
				"left join mf_office_branch b on a.branch_id = b.branch_id \n" + 
				"left join mf_company c on a.co_id = c.co_id \n" +
				"where (a.co_id = '"+lookupCompany.getValue()+"' or '"+lookupCompany.getValue()+"' = '') \n" +
				"order by a.trans_no desc;";
	}

	private String sql_getSumValue() {	

		String total_value = "";

		String SQL = 
				"select " + 
						"denom_1000+denom_500+denom_200+denom_100 +denom_50+ \n" + 
						"denom_20+denom_10+denom_5+denom_1+denom_50c+denom_25c+ \n" + 
						"denom_10c+ denom_1c " + 
						"from rf_cash_count_summary \n" +
						"where trans_no = "+trans_no+" \r\n" ;

		System.out.printf("SQL #1: %s", SQL);

		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			if( db.getResult()[0][0].toString()==null||db.getResult()[0][0].toString().equals("null")) {total_value = "0.00";}
			else {total_value = db.getResult()[0][0].toString(); }	

		}else{
			total_value = "0.00";
		}

		return total_value;
	}

	private String sql_getDenomPieceNo(String denom, String x) {//ok	

		String denom_piece = "";

		String SQL = 
				"select " +
						"("+denom+"/"+x+")::int  \n" +
						"from rf_cash_count_summary  \n" +
						"where trans_no = "+trans_no+" \r\n" ;

		System.out.printf("SQL #1: %s", SQL);

		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			if( db.getResult()[0][0].toString()==null||db.getResult()[0][0].toString().equals("null")) {denom_piece = "";}
			else {denom_piece = db.getResult()[0][0].toString(); }	

		}else{
			denom_piece = "";
		}

		return denom_piece;
	}

	private String sql_getDenomValue(String denom) {//ok	

		String denom_value = "";

		String SQL = 
				"select " +
						""+denom+"  " +
						"from rf_cash_count_summary  \n" +
						"where trans_no = "+trans_no+" \r\n" ;

		System.out.printf("SQL #1: %s", SQL);

		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			if( db.getResult()[0][0].toString()==null||db.getResult()[0][0].toString().equals("null")) {denom_value = "";}
			else {denom_value = db.getResult()[0][0].toString(); }	

		}else{
			denom_value = "";
		}

		return denom_value;
	}

	private String sql_getDocId(String doc_alias) {//ok	

		String doc_id = "";

		String SQL = 
				"select " +
						"doc_id " +
						"from mf_doc_details  \n" +
						"where trim(doc_alias) like '"+doc_alias+"' \r\n" ;

		System.out.printf("SQL #1: %s", SQL);

		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			if( db.getResult()[0][0].toString()==null||db.getResult()[0][0].toString().equals("null")) {doc_id = "";}
			else {doc_id = db.getResult()[0][0].toString(); }	

		}else{
			doc_id = "";
		}

		return doc_id;
	}

	private String sql_getProjID(String proj_alias) {//ok	

		String proj_id = "";

		String SQL = 
				"select " +
						"proj_id " +
						"from mf_project  \n" +
						"where trim(proj_alias) like '"+proj_alias+"' \r\n" ;

		System.out.printf("SQL #1: %s", SQL);

		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			if( db.getResult()[0][0].toString()==null||db.getResult()[0][0].toString().equals("null")) {proj_id = "";}
			else {proj_id = db.getResult()[0][0].toString(); }	

		}else{
			proj_id = "";
		}

		return proj_id;
	}


	//preview
	private void preview(){//used
		String criteria = "Cash Count Summary";		
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());	

		Double amt_1000	= Double.parseDouble(modelCashCount.getValueAt(0,2).toString().replace(",", ""));	
		Double amt_500	= Double.parseDouble(modelCashCount.getValueAt(1,2).toString().replace(",", ""));	
		Double amt_200	= Double.parseDouble(modelCashCount.getValueAt(2,2).toString().replace(",", ""));	
		Double amt_100	= Double.parseDouble(modelCashCount.getValueAt(3,2).toString().replace(",", ""));	
		Double amt_50	= Double.parseDouble(modelCashCount.getValueAt(4,2).toString().replace(",", ""));	
		Double amt_20	= Double.parseDouble(modelCashCount.getValueAt(5,2).toString().replace(",", ""));	
		Double amt_10	= Double.parseDouble(modelCashCount.getValueAt(6,2).toString().replace(",", ""));	
		Double amt_5	= Double.parseDouble(modelCashCount.getValueAt(7,2).toString().replace(",", ""));	
		Double amt_1	= Double.parseDouble(modelCashCount.getValueAt(8,2).toString().replace(",", ""));	
		Double amt_50c	= Double.parseDouble(modelCashCount.getValueAt(9,2).toString().replace(",", ""));	
		Double amt_25c	= Double.parseDouble(modelCashCount.getValueAt(10,2).toString().replace(",", ""));	
		Double amt_10c	= Double.parseDouble(modelCashCount.getValueAt(11,2).toString().replace(",", ""));	
		Double amt_1c	= Double.parseDouble(modelCashCount.getValueAt(12,2).toString().replace(",", ""));	
		Double amt_tot	= Double.parseDouble(modelCashCountTotal.getValueAt(0,2).toString().replace(",", ""));	

		Integer pc_1000	= Integer.parseInt(modelCashCount.getValueAt(0,1).toString());	
		Integer pc_500	= Integer.parseInt(modelCashCount.getValueAt(1,1).toString());	
		Integer pc_200	= Integer.parseInt(modelCashCount.getValueAt(2,1).toString());		
		Integer pc_100	= Integer.parseInt(modelCashCount.getValueAt(3,1).toString());	
		Integer pc_50	= Integer.parseInt(modelCashCount.getValueAt(4,1).toString());	
		Integer pc_20	= Integer.parseInt(modelCashCount.getValueAt(5,1).toString());	
		Integer pc_10	= Integer.parseInt(modelCashCount.getValueAt(6,1).toString());	
		Integer pc_5	= Integer.parseInt(modelCashCount.getValueAt(7,1).toString());	
		Integer pc_1	= Integer.parseInt(modelCashCount.getValueAt(8,1).toString());	
		Integer pc_50c	= Integer.parseInt(modelCashCount.getValueAt(9,1).toString());	
		Integer pc_25c	= Integer.parseInt(modelCashCount.getValueAt(10,1).toString());	
		Integer pc_10c	= Integer.parseInt(modelCashCount.getValueAt(11,1).toString());		
		Integer pc_1c	= Integer.parseInt(modelCashCount.getValueAt(12,1).toString());		

		Map<String, Object> mapParameters = new HashMap<String, Object>();


		mapParameters.put("company", company);
		mapParameters.put("co_id", lookupCompany.getValue());

		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));

		mapParameters.put("prepared_by", UserInfo.FullName);
		mapParameters.put("1000_amt", amt_1000);
		mapParameters.put("500_amt", amt_500);
		mapParameters.put("200_amt", amt_200);
		mapParameters.put("100_amt", amt_100);
		mapParameters.put("50_amt", amt_50);
		mapParameters.put("20_amt", amt_20);
		mapParameters.put("10_amt", amt_10 );
		mapParameters.put("5_amt", amt_5 );
		mapParameters.put("1_amt", amt_1 );
		mapParameters.put("50c_amt", amt_50c );
		mapParameters.put("25c_amt", amt_25c );
		mapParameters.put("10c_amt", amt_10c );
		mapParameters.put("1c_amt", amt_1c );
		mapParameters.put("tot_amt", new BigDecimal(amt_tot));
		mapParameters.put("1000_pc", pc_1000);
		mapParameters.put("500_pc", pc_500);
		mapParameters.put("200_pc", pc_200);
		mapParameters.put("100_pc", pc_100);
		mapParameters.put("50_pc", pc_50);
		mapParameters.put("20_pc", pc_20);
		mapParameters.put("10_pc", pc_10 );
		mapParameters.put("5_pc", pc_5 );
		mapParameters.put("1_pc", pc_1 );
		mapParameters.put("50c_pc", pc_50c );
		mapParameters.put("25c_pc", pc_25c );
		mapParameters.put("10c_pc", pc_10c );	
		mapParameters.put("1c_pc", pc_1c );	
		mapParameters.put("tran_date", dateFormat.format(dteDeposit.getDate()));
		mapParameters.put("date", dteDeposit.getDate());
		mapParameters.put("trans_no", trans_no);
		mapParameters.put("branch_id", branch_id);
		mapParameters.put("branch", branch);
		mapParameters.put("status_name", status_name);
		mapParameters.put("emp_code", UserInfo.EmployeeCode);

		if (lookupCompany.getValue().equals("")) {
			FncReport.generateReport("/Reports/rptCashCountNew_preview_wologo.jasper", reportTitle, company, mapParameters);
		} else {
			FncReport.generateReport("/Reports/rptCashCountNew_preview.jasper", reportTitle, company, mapParameters);
		}

	}

	private void adjustRowHeight(_JTableMain table){		

		int rw = table.getModel().getRowCount();
		int x = 0;

		while (x<rw){			
			table.setRowHeight(x, 22);			
			x++;
		}
	}

	private void clickCashDeposit() {

		int column = tblCashDeposit.getSelectedColumn();
		int row = tblCashDeposit.getSelectedRow();		

		Integer x[] = {0,1};
		String sql[] = {getBankAccount(),""};
		String lookup_name[] = {"Bank Account",""};				

		if (column == x[column] && modelCashDeposit.isEditable() && sql[column]!="") {  
			_JLookupTable dlg = new _JLookupTable(FncGlobal.homeMDI, null, lookup_name[column], sql[column], false);
			dlg.setLocationRelativeTo(FncGlobal.homeMDI);
			dlg.setVisible(true);

			Object[] data = dlg.getReturnDataSet();
			if (data != null && column == 0) {
				modelCashDeposit.setValueAt(data[3], row, column);
				tblCashDeposit.packAll();
			}
			else {}		
		}

	}

	private void saveCashCount() {

		if (lookupOfficeBranch.getValue().equals("")) {
			JOptionPane.showMessageDialog(getContentPane(), "Please enter Branch Name.", "Incomplete Detail", 
					JOptionPane.WARNING_MESSAGE);
		} else {

			Double amount = Double.parseDouble(tblCashCountTotal.getValueAt(0,2).toString().replace(",", ""));

			if(amount==0.00)
			{
				if (JOptionPane.showConfirmDialog(getContentPane(), "Total cash count is zero, proceed?", "Confirmation", 
						JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

					if (JOptionPane.showConfirmDialog(getContentPane(), "Are all entries correct?", "Confirmation", 
							JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) 
					{
						pgUpdate db = new pgUpdate();	
						insertCashCount(db);
						insertUnusedReceipt(db);
						insertCashDeposit(db);
						insertOtherDeposit(db);
						db.commit();
						lookupTransNo.setValue(trans_no.toString());
						enableButtons(false, true, false, true, true, true);
						modelCashCount.setEditable(false);
						modelCashDeposit.setEditable(false);
						modelOtherDeposit.setEditable(false);
						JOptionPane.showMessageDialog(getContentPane(),"New cash count summary saved.","Information",JOptionPane.INFORMATION_MESSAGE);	

					}
				}
			}

			else {	
				if (JOptionPane.showConfirmDialog(getContentPane(), "Are all entries correct?", "Confirmation", 
						JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) 
				{

					pgUpdate db = new pgUpdate();	
					insertCashCount(db);
					insertUnusedReceipt(db);
					insertCashDeposit(db);
					insertOtherDeposit(db);
					db.commit();						
					lookupTransNo.setValue(trans_no.toString());
					enableButtons(false, true, false, true, true, true);
					modelCashCount.setEditable(false);
					modelCashDeposit.setEditable(false);
					modelOtherDeposit.setEditable(false);
					JOptionPane.showMessageDialog(getContentPane(),"New cash count summary saved.","Information",JOptionPane.INFORMATION_MESSAGE);						
				}  

				else {}

			}
		}
	}

	private void editCashCount(){//ok

		Double amount = Double.parseDouble(tblCashCountTotal.getValueAt(0,2).toString().replace(",", ""));

		if(amount==0.00)
		{
			if (JOptionPane.showConfirmDialog(getContentPane(), "Total cash count is zero, proceed?", "Confirmation", 
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

				if (JOptionPane.showConfirmDialog(getContentPane(), "Are all entries correct?", "Confirmation", 
						JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) 
				{
					pgUpdate db = new pgUpdate();	
					System.out.println("update cash count");
					updateCashCount(db);

					System.out.println("update unused receipt");
					updateUnusedReceipts(db);

					System.out.println("update cash deposit");
					updateCashDeposit(db);

					System.out.println("update other deposit");
					updateOtherDeposit(db);

					System.out.println("insert unused receipt");
					insertUnusedReceipt(db);

					System.out.println("insert cash deposit");
					insertCashDeposit(db);

					System.out.println("insert other deposit");
					insertOtherDeposit(db);
					db.commit();
					lookupTransNo.setValue(trans_no.toString());
					enableButtons(false, true, false, true, true, true);
					modelCashCount.setEditable(false);
					modelCashDeposit.setEditable(false);
					modelOtherDeposit.setEditable(false);
					JOptionPane.showMessageDialog(getContentPane(),"New cash count summary updated.","Information",JOptionPane.INFORMATION_MESSAGE);	

				}
			}
		}

		else {	
			if (JOptionPane.showConfirmDialog(getContentPane(), "Are all entries correct?", "Confirmation", 
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) 
			{

				pgUpdate db = new pgUpdate();	
				updateCashCount(db);
				updateUnusedReceipts(db);					
				updateCashDeposit(db);
				updateOtherDeposit(db);
				insertUnusedReceipt(db);					
				insertCashDeposit(db);
				insertOtherDeposit(db);
				db.commit();						
				lookupTransNo.setValue(trans_no.toString());
				enableButtons(false, true, false, true, true, true);
				modelCashCount.setEditable(false);
				modelCashDeposit.setEditable(false);
				modelOtherDeposit.setEditable(false);
				JOptionPane.showMessageDialog(getContentPane(),"New cash count summary updated.","Information",JOptionPane.INFORMATION_MESSAGE);						
			}  

			else {}

		}
	}

	private void insertCashCount(pgUpdate db){

		trans_no = FncGlobal.GetInteger("select * from fn_get_cash_count()"); 

		double denom_1000 = 0.00;
		double denom_500 = 0.00;
		double denom_200 = 0.00;
		double denom_100 = 0.00;
		double denom_50 = 0.00;
		double denom_20 = 0.00;
		double denom_10 = 0.00;
		double denom_5 = 0.00;
		double denom_1 = 0.00;
		double denom_50c = 0.00;
		double denom_25c = 0.00;
		double denom_10c = 0.00;
		double denom_1c = 0.00;

		try {
			denom_1000 = Double.parseDouble(tblCashCount.getValueAt(0, 2).toString());
		} catch (NullPointerException e) {
			denom_1000 = 0.00;
		}

		try { denom_500 	= Double.parseDouble(tblCashCount.getValueAt(1,2).toString()); } catch (NullPointerException e) { denom_500 = 0.00; }
		try { denom_200 	= Double.parseDouble(tblCashCount.getValueAt(2,2).toString()); } catch (NullPointerException e) { denom_500 = 0.00; }
		try { denom_100 	= Double.parseDouble(tblCashCount.getValueAt(3,2).toString()); } catch (NullPointerException e) { denom_100 = 0.00; }
		try { denom_50 		= Double.parseDouble(tblCashCount.getValueAt(4,2).toString()); } catch (NullPointerException e) { denom_50 = 0.00; }
		try { denom_20 		= Double.parseDouble(tblCashCount.getValueAt(5,2).toString()); } catch (NullPointerException e) { denom_20 = 0.00; }
		try { denom_10 		= Double.parseDouble(tblCashCount.getValueAt(6,2).toString()); } catch (NullPointerException e) { denom_10 = 0.00; }
		try { denom_5 		= Double.parseDouble(tblCashCount.getValueAt(7,2).toString()); } catch (NullPointerException e) { denom_1000 = 0.00; }
		try { denom_1 		= Double.parseDouble(tblCashCount.getValueAt(8,2).toString()); } catch (NullPointerException e) { denom_1 = 0.00; }
		try { denom_50c 	= Double.parseDouble(tblCashCount.getValueAt(9,2).toString()); } catch (NullPointerException e) { denom_50c = 0.00; }
		try { denom_25c 	= Double.parseDouble(tblCashCount.getValueAt(10,2).toString()); } catch (NullPointerException e) { denom_25c = 0.00; }
		try { denom_10c 	= Double.parseDouble(tblCashCount.getValueAt(11,2).toString()); } catch (NullPointerException e) { denom_10c = 0.00; }
		try { denom_1c 	= Double.parseDouble(tblCashCount.getValueAt(12,2).toString()); } catch (NullPointerException e) { denom_1c = 0.00; }

		String branch_id	= lookupOfficeBranch.getText().trim();

		String sqlDetail = 
				"INSERT into rf_cash_count_summary values (" +
						""+trans_no+",  \n" +  		//0 trans_no
						"'"+dteDeposit.getDate()+"',  \n" +  	//1 trans_date
						"'"+branch_id+"',  \n" +  	//2 branch_id
						""+denom_1000+",  \n" +  	//3	denom_1000
						""+denom_500+",  \n" +  	//4	denom_500
						""+denom_200+",  \n" +  	//5	denom_200
						""+denom_100+",  \n" +  	//6	denom_100
						""+denom_50+",  \n" +  		//7	denom_50
						""+denom_20+",  \n" +  		//8	denom_20
						""+denom_10+",  \n" +  		//9	denom_10
						""+denom_5+",  \n" +  		//10 denom_5
						""+denom_1+",  \n" +  		//11 denom_1
						""+denom_50c+",  \n" +  	//12 denom_50c
						""+denom_25c+",  \n" +  	//13 denom_25c
						""+denom_10c+",  \n" +  	//14 denom_10c
						"'"+lookupCompany.getValue()+"',  \n" +  		//15 co_id
						"'A',  \n" +  				//16 status_id		 
						"'"+UserInfo.EmployeeCode+"',  \n" +  	//17 created_by
						"now(), \n" + 				//18 created_date
						"'', \n" +					//19 edited_by
						"null,  \n" +  				//20 edited_date
						"'',  \n" +  				//21 posted_by
						"null,  \n" +  				//22 posted_date			
						""+denom_1c+"  \n" +  		//23 denom_1c
						")   " ;

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);	

	}

	private void updateCashCount(pgUpdate db){		

		double denom_1000 = 0.00;
		double denom_500 = 0.00;
		double denom_200 = 0.00;
		double denom_100 = 0.00;
		double denom_50 = 0.00;
		double denom_20 = 0.00;
		double denom_10 = 0.00;
		double denom_5 = 0.00;
		double denom_1 = 0.00;
		double denom_50c = 0.00;
		double denom_25c = 0.00;
		double denom_10c = 0.00;
		double denom_1c = 0.00;

		try { denom_1000 	= Double.parseDouble(tblCashCount.getValueAt(0,2).toString()); } catch (NullPointerException e) { denom_1000 = 0.00; }
		try { denom_500 	= Double.parseDouble(tblCashCount.getValueAt(1,2).toString()); } catch (NullPointerException e) { denom_500 = 0.00; }
		try { denom_200 	= Double.parseDouble(tblCashCount.getValueAt(2,2).toString()); } catch (NullPointerException e) { denom_500 = 0.00; }
		try { denom_100 	= Double.parseDouble(tblCashCount.getValueAt(3,2).toString()); } catch (NullPointerException e) { denom_100 = 0.00; }
		try { denom_50 		= Double.parseDouble(tblCashCount.getValueAt(4,2).toString()); } catch (NullPointerException e) { denom_50 = 0.00; }
		try { denom_20 		= Double.parseDouble(tblCashCount.getValueAt(5,2).toString()); } catch (NullPointerException e) { denom_20 = 0.00; }
		try { denom_10 		= Double.parseDouble(tblCashCount.getValueAt(6,2).toString()); } catch (NullPointerException e) { denom_10 = 0.00; }
		try { denom_5 		= Double.parseDouble(tblCashCount.getValueAt(7,2).toString()); } catch (NullPointerException e) { denom_1000 = 0.00; }
		try { denom_1 		= Double.parseDouble(tblCashCount.getValueAt(8,2).toString()); } catch (NullPointerException e) { denom_1 = 0.00; }
		try { denom_50c 	= Double.parseDouble(tblCashCount.getValueAt(9,2).toString()); } catch (NullPointerException e) { denom_50c = 0.00; }
		try { denom_25c 	= Double.parseDouble(tblCashCount.getValueAt(10,2).toString()); } catch (NullPointerException e) { denom_25c = 0.00; }
		try { denom_10c 	= Double.parseDouble(tblCashCount.getValueAt(11,2).toString()); } catch (NullPointerException e) { denom_10c = 0.00; }
		try { denom_1c 	= Double.parseDouble(tblCashCount.getValueAt(12,2).toString()); } catch (NullPointerException e) { denom_1c = 0.00; }

		String branch_id	= lookupOfficeBranch.getText().trim();

		String sqlDetail = 
				"update rf_cash_count_summary set \n" +			
						"trans_date = '"+dteDeposit.getDate()+"',  \n" +  	//1
						"branch_id = '"+branch_id+"',  \n" +  	//2
						"denom_1000 = "+denom_1000+",  \n" +  	//3	
						"denom_500 = "+denom_500+",  \n" +  	//4	
						"denom_200 = "+denom_200+",  \n" +  	//5	
						"denom_100 = "+denom_100+",  \n" +  	//6	
						"denom_50 = "+denom_50+",  \n" +  		//7	
						"denom_20 = "+denom_20+",  \n" +  		//8	
						"denom_10 = "+denom_10+",  \n" +  		//9	
						"denom_5 = "+denom_5+",  \n" +  		//10
						"denom_1 = "+denom_1+",  \n" +  		//11
						"denom_50c = "+denom_50c+",  \n" +  	//12
						"denom_25c = "+denom_25c+",  \n" +  	//13
						"denom_10c = "+denom_10c+",  \n" +  	//14
						"denom_1c = "+denom_1c+",  \n" +  	//22
						"co_id = '"+lookupCompany.getValue()+"',  \n" +  		//15
						"edited_by = '"+UserInfo.EmployeeCode+"',  \n" +  	//16
						"edited_date  = now() \n" + //17
						"where trans_no = "+trans_no+"  \n" ;

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);	

	}

	private void insertUnusedReceipt(pgUpdate db){

		int x  = 0;	
		int rw = tblUnusedRcpt.getModel().getRowCount();	

		while(x < rw ) {	

			String rec_id = tblUnusedRcpt.getValueAt(x, 0).toString(); 
			String doc_id = sql_getDocId(tblUnusedRcpt.getValueAt(x, 1).toString()); 
			String first_no = tblUnusedRcpt.getValueAt(x, 2).toString();	
			String last_no = tblUnusedRcpt.getValueAt(x, 3).toString(); 
			String first_no_unused = tblUnusedRcpt.getValueAt(x, 4).toString(); 
			String last_no_unused = tblUnusedRcpt.getValueAt(x, 5).toString();
			String strChar = tblUnusedRcpt.getValueAt(x, 6).toString(); 

			String strSQL = "insert into rf_unused_receipts  \n" +
					"values (" +
					""+trans_no+", \n" + 
					"'"+dteDeposit.getDate()+"', \n" + 					
					"'"+lookupOfficeBranch.getText().trim()+"', \n" + 	
					"'"+doc_id+"', \n" + 
					""+rec_id+", \n" + 
					""+first_no+", \n" + 
					""+last_no+", \n" + 
					""+first_no_unused+", \n" + 
					""+last_no_unused+", \n" + 
					"'"+modelUnusedRcpt.getValueAt(x, 8).toString()+"', \n" + 
					"'A', \n" + 				
					"'"+UserInfo.EmployeeCode+"', \n" + 
					"now(), \n" + 
					"'', \n" + 
					"null, \n" + 
					"'"+strChar+"'); " ;
			db.executeUpdate(strSQL, true);	


			x++;
		}
	}

	private void updateUnusedReceipts(pgUpdate db) {//ok

		String sqlDetail = 
				"update rf_unused_receipts set " +
						"status_id = 'I', \n " +
						"edited_by = '"+UserInfo.EmployeeCode+"',  \n" +  	
						"edited_date = now()  \n" +  
						"where trans_no = "+trans_no+"   " ;

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);	
	}

	private void insertCashDeposit(pgUpdate db) {

		int x  = 0;	
		int rw = tblCashDeposit.getModel().getRowCount();	

		while(x < rw ) {	

			String bank_acct = "";
			String proj_id   = "";
			String phase     = "";
			Double cash_dep_amt = 0.00;

			try {
				bank_acct = tblCashDeposit.getValueAt(x,0).toString();
			} catch (NullPointerException e) {
				bank_acct  = "";
			}

			try {
				proj_id = sql_getProjID(tblCashDeposit.getValueAt(x,1).toString());
			} catch (NullPointerException e) {
				proj_id = "";
			}

			try {
				phase = tblCashDeposit.getValueAt(x,2).toString();
			} catch (NullPointerException e) {
				phase  = "";
			}

			try {
				cash_dep_amt = Double.parseDouble(tblCashDeposit.getValueAt(x,3).toString());
			} catch (NullPointerException e) {
				cash_dep_amt = 0.00;
			}

			String sqlDetail = "insert into rf_cash_deposit_summary values ("+trans_no+", '"+bank_acct+"', '"+proj_id+"', '"+phase+"', "+cash_dep_amt+", 'A', '"+UserInfo.EmployeeCode+"', now(), '', null); ";
			db.executeUpdate(sqlDetail, false);	


			x++;
		}
	}

	private void updateCashDeposit(pgUpdate db) {//ok

		String sqlDetail = 
				"update rf_cash_deposit_summary set " +
						"status_id = 'I', \n " +
						"edited_by = '"+UserInfo.EmployeeCode+"',  \n" +  	
						"edited_date = now()  \n" +  
						"where trans_no = "+trans_no+"   " ;

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);	
	}

	private void insertOtherDeposit(pgUpdate db){//ok

		int x  = 0;	
		int rw = tblOtherDeposit.getModel().getRowCount();	

		while(x < rw ) {	

			String partic 	= tblOtherDeposit.getValueAt(x,0).toString();	
			Double other_dep_amt = Double.parseDouble(tblOtherDeposit.getValueAt(x,1).toString());	

			String sqlDetail = 
					"INSERT into rf_other_deposit_summary values (" +
							""+trans_no+",  \n" +  		//1
							"'"+partic+"',  \n" +  		//2		
							""+other_dep_amt+",  \n" +  //3
							"'A',  \n" +  				//4	
							"'"+UserInfo.EmployeeCode+"',  \n" +  	//5
							"now(),  \n" +  //6
							"'',  \n" +  				//7
							"null  \n" +  				//8
							")   " ;

			System.out.printf("SQL #1: %s", sqlDetail);
			db.executeUpdate(sqlDetail, false);	


			x++;
		}
	}

	private void updateOtherDeposit(pgUpdate db) {//ok

		String sqlDetail = 
				"update rf_other_deposit_summary set " +
						"status_id = 'I', \n " +
						"edited_by = '"+UserInfo.EmployeeCode+"',  \n" +  	
						"edited_date = now()  \n" +  
						"where trans_no = "+trans_no+"   " ;

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);	
	}

	private void postCashCount() {//ok

		if (JOptionPane.showConfirmDialog(getContentPane(), "Are you sure you want to post cash count summary?", "Confirmation", 
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) 
		{

			pgUpdate db = new pgUpdate();	

			String sqlDetail = 
					"update rf_cash_count_summary set " +
							"status_id = 'P', \n " +
							"posted_by = '"+UserInfo.EmployeeCode+"',  \n" +  	
							"posted_date = now()  \n" +  
							"where trans_no = "+trans_no+"   " ;

			System.out.printf("SQL #1: %s", sqlDetail);
			db.executeUpdate(sqlDetail, false);	

			db.commit();
			JOptionPane.showMessageDialog(getContentPane(),"Cash count summary posted.","Information",JOptionPane.INFORMATION_MESSAGE);	
			tagStatus.setTag("POSTED");
			status_id = "P";
			status_name = "POSTED";
			enableButtons(false, false, false, false, true, true);

		}
	}

	private void prime() {
		SwingWorker sw = new SwingWorker() {
			protected Object doInBackground()
					throws FileNotFoundException, IOException, InterruptedException {
				FncGlobal.startProgress("Loading...");

				String branch_id = ""; 

				if (lookupOfficeBranch.getText().equals("")) {
					branch_id = "ALL";
				} else {
					branch_id = lookupOfficeBranch.getValue(); 
				}

				System.out.println("call sp_prime_cdr2('ALL', '"+branch_id+"', '"+lookupCompany.getText()+"', 'transdate', '"+dteDeposit.getDate().toString()+"', '"+dteDeposit.getDate().toString()+"', 'Receipt No.', '"+UserInfo.EmployeeCode+"'); ");
				
				pgUpdate dbExec = new pgUpdate(); 
				dbExec.executeUpdate("call sp_prime_cdr2('ALL', '"+branch_id+"', '"+lookupCompany.getText()+"', 'transdate', '"+dteDeposit.getDate().toString()+"', '"+dteDeposit.getDate().toString()+"', 'Receipt No.', '"+UserInfo.EmployeeCode+"'); ", false);
				dbExec.commit();				

				FncGlobal.stopProgress();
				return null;
			}
		};
		sw.execute();
	}





}
