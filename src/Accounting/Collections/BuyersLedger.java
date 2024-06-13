package Accounting.Collections;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXFormattedTextField;
import org.jdesktop.swingx.JXLabel;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.JXTextField;

import tablemodel.modelBuyerPaymentsList;
import tablemodel.modelByrPmtPosting_CRB;
import tablemodel.modelByrPmtPosting_ledger;
import tablemodel.modelJV_acct_entries;
import Accounting.Disbursements.RequestForPayment;
import Database.pgSelect;
import DateChooser._JDateChooser;
import FormattedTextField._JXFormattedTextField;
import Functions.FncFocusTraversalPolicy;
import Functions.FncGlobal;
import Functions.FncTables;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components._JInternalFrame;
import components._JRadioButton;
import components._JScrollPaneMain;
import components._JScrollPaneTotal;
import components._JTabbedPane;
import components._JTableMain;
import components._JTableTotal;

public class BuyersLedger extends _JInternalFrame {

	private static final long serialVersionUID = 1L;

	private static String title = "Buyers Payment Ledger";
	private Dimension frameSize = new Dimension(806, 620);
	static Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	static Cursor handCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);

	private JXPanel pnlMain;
	private JXPanel pnlNorth;
	private JXPanel pnlSouth;	
	private JXPanel pnlSouthEast;
	private JXPanel pnlCenter;
	private JXPanel pnlSouthWest;	
	private JXPanel pnlCenter_sub1;
	private JXPanel pnlCenter_sub2;
	private JPanel pnlSample2;
	private JPanel pnlSample3;

	private JXLabel lblCompany;
	public static JXLabel lblProject;
	private static JXLabel lblSequence;
	public static JXLabel lblPhase;
	public static JXLabel lblReceipt;
	public static JXLabel lblReceiptType;
	private static JXLabel lblClient;
	private static JXLabel lblUnit;
	private static JXLabel lblStatus;
	private JXLabel lblNSP;

	public static _JLookup lookupProject;
	public static _JLookup lookupPhase;
	public static _JLookup lookupReceipt;
	public static _JLookup lookupCompany;
	private _JLookup lookupUnit;

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

	public static JButton btnViewCARD;	
	private static JButton btnCancel;

	public static _JDateChooser dateReceiptDate;

	private _JTabbedPane tabSouth;	
	
	private _JScrollPaneMain scrollSample3Main;
	private _JScrollPaneMain scrollSample5Main;	
	private _JScrollPaneMain scrollMainCenter;
	
	private modelByrPmtPosting_ledger modelSample3Main;
	private modelByrPmtPosting_ledger modelSample3Total;
	private modelByrPmtPosting_CRB modelSample5Main;
	private modelByrPmtPosting_CRB modelSample5Total;
	public static modelBuyerPaymentsList _modelMain;
	public static modelBuyerPaymentsList _modelTotal;
	
	private _JTableMain tblSample3Main;
	private _JTableMain tblSample5Main;
	public static _JTableMain _tblMain;
	
	private JList rowHeaderSample3Main;
	private JList rowHeaderSample5Main;
	public static JList rowHeader;	
	
	private _JScrollPaneTotal scrollSample3Total;	
	private _JScrollPaneTotal scrollSample5Total;	
	public static _JScrollPaneTotal scrollMainSouth;
	
	private _JTableTotal tblSample3Total;
	private _JTableTotal tblSample5Total;
	private _JTableTotal _tblTotal;
	
	private _JXFormattedTextField txtNSP;

	protected static Font dialog11Bold = new Font("DIALOG", Font.BOLD, 11);
	
	String proj_id 	= "";
	String rec_id 	= "";
	String crb_no 	= "";
	String proj		= "";
	String phase_id	= "";

	private JSplitPane splitSchedule;

	private JPanel pnlSample4;

	private _JScrollPaneMain scrollSample4Main;

	private modelJV_acct_entries modelSample4Main;

	private _JTableMain tblSample4Main;

	private JList rowHeaderSample4Main;

	private _JScrollPaneTotal scrollSample4Total;

	private modelJV_acct_entries modelSample4Total;

	private _JTableTotal tblSample4Total;
	
	public static String co_id = "";		
	public static String company = "";
	public static String company_logo;	
	public static String entity_id = "";
	public static String entity_name = "";
	public static String unit_id = "";
	public static String unit_desc = "";
	public static String status_id = "";
	public static String status_name = "";
	public static String pbl_id = "";
	public static String seq_no = "";		
	public static Double NSP = 0.00;		

	public BuyersLedger() {
		super(title, true, true, true, true);
		initGUI();
	}

	public BuyersLedger(String title) {
		super(title);
		initGUI();
	}

	public BuyersLedger(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	private void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		this.setPreferredSize(frameSize);
		this.setLayout(new BorderLayout());

		{
			pnlMain = new JXPanel();
			getContentPane().add(pnlMain, BorderLayout.CENTER);
			pnlMain.setLayout(new BorderLayout(5, 5));
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			{
				pnlNorth = new JXPanel(null);
				pnlMain.add(pnlNorth, BorderLayout.NORTH);
				pnlNorth.setLayout(null);
				pnlNorth.setPreferredSize(new java.awt.Dimension(794, 186));
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
									
									proj_id = (String) data[0];	
									
									txtProject.setText(data[1].toString());
									lookupPhase.setLookupSQL(getPhase(proj_id));
									KEYBOARD_MANAGER.focusNextComponent();
									enablePhase(true);
									
									refresh_table_main();
									lookupPhase.setValue("");
									txtPhase.setText("");
									
									btnViewCARD.setEnabled(true);
									btnCancel.setEnabled(true);
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
									
									phase_id = data[0].toString();	
									
									txtPhase.setText(data[2].toString());
									KEYBOARD_MANAGER.focusNextComponent(rbtnOR);									
									refresh_table_main();
									lblUnit.setEnabled(true);
									lookupUnit.setEnabled(true);
									lookupUnit.setLookupSQL(getClientList());			
									
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
						lblUnit = new JXLabel("Unit");
						pnlNorth.add(lblUnit);
						lblUnit.setBounds(9, 119, 76, 25);
						lblUnit.setEnabled(false);
					}
					{
						txtUnitDescription = new JXTextField("");
						pnlNorth.add(txtUnitDescription);
						txtUnitDescription.setEditable(false);
						txtUnitDescription.setEnabled(false);
						txtUnitDescription.setBounds(190, 119, 158, 25);
					}
					{
						lblSequence = new JXLabel("Seq. No");
						pnlNorth.add(lblSequence);
						lblSequence.setHorizontalAlignment(JLabel.RIGHT);
						lblSequence.setBounds(350, 119, 60, 25);
						lblSequence.setEnabled(false);
					}
					{
						txtSequence = new JXTextField("");
						pnlNorth.add(txtSequence);
						txtSequence.setEditable(false);
						txtSequence.setEnabled(false);
						txtSequence.setHorizontalAlignment(JTextField.CENTER);
						txtSequence.setBounds(414, 119, 80, 25);
					}
					{
						lblStatus = new JXLabel("Status");
						pnlNorth.add(lblStatus);
						lblStatus.setHorizontalAlignment(JLabel.RIGHT);
						lblStatus.setBounds(504, 118, 54, 26);
						lblStatus.setEnabled(false);
					}
					{
						txtStatus = new JXTextField("");
						pnlNorth.add(txtStatus);
						txtStatus.setEditable(false);
						txtStatus.setEnabled(false);
						txtStatus.setHorizontalAlignment(JTextField.CENTER);
						txtStatus.setBounds(562, 119, 212, 24);
					}
					{
						lblClient = new JXLabel("Client");
						pnlNorth.add(lblClient);
						lblClient.setBounds(9, 150, 75, 23);
						lblClient.setEnabled(false);
					}
					{
						txtClientID = new JXTextField("");
						pnlNorth.add(txtClientID);
						txtClientID.setEditable(false);
						txtClientID.setEnabled(false);
						txtClientID.setHorizontalAlignment(JTextField.CENTER);
						txtClientID.setBounds(84, 149, 100, 25);
					}
					{
						txtClientName = new JXTextField("");
						pnlNorth.add(txtClientName);
						txtClientName.setEditable(false);
						txtClientName.setEnabled(false);
						txtClientName.setBounds(190, 149, 442, 25);
					}
					{
						lookupUnit = new _JLookup("", "Unit");
						pnlNorth.add(lookupUnit);
						lookupUnit.setReturnColumn(0);
						lookupUnit.setLookupSQL(SQL_COMPANY());
						lookupUnit.setEnabled(false);
						lookupUnit.setFilterName(true);
						lookupUnit.setBounds(84, 119, 100, 25);
						lookupUnit.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {//XXX Project
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){	

									entity_id 	= (String) data[0];	
									entity_name = (String) data[1];	
									unit_id 	= (String) data[2];	
									unit_desc 	= (String) data[3];	
									status_id 	= (String) data[4];	
									status_name = (String) data[5];	
									pbl_id 		= (String) data[6];	
									seq_no 		= data[7].toString();	
									NSP 		= Double.parseDouble(data[8].toString());	

									txtClientID.setText(entity_id);
									txtClientName.setText(entity_name);
									lookupUnit.setText(unit_id);
									txtUnitDescription.setText(unit_desc);
									txtStatus.setText(status_name);	
									txtSequence.setText(seq_no);	
									txtNSP.setText(data[8].toString());
							
									enableUnitDetails(true);
									displayPaymentList(_modelMain, rowHeader, _modelTotal);	
									refresh_table_crb_ledger();
									displayJV_details(modelSample4Main, rowHeaderSample4Main, modelSample4Total);								
								}
							}
						});
					}
					{
						lblNSP = new JXLabel("NSP");
						pnlNorth.add(lblNSP);
						lblNSP.setHorizontalAlignment(JLabel.RIGHT);
						lblNSP.setBounds(632, 149, 30, 24);
						lblNSP.setEnabled(false);
					}
					{					
						txtNSP = new _JXFormattedTextField(JXFormattedTextField.CENTER);
						pnlNorth.add(txtNSP);
						txtNSP.setFormatterFactory(_JXFormattedTextField.DECIMAL);
						txtNSP.setText("0.00");
						txtNSP.setEnabled(false);
						txtNSP.setEditable(false);
						txtNSP.setBounds(668, 148, 106, 26);					
					}	
				}
			}
			{
				pnlCenter = new JXPanel();
				pnlMain.add(pnlCenter, BorderLayout.CENTER);
				pnlCenter.setLayout(new BorderLayout());
				pnlCenter.setPreferredSize(new java.awt.Dimension(794, 298));
				
				splitSchedule = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
				pnlCenter.add(splitSchedule);
				splitSchedule.setOneTouchExpandable(true);
				splitSchedule.setResizeWeight(.7d);

				{

					pnlCenter_sub1 = new JXPanel();
					//pnlCenter.add(pnlCenter_sub1, BorderLayout.CENTER);
					splitSchedule.add(pnlCenter_sub1, JSplitPane.LEFT);
					pnlCenter_sub1.setLayout(new BorderLayout());
					pnlCenter_sub1.setPreferredSize(new java.awt.Dimension(794, 179));
					pnlCenter_sub1.setBorder(components.JTBorderFactory.createTitleBorder("Buyer Payments"));					
					

					{
						scrollMainCenter = new _JScrollPaneMain();						
						pnlCenter_sub1.add(scrollMainCenter, BorderLayout.CENTER);
						{
							_modelMain = new modelBuyerPaymentsList();
							_tblMain = new _JTableMain(_modelMain);
							scrollMainCenter.setViewportView(_tblMain);
							_tblMain.addMouseListener(new MouseAdapter() {
								public void mousePressed(MouseEvent e) {
									if(_tblMain.rowAtPoint(e.getPoint()) == -1){_tblTotal.clearSelection();}
									else{_tblMain.setCellSelectionEnabled(true);}
									displayCRB(modelSample5Main, rowHeaderSample5Main, modelSample5Total);	
									displayLedger(modelSample3Main, rowHeaderSample3Main, modelSample3Total);	
								}
								public void mouseReleased(MouseEvent e) {
									if(_tblMain.rowAtPoint(e.getPoint()) == -1){_tblTotal.clearSelection();}
									else{_tblMain.setCellSelectionEnabled(true);}
									displayCRB(modelSample5Main, rowHeaderSample5Main, modelSample5Total);	
									displayLedger(modelSample3Main, rowHeaderSample3Main, modelSample3Total);	
								}
							});

							_tblMain.addKeyListener(new KeyAdapter() {
								public void keyReleased(KeyEvent evt) {
									displayCRB(modelSample5Main, rowHeaderSample5Main, modelSample5Total);	
									displayLedger(modelSample3Main, rowHeaderSample3Main, modelSample3Total);	
								}							
								public void keyPressed(KeyEvent e) {
									displayCRB(modelSample5Main, rowHeaderSample5Main, modelSample5Total);	
									displayLedger(modelSample3Main, rowHeaderSample3Main, modelSample3Total);	
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
						pnlCenter_sub1.add(scrollMainSouth, BorderLayout.SOUTH);
						{
							_modelTotal = new modelBuyerPaymentsList();
							_modelTotal.addRow(new Object[] { "Total", null, null, null, 0.00,  null });

							_tblTotal = new _JTableTotal(_modelTotal, _tblMain);
							scrollMainSouth.setViewportView(_tblTotal);
							_tblTotal.setFont(dialog11Bold);
							_tblTotal.setTotalLabel(0);

							/*********************** Setting Total table for Main table ***********************/
							//_tblMain.setTotalTable(_tblTotal);
						}
					}
				}
				{

					pnlCenter_sub2 = new JXPanel();
					splitSchedule.add(pnlCenter_sub2, JSplitPane.RIGHT);
					//pnlCenter.add(pnlCenter_sub2, BorderLayout.SOUTH);
					pnlCenter_sub2.setLayout(new BorderLayout());
					pnlCenter_sub2.setPreferredSize(new java.awt.Dimension(794, 177));

					tabSouth = new _JTabbedPane();
					pnlCenter_sub2.add(tabSouth, BorderLayout.CENTER);			
					tabSouth.setPreferredSize(new java.awt.Dimension(636, 285));

									
					
					//CRB ENTRIES - BEGIN
					{
						pnlSample2 = new JPanel(new BorderLayout());
						tabSouth.addTab("CRB Entries", null, pnlSample2, null);
						pnlSample2.setPreferredSize(new java.awt.Dimension(631, 354));
						{
							scrollSample5Main = new _JScrollPaneMain();
							pnlSample2.add(scrollSample5Main, BorderLayout.CENTER);
							{
								modelSample5Main = new modelByrPmtPosting_CRB();

								tblSample5Main = new _JTableMain(modelSample5Main);
								scrollSample5Main.setViewportView(tblSample5Main);
								tblSample5Main.setCellSelectionEnabled(true);
								tblSample5Main.setEditable(false);
								tblSample5Main.setSortable(false);
								tblSample5Main.getColumnModel().getColumn(2).setPreferredWidth(100);
								tblSample5Main.getColumnModel().getColumn(3).setPreferredWidth(100);
								/*tblSample5Main.addMouseListener(new MouseAdapter() {
									public void mousePressed(MouseEvent e) {
										if(tblSample5Main.rowAtPoint(e.getPoint()) == -1){tblSample5Total.clearSelection();}
										else{tblSample5Main.setCellSelectionEnabled(true);}
									}
									public void mouseReleased(MouseEvent e) {
										if(tblSample5Main.rowAtPoint(e.getPoint()) == -1){tblSample5Total.clearSelection();}
										else{tblSample5Main.setCellSelectionEnabled(true);}
									}
								});
								tblSample5Main.addKeyListener(new KeyAdapter() {
									public void keyReleased(KeyEvent evt) {
										//computeCRB_amount();
									}							
									public void keyPressed(KeyEvent e) {
										//computeCRB_amount();
									}

								}); */
							}
							{
								rowHeaderSample5Main = tblSample5Main.getRowHeader();
								scrollSample5Main.setRowHeaderView(rowHeaderSample5Main);
								scrollSample5Main.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
							}
						}
						{
							scrollSample5Total = new _JScrollPaneTotal(scrollSample5Main);
							pnlSample2.add(scrollSample5Total, BorderLayout.SOUTH);
							{
								modelSample5Total = new modelByrPmtPosting_CRB();
								modelSample5Total.addRow(new Object[] { "Total", null,  new BigDecimal(0.00), new BigDecimal(0.00), null });	

								tblSample5Total = new _JTableTotal(modelSample5Total, tblSample5Main);
								scrollSample5Total.setViewportView(tblSample5Total);
								tblSample5Total.setFont(dialog11Bold);
								((_JTableTotal) tblSample5Total).setTotalLabel(0);
							}
						}
					}
					//CRB ENTRIES - END
					
					//BUYERS LEDGER APPLICATION - BEGIN
					{
						pnlSample3 = new JPanel(new BorderLayout());
						tabSouth.addTab("Buyer's Ledger", null, pnlSample3, null);
						pnlSample3.setPreferredSize(new java.awt.Dimension(1116, 335));
						{
							scrollSample3Main = new _JScrollPaneMain();							
							pnlSample3.add(scrollSample3Main, BorderLayout.CENTER);
							{
								modelSample3Main = new modelByrPmtPosting_ledger();

								tblSample3Main = new _JTableMain(modelSample3Main);
								scrollSample3Main.setViewportView(tblSample3Main);

								tblSample3Main.addMouseListener(new MouseAdapter() {
									public void mousePressed(MouseEvent e) {
										if(tblSample3Main.rowAtPoint(e.getPoint()) == -1){tblSample3Total.clearSelection();}
										else{tblSample3Main.setCellSelectionEnabled(true);}
									}
									public void mouseReleased(MouseEvent e) {
										if(tblSample3Main.rowAtPoint(e.getPoint()) == -1){tblSample3Total.clearSelection();}
										else{tblSample3Main.setCellSelectionEnabled(true);}
									}
								});

							}
							{
								rowHeaderSample3Main = tblSample3Main.getRowHeader();
								scrollSample3Main.setRowHeaderView(rowHeaderSample3Main);
								scrollSample3Main.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
							}
						}
						{
							scrollSample3Total = new _JScrollPaneTotal(scrollSample3Main);
							pnlSample3.add(scrollSample3Total, BorderLayout.SOUTH);
							{
								modelSample3Total = new modelByrPmtPosting_ledger();
								modelSample3Total.addRow(new Object[] { "Total", null, new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00),
										new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00),
										new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00)});	

								tblSample3Total = new _JTableTotal(modelSample3Total, tblSample3Main);
								scrollSample3Total.setViewportView(tblSample3Total);
								tblSample3Total.setFont(dialog11Bold);
								((_JTableTotal) tblSample3Total).setTotalLabel(0);
							}
						}
					}
					//BUYERS LEDGER APPLICATION - END
					
					
					//NOA RELEASE - BEGIN
					{
						pnlSample4 = new JPanel(new BorderLayout());
						tabSouth.addTab("NOA Release", null, pnlSample4, null);
						pnlSample4.setPreferredSize(new java.awt.Dimension(1116, 335));
						{
							scrollSample4Main = new _JScrollPaneMain();							
							pnlSample4.add(scrollSample4Main, BorderLayout.CENTER);
							{
								modelSample4Main = new modelJV_acct_entries();

								tblSample4Main = new _JTableMain(modelSample4Main);
								scrollSample4Main.setViewportView(tblSample4Main);

								tblSample4Main.addMouseListener(new MouseAdapter() {
									public void mousePressed(MouseEvent e) {
										if(tblSample4Main.rowAtPoint(e.getPoint()) == -1){tblSample4Total.clearSelection();}
										else{tblSample4Main.setCellSelectionEnabled(true);}
									}
									public void mouseReleased(MouseEvent e) {
										if(tblSample4Main.rowAtPoint(e.getPoint()) == -1){tblSample4Total.clearSelection();}
										else{tblSample4Main.setCellSelectionEnabled(true);}
									}
								});

							}
							{
								rowHeaderSample4Main = tblSample4Main.getRowHeader();
								scrollSample4Main.setRowHeaderView(rowHeaderSample4Main);
								scrollSample4Main.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
							}
						}
						{
							scrollSample4Total = new _JScrollPaneTotal(scrollSample4Main);
							pnlSample4.add(scrollSample4Total, BorderLayout.SOUTH);
							{
								modelSample4Total = new modelJV_acct_entries();
								modelSample4Total.addRow(new Object[] { "Total",null,  null, null, null, null, null, null, new BigDecimal(0.00), new BigDecimal(0.00), null});

								tblSample4Total = new _JTableTotal(modelSample4Total, tblSample4Main);
								scrollSample4Total.setViewportView(tblSample4Total);
								tblSample4Total.setFont(dialog11Bold);
								((_JTableTotal) tblSample4Total).setTotalLabel(0);
							}
						}
					}
					//BUYERS LEDGER APPLICATION - END
					
					
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
					pnlSouthWest.setPreferredSize(new java.awt.Dimension(199, 30));

					{
						btnViewCARD = new JButton("View CARD");
						pnlSouthWest.add(btnViewCARD);
						btnViewCARD.setCursor(handCursor);
						btnViewCARD.setEnabled(false);
						btnViewCARD.setMnemonic(KeyEvent.VK_V);
						btnViewCARD.setDisplayedMnemonicIndex(8);
						btnViewCARD.setPreferredSize(new java.awt.Dimension(183, 30));
						btnViewCARD.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {//XXX Expand
								JOptionPane.showMessageDialog(getContentPane(),"Sorry, this button is not \n"
										+ "functioning yet.","Information",JOptionPane.INFORMATION_MESSAGE);
							}
						});
					}
				}
				{
					pnlSouthEast = new JXPanel();
					pnlSouth.add(pnlSouthEast, BorderLayout.EAST);
					pnlSouthEast.setLayout(new GridLayout(1, 2, 5, 5));
					pnlSouthEast.setPreferredSize(new java.awt.Dimension(202, 30));
					
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
								enableButtons(false, false);
								refresh_table_crb_ledger();
								enablePhase(false);
								lblUnit.setEnabled(false);
								lookupUnit.setEnabled(false);
								enableUnitDetails(false);

							}
						});
					}
				}
			}
		}

		//XXX Bind column of Main table into Total table
		FncTables.bindColumnTables(_tblMain, _tblTotal);
		
		//XXX Set components to focus traversal policy
		FncFocusTraversalPolicy policy = new FncFocusTraversalPolicy(lookupProject, lookupPhase, rbtnAR, rbtnOR, lookupReceipt, btnViewCARD, btnCancel);
		this.setFocusTraversalPolicy(policy);
		
		//added 01/26/2016 - purpose - set CENQHOMES as default company
		initialize_comp();
	}

	
	//display tables
	public void displayPaymentList(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal ) {
		
		FncTables.clearTable(modelMain);//Code to clear modelMain.
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.		
		
		String sql = 

			"select \r\n" + 
			"\r\n" + 
			"a.pay_rec_id,\r\n" + 
			"(case when a.or_doc_id = '01' then a.or_no else '' end) as or_no,\r\n" + 
			"(case when a.pr_doc_id = '03' then a.or_no else '' end) as ar_no,\r\n" + 
			"trim(b.particulars) as pmt_part,\r\n" + 
			"(case when pymnt_type = 'A' then 'CASH' else 'CHECK' end) as pmt_type,\r\n" + 
			"a.amount,\r\n" + 
			"coalesce(d.prin,0) as prin,\r\n" + 
			"0 as perc,\r\n" + 
			"a.trans_date,\r\n" + 
			"a.or_date,\r\n" + 
			"a.remarks,\r\n" + 
			"trim(c.branch_alias) as branch\r\n" + 
			"\r\n" + 
			"from rf_payments a\r\n" + 
			"left join mf_pay_particular b on a.pay_part_id = b.pay_part_id\r\n" + 
			"left join mf_office_branch c on a.branch_id = c.branch_id\r\n" + 
			"left join (select distinct on (pay_rec_id) pay_rec_id, sum(amount) as prin \r\n" + 
			"	from rf_client_ledger where pbl_id = '"+pbl_id+"' and seq_no = "+seq_no+" " +
			
			//	Modified by Mann2x; Date Modified: April 24, 2017; Inactive ledger entries are being summed up along with the active ones which results to overflowing in the percentage;
			//	"	and part_id in ('002','013') group by pay_rec_id) d on a.pay_rec_id = d.pay_rec_id  \n" +
			"	and part_id in ('002','013') and status_id = 'A' group by pay_rec_id) d on a.pay_rec_id = d.pay_rec_id  \n" +
			
			"\r\n" + 
			"where a.pbl_id = '"+pbl_id+"' \n" +
			"and a.seq_no = "+seq_no+" \n" +
			"and a.status_id = 'A' \r\n" + 
			"\r\n" + 
			"order by pay_rec_id\r\n" + 
			"" ;  //

		System.out.printf("SQL: %s%n%n", sql);

		pgSelect db = new pgSelect();
		db.select(sql);

		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());
			}

			totalEntries5(modelMain, modelTotal);
		}

		else {
			_modelTotal = new modelBuyerPaymentsList();
			_modelTotal.addRow(new Object[] { "Total", null, null, null, 0.00,  null });

			_tblTotal = new _JTableTotal(_modelTotal, _tblMain);
			scrollMainSouth.setViewportView(_tblTotal);
			_tblTotal.setFont(dialog11Bold);
			_tblTotal.setTotalLabel(0);
		}


		_tblMain.packAll();
		computePaymentPerc();
	}
	
	public void displayCRB(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal) {

		FncTables.clearTable(modelMain);//Code to clear modelMain.
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.		

		int row = _tblMain.getSelectedRow();
		String rec_id = _tblMain.getValueAt(row,0).toString();
		
		String sql = 	

			"--displayCRB  \n" +
			"select * from ( \n" +
			
			"--#1 \n" +
			"select trim(a.acct_id) as acct_id,  \n" +
			"trim(d.acct_name), \n" + 
			"(case when a.trans_amt<0 then 0.00 else a.trans_amt end) as debit,\n" + 
			"(case when a.trans_amt>0 then 0.00 else -1*a.trans_amt end) as credit,\n" + 
			"a.remarks 	\n\n" + 

			"from \r\n" + 
			"( \n\n" +			
			"select * from rf_crb_detail \n" +
			"	where trim(pay_rec_id) = '"+rec_id+"' \n" +
			"	and co_id = '"+co_id+"' \n" +
			"	and status_id = 'A' 	\n" +
			"	and trans_amt <> 0 \n" +
			"	and entity_id = '"+entity_id+"' \r\n" + 
			"	and rb_id in (select rb_id from rf_crb_header where co_id = '"+co_id+"' \n" +
			"		and pay_rec_id = '"+rec_id+"' ) order by line_no ) as a   \n" + 

			"left join mf_boi_chart_of_accounts d   \r\n" + 
			"on a.acct_id = d.acct_id \n\n" +
			
			"union all --#2 (entries of previous AR, if in case AR and OR have the same date)  \n\n" + 			
			 
			"select trim(f.acct_id) as acct_id,  \r\n" + 
			"trim(g.acct_name), \r\n" + 
			"(case when f.trans_amt<0 then 0.00 else f.trans_amt end) as debit,\r\n" + 
			"(case when f.trans_amt>0 then 0.00 else -1*f.trans_amt end) as credit,\r\n" + 
			"f.remarks\r\n" + 
			"\r\n" + 
			"from ( select * from rf_payments where to_char(actual_date, 'MM-dd-yyyy') = to_char(or_date, 'MM-dd-yyyy')  and or_no = '"+crb_no+"' ) e \r\n" + 
			"\r\n" + 
			"left join ( select * from rf_crb_detail \r\n" + 
			"	where co_id = '"+co_id+"' \r\n" + 
			"	and status_id = 'A' 	\r\n" + 
			"	and trans_amt <> 0 \r\n" + 
			"	and entity_id = '"+entity_id+"' \r\n" + 
			"	and rb_id in (select rb_id from rf_crb_header where co_id = '"+co_id+"' \n" +
			"		and pay_rec_id = '"+rec_id+"') order by line_no ) f on e.ar_no = f.rb_id   \r\n" + 
			"	\r\n" + 
			"left join mf_boi_chart_of_accounts g on f.acct_id = g.acct_id \r\n" +
				
			") a \r\n" + 
			"\r\n" + 
			"where a.acct_id is not null \n" +
			"order by a.credit, a.debit \n\n" ;	

		System.out.printf("SQL: %s%n", sql);

		System.out.printf("SQL displayCRB: \n" + sql);
		pgSelect db = new pgSelect();
		db.select(sql);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());
			}
			totalEntries_CRB(modelMain, modelTotal);

		}	

		else {
			modelSample5Total = new modelByrPmtPosting_CRB();
			modelSample5Total.addRow(new Object[] { "Total", null,  new BigDecimal(0.00), new BigDecimal(0.00), null });	
			tblSample5Total = new _JTableTotal(modelSample5Total, tblSample5Main);
			scrollSample5Total.setViewportView(tblSample5Total);
			tblSample5Total.setFont(dialog11Bold);
			((_JTableTotal) tblSample5Total).setTotalLabel(0);}

		tblSample5Main.packAll();
	}
	
	public void displayLedger(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal) {

		FncTables.clearTable(modelMain);//Code to clear modelMain.
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.		

		int row = _tblMain.getSelectedRow();
		String rec_id = _tblMain.getValueAt(row,0).toString();
		
		String sql = 	

			"--displayLedger \n" +
			"select\r\n" + 
			"			--a.pay_rec_id, \r\n" + 
			"			to_char(a.date_paid::date, 'Mon dd, yyyy'),\r\n" + 
			"			to_char(a.sched_date::date, 'Mon dd, yyyy'),\r\n" + 
			"			coalesce(b.pmt_amount, null) as amount, \r\n" + 
			"			null as other_fees, \r\n" + 
			"			coalesce(d.amount, null) as proc_fee, \r\n" + 
			"			coalesce(e.amount, null) as res, \r\n" + 
			"			coalesce(f.amount, null) as dp, \r\n" + 
			"			coalesce(g.amount, null) as mri,\r\n" + 
			"			coalesce(h.amount, null) as fire, \r\n" + 
			"			coalesce(i.amount, null) as maf, \r\n" + 
			"			coalesce(j.amount, null) as vat, \r\n" + 
			"			coalesce(k.amount, null) as soi, \r\n" + 			
			"			coalesce(l.amount, null) as sop, \r\n" + 
			"			coalesce(m.amount, null) as cbp, \r\n" + 
			"			coalesce(n.amount, null) as adj, \r\n" + 
			"			coalesce(o.amount, null) as interest, \r\n" + 
			"			coalesce(p.amount, null) as principal, \r\n" + 
			"			q.balance as balance \r\n" + 
			"			from rf_client_ledger a\r\n" + 
			"\r\n" + 
			"				--amount paid \r\n" + 
			"				left join (select distinct on (pay_rec_id) pay_rec_id, date_paid, sched_date, pmt_amount \r\n" + 
			"				from rf_client_ledger \r\n" + 
			"				where entity_id='"+entity_id+"' \r\n" + 
			"				and pbl_id='"+pbl_id+"' \r\n" + 
			"				and seq_no="+seq_no+"\r\n" + 
			"			 	and status_id='A'\r\n" + 
			"				and pay_rec_id = '"+rec_id+"'  \n" +
			"				order by pay_rec_id, date_paid::date, sched_date::date asc) b \r\n" + 
			"				on b.date_paid=a.date_paid and b.sched_date=a.sched_date and b.pay_rec_id=a.pay_rec_id \r\n" + 
			"\r\n" + 
			"				--processing fee\r\n" + 
			"				left join (select a.sched_date, a.date_paid, sum(a.amount) as amount, pay_rec_id \r\n" + 
			"				from rf_client_ledger a \r\n" + 
			"				where a.pbl_id='"+pbl_id+"'\r\n" + 
			"				and a.entity_id='"+entity_id+"' \r\n" + 
			"				and a.seq_no="+seq_no+"\r\n" + 
			"				and a.pay_rec_id = '"+rec_id+"'  \n" +
			"				and a.part_id='019' \r\n" + 
			"			 	and a.status_id='A'\r\n" + 
			"				group by a.sched_date, a.date_paid, pay_rec_id \r\n" + 
			"				order by a.sched_date) d \r\n" + 
			"				on a.sched_date=d.sched_date::date and a.date_paid::date=d.date_paid::date and a.pay_rec_id=d.pay_rec_id \r\n" + 
			"\r\n" + 
			"				--reservation \r\n" + 
			"				left join (select a.sched_date::date, a.date_paid, sum(a.amount) as amount, pay_rec_id, part_id \r\n" + 
			"				from rf_client_ledger a \r\n" + 
			"				where a.pbl_id='"+pbl_id+"'\r\n" + 
			"				and a.entity_id='"+entity_id+"' \r\n" + 
			"				and a.seq_no="+seq_no+"\r\n" + 
			"				and a.pay_rec_id = '"+rec_id+"'  \n" +
			"				and a.part_id in ('012') \r\n" + 
			"			 	and a.status_id='A' \r\n" + 
			"				group by a.sched_date, a.date_paid, pay_rec_id, part_id\r\n" + 
			"				order by a.sched_date) e \r\n" + 
			"				on a.sched_date::date=e.sched_date::date and a.date_paid::date=e.date_paid::date and a.pay_rec_id=e.pay_rec_id   -- and a.part_id=e.part_id \r\n" + 
			"\r\n" + 
			"				--downpayment \r\n" + 
			"				left join (select a.sched_date, a.date_paid, sum(a.amount) as amount, pay_rec_id, part_id \r\n" + 
			"				from rf_client_ledger a\r\n" + 
			"				where a.pbl_id='"+pbl_id+"'\r\n" + 
			"				and a.entity_id='"+entity_id+"' \r\n" + 
			"				and a.seq_no="+seq_no+"\r\n" + 
			"				and a.pay_rec_id = '"+rec_id+"'  \n" +
			"				and a.part_id in ('013') \r\n" + 
			"			 	and a.status_id='A' \r\n" + 
			"				group by a.sched_date, a.date_paid, pay_rec_id, part_id \r\n" + 
			"				order by a.sched_date) f \r\n" + 
			"				on f.sched_date::date=a.sched_date::date and f.date_paid::date=a.date_paid::date and f.pay_rec_id=a.pay_rec_id    -- and f.part_id=a.part_id \r\n" + 
			"\r\n" + 
			"				--mri\r\n" + 
			"				left join (select a.sched_date, a.date_paid, sum(a.amount) as amount, pay_rec_id \r\n" + 
			"				from rf_client_ledger a \r\n" + 
			"				where a.pbl_id='"+pbl_id+"'\r\n" + 
			"				and a.entity_id='"+entity_id+"' \r\n" + 
			"				and a.seq_no="+seq_no+"\r\n" + 
			"				and a.pay_rec_id = '"+rec_id+"'  \n" +
			"				and a.part_id in ('003', '018') \r\n" + 
			"			 	and a.status_id='A' \r\n" + 
			"				group by a.sched_date, a.date_paid, pay_rec_id\r\n" + 
			"				order by a.sched_date) g \r\n" + 
			"				on g.sched_date=a.sched_date and g.date_paid::date=a.date_paid::date and g.pay_rec_id=a.pay_rec_id \r\n" + 
			"\r\n" + 
			"				--fire \r\n" + 
			"				left join (select a.sched_date, a.date_paid, sum(a.amount) as amount, pay_rec_id \r\n" + 
			"				from rf_client_ledger a\r\n" + 
			"				where a.pbl_id='"+pbl_id+"'\r\n" + 
			"				and a.entity_id='"+entity_id+"' \r\n" + 
			"				and a.seq_no="+seq_no+"\r\n" + 
			"				and a.pay_rec_id = '"+rec_id+"'  \n" +
			"				and a.part_id='004'\r\n" + 
			"			 	and a.status_id='A' \r\n" + 
			"				group by a.sched_date, a.date_paid, pay_rec_id \r\n" + 
			"				order by a.sched_date) h \r\n" + 
			"				on h.sched_date=a.sched_date::date and h.date_paid::date=a.date_paid::date and h.pay_rec_id=a.pay_rec_id \r\n" + 
			"\r\n" + 
			"				--maf \r\n" + 
			"				left join (select a.sched_date, a.date_paid, sum(a.amount) as amount, pay_rec_id \r\n" + 
			"				from rf_client_ledger a \r\n" + 
			"				where a.pbl_id='"+pbl_id+"'\r\n" + 
			"				and a.entity_id='"+entity_id+"' \r\n" + 
			"				and a.seq_no="+seq_no+"\r\n" + 
			"				and a.pay_rec_id = '"+rec_id+"'  \n" +
			"				and a.part_id='005' \r\n" + 
			"			 	and a.status_id='A' \r\n" + 
			"				group by a.sched_date, a.date_paid, pay_rec_id \r\n" + 
			"				order by a.sched_date) i \r\n" + 
			"				on i.sched_date=a.sched_date::date and i.date_paid::date=a.date_paid::date and i.pay_rec_id=a.pay_rec_id \r\n" + 
			"\r\n" + 
			"				--vat \r\n" + 
			"				left join (select a.sched_date, a.date_paid, sum(a.amount) as amount, pay_rec_id \r\n" + 
			"				from rf_client_ledger a\r\n" + 
			"				where a.pbl_id='"+pbl_id+"'\r\n" + 
			"				and a.entity_id='"+entity_id+"' \r\n" + 
			"				and a.seq_no="+seq_no+"\r\n" + 
			"				and a.pay_rec_id = '"+rec_id+"'  \n" +
			"				and a.part_id in ('008', '023') \r\n" + 
			"				and a.status_id='A' \r\n" + 
			"				group by a.sched_date, a.date_paid, pay_rec_id \r\n" + 
			"				order by a.sched_date) j \r\n" + 
			"				on j.sched_date=a.sched_date::date and j.date_paid::date=a.date_paid::date and j.pay_rec_id=a.pay_rec_id\r\n" + 
			"\r\n" + 
			"				--soi \r\n" + 
			"				left join (select a.sched_date, a.date_paid, sum(a.amount) as amount, pay_rec_id \r\n" + 
			"				from rf_client_ledger a \r\n" + 
			"				where a.pbl_id='"+pbl_id+"'\r\n" + 
			"				and a.entity_id='"+entity_id+"'\r\n" + 
			"				and a.seq_no="+seq_no+"\r\n" + 
			"				and a.pay_rec_id = '"+rec_id+"'  \n" +
			"				and a.part_id in ('006') \r\n" + 
			"			 	and a.status_id='A' \r\n" + 
			"				group by a.sched_date, a.date_paid, pay_rec_id \r\n" + 
			"				order by sched_date) k \r\n" + 
			"				on k.sched_date=a.sched_date::date and k.date_paid::date=a.date_paid::date and k.pay_rec_id=a.pay_rec_id \r\n" + 
			"\r\n" + 
			"				--sop \r\n" + 
			"				left join (select a.sched_date, a.date_paid, sum(a.amount) as amount, pay_rec_id \r\n" + 
			"				from rf_client_ledger a \r\n" + 
			"				where a.pbl_id='"+pbl_id+"'\r\n" + 
			"				and a.entity_id='"+entity_id+"' \r\n" + 
			"				and a.seq_no="+seq_no+"\r\n" + 
			"				and a.pay_rec_id = '"+rec_id+"'  \n" +
			"				and a.part_id in ('007', '020', '021') \r\n" + 
			"			 	and a.status_id='A' \r\n" + 
			"				group by a.sched_date, a.date_paid, pay_rec_id \r\n" + 
			"				order by a.sched_date) l \r\n" + 
			"				on l.sched_date=a.sched_date::date and l.date_paid::date=a.date_paid::date and l.pay_rec_id=a.pay_rec_id \r\n" + 
			"\r\n" + 
			"				--fc/cbp \r\n" + 
			"				left join (select a.sched_date, a.date_paid, sum(a.amount) as amount, pay_rec_id \r\n" + 
			"				from rf_client_ledger a \r\n" + 
			"				where a.pbl_id='"+pbl_id+"'\r\n" + 
			"				and a.entity_id='"+entity_id+"' \r\n" + 
			"				and a.seq_no="+seq_no+"\r\n" + 
			"				and a.pay_rec_id = '"+rec_id+"'  \n" +
			"				and a.part_id='009' \r\n" + 
			"			 	and a.status_id='A' \r\n" + 
			"				group by a.sched_date, a.date_paid, pay_rec_id \r\n" + 
			"				order by a.sched_date) m \r\n" + 
			"				on m.sched_date=a.sched_date::date and m.date_paid::date=a.date_paid::date and m.pay_rec_id=a.pay_rec_id \r\n" + 
			"\r\n" + 
			"				--adjustment \r\n" + 
			"				left join (select a.sched_date, a.date_paid, sum(a.amount) as amount, pay_rec_id \r\n" + 
			"				from rf_client_ledger a \r\n" + 
			"				where a.pbl_id='"+pbl_id+"'\r\n" + 
			"				and a.entity_id='"+entity_id+"' \r\n" + 
			"				and a.seq_no="+seq_no+"\r\n" + 
			"				and a.pay_rec_id = '"+rec_id+"'  \n" +
			"				and a.part_id in ('011', '016') \r\n" + 
			"			 	and a.status_id='A' \r\n" + 
			"				group by a.sched_date, a.date_paid, pay_rec_id \r\n" + 
			"				order by a.sched_date) n \r\n" + 
			"				on n.sched_date=a.sched_date::date and n.date_paid::date=a.date_paid::date and n.pay_rec_id=a.pay_rec_id \r\n" + 
			"\r\n" + 
			"				--interest \r\n" + 
			"				left join (select a.sched_date, a.date_paid, sum(a.amount) as amount, pay_rec_id \r\n" + 
			"				from rf_client_ledger a left join rf_client_schedule b \r\n" + 
			"				on a.entity_id=b.entity_id and a.pbl_id=b.pbl_id and a.sched_date=b.scheddate \r\n" + 
			"				where a.pbl_id='"+pbl_id+"'\r\n" + 
			"				and a.entity_id='"+entity_id+"' \r\n" + 
			"				and a.seq_no="+seq_no+"\r\n" + 
			"				and a.pay_rec_id = '"+rec_id+"'  \n" +
			"				and a.part_id in ('001', '015') \r\n" + 
			"			 	and a.status_id='A' \r\n" + 
			"				group by a.sched_date, a.date_paid, pay_rec_id \r\n" + 
			"				order by a.sched_date) o \r\n" + 
			"				on o.sched_date=a.sched_date::date and o.date_paid::date=a.date_paid::date and o.pay_rec_id=a.pay_rec_id \r\n" + 
			"\r\n" + 
			"				--monthly amortization \r\n" + 
			"				left join (select a.sched_date, a.date_paid, sum(a.amount) as amount, pay_rec_id, part_id \r\n" + 
			"				from rf_client_ledger a \r\n" + 
			"				where a.pbl_id='"+pbl_id+"'\r\n" + 
			"				and a.entity_id='"+entity_id+"' \r\n" + 
			"				and a.seq_no="+seq_no+"\r\n" + 
			"				and a.pay_rec_id = '"+rec_id+"'  \n" +
			"				and a.part_id in ('002') \r\n" + 
			"			 	and a.status_id='A' \r\n" + 
			"				group by a.sched_date, a.date_paid, pay_rec_id, part_id \r\n" + 
			"				order by a.sched_date) p \r\n" + 
			"				on p.sched_date=a.sched_date::date and p.date_paid::date=a.date_paid::date and p.pay_rec_id=a.pay_rec_id \r\n" + 
			"\r\n" + 
			"				--balance \r\n" + 
			"				left join (select distinct on (date_paid, pay_rec_id, sched_date, balance) * \r\n" + 
			"				from (select date_paid, pay_rec_id, sched_date, balance, part_id \r\n" + 
			"				from rf_client_ledger \r\n" + 
			"				where pbl_id='"+pbl_id+"'\r\n" + 
			"				and entity_id='"+entity_id+"' \r\n" + 
			"				and seq_no="+seq_no+"\r\n" + 
			"				and pay_rec_id = '"+rec_id+"'  \n" +
			"				and part_id in ('012', '013', '014') \r\n" + 
			"				and status_id='A' \r\n" + 
			"				--group by date_paid, pay_rec_id, sched_date, balance \r\n" + 
			"				--order by date_paid, sched_date, pay_rec_id \r\n" + 
			"			\r\n" + 
			/*"				union \r\n" + 
			"			\r\n" + 
			"				select date_paid, pay_rec_id, sched_date, balance, part_id \r\n" + 
			"				from rf_client_ledger \r\n" + 
			"				where pbl_id='"+pbl_id+"'\r\n" + 
			"				and entity_id='"+entity_id+"' \r\n" + 
			"				and seq_no="+seq_no+"\r\n" + 
			"				and pay_rec_id = '"+rec_id+"'  \n" +
			"				and part_id not in ('012', '013', '014') \r\n" + 
			"			 	and status_id='A' \r\n" + 
			"				--group by date_paid, pay_rec_id, sched_date, balance \r\n" + 
			"				order by date_paid, sched_date, pay_rec_id" +*/
			") a) q \r\n" + 
			"				on q.sched_date::date=a.sched_date::date and q.date_paid::date=a.date_paid::date and q.pay_rec_id=a.pay_rec_id-- and q.part_id=a.part_id \r\n" + 
			"			\r\n" + 
			"			where a.entity_id='"+entity_id+"' \r\n" + 
			"			and a.pbl_id='"+pbl_id+"'\r\n" + 
			"			and a.seq_no="+seq_no+"\r\n" + 
			"			and a.pay_rec_id = '"+rec_id+"'  \n" +
			"			and a.status_id='A' \r\n" + 
			"			--and a.pay_rec_id='\"+rec_id+\"' \r\n" + 
			"			\r\n" + 
			"			group by a.pay_rec_id, a.date_paid::date, a.sched_date::date, b.pmt_amount, d.amount, e.amount, f.amount, \r\n" + 
			"			g.amount, h.amount, i.amount, j.amount, k.amount, l.amount, m.amount, n.amount, o.amount, p.amount, \r\n" + 
			"			q.balance \r\n" + 
			"			\r\n" + 
			"			order by a.date_paid::date, a.sched_date::date  " ;


		System.out.printf("SQL displayLedger: \n" + sql);
		pgSelect db = new pgSelect();
		db.select(sql);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());
			}
			totalEntries_Ledger(modelMain, modelTotal);

		}	

		else {
			modelSample3Total = new modelByrPmtPosting_ledger();
			modelTotal.addRow(new Object[] { "Total", null,  new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00),
					new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00),
					new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00)});	
			tblSample3Total = new _JTableTotal(modelSample3Total, tblSample3Main);
			scrollSample3Total.setViewportView(tblSample3Total);
			tblSample3Total.setFont(dialog11Bold);
			((_JTableTotal) tblSample3Total).setTotalLabel(0);}
			
	}	
	
	public void displayJV_details(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal) {//used

		FncTables.clearTable(modelMain);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		String sql = 			
			"---------display JV details XX \r\n" + 
			"select \r\n" + 
			"\r\n" + 
			"coalesce(a.acct_id,''),\r\n" + 
			"coalesce(a.div_id,''),\r\n" + 
			"coalesce(a.dept_id,''),\r\n" + 
			"coalesce(a.sect_id,''),\r\n" + 
			"coalesce(a.project_id,''),\r\n" + 
			"coalesce(a.sub_projectid,''),\r\n" + 
			"coalesce(a.inter_busunit_id,''),\r\n" + 
			"coalesce(b.acct_name,''),\r\n" + 
			"( case when a.bal_side = 'D' then a.tran_amt else 0.00 end ) as debit,\r\n" + 
			"( case when a.bal_side = 'C' then a.tran_amt else 0.00 end ) as credit,\r\n" + 
			"(coalesce(a.ref_no,'')||coalesce(d.description,'')) as ref_no  \r\n" + 
			"\r\n" + 
			"from rf_jv_detail a\r\n" + 
			"left join mf_boi_chart_of_accounts b on a.acct_id = b.acct_id\r\n" +
			"left join rf_jv_header c on a.jv_no = c.jv_no " +
			"left join mf_unit_info d on a.pbl_id = d.pbl_id  and a.project_id = d.proj_id \n" + 
			"\r\n" + 
			"where trim(a.jv_no) = trim('"+sql_GetJV()+"') " +
					"and a.co_id = '"+co_id+"' \n" +
					"and a.status_id = 'A' \n" +
					"and a.pbl_id = '"+pbl_id+"' \n\n " +
			"order by a.pbl_id, a.bal_side desc, a.entry_no, a.line_no \n\n " ;

		System.out.printf("SQL #2: %s", sql);
		
		pgSelect db = new pgSelect();
		db.select(sql);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());				
			}	

			totalJV(modelMain, modelTotal);			
		}		

		else {
			modelSample4Total = new modelJV_acct_entries();
			modelSample4Total.addRow(new Object[] { "Total",null,  null, null, null, null, null, null, new BigDecimal(0.00), new BigDecimal(0.00), null});

			tblSample4Total = new _JTableTotal(modelSample4Total, tblSample4Main);
			scrollSample4Total.setViewportView(tblSample4Total);
			tblSample4Total.setFont(dialog11Bold);
			((_JTableTotal) tblSample4Total).setTotalLabel(0);}

		setTableWidth();		
		//adjustRowHeight_account();

	}	
	
	
	
	//enable, disable, refresh
	private void refreshFields(){

		lookupProject.setValue("");
		txtProject.setText("");
		lookupPhase.setValue("");
		txtPhase.setText("");
		lookupUnit.setText("");
		txtUnitDescription.setText("");
		txtSequence.setText("");
		txtClientID.setText("");
		txtClientName.setText("");	
		txtStatus.setText("");
		txtNSP.setText("0.00");
	}

	public static void enableButtons(Boolean a, Boolean b){

		btnViewCARD.setEnabled(a);
		btnCancel.setEnabled(b);

	}

	public void refresh_table_main(){

		FncTables.clearTable(_modelMain);
		FncTables.clearTable(_modelTotal);
		rowHeader = _tblMain.getRowHeader();
		scrollMainCenter.setRowHeaderView(rowHeader);
		_modelTotal.addRow(new Object[] {"Total", null, null, null, null, 0.00 });
	}
	
	public void refresh_table_crb_ledger(){

		FncTables.clearTable(modelSample5Main);
		FncTables.clearTable(modelSample5Total);
		rowHeaderSample5Main = tblSample5Main.getRowHeader();
		scrollSample5Main.setRowHeaderView(rowHeaderSample5Main);
		modelSample5Total.addRow(new Object[] { "Total", null,  new BigDecimal(0.00), new BigDecimal(0.00), null });	
		
		FncTables.clearTable(modelSample3Main);
		FncTables.clearTable(modelSample3Total);
		rowHeaderSample3Main = tblSample3Main.getRowHeader();
		scrollSample3Main.setRowHeaderView(rowHeaderSample3Main);
		modelSample3Total.addRow(new Object[] { "Total", null, new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00),
				new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00),
				new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00)});	

	}
	
	public void enablePhase(Boolean x){

		lblPhase.setEnabled(x);
		lookupPhase.setEnabled(x);
		txtPhase.setEnabled(x);		

	}

	public void enableUnitDetails(Boolean x){


		lblUnit.setEnabled(x);
		lookupUnit.setEnabled(x);
		txtUnitDescription.setEnabled(x);
		lblSequence.setEnabled(x);
		txtSequence.setEnabled(x);
		lblStatus.setEnabled(x);
		txtStatus.setEnabled(x);
		lblClient.setEnabled(x);
		txtClientID.setEnabled(x);
		txtClientName.setEnabled(x);
		lblNSP.setEnabled(x);
		txtNSP.setEnabled(x);

	}

	public void initialize_comp(){		
		
		co_id 		= "02";	
		company		= "CENQHOMES DEVELOPMENT CORPORATION";		
		company_logo = RequestForPayment.sql_getCompanyLogo();			
		
		txtCompany.setText(company);	
		lblProject.setEnabled(true);
		lookupProject.setEnabled(true);
		txtProject.setEnabled(true);	
		lookupProject.setLookupSQL(CheckStatusMonitoring.sql_project(co_id));
		
		lookupCompany.setValue(co_id);
}

	

	//table operation
	public void totalEntries5(DefaultTableModel modelMain, DefaultTableModel modelTotal) {

		BigDecimal amount 		= new BigDecimal(0.00);	
		BigDecimal amount_prin 	= new BigDecimal(0.00);	

		FncTables.clearTable(modelTotal);//Code to clear modelMain.
		for(int x=0; x < modelMain.getRowCount(); x++){

			try { amount 	= amount.add(((BigDecimal) modelMain.getValueAt(x, 5)));} 
			catch (NullPointerException e) { amount 	= amount.add(new BigDecimal(0.00)); }
			
			try { amount_prin 	= amount_prin.add(((BigDecimal) modelMain.getValueAt(x, 6)));} 
			catch (NullPointerException e) { amount_prin 	= amount_prin.add(new BigDecimal(0.00)); }

		}		
		modelTotal.addRow(new Object[] {"Total",  null, null,  null, null, amount, amount_prin });	

	}

	public void computePaymentPerc(){
		
		Double prin_amt_tot = 0.00;
		Double pmt_perc = 0.00;
		int row = _tblMain.getRowCount();
		int x = 0;
		
		while (x<row){			
			
			Double prin_amt = Double.parseDouble(_tblMain.getValueAt(x,6).toString());
			prin_amt_tot = prin_amt + prin_amt_tot;				
			pmt_perc = 	(prin_amt_tot / NSP ) * 100;
			
			_modelMain.setValueAt(pmt_perc, x, 7);			
			
			x++;
		}
		
		
	}
	
	public static void totalEntries_CRB(DefaultTableModel modelMain, DefaultTableModel modelTotal) {

		BigDecimal debit 		= new BigDecimal(0.00);	
		BigDecimal credit 		= new BigDecimal(0.00);	

		FncTables.clearTable(modelTotal);//Code to clear modelMain.
		for(int x=0; x < modelMain.getRowCount(); x++){

			try { debit 	= debit.add(((BigDecimal) modelMain.getValueAt(x, 2)));} 
			catch (NullPointerException e) { debit 	= debit.add(new BigDecimal(0.00)); }

			try { credit 	= credit.add(((BigDecimal) modelMain.getValueAt(x, 3)));} 
			catch (NullPointerException e) { credit = credit.add(new BigDecimal(0.00)); }
		}		
		modelTotal.addRow(new Object[] { "Total", null, debit, credit, null });	

	}
	
	public static void totalEntries_Ledger(DefaultTableModel modelMain, DefaultTableModel modelTotal) {

		BigDecimal amt_paid 	= new BigDecimal(0.00);	
		BigDecimal other_fees 	= new BigDecimal(0.00);	
		BigDecimal proc_fees 	= new BigDecimal(0.00);	
		BigDecimal res 			= new BigDecimal(0.00);
		BigDecimal dp 			= new BigDecimal(0.00);
		BigDecimal mri 			= new BigDecimal(0.00);
		BigDecimal fi 			= new BigDecimal(0.00);
		BigDecimal maf 			= new BigDecimal(0.00);
		BigDecimal vat 			= new BigDecimal(0.00);
		BigDecimal soi 			= new BigDecimal(0.00);
		BigDecimal sop 			= new BigDecimal(0.00);
		BigDecimal cbp 			= new BigDecimal(0.00);
		BigDecimal adjus 		= new BigDecimal(0.00);
		BigDecimal interest		= new BigDecimal(0.00);
		BigDecimal principal	= new BigDecimal(0.00);

		FncTables.clearTable(modelTotal);//Code to clear modelMain.
		for(int x=0; x < modelMain.getRowCount(); x++){
			try { amt_paid 	= amt_paid.add(((BigDecimal) modelMain.getValueAt(x, 2)));} 
			catch (NullPointerException e) { amt_paid 	= amt_paid.add(new BigDecimal(0.00)); }

			try { other_fees 	= other_fees.add(((BigDecimal) modelMain.getValueAt(x, 3)));} 
			catch (NullPointerException e) { other_fees 	= other_fees.add(new BigDecimal(0.00)); }

			try { proc_fees 	= proc_fees.add(((BigDecimal) modelMain.getValueAt(x, 4)));} 
			catch (NullPointerException e) { proc_fees 	= proc_fees.add(new BigDecimal(0.00)); }

			try { res 	= res.add(((BigDecimal) modelMain.getValueAt(x, 5)));} 
			catch (NullPointerException e) { res 	= res.add(new BigDecimal(0.00)); }

			try { dp 	= dp.add(((BigDecimal) modelMain.getValueAt(x, 6)));} 
			catch (NullPointerException e) { dp 	= dp.add(new BigDecimal(0.00)); }

			try { mri 	= mri.add(((BigDecimal) modelMain.getValueAt(x, 7)));} 
			catch (NullPointerException e) { mri 	= mri.add(new BigDecimal(0.00)); }

			try { fi 	= fi.add(((BigDecimal) modelMain.getValueAt(x, 8)));} 
			catch (NullPointerException e) { fi 	= fi.add(new BigDecimal(0.00)); }

			try { maf 	= maf.add(((BigDecimal) modelMain.getValueAt(x, 9)));} 
			catch (NullPointerException e) { maf 	= maf.add(new BigDecimal(0.00)); }

			try { vat 	= vat.add(((BigDecimal) modelMain.getValueAt(x, 10)));} 
			catch (NullPointerException e) { vat 	= vat.add(new BigDecimal(0.00)); }

			try { soi 	= soi.add(((BigDecimal) modelMain.getValueAt(x, 11)));} 
			catch (NullPointerException e) { soi 	= soi.add(new BigDecimal(0.00)); }

			try { sop 	= sop.add(((BigDecimal) modelMain.getValueAt(x, 12)));} 
			catch (NullPointerException e) { sop 	= sop.add(new BigDecimal(0.00)); }

			try { cbp 	= cbp.add(((BigDecimal) modelMain.getValueAt(x, 13)));} 
			catch (NullPointerException e) { cbp 	= cbp.add(new BigDecimal(0.00)); }

			try { adjus 	= adjus.add(((BigDecimal) modelMain.getValueAt(x, 14)));} 
			catch (NullPointerException e) { adjus 	= adjus.add(new BigDecimal(0.00)); }

			try { interest 	= interest.add(((BigDecimal) modelMain.getValueAt(x, 15)));} 
			catch (NullPointerException e) { interest 	= interest.add(new BigDecimal(0.00)); }

			try { principal 	= principal.add(((BigDecimal) modelMain.getValueAt(x, 16)));} 
			catch (NullPointerException e) { principal 	= principal.add(new BigDecimal(0.00)); }

		}		
		modelTotal.addRow(new Object[] {  "Total",null, amt_paid, other_fees, proc_fees, res, dp, mri, fi, maf, vat, soi, sop, cbp, adjus, interest, principal, null });	




	}	
	
	public void setTableWidth(){		
		tblSample4Main.packAll();
		tblSample4Main.getColumnModel().getColumn(0).setPreferredWidth(90);
		tblSample4Main.getColumnModel().getColumn(1).setPreferredWidth(40);
		tblSample4Main.getColumnModel().getColumn(2).setPreferredWidth(40);
		tblSample4Main.getColumnModel().getColumn(3).setPreferredWidth(40);
		tblSample4Main.getColumnModel().getColumn(4).setPreferredWidth(40);
		tblSample4Main.getColumnModel().getColumn(5).setPreferredWidth(40);
		tblSample4Main.getColumnModel().getColumn(6).setPreferredWidth(40);
		tblSample4Main.getColumnModel().getColumn(8).setPreferredWidth(100);
		tblSample4Main.getColumnModel().getColumn(9).setPreferredWidth(100);
	}
	
	public static void totalJV(DefaultTableModel modelMain, DefaultTableModel modelTotal) {//used

		FncTables.clearTable(modelTotal);//Code to clear modelMain.		

		BigDecimal debit 		= new BigDecimal(0.00);	
		BigDecimal credit		= new BigDecimal(0.00);	

		for(int x=0; x < modelMain.getRowCount(); x++){		

			try { debit 	= debit.add(((BigDecimal) modelMain.getValueAt(x,8)));} 
			catch (NullPointerException e) { debit 	= debit.add(new BigDecimal(0.00)); }

			try { credit 	= credit.add(((BigDecimal) modelMain.getValueAt(x,9)));} 
			catch (NullPointerException e) { credit 	= credit.add(new BigDecimal(0.00)); }

		}		

		modelTotal.addRow(new Object[] { "Total", null,  null, null, null, null, null,null, debit, credit });		
	}

	

	//select, loookup SQL
	public String getChartofAccount(){

		String sql = "select acct_id, acct_name, bs_is from mf_boi_chart_of_accounts where status_id = 'A' and w_subacct is null ";		
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
	
	public String getClientList(){//used

		return 

		"select \r\n" + 
		"\r\n" + 
		"a.entity_id as \"Client ID\",\r\n" +    //0
		"upper(trim(b.entity_name)) as \"Client Name\",\r\n" +   //1
		"trim(c.unit_id) as \"Unit ID\",\r\n" +   //2
		"trim(c.description) as \"Unit Desc.\",\r\n" + //3
		"trim(a.currentstatus) as \"Buyer Status ID\",\r\n" + //4
		"trim(d.status_desc) as \"Buyer Status Name\",\r\n" + //5
		"a.pbl_id as \"PBL ID\",\r\n" + //5
		"a.seq_no as \"Seq. No.\", \r\n" + //6
		"e.net_sprice " +  //7
		"\r\n" + 
		"from rf_sold_unit a\r\n" + 
		"left join rf_entity b on a.entity_id = b.entity_id \r\n" + 
		"left join mf_unit_info c on a.pbl_id = c.pbl_id \r\n" + 
		"left join mf_buyer_status d on a.currentstatus = d.byrstatus_id\r\n" +
		"left join rf_client_price_history e on a.pbl_id = e.pbl_id and a.seq_no = e.seq_no \n" + 
		"where c.sub_proj_id = '"+phase_id+"'  " +
		"and e.status_id = 'A' \n" +
		"\r\n" + 
		"order by b.entity_name \r\n" + 
		"" ;			

	}

	public static String getPhase(String proj_id) {
		
		String phase = "select\n" +
				"a.sub_proj_id, \n" +
				"getinteger(a.phase) as \"Phase\",\n" +
				"'Phase ' || trim(array_to_string(array_agg(trim(a.phase)), ' & ')) as \"Description\",\n" +
				"b.proj_alias || getinteger(a.phase) as \"Alias\"\n" +
				"from mf_sub_project a\n" +
				"left join mf_project b on a.proj_id = b.proj_id\n" +
				"where a.proj_id = '"+ proj_id +"'\n" +
				"and a.status_id = 'A'\n" +
				"group by a.sub_proj_id, getinteger(a.phase), b.proj_alias \n" +
				"order by a.sub_proj_id, getinteger(a.phase), b.proj_alias ;" ;
		
		return phase;
	}
	
	public String sql_GetJV() {

		String jv_no = "";

		String SQL = 
			"select jv_no from rf_jv_detail where trim(pbl_id) = '"+pbl_id+"' and project_id = '"+proj_id+"' and status_id = 'A' " ;

		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			if((String) db.getResult()[0][0]==null||db.getResult()[0][0].equals("null")) {jv_no = "";}
			else {jv_no = (String) db.getResult()[0][0]; }
		}else{
			jv_no = "";
		}

		return jv_no;
	}
	
	

}
