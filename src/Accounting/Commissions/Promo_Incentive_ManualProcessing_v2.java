package Accounting.Commissions;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXFormattedTextField;

import tablemodel.modelClientList;
import DateChooser._JDateChooser;
import FormattedTextField._JXFormattedTextField;
import Functions.FncAcounting;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;

import com.toedter.calendar.JTextFieldDateEditor;

import Database.pgSelect;
import Database.pgUpdate;
import components._JInternalFrame;
import components._JScrollPaneMain;
import components._JTableMain;
import components._JTagLabel;

public class Promo_Incentive_ManualProcessing_v2 extends _JInternalFrame implements _GUI, ActionListener, MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5887172858052178694L;
	
	static String title = "Promo / Incentive Manual Entry";
	static Dimension SIZE = new Dimension(1000, 600);

	private JPanel pnlMain;
	private JPanel pnlNorth;
	private JPanel pnlSouth;
	private JPanel pnlComp;
	private JPanel pnlComp_a;
	private JPanel pnlComp_a1;
	private JPanel pnlComp_a2;
	private JPanel pnlComp_b2a;
	private JPanel pnlChangeOrder_a1_sub1;
	private JPanel pnlCenter;
	private JPanel pnlComp_a3;
	private JPanel pnlComp_date_lbl;
	private JPanel pnlComp_date;
	private JPanel pnlPromoDetails;
	private JPanel pnlPromoDetails_left;
	private JPanel pnlClient_label;
	private JPanel pnlClient_label_sub1;
	private JPanel pnlClient_label_sub2;
	private JPanel pnlClient_label_sub2a;
	private JPanel pnlClient_label_sub2b;
	private JPanel pnlClientProjUnit;
	private JPanel pnlClientProject;
	private JPanel pnlClientProjLbl;	
	private JPanel pnlClientProjAlias;
	private JPanel pnlClientUnit;
	private JPanel pnlClientUnitLbl;
	private JPanel pnlClientUnitDesc;
	private JPanel pnlClientList;
	private JPanel pnlClientList_rbtn;
	private JPanel pnlClientListTable;
	private JPanel pnlPromoAmount;
	private JPanel pnlGross;
	private JPanel pnlGrossLabel;
	private JPanel pnlGrossText;
	private JPanel pnlWtax;
	private JPanel pnlWtaxLabel;
	private JPanel pnlWtaxText;
	private JPanel pnlNetAmount;
	private JPanel pnlNetLabel;
	private JPanel pnlNetAmtText;
	private JPanel pnlPromoDetailsRight;
	private JPanel pnlAgent_OtherPayee;
	private JPanel pnlAgent;
	private JPanel pnlAgentLabel;
	private JPanel pnlAgentName;
	private JPanel pnlAgentNameLkp;
	private JPanel pnlAgentTag;
	private JPanel pnlOtherPayee;
	private JPanel pnlOtherPayeeName;
	private JPanel pnlOtherPayeeRbtn;
	private JPanel pnlOtherPayeeLkp;
	private JPanel pnlOtherPayeeLkp_a;
	private JPanel pnlOtherPayeeLkp_b;
	private JPanel pnlOtherPayeeProj;
	private JPanel pnlOtherPayeeProjLbl;
	private JPanel pnlOtherPayeeProjTag;
	private JPanel pnlPromoCode_Prodn;
	private JPanel pnlPromoCode;
	private JPanel pnlPromoCodeLbl;
	private JPanel pnlPromoCodeLkp;
	private JPanel pnlPromoCodeLkp_a;
	private JPanel pnlPromoCodeLkp_b;
	private JPanel pnlProduction;
	private JPanel pnlProd_Period;	
	private JPanel pnlSupportFund;
	private JPanel pnlProdTarget;
	private JPanel pnlProdTargetLbl;
	private JPanel pnlProdTargetText;
	private JPanel pnProdTarget_extra;
	private JPanel pnlRemarks;
	private JPanel pnlInKind;
	private JPanel pnlInKindRbtn;
	private JPanel pnlInKindLbl;
	private JPanel pnlInKindLkp;
	private JPanel pnlInKindLkp_a;
	private JPanel pnlInKindLkp_b;
	private JPanel pnlInKind_table;	
	private JPanel pnlOtherPayeeLkp_d;
	private JPanel pnlCDF_no;
	private JPanel pnlCDF_no_a;	
	private JPanel pnlCDF_no_b;
	private JPanel pnlCDF_no_b1;
	private JPanel pnlCDF_no_b2;
	private JPanel pnlCDF_no_b3;
	private JPanel pnlPayee;	

	private JLabel lblGrossAmt;
	private JLabel lblClientProject;
	private JLabel lblProdTarget;
	private JLabel lblCompany;
	private JLabel lblClientUnit;	
	private JLabel lblWtax;	
	private JLabel lblNetAmount;	
	private JLabel lblOtherPayeeProj;	
	private JLabel lblPromoCode;
	private JLabel lblTranDate;	
	private JLabel lblProdDateFrom;	
	private JLabel lblProdDateTo;
	private JLabel lblSuppDateFrom;
	private JLabel lblSuppDateTo;
	private JLabel lblCDF_no;

	private _JScrollPaneMain scrollClientList;
	private _JScrollPaneMain scrollInKind;

	private tablemodel.modelClientList modelClientList;
	private tablemodel.modelClientList modelInKind;	

	private _JTableMain tblCO;
	private _JTableMain tblClientList;
	private _JTableMain tblInKind;

	private JList rowHeaderClientList;	
	private JList rowHeaderInKind;

	private _JLookup lookupCompany;
	private _JLookup lookupAgent;	
	private _JLookup lookupOtherPayee;	
	private _JLookup lookupPromoCode;	
	private _JLookup lookupInKind;	
	private _JLookup lookupClient;
	private _JLookup lookupAgentProject;
	private _JLookup lookupCDF;	

	private _JTagLabel tagCompany;
	private _JTagLabel tagClientName;	
	private _JTagLabel tagProjAlias;	
	private _JTagLabel tagClientUnitDesc;	
	private _JTagLabel tagAgent;	
	private _JTagLabel tagOtherPayee;		
	private _JTagLabel tagPromoCode;	
	private _JTagLabel tagInKind;	
	private _JTagLabel tagProj;	
	private _JTagLabel tagCDF;

	private JButton btnSave;
	private JButton btnCancel;
	private JButton btnAddNew;
	private JButton btnEdit;
	private JButton btnUntag;

	private JRadioButton rbtnClient;
	private JRadioButton rbtnClientList;	
	private JRadioButton rbtnAgent;	
	private JRadioButton rbtnOtherPayee;	

	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);

	private _JDateChooser dteSuppDateFrom;	
	private _JDateChooser dateSuppDateTo;
	private _JDateChooser dteTransaction;
	private _JDateChooser dteProdDateFrom;
	private _JDateChooser dateProdDateTo;	

	private _JXFormattedTextField txtGrossAmount;	
	private _JXFormattedTextField txtWtax;		
	private _JXFormattedTextField txtNetAmount;	
	private _JXFormattedTextField txtProdTarget;		

	private JScrollPane scpPromoRemark;
	private JTextArea txtPromoRemark;
	private JCheckBox cBtnInKind;	

	static NumberFormat nf = new DecimalFormat("###,###,###,##0.00"); 	
	protected static DecimalFormat df = new DecimalFormat("#,##0.00");
	protected static Font dialog11Bold = new Font("DIALOG", Font.BOLD, 11);
	SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

	String co_id = "";
	String proj_code 	= "";
	String payee_id 	= "";
	String entity_id 	= "";
	String pbl_id 		= "";
	String seq_no 		= "";
	String promo_type	= "";
	String release_to	= "";
	Integer rec_id		= null;	
	String to_do		= "addnew";
	Boolean for_disbproc = true;	
	String acct_id		= "";
	String phase_id		= "";
	String phase_no		= "";
	private _JLookup lookupPhase;
	private JLabel lblOtherPayeePhase;
	private _JTagLabel tagPhase;

	public Promo_Incentive_ManualProcessing_v2() {
		super(title, true, true, true, true);
		initGUI();
	}

	public Promo_Incentive_ManualProcessing_v2(String title) {
		super(title);
		// TODO Auto-generated constructor stub
	}

	public Promo_Incentive_ManualProcessing_v2(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
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
		this.setPreferredSize(new java.awt.Dimension(960, 533));
		this.setBounds(0, 0, 960, 533);

		pnlMain = new JPanel();
		getContentPane().add(pnlMain, BorderLayout.CENTER);
		pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		pnlMain.setLayout(new BorderLayout(5, 5));
		pnlMain.setPreferredSize(new java.awt.Dimension(933, 489));

		{
			pnlNorth = new JPanel();
			pnlMain.add(pnlNorth, BorderLayout.NORTH);
			pnlNorth.setLayout(new BorderLayout(5, 5));
			pnlNorth.setBorder(lineBorder);
			pnlNorth.setPreferredSize(new java.awt.Dimension(948, 371));			

			{
				pnlComp = new JPanel(new BorderLayout(0, 0));
				pnlNorth.add(pnlComp, BorderLayout.NORTH);			
				pnlComp.setPreferredSize(new java.awt.Dimension(995, 58));	

				{
					//company
					pnlComp_a = new JPanel(new BorderLayout(5, 5));
					pnlComp.add(pnlComp_a, BorderLayout.NORTH);	
					pnlComp_a.setPreferredSize(new java.awt.Dimension(995, 31));	
					pnlComp_a.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));			

					{
						pnlComp_a1 = new JPanel(new GridLayout(1, 2, 5, 5));
						pnlComp_a.add(pnlComp_a1, BorderLayout.WEST);	
						pnlComp_a1.setPreferredSize(new java.awt.Dimension(207, 30));
						pnlComp_a1.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));

						{
							lblCompany = new JLabel("        COMPANY", JLabel.TRAILING);
							pnlComp_a1.add(lblCompany);
							lblCompany.setBounds(8, 11, 62, 12);
							lblCompany.setPreferredSize(new java.awt.Dimension(115, 25));
							lblCompany.setFont(new java.awt.Font("Segoe UI",Font.BOLD,10));
						}
						{
							lookupCompany = new _JLookup(null, "Company",0,2);
							pnlComp_a1.add(lookupCompany);
							lookupCompany.setLookupSQL(SQL_COMPANY());
							lookupCompany.setReturnColumn(0);
							lookupCompany.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent event) {
									Object[] data = ((_JLookup)event.getSource()).getDataSet();
									if(data != null){

										co_id 		= (String) data[0];	
										String name = (String) data[1];						
										tagCompany.setTag(name);

										lblCDF_no.setEnabled(true);	
										lookupCDF.setEnabled(true);	

										KEYBOARD_MANAGER.focusNextComponent();
										enableButtons(true, false, false, false, false);

									}
								}
							});
						}	
					}
					{
						pnlComp_a2 = new JPanel(new GridLayout(1, 1, 5, 5));
						pnlComp_a.add(pnlComp_a2, BorderLayout.CENTER);	
						pnlComp_a2.setPreferredSize(new java.awt.Dimension(423, 20));	
						pnlComp_a2.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

						{
							tagCompany = new _JTagLabel("[ ]");
							pnlComp_a2.add(tagCompany);
							tagCompany.setBounds(209, 27, 700, 22);
							tagCompany.setEnabled(true);	
							tagCompany.setPreferredSize(new java.awt.Dimension(27, 33));
						}	
					}
					{
						pnlComp_a3 = new JPanel(new BorderLayout(0, 0));
						pnlComp_a.add(pnlComp_a3, BorderLayout.EAST);	
						pnlComp_a3.setPreferredSize(new java.awt.Dimension(284, 30));	
						pnlComp_a3.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));

						{
							pnlComp_date_lbl = new JPanel(new BorderLayout(0, 0));
							pnlComp_a3.add(pnlComp_date_lbl, BorderLayout.WEST);	
							pnlComp_date_lbl.setPreferredSize(new java.awt.Dimension(114, 30));
							pnlComp_date_lbl.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));


						}
						{
							pnlComp_date = new JPanel(new BorderLayout(0,0));
							pnlComp_a3.add(pnlComp_date, BorderLayout.CENTER);	
							pnlComp_date.setPreferredSize(new java.awt.Dimension(160, 20));	
							pnlComp_date.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));

							{
								pnlComp_b2a = new JPanel(new GridLayout(1, 1, 0, 0));
								pnlComp_date.add(pnlComp_b2a, BorderLayout.WEST);	
								pnlComp_b2a.setPreferredSize(new java.awt.Dimension(143, 27));	
								pnlComp_b2a.setBorder(BorderFactory.createEmptyBorder(5,0,0,0));
							}
						}
					}
				}


				{
					pnlCDF_no = new JPanel(new BorderLayout(5, 5));
					pnlComp.add(pnlCDF_no, BorderLayout.SOUTH);	
					pnlCDF_no.setPreferredSize(new java.awt.Dimension(995, 29));					

					pnlCDF_no_a = new JPanel(new GridLayout(1, 1, 5, 10));
					pnlCDF_no.add(pnlCDF_no_a, BorderLayout.WEST);	
					pnlCDF_no_a.setPreferredSize(new java.awt.Dimension(95, 40));	
					pnlCDF_no_a.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));

					{
						lblCDF_no = new JLabel("Record No.", JLabel.TRAILING);
						pnlCDF_no_a.add(lblCDF_no);
						lblCDF_no.setEnabled(false);	
						lblCDF_no.setPreferredSize(new java.awt.Dimension(100, 40));
						lblCDF_no.setFont(new java.awt.Font("Segoe UI",Font.BOLD,10));
						lblCDF_no.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
					}						

					pnlCDF_no_b = new JPanel(new BorderLayout(5, 5));
					pnlCDF_no.add(pnlCDF_no_b, BorderLayout.CENTER);	
					pnlCDF_no_b.setPreferredSize(new java.awt.Dimension(814, 40));	
					pnlCDF_no_b.setBorder(BorderFactory.createEmptyBorder(4, 5, 4, 5));

					pnlCDF_no_b1 = new JPanel(new GridLayout(1, 1, 5, 10));
					pnlCDF_no_b.add(pnlCDF_no_b1, BorderLayout.WEST);	
					pnlCDF_no_b1.setPreferredSize(new java.awt.Dimension(147, 24));					

					{
						lookupCDF = new _JLookup(null, "PV No.", 2, 2);
						pnlCDF_no_b1.add(lookupCDF);
						lookupCDF.setBounds(20, 27, 20, 25);
						lookupCDF.setLookupSQL(getPromo_list());
						lookupCDF.setReturnColumn(0);
						lookupCDF.setEnabled(false);
						lookupCDF.setPreferredSize(new java.awt.Dimension(135, 24));
						lookupCDF.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){	

									refresh_fields();									

									Date tran_date = (Date) data[2];						
									dteTransaction.setDate(tran_date);	
									String cdf_no = "";
									String rec_id = data[0].toString();
									lookupCDF.setValue(rec_id);
									if(data[1]==null){
										enableButtons(false, true, false, true, true);
										cdf_no = "";
										tagCDF.setTag("CDF No. : null");
									}else{
										enableButtons(false, false, false, true, false);
										cdf_no = data[1].toString();
										tagCDF.setTag("CDF No. : "+cdf_no);
									}

									setPromoHeader(rec_id);
									setSingleAccountCode(rec_id);
									enable_leftPanel(true);
									enable_rightPanel(true);
									enable_lookup_datechoosers(false);

								}
							}
						});
					}	

					pnlCDF_no_b2 = new JPanel(new GridLayout(1, 2, 5, 0));
					pnlCDF_no_b.add(pnlCDF_no_b2, BorderLayout.CENTER);	
					pnlCDF_no_b2.setPreferredSize(new java.awt.Dimension(357, 25));	

					{
						tagCDF = new _JTagLabel("[ ]");
						pnlCDF_no_b2.add(tagCDF);
						tagCDF.setBounds(209, 27, 700, 22);
						tagCDF.setEnabled(true);	
						tagCDF.setPreferredSize(new java.awt.Dimension(27, 33));
					}	

					pnlCDF_no_b3 = new JPanel(new GridLayout(1, 2,5,0));
					pnlCDF_no_b.add(pnlCDF_no_b3, BorderLayout.EAST);	
					pnlCDF_no_b3.setPreferredSize(new java.awt.Dimension(310, 20));	
					pnlCDF_no_b3.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));			

					{
						lblTranDate = new JLabel("Transaction Date  ", JLabel.TRAILING);
						pnlCDF_no_b3.add(lblTranDate);
						lblTranDate.setBounds(8, 11, 62, 12);
						lblTranDate.setEnabled(true);
						lblTranDate.setPreferredSize(new java.awt.Dimension(96, 26));
						lblTranDate.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
					}
					{
						dteTransaction = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
						pnlCDF_no_b3.add(dteTransaction);
						dteTransaction.setBounds(485, 7, 125, 21);
						dteTransaction.setDate(Calendar.getInstance().getTime());
						dteTransaction.setEnabled(true);
						dteTransaction.setDateFormatString("yyyy-MM-dd");
						((JTextFieldDateEditor)dteTransaction.getDateEditor()).setEditable(false);
					}	
				}								
			}
			{
				pnlPromoDetails = new JPanel(new BorderLayout(0,0));
				pnlNorth.add(pnlPromoDetails, BorderLayout.CENTER);	
				pnlPromoDetails.setPreferredSize(new java.awt.Dimension(946, 300));
				pnlPromoDetails.setBorder(BorderFactory.createEmptyBorder(0, 0,0,0));
				pnlPromoDetails.setBorder(lineBorder);
				
				//start of LEFT panel
				{
					pnlPromoDetailsRight = new JPanel(new BorderLayout(0,0));
					pnlPromoDetails.add(pnlPromoDetailsRight, BorderLayout.WEST);	
					pnlPromoDetailsRight.setPreferredSize(new java.awt.Dimension(455, 158));
					pnlPromoDetailsRight.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
					pnlPromoDetailsRight.setBorder(lineBorder);

					{

						pnlAgent_OtherPayee = new JPanel(new BorderLayout(0,0));
						pnlPromoDetailsRight.add(pnlAgent_OtherPayee, BorderLayout.NORTH);	
						pnlAgent_OtherPayee.setPreferredSize(new java.awt.Dimension(453, 126));
						pnlAgent_OtherPayee.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
						pnlAgent_OtherPayee.setBorder(lineBorder);						

						{

							pnlPayee = new JPanel(new BorderLayout(0, 0));
							pnlAgent_OtherPayee.add(pnlPayee, BorderLayout.NORTH);	
							pnlPayee.setPreferredSize(new java.awt.Dimension(451, 62));
							pnlPayee.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
							pnlPayee.setBorder(lineBorder);

							{

								pnlAgent = new JPanel(new BorderLayout(0, 0));
								pnlPayee.add(pnlAgent, BorderLayout.NORTH);	
								pnlAgent.setPreferredSize(new java.awt.Dimension(449, 27));
								pnlAgent.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
								//pnlAgent.setBorder(lineBorder);

								{
									pnlAgentLabel = new JPanel(new BorderLayout(5, 5));
									pnlAgent.add(pnlAgentLabel, BorderLayout.WEST);	
									pnlAgentLabel.setPreferredSize(new java.awt.Dimension(97, 33));
									pnlAgentLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 5));

									{
										rbtnAgent = new JRadioButton();
										pnlAgentLabel.add(rbtnAgent);
										rbtnAgent.setText("* Agent");
										rbtnAgent.setBounds(277, 12, 77, 18);
										rbtnAgent.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
										rbtnAgent.setSelected(true);
										rbtnAgent.setEnabled(false);
										rbtnAgent.setPreferredSize(new java.awt.Dimension(78, 23));
										rbtnAgent.addActionListener(new ActionListener() {
											public void actionPerformed(ActionEvent ae){	

												rbtnOtherPayee.setSelected(false);
												lookupOtherPayee.setEnabled(false);
												lookupAgent.setEnabled(true);
												txtGrossAmount.setText("0.00");
												txtWtax.setText("0.00");
												txtNetAmount.setText("0.00");
												lookupOtherPayee.setValue("");
												tagOtherPayee.setText("[ ]");
												
												tagOtherPayee.setEnabled(false);	
												tagAgent.setEnabled(true);	

											}});					
									}
								}
								{
									pnlAgentName = new JPanel(new BorderLayout(5, 5));
									pnlAgent.add(pnlAgentName, BorderLayout.CENTER);	
									pnlAgentName.setPreferredSize(new java.awt.Dimension(104, 35));
									pnlAgentName.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));

									{
										pnlAgentNameLkp = new JPanel(new BorderLayout(5, 0));
										pnlAgentName.add(pnlAgentNameLkp, BorderLayout.WEST);	
										pnlAgentNameLkp.setPreferredSize(new java.awt.Dimension(101, 33));
										pnlAgentNameLkp.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 0));

										{

											lookupAgent = new _JLookup(null, "Agent",0,2);
											pnlAgentNameLkp.add(lookupAgent);
											lookupAgent.setLookupSQL(getAgent());
											lookupAgent.setEnabled(false);
											lookupAgent.setReturnColumn(0);
											lookupAgent.setFilterName(true);
											lookupAgent.addLookupListener(new LookupListener() {
												public void lookupPerformed(LookupEvent event) {
													Object[] data = ((_JLookup)event.getSource()).getDataSet();
													if(data != null){

														payee_id = data[0].toString();
														tagAgent.setTag("");
														String agent_name = (String) data[1];						
														tagAgent.setTag(agent_name);
														enable_leftPanel(true);														
													}
												}
											});

										}
									}
									{

										pnlAgentTag = new JPanel(new BorderLayout(0, 0));
										pnlAgentName.add(pnlAgentTag, BorderLayout.CENTER);	
										pnlAgentTag.setPreferredSize(new java.awt.Dimension(249, 35));
										pnlAgentTag.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));

										{
											tagAgent = new _JTagLabel("[ ]");
											pnlAgentTag.add(tagAgent);
											tagAgent.setBounds(209, 27, 700, 22);
											tagAgent.setEnabled(false);	
											tagAgent.setPreferredSize(new java.awt.Dimension(233, 33));
										}	
									}
								}
							}
							{
								pnlOtherPayeeName = new JPanel(new BorderLayout(0, 0));
								pnlPayee.add(pnlOtherPayeeName, BorderLayout.CENTER);	
								pnlOtherPayeeName.setPreferredSize(new java.awt.Dimension(449, 35));
								//pnlOtherPayeeName.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

								{

									pnlOtherPayeeRbtn = new JPanel(new BorderLayout(5, 5));
									pnlOtherPayeeName.add(pnlOtherPayeeRbtn, BorderLayout.WEST);	
									pnlOtherPayeeRbtn.setPreferredSize(new java.awt.Dimension(98, 36));
									pnlOtherPayeeRbtn.setBorder(BorderFactory.createEmptyBorder(0,5,0,0));

									{
										rbtnOtherPayee = new JRadioButton();
										pnlOtherPayeeRbtn.add(rbtnOtherPayee);
										rbtnOtherPayee.setText("Other Payee");
										rbtnOtherPayee.setBounds(277, 12, 77, 18);
										rbtnOtherPayee.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
										rbtnOtherPayee.setSelected(false);
										rbtnOtherPayee.setEnabled(false);
										rbtnOtherPayee.setPreferredSize(new java.awt.Dimension(90, 36));
										rbtnOtherPayee.addActionListener(new ActionListener() {
											public void actionPerformed(ActionEvent ae){													
												rbtnAgent.setSelected(false);
												lookupOtherPayee.setEnabled(true);
												lookupAgent.setEnabled(false);
												txtGrossAmount.setText("0.00");
												txtWtax.setText("0.00");
												txtNetAmount.setText("0.00");
												lookupOtherPayee.setValue("");
												lookupAgent.setValue("");
												tagAgent.setText("[ ]");
												tagOtherPayee.setEnabled(true);	
												tagAgent.setEnabled(false);	
											}});					
									}
								}
								{
									pnlOtherPayeeLkp= new JPanel(new BorderLayout(5, 5));
									pnlOtherPayeeName.add(pnlOtherPayeeLkp, BorderLayout.CENTER);	
									pnlOtherPayeeLkp.setPreferredSize(new java.awt.Dimension(104, 35));
									pnlOtherPayeeLkp.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));

									{
										pnlOtherPayeeLkp_a = new JPanel(new BorderLayout(5, 5));
										pnlOtherPayeeLkp.add(pnlOtherPayeeLkp_a, BorderLayout.WEST);	
										pnlOtherPayeeLkp_a.setPreferredSize(new java.awt.Dimension(100, 35));
										pnlOtherPayeeLkp_a.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 0));

										{

											lookupOtherPayee = new _JLookup(null, "Other Payee",0,2);
											pnlOtherPayeeLkp_a.add(lookupOtherPayee);
											lookupOtherPayee.setLookupSQL(getOtherPayee());
											lookupOtherPayee.setReturnColumn(0);
											lookupOtherPayee.setEnabled(false);
											lookupOtherPayee.setFilterName(true);
											lookupOtherPayee.addLookupListener(new LookupListener() {
												public void lookupPerformed(LookupEvent event) {
													Object[] data = ((_JLookup)event.getSource()).getDataSet();
													if(data != null){

														payee_id = (String) data[0];
														tagOtherPayee.setTag("");
														String name = (String) data[1];						
														tagOtherPayee.setTag(name);
														KEYBOARD_MANAGER.focusNextComponent();	
														enable_leftPanel(true);
													}
												}
											});

										}
									}
									{

										pnlOtherPayeeLkp_b = new JPanel(new BorderLayout(0,0));
										pnlOtherPayeeLkp.add(pnlOtherPayeeLkp_b, BorderLayout.CENTER);	
										pnlOtherPayeeLkp_b.setPreferredSize(new java.awt.Dimension(249, 35));
										pnlOtherPayeeLkp_b.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));

										{
											tagOtherPayee = new _JTagLabel("[ ]");
											pnlOtherPayeeLkp_b.add(tagOtherPayee);
											tagOtherPayee.setBounds(209, 27, 700, 22);
											tagOtherPayee.setEnabled(false);	
											tagOtherPayee.setPreferredSize(new java.awt.Dimension(27, 33));
										}	
									}
								}
							}
						}								

						{
							pnlOtherPayee = new JPanel(new BorderLayout(0, 0));
							pnlAgent_OtherPayee.add(pnlOtherPayee, BorderLayout.CENTER);	
							pnlOtherPayee.setPreferredSize(new java.awt.Dimension(451, 65));
							pnlOtherPayee.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
							pnlOtherPayee.setBorder(lineBorder);

						
						}
						{
							pnlOtherPayeeProj = new JPanel(new BorderLayout(0, 0));
							pnlOtherPayee.add(pnlOtherPayeeProj, BorderLayout.CENTER);	
							pnlOtherPayeeProj.setPreferredSize(new java.awt.Dimension(452, 17));
							pnlOtherPayeeProj.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));

							{
								/*pnlOtherPayeeProjLbl = new JPanel(new BorderLayout(0, 0));
								pnlOtherPayeeProj.add(pnlOtherPayeeProjLbl, BorderLayout.WEST);	
								pnlOtherPayeeProjLbl.setPreferredSize(new java.awt.Dimension(99, 27));
								pnlOtherPayeeProjLbl.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));*/
								
								pnlOtherPayeeProjLbl = new JPanel(new GridLayout(2,1, 5, 5));
								pnlOtherPayeeProj.add(pnlOtherPayeeProjLbl, BorderLayout.WEST);
								pnlOtherPayeeProjLbl.setPreferredSize(new java.awt.Dimension(104, 46));
								pnlOtherPayeeProjLbl.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 0));

								{
									lblOtherPayeeProj = new JLabel("* Project : ", JLabel.TRAILING);
									pnlOtherPayeeProjLbl.add(lblOtherPayeeProj);
									lblOtherPayeeProj.setBounds(8, 11, 62, 12);
									lblOtherPayeeProj.setEnabled(false);	
									lblOtherPayeeProj.setPreferredSize(new java.awt.Dimension(108, 31));
									lblOtherPayeeProj.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
								}
								{
									lblOtherPayeePhase = new JLabel("Phase : ", JLabel.TRAILING);
									pnlOtherPayeeProjLbl.add(lblOtherPayeePhase);
									lblOtherPayeePhase.setEnabled(false);	
									lblOtherPayeePhase.setPreferredSize(new java.awt.Dimension(108, 31));
									lblOtherPayeePhase.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
								}
							}
							{
								pnlOtherPayeeProjTag = new JPanel(new BorderLayout(0, 0));
								pnlOtherPayeeProj.add(pnlOtherPayeeProjTag, BorderLayout.CENTER);	
								pnlOtherPayeeProjTag.setPreferredSize(new java.awt.Dimension(7, 29));
								pnlOtherPayeeProjTag.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
								{
									/*pnlOtherPayeeLkp_d = new JPanel(new BorderLayout(5, 5));
									pnlOtherPayeeProjTag.add(pnlOtherPayeeLkp_d, BorderLayout.CENTER);	
									pnlOtherPayeeLkp_d.setPreferredSize(new java.awt.Dimension(103, 31));
									pnlOtherPayeeLkp_d.setBorder(BorderFactory.createEmptyBorder(0,5,5,0));*/
									
									pnlOtherPayeeLkp_d = new JPanel(new GridLayout(2,1, 5, 5));
									pnlOtherPayeeProjTag.add(pnlOtherPayeeLkp_d, BorderLayout.WEST);
									pnlOtherPayeeLkp_d.setPreferredSize(new java.awt.Dimension(96, 46));
									pnlOtherPayeeLkp_d.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));

									{

										lookupAgentProject = new _JLookup(null, "Project",5,2);
										pnlOtherPayeeLkp_d.add(lookupAgentProject);
										//lookupAgentProject.setLookupSQL(getProject());
										lookupAgentProject.setReturnColumn(0);
										lookupAgentProject.setEnabled(false);
										lookupAgentProject.addLookupListener(new LookupListener() {
											public void lookupPerformed(LookupEvent event) {
												Object[] data = ((_JLookup)event.getSource()).getDataSet();
												if(data != null){

													String name = (String) data[1];						
													tagProj.setTag(name);
													proj_code   = (String) data[0];	
													lookupPromoCode.setLookupSQL(getPromoCode());
													lookupPromoCode.setText("");
													tagPromoCode.setTag("");
													
													//enable phase
													lblOtherPayeePhase.setEnabled(true);	
													lookupPhase.setEnabled(true);	
													tagPhase.setEnabled(true);	
													lookupPhase.setValue("");
													tagPhase.setTag("");
													lookupPhase.setLookupSQL(getSubproject());
													
												}
											}
										});
										lookupAgentProject.addFocusListener(new FocusListener() {
											
											@Override
											public void focusLost(FocusEvent e) {
												// TODO Auto-generated method stub
												
											}
											
											@Override
											public void focusGained(FocusEvent e) {
												lookupAgentProject.setLookupSQL(getProject(lookupCompany.getValue()));
												
											}
										});
									}
									{
										lookupPhase = new _JLookup(null, "Phase",0,2);
										pnlOtherPayeeLkp_d.add(lookupPhase);
										lookupPhase.setLookupSQL(SQL_COMPANY());
										lookupPhase.setReturnColumn(0);
										lookupPhase.setEnabled(false);	
										lookupPhase.addLookupListener(new LookupListener() {
											public void lookupPerformed(LookupEvent event) {
												Object[] data = ((_JLookup)event.getSource()).getDataSet();
												if(data != null){

													phase_id		= (String) data[0];	
													phase_no		= (String) data[1];	
													tagPhase.setTag(phase_no);
												}
											}
										});
									}
								}
								{

									/*pnlOtherPayeeLkp_b = new JPanel(new BorderLayout(5, 5));
									pnlOtherPayeeProjTag.add(pnlOtherPayeeLkp_b, BorderLayout.EAST);	
									pnlOtherPayeeLkp_b.setPreferredSize(new java.awt.Dimension(240, 31));
									pnlOtherPayeeLkp_b.setBorder(BorderFactory.createEmptyBorder(0,5,5,0));*/
									
									pnlOtherPayeeLkp_b = new JPanel(new GridLayout(2,1, 5, 5));
									pnlOtherPayeeProjTag.add(pnlOtherPayeeLkp_b, BorderLayout.CENTER);
									pnlOtherPayeeLkp_b.setPreferredSize(new java.awt.Dimension(96, 46));
									pnlOtherPayeeLkp_b.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 0));

									{
										tagProj = new _JTagLabel("[ ]");
										pnlOtherPayeeLkp_b.add(tagProj);
										tagProj.setBounds(209, 27, 700, 22);
										tagProj.setEnabled(false);	
										tagProj.setPreferredSize(new java.awt.Dimension(238, 26));
									}	
									{
										tagPhase = new _JTagLabel("[ ]");
										pnlOtherPayeeLkp_b.add(tagPhase);
										tagPhase.setBounds(209, 27, 700, 22);
										tagPhase.setEnabled(false);	
										tagPhase.setPreferredSize(new java.awt.Dimension(238, 26));
									}	
								}
							}
						}
					}		

					pnlPromoCode_Prodn = new JPanel(new BorderLayout(0,0));
					pnlPromoDetailsRight.add(pnlPromoCode_Prodn, BorderLayout.CENTER);	
					pnlPromoCode_Prodn.setPreferredSize(new java.awt.Dimension(453, 161));
					pnlPromoCode_Prodn.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
					pnlPromoCode_Prodn.setBorder(lineBorder);		

					{
						{
							pnlPromoCode = new JPanel(new BorderLayout(0, 0));
							pnlPromoCode_Prodn.add(pnlPromoCode, BorderLayout.NORTH);	
							pnlPromoCode.setPreferredSize(new java.awt.Dimension(451, 34));
							pnlPromoCode.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
							pnlPromoCode.setBorder(lineBorder);

							{

								pnlPromoCodeLbl = new JPanel(new BorderLayout(5, 5));
								pnlPromoCode.add(pnlPromoCodeLbl, BorderLayout.WEST);	
								pnlPromoCodeLbl.setPreferredSize(new java.awt.Dimension(105, 32));
								pnlPromoCodeLbl.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

								{
									lblPromoCode = new JLabel("* Promo Code : ", JLabel.TRAILING);
									pnlPromoCodeLbl.add(lblPromoCode);
									lblPromoCode.setBounds(8, 11, 62, 12);
									lblPromoCode.setEnabled(false);	
									lblPromoCode.setPreferredSize(new java.awt.Dimension(86, 25));	
									lblPromoCode.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,11));
								}

							}
							{
								pnlPromoCodeLkp = new JPanel(new BorderLayout(5, 5));
								pnlPromoCode.add(pnlPromoCodeLkp, BorderLayout.CENTER);	
								pnlPromoCodeLkp.setPreferredSize(new java.awt.Dimension(104, 35));
								pnlPromoCodeLkp.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));

								{
									pnlPromoCodeLkp_a = new JPanel(new BorderLayout(5, 5));
									pnlPromoCodeLkp.add(pnlPromoCodeLkp_a, BorderLayout.WEST);	
									pnlPromoCodeLkp_a.setPreferredSize(new java.awt.Dimension(46, 35));
									pnlPromoCodeLkp_a.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

									{

										lookupPromoCode = new _JLookup(null, "Promo Code",0,2);
										pnlPromoCodeLkp_a.add(lookupPromoCode);										
										lookupPromoCode.setReturnColumn(0);
										lookupPromoCode.setEnabled(false);
										lookupPromoCode.setFilterName(true);
										lookupPromoCode.addLookupListener(new LookupListener() {
											public void lookupPerformed(LookupEvent event) {
												Object[] data = ((_JLookup)event.getSource()).getDataSet();
												if(data != null){

													String name = (String) data[1];						
													tagPromoCode.setTag(name);
													promo_type = (String) data[2];	
													System.out.printf("promo_type: " + promo_type);
													
													enable_rightPanel(true);
													
													if (lookupPromoCode.getText().equals("6"))
													{lookupClient.setLookupSQL(getClient_BDO(payee_id));}
													else {lookupClient.setLookupSQL(getClient(payee_id));}																										
													
													String entity			= "";
													String agent_id 		= lookupAgent.getText();
													String other_payee_id 	= lookupOtherPayee.getText();
													if (agent_id.equals("")) {entity=other_payee_id;} else {entity=agent_id;}
													
													txtGrossAmount.setText(nf.format(Double.parseDouble(data[6].toString())));
													
													Double gr_amt  = Double.parseDouble(data[6].toString());
													Double tax_rate = sql_getTaxRate(entity)/100;

													Double tax_amt = gr_amt*tax_rate;												
													Double net_amt = gr_amt*(1-tax_rate);

													txtWtax.setText(nf.format(tax_amt));
													txtNetAmount.setText(nf.format(net_amt));
													
													
													release_to = (String) data[4];
													acct_id = data[7].toString().substring(0,8);
													String remarks = "This promo/incentive ";
													System.out.printf("Account ID" + acct_id);
													
																									
													if (lookupPromoCode.getText().equals("2")||lookupPromoCode.getText().equals("1"))
													{
														remarks = remarks + "Tagging of units is not needed.";
													}	
													
													
													
													/*if (release_to.equals("In-House Brokers")||acct_id.equals("08-03-03"))
													{ JOptionPane.showMessageDialog(getContentPane(),remarks,
															"Information",JOptionPane.INFORMATION_MESSAGE); }*/
												}

											}
										});

									}
								}
								{

									pnlPromoCodeLkp_b = new JPanel(new BorderLayout(5, 5));
									pnlPromoCodeLkp.add(pnlPromoCodeLkp_b, BorderLayout.CENTER);	
									pnlPromoCodeLkp_b.setPreferredSize(new java.awt.Dimension(249, 35));
									pnlPromoCodeLkp_b.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));

									{
										tagPromoCode = new _JTagLabel("[ ]");
										pnlPromoCodeLkp_b.add(tagPromoCode);
										tagPromoCode.setBounds(209, 27, 700, 22);
										tagPromoCode.setEnabled(false);	
										tagPromoCode.setPreferredSize(new java.awt.Dimension(27, 33));
										tagPromoCode.setFont(new java.awt.Font("Segoe UI",Font.ITALIC,10));
									}	
								}
							}
						}

						{
							pnlProduction = new JPanel(new BorderLayout(0, 0));
							pnlPromoCode_Prodn.add(pnlProduction, BorderLayout.CENTER);	
							pnlProduction.setPreferredSize(new java.awt.Dimension(451, 111));
							pnlProduction.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

							{
								pnlProd_Period = new JPanel(new GridLayout(1, 2,5, 5));
								pnlProduction.add(pnlProd_Period, BorderLayout.NORTH);
								pnlProd_Period.setPreferredSize(new java.awt.Dimension(451, 55));
								pnlProd_Period.setBorder(components.JTBorderFactory.createTitleBorder("Production Period"));
								pnlProd_Period.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));

								{
									lblProdDateFrom = new JLabel("Date From   ", JLabel.TRAILING);
									pnlProd_Period.add(lblProdDateFrom, BorderLayout.CENTER);
									lblProdDateFrom.setEnabled(false);			
									lblProdDateFrom.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
								}
								{												
									dteProdDateFrom = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
									pnlProd_Period.add(dteProdDateFrom, BorderLayout.CENTER);						
									dteProdDateFrom.setDate(null);
									dteProdDateFrom.setEnabled(false);			
									dteProdDateFrom.setPreferredSize(new java.awt.Dimension(248, 38));
									dteProdDateFrom.setDate(null);
								}		
								{
									lblProdDateTo = new JLabel("Date To   ", JLabel.TRAILING);
									pnlProd_Period.add(lblProdDateTo);
									lblProdDateTo.setEnabled(false);	
									lblProdDateTo.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
								}
								{
									dateProdDateTo = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
									pnlProd_Period.add(dateProdDateTo, BorderLayout.EAST);
									dateProdDateTo.setBounds(485, 7, 125, 21);
									dateProdDateTo.setDate(null);
									dateProdDateTo.setEnabled(false);
									dateProdDateTo.setPreferredSize(new java.awt.Dimension(291, 27));
									dateProdDateTo.setDate(null);
								}
							}
							{
								pnlSupportFund = new JPanel(new GridLayout(1, 2,5, 5));
								pnlProduction.add(pnlSupportFund, BorderLayout.CENTER);
								pnlSupportFund.setPreferredSize(new java.awt.Dimension(451, 52));
								pnlSupportFund.setBorder(components.JTBorderFactory.createTitleBorder("Support Fund Period"));
								pnlSupportFund.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));

								{
									lblSuppDateFrom = new JLabel("Date From   ", JLabel.TRAILING);
									pnlSupportFund.add(lblSuppDateFrom, BorderLayout.CENTER);
									lblSuppDateFrom.setEnabled(false);			
									lblSuppDateFrom.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
								}
								{												
									dteSuppDateFrom = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
									pnlSupportFund.add(dteSuppDateFrom, BorderLayout.CENTER);						
									dteSuppDateFrom.setDate(null);
									dteSuppDateFrom.setEnabled(false);			
									dteSuppDateFrom.setPreferredSize(new java.awt.Dimension(248, 38));
									dteSuppDateFrom.setDate(null);
								}		
								{
									lblSuppDateTo = new JLabel("Date To   ", JLabel.TRAILING);
									pnlSupportFund.add(lblSuppDateTo);
									lblSuppDateTo.setEnabled(false);	
									lblSuppDateTo.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
								}
								{
									dateSuppDateTo = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
									pnlSupportFund.add(dateSuppDateTo, BorderLayout.EAST);
									dateSuppDateTo.setBounds(485, 7, 125, 21);
									dateSuppDateTo.setDate(null);
									dateSuppDateTo.setEnabled(false);
									dateSuppDateTo.setPreferredSize(new java.awt.Dimension(291, 27));
									dateSuppDateTo.setDate(null);
								}
							}
						}
					}

					pnlProdTarget = new JPanel(new BorderLayout(0,0));
					pnlPromoDetailsRight.add(pnlProdTarget, BorderLayout.SOUTH);	
					pnlProdTarget.setPreferredSize(new java.awt.Dimension(453, 33));
					pnlProdTarget.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
					pnlProdTarget.setBorder(lineBorder);	

					{
						{
							pnlProdTargetLbl = new JPanel(new BorderLayout(0,0));
							pnlProdTarget.add(pnlProdTargetLbl, BorderLayout.WEST);	
							pnlProdTargetLbl.setPreferredSize(new java.awt.Dimension(109, 36));
							pnlProdTargetLbl.setBorder(BorderFactory.createEmptyBorder(0, 0,0,0));							
							{
								lblProdTarget = new JLabel("Production Target : ", JLabel.TRAILING);
								pnlProdTargetLbl.add(lblProdTarget);
								lblProdTarget.setBounds(8, 11, 62, 12);
								lblProdTarget.setEnabled(false);	
								lblProdTarget.setPreferredSize(new java.awt.Dimension(108, 36));
								lblProdTarget.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
							}
						}
						{
							pnlProdTargetText = new JPanel(new BorderLayout(5, 5));
							pnlProdTarget.add(pnlProdTargetText, BorderLayout.CENTER);	
							pnlProdTargetText.setPreferredSize(new java.awt.Dimension(342, 36));
							pnlProdTargetText.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));							

							{
								txtProdTarget = new _JXFormattedTextField(JXFormattedTextField.CENTER);
								pnlProdTargetText.add(txtProdTarget);
								txtProdTarget.setFormatterFactory(_JXFormattedTextField.DECIMAL);
								txtProdTarget.setText("0.00");
								txtProdTarget.setEnabled(false);	
								txtProdTarget.setBounds(120, 0, 72, 22);	
								txtProdTarget.addKeyListener(new KeyAdapter() {
									public void keyReleased(KeyEvent e) {

									}				 
								});	
							}
						}
						{
							pnProdTarget_extra = new JPanel(new BorderLayout(5, 5));
							pnlProdTarget.add(pnProdTarget_extra, BorderLayout.EAST);	
							pnProdTarget_extra.setPreferredSize(new java.awt.Dimension(223, 36));
							pnProdTarget_extra.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));	

						}
					} //end of LEFT panel
				}

				{//start of RIGHT panel

					pnlPromoDetails_left = new JPanel(new BorderLayout(0, 0));
					pnlPromoDetails.add(pnlPromoDetails_left, BorderLayout.CENTER);	
					pnlPromoDetails_left.setPreferredSize(new java.awt.Dimension(475, 158));
					pnlPromoDetails_left.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
					pnlPromoDetails_left.setBorder(lineBorder);

					{
						pnlChangeOrder_a1_sub1 = new JPanel(new BorderLayout(0, 0));
						pnlPromoDetails_left.add(pnlChangeOrder_a1_sub1, BorderLayout.NORTH);	
						pnlChangeOrder_a1_sub1.setPreferredSize(new java.awt.Dimension(487, 63));
						pnlChangeOrder_a1_sub1.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
						pnlChangeOrder_a1_sub1.setBorder(lineBorder);

						{
							pnlClient_label = new JPanel(new BorderLayout(0, 0));
							pnlChangeOrder_a1_sub1.add(pnlClient_label, BorderLayout.NORTH);	
							pnlClient_label.setPreferredSize(new java.awt.Dimension(452, 34));
							pnlClient_label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

							{

								pnlClient_label_sub1 = new JPanel(new BorderLayout(5, 5));
								pnlClient_label.add(pnlClient_label_sub1, BorderLayout.WEST);	
								pnlClient_label_sub1.setPreferredSize(new java.awt.Dimension(88, 34));
								pnlClient_label_sub1.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

								{
									rbtnClient = new JRadioButton();
									pnlClient_label_sub1.add(rbtnClient);
									rbtnClient.setText("* Client");
									rbtnClient.setBounds(277, 12, 77, 18);
									rbtnClient.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
									rbtnClient.setSelected(true);
									rbtnClient.setEnabled(false);
									rbtnClient.setPreferredSize(new java.awt.Dimension(70, 25));
									rbtnClient.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent ae){
											rbtnClientList.setSelected(false);
											rbtnClient.setSelected(true);
											lookupClient.setEnabled(true);
											tagClientName.setEnabled(true);	
											lblClientProject.setEnabled(true);
											tagProjAlias.setEnabled(true);	
											lblClientUnit.setEnabled(true);
											tagClientUnitDesc.setEnabled(true);

											tblClientList.setEnabled(false); 	

											FncTables.clearTable(modelClientList);//Code to clear modelMain.		
											DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
											rowHeaderClientList.setModel(listModel);//Setting of DefaultListModel into rowHeader.
										}});					
								}
							}
							{
								pnlClient_label_sub2 = new JPanel(new BorderLayout(5, 5));
								pnlClient_label.add(pnlClient_label_sub2, BorderLayout.CENTER);	
								pnlClient_label_sub2.setPreferredSize(new java.awt.Dimension(104, 35));
								pnlClient_label_sub2.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));

								{
									pnlClient_label_sub2a = new JPanel(new BorderLayout(5, 5));
									pnlClient_label_sub2.add(pnlClient_label_sub2a, BorderLayout.WEST);	
									pnlClient_label_sub2a.setPreferredSize(new java.awt.Dimension(104, 35));
									pnlClient_label_sub2a.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

									{

										lookupClient = new _JLookup(null, "Client",0,2);
										pnlClient_label_sub2a.add(lookupClient);										
										lookupClient.setReturnColumn(0);
										lookupClient.setFilterName(true);
										lookupClient.setEnabled(false);
										lookupClient.addLookupListener(new LookupListener() {
											public void lookupPerformed(LookupEvent event) {
												Object[] data = ((_JLookup)event.getSource()).getDataSet();
												if(data != null){

													entity_id		   = (String) data[0];
													String client_name = (String) data[1];	
													String proj_alias  = (String) data[4];	
													String unit_desc   = (String) data[5];	
													proj_code   	   = (String) data[7];	
													pbl_id   	   	   = (String) data[2];	
													seq_no   	   	   = data[3].toString();	

													tagClientName.setTag(client_name);
													tagProjAlias.setTag(proj_alias);
													tagClientUnitDesc.setTag(unit_desc);
												}
											}
										});

									}
								}
								{

									pnlClient_label_sub2b = new JPanel(new BorderLayout(5, 5));
									pnlClient_label_sub2.add(pnlClient_label_sub2b, BorderLayout.CENTER);	
									pnlClient_label_sub2b.setPreferredSize(new java.awt.Dimension(249, 35));
									pnlClient_label_sub2b.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));

									{
										tagClientName = new _JTagLabel("[ ]");
										pnlClient_label_sub2b.add(tagClientName);
										tagClientName.setBounds(209, 27, 700, 22);
										tagClientName.setEnabled(false);	
										tagClientName.setPreferredSize(new java.awt.Dimension(27, 33));
									}	
								}
							}
						}
						{
							pnlClientProjUnit = new JPanel(new BorderLayout(0, 0));
							pnlChangeOrder_a1_sub1.add(pnlClientProjUnit, BorderLayout.CENTER);	
							pnlClientProjUnit.setPreferredSize(new java.awt.Dimension(452, 17));
							pnlClientProjUnit.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

							{
								pnlClientProject= new JPanel(new BorderLayout(0, 0));
								pnlClientProjUnit.add(pnlClientProject, BorderLayout.WEST);	
								pnlClientProject.setPreferredSize(new java.awt.Dimension(150, 27));
								pnlClientProject.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

								{

									pnlClientProjLbl = new JPanel(new BorderLayout(0, 0));
									pnlClientProject.add(pnlClientProjLbl, BorderLayout.WEST);	
									pnlClientProjLbl.setPreferredSize(new java.awt.Dimension(75, 29));
									pnlClientProjLbl.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

									{
										lblClientProject = new JLabel("Project : ", JLabel.TRAILING);
										pnlClientProjLbl.add(lblClientProject);
										lblClientProject.setBounds(8, 11, 62, 12);
										lblClientProject.setEnabled(false);
										lblClientProject.setPreferredSize(new java.awt.Dimension(96, 26));
										lblClientProject.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
									}
								}
								{
									pnlClientProjAlias = new JPanel(new BorderLayout(0, 0));
									pnlClientProject.add(pnlClientProjAlias, BorderLayout.CENTER);	
									pnlClientProjAlias.setPreferredSize(new java.awt.Dimension(7, 29));
									pnlClientProjAlias.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));

									{
										tagProjAlias = new _JTagLabel("[ ]");
										pnlClientProjAlias.add(tagProjAlias);
										tagProjAlias.setBounds(209, 27, 700, 22);
										tagProjAlias.setEnabled(false);	
										tagProjAlias.setPreferredSize(new java.awt.Dimension(27, 33));
									}	
								}								
							}
							{
								pnlClientUnit = new JPanel(new BorderLayout(0, 0));
								pnlClientProjUnit.add(pnlClientUnit, BorderLayout.CENTER);	
								pnlClientUnit.setPreferredSize(new java.awt.Dimension(230, 29));
								pnlClientUnit.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

								{
									pnlClientUnitLbl = new JPanel(new BorderLayout(0, 0));
									pnlClientUnit.add(pnlClientUnitLbl, BorderLayout.WEST);	
									pnlClientUnitLbl.setPreferredSize(new java.awt.Dimension(61, 29));
									pnlClientUnitLbl.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

									{
										lblClientUnit = new JLabel("Unit : ", JLabel.TRAILING);
										pnlClientUnitLbl.add(lblClientUnit);
										lblClientUnit.setBounds(8, 11, 62, 12);
										lblClientUnit.setEnabled(false);
										lblClientUnit.setPreferredSize(new java.awt.Dimension(79, 29));
										lblClientUnit.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
									}

								}
								{
									pnlClientUnitDesc = new JPanel(new BorderLayout(0, 0));
									pnlClientUnit.add(pnlClientUnitDesc, BorderLayout.CENTER);	
									pnlClientUnitDesc.setPreferredSize(new java.awt.Dimension(7, 29));
									pnlClientUnitDesc.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
									{
										tagClientUnitDesc = new _JTagLabel("[ ]");
										pnlClientUnitDesc.add(tagClientUnitDesc);
										tagClientUnitDesc.setBounds(209, 27, 700, 22);
										tagClientUnitDesc.setEnabled(false);	
										tagClientUnitDesc.setPreferredSize(new java.awt.Dimension(27, 33));
									}	
								}			
							}
						}
					}
					{

						pnlClientList = new JPanel(new BorderLayout(5, 5));
						pnlPromoDetails_left.add(pnlClientList, BorderLayout.CENTER);	
						pnlClientList.setPreferredSize(new java.awt.Dimension(451, 121));
						pnlClientList.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
						pnlClientList.setBorder(lineBorder);

						{

							pnlClientList_rbtn = new JPanel(new BorderLayout(5,5));
							pnlClientList.add(pnlClientList_rbtn, BorderLayout.NORTH);	
							pnlClientList_rbtn.setPreferredSize(new java.awt.Dimension(442, 21));
							pnlClientList_rbtn.setBorder(BorderFactory.createEmptyBorder(5,5,0,0));

							{
								rbtnClientList = new JRadioButton();
								pnlClientList_rbtn.add(rbtnClientList);
								rbtnClientList.setText("Unit List");
								rbtnClientList.setBounds(277, 12, 77, 18);
								rbtnClientList.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
								rbtnClientList.setSelected(false);
								rbtnClientList.setEnabled(false);
								rbtnClientList.setPreferredSize(new java.awt.Dimension(70, 25));
								rbtnClientList.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent ae){	
										
										rbtnClientList.setSelected(true);
										rbtnClient.setSelected(false);
										lookupClient.setEnabled(false);
										tagClientName.setEnabled(false);	
										lblClientProject.setEnabled(false);
										tagProjAlias.setEnabled(false);	
										lblClientUnit.setEnabled(false);
										tagClientUnitDesc.setEnabled(false);

										tblClientList.setEnabled(true); 
										
										if (lookupPromoCode.getText().equals("6")||lookupPromoCode.getText().equals("10"))
										{displayClientList_BDO(modelClientList, rowHeaderClientList );}
										else {displayClientList(modelClientList, rowHeaderClientList );}		
										
										
										
											
										modelClientList.setEditable(true);

										lookupClient.setValue("");
										tagClientName.setText("[ ]");
										tagProjAlias.setText("[ ]");
										tagClientUnitDesc.setText("[ ]");

									}});					
							}

						}
						{
							pnlClientListTable = new JPanel(new BorderLayout(5, 5));
							pnlClientList.add(pnlClientListTable, BorderLayout.CENTER);	
							pnlClientListTable.setPreferredSize(new java.awt.Dimension(75, 35));
							pnlClientListTable.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

							{
								scrollClientList = new _JScrollPaneMain();
								pnlClientListTable.add(scrollClientList, BorderLayout.CENTER);
								{
									modelClientList = new modelClientList();

									tblClientList= new _JTableMain(modelClientList);
									scrollClientList.setViewportView(tblClientList);
									modelClientList.setEditable(true);
									tblClientList.addMouseListener(this);	
									tblClientList.setEnabled(false);	
									tblClientList.setSortable(false);	
									tblClientList.getColumnModel().getColumn(0).setPreferredWidth(50);
									tblClientList.getColumnModel().getColumn(1).setPreferredWidth(240);
									tblClientList.getColumnModel().getColumn(2).setPreferredWidth(80);
									tblClientList.getColumnModel().getColumn(3).setPreferredWidth(80);
									tblClientList.addKeyListener(new KeyAdapter() {
										public void keyReleased(KeyEvent evt) {tblClientList.packAll();}							
										public void keyPressed(KeyEvent e) {tblClientList.packAll();}	

									}); 
									tblClientList.addMouseListener(new MouseAdapter() {
										public void mousePressed(MouseEvent e) {
											if(tblClientList.rowAtPoint(e.getPoint()) == -1){}
											else{tblClientList.setCellSelectionEnabled(true);}
										}
										public void mouseReleased(MouseEvent e) {
											if(tblClientList.rowAtPoint(e.getPoint()) == -1){}
											else{tblClientList.setCellSelectionEnabled(true);}
										}
									});

								}
								{
									rowHeaderClientList = tblClientList.getRowHeader22();
									scrollClientList.setRowHeaderView(rowHeaderClientList);
									scrollClientList.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
									scrollClientList.setPreferredSize(new java.awt.Dimension(505, 220));
								}
							}
						}
					}

					{ 
						pnlPromoAmount = new JPanel(new GridLayout(1, 3, 3, 3));
						pnlPromoAmount = new JPanel(new GridLayout(0, 3, 3, 3));
						pnlPromoDetails_left.add(pnlPromoAmount, BorderLayout.SOUTH);	
						pnlPromoAmount.setPreferredSize(new java.awt.Dimension(462, 70));
						pnlPromoAmount.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
						pnlPromoAmount.setBorder(components.JTBorderFactory.createTitleBorder("Per Unit"));

						{
							pnlGross = new JPanel(new BorderLayout(0,0));
							pnlPromoAmount.add(pnlGross);	
						//	pnlGross.setPreferredSize(new java.awt.Dimension(146, 65));
							pnlGross.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));

							{
								pnlGrossLabel = new JPanel(new BorderLayout(0,0));
								pnlGross.add(pnlGrossLabel, BorderLayout.WEST);	
								//pnlGrossLabel.setPreferredSize(new java.awt.Dimension(67, 65));
								//pnlGrossLabel.setBorder(BorderFactory.createEmptyBorder(0, 0,0,0));							
								{
									lblGrossAmt = new JLabel("* Grs. Amt. : ");
									pnlGrossLabel.add(lblGrossAmt);
									//lblGrossAmt.setBounds(8, 11, 62, 12);
									lblGrossAmt.setEnabled(false);				
									//lblGrossAmt.setPreferredSize(new java.awt.Dimension(61, 65));
									lblGrossAmt.setFont(new java.awt.Font("Segoe UI",Font.ITALIC,12));
									//lblGrossAmt.setPreferredSize(new java.awt.Dimension(61, 65));
									lblGrossAmt.setFont(new java.awt.Font("Segoe UI",Font.ITALIC,10));
								}

							}
							{
								pnlGrossText = new JPanel(new BorderLayout(5, 5));
								pnlGross.add(pnlGrossText);	
								//pnlGrossText.setPreferredSize(new java.awt.Dimension(462, 79));
								//pnlGrossText.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));							

								{
									txtGrossAmount = new _JXFormattedTextField(JXFormattedTextField.CENTER);
									pnlGrossText.add(txtGrossAmount);
									txtGrossAmount.setFormatterFactory(_JXFormattedTextField.DECIMAL);
									txtGrossAmount.setText("0.00");
									txtGrossAmount.setEnabled(false);	
									txtGrossAmount.setEditable(false);	
									//txtGrossAmount.setBounds(120, 0, 72, 22);	
									txtGrossAmount.addKeyListener(new KeyAdapter() {
										public void keyReleased(KeyEvent e) {

											String entity			= "";
											String agent_id 		= lookupAgent.getText();
											String other_payee_id 	= lookupOtherPayee.getText();
											if (agent_id.equals("")) {entity=other_payee_id;} else {entity=agent_id;}

											if (agent_id.equals("")&&other_payee_id.equals(""))
											{
												JOptionPane.showMessageDialog(null,"Please select either an agent or other payee first.","", 
														JOptionPane.INFORMATION_MESSAGE);												
												txtGrossAmount.setText("0.00");
											}
											else 
											{
												Double gr_amt  = Double.valueOf(txtGrossAmount.getText().trim().replace(",","")).doubleValue();
												Double tax_rate = sql_getTaxRate(entity)/100;

												Double tax_amt = gr_amt*tax_rate;												
												Double net_amt = gr_amt*(1-tax_rate);

												txtWtax.setText(nf.format(tax_amt));
												txtNetAmount.setText(nf.format(net_amt));
											}

										}				 
									});	
								}
							}
						}
						{
							pnlWtax = new JPanel(new BorderLayout(0,0));
							pnlPromoAmount.add(pnlWtax);	
							//pnlWtax.setPreferredSize(new java.awt.Dimension(159, 41));
							//pnlWtax.setBorder(BorderFactory.createEmptyBorder(0, 0,0,0));							

							{
								pnlWtaxLabel = new JPanel(new BorderLayout(0,0));
								pnlWtax.add(pnlWtaxLabel, BorderLayout.WEST);	
								//pnlWtaxLabel.setPreferredSize(new java.awt.Dimension(79, 65));
								//pnlWtaxLabel.setBorder(BorderFactory.createEmptyBorder(0, 0,0,0));							

								{
									lblWtax = new JLabel("WTax/Others: ");
									pnlWtaxLabel.add(lblWtax);
									//lblWtax.setBounds(8, 11, 62, 12);
									lblWtax.setEnabled(false);		
									//lblWtax.setPreferredSize(new java.awt.Dimension(50, 41));
									lblWtax.setFont(new java.awt.Font("Segoe UI",Font.ITALIC,12));
									lblWtax.setPreferredSize(new java.awt.Dimension(50, 41));
									lblWtax.setFont(new java.awt.Font("Segoe UI",Font.ITALIC,10));
								}
							}
							{
								pnlWtaxText = new JPanel(new BorderLayout(5,5));
								pnlWtax.add(pnlWtaxText, BorderLayout.CENTER);	
								//pnlWtaxText.setPreferredSize(new java.awt.Dimension(462, 79));
								//pnlWtaxText.setBorder(BorderFactory.createEmptyBorder(5,5,5,0));	

								{
									txtWtax = new _JXFormattedTextField(JXFormattedTextField.CENTER);
									pnlWtaxText.add(txtWtax);
									txtWtax.setFormatterFactory(_JXFormattedTextField.DECIMAL);
									txtWtax.setText("0.00");
									txtWtax.setEnabled(false);	
									txtWtax.setEditable(false);	
									//txtWtax.setBounds(120, 0, 72, 22);
								}
							}
						}
						{
							pnlNetAmount = new JPanel(new BorderLayout(0,0));
							pnlPromoAmount.add(pnlNetAmount, BorderLayout.EAST);	
							pnlNetAmount.setPreferredSize(new java.awt.Dimension(148, 65));
							pnlNetAmount.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));							

							{
								pnlNetLabel = new JPanel(new BorderLayout(0,0));
								pnlNetAmount.add(pnlNetLabel, BorderLayout.WEST);	
								//pnlNetLabel.setPreferredSize(new java.awt.Dimension(63, 65));
								//pnlNetLabel.setBorder(BorderFactory.createEmptyBorder(0, 0,0,0));							

								{
									lblNetAmount = new JLabel("Net Amt. : ");
									pnlNetLabel.add(lblNetAmount);
									//lblNetAmount.setBounds(8, 11, 62, 12);
									lblNetAmount.setEnabled(false);		
									//lblNetAmount.setPreferredSize(new java.awt.Dimension(50, 41));
									lblNetAmount.setFont(new java.awt.Font("Segoe UI",Font.ITALIC,12));
									//lblNetAmount.setPreferredSize(new java.awt.Dimension(50, 41));
									lblNetAmount.setFont(new java.awt.Font("Segoe UI",Font.ITALIC,10));
								}
							}
							{
								pnlNetAmtText = new JPanel(new BorderLayout(5,5));
								pnlNetAmount.add(pnlNetAmtText, BorderLayout.CENTER);	
								//pnlNetAmtText.setPreferredSize(new java.awt.Dimension(462, 79));
							    //pnlNetAmtText.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));	

								{
									txtNetAmount = new _JXFormattedTextField(JXFormattedTextField.CENTER);
									pnlNetAmtText.add(txtNetAmount);
									txtNetAmount.setFormatterFactory(_JXFormattedTextField.DECIMAL);
									txtNetAmount.setText("0.00");									
									txtNetAmount.setEnabled(false);	
									txtNetAmount.setEditable(false);	
									//txtNetAmount.setBounds(120, 0, 72, 22);	
								}
							}
						}
					}					

				}// end of RIGHT panel : Promo Amount			
			}
			{
				pnlCenter = new JPanel(new BorderLayout(0, 0));
				pnlMain.add(pnlCenter, BorderLayout.CENTER);
				pnlCenter.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
				pnlCenter.setBorder(lineBorder);	
				pnlCenter.setPreferredSize(new java.awt.Dimension(948, 99));

				{
					pnlRemarks = new JPanel(new BorderLayout(0, 0));
					pnlCenter.add(pnlRemarks, BorderLayout.CENTER);	
					pnlRemarks.setPreferredSize(new java.awt.Dimension(464, 132));					
					pnlRemarks.setBorder(components.JTBorderFactory.createTitleBorder("Remarks"));

					{
						scpPromoRemark = new JScrollPane();
						pnlRemarks.add(scpPromoRemark);
						scpPromoRemark.setBounds(82, 7, 150, 61);
						scpPromoRemark.setOpaque(true);
						scpPromoRemark.setPreferredSize(new java.awt.Dimension(100, 285));

						{
							txtPromoRemark = new JTextArea();
							scpPromoRemark.add(txtPromoRemark);
							scpPromoRemark.setViewportView(txtPromoRemark);
							txtPromoRemark.setBounds(77, 3, 250, 81);
							txtPromoRemark.setLineWrap(true);
							txtPromoRemark.setPreferredSize(new java.awt.Dimension(977, 76));
							txtPromoRemark.setEditable(false);
							txtPromoRemark.setEnabled(false);	
							txtPromoRemark.setText("");	
						}			
					}
				}

				{
					pnlInKind = new JPanel(new BorderLayout(0, 0));
					pnlCenter.add(pnlInKind, BorderLayout.EAST);	
					pnlInKind.setPreferredSize(new java.awt.Dimension(527, 108));
					pnlInKind.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
					pnlInKind.setBorder(lineBorder);

					{

						{
							pnlInKindRbtn = new JPanel(new BorderLayout(0, 0));
							pnlInKind.add(pnlInKindRbtn, BorderLayout.NORTH);	
							pnlInKindRbtn.setPreferredSize(new java.awt.Dimension(452, 34));
							pnlInKindRbtn.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

							{

								pnlInKindLbl = new JPanel(new BorderLayout(5, 5));
								pnlInKindRbtn.add(pnlInKindLbl, BorderLayout.WEST);	
								pnlInKindLbl.setPreferredSize(new java.awt.Dimension(229, 34));								
								pnlInKindLbl.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

								{

									cBtnInKind = new JCheckBox("Create Disbursement Request");
									pnlInKindLbl.add(cBtnInKind);
									cBtnInKind.setSelected(true);	
									cBtnInKind.setEnabled(false);	
									cBtnInKind.setPreferredSize(new java.awt.Dimension(345, 130));
									cBtnInKind.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
									cBtnInKind.addItemListener(new ItemListener() {
										public void itemStateChanged(ItemEvent arg0) {

											/*if (cBtnInKind.isSelected()){
												lookupInKind.setEnabled(true);
												tagInKind.setEnabled(true);	
											}
											else {
												lookupInKind.setEnabled(false);
												tagInKind.setEnabled(false);	
											}*/
											if (cBtnInKind.isSelected()==true){
												for_disbproc = true;
											}
											else {
												for_disbproc = false;
											}
										}
									});			
								}

							}
							{
								pnlInKindLkp = new JPanel(new BorderLayout(5, 5));
								pnlInKindRbtn.add(pnlInKindLkp, BorderLayout.CENTER);	
								pnlInKindLkp.setPreferredSize(new java.awt.Dimension(104, 35));
								pnlInKindLkp.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));

								{
									pnlInKindLkp_a = new JPanel(new BorderLayout(5, 5));
									pnlInKindLkp.add(pnlInKindLkp_a, BorderLayout.WEST);	
									pnlInKindLkp_a.setPreferredSize(new java.awt.Dimension(104, 35));
									pnlInKindLkp_a.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

									{

										lookupInKind = new _JLookup(null, "In Kind",0,2);
										pnlInKindLkp_a.add(lookupInKind);
										//lookupInKind.setLookupSQL();
										lookupInKind.setReturnColumn(0);
										lookupInKind.setEnabled(false);
										lookupInKind.setVisible(false);
										lookupInKind.addLookupListener(new LookupListener() {
											public void lookupPerformed(LookupEvent event) {
												Object[] data = ((_JLookup)event.getSource()).getDataSet();
												if(data != null){

													co_id 		= (String) data[0];	
													String name = (String) data[1];						
													tagCompany.setTag(name);

													KEYBOARD_MANAGER.focusNextComponent();		

												}
											}
										});

									}
								}
								{

									pnlInKindLkp_b = new JPanel(new BorderLayout(5, 5));
									pnlInKindLkp.add(pnlInKindLkp_b, BorderLayout.CENTER);	
									pnlInKindLkp_b.setPreferredSize(new java.awt.Dimension(249, 35));
									pnlInKindLkp_b.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));

									{
										tagInKind = new _JTagLabel("[ ]");
										pnlInKindLkp_b.add(tagInKind);
										tagInKind.setBounds(209, 27, 700, 22);
										tagInKind.setEnabled(false);	
										tagInKind.setVisible(false);
										tagInKind.setPreferredSize(new java.awt.Dimension(27, 33));
									}	
								}
							}
						}
					}
					{			

						pnlInKind_table = new JPanel(new BorderLayout(0, 0));
						pnlInKind.add(pnlInKind_table, BorderLayout.CENTER);	
						pnlInKind_table.setPreferredSize(new java.awt.Dimension(454, 74));
						pnlInKind_table.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
						pnlInKind_table.setVisible(false);
						pnlInKind_table.setBorder(lineBorder);

						{
							scrollInKind = new _JScrollPaneMain();
							pnlInKind_table.add(scrollInKind, BorderLayout.CENTER);
							{
								modelInKind = new modelClientList();

								tblInKind = new _JTableMain(modelInKind);
								scrollInKind.setViewportView(tblInKind);
								tblInKind.setEnabled(false);	
								tblInKind.addMouseListener(this);	
								tblInKind.getColumnModel().getColumn(0).setPreferredWidth(80);
								tblInKind.getColumnModel().getColumn(1).setPreferredWidth(80);
								tblInKind.getColumnModel().getColumn(2).setPreferredWidth(80);
								tblInKind.getColumnModel().getColumn(3).setPreferredWidth(80);
								tblInKind.getColumnModel().getColumn(4).setPreferredWidth(80);
								tblInKind.getColumnModel().getColumn(5).setPreferredWidth(80);
								tblInKind.getColumnModel().getColumn(6).setPreferredWidth(80);
								tblInKind.getColumnModel().getColumn(7).setPreferredWidth(80);
								tblInKind.addKeyListener(new KeyAdapter() {
									public void keyReleased(KeyEvent evt) {}							
									public void keyPressed(KeyEvent e) {}

								}); 
								tblInKind.addMouseListener(new MouseAdapter() {
									public void mousePressed(MouseEvent e) {
										if(tblCO.rowAtPoint(e.getPoint()) == -1){tblInKind.clearSelection();}
										else{tblCO.setCellSelectionEnabled(true);}
									}
									public void mouseReleased(MouseEvent e) {
										if(tblCO.rowAtPoint(e.getPoint()) == -1){tblInKind.clearSelection();}
										else{tblCO.setCellSelectionEnabled(true);}
									}
								});

							}
							{
								rowHeaderInKind = tblInKind.getRowHeader();
								scrollInKind.setRowHeaderView(rowHeaderInKind);
								scrollInKind.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
								scrollInKind.setPreferredSize(new java.awt.Dimension(454, 130));
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
				pnlSouth.setPreferredSize(new Dimension(55, 30));
				{
					JPanel pnlSouthCenter = new JPanel(new GridLayout(1, 2, 5, 5));
					pnlSouth.add(pnlSouthCenter, BorderLayout.CENTER);
					pnlSouthCenter.setPreferredSize(new Dimension(1000, 30));

					{
						btnAddNew = new JButton("Add New");
						pnlSouthCenter.add(btnAddNew);
						btnAddNew.setActionCommand("Add");
						btnAddNew.addActionListener(this);
						btnAddNew.setEnabled(false);
					}
					{
						btnEdit = new JButton("Edit");
						pnlSouthCenter.add(btnEdit);
						btnEdit.setActionCommand("Edit");	
						btnEdit.setEnabled(false);
						//btnEdit.setVisible(false);
						btnEdit.addActionListener(this);
						btnEdit.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {

								if(FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "8")==true){
									//enable_lookup_datechoosers(true);
									txtGrossAmount.setEnabled(true);
									txtPromoRemark.setEditable(true);
									txtPromoRemark.setEnabled(true);
									
									enableButtons(false, false, true, true, false);
									lookupCDF.setEnabled(false);
									rbtnClient.setSelected(true);
									rbtnClient.setEnabled(false);
									rbtnClientList.setSelected(false);
									rbtnClientList.setEnabled(false);
									lookupOtherPayee.setEnabled(false);
									to_do = "edit";
								}else if (FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "8")==false){
									JOptionPane.showMessageDialog(getContentPane(),"Sorry, you are not authorized to edit promo/incentive.","Warning",JOptionPane.WARNING_MESSAGE); 
								}
							}
						});
					}
					{
						btnUntag = new JButton("Untag");
						pnlSouthCenter.add(btnUntag);
						btnUntag.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								untagCommManual(lookupCDF.getValue());
								cancel();
								JOptionPane.showMessageDialog(getContentPane(), "Untag succesful", "Save", JOptionPane.INFORMATION_MESSAGE);
							}
						});
					}
					{
						btnSave = new JButton("Save");
						pnlSouthCenter.add(btnSave);
						btnSave.setActionCommand("Save");
						btnSave.addActionListener(this);
						btnSave.setEnabled(false);
					}				
					{
						btnCancel = new JButton("Cancel");
						pnlSouthCenter.add(btnCancel);
						btnCancel.setActionCommand("Cancel");
						btnCancel.addActionListener(this);
						btnCancel.setEnabled(false);
					}
				}
			}
		}

		//added 01/26/2016 - purpose - set CENQHOMES as default company
		initialize_comp();
	}

	//display tables
	private void displayClientList(DefaultTableModel modelMain, JList rowHeader) {//used

		FncTables.clearTable(modelMain);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		String sql = 			
			"select --distinct on (a.pbl_id::int) \r\n" + 
			"\r\n" + 
			"false," +
			"c.proj_alias,\r\n" + 
			"trim(d.description),\r\n" + 
			"upper(trim(b.entity_name)) as client,\r\n" + 
			"a.entity_id,\r\n" + 
			"trim(a.pbl_id) as pbl_id,\r\n" + 
			"a.seq_no,\r\n" + 
			"e.status_desc," +
			"c.proj_id  \r\n" + 
			"\r\n" + 
			"from (select * from rf_sold_unit a \n" +
			"	--where (pbl_id::int, seq_no) not in (select pbl_id, seq_no from canceled_accounts) \n" +
			"	where status_id != 'I' and (pbl_id, seq_no, '"+payee_id+"')  not in (select pbl_id, seq_no, agent_code from cm_schedule_dl \n" +  //no the same agent, pbl_id, seq_no, promo_code can have double promo disbursement
			"		where status != 'I' and tran_type = 'BB' and promo_code = '"+lookupPromoCode.getText()+"')\r\n" + 
			")a\r\n" + 
			"left join mf_sellingagent_info aa on a.sellingagent = aa.agent_id\n" + 
			"left join mf_sellingagent_info aaa on aa.override_id = aaa.agent_id \n" +
			"left join rf_entity b on a.entity_id = b.entity_id\r\n" + 
			"left join mf_project c on a.projcode = c.proj_id\r\n" + 
			"left join mf_unit_info d on a.projcode = d.proj_id and a.pbl_id = d.pbl_id\r\n" + 
			"left join mf_record_status e on a.status_id = e.status_id\r\n" +
			
			"where a.pbl_id is not null \n" ;
			
			if (rbtnAgent.isSelected()==true)
			{
				sql = sql + " and a.sellingagent = '"+payee_id+"' \n" +  //Agent Mismo						
						"or a.sellingagent in (select a.agent_id \n" + //Agent's Downline
						"	from mf_sellingagent_info a \r\n" + 
						"	where trim(a.override_id) = '"+payee_id+"') \n" +
						" or aa.override_id = '"+payee_id+"'  " +
						" or aaa.override_id = '"+payee_id+"'  " ;
			}
			else {}
			
			if (promo_type.equals("TR"))
			{
				sql = sql + " and (a.pbl_id,a.seq_no) in (select distinct on (pbl_id, seq_no) pbl_id, seq_no  " +
						"from rf_buyer_status where byrstatus_id = '17' and status_id = 'A') \n";
			}
			else if (promo_type.equals("OR"))
			{
				sql = sql + " and (a.pbl_id,a.seq_no) in (select distinct on (pbl_id, seq_no) pbl_id, seq_no  " +
						"from rf_buyer_status where byrstatus_id = '01' and status_id = 'A') \n";
			}
			else {}
			
			
			sql = sql +
			
			"and (case when '"+phase_id+"' = '' then true else d.sub_proj_id = '"+phase_id+"' end)  " +
			"order by b.entity_name, a.pbl_id::int, a.seq_no desc \r\n" + 
			"\r\n"  ;	
			
			System.out.printf("displayClientList: " + sql);
		pgSelect db = new pgSelect();
		db.select(sql);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());				
			}	
		}		

		else {}

		tblClientList.packAll();
		adjustRowHeight_account();

	}	
	
	private void displayClientList_BDO(DefaultTableModel modelMain, JList rowHeader) {//used

		FncTables.clearTable(modelMain);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.
		
		String payeeID = lookupAgent.getText();

		String sql = "select * \n" +
			"from (" +
			"select distinct on (a.pbl_id::int, seq_no) \n" +  
			"false, \n" +
			"c.proj_alias, \n" + 
			"trim(d.description), \n" + 
			"upper(trim(b.entity_name)) as client, \n" + 
			"a.entity_id, \n" + 
			"trim(a.pbl_id) as pbl_id, \n" + 
			"a.seq_no, \n" + 
			"e.status_desc," +
			"c.proj_id \n" + 
			"from \n" +
			// No same agent, unit and sequence number can have the same promo code
			// Modified by Mann2x; Date Modified: July 22, 2019; Exception made for Arnold Tajan's promo of employee production incentive;
			"( \n" +
			"		select * \n" +
			"		from rf_sold_unit \n" +
			"		where \n" + 
			"		(\n" + 
			"			(pbl_id, seq_no, '"+payee_id+"') not in (select pbl_id, seq_no, agent_code from cm_schedule_dl where status != 'I' and tran_type = 'BB' and promo_code = '"+lookupPromoCode.getText()+"') \n" + 
			"			or \n" + 
			"			(entity_id = '7362598627' and '"+payee_id+"' = '0000000016' and '"+lookupPromoCode.getText()+"' = '6') \n" +
			"			or \n" + 
			"			(entity_id = '9449886339' and '"+payee_id+"' = '0000000009' and '"+lookupPromoCode.getText()+"' = '6') \n" + 
			"			or \n" + 
			"			(entity_id = '7362598627' and '"+payee_id+"' = '0000000051' and '"+lookupPromoCode.getText()+"' = '6') \n" +			
			"		) \n" +
			") a \n" + 
			"left join rf_entity b on a.entity_id = b.entity_id\r\n" + 
			"left join mf_project c on a.projcode = c.proj_id\r\n" + 
			"left join mf_unit_info d on a.projcode = d.proj_id and a.pbl_id = d.pbl_id\r\n" + 
			"left join mf_record_status e on a.status_id = e.status_id\r\n" +
			"left join mf_sellingagent_info f on a.sellingagent = f.agent_id \n" +
			
			"where a.pbl_id is not null \n";
			
			if (payeeID.equals("0000000016")||payeeID.equals("0000000012")||payeeID.equals("0000000009")||payeeID.equals("0000000010")||payeeID.equals("0000000024"))				
			{sql = sql + "and f.sales_div_id in (select div_code from em_employee where entity_id = '"+lookupAgent.getText()+"' limit  1) ";} else 				
			{sql = sql + "and a.mktgarm in (select dept_code from em_employee where entity_id = '"+lookupAgent.getText()+"' limit  1) ";}			
			
			if (promo_type.equals("TR"))
			{
				sql = sql + " and (a.pbl_id,a.seq_no) in (select distinct on (pbl_id, seq_no) pbl_id, seq_no  " +
						"from rf_buyer_status where byrstatus_id = '17' and status_id = 'A') \n";
			}
			else if (promo_type.equals("OR"))
			{
				sql = sql + " and (a.pbl_id,a.seq_no) in (select distinct on (pbl_id, seq_no) pbl_id, seq_no  " +
						"from rf_buyer_status where byrstatus_id = '01' and status_id = 'A') \n";
			}
			else {}
			
			sql = sql +
			"and (case when '"+phase_id+"' = '' then true else d.sub_proj_id = '"+phase_id+"' end)  " +
			"order by a.pbl_id::int, a.seq_no desc \r\n" + 
			") a order by a.client \r\n"  ;	
			
			System.out.printf("displayClientList: " + sql);
		pgSelect db = new pgSelect();
		db.select(sql);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());				
			}	
		}	

		tblClientList.packAll();
		adjustRowHeight_account();

	}

	/*private void displayClientList_givenCDF(DefaultTableModel modelMain, JList rowHeader, String cdf_no) {//used

		FncTables.clearTable(modelMain);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		String sql = 			
			"select  \r\n" + 
			"			 \r\n" + 
			"			true, \r\n" + 
			"			c.proj_alias, \r\n" + 
			"			trim(d.description), \r\n" + 
			"			upper(trim(b.entity_name)) as client, \r\n" + 
			"			a.account_code, \r\n" + 
			"			trim(a.pbl_id) as pbl_id, \r\n" + 
			"			a.seq_no, \r\n" + 
			"			e.status_desc \r\n" + 
			"			 \r\n" + 
			"			from ( select * from cm_cdf_dl where cdf_no = '"+cdf_no+"' and tran_type = 'BB' ) a \r\n" + 
			"			left join rf_entity b on a.account_code = b.entity_id \r\n" + 
			"			left join mf_project c on a.projcode = c.proj_id \r\n" + 
			"			left join rf_sold_unit f on a.pbl_id=f.pbl_id  and a.seq_no=f.seq_no\r\n" + 
			"			left join mf_unit_info d on a.pbl_id = d.pbl_id \r\n" + 
			"			left join mf_record_status e on f.status_id = e.status_id \r\n" + 
			"			\r\n" + 
			"			order by c.proj_alias, a.pbl_id::int, d.description " ;

		System.out.printf("getPromoAccount: " + sql);
		pgSelect db = new pgSelect();
		db.select(sql);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());				
			}	
		}		

		else {}

		tblClientList.packAll();
		adjustRowHeight_account();

	}*/	

	private void setPromoHeader(String cdf_no){//used

		Object[] prm_hrd = getPromoDetails(cdf_no);	

		String agent_id 	= (String) prm_hrd[0];
		String agent_name 	= (String) prm_hrd[1];
		String promo_code 	= (String) prm_hrd[2];
		String promo_desc 	= (String) prm_hrd[3];

		Double grs_amt		= Double.parseDouble(prm_hrd[9].toString());
		Double tax_rate = sql_getTaxRate(agent_id)/100;

		Double wtax_other_amt	= tax_rate*grs_amt;
		Double net_amt		= grs_amt - wtax_other_amt;
		String remarks 		= (String) prm_hrd[10];
		String in_kind_code	= (String) prm_hrd[11];

		lookupAgent.setValue(agent_id);
		tagAgent.setTag(agent_name);
		lookupPromoCode.setValue(promo_code);
		tagPromoCode.setTag(promo_desc);

		try { txtProdTarget.setText(nf.format(Double.parseDouble(prm_hrd[8].toString()))); } catch (NullPointerException e) {  txtProdTarget.setText("0.00"); }
		try { dteProdDateFrom.setDate((Date)prm_hrd[4]); }	catch (NullPointerException e) {  dteProdDateFrom.setDate(null); }
		try { dateProdDateTo.setDate((Date)prm_hrd[5]); }	catch (NullPointerException e) {  dateProdDateTo.setDate(null); }
		try { dteSuppDateFrom.setDate((Date)prm_hrd[6]); }	catch (NullPointerException e) {  dteSuppDateFrom.setDate(null); }
		try { dateSuppDateTo.setDate((Date)prm_hrd[7]); }	catch (NullPointerException e) {  dateSuppDateTo.setDate(null); }

		txtGrossAmount.setText(nf.format(grs_amt));
		txtWtax.setText(nf.format(wtax_other_amt));
		txtNetAmount.setText(nf.format(net_amt));

		txtPromoRemark.setText(remarks);
		lookupInKind.setValue(in_kind_code);


	}

	private void setSingleAccountCode(String rec_id){//used

		Object[] cdf_acct = getPromoAccount(rec_id);	


		try { entity_id	= (String) cdf_acct[0]; }	catch (NullPointerException e) { entity_id	= ""; }
		try { proj_code		= (String) cdf_acct[2]; }	catch (NullPointerException e) {  proj_code		=  ""; }
		try { pbl_id	= (String) cdf_acct[6]; }	catch (NullPointerException e) { pbl_id	=  ""; }
		try { seq_no	= cdf_acct[7].toString(); }	catch (NullPointerException e) { seq_no	=  ""; }

		String account_name = "";	
		String proj_alias 	= "";
		String proj_alias2 	= "";
		String unit_desc 	= "";	

		try { account_name = (String) cdf_acct[1]; }	catch (NullPointerException e) { account_name	=  ""; }
		try { proj_alias 	= (String) cdf_acct[3]; }	catch (NullPointerException e) { proj_alias		=  ""; }
		try { proj_alias2 	= (String) cdf_acct[4];}	catch (NullPointerException e) { proj_alias2	=  ""; }
		try { unit_desc 	= (String) cdf_acct[5]; }	catch (NullPointerException e) { unit_desc	    =  ""; }

		lookupClient.setValue(entity_id);
		tagClientName.setTag(account_name);
		tagProjAlias.setTag(proj_alias2);
		tagClientUnitDesc.setTag(unit_desc);
		lookupAgentProject.setValue(proj_code);
		tagProj.setTag(proj_alias);

	}


	//Enable/Disable all components inside JPanel
	/*private void setComponentEnabled(JPanel panel, boolean enable) {
		for(Component comp : panel.getComponents()){
			comp.setEnabled(enable);
		}
	}
*/
	private void enable_Payee(Boolean x){		
		tagAgent.setEnabled(x);	
		lblOtherPayeeProj.setEnabled(x);
		rbtnAgent.setEnabled(x);
		lookupAgent.setEnabled(x);
		rbtnOtherPayee.setEnabled(x);
		lookupOtherPayee.setEnabled(x);
		tagOtherPayee.setEnabled(x);
	}
	
	private void enable_leftPanel(Boolean x){   

		/*tagAgent.setEnabled(x);	
		lblOtherPayeeProj.setEnabled(x);
		rbtnAgent.setEnabled(x);
		lookupAgent.setEnabled(x);
		rbtnOtherPayee.setEnabled(x);
		lookupOtherPayee.setEnabled(x);
		tagOtherPayee.setEnabled(x);*/
		
		lookupAgentProject.setEnabled(x);
		txtProdTarget.setEnabled(x);
		lblTranDate.setEnabled(x);
		lblPromoCode.setEnabled(x);	
		lookupPromoCode.setEnabled(x);
		tagPromoCode.setEnabled(x);	

		//left panel
		tagProj.setEnabled(x);			
		lblPromoCode.setEnabled(x);	
		tagPromoCode.setEnabled(x);	
		lblProdDateFrom.setEnabled(x);		
		lblProdDateTo.setEnabled(x);	
		lblSuppDateFrom.setEnabled(x);		
		lblSuppDateTo.setEnabled(x);	
		lblProdTarget.setEnabled(x);	
		lblOtherPayeePhase.setEnabled(x);	
		lookupPhase.setEnabled(x);	
		tagPhase.setEnabled(x);	

		//south panel	
		tagInKind.setEnabled(x);	
		tblInKind.setEnabled(x);
		
		dteProdDateFrom.setEnabled(x);			
		dateProdDateTo.setEnabled(x);
		dteSuppDateFrom.setEnabled(x);		
		dateSuppDateTo.setEnabled(x);
		txtProdTarget.setEnabled(x);
		
		txtPromoRemark.setEditable(x);
		txtPromoRemark.setEnabled(x);	

	}
	
	private void enable_rightPanel(Boolean x){   

		lblTranDate.setEnabled(x);

		//right panel
		rbtnClient.setEnabled(x);
		rbtnClientList.setEnabled(x);
		lookupClient.setEnabled(x);
		
		tagClientName.setEnabled(x);	
		lblClientProject.setEnabled(x);
		tagProjAlias.setEnabled(x);	
		lblClientUnit.setEnabled(x);
		tagClientUnitDesc.setEnabled(x);
		tblClientList.setEnabled(x); 
		lblGrossAmt.setEnabled(x);		
		lblWtax.setEnabled(x);	
		lblNetAmount.setEnabled(x);	

		//south panel	
		tagInKind.setEnabled(x);	
		tblInKind.setEnabled(x);
		
		txtGrossAmount.setEnabled(x);	
		txtGrossAmount.setEditable(x);	
		txtWtax.setEnabled(x);	
		txtWtax.setEditable(false);	
		txtNetAmount.setEnabled(x);	
		txtNetAmount.setEditable(false);	
		cBtnInKind.setEnabled(x);	
		lookupInKind.setEnabled(x);

	}

	private void enable_lookup_datechoosers(Boolean x){   

		//right panel
		rbtnClient.setEnabled(x);
		lookupClient.setEnabled(x);	
		rbtnClientList.setEnabled(x);
		tblClientList.setEnabled(x); 	
		txtGrossAmount.setEnabled(x);	
		txtWtax.setEnabled(x);	
		txtNetAmount.setEnabled(x);	
		lookupOtherPayee.setEnabled(x);

		//left panel
		rbtnAgent.setEnabled(x);
		lookupAgent.setEnabled(x);
		tagAgent.setEnabled(x);	
		rbtnOtherPayee.setEnabled(x);
		//lookupOtherPayee.setEnabled(x);
		lookupAgentProject.setEnabled(x);	
		lookupPromoCode.setEnabled(x);				
		dteProdDateFrom.setEnabled(x);		
		dateProdDateTo.setEnabled(x);		
		dteSuppDateFrom.setEnabled(x);		
		dateSuppDateTo.setEnabled(x);
		txtProdTarget.setEnabled(x);	

		//south panel
		txtPromoRemark.setEditable(x);
		txtPromoRemark.setEnabled(x);	
		cBtnInKind.setEnabled(x);	
		lookupInKind.setEnabled(x);
	}

	private void refresh_fields(){  

		lookupCDF.setValue("");
		dteTransaction.setDate(Calendar.getInstance().getTime());
		tagCDF.setTag("");

		rbtnClient.setSelected(true);
		lookupClient.setValue("");
		tagClientName.setText("[ ]");
		tagProjAlias.setText("[ ]");
		tagClientUnitDesc.setText("[ ]");

		rbtnClientList.setSelected(false);
		FncTables.clearTable(modelClientList);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeaderClientList.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		txtGrossAmount.setText("0.00");
		txtWtax.setText("0.00");
		txtNetAmount.setText("0.00");
		txtPromoRemark.setText("");	

		rbtnAgent.setSelected(true);
		lookupAgent.setValue("");
		tagAgent.setTag("");
		rbtnOtherPayee.setSelected(false);
		lookupOtherPayee.setValue("");
		lookupAgentProject.setValue("");
		tagOtherPayee.setTag("");
		tagProj.setTag("");
		
		lblOtherPayeePhase.setEnabled(false);	
		lookupPhase.setEnabled(false);	
		tagPhase.setEnabled(false);	
		lookupPhase.setValue("");
		tagPhase.setTag("");

		lookupPromoCode.setValue("");
		tagPromoCode.setText("[ ]");
		dteProdDateFrom.setDate(null);
		dateProdDateTo.setDate(null);
		dteSuppDateFrom.setDate(null);
		dateSuppDateTo.setDate(null);		
		txtProdTarget.setText("0.00");

		//cBtnInKind.setSelected(false);
		lookupInKind.setValue("");
		tagInKind.setText("[ ]");
		FncTables.clearTable(modelInKind);//Code to clear modelMain.		
		DefaultListModel listModel2 = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeaderInKind.setModel(listModel2);//Setting of DefaultListModel into rowHeader.
		cBtnInKind.setEnabled(true);	


	}

	private void enableButtons( Boolean a, Boolean b, Boolean c, Boolean d, Boolean untag){

		btnAddNew.setEnabled(a);
		btnEdit.setEnabled(b);
		btnSave.setEnabled(c);
		btnCancel.setEnabled(d);
		btnUntag.setEnabled(untag);
	}

	private void resetVariables(){

		//reset variables
		proj_code 	= "";
		pbl_id 		= "";
		seq_no 		= "";
		promo_type	= "";
		rec_id      = null;
		to_do		= "addnew";
		entity_id   = "";
		acct_id		= "";
		release_to	= "";
		for_disbproc = true;	
		phase_id    = "";
		phase_no	= "";
	}

	private void initialize_comp(){		

		co_id 		= "02";	

		tagCompany.setTag("CENQHOMES DEVELOPMENT CORPORATION");

		lblCDF_no.setEnabled(true);	
		lookupCDF.setEnabled(true);	

		KEYBOARD_MANAGER.focusNextComponent();
		enableButtons(true, false, false, false, false);
		lookupCompany.setValue(co_id);
	}


	//action performed
	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getActionCommand().equals("Refresh")){}

		if(e.getActionCommand().equals("Cancel")){ 
			cancel(); 
		}

		if (e.getActionCommand().equals("Add")&& FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "8")==true) { 
			addnew(); 
		}else if (e.getActionCommand().equals("Add")&& FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "8")==false) {
			JOptionPane.showMessageDialog(getContentPane(),"Sorry, you are not authorized to create new promo/incentive.","Warning",JOptionPane.WARNING_MESSAGE); 
		}

		if (e.getActionCommand().equals("Save")&&to_do.equals("addnew")&& FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "8")==true) { 
			save(); 
		}else if (e.getActionCommand().equals("Save")&&to_do.equals("addnew")&& FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "8")==false){
			JOptionPane.showMessageDialog(getContentPane(),"Sorry, you are not authorized to create promo/incentive.","Warning",JOptionPane.WARNING_MESSAGE); 
		}
		
		if (e.getActionCommand().equals("Save")&&to_do.equals("edit")&& FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "8")==true) { 
			//update();
			updateCommSchedManual(lookupCDF.getValue());
			cancel();
			JOptionPane.showMessageDialog(getContentPane(), "Update successful", "Save", JOptionPane.INFORMATION_MESSAGE);
		}else if (e.getActionCommand().equals("Save")&&to_do.equals("edit")&& FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "8")==false){
			JOptionPane.showMessageDialog(getContentPane(),"Sorry, you are not authorized to edit promo/incentive.","Warning",JOptionPane.WARNING_MESSAGE); 
		}

	}

	public void mouseClicked(MouseEvent evt) {
		if ((evt.getClickCount() >= 2)) {

		}
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

	private void addnew(){

		enable_Payee(true);
		lookupOtherPayee.setEnabled(false);
		//enable_rightPanel(false);
		//enable_lookup_datechoosers(true);
		enableButtons(false, false, true, true, false);
		refresh_fields();
		lblCDF_no.setEnabled(false);	
		lookupCDF.setEnabled(false);	
		lookupCDF.setValue("");	
		to_do = "addnew";

	}

	private void cancel(){

		if(!btnSave.isEnabled())  {	
			enable_Payee(false);
			enable_leftPanel(false);
			enable_rightPanel(false);
			refresh_fields();
			lblCDF_no.setEnabled(true);	
			lookupCDF.setEnabled(true);	
			lookupCDF.setValue("");	
			enableButtons(true, false, false, false, false);
			resetVariables();
		}

		else {
			if (JOptionPane.showConfirmDialog(getContentPane(), "Cancel unsaved data?", "Cancel", 
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
				enable_Payee(false);
				enable_leftPanel(false);	
				enable_rightPanel(false);
				refresh_fields();
				lblCDF_no.setEnabled(true);	
				lookupCDF.setEnabled(true);	
				lookupCDF.setValue("");	
				enableButtons(true, false, false, false, false);
				resetVariables();
			}
		}



	}

	private void save(){

		if(checkCompleteDetails()==false)
		{JOptionPane.showMessageDialog(getContentPane(), "Please enter complete promo details.", "Incomplete Details", 
				JOptionPane.WARNING_MESSAGE);}
		else {	

			if(rbtnClientList.isSelected()==true&&noTaggedClient()==true){
				JOptionPane.showMessageDialog(getContentPane(), "Please select client(s) from the Unit List table.", "Incomplete Details", 
					JOptionPane.WARNING_MESSAGE);
			}else if (rbtnClientList.isSelected()==true&&noTaggedClient()==false) {

				if(lookupAgentProject.getText().equals("")){
					JOptionPane.showMessageDialog(getContentPane(), "Please enter agent's project", "Missing Agent Project", 
						JOptionPane.WARNING_MESSAGE);
				}else {
					if (JOptionPane.showConfirmDialog(getContentPane(), "Are all entries correct?", "Confirmation", 
							JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) 
					{	
						pgUpdate db = new pgUpdate();
						insertSched_detail_batch(db);
						db.commit();	
						JOptionPane.showMessageDialog(getContentPane(),"New Promo/Incentive details saved.","Information",JOptionPane.INFORMATION_MESSAGE);					
						btnSave.setEnabled(false);
						lblCDF_no.setEnabled(true);	
						lookupCDF.setValue(rec_id.toString());
						lookupCDF.setEnabled(true);	
						enable_leftPanel(false);
						enable_lookup_datechoosers(false);
						resetVariables();
						enableButtons(false, true, false, true, false);
						tagCDF.setTag("CDF No.: null");
					}
				}
			}else if (rbtnClient.isSelected()==true&&lookupClient.getText().equals("")) {	
				
				if (lookupPromoCode.getText().equals("2")||lookupPromoCode.getText().equals("1"))
				{
					if(lookupAgentProject.getText().equals(""))
					{JOptionPane.showMessageDialog(getContentPane(), "Please enter agent's project", "Missing Agent Project", 
							JOptionPane.WARNING_MESSAGE);}
					else {execute_save_indiv();}
				}			
				else {
					{JOptionPane.showMessageDialog(getContentPane(), "A client has not been selected yet.", "Incomplete Details", 
							JOptionPane.WARNING_MESSAGE);}
				}

				/*else if (JOptionPane.showConfirmDialog(getContentPane(), "A client has not been selected yet. Proceed anyway?", "Cancel", 
						JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
					if(lookupAgentProject.getText().equals(""))
					{JOptionPane.showMessageDialog(getContentPane(), "Please enter agent's project", "Missing Agent Project", 
							JOptionPane.WARNING_MESSAGE);}
					else {execute_save_indiv();}
				}*/
			}

			else if (rbtnClient.isSelected()==true&&!lookupClient.getText().equals("")) 
			{
				if(lookupAgentProject.getText().equals(""))
				{JOptionPane.showMessageDialog(getContentPane(), "Please enter agent's project", "Missing Agent Project", 
						JOptionPane.WARNING_MESSAGE);}
				else {execute_save_indiv();}
			}
		}
	}

	private void execute_save_indiv(){

		if (JOptionPane.showConfirmDialog(getContentPane(), "Are all entries correct?", "Confirmation", 
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) 
		{				
			pgUpdate db = new pgUpdate();
			insertSched_detail(db);
			db.commit();	
			JOptionPane.showMessageDialog(getContentPane(),"New Promo/Incentive details saved.","Information",JOptionPane.INFORMATION_MESSAGE);	
			lblCDF_no.setEnabled(true);	
			lookupCDF.setValue(rec_id.toString());
			lookupCDF.setEnabled(true);	
			enable_leftPanel(false);
			resetVariables();
			enable_lookup_datechoosers(false);
			enableButtons(false, true, false, true, false);
			tagCDF.setTag("CDF No.: null");
		}
	}

	private void update(){

		if(checkCompleteDetails()==false){
			JOptionPane.showMessageDialog(getContentPane(), "Please enter complete promo details.", "Incomplete Details", 
				JOptionPane.WARNING_MESSAGE);
		}else{			

			if(lookupClient.getText().trim().equals("")){
				if (JOptionPane.showConfirmDialog(getContentPane(), "A client has not been selected yet. Proceed anyway?", "Cancel", 
						JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

					if(lookupAgentProject.getText().equals("")){
						JOptionPane.showMessageDialog(getContentPane(), "Please enter agent's project", "Missing Agent Project", JOptionPane.WARNING_MESSAGE);
					}else{ 
						execute_update();
					}
				}else {
					if(lookupAgentProject.getText().equals("")){
						JOptionPane.showMessageDialog(getContentPane(), "Please enter agent's project", "Missing Agent Project", JOptionPane.WARNING_MESSAGE);
					}else{ 
						execute_update();
					}
				}
			}else { 
				execute_update();
			}
		}
	}

	private void execute_update(){
		if (JOptionPane.showConfirmDialog(getContentPane(), "Are all entries correct?", "Confirmation", 
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) 
		{	
			pgUpdate db = new pgUpdate();
			updateSchedDetail(db);
			db.commit();	
			JOptionPane.showMessageDialog(getContentPane(),"New Promo/Incentive details updated.","Information",JOptionPane.INFORMATION_MESSAGE);
			lblCDF_no.setEnabled(true);				
			lookupCDF.setEnabled(true);	
			enable_leftPanel(false);
			enable_lookup_datechoosers(false);
			resetVariables();
			enableButtons(false, true, false, true, false);
			tagCDF.setTag("CDF No.: null");				
		}
	}


	//select, lookup and get statements	
	private String getClient(String x){//used
		
		String sql = 
			
		"select distinct on (a.pbl_id::int) \r\n" + 
		"\r\n" + 
		"a.entity_id  as \"Entity ID\",\r\n" + 
		"upper(trim(b.entity_name)) as \"Client\",\r\n" + 		
		"a.pbl_id as \"PBL ID\",\r\n" + 
		"a.seq_no as \"Seq No.\",\r\n" + 
		"c.proj_alias as \"Proj Alias\" ,\r\n" + 
		"trim(d.description) as \"Unit\",\r\n" + 
		"e.status_desc as \"Status\", \r\n" +
		"c.proj_id as \"Proj Code\" " + 
		"\r\n" + 
		"from (select * from rf_sold_unit where (pbl_id::int, seq_no) " +
		"	not in (select pbl_id, seq_no from canceled_accounts --where canc_date::date <= '"+dateProdDateTo.getDate()+"' \n" +
				") " +  //unit not yet canceled
		"	and (pbl_id, seq_no, '"+x+"')  not in (select pbl_id, seq_no, agent_code from cm_schedule_dl " +  //no the same agent, pbl_id, seq_no, promo_code can have double promo disbursement
		"		where tran_type = 'BB' and promo_code = '"+lookupPromoCode.getText()+"')" +
		"	and (pbl_id, seq_no) in (select pbl_id, seq_no from rf_buyer_status where byrstatus_id = '17' \r\n" + 
		"		--and tran_date >= '"+dteProdDateFrom.getDate()+"' \r\n" + 
		"		--and tran_date <= '"+dateProdDateTo.getDate()+"' \n" +
				" ) \n" +
				")a\r\n" +  //status != 'I' and 
		"left join rf_entity b on a.entity_id = b.entity_id\r\n" + 
		"left join mf_project c on a.projcode = c.proj_id\r\n" + 
		"left join mf_unit_info d on a.pbl_id = d.pbl_id and a.projcode = d.proj_id \r\n" + 
		"left join mf_record_status e on a.status_id = e.status_id\r\n" +
		"where a.pbl_id is not null  \n" ;
		
		if (rbtnAgent.isSelected()==true)
		{
			sql = sql + " and a.sellingagent = '"+x+"' \n" +  //Agent Mismo
					"or a.sellingagent in (select a.agent_id \n" + //Agent's Downline
					"	from mf_sellingagent_info a \r\n" + 
					"	where trim(a.override_id) = '"+x+"') " +
					"\n";
		}
		else {}
		
		if (promo_type.equals("TR"))
		{
			sql = sql + " and (a.pbl_id,a.seq_no) in (select distinct on (pbl_id, seq_no) pbl_id, seq_no  " +
					"from rf_buyer_status where byrstatus_id = '17' and status_id = 'A') \n";
		}
		else if (promo_type.equals("OR"))
		{
			sql = sql + " and (a.pbl_id,a.seq_no) in (select distinct on (pbl_id, seq_no) pbl_id, seq_no  " +
					"from rf_buyer_status where byrstatus_id = '01' and status_id = 'A') \n";
		}
		else {}
		
		sql = sql +
		
		"order by a.pbl_id::int, a.seq_no desc \r\n" + 
		"\r\n"  ;	
		
		System.out.printf("getClient: " + sql);
		return sql;
	}
	
	private String getClient_BDO(String x){//used
		
		String sql = 
			
		"select --distinct on (a.pbl_id::int) \r\n" + 
		"\r\n" + 
		"a.entity_id  as \"Entity ID\",\r\n" + 
		"upper(trim(b.entity_name)) as \"Client\",\r\n" + 		
		"a.pbl_id as \"PBL ID\",\r\n" + 
		"a.seq_no as \"Seq No.\",\r\n" + 
		"c.proj_alias as \"Proj Alias\" ,\r\n" + 
		"trim(d.description) as \"Unit\",\r\n" + 
		"e.status_desc as \"Status\", \r\n" +
		"c.proj_id as \"Proj Code\" " + 
		"\r\n" + 
		"from (select * from rf_sold_unit \n" +
		"	--where (pbl_id::int, seq_no) not in (select pbl_id, seq_no from canceled_accounts where canc_date::date <= '"+dateProdDateTo.getDate()+"') \n" +  //unit not yet canceled
		"	where (pbl_id, seq_no, '"+x+"')  not in (select pbl_id, seq_no, agent_code from cm_schedule_dl " +  //no the same agent, pbl_id, seq_no, promo_code can have double promo disbursement
		"		where tran_type = 'BB' and promo_code = '"+lookupPromoCode.getText()+"')" +  //status != 'I' and 
		"	and (pbl_id, seq_no) in (select pbl_id, seq_no from rf_buyer_status where byrstatus_id = '17' \r\n" + 
		"		--and tran_date >= '"+dteProdDateFrom.getDate()+"' \r\n" + 
		"		--and tran_date <= '"+dateProdDateTo.getDate()+"' \n" +
				")" +
		")a\r\n" + 
		"left join rf_entity b on a.entity_id = b.entity_id\r\n" + 
		"left join mf_project c on a.projcode = c.proj_id\r\n" + 
		"left join mf_unit_info d on a.pbl_id = d.pbl_id and a.projcode = d.proj_id \r\n" + 
		"left join mf_record_status e on a.status_id = e.status_id\r\n" +
		"left join mf_sellingagent_info f on a.sellingagent = f.agent_id \n" +
		
		"where a.pbl_id is not null  \n" ;
		if (x.equals("0000000016")||x.equals("0000000012")||x.equals("0000000009")||x.equals("0000000010")||x.equals("0000000024"))				
		{sql = sql + "and f.sales_div_id in (select div_code from em_employee where entity_id = '"+lookupAgent.getText()+"' limit  1) ";} else 				
		{sql = sql + "and f.dept_id in (select dept_code from em_employee where entity_id = '"+lookupAgent.getText()+"' limit  1) ";}			
		
		if (promo_type.equals("TR"))
		{
			sql = sql + " and (a.pbl_id,a.seq_no) in (select distinct on (pbl_id, seq_no) pbl_id, seq_no  " +
					"from rf_buyer_status where byrstatus_id = '17' and status_id = 'A') \n";
		}
		else if (promo_type.equals("OR"))
		{
			sql = sql + " and (a.pbl_id,a.seq_no) in (select distinct on (pbl_id, seq_no) pbl_id, seq_no  " +
					"from rf_buyer_status where byrstatus_id = '01' and status_id = 'A') \n";
		}
		else {}
		
		sql = sql +
			
		"order by a.pbl_id::int, a.seq_no desc \r\n" + 
		"\r\n"  ;	
		
		System.out.printf("getClient: " + sql);
		return sql;
	}

	private String getAgent(){//used
		return 
		"select \r\n" + 
		"\r\n" + 
		"a.agent_id as \"Agent ID\" ,\r\n" + 
		"upper(trim(b.entity_name)) as \"Agent Name\" \r\n" + 
		"\r\n" + 
		"from mf_sellingagent_info a\r\n" + 
		"left join rf_entity b on a.agent_id = b.entity_id " +
		"order by b.entity_name "  ;		
	}

	private String getOtherPayee(){//used
		return 
		"select " +
		"entity_id as \"Entity\", " +
		"upper(trim(entity_name)) as \"Entity Name\", " +
		"vat_registered as \"Vatable\" from rf_entity \n" +
		"where entity_id not in (select agent_id from mf_sellingagent_info) " +
		"and entity_id in (select entity_id from em_employee) " +
		"order by entity_name  \n";		
	}

	private String getPromoCode(){//used
		return 
		"select a.promo_code as \"Code\", " +
		"trim(a.promo_desc) as \"Description\"," +
		"(case when a.promo_type = '' then 'All' else " +
		"   case when  a.promo_type = 'A' then 'TR' else " +
		"   case when  a.promo_type = 'B' then 'OR' else '' end end end) as \"Promo Type\", " +
		"(case when a.frequency = 'O' then 'Once' else\r\n" + 
		"	case when a.frequency = 'M' then 'Monthly' else\r\n" + 
		"	case when a.frequency = 'S' then 'Semi-Annual' else\r\n" + 
		"	case when a.frequency = 'Y' then 'Yearly' else '' end end end end) as \"Frequency\",\r\n" + 		"  " +
		"(case when a.release_to = '' then 'All' else \r\n" + 
		"	case when a.release_to = 'A' then 'Agents' else\r\n" + 
		"	case when a.release_to = 'C' then 'Coordinator' else  \r\n" + 
		"	case when a.release_to = 'B' then 'In-House Brokers' else  \r\n" + 
		"	case when a.release_to = 'O' then 'Overriding' else '' end end end end end) as \"Release To\",\r\n" + 
		"(case when a.release_group = '' then 'All' else \r\n" + 
		"	case when a.release_group = '001' then 'In-House' else\r\n" + 
		"	case when a.release_group = '002' then 'Broker' else '' end end end) as \"Release Group\", \r\n" +
		"coalesce(a.amount,0) as \"Amount\"," +
		"trim(a.new_acct_id) as \"Account ID\", \n" +
		"trim(b.acct_name) as \"Account Name\" \n\n" + 
		
		"from cm_promo_bonus a \r\n" +
		"left join mf_boi_chart_of_accounts b on a.new_acct_id = b.acct_id \n" +
		"where a.status = 'A' " +
		"order by a.promo_code \n\n" ;		
	}

	private String getProject(String co_id){//used
		return 
		"select\r\n" + 
		"proj_id as\"Proj ID\",\r\n" + 
		"trim(proj_alias) as \"Proj Alias\",\r\n" + 
		"trim(proj_name)  as \"Proj Name\" \r\n" + 
		"from mf_project \r\n"+
		"where co_id = '"+co_id+"' and status_id = 'A'";
	}

	private String getPromo_list(){

		return 
		"select  \r\n" + 

		"a.rec_id,\r\n" + 
		"a.cdf_no,\r\n" + 
		"a.tran_date,\r\n" + 
		"upper(trim(b.entity_name)),\r\n" + 
		"a.comm_amt,\r\n" + 
		"c.date_released,\r\n" + 
		"c.date_paid\r\n" + 

		"from cm_schedule_dl a\r\n" + 
		"left join rf_entity b on a.agent_code = b.entity_id \r\n" + 
		"left join (select * from cm_cdf_hd where status_id != 'I') c on a.cdf_no = c.cdf_no\r\n" + 

		"where a.tran_type = 'BB' \r\n" +		
		"order by a.rec_id desc ";		
	}

	private Object [] getPromoDetails(String rec_id) {//used

		String strSQL = 
			"select  \r\n" + 

			"a.agent_code, \r\n" +   //0
			"upper(trim(b.entity_name)) as agent, \r\n" +  //1
			"a.promo_code, \r\n" + 			//2
			"trim(d.promo_desc), \r\n" + 	//3
			"a.production_fr, \r\n" + 		//4
			"a.production_to, \r\n" + 		//5
			"a.fund_fr, \r\n" + 			//6
			"a.fund_to, \r\n" + 			//7
			"a.production_target, \r\n" + 	//8
			"a.comm_amt,\r\n" + 			//9
			"trim(a.remarks), \r\n" + 		//10
			"a.in_kind_promo_code \r\n" + 	//11

			"from cm_schedule_dl a \r\n" + 
			"left join rf_entity b on a.agent_code = b.entity_id \r\n" + 
			"left join cm_promo_bonus d on a.promo_code = d.promo_code \r\n" + 
			"			\r\n" + 
			"where a.rec_id = "+rec_id+" \r\n" + 
			"and a.tran_type = 'BB'" ;		

		pgSelect db = new pgSelect();
		db.select(strSQL);		

		if(db.isNotNull()){
			return db.getResult()[0];
		}else{
			return null;
		}
	}	

	private Object [] getPromoAccount(String rec_id) {//used

		String strSQL = 
			"select\r\n" + 
			"a.account_code,\r\n" + 			//0
			"upper(trim(b.entity_name)),\r\n" + //1
			"c.proj_id," +				//2		
			"c.proj_alias," +			//3
			"e.proj_alias, \r\n" + 		//4
			"trim(d.description)\r\n," +//5	
			"a.pbl_id, \n" +			//6
			"a.seq_no \n" + 			//7		
			"\r\n" + 
			"from cm_schedule_dl a\r\n" + 
			"left join rf_entity b on a.account_code = b.entity_id\r\n" + 
			"left join mf_project c on a.projcode = c.proj_id\r\n" + 
			"left join mf_unit_info d on a.projcode = d.proj_id and a.pbl_id = d.pbl_id\r\n" + 
			"left join mf_project e on d.proj_id = e.proj_id \r\n" + 
			"\r\n" + 
			"where a.rec_id = "+rec_id+" " ;		

		System.out.printf("getPromoAccount: " + strSQL);
		pgSelect db = new pgSelect();
		db.select(strSQL);		

		if(db.isNotNull()){
			return db.getResult()[0];
		}else{
			return null;
		}
	}	

	private Double sql_getTaxRate(String entity_id) {		

		Double tax_rate = 0.00;
		String SQL = 
			"select \r\n" + 
			"\r\n" + 
			"c.wtax_rate\r\n" + 
			"\r\n" + 
			"from (select * from rf_entity_assigned_type where status_id = 'A' order by date_created desc) a \r\n" + 
			"left join mf_entity_type b on a.entity_type_id = b.entity_type_id\r\n" + 
			"left join rf_withholding_tax c on b.wtax_id = c.wtax_id\r\n" + 
			"\r\n" + 
			"where a.entity_id = '"+entity_id+"'\r\n" + 
			"and a.entity_type_id in ('03', '04', '14')" ;

		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			tax_rate = Double.parseDouble(db.getResult()[0][0].toString());
		}else{
			tax_rate = 0.00;
		}

		return tax_rate;
	}

	private String getBuyerType() {//used

		String strSQL = 			
			"select trim(buyertype) from rf_sold_unit where pbl_id = '"+pbl_id+"' and seq_no = "+seq_no+"  ";		

		System.out.printf("SQL - getCommSched: %s", strSQL);
		pgSelect db = new pgSelect();
		db.select(strSQL);		

		if(db.isNotNull()){
			return (String) db.getResult()[0][0];
		}else{
			return null;
		}
	}

	private String getSubproject(){
		String sql = 
			"select \r\n" + 
			"\r\n" + 
			"a.sub_proj_id,\r\n" + 
			"a.phase,\r\n" + 
			"a.proj_id,\r\n" + 
			"trim(b.proj_name) \r\n" + 
			"\r\n" + 
			"from mf_sub_project a\r\n" + 
			"left join mf_project b  on a.proj_id = b.proj_id \r\n" + 
			"\r\n" + 
			"where b.co_id = '"+co_id+"' and a.proj_id = '"+proj_code+"' AND a.status_id = 'A' \r\n" + 
			"\r\n" + 
			"order by a.sub_proj_id " ;
		return sql;
	}	
	
	/*private Integer getCommFrequency() {//used

		Integer comm_freq = 0;
		
		String strSQL = 			
			"select max(coalesce(frequency::int,0)) from cm_schedule_dl where pbl_id = '"+pbl_id+"' and seq_no = "+seq_no+"  ";		

		System.out.printf("SQL - getCommFrequency: %s", strSQL);
		pgSelect db = new pgSelect();
		db.select(strSQL);		

		if(db.isNotNull()){
			//return (Integer) db.getResult()[0][0];
			if((Integer) db.getResult()[0][0]==null||db.getResult()[0][0].equals("null")) {comm_freq = 0;}
			else {comm_freq = Integer.parseInt(db.getResult()[0][0].toString()); }
		}else{
			return comm_freq;
		}
		
		return comm_freq;
	}*/
	

	//table operations	
	private void adjustRowHeight_account(){//used
		int rw = tblClientList.getModel().getRowCount();
		int x = 0;

		while (x<rw){			
			tblClientList.setRowHeight(x, 22);			
			x++;
		}

	}


	//save and insert
	private void insertSched_detail(pgUpdate db){

		String agent_entity		= "";		
		String agent_id 		= lookupAgent.getText();		
		String other_payee_id 	= lookupOtherPayee.getText();		
		if (rbtnAgent.isSelected()) {agent_entity = agent_id; }
		else {agent_entity=other_payee_id; }	
		Double gr_amt  			= Double.valueOf(txtGrossAmount.getText().trim().replace(",","")).doubleValue();
		Double prod_amt  		= Double.valueOf(txtProdTarget.getText().trim().replace(",","")).doubleValue();

		String sqlDetail = 
			"insert into cm_schedule_dl " +
			"values ( " +
			"'"+lookupClient.getText().trim()+"',   " +
			"'"+proj_code+"',   " +
			"'"+pbl_id+"',   " ;

		if (entity_id.equals("")){sqlDetail = sqlDetail + "null," ; } else {sqlDetail = sqlDetail + ""+seq_no+",   " ;}

		sqlDetail = sqlDetail +

		"'"+agent_entity+"',   " +
		"'"+dteTransaction.getDate()+"',   " +
		"'BB',   " +
		"'BB',   " +
		"'"+lookupPromoCode.getText().trim()+"',   " +
		"null ,   " +
		"'0',   " +
		"'',   " +
		"0.00,   " +
		"0.00,   " +
		""+gr_amt+",   " +
		"0.00,   " +
		"0.00,   " +
		"'0',   " +
		"0,   " +
		"'A',   " +
		"1,   " +
		"'"+co_id+"',   " +
		"'"+co_id+"',   " +
		"null,   " +
		"'"+txtPromoRemark.getText().trim().replace("'","''")+"',   " +
		""+for_disbproc+",   " +
		"null,   " +
		"true,   " +
		"null,   " +
		"null,   " ;

		if (dteProdDateFrom.getDate()==null){sqlDetail = sqlDetail + "null, " ; } else {sqlDetail = sqlDetail + "'"+dateFormat.format(dteProdDateFrom.getDate())+"',   " ; }
		if (dateProdDateTo.getDate()==null){sqlDetail = sqlDetail + "null, " ; } else {sqlDetail = sqlDetail + "'"+dateFormat.format(dateProdDateTo.getDate())+"',   " ; }
		if (dteSuppDateFrom.getDate()==null){sqlDetail = sqlDetail + "null, " ; } else {sqlDetail = sqlDetail + "'"+dateFormat.format(dteSuppDateFrom.getDate())+"',   " ; }
		if (dateSuppDateTo.getDate()==null){sqlDetail = sqlDetail + "null, " ; } else {sqlDetail = sqlDetail + "'"+dateFormat.format(dateSuppDateTo.getDate())+"',   " ; }

		sqlDetail = sqlDetail +

		""+prod_amt+",   " +
		"true,   " +  //temporary
		"null,   " ;

		if (entity_id.equals("")){sqlDetail = sqlDetail + "''," ; } else {sqlDetail = sqlDetail +"'"+getBuyerType()+"', ";}

		sqlDetail = sqlDetail +
		"false,   " +
		"true   " +
		") " ;

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);	
	}	

	private void insertSched_detail_batch(pgUpdate db){

		Integer x 		= 0;
		Integer y 		= 0;		

		//create CDF 
		while ( x < modelClientList.getRowCount()) {

			Boolean isTrue = false;
			if(tblClientList.getValueAt(x,0) instanceof String){isTrue = new Boolean((String)tblClientList.getValueAt(x,0));}
			if(tblClientList.getValueAt(x,0) instanceof Boolean){isTrue = (Boolean)tblClientList.getValueAt(x,0);}

			if(isTrue){			
				String agent_entity		= "";		
				String agent_id 		= lookupAgent.getText();		
				String other_payee_id 	= lookupOtherPayee.getText();		
				if (rbtnAgent.isSelected()) {agent_entity = agent_id; }
				else {agent_entity=other_payee_id; }	
				Double gr_amt  			= Double.valueOf(txtGrossAmount.getText().trim().replace(",","")).doubleValue();
				Double prod_amt  		= Double.valueOf(txtProdTarget.getText().trim().replace(",","")).doubleValue();

				String entity_id 		= tblClientList.getValueAt(x,4).toString().trim();
				proj_code				= tblClientList.getValueAt(x,8).toString().trim();
				pbl_id 					= tblClientList.getValueAt(x,5).toString().trim();
				seq_no 					= tblClientList.getValueAt(x,6).toString().trim();

				String sqlDetail = 
					"insert into cm_schedule_dl " +
					"values ( " +
					"'"+entity_id+"',   " +
					"'"+proj_code+"',   " +
					"'"+pbl_id+"',   " +
					""+seq_no+",   " +
					"'"+agent_entity+"',   " +
					"'"+dteTransaction.getDate()+"',   " +
					"'BB',   " +
					"'BB',   " +
					"'"+lookupPromoCode.getText().trim()+"',   " +
					"null ,   " +
					"'0',   " +
					"'',   " +
					"0.00,   " +
					"0.00,   " +
					""+gr_amt+",   " +
					"0.00,   " +
					"0.00,   " +
					"'0',   " +
					"0,   " +
					"'A',   " +
					"1,   " +
					"'"+co_id+"',   " +
					"'"+co_id+"',   " +
					"null,   " +
					"'"+txtPromoRemark.getText().trim().replace("'","''")+"',   " +
					""+cBtnInKind.isSelected()+",   " +
					"null,   " +
					"true,   " +
					"null,   " +
					"null,   " ;

				if (dteProdDateFrom.getDate()==null){sqlDetail = sqlDetail + "null, " ; } else {sqlDetail = sqlDetail + "'"+dateFormat.format(dteProdDateFrom.getDate())+"',   " ; }
				if (dateProdDateTo.getDate()==null){sqlDetail = sqlDetail + "null, " ; } else {sqlDetail = sqlDetail + "'"+dateFormat.format(dateProdDateTo.getDate())+"',   " ; }
				if (dteSuppDateFrom.getDate()==null){sqlDetail = sqlDetail + "null, " ; } else {sqlDetail = sqlDetail + "'"+dateFormat.format(dteSuppDateFrom.getDate())+"',   " ; }
				if (dateSuppDateTo.getDate()==null){sqlDetail = sqlDetail + "null, " ; } else {sqlDetail = sqlDetail + "'"+dateFormat.format(dateSuppDateTo.getDate())+"',   " ; }

				sqlDetail = sqlDetail +

				""+prod_amt+",   " +
				"true,   " +  //temporary
				"null,   " +
				"'"+getBuyerType()+"',   " +  
				"false,   " +
				"true   " +
				") " ;

				System.out.printf("SQL #1: %s", sqlDetail);
				db.executeUpdate(sqlDetail, false);	

				y++;
			}

			x++;
		}
	}

	private void updateSchedDetail(pgUpdate db){

		Double gr_amt  			= Double.valueOf(txtGrossAmount.getText().trim().replace(",","")).doubleValue();
		Double prod_amt  		= Double.valueOf(txtProdTarget.getText().trim().replace(",","")).doubleValue();

		String sqlDetail = 
			"update cm_schedule_dl set " +
			"account_code = '"+lookupClient.getText().trim()+"',   " +
			"projcode = '"+proj_code+"',   " +
			"pbl_id = '"+pbl_id+"',   " ;
		if (entity_id.trim().equals("")){sqlDetail = sqlDetail + "seq_no = null," ; } else {sqlDetail = sqlDetail + "seq_no = "+seq_no+",   " ;}
		sqlDetail = sqlDetail +
		"promo_code = '"+lookupPromoCode.getText().trim()+"',   " +
		"comm_amt = "+gr_amt+",   " +
		"co_id = '"+co_id+"',   " +
		"busunit_id = '"+co_id+"',   " +
		"remarks = '"+txtPromoRemark.getText().trim().replace("'","''")+"',   " ;

		if (dteProdDateFrom.getDate()==null){sqlDetail = sqlDetail + "production_fr = null, " ; } else {sqlDetail = sqlDetail + "production_fr = '"+dateFormat.format(dteProdDateFrom.getDate())+"',   " ; }
		if (dateProdDateTo.getDate()==null){sqlDetail = sqlDetail + "production_to = null, " ; } else {sqlDetail = sqlDetail + "production_to = '"+dateFormat.format(dateProdDateTo.getDate())+"',   " ; }
		if (dteSuppDateFrom.getDate()==null){sqlDetail = sqlDetail + "fund_fr = null, " ; } else {sqlDetail = sqlDetail + "fund_fr = '"+dateFormat.format(dteSuppDateFrom.getDate())+"',   " ; }
		if (dateSuppDateTo.getDate()==null){sqlDetail = sqlDetail + "fund_to = null, " ; } else {sqlDetail = sqlDetail + "fund_to = '"+dateFormat.format(dateSuppDateTo.getDate())+"',   " ; }

		sqlDetail = sqlDetail +

		"production_target = "+prod_amt+",   " ;
		if (entity_id.trim().equals("")){sqlDetail = sqlDetail + "orig_btype = ''" ; } else {sqlDetail = sqlDetail +"orig_btype = '"+getBuyerType()+"' ";}

		sqlDetail = sqlDetail +
		"where rec_id = "+lookupCDF.getText().trim()+" " ;

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);	
	}
	
	private void updateCommSchedManual(String rec_id) {
		Double gr_amt  			= Double.valueOf(txtGrossAmount.getText().trim().replace(",","")).doubleValue();
		
		pgUpdate db = new pgUpdate();
		String SQL = "UPDATE cm_schedule_dl set comm_amt = "+gr_amt+", remarks = '"+txtPromoRemark.getText().trim().replace("'","''")+"' where rec_id = "+rec_id+"";
		db.executeUpdate(SQL, true);
		db.commit();
	}
	
	private void untagCommManual(String rec_id) {
		pgUpdate db = new pgUpdate();
		String SQL = "UPDATE cm_schedule_dl set status = 'I' WHERE rec_id = "+rec_id+"";
		db.executeUpdate(SQL, true);
		db.commit();
	}

	//saving validation
	private Boolean checkCompleteDetails(){

		boolean x = false;				

		String comm 		 	= txtNetAmount.getText();
		String promo		 	= lookupPromoCode.getText();

		String agent_entity		= "";

		String agent_id 		= lookupAgent.getText();		
		String other_payee_id 	= lookupOtherPayee.getText();

		if (rbtnAgent.isSelected()) {agent_entity = agent_id; }
		else {agent_entity=other_payee_id; }		

		if (comm.equals("0.00") || promo.equals("") || agent_entity.equals("")) {x=false;} 
		else {x=true;}		

		return x;

	}

	private boolean noTaggedClient() {		

		boolean noTaggedClient = true;		

		for(int x=0; x < modelClientList.getRowCount(); x++){

			Boolean isTrue = false;
			if(modelClientList.getValueAt(x,0) instanceof String){isTrue = new Boolean((String)modelClientList.getValueAt(x,0));}
			if(modelClientList.getValueAt(x,0) instanceof Boolean){isTrue = (Boolean)modelClientList.getValueAt(x,0);}

			if(isTrue){noTaggedClient = false; break;}		
			else {}
		}
		
		System.out.printf("Account ID" + acct_id);
		if (lookupPromoCode.getText().equals("2")||lookupPromoCode.getText().equals("1"))  //In-House Broker does not need a unit
		{
			noTaggedClient = true;	
		}
		
		return noTaggedClient;

	}



}