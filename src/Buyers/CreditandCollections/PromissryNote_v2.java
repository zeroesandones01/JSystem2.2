package Buyers.CreditandCollections;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.Timer;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableColumn;

import org.jdesktop.swingx.JXFormattedTextField;
import org.jdesktop.swingx.table.NumberEditorExt;

import com.toedter.calendar.JTextFieldDateEditor;

import Buyers.ClientServicing.ClientInformation;
import Database.pgSelect;
import Database.pgUpdate;
import DateChooser._JDateChooser;
import Dialogs.dlgDate;
import FormattedTextField._JXFormattedTextField;
import Functions.FncGlobal;
import Functions.FncReport;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components._JButton;
import components._JInternalFrame;
import components._JScrollPaneMain;
import components._JScrollPaneTotal;
import components._JTabbedPane;
import components._JTableMain;
import components._JTableTotal;
import interfaces._GUI;
import tablemodel.model_PN_Commit;
import tablemodel.model_PN_History;

public class PromissryNote_v2 extends _JInternalFrame implements _GUI, ActionListener, LookupListener{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static String title = "Promissory Note / Commitment";
	public static Dimension frameSize = new Dimension(920, 550);
	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);

	private _FPromissoryNoteCommintment method = new _FPromissoryNoteCommintment();
	private JPanel pnlMain;
	private JPanel pnlNorth;
	private JLabel lblCompany;
	private _JLookup lookupCompany;
	private JTextField txtCompany;
	private JLabel lblClientID;
	private _JLookup lookUpClientID;
	private JTextField txtClientName;
	private JLabel lblPNno;
	private _JLookup lookUpPNno;
	private _JTabbedPane tabCenter;
	private JPanel pnlTabDetails;
	private JPanel pnlLeft;
	private JLabel lblProject;
	private JLabel lblPBL;
	private JLabel lblTelNo;
	private JLabel lblMobile;
	private JPanel pnlRight;
	private JTextField txtProjID;
	private JTextField txtProjDesc;
	private JTextField txtPBLID;
	private JTextField txtPBLDesc;
	private JTextField txtTelNo;
	private JTextField txtMobile;
	private JPanel pnlPastDue;
	private JLabel lblMoUpdate;
	private JLabel lblNoDaysPD;
	private JLabel lblDPMA;
	private JTextField txtMoUpdate;
	private JTextField txtNoDays;
	private JTextField txtDPMA;
	private JLabel lblBlank;
	private JLabel lblBlank_;
	private JPanel pnlPastDue_Amt;
	private JLabel lblAmountDue;
	private JLabel lblLastDatePaid;
	private JLabel lblDefaultDate;
	private JLabel lblBlankAmt;
	private _JXFormattedTextField txtAmountDue;
	private JTextField txtLastDatePaid;
	private JTextField txtDefaultDate;
	private JLabel lblBlankAmt_;
	private JPanel pnlTabPnHistory;
	private JPanel pnlCenter;
	private JPanel pnlPNPaymentPlan;
	private JPanel pnlCommitment;
	private JPanel pnlSouth;
	private _JButton btnViewInfo;
	private _JButton btnPrintForm;
	private _JButton btnPreview;
	private _JButton btnAdd;
	private ButtonGroup grpButton = new ButtonGroup();
	private _JButton btnEdit;
	private _JButton btnSave;
	private _JButton btnPost;
	private _JButton btnCancel;
	private JLabel lblAmtToPay;
	private JLabel lblPaymentAmount;
	private JLabel lblPaymentDate;
	private JLabel lblSignedBy;
	private JLabel lblRwClient;
	private JLabel lblContact_No;
	private JLabel lblEmailAdd;
	private JLabel lblRemarks;
	private JLabel lblApproved;
	private _JXFormattedTextField txtAmountPay;
	private _JXFormattedTextField txtPaymentAmt;
	private _JDateChooser dtePaymentDate;
	private JTextField txtSignedby;
	private JTextField txtRwClient;
	private JTextField txtContactNo;
	private JTextField txtEmailAdd;
	private JTextField txtRemarks;
	private _JLookup lookupApproved;
	private JTextField txtApproved;
	private JPanel pnlCenter_Commit;
	private _JDateChooser dteCommitDate;
	private DefaultComboBoxModel cmbMonthUpdateModel;
	private _JScrollPaneMain scrollCommit;
	private model_PN_Commit modelCommit;
	private _JTableMain tblCommit;
	private JComboBox cmbMonthUpdate;
	private JList rowHeaderCommit;
	private _JScrollPaneTotal scrollCommitAmountTotal;
	private model_PN_Commit modelCommitAmountTotal;
	private _JTableTotal tblCommitAmountTotal;
	private JButton btnAddRow;
	private JButton btnRemoveRow;
	private JPanel pnlDetails;
	private JPanel north;
	private String co_id;
	private String company;
	private String company_logo;
	private JCheckBox chkIfPD;
	private String entity_id;
	private String proj_id;
	private String pbl_id;
	private Integer seq_no;
	private String client_name;
	private String unit;
	private String proj_name;
	private String tel_no;
	private String mobile_no;
	private String buyertypeID;
	private Timer timerStatus = null;
	private boolean blinkState = false;
	private ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
	private String payment_stage;
	private BigDecimal amountPay;
	private Timestamp paymentDate;
	private Integer monthsPD;
	private Integer daysPD;
	private BigDecimal amountDue;
	private String lastPaidDate;
	private String defaultdDate;
	private String PnNo;
	private JScrollPane scrollPNHistory;
	private model_PN_History modelPNHistory;
	private _JTableMain tblPNHistory;
	private JList rowHeaderPNH;
	private Timestamp commit_date;
	private BigDecimal commit_amount;
	private String phase;
	private String block;
	private String lot;
	private Boolean printall = false;
	private Date dateFrom;
	private Date dateTo;
	private String remarks;
	private String signedby;
	private String rwC;
	private String rwC_ContactNo;
	private String rwC_Emailadd;
	private BigDecimal payment_amount = new BigDecimal("0.00");

	private Boolean ifViewPnHistory = false;
	public PromissryNote_v2() {
		super(title, true, true, true, true);
		initGUI();
		formLoad();
	}

	public PromissryNote_v2(String title) {
		super(title);
		initGUI();
		formLoad();
	}

	public PromissryNote_v2(String title, boolean resizable, boolean closable, boolean maximizable,
			boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
		formLoad();
	}
	public PromissryNote_v2(String entity_id, String proj_id,String pbl_id ,Integer seq_no,String buyerID) {
		super(title, true, true, true, true);
		initGUI();
		formLoad();
		getDefaultCompany();
		System.out.println("************dddddddddddddddddddddddddddddd" + seq_no);
		getDetails(entity_id, proj_id, pbl_id, seq_no,buyerID );

	}

	@Override
	public void initGUI() {
		this.setTitle(title);
		this.setPrimaryTitle(title);
		this.setSize(frameSize);
		this.setPreferredSize(frameSize);
		getContentPane().setLayout(new BorderLayout());
		this.addInternalFrameListener(new InternalFrameAdapter() {
			public void internalFrameClosing(InternalFrameEvent arg0) {
				if(timerStatus != null){
					timerStatus.stop();
				}
			}

			public void internalFrameClosed(InternalFrameEvent arg0) {
				//System.out.println("internalFrameClosed");
				executor.shutdown();
				if(timerStatus != null){
					timerStatus.stop();
				}
			}
		});

		{

			pnlMain = new JPanel();
			getContentPane().add(pnlMain,BorderLayout.CENTER);
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			pnlMain.setLayout(new BorderLayout(5,5));
			{
				pnlNorth = new JPanel(new BorderLayout(3, 3));
				pnlMain.add(pnlNorth, BorderLayout.NORTH);
				pnlNorth.setPreferredSize(new Dimension(pnlMain.getWidth(), 180));
				{
					north = new JPanel(new BorderLayout(3, 3));
					pnlNorth.add(north, BorderLayout.NORTH);
					north.setPreferredSize(new Dimension(pnlMain.getWidth(), 45));
					//north.setBorder(lineBorder);
					{
						JPanel pnlCompany = new JPanel(new BorderLayout(3, 3));
						north.add(pnlCompany, BorderLayout.NORTH);

						{
							JPanel pnlCompanyID = new JPanel(new GridLayout(1, 1, 3, 3));
							pnlCompany.add(pnlCompanyID, BorderLayout.WEST);
							{
								lblCompany = new JLabel("Company ");
								pnlCompanyID.add(lblCompany);
								lblCompany.setPreferredSize(new Dimension(65, 20));
							}

							JPanel pnlLookUp = new JPanel(new GridLayout(1, 1, 3, 3));
							pnlCompany.add(pnlLookUp,BorderLayout.CENTER);
							{

								JPanel pnlID = new JPanel(new BorderLayout(3,3));
								pnlLookUp.add(pnlID);
								{
									{

										lookupCompany = new _JLookup("", "Company", 0) ; /// XXX lookupCompany 
										pnlID.add(lookupCompany,BorderLayout.WEST);
										lookupCompany.addLookupListener(this);
										lookupCompany.setReturnColumn(0);
										lookupCompany.setLookupSQL(method.getCompany());
										lookupCompany.setPreferredSize(new Dimension(100, 20));
									}
									{
										txtCompany = new JTextField();
										pnlID.add(txtCompany,BorderLayout.CENTER);
										txtCompany.setEditable(false);
										txtCompany.setPreferredSize(new Dimension(100, 20));
									} 

								}
							}
						}

						JPanel pnlClient = new JPanel(new BorderLayout(3, 3));
						north.add(pnlClient, BorderLayout.CENTER);

						{

							JPanel pnlWest = new JPanel(new BorderLayout(3, 3));
							pnlClient.add(pnlWest, BorderLayout.WEST);
							pnlWest.setPreferredSize(new Dimension(500, 20));
							{
								JPanel pnlClientID = new JPanel(new GridLayout(1, 1, 3, 3));
								pnlWest.add(pnlClientID, BorderLayout.WEST);
								{
									lblClientID = new JLabel("Client ID  ");
									pnlClientID.add(lblClientID);
									lblClientID.setPreferredSize(new Dimension(65, 20));
								}

								JPanel pnlCheckBox = new JPanel(new GridLayout(1, 1, 3, 3));
								pnlWest.add(pnlCheckBox,BorderLayout.EAST);
								{
									chkIfPD = new JCheckBox("Past Due", true);
									pnlCheckBox.add(chkIfPD);
									chkIfPD.addActionListener(this);
								}
								JPanel pnlLookUp = new JPanel(new GridLayout(1, 1, 3, 3));
								pnlWest.add(pnlLookUp,BorderLayout.CENTER);
								{
									JPanel pnlID = new JPanel(new BorderLayout(3,3));
									pnlLookUp.add(pnlID);
									{
										{

											lookUpClientID = new _JLookup("", "Client ID", 0) ; /// XXX lookUpClientID 
											pnlID.add(lookUpClientID,BorderLayout.WEST);
											lookUpClientID.addLookupListener(this);
											lookUpClientID.setReturnColumn(0);
											lookUpClientID.setLookupSQL(method.getClient_new(chkIfPD.isSelected(), lookupCompany.getValue()));
											lookUpClientID.setPreferredSize(new Dimension(100, 20));
										}
										{
											txtClientName = new JTextField();
											pnlID.add(txtClientName,BorderLayout.CENTER);
											txtClientName.setEditable(false);
											txtClientName.setPreferredSize(new Dimension(100, 20));
										} 
									}
								}
							}

							JPanel pnlEast = new JPanel(new BorderLayout(3, 3));
							pnlClient.add(pnlEast, BorderLayout.EAST);
							pnlEast.setPreferredSize(new Dimension(200, 20));
							{
								JPanel pnlPN = new JPanel(new GridLayout(1, 1, 3, 3));
								pnlEast.add(pnlPN, BorderLayout.WEST);
								{
									lblPNno = new JLabel("PN No.");
									pnlPN.add(lblPNno);
								}

								JPanel pnlLookUp = new JPanel(new GridLayout(1, 1, 3, 3));
								pnlEast.add(pnlLookUp,BorderLayout.CENTER);
								{
									JPanel pnlPNno = new JPanel(new BorderLayout(3,3));
									pnlLookUp.add(pnlPNno);
									{

										lookUpPNno = new _JLookup("", "PN No.", 0) ; /// XXX lookUpPNno 
										pnlPNno.add(lookUpPNno,BorderLayout.WEST);
										lookUpPNno.addLookupListener(this);
										lookUpPNno.setReturnColumn(0);
										lookUpPNno.setLookupSQL(method.LookupPn_No());
										lookUpPNno.setPreferredSize(new Dimension(154, 20));
									}
								}
							}
						}
					}
					{
						pnlDetails = new JPanel(new BorderLayout(3, 3));
						pnlNorth.add(pnlDetails, BorderLayout.CENTER);
						{
							tabCenter = new _JTabbedPane();
							pnlDetails.add(tabCenter, BorderLayout.CENTER);
							{
								pnlTabDetails = new  JPanel();
								tabCenter.addTab(" Details ", null, pnlTabDetails, null);
								pnlTabDetails.setPreferredSize(new Dimension(pnlMain.getWidth(), 250));
								pnlTabDetails.setLayout(new BorderLayout(5,5));
								{
									JPanel pnlWest = new JPanel(new BorderLayout(5, 5));
									pnlTabDetails.add(pnlWest, BorderLayout.WEST);
									pnlWest.setPreferredSize(new Dimension(450, 20));
									{
										pnlLeft = new JPanel(new GridLayout(4, 1, 2, 2));
										pnlWest.add(pnlLeft, BorderLayout.WEST);
										pnlLeft.setPreferredSize(new Dimension(70, 30));
										{
											{
												lblProject = new JLabel(" Project");
												pnlLeft.add(lblProject);
											}
											{
												lblPBL = new JLabel(" Ph/Bl/Lt");
												pnlLeft.add(lblPBL);
											}
											{
												lblTelNo = new JLabel(" Tel. No. ");
												pnlLeft.add(lblTelNo);
											}
											{
												lblMobile = new JLabel(" Mobile No ");
												pnlLeft.add(lblMobile);
											}
										}

									}
									{
										pnlRight = new JPanel(new GridLayout(4, 1, 2, 2));
										pnlWest.add(pnlRight, BorderLayout.CENTER);
										{
											{
												JPanel pnlProj_text = new JPanel(new BorderLayout(5, 5));
												pnlRight.add(pnlProj_text);
												{
													{
														txtProjID = new JTextField();
														pnlProj_text.add(txtProjID, BorderLayout.WEST);
														txtProjID.setPreferredSize(new Dimension(80, 0));
														txtProjID.setEditable(false);
														txtProjID.setHorizontalAlignment(JTextField.CENTER);

													}
													{
														txtProjDesc = new JTextField();
														pnlProj_text.add(txtProjDesc);
														txtProjDesc.setPreferredSize(new Dimension(500, 0));
														txtProjDesc.setEditable(false);
													}
												}
											}
											{
												JPanel pnlPBL = new JPanel(new BorderLayout(5, 5));
												pnlRight.add(pnlPBL);
												{
													{
														txtPBLID = new JTextField();
														pnlPBL.add(txtPBLID, BorderLayout.WEST);
														txtPBLID.setPreferredSize(new Dimension(80, 0));
														txtPBLID.setEditable(false);
														txtPBLID.setHorizontalAlignment(JTextField.CENTER);

													}
													{
														txtPBLDesc = new JTextField();
														pnlPBL.add(txtPBLDesc);
														txtPBLDesc.setPreferredSize(new Dimension(500, 0));
														txtPBLDesc.setEditable(false);
													}
												}
											}
											{
												JPanel pnlTelNo = new JPanel(new BorderLayout(5, 5));
												pnlRight.add(pnlTelNo);
												{
													{
														txtTelNo = new JTextField();
														pnlTelNo.add(txtTelNo, BorderLayout.CENTER);
														txtTelNo.setEditable(false);
														txtTelNo.setHorizontalAlignment(JTextField.CENTER);
													}
												}
											}
											{
												JPanel pnlMobileNo = new JPanel(new BorderLayout(5, 5));
												pnlRight.add(pnlMobileNo);
												{
													{
														txtMobile = new JTextField();
														pnlMobileNo.add(txtMobile, BorderLayout.CENTER);
														txtMobile.setEditable(false);
														txtMobile.setHorizontalAlignment(JTextField.CENTER);
													}
												}
											}
										}
									}
									{

										pnlPastDue = new JPanel(new BorderLayout(5, 5));
										pnlTabDetails.add(pnlPastDue, BorderLayout.CENTER);
										pnlPastDue.setPreferredSize(new Dimension(150, 0));
										{
											JPanel pnlWest_ = new JPanel(new GridLayout(4, 1, 3, 3));
											pnlPastDue.add(pnlWest_, BorderLayout.WEST);
											{
												lblMoUpdate = new JLabel("No To Update");
												pnlWest_.add(lblMoUpdate);
											}
											{
												lblNoDaysPD = new JLabel("No of Days PD");
												pnlWest_.add(lblNoDaysPD);
											}
											{
												lblDPMA = new JLabel("DP / MA Stage");
												pnlWest_.add(lblDPMA);
											}

											{
												lblBlank = new JLabel("");
												pnlWest_.add(lblBlank);
											}

										}
										{
											JPanel pnlCenter_ = new JPanel(new GridLayout(4, 1, 2, 2));
											pnlPastDue.add(pnlCenter_, BorderLayout.CENTER);
											{
												txtMoUpdate = new JTextField();
												pnlCenter_.add(txtMoUpdate, BorderLayout.CENTER);
												txtMoUpdate.setEditable(false);
												txtMoUpdate.setHorizontalAlignment(JTextField.CENTER);
											}
											{

												txtNoDays = new JTextField();
												pnlCenter_.add(txtNoDays, BorderLayout.CENTER);
												txtNoDays.setEditable(false);
												txtNoDays.setHorizontalAlignment(JTextField.CENTER);

											}
											{
												txtDPMA = new JTextField();
												pnlCenter_.add(txtDPMA, BorderLayout.CENTER);
												txtDPMA.setEditable(false);
												txtDPMA.setHorizontalAlignment(JTextField.CENTER);

											}
											{
												lblBlank_ = new JLabel("");
												pnlCenter_.add(lblBlank);
											}
										}

									}
									{

										pnlPastDue_Amt = new JPanel(new BorderLayout(5, 5));
										pnlTabDetails.add(pnlPastDue_Amt, BorderLayout.EAST);
										pnlPastDue_Amt.setPreferredSize(new Dimension(230, 0));
										{
											JPanel Amt_West = new JPanel(new GridLayout(4, 1, 3, 3));
											pnlPastDue_Amt.add(Amt_West, BorderLayout.WEST);
											{
												lblAmountDue = new JLabel("Amount Due");
												Amt_West.add(lblAmountDue);
											}
											{
												lblLastDatePaid = new JLabel("Last Date Paid");
												Amt_West.add(lblLastDatePaid);
											}
											{
												lblDefaultDate = new JLabel("Default Date");
												Amt_West.add(lblDefaultDate);
											}
											{
												lblBlankAmt = new JLabel("");
												Amt_West.add(lblBlankAmt);
											}

										}

										JPanel pnlAmt_Center = new JPanel(new GridLayout(4, 1, 2, 2));
										pnlPastDue_Amt.add(pnlAmt_Center, BorderLayout.CENTER);

										{
											{
												txtAmountDue = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
												pnlAmt_Center.add(txtAmountDue, BorderLayout.CENTER);
												txtAmountDue.	setFormatterFactory(_JXFormattedTextField.DECIMAL);
												txtAmountDue.setEditable(false);
											}
											{

												txtLastDatePaid = new JTextField();
												pnlAmt_Center.add(txtLastDatePaid, BorderLayout.CENTER);
												txtLastDatePaid.setEditable(false);
												txtLastDatePaid.setHorizontalAlignment(JTextField.CENTER);

											}
											{

												txtDefaultDate = new JTextField();
												pnlAmt_Center.add(txtDefaultDate, BorderLayout.CENTER);
												txtDefaultDate.setEditable(false);
												txtDefaultDate.setHorizontalAlignment(JTextField.CENTER);

											}
											{
												lblBlankAmt_ = new JLabel("");
												pnlAmt_Center.add(lblBlankAmt_);
											}
										}

									}
								}
								{
									pnlTabPnHistory = new  JPanel();
									tabCenter.addTab(" PN History ", null, pnlTabPnHistory, null);
									pnlTabPnHistory.setPreferredSize(new Dimension(pnlMain.getWidth(), 250));
									pnlTabPnHistory.setLayout(new BorderLayout(5,5));

									{

										JPanel pnlPnTable = new JPanel(new BorderLayout(3, 3));
										pnlTabPnHistory.add(pnlPnTable,BorderLayout.CENTER);

										{


											scrollPNHistory = new JScrollPane();
											pnlPnTable.add(scrollPNHistory, BorderLayout.CENTER);
											scrollPNHistory.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
											scrollPNHistory.addMouseListener(new MouseAdapter() {
												public void mouseClicked(MouseEvent e) {
													tblPNHistory.clearSelection();
												}
											});

											{

												modelPNHistory = new model_PN_History();
												modelPNHistory.addTableModelListener(new TableModelListener() {
													public void tableChanged(TableModelEvent e) {
														if(e.getType() == TableModelEvent.DELETE){
															rowHeaderPNH.setModel(new DefaultListModel());
														}
														if(e.getType() == TableModelEvent.INSERT){
															((DefaultListModel)rowHeaderPNH.getModel()).addElement(modelPNHistory.getRowCount());
														}
													}
												});

												tblPNHistory = new _JTableMain(modelPNHistory);
												scrollPNHistory.setViewportView(tblPNHistory);
												modelPNHistory.setEditable(false);
												tblPNHistory.setHorizontalScrollEnabled(true);
												//tblPNHistory.addMouseListener(this);
												tblPNHistory.packAll();

												/** Repaint for Highlight **/
												tblPNHistory.getTableHeader().addMouseListener(new MouseAdapter() {
													public void mouseClicked(MouseEvent evt) {
														tblPNHistory.repaint();
													}
												});

												tblPNHistory.addMouseListener(new MouseAdapter() {


													@Override
													public void mouseClicked(MouseEvent e) {

														if (ifViewPnHistory) {

															modelCommit.clear();
															rowHeaderCommit.setModel(new DefaultListModel());
															scrollCommit.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblCommit.getRowCount())));
															int selectedIndex =  tblPNHistory.convertRowIndexToModel(tblPNHistory.getSelectedRow());

															pgSelect db = new pgSelect();
															String  PnNo = (String) modelPNHistory.getValueAt(selectedIndex, 0);
															String  status_id  = (String) modelPNHistory.getValueAt(selectedIndex, 4);

															db.select("select commit_date ,amount,no_of_mo from rf_pn_detail where pn_no = '"+PnNo+"' and status_id = 'A'");


															if (db.isNotNull()) {

																for (int i = 0; i < db.getRowCount(); i++) {
																	commit_date = (Timestamp)db.Result[i][0];

																	SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");
																	//sdf.format(commit_date),tblCommit.getSelectedRow(),0));
																	commit_amount = (BigDecimal)db.Result[i][1];

																	modelCommit.addRow(new Object[] {sdf.format(commit_date),commit_amount,db.Result[i][2],null });	
																}
															}

															computeTotal();
															scrollCommitAmountTotal.setRowHeaderView(FncTables.getRowHeader_Footer(Integer.toString(tblCommit.getRowCount())));
															tblCommit.packAll();

															if (status_id.equals("A")) {
																btnPrintForm.setEnabled(true);	
															}else{
																btnPrintForm.setEnabled(false);	
															}
															db.select(method.getEditDetails(PnNo));

															if (db.isNotNull()) {

																entity_id 		= db.Result[0][1].toString();
																proj_id 			= db.Result[0][7].toString();
																pbl_id 			= db.Result[0][8].toString();
																seq_no 		= (Integer) db.Result[0][9];

																client_name = db.Result[0][2].toString();
																remarks = db.Result[0][18].toString();
																signedby = db.Result[0][17].toString();
																rwC = db.Result[0][24]== null ? "" : db.Result[0][24].toString();
																rwC_ContactNo = db.Result[0][25]== null ? "" : db.Result[0][25].toString();
																rwC_Emailadd = db.Result[0][26]== null ? "" :db.Result[0][26].toString();

																//lookupPN_No.setText(_pn_no);
																//lookUpClientID.setValue(entity_id);
																//txtClientName.setText(db.Result[0][2].toString());
																//txtProjID.setText(db.Result[0][7].toString());
																//txtProjDesc.setText(db.Result[0][4].toString());
																//txtPBLID.setText(db.Result[0][8].toString());
																//txtPBLDesc.setText(db.Result[0][3].toString());

																//txtTelNo.setText((String) (db.Result[0][5]== null ? "" : db.Result[0][5]));
																//txtMobile.setText((String) (db.Result[0][6]== null ? "" : db.Result[0][6]) );
																//txtNoDays.setText(db.Result[0][11].toString()+" days");
																//txtMoUpdate.setText(db.Result[0][10].toString()+" month(s)");

																//txtDPMA.setText(db.Result[0][12].toString());

																//payment_stage = txtDPMA.getText();
																//txtAmountDue.setValue((BigDecimal)db.Result[0][13]);
																//txtLastDatePaid.setText(db.Result[0][14].toString());
																//txtDefaultDate.setText(db.Result[0][15].toString());

																//dteDateSubmitted.setDate((Date)db.Result[0][16]);
																txtRemarks.setText(db.Result[0][18].toString());
																monthsPD = Integer.valueOf(db.Result[0][10].toString());
																lookupApproved.setValue(db.Result[0][19].toString());
																txtApproved.setText(db.Result[0][20].toString());
																txtSignedby.setText(db.Result[0][17].toString());

																paymentDate = (Timestamp) db.Result[0][23];
																txtAmountPay.setValue((BigDecimal)db.Result[0][21]);
																txtPaymentAmt.setValue((BigDecimal)db.Result[0][22]);
																dtePaymentDate.setDate(paymentDate);

																txtRwClient.setText(db.Result[0][24]== null ? "" : db.Result[0][24].toString());
																txtContactNo.setText(db.Result[0][25]== null ? "" : db.Result[0][25].toString());
																txtEmailAdd.setText(db.Result[0][26]== null ? "" :db.Result[0][26].toString());


																payment_amount = (BigDecimal)db.Result[0][22];
																lastPaidDate = txtLastDatePaid.getText();
																defaultdDate = txtDefaultDate.getText();
																daysPD = Integer.valueOf(db.Result[0][11].toString());


																//btnState(true, true, true, false, true, false, true, true);

																//	rowHeaderPNH.setModel(new DefaultListModel());
																//	_FPromissoryNoteCommintment.displayPnHistory(modelPNHistory, rowHeaderPNH,  entity_id,  proj_id,pbl_id, seq_no );
																//	scrollPNHistory.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPNHistory.getRowCount())));

																if (monthsPD == 0) {
																	setSecondaryTitle((String) "Client has no Past Due Payments");
																	startTimerStatus();
																}else{
																	if(timerStatus != null){
																		timerStatus.stop();
																	}

																	setSecondaryTitle("Past Due Payments");
																	startTimerStatus();
																}
															}
														}
													}
												});
											}
											{

												rowHeaderPNH = tblPNHistory.getRowHeader();
												rowHeaderPNH.setModel(new DefaultListModel());
												scrollPNHistory.setRowHeaderView(rowHeaderPNH);
												scrollPNHistory.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
											}
										}
									}
								}
							}
						}
					}
				}
				{
					pnlCenter = new JPanel(new BorderLayout(3, 3));
					pnlMain.add(pnlCenter,BorderLayout.CENTER);
					pnlCenter.setPreferredSize(new Dimension(pnlMain.getWidth(), 100));
					{
						pnlPNPaymentPlan = new  JPanel();
						pnlCenter.add(pnlPNPaymentPlan,BorderLayout.CENTER);
						pnlPNPaymentPlan.setBorder(components.JTBorderFactory.createTitleBorder("Promissory Note - Payment Plan"));// XXX
						pnlPNPaymentPlan.setPreferredSize(new Dimension(650, 20));
						pnlPNPaymentPlan.setLayout(new BorderLayout(5,5));
						{

							JPanel pnlWest = new JPanel(new GridLayout(9, 1, 3, 3));
							pnlPNPaymentPlan.add(pnlWest, BorderLayout.WEST);
							{

								lblAmtToPay = new JLabel("Amount to Pay");
								pnlWest.add(lblAmtToPay);
								{
									lblPaymentAmount= new JLabel("* Payment Amount");
									pnlWest.add(lblPaymentAmount);
								}
								{
									lblPaymentDate = new JLabel("* Payment Date");
									pnlWest.add(lblPaymentDate);
								}
								{
									lblSignedBy = new JLabel("* Signed By");
									pnlWest.add(lblSignedBy);
								}
								{
									lblRwClient = new JLabel("* Relationship w/ Client");
									pnlWest.add(lblRwClient);
								}
								{
									lblContact_No = new JLabel("Contact No.");
									pnlWest.add(lblContact_No);
								}
								{
									lblEmailAdd = new JLabel("Email Add.");
									pnlWest.add(lblEmailAdd);
								}
								{
									lblRemarks = new JLabel("* Remarks");
									pnlWest.add(lblRemarks);
								}
								{
									lblApproved = new JLabel("* Approved by :");
									pnlWest.add(lblApproved);
								}

								JPanel pnlCenter = new JPanel(new GridLayout(9, 1, 3, 3));
								pnlPNPaymentPlan.add(pnlCenter, BorderLayout.CENTER);
								{
									{
										txtAmountPay = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
										pnlCenter.add(txtAmountPay, BorderLayout.CENTER);
										txtAmountPay.setFormatterFactory(_JXFormattedTextField.DECIMAL);
										txtAmountPay.setEditable(false);

									}
									{
										txtPaymentAmt = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
										pnlCenter.add(txtPaymentAmt, BorderLayout.CENTER);
										txtPaymentAmt.setFormatterFactory(_JXFormattedTextField.DECIMAL);
									}
									{
										dtePaymentDate = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
										pnlCenter.add(dtePaymentDate);
										dtePaymentDate.setDate(null);
									}
									{
										txtSignedby = new JTextField();
										pnlCenter.add(txtSignedby);
									}
									{
										txtRwClient = new JTextField();
										pnlCenter.add(txtRwClient);
									}
									{
										txtContactNo = new JTextField();
										pnlCenter.add(txtContactNo);
									}
									{
										txtEmailAdd = new JTextField();
										pnlCenter.add(txtEmailAdd);
									}
									{
										txtRemarks = new JTextField();
										pnlCenter.add(txtRemarks);
									}
									{
										JPanel pnlApproved = new JPanel(new BorderLayout(5,5));
										pnlCenter.add(pnlApproved,BorderLayout.CENTER);
										pnlApproved.setPreferredSize(new Dimension(74, 20));
										{

											lookupApproved = new _JLookup("", "", 0) ; /// XXX  
											pnlApproved.add(lookupApproved,BorderLayout.WEST);
											lookupApproved.addLookupListener(this);
											lookupApproved.setLookupSQL(method.getSignedBy());
											lookupApproved.setPreferredSize(new Dimension(100, 20));
										}
										{
											txtApproved = new JTextField();
											pnlApproved.add(txtApproved,BorderLayout.CENTER);
											txtApproved.setEditable(false);
											txtApproved.setPreferredSize(new Dimension(100, 25));
										} 
									}
								}
							}
						}
					}
					{
						pnlCommitment = new  JPanel();
						pnlCenter.add(pnlCommitment,BorderLayout.EAST);
						pnlCommitment.setBorder(components.JTBorderFactory.createTitleBorder("Promissory Note - Commitment"));// XXX
						pnlCommitment.setPreferredSize(new Dimension(420, 25));
						pnlCommitment.setLayout(new BorderLayout(5,5));
						{


							pnlCenter_Commit = new JPanel(new BorderLayout(3, 3));
							pnlCommitment.add(pnlCenter_Commit, BorderLayout.CENTER);
							pnlCenter_Commit.setPreferredSize(new Dimension(0, 150));
							{
								dteCommitDate = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
								pnlCenter_Commit.add(dteCommitDate);
								dteCommitDate.setDate(null);
								((JTextFieldDateEditor)dteCommitDate.getDateEditor()).setEditable(false);
								dteCommitDate.getCalendarButton().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
								((JTextFieldDateEditor)dteCommitDate.getDateEditor()).getDocument().addDocumentListener(new DocumentListener() {
									public void insertUpdate(DocumentEvent evt) {
										SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");
										modelCommit.setValueAt(""+sdf.format(dteCommitDate.getDate()),tblCommit.getSelectedRow(),0);
									}
									public void changedUpdate(DocumentEvent e){ }
									public void removeUpdate(DocumentEvent e) { }
								});
							}
							{

								cmbMonthUpdateModel = new DefaultComboBoxModel(new String[] { "","1 month", "2 months","3 months","4 months","5 months","to update" });
								cmbMonthUpdate = new JComboBox();
								cmbMonthUpdate.setModel(cmbMonthUpdateModel);
								cmbMonthUpdate.addActionListener(this);
							}
							{

								scrollCommit = new _JScrollPaneMain();
								pnlCenter_Commit.add(scrollCommit, BorderLayout.CENTER);
								scrollCommit.addMouseListener(new MouseAdapter() {
									public void mouseClicked(MouseEvent e) {
										tblCommit.clearSelection();
									}
								});
								{

									modelCommit = new model_PN_Commit();
									modelCommit.addTableModelListener(new TableModelListener() {
										public void tableChanged(TableModelEvent e) {
											//Addition of rows
											if(e.getType() == 1){
												((DefaultListModel)rowHeaderCommit.getModel()).addElement(modelCommit.getRowCount());

												if(modelCommit.getRowCount() == 0){
													rowHeaderCommit.setModel(new DefaultListModel());
												}
											}
										}
									});

									tblCommit = new _JTableMain(modelCommit);
									scrollCommit.setViewportView(tblCommit);
									modelCommit.setEditable(true);
									tblCommit.setHorizontalScrollEnabled(true);
									//tblCommit.addMouseMotionListener(this);
									//tblCommit.addMouseListener(this);
									tblCommit.hideColumns("Balance");

									TableColumn monthcolumn = tblCommit.getColumnModel().getColumn(2);
									monthcolumn.setCellEditor(new DefaultCellEditor(cmbMonthUpdate));
									tblCommit.addPropertyChangeListener(new PropertyChangeListener() {//XXX 
										public void propertyChange(PropertyChangeEvent arg0) {
											_JTableMain table = (_JTableMain) arg0.getSource();
											String propertyName = arg0.getPropertyName();

											if (propertyName.equals("tableCellEditor")) {
												int column = table.convertColumnIndexToModel(table.getEditingColumn());
												int row = table.getEditingRow();

												int total_amount = table.convertColumnIndexToModel(table.getColumnModel().getColumnIndex("Amount"));

												if (column != -1 && column != 3 && modelCommit.getColumnClass(column).equals(BigDecimal.class)) {
													Object oldValue = null;
													try {
														oldValue = ((NumberEditorExt) arg0.getOldValue()).getCellEditorValue();
													} catch (NullPointerException e) {
													}

													if (oldValue != null) {
														if (oldValue instanceof Double) {
															table.setValueAt(new BigDecimal((Double) oldValue), row, column);
															modelCommit.setValueAt(new BigDecimal((Double) oldValue), row, total_amount);
														}
														if (oldValue instanceof Long) {
															table.setValueAt(new BigDecimal((Long) oldValue), row, column);
															modelCommit.setValueAt(new BigDecimal((Long) oldValue), row, total_amount);
														}
														computeTotal();
													}
												}
											}
										}
									});

									/** Repaint for Highlight **/
									tblCommit.getTableHeader().addMouseListener(new MouseAdapter() {
										public void mouseClicked(MouseEvent evt) {
											tblCommit.repaint();
										}
									});

									tblCommit.addMouseListener(new MouseAdapter() {

										@Override
										public void mouseClicked(MouseEvent e) {

											if (e.getSource().equals(tblCommit)) {
												if (tblCommit.getSelectedColumn()== 0 ) {

													if (e.getClickCount() ==2 ) {
														dteCommitDate.setBounds((int)pnlCenter_Commit.getMousePosition().getX(), (int)pnlCenter_Commit.getMousePosition().getY(), 0, 0);
														dteCommitDate.getCalendarButton().doClick();
													}
												} // column 0
											}
										}
									});
								}
								{

									rowHeaderCommit = tblCommit.getRowHeader();
									rowHeaderCommit.setModel(new DefaultListModel());
									scrollCommit.setRowHeaderView(rowHeaderCommit);
									scrollCommit.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
								}
								{

									scrollCommitAmountTotal = new _JScrollPaneTotal(scrollCommit);
									pnlCenter_Commit.add(scrollCommitAmountTotal, BorderLayout.SOUTH);
									{
										modelCommitAmountTotal = new model_PN_Commit();
										modelCommitAmountTotal.addRow(new Object[] {"Total", 0.00, null, null });

										tblCommitAmountTotal = new _JTableTotal(modelCommitAmountTotal, tblCommit);
										scrollCommitAmountTotal.setViewportView(tblCommitAmountTotal);
										tblCommitAmountTotal.hideColumns("Balance");
										tblCommitAmountTotal.setTotalLabel(0);
									}
									scrollCommitAmountTotal.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, displayTableNavigation());
								}
							}
						}
					}
				}
				{
					pnlSouth = new JPanel(new BorderLayout(3, 3));
					pnlMain.add(pnlSouth,BorderLayout.SOUTH);
					pnlSouth.setPreferredSize(new Dimension(pnlMain.getWidth(), 40));
					{
						{
							JPanel pnlWest = new JPanel(new BorderLayout(3, 3));
							pnlSouth.add(pnlWest,BorderLayout.WEST);
							pnlWest.setPreferredSize(new Dimension(100, 40));
							{
								JPanel pnlWest_btn = new JPanel(new GridLayout(1, 4, 3, 3));
								pnlSouth.add(pnlWest_btn, BorderLayout.WEST);
								{
									btnViewInfo = new _JButton("View Client Info.");
									pnlWest_btn.add(btnViewInfo);
									btnViewInfo.addActionListener(this);
								}
								{
									btnPrintForm = new _JButton("Preview");
									pnlWest_btn.add(btnPrintForm);
									btnPrintForm.addActionListener(this);
								}
								{
									btnPreview = new _JButton("<html><b><p align=\"center\">Preview <br>(PN Summary)</p></b></html>");
									pnlWest_btn.add(btnPreview);
									btnPreview.addActionListener(this);

								}
							}
						}
						{
							JPanel pnlEast = new JPanel(new BorderLayout(3, 3));
							pnlSouth.add(pnlEast,BorderLayout.EAST);
							pnlEast.setPreferredSize(new Dimension(460, 40));
							{
								JPanel pnlEast_btn = new JPanel(new GridLayout(1, 3, 3, 3));
								pnlEast.add(pnlEast_btn, BorderLayout.CENTER);
								{
									btnAdd = new _JButton("Add New");
									pnlEast_btn.add(btnAdd);
									btnAdd.setActionCommand("Add");
									grpButton.add(btnAdd);
									btnAdd.addActionListener(this);
								}
								{
									btnEdit = new _JButton("Edit");
									pnlEast_btn.add(btnEdit);
									btnEdit.setActionCommand("Edit");
									grpButton.add(btnEdit);
									btnEdit.addActionListener(this);
								}
								{
									btnSave = new _JButton("Save");
									pnlEast_btn.add(btnSave);
									btnSave.addActionListener(this);
								}
								{

									btnPost = new _JButton("Post");
									pnlEast_btn.add(btnPost);
									btnPost.setActionCommand("Post");
									grpButton.add(btnPost);
									btnPost.addActionListener(this);

								}
								{
									btnCancel = new _JButton("Cancel");
									pnlEast_btn.add(btnCancel);
									btnCancel.addActionListener(this);
								}
							}
						}

					}
				}
			}
		}

	}
	private JPanel displayTableNavigation() {
		btnAddRow = new JButton(new ImageIcon(this.getClass().getClassLoader().getResource("Images/Science-Plus2-Math-icon.png")));
		btnAddRow.setActionCommand("Add Row");
		btnAddRow.setToolTipText("Add Row");
		btnAddRow.setEnabled(true);
		btnAddRow.addActionListener(this);

		btnRemoveRow = new JButton(new ImageIcon(this.getClass().getClassLoader().getResource("Images/Science-Minus2-Math-icon.png")));
		btnRemoveRow.setActionCommand("Remove Row");
		btnRemoveRow.setToolTipText("Remove Row");
		btnRemoveRow.setEnabled(true);
		btnRemoveRow.addActionListener(this);

		JPanel pnl = new JPanel(new GridLayout(1, 2));
		pnl.add(btnAddRow);
		pnl.add(btnRemoveRow);

		return pnl;
	}
	private void computeTotal() {
		BigDecimal totalAmountCommitted = new BigDecimal("0.00");

		for (int x = 0; x < modelCommit.getRowCount(); x++) {
			BigDecimal totalamount = (BigDecimal) modelCommit.getValueAt(x, 1);

			try {
				totalAmountCommitted = totalAmountCommitted.add(totalamount);
			} catch (Exception e1) { }
		}

		modelCommitAmountTotal.setValueAt(totalAmountCommitted, 0, 1);
	}

	private void updateCommit() {
		scrollCommitAmountTotal.setRowHeaderView(FncTables.getRowHeader_Footer(Integer.toString(tblCommit.getRowCount())));
		tblCommit.packAll();

		for (int row = 0; row < tblCommit.getRowCount(); row++) {
			((DefaultListModel) rowHeaderCommit.getModel()).setElementAt(row + 1, row);
		}
	}


	private void btnState(Boolean sInfo, Boolean sForm,Boolean sSummary, Boolean sAdd, Boolean sEdit, Boolean sSave, Boolean sPost, Boolean sCancel ){
		btnViewInfo.setEnabled(sInfo);
		btnPrintForm.setEnabled(sForm);
		btnPreview.setEnabled(sSummary);
		btnAdd.setEnabled(sAdd);
		btnEdit.setEnabled(sEdit);
		btnSave.setEnabled(sSave);
		btnPost.setEnabled(sPost);
		btnCancel.setEnabled(sCancel);


	}
	@Override
	public void lookupPerformed(LookupEvent e) {


		if( e.getSource().equals(lookupCompany)){ 
			Object[] data = ((_JLookup)e.getSource()).getDataSet();

			if(data != null){

				co_id = data[0].toString();
				company = data[1].toString();
				company_logo = data[3] == null ? "" : data[3].toString() ;
				txtCompany.setText(company);
			}
		}

		if (e.getSource().equals(lookUpClientID)) {
			ifViewPnHistory = true;
			System.out.println("SQL" +lookUpClientID.getLookupSQL() );

			Object[] data = ((_JLookup)e.getSource()).getDataSet();
			pgSelect db = new pgSelect();
			
			if (data != null) {
				entity_id = data[6].toString();
				proj_id = data[7].toString();
				pbl_id = data[8].toString();
				seq_no = (Integer)data[9];
				buyertypeID = data[10].toString();
				client_name = data[0].toString();
				unit = data[1].toString();
				proj_name = data[3].toString();
				tel_no = data[4] == null ? "" : data[4].toString();
				mobile_no = data[5] == null ? "" : data[5].toString();
				payment_stage = data[11] == null ? "" : data[11].toString();
				lookUpClientID.setValue(entity_id);
				txtClientName.setText(client_name);
				txtProjID.setText(proj_id);
				txtProjDesc.setText(proj_name);
				txtPBLID.setText(pbl_id);
				txtPBLDesc.setText(unit);
				txtTelNo.setText(tel_no);
				txtMobile.setText(mobile_no);

				db.select(method.getIfPasDue(entity_id, proj_id, pbl_id, seq_no, buyertypeID));

				if (db.isNotNull()) {

					monthsPD =  (Integer) (db.Result[0][0] == null ? 0 : db.Result[0][0]);
					daysPD = (Integer) (db.Result[0][1] == null ? 0 : db.Result[0][1]);
					amountDue = (BigDecimal) (db.Result[0][2] == null ? "0.00" :  db.Result[0][2]);
					lastPaidDate = (String) (db.Result[0][3] == null ? "": db.Result[0][3]);
					defaultdDate = (String) (db.Result[0][4] == null ? "" :db.Result[0][4]) ;


					if (monthsPD == 0) {
						setSecondaryTitle((String) "Client has no Past Due Payments");
						startTimerStatus();
					}else{
						if(timerStatus != null){
							timerStatus.stop();
						}

						setSecondaryTitle("Past Due Payments");
						startTimerStatus();
					}



					txtMoUpdate.setText(monthsPD +" month(s)");
					txtNoDays.setText(daysPD +" day(s)");
					txtDPMA.setText(payment_stage);
					txtAmountDue.setValue(amountDue);
					txtLastDatePaid.setText(lastPaidDate);
					txtDefaultDate.setText(defaultdDate);



					db.select(method.displayPastDues(entity_id, proj_id, pbl_id, seq_no));

					if (db.isNotNull()) {
						amountPay = (BigDecimal) db.Result[0][0];
						paymentDate = (Timestamp) db.Result[0][1];

						txtAmountPay.setValue(amountPay);
						dtePaymentDate.setDate(paymentDate);

					}
					btnState(false, false, true, true, false, false, false, true);
				} else {

					setSecondaryTitle((String) "Client has no Past Due Payments");
					startTimerStatus();
					JOptionPane.showMessageDialog(FncGlobal.homeMDI, "No Records to Show!", "Empty Resultset", JOptionPane.INFORMATION_MESSAGE);
					
					btnState(false, false, true, false, false, false, false, true);
				}

				lookUpPNno.setEditable(false);
				rowHeaderPNH.setModel(new DefaultListModel());
				_FPromissoryNoteCommintment.displayPnHistory(modelPNHistory, rowHeaderPNH,  entity_id, proj_id,pbl_id, seq_no );
				scrollPNHistory.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPNHistory.getRowCount())));
			}
		}

		if (e.getSource().equals(lookUpPNno)) {
			Object[] data = ((_JLookup)e.getSource()).getDataSet();
			ifViewPnHistory = false;
			lookUpClientID.setEnabled(false);
			modelCommit.clear();

			if (data != null) {

				pgSelect db = new pgSelect();
				PnNo = data[0].toString();
				co_id = (String) data[6];
				company = (String) data[7];
				company_logo = (String) data[8];
				db.select(method.getEditDetails(PnNo));

				if (db.isNotNull()) {

					entity_id 		= db.Result[0][1].toString();
					proj_id 			= db.Result[0][7].toString();
					pbl_id 			= db.Result[0][8].toString();
					seq_no 		= (Integer) db.Result[0][9];
					
					lookupCompany.setValue(co_id);
					txtCompany.setText(company);

					client_name = db.Result[0][2].toString();
					remarks = db.Result[0][18].toString();
					signedby = db.Result[0][17].toString();
					rwC = db.Result[0][24]== null ? "" : db.Result[0][24].toString();
					rwC_ContactNo = db.Result[0][25]== null ? "" : db.Result[0][25].toString();
					rwC_Emailadd = db.Result[0][26]== null ? "" :db.Result[0][26].toString();

					//lookupPN_No.setText(_pn_no);
					lookUpClientID.setValue(entity_id);
					txtClientName.setText(db.Result[0][2].toString());
					txtProjID.setText(db.Result[0][7].toString());
					txtProjDesc.setText(db.Result[0][4].toString());
					txtPBLID.setText(db.Result[0][8].toString());
					txtPBLDesc.setText(db.Result[0][3].toString());

					txtTelNo.setText((String) (db.Result[0][5]== null ? "" : db.Result[0][5]));
					txtMobile.setText((String) (db.Result[0][6]== null ? "" : db.Result[0][6]) );
					txtNoDays.setText(db.Result[0][11].toString()+" days");
					txtMoUpdate.setText(db.Result[0][10].toString()+" month(s)");

					txtDPMA.setText(db.Result[0][12].toString());

					payment_stage = txtDPMA.getText();
					txtAmountDue.setValue((BigDecimal)db.Result[0][13]);
					txtLastDatePaid.setText(db.Result[0][14].toString());
					txtDefaultDate.setText(db.Result[0][15].toString());

					//dteDateSubmitted.setDate((Date)db.Result[0][16]);
					txtRemarks.setText(db.Result[0][18].toString());
					monthsPD = Integer.valueOf(db.Result[0][10].toString());
					lookupApproved.setValue(db.Result[0][19].toString());
					txtApproved.setText(db.Result[0][20].toString());
					txtSignedby.setText(db.Result[0][17].toString());

					paymentDate = (Timestamp) db.Result[0][23];
					txtAmountPay.setValue((BigDecimal)db.Result[0][21]);
					txtPaymentAmt.setValue((BigDecimal)db.Result[0][22]);
					dtePaymentDate.setDate(paymentDate);

					txtRwClient.setText(db.Result[0][24]== null ? "" : db.Result[0][24].toString());
					txtContactNo.setText(db.Result[0][25]== null ? "" : db.Result[0][25].toString());
					txtEmailAdd.setText(db.Result[0][26]== null ? "" :db.Result[0][26].toString());



					lastPaidDate = txtLastDatePaid.getText();
					defaultdDate = txtDefaultDate.getText();
					daysPD = Integer.valueOf(db.Result[0][11].toString());

					db.select("select commit_date ,amount,no_of_mo from rf_pn_detail where pn_no = '"+PnNo+"' and status_id = 'A'");


					if (db.isNotNull()) {

						for (int i = 0; i < db.getRowCount(); i++) {
							commit_date = (Timestamp)db.Result[i][0];

							SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");
							//sdf.format(commit_date),tblCommit.getSelectedRow(),0));
							commit_amount = (BigDecimal)db.Result[i][1];

							modelCommit.addRow(new Object[] {sdf.format(commit_date),commit_amount,db.Result[i][2],null });	
						}
					}

					btnState(true, true, true, false, true, false, true, true);


					computeTotal();
					scrollCommitAmountTotal.setRowHeaderView(FncTables.getRowHeader_Footer(Integer.toString(tblCommit.getRowCount())));
					tblCommit.packAll();

					rowHeaderPNH.setModel(new DefaultListModel());
					_FPromissoryNoteCommintment.displayPnHistory(modelPNHistory, rowHeaderPNH,  entity_id,  proj_id,pbl_id, seq_no );
					scrollPNHistory.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPNHistory.getRowCount())));

					if (monthsPD == 0) {
						setSecondaryTitle((String) "Client has no Past Due Payments");
						startTimerStatus();
					}else{
						if(timerStatus != null){
							timerStatus.stop();
						}

						setSecondaryTitle("Past Due Payments");
						startTimerStatus();
					}
				}

				///ifDept(_pn_no);
			}

		}

		if (e.getSource().equals(lookupApproved)) {
			Object[] data = ((_JLookup)e.getSource()).getDataSet();
			if(data != null){
				txtApproved.setText(data[1].toString());
				txtApproved.setCaretPosition(0);
			}
		}

	}

	public void actionPerformed(ActionEvent e) { // XXX actionPerformed


		String actionCommand = e.getActionCommand();

		if (e.getSource().equals(btnViewInfo)) {

			if (txtClientName.getText().isEmpty()) {
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select client first .", "Invalid", JOptionPane.INFORMATION_MESSAGE);
				return;
			}

			Runnable run = new Runnable() {
				@SuppressWarnings("static-access")
				@Override
				public void run() {
					FncGlobal.startProgress("Loading Client Information . . .Please wait ");

					if(FncGlobal.homeMDI.isNotExisting("ClientInformation")){
						ClientInformation ci = new ClientInformation("Client Information",entity_id, txtClientName.getText());
						ci.setVisible(true);
						FncGlobal.homeMDI.addWindow(ci);
					}
					FncGlobal.stopProgress();
				}
			};
			FncGlobal.loadThread("", run);
		}
		if (e.getSource().equals(chkIfPD)) {

			lookUpClientID.setLookupSQL(method.getClient_new(chkIfPD.isSelected(), lookupCompany.getValue()));

		}

		if (e.getSource().equals(btnAdd)) {
			grpButton.setSelected(btnAdd.getModel(), true);

			newProcess();

			btnState(true, true, false, false, false, true, false, true);

		}
		if (e.getSource().equals(btnEdit)) {
			grpButton.setSelected(btnEdit.getModel(), true);
			editProces();
		}

		if (e.getSource().equals(btnSave)) {
			toSave();
		}

		if (e.getSource().equals(btnPrintForm)) {
			PreviewForm();
		}

		if (e.getSource().equals(btnPost)) {
			grpButton.setSelected(btnPost.getModel(), true);

			computeTotal();
			int response = JOptionPane.showConfirmDialog(this, "Are you sure you want to post PN details ?  ", "Post", 	JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (response == JOptionPane.YES_OPTION) {

				toPost();
				System.out.println("----SAVE" );
				JOptionPane.showMessageDialog(this,"PN / commitment details posted","Successful", JOptionPane.INFORMATION_MESSAGE);			

				btnPrintForm.setEnabled(true);
				//	btnState(false, false, false, true, false, false);
				btnState(false, true, true, false, false, false, false, true);
				PreviewForm();
				clear();
				formLoad();
			}
		}



		if (actionCommand.equals("Add Row")) {
			modelCommit.addRow(new Object[] { null,null,null,null });
			updateCommit();
		}

		if (actionCommand.equals("Remove Row")) {
			int[] selectedRows = tblCommit.getSelectedRows();

			if (selectedRows.length == 0) {
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select row in Item(s).", "Remove Row", JOptionPane.INFORMATION_MESSAGE);
				return;
			}

			for (int x = selectedRows.length - 1; x > -1; x--) {
				int row = selectedRows[x];

				modelCommit.removeRow(row);
				((DefaultListModel) rowHeaderCommit.getModel()).removeElementAt(row);
			}

			updateCommit();
			computeTotal();
		}

		if (e.getSource().equals(btnPreview)) {

			dlgDate dlg= new dlgDate(FncGlobal.homeMDI, "Print Report");
			dlg.setLocationRelativeTo(null);
			dlg.setVisible(true);
			printall = dlg.getPrintAll();

			dateFrom = dlg.getDateFrom();
			dateTo = dlg.getDateTo();

			if (printall) {
				if (dlg.isOK()) {
					new Thread(new ForPreview()).start();	
				}

			}else{

				if (dateFrom == null || dateTo == null) {
					return;
				}else{
					dateFrom = dlg.getDateFrom();
					dateTo = dlg.getDateTo();
					printall = dlg.getPrintAll();

					new Thread(new ForPreview()).start();	
				}
			}

		}

		if (e.getSource().equals(btnCancel)) {

			int response = JOptionPane.showConfirmDialog(this,"Are you sure you want to Clear all fields?   ","Confirm",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (response == JOptionPane.YES_OPTION) {

				clear();
				formLoad(); 
			}
		}
	}

	private void formLoad(){

		load();
		chkIfPD.setSelected(true);
		lookUpClientID.setLookupSQL(method.getClient_new(chkIfPD.isSelected(), lookupCompany.getValue()));
		btnState(false, false, true, false, false, false, false, false);
		getDefaultCompany();
		lookUpClientID.setEditable(true);
		lookUpPNno.setEditable(true);
	}

	private void clear(){

		rowHeaderCommit.setModel(new DefaultListModel());
		scrollCommit.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblCommit.getRowCount())));
		scrollCommitAmountTotal.setRowHeaderView(FncTables.getRowHeader_Footer(Integer.toString(tblCommit.getRowCount())));
		computeTotal();

		lookUpClientID.setValue("");
		txtClientName.setText("");
		lookUpPNno.setValue("");

		txtProjID.setText("");
		txtProjDesc.setText("");
		txtPBLID.setText("");
		txtPBLDesc.setText("");
		txtTelNo.setText("");
		txtMobile.setText("");

		txtMoUpdate.setText("");
		txtNoDays.setText("");
		txtDPMA.setText("");
		txtAmountDue.setValue(null);
		txtLastDatePaid.setText("");
		txtDefaultDate.setText("");

		txtAmountPay.setValue(null);
		txtPaymentAmt.setValue(null);
		dtePaymentDate.setDate(null);
		txtSignedby.setText("");
		txtRwClient.setText("");
		txtContactNo.setText("");
		txtEmailAdd.setText("");
		txtRemarks.setText("");
		lookupApproved.setValue("");
		txtApproved.setText("");

		FncTables.clearTable(modelCommit);
		rowHeaderCommit.setModel(new DefaultListModel());
		scrollCommit.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblCommit.getRowCount())));
		scrollCommitAmountTotal.setRowHeaderView(FncTables.getRowHeader_Footer(Integer.toString(tblCommit.getRowCount())));
		computeTotal();

		FncTables.clearTable(modelPNHistory);
		rowHeaderPNH.setModel(new DefaultListModel());
		scrollPNHistory.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPNHistory.getRowCount())));



		PromissryNote_v2.this.setTitle(getPrimaryTitle());
	}

	private void load(){
		this.setComponentsEnabled(pnlNorth, false);
		this.setComponentsEnabled(pnlCenter, false);
		this.setComponentsEnabled(pnlTabDetails, false);
		this.setComponentsEnabled(north, true);


	}
	public void startTimerStatus() {//XXX
		ActionListener action = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				blinkState = !blinkState;

				if(blinkState){
					PromissryNote_v2.this.setTitle(String.format("%s *** %s ***", title, getSecondaryTitle()));
					//System.out.printf("********************************************************************** %s (%s)%n", getSecondaryTitle(), timerStatus.getActionCommand());
				}else{
					PromissryNote_v2.this.setTitle(getPrimaryTitle());
				}
			}
		};

		if(timerStatus == null){
			System.out.println("********** DUMAAN! #1");
			timerStatus = new Timer(1000, action);
			timerStatus.setActionCommand(getSecondaryTitle());
			timerStatus.start();
		}else{
			System.out.println("********** DUMAAN! #2");
			timerStatus.stop();
			timerStatus.restart();
		}

		//System.out.printf("Length: %s%n", timerStatus.getActionListeners().length);
	}

	public void stopTimerStatus() {
		timerStatus.stop();
		timerStatus = null;
	}


	private void newProcess(){


		PnNo  = method.getPN_no();
		lookUpPNno.setValue(PnNo);
		this.setComponentsEnabled(pnlCenter, true);
		this.setComponentsEnabled(pnlTabDetails, true);
		modelCommit.clear();
		rowHeaderCommit.setModel(new DefaultListModel());
		scrollCommit.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblCommit.getRowCount())));

		txtPaymentAmt.setValue(null);
		dtePaymentDate.setDate(paymentDate);
		txtSignedby.setText("");
		txtRwClient.setText("");
		txtContactNo.setText("");
		txtEmailAdd.setText("");
		lookupApproved.setValue("");
		txtApproved.setText("");
		txtRemarks.setText("");

		ifViewPnHistory = false;
	}

	private void editProces(){

		lookUpClientID.setEditable(false);

		this.setComponentsEnabled(pnlCenter, true);
		this.setComponentsEnabled(pnlTabDetails, true);
		btnState(true, true, false, false, false, true, false, true);
		ifViewPnHistory = false;
	}

	
	private void toSave(){
		String partID;
		Boolean ifAdd_Edit = false;
		rwC = txtRwClient.getText().equals("") ? null : txtRwClient.getText();
		rwC_ContactNo = txtContactNo.getText().equals("") ? null : txtContactNo.getText();
		rwC_Emailadd = txtEmailAdd.getText().equals("") ? null : txtEmailAdd.getText();
		payment_amount = (BigDecimal) txtPaymentAmt.getValued();
		BigDecimal commit_amount = new BigDecimal("0.00");
		BigDecimal ifnullcommit_amount = new BigDecimal("0.00");

		ArrayList<String> listCommitDate = new ArrayList<String>();
		ArrayList<String> listCommitAmt = new ArrayList<String>();
		ArrayList<String> listNooFMo = new ArrayList<String>();




		String SQL= "";



		pgSelect db = new pgSelect();
		if (checkRequiredFields()) {
			if (JOptionPane.showConfirmDialog((JFrame) this.getTopLevelAncestor(), "Are all entries correct?", "Save", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
				if (grpButton.getSelection().getActionCommand().equals("Add")) {
					ifAdd_Edit = true;
				}
				if(grpButton.getSelection().getActionCommand().equals("Edit")){
					ifAdd_Edit = false;
				}

				if (payment_stage.contains("MA") ) {
					partID = "014";
				}else{
					partID = "013";
				}

				SQL = "SELECT sp_save_promissorynote_final(\n" + 
						"    '"+entity_id+"',---p_entity_id character varying,\n" + 
						"    '"+proj_id+"',---p_proj_id character varying,\n" + 
						"    '"+pbl_id+"',---p_pbl_id character varying,\n" + 
						"    "+seq_no+",---i_seq_no integer,\n" + 
						"    '"+partID+"',---p_part_id character varying,\n" + 
						"    '"+lastPaidDate+"',---p_last_paid_date timestamp without time zone,\n" + 
						"    '"+defaultdDate+"',---p_default_date timestamp without time zone,\n" + 
						"    "+monthsPD+",---p_months_pd integer,\n" + 
						"    "+daysPD+",---p_days_pd integer,\n" + 
						"    '"+txtAmountDue.getValued()+"',---p_amount_due numeric,\n" + 
						"    '"+txtSignedby.getText()+"',---p_signed_by character varying,\n" + 
						"    '"+txtRemarks.getText()+"',---p_remarks character varying,\n" + 
						"    '"+ UserInfo.EmployeeCode+"',---p_requested_by character varying,\n" + 
						"    '"+lookUpPNno.getValue()+"',---p_pn_no character varying,\n" + 
						"    '"+lookupApproved.getValue()+"',---p_approved character varying,\n" + 
						"    '"+txtAmountPay.getValued()+"',---p_amount_to_pay numeric,\n" + 
						"    '"+txtPaymentAmt.getValued()+"',---p_payment_amount numeric,\n" + 
						"    '"+dtePaymentDate.getDate()+"',---p_payment_date timestamp without time zone,\n" + 
						"    NULLIF('"+rwC+"','null'),---p_relationwclient character varying,\n" + 
						"    NULLIF('"+rwC_ContactNo+"','null'),---p_contact_no_rwc character varying,\n" + 
						"    NULLIF('"+rwC_Emailadd+"','null'),---p_email_add_rwc character varying \n "+
						"   "+ifAdd_Edit+" ---p_email_add_rwc character varying \n "+
						")";


				db.select(SQL);


				for (int i = 0; i < modelCommit.getRowCount(); i++) {

					String commit_date = (String) (modelCommit.getValueAt(i, 0) == null ? null : modelCommit.getValueAt(i, 0));

					commit_amount = (BigDecimal) (modelCommit.getValueAt(i, 1) == null? ifnullcommit_amount :  modelCommit.getValueAt(i, 1))  ;
					String no_of_mo = (String) (modelCommit.getValueAt(i, 2) == null ? "" : modelCommit.getValueAt(i, 2));

					System.out.println("***********************" +commit_amount );
					listCommitDate.add(String.format("'%s'", commit_date));
					listCommitAmt.add(String.format("'%s'", commit_amount));
					listNooFMo.add(String.format("'%s'", no_of_mo));
				}
				String commitDate = listCommitDate.toString().replaceAll("\\[|\\]", "");
				String commitAmt = listCommitAmt.toString().replaceAll("\\[|\\]", "");
				String noofMo =listNooFMo.toString().replaceAll("\\[|\\]", "");

				SQL = "SELECT sp_save_promissorynote_detail(\n" + 
						"    '"+lookUpPNno.getValue()+"',---p_pn_no  character varying,\n" + 
						"    ARRAY["+ commitDate +"]::TIMESTAMP[],---p_commit_date timestamp without time zone,\n" + 
						"    ARRAY["+commitAmt +"]::NUMERIC[],---p_amount_commit numeric,\n" + 
						"    ARRAY["+ noofMo +"]::VARCHAR[]---p_no_of_mo character varying\n" + 
						"   )";


				System.out.println("***********************" +SQL );
				db.select(SQL);

				JOptionPane.showMessageDialog(this,"PN / commitment details saved","Successful", JOptionPane.INFORMATION_MESSAGE);	
				formLoad();
				
				
				btnState(false, true, true, false, true, false, false, true);
			}
		}
	}


	public boolean checkRequiredFields(){//CHECKS THE REQUIRED FIELD BEFORE SAVING
		String required_fields = "";
		Boolean toSave = true;
		
		if(isNull() || modelCommit.getRowCount() == 0){
			required_fields = required_fields + "Commit Date \n";
			toSave = false;
		}
		if(txtPaymentAmt.getText().isEmpty() || txtPaymentAmt.getText().equals("")){
			required_fields = required_fields + "Payment Amount \n";
			toSave = false;
		}
		if(dtePaymentDate.getDate() == null ){
			required_fields = required_fields + "Payment Date \n";
			toSave = false;
		}
		if(txtSignedby.getText().isEmpty() || txtSignedby.getText().equals("")){
			required_fields = required_fields + "Signed by \n";
			toSave = false;
		}

		if(txtRwClient.getText().isEmpty() || txtRwClient.getText().equals("")){
			required_fields = required_fields + "Relationship w/ Client \n";
			toSave = false;
		}
		if(txtRemarks.getText().isEmpty() || txtRemarks.getText().equals("")){
			required_fields = required_fields + "Remarks \n";
			toSave = false;
		}
		if(lookupApproved.getText().isEmpty() || lookupApproved.getText().equals("") || txtApproved.getText().equals("")){
			required_fields = required_fields + "Approved by \n";
			toSave = false;
		}
		if(toSave == false){
			JOptionPane.showMessageDialog(null, "Please fill up all required fields:\n"+required_fields,"Save",JOptionPane.WARNING_MESSAGE);
			return false;
		}
		return true;
	}

	private void getDefaultCompany(){

		co_id = "02";
		company = "CENQHOMES DEVELOPMENT CORPORATION" ;
		company_logo = "cenq_logo.jpg";
		txtCompany.setText(company);
		lookupCompany.setValue(co_id);

	}

	private void PreviewForm(){


		BigDecimal amount_due = new BigDecimal("0.00");

		BigDecimal commitamount = new BigDecimal("0.00");
		ArrayList<TD_PN_Commitment> list_PN_Commitment = new ArrayList<TD_PN_Commitment>();

		pgSelect db = new pgSelect();
		for (int i = 0; i < modelCommit.getRowCount(); i++) {

			String commit_date = modelCommit.getValueAt(i, 0).toString();

			db.select("SELECT to_char(Date '"+commit_date+"', 'MM/DD/YYYY')::timestamp;");
			//Timestamp commit_date = (Timestamp) modelCommit.getValueAt(i, 0);

			Timestamp commit_dates =  (Timestamp) db.Result[0][0];
			BigDecimal commit_amount = (BigDecimal)( modelCommit.getValueAt(i, 1) == null ? commitamount : modelCommit.getValueAt(i, 1));
			String to_update = (String) modelCommit.getValueAt(i, 2);
			Integer count = modelCommit.getRowCount() ;

			list_PN_Commitment.add(new TD_PN_Commitment(commit_dates,commit_amount,to_update,count));	
		}

		amount_due = (BigDecimal) txtAmountPay.getValued();

		payment_amount = (BigDecimal) txtPaymentAmt.getValued();		

		db.select("SELECT phase,block,lot FROM mf_unit_info where pbl_id  = '"+txtPBLID.getText().trim()+"' and proj_id = '"+txtProjID.getText().trim()+"'");

		if (db.isNotNull()) {
			phase = db.Result[0][0].toString();
			block = db.Result[0][1].toString();
			lot = db.Result[0][2].toString();
		}

		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("company", company);
		mapParameters.put("co_id", co_id);
		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
		mapParameters.put("pn_no", lookUpPNno.getValue().toString());
		mapParameters.put("SUBREPORT_DIR", this.getClass().getResourceAsStream("/Reports/subrptPromissoryNote_Preview.jasper"));
		mapParameters.put("list_PN_Commitment", list_PN_Commitment);

		mapParameters.put("client_name", txtClientName.getText().trim());
		mapParameters.put("phase", phase);
		mapParameters.put("block", block); 
		mapParameters.put("lot", lot);
		mapParameters.put("proj_name", txtProjDesc.getText().trim());
		mapParameters.put("as_of_date",paymentDate);
		mapParameters.put("amount_due", amount_due);
		mapParameters.put("signedby", txtSignedby.getText());
		mapParameters.put("payment_amount",payment_amount);
		mapParameters.put("relationwclient", txtRwClient.getText());
		mapParameters.put("contact_no", rwC_ContactNo);
		mapParameters.put("email_add", rwC_Emailadd);

		FncReport.generateReport("/Reports/rptPromissoryNoteForm_Preview.jasper", "Promissory Note", mapParameters);

	}

	private void toPost(){

		String rwC_ContactNo = txtContactNo.getText().equals("") ? null : txtContactNo.getText();
		String rwC_Emailadd = txtEmailAdd.getText().equals("") ? null : txtEmailAdd.getText();

		String convo_detail = "PN/Commit No."+lookUpPNno.getValue()+" - " + txtRemarks.getText();

		pgSelect dbs = new pgSelect();

		Timestamp commit_date = null;

		dbs.select("select commit_date ,amount,no_of_mo from rf_pn_detail where pn_no = '"+PnNo+"' and status_id = 'A' order by commit_date limit 1");

		if (dbs.isNotNull()) {

			commit_date = (Timestamp) dbs.Result[0][0];	
		}

		if (grpButton.getSelection().getActionCommand().equals("Post")) {
			pgUpdate db = new pgUpdate();
			db.executeUpdate("UPDATE rf_pn_header \n" + 
					"		SET	date_posted = now()::TIMESTAMP, \n" + 
					"			posted_by = '"+lookupApproved.getValue()+"', \n" + 
					"			approved = '"+lookupApproved.getValue()+"' \n" + 
					//"			,status_id = 'I' \n" +
					"		WHERE pn_no = '"+lookUpPNno.getValue()+"' AND status_id = 'A';\n" + 
					" ", true);


			String sql = "INSERT INTO rf_complaint_phone_followup(entity_id, pbl_id, seq_no, unit_id, proj_id, purpose_of_call_id, \n" + 
					"contact_person, follow_on_call_date, status_id, created_by, date_created, conversation, contact_no, \n"+
					"email, call_start, call_end)\n" + 
					"VALUES ('"+entity_id+"', '"+pbl_id+"', "+seq_no+", get_unit_id('"+proj_id+"', '"+pbl_id+"'), '"+proj_id+"', \n"+
					"'05', \n" + 
					"'"+txtSignedby.getText()+"', '"+commit_date+"', 'A', (select requested_by from rf_pn_header where pn_no = '"+lookUpPNno.getValue()+"' and status_id = 'A' limit 1), now(), \n" + 
					"'"+convo_detail+"',  NULLIF('"+rwC_ContactNo+"','null'),  NULLIF('"+rwC_Emailadd+"','null'), null, null);\n";


			db.executeUpdate(sql,true);
			db.commit();
		}
	}

	public class ForPreview implements Runnable{
		@Override
		public void run() {

			Map<String, Object> mapParameters = new HashMap<String, Object>();
			mapParameters.put("company", company);
			mapParameters.put("co_id", co_id); 
			mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
			mapParameters.put("dateFrom", dateFrom);
			mapParameters.put("dateTo", dateTo);
			mapParameters.put("printall", printall);
			mapParameters.put("pn_no", lookUpPNno.getValue().toString());

			FncReport.generateReport("/Reports/rptPN.jasper", "List of Accounts With Committments",  company, mapParameters);	

		}
	}

	public void getDetails(String entity_id, String proj_id, String pbl_id, Integer seqno,String buyerID){/*

		clear();
		clearpastdue();

		pgSelect  db = new pgSelect();
		db.select("select _get_client_name('"+entity_id+"'),\n" + 
				"get_project_name('"+proj_id+"') ,\n" + 
				"get_unit_description('"+proj_id+"','"+pbl_id+"'),\n" + 
				"_get_client_telephoneno('"+entity_id+"'), \n" + 
				"_get_client_mobileNo('"+entity_id+"') ");

		if (db.isNotNull()) {

			_entity_id = entity_id;
			_entity_name = (String) db.Result[0][0];

			if (method.getBuyerType(entity_id, proj_id, pbl_id, seqno).equals("04")) {
				table_name ="pagibig_due_accounts";
			}else{
				table_name ="ihf_due_accounts";
			}
			lookupPnNo.setValue(_pn_no);
			lookupClientName.setValue(entity_id);
			txtClientName.setText((String) db.Result[0][0]);
			txtProjID.setText(proj_id);
			txtProjDesc.setText((String) db.Result[0][1]);
			txtPBLID.setText(pbl_id);
			txtPBLDesc.setText((String) db.Result[0][2]);
			txtTelNo.setText((String) db.Result[0][3] == null ? "-" : (String) db.Result[0][3]);
			txtMobile.setText((String) db.Result[0][4]== null ? "-" :(String) db.Result[0][4]);

			btnClientInfo.setEnabled(true);

			db.select(method.displayPastDue(entity_id, proj_id, pbl_id, seqno, table_name));

			_months_pd = Integer.valueOf(db.Result[0][1].toString());
			_days_pd = Integer.valueOf(db.Result[0][0].toString());
			_part_id =  (String) (db.Result[0][8]== null ? "-" : db.Result[0][8]);    

			if (db.isNotNull()) {
				noofdays =  Integer.valueOf(db.Result[0][0].toString());

				if(noofdays == 0){
					lblNote_Client.setVisible(true);
					lblNote_Client.setText("<html><i><font color = \"black\">Note  :  <font color = \"red\">Client has no Past Due Payments");

				}else{
					txtNoDays.setText(db.Result[0][0].toString()+" days");
					txtMoUpdate.setText(db.Result[0][1].toString()+" month(s)");
					amountdue = (BigDecimal) db.Result[0][2];
					txtAmountDue.setValue(amountdue);
					txtLastDatePaid.setText(db.Result[0][3].toString());
					txtDefaultDate.setText(db.Result[0][4].toString());
					txtDPMA.setText(db.Result[0][5].toString());
					lblNote_Client.setVisible(false);

					db.select(method.displayPastDues(entity_id, proj_id, pbl_id, seqno));

					if (db.isNotNull()) {
						txtAmountPay.setValue((BigDecimal)db.Result[0][0]);
						dtePaymentDate.setDate((Date) db.Result[0][1]);

					}
				}
			}
		}

		rowHeaderPNH.setModel(new DefaultListModel());
		_FPromissoryNoteCommintment.displayPnHistory(modelPNHistory, rowHeaderPNH,  entity_id, proj_id, pbl_id, seqno);
		scrollPNHistory.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPNHistory.getRowCount())));

		btnState(true, false, false, true, false, false);

		tabCenter.setSelectedIndex(1);

	 */
		ifViewPnHistory = true;
		pgSelect db = new pgSelect();

		db.select("select _get_client_name('"+entity_id+"'),\n" + 
				"get_project_name('"+proj_id+"') ,\n" + 
				"get_unit_description('"+proj_id+"','"+pbl_id+"'),\n" + 
				"_get_client_telephoneno('"+entity_id+"'), \n" + 
				"_get_client_mobileNo('"+entity_id+"') ");

		if (db.isNotNull()) {

			client_name = db.Result[0][0].toString();
			unit = db.Result[0][2].toString();
			proj_name =  db.Result[0][1].toString();
			tel_no =db.Result[0][3] == null ? "" : db.Result[0][3].toString();
			mobile_no = db.Result[0][4] == null ? "" : db.Result[0][4].toString();

			lookUpClientID.setValue(entity_id);
			txtClientName.setText(client_name);

			txtProjID.setText(proj_id);
			txtProjDesc.setText(proj_name);
			txtPBLID.setText(pbl_id);
			txtPBLDesc.setText(unit);
			txtTelNo.setText(tel_no);
			txtMobile.setText(mobile_no);

			db.select(method.getIfPasDue(entity_id, proj_id, pbl_id, seqno, buyerID));

			if (db.isNotNull()) {

				monthsPD =  (Integer) (db.Result[0][0] == null ? 0 : db.Result[0][0]);
				daysPD = (Integer) (db.Result[0][1] == null ? 0 : db.Result[0][1]);
				amountDue = (BigDecimal) (db.Result[0][2] == null ? "0.00" :  db.Result[0][2]);
				lastPaidDate = (String) (db.Result[0][3] == null ? "": db.Result[0][3]);
				defaultdDate = (String) (db.Result[0][4] == null ? "" :db.Result[0][4]) ;


				if (monthsPD == 0) {
					setSecondaryTitle((String) "Client has no Past Due Payments");
					startTimerStatus();
				}else{
					if(timerStatus != null){
						timerStatus.stop();
					}

					setSecondaryTitle("Past Due Payments");
					startTimerStatus();
				}



				txtMoUpdate.setText(monthsPD +" month(s)");
				txtNoDays.setText(daysPD +" day(s)");
				txtDPMA.setText(payment_stage);
				txtAmountDue.setValue(amountDue);
				txtLastDatePaid.setText(lastPaidDate);
				txtDefaultDate.setText(defaultdDate);



				db.select(method.displayPastDues(entity_id, proj_id, pbl_id, seq_no));

				if (db.isNotNull()) {
					amountPay = (BigDecimal) db.Result[0][0];
					paymentDate = (Timestamp) db.Result[0][1];

					txtAmountPay.setValue(amountPay);
					dtePaymentDate.setDate(paymentDate);

				}
				btnState(false, false, true, true, false, false, false, true);
			}else{

				setSecondaryTitle((String) "Client has no Past Due Payments");
				startTimerStatus();

				btnState(false, false, true, false, false, false, false, true);
			}

			lookUpPNno.setEditable(false);

			rowHeaderPNH.setModel(new DefaultListModel());
			_FPromissoryNoteCommintment.displayPnHistory(modelPNHistory, rowHeaderPNH,  entity_id, proj_id,pbl_id, seqno );
			scrollPNHistory.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPNHistory.getRowCount())));
		}

		btnState(true, true, true, true, false, false, false, false);
		tabCenter.setSelectedIndex(1);
	}
	
	private Boolean isNull() {
		ArrayList<String> listNull = new ArrayList<String>();
		for(int x=0; x < modelCommit.getRowCount(); x++){
			listNull.add((String) modelCommit.getValueAt(x, 0));
		}
		return listNull.contains(null);
	}

}
