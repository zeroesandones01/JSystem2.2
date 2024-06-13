package Reports.Accounting;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.jdesktop.swingx.JXPanel;

import Buyers.CreditandCollections._RealTimeDebit;
import Database.pgSelect;
import DateChooser._JDateChooser;
import Functions.FncGlobal;
import Functions.FncLookAndFeel;
import Functions.FncReport;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import Lookup.lookupMethods;
import components.JTBorderFactory;
import components._JInternalFrame;
import interfaces.GUIBuilder;
import interfaces._GUI;

public class otherReports extends _JInternalFrame implements _GUI {

	private static final long serialVersionUID = -2123799804344469671L;
	static String title = "Project Closing Reports";
	Dimension frameSize = new Dimension(500, 305);
	
	private JLabel lblCompany;
	private JLabel lblProject;
	private JLabel lblPhase;
	
	private _JLookup txtCoID;
	private _JLookup txtProID;
	private _JLookup txtPhaseID;
			
	private JTextField txtCompany;
	private JTextField txtProject;
	private JTextField txtPhase;
	
	private _JDateChooser dteDateFrom;
	private _JDateChooser dteDateTo;
	
	private JButton btnPreview;
	private JButton btnExport;
	
	private JComboBox cboRep; 
	
	private String strComLogo; 
	
	DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	
	public otherReports() {
		super(title, false, true, false, false);
		initGUI();
	}

	public otherReports(String title) {
		super(title);
		initGUI();
	}

	public otherReports(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		
		JXPanel panMain = new JXPanel(new BorderLayout(5, 5));
		getContentPane().add(panMain, BorderLayout.CENTER);
		panMain.setPreferredSize(frameSize);
		panMain.setBorder(new EmptyBorder(5, 5, 5, 5));
		{
			JXPanel panNorth = new JXPanel(new BorderLayout(5, 5));
			panMain.add(panNorth, BorderLayout.PAGE_START);
			panNorth.setPreferredSize(new Dimension(0, 230));
			{
				JXPanel panNorth1 = new JXPanel(new GridLayout(4, 1, 5, 5));
				panNorth.add(panNorth1, BorderLayout.PAGE_START);
				panNorth1.setPreferredSize(new Dimension(0, 165));
				panNorth1.setBorder(JTBorderFactory.createTitleBorder("Project and Company", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
				{
					{
						JXPanel panDiv1 = new JXPanel(new BorderLayout(5, 5)); 
						panNorth1.add(panDiv1); 
						{
							{
								JXPanel panLabel = new JXPanel(new GridLayout(1, 2, 5, 5)); 
								panDiv1.add(panLabel, BorderLayout.LINE_START); 
								panLabel.setPreferredSize(new Dimension(150, 0));
								{
									{
										lblCompany = new JLabel("Company");
										panLabel.add(lblCompany);
										lblCompany.setHorizontalAlignment(JTextField.LEFT);
									}
									{
										txtCoID = new _JLookup("");
										panLabel.add(txtCoID);
										txtCoID.setHorizontalAlignment(JTextField.CENTER);
										txtCoID.setLookupSQL(lookupMethods.getCompany());
										txtCoID.setReturnColumn(0);
										txtCoID.addLookupListener(new LookupListener() {
											public void lookupPerformed(LookupEvent e) {
												Object[] data = ((_JLookup) e.getSource()).getDataSet();
												if (data != null) {
													txtCompany.setText(data[1].toString());
													txtProID.setLookupSQL(lookupMethods.getProject(txtCoID.getValue()));
													strComLogo = data[3].toString();
													
													btnPreview.setEnabled(true);
												} else {
													btnPreview.setEnabled(false);									
												}
											}
										});
										txtCoID.addKeyListener(new KeyListener() {
											public void keyTyped(KeyEvent e) {
												if (e.getKeyChar()==KeyEvent.VK_BACK_SPACE) {
													txtCoID.setValue("");
													txtCompany.setText("All Companies");
												}
											}
											
											public void keyReleased(KeyEvent e) {
												
											}
											
											public void keyPressed(KeyEvent e) {

											}
										});
									}
								}
							}
							{
								txtCompany = new JTextField("");
								panDiv1.add(txtCompany);
								txtCompany.setHorizontalAlignment(JTextField.CENTER);
							}
						}
					}
					{
						JXPanel panDiv2 = new JXPanel(new BorderLayout(5, 5)); 
						panNorth1.add(panDiv2); 
						{
							{
								JXPanel panLabel = new JXPanel(new GridLayout(1, 2, 5, 5)); 
								panDiv2.add(panLabel, BorderLayout.LINE_START); 
								panLabel.setPreferredSize(new Dimension(150, 0));
								{
									{
										lblProject = new JLabel("Project");
										panLabel.add(lblProject);
										lblProject.setHorizontalAlignment(JTextField.LEFT);
									}
									{
										txtProID = new _JLookup("");
										panLabel.add(txtProID);
										txtProID.setHorizontalAlignment(JTextField.CENTER);
										txtProID.setLookupSQL(lookupMethods.getProject(""));
										txtProID.setReturnColumn(0);
										txtProID.addLookupListener(new LookupListener() {
											public void lookupPerformed(LookupEvent e) {
												Object[] data = ((_JLookup) e.getSource()).getDataSet();
												if (data != null) {
													txtProject.setText(data[1].toString());
													txtPhaseID.setLookupSQL(lookupMethods.getPhase(txtProID.getValue()));
												}
											}
										});
										txtProID.addKeyListener(new KeyListener() {
											public void keyTyped(KeyEvent e) {
												if (e.getKeyChar()==KeyEvent.VK_BACK_SPACE) {
													txtProID.setValue("");
													txtProject.setText("All Projects");
												}
											}

											public void keyReleased(KeyEvent e) {
																					
											}
											
											public void keyPressed(KeyEvent e) {
																					
											}
										});
										txtProID.addFocusListener(new FocusListener() {
											
											@Override
											public void focusLost(FocusEvent e) {
												// TODO Auto-generated method stub
												
											}
											
											@Override
											public void focusGained(FocusEvent e) {
												txtProID.setLookupSQL(lookupMethods.getProject(txtCoID.getValue()));
											}
										});
									}
								}
							}
							{
								txtProject = new JTextField("");
								panDiv2.add(txtProject);
								txtProject.setHorizontalAlignment(JTextField.CENTER);
							}
						}
					}
					{
						JXPanel panDiv3 = new JXPanel(new BorderLayout(5, 5)); 
						panNorth1.add(panDiv3); 
						{
							{
								JXPanel panLabel = new JXPanel(new GridLayout(1, 2, 5, 5)); 
								panDiv3.add(panLabel, BorderLayout.LINE_START); 
								panLabel.setPreferredSize(new Dimension(150, 0));
								{
									{
										lblPhase = new JLabel("Phase");
										panLabel.add(lblPhase);
										lblPhase.setHorizontalAlignment(JTextField.LEFT);
									}
									{
										txtPhaseID = new _JLookup("");
										panLabel.add(txtPhaseID);
										txtPhaseID.setHorizontalAlignment(JTextField.CENTER);
										txtPhaseID.setLookupSQL(lookupMethods.getPhase(txtProID.getValue()));
										txtPhaseID.setReturnColumn(0);
										txtPhaseID.addLookupListener(new LookupListener() {
											public void lookupPerformed(LookupEvent e) {
												Object[] data = ((_JLookup) e.getSource()).getDataSet();
												if (data != null) {
													txtPhase.setText(data[1].toString());
												}
											}
										});
										txtPhaseID.addKeyListener(new KeyListener() {
											public void keyTyped(KeyEvent e) {
												if (e.getKeyChar()==KeyEvent.VK_BACK_SPACE) {
													txtPhaseID.setValue("");
													txtPhase.setText("All Phase");
												}
											}
											
											public void keyReleased(KeyEvent e) {
												
											}
											
											public void keyPressed(KeyEvent e) {
												
											}
										});
										txtPhaseID.addFocusListener(new FocusListener() {
											
											@Override
											public void focusLost(FocusEvent e) {
												// TODO Auto-generated method stub
												
											}
											
											@Override
											public void focusGained(FocusEvent e) {
												txtPhaseID.setLookupSQL(lookupMethods.getPhase(txtProID.getValue()));
											}
										});
									}
								}
							}
							{
								txtPhase = new JTextField("");
								panDiv3.add(txtPhase);
								txtPhase.setHorizontalAlignment(JTextField.CENTER);
							}
						}
					}
					{
						JXPanel panDiv4 = new JXPanel(new BorderLayout(5, 5)); 
						panNorth1.add(panDiv4); 
						{
							{
								JLabel lblReportType = new JLabel("Report");
								panDiv4.add(lblReportType, BorderLayout.LINE_START);
								lblReportType.setHorizontalAlignment(JTextField.LEFT);
								lblReportType.setPreferredSize(new Dimension(72, 0));
							}
							{
								String strCombo[] =	{
										"Project Closing Miscellaneous - Other Income",
										"Commission Processed Report - Active Accounts",
										"Commission Released Summary for Cancelled Accounts",
										"---", 
										"Gross Forfeiture (Holding Fee)",
										"---", 
										"Commission BOI Schedule",
										"Commission Schedule (FAD Format)",
										"Commission of Active Accounts",
										"Commission of Canceled Accounts",
										"Cash Advances (Agent/Broker) - For Liquidation",
										"Cash Advances (Agent/Broker) - For Write-Off",
										"Commission Released - Active Accounts",
										"Commission Released - Canceled Accounts", 
										"---",
										"House Construction Status - Detailed", 
										"---", 
										"Schedule of Customer Deposit Forfeiture", 
										"---", 
										"List of Contracts"
										
								}; 
								
								cboRep = new JComboBox(strCombo);
								panDiv4.add(cboRep, BorderLayout.CENTER); 
								cboRep.addItemListener(new ItemListener() {
									public void itemStateChanged(ItemEvent e) {
										dteDateTo.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
										
										if (cboRep.getSelectedIndex()==0 || cboRep.getSelectedIndex()==1) {
											dteDateFrom.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
										} else {
											dteDateFrom.setDate(FncGlobal.dateFormat(new String("2017-08-01")));
										} 
									}
								});
								
								
							}
						}
					}
				}
				{
					JXPanel panNorth2 = new JXPanel(new GridLayout(1, 2, 5, 5));
					panNorth.add(panNorth2, BorderLayout.CENTER);
					panNorth2.setBorder(JTBorderFactory.createTitleBorder("Cut-Off Date", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
					{
						{
							JXPanel panDateFrom = new JXPanel(new BorderLayout(5, 5));
							panNorth2.add(panDateFrom, BorderLayout.LINE_START);
							panDateFrom.setPreferredSize(new Dimension(200, 0));
							{
								{
									JLabel lblFrom = new JLabel("From:"); 
									panDateFrom.add(lblFrom, BorderLayout.LINE_START);
									lblFrom.setHorizontalAlignment(JTextField.LEADING);
									lblFrom.setPreferredSize(new Dimension(50, 0));
								}
								{
									dteDateFrom = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
									panDateFrom.add(dteDateFrom, BorderLayout.CENTER);
									dteDateFrom.setEnabled(true);
									dteDateFrom.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
								}	
							}
						}
						{
							JXPanel panDateTo = new JXPanel(new BorderLayout(5, 5));
							panNorth2.add(panDateTo, BorderLayout.LINE_START);
							panDateTo.setPreferredSize(new Dimension(200, 0));
							{
								{
									JLabel lblTo = new JLabel("To:"); 
									panDateTo.add(lblTo, BorderLayout.LINE_START); 
									lblTo.setHorizontalAlignment(JTextField.CENTER);
									lblTo.setPreferredSize(new Dimension(50, 0));
								}
								{
									dteDateTo = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
									panDateTo.add(dteDateTo, BorderLayout.CENTER);
									dteDateTo.setEnabled(true);
									dteDateTo.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
								}	
							}
						}
					}
				}
			}
			{
				btnPreview = new JButton("Preview");
				panMain.add(btnPreview, BorderLayout.CENTER);
				btnPreview.setEnabled(true);
				btnPreview.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						GenerateReport(); 
					}
				});
			}
		}
		
		Setdefaults();
	}
    
	private void Setdefaults() {
		txtCoID.setValue("02");
		txtCompany.setText("CENQHOMES DEVELOPMENT CORPORATION");
		
		txtProID.setValue("");
		txtProject.setText("All Projects");
		
		txtPhaseID.setValue("");
		txtPhase.setText("All Phase");
		
		strComLogo = "cenqlogo.png";
	}

	private void previewCommBOISchedule() {
		String criteria = "Commission BOI Schedule";		
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());		

		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("company", txtCompany.getText());	
		mapParameters.put("project", txtProject.getText());	
		mapParameters.put("prepared_by", UserInfo.Alias);
		mapParameters.put("prepared_by_name", UserInfo.FullName);
		mapParameters.put("report_date", dateFormat.format(FncGlobal.dateFormat(FncGlobal.getDateSQL())));
		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ strComLogo));
		mapParameters.put("date_from",dteDateFrom.getDate());
		mapParameters.put("date_to", dteDateTo.getDate());
		mapParameters.put("proj_id", txtProID.getValue());
		mapParameters.put("co_id", txtCoID.getValue());
		FncReport.generateReport("/Reports/rptCommissionSchedule.jasper", reportTitle, txtCompany.getText(), mapParameters);		
	}
	
	private void previewCommSchedule_FAD_format() {
		String criteria = "Commission Schedule (FAD Format)";		
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());		

		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("company", txtCompany.getText());	
		mapParameters.put("project", txtProject.getText());	
		mapParameters.put("prepared_by", UserInfo.Alias);
		mapParameters.put("prepared_by_name", UserInfo.FullName);
		mapParameters.put("report_date", dateFormat.format(FncGlobal.dateFormat(FncGlobal.getDateSQL())));
		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ strComLogo));
		mapParameters.put("date_from",dteDateFrom.getDate());
		mapParameters.put("date_to", dteDateTo.getDate());
		mapParameters.put("proj_id", txtProID.getValue());
		mapParameters.put("co_id", txtCoID.getValue());
		mapParameters.put("phase", txtPhaseID.getValue());
		FncReport.generateReport("/Reports/rptCommissionSchedule_FAD_Format.jasper", reportTitle, txtCompany.getText(), mapParameters);		
	}

	private void previewCommissionActiveAccounts() {
		String criteria = "Comm. Processed Not Yet Released";		
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());		

		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("company", txtCompany.getText());	
		mapParameters.put("project", txtProject.getText());	
		mapParameters.put("prepared_by", UserInfo.Alias);
		mapParameters.put("prepared_by_name", UserInfo.FullName);
		mapParameters.put("report_date", dateFormat.format(FncGlobal.dateFormat(FncGlobal.getDateSQL())));
		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ strComLogo));
		mapParameters.put("date_from",dteDateFrom.getDate());
		mapParameters.put("date_to", dteDateTo.getDate());
		mapParameters.put("proj_id", txtProID.getValue());
		mapParameters.put("co_id", txtCoID.getValue());
		FncReport.generateReport("/Reports/rptCommissionActiveAccts.jasper", reportTitle, txtCompany.getText(), mapParameters);		
	}

	private void previewCommissionCanceledAccounts() {
		String criteria = "Commission of Canceled Accounts";		
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());		

		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("company", txtCompany.getText());	
		mapParameters.put("project", txtProject.getText());	
		mapParameters.put("prepared_by", UserInfo.Alias);
		mapParameters.put("prepared_by_name", UserInfo.FullName);
		mapParameters.put("report_date", dateFormat.format(FncGlobal.dateFormat(FncGlobal.getDateSQL())));
		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ strComLogo));
		mapParameters.put("date_from",dteDateFrom.getDate());
		mapParameters.put("date_to", dteDateTo.getDate());
		mapParameters.put("proj_id", txtProID.getValue());
		mapParameters.put("co_id", txtCoID.getValue());
		FncReport.generateReport("/Reports/rptCommissionCancldAccts.jasper", reportTitle, txtCompany.getText(), mapParameters);		
	}
	
	private void previewCA_AgentBrker_forLiqui() {
		String criteria = "Cash Advances (Agent/Broker) - For Liquidation";		
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());		

		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("company", txtCompany.getText());	
		mapParameters.put("project", txtProject.getText());	
		mapParameters.put("prepared_by", UserInfo.Alias);
		mapParameters.put("prepared_by_name", UserInfo.FullName);
		mapParameters.put("report_date", dateFormat.format(FncGlobal.dateFormat(FncGlobal.getDateSQL())));
		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ strComLogo));
		mapParameters.put("date_from",dteDateFrom.getDate());
		mapParameters.put("date_to", dteDateTo.getDate());
		mapParameters.put("proj_id", txtProID.getValue());
		mapParameters.put("co_id", txtCoID.getValue());
		FncReport.generateReport("/Reports/rptCA_agentBrkr_active.jasper", reportTitle, txtCompany.getText(), mapParameters);		
	}
	
	private void previewCA_AgentBrker_forWriteoff() {
		String criteria = "Cash Advances (Agent/Broker) - For Write-Off";		
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());		

		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("company", txtCompany.getText());	
		mapParameters.put("project", txtProject.getText());	
		mapParameters.put("prepared_by", UserInfo.Alias);
		mapParameters.put("prepared_by_name", UserInfo.FullName);
		mapParameters.put("report_date", dateFormat.format(FncGlobal.dateFormat(FncGlobal.getDateSQL())));
		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ strComLogo));
		mapParameters.put("date_from",dteDateFrom.getDate());
		mapParameters.put("date_to", dteDateTo.getDate());
		mapParameters.put("proj_id", txtProID.getValue());
		mapParameters.put("co_id", txtCoID.getValue());
		FncReport.generateReport("/Reports/rptCA_agentBrkr_inactive.jasper", reportTitle, txtCompany.getText(), mapParameters);		
	}
	
	private void previewCommissionReleasedActive() {
		String criteria = "Commission Released (Active Accounts)";		
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());		

		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("company", txtCompany.getText()); 
		mapParameters.put("project", txtProject.getText()); 
		mapParameters.put("prepared_by", UserInfo.Alias);
		mapParameters.put("prepared_by_name", UserInfo.FullName);
		mapParameters.put("report_date", dateFormat.format(FncGlobal.dateFormat(FncGlobal.getDateSQL())));
		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ strComLogo));
		mapParameters.put("date_from",dteDateFrom.getDate());
		mapParameters.put("date_to", dteDateTo.getDate());
		mapParameters.put("proj_id", txtProID.getValue());
		mapParameters.put("co_id", txtCoID.getValue());
		mapParameters.put("phase", txtPhaseID.getValue());
		mapParameters.put("phase_name", "All");
		FncReport.generateReport("/Reports/rptCommReleases.jasper", reportTitle, txtCompany.getText(), mapParameters);		
	}
	
	private void previewCommissionReleasedCanceled() {
		String criteria = "Commission Released (Canceled Accounts)";		
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());		

		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("company", txtCompany.getText());	
		mapParameters.put("project", txtProject.getText());	
		mapParameters.put("prepared_by", UserInfo.Alias);
		mapParameters.put("prepared_by_name", UserInfo.FullName);
		mapParameters.put("report_date", dateFormat.format(FncGlobal.dateFormat(FncGlobal.getDateSQL())));
		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ strComLogo));
		mapParameters.put("date_from",dteDateFrom.getDate());
		mapParameters.put("date_to", dteDateTo.getDate());
		mapParameters.put("proj_id", txtProID.getValue());
		mapParameters.put("co_id", txtCoID.getValue());
		mapParameters.put("phase", txtPhaseID.getValue());
		mapParameters.put("phase_name", txtPhase.getText());
		FncReport.generateReport("/Reports/rptCommReleases_Canceled.jasper", reportTitle, txtCompany.getText(), mapParameters);		
	}
	
	private void previewHouseConstStatus_Detailed1() {
		String reportTitle = "House Construction Status (Detailed - Page 1) Report";	

		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("company", txtCompany.getText());	
		mapParameters.put("project", txtProject.getText());	
		mapParameters.put("prepared_by", UserInfo.Alias);
		mapParameters.put("prepared_by_name", UserInfo.FullName);
		mapParameters.put("report_date", dateFormat.format(FncGlobal.dateFormat(FncGlobal.getDateSQL())));
		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ strComLogo));
		mapParameters.put("date", dteDateTo.getDate());
		mapParameters.put("proj_id", txtProID.getValue());
		mapParameters.put("phase_no", txtPhase.getText());
		mapParameters.put("phase", txtPhaseID.getValue());
		mapParameters.put("co_id", txtCoID.getValue());
		FncReport.generateReport("/Reports/rptConstructionStatusHousing_Dtld1.jasper", reportTitle, txtCompany.getText(), mapParameters);	
	}

	private void previewHouseConstStatus_Detailed2() {
		String reportTitle = "House Construction Status (Detailed - Page 2) Report";	
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("company", txtCompany.getText());	
		mapParameters.put("project", txtProject.getText());	
		mapParameters.put("prepared_by", UserInfo.Alias);
		mapParameters.put("prepared_by_name", UserInfo.FullName);
		mapParameters.put("report_date", dateFormat.format(FncGlobal.dateFormat(FncGlobal.getDateSQL())));
		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ strComLogo));
		mapParameters.put("date", dteDateTo.getDate());
		mapParameters.put("proj_id", txtProID.getValue());
		mapParameters.put("phase_no", txtPhase.getText());
		mapParameters.put("phase", txtPhaseID.getValue());
		mapParameters.put("co_id", txtCoID.getValue());
		FncReport.generateReport("/Reports/rptConstructionStatusHousing_Dtld2.jasper", reportTitle, txtCompany.getText(), mapParameters);	
	}
	
	private void previewScheduleOfCustomerDepositForfeiture() {
		String criteria = "Contractors Billing Summary";		
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());		

		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("company", txtCompany.getText());
		mapParameters.put("proj_id", txtProID.getValue());
		mapParameters.put("project", txtProject.getText());
		mapParameters.put("phase_code", txtPhase.getText()); 
		mapParameters.put("phase_no", txtPhaseID.getValue());
		mapParameters.put("co_id", txtCoID.getValue());
		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ strComLogo));
		mapParameters.put("prepared_by", UserInfo.Alias);
		mapParameters.put("date_from", dteDateFrom.getDate());	
		mapParameters.put("date_to", dteDateTo.getDate());	
		FncReport.generateReport("/Reports/rptScheduleofCD_forfeitures.jasper", reportTitle, txtProject.getText(), mapParameters);		
	}
	
	private void previewListOfContracts() {

		JComboBox<String> cboNTP = new JComboBox<String>(); 
		
		pgSelect dbExec = new pgSelect(); 
		dbExec.select(lookupMethods.getNTPType());
		
		if (dbExec!=null) {
			cboNTP.addItem("");
			for (int x=0; x<dbExec.getRowCount(); x++) {
				cboNTP.addItem(dbExec.getResult()[x][0].toString().concat(" - ").concat(dbExec.getResult()[x][1].toString()));				
			}
		}
		
		
		JPanel panNTP = new JPanel(new BorderLayout(5, 5)); 
		panNTP.setSize(new Dimension(300, 20));
		{
			panNTP.add(GUIBuilder.createLabelCombo(75, "NTP Type", JLabel.LEADING, cboNTP)); 
		}
		
		int scanOption = JOptionPane.showOptionDialog(getContentPane(), panNTP, "Transaction Date", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, new Object[] {"OK", "CANCEL"}, null);
		
		String asof = ((dteDateTo.getDate().equals(dteDateFrom.getDate()))?dateFormat.format(dteDateTo.getDate()).toString():dateFormat.format(dteDateFrom.getDate()).toString().concat(" to ").concat(dateFormat.format(dteDateTo.getDate()).toString()));
			
		
		Map<String, Object> mapParameters = new HashMap<String, Object>();		
		mapParameters.put("01_Company", txtCompany.getText());
		mapParameters.put("02_CoID", txtCoID.getValue());
		mapParameters.put("03_ProID", txtProID.getValue());
		mapParameters.put("04_Project", txtProject.getText());
		mapParameters.put("05_phase", txtPhaseID.getValue());
		mapParameters.put("06_dateFrom", dteDateFrom.getDate());
		mapParameters.put("07_dateTo", dteDateTo.getDate());
		mapParameters.put("08_ntp_type_id", 
				
				(cboNTP.getSelectedItem().toString().length()>1?cboNTP.getSelectedItem().toString().substring(0, 2):"")
				
				); 
		mapParameters.put("09_ntp_type", FncGlobal.GetString("select type_desc from mf_ntp_type where type_id = '"+(cboNTP.getSelectedItem().toString().length()>1?cboNTP.getSelectedItem().toString().substring(0, 2):"")+"'; "));
		mapParameters.put("10_asof", asof);
		FncReport.generateReport("/Reports/rpt_ntp_list_of_contracts.jasper", "List of Contracts", txtProject.getText(), mapParameters);		
	}
	
    private void GenerateReport() {
		new Thread(new Runnable() {
			public void run() {
				String strStatus = "";
				String strProject = "";
				String strPhase = "";
				String strDate = "";
				
				if (strStatus.equals("Al")) {
					strStatus = "";
				}
				
				if (txtProject.getText() == "All Projects") {
					strProject = "";
				} else {
					strProject = txtProID.getValue();
				}
				
				if (strProject == null) {
					strProject = "";
				}
				
				if (txtPhase.getText() == "All Phase") {
					strPhase = "";
				} else {
					strPhase = txtPhaseID.getValue();
				}
				
				if (dteDateFrom.getDate().equals(dteDateTo.getDate())) {
					strDate = df.format(dteDateFrom.getDate());
				} else {
					strDate = df.format(dteDateFrom.getDate()).toString() + " to " + df.format(dteDateTo.getDate()).toString();  
				}

				Map<String, Object> mapParameters = new HashMap<String, Object>();
				mapParameters.put("01_Company", txtCompany.getText());
				mapParameters.put("02_CoID", txtCoID.getText());
				mapParameters.put("03_ProID", strProject);
				mapParameters.put("04_Project", txtProject.getText());
				mapParameters.put("05_User", UserInfo.FullName);
				mapParameters.put("07_phase", strPhase);
				mapParameters.put("08_dateFrom", dteDateFrom.getDate());
				mapParameters.put("09_dateTo", dteDateTo.getDate());
				mapParameters.put("10_AsOfDate", strDate); 

				if (cboRep.getSelectedIndex()==0) {
					FncReport.generateReport("/Reports/rpt_project_closing_misc_oth_income.jasper", "Loan Released Report Full", "", mapParameters);
				} else if (cboRep.getSelectedIndex()==1) {
					FncReport.generateReport("/Reports/rpt_commission_processed_active.jasper", "Loan Released Report Part", "", mapParameters);
				} else if (cboRep.getSelectedIndex()==2) {
					FncReport.generateReport("/Reports/rpt_commission_released_summary_cancelled_accounts.jasper", "Loan Released Report Part", "", mapParameters);
				} else if (cboRep.getSelectedIndex()==4) {
					
					System.out.printf("Display co_id: %s%n", txtCoID.getValue());
					System.out.printf("Display proj_id: %s%n", txtProID.getValue());
					System.out.printf("Display value of phase: %s%n", txtPhaseID.getValue());
					System.out.printf("Display value of date from : %s%n", dteDateFrom.getDate());
					System.out.printf("Display value of date to: %s%n", dteDateTo.getDate());
					
					mapParameters.put("company_name", txtCompany.getText());
					mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ "cenq_logo.jpg"));
					mapParameters.put("co_id", txtCoID.getValue());
					mapParameters.put("proj_id", txtProID.getValue());
					mapParameters.put("phase", txtPhaseID.getValue());
					mapParameters.put("date_from", dteDateFrom.getDate());
					mapParameters.put("date_to", dteDateTo.getDate());
					
					FncReport.generateReport("/Reports/rptGrossForfeiture_HoldingFee.jasper", "Gross Forfeiture (Holding Fee)", mapParameters);
				
				/********************************************/
				/*			Commission Schedules			*/
				/********************************************/
				} else if (cboRep.getSelectedIndex()==6) {
					previewCommBOISchedule(); 
				} else if (cboRep.getSelectedIndex()==7) {
					previewCommSchedule_FAD_format(); 
				} else if (cboRep.getSelectedIndex()==8) {
					previewCommissionActiveAccounts(); 
				} else if (cboRep.getSelectedIndex()==9) {
					previewCommissionCanceledAccounts(); 
				} else if (cboRep.getSelectedIndex()==10) {
					previewCA_AgentBrker_forLiqui(); 
				} else if (cboRep.getSelectedIndex()==11) {
					previewCA_AgentBrker_forWriteoff(); 
				} else if (cboRep.getSelectedIndex()==12) {
					previewCommissionReleasedActive(); 
				} else if (cboRep.getSelectedIndex()==13) {
					previewCommissionReleasedCanceled(); 
				} else if (cboRep.getSelectedIndex()==14) {
				
				/********************************************/
				/*			Commission Schedules			*/
				/********************************************/
					
				} else if (cboRep.getSelectedIndex()==15) {
					previewHouseConstStatus_Detailed1();
					previewHouseConstStatus_Detailed2(); 
					
				/********************************************/
				/*		Schedule of Customer's Deposit 		*/
				/********************************************/
					
				} else if (cboRep.getSelectedIndex()==17) {
					previewScheduleOfCustomerDepositForfeiture(); 
				
				
				} else if (cboRep.getSelectedIndex()==19) {
					previewListOfContracts(); 
				}
				
			}
		}).start();
	}
	
}
