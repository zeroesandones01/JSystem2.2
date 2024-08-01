package Buyers.LoansManagement;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.jdesktop.swingx.JXPanel;

import Accounting.Disbursements.RequestForPayment;
import Buyers.CreditandCollections._RealTimeDebit;
import Database.pgSelect;
import Database.pgUpdate;
import DateChooser._JDateChooser;
import Functions.FncGlobal;
import Functions.FncLookAndFeel;
import Functions.FncReport;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import Renderer.DateRenderer;
import components.JTBorderFactory;
import components._JTableMain;
import interfaces._GUI;
import tablemodel.model_hdmf_borrower_val;
import tablemodel.model_hdmf_remConversion;

public class hdmfMon_REM extends JXPanel implements _GUI {

	private static final long serialVersionUID = 1866421685836756566L;
	private static PagibigStatusMonitoring_v2 hdmfMainMod;
	static Dimension size = new Dimension(738, 351);

	private JLabel lblFrom;
	private JLabel lblBat;

	private _JDateChooser dteFrom;
	private _JLookup txtBat;

	private JXPanel panGrid;

	public static JList rowREM;
	private _JTableMain tblREM;
	private JScrollPane scrollREM;
	private model_hdmf_remConversion modelremConversion;

	private JTextArea txtName;
	private JTextArea txtRemarks; 

	private JScrollPane scrName;
	private JScrollPane scrRemarks;

	private String strBat = ""; 
	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

	private JComboBox cboStatus;  
	public static Integer intIndex;
	private static JSplitPane splitReleasedLoanDetail; 

	public hdmfMon_REM(PagibigStatusMonitoring_v2 psm) {
		this.hdmfMainMod = psm;
		initGUI();
	}

	public hdmfMon_REM(boolean isDoubleBuffered) {
		super(isDoubleBuffered);

	}

	public hdmfMon_REM(LayoutManager layout) {
		super(layout);

	}

	public hdmfMon_REM(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);

	}

	public void initGUI() {
		this.setLayout(new BorderLayout(5, 5));
		this.setPreferredSize(size);
		{
			JXPanel panMain = new JXPanel(new BorderLayout(5, 5));
			this.add(panMain, BorderLayout.CENTER);
			panMain.setBorder(new EmptyBorder(5, 5, 5, 5));
			{
				{
					JXPanel panPage = new JXPanel(new BorderLayout(5, 5));
					panMain.add(panPage, BorderLayout.PAGE_START);
					panPage.setPreferredSize(new Dimension(0, 60));
					{
						{
							JXPanel panDate = new JXPanel(new BorderLayout(5, 5));
							panPage.add(panDate, BorderLayout.LINE_START);
							panDate.setPreferredSize(new Dimension(200, 0));
							panDate.setBorder(JTBorderFactory.createTitleBorder("Actual Date", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
							{
								JXPanel panDate1 = new JXPanel(new BorderLayout(5, 5));
								panDate.add(panDate1, BorderLayout.CENTER);
								{
									dteFrom = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_'); 
									panDate1.add(dteFrom, BorderLayout.CENTER);
									dteFrom.getCalendarButton().setVisible(true);
									dteFrom.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
								}
							}
						}
						{
							JXPanel panCBO = new JXPanel(new BorderLayout(5, 5)); 
							panPage.add(panCBO, BorderLayout.CENTER); 
							panCBO.setBorder(JTBorderFactory.createTitleBorder("Status", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
							{
								pgSelect dbExec = new pgSelect(); 
								/*
								dbExec.select("select a.\"order\"::int, a.\"order\" || ' - ' || a.status_desc\n" + 
										"from (select (case when byrstatus_id = '114' then '1' when byrstatus_id = '69' then '2' when byrstatus_id = '70' then '3' when byrstatus_id = '91' then '4'\n" + 
										"when byrstatus_id = '92' then '5' when byrstatus_id = '86' then '6' when byrstatus_id = '97' then '7' when byrstatus_id = '87' then '8'\n" + 
										"when byrstatus_id = '73' then '9' when byrstatus_id = '95' then '10' when byrstatus_id = '89' then '11' when byrstatus_id = '78' then '12' end)::varchar as \"order\", status_desc\n" + 
										"from mf_buyer_status\n" + 
										"where byrstatus_id in ('114', '69', '70', '91', '92', '86', '97', '87', '73', '95', '89', '78')) a\n" + 
										"order by a.\"order\"::int");
								 */

								dbExec.select("select a.\"order\"::int, a.\"order\" || ' - ' || UPPER(a.status_desc) \n" + 
										"from (select (case when byrstatus_id = '114' then '1' when byrstatus_id = '69' then '2' when byrstatus_id = '91' \n" + 
										"then '3' when byrstatus_id = '132' then '4' when byrstatus_id = '97' then '5' when byrstatus_id = '87' \n" + 
										"then '6' when byrstatus_id = '137' then '7' when byrstatus_id = '73' then '8' when byrstatus_id = '95' \n" + 
										"then '9' when byrstatus_id = '89' then '10' when byrstatus_id = '78' then '11' when byrstatus_id = '138' \n" + 
										"then '12' when byrstatus_id = '79' then '13' when byrstatus_id = '139' then '14' end)::varchar as \"order\", status_desc from mf_buyer_status \n" + 
										"where byrstatus_id in ('114', '69', '91', '132', '97', '87', '137', '73', '95', '89', '78', '138', '79', '139') and server_id is null )  a order by a.\"order\"::int");

								System.out.println(""); 
								System.out.println("select a.\"order\"::int, a.\"order\" || ' - ' || UPPER(a.status_desc) \n" + 
										"from (select (case when byrstatus_id = '114' then '1' when byrstatus_id = '69' then '2' when byrstatus_id = '91' \n" + 
										"then '3' when byrstatus_id = '132' then '4' when byrstatus_id = '97' then '5' when byrstatus_id = '87' \n" + 
										"then '6' when byrstatus_id = '137' then '7' when byrstatus_id = '73' then '8' when byrstatus_id = '95' \n" + 
										"then '9' when byrstatus_id = '89' then '10' when byrstatus_id = '78' then '11' when byrstatus_id = '138' \n" + 
										"then '12' when byrstatus_id = '79' then '13' when byrstatus_id = '139' then '14' end)::varchar as \"order\", status_desc from mf_buyer_status \n" + 
										"where byrstatus_id in ('114', '69', '91', '132', '97', '87', '137', '73', '95', '89', '78', '138', '79', '139') and server_id is null)  a order by a.\"order\"::int");

								cboStatus = new JComboBox(); 
								panCBO.add(cboStatus, BorderLayout.CENTER); 
								cboStatus.addItemListener(new ItemListener() {
									public void itemStateChanged(ItemEvent e) {
										Integer index = cboStatus.getSelectedIndex();
										intIndex = index; 

										try {
											if (index==0) {
												SQL_BATCH("114"); 
												txtBat.setLookupSQL(SQL_BATCH("114"));
												txtRemarks.setEditable(false);
												dteFrom.setEnabled(true);

											} else if (index==1) {
												SQL_BATCH("69");
												txtBat.setLookupSQL(SQL_BATCH("69"));
												txtRemarks.setEditable(true);
												dteFrom.setEnabled(true);

											} else if (index==2) {
												SQL_BATCH("91");
												txtBat.setLookupSQL(SQL_BATCH("91"));
												txtRemarks.setEditable(false);
												dteFrom.setEnabled(true);

											} else if (index==3) {
												SQL_BATCH("132");
												txtBat.setLookupSQL(SQL_BATCH("132"));
												txtRemarks.setEditable(false);
												dteFrom.setEnabled(true);

											} else if (index==4) {
												SQL_BATCH("97");
												txtBat.setLookupSQL(SQL_BATCH("97"));
												txtRemarks.setEditable(false);
												dteFrom.setEnabled(true);

											} else if (index==5) {
												SQL_BATCH("87");
												txtBat.setLookupSQL(SQL_BATCH("87"));
												txtRemarks.setEditable(false);
												dteFrom.setEnabled(true);

											} else if (index==6) {
												SQL_BATCH("137");
												txtBat.setLookupSQL(SQL_BATCH("137"));
												txtRemarks.setEditable(false);
												dteFrom.setEnabled(true);

											} else if (index==7) {
												SQL_BATCH("73");
												txtBat.setLookupSQL(SQL_BATCH("73"));
												txtRemarks.setEditable(false);
												dteFrom.setEnabled(true);

											} else if (index==8) {
												SQL_BATCH("95");
												txtBat.setLookupSQL(SQL_BATCH("95"));
												txtRemarks.setEditable(false);
												dteFrom.setEnabled(true);

											} else if (index==9) {
												SQL_BATCH("89");
												txtBat.setLookupSQL(SQL_BATCH("89"));
												txtRemarks.setEditable(false);
												dteFrom.setEnabled(true);
											} else if (index==10) {
												SQL_BATCH("78");
												txtBat.setLookupSQL(SQL_BATCH("78"));
												txtRemarks.setEditable(false);
												dteFrom.setEnabled(true);

											} else if (index==11) {
												SQL_BATCH("138");
												txtBat.setLookupSQL(SQL_BATCH("138"));
												txtRemarks.setEditable(false);	
												dteFrom.setEnabled(true);

											} else if (index==12) {
												SQL_BATCH("79");
												txtBat.setLookupSQL(SQL_BATCH("79"));
												txtRemarks.setEditable(false);	
												dteFrom.setEnabled(true);

											} else if (index==13) {
												SQL_BATCH("139");
												txtBat.setLookupSQL(SQL_BATCH("139"));
												txtRemarks.setEditable(false);		
												dteFrom.setEnabled(true);

											}

											tblREM.showColumns("Tag", "Name", "Unit", "Loan Amount", "Loan Released", "REM Notification", "MA Count",
													"Circular No.", "entity_id", "proj_id", "pbl_id", "seq_no", "HLID", "MA Paid", "RetFee1", "RetFee2-1", 
													"RetFee2-2", "CAR", "EPEB Date", "DOAS", "Selling Price", "Lot Area", "Model", "ECR", "EPEB", "First Name",  
													"Middle Name", "Last Name", "TCT Pages");
											if (index==9) {
												tblREM.hideColumns("REM Notification", "MA Count", "Circular No.", "entity_id", "proj_id", "pbl_id",	
														"seq_no", "HLID", "MA Paid", "RetFee1", "RetFee2-1", "RetFee2-2", "CAR", "EPEB Date",
														"DOAS",	"Selling Price", "Lot Area", "Model", "ECR", "EPEB", "First Name", "Middle Name",  
														"Last Name");												
											} else {
												tblREM.hideColumns("entity_id", "proj_id", "pbl_id", "seq_no", "DOAS", "Selling Price",  
														"Lot Area",	"Model", "ECR", "EPEB", "First Name", "Middle Name", "Last Name");										
											}
											tblREM.getTableHeader().setEnabled(true); //enabled M'mavic req. 05-04-23

											hdmfMainMod.CtrlLock_2(1);
											txtBat.setValue("");
											txtRemarks.setText("");

											FncTables.clearTable(modelremConversion);
											DefaultListModel listModel = new DefaultListModel();
											rowREM.setModel(listModel); 
										} catch (NullPointerException ex) {
											System.out.println("Object not instantiated yet.");
										}
									}
								});

								if (dbExec.getRowCount()>0) {
									for (int x = 0; x < dbExec.getRowCount(); x++) {
										cboStatus.addItem((String) dbExec.getResult()[x][1].toString());										
									}
								}
							}
						}
						{
							JXPanel panBatch = new JXPanel(new BorderLayout(5, 5));
							panPage.add(panBatch, BorderLayout.LINE_END);
							panBatch.setPreferredSize(new Dimension(200, 0));
							panBatch.setBorder(JTBorderFactory.createTitleBorder("Batch No.", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
							{
								txtBat = new _JLookup("");
								panBatch.add(txtBat, BorderLayout.CENTER);
								txtBat.setReturnColumn(0);
								txtBat.setLookupSQL(SQL_BATCH("114"));
								txtBat.setHorizontalAlignment(JTextField.CENTER);
								txtBat.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup) event.getSource()).getDataSet();
										if (data!=null) {
											System.out.println("");
											System.out.println("data[0].toString(): " + data[0].toString());

											txtBat.setValue(data[0].toString());
											txtRemarks.setEditable(false);
											hdmfMainMod.CtrlLock_2(4);
											hdmfMainMod.intREM=1;
											DisplayREM();
										}
									}
								});
							}
						}
					}	
				}
				{
					JXPanel panCenter = new JXPanel(new BorderLayout(5, 5));
					panMain.add(panCenter, BorderLayout.CENTER);
					{
						splitReleasedLoanDetail = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
						panCenter.add(splitReleasedLoanDetail); 
						splitReleasedLoanDetail.setOneTouchExpandable(true);
						splitReleasedLoanDetail.setResizeWeight(.9d);
						{
							{
								CreateREMGrid();
								splitReleasedLoanDetail.add(panGrid);	
							}
							{
								JXPanel panSearchAndRemark = new JXPanel(new GridLayout(1, 2, 5, 5)); 
								splitReleasedLoanDetail.add(panSearchAndRemark);
								panSearchAndRemark.setPreferredSize(new Dimension(0, 30));
								{
									{
										JXPanel panSearch = new JXPanel(new BorderLayout(5, 5)); 
										panSearchAndRemark.add(panSearch);
										panSearch.setBorder(JTBorderFactory.createTitleBorder("Search Name", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
										{
											{
												txtName = new JTextArea(""); 
												txtName.setEditable(true); 
												txtName.setLineWrap(true);
												txtName.getDocument().addDocumentListener(new DocumentListener() {
													public void removeUpdate(DocumentEvent e) {
														focusonthis(); 
													}

													public void insertUpdate(DocumentEvent e) {
														focusonthis();  
													}

													public void changedUpdate(DocumentEvent e) {
														focusonthis(); 
													}

													void focusonthis() {
														if (txtName.getText().equals("")) {
															tblREM.changeSelection(0, 0, false, false);
														} else {
															for (int x=0; x<tblREM.getRowCount(); x++) {
																if (tblREM.getValueAt(x, 1).toString().toLowerCase().contains(txtName.getText()) ||
																		tblREM.getValueAt(x, 1).toString().substring(0, txtName.getText().length()).equals(txtName.getText())) {
																	tblREM.changeSelection(x, 1, false, false);
																	x=tblREM.getRowCount(); 
																}
															}
														}
													}
												});
											}
											{
												scrName = new JScrollPane(txtName);
												panSearch.add(scrName, BorderLayout.CENTER); 
											}
										}

									}
									{
										JXPanel panRemarks = new JXPanel(new BorderLayout(5, 5)); 
										panSearchAndRemark.add(panRemarks);
										panRemarks.setBorder(JTBorderFactory.createTitleBorder("Remarks", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
										{
											{
												txtRemarks = new JTextArea(""); 
												txtRemarks.setEditable(false); 
												txtRemarks.setLineWrap(true);
											}
											{
												scrRemarks = new JScrollPane(txtRemarks);
												panRemarks.add(scrRemarks, BorderLayout.CENTER); 
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}

	private void CreateREMGrid() {
		panGrid = new JXPanel(new GridLayout(1, 1, 0, 0));
		panGrid.setOpaque(isOpaque());
		{
			scrollREM = new JScrollPane();
			panGrid.add(scrollREM, BorderLayout.CENTER);
			scrollREM.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			{
				modelremConversion = new model_hdmf_remConversion();
				modelremConversion.setEditable(false);

				tblREM = new _JTableMain(modelremConversion);

				rowREM = tblREM.getRowHeader();
				scrollREM.setViewportView(tblREM);

				tblREM.getColumnModel().getColumn(1).setPreferredWidth(255);	//Name
				tblREM.getColumnModel().getColumn(2).setPreferredWidth(119);	//Unit
				tblREM.getColumnModel().getColumn(3).setPreferredWidth(110);	//Loan Amount
				tblREM.getColumnModel().getColumn(4).setPreferredWidth(110);	//Loan Released
				tblREM.getColumnModel().getColumn(5).setPreferredWidth(110);	//REM Notification
				tblREM.getColumnModel().getColumn(6).setPreferredWidth(80);		//MA Count
				tblREM.getColumnModel().getColumn(7).setPreferredWidth(125);	//Circular No.
				tblREM.getColumnModel().getColumn(12).setPreferredWidth(100);	//HLID
				tblREM.getColumnModel().getColumn(13).setPreferredWidth(100);	//Pay Status

				tblREM.getColumnModel().getColumn(14).setPreferredWidth(110);
				tblREM.getColumnModel().getColumn(15).setPreferredWidth(110);
				tblREM.getColumnModel().getColumn(16).setPreferredWidth(110);
				tblREM.getColumnModel().getColumn(17).setPreferredWidth(110);
				tblREM.getColumnModel().getColumn(19).setPreferredWidth(110);

				tblREM.getColumnModel().getColumn(28).setPreferredWidth(80);

				tblREM.getColumnModel().getColumn(4).setCellRenderer(new DateRenderer(sdf));
				tblREM.getColumnModel().getColumn(5).setCellRenderer(new DateRenderer(sdf));
				tblREM.getColumnModel().getColumn(17).setCellRenderer(new DateRenderer(sdf));
				tblREM.getColumnModel().getColumn(18).setCellRenderer(new DateRenderer(sdf));

				tblREM.hideColumns("entity_id", "proj_id", "pbl_id", "seq_no", "DOAS", "Selling Price",  
						"Lot Area",	"Model", "ECR", "EPEB", "First Name", "Middle Name", "Last Name");
				tblREM.getTableHeader().setEnabled(true);//enabled M'mavic req. 05-04-23

				rowREM = tblREM.getRowHeader();
				rowREM.setModel(new DefaultListModel());
				scrollREM.setRowHeaderView(rowREM);
				scrollREM.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
			}
		}
	}

	private void DisplayREM() {
		String SQL = ""; 
		Integer index = cboStatus.getSelectedIndex();

		if (index==0) {
//			SQL = "select * from view_hdmf_rem_first_nfrc('"+txtBat.getText()+"');";
			SQL = "select * from view_hdmf_rem_first_nfrc_v2('"+txtBat.getText()+"','"+PagibigStatusMonitoring_v2.company_id+"','"+PagibigStatusMonitoring_v2.project_id+"','"+PagibigStatusMonitoring_v2.phase_id+"');";
		} else if (index==1) {
//			SQL = "select * from view_hdmf_rem_second_prc_v2('"+txtBat.getText()+"');";
			SQL = "select * from view_hdmf_rem_second_prc_v2('"+txtBat.getText()+"','"+PagibigStatusMonitoring_v2.company_id+"','"+PagibigStatusMonitoring_v2.project_id+"','"+PagibigStatusMonitoring_v2.phase_id+"');";
			txtRemarks.setText(FncGlobal.GetString("select remarks from rf_hdmf_status_other_values where batch_no = '"+txtBat.getText()+"' and byrstatus_id = '69' ")); //Edited by Erick 2021-07-07 -- Added  byrstatus_id as filter to display remarks
		} else if (index==2) {
//			SQL = "select * from view_hdmf_rem_third_fpdoas('"+txtBat.getText()+"');";
			SQL = "select * from view_hdmf_rem_third_fpdoas_v2('"+txtBat.getText()+"','"+PagibigStatusMonitoring_v2.company_id+"','"+PagibigStatusMonitoring_v2.project_id+"','"+PagibigStatusMonitoring_v2.phase_id+"');";
		} else if (index==3) {
//			SQL = "select * from view_hdmf_rem_fourth_carapp('"+txtBat.getText()+"');";
			SQL = "select * from view_hdmf_rem_fourth_carapp_v2('"+txtBat.getText()+"','"+PagibigStatusMonitoring_v2.company_id+"','"+PagibigStatusMonitoring_v2.project_id+"','"+PagibigStatusMonitoring_v2.phase_id+"');";
		} else if (index==4) {
//			SQL = "select * from view_hdmf_rem_fifth_bir_forwarded('"+txtBat.getText()+"');";
			SQL = "select * from view_hdmf_rem_fifth_bir_forwarded_v2('"+txtBat.getText()+"','"+PagibigStatusMonitoring_v2.company_id+"','"+PagibigStatusMonitoring_v2.project_id+"','"+PagibigStatusMonitoring_v2.phase_id+"');";
		} else if (index==5) {
//			SQL = "select * from view_hdmf_rem_sixth_car_forwarded('"+txtBat.getText()+"');";
			SQL = "select * from view_hdmf_rem_sixth_car_forwarded_v2('"+txtBat.getText()+"','"+PagibigStatusMonitoring_v2.company_id+"','"+PagibigStatusMonitoring_v2.project_id+"','"+PagibigStatusMonitoring_v2.phase_id+"');";
		} else if (index==6) {
//			SQL = "select * from view_hdmf_rem_seventh_car_forwarded('"+txtBat.getText()+"');";
			SQL = "select * from view_hdmf_rem_seventh_car_forwarded_v2('"+txtBat.getText()+"','"+PagibigStatusMonitoring_v2.company_id+"','"+PagibigStatusMonitoring_v2.project_id+"','"+PagibigStatusMonitoring_v2.phase_id+"');";
		} else if (index==7) {
//			SQL = "select * from view_hdmf_rem_eigth_car_forwarded('"+txtBat.getText()+"');";
			SQL = "select * from view_hdmf_rem_eigth_car_forwarded_v2('"+txtBat.getText()+"','"+PagibigStatusMonitoring_v2.company_id+"','"+PagibigStatusMonitoring_v2.project_id+"','"+PagibigStatusMonitoring_v2.phase_id+"');";
		} else if (index==8) {
//			SQL = "select * from view_hdmf_rem_ninth_rd_payment_forwarded('"+txtBat.getText()+"');";
			SQL = "select * from view_hdmf_rem_ninth_rd_payment_forwarded_v2('"+txtBat.getText()+"','"+PagibigStatusMonitoring_v2.company_id+"','"+PagibigStatusMonitoring_v2.project_id+"','"+PagibigStatusMonitoring_v2.phase_id+"');";
		} else if (index==9) {
//			SQL = "select * from view_hdmf_rem_tenth_tct_forwarded('"+txtBat.getText()+"');";
			SQL = "select * from view_hdmf_rem_tenth_tct_forwarded_v2('"+txtBat.getText()+"','"+PagibigStatusMonitoring_v2.company_id+"','"+PagibigStatusMonitoring_v2.project_id+"','"+PagibigStatusMonitoring_v2.phase_id+"');";
		} else if (index==10) {
//			SQL = "select * from view_hdmf_rem_eleventh_officially_rem('"+txtBat.getText()+"');";
			SQL = "select * from view_hdmf_rem_eleventh_officially_rem_v2('"+txtBat.getText()+"','"+PagibigStatusMonitoring_v2.company_id+"','"+PagibigStatusMonitoring_v2.project_id+"','"+PagibigStatusMonitoring_v2.phase_id+"');";
		} else if (index==11) {
//			SQL = "select * from view_hdmf_rem_twelfth_full_settled('"+txtBat.getText()+"');";
			SQL = "select * from view_hdmf_rem_twelfth_full_settled_v2('"+txtBat.getText()+"','"+PagibigStatusMonitoring_v2.company_id+"','"+PagibigStatusMonitoring_v2.project_id+"','"+PagibigStatusMonitoring_v2.phase_id+"');";
		} else if (index==12) {
//			SQL = "select * from view_hdmf_rem_thirteenth_full_settled('"+txtBat.getText()+"');";
			SQL = "select * from view_hdmf_rem_thirteenth_full_settled_v2('"+txtBat.getText()+"','"+PagibigStatusMonitoring_v2.company_id+"','"+PagibigStatusMonitoring_v2.project_id+"','"+PagibigStatusMonitoring_v2.phase_id+"');";
		} else if (index==13) {
//			SQL = "select * from view_hdmf_rem_fourteenth_td_extension('"+txtBat.getText()+"', false);";
			SQL = "select * from view_hdmf_rem_fourteenth_td_extension_v2('"+txtBat.getText()+"','"+PagibigStatusMonitoring_v2.company_id+"','"+PagibigStatusMonitoring_v2.project_id+"','"+PagibigStatusMonitoring_v2.phase_id+"', false);";
		}

		FncTables.clearTable(modelremConversion);
		DefaultListModel listModel = new DefaultListModel();
		rowREM.setModel(listModel); 

		System.out.println("");
		System.out.println("txtBat:"+txtBat.getText());
		System.out.println(SQL);
		System.out.println(index);
		pgSelect db = new pgSelect();
		db.select(SQL);
		if (db.isNotNull()){
			for (int x = 0; x < db.getRowCount(); x++) {
				modelremConversion.addRow(db.getResult()[x]);
				listModel.addElement(modelremConversion.getRowCount());
			}
		}
	}

	public void Generate() {
		new Thread(new Runnable() {
			public void run() {
				strBat = "";

				FncGlobal.startProgress("Generating List of Accounts");
				hdmfMainMod.CtrlLock_0(3);

				txtBat.setText("");
				DisplayREM();
				txtBat.setEnabled(true);
				txtRemarks.setEditable(true);

				if (cboStatus.getSelectedIndex()==13) {
					hdmfMainMod.CtrlLock_2(5);
					hdmfMainMod.intREM = 5;
				} else {
					hdmfMainMod.CtrlLock_2(2);
					hdmfMainMod.intREM = 2;	
				}

				FncGlobal.stopProgress();
			}
		}).start();
	}

	public void Post() {
		String strStatus = "";
		Integer index = cboStatus.getSelectedIndex();
		SimpleDateFormat sdfTo = new SimpleDateFormat("MM-dd-yyyy");
		String strDate = (String) sdfTo.format(dteFrom.getDate());
				
		if (index==0) {
			strStatus = "114";
		} else if (index==1) {
			strStatus = "69";	
		} else if (index==2) {
			strStatus = "91";	
		} else if (index==3) {
			strStatus = "132";	
		} else if (index==4) {
			strStatus = "97";	
		} else if (index==5) {
			strStatus = "87";	
		} else if (index==6) {
			strStatus = "137";	
		} else if (index==7) {
			strStatus = "73";	
		} else if (index==8) {
			strStatus = "95";
		} else if (index==9) {
			strStatus = "89";
		} else if (index==10) {
			strStatus = "78";
		} else if (index==11) {
			strStatus = "138";
		} else if (index==12) {
			strStatus = "79";
		} else if (index==13) {
			strStatus = "139";
		}
		
		
		
//		if(strStatus.equals("89")) {
//			String ddate = FncGlobal.GetString("Select now()::varchar");
//			strDate = (String) sdfTo.format(ddate);
//		}
		strBat = GETBATCH(strStatus);
		
		if (JOptionPane.showConfirmDialog(null, "Are you sure you want to proceed? \n" + 
				"If so, " + strDate + " will be set as actual date. Continue?", 
				"Confirmation", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION) {
			
			Boolean blnChk = false;
			txtBat.setValue(strBat);
			
			if (!txtRemarks.getText().equals("")) {
				blnChk = true;
			} else {
				if (JOptionPane.showConfirmDialog(null, "You did not put any remarks. Proceed anyway?", "Warning", 
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_OPTION) {
					blnChk = true; 
				} else {
					blnChk = false; 
				}	
			}
			
			if (blnChk) {
				for (int x = 0; x < modelremConversion.getRowCount(); x++) {
					if ((Boolean) modelremConversion.getValueAt(x, 0)) {
						String strEntID = (String) modelremConversion.getValueAt(x, 8).toString();
						String strProID = (String) modelremConversion.getValueAt(x, 9).toString();
						String strUnitID = (String) modelremConversion.getValueAt(x, 10).toString();
						String strSeq = (String) modelremConversion.getValueAt(x, 11).toString();
						String strUser = UserInfo.EmployeeCode;
						
						String strEPEBDate = ""; 
						
						try {
							strEPEBDate = (String) modelremConversion.getValueAt(x, 18).toString();
						} catch (NullPointerException ex) {
							strEPEBDate = "";
						}
						
						pgUpdate dbBor = new pgUpdate();
						String strSQL = ""; 
						
						System.out.println("");
						System.out.println("index: "+index);
						
						
						Integer pages = 0; 
						try {
							pages = (Integer) modelremConversion.getValueAt(x, 28);
						} catch (NullPointerException ex) {
							pages = 0;
						}
						
						pages = (pages==null || pages.equals(""))?0:pages;

						System.out.println("txtRemarks.getText(): "+txtRemarks.getText());
						System.out.println("strEPEBDate: "+strEPEBDate);
						System.out.println("pages: "+pages);
						
						if (txtRemarks.getText().equals("") && strEPEBDate.equals("") && !(pages > 0)) {
							// modified date : 2021-11-24 modify by jervin -- reference dcrf 1869
							if(strStatus.equals("89")) {
								strSQL = "INSERT INTO rf_buyer_status(entity_id, proj_id, pbl_id, seq_no, byrstatus_id, tran_date, actual_date, status_id, trans_no, created_by) \n" +
										"VALUES ('"+strEntID+"', '"+strProID+"', '"+strUnitID+"', "+strSeq+", '"+strStatus+"', now(),now(), 'A', '"+strBat+"', '"+strUser+"');";
								dbBor.executeUpdate(strSQL, false);
								dbBor.commit();
							}
							else {
								strSQL = "INSERT INTO rf_buyer_status(entity_id, proj_id, pbl_id, seq_no, byrstatus_id, tran_date, actual_date, status_id, trans_no, created_by) \n" +
										"VALUES ('"+strEntID+"', '"+strProID+"', '"+strUnitID+"', "+strSeq+", '"+strStatus+"', now(), '"+strDate+"', 'A', '"+strBat+"', '"+strUser+"');";
								dbBor.executeUpdate(strSQL, false);
								dbBor.commit();
							}
						
							
							dbBor = new pgUpdate();
							strSQL = "INSERT INTO rf_hdmf_status_other_values(entity_id, proj_id, pbl_id, seq_no, remarks, byrstatus_id, batch_no) \n" +
									"VALUES ('"+strEntID+"', '"+strProID+"', '"+strUnitID+"', "+strSeq+", '"+txtRemarks.getText()+"', '"+strStatus+"', '"+strBat+"');";
							dbBor.executeUpdate(strSQL, true);
							dbBor.commit();
							System.out.println("d2 pumasok1");
							
							System.out.println("strEntID:" + strEntID);
							System.out.println("strProID:" + strProID);
							System.out.println("strUnitID:" + strUnitID);
							System.out.println("strSeq:" + strSeq);
							System.out.println("txtRemarks.getText():" + txtRemarks.getText());
							System.out.println("strStatus:" + strStatus);
							System.out.println("strBat:" + strBat);

						} else if (txtRemarks.getText().equals("") && !strEPEBDate.equals("")) {
							// modified date : 2021-11-24 modify by jervin -- reference dcrf 1869
							if(strStatus.equals("89")) {
								strSQL = "INSERT INTO rf_buyer_status(entity_id, proj_id, pbl_id, seq_no, byrstatus_id, tran_date, actual_date, status_id, trans_no, created_by) \n" +
										"VALUES ('"+strEntID+"', '"+strProID+"', '"+strUnitID+"', "+strSeq+", '"+strStatus+"', now(),now(), 'A', '"+strBat+"', '"+strUser+"');";
								dbBor.executeUpdate(strSQL, false);
								dbBor.commit();
							}
							else {
								strSQL = "INSERT INTO rf_buyer_status(entity_id, proj_id, pbl_id, seq_no, byrstatus_id, tran_date, actual_date, status_id, trans_no, created_by) \n" +
										"VALUES ('"+strEntID+"', '"+strProID+"', '"+strUnitID+"', "+strSeq+", '"+strStatus+"', now(), '"+strDate+"', 'A', '"+strBat+"', '"+strUser+"');";
								
								if(strStatus.equals("132")) {
									String rplf_no = "";
									String availer = SQL_AVAILER();
									String batch_no = generateBatchNo();
									String remarks = "TCP (rounded off nearest thousand x 1.5%)";
									String SQL = "SELECT sp_save_transfer_cost_woutrplf_batch_v2('"
											+ strEntID + "', '" + strProID + "', '" + strUnitID + "', '"
											+ 034 + "',now()::date," + "" + 16320.00 + ", "
											+ 16320.00 + ", '" + strUser + "', NULLIF('" + remarks
											+ "','null'), '" + strUser + "', NULLIF('" + batch_no
											+ "','null'), NULLIF('" + strUser + "','null')," + "" + strSeq
											+ ", '" + availer + "', '" + rplf_no + "')";

									pgSelect db = new pgSelect();
									FncSystem.out("SQL", SQL);
									db.select(SQL);
								}
								
								
								
								dbBor.executeUpdate(strSQL, false);
								dbBor.commit();
							}
						
							
							
							dbBor = new pgUpdate();
							strSQL = "INSERT INTO rf_hdmf_status_other_values(entity_id, proj_id, pbl_id, seq_no, remarks, byrstatus_id, batch_no, actual_date, status_id, tct_pages) \n" +
									"VALUES ('"+strEntID+"', '"+strProID+"', '"+strUnitID+"', "+strSeq+", '"+txtRemarks.getText()+"', '"+strStatus+"', '"+strBat+"', '"+strEPEBDate+"'::date, 'A', '"+pages+"');";
							dbBor.executeUpdate(strSQL, true);
							dbBor.commit();
							System.out.println("d2 pumasok2");
						} else {
							// modified date : 2021-11-24 modify by jervin -- reference dcrf 1869
							if(strStatus.equals("89")) {
								strSQL = "INSERT INTO rf_buyer_status(entity_id, proj_id, pbl_id, seq_no, byrstatus_id, tran_date, actual_date, status_id, trans_no, created_by) \n" +
										"VALUES ('"+strEntID+"', '"+strProID+"', '"+strUnitID+"', "+strSeq+", '"+strStatus+"', now(),now(), 'A', '"+strBat+"', '"+strUser+"');";
								dbBor.executeUpdate(strSQL, false);
								dbBor.commit();
							}
							else {
								strSQL = "INSERT INTO rf_buyer_status(entity_id, proj_id, pbl_id, seq_no, byrstatus_id, tran_date, actual_date, status_id, trans_no, created_by) \n" +
										"VALUES ('"+strEntID+"', '"+strProID+"', '"+strUnitID+"', "+strSeq+", '"+strStatus+"', now(), '"+strDate+"', 'A', '"+strBat+"', '"+strUser+"');";
								dbBor.executeUpdate(strSQL, false);
								dbBor.commit();
							}
							
							
							dbBor = new pgUpdate();
							strSQL = "INSERT INTO rf_hdmf_status_other_values(entity_id, proj_id, pbl_id, seq_no, remarks, byrstatus_id, batch_no, tct_pages,status_id) \n" +
									"VALUES ('"+strEntID+"', '"+strProID+"', '"+strUnitID+"', "+strSeq+", '"+txtRemarks.getText()+"', '"+strStatus+"', '"+strBat+"', '"+pages+"','A');";
							dbBor.executeUpdate(strSQL, true);
							dbBor.commit();
							System.out.println("d2 pumasok3");
							
						}
						
						String strDoc = "";
						
						if (index==9) {
							strDoc = FncGlobal.GetString("select doc_no \n" + 
									"from rf_tct_taxdec_monitoring_hd \n" + 
									"where pbl_id = '"+strUnitID+"' and doc_type = '65' \n" + 
									"and proj_id ='"+strProID+"' \n"+
									"limit 1"); 
							
							FncSystem.out("strDoc1: ", strDoc);

							dbBor = new pgUpdate(); 
							dbBor.executeUpdate("insert into rf_tct_taxdec_monitoring_dl (pbl_id, doc_type, doc_no, doc_status, status_date, done_by, status_id, created_by, date_created)\n" + 
									"values ('"+strUnitID+"', '65', '"+strDoc+"', '212', '"+strDate+"', '"+UserInfo.EmployeeCode+"', 'A', '"+UserInfo.EmployeeCode+"', now())", true);
							dbBor.commit();
						} else if (index==10) {
							strDoc = FncGlobal.GetString("select doc_no \n" +  //XXX ADD PROJ_ID TO GET PROPER DOC_NO
									"from rf_tct_taxdec_monitoring_hd \n" + 
									"where pbl_id = '"+strUnitID+"' and doc_type = '98' \n" +
									"and proj_id = '"+strProID+"' \n"+
									"limit 1"); 
							
							dbBor = new pgUpdate(); 
							dbBor.executeUpdate("insert into rf_tct_taxdec_monitoring_dl (pbl_id, doc_type, doc_no, doc_status, status_date, done_by, status_id, created_by, date_created)\n" + 
									"values ('"+strUnitID+"', '98', '"+strDoc+"', '212', '"+strDate+"', '"+UserInfo.EmployeeCode+"', 'A', '"+UserInfo.EmployeeCode+"', now())", true);
							dbBor.commit();
							
							strDoc = FncGlobal.GetString("select doc_no \n" + //XXX ADD PROJ_ID TO GET PROPER DOC_NO
									"from rf_tct_taxdec_monitoring_hd \n" + 
									"where pbl_id = '"+strUnitID+"' and doc_type = '67' \n" +
									"and proj_id = '"+strProID+"' \n"+
									"limit 1"); 
							
							dbBor = new pgUpdate(); 
							dbBor.executeUpdate("insert into rf_tct_taxdec_monitoring_dl (pbl_id, doc_type, doc_no, doc_status, status_date, done_by, status_id, created_by, date_created)\n" + 
									"values ('"+strUnitID+"', '67', '"+strDoc+"', '212', '"+strDate+"', '"+UserInfo.EmployeeCode+"', 'A', '"+UserInfo.EmployeeCode+"', now())", true);
							dbBor.commit();
						}
					}
				}	
			}
			
			if (!blnChk) {
				JOptionPane.showMessageDialog(null, "Tagging did not proceed.");
			} else {
				DisplayREM();
				
				/*if (index==13) {
					hdmfMainMod.CtrlLock_2(1);
					hdmfMainMod.intREM=1;
				} else*/ {
					hdmfMainMod.CtrlLock_2(4);
					hdmfMainMod.intREM=4;	
				}

				JOptionPane.showMessageDialog(null, "Accounts are successfully tagged.");
			}
		}
	}

	private static String SQL_BATCH(String strType) {
		String strSQL = "SELECT DISTINCT trans_no, tran_date::date as date_created FROM rf_buyer_status WHERE byrstatus_id = '"+strType+"' AND status_id = 'A' ORDER BY trans_no";
		return strSQL;
	}
	private static String GETBATCH(String strType) {
		pgSelect dbBatch = new pgSelect();
		String strBat = "";
		String strSQL = "";

		strSQL = "SELECT CASE WHEN TRIM(to_char(max(COALESCE(getinteger(trans_no), 0)) + 1, '000000')) \n" +
				"IS NULL THEN '000001' ELSE TRIM(to_char(max(COALESCE(getinteger(trans_no), 0)) + 1, '000000')) END \n" +
				"FROM rf_buyer_status \n" +
				"WHERE byrstatus_id = '"+strType+"' AND status_id = 'A'";

		dbBatch.select(strSQL);

		if (dbBatch.isNotNull()) {
			strBat = (String) dbBatch.getResult()[0][0];
		} else {
			strBat = "000001";
		}

		return strBat;
	}

	public void Preview() {
		String strDate = sdf.format(dteFrom.getDate()); 

		System.out.println("");
		System.out.println("index: " + intIndex);
		System.out.println("Combobox: " + cboStatus.getSelectedItem().toString());

		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("co_name", hdmfMainMod.txtCo.getText());
		mapParameters.put("dateParam", strDate);
		mapParameters.put("user_name", UserInfo.FullName);
		mapParameters.put("project", hdmfMainMod.txtPro.getText());
		mapParameters.put("batch_no", txtBat.getValue());
		mapParameters.put("co_id", PagibigStatusMonitoring_v2.txtCoID.getText());
		
		System.out.println("co_name: " + hdmfMainMod.txtCo.getText());
		System.out.println("dateParam: " + strDate);
		System.out.println("project: " + hdmfMainMod.txtPro.getText());
		System.out.println("batch_no: " + txtBat.getValue());
		System.out.println("co_id: " + PagibigStatusMonitoring_v2.txtCoID.getText());
		System.out.println("Combobox: " + txtBat.getValue());
		System.out.println("Combobox: " + txtBat.getText());
		

		if (intIndex==0) {
			FncReport.generateReport("/Reports/rpt_hdmf_forEarlyConversion.jasper", "For Early Conversion", "", mapParameters);
		} else if (intIndex==1) {
			FncReport.generateReport("/Reports/rpt_hdmf_rem_conversion.jasper", "PAGIBIG REM Conversion", "", mapParameters);
		} else if (intIndex==2) {
			FncReport.generateReport("/Reports/rpt_hdmf_forwardedDOASRPT.jasper", "Forwarded Photocopy of DOAS/RPT to HDMF", "", mapParameters);
		} else if (intIndex==3) {
			FncReport.generateReport("/Reports/rpt_hdmf_carapplication.jasper", "CAR Application", "", mapParameters);
		} else if (intIndex==4) {
			FncReport.generateReport("/Reports/rpt_hdmf_bir_payment.jasper", "BIR Payment forwawrded to HDMF", "", mapParameters);
			//			FncReport.generateReport("/Reports/rptRem_Dts.jasper", "BIR Payment forwawrded to HDMF DTS", "", mapParameters);
			FncReport.generateReport("/Reports/rptRem_Ewt.jasper", "BIR Payment forwawrded to HDMF EWT", "", mapParameters);
		} else if (intIndex==5) {
			FncReport.generateReport("/Reports/rpt_hdmf_car_forwarded.jasper", "List of Accounts with CAR", "", mapParameters);
		} else if (intIndex==7) {
			FncReport.generateReport("/Reports/rpt_hdmf_rem_tct_ret_dev.jasper", "List of Accounts with CAR", "", mapParameters);
		} else if (intIndex==8) {
			FncReport.generateReport("/Reports/rpt_hdmf_rem_rd_payment_forwarded.jasper", "OR Payment to RD forwarded to HDMF", "", mapParameters);
		} else if (intIndex==9) {
			mapParameters.put("count", modelremConversion.getRowCount());
			System.out.println("count:"+ modelremConversion.getRowCount());
			if (FncGlobal.GetBoolean("select exists(select * \n" + 
					"from rf_buyer_status a \n" + 
					"where a.byrstatus_id = '89' and a.status_id = 'A' and a.trans_no = '"+txtBat.getValue()+"' \n" + 
					"and exists(select * from rf_buyer_status x where x.entity_id = a.entity_id and x.proj_id = a.proj_id and x.pbl_id = a.pbl_id and x.seq_no::int = a.seq_no::int and x.status_id = 'A' and x.byrstatus_id = '78')); ")) {
				FncReport.generateReport("/Reports/rpt_hdmf_rem_tct_forwarded_to_hdmf.jasper", "TCT 4Warded to HDMF w/ Undertaking TD", "", mapParameters);
			} 

			if (FncGlobal.GetBoolean("select exists(select * \n" + 
					"from rf_buyer_status a \n" + 
					"where a.byrstatus_id = '89' and a.status_id = 'A' and a.trans_no = '"+txtBat.getValue()+"' \n" + 
					"and not exists(select * from rf_buyer_status x where x.entity_id = a.entity_id and x.proj_id = a.proj_id and x.pbl_id = a.pbl_id and x.seq_no::int = a.seq_no::int and x.status_id = 'A' and x.byrstatus_id = '78')); ")) {
				FncReport.generateReport("/Reports/rpt_hdmf_rem_tct_forwarded_to_hdmf_notoffrem.jasper", "TCT 4Warded to HDMF w/ Undertaking TD", "", mapParameters);
				FncReport.generateReport("/Reports/rpt_hdmf_rem_tct_forwarded_to_hdmf_undertaking.jasper", "Developer's Undertaking", "", mapParameters);
			}
		} else if (intIndex==10) {
			FncReport.generateReport("/Reports/rpt_hdmf_officially_rem.jasper", "Officially REM", "", mapParameters);
		} else if (intIndex==13) {
			Integer intCount = 0;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          

			pgUpdate dbExec = new pgUpdate(); 
			dbExec.executeUpdate("delete from tmp_hdmf_undertaking_extension", true);
			dbExec.commit();

			dbExec = new pgUpdate();
			for (int x=0; x<modelremConversion.getRowCount(); x++) {
				if ((Boolean) modelremConversion.getValueAt(x, 0)) {
					dbExec.executeUpdate("insert into tmp_hdmf_undertaking_extension values ('"+modelremConversion.getValueAt(x, 1)+"', '"+UserInfo.EmployeeCode+"')", true);
					intCount++; 
				}
			}
			dbExec.commit();

			mapParameters.put("count", intCount);
			mapParameters.put("tag", true);
			System.out.println("count:" + intCount);
			FncReport.generateReport("/Reports/rpt_hdmf_rem_td_extension_undertaking.jasper", "Developer's Undertaking", "", mapParameters);
		}
	}

	private static String generateBatchNo() {
		String strSQL = "SELECT to_char(COALESCE(MAX(NULLIF(trim(batch_no), '')::INT), 0) + 1, 'FM0000000000') FROM rf_transfer_cost WHERE NULLIF(TRIM(batch_no), 'null') IS not null";

		FncSystem.out("Batch No", strSQL);
		pgSelect db = new pgSelect();
		db.select(strSQL);

		if (db.isNotNull()) {
			return (String) db.getResult()[0][0];
		} else {
			return null;
		}
	}

	private static String SQL_AVAILER() {
		String strSQL = "SELECT trim(entity_id) as \"Entity ID\", trim(entity_name) as \"Name\", entity_kind as \"Kind\", vat_registered as \"VAT\"  \n"
				+ "FROM (select entity_id, entity_name, entity_kind, vat_registered from rf_entity where entity_id in \r\n"
				+ "(select entity_id from rf_entity_assigned_type " + " ) and server_id is null) a \n"
				+ "ORDER BY a.entity_name  ";

		pgSelect db = new pgSelect();
		db.select(strSQL);


		if (db.isNotNull()) {
			return (String) db.getResult()[0][0];
		} else {
			return null;
		}
	}


	private static Boolean checkByrStatus(String entity_id, String proj_id, String pbl_id, String seq_no ) {

		Boolean exist = false;
		
		String SQL = "select exists(select * \n" + 
				"					from rf_buyer_status a \n" + 
				"					where trim(a.byrstatus_id) = '132' \n" + 
				"                   and trim(a.entity_id) ='"+entity_id+"'\n" + 
				"                   and trim(a.proj_id)='"+proj_id+"'\n" + 
				"                   and trim(a.pbl_id) ='"+pbl_id+"'\n" + 
				"                   and a.seq_no ='"+seq_no+"'::INT\n" + 
				"					and a.status_id = 'A')";

		pgSelect db = new pgSelect();
		db.select(SQL);

		if (db.isNotNull()) {

			exist = (Boolean) db.getResult()[0][0];
			
		} 

		return exist;

	}





}
