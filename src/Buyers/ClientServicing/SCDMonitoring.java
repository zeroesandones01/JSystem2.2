package Buyers.ClientServicing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.jdesktop.swingx.JXTextField;

import DateChooser._JDateChooser;
import Functions.FncReport;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components.JTBorderFactory;
import components._JCheckBox;
import components._JInternalFrame;
import components._JScrollPane;
import components._JTableMain;
import components._JXTextField;
import interfaces._GUI;
import tablemodel.modelSCDM_Accounts;
import tablemodel.modelSCDM_InAccounts;
import tablemodel.modelSCDM_OutAccounts;
import tablemodel.modelSCDStatusEditor;

/**
 * @author Alvin Gonzales (2015-07-23)
 *
 */
public class SCDMonitoring extends _JInternalFrame implements ActionListener, _GUI {

	/**
	 * 
	 */

	private static final long serialVersionUID = -105207442500121795L;

	static String title = "SCD Monitoring";
	Dimension frameSize = new Dimension(800, 550);// 510, 250

	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	Cursor handCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);

	private JPanel pnlMain;
	private JTabbedPane tabCenter;
	private JPanel pnlIn;
	private JPanel pnlOut;
	private JPanel pnlPrep;
	private JPanel pnlBuyerStatusEditor;

	private _JXTextField txtPrepBatchNo;
	private _JLookup lookupPrepCompany;
	private _JXTextField txtPrepCompany;
	private _JCheckBox chkPrepProject;
	private _JLookup lookupPrepProject;
	private _JXTextField txtPrepProjectName;
	private _JDateChooser datePrepORFrom;
	private _JDateChooser datePrepORTo;
	private JButton btnPrepGenerate;
	private JButton btnPrepPreview;
	private JButton btnPrepPost;

	private _JScrollPane scrollPrepAccount;
	private _JTableMain tblPrepAccount;
	private modelSCDM_Accounts modelPrepAccount;
	private JList rowheaderPrepAccount;

	private _JXTextField txtOutBatchNo;
	private _JLookup lookupOutCompany;
	private _JXTextField txtOutCompany;
	private _JCheckBox chkOutProject;
	private _JLookup lookupOutProject;
	private _JXTextField txtOutProjectName;
	private _JDateChooser dateOutPrepFrom;
	private _JDateChooser dateOutPrepTo;
	private JButton btnOutGenerate;
	//private JButton btnOutPreview;
	private JButton btnOutPost;

	private _JScrollPane scrollOutAccount;
	private _JTableMain tblOutAccount;
	private modelSCDM_OutAccounts modelOutAccount;
	private JList rowheaderOutAccount;

	private _JScrollPane scrollInAccount;
	private _JTableMain tblInAccount;
	private modelSCDM_InAccounts modelInAccount;
	private _JXTextField txtInBatchNo;
	private _JCheckBox chkInProject;
	private _JLookup lookupInProject;
	private _JXTextField txtInProject;
	private JButton btnInGenerate;
	private JList rowheaderInAccount;
	private JButton btnInPreview;
	private JButton btnInPost;

	private JPanel pnlBuyerStatusEditorNorth;

	private JPanel pnlStatusEditorLabel;
	private JLabel lblClient;
	private JLabel lblProject;
	private JLabel lblUnit;
	private JLabel lblSeq;
	private JLabel lblHouseModel;
	private JLabel lblDateInactive;
	private JLabel lblStatus;

	private JPanel pnlStatusEditorComponent;
	private _JLookup lookupClient;
	private _JXTextField txtClient;
	private _JXTextField txtProjID;
	private _JXTextField txtProjName;
	private _JXTextField txtUnitID;
	private _JXTextField txtSeqNo;
	private _JXTextField txtUnitDesc;
	private _JXTextField txtModelID;
	private _JXTextField txtModelDesc;
	private _JDateChooser dteInactive;
	private JComboBox cmbStatus;

	private JPanel pnlBuyerStatusEditorCenter;
	private _JScrollPane scrollStatusHistory;
	private _JTableMain tblStatusHistory;
	private modelSCDStatusEditor modelSCDStatusEditor;
	private JList rowheaderStatusHistory;

	private JPanel pnlBuyerStatusEditorSouth;
	private JButton btnSave;

	private KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();

	public SCDMonitoring() {
		super(title, true, true, true, true);
		initGUI();
	}

	public SCDMonitoring(String title) {
		super(title);
		initGUI();
	}

	public SCDMonitoring(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		this.setPreferredSize(frameSize);
		this.setMinimumSize(frameSize);
		this.setLayout(new BorderLayout());
		{
			pnlMain = new JPanel();
			getContentPane().add(pnlMain, BorderLayout.CENTER);
			pnlMain.setLayout(new BorderLayout(3, 3));
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 3));
			{
				tabCenter = new JTabbedPane();
				pnlMain.add(tabCenter, BorderLayout.CENTER);
				tabCenter.addChangeListener(new ChangeListener() {
					public void stateChanged(ChangeEvent arg0) {
						JTabbedPane tab = (JTabbedPane) arg0.getSource();

						if(tab.getSelectedIndex() == 1){

						}
						if(tab.getSelectedIndex() == 2){
							//displayInAccounts();
						}
					}
				});
				{
					pnlPrep = new JPanel(new BorderLayout(3, 3));
					tabCenter.addTab("Preparation", null, pnlPrep, null);
					pnlPrep.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
					{
						JPanel pnlPrepNorth1 = new JPanel(new BorderLayout());
						pnlPrep.add(pnlPrepNorth1, BorderLayout.NORTH);
						pnlPrepNorth1.setBorder(LINE_BORDER);
						{
							JPanel pnlPrepNorth2 = new JPanel(new BorderLayout(3, 3));
							pnlPrepNorth1.add(pnlPrepNorth2, BorderLayout.CENTER);
							pnlPrepNorth2.setPreferredSize(new Dimension(688, 110));
							pnlPrepNorth2.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
							{
								JPanel pnlPrepNorthWest = new JPanel(new BorderLayout(3, 3));
								pnlPrepNorth2.add(pnlPrepNorthWest, BorderLayout.WEST);
								pnlPrepNorthWest.setPreferredSize(new Dimension(200, 81));
								{
									JPanel pnlLabels = new JPanel(new GridLayout(4, 1, 3, 3));
									pnlPrepNorthWest.add(pnlLabels, BorderLayout.WEST);
									pnlLabels.setPreferredSize(new Dimension(75, 0));
									{
										JLabel lblPrepCompany = new JLabel("Batch No.");
										pnlLabels.add(lblPrepCompany);
										lblPrepCompany.setHorizontalAlignment(JLabel.RIGHT);
									}
									/*{
										JLabel lblCompany = new JLabel("Company");
										pnlLabels.add(lblCompany);
									}*/
									{
										chkPrepProject = new _JCheckBox("Project");
										pnlLabels.add(chkPrepProject);
										chkPrepProject.setIconTextGap(11);
										chkPrepProject.addItemListener(new ItemListener() {
											public void itemStateChanged(ItemEvent e) {
												lookupPrepProject.setEnabled(e.getStateChange() == ItemEvent.SELECTED);
												lookupPrepProject.requestFocus();
											}
										});
									}
									/*{
										JLabel lblPrepORFrom = new JLabel("OR From", JLabel.TRAILING);
										pnlLabels.add(lblPrepORFrom);
									}
									{
										JLabel lblPrepORTo = new JLabel("OR To", JLabel.TRAILING);
										pnlLabels.add(lblPrepORTo);
									}*/
								}
								{
									JPanel pnlLookups = new JPanel(new GridLayout(4, 1, 3, 3));
									pnlPrepNorthWest.add(pnlLookups, BorderLayout.CENTER);
									pnlLookups.setPreferredSize(new Dimension(75, 0));
									{
										txtPrepBatchNo = new _JXTextField("Batch No.");
										pnlLookups.add(txtPrepBatchNo);
										txtPrepBatchNo.setHorizontalAlignment(JXTextField.CENTER);
									}
									/*{
										lookupPrepCompany = new _JLookup(null, "Company", 0);
										pnlLookups.add(lookupPrepCompany);
									}*/
									{
										lookupPrepProject = new _JLookup(null, "Project", "Please select company.");
										pnlLookups.add(lookupPrepProject);
										lookupPrepProject.setLookupSQL(SQL_PROJECT());
										lookupPrepProject.setEnabled(false);
										lookupPrepProject.setReturnColumn(0);
										lookupPrepProject.setBounds(90, 40, 50, 25);
										lookupPrepProject.addLookupListener(new LookupListener() {
											public void lookupPerformed(LookupEvent event) {//XXX Project
												Object[] data = ((_JLookup)event.getSource()).getDataSet();
												if(data != null){
													txtPrepProjectName.setText(data[1].toString());
													manager.focusNextComponent();
												}else{
													txtPrepProjectName.setText("");
												}
											}
										});
									}
									/*{
										datePrepORFrom = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
										pnlLookups.add(datePrepORFrom);
									}
									{
										datePrepORTo = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
										pnlLookups.add(datePrepORTo);
										datePrepORTo.setDate(Calendar.getInstance().getTime());
									}*/
								}
							}
							{
								JPanel pnlPrepNorthCenter = new JPanel(new GridLayout(4, 1, 3, 3));
								pnlPrepNorth2.add(pnlPrepNorthCenter, BorderLayout.CENTER);
								{
									pnlPrepNorthCenter.add(Box.createHorizontalGlue());
									/*txtPrepCompany = new _JXTextField();
									pnlPrepNorthCenter.add(txtPrepCompany);*/

									txtPrepProjectName = new _JXTextField();
									pnlPrepNorthCenter.add(txtPrepProjectName);
								}
							}
							{
								btnPrepGenerate = new JButton("Generate");
								pnlPrepNorth2.add(btnPrepGenerate, BorderLayout.EAST);
								btnPrepGenerate.setActionCommand("Generate Preparation");
								btnPrepGenerate.setPreferredSize(new Dimension(200, 81));
								btnPrepGenerate.addActionListener(this);
							}
						}
					}
					{

						scrollPrepAccount = new _JScrollPane();
						pnlPrep.add(scrollPrepAccount, BorderLayout.CENTER);
						scrollPrepAccount.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
						scrollPrepAccount.addMouseListener(new MouseAdapter() {
							public void mouseClicked(MouseEvent e) {
								tblPrepAccount.clearSelection();
							}
						});
						{
							modelPrepAccount = new modelSCDM_Accounts();
							modelPrepAccount.addTableModelListener(new TableModelListener() {
								public void tableChanged(TableModelEvent e) {
									if(e.getType() == TableModelEvent.DELETE){
										rowheaderPrepAccount.setModel(new DefaultListModel());
										scrollPrepAccount.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPrepAccount.getRowCount())));
									}
									if(e.getType() == TableModelEvent.INSERT){
										((DefaultListModel)rowheaderPrepAccount.getModel()).addElement(modelPrepAccount.getRowCount());
									}
								}
							});

							tblPrepAccount = new _JTableMain(modelPrepAccount);
							scrollPrepAccount.setViewportView(tblPrepAccount);
							tblPrepAccount.hideColumns("Sales Group", "Network", "Batch No.", "TR Date" ,"Client's ID", "Project ID",	"PBL ID", "Sequence", "Coapplicant", "Loan Amt", "Company Name", "Company Alias", "Proj. Name", "Coordinator", "Sales Dept.");

							tblPrepAccount.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
								public void valueChanged(ListSelectionEvent e) {
									if(!e.getValueIsAdjusting()){//XXX tblJVDetail

									}
								}
							});
						}
						{
							rowheaderPrepAccount = tblPrepAccount.getRowHeader();
							rowheaderPrepAccount.setModel(new DefaultListModel());
							scrollPrepAccount.setRowHeaderView(rowheaderPrepAccount);
							scrollPrepAccount.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
						}
					}
					{
						JPanel pnlPrepSouth = new JPanel(new GridLayout(1, 9, 3, 3));
						pnlPrep.add(pnlPrepSouth, BorderLayout.SOUTH);
						pnlPrepSouth.setPreferredSize(new Dimension(0, 30));
						{
							pnlPrepSouth.add(Box.createHorizontalBox());
							pnlPrepSouth.add(Box.createHorizontalBox());
							pnlPrepSouth.add(Box.createHorizontalBox());
							pnlPrepSouth.add(Box.createHorizontalBox());
							pnlPrepSouth.add(Box.createHorizontalBox());
							pnlPrepSouth.add(Box.createHorizontalBox());
							pnlPrepSouth.add(Box.createHorizontalBox());
						}
						{
							btnPrepPreview = new JButton("Preview");
							pnlPrepSouth.add(btnPrepPreview);
							btnPrepPreview.setActionCommand("Preview Preparation");
							btnPrepPreview.setPreferredSize(new Dimension(100, 0));
							btnPrepPreview.addActionListener(this);
						}
						{
							btnPrepPost = new JButton("Post");
							pnlPrepSouth.add(btnPrepPost);
							btnPrepPost.setActionCommand("Post Preparation");
							btnPrepPost.setPreferredSize(new Dimension(100, 0));
							btnPrepPost.addActionListener(this);
						}
					}
				}
				{
					pnlOut = new JPanel(new BorderLayout(3, 3));
					tabCenter.addTab("Out", null, pnlOut, null);
					pnlOut.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
					{
						JPanel pnlOutNorth1 = new JPanel(new BorderLayout());
						pnlOut.add(pnlOutNorth1, BorderLayout.NORTH);
						pnlOutNorth1.setBorder(LINE_BORDER);
						{
							JPanel pnlOutNorth2 = new JPanel(new BorderLayout(3, 3));
							pnlOutNorth1.add(pnlOutNorth2, BorderLayout.CENTER);
							pnlOutNorth2.setPreferredSize(new Dimension(688, 110));
							pnlOutNorth2.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
							{
								JPanel pnlOutNorthWest = new JPanel(new BorderLayout(3, 3));
								pnlOutNorth2.add(pnlOutNorthWest, BorderLayout.WEST);
								pnlOutNorthWest.setPreferredSize(new Dimension(200, 81));
								{
									JPanel pnlLabels = new JPanel(new GridLayout(4, 1, 3, 3));
									pnlOutNorthWest.add(pnlLabels, BorderLayout.WEST);
									pnlLabels.setPreferredSize(new Dimension(75, 0));
									{
										JLabel lblOutCompany = new JLabel("Batch No.");
										pnlLabels.add(lblOutCompany);
										lblOutCompany.setHorizontalAlignment(JLabel.RIGHT);
									}
									{
										chkOutProject = new _JCheckBox("Project");
										pnlLabels.add(chkOutProject);
										chkOutProject.setIconTextGap(11);
										chkOutProject.addItemListener(new ItemListener() {
											public void itemStateChanged(ItemEvent e) {
												lookupOutProject.setEnabled(e.getStateChange() == ItemEvent.SELECTED);
												lookupOutProject.requestFocus();
											}
										});
									}
									/*{
										JLabel lblOutPrepFrom = new JLabel("Prep. From", JLabel.TRAILING);
										pnlLabels.add(lblOutPrepFrom);
									}
									{
										JLabel lblOutPrepTo = new JLabel("Prep. To", JLabel.TRAILING);
										pnlLabels.add(lblOutPrepTo);
									}*/
								}
								{
									JPanel pnlLookups = new JPanel(new GridLayout(4, 1, 3, 3));
									pnlOutNorthWest.add(pnlLookups, BorderLayout.CENTER);
									pnlLookups.setPreferredSize(new Dimension(75, 0));
									{
										txtOutBatchNo = new _JXTextField("Batch No.");
										pnlLookups.add(txtOutBatchNo);
										txtOutBatchNo.setHorizontalAlignment(JXTextField.CENTER);
									}
									{
										lookupOutProject = new _JLookup(null, "Project", "Please select company.");
										pnlLookups.add(lookupOutProject);
										lookupOutProject.setLookupSQL(SQL_PROJECT());
										lookupOutProject.setEnabled(false);
										lookupOutProject.setReturnColumn(0);
										lookupOutProject.setBounds(90, 40, 50, 25);
										lookupOutProject.addLookupListener(new LookupListener() {
											public void lookupPerformed(LookupEvent event) {//XXX Project
												Object[] data = ((_JLookup)event.getSource()).getDataSet();
												if(data != null){
													txtOutProjectName.setText(data[1].toString());
													manager.focusNextComponent();
												}else{
													txtOutProjectName.setText("");
												}
											}
										});
									}
									/*{
										dateOutPrepFrom = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
										pnlLookups.add(dateOutPrepFrom);
									}
									{
										dateOutPrepTo = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
										pnlLookups.add(dateOutPrepTo);
									}*/
								}
							}
							{
								JPanel pnlOutNorthCenter = new JPanel(new GridLayout(4, 1, 3, 3));
								pnlOutNorth2.add(pnlOutNorthCenter, BorderLayout.CENTER);
								{
									pnlOutNorthCenter.add(Box.createHorizontalGlue());

									txtOutProjectName = new _JXTextField();
									pnlOutNorthCenter.add(txtOutProjectName);
								}
							}
							{
								btnOutGenerate = new JButton("Generate");
								pnlOutNorth2.add(btnOutGenerate, BorderLayout.EAST);
								btnOutGenerate.setActionCommand("Generate Out");
								btnOutGenerate.setPreferredSize(new Dimension(200, 81));
								btnOutGenerate.addActionListener(this);
							}
						}
					}
					{

						scrollOutAccount = new _JScrollPane();
						pnlOut.add(scrollOutAccount, BorderLayout.CENTER);
						scrollOutAccount.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
						scrollOutAccount.addMouseListener(new MouseAdapter() {
							public void mouseClicked(MouseEvent e) {
								tblOutAccount.clearSelection();
							}
						});
						{
							modelOutAccount = new modelSCDM_OutAccounts();
							modelOutAccount.addTableModelListener(new TableModelListener() {
								public void tableChanged(TableModelEvent e) {
									if(e.getType() == TableModelEvent.DELETE){
										rowheaderOutAccount.setModel(new DefaultListModel());
										scrollOutAccount.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblOutAccount.getRowCount())));
									}
									if(e.getType() == TableModelEvent.INSERT){
										((DefaultListModel)rowheaderOutAccount.getModel()).addElement(modelOutAccount.getRowCount());
									}
								}
							});

							tblOutAccount = new _JTableMain(modelOutAccount);
							scrollOutAccount.setViewportView(tblOutAccount);
							tblOutAccount.hideColumns("Sales Group", "Network", "Batch No.", "TR Date" ,"Client's ID", "Project ID", "PBL ID", "Sequence", "Coapplicant", "Loan Amt", "Company Name", "Company Alias", "Proj. Name", "Coordinator", "Sales Dept.", "Sales Person");

							tblOutAccount.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
								public void valueChanged(ListSelectionEvent e) {
									if(!e.getValueIsAdjusting()){//XXX tblJVDetail

									}
								}
							});
						}
						{
							rowheaderOutAccount = tblOutAccount.getRowHeader();
							rowheaderOutAccount.setModel(new DefaultListModel());
							scrollOutAccount.setRowHeaderView(rowheaderOutAccount);
							scrollOutAccount.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
						}
					}
					{
						JPanel pnlOutSouth = new JPanel(new GridLayout(1, 9, 3, 3));
						pnlOut.add(pnlOutSouth, BorderLayout.SOUTH);
						pnlOutSouth.setPreferredSize(new Dimension(0, 30));
						{
							pnlOutSouth.add(Box.createHorizontalBox());
							pnlOutSouth.add(Box.createHorizontalBox());
							pnlOutSouth.add(Box.createHorizontalBox());
							pnlOutSouth.add(Box.createHorizontalBox());
							pnlOutSouth.add(Box.createHorizontalBox());
							pnlOutSouth.add(Box.createHorizontalBox());
							pnlOutSouth.add(Box.createHorizontalBox());
							pnlOutSouth.add(Box.createHorizontalBox());
						}
						/*{
							btnOutPreview = new JButton("Preview");
							pnlOutSouth.add(btnOutPreview);
							btnOutPreview.setActionCommand("Preview Out");
							btnOutPreview.setPreferredSize(new Dimension(100, 0));
							btnOutPreview.addActionListener(this);
						}*/
						{
							btnOutPost = new JButton("Post");
							pnlOutSouth.add(btnOutPost);
							btnOutPost.setActionCommand("Post Out");
							btnOutPost.setPreferredSize(new Dimension(100, 0));
							btnOutPost.addActionListener(this);
						}
					}
				}
				{
					pnlIn = new JPanel(new BorderLayout(3, 3));
					tabCenter.addTab("In", null, pnlIn, null);
					pnlIn.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
					{
						JPanel pnlInNorth1 = new JPanel(new BorderLayout());
						pnlIn.add(pnlInNorth1, BorderLayout.NORTH);
						pnlInNorth1.setBorder(LINE_BORDER);
						{
							JPanel pnlInNorth2 = new JPanel(new BorderLayout(3, 3));
							pnlInNorth1.add(pnlInNorth2, BorderLayout.CENTER);
							pnlInNorth2.setPreferredSize(new Dimension(688, 110));
							pnlInNorth2.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
							{
								JPanel pnlINNorthWest = new JPanel(new BorderLayout(3, 3));
								pnlInNorth2.add(pnlINNorthWest, BorderLayout.WEST);
								pnlINNorthWest.setPreferredSize(new Dimension(200, 81));
								{
									JPanel pnlLabels = new JPanel(new GridLayout(4, 1, 3, 3));
									pnlINNorthWest.add(pnlLabels, BorderLayout.WEST);
									pnlLabels.setPreferredSize(new Dimension(75, 0));
									{
										JLabel lblInCompany = new JLabel("Batch No.");
										pnlLabels.add(lblInCompany);
										lblInCompany.setHorizontalAlignment(JLabel.RIGHT);
									}
									{
										chkInProject = new _JCheckBox("Project");
										pnlLabels.add(chkInProject);
										chkInProject.setIconTextGap(11);
										chkInProject.addItemListener(new ItemListener() {
											public void itemStateChanged(ItemEvent e) {
												lookupInProject.setEnabled(e.getStateChange() == ItemEvent.SELECTED);
												lookupInProject.requestFocus();
											}
										});
									}
									/*{
									JLabel lblOutPrepFrom = new JLabel("Prep. From", JLabel.TRAILING);
									pnlLabels.add(lblOutPrepFrom);
								}
								{
									JLabel lblOutPrepTo = new JLabel("Prep. To", JLabel.TRAILING);
									pnlLabels.add(lblOutPrepTo);
								}*/
								}
								{
									JPanel pnlLookups = new JPanel(new GridLayout(4, 1, 3, 3));
									pnlINNorthWest.add(pnlLookups, BorderLayout.CENTER);
									pnlLookups.setPreferredSize(new Dimension(75, 0));
									{
										txtInBatchNo = new _JXTextField("Batch No.");
										pnlLookups.add(txtInBatchNo);
										txtInBatchNo.setHorizontalAlignment(JXTextField.CENTER);
									}
									{
										lookupInProject = new _JLookup(null, "Project", "Please select company.");
										pnlLookups.add(lookupInProject);
										lookupInProject.setLookupSQL(SQL_PROJECT());
										lookupInProject.setEnabled(false);
										lookupInProject.setReturnColumn(0);
										lookupInProject.setBounds(90, 40, 50, 25);
										lookupInProject.addLookupListener(new LookupListener() {
											public void lookupPerformed(LookupEvent event) {//XXX Project
												Object[] data = ((_JLookup)event.getSource()).getDataSet();
												if(data != null){
													txtInProject.setText(data[1].toString());
													manager.focusNextComponent();
												}else{
													txtInProject.setText("");
												}
											}
										});
									}
									/*{
									dateOutPrepFrom = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
									pnlLookups.add(dateOutPrepFrom);
								}
								{
									dateOutPrepTo = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
									pnlLookups.add(dateOutPrepTo);
								}*/
								}
							}
							{
								JPanel pnlInNorthCenter = new JPanel(new GridLayout(4, 1, 3, 3));
								pnlInNorth2.add(pnlInNorthCenter, BorderLayout.CENTER);
								{
									pnlInNorthCenter.add(Box.createHorizontalGlue());

									txtInProject = new _JXTextField();
									pnlInNorthCenter.add(txtInProject);
								}
							}
							{
								btnInGenerate = new JButton("Generate");
								pnlInNorth2.add(btnInGenerate, BorderLayout.EAST);
								btnInGenerate.setActionCommand("Generate In");
								btnInGenerate.setPreferredSize(new Dimension(200, 81));
								btnInGenerate.addActionListener(this);
							}
						}
					}

					{
						scrollInAccount = new _JScrollPane();
						pnlIn.add(scrollInAccount, BorderLayout.CENTER);
						//scrollInAccount.setBorder(components.JTBorderFactory.createTitleBorder("Sales Confirmation Documents (SCD) In"));
						scrollInAccount.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
						scrollInAccount.addMouseListener(new MouseAdapter() {
							public void mouseClicked(MouseEvent e) {
								tblInAccount.clearSelection();
							}
						});
						{
							modelInAccount = new modelSCDM_InAccounts();
							modelInAccount.addTableModelListener(new TableModelListener() {
								public void tableChanged(TableModelEvent e) {
									if(e.getType() == TableModelEvent.DELETE){
										rowheaderInAccount.setModel(new DefaultListModel());
										scrollInAccount.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblInAccount.getRowCount())));
									}
									if(e.getType() == TableModelEvent.INSERT){
										((DefaultListModel)rowheaderInAccount.getModel()).addElement(modelInAccount.getRowCount());
									}
								}
							});

							tblInAccount = new _JTableMain(modelInAccount);
							scrollInAccount.setViewportView(tblInAccount);
							tblInAccount.hideColumns("Client's ID", "Project ID",	"PBL ID", "Sequence", "Sales Division");

							tblInAccount.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
								public void valueChanged(ListSelectionEvent e) {
									if(!e.getValueIsAdjusting()){//XXX tblJVDetail

									}
								}
							});
						}
						{
							rowheaderInAccount = tblInAccount.getRowHeader();
							rowheaderInAccount.setModel(new DefaultListModel());
							scrollInAccount.setRowHeaderView(rowheaderInAccount);
							scrollInAccount.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
						}
					}
					{
						JPanel pnlOutSouth = new JPanel(new GridLayout(1, 9, 3, 3));
						pnlIn.add(pnlOutSouth, BorderLayout.SOUTH);
						pnlOutSouth.setPreferredSize(new Dimension(0, 30));
						{
							pnlOutSouth.add(Box.createHorizontalBox());
							pnlOutSouth.add(Box.createHorizontalBox());
							pnlOutSouth.add(Box.createHorizontalBox());
							pnlOutSouth.add(Box.createHorizontalBox());
							pnlOutSouth.add(Box.createHorizontalBox());
							pnlOutSouth.add(Box.createHorizontalBox());
							pnlOutSouth.add(Box.createHorizontalBox());
							//pnlOutSouth.add(Box.createHorizontalBox());
						}
						{
							btnInPreview = new JButton("Preview");
							pnlOutSouth.add(btnInPreview);
							btnInPreview.setActionCommand("Preview SCD In");
							btnInPreview.setPreferredSize(new Dimension(100, 0));
							btnInPreview.addActionListener(this);
						}
						{
							btnInPost = new JButton("Post");
							pnlOutSouth.add(btnInPost);
							btnInPost.setActionCommand("Post In");
							btnInPost.setPreferredSize(new Dimension(100, 0));
							btnInPost.addActionListener(this);
						}
					}
				}
				{
					pnlBuyerStatusEditor = new JPanel(new BorderLayout(3, 3));
					tabCenter.addTab("Buyer Status Inactive", null, pnlBuyerStatusEditor, null);
					pnlBuyerStatusEditor.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
					{
						pnlBuyerStatusEditorNorth = new JPanel(new BorderLayout(3, 3));
						pnlBuyerStatusEditor.add(pnlBuyerStatusEditorNorth, BorderLayout.NORTH);
						pnlBuyerStatusEditorNorth.setBorder(JTBorderFactory.createTitleBorder("Client Information"));
						{
							pnlStatusEditorLabel = new JPanel(new GridLayout(5, 1, 3, 3));
							pnlBuyerStatusEditorNorth.add(pnlStatusEditorLabel, BorderLayout.WEST);
							{
								lblClient = new JLabel("Client");
								pnlStatusEditorLabel.add(lblClient);
							}
							{
								lblProject = new JLabel("Project");
								pnlStatusEditorLabel.add(lblProject);
							}
							{
								lblUnit = new JLabel("Unit");
								pnlStatusEditorLabel.add(lblUnit);
							}
							{
								lblHouseModel = new JLabel("House Model");
								pnlStatusEditorLabel.add(lblHouseModel);
							}
							{
								lblDateInactive = new JLabel("Date Inactive");
								pnlStatusEditorLabel.add(lblDateInactive);
							}
						}
						{
							pnlStatusEditorComponent = new JPanel(new GridLayout(5, 1, 3, 3));
							pnlBuyerStatusEditorNorth.add(pnlStatusEditorComponent, BorderLayout.CENTER);
							{
								JPanel pnlClient = new JPanel(new BorderLayout(3, 3));
								pnlStatusEditorComponent.add(pnlClient);
								{
									lookupClient = new _JLookup(null, "Client", 0);
									pnlClient.add(lookupClient, BorderLayout.WEST);
									lookupClient.setFilterName(true);
									lookupClient.setLookupSQL(_SCDMonitoring.sqlClients());
									lookupClient.setPreferredSize(new Dimension(150, 0));
									lookupClient.addLookupListener(new LookupListener() {

										@Override
										public void lookupPerformed(LookupEvent event) {
											Object[] data = ((_JLookup)event.getSource()).getDataSet();

											if(data != null){

												String entity_id = (String) data[0];
												String entity_name = (String) data[1];
												String proj_id = (String) data[2];
												String proj_name = (String) data[3];
												String unit_id = (String) data[4];
												Integer seq_no = (Integer) data[5];
												String unit_desc = (String) data[6];
												String model_id = (String) data[7];
												String model_desc = (String) data[8];

												txtClient.setText(entity_name);
												txtProjID.setText(proj_id);
												txtProjName.setText(proj_name);
												txtUnitID.setText(unit_id);
												txtSeqNo.setText(seq_no.toString());
												txtUnitDesc.setText(unit_desc);
												txtModelID.setText(model_id);
												txtModelDesc.setText(model_desc);

												_SCDMonitoring.displayStatusHistory(modelSCDStatusEditor, entity_id, proj_id, unit_id, seq_no);

											}
										}
									});
								}
								{
									txtClient = new _JXTextField();
									pnlClient.add(txtClient, BorderLayout.CENTER);
								}
							}
							{
								JPanel pnlProject = new JPanel(new BorderLayout(3, 3));
								pnlStatusEditorComponent.add(pnlProject);
								{
									txtProjID = new _JXTextField();
									pnlProject.add(txtProjID, BorderLayout.WEST);
									txtProjID.setPreferredSize(new Dimension(50, 0));
								}
								{
									txtProjName = new _JXTextField();
									pnlProject.add(txtProjName, BorderLayout.CENTER);
								}
							}
							{
								JPanel pnlUnit = new JPanel(new BorderLayout(3, 3));
								pnlStatusEditorComponent.add(pnlUnit);
								{
									JPanel pnlUnitID = new JPanel(new BorderLayout(3, 3));
									pnlUnit.add(pnlUnitID, BorderLayout.WEST);
									pnlUnitID.setPreferredSize(new Dimension(200, 0));
									{
										txtUnitID = new _JXTextField();
										pnlUnitID.add(txtUnitID, BorderLayout.WEST);
										txtUnitID.setPreferredSize(new Dimension(150, 0));
									}
									{
										txtSeqNo = new _JXTextField();
										pnlUnitID.add(txtSeqNo, BorderLayout.CENTER);
									}
								}
								{
									txtUnitDesc = new _JXTextField();
									pnlUnit.add(txtUnitDesc, BorderLayout.CENTER);
								}
							}
							{
								JPanel pnlHouseModel = new JPanel(new BorderLayout(3, 3));
								pnlStatusEditorComponent.add(pnlHouseModel);
								{
									txtModelID = new _JXTextField();
									pnlHouseModel.add(txtModelID, BorderLayout.WEST);
									txtModelID.setPreferredSize(new Dimension(50, 0));
								}
								{
									txtModelDesc = new _JXTextField();
									pnlHouseModel.add(txtModelDesc, BorderLayout.CENTER);
								}
							}
							{
								JPanel pnlDateInactive = new JPanel(new BorderLayout(3, 3));
								pnlStatusEditorComponent.add(pnlDateInactive);
								{
									dteInactive = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
									pnlDateInactive.add(dteInactive, BorderLayout.WEST);
									dteInactive.setPreferredSize(new Dimension(150, 0));
									dteInactive.getCalendarButton().setEnabled(false);
									dteInactive.setEditable(false);
									dteInactive.setDate(Calendar.getInstance().getTime());
								}
								{
									lblStatus = new JLabel("Status", JLabel.TRAILING);
									pnlDateInactive.add(lblStatus);
								}
								{
									cmbStatus = new JComboBox(new String[]{"Active", "Inactive"});
									pnlDateInactive.add(cmbStatus, BorderLayout.EAST);
									cmbStatus.setPreferredSize(new Dimension(150, 0));
								}
							}
						}
					}
					{
						pnlBuyerStatusEditorCenter = new JPanel(new BorderLayout(3, 3));
						pnlBuyerStatusEditor.add(pnlBuyerStatusEditorCenter, BorderLayout.CENTER);
						pnlBuyerStatusEditorCenter.setBorder(JTBorderFactory.createTitleBorder("Buyer Status History"));
						{
							scrollStatusHistory = new _JScrollPane();
							pnlBuyerStatusEditorCenter.add(scrollStatusHistory, BorderLayout.CENTER);
							//scrollStatusHistory.setBorder(components.JTBorderFactory.createTitleBorder("Buyer Status History"));
							scrollStatusHistory.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
							scrollStatusHistory.addMouseListener(new MouseAdapter() {
								public void mouseClicked(MouseEvent e) {
									tblStatusHistory.clearSelection();
								}
							});
							{
								modelSCDStatusEditor = new modelSCDStatusEditor();
								modelSCDStatusEditor.addTableModelListener(new TableModelListener() {
									public void tableChanged(TableModelEvent e) {
										if(e.getType() == TableModelEvent.DELETE){
											rowheaderStatusHistory.setModel(new DefaultListModel());
											scrollStatusHistory.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblStatusHistory.getRowCount())));
										}
										if(e.getType() == TableModelEvent.INSERT){
											((DefaultListModel)rowheaderStatusHistory.getModel()).addElement(modelSCDStatusEditor.getRowCount());
										}
									}
								});

								tblStatusHistory = new _JTableMain(modelSCDStatusEditor);
								scrollStatusHistory.setViewportView(tblStatusHistory);
								tblStatusHistory.setSortable(false);
								//tblStatusHistory.hideColumns("Client's ID", "Project ID",	"PBL ID", "Sequence", "Sales Division");
								tblStatusHistory.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
									public void valueChanged(ListSelectionEvent e) {
										if(!e.getValueIsAdjusting()){

										}
									}
								});
							}
							{
								rowheaderStatusHistory = tblStatusHistory.getRowHeader();
								rowheaderStatusHistory.setModel(new DefaultListModel());
								scrollStatusHistory.setRowHeaderView(rowheaderStatusHistory);
								scrollStatusHistory.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
							}
						}
					}
					{
						pnlBuyerStatusEditorSouth = new JPanel(new GridLayout(1, 4, 3, 3));
						pnlBuyerStatusEditor.add(pnlBuyerStatusEditorSouth, BorderLayout.SOUTH);
						pnlBuyerStatusEditorSouth.setPreferredSize(new Dimension(0, 30));
						{
							pnlBuyerStatusEditorSouth.add(Box.createHorizontalBox());
							pnlBuyerStatusEditorSouth.add(Box.createHorizontalBox());
						}
						{
							btnSave = new JButton("Save");
							pnlBuyerStatusEditorSouth.add(btnSave);
							btnSave.setActionCommand("Save");
							btnSave.setPreferredSize(new Dimension(100, 0));
							btnSave.addActionListener(this);
						}
					}
				}
			}
		}
	}//XXX END OF INIT GUI

	private void displayPreparationAccounts() {
		if(chkPrepProject.isSelected() && lookupPrepProject.getValue() == null){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select project.", "Post", JOptionPane.WARNING_MESSAGE);
			return;
		}

		/*if(datePrepORFrom.getDate() == null){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select TR From.", "Post", JOptionPane.WARNING_MESSAGE);
			return;
		}

		if(datePrepORTo.getDate() == null){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select TR To.", "Post", JOptionPane.WARNING_MESSAGE);
			return;
		}*/

		_SCDMonitoring.displayPreparation(modelPrepAccount, chkPrepProject.isSelected(), lookupPrepProject.getValue(), null, null);
		scrollPrepAccount.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPrepAccount.getRowCount())));
		tblPrepAccount.packAll();
	}

	private void generatePreparationAccounts(){

		//PUT PREPARATION OF ACCOUNTS HERE

		Map<String, Object> mapParametersCIS = new HashMap<String, Object>();

		mapParametersCIS.put("proj_name", txtPrepProjectName.getText());
		mapParametersCIS.put("filter_project", chkPrepProject.isSelected());
		/*mapParametersCIS.put("tr_from", datePrepORFrom.getDate());
		mapParametersCIS.put("tr_to", datePrepORTo.getDate());*/

		mapParametersCIS.put("proj_id", lookupPrepProject.getValue());

		FncReport.generateReport("/Reports/rptSCDMonitoring_CIS.jasper", "Client Information Sheet", mapParametersCIS);

		Map<String, Object> mapParameters_SCD_Prep = new HashMap<String, Object>();
		mapParameters_SCD_Prep.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ "cenq_logo.jpg"));
		mapParameters_SCD_Prep.put("filter_project", chkPrepProject.isSelected());
		mapParameters_SCD_Prep.put("project_id", lookupPrepProject.getValue());
		/*mapParameters_SCD_Prep.put("tr_from", datePrepORFrom.getDate());
		mapParameters_SCD_Prep.put("tr_to", datePrepORTo.getDate());*/
		mapParameters_SCD_Prep.put("prepared_by", UserInfo.Alias);

		//CHECK HERE FUNCTION OF GENERATION
		FncReport.generateReport("/Reports/rptListOfClientsForSCDPreparation.jasper", "List of Clients For SCD Preparation", mapParameters_SCD_Prep);
	}

	private void generateSCDIn(){

		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ "cenq_logo.jpg"));
		mapParameters.put("filter_project", chkInProject.isSelected());
		mapParameters.put("proj_id", lookupInProject.getValue());

		FncReport.generateReport("/Reports/rptListOfClientsForSCDIn.jasper", "List of Clients Tagged SCD In", mapParameters);
	}

	private void displayOutAccounts() {

		if(chkOutProject.isSelected() && lookupOutProject.getValue() == null){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select project.", "Post", JOptionPane.WARNING_MESSAGE);
			return;
		}

		/*if(dateOutPrepFrom.getDate() == null){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select Prep. From.", "Post", JOptionPane.WARNING_MESSAGE);
			return;
		}

		if(dateOutPrepTo.getDate() == null){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select Prep. To.", "Post", JOptionPane.WARNING_MESSAGE);
			return;
		}*/

		_SCDMonitoring.displayOut(modelOutAccount, chkOutProject.isSelected(), lookupOutProject.getValue(), null, null);
		scrollOutAccount.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblOutAccount.getRowCount())));
		tblOutAccount.packAll();
	}

	private void displayInAccounts() {
		_SCDMonitoring.displayIn(modelInAccount, chkInProject.isSelected(), lookupInProject.getValue());
		scrollInAccount.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblInAccount.getRowCount())));
		tblInAccount.packAll();
	}

	@Override
	public void actionPerformed(ActionEvent e) {//XXX actionCommand
		String action = e.getActionCommand();

		if(action.equals("Generate Preparation")){
			displayPreparationAccounts();
		}

		if(action.equals("Generate Out")){
			displayOutAccounts();
		}
		
		if(action.equals("Generate In")){
			displayInAccounts();
		}

		if(action.equals("Preview Preparation")){
			//if(modelPrepAccount.getRowCount() != 0){
			generatePreparationAccounts();
			/*}else{
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please generate list to preview", "Preview", JOptionPane.WARNING_MESSAGE);
			}*/
		}

		if(action.equals("Post Preparation")){//XXX
			if(chkPrepProject.isSelected() && lookupPrepProject.getValue() == null && datePrepORFrom.getDate() == null && datePrepORTo.getDate() == null){
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select project.", "Post", JOptionPane.WARNING_MESSAGE);
				return;
			}

			ArrayList<Boolean> listSelected = new ArrayList<Boolean>();
			for(int x=0; x < modelPrepAccount.getRowCount(); x++){
				Boolean selected = (Boolean) modelPrepAccount.getValueAt(x, 0);
				listSelected.add(selected);
			}

			if(listSelected.contains(true)){
				if (JOptionPane.showConfirmDialog(this.getTopLevelAncestor(), "Are all entries correct?", "Save", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
					_SCDMonitoring.savePrep(modelPrepAccount);

					JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Client(s) has been tagged.", "Save", JOptionPane.INFORMATION_MESSAGE);

					/*Map<String, Object> mapParam = new HashMap<String, Object>();
					mapParam.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ "cenq_logo.jpg"));
					mapParam.put("project_id", lookupPrepProject.getValue());
					mapParam.put("project_name", txtPrepProjectName.getText());
					mapParam.put("prepared_by", UserInfo.Alias);

					FncReport.generateReport("/Reports/rptListOfClientsForSCDPreparation.jasper", "List of Clients For SCD Preparation", mapParam);*/

					displayPreparationAccounts();
				}
			}else{
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select account to post.", "Post", JOptionPane.WARNING_MESSAGE);
			}
		}

		if(action.equals("Post Out")){
			if(chkOutProject.isSelected() && lookupOutProject.getValue() == null && dateOutPrepFrom.getDate() == null && dateOutPrepTo.getDate() == null){
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select project.", "Post", JOptionPane.WARNING_MESSAGE);
				return;
			}

			ArrayList<Boolean> listSelected = new ArrayList<Boolean>();
			for(int x=0; x < modelOutAccount.getRowCount(); x++){
				Boolean selected = (Boolean) modelOutAccount.getValueAt(x, 0);
				listSelected.add(selected);
			}

			if(listSelected.contains(true)){
				if (JOptionPane.showConfirmDialog(this.getTopLevelAncestor(), "Are all entries correct?", "Save", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
					_SCDMonitoring.saveOut(modelOutAccount);

					displayOutAccounts();
				}
			}else{
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select account to post.", "Post", JOptionPane.WARNING_MESSAGE);
			}
		}

		if(action.equals("Preview SCD In")){
			generateSCDIn();
		}

		if(action.equals("Post In")){
			ArrayList<Boolean> listSelected = new ArrayList<Boolean>();
			for(int x=0; x < modelInAccount.getRowCount(); x++){
				Boolean selected = (Boolean) modelInAccount.getValueAt(x, 0);
				listSelected.add(selected);
			}

			if(listSelected.contains(true)){
				if (JOptionPane.showConfirmDialog(this.getTopLevelAncestor(), "Are all entries correct?", "Save", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
					_SCDMonitoring.saveIn(modelInAccount);

					displayInAccounts();
				}
			}else{
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select account to post.", "Post", JOptionPane.WARNING_MESSAGE);
			}
		}

		if(action.equals("Save")){
			System.out.println("Dumaan dito sa save");
			String status_id = cmbStatus.getSelectedItem().equals("Active") ? "A":"I";

			_SCDMonitoring.saveInactiveStatus(modelSCDStatusEditor, lookupClient.getValue(), txtProjID.getText(), txtUnitID.getText(), txtSeqNo.getText(), status_id);

			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Status successfully updated", "Save", JOptionPane.INFORMATION_MESSAGE);

			_SCDMonitoring.displayStatusHistory(modelSCDStatusEditor, lookupClient.getValue(), txtProjID.getText(), txtUnitID.getText(), Integer.valueOf(txtSeqNo.getText()));


		}
	}

}
