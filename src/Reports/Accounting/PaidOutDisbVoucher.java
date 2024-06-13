package Reports.Accounting;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JRSortField;
import net.sf.jasperreports.engine.design.JRDesignSortField;
import net.sf.jasperreports.engine.type.SortFieldTypeEnum;
import net.sf.jasperreports.engine.type.SortOrderEnum;
import Accounting.Disbursements.CheckVoucher;
import Accounting.Disbursements.RequestForPayment;
import DateChooser._JDateChooser;
import Functions.FncFocusTraversalPolicy;
import Functions.FncGlobal;
import Functions.FncReport;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;

import components._JInternalFrame;

public class PaidOutDisbVoucher extends _JInternalFrame implements ActionListener, AncestorListener, _GUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1162406788303915011L;

	static String title = "Paid Out Disbursement Voucher";
	static Dimension frameSize = new Dimension(650, 480);
	static Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	static Cursor handCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);

	private JPanel pnlMain;
	private JPanel pnlNorth;
	private JPanel pnlNorthWest;
	private JPanel pnlCenter;
	private JPanel pnlNorthEast;
	private JPanel pnlCenter2;
	private JPanel pnlCriteria2;
	private JPanel pnlA;
	private JPanel pnlB;
	private JPanel pnlSouth;
	
	private JLabel lblPmtType;
	private JLabel lblCompany;
	private JLabel lblDateFrom;
	private JLabel lblSortby;
	private JLabel lblPayee;
	private JLabel lblExclPayee;
	private JLabel lblSummary;
	
	private _JLookup lookupPmtType;
	private _JLookup lookupCompany;
	private _JLookup lookupPayee;
	private _JLookup lookupExclPayee;
	
	private JTextField txtPmtType;
	private JTextField txtCompany;
	private JTextField txtPayee;
	private JTextField txtExclPayee;		
	
	private JButton btnPreview;
	private JButton btnCancel;
	
	String company;
	String company_logo;		

	private _JDateChooser dteDateFrom;

	private JComboBox cmbSortby;	
	private JComboBox cmbSummary;
	DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	
	String co_id = "";
	private JLabel lblBankAcct;
	private _JLookup lookupBankAcct;
	private JTextField txtBankAcct;
	private JLabel lblDateTo;
	private JPanel pnlCriteria3;
	private JCheckBox chkInclAddTIN;
	private _JDateChooser dteDateTo;
	
	String acct_id = "";
	String payee_id = "";
	String excl_payee_id = "";
	String pymnt_type = "";

	private JLabel lblSortby_1;

	private JLabel lblSortby_2;

	private JComboBox cmbSortby2;

	
	public PaidOutDisbVoucher() {
		super(title, true, true, true, true);
		initGUI();
	}

	public PaidOutDisbVoucher(String title) {
		super(title);
		initGUI();
	}

	public PaidOutDisbVoucher(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	@Override
//	public void initGUI() {
//		this.setTitle(title);
//		this.setSize(frameSize);
//		this.setPreferredSize(new java.awt.Dimension(562, 418));
//		this.setLayout(new BorderLayout());
//		this.addAncestorListener(this);
//		{
//			pnlMain = new JPanel(new BorderLayout(5, 5));
//			getContentPane().add(pnlMain, BorderLayout.WEST);
//			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
//			pnlMain.setPreferredSize(new java.awt.Dimension(557, 247));
//			{
//				pnlNorth = new JPanel(new BorderLayout(5, 5));
//				pnlMain.add(pnlNorth, BorderLayout.NORTH);
//				pnlNorth.setPreferredSize(new java.awt.Dimension(547, 260));
//				pnlNorth.setBorder(components.JTBorderFactory.createTitleBorder("Report Details"));// XXX
//				
//				{
//					pnlNorthWest = new JPanel(new GridLayout(5,2, 5, 5));
//					pnlNorth.add(pnlNorthWest, BorderLayout.WEST);
//					pnlNorthWest.setPreferredSize(new java.awt.Dimension(176, 147));
//					{
//						lblCompany = new JLabel("Company", JLabel.TRAILING);
//						pnlNorthWest.add(lblCompany);
//					}
//					{
//						lookupCompany = new _JLookup(null, "Company");
//						pnlNorthWest.add(lookupCompany);
//						lookupCompany.setReturnColumn(0);
//						lookupCompany.setLookupSQL(SQL_COMPANY());
//						lookupCompany.addLookupListener(new LookupListener() {
//							public void lookupPerformed(LookupEvent event) {
//								Object[] data = ((_JLookup)event.getSource()).getDataSet();
//								if(data != null){
//									
//									company = (String) data[1];	
//									company_logo = (String) data[3];
//									
//									txtCompany.setText(company);
//														
//									KEYBOARD_MANAGER.focusNextComponent();
//									btnPreview.setEnabled(true);
//									btnCancel.setEnabled(true);									
//									
//									lblBankAcct.setEnabled(false);
//									lookupBankAcct.setEnabled(false);
//									txtBankAcct.setEnabled(false);
//								}
//							}
//						});
//					}
//					{
//						lblPmtType = new JLabel("Payment Type", JLabel.TRAILING);
//						pnlNorthWest.add(lblPmtType);
//						lblPmtType.setEnabled(false);	
//					}
//					{
//						lookupPmtType = new _JLookup(null, "Project");
//						pnlNorthWest.add(lookupPmtType);
//						lookupPmtType.setReturnColumn(0);
//						lookupPmtType.setFilterName(true);
//						lookupPmtType.setEnabled(false);
//						lookupPmtType.addLookupListener(new LookupListener() {
//							public void lookupPerformed(LookupEvent event) {
//								Object[] data = ((_JLookup)event.getSource()).getDataSet();
//								if(data != null){
//									
//									pymnt_type = data[0].toString();
//									String pmt_type = (String) data[1];						
//									txtPmtType.setText(pmt_type);	
//									
//									if(pymnt_type.equals("A"))
//									{
//										lblBankAcct.setEnabled(false);
//										lookupBankAcct.setEnabled(false);
//										txtBankAcct.setEnabled(false);
//										lookupBankAcct.setText("");
//										txtBankAcct.setText("");
//									}
//									else if(pymnt_type.equals("B"))
//									{
//										lblBankAcct.setEnabled(true);
//										lookupBankAcct.setEnabled(true);
//										txtBankAcct.setEnabled(true);										
//									}
//												
//									KEYBOARD_MANAGER.focusNextComponent();
//								}
//							}
//						});
//					}
//					{
//						lblPayee = new JLabel("Payee", JLabel.TRAILING);
//						pnlNorthWest.add(lblPayee);
//						lblPayee.setEnabled(false);	
//					}
//					{
//						lookupPayee = new _JLookup(null, "Branch");
//						pnlNorthWest.add(lookupPayee);
//						lookupPayee.setReturnColumn(0);
//						lookupPayee.setFilterName(true);
//						lookupPayee.setEnabled(false);
//						//lookupPayee.setLookupSQL(SQL_COMPANY());
//						lookupPayee.addLookupListener(new LookupListener() {
//							public void lookupPerformed(LookupEvent event) {
//								Object[] data = ((_JLookup)event.getSource()).getDataSet();
//								if(data != null){
//									
//									String name = (String) data[1];						
//									txtPayee.setText(name);
//									payee_id =  data[0].toString();
//									
//									KEYBOARD_MANAGER.focusNextComponent();
//									btnCancel.setEnabled(true);
//								}
//							}
//						});
//					}
//					{
//						lblExclPayee = new JLabel("Excl. Payee", JLabel.TRAILING);
//						pnlNorthWest.add(lblExclPayee);
//						lblExclPayee.setEnabled(false);	
//					}
//					{
//						lookupExclPayee = new _JLookup(null, "Cashier");
//						pnlNorthWest.add(lookupExclPayee);
//						lookupExclPayee.setReturnColumn(0);
//						lookupExclPayee.setFilterName(true);
//						lookupExclPayee.setEnabled(false);
//						lookupExclPayee.addLookupListener(new LookupListener() {
//							public void lookupPerformed(LookupEvent event) {
//								Object[] data = ((_JLookup)event.getSource()).getDataSet();
//								if(data != null){
//									String name = (String) data[1];						
//									txtExclPayee.setText(name);	
//									excl_payee_id =  data[0].toString();
//												
//									KEYBOARD_MANAGER.focusNextComponent();
//									btnCancel.setEnabled(true);
//								}
//							}
//						});
//					}
//					{
//						lblBankAcct = new JLabel("Bank Account", JLabel.TRAILING);
//						pnlNorthWest.add(lblBankAcct);
//						lblBankAcct.setEnabled(false);	
//					}
//					{
//						lookupBankAcct = new _JLookup(null, "Cashier");
//						pnlNorthWest.add(lookupBankAcct);
//						lookupBankAcct.setReturnColumn(1);
//						lookupBankAcct.setEnabled(false);
//						lookupBankAcct.addLookupListener(new LookupListener() {
//							public void lookupPerformed(LookupEvent event) {
//								Object[] data = ((_JLookup)event.getSource()).getDataSet();
//								if(data != null){
//									
//									String bank_name = (String) data[3];	
//									String bank_acct_no = (String) data[2];	
//									txtBankAcct.setText(bank_name + "  |  " + bank_acct_no);	
//									acct_id = data[5].toString();
//												
//									KEYBOARD_MANAGER.focusNextComponent();
//								}
//							}
//						});
//					}
//				}
//				pnlNorthEast = new JPanel(new GridLayout(5, 1, 5, 5));
//				pnlNorth.add(pnlNorthEast, BorderLayout.CENTER);
//				pnlNorthEast.setPreferredSize(new java.awt.Dimension(300, 159));
//				{
//					txtCompany = new JTextField();
//					pnlNorthEast.add(txtCompany, BorderLayout.CENTER);
//					txtCompany.setEditable(false);
//				}
//				{
//					txtPmtType = new JTextField();
//					pnlNorthEast.add(txtPmtType, BorderLayout.CENTER);
//					txtPmtType.setEditable(false);
//					txtPmtType.setEnabled(false);
//				}
//				{
//					txtPayee = new JTextField();
//					pnlNorthEast.add(txtPayee, BorderLayout.CENTER);
//					txtPayee.setEditable(false);
//				}
//				{
//					txtExclPayee = new JTextField();
//					pnlNorthEast.add(txtExclPayee, BorderLayout.CENTER);
//					txtExclPayee.setEditable(false);
//					txtExclPayee.setEnabled(false);
//				}
//				{
//					txtBankAcct = new JTextField();
//					pnlNorthEast.add(txtBankAcct, BorderLayout.CENTER);
//					txtBankAcct.setEditable(false);
//					txtBankAcct.setEnabled(false);
//				}
//				
//				
//				pnlCenter2 = new JPanel(new GridLayout(2,2,5, 5));
//				pnlNorth.add(pnlCenter2, BorderLayout.SOUTH);
//				pnlCenter2.setPreferredSize(new java.awt.Dimension(547, 84));
//				pnlCenter2.setBorder(components.JTBorderFactory.createTitleBorder("Date Paid"));
//				
//					pnlCriteria2 = new JPanel(new GridLayout(1, 2, 3, 3));
//					pnlCenter2.add(pnlCriteria2, BorderLayout.NORTH);					
//					
//					{
//						lblDateFrom = new JLabel("Date From   ", JLabel.TRAILING);
//						pnlCriteria2.add(lblDateFrom, BorderLayout.CENTER);
//						lblDateFrom.setEnabled(true);							
//					}
//					{
//						dteDateFrom = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
//						pnlCriteria2.add(dteDateFrom, BorderLayout.CENTER);						
//						dteDateFrom.setDate(null);
//						dteDateFrom.setEnabled(true);
//						dteDateFrom.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
//					}	
//					{
//						lblDateTo = new JLabel("Date To   ", JLabel.TRAILING);
//						pnlCriteria2.add(lblDateTo, BorderLayout.CENTER);
//						lblDateTo.setEnabled(true);							
//					}
//					{
//						dteDateTo = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
//						pnlCriteria2.add(dteDateTo, BorderLayout.CENTER);						
//						dteDateTo.setDate(null);
//						dteDateTo.setEnabled(true);
//						dteDateTo.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
//					}	
//					
//					pnlCriteria3 = new JPanel(new GridLayout(1, 2, 3, 3));
//					pnlCenter2.add(pnlCriteria3, BorderLayout.CENTER);	
//					
//					{
//						chkInclAddTIN = new JCheckBox("Include Address & TIN");
//						pnlCriteria3.add(chkInclAddTIN);
//						chkInclAddTIN.setEnabled(false);	
//						chkInclAddTIN.setPreferredSize(new java.awt.Dimension(383, 26));
//						chkInclAddTIN.addItemListener(new ItemListener() {
//							public void itemStateChanged(ItemEvent arg0) {
//								
//							}
//						});
//					}
//			}	
//			
//
//			{
//				pnlCenter = new JPanel(new GridLayout(2,2,5, 5)); //1, 2
//				pnlMain.add(pnlCenter, BorderLayout.CENTER);
//				pnlCenter.setPreferredSize(new java.awt.Dimension(547, 83));
//				pnlCenter.setBorder(components.JTBorderFactory.createTitleBorder("Search Option"));
//				{		
//						pnlA = new JPanel(new GridLayout(1, 1, 3, 3));
//						pnlCenter.add(pnlA, BorderLayout.EAST);	
//						pnlA.setPreferredSize(new java.awt.Dimension(142, 100));
//						{
//							lblSortby_1 = new JLabel("Sort by (first)");
//							pnlA.add(lblSortby_1);
//							lblSortby_1.setEnabled(true);	
//						}
//						{
//							lblSortby_2 = new JLabel("Sort by (second)");
//							pnlA.add(lblSortby_2);
//							lblSortby_2.setEnabled(true);	
//						}
//						{
//							lblSummary = new JLabel("Summary");
//							pnlA.add(lblSummary);
//							lblSummary.setEnabled(true);	
//						}
//						
//						pnlB = new JPanel(new GridLayout(1,1, 3, 3));
//						pnlCenter.add(pnlB, BorderLayout.CENTER);								
//						pnlB.setPreferredSize(new java.awt.Dimension(300, 159));
//						
//						String status[] = {"Payee","Bank Acct.","Check No.","DV No.", "Date Paid"};					
//							cmbSortby = new JComboBox(status);
//							pnlB.add(cmbSortby);
//							cmbSortby.setSelectedItem(null);
//							cmbSortby.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,13));
//							cmbSortby.setBounds(537, 15, 190, 21);	
//							cmbSortby.setEnabled(true);
//							cmbSortby.setPreferredSize(new java.awt.Dimension(131, 80));
//							cmbSortby.setSelectedIndex(0);	
//							
//							String status3[] = {"Payee","Bank Acct.","Check No.","DV No.", "Date Paid"};					
//							cmbSortby2 = new JComboBox(status);
//							pnlB.add(cmbSortby2);
//							cmbSortby2.setSelectedItem(null);
//							cmbSortby2.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,13));
//							cmbSortby2.setBounds(537, 15, 190, 21);	
//							cmbSortby2.setEnabled(true);
//							cmbSortby2.setPreferredSize(new java.awt.Dimension(131, 80));
//							cmbSortby2.setSelectedIndex(0);						
//						
//						String status2[] = {"By Bank Account","By Date Paid"};					
//							cmbSummary = new JComboBox(status2);
//							pnlB.add(cmbSummary);
//							cmbSummary.setSelectedItem(null);
//							cmbSummary.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,13));
//							cmbSummary.setBounds(537, 15, 180, 21);	
//							cmbSummary.setEnabled(true);
//							cmbSummary.setSelectedIndex(0);
//							
//				}
//			}
//			
//			{				
//				pnlSouth = new JPanel(new GridLayout(1, 2,5, 5));
//				pnlMain.add(pnlSouth, BorderLayout.SOUTH);
//				pnlSouth.setPreferredSize(new Dimension(300, 30));
//				{
//					btnPreview = new JButton("Preview");
//					pnlSouth.add(btnPreview);
//					btnPreview.setAlignmentX(0.5f);
//					btnPreview.setAlignmentY(0.5f);
//					btnPreview.setEnabled(false);
//					btnPreview.setMaximumSize(new Dimension(100, 30));
//					btnPreview.setMnemonic(KeyEvent.VK_P);
//					btnPreview.addActionListener(this);
//				}
//				{
//					btnCancel = new JButton("Cancel");
//					pnlSouth.add(btnCancel);
//					btnCancel.setAlignmentX(0.5f);
//					btnCancel.setAlignmentY(0.5f);
//					btnCancel.setMaximumSize(new Dimension(100, 30));
//					btnCancel.setMnemonic(KeyEvent.VK_P);
//					btnCancel.setEnabled(false);
//					btnCancel.addActionListener(this);
//				}
//			}
//		}
//		this.setFocusTraversalPolicy(new FncFocusTraversalPolicy(lookupCompany, lookupPmtType,  
//				 dteDateFrom, btnPreview));
//		this.setBounds(0, 0, 562, 418);  //174
//		
//		//added 01/26/2016 - purpose - set CENQHOMES as default company
//		initialize_comp();
//	}
	
	public void initGUI() {
		this.setLayout(new BorderLayout(5,5));
		this.setSize(frameSize);
		this.setMinimumSize(frameSize);
		{
			pnlMain = new JPanel(new BorderLayout(5,5));
			this.add(pnlMain, BorderLayout.CENTER);
			pnlMain.setBorder(new EmptyBorder(5, 5, 5, 5));
			{
				pnlCenter = new JPanel(new GridBagLayout());
				pnlMain.add(pnlCenter, BorderLayout.CENTER);
				{
					GridBagConstraints c = new GridBagConstraints();
					c.fill = GridBagConstraints.BOTH;
					{
						c.weightx = 1;
						c.weighty = 1.5;

						c.gridx = 0; 
						c.gridy = 0; 
						
						JPanel pnlCompany = new JPanel(new GridBagLayout());
						pnlCenter.add(pnlCompany, c);
						pnlCompany.setBorder(components.JTBorderFactory.createTitleBorder("Company" ));// XXX
						{
							GridBagConstraints cons_com = new GridBagConstraints();
							cons_com.fill = GridBagConstraints.BOTH;
							cons_com.insets = new Insets(5, 5, 5, 5);
							
							/*LINE 1 company*/
							{
								cons_com.weightx = 0;
								cons_com.weighty = 1;

								cons_com.gridx = 0; 
								cons_com.gridy = 0; 
								
								lblCompany = new JLabel("Company", JLabel.CENTER);
								pnlCompany.add(lblCompany, cons_com);
								lblCompany.setFont(FncGlobal.sizeLabel);
							}
							{
								cons_com.weightx = 0.25;
								cons_com.weighty = 1;

								cons_com.gridx = 1; 
								cons_com.gridy = 0; 
								
								lookupCompany = new _JLookup(null, "Company");
								pnlCompany.add(lookupCompany, cons_com);
								lookupCompany.setReturnColumn(0);
								lookupCompany.setLookupSQL(SQL_COMPANY());
								lookupCompany.setFont(FncGlobal.sizeTextValue);
								lookupCompany.setPreferredSize(new Dimension(110,0));
								lookupCompany.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup)event.getSource()).getDataSet();
										if(data != null){
											
											company = (String) data[1];	
											company_logo = (String) data[3];
											
											txtCompany.setText(company);
																
											KEYBOARD_MANAGER.focusNextComponent();
											btnPreview.setEnabled(true);
											btnCancel.setEnabled(true);									
											
											lblBankAcct.setEnabled(false);
											lookupBankAcct.setEnabled(false);
											txtBankAcct.setEnabled(false);
										}
									}
								});														
							}
							{
								cons_com.weightx = 1.5;
								cons_com.weighty = 1;

								cons_com.gridx = 2; 
								cons_com.gridy = 0; 
								
								txtCompany = new JTextField();
								pnlCompany.add(txtCompany, cons_com);
								txtCompany.setEditable(false);
								txtCompany.setFont(FncGlobal.sizeTextValue);						
							}
							
							/*LINE 2 payment type*/
							{
								cons_com.weightx = 0;
								cons_com.weighty = 1;

								cons_com.gridx = 0; 
								cons_com.gridy = 1; 
								
								lblPmtType = new JLabel("Payment Type", JLabel.CENTER);
								pnlCompany.add(lblPmtType, cons_com);
								lblPmtType.setEnabled(false);
								lblPmtType.setFont(FncGlobal.sizeLabel);
							}
							{
								cons_com.weightx = 0.25;
								cons_com.weighty = 1;
	
								cons_com.gridx = 1; 
								cons_com.gridy = 1; 
								
								lookupPmtType = new _JLookup(null, "Project");
								pnlCompany.add(lookupPmtType, cons_com);
								lookupPmtType.setReturnColumn(0);
								lookupPmtType.setFilterName(true);
								lookupPmtType.setEnabled(false);
								lookupPmtType.setFont(FncGlobal.sizeTextValue);
								lookupPmtType.setPreferredSize(new Dimension(110,0));
								lookupPmtType.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup)event.getSource()).getDataSet();
										if(data != null){
											
											pymnt_type = data[0].toString();
											String pmt_type = (String) data[1];						
											txtPmtType.setText(pmt_type);	
											
											if(pymnt_type.equals("A"))
											{
												lblBankAcct.setEnabled(false);
												lookupBankAcct.setEnabled(false);
												txtBankAcct.setEnabled(false);
												lookupBankAcct.setText("");
												txtBankAcct.setText("");
											}
											else if(pymnt_type.equals("B"))
											{
												lblBankAcct.setEnabled(true);
												lookupBankAcct.setEnabled(true);
												txtBankAcct.setEnabled(true);										
											}
														
											KEYBOARD_MANAGER.focusNextComponent();
										}
									}
								});	
							}
							{
								cons_com.weightx = 1.5;
								cons_com.weighty = 1;

								cons_com.gridx = 2; 
								cons_com.gridy = 1; 
								
								txtPmtType = new JTextField();
								pnlCompany.add(txtPmtType, cons_com);
								txtPmtType.setEditable(false);
								txtPmtType.setEnabled(false);		
								txtPmtType.setFont(FncGlobal.sizeTextValue);
							}
							
							/*LINE 3 payee*/
							{
								cons_com.weightx = 0;
								cons_com.weighty = 1;

								cons_com.gridx = 0; 
								cons_com.gridy = 2;
								
								lblPayee = new JLabel("Payee", JLabel.CENTER);
								pnlCompany.add(lblPayee, cons_com);
								lblPayee.setEnabled(false);	
								lblPayee.setFont(FncGlobal.sizeLabel);
							}
							{
								cons_com.weightx = 0.25;
								cons_com.weighty = 1;

								cons_com.gridx = 1; 
								cons_com.gridy = 2; 
								
								lookupPayee = new _JLookup(null, "Branch");
								pnlCompany.add(lookupPayee, cons_com);
								lookupPayee.setReturnColumn(0);
								lookupPayee.setFilterName(true);
								lookupPayee.setEnabled(false);
								lookupPayee.setFont(FncGlobal.sizeTextValue);
								lookupPayee.setPreferredSize(new Dimension(110,0));
								//lookupPayee.setLookupSQL(SQL_COMPANY());
								lookupPayee.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup)event.getSource()).getDataSet();
										if(data != null){
											
											String name = (String) data[1];						
											txtPayee.setText(name);
											payee_id =  data[0].toString();
											
											KEYBOARD_MANAGER.focusNextComponent();
											btnCancel.setEnabled(true);
										}
									}
								});							
							}
							{
								cons_com.weightx = 0;
								cons_com.weighty = 1;

								cons_com.gridx = 2; 
								cons_com.gridy = 2;
								
								txtPayee = new JTextField();
								pnlCompany.add(txtPayee, cons_com);
								txtPayee.setEditable(false);
								txtPayee.setFont(FncGlobal.sizeTextValue);						
							}
							
							/*LINE 4 excl payee*/
							{
								cons_com.weightx = 0;
								cons_com.weighty = 1;

								cons_com.gridx = 0; 
								cons_com.gridy = 3;
								
								lblExclPayee = new JLabel("Excl. Payee", JLabel.CENTER);
								pnlCompany.add(lblExclPayee, cons_com);
								lblExclPayee.setEnabled(false);
								lblExclPayee.setFont(FncGlobal.sizeLabel);
							}
							{
								cons_com.weightx = 0.25;
								cons_com.weighty = 1;

								cons_com.gridx = 1; 
								cons_com.gridy = 3;
								
								lookupExclPayee = new _JLookup(null, "Cashier");
								pnlCompany.add(lookupExclPayee, cons_com);
								lookupExclPayee.setReturnColumn(0);
								lookupExclPayee.setFilterName(true);
								lookupExclPayee.setEnabled(false);
								lookupExclPayee.setPreferredSize(new Dimension(110,0));
								lookupExclPayee.setFont(FncGlobal.sizeTextValue);
								lookupExclPayee.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup)event.getSource()).getDataSet();
										if(data != null){
											String name = (String) data[1];						
											txtExclPayee.setText(name);	
											excl_payee_id =  data[0].toString();
														
											KEYBOARD_MANAGER.focusNextComponent();
											btnCancel.setEnabled(true);
										}
									}
								});					
							}
							{
								cons_com.weightx = 0.25;
								cons_com.weighty = 1;

								cons_com.gridx = 2; 
								cons_com.gridy = 3;
								
								txtExclPayee = new JTextField();
								pnlCompany.add(txtExclPayee, cons_com);
								txtExclPayee.setEditable(false);
								txtExclPayee.setEnabled(false);
								txtExclPayee.setFont(FncGlobal.sizeTextValue);				
							}
							
							/*LINE 5 bank account*/
							{
								cons_com.weightx = 0;
								cons_com.weighty = 1;

								cons_com.gridx = 0; 
								cons_com.gridy = 4;
								
								lblBankAcct = new JLabel("Bank Account", JLabel.CENTER);
								pnlCompany.add(lblBankAcct, cons_com);
								lblBankAcct.setEnabled(false);	
								lblBankAcct.setFont(FncGlobal.sizeLabel);
							}
							{
								cons_com.weightx = 0.25;
								cons_com.weighty = 1;

								cons_com.gridx = 1; 
								cons_com.gridy = 4;
								
								lookupBankAcct = new _JLookup(null, "Cashier");
								pnlCompany.add(lookupBankAcct, cons_com);
								lookupBankAcct.setReturnColumn(1);
								lookupBankAcct.setPreferredSize(new Dimension(110,0));
								lookupBankAcct.setFont(FncGlobal.sizeTextValue);
								lookupBankAcct.setEnabled(false);
								lookupBankAcct.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup)event.getSource()).getDataSet();
										if(data != null){
											
											String bank_name = (String) data[3];	
											String bank_acct_no = (String) data[2];	
											txtBankAcct.setText(bank_name + "  |  " + bank_acct_no);	
											acct_id = data[5].toString();
														
											KEYBOARD_MANAGER.focusNextComponent();
										}
									}
								});						
							}
							{
								cons_com.weightx = 0.25;
								cons_com.weighty = 1;

								cons_com.gridx = 2; 
								cons_com.gridy = 4;
								
								txtBankAcct = new JTextField();
								pnlCompany.add(txtBankAcct, cons_com);
								txtBankAcct.setEditable(false);
								txtBankAcct.setEnabled(false);
								txtBankAcct.setFont(FncGlobal.sizeTextValue);						
							}
						}
					}
					{
						c.weightx = 1;
						c.weighty = 0.5;

						c.gridx = 0; 
						c.gridy = 1;
						
						JPanel pnlDtePaid = new JPanel(new GridBagLayout());
						pnlCenter.add(pnlDtePaid, c);
						pnlDtePaid.setBorder(components.JTBorderFactory.createTitleBorder("Date Paid"));
						{
							GridBagConstraints cons_dtepaid = new GridBagConstraints();
							cons_dtepaid.fill = GridBagConstraints.BOTH;
							cons_dtepaid.insets = new Insets(5, 5, 5, 5);
							
							/*LINE 1 date*/
							{
								cons_dtepaid.weightx = 0.5;
								cons_dtepaid.weighty = 1;

								cons_dtepaid.gridx = 0; 
								cons_dtepaid.gridy = 0;
								
								lblDateFrom = new JLabel("Date From", JLabel.CENTER);
								pnlDtePaid.add(lblDateFrom, cons_dtepaid);
								lblDateFrom.setEnabled(true);
								lblDateFrom.setFont(FncGlobal.sizeLabel);
							}
							{
								cons_dtepaid.weightx = 1;
								cons_dtepaid.weighty = 1;

								cons_dtepaid.gridx = 1; 
								cons_dtepaid.gridy = 0;
								
								dteDateFrom = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
								pnlDtePaid.add(dteDateFrom, cons_dtepaid);						
								dteDateFrom.setDate(null);
								dteDateFrom.setEnabled(true);
								dteDateFrom.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
								dteDateFrom.setFont(FncGlobal.sizeTextValue);	
							}
							{
								cons_dtepaid.weightx = 0.5;
								cons_dtepaid.weighty = 1;

								cons_dtepaid.gridx = 2; 
								cons_dtepaid.gridy = 0;
								
								lblDateTo = new JLabel("Date To", JLabel.CENTER);
								pnlDtePaid.add(lblDateTo, cons_dtepaid);
								lblDateTo.setEnabled(true);	
								lblDateTo.setFont(FncGlobal.sizeLabel);
							}
							{
								cons_dtepaid.weightx = 1;
								cons_dtepaid.weighty = 1;

								cons_dtepaid.gridx = 3; 
								cons_dtepaid.gridy = 0;
								
								dteDateTo = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
								pnlDtePaid.add(dteDateTo, cons_dtepaid);						
								dteDateTo.setDate(null);
								dteDateTo.setEnabled(true);
								dteDateTo.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
								dteDateTo.setFont(FncGlobal.sizeTextValue);			
							}
							
							/*LINE 2 chkbox*/
							{
								cons_dtepaid.weightx = 0.25;
								cons_dtepaid.weighty = 1;

								cons_dtepaid.gridx = 0; 
								cons_dtepaid.gridy = 1;
								
								cons_dtepaid.gridwidth = 2;
						
								chkInclAddTIN = new JCheckBox("Include Address & TIN");
								pnlDtePaid.add(chkInclAddTIN, cons_dtepaid);
								chkInclAddTIN.setEnabled(false);
								chkInclAddTIN.setFont(FncGlobal.sizeTextValue);
								chkInclAddTIN.addItemListener(new ItemListener() {
									public void itemStateChanged(ItemEvent arg0) {
										
									}
								});				
							}
						}
					}
					{
						c.weightx = 1;
						c.weighty = 0.5;

						c.gridx = 0; 
						c.gridy = 2;
						
						JPanel pnlSearch = new JPanel(new GridBagLayout());
						pnlCenter.add(pnlSearch, c);
						pnlSearch.setBorder(components.JTBorderFactory.createTitleBorder("Search Options"));
						{
							GridBagConstraints cons_searchopt = new GridBagConstraints();
							cons_searchopt.fill = GridBagConstraints.BOTH;
							cons_searchopt.insets = new Insets(5, 5, 5, 5);
							
							/*LINE 1 labels*/
							{
								cons_searchopt.weightx = 0.25;
								cons_searchopt.weighty = 1;

								cons_searchopt.gridx = 0; 
								cons_searchopt.gridy = 0;
								
								lblSortby_1 = new JLabel("Sort by (first)", JLabel.CENTER);
								pnlSearch.add(lblSortby_1, cons_searchopt);
								lblSortby_1.setEnabled(true);	
								lblSortby_1.setFont(FncGlobal.sizeLabel);
							}
							{
								cons_searchopt.weightx = 0.25;
								cons_searchopt.weighty = 1;

								cons_searchopt.gridx = 1; 
								cons_searchopt.gridy = 0;
								
								lblSortby_2 = new JLabel("Sort by (second)", JLabel.CENTER);
								pnlSearch.add(lblSortby_2, cons_searchopt);
								lblSortby_2.setEnabled(true);
								lblSortby_2.setFont(FncGlobal.sizeLabel);
							}
							{
								cons_searchopt.weightx = 0.25;
								cons_searchopt.weighty = 1;

								cons_searchopt.gridx = 2; 
								cons_searchopt.gridy = 0;
								
								lblSummary = new JLabel("Summary", JLabel.CENTER);
								pnlSearch.add(lblSummary, cons_searchopt);
								lblSummary.setEnabled(true);
								lblSummary.setFont(FncGlobal.sizeLabel);
							}
							
							/*LINE 2 cmbbox*/
							{
								cons_searchopt.weightx = 0.25;
								cons_searchopt.weighty = 1;

								cons_searchopt.gridx = 0; 
								cons_searchopt.gridy = 1;
								
								String status[] = {"Payee","Bank Acct.","Check No.","DV No.", "Date Paid"};					
								cmbSortby = new JComboBox(status);
								pnlSearch.add(cmbSortby, cons_searchopt);
								cmbSortby.setSelectedItem(null);
								cmbSortby.setEnabled(true);
								cmbSortby.setSelectedIndex(0);
								cmbSortby.setFont(FncGlobal.sizeTextValue);
							}
							{
								cons_searchopt.weightx = 0.25;
								cons_searchopt.weighty = 1;

								cons_searchopt.gridx = 1; 
								cons_searchopt.gridy = 1;
								
								String status3[] = {"Payee","Bank Acct.","Check No.","DV No.", "Date Paid"};					
								cmbSortby2 = new JComboBox(status3);
								pnlSearch.add(cmbSortby2, cons_searchopt);
								cmbSortby2.setSelectedItem(null);
								cmbSortby2.setEnabled(true);
								cmbSortby2.setSelectedIndex(0);
								cmbSortby2.setFont(FncGlobal.sizeTextValue);
							}
							{
								cons_searchopt.weightx = 0.25;
								cons_searchopt.weighty = 1;

								cons_searchopt.gridx = 2; 
								cons_searchopt.gridy = 1;
								
								String status2[] = {"By Bank Account","By Date Paid"};					
								cmbSummary = new JComboBox(status2);
								pnlSearch.add(cmbSummary, cons_searchopt);
								cmbSummary.setSelectedItem(null);
								cmbSummary.setEnabled(true);
								cmbSummary.setSelectedIndex(0);
								cmbSummary.setFont(FncGlobal.sizeTextValue);
							}
						}
					}
				}
			}
			{
				
				pnlSouth = new JPanel(new GridLayout(1, 2,5, 5));
				pnlMain.add(pnlSouth, BorderLayout.SOUTH);
				pnlSouth.setPreferredSize(new Dimension(0, 30));
				{
					btnPreview = new JButton("Preview");
					pnlSouth.add(btnPreview);
					btnPreview.setEnabled(false);
					btnPreview.setMnemonic(KeyEvent.VK_P);
					btnPreview.addActionListener(this);
					btnPreview.setFont(FncGlobal.sizeControls);
				}
				{
					btnCancel = new JButton("Cancel");
					pnlSouth.add(btnCancel);
					btnCancel.setMnemonic(KeyEvent.VK_P);
					btnCancel.setEnabled(false);
					btnCancel.addActionListener(this);
					btnCancel.setFont(FncGlobal.sizeControls);
				}			
			}
		}
		
		this.setFocusTraversalPolicy(new FncFocusTraversalPolicy(lookupCompany, lookupPmtType,  
				 dteDateFrom, btnPreview));
		//this.setBounds(0, 0, 562, 418);  //174
		
		//added 01/26/2016 - purpose - set CENQHOMES as default company
		initialize_comp();
	}
	
	private List<JRSortField> sortBy(String sort_by, String sort_by_2) {
		
		Map<String, String[]> map = new HashMap<String, String[]>();
		map.put("Payee", new String[]{"payee"});
		map.put("Bank Acct.", new String[]{"acct_id"});
		map.put("Check No.", new String[]{"check_no"});
		map.put("DV No.", new String[]{"cv_no"});
		map.put("Date Paid", new String[]{"date_paid"});
		
		List<JRSortField> sortList = new ArrayList<JRSortField>();
		for(String fields : map.get(sort_by)){
			
			JRDesignSortField sortField = new JRDesignSortField();
			sortField.setName(fields);
			sortField.setOrder(SortOrderEnum.ASCENDING);
			sortField.setType(SortFieldTypeEnum.FIELD);			
			sortList.add(sortField);			
		}
				
		for(String fields : map.get(sort_by_2)){			
			JRDesignSortField sortField2 = new JRDesignSortField();
			sortField2.setName(fields);
			sortField2.setOrder(SortOrderEnum.ASCENDING);
			sortField2.setType(SortFieldTypeEnum.FIELD);			
			sortList.add(sortField2);			
		}
		
		return sortList;
	}

	@Override
	public void ancestorAdded(AncestorEvent event) {
		lookupPmtType.requestFocus();
	}

	@Override
	public void ancestorMoved(AncestorEvent event) { }

	@Override
	public void ancestorRemoved(AncestorEvent event) { }
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getActionCommand().equals("Preview")){		
			String detailed = "Paid Out Voucher";	
			String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), detailed.toUpperCase());
			String sort_by = (String) cmbSortby.getSelectedItem();
			String sort_by_2 = (String) cmbSortby2.getSelectedItem();
		
			Map<String, Object> mapParameters = new HashMap<String, Object>();
			mapParameters.put("company", company);
			mapParameters.put("co_id", lookupCompany.getValue());
			mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
			mapParameters.put("prepared_by", UserInfo.Alias);
			mapParameters.put("acct_id", acct_id);
			mapParameters.put("payee_id", payee_id);
			mapParameters.put("pymnt_type", pymnt_type);
			mapParameters.put("excl_payee_id", excl_payee_id);
			mapParameters.put("date_from", dteDateFrom.getDate());
			mapParameters.put("date_to",  dteDateTo.getDate());
			mapParameters.put(JRParameter.SORT_FIELDS, sortBy(sort_by, sort_by_2));	

			if (cmbSummary.getSelectedIndex()==0){
				FncReport.generateReport("/Reports/rptPaidOut_preview.jasper", reportTitle, txtPmtType.getText(), mapParameters);
			}else {
				FncReport.generateReport("/Reports/rptPaidOut_preview_byDatePaid.jasper", reportTitle, txtPmtType.getText(), mapParameters);
			}
			
		}
		
		if(e.getActionCommand().equals("Cancel")){	
			
			lookupPmtType.setValue("");
			txtPmtType.setText("");
			
			lookupPayee.setValue("");
			txtPayee.setText("");
			
			lookupExclPayee.setValue("");
			txtExclPayee.setText("");
			
			lookupBankAcct.setValue("");
			txtBankAcct.setText("");
			
			lookupBankAcct.setEnabled(false);
			txtBankAcct.setEnabled(false);
			lblBankAcct.setEnabled(false);	
			
			dteDateFrom.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
			dteDateTo.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
			
			chkInclAddTIN.setSelected(false);
			
			cmbSortby.setSelectedIndex(0);	
			cmbSummary.setSelectedIndex(0);
			
			acct_id = "";
			payee_id = "";
			excl_payee_id = "";
			co_id = "";
			
		}
		
	}
	
	public void enableFields(Boolean x){
		
		lblPmtType.setEnabled(x);
		lblPayee.setEnabled(x);
		lblExclPayee.setEnabled(x);
		
		lookupPmtType.setEnabled(x);
		lookupPayee.setEnabled(x);
		lookupExclPayee.setEnabled(x);
		
		txtPmtType.setEnabled(x);
		txtPayee.setEnabled(x);
		txtExclPayee.setEnabled(x);		
		
		chkInclAddTIN.setEnabled(x);			
	}

	public void initialize_comp(){
		
		co_id 		= "02";	
		company		= "CENQHOMES DEVELOPMENT CORPORATION";	
		company_logo = RequestForPayment.sql_getCompanyLogo();
		txtCompany.setText(company);
		
		lookupPmtType.setLookupSQL(CheckVoucher.getPayment_type());		
		lookupPayee.setLookupSQL(getEntityList());//--edited by jed 2018-12-10--//
		lookupExclPayee.setLookupSQL(getEntityList());//--edited by jed 2018-12-10--//
		lookupBankAcct.setLookupSQL(getBankAcct());
							
		KEYBOARD_MANAGER.focusNextComponent();
		btnPreview.setEnabled(true);
		btnCancel.setEnabled(true);
		enableFields(true);
		
		lookupCompany.setValue(co_id);
}
	
	
	//SQL
	public static String getBankAcct(){//used

		String sql = 		

			"select   \r\n" + 
			"\r\n" + 
			"		a.rec_id,\r\n" +    //0
			"		a.bank_acct_id  ,  \r\n" + //1
			"		b.bank_acct_no ,  \r\n" +  //2
			"		d.bank_alias  ,  \r\n" +   //3
			"		( case when b.fund_class_id = '01' then 'COLLECTING ACCOUNT' else   \r\n" +   
			"			case when b.fund_class_id = '02' then 'DISBURSING ACCOUNT' else  \r\n" + 
			"				case when b.fund_class_id = '03' then 'MONEY MARKET PLACEMENT ACCOUNT 'end end end) , \r\n" + //4
			"		b.acct_id,\r\n" + 		//5
			"		a.from_check_no,\r\n" + //6
			"		a.to_check_no, \n" +	//7
			"		a.last_no_used \r\n" + 	//8
			"\r\n" + 
			"		from ( select * from rf_check_book where rec_id in ( select rec_id from rf_check_book where trim(user_id) = '"+UserInfo.EmployeeCode+"' and status_id = 'A' ) ) a\r\n" + 
			"		left join mf_bank_account b on a.bank_acct_id = b.bank_acct_id  \r\n" + 
			"		left join mf_bank_branch c on b.bank_branch_id = c.bank_branch_id  \r\n" + 
			"		left join mf_bank d on c.bank_id = d.bank_id   \r\n" +
			"		order by a.bank_acct_id \r\n" ;	

		System.out.printf("getBankAcct() sql :" + sql);
		return sql;

	}
	//--added by JED 2018-12-10 no dcrf initialize lookup for Payee--//
	public static String getEntityList(){

		String sql = 
			"select\n" + 
			"trim(entity_id) as \"Entity ID\",\n" + 
			"trim(entity_name) as \"Name\",\n" + 
			"entity_kind as \"Kind\",\n" + 
			"vat_registered as \"VAT\"\n" + 
			"from (\n" + 
			"(select entity_id, entity_name, entity_kind, vat_registered from rf_entity where status_id = 'A' limit 3000) union\n" + 
			"(select entity_id, entity_name, entity_kind, vat_registered from rf_entity where entity_id in (select entity_id  from em_employee)) union\n" + 
			"(select entity_id, entity_name, entity_kind, vat_registered from rf_entity)) a\n" + 
			"order by a.entity_name	";		
		return sql;

	}
	
	
}
