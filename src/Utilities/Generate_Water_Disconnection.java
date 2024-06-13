package Utilities;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.ListModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import Accounting.Disbursements.RequestForPayment;
import Database.pgSelect;
import DateChooser._JDateChooser;
import Functions.FncGlobal;
import Functions.FncReport;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JScrollPaneMain;
import components._JScrollPaneTotal;
import components._JTabbedPane;
import components._JTableMain;
import components._JTableTotal;
import components._JTagLabel;
import interfaces._GUI;
import tablemodel.model_wtr_disc_notice;


public class Generate_Water_Disconnection extends _JInternalFrame implements _GUI, ActionListener, MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3061284418918863916L;
	static String title = "Generate Water Disconnection Notice";
	static Dimension SIZE = new Dimension(1000, 600);

	private JPanel pnlMain;
	private JPanel pnlTable;
	private JPanel pnlClientDiscDetails;
	private JPanel pnlClient_Promo;
	private JPanel pnlSubTable;
	private JPanel pnlClientDetails;
	private JPanel pnlSouth;
	private JPanel pnlSouthCenterb;

	private JPanel pnlGenDetails;
	private JPanel pnlGenDetails1;
	private JPanel pnlGenDetailsA;
	private JPanel pnlGenDetailsA1;
	private JPanel pnlGenDetailsA1Proj;
	private static JComboBox cmbProject;
	private static JComboBox cmbPhase;
	private static JComboBox cmbTO;

	private JPanel pnlGenDetailsA1PB; 
	private JPanel pnlGenDetailsA1TO; 
	private JPanel pnlGenDetailsPeriodFrom;
	private JPanel pnlGenDetailsPeriodTo;
	private JPanel pnlGenDetailsDiscDate;
	private JPanel pnlGenDetailsA2;
	private JPanel pnlGenDetailsA3;
	private JPanel pnlGenDetailsB;
	private JPanel pnlGenDetailsB1;
	private JPanel pnlGenDetailsB2;
	private JPanel pnlGenDetailsB3;
	private JPanel pnlGenDetailsC;

	private JPanel pnlGenDetails2;

	private _JDateChooser dteDateFrom;	
	private _JDateChooser dteDateTo;
	private _JDateChooser dteReading_Date;

	private JPanel pnlComp;
	private JPanel pnlComp_a;
	private JPanel pnlComp_a1;
	private JPanel pnlComp_a2;
	private JPanel pnlComp_a3;

	private JLabel lblCompany;
	private JLabel lblProject;
	private JLabel lblPhase;
	private JLabel lblTO; 
	private JLabel lblDateFrom;
	private JLabel lblDateTo;
	private JLabel lblDiscDate;
	private JLabel lblBatch;
	private JLabel lblBatchNo;
	private _JLookup lookupBatchNo;

	private model_wtr_disc_notice modelWaterDisc;
	private model_wtr_disc_notice modelWaterDisc_total;
	//private modelWaterDiscmm_agentPromo_perAcct modelAgent_Promo_perAcct_total;

	private _JTabbedPane tabClientDetails;	

	private JButton btnPost;
	private JButton btnGenerate;
	private JButton btnCancel;
	private JButton btnGetAccts;

	private _JLookup lookupCompany;

	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);

	private _JScrollPaneMain scrollDiscAccounts;
	private _JScrollPaneTotal scrollWaterDisc_total;

	private _JTableMain tblClient_WaterDisc;
	JList rowHeaderWaterDisc;	


	private _JTableTotal tblClient_WaterDisc_total;

	private _JTagLabel tagCompany;

	private JPopupMenu menu;
	private JMenuItem mniViewDisconnection;


	protected static Font dialog11Bold = new Font("DIALOG", Font.BOLD, 11);

	String co_id = "";		
	String company 	= "";
	String company_logo;
	String agent_id = "";
	String proj_id = "";
	String proj = "";
	String proj_name = "";
	String phase = "";
	String block = "";
	String pbl_id 	= "";
	String seq_no 	= "";
	String entity_id = "";
	String comm_no 	= "";

	public Generate_Water_Disconnection() {
		super(title, true, true, true, true);
		initGUI();
	}

	public Generate_Water_Disconnection(String title) {
		super(title);
		// TODO Auto-generated constructor stub
	}

	public Generate_Water_Disconnection(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
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
		this.setPreferredSize(new java.awt.Dimension(917, 604));
		this.setBounds(0, 0, 917, 604);

		pnlMain = new JPanel();
		getContentPane().add(pnlMain, BorderLayout.CENTER);
		pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		pnlMain.setLayout(new BorderLayout(5, 5));
		pnlMain.setPreferredSize(new java.awt.Dimension(1134, 645));
		/*{
			menu = new JPopupMenu("Popup");
			mniViewDisconnection = new JMenuItem("View Client Disconnection");	
			menu.add(mniViewDisconnection);
		
			mniViewDisconnection.addActionListener(new ActionListener(){
				public void	actionPerformed(ActionEvent e){
					int row = tblClient_WaterDisc.getSelectedRow();
					pbl_id 	 =  modelWaterDisc.getValueAt(row, 6).toString().replace(",","");
					try { 
						pbl_id = modelWaterDisc.getValueAt(row, 6).toString().replace(",",""); 
					} 
					catch (NullPointerException e1) { 
						pbl_id = "" ; 
					}
		
					System.out.printf("pbl_id #1: " + pbl_id + "\n");
		
					generate_notice();				
				}
			});
		}*/
		{

			pnlComp = new JPanel(new BorderLayout(5, 0));
			pnlMain.add(pnlComp, BorderLayout.NORTH);	

			//company
			pnlComp_a = new JPanel(new BorderLayout(5, 5));
			pnlComp.add(pnlComp_a, BorderLayout.NORTH);	
			pnlComp_a.setPreferredSize(new java.awt.Dimension(820, 30));	

			pnlComp_a1 = new JPanel(new GridLayout(1, 2, 5, 5));
			pnlComp_a.add(pnlComp_a1, BorderLayout.WEST);	
			pnlComp_a1.setPreferredSize(new java.awt.Dimension(209, 30));
			pnlComp_a1.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));

			{
				lblCompany = new JLabel("COMPANY", JLabel.TRAILING);
				pnlComp_a1.add(lblCompany);
				lblCompany.setBounds(2, 11, 62, 12);
				lblCompany.setPreferredSize(new java.awt.Dimension(105, 25));
				lblCompany.setFont(new java.awt.Font("Segoe UI",Font.BOLD,12));
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
							tagCompany.setTag((String) data[1]);
							company 	= (String) data[1];

							refresh_tablesMain();

						}
					}
				});
			}

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
			pnlComp_a3 = new JPanel(new GridLayout(1, 2, 5, 5));
			pnlComp_a.add(pnlComp_a3, BorderLayout.EAST);	
			pnlComp_a3.setPreferredSize(new java.awt.Dimension(300, 20));	
			pnlComp_a3.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

			{
				lblBatch = new JLabel("BATCH NO.", JLabel.TRAILING);
				pnlComp_a3.add(lblBatch);
				lblBatch.setBounds(2, 11, 62, 12);
				lblBatch.setPreferredSize(new java.awt.Dimension(105, 25));
				lblBatch.setFont(new java.awt.Font("Segoe UI",Font.BOLD,12));
			}
			{
				lookupBatchNo = new _JLookup(null, "Batch No", 0);
				pnlComp_a3.add(lookupBatchNo);
				lookupBatchNo.setBounds(209, 27, 700, 22);
				lookupBatchNo.setPreferredSize(new java.awt.Dimension(200, 33));
				lookupBatchNo.setLookupSQL(sqlBatchNo());
				lookupBatchNo.addLookupListener(new LookupListener() {
					
					@Override
					public void lookupPerformed(LookupEvent event) {
						// TODO Auto-generated method stub
						Object[] data = ((_JLookup)event.getSource()).getDataSet();
						System.out.println("Dumaan dito sa lookupbatch");
						if(data != null) {
							String batch_no = (String) data[0];
							/*new Thread(new Runnable() {
								public void run() {
							
									FncGlobal.startProgress("Generating Accounts");*/
									System.out.println("Dumaan dito sa lookupbatch");
									cmbSelect();
									displayWaterDisconnection(batch_no);
									FncGlobal.stopProgress();
							/*}
							}).start();*/
							enableButtons(false, true, true);
						}
					}
				});
			}
			/*{
				lblBatchNo =  new JLabel("", JLabel.CENTER);
				pnlComp_a3.add(lblBatchNo);
				lblBatchNo.setBounds(209, 27, 700, 22);
				lblBatchNo.setEnabled(true);	
				lblBatchNo.setPreferredSize(new java.awt.Dimension(200, 33));
			}*/	

		}
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

				pnlClientDetails = new JPanel();
				pnlSubTable.add(pnlClientDetails, BorderLayout.CENTER);
				pnlClientDetails.setLayout(new BorderLayout(5, 5));
				pnlClientDetails.setBorder(lineBorder);		
				pnlClientDetails.setPreferredSize(new java.awt.Dimension(923, 177));
				pnlClientDetails.setBorder(JTBorderFactory.createTitleBorder(""));

				tabClientDetails = new _JTabbedPane();
				pnlClientDetails.add(tabClientDetails, BorderLayout.CENTER);	

				{
					pnlClientDiscDetails = new JPanel(new BorderLayout());
					tabClientDetails.addTab("Generate", null, pnlClientDiscDetails, null);
					pnlClientDiscDetails.setPreferredSize(new java.awt.Dimension(1183, 200));	

					{

						pnlGenDetails = new JPanel(new BorderLayout());
						pnlClientDiscDetails.add(pnlGenDetails, BorderLayout.NORTH);
						pnlGenDetails.setPreferredSize(new java.awt.Dimension(921, 100));
						pnlGenDetails.setBackground(new Color(201,206,204));
						pnlGenDetails.setBorder(BorderFactory.createLineBorder(Color.darkGray));
						{
							pnlGenDetails1 = new JPanel(new GridLayout(1, 2, 0, 5));
							pnlGenDetails.add(pnlGenDetails1, BorderLayout.WEST);
							pnlGenDetails1.setBackground(new Color(201,206,204));
							pnlGenDetails1.setPreferredSize(new java.awt.Dimension(768, 100));

							//pnlGenDetails.setBorder(BorderFactory.createEtchedBorder(Color.white, Color.gray));
							{
								pnlGenDetailsA = new JPanel(new GridLayout(3, 1, 0, 0));
								pnlGenDetails1.add(pnlGenDetailsA, BorderLayout.CENTER);
								pnlGenDetailsA.setBackground(new Color(201,206,204));
								//pnlGenDetailsA.setBorder(BorderFactory.createEtchedBorder(Color.white, Color.gray));
								{

									pnlGenDetailsA1 =  new JPanel(new BorderLayout(0,0));
									pnlGenDetailsA.add(pnlGenDetailsA1, BorderLayout.CENTER);
									pnlGenDetailsA1.setBackground(new Color(201,206,204));
									pnlGenDetailsA1.setBorder(BorderFactory.createLineBorder(Color.lightGray));
									{
										pnlGenDetailsA1Proj =  new JPanel(new BorderLayout(2,2));
										pnlGenDetailsA1.add(pnlGenDetailsA1Proj, BorderLayout.WEST);
										pnlGenDetailsA1Proj.setBackground(new Color(201,206,204));
										//pnlGenDetailsA1PB.setBorder(BorderFactory.createLineBorder(Color.lightGray));

										{
											lblProject = new JLabel("Project", JLabel.CENTER);
											pnlGenDetailsA1Proj.add(lblProject);
											//lblPhase.setBounds(15, 11, 62, 12);
											lblProject.setEnabled(true);
											lblProject.setPreferredSize(new java.awt.Dimension(80, 26));

											cmbProject = new JComboBox(new DefaultComboBoxModel(new String[] {"015  -   TERRAVERDE RESIDENCES", "017  -   EAST BELLEVUE",  "018  -   EASTWOOD VILLA EXTENSION",  "019  -   EASTWOOD RESIDENCE"}));
											pnlGenDetailsA1Proj.add(cmbProject, BorderLayout.EAST);
											//cmbPhase.setBounds(80, 60, 450, 20);
											cmbProject.setSelectedIndex(0);
											cmbProject.setPreferredSize(new java.awt.Dimension(298, 26));

										}
									}

									pnlGenDetailsA2 =  new JPanel(new BorderLayout(0,0));
									pnlGenDetailsA.add(pnlGenDetailsA2, BorderLayout.CENTER);
									pnlGenDetailsA2.setBackground(new Color(201,206,204));
									pnlGenDetailsA2.setBorder(BorderFactory.createLineBorder(Color.lightGray));
									{
										pnlGenDetailsA1PB =  new JPanel(new BorderLayout(2,2));
										pnlGenDetailsA2.add(pnlGenDetailsA1PB, BorderLayout.WEST);
										pnlGenDetailsA1PB.setBackground(new Color(201,206,204));
										//pnlGenDetailsA1PB.setBorder(BorderFactory.createLineBorder(Color.lightGray));

										{
											lblPhase = new JLabel("Phase", JLabel.CENTER);
											pnlGenDetailsA1PB.add(lblPhase);
											//lblPhase.setBounds(15, 11, 62, 12);
											lblPhase.setEnabled(true);
											lblPhase.setPreferredSize(new java.awt.Dimension(80, 26));

											cmbPhase = new JComboBox(new DefaultComboBoxModel(new String[] {"--          All", "1 -        PHASE 1", "2 -        PHASE 2", "3 -        PHASE 3", "3A -      PHASE 3A", "4 -        PHASE 4", "4-A -     PHASE 4-A",  "5 -        PHASE 5",  "1-B -        PHASE 1-B"}));

											pnlGenDetailsA1PB.add(cmbPhase, BorderLayout.EAST);
											//cmbPhase.setBounds(80, 60, 450, 20);
											cmbPhase.setSelectedIndex(0);
											cmbPhase.setPreferredSize(new java.awt.Dimension(298, 26));

										}
									}

									pnlGenDetailsA3 = new JPanel(new BorderLayout(0,0));
									pnlGenDetailsA.add(pnlGenDetailsA3, BorderLayout.CENTER);
									pnlGenDetailsA3.setBackground(new Color(201,206,204));
									pnlGenDetailsA3.setBorder(BorderFactory.createLineBorder(Color.lightGray));
									{
										pnlGenDetailsA1TO =  new JPanel(new BorderLayout(2,2));
										pnlGenDetailsA3.add(pnlGenDetailsA1TO, BorderLayout.WEST);
										pnlGenDetailsA1TO.setBackground(new Color(201,206,204));
										//pnlGenDetailsA1TO.setBorder(BorderFactory.createLineBorder(Color.lightGray));
										{
											lblTO = new JLabel("TO/MI", JLabel.CENTER);
											pnlGenDetailsA1TO.add(lblTO);
											//lblTO.setBounds(15, 11, 62, 12);
											lblTO.setEnabled(true);
											lblTO.setPreferredSize(new java.awt.Dimension(80, 26));

											cmbTO = new JComboBox(new DefaultComboBoxModel(new String[] {"--          All","1 -        Turn Over (Not Moved In)", "2 -        Turn Over (Moved In)"}));
											pnlGenDetailsA1TO.add(cmbTO, BorderLayout.EAST);
											//cmbTO.setBounds(80, 60, 450, 20);
											cmbTO.setSelectedIndex(0);
											cmbTO.setPreferredSize(new java.awt.Dimension(298, 26));

										}
									}
								}

								pnlGenDetailsB = new JPanel(new GridLayout(3, 1, 3, 3));
								pnlGenDetails1.add(pnlGenDetailsB, BorderLayout.CENTER);
								pnlGenDetailsB.setBackground(new Color(201,206,204));
								pnlGenDetailsB.setBorder(BorderFactory.createEtchedBorder(Color.white, Color.gray));
								{
									pnlGenDetailsB1 = new JPanel(new GridLayout(1, 1, 3, 3));
									pnlGenDetailsB.add(pnlGenDetailsB1, BorderLayout.CENTER);
									//pnlGenDetailsB1.setBackground(new Color(201,206,204));
									//pnlGenDetailsB1.setBorder(BorderFactory.createLineBorder(Color.lightGray));
									{
										pnlGenDetailsPeriodFrom = new JPanel(new GridLayout(1, 2, 3, 3));
										pnlGenDetailsB1.add(pnlGenDetailsPeriodFrom, BorderLayout.WEST);
										pnlGenDetailsPeriodFrom.setBackground(new Color(201,206,204));
										//pnlGenDetailsPeriodFrom.setBorder(BorderFactory.createLineBorder(Color.lightGray));
										{
											lblDateFrom = new JLabel("         Period Coverage From", JLabel.CENTER);
											pnlGenDetailsPeriodFrom.add(lblDateFrom, BorderLayout.CENTER);
											lblDateFrom.setEnabled(true);
										}
										{
											dteDateFrom = new _JDateChooser("MM/dd/yyyy", "##/##/##",'_');
											pnlGenDetailsPeriodFrom.add(dteDateFrom, BorderLayout.CENTER);
											dteDateFrom.setDate(null);
											dteDateFrom.setEnabled(true);
											dteDateFrom.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
											//dteDateFrom.setDate(FncGlobal.dateFormat("2018-01-01"));
										}

									}

									pnlGenDetailsB2 = new JPanel(new GridLayout(1, 1, 3, 3));
									pnlGenDetailsB.add(pnlGenDetailsB2, BorderLayout.CENTER);
									pnlGenDetailsB2.setBackground(new Color(201,206,204));
									//pnlGenDetailsB2.setBorder(BorderFactory.createLineBorder(Color.lightGray));
									{
										pnlGenDetailsPeriodTo = new JPanel(new GridLayout(1, 2, 3, 3));
										pnlGenDetailsB2.add(pnlGenDetailsPeriodTo, BorderLayout.WEST);
										pnlGenDetailsPeriodTo.setBackground(new Color(201,206,204));
										//pnlGenDetailsPeriodTo.setBorder(BorderFactory.createLineBorder(Color.lightGray));

										{
											lblDateTo = new JLabel("                                 To", JLabel.CENTER);
											pnlGenDetailsPeriodTo.add(lblDateTo, BorderLayout.CENTER);
											lblDateTo.setEnabled(true);
										}
										{
											dteDateTo = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
											pnlGenDetailsPeriodTo.add(dteDateTo, BorderLayout.CENTER);
											dteDateTo.setDate(null);
											dteDateTo.setEnabled(true);
											dteDateTo.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
										}
									}

									pnlGenDetailsB3 = new JPanel(new GridLayout(1, 1, 0, 0));
									pnlGenDetailsB.add(pnlGenDetailsB3, BorderLayout.CENTER);
									pnlGenDetailsB3.setBackground(new Color(201,206,204));
									pnlGenDetailsB3.setBorder(BorderFactory.createLineBorder(Color.lightGray));
									{
										pnlGenDetailsDiscDate =  new JPanel(new GridLayout(1, 2, 3, 3));
										pnlGenDetailsB3.add(pnlGenDetailsDiscDate, BorderLayout.WEST);
										//pnlGenDetailsDiscDate.setBackground(new Color(201,206,204));
										//pnlGenDetailsDiscDate.setBorder(BorderFactory.createLineBorder(Color.lightGray));

										{
											lblDiscDate = new JLabel("READING  DATE   ", JLabel.CENTER);
											pnlGenDetailsDiscDate.add(lblDiscDate, BorderLayout.CENTER);
											lblDiscDate.setEnabled(true);
										}
										{
											dteReading_Date = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
											pnlGenDetailsDiscDate.add(dteReading_Date, BorderLayout.CENTER);
											dteReading_Date.setDate(null);
											dteReading_Date.setEnabled(true);
											dteReading_Date.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
										}
									}

								}

							}
							pnlGenDetails2 =  new JPanel(new GridLayout(1, 1, 2 ,2));
							pnlGenDetails.add(pnlGenDetails2, BorderLayout.CENTER);
							pnlGenDetails2.setBackground(new Color(201,206,204));
							//pnlGenDetails2.setPreferredSize(new java.awt.Dimension(154, 100));
							{
								pnlGenDetailsC = new JPanel(new GridLayout(1, 1, 2 ,2));
								pnlGenDetails2.add(pnlGenDetailsC, BorderLayout.CENTER);
								pnlGenDetailsC.setBackground(new Color(201,206,204));
								//pnlGenDetailsC.setBorder(BorderFactory.createEtchedBorder(Color.darkGray, Color.gray));
								{		
									btnGetAccts = new JButton("Get Accounts");
									pnlGenDetailsC.add(btnGetAccts);
									btnGetAccts.addActionListener(this);
									btnGetAccts.setEnabled(true);
									btnGetAccts.setMnemonic(KeyEvent.VK_A);	
									btnGetAccts.setActionCommand("Get Accounts");

								}
							}
						}

						scrollDiscAccounts = new _JScrollPaneMain();
						pnlClientDiscDetails.add(scrollDiscAccounts, BorderLayout.CENTER);
						pnlClientDiscDetails.setPreferredSize(new java.awt.Dimension(1100, 100));
						//pnlClientDiscDetails.setBorder(BorderFactory.createLineBorder(Color.black));

						{
							modelWaterDisc = new model_wtr_disc_notice();


							tblClient_WaterDisc = new _JTableMain(modelWaterDisc);
							scrollDiscAccounts.setViewportView(tblClient_WaterDisc);
							//scrollDiscAccounts.setBorder(BorderFactory.createLineBorder(Color.darkGray));
							tblClient_WaterDisc.addMouseListener(this);								
							tblClient_WaterDisc.setSortable(false);
							//tblClient_WaterDisc.addMouseListener(this);	
							tblClient_WaterDisc.addMouseListener(new PopupTriggerListener_panel());
							tblClient_WaterDisc.getColumnModel().getColumn(0).setPreferredWidth(0);
							tblClient_WaterDisc.getColumnModel().getColumn(1).setPreferredWidth(120);
							tblClient_WaterDisc.getColumnModel().getColumn(2).setPreferredWidth(300);
							tblClient_WaterDisc.getColumnModel().getColumn(3).setPreferredWidth(100);
							tblClient_WaterDisc.getColumnModel().getColumn(4).setPreferredWidth(130);
							tblClient_WaterDisc.getColumnModel().getColumn(5).setPreferredWidth(150);
							tblClient_WaterDisc.getColumnModel().getColumn(6).setPreferredWidth(100);


							/*tblClient_WaterDisc.getSelectionModel().addListSelectionListener(new ListSelectionListener() 
							{
								public void valueChanged(ListSelectionEvent e)
								{
									if(!e.getValueIsAdjusting())
									{
										try 
										{
											int row = tblClient_WaterDisc.convertRowIndexToModel(tblClient_WaterDisc.getSelectedRow());
											Boolean isSelected = (Boolean) modelWaterDisc.getValueAt(row, 0);											
											String unit = (String) modelWaterDisc.getValueAt(row, 1).toString();
											BigDecimal amount_due = (BigDecimal) modelWaterDisc.getValueAt(row, 3);
											String due_date = (String) modelWaterDisc.getValueAt(row, 4).toString();
											String last_pmt_date = (String) modelWaterDisc.getValueAt(row, 5).toString();
											pbl_id = modelWaterDisc.getValueAt(row, 6).toString();
							
											//generate_notice();
							
										} catch (ArrayIndexOutOfBoundsException e1) {
							
										} catch (IndexOutOfBoundsException e2) {}
									}
								}
							});*/

							tblClient_WaterDisc.hideColumns("Entity ID", "Proj. ID" ,"PBL ID", "Seq. No");


							tblClient_WaterDisc.addMouseListener(new MouseAdapter() {
								public void mousePressed(MouseEvent e) {
									if(tblClient_WaterDisc.rowAtPoint(e.getPoint()) == -1){
										//
									}else{
										tblClient_WaterDisc_total.clearSelection();
									}
								}
								public void mouseReleased(MouseEvent e) {
									if(tblClient_WaterDisc.rowAtPoint(e.getPoint()) == -1){
										tblClient_WaterDisc_total.clearSelection();
									};
								}

							});
							modelWaterDisc.addTableModelListener(new TableModelListener() {
								public void tableChanged(TableModelEvent e) {
									if(e.getType() == TableModelEvent.DELETE){
										//
									}
									if(e.getType() == TableModelEvent.INSERT){
										((DefaultListModel)rowHeaderWaterDisc.getModel()).addElement(modelWaterDisc.getRowCount());
									}
								}
							});

						}
						{
							rowHeaderWaterDisc = tblClient_WaterDisc.getRowHeader();
							rowHeaderWaterDisc.setModel(new DefaultListModel());
							//rowHeaderWaterDisc.setModel(modelWaterDisc());
							scrollDiscAccounts.setRowHeaderView(rowHeaderWaterDisc);
							scrollDiscAccounts.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());

						}
					}
					{
						scrollWaterDisc_total = new _JScrollPaneTotal(scrollDiscAccounts);
						pnlClientDiscDetails.add(scrollWaterDisc_total, BorderLayout.SOUTH);
						{
							modelWaterDisc_total = new model_wtr_disc_notice();
							modelWaterDisc_total.addRow(new Object[] { null,"Total", null, new BigDecimal(0.00), null, null });
							tblClient_WaterDisc_total = new _JTableTotal(modelWaterDisc_total, tblClient_WaterDisc);
							tblClient_WaterDisc_total.setFont(dialog11Bold);
							scrollWaterDisc_total.setViewportView(tblClient_WaterDisc_total);
							((_JTableTotal) tblClient_WaterDisc_total).setTotalLabel(1);
						}
					}
				} 

				{
					pnlClient_Promo = new JPanel(new BorderLayout());
					tabClientDetails.addTab("Retrieve", null, pnlClient_Promo, null);
					pnlClient_Promo.setPreferredSize(new java.awt.Dimension(1183, 365));	

				}
			}

		} 
		{
			pnlSouth = new JPanel();
			pnlMain.add(pnlSouth, BorderLayout.SOUTH);
			pnlSouth.setLayout(new BorderLayout());
			pnlSouth.setBorder(BorderFactory.createLineBorder(Color.GRAY));
			pnlSouth.setPreferredSize(new java.awt.Dimension(1014, 33));

			pnlSouthCenterb = new JPanel(new GridLayout(1, 1, 3, 3));
			pnlSouth.add(pnlSouthCenterb, BorderLayout.NORTH);
			pnlSouthCenterb.setPreferredSize(new java.awt.Dimension(921, 31));
			{
				{
					btnPost = new JButton("Post");
					pnlSouthCenterb.add(btnPost);
					btnPost.setActionCommand("Post");
					btnPost.addActionListener(this);
					btnPost.setEnabled(true);
					btnPost.setMnemonic(KeyEvent.VK_S);
				}
				{
					btnGenerate = new JButton("Generate Disconnection Notice");
					pnlSouthCenterb.add(btnGenerate);
					btnGenerate.setActionCommand("generate");
					btnGenerate.addActionListener(this);
					btnGenerate.setEnabled(true);
					btnGenerate.setMnemonic(KeyEvent.VK_G);

				}
				{
					btnCancel = new JButton("Cancel");
					pnlSouthCenterb.add(btnCancel);
					btnCancel.setActionCommand("cancel");
					btnCancel.addActionListener(this);
					btnCancel.setEnabled(false);
					btnCancel.setMnemonic(KeyEvent.VK_P);
				}

			}
		}

		initialize_comp();
	}



	protected ListModel modelWaterDisc() {
		// TODO Auto-generated method stub
		return null;
	}

	//Enable/Disable all components inside JPanel	
	public void refresh_tablesMain(){//

		//reset table 1
		if(modelWaterDisc.getRowCount() > 0) {
			FncTables.clearTable(modelWaterDisc);
		}
		
		if(modelWaterDisc.getRowCount() > 0) {
			FncTables.clearTable(modelWaterDisc_total);
		}		
		rowHeaderWaterDisc = tblClient_WaterDisc.getRowHeader22();
		scrollDiscAccounts.setRowHeaderView(rowHeaderWaterDisc);	
		modelWaterDisc_total.addRow(new Object[]   { null, null,"Total", new BigDecimal(0.00), null, null });

	}

	public void initialize_comp(){		

		co_id 		= "02";	
		company		= "CENQHOMES DEVELOPMENT CORPORATION";			
		tagCompany.setTag(company);
		company_logo = RequestForPayment.sql_getCompanyLogo();		
		lookupCompany.setValue(co_id);
		enableButtons(false, false, false);
		
	}	


	public void actionPerformed(ActionEvent e) {

		String actionCommand = e.getActionCommand();
		
		if(actionCommand.equals("Post")) {
			if(JOptionPane.showConfirmDialog(this.getTopLevelAncestor(), "Are you sure you want to save?", actionCommand, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
				saveDisconnectionNotice();
				generate_notice();
				modelWaterDisc.clear();
				rowHeaderWaterDisc.setModel(new DefaultListModel());
				lookupBatchNo.setValue(null);
				enableButtons(false, false, false);
			}
		}

		if(actionCommand.equals("Get Accounts")){
			new Thread(new Runnable() {
				public void run() {
					FncGlobal.startProgress("Generating Accounts");
					String batch_no =  generateBatchNo().toString();
					lookupBatchNo.setValue(batch_no);

					cmbSelect();

					displayWaterDisconnection(lookupBatchNo.getValue());	
					//tblClient_WaterDisc.packAll();
					tblClient_WaterDisc.setEditable(true);
					btnGenerate.setEnabled(true);	
					
					FncGlobal.stopProgress();
					enableButtons(true,true,true);
				}
			}).start();
		};

		if(e.getActionCommand().equals("generate")){ 		
			pbl_id 	 = "";  
			generate_notice();
			//enableButtons(true,true,true);
		}

		if(actionCommand.equals("cancel")){
			//modelWaterDisc.setRowCount(0);
			lookupBatchNo.setValue(null);
			enableButtons(false, false, false);
			rowHeaderWaterDisc.setModel(new DefaultListModel());
			modelWaterDisc.clear();
		}

		


	}


	//action performed
	@Override
	// TODO Auto-generated method stub

	public void mouseClicked(MouseEvent e) {		
		if ((e.getClickCount() >= 2)) {

		}
	}

	private void enableButtons (Boolean a, Boolean b, Boolean c){
		btnPost.setEnabled(a);		
		btnGenerate.setEnabled(b);
		btnCancel.setEnabled(c);

	}

	public void refresh(){
		lookupCompany.setValue("");
		FncTables.clearTable(modelWaterDisc);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.

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
	public class PopupTriggerListener_panel extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			/*if (e.isPopupTrigger()) {
					menu.show(e.getComponent(), e.getX(), e.getY()); 
					mniViewDisconnection.setEnabled(true) ;
			}*/
		}
		public void mouseReleased(MouseEvent e) {
			/*if (e.isPopupTrigger()) {

				menu.show(e.getComponent(), e.getX(), e.getY()); 
				mniViewDisconnection.setEnabled(true) ;
			}	*/
		}
	}
	
	private void displayWaterDisconnection(String batch_no) {
		FncTables.clearTable(modelWaterDisc);//Code to clear modelMain.	
		rowHeaderWaterDisc.setModel(new DefaultListModel());
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.


		//String strSQL = "select false, c_description,c_client_name, c_total_amt_due, c_due_date, c_last_pmt_date, c_pbl_id from view_water_disconnection('"+co_id+"','"+proj+"','"+phase+"','"+pbl_id+"','"+ dteDateFrom.getDateString()+"','"+ dteDateTo.getDateString()+"', '"+ dteDiscDate.getDateString()+"')where (c_due_count-c_pmt_count)>2 and c_with_disconnection = true and c_last_pmt_date::date not between '"+ dteDateFrom.getDateString()+"'::date and'"+ dteDateTo.getDateString()+"'::date;";
		String strSQL = "select * from view_water_disconnection_V2('"+co_id+"','"+proj+"','"+phase+"','"+pbl_id+"','"+ dteDateFrom.getDateString()+"','"+ dteDateTo.getDateString()+"', '"+ dteReading_Date.getDateString()+"', '"+batch_no+"')";
		FncSystem.out("Display SQL for Generation", strSQL);
		
		pgSelect db = new pgSelect();
		db.select(strSQL);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				// Adding of row in table
				modelWaterDisc.addRow(db.getResult()[x]);
				listModel.addElement(modelWaterDisc.getRowCount());				
			}	
		}
	}
    
	private void saveDisconnectionNotice() {
		String batch_no = lookupBatchNo.getValue();
		Date date_from = dteDateFrom.getDate();
		Date date_to = dteDateTo.getDate();
		Date reading_date = dteReading_Date.getDate();
		
		/*Date date_from = (Date) dteDateFrom.getDate();
		Date date_to = (Date) dteDateTo.getDate();
		java.util.Date reading_date = (Date) dteReading_Date.getDate();*/
		
		ArrayList<String> listEntityID = new ArrayList<String>();
		ArrayList<String> listProjID = new ArrayList<String>();
		ArrayList<String> listPBL = new ArrayList<String>();
		ArrayList<Integer> listSeq = new ArrayList<Integer>();
		ArrayList<String> listDue_Date = new ArrayList<String>();
		ArrayList<String> listLastPmtDate = new ArrayList<String>();
		
		for(int x= 0; x<modelWaterDisc.getRowCount(); x++){
			Boolean isSelected = (Boolean) modelWaterDisc.getValueAt(x, 0);
			
			if(isSelected){
				
				String due_date = (String) modelWaterDisc.getValueAt(x, 4);
				String last_pmt_date = (String) modelWaterDisc.getValueAt(x, 5);
				
				String entity_id = (String) modelWaterDisc.getValueAt(x, 6);
				String proj_id = (String) modelWaterDisc.getValueAt(x, 7);
				String pbl_id = (String) modelWaterDisc.getValueAt(x, 8);
				Integer seq_no = (Integer) modelWaterDisc.getValueAt(x, 9);
				
				listProjID.add(String.format("'%S'", proj_id));
				listPBL.add(String.format("'%s'", pbl_id));
				listSeq.add(seq_no);
				listEntityID.add(String.format("'%s'", entity_id));
				listDue_Date.add(String.format("'%s'", due_date));
				listLastPmtDate.add(String.format("'%s'", last_pmt_date));
			}
		}
		
		String entity_id = listEntityID.toString().replaceAll("\\[|\\]", "");
		String proj_id = listProjID.toString().replaceAll("\\[|\\]", "");
		String pbl_id = listPBL.toString().replaceAll("\\[|\\]", "");
		String seq_no = listSeq.toString().replaceAll("\\[|\\]", "");
		String due_date = listDue_Date.toString().replaceAll("\\[|\\]", "");
		String last_pmt_date = listLastPmtDate.toString().replaceAll("\\[|\\]", "");
		
		System.out.printf("Display value of due_date: %s%n", due_date);
		System.out.printf("Display value of last pmt date: %s%n", last_pmt_date);
		
		pgSelect db = new pgSelect();
		
		String SQL = "SELECT sp_tag_water_disconnection('"+batch_no+"',ARRAY["+entity_id+"]::VARCHAR[], ARRAY["+proj_id+"]::VARCHAR[], ARRAY["+pbl_id+"]::VARCHAR[], ARRAY["+seq_no+"]::INT[],\n" + 
					 "ARRAY["+due_date+"]::VARCHAR[], ARRAY["+last_pmt_date+"]::VARCHAR[], '"+date_from+"', '"+date_to+"', '"+reading_date+"' ,'"+UserInfo.EmployeeCode+"')";
		db.select(SQL, "Post", true);
		
		FncSystem.out("Display SQL for Posting Meralco Individualization", SQL);
	}

	private void generate_notice(){

		ArrayList<String> listEntityID = new ArrayList<String>();
		ArrayList<String> listProjID = new ArrayList<String>();
		ArrayList<String> listPBL = new ArrayList<String>();
		ArrayList<String> listSeq = new ArrayList<String>();

		for(int x= 0; x<modelWaterDisc.getRowCount(); x++){
			Boolean isSelected = (Boolean) modelWaterDisc.getValueAt(x, 0);

			if(isSelected){
				String entity_id = (String) modelWaterDisc.getValueAt(x, 6);
				String proj_id = (String) modelWaterDisc.getValueAt(x, 7);
				String pbl_id = (String) modelWaterDisc.getValueAt(x, 8);
				Integer seq_no = (Integer) modelWaterDisc.getValueAt(x, 9);

				listEntityID.add(String.format("%s", entity_id));
				listProjID.add(String.format("%s", proj_id));
				listPBL.add(String.format("%s", pbl_id));
				listSeq.add(String.format("%s", seq_no));
				//listSeq.add(seq_no);
			}
		}

		String entity_id = listEntityID.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");
		String proj_id = listProjID.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");
		String pbl_id = listPBL.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");
		String seq_no = listSeq.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");

		System.out.printf("Display value of entity_id: (%s)%n", entity_id);
		System.out.printf("Display value of proj_id: (%s)%n", proj_id);
		System.out.printf("Display value of pbl id: (%s)%n", pbl_id);
		System.out.printf("Display value of seq_no: (%s)%n", seq_no);

		Map<String, Object> mapParameters = new HashMap<String, Object>();

		mapParameters.put("co_id", co_id);
		mapParameters.put("company", tagCompany.getText().replace("[", "").replace("]", ""));
		mapParameters.put("proj_id", proj);
		mapParameters.put("proj_name", proj_name);
		mapParameters.put("phase_no", phase);
		//mapParameters.put("pbl_id", pbl_id);
		mapParameters.put("batch_no", lookupBatchNo.getValue());
		mapParameters.put("date_fr", dteDateFrom.getDate());
		mapParameters.put("date_to", dteDateTo.getDate());
		mapParameters.put("reading_date", dteReading_Date.getDate());
		mapParameters.put("emp_name", UserInfo.FullName);
		mapParameters.put("emp_alias", UserInfo.Alias);
		mapParameters.put("entity_id", entity_id);
		mapParameters.put("proj_id", proj_id);
		mapParameters.put("pbl_id", pbl_id);
		mapParameters.put("seq_no", seq_no);
		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ sql_getCompanyLogo(co_id)));

		System.out.printf("co_id #1: " + co_id + "\n");
		System.out.printf("proj_id #1: " + proj + "\n");
		System.out.printf("phase_no #1: " + phase + "\n");
		System.out.printf("pbl_id #1: " + pbl_id + "\n");
		System.out.printf("date_fr #1: " + dteDateFrom.getDateString()+ "\n");
		System.out.printf("date_to #1: " + dteDateTo.getDateString()+ "\n");
		
		String SQL = "select * from view_water_disconnection_v3("
				+ "'"+co_id+"',"
				+ "'"+phase+"',"
				+ "'"+dteDateFrom.getDate()+"'::date,"
				+ "'"+dteDateTo.getDate()+"'::date,"
				+ "'"+dteReading_Date.getDate()+"'::date,"
				+ "string_to_array('"+entity_id+"', ',')::VARCHAR[], string_to_array('"+proj_id+"', ',')::VARCHAR[], string_to_array('"+pbl_id+"', ',')::VARCHAR[], string_to_array('"+seq_no+"', ',')::VARCHAR[], '"+lookupBatchNo.getValue()+"')";
		
		FncSystem.out("view_water_disconnection_v3", SQL);
		FncReport.generateReport("/Reports/rptPMD_NoticeofDisconnection.jasper", "Disconnection Notice", mapParameters);

		FncReport.generateReport("/Reports/rptWater_Disconnection_Transmittal.jasper", "Transmittal", mapParameters);

	}


	public void cmbSelect(){
		String selected_proj = (String) cmbProject.getSelectedItem().toString();
		String selected_phase = (String) cmbPhase.getSelectedItem().toString();

		if ((selected_proj.equals("015  -   TERRAVERDE RESIDENCES"))){
			proj = "015";
			proj_name =  "TERRAVERDE RESIDENCES";
		}else if((selected_proj.equals("017  -   EAST BELLEVUE"))){
			proj = "017";
			proj_name =  "EAST BELLEVUE";
		}else if((selected_proj.equals("018  -   EASTWOOD VILLA EXTENSION"))){
			proj = "018";
			proj_name = "EASTWOOD VILLA EXTENSION";
		}else if((selected_proj.equals("019  -   EASTWOOD RESIDENCE"))){
			proj = "019";
			proj_name = "EASTWOOD VILLA EXTENSION";
		};
		System.out.printf("Display Project: %s%n", proj);

		if ((selected_phase.equals("--          All"))){
			phase = "";
		}else if((selected_phase.equals("1 -        PHASE 1"))){
			phase = "1";	
		}else if((selected_phase.equals("2 -        PHASE 2"))){
			phase = "2";	
		}else if((selected_phase.equals("3 -        PHASE 3"))){
			phase = "3";	
		}else if((selected_phase.equals("3A -      PHASE 3A"))){
			phase = "3A";	
		}else if((selected_phase.equals("4 -        PHASE 4"))){
			phase = "4";	
		}else if((selected_phase.equals("4-A -     PHASE 4-A"))){
			phase = "4-A";	
		}else if((selected_phase.equals("5 -        PHASE 5"))){
			phase = "5";	
		}else if((selected_phase.equals("1-B -        PHASE 1-B"))){
			phase = "1-B";	
		};
		System.out.printf("Display Phase: %s%n", phase);
	}

	private static String generateBatchNo() {
		String strSQL = "SELECT trim(to_char((COALESCE(max(batch_no::int), 0) + 1), '0000000000'))  FROM rf_water_disconnection WHERE status_id = 'A' and batch_no is not null and batch_no != '';";

		FncSystem.out("Batch No", strSQL);
		pgSelect db = new pgSelect();
		db.select(strSQL);

		if(db.isNotNull()){
			return (String) db.getResult()[0][0];
		}else{
			return null;
		}
	}
	
	private String sqlBatchNo() {
		String SQL = "SELECT distinct batch_no as \"Batch No.\" FROM rf_water_disconnection WHERE status_id = 'A' ORDER BY batch_no DESC";
		return SQL;
	}
	/**/
	private String sql_getCompanyLogo(String co_id) {

		String a = "";

		String SQL = "select company_logo from mf_company \n" + "where co_id = '" + co_id + "' ";

		System.out.printf("SQL #1: sql_getCompanyLogo", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);

		if (db.isNotNull()) {
			if ((String) db.getResult()[0][0] == null || db.getResult()[0][0].equals("null")) {
				a = "";
			} else {
				a = (String) db.getResult()[0][0];
			}

		} else {
			a = "";
		}

		return a;
	}

}
