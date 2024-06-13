package Utilities;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.JXTextField;

import tablemodel.model_PF_entries;
import tablemodel.model_PF_entries_sub;
import tablemodel.model_proforma_entries;
import tablemodel.model_proforma_payments;
import Accounting.Disbursements.RequestForPayment;
import Database.pgSelect;
import Database.pgUpdate;
import Functions.FncLookAndFeel;
import Functions.FncReport;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;

import components.JTBorderFactory;
import components._JInternalFrame;
import components._JScrollPaneMain;
import components._JTabbedPane;
import components._JTableMain;
import components._JTagLabel;

public class ProForma_Entries extends _JInternalFrame implements _GUI, ActionListener, MouseListener {

	private static final long serialVersionUID = -3061284418918863916L;
	static String title = "Pro-Forma Entries";
	static Dimension size = new Dimension(1200, 600);

	private JPanel pnlMain;
	private JPanel pnlPF_sub_b;
	private JPanel pnlNew_PF;
	private JPanel pnlNew_PF_a;
	private JPanel pnlNew_PF_a1;
	private JPanel pnlNew_PF_a2;
	private JPanel pnlNew_PF_c;
	private JPanel pnlNew_Entries;
	private JPanel pnlNew_Entries_a;
	private JPanel pnlNew_Entries_a1;
	private JPanel pnlNew_Entries_a2;
	private JPanel pnlNew_Entries_c;

	private _JScrollPaneMain scrollPF_entries;	
	private _JScrollPaneMain scrollPF_sub;	
	private static model_proforma_payments modelPF_main;
	private static model_PF_entries_sub modelPF_sub;
	private static model_PF_entries_sub modelPF_sub_total;
	private static model_PF_entries modelPF_main_total;

	private static _JTableMain tblPF_main;
	private static _JTableMain tblPF_sub;	

	static _JLookup lookupCompany;	
	private _JLookup lookupPmtPart;	
	private _JLookup lookupDebit;
	private _JLookup lookupCredit;
	private _JLookup lookupCostPart;
	
	static _JTagLabel tagCompany;
	private static JList rowHeader_PFmain;
	private static JList rowHeader_PF_sub;

	private JButton btnSave;
	private static JButton btnCancel;
	private static JButton btnAddTrans;
	private static JButton btnEditTrans;
	private JButton btnPreview;	
	private JButton btnAddEntries;
	private JButton btnEditEntries;

	private JLabel lblTransDesc;	
	private JLabel lblPaymentPart;
	private JLabel lblReceiptType;	
	private JLabel lblStatus;
	private JLabel lblCostPart;
	private JLabel lblCredit;
	private JLabel lblDebit;
	private JLabel lblRemarks;
	private JLabel lblPF_no_main;
	private JLabel lblPF_no;
	
	private JComboBox cmbStatus;
	private JComboBox cmbReceiptType;
	private static JXTextField txtSearch;
	private JXTextField txtTransDesc;
	private JXTextField txtPF_no;	
	private JXTextField txtRemarks;	
	private JXTextField txtPF_no_main;
	
	protected static Font dialog11Bold = new Font("DIALOG", Font.BOLD, 11);
	SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

	public static String company = "";
	public static String company_logo;
	static String co_id = "";
	private String to_do = "addnew_pf";	

	private static JComboBox cboType; 
	
	/*	Mann2x's Declarations	*/
	private Font font11 = FncLookAndFeel.systemFont_Plain.deriveFont(11f);

	private static _JTabbedPane tabProForma; 
	private static proForma_payments pf_payments; 
 
	public static JList rowProForma;
	private static _JTableMain tblProForma;
	private static JScrollPane scrProForma;
	public static model_proforma_entries modelProForma;
	
	public ProForma_Entries() {
		super(title, false, true, false, true);
		initGUI();
	}

	public ProForma_Entries(String title) {
		super(title);
	}

	public ProForma_Entries(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
	}

	@Override
	public void initGUI() {
		this.setLayout(new BorderLayout(5,5));
		this.setSize(size);
		this.setPreferredSize(size);

		pnlMain = new JPanel(new BorderLayout(5, 5));
		getContentPane().add(pnlMain, BorderLayout.CENTER);
		pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		{
			{
				JXPanel panPage = new JXPanel(new GridLayout(1, 3, 5, 5));
				pnlMain.add(panPage, BorderLayout.PAGE_START);
				panPage.setPreferredSize(new Dimension(0, 60));				
				{
					{
						JXPanel panDiv1 = new JXPanel(new BorderLayout(5, 5));
						panPage.add(panDiv1, BorderLayout.LINE_START);
						panDiv1.setPreferredSize(new Dimension(410, 0));
						panDiv1.setBorder(JTBorderFactory.createTitleBorder("Company", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
						{
							{
								lookupCompany = new _JLookup(null, "Company", 0, 2);
								panDiv1.add(lookupCompany, BorderLayout.LINE_START);
								lookupCompany.setLookupSQL(SQL_COMPANY());
								lookupCompany.setReturnColumn(0);
								lookupCompany.setFont(font11);
								lookupCompany.setPreferredSize(new Dimension(75, 0));
								lookupCompany.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup)event.getSource()).getDataSet();
										if(data != null){

											co_id = (String) data[0];	
											company = (String) data[1];					
											tagCompany.setTag(company);
											company_logo = (String) data[3];

											KEYBOARD_MANAGER.focusNextComponent();							

											enableButtons(true, false, false, false, true, true);
											displayPF_main(modelPF_main, rowHeader_PFmain, "");

										}
									}
								});
							}
							{
								tagCompany = new _JTagLabel("[ ]");
								panDiv1.add(tagCompany, BorderLayout.CENTER);
								tagCompany.setHorizontalAlignment(_JTagLabel.CENTER);
								tagCompany.setFont(font11);
							}
						}
					}
					{
						JXPanel panDiv2 = new JXPanel(new BorderLayout(5, 5));
						panPage.add(panDiv2, BorderLayout.CENTER);
						panDiv2.setBorder(JTBorderFactory.createTitleBorder("Process Type", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
						{
							cboType = new JComboBox(new String[] {
									"1 - Payments"
							});
							panDiv2.add(cboType, BorderLayout.CENTER);
						}
					}
					{
						JXPanel panDiv3 = new JXPanel(new BorderLayout(5, 5));
						panPage.add(panDiv3, BorderLayout.CENTER);
						panDiv3.setBorder(JTBorderFactory.createTitleBorder("Search", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
						{
							txtSearch = new JXTextField("");
							panDiv3.add(txtSearch);
							txtSearch.setEnabled(false); 
							txtSearch.setEditable(false); 
							txtSearch.setHorizontalAlignment(JTextField.CENTER);	
							txtSearch.addKeyListener(new KeyAdapter() {
								public void keyReleased(KeyEvent e) {	
									displayPF_main(modelPF_main, rowHeader_PFmain, txtSearch.getText());							
								}				 
							});					
						}
					}
				}	
			}
		}
		{
			pnlNew_PF = new JPanel();
			pnlNew_PF.setLayout(new BorderLayout(5, 5));	
			pnlNew_PF.setPreferredSize(new java.awt.Dimension(382, 210));		
			{		
				pnlNew_PF_a = new JPanel(new BorderLayout(5, 5));
				pnlNew_PF.add(pnlNew_PF_a, BorderLayout.NORTH);				
				pnlNew_PF_a.setPreferredSize(new java.awt.Dimension(921, 41));
				pnlNew_PF_a.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
				pnlNew_PF_a.setPreferredSize(new java.awt.Dimension(380, 170));		
				{
					pnlNew_PF_a1 = new JPanel(new GridLayout(6, 1, 5, 5));
					pnlNew_PF_a.add(pnlNew_PF_a1, BorderLayout.WEST);				
					pnlNew_PF_a1.setPreferredSize(new java.awt.Dimension(921, 41));
					pnlNew_PF_a1.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
					pnlNew_PF_a1.setPreferredSize(new java.awt.Dimension(85, 145));		
					{
						lblPF_no_main = new JLabel("PF No.", JLabel.TRAILING);
						pnlNew_PF_a1.add(lblPF_no_main);
						lblPF_no_main.setEnabled(true);	
						lblPF_no_main.setPreferredSize(new java.awt.Dimension(136, 24));
					}	
					{
						lblTransDesc = new JLabel("Transaction", JLabel.TRAILING);
						pnlNew_PF_a1.add(lblTransDesc);
						lblTransDesc.setEnabled(true);	
						lblTransDesc.setPreferredSize(new java.awt.Dimension(136, 24));
					}	
					{
						lblPaymentPart = new JLabel("Payment", JLabel.TRAILING);
						pnlNew_PF_a1.add(lblPaymentPart);
						lblPaymentPart.setEnabled(true);	
						lblPaymentPart.setPreferredSize(new java.awt.Dimension(136, 24));
					}	
					{
						lblCostPart = new JLabel("Cost", JLabel.TRAILING);
						pnlNew_PF_a1.add(lblCostPart);
						lblCostPart.setEnabled(true);	
						lblCostPart.setPreferredSize(new java.awt.Dimension(136, 24));
					}	
					{
						lblReceiptType = new JLabel("Receipt Type", JLabel.TRAILING);
						pnlNew_PF_a1.add(lblReceiptType);
						lblReceiptType.setEnabled(true);	
						lblReceiptType.setPreferredSize(new java.awt.Dimension(136, 24));
					}
					{
						lblStatus = new JLabel("Status", JLabel.TRAILING);
						pnlNew_PF_a1.add(lblStatus);
						lblStatus.setEnabled(true);	
						lblStatus.setPreferredSize(new java.awt.Dimension(136, 24));
					}
				}
				{
					pnlNew_PF_a2 = new JPanel(new GridLayout(6, 1, 0, 5));
					pnlNew_PF_a.add(pnlNew_PF_a2, BorderLayout.CENTER);				
					pnlNew_PF_a2.setPreferredSize(new java.awt.Dimension(921, 41));
					pnlNew_PF_a2.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
					pnlNew_PF_a2.setPreferredSize(new java.awt.Dimension(200, 150));
					
					{
						txtPF_no_main = new JXTextField("");
						pnlNew_PF_a2.add(txtPF_no_main);
						txtPF_no_main.setEnabled(true);	
						txtPF_no_main.setEditable(false);
						txtPF_no_main.setBounds(120, 25, 300, 22);	
						txtPF_no_main.setHorizontalAlignment(JTextField.CENTER);
						txtPF_no_main.setFont(new java.awt.Font("Segoe UI",Font.BOLD,12));
					}	
					{
						txtTransDesc = new JXTextField("");
						pnlNew_PF_a2.add(txtTransDesc);
						txtTransDesc.setEnabled(true);	
						txtTransDesc.setEditable(true);
						txtTransDesc.setBounds(120, 25, 300, 22);	
						txtTransDesc.setHorizontalAlignment(JTextField.CENTER);
						txtTransDesc.setFont(new java.awt.Font("Segoe UI",Font.BOLD,12));
					}						
					{
						lookupPmtPart = new _JLookup(null, "Payment", 2, 2);
						pnlNew_PF_a2.add(lookupPmtPart);
						lookupPmtPart.setBounds(20, 27, 20, 25);
						lookupPmtPart.setReturnColumn(0);
						lookupPmtPart.setFilterName(true);
						lookupPmtPart.setEnabled(true);	
						lookupPmtPart.setLookupSQL(getPaymentPart());
						lookupPmtPart.setPreferredSize(new java.awt.Dimension(157, 22));
						lookupPmtPart.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){		

								}
							}
						});	
					}					
					{
						lookupCostPart = new _JLookup(null, "Cost", 2, 2);
						pnlNew_PF_a2.add(lookupCostPart);
						lookupCostPart.setBounds(20, 27, 20, 25);
						lookupCostPart.setReturnColumn(0);
						lookupCostPart.setFilterName(true);
						lookupCostPart.setEnabled(true);	
						lookupCostPart.setLookupSQL(getCostPart());
						lookupCostPart.setPreferredSize(new java.awt.Dimension(157, 22));
						lookupCostPart.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();

								if(data != null){		

								}
							}
						});	
					}
					{
						String status[] = {"AR","OR","PIR","NA"};					
						cmbReceiptType = new JComboBox(status);
						pnlNew_PF_a2.add(cmbReceiptType);
						cmbReceiptType.setBounds(537, 15, 160, 21);	
						cmbReceiptType.setEnabled(true);
						cmbReceiptType.setSelectedIndex(0);	
						cmbReceiptType.setPreferredSize(new java.awt.Dimension(89, 26));
						cmbReceiptType.addItemListener(new ItemListener() {
							public void itemStateChanged(ItemEvent evt) {

							}
						});		
					}
					{
						String status[] = {"A","I"};					
						cmbStatus = new JComboBox(status);
						pnlNew_PF_a2.add(cmbStatus);
						cmbStatus.setBounds(537, 15, 160, 21);	
						cmbStatus.setEnabled(true);
						cmbStatus.setSelectedIndex(0);	
						cmbStatus.setPreferredSize(new java.awt.Dimension(89, 26));
						cmbStatus.addItemListener(new ItemListener() {
							public void itemStateChanged(ItemEvent evt) {

							}
						});		
					}
				}
				{
					pnlNew_PF_c = new JPanel(new BorderLayout(5, 5));
					pnlNew_PF.add(pnlNew_PF_c, BorderLayout.CENTER);				
					pnlNew_PF_c.setPreferredSize(new java.awt.Dimension(921, 41));
					pnlNew_PF_c.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
					pnlNew_PF_c.setPreferredSize(new java.awt.Dimension(500, 40));	
					{
						btnSave = new JButton("Save");
						pnlNew_PF_c.add(btnSave);
						btnSave.setActionCommand("Save");
						btnSave.addActionListener(this);
						btnSave.setEnabled(true);
						btnSave.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								
								saveNewPF();
								Window optionPaneWindow = SwingUtilities.getWindowAncestor(pnlNew_PF_c);
								optionPaneWindow.dispose();
								
							}
						});
					}	
				}
			}
		}
		{

			pnlNew_Entries = new JPanel();
			pnlNew_Entries.setLayout(new BorderLayout(5, 5));
			pnlNew_Entries.setPreferredSize(new java.awt.Dimension(382, 210));
			{
				pnlNew_Entries_a = new JPanel(new BorderLayout(5, 5));
				pnlNew_Entries.add(pnlNew_Entries_a, BorderLayout.NORTH);
				pnlNew_Entries_a.setPreferredSize(new java.awt.Dimension(921, 41));
				pnlNew_Entries_a.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
				pnlNew_Entries_a.setPreferredSize(new java.awt.Dimension(380, 170));
				{
					pnlNew_Entries_a1 = new JPanel(new GridLayout(5, 1, 5, 5));
					pnlNew_Entries_a.add(pnlNew_Entries_a1, BorderLayout.WEST);	
					pnlNew_Entries_a1.setPreferredSize(new java.awt.Dimension(921, 41));
					pnlNew_Entries_a1.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
					pnlNew_Entries_a1.setPreferredSize(new java.awt.Dimension(85, 145));
					{
						lblPF_no = new JLabel("PF No.", JLabel.TRAILING);
						pnlNew_Entries_a1.add(lblPF_no);
						lblPF_no.setEnabled(true);	
						lblPF_no.setPreferredSize(new java.awt.Dimension(136, 24));
					}	
					{
						lblDebit = new JLabel("Debit Acct ID", JLabel.TRAILING);
						pnlNew_Entries_a1.add(lblDebit);
						lblDebit.setEnabled(true);	
						lblDebit.setPreferredSize(new java.awt.Dimension(136, 24));
					}	
					{
						lblCredit = new JLabel("Credit Acct ID", JLabel.TRAILING);
						pnlNew_Entries_a1.add(lblCredit);
						lblCredit.setEnabled(true);	
						lblCredit.setPreferredSize(new java.awt.Dimension(136, 24));
					}	
					{
						lblRemarks = new JLabel("Remarks", JLabel.TRAILING);
						pnlNew_Entries_a1.add(lblRemarks);
						lblRemarks.setEnabled(true);	
						lblRemarks.setPreferredSize(new java.awt.Dimension(136, 24));
					}
					{
						lblStatus = new JLabel("Status", JLabel.TRAILING);
						pnlNew_Entries_a1.add(lblStatus);
						lblStatus.setEnabled(true);	
						lblStatus.setPreferredSize(new java.awt.Dimension(136, 24));
					}
				}
				{
					pnlNew_Entries_a2 = new JPanel(new GridLayout(5, 1, 0, 5));
					pnlNew_Entries_a.add(pnlNew_Entries_a2, BorderLayout.CENTER);				
					pnlNew_Entries_a2.setPreferredSize(new java.awt.Dimension(921, 41));
					pnlNew_Entries_a2.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
					pnlNew_Entries_a2.setPreferredSize(new java.awt.Dimension(200, 150));
					{
						txtPF_no = new JXTextField("");
						pnlNew_Entries_a2.add(txtPF_no);
						txtPF_no.setEnabled(true);	
						txtPF_no.setEditable(false);
						txtPF_no.setBounds(120, 25, 300, 22);	
						txtPF_no.setHorizontalAlignment(JTextField.CENTER);
						txtPF_no.setFont(new java.awt.Font("Segoe UI",Font.BOLD,12));
					}						
					{
						lookupDebit = new _JLookup(null, "Debit", 2, 2);
						pnlNew_Entries_a2.add(lookupDebit);
						lookupDebit.setBounds(20, 27, 20, 25);
						lookupDebit.setReturnColumn(0);
						lookupDebit.setFilterName(true);
						lookupDebit.setEnabled(true);	
						lookupDebit.setLookupSQL(getListofAcct());
						lookupDebit.setPreferredSize(new java.awt.Dimension(157, 22));
						lookupDebit.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if (data != null) {

								}
							}
						});	
					}					
					{
						lookupCredit = new _JLookup(null, "Credit", 2, 2);
						pnlNew_Entries_a2.add(lookupCredit);
						lookupCredit.setBounds(20, 27, 20, 25);
						lookupCredit.setReturnColumn(0);
						lookupCredit.setFilterName(true);
						lookupCredit.setEnabled(true);	
						lookupCredit.setLookupSQL(getListofAcct());
						lookupCredit.setPreferredSize(new java.awt.Dimension(157, 22));
						lookupCredit.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){		


								}
							}
						});	
					}
					{
						txtRemarks = new JXTextField("");
						pnlNew_Entries_a2.add(txtRemarks);
						txtRemarks.setEnabled(true);	
						txtRemarks.setEditable(true);
						txtRemarks.setBounds(120, 25, 300, 22);	
						txtRemarks.setHorizontalAlignment(JTextField.CENTER);
						txtRemarks.setFont(new java.awt.Font("Segoe UI",Font.BOLD,12));
					}	
					{
						String status[] = {"A","I"};					
						cmbStatus = new JComboBox(status);
						pnlNew_Entries_a2.add(cmbStatus);
						cmbStatus.setBounds(537, 15, 160, 21);	
						cmbStatus.setEnabled(true);
						cmbStatus.setSelectedIndex(0);	
						cmbStatus.setPreferredSize(new java.awt.Dimension(89, 26));
						cmbStatus.addItemListener(new ItemListener() {
							public void itemStateChanged(ItemEvent evt) 
							{

							}
						});		
					}
				}
				{
					pnlNew_Entries_c = new JPanel(new BorderLayout(5, 5));
					pnlNew_Entries.add(pnlNew_Entries_c, BorderLayout.CENTER);				
					pnlNew_Entries_c.setPreferredSize(new java.awt.Dimension(921, 41));
					pnlNew_Entries_c.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
					pnlNew_Entries_c.setPreferredSize(new java.awt.Dimension(500, 40));	
					{
						btnSave = new JButton("Save");
						pnlNew_Entries_c.add(btnSave);
						btnSave.setActionCommand("Save");
						btnSave.addActionListener(this);
						btnSave.setEnabled(true);
						btnSave.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								
								saveNewEntries();
								Window optionPaneWindow = SwingUtilities.getWindowAncestor(pnlNew_Entries);
								optionPaneWindow.dispose();
								
							}
						});
					}	
				}
			}
		}
		{
			JXPanel panCenter = new JXPanel(new BorderLayout(5, 5));
			pnlMain.add(panCenter, BorderLayout.CENTER);	
			{
				{
					tabProForma = new _JTabbedPane(); 
					panCenter.add(tabProForma, BorderLayout.CENTER); 
					{
						pf_payments = new proForma_payments(this); 
						JXPanel panRefund = new JXPanel(new BorderLayout(5, 5)); 
						
						tabProForma.addTab("          Payments          ", null, pf_payments, null);
						tabProForma.addTab("      Late OR Issuance      ", null, panRefund, null);
					}
				}
				{
					JXPanel panDiv2 = new JXPanel(new GridLayout(1, 2, 5, 5)); 
					panCenter.add(panDiv2, BorderLayout.PAGE_END);
					panDiv2.setPreferredSize(new Dimension(0, 175));
					{
						{
							JXPanel panDiv3 = new JXPanel(new BorderLayout(5, 5)); 
							panDiv2.add(panDiv3);
							panDiv3.setBorder(JTBorderFactory.createTitleBorder("Pro-Forma Entries", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
							{
								scrProForma = new JScrollPane();
								panDiv3.add(scrProForma, BorderLayout.CENTER);
								{
									{
										modelProForma = new model_proforma_entries();
										tblProForma = new _JTableMain(modelProForma);
										scrProForma.setViewportView(tblProForma);
										scrProForma.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
										
										tblProForma.getColumnModel().getColumn(0).setPreferredWidth(75);
										tblProForma.getColumnModel().getColumn(1).setPreferredWidth(110);
										tblProForma.getColumnModel().getColumn(2).setPreferredWidth(250);
										tblProForma.getColumnModel().getColumn(3).setPreferredWidth(80);
										tblProForma.setSortable(false);
									}
									{
										rowProForma = tblProForma.getRowHeader();
										rowProForma.setModel(new DefaultListModel());
										scrProForma.setRowHeaderView(rowProForma);
										scrProForma.setCorner(JScrollPane.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
									}	
								}
							}
						}
						{
							JXPanel panDiv4 = new JXPanel(new BorderLayout(5, 5)); 
							panDiv2.add(panDiv4); 
							{
								{
									JXPanel panRemarks = new JXPanel(new BorderLayout(5, 5)); 
									panDiv4.add(panRemarks, BorderLayout.CENTER); 
									panRemarks.setBorder(JTBorderFactory.createTitleBorder("Remarks", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
								}
								{
									JXPanel panLineEnd = new JXPanel(new GridLayout(5, 1, 2, 2)); 
									panDiv4.add(panLineEnd, BorderLayout.LINE_END); 
									panLineEnd.setPreferredSize(new Dimension(225, 0));
									{
										{
											btnEditTrans = new JButton("Edit Trans");
											//panLineEnd.add(btnEditTrans);
											btnEditTrans.setActionCommand("EditTrans");
											btnEditTrans.addActionListener(this);
											btnEditTrans.setEnabled(false);
											btnEditTrans.setVisible(false);
										}
										{
											btnEditEntries = new JButton("Edit Entries");
											panLineEnd.add(btnEditEntries);
											btnEditEntries.setActionCommand("EditEntries");
											btnEditEntries.addActionListener(this);
											btnEditEntries.setEnabled(false);
											btnEditEntries.setVisible(false);
										}
										{
											btnPreview = new JButton("Preview");
											panLineEnd.add(btnPreview);
											btnPreview.setActionCommand("Preview");
											btnPreview.addActionListener(this);
											btnPreview.setEnabled(true);
											btnPreview.setVisible(false);
										}
										{
											btnCancel = new JButton("Cancel");
											panLineEnd.add(btnCancel);
											btnCancel.setActionCommand("Cancel");
											btnCancel.addActionListener(this);
											btnCancel.setEnabled(false);
											btnCancel.setVisible(false);
										}
										{
											btnAddTrans = new JButton("Add New Trans");
											panLineEnd.add(btnAddTrans);
											btnAddTrans.setActionCommand("AddTrans");
											btnAddTrans.addActionListener(this);
										}
										{
											btnAddEntries = new JButton("Add Entries");
											panLineEnd.add(btnAddEntries);
											btnAddEntries.setActionCommand("AddEntries");
											btnAddEntries.addActionListener(this);
										}
									}
								}
							}
						}
					}
				}
				/*
				splitPanel = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
				panCenter.add(splitPanel);
				splitPanel.setOneTouchExpandable(true);
				splitPanel.setResizeWeight(.7d);
				{
					{
						JXPanel panTab = new JXPanel(new BorderLayout(5, 5)); 
						splitPanel.add(panTab, JSplitPane.LEFT); 
						{
							tabProForma = new _JTabbedPane(); 
							panTab.add(tabProForma, BorderLayout.CENTER);
							{
								pf_payments = new proForma_payments(this); 
								tabProForma.addTab("Payments", null, pf_payments, null);
							}
						}						
					}
					{
						pnlPF_maintable = new JPanel(new BorderLayout(5, 5));
						splitPanel.add(pnlPF_maintable, JSplitPane.LEFT);
						pnlPF_maintable.setPreferredSize(new java.awt.Dimension(923, 321));
						{
							pnlPF = new JPanel(new BorderLayout());
							pnlPF_maintable.add(pnlPF, BorderLayout.CENTER);
							pnlPF.setBorder(JTBorderFactory.createTitleBorder("Process", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
							{
								scrollPF_entries = new _JScrollPaneMain();
								pnlPF.add(scrollPF_entries, BorderLayout.CENTER);
								{
									modelPF_main = new model_proforma_payments();
									tblPF_main = new _JTableMain(modelPF_main);
									scrollPF_entries.setViewportView(tblPF_main);
									tblPF_main.setSortable(false);
									tblPF_main.getColumnModel().getColumn(1).setPreferredWidth(80);
									tblPF_main.getColumnModel().getColumn(2).setPreferredWidth(200);	
									tblPF_main.addMouseListener(new MouseAdapter() {
										public void mousePressed(MouseEvent e) {
											clickTable();
										}							
									});
									tblPF_main.addKeyListener(new KeyAdapter() {
										public void keyReleased(KeyEvent evt) {	
											clickTable();
										}

										public void keyPressed(KeyEvent e) {
											clickTable();
										}
									}); 
								}
								{
									rowHeader_PFmain = tblPF_main.getRowHeader22();
									scrollPF_entries.setRowHeaderView(rowHeader_PFmain);
									scrollPF_entries.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
								}
								{
									scrollPF_main_total = new _JScrollPaneTotal(scrollPF_entries);
									pnlPF.add(scrollPF_main_total, BorderLayout.SOUTH);
									{
										modelPF_main_total = new model_PF_entries();
										modelPF_main_total.addRow(new Object[] { "Total", new BigDecimal(0.00) });
										
										tblPF_main_total = new _JTableTotal(modelPF_main_total, tblPF_main);
										tblPF_main_total.setFont(dialog11Bold);
										scrollPF_main_total.setViewportView(tblPF_main_total);
										((_JTableTotal) tblPF_main_total).setTotalLabel(0);
									}
								}
							}				
						}
					}
					{
						pnlPF_sub = new JPanel(new BorderLayout(5, 5));
						splitPanel.add(pnlPF_sub, JSplitPane.RIGHT);
						pnlPF_sub.setPreferredSize(new java.awt.Dimension(923, 195));
						pnlPF_sub.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
						{
							pnlPF_sub_b = new JPanel(new GridLayout(1, 1, 0, 0));
							pnlPF_sub.add(pnlPF_sub_b, BorderLayout.CENTER);
							pnlPF_sub_b.setPreferredSize(new java.awt.Dimension(400, 255));	
							pnlPF_sub_b.setBorder(JTBorderFactory.createTitleBorder("Pro-Forma Entries", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
							{
								pnlSub = new JPanel(new BorderLayout());
								pnlPF_sub_b.add(pnlSub, BorderLayout.CENTER);
								pnlSub.setPreferredSize(new java.awt.Dimension(401, 253));				

								{
									scrollPF_sub = new _JScrollPaneMain();
									pnlSub.add(scrollPF_sub, BorderLayout.CENTER);
									{
										modelPF_sub = new model_PF_entries_sub();
										tblPF_sub = new _JTableMain(modelPF_sub);
										scrollPF_sub.setViewportView(tblPF_sub);
										tblPF_sub.addMouseListener(new MouseAdapter() {
											public void mousePressed(MouseEvent e) {
												if ((e.getClickCount() >= 2)) {
													
												}
											}
											
											public void mouseReleased(MouseEvent e) {
												if ((e.getClickCount() >= 2)) {
													
												}
											}
										});

										tblPF_sub.addKeyListener(new KeyAdapter() {
											public void keyReleased(KeyEvent evt) {
												
											}
											
											public void keyPressed(KeyEvent e) {
												
											}
										});
										
										tblPF_sub.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
											public void valueChanged(ListSelectionEvent e) {
												if (!e.getValueIsAdjusting()) {
													enableButtons(false, false, false, true, true, true);
												}
											}
										});
									}
									{
										rowHeader_PF_sub = tblPF_sub.getRowHeader22();
										scrollPF_sub.setRowHeaderView(rowHeader_PF_sub);
										scrollPF_sub.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
									}
									{
										scrollPF_sub_total = new _JScrollPaneTotal(scrollPF_entries);
										pnlSub.add(scrollPF_sub_total, BorderLayout.SOUTH);
										{
											modelPF_sub_total = new model_PF_entries_sub();
											modelPF_sub_total.addRow(new Object[] { "Total", new BigDecimal(0.00) });

											tblPF_sub_total = new _JTableTotal(modelPF_sub_total, tblPF_sub);
											tblPF_sub_total.setFont(dialog11Bold);
											scrollPF_sub_total.setViewportView(tblPF_sub_total);
											((_JTableTotal) tblPF_sub_total).setTotalLabel(0);
										}
									}
								}
							}	
						}						
					}
				}
				*/
			}
		}
		/*
		{
			JXPanel panEnd = new JXPanel(new GridLayout(1, 6, 5, 5));
			pnlMain.add(panEnd, BorderLayout.SOUTH);
			panEnd.setPreferredSize(new Dimension(0, 30));
			{
				{
					btnAddTrans = new JButton("Add New Trans");
					panEnd.add(btnAddTrans);
					btnAddTrans.setActionCommand("AddTrans");
					btnAddTrans.addActionListener(this);
					btnAddTrans.setEnabled(false);
				}
				{
					btnEditTrans = new JButton("Edit Trans");
					panEnd.add(btnEditTrans);
					btnEditTrans.setActionCommand("EditTrans");
					btnEditTrans.addActionListener(this);
					btnEditTrans.setEnabled(false);
				}
				{
					btnAddEntries = new JButton("Add Entries");
					panEnd.add(btnAddEntries);
					btnAddEntries.setActionCommand("AddEntries");
					btnAddEntries.addActionListener(this);
					btnAddEntries.setEnabled(false);
				}				
				{
					btnEditEntries = new JButton("Edit Entries");
					panEnd.add(btnEditEntries);
					btnEditEntries.setActionCommand("EditEntries");
					btnEditEntries.addActionListener(this);
					btnEditEntries.setEnabled(false);
				}
				{
					btnPreview = new JButton("Preview");
					panEnd.add(btnPreview);
					btnPreview.setActionCommand("Preview");
					btnPreview.addActionListener(this);
					btnPreview.setEnabled(true);
				}
				{
					btnCancel = new JButton("Cancel");
					panEnd.add(btnCancel);
					btnCancel.setActionCommand("Cancel");
					btnCancel.addActionListener(this);
					btnCancel.setEnabled(false);
				}
			}
		}
		*/
		
		//added 01/26/2016 - purpose - set CENQHOMES as default company
		initialize_comp();
		proForma_payments.display_proforma_categories(); 
	}


	//display
	private void displayPF_main(DefaultTableModel modelMain, JList rowHeader, String text_search) {

		FncTables.clearTable(modelMain);//Code to clear modelMain.
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		String sql = 				
			"select \r\n" + 
			"\r\n" + 
			"a.pf_no,\r\n" + 
			"a.trans_desc,\r\n" + 
			"a.pmt_particular,\r\n" + 
			"a.cost_particular,\r\n" + 
			"a.receipt_doc_id,\r\n" + 
			"a.status_id,\r\n" + 
			"get_employee_name(a.created_by),\r\n" + 
			"a.date_created,\r\n" + 
			"get_employee_name(a.edited_by),\r\n" + 
			"a.date_edited,\r\n" + 
			"get_employee_name(a.canceled_by),\r\n" + 
			"a.date_canceled\r\n" + 
			"\r\n" + 
			"from mf_proforma_entries_hd a " +
			"where (case when '"+text_search+"' = '' then true else upper(a.trans_desc) like '%"+text_search.toUpperCase().replace("'","''")+"%' end)   " +
			"order by pf_no ";

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

		tblPF_main.packAll();	
		adjustRowHeight(tblPF_main);

		int row = tblPF_main.getRowCount();			
		modelPF_main_total.setValueAt(new BigDecimal(row), 0, 1);
		
	}
	
	private void displayPF_sub(DefaultTableModel modelMain, JList rowHeader, String pf_no) {

		FncTables.clearTable(modelMain);//Code to clear modelMain.
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		String sql = 				
			"select * from (" +
			"select \r\n" + 
			"\r\n" + 
			"a.debit_acct_id,\r\n" + 
			"a.credit_acct_id,\r\n" + 
			"(case when coalesce(a.debit_acct_id,'') = '' then c.acct_name else b.acct_name end) as acct_name,\r\n" + 
			"a.remarks,\r\n" +
			"a.status_id," +
			"(case when a.debit_acct_id = '' then 2 else 1 end) as order \r\n" + 
			"\r\n" + 
			"from mf_proforma_entries_dl a\r\n" + 
			"left join mf_boi_chart_of_accounts b on a.debit_acct_id = b.acct_id\r\n" + 
			"left join mf_boi_chart_of_accounts c on a.credit_acct_id = c.acct_id\r\n" +
			"where pf_no = '"+pf_no+"' " +
			"and a.status_id = 'A' " +
			") a " +
			"order by a.order, a.debit_acct_id, a.credit_acct_id " + 
			"" ;

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

		tblPF_sub.packAll();	
		adjustRowHeight(tblPF_sub);

		int row_tot = tblPF_sub.getRowCount();	
		modelPF_sub_total.setValueAt(new BigDecimal(row_tot), 0, 1);		

	}
	

	//Enable/Disable all components inside JPanel
	private void enable_fields(Boolean x){	
		//lblSearch.setEnabled(x);	
		//txtSearch.setEnabled(x);	
		//txtSearch.setEditable(x);
	}

	private void refresh_fieldsAddNew(){

		lookupPmtPart.setValue("");
		txtTransDesc.setText("");
		lookupPmtPart.setValue("");
		lookupCostPart.setValue("");
		cmbReceiptType.setSelectedIndex(0);	
		cmbStatus.setSelectedIndex(0);	
		lookupDebit.setValue("");
		lookupCredit.setValue("");
		txtRemarks.setText("");
		cmbStatus.setSelectedIndex(0);
		pnlPF_sub_b.setBorder(JTBorderFactory.createTitleBorder("Pro-Forma Entries [ ]" ));
	}

	private void refresh_tablesMain(){

		//reset table 1
		FncTables.clearTable(modelPF_main);
		FncTables.clearTable(modelPF_main_total);			
		rowHeader_PFmain = tblPF_main.getRowHeader22();
		scrollPF_entries.setRowHeaderView(rowHeader_PFmain);	
		modelPF_main_total.addRow(new Object[] { "Total", new BigDecimal(0.00) });

		//reset table 2
		FncTables.clearTable(modelPF_sub);
		FncTables.clearTable(modelPF_sub_total);			
		rowHeader_PF_sub = tblPF_sub.getRowHeader22();
		scrollPF_sub.setRowHeaderView(rowHeader_PF_sub);	
		modelPF_sub_total.addRow(new Object[] { "Total", new BigDecimal(0.00) });
		
		
	}

	private void enableButtons(Boolean a, Boolean b, Boolean c, Boolean d, Boolean e, Boolean f ) {
		/*
		btnAddTrans.setEnabled(a);
		btnEditTrans.setEnabled(b);
		btnAddEntries.setEnabled(c);
		btnEditEntries.setEnabled(d);
		btnPreview.setEnabled(e);
		btnCancel.setEnabled(f);
		*/
	}

	private void initialize_comp(){		
		
		co_id 		= "02";	
		company		= "CENQHOMES DEVELOPMENT CORPORATION";			
		tagCompany.setTag(company);
		company_logo = RequestForPayment.sql_getCompanyLogo();	

		KEYBOARD_MANAGER.focusNextComponent();							

		enable_fields(true);
		enableButtons(true, false, false, false, true, true);
		//displayPF_main(modelPF_main, rowHeader_PFmain, "");
		
		lookupCompany.setValue(co_id);
}
	

	//action performed
	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getActionCommand().equals("Cancel")){

			enable_fields(true);	
			refresh_tablesMain();
			enableButtons(true, false, false, false, false, true);
			txtSearch.setText("");	
			displayPF_main(modelPF_main, rowHeader_PFmain, "");
			refresh_fieldsAddNew();
			
		}

		if (e.getActionCommand().equals("AddTrans")) { addNew_PF(); }	
		
		if (e.getActionCommand().equals("AddEntries")) { addNew_Entries(); }	

		if (e.getActionCommand().equals("EditTrans")) { edit_PF(); }	
		
		if (e.getActionCommand().equals("EditEntries")) { edit_Entries(); }	
		
		if (e.getActionCommand().equals("Preview")) { 
			
			String criteria = "Pro-Forma Entries";		
			String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());			

			Map<String, Object> mapParameters = new HashMap<String, Object>();
			mapParameters.put("company", company);
			mapParameters.put("co_id", co_id);
			mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
			mapParameters.put("prepared_by", UserInfo.FullName);
			
			FncReport.generateReport("/Reports/rptProFormaEntries.jasper", reportTitle, company, mapParameters); 
			
		
		}	
		
		
	}

	public void mouseClicked(MouseEvent evt) {
		if ((evt.getClickCount() >= 2)) {

		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	private void addNew_PF(){
			
		to_do = "addnew_pf";
		txtPF_no_main.setText(sql_getpf_no().toString());

		int scanOption = JOptionPane.showOptionDialog(getContentPane(), pnlNew_PF, "Add New Transaction",
				JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, new Object[] {}, null);	

		if ( scanOption == JOptionPane.CLOSED_OPTION ) {
		} // CLOSED_OPTION

	}
	
	private void addNew_Entries(){
		
		to_do = "addnew_entries";
		
		int row = tblPF_main.getSelectedRow();		
		
		String pf_no = tblPF_main.getValueAt(row,0).toString().trim();	
		txtPF_no.setText(pf_no);

		int scanOption = JOptionPane.showOptionDialog(getContentPane(), pnlNew_Entries, "Add New Entries",
				JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, new Object[] {}, null);	

		if ( scanOption == JOptionPane.CLOSED_OPTION ) {
		} // CLOSED_OPTION

	}

	private void saveNewPF(){

		if(checkCompleteDetails()==false)
		{JOptionPane.showMessageDialog(getContentPane(), "Please enter transaction description.", "Incomplete Detail", 
				JOptionPane.INFORMATION_MESSAGE);}
		else {			

			if (JOptionPane.showConfirmDialog(getContentPane(), "Are all entries correct?", "Confirmation", 
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
				pgUpdate db = new pgUpdate();	
				if(to_do.equals("addnew_pf")) 
				{		
					insertNewPF(db);	
					db.commit();	
					JOptionPane.showMessageDialog(getContentPane(),"New PF transaction saved.","Information",JOptionPane.INFORMATION_MESSAGE);
					displayPF_main(modelPF_main, rowHeader_PFmain, "");
				}
				else if(to_do.equals("edit_pf")) 
				{
					updatePF(db);
					db.commit();	
					JOptionPane.showMessageDialog(getContentPane(),"PF Transaction updated.","Information",JOptionPane.INFORMATION_MESSAGE);
					displayPF_main(modelPF_main, rowHeader_PFmain, "");
				}
			}}	
	}
	
	private void saveNewEntries(){

		if(lookupDebit.getText().equals("")&&lookupCredit.getText().equals(""))
		{JOptionPane.showMessageDialog(getContentPane(), "Enter a debit or credit entry.", "Incomplete Detail", 
				JOptionPane.INFORMATION_MESSAGE);}
		else {			

			if (JOptionPane.showConfirmDialog(getContentPane(), "Are all entries correct?", "Confirmation", 
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
				pgUpdate db = new pgUpdate();	
				if(to_do.equals("addnew_entries")) 
				{		
					insertNewEntries(db);
					db.commit();	
					JOptionPane.showMessageDialog(getContentPane(),"New PF entries saved.","Information",JOptionPane.INFORMATION_MESSAGE);
					int row = tblPF_main.getSelectedRow();										
					String pf_no = tblPF_main.getValueAt(row,0).toString().trim();	
					displayPF_sub(modelPF_sub, rowHeader_PF_sub, pf_no);
				}
				else if(to_do.equals("edit_entries")) 
				{
					updateEntries(db);
					db.commit();	
					JOptionPane.showMessageDialog(getContentPane(),"PF Entry updated.","Information",JOptionPane.INFORMATION_MESSAGE);
					int row = tblPF_main.getSelectedRow();										
					String pf_no = tblPF_main.getValueAt(row,0).toString().trim();	
					displayPF_sub(modelPF_sub, rowHeader_PF_sub, pf_no);
				}
				

			}}	
	}

	private void edit_PF(){
		
		int row = tblPF_main.getSelectedRow();			

		String pf_no 	 = tblPF_main.getValueAt(row,0).toString().trim();	
		String pf_desc 	 = tblPF_main.getValueAt(row,1).toString().trim();	
		String pmt_part  = tblPF_main.getValueAt(row,2).toString().trim();
		String cost_part = tblPF_main.getValueAt(row,3).toString().trim();
		String receipt_type    = tblPF_main.getValueAt(row,4).toString().trim();
		String status    = tblPF_main.getValueAt(row,5).toString().trim();

		txtPF_no_main.setText(pf_no);
		txtTransDesc.setText(pf_desc);
		lookupPmtPart.setText(pmt_part);
		lookupCostPart.setText(cost_part);
		
		if (receipt_type.equals("AR")) {cmbReceiptType.setSelectedIndex(0);}
		else if (receipt_type.equals("OR")) {cmbReceiptType.setSelectedIndex(1);}
		else if (receipt_type.equals("PIR")) {cmbReceiptType.setSelectedIndex(2);}
		else if (receipt_type.equals("None")) {cmbReceiptType.setSelectedIndex(3);}

		if (status.equals("A")) {cmbStatus.setSelectedIndex(0);} else {cmbStatus.setSelectedIndex(1);}
		
		to_do = "edit_pf";

		int scanOption = JOptionPane.showOptionDialog(getContentPane(), pnlNew_PF, "Edit Transaction",
				JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, new Object[] {}, null);	
		
		if ( scanOption == JOptionPane.CLOSED_OPTION ) {
		} // CLOSED_OPTION
	}
	
	private void edit_Entries(){
		
		int row = tblPF_main.getSelectedRow();			
		String pf_no 	 = tblPF_main.getValueAt(row,0).toString().trim();	
		
		int row2 = tblPF_sub.getSelectedRow();	
		
		String 	debit, credit, remarks, status_id;
		try { debit 	= tblPF_sub.getValueAt(row2,0).toString().trim(); } catch (NullPointerException e) {  debit = ""; }
		try { credit 	= tblPF_sub.getValueAt(row2,1).toString().trim(); } catch (NullPointerException e) {  credit = ""; }
		try { remarks 	= tblPF_sub.getValueAt(row2,3).toString().trim(); } catch (NullPointerException e) {  remarks = ""; }
		try { status_id = tblPF_sub.getValueAt(row2,4).toString().trim(); } catch (NullPointerException e) {  status_id = ""; }
		
		txtPF_no.setText(pf_no);
		lookupDebit.setText(debit);
		lookupCredit.setText(credit);
		txtRemarks.setText(remarks);
		if (status_id.equals("A")) {cmbStatus.setSelectedIndex(0);} else {cmbStatus.setSelectedIndex(1);}
		
		to_do = "edit_entries";

		int scanOption = JOptionPane.showOptionDialog(getContentPane(), pnlNew_Entries, "Edit Entries",
				JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, new Object[] {}, null);	
		
		if ( scanOption == JOptionPane.CLOSED_OPTION ) {
		} // CLOSED_OPTION
	}



	//select, lookup and get statements		
	private String getPaymentPart(){

		String sql = 
			" select particulars, partdesc \r\n" + 
			" from mf_pay_particular \r\n" + 
			" where status_id = 'A' \r\n" + 
			" order by particulars";		
		return sql;

	}
	
	private String getCostPart(){

		String sql = 
			" select * from (\r\n" + 
			"\r\n" + 
			" select tcostdtl_alias as alias, tcostdtl_desc \r\n" + 
			" from mf_transfer_cost_dl \r\n" + 
			" where status_id = 'A' \r\n" + 
			"\r\n" + 
			" UNION ALL \r\n" + 
			" \r\n" + 
			" select pcostdtl_alias, pcostdtl_desc \r\n" + 
			" from mf_processing_cost_dl \r\n" + 
			" where status_id = 'A' \r\n" + 
			"\r\n" + 
			")a order by alias";		
		return sql;

	}

	private Integer sql_getpf_no() {

		Integer nxt_pf_no = 0;

		String SQL = 
				"select max(coalesce(pf_no,0)) +1  from mf_proforma_entries_hd " ;

		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			if((Integer) db.getResult()[0][0]==null||db.getResult()[0][0].equals("null")) {nxt_pf_no = 1;}
			else {nxt_pf_no = (Integer) db.getResult()[0][0]; }

		}else{
			nxt_pf_no = 1;
		}

		return nxt_pf_no;
	}
	
	private String getListofAcct(){

		String sql = 
			"select acct_id, acct_name from mf_boi_chart_of_accounts where status_id = 'A' and w_subacct is null";		
		return sql;

	}
	
	
	//table operations	
	private void adjustRowHeight(_JTableMain tbl){

		int rw = tbl.getModel().getRowCount();
		int x = 0;

		while (x<rw){			
			tbl.setRowHeight(x, 22);			
			x++;
		}
	}

	private void clickTable(){
		int row = tblPF_main.getSelectedRow();										
		String pf_no = tblPF_main.getValueAt(row,0).toString().trim();	
		String pf_desc = tblPF_main.getValueAt(row,1).toString().trim();	
		displayPF_sub(modelPF_sub, rowHeader_PF_sub, pf_no);
		enableButtons(false, true, true, false, true, true);
		pnlPF_sub_b.setBorder(JTBorderFactory.createTitleBorder("Pro-Forma Entries [ PF No. " + pf_no  + " | " + pf_desc + "]" ));
	}
	

	//save, insert
	private void insertNewPF(pgUpdate db) {

		String pf_desc = txtTransDesc.getText();
		String pmt_part = lookupPmtPart.getText();
		String cost_part = lookupCostPart.getText();
		String receipt_type = cmbReceiptType.getSelectedItem().toString();
		String status_id = cmbStatus.getSelectedItem().toString();
				
		String sqlDetail = 
			"INSERT into mf_proforma_entries_hd values (" +
			""+sql_getpf_no()+"," +		//0
			"'"+pf_desc.replace("'", "''")+"',  \n" +  	//1
			"'"+pmt_part+"',  \n" +  	//2
			"'"+cost_part+"',  \n" +  	//3
			"'"+receipt_type+"',  \n" + //5
			"'"+status_id+"',  \n" +  	//6
			"'"+UserInfo.EmployeeCode+"'," +
			"now()  \n" +  				//7
			")   " ;

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);	
	}
	
	private void insertNewEntries(pgUpdate db) {
				
		String sqlDetail = 
			"INSERT into mf_proforma_entries_dl values (" +
			""+txtPF_no.getText()+"," +		//0
			"'"+lookupDebit.getText()+"',  \n" +  	//1
			"'"+lookupCredit.getText()+"',  \n" +  	//2
			"'A',  \n" +  					//3
			"'"+txtRemarks.getText().replace("'","''")+"',  \n" +  	//4
			"'"+UserInfo.EmployeeCode+"'," +//5
			"now()  \n" +  					//6
			")   " ;

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);	
		lookupDebit.setText("");
		lookupCredit.setText("");
		txtRemarks.setText("");		
	}

	private void updatePF(pgUpdate db) {
		
		String pf_no = txtPF_no_main.getText();
		String pf_desc = txtTransDesc.getText();
		String pmt_part = lookupPmtPart.getText();
		String cost_part = lookupCostPart.getText();
		String receipt_type = cmbReceiptType.getSelectedItem().toString();
		String status_id = cmbStatus.getSelectedItem().toString();
				
		String sqlDetail = 
			"UPDATE mf_proforma_entries_hd set " +
			"trans_desc = '"+pf_desc.replace("'", "''")+"',  \n" +  	//1
			"pmt_particular = '"+pmt_part+"',  \n" +  	//2
			"cost_particular = '"+cost_part+"',  \n" +  //3
			"receipt_doc_id = '"+receipt_type+"',  \n" +//5
			"status_id = '"+status_id+"',  \n" ; 		//6			
			if (status_id.equals("A")) {}
			else 
			{
				sqlDetail = sqlDetail+
				"canceled_by = '"+UserInfo.EmployeeCode+"'," +
				"date_canceled = now(),  \n" ;  		//7
			}
			
			sqlDetail = sqlDetail+
			
			"edited_by = '"+UserInfo.EmployeeCode+"'," +
			"date_edited = now()  \n" +  				//7
			"where pf_no = '"+pf_no+"'   " ;

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);	
	}
	
	private void updateEntries(pgUpdate db) {
		
		int row = tblPF_main.getSelectedRow();			
		String pf_no 	 = tblPF_main.getValueAt(row,0).toString().trim();	
		int row2 = tblPF_sub.getSelectedRow();	
		String 	debit, credit, remarks, status_id;
		try { debit 	= tblPF_sub.getValueAt(row2,0).toString().trim(); } catch (NullPointerException e) {  debit = ""; }
		try { credit 	= tblPF_sub.getValueAt(row2,1).toString().trim(); } catch (NullPointerException e) {  credit = ""; }
		try { remarks 	= tblPF_sub.getValueAt(row2,3).toString().trim(); } catch (NullPointerException e) {  remarks = ""; }
		try { status_id = tblPF_sub.getValueAt(row2,4).toString().trim(); } catch (NullPointerException e) {  status_id = ""; }
		
		String sqlDetail = 
			"update mf_proforma_entries_dl set " +
			"debit_acct_id = '"+lookupDebit.getText()+"',  \n" +  	//1
			"credit_acct_id = '"+lookupCredit.getText()+"',  \n" +  //2
			"status_id = '"+cmbStatus.getSelectedItem()+"',  \n" + //3
			"remarks = '"+txtRemarks.getText().replace("'","''")+"',  \n" ;  	//4
		
			if (cmbStatus.getSelectedItem().equals("A")) {}
			else 
			{
				sqlDetail = sqlDetail+
				"canceled_by = '"+UserInfo.EmployeeCode+"'," +
				"date_canceled = now(),  \n" ;  		//7
			}
			
			sqlDetail = sqlDetail+
			
			"edited_by = '"+UserInfo.EmployeeCode+"'," +
			"date_edited = now()  \n" +
			"where pf_no = '"+pf_no+"' " +
			"and debit_acct_id = '"+debit+"' " +  	
			"and credit_acct_id = '"+credit+"' " +  	
			"and remarks = '"+remarks+"' " +
			"and status_id = '"+status_id+"' " ;

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);	
		lookupDebit.setText("");
		lookupCredit.setText("");
		txtRemarks.setText("");		
	}

	private Boolean checkCompleteDetails(){
		boolean x = false;		

		String pf_desc = "";
		try {
			pf_desc = txtTransDesc.getText();
		} catch (NullPointerException e) { pf_desc 	= ""; }		

		if (pf_desc.equals("")) {
			x=false;
		} else {
			x=true;
		}		

		return x;
	}
	
	public static void display_entries(DefaultTableModel modelMain, JList rowHeader, String pf_no) {
		FncTables.clearTable(modelMain); 
		DefaultListModel listModel = new DefaultListModel(); 
		rowHeader.setModel(listModel); 
		
		String strSQL = "select x.bal_side, x.acct_id, \n" + 
				"(case when x.bal_side = 'C' THEN concat(E'\t', y.acct_name) else y.acct_name end) as acct_name, \n" + 
				"(case when x.bal_side = 'C' THEN concat(E'\t', 'XXX') else 'XXX' end) as amount \n" + 
				"from mf_proforma_entries_detail x \n" + 
				"inner join mf_boi_chart_of_accounts y on x.acct_id = y.acct_id \n" + 
				"where x.pf_no = "+pf_no;

		System.out.println("strSQL: "+strSQL);
		pgSelect db = new pgSelect();
		db.select(strSQL);

		if (db.isNotNull()) {
			for(int x=0; x < db.getRowCount(); x++){
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());
			}			
		}
	}

}
