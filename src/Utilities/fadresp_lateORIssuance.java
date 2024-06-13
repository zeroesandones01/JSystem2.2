package Utilities;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jdesktop.swingx.JXPanel;

import Accounting.FixedAssets.panelAssetInformation;
import Buyers.CreditandCollections._RealTimeDebit;
import Database.pgSelect;
import Database.pgUpdate;
import DateChooser._JDateChooser;
import Functions.FncGlobal;
import Functions.FncLookAndFeel;
import Functions.FncReport;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import Renderer.DateRenderer;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JTableMain;
import interfaces._GUI;
import tablemodel.modelPagibigStatusMonitoring_MSVSMonitoring;
import tablemodel.model_spotcash_lateor;

public class fadresp_lateORIssuance extends _JInternalFrame implements _GUI, ActionListener {

	private static final long serialVersionUID = 6575110129729811589L;
	Dimension size = new Dimension(800, 600);
	static String title = "SPOT Cash OR Issuance";

	public _JLookup txtCoID;
	public _JLookup txtProID;
	public _JLookup txtPhaseID;
	public _JLookup txtPeriod; 
	
	public JTextField txtCo;
	public JTextField txtPro;
	public JTextField txtPhase;
	
	public static JButton btnRefresh;
	public static JButton btnApprove;
	public static JButton btnPreview;
	private JButton btnCancel;
	private JButton btnClear;

	public static JList rowPending;
	private _JTableMain tblPending;
	private JScrollPane scrollPending;
	private model_spotcash_lateor modelPending;
	
	public static JList rowCashCheck;
	private _JTableMain tblCashCheck;
	private JScrollPane scrollCashCheck;
	private model_spotcash_lateor modelCashCheck;
	
	private JTabbedPane tabORIssuance;
	
	private JXPanel panForApproval; 
	private JXPanel panORIssued;
	private JXPanel panApprovedPerPeriod;
	
	private JComboBox cboListParameters;
	private JComboBox cboListPeriod;
	private JComboBox cboDateParameters;
	
	private _JDateChooser dteFrom;
	private _JDateChooser dteTo; 
	
	private Integer[][] intArray = new Integer[][] {{0, 0}}; 
	
	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	
	private JXPanel panDiv2; 
	private JScrollPane scrLog; 
	private JTextArea txtLog; 

	public fadresp_lateORIssuance() {
		super(title, false, true, false, true);
		initGUI(); 
	}

	public fadresp_lateORIssuance(String title) {
		super(title);
		initGUI(); 
	}

	public fadresp_lateORIssuance(String title, boolean resizable, boolean closable, boolean maximizable,
			boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI(); 
	}

	public void initGUI() {
		this.setTitle(title);
		this.setSize(size);
		
		JXPanel panMain = new JXPanel(new BorderLayout(5, 5));
		getContentPane().add(panMain, BorderLayout.CENTER);
		panMain.setBorder(new EmptyBorder(5, 5, 5, 5));
		{
			{
				JXPanel panPage = new JXPanel(new BorderLayout(5, 5));
				panMain.add(panPage, BorderLayout.PAGE_START);
				panPage.setPreferredSize(new Dimension(0, 120));
				{
					JXPanel panDiv1 = new JXPanel(new BorderLayout(5, 5));
					panPage.add(panDiv1, BorderLayout.LINE_START);
					panDiv1.setPreferredSize(new Dimension(550, 0));
					panDiv1.setBorder(JTBorderFactory.createTitleBorder("Company, Project and Phase", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
					{
						JXPanel panPrimeLabel = new JXPanel(new GridLayout(3, 2, 5, 5));
						panDiv1.add(panPrimeLabel, BorderLayout.LINE_START);
						panPrimeLabel.setPreferredSize(new Dimension(200, 0));
						{
							JLabel lblCo = new JLabel("Company");
							panPrimeLabel.add(lblCo, BorderLayout.CENTER);
							lblCo.setHorizontalAlignment(JTextField.LEADING);
						}
						{
							txtCoID = new _JLookup();
							panPrimeLabel.add(txtCoID, BorderLayout.CENTER);
							txtCoID.setReturnColumn(0);
							txtCoID.setLookupSQL(SQL_COMPANY());
							txtCoID.setHorizontalAlignment(JTextField.CENTER);
							txtCoID.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent event) {
									Object[] data = ((_JLookup) event.getSource()).getDataSet();

									if (data!=null) {
										txtPeriod.setValue("");
	
										txtCoID.setValue(data[0].toString());
										txtCo.setText(data[1].toString());
										txtProID.setLookupSQL(SQL_PROJECT(txtCoID.getText()));
										
										txtProID.setValue("");
										txtPro.setText("");
										
										txtPeriod.setLookupSQL(period(txtCoID.getValue()));
									}
								}
							});
							txtCoID.addKeyListener(new KeyListener() {

								public void keyTyped(KeyEvent key) {
									if (key.getKeyChar()==KeyEvent.VK_BACK_SPACE) {
										txtCo.setText("");
										txtCoID.setValue("");
										
										txtProID.setValue("");
										txtPro.setText("");
										
										txtPhaseID.setValue("");
										txtPhase.setText("");
									}
								}

								public void keyReleased(KeyEvent arg0) {
									
								}

								public void keyPressed(KeyEvent arg0) {
									
								}
							});
						}
						{
							JLabel lblPro = new JLabel("Project");
							panPrimeLabel.add(lblPro, BorderLayout.CENTER);
							lblPro.setHorizontalAlignment(JTextField.LEADING);
						}
						{
							txtProID = new _JLookup();
							panPrimeLabel.add(txtProID, BorderLayout.CENTER);
							txtProID.setReturnColumn(0);
							txtProID.setHorizontalAlignment(JTextField.CENTER);
							txtProID.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent event) {
									Object[] data = ((_JLookup) event.getSource()).getDataSet();

									if (data!=null) {
										txtPhaseID.setValue("");
										txtPhase.setText("");
										
										txtProID.setValue(data[0].toString());
										txtPro.setText(data[1].toString());
										txtPhaseID.setLookupSQL(SQL_PHASE(txtProID.getText()));
									}
								}
							});
							txtProID.addKeyListener(new KeyListener() {

								public void keyTyped(KeyEvent key) {
									if (key.getKeyChar()==KeyEvent.VK_BACK_SPACE) {
										txtProID.setValue("");
										txtPro.setText("");
										
										txtPhaseID.setValue("");
										txtPhase.setText("");
									}
								}

								public void keyReleased(KeyEvent arg0) {
									
								}

								public void keyPressed(KeyEvent arg0) {
									
								}
							});
						}
						{
							JLabel lblPhase = new JLabel("Phase");
							panPrimeLabel.add(lblPhase, BorderLayout.CENTER);
							lblPhase.setHorizontalAlignment(JTextField.LEADING);
						}
						{
							txtPhaseID = new _JLookup();
							panPrimeLabel.add(txtPhaseID, BorderLayout.CENTER);
							txtPhaseID.setReturnColumn(0);
							txtPhaseID.setLookupSQL("");
							txtPhaseID.setHorizontalAlignment(JTextField.CENTER);
							txtPhaseID.setLookupSQL(SQL_PHASE(txtProID.getValue()));
							txtPhaseID.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent event) {
									Object data[] = ((_JLookup) event.getSource()).getDataSet();

									if (data!=null) {
										txtPhaseID.setText(data[0].toString());
										txtPhase.setText(data[1].toString());
									}
								}
							});
							txtPhaseID.addKeyListener(new KeyListener() {

								public void keyTyped(KeyEvent key) {
									if (key.getKeyChar()==KeyEvent.VK_BACK_SPACE) {
										txtPhaseID.setValue("");
										txtPhase.setText("");
									}
								}

								public void keyReleased(KeyEvent arg0) {
									
								}

								public void keyPressed(KeyEvent arg0) {
									
								}
							});
						}
						JXPanel panPrimeBox = new JXPanel(new GridLayout(3, 1, 5, 5));
						panDiv1.add(panPrimeBox, BorderLayout.CENTER);
						{
							txtCo = new JTextField();
							panPrimeBox.add(txtCo, BorderLayout.CENTER);
							txtCo.setHorizontalAlignment(JTextField.CENTER);
							txtCo.setEditable(false);
						}
						{
							txtPro = new JTextField();
							panPrimeBox.add(txtPro, BorderLayout.CENTER);
							txtPro.setHorizontalAlignment(JTextField.CENTER);
							txtPro.setEditable(false);
						}
						{
							txtPhase = new JTextField();
							panPrimeBox.add(txtPhase, BorderLayout.CENTER);
							txtPhase.setHorizontalAlignment(JTextField.CENTER);
							txtPhase.setEditable(false);
						}
						Setdefaults(txtCoID, txtCo, txtProID,txtPro);
					}
					{
						panDiv2 = new JXPanel(new GridLayout(2, 1, 5, 5));
						panPage.add(panDiv2, BorderLayout.CENTER);
						{
							{
								btnRefresh = new JButton("Refresh");
								panDiv2.add(btnRefresh);
								btnRefresh.setActionCommand("Refresh");
								btnRefresh.addActionListener(this);	
							}
							{
								txtLog = new JTextArea("");
								txtLog.setFont(new Font("Tahoma", Font.PLAIN, 8));
								txtLog.setBackground(Color.BLACK);
								txtLog.setForeground(Color.WHITE);
								txtLog.setEditable(false);
								
								scrLog = new JScrollPane(txtLog);
								panDiv2.add(scrLog);
							}
						}
					}
				}
			}
			{
				JXPanel panCenter = new JXPanel(new BorderLayout(5, 5)); 
				panMain.add(panCenter, BorderLayout.CENTER); 
				{
					CreateTabs();
					
					tabORIssuance = new JTabbedPane(); 
					panCenter.add(tabORIssuance, BorderLayout.CENTER); 
					{
						tabORIssuance.add("               For Approval               ", panForApproval);
						tabORIssuance.add("                OR Issued                 ", panORIssued); 
						tabORIssuance.add("           Approved per Period            ", panApprovedPerPeriod);
					}
					tabORIssuance.addChangeListener(new ChangeListener() {
						public void stateChanged(ChangeEvent act) {
							if (tabORIssuance.getSelectedIndex()==0) {
								btnRefresh.setEnabled(true);
								btnApprove.setEnabled((Boolean) (intArray[0][0].toString().equals("0")?false:true));
								btnPreview.setEnabled((Boolean) (intArray[0][1].toString().equals("0")?false:true));
							} else {
								btnRefresh.setEnabled(false);
								btnApprove.setEnabled(false);
								btnPreview.setEnabled(true);
								cboListParameters.setSelectedIndex(0);
							}
						}
					});
					//tabORIssuance.setEnabledAt(2, false);
				}
			}
			{
				JXPanel panButtons = new JXPanel(new GridLayout(1, 3, 5, 5)); 
				panMain.add(panButtons, BorderLayout.PAGE_END); 
				panButtons.setPreferredSize(new Dimension(0, 30));
				{
					{
						btnApprove = new JButton("Approve"); 
						panButtons.add(btnApprove); 
						btnApprove.setActionCommand("Approve");
						btnApprove.addActionListener(this);
					}
					{
						btnPreview = new JButton("Preview"); 
						panButtons.add(btnPreview); 
						btnPreview.setActionCommand("Preview");
						btnPreview.addActionListener(this);						
					}
					{
						btnCancel = new JButton("Cancel");
						panButtons.add(btnCancel);
						btnCancel.setActionCommand("Cancel");
						btnCancel.addActionListener(this);
						btnCancel.setEnabled(false);
					}
				}
			}
		}
		
		stateButton(true, false); 
	}

	private void Setdefaults(_JLookup _txtCoID, JTextField _txtCo, _JLookup _txtProID, JTextField _txtPro) {
		String strCount = "";
		Integer intCount = 0;

		/*	Company Default	*/
		strCount = GetValue("SELECT TRIM(COUNT(*)::CHAR(1)) FROM mf_company");
		intCount = Integer.parseInt(strCount);

		if (intCount > 1) {
			_txtCoID.setValue("");
			_txtCo.setText("[ Company ]");
		} else {
			_txtCoID.setValue(GetValue("SELECT co_id FROM mf_company LIMIT 1"));

			try {
				String strCo = GetValue("SELECT company_name FROM mf_company WHERE co_id = '"+_txtCoID.getValue()+"'");
				System.out.println(strCo);
				_txtCo.setText(strCo);
			} catch (NullPointerException e) {
				System.out.println("");
				System.out.println("txtCoID: " + _txtCoID.getValue());
				System.out.println("txtCoName: " + GetValue("SELECT company_name FROM mf_company WHERE co_id = '"+_txtCoID.getValue()+"'"));
			}
		}

		/*	Project Default	*/
		strCount = GetValue("SELECT TRIM(COUNT(*)::CHAR(1)) FROM mf_project");
		intCount = Integer.parseInt(strCount);

		if (intCount > 1) {
			_txtProID.setValue("");
			_txtPro.setText("");
		} else {
			_txtProID.setValue(GetValue("SELECT proj_id FROM mf_project LIMIT 1"));

			try {
				String strPro = GetValue("SELECT proj_name FROM mf_project WHERE proj_id = '"+_txtProID.getValue()+"'");
				System.out.println(strPro);
				_txtPro.setText(strPro);	
			} catch (NullPointerException e) {
				System.out.println("");
				System.out.println("txtProjectID: " + _txtProID.getValue());
				System.out.println(GetValue("SELECT proj_name FROM mf_project WHERE proj_id = '"+_txtProID.getValue()+"'"));
			}
		}
		_txtCoID.setValue("02");
		_txtCo.setText("CENQHOMES DEVELOPMENT CORPORATION");

		_txtProID.setValue("015");
		_txtPro.setText("TERRAVERDE RESIDENCES");

		txtPhaseID.setLookupSQL(SQL_PHASE(txtProID.getValue()));
		
		txtPhaseID.setValue("");
		txtPhase.setText("");
	}
	
	private void CreateTabs() {
		panForApproval = new JXPanel(new BorderLayout(5, 5)); 
		panForApproval.setBorder(new EmptyBorder(5, 5, 5, 5));
		{
			{
				JXPanel panFilters = new JXPanel(new GridLayout(1, 2, 5, 5)); 
				panForApproval.add(panFilters, BorderLayout.PAGE_START); 
				panFilters.setPreferredSize(new Dimension(0, 60));
				{
					{
						JXPanel panList = new JXPanel(new BorderLayout(5, 5)); 
						panFilters.add(panList); 
						panList.setBorder(JTBorderFactory.createTitleBorder("", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
						{
							{
								JLabel lblList = new JLabel("List Type"); 
								panList.add(lblList, BorderLayout.LINE_START); 
								lblList.setPreferredSize(new Dimension(75, 0));
								lblList.setHorizontalAlignment(JLabel.LEADING);
							}
							{
								JXPanel panListParameters = new JXPanel(new BorderLayout(5, 5)); 
								panList.add(panListParameters, BorderLayout.CENTER);
								{
									cboListParameters = new JComboBox(new String[] {"All Payments", "Spot & Deferred Cash"});
									panListParameters.add(cboListParameters, BorderLayout.CENTER);
									
									System.out.println("");
									System.out.println("User: " + UserInfo.EmployeeCode);
									
									if (!UserInfo.EmployeeCode.equals("900748")) {
										cboListParameters.setSelectedIndex(1);
									}
								}
							}
						}
					}
					{
						JXPanel panPeriod = new JXPanel(new BorderLayout(5, 5)); 
						panFilters.add(panPeriod); 
						panPeriod.setBorder(JTBorderFactory.createTitleBorder("", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
						{
							{
								JLabel lblPeriod = new JLabel("Batch No"); 
								panPeriod.add(lblPeriod, BorderLayout.LINE_START); 
								lblPeriod.setPreferredSize(new Dimension(75, 0));
								lblPeriod.setHorizontalAlignment(JLabel.LEADING);
							}
							{
								JXPanel panListPeriod = new JXPanel(new BorderLayout(5, 5)); 
								panPeriod.add(panListPeriod, BorderLayout.CENTER);
								{
									txtPeriod = new _JLookup(); 
									panListPeriod.add(txtPeriod);
									txtPeriod.setReturnColumn(0);
									txtPeriod.setLookupSQL(period("02"));
									txtPeriod.setHorizontalAlignment(JTextField.CENTER);
									txtPeriod.addLookupListener(new LookupListener() {
										public void lookupPerformed(LookupEvent event) {
											System.out.println("Batch lookup ?");
											stateButton(false, true);
											
											Object data[] = ((_JLookup) event.getSource()).getDataSet();
											String batch_no = (String) data[0];
											Date date_issued = (Date) data[2];
											displayApprovedBatchNo(batch_no);
											if(date_issued == null) {
												btnCancel.setEnabled(true);
											}else {
												btnCancel.setEnabled(false);
											}
										}
									});
									txtPeriod.addFocusListener(new FocusListener() {
										
										@Override
										public void focusLost(FocusEvent e) {
											// TODO Auto-generated method stub
											
										}
										
										@Override
										public void focusGained(FocusEvent e) {
											//if(UserInfo.EmployeeCode.equals("900876")) {
												txtPeriod.setLookupSQL(batchNo(txtCoID.getValue()));
											//}
											//
										}
									});
								}
							}
						}
					}
				}
			}
			{
				JXPanel panCenter = new JXPanel(new BorderLayout(5, 5)); 
				panForApproval.add(panCenter, BorderLayout.CENTER); 
				{
					scrollPending = new JScrollPane();
					panCenter.add(scrollPending, BorderLayout.CENTER);
					{
						modelPending = new model_spotcash_lateor();
						tblPending = new _JTableMain(modelPending);
						scrollPending.setViewportView(tblPending);
						scrollPending.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
						
						tblPending.getColumnModel().getColumn(1).setPreferredWidth(75);
						tblPending.getColumnModel().getColumn(2).setPreferredWidth(100);
						tblPending.getColumnModel().getColumn(3).setPreferredWidth(250);
						tblPending.getColumnModel().getColumn(4).setPreferredWidth(125);
						tblPending.getColumnModel().getColumn(5).setPreferredWidth(125);
						tblPending.getColumnModel().getColumn(6).setPreferredWidth(75);
						tblPending.getColumnModel().getColumn(7).setPreferredWidth(100);
						tblPending.getColumnModel().getColumn(8).setPreferredWidth(75);
						tblPending.getColumnModel().getColumn(9).setPreferredWidth(100); 
						tblPending.getColumnModel().getColumn(10).setPreferredWidth(75); 
						tblPending.getColumnModel().getColumn(11).setPreferredWidth(250);
						tblPending.getColumnModel().getColumn(12).setPreferredWidth(100);
						tblPending.getColumnModel().getColumn(13).setPreferredWidth(75);
						tblPending.getColumnModel().getColumn(14).setPreferredWidth(100);
						tblPending.getColumnModel().getColumn(15).setPreferredWidth(100);
						tblPending.getColumnModel().getColumn(16).setPreferredWidth(100);  
						
						tblPending.getColumnModel().getColumn(2).setCellRenderer(new DateRenderer(sdf));
						tblPending.getColumnModel().getColumn(9).setCellRenderer(new DateRenderer(sdf)); 
						tblPending.getColumnModel().getColumn(12).setCellRenderer(new DateRenderer(sdf)); 
						tblPending.getColumnModel().getColumn(14).setCellRenderer(new DateRenderer(sdf));
						
						tblPending.hideColumns("Pay Rec ID");
					}
					{
						rowPending = tblPending.getRowHeader();
						rowPending.setModel(new DefaultListModel());
						scrollPending.setRowHeaderView(rowPending);
						scrollPending.setCorner(JScrollPane.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
					}
				}
			}
		}
		
		panORIssued = new JXPanel(new BorderLayout(5, 5)); 
		panORIssued.setBorder(new EmptyBorder(5, 5, 5, 5));
		{
			{
				JXPanel panPage = new JXPanel(new BorderLayout(5, 5)); 
				panORIssued.add(panPage, BorderLayout.PAGE_START); 
				panPage.setPreferredSize(new Dimension(0, 60));
				{
					{
						JXPanel panCbo = new JXPanel(new BorderLayout(5, 5)); 
						panPage.add(panCbo, BorderLayout.LINE_START); 
						panCbo.setPreferredSize(new Dimension(300, 0));
						panCbo.setBorder(JTBorderFactory.createTitleBorder("", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
						{
							cboDateParameters = new JComboBox(new String[] {"OR Date", "Date Issued"});
							panCbo.add(cboDateParameters, BorderLayout.CENTER); 
						}
					}
					{
						JXPanel panDate = new JXPanel(new GridLayout(1, 2, 5, 5)); 
						panPage.add(panDate, BorderLayout.CENTER); 
						panDate.setBorder(JTBorderFactory.createTitleBorder("Date Details", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
						{
							{
								JXPanel panFrom = new JXPanel(new BorderLayout(5, 5)); 
								panDate.add(panFrom); 
								{
									{
										JLabel lblFrom = new JLabel("From"); 
										panFrom.add(lblFrom, BorderLayout.LINE_START); 
										lblFrom.setPreferredSize(new Dimension(50, 0));
									}
									{
										dteFrom = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
										panFrom.add(dteFrom);
										dteFrom.getCalendarButton().setVisible(true);
										dteFrom.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
									}
								}
							}
							{
								JXPanel panTo = new JXPanel(new BorderLayout(5, 5)); 
								panDate.add(panTo); 
								{
									{
										JLabel lblTo = new JLabel("To"); 
										panTo.add(lblTo, BorderLayout.LINE_START); 
										lblTo.setHorizontalAlignment(JLabel.CENTER);
										lblTo.setPreferredSize(new Dimension(50, 0));
									}
									{
										dteTo = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
										panTo.add(dteTo);
										dteTo.getCalendarButton().setVisible(true);
										dteTo.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
									}
								}
							}
						}
					}
				}	
			}
			{
				JXPanel panCenter = new JXPanel(new BorderLayout(5, 5)); 
				panORIssued.add(panCenter, BorderLayout.CENTER); 
				{
					scrollCashCheck = new JScrollPane();
					panCenter.add(scrollCashCheck, BorderLayout.CENTER);
					{
						modelCashCheck = new model_spotcash_lateor();
						tblCashCheck = new _JTableMain(modelCashCheck);
						scrollCashCheck.setViewportView(tblCashCheck);
						scrollCashCheck.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
						
						tblCashCheck.getColumnModel().getColumn(1).setPreferredWidth(75);
						tblCashCheck.getColumnModel().getColumn(2).setPreferredWidth(100);
						tblCashCheck.getColumnModel().getColumn(3).setPreferredWidth(250);
						tblCashCheck.getColumnModel().getColumn(4).setPreferredWidth(125);
						tblCashCheck.getColumnModel().getColumn(5).setPreferredWidth(125);
						tblCashCheck.getColumnModel().getColumn(6).setPreferredWidth(75);
						tblCashCheck.getColumnModel().getColumn(7).setPreferredWidth(100);
						tblCashCheck.getColumnModel().getColumn(8).setPreferredWidth(75);
						tblCashCheck.getColumnModel().getColumn(9).setPreferredWidth(100); 
						tblCashCheck.getColumnModel().getColumn(10).setPreferredWidth(75); 
						tblCashCheck.getColumnModel().getColumn(11).setPreferredWidth(250);
						tblCashCheck.getColumnModel().getColumn(12).setPreferredWidth(100);
						tblCashCheck.getColumnModel().getColumn(13).setPreferredWidth(75);
						tblCashCheck.getColumnModel().getColumn(14).setPreferredWidth(100);
						tblCashCheck.getColumnModel().getColumn(15).setPreferredWidth(100);
						tblCashCheck.getColumnModel().getColumn(16).setPreferredWidth(100);  
						
						tblCashCheck.getColumnModel().getColumn(2).setCellRenderer(new DateRenderer(sdf));
						tblCashCheck.getColumnModel().getColumn(9).setCellRenderer(new DateRenderer(sdf)); 
						tblCashCheck.getColumnModel().getColumn(12).setCellRenderer(new DateRenderer(sdf)); 
						tblCashCheck.getColumnModel().getColumn(14).setCellRenderer(new DateRenderer(sdf));
						
						tblCashCheck.hideColumns("Tag", "Pay Rec ID");
					}
					{
						rowCashCheck = tblCashCheck.getRowHeader();
						rowCashCheck.setModel(new DefaultListModel());
						scrollCashCheck.setRowHeaderView(rowCashCheck);
						scrollCashCheck.setCorner(JScrollPane.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
					}
				}
			}
		} 
		
		panApprovedPerPeriod = new JXPanel(new BorderLayout(5, 5)); 
		panApprovedPerPeriod.setBorder(new EmptyBorder(5, 5, 5, 5));
		{
			{
				JXPanel panPage = new JXPanel(new BorderLayout(5, 5)); 
				panApprovedPerPeriod.add(panPage, BorderLayout.PAGE_START); 
				panPage.setPreferredSize(new Dimension(0, 60));
				{
					{
						JXPanel panCbo = new JXPanel(new BorderLayout(5, 5)); 
						panPage.add(panCbo, BorderLayout.CENTER); 
						panCbo.setBorder(JTBorderFactory.createTitleBorder("", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
						{
							cboListPeriod = new JComboBox();
							panCbo.add(cboListPeriod, BorderLayout.CENTER); 
						}
						
						FillCombo(); 
					}
				}	
			}
		}
	}
	
	private void displayApprovedBatchNo(String batch_no) {
		pgSelect db = new pgSelect();
		DefaultListModel listModelPending = new DefaultListModel();
		
		FncTables.clearTable(modelPending);
		rowPending.setModel(listModelPending);
		String SQL = "SELECT * FROM view_spotcash_orissuance_batch('"+batch_no+"')";
		db.select(SQL);
		
		if (db.isNotNull()) {
			for (int x = 0; x < db.getRowCount(); x++) {
				modelPending.addRow(db.getResult()[x]);
				listModelPending.addElement(modelPending.getRowCount());
			}
		}
	}
	
	private void RefreshListIndexOne() {
		pgSelect dbExec = new pgSelect();
		DefaultListModel listModelPending = new DefaultListModel();
		
		FncTables.clearTable(modelPending);
		rowPending.setModel(listModelPending);
		
		dbExec = new pgSelect(); 
		dbExec.select("select * \n" +
				"from view_spotcash_orissuance('"+txtCoID.getValue().toString()+"', '"+txtProID.getValue().toString()+"', '"+txtPhaseID.getValue().toString()+"', '"+dteFrom.getDate().toString()+"', '"+dteTo.getDate().toString()+"', '"+cboListParameters.getSelectedIndex()+"'::int, 0, false) x \n"+
				"where not exists(select * from rf_orlist y where x.c_name = y.name and x.c_unit = y.unit and y.date_issued::date is null AND status_id = 'A' and y.pay_rec_id = c_pay_rec_id) \n" +
				"order by x.c_type, x.c_or_date, x.c_or_no, x.c_name, x.c_unit");
		
		System.out.println(""); 
		System.out.println("select * \n" +
				"from view_spotcash_orissuance('"+txtCoID.getValue().toString()+"', '"+txtProID.getValue().toString()+"', '"+txtPhaseID.getValue().toString()+"', '"+dteFrom.getDate().toString()+"', '"+dteTo.getDate().toString()+"', '"+cboListParameters.getSelectedIndex()+"'::int, 0, false) x \n"+
				"where not exists(select * from rf_orlist y where x.c_name = y.name and x.c_unit = y.unit and y.date_issued::date is null AND status_id = 'A' and y.pay_rec_id = c_pay_rec_id) \n" +
				"order by x.c_type, x.c_or_date, x.c_or_no, x.c_name, x.c_unit");
		
		if (dbExec.getRowCount()>0) {
			for (int x = 0; x < dbExec.getRowCount(); x++) {
				modelPending.addRow(dbExec.getResult()[x]);
				listModelPending.addElement(modelPending.getRowCount());
			}
			
			FillCombo();
		} else {
			JOptionPane.showMessageDialog(getContentPane(), "No list was generated.");
		}
		
		 
	}
	
	private void RefreshListIndexTwo() {
		pgSelect dbExec = new pgSelect();
		DefaultListModel listModel = new DefaultListModel();
		
		FncTables.clearTable(modelCashCheck);
		rowCashCheck.setModel(listModel);
		
		dbExec = new pgSelect(); 
		dbExec.select("select * \n" +
				"from view_spotcash_orissuance('"+txtCoID.getValue().toString()+"', '"+txtProID.getValue().toString()+"', '"+txtPhaseID.getValue().toString()+"', '"+dteFrom.getDate().toString()+"'::date, '"+dteTo.getDate().toString()+"'::date, '"+cboListParameters.getSelectedIndex()+"'::int, '"+cboDateParameters.getSelectedIndex()+"'::int, true) \n" + 
				"order by c_type, c_or_date, c_or_no, c_name, c_unit");

		System.out.println(""); 
		System.out.println("select * \n" +
				"from view_spotcash_orissuance('"+txtCoID.getValue().toString()+"', '"+txtProID.getValue().toString()+"', '"+txtPhaseID.getValue().toString()+"', '"+dteFrom.getDate().toString()+"'::date, '"+dteTo.getDate().toString()+"'::date, '"+cboListParameters.getSelectedIndex()+"'::int, '"+cboDateParameters.getSelectedIndex()+"'::int, true) \n" +
				"order by c_type, c_or_date, c_or_no, c_name, c_unit");
		
		if (dbExec.getRowCount()>0) {
			for (int x = 0; x < dbExec.getRowCount(); x++) {
				modelCashCheck.addRow(dbExec.getResult()[x]);
				listModel.addElement(modelCashCheck.getRowCount());
			}
		}
	}
	
	public void actionPerformed(ActionEvent act) {
		if (act.getActionCommand().equals("Refresh")) {
			refresh(); 
		} else if (act.getActionCommand().equals("Approve")) {
			@SuppressWarnings("unused")
			boolean blnWith = false; 
			pgUpdate dbExec = new pgUpdate(); 
			
			if ((JOptionPane.showConfirmDialog(getContentPane(), "Are you sure you approve of these accounts?", "Approve", 
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE))==JOptionPane.YES_OPTION) {
				/*
				dbExec = new pgUpdate();
				dbExec.executeUpdate("delete from rf_orlist where or_no is null", false); 
				dbExec.commit();
				*/ 
				String batch_no = sqlBatchNo();
				
				for (int x = 0; x < modelPending.getRowCount(); x++) {
					if ((boolean) modelPending.getValueAt(x, 0)) {
						blnWith = true;
						String strTranDate = FncGlobal.GetString("select trans_date::date::varchar from rf_payments where pay_rec_id::int = '"+modelPending.getValueAt(x, 15).toString()+"'::int");
						String strLTSDate = FncGlobal.GetString("select x.lts_date::date::varchar \n" + 
								"from mf_sub_project x \n" + 
								"inner join mf_unit_info y on y.proj_id = x.proj_id and y.phase = x.phase \n" + 
								"inner join rf_payments z on y.proj_id = z.proj_id and y.pbl_id = z.pbl_id \n" + 
								"where z.pay_rec_id::int = '"+modelPending.getValueAt(x, 15)+"'::int and x.status_id = 'A'");//ADDED STATUS ID BY LESTER DCRF 2718 
						String strBOIDate = FncGlobal.GetString("select x.boi::date::varchar \n" + 
								"from mf_sub_project x \n" + 
								"inner join mf_unit_info y on y.proj_id = x.proj_id and y.phase = x.phase \n" + 
								"inner join rf_payments z on y.proj_id = z.proj_id and y.pbl_id = z.pbl_id \n" + 
								"where z.pay_rec_id::int = '"+modelPending.getValueAt(x, 15)+"'::int and x.status_id = 'A'");//ADDED STATUS ID BY LESTER DCRF 2718
						
						System.out.println("");
						System.out.println("modelPending.getValueAt(x, 6).toString(): " + modelPending.getValueAt(x, 6).toString());  
						
						if (modelPending.getValueAt(x, 6).toString().equals("A.CASH")) {
							dbExec = new pgUpdate();
							dbExec.executeUpdate("INSERT INTO rf_orlist(name, unit, particulars, type, amount, trans_date, \n" + 
									"officially_reserved, cleared_date, lts, boi, percentage, pay_rec_id, \n" + 
									"check_no, check_date, or_no, date_issued, issued_by, date_approved, co_id, status_id, batch_no)\n" + 
									"VALUES ('"+modelPending.getValueAt(x, 3)+"', "
											+ "'"+modelPending.getValueAt(x, 4)+"', "
											+ "'"+FncGlobal.GetString("select particulars from mf_pay_particular where partdesc ~* '"+modelPending.getValueAt(x, 5)+"'")+"', "
											+ "'CASH', "
											+ "'"+modelPending.getValueAt(x, 7)+"'::numeric(19, 2),"
											+ " nullif('"+strTranDate+"','')::date, \n" +
									"(case when '"+modelPending.getValueAt(x, 9)+"' = 'null' then nullif('"+modelPending.getValueAt(x, 9)+"','null')::date else nullif('"+modelPending.getValueAt(x, 9)+"','')::date  end), null, "
									+ "(case when '"+strLTSDate+"' = '' then nullif('"+strLTSDate+"','')::date else nullif('"+strLTSDate+"','null')::date end), "
									+ "(case when '"+strBOIDate+"' = '' then nullif('"+strBOIDate+"','')::date else nullif('"+strBOIDate+"','null')::date end), '0.00'::numeric(19, 2), '"+modelPending.getValueAt(x, 15)+"'::int, \n" +
									"null, null, null, null, null, now(), '"+txtCoID.getValue()+"', 'A', '"+batch_no+"');", true);
							dbExec.commit();	
						} else {
							dbExec = new pgUpdate();
							dbExec.executeUpdate("INSERT INTO rf_orlist(name, unit, particulars, type, amount, trans_date, \n" + 
									"officially_reserved, cleared_date, lts, boi, percentage, pay_rec_id, \n" + 
									"check_no, check_date, or_no, date_issued, issued_by, date_approved, co_id, status_id, batch_no)\n" + 
									"VALUES ('"+modelPending.getValueAt(x, 3)+"', '"+modelPending.getValueAt(x, 4)+"', '"+modelPending.getValueAt(x, 5)+"', 'CHECK', '"+modelPending.getValueAt(x, 7)+"'::numeric(19, 2), "
									+ "(case when '"+strTranDate+"' = '' then nullif('"+strTranDate+"','')::date else nullif('"+strTranDate+"','null')::date end), \n" +
									"(case when '"+modelPending.getValueAt(x, 9)+"' = 'null' then nullif('"+modelPending.getValueAt(x, 9)+"','null')::date else nullif('"+modelPending.getValueAt(x, 9)+"','')::date end), "
									+ "'"+modelPending.getValueAt(x, 12)+"', "
									+ "(case when '"+strLTSDate+"' = '' then nullif('"+strLTSDate+"','')::date else nullif('"+strLTSDate+"','null')::date end), "
									+ "(case when '"+strBOIDate+"' = '' then nullif('"+strBOIDate+"','')::date else nullif('"+strBOIDate+"','null')::date end), '0.00'::numeric(19, 2), '"+modelPending.getValueAt(x, 15)+"'::int, \n" +
									"'"+modelPending.getValueAt(x, 13)+"', nullif('"+modelPending.getValueAt(x, 14)+"','')::date, null, null, null, now(), '"+txtCoID.getValue()+"', 'A', '"+batch_no+"');", true);
							dbExec.commit();	
						}
					}
				}
				
				JOptionPane.showMessageDialog(getContentPane(), "Receipts approved!");
				txtPeriod.setValue(FncGlobal.getDateSQL());
				FillCombo(); 
				stateButton(false, true);
			} else {
				
			}
		} else if (act.getActionCommand().equals("Preview")) {
			

			if (tabORIssuance.getSelectedIndex()==0) {
				boolean checker = checkIfIssued(txtPeriod.getValue(), txtCoID.getValue());
				if (checker) {
					System.out.println("Issued already");
					/*
					 * Added by Jari Cruz asof Oct 18 2022
					 * Reason : para pag issued na sya ung i produce nya is ung nasa isang tab at ung laman lang is ung nasa batch
					 * */
					Map<String, Object> mapParameters2 = new HashMap<String, Object>();
					mapParameters2.put("01_Company", txtCo.getText());
					mapParameters2.put("02_User", UserInfo.EmployeeCode);
					mapParameters2.put("03_dateFrom", dteFrom.getDate());
					mapParameters2.put("04_Identifier", cboDateParameters.getSelectedIndex());
					mapParameters2.put("05_dateTo", dteTo.getDate());
					mapParameters2.put("06_period", txtPeriod.getValue());
					mapParameters2.put("09_co_id", txtCoID.getValue());
					mapParameters2.put("filter_pmt", cboListParameters.getSelectedIndex());
					SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
					String strFrom = sdf.format(dteFrom.getDate());
					String strTo = sdf.format(dteTo.getDate());
					
					mapParameters2.put("06_IdentifierName", cboDateParameters.getSelectedItem().toString());
					mapParameters2.put("07_dateParam", (strFrom.equals(strTo))?strTo:strFrom+" to "+strTo);
					String SQL = "select row_number() over(order by c_or_date, c_jv_no, c_type, c_or_no, c_name, c_unit, c_check_date)::int as row, *,\n"
							+ "(\n"
							+ "select y.entity_name\n"
							+ "from em_employee x\n"
							+ "inner join rf_entity y on x.entity_id = y.entity_id\n"
							+ "where x.emp_code = '"+UserInfo.EmployeeCode+"'\n"
							+ ") as c_user\n"
							+ "from view_spotcash_orissuance_v2('"+txtCoID.getValue()+"', '', '', '"+dteFrom.getDate()+"', '"+dteTo.getDate()+"', '"+cboListParameters.getSelectedIndex()+"', '"+cboDateParameters.getSelectedIndex()+"'::int, true, '"+txtPeriod.getValue()+"')\n"
							+ "order by c_or_date, c_jv_no, c_type, c_or_no, c_name, c_unit, c_check_date";
					System.out.println("SQL FOR rpt_tobeissuedORList_withOR_v2: "+ SQL);
					FncReport.generateReport("/Reports/rpt_tobeissuedORList_withOR_v2.jasper", "OR List", "", mapParameters2);	
				} else {
					System.out.println("Not yet issued");
					/*
					 * Dito naman ung lalabas ung nasa batch na ndi pa na issue ni cashier*/
					Map<String, Object> mapParameters = new HashMap<String, Object>();
					mapParameters.put("09_co_id", txtCoID.getValue());
					mapParameters.put("01_Company", txtCo.getText());
					mapParameters.put("batch_no", txtPeriod.getValue());
					
					FncReport.generateReport("/Reports/rpt_tobeissuedORList.jasper", "OR List", "", mapParameters);	
				}
			} else if (tabORIssuance.getSelectedIndex()==1) {
				Map<String, Object> mapParameters = new HashMap<String, Object>();
				mapParameters.put("01_Company", txtCo.getText());
				mapParameters.put("02_User", UserInfo.EmployeeCode);
				mapParameters.put("03_dateFrom", dteFrom.getDate());
				mapParameters.put("04_Identifier", cboDateParameters.getSelectedIndex());
				mapParameters.put("05_dateTo", dteTo.getDate());
				mapParameters.put("06_period", txtPeriod.getValue());
				mapParameters.put("09_co_id", txtCoID.getValue());
				mapParameters.put("filter_pmt", cboListParameters.getSelectedIndex());
				SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
				String strFrom = sdf.format(dteFrom.getDate());
				String strTo = sdf.format(dteTo.getDate());
				
				mapParameters.put("06_IdentifierName", cboDateParameters.getSelectedItem().toString());
				mapParameters.put("07_dateParam", (strFrom.equals(strTo))?strTo:strFrom+" to "+strTo);
				String SQL = "select row_number() over(order by c_or_date, c_jv_no, c_type, c_or_no, c_name, c_unit, c_check_date)::int as row, *,\n"
						+ "(\n"
						+ "select y.entity_name\n"
						+ "from em_employee x\n"
						+ "inner join rf_entity y on x.entity_id = y.entity_id\n"
						+ "where x.emp_code = '"+UserInfo.EmployeeCode+"'\n"
						+ ") as c_user\n"
						+ "from view_spotcash_orissuance('"+txtCoID.getValue()+"', '', '', '"+dteFrom.getDate()+"', '"+dteTo.getDate()+"', '"+cboListParameters.getSelectedIndex()+"', '"+cboDateParameters.getSelectedIndex()+"'::int, true)\n"
						+ "order by c_or_date, c_jv_no, c_type, c_or_no, c_name, c_unit, c_check_date";
				System.out.println("SQL FOR rpt_tobeissuedORList_withOR: "+ SQL);
				FncReport.generateReport("/Reports/rpt_tobeissuedORList_withOR.jasper", "OR List", "", mapParameters);	
			}
		}
		
		if(act.getActionCommand().equals("Cancel")) {
			if ((JOptionPane.showConfirmDialog(getContentPane(), "Are you sure you want to cancel this batch number?", "Cancel", 
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE))==JOptionPane.YES_OPTION) {
				cancelBatchforIssuance(txtPeriod.getValue());
				JOptionPane.showMessageDialog(getContentPane(), "Batch Cancelled!");
			}
		}
	}
	
	private boolean checkIfIssued(String batch_no, String co_id) {
		boolean is_issued = false;
		pgSelect db = new pgSelect();
		String SQL = "select * from rf_orlist where batch_no = '"+batch_no+"' and co_id = '"+co_id+"' and or_no is not null";

		db.select(SQL);
		
		if(db.isNotNull()) {
			is_issued = true;
		}
		
		return is_issued;
	}
	
	private void cancelBatchforIssuance(String batch_no) {
		pgUpdate db = new pgUpdate();
		String SQL = "UPDATE rf_orlist set status_id = 'I' WHERE batch_no = '"+batch_no+"'";
		db.executeUpdate(SQL, true);
		db.commit();
	}
	
	private void FillCombo() {
		String strSQL = "select (coalesce((select x.date_approved::date from rf_orlist x where x.date_approved::date < a.date_approved::date order by x.date_approved::date desc limit 1) || ' to ', '') || (date_approved::date - 1)::varchar)::varchar as period, \n" + 
				"(select x.date_approved::date from rf_orlist x where x.date_approved::date < a.date_approved::date order by x.date_approved::date desc limit 1) as period_from \n" + 
				"from (select distinct x.date_approved::date from rf_orlist x) a \n" + 
				"where (select x.date_approved::date from rf_orlist x where x.date_approved::date < a.date_approved::date order by x.date_approved::date desc limit 1) is not null \n" +
				"order by a.date_approved::date desc"; 
		
		pgSelect dbExec = new pgSelect(); 
		dbExec.select(strSQL);
		
		if (dbExec.isNotNull()) {
			for (int x = 0; x < dbExec.getRowCount(); x++) {
				cboListPeriod.addItem(dbExec.getResult()[x][0].toString());
			}
		}
	}
	
	private String getPeriod() {
		return FncGlobal.GetString("select (coalesce((select x.date_approved::date from rf_orlist x where x.date_approved::date < a.date_approved::date order by x.date_approved::date desc limit 1) || ' to ', '') || (date_approved::date - 1)::varchar)::varchar as period \n" + 
				"from (select distinct x.date_approved::date from rf_orlist x) a \n" + 
				"order by a.date_approved::date desc \n" + 
				"limit 1"); 
	}
	
	private void stateButton(boolean blnApprove, boolean blnPreview) {
		btnApprove.setEnabled(blnApprove);
		btnPreview.setEnabled(blnPreview);
		
		intArray[0][0] = (blnApprove)?1:0;
		intArray[0][1] = (blnPreview)?1:0;
	}
	
	@SuppressWarnings("unused")
	private void ViewPeriod() {
		pgSelect dbExec = new pgSelect();
		DefaultListModel listModel = new DefaultListModel();
		
		FncTables.clearTable(modelCashCheck);
		rowCashCheck.setModel(listModel);
		
		String strFrom = FncGlobal.GetString("select (select x.date_approved::date from rf_orlist x where x.date_approved::date < a.date_approved::date order by x.date_approved::date desc limit 1) \n" +
				"from (select distinct x.date_approved::date from rf_orlist x) a \n" +
				"where (coalesce((select x.date_approved::date from rf_orlist x where x.date_approved::date < a.date_approved::date order by x.date_approved::date desc limit 1) || ' to ', '') || (date_approved::date - 1)::varchar)::varchar = '"+txtPeriod.getValue()+"'"); 
		
		String strTo = FncGlobal.GetString("select (date_approved::date - 1)::varchar \n" +
				"from (select distinct x.date_approved::date from rf_orlist x) a \n" +
				"where (coalesce((select x.date_approved::date from rf_orlist x where x.date_approved::date < a.date_approved::date order by x.date_approved::date desc limit 1) || ' to ', '') || (date_approved::date - 1)::varchar)::varchar = '"+txtPeriod.getValue()+"'");
		
		dbExec = new pgSelect(); 
		dbExec.select("select * \n" +
				"from view_spotcash_orissuance('"+txtCoID.getValue().toString()+"', '"+txtProID.getValue().toString()+"', '"+txtPhaseID.getValue().toString()+"', '"+strFrom+"'::date, '"+strTo+"'::date, '"+cboListParameters.getSelectedIndex()+"'::int, '0'::int, true) \n" + 
				"order by c_type, c_or_date, c_or_no, c_name, c_unit");

		System.out.println(""); 
		System.out.println("select * \n" +
				"from view_spotcash_orissuance('"+txtCoID.getValue().toString()+"', '"+txtProID.getValue().toString()+"', '"+txtPhaseID.getValue().toString()+"', '"+strFrom+"'::date, '"+strTo+"'::date, '"+cboListParameters.getSelectedIndex()+"'::int, '0'::int, true) \n" +
				"order by c_type, c_or_date, c_or_no, c_name, c_unit");
		
		if (dbExec.getRowCount()>0) {
			for (int x = 0; x < dbExec.getRowCount(); x++) {
				modelCashCheck.addRow(dbExec.getResult()[x]);
				listModel.addElement(modelCashCheck.getRowCount());
			}
		}
	}
	
	private void refresh() {
		txtPeriod.setValue("");
		
		if (tabORIssuance.getSelectedIndex()==0) {
			RefreshListIndexOne();
			cboListPeriod.setEnabled(false);
			txtPeriod.setText(""); 
			stateButton(true, false);
		} else {
			RefreshListIndexTwo(); 
		}
	}
	
	private String sqlBatchNo() {
		String batch_no = "";
		pgSelect db = new pgSelect();
		String SQL = "(select trim(to_char((coalesce(max(batch_no::int), 0) + 1), '0000000000')) from rf_orlist)";
		db.select(SQL);
		
		if(db.isNotNull()) {
			batch_no = (String) db.getResult()[0][0];
		}
		
		return batch_no;
	}
	
	private String period(String coid) {
		return "select date_approved::date \n" + 
				"from rf_orlist \n" + 
				"where date_approved is not null \n" + 
				"and (co_id = '"+coid+"' or '"+coid+"' = '') \n" +
				"group by date_approved::date \n" + 
				"order by date_approved::date desc"; 
	}
	
	private String batchNo(String co_id) {
		
		String SQL = "SELECT distinct (batch_no) as \"Batch No\", date_approved::DATE as \"Date Approved\", \n"+
					 "date_issued::dATE as \"Date Issued\" \n" + 
					 "from rf_orlist \n" + 
					 "where status_id = 'A'\n" + 
					 "AND batch_no is not null\n" + 
					 "and co_id = '"+co_id+"'\n" + 
					 "order by batch_no DESC;";
		
		return SQL;
	}
	
	private String GetValue(String SQL) {
		String strValue = "";
		pgSelect sqlExec = new pgSelect();
		
		sqlExec.select(SQL);

		if (sqlExec.isNotNull()) {
			strValue = (String) sqlExec.getResult()[0][0];
		} else {
			strValue = "";
		}
		
		return strValue;
	}
}
