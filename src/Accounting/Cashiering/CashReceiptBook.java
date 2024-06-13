package Accounting.Cashiering;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXLabel;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.JXTextField;
import org.jdesktop.swingx.JXTitledSeparator;

import tablemodel.modelCRBAccountEntries;
import Accounting.Collections.CheckStatusMonitoring;
import Database.pgSelect;
import Database.pgUpdate;
import DateChooser._JDateChooser;
import Functions.FncAcounting;
import Functions.FncFocusTraversalPolicy;
import Functions.FncGlobal;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import Lookup._JLookupTable;
import components._JInternalFrame;
import components._JRadioButton;
import components._JScrollPaneMain;
import components._JScrollPaneTotal;
import components._JTableMain;
import components._JTableTotal;

public class CashReceiptBook extends _JInternalFrame {

	private static final long serialVersionUID = 1L;

	private static String title = "Receipt Details (CRB)";
	private Dimension frameSize = new Dimension(806, 600);
	static Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	static Cursor handCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);

	private JXPanel pnlMain;
	private JXPanel pnlNorth;
	private JXPanel pnlSouth;	
	private JXPanel pnlSouthEast;
	private JXPanel pnlCenter;
	private JXPanel pnlSouthWest;	

	private JXLabel lblCompany;
	public static JXLabel lblProject;
	private static JXLabel lblSequence;
	public static JXLabel lblPhase;
	public static JXLabel lblReceipt;
	public static JXLabel lblReceiptType;
	private static JXLabel lblClient;
	private static JXLabel lblUnit;
	private static JXLabel lblReceiptDate;	
	private static JXLabel lblStatus;

	public static _JLookup lookupProject;
	public static _JLookup lookupPhase;
	public static _JLookup lookupReceipt;
	public static _JLookup lookupCompany;

	public static JXTextField txtProject;
	public static JXTextField txtSequence;	
	public static JXTextField txtReceipt;	
	public static JXTextField txtPhase;	
	public static JXTextField txtClientID;
	public static JXTextField txtClientName;	
	public static JXTextField txtUnitID;
	public static JXTextField txtUnitDescription;	
	public static JXTextField txtStatus;
	public static JXTextField txtCompany;

	public static _JRadioButton rbtnOR;
	public static _JRadioButton rbtnAR;

	private JXTitledSeparator tsClient;
	private JXTitledSeparator tsReceipt;
	private JXTitledSeparator sepReceipt;	

	public static JButton btnExpand;	
	private static JButton btnCancel;
	private static JButton btnSetInactive;
	private static JButton btnSetActive;
	private static JButton btnEdit;
	private static JButton btnSave;

	public static _JDateChooser dateReceiptDate;

	private ButtonGroup btngrpReceipt = new ButtonGroup();

	private _JScrollPaneMain scrollMainCenter;
	public static _JScrollPaneTotal scrollMainSouth;
	public static _JTableMain _tblMain;
	private _JTableTotal _tblTotal;
	public static modelCRBAccountEntries _modelMain;
	public static modelCRBAccountEntries _modelTotal;
	public static JList rowHeader;	

	private JPopupMenu menu;
	private JMenuItem mnidelete;
	private JMenuItem mniaddrow;

	protected static Font dialog11Bold = new Font("DIALOG", Font.BOLD, 11);

	private ArrayList<Object[]> originalEntries = new ArrayList<Object[]>();

	String entity_id= "";
	String co_id 	= "";
	String proj_id 	= "";
	String branch_id= "";
	String rec_id 	= "";
	String crb_no 	= "";
	String pbl_id 	= "";
	Integer seq_no 	= 0;

	String rb_fiscal_yr = "";
	String rb_month	= "";
	String ar_no	= "";
	String proj		= "";
	String phase	= "";
	String doc_id 	= "";
	String company 	= "";

	private _JRadioButton rbtnSI;

	public CashReceiptBook() {
		super(title, true, true, true, true);
		initGUI();
	}

	public CashReceiptBook(String receipt_type, String receipt_no, String pbl_id, Integer seq_no) {
		super(title, true, true, true, true);
		initGUI();

		displayReceiptDetails(receipt_type, receipt_no, pbl_id, seq_no);
	}

	public CashReceiptBook(String title) {
		super(title);
		initGUI();
	}

	public CashReceiptBook(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	private void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		this.setPreferredSize(frameSize);
		this.setLayout(new BorderLayout());
		{
			menu = new JPopupMenu("Popup");
			mnidelete = new JMenuItem("Remove Row      ");
			mniaddrow = new JMenuItem("Add Row");
			menu.add(mnidelete);
			menu.add(mniaddrow);	
			mnidelete.setEnabled(false);
			mniaddrow.setEnabled(false);

			mnidelete.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					removeRow();				
				}
			});
			mniaddrow.addActionListener(new ActionListener() {
				public void	actionPerformed(ActionEvent evt) {
					AddRow();
				}
			});
		}
		{
			pnlMain = new JXPanel();
			getContentPane().add(pnlMain, BorderLayout.CENTER);
			pnlMain.setLayout(new BorderLayout(5, 5));
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			{
				pnlNorth = new JXPanel(new BorderLayout(5, 5));
				pnlMain.add(pnlNorth, BorderLayout.NORTH);
				pnlNorth.setLayout(null);
				pnlNorth.setPreferredSize(new java.awt.Dimension(794, 247));
				pnlNorth.setBorder(components.JTBorderFactory.createTitleBorder("Details"));
				{
					{
						lblCompany = new JXLabel("Company");
						pnlNorth.add(lblCompany);
						lblCompany.setBounds(9, 28, 76, 25);
						lblCompany.setPreferredSize(new java.awt.Dimension(76, 25));
					}
					{
						lookupCompany = new _JLookup("", "Company");
						pnlNorth.add(lookupCompany);
						lookupCompany.setReturnColumn(0);
						lookupCompany.setLookupSQL(SQL_COMPANY());
						lookupCompany.setBounds(85, 28, 50, 25);
						lookupCompany.setPreferredSize(new java.awt.Dimension(50, 25));
						lookupCompany.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {//XXX Project
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){

									co_id = (String) data[0];	
									company		= (String) data[1];	
									txtCompany.setText(company);	
									lblProject.setEnabled(true);
									lookupProject.setEnabled(true);
									txtProject.setEnabled(true);	
									lookupProject.setLookupSQL(CheckStatusMonitoring.sql_project(co_id));

									enableReceiptLookup(true);
									lookupReceipt.setValue("");
									refreshSubFields();
									enableButtons(false,false,false,false,false,false);
									refresh_table_main();

								}else{
									txtCompany.setText("");
								}

							}
						});
					}
					{
						txtCompany = new JXTextField("");
						pnlNorth.add(txtCompany);
						txtCompany.setEditable(false);
						txtCompany.setBounds(141, 28, 634, 25);
						txtCompany.setPreferredSize(new java.awt.Dimension(634, 25));
					}
					{
						lblProject = new JXLabel("Project");
						pnlNorth.add(lblProject);
						lblProject.setEnabled(false);
						lblProject.setBounds(9, 58, 76, 25);
					}
					{
						lookupProject = new _JLookup("", "Project");
						pnlNorth.add(lookupProject);
						lookupProject.setReturnColumn(0);
						lookupProject.setEnabled(false);						
						lookupProject.setBounds(85, 58, 50, 25);
						lookupProject.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {//XXX Project
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){
									txtProject.setText(data[1].toString());
									lookupPhase.setLookupSQL(FncGlobal.getPhase(data[0].toString()));
									KEYBOARD_MANAGER.focusNextComponent();
									enablePhase(true);
									lookupReceipt.setValue("");
									refreshSubFields();
									enableButtons(false,false,false,false,false,false);
									refresh_table_main();
									lookupPhase.setValue("");
									txtPhase.setText("");

									enableReceiptLookup(true);
									lookupReceipt.setValue("");
									refreshSubFields();
									enableButtons(false,false,false,false,false,false);
									refresh_table_main();
								}else{
									txtProject.setText("");
								}

							}
						});
					}
					{
						txtProject = new JXTextField("");
						pnlNorth.add(txtProject);
						txtProject.setEditable(false);
						txtProject.setEnabled(false);
						txtProject.setBounds(141, 58, 634, 25);
					}
					{
						lblPhase = new JXLabel("Phase");
						pnlNorth.add(lblPhase);
						lblPhase.setEnabled(false);
						lblPhase.setBounds(9, 88, 76, 25);
					}
					{
						lookupPhase = new _JLookup("", "Phase");
						pnlNorth.add(lookupPhase);
						lookupPhase.setReturnColumn(0);
						lookupPhase.setEnabled(false);
						lookupPhase.setBounds(85, 88, 50, 25);
						lookupPhase.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {//XXX Phase
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){
									txtPhase.setText(data[1].toString());
									KEYBOARD_MANAGER.focusNextComponent(rbtnOR);
									enableReceiptLookup(true);
									lookupReceipt.setValue("");
									refreshSubFields();
									enableButtons(false,false,false,false,false,false);
									refresh_table_main();
								}else{
									JOptionPane.showMessageDialog(FncGlobal.homeMDI, "Please select project first.", "Phase", JOptionPane.WARNING_MESSAGE);
								}
							}
						});
					}
					{
						txtPhase = new JXTextField("");
						pnlNorth.add(txtPhase);
						txtPhase.setEditable(false);
						txtPhase.setEnabled(false);
						txtPhase.setBounds(141, 88, 120, 25);
					}
					{
						tsReceipt = new JXTitledSeparator("Receipt");
						tsReceipt.setBounds(7, 89, 430, 15);
						tsReceipt.setEnabled(false);
					}
					{
						lblReceipt = new JXLabel("Receipt");
						pnlNorth.add(lblReceipt);
						lblReceipt.setBounds(7, 152, 76, 25);
						lblReceipt.setEnabled(true);
					}
					{
						lookupReceipt = new _JLookup("", "Receipt");
						pnlNorth.add(lookupReceipt);
						lookupReceipt.setBounds(83, 152, 100, 25);
						lookupReceipt.setEnabled(true);
						lookupReceipt.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {//XXX Receipt
								_JLookup lookup = (_JLookup) event.getSource();
								Object[] data = lookup.getDataSet();

								refreshSubFields();

								if(data != null)
								{
									crb_no = (String) data[0];
									String particular = (String) data[2];
									pbl_id = "";
									seq_no = null;
									enableReceiptDtls(true);
									enableButtons(true, true, false, false, false, false);

									try { pbl_id = (String) data[5];} 
									catch (NullPointerException e) { pbl_id 	= ""; }									
									String description = (String) data[6];
									try { seq_no = Integer.parseInt(data[7].toString());} 
									catch (NullPointerException e) {  seq_no = null; }		

									String entity_id = (String) data[8];
									String entity_name = (String) data[9];
									String status = (String) data[10];

									if (status.trim().equals("ACTIVE")) {
										enableButtons(true, true, false, true, true, false);
									} else if (status.trim().equals("POSTED")) {
										//enableButtons(true, true, false, false, false, false);
										enableButtons(true, true, true, true, true, true);
									} else if (status.trim().equals("INACTIVE")) {
										enableButtons(true, true, true, false, false, false);
									}								

									txtReceipt.setText((String) data[1]);
									dateReceiptDate.setDate((Date) data[4]);
									txtStatus.setText(status);
									
									try {
										if(particular.equals("LNREL")){
											txtUnitID.setText("");
											txtUnitDescription.setText("");
											txtSequence.setText("");
											txtClientID.setText("0000000921");
											txtClientName.setText("PAG-IBIG FUND");
										}else{
											txtUnitID.setText(pbl_id);
											txtUnitDescription.setText(description);
											txtSequence.setText(seq_no.toString());
											txtClientID.setText(entity_id);
											txtClientName.setText(entity_name);
										}	
									} catch(NullPointerException ex) {
										System.out.println("The particular field is null; ");
										
										pbl_id = ""; 
										description = ""; 
										seq_no = 0; 
										entity_id = ""; 
										entity_name = ""; 
									} finally {
										
									}
									
									System.out.println("");
									System.out.println("Entries");
									
									originalEntries = new ArrayList<Object[]>();
									_CashRecieptBooks.displayAccountEntries(lookup.getActionCommand(), _modelMain, _modelTotal, rowHeader, pbl_id, seq_no.toString(), co_id.toString(),  crb_no, originalEntries, false);
									scrollMainSouth.setRowHeaderView(FncTables.getRowHeader_Footer(Integer.toString(_tblMain.getRowCount())));
									_tblMain.packColumns(/*"Entry #", */"Particulars", "Account ID", "Account Name", "Debit", "Credit", "Remarks", "Rec. ID");
									btnExpand.setText("Group Entries");
									btnExpand.setActionCommand("Collapse");
									btnExpand.setDisplayedMnemonicIndex(6);
									FncSystem.out("Display SQL for Lookup", lookupReceipt.getLookupSQL());
								}
								else
								{
									JOptionPane.showMessageDialog(FncGlobal.homeMDI, "Please select Phase.", "Receipt", JOptionPane.WARNING_MESSAGE);
								}
							}
						});
						lookupReceipt.addFocusListener(new FocusAdapter() {
							public void focusGained(FocusEvent e) {
								_JLookup lookup = (_JLookup) e.getSource();

								lookup.setActionCommand(getReceiptType());
								lookup.setReturnColumn(0);
								//Modified by Mann2x; Date Modified: January 16, 2017; Added filter for company;
								//lookup.setLookupSQL(_CashRecieptBooks.getReceiptSQL(lookup.getActionCommand(), lookupProject.getText(), lookupPhase.getText()));
								lookup.setLookupSQL(_CashRecieptBooks.getReceiptSQL(lookup.getActionCommand(), lookupCompany.getText(), lookupProject.getText(), lookupPhase.getText()));
							}
						});
					}
					{
						txtReceipt = new JXTextField();
						pnlNorth.add(txtReceipt);
						txtReceipt.setEditable(false);
						txtReceipt.setBounds(189, 152, 313, 25);
						txtReceipt.setEnabled(false);
					}
					{
						lblReceiptDate = new JXLabel("Receipt Date");
						pnlNorth.add(lblReceiptDate);
						lblReceiptDate.setEnabled(false);
						lblReceiptDate.setHorizontalAlignment(JLabel.RIGHT);
						lblReceiptDate.setBounds(510, 152, 120, 25);
					}
					{
						dateReceiptDate = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
						pnlNorth.add(dateReceiptDate);
						dateReceiptDate.setEditable(false);
						dateReceiptDate.setEnabled(false);
						dateReceiptDate.getCalendarButton().setVisible(false);
						dateReceiptDate.setBounds(635, 152, 138, 25);
					}
					{
						tsClient = new JXTitledSeparator("Client");
						tsClient.setBounds(7, 193, 430, 15);
					}
					{
						lblUnit = new JXLabel("Unit");
						pnlNorth.add(lblUnit);
						lblUnit.setBounds(7, 180, 76, 25);
						lblUnit.setEnabled(false);
					}
					{
						txtUnitID = new JXTextField("");
						pnlNorth.add(txtUnitID);
						txtUnitID.setEditable(false);
						txtUnitID.setHorizontalAlignment(JTextField.CENTER);
						txtUnitID.setBounds(83, 180, 100, 25);
						txtUnitID.setEnabled(false);
					}
					{
						txtUnitDescription = new JXTextField("");
						pnlNorth.add(txtUnitDescription);
						txtUnitDescription.setEditable(false);
						txtUnitDescription.setEnabled(false);
						txtUnitDescription.setBounds(189, 180, 158, 25);
					}
					{
						lblSequence = new JXLabel("Seq. No");
						pnlNorth.add(lblSequence);
						lblSequence.setHorizontalAlignment(JLabel.CENTER);
						lblSequence.setBounds(355, 180, 60, 25);
						lblSequence.setEnabled(false);
					}
					{
						txtSequence = new JXTextField("");
						pnlNorth.add(txtSequence);
						txtSequence.setEditable(false);
						txtSequence.setEnabled(false);
						txtSequence.setHorizontalAlignment(JTextField.CENTER);
						txtSequence.setBounds(422, 180, 80, 25);
					}
					{
						lblStatus = new JXLabel("Status");
						pnlNorth.add(lblStatus);
						lblStatus.setHorizontalAlignment(JLabel.RIGHT);
						lblStatus.setBounds(510, 180, 120, 25);
						lblStatus.setEnabled(false);
					}
					{
						txtStatus = new JXTextField("");
						pnlNorth.add(txtStatus);
						txtStatus.setEditable(false);
						txtStatus.setEnabled(false);
						txtStatus.setHorizontalAlignment(JTextField.CENTER);
						txtStatus.setBounds(635, 180, 138, 25);
					}
					{
						lblClient = new JXLabel("Client");
						pnlNorth.add(lblClient);
						lblClient.setBounds(8, 210, 75, 25);
						lblClient.setEnabled(false);
					}
					{
						txtClientID = new JXTextField("");
						pnlNorth.add(txtClientID);
						txtClientID.setEditable(false);
						txtClientID.setEnabled(false);
						txtClientID.setHorizontalAlignment(JTextField.CENTER);
						txtClientID.setBounds(83, 209, 100, 25);
					}
					{
						txtClientName = new JXTextField("");
						pnlNorth.add(txtClientName);
						txtClientName.setEditable(false);
						txtClientName.setEnabled(false);
						txtClientName.setBounds(189, 209, 584, 25);
					}
					{
						lblReceiptType = new JXLabel("Rcpt. Type");
						pnlNorth.add(lblReceiptType);
						lblReceiptType.setEnabled(true);
						lblReceiptType.setBounds(7, 121, 76, 25);
					}
					{
						rbtnAR = new _JRadioButton("AR");
						pnlNorth.add(rbtnAR);
						rbtnAR.setActionCommand("AR");
						rbtnAR.setMnemonic(KeyEvent.VK_A);
						rbtnAR.setCursor(handCursor);
						rbtnAR.setBounds(83, 121, 50, 25);
						btngrpReceipt.add(rbtnAR);
						rbtnAR.setEnabled(true);
						rbtnAR.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								_JRadioButton radioButton = (_JRadioButton) e.getSource();
								btngrpReceipt.setSelected(radioButton.getModel(), true);
								btngrpReceipt.getSelection().setActionCommand(radioButton.getActionCommand());
								KEYBOARD_MANAGER.focusNextComponent(rbtnOR);
								lookupReceipt.setValue("");
								refreshSubFields();
								enableButtons(false,false,false,false,false,false);
								refresh_table_main();
							}
						});
					}
					{
						rbtnOR = new _JRadioButton("OR");
						pnlNorth.add(rbtnOR);
						rbtnOR.setActionCommand("OR");
						rbtnOR.setMnemonic(KeyEvent.VK_O);
						rbtnOR.setCursor(handCursor);
						rbtnOR.setBounds(133, 121, 50, 25);
						btngrpReceipt.add(rbtnOR);
						rbtnOR.setEnabled(true);
						rbtnOR.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								_JRadioButton radioButton = (_JRadioButton) e.getSource();
								btngrpReceipt.setSelected(radioButton.getModel(), true);
								btngrpReceipt.getSelection().setActionCommand(radioButton.getActionCommand());
								KEYBOARD_MANAGER.focusNextComponent();
								lookupReceipt.setValue("");
								refreshSubFields();
								enableButtons(false,false,false,false,false,false);
								refresh_table_main();
							}
						});
					}
					{ /*Added by Monique dtd 10-11-2022; FOR Sales Invoice*/
						rbtnSI = new _JRadioButton("SI");
						pnlNorth.add(rbtnSI);
						rbtnSI.setActionCommand("SI");
						rbtnSI.setMnemonic(KeyEvent.VK_S);
						rbtnSI.setCursor(handCursor);
						rbtnSI.setBounds(183, 121, 50, 25);
						btngrpReceipt.add(rbtnSI);
						rbtnSI.setEnabled(true);
						rbtnSI.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								_JRadioButton radioButton = (_JRadioButton) e.getSource();
								btngrpReceipt.setSelected(radioButton.getModel(), true);
								btngrpReceipt.getSelection().setActionCommand(radioButton.getActionCommand());
								KEYBOARD_MANAGER.focusNextComponent();
								lookupReceipt.setValue("");
								refreshSubFields();
								enableButtons(false,false,false,false,false,false);
								refresh_table_main();
							}
						});
					}
					{
						sepReceipt = new JXTitledSeparator("");
						pnlNorth.add(sepReceipt);
						sepReceipt.setBounds(233, 121, 540, 25);
					}	
				}
			}
			{
				pnlCenter = new JXPanel();
				pnlMain.add(pnlCenter, BorderLayout.CENTER);
				pnlCenter.setLayout(new BorderLayout());
				pnlCenter.setPreferredSize(new java.awt.Dimension(794, 305));
				pnlCenter.setBorder(components.JTBorderFactory.createTitleBorder("Account Entries"));
				{
					scrollMainCenter = new _JScrollPaneMain();
					pnlCenter.add(scrollMainCenter, BorderLayout.CENTER);
					{
						_modelMain = new modelCRBAccountEntries();
						_tblMain = new _JTableMain(_modelMain);
						scrollMainCenter.setViewportView(_tblMain);

						_tblMain.addMouseListener(new PopupTriggerListener_panel());
						_tblMain.addMouseListener(new MouseAdapter() {
							public void mousePressed(MouseEvent e) {
								if(_tblMain.rowAtPoint(e.getPoint()) == -1){
									_tblTotal.clearSelection();
								}else{
									_tblMain.setCellSelectionEnabled(true);
								}								
								if ((e.getClickCount() >= 2) && _tblMain.isEnabled() && _tblMain.getSelectedColumn() == 1 ) {
									clickTableColumn();
								}
							}
							public void mouseReleased(MouseEvent e) {
								if(_tblMain.rowAtPoint(e.getPoint()) == -1){
									_tblTotal.clearSelection();
								}else{
									_tblMain.setCellSelectionEnabled(true);
								}
								if ((e.getClickCount() >= 2) && _tblMain.isEnabled()  && _tblMain.getSelectedColumn() == 1) {
									clickTableColumn();
								}
							}
						});

						_tblMain.addKeyListener(new KeyAdapter() {
							public void keyReleased(KeyEvent evt) {
								computeCRB_amount();
							}							
							public void keyPressed(KeyEvent e) {
								computeCRB_amount();
							}

						}); 
					}
					{
						rowHeader = _tblMain.getRowHeader();
						scrollMainCenter.setRowHeaderView(rowHeader);
						scrollMainCenter.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
					}
				}
				{
					scrollMainSouth = new _JScrollPaneTotal(scrollMainCenter);
					pnlCenter.add(scrollMainSouth, BorderLayout.SOUTH);
					{
						_modelTotal = new modelCRBAccountEntries();
						_modelTotal.addRow(new Object[] { null, null, "Total", 0.00, 0.00 });

						_tblTotal = new _JTableTotal(_modelTotal, _tblMain);
						scrollMainSouth.setViewportView(_tblTotal);
						_tblTotal.setFont(dialog11Bold);
						_tblTotal.setTotalLabel(2);

						/*********************** Setting Total table for Main table ***********************/
						//_tblMain.setTotalTable(_tblTotal);
					}
				}
			}
			{
				pnlSouth = new JXPanel();
				pnlMain.add(pnlSouth, BorderLayout.SOUTH);
				pnlSouth.setLayout(new BorderLayout());
				pnlSouth.setPreferredSize(new Dimension(798, 30));
				{
					pnlSouthWest = new JXPanel();
					pnlSouth.add(pnlSouthWest, BorderLayout.WEST);
					pnlSouthWest.setLayout(new GridLayout(1, 1, 5, 5));
					pnlSouthWest.setPreferredSize(new java.awt.Dimension(359, 30));

					{
						btnExpand = new JButton("Ungroup Entries");
						pnlSouthWest.add(btnExpand);
						btnExpand.setCursor(handCursor);
						btnExpand.setEnabled(false);
						btnExpand.setActionCommand("Collapse");
						btnExpand.setMnemonic(KeyEvent.VK_U);
						btnExpand.setDisplayedMnemonicIndex(8);
						btnExpand.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {//XXX Expand
								JButton button = (JButton) e.getSource();
								if(button.getActionCommand().equals("Collapse")){
									System.out.println("*****Collapse");
									_CashRecieptBooks.collapseAccountEntries(_modelMain, _modelTotal, rowHeader);

									button.setText("Ungroup Entries");
									button.setActionCommand("Expand");
									button.setDisplayedMnemonicIndex(8);

									btnEdit.setEnabled(false);	
									btnSave.setEnabled(false);
									mnidelete.setEnabled(false);
									mniaddrow.setEnabled(false);
									btnExpand.setToolTipText("Ungroup entries to enable editing of entries");

								}else{
									System.out.println("*****Expand");
									_CashRecieptBooks.expandAccountEntries(_modelMain, _modelTotal, rowHeader, originalEntries);

									button.setText("Group Entries");
									button.setActionCommand("Collapse");
									button.setDisplayedMnemonicIndex(6);

									if (txtStatus.getText().trim().equals("ACTIVE"))
									{
										btnEdit.setEnabled(true);	
										btnSave.setEnabled(false);
										mnidelete.setEnabled(false);
										mniaddrow.setEnabled(false);
										btnExpand.setToolTipText("Ungroup entries to enable editing of entries");
									}
									else 
									{
										btnEdit.setEnabled(false);	
										btnSave.setEnabled(false);
										mnidelete.setEnabled(false);
										mniaddrow.setEnabled(false);
										btnExpand.setToolTipText("");
									}
								}

								if(_tblMain.getRowCount() > 0){
									scrollMainSouth.setRowHeaderView(FncTables.getRowHeader_Footer(Integer.toString(_tblMain.getRowCount())));
									_tblMain.packColumns(/*"Entry #", */"Particulars", "Account ID", "Account Name", "Debit", "Credit", "Remarks", "Rec. ID");
								}
							}
						});
					}
					{
						btnEdit = new JButton("Edit");
						pnlSouthWest.add(btnEdit);
						btnEdit.setCursor(handCursor);
						btnEdit.setEnabled(false);
						btnEdit.setMnemonic(KeyEvent.VK_E);
						btnEdit.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {

								_modelMain.setEditable(true);
								_tblMain.setEditable(true);
								btnEdit.setEnabled(false);	
								btnSave.setEnabled(true);

								mnidelete.setEnabled(true);
								mniaddrow.setEnabled(true);
							}
						});
					}
					{
						btnSave = new JButton("Save");
						pnlSouthWest.add(btnSave);
						btnSave.setCursor(handCursor);
						btnSave.setEnabled(false);
						btnSave.setMnemonic(KeyEvent.VK_S);
						btnSave.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								saveCRB();								
							}
						});
					}
				}
				{
					pnlSouthEast = new JXPanel();
					pnlSouth.add(pnlSouthEast, BorderLayout.EAST);
					pnlSouthEast.setLayout(new GridLayout(1, 2, 5, 5));
					pnlSouthEast.setPreferredSize(new java.awt.Dimension(367, 30));
					{
						btnSetInactive = new JButton("Cancel Receipt");
						pnlSouthEast.add(btnSetInactive);
						btnSetInactive.setCursor(handCursor);
						btnSetInactive.setEnabled(false);
						btnSetInactive.setMnemonic(KeyEvent.VK_R);
						btnSetInactive.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {

								if (FncAcounting.EmpCanApprove(UserInfo.EmployeeCode, "16")==true)
								{
									cancelCRB();
								}
								else if (FncAcounting.EmpCanApprove(UserInfo.EmployeeCode, "16")==false ) 
								{ JOptionPane.showMessageDialog(getContentPane(),"Sorry, you are not authorized to cancel receipt.","Information",JOptionPane.INFORMATION_MESSAGE); }


							}
						});
					}
					{
						btnSetActive = new JButton("Reactivate Receipt");
						pnlSouthEast.add(btnSetActive);
						btnSetActive.setCursor(handCursor);
						btnSetActive.setEnabled(false);
						btnSetActive.setMnemonic(KeyEvent.VK_R);
						btnSetActive.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								if (FncAcounting.EmpCanApprove(UserInfo.EmployeeCode, "14")==true)
								{
									updateCRBstatus("A");
								}
								else if (FncAcounting.EmpCanApprove(UserInfo.EmployeeCode, "14")==false ) 
								{ JOptionPane.showMessageDialog(getContentPane(),"Sorry, you are not authorized to update CRB.","Information",JOptionPane.INFORMATION_MESSAGE); }


							}
						});
					}					
					{
						btnCancel = new JButton("Cancel");
						pnlSouthEast.add(btnCancel);
						btnCancel.setCursor(handCursor);
						btnCancel.setMnemonic(KeyEvent.VK_C);
						btnCancel.setEnabled(false);
						btnCancel.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {

								refreshFields();
								refresh_table_main();
								enableButtons(false, false,false,false,false,false);
								enablePhase(false);
								enableReceiptLookup(false);
								enableReceiptDtls(false);

								mnidelete.setEnabled(false);
								mniaddrow.setEnabled(false);
							}
						});
					}
				}
			}
		}
		rbtnOR.setSelected(true);
		btngrpReceipt.setSelected(rbtnOR.getModel(), true);
		btngrpReceipt.getSelection().setActionCommand(rbtnOR.getActionCommand());

		//XXX Bind column of Main table into Total table
		FncTables.bindColumnTables(_tblMain, _tblTotal);
		//_tblMain.hideColumns(/*"Entry #", */"Account ID", "Rec. ID");

		//XXX Set components to focus traversal policy
		FncFocusTraversalPolicy policy = new FncFocusTraversalPolicy(lookupProject, lookupPhase, rbtnAR, rbtnOR, lookupReceipt, btnExpand, btnCancel);
		this.setFocusTraversalPolicy(policy);
		
		//added 01/26/2016 - purpose - set CENQHOMES as default company
		initialize_comp();
	}

	private void displayReceiptDetails(String receipt_type, String crb_no, String pbl_id, Integer seq_no) {//XXX
		enableReceiptDtls(true);
		//enableButtons(true, true, false, false, false, false);
		enableButtons(true, true, false, true, true, false);

		originalEntries = new ArrayList<Object[]>();
		_CashRecieptBooks.displayAccountEntries(receipt_type, _modelMain, _modelTotal, rowHeader, pbl_id, seq_no.toString(), co_id.toString(), crb_no, originalEntries, false);
		scrollMainSouth.setRowHeaderView(FncTables.getRowHeader_Footer(Integer.toString(_tblMain.getRowCount())));
		//_tblMain.packColumns(/*"Entry #", */"Particulars", "Account ID", "Account Name", "Debit", "Credit", "Remarks", "Rec. ID");
		_tblMain.packAll();
		_tblMain.packColumns(15, "Debit", "Credit");
		btnExpand.setText("Group Entries");
		btnExpand.setActionCommand("Collapse");
		btnExpand.setDisplayedMnemonicIndex(6);
	}

	private String getReceiptType(){
		String receipt_type = btngrpReceipt.getSelection().getActionCommand();
		System.out.println(receipt_type);
		return receipt_type;
		
	}

	//enable, disable, refresh
	private void refreshFields(){

		lookupProject.setValue("");
		txtProject.setText("");
		lookupPhase.setValue("");
		txtPhase.setText("");
		rbtnAR.setSelected(false);	
		rbtnOR.setSelected(true);	
		lookupReceipt.setValue("");
		txtReceipt.setText("");
		dateReceiptDate.setDate(null);
		txtUnitID.setText("");
		txtUnitDescription.setText("");
		txtSequence.setText("");
		txtClientID.setText("");
		txtClientName.setText("");	
		txtStatus.setText("");
	}

	private void refreshSubFields(){		

		txtReceipt.setText("");
		dateReceiptDate.setDate(null);
		txtUnitID.setText("");
		txtUnitDescription.setText("");
		txtSequence.setText("");
		txtClientID.setText("");
		txtClientName.setText("");	
		txtStatus.setText("");	

	}

	public static void enableButtons(Boolean a, Boolean b, Boolean c, Boolean d, Boolean e, Boolean f){

		btnExpand.setEnabled(a);
		btnCancel.setEnabled(b);
		btnSetActive.setEnabled(c);
		btnSetInactive.setEnabled(d);
		btnEdit.setEnabled(e);
		btnSave.setEnabled(f);

	}

	public void refresh_table_main(){

		FncTables.clearTable(_modelMain);
		FncTables.clearTable(_modelTotal);
		rowHeader = _tblMain.getRowHeader();
		scrollMainCenter.setRowHeaderView(rowHeader);
		_modelTotal.addRow(new Object[] { null, null, "Total", 0.00, 0.00 });
	}

	public void enablePhase(Boolean x){

		lblPhase.setEnabled(x);
		lookupPhase.setEnabled(x);
		txtPhase.setEnabled(x);		

	}

	public void enableReceiptLookup(Boolean x){

		lblReceiptType.setEnabled(x);
		rbtnAR.setEnabled(x);
		rbtnOR.setEnabled(x);
		lblReceipt.setEnabled(x);
		lookupReceipt.setEnabled(x);

	}

	public static void enableReceiptDtls(Boolean x){


		txtReceipt.setEnabled(x);
		lblReceiptDate.setEnabled(x);
		dateReceiptDate.setEnabled(x);
		lblUnit.setEnabled(x);
		txtUnitID.setEnabled(x);
		txtUnitDescription.setEnabled(x);
		lblSequence.setEnabled(x);
		txtSequence.setEnabled(x);
		lblClient.setEnabled(x);
		txtClientID.setEnabled(x);
		txtClientName.setEnabled(x);
		lblStatus.setEnabled(x);
		txtStatus.setEnabled(x);

	}

	public void initialize_comp(){		
		
		co_id 		= "02";	
		company		= "CENQHOMES DEVELOPMENT CORPORATION";			
		txtCompany.setText(company);
		
		txtCompany.setText(company);	
		lblProject.setEnabled(true);
		lookupProject.setEnabled(true);
		txtProject.setEnabled(true);	
		lookupProject.setLookupSQL(CheckStatusMonitoring.sql_project(co_id));

		enableReceiptLookup(true);
		lookupReceipt.setValue("");
		refreshSubFields();
		enableButtons(false,false,false,false,false,false);
		refresh_table_main();
		lookupCompany.setValue(co_id);
}
	

	//table operation
	private void clickTableColumn() {
		int column = _tblMain.getSelectedColumn();
		int row = _tblMain.getSelectedRow();

		if (column == 1 && _modelMain.isEditable()) {  
			_JLookupTable dlg = new _JLookupTable(FncGlobal.homeMDI, null, "Chart of Account", getChartofAccount(), false);
			dlg.setLocationRelativeTo(FncGlobal.homeMDI);
			dlg.setVisible(true);

			Object[] data = dlg.getReturnDataSet();
			if (data != null) {
				_modelMain.setValueAt(data[0], row, column);
				_modelMain.setValueAt(data[1], row, column+1);
			}
		}		

		//_tblMain.packAll();
		//_tblMain.getColumnModel().getColumn(3).setPreferredWidth(100);
		//_tblMain.getColumnModel().getColumn(4).setPreferredWidth(100);
	}

	public static void totalEntries5(DefaultTableModel modelMain, DefaultTableModel modelTotal) {

		BigDecimal debit 		= new BigDecimal(0.00);	
		BigDecimal credit 		= new BigDecimal(0.00);	

		FncTables.clearTable(modelTotal);//Code to clear modelMain.
		for(int x=0; x < modelMain.getRowCount(); x++){

			try { debit 	= debit.add(((BigDecimal) modelMain.getValueAt(x, 3)));} 
			catch (NullPointerException e) { debit 	= debit.add(new BigDecimal(0.00)); }

			try { credit 	= credit.add(((BigDecimal) modelMain.getValueAt(x, 4)));} 
			catch (NullPointerException e) { credit = credit.add(new BigDecimal(0.00)); }
		}		
		modelTotal.addRow(new Object[] { null, null, "Total", debit, credit, null });	

	}



	//select, loookup SQL
	public String getChartofAccount(){

		String sql = "select acct_id, acct_name, bs_is from mf_boi_chart_of_accounts where status_id = 'A' and (w_subacct is null OR filtered = false)";//ADDED FILTERED BY LESTER DCRF 2719	
		return sql;

	}

	private void computeCRB_amount(){

		int rw = _tblMain.getModel().getRowCount();	
		int x = 0;

		while (x < rw) {			

			Double debit	= 0.00;	
			Double credit	= 0.00;	

			if (_tblMain.getValueAt(x,3)==null || _tblMain.getValueAt(x,3).equals("")){debit = 0.00;}
			else {debit	= Double.parseDouble(_tblMain.getValueAt(x,3).toString());	}

			if (_tblMain.getValueAt(x,4)==null || _tblMain.getValueAt(x,4).equals("")){credit = 0.00;}
			else {credit	= Double.parseDouble(_tblMain.getValueAt(x,4).toString());	}			

			BigDecimal debit_bd 		= new BigDecimal(debit);		
			BigDecimal credit_bd 		= new BigDecimal(credit);

			_tblMain.setValueAt(debit_bd, x, 3);
			_tblMain.setValueAt(credit_bd, x, 4);
			x++;
		}
		totalEntries5(_modelMain, _modelTotal);
	}

	public Object [] getCRB_details (String receipt_no, String rec_id) {

		String SQL = 
				"--SQL getCRB_details: \n" +
						"select " +   	
						"rb_fiscal_year, " +  //0
						"rb_month, " +	//1
						"ar_no, " +		//2
						"proj_id, " +	//3
						"phase, " +		//4
						"doc_id," +		//5
						"co_id " +		//6
						"from (select distinct on (rb_id) * from rf_crb_detail) a " +
						"where rb_id = '"+receipt_no+"' " +
						"and trim(pay_rec_id) = '"+rec_id+"'\n\n" ;

		System.out.printf("SQL getCRB_details: \n" + SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			return db.getResult()[0];
		}else{
			return null;
		}		

	}



	//save, insert, update
	public void saveCRB(){		

		if(checkAcctBalanceIfEqual()==false)
		{JOptionPane.showMessageDialog(getContentPane(), "Debit and Credit balance totals are not equal.", "Warning", 
				JOptionPane.WARNING_MESSAGE);}
		else {	

			if (JOptionPane.showConfirmDialog(getContentPane(), "Are you sure you want to update CRB entries?", "Update", 
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

				String crb_no = lookupReceipt.getValue().trim();
				String rec_id = _tblMain.getValueAt(0,6).toString().trim();

				pgUpdate db = new pgUpdate();						

				//get other CRB details neede in saving
				setCRBdetails(crb_no, rec_id);

				//saving implementation						
				inactivateOld_CRB(crb_no, rec_id, db);		
				insertNew_CRBdetail(crb_no, rec_id, db);

				db.commit();
				JOptionPane.showMessageDialog(getContentPane(),"CRB entries are successfully updated.","", JOptionPane.INFORMATION_MESSAGE);		
				_modelMain.setEditable(false);
				_tblMain.setEditable(false);
				btnEdit.setEnabled(false);	
				btnSave.setEnabled(false);

				originalEntries = new ArrayList<Object[]>();

				String doc_type = "";
				if (doc_id.equals("01")) {doc_type="OR";} else if(doc_id.equals("307")) {doc_type="SI";} else {doc_type="AR";}
				_CashRecieptBooks.displayAccountEntries(doc_type, _modelMain, _modelTotal, rowHeader, pbl_id, seq_no.toString(), co_id.toString(), crb_no, originalEntries, btnExpand.getActionCommand().equals("Collapse"));
				scrollMainSouth.setRowHeaderView(FncTables.getRowHeader_Footer(Integer.toString(_tblMain.getRowCount())));
				_tblMain.packColumns(/*"Entry #", */"Particulars", "Account ID", "Account Name", "Debit", "Credit", "Remarks", "Rec. ID");
				btnExpand.setText("Group Entries");
				btnExpand.setActionCommand("Collapse");
				btnExpand.setDisplayedMnemonicIndex(6);
			}			
		}

	}

	public void inactivateOld_CRB(String receipt_no, String record_id, pgUpdate db) {

		String sqlDetail = 

				"--inactivateOld_CRB \n" +
						"UPDATE rf_crb_detail set \n" +
						"status_id = 'I'  \n" +
						"where trim(rb_id) = '"+receipt_no+"' \n" +
						"and trim(pay_rec_id) = '"+record_id+"' \n\n" ;

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);	
		
		String sqlDetail2 = 

			"--inactivate rf_payments \n" +
					"UPDATE rf_payments set \n" +
					"status_id = 'I'  \n" +
					"where trim(or_no) = '"+receipt_no+"' \n" +
					"and trim(pay_rec_id) = '"+record_id+"' \n\n" ;

		System.out.printf("SQL #1: %s", sqlDetail2);
		db.executeUpdate(sqlDetail, false);	
	
	}

	public void insertNew_CRBdetail(String receipt_no, String record_id, pgUpdate db){

		int x  = 0;	
		int y  = 1;
		int rw = _tblMain.getModel().getRowCount();	

		while(x < rw ) {	

			String account_id 	= _tblMain.getValueAt(x,1).toString();
			String remarks 		= _tblMain.getValueAt(x,5).toString().replace("'","''").trim();			

			Double debit	= 0.00;	
			Double credit	= 0.00;	

			if (_tblMain.getValueAt(x,3)==null || _tblMain.getValueAt(x,3).equals("")){debit = 0.00;}
			else {debit	= Double.parseDouble(_tblMain.getValueAt(x,3).toString());	}

			if (_tblMain.getValueAt(x,4)==null || _tblMain.getValueAt(x,4).equals("")){credit = 0.00;}
			else {credit	=  -1*Double.parseDouble(_tblMain.getValueAt(x,4).toString());	}	


			Double trans_amt	= debit + credit;

			if (trans_amt == 0.00) {}
			else
			{	
				String sqlDetail = 
						"INSERT into rf_crb_detail values (" +
								"'"+receipt_no+"',  \n" +  	//1 rb_id
								"'"+txtClientID.getText().trim()+"',  \n" +  	//2 entity_id
								"'"+txtUnitID.getText().trim()+"',  \n" +  		//3 pbl_id
								"'"+txtSequence.getText().trim()+"',  \n" +  		//4	seq_no	
								""+y+",  \n" +  			//5	line_no		
								"'"+account_id+"',  \n" +  	//6 acct_id
								""+trans_amt+",  \n" +  	//7 trans_amt
								"'"+rb_fiscal_yr+"',  \n" + //8 rb_fiscal_year
								"'"+rb_month+"',  \n" ; 	//9 rb_month

				if(ar_no==null) {sqlDetail = sqlDetail + "null,  \n" ; }  		//10 ar_no
				else {sqlDetail = sqlDetail + "'"+ar_no+"',  \n" ; }		//10 ar_no
				sqlDetail = sqlDetail +

						"'"+remarks+"',  \n" +  	//11 remarks
						"'"+record_id+"',  \n" +  	//12 pay_rec_id
						"'"+proj+"',  \n" +  	//13 proj_id
						"'"+phase+"',  \n" +  		//14 phase
						"'"+co_id+"',  \n" +  		//15 co_id
						"'"+doc_id+"',  \n" +  		//16 doc_id					
						"'A'  \n" + 				//17 status_id
						")   " ;

				System.out.printf("SQL #1: %s", sqlDetail);
				db.executeUpdate(sqlDetail, false);					
				y++;
			}	

			x++;
		}
	}

	public void setCRBdetails(String receipt_no, String record_id){

		Object[] pmt_sch = getCRB_details(receipt_no, record_id);			

		try { rb_fiscal_yr 	= (String) pmt_sch[0].toString(); } catch (NullPointerException e) { rb_fiscal_yr = ""; }
		try { rb_month 		= (String) pmt_sch[1].toString(); } catch (NullPointerException e) { rb_month = ""; }
		try { ar_no 		= (String) pmt_sch[2].toString(); } catch (NullPointerException e) { ar_no = null; }
		try { proj 			= (String) pmt_sch[3].toString(); } catch (NullPointerException e) { proj = ""; }
		try { phase 		= (String) pmt_sch[4].toString(); } catch (NullPointerException e) { phase = ""; }
		try { doc_id 		= (String) pmt_sch[5].toString(); } catch (NullPointerException e) { doc_id = ""; }
		try { co_id 		= (String) pmt_sch[6].toString(); } catch (NullPointerException e) { co_id = ""; }

	}

	public void updateCRBstatus(String status) {

		String remark = "";
		if (status == "A") {
			remark = "Are you sure you want to reactivate this receipt?"; 
		}
		
		if (status == "I") {
			remark = "Are you sure you want to cancel this receipt?"; 
		}
		
		if (JOptionPane.showConfirmDialog(getContentPane(), remark, "Save", 
			JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

			String doc_id = "";		
			if (rbtnAR.isSelected()==true) {
				doc_id = "03";
			} else if (rbtnSI.isSelected()==true) {
				doc_id = "307";
			}else {
				doc_id = "01";
			}
			
			String rec_id = _tblMain.getValueAt(0,6).toString().trim();

			pgUpdate db = new pgUpdate();	
			String sqlDetail = "--updateCRBstatus \n" +
				"UPDATE rf_crb_header set " +
				"status_id = '"+status+"' \n" +
				"where trim(rb_id) = '"+lookupReceipt.getText().trim()+"' " +
				"and doc_id = '"+doc_id+"'";
			
			System.out.printf("SQL #1: %s", sqlDetail);
			db.executeUpdate(sqlDetail, false);		
			
			String sqlDetail2 = 
				"--inactivate rf_payments \n" +
						"UPDATE rf_payments set \n" +
						"status_id = '"+status+"' \n" +
						"where trim(or_no) = '"+lookupReceipt.getText().trim()+"' \n" +
						"and pay_rec_id = '"+rec_id+"' \n\n" ;
			System.out.printf("SQL #1: %s", sqlDetail2);
			db.executeUpdate(sqlDetail2, false);	
			
			String sqlDetail3 = 
				"--inactivate rf_payments \n" +
						"UPDATE rf_client_ledger set \n" +
						"status_id =  '"+status+"' \n" +
						"where pay_rec_id = '"+rec_id+"' \n\n" ;
			System.out.printf("SQL #1: %s", sqlDetail3);
			db.executeUpdate(sqlDetail3, false);	
							
			db.commit();
			JOptionPane.showMessageDialog(getContentPane(),"CRB status successfully updated.","", JOptionPane.INFORMATION_MESSAGE);		

			if (status.equals("I")) {
				btnSetActive.setEnabled(true);
				btnSetInactive.setEnabled(false);
				txtStatus.setText("INACTIVE");
			} else {
				btnSetActive.setEnabled(false);
				btnSetInactive.setEnabled(true);
				txtStatus.setText("ACTIVE");
			}
		}
	}

	//check and validate	
	private Boolean checkAcctBalanceIfEqual(){
		boolean x = true;

		Double debit 	= (double) Math.round(Double.parseDouble(_modelTotal.getValueAt(0,3).toString())*100)/100;	
		Double credit 	= (double) Math.round(Double.parseDouble(_modelTotal.getValueAt(0,4).toString())*100)/100;	

		System.out.printf("Debit : " + debit + "\n");
		System.out.printf("Credit :" + credit);

		if (debit.equals(credit)||debit==credit) { } 
		else {x=false;}		

		return x;
	}

	//right-click	
	class PopupTriggerListener_panel extends MouseAdapter {
		public void mousePressed(MouseEvent ev) {
			if (ev.isPopupTrigger()) {
				menu.show(ev.getComponent(), ev.getX(), ev.getY());
			}
		}
		public void mouseReleased(MouseEvent ev) {
			if (ev.isPopupTrigger()) {
				menu.show(ev.getComponent(), ev.getX(), ev.getY());
			}
		}
	}

	private void removeRow(){		
		int r  = _tblMain.getSelectedRow();		
		String particular = _tblMain.getValueAt(r,0).toString();
		String rec_id = _tblMain.getValueAt(r,6).toString();

		if (JOptionPane.showConfirmDialog(getContentPane(), "Remove row?", "Confirmation", 
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {			
			((DefaultTableModel) _tblMain.getModel()).removeRow(r);  
			_modelMain.addRow(new Object[] { particular, null, null,  new BigDecimal(0.00), new BigDecimal(0.00), "", rec_id });				
		}

		totalEntries5(_modelMain, _modelTotal);
	}

	private void AddRow(){			
		String particular = _tblMain.getValueAt(0,0).toString();
		String rec_id = _tblMain.getValueAt(0,6).toString();

		_modelMain.addRow(new Object[] {  particular, null, null,  new BigDecimal(0.00), new BigDecimal(0.00), "", rec_id });			
		((DefaultListModel) rowHeader.getModel()).addElement(_modelMain.getRowCount());
		totalEntries5(_modelMain, _modelTotal);
	}

	public void cancelCRB() {
		
		System.out.println("");
		System.out.println("UserInfo.branch_id: " + UserInfo.branch_id);
		System.out.println("UserInfo.branch: " + UserInfo.Branch);
		
		/*	Modified by Mann2x; Date Added: July 4, 2017; Added a loop so for receipts with multiple rows;	*/
		if (JOptionPane.showConfirmDialog(getContentPane(), "Are you sure you want to cancel this receipt?", "Save", 
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
			
			pgUpdate db = new pgUpdate();
			String crb_status = txtStatus.getText();
			
			for (int x = 0; x < _modelMain.getRowCount(); x++) {
				
				String doc_id = "";
				if (rbtnAR.isSelected()==true) {
					doc_id = "03";
				} else if (rbtnSI.isSelected()==true) {
					doc_id = "307";
				} else {
					doc_id = "01";
				}
				
				/**	String rec_id = _tblMain.getValueAt(0, 6).toString().trim();	**/
				String rec_id = _tblMain.getValueAt(x, 6).toString().trim();
				
				if(crb_status.equals("POSTED") == false) {
					db = new pgUpdate();	
					String sqlDetail = "--update CRB status: header \n" +
					"UPDATE rf_crb_header " +
					"set status_id = 'I', canceled_by = '"+UserInfo.EmployeeCode+"', canceled_date = now() \n" +
					"where trim(rb_id) = '"+lookupReceipt.getText().trim()+"' and doc_id = '"+doc_id+"' \n" +
					"and pay_rec_id::int = '"+rec_id+"'::int and status_id != 'I'; \n\n";
					
					System.out.printf("SQL #1: %s", sqlDetail);
					db = new pgUpdate();
					db.executeUpdate(sqlDetail, false);
					db.commit();
					
					//COMMENTED SO THAT CANCELLED RECEIPT DETAILS CAN STILL BE VIEW ONLY CRB HEADER SHOULD BE INACTIVE
//					db = new pgUpdate();	
//					String sqlDetail0 = "--update CRB status: detail \n" +
//					"update rf_crb_detail \n" +
//					"set remarks = remarks || '; Cancelled;', status_id = 'I' \n" +
//					"where trim(rb_id) = '"+lookupReceipt.getText().trim()+"' \n" +
//					"and pay_rec_id::int = '"+rec_id+"'::int and status_id != 'I'; \n\n";
//					
//					System.out.printf("SQL #1: %s", sqlDetail0);
//					db = new pgUpdate();
//					db.executeUpdate(sqlDetail0, false);
//					db.commit();
				}
				
				
				String sqlDetail2 = "--inactivate rf_payments \n" +
				"UPDATE rf_payments \n" +
				"set status_id = 'I', remarks = coalesce(remarks, '') || ' Canceled Receipt;' \n" +
				"where trim(or_no) = '"+lookupReceipt.getText().trim()+"' \n" +
				"and pay_rec_id::int = '"+rec_id+"'::int and status_id != 'I'; \n\n" ;
				
				System.out.printf("SQL #1: %s", sqlDetail2);
				db = new pgUpdate();
				db.executeUpdate(sqlDetail2, false);
				db.commit();
				
				String sqlDetail3 = "--inactivate rf_client_ledger \n" +
				"UPDATE rf_client_ledger \n" +
				"set status_id = 'I', remarks = coalesce(remarks,'') || 'Canceled Receipt;' \n" +
				"where pay_rec_id = '"+rec_id+"' and status_id != 'I'; \n\n" ;
				System.out.printf("SQL #1: %s", sqlDetail3);
				db = new pgUpdate();
				db.executeUpdate(sqlDetail3, false);
				db.commit();
				
				/* Added by Mann2x; Date Added: September 21, 2017; DCRF# 257	*/
				String strSQL = "update rf_check_history set status_id = 'I' where pay_rec_id::int = '"+rec_id+"'::int";
				System.out.println(strSQL);
				db = new pgUpdate();
				db.executeUpdate(strSQL, false);
				db.commit();
			}
			
			btnSetActive.setEnabled(true);
			btnSetInactive.setEnabled(false);
			txtStatus.setText("INACTIVE");	
			JOptionPane.showMessageDialog(getContentPane(),"CRB status successfully updated.","", JOptionPane.INFORMATION_MESSAGE);
		}	
	}
}