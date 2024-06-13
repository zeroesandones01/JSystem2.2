/**
 * 
 */
package Buyers.CreditandCollections;

import java.awt.BorderLayout;
import java.awt.Color;
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
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
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
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableColumn;

import org.jdesktop.swingx.JXFormattedTextField;

import tablemodel.model_CancelActive_PerBatch;
import tablemodel.model_Cancellation_Hold;
import tablemodel.model_Cancellation_Status;
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
import Lookup._JLookupTable;

import com.toedter.calendar.JTextFieldDateEditor;

import Database.pgSelect;
import components._JButton;
import components._JInternalFrame;
import components._JRadioButton;
import components._JTabbedPane;
import components._JTableMain;

/**
 * @author Christian Paquibot
 *
 */
@SuppressWarnings("rawtypes")
public class CancellationProcessing extends _JInternalFrame  implements ActionListener,ChangeListener,LookupListener,MouseListener,MouseMotionListener,KeyListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static String title = "Cancellation Processing";
	public static Dimension frameSize = new Dimension(900, 630);
	private Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	private JPanel pnlMain;
	private JPanel pnlCenter;
	private _JTabbedPane tabpane;
	private JPanel pnlCancelActive;
	private JPanel pnlCancelActive_North;
	private JPanel pnlCancelHoldUnit;
	private JPanel pnlCancelHoldUnit_North;
	private JPanel pnlTagAccounts;
	private JPanel pnlTagAccounts_North;
	private ButtonGroup btngrpPer = new ButtonGroup();
	private _JRadioButton rbtnPerBatch;
	private _JRadioButton rbtnPerAccount;
	private JLabel lblBuyerType;
	private DefaultComboBoxModel cmbBuyerTypeModel;
	private JComboBox cmbBuyerType;
	private JPanel pnlCancelActive_Center;
	private JPanel pnlCancelActive_Center_North;
	private JLabel lblProcessFor;
	private JLabel lblProjID;
	private DefaultComboBoxModel cmbProcessForModel;
	private JComboBox cmbProcessFor;
	private _JLookup lookupProjID;
	private JTextField txtProjectName;
	private _JButton btnGenerateActive;
	private _JButton btnPreviewActive;
	private JPanel pnlCancelActive_Center_Center;
	private JLabel lblApproveBy_Active;
	private _JLookup lookupApproveby_Active;
	private _JButton btnPost_Active;
	private _JButton btnClear_Active;
	private JPanel pnlSouth_Center_Active;
	private JPanel pnlSouth_East_Active;
	private JTextField txtApprovedBy_Active;
	private JPanel pnlCenter_Approved_C_Active;
	private JPanel pnlCancelActive_South;
	private JLabel lblProjID_Hold;
	private _JLookup lookupProjID_Hold;
	private JTextField txtProjectName_Hold;
	private JLabel lblEditable_Hold;
	private JPanel pnlCancelHold_Center;
	private JPanel pnlCancelHold_South;
	private JPanel pnlSouth_Center_Active_Hold;
	private JPanel pnlCenter_Approved_C_Hold;
	private JLabel lblApproveBy_Hold;
	private _JLookup lookupApproveby_Hold;
	private JTextField txtApprovedBy_Hold;
	private JPanel pnlSouth_East_Hold;
	private _JButton btnPost_Hold;
	private _JButton btnClear_Hold;
	private JPanel pnlTagAccounts_North_North;
	private JLabel lblProcessFor_Tag;
	private JLabel lblProjID_Tag;
	private DefaultComboBoxModel cmbProcessForModel_Tag;
	private JComboBox cmbProcessFor_Tag;
	private _JLookup lookupProjID_Tag;
	private JTextField txtProjectName_Tag;
	private _JButton btnGenerate_Tag;
	private _JButton btnPreview_Tag;
	private _JButton btnGenerate_Hold;
	private _JButton btnPreview_Hold;
	private JPanel pnlTagAccounts_South;
	private JLabel lblEditable_Tag;
	private JPanel pnlTagAccounts_Center;
	private JScrollPane scrollPerBatch;
	private model_CancelActive_PerBatch modelPerBatchModel;
	private _JTableMain tblPerBatch;
	private JList rowHeadePerBatch;
	private _FCancellationProcessing functions= new _FCancellationProcessing();
	private _FPromissoryNoteCommintment methods = new _FPromissoryNoteCommintment();
	private String _proj_name;
	private JPanel pnlCancelActive_Center_PerAccount;
	private JLabel lblClientID;
	private _JLookup lookupClientName;
	private JTextField txtClientName;
	private JLabel lblPBL;
	private JLabel lblStage;
	private JTextField txtPBLID;
	private JTextField txtPBLDesc;
	private JTextField txtStage;
	private JLabel lblMonthPD;
	private JLabel lblDaysPD;
	private JLabel lblDefaultDate;
	private JLabel lblAmountDue;
	private JTextField txtMonthPD;
	private JTextField txtDayPD;
	private JTextField txtDefaultDate;
	private _JXFormattedTextField txtAmountDue;
	private JLabel lblRemarks;
	private JTextField txtRemarks;
	private JLabel lblReason;
	private JScrollPane scrollReason;
	private JTextArea txtareaReason;
	private String table_name;

	private _JButton btnPost_Tag;
	private _JButton btnClear_Tag;

	private int tagged;
	private JScrollPane scrollCancelHold;
	private _JTableMain tblCancelHold;
	private model_Cancellation_Hold modelCancelHold;

	private JList rowHeadeCancelHold;
	private JScrollPane scrollCancelTag;
	private model_Cancellation_Status modelCancelTag;
	private _JTableMain tblCancelTag;
	private JList rowHeaderCancelTag;
	private _JDateChooser dteStatus;
	private String _client_name;
	private String _pbldesc;
	private JLabel lblCompany;
	private _JLookup lookupCompany;
	private JTextField txtCompany;
	private String co_id;
	private String company;
	private String company_logo;

	private String _entity_id;
	private String _proj_id;
	private String _pbl_id;
	private Integer _seq_no;
	private String _batchno;
	private Integer _monthspd;
	private Integer _dayspd; 
	private BigDecimal _amountdue;
	private boolean _macedalaw;
	private String _reason;
	private String _remarks;
	private String _cancelledby;
	private String _approvedby;
	private Integer _monthspaid = 0;
	private String buyer_id;
	private Date _default_date;
	private Date _lastpaid_date;
	private String _phase_no;
	private String _user_id;
	private JLabel lblNote;
	private JLabel lblNote1;
	private _JLookup lookupProjID_pa;
	private JTextField txtProjectName_pa;
	private pgSelect db = new pgSelect();
	private BigDecimal _csv_mount;
	private String rplfno;
	private Object forcsv;
	private String _stage;
	private Boolean SelectedItem;
	private JPanel pnlNorth;
	private String lblListof;
	private Date dateFrom;
	private Date dateTo;
	private Boolean printall;
	private DefaultComboBoxModel cmbCancelTypeModel;
	private JComboBox cmbCancelType;
	private String cancel_type;
	private String stage_id;
	private JLabel lblForTestingDate;
	private _JDateChooser dteDueDate;
	private Date _due_date;
	private String title_report;
	private String _reason_id;
	private _JLookup lookupReason;
	private JTextField txtReason_;
	private DefaultComboBoxModel cmbCancelTypeModel_Per;
	private JComboBox cmbCancelType_Per;
	private _JButton btnSave_Active;

	public CancellationProcessing() {
		super(title, true, true, true, true);
		initGui();
		rbtnPerBatch.setSelected(true);
		formload();
		//this.setComponentsEnabled(pnlCancelHoldUnit, false);
		//	this.setComponentsEnabled(pnlTagAccounts, false);

	}

	/**
	 * @param title
	 */
	public CancellationProcessing(String title) {
		super(title);
		initGui();
		rbtnPerBatch.setSelected(true);
		formload();
		setEnabled();
	}

	/**
	 * @param title 
	 * @param resizable
	 * @param closable
	 * @param maximizable
	 * @param iconifiable
	 */
	public CancellationProcessing(String title, boolean resizable,
			boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
	}

	@SuppressWarnings({ "unchecked" })
	private void initGui(){
		try {
			this.setTitle(title);
			this.setSize(frameSize);
			this.setPreferredSize(new Dimension(900, 600));
			getContentPane().setLayout(new BorderLayout());

			{
				pnlMain = new JPanel();
				getContentPane().add(pnlMain,BorderLayout.CENTER);
				pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
				pnlMain.setLayout(new BorderLayout(3,3));

				{
					pnlNorth = new JPanel(new BorderLayout());
					pnlMain.add(pnlNorth, BorderLayout.NORTH);
					pnlNorth.setPreferredSize(new Dimension(pnlMain.getWidth(), 30));
					// pnlNorth.setBorder(lineBorder);

					{

						JPanel pnlLabel = new JPanel(new GridLayout(1, 1, 3, 3));
						pnlNorth.add(pnlLabel,BorderLayout.WEST);
						{

							lblCompany = new JLabel("Company");
							pnlLabel.add(lblCompany);
							lblCompany.setPreferredSize(new Dimension(74, 20));
						}

						JPanel pnlCompany = new JPanel(new BorderLayout(3,3));
						pnlNorth.add(pnlCompany,BorderLayout.CENTER);
						pnlCompany.setPreferredSize(new Dimension(74, 20));
						{
							{

								lookupCompany = new _JLookup("", "Company", 0) ; /// XXX lookupCompany 
								pnlCompany.add(lookupCompany,BorderLayout.WEST);
								lookupCompany.addLookupListener(this);
								lookupCompany.setReturnColumn(0);
								lookupCompany.setLookupSQL(methods.getCompany());
								lookupCompany.setPreferredSize(new Dimension(100, 20));
							}
							{
								txtCompany = new JTextField();
								pnlCompany.add(txtCompany,BorderLayout.CENTER);
								txtCompany.setEditable(false);
								txtCompany.setPreferredSize(new Dimension(100, 20));
							} 
						}
					}
				}

				{
					pnlCenter = new JPanel(new BorderLayout());
					pnlMain.add(pnlCenter, BorderLayout.CENTER);
					{
						tabpane = new _JTabbedPane();
						pnlCenter.add(tabpane,BorderLayout.CENTER);
						tabpane.setVisible(true);
						tabpane.addChangeListener(this);
						{
							pnlCancelActive = new  JPanel(new BorderLayout(5,5));
							tabpane.addTab("Cancellation of Active Accounts", pnlCancelActive);
							{
								pnlCancelActive_North = new JPanel(new BorderLayout(3,3));
								pnlCancelActive.add(pnlCancelActive_North, BorderLayout.NORTH);
								pnlCancelActive_North.setPreferredSize(new Dimension(pnlMain.getWidth(), 60));
								pnlCancelActive_North.setBorder(components.JTBorderFactory.createTitleBorder("Process"));
								{
									JPanel  pnlCancelActive_North_Co = new  JPanel(new BorderLayout(5,5));
									pnlCancelActive_North.add(pnlCancelActive_North_Co,BorderLayout.NORTH);

								}
								{
									JPanel  pnlCancelActive_North_West = new  JPanel(new  GridLayout(1, 2,3,3));
									pnlCancelActive_North.add(pnlCancelActive_North_West,BorderLayout.WEST);

									{
										rbtnPerBatch = new _JRadioButton("Per Batch Cancellation");
										pnlCancelActive_North_West.add(rbtnPerBatch,BorderLayout.EAST);
										btngrpPer.add(rbtnPerBatch);
										rbtnPerBatch.addActionListener(new ActionListener() {
											public void actionPerformed(ActionEvent e) {
												try {

													lblBuyerType.setEnabled(true); 
													cmbBuyerType.setEnabled(true);
													pnlCancelActive_Center.setBorder(components.JTBorderFactory.createTitleBorder("Cancellation Per Batch"));
													pnlCancelActive_Center.add(pnlCancelActive_Center_Center,BorderLayout.CENTER);
													pnlCancelActive_Center_Center.setVisible(true);
													pnlCancelActive_Center_PerAccount.setVisible(false);
													pnlCancelActive_Center_North.setVisible(true);
													btnGenerateActive.setVisible(true);
													btnPreviewActive.setVisible(true);
												} catch (NullPointerException e1) {
												}
											}
										});
									}
									{
										rbtnPerAccount= new _JRadioButton("Per Account Cancellation");
										pnlCancelActive_North_West.add(rbtnPerAccount,BorderLayout.EAST);
										btngrpPer.add(rbtnPerAccount);
										rbtnPerAccount.addActionListener(new ActionListener() {
											public void actionPerformed(ActionEvent e) {

												lblBuyerType.setEnabled(true); 
												cmbBuyerType.setEnabled(true);
												pnlCancelActive_Center.setBorder(components.JTBorderFactory.createTitleBorder("Cancellation Per Account"));
												pnlCancelActive_Center_Center.setVisible(false);
												pnlCancelActive_Center_North.setVisible(false);
												displayPerAccountPanel();
												//lookupClientName.setLookupSQL(functions.getIHFClient());	

											}
										});
									}
									{
										JPanel  pnlCancelActive_North_East = new  JPanel(new  BorderLayout(5, 5));
										pnlCancelActive_North.add(pnlCancelActive_North_East,BorderLayout.EAST);
										pnlCancelActive_North_East.setPreferredSize(new  Dimension(300, 60));
										{
											lblBuyerType = new JLabel("Buyer Type :");
											pnlCancelActive_North_East.add(lblBuyerType,BorderLayout.WEST);
											lblBuyerType.setPreferredSize(new  Dimension(80, 60));
										}
										{
											cmbBuyerTypeModel = new DefaultComboBoxModel(new String[] { "IHF Accounts", "Pag-Ibig Accounts" });
											cmbBuyerType = new JComboBox();
											pnlCancelActive_North_East.add(cmbBuyerType,BorderLayout.CENTER);
											cmbBuyerType.setModel(cmbBuyerTypeModel);
											cmbBuyerType.setPreferredSize(new Dimension(220, 25));
											cmbBuyerType.addActionListener(this);

										}
									}//pnlCancelActive_North_East
								}
							}//pnlCancelActive_North
							{
								pnlCancelActive_Center = new JPanel(new BorderLayout(5,5));
								pnlCancelActive.add(pnlCancelActive_Center, BorderLayout.CENTER);
								pnlCancelActive_Center.setBorder(components.JTBorderFactory.createTitleBorder("Cancellation Per Batch"));
								{
									pnlCancelActive_Center_North = new JPanel(new BorderLayout(5, 5));
									pnlCancelActive_Center.add(pnlCancelActive_Center_North,BorderLayout.NORTH);
									pnlCancelActive_Center_North.setPreferredSize(new Dimension(pnlCancelActive.getWidth(), 90));
									{
										JPanel _pnlLabel = new JPanel(new GridLayout(3, 1, 3, 3));
										pnlCancelActive_Center_North.add(_pnlLabel,BorderLayout.WEST);
										_pnlLabel.setVisible(true);
										
										{
											lblProcessFor = new JLabel(" Process For : ");
											_pnlLabel.add(lblProcessFor);
										}
										{
											lblProjID = new JLabel(" Project ID : ");
											_pnlLabel.add(lblProjID);
										}
										{
											lblForTestingDate = new JLabel(" Due Date : ");
											_pnlLabel.add(lblForTestingDate);
										}
									}
									{

										JPanel pnlProcess = new JPanel(new GridLayout(3, 1, 3, 3));
										pnlCancelActive_Center_North.add(pnlProcess,BorderLayout.CENTER);
										pnlProcess.setPreferredSize(new Dimension(300, 60));
										{

											cmbProcessForModel = new DefaultComboBoxModel(new String[] { " Recommended for Cancellation", " Qualified for CSV Tagging" });
											cmbProcessFor = new JComboBox();
											pnlProcess.add(cmbProcessFor);
											cmbProcessFor.setModel(cmbProcessForModel);
											cmbProcessFor.setPreferredSize(new Dimension(250, 25));
											cmbProcessFor.addActionListener(this);
										}
										{
											JPanel pnlProject = new JPanel(new BorderLayout(3,3));
											pnlProcess.add(pnlProject);
											{
												lookupProjID = new _JLookup("Proj ID", "Search Project", 0);
												pnlProject.add(lookupProjID,BorderLayout.WEST);
												lookupProjID.setPreferredSize(new Dimension(50, 25));
												lookupProjID.addLookupListener(this);

											}
											{
												txtProjectName = new JTextField();
												pnlProject.add(txtProjectName,BorderLayout.CENTER);
												txtProjectName.setEditable(false);
											}
										}
										{

											dteDueDate= new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
											pnlProcess.add(dteDueDate);
											dteDueDate.setDate(null);

										}

									}
									{
										JPanel pnlButton = new JPanel(new GridLayout(1, 2, 3, 3));
										pnlCancelActive_Center_North.add(pnlButton,BorderLayout.EAST);
										pnlButton.setPreferredSize(new Dimension(300, 60));
										{
											btnGenerateActive = new _JButton("Generate");
											pnlButton.add(btnGenerateActive);
											btnGenerateActive.addActionListener(this);
										}
										{
											btnPreviewActive = new _JButton("Preview");
											pnlButton.add(btnPreviewActive);
											btnPreviewActive.addActionListener(this);
										}
									}
								}
								{
									pnlCancelActive_Center_Center = new JPanel(new BorderLayout(5, 5));
									pnlCancelActive_Center.add(pnlCancelActive_Center_Center,BorderLayout.CENTER);
									pnlCancelActive_Center_Center.setBorder(components.JTBorderFactory.createTitleBorder("List of IHF Accounts"));
									pnlCancelActive_Center_Center.setPreferredSize(new Dimension(pnlCancelActive.getWidth(), 300));
									{
										cmbCancelTypeModel = new DefaultComboBoxModel(new String[] {"","Company Initiated", "Buyer Initiated"});
										cmbCancelType.setModel(cmbCancelTypeModel);
										cmbCancelType.addActionListener(this);

									}
									{

										scrollPerBatch = new JScrollPane();
										pnlCancelActive_Center_Center.add(scrollPerBatch, BorderLayout.CENTER);
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
												}
											});

											tblPerBatch = new _JTableMain(modelPerBatchModel);
											scrollPerBatch.setViewportView(tblPerBatch);
											modelPerBatchModel.setEditable(true);
											tblPerBatch.setHorizontalScrollEnabled(true);
											tblPerBatch.addMouseMotionListener(this);
											tblPerBatch.addMouseListener(this);
											tblPerBatch.addKeyListener(this);
											tblPerBatch.packAll();

											TableColumn monthcolumn = tblPerBatch.getColumnModel().getColumn(28);
											monthcolumn.setCellEditor(new DefaultCellEditor(cmbCancelType));

											/** Repaint for Highlight **/
											tblPerBatch.getTableHeader().addMouseListener(new MouseAdapter() {
												public void mouseClicked(MouseEvent evt) {
													tblPerBatch.repaint();
												}
											});
										}
										{
											rowHeadePerBatch = tblPerBatch.getRowHeader();
											rowHeadePerBatch.setModel(new DefaultListModel());
											scrollPerBatch.setRowHeaderView(rowHeadePerBatch);
											scrollPerBatch.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
											scrollPerBatch.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPerBatch.getRowCount())));

										}
									}
								}
								{

									pnlCancelActive_South = new JPanel(new BorderLayout(4,4));
									pnlCancelActive.add(pnlCancelActive_South, BorderLayout.SOUTH);
									pnlCancelActive_South.setPreferredSize(new Dimension(pnlMain.getWidth(), 55));
									{
										JPanel pnlNorth = new  JPanel(new BorderLayout(5, 5));
										pnlCancelActive_South.add(pnlNorth,BorderLayout.NORTH);
									}

									{
										pnlSouth_Center_Active = new  JPanel(new BorderLayout(5, 5));
										pnlCancelActive_South.add(pnlSouth_Center_Active,BorderLayout.CENTER);
										{
											JPanel pnlCenter_Approved = new JPanel(new BorderLayout(5, 5));
											pnlSouth_Center_Active.add(pnlCenter_Approved,BorderLayout.NORTH);
											pnlCenter_Approved.setPreferredSize(new Dimension(0, 5));
											pnlCenter_Approved.setVisible(true);

										}
										{
											pnlCenter_Approved_C_Active = new JPanel(new BorderLayout(5, 5));
											pnlSouth_Center_Active.add(pnlCenter_Approved_C_Active,BorderLayout.CENTER);
											pnlCenter_Approved_C_Active.setVisible(true);
											{
												lblApproveBy_Active = new JLabel(" Approved By :");
												pnlCenter_Approved_C_Active.add(lblApproveBy_Active,BorderLayout.WEST);
											}
											{
												JPanel pnlApproved = new JPanel(new BorderLayout(3, 3));
												pnlCenter_Approved_C_Active.add(pnlApproved,BorderLayout.CENTER);
												pnlApproved.setVisible(true);
												{
													lookupApproveby_Active = new _JLookup("Emp. ID", "Search Employee", 0);
													pnlApproved.add(lookupApproveby_Active,BorderLayout.WEST);
													lookupApproveby_Active.setLookupSQL(functions.getApprovedby());
													lookupApproveby_Active.setPreferredSize(new Dimension(60, 50));
													lookupApproveby_Active.addLookupListener(this);

												}
												{
													txtApprovedBy_Active = new JTextField();
													pnlApproved.add(txtApprovedBy_Active,BorderLayout.CENTER);
													txtApprovedBy_Active.setEditable(false);
												}
											}
										}

										{
											JPanel pnlCenter_Approved_S = new JPanel(new BorderLayout(5, 5));
											pnlSouth_Center_Active.add(pnlCenter_Approved_S,BorderLayout.SOUTH);
											pnlCenter_Approved_S.setPreferredSize(new Dimension(0, 5));
										}	
									}
									{
										pnlSouth_East_Active = new  JPanel(new GridLayout(1, 3, 5, 5));
										pnlCancelActive_South.add(pnlSouth_East_Active,BorderLayout.EAST);
										pnlSouth_East_Active.setPreferredSize(new Dimension(400, 0));


										{
											btnSave_Active = new _JButton("Save");
											pnlSouth_East_Active.add(btnSave_Active);
											btnSave_Active.addActionListener(this);
										}
										{
											btnPost_Active = new _JButton("Post");
											pnlSouth_East_Active.add(btnPost_Active);
											btnPost_Active.addActionListener(this);
										}

										{
											btnClear_Active = new _JButton("Clear");
											pnlSouth_East_Active.add(btnClear_Active);
											btnClear_Active.addActionListener(this);

										}
									}

									JPanel pnlSouth = new  JPanel(new BorderLayout(5, 5));
									pnlCancelActive_South.add(pnlSouth,BorderLayout.SOUTH);

								}
							}
						}//pnlCancelActive
						{/*

							pnlCancelHoldUnit = new  JPanel(new BorderLayout(5,5)); // XXX  Cancellation of Hold Units
							tabpane.addTab("Cancellation of Hold Units", pnlCancelHoldUnit);
							{
								pnlCancelHoldUnit_North = new JPanel();
								pnlCancelHoldUnit.add(pnlCancelHoldUnit_North, BorderLayout.NORTH);
								pnlCancelHoldUnit_North.setPreferredSize(new Dimension(pnlMain.getWidth(),65));
								pnlCancelHoldUnit_North.setLayout(new BorderLayout(5,5));
								{
									JPanel pnlNorth = new JPanel(new BorderLayout(4, 4) );
									pnlCancelHoldUnit_North.add(pnlNorth,BorderLayout.NORTH);
								}
								{
									JPanel pnlNorth = new JPanel(new BorderLayout(4, 4) );
									pnlCancelHoldUnit_North.add(pnlNorth,BorderLayout.CENTER);
									pnlNorth.setPreferredSize(new Dimension(pnlCancelHoldUnit.getWidth(), 40));
									{
										JPanel pnlNorth_North= new JPanel(new BorderLayout(4, 4) );
										pnlNorth.add(pnlNorth_North,BorderLayout.NORTH);
										pnlNorth_North.setPreferredSize(new Dimension(0, 3));
									}
									{
										JPanel pnlNorth_West = new JPanel(new BorderLayout(4, 4) );
										pnlNorth.add(pnlNorth_West,BorderLayout.CENTER);
										{
											JPanel pnlLabel_Hold = new JPanel(new GridLayout(1, 1, 3, 3));
											pnlNorth_West.add(pnlLabel_Hold,BorderLayout.WEST);
											{
												lblProjID_Hold = new JLabel(" Project ID : ");
												pnlLabel_Hold.add(lblProjID_Hold);
											}
										}

										JPanel pnlNorth_Center = new JPanel(new BorderLayout(4, 4) );
										pnlNorth_West.add(pnlNorth_Center);
										pnlNorth_Center.setVisible(true);
										{
											{
												lookupProjID_Hold = new _JLookup("Proj ID", "Search Project", 0);
												pnlNorth_Center.add(lookupProjID_Hold,BorderLayout.WEST);
												lookupProjID_Hold.setPreferredSize(new Dimension(50, 25));
												//lookupProjID_Hold.setLookupSQL(functions.lookupProjects(co_id));
												lookupProjID_Hold.addLookupListener(this);

											}
											{
												txtProjectName_Hold = new JTextField();
												pnlNorth_Center.add(txtProjectName_Hold,BorderLayout.CENTER);
												txtProjectName_Hold.setEditable(false);
											}
										}
									}
									{
										JPanel pnlNorth_South= new JPanel(new BorderLayout() );
										pnlNorth.add(pnlNorth_South,BorderLayout.SOUTH);
										pnlNorth_South.setPreferredSize(new Dimension(0, 20));
										{
											lblEditable_Hold = new JLabel("<html><i>&nbsp;&nbsp;( <font color = \"red\">*</font> ) Editable Columns ( Double Click on Selected cell to input Details )");
											pnlNorth_South.add(lblEditable_Hold);		
										}
									}
									{
										pnlButton_Hold = new JPanel(new GridLayout(1, 2, 3, 3));
										pnlCancelHoldUnit_North.add(pnlButton_Hold,BorderLayout.EAST);
										pnlButton_Hold.setPreferredSize(new Dimension(300, 60));
										{
											btnGenerate_Hold = new _JButton("Generate");
											pnlButton_Hold.add(btnGenerate_Hold);
											btnGenerate_Hold.addActionListener(this);
										}
										{
											btnPreview_Hold = new _JButton("Preview");
											pnlButton_Hold.add(btnPreview_Hold);
											btnPreview_Hold.addActionListener(this);
										}}
								}
							}//
							{
								pnlCancelHold_Center = new JPanel(new BorderLayout(5, 5));
								pnlCancelHoldUnit.add(pnlCancelHold_Center,BorderLayout.CENTER);
								pnlCancelHold_Center.setBorder(components.JTBorderFactory.createTitleBorder("List of IHF Accounts"));
								{


									scrollCancelHold = new JScrollPane();
									pnlCancelHold_Center.add(scrollCancelHold, BorderLayout.CENTER);
									scrollCancelHold.addMouseListener(new MouseAdapter() {
										public void mouseClicked(MouseEvent e) {
											tblCancelHold.clearSelection();
										}
									});
									{

										modelCancelHold = new model_Cancellation_Hold();
										modelCancelHold.addTableModelListener(new TableModelListener() {
											public void tableChanged(TableModelEvent e) {
												//Addition of rows
												if(e.getType() == 1){
													((DefaultListModel)rowHeadeCancelHold.getModel()).addElement(modelCancelHold.getRowCount());

													if(modelCancelHold.getRowCount() == 0){
														rowHeadeCancelHold.setModel(new DefaultListModel());
													}
												}
											}
										});

										tblCancelHold = new _JTableMain(modelCancelHold);
										scrollCancelHold.setViewportView(tblCancelHold);
										modelCancelHold.setEditable(true);
										tblCancelHold.setHorizontalScrollEnabled(true);
										tblCancelHold.addMouseMotionListener(this);
										tblCancelHold.addMouseListener(this);
										tblCancelHold.addKeyListener(this);
										tblCancelHold.hideColumns("Entity ID","Project ID","PBL ID","Seq ID","Unit ID","PartID","Phase","TRA Header ID");
										tblCancelHold.packColumn(9	, 400, 400);
										tblCancelHold.packColumn(10	, 400, 400);

									}
									{
										rowHeadeCancelHold = tblCancelHold.getRowHeader();
										rowHeadeCancelHold.setModel(new DefaultListModel());
										scrollCancelHold.setRowHeaderView(rowHeadeCancelHold);
										scrollCancelHold.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
										scrollCancelHold.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblCancelHold.getRowCount())));

									}
								}
							}
							{

								pnlCancelHold_South = new JPanel(new BorderLayout(4,4));
								pnlCancelHoldUnit.add(pnlCancelHold_South, BorderLayout.SOUTH);
								pnlCancelHold_South.setPreferredSize(new Dimension(pnlMain.getWidth(), 55));
								{
									JPanel pnlNorth = new  JPanel(new BorderLayout(5, 5));
									pnlCancelHold_South.add(pnlNorth,BorderLayout.NORTH);

								}
								{
									pnlSouth_Center_Active_Hold = new  JPanel(new BorderLayout(5, 5));
									pnlCancelHold_South.add(pnlSouth_Center_Active_Hold,BorderLayout.CENTER);
									{
										JPanel pnlCenter_Approved = new JPanel(new BorderLayout(5, 5));
										pnlSouth_Center_Active_Hold.add(pnlCenter_Approved,BorderLayout.NORTH);
										pnlCenter_Approved.setPreferredSize(new Dimension(0, 5));
										pnlCenter_Approved.setVisible(true);

									}
									{
										pnlCenter_Approved_C_Hold = new JPanel(new BorderLayout(5, 5));
										pnlSouth_Center_Active_Hold.add(pnlCenter_Approved_C_Hold,BorderLayout.CENTER);
										pnlCenter_Approved_C_Hold.setVisible(true);
										{
											lblApproveBy_Hold = new JLabel(" Approved By :");
											pnlCenter_Approved_C_Hold.add(lblApproveBy_Hold,BorderLayout.WEST);
										}
										{
											JPanel pnlApproved = new JPanel(new BorderLayout(3, 3));
											pnlCenter_Approved_C_Hold.add(pnlApproved,BorderLayout.CENTER);
											pnlApproved.setVisible(true);
											{
												lookupApproveby_Hold = new _JLookup("Emp. ID", "Search Employee", 0);
												pnlApproved.add(lookupApproveby_Hold,BorderLayout.WEST);
												lookupApproveby_Hold.setPreferredSize(new Dimension(60, 50));
												lookupApproveby_Hold.setLookupSQL(functions.getApprovedby());
												lookupApproveby_Hold.addLookupListener(this);
											}
											{
												txtApprovedBy_Hold = new JTextField();
												pnlApproved.add(txtApprovedBy_Hold,BorderLayout.CENTER);
												txtApprovedBy_Hold.setEditable(false);
											}
										}
										{
											JPanel pnlSouth = new  JPanel(new BorderLayout(5, 5));
											pnlCancelHold_South.add(pnlSouth,BorderLayout.SOUTH);
										}
									}
									{
										JPanel pnlCenter_Approved_S = new JPanel(new BorderLayout(5, 5));
										pnlSouth_Center_Active_Hold.add(pnlCenter_Approved_S,BorderLayout.SOUTH);
										pnlCenter_Approved_S.setPreferredSize(new Dimension(0, 5));
									}	
								}
								{
									pnlSouth_East_Hold = new  JPanel(new GridLayout(1, 2, 5, 5));
									pnlCancelHold_South.add(pnlSouth_East_Hold,BorderLayout.EAST);
									pnlSouth_East_Hold.setPreferredSize(new Dimension(300, 0));
									{
										btnPost_Hold = new _JButton("Post");
										pnlSouth_East_Hold.add(btnPost_Hold);
										btnPost_Hold.addActionListener(this);
									}
									{
										btnClear_Hold = new _JButton("Clear");
										pnlSouth_East_Hold.add(btnClear_Hold);
										btnClear_Hold.addActionListener(this);
									}
								}
							}
						 */}//pnlCancelHoldUnit
						{
							pnlTagAccounts = new  JPanel(new BorderLayout(5,5)); /// ; // XXX  Tag Accounts Status
							tabpane.addTab("Tag Accounts Status", pnlTagAccounts);

							{
								pnlTagAccounts_North = new JPanel();
								pnlTagAccounts.add(pnlTagAccounts_North, BorderLayout.NORTH);
								pnlTagAccounts_North.setPreferredSize(new Dimension(pnlMain.getWidth(),70));
								pnlTagAccounts_North.setLayout(new BorderLayout(5,5));
								//pnlTagAccounts_North.setBorder(components.JTBorderFactory.createTitleBorder("Process"));
								{

									{/*

										pnlTagAccounts_Company = new JPanel(new BorderLayout(5, 5));
										pnlTagAccounts_North.add(pnlTagAccounts_Company,BorderLayout.NORTH);
										pnlTagAccounts_Company.setPreferredSize(new Dimension(pnlCancelActive.getWidth(), 30));


										{

											JPanel pnlLabel = new JPanel(new GridLayout(1, 1, 3, 3));
											pnlTagAccounts_Company.add(pnlLabel,BorderLayout.WEST);
											{

												lblCompany = new JLabel("Company");
												pnlLabel.add(lblCompany);
												lblCompany.setPreferredSize(new Dimension(74, 20));
											}

											JPanel pnlCompany = new JPanel(new BorderLayout(3,3));
											pnlTagAccounts_Company.add(pnlCompany,BorderLayout.CENTER);
											pnlCompany.setPreferredSize(new Dimension(74, 20));
											{
												{

													lookupCompany_Status = new _JLookup("", "Company", 0) ; /// XXX lookupCompany 
													pnlCompany.add(lookupCompany_Status,BorderLayout.WEST);
													lookupCompany_Status.addLookupListener(this);
													lookupCompany_Status.setLookupSQL(methods.getCompany());
													lookupCompany_Status.setPreferredSize(new Dimension(100, 20));
												}
												{
													txtCompany_Status = new JTextField();
													pnlCompany.add(txtCompany_Status,BorderLayout.CENTER);
													txtCompany_Status.setEditable(false);
													txtCompany_Status.setPreferredSize(new Dimension(100, 20));
												} 

											}


										}
									 */}

									{

										pnlTagAccounts_North_North = new JPanel(new BorderLayout(5, 5));
										pnlTagAccounts_North.add(pnlTagAccounts_North_North,BorderLayout.CENTER);
										pnlTagAccounts_North_North.setPreferredSize(new Dimension(pnlCancelActive.getWidth(), 60));

										JPanel pnlNorth = new JPanel(new BorderLayout(5, 5));
										pnlTagAccounts_North_North.add(pnlNorth,BorderLayout.NORTH);
										{
											JPanel pnlLabel = new JPanel(new GridLayout(2, 1, 3, 3));
											pnlTagAccounts_North_North.add(pnlLabel,BorderLayout.WEST);
											{
												lblProcessFor_Tag = new JLabel(" Process For : ");
												pnlLabel.add(lblProcessFor_Tag);
											}
											{
												lblProjID_Tag = new JLabel(" Project ID : ");
												pnlLabel.add(lblProjID_Tag);
											}
										}
										{

											JPanel pnlProcess = new JPanel(new GridLayout(2, 1, 3, 3));
											pnlTagAccounts_North_North.add(pnlProcess,BorderLayout.CENTER);
											pnlProcess.setPreferredSize(new Dimension(300, 60));
											{

												cmbProcessForModel_Tag = new DefaultComboBoxModel(new String[] { "Cancelled Accounts Status", "CSV Status" });
												cmbProcessFor_Tag = new JComboBox();
												pnlProcess.add(cmbProcessFor_Tag);
												cmbProcessFor_Tag.setModel(cmbProcessForModel_Tag);
												cmbProcessFor_Tag.setPreferredSize(new Dimension(250, 25));
												cmbProcessFor_Tag.addActionListener(this);
											}
											{
												JPanel pnlProject = new JPanel(new BorderLayout(3,3));
												pnlProcess.add(pnlProject);

												{
													lookupProjID_Tag = new _JLookup("Proj ID", "Search Project", 0);
													pnlProject.add(lookupProjID_Tag,BorderLayout.WEST);
													lookupProjID_Tag.setPreferredSize(new Dimension(50, 25));
													//lookupProjID_Tag.setLookupSQL(functions.lookupProjects(co_id));
													lookupProjID_Tag.addLookupListener(this);
												}
												{
													txtProjectName_Tag= new JTextField();
													pnlProject.add(txtProjectName_Tag,BorderLayout.CENTER);
													txtProjectName_Tag.setEditable(false);
												}
											}
										}
										{
											JPanel pnlButton = new JPanel(new GridLayout(1, 2, 3, 3));
											pnlTagAccounts_North_North.add(pnlButton,BorderLayout.EAST);
											pnlButton.setPreferredSize(new Dimension(300, 60));
											{
												btnGenerate_Tag = new _JButton("Generate");
												pnlButton.add(btnGenerate_Tag);
												btnGenerate_Tag.addActionListener(this);										
											}
											{
												btnPreview_Tag = new _JButton("Preview");
												pnlButton.add(btnPreview_Tag);
												btnPreview_Tag.addActionListener(this);
											}
										}
										{
											JPanel pnlSouth = new JPanel(new BorderLayout(5, 5));
											pnlTagAccounts_North_North.add(pnlSouth,BorderLayout.SOUTH);
										}
									}
									{
										pnlTagAccounts_Center = new JPanel(new BorderLayout(5,5));
										pnlTagAccounts.add(pnlTagAccounts_Center, BorderLayout.CENTER);
										//pnlTagAccounts_Center.setBorder(lineBorder);	
										pnlTagAccounts_Center.setBorder(components.JTBorderFactory.createTitleBorder("Status of Cancellation Accounts"));
										{
											dteStatus = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
											pnlTagAccounts_Center.add(dteStatus);
											dteStatus.setDate(null);
											((JTextFieldDateEditor)dteStatus.getDateEditor()).setEditable(false);
											dteStatus.getCalendarButton().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
											((JTextFieldDateEditor)dteStatus.getDateEditor()).getDocument().addDocumentListener(new DocumentListener() {
												public void insertUpdate(DocumentEvent evt) {
													SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");
													if (modelCancelTag.getValueAt(tblCancelTag.getSelectedRow(),0).equals(true)) {
														modelCancelTag.setValueAt(""+sdf.format(dteStatus.getDate()),tblCancelTag.getSelectedRow(),7);
													}
												}
												public void changedUpdate(DocumentEvent e) {}
												public void removeUpdate(DocumentEvent e) {}
											});
										}
										{

											scrollCancelTag = new JScrollPane();
											pnlTagAccounts_Center.add(scrollCancelTag, BorderLayout.CENTER);
											scrollCancelTag.addMouseListener(new MouseAdapter() {
												public void mouseClicked(MouseEvent e) {
													tblCancelTag.clearSelection();
												}
											});
											{

												modelCancelTag = new model_Cancellation_Status();
												modelCancelTag.addTableModelListener(new TableModelListener() {
													public void tableChanged(TableModelEvent e) {
														//Addition of rows
														if(e.getType() == 1){
															((DefaultListModel)rowHeaderCancelTag.getModel()).addElement(modelCancelTag.getRowCount());

															if(modelCancelTag.getRowCount() == 0){
																rowHeaderCancelTag.setModel(new DefaultListModel());
															}
														}
													}
												});

												tblCancelTag = new _JTableMain(modelCancelTag);
												scrollCancelTag.setViewportView(tblCancelTag);
												modelCancelTag.setEditable(true);
												tblCancelTag.setHorizontalScrollEnabled(true);
												tblCancelTag.addMouseMotionListener(this);
												tblCancelTag.addMouseListener(this);
												tblCancelTag.addKeyListener(this);
												tblCancelTag.hideColumns("Status ID","CSV ID","Entity ID","Project ID","PBL ID","Seq ID","Unit ID","PartID","Cancel ID");


												/** Repaint for Highlight **/
												tblCancelTag.getTableHeader().addMouseListener(new MouseAdapter() {
													public void mouseClicked(MouseEvent evt) {
														tblCancelTag.repaint();
													}
												});
											}
											{
												rowHeaderCancelTag = tblCancelTag.getRowHeader();
												rowHeaderCancelTag.setModel(new DefaultListModel());
												scrollCancelTag.setRowHeaderView(rowHeaderCancelTag);
												scrollCancelTag.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
												scrollCancelTag.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblCancelTag.getRowCount())));

											}
										}
									}
									{
										pnlTagAccounts_South = new JPanel(new BorderLayout(5,5));
										pnlTagAccounts.add(pnlTagAccounts_South, BorderLayout.SOUTH);
										pnlTagAccounts_South.setPreferredSize(new Dimension(pnlMain.getWidth(),57));
										{
											JPanel pnlNorth1 = new JPanel(new BorderLayout(5,5));
											pnlTagAccounts_South.add(pnlNorth1,BorderLayout.NORTH);
										}
										{
											JPanel pnlCenter = new JPanel(new BorderLayout(5,5));
											pnlTagAccounts_South.add(pnlCenter,BorderLayout.CENTER);
											{
												lblEditable_Tag = new JLabel("<html><i>&nbsp;&nbsp; ( <font color = \"red\">*</font> ) Editable Columns ( Double Click on Selected cell to input Details )");
												pnlCenter.add(lblEditable_Tag);	
											}

										}
										{
											JPanel pnlSouth_East_Tag = new  JPanel(new GridLayout(1, 2, 5, 5));
											pnlTagAccounts_South.add(pnlSouth_East_Tag,BorderLayout.EAST);
											pnlSouth_East_Tag.setPreferredSize(new Dimension(300, 0));
											{
												btnPost_Tag = new _JButton("Post");
												pnlSouth_East_Tag.add(btnPost_Tag);
												btnPost_Tag.addActionListener(this);
											}
											{
												btnClear_Tag = new _JButton("Clear");
												pnlSouth_East_Tag.add(btnClear_Tag);
												btnClear_Tag.addActionListener(this);
											}

										}
										{
											JPanel pnlSouth = new JPanel(new BorderLayout(5,5));
											pnlTagAccounts_South.add(pnlSouth,BorderLayout.SOUTH);
										}
									}
								}
							}
						}//pnlTagAccounts
					}
				}//pnlCenter
			}//pnlMain

			tabpane.setSelectedIndex(0);
		} catch (Exception e) {
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) { // XXX actionPerformed

		if (e.getSource().equals(cmbCancelType)) {
			if (cmbCancelType.getSelectedIndex() == 1) {
				cancel_type = "C";

			}
			if (cmbCancelType.getSelectedIndex() == 2) {
				cancel_type = "B";

			}

		}


		if (e.getSource().equals(cmbBuyerType)) {

			if (tabpane.getSelectedIndex()== 0 ) {
				clear();
				setEnableComponents_Active(false);
				lblProcessFor.setEnabled(true);
				cmbBuyerType.setEnabled(true);
				lblProjID.setEnabled(true);
				cmbProcessFor.setEnabled(true);
				lookupProjID.setEnabled(true);
				txtProjectName.setEnabled(true);
				btnClear_Active.setEnabled(true);
				btnGenerateActive.setEnabled(true);

			}

			if (cmbBuyerType.getSelectedIndex()== 0) {
				pnlCancelActive_Center_Center.setBorder(components.JTBorderFactory.createTitleBorder("List of IHF Accounts"));
				table_name = "ihf_due_accounts";

				if (rbtnPerAccount.isSelected()) {
				//	lookupClientName.setLookupSQL(functions.getIHFClient());	
				}else{

					clear();
				}

			}else{

				pnlCancelActive_Center_Center.setBorder(components.JTBorderFactory.createTitleBorder("List of Pag-ibig Accounts"));
				table_name = "pagibig_due_accounts";
				if (rbtnPerAccount.isSelected()) {
					//lookupClientName.setLookupSQL(functions.getHDMFClient());	
				}else{
					clear();
				}

			}
		}
		if (e.getSource().equals(cmbProcessFor)) {
			if (cmbBuyerType.getSelectedIndex() == 1) {
				if (cmbProcessFor.getSelectedIndex()==1) {
					JOptionPane.showMessageDialog(getContentPane(), "CSV is only Applicable for IHF Accounts  ", "Invalid", JOptionPane.OK_OPTION);
					cmbProcessFor.setSelectedIndex(0);

				}
			}
			clear();
		}
		if (e.getSource().equals(btnGenerateActive)) {
			btnPreviewActive.setEnabled(true);
			lblApproveBy_Active.setEnabled(true);
			lookupApproveby_Active.setEnabled(true);
			txtApprovedBy_Active.setEnabled(true);
			btnPost_Active.setEnabled(true);


			if (cmbBuyerType.getSelectedIndex() == 0 ) {
				table_name ="ihf_due_accounts";
			}else{
				table_name ="pagibig_due_accounts";
			}

			if (lookupProjID.getText().isEmpty()){
				JOptionPane.showMessageDialog( getContentPane(), "Please Choose a Project Name first  ", "Incomplete", JOptionPane.OK_OPTION );
				return;
			}

			if (rbtnPerBatch.isSelected()) {
				new Thread(new Generate()).start();

				dteDueDate.setDate(dateFormat("2016-04-14"));
			}


		}

		if (e.getSource().equals(btnPost_Active)) {// XXX POSTING ACTIVE
			new Thread(new ForPosting_Active()).start();




		}
		if (e.getSource().equals(btnClear_Active)) {

			int response = JOptionPane.showConfirmDialog(this,"Are you sure you want to Clear all fields? ","Confirm",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (response == JOptionPane.YES_OPTION) {


				formload();		
				clear();
			}
		}


		if (e.getSource().equals(btnPreviewActive)) {
			new Thread(new ForPreview()).start();
		}

		/*	if (e.getSource().equals(btnGenerate_Hold)) {// XXX GENERATE HOLD

			rowHeadeCancelHold.setModel(new DefaultListModel());
			modelCancelHold.clear();
			lookupApproveby_Hold.setValue("");
			txtApprovedBy_Hold .setText("");
			tblCancelHold.setEnabled(true);
			new Thread(new Generate()).start();

		}
		if (e.getSource().equals(btnPost_Hold)) {

			tagged = 0;

			System.out.println("POST");
			if (modelCancelHold.getRowCount() == 0) { // XXX  POSTING HOLD
				JOptionPane.showMessageDialog(this, "Please generate qualified Account(s) first  ","Invalid",JOptionPane.OK_OPTION);
			}

			for (int x = 0; x < tblCancelHold.getModel().getRowCount(); x++) {

				Boolean SelectedItem = (Boolean) modelCancelHold.getValueAt(x, 0);

				if (SelectedItem) {
					tagged++;
				}
			}

			System.out.println("SELECTED " + tagged);
			if (lookupApproveby_Hold.getValue().isEmpty()) {
				JOptionPane.showMessageDialog( getContentPane(), "Please Select the Approving Officer for this Cancellation Process  ", "Invalid", JOptionPane.OK_OPTION );
				return;
			}

			if (tagged == 0 )  {
				JOptionPane.showMessageDialog(this, "Please Select Units to be Cancelled ");
				return;
			}

			if (tagged >= 1) {
				tblCancelTag.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);// Stops Cell Edit
				System.out.println("tagged >= 1");
				int isEmpty = 0;
				for (int x = 0; x < modelCancelHold.getRowCount(); x++) {
					SelectedItem = (Boolean) modelCancelHold.getValueAt(x, 0);

					if (SelectedItem) {
						if (modelCancelHold.getValueAt(x,9) == null || modelCancelHold.getValueAt(x,10) == null || modelCancelHold.getValueAt(x,9).equals("") || modelCancelHold.getValueAt(x,10).equals("")) {
							isEmpty++;
						}
					}
				}

				System.out.println("empty" +isEmpty);
				if (isEmpty >= 1) {

					System.out.println("empty ang reason" +isEmpty);
					JOptionPane.showMessageDialog(getContentPane(),"Please select account(s) to be Cancelled and fill in the ''Reason'' and ''Remarks'' Column","Incomplete Details",JOptionPane.OK_OPTION);
					return;

				} 

				if (JOptionPane.showConfirmDialog(getContentPane(), "Are you sure you want to Cancel Selected Account(s)", btnPost_Active.getText(), 
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

					System.out.println("POSTED SAVE");
					{
						new Thread(new ForPosting_Hold()).start();
					}

					JOptionPane.showMessageDialog(this,"Post successfully saved.","Post", JOptionPane.INFORMATION_MESSAGE);			
				}	
			}
		}

		if (e.getSource().equals(btnClear_Hold)) {

			int response = JOptionPane.showConfirmDialog(this,"Are you sure you want to Clear all fields?   ","Confirm",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (response == JOptionPane.YES_OPTION) {

				clear();

			}

		}*/

		if (e.getSource().equals(cmbProcessFor_Tag)) {

			if (cmbProcessFor_Tag.getSelectedIndex() == 0 ) {
				pnlTagAccounts_Center.setBorder(components.JTBorderFactory.createTitleBorder("List of Cancellation Accounts"));

			}else{

				pnlTagAccounts_Center.setBorder(components.JTBorderFactory.createTitleBorder("List of Accounts With CSV"));
			}
		}

		if (e.getSource().equals(btnGenerate_Tag)) {

			new Thread(new Generate()).start(); 
		}
		if (e.getSource().equals(btnPost_Tag)) {

			if (modelCancelTag.getRowCount() == 0) {

				JOptionPane.showMessageDialog(getContentPane(), "Please generate qualified Account(s) first  ", "Incomplete", JOptionPane.OK_OPTION);
				return;
			} 

			for (int x = 0; x < modelCancelTag.getRowCount(); x++) {

				SelectedItem = (Boolean) modelCancelTag.getValueAt(x, 0);
				if (SelectedItem) {
					tagged++;
				}
			}

			if (tagged ==0 ) {
				JOptionPane.showMessageDialog(getContentPane(), "Please Select Account(s) to be Posted  "  ,"Incomplete",JOptionPane.OK_OPTION);
				return;
			}

			if (tagged >= 1) {

				tblCancelTag.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);// Stops Cell Edit
				System.out.println("tagged >= 1");
				int isEmpty = 0;
				for (int x = 0; x < modelCancelTag.getRowCount(); x++) {
					SelectedItem = (Boolean) modelCancelTag.getValueAt(x, 0);
					if (SelectedItem) {
						if (modelCancelTag.getValueAt(x,6) == null || modelCancelTag.getValueAt(x,7) == null || modelCancelTag.getValueAt(x,8) == null || modelCancelTag.getValueAt(x,8).equals("")) {
							isEmpty++;
						}
					}
				}

				System.out.println("empty" +isEmpty);
				if (isEmpty >= 1) {

					System.out.println("empty ang reason" +isEmpty);
					JOptionPane.showMessageDialog(getContentPane(),"Please Select New Status,  Status Date and Remarks for the Selected Account(s)","Incomplete Details",JOptionPane.OK_OPTION);
					return;

				}

				int response = JOptionPane.showConfirmDialog(getContentPane(),"Are you sure you want to post new status(s)  ","Confirm",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (response == JOptionPane.YES_OPTION) {

					new Thread(new ForPosting_Tag()).start();

					JOptionPane.showMessageDialog( getContentPane(), "Selected accounts status updated  ", "Successful", JOptionPane.INFORMATION_MESSAGE );
					formload();
					clear();
				}

			}

		}

		if (e.getSource().equals(btnClear_Tag)) {

			int response = JOptionPane.showConfirmDialog(this,"Are you sure you want to Clear all fields?   ","Confirm",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (response == JOptionPane.YES_OPTION) {

				clear();
			}
		}

		if (e.getSource().equals(btnPreview_Tag)) {

			dlgDate date_cancel = new dlgDate(FncGlobal.homeMDI, "Print Report");
			date_cancel.setLocationRelativeTo(null);
			date_cancel.setVisible(true);

			dateFrom = date_cancel.getDateFrom();
			dateTo = date_cancel.getDateTo();

			printall = date_cancel.getPrintAll();
			new Thread(new ForPreview()).start();
		}

	}

	@Override
	public void stateChanged(ChangeEvent e) {
		if (e.getSource().equals(tabpane)) {
			if (tabpane.getSelectedIndex() ==0) {
				setEnabled();
			}
			if (tabpane.getSelectedIndex() ==1) {
				setEnabled();

			}

			if (tabpane.getSelectedIndex() ==2) {
				setEnabled();
			}
		}
	}

	private void setEnableComponents_Active(boolean ena){

		if (lookupCompany.getValue().isEmpty()) {

			if (tabpane.getSelectedIndex() == 0) {

				lblProcessFor.setEnabled(ena);
				cmbProcessFor.setEnabled(ena);
				lblProjID.setEnabled(ena);
				lookupProjID.setEnabled(ena);
				txtProjectName.setEnabled(ena);
				btnGenerateActive.setEnabled(ena);
				btnPreviewActive.setEnabled(ena);
				btnPost_Active.setEnabled(ena);
				btnSave_Active.setEnabled(ena);

				lblApproveBy_Active.setEnabled(ena);
				lookupApproveby_Active.setEnabled(ena);
				txtApprovedBy_Active.setEnabled(ena);
				btnPost_Active.setEnabled(ena);
				btnClear_Active.setEnabled(ena);

				rbtnPerAccount.setEnabled(ena);
				rbtnPerBatch.setEnabled(ena);
				lblBuyerType.setEnabled(ena);
				cmbBuyerType.setEnabled(ena);
			}

			if (tabpane.getSelectedIndex() == 1) {
				System.out.println("FALSE LOAD ");

				/*lookupProjID_Hold.setEnabled(ena);
				txtProjectName_Hold.setEnabled(ena);
				btnGenerate_Hold.setEnabled(ena); 
				btnPreview_Hold.setEnabled(ena);
				tblCancelHold.setEnabled(ena);
				btnPost_Hold.setEnabled(ena);
				btnClear_Hold.setEnabled(ena);

				lblApproveBy_.setEnabled(ena);
				lookupApproveby_Active.setEnabled(ena);
				txtApprovedBy_Active.setEnabled(ena);
				 */

			}

			if (tabpane.getSelectedIndex() == 2) {

			}

		}else{


			lblProcessFor.setEnabled(true);
			cmbProcessFor.setEnabled(true);
			lblProjID.setEnabled(true);
			lookupProjID.setEnabled(true);
			txtProjectName.setEnabled(true);
			rbtnPerAccount.setEnabled(true);
			rbtnPerBatch.setEnabled(true);
			lblBuyerType.setEnabled(true);
			cmbBuyerType.setEnabled(true);

			btnGenerateActive.setEnabled(ena);
			btnPreviewActive.setEnabled(ena);
			btnPost_Active.setEnabled(ena);
			btnClear_Active.setEnabled(ena);
		}
	}

	@Override
	public void lookupPerformed(LookupEvent e) { // XXX lookupPerformed

		if( e.getSource().equals(lookupCompany)){
			Object[] data = ((_JLookup)e.getSource()).getDataSet();

			if(data != null){

				co_id = data[0].toString();
				company = data[1].toString();
				company_logo = data[3].toString();
				txtCompany.setText(company);

				if (tabpane.getSelectedIndex() == 0) {
					rbtnPerAccount.setEnabled(true);
					rbtnPerBatch.setEnabled(true);
					lblBuyerType.setEnabled(true);
					cmbBuyerType.setEnabled(true);
					lblProcessFor.setEnabled(true);
					cmbProcessFor.setEnabled(true);
					lblProjID.setEnabled(true);
					lookupProjID.setEnabled(true);
					txtProjectName.setEnabled(true);

				}

				if (tabpane.getSelectedIndex() == 1) {
					setEnabled();
				}

				if (tabpane.getSelectedIndex() == 2) {
					setEnabled();
				}


				lookupProjID.setLookupSQL(functions.lookupProjects(co_id));
				//	lookupProjID_Hold.setLookupSQL(functions.lookupProjects(co_id));
				lookupProjID_Tag.setLookupSQL(functions.lookupProjects(co_id));



			}
		}
		if (e.getSource().equals(lookupProjID)) {

			Object[] data = ((_JLookup)e.getSource()).getDataSet();
			if(data != null){
				clear();
				System.out.println("Company " + co_id);

				co_id = lookupCompany.getValue();
				_proj_id = data[0].toString();
				_proj_name = data[1].toString();
				txtProjectName.setText(_proj_name);
				lookupProjID.setValue(_proj_id);
				btnGenerateActive.setEnabled(true);
				btnClear_Active.setEnabled(true);

			}
		}

		if (e.getSource().equals(lookupProjID_pa)) {

			Object[] data = ((_JLookup)e.getSource()).getDataSet();
			if(data != null){
				clear();
				_proj_id = data[0].toString();
				_proj_name = data[1].toString();
				lookupProjID_pa.setValue(_proj_id);
				txtProjectName_pa.setText(_proj_name);

			}
		}



		if (e.getSource().equals(lookupApproveby_Active)) {

			if (tabpane.getSelectedIndex()==0) {
				Object[] data = ((_JLookup)e.getSource()).getDataSet();
				if(data != null){
					txtApprovedBy_Active.setText(data[1].toString());
				}
			}

		}

		if (e.getSource().equals(lookupProjID_Hold)) {

			if (tabpane.getSelectedIndex()==1) {
				Object[] data = ((_JLookup)e.getSource()).getDataSet();
				if(data != null){
					_proj_id = data[0].toString();
					_proj_name = data[1].toString();
					txtProjectName_Hold.setText(_proj_name);
					btnGenerate_Hold.setEnabled(true);

					//this.setComponentsEnabled(pnlCancelHoldUnit, false);
				}
			}

		}

		if (e.getSource().equals(lookupApproveby_Hold)) {

			if (tabpane.getSelectedIndex()==1) {
				Object[] data = ((_JLookup)e.getSource()).getDataSet();
				if(data != null){
					txtApprovedBy_Hold.setText(data[1].toString());
				}
			}

		}
		if (e.getSource().equals(lookupProjID_Tag)) {

			if (tabpane.getSelectedIndex()==2) {
				Object[] data = ((_JLookup)e.getSource()).getDataSet();
				if(data != null){
					_proj_id = data[0].toString();
					_proj_name = data[1].toString();
					txtProjectName_Tag.setText(_proj_name);
					btnGenerate_Tag.setEnabled(true);
				}
			}
		}

		if (e.getSource().equals(lookupClientName)) {

			pgSelect dbS = new pgSelect();

			if (cmbBuyerType.getSelectedIndex() == 0 ) {

				table_name ="ihf_due_accounts";


			}else{
				table_name ="pagibig_due_accounts";
			}

			Object[] data = ((_JLookup)e.getSource()).getDataSet();
			if(data != null){

				_entity_id 		= data[0].toString();
				_client_name 	= data[1].toString();
				_pbl_id 		= data[7].toString();
				_pbldesc	 	= data[2].toString();
				_seq_no 		= (Integer)data[8];


				txtClientName.setText(_client_name);
				txtPBLID.setText(_pbl_id);
				txtPBLDesc.setText(_pbldesc);

				dbS.select(methods.displayPastDue(_entity_id, _proj_id, _pbl_id, _seq_no, table_name));

				if (dbS.isNotNull()) {

					_dayspd 	 	= Integer.valueOf((String) (dbS.Result[0][0].toString() == null ? 0 : dbS.Result[0][0].toString()));
					_monthspd 		= Integer.valueOf(dbS.Result[0][1].toString());
					_amountdue 		= (BigDecimal)dbS.Result[0][2];
					_lastpaid_date 	= (Date) dbS.Result[0][3] == null ? null : (Date) dbS.Result[0][3];
					_default_date   = (Date) dbS.Result[0][4] == null ? null : (Date) dbS.Result[0][4];   

					if (_dayspd < 60) {
						lblNote1.setVisible(true);
						lblNote.setVisible(true);// not recommended
					}

					if (_dayspd >= 60){ 
						lblNote1.setVisible(false); // recommended
						lblNote.setVisible(false);
					}

					//	txtDayPD.setText(dbS.Result[0][0].toString());
					txtMonthPD.setText(dbS.Result[0][1].toString());
					txtAmountDue.setValue((BigDecimal)dbS.Result[0][2]);
					txtDefaultDate.setText (functions.dateFormat((Date) dbS.Result[0][4])  == null ? null : functions.dateFormat((Date) dbS.Result[0][4]));
					txtStage.setText(dbS.Result[0][5] == null ? "" : dbS.Result[0][5].toString());


				}
				btnPost_Active.setEnabled(true);
				lookupApproveby_Active.setEnabled(true);
				txtApprovedBy_Active.setEnabled(true);
			}
		}

		if (e.getSource().equals(lookupReason)) {


			Object[] data = ((_JLookup)e.getSource()).getDataSet();
			if(data != null){
				_reason_id = data[0].toString();
				_reason = data[1].toString();

				txtReason_.setText(_reason);

			}


		}
	}

	@SuppressWarnings("unchecked")
	private void displayPerAccountPanel(){ /// XXX displayPerAccountPanel

		btnGenerateActive.setVisible(false);
		btnPreviewActive.setVisible(false);

		{

			JPanel pnlCancelActive_Center_North = new JPanel(new BorderLayout(5, 5));
			pnlCancelActive_Center.add(pnlCancelActive_Center_North,BorderLayout.NORTH);
			pnlCancelActive_Center_North.setPreferredSize(new Dimension(pnlCancelActive.getWidth(), 60));
			pnlCancelActive_Center_North.setVisible(true);
			{
				JPanel _pnlLabel = new JPanel(new GridLayout(2, 1, 3, 3));
				pnlCancelActive_Center_North.add(_pnlLabel,BorderLayout.WEST);
				_pnlLabel.setVisible(true);
				{
					lblNote = new JLabel("Note : ");
					_pnlLabel.add(lblNote);
					lblNote.setVisible(false);
				}
				{
					lblProjID = new JLabel(" Project ID : ");
					_pnlLabel.add(lblProjID);
				}
			}
			{

				JPanel pnlProcess = new JPanel(new GridLayout(2, 1, 3, 3));
				pnlCancelActive_Center_North.add(pnlProcess,BorderLayout.CENTER);
				pnlProcess.setPreferredSize(new Dimension(300, 60));
				{

					lblNote1 = new JLabel("<html><font color=red><i> Selected Client is not Recommended for Cancellation</i></font></html>");
					pnlProcess.add(lblNote1);
					lblNote1.setVisible(false);
				}
				{
					JPanel pnlProject = new JPanel(new BorderLayout(3,3));
					pnlProcess.add(pnlProject);
					{
						lookupProjID_pa = new _JLookup("Proj ID", "Search Project", 0);
						pnlProject.add(lookupProjID_pa,BorderLayout.WEST);
						lookupProjID_pa.setLookupSQL(functions.lookupProjects(co_id));
						lookupProjID_pa.setPreferredSize(new Dimension(50, 25));
						lookupProjID_pa.addLookupListener(this);

					}
					{
						txtProjectName_pa = new JTextField(); 
						pnlProject.add(txtProjectName_pa,BorderLayout.CENTER);
						txtProjectName_pa.setEditable(false); 
					}
				}
			}
			{
				JPanel pnlButton = new JPanel(new GridLayout(1, 2, 3, 3));
				pnlCancelActive_Center_North.add(pnlButton,BorderLayout.EAST);
				pnlButton.setPreferredSize(new Dimension(300, 60));
				/*{
					btnGenerateActive = new _JButton("Generate");
					pnlButton.add(btnGenerateActive);
					btnGenerateActive.addActionListener(this);
				}
				{
					btnPreviewActive = new _JButton("Preview");
					pnlButton.add(btnPreviewActive);
					btnPreviewActive.addActionListener(this);
				}*/
			}
		}

		pnlCancelActive_Center_PerAccount = new JPanel(new BorderLayout(5, 5));
		pnlCancelActive_Center.add(pnlCancelActive_Center_PerAccount,BorderLayout.CENTER);
		pnlCancelActive_Center_PerAccount.setBorder(components.JTBorderFactory.createTitleBorder(""));
		pnlCancelActive_Center_PerAccount.setVisible(true);

		{
			{
				JPanel pnlNorth = new JPanel(new BorderLayout(3, 3));
				pnlCancelActive_Center_PerAccount.add(pnlNorth, BorderLayout.NORTH);

			}
			{ 
				JPanel pnlCenter = new JPanel(new BorderLayout(3, 3));
				pnlCancelActive_Center_PerAccount.add(pnlCenter, BorderLayout.CENTER);
				{

					JPanel pnlClient = new JPanel(new BorderLayout(5, 5));
					pnlCenter.add(pnlClient,BorderLayout.NORTH);
					{

						JPanel pnlLabel = new JPanel(new GridLayout(8, 1, 3, 3));
						pnlClient.add(pnlLabel,BorderLayout.WEST);
						{
							lblClientID = new JLabel("Client ID");
							pnlLabel.add(lblClientID);
						}
						{
							lblPBL = new JLabel("Ph/Bl/Lt");
							pnlLabel.add(lblPBL);
							lblPBL.setPreferredSize(new Dimension(84, 25));
						}
						{ 
							lblStage= new JLabel("Stage");
							pnlLabel.add(lblStage);
						}
						/*	{
							lblNone= new JLabel("");
							pnlLabel.add(lblNone);
						}
						 */	{
							 lblMonthPD = new JLabel("Months PD");
							 pnlLabel.add(lblMonthPD);
						 }
						 {
							 lblDefaultDate= new JLabel("Default Date");
							 pnlLabel.add(lblDefaultDate);
						 }
						 {
							 lblDaysPD = new JLabel("Cancellation Type");
							 pnlLabel.add(lblDaysPD);
						 }
						 {
							 lblAmountDue= new JLabel("Amount Due");
							 pnlLabel.add(lblAmountDue);
						 }
						 {
							 lblRemarks= new JLabel("Reason");
							 pnlLabel.add(lblRemarks);
						 }
						 {
							 JPanel pnlNorth_Center = new JPanel(new GridLayout(8, 1, 3, 3));
							 pnlClient.add( pnlNorth_Center,BorderLayout.CENTER);	
							 {

								 {
									 JPanel pnlLookUp = new JPanel(new BorderLayout(3,3));
									 pnlNorth_Center.add(pnlLookUp,BorderLayout.CENTER);
									 {
										 lookupClientName = new _JLookup("Select Client ID ", "Search Client ID", 0);
										 pnlLookUp.add(lookupClientName,BorderLayout.WEST);
										 lookupClientName.setPreferredSize(new Dimension(100, 0));
										 lookupClientName.addLookupListener(this);
									 }
									 {
										 txtClientName = new JTextField();
										 pnlLookUp.add(txtClientName,BorderLayout.CENTER);
										 txtClientName.setEditable(false);
									 }
								 }
								 {
									 JPanel pnlPBL = new JPanel(new BorderLayout(3,3));
									 pnlNorth_Center.add( pnlPBL,BorderLayout.CENTER);			
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
									 pnlNorth_Center.add(pnlStage);
									 {
										 txtStage = new JTextField();
										 pnlStage.add(txtStage);
										 txtStage.setPreferredSize(new Dimension(500, 0));
										 txtStage.setEditable(false);
									 }
								 }
								 /*{
									lbNone_1 = new JLabel();
									pnlNorth_Center.add(lbNone_1, BorderLayout.WEST);
								}*/
								 {
									 txtMonthPD = new JTextField();
									 pnlNorth_Center.add(txtMonthPD, BorderLayout.WEST);
									 txtMonthPD.setEditable(false);
								 }
								 {
									 txtDefaultDate = new JTextField();
									 pnlNorth_Center.add(txtDefaultDate, BorderLayout.WEST);
									 txtDefaultDate.setEditable(false);
								 }
								 {
									 cmbCancelTypeModel_Per = new DefaultComboBoxModel(new String[] {"","Company Initiated", "Buyer Initiated"});
									 cmbCancelType_Per = new JComboBox();
									 pnlNorth_Center.add(cmbCancelType_Per, BorderLayout.WEST);
									 cmbCancelType_Per.setModel(cmbCancelTypeModel_Per);
									 cmbCancelType_Per.addActionListener(this);

								 }
								 {

									 txtAmountDue = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
									 pnlNorth_Center.add(txtAmountDue, BorderLayout.WEST);
									 txtAmountDue.setFormatterFactory(_JXFormattedTextField.DECIMAL);
									 txtAmountDue.setEditable(false);
								 }
								 { 
									 JPanel pnlLookUp_Reason = new JPanel(new BorderLayout(3,3));
									 pnlNorth_Center.add(pnlLookUp_Reason,BorderLayout.CENTER);
									 {
										 lookupReason = new _JLookup("Reason ID ", "Reason Desc.", 0);
										 pnlLookUp_Reason.add(lookupReason,BorderLayout.WEST);
										 lookupReason.setLookupSQL("select remark_id, remdesc from mf_remarks where remark_id in ('00232','00227','00034','00035','00036','00037','00039') and remtype = '02' order by remdesc");
										 lookupReason.setPreferredSize(new Dimension(100, 0));
										 lookupReason.addLookupListener(this);


									 }
									 {
										 txtReason_ = new JTextField();
										 pnlLookUp_Reason.add(txtReason_,BorderLayout.CENTER);
										 txtReason_.setEditable(false);
									 }
								 }
							 }
						 }
					}
				}
			}
			{
				JPanel pnlEast = new JPanel(new BorderLayout(3, 3));
				pnlCancelActive_Center_PerAccount.add(pnlEast, BorderLayout.EAST);
				pnlEast.setPreferredSize(new Dimension(300, 0));
			}
			{
				JPanel pnlSouth = new JPanel(new BorderLayout(3, 3));
				pnlCancelActive_Center_PerAccount.add(pnlSouth, BorderLayout.SOUTH);
				pnlSouth.setPreferredSize(new Dimension(lblRemarks.getWidth(), 30));
				{
					JPanel pnlReason = new JPanel(new BorderLayout(5, 5));
					pnlSouth.add(pnlReason,BorderLayout.CENTER);
					pnlReason.setPreferredSize(new Dimension(100, 0));
					{
						JPanel pnlLabel = new JPanel(new GridLayout(1, 1, 3, 3));
						pnlReason.add(pnlLabel,BorderLayout.WEST);
						{
							lblReason= new JLabel("Remarks");
							pnlLabel.add(lblReason);
							lblReason.setPreferredSize(new Dimension(84, 25));
						}
						JPanel pnlTextArea = new JPanel(new GridLayout(1, 1, 3, 3));
						pnlReason.add(pnlTextArea,BorderLayout.CENTER);
						{

							scrollReason = new JScrollPane();
							pnlTextArea.add(scrollReason,BorderLayout.CENTER);
							scrollReason.setOpaque(true);
							scrollReason.setBorder(lineBorder);
							{
								txtareaReason = new JTextArea();
								scrollReason.add(txtareaReason);
								scrollReason.setViewportView(txtareaReason);
								txtareaReason.setLineWrap(true);
								txtareaReason.setWrapStyleWord(true);
								txtareaReason.setAutoscrolls(true);
								txtareaReason.setText("");	
							}
						}
					}
				}
				{
					JPanel pnlEast = new JPanel(new BorderLayout(5, 5));
					pnlSouth.add(pnlEast,BorderLayout.EAST);
					pnlEast.setPreferredSize(new Dimension(300, 0));
				}
			}
		}

	}
	public class Generate implements Runnable{
		private int recomend_or_csv;

		@SuppressWarnings({ "unchecked" })
		public void run() {

			if (tabpane.getSelectedIndex()== 0 || tblPerBatch.isShowing()) {
				if (cmbProcessFor.getSelectedIndex() == 0 ) {
					recomend_or_csv = 0;
				}else{
					recomend_or_csv = 1;
				}

				if (cmbBuyerType.getSelectedIndex() == 0) {
					lblListof = "Recommended for Cancellation for IHF Accounts";

				}else{
					lblListof = "Recommended for Cancellation for Pag-ibig Accounts";

				}

				rowHeadePerBatch.setModel(new DefaultListModel());
				functions.displayPerBatchRecommended(modelPerBatchModel, rowHeadePerBatch, table_name, _proj_id,recomend_or_csv);
				scrollPerBatch.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPerBatch.getRowCount())));

				if (cmbProcessFor.getSelectedIndex() == 0 ) {
					tblPerBatch.hideColumns("Entity ID","Project ID","PBL ID","Seq ID","Unit ID","PartID","Phase",	"<html><font color = \"red\">*</font> &nbsp;<i>CSV</i>","Project Name");	
					tblPerBatch.showColumns("<html><font color = \"red\">*</font> &nbsp;<i>Remarks</i>","<html><font color = \"red\">*</font> &nbsp;<i>Reason</i>");
					tblPerBatch.packAll();
					tblPerBatch.packColumn(21, 400, 400);
					tblPerBatch.packColumn(22, 400, 400);
				}else{
					tblPerBatch.hideColumns("Entity ID","Project ID","PBL ID","Seq ID","Unit ID","PartID","Phase","<html><font color = \"red\">*</font> &nbsp;<i>Remarks</i>","<html><font color = \"red\">*</font> &nbsp;<i>Reason</i>","Project Name");
					tblPerBatch.showColumns("<html><font color = \"red\">*</font> &nbsp;<i>CSV</i>");
					tblPerBatch.packAll();
				}

			}

			if (tabpane.getSelectedIndex()== 1) {
				rowHeadeCancelHold.setModel(new DefaultListModel());
				_FCancellationProcessing.displayCancellationHold(modelCancelHold, rowHeadeCancelHold, _proj_id);
				scrollCancelHold.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblCancelHold.getRowCount())));

				tblCancelHold.packAll();
				tblCancelHold.packColumn(9, 400, 400);
				tblCancelHold.packColumn(10, 400, 400);

			}

			if (tabpane.getSelectedIndex()== 2 || tblCancelTag.isShowing()) {

				rowHeaderCancelTag.setModel(new DefaultListModel());

				_FCancellationProcessing.displayCancellationTag(modelCancelTag, rowHeaderCancelTag, _proj_id,cmbProcessFor_Tag.getSelectedIndex());
				scrollCancelTag.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblCancelTag.getRowCount())));

				tblCancelTag.packAll();
				tblCancelTag.packColumn(8, 400, 400);

			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource().equals(tblPerBatch)) {
			if (modelPerBatchModel.getValueAt(tblPerBatch.getSelectedRow(), 0).equals(true)) { 



				if (tblPerBatch.getSelectedColumn()== 21) {

					String SQL = "select remark_id, remdesc from mf_remarks where remark_id in ('00232','00227','00034','00035','00036','00037','00039') and remtype = '02' order by remdesc";

					_JLookupTable dlg = new _JLookupTable(FncGlobal.homeMDI, null, SQL, false);

					dlg.setLocationRelativeTo(FncGlobal.homeMDI);
					dlg.setVisible(true); 

					Object[] data = dlg.getReturnDataSet();

					if(data != null){

						modelPerBatchModel.setValueAt(data[1], tblPerBatch.getSelectedRow(),29);
						_reason_id = data[0].toString();
						tblPerBatch.packAll();
						tblPerBatch.packColumn(21, 400, 400);

						System.out.println("Status_id :" + modelPerBatchModel.getValueAt( tblPerBatch.getSelectedRow(), 29));
					}

				} // 
			}
			/** Repaint for Highlight **/
			if (tblPerBatch.getSelectedColumn() == 0) {
				tblPerBatch.repaint();
				tblPerBatch.packAll();
				tblPerBatch.packColumn(22, 400, 400); 
			}

			/** Listener for boolean canEditStatus **/
			if (tblPerBatch.getSelectedColumn()== 22) {

				System.out.println("Enable :" + modelPerBatchModel.getValueAt(tblPerBatch.getSelectedRow(),0).equals(true));
				modelPerBatchModel.setEditable(( modelPerBatchModel.getValueAt(tblPerBatch.getSelectedRow(),0).equals(true)) );


			} //
		}


		if (e.getSource().equals(tblCancelHold)) {
			if (tblCancelHold.getSelectedColumn()== 0){
				tblCancelHold.repaint();
			}

			/** Listener for boolean canEditCancel **/
			if (tblCancelHold.getSelectedColumn()== 9 || tblCancelHold.getSelectedColumn()== 10 ) {
				modelCancelHold.setEditable(( modelCancelHold.getValueAt(tblCancelHold.getSelectedRow(),0).equals(true)) );
			} 
		}

		if (e.getSource().equals(tblCancelTag)) {
			if (modelCancelTag.getValueAt(tblCancelTag.getSelectedRow(), 0).equals(true)) { 


				if (tblCancelTag.getSelectedColumn()== 7) {
					if (e.getClickCount() ==2) {
						dteStatus.setBounds((int)pnlTagAccounts_Center.getMousePosition().getX(), (int)pnlTagAccounts_Center.getMousePosition().getY(), 0, 0);
						dteStatus.getCalendarButton().doClick();
					}

				} // column 7

				if (tblCancelTag.getSelectedColumn()== 6) {

					String SQL = 
							"select cancel_status_id as id,"
									+ "trim(status_desc) as description "
									+ "from rf_cancel_status "
									+ "where status_id = 'A'\n" +
									((cmbProcessFor_Tag.getSelectedIndex()==0)?"and status_group = 'CNCL'\n":"and status_group = 'CSV'\n") +
									"and cancel_status_id not in\n" +
									((cmbProcessFor_Tag.getSelectedIndex()==0)?
											"(select cancel_status_id from rf_cancelled_accts_status "
											+ "where cancel_id = "+ modelCancelTag.getValueAt(tblCancelTag.getSelectedRow(),13) +" "
											+ "and status_id = 'A')\n":
												"(select csv_status_id from rf_csv_status where csv_id = "+ modelCancelTag.getValueAt(tblCancelTag.getSelectedRow(),10) +" and status_id = 'A')\n") +
												"order by id";

					_JLookupTable dlg = new _JLookupTable(FncGlobal.homeMDI, null, SQL, false);

					dlg.setLocationRelativeTo(FncGlobal.homeMDI);
					dlg.setVisible(true); 

					Object[] data = dlg.getReturnDataSet();

					if(data != null){

						modelCancelTag.setValueAt(data[1], tblCancelTag.getSelectedRow(),6);
						modelCancelTag.setValueAt(data[0], tblCancelTag.getSelectedRow(),9);
						tblCancelTag.packAll();
						tblCancelTag.packColumn(8, 400, 400);

						System.out.println("Status_id :" + modelCancelTag.getValueAt( tblCancelTag.getSelectedRow(), 9));
					}
				} // column 6
			}
			/** Repaint for Highlight **/
			if (tblCancelTag.getSelectedColumn() == 0) {
				tblCancelTag.repaint();
				tblCancelTag.packAll();
				tblCancelTag.packColumn(8, 400, 400); 
			}

			/** Listener for boolean canEditStatus **/
			if (tblCancelTag.getSelectedColumn() == 8 || tblCancelTag.getSelectedColumn()== 6) {

				System.out.println("Enable :" + modelCancelTag.getValueAt(tblCancelTag.getSelectedRow(),0).equals(true));
				modelCancelTag.setEditable(( modelCancelTag.getValueAt(tblCancelTag.getSelectedRow(),0).equals(true)) );


			} // col 8
		}
	}

	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseDragged(MouseEvent e) {}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (e.getSource().equals(tblPerBatch)) {
			if (tblPerBatch.columnAtPoint(e.getPoint())== 20 || 
					tblPerBatch.columnAtPoint(e.getPoint())== 21 || // Reason
					tblPerBatch.columnAtPoint(e.getPoint())== 22 || // Remarks
					tblPerBatch.columnAtPoint(e.getPoint())== 23 )	// CSV
			{ // CSV
				if (tblPerBatch.getValueAt(tblPerBatch.rowAtPoint(e.getPoint()),0).equals(true))
					tblPerBatch.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				else tblPerBatch.setCursor(null);

			}else tblPerBatch.setCursor(null);
		}
		if (e.getSource().equals(tblCancelHold)) {
			if (tblCancelHold.columnAtPoint(e.getPoint())==9 || 	// Reason
					tblCancelHold.columnAtPoint(e.getPoint())==10 )	// Remarks
			{ // CSV
				if (tblCancelHold.getValueAt(tblCancelHold.rowAtPoint(e.getPoint()),0).equals(true))
					tblCancelHold.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				else tblCancelHold.setCursor(null);

			}else tblCancelHold.setCursor(null);
		}

		if (e.getSource().equals(tblCancelTag)) {

			if (tblCancelTag.columnAtPoint(e.getPoint())==6 || 		// New Status
					tblCancelTag.columnAtPoint(e.getPoint())==7 || 	// Status Date
					tblCancelTag.columnAtPoint(e.getPoint())==8)   	// Remarks
			{
				if (modelCancelTag.getValueAt(tblCancelTag.rowAtPoint(e.getPoint()),0).equals(true)) {
					tblCancelTag.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				}
				else tblCancelTag.setCursor(null);
			}
			else tblCancelTag.setCursor(null);

		}
	} // mouseMoved

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getSource().equals(tblPerBatch)) {
			if ( e.getKeyCode()==KeyEvent.VK_UP || 
					e.getKeyCode()==KeyEvent.VK_DOWN || 
					e.getKeyCode()==KeyEvent.VK_ENTER || 
					e.getKeyCode()==KeyEvent.VK_LEFT || 
					e.getKeyCode()==KeyEvent.VK_RIGHT ) {
				if (tblPerBatch.getSelectedColumn()==20||tblPerBatch.getSelectedColumn()==21 || tblPerBatch.getSelectedColumn()== 22 ) {
					modelPerBatchModel.setEditable(( modelPerBatchModel.getValueAt(tblPerBatch.getSelectedRow(),0).equals(true)) );
				} // col 17,18
			} // getKeyCode

		}

		if (e.getSource().equals(tblCancelHold)) {

			if ( e.getKeyCode()==KeyEvent.VK_UP || 
					e.getKeyCode()==KeyEvent.VK_DOWN || 
					e.getKeyCode()==KeyEvent.VK_ENTER || 
					e.getKeyCode()==KeyEvent.VK_LEFT || 
					e.getKeyCode()==KeyEvent.VK_RIGHT ) {
				if (tblCancelHold.getSelectedColumn()==9 || tblCancelHold.getSelectedColumn()== 10) {
					modelCancelHold.setEditable(( modelCancelHold.getValueAt(tblCancelHold.getSelectedRow(),0).equals(true)) );
				} // col 17,18
			} // getKeyCode

		}
		if (e.getSource().equals(tblCancelTag)) {
			if ( e.getKeyCode()==KeyEvent.VK_UP || 
					e.getKeyCode()==KeyEvent.VK_DOWN || 
					e.getKeyCode()==KeyEvent.VK_ENTER || 
					e.getKeyCode()==KeyEvent.VK_LEFT || 
					e.getKeyCode()==KeyEvent.VK_RIGHT ) {
				if (tblCancelTag.getSelectedColumn()==8) {
					modelCancelTag.setEditable(( modelCancelTag.getValueAt(tblCancelTag.getSelectedRow(),0).equals(true)) );
				} // col 8
			} // getKeyCode
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}
	public class ForPosting_Active implements Runnable{ // XXX ForPosting_Active


		private String txtNote;
		private Boolean SelectedItem;
		private String _textreason;

		@Override
		public void run() {

			_batchno =functions.getBatchNo();
			FncGlobal.startProgress("Saving  . . .Please wait ");

			int isEmpty = 0;
			if (rbtnPerBatch.isSelected()) {
				tagged = 0;

				if (cmbProcessFor.getSelectedIndex() == 0) {
					if (tblPerBatch.getRowCount() == 0) {
						JOptionPane.showMessageDialog(getContentPane(), "Please Generate Recommended Accounts for Cancellation  ", "Incomplete", JOptionPane.OK_OPTION);
						FncGlobal.stopProgress();
						return;

					}

					for (int x = 0; x < tblPerBatch.getModel().getRowCount(); x++) {

						SelectedItem = (Boolean) modelPerBatchModel.getValueAt(x, 0);
						_textreason =  modelPerBatchModel.getValueAt(x,29).toString().trim();
						if (SelectedItem) {
							tagged++;
						}
					}

					if (tagged ==0 ) {
						JOptionPane.showMessageDialog(getContentPane(), "Please select Account(s) to be Cancelled and fill in the ''Reason'' and ''Remarks'' Column"  ,"Incomplete Details",JOptionPane.OK_OPTION);
						FncGlobal.stopProgress();
						return;
					}

					if (tagged >= 1) {
						tblPerBatch.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);// Stops Cell Edit
						System.out.println("tagged >= 1");

						for (int x = 0; x < tblPerBatch.getModel().getRowCount(); x++) {
							SelectedItem = (Boolean) modelPerBatchModel.getValueAt(x, 0);
							_textreason =  modelPerBatchModel.getValueAt(x,29).toString().trim();
							if (SelectedItem) {
								if (_textreason.equals("")) {
									System.out.println("empty ang reason");

									isEmpty++;
								}
							}
						}

						System.out.println("empty" +isEmpty);
						if (isEmpty >= 1) {

							System.out.println("empty ang reason" +isEmpty);
							JOptionPane.showMessageDialog(getContentPane(),"Please Indicate the Reason for Cancellation on the ''Reason Column''  \n You can also put Remarks on ''Remarks Column''","Incomplete Details",JOptionPane.OK_OPTION);
							FncGlobal.stopProgress();
							return;

						}

						if (lookupApproveby_Active.getText().isEmpty()) {
							JOptionPane.showMessageDialog(getContentPane(), "Please Select an Approver for this Cancellation Process  ",  "Incomplete Details",  JOptionPane.OK_OPTION);
							FncGlobal.stopProgress();
							return;
						} // Approver is Empty

						int response = JOptionPane.showConfirmDialog(getContentPane(),"Are you sure you want to Cancel Selected Account(s)  ","Confirm",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
						if (response == JOptionPane.YES_OPTION) {

							perBatchRecommend();

							new Thread(new ForNotices()).start();
							formload();
							clear();
						}
					}

				}//recommended

				if (cmbProcessFor.getSelectedIndex() == 1) {

					if (tblPerBatch.getRowCount() == 0) {
						JOptionPane.showMessageDialog(getContentPane(), "Please Generate Recommended Accounts for Cancellation  ", "Incomplete", JOptionPane.OK_OPTION);
						FncGlobal.stopProgress();
						return;

					}

					for (int x = 0; x < modelPerBatchModel.getRowCount(); x++) {

						SelectedItem = (Boolean) modelPerBatchModel.getValueAt(x, 0);
						if (SelectedItem) {
							tagged++;
						}
					}

					if (tagged ==0 ) {
						JOptionPane.showMessageDialog(getContentPane(), "Please select Account(s) to be Posted for CSV \n and input the Amount of CSV on the CSV Column  "  ,"Incomplete Details",JOptionPane.OK_OPTION);
						FncGlobal.stopProgress();
						return;
					}

					if (tagged >= 1) {
						if (SelectedItem) {

							for (int x = 0; x < tblPerBatch.getModel().getRowCount(); x++) {
								SelectedItem = (Boolean) modelPerBatchModel.getValueAt(x, 0);
								BigDecimal csv_amount =  (BigDecimal) (modelPerBatchModel.getValueAt(x,31) == null ? null : modelPerBatchModel.getValueAt(x,31)) ;
								if (SelectedItem) {
									if (csv_amount == null) {
										System.out.println("empty ang reason");

										isEmpty++;
									}
								}
							}
						}

						if (isEmpty >= 1) {

							JOptionPane.showMessageDialog(getContentPane(),
									"Please Input the Amount of CSV on the CSV Column  ",
									"Incomplete Details", JOptionPane.OK_OPTION);
							FncGlobal.stopProgress();
							return;
						}

						if (lookupApproveby_Active.getText().isEmpty()) {
							JOptionPane.showMessageDialog(getContentPane(), "Please Select an Approver for this Cancellation Process  ",  "Incomplete Details",  JOptionPane.OK_OPTION);
							FncGlobal.stopProgress();
							return;
						} // Approver is Empty

						if (JOptionPane.showConfirmDialog(getContentPane(), "Are you sure you want to qualified selected account(s)  " ,"Confirm", 
								JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
							perBatchCSV();
							formload();
							clear();

							JOptionPane.showMessageDialog(getContentPane(),
									"\nSelected Account(s) Posted for CSV with the following RPLF No.  \n" + forcsv,
									"Succesful",JOptionPane.INFORMATION_MESSAGE);
						}
					}

				}//csv
				/*
				for (int i = 0 ;  i < modelPerBatchModel.getRowCount(); i++) {
					Boolean ifSelected =(Boolean) modelPerBatchModel.getValueAt(i, 0);

					if (ifSelected) {
						if (cmbProcessFor.getSelectedIndex() == 0) { 
							_entity_id  = modelPerBatchModel.getValueAt(i, 17).toString();
							_proj_id =  modelPerBatchModel.getValueAt(i, 18).toString();
							_pbl_id =  modelPerBatchModel.getValueAt(i, 19).toString().trim();
							_seq_no =  (Integer) modelPerBatchModel.getValueAt(i, 20);
							_phase_no =  modelPerBatchModel.getValueAt(i, 23).toString().trim();
							_approvedby = lookupApproveby_Active.getValue().trim();
							_monthspd = (Integer) modelPerBatchModel.getValueAt(i, 12); 
							_dayspd = (Integer) modelPerBatchModel.getValueAt(i, 13);		
							_amountdue = (BigDecimal) modelPerBatchModel.getValueAt(i, 15);
							_reason = modelPerBatchModel.getValueAt(i, 24).toString().replace("'", "''");
							_remarks = modelPerBatchModel.getValueAt(i, 25).toString().replace("'", "''");
							_cancelledby = UserInfo.EmployeeCode.trim();

							_monthspaid = (Integer) modelPerBatchModel.getValueAt(i, 14);

							if (cmbBuyerType.getSelectedIndex()==0) {
								if (_monthspaid >= 24 ) {
									_macedalaw = true;

								}
								if (_monthspaid  <  24 ) {
									_macedalaw = false;
								}
								buyer_id = "02";
							}else{
								_macedalaw = false;
								buyer_id = "04";
							}

							SQL = "SELECT sp_update_cancellation('"+ _entity_id +"', '"+ _proj_id +"','"+ _pbl_id +"',"+_seq_no+",'"+_batchno+"',"+_monthspd+","+_dayspd+",'"+_amountdue+"',"+_macedalaw+",'"+_reason+"','"+_remarks+"','"+_cancelledby+"','"+_approvedby+"' ,"+_monthspaid+","+ifSelected+"  );";
						}else{
							_entity_id  = modelPerBatchModel.getValueAt(i, 17).toString();
							_proj_id =  modelPerBatchModel.getValueAt(i, 18).toString();
							_pbl_id =  modelPerBatchModel.getValueAt(i, 19).toString().trim();
							_seq_no =  (Integer) modelPerBatchModel.getValueAt(i, 20);
							_phase_no =  modelPerBatchModel.getValueAt(i, 23).toString().trim();

							_approvedby = lookupApproveby_Active.getValue().trim();
							_csv_mount =  (BigDecimal)modelPerBatchModel.getValueAt(i, 26);

							SQL = "SELECT sp_posting_cancellation_csv(\n" + 
									"	'"+_entity_id+"' ,\n" + 
									"	'"+_proj_id+"' ,\n" + 
									"	'"+_pbl_id +"' ,\n" + 
									"	"+_seq_no+" ,\n" + 
									"	'"+_phase_no+"' ,\n" + 
									"	'"+UserInfo.Branch+"'  ,\n" + 
									"	"+ifSelected+" ,\n" + 
									"	'"+_csv_mount+"' ,\n" + 
									"	'"+_approvedby+"'  ,\n" + 
									"	'"+UserInfo.EmployeeCode+"' ";
						}

						System.out.printf("SQL: %s%n", SQL);
						pgSelect db = new pgSelect();
						db.select(SQL);
					}

				}*/
			}

			if (rbtnPerAccount.isSelected()) { 

				if (lookupApproveby_Active.getValue().isEmpty()) {
					JOptionPane.showMessageDialog(getContentPane(), "Please Select the Approving Officer for this Cancellation Process", "Incomplete Details", JOptionPane.OK_OPTION);
					FncGlobal.stopProgress();
					return;

				} // Approved by is empty
				if (txtareaReason.getText().isEmpty()) {
					JOptionPane.showMessageDialog(getContentPane(), "Please indicate the Reason for Cancellation", "Incomplete", JOptionPane.OK_OPTION);
					FncGlobal.stopProgress();
					return;
				}

				if (lblNote.isVisible()) {
					txtNote = "Selected Client is not Recommended for Cancellation \nDo you want to Cancel this Account?";
				}else{
					txtNote = "Are you sure you want to Cancel Selected Account(s)  "; 
				}

				if (JOptionPane.showConfirmDialog(getContentPane(), txtNote ,"Confirm", 
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					postingPerAccount();
					new Thread(new ForNotices()).start();
					//JOptionPane.showMessageDialog( getContentPane(), "Account(s) Cancelled with Batch No. : "+_batchno+"   ", "Successful", JOptionPane.INFORMATION_MESSAGE );
					formload();
					clear();
				}
			}
			FncGlobal.stopProgress();
		}
	}
	public class ForPosting_Hold implements Runnable{

		private String _batchno;
		private String _pbl_id;
		private Integer _seq_no;
		private String _unit_id;
		private Integer _tra_header_id;
		private String _remarks;
		private String _reason;
		private String _user_id; 
		private String _approvedby;

		@Override
		public void run() {

			_batchno =functions.getBatchNo();
			FncGlobal.startProgress("Saving  . . .Please wait ");


			for (int i = 0 ;  i < modelCancelHold.getRowCount(); i++) {
				Boolean ifSelected =(Boolean) modelCancelHold.getValueAt(i, 0);

				if (ifSelected) {
					Timestamp _due_date = (Timestamp) modelCancelHold.getValueAt(i, 8);
					_reason = modelCancelHold.getValueAt(i, 9).toString().replace("'", "''");;
					_remarks = modelCancelHold.getValueAt(i, 10).toString().replace("'", "''");;

					_entity_id = modelCancelHold.getValueAt(i, 11).toString();
					_proj_id = modelCancelHold.getValueAt(i, 12).toString();
					_pbl_id = modelCancelHold.getValueAt(i, 13).toString();
					_seq_no = (Integer) modelCancelHold.getValueAt(i, 14);
					_unit_id = modelCancelHold.getValueAt(i, 15).toString();
					_tra_header_id = (Integer) modelCancelHold.getValueAt(i, 16);
					_user_id = UserInfo.EmployeeCode.trim();
					_approvedby = lookupApproveby_Active.getValue().trim();

					String SQL = "";		

					SQL = "SELECT sp_update_cancellation_hold(\n" +
							"    '"+_batchno+"',\n" + 
							"    '"+_entity_id+"' ,\n" + 
							"    '"+_proj_id+"' ,\n" + 
							"    '"+_pbl_id+"' ,\n" + 
							"    "+_seq_no+" ,\n" + 
							"    '"+_unit_id+"' ,\n" + 
							"    '"+_due_date+"' , \n" + 
							"    '"+_reason+"' ,\n" + 
							"    '"+_remarks+"' ,\n" + 
							"    '"+_user_id+"' ,\n" + 
							"    '"+_user_id+"' ,\n" + 
							"    '"+_approvedby+"' ,\n" + 
							"    "+ifSelected+" ,\n" + 
							"    "+_tra_header_id+" )";


					System.out.printf("SQL: %s%n", SQL);
					pgSelect db = new pgSelect();
					db.select(SQL);
				}
			}

			FncGlobal.stopProgress();
		}
	}

	public class ForPosting_Tag implements Runnable{
		@Override
		public void run() {
			FncGlobal.startProgress("Saving  . . .Please wait ");

			postingStatuTag();


			FncGlobal.stopProgress();
		}
	}

	@SuppressWarnings("unchecked")
	private void clear(){
		_proj_id = null;
		if (tabpane.getSelectedIndex() == 0) {

			//lookupCompany.setValue("");
			//txtCompany.setText("");

			if (rbtnPerAccount.isSelected()) {

				txtAmountDue.setValue(null);
				txtareaReason.setText("");
				lookupClientName.setValue("");
				txtClientName.setText("");
				//	txtDayPD.setText("");
				txtDefaultDate.setText("");
				txtMonthPD.setText("");
				txtStage.setText("");
				txtPBLDesc.setText("");
				txtPBLID.setText("");
				//txtRemarks.setText("");
				txtReason_.setText("");
				lookupReason.setValue("");
				txtProjectName_pa.setText("");
				lookupProjID_pa.setValue("");
				lookupApproveby_Active.setValue("");
				txtApprovedBy_Active.setText("");
				lblNote.setVisible(false);
				lblNote1.setVisible(false);

			}else{

				lookupProjID.setValue("");
				txtProjectName.setText("");
				lookupApproveby_Active.setValue("");
				txtApprovedBy_Active.setText("");
				FncTables.clearTable(modelPerBatchModel);
				rowHeadePerBatch.setModel(new DefaultListModel());
				scrollPerBatch.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPerBatch.getRowCount())));

			}
		}

		if (tabpane.getSelectedIndex() == 1) {

			lookupProjID_Hold.setValue("");
			txtProjectName_Hold.setText("");
			lookupApproveby_Hold.setValue("");
			txtApprovedBy_Hold.setText("");
			FncTables.clearTable(modelCancelHold);
			rowHeadeCancelHold.setModel(new DefaultListModel());
			scrollCancelHold.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblCancelHold.getRowCount())));

		}

		if (tabpane.getSelectedIndex() == 2) {
			lookupProjID_Tag.setValue("");
			txtProjectName_Tag.setText("");
			FncTables.clearTable(modelCancelTag);
			rowHeaderCancelTag.setModel(new DefaultListModel());
			scrollCancelTag.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblCancelTag.getRowCount())));

		}
	}

	private void formload(){
		lookupCompany.setRequestFocusEnabled(true);

		setEnableComponents_Active(false);

		if (cmbBuyerType.getSelectedIndex() == 0 ) {

			table_name ="ihf_due_accounts";

			if (rbtnPerAccount.isSelected()) {
				//lookupClientName.setLookupSQL(functions.getIHFClient());	
			}

		}else{

			table_name ="pagibig_due_accounts";
			if (rbtnPerAccount.isSelected()) {
			//	lookupClientName.setLookupSQL(functions.getHDMFClient());	
			}
		}
	}

	private void postingPerAccount(){
		_batchno =functions.getBatchNo();
		
		db.select("SELECT _payments_made('"+_entity_id +"','"+_proj_id +"','"+_pbl_id +"',"+ _seq_no +")");

		if (db.isNotNull()) {
			_monthspaid = (Integer) (db.Result[0][0] == null ? 0 : db.Result[0][0]);
		}
		//_reason = txtareaReason.getText();
		_monthspd = Integer.valueOf(txtMonthPD.getText());
		_reason_id = lookupReason.getValue();
		_remarks = txtareaReason.getText();
		_cancelledby = UserInfo.EmployeeCode.trim();
		_user_id = UserInfo.EmployeeCode.trim();
		_approvedby = lookupApproveby_Active.getValue().toString();
		_stage =  txtStage.getText().toString();

		//TEMP 
		_due_date = dateFormat("2016-04-14");

		if (cmbBuyerType.getSelectedIndex()==0) {
			if (_monthspaid >= 24 ) {
				_macedalaw = true;

			}
			if (_monthspaid  <  24 ) {
				_macedalaw = false;
			}
			buyer_id = "02";
		}else{
			_macedalaw = false;
			buyer_id = "04";
		}


		if (cmbCancelType_Per.getSelectedIndex() == 1) {
			cancel_type = "C";
		}
		if (cmbCancelType_Per.getSelectedIndex() == 2) {
			cancel_type = "B";
		}

		//Timestamp default_date = (Timestamp) _default_date ;
		//Timestamp lastpaid_date = (Timestamp) _lastpaid_date ;

		/*String SQL = "SELECT sp_update_cancellation_per_account(\n" + 
				"    '"+buyer_id+"',---_buyer_id ,\n" + 
				"    '"+ _entity_id +"',---p_entity_id ,\n" + 
				"    '"+ _proj_id +"', ---p_proj_id ,\n" + 
				"    '"+ _pbl_id +"', ---p_pbl_id ,\n" + 
				"    "+ _seq_no +", ---i_seq_no ,\n" + 
				"    '"+ _batchno +"', ---p_batchno ,\n" + 
				"    "+_monthspd+", ---i_monthspd ,\n" + 
				"    "+_dayspd +", ---i_dayspd ,\n" + 
				"    '"+_amountdue +"', ---n_amountdue ,\n" + 
				"    "+_macedalaw+", ---b_maceda_law ,\n" + 
				"    '"+_reason +"', ---p_reason text,\n" + 
				"    '"+_remarks +"', ---p_remarks text,\n" + 
				"    '"+_user_id +"', ---p_cancelledby ,\n" + 
				"    '"+_approvedby +"', ---p_approvedby ,\n" +  
				"    '"+_monthspaid +"', ---i_monthspaid ,\n" + 
				"    '"+_lastpaid_date +"'::timestamp, ---p_lastpaid_date ,\n" + 
				"    '"+_stage+"', ---p_part_id ,\n" + 
				"    '"+_default_date +"'::timestamp, ---p_default_date ,\n" +  
				"    "+true+" )---p_selected "
				+ "";
		 */


		db.select("SELECT sp_post_projected_cancellation(\n" + 
				"    '"+_entity_id+"', ---p_entity_id ,\n" + 
				"    '"+_proj_id+"', ---p_proj_id ,\n" + 
				"    '"+_pbl_id+"', ---p_pbl_id ,\n" + 
				"    "+_seq_no+", ---i_seq_no ,\n" +  
				"    '"+_stage+"', ---p_part_stage ,\n" + 
				"    '"+_batchno+"', ---p_batchno ,\n" +
				"    '"+_monthspd+"', ---i_months_pd ,\n" +
				"   NULL, ---i_days_pd ,\n" + 
				"    '"+_amountdue+"', ---n_amountdue ,\n" + 
				"    '"+_lastpaid_date+"', ---p_lastpaid_date ,\n" + 
				"    '"+_default_date+"', ---p_default_date ,\n" + 
				"    NULL, ---p_lastsched_date ,\n" + 
				"    '"+_user_id+"', ---p_user_id ,\n" + 
				"    "+true+") ---p_selected ");

		String SQL = "SELECT sp_update_cancellation(\n" + 
				"    '"+_entity_id+"', ---p_entity_id ,\n" + 
				"    '"+_proj_id+"', ---p_proj_id ,\n" + 
				"    '"+_pbl_id+"', ---p_pbl_id ,\n" + 
				"    "+_seq_no+", ---i_seq_no ,\n" + 
				"    '"+_batchno+"', ---p_batchno ,\n" + 
				"    "+_monthspd+", ---i_monthspd ,\n" + 
				"    NULLIF('"+_reason_id+"','null'), ---p_reason text,\n" + 
				"    NULLIF('"+_remarks+"','null'), ---p_remarks text,\n" + 
				"    '"+_cancelledby+"', ---p_cancelledby ,\n" + 
				"    '"+_approvedby+"', ---p_approvedby ,\n" + 
				"    "+_monthspaid+", ---i_monthspaid ,\n" + 
				"    "+true+",'"+_user_id+"', '"+cancel_type+"','"+_due_date+"' )---p_selected ;";

		System.out.printf("SQL: %s%n", SQL);
		db.select(SQL);

		SQL = "SELECT sp_post_cancellation_final(\n" +  
				"    '"+_entity_id+"', ---p_entity_id ,\n" + 
				"    '"+_proj_id+"', ---p_proj_id ,\n" + 
				"    '"+_pbl_id+"', ---p_pbl_id ,\n" + 
				"    "+_seq_no+", ---i_seq_no ,\n" + 
				"    '"+_user_id+"', ---p_user_id ,\n" + 
				"    '"+_stage+"', ---stage_id ,\n" + 
				"    '"+_batchno+"', ---p_batchno ,\n" + 
				"    "+true+", \n"+ 
				"    "+_macedalaw+", \n" + 
				"	 '"+_due_date+"') ";

		db.select(SQL);

		System.out.printf("SQL: %s%n", SQL); 

	}

	private void perBatchRecommend(){

		for (int i = 0 ;  i < modelPerBatchModel.getRowCount(); i++) {
			Boolean ifSelected =(Boolean) modelPerBatchModel.getValueAt(i, 0);

			if (ifSelected) {

				_entity_id  = modelPerBatchModel.getValueAt(i, 20).toString();
				_proj_id =  modelPerBatchModel.getValueAt(i, 21).toString();
				_pbl_id =  modelPerBatchModel.getValueAt(i, 22).toString().trim();
				_seq_no =  (Integer) modelPerBatchModel.getValueAt(i, 23);
				_stage =  modelPerBatchModel.getValueAt(i, 12).toString().trim();
				_approvedby = lookupApproveby_Active.getValue().trim();
				_monthspd = (Integer) modelPerBatchModel.getValueAt(i, 15); 

				//_dayspd = (Integer) modelPerBatchModel.getValueAt(i, 13);		
				//_amountdue = (BigDecimal) modelPerBatchModel.getValueAt(i, 15);
				_reason = modelPerBatchModel.getValueAt(i, 29).toString().replace("'", "''");
				_remarks = modelPerBatchModel.getValueAt(i, 30).toString().replace("'", "''");
				_cancelledby = UserInfo.EmployeeCode.trim();
				_user_id =  UserInfo.EmployeeCode.trim();
				_monthspaid = (Integer) modelPerBatchModel.getValueAt(i, 16);
				stage_id = modelPerBatchModel.getValueAt(i,12).toString().trim(); 
				_due_date = dteDueDate.getDate();

				System.out.printf("_monthspd: %s%n", _monthspd);

				db.select("SELECT remark_id FROM mf_remarks WHERE remdesc = TRIM('"+_reason+"') AND remtype = '02'");

				if (db.isNotNull()) {

					_reason_id = db.Result[0][0].toString();
				}


				if (cmbBuyerType.getSelectedIndex()==0) {
					if (_monthspaid >= 24 ) {
						_macedalaw = true;

					}
					if (_monthspaid  <  24 ) {
						_macedalaw = false;
					}

					buyer_id = "02";
				}else{
					_macedalaw = false;	
					buyer_id = "04";
				}

				if (cmbCancelType.getSelectedIndex() == 1) {
					cancel_type = "C";
				}
				if (cmbCancelType.getSelectedIndex() == 2) {
					cancel_type = "B";
				}
				String SQL = "SELECT sp_update_cancellation(\n" + 
						"    '"+_entity_id+"', ---p_entity_id ,\n" + 
						"    '"+_proj_id+"', ---p_proj_id ,\n" + 
						"    '"+_pbl_id+"', ---p_pbl_id ,\n" + 
						"    "+_seq_no+", ---i_seq_no ,\n" + 
						"    '"+_batchno+"', ---p_batchno ,\n" + 
						"    "+_monthspd+", ---i_monthspd ,\n" + 
						"    NULLIF('"+_reason_id+"','null'), ---p_reason text,\n" + 
						"    NULLIF('"+_remarks+"','null'), ---p_remarks text,\n" + 
						"    '"+_cancelledby+"', ---p_cancelledby ,\n" + 
						"    '"+_approvedby+"', ---p_approvedby ,\n" + 
						"    "+_monthspaid+", ---i_monthspaid ,\n" + 
						"    "+ifSelected+",'"+_user_id+"', '"+cancel_type+"','"+_due_date+"' )---p_selected ;";

				System.out.printf("SQL: %s%n", SQL);
				db.select(SQL);

				SQL = "SELECT sp_post_cancellation_final(\n" +  
						"    '"+_entity_id+"', ---p_entity_id ,\n" + 
						"    '"+_proj_id+"', ---p_proj_id ,\n" + 
						"    '"+_pbl_id+"', ---p_pbl_id ,\n" + 
						"    "+_seq_no+", ---i_seq_no ,\n" + 
						"    '"+_user_id+"', ---p_user_id ,\n" + 
						"    '"+stage_id+"', ---stage_id ,\n" + 
						"    '"+_batchno+"', ---p_batchno ,\n" + 
						"    "+ifSelected+", \n"+ 
						"    "+_macedalaw+", \n" + 
						"	 '"+_due_date+"') ";

				db.select(SQL);
				System.out.printf("SQL: %s%n", SQL); 
				//db.select("SELECT _get_client_undermaceda_law('"+_entity_id+"', '"+_proj_id+"', '"+_pbl_id+"', "+_seq_no+",'"+_due_date+"')"); 
				//if_undermacedalaw = (Boolean) db.Result[0][0];
			} 

		}
		JOptionPane.showMessageDialog( getContentPane(), "Account(s) Cancelled with Batch No. : "+_batchno+"   ", "Successful", JOptionPane.INFORMATION_MESSAGE );
	}

	private void perBatchCSV(){

		for (int i = 0 ;  i < modelPerBatchModel.getRowCount(); i++) {
			Boolean ifSelected = (Boolean) modelPerBatchModel.getValueAt(i, 0);

			if (ifSelected) {

				_entity_id  = modelPerBatchModel.getValueAt(i, 20).toString();
				_proj_id =  modelPerBatchModel.getValueAt(i, 21).toString();
				_pbl_id =  modelPerBatchModel.getValueAt(i, 22).toString().trim();
				_seq_no =  (Integer) modelPerBatchModel.getValueAt(i, 23);
				_phase_no =  modelPerBatchModel.getValueAt(i, 26).toString().trim();
				_monthspaid = (Integer) modelPerBatchModel.getValueAt(i, 16);

				_approvedby = lookupApproveby_Active.getValue().trim();
				_csv_mount =  (BigDecimal)modelPerBatchModel.getValueAt(i, 31);

				rplfno = functions.rplfNo(functions.co_id(_proj_id)) ; 

				forcsv =    rplfno + "  :  " + modelPerBatchModel.getValueAt(i, 2).toString();

				String SQL = "SELECT sp_posting_cancellation_csv_new(\n" + 
						"	'"+_entity_id+"' ,\n" + 
						"	'"+_proj_id+"' ,\n" + 
						"	'"+_pbl_id +"' ,\n" +  
						"	"+_seq_no+" ,\n" + 
						"	'"+_phase_no+"' ,\n" + 
						"	'"+UserInfo.Branch+"'  ,\n" + 
						"	"+ifSelected+" ,\n" + 
						"	'"+_csv_mount+"' ,\n" + 
						"	'"+_approvedby+"'  ,\n" + 
						"	'"+UserInfo.EmployeeCode+"' ,\n" + 
						"	'"+UserInfo.Department+"'  ,\n" + 
						"	'"+UserInfo.Division+"', \n" +
						" 	'"+_monthspaid+"' );";

				System.out.printf("SQL: %s%n", SQL);
				db.select(SQL);
			}

		}

	}

	private boolean _p_table;
	private Integer _cancel_csv_id = 0;
	private String _cancel_csv_status_id;
	private String _transdate;

	private void postingStatuTag(){
		for (int i = 0 ;  i < modelCancelTag.getRowCount(); i++) {

			Boolean ifSelected =  (Boolean) modelCancelTag.getValueAt(i, 0);

			if (ifSelected) {

				_user_id 	= UserInfo.EmployeeCode.trim();
				_transdate	=  modelCancelTag.getValueAt(i, 7).toString();
				_remarks 	= modelCancelTag.getValueAt(i, 8).toString().replace("'", "''");
				_cancel_csv_status_id =  modelCancelTag.getValueAt(i, 9).toString();

				_entity_id = modelCancelTag.getValueAt(i, 11).toString();
				_proj_id = modelCancelTag.getValueAt(i, 12).toString();
				_pbl_id = modelCancelTag.getValueAt(i, 13).toString();
				_seq_no = (Integer) modelCancelTag.getValueAt(i, 14); 
				String SQL = "";

				if (cmbProcessFor_Tag.getSelectedIndex() == 0) {
					_p_table = true;
					_cancel_csv_id = (Integer) modelCancelTag.getValueAt(i, 16); 

				}else{
					_p_table = false;
					_cancel_csv_id = (Integer) modelCancelTag.getValueAt(i, 10); 
				}

				SQL = "SELECT sp_cancellation_status_tag(\n" + 
						"    "+_p_table+" ,\n" + 
						"    "+_cancel_csv_id+" ,\n" + 
						"    '"+_cancel_csv_status_id+"' ,\n" + 
						"    '"+_transdate+"'::timestamp ,\n" + 
						"    '"+_remarks+"' ,\n" + 
						"    '"+_user_id+"' ,\n" + 
						"    "+ifSelected+" )";		

				System.out.printf("SQL: %s%n", SQL);
				db.select(SQL);


			} 
		}	
	}
	public class ForPreview implements Runnable{

		@Override
		public void run() {
			Map<String, Object> mapParameters = new HashMap<String, Object>();
			mapParameters.put("company", company);
			mapParameters.put("co_id", co_id);
			mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
			mapParameters.put("lblListof", lblListof);
			mapParameters.put("dateFrom", dateFrom);
			mapParameters.put("dateTo", dateTo);
			mapParameters.put("printall", printall);
			mapParameters.put("proj_id", _proj_id);

			if (tabpane.getSelectedIndex() == 0) {
				if (cmbProcessFor.getSelectedIndex() == 0) {
					FncReport.generateReport("/Reports/rptCancellation_Active.jasper", "",  mapParameters,functions.printSQL);	
				}
			}

			if (tabpane.getSelectedIndex() == 2) {
				if (cmbProcessFor_Tag.getSelectedIndex() == 0) {

					FncReport.generateReport("/Reports/rptCancellationStatus.jasper", "Status of Canceled Accounts",  company, mapParameters);	
				}else{

					FncReport.generateReport("/Reports/rptCancellationStatus_CSV.jasper", "Status of Accounts With CSV",  company, mapParameters);
				}
			}	

		}
	}

	public class ForNotices implements Runnable{

		private String db_reason_id;
		private ArrayList<String> listReport = new ArrayList<String>();
		private ArrayList<String> listTitle = new ArrayList<String>();
		private ArrayList<Map> listParameters = new ArrayList<Map>();
		private String db_entity_id;
		private String db_proj_id;
		private String db_pbl_id;
		private Integer db_seq_no;


		@Override
		public void run() {

			int OK = JOptionPane.showConfirmDialog(getContentPane(),"\nSelected Account(s) Posted on the Cancellation Table \n" +
					"\n Click OK to Display Notice","Successful",
					JOptionPane.OK_CANCEL_OPTION);
			if (OK == JOptionPane.OK_OPTION){

				Map<String, Object> mapParameters = new HashMap<String, Object>();
				mapParameters.put("batch_no", _batchno);
				mapParameters.put("company", company);
				String SQL = "";

				if (rbtnPerAccount.isSelected()) {
					SQL = "SELECT \n" + 
							"a.reason,\n" + 
							"a.entity_id,\n" + 
							"a.proj_id,\n" + 
							"a.pbl_id,\n" + 
							"a.seq_no,\n" + 
							"a.under_maceda_law,\n" + 
							"(SELECT notice_desc FROM mf_notice_type WHERE notice_id = b.notice_id )\n" + 
							"FROM rf_cancellation a\n" + 
							"LEFT JOIN rf_client_notices b  ON a.entity_id = b.entity_id \n" + 
							"WHERE rec_id = (SELECT MAX(rec_id) FROM rf_client_notices) and status_id = 'A'";
					_due_date = dateFormat("2016-04-14");

				}else{

					SQL = "SELECT \n" + 
							"a.reason,\n" + 
							"a.entity_id,\n" + 
							"a.proj_id,\n" + 
							"a.pbl_id,\n" + 
							"a.seq_no,\n" + 
							"a.under_maceda_law,\n" + 
							"(SELECT notice_desc FROM mf_notice_type WHERE notice_id = b.notice_id )\n" + 
							"FROM rf_cancellation a\n" + 
							"LEFT JOIN rf_client_notices b ON a.batch_no = b.batch_no  AND a.entity_id = b.entity_id \n" + 
							"WHERE b.batch_no ='"+_batchno+"' and status_id = 'A'";
					_due_date = dteDueDate.getDate();
				}

				db.select(SQL);

				System.out.println("*************SQL REPORT:\n " +SQL);

				for (int i = 0; i < db.getRowCount(); i++) {

					db_reason_id = db.Result[i][0].toString();
					System.out.println("NOTICE : " + db_reason_id);
					db_entity_id = db.Result[i][1].toString();
					db_proj_id = db.Result[i][2].toString();
					db_pbl_id = db.Result[i][3].toString();
					db_seq_no = (Integer)db.Result[i][4];

					title_report = db.Result[i][6].toString();

					if (db_reason_id.equals("00036") || db_reason_id.equals("00227") ||  db_reason_id.equals("00037") || db_reason_id.equals("00034") || db_reason_id.equals("00039")) {

						System.out.println("DUMAAN B DITO FOR FIRST REPORT");
						mapParameters.put("entity_id", db_entity_id);
						mapParameters.put("proj_id", db_proj_id);
						mapParameters.put("pbl_id", db_pbl_id);
						mapParameters.put("seq_no", db_seq_no);

						pgSelect dbs = new pgSelect();
						dbs.select("SELECT get_clients_has_moved_in('"+db_entity_id+"', '"+db_proj_id+"', '"+db_pbl_id+"', "+db_seq_no+",'"+_due_date+"' ) ");


						if ((Boolean) dbs.Result[0][0]) {
							listReport.add(String.format("/Reports/%s.jasper", "rptNoticeOfCancellationAndToVacate"));
							listTitle.add(String.format("%s - Page 2", title_report));
							listParameters.add(mapParameters);
						}else{
							listReport.add(String.format("/Reports/%s.jasper", "rptNoticeOfCancellationWithNonMacedaLaw"));
							listTitle.add(String.format("%s ", title_report));
							listParameters.add(mapParameters);
						}
					}

					if (db_reason_id.equals("00232") || db_reason_id.equals("00231")) {
						System.out.println("DUMAAN B DITO FOR SECOND REPORT");
						mapParameters.put("entity_id", db_entity_id);
						mapParameters.put("proj_id", db_proj_id);
						mapParameters.put("pbl_id", db_pbl_id);
						mapParameters.put("seq_no", db_seq_no);

						listReport.add(String.format("/Reports/%s.jasper", "rptNoticeOfCancellation_DueDocs"));
						listTitle.add(String.format("%s - Page 2", title_report));
						listParameters.add(mapParameters);
					}

					if(db_reason_id.equals("00035") || db_reason_id.equals("00002")){ 
						//FncReport.generateReport("/Reports/rptNoticeOfCancellation_Backout.jasper", title_report,  company, mapParameters); // FOR BACKOUT	

						mapParameters.put("entity_id", db_entity_id);
						mapParameters.put("proj_id", db_proj_id);
						mapParameters.put("pbl_id", db_pbl_id);
						mapParameters.put("seq_no", db_seq_no);

						listReport.add(String.format("/Reports/%s.jasper", "rptNoticeOfCancellation_Backout"));
						listTitle.add(String.format("%s ", title_report));
						listParameters.add(mapParameters);

					}
				}

				FncReport.generateReport(listReport.toArray(), listTitle.toArray(), listParameters.toArray(), "Notice to Cancellation");

			}
		}
	}
	private void setEnabled(){

		if (lookupCompany.getValue().isEmpty()) {

			if (tabpane.getSelectedIndex()==0) {

				this.setComponentsEnabled(pnlCancelActive, false);

			}

		/*	if (tabpane.getSelectedIndex()==1) {
				this.setComponentsEnabled(pnlCancelHoldUnit, false);				
			}
*/
			if (tabpane.getSelectedIndex()==1) {
				this.setComponentsEnabled(pnlTagAccounts, false);
			}
		}else{
			if (tabpane.getSelectedIndex()==0) {
				rbtnPerAccount.setEnabled(true);
				rbtnPerBatch.setEnabled(true);

			}

			if (tabpane.getSelectedIndex()==1) {
				//	lblProjID_Hold.setEnabled(true);
				//	lookupProjID_Hold.setEnabled(true);
				//	txtProjectName_Hold.setEnabled(true);
			}
		}
	}

	///TEMP

	public Date dateFormat(String dates){

		DateFormat formatter ; 
		Date date = null ; 
		formatter = new SimpleDateFormat("yyyy-MM-dd");
		try {
			date = (Date)formatter.parse(dates);
		} catch (ParseException e) {
		} 


		return date;
	}

}
