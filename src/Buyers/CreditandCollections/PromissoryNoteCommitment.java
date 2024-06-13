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
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpringLayout;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableColumn;
import javax.swing.text.BadLocationException;

import org.jdesktop.swingx.JXFormattedTextField;
import org.jdesktop.swingx.table.NumberEditorExt;

import tablemodel.model_PN_Commit;
import Buyers.ClientServicing.ClientInformation;
import Database.pgSelect;
import Database.pgUpdate;
import DateChooser._JDateChooser;
import Dialogs.dlgDate;
import FormattedTextField._JXFormattedTextField;
import Functions.FncBigDecimal;
import Functions.FncGlobal;
import Functions.FncReport;
import Functions.FncTables;
import Functions.SpringUtilities;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;

import com.toedter.calendar.JTextFieldDateEditor;

import components._JButton;
import components._JInternalFrame;
import components._JScrollPaneMain;
import components._JScrollPaneTotal;
import components._JTableMain;
import components._JTableTotal;
/**
 * @author CHRISTIAN PAQUIBOT
 *
 */
public class PromissoryNoteCommitment extends _JInternalFrame implements ActionListener,LookupListener ,KeyListener,MouseListener,MouseMotionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1208005779722605913L;
	public static String title = "Promissory Note / Commitment";
	public static Dimension frameSize = new Dimension(900, 650);
	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	private ButtonGroup btngrpBuyerType = new ButtonGroup();

	private JPanel pnlMain;
	private JPanel pnlNorth;
	private JPanel pnlNorth_North;

	private JPanel pnlNorth_North_West;
	private JLabel lblByrType;
	private JPanel pnlNorth_Center;
	private JPanel pnlNorth_Center_West;
	private JLabel lblClientID;
	private _JLookup lookupClientName;
	private JPanel pnlLookUp;
	private JTextField txtClientName;
	private JPanel pnlNort_Center_Center;
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
	private JPanel pnlPastDue;
	private JTextField txtMobile;
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
	private JPanel pnlNorth_South;
	private JPanel pnlViewClient;
	private _JButton btnClientInfo;
	private JPanel pnlCenter;
	private JPanel pnlCenter_Center;
	private JPanel pnlCenter_East;
	private JCheckBox chkSched;
	private JPanel pnlCenter_PNPayment_West;
	private JLabel lblDateSubmitted;
	private JLabel lblSignedBy;
	private JLabel lblRemarks;
	private JPanel pnlCenter_PNPayment_East;
	private _JXFormattedTextField txtCommittedAmount_1;
	private _JXFormattedTextField txtCommittedAmount_2;
	private JTextField txtSignedBy;
	private JTextField txtRemarks;
	private _JDateChooser dteCommintmentDate_1;
	private _JDateChooser dteCommintmentDate_2;
	private _JDateChooser dteDateSubmitted;
	private _JDateChooser dteDateGranted;
	private JPanel pnlSouth;
	private JPanel pnlSouth_Center;
	private _JButton btnSave;
	private _JButton btnCancel;
	private JLabel lblAppliedPN;
	private JLabel lblPaymentStatus;
	private JPanel pnlCenter_East_Center;
	private JTextField txtCommittedDate;
	private JLabel lblCommittedDate_PN;
	private JLabel lblDatePaid_PN;
	private _JXFormattedTextField txtTotalCommittedAmt;
	private _JXFormattedTextField txtTotalAmountPaid;
	private JLabel lblTotalCommittedAmt;
	private JLabel lblTotalAmountPaid;
	private JLabel lblNote_Client;
	private String _entity_id;
	private String _proj_id;
	private String _pbl_id;
	private Integer _seq_no;
	private _FPromissoryNoteCommintment method = new _FPromissoryNoteCommintment();
	private String table_name;
	private String _entity_name;
	private Integer noofdays = 0;
	private _JLookup lookupSignedBy;
	private String _unit_id;
	private Integer _months_pd;
	private Integer _days_pd;
	private BigDecimal amountdue;
	private String _committed_datePN;

	private _JButton btnAdd;
	private _JButton btnEdit;
	private _JButton btnSettle;
	private _JButton btnPreview;
	private String _part_id;
	private ButtonGroup grpButton = new ButtonGroup();
	private _JDateChooser dteDatePaid;
	private Date _datepaid;
	private JPanel pnlNorth_North_East;
	private _JLookup lookupPN_No;
	private _JLookup lookupCompany;
	private JPanel _pnlNorth;
	private JPanel pnlCompany;
	private JTextField txtCompany;
	private JLabel lblCompany;
	private String company_logo;
	private String co_id;
	private String company;
	private Date dateFrom;
	private Date dateTo;
	private Boolean printall;
	private JLabel lblApproved;
	private _JLookup lookupApproved;
	private JTextField txtApproved;
	@SuppressWarnings("rawtypes")
	private DefaultComboBoxModel cmbMonthUpdateModel;
	@SuppressWarnings("rawtypes")
	private JComboBox cmbMonthUpdate;
	private JPanel pnlCenter_C;
	private JPanel pnlCenter_S;
	private _JScrollPaneMain scrollCommit;
	private model_PN_Commit modelCommit;
	private _JTableMain tblCommit;
	private JList rowHeaderCommit;
	private _JScrollPaneTotal scrollCommitAmountTotal;
	private model_PN_Commit modelCommitAmountTotal;
	private _JTableTotal tblCommitAmountTotal;
	private JButton btnAddRow;
	private JButton btnRemoveRow;
	private _JDateChooser dteCommitDate;
	private JTextField txtSignedby;

	public PromissoryNoteCommitment() {
		super(title, true, true, true, true);
		initGui();
		formLoad();
		lookupCompany.doClick();
	}

	/**
	 * @param title
	 */
	public PromissoryNoteCommitment(String title) {
		super(title);
		initGui();
		formLoad();
		lookupCompany.doClick();
	}

	/**
	 * @param title
	 * @param resizable
	 * @param closable
	 * @param maximizable
	 * @param iconifiable
	 */

	public PromissoryNoteCommitment(String title, boolean resizable,
			boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGui();
		formLoad();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initGui(){
		try {
			this.setTitle(title);
			this.setSize(frameSize);
			this.setPreferredSize(new java.awt.Dimension(900, 600));
			getContentPane().setLayout(new BorderLayout());

			{
				pnlMain = new JPanel();
				getContentPane().add(pnlMain,BorderLayout.CENTER);
				pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
				pnlMain.setLayout(new BorderLayout(5,5));
				{
					pnlNorth = new JPanel();
					pnlMain.add(pnlNorth, BorderLayout.NORTH);
					pnlNorth.setPreferredSize(new Dimension(pnlMain.getWidth(), 250));
					pnlNorth.setLayout(new BorderLayout(5,5));
					{
						pnlNorth_North = new JPanel();
						pnlNorth.add(pnlNorth_North, BorderLayout.NORTH);
						pnlNorth_North.setPreferredSize(new Dimension(pnlMain.getWidth(), 55));
						pnlNorth_North.setLayout(new BorderLayout(5,5));
						//	pnlNorth_North.setBorder(components.JTBorderFactory.createTitleBorder(""));

						{
							pnlNorth_North_West = new JPanel();
							pnlNorth_North.add(pnlNorth_North_West, BorderLayout.WEST);
							pnlNorth_North_West.setPreferredSize(new Dimension(350, 25));
							pnlNorth_North_West.setLayout(new BorderLayout(5,5));
							{
								{
									lblByrType = new JLabel(" PN no. :");
									pnlNorth_North_West.add(lblByrType,BorderLayout.WEST);
									lblByrType.setPreferredSize(new Dimension(80, 25));
									lblByrType.setHorizontalAlignment(JTextField.LEFT);
								}
								{

									lookupPN_No = new _JLookup("", "PN No.", 0) ; /// XXX lookupPN_No 
									pnlNorth_North_West.add(lookupPN_No,BorderLayout.CENTER);
									lookupPN_No.addLookupListener(this);
									lookupPN_No.setPreferredSize(new Dimension(100, 25));
									lookupPN_No.setLookupSQL(method.LookupPn_No());
								}	
							}

						}
						{
							_pnlNorth = new JPanel();
							pnlNorth_North.add(_pnlNorth, BorderLayout.NORTH);
							_pnlNorth.setPreferredSize(new Dimension(100, 27));
							_pnlNorth.setLayout(new SpringLayout());

							{

								lblCompany = new JLabel("Company");
								_pnlNorth.add(lblCompany);
								lblCompany.setPreferredSize(new Dimension(81, 25));
							}
							{
								pnlCompany = new JPanel(new BorderLayout(5,5));
								_pnlNorth.add(pnlCompany,BorderLayout.CENTER);
								//	pnlCompany.setPreferredSize(new Dimension(74, 25));
								{

									lookupCompany = new _JLookup("", "Company", 0) ; /// XXX lookupCompany 
									pnlCompany.add(lookupCompany,BorderLayout.WEST);
									lookupCompany.addLookupListener(this);
									lookupCompany.setLookupSQL(method.getCompany());
									lookupCompany.setPreferredSize(new Dimension(100, 25));
								}
								{
									txtCompany = new JTextField();
									pnlCompany.add(txtCompany,BorderLayout.CENTER);
									txtCompany.setEditable(false);
									txtCompany.setPreferredSize(new Dimension(100, 25));
								} 
							}

							SpringUtilities.makeCompactGrid(_pnlNorth, 1, 2, 2, 2, 2, 2, false);

						}
						{
							pnlNorth_North_East = new JPanel();
							pnlNorth_North.add(pnlNorth_North_East, BorderLayout.EAST);
							pnlNorth_North_East.setPreferredSize(new Dimension(400, 60));
							pnlNorth_North_East.setLayout(new BorderLayout(5,5));
							{
								lblNote_Client = new JLabel("<html><i><font color = \"black\">Note  :  <font color = \"red\">Client has no Past Due Payments");
								pnlNorth_North_East.add(lblNote_Client,BorderLayout.CENTER);
								lblNote_Client.setHorizontalAlignment(JLabel.RIGHT);
							}
						}
					}
					{
						pnlNorth_Center = new  JPanel();
						pnlNorth.add(pnlNorth_Center,BorderLayout.CENTER);
						pnlNorth_Center.setPreferredSize(new Dimension(pnlMain.getWidth(), 250));
						pnlNorth_Center.setLayout(new BorderLayout(5,5));
						pnlNorth_Center.setBorder(components.JTBorderFactory.createTitleBorder("Details"));
						{
							pnlNorth_Center_West = new JPanel();
							pnlNorth_Center.add(pnlNorth_Center_West, BorderLayout.NORTH);
							pnlNorth_Center_West.setPreferredSize(new Dimension(600, 27));
							pnlNorth_Center_West.setLayout(new SpringLayout());

							{
								lblClientID = new JLabel("Client ID :");
								pnlNorth_Center_West.add(lblClientID);
								lblClientID.setPreferredSize(new Dimension(74, 25));
							}
							{
								pnlLookUp = new JPanel(new BorderLayout(5,5));
								pnlNorth_Center_West.add(pnlLookUp,BorderLayout.CENTER);
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

							SpringUtilities.makeCompactGrid(pnlNorth_Center_West, 1, 2, 2, 2, 2, 2, false);
						}
						{
							pnlNort_Center_Center = new JPanel();
							pnlNort_Center_Center.setLayout(new SpringLayout());
							pnlNorth_Center.add(pnlNort_Center_Center, BorderLayout.CENTER);
							pnlNort_Center_Center.setPreferredSize(new Dimension(510, 90));
							{
								pnlProject = new JPanel(new BorderLayout(5, 5));
								pnlNort_Center_Center.add(pnlProject, BorderLayout.WEST);
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
								pnlNort_Center_Center.add(pnlPastDue, BorderLayout.CENTER);
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
								pnlNort_Center_Center.add(pnlPastDue_Amt, BorderLayout.EAST);
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
							SpringUtilities.makeCompactGrid(pnlNort_Center_Center, 1, 3, 3, 3, 3, 3, false);
						} 
						{
							pnlNorth_South = new  JPanel();
							pnlNorth.add(pnlNorth_South,BorderLayout.SOUTH);
							pnlNorth_South.setPreferredSize(new Dimension(pnlMain.getWidth(), 25));
							pnlNorth_South.setLayout(new BorderLayout(5,5));
							{
								pnlViewClient = new JPanel(new BorderLayout(5, 5));
								pnlNorth_South.add(pnlViewClient,BorderLayout.WEST);
								pnlViewClient.setPreferredSize(new Dimension(200, 25));
								{
									btnClientInfo = new _JButton("View Client Info.");
									pnlViewClient.add(btnClientInfo,BorderLayout.CENTER);
									btnClientInfo.addActionListener(this);
								}
							}
						}
					}
				}//pnlNorth
				{
					pnlCenter = new JPanel(new BorderLayout(5,5));
					pnlMain.add(pnlCenter,BorderLayout.CENTER);
					pnlCenter.setPreferredSize(new Dimension(pnlCenter.getWidth(), 200));
					//pnlCenter.setBorder(lineBorder);
					{
						pnlCenter_Center = new  JPanel();
						pnlCenter.add(pnlCenter_Center,BorderLayout.CENTER);
						pnlCenter_Center.setBorder(components.JTBorderFactory.createTitleBorder("Promissory Note - Payment Plan"));// XXX
						pnlCenter_Center.setPreferredSize(new Dimension(550, 20));
						pnlCenter_Center.setLayout(new BorderLayout(5,5));

						{
							pnlCenter_C = new JPanel(new BorderLayout(3, 3));
							pnlCenter_Center.add(pnlCenter_C, BorderLayout.CENTER);
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
									tblCommit.addKeyListener(this);
									tblCommit.hideColumns("Actual Paid");

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
												//int total_unit_cost = table.convertColumnIndexToModel(table.getColumnModel().getColumnIndex("Total Unit Cost"));
												///int total_cost = table.convertColumnIndexToModel(table.getColumnModel().getColumnIndex("Total Cost"));

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
									/*	rowHeaderCommit = tblCommit.getRowHeader();
									rowHeaderCommit.setModel(new DefaultListModel());
									scrollCommit.setRowHeaderView(rowHeaderCommit);
									scrollCommit.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
									scrollCommit.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblCommit.getRowCount())));
									 */
								}
								{


									scrollCommitAmountTotal = new _JScrollPaneTotal(scrollCommit);
									pnlCenter_C.add(scrollCommitAmountTotal, BorderLayout.SOUTH);
									{
										modelCommitAmountTotal = new model_PN_Commit();
										modelCommitAmountTotal.addRow(new Object[] {"Total", 0.00, null, null });

										tblCommitAmountTotal = new _JTableTotal(modelCommitAmountTotal, tblCommit);
										scrollCommitAmountTotal.setViewportView(tblCommitAmountTotal);
										tblCommitAmountTotal.hideColumns("Actual Paid");
										tblCommitAmountTotal.setTotalLabel(0);
									}
									scrollCommitAmountTotal.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, displayTableNavigation());
								}
							}
						}
						{
							pnlCenter_S = new JPanel(new BorderLayout(3, 3));
							pnlCenter_Center.add(pnlCenter_S, BorderLayout.SOUTH);
							//pnlCenter_S.setBorder(components.JTBorderFactory.createTitleBorder("Commitment"));// XXX
							pnlCenter_S.setPreferredSize(new Dimension(0, 100));	
							{
								pnlCenter_PNPayment_West = new JPanel(new GridLayout(4, 1, 5, 5));
								pnlCenter_S.add(pnlCenter_PNPayment_West, BorderLayout.WEST);
								{
									lblDateSubmitted = new JLabel("Date Submitted");
									pnlCenter_PNPayment_West.add(lblDateSubmitted);
								}
								{
									lblSignedBy = new JLabel("Signed By");
									pnlCenter_PNPayment_West.add(lblSignedBy);
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
								pnlCenter_PNPayment_East = new JPanel(new GridLayout(4, 1, 5, 5));
								pnlCenter_S.add(pnlCenter_PNPayment_East, BorderLayout.CENTER);
								{
									dteDateSubmitted = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
									pnlCenter_PNPayment_East.add(dteDateSubmitted);
									dteDateSubmitted.setDate(null);
								}
								{
									txtSignedby = new JTextField();
									pnlCenter_PNPayment_East.add(txtSignedby);
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

						}
					}

					{/*
						pnlCenter_Center = new  JPanel();
						pnlCenter.add(pnlCenter_Center,BorderLayout.CENTER);
						pnlCenter_Center.setBorder(components.JTBorderFactory.createTitleBorder("Promissory Note - Payment Plan"));// XXX
						pnlCenter_Center.setPreferredSize(new Dimension(550, 20));
						pnlCenter_Center.setLayout(new BorderLayout(5,5));
						{
							pnlCenter_PNPayment = new JPanel(new BorderLayout(5,5));
							pnlCenter_Center.add(pnlCenter_PNPayment,BorderLayout.NORTH);
							{
								chkSched = new JCheckBox("<html><i>  Partial Payment will be Paid on 2 Schedules");
								pnlCenter_PNPayment.add(chkSched,BorderLayout.CENTER);
								chkSched.addActionListener(this);
							}
						}
						{
							pnlCenter_PNPayment_Center= new JPanel(new BorderLayout(5,5));
							pnlCenter_Center.add(pnlCenter_PNPayment_Center,BorderLayout.CENTER);
							pnlCenter_PNPayment_Center.setPreferredSize(new Dimension(70, 350));
							{

								pnlCenter_PNPayment_West = new JPanel(new GridLayout(10, 1, 5, 5));
								pnlCenter_PNPayment_Center.add(pnlCenter_PNPayment_West, BorderLayout.WEST);
								{
									lblCommintmentDate_1 = new JLabel("1 Commitment Date");
									pnlCenter_PNPayment_West.add(lblCommintmentDate_1);
								}
								{
									lblCommittedAmount_1 = new JLabel("1 Committed Amount");
									pnlCenter_PNPayment_West.add(lblCommittedAmount_1);

								}
								{
									lblMonthUpdate_1 = new JLabel("Months to Update");
									pnlCenter_PNPayment_West.add(lblMonthUpdate_1);

								}
								{
									lblCommintmentDate_2 = new JLabel("2 Commitment Date");
									pnlCenter_PNPayment_West.add(lblCommintmentDate_2);
								}
								{
									lblCommittedAmount_2 = new JLabel("2 Committed Amount");
									pnlCenter_PNPayment_West.add(lblCommittedAmount_2);
								}
								{
									lblMonthUpdate_2 = new JLabel("Months to Update 2");
									pnlCenter_PNPayment_West.add(lblMonthUpdate_2);

								}
								{
									lblDateSubmitted = new JLabel("Date Submitted");
									pnlCenter_PNPayment_West.add(lblDateSubmitted);
								}
								{
									lblDateGranted = new JLabel("Date Granted");
									pnlCenter_PNPayment_West.add(lblDateGranted);
								}
								{
									lblSignedBy = new JLabel("Signed By");
									pnlCenter_PNPayment_West.add(lblSignedBy);
								}
								{
									lblRemarks = new JLabel("Remarks");
									pnlCenter_PNPayment_West.add(lblRemarks);
								}
							}
							{
								pnlCenter_PNPayment_East = new JPanel(new GridLayout(10, 1, 5, 5));
								pnlCenter_PNPayment_Center.add(pnlCenter_PNPayment_East, BorderLayout.CENTER);
								{


									{
										dteCommintmentDate_1 = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
										pnlCenter_PNPayment_East.add(dteCommintmentDate_1);
										dteCommintmentDate_1.setDate(null);
									}
									{
										txtCommittedAmount_1 = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
										pnlCenter_PNPayment_East.add(txtCommittedAmount_1);
										txtCommittedAmount_1.setFormatterFactory(_JXFormattedTextField.DECIMAL);
										txtCommittedAmount_1.addKeyListener(this);
									}
									{
										cmbMonthUpdateModel_1 = new DefaultComboBoxModel(new String[] {"","1 month", "2 months","3 months","4 months","5 months","to update"});
										cmbMonthUpdate_1 = new JComboBox();
										pnlCenter_PNPayment_East.add(cmbMonthUpdate_1,BorderLayout.CENTER);
										cmbMonthUpdate_1.setModel(cmbMonthUpdateModel_1);
										cmbMonthUpdate_1.setPreferredSize(new Dimension(220, 25));
										cmbMonthUpdate_1.addActionListener(this);}
									{
										dteCommintmentDate_2 = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
										pnlCenter_PNPayment_East.add(dteCommintmentDate_2);
										dteCommintmentDate_2.setDate(null);
									}
									{
										txtCommittedAmount_2 = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
										pnlCenter_PNPayment_East.add(txtCommittedAmount_2);
										txtCommittedAmount_2.setFormatterFactory(_JXFormattedTextField.DECIMAL);
										txtCommittedAmount_2.addKeyListener(this);

									}
									{

										cmbMonthUpdateModel_2 = new DefaultComboBoxModel(new String[] { "","1 month", "2 months","3 months","4 months","5 months","to update" });
										cmbMonthUpdate_2 = new JComboBox();
										pnlCenter_PNPayment_East.add(cmbMonthUpdate_2,BorderLayout.CENTER);
										cmbMonthUpdate_2.setModel(cmbMonthUpdateModel_2);
										cmbMonthUpdate_2.setPreferredSize(new Dimension(220, 25));
										cmbMonthUpdate_2.addActionListener(this);
									}
									{
										dteDateSubmitted = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
										pnlCenter_PNPayment_East.add(dteDateSubmitted);
										dteDateSubmitted.setDate(null);
									}
									{
										dteDateGranted = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
										pnlCenter_PNPayment_East.add(dteDateGranted);
										dteDateGranted.setDate(null);
									}

									{
										JPanel pnlSignedby = new JPanel(new BorderLayout(5,5));
										pnlCenter_PNPayment_East.add(pnlSignedby,BorderLayout.CENTER);
										{
											lookupSignedBy = new _JLookup("Emp. ID ", "Search Signed By", 0);
											pnlSignedby.add(lookupSignedBy,BorderLayout.WEST);
											lookupSignedBy.setLookupSQL(method.getSignedBy());
											lookupSignedBy.setPreferredSize(new Dimension(90, 0));
											lookupSignedBy.addLookupListener(this);
										}
										{
											txtSignedBy = new JTextField();
											pnlSignedby.add(txtSignedBy,BorderLayout.CENTER);
											txtSignedBy.setEditable(false);

										}

									}
									{
										txtRemarks = new JTextField();
										pnlCenter_PNPayment_East.add(txtRemarks);
									}
								}
							}
						}
					 */}
					{
						pnlCenter_East = new  JPanel();
						pnlCenter.add(pnlCenter_East,BorderLayout.EAST);
						pnlCenter_East.setBorder(components.JTBorderFactory.createTitleBorder("Promissory Note - Commitment Status"));// XXX
						pnlCenter_East.setPreferredSize(new Dimension(400, 25));
						pnlCenter_East.setLayout(new BorderLayout(5,5));

						{
							pnlCenter_East_Center = new JPanel(new BorderLayout(5, 5));
							pnlCenter_East.add(pnlCenter_East_Center,BorderLayout.NORTH);
							pnlCenter_East_Center.setPreferredSize(new Dimension(450, 230));
							{
								JPanel pnlNorth = new JPanel(new GridLayout(1, 2, 3, 3));
								pnlCenter_East_Center.add(pnlNorth,BorderLayout.NORTH);
								pnlNorth.setPreferredSize(new Dimension(400, 70));
								{
									{
										lblAppliedPN = new JLabel("<html><b>Applied PN");
										pnlNorth.add(lblAppliedPN);
										lblAppliedPN.setHorizontalAlignment(JTextField.CENTER);
									}
									{
										lblPaymentStatus = new JLabel("<html><b>Payment Status");
										pnlNorth.add(lblPaymentStatus);
										lblPaymentStatus.setHorizontalAlignment(JTextField.CENTER);

									}
								}
							}
							{
								JPanel pnlCenter = new JPanel(new BorderLayout(5,5));
								pnlCenter_East_Center.add(pnlCenter,BorderLayout.CENTER);
								pnlCenter.setPreferredSize(new Dimension(450, 80));
								{
									JPanel pnlCenter_North = new JPanel(new GridLayout(2, 2, 3, 3));
									pnlCenter.add(pnlCenter_North,BorderLayout.NORTH);
									pnlCenter_North.setPreferredSize(new Dimension(450, 75));
									//	pnlCenter_North.setBorder(lineBorder);
									{
										{
											txtCommittedDate = new JTextField();
											pnlCenter_North.add(txtCommittedDate);
											txtCommittedDate.setHorizontalAlignment(JTextField.CENTER);
											txtCommittedDate.setPreferredSize(new Dimension(100, 25));
											txtCommittedDate.setEditable(false);
										}
										{
											dteDatePaid = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
											pnlCenter_North.add(dteDatePaid);
											dteDatePaid.setDate(null);
										}
										{
											lblCommittedDate_PN = new JLabel("<html><i>Committed Date");
											pnlCenter_North.add(lblCommittedDate_PN);
											lblCommittedDate_PN.setHorizontalAlignment(JTextField.CENTER);
											lblCommittedDate_PN.setVerticalAlignment(JTextField.TOP);

										}
										{
											lblDatePaid_PN= new JLabel("<html><i>Date Paid");
											pnlCenter_North.add(lblDatePaid_PN);
											lblDatePaid_PN.setHorizontalAlignment(JTextField.CENTER);
											lblDatePaid_PN.setVerticalAlignment(JTextField.TOP);

										}
									}
								}
								{
									JPanel pnlCenter_Center = new JPanel(new GridLayout(2, 2, 3, 3));
									pnlCenter.add(pnlCenter_Center,BorderLayout.CENTER);
									pnlCenter_Center.setPreferredSize(new Dimension(450, 80));
									{
										{
											txtTotalCommittedAmt = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
											pnlCenter_Center.add(txtTotalCommittedAmt);
											txtTotalCommittedAmt.	setFormatterFactory(_JXFormattedTextField.DECIMAL);
											txtTotalCommittedAmt.setEditable(false);
										}
										{
											txtTotalAmountPaid = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
											pnlCenter_Center.add(txtTotalAmountPaid);
											txtTotalAmountPaid.	setFormatterFactory(_JXFormattedTextField.DECIMAL);
										}
										{
											lblTotalCommittedAmt = new JLabel("<html><i>Total Committed Amount");
											pnlCenter_Center.add(lblTotalCommittedAmt);
											lblTotalCommittedAmt.setHorizontalAlignment(JTextField.CENTER);
											lblTotalCommittedAmt.setVerticalAlignment(JTextField.TOP);
										}
										{
											lblTotalAmountPaid = new JLabel("<html><i>Total Amount Paid");
											pnlCenter_Center.add(lblTotalAmountPaid);
											lblTotalAmountPaid.setHorizontalAlignment(JTextField.CENTER);
											lblTotalAmountPaid.setVerticalAlignment(JTextField.TOP);
										}
									}
								}
							}
						}
					}
				}//pnlCenter
				{
					pnlSouth = new JPanel(new BorderLayout(5,5));
					pnlMain.add(pnlSouth,BorderLayout.SOUTH);
					pnlSouth.setPreferredSize(new Dimension(pnlCenter.getWidth(), 40));

					{/*
						pnlSouth_North = new JPanel(new BorderLayout(5,5));
						pnlSouth.add(pnlSouth_North,BorderLayout.NORTH);
						pnlSouth_North.setPreferredSize(new Dimension(400, 20));
						//pnlSouth_North.setBorder(lineBorder);
						{
							JPanel pnlLabel = new JPanel(new GridLayout(1, 1, 3, 3));
							pnlSouth_North.add(pnlLabel,BorderLayout.WEST);

							{

								lblApproved = new JLabel("Approved by :");
								pnlLabel.add(lblApproved);
								//lblApproved.setPreferredSize(new Dimension(74, 25));
							}
							{
								JPanel pnlApproved = new JPanel(new BorderLayout(5,5));
								pnlSouth_North.add(pnlApproved,BorderLayout.CENTER);
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
					 */}
					{


						pnlSouth_Center = new JPanel(new GridLayout(1, 6, 3, 3));
						pnlSouth.add(pnlSouth_Center,BorderLayout.CENTER);
						pnlSouth_Center.setPreferredSize(new Dimension(400, 30));
						{
							{
								btnAdd = new _JButton("Add New");
								pnlSouth_Center.add(btnAdd);
								btnAdd.setPreferredSize(new Dimension(200, 40));
								btnAdd.setActionCommand("Add");
								grpButton.add(btnAdd);
								btnAdd.addActionListener(this);
							}
							{
								btnEdit = new _JButton("Edit");
								pnlSouth_Center.add(btnEdit);
								btnEdit.setPreferredSize(new Dimension(200, 40));
								btnEdit.setActionCommand("Edit");
								grpButton.add(btnEdit);
								btnEdit.addActionListener(this);
							}
							{
								btnSave = new _JButton("Save");
								pnlSouth_Center.add(btnSave);
								btnSave.setPreferredSize(new Dimension(200, 40));
								btnSave.addActionListener(this);
							}
							{

								btnSettle = new _JButton("Settle");
								pnlSouth_Center.add(btnSettle);
								btnSettle.setPreferredSize(new Dimension(200, 40));
								btnSettle.setActionCommand("Settle");
								grpButton.add(btnSettle);
								btnSettle.addActionListener(this);

							}
							{
								btnPreview = new _JButton("Preview(PN Summary)");
								pnlSouth_Center.add(btnPreview);
								btnPreview.setPreferredSize(new Dimension(200, 40));
								btnPreview.addActionListener(this);

							}
							{
								btnCancel = new _JButton("Cancel");
								pnlSouth_Center.add(btnCancel);
								btnCancel.setPreferredSize(new Dimension(200, 40));
								btnCancel.addActionListener(this);
							}
						}
					}

				}
			}//pnlMain
		} catch (Exception e) {
		}
	}

	private void formLoad(){
		//clear();
		//setEnableComponents(false);
		_setEnabled(false);
		visibleNote(false);
		btnState(false, false, false, false, false,false);
		lookupPN_No.setEditable(true);
		lookupClientName.setEditable(true);

	}


	private void setEnableComponents(boolean ena){/*
		//cmbProcessPN.setEnabled(ena);
		lookupPN_No.setEnabled(ena);

		setEnable(pnlNorth_Center_West, ena);
		setEnable(pnlLookUp, ena);
		setEnable(pnlProject_West, ena);
		setEnable(pnlPastDue_West, ena);
		setEnable(pnlPastDue_Amt_West, ena);
		setEnable(pnlCenter_PNPayment_West, ena);
		txtAmountDue.setEnabled(ena);

		txtProjID.setEnabled(ena);
		txtProjDesc.setEnabled(ena);
		txtPBLID.setEnabled(ena);
		txtPBLDesc.setEnabled(ena);
		txtTelNo.setEnabled(ena);
		txtMobile.setEnabled(ena);
		txtMoUpdate.setEnabled(ena);
		txtNoDays.setEnabled(ena);
		txtDPMA.setEnabled(ena);
		txtAmountDue.setEnabled(ena);
		txtLastDatePaid.setEnabled(ena);
		txtDefaultDate.setEnabled(ena);

		dteCommintmentDate_1.setEnabled(ena);
		dteCommintmentDate_2.setEnabled(ena);
		txtCommittedAmount_1.setEnabled(ena);
		txtCommittedAmount_2.setEnabled(ena);
		cmbMonthUpdate_1.setEnabled(ena);
		cmbMonthUpdate.setEnabled(ena);
		dteDateGranted.setEnabled(ena);
		dteDateSubmitted.setEnabled(ena);
		lookupSignedBy.setEnabled(ena);
		txtSignedBy.setEnabled(ena);
		txtRemarks.setEnabled(ena);
		chkSched.setEnabled(ena);

		txtCommittedDate.setEnabled(ena);
		dteDatePaid.setEnabled(ena);
		lblCommittedDate_PN.setEnabled(ena);
		lblDatePaid_PN.setEnabled(ena);

		txtTotalAmountPaid.setEnabled(ena);
		txtTotalCommittedAmt.setEnabled(ena);
		lblTotalAmountPaid.setEnabled(ena);
		lblTotalCommittedAmt.setEnabled(ena);

		if (chkSched.isSelected()) {
			lblCommintmentDate_2.setEnabled(true);
			lblCommittedAmount_2.setEnabled(true);
			dteCommintmentDate_2.setEnabled(true);
			txtCommittedAmount_2.setEnabled(true);
			lblMonthUpdate_2.setEnabled(true);
			cmbMonthUpdate.setEnabled(true);
		}else{
			lblCommintmentDate_2.setEnabled(false);
			lblCommittedAmount_2.setEnabled(false);
			dteCommintmentDate_2.setEnabled(false);
			txtCommittedAmount_2.setEnabled(false);
			lblMonthUpdate_2.setEnabled(false);
			cmbMonthUpdate.setEnabled(false);
			txtCommittedAmount_2.setValue(null);
			dteCommintmentDate_2.setDate(null);
		}

		btnClientInfo.setEnabled(ena);

	 */}
	@SuppressWarnings("rawtypes")
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
			clear();
			//cmbProcessPN.setEnabled(true);
			//	cmbProcessPN.setSelectedIndex(0);
			lookupPN_No.setEnabled(true);
			lookupPN_No.setEditable(false);
			//setEnable(pnlNorth_Center_West, true);
			//setEnable(pnlLookUp, true);
			_setDisable_new ();
			lookupPN_No.setValue(method.getPN_no());
			btnState(false, false, true, true, false, false);
		}



		if (e.getActionCommand().equals("Edit")){

			grpButton.setSelected(btnEdit.getModel(), true);

			setEnableComponents(true);
			btnState(false, false, true, true, false, false);

			_setDisable();
			lookupPN_No.setEditable(false);
			lookupClientName.setEditable(false);
			/*
			_JLookupTable dlg = new _JLookupTable(FncGlobal.homeMDI, null, "Edit", method.LookupEdit(), false);
			dlg.setLocationRelativeTo(FncGlobal.homeMDI);
			dlg.setVisible(true);

		//	Object[] data = dlg.getReturnDataSet();
				if (data != null )  {		
					pgSelect db = new pgSelect();
					_pn_no = data[0].toString();
					db.select(method.getEditDetails(_pn_no));

					if (db.isNotNull()) {

						lookupPN_No.setText(_pn_no);
						lookupClientName.setText(db.Result[0][1].toString());
						txtClientName.setText(db.Result[0][2].toString());
						txtProjID.setText(db.Result[0][7].toString());
						txtProjDesc.setText(db.Result[0][4].toString());
						txtPBLID.setText(db.Result[0][8].toString());
						txtPBLDesc.setText(db.Result[0][8].toString());


						txtTelNo.setText((String) (db.Result[0][5]== null ? "-" : db.Result[0][5]));
						txtMobile.setText((String) (db.Result[0][6]== null ? "-" : db.Result[0][6]) );
						txtNoDays.setText(db.Result[0][11].toString()+" days");
						txtMoUpdate.setText(db.Result[0][10].toString()+" month(s)");

						txtDPMA.setText(db.Result[0][12].toString());
						txtAmountDue.setValue((BigDecimal)db.Result[0][13]);
						txtLastDatePaid.setText(db.Result[0][14].toString());
						txtDefaultDate.setText(db.Result[0][15].toString());

						dteCommintmentDate_1.setDate((Date)db.Result[0][16]);
						txtCommittedAmount_1.setValue((BigDecimal)db.Result[0][17]);
						dteCommintmentDate_2.setDate((Date) ((Date)db.Result[0][18] == null ? null : (Date)db.Result[0][18]) );

						///BigDecimal CommittedAmount_2 = new BigDecimal(db.Result[0][19].toString());

				    	txtCommittedAmount_2.setValue((BigDecimal) db.Result[0][19] == null ? null:  (BigDecimal)db.Result[0][19]  );
						dteDateSubmitted.setDate((Date)db.Result[0][20]);
						dteDateGranted.setDate((Date)db.Result[0][21]);
						lookupSignedBy.setText(db.Result[0][22].toString());
						txtRemarks.setText(db.Result[0][23].toString());
						noofdays =Integer.valueOf(db.Result[0][11].toString());

						if (dteCommintmentDate_2.getDate() == null) {
							chkSched.setSelected(false);
						}else{
							chkSched.setSelected(true);
						}
						db.select("select (select _get_client_name(a.entity_id))  from em_employee a where emp_code= '"+db.Result[0][22].toString()+"' ");
						if (db.isNotNull()) {
							txtSignedBy.setText(db.Result[0][0].toString());
						}

					}

					setEnableComponents(true);
					btnState(false, false, true, true, false, false);
					_setDisable();
				}else{
					return;
				}
			 */}

		if (e.getSource().equals(btnSave)) {
			toPost();
		}

		if (e.getSource().equals(btnSettle)) {
			grpButton.setSelected(btnSettle.getModel(), true);

			setEnableComponents(true);
			btnState(false, false, true, true, false, true);

			_setDisable();
			lookupPN_No.setEditable(false);
			lookupClientName.setEditable(false);

			btnSave.setText("Post");

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

				btngrpBuyerType.clearSelection();
				clear();
				formLoad(); 
				lookupPN_No.setEnabled(true);
				btnState(true, false, false, false, false, true); 
			}
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

	}  

	@Override
	public void lookupPerformed(LookupEvent e) { //XXX lookupPerformed
		pgSelect dbS = new pgSelect();
		if( e.getSource().equals(lookupCompany)){ 
			Object[] data = ((_JLookup)e.getSource()).getDataSet();

			if(data != null){

				co_id = data[0].toString();
				company = data[1].toString();
				company_logo = data[3].toString();
				txtCompany.setText(company);

				lookupPN_No.setEnabled(true);
				btnState(true, false, false, false, false, true); 
			}
		}
		if( e.getSource().equals(lookupPN_No)){
			System.out.println("lookupPN_No");
			Object[] data = ((_JLookup)e.getSource()).getDataSet();

			if(data != null){

				pgSelect db = new pgSelect();
				///_pn_no = data[0].toString();
				db.select(method.getEditDetails(data[0].toString()));

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

					dteCommintmentDate_1.setDate((Date)db.Result[0][16]);
					txtCommittedAmount_1.setValue((BigDecimal)db.Result[0][17]);
					dteCommintmentDate_2.setDate((Date) ((Date)db.Result[0][18] == null ? null : (Date)db.Result[0][18]) );

					txtCommittedAmount_2.setValue((BigDecimal) db.Result[0][19] == null ? null:  (BigDecimal)db.Result[0][19]  );
					dteDateSubmitted.setDate((Date)db.Result[0][20]);
					dteDateGranted.setDate((Date)db.Result[0][21]);
					lookupSignedBy.setValue(db.Result[0][22].toString());
					txtRemarks.setText(db.Result[0][23].toString());
					noofdays =Integer.valueOf(db.Result[0][11].toString());

					if (dteCommintmentDate_2.getDate() == null) {
						chkSched.setSelected(false);
					}else{
						chkSched.setSelected(true);
					}
					db.select("select (select _get_client_name(a.entity_id))  from em_employee a where emp_code= '"+db.Result[0][22].toString()+"' ");
					if (db.isNotNull()) {
						txtSignedBy.setText(db.Result[0][0].toString());
					}

				}

				dbS.select(method.getPNCommitmentStatus(_entity_id, _proj_id, _pbl_id, _seq_no));
				if (dbS.isNotNull()) {
					_committed_datePN = dbS.Result[0][0].toString();
					_datepaid = (Date) dbS.Result[0][1];
					BigDecimal total_committed_amt = new BigDecimal(dbS.Result[0][2].toString());
					BigDecimal total_amount_paid = new BigDecimal(dbS.Result[0][3].toString());
					//txtAppliedPN.setText(dbS); 
					txtCommittedDate.setText(_committed_datePN);
					dteDatePaid.setDate(_datepaid);
					txtTotalCommittedAmt.setValue(total_committed_amt);
					txtTotalAmountPaid.setValue(total_amount_paid);

					//if (total_committed_amt != null && total_committed_amt.compareTo(total_amount_paid) == 1 ) {
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
				}

				if (lblNote_Client.getText().contains("Committment is already settled")) {
					btnState(false, false, false, true, false, false);
				}else{
					btnState(false, true, false, true, true, false); 
				}
			}

		}
		if(e.getSource().equals(lookupClientName)){

			Object[] data = ((_JLookup)e.getSource()).getDataSet();
			if(data != null){
				
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

				lookupClientName.setValue(data[0].toString());
				txtClientName.setText(data[1].toString());
				txtProjID.setText(_proj_id);
				txtProjDesc.setText(data[3].toString());
				txtPBLID.setText(_pbl_id);
				txtPBLDesc.setText(data[2].toString());
				txtTelNo.setText(data[4] == null ? "-" : data[4].toString());
				txtMobile.setText(data[5] == null ? "-" : data[5].toString());

				btnClientInfo.setEnabled(true);				
				this.setComponentsEnabled(pnlProject_West, true);

				dbS.select(method.displayPastDue(_entity_id, _proj_id, _pbl_id, _seq_no, table_name));

				_months_pd = Integer.valueOf(dbS.Result[0][1].toString());
				_days_pd = Integer.valueOf(dbS.Result[0][0].toString());
				_part_id =  (String) (dbS.Result[0][8]== null ? "-" : dbS.Result[0][8]);    

				if (dbS.isNotNull()) {
					
					noofdays =  Integer.valueOf(dbS.Result[0][0].toString());

					if(noofdays == 0){
						lblNote_Client.setVisible(true);
						lblNote_Client.setText("<html><i><font color = \"black\">Note  :  <font color = \"red\">Client has no Past Due Payments");

					}else{
						txtNoDays.setText(dbS.Result[0][0].toString()+" days");
						txtMoUpdate.setText(dbS.Result[0][1].toString()+" month(s)");
						amountdue = (BigDecimal) dbS.Result[0][2];
						txtAmountDue.setValue(amountdue);
						txtLastDatePaid.setText(dbS.Result[0][3].toString());
						txtDefaultDate.setText(dbS.Result[0][4].toString());
						txtDPMA.setText(dbS.Result[0][5].toString());
						lblNote_Client.setVisible(false);

						/*	if (cmbProcessPN.getSelectedIndex()==1) {
							dbS.select(method.getPNCommitmentStatus(_entity_id, _proj_id, _pbl_id, _seq_no));

							_committed_datePN = dbS.Result[0][0].toString();
							_datepaid = (Date) dbS.Result[0][1];
							BigDecimal total_committed_amt = new BigDecimal(dbS.Result[0][2].toString());
							BigDecimal total_amount_paid = new BigDecimal(dbS.Result[0][3].toString());
							//txtAppliedPN.setText(dbS); 
							txtCommittedDate.setText(_committed_datePN);
							dteDatePaid.setDate(_datepaid);
							txtTotalCommittedAmt.setText(String.valueOf(total_committed_amt));
							txtTotalAmountPaid.setText(String.valueOf(total_amount_paid));

							//if (total_committed_amt != null && total_committed_amt.compareTo(total_amount_paid) == 1 ) {
							if(Double.parseDouble(String.valueOf(total_committed_amt)) > Double.parseDouble(String.valueOf(total_amount_paid))){
								System.out.println("not yet Settle");

								lblNote_CIient.setText("<html><i><font color = \"black\">Note  :  <font color = \"red\">PN-Committment is not yet Settle");
								lblNote_CIient.setVisible(true);
							}

							if(Double.parseDouble(String.valueOf(total_committed_amt)) <= Double.parseDouble(String.valueOf(total_amount_paid))){
								System.out.println("PN-Committment is already Settle");
								lblNote_CIient.setText("<html><i><font color = \"black\">Note  :  <font color = \"red\">PN-Committment is already Settle");
								lblNote_CIient.setVisible(true);
							}
						}*/
					}

					_setDisable();
				}
			}
		}//lookupClientName

		if (e.getSource().equals(lookupSignedBy)) {
			Object[] data = ((_JLookup)e.getSource()).getDataSet();
			if(data != null){
				txtSignedBy.setText(data[1].toString());
				txtSignedBy.setCaretPosition(0);
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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void updateCommit() {
		scrollCommitAmountTotal.setRowHeaderView(FncTables.getRowHeader_Footer(Integer.toString(tblCommit.getRowCount())));
		tblCommit.packAll();

		for (int row = 0; row < tblCommit.getRowCount(); row++) {
			((DefaultListModel) rowHeaderCommit.getModel()).setElementAt(row + 1, row);
		}
	}

	private Boolean toPost() { //XXX POST

		if (grpButton.getSelection().getActionCommand().equals("Add") || grpButton.getSelection().getActionCommand().equals("Edit")) {
			if (!chkSched.isSelected()) {
				if(txtCommittedAmount_1.getValued() == null || ((BigDecimal)txtCommittedAmount_1.getValued()).compareTo(FncBigDecimal.zeroValue()) <= 0
						|| dteCommintmentDate_1.getDate()== null || dteDateSubmitted.getDate()== null ||  dteDateGranted.getDate()== null 
						|| txtSignedBy.getText().equals("") || txtRemarks.getText().equals("")){
					JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please fill up all required Details before Posting   ", "Save", JOptionPane.WARNING_MESSAGE);
					return false;
				}

			}else{
				if(txtCommittedAmount_1.getValued() == null || ((BigDecimal)txtCommittedAmount_1.getValued()).compareTo(FncBigDecimal.zeroValue()) <= 0
						|| dteCommintmentDate_1.getDate()== null || dteDateSubmitted.getDate()== null ||  dteDateGranted.getDate()== null 
						|| txtSignedBy.getText().equals("") || txtRemarks.getText().equals("")
						||txtCommittedAmount_2.getValued() == null || ((BigDecimal)txtCommittedAmount_2.getValued()).compareTo(FncBigDecimal.zeroValue()) <= 0
						|| dteCommintmentDate_1.getDate()== null){
					JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please fill up all required Details before Posting ", "Save", JOptionPane.WARNING_MESSAGE);
					return false;
				}


			}

			String amount_due = txtAmountDue.getValued().toString().replace(",","");
			Double amt1 = Double.parseDouble(String.valueOf(txtCommittedAmount_1.getValue()));
			Double amt2 = (double) 0;
			Double totalamt = (double) 0;

			if (txtCommittedAmount_2.getValued() != null){
				amt2 = Double.parseDouble(String.valueOf(txtCommittedAmount_2.getValue())); 

			}
			totalamt = amt1 + amt2;


			if(totalamt < Double.parseDouble(String.valueOf(amount_due))){
				JOptionPane.showMessageDialog(this,"Your enter amount is less than to amount due","Info.", JOptionPane.INFORMATION_MESSAGE);	

			}


			chkSched.setSelected(false);
		}

		if (grpButton.getSelection().getActionCommand().equals("Settle")) {
			BigDecimal amount  = (BigDecimal) txtTotalCommittedAmt.getValued();
			BigDecimal amountpaid  = (BigDecimal) txtTotalAmountPaid.getValued();

			if (txtTotalAmountPaid.getValued() == null ||((BigDecimal)txtTotalAmountPaid.getValued()).compareTo(FncBigDecimal.zeroValue()) <= 0 || dteDatePaid.getDate()== null ) {
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please fill up all required Details before Saving   ", "Incomplete Details", JOptionPane.WARNING_MESSAGE);
				return false;
			}
			if(!(Double.parseDouble(String.valueOf(amount.doubleValue())) <= Double.parseDouble(String.valueOf(amountpaid.doubleValue())))){
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please enter right amount   ", "Invalid", JOptionPane.WARNING_MESSAGE);
				txtTotalAmountPaid.setValue(FncBigDecimal.zeroValue());
				return false;
			}
			if (txtApproved.getText().isEmpty()) {
				JOptionPane.showMessageDialog( getContentPane(), "Please Select the Approving Officer for this Settled Account  ", "Invalid", JOptionPane.OK_OPTION );
				return false;
			}
		}

		int response = JOptionPane.showConfirmDialog(this, "Are you sure you want to Save PN Details?  ", "Save", 	JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		if (response == JOptionPane.YES_OPTION) {

			new Thread(new ForPosting()).start();
			System.out.println("----SAVE" );
			JOptionPane.showMessageDialog(this,"PN / Commitment Details Saved","Successful", JOptionPane.INFORMATION_MESSAGE);			

			formLoad();
		}
		return true;
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
			mapParameters.put("pn_no", lookupPN_No.getValue().toString());


			if (grpButton.getSelection().getActionCommand().equals("Settle")) {


				FncReport.generateReport("/Reports/rptPromissoryNoteForm.jasper", "Promissory Note",  company, mapParameters);
			}else{
				FncReport.generateReport("/Reports/rptPN.jasper", "List of Accounts With Committments",  company, mapParameters);	
			}

		}
	}
	public class ForPreviewSettled implements Runnable{
		@Override
		public void run() {
			Map<String, Object> mapParameters = new HashMap<String, Object>();
			mapParameters.put("company", company);
			mapParameters.put("co_id", co_id);
			mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
			mapParameters.put("pn_no", lookupPN_No.getValue().toString());


			FncReport.generateReport("/Reports/rptPromissoryNoteForm.jasper", "Promissory Note",  company, mapParameters);

		}
	}
	public class ForPosting implements Runnable{
		@Override
		public void run() {

			FncGlobal.startProgress("Saving  . . .Please wait ");
			pgUpdate dbU = new pgUpdate();
			pgSelect dbS = new pgSelect();

			_proj_id = txtProjID.getText().toString().trim();
			_pbl_id = txtPBLID.getText().toString().trim();

			dbS.select("select get_unit_id('"+_proj_id+"', '"+_pbl_id+"')");

			_unit_id = dbS.Result[0][0].toString().trim();
			_entity_id = lookupClientName.getValue().trim();

			String _lastpaydate =  txtLastDatePaid.getText();
			String _defaultdate = txtDefaultDate.getText();
			System.out.println("2***" +  txtCommittedAmount_2.getValued());
			Date _datecommitted1 =   dteCommintmentDate_1.getDate();
			String _committed_amt1 = txtCommittedAmount_1.getValued().toString().replace(",","") ;
			Date _datecommitted2=   dteCommintmentDate_2.getDate();
			String _committed_amt2 = txtCommittedAmount_2.getValued() == null ? "" :  txtCommittedAmount_2.getValued().toString().replace(",","");
			Date _datesubmitted = dteDateSubmitted.getDate();
			Date _dategranted =  dteDateGranted.getDate();
			String _signedby = lookupSignedBy.getValue().toString().trim();
			String _remarks = txtRemarks.getText().toString();
			String _postedby = UserInfo.EmployeeCode;
			//			String months_update_1 = cmbMonthUpdate_1.getSelectedItem().toString();
			///		String months_update_2 = cmbMonthUpdate.getSelectedItem().toString();

			if (grpButton.getSelection().getActionCommand().equals("Add")) {

				dbS.select("select _get_next_scheddate('"+_entity_id+"', '"+_proj_id+"', '"+_pbl_id+"', "+_seq_no+", current_date)");
				Timestamp _duedate = Timestamp.valueOf(dbS.Result[0][0].toString());

				
				
				/*		dbU.executeUpdate(method.PostingSQL(_entity_id, _proj_id, _unit_id, _pbl_id, _seq_no, _part_id,
						_lastpaydate, _defaultdate, _duedate, _months_pd, _days_pd, amountdue, _datecommitted1, 
						_committed_amt1, _datecommitted2, _committed_amt2, _datesubmitted, _dategranted, _signedby, _remarks,  _postedby,lookupPN_No.getValue(),months_update_1,months_update_2), true);*/
			}

			if (grpButton.getSelection().getActionCommand().equals("Edit")) {

				//dbU.executeUpdate(method.SaveEdit(lookupPN_No.getValue(), _datecommitted1, _committed_amt1, _datecommitted2, _committed_amt2, _datesubmitted, _dategranted, _signedby, _remarks, _postedby,months_update_1,months_update_2), true);
			}

			if (grpButton.getSelection().getActionCommand().equals("Settle")) {

				Date _date_paid = dteDatePaid.getDate();
				String total_amount_paid = txtTotalAmountPaid.getValued().toString().replace(",","");
				String total_committed_amount = txtTotalCommittedAmt.getValued().toString().replace(",","");

				Date _date_Settle = null;
				if(Double.parseDouble(String.valueOf(total_committed_amount)) > Double.parseDouble(String.valueOf(total_amount_paid))){
					_date_Settle = null;

				}
				if(Double.parseDouble(String.valueOf(total_committed_amount)) <= Double.parseDouble(String.valueOf(total_amount_paid))){
					_date_Settle = _date_paid;
				}



				dbU.executeUpdate(method.SaveSettledAccount(lookupPN_No.getValue(), _date_paid, total_amount_paid, _date_Settle,lookupApproved.getValue()), true);
				new Thread(new ForPreviewSettled()).start();
			}

			dbU.commit();
			FncGlobal.stopProgress();
		}
	}

	private void btnState(Boolean sAddNew, Boolean sEdit, Boolean sSave, Boolean sCancel, Boolean sSettle, Boolean sPreview){
		btnAdd.setEnabled(sAddNew);
		btnEdit.setEnabled(sEdit);
		btnSave.setEnabled(sSave);
		btnCancel.setEnabled(sCancel);
		btnSettle.setEnabled(sSettle);
		btnPreview.setEnabled(sPreview);
	}//end of btnState

	private void visibleNote(Boolean ena){

		lblNote_Client.setVisible(ena);
	}
	private void clearpastdue(){

		txtMoUpdate.setText("");
		txtNoDays.setText("");
		txtDPMA.setText("");
		txtAmountDue.setValue(null);
		txtLastDatePaid.setText("");
		txtDefaultDate.setText("");
	}


	private void _setEnabled(boolean ena){

		this.setComponentsEnabled(pnlCenter, ena);
		this.setComponentsEnabled(pnlNorth_Center, ena);
	}

	
	private void _setDisable_new(){

		if(grpButton.getSelection().getActionCommand().equals("Add")){

			this.setComponentsEnabled(pnlNorth_Center, true);
		}
	}


	private void _setDisable(){
		if(grpButton.getSelection().getActionCommand().equals("Edit") || grpButton.getSelection().getActionCommand().equals("Add")){

			if(noofdays != null){
				if (noofdays == 0) {
				/*	//setEnable(pnlCenter_PNPayment_West, false);
					this.setComponentsEnabled(pnlCenter_PNPayment_West, true);

					chkSched.setEnabled(false);

					dteCommintmentDate_1.setEnabled(false);
					dteCommintmentDate_2.setEnabled(false);
					txtCommittedAmount_1.setEnabled(false);
					txtCommittedAmount_2.setEnabled(false);
					//		cmbMonthUpdate_1.setEnabled(false);
					//		cmbMonthUpdate.setEnabled(false);
					dteDateGranted.setEnabled(false);
					dteDateSubmitted.setEnabled(false);
					lookupSignedBy.setEnabled(false);
					txtSignedBy.setEnabled(false);
					lookupSignedBy.setEnabled(false);
					txtRemarks.setEnabled(false);
*/
					this.setComponentsEnabled(pnlCenter_East, false);
					
					
				}else{
					this.setComponentsEnabled(pnlCenter_East, true);

				}
			}
			txtCommittedDate.setEnabled(false);
			dteDatePaid.setEnabled(false);
			lblCommittedDate_PN.setEnabled(false);
			lblDatePaid_PN.setEnabled(false);

			txtTotalAmountPaid.setEnabled(false);
			txtTotalCommittedAmt.setEnabled(false);
			lblTotalAmountPaid.setEnabled(false);
			lblTotalCommittedAmt.setEnabled(false);

			/*if (chkSched.isSelected()) {
				lblCommintmentDate_2.setEnabled(true);
				lblCommittedAmount_2.setEnabled(true);
				dteCommintmentDate_2.setEnabled(true);
				txtCommittedAmount_2.setEnabled(true);
				lblMonthUpdate_2.setEnabled(true);
				cmbMonthUpdate.setEnabled(true);


			}else{
				lblCommintmentDate_2.setEnabled(false);
				lblCommittedAmount_2.setEnabled(false);
				dteCommintmentDate_2.setEnabled(false); 
				txtCommittedAmount_2.setEnabled(false);
				lblMonthUpdate_2.setEnabled(false); 
				cmbMonthUpdate.setEnabled(false);
			}*/
		}

		if(grpButton.getSelection().getActionCommand().equals("Settle")){

			dteCommintmentDate_1.setEnabled(false);
			dteCommintmentDate_2.setEnabled(false);
			txtCommittedAmount_1.setEnabled(false);
			txtCommittedAmount_2.setEnabled(false);
			//	cmbMonthUpdate_1.setEnabled(false);
			//	cmbMonthUpdate.setEnabled(false);

			dteDateGranted.setEnabled(false);
			dteDateSubmitted.setEnabled(false);
			lookupSignedBy.setEnabled(false);
			txtSignedBy.setEnabled(false);
			txtRemarks.setEnabled(false);
			chkSched.setEnabled(false);
			//setEnable(pnlCenter_PNPayment_West, false);
			this.setComponentsEnabled(pnlCenter_PNPayment_West, false);
			txtCommittedDate.setEnabled(true);
			dteDatePaid.setEnabled(true);
			lblCommittedDate_PN.setEnabled(true);
			lblDatePaid_PN.setEnabled(true);

			txtTotalAmountPaid.setEnabled(true);
			txtTotalCommittedAmt.setEnabled(true);
			lblTotalAmountPaid.setEnabled(true);
			lblTotalCommittedAmt.setEnabled(true);

			if (lblNote_Client.getText().contains("Committment is already Settled")) {
				txtTotalAmountPaid.setEditable(false);
				dteDatePaid.setEditable(false);
				dteDatePaid.setEnabled(false);

			}else{

				txtTotalAmountPaid.setEditable(true);
				dteDatePaid.setEditable(true);
				dteDatePaid.setEnabled(true);
			}
		}
	}

	private void clear(){
		_committed_datePN = "";
		_datepaid = null;
		_entity_id = "";
		_entity_name = "";
		_part_id = "";
		_pbl_id = "";
		_proj_id = "";
		_months_pd = 0;
		_unit_id ="";
		_days_pd = 0;
		amountdue = null;
		noofdays = 0;

		if (!btnAdd.isEnabled()) {
			//lookupCompany.setValue("");
			//txtCompany.setText("");
		}


		lookupPN_No.setValue("");
		lookupClientName.setValue("");
		txtClientName.setText("");
		txtClientName.setText("");
		txtProjID.setText("");
		txtProjDesc.setText("");
		txtPBLID.setText("");
		txtPBLDesc.setText("");
		txtTelNo.setText("");
		txtMobile.setText("");
		//	txtSignedBy.setText("");

		dteDatePaid.setDate(null);
		txtTotalCommittedAmt.setValue(null);
		txtTotalAmountPaid.setValue(null);
		lookupApproved.setValue(null);
		txtApproved.setText("");
		noofdays = null;
		//chkSched.setSelected(false);
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

	@Override
	public void keyPressed(KeyEvent e) {
		int key=e.getKeyCode();
		if (e.getSource().equals(txtCommittedAmount_1)) {
			if(key==KeyEvent.VK_ENTER){
				try {

					String amount_due = txtAmountDue.getValued().toString().replace(",","");

					Double amt2 = (double) 0 ;

					if (txtCommittedAmount_2.getValue() != null) {
						amt2 =  Double.parseDouble(String.valueOf(txtCommittedAmount_2.getValue())); 
					}

					String committedamt1 = txtCommittedAmount_1.getDocument().getText(0, txtCommittedAmount_1.getDocument().getLength()).replace(",","");

					amt2 = Double.parseDouble(String.valueOf(committedamt1)) + amt2;


					if(amt2 < Double.parseDouble(String.valueOf(amount_due))){
						JOptionPane.showMessageDialog(this,"Your entered amount is less than the amount due","Info.", JOptionPane.INFORMATION_MESSAGE);	
						return;
					}

				} catch (BadLocationException e1) {
				}
			}
		}

		if (e.getSource().equals(txtCommittedAmount_2)) {
			if(key==KeyEvent.VK_ENTER){
				try {

					String amount_due = txtAmountDue.getValued().toString().replace(",","");

					Double amt1 = (double) 0 ;

					if (txtCommittedAmount_1.getValue() != null) {
						amt1 =  Double.parseDouble(String.valueOf(txtCommittedAmount_1.getValue())); 
					}

					String committedamt2 = txtCommittedAmount_2.getDocument().getText(0, txtCommittedAmount_2.getDocument().getLength()).replace(",","");

					amt1 = Double.parseDouble(String.valueOf(committedamt2)) + amt1;

					if(amt1 < Double.parseDouble(String.valueOf(amount_due))){
						JOptionPane.showMessageDialog(this,"Your entered amount is less than the amount due","Info.", JOptionPane.INFORMATION_MESSAGE);	
						return;
					}

				} catch (BadLocationException e1) {   
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
	@Override
	public void keyReleased(KeyEvent arg0) {
	}
	@Override
	public void keyTyped(KeyEvent arg0) {

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource().equals(tblCommit)) {
			if (tblCommit.getSelectedColumn()== 0) {
				if (e.getClickCount() ==2) {
					dteCommitDate.setBounds((int)pnlCenter_C.getMousePosition().getX(), (int)pnlCenter_C.getMousePosition().getY(), 0, 0);
					dteCommitDate.getCalendarButton().doClick();
				}

			} // column 0
			if (tblCommit.getSelectedColumn()== 2) { 
				if (e.getClickCount() ==2) {

				}

			} // column 7
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

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

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
}


