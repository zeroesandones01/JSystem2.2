package Reports.LegalAndLiaisoning;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JTextFieldDateEditor;

import Accounting.Disbursements.RequestForPayment;
import Buyers.ClientServicing._OtherRequest;
import Buyers.ClientServicing.pnlOtherReq_PreviewSchedule;
import Database.pgSelect;
import Database.pgUpdate;
import DateChooser._JDateChooser;
import Functions.FncGlobal;
import Functions.FncReport;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.UserInfo;
import Home.Home_JSystem;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JTabbedPane;
import components._JTableMain;
import components._JXTextField;
import interfaces._GUI;
import tablemodel.modelTransactionSummary;
import tablemodel.model_AccessMain_Modules;
import tablemodel.model_PCost2;
import tablemodel.model_TCost2;

public class ProcessingTransferCostReport extends _JInternalFrame implements _GUI {

	private static final long serialVersionUID = -6517018477299344044L;

	JPanel pnlMain;
	JPanel pnlNorth;
	JPanel pnlWest;
	JPanel pnlEast;
	JPanel pnlCenter;
	JPanel pnlCenter1;
	JPanel pnlSouth;

	JTextField txtProject;
	JTextField txtCompany;
	JTextField txtPhase;
	JTextField txtBuyer;
	JTextField txtBatch;
	JTextField txtrplf;

	JLabel lblBatch;

	static JTabbedPane tabCenter;

	_JLookup lookupProject;
	_JLookup lookupCompany;
	_JLookup lookupPhase;
	_JLookup lookupPCOST_Batch;
	_JLookup lookupBuyer;
	_JLookup lookupTCOST_Batch;
	_JLookup lookupRplf;

	_JDateChooser dateFrom;
	_JDateChooser dateTo;

	JComboBox cmbClass;
	JCheckBox chkboxListSummary;

	model_PCost2 modelPCost;
	_JTableMain tblPCost;

	model_TCost2 modelTCost;
	_JTableMain tblTCost;
	String co_id = "";
	String company = "";
	String company_logo = "";

	String batch = "";
	String batch1 = "";
	static String SQL = "";

	static String title = "Processing/Transfer Cost Report";
	Dimension frameSize = new Dimension(700, 500);// 510, 250
	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);

	private String emp_code;

	private String proj_id;

	private String rplf_no_pcost;

	public ProcessingTransferCostReport() {
		super(title, false, true, false, true);
		initGUI();
	}

	public ProcessingTransferCostReport(String title) {
		super(title, false, true, false, true);
		initGUI();
	}

	public ProcessingTransferCostReport(String title, boolean resizable,
			boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		this.setPreferredSize(frameSize);
		{
			pnlMain = new JPanel();
			this.getContentPane().add(pnlMain, BorderLayout.CENTER);
			pnlMain.setLayout(new BorderLayout(10, 10));
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			{
				pnlNorth = new JPanel();
				pnlMain.add(pnlNorth, BorderLayout.NORTH);
				pnlNorth.setLayout(new BorderLayout(5, 5));
				pnlNorth.setPreferredSize(new Dimension(700, 180));// XXX
				{
					pnlWest = new JPanel(new BorderLayout(5, 5));
					pnlNorth.add(pnlWest, BorderLayout.WEST);
					pnlWest.setPreferredSize(new Dimension(440, 180));// XXX
					{
						JPanel pnlWest1 = new JPanel(new BorderLayout(5, 5));
						pnlWest.add(pnlWest1, BorderLayout.NORTH);
						pnlWest1.setPreferredSize(new Dimension(440, 102));// XXX
						{
							JPanel pnlLabel = new JPanel(new GridLayout(4, 0, 5, 5));
							pnlWest1.add(pnlLabel, BorderLayout.WEST);
							{
								JLabel lblCompany = new JLabel("Company:");
								pnlLabel.add(lblCompany);
								lblCompany.setHorizontalAlignment(JLabel.RIGHT);

								JLabel lblProject = new JLabel("Project:");
								pnlLabel.add(lblProject);
								lblProject.setHorizontalAlignment(JLabel.RIGHT);

								JLabel lblPhase = new JLabel("Phase:");
								pnlLabel.add(lblPhase);
								lblPhase.setHorizontalAlignment(JLabel.RIGHT);

								JLabel lblClass = new JLabel("Class:");
								pnlLabel.add(lblClass);
								lblClass.setHorizontalAlignment(JLabel.RIGHT);
							}
						}
						{
							JPanel pnlText = new JPanel(new GridLayout(4, 0, 5, 5));
							pnlWest1.add(pnlText, BorderLayout.CENTER);
							{
								JPanel pnlLookup1 = new JPanel(new BorderLayout(3, 3));
								pnlText.add(pnlLookup1);
								{
									lookupCompany = new _JLookup(null, "Company");
									pnlLookup1.add(lookupCompany, BorderLayout.WEST);
									lookupCompany.setLookupSQL(SQL_COMPANY());
									lookupCompany.setReturnColumn(0);
									lookupCompany.setPreferredSize(new Dimension(50, 20));
									lookupCompany.addLookupListener(new LookupListener() {
										public void lookupPerformed(LookupEvent event) {
											Object[] data = ((_JLookup) event.getSource()).getDataSet();
											if (data != null) {

												txtCompany.setText(String.format("%s", (String) data[1]));
												lookupProject.setLookupSQL(SQL_PROJECT((String) data[0]));

												KEYBOARD_MANAGER.focusNextComponent();
											}
										}
									});
									{
										txtCompany = new JTextField("Select Company");
										pnlLookup1.add(txtCompany,BorderLayout.CENTER);
										txtCompany.setHorizontalAlignment(JLabel.LEFT);
										txtCompany.setEditable(false);
										txtCompany.setPreferredSize(new Dimension(50, 20));
									}
								}
							}
							{
								JPanel pnlLookup = new JPanel(new BorderLayout(3, 3));
								pnlText.add(pnlLookup);
								{
									lookupProject = new _JLookup(null, "Project", "Please select Company.");
									pnlLookup.add(lookupProject, BorderLayout.WEST);
									lookupProject.setLookupSQL(SQL_PROJECT());
									lookupProject.setReturnColumn(0);
									lookupProject.setPreferredSize(new Dimension(50, 20));
									lookupProject.addLookupListener(new LookupListener() {
										public void lookupPerformed(LookupEvent event) {
											Object[] data = ((_JLookup) event.getSource()).getDataSet();
											if (data != null) {

												txtProject.setText(String.format("%s", (String) data[1]));
												lookupPhase.setLookupSQL(SQL_PHASE((String) data[0]));

												KEYBOARD_MANAGER.focusNextComponent();
											}
										}
									});
									{
										txtProject = new JTextField("All Projects");
										pnlLookup.add(txtProject,BorderLayout.CENTER);
										txtProject.setHorizontalAlignment(JLabel.LEFT);
										txtProject.setEditable(false);
									}
								}
							}
							{
								JPanel pnlLookup2 = new JPanel(new BorderLayout(3, 3));
								pnlText.add(pnlLookup2);
								{
									lookupPhase = new _JLookup(null, "Phase", "Please select Project.");
									pnlLookup2.add(lookupPhase, BorderLayout.WEST);
									lookupPhase.setReturnColumn(0);
									lookupPhase.setPreferredSize(new Dimension(50, 20));
									lookupPhase.addLookupListener(new LookupListener() {
										public void lookupPerformed(LookupEvent event) {
											Object[] data = ((_JLookup) event.getSource()).getDataSet();
											if (data != null) {

												txtPhase.setText(String.format("%s", (String) data[1]));

												KEYBOARD_MANAGER.focusNextComponent();
											}
										}
									});
									{
										txtPhase = new JTextField("All Phases");
										pnlLookup2.add(txtPhase,BorderLayout.CENTER);
										txtPhase.setHorizontalAlignment(JLabel.LEFT);
										txtPhase.setEditable(false);
									}
								}
								/*{
									JPanel pnlLookup3 = new JPanel(new BorderLayout(3, 3));
									pnlLookup2.add(pnlLookup3, BorderLayout.EAST);
									{
										lblBatch = new JLabel("PCost Batch No:");
										pnlLookup3.add(lblBatch,BorderLayout.CENTER);
										lblBatch.setHorizontalAlignment(JLabel.CENTER);
									}
									{
										lookupPCOST_Batch = new _JLookup(null, "Batch");
										pnlLookup3.add(lookupPCOST_Batch, BorderLayout.EAST);
										lookupPCOST_Batch.setLookupSQL(SQL_BATCHPCOST());
										lookupPCOST_Batch.setReturnColumn(0);
										lookupPCOST_Batch.setPreferredSize(new Dimension(135, 20));
									}
								}*/
							}
							{
								cmbClass = new JComboBox(new DefaultComboBoxModel(getClass1()));
								pnlText.add(cmbClass, BorderLayout.WEST);
								//cmbClass.addActionListener(this);
								cmbClass.addItemListener(new ItemListener() {//**ADDED BY JED VICEDO 2019-07-18 : TO SEPARATE BUYER RELATED TO BLOCK LOT RELATED PARTICULARS**//

									@Override
									public void itemStateChanged(ItemEvent e) {
										// TODO Auto-generated method stub
										int index = ((JComboBox) e.getSource()).getSelectedIndex();
										String type = "";

										if(index == 0) {
											type = "";
											displayPCost2(type);
										}
										if(index == 1) {
											type = "B";
											displayPCost2(type);
										}
										if(index == 2) {
											type = "L";
											displayPCost2(type);
										}
									}
								});
							}
						}
					}
					{
						JPanel pnlWest2 = new JPanel(new BorderLayout(5, 5));
						pnlWest.add(pnlWest2, BorderLayout.SOUTH);
						pnlWest2.setBorder(JTBorderFactory.createTitleBorder("Select Buyer"));
						pnlWest2.setPreferredSize(new Dimension(440, 55));// XXX
						{
							JPanel pnlLookup4 = new JPanel(new BorderLayout(3, 3));
							pnlWest2.add(pnlLookup4);
							{
								lookupBuyer = new _JLookup(null, "Buyer");
								pnlLookup4.add(lookupBuyer, BorderLayout.WEST);
								lookupBuyer.setLookupSQL(SQL_CLIENT());
								lookupBuyer.setReturnColumn(0);
								lookupBuyer.setPreferredSize(new Dimension(80, 20));
								lookupBuyer.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup) event.getSource()).getDataSet();
										if (data != null) {

											txtBuyer.setText(String.format("%s", (String) data[1]));

											KEYBOARD_MANAGER.focusNextComponent();
										}
									}
								});
								{
									txtBuyer = new JTextField("");
									pnlLookup4.add(txtBuyer,BorderLayout.CENTER);
									txtBuyer.setHorizontalAlignment(JLabel.LEFT);
									txtBuyer.setEditable(false);
									txtBuyer.setPreferredSize(new Dimension(50, 20));
								}
							}
						}
					}
				}
				{
					pnlEast = new JPanel(new BorderLayout(5, 5));
					pnlNorth.add(pnlEast, BorderLayout.EAST);
					pnlEast.setPreferredSize(new Dimension(235, 180));// XXX
					{
						JPanel pnlEast1 = new JPanel(new BorderLayout(5, 5));
						pnlEast.add(pnlEast1, BorderLayout.NORTH);
						pnlEast1.setBorder(JTBorderFactory.createTitleBorder("Trans Date"));
						pnlEast1.setPreferredSize(new Dimension(235, 70));// XXX
						{
							JPanel pnlLabel1 = new JPanel(new GridLayout(2, 0, 5, 5));
							pnlEast1.add(pnlLabel1, BorderLayout.WEST);
							{
								JLabel lblDateFrom = new JLabel("Date From:");
								pnlLabel1.add(lblDateFrom);
								lblDateFrom.setHorizontalAlignment(JLabel.RIGHT);

								JLabel lblDateTo = new JLabel("Date To:");
								pnlLabel1.add(lblDateTo);
								lblDateTo.setHorizontalAlignment(JLabel.RIGHT);
							}
						}
						{
							JPanel pnlCalendar = new JPanel(new GridLayout(2, 0, 5, 5));
							pnlEast1.add(pnlCalendar, BorderLayout.CENTER);
							{
								dateFrom = new _JDateChooser("MM/dd/yyyy", "##/##/#####", '_');
								pnlCalendar.add(dateFrom);
								dateFrom.setDate(null);
								dateFrom.setDateFormatString("yyyy-MM-dd");
								((JTextFieldDateEditor)dateFrom.getDateEditor()).setEditable(false);
								dateFrom.setDate(FncGlobal.dateFormat("2017-01-01"));
							}
							{
								dateTo = new _JDateChooser("MM/dd/yyyy", "##/##/#####", '_');
								pnlCalendar.add(dateTo);
								dateTo.setDate(null);
								dateTo.setDateFormatString("yyyy-MM-dd");
								((JTextFieldDateEditor)dateTo.getDateEditor()).setEditable(false);
								//dateTo.setDate(Calendar.getInstance().getTime());
								dateTo.setDate(FncGlobal.getDateToday());
							}
						}
					}
					{
						JPanel pnlEast2 = new JPanel(new BorderLayout(5, 5));
						pnlEast.add(pnlEast2, BorderLayout.CENTER);
						pnlEast2.setBorder(JTBorderFactory.createTitleBorder("TCost Batch No."));
						pnlEast2.setPreferredSize(new Dimension(235, 62));// XXX
						{
							lookupTCOST_Batch = new _JLookup(null, "Batch1");
							pnlEast2.add(lookupTCOST_Batch, BorderLayout.CENTER);
							lookupTCOST_Batch.setLookupSQL(SQL_BATCHTCOST());
							lookupTCOST_Batch.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent event) {
									Object[] data = ((_JLookup) event.getSource()).getDataSet();
									if (data != null) {
										
										proj_id = (String) data[04];
										
										pnlState(false, true);

										KEYBOARD_MANAGER.focusNextComponent();
									}
								}
							});
							lookupTCOST_Batch.setReturnColumn(0);
							lookupTCOST_Batch.setPreferredSize(new Dimension(200, 20));
							lookupTCOST_Batch.setEnabled(false);
						}
					}
					{
						JPanel pnlEast3 = new JPanel(new BorderLayout(5, 5));
						pnlEast.add(pnlEast3, BorderLayout.SOUTH);
						pnlEast3.setBorder(JTBorderFactory.createTitleBorder("PCost Batch No."));
						pnlEast3.setPreferredSize(new Dimension(235, 50));// XXX
						{
							lookupPCOST_Batch = new _JLookup(null, "Batch");
							pnlEast3.add(lookupPCOST_Batch, BorderLayout.CENTER);
							lookupPCOST_Batch.setLookupSQL(SQL_BATCHPCOST());
							lookupPCOST_Batch.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent event) {
									Object[] data = ((_JLookup) event.getSource()).getDataSet();
									if (data != null) {
										FncSystem.out("PCOST", SQL_BATCHPCOST());
										
										proj_id = (String) data[04];
										rplf_no_pcost = (String) data[02];
										
										pnlState(true, false);

										KEYBOARD_MANAGER.focusNextComponent();
									}
								}
							});
							lookupPCOST_Batch.setReturnColumn(0);
							lookupPCOST_Batch.setPreferredSize(new Dimension(200, 20));
							lookupPCOST_Batch.setEnabled(true);
						}
					}
				}
			}
			{
				tabCenter = new JTabbedPane();
				pnlMain.add(tabCenter, BorderLayout.CENTER);
				{
					pnlCenter = new JPanel();
					tabCenter.addTab("Processing Cost Particular", null, pnlCenter, null);
					pnlCenter.setLayout(new BorderLayout());
					{
						JScrollPane scrollPCost = new JScrollPane();
						pnlCenter.add(scrollPCost, BorderLayout.CENTER);
						scrollPCost.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

						modelPCost = new model_PCost2();

						tblPCost = new _JTableMain(modelPCost);
						scrollPCost.setViewportView(tblPCost);
					}
					displayPCost2("");
				}
				{
					pnlCenter1 = new JPanel();
					tabCenter.addTab("Transfer Cost Particular", null, pnlCenter1, null);
					pnlCenter1.setLayout(new BorderLayout());
					{
						JScrollPane scrollTCost = new JScrollPane();
						pnlCenter1.add(scrollTCost, BorderLayout.CENTER);
						scrollTCost.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

						modelTCost = new model_TCost2();

						tblTCost = new _JTableMain(modelTCost);
						scrollTCost.setViewportView(tblTCost);
					}
					displayTCost();
				}
//				tabCenter.addChangeListener(new ChangeListener() {
//
//					public void stateChanged(ChangeEvent e) {
//						int selectedTab = ((_JTabbedPane)e.getSource()).getSelectedIndex();
//						if(selectedTab == 0){
//							if(lookupTCOST_Batch.getValue() != null){
//								lookupTCOST_Batch.setValue(null);
//							}
//						}
//						if(selectedTab == 1){
//							if(lookupPCOST_Batch.getValue() != null){
//								lookupPCOST_Batch.setValue(null);
//							}
//						}
//					}
//				});
				tabCenter.addChangeListener(new ChangeListener() {//**ADDED BY JED 2019-02-28***//
					public void stateChanged(ChangeEvent arg0) {
						int selectedTab = ((JTabbedPane)arg0.getSource()).getSelectedIndex();

						if(selectedTab == 0){
							proj_id = "";
							lookupPCOST_Batch.setEnabled(true);
							lookupTCOST_Batch.setEnabled(false);
							lookupTCOST_Batch.setValue(null);
							cmbClass.setEnabled(true);
//							if(lookupTCOST_Batch.getValue() != null){
//								lookupTCOST_Batch.setValue(null);
//							}
						}
						if(selectedTab == 1){
							proj_id = "";
							lookupPCOST_Batch.setEnabled(false);
							lookupTCOST_Batch.setEnabled(true);
							lookupPCOST_Batch.setValue(null);
							cmbClass.setEnabled(false);
//							if(lookupPCOST_Batch.getValue() != null){
//								lookupPCOST_Batch.setValue(null);
//							}
						}
					}
				});
			}
			{
				pnlSouth = new JPanel();
				pnlMain.add(pnlSouth, BorderLayout.SOUTH);
				pnlSouth.setLayout(new GridLayout(1, 10, 3, 3));
				pnlSouth.setPreferredSize(new Dimension(700, 25));// XXX
//				{
//					JLabel lblrplf = new JLabel("RPLF No.");
//					pnlSouth.add(lblrplf);
//					lblrplf.setBounds(10, 10, 120, 22);
//				}
//				{
//					lookupRplf = new _JLookup(null, "Rplf No");
//					pnlSouth.add(lookupRplf);
//					lookupRplf.setReturnColumn(0);
//					lookupRplf.setLookupSQL(SQL_RPLF());
//					lookupRplf.setBounds(35, 10, 200, 18);
//					lookupRplf.addLookupListener(new LookupListener() {
//						public void lookupPerformed(LookupEvent event) {
//							final Object[] data = ((_JLookup) event.getSource()).getDataSet();
//							if (data != null) {
//								openDRF();
//							}
//						}
//					});
//				}
				{
					pnlSouth.add(Box.createHorizontalBox());
					pnlSouth.add(Box.createHorizontalBox());
					pnlSouth.add(Box.createHorizontalBox());
					pnlSouth.add(Box.createHorizontalBox());
				}
				{
					JButton btnPreview = new JButton("Preview");
					pnlSouth.add(btnPreview);
					btnPreview.setActionCommand("Preview");
					btnPreview.setMnemonic(KeyEvent.VK_P);
					btnPreview.addActionListener(this);
				}
				{//**ADDED BY JED DCRF NO. 1489 : PREVIEW SUMMARY OF TRANSACTION REPORT PER DAY**//
					JButton btnSummary = new JButton("Summary");
					pnlSouth.add(btnSummary);
					btnSummary.setActionCommand("Summary");
					btnSummary.setMnemonic(KeyEvent.VK_S);
					btnSummary.addActionListener(this);
				}
				{
					JButton btnCancel = new JButton("Cancel");
					pnlSouth.add(btnCancel);
					btnCancel.setActionCommand("Cancel");
					btnCancel.setMnemonic(KeyEvent.VK_C);
					btnCancel.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							int response = JOptionPane.showConfirmDialog(ProcessingTransferCostReport.this.getTopLevelAncestor(),"Are you sure you want to Clear all fields?   ",
									"Confirm",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
							if (response == JOptionPane.YES_OPTION) {
								int index = tabCenter.getSelectedIndex();
									if(index == 0) {
										modelPCost.clear();
										displayPCost();
										
									}
									
									if(index == 1) {
										modelTCost.clear();
										displayTCost();
									}
								
								//lookupProject.setText(" ");
								refreshTO();
								pnlState(true, true);
							}
						}
					});
				}
			}
		}

		/*if (tabCenter.getSelectedIndex() == 0) {
			lookupPCOST_Batch.setLookupSQL(SQL_BATCHPCOST());
			lookupTCOST_Batch.setLookupSQL(SQL_ENCODINGBATCHPCOST());
			lookupRplf.setLookupSQL(SQL_RPLFPCOST());
		}
		if (tabCenter.getSelectedIndex() == 1) {
			lookupPCOST_Batch.setLookupSQL(SQL_BATCHTCOST());
			lookupTCOST_Batch.setLookupSQL(SQL_ENCODINGBATCHTCOST());
			lookupRplf.setLookupSQL(SQL_RPLFTCOST());
		}*/

	}
	private String[] getClass1() {
		return new String[] {
				"All",
				"Buyer Related",
				"Block - Lot Related",
		};
	}
	
	private void displayPCost() {

		FncTables.clearTable(modelPCost);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		//rowheaderTagAccounts.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		String sql = "SELECT false, pcostdtl_id as \"PCost ID.\", trim(pcostdtl_desc) as \"PCost Desc\"\n" + 
				"FROM mf_processing_cost_dl \n" + 
				"--WHERE status_id = 'A'\n" +
				"GROUP BY pcostdtl_id, pcostdtl_desc\n" + 
				"ORDER BY pcostdtl_desc ASC;";

		pgSelect db = new pgSelect();
		db.select(sql);

		if(db.isNotNull()){ 
			for(int x=0; x < db.getRowCount(); x++){
				modelPCost.addRow(db.getResult()[x]);
				listModel.addElement(modelPCost.getRowCount());
			}	
		}		
		tblPCost.packAll();
	}
	
	private void displayPCost2(String type) {

		FncTables.clearTable(modelPCost);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		//rowheaderTagAccounts.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		String sql = "SELECT false, pcostdtl_id as \"PCost ID.\", trim(pcostdtl_desc) as \"PCost Desc\" \n" + 
				"				FROM mf_processing_cost_dl\n" + 
				"				WHERE (case when '"+type+"' = '' then true else pcostdtl_type = '"+type+"' end)\n" + 
				"				GROUP BY pcostdtl_id, pcostdtl_desc\n" + 
				"				ORDER BY pcostdtl_desc ASC";

		pgSelect db = new pgSelect();
		db.select(sql);

		if(db.isNotNull()){ 
			for(int x=0; x < db.getRowCount(); x++){
				modelPCost.addRow(db.getResult()[x]);
				listModel.addElement(modelPCost.getRowCount());
			}	
		}		
		tblPCost.packAll();
	}
	
	private void displayTCost() {

		FncTables.clearTable(modelTCost);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		//rowheaderTagAccounts.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		String sql = "SELECT false, tcostdtl_id as \"TCost ID.\", trim(tcostdtl_desc) as \"TCost Desc\"\n" + 
				"FROM mf_transfer_cost_dl \n" + 
				"WHERE status_id = 'A'\n" +
				"GROUP BY tcostdtl_id, tcostdtl_desc\n" + 
				"ORDER BY tcostdtl_desc ASC;";

		pgSelect db = new pgSelect();
		db.select(sql);

		if(db.isNotNull()){ 
			for(int x=0; x < db.getRowCount(); x++){
				modelTCost.addRow(db.getResult()[x]);
				listModel.addElement(modelTCost.getRowCount());
			}	
		}		
		tblTCost.packAll();
	}
	
	public void actionPerformed(ActionEvent arg0) {
		String actionCommand = arg0.getActionCommand();
		ArrayList<String> iftrue = new ArrayList<String>();

		if(actionCommand.equals("Preview")){
			ArrayList<String> listparticular = new ArrayList<String>();
			ArrayList<String> listcost_id = new ArrayList<String>();
			
			batch = lookupPCOST_Batch.getText();
			batch1 = lookupTCOST_Batch.getText();
			emp_code = UserInfo.EmployeeCode;
			String phase = lookupPhase.getText();
			String co_name = "";
			if(proj_id.equals("019")) {
				co_name = "VERDANTPOINT DEVELOPMENT CORPORATION";
			}else {
				co_name = "CENQHOMES DEVELOPMENT CORPORATION";
			}

			if (tabCenter.getSelectedIndex() == 0) {

				/*for (int i = 0; i < modelPCost.getRowCount(); i++) {
					Boolean SelectedItem = (Boolean) modelPCost.getValueAt(i, 0);
					String particular = (String) modelPCost.getValueAt(i, 2);
					String cost_id = (String) modelPCost.getValueAt(i, 1);

					if (SelectedItem) {
						listparticular.add(particular);
						listcost_id.add(cost_id);
						iftrue.add(modelPCost.getValueAt(i, 2).toString());

					}
				}
				
				if (iftrue.isEmpty()) {
					JOptionPane.showMessageDialog(getContentPane(),"Please select first for preview ","Preview", JOptionPane.OK_OPTION);
					return;
				}

				System.out.println("**************" + listcost_id);
				Map<String, Object> mapParameters = new HashMap<String, Object>();

				mapParameters.put("proj_name", txtProject.getText());
				mapParameters.put("emp_alias", UserInfo.Alias);
				mapParameters.put("emp_fname", UserInfo.FirstName);
				mapParameters.put("emp_lname", UserInfo.LastName);
				mapParameters.put("date_from", dateFrom.getDate());
				mapParameters.put("date_to", dateTo.getDate());
				mapParameters.put("part_desc", listparticular.toString().replace("[", "").replace("]", ""));
				mapParameters.put("pcost_id", listcost_id);
				mapParameters.put("projcode", lookupProject.getValue());
				mapParameters.put("buyer", lookupBuyer.getValue());
				mapParameters.put("batch_no", batch);
				mapParameters.put("batch_no1", batch1);
				mapParameters.put("subrptPCostSummary", this.getClass().getResourceAsStream("/Reports/subrptPCostSummary.jasper"));
				mapParameters.put("subrptPCostSummary2", this.getClass().getResourceAsStream("/Reports/subrptPCostSummary2.jasper"));

//				if (batch1.equals(null) || batch1.equals("")) {
//					FncReport.generateReport("/Reports/rptProcessingCost.jasper", "Processing Cost Report", mapParameters);
//				} else {
//					FncReport.generateReport("/Reports/rptProcessingCost2.jasper", "Processing Cost Report", mapParameters);
//				}
				FncReport.generateReport("/Reports/rptProcessingCost_v3.jasper", "Processing Cost Report", mapParameters);

				//----added by JED 2018-09-24 : DCRF no. 771 additional report for DB req

				if(checkEmployee(emp_code).equals(null) || checkEmployee(emp_code).equals("")){
					
				}else{
					if(rplfExist_pcost(batch).equals(null) || rplfExist_pcost(batch).equals("")){
						
					}else{
						System.out.printf("listcost_id: %s\n", listcost_id);
						System.out.printf("listparticulars: %s\n", listparticular);

						String rplf_no = rplfExist_pcost(batch);
						String targetValue = "220";

						Map<String, Object> mapParameters1 = new HashMap<String, Object>();

						mapParameters1.put("batch_no", batch);
						mapParameters1.put("rplf_no", rplf_no);
						mapParameters1.put("emp_code", UserInfo.EmployeeCode);

						if(getClientNo_pcost(rplf_no) <= 1){//added by jed 2018-10-05 : separate batching report from individual

							FncReport.generateReport("/Reports/rptPCOSTDisbursement_Req.jasper", "Disbursement Request", mapParameters1);

						}else{

							FncReport.generateReport("/Reports/rptPCOSTDisbursement_Req_batch.jasper", "Disbursement Request", mapParameters1);
						}


						//---added by JED 2018-11-15 : DRCF no. 855 to preview Affidavit report---//
						Map<String, Object> mapParameters2 = new HashMap<String, Object>();

						mapParameters2.put("batch_no", batch);
						mapParameters2.put("emp_code", UserInfo.EmployeeCode);

						if(useLoop(listcost_id, targetValue) == true){

							FncReport.generateReport("/Reports/rptPCOST_Affidavit.jasper", "Affidavit", mapParameters2);
							FncReport.generateReport("/Reports/rptPCost_TechnicalDescription.jasper", "Technical Description", mapParameters2);
						}

					}
				}*/
				
				//**ADDED BY JED VICEDO 2019-07-02 : PREVIEW SELECTED PARTICULARS**//
				//**EDITED BY JED VICEDO 2019-07-03 : CHANGE PREVIEW OF REPORT INTO FUNCTION**//
				for (int x = 0; x < modelPCost.getRowCount(); x++) {
					Boolean isSelected = (Boolean) modelPCost.getValueAt(x, 0);
						if(isSelected) {
							String pcost_id = (String) modelPCost.getValueAt(x, 1);
							String particulars = (String) modelPCost.getValueAt(x, 2);
							listcost_id.add(String.format("%s", pcost_id));
							listparticular.add(String.format("%s", particulars));
							
					}
				}
					if(listcost_id.isEmpty()) {
						JOptionPane.showMessageDialog(getContentPane(), "Please select first for preview!", "Error", JOptionPane.ERROR_MESSAGE);
				}else {
					
					String pcost_id = listcost_id.toString().replaceAll("\\[", "").replaceAll("\\]","");
					String particulars = listparticular.toString().replaceAll("\\[", "").replaceAll("\\]","");
					System.out.printf("The value of new pcost_id: (%s)\n", pcost_id);
					System.out.printf("The value of new particulars: (%s)\n", particulars);
					
					Map<String, Object> mapParameters = new HashMap<String, Object>();
					mapParameters.put("proj_name", txtProject.getText());
					mapParameters.put("emp_alias", UserInfo.Alias);
					mapParameters.put("emp_fname", UserInfo.FirstName);
					mapParameters.put("emp_lname", UserInfo.LastName);
					mapParameters.put("date_from", dateFrom.getDate());
					mapParameters.put("date_to", dateTo.getDate());
					mapParameters.put("part_desc", particulars);
					mapParameters.put("pcost_id", pcost_id);
					mapParameters.put("projcode", lookupProject.getValue());
					mapParameters.put("buyer", lookupBuyer.getValue());
					mapParameters.put("batch_no", batch);
					mapParameters.put("batch_no1", batch1);
					mapParameters.put("phase", phase);
					mapParameters.put("co_name", co_name);
					mapParameters.put("rplf_no", rplf_no_pcost);
					//mapParameters.put("subrptPCostSummary", this.getClass().getResourceAsStream("/Reports/subrptPCostSummary.jasper"));
					mapParameters.put("subrptPCostSummary2", this.getClass().getResourceAsStream("/Reports/subrptPCostSummary2.jasper"));
					
					if(checkRPT(batch) == true) {//***ADDED BY JED 2019-07-16 DCRF NO. 1122 : ENHANCE REPORT FOR RPT LOTS AND HOUSE***//
						FncReport.generateReport("/Reports/rptProcessingCost_RPT.jasper", "Processing Cost Report RPT", mapParameters);
					}else {
						if(batch.equals("0000001022")) {//**ADDED BY JED VICEDO 2019-11-21 DCRF NO. 1291 : ADDITIONAL REMARKS FOR REPORT SPECIAL CASES**//
							FncReport.generateReport("/Reports/rptProcessingCost_v4.jasper", "Processing Cost Report", mapParameters);
						}else {
							//FncReport.generateReport("/Reports/rptProcessingCost_v3.jasper", "Processing Cost Report", mapParameters);
							//FncReport.generateReport("/Reports/rptProcessingCostRevise.jasper", "Processing Cost Report", mapParameters);
							FncReport.generateReport("/Reports/rptProcessingCostRevise_Meralco.jasper", "Processing Cost Report", mapParameters);
							System.out.println("rptProcessingCostRevise_Meralco.jasper");
						}
						
					}

					//----added by JED 2018-09-24 : DCRF no. 771 additional report for DB req
//					if(checkEmployee(emp_code).equals(null) || checkEmployee(emp_code).equals("")){}
//					else{
//						if(rplfExist_pcost(batch).equals(null) || rplfExist_pcost(batch).equals("")){}
//						else{
//							System.out.printf("listcost_id: %s\n", pcost_id);
//							System.out.printf("listparticulars: %s\n", particulars);
//
//							String rplf_no = rplfExist_pcost(batch);
//							String targetValue = "220";
//
//							Map<String, Object> mapParameters1 = new HashMap<String, Object>();
//							mapParameters1.put("batch_no", batch);
//							mapParameters1.put("rplf_no", rplf_no);
//							mapParameters1.put("emp_code", UserInfo.EmployeeCode);
//							mapParameters1.put("co_name", co_name);
//							if(getClientNo_pcost(rplf_no) <= 1){//added by jed 2018-10-05 : separate batching report from individual
//								FncReport.generateReport("/Reports/rptPCOSTDisbursement_Req.jasper", "Disbursement Request", mapParameters1);
//							}else{
//								FncReport.generateReport("/Reports/rptPCOSTDisbursement_Req_batch.jasper", "Disbursement Request", mapParameters1);
//							}
//
//							//---added by JED 2018-11-15 : DRCF no. 855 to preview Affidavit report---//
//							Map<String, Object> mapParameters2 = new HashMap<String, Object>();
//							mapParameters2.put("batch_no", batch);
//							mapParameters2.put("emp_code", UserInfo.EmployeeCode);
//							
//							if(useLoop(listcost_id, targetValue) == true){
//								FncReport.generateReport("/Reports/rptPCOST_Affidavit.jasper", "Affidavit", mapParameters2);
//								FncReport.generateReport("/Reports/rptPCost_TechnicalDescription.jasper", "Technical Description", mapParameters2);
//							}
//						}
//					}
					
					if(UserInfo.EmployeeCode.equals("901128")){
						if(rplfExist_pcost(batch).equals(null) || rplfExist_pcost(batch).equals("")){}
						else{
							System.out.printf("listcost_id: %s\n", pcost_id);
							System.out.printf("listparticulars: %s\n", particulars);

							String rplf_no = rplfExist_pcost(batch);
							String targetValue = "220";

							Map<String, Object> mapParameters1 = new HashMap<String, Object>();
							mapParameters1.put("batch_no", batch);
							mapParameters1.put("rplf_no", rplf_no);
							mapParameters1.put("emp_code", UserInfo.EmployeeCode);
							mapParameters1.put("co_name", co_name);
							if(getClientNo_pcost(rplf_no) <= 1){//added by jed 2018-10-05 : separate batching report from individual
								FncReport.generateReport("/Reports/rptPCOSTDisbursement_Req.jasper", "Disbursement Request", mapParameters1);
							}else{
								FncReport.generateReport("/Reports/rptPCOSTDisbursement_Req_batch.jasper", "Disbursement Request", mapParameters1);
							}

							//---added by JED 2018-11-15 : DRCF no. 855 to preview Affidavit report---//
							Map<String, Object> mapParameters2 = new HashMap<String, Object>();
							mapParameters2.put("batch_no", batch);
							mapParameters2.put("emp_code", UserInfo.EmployeeCode);
							
							if(useLoop(listcost_id, targetValue) == true){
								FncReport.generateReport("/Reports/rptPCOST_Affidavit.jasper", "Affidavit", mapParameters2);
								FncReport.generateReport("/Reports/rptPCost_TechnicalDescription.jasper", "Technical Description", mapParameters2);
							}
						}
					}
				}
			}

			if (tabCenter.getSelectedIndex() == 1) {

//				for (int i = 0; i < modelTCost.getRowCount(); i++) {
//					Boolean SelectedItem = (Boolean) modelTCost.getValueAt(i, 0);
//					String particular = (String) modelTCost.getValueAt(i, 2);
//					String cost_id = (String) modelTCost.getValueAt(i, 1);
//
//					if (SelectedItem) {
//						listparticular.add(particular);
//						listcost_id.add(cost_id);
//						iftrue.add(modelTCost.getValueAt(i, 2).toString());
//
//					}
//				}
//
//				if (iftrue.isEmpty()) {
//					JOptionPane.showMessageDialog(getContentPane(),"Please select first for preview ","Preview", JOptionPane.OK_OPTION);
//					return;
//				}
//				System.out.printf("The value of new pcost_id: (%s)\n", listparticular);
//				System.out.printf("The value of new pcost_id: (%s)\n", listcost_id);
//				//System.out.println("**************" + listcost_id);
//				Map<String, Object> mapParameters = new HashMap<String, Object>();
//
//				mapParameters.put("proj_name", txtProject.getText());
//				mapParameters.put("emp_alias", UserInfo.Alias);
//				mapParameters.put("emp_fname", UserInfo.FirstName);
//				mapParameters.put("emp_lname", UserInfo.LastName);
//				mapParameters.put("date_from", dateFrom.getDate());
//				mapParameters.put("date_to", dateTo.getDate());
//				mapParameters.put("part_desc", listparticular.toString().replace("[", "").replace("]", ""));
//				mapParameters.put("tcost_id", listcost_id);
//				mapParameters.put("projcode", lookupProject.getValue());
//				mapParameters.put("buyer", lookupBuyer.getValue());
//				mapParameters.put("batch_no", batch);
//				mapParameters.put("batch_no1", batch1);
//				mapParameters.put("subrptTCostSummary", this.getClass().getResourceAsStream("/Reports/subrptTCostSummary.jasper"));
//				mapParameters.put("subrptTCostSummary2", this.getClass().getResourceAsStream("/Reports/subrptTCostSummary2.jasper"));
//
//				/*if (batch1.equals(null) || batch1.equals("")) {
//					FncReport.generateReport("/Reports/rptTransferCost.jasper", "Processing Cost Report", mapParameters);
//				} else {
//					FncReport.generateReport("/Reports/rptTransferCost2.jasper", "Processing Cost Report", mapParameters);
//				}*/
//				FncReport.generateReport("/Reports/rptTransferCost2.jasper", "Transfer Cost Report", mapParameters);
//
//				//----added by JED 2018-09-24 : DCRF no. 771 additional report for DB req
//
//				if(checkEmployee(emp_code).equals(null) || checkEmployee(emp_code).equals("")){
//				}else{
//					if(rplfExist_tcost(batch1).equals(null) || rplfExist_tcost(batch1).equals("")){}
//					else{
//						String rplf_no = rplfExist_tcost(batch1);
//
//						Map<String, Object> mapParameters1 = new HashMap<String, Object>();
//
//						mapParameters1.put("batch_no", batch1);
//						mapParameters1.put("rplf_no", rplf_no);
//						mapParameters1.put("emp_code", UserInfo.EmployeeCode);
//
//						if(getClientNo_tcost(rplf_no) == 1){//added by jed 2018-10-05 : separate batching report from individual
//
//							FncReport.generateReport("/Reports/rptTCOSTDisbursement_Req.jasper", "Disbursement Request", mapParameters1);
//
//						}else{
//
//							FncReport.generateReport("/Reports/rptTCOSTDisbursement_Req_batch.jasper", "Disbursement Request", mapParameters1);
//
//						}
//					}
//				}
				
				for (int x = 0; x < modelTCost.getRowCount(); x++) {
					Boolean isSelected = (Boolean) modelTCost.getValueAt(x, 0);
						if(isSelected) {
							String tcost_id = (String) modelTCost.getValueAt(x, 1);
							String particulars = (String) modelTCost.getValueAt(x, 2);
							listcost_id.add(String.format("%s", tcost_id));
							listparticular.add(String.format("%s", particulars));
							
					}
				}

				if (listcost_id.isEmpty()) {
					JOptionPane.showMessageDialog(getContentPane(),"Please select first for preview ","Preview", JOptionPane.OK_OPTION);
					return;
				}
				else {
					
					String tcost_id = listcost_id.toString().replaceAll("\\[", "").replaceAll("\\]","");
					String particulars = listparticular.toString().replaceAll("\\[", "").replaceAll("\\]","");
					System.out.printf("The value of new tcost_id: (%s)\n", tcost_id);
					System.out.printf("The value of new particulars: (%s)\n", particulars);
					
					System.out.printf("The value of new tcost_id: (%s)\n", listparticular);
					System.out.printf("The value of new tcost_id: (%s)\n", listcost_id);
					//System.out.println("**************" + listcost_id);
					Map<String, Object> mapParameters = new HashMap<String, Object>();

					mapParameters.put("proj_name", txtProject.getText());
					mapParameters.put("emp_alias", UserInfo.Alias);
					mapParameters.put("emp_fname", UserInfo.FirstName);
					mapParameters.put("emp_lname", UserInfo.LastName);
					mapParameters.put("date_from", dateFrom.getDate());
					mapParameters.put("date_to", dateTo.getDate());
					mapParameters.put("part_desc", particulars);
					mapParameters.put("tcost_id", tcost_id);
					mapParameters.put("projcode", lookupProject.getValue());
					mapParameters.put("buyer", lookupBuyer.getValue());
					mapParameters.put("batch_no", batch);
					mapParameters.put("batch_no1", batch1);
					mapParameters.put("phase", phase);
					mapParameters.put("co_name", co_name);
					mapParameters.put("subrptTCostSummary", this.getClass().getResourceAsStream("/Reports/subrptTCostSummary.jasper"));
					mapParameters.put("subrptTCostSummary2", this.getClass().getResourceAsStream("/Reports/subrptTCostSummary2.jasper"));

					/*if (batch1.equals(null) || batch1.equals("")) {
						FncReport.generateReport("/Reports/rptTransferCost.jasper", "Processing Cost Report", mapParameters);
					} else {
						FncReport.generateReport("/Reports/rptTransferCost2.jasper", "Processing Cost Report", mapParameters);
					}*/
					FncReport.generateReport("/Reports/rptTransferCost2.jasper", "Transfer Cost Report", mapParameters);

					//----added by JED 2018-09-24 : DCRF no. 771 additional report for DB req

//					if(checkEmployee(emp_code).equals(null) || checkEmployee(emp_code).equals("")){
//					}else{
//						if(rplfExist_tcost(batch1).equals(null) || rplfExist_tcost(batch1).equals("")){}
//						else{
//							String rplf_no = rplfExist_tcost(batch1);
//
//							Map<String, Object> mapParameters1 = new HashMap<String, Object>();
//
//							mapParameters1.put("batch_no", batch1);
//							mapParameters1.put("rplf_no", rplf_no);
//							mapParameters1.put("emp_code", UserInfo.EmployeeCode);
//
//							if(getClientNo_tcost(rplf_no) == 1){//added by jed 2018-10-05 : separate batching report from individual
//
//								FncReport.generateReport("/Reports/rptTCOSTDisbursement_Req.jasper", "Disbursement Request", mapParameters1);
//
//							}else{
//
//								FncReport.generateReport("/Reports/rptTCOSTDisbursement_Req_batch.jasper", "Disbursement Request", mapParameters1);
//
//							}
//						}
//					}
					
					if(UserInfo.EmployeeCode.equals("901128")){
						if(rplfExist_tcost(batch1).equals(null) || rplfExist_tcost(batch1).equals("")){}
						else{
							String rplf_no = rplfExist_tcost(batch1);

							Map<String, Object> mapParameters1 = new HashMap<String, Object>();

							mapParameters1.put("batch_no", batch1);
							mapParameters1.put("rplf_no", rplf_no);
							mapParameters1.put("emp_code", UserInfo.EmployeeCode);

							if(getClientNo_tcost(rplf_no) == 1){//added by jed 2018-10-05 : separate batching report from individual

								FncReport.generateReport("/Reports/rptTCOSTDisbursement_Req.jasper", "Disbursement Request", mapParameters1);

							}else{

								FncReport.generateReport("/Reports/rptTCOSTDisbursement_Req_batch.jasper", "Disbursement Request", mapParameters1);

							}
						}
					}
				}
			}
		}
		
		//**ADDED BY JED DCRF NO. 1489 : PREVIEW SUMMARY OF TRANSACTION REPORT PER DAY**//
		if(actionCommand.equals("Summary")) {
			//lookupSCompany.setValue(null);
			//txtSCompany.setText("");
			//cmbParticular.setSelectedIndex(0);
			//txtSearch.setText("");
			//txtSearch.setEditable(false);
			//modelSummary.clear();
			//JOptionPane.showOptionDialog(getContentPane(), pnlSummary, "Report Summary",
			//		JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, new Object[] {}, null);
			
			pnlTransactionSummaryReport ps = new pnlTransactionSummaryReport(FncGlobal.homeMDI, "Report Summary");
			ps.setLocationRelativeTo(this);
			ps.setVisible(true);
		}
	}

	private static boolean useLoop(ArrayList<String> arr, String targetValue) {//--added by JED 2018-11-15: DRCF no. 855 for previewing Affidavit---//
		for(String s: arr){//--store array in String "s" then compare it on the target value--//
			if(s.equals(targetValue))
				return true;
		}
		return false;

	}
	
	private static boolean checkRPT(String batch_no) {

		Boolean x = false;

		String SQL = "select\n" + 
				"*\n" + 
				"from rf_processing_cost\n" + 
				"where batch_no = '"+batch_no+"'\n" + 
				"and pcostid_dl in ('215', '216')\n" + 
				"and status_id = 'A'";

		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()) {
			if ((String) db.getResult()[0][1] == null || db.getResult()[0][1].equals("null")) {
				x = false;
			}else {
				x = true;
			}
		}else {
			x = false;
		}
		return x;			
	}

	private static String rplfExist_pcost(String batch){//----added by JED  2018-09-24 : DCRF no. 771 additional report for DB req

		String rplfExist_pcost = "";
		String sql = 
				"select rplf_no from rf_processing_cost where batch_no = '"+batch+"' AND status_id = 'A' limit 1";

		pgSelect db = new pgSelect();
		db.select(sql);

		if (db.isNotNull()) {
			if ((String) db.getResult()[0][0] == null || db.getResult()[0][0].equals("null")) {
			} else {
				rplfExist_pcost = (String) db.getResult()[0][0].toString();
				System.out.println(rplfExist_pcost);
			}
		} else {
			rplfExist_pcost = "";
			System.out.println(rplfExist_pcost);
		}

		return rplfExist_pcost;
	}

	private static Object [] getPCOST_info(String batch) {			

		String strSQL = 
				"select pcostid_dl, rplf_no from rf_processing_cost where batch_no = '"+batch+"' limit 1";


		pgSelect db = new pgSelect();
		db.select(strSQL);		

		if(db.isNotNull()){
			return db.getResult()[0];
		}else{
			return null;
		}
	}

	private static String rplfExist_tcost(String batch1){//----added by JED  2018-09-24 : DCRF no. 771 additional report for DB req

		String rplfExist_tcost = "";
		String sql = 
				"select rplf_no from rf_transfer_cost where batch_no = '"+batch1+"' and status_id = 'A' limit 1";

		pgSelect db = new pgSelect();
		db.select(sql);

		if (db.isNotNull()) {
			if ((String) db.getResult()[0][0] == null || db.getResult()[0][0].equals("null")) {
			} else {
				rplfExist_tcost = (String) db.getResult()[0][0].toString();
			}
		} else {
			rplfExist_tcost = "";
		}

		return rplfExist_tcost;
	}


	private static String checkEmployee(String emp_code){

		String employee_code = "";
		String sql =
				"select\n" + 
						"emp_code\n" + 
						"from em_employee\n" + 
						"where emp_code = '"+emp_code+"' and\n" + 
						"div_code = '05' and \n" + 
						"dept_code = '18'" ;

		pgSelect db = new pgSelect();
		db.select(sql);

		if (db.isNotNull()){
			if ((String) db.getResult()[0][0] == null || db.getResult()[0][0].equals("null")){
			}else{
				employee_code = (String) db.getResult()[0][0].toString();
				System.out.printf("The employee code is: %s", employee_code);
			}
		}else{
			employee_code = "";
		}
		return employee_code;
	}

	private static Integer getClientNo_pcost (String rplf_no){

		Integer no_of_clients = 0;
		String sql =
				"Select sum(a.count)::INT from (Select distinct on (entity_id) 1 as count, entity_id from rf_processing_cost where rplf_no = '"+rplf_no+"') a" ;

		pgSelect db = new pgSelect();
		db.select(sql);

		if(db.isNotNull()){
			if((Integer) db.getResult()[0][0] == null || db.getResult()[0][0].equals("null")){
			}else{
				no_of_clients = (Integer) db.getResult()[0][0];
			}
		}else{
			no_of_clients = 0;
		}

		return no_of_clients;	
	}

	private static Integer getClientNo_tcost (String rplf_no){

		Integer no_of_clients = 0;
		String sql =
				"Select sum(a.count)::INT from (Select distinct on (entity_id) 1 as count, entity_id from rf_transfer_cost where rplf_no = '"+rplf_no+"') a" ;

		pgSelect db = new pgSelect();
		db.select(sql);

		if(db.isNotNull()){
			if((Integer) db.getResult()[0][0] == null || db.getResult()[0][0].equals("null")){
			}else{
				no_of_clients = (Integer) db.getResult()[0][0];
			}
		}else{
			no_of_clients = 0;
		}

		return no_of_clients;	
	}

	private static String SQL_ENCODINGBATCHPCOST() {//XXX Batch ID
		SQL = "SELECT batch_no as \"Batch No.\", get_employee_name(requested_by) as \"Requested By\", rplf_no as \"RPLF No\", tran_date::DATE::TIMESTAMP as \"Trans Date\"\n" + 
				"FROM rf_transfer_cost\n" + 
				"WHERE created_by = '"+UserInfo.EmployeeCode+"' \n" +
				//"AND date_created::timestamp IN (now()) \n" +
				"AND status_id = 'A' \n" +
				"GROUP BY batch_no, requested_by, rplf_no, tran_date::DATE::TIMESTAMP\n" + 
				"ORDER BY batch_no::INT DESC;";
		return SQL;
	}
	private static String SQL_ENCODINGBATCHTCOST() {//XXX Batch ID
		SQL = "SELECT batch_no as \"Batch No.\", get_employee_name(requested_by) as \"Requested By\", rplf_no as \"RPLF No\", tran_date::DATE::TIMESTAMP as \"Trans Date\"\n" + 
				"FROM rf_transfer_cost\n" + 
				"WHERE created_by = '"+UserInfo.EmployeeCode+"' \n" +
				//"AND date_created::timestamp IN (now()) \n" +
				"AND status_id = 'A' \n" +
				"GROUP BY batch_no, requested_by, rplf_no, tran_date::DATE::TIMESTAMP\n" + 
				"ORDER BY batch_no::INT DESC;";

		return SQL;
	}
//	private static String SQL_BATCHPCOST() {//XXX Batch ID
//		SQL = "SELECT batch_no as \"Batch No.\", get_employee_name(requested_by) as \"Requested By\", rplf_no as \"RPLF No\", tran_date::DATE::TIMESTAMP as \"Trans Date\"\n" + 
//				"FROM rf_processing_cost\n" + 
//				//"WHERE '"+UserInfo.Department+"' = '04'\n" +
//				//"AND date_created::timestamp IN (now()) \n" +
//				"WHERE status_id = 'A' \n" +
//				"GROUP BY batch_no, requested_by, tran_date::DATE::TIMESTAMP, rplf_no\n" + 
//				"ORDER BY batch_no::INT DESC;";
//		return SQL;
	
	//**change by JED 2019-01-29**//
	private static String SQL_BATCHPCOST() {//XXX Batch ID
		/*EDITED BY JED 2021-10-21 DCRF NO. 1757*/
//		SQL = "SELECT distinct on (batch_no::int, requested_by) batch_no as \"Batch No.\", get_employee_name(requested_by) as \"Requested By\", rplf_no as \"RPLF No\", tran_date::DATE::TIMESTAMP as \"Trans Date\", projcode as \"Projcode\"\n" + 
//				"FROM rf_processing_cost  \n" + 
//				"WHERE status_id = 'A'\n" + 
//				"--GROUP BY batch_no, requested_by, /*tran_date::DATE::TIMESTAMP,*/ rplf_no\n" + 
//				"group by batch_no, requested_by, rplf_no, tran_date::DATE::TIMESTAMP, projcode\n" + 
//				"ORDER BY batch_no::INT DESC, requested_by";
		
		SQL = "select distinct on (batch_no, rplf_no)\n" + 
				"batch_no as \"Batch No.\",\n" + 
				"c.entity_name as \"Requested By\",\n" + 
				"rplf_no as \"RPLF No.\",\n" + 
				"tran_date::DATE::TIMESTAMP as \"Trans Date\",\n" + 
				"projcode as \"Projcode\"\n" + 
				"from rf_processing_cost a\n" + 
				"left join em_employee b on a.requested_by = b.emp_code\n" + 
				"left join rf_entity c on b.entity_id = c.entity_id\n" + 
				"where a.status_id = 'A'\n" + 
				"--and batch_no = '0000004912' \n" + 
				"order by batch_no DESC, rplf_no DESC";
		
		return SQL;
	}
	private static String SQL_BATCHTCOST() {//XXX Batch ID
		SQL = "SELECT batch_no as \"Batch No.\", get_employee_name(requested_by) as \"Requested By\", rplf_no as \"RPLF No\", tran_date::DATE::TIMESTAMP as \"Trans Date\", projcode as \"Projcode\"\n" + 
				"FROM rf_transfer_cost\n" + 
				//"WHERE '"+UserInfo.Department+"' = '04'\n" +
				//"AND date_created::timestamp IN (now()) \n" +
				"WHERE status_id = 'A' \n" +
				"GROUP BY batch_no, requested_by, tran_date::DATE::TIMESTAMP, rplf_no, projcode\n" + 
				"ORDER BY batch_no::INT DESC;";

		return SQL;
	}

	private static String SQL_RPLF() {//XXX Batch ID

		SQL = "SELECT rplf_no as \"RPLF No\", get_client_name(entity_id1) as \"Payee\", rplf_date::DATE::TIMESTAMP as \"Trans Date\"\n" + 
				"FROM rf_request_header\n" + 
				"WHERE rplf_no in (select rplf_no from rf_transfer_cost where status_id = 'A' and rplf_no is not null union all select rplf_no from rf_processing_cost where status_id = 'A' and rplf_no is not null) \n" +
				"GROUP BY rplf_no, entity_id1, rplf_date::DATE::TIMESTAMP\n" + 
				"ORDER BY rplf_no::INT DESC;";

		return SQL;
	}
	
	private void refreshTO() {

		/*
		 * for (int i = 0; i < modelPCost.getRowCount(); i++) { Boolean SelectedItem =
		 * (Boolean) modelPCost.getValueAt(i, 0); //int row =
		 * tblPCost.convertRowIndexToModel(tblPCost.getSelectedRow());
		 * 
		 * if(SelectedItem){ modelPCost.setValueAt(false, i, 0); } }
		 * 
		 * for (int i = 0; i < modelTCost.getRowCount(); i++) { Boolean SelectedItem =
		 * (Boolean) modelTCost.getValueAt(i, 0); //int row =
		 * tblPCost.convertRowIndexToModel(tblPCost.getSelectedRow());
		 * 
		 * if(SelectedItem){ modelTCost.setValueAt(false, i, 0); } }
		 */

		//if (lookupProject.getText().equals(" ")) {
			lookupCompany.setValue(null);
			lookupProject.setValue(null);
			lookupPhase.setValue(null);
			txtCompany.setText("Select Company");
			txtProject.setText("All Projects");
			txtPhase.setText("All Phases");
			lookupPCOST_Batch.setValue(null);
			cmbClass.setSelectedItem("All");
			//lookupBuyer.setText("");
			lookupBuyer.setValue(null);
			txtBuyer.setText("");
			//dateFrom.setDate(Calendar.getInstance().getTime());
			//dateTo.setDate(Calendar.getInstance().getTime());
			dateTo.setDate(FncGlobal.getDateToday());
			lookupTCOST_Batch.setValue(null);
			//chkboxListSummary.setSelected(false);
			//txtrplf.se	tText("");
		//}
	} // refreshTO()
	private void openDRF(){

		RequestForPayment drf = new RequestForPayment();
		Home_JSystem.addWindow(drf);

		RequestForPayment.co_id 		= lookupCompany.getValue();	
		RequestForPayment.company		= txtCompany.getText();	
		RequestForPayment.lookupCompany.setValue(lookupCompany.getValue());
		//RequestForPayment.tagCompany.setTag(company);
		//RequestForPayment.company_logo = company_logo;

		RequestForPayment.lblDRF_no.setEnabled(true);	
		RequestForPayment.lookupDRF_no.setEnabled(true);	
		RequestForPayment.lookupDRF_no.setLookupSQL(RequestForPayment.getDRF_no());

		RequestForPayment.btnAddNew.setEnabled(true);
		RequestForPayment.btnCancel.setEnabled(true);

		String rplf = lookupRplf.getValue().toString().trim();

		if (rplf.equals("")) {}
		else {			
			RequestForPayment.drf_no  = rplf;
			RequestForPayment.lookupDRF_no.setValue(RequestForPayment.drf_no);	

			RequestForPayment.setDRF_header(RequestForPayment.drf_no);
			RequestForPayment.displayDRF_details(RequestForPayment.modelDRF_part, RequestForPayment.rowHeaderDRF, RequestForPayment.modelDRF_part_total, RequestForPayment.drf_no );	
			RequestForPayment.btnAddNew.setEnabled(false);
			RequestForPayment.btnRefresh.setEnabled(true);
			RequestForPayment.btnPreview.setEnabled(true);
			if(RequestForPayment.isPVcreated()==true) {RequestForPayment.btnEdit.setEnabled(false);} 
			else {RequestForPayment.btnEdit.setEnabled(true);}
			RequestForPayment.mnidelete.setEnabled(false);
			RequestForPayment.mniaddrow.setEnabled(false);
			RequestForPayment.mnisetupPV.setEnabled(true);
		}
	}
	
	private static void pnlState (Boolean PCOST, Boolean TCOST) {
	
		tabCenter.setEnabledAt(0, PCOST);
		tabCenter.setEnabledAt(1, TCOST);
		
	}
}