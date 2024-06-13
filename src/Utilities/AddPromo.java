package Utilities;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXFormattedTextField;
import org.jdesktop.swingx.JXTextField;

import Database.pgSelect;
import Database.pgUpdate;
import tablemodel.modelPromoDetails;
import DateChooser._JDateChooser;
import FormattedTextField._JXFormattedTextField;
import Functions.FncGlobal;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JScrollPaneMain;
import components._JScrollPaneTotal;
import components._JTableMain;
import components._JTableTotal;
import components._JTagLabel;

public class AddPromo extends _JInternalFrame implements _GUI, ActionListener, MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3061284418918863916L;
	static String title = "Promo";
	static Dimension SIZE = new Dimension(1000, 600);

	private JPanel pnlMain;
	private JPanel pnlTable;
	private JPanel pnlSubTable;
	private JPanel pnlPromoList;
	private JPanel pnlSouth;
	private JPanel pnlSouthCenterb;
	private JPanel pnlPromoDetails;
	private JPanel pnlPromoDetails_b;
	private JPanel pnlPromoDtl_right;
	private JPanel pnlPromoDtl_rightb;
	private JPanel pnlPromoDetailsAmt;
	private JPanel pnlPromoDetailsAmt_1;
	private JPanel pnlPromoName;
	private JPanel pnlAcctLabel;
	private JPanel pnlPromoDesc;
	private JPanel pnlPromoDesc_1;
	private JPanel pnlPromo_right_a;
	private JPanel pnlPromoDetailsAmt_2;
	private JPanel pnlPromoDetailsAmt_2a;
	private JPanel pnlPromoDetailsAmt2;
	private JPanel pnlPromoDesc_3;
	private JPanel pnlPromoDetailsAmt_2b;

	private modelPromoDetails modelPromo;
	private modelPromoDetails modelPromo_total;

	private _JLookup lookupPromoCode;
	private _JLookup lookupAccount;
	private _JLookup lookupProject;

	private JButton btnCancel;
	private JButton btnSave;
	private JButton btnAddNew;
	private JButton btnEdit;

	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	private JLabel lblPromoName;
	private JLabel lblDateFr;
	private JLabel lblStatus;
	private JLabel lblDateTo;
	private JLabel lblAcctID;
	private JLabel lblReleaseTo;
	private JLabel lblType;
	private JLabel lblFreq;
	private JLabel lblRelGrp;
	private JLabel lblAmount;
	private JLabel lblProj;

	private _JTagLabel tagDateFrom;
	private _JTagLabel tagAccount;
	private _JTagLabel tagDateTo;
	private _JTagLabel tagProject;


	private _JScrollPaneMain scrollPromo;
	private _JScrollPaneTotal scrollPromo_total;
	private _JTableMain tblPromo;
	private JList rowHeaderPromo;
	private _JTableTotal tblPromo_total;
	
	private JXTextField txtPromoDesc;

	private JComboBox cmbStatus;
	private JComboBox cmbReleaseTo;
	private JComboBox cmbType;
	private JComboBox cmbFreq;
	private JComboBox cmbReleaseGrp;
	
	private _JDateChooser dteDateFrom;
	private _JDateChooser dteDateTo;
	
	private _JXFormattedTextField txtAmount;

	private Font dialog11Bold = new Font("DIALOG", Font.BOLD, 11);
	SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");		
	private NumberFormat nf = new DecimalFormat("###,###,###,##0.00"); 	

	String to_do 	= "";  //to distinguish whether to add new agent or new sched.	
	
	public AddPromo() {
		super(title, true, true, true, true);
		initGUI();
	}

	public AddPromo(String title) {
		super(title);
		// TODO Auto-generated constructor stub
	}

	public AddPromo(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see interfaces._GUI#initGUI()
	 */
	/* (non-Javadoc)
	 * @see interfaces._GUI#initGUI()
	 */
	@Override
	public void initGUI() {
		this.setLayout(new BorderLayout(5,5));
		this.setSize(SIZE);
		this.setPreferredSize(new java.awt.Dimension(747, 501));
		this.setBounds(0, 0, 747, 501);

		pnlMain = new JPanel();
		getContentPane().add(pnlMain, BorderLayout.CENTER);
		pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		pnlMain.setLayout(new BorderLayout(5, 5));
		pnlMain.setPreferredSize(new java.awt.Dimension(745, 487));		

		{
			pnlTable = new JPanel(new BorderLayout(5, 5));
			pnlMain.add(pnlTable, BorderLayout.CENTER);	
			pnlTable.setPreferredSize(new java.awt.Dimension(610, 30));	

			pnlSubTable = new JPanel();
			pnlTable.add(pnlSubTable, BorderLayout.CENTER);
			pnlSubTable.setLayout(new BorderLayout(5, 5));
			pnlSubTable.setPreferredSize(new java.awt.Dimension(923, 199));
			pnlSubTable.setBorder(lineBorder);

			{						

				pnlPromoList = new JPanel();
				pnlSubTable.add(pnlPromoList, BorderLayout.NORTH);
				pnlPromoList.setLayout(new BorderLayout(0,0));
				pnlPromoList.setBorder(lineBorder);		
				pnlPromoList.setPreferredSize(new java.awt.Dimension(733, 237));
				pnlPromoList.setBorder(JTBorderFactory.createTitleBorder("List of Promo"));

				//start of Commission Schedule (by client)
				{			

					{
						scrollPromo = new _JScrollPaneMain();
						pnlPromoList.add(scrollPromo, BorderLayout.CENTER);
						{
							modelPromo = new modelPromoDetails();

							tblPromo = new _JTableMain(modelPromo);
							scrollPromo.setViewportView(tblPromo);
							tblPromo.addMouseListener(this);								
							tblPromo.setSortable(false);
							tblPromo.getColumnModel().getColumn(0).setPreferredWidth(80);
							tblPromo.getColumnModel().getColumn(1).setPreferredWidth(80);
							tblPromo.getColumnModel().getColumn(2).setPreferredWidth(250);
							tblPromo.addKeyListener(new KeyAdapter() {
								public void keyReleased(KeyEvent evt) {clickTable();}							
								public void keyPressed(KeyEvent e) {clickTable();}	

							}); 
							tblPromo.addMouseListener(new MouseAdapter() {
								public void mousePressed(MouseEvent e) {
									if(tblPromo.rowAtPoint(e.getPoint()) == -1){}
									else{tblPromo.setCellSelectionEnabled(true);}
									clickTable();
								}
								public void mouseReleased(MouseEvent e) {
									if(tblPromo.rowAtPoint(e.getPoint()) == -1){}
									else{tblPromo.setCellSelectionEnabled(true);}
									clickTable();
								}
							});

						}
						{
							rowHeaderPromo = tblPromo.getRowHeader22();
							scrollPromo.setRowHeaderView(rowHeaderPromo);
							scrollPromo.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
						}
						{
							scrollPromo_total = new _JScrollPaneTotal(scrollPromo);
							pnlPromoList.add(scrollPromo_total, BorderLayout.SOUTH);
							{
								modelPromo_total = new modelPromoDetails();
								modelPromo_total.addRow(new Object[] {  "Total", new BigDecimal(0.00) });

								tblPromo_total = new _JTableTotal(modelPromo_total, tblPromo);
								tblPromo_total.setFont(dialog11Bold);
								scrollPromo_total.setViewportView(tblPromo_total);
								((_JTableTotal) tblPromo_total).setTotalLabel(0);
							}
						}
					}
				} 
				//end 
			}
			{
				pnlPromoDetails = new JPanel(new BorderLayout(5, 5));
				pnlSubTable.add(pnlPromoDetails, BorderLayout.CENTER);	
				pnlPromoDetails.setPreferredSize(new java.awt.Dimension(733, 174));	
				pnlPromoDetails.setBorder(BorderFactory.createEmptyBorder(0,0,5,0));
				pnlPromoDetails.setBorder(lineBorder);

				{
					pnlPromoName = new JPanel(new BorderLayout(5, 5));
					pnlPromoDetails.add(pnlPromoName, BorderLayout.NORTH);	
					pnlPromoName.setPreferredSize(new java.awt.Dimension(901, 36));	
					pnlPromoName.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));

					{
						pnlAcctLabel = new JPanel(new GridLayout(1, 1, 5, 10));
						pnlPromoName.add(pnlAcctLabel, BorderLayout.WEST);	
						pnlAcctLabel.setPreferredSize(new java.awt.Dimension(133, 36));	
						pnlAcctLabel.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));

						{
							lblPromoName = new JLabel("Promo Code | Name ", JLabel.TRAILING);
							pnlAcctLabel.add(lblPromoName);
							lblPromoName.setEnabled(false);	
							lblPromoName.setPreferredSize(new java.awt.Dimension(107, 40));
							lblPromoName.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
						}						

						pnlPromoDesc = new JPanel(new BorderLayout(5, 5));
						pnlPromoName.add(pnlPromoDesc, BorderLayout.CENTER);	
						pnlPromoDesc.setPreferredSize(new java.awt.Dimension(814, 40));	
						pnlPromoDesc.setBorder(BorderFactory.createEmptyBorder(4, 5, 4, 5));

						{
							pnlPromoDesc_1 = new JPanel(new GridLayout(1, 1, 5, 10));
							pnlPromoDesc.add(pnlPromoDesc_1, BorderLayout.WEST);	
							pnlPromoDesc_1.setPreferredSize(new java.awt.Dimension(67, 28));					

							{
								lookupPromoCode = new _JLookup(null, "Bank Acct ID",0,2);
								pnlPromoDesc_1.add(lookupPromoCode);
								lookupPromoCode.setLookupSQL(SQL_COMPANY());
								lookupPromoCode.setReturnColumn(0);
								lookupPromoCode.setEnabled(false);
								lookupPromoCode.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup)event.getSource()).getDataSet();
										if(data != null){


										}
									}
								});
							}																
						}
						{
							pnlPromoDesc_3 = new JPanel(new GridLayout(1, 1, 5, 10));
							pnlPromoDesc.add(pnlPromoDesc_3, BorderLayout.CENTER);	
							pnlPromoDesc_3.setPreferredSize(new java.awt.Dimension(684, 24));

							{
								txtPromoDesc = new JXTextField("");
								pnlPromoDesc_3.add(txtPromoDesc);
								txtPromoDesc.setEnabled(false);	
								txtPromoDesc.setEditable(false);
								txtPromoDesc.setBounds(120, 25, 300, 22);	
								txtPromoDesc.setHorizontalAlignment(JTextField.CENTER);
								txtPromoDesc.setToolTipText("Bank Acount No.");
								txtPromoDesc.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
								txtPromoDesc.setPreferredSize(new java.awt.Dimension(292, 24));
							}
						}
					}

				}
				{
					pnlPromoDetails_b = new JPanel(new BorderLayout(5, 5));
					pnlPromoDetails.add(pnlPromoDetails_b, BorderLayout.CENTER);	
					pnlPromoDetails_b.setPreferredSize(new java.awt.Dimension(926, 114));	
					pnlPromoDetails_b.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));					

					{
						pnlPromoDtl_right = new JPanel(new BorderLayout(0, 5));
						pnlPromoDetails_b.add(pnlPromoDtl_right, BorderLayout.WEST);	
						pnlPromoDtl_right.setPreferredSize(new java.awt.Dimension(210, 130));
						pnlPromoDtl_right.setBorder(BorderFactory.createEmptyBorder(0,0,0, 0));

						{
							pnlPromo_right_a = new JPanel(new GridLayout(5, 1, 0, 5));
							pnlPromoDtl_right.add(pnlPromo_right_a, BorderLayout.WEST);	
							pnlPromo_right_a.setPreferredSize(new java.awt.Dimension(91, 107));
							pnlPromo_right_a.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));

							{
								{
									lblDateFr = new JLabel("Date From", JLabel.TRAILING);
									pnlPromo_right_a.add(lblDateFr);
									lblDateFr.setEnabled(false);	
									lblDateFr.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
								}	
								{
									lblDateTo = new JLabel("Date To", JLabel.TRAILING);
									pnlPromo_right_a.add(lblDateTo);
									lblDateTo.setEnabled(false);	
									lblDateTo.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
								}	
								{
									lblAcctID = new JLabel("Account ID", JLabel.TRAILING);
									pnlPromo_right_a.add(lblAcctID);
									lblAcctID.setEnabled(false);	
									lblAcctID.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
								}
								{
									lblType = new JLabel("Type", JLabel.TRAILING);
									pnlPromo_right_a.add(lblType);
									lblType.setEnabled(false);	
									lblType.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
								}
								{
									lblAmount = new JLabel("Amount", JLabel.TRAILING);
									pnlPromo_right_a.add(lblAmount);
									lblAmount.setEnabled(false);	
									lblAmount.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
								}
							}

							pnlPromoDtl_rightb = new JPanel(new GridLayout(5, 1, 0, 5));
							pnlPromoDtl_right.add(pnlPromoDtl_rightb, BorderLayout.CENTER);	
							pnlPromoDtl_rightb.setPreferredSize(new java.awt.Dimension(127, 107));
							pnlPromoDtl_rightb.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));

							{
								dteDateFrom = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
								pnlPromoDtl_rightb.add(dteDateFrom, BorderLayout.CENTER);						
								dteDateFrom.setDate(null);
								dteDateFrom.setEnabled(false);			
								dteDateFrom.setPreferredSize(new java.awt.Dimension(248, 38));
								dteDateFrom.setDate(null);
							}			
							{
								dteDateTo = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
								pnlPromoDtl_rightb.add(dteDateTo, BorderLayout.CENTER);						
								dteDateTo.setDate(null);
								dteDateTo.setEnabled(false);			
								dteDateTo.setPreferredSize(new java.awt.Dimension(248, 38));
								dteDateTo.setDate(null);
							}			
							{
								lookupAccount = new _JLookup(null, "Account",0,2);
								pnlPromoDtl_rightb.add(lookupAccount);
								lookupAccount.setLookupSQL(getAccount());
								lookupAccount.setReturnColumn(0);
								lookupAccount.setEnabled(false);
								lookupAccount.setFilterName(true);
								lookupAccount.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup)event.getSource()).getDataSet();
										if(data != null){											
											tagAccount.setTag((String) data[1]);	
										}
									}
								});
							}
							{
								String type[] = {"All","TR","OR"};					
								cmbType = new JComboBox(type);
								pnlPromoDtl_rightb.add(cmbType);
								cmbType.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
								cmbType.setBounds(537, 15, 190, 21);	
								cmbType.setEnabled(false);
								cmbType.setEditable(false);
								cmbType.setPreferredSize(new java.awt.Dimension(217, 60));
								cmbType.setSelectedIndex(0);	
							}	
							{
								txtAmount = new _JXFormattedTextField(JXFormattedTextField.CENTER);
								pnlPromoDtl_rightb.add(txtAmount);
								txtAmount.setFormatterFactory(_JXFormattedTextField.DECIMAL);
								txtAmount.setText("0.00");
								txtAmount.setEnabled(false);	
								txtAmount.setEditable(false);	
								txtAmount.setBounds(120, 0, 72, 22);
							}
						}
					}
					{
						pnlPromoDetailsAmt2 = new JPanel(new GridLayout(5, 1, 0, 5));
						pnlPromoDetails_b.add(pnlPromoDetailsAmt2, BorderLayout.CENTER);
						pnlPromoDetailsAmt2.setPreferredSize(new java.awt.Dimension(675, 116));
						pnlPromoDetailsAmt2.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));

						{
							tagDateFrom = new _JTagLabel("[ ]");
							pnlPromoDetailsAmt2.add(tagDateFrom);
							tagDateFrom.setBounds(209, 27, 700, 22);
							tagDateFrom.setEnabled(false);	
							tagDateFrom.setVisible(false);	
							tagDateFrom.setPreferredSize(new java.awt.Dimension(27, 33));
						}
						{
							tagDateTo = new _JTagLabel("[ ]");
							pnlPromoDetailsAmt2.add(tagDateTo);
							tagDateTo.setBounds(209, 27, 700, 22);
							tagDateTo.setEnabled(false);	
							tagDateTo.setVisible(false);	
							tagDateTo.setPreferredSize(new java.awt.Dimension(27, 33));
						}
						{
							tagAccount = new _JTagLabel("[ ]");
							pnlPromoDetailsAmt2.add(tagAccount);
							tagAccount.setBounds(209, 27, 700, 22);
							tagAccount.setEnabled(false);	
							tagAccount.setHorizontalAlignment(JTextField.LEFT);
							tagAccount.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
							tagAccount.setPreferredSize(new java.awt.Dimension(27, 33));
						}
					}
					{
						//Start of Left Panel 
						pnlPromoDetailsAmt = new JPanel(new BorderLayout(0,0));
						pnlPromoDetails_b.add(pnlPromoDetailsAmt, BorderLayout.EAST);
						pnlPromoDetailsAmt.setPreferredSize(new java.awt.Dimension(276, 141));
						pnlPromoDetailsAmt.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));

						{
							pnlPromoDetailsAmt_1 = new JPanel(new GridLayout(5, 1, 5, 5));
							pnlPromoDetailsAmt.add(pnlPromoDetailsAmt_1, BorderLayout.WEST);
							pnlPromoDetailsAmt_1.setPreferredSize(new java.awt.Dimension(97, 113));
							pnlPromoDetailsAmt_1.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

							{

								{
									lblProj = new JLabel("Project", JLabel.TRAILING);
									pnlPromoDetailsAmt_1.add(lblProj);
									lblProj.setEnabled(false);
									lblProj.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
								}
								{
									lblFreq = new JLabel("Frequency", JLabel.TRAILING);
									pnlPromoDetailsAmt_1.add(lblFreq);
									lblFreq.setEnabled(false);	
									lblFreq.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
								}
								{
									lblRelGrp = new JLabel("Release Group", JLabel.TRAILING);
									pnlPromoDetailsAmt_1.add(lblRelGrp);
									lblRelGrp.setEnabled(false);	
									lblRelGrp.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
								}
								{
									lblReleaseTo = new JLabel("Release To", JLabel.TRAILING);
									pnlPromoDetailsAmt_1.add(lblReleaseTo);
									lblReleaseTo.setEnabled(false);	
									lblReleaseTo.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
								}	
								{
									lblStatus = new JLabel("Status", JLabel.TRAILING);
									pnlPromoDetailsAmt_1.add(lblStatus);
									lblStatus.setEnabled(false);
									lblStatus.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
								}
							}

							pnlPromoDetailsAmt_2 = new JPanel(new BorderLayout(5,0));
							pnlPromoDetailsAmt.add(pnlPromoDetailsAmt_2, BorderLayout.CENTER);
							pnlPromoDetailsAmt_2.setPreferredSize(new java.awt.Dimension(265, 98));
							pnlPromoDetailsAmt_2.setBorder(BorderFactory.createEmptyBorder(0,5,0,0));

							{
								{
									pnlPromoDetailsAmt_2a = new JPanel(new GridLayout(5, 1, 0, 5));
									pnlPromoDetailsAmt_2.add(pnlPromoDetailsAmt_2a, BorderLayout.WEST);
									pnlPromoDetailsAmt_2a.setPreferredSize(new java.awt.Dimension(119, 119));
									pnlPromoDetailsAmt_2a.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

									{

										lookupProject = new _JLookup(null, "Project",0,2);
										pnlPromoDetailsAmt_2a.add(lookupProject);
										lookupProject.setLookupSQL(getProject());
										lookupProject.setReturnColumn(0);
										lookupProject.setEnabled(false);
										lookupProject.addLookupListener(new LookupListener() {
											public void lookupPerformed(LookupEvent event) {
												Object[] data = ((_JLookup)event.getSource()).getDataSet();
												if(data != null){

													String name = (String) data[1];						
													tagProject.setTag(name);
													//proj_code   = (String) data[0];	
												}
											}
										});
									}
									{
										String type[] = {"Once","Monthly","Semi-Annual","Yearly"};					
										cmbFreq = new JComboBox(type);
										pnlPromoDetailsAmt_2a.add(cmbFreq) ;
										cmbFreq.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
										cmbFreq.setBounds(537, 15, 190, 21);	
										cmbFreq.setEnabled(false);
										cmbFreq.setEditable(false);
										cmbFreq.setPreferredSize(new java.awt.Dimension(217, 60));
										cmbFreq.setSelectedIndex(0);	
									}	
									{
										String type[] = {"All","In-House","Broker"};					
										cmbReleaseGrp = new JComboBox(type);
										pnlPromoDetailsAmt_2a.add(cmbReleaseGrp);
										cmbReleaseGrp.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
										cmbReleaseGrp.setBounds(537, 15, 190, 21);	
										cmbReleaseGrp.setEnabled(false);
										cmbReleaseGrp.setEditable(false);
										cmbReleaseGrp.setPreferredSize(new java.awt.Dimension(217, 60));
										cmbFreq.setSelectedIndex(0);	
									}					
									{
										String fund_cls[] = {"All","Agents","Coordinator","Overriding","In-House Brokers"};					
										cmbReleaseTo = new JComboBox(fund_cls);
										pnlPromoDetailsAmt_2a.add(cmbReleaseTo);
										cmbReleaseTo.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
										cmbReleaseTo.setBounds(537, 15, 190, 21);	
										cmbReleaseTo.setEnabled(false);
										cmbReleaseTo.setEditable(false);
										cmbReleaseTo.setPreferredSize(new java.awt.Dimension(217, 60));
										cmbReleaseTo.setSelectedIndex(0);	
										cmbReleaseTo.setToolTipText("01-COLLECTING ACCOUNT ; 02-DISBURSING ACCOUNT ; 03-MONEY MARKET ");										
									}	
									{
										String status[] = {"Active","Inactive"};					
										cmbStatus = new JComboBox(status);
										pnlPromoDetailsAmt_2a.add(cmbStatus);
										cmbStatus.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
										cmbStatus.setBounds(537, 15, 190, 21);	
										cmbStatus.setEnabled(false);
										cmbStatus.setEditable(false);
										cmbStatus.setPreferredSize(new java.awt.Dimension(217, 60));
										cmbStatus.setSelectedIndex(0);	
									}
								}
								{
									pnlPromoDetailsAmt_2b = new JPanel(new GridLayout(5, 1, 0, 5));
									pnlPromoDetailsAmt_2.add(pnlPromoDetailsAmt_2b, BorderLayout.EAST);
									pnlPromoDetailsAmt_2b.setPreferredSize(new java.awt.Dimension(52, 136));
									pnlPromoDetailsAmt_2b.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

									{
										tagProject = new _JTagLabel("[ ]");
										pnlPromoDetailsAmt_2b.add(tagProject);
										tagProject.setBounds(209, 27, 700, 22);
										tagProject.setEnabled(false);	
										tagProject.setPreferredSize(new java.awt.Dimension(27, 33));
									}
								}
							}
						}
					}
				}			
			}
		} 
		{
			pnlSouth = new JPanel();
			pnlMain.add(pnlSouth, BorderLayout.SOUTH);
			pnlSouth.setLayout(new BorderLayout());
			pnlSouth.setBorder(BorderFactory.createLineBorder(Color.GRAY));
			pnlSouth.setPreferredSize(new java.awt.Dimension(1014, 33));

			pnlSouthCenterb = new JPanel(new GridLayout(1, 2, 5, 5));
			pnlSouth.add(pnlSouthCenterb, BorderLayout.NORTH);
			pnlSouthCenterb.setPreferredSize(new java.awt.Dimension(921, 31));
			{
				{
					btnAddNew = new JButton("Add New");
					pnlSouthCenterb.add(btnAddNew);
					btnAddNew.setActionCommand("Add");
					btnAddNew.addActionListener(this);
					btnAddNew.setEnabled(true);
				}
				{
					btnEdit = new JButton("Edit");
					pnlSouthCenterb.add(btnEdit);
					btnEdit.setActionCommand("Edit");
					btnEdit.addActionListener(this);
					btnEdit.setEnabled(false);
				}
				{
					btnSave = new JButton("Save");
					pnlSouthCenterb.add(btnSave);
					btnSave.setActionCommand("Save");
					btnSave.addActionListener(this);
					btnSave.setEnabled(false);
				}
				{
					btnCancel = new JButton("Cancel");
					pnlSouthCenterb.add(btnCancel);
					btnCancel.setActionCommand("Cancel");
					btnCancel.addActionListener(this);
					btnCancel.setEnabled(false);
				}
			}
		}
		
		displayPromoList(modelPromo, rowHeaderPromo, modelPromo_total);
	}

	//display tables
	private void displayPromoList(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal) {//

		FncTables.clearTable(modelMain);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		String sql = 			
			"select \r\n" + 
			"a.promo_code, \r\n" + 
			"a.promo_desc, \r\n" + 
			"a.date_from, \r\n" + 
			"a.date_to, \r\n" + 
			"a.new_acct_id, \r\n" + 
			"trim(b.acct_name) as acct_name,\r\n" + 			
			"a.amount, \r\n" + 
			"trim(c.proj_alias) as proj,\r\n" + 
			"(case when a.frequency = 'O' then 'Once' else\r\n" + 
			"	case when a.frequency = 'M' then 'Monthly' else\r\n" + 
			"	case when a.frequency = 'S' then 'Semi-Annual' else\r\n" + 
			"	case when a.frequency = 'Y' then 'Yearly' else '' end end end end) as frequency,\r\n" + 
			"(case when a.promo_type = '' then 'All' else \r\n" + 
			"	case when a.promo_type = 'A' then 'TR' else\r\n" + 
			"	case when a.promo_type = 'B' then 'OR' else '' end end end) as type,\r\n" + 
			"(case when a.release_group = '' then 'All' else \r\n" + 
			"	case when a.release_group = '001' then 'In-House' else\r\n" + 
			"	case when a.release_group = '002' then 'Broker' else '' end end end) as release_group,\r\n" + 
			"(case when a.release_to = '' then 'All' else \r\n" + 
			"	case when a.release_to = 'A' then 'Agents' else\r\n" + 
			"	case when a.release_to = 'C' then 'Coordinator' else  \r\n" + 
			"	case when a.release_to = 'B' then 'In-House Brokers' else  \r\n" + 
			"	case when a.release_to = 'O' then 'Overriding' else '' end end end end end) as release_to,\r\n" + 
			"trim(d.status_desc) as status,\r\n" + 
			"trim(f.entity_name) as created_by,\r\n" + 
			"a.created_date as created_date\r\n" + 
			"\r\n" + 
			"\r\n" + 
			"from cm_promo_bonus a\r\n" + 
			"left join mf_boi_chart_of_accounts b on a.new_acct_id = b.acct_id\r\n" + 
			"left join mf_project c on a.projcode = c.proj_id\r\n" + 
			"left join mf_record_status d on a.status = d.status_id\n" +
			"left join em_employee e on a.created_by = e.emp_code \n" +
			"left join rf_entity f on e.entity_id = f.entity_id \n" +
			
			"order by a.rec_id \n\n" ;

		System.out.printf("SQL #1: %s", sql);
		pgSelect db = new pgSelect();
		db.select(sql);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());				
			}	

			//totalAllComm(modelMain, modelTotal);			
		}		


		else {
			modelPromo_total = new modelPromoDetails();
			modelPromo_total.addRow(new Object[] {  "Total", new BigDecimal(0.00) });

			tblPromo_total = new _JTableTotal(modelPromo_total, tblPromo);
			tblPromo_total.setFont(dialog11Bold);
			scrollPromo_total.setViewportView(tblPromo_total);
			((_JTableTotal) tblPromo_total).setTotalLabel(0);}

		tblPromo.packAll();				
		//tblPromo.getColumnModel().getColumn(0).setPreferredWidth(80);

		int row_tot = tblPromo.getRowCount();			
		modelPromo_total.setValueAt(new BigDecimal(row_tot), 0, 1);

		adjustRowHeight_account(tblPromo);
	}	


	//action performed
	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getActionCommand().equals("Cancel")){cancel();}

		if(e.getActionCommand().equals("Add")){addNew(); }

		if(e.getActionCommand().equals("Edit")){edit(); }

		if(e.getActionCommand().equals("Save")){save();}

	}

	public void mouseClicked(MouseEvent evt) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	private void cancel(){

		if (btnSave.isEnabled()) {

			if (JOptionPane.showConfirmDialog(getContentPane(), "Cancel unsaved data?", "Cancel", 
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
				execute_cancel();
			}

		}

		else {	execute_cancel(); 	}
	}

	private void execute_cancel(){

		enableFields(false);		
		enableButtons(true, true, false, false);
		enableComponents(false);
		refreshFields();
		to_do 	= ""; 
		txtPromoDesc.setEditable(false);
		txtAmount.setEditable(false);	
	}

	private void save(){

		if(checkCompleteDetails()==false)
		{JOptionPane.showMessageDialog(getContentPane(), "Please enter complete promo/incentive details.", "Incomplete Detail", 
				JOptionPane.WARNING_MESSAGE);}

		else if (JOptionPane.showConfirmDialog(getContentPane(), "Are all entries correct?", "Confirmation", 
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

			pgUpdate db = new pgUpdate();	

			if (to_do.equals("addnew")) 
			{
				saveNewPromo(db); 
				db.commit();
				JOptionPane.showMessageDialog(getContentPane(),"A new promo was added.","Information",JOptionPane.INFORMATION_MESSAGE);
				execute_cancel();
				displayPromoList(modelPromo,rowHeaderPromo,modelPromo_total);

			} 

			else if (to_do.equals("edit"))
			{				
				updatePromoDetails(db); 
				db.commit();				
				JOptionPane.showMessageDialog(getContentPane(),"An existing promo was successfully updated.","Information",JOptionPane.INFORMATION_MESSAGE);
				execute_cancel();
				displayPromoList(modelPromo,rowHeaderPromo,modelPromo_total);
			}			
		}

		else {}	



	}

	private void edit(){

		enableFields(true);
		enableButtons(false, false, true, true);
		enableComponents(true);		
		
		txtPromoDesc.setEditable(true);
		txtAmount.setEditable(true);
		lookupPromoCode.setEnabled(false);

		to_do = "edit";
	}

	private void addNew(){

		enableFields(true);	
		enableComponents(true);
		enableButtons(false, false, true, true);
		refreshFields();
		lookupPromoCode.setValue(sql_getNextPromoCode().toString());
		txtPromoDesc.setEditable(true);
		txtAmount.setEditable(true);	

		to_do = "addnew";
	}


	//enable, disable
	private void enableFields(boolean x){

		lblPromoName.setEnabled(x);	
		txtPromoDesc.setEnabled(x);	
		lblDateFr.setEnabled(x);	
		lblDateTo.setEnabled(x);	
		lblAcctID.setEnabled(x);	
		lblType.setEnabled(x);	
		lblAmount.setEnabled(x);	
		lblProj.setEnabled(x);
		tagProject.setEnabled(x);	
		lblFreq.setEnabled(x);	
		lblRelGrp.setEnabled(x);
		lblReleaseTo.setEnabled(x);	
		lblStatus.setEnabled(x);

	}
	
	private void enableComponents(boolean x){//Lookup and Comboboxes

		lookupPromoCode.setEnabled(x);		
		dteDateFrom.setEnabled(x);	
		dteDateTo.setEnabled(x);	
		lookupAccount.setEnabled(x);	
		cmbType.setEnabled(x);	
		txtAmount.setEnabled(x);	
		lookupProject.setEnabled(x);	
		cmbFreq.setEnabled(x);
		cmbReleaseGrp.setEnabled(x);	
		cmbReleaseTo.setEnabled(x);	
		cmbStatus.setEnabled(x);

	}

	private void refreshFields(){

		lookupPromoCode.setText("");		
		dteDateFrom.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
		dteDateTo.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
		lookupAccount.setText("");
		cmbType.setSelectedIndex(0);	
		txtAmount.setValue(0.00);	
		lookupProject.setText("");
		cmbFreq.setSelectedIndex(0);	
		cmbReleaseGrp.setSelectedIndex(0);		
		cmbReleaseTo.setSelectedIndex(0);	
		cmbStatus.setSelectedIndex(0);
		txtPromoDesc.setText("");
		tagAccount.setTag("");
		tagProject.setTag("");
	}

	private void enableButtons( Boolean a, Boolean b, Boolean c, Boolean d){

		btnAddNew.setEnabled(a);
		btnEdit.setEnabled(b);
		btnSave.setEnabled(c);	
		btnCancel.setEnabled(d);			
	}	


	//select, lookup and get statements	
	private String getAccount(){

		String sql = 
			"select acct_id as \"Acct ID\", " +
			"trim(acct_name) as \"Acct Name\" " +
			"from mf_boi_chart_of_accounts " +
			"where acct_id like '08%' and status_id = 'A' " +
			"order by acct_id\r\n" ;
		return sql;

	}	

	private String getProject(){//used
		return 
		"select\r\n" + 
		"proj_id as\"Proj ID\",\r\n" + 
		"trim(proj_alias) as \"Proj Alias\",\r\n" + 
		"trim(proj_name)  as \"Proj Name\" \r\n" + 
		"from mf_project \r\n" ;		
	}

	private static String sql_getProjID(String proj_alias) {

		String prj = "";

		String SQL = 
			"select proj_id from mf_project where trim(proj_alias) = '"+proj_alias+"' and status_id != 'I' \n" ;

		System.out.printf("SQL #1 getCV_no : %s", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);


		if(db.isNotNull()){
			prj = (String) db.getResult()[0][0];
		}else{
			prj = "";
		}

		return prj;
	}
	
	private static Integer sql_getNextPromoNo() {

		Integer nxt_promo_no = 0;

		String SQL = 
				"select max(coalesce(rec_id,0))+1 from cm_promo_bonus  " ;

		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			if((Integer) db.getResult()[0][0]==null||db.getResult()[0][0].equals("null")) {nxt_promo_no = 1;}
			else {nxt_promo_no = (Integer) db.getResult()[0][0]; }

		}else{
			nxt_promo_no = 1;
		}

		return nxt_promo_no;
	}
	
	private static Integer sql_getNextPromoCode() {

		Integer nxt_promo_no = 0;

		String SQL = 
				"select max(coalesce(promo_code::int,0))+1 from cm_promo_bonus  " ;

		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			if((Integer) db.getResult()[0][0]==null||db.getResult()[0][0].equals("null")) {nxt_promo_no = 1;}
			else {nxt_promo_no = (Integer) db.getResult()[0][0]; }

		}else{
			nxt_promo_no = 1;
		}

		return nxt_promo_no;
	}
	

	//table operations	
	private void adjustRowHeight_account(_JTableMain table){//
		int rw = table.getModel().getRowCount();
		int x = 0;

		while (x<rw){			
			table.setRowHeight(x, 22);			
			x++;
		}

	}

	private void displayRowDetails(){
		
		enableComponents(false);

		Integer row = tblPromo.getSelectedRow();

		String promo_id 	= "";	
		String promo_desc 	= "";	
		Date date_from 		= null;
		Date date_to 		= null;
		String acct_id 		= "";	
		String acct_desc 	= "";	
		Double amount     	= null;	
		String proj_alias  	= "";	
		String type_code  	= "";	
		String freq_code  	= "";
		String rel_group  	= "";
		String rel_to  		= "";
		String status_desc	= "";		

		try { promo_id 		= tblPromo.getValueAt(row,0).toString().trim();} catch (NullPointerException e) { promo_id 	= ""; }
		try { promo_desc 	= tblPromo.getValueAt(row,1).toString().trim();} catch (NullPointerException e) { promo_desc 	= ""; }
		try { date_from 	= (Date)tblPromo.getValueAt(row,2);} catch (NullPointerException e) { date_from = null; }
		try { date_to 		= (Date)tblPromo.getValueAt(row,3);} catch (NullPointerException e) { date_to = null; }
		try { acct_id 		= tblPromo.getValueAt(row,4).toString().trim();} catch (NullPointerException e) { acct_id 	= ""; }
		try { acct_desc 	= tblPromo.getValueAt(row,5).toString().trim();} catch (NullPointerException e) { acct_desc 	= ""; }
		try { amount    	= Double.parseDouble(tblPromo.getValueAt(row,6).toString().trim());} catch (NullPointerException e) { amount = 0.00; }
		try { proj_alias    = tblPromo.getValueAt(row,7).toString().trim();} catch (NullPointerException e) { proj_alias = ""; }
		try { freq_code     = tblPromo.getValueAt(row,8).toString().trim();} catch (NullPointerException e) { freq_code = ""; }
		try { type_code     = tblPromo.getValueAt(row,9).toString().trim();} catch (NullPointerException e) { type_code = ""; }
		try { rel_group     = tblPromo.getValueAt(row,10).toString().trim();} catch (NullPointerException e) { rel_group = ""; }
		try { rel_to    	= tblPromo.getValueAt(row,11).toString().trim();} catch (NullPointerException e) { rel_to = ""; }
		try { status_desc   = tblPromo.getValueAt(row,12).toString().trim();} catch (NullPointerException e) { status_desc = ""; }

		lookupPromoCode.setText(promo_id);
		txtPromoDesc.setText(promo_desc);
		dteDateFrom.setDate(date_from);
		dteDateTo.setDate(date_to);
		lookupAccount.setText(acct_id);
		tagAccount.setTag(acct_desc);
		lookupProject.setText(sql_getProjID(proj_alias));
		txtAmount.setText(nf.format(amount));
		tagProject.setTag(proj_alias);
		
		//promo type
		if (type_code.equals("All")){cmbType.setSelectedIndex(0);} 
		else if (type_code.equals("TR")){cmbType.setSelectedIndex(1);} 
		else if (type_code.equals("OR")){cmbType.setSelectedIndex(2);} 
		
		//promo frequency
		if (freq_code.equals("Once")){cmbFreq.setSelectedIndex(0);} 
		else if (freq_code.equals("Monthly")){cmbFreq.setSelectedIndex(1);} 
		else if (freq_code.equals("Semi-Annual")){cmbFreq.setSelectedIndex(2);} 
		else if (freq_code.equals("Yearly")){cmbFreq.setSelectedIndex(3);} 
		
		//release group
		if (rel_group.equals("All")){cmbReleaseGrp.setSelectedIndex(0);} 
		else if (rel_group.equals("In-House")){cmbReleaseGrp.setSelectedIndex(1);} 
		else if (rel_group.equals("Broker")){cmbReleaseGrp.setSelectedIndex(2);} 
		
		//release to
		if (rel_to.equals("All")){cmbReleaseTo.setSelectedIndex(0);} 
		else if (rel_to.equals("Agents")){cmbReleaseTo.setSelectedIndex(1);} 
		else if (rel_to.equals("Coordinator")){cmbReleaseTo.setSelectedIndex(2);} 
		else if (rel_to.equals("Overriding")){cmbReleaseTo.setSelectedIndex(3);} 
		else if (rel_to.equals("In-House Brokers")){cmbReleaseTo.setSelectedIndex(4);} 
		
		//status
		if (status_desc.equals("ACTIVE")){cmbStatus.setSelectedIndex(0);} 
		else if (status_desc.equals("INACTIVE")){cmbStatus.setSelectedIndex(1);} 

	}

	private void clickTable(){		
		displayRowDetails();
		enableFields(true);
		enableButtons(true, true, false, true);
	}


	//processes and validation
	private Boolean checkCompleteDetails(){

		boolean x = false;		

		String promo_desc, acct;

		promo_desc 	= txtPromoDesc.getText();
		acct 		= lookupAccount.getText();

		if (promo_desc.equals("") || acct.equals("")) {x=false;} 
		else {x=true;}		

		return x;

	}


	//save
	private void saveNewPromo(pgUpdate db){

		String freq_code = "";
		String status_id = "";
		String release_to = "";
		String release_grp = "";
		String promo_type = "";
		
		//promo frequency
		if (cmbFreq.getSelectedIndex()==0) {freq_code="O";}
		else if (cmbFreq.getSelectedIndex()==1) {freq_code="M";}
		else if (cmbFreq.getSelectedIndex()==2) {freq_code="S";}
		else if (cmbFreq.getSelectedIndex()==3) {freq_code="Y";}
		
		//status
		if (cmbStatus.getSelectedIndex()==0) {status_id="A";}
		else if (cmbStatus.getSelectedIndex()==1) {status_id="I";}
		
		//release to
		if (cmbReleaseTo.getSelectedIndex()==0) {release_to="";}
		else if (cmbReleaseTo.getSelectedIndex()==1) {release_to="A";}
		else if (cmbReleaseTo.getSelectedIndex()==2) {release_to="C";}
		else if (cmbReleaseTo.getSelectedIndex()==3) {release_to="O";}
		else if (cmbReleaseTo.getSelectedIndex()==4) {release_to="B";}
		
		//release group
		if (cmbReleaseGrp.getSelectedIndex()==0) {release_grp="";}
		else if (cmbReleaseGrp.getSelectedIndex()==1) {release_grp="001";}
		else if (cmbReleaseGrp.getSelectedIndex()==2) {release_grp="002";}
		
		//promo type
		if (cmbType.getSelectedIndex()==0) {promo_type="";}
		else if (cmbType.getSelectedIndex()==1) {promo_type="A";}
		else if (cmbType.getSelectedIndex()==2) {promo_type="B";}
		
		String sqlDetail = 
			"insert into cm_promo_bonus \n" +
			"values ( \n" +
			"'"+sql_getNextPromoNo()+"',   \n" +   				//1 rec_id
			"'"+lookupPromoCode.getText().trim()+"',   \n" +	//2 promo_code
			"'"+txtPromoDesc.getText().trim()+"',   \n" +		//3 promo desc			
			"'',   \n" +										//4 promo class
			"'"+dateFormat.format(dteDateFrom.getDate())+"',  \n" +		//5 date_from
			"'"+dateFormat.format(dteDateTo.getDate())+"',  \n" +		//6 date_to
			"'"+freq_code+"',   \n" +							//7 frequency
			"'',   \n" +										//8 mode
			"'"+Double.valueOf(txtAmount.getText().trim().replace(",","")).doubleValue()+"',   \n" + //9 amount
			"'',   \n" +										//10 sp_name
			"'',   \n" +										//11 isu_acctid
			"'',   \n" +										//12 esu_acctid
			"'"+status_id+"',   \n" +							//13 status
			"'"+release_to+"',   \n" +							//14 release_to
			"'"+release_grp+"',   \n" +							//15 release_group
			"false,   \n" +										//16 view_on
			"'"+promo_type+"',   \n" +							//17 promo_type
			"null,   \n" +										//18 promo_title
			"null,   \n" +										//19 part_of_comm
			"'"+lookupProject.getText()+"',   \n" +				//20 projcode
			"null,   \n" +										//21 releaseto_salespos
			"null,   \n" +										//22 pre_op_acct_id
			"'"+lookupAccount.getText()+"',   \n" +				//23 new_acct_id
			"'"+UserInfo.EmployeeCode+"',   \n" +				//24 created_by
			"now()   \n" +										//25 created_date			
		") \n\n" ;

		System.out.printf("SQL #1 - New Promo: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);	

	}

	private void updatePromoDetails(pgUpdate db){

		String freq_code = "";
		String status_id = "";
		String release_to = "";
		String release_grp = "";
		String promo_type = "";
		
		//promo frequency
		if (cmbFreq.getSelectedIndex()==0) {freq_code="O";}
		else if (cmbFreq.getSelectedIndex()==1) {freq_code="M";}
		else if (cmbFreq.getSelectedIndex()==2) {freq_code="S";}
		else if (cmbFreq.getSelectedIndex()==3) {freq_code="Y";}
		
		//status
		if (cmbStatus.getSelectedIndex()==0) {status_id="A";}
		else if (cmbStatus.getSelectedIndex()==1) {status_id="I";}
		
		//release to
		if (cmbReleaseTo.getSelectedIndex()==0) {release_to="";}
		else if (cmbReleaseTo.getSelectedIndex()==1) {release_to="A";}
		else if (cmbReleaseTo.getSelectedIndex()==2) {release_to="C";}
		else if (cmbReleaseTo.getSelectedIndex()==3) {release_to="O";}
		else if (cmbReleaseTo.getSelectedIndex()==4) {release_to="B";}
		
		//release group
		if (cmbReleaseGrp.getSelectedIndex()==0) {release_grp="";}
		else if (cmbReleaseGrp.getSelectedIndex()==1) {release_grp="001";}
		else if (cmbReleaseGrp.getSelectedIndex()==2) {release_grp="002";}
		
		//promo type
		if (cmbType.getSelectedIndex()==0) {promo_type="";}
		else if (cmbType.getSelectedIndex()==1) {promo_type="A";}
		else if (cmbType.getSelectedIndex()==2) {promo_type="B";}
		
		String sqlDetail = 
			"update cm_promo_bonus \n" +
			"set \n" +
			"promo_desc = '"+txtPromoDesc.getText().trim()+"',   \n" +	//3 promo desc	
			"date_from = '"+dateFormat.format(dteDateFrom.getDate())+"',  \n" +	//5 date_from
			"date_to = '"+dateFormat.format(dteDateTo.getDate())+"',  \n" +		//6 date_to
			"frequency = '"+freq_code+"',   \n" +								//7 frequency
			"amount = '"+Double.valueOf(txtAmount.getText().trim().replace(",","")).doubleValue()+"',   \n" + //9 amount
			"status = '"+status_id+"',   \n" +						//13 status
			"release_to = '"+release_to+"',   \n" +					//14 release_to
			"release_group = '"+release_grp+"',   \n" +				//15 release_group
			"promo_type = '"+promo_type+"',   \n" +					//17 promo_type
			"projcode = '"+lookupProject.getText()+"',   \n" +		//20 projcode
			"new_acct_id = '"+lookupAccount.getText()+"'   \n" +	//23 new_acct_id		
			
			"where promo_code = '"+lookupPromoCode.getText().trim()+"'  \n\n";


		System.out.printf("SQL #1 - updatePromoDetails: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);	

	}






}
