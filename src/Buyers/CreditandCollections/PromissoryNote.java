/**
 * 
 */
package Buyers.CreditandCollections;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpringLayout;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableColumn;

import org.jdesktop.swingx.JXFormattedTextField;
import org.jdesktop.swingx.table.NumberEditorExt;

import com.toedter.calendar.JTextFieldDateEditor;

import Buyers.ClientServicing.ClientInformation;
import Database.pgSelect;
import Database.pgUpdate;
import DateChooser.DateEvent;
import DateChooser.DateListener;
import DateChooser._JDateChooser;
import Dialogs.dlgDate;
import FormattedTextField._JXFormattedTextField;
import Functions.FncGlobal;
import Functions.FncReport;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.SpringUtilities;
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

/**
 * @author PC-112l
 *
 */

@SuppressWarnings({ "unchecked", "rawtypes" })
public class PromissoryNote extends _JInternalFrame implements _GUI,LookupListener,MouseMotionListener ,MouseListener,KeyListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */

	public static String title = "Promissory Note / Commitment";
	public static Dimension frameSize = new Dimension(900, 630);
	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	private JPanel pnlMain;
	private _FPromissoryNoteCommintment method = new _FPromissoryNoteCommintment();
	private JPanel pnlNorth_Main;
	private JPanel pnlCenter_Main;
	private JLabel lblCompany;
	private JLabel lblPnNo;
	private _JLookup lookupCompany;
	private JTextField txtCompany;
	private _JLookup lookupPnNo;
	private JPanel pnlCompany;
	private JPanel pnlNote;
	private JLabel lblNone;
	private JLabel lblNote_Client;
	private JPanel pnlDetails;
	private JPanel pnlDetails_Center;
	private JPanel pnlClient;
	private JLabel lblClientID;
	private JPanel pnlLookUp;
	private _JLookup lookupClientName;
	private JTextField txtClientName;
	private JPanel pnlDetailFill;
	private JPanel pnlProject;
	private JPanel pnlProject_West;
	private JLabel lblProject;
	private JLabel lblPBL;
	private JLabel lblTelNo;
	private JLabel lblCelNo;
	private JPanel pnlProject_Center;
	private JTextField txtProjID;
	private JTextField txtProjDesc;
	private JTextField txtPBLID;
	private JTextField txtPBLDesc;
	private JTextField txtTelNo;
	private JTextField txtMobile;
	private JPanel pnlPastDue;
	private JPanel pnlPastDue_West;
	private JLabel lblMoUpdate;
	private JLabel lblNoDaysPD;
	private JLabel lblDPMA;
	private JPanel pnlPastDue_Center;
	private JTextField txtMoUpdate;
	private JTextField txtNoDays;
	private JTextField txtDPMA;
	private JPanel pnlPastDue_Amt;
	private JPanel pnlPastDue_Amt_West;
	private JLabel lblAmountDue;
	private JLabel lblLastDatePaid;
	private JLabel lblDefaultDate;
	private JPanel pnlPastDue_Amt_Center;
	private _JXFormattedTextField txtAmountDue;
	private JTextField txtLastDatePaid;
	private JTextField txtDefaultDate;
	private JPanel pnlViewDetails;
	private _JButton btnClientInfo;
	private JPanel pnlViewClient;
	private JPanel pnlPNCommitment;
	private JPanel pnlCenter_C;
	private _JDateChooser dteCommitDate;
	private DefaultComboBoxModel cmbMonthUpdateModel;
	private JComboBox cmbMonthUpdate;
	private _JScrollPaneMain scrollCommit;
	private model_PN_Commit modelCommit;
	private _JTableMain tblCommit;
	private JList rowHeaderCommit;
	private _JScrollPaneTotal scrollCommitAmountTotal;
	private model_PN_Commit modelCommitAmountTotal;
	private _JTableTotal tblCommitAmountTotal;
	private JButton btnAddRow;
	private JButton btnRemoveRow;
	private JPanel pnlSouth_Main;
	private _JButton btnAdd;
	private _JButton btnEdit;
	private _JButton btnSave;
	private _JButton btnPost;
	private _JButton btnPreview;
	private _JButton btnCancel;
	private ButtonGroup grpButton = new ButtonGroup();
	private JPanel pnlCenter_S;
	private JPanel pnlCenter_PNPayment_West;
	//private JLabel lblDateSubmitted;
	private JLabel lblSignedBy;
	private JLabel lblRemarks;
	private JLabel lblApproved;
	private JPanel pnlCenter_PNPayment_East;
	private JTextField txtSignedby;
	private JTextField txtRemarks;
	private _JLookup lookupApproved;
	private JTextField txtApproved;
	private JPanel pnlCommitmentStatus;
	private String co_id;
	private String company;
	private String company_logo;
	private String _pn_no;
	private Integer noofdays;
	private String _entity_id;
	private String _proj_id;
	private String _pbl_id;
	private Integer _seq_no;
	private String _entity_name;
	private String table_name;
	private Integer _months_pd;
	private Integer _days_pd;
	private String _part_id;
	private BigDecimal amountdue;
	//private _JDateChooser dtePaidDate;
	//private _JScrollPaneMain scrollCommitted;
	///private model_commit_pmt modelCommitted;
	//private _JScrollPaneTotal scrollCommittedAmountTotal;
	//private _JTableMain tblCommittedStatus;
	//private model_commit_pmt modelCommittedAmountTotal;

	//private JList rowHeaderCommitted;
	//private _JTableTotal tblCommittedAmountTotal;
	private JPanel pnlCommitmentStatus_S;
	private JPanel pnlPrint;
	private _JButton btnPrintForm;
	private Boolean printall;
	private Date dateFrom;
	private Date dateTo;
	private JLabel lblPaymentDate;
	private JLabel lblPaymentAmount;
	private _JDateChooser dtePaymentDate;
	private _JXFormattedTextField txtPaymentAmt;
	private JPanel pnlDPMA;
	private _JXFormattedTextField txtAmountPay;
	private Date commit_date;
	private BigDecimal commit_amount;
	private Date paymentdate;
	private JLabel lblRwClient;
	private JTextField txtRwClient;
	private JLabel lblContact_No;
	private JLabel lblEmailAdd;
	private JTextField txtContactNo;
	private JTextField txtEmailAdd;
	private _JTabbedPane tabCenter;
	private JPanel pnlPnHistory_Center;
	private JScrollPane scrollPNHistory;
	private model_PN_History modelPNHistory;
	private _JTableMain tblPNHistory;
	private JList rowHeaderPNH;
	private Boolean ifviewing = false;

	public PromissoryNote() {
		super(title, true, true, true, true);
		initGUI();
		formload();
		getDefaultCompany();
	}

	public PromissoryNote(String entity_id, String proj_id,String pbl_id ,Integer seq_no) {
		super(title, true, true, true, true);
		initGUI();
		formload();
		getDefaultCompany();

		ifviewing  = true;
		getDetails(entity_id, proj_id, pbl_id, seq_no );

	}
	/**
	 * @param title
	 */
	public PromissoryNote(String title) {
		super(title);
		initGUI();
		formload();
		getDefaultCompany();
	}

	/**
	 * @param title
	 * @param resizable
	 * @param closable 
	 * @param maximizable
	 * @param iconifiable
	 */
	public PromissoryNote(String title, boolean resizable, boolean closable,
			boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);

		initGUI();
		formload();
		getDefaultCompany();
	}

	@Override
	public void initGUI() {
		{
			this.setTitle(title);
			this.setSize(frameSize);
			this.setPreferredSize(frameSize);
			getContentPane().setLayout(new BorderLayout());

			{
				pnlMain = new JPanel();
				getContentPane().add(pnlMain,BorderLayout.CENTER);
				pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
				pnlMain.setLayout(new BorderLayout(5,5));
				{
					pnlNorth_Main = new JPanel(new BorderLayout(3, 3));
					pnlMain.add(pnlNorth_Main,BorderLayout.NORTH);
					pnlNorth_Main.setPreferredSize(new Dimension(pnlMain.getWidth(), 260));
					{
						pnlCompany = new JPanel(new BorderLayout(3, 3));
						pnlNorth_Main.add(pnlCompany,BorderLayout.NORTH);
						pnlCompany.setPreferredSize(new Dimension(pnlMain.getWidth(), 50));
						{
							JPanel pnlLabel = new JPanel(new GridLayout(2, 1, 3, 3));
							pnlCompany.add(pnlLabel,BorderLayout.WEST);

							{
								lblCompany = new JLabel("Company");
								pnlLabel.add(lblCompany);
								//lblCompany.setPreferredSize(new Dimension(74, 20));
							}
							{
								lblPnNo = new JLabel("PN no.");
								pnlLabel.add(lblPnNo);
								//lblPnNo.setPreferredSize(new Dimension(74, 20));
							}
						}
						{
							JPanel pnlLookup = new JPanel(new GridLayout(2, 1, 3, 3));
							pnlCompany.add(pnlLookup,BorderLayout.CENTER);

							{
								JPanel _pnlCompany = new JPanel(new BorderLayout(3,3));
								pnlLookup.add(_pnlCompany);
								{
									{

										lookupCompany = new _JLookup("", "Company", 0) ; /// XXX lookupCompany 
										_pnlCompany.add(lookupCompany,BorderLayout.WEST);
										lookupCompany.addLookupListener(this);
										lookupCompany.setReturnColumn(0);
										lookupCompany.setLookupSQL(method.getCompany());
										lookupCompany.setPreferredSize(new Dimension(100, 20));
									}
									{
										txtCompany = new JTextField();
										_pnlCompany.add(txtCompany,BorderLayout.CENTER);
										txtCompany.setEditable(false);
										txtCompany.setPreferredSize(new Dimension(100, 20));
									} 

								}
							}
							{

								JPanel _pnlPnNo = new JPanel(new BorderLayout(3,3));
								pnlLookup.add(_pnlPnNo);
								{

									{
										lookupPnNo = new _JLookup("", "", 0) ; /// XXX  lookupPnNo
										_pnlPnNo.add(lookupPnNo,BorderLayout.WEST);
										lookupPnNo.addLookupListener(this);
										lookupPnNo.setReturnColumn(0);
										lookupPnNo.setLookupSQL(method.LookupPn_No());
										lookupPnNo.setPreferredSize(new Dimension(150, 20));
									}
									{
										lblNone = new JLabel();
										_pnlPnNo.add(lblNone,BorderLayout.CENTER);
										lblNone.setPreferredSize(new Dimension(100, 20));
									} 
								}
							}
						}
						{
							pnlNote = new JPanel(new BorderLayout(3,3));
							pnlCompany.add(pnlNote,BorderLayout.EAST);
							pnlNote.setPreferredSize(new Dimension(280, 40));
							{
								{
									lblNote_Client = new JLabel("<html><i><font color = \"black\">Note  :  <font color = \"red\">Client has no Past Due Payments");
									pnlNote.add(lblNote_Client,BorderLayout.SOUTH);
									lblNote_Client.setVisible(false);
									lblNote_Client.setHorizontalAlignment(JLabel.RIGHT);
								}
							}
						}
					}
					{
						pnlDetails = new JPanel(new BorderLayout(3, 3));
						pnlNorth_Main.add(pnlDetails,BorderLayout.CENTER);

						tabCenter = new _JTabbedPane();
						pnlDetails.add(tabCenter, BorderLayout.CENTER);			
						{
							pnlDetails_Center = new  JPanel();
							tabCenter.addTab(" Details ", null, pnlDetails_Center, null);
							pnlDetails_Center.setPreferredSize(new Dimension(pnlMain.getWidth(), 250));
							pnlDetails_Center.setLayout(new BorderLayout(5,5));
							{

								pnlClient = new JPanel();
								pnlDetails_Center.add(pnlClient, BorderLayout.NORTH);
								pnlClient.setPreferredSize(new Dimension(600, 27));
								pnlClient.setLayout(new SpringLayout());

								{
									lblClientID = new JLabel("Client ID :");
									pnlClient.add(lblClientID);
									lblClientID.setPreferredSize(new Dimension(74, 25));
								}
								{
									pnlLookUp = new JPanel(new BorderLayout(5,5));
									pnlClient.add(pnlLookUp,BorderLayout.CENTER);
									{


										lookupClientName = new _JLookup("", "Client ID", 0) ; /// XXX lookupClientName 
										pnlLookUp.add(lookupClientName,BorderLayout.WEST);
										lookupClientName.addLookupListener(this);
										lookupClientName.setLookupSQL(method.getClient());
										lookupClientName.setPreferredSize(new Dimension(100, 25));
									}
									{
										txtClientName = new JTextField();
										pnlLookUp.add(txtClientName);
										txtClientName.setEditable(false);
									}
								}

								SpringUtilities.makeCompactGrid(pnlClient, 1, 2, 2, 2, 2, 2, false);

							}
							{
								pnlDetailFill = new JPanel();
								pnlDetailFill.setLayout(new SpringLayout());
								pnlDetails_Center.add(pnlDetailFill, BorderLayout.CENTER);
								pnlDetailFill.setPreferredSize(new Dimension(510, 90));
								{
									pnlProject = new JPanel(new BorderLayout(5, 5));
									pnlDetailFill.add(pnlProject, BorderLayout.WEST);
									pnlProject.setPreferredSize(new Dimension(300, 20));
									{
										pnlProject_West = new JPanel(new GridLayout(4, 1, 2, 2));
										pnlProject.add(pnlProject_West, BorderLayout.WEST);
										pnlProject_West.setPreferredSize(new Dimension(70, 30));
										{
											{
												lblProject = new JLabel("Project");
												pnlProject_West.add(lblProject);
											}
											{
												lblPBL = new JLabel("Ph/Bl/Lt");
												pnlProject_West.add(lblPBL);
											}
											{
												lblTelNo = new JLabel("Tel. No. :");
												pnlProject_West.add(lblTelNo);
											}
											{
												lblCelNo = new JLabel("Mobile No :");
												pnlProject_West.add(lblCelNo);
											}
										}
										{

											pnlProject_Center = new JPanel(new GridLayout(4, 1, 2, 2));
											pnlProject.add(pnlProject_Center, BorderLayout.CENTER);
											{
												JPanel pnlProj_text = new JPanel(new BorderLayout(5, 5));
												pnlProject_Center.add(pnlProj_text);
												{
													{
														txtProjID = new JTextField();
														pnlProj_text.add(txtProjID, BorderLayout.WEST);
														txtProjID.setPreferredSize(new Dimension(100, 0));
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
												pnlProject_Center.add(pnlPBL);
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
														txtPBLDesc.setPreferredSize(new Dimension(500, 0));
														txtPBLDesc.setEditable(false);
													}
												}
											}
											{
												JPanel pnlTelNo = new JPanel(new BorderLayout(5, 5));
												pnlProject_Center.add(pnlTelNo);
												pnlTelNo.setLayout(new BorderLayout());
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
												JPanel pnlMobNo = new JPanel(new BorderLayout(5, 5));
												pnlProject_Center.add(pnlMobNo);
												pnlMobNo.setLayout(new BorderLayout());
												{
													{
														txtMobile = new JTextField();
														pnlMobNo.add(txtMobile, BorderLayout.CENTER);
														txtMobile.setEditable(false);
														txtMobile.setHorizontalAlignment(JTextField.CENTER);
													}
												}
											}
										}
									}

								}
								{
									pnlPastDue = new JPanel(new BorderLayout(5, 5));
									pnlDetailFill.add(pnlPastDue, BorderLayout.CENTER);
									pnlPastDue.setPreferredSize(new Dimension(150, 0));
									{
										pnlPastDue_West = new JPanel(new GridLayout(3, 1, 3, 3));
										pnlPastDue.add(pnlPastDue_West, BorderLayout.WEST);
										{
											lblMoUpdate = new JLabel("No To Update");
											pnlPastDue_West.add(lblMoUpdate);
										}
										{
											lblNoDaysPD = new JLabel("No of Days PD");
											pnlPastDue_West.add(lblNoDaysPD);
										}
										{
											lblDPMA = new JLabel("DP / MA Stage");
											pnlPastDue_West.add(lblDPMA);
										}

									}
									{
										pnlPastDue_Center = new JPanel(new GridLayout(3, 1, 2, 2));
										pnlPastDue.add(pnlPastDue_Center, BorderLayout.CENTER);
										{
											txtMoUpdate = new JTextField();
											pnlPastDue_Center.add(txtMoUpdate, BorderLayout.CENTER);
											txtMoUpdate.setEditable(false);
											txtMoUpdate.setHorizontalAlignment(JTextField.CENTER);
										}
										{

											txtNoDays = new JTextField();
											pnlPastDue_Center.add(txtNoDays, BorderLayout.CENTER);
											txtNoDays.setEditable(false);
											txtNoDays.setHorizontalAlignment(JTextField.CENTER);

										}
										{
											txtDPMA = new JTextField();
											pnlPastDue_Center.add(txtDPMA, BorderLayout.CENTER);
											txtDPMA.setEditable(false);
											txtDPMA.setHorizontalAlignment(JTextField.CENTER);

										}
									}
								}
								{
									pnlPastDue_Amt = new JPanel(new BorderLayout(5, 5));
									pnlDetailFill.add(pnlPastDue_Amt, BorderLayout.EAST);
									pnlPastDue_Amt.setPreferredSize(new Dimension(150, 0));
									{
										pnlPastDue_Amt_West = new JPanel(new GridLayout(3, 1, 3, 3));
										pnlPastDue_Amt.add(pnlPastDue_Amt_West, BorderLayout.WEST);
										{
											lblAmountDue = new JLabel("Amount Due");
											pnlPastDue_Amt_West.add(lblAmountDue);
										}
										{
											lblLastDatePaid = new JLabel("Last Date Paid");
											pnlPastDue_Amt_West.add(lblLastDatePaid);
										}
										{
											lblDefaultDate = new JLabel("Default Date");
											pnlPastDue_Amt_West.add(lblDefaultDate);
										}

									}

									pnlPastDue_Amt_Center = new JPanel(new GridLayout(3, 1, 2, 2));
									pnlPastDue_Amt.add(pnlPastDue_Amt_Center, BorderLayout.CENTER);
									{
										{
											txtAmountDue = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
											pnlPastDue_Amt_Center.add(txtAmountDue, BorderLayout.CENTER);
											txtAmountDue.	setFormatterFactory(_JXFormattedTextField.DECIMAL);
											txtAmountDue.setEditable(false);
										}
										{

											txtLastDatePaid = new JTextField();
											pnlPastDue_Amt_Center.add(txtLastDatePaid, BorderLayout.CENTER);
											txtLastDatePaid.setEditable(false);
											txtLastDatePaid.setHorizontalAlignment(JTextField.CENTER);

										}
										{

											txtDefaultDate = new JTextField();
											pnlPastDue_Amt_Center.add(txtDefaultDate, BorderLayout.CENTER);
											txtDefaultDate.setEditable(false);
											txtDefaultDate.setHorizontalAlignment(JTextField.CENTER);

										}
									}
								}
								SpringUtilities.makeCompactGrid(pnlDetailFill, 1, 3, 3, 3, 3, 3, false);

							}
						}
						{

							pnlPnHistory_Center = new  JPanel();
							tabCenter.addTab(" PN History ", null, pnlPnHistory_Center, null);
							pnlPnHistory_Center.setPreferredSize(new Dimension(pnlMain.getWidth(), 250));
							pnlPnHistory_Center.setLayout(new BorderLayout(5,5));
							{
								JPanel pnlPnTable = new JPanel(new BorderLayout(3, 3));
								pnlPnHistory_Center.add(pnlPnTable,BorderLayout.CENTER);

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
										tblPNHistory.addMouseListener(this);
										tblPNHistory.packAll();

										/** Repaint for Highlight **/
										tblPNHistory.getTableHeader().addMouseListener(new MouseAdapter() {
											public void mouseClicked(MouseEvent evt) {
												tblPNHistory.repaint();
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
					{
						pnlViewDetails = new JPanel(new BorderLayout(3, 3));
						pnlNorth_Main.add(pnlViewDetails,BorderLayout.SOUTH);
						pnlViewDetails.setPreferredSize(new Dimension(0, 40));
						{
							pnlViewClient = new JPanel(new BorderLayout(5, 5));
							pnlViewDetails.add(pnlViewClient,BorderLayout.WEST);
							pnlViewClient.setPreferredSize(new Dimension(200, 30));
							{
								btnClientInfo = new _JButton("View Client Info.");
								pnlViewClient.add(btnClientInfo,BorderLayout.CENTER);
								btnClientInfo.addActionListener(this);
							}
						}
						{

							pnlPrint = new JPanel(new BorderLayout(5, 5));
							pnlViewDetails.add(pnlPrint,BorderLayout.EAST);
							pnlPrint.setPreferredSize(new Dimension(200, 30));
							{
								btnPrintForm = new _JButton("Print Form");
								pnlPrint.add(btnPrintForm,BorderLayout.CENTER);
								btnPrintForm.addActionListener(this);
							}
						}
					}
				}
				{
					pnlCenter_Main = new JPanel(new BorderLayout(3, 3));
					pnlMain.add(pnlCenter_Main,BorderLayout.CENTER);
					pnlCenter_Main.setPreferredSize(new Dimension(pnlMain.getWidth(), 100));

					{
						pnlPNCommitment = new  JPanel();
						pnlCenter_Main.add(pnlPNCommitment,BorderLayout.CENTER);
						pnlPNCommitment.setBorder(components.JTBorderFactory.createTitleBorder("Promissory Note - Payment Plan"));// XXX
						pnlPNCommitment.setPreferredSize(new Dimension(650, 20));
						pnlPNCommitment.setLayout(new BorderLayout(5,5));

						{
							pnlDPMA = new JPanel(new BorderLayout(5, 5)); 
							pnlPNCommitment.add(pnlDPMA, BorderLayout.NORTH);
							pnlDPMA.setPreferredSize(new Dimension(10, 25));	
							{

								lblDPMA = new JLabel("Amount to Pay");
								pnlDPMA.add(lblDPMA, BorderLayout.WEST);
								lblDPMA.setPreferredSize(new Dimension(133, 25));

							}
							{
								txtAmountPay = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
								pnlDPMA.add(txtAmountPay, BorderLayout.CENTER);
								txtAmountPay.setFormatterFactory(_JXFormattedTextField.DECIMAL);
								txtAmountPay.setEditable(false);
							}
						}
						{

							pnlCenter_PNPayment_West = new JPanel(new GridLayout(8, 1, 5, 5));
							pnlPNCommitment.add(pnlCenter_PNPayment_West, BorderLayout.WEST);

							{
								lblPaymentAmount= new JLabel("Payment Amount");
								pnlCenter_PNPayment_West.add(lblPaymentAmount);
							}
							{
								lblPaymentDate = new JLabel("Payment Date");
								pnlCenter_PNPayment_West.add(lblPaymentDate);
							}
							{/*
								lblDateSubmitted = new JLabel("Date Submitted");
								pnlCenter_PNPayment_West.add(lblDateSubmitted);
							 */}
							{
								lblSignedBy = new JLabel("Signed By");
								pnlCenter_PNPayment_West.add(lblSignedBy);
							}
							{
								lblRwClient = new JLabel("Relationship w/ Client");
								pnlCenter_PNPayment_West.add(lblRwClient);
							}
							{
								lblContact_No = new JLabel("Contact No.");
								pnlCenter_PNPayment_West.add(lblContact_No);
							}
							{
								lblEmailAdd = new JLabel("Email Add.");
								pnlCenter_PNPayment_West.add(lblEmailAdd);
							}
							{
								lblRemarks = new JLabel("Remarks");
								pnlCenter_PNPayment_West.add(lblRemarks);
							}
							{
								lblApproved = new JLabel("Approved by :");
								pnlCenter_PNPayment_West.add(lblApproved);
							}

						}
						{
							pnlCenter_PNPayment_East = new JPanel(new GridLayout(8, 1, 5, 5));
							pnlPNCommitment.add(pnlCenter_PNPayment_East, BorderLayout.CENTER);

							{
								txtPaymentAmt = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
								pnlCenter_PNPayment_East.add(txtPaymentAmt, BorderLayout.CENTER);
								txtPaymentAmt.setFormatterFactory(_JXFormattedTextField.DECIMAL);

							}
							{
								dtePaymentDate = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
								pnlCenter_PNPayment_East.add(dtePaymentDate);
								dtePaymentDate.setDate(null);
								dtePaymentDate.addDateListener(new DateListener() {

									@Override
									public void datePerformed(DateEvent event) {/*
										String day = "";	
										day = method.dateFormat_day(dtePaymentDate.getDate());

										System.out.println("DATE " +day );
										if (day == null) {

										}else{
											if (!(day.equals("07") || day.equals("14") || day.equals("21") || day.equals("28"))) {
												dtePaymentDate.setDate(paymentdate);
												JOptionPane.showMessageDialog(null,"Please select [07], [14], [21], [28] for due day","Date", JOptionPane.INFORMATION_MESSAGE);
												return;
											}

										}

									 */}
								});
								/*dtePaymentDate.addPropertyChangeListener(new PropertyChangeListener() {

									@Override
									public void propertyChange(PropertyChangeEvent evt) {


									}
								});;*/
							}
							{/*
								dteDateSubmitted = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
								pnlCenter_PNPayment_East.add(dteDateSubmitted);
								dteDateSubmitted.setDate(null);
							 */}
							{
								txtSignedby = new JTextField();
								pnlCenter_PNPayment_East.add(txtSignedby);
							}
							{
								txtRwClient = new JTextField();
								pnlCenter_PNPayment_East.add(txtRwClient);
							}
							{
								txtContactNo = new JTextField();
								pnlCenter_PNPayment_East.add(txtContactNo);
							}
							{
								txtEmailAdd = new JTextField();
								pnlCenter_PNPayment_East.add(txtEmailAdd);
							}
							{
								txtRemarks = new JTextField();
								pnlCenter_PNPayment_East.add(txtRemarks);
							}
							{
								JPanel pnlApproved = new JPanel(new BorderLayout(5,5));
								pnlCenter_PNPayment_East.add(pnlApproved,BorderLayout.CENTER);
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

						{/*
							pnlCenter_C = new JPanel(new BorderLayout(3, 3));
							pnlPNCommitment.add(pnlCenter_C, BorderLayout.CENTER);
							pnlCenter_C.setBorder(components.JTBorderFactory.createTitleBorder("Commitment"));// XXX
							pnlCenter_C.setPreferredSize(new Dimension(0, 150));
							{
								dteCommitDate = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
								pnlCenter_C.add(dteCommitDate);
								dteCommitDate.setDate(null);
								((JTextFieldDateEditor)dteCommitDate.getDateEditor()).setEditable(false);
								dteCommitDate.getCalendarButton().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
								((JTextFieldDateEditor)dteCommitDate.getDateEditor()).getDocument().addDocumentListener(new DocumentListener() {
									public void insertUpdate(DocumentEvent evt) {
										SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");

										modelCommit.setValueAt(""+sdf.format(dteCommitDate.getDate()),tblCommit.getSelectedRow(),0);
									}
									public void changedUpdate(DocumentEvent e) { }
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
								pnlCenter_C.add(scrollCommit, BorderLayout.CENTER);
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
									tblCommit.addMouseMotionListener(this);
									tblCommit.addMouseListener(this);
									tblCommit.hideColumns("Balance");

									TableColumn monthcolumn = tblCommit.getColumnModel().getColumn(2);
									monthcolumn.setCellEditor(new DefaultCellEditor(cmbMonthUpdate));
									tblCommit.addPropertyChangeListener(new PropertyChangeListener() {//XXX Work Items
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
														//method.totalCommitAmount(modelCommit, modelCommitAmountTotal);
													}
												}
											}
										}
									});

						 *//** Repaint for Highlight **//*
									tblCommit.getTableHeader().addMouseListener(new MouseAdapter() {
										public void mouseClicked(MouseEvent evt) {
											tblCommit.repaint();
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
									pnlCenter_C.add(scrollCommitAmountTotal, BorderLayout.SOUTH);
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
						  */}
						{

							pnlCenter_S = new JPanel(new BorderLayout(3, 3));
							pnlPNCommitment.add(pnlCenter_S, BorderLayout.SOUTH);
							//pnlCenter_S.setBorder(components.JTBorderFactory.createTitleBorder("Commitment"));// XXX

						}

					}
					{
						pnlCommitmentStatus = new  JPanel();
						pnlCenter_Main.add(pnlCommitmentStatus,BorderLayout.EAST);
						pnlCommitmentStatus.setBorder(components.JTBorderFactory.createTitleBorder("Promissory Note - Commitment"));// XXX
						pnlCommitmentStatus.setPreferredSize(new Dimension(420, 25));
						pnlCommitmentStatus.setLayout(new BorderLayout(5,5));

						{/*


							pnlCommitmentStatus_C = new JPanel(new BorderLayout(3, 3));
							pnlCommitmentStatus.add(pnlCommitmentStatus_C, BorderLayout.CENTER);
							pnlCommitmentStatus_C.setBorder(components.JTBorderFactory.createTitleBorder("Committed Status"));// XXX
							pnlCommitmentStatus_C.setPreferredSize(new Dimension(0, 150));
							{
								dtePaidDate = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
								pnlCommitmentStatus_C.add(dtePaidDate);
								dtePaidDate.setDate(null);
								((JTextFieldDateEditor)dtePaidDate.getDateEditor()).setEditable(false);
								dtePaidDate.getCalendarButton().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
								((JTextFieldDateEditor)dtePaidDate.getDateEditor()).getDocument().addDocumentListener(new DocumentListener() {
									public void insertUpdate(DocumentEvent evt) {
										SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");
										if (modelCommitted.getValueAt(tblCommittedStatus.getSelectedRow(),0).equals(true)) {
											modelCommitted.setValueAt(""+sdf.format(dtePaidDate.getDate()),tblCommittedStatus.getSelectedRow(),3);	
										}

									}
									public void changedUpdate(DocumentEvent e) { }
									public void removeUpdate(DocumentEvent e) { }
								});
							}
							{

								scrollCommitted = new _JScrollPaneMain();
								pnlCommitmentStatus_C.add(scrollCommitted, BorderLayout.CENTER);
								scrollCommitted.addMouseListener(new MouseAdapter() {
									public void mouseClicked(MouseEvent e) {
										tblCommittedStatus.clearSelection();
									}
								});
								{

									modelCommitted = new model_commit_pmt();
									modelCommitted.addTableModelListener(new TableModelListener() {
										public void tableChanged(TableModelEvent e) {
											//Addition of rows

											if(e.getType() == 1){
												((DefaultListModel)rowHeaderCommitted.getModel()).addElement(modelCommitted.getRowCount());

												if(modelCommitted.getRowCount() == 0){
													rowHeaderCommitted.setModel(new DefaultListModel());
												}
											}
										}
									});

									tblCommittedStatus = new _JTableMain(modelCommitted);
									scrollCommitted.setViewportView(tblCommittedStatus);
									modelCommitted.setEditable(true);
									tblCommittedStatus.setHorizontalScrollEnabled(true);
									tblCommittedStatus.addMouseMotionListener(this);
									tblCommittedStatus.addMouseListener(this);
									tblCommittedStatus.packAll();
									tblCommittedStatus.hideColumns("Balance");
									tblCommittedStatus.addPropertyChangeListener(new PropertyChangeListener() {//XXX Work Items
										public void propertyChange(PropertyChangeEvent arg0) {
											_JTableMain table = (_JTableMain) arg0.getSource();
											String propertyName = arg0.getPropertyName();

											if (propertyName.equals("tableCellEditor")) {
												int column = table.convertColumnIndexToModel(table.getEditingColumn());
												int row = table.getEditingRow();

												//int total_amount = table.convertColumnIndexToModel(table.getColumnModel().getColumnIndex("Committed Amount"));
												//int balance = table.convertColumnIndexToModel(table.getColumnModel().getColumnIndex("Balance"));
												int amount_paid = table.convertColumnIndexToModel(table.getColumnModel().getColumnIndex("Amount Paid"));
												if (column != -1 && column != 3 && modelCommitted.getColumnClass(column).equals(BigDecimal.class)) {
													Object oldValue = null;
													try {
														oldValue = ((NumberEditorExt) arg0.getOldValue()).getCellEditorValue();
													} catch (NullPointerException e) {
													}

													if (oldValue != null) {
														if (oldValue instanceof Double) {
															table.setValueAt(new BigDecimal((Double) oldValue), row, column);
															//	modelCommitted.setValueAt(new BigDecimal((Double) oldValue), row, total_amount);
															modelCommitted.setValueAt(new BigDecimal((Double) oldValue), row, amount_paid);

														}
														if (oldValue instanceof Long) {
															table.setValueAt(new BigDecimal((Long) oldValue), row, column);
															//	modelCommitted.setValueAt(new BigDecimal((Long) oldValue), row, total_amount);
															modelCommitted.setValueAt(new BigDecimal((Long) oldValue), row, amount_paid);
														}
														computeTotal();
														computeTotalCommitted();
														//method.totalCommitAmount(modelCommit, modelCommitAmountTotal);
													}
												}
											}
										}
									});

						 *//** Repaint for Highlight **//*
									tblCommittedStatus.getTableHeader().addMouseListener(new MouseAdapter() {
										public void mouseClicked(MouseEvent evt) {
											tblCommittedStatus.repaint();
										}
									});
								}
								{

									rowHeaderCommitted = tblCommittedStatus.getRowHeader();
									rowHeaderCommitted.setModel(new DefaultListModel());
									scrollCommitted.setRowHeaderView(rowHeaderCommitted);
									scrollCommitted.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
								}
								{

									scrollCommittedAmountTotal = new _JScrollPaneTotal(scrollCommitted);
									pnlCommitmentStatus_C.add(scrollCommittedAmountTotal, BorderLayout.SOUTH);
									{
										modelCommittedAmountTotal = new model_commit_pmt();
										modelCommittedAmountTotal.addRow(new Object[] {null, "Total",0.00, null, 0.00 ,  null});

										tblCommittedAmountTotal = new _JTableTotal(modelCommittedAmountTotal, tblCommittedStatus);
										scrollCommittedAmountTotal.setViewportView(tblCommittedAmountTotal);
										tblCommittedAmountTotal.hideColumns("Balance");
										tblCommittedAmountTotal.setTotalLabel(1);
									}
									///scrollCommittedAmountTotal.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, displayTableNavigation());
								}
							}


						  */

							pnlCenter_C = new JPanel(new BorderLayout(3, 3));
							pnlCommitmentStatus.add(pnlCenter_C, BorderLayout.CENTER);
							//pnlCenter_C.setBorder(components.JTBorderFactory.createTitleBorder("Commitment"));// 
							pnlCenter_C.setPreferredSize(new Dimension(0, 150));
							{
								dteCommitDate = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
								pnlCenter_C.add(dteCommitDate);
								dteCommitDate.setDate(null);
								((JTextFieldDateEditor)dteCommitDate.getDateEditor()).setEditable(false);
								dteCommitDate.getCalendarButton().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
								((JTextFieldDateEditor)dteCommitDate.getDateEditor()).getDocument().addDocumentListener(new DocumentListener() {
									public void insertUpdate(DocumentEvent evt) {/*
										SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");

										String day = "";	
										day = method.dateFormat_day(dteCommitDate.getDate());

										System.out.println("DATE " +day );
										if (day == null) {

										}else{
											if (!(day.equals("07") || day.equals("14") || day.equals("21") || day.equals("28"))) {
												JOptionPane.showMessageDialog(null,"Please select [07], [14], [21], [28] for due day","Date", JOptionPane.INFORMATION_MESSAGE);
												return;
											}else{
												modelCommit.setValueAt(""+sdf.format(dteCommitDate.getDate()),tblCommit.getSelectedRow(),0);
											}
										}
									 */
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
								pnlCenter_C.add(scrollCommit, BorderLayout.CENTER);
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
									tblCommit.addMouseMotionListener(this);
									tblCommit.addMouseListener(this);
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
								}
								{

									rowHeaderCommit = tblCommit.getRowHeader();
									rowHeaderCommit.setModel(new DefaultListModel());
									scrollCommit.setRowHeaderView(rowHeaderCommit);
									scrollCommit.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
								}
								{

									scrollCommitAmountTotal = new _JScrollPaneTotal(scrollCommit);
									pnlCenter_C.add(scrollCommitAmountTotal, BorderLayout.SOUTH);
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
						{

							pnlCommitmentStatus_S = new JPanel(new BorderLayout(3, 3));
							pnlCommitmentStatus.add(pnlCommitmentStatus_S, BorderLayout.SOUTH);
						}
					}
				}
				{

					pnlSouth_Main = new JPanel(new GridLayout(1, 6, 3, 3));
					pnlMain.add(pnlSouth_Main,BorderLayout.SOUTH);
					pnlSouth_Main.setPreferredSize(new Dimension(400, 40));
					{
						{
							btnAdd = new _JButton("Add New");
							pnlSouth_Main.add(btnAdd);
							btnAdd.setPreferredSize(new Dimension(200, 40));
							btnAdd.setActionCommand("Add");
							grpButton.add(btnAdd);
							btnAdd.addActionListener(this);
						}
						{
							btnEdit = new _JButton("Edit");
							pnlSouth_Main.add(btnEdit);
							btnEdit.setPreferredSize(new Dimension(200, 40));
							btnEdit.setActionCommand("Edit");
							grpButton.add(btnEdit);
							btnEdit.addActionListener(this);
						}
						{
							btnSave = new _JButton("Save");
							pnlSouth_Main.add(btnSave);
							btnSave.setPreferredSize(new Dimension(200, 40));
							btnSave.addActionListener(this);
						}
						{

							btnPost = new _JButton("Post");
							pnlSouth_Main.add(btnPost);
							btnPost.setPreferredSize(new Dimension(200, 40));
							btnPost.setActionCommand("Post");
							grpButton.add(btnPost);
							btnPost.addActionListener(this);

						}
						{
							btnPreview = new _JButton("<html><b><p align=\"center\">Preview <br>(PN Summary)</p></b></html>");
							pnlSouth_Main.add(btnPreview);
							btnPreview.setPreferredSize(new Dimension(200, 40));
							btnPreview.addActionListener(this);

						}
						{
							btnCancel = new _JButton("Cancel");
							pnlSouth_Main.add(btnCancel);
							btnCancel.setPreferredSize(new Dimension(200, 40));
							btnCancel.addActionListener(this);
						}
					}
				}
			}
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) { // XXX actionPerformed
		String actionCommand = e.getActionCommand();
		if (e.getSource().equals(btnClientInfo)) {

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
						ClientInformation ci = new ClientInformation("Client Information", _entity_id, _entity_name);
						ci.setVisible(true);
						FncGlobal.homeMDI.addWindow(ci);
					}
					FncGlobal.stopProgress();
				}
			};
			FncGlobal.loadThread("", run);
		}


		if (e.getSource().equals(btnAdd)) {

			grpButton.setSelected(btnAdd.getModel(), true);

			if (ifviewing) {
				lookupClientName.setEditable(false);

				_setDisable();
				btnPrintForm.setEnabled(true);
			}else{
				clear();
				lookupClientName.setEditable(true);
			}


			_pn_no  = method.getPN_no();
			lookupPnNo.setValue(_pn_no);
			lookupPnNo.setEditable(false);
			this.setComponentsEnabled(pnlDetails_Center, true);
			btnState(false, false, true, true, false, false);
			modelCommit.setEditable(true);

			tabCenter.setSelectedIndex(0);
		}

		if (e.getSource().equals(btnSave)) {
			toPost();

		}

		if (actionCommand.equals("Add Row")) {
			modelCommit.addRow(new Object[] { null,null,null,null });
			updateCommit();
		}

		if (actionCommand.equals("Remove Row")) {
			int[] selectedRows = tblCommit.getSelectedRows();

			if (selectedRows.length == 0) {
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select row in Work Item(s).", "Remove Row", JOptionPane.INFORMATION_MESSAGE);
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

		if (e.getActionCommand().equals("Edit")) {

			grpButton.setSelected(btnEdit.getModel(), true);
			_setDisable();
			btnState(false, false, true, true, false, false);
			lookupPnNo.setEditable(false);
			lookupClientName.setEditable(false);
		}
		if (e.getSource().equals(btnCancel)) {

			int response = JOptionPane.showConfirmDialog(this,"Are you sure you want to Clear all fields?   ","Confirm",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (response == JOptionPane.YES_OPTION) {

				clear();
				formload(); 
				lblPnNo.setEnabled(true);
				lookupPnNo.setEnabled(true);
				lookupPnNo.setEditable(true);

				btnState(true, false, false, false, false, true); 
				ifviewing = false;
			}
		}

		if (e.getSource().equals(btnPost)) {

			grpButton.setSelected(btnPost.getModel(), true);

			toPost();
			//_setDisable();

		}

		if (e.getSource().equals(btnPrintForm)) {
			new Thread(new ForPrintPNForm()).start();
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
	}

	@Override
	public void lookupPerformed(LookupEvent e) {
		pgSelect dbs = new pgSelect();

		if( e.getSource().equals(lookupCompany)){ 
			Object[] data = ((_JLookup)e.getSource()).getDataSet();

			if(data != null){

				co_id = data[0].toString();
				company = data[1].toString();
				company_logo = data[3] == null ? "" : data[3].toString() ;
				txtCompany.setText(company);

				lblPnNo.setEnabled(true);
				lookupPnNo.setEnabled(true);
				btnState(true, false, false, false, false, true); 
			}
		}
		if( e.getSource().equals(lookupPnNo)){

			System.out.println("lookupPN_No");
			Object[] data = ((_JLookup)e.getSource()).getDataSet();

			modelCommit.clear();
			//modelCommitted.clear();

			if(data != null){

				pgSelect db = new pgSelect();
				_pn_no = data[0].toString();
				db.select(method.getEditDetails(_pn_no));

				if (db.isNotNull()) {

					_entity_id 		= db.Result[0][1].toString();
					_proj_id 			= db.Result[0][7].toString();
					_pbl_id 			= db.Result[0][8].toString();
					_seq_no 		= (Integer) db.Result[0][9];

					//lookupPN_No.setText(_pn_no);
					lookupClientName.setValue(_entity_id);
					txtClientName.setText(db.Result[0][2].toString());
					txtProjID.setText(db.Result[0][7].toString());
					txtProjDesc.setText(db.Result[0][4].toString());
					txtPBLID.setText(db.Result[0][8].toString());
					txtPBLDesc.setText(db.Result[0][3].toString());

					txtTelNo.setText((String) (db.Result[0][5]== null ? "-" : db.Result[0][5]));
					txtMobile.setText((String) (db.Result[0][6]== null ? "-" : db.Result[0][6]) );
					txtNoDays.setText(db.Result[0][11].toString()+" days");
					txtMoUpdate.setText(db.Result[0][10].toString()+" month(s)");

					txtDPMA.setText(db.Result[0][12].toString());
					txtAmountDue.setValue((BigDecimal)db.Result[0][13]);
					txtLastDatePaid.setText(db.Result[0][14].toString());
					txtDefaultDate.setText(db.Result[0][15].toString());

					//dteDateSubmitted.setDate((Date)db.Result[0][16]);
					txtRemarks.setText(db.Result[0][18].toString());
					noofdays =Integer.valueOf(db.Result[0][11].toString());
					lookupApproved.setValue(db.Result[0][19].toString());
					txtApproved.setText(db.Result[0][20].toString());
					txtSignedby.setText(db.Result[0][17].toString());

					paymentdate = (Date) db.Result[0][23];
					txtAmountPay.setValue((BigDecimal)db.Result[0][21]);
					txtPaymentAmt.setValue((BigDecimal)db.Result[0][22]);
					dtePaymentDate.setDate(paymentdate);

					txtRwClient.setText(db.Result[0][24]== null ? "" : db.Result[0][24].toString());
					txtContactNo.setText(db.Result[0][25]== null ? "" : db.Result[0][25].toString());
					txtEmailAdd.setText(db.Result[0][26]== null ? "" :db.Result[0][26].toString());

					dbs.select("select commit_date ,amount,no_of_mo from rf_pn_detail where pn_no = '"+_pn_no+"' and status_id = 'A'");


					if (dbs.isNotNull()) {

						for (int i = 0; i < dbs.getRowCount(); i++) {
							commit_date = (Date)dbs.Result[i][0];
							commit_amount = (BigDecimal)dbs.Result[i][1];

							modelCommit.addRow(new Object[] {commit_date,commit_amount,dbs.Result[i][2],null });	
						}
					}
					btnState(false, true, false, true, true, true);
					ifPosted(_pn_no);


					//if (dbs.isNotNull()) {
					/*
						for (int i = 0; i < dbs.getRowCount(); i++) {

							commit_date = (Date)dbs.Result[i][0];
							commit_amount = (BigDecimal)dbs.Result[i][1];

							modelCommit.addRow(new Object[] {commit_date,commit_amount,dbs.Result[i][2],null });		

							modelCommitted.addRow(new Object[] {
									false,
									commit_date,
									commit_amount,
									null,
									null,
									null 
							});
						}	
						dbs.select("SELECT committed_date, amt_applied, date_paid,amount_paid from rf_pn_pmt WHERE pn_no = '"+_pn_no+"' and status_id = 'A'");

						if (dbs.isNotNull()) {

							for (int x = 0; x < dbs.getRowCount(); x++) {
								Date date_paid = (Date) dbs.Result[x][2];
								BigDecimal amount_paid = (BigDecimal)dbs.Result[x][3];

								for (int i = 0; i < modelCommit.getRowCount(); i++) {


									System.out.println(""+modelCommit.getValueAt(i, 1) +"= "+committed_amount +"::"+modelCommit.getValueAt(i, 0)+"= "+ committed_date);

									if (modelCommit.getValueAt(i, 1).equals(committed_amount) &&  modelCommit.getValueAt(i, 0).equals(committed_date)) {
										System.out.println("TRUE");
										ifPaid  = true;
									}

									//modelCommitted.setValueAt(modelCommit.getValueAt(i, 1).equals(committed_amount) &&  modelCommit.getValueAt(i, 0).equals(committed_date), x, 0);
									modelCommitted.setValueAt(ifPaid, x, 0);
									modelCommitted.setValueAt(date_paid, x, 3);
									modelCommitted.setValueAt(amount_paid, x, 4);	
								}


								//modelCommitted.setValueAt(modelCommit.getValueAt(i, 1).equals(committed_amount) &&  modelCommit.getValueAt(i, 0).equals(committed_date), x, 0);

								modelCommitted.setValueAt(date_paid != null, x, 0);
								modelCommitted.setValueAt(date_paid, x, 3);
								modelCommitted.setValueAt(amount_paid, x, 4);	
							}
						}
					 */
					//}

					//		computeTotalCommitted();
					//		total_amount_paid = modelCommittedAmountTotal.getValueAt(0, 4);
					//		total_committed_amt = modelCommittedAmountTotal.getValueAt(0, 2);

					/*System.out.println("not yet Settle" + total_committed_amt +""+ total_amount_paid);
					if(Double.parseDouble(String.valueOf(total_committed_amt)) > Double.parseDouble(String.valueOf(total_amount_paid))){
						System.out.println("not yet Settle");

						lblNote_Client.setText("<html><i><font color = \"black\">Note  :  <font color = \"red\">PN-Committment is not yet settled");
						lblNote_Client.setVisible(true);
					}

					if(Double.parseDouble(String.valueOf(total_committed_amt)) <= Double.parseDouble(String.valueOf(total_amount_paid))){
						System.out.println("PN-Committment is already Settle");
						lblNote_Client.setText("<html><i><font color = \"black\">Note  :  <font color = \"red\">PN-Committment is already settled");
						lblNote_Client.setVisible(true);
					}
					 */

					computeTotal();
					scrollCommitAmountTotal.setRowHeaderView(FncTables.getRowHeader_Footer(Integer.toString(tblCommit.getRowCount())));
					tblCommit.packAll();

					rowHeaderPNH.setModel(new DefaultListModel());
					_FPromissoryNoteCommintment.displayPnHistory(modelPNHistory, rowHeaderPNH,  _entity_id,  _proj_id,_pbl_id, _seq_no );
					scrollPNHistory.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPNHistory.getRowCount())));
				}

				///ifDept(_pn_no);
			}
		}

		if( e.getSource().equals(lookupClientName)){/*

			FncSystem.out("SQL", method.getClient());
			Object[] data = ((_JLookup)e.getSource()).getDataSet();
			if(data != null){
				clear();
				clearpastdue();

				_entity_name 	= data[1].toString();
				_entity_id 		= data[0].toString();
				_proj_id 		= data[6].toString();
				_pbl_id 		= data[7].toString();
				_seq_no 		= (Integer) data[9];

				if (method.getBuyerType(_entity_id, _proj_id, _pbl_id, _seq_no).equals("04")) {
					table_name ="pagibig_due_accounts";
				}else{
					table_name ="ihf_due_accounts";
				}
				lookupPnNo.setValue(_pn_no);
				lookupClientName.setValue(data[0].toString());
				txtClientName.setText(_entity_name);
				txtProjID.setText(_proj_id);
				txtProjDesc.setText(data[3].toString());
				txtPBLID.setText(_pbl_id);
				txtPBLDesc.setText(data[2].toString());
				txtTelNo.setText(data[4] == null ? "-" : data[4].toString());
				txtMobile.setText(data[5] == null ? "-" : data[5].toString());

				btnClientInfo.setEnabled(true);

				dbs.select(method.displayPastDue(_entity_id, _proj_id, _pbl_id, _seq_no, table_name));

				_months_pd = Integer.valueOf(dbs.Result[0][1].toString());
				_days_pd = Integer.valueOf(dbs.Result[0][0].toString());
				_part_id =  (String) (dbs.Result[0][8]== null ? "-" : dbs.Result[0][8]);    

				if (dbs.isNotNull()) {
					noofdays =  Integer.valueOf(dbs.Result[0][0].toString());

					if(noofdays == 0){
						lblNote_Client.setVisible(true);
						lblNote_Client.setText("<html><i><font color = \"black\">Note  :  <font color = \"red\">Client has no Past Due Payments");

					}else{
						txtNoDays.setText(dbs.Result[0][0].toString()+" days");
						txtMoUpdate.setText(dbs.Result[0][1].toString()+" month(s)");
						amountdue = (BigDecimal) dbs.Result[0][2];
						txtAmountDue.setValue(amountdue);
						txtLastDatePaid.setText(dbs.Result[0][3].toString());
						txtDefaultDate.setText(dbs.Result[0][4].toString());
						txtDPMA.setText(dbs.Result[0][5].toString());
						lblNote_Client.setVisible(false);

						dbs.select(method.displayPastDues(_entity_id, _proj_id, _pbl_id, _seq_no));

						if (dbs.isNotNull()) {
							txtAmountPay.setValue((BigDecimal)dbs.Result[0][0]);
							dtePaymentDate.setDate((Date) dbs.Result[0][1]);

						}
					}
					_setDisable();
					btnPrintForm.setEnabled(true);

				}

				rowHeaderPNH.setModel(new DefaultListModel());
				_FPromissoryNoteCommintment.displayPnHistory(modelPNHistory, rowHeaderPNH,  _entity_id,  _proj_id,_pbl_id, _seq_no );
				scrollPNHistory.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPNHistory.getRowCount())));
			}
		*/
			
		
			FncSystem.out("SQL", method.getClient());
			Object[] data = ((_JLookup)e.getSource()).getDataSet();
			if(data != null){
				
				
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

	private void _setDisable(){

		if(grpButton.getSelection().getActionCommand().equals("Edit") || grpButton.getSelection().getActionCommand().equals("Add")){

			if(noofdays != null){
				if (noofdays == 0) {
					this.setComponentsEnabled(pnlPNCommitment, false);
				}else{
					this.setComponentsEnabled(pnlPNCommitment, true);

				}

				this.setComponentsEnabled(pnlCommitmentStatus, true);
			}
		}

		if (grpButton.getSelection().getActionCommand().equals("Post")) {
			this.setComponentsEnabled(pnlCommitmentStatus, true);
		}
	}

	private void updateCommit() {
		scrollCommitAmountTotal.setRowHeaderView(FncTables.getRowHeader_Footer(Integer.toString(tblCommit.getRowCount())));
		tblCommit.packAll();

		for (int row = 0; row < tblCommit.getRowCount(); row++) {
			((DefaultListModel) rowHeaderCommit.getModel()).setElementAt(row + 1, row);
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

	/*	private void computeTotalCommitted() {
		BigDecimal totalAmountCommitted = new BigDecimal("0.00");
		BigDecimal totalAmountPaid = new BigDecimal("0.00");


		for (int x = 0; x < modelCommitted.getRowCount(); x++) {
			BigDecimal committed_amount = (BigDecimal) modelCommitted.getValueAt(x, 2);
			BigDecimal amount_paid = (BigDecimal) modelCommitted.getValueAt(x, 4);
			try {
				totalAmountCommitted = totalAmountCommitted.add(committed_amount);
				totalAmountPaid = totalAmountPaid.add(amount_paid);
			} catch (Exception e1) { }
		}

		modelCommittedAmountTotal.setValueAt(totalAmountCommitted, 0, 2);
		modelCommittedAmountTotal.setValueAt(totalAmountPaid, 0, 4);


	}*/
	@Override
	public void mouseClicked(MouseEvent e) {

		if (e.getSource().equals(tblCommit)) {
			if (tblCommit.getSelectedColumn()== 0 ) {

				if (e.getClickCount() ==2 ) {
					dteCommitDate.setBounds((int)pnlCenter_C.getMousePosition().getX(), (int)pnlCenter_C.getMousePosition().getY(), 0, 0);
					dteCommitDate.getCalendarButton().doClick();
				}
			} // column 0
		}
		/*	if (e.getSource().equals(tblCommittedStatus)) {
			try {
				if (modelCommitted.getValueAt(tblCommittedStatus.getSelectedRow(), 0).equals(true)) { 
					if (e.getClickCount() ==2 ) {
						dtePaidDate.setBounds((int)pnlCommitmentStatus_C.getMousePosition().getX(), (int)pnlCommitmentStatus_C.getMousePosition().getY(), 0, 0);
						dtePaidDate.getCalendarButton().doClick();
					}

				}
			} catch (java.lang.ArrayIndexOutOfBoundsException e1) {
			}

		 *//** Listener for boolean canEditCancel **//*
			if (tblCommittedStatus.getSelectedColumn()== 4  ) {
				modelCommitted.setEditable(( modelCommitted.getValueAt(tblCommittedStatus.getSelectedRow(),0).equals(true)) );
			} 
		}*/
	}

	@Override
	public void mouseEntered(MouseEvent arg0){}
	@Override
	public void mouseExited(MouseEvent arg0){}
	@Override
	public void mousePressed(MouseEvent arg0){}
	@Override
	public void mouseReleased(MouseEvent arg0){}
	@Override
	public void mouseDragged(MouseEvent arg0){}
	@Override
	public void mouseMoved(MouseEvent arg0){}


	private void formload(){
		clear();
		_setEnabled(false);
		visibleNote(false);

		lblPnNo.setEnabled(false);
		lookupPnNo.setEnabled(false);

		if (lookupCompany.getValue().isEmpty()) {
			btnState(false, false, false, false, false, false);	
			lblPnNo.setEnabled(false);
			lookupPnNo.setEnabled(false); 
		}else{
			btnState(true, false, false, false, false, true);
			lblPnNo.setEnabled(true);
			lookupPnNo.setEnabled(true);
			lookupPnNo.setEditable(true);
		}
	}

	private void clear(){

		_entity_id = "";
		_entity_name = "";
		_part_id = "";
		_pbl_id = "";
		_proj_id = "";
		_months_pd = 0;
		_days_pd = 0;
		amountdue = null;
		noofdays = 0;

		rowHeaderCommit.setModel(new DefaultListModel());
		scrollCommit.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblCommit.getRowCount())));
		scrollCommitAmountTotal.setRowHeaderView(FncTables.getRowHeader_Footer(Integer.toString(tblCommit.getRowCount())));
		computeTotal();

		if (!btnAdd.isEnabled()) {
			//lookupCompany.setValue("");
			//txtCompany.setText("");
		}

		txtAmountDue.setValue(null);
		lookupPnNo.setValue("");
		lookupClientName.setValue("");
		txtClientName.setText("");
		txtClientName.setText("");
		txtProjID.setText("");
		txtProjDesc.setText("");
		txtPBLID.setText("");
		txtPBLDesc.setText("");
		txtTelNo.setText("");
		txtMobile.setText("");
		txtSignedby.setText("");
		FncTables.clearTable(modelCommit);
		rowHeaderCommit.setModel(new DefaultListModel());
		scrollCommit.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblCommit.getRowCount())));
		scrollCommitAmountTotal.setRowHeaderView(FncTables.getRowHeader_Footer(Integer.toString(tblCommit.getRowCount())));
		computeTotal();

		lookupApproved.setValue(null);
		txtApproved.setText("");
		noofdays = null;
		txtAmountPay.setValue(null);

		FncTables.clearTable(modelPNHistory);
		rowHeaderPNH.setModel(new DefaultListModel());
		scrollPNHistory.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPNHistory.getRowCount())));

		for (Component component : pnlCenter_Main.getComponents()){
			if(component instanceof JTextField){
				((JTextField) component).setText("");
			}
		}
		for (Component component : pnlPastDue_Center.getComponents()){
			if(component instanceof JTextField){
				((JTextField) component).setText("");
			}
		}

		for (Component component : pnlPastDue_Amt_Center.getComponents()){
			if(component instanceof JTextField){
				((JTextField) component).setText("");
			}
		}

		for (Component component : pnlPastDue_Amt_Center.getComponents()){
			if(component instanceof JTextField){
				((JTextField) component).setText("");
			}
		}

		for (Component component : pnlCenter_PNPayment_East.getComponents()){
			if(component instanceof JTextField){
				((JTextField) component).setText("");
			}
			if(component instanceof _JDateChooser){
				((_JDateChooser) component).setDate(null);;
			}
		}
		lblNote_Client.setVisible(false);
	}

	private void _setEnabled(boolean ena){

		this.setComponentsEnabled(pnlCenter_Main, ena);
		this.setComponentsEnabled(pnlDetails_Center, ena);
		btnClientInfo.setEnabled(ena);
		btnPrintForm.setEnabled(ena);

	}

	private void btnState(Boolean sAddNew, Boolean sEdit, Boolean sSave, Boolean sCancel, Boolean sPost, Boolean sPreview){
		btnAdd.setEnabled(sAddNew);
		btnEdit.setEnabled(sEdit);
		btnSave.setEnabled(sSave);
		btnCancel.setEnabled(sCancel);
		btnPost.setEnabled(sPost);
		btnPreview.setEnabled(sPreview);
	}//end of btnState

	private void clearpastdue(){

		txtMoUpdate.setText("");
		txtNoDays.setText("");
		txtDPMA.setText("");
		txtAmountDue.setValue(null);
		txtLastDatePaid.setText("");
		txtDefaultDate.setText("");
	}

	@Override
	public void keyPressed(KeyEvent e) {

	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}
	private Boolean toPost() { //XXX POST


		if (grpButton.getSelection().getActionCommand().equals("Add") || grpButton.getSelection().getActionCommand().equals("Edit")) {

			if (modelCommit.getRowCount() == 0 || modelCommit.getValueAt(0, 0) == null  || modelCommit.getValueAt(0, 1) == null || txtApproved.getText().isEmpty() || txtSignedby.getText().isEmpty() || txtRemarks.getText().isEmpty() || txtPaymentAmt.getValue() == null) {
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please fill up all required Details before Saving   ", "Save", JOptionPane.WARNING_MESSAGE);
				return false;

			}

			int response = JOptionPane.showConfirmDialog(this, "Are you sure you want to save PN details?  ", "Save", 	JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (response == JOptionPane.YES_OPTION) {

				new Thread(new ForPosting()).start();
				System.out.println("----SAVE" );
				JOptionPane.showMessageDialog(this,"PN / commitment details saved","Successful", JOptionPane.INFORMATION_MESSAGE);			

				formload();
			}

		}
		if (grpButton.getSelection().getActionCommand().equals("Post") ) {
			{
				computeTotal();
			}
			int response = JOptionPane.showConfirmDialog(this, "Are you sure you want to post PN details ?  ", "Post", 	JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (response == JOptionPane.YES_OPTION) {

				new Thread(new ForPosting()).start();
				System.out.println("----SAVE" );
				JOptionPane.showMessageDialog(this,"PN / commitment details posted","Successful", JOptionPane.INFORMATION_MESSAGE);			

				btnPrintForm.setEnabled(true);
				btnState(false, false, false, true, false, false);
			}
		}

		return true;
	}

	public class ForPosting implements Runnable{

		private String _last_paid_date;
		private String _default_date;
		private String _signedby;
		private String _remarks;
		private String _requestedby;
		private String _approvedby;
		private String _commit_date;
		private BigDecimal _amount_commit;
		private String _no_of_mo;
		private String _relationwclient;
		private String _contact_no_rwc;
		private String _email_add_rwc;

		@Override
		public void run() {

			_entity_id = lookupClientName.getValue();
			_proj_id = txtProjID.getText();
			_pbl_id = txtPBLID.getText();
			_last_paid_date = txtLastDatePaid.getText();
			_default_date = txtDefaultDate.getText();
			///_date_submit =dteDateSubmitted.getDate();
			_signedby = txtSignedby.getText();
			_relationwclient = txtRwClient.getText();
			_remarks = txtRemarks.getText();
			_requestedby = UserInfo.EmployeeCode;
			_pn_no = lookupPnNo.getValue();
			_approvedby = lookupApproved.getValue();

			_contact_no_rwc = txtContactNo.getText().trim();
			_email_add_rwc = txtEmailAdd.getText().trim();
			String SQL = "";		

			if (grpButton.getSelection().getActionCommand().equals("Post")) {
				pgUpdate db = new pgUpdate();
				db.executeUpdate("UPDATE rf_pn_header \n" + 
						"		SET	date_posted = now()::TIMESTAMP, \n" + 
						"			posted_by = '"+_approvedby+"', \n" + 
						"			approved = '"+_approvedby+"' \n" + 
						//"			,status_id = 'I' \n" +
						"		WHERE pn_no = '"+_pn_no+"' AND status_id = 'A';\n" + 
						" ", true);

				db.commit();
			}


			if (grpButton.getSelection().getActionCommand().equals("Add")) {

				for (int i = 0; i < modelCommit.getRowCount(); i++) {

					_commit_date =  modelCommit.getValueAt(i, 0).toString();
					_amount_commit = (BigDecimal) modelCommit.getValueAt(i, 1);
					_no_of_mo =  modelCommit.getValueAt(i, 2) == null ? "" :modelCommit.getValueAt(i, 2).toString() ;

					SQL = "SELECT sp_save_promissorynote_new(\n" + 
							""+i+", ---p_row ,\n" + 
							"'"+_entity_id+"', ---p_entity_id ,\n" + 
							" '"+_proj_id+"', ---p_proj_id ,\n" + 
							" '"+_pbl_id+"', ---p_pbl_id ,\n" + 
							" "+_seq_no+", ---i_seq_no ,\n" + 
							" '"+_part_id+"', ---p_part_id ,\n" + 
							" '"+_last_paid_date+"', ---_last_paid_date ,\n" + 
							" '"+_default_date+"', ---_default_date ,\n" + 
							" "+_months_pd+", ---_months_pd , \n" + 
							" "+_days_pd+", ---_days_pd ,\n" + 
							" '"+amountdue+"', ---_amount_due , \n" + 
							//" '"+_date_submit+"', ---_date_submit ,\n" + 
							" '"+_signedby+"', ---_signed_by ,\n" + 
							" '"+_remarks+"', ---_remarks ,\n" + 
							" '"+_requestedby+"', ---_requested_by ,\n" + 
							" '"+_pn_no+"', ---_pn_no ,\n" + 
							" '"+_approvedby+"', ---_approved ,\n" +
							" '"+txtAmountPay.getValued()+"', ---_amount_to_pay ,\n" +
							" '"+txtPaymentAmt.getValued()+"', ---_payment_amount ,\n" +
							" '"+dtePaymentDate.getDate()+"', ---_payment_date ,\n" +
							" '"+_commit_date+"', ---_commit_date , \n" + 
							" '"+_amount_commit+"', ---_amount_commit ,\n" + 
							" NULLIF('"+_no_of_mo+"','null'), ---_no_of_mo  \n" +
							"'"+_relationwclient+"',---_relationwclient \n" +
							"'"+_contact_no_rwc+"' ,---contact_no_rwc\n" +
							"'"+_email_add_rwc+"' );---email_add_rwc";


					System.out.printf("SQL: %s%n", SQL);
					pgSelect db = new pgSelect();
					db.select(SQL); 
				}
			}

			if (grpButton.getSelection().getActionCommand().equals("Edit")) {
				for (int i = 0; i < modelCommit.getRowCount(); i++) {

					_commit_date =  modelCommit.getValueAt(i, 0).toString();
					_amount_commit = (BigDecimal) modelCommit.getValueAt(i, 1);
					_no_of_mo =  modelCommit.getValueAt(i, 2) == null ? null : modelCommit.getValueAt(i, 2).toString();

					SQL = "SELECT sp_edit_promissorynote_new(\n" + 
							" "+i+", ---p_row ,\n" + 
							" '"+_pn_no+"', ---_pn_no ,\n" + 
							" '"+_signedby+"', ---_signed_by ,\n" +
							" '"+_relationwclient+"', ---_relationwclient,\n" +
							" '"+_remarks+"', ---_remarks ,\n" + 
							" '"+_approvedby+"', ---_approved ,\n" + 
							" '"+txtPaymentAmt.getValue()+"', ---_payment_amount ,\n" +
							" '"+dtePaymentDate.getDate()+"', ---_payment_date ,\n" +
							" '"+_commit_date+"', ---_commit_date \n" + 
							" '"+_amount_commit+"', ---_amount_commit,\n" + 
							" NULLIF('"+_no_of_mo+"','null'),---_no_of_mo \n" + 
							"'"+_contact_no_rwc+"' ,---contact_no_rwc\n" +
							"'"+_email_add_rwc+"' );---email_add_rwc";


					System.out.printf("SQL: %s%n", SQL);
					pgSelect db = new pgSelect();
					db.select(SQL);
				}
			}	
		}
	}

	private void visibleNote(Boolean ena){
		lblNote_Client.setVisible(ena);
	}

	public class ForPrintPNForm implements Runnable{
		private String phase;
		private String block;
		private String lot;

		@Override
		public void run() {
			BigDecimal amount_due = new BigDecimal("0.00");
			BigDecimal payment_amount = new BigDecimal("0.00");
			ArrayList<TD_PN_Commitment> list_PN_Commitment = new ArrayList<TD_PN_Commitment>();

			pgSelect db = new pgSelect();
			for (int i = 0; i < modelCommit.getRowCount(); i++) {

				String commit_date = modelCommit.getValueAt(i, 0).toString();

				db.select("SELECT to_char(Date '"+commit_date+"', 'MM/DD/YYYY')::timestamp;");
				//Timestamp commit_date = (Timestamp) modelCommit.getValueAt(i, 0);

				Date commit_dates =  (Date) db.Result[0][0];
				BigDecimal amount = (BigDecimal) modelCommit.getValueAt(i, 1);
				String to_update = (String) modelCommit.getValueAt(i, 2);
				Integer count = modelCommit.getRowCount() ;

				list_PN_Commitment.add(new TD_PN_Commitment(commit_dates,amount,to_update,count));	

			}

			amount_due = (BigDecimal) txtAmountPay.getValued();
			payment_amount = (BigDecimal) txtPaymentAmt.getValued();		

			db.select("SELECT phase,block,lot FROM mf_unit_info where pbl_id  = '"+txtPBLID.getText().trim()+"'");

			if (db.isNotNull()) {
				phase = db.Result[0][0].toString();
				block = db.Result[0][1].toString();
				lot = db.Result[0][2].toString();
			}

			Map<String, Object> mapParameters = new HashMap<String, Object>();
			mapParameters.put("company", company);
			mapParameters.put("co_id", co_id);
			mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
			mapParameters.put("pn_no", lookupPnNo.getValue().toString());
			mapParameters.put("SUBREPORT_DIR", this.getClass().getResourceAsStream("/Reports/subrptPromissoryNote_Preview.jasper"));
			mapParameters.put("list_PN_Commitment", list_PN_Commitment);

			mapParameters.put("client_name", txtClientName.getText().trim());
			mapParameters.put("phase", phase);
			mapParameters.put("block", block);
			mapParameters.put("lot", lot);
			mapParameters.put("proj_name", txtProjDesc.getText().trim());
			mapParameters.put("as_of_date", dtePaymentDate.getText().trim());
			mapParameters.put("amount_due", amount_due);
			mapParameters.put("signedby", txtSignedby.getText());
			mapParameters.put("payment_amount",payment_amount);
			mapParameters.put("relationwclient", txtRwClient.getText().trim());
			mapParameters.put("contact_no", txtContactNo.getText().trim());
			mapParameters.put("email_add", txtEmailAdd.getText().trim());

			FncReport.generateReport("/Reports/rptPromissoryNoteForm_Preview.jasper", "Promissory Note", mapParameters);

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
			mapParameters.put("pn_no", lookupPnNo.getValue().toString());

			FncReport.generateReport("/Reports/rptPN.jasper", "List of Accounts With Committments",  company, mapParameters);	

		}

	}

	private void ifPosted(String _pn_no){
		pgSelect db = new pgSelect();
		String posted = null;
		db.select("SELECT posted_by FROM rf_pn_header where pn_no = '"+_pn_no+"' AND status_id = 'A'");

		posted = db.Result[0][0] == null ? null : db.Result[0][0].toString();
		if (db.isNotNull()) {
			if (posted == null) {
				btnState(false, true, false, true, true, true);
			}else{
				btnState(false, false, false, true, false, true);
				btnPrintForm.setEnabled(true);
				btnClientInfo.setEnabled(true);	
			}
		}
	}

	/*private void ifDept(String _pn_no){
		String posted = null;

		if (db.isNotNull()) {
			posted = db.Result[0][0] == null ? null : db.Result[0][0].toString();
		}


		if (UserInfo.Department.equals("54")) {
			if (UserInfo.EmployeeCode.equals("900421")) {


					btnState(false, false, false, true, false, false);
				}else{

					if (posted == null) {
						System.out.println("NOT POSTED");
						btnState(false, true, true, true, false, true);	
					}else{
						btnState(false, false, false, true, false, true);
					}

				}

			}else{
				if (lblNote_Client.getText().contains("Committment is already settled")) {
					btnState(false, false, false, true, false, false);
				}else{
					btnState(false, true, false, true, false, true);	
					if (posted == null) {
						System.out.println("NOT POSTED");
						btnState(false, true, false, true, false, true);	
					}else{
						btnState(false, false, false, true, false, true);
					}
				}
			}
		}

		if (UserInfo.Department.equals("52")) {

			if (lblNote_Client.getText().contains("Committment is already settled")) {
				btnState(false, false, false, true, false, false);
			}else{

				if (posted == null) {
					System.out.println("NOT POSTED");
					btnState(false, false, false, true, false, true);	
				}else{
					btnState(false, false, false, true, true, true);
				}
			}
		}

		if (UserInfo.Alias.equals("eagonzales") || UserInfo.Alias.equals("ccpaquibot") ||  UserInfo.Alias.equals("aagonzales") ||  UserInfo.Alias.equals("jffatallo") ) {
			System.out.println("ADMIN");

			if (lblNote_Client.getText().contains("Committment is already settled")) {
				btnState(false, false, false, true, false, false);
			}else{

				if (posted == null) {
					System.out.println("NOT POSTED");
					btnState(false, true, true, true, false, true);	
				}else{
					btnState(false, false, false, true, true, true);
				}

			}
		}

	 }
	 */

	private void getDefaultCompany(){

		co_id = "02";
		company = "CENQHOMES DEVELOPMENT CORPORATION" ;
		company_logo = "cenq_logo.jpg";
		txtCompany.setText(company);
		lookupCompany.setValue(co_id);

		lblPnNo.setEnabled(true);
		lookupPnNo.setEnabled(true);
		btnState(true, false, false, false, false, true); 
	}

	public void getDetails(String entity_id, String proj_id, String pbl_id, Integer seqno){

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

	}
}
