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
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import Accounting.Disbursements.RequestForPayment;
import DateChooser._JDateChooser;
import Functions.FncAcounting;
import Functions.FncFocusTraversalPolicy;
import Functions.FncGlobal;
import Functions.FncReport;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;

import components._JInternalFrame;

public class BatchPrinting extends _JInternalFrame implements ActionListener, AncestorListener, _GUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1162406788303915011L;

	static String title = "Batch Printing";
	static Dimension frameSize = new Dimension(600, 500);
	static Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	static Cursor handCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);

	private JPanel pnlMain;
	private JPanel pnlNorth;
	private JPanel pnlNorthWest;
	private JPanel pnlNorthEast;
	private JPanel pnlCenter2;
	private JPanel pnlCriteria2;
	private JPanel pnlCenter;
	private JPanel pnlDoc;
	private JPanel pnlCenter_b;
	private JPanel pnlSearchBy;
	private JPanel pnlSouth;
	private JPanel pnlDocument;
	private JPanel pnlDocument_a;
	private JPanel pnlDocument_b;
	private JPanel pnlDocument_sub;
	private JPanel pnlDocument2;

	private JLabel lblCompany;
	private JLabel lblDateFrom;
	private JLabel lblDateTo;
	private JLabel lblDocNo_fr;
	private JLabel lblDocNo_to;
	private JLabel lblSearchBy;
	private JLabel lblDocument;
	
	private _JLookup lookupCompany;
	private _JLookup lookupPV_no_from;
	private _JLookup lookupPV_no_to;
	private _JLookup lookupTransType;
	private _JLookup lookupCreatedBy;
	private _JLookup lookupPayee;
	private JTextField txtPayee;

	private JTextField txtCompany;
	private JTextField txtTransType;
	private JTextField txtCreatedBy;

	private JRadioButton rbtnDocNumber;
	private JRadioButton rbtnDocDate;	

	private JButton btnPreview;
	private JButton btnCancel;

	private _JDateChooser dteDateFrom;
	private _JDateChooser dteDateTo;

	String company;
	String company_logo;	
	String co_id = "02";
	String proj_id = "All";	
	String pv_no_from = "";	
	String pv_no_to = "";	
	String status_id = "A"; 

	SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

	private JComboBox cmbDocument;

	private JLabel lblStatus;

	private JComboBox cmbStatus;

	public BatchPrinting() {
		super(title, true, true, true, true);
		initGUI();
	}

	public BatchPrinting(String title) {
		super(title);
		initGUI();
	}

	public BatchPrinting(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	@Override
//	public void initGUI() {
//		this.setTitle(title);
//		this.setSize(frameSize);
//		this.setPreferredSize(new java.awt.Dimension(560, 500));
//		this.setLayout(new BorderLayout());
//		this.addAncestorListener(this);
//		{
//			pnlMain = new JPanel(new BorderLayout(5, 5));
//			getContentPane().add(pnlMain, BorderLayout.WEST);
//			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
//			pnlMain.setPreferredSize(new java.awt.Dimension(557, 150));
//			{
//				pnlNorth = new JPanel(new BorderLayout(5, 5));
//				pnlMain.add(pnlNorth, BorderLayout.NORTH);
//				pnlNorth.setPreferredSize(new java.awt.Dimension(547, 65));
//				pnlNorth.setBorder(components.JTBorderFactory.createTitleBorder("Comp. / Project Option" ));// XXX
//
//				{
//					pnlNorthWest = new JPanel(new GridLayout(1,1, 5, 5));
//					pnlNorth.add(pnlNorthWest, BorderLayout.WEST);
//					pnlNorthWest.setPreferredSize(new java.awt.Dimension(142, 100));
//
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
//									co_id = (String) data[0];	
//									company		= (String) data[1];	
//									company_logo = (String) data[3];
//
//									String name = (String) data[1];						
//									txtCompany.setText(name);
//
//									enable_fields(true);
//									lblDateFrom.setEnabled(false);		
//									dteDateFrom.setEnabled(false);
//									lblDateTo.setEnabled(false);	
//									dteDateTo.setEnabled(false);
//
//									KEYBOARD_MANAGER.focusNextComponent();
//									btnCancel.setEnabled(true);
//									btnPreview.setEnabled(true);
//								}
//							}
//						});
//					}
//				}
//
//				pnlNorthEast = new JPanel(new GridLayout(1, 1, 5, 5));
//				pnlNorth.add(pnlNorthEast, BorderLayout.CENTER);
//				pnlNorthEast.setPreferredSize(new java.awt.Dimension(300, 159));
//				{
//					txtCompany = new JTextField();
//					pnlNorthEast.add(txtCompany, BorderLayout.CENTER);
//					txtCompany.setEditable(false);
//				}
//			}
//
//			pnlCenter2 = new JPanel(new BorderLayout(5, 5));
//			pnlMain.add(pnlCenter2, BorderLayout.CENTER);
//			pnlCenter2.setPreferredSize(new java.awt.Dimension(499, 150));
//			pnlCenter2.setBorder(components.JTBorderFactory.createTitleBorder("Document Option"));
//
//			{
//				pnlDocument = new JPanel(new BorderLayout(5,5));
//				pnlCenter2.add(pnlDocument, BorderLayout.NORTH);
//				pnlDocument.setPreferredSize(new java.awt.Dimension(547, 150));
//
//				{
//					pnlDocument_sub = new JPanel(new BorderLayout(5,5));
//					pnlDocument.add(pnlDocument_sub, BorderLayout.NORTH);
//					pnlDocument_sub.setPreferredSize(new java.awt.Dimension(547, 120));
//
//					{
//						pnlDocument_a = new JPanel(new GridLayout(4, 1, 0, 5));
//						pnlDocument_sub.add(pnlDocument_a, BorderLayout.CENTER);
//						pnlDocument_a.setPreferredSize(new java.awt.Dimension(70, 44));
//
//						{
//							//lblDocument = new JLabel("Doc. Name ", JLabel.TRAILING);
//							lblDocument = new JLabel("     Doc. Name ");
//							pnlDocument_a.add(lblDocument, BorderLayout.CENTER);
//							lblDocument.setPreferredSize(new java.awt.Dimension(84, 65));
//							lblDocument.setEnabled(true);	
//							lblDocument.setFont(new java.awt.Font("Segoe UI",Font.ITALIC,12));
//							lblDocument.setFont(new java.awt.Font("Segoe UI",Font.BOLD,12));
//						}
//						{
//							//lblDocument = new JLabel("Doc. Name ", JLabel.TRAILING);
//							lblStatus = new JLabel("     Status ");
//							pnlDocument_a.add(lblStatus, BorderLayout.CENTER);
//							lblStatus.setPreferredSize(new java.awt.Dimension(84, 65));
//							lblStatus.setEnabled(true);	
//							lblStatus.setFont(new java.awt.Font("Segoe UI",Font.ITALIC,12));
//							lblStatus.setFont(new java.awt.Font("Segoe UI",Font.BOLD,12));
//						}
//						{
//							//lblDocument = new JLabel("Doc. Name ", JLabel.TRAILING);
//							JLabel lblCreatedBy = new JLabel("     Created By ");
//							pnlDocument_a.add(lblCreatedBy, BorderLayout.CENTER);
//							lblCreatedBy.setPreferredSize(new java.awt.Dimension(84, 65));
//							lblCreatedBy.setEnabled(true);	
//							lblCreatedBy.setFont(new java.awt.Font("Segoe UI",Font.ITALIC,12));
//							lblCreatedBy.setFont(new java.awt.Font("Segoe UI",Font.BOLD,12));
//						}
//						{
//							//lblDocument = new JLabel("Doc. Name ", JLabel.TRAILING);
//							JLabel lblType = new JLabel("     Trans. Type ");
//							pnlDocument_a.add(lblType, BorderLayout.CENTER);
//							lblType.setPreferredSize(new java.awt.Dimension(84, 65));
//							lblType.setEnabled(true);	
//							lblType.setFont(new java.awt.Font("Segoe UI",Font.ITALIC,12));
//							lblType.setFont(new java.awt.Font("Segoe UI",Font.BOLD,12));
//						}
//					}
//					{
//						pnlDocument_b = new JPanel(new GridLayout(4, 1, 0, 5));
//						pnlDocument_sub.add(pnlDocument_b, BorderLayout.EAST);
//						pnlDocument_b.setPreferredSize(new java.awt.Dimension(404, 120));
//
//						{
//							String docu[] = {"PAYABLE VOUCHER (PV)","JOURNAL VOUCHER (JV)"};					
//							cmbDocument = new JComboBox(docu);
//							pnlDocument_b.add(cmbDocument);
//							cmbDocument.setSelectedItem(null);
//							cmbDocument.setBounds(537, 15, 160, 21);	
//							cmbDocument.setEnabled(true);
//							cmbDocument.setSelectedIndex(0);	
//							cmbDocument.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
//							((JLabel)cmbDocument.getRenderer()).setHorizontalAlignment(JLabel.LEFT);
//							cmbDocument.setPreferredSize(new java.awt.Dimension(89, 22));	
//							cmbDocument.addItemListener(new ItemListener() {
//								public void itemStateChanged(ItemEvent evt) 
//								{
//									if (cmbDocument.getSelectedIndex()==0)
//									{
//										lookupPV_no_from.setLookupSQL(getPV_no());	
//										lookupPV_no_to.setLookupSQL(getPV_no());
//										lookupCreatedBy.setLookupSQL(SQL_EmpPV());
//										lookupTransType.setLookupSQL(SQL_TypePV());
//									}
//									else if (cmbDocument.getSelectedIndex()==1)
//									{
//										lookupPV_no_from.setLookupSQL(getJV_no());	
//										lookupPV_no_to.setLookupSQL(getJV_no());
//										lookupCreatedBy.setLookupSQL(SQL_EmpJV());
//										lookupTransType.setLookupSQL(SQL_TypeJV());
//									}
//
//								}
//							});		
//						}
//						{
//							String status[] = {"ACTIVE","TAGGED","POSTED","INACTIVE","DELETED","ALL"};					
//							cmbStatus = new JComboBox(status);
//							pnlDocument_b.add(cmbStatus);
//							cmbStatus.setSelectedItem(null);
//							cmbStatus.setBounds(537, 15, 160, 21);	
//							cmbStatus.setEnabled(true);
//							cmbStatus.setSelectedIndex(0);	
//							cmbStatus.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
//							((JLabel)cmbStatus.getRenderer()).setHorizontalAlignment(JLabel.LEFT);
//							cmbStatus.setPreferredSize(new java.awt.Dimension(89, 22));	
//							cmbStatus.addItemListener(new ItemListener() {
//								public void itemStateChanged(ItemEvent evt) 
//								{
//									if (cmbStatus.getSelectedIndex()==0)
//									{
//										status_id = "A"; 
//									}
//									else if (cmbStatus.getSelectedIndex()==1)
//									{
//										status_id = "F"; 
//									}
//									else if (cmbStatus.getSelectedIndex()==2)
//									{
//										status_id = "P"; 
//									}
//									else if (cmbStatus.getSelectedIndex()==3)
//									{
//										status_id = "I"; 
//									}
//									else if (cmbStatus.getSelectedIndex()==4)
//									{
//										status_id = "D"; 
//									}
//									else if (cmbStatus.getSelectedIndex()==5)
//									{
//										status_id = ""; 
//									}
//
//								}
//							});		
//						}
//						{
//							JPanel pnlLookupEmp = new JPanel(new BorderLayout(3, 3));
//							pnlDocument_b.add(pnlLookupEmp);
//							{
//								lookupCreatedBy = new _JLookup(null, "Created By");
//								pnlLookupEmp.add(lookupCreatedBy, BorderLayout.WEST);
//								lookupCreatedBy.setPreferredSize(new Dimension(70, 20));
//								lookupCreatedBy.setReturnColumn(0);
//								lookupCreatedBy.setFilterName(true);
//								if (cmbDocument.getSelectedIndex() == 0) {
//									lookupCreatedBy.setLookupSQL(SQL_EmpPV());
//								} 
//								lookupCreatedBy.addActionListener(this);
//								lookupCreatedBy.addLookupListener(new LookupListener() {
//									public void lookupPerformed(LookupEvent event) {
//										Object[] data = ((_JLookup) event.getSource()).getDataSet();
//										if (data != null) {
//											txtCreatedBy.setText(String.format("%s", (String) data[1]));
//								
//											KEYBOARD_MANAGER.focusNextComponent();
//										}
//									}
//								});
//								{
//									txtCreatedBy = new JTextField();
//									pnlLookupEmp.add(txtCreatedBy,BorderLayout.CENTER);
//									txtCreatedBy.setHorizontalAlignment(JLabel.LEFT);
//								}
//							}
//						}
//						{
//							JPanel pnlLookup = new JPanel(new BorderLayout(3, 3));
//							pnlDocument_b.add(pnlLookup);
//							{
//								lookupTransType = new _JLookup(null, "Transaction Type");
//								pnlLookup.add(lookupTransType, BorderLayout.WEST);
//								lookupTransType.setPreferredSize(new Dimension(70, 20));
//								lookupTransType.setReturnColumn(0);
//								lookupTransType.setFilterName(true);
//								if (cmbDocument.getSelectedIndex() == 0) {
//									lookupTransType.setLookupSQL(SQL_TypePV());
//								} 
//								
//								lookupTransType.addActionListener(this);
//								lookupTransType.addLookupListener(new LookupListener() {
//									public void lookupPerformed(LookupEvent event) {
//										Object[] data = ((_JLookup) event.getSource()).getDataSet();
//										if (data != null) {
//											txtTransType.setText(String.format("%s", (String) data[1]));
//								
//											KEYBOARD_MANAGER.focusNextComponent();
//										}
//									}
//								});
//								{
//									txtTransType = new JTextField();
//									pnlLookup.add(txtTransType,BorderLayout.CENTER);
//									txtTransType.setHorizontalAlignment(JLabel.LEFT);
//								}
//							}
//						}
//					}
//				}
//				{
//					pnlDocument2 = new JPanel(new BorderLayout(5,5));
//					pnlDocument.add(pnlDocument2, BorderLayout.CENTER);
//					pnlDocument2.setPreferredSize(new java.awt.Dimension(499, 29));
//
//					{
//						pnlSearchBy =  new JPanel(new GridLayout(1,3, 5, 5));
//						pnlDocument2.add(pnlSearchBy, BorderLayout.CENTER);
//						pnlSearchBy.setPreferredSize(new java.awt.Dimension(499, 46));	
//
//						{
//							lblSearchBy = new JLabel("     Search by :");
//							pnlSearchBy.add(lblSearchBy);
//							lblSearchBy.setEnabled(false);	
//							lblSearchBy.setPreferredSize(new java.awt.Dimension(86, 40));
//							lblSearchBy.setFont(new java.awt.Font("Segoe UI",Font.ITALIC,12));
//							lblSearchBy.setFont(new java.awt.Font("Segoe UI",Font.BOLD,12));
//						}	
//						{
//							rbtnDocNumber = new JRadioButton();
//							pnlSearchBy.add(rbtnDocNumber);
//							rbtnDocNumber.setText("by Doc. No.");
//							rbtnDocNumber.setBounds(277, 12, 77, 18);
//							rbtnDocNumber.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
//							rbtnDocNumber.setSelected(true);
//							rbtnDocNumber.setEnabled(false);
//							rbtnDocNumber.setPreferredSize(new java.awt.Dimension(220, 18));
//							rbtnDocNumber.addActionListener(new ActionListener() {
//								public void actionPerformed(ActionEvent ae){									
//									rbtnDocNumber.setSelected(true);	rbtnDocDate.setSelected(false);	
//
//									lblDocNo_fr.setEnabled(true);	
//									lookupPV_no_from.setEnabled(true);
//									lblDocNo_to.setEnabled(true);	
//									lookupPV_no_to.setEnabled(true);									
//
//									lblDateFrom.setEnabled(false);	
//									dteDateFrom.setEnabled(false);
//									lblDateTo.setEnabled(false);	
//									dteDateTo.setEnabled(false);
//									dteDateFrom.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
//									dteDateTo.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
//								}});					
//						}
//						{
//							rbtnDocDate = new JRadioButton();
//							pnlSearchBy.add(rbtnDocDate);
//							rbtnDocDate.setText("by Doc. Date");
//							rbtnDocDate.setBounds(277, 12, 77, 18);
//							rbtnDocDate.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
//							rbtnDocDate.setSelected(false);
//							rbtnDocDate.setEnabled(false);
//							rbtnDocDate.setPreferredSize(new java.awt.Dimension(232, 24));
//							rbtnDocDate.addActionListener(new ActionListener() {
//								public void actionPerformed(ActionEvent ae){									
//									rbtnDocNumber.setSelected(false);	rbtnDocDate.setSelected(true);
//
//									lblDocNo_fr.setEnabled(false);	
//									lookupPV_no_from.setEnabled(false);	
//									lblDocNo_to.setEnabled(false);	
//									lookupPV_no_to.setEnabled(false);									
//									lookupPV_no_from.setText("");	
//									lookupPV_no_to.setText("");	
//
//									lblDateFrom.setEnabled(true);	
//									dteDateFrom.setEnabled(true);
//									lblDateTo.setEnabled(true);	
//									dteDateTo.setEnabled(true);
//								}});
//						}
//					}
//				}	
//			}			
//			{
//				pnlDoc = new JPanel(new BorderLayout(0,0));
//				pnlCenter2.add(pnlDoc, BorderLayout.CENTER);
//				pnlDoc.setPreferredSize(new java.awt.Dimension(547, 80));				
//
//				{
//					pnlCenter = new JPanel(new GridLayout(1,1,0,0));
//					pnlDoc.add(pnlCenter, BorderLayout.NORTH);
//					pnlCenter.setPreferredSize(new java.awt.Dimension(547, 59));
//					pnlCenter.setBorder(components.JTBorderFactory.createTitleBorder("by Doc Number"));					
//
//					{
//						lblDocNo_fr = new JLabel("From :  ", JLabel.TRAILING);
//						pnlCenter.add(lblDocNo_fr, BorderLayout.CENTER);
//						lblDocNo_fr.setEnabled(false);							
//					}
//					{
//						lookupPV_no_from = new _JLookup(null, "PV No.", 2, 2);
//						pnlCenter.add(lookupPV_no_from);
//						lookupPV_no_from.setBounds(20, 27, 20, 25);
//						lookupPV_no_from.setReturnColumn(0);
//						lookupPV_no_from.setEnabled(false);					
//						lookupPV_no_from.setPreferredSize(new java.awt.Dimension(157, 22));
//						lookupPV_no_from.addLookupListener(new LookupListener() {
//							public void lookupPerformed(LookupEvent event) {
//								Object[] data = ((_JLookup)event.getSource()).getDataSet();
//								if(data != null){		
//									pv_no_from = (String) data[0];	
//
//								}
//							}
//						});
//					}	
//					{
//						lblDocNo_to = new JLabel("To :  ", JLabel.TRAILING);
//						pnlCenter.add(lblDocNo_to);
//						lblDocNo_to.setEnabled(false);	
//					}
//					{
//						lookupPV_no_to = new _JLookup(null, "PV No.", 2, 2);
//						pnlCenter.add(lookupPV_no_to);
//						lookupPV_no_to.setBounds(20, 27, 20, 25);
//						lookupPV_no_to.setReturnColumn(0);
//						lookupPV_no_to.setEnabled(true);	
//						lookupPV_no_to.setPreferredSize(new java.awt.Dimension(157, 22));
//						lookupPV_no_to.addLookupListener(new LookupListener() {
//							public void lookupPerformed(LookupEvent event) {
//								Object[] data = ((_JLookup)event.getSource()).getDataSet();
//								if(data != null){		
//									pv_no_to = (String) data[0];	
//
//								}
//							}
//						});
//					}	
//				}
//				{
//					pnlCenter_b = new JPanel(new GridLayout(1,1,0,0));
//					pnlDoc.add(pnlCenter_b, BorderLayout.CENTER);
//					pnlCenter_b.setPreferredSize(new java.awt.Dimension(480, 65));
//					pnlCenter_b.setBorder(components.JTBorderFactory.createTitleBorder("by Doc Date"));
//
//					{
//						pnlCriteria2 = new JPanel(new GridLayout(1, 2, 3, 3));
//						pnlCenter_b.add(pnlCriteria2, BorderLayout.WEST);					
//						pnlCriteria2.setPreferredSize(new java.awt.Dimension(480, 63));
//
//						{
//							lblDateFrom = new JLabel("From  :", JLabel.TRAILING);
//							pnlCriteria2.add(lblDateFrom, BorderLayout.CENTER);
//							lblDateFrom.setEnabled(false);							
//						}
//						{
//							dteDateFrom = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
//							pnlCriteria2.add(dteDateFrom, BorderLayout.CENTER);						
//							dteDateFrom.setDate(null);
//							dteDateFrom.setEnabled(false);
//							dteDateFrom.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
//						}		
//						{
//							lblDateTo = new JLabel("To  :", JLabel.TRAILING);
//							pnlCriteria2.add(lblDateTo);
//							lblDateTo.setEnabled(false);	
//						}
//						{
//							dteDateTo = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
//							pnlCriteria2.add(dteDateTo, BorderLayout.EAST);
//							dteDateTo.setBounds(485, 7, 125, 21);
//							dteDateTo.setDate(null);
//							dteDateTo.setEnabled(false);
//							dteDateTo.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
//						}		
//					}
//				}	
//			}
//
//			{				
//				pnlSouth = new JPanel(new GridLayout(1,2,5,5));
//				pnlMain.add(pnlSouth, BorderLayout.SOUTH);
//				pnlSouth.setPreferredSize(new Dimension(300, 30));
//
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
//					btnCancel.addActionListener(new ActionListener() {
//						public void actionPerformed(ActionEvent e) {
//							enable_fields(true);	
//							
//						}
//					});
//				}
//			}
//		}
//		this.setFocusTraversalPolicy(new FncFocusTraversalPolicy(lookupCompany, lookupPV_no_from,  lookupPV_no_to,
//				dteDateFrom, dteDateTo, btnPreview));
//		this.setBounds(0, 0, 560, 450);  //174
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
						c.weighty = 0.25;

						c.gridx = 0; 
						c.gridy = 0; 
						
						JPanel pnlCompany = new JPanel(new GridBagLayout());
						pnlCenter.add(pnlCompany, c);
						pnlCompany.setBorder(components.JTBorderFactory.createTitleBorder("Comp. / Project Option" ));// XXX
						{
							GridBagConstraints cons_com = new GridBagConstraints();
							cons_com.fill = GridBagConstraints.BOTH;
							cons_com.insets = new Insets(5, 5, 5, 5);
							cons_com.ipady = 3;
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
								lookupCompany.setPreferredSize(new Dimension(60,0));
								lookupCompany.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup)event.getSource()).getDataSet();
										if(data != null){

											co_id = (String) data[0];	
											company		= (String) data[1];	
											company_logo = (String) data[3];

											String name = (String) data[1];						
											txtCompany.setText(name);

											enable_fields(true);
											lblDateFrom.setEnabled(false);		
											dteDateFrom.setEnabled(false);
											lblDateTo.setEnabled(false);	
											dteDateTo.setEnabled(false);

											KEYBOARD_MANAGER.focusNextComponent();
											btnCancel.setEnabled(true);
											btnPreview.setEnabled(true);
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
						}
					}
					{
						c.weightx = 1;
						c.weighty = 1.5;

						c.gridx = 0; 
						c.gridy = 1; 
						
						JPanel pnlDocument = new JPanel(new GridBagLayout());
						pnlCenter.add(pnlDocument, c);
						pnlDocument.setBorder(components.JTBorderFactory.createTitleBorder("Document Option"));
						{
							GridBagConstraints cons_docsopt = new GridBagConstraints();
							cons_docsopt.fill = GridBagConstraints.BOTH;
							cons_docsopt.insets = new Insets(5, 5, 5, 5);
							//cons_docsopt.ipady = 10;
							
							/*LINE 1*/
							{
								cons_docsopt.weightx = 0;
								cons_docsopt.weighty = 1;

								cons_docsopt.gridx = 0; 
								cons_docsopt.gridy = 0; 
								
								lblDocument = new JLabel("Doc. Name");
								pnlDocument.add(lblDocument, cons_docsopt);
								lblDocument.setEnabled(true);	
								lblDocument.setFont(FncGlobal.sizeLabel);
							}
							{
								cons_docsopt.weightx = 1;
								cons_docsopt.weighty = 1;

								cons_docsopt.gridx = 1; 
								cons_docsopt.gridy = 0; 
								
								cons_docsopt.gridwidth = 2;

								String docu[] = {"PAYABLE VOUCHER (PV)","JOURNAL VOUCHER (JV)"};					
								cmbDocument = new JComboBox(docu);
								pnlDocument.add(cmbDocument, cons_docsopt);
								cmbDocument.setSelectedItem(null);
								cmbDocument.setEnabled(true);
								cmbDocument.setSelectedIndex(0);
								cmbDocument.setFont(FncGlobal.sizeTextValue);
								cmbDocument.addItemListener(new ItemListener() {
									public void itemStateChanged(ItemEvent evt) 
									{
										if (cmbDocument.getSelectedIndex()==0)
										{
											lookupPV_no_from.setLookupSQL(getPV_no());	
											lookupPV_no_to.setLookupSQL(getPV_no());
											lookupCreatedBy.setLookupSQL(SQL_EmpPV());
											lookupTransType.setLookupSQL(SQL_TypePV());
											lookupPayee.setEditable(true);
											
										}
										else if (cmbDocument.getSelectedIndex()==1)
										{
											lookupPV_no_from.setLookupSQL(getJV_no());	
											lookupPV_no_to.setLookupSQL(getJV_no());
											lookupCreatedBy.setLookupSQL(SQL_EmpJV());
											lookupTransType.setLookupSQL(SQL_TypeJV());
											lookupPayee.setEditable(false);
											lookupPayee.setValue(null);
											txtPayee.setText("");
										}

									}
								});		
							
							}
							
							/*LINE 2*/
							{
								cons_docsopt.weightx = 0;
								cons_docsopt.weighty = 1;

								cons_docsopt.gridx = 0; 
								cons_docsopt.gridy = 1; 

								cons_docsopt.gridwidth = 1;
								
								lblStatus = new JLabel("Status");
								pnlDocument.add(lblStatus, cons_docsopt);
								lblStatus.setEnabled(true);	
								lblStatus.setFont(FncGlobal.sizeLabel);
							}
							{
								cons_docsopt.weightx = 0;
								cons_docsopt.weighty = 1;

								cons_docsopt.gridx = 1; 
								cons_docsopt.gridy = 1;
								
								cons_docsopt.gridwidth = 2;

								String status[] = {"ACTIVE","TAGGED","POSTED","INACTIVE","DELETED","ALL"};					
								cmbStatus = new JComboBox(status);
								pnlDocument.add(cmbStatus, cons_docsopt);
								cmbStatus.setSelectedItem(null);
								cmbStatus.setEnabled(true);
								cmbStatus.setSelectedIndex(0);	
								cmbStatus.setFont(FncGlobal.sizeTextValue);
								((JLabel)cmbStatus.getRenderer()).setHorizontalAlignment(JLabel.LEFT);
								cmbStatus.addItemListener(new ItemListener() {
									public void itemStateChanged(ItemEvent evt) 
									{
										if (cmbStatus.getSelectedIndex()==0)
										{
											status_id = "A"; 
										}
										else if (cmbStatus.getSelectedIndex()==1)
										{
											status_id = "F"; 
										}
										else if (cmbStatus.getSelectedIndex()==2)
										{
											status_id = "P"; 
										}
										else if (cmbStatus.getSelectedIndex()==3)
										{
											status_id = "I"; 
										}
										else if (cmbStatus.getSelectedIndex()==4)
										{
											status_id = "D"; 
										}
										else if (cmbStatus.getSelectedIndex()==5)
										{
											status_id = ""; 
										}
									}
								});								
							}
							
							/*LINE 3*/
							{
								cons_docsopt.weightx = 0;
								cons_docsopt.weighty = 1;

								cons_docsopt.gridx = 0; 
								cons_docsopt.gridy = 2; 
								
								cons_docsopt.gridwidth = 1;
								
								JLabel lblCreatedBy = new JLabel("Created by");
								pnlDocument.add(lblCreatedBy, cons_docsopt);
								lblCreatedBy.setEnabled(true);	
								lblCreatedBy.setFont(FncGlobal.sizeLabel);
							}
							{
								cons_docsopt.weightx = 0.25;
								cons_docsopt.weighty = 1;

								cons_docsopt.gridx = 1; 
								cons_docsopt.gridy = 2; 
								
								lookupCreatedBy = new _JLookup(null, "Created By");
								pnlDocument.add(lookupCreatedBy, cons_docsopt);
								lookupCreatedBy.setReturnColumn(0);
								lookupCreatedBy.setFilterName(true);
								lookupCreatedBy.setFont(FncGlobal.sizeTextValue);
								if (cmbDocument.getSelectedIndex() == 0) {
									lookupCreatedBy.setLookupSQL(SQL_EmpPV());
								} 
								lookupCreatedBy.addActionListener(this);
								lookupCreatedBy.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup) event.getSource()).getDataSet();
										if (data != null) {
											txtCreatedBy.setText(String.format("%s", (String) data[1]));
								
											KEYBOARD_MANAGER.focusNextComponent();
										}
									}
								});
							}
							{
								cons_docsopt.weightx = 1.25;
								cons_docsopt.weighty = 1;

								cons_docsopt.gridx = 2; 
								cons_docsopt.gridy = 2; 
								
								txtCreatedBy = new JTextField();
								pnlDocument.add(txtCreatedBy, cons_docsopt);
								txtCreatedBy.setHorizontalAlignment(JLabel.LEFT);
								txtCreatedBy.setFont(FncGlobal.sizeTextValue);
							}
							
							/*LINE 4*/
							{
								cons_docsopt.weightx = 0;
								cons_docsopt.weighty = 1;

								cons_docsopt.gridx = 0; 
								cons_docsopt.gridy = 3; 
								
								JLabel lblType = new JLabel("Trans. Type");
								pnlDocument.add(lblType, cons_docsopt);
								lblType.setEnabled(true);	
								lblType.setFont(FncGlobal.sizeLabel);
							}
							{
								cons_docsopt.weightx = 0.25;
								cons_docsopt.weighty = 1;

								cons_docsopt.gridx = 1; 
								cons_docsopt.gridy = 3; 
								
								lookupTransType = new _JLookup(null, "Transaction Type");
								pnlDocument.add(lookupTransType, cons_docsopt);
								lookupTransType.setReturnColumn(0);
								lookupTransType.setFilterName(true);
								lookupTransType.setFont(FncGlobal.sizeTextValue);
								if (cmbDocument.getSelectedIndex() == 0) {
									lookupTransType.setLookupSQL(SQL_TypePV());
								} 
								
								lookupTransType.addActionListener(this);
								lookupTransType.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup) event.getSource()).getDataSet();
										if (data != null) {
											txtTransType.setText(String.format("%s", (String) data[1]));
								
											KEYBOARD_MANAGER.focusNextComponent();
										}
									}
								});
							}
							{
								cons_docsopt.weightx = 1.25;
								cons_docsopt.weighty = 1;

								cons_docsopt.gridx = 2; 
								cons_docsopt.gridy = 3; 
								
								txtTransType = new JTextField();
								pnlDocument.add(txtTransType, cons_docsopt);
								txtTransType.setHorizontalAlignment(JLabel.LEFT);
								txtTransType.setFont(FncGlobal.sizeTextValue);
							}
							{
								cons_docsopt.weightx = 0;
								cons_docsopt.weighty = 1;

								cons_docsopt.gridx = 0; 
								cons_docsopt.gridy = 4; 
								
								JLabel lblPayee = new JLabel("Payee");
								pnlDocument.add(lblPayee, cons_docsopt);
								lblPayee.setEnabled(true);	
								lblPayee.setFont(FncGlobal.sizeLabel);
							}
							{
								cons_docsopt.weightx = 0.25;
								cons_docsopt.weighty = 1;

								cons_docsopt.gridx = 1; 
								cons_docsopt.gridy = 4; 
								
								lookupPayee = new _JLookup(null, "Payee");
								pnlDocument.add(lookupPayee, cons_docsopt);
								lookupPayee.setReturnColumn(0);
								lookupPayee.setFilterName(true);
								lookupPayee.setFont(FncGlobal.sizeTextValue);
								if (cmbDocument.getSelectedIndex() == 0) {
									lookupPayee.setLookupSQL(sqlPayee());
								} 
								
								lookupPayee.addActionListener(this);
								lookupPayee.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup) event.getSource()).getDataSet();
										if (data != null) {
											txtPayee.setText(String.format("%s", (String) data[1]));
								
											KEYBOARD_MANAGER.focusNextComponent();
										}
									}
								});
							}
							{
								cons_docsopt.weightx = 1.25;
								cons_docsopt.weighty = 1;

								cons_docsopt.gridx = 2; 
								cons_docsopt.gridy = 4; 
								
								txtPayee = new JTextField();
								pnlDocument.add(txtPayee, cons_docsopt);
								txtPayee.setHorizontalAlignment(JLabel.LEFT);
								txtPayee.setFont(FncGlobal.sizeTextValue);
								txtPayee.setEditable(false);
							}
							
							/*LINE 5*/
							{
								cons_docsopt.weightx = 0;
								cons_docsopt.weighty = 1;

								cons_docsopt.gridx = 0; 
								cons_docsopt.gridy = 5; 
								
								lblSearchBy = new JLabel("Search by:");
								pnlDocument.add(lblSearchBy, cons_docsopt);
								lblSearchBy.setEnabled(false);	
								lblSearchBy.setFont(FncGlobal.sizeLabel);
							}
							{
								cons_docsopt.weightx = 0;
								cons_docsopt.weighty = 1;

								cons_docsopt.gridx = 1; 
								cons_docsopt.gridy = 5; 
								
								rbtnDocNumber = new JRadioButton();
								pnlDocument.add(rbtnDocNumber, cons_docsopt);
								rbtnDocNumber.setText("by Doc. No.");
								rbtnDocNumber.setFont(FncGlobal.sizeTextValue);
								rbtnDocNumber.setSelected(true);
								rbtnDocNumber.setEnabled(false);
								rbtnDocNumber.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent ae){									
										rbtnDocNumber.setSelected(true);	rbtnDocDate.setSelected(false);	

										lblDocNo_fr.setEnabled(true);	
										lookupPV_no_from.setEnabled(true);
										lblDocNo_to.setEnabled(true);	
										lookupPV_no_to.setEnabled(true);									

										lblDateFrom.setEnabled(false);	
										dteDateFrom.setEnabled(false);
										lblDateTo.setEnabled(false);	
										dteDateTo.setEnabled(false);
										dteDateFrom.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
										dteDateTo.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
									}});
							}
							{
								cons_docsopt.weightx = 0;
								cons_docsopt.weighty = 1;

								cons_docsopt.gridx = 2; 
								cons_docsopt.gridy = 5; 
								

								rbtnDocDate = new JRadioButton();
								pnlDocument.add(rbtnDocDate, cons_docsopt);
								rbtnDocDate.setText("by Doc. Date");
								rbtnDocDate.setFont(FncGlobal.sizeTextValue);
								rbtnDocDate.setSelected(false);
								rbtnDocDate.setEnabled(false);
								rbtnDocDate.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent ae){									
										rbtnDocNumber.setSelected(false);	rbtnDocDate.setSelected(true);

										lblDocNo_fr.setEnabled(false);	
										lookupPV_no_from.setEnabled(false);	
										lblDocNo_to.setEnabled(false);	
										lookupPV_no_to.setEnabled(false);									
										lookupPV_no_from.setText("");	
										lookupPV_no_to.setText("");	

										lblDateFrom.setEnabled(true);	
										dteDateFrom.setEnabled(true);
										lblDateTo.setEnabled(true);	
										dteDateTo.setEnabled(true);
									}});
							
							}
						}
					}
					{
						c.weightx = 1;
						c.weighty = 0.25;

						c.gridx = 0; 
						c.gridy = 2; 
						
						JPanel pnlDocBy = new JPanel(new GridBagLayout());
						pnlCenter.add(pnlDocBy, c);
						pnlDocBy.setBorder(components.JTBorderFactory.createTitleBorder("by Doc Number"));
						{
							GridBagConstraints cons_docnum = new GridBagConstraints();
							cons_docnum.fill = GridBagConstraints.BOTH;
							cons_docnum.insets = new Insets(5, 5, 5, 5);

							{
								cons_docnum.weightx = 0.5;
								cons_docnum.weighty = 1;

								cons_docnum.gridx = 0; 
								cons_docnum.gridy = 0; 

								lblDocNo_fr = new JLabel("From:", JLabel.CENTER);
								pnlDocBy.add(lblDocNo_fr, cons_docnum);
								lblDocNo_fr.setEnabled(false);	
								lblDocNo_fr.setFont(FncGlobal.sizeLabel);
							}
							{
								cons_docnum.weightx = 1.5;
								cons_docnum.weighty = 1;

								cons_docnum.gridx = 1; 
								cons_docnum.gridy = 0; 
								
								lookupPV_no_from = new _JLookup(null, "PV No.", 2, 2);
								pnlDocBy.add(lookupPV_no_from, cons_docnum);
								lookupPV_no_from.setReturnColumn(0);
								lookupPV_no_from.setEnabled(false);
								lookupPV_no_from.setFont(FncGlobal.sizeTextValue);
								lookupPV_no_from.addLookupListener(new LookupListener() {
									
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup)event.getSource()).getDataSet();
										if(data != null){		
											pv_no_from = (String) data[0];	
										}
									}
								});
							}
							{
								cons_docnum.weightx = 0.5;
								cons_docnum.weighty = 1;

								cons_docnum.gridx = 2; 
								cons_docnum.gridy = 0; 
								
								lblDocNo_to = new JLabel("To:", JLabel.CENTER);
								pnlDocBy.add(lblDocNo_to, cons_docnum);
								lblDocNo_to.setEnabled(false);	
								lblDocNo_to.setFont(FncGlobal.sizeLabel);
							}
							{
								cons_docnum.weightx = 1.5;
								cons_docnum.weighty = 1;

								cons_docnum.gridx = 3; 
								cons_docnum.gridy = 0; 
								

								lookupPV_no_to = new _JLookup(null, "PV No.", 2, 2);
								pnlDocBy.add(lookupPV_no_to, cons_docnum);
								lookupPV_no_to.setReturnColumn(0);
								lookupPV_no_to.setEnabled(true);	
								lookupPV_no_to.setFont(FncGlobal.sizeTextValue);
								lookupPV_no_to.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup)event.getSource()).getDataSet();
										if(data != null){		
											pv_no_to = (String) data[0];	

										}
									}
								});
							}
						}
					}
					{
						c.weightx = 1;
						c.weighty = 0.25;

						c.gridx = 0; 
						c.gridy = 3; 
						
						JPanel pnlDocDate = new JPanel(new GridBagLayout());
						pnlCenter.add(pnlDocDate, c);
						pnlDocDate.setBorder(components.JTBorderFactory.createTitleBorder("by Doc Date"));
						{
							GridBagConstraints cons_docdate = new GridBagConstraints();
							cons_docdate.fill = GridBagConstraints.BOTH;
							cons_docdate.insets = new Insets(5, 5, 5, 5);
							
							{
								cons_docdate.weightx = 0.5;
								cons_docdate.weighty = 1;

								cons_docdate.gridx = 0; 
								cons_docdate.gridy = 0; 
								
								lblDateFrom = new JLabel("From:", JLabel.CENTER);
								pnlDocDate.add(lblDateFrom, cons_docdate);
								lblDateFrom.setEnabled(false);	
								lblDateFrom.setFont(FncGlobal.sizeLabel);
							}
							{
								cons_docdate.weightx = 1;
								cons_docdate.weighty = 1;

								cons_docdate.gridx = 1; 
								cons_docdate.gridy = 0; 
								
								dteDateFrom = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
								pnlDocDate.add(dteDateFrom, cons_docdate);						
								dteDateFrom.setDate(null);
								dteDateFrom.setEnabled(false);
								dteDateFrom.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
								dteDateFrom.setFont(FncGlobal.sizeTextValue);
							}
							{
								cons_docdate.weightx = 0.5;
								cons_docdate.weighty = 1;

								cons_docdate.gridx = 2; 
								cons_docdate.gridy = 0; 
								
								lblDateTo = new JLabel("To:", JLabel.CENTER);
								pnlDocDate.add(lblDateTo, cons_docdate);
								lblDateTo.setEnabled(false);
								lblDateTo.setFont(FncGlobal.sizeLabel);
							}
							{
								cons_docdate.weightx = 1;
								cons_docdate.weighty = 1;

								cons_docdate.gridx = 3; 
								cons_docdate.gridy = 0;
								
								dteDateTo = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
								pnlDocDate.add(dteDateTo, cons_docdate);
								dteDateTo.setDate(null);
								dteDateTo.setEnabled(false);
								dteDateTo.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
								dteDateTo.setFont(FncGlobal.sizeTextValue);
							}
						}
					}
				}
			}
			{				
				pnlSouth = new JPanel(new GridLayout(1,2,5,5));
				pnlMain.add(pnlSouth, BorderLayout.SOUTH);
				pnlSouth.setPreferredSize(new Dimension(0,30));

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
					btnCancel.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							enable_fields(true);	
							
						}
					});
				}
			}
		}
		
		this.setFocusTraversalPolicy(new FncFocusTraversalPolicy(lookupCompany, lookupPV_no_from,  lookupPV_no_to,
				dteDateFrom, dteDateTo, btnPreview));
		//this.setBounds(0, 0, 560, 450);  //174

		//added 01/26/2016 - purpose - set CENQHOMES as default company
		initialize_comp();
	}

	@Override
	public void ancestorAdded(AncestorEvent event) {
		//lookupProject.requestFocus();
	}

	@Override
	public void ancestorMoved(AncestorEvent event) { }

	@Override
	public void ancestorRemoved(AncestorEvent event) { }

	@Override
	public void actionPerformed(ActionEvent e) {

		if (FncAcounting.EmpCanPreview(UserInfo.EmployeeCode, "4")==true||FncAcounting.EmpCanPreview(UserInfo.EmployeeCode, "2")==true)
		{
			if(e.getActionCommand().equals("Preview")){ previewDoc(); }  //  				
		}
		else 
		{JOptionPane.showMessageDialog(getContentPane(),"Sorry, you are not authorized to preview PV/JV.","Warning",JOptionPane.WARNING_MESSAGE); }

	}

	private void previewDoc(){

		String x = "";
		if (rbtnDocNumber.isSelected()==true) {x="Doc. Nos.";}
		else {x="Doc. Period";}

		if(doc_range_complete()==false && rbtnDocNumber.isSelected()==true)
		{JOptionPane.showMessageDialog(getContentPane(), "Enter complete " + x, "Incomplete Details", 
				JOptionPane.ERROR_MESSAGE);}
		else 
		{

			String search_by = "";
			String pv_no_from = "";
			String pv_no_to ="";
			String criteria = "";	

			if(rbtnDocNumber.isSelected()==true)
			{
				search_by = "doc_no";
				pv_no_from = (String) lookupPV_no_from.getText();
				pv_no_to = (String) lookupPV_no_to.getText();
				//pv_no_from = Integer.parseInt(lookupPV_no_from.getText());
				//pv_no_to = Integer.parseInt(lookupPV_no_to.getText());		
				
			}
			else 
			{
				search_by = "date";
				pv_no_from = "";
				pv_no_to = "";	
				
			}
			
			if (cmbDocument.getSelectedIndex()==0){criteria = "PV Batch Preview";}
			else {criteria = "JV Batch Preview";}
				
			String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());		

			Map<String, Object> mapParameters = new HashMap<String, Object>();
			mapParameters.put("company", company);
			mapParameters.put("co_id", co_id);
			mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
			mapParameters.put("remark_unliq_ca", "");
			mapParameters.put("pv_no_from", pv_no_from);
			mapParameters.put("pv_no_to", pv_no_to);
			mapParameters.put("prepared_by", UserInfo.FullName);
			mapParameters.put("date_from", dteDateFrom.getDate());
			mapParameters.put("date_to",  dteDateTo.getDate());
			mapParameters.put("search_by",  search_by);
			mapParameters.put("from_cpf",  "no");
			mapParameters.put("status_id",  status_id);
			mapParameters.put("created_by",  lookupCreatedBy.getText());
			mapParameters.put("trans_type",  lookupTransType.getText());
			mapParameters.put("payee_id", lookupPayee.getValue()); //ADDED FOR DCRF 2768

			if (cmbDocument.getSelectedIndex()==0)
			{
				FncReport.generateReport("/Reports/rptPV_preview_batch.jasper", reportTitle, company, mapParameters);	
			}
			else 
			{
				FncReport.generateReport("/Reports/rptJV_preview_batch.jasper", reportTitle, company, mapParameters);	
			}
		}	
	}

	private void enable_fields(Boolean x){

		lblSearchBy.setEnabled(x);
		rbtnDocNumber.setEnabled(x);
		rbtnDocNumber.setSelected(true);
		rbtnDocDate.setEnabled(x);
		rbtnDocDate.setSelected(false);
		lblDocNo_fr.setEnabled(x);					
		lblDocNo_to.setEnabled(x);	
		lookupPV_no_from.setEnabled(x);	
		lookupPV_no_from.setText("");
		lookupPV_no_to.setEnabled(x);	
		lookupPV_no_to.setText("");
		lblDateFrom.setEnabled(x);		
		dteDateFrom.setEnabled(x);
		lblDateTo.setEnabled(x);	
		dteDateTo.setEnabled(x);
		cmbDocument.setSelectedIndex(0);	
		lookupPV_no_from.setLookupSQL(getPV_no());	
		lookupPV_no_to.setLookupSQL(getPV_no());	
	}

	private void initialize_comp(){		

		co_id 		= "02";	
		company		= "CENQHOMES DEVELOPMENT CORPORATION";
		company_logo = RequestForPayment.sql_getCompanyLogo();			
		txtCompany.setText(company);

		enable_fields(true);
		lblDateFrom.setEnabled(false);		
		dteDateFrom.setEnabled(false);
		lblDateTo.setEnabled(false);	
		dteDateTo.setEnabled(false);

		KEYBOARD_MANAGER.focusNextComponent();
		btnCancel.setEnabled(true);
		btnPreview.setEnabled(true);

		lookupCompany.setValue(co_id);

		lookupPV_no_from.setLookupSQL(getPV_no());	
		lookupPV_no_to.setLookupSQL(getPV_no());	
	}


	//checking
	private Boolean doc_range_complete(){

		boolean x = false;

		if (lookupPV_no_from.getText().equals("")||lookupPV_no_to.getText().equals("")) { x = false;}
		else { x = true; }		

		return x;		
	}


	//SQL
	private String getPV_no(){//used

		return 

		"select \r\n" + 
		"a.pv_no as \"PV No.\" , \r\n" + 
		"a.pv_date as \"PV Date\"      ,\r\n" + 
		"(case when a.pay_type_id = 'B' then 'CHECK' else 'CASH' end) as \"Pay Type\",  \r\n" + 
		"trim(b.entity_name) as \"Payee\"    ,\r\n" + 
		"c.status_desc  as \"status\"  ,\r\n" + 
		"a.date_posted  as \"Date Posted\"  \r\n" + 

		"from rf_pv_header a  \r\n" + 
		"left join rf_entity b on a.entity_id1 = b.entity_id\r\n" + 
		"left join mf_record_status c on a.status_id = c.status_id \n"  +
		"where a.co_id = '"+co_id+"' " +
		"order by a.pv_no desc" ;		


	}

	private String getJV_no(){//used

		return 

		"select \r\n" + 
		"trim(a.jv_no) as \"JV No\",  \n" +
		"a.jv_date as \"JV Date\",  \n" +
		"a.fiscal_yr  as \"Year\",  \n" +
		"a.period_id  as \"Period\" ,  \n" +
		"c.status_desc as \"Status\" \n " +

		"from rf_jv_header a  \r\n" + 		
		"left join mf_record_status c on a.status_id = c.status_id \n"  +
		"where a.co_id = '"+co_id+"' " +
		"order by a.jv_no desc" ;			

	}
	private static String SQL_EmpPV() {
		return "select \r\n" + 
				"\r\n" + 
				"a.emp_code as \"Employee Code\", \r\n" + 
				"upper(trim(d.first_name)||' '||trim(d.last_name)) as \"Name\",\r\n" + 
				"b.dept_alias as \"Dept\",\r\n" + 
				"c.division_alias as \"Divsion\"\r\n" + 
				"\r\n" + 
				"from em_employee a\r\n" + 
				"left join mf_department b on a.dept_code = b.dept_code\r\n" + 
				"left join mf_division c on a.div_code = c.division_code\r\n" + 
				"left join rf_entity d on a.entity_id = d.entity_id \n" +
				"left join mf_employee_status e on a.emp_status = e.empstatus_code \r\n" + 
				"\r\n " +
				"where emp_code in (select created_by from rf_pv_header where status_id != 'I')" +
				"\r\n" + 
				"order by d.first_name";
	}
	private static String SQL_EmpJV() {
		return "select \r\n" + 
				"\r\n" + 
				"a.emp_code as \"Employee Code\", \r\n" + 
				"upper(trim(d.first_name)||' '||trim(d.last_name)) as \"Name\",\r\n" + 
				"b.dept_alias as \"Dept\",\r\n" + 
				"c.division_alias as \"Divsion\"\r\n" + 
				"\r\n" + 
				"from em_employee a\r\n" + 
				"left join mf_department b on a.dept_code = b.dept_code\r\n" + 
				"left join mf_division c on a.div_code = c.division_code\r\n" + 
				"left join rf_entity d on a.entity_id = d.entity_id \n" +
				"left join mf_employee_status e on a.emp_status = e.empstatus_code \r\n" + 
				"\r\n " +
				"where emp_code in (select created_by from rf_jv_header where status_id != 'I')" +
				"\r\n" + 
				"order by d.first_name";
	}
	/*public String SQL_Type(Integer doc) {
		String sql = "";
		if (doc == 0) {
			sql = "select " +
					"rplf_type_id as \"Type ID\",  \n" +
					"trim(rplf_type_desc) as \"Description\"  \n" +
					"from mf_rplf_type where status_id = 'A' " +
					"order by rplf_type_id    ";
		}
		else if (doc == 1) {
			sql = "select \r\n" + 
					"\r\n" + 
					"a.tran_id as \"Tran ID\",\r\n" + 
					"trim(a.tran_desc)  as \"Tran. Desc.\" ,\r\n" + 
					"a.system_id  as \"System ID\", " +		
					"b.status_desc  as \"Status\" \r\n" + 
					"\r\n" + 
					"from mf_acctg_trans a\r\n" + 
					"left join mf_record_status b on a.status_id=b.status_id\r\n" + 
					"where a.status_id = 'A'"  ;	
		}
		return sql;
	}*/
	public String SQL_TypePV() {
		String sql = "select " +
					"rplf_type_id as \"Type ID\",  \n" +
					"trim(rplf_type_desc) as \"Description\"  \n" +
					"from mf_rplf_type where status_id = 'A' " +
					"order by rplf_type_id    ";
		return sql;
	}
	public String SQL_TypeJV() {
		String sql = "select \r\n" + 
					"\r\n" + 
					"a.tran_id as \"Tran ID\",\r\n" + 
					"trim(a.tran_desc)  as \"Tran. Desc.\" ,\r\n" + 
					"a.system_id  as \"System ID\", " +		
					"b.status_desc  as \"Status\" \r\n" + 
					"\r\n" + 
					"from mf_acctg_trans a\r\n" + 
					"left join mf_record_status b on a.status_id=b.status_id\r\n" + 
					"where a.status_id = 'A'"  ;	
		return sql;
	}
	
	public String sqlPayee() {

		String sql = "select " + "trim(entity_id) as \"Entity ID\",  \n" + "trim(entity_name) as \"Name\",  \n"
				+ "entity_kind as \"Kind\",  \n" + "vat_registered as \"VAT\"  \n" + "from ("
				+ "select entity_id, entity_name, entity_kind, vat_registered from rf_entity where status_id = 'A'  \n"
//				+ "(select entity_id, entity_name, entity_kind, vat_registered from rf_entity where entity_id in (select entity_id  from em_employee)) union \n"
//				+ "(select entity_id, entity_name, entity_kind, vat_registered from rf_entity where vat_registered = true )"
				+ ") a \n"
				+ "order by a.entity_name  ";
		return sql;

	}
}
