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
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.math.BigDecimal;
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
import javax.swing.JButton;
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
import org.jdesktop.swingx.JXPanel;

import com.toedter.calendar.JTextFieldDateEditor;

import Database.pgSelect;
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
import components._JButton;
import components._JInternalFrame;
import components._JTabbedPane;
import components._JTableMain;
import components._JXTextField;
import interfaces._GUI;
import tablemodel.model_CancelActive_PerBatch;
import tablemodel.model_Cancellation_Status;

/**
 * @author PC-112l
 *
 */
public class _CancellationProcessing extends _JInternalFrame implements _GUI, LookupListener,ChangeListener,MouseListener,MouseMotionListener,KeyListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */

	public static String title = "Cancellation Processing";
	public static Dimension frameSize = new Dimension(900, 650);
	private Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	private JXPanel pnlMain;
	private JXPanel pnlNorth;
	private JLabel lblCompany;
	private _JLookup lookupCompany;

	private _FPromissoryNoteCommintment methods = new _FPromissoryNoteCommintment();
	private JPanel pnlCenter;
	private _JTabbedPane tabpane;
	private JPanel pnlCancelActive;
	private _JTabbedPane tabpane_per_account;
	private JPanel pnlPerBatch;
	private JPanel pnlPerAccount;
	private JLabel lblBatchNo;
	private _JLookup lookupBatchNo;
	private JXPanel pnlMainBatch;
	private JXPanel pnlNorthBatch;
	private JXPanel pnlActiveNorth;
	private JLabel lblProcessFor;
	private JLabel lblProjID;
	private JLabel lblForTestingDate;
	private DefaultComboBoxModel cmbProcessForModel;
	private _JLookup lookupProjID;
	private JComboBox cmbProcessFor;
	private _JDateChooser dteDueDate;
	private _JButton btnGenerateActive;
	private _JButton btnPreviewActive;
	private JLabel lblBuyerType;
	private DefaultComboBoxModel cmbBuyerTypeModel;
	private JComboBox cmbBuyerType;
	private JPanel pnlCenterBatch;
	private JScrollPane scrollPerBatch;
	private model_CancelActive_PerBatch modelPerBatchModel;
	private _JTableMain tblPerBatch;
	private JList rowHeadePerBatch;
	private DefaultComboBoxModel cmbCancelTypeModel;
	private JComboBox cmbCancelType;
	private JXPanel pnlNorthPerA;
	private JXPanel pnlMainPerA;
	private JXPanel pnlWest;
	private JLabel lblProj;
	private _JLookup lookupProj;
	private JXPanel pnlEast;
	private JXPanel pnlCenterPerA;
	private JLabel lblClientID;
	private JLabel lblPBL;
	private JLabel lblStage;
	private JLabel lblMonthPD;
	private JLabel lblDefaultDate;
	private JLabel lblCanclType;
	private JLabel lblAmountDue;
	private JLabel lblReason;
	private JXPanel pnlSouthPerA;
	private _JLookup lookupClientName;
	private JTextField txtPBLID;
	private JTextField txtPBLDesc;
	private JTextField txtStage;
	private JTextField txtMonthPD;
	private JTextField txtDefaultDate;
	private DefaultComboBoxModel cmbCancelTypeModel_Per;
	private _JXFormattedTextField txtAmountDue;
	private _JLookup lookupReason;
	private JTextField txtReason_;
	private JComboBox cmbCancelType_Per;
	private JScrollPane scrollRemarks;
	private JTextArea txtareaRemarks;
	private JLabel lblNote1;
	private JXPanel pnlEastPerA;
	private _JXTextField txtProj;
	private _JXTextField txtCompany;
	private _JXTextField txtProjectName;
	private _JXTextField txtClientName;
	private String co_id;
	private String company;
	private String company_logo;

	private _FCancellationProcessing functions= new _FCancellationProcessing();
	private JPanel pnlSouth_Center_Active;
	private JPanel pnlCenter_Approved_C_Active;
	private JLabel lblApproveBy_Active;
	private _JLookup lookupApproveby_Active;
	private JTextField txtApprovedBy_Active;
	private JPanel pnlSouth_East_Active;
	private _JButton btnSave_Active;
	private _JButton btnPost_Active;
	private _JButton btnClear_Active;
	private _JButton btnEdit_Active;
	private _JButton btnNew_Active;
	private String proj_id;
	private String proj_name;
	private String table_name;
	private String lblListof;
	private String _reason_id;
	private String approved_id;
	private String aaproved_name;
	pgSelect db = new pgSelect();
	private int tagged;

	ButtonGroup grpButton = new ButtonGroup();
	ButtonGroup grpButton_Pa = new ButtonGroup();
	
	private JPanel pnlCancelTagStatus;
	private JPanel pnlCancelTagStatus_North;
	private JPanel pnlCancelTagStatus_North_North;
	private JLabel lblProcessFor_Tag;
	private JLabel lblProjID_Tag;
	private DefaultComboBoxModel cmbProcessForModel_Tag;
	private _JLookup lookupProjID_Tag;
	private JTextField txtProjectName_Tag;
	private _JButton btnGenerate_Tag;
	private _JButton btnPreview_Tag;
	private JPanel pnlCancelTagStatus_Center;
	private _JDateChooser dteStatus;
	private JScrollPane scrollCancelTag;
	private model_Cancellation_Status modelCancelTag;
	private _JTableMain tblCancelTag;
	private JList rowHeaderCancelTag;
	private JPanel pnlCancelTagStatus_South;
	private JLabel lblEditable_Tag;
	private _JButton btnPost_Tag;
	private _JButton btnClear_Tag;
	private JComboBox cmbProcessFor_Tag;
	private String pbldesc;
	private String client_name;
	private BigDecimal _amountdue;
	private Date _lastpaid_date;
	private Date _default_date;
	private JPanel pnlSouthBatch;
	private JPanel pnlSouth_Center_Active_PA;
	private JPanel pnlCenter_Approved_C_Active_PA;
	private JLabel lblApproveBy_Active_PA;
	private _JLookup lookupApproveby_Active_PA;
	private JTextField txtApprovedBy_Active_PA;
	private JPanel pnlSouth_East_Active_PA;
	private _JButton btnNew_Active_PA;
	private _JButton btnEdit_Active_PA;
	private _JButton btnSave_Active_PA;
	private _JButton btnPost_Active_PA;
	private _JButton btnClear_Active_PA;
	private JPanel pnlSouthPerA_C;
	private Boolean SelectedItem;
	private Date dateFrom;
	private Date dateTo;
	private Boolean printall;
	private String _proj_name;
	private JPanel pnlBatch;
	private _JLookup lookupClientID;
	private String part_id;
	private String unit_id;
	private String pbl_desc;
	private String approved_name;

	public _CancellationProcessing() {
		super(title, true, true, true, true);
		initGUI();
		_setEnabled(false);
		getDefaultCompany();
		
	}

	/**
	 * @param title
	 */
	public _CancellationProcessing(String title) {
		super(title, true, true, true, true);
		initGUI(); 
		getDefaultCompany();
	}

	/**
	 * @param title
	 * @param resizable
	 * @param closable
	 * @param maximizable
	 * @param iconifiable
	 */
	public _CancellationProcessing(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
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
					pnlNorth.setPreferredSize(new Dimension(pnlMain.getWidth(), 30));
					//	pnlNorth.setBorder(lineBorder);
					{
						{
							lblCompany = new JLabel("Company");
							pnlNorth.add(lblCompany,BorderLayout.WEST);
							lblCompany.setPreferredSize(new Dimension(74, 20));
						}
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
					pnlCenter = new JPanel(new BorderLayout());
					pnlMain.add(pnlCenter, BorderLayout.CENTER);
					{
						tabpane = new _JTabbedPane();
						pnlCenter.add(tabpane,BorderLayout.CENTER);
						tabpane.setVisible(true);
						tabpane.setEnabled(true);
						tabpane.addMouseListener(this);
						tabpane.addChangeListener(this);
						{
							pnlCancelActive = new  JPanel(new BorderLayout(5,5));
							tabpane.addTab("Cancellation of Active Accounts", pnlCancelActive);
							{

								pnlActiveNorth = new JXPanel(new BorderLayout(5,5));
								pnlCancelActive.add(pnlActiveNorth,BorderLayout.NORTH);
								//pnlActiveNorth.setBorder(lineBorder);
								pnlActiveNorth.setPreferredSize(new Dimension(0, 30));
								{
									JPanel pnlNorth = new  JPanel(new BorderLayout(5, 5));
									pnlActiveNorth.add(pnlNorth,BorderLayout.NORTH);
								}
								{
									{

										JPanel pnlCancelActive_North_East = new  JPanel(new  BorderLayout(5, 5));
										pnlActiveNorth.add(pnlCancelActive_North_East,BorderLayout.WEST);
										pnlCancelActive_North_East.setPreferredSize(new  Dimension(300, 60));
										{
											lblBuyerType = new JLabel(" Buyer Type :");
											pnlCancelActive_North_East.add(lblBuyerType,BorderLayout.WEST);
											lblBuyerType.setPreferredSize(new  Dimension(80, 60));
										}
										{
											cmbBuyerTypeModel = new DefaultComboBoxModel(new String[] {"All", "IHF Accounts", "Pag-Ibig Accounts" });
											cmbBuyerType = new JComboBox();
											pnlCancelActive_North_East.add(cmbBuyerType,BorderLayout.CENTER);
											cmbBuyerType.setModel(cmbBuyerTypeModel);
											cmbBuyerType.setPreferredSize(new Dimension(220, 25));
											cmbBuyerType.addActionListener(this);
										}
									}
								}
								{
									pnlBatch = new JPanel(new BorderLayout(5,5));
									pnlActiveNorth.add(pnlBatch,BorderLayout.EAST);
									pnlBatch.setPreferredSize(new Dimension(250, 20));
									{ 
										lblBatchNo = new  JLabel("Batch No :");
										pnlBatch.add(lblBatchNo,BorderLayout.WEST);
										//lblBatchNo.setHorizontalTextPosition(SwingConstants.EAST);
									}
									{
										lookupBatchNo = new _JLookup("Search Batch No", "Batch No", 0) ; 
										pnlBatch.add(lookupBatchNo,BorderLayout.CENTER);
										lookupBatchNo.addLookupListener(this);
										lookupBatchNo.setReturnColumn(0);
										lookupBatchNo.setPreferredSize(new Dimension(60, 20));
									}
								}
							}
							{

								tabpane_per_account = new _JTabbedPane();
								pnlCancelActive.add(tabpane_per_account,BorderLayout.CENTER);
								tabpane_per_account.setVisible(true);
								tabpane_per_account.setEnabled(true);
								tabpane_per_account.addChangeListener(this);
								{
									pnlPerBatch = new  JPanel(new BorderLayout(5,5));
									tabpane_per_account.addTab("Per Batch Cancellation", pnlPerBatch);

									pnlPerBatch.setEnabled(true);
									pnlPerBatch.setVisible(true);
									{
										pnlMainBatch = new JXPanel(new BorderLayout(5,5));
										pnlPerBatch.add(pnlMainBatch,BorderLayout.CENTER);
										{
											pnlNorthBatch = new JXPanel(new BorderLayout(5,5));
											pnlMainBatch.add(pnlNorthBatch,BorderLayout.NORTH);
											pnlNorthBatch.setPreferredSize(new Dimension(0, 80));
											{
												JPanel _pnlLabel = new JPanel(new GridLayout(3, 1, 3, 3));
												pnlNorthBatch.add(_pnlLabel,BorderLayout.WEST);
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
													lblForTestingDate.setVisible(false);
												}
											}


											{
												JPanel pnlProcess = new JPanel(new GridLayout(3, 1, 3, 3));
												pnlNorthBatch.add(pnlProcess,BorderLayout.CENTER);
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
														lookupProjID = new _JLookup("ID", "Search Project", 0);
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

													dteDueDate= new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
													pnlProcess.add(dteDueDate);
													dteDueDate.setDate(null);
													dteDueDate.setVisible(false);

												}
											}
											{
												JPanel pnlButton = new JPanel(new GridLayout(1, 2, 3, 3));
												pnlNorthBatch.add(pnlButton,BorderLayout.EAST);
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

											pnlCenterBatch = new JPanel(new BorderLayout(5, 5));
											pnlMainBatch.add(pnlCenterBatch,BorderLayout.CENTER);
											pnlCenterBatch.setBorder(components.JTBorderFactory.createTitleBorder("List of IHF Accounts"));
											pnlCenterBatch.setPreferredSize(new Dimension(pnlCancelActive.getWidth(), 300));
											{
												cmbCancelTypeModel = new DefaultComboBoxModel(new String[] {"","Company Initiated", "Buyer Initiated"});
												cmbCancelType = new JComboBox();
												cmbCancelType.setModel(cmbCancelTypeModel);
												cmbCancelType.addActionListener(this);

											}
											{

												scrollPerBatch = new JScrollPane();
												pnlCenterBatch.add(scrollPerBatch, BorderLayout.CENTER);
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
										}//CENTER
										{

											pnlSouthBatch = new JPanel(new BorderLayout(4,4));
											pnlMainBatch.add(pnlSouthBatch, BorderLayout.SOUTH);
											pnlSouthBatch.setPreferredSize(new Dimension(pnlMain.getWidth(), 55));
											{
												JPanel pnlNorth = new  JPanel(new BorderLayout(5, 5));
												pnlSouthBatch.add(pnlNorth,BorderLayout.NORTH);
											}

											{
												pnlSouth_Center_Active = new  JPanel(new BorderLayout(5, 5));
												pnlSouthBatch.add(pnlSouth_Center_Active,BorderLayout.CENTER);
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
												pnlSouth_East_Active = new  JPanel(new GridLayout(1, 5, 3, 3));
												pnlSouthBatch.add(pnlSouth_East_Active,BorderLayout.EAST);
												pnlSouth_East_Active.setPreferredSize(new Dimension(400, 0));

												{
													btnNew_Active = new _JButton("New");
													pnlSouth_East_Active.add(btnNew_Active);
													btnNew_Active.setActionCommand("New");
													btnNew_Active.addActionListener(this);
													grpButton.add(btnNew_Active);
												}

												{
													btnEdit_Active = new _JButton("Edit");
													pnlSouth_East_Active.add(btnEdit_Active);
													btnEdit_Active.setActionCommand("Edit");
													btnEdit_Active.addActionListener(this);
													grpButton.add(btnEdit_Active);
												}
												{
													btnSave_Active = new _JButton("Save");
													pnlSouth_East_Active.add(btnSave_Active);
													btnSave_Active.addActionListener(this);
												}
												{
													btnPost_Active = new _JButton("Post");
													pnlSouth_East_Active.add(btnPost_Active);
													btnPost_Active.setActionCommand("Post");
													btnPost_Active.addActionListener(this);
												}

												{
													btnClear_Active = new _JButton("Clear");
													pnlSouth_East_Active.add(btnClear_Active);
													btnClear_Active.setActionCommand("Clear_Active");
													btnClear_Active.addActionListener(this);
													grpButton.add(btnClear_Active);

												}
											}

											JPanel _pnlSouth = new  JPanel(new BorderLayout(5, 5));
											pnlSouthBatch.add(_pnlSouth,BorderLayout.SOUTH);

										}
									}
								}
								{
									pnlPerAccount = new  JPanel(new BorderLayout(5,5));
									tabpane_per_account.addTab("Per Account Cancellation", pnlPerAccount);

									{
										pnlMainPerA = new JXPanel(new BorderLayout(5,5));
										pnlPerAccount.add(pnlMainPerA,BorderLayout.CENTER);
										{
											pnlNorthPerA = new JXPanel(new BorderLayout(5,5));
											pnlMainPerA.add(pnlNorthPerA,BorderLayout.NORTH);
											pnlNorthPerA.setPreferredSize(new Dimension(this.getWidth(), 40));
											//	pnlNorthPerA.setBorder(lineBorder);
											{
												JPanel pnlNorth = new  JPanel(new BorderLayout(5, 5));
												pnlNorthPerA.add(pnlNorth,BorderLayout.NORTH);
											}
											{
												pnlWest = new JXPanel(new BorderLayout(5,5));
												pnlNorthPerA.add(pnlWest,BorderLayout.WEST);
												pnlWest.setPreferredSize(new Dimension(500, 30));	
												//pnlWest.setBorder(lineBorder);
												{

													lblProj = new JLabel(" Project :");
													pnlWest.add(lblProj,BorderLayout.WEST);
													lblProj.setPreferredSize(new Dimension(114, 20));
												}
												{
													JPanel pnlProj = new JPanel(new BorderLayout(3,3));
													pnlWest.add(pnlProj,BorderLayout.CENTER);
													pnlProj.setPreferredSize(new Dimension(74, 20));
													{
														{

															lookupProj = new _JLookup("ID", "Search Project", 0) ;  
															pnlProj.add(lookupProj,BorderLayout.WEST);
															lookupProj.addLookupListener(this);
															lookupProj.setReturnColumn(0);
															lookupProj.setLookupSQL(methods.getCompany());
															lookupProj.setPreferredSize(new Dimension(100, 20));
														}
														{
															txtProj = new _JXTextField();
															pnlProj.add(txtProj,BorderLayout.CENTER);
															txtProj.setEditable(false);
															txtProj.setPrompt("Project Name");
															txtProj.setPreferredSize(new Dimension(100, 20));
														}
													}
												}
											}
											{
												pnlEast = new JXPanel(new BorderLayout());
												pnlNorthPerA.add(pnlEast,BorderLayout.EAST);
												pnlEast.setPreferredSize(new Dimension(400, 30));
												//	pnlEast.setBorder(lineBorder);
												{


													lblNote1 = new JLabel("<html><font color=red><i>Note :    Selected Client is not Recommended for Cancellation</i></font></html>");
													pnlEast.add(lblNote1,BorderLayout.EAST);
													lblNote1.setVisible(false);

												}
											}
										}
										{
											pnlCenterPerA = new JXPanel(new BorderLayout(5,5));
											pnlMainPerA.add(pnlCenterPerA,BorderLayout.CENTER);
											//	pnlCenterPerA.setBorder(lineBorder);
											{

												JPanel pnlLabel = new JPanel(new GridLayout(9, 1, 3, 3));
												pnlCenterPerA.add(pnlLabel,BorderLayout.WEST);
												{
													lblClientID = new JLabel("Client ID");
													pnlLabel.add(lblClientID);
												}
												{
													lblPBL = new JLabel(" Ph/Bl/Lt");
													pnlLabel.add(lblPBL);
													lblPBL.setPreferredSize(new Dimension(84, 25));
												}
												{ 
													lblStage= new JLabel(" Stage");
													pnlLabel.add(lblStage);
												}
												{
													lblMonthPD = new JLabel(" Months PD");
													pnlLabel.add(lblMonthPD);
												}
												{
													lblDefaultDate= new JLabel(" Default Date");
													pnlLabel.add(lblDefaultDate);
												}
												{
													lblCanclType = new JLabel(" Cancellation Type");
													pnlLabel.add(lblCanclType);
												}
												{
													lblAmountDue= new JLabel(" Amount Due");
													pnlLabel.add(lblAmountDue);
												}
												{
													lblReason= new JLabel(" Reason");
													pnlLabel.add(lblReason);
												}
												{
													lblReason= new JLabel(" Remarks");
													pnlLabel.add(lblReason);
												}
											}
											{

												JPanel pnlNorth_Center = new JPanel(new GridLayout(9, 1, 3, 3));
												pnlCenterPerA.add( pnlNorth_Center,BorderLayout.CENTER);	
												{

													{
														JPanel pnlLookUp = new JPanel(new BorderLayout(3,3));
														pnlNorth_Center.add(pnlLookUp,BorderLayout.CENTER);
														{
															lookupClientName = new _JLookup("ID", "Search Client", 0);
															pnlLookUp.add(lookupClientName,BorderLayout.WEST);
															lookupClientName.setPreferredSize(new Dimension(100, 0));
															lookupClientName.addLookupListener(this);
														}
														{
															txtClientName = new _JXTextField();
															pnlLookUp.add(txtClientName,BorderLayout.CENTER);
															txtClientName.setEditable(false);
															txtClientName.setPrompt("Client Name");
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
															lookupReason.setLookupSQL("SELECT remark_id, remdesc FROM mf_remarks WHERE remark_id IN ('00232','00227','00034','00035','00036','00037','00039') AND remtype = '02' ORDER BY remdesc");
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
														pnlNorth_Center.add(pnlTextArea,BorderLayout.CENTER);
														{

															scrollRemarks = new JScrollPane();
															pnlTextArea.add(scrollRemarks,BorderLayout.CENTER);
															scrollRemarks.setOpaque(true);
															scrollRemarks.setBorder(lineBorder);
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
											}
										}
										{
											pnlEastPerA = new JXPanel(new BorderLayout(5,5));
											pnlMainPerA.add(pnlEastPerA,BorderLayout.EAST);
											//	pnlEastPerA.setBorder(lineBorder);
											pnlEastPerA.setPreferredSize(new Dimension(200, 0));
										}
										{
											pnlSouthPerA = new JXPanel(new BorderLayout(5,5));
											pnlMainPerA.add(pnlSouthPerA,BorderLayout.SOUTH);
											//	pnlSouthPerA.setBorder(lineBorder);
											pnlSouthPerA.setPreferredSize(new Dimension(this.getWidth(), 100));
											{

												pnlSouthPerA_C = new JPanel(new BorderLayout(5,5));
												pnlSouthPerA.add(pnlSouthPerA_C, BorderLayout.SOUTH);
												pnlSouthPerA_C.setPreferredSize(new Dimension(pnlMain.getWidth(), 57));


												{
													JPanel pnlNorth = new  JPanel(new BorderLayout(5, 5));
													pnlSouthPerA_C.add(pnlNorth,BorderLayout.NORTH);
												}

												{
													pnlSouth_Center_Active_PA = new  JPanel(new BorderLayout(5, 5));
													pnlSouthPerA_C.add(pnlSouth_Center_Active_PA,BorderLayout.CENTER);
													{
														JPanel pnlCenter_Approved = new JPanel(new BorderLayout(5, 5));
														pnlSouth_Center_Active_PA.add(pnlCenter_Approved,BorderLayout.NORTH);
														pnlCenter_Approved.setPreferredSize(new Dimension(0, 5));
														pnlCenter_Approved.setVisible(true);

													}
													{
														pnlCenter_Approved_C_Active_PA = new JPanel(new BorderLayout(5, 5));
														pnlSouth_Center_Active_PA.add(pnlCenter_Approved_C_Active_PA,BorderLayout.CENTER);
														pnlCenter_Approved_C_Active_PA.setVisible(true);
														{
															lblApproveBy_Active_PA = new JLabel(" Approved By :");
															pnlCenter_Approved_C_Active_PA.add(lblApproveBy_Active_PA,BorderLayout.WEST);
														}
														{
															JPanel pnlApproved = new JPanel(new BorderLayout(3, 3));
															pnlCenter_Approved_C_Active_PA.add(pnlApproved,BorderLayout.CENTER);
															pnlApproved.setVisible(true);
															{
																lookupApproveby_Active_PA = new _JLookup("Emp. ID", "Search Employee", 0);
																pnlApproved.add(lookupApproveby_Active_PA,BorderLayout.WEST);
																lookupApproveby_Active_PA.setLookupSQL(functions.getApprovedby());
																lookupApproveby_Active_PA.setPreferredSize(new Dimension(60, 50));
																lookupApproveby_Active_PA.addLookupListener(this);

															}
															{
																txtApprovedBy_Active_PA = new JTextField();
																pnlApproved.add(txtApprovedBy_Active_PA,BorderLayout.CENTER);
																txtApprovedBy_Active_PA.setEditable(false);
															}
														}
													}

													{
														JPanel pnlCenter_Approved_S = new JPanel(new BorderLayout(5, 5));
														pnlSouth_Center_Active_PA.add(pnlCenter_Approved_S,BorderLayout.SOUTH);
														pnlCenter_Approved_S.setPreferredSize(new Dimension(0, 5));
													}	
												}
												{
													pnlSouth_East_Active_PA = new  JPanel(new GridLayout(1, 5, 3, 3));
													pnlSouthPerA_C.add(pnlSouth_East_Active_PA,BorderLayout.EAST);
													pnlSouth_East_Active_PA.setPreferredSize(new Dimension(400, 0));

													{
														btnNew_Active_PA = new _JButton("New");
														pnlSouth_East_Active_PA.add(btnNew_Active_PA);
														btnNew_Active_PA.setActionCommand("New_PA");
														btnNew_Active_PA.addActionListener(this);
														grpButton_Pa.add(btnNew_Active_PA);
													}

													{
														btnEdit_Active_PA = new _JButton("Edit");
														pnlSouth_East_Active_PA.add(btnEdit_Active_PA);
														btnEdit_Active_PA.setActionCommand("Edit_PA");
														btnEdit_Active_PA.addActionListener(this);
														grpButton_Pa.add(btnEdit_Active_PA);
													}
													{
														btnSave_Active_PA = new _JButton("Save");
														pnlSouth_East_Active_PA.add(btnSave_Active_PA);
														btnSave_Active_PA.addActionListener(this);
													}
													{
														btnPost_Active_PA = new _JButton("Post");
														pnlSouth_East_Active_PA.add(btnPost_Active_PA);
														btnPost_Active_PA.addActionListener(this);
													}

													{
														btnClear_Active_PA = new _JButton("Clear");
														pnlSouth_East_Active_PA.add(btnClear_Active_PA);
														btnClear_Active_PA.setActionCommand("Clear_Active_PA");
														btnClear_Active_PA.addActionListener(this);

													}
												}

												JPanel _pnlSouth = new  JPanel(new BorderLayout(5, 5));
												pnlSouthPerA_C.add(_pnlSouth,BorderLayout.SOUTH);
											}
										}
									}
								}//per account
							}
						}
						{

							pnlCancelTagStatus = new  JPanel(new BorderLayout(5,5)); /// ; // XXX  Tag Accounts Status
							tabpane.addTab("Tag Accounts Status", pnlCancelTagStatus);

							{
								pnlCancelTagStatus_North = new JPanel();
								pnlCancelTagStatus.add(pnlCancelTagStatus_North, BorderLayout.NORTH);
								pnlCancelTagStatus_North.setPreferredSize(new Dimension(pnlMain.getWidth(),70));
								pnlCancelTagStatus_North.setLayout(new BorderLayout(5,5));
								//pnlCancelTagStatus_North.setBorder(components.JTBorderFactory.createTitleBorder("Process"));
								{


									{

										pnlCancelTagStatus_North_North = new JPanel(new BorderLayout(5, 5));
										pnlCancelTagStatus_North.add(pnlCancelTagStatus_North_North,BorderLayout.CENTER);
										pnlCancelTagStatus_North_North.setPreferredSize(new Dimension(pnlCancelActive.getWidth(), 60));

										JPanel pnlNorth = new JPanel(new BorderLayout(5, 5));
										pnlCancelTagStatus_North_North.add(pnlNorth,BorderLayout.NORTH);
										{
											JPanel pnlLabel = new JPanel(new GridLayout(2, 1, 3, 3));
											pnlCancelTagStatus_North_North.add(pnlLabel,BorderLayout.WEST);
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
											pnlCancelTagStatus_North_North.add(pnlProcess,BorderLayout.CENTER);
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
											pnlCancelTagStatus_North_North.add(pnlButton,BorderLayout.EAST);
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
											pnlCancelTagStatus_North_North.add(pnlSouth,BorderLayout.SOUTH);
										}
									}
									{
										pnlCancelTagStatus_Center = new JPanel(new BorderLayout(5,5));
										pnlCancelTagStatus.add(pnlCancelTagStatus_Center, BorderLayout.CENTER);
										//pnlCancelTagStatus_Center.setBorder(lineBorder);	
										pnlCancelTagStatus_Center.setBorder(components.JTBorderFactory.createTitleBorder("Status of Cancellation Accounts"));
										{
											dteStatus = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
											pnlCancelTagStatus_Center.add(dteStatus);
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
											pnlCancelTagStatus_Center.add(scrollCancelTag, BorderLayout.CENTER);
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
										pnlCancelTagStatus_South = new JPanel(new BorderLayout(5,5));
										pnlCancelTagStatus.add(pnlCancelTagStatus_South, BorderLayout.SOUTH);
										pnlCancelTagStatus_South.setPreferredSize(new Dimension(pnlMain.getWidth(),57));
										{
											JPanel pnlNorth1 = new JPanel(new BorderLayout(5,5));
											pnlCancelTagStatus_South.add(pnlNorth1,BorderLayout.NORTH);
										}
										{
											JPanel pnlCenter = new JPanel(new BorderLayout(5,5));
											pnlCancelTagStatus_South.add(pnlCenter,BorderLayout.CENTER);
											{
												lblEditable_Tag = new JLabel("<html><i>&nbsp;&nbsp; ( <font color = \"red\">*</font> ) Editable Columns ( Double Click on Selected cell to input Details )");
												pnlCenter.add(lblEditable_Tag);	
											}

										}
										{
											JPanel pnlSouth_East_Tag = new  JPanel(new GridLayout(1, 2, 5, 5));
											pnlCancelTagStatus_South.add(pnlSouth_East_Tag,BorderLayout.EAST);
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
											pnlCancelTagStatus_South.add(pnlSouth,BorderLayout.SOUTH);
										}
									}
								}
							}
						}
					}
				}//pnlCenter
			}
			tabpane.setSelectedIndex(0);
			tabpane_per_account.setSelectedIndex(0);
		} catch (Exception e) {
		}

	}

	@Override
	public void lookupPerformed(LookupEvent e) { // XXX lookupPerformed
		
		if( e.getSource().equals(lookupCompany)){
			Object[] data = ((_JLookup)e.getSource()).getDataSet();

			if(data != null){

				co_id = data[0].toString();
				company = data[1].toString();
				company_logo = data[3] == null ? "" : data[3].toString() ;
				txtCompany.setText(company);

				lookupBatchNo.setEnabled(true);
				lblBatchNo.setEnabled(true);
				lookupProjID.setLookupSQL(functions.lookupProjects(co_id));
				lookupProj.setLookupSQL(functions.lookupProjects(co_id));
				
				cmbBuyerType.setEnabled(true);
				lblBuyerType.setEnabled(true);
				lookupBatchNo.setLookupSQL(functions.listBatch());

				if (tabpane_per_account.getSelectedIndex() == 0 ) {
					btnState(true, true, false, false, false);	
				}
				if (tabpane_per_account.getSelectedIndex() == 1 ) {
					btnState_PA(true, true, false, false, false);
					lookupProjID_Tag.setLookupSQL(functions.lookupProjects(co_id));
				}

			}
		}

		if( e.getSource().equals(lookupProjID)){
			Object[] data = ((_JLookup)e.getSource()).getDataSet();

			if(data != null){
				proj_id = data[0].toString();
				proj_name = data[1].toString();

				txtProjectName.setText(proj_name);

			}
		}

		if( e.getSource().equals(lookupApproveby_Active)){
			Object[] data = ((_JLookup)e.getSource()).getDataSet();

			if(data != null){
				approved_id = data[0].toString();
				aaproved_name = data[1].toString();

				txtApprovedBy_Active.setText(aaproved_name);

			}
		}

		if( e.getSource().equals(lookupApproveby_Active_PA)){
			Object[] data = ((_JLookup)e.getSource()).getDataSet();

			if(data != null){
				approved_id = data[0].toString();
				aaproved_name = data[1].toString();

				txtApprovedBy_Active_PA.setText(aaproved_name);

			}
		}

		//displayEditPerBatchRecommended

		if (e.getSource().equals(lookupBatchNo)) {

			Object[] data = ((_JLookup)e.getSource()).getDataSet();

			if(data != null){
				batch_no = data[0].toString();

				new Thread(new Generate_Edit()).start();
				btnState(true, true, false, true, false);
				cmbBuyerType.setEnabled(true);
				lblBuyerType.setEnabled(true);
				lblForTestingDate.setEnabled(true);
				dteDueDate.setEnabled(true);
				btnPreviewActive.setEnabled(true);
				dteDueDate.setDate(dateFormat("2018-05-14"));
				
				System.out.println("Due Date" +dteDueDate.getDate() );
				tabpane_per_account.setSelectedIndex(0);
			}
		}

		if( e.getSource().equals(lookupProj)){ 
			Object[] data = ((_JLookup)e.getSource()).getDataSet();

			if(data != null){
				proj_id = data[0].toString();
				proj_name = data[1].toString();

				txtProj.setText(proj_name);

				if (cmbBuyerType.getSelectedIndex() == 0 ) {
					lookupClientName.setLookupSQL(functions.getAllClient(proj_id));
				}

				if (cmbBuyerType.getSelectedIndex() == 1 ) { 
					lookupClientName.setLookupSQL(functions.getIHFClient(proj_id));	
				}

				if (cmbBuyerType.getSelectedIndex() == 2 ) {
					lookupClientName.setLookupSQL(functions.getHDMFClient(proj_id));
				}

				this.setComponentsEnabled(pnlCenterPerA, true);
				this.setComponentsEnabled(pnlSouth_Center_Active_PA, true);

			}
		}

		if (e.getSource().equals(lookupClientName)) {


			pgSelect dbS = new pgSelect();

			if (cmbBuyerType.getSelectedIndex() == 0 ) {
				table_name ="All";
			}

			if (cmbBuyerType.getSelectedIndex() == 1 ) {
				table_name ="ihf_due_accounts";
			}

			if (cmbBuyerType.getSelectedIndex() == 2 ) {
				table_name ="pagibig_due_accounts";
			}

			Object[] data = ((_JLookup)e.getSource()).getDataSet();
			if(data != null){

				entity_id 		= data[0].toString();
				client_name 	= data[1].toString();
				pbl_id 			= data[8].toString();
				pbldesc	 		= data[3].toString();
				seq_no 			= (Integer)data[9]; 

				txtClientName.setText(client_name);
						
				txtPBLID.setText(pbl_id);
				txtPBLDesc.setText(pbldesc);

				dbS.select(functions.displayPastDue(entity_id, proj_id, pbl_id, seq_no, table_name));

				if (dbS.isNotNull()) {  

					//_dayspd 	 	= Integer.valueOf((String) (dbS.Result[0][0].toString() == null ? 0 : dbS.Result[0][0].toString()));
					months_pd 		= Integer.valueOf(dbS.Result[0][1].toString());
					_amountdue 		= (BigDecimal)dbS.Result[0][2];
					_lastpaid_date 	= (Date) dbS.Result[0][3] == null ? null : (Date) dbS.Result[0][3];
					_default_date   = (Date) dbS.Result[0][4] == null ? null : (Date) dbS.Result[0][4];   
					part_id         = dbS.Result[0][8] == null ? null : dbS.Result[0][8].toString();

					if (months_pd < 2) {
						lblNote1.setVisible(true);
						//	lblNote.setVisible(true);// not recommended
					}

					if (months_pd >= 2){ 
						lblNote1.setVisible(false); // recommended
						//	lblNote.setVisible(false);
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

		if (e.getSource().equals(lookupProjID_Tag)) {

			if (tabpane.getSelectedIndex()==1) {
				Object[] data = ((_JLookup)e.getSource()).getDataSet();
				if(data != null){ 
					_proj_id = data[0].toString();
					_proj_name = data[1].toString();
					txtProjectName_Tag.setText(_proj_name);
					btnGenerate_Tag.setEnabled(true); 
				}
			}
		}

		if (e.getSource().equals(lookupClientID)) {

			Object[] data = ((_JLookup)e.getSource()).getDataSet();
			if(data != null){
				entity_id = data[0].toString();
				client_name = data[1].toString();
				unit_id = data[4].toString();

				db.select("SELECT"
						+ " proj_id, \n" + 
						"get_project_name(proj_id),\n" + 
						"pbl_id, \n" + 
						"get_unit_description(a.proj_id,a.pbl_id), \n" + 
						"_get_particular_alias(a.part_id),\n" + 
						"months_past_due,\n" + 
						"default_date,\n" + 
						"cancel_type,\n" + 
						"amount_due,\n" + 
						"reason,\n" +
						"(SELECT remdesc FROM mf_remarks WHERE remark_id = a.reason),\n" + 
						"remarks,\n" + 
						"approved_by,\n" + 
						"get_client_name_emp_id(approved_by),\n" +
						"seq_no \n" +
						"FROM rf_cancellation a WHERE entity_id = '"+entity_id+"' AND unit_id = '"+unit_id+"' and status_id = 'A'\n" + 
						"\n" + 
						"");


				if (db.isNotNull()) {

					proj_id = db.Result[0][0].toString();
					proj_name = db.Result[0][1].toString();
					pbl_id = db.Result[0][2].toString();
					pbl_desc = db.Result[0][3].toString();
					stage = db.Result[0][4].toString();
					months_pd = (Integer) db.Result[0][5];
					_default_date   = (Date) db.Result[0][6] == null ? null : (Date) db.Result[0][6];   
					cancel_type = db.Result[0][7].toString();
					_amountdue 		= (BigDecimal)db.Result[0][8];
					_reason_id =  db.Result[0][9].toString();
					_reason =  db.Result[0][10].toString();
					remarks =  db.Result[0][11].toString();
					approved_id =  db.Result[0][12].toString();
					approved_name =  db.Result[0][13].toString();
					seq_no =  (Integer) db.Result[0][14];

					lookupProj.setValue(proj_id);
					txtProj.setText(proj_name);
					lookupClientName.setValue(entity_id); 
					txtClientName.setText(client_name);
					txtPBLID.setText(pbl_id);
					txtPBLDesc.setText(pbl_desc);
					txtStage.setText(stage);
					txtMonthPD.setText(String.valueOf(months_pd));
					txtDefaultDate.setText(functions.dateFormat((Date) db.Result[0][6])  == null ? null : functions.dateFormat((Date) db.Result[0][6]));

					if (cancel_type.equals("C")) {
						cmbCancelType_Per.setSelectedIndex(1);

					}else{
						cmbCancelType_Per.setSelectedIndex(2);
					}

					txtAmountDue.setValue(_amountdue);
					lookupReason.setValue(_reason_id);
					txtReason_.setText(_reason);
					txtareaRemarks.setText(remarks);
					lookupApproveby_Active_PA.setValue(approved_id);
					txtApprovedBy_Active_PA.setText(approved_name);

					btnState_PA(true, true, false, true, true);
				}

			}
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void actionPerformed(ActionEvent e) { // XXX actionPerformed


		if (e.getSource().equals(btnNew_Active)) {
			JButton button = (JButton) e.getSource();
			grpButton.setSelected(button.getModel(), true);

			modelPerBatchModel.clear();
			rowHeadePerBatch.setModel(new DefaultListModel());
			scrollPerBatch.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPerBatch.getRowCount())));

			lookupApproveby_Active.setValue("");
			txtApprovedBy_Active.setText("");

			lookupProjID.setValue("");
			txtProjectName.setText("");

			lookupBatchNo.setValue(functions.getBatchNo());
			this.setComponentsEnabled(pnlCancelActive, true);
			this.setComponentsEnabled(pnlPerBatch, true);
			this.setComponentsEnabled(pnlCenter_Approved_C_Active, true);
			btnState(false, false, true, false, true);		

		}
		
		if (e.getSource().equals(btnNew_Active_PA)) {
			JButton button = (JButton) e.getSource();
			grpButton_Pa.setSelected(button.getModel(), true);

			lookupBatchNo.setValue(functions.getBatchNo());	
			this.setComponentsEnabled(pnlNorthPerA, true);
			btnState_PA(false, false, true, false, true);		

		}

		if (e.getSource().equals(cmbBuyerType)) {

			if (cmbBuyerType.getSelectedIndex() == 0 ) {
				_Clear();
			}
			if(cmbBuyerType.getSelectedIndex() == 1 ){
				_Clear();
			}
			if(cmbBuyerType.getSelectedIndex() == 2 ){
				_Clear(); 
			}
		}

		if (e.getSource().equals(btnGenerateActive)) {

			if (cmbBuyerType.getSelectedIndex() == 0 ) {
				table_name ="All";
			}
			if(cmbBuyerType.getSelectedIndex() == 1 ){
				table_name ="ihf_due_accounts";
			}
			if(cmbBuyerType.getSelectedIndex() == 2 ){
				table_name ="pagibig_due_accounts";
			}

			if (txtProjectName.getText().isEmpty() || txtProjectName.getText().equals("Project Name")){
				JOptionPane.showMessageDialog( getContentPane(), "Please Choose a Project Name first  ", "Incomplete", JOptionPane.OK_OPTION );
				return;
			}

			if (tabpane_per_account.getSelectedIndex() == 0) {

				new Thread(new Generate()).start();
			}
			dteDueDate.setDate(dateFormat("2018-05-14"));
			
			System.out.println("Due Date" +dteDueDate.getDate() );
		}

		if (e.getSource().equals(btnPreviewActive)) {
			new Thread(new ForPreviewProjCancel()).start();
		}

		if (e.getSource().equals(btnGenerate_Tag)) {

			new Thread(new Generate()).start();
		}

		if (e.getSource().equals(btnClear_Active)){
			JButton button = (JButton) e.getSource();
			grpButton.setSelected(button.getModel(), true);
			_Clear();
			cmbProcessFor.setEnabled(true);
			lookupProjID.setEditable(true);
			lookupApproveby_Active.setEditable(true);
			_setEnabled(false);
			lookupBatchNo.setEnabled(true);
			lblBatchNo.setEnabled(true);
			btnState(true, true, false, false, false);


		}

		if (e.getSource().equals(btnSave_Active)) {
			new Thread(new Saved_Cancellation_Active()).start();
			//Save_Cancellation();

		}

		if (e.getSource().equals(btnEdit_Active)) {
			JButton button = (JButton) e.getSource();
			grpButton.setSelected(button.getModel(), true);


			if (lookupBatchNo.getValue().equals("") || lookupBatchNo.getValue().equals("Search Batch No")) {
				JOptionPane.showMessageDialog(getContentPane(), "Please select batch no first to edit ", "Incomplete", JOptionPane.OK_OPTION);
				return;
			}
			_setEnabled(true);
			btnGenerateActive.setEnabled(false);
			cmbProcessFor.setEnabled(false);
			lookupProjID.setEditable(false);
			lookupApproveby_Active.setEditable(false);
			btnState(false, false, true, false, true);


		}

		if (e.getSource().equals(btnPost_Active)) {
			new Thread(new Posting_Cancellation_Active()).start();
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
					FormLoad();
					_Clear();

				}
			}
		}

		if (e.getSource().equals(btnSave_Active_PA)) {
			new Thread(new ForSave_PerAccount()).start();

		}

		if (e.getSource().equals(btnPost_Active_PA)) {
			new Thread(new ForPosting_PerAccount()).start();

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

		if (e.getActionCommand().equals("Edit_PA")) {
			JButton button = (JButton) e.getSource();
			grpButton_Pa.setSelected(button.getModel(), true);

			this.setComponentsEnabled(pnlPerAccount, true);
			this.setComponentsEnabled(pnlCenter_Approved_C_Active_PA, true);
			btnState_PA(false, false, true, false, true);

		}
		if (e.getSource().equals(btnClear_Active_PA)) {
			_Clear();
			FormLoad();
		}

		if (e.getSource().equals(btnClear_Tag)) {

			int response = JOptionPane.showConfirmDialog(this,"Are you sure you want to Clear all fields?   ","Confirm",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (response == JOptionPane.YES_OPTION) {
				_Clear();
			}
		}
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		/*if (e.getSource().equals(tabpane)) {
			if (tabpane.getSelectedIndex() == 0) {
				if (tabpane_per_account.getSelectedIndex() ==0 ) {
					pnlSouthPerA.setVisible(true);
				}

			}else{
				if (tabpane_per_account.getSelectedIndex() ==0 ) {
					pnlSouthPerA.setVisible(true);
				}
			}
		}
		 */

		if (e.getSource().equals(tabpane_per_account)) {
			if (!lookupCompany.getValue().equals("ID")) {

				if (tabpane_per_account.getSelectedIndex() == 1) {
					System.out.println("ENABLED : DDD " + btnNew_Active_PA.isEnabled());
					if (!(btnNew_Active_PA.isEnabled()) && !lookupProj.isEnabled()) {
						btnState_PA(true, true, false, false, false);
					}
					/*	lookupBatchNo.setVisible(false);
					lblBatchNo.setText("Client ID :");
					lookupClientID = new _JLookup("Search Client", "Client", 0) ; 
					pnlBatch.add(lookupClientID,BorderLayout.CENTER);
					lookupClientID.addLookupListener(this);
					lookupClientID.setReturnColumn(0);
					lookupClientID.setLookupSQL(functions.getClientPerAccount());
					lookupClientID.setPreferredSize(new Dimension(60, 20));*/

				}else{

					//lookupClientID.setVisible(false);
					lookupBatchNo.setVisible(true);
					lblBatchNo.setText("Batch No.");
				}

			}

			/*
			if (tabpane_per_account.getSelectedIndex() == 0) {
				if (grpButton.getSelection().getActionCommand().equals("New") || grpButton.getSelection().getActionCommand().equals("Edit")) {
					btnState(false, false, true, false, true);
				}

				if (grpButton.getSelection().getActionCommand().equals("Clear_Active")) {
					btnState(true, true, false, false, false);
				}
			}

			 */
		}	

	}

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
				if (tblPerBatch.getSelectedColumn()==21||tblPerBatch.getSelectedColumn()==22 || tblPerBatch.getSelectedColumn()== 23 ) {
					modelPerBatchModel.setEditable(( modelPerBatchModel.getValueAt(tblPerBatch.getSelectedRow(),0).equals(true)) );
				} // col 17,18
			} // getKeyCode

		}
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void mouseDragged(MouseEvent e) {}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (e.getSource().equals(tblPerBatch)) {
			if (tblPerBatch.columnAtPoint(e.getPoint())== 21 || 
					tblPerBatch.columnAtPoint(e.getPoint())== 22 || 		// Reason
					tblPerBatch.columnAtPoint(e.getPoint())== 23 || 	// Remarks
					tblPerBatch.columnAtPoint(e.getPoint())== 24 )	// CSV
			{ // CSV
				if (tblPerBatch.getValueAt(tblPerBatch.rowAtPoint(e.getPoint()),0).equals(true))
					tblPerBatch.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				else tblPerBatch.setCursor(null);

			}else tblPerBatch.setCursor(null);
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
	}

	@Override
	public void mouseClicked(MouseEvent e) {


		if (e.getSource().equals(tblPerBatch)) {

			if (modelPerBatchModel.getValueAt(tblPerBatch.getSelectedRow(), 0).equals(true)) { 
				if (tblPerBatch.getSelectedColumn()== 22) {

					String SQL = "select remark_id, remdesc from mf_remarks where remark_id in ('00232','00227','00034','00035','00036','00037','00039') and remtype = '02' order by remdesc";

					_JLookupTable dlg = new _JLookupTable(FncGlobal.homeMDI, null, SQL, false);

					dlg.setLocationRelativeTo(FncGlobal.homeMDI);
					dlg.setVisible(true); 

					Object[] data = dlg.getReturnDataSet();

					if(data != null){

						modelPerBatchModel.setValueAt(data[1], tblPerBatch.getSelectedRow(),30);
						_reason_id = data[0].toString();
						tblPerBatch.packAll();
						//tblPerBatch.packColumn(22, 400, 400);

						System.out.println("Status_id :" + modelPerBatchModel.getValueAt( tblPerBatch.getSelectedRow(), 22));
					}

				} // 

				if (tblPerBatch.getSelectedColumn()== 21) {
					TableColumn monthcolumn = tblPerBatch.getColumnModel().getColumn(21);
					monthcolumn.setCellEditor(new DefaultCellEditor(cmbCancelType));
				}

				/** Repaint for Highlight **/
				if (tblPerBatch.getSelectedColumn() == 0) {
					tblPerBatch.repaint();
					tblPerBatch.packAll();
					//tblPerBatch.packColumn(23, 400, 400); 
				}

				/** Listener for boolean canEditStatus **/
				if (tblPerBatch.getSelectedColumn()== 23) {

					System.out.println("Enable :" + modelPerBatchModel.getValueAt(tblPerBatch.getSelectedRow(),0).equals(true));
					if( modelPerBatchModel.getValueAt(tblPerBatch.getSelectedRow(),0).equals(true)){
						modelPerBatchModel.setEditable(( modelPerBatchModel.getValueAt(tblPerBatch.getSelectedRow(),0).equals(true)) );	
					}

				} //
			}
		}

		if (e.getSource().equals(tblCancelTag)) {
			if (modelCancelTag.getValueAt(tblCancelTag.getSelectedRow(), 0).equals(true)) { 


				if (tblCancelTag.getSelectedColumn()== 7) {
					if (e.getClickCount() ==2) {
						dteStatus.setBounds((int)pnlCancelTagStatus_Center.getMousePosition().getX(), (int)pnlCancelTagStatus_Center.getMousePosition().getY(), 0, 0);
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

	@Override
	public void mouseEntered(MouseEvent e){}

	@Override
	public void mouseExited(MouseEvent e){}

	@Override
	public void mousePressed(MouseEvent e){}

	@Override
	public void mouseReleased(MouseEvent e){}

	public void _setEnabled(Boolean ena){

		this.setComponentsEnabled(pnlCancelActive, ena);


		if (tabpane.getSelectedIndex() == 0) {

			this.setComponentsEnabled(pnlMainBatch, ena);		
			this.setComponentsEnabled(pnlSouth_Center_Active, ena);
			this.setComponentsEnabled(pnlMainPerA, ena);


			cmbProcessFor.setEnabled(true);
			lookupProjID.setEditable(true);
			lookupApproveby_Active.setEditable(true);

		}else{
			this.setComponentsEnabled(pnlCancelTagStatus_North_North, ena);
			this.setComponentsEnabled(pnlCancelTagStatus_Center, ena);
			this.setComponentsEnabled(pnlCancelTagStatus_South, ena);	
		}

	}

	private void btnState(Boolean sNew,Boolean sEdit ,Boolean sSave,Boolean sPost,Boolean sClear ){
		btnNew_Active.setEnabled(sNew);
		btnEdit_Active.setEnabled(sEdit);
		btnSave_Active.setEnabled(sSave);
		btnPost_Active.setEnabled(sPost);
		btnClear_Active.setEnabled(sClear);

	}

	private void btnState_PA(Boolean sNew,Boolean sEdit ,Boolean sSave,Boolean sPost,Boolean sClear ){
		btnNew_Active_PA.setEnabled(sNew);
		btnEdit_Active_PA.setEnabled(sEdit);
		btnSave_Active_PA.setEnabled(sSave);
		btnPost_Active_PA.setEnabled(sPost);
		btnClear_Active_PA.setEnabled(sClear);

	}

	public class Generate implements Runnable{
		private int recomend_or_csv;



		@SuppressWarnings({ "unchecked", "rawtypes" })
		public void run() {

			if (tabpane.getSelectedIndex()== 0 || tblPerBatch.isShowing()) {
				if (cmbProcessFor.getSelectedIndex() == 0 ) {
					recomend_or_csv = 0;
				}else{
					recomend_or_csv = 1;
				}


				if (cmbBuyerType.getSelectedIndex() == 0) {
					lblListof = "Recommended for Cancellation for All Accounts";

				}
				if (cmbBuyerType.getSelectedIndex() == 1) {
					lblListof = "Recommended for Cancellation for IHF Accounts";

				}	
				if (cmbBuyerType.getSelectedIndex() == 2) {
					lblListof = "Recommended for Cancellation for Pag-ibig Accounts";

				}

				rowHeadePerBatch.setModel(new DefaultListModel());
				functions.displayPerBatchRecommended(modelPerBatchModel, rowHeadePerBatch, table_name, proj_id,recomend_or_csv);
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

			if (tabpane.getSelectedIndex()== 1 || tblCancelTag.isShowing()) {

				rowHeaderCancelTag.setModel(new DefaultListModel());

				_FCancellationProcessing.displayCancellationTag(modelCancelTag, rowHeaderCancelTag, _proj_id,cmbProcessFor_Tag.getSelectedIndex());
				scrollCancelTag.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblCancelTag.getRowCount())));

				tblCancelTag.packAll();
				tblCancelTag.packColumn(8, 400, 400);

			}
		}
	}

	public class ForPreviewProjCancel implements Runnable{

		private String preparedby = "";
		private String approvedby = "";
		ArrayList<String> iftrue = new ArrayList<String>();
		private String entity_id;
		private String _proj_id;
		private String pbl_id;
		private String seq_no;
		@Override
		public void run() {
			Map<String, Object> mapParameters = new HashMap<String, Object>();
			mapParameters.put("company", company);
			mapParameters.put("co_id", co_id);
			mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
			mapParameters.put("lblListof", lblListof);


			/*
			if (grpButton.getSelection().getActionCommand().equals("New")) {

			}else{
				mapParameters.put("batchno", lookupBatchNo.getValue().toString());
			}*/


			//mapParameters.put("lblBuyerStatus", cmbAccountStatus.getSelectedItem() == null ? ""  : cmbAccountStatus.getSelectedItem().toString());
			pgSelect db = new pgSelect();

			String SQL ="";
			for (int i = 0; i < modelPerBatchModel.getRowCount(); i++) {
				Boolean SelectedItem = (Boolean) modelPerBatchModel.getValueAt(i, 0);



				if (SelectedItem) {
					entity_id = modelPerBatchModel.getValueAt(i, 21).toString();
					_proj_id = modelPerBatchModel.getValueAt(i, 22).toString();
					pbl_id = modelPerBatchModel.getValueAt(i, 23).toString();
					seq_no = modelPerBatchModel.getValueAt(i, 24).toString();
					iftrue.add(modelPerBatchModel.getValueAt(i, 25).toString());


					db.select("SELECT batch_no  FROM rf_cancellation WHERE entity_id = '"+entity_id+"' AND proj_id = '"+_proj_id+"' AND pbl_id = '"+pbl_id+"' AND seq_no = '"+seq_no+"' AND status_id = 'A'");

					if (db.isNotNull()) {

						mapParameters.put("batchno", db.Result[0][0]  == null ? null :  db.Result[0][0].toString());
					}


					SQL = (!SQL.isEmpty() ? SQL + "UNION\n" : "") +

							"SELECT '"+modelPerBatchModel.getValueAt(i, 1)+"' AS unit_pbl,\n" + 
							"'"+modelPerBatchModel.getValueAt(i, 2)+"' AS client_name,\n" + 
							"'"+modelPerBatchModel.getValueAt(i, 3)+"' AS buyertype,\n" + 
							"NULLIF( '"+modelPerBatchModel.getValueAt(i, 5)+"','null') AS sale_div,\n" + 
							"_get_sales_agent('"+entity_id+"','"+_proj_id+"','"+pbl_id+"') AS sale_agent,\n" +
							"'"+modelPerBatchModel.getValueAt(i, 7)+"' AS house_model,\n" + 
							"get_payment_stage('"+entity_id+"','"+_proj_id+"','"+pbl_id+"','"+seq_no+"') AS stage,\n" + 
							"'"+modelPerBatchModel.getValueAt(i, 27)+"' AS phase,\n" + 
							"_get_tr_date('"+entity_id+"','"+_proj_id+"','"+pbl_id+"','"+seq_no+"') AS tr_date,\n" + 
							"_get_or_date('"+entity_id+"','"+_proj_id+"','"+pbl_id+"','"+seq_no+"') AS or_date,\n" + 
							"NULLIF('"+modelPerBatchModel.getValueAt(i, 14)+"','null') AS default_date,\n" + 
							"get_nsp('"+entity_id+"','"+pbl_id+"','"+seq_no+"','"+_proj_id+"')::numeric AS nsp,\n" + 
							"'"+modelPerBatchModel.getValueAt(i, 19)+"' AS withntc,\n" +
							"'"+modelPerBatchModel.getValueAt(i, 22)+"' AS proj_id,\n" + 
							"NULLIF('"+modelPerBatchModel.getValueAt(i, 28)+"','null') AS proj_name,\n"+
							"(case when _get_or_date('"+entity_id+"','"+_proj_id+"','"+pbl_id+"','"+seq_no+"') is null then 'TR' ELSE 'OR' END) AS class \n";

				}
			}

			if (iftrue.isEmpty()) {
				JOptionPane.showMessageDialog(getContentPane(),"Please select first for preview ","Preview", JOptionPane.OK_OPTION);
				return;
			}

			db.select(SQL);

			if (db.isNotNull()) {
				int count_tr = 0;
				int count_or = 0;
				int total_or_tr = 0;
				BigDecimal total_tr = new BigDecimal("0.00");
				BigDecimal total_or = new BigDecimal("0.00");
				BigDecimal subtotal_or_tr = new BigDecimal("0.00");

				for (int j = 0; j < db.getRowCount(); j++) {
					if (db.Result[j][15].equals("TR")) {
						BigDecimal totalTR = (BigDecimal) db.Result[j][11];

						try {
							total_tr = total_tr.add(totalTR);

						} catch (Exception e1) { }
						count_tr++;
					}
					if (db.Result[j][15].equals("OR")) {

						BigDecimal totalOR = (BigDecimal) db.Result[j][11];

						try {
							total_or = total_or.add(totalOR);

						} catch (Exception e1) { }
						count_or++;
					}	
				}


				total_or_tr = count_or + count_tr;
				subtotal_or_tr = subtotal_or_tr.add(total_tr) ;
				subtotal_or_tr = subtotal_or_tr.add(total_or) ;

				mapParameters.put("total_sum_tr",total_tr);
				mapParameters.put("count_tr",count_tr);
				mapParameters.put("total_sum_or",total_or);
				mapParameters.put("count_or",count_or);
				mapParameters.put("subtotal_or_tr",subtotal_or_tr);
				mapParameters.put("total_or_tr",total_or_tr);

			//  Modified by : Jervin Vilog /change of level no. due to request of sir garret 
//				db.select("select (select  level_no from mf_rank_level where level_code = emp_rank), _get_client_name(entity_id) "
//						+ "from em_employee where emp_code = '"+UserInfo.EmployeeCode+"' "
//						+ "and (select level_no from mf_rank_level where level_code = emp_rank) > 13");
				db.select("select (select  level_no from mf_rank_level where level_code = emp_rank), _get_client_name(entity_id) "
						+ "from em_employee where emp_code = '"+UserInfo.EmployeeCode+"' "
						+ "and (select level_no from mf_rank_level where level_code = emp_rank) < 5");

				if (db.isNotNull()) {

					preparedby = db.Result[0][1].toString();

				}
			//  Modified by : Jervin Vilog /change of level no. due to request of sir garret 
//				db.select("select (select  level_no from mf_rank_level where level_code = emp_rank), _get_client_name(entity_id) "
//						+ "from em_employee where emp_code = '"+UserInfo.EmployeeCode+"' "
//						+ "and (select  level_no from mf_rank_level where level_code = emp_rank) <= 13");
				db.select("select (select  level_no from mf_rank_level where level_code = emp_rank), _get_client_name(entity_id) "
						+ "from em_employee where emp_code = '"+UserInfo.EmployeeCode+"' "
						+ "and (select  level_no from mf_rank_level where level_code = emp_rank) >= 5");
				

				if (db.isNotNull()) {
					approvedby = db.Result[0][1].toString();
				}

				mapParameters.put("approvedby",approvedby);
				mapParameters.put("preparedby",preparedby);

				System.out.println("SQL: " + SQL) ;
				FncReport.generateReport("/Reports/rptCancellation_Processing.jasper", "List of Recommendation Accounts",  mapParameters, SQL);

			}


		}
	}

	public class Saved_Cancellation_Active implements Runnable{ // XXX Saved_Cancellation_Active

		private Boolean SelectedItem;
		private String _textreason;

		@Override
		public void run() {

			FncGlobal.startProgress("Saving  . . .Please wait ");

			int isEmpty = 0;
			if (tabpane_per_account.getSelectedIndex() == 0) {
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


						int response = JOptionPane.showConfirmDialog(getContentPane(),"Are you sure you want to save selected account(s)  ","Confirm",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
						if (response == JOptionPane.YES_OPTION) {

							if (grpButton.getSelection().getActionCommand().equals("New")) {
								Save_Cancellation();
							}
							if (grpButton.getSelection().getActionCommand().equals("Edit")) {
								Save_Cancellation_Edit();
							}

							//new Thread(new ForNotices()).start();
							new Thread(new ForPreviewProjCancel()).start();
							_setEnabled(false);
							_Clear();
							lblBatchNo.setEnabled(true);
							lookupBatchNo.setEnabled(true);

						}
					}

				}//recommended

				/*	if (cmbProcessFor.getSelectedIndex() == 1) {

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

				}*/
			}
			FncGlobal.stopProgress();
		}
	}

	private String entity_id;
	private String _proj_id;
	private String pbl_id;
	private Integer seq_no;
	private String _reason;
	private String cancel_type = "";
	private String remarks;
	private String batch_no;

	private String cancel_type_desc;
	private String requested_by;
	private String part_stage;
	private String stage;
	private Integer months_pd;
	private String _user_id;
	private String cancelled_by;
	private Date _due_date;
	private Integer months_paid = 0;
	private boolean _macedalaw;
	private String _batch_no;
	private String _transdate;
	private String _remarks;
	private String _cancel_csv_status_id;
	private boolean _p_table;
	private Integer _cancel_csv_id;

	private void Save_Cancellation(){

		for (int i = 0; i < modelPerBatchModel.getRowCount(); i++) {

			Boolean SelectedItem = (Boolean) modelPerBatchModel.getValueAt(i, 0);

			batch_no = lookupBatchNo.getValue().toString();
			entity_id = modelPerBatchModel.getValueAt(i, 21).toString();
			_proj_id = modelPerBatchModel.getValueAt(i, 22).toString();
			pbl_id = modelPerBatchModel.getValueAt(i, 23).toString();
			seq_no = (Integer) modelPerBatchModel.getValueAt(i, 24);
			_reason = modelPerBatchModel.getValueAt(i, 30).toString();
			remarks = modelPerBatchModel.getValueAt(i, 31).toString();
			approved_id = lookupApproveby_Active.getValue().toString();
			cancel_type_desc = modelPerBatchModel.getValueAt(i, 29).toString();
			requested_by = UserInfo.EmployeeCode;
			part_stage = modelPerBatchModel.getValueAt(i, 13).toString();

			if (cancel_type_desc.equals("Company Initiated")) {
				cancel_type = "C";
			}else{
				cancel_type = "B";
			}

			if (SelectedItem) {
				System.out.println("SAMPLE  " +  entity_id +""+ pbl_id);

				System.out.println("cancel_type  " +  cancel_type);

				db.select("SELECT remark_id FROM mf_remarks WHERE remdesc = TRIM('"+_reason+"') AND remtype = '02'");
				if(db.isNotNull()){
					_reason_id = db.Result[0][0].toString();
				}

				System.out.println("_reasonn  " +  _reason_id);
				db.select("SELECT sp_save_recom_cancellation('"+entity_id+"' ,'"+_proj_id+"','"+pbl_id+"', '"+seq_no+"','"+batch_no+"','"+_reason_id+"','"+remarks+"','"+approved_id+"','"+requested_by+"','"+part_stage+"','"+cancel_type+"' )");
			}
		}
	}

	private void Save_Cancellation_Edit(){

		for (int i = 0; i < modelPerBatchModel.getRowCount(); i++) {

			Boolean SelectedItem = (Boolean) modelPerBatchModel.getValueAt(i, 0);

			batch_no = lookupBatchNo.getValue().toString();
			entity_id = modelPerBatchModel.getValueAt(i, 21).toString();
			_proj_id = modelPerBatchModel.getValueAt(i, 22).toString();
			pbl_id = modelPerBatchModel.getValueAt(i, 23).toString();
			seq_no = (Integer) modelPerBatchModel.getValueAt(i, 24);
			_reason = modelPerBatchModel.getValueAt(i, 30).toString();
			remarks = modelPerBatchModel.getValueAt(i, 31).toString();
			approved_id = lookupApproveby_Active.getValue().toString();
			cancel_type_desc = modelPerBatchModel.getValueAt(i, 29).toString();
			requested_by = UserInfo.EmployeeCode;
			part_stage = modelPerBatchModel.getValueAt(i, 13).toString();

			if (cancel_type_desc.equals("Company Initiated")) {
				cancel_type = "C";
			}else{
				cancel_type = "B";
			}

			if (SelectedItem) {
				System.out.println("SAMPLE  " +  entity_id +""+ pbl_id);

				System.out.println("cancel_type  " +  cancel_type);

				db.select("SELECT remark_id FROM mf_remarks WHERE remdesc = TRIM('"+_reason+"') AND remtype = '02'");
				if(db.isNotNull()){
					_reason_id = db.Result[0][0].toString();
				}

				System.out.println("_reasonn  " +  _reason_id);


				db.select("SELECT sp_save_recom_cancellation_edit('"+entity_id+"' ,'"+_proj_id+"','"+pbl_id+"', '"+seq_no+"','"+batch_no+"','"+_reason_id+"','"+remarks+"','"+approved_id+"','"+requested_by+"','"+part_stage+"','"+cancel_type+"' )");


			}
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void _Clear(){


		if (tabpane.getSelectedIndex() == 0 ) {
			if (tabpane_per_account.getSelectedIndex()== 0) {
				/*if (grpButton.getSelection().getActionCommand().equals("Clear_Active")) {
					lookupBatchNo.setValue("");
					cmbBuyerType.setSelectedIndex(0);
					cmbProcessFor.setSelectedIndex(0);

					modelPerBatchModel.clear();
					rowHeadePerBatch.setModel(new DefaultListModel());
					scrollPerBatch.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPerBatch.getRowCount())));
					dteDueDate.setDate(null);
				}*/

				modelPerBatchModel.clear();
				rowHeadePerBatch.setModel(new DefaultListModel());
				scrollPerBatch.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPerBatch.getRowCount())));

				lookupApproveby_Active.setValue("");
				txtApprovedBy_Active.setText("");
				lookupBatchNo.setValue("");
				lookupProjID.setValue("");
				txtProjectName.setText("");

				//dteDueDate.setDate(null);
			}else{
				lookupBatchNo.setValue("");
				txtProj.setText("");
				lookupProj.setValue("");
				lookupClientName.setValue("");
				txtClientName.setText("");
				txtPBLID.setText("");
				txtPBLDesc.setText("");
				txtStage.setText("");
				txtMonthPD.setText("");
				txtDefaultDate.setText("");
				cmbCancelType_Per.setSelectedIndex(0);
				txtAmountDue.setValue(0.00);
				txtReason_.setText("");
				txtareaRemarks.setText("");
				lookupApproveby_Active_PA.setValue("");
				txtApprovedBy_Active_PA.setText("");


			}

			txtApprovedBy_Active.setText("");
			lookupApproveby_Active.setValue("");
		}else{

			lookupProjID_Tag.setValue("");
			txtProjectName_Tag.setText("");
			FncTables.clearTable(modelCancelTag);
			rowHeaderCancelTag.setModel(new DefaultListModel());
			scrollCancelTag.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblCancelTag.getRowCount())));
		}

	}

	public class Generate_Edit implements Runnable{
		@SuppressWarnings({ "unchecked", "rawtypes" })
		@Override
		public void run() {

			batch_no = lookupBatchNo.getValue().toString();

			if (tabpane.getSelectedIndex()== 0 || tblPerBatch.isShowing()) {


				if (cmbBuyerType.getSelectedIndex() == 0) {
					lblListof = "Recommended for Cancellation for All Accounts";

				}
				if (cmbBuyerType.getSelectedIndex() == 1) {
					lblListof = "Recommended for Cancellation for IHF Accounts";

				}	
				if (cmbBuyerType.getSelectedIndex() == 2) {
					lblListof = "Recommended for Cancellation for Pag-ibig Accounts";
				}

				rowHeadePerBatch.setModel(new DefaultListModel());
				functions.displayEditPerBatchRecommended(modelPerBatchModel, rowHeadePerBatch,batch_no);
				scrollPerBatch.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPerBatch.getRowCount())));


				if (cmbProcessFor.getSelectedIndex() == 0 ) {
					tblPerBatch.hideColumns("Entity ID","Project ID","PBL ID","Seq ID","Unit ID","PartID","Phase",	"<html><font color = \"red\">*</font> &nbsp;<i>CSV</i>","Project Name");	
					tblPerBatch.showColumns("<html><font color = \"red\">*</font> &nbsp;<i>Remarks</i>","<html><font color = \"red\">*</font> &nbsp;<i>Reason</i>");
					tblPerBatch.packAll();
					//tblPerBatch.packColumn(21, 400, 400);
					//tblPerBatch.packColumn(22, 400, 400);
				}else{
					tblPerBatch.hideColumns("Entity ID","Project ID","PBL ID","Seq ID","Unit ID","PartID","Phase","<html><font color = \"red\">*</font> &nbsp;<i>Remarks</i>","<html><font color = \"red\">*</font> &nbsp;<i>Reason</i>","Project Name");
					tblPerBatch.showColumns("<html><font color = \"red\">*</font> &nbsp;<i>CSV</i>");
					tblPerBatch.packAll();
				}

				db.select("SELECT proj_id, get_project_name(proj_id),approved_by,get_client_name_emp_id(approved_by),reason FROM rf_cancellation where batch_no = ('"+lookupBatchNo.getValue().toString()+"')");

				if (db.isNotNull()) {
					lookupProjID.setValue(db.Result[0][0].toString());
					txtProjectName.setText(db.Result[0][1].toString());
					lookupApproveby_Active.setValue(db.Result[0][2].toString());
					txtApprovedBy_Active.setText(db.Result[0][3].toString());
					_reason_id = db.Result[0][4].toString();
				}

				/*db.select("SELECT remdesc FROM mf_remarks WHERE remark_id  = '"+reason+"' AND status_id = 'A'");

				if (db.isNotNull()) {

					modelPerBatchModel.setValueAt( db.Result[0][0].toString(), tblPerBatch.getSelectedRow(),22);
				}*/

				modelPerBatchModel.setEditable(true);
			}
		}
	}

	private void perBatchRecommend(){

		for (int i = 0 ;  i < modelPerBatchModel.getRowCount(); i++) {
			Boolean ifSelected =(Boolean) modelPerBatchModel.getValueAt(i, 0);

			_batch_no = lookupBatchNo.getValue().toString();

			if (ifSelected) {

				entity_id  = modelPerBatchModel.getValueAt(i, 21).toString();
				_proj_id =  modelPerBatchModel.getValueAt(i, 22).toString();
				pbl_id =  modelPerBatchModel.getValueAt(i, 23).toString().trim();
				seq_no =  (Integer) modelPerBatchModel.getValueAt(i, 24);
				stage =  modelPerBatchModel.getValueAt(i, 13).toString().trim();
				approved_id = lookupApproveby_Active.getValue().trim();
				months_pd = (Integer) modelPerBatchModel.getValueAt(i, 16); 

				//_dayspd = (Integer) modelPerBatchModel.getValueAt(i, 13);		
				//_amountdue = (BigDecimal) modelPerBatchModel.getValueAt(i, 15);
				_reason = modelPerBatchModel.getValueAt(i, 30).toString().replace("'", "''");
				remarks = modelPerBatchModel.getValueAt(i, 31).toString().replace("'", "''");
				cancelled_by = UserInfo.EmployeeCode.trim();
				_user_id =  UserInfo.EmployeeCode.trim();
				months_paid = (Integer) modelPerBatchModel.getValueAt(i, 17);  
				_due_date = dteDueDate.getDate();

				cancel_type_desc = modelPerBatchModel.getValueAt(i, 29).toString();

				System.out.printf("_monthspd: %s%n", months_pd);

				db.select("SELECT remark_id FROM mf_remarks WHERE remdesc = TRIM('"+_reason+"') AND remtype = '02'");

				if (db.isNotNull()) {

					_reason_id = db.Result[0][0].toString();
				}

				if (cmbBuyerType.getSelectedIndex()==0) {
					if (months_paid >= 24 ) {
						_macedalaw = true;

					}
					if (months_paid  <  24 ) {
						_macedalaw = false;
					}

				}else{
					_macedalaw = false;	
				}

				if (cancel_type_desc.equals("Company Initiated")) {
					cancel_type = "C";
				}else{
					cancel_type = "B";
				}	

				String SQL = "SELECT sp_update_cancellation(\n" + 
						"    '"+entity_id+"', ---p_entity_id ,\n" + 
						"    '"+_proj_id+"', ---p_proj_id ,\n" + 
						"    '"+pbl_id+"', ---p_pbl_id ,\n" + 
						"    "+seq_no+", ---i_seq_no ,\n" +
						"    '"+_batch_no+"', ---p_batchno ,\n" +
						"    "+months_pd+", ---i_monthspd ,\n" + 
						"    NULLIF('"+_reason_id+"','null'), ---p_reason text,\n" + 
						"    NULLIF('"+remarks+"','null'), ---p_remarks text,\n" + 
						"    '"+cancelled_by+"', ---p_cancelledby ,\n" + 
						"    '"+approved_id+"', ---p_approvedby ,\n" +
						"    "+months_paid+", ---i_monthspaid ,\n" + 
						"    "+ifSelected+", \n" + 
						"    '"+_user_id+"', \n" + 
						"    '"+cancel_type+"',\n" +
						"    '"+_due_date+"' )---p_selected ;";

				System.out.printf("SQL: %s%n", SQL);
				db.select(SQL); 

				SQL = "SELECT sp_post_cancellation_final(\n" +  
						"    '"+entity_id+"', ---p_entity_id ,\n" + 
						"    '"+_proj_id+"', ---p_proj_id ,\n" + 
						"    '"+pbl_id+"', ---p_pbl_id ,\n" + 
						"    "+seq_no+", ---i_seq_no ,\n" + 
						"    '"+_user_id+"', ---p_user_id ,\n" + 
						"    '"+stage+"', ---stage_id ,\n" + 
						"    '"+_batch_no+"', ---p_batchno ,\n" + 
						"    "+ifSelected+", \n"+ 
						"    "+_macedalaw+", \n" + 
						"	 '"+_due_date+"') ";

				db.select(SQL);
				System.out.printf("SQL: %s%n", SQL); 
			} 

		}
		JOptionPane.showMessageDialog( getContentPane(), "Account(s) Cancelled with Batch No. : "+_batch_no+"   ", "Successful", JOptionPane.INFORMATION_MESSAGE );
	}

	public class Posting_Cancellation_Active implements Runnable{ // XXX Posting_Cancellation_Active

		private Boolean SelectedItem;
		private String _textreason;

		@Override
		public void run() {

			FncGlobal.startProgress("Saving  . . .Please wait ");
 
			int isEmpty = 0;
			if (tabpane_per_account.getSelectedIndex() == 0) {
				tagged = 0;

				if (cmbProcessFor.getSelectedIndex() == 0) {

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

						int response = JOptionPane.showConfirmDialog(getContentPane(),"Are you sure you want to cancel selected account(s)  ","Confirm",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
						if (response == JOptionPane.YES_OPTION) {

							//Save_Cancellation();
							perBatchRecommend();
							new Thread(new ForNotices()).start();
							_setEnabled(false);
							_Clear();
							lblBatchNo.setEnabled(true);
							lookupBatchNo.setEnabled(true);
							btnState(true, true, false, false, false);

						}
					}
				}//recommended 

				/*	
				 * 
				 * **   
				 * ** 
				 * **
				 * if (cmbProcessFor.getSelectedIndex() == 1) {

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
				}*/
			}
			FncGlobal.stopProgress(); 
		}
	}
	@SuppressWarnings("rawtypes")
	public class ForNotices implements Runnable{

		private String db_reason_id;
		ArrayList<String> listReport = new ArrayList<String>(); 
		ArrayList<String> listTitle = new ArrayList<String>();
		
		ArrayList<Map> listParameters = new ArrayList<Map>();
		private String db_entity_id;
		private String db_proj_id;
		private String db_pbl_id;
		private Integer db_seq_no;
		private String title_report; 

		@Override
		public void run() {

			//batch_no = lookupBatchNo.getValue().toString();

			int OK = JOptionPane.showConfirmDialog(getContentPane(),"\nSelected Account(s) Posted on the Cancellation Table \n" +
					"\n Click OK to Display Notice","Successful",
					JOptionPane.OK_CANCEL_OPTION);
			if (OK == JOptionPane.OK_OPTION){

				Map<String, Object> mapParameters = new HashMap<String, Object>();
				mapParameters.put("batch_no", batch_no);
				mapParameters.put("company", company);
				String SQL = "";

				if (tabpane_per_account.getSelectedIndex() == 1) {
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
							"WHERE rec_id = (SELECT MAX(rec_id) FROM rf_client_notices)";
					
					_due_date = dateFormat("2018-05-14");

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
							"WHERE b.batch_no ='"+batch_no+"' and dateprep >= current_date ";
					
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

							if (tabpane_per_account.getSelectedIndex() == 1) {
								listReport.add(String.format("/Reports/%s.jasper", "rptNoticeOfCancellationAndToVacate_Per_Account"));
								listTitle.add(String.format("%s ", title_report));
								listParameters.add(mapParameters);
							}else{
								listReport.add(String.format("/Reports/%s.jasper", "rptNoticeOfCancellationAndToVacate"));
								listTitle.add(String.format("%s ", title_report));
								listParameters.add(mapParameters);	
							}

						}else{
							if (tabpane_per_account.getSelectedIndex() == 1) {
								listReport.add(String.format("/Reports/%s.jasper", "rptNoticeOfCancellationWithNonMacedaLaw_Per_account"));
								listTitle.add(String.format("%s ", title_report));
								listParameters.add(mapParameters);
							}else{
								listReport.add(String.format("/Reports/%s.jasper", "rptNoticeOfCancellationWithNonMacedaLaw"));
								listTitle.add(String.format("%s ", title_report));
								listParameters.add(mapParameters);	
							}
						}
					}

					if (db_reason_id.equals("00232") || db_reason_id.equals("00231")) {
						System.out.println("DUMAAN B DITO FOR SECOND REPORT");
						mapParameters.put("entity_id", db_entity_id);
						mapParameters.put("proj_id", db_proj_id);
						mapParameters.put("pbl_id", db_pbl_id);
						mapParameters.put("seq_no", db_seq_no);


						if (tabpane_per_account.getSelectedIndex() == 1) {
							listReport.add(String.format("/Reports/%s.jasper", "rptNoticeOfCancellation_DueDocs_Per_Account"));
							listTitle.add(String.format("%s ", title_report));
							listParameters.add(mapParameters);

						}else{
							listReport.add(String.format("/Reports/%s.jasper", "rptNoticeOfCancellation_DueDocs"));
							listTitle.add(String.format("%s ", title_report)); 
							listParameters.add(mapParameters);	
						}

					}

					if(db_reason_id.equals("00035") || db_reason_id.equals("00002")){ 
						//FncReport.generateReport("/Reports/rptNoticeOfCancellation_Backout.jasper", title_report,  company, mapParameters); // FOR BACKOUT	

						mapParameters.put("entity_id", db_entity_id);
						mapParameters.put("proj_id", db_proj_id);
						mapParameters.put("pbl_id", db_pbl_id);
						mapParameters.put("seq_no", db_seq_no);

						if (tabpane_per_account.getSelectedIndex() == 1) {

							listReport.add(String.format("/Reports/%s.jasper", "rptNoticeOfCancellation_Backout_Per_Account"));
							listTitle.add(String.format("%s ", title_report));
							listParameters.add(mapParameters);
						}else{
							listReport.add(String.format("/Reports/%s.jasper", "rptNoticeOfCancellation_Backout"));
							listTitle.add(String.format("%s ", title_report));
							listParameters.add(mapParameters); 
						}
					}
				}

				FncReport.generateReport(listReport.toArray(), listTitle.toArray(), listParameters.toArray(), "Notice to Cancellation");

			}
		}
	}

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

	private void Save_PerAccount_Edit(){
		_reason_id = lookupReason.getValue();
		remarks = txtareaRemarks.getText();
		approved_id = lookupApproveby_Active_PA.getValue().toString();

		if (cmbCancelType_Per.getSelectedIndex() == 1) {
			cancel_type = "C";
		}
		if (cmbCancelType_Per.getSelectedIndex() == 2) {
			cancel_type = "B";
		}

		String SQL = "SELECT sp_update_projected_cancellation_recom(\n" + 
				"    '"+entity_id+"',---p_entity_id character varying,\n" + 
				"    '"+unit_id+"',---p_unit_id character varying,\n" + 
				"    '"+_reason_id+"',---p_reason character varying,\n" + 
				"    '"+approved_id+"',---p_approved character varying,\n" + 
				"    '"+cancel_type+"',---p_cancel_type character varying,\n" + 
				"    '"+remarks+"',---p_remarks character varying,\n" + 
				"    "+true+")---p_selected boolean";

		
		System.out.printf("SQL: %s%n", SQL);
		db.select(SQL);

	}

	private void Save_PerAccount(){

		batch_no = lookupBatchNo.getValue().toString();

		db.select("SELECT _payments_made('"+entity_id +"','"+proj_id +"','"+pbl_id +"',"+ seq_no +")");

		if (db.isNotNull()) {
			months_paid = (Integer) (db.Result[0][0] == null ? 0 : db.Result[0][0]);
		}

		months_pd = Integer.valueOf(txtMonthPD.getText());
		_reason_id = lookupReason.getValue();
		remarks = txtareaRemarks.getText();
		cancelled_by = UserInfo.EmployeeCode.trim();
		_user_id = UserInfo.EmployeeCode.trim();
		approved_id = lookupApproveby_Active_PA.getValue().toString();
		stage =  txtStage.getText().toString();

		
		if (cmbCancelType_Per.getSelectedIndex() == 1) {
			cancel_type = "C";
		}
		if (cmbCancelType_Per.getSelectedIndex() == 2) {
			cancel_type = "B"; 
		}

		String SQL = "SELECT sp_post_projected_cancellation_recom(\n" + 
				"    '"+entity_id+"',---p_entity_id character varying,\n" + 
				"    '"+proj_id+"',---p_proj_id character varying,\n" + 
				"    '"+pbl_id+"',---p_pbl_id character varying,\n" + 
				"    "+seq_no+",---i_seq_no integer,\n" + 
				"    '"+stage+"',---p_part_id character varying,\n" + 
				"    "+months_pd+",---i_months_pd integer,\n" + 
				"    "+months_paid+", ---i_monthspaid ,\n" + 
				"     "+_amountdue+",---n_amountdue numeric,\n" + 
				"    NULLIF('"+_lastpaid_date+"','null')::date,---p_lastpaid_date timestamp without time zone,\n" + 
				"    NULLIF('"+_default_date+"','null')::date,---p_default_date timestamp without time zone,\n" + 
				"    NULL,---p_lastsched_date timestamp without time zone,\n" + 
				"    '"+_user_id+"',---p_user_id character varying,\n" + 
				"    '"+_reason_id+"',---p_reason character varying,\n" + 
				"    '"+remarks+"',---p_remarks character varying,\n" + 
				"    '"+cancel_type+"',---p_cancel_type character varying,\n" + 
				"    '"+approved_id+"',---p_approved_id character varying,\n" + 
				"    '"+batch_no+"', ---p_batchno ,\n" + 
				"    "+true+"---p_selected boolean \n" +
				"    )";

		System.out.printf("SQL: %s%n", SQL);
		db.select(SQL);
	}

	private void postingPerAcoount(){

		db.select("SELECT _payments_made('"+entity_id +"','"+proj_id +"','"+pbl_id +"',"+ seq_no +")");

		if (db.isNotNull()) {
			months_paid = (Integer) (db.Result[0][0] == null ? 0 : db.Result[0][0]);
		}
		months_pd = Integer.valueOf(txtMonthPD.getText());
		_reason_id = lookupReason.getValue();
		remarks = txtareaRemarks.getText();
		cancelled_by = UserInfo.EmployeeCode.trim();
		_user_id = UserInfo.EmployeeCode.trim();
		approved_id = lookupApproveby_Active_PA.getValue().toString();
		stage =  txtStage.getText().toString();

		//TEMP 
		_due_date = dateFormat("2018-05-14");

		if (cmbBuyerType.getSelectedIndex()==0) {
			if (months_paid >= 24 ) {
				_macedalaw = true;

			}
			if (months_paid  <  24 ) {
				_macedalaw = false; 
			}
		}else{
			_macedalaw = false;
		}

 
		if (cmbCancelType_Per.getSelectedIndex() == 1) {
			cancel_type = "C";
		}
		if (cmbCancelType_Per.getSelectedIndex() == 2) {
			cancel_type = "B";
		}

		String SQL = "SELECT sp_update_cancellation(\n" + 
				"    '"+entity_id+"', ---p_entity_id ,\n" + 
				"    '"+proj_id+"', ---p_proj_id ,\n" + 
				"    '"+pbl_id+"', ---p_pbl_id ,\n" + 
				"    "+seq_no+", ---i_seq_no ,\n" + 
				"    '"+batch_no+"', ---p_batchno ,\n" + 
				"    "+months_pd+", ---i_monthspd ,\n" + 
				"    NULLIF('"+_reason_id+"','null'), ---p_reason text,\n" + 
				"    NULLIF('"+remarks+"','null'), ---p_remarks text,\n" + 
				"    '"+cancelled_by+"', ---p_cancelledby ,\n" + 
				"    '"+approved_id+"', ---p_approvedby ,\n" + 
				"    "+months_paid+", ---i_monthspaid ,\n" + 
				"    "+true+",'"+_user_id+"', '"+cancel_type+"','"+_due_date+"' )---p_selected ;";

		System.out.printf("SQL: %s%n", SQL);
		db.select(SQL); 

		SQL = "SELECT sp_post_cancellation_final(\n" +  
				"    '"+entity_id+"', ---p_entity_id ,\n" + 
				"    '"+proj_id+"', ---p_proj_id ,\n" + 
				"    '"+pbl_id+"', ---p_pbl_id ,\n" + 
				"    "+seq_no+", ---i_seq_no ,\n" + 
				"    '"+_user_id+"', ---p_user_id ,\n" +  
				"    '"+stage+"', ---stage_id ,\n" + 
				"    '"+batch_no+"', ---p_batchno ,\n" + 
				"    "+true+", \n"+ 
				"    "+_macedalaw+", \n" + 
				"	 '"+_due_date+"') ";

		db.select(SQL);

		System.out.printf("SQL: %s%n", SQL); 
	}

	public class ForSave_PerAccount implements Runnable{

		private String txtNote; 

		@Override
		public void run() {

			FncGlobal.startProgress("Saving  . . .Please wait ");

			if (lookupApproveby_Active_PA.getValue().isEmpty()) {
				JOptionPane.showMessageDialog(getContentPane(), "Please Select the Approving Officer for this Cancellation Process", "Incomplete Details", JOptionPane.OK_OPTION);
				FncGlobal.stopProgress();
				return;

			} // Approved by is empty
			if (txtareaRemarks.getText().isEmpty()) {
				JOptionPane.showMessageDialog(getContentPane(), "Please indicate the Remarks for Cancellation", "Incomplete", JOptionPane.OK_OPTION);
				FncGlobal.stopProgress();
				return;
			}

			if (lblNote1.isVisible()) {
				txtNote = "Selected Client is not Recommended for Cancellation \nDo you want to save this Account?";
			}else{
				txtNote = "Are you sure you want to save selected account(s)  "; 
			}

			if (JOptionPane.showConfirmDialog(getContentPane(), txtNote ,"Confirm", 
					JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

				System.out.println("***DDD" + grpButton_Pa.getSelection().getActionCommand());
				if (grpButton_Pa.getSelection().getActionCommand().equals("Edit_PA")) {
					System.out.println("EDIT");
					Save_PerAccount_Edit();
				}else{
					System.out.println("NEW SAVE");
					Save_PerAccount();
				}

				new Thread(new ForPreviewPerAccount()).start();
				FormLoad();
				_Clear();	

			}

			FncGlobal.stopProgress();
		}
	}

	public class ForPosting_PerAccount implements Runnable{

		private String txtNote;

		@Override
		public void run() {

			FncGlobal.startProgress("Posting  . . .Please wait ");

			if (lookupApproveby_Active_PA.getValue().isEmpty()) {
				JOptionPane.showMessageDialog(getContentPane(), "Please Select the Approving Officer for this Cancellation Process", "Incomplete Details", JOptionPane.OK_OPTION);
				FncGlobal.stopProgress();
				return;

			} // Approved by is empty
			if (txtareaRemarks.getText().isEmpty()) {
				JOptionPane.showMessageDialog(getContentPane(), "Please indicate the Reason for Cancellation", "Incomplete", JOptionPane.OK_OPTION);
				FncGlobal.stopProgress();
				return;
			}

			if (lblNote1.isVisible()) {
				txtNote = "Selected Client is not Recommended for Cancellation \nDo you want to cancel this Account?";
			}else{
				txtNote = "Are you sure you want to cancel selected account(s)  "; 
			}

			if (JOptionPane.showConfirmDialog(getContentPane(), txtNote ,"Confirm", 
					JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				postingPerAcoount();
				new Thread(new ForNotices()).start();
				FormLoad();
				_Clear();
			}

			FncGlobal.stopProgress();
		}
	}

	public class ForPosting_Tag implements Runnable{
		@Override
		public void run() {
			FncGlobal.startProgress("Posting  . . .Please wait ");

			postingStatuTag(); 
			FncGlobal.stopProgress();
		}
	}

	private void postingStatuTag(){
		for (int i = 0 ;  i < modelCancelTag.getRowCount(); i++) {

			Boolean ifSelected =  (Boolean) modelCancelTag.getValueAt(i, 0);

			if (ifSelected) {

				_user_id 	= UserInfo.EmployeeCode.trim();
				_transdate	=  modelCancelTag.getValueAt(i, 7).toString();
				_remarks 	= modelCancelTag.getValueAt(i, 8).toString().replace("'", "''");
				_cancel_csv_status_id =  modelCancelTag.getValueAt(i, 9).toString();

				/*String _entity_id = modelCancelTag.getValueAt(i, 11).toString();
				String proj_id_ = modelCancelTag.getValueAt(i, 12).toString();
				String _pbl_id = modelCancelTag.getValueAt(i, 13).toString();
				Integer _seq_no = (Integer) modelCancelTag.getValueAt(i, 14); 
*/
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

			/*	if (tabpane.getSelectedIndex() == 0) {
				if (cmbProcessFor.getSelectedIndex() == 0) {
					FncReport.generateReport("/Reports/rptCancellation_Active.jasper", "",  mapParameters,functions.printSQL);	
				}
			}*/

			if (tabpane.getSelectedIndex() == 1) {
				if (cmbProcessFor_Tag.getSelectedIndex() == 0) {

					FncReport.generateReport("/Reports/rptCancellationStatus.jasper", "Status of Canceled Accounts",  company, mapParameters);	
				}else{

					FncReport.generateReport("/Reports/rptCancellationStatus_CSV.jasper", "Status of Accounts With CSV",  company, mapParameters);
				}
			}	
		}
	}

	private void FormLoad(){

		if (tabpane.getSelectedIndex() == 0 ) {
			if (tabpane_per_account.getSelectedIndex() == 0 ) {

			}else{
				this.setComponentsEnabled(pnlPerAccount, false);
				this.setComponentsEnabled(pnlCenter_Approved_C_Active_PA, false);
				btnState_PA(true, true, false, false, false);
			}
		}else{
			this.setComponentsEnabled(pnlCancelTagStatus_North_North, false);
			this.setComponentsEnabled(pnlCancelTagStatus_Center, false);
			this.setComponentsEnabled(pnlCancelTagStatus_South, false);

			if (lookupCompany.getValue().equals("ID")) {
				lookupProjID_Tag.setEnabled(false);
				txtProjectName_Tag.setEnabled(false);

			}else{
				lookupProjID_Tag.setEnabled(true);
				txtProjectName_Tag.setEnabled(true);
			}
			//this.setComponentsEnabled(pnlCenter_Approved_C_Active_PA, false);

		}

	}
	public class ForPreviewPerAccount implements Runnable{

		private String preparedby;
		private String approvedby;

		@Override
		public void run() {
			// TODO Auto-generated method stub


			Map<String, Object> mapParameters = new HashMap<String, Object>();
			mapParameters.put("company", company);
			mapParameters.put("co_id", co_id);
			mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
			mapParameters.put("batchno",batch_no);

			String SQL = "select \n" + 
					"get_unit_description(a.proj_id,a.pbl_id) AS unit_pbl,\n" + 
					"_get_client_name(a.entity_id) AS client_name,\n" + 
					"(SELECT CASE WHEN _get_client_buyertype(entity_id,proj_id,pbl_id,seq_no) = '02' THEN 'IHF' ELSE 'PAG-IBIG' END)  AS buyertype,\n" + 
					"(SELECT _get_sales_div(entity_id,pbl_id,seq_no)) AS sale_div,\n" + 
					"_get_sales_agent(entity_id,proj_id,pbl_id) AS sale_agent,\n" + 
					"(SELECT _get_house_model_alias(pbl_id)) AS house_model,\n" + 
					"get_payment_stage(entity_id,proj_id,pbl_id,seq_no) AS stage,\n" + 
					"_get_project_phase(proj_id, pbl_id )  AS phase,\n" + 
					"_get_tr_date(entity_id,proj_id,pbl_id,seq_no) AS tr_date,\n" + 
					"_get_or_date(entity_id,proj_id,pbl_id,seq_no) AS or_date,\n" + 
					"default_date,\n" + 
					"get_nsp(entity_id,pbl_id,seq_no,proj_id)::numeric AS nsp,\n" + 
					"(SELECT CASE WHEN _get_with_ntc(proj_id,pbl_id) ISNULL THEN 'No' ELSE 'Yes' END) AS withntc,\n" + 
					"proj_id AS proj_id,\n" + 
					"get_project_name(proj_id) AS proj_name,\n" + 
					"(CASE WHEN _get_or_date(entity_id,proj_id,pbl_id,seq_no) IS NULL THEN 'TR' ELSE 'OR' END) AS class\n" + 
					"from rf_cancellation a where batch_no = '"+batch_no+"'";


			db.select(SQL);

			if (db.isNotNull()) {

				if (db.Result[0][2].toString().equals("IHF")) {
					lblListof = "Recommended for Cancellation for IHF Accounts";
				}else{

					lblListof = "Recommended for Cancellation for Pag-ibig Accounts";
				}

				mapParameters.put("lblListof", lblListof);

				int count_tr = 0;
				int count_or = 0;
				int total_or_tr = 0;
				
				BigDecimal total_tr = new BigDecimal("0.00");
				BigDecimal total_or = new BigDecimal("0.00");
				BigDecimal subtotal_or_tr = new BigDecimal("0.00");

				for (int j = 0; j < db.getRowCount(); j++) {
					if (db.Result[j][15].equals("TR")) {
						BigDecimal totalTR = (BigDecimal) db.Result[j][11];

						try {
							total_tr = total_tr.add(totalTR);

						} catch (Exception e1) { }
						count_tr++;
					}
					if (db.Result[j][15].equals("OR")) {

						BigDecimal totalOR = (BigDecimal) db.Result[j][11];

						try {
							total_or = total_or.add(totalOR);

						} catch (Exception e1) { }
						count_or++;
					}	
				}

				total_or_tr = count_or + count_tr;
				subtotal_or_tr = subtotal_or_tr.add(total_tr) ;
				subtotal_or_tr = subtotal_or_tr.add(total_or) ;

				mapParameters.put("total_sum_tr",total_tr);
				mapParameters.put("count_tr",count_tr);
				mapParameters.put("total_sum_or",total_or);
				mapParameters.put("count_or",count_or);
				mapParameters.put("subtotal_or_tr",subtotal_or_tr);
				mapParameters.put("total_or_tr",total_or_tr);
			//  Modified by : Jervin Vilog /change of level no. due to request of sir garret 
//				db.select("select (select  level_no from mf_rank_level where level_code = emp_rank), _get_client_name(entity_id) "
//						+ "from em_employee where emp_code = '"+UserInfo.EmployeeCode+"' "
//						+ "and (select level_no from mf_rank_level where level_code = emp_rank) > 13");
				db.select("select (select  level_no from mf_rank_level where level_code = emp_rank), _get_client_name(entity_id) "
						+ "from em_employee where emp_code = '"+UserInfo.EmployeeCode+"' "
						+ "and (select level_no from mf_rank_level where level_code = emp_rank) < 5");

				if (db.isNotNull()) {

					preparedby = db.Result[0][1].toString();

				}
//  Modified by : Jervin Vilog /change of level no. due to request of sir garret 
//				db.select("select (select  level_no from mf_rank_level where level_code = emp_rank), _get_client_name(entity_id) "
//						+ "from em_employee where emp_code = '"+UserInfo.EmployeeCode+"' "
//						+ "and (select  level_no from mf_rank_level where level_code = emp_rank) <= 13");
				db.select("select (select  level_no from mf_rank_level where level_code = emp_rank), _get_client_name(entity_id) "
						+ "from em_employee where emp_code = '"+UserInfo.EmployeeCode+"' "
						+ "and (select  level_no from mf_rank_level where level_code = emp_rank) >= 5");

				if (db.isNotNull()) {
					approvedby = db.Result[0][1].toString();
				}

				mapParameters.put("approvedby",approvedby);
				mapParameters.put("preparedby",preparedby);

				System.out.println("SQL: " + SQL) ;
				FncReport.generateReport("/Reports/rptCancellation_Processing.jasper", "List of Recommendation Accounts",  mapParameters, SQL);

			}
		}
	}
	
	private void getDefaultCompany(){


		co_id = "02";
		company = "CENQHOMES DEVELOPMENT CORPORATION" ;
		company_logo = "cenq_logo.jpg";
		txtCompany.setText(company);
		lookupCompany.setValue(co_id);
		lookupBatchNo.setEnabled(true);
		lblBatchNo.setEnabled(true);
		lookupProjID.setLookupSQL(functions.lookupProjects(co_id));
		lookupProj.setLookupSQL(functions.lookupProjects(co_id));
		lookupProjID_Tag.setLookupSQL(functions.lookupProjects(co_id));
		cmbBuyerType.setEnabled(true);
		lblBuyerType.setEnabled(true);
		lookupBatchNo.setLookupSQL(functions.listBatch());

		if (tabpane_per_account.getSelectedIndex() == 0 ) {
			btnState(true, true, false, false, false);	
		}
		if (tabpane_per_account.getSelectedIndex() == 1 ) {
			btnState_PA(true, true, false, false, false);
			lookupProjID_Tag.setLookupSQL(functions.lookupProjects(co_id));
		}

	
	}
}
