package Buyers.CreditandCollections;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableColumn;

import org.jdesktop.swingx.JXFormattedTextField;
import org.jdesktop.swingx.JXPanel;

import Database.pgSelect;
import Database.pgUpdate;
import DateChooser.DateEvent;
import DateChooser.DateListener;
import DateChooser._JDateChooser;
import Dialogs.dlgDate;
import FormattedTextField._JXFormattedTextField;
import Functions.CheckBoxHeader;
import Functions.FncGlobal;
import Functions.FncReport;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import Lookup._JLookupTable;
import components._JButton;
import components._JInternalFrame;
import components._JTabbedPane;
import components._JTableMain;
import components._JXTextField;
import interfaces._GUI;
import tablemodel.model_CancelActive_PerBatch;
import tablemodel.model_Recommend_Cancellation;



@SuppressWarnings({ "rawtypes", "unchecked" })
public class Cancellation extends _JInternalFrame implements _GUI,LookupListener,MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static String title = "Cancellation Processing";

	public static Dimension frameSize = new Dimension(1000, 620);
	private JXPanel pnlMain;
	private JXPanel pnlNorth;
	public _JLookup lookupCompany;
	private _JXTextField txtCompany;
	private _FPromissoryNoteCommintment methods = new _FPromissoryNoteCommintment();
	private JXPanel pnlCenter;
	private _JTabbedPane tabPaneCenter;
	private JPanel pnlCancellation;
	private JLabel lblBuyerType;
	private JLabel lblProject;
	private DefaultComboBoxModel cmbBuyerTypeModel;
	private JComboBox cmbBuyerType;
	private _JLookup lookupProjID;
	private _JXTextField txtProjectName;
	private JLabel lblBatchNo;
	private _JLookup lookupBatchNo;
	private _JTabbedPane tabPaneAccounts;
	private JPanel pnlPerBatch;
	private JPanel pnlPerAccount;
	private JXPanel pnlSouth;
	private JLabel lblApprovedBy;
	private _JLookup lookupApprovedBy;
	private _JXTextField txtApprovedBy;
	private _JButton btnNew;
	private ButtonGroup grpButton = new ButtonGroup();
	private _JButton btnEdit;
	private _JButton btnSave;
	private _JButton btnPost;
	private _JButton btnClear;
	private _JDateChooser dteDueDate;
	private _JButton btnPreview;
	private JPanel pnlBatch;
	private JScrollPane scrollPerBatch;
	private model_CancelActive_PerBatch modelPerBatchModel;
	private _JTableMain tblPerBatch;
	private JList rowHeadePerBatch;
	private JPanel pnlPerAccountCancel;
	private JLabel lblClient;
	private JLabel lblPBL;
	private JLabel lblStage;
	private JLabel lblMonthPD;
	private JLabel lblDefaultDate;
	private JLabel lblCanclType;
	private JLabel lblAmountDue;
	private JLabel lblRemarks;
	private JLabel lblReason;
	private _JLookup lookupClientName;
	private _JXTextField txtClientName;
	private JTextField txtPBLID;
	private JTextField txtPBLDesc;
	private JTextField txtStageID;
	private JTextField txtStageDesc;
	private JTextField txtMonthPD;
	private JTextField txtDefaultDate;
	private DefaultComboBoxModel cmbCancelTypeModel_Per;
	private JComboBox cmbCancelType_Per;
	private _JXFormattedTextField txtAmountDue;
	private _JLookup lookupReason;
	private JTextField txtReason_;
	private JScrollPane scrollRemarks;
	private JTextArea txtareaRemarks;
	private JLabel lblDueDate;
	public String co_id;
	public String company;
	public String company_logo;
	private String approved_id;
	private String approved_name;

	private _FCancellationProcessing functions= new _FCancellationProcessing();
	private String proj_id;
	private String proj_name;
	private DefaultComboBoxModel cmbCancelTypeModel;
	private JComboBox cmbCancelType;
	private String cancel_type;
	//private String reason_id;
	private String batch_no;
	private JPanel pnlBatch_Recom;
	private JScrollPane scrollPerBatch_Recom;
	private model_Recommend_Cancellation modelRecommendCancel;
	private _JTableMain tblRecomCancel;
	private JList rowHeaderRecom;
	private JPanel pnlCancellationStatus;
	private pnlTagAccountsStatus pnlTagStatus;
	private String noticeBatchno;
	private Integer seq_no;
	private String reason_id;
	private String reason_name;
	private String entity_id;
	private String pbl_id;
	private String client_name;
	private String pbl_desc;
	private String payment_stage;
	private String stage_id;
	private Integer months_pd;
	private Date default_date;
	private String default_date_format;
	private BigDecimal amount_due;
	private Date dateFrom;
	private Date dateTo;
	private Boolean printall;
	private JLabel lblCancelType;
	private JComboBox cmbReasonType;
	private DefaultComboBoxModel cmbReasonTypeModel;
	private String houseModel;
	private Timestamp trDate;
	private Timestamp orDate;
	private Timestamp defaultDate;
	private String stage;
	private String saleDiv;
	private String class_type;
	private String saleAgent;
	private String withNTC;
	private String phase;
	private Timestamp CtsNotarize;
	private BigDecimal grossTCP;
	private _JButton btnUnRecommend;
	private JLabel lblDivision;
	private DefaultComboBoxModel cmbSalesDivModel;
	private JComboBox cmbSalesDiv;
	private JLabel lbl3;
	private JLabel lbl4;
	private JLabel lbl5;
	private JCheckBox chkHeader;
	private JCheckBox chkHeaderRecom;

	public Cancellation() {
		super(title, true, true, true, true);
		initGUI();
		getDefaultCompany();
		formLoad();
	}

	public Cancellation(String title) {
		super(title, true, true, true, true);
		initGUI();
		getDefaultCompany();
		formLoad();
	}

	public Cancellation(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
		formLoad();
	}

	@Override
	public void initGUI() {

		try {
			this.setTitle(title);
			this.setSize(frameSize);
			this.setPreferredSize(new java.awt.Dimension(frameSize));
			getContentPane().setLayout(new BorderLayout());

			{
				pnlMain = new JXPanel();
				this.add(pnlMain,BorderLayout.CENTER);
				pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
				pnlMain.setLayout(new BorderLayout(3,3));
				{
					pnlNorth = new JXPanel(new BorderLayout());
					pnlMain.add(pnlNorth,BorderLayout.NORTH);
					pnlNorth.setPreferredSize(new Dimension(pnlMain.getWidth(), 55));
					pnlNorth.setBorder(components.JTBorderFactory.createTitleBorder("Company"));
					{
						{
							JPanel pnlCompany = new JPanel(new BorderLayout(3,3));
							pnlNorth.add(pnlCompany,BorderLayout.CENTER);
							pnlCompany.setPreferredSize(new Dimension(74, 20));
							{
								{

									lookupCompany = new _JLookup("ID", "Company", 0) ; /// XXX lookupCompany 
									pnlCompany.add(lookupCompany,BorderLayout.WEST);
									lookupCompany.addLookupListener(this);
									lookupCompany.setReturnColumn(0);
									lookupCompany.setLookupSQL(methods.getCompany());
									lookupCompany.setPreferredSize(new Dimension(100, 20));
									lookupCompany.addLookupListener(new LookupListener() {
										
										@Override
										public void lookupPerformed(LookupEvent event) {
											Object [] data = ((_JLookup)event.getSource()).getDataSet();
											
											if(data != null) {
												String co_id = (String) data[0];
												String co_name = (String) data[1];
												String company_logo = (String) data[2];
												
												txtCompany.setText(co_name);
								
												
												lookupProjID.setLookupSQL(functions.lookupProjects(co_id));
												
											}
										}
									});
								}
								{
									txtCompany = new _JXTextField();
									pnlCompany.add(txtCompany,BorderLayout.CENTER);
									txtCompany.setEditable(false);
									txtCompany.setPrompt("Company Name");
									txtCompany.setPreferredSize(new Dimension(100, 20));
								} 
							}
						}
					}
				}
				{
					pnlCenter = new JXPanel(new BorderLayout());
					pnlMain.add(pnlCenter,BorderLayout.CENTER);

					{
						tabPaneCenter = new _JTabbedPane();
						pnlCenter.add(tabPaneCenter,BorderLayout.CENTER);
						{
							pnlCancellation = new  JPanel(new BorderLayout(5,5));
							tabPaneCenter.addTab(" Cancellation Active Accounts ", null, pnlCancellation, null);
							{
								JPanel pnlProcess = new JPanel(new BorderLayout(3, 3));
								pnlCancellation.add(pnlProcess, BorderLayout.NORTH);
								pnlProcess.setPreferredSize(new Dimension(pnlMain.getWidth(), 120));

								{
									JPanel pnlNorth = new  JPanel(new BorderLayout(5, 5));
									pnlProcess.add(pnlNorth,BorderLayout.NORTH);
								}
								{
									JPanel pnlWest = new JPanel(new BorderLayout(3, 3));
									pnlProcess.add(pnlWest, BorderLayout.WEST);
									pnlWest.setPreferredSize(new Dimension(450, 55));
									{
										JPanel pnLable = new JPanel(new GridLayout(5, 1, 3, 3));
										pnlWest.add(pnLable, BorderLayout.WEST);
										{

											lblProject = new JLabel(" Project");
											pnLable.add(lblProject);

											lblBuyerType = new JLabel(" Buyer Type");
											pnLable.add(lblBuyerType);


											lblCancelType = new JLabel(" Reason for Cancellation");
											pnLable.add(lblCancelType);

											lblDivision = new JLabel(" Sales Division");
											pnLable.add(lblDivision);


											lblDueDate = new JLabel(" Date Cut Off");
											pnLable.add(lblDueDate);

										}
									}
									{

										JPanel pnlAction = new JPanel(new GridLayout(5, 1, 3, 3));
										pnlWest.add(pnlAction, BorderLayout.CENTER);
										{
											JPanel pnlProject = new JPanel(new BorderLayout(3,3));
											pnlAction.add(pnlProject);
											{
												lookupProjID = new _JLookup(null, "Search Project", 0);
												pnlProject.add(lookupProjID,BorderLayout.WEST);
												lookupProjID.setPreferredSize(new Dimension(50, 25));
												lookupProjID.addLookupListener(this);
											}
											{
												txtProjectName = new _JXTextField();
												pnlProject.add(txtProjectName,BorderLayout.CENTER);
												txtProjectName.setEditable(false);
												txtProjectName.setPrompt("Project Name");
											}
										}
										{
											cmbBuyerTypeModel = new DefaultComboBoxModel(new String[] {"All", "IHF Accounts", "Pag-Ibig Accounts", "Bank Finance", "Cash"});
											cmbBuyerType = new JComboBox();
											pnlAction.add(cmbBuyerType);
											cmbBuyerType.setModel(cmbBuyerTypeModel);
											cmbBuyerType.setPreferredSize(new Dimension(220, 25));
											cmbBuyerType.addActionListener(this);
										}

										{
											JPanel radion = new JPanel(new BorderLayout(3, 3));
											pnlAction.add(radion);
											{
												cmbReasonTypeModel = new DefaultComboBoxModel(
														new String[] {"All","TR Due To Docs","TR Due To Payments","OR Due To Payments"});
												cmbReasonType = new JComboBox();
												radion.add(cmbReasonType);
												cmbReasonType.setModel(cmbReasonTypeModel);
												//cmbCancelType.setBounds(122,cmbSalesDiv.getY()+25, 230, 22);
												cmbReasonType.setSelectedItem(null);
												cmbReasonType.addActionListener(this);
											}
											{/*

												{
													rbtnAll = new _JRadioButton("All");
													radion.add(rbtnAll,BorderLayout.WEST);
													rbtnAll.setActionCommand("All");
													rbtnAll.setPreferredSize(new Dimension(100, 0));
													grpRadioButton.add(rbtnAll);
													rbtnAll.addActionListener(new ActionListener() {

														@Override
														public void actionPerformed(ActionEvent e) {
															grpRadioButton.setSelected(rbtnAll.getModel(), true);
															if (grpButton.getSelection().getActionCommand().equals("New")) {
																if (tabPaneAccounts.getSelectedIndex()==0) {
																	new Thread(new Generate()).start();
																	tblPerBatch.packAll();
																}else{
																	lookupClientName.setLookupSQL(functions.generatePerAccountRecommended(proj_id, cmbBuyerType.getSelectedIndex(), rbtnDuetoDosc.isSelected(), rbtnDueToPayment.isSelected(), rbtnAll.isSelected()));
																}
															}
															if (grpButton.getSelection().getActionCommand().equals("Edit")) {
																new Thread(new Generate_Edit()).start();
																tblPerBatch.packAll();
															}
														}
													});

												}
												{
													rbtnDuetoDosc = new _JRadioButton("Due to Docs");
													radion.add(rbtnDuetoDosc,BorderLayout.CENTER);
													rbtnDuetoDosc.setActionCommand("Docs");
													grpRadioButton.add(rbtnDuetoDosc);
													rbtnDuetoDosc.addActionListener(new ActionListener() {

														@Override
														public void actionPerformed(ActionEvent e) {
															grpRadioButton.setSelected(rbtnDuetoDosc.getModel(), true);
															if (grpButton.getSelection().getActionCommand().equals("New")) {
																if (tabPaneAccounts.getSelectedIndex()==0) {
																	new Thread(new Generate()).start();
																	tblPerBatch.packAll();
																}else{
																	lookupClientName.setLookupSQL(functions.generatePerAccountRecommended(proj_id, cmbBuyerType.getSelectedIndex(), rbtnDuetoDosc.isSelected(), rbtnDueToPayment.isSelected(), rbtnAll.isSelected()));

																}
															}
															if (grpButton.getSelection().getActionCommand().equals("Edit")) {
																new Thread(new Generate_Edit()).start();
																tblPerBatch.packAll();
															}

														}
													});

												}
												{
													rbtnDueToPayment = new _JRadioButton("Due To Payment");
													radion.add(rbtnDueToPayment,BorderLayout.EAST);
													rbtnDueToPayment.setActionCommand("Payments");
													grpRadioButton.add(rbtnDueToPayment);
													rbtnDueToPayment.addActionListener(new ActionListener() {

														@Override
														public void actionPerformed(ActionEvent e) {
															grpRadioButton.setSelected(rbtnDueToPayment.getModel(), true);
															if (grpButton.getSelection().getActionCommand().equals("New")) {

																if (tabPaneAccounts.getSelectedIndex()==0) {
																	new Thread(new Generate()).start();
																	tblPerBatch.packAll();
																}else{
																	lookupClientName.setLookupSQL(functions.generatePerAccountRecommended(proj_id, cmbBuyerType.getSelectedIndex(), rbtnDuetoDosc.isSelected(), rbtnDueToPayment.isSelected(), rbtnAll.isSelected()));
																}
															}

															if (grpButton.getSelection().getActionCommand().equals("Edit")) {
																new Thread(new Generate_Edit()).start();
																tblPerBatch.packAll();
															}
														}
													});
												}
											 */}
										}
										{

											cmbSalesDivModel = new DefaultComboBoxModel(
													new String[] {"All Division","Division 1","Division 2","Division 3","Division 4"});
											cmbSalesDiv = new JComboBox();
											pnlAction.add(cmbSalesDiv);
											cmbSalesDiv.setModel(cmbSalesDivModel);
											cmbSalesDiv.setSelectedItem(null);
											cmbSalesDiv.addActionListener(this);

										}
										{
											dteDueDate = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
											pnlAction.add(dteDueDate);
											dteDueDate.setDate(dateFormat(getDateSQL()));
											//dteDueDate.setSelectableDateRange(dateFormat("2016-04-01"),dateFormat("2016-04-29"));
											//dteDueDate.setMinSelectableDate(dateFormat(getDateSQL()));

											dteDueDate.addDateListener(new DateListener() {

												@Override
												public void datePerformed(DateEvent event) {

													/*String day = "";	
													day = dateFormat_day(dteDueDate.getDate());

													System.out.println("DATE " +day );
													if (day == null) {

													}else{
														if (!(day.equals("07") || day.equals("14") || day.equals("21") || day.equals("28"))) {
															dteDueDate.setDate(null);
															JOptionPane.showMessageDialog(null,"Please select [07], [14], [21], [28] for due day","Date", JOptionPane.INFORMATION_MESSAGE);
															return;
														}

													}*/

													if(tabPaneAccounts.getSelectedIndex() ==0){
														if (grpButton.getSelection().getActionCommand().equals("New")) {
															new Thread(new Generate()).start();
															tblPerBatch.packAll();
														}

														if (grpButton.getSelection().getActionCommand().equals("Edit")) {
															new Thread(new GenerateList()).start();
															tblPerBatch.packAll();
														}
													}else{
														//lookupClientName.setLookupSQL(_FCancellationProcessing.sqlPerAcctProjCancel(lookupCompany.getValue(), lookupProjID.getValue(), cmbBuyerType.getSelectedIndex(), cmbCancelType.getSelectedIndex(), cmbSalesDiv.getSelectedIndex(), dteDueDate.getDate()));
													}
												}
											});
										}
									}
								}
								{
									JPanel pnlBatch = new JPanel(new BorderLayout(3,3));
									pnlProcess.add(pnlBatch,BorderLayout.EAST);
									pnlBatch.setPreferredSize(new Dimension(380, 20));

									{
										JPanel pnLable = new JPanel(new GridLayout(4, 1, 3, 3));
										pnlBatch.add(pnLable, BorderLayout.WEST);
										{

											lblBatchNo = new JLabel("Batch No.");
											pnLable.add(lblBatchNo);

											lblApprovedBy = new JLabel("Approved By");
											pnLable.add(lblApprovedBy);

											lbl3 = new JLabel("");
											pnLable.add(lbl3);

											lbl4 = new JLabel("");
											pnLable.add(lbl4);
										}

										JPanel pnlAction = new JPanel(new GridLayout(4, 1, 3, 3));
										pnlBatch.add(pnlAction, BorderLayout.CENTER);
										{
											{
												lookupBatchNo = new _JLookup("Search Batch No", "Batch No", 0) ;  /// XXX lookupBatchNo 
												pnlAction.add(lookupBatchNo,BorderLayout.CENTER);
												lookupBatchNo.addLookupListener(this);
												lookupBatchNo.setReturnColumn(0);
												lookupBatchNo.setPreferredSize(new Dimension(60, 20));
												lookupBatchNo.setLookupSQL(functions.listBatch());
											}

											JPanel pnlApproved = new JPanel(new BorderLayout(3,3));
											pnlAction.add(pnlApproved);

											{
												lookupApprovedBy = new _JLookup("ID", "Search Employee", 0);  /// XXX lookupApprovedBy 
												pnlApproved.add(lookupApprovedBy,BorderLayout.WEST);
												lookupApprovedBy.setPreferredSize(new Dimension(80, 25));
												lookupApprovedBy.setLookupSQL(functions.getApprovedby());
												lookupApprovedBy.addLookupListener(this);
											}
											{
												txtApprovedBy = new _JXTextField();
												pnlApproved.add(txtApprovedBy,BorderLayout.CENTER);
												txtApprovedBy.setEditable(false);
												txtApprovedBy.setPrompt("Name");
											}

											lbl5 = new JLabel("");
											pnlAction.add(lbl5);
										}
									}
								}
							}
							{
								pnlCancellationStatus = new  JPanel(new BorderLayout(5,5));
								tabPaneCenter.addTab(" Tag Accounts Status", null, pnlCancellationStatus, null);
								{

									pnlTagStatus = new pnlTagAccountsStatus(this);
									pnlCancellationStatus.add(pnlTagStatus, BorderLayout.NORTH);

								}
							}
						}
					}
					{
						tabPaneAccounts = new _JTabbedPane();
						pnlCancellation.add(tabPaneAccounts,BorderLayout.CENTER);
						{
							pnlPerBatch = new  JPanel(new BorderLayout(5,5));
							tabPaneAccounts.addTab(" Per Batch Cancellation ", null, pnlPerBatch, null);
							tabPaneAccounts.addChangeListener(new ChangeListener() {

								@Override
								public void stateChanged(ChangeEvent e) {
									int selected_tab = ((_JTabbedPane) e.getSource()).getSelectedIndex();

									if (selected_tab ==0) {
										if (lookupClientName.getValue() != null) {
											btnState(false,true, false, false, false, false, false);
										}

									}else{

										if (lookupClientName.getValue() != null) {
											btnState(false,true, false, false, false, false, false);
										}
									}
								}
							});

							/*tabPaneAccounts.addMouseListener(new MouseAdapter() {

								@Override
								public void mouseClicked(MouseEvent e) {
									if (tabPaneAccounts.getSelectedIndex()==0) {
										btnState(true, false, false, false, false, false);
										lblDueDate.setEnabled(true);
										dteDueDate.setEnabled(true);
									}else{
										lblDueDate.setEnabled(false);
										dteDueDate.setEnabled(false);
										btnState(true, false, false, false, false, true);
									}
								}

							});*/
							{
								pnlBatch = new JPanel(new BorderLayout(5, 5));
								pnlPerBatch.add(pnlBatch,BorderLayout.CENTER);
								pnlBatch.setBorder(components.JTBorderFactory.createTitleBorder(""));
								pnlBatch.setPreferredSize(new Dimension(pnlBatch.getWidth(), 0));

								{
									scrollPerBatch = new JScrollPane();
									pnlBatch.add(scrollPerBatch, BorderLayout.CENTER);
									scrollPerBatch.addMouseListener(new MouseAdapter() {
										public void mouseClicked(MouseEvent e) {
											tblPerBatch.clearSelection();
										}
									});
									{

										modelPerBatchModel = new model_CancelActive_PerBatch();
										modelPerBatchModel.addTableModelListener(new TableModelListener() {
											public void tableChanged(TableModelEvent e) {
												//Addition of rows
												if(e.getType() == 1){
													((DefaultListModel)rowHeadePerBatch.getModel()).addElement(modelPerBatchModel.getRowCount());

													if(modelPerBatchModel.getRowCount() == 0){
														rowHeadePerBatch.setModel(new DefaultListModel());
													}
												}

												if(modelPerBatchModel.getColumnClass(0).equals(Boolean.class)){


												}
											}
										});

										tblPerBatch = new _JTableMain(modelPerBatchModel);
										scrollPerBatch.setViewportView(tblPerBatch);
										modelPerBatchModel.setEditable(true);
										tblPerBatch.setSortable(false);
										tblPerBatch.setHorizontalScrollEnabled(true);
										tblPerBatch.packAll();
										
										tblPerBatch.hideColumns("Entity ID","Project ID","PBL ID",	"Seq ID","Unit ID",	"PartID","Phase","Project Name", "<html><font color = \"red\">*</font> &nbsp;<i>Cancellation Type</i>","<html><font color = \"red\">*</font> &nbsp;<i>Reason</i>","<html><font color = \"red\">*</font> &nbsp;<i>Remarks</i>");
										/** Repaint for Highlight **/
										tblPerBatch.getTableHeader().addMouseListener(new MouseAdapter() {

											public void mouseClicked(MouseEvent evt) {


												tblPerBatch.repaint();


											}
										});

										chkHeader = tblPerBatch.getCheckHeader();
										chkHeader.addActionListener(this);
										chkHeader.setActionCommand("CheckAll");

										tblPerBatch.addMouseListener(this);
										tblPerBatch.setSortable(false);
									}
									{
										rowHeadePerBatch = tblPerBatch.getRowHeader();
										rowHeadePerBatch.setModel(new DefaultListModel());
										scrollPerBatch.setRowHeaderView(rowHeadePerBatch);
										scrollPerBatch.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
										///	scrollPerBatch.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPerBatch.getRowCount())));

									}
								}//
							}//pnlBatch Center
							{

								pnlBatch_Recom = new JPanel(new BorderLayout(5, 5));
								pnlPerBatch.add(pnlBatch_Recom,BorderLayout.SOUTH);
								pnlBatch_Recom.setBorder(components.JTBorderFactory.createTitleBorder("List Of Recommended Cancellation"));
								pnlBatch_Recom.setPreferredSize(new Dimension(pnlBatch_Recom.getWidth(), 150));

								{
									cmbCancelTypeModel = new DefaultComboBoxModel(new String[] {"","Company Initiated", "Buyer Initiated"});
									cmbCancelType = new JComboBox();
									cmbCancelType.setModel(cmbCancelTypeModel);
									cmbCancelType.addActionListener(this);

								}
								{
									scrollPerBatch_Recom = new JScrollPane();
									pnlBatch_Recom.add(scrollPerBatch_Recom);
									scrollPerBatch_Recom.addMouseListener(new MouseAdapter() {
										public void mouseClicked(MouseEvent e) {
											tblRecomCancel.clearSelection();
										}
									});
									{

										modelRecommendCancel = new model_Recommend_Cancellation();
										modelRecommendCancel.addTableModelListener(new TableModelListener() {
											public void tableChanged(TableModelEvent e) {
												if(e.getType() == TableModelEvent.DELETE){
													rowHeaderRecom.setModel(new DefaultListModel());
												}
												if(e.getType() == TableModelEvent.INSERT){
													((DefaultListModel)rowHeaderRecom.getModel()).addElement(modelRecommendCancel.getRowCount());
												}
											}
										});

										tblRecomCancel = new _JTableMain(modelRecommendCancel);
										scrollPerBatch_Recom.setViewportView(tblRecomCancel);

										tblRecomCancel.hideColumns("Entity ID","Project ID","PBL ID",	"Seq ID","Unit ID",	"PartID","Phase","Project Name");
										tblRecomCancel.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
											public void valueChanged(ListSelectionEvent e) {
												if(!e.getValueIsAdjusting()){ 

												}
											}
										});
										modelRecommendCancel.setEditable(true);
										tblRecomCancel.setHorizontalScrollEnabled(true);
										tblRecomCancel.addMouseListener(this);

										chkHeaderRecom = tblRecomCancel.getCheckHeader();
										chkHeaderRecom.addActionListener(this);
										chkHeaderRecom.setActionCommand("CheckAllRecom");

									}
									{
										rowHeaderRecom = tblRecomCancel.getRowHeader();
										rowHeaderRecom.setModel(new DefaultListModel());
										scrollPerBatch_Recom.setRowHeaderView(rowHeaderRecom);
										scrollPerBatch_Recom.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
										scrollPerBatch_Recom.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(modelRecommendCancel.getRowCount())));
									}
								}//


							}
						}
						{
							pnlPerAccountCancel = new  JPanel(new BorderLayout(5,5));
							tabPaneAccounts.addTab(" Per Account Cancellation ", null, pnlPerAccountCancel, null);
							{

								pnlPerAccount = new JPanel(new BorderLayout(3, 3));
								pnlPerAccountCancel.add(pnlPerAccount,BorderLayout.CENTER);
								{
									JPanel pnlWest = new JPanel(new GridLayout(9, 1, 3, 3));
									pnlPerAccount.add(pnlWest, BorderLayout.WEST);
									{
										{
											lblClient = new JLabel("Client");
											pnlWest.add(lblClient);
										}
										{
											lblPBL = new JLabel(" Ph/Bl/Lt");
											pnlWest.add(lblPBL);
											lblPBL.setPreferredSize(new Dimension(84, 25));
										}
										{ 
											lblStage= new JLabel(" Stage");
											pnlWest.add(lblStage);
										}
										{
											lblMonthPD = new JLabel(" Months PD");
											pnlWest.add(lblMonthPD);
										}
										{
											lblDefaultDate= new JLabel(" Default Date");
											pnlWest.add(lblDefaultDate);
										}
										{
											lblCanclType = new JLabel(" Cancellation Type");
											pnlWest.add(lblCanclType);
										}
										{
											lblAmountDue= new JLabel(" Amount Due");
											pnlWest.add(lblAmountDue);
										}
										{
											lblReason= new JLabel(" Reason");
											pnlWest.add(lblReason);
										}
										{
											lblRemarks= new JLabel(" Remarks");
											pnlWest.add(lblRemarks);
										}
									}
								}
								{
									JPanel pnlAction = new JPanel(new GridLayout(9, 1, 3, 3));
									pnlPerAccount.add( pnlAction,BorderLayout.CENTER);	
									{
										{
											JPanel pnlClient = new JPanel(new BorderLayout(3,3));
											pnlAction.add(pnlClient,BorderLayout.CENTER);
											{
												//lookupClientName = new _JLookup("ID", "Search Client", 0);  /// XXX lookupClientName
												lookupClientName = new _JLookup(null, "Client");
												pnlClient.add(lookupClientName,BorderLayout.WEST);
												lookupClientName.setPreferredSize(new Dimension(100, 0));
												lookupClientName.addLookupListener(this);
												//lookupClientName.setLookupSQL(functions.generatePerAccountRecommended(proj_id, cmbBuyerType.getSelectedIndex(), false, true, false));
												/*lookupClientName.addLookupListener(new LookupListener() {
													
													public void lookupPerformed(LookupEvent event) {
														Object [] data = ((_JLookup) event.getSource()).getDataSet();
														
														if(data != null){
															String entity_name = (String) data[1];
															String unit_id = (String) data[2];
															String unit_desc = (String) data[3];
															String part_id = (String) data[4];
															String part_desc = (String) data[5];
															String default_date = (String) data[6];
															BigDecimal amt_due = (BigDecimal) data[7];
															
															
														}
													}
												});*/
											}
											{
												txtClientName = new _JXTextField();
												pnlClient.add(txtClientName,BorderLayout.CENTER);
												txtClientName.setEditable(false);
												txtClientName.setPrompt("Client Name");
											}
										}
										{
											JPanel pnlPBL = new JPanel(new BorderLayout(3,3));
											pnlAction.add(pnlPBL);			
											{
												{ 
													txtPBLID = new JTextField();
													pnlPBL.add(txtPBLID, BorderLayout.WEST);
													txtPBLID.setPreferredSize(new Dimension(100, 0));
													txtPBLID.setEditable(false);
													txtPBLID.setHorizontalAlignment(JTextField.CENTER);
												}
												{
													txtPBLDesc = new JTextField();
													pnlPBL.add(txtPBLDesc);
													txtPBLDesc.setEditable(false);
												}
											}
										}
										{
											JPanel pnlStage = new JPanel(new BorderLayout(3, 3));
											pnlAction.add(pnlStage);
											{
												txtStageID = new JTextField();
												pnlStage.add(txtStageID, BorderLayout.WEST);
												txtStageID.setPreferredSize(new Dimension(100, 0));
												txtStageID.setEditable(false);
												txtStageID.setHorizontalAlignment(JTextField.CENTER);
											}
											{
												txtStageDesc = new JTextField();
												pnlStage.add(txtStageDesc);
												txtStageDesc.setEditable(false);
											}
										}
										{
											txtMonthPD = new JTextField();
											pnlAction.add(txtMonthPD, BorderLayout.WEST);
											txtMonthPD.setEditable(false);
										}
										{
											txtDefaultDate = new JTextField();
											pnlAction.add(txtDefaultDate, BorderLayout.WEST);
											txtDefaultDate.setEditable(false);
										}
										{
											cmbCancelTypeModel_Per = new DefaultComboBoxModel(new String[] {"","Company Initiated", "Buyer Initiated"});
											cmbCancelType_Per = new JComboBox();
											pnlAction.add(cmbCancelType_Per, BorderLayout.WEST);
											cmbCancelType_Per.setModel(cmbCancelTypeModel_Per);
											cmbCancelType_Per.addActionListener(this);

										}
										{

											txtAmountDue = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
											pnlAction.add(txtAmountDue, BorderLayout.WEST);
											txtAmountDue.setFormatterFactory(_JXFormattedTextField.DECIMAL);
											txtAmountDue.setEditable(false);
										}
										{ 
											JPanel pnlLookUp_Reason = new JPanel(new BorderLayout(3,3));
											pnlAction.add(pnlLookUp_Reason,BorderLayout.CENTER);
											{
												lookupReason = new _JLookup("Reason ID ", "Reason Desc.", 0); /// XXX lookupReason 
												pnlLookUp_Reason.add(lookupReason,BorderLayout.WEST);
												lookupReason.setLookupSQL("SELECT remark_id, remdesc FROM mf_remarks WHERE remark_id IN ('00232','00227','00034','00035','00036','00037','00039', '00127') /*AND remtype = '02'*/ ORDER BY remdesc");
												lookupReason.setPreferredSize(new Dimension(100, 0));
												lookupReason.addLookupListener(this);

											}
											{
												txtReason_ = new JTextField();
												pnlLookUp_Reason.add(txtReason_,BorderLayout.CENTER);
												txtReason_.setEditable(false);
											}
										}
										{

											JPanel pnlTextArea = new JPanel(new BorderLayout(3,3));
											pnlAction.add(pnlTextArea,BorderLayout.CENTER);
											{
												scrollRemarks = new JScrollPane();
												pnlTextArea.add(scrollRemarks,BorderLayout.CENTER);
												scrollRemarks.setOpaque(true);
												scrollRemarks.setBorder(FncGlobal.lineBorder);
												{
													txtareaRemarks = new JTextArea();
													scrollRemarks.add(txtareaRemarks);
													scrollRemarks.setViewportView(txtareaRemarks);
													txtareaRemarks.setLineWrap(true);
													txtareaRemarks.setWrapStyleWord(true);
													txtareaRemarks.setAutoscrolls(true);
													txtareaRemarks.setText("");	
												}
											}
										}
									}
									{
										JPanel pnlEast = new JPanel(new BorderLayout(3,3));
										pnlPerAccountCancel.add(pnlEast,BorderLayout.EAST);
										pnlEast.setPreferredSize(new Dimension(300, 0));
									}
								}
							}
						}
						{

							pnlSouth = new JXPanel(new BorderLayout());
							pnlCancellation.add(pnlSouth,BorderLayout.SOUTH);
							pnlSouth.setPreferredSize(new Dimension(0, 30));

							{

								JPanel pnlButtonP = new JPanel(new BorderLayout(3, 3));
								pnlSouth.add(pnlButtonP,BorderLayout.WEST);
								pnlButtonP.setPreferredSize(new Dimension(300, 30));
								{
									{
										JPanel pnlButton = new JPanel(new GridLayout(1, 2, 3, 3));
										pnlButtonP.add(pnlButton,BorderLayout.CENTER);

										{
											btnPreview = new _JButton("Preview");
											pnlButton.add(btnPreview, BorderLayout.WEST);
											btnPreview.setActionCommand("Preview");
											btnPreview.setPreferredSize(new Dimension(150, 30));
											btnPreview.addActionListener(this);
										}
										{

											btnUnRecommend = new _JButton("UnRecommend");
											pnlButton.add(btnUnRecommend, BorderLayout.WEST);
											btnUnRecommend.setActionCommand("UnRecommend");
											btnUnRecommend.setPreferredSize(new Dimension(150, 30));
											btnUnRecommend.addActionListener(this);

										}
									}

								}
							}
							{
								JPanel pnlButton = new JPanel(new GridLayout(1, 5, 3, 3));
								pnlSouth.add(pnlButton,BorderLayout.EAST);
								pnlButton.setPreferredSize(new Dimension(500, 30));
								{
									{
										btnNew = new _JButton("New");
										pnlButton.add(btnNew);
										btnNew.setActionCommand("New");
										btnNew.addActionListener(this);
										grpButton.add(btnNew);
									}
									{
										btnEdit = new _JButton("Edit");
										pnlButton.add(btnEdit);
										btnEdit.setActionCommand("Edit");
										btnEdit.addActionListener(this);
										grpButton.add(btnEdit);
									}
									{
										btnSave = new _JButton("Save");
										pnlButton.add(btnSave);
										btnSave.setActionCommand("Save");
										btnSave.addActionListener(this);
									}
									{
										btnPost = new _JButton("Post");
										pnlButton.add(btnPost);
										btnPost.setActionCommand("Post");
										btnPost.addActionListener(this);
									}
									{
										btnClear = new _JButton("Clear");
										pnlButton.add(btnClear);
										btnClear.setActionCommand("Cancel");
										btnClear.addActionListener(this);
										grpButton.add(btnClear);

									}
								}
							}
						}
					}
				}//pnlCenter
			}
		} catch (Exception e) {
		}
	}

	@Override
	public void lookupPerformed(LookupEvent e) { // XXX lookupPerformed
		if (e.getSource().equals(lookupProjID)) {
			Object[] data = ((_JLookup)e.getSource()).getDataSet();

			if(data != null){

				proj_id = data[0].toString();
				proj_name = data[1].toString();

				txtProjectName.setText(proj_name);
				lookupClientName.setLookupSQL(functions.generatePerAccountRecommended(proj_id, cmbBuyerType.getSelectedIndex(), false, true, false));
			}
		}

		if (e.getSource().equals(lookupBatchNo)) {
			Object[] data = ((_JLookup)e.getSource()).getDataSet();

			if (data != null) {
				batch_no = data[0].toString();
				//rbtnAll.setSelected(true);
				tabPaneAccounts.setSelectedIndex(0);

				Edit_Process(batch_no);
				grpButton.setSelected(btnEdit.getModel(), true);
			}
		}

		if (e.getSource().equals(lookupApprovedBy)) {
			Object[] data = ((_JLookup)e.getSource()).getDataSet();

			if (data != null) {
				approved_id = data[0].toString();
				approved_name = data[1].toString();
				lookupApprovedBy.setValue(approved_id);
				txtApprovedBy.setText(approved_name);
				lookupApprovedBy.setEnabled(true);
				lblApprovedBy.setEnabled(true);

			}
		}

		if (e.getSource().equals(lookupReason)) {
			Object[] data = ((_JLookup)e.getSource()).getDataSet();

			if (data != null) {
				reason_id = data[0].toString();
				reason_name = data[1].toString();

				lookupReason.setValue(reason_id);
				txtReason_.setText(reason_name);

			}
		}

		if (e.getSource().equals(lookupClientName)) {
			System.out.printf("Display value of lookupProject: %s%n", lookupProjID.getValue());
			FncSystem.out("Display SQL:", lookupClientName.getLookupSQL());
			if(lookupProjID.getValue() == null){
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select project", "Client", JOptionPane.WARNING_MESSAGE);
			}else{
				Object[] data = ((_JLookup)e.getSource()).getDataSet();

				if (data != null) {
					entity_id = data[4].toString();
					proj_id = data[5].toString();
					pbl_id = data[6].toString();
					seq_no = (Integer) data[7];

					client_name = data[1].toString();
					
					//
					if(proj_id.equals("015")){
						pbl_desc = data[0].toString().replace("TV - ", "");
					}else{
						pbl_desc = data[0].toString().split("-")[1];
					}
					
					System.out.printf("Display value of data 1: %s%n(%s)", data[0], data[1]);

					pgSelect db = new pgSelect();

					db.select("select get_payment_stage('"+entity_id+"','"+proj_id+"',TRIM('"+pbl_id+"'),"+seq_no+"), \n" + 
							"sp_months_past_due('"+entity_id+"','"+proj_id+"',TRIM('"+pbl_id+"'),"+seq_no+",CURRENT_DATE,false ),\n" + 
							"_get_default_date('"+entity_id+"','"+proj_id+"',TRIM('"+pbl_id+"'),"+seq_no+",CURRENT_DATE ),\n" + 
							"NULLIF(to_char(_get_default_date('"+entity_id+"','"+proj_id+"',TRIM('"+pbl_id+"'),"+seq_no+",CURRENT_DATE ), 'FMMonth dd, YYYY'), '<NULL>'),\n" +
							"sp_compute_amount_due('"+entity_id+"','"+proj_id+"',TRIM('"+pbl_id+"'),"+seq_no+", CURRENT_DATE, FALSE)\n" + 
							"");

					if (db.isNotNull()) {

						payment_stage = (String) (db.Result[0][0] == null ? "" : db.Result[0][0]);
						stage_id = payment_stage.contains("PF") ? "019" : (payment_stage.contains("DP") ? "013" : (payment_stage.contains("MA") ? "014" : "012"));
						months_pd = (Integer) (db.Result[0][1] == null ? "" : db.Result[0][1]);
						System.out.printf("Display value of result: %s%n", db.getResult()[0][2]);
						default_date =  (Date) (db.Result[0][2] == null ? null : db.Result[0][2]);
						default_date_format = (String) (db.Result[0][3] == null ? "" : db.Result[0][3]);
						amount_due =  (BigDecimal) (db.Result[0][4] == null ? "" : db.Result[0][4]);


						lookupClientName.setValue(entity_id);
						txtClientName.setText(client_name);
						txtPBLID.setText(pbl_id);
						txtPBLDesc.setText(pbl_desc);
						txtStageID.setText(stage_id);
						txtStageDesc.setText(payment_stage);
						txtMonthPD.setText(String.valueOf(months_pd));
						txtDefaultDate.setText(default_date_format);
						txtAmountDue.setValue(amount_due);

					}
				}
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) { // XXX actionPerformed

		if (e.getActionCommand().equals("CheckAll")) {

			if (chkHeader.isSelected()) {
				System.out.println("*********************CHECK ALL");
			}else{
				System.out.println("*********************UNCHECK ALL");
			}
		}

		if (e.getActionCommand().equals("CheckAllRecom")) {

			if (chkHeaderRecom.isSelected()) {
				System.out.println("*********************CHECK ALL");
			}else{
				System.out.println("*********************UNCHECK ALL");
			}
		}


		if (e.getActionCommand().equals("New")) {
			grpButton.setSelected(btnNew.getModel(), true);
			if (tabPaneAccounts.getSelectedIndex()  == 0) {

				this.setComponentsEnabled(pnlCancellation, true);
				tabPaneAccounts.setEnabled(true);
				btnState(false,false, false, true, false, true, true);
				tabPaneAccounts.setEnabledAt(1, false);
				this.setComponentsEnabled(pnlBatch, true);
				this.setComponentsEnabled(pnlBatch_Recom, true);
				this.setComponentsEditable(pnlBatch_Recom, true);
				lblDueDate.setEnabled(true);
				dteDueDate.setEnabled(true);
				dteDueDate.setDate(dateFormat(getDateSQL()));

				modelRecommendCancel.setEditable(false);
				tblPerBatch.setSortable(false);

			}

			if (tabPaneAccounts.getSelectedIndex()  == 1) {
				this.setComponentsEnabled(pnlCancellation, true);
				tabPaneAccounts.setEnabled(true);
				btnState(false,false, false, true, false, true, true);
				tabPaneAccounts.setEnabledAt(0, false);
				lblDueDate.setEnabled(true);
				dteDueDate.setEnabled(true);
				

				this.setComponentsEnabled(pnlPerAccount, true);
			}

			getDefaultApproval();
			batch_no = functions.getBatchNo();
			//lookupBatchNo.setValue(functions.getBatchNo());
			lookupBatchNo.setEditable(false);
			lookupBatchNo.setEnabled(false);
			lookupProjID.requestFocus();
		}

		if (e.getActionCommand().equals("Save")) {
			grpButton.setSelected(btnSave.getModel(), true);
			if (tabPaneAccounts.getSelectedIndex() == 0) {

				if (checkRequiredFields()) {
					int response = JOptionPane.showConfirmDialog(getContentPane(),"Are you sure you want to save selected account(s)  ","Confirm",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if (response == JOptionPane.YES_OPTION) {

						new Thread(new Save_Request()).start();
					}
				}

			}

			if (tabPaneAccounts.getSelectedIndex() == 1) {

				if (tabPaneAccounts.getSelectedIndex() == 1) {
					int response = JOptionPane.showConfirmDialog(getContentPane(),"Are you sure you want to save selected account(s)  ","Confirm",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if (response == JOptionPane.YES_OPTION) {

						ToSavePerAccount();
						clearPerAcct();
					}
				}
			}
		}


		if (e.getActionCommand().equals("Post")) {

			new Thread(new Post_Cancellation()).start();
		}

		if (e.getActionCommand().equals("Edit")) {
			modelRecommendCancel.setEditable(false);
			grpButton.setSelected(btnEdit.getModel(), true);

			if (!(UserInfo.Position.contains("Head") || UserInfo.ADMIN || UserInfo.EmployeeCode.equals("900421") || UserInfo.EmployeeCode.equals("900414")|| UserInfo.EmployeeCode.equals("900767")|| UserInfo.EmployeeCode.equals("900864") || UserInfo.EmployeeCode.equals("900864") || UserInfo.EmployeeCode.equals("900298") || UserInfo.EmployeeCode.equals("900024"))) {

				JOptionPane.showMessageDialog(getContentPane(),"Please tell your department head to edit or unrecommend this batch no. ","Edit",JOptionPane.OK_OPTION);
				return;
			}

			modelPerBatchModel.setEditable(true);
			modelRecommendCancel.setEditable(true);

			this.setComponentsEnabled(pnlCancellation, true);
			tabPaneAccounts.setEnabled(true);

			btnState(true,false, false, true, false, true, true);
			tabPaneAccounts.setEnabledAt(1, false);
			this.setComponentsEnabled(pnlBatch, true);
			this.setComponentsEnabled(pnlBatch_Recom, true);
			lblDueDate.setEnabled(true);
			dteDueDate.setEnabled(true);
			dteDueDate.setDate(dateFormat(getDateSQL()));

			lookupBatchNo.setEditable(false);
			lookupBatchNo.setEnabled(false);

		}

		if (e.getActionCommand().equals("UnRecommend")) {
			modelRecommendCancel.setEditable(true);
			if (hasSelected()) {
				int response = JOptionPane.showConfirmDialog(this,"Are you sure you want to Unrecommend this client ","Confirm",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (response == JOptionPane.YES_OPTION) {

					pgUpdate db = new pgUpdate();

					for (int i = 0; i < modelRecommendCancel.getRowCount(); i++) {
						Boolean isSelected = (Boolean) modelRecommendCancel.getValueAt(i, 0);

						String entityID = (String) modelRecommendCancel.getValueAt(i, 7);
						String projID = (String) modelRecommendCancel.getValueAt(i, 8);
						String pblID = (String) modelRecommendCancel.getValueAt(i, 9);
						Integer seqNo = (Integer) modelRecommendCancel.getValueAt(i, 10);


						if (isSelected) {
							db.executeUpdate("UPDATE rf_cancellation set status_id = 'I'\n" + 
									"where batch_no = '"+lookupBatchNo.getValue()+"'\n" + 
									"and entity_id = '"+entityID+"'\n" + 
									"and proj_id = '"+projID+"'\n" + 
									"and pbl_id = '"+pblID+"' \n" + 
									"and seq_no = "+seqNo+"\n" + 
									"and status_id = 'A' ", true);

						}
					}

					db.commit();
					JOptionPane.showMessageDialog( getContentPane(), "Your Unrecommendation has been successfully ", "Successfull", JOptionPane.INFORMATION_MESSAGE );

					new Thread(new Generate_Edit()).start();
					tblRecomCancel.packAll();

					formLoad();

					if (!(UserInfo.Rank_Desc.contains("Head") || UserInfo.ADMIN || UserInfo.EmployeeCode.equals("900421") || UserInfo.EmployeeCode.equals("900414")|| UserInfo.EmployeeCode.equals("900767")|| UserInfo.EmployeeCode.equals("900864"))) {
						btnState(false,false, true, false, false, true, true); 	
					}else{

						btnState(false,false, true, false, true, true, true);	

					}
				}
			}else{
				JOptionPane.showMessageDialog( getContentPane(), "Please select client", "UnRecommend", JOptionPane.WARNING_MESSAGE );
			}


		}

		if (e.getActionCommand().equals("Cancel")) {

			grpButton.setSelected(btnClear.getModel(), true);
			int response = JOptionPane.showConfirmDialog(this,"Are you sure you want to Clear all fields? ","Confirm",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (response == JOptionPane.YES_OPTION) {
				clear();
				formLoad();
			}
		}

		if (e.getActionCommand().equals("Preview")) {
			if (tabPaneCenter.getSelectedIndex() == 0) {
				if (tabPaneAccounts.getSelectedIndex() == 0) {
					preview_batchAccount();	
				}else{
					preview_perAccount();
				}	
			}

			if (tabPaneCenter.getSelectedIndex() == 1) {
				dlgDate date_cancel = new dlgDate(FncGlobal.homeMDI, "Print Report");
				date_cancel.setLocationRelativeTo(null);
				date_cancel.setVisible(true);

				dateFrom = date_cancel.getDateFrom();
				dateTo = date_cancel.getDateTo();

				printall = date_cancel.getPrintAll();
				new Thread(new ForPreviewStatus()).start();
			}
		}

		if (e.getSource().equals(cmbBuyerType)) {/*

			if (grpButton.getSelection().getActionCommand().equals("New")) {
				if (tabPaneAccounts.getSelectedIndex()  == 0) {

					if (dteDueDate.getDate() != null) {
						new Thread(new Generate()).start();
						tblPerBatch.packAll();
					}
				}
				if (tabPaneAccounts.getSelectedIndex()  == 1) {

				}	
			}

			if (grpButton.getSelection().getActionCommand().equals("Edit")) {
				if (tabPaneAccounts.getSelectedIndex()  == 0) {

					if (dteDueDate.getDate() != null) {
						new Thread(new Generate_Edit()).start();
						tblPerBatch.packAll();
					}
				}

				if (tabPaneAccounts.getSelectedIndex()  == 1) {

				}		
			}
		 */}
	}

	private void btnState(Boolean sUnrecommend,Boolean sNew,Boolean sEdit ,Boolean sSave,Boolean sPost,Boolean sPreview,Boolean sClear ){
		btnUnRecommend.setEnabled(sUnrecommend);
		btnNew.setEnabled(sNew);
		btnEdit.setEnabled(sEdit);
		btnSave.setEnabled(sSave);
		btnPost.setEnabled(sPost);
		btnPreview.setEnabled(sPreview);
		btnClear.setEnabled(sClear);

	}

	private void formLoad(){
		this.setComponentsEnabled(pnlCancellation, false);

		btnState(false,true, false, false, false, false, false);

		lblBatchNo.setEnabled(true);
		lookupBatchNo.setEditable(true);
		lookupBatchNo.setEnabled(true);
		this.setComponentsEnabled(pnlBatch, false);
		this.setComponentsEnabled(pnlBatch_Recom, false);
		this.setComponentsEnabled(pnlPerAccount, false);
		tabPaneAccounts.setEnabledAt(0, true);
		tabPaneAccounts.setEnabledAt(1, true);
	}

	private void getDefaultCompany(){
		co_id = "02";
		company = "CENQHOMES DEVELOPMENT CORPORATION" ;
		company_logo = "cenq_logo.jpg";

		txtCompany.setText(company);
		lookupCompany.setValue(co_id);

		lookupProjID.setLookupSQL(functions.lookupProjects(co_id));

		pnlTagStatus.lookupProjID.setLookupSQL(functions.lookupProjects(co_id));
	}

	private void getDefaultApproval(){

		approved_id = "987120";
		approved_name = "FLORES, NELIA DONOSO";

		lookupApprovedBy.setValue(approved_id);
		txtApprovedBy.setText(approved_name);

	}

	public class Generate implements Runnable{

		@Override
		public void run() {
			FncGlobal.startProgress("Please wait. . . ");

			rowHeadePerBatch.setModel(new DefaultListModel());
			functions.generatePerBatchRecommended(modelPerBatchModel, rowHeadePerBatch, proj_id, dteDueDate.getDate(), cmbBuyerType.getSelectedIndex(),cmbReasonType.getSelectedIndex(),cmbSalesDiv.getSelectedIndex());
			scrollPerBatch.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPerBatch.getRowCount())));

			tblPerBatch.packAll();
			FncGlobal.stopProgress();
		}
	}

	public class GenerateList implements Runnable{

		@Override
		public void run() {
			FncGlobal.startProgress("Please wait. . . ");

			rowHeadePerBatch.setModel(new DefaultListModel());
			functions.generatePerBatchRecommended_edit(modelPerBatchModel, rowHeadePerBatch, proj_id, dteDueDate.getDate(), cmbBuyerType.getSelectedIndex(),batch_no,cmbReasonType.getSelectedIndex());
			scrollPerBatch.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPerBatch.getRowCount())));

			tblPerBatch.packAll();
			FncGlobal.stopProgress();
		}
	}

	public class Save_Request implements Runnable{

		@Override
		public void run() {
			FncGlobal.startProgress("Please wait. . . ");
			toSave();
			FncGlobal.stopProgress();
		}
	}

	private void toSave(){

		String SQL= "";

		ArrayList<String> listEntityID = new ArrayList<String>();
		ArrayList<String> listprojID = new ArrayList<String>();
		ArrayList<String> listpblID = new ArrayList<String>();
		ArrayList<String> listSeqNo = new ArrayList<String>();

		ArrayList<String> listpartID = new ArrayList<String>();

		ArrayList<String> listreason = new ArrayList<String>();
		ArrayList<String> listremarks = new ArrayList<String>();
		ArrayList<String> listcancelType = new ArrayList<String>();

		for(int x=0; x < modelRecommendCancel.getRowCount(); x++){

			String entityID = (String) modelRecommendCancel.getValueAt(x, 7);
			String projID = (String) modelRecommendCancel.getValueAt(x, 8);
			String pblID = (String) modelRecommendCancel.getValueAt(x, 9);
			String seqNo =  String.valueOf(modelRecommendCancel.getValueAt(x, 10));

			String partID = (String) modelRecommendCancel.getValueAt(x, 11);

			String cancel_desc = (String) modelRecommendCancel.getValueAt(x, 12);

			String reason = (String) modelRecommendCancel.getValueAt(x, 13);
			String remarks = ((String) modelRecommendCancel.getValueAt(x, 14)).trim().replace("'", "''");;

			if (cancel_desc.contains("Company")){
				cancel_type = "C";
			}

			if (cancel_desc.contains("Buyer")){
				cancel_type = "B";
			}

			listEntityID.add(String.format("'%s'", entityID));
			listprojID.add(String.format("'%s'", projID));
			listpblID.add(String.format("'%s'", pblID));
			listSeqNo.add(String.format("'%s'", seqNo));

			listpartID.add(String.format("'%s'", partID));
			listreason.add(String.format("'%s'", reason));
			listremarks.add(String.format("'%s'", remarks));
			listcancelType.add(String.format("'%s'", cancel_type));

		}

		String entityID = listEntityID.toString().replaceAll("\\[|\\]", "");
		String projID = listprojID.toString().replaceAll("\\[|\\]", "");
		String pblID =listpblID.toString().replaceAll("\\[|\\]", "");
		String seqNo = listSeqNo.toString().replaceAll("\\[|\\]", "");
		String partID = listpartID.toString().replaceAll("\\[|\\]", "");
		String reason =listreason.toString().replaceAll("\\[|\\]", "");
		String remarks = listremarks.toString().replaceAll("\\[|\\]", "");
		String cancel_desc =listcancelType.toString().replaceAll("\\[|\\]", "");

		//SQL = "SELECT sp_save_notices_sent(ARRAY["+ batch_no +"]::VARCHAR[], '"+ UserInfo.EmployeeCode +"','tagged');";
		SQL = "SELECT sp_save_req_for_cancellation(\n" + 
				"ARRAY["+ entityID +"]::VARCHAR[]   ,---p_entity_id character varying,\n" + 
				"ARRAY["+ projID +"]::VARCHAR[]     ,---p_proj_id character varying,\n" + 
				"ARRAY["+ pblID +"]::VARCHAR[]      ,---p_pbl_id character varying,\n" + 
				"ARRAY["+ seqNo +"]::VARCHAR[]      ,---p_seq_no integer,\n" + 
				"ARRAY["+ partID +"]::VARCHAR[]     ,---p_part_id character varying,\n" + 
				"'"+batch_no+"'						,---p_batchno character varying,\n" + 
				"'"+dteDueDate.getDate()+"'         ,---p_date timestamp without time zone,\n" + 
				"'"+UserInfo.EmployeeCode+"'        ,---p_user_id character varying,\n" +
				"'"+lookupApprovedBy.getValue()+"'  ,---p_approvedby character varying,\n" +
				"ARRAY["+ reason +"]::VARCHAR[]     ,---p_reason character varying,\n" +
				"ARRAY["+ remarks +"]::VARCHAR[]    ,---p_remarks character varying,\n" +
				"ARRAY["+ cancel_desc +"]::VARCHAR[]---p_cancel_type character varying,\n" +
				"    )---p_selected boolean \n" + 
				"";

		System.out.printf("SQL: %s%n", SQL);

		pgSelect db = new pgSelect();
		db.select(SQL);

		JOptionPane.showMessageDialog( getContentPane(), "Your request has been successfully ", "Successful", JOptionPane.INFORMATION_MESSAGE );
		preview_batchAccount();


		clear();
		formLoad();
	}

	private void ToSavePerAccount(){

		String entityID = lookupClientName.getValue();
		String projID = lookupProjID.getValue();
		String pblID = txtPBLID.getText();
		Integer seqNo = seq_no;
		String partID = txtStageID.getText();
		String cancel_type = cmbCancelType_Per.getSelectedIndex() == 1  ? "C" : (cmbCancelType_Per.getSelectedIndex() == 2 ? "B" : "");
		String reason = lookupReason.getValue();
		String remarks = txtareaRemarks.getText().replace("'", "''");


		String SQL = "SELECT sp_save_req_for_cancellation_per_account(\n" + 
				"'"+entityID+"'    ,---p_entity_id character varying,\n" + 
				"'"+projID+"'    ,---p_proj_id character varying,\n" + 
				"TRIM('"+pblID+"')    ,---p_pbl_id character varying,\n" + 
				""+seqNo+"    ,---p_seq_no integer,\n" + 
				"'"+partID+"'    ,---p_part_id character varying,\n" + 
				"'"+batch_no+"'                ,---p_batchno character varying,\n" + 
				"'"+dteDueDate.getDate()+"'                    ,---p_date timestamp without time zone,\n" + 
				"'"+UserInfo.EmployeeCode+"'                   ,---p_user_id character varying,\n" +
				"'"+lookupApprovedBy.getValue()+"'             ,---p_approvedby character varying,\n" +
				"'"+reason+"'    ,---p_reason character varying,\n" +
				"'"+remarks+"'    ,---p_remarks character varying,\n" +
				"'"+cancel_type+"'                             ---p_cancel_type character varying,\n" +
				")";

		pgSelect db = new pgSelect();

		db.select(SQL);

		JOptionPane.showMessageDialog( getContentPane(), "Your request has been successfully ", "Successful", JOptionPane.INFORMATION_MESSAGE );
	}

	public class Post_Cancellation implements Runnable{

		@Override
		public void run() {
			FncGlobal.startProgress("Please wait. . . ");


			String cancel_type = "";
			String reason = "";
			String remarks = "";

			if (tabPaneAccounts.getSelectedIndex() == 0) {
				for (int i = 0; i < modelRecommendCancel.getRowCount(); i++) {
					cancel_type = (String) modelRecommendCancel.getValueAt(i, 12);
					reason = (String) modelRecommendCancel.getValueAt(i, 13);
					remarks = (String) modelRecommendCancel.getValueAt(i, 14);

					String entityID_ =  (String) modelRecommendCancel.getValueAt(i, 7);
					String projID_ =  (String) modelRecommendCancel.getValueAt(i, 8);
					String pblID_ =  (String) modelRecommendCancel.getValueAt(i, 9).toString().trim();
					Integer seqNo_ =  (Integer) modelRecommendCancel.getValueAt(i,10);

					if (cancel_type == null|| reason == null || remarks == null) {
						JOptionPane.showMessageDialog(getContentPane(),"Please Indicate the Reason for Cancellation on the ''Reason Column''  \n You can also put Remarks on ''Remarks Column''","Incomplete Details",JOptionPane.OK_OPTION);
						FncGlobal.stopProgress(); 
						return;

					}

					if (ifSoldToBank(entityID_,projID_,pblID_,seqNo_) == true) {
						String client_name =  (String) modelRecommendCancel.getValueAt(i, 2);
						JOptionPane.showMessageDialog(getContentPane(),"This client "+client_name+" with status sold to bank ","Sold to Bank",JOptionPane.OK_OPTION);
						FncGlobal.stopProgress(); 
						return;
					}
				}

			}else{

				cancel_type = (String) cmbCancelType_Per.getSelectedItem();
				reason = (String) txtReason_.getText();
				remarks = (String) txtareaRemarks.getText();


				if (cancel_type == null|| reason == null || remarks == null) {
					JOptionPane.showMessageDialog(getContentPane(),"Please Indicate the Reason for Cancellation on the ''Reason Column''  \n You can also put Remarks on ''Remarks Column''","Incomplete Details",JOptionPane.OK_OPTION);
					FncGlobal.stopProgress(); 
					return;

				}
			}


			int response = JOptionPane.showConfirmDialog(getContentPane(),"Are you sure you want to cancel this batch(s)  ","Confirm",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (response == JOptionPane.YES_OPTION) {

				toPost();


			}



			FncGlobal.stopProgress();

		}
	}

	private void toPost(){

		String SQL = "";
		pgSelect db = new pgSelect();
		batch_no = lookupBatchNo.getValue().toString();


		noticeBatchno = functions.getNoticeBatchNo();
		//for (int i = 0; i < modelPerBatchModel.getRowCount(); i++) {
		//	Boolean isSelected = (Boolean) modelPerBatchModel.getValueAt(i, 0);

		//if (isSelected) {

		SQL = "SELECT sp_batch_cancellation(\n" +
				"'"+batch_no+"'    ,---p_batch_no character varying,\n" + 
				"'"+noticeBatchno+"'    ,---noticeBatchno character varying,\n" + 
				"'"+co_id+"'    ,---p_co_id character varying,\n" + 
				"'"+UserInfo.EmployeeCode+"'    ,---p_user_id character varying,\n" + 
				"'"+UserInfo.Branch+"'   --p_branch_id character varying \n" +
				");";

		FncSystem.out("SQL :", SQL);

		db.select(SQL);




		JOptionPane.showMessageDialog( getContentPane(), "Account(s) Cancelled with Batch No. : "+batch_no+"   ", "Successful", JOptionPane.INFORMATION_MESSAGE );



		PrintBatchReport(noticeBatchno);
		clear();
		formLoad();
	}

	@Override
	public void mouseClicked(MouseEvent e) {

		if (e.getSource().equals(tblRecomCancel)) {
			//if (modelPerBatchModel.getValueAt(tblPerBatch.getSelectedRow(), 0).equals(true)) { 

			if (tblRecomCancel.getSelectedColumn()== 7) {
				System.out.println("CANCELLATION TYPE");
				TableColumn monthcolumn = tblRecomCancel.getColumnModel().getColumn(7);
				monthcolumn.setCellEditor(new DefaultCellEditor(cmbCancelType));
			}

			if (tblRecomCancel.getSelectedColumn()== 8) {
				String SQL = "select remark_id, remdesc from mf_remarks where remark_id in ('00232','00227','00034','00035','00036','00037','00039') and remtype = '02' order by remdesc";

				_JLookupTable dlg = new _JLookupTable(FncGlobal.homeMDI, null, SQL, false);

				dlg.setLocationRelativeTo(FncGlobal.homeMDI);
				dlg.setVisible(true); 

				Object[] data = dlg.getReturnDataSet();

				if(data != null){

					modelRecommendCancel.setValueAt(data[1], tblRecomCancel.getSelectedRow(),13);
					//reason_id = data[0].toString();
					tblPerBatch.packAll();

				}
			} // 

			/** Listener for boolean canEditStatus **/
			if (tblRecomCancel.getSelectedColumn()== 9) {/*

					System.out.println("Enable :" + modelRecommendCancel.getValueAt(tblRecomCancel.getSelectedRow(),0).equals(true));
					if( modelRecommendCancel.getValueAt(tblRecomCancel.getSelectedRow(),0).equals(true)){
						modelRecommendCancel.setEditable((modelRecommendCancel.getValueAt(tblRecomCancel.getSelectedRow(),0).equals(true)) );	
					}
			 */} //
			//}

		}
		if (e.getSource().equals(tblPerBatch)) {

			MoveForRecommendCancel();	

			scrollPerBatch_Recom.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblRecomCancel.getRowCount())));
			tblRecomCancel.packAll(11);
		}




		if (e.getSource().equals(tblRecomCancel)) {
			toRemoveListSelected();
		}
	}

	public Date dateFormat(String dates){

		DateFormat formatter ; 
		Date date = null ;

		formatter = new SimpleDateFormat("yyyy-MM-dd");

		try {
			date = (Date)formatter.parse(dates);
		} catch (ParseException e){
		} 

		return date;
	}

	private  String getDateSQL(){
		pgSelect db = new pgSelect();
		db.select("SELECT CURRENT_DATE");
		return db.Result[0][0].toString();

	}


	private void clear(){

		lookupProjID.setValue("");
		txtProjectName.setText("");
		lookupBatchNo.setValue("");

		lookupApprovedBy.setValue("");
		txtApprovedBy.setText("");

		cmbBuyerType.setSelectedIndex(0);
		//	dteDueDate.setDate(null);

		modelPerBatchModel.clear();
		rowHeadePerBatch.setModel(new DefaultListModel());
		scrollPerBatch.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPerBatch.getRowCount())));

		modelRecommendCancel.clear();
		rowHeaderRecom.setModel(new DefaultListModel());
		scrollPerBatch_Recom.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblRecomCancel.getRowCount())));

	}
	
	private void clearPerAcct(){
		lookupClientName.setValue(null);
		txtClientName.setText("");
		txtPBLID.setText("");
		txtPBLDesc.setText("");
		txtStageID.setText("");
		txtStageDesc.setText("");
		txtMonthPD.setText("");
		txtDefaultDate.setText("");
		cmbCancelType_Per.setSelectedIndex(0);
		txtAmountDue.setText("");
		lookupReason.setValue(null);
		txtReason_.setText("");
		txtareaRemarks.setText("");
	}


	private void Edit_Process(String batch_no){

		pgSelect db = new pgSelect();

		db.select("SELECT proj_id, get_project_name(proj_id), approved_by,get_client_name_emp_id(approved_by) FROM rf_cancellation where batch_no = '"+batch_no+"' and status_id = 'A' and posted_by is null ");

		if (db.isNotNull()) {
			proj_id = (String) db.Result[0][0];
			proj_name = (String) db.Result[0][1];
			lookupProjID.setValue(proj_id);
			txtProjectName.setText(proj_name);

			approved_id = (String) db.Result[0][2];
			approved_name =(String) db.Result[0][3];
			lookupApprovedBy.setValue(approved_id);
			txtApprovedBy.setText(approved_name);
		}

		new Thread(new Generate_Edit()).start();
		tblRecomCancel.packAll();


		if (!(UserInfo.Rank_Desc.contains("Head") || UserInfo.ADMIN || UserInfo.EmployeeCode.equals("900421") || UserInfo.EmployeeCode.equals("900414")|| UserInfo.EmployeeCode.equals("900767")|| UserInfo.EmployeeCode.equals("900864") || UserInfo.EmployeeCode.equals("900298") 
				|| UserInfo.EmployeeCode.equals("987120") || UserInfo.EmployeeCode.equals("900851") || UserInfo.EmployeeCode.equals("900791"))) {
			btnState(false,false, true, false, false, true, true); 	
		}else{
			btnState(false,false, true, false, true, true, true);	

		}

		tabPaneAccounts.setEnabledAt(1, false);
	}


	public class Generate_Edit implements Runnable{

		@Override
		public void run() {
			FncGlobal.startProgress("Please wait. . . ");

			rowHeaderRecom.setModel(new DefaultListModel());
			functions.displayEditPerBatchRecommended_new(modelRecommendCancel, rowHeaderRecom,batch_no);
			scrollPerBatch_Recom.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblRecomCancel.getRowCount())));
			tblRecomCancel.packAll();

			/*rowHeadePerBatch.setModel(new DefaultListModel());
			functions.generatePerBatchRecommended_edit(modelPerBatchModel, rowHeadePerBatch, proj_id, dteDueDate.getDate(), cmbBuyerType.getSelectedIndex(),batch_no,rbtnDuetoDosc.isSelected(),rbtnDueToPayment.isSelected(),rbtnAll.isSelected());
			scrollPerBatch.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPerBatch.getRowCount())));
			 */
			tblPerBatch.packAll();

			FncGlobal.stopProgress();
		}
	}


	private void MoveForRecommendCancel( ){

		if (tblPerBatch.isColumnSelected(0)) {

			int selectedIndex =  tblPerBatch.convertRowIndexToModel(tblPerBatch.getSelectedRow());

			Boolean isSelected = (Boolean) modelPerBatchModel.getValueAt(selectedIndex, 0);

			String  Unit = (String) modelPerBatchModel.getValueAt(selectedIndex, 1);
			String  ClientName = (String) modelPerBatchModel.getValueAt(selectedIndex, 2);
			String  BuyerType = (String) modelPerBatchModel.getValueAt(selectedIndex, 3);
			String  CurrentStatus = (String) modelPerBatchModel.getValueAt(selectedIndex, 4);
			Integer MonthsPD = (Integer) modelPerBatchModel.getValueAt(selectedIndex, 17);
			String  PaymentStage = (String) modelPerBatchModel.getValueAt(selectedIndex, 18);

			String entityID =  (String) modelPerBatchModel.getValueAt(selectedIndex, 22);
			String projID =  (String) modelPerBatchModel.getValueAt(selectedIndex, 23);
			String pblID =  (String) modelPerBatchModel.getValueAt(selectedIndex, 24).toString().trim();
			Integer seqNo =  (Integer) modelPerBatchModel.getValueAt(selectedIndex,25);
			String partID =  (String) modelPerBatchModel.getValueAt(selectedIndex,27);


			System.out.println("******************" + entityID +","+projID+","+pblID+"," +seqNo);
			if (isSelected){

				for (int i = 0; i < modelRecommendCancel.getRowCount(); i++) {
					String entityID_ =  (String) modelRecommendCancel.getValueAt(i, 7);
					String projID_ =  (String) modelRecommendCancel.getValueAt(i, 8);
					String pblID_ =  (String) modelRecommendCancel.getValueAt(i, 9).toString().trim();
					Integer seqNo_ =  (Integer) modelRecommendCancel.getValueAt(i,10);



					if (entityID.equals(entityID_) &&  projID.equals(projID_) && pblID.equals(pblID_) && seqNo.equals(seqNo_)) {

						modelRecommendCancel.removeRow(i);
					}
				}

				modelRecommendCancel.addRow(new Object[]{true,Unit,ClientName,BuyerType,CurrentStatus,MonthsPD,PaymentStage,entityID,projID,pblID,seqNo,partID,"","",""});

			}else{

				for(int i=0; i< modelRecommendCancel.getRowCount(); i++){
					String entityID_ =  (String) modelRecommendCancel.getValueAt(i, 7);
					String projID_ =  (String) modelRecommendCancel.getValueAt(i, 8);
					String pblID_ =  (String) modelRecommendCancel.getValueAt(i, 9).toString().trim();
					Integer seqNo_ =  (Integer) modelRecommendCancel.getValueAt(i,10);
					if(entityID.equals(entityID_) &&  projID.equals(projID_) && pblID.equals(pblID_) && seqNo.equals(seqNo_)){
						modelRecommendCancel.removeRow(i);

						for(int y= 1; y <= modelRecommendCancel.getRowCount(); y++){
							((DefaultListModel) rowHeaderRecom.getModel()).addElement(y);
						}
						scrollPerBatch_Recom.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblRecomCancel.getRowCount())));
					}
				}//else
			}
		}
	}

	private void preview_batchAccount(){

		List<TD_Projected_Cancellation> list = new ArrayList<TD_Projected_Cancellation>();

		pgSelect dbs = new pgSelect();

		String SQL = "";
		for (int i = 0; i < modelRecommendCancel.getRowCount(); i++) {
			
			Integer mpd = (Integer) modelRecommendCancel.getValueAt(i, 5);
			String entityID =  (String) modelRecommendCancel.getValueAt(i, 7);
			String projID =  (String) modelRecommendCancel.getValueAt(i, 8);
			String pblID =  (String) modelRecommendCancel.getValueAt(i, 9).toString().trim();
			Integer seqNo =  (Integer) modelRecommendCancel.getValueAt(i,10);

			String unitPBL = (String) modelRecommendCancel.getValueAt(i, 1);

			String clientName  = (String) modelRecommendCancel.getValueAt(i, 2);
			String buyerType = (String) modelRecommendCancel.getValueAt(i, 3);
			String reason  = (String) modelRecommendCancel.getValueAt(i, 13);

			/*if (!(lookupBatchNo.getValue().equals("") || lookupBatchNo.getValue() == null) || lookupBatchNo.getValue().equals("Search Batch No") ) {
				SQL  = "select _get_house_model_alias(pbl_id),\n" + 
						"_get_tr_date(entity_id,proj_id,pbl_id,seq_no),\n" + 
						"_get_or_date(entity_id,proj_id,pbl_id,seq_no),\n" + 
						"default_date,\n" + 
						"get_payment_stage(entity_id,proj_id,pbl_id,seq_no),\n" + 
						"_get_sales_div(entity_id,pbl_id,seq_no),\n" + 
						"Case when _get_or_date(entity_id,proj_id,pbl_id,seq_no) is null then 'TR' else 'OR' end as class_type, \n" + 
						"_get_sales_agent(entity_id,proj_id,pbl_id) as sales_agent,\n" + 
						"(CASE WHEN _get_with_ntc(proj_id,pbl_id) ISNULL THEN 'No' ELSE 'Yes' END ) as withNTC,\n" + 
						"_get_project_phase(proj_id,pbl_id) as phase,\n" +
						"_get_cts_date(entity_id,proj_id,pbl_id,seq_no),\n" + 
						" get_nsp(entity_id,pbl_id,seq_no,proj_id)::numeric,\n" + 
						"(select remdesc from mf_remarks where remark_id = reason)\n" +
						"\n" + 
						"from rf_cancellation where batch_no = '"+batch_no+"' and status_id = 'A' and posted_by is null ";
			}*/


			if (grpButton.getSelection().getActionCommand().equals("New")) {

				System.out.println("***************NEW");
				SQL  = "select _get_house_model_alias('"+pblID+"'),\n" + 
						"_get_tr_date('"+entityID+"','"+projID+"','"+pblID+"',"+seqNo+"),\n" + 
						"_get_or_date('"+entityID+"','"+projID+"','"+pblID+"',"+seqNo+"),\n" + 
						"_get_default_date('"+entityID+"','"+projID+"',TRIM('"+pblID+"'),"+seqNo+",'"+dteDueDate.getDate()+"')as default_date,\n" + 
						"get_payment_stage('"+entityID+"','"+projID+"','"+pblID+"',"+seqNo+"),\n" + 
						"_get_sales_div('"+entityID+"','"+pblID+"',"+seqNo+"),\n" + 
						"Case when _get_or_date('"+entityID+"','"+projID+"','"+pblID+"',"+seqNo+") is null then 'TR' else 'OR' end as class_type, \n" + 
						"get_lot_area('"+entityID+"','"+projID+"','"+pblID+"',"+seqNo+") as sales_agent,\n" + 
						"(CASE WHEN _get_with_ntc('"+projID+"','"+pblID+"') ISNULL THEN 'No' ELSE 'Yes' END ) as withNTC,\n" + 
						"_get_project_phase('"+projID+"','"+pblID+"') as phase,\n" + 
						"_get_cts_date('"+entityID+"','"+projID+"','"+pblID+"',"+seqNo+"),\n" + 
						" get_nsp('"+entityID+"','"+pblID+"',"+seqNo+",'"+projID+"')::numeric\n" +  
						"ORDER BY _get_sales_div('"+entityID+"','"+pblID+"',"+seqNo+") \n" +  
						"\n" + 
						"";
				dbs.select(SQL);
				houseModel = (String) dbs.Result[0][0];
				trDate = (Timestamp) dbs.Result[0][1];
				orDate = (Timestamp) dbs.Result[0][2];
				defaultDate = (Timestamp) dbs.Result[0][3];
				stage = (String) dbs.Result[0][4];
				saleDiv = (String) dbs.Result[0][5];
				class_type = (String) dbs.Result[0][6];
				saleAgent = (String) dbs.Result[0][7];
				withNTC = (String) dbs.Result[0][8];

				phase =  (String)dbs.Result[0][9];
				CtsNotarize = (Timestamp)dbs.Result[0][10];
				grossTCP = (BigDecimal) dbs.Result[0][11];

				System.out.printf("Display Sales Division dito sa New: %s%n", saleDiv);

			}

			if (grpButton.getSelection().getActionCommand().equals("Save") || grpButton.getSelection().getActionCommand().equals("Edit") || (!(lookupBatchNo.getValue().equals("") || lookupBatchNo.getValue() == null) || lookupBatchNo.getValue().equals("Search Batch No")) ) {

				System.out.println("***************SAVE AND EDIT");
				SQL  = "select _get_house_model_alias_v2(proj_id, pbl_id),\n" + 
						"_get_tr_date(entity_id,proj_id,pbl_id,seq_no),\n" + 
						"_get_or_date(entity_id,proj_id,pbl_id,seq_no),\n" + 
						"default_date,\n" + 
						"get_payment_stage(entity_id,proj_id,pbl_id,seq_no),\n" + 
						"_get_sales_div(entity_id,pbl_id,seq_no),\n" + 
						"Case when _get_or_date(entity_id,proj_id,pbl_id,seq_no) is null then 'TR' else 'OR' end as class_type, \n" + 
						"get_lot_area(entity_id,proj_id,pbl_id,seq_no) as sales_agent,\n" + 
						"(CASE WHEN _get_with_ntc(proj_id,pbl_id) ISNULL THEN 'No' ELSE 'Yes' END ) as withNTC,\n" + 
						"_get_project_phase(proj_id,pbl_id) as phase,\n"+
						"_get_cts_date(entity_id,proj_id,pbl_id,seq_no),\n" + 
						" get_nsp(entity_id,pbl_id,seq_no,proj_id)::numeric,\n" + 
						"(select remdesc from mf_remarks where remark_id = reason)\n" +
						"from rf_cancellation where batch_no = '"+batch_no+"' and status_id = 'A' and posted_by is null order by _get_sales_div(entity_id,pbl_id,seq_no) ";

				System.out.println("***************" + SQL);
				dbs.select(SQL);


				for (int j = 0; j < dbs.getRowCount(); j++) {
					if (dbs.isNotNull()) {
						houseModel = (String) dbs.Result[i][0];
						trDate = (Timestamp) dbs.Result[i][1];
						orDate = (Timestamp) dbs.Result[i][2];
						defaultDate = (Timestamp) dbs.Result[i][3];
						stage = (String) dbs.Result[i][4];
						saleDiv = (String) dbs.Result[i][5];
						class_type = (String) dbs.Result[i][6];
						saleAgent = (String) dbs.Result[i][7];
						withNTC = (String) dbs.Result[i][8];

						phase =  (String)dbs.Result[i][9];
						CtsNotarize = (Timestamp)dbs.Result[i][10];
						grossTCP = (BigDecimal) dbs.Result[i][11];
						// reason = (String)dbs.Result[i][12];

					}
				}

				System.out.printf("Display value of Sales Division dito sa save saka edit: %s%n", saleDiv);
			}

			/*
			pgSelect db = new pgSelect();


			db.select("select (select remdesc from mf_remarks where remark_id = reason) from rf_cancellation where batch_no = '"+lookupBatchNo.getValue()+"' ");

			String reason = "";			
			for (int x = 0; x < db.getRowCount(); x++) {
				if (db.isNotNull()) {
					reason = (String) db.Result[i][0];
				}
			}*/


			list.add(new TD_Projected_Cancellation(unitPBL, clientName, buyerType, houseModel, trDate, orDate, defaultDate, stage, saleDiv, class_type, saleAgent, withNTC, CtsNotarize, reason, grossTCP, phase, mpd));
		}


		/*for (int i = 0; i < modelPerBatchModel.getRowCount(); i++) {
			Boolean isSelected = (Boolean) modelPerBatchModel.getValueAt(i, 0);

			if (isSelected) {

				String entityID =  (String) modelPerBatchModel.getValueAt(i, 22);
				String projID =  (String) modelPerBatchModel.getValueAt(i, 23);
				String pblID =  (String) modelPerBatchModel.getValueAt(i, 24).toString().trim();
				Integer seqNo =  (Integer) modelPerBatchModel.getValueAt(i,25);

				String unitPBL = (String) modelPerBatchModel.getValueAt(i, 1);
				String clientName  = (String) modelPerBatchModel.getValueAt(i, 2);
				String buyerType = (String) modelPerBatchModel.getValueAt(i, 3);
				String houseModel = (String) modelPerBatchModel.getValueAt(i, 7);
				Timestamp trDate = (Timestamp) modelPerBatchModel.getValueAt(i, 13);
				Timestamp orDate = (Timestamp) modelPerBatchModel.getValueAt(i, 14);
				Timestamp defaultDate = (Timestamp) modelPerBatchModel.getValueAt(i, 15);
				String stage = modelPerBatchModel.getValueAt(i, 18) == null ? "" :  modelPerBatchModel.getValueAt(i, 18).toString().replaceAll("\\d","");
				String saleDiv = (String) modelPerBatchModel.getValueAt(i, 5);
				String class_type = (String) (orDate == null ? "TR" : "OR");
				String saleAgent = (String) modelPerBatchModel.getValueAt(i, 19);
				String withNTC = (String) modelPerBatchModel.getValueAt(i, 20);

				pgSelect db = new pgSelect();
				db.select("SELECT _get_cts_date('"+entityID+"', '"+projID+"','"+pblID+"',"+seqNo+"), get_nsp('"+entityID+"', '"+pblID+"',"+seqNo+",'"+projID+"')::numeric");


				Timestamp CtsNotarize = (Timestamp)db.Result[0][0];
				BigDecimal grossTCP = (BigDecimal) db.Result[0][1];

				System.out.println("********************DUMAAN b " + db.Result[0][0] + db.Result[0][1]);
				db.select("select (select remdesc from mf_remarks where remark_id = reason) from rf_cancellation where batch_no = '"+lookupBatchNo.getValue()+"' ");

				String reason = "";					
				if (db.isNotNull()) {
					reason = (String) db.Result[0][0];
				}

				String phase =  (String) modelPerBatchModel.getValueAt(i,28);

				list.add(new TD_Projected_Cancellation(unitPBL, clientName, buyerType, houseModel, trDate, orDate, defaultDate, stage, saleDiv, class_type, saleAgent, withNTC, CtsNotarize, reason, grossTCP, phase));

			}
		}*/

		int count_tr = 0;
		int count_or = 0;
		int total_or_tr = 0;

		BigDecimal total_tr = new BigDecimal("0.00");
		BigDecimal total_or = new BigDecimal("0.00");
		BigDecimal subtotal_or_tr = new BigDecimal("0.00");

		for (int i = 0; i < list.size(); i++) {
			System.out.println("LIST**********************************" +list.get(i).getClientName()+" "+list.get(i).getClass_type()+" "+ list.get(i).getOrDate());

			if (list.get(i).getClass_type().equals("TR")) {
				BigDecimal totalTR = (BigDecimal) list.get(i).getGrossTCP();

				try {
					total_tr = total_tr.add(totalTR);

				} catch (Exception e1) { }
				count_tr++;
			}

			if (list.get(i).getClass_type().equals("OR")) {

				BigDecimal totalOR = (BigDecimal)  list.get(i).getGrossTCP();

				try {
					total_or = total_or.add(totalOR);

				} catch (Exception e1) { }
				count_or++;
			}	
		}

		total_or_tr = count_or + count_tr;
		subtotal_or_tr = subtotal_or_tr.add(total_tr) ;
		subtotal_or_tr = subtotal_or_tr.add(total_or) ;

		String co_id = (String)	lookupCompany.getValue();
		String company = (String) txtCompany.getText();
		String company_logo = (String) getLogo(); 
		
		String preparedby = (String) UserInfo.FullName;
		String approvedby = (String) txtApprovedBy.getText();
		String lblListof = "Recommended for Cancellation";

		Collections.sort(list, new Comparator<TD_Projected_Cancellation>() {

			@Override
			public int compare(TD_Projected_Cancellation o1, TD_Projected_Cancellation o2) {
				return  o1.getSaleDiv().compareTo(o2.getSaleDiv());
			}
		});

		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("company", company);
		mapParameters.put("co_id", co_id);
		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
		mapParameters.put("lblListof", lblListof);
		if (!(lookupBatchNo.getValue().equals("") || lookupBatchNo.getValue() == null ) || lookupBatchNo.getValue().equals("Search Batch No")) {
			mapParameters.put("batchno", lookupBatchNo.getValue());
		}
		if (grpButton.getSelection().getActionCommand().equals("New")) {
			mapParameters.put("batchno", "");
		}
		if (grpButton.getSelection().getActionCommand().equals("Save")) {
			mapParameters.put("batchno", batch_no);
		}


		mapParameters.put("proj_id", lookupProjID.getValue());
		mapParameters.put("proj_name", txtProjectName.getText());
		//mapParameters.put("SUBREPORT_DIR", this.getClass().getResourceAsStream("/Reports/subrptPreviewCancellation.jasper"));
		mapParameters.put("list",list);

		mapParameters.put("total_sum_tr",total_tr);
		mapParameters.put("count_tr",count_tr);
		mapParameters.put("total_sum_or",total_or);
		mapParameters.put("count_or",count_or);
		mapParameters.put("subtotal_or_tr",subtotal_or_tr);
		mapParameters.put("total_or_tr",total_or_tr);

		mapParameters.put("preparedby",preparedby);
		mapParameters.put("approvedby",approvedby);

		FncReport.generateReport("/Reports/rptPreviewCancellation.jasper", "List of Recommended Cancellation", mapParameters);
	}


	private void preview_perAccount(){
		List<TD_Projected_Cancellation> list = new ArrayList<TD_Projected_Cancellation>();

		String entityID =  entity_id;
		String projID =  proj_id;
		String pblID =  pbl_id;
		Integer seqNo =  seq_no;

		String unitPBL = pbl_desc;
		String clientName  = client_name;

		pgSelect db = new pgSelect();

		db.select("SELECT get_buyer_type_alias('"+entity_id+"', '"+projID+"', TRIM('"+pblID+"'), "+seqNo+" ),"
				+ "_get_house_model_alias(TRIM('"+pblID+"')),"
				+ "_get_tr_date('"+entity_id+"', '"+projID+"', TRIM('"+pblID+"'), "+seqNo+"),"
				+ "_get_or_date('"+entity_id+"', '"+projID+"', TRIM('"+pblID+"'), "+seqNo+"),"
				+ "_get_sales_div('"+entity_id+"', TRIM('"+pblID+"'), "+seqNo+"),"
				+ "get_lot_area('"+entity_id+"', '"+projID+"', TRIM('"+pblID+"'),"+seqNo+"),"
				+ "(CASE WHEN _get_with_ntc('"+projID+"',TRIM('"+pblID+"')) ISNULL THEN 'No' ELSE 'Yes' END),"
				+ "_get_cts_date('"+entityID+"', '"+projID+"','"+pblID+"',"+seqNo+"), "
				+ "get_nsp('"+entityID+"', '"+pblID+"',"+seqNo+",'"+projID+"')::numeric,"
				+ "_get_project_phase('"+projID+"',TRIM('"+pblID+"'))");


		if (db.isNotNull()) {

			String buyerType = (String) db.Result[0][0];
			String houseModel = (String)db.Result[0][1];
			Timestamp trDate = (Timestamp) db.Result[0][2];
			Timestamp orDate = (Timestamp) db.Result[0][3];
			Timestamp defaultDate = (Timestamp) default_date;
			String stage = payment_stage.replaceAll("\\d","");
			String saleDiv = (String) db.Result[0][4];
			String class_type = (String) (orDate == null ? "TR" : "OR");
			String saleAgent = (String) db.Result[0][5];
			String withNTC = (String) db.Result[0][6];
			Timestamp CtsNotarize = (Timestamp)db.Result[0][7];
			BigDecimal grossTCP = (BigDecimal) db.Result[0][8];
			String phase = (String) db.Result[0][9];

			list.add(new TD_Projected_Cancellation(unitPBL, clientName, buyerType, houseModel, trDate, orDate, defaultDate, stage, saleDiv, class_type, saleAgent, withNTC, CtsNotarize, reason_name, grossTCP, phase, null));	
		}

		int count_tr = 0;
		int count_or = 0;
		int total_or_tr = 0;

		BigDecimal total_tr = new BigDecimal("0.00");
		BigDecimal total_or = new BigDecimal("0.00");
		BigDecimal subtotal_or_tr = new BigDecimal("0.00");

		for (int i = 0; i < list.size(); i++) {
			System.out.println("LIST**********************************" +list.get(i).getClientName()+" "+list.get(i).getClass_type()+" "+ list.get(i).getOrDate());

			if (list.get(i).getClass_type().equals("TR")) {
				BigDecimal totalTR = (BigDecimal) list.get(i).getGrossTCP();

				try {
					total_tr = total_tr.add(totalTR);

				} catch (Exception e1) { }
				count_tr++;
			}

			if (list.get(i).getClass_type().equals("OR")) {

				BigDecimal totalOR = (BigDecimal)  list.get(i).getGrossTCP();

				try {
					total_or = total_or.add(totalOR);

				} catch (Exception e1) { }
				count_or++;
			}	
		}

		total_or_tr = count_or + count_tr;
		subtotal_or_tr = subtotal_or_tr.add(total_tr) ;
		subtotal_or_tr = subtotal_or_tr.add(total_or) ;


		String preparedby = (String) UserInfo.FullName;
		String approvedby = (String) txtApprovedBy.getText();
		String lblListof = "Recommended for Cancellation";

		Collections.sort(list, new Comparator<TD_Projected_Cancellation>() {

			@Override
			public int compare(TD_Projected_Cancellation o1, TD_Projected_Cancellation o2) {
				return  o1.getPhase().compareTo(o2.getPhase());
			}
		});

		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("company", company);
		mapParameters.put("co_id", co_id);
		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
		mapParameters.put("lblListof", lblListof);
		mapParameters.put("batchno", batch_no);
		mapParameters.put("proj_id", lookupProjID.getValue());
		mapParameters.put("proj_name", txtProjectName.getText());
		//mapParameters.put("SUBREPORT_DIR", this.getClass().getResourceAsStream("/Reports/subrptPreviewCancellation.jasper"));
		mapParameters.put("list",list);

		mapParameters.put("total_sum_tr",total_tr);
		mapParameters.put("count_tr",count_tr);
		mapParameters.put("total_sum_or",total_or);
		mapParameters.put("count_or",count_or);
		mapParameters.put("subtotal_or_tr",subtotal_or_tr);
		mapParameters.put("total_or_tr",total_or_tr);

		mapParameters.put("preparedby",preparedby);
		mapParameters.put("approvedby",approvedby);


		FncReport.generateReport("/Reports/rptPreviewCancellation.jasper", "List of Recommended Cancellation", mapParameters);

	}

	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}

	private void PrintBatchReport(String noticeBatchno){

		pgSelect db = new pgSelect();
		ArrayList<String> listReport = new ArrayList<String>(); 
		ArrayList<String> listTitle = new ArrayList<String>();

		ArrayList<Map> listParameters = new ArrayList<Map>();

		String entity_id = "";
		String proj_id = "";
		String pbl_id = "";
		Integer seq_no = null;
		String notice_id ;
		String report_name ;
		String title_name;
		Boolean underMacedaLaw;

		/** for cancellations due to non-payment**/

		int OK = JOptionPane.showConfirmDialog(getContentPane(),"\nSelected Account(s) Posted on the Cancellation Table \n" +
				"\n Click OK to Display Notice","Successful",
				JOptionPane.OK_CANCEL_OPTION);
		if (OK == JOptionPane.OK_OPTION){


			String SQL = "";

			SQL = "SELECT\n" + 
					" b.notice_id, \n" + 
					"(SELECT report_name from mf_notice_type where notice_id = b.notice_id) as report_name,\n" + 
					"a.entity_id,\n" + 
					"a.proj_id,\n" + 
					"a.pbl_id,\n" + 
					"a.seq_no,\n" + 
					"a.under_maceda_law,\n" + 
					"(SELECT notice_desc FROM mf_notice_type WHERE notice_id = b.notice_id )\n" + 
					"FROM rf_cancellation a \n" + 
					"LEFT JOIN rf_client_notices b ON a.entity_id = b.entity_id and a.proj_id = b.projcode and a.pbl_id = b.pbl_id  and a.seq_no = a.seq_no \n" + 
					"WHERE b.batch_no ='"+noticeBatchno+"' \n" +
					//"AND reason <> '00232' \n" +
					"--AND dateprep >= CURRENT_DATE ;";

			System.out.println("******222**************" + SQL);
			db.select(SQL);
			if (db.isNotNull()) {

				for (int i = 0; i < db.getRowCount(); i++) {

					underMacedaLaw = (Boolean) db.Result[i][6];
					entity_id = (String) db.Result[i][2];
					proj_id = (String) db.Result[i][3];
					pbl_id = (String) db.Result[i][4];
					seq_no = (Integer) db.Result[i][5];

					notice_id = (String) db.Result[i][0];
					report_name = (String) db.Result[i][1];
					title_name = (String) db.Result[i][7];

					Map<String, Object> mapParameters = new HashMap<String, Object>();
					mapParameters.put("batch_no", noticeBatchno);
					mapParameters.put("company", company);

					if (underMacedaLaw) {

						mapParameters.put("entity_id", entity_id);
						mapParameters.put("proj_id", proj_id);
						mapParameters.put("pbl_id", pbl_id);
						mapParameters.put("seq_no", seq_no);

						listReport.add(String.format("/Reports/%s.jasper", report_name));
						listTitle.add(String.format("%s ", title_name));
						listParameters.add(mapParameters);

					}else{

						mapParameters.put("entity_id", entity_id);
						mapParameters.put("proj_id", proj_id);
						mapParameters.put("pbl_id", pbl_id);
						mapParameters.put("seq_no", seq_no);
						mapParameters.put("prepared_by", UserInfo.Alias.toUpperCase());

						listReport.add(String.format("/Reports/%s.jasper", report_name));
						listTitle.add(String.format("%s ", title_name));
						listParameters.add(mapParameters);
					}


					if (notice_id.equals("58")) {
						mapParameters.put("proj_id", proj_id);
						mapParameters.put("pbl_id", pbl_id);
						mapParameters.put("seq_no", seq_no);
						mapParameters.put("prepared_by", UserInfo.Alias.toUpperCase());

						listReport.add(String.format("/Reports/%s.jasper", report_name));
						listTitle.add(String.format("%s ", title_name));
						listParameters.add(mapParameters);
					}
				}



				Map<String, Object> mapParameters = new HashMap<String, Object>();
				mapParameters.put("batch_no", noticeBatchno);
				FncReport.generateReport(String.format("/Reports/%s.jasper", "subrptTransmittalForPostOffice"), "Transmittal For Post Office", mapParameters);	

				TreeSet<String> uniqueReport = new TreeSet<String>(listReport);
				TreeSet<String> uniqueTitle = new TreeSet<String>(listTitle);

				FncReport.generateReport(uniqueReport.toArray(), uniqueTitle.toArray(), listParameters.toArray(), "Notice to Cancellation");

			}
		}
	}


	public class ForPreviewStatus implements Runnable{

		@Override
		public void run() {
			Map<String, Object> mapParameters = new HashMap<String, Object>();
			mapParameters.put("company", company);
			mapParameters.put("co_id", co_id);
			mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
			//mapParameters.put("lblListof", lblListof);
			mapParameters.put("dateFrom", dateFrom);
			mapParameters.put("dateTo", dateTo);
			mapParameters.put("printall", printall);
			mapParameters.put("proj_id", lookupProjID.getValue());

			/*	if (tabpane.getSelectedIndex() == 0) {
				if (cmbProcessFor.getSelectedIndex() == 0) {
					FncReport.generateReport("/Reports/rptCancellation_Active.jasper", "",  mapParameters,functions.printSQL);	
				}
			}*/


			FncReport.generateReport("/Reports/rptCancellationStatus.jasper", "Status of Canceled Accounts",  company, mapParameters);	
		}
	}
	public boolean checkRequiredFields(){//CHECKS THE REQUIRED FIELD BEFORE SAVING
		String required_fields = "";
		Boolean toSave = true;

		for (int i = 0; i < modelRecommendCancel.getRowCount(); i++) {
			String canceltype = (String) modelRecommendCancel.getValueAt(i, 12);
			String reason = (String) modelRecommendCancel.getValueAt(i, 13);


			if (canceltype.equals("") || canceltype == null) {
				required_fields = required_fields + "Cancellation Type \n";
				toSave = false;
			}

			if (reason.equals("") || reason == null) {
				required_fields = required_fields + "Reason \n";
				toSave = false;
			}
		}



		if(toSave == false){
			JOptionPane.showMessageDialog(null, "Please fill up all required fields:\n"+required_fields,"Save",JOptionPane.WARNING_MESSAGE);
			return false;
		}
		return true;
	}

	public String dateFormat_day(Date date){
		String strdate = null;
		DateFormat df = new SimpleDateFormat("dd");
		if (!(date == null)) {
			strdate = df.format(date); 
		}

		return strdate;
	}
	
	private String getLogo(){
		pgSelect db = new pgSelect();
		
		String co_id = (String) lookupCompany.getValue(); 
		String company_logo = "";
		
		String sql = "Select company_logo from mf_company where co_id = '"+co_id+"'";
		db.select(sql);
		//FncSystem.out("Display generation of reference no", sql);
		
		if(db.isNotNull()){
			company_logo = (String) db.getResult()[0][0];
		}
		return company_logo;
	}

	private Boolean hasSelected() {
		ArrayList<Boolean> listSelected = new ArrayList<Boolean>();
		for(int x=0; x < modelRecommendCancel.getRowCount(); x++){
			listSelected.add((Boolean) modelRecommendCancel.getValueAt(x, 0));
		}
		return listSelected.contains(true);
	}


	private  Boolean ifSoldToBank(String entityID, String projID, String pblID,Integer seqNo){

		pgSelect db = new pgSelect();
		Boolean status = false;

		db.select("select * from rf_buyer_status a \n" + 
				"where TRIM(a.byrstatus_id) = '25' \n" + 
				"and trim(a.entity_id) = '"+entityID+"'\n" + 
				"and TRIM(a.proj_id) = '"+projID+"'\n" + 
				"and TRIM(a.pbl_id) = '"+pblID+"'\n" + 
				"and seq_no = '"+seqNo+"'\n" + 
				"and TRIM(a.status_id) = 'A' \n"+
				"AND NOT EXISTS (SELECT * \n"+
				" 				 FROM rf_buyer_status \n"+
				"				 where TRIM(entity_id) = a.entity_id \n"+
				"				 AND TRIM(proj_id) = a.proj_id \n"+
				"				 AND TRIM(pbl_id) = a.pbl_id \n"+
				"				 and seq_no = a.seq_no \n"+
				"				 and trim(byrstatus_id) = '26' \n"+
				"				 and status_id = 'A')");

		if (db.isNotNull()) {
			status = true;
		}

		return status;
	}


	public void toRemoveListSelected(){

		for (int i = 0; i < modelRecommendCancel.getRowCount(); i++) {

			Boolean isSelected = (Boolean) modelRecommendCancel.getValueAt(i, 0);

			if (!isSelected) {



				String entity_id_r = (String) modelRecommendCancel.getValueAt(i, 7);
				String proj_id_r = (String) modelRecommendCancel.getValueAt(i, 8);
				String pbl_id_r = (String) modelRecommendCancel.getValueAt(i, 9);
				Integer seq_no_r = (Integer) modelRecommendCancel.getValueAt(i, 10);
				System.out.println("******REMOVE" +entity_id_r);
				modelRecommendCancel.removeRow(i);
				for(int y= 1; y <= modelRecommendCancel.getRowCount(); y++){
					((DefaultListModel) rowHeaderRecom.getModel()).addElement(y);
				}
				scrollPerBatch_Recom.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblRecomCancel.getRowCount())));


				for (int j = 0; j < modelPerBatchModel.getRowCount(); j++) { 
					Boolean isSelectedList = (Boolean) modelPerBatchModel.getValueAt(j, 0);		

					String entity_id = (String) modelPerBatchModel.getValueAt(j, 22);
					String proj_id = (String) modelPerBatchModel.getValueAt(j, 23);
					String pbl_id = (String) modelPerBatchModel.getValueAt(j, 24);
					Integer seq_no = (Integer) modelPerBatchModel.getValueAt(j, 25);

					//System.out.println("******" + entity_id_r +"" + entity_id);
					if (isSelectedList) {
						System.out.println("****** TO UNSELECT" + entity_id_r +" -- " + entity_id);
						if(entity_id_r.equals(entity_id) && proj_id_r.equals(proj_id) && pbl_id_r.equals(pbl_id) && seq_no_r.equals(seq_no)){
							System.out.println("****** TO UNSELECT@@@  " + entity_id_r +" -- " + entity_id);	
							modelPerBatchModel.setValueAt(false, j, 0);

						}

					}

				}
			}


		}
	}
}
