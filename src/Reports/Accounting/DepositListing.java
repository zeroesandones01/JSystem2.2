package Reports.Accounting;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import Accounting.Collections.CheckStatusMonitoring;
import Accounting.Disbursements.RequestForPayment;
import DateChooser._JDateChooser;
import Functions.FncAcounting;
import Functions.FncFocusTraversalPolicy;
import Functions.FncGlobal;
import Functions.FncReport;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;

import components._JInternalFrame;

public class DepositListing extends _JInternalFrame implements ActionListener, AncestorListener, _GUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1162406788303915011L;

	static String title = "Deposit Listing";
	static Dimension frameSize = new Dimension(500, 250);
	static Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	static Cursor handCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);

	private JPanel pnlMain;
	private JPanel pnlNorth;
	private JPanel pnlNorthWest;
	private JPanel pnlNorthEast;
	private JPanel pnlCenter2;
	private JPanel pnlCriteria2;
	private JPanel pnlCenter;
	private JPanel pnlCPF;
	private JPanel pnlCenter_b;
	private JPanel pnlSouth;

	private JLabel lblProject;
	private JLabel lblCompany;
	private JLabel lblColl_dateFr;
	private JLabel lblColl_depTo;
	private JLabel lblDep_dateTo;
	private JLabel lblBranch;
	private JLabel lblAccount;
	private JLabel lblDepStatus;
	private JLabel lblDep_dateFr;

	private _JLookup lookupProject;
	private _JLookup lookupCompany;
	private _JLookup lookupBranch;
	private _JLookup lookupAccount;
	private _JLookup lookupStatus;

	private JTextField txtProject;
	private JTextField txtCompany;
	private JTextField txtBranch;
	private JTextField txtAccount;
	private JTextField txtStatus;
	
	private JRadioButton rbtnDeposit;
	private JRadioButton rbtnCollection;	

	private JButton btnPreview;
	private JButton btnCancel;

	private _JDateChooser dteColl_dateFr;
	private _JDateChooser dateColl_dateTo;
	private _JDateChooser dteDep_dateFr;
	private _JDateChooser dateDep_dateTo;
	
	private JCheckBox chkRep1;
	private JCheckBox chkRep2;
	private JCheckBox chkRep3;

	String company;
	String company_logo;	
	String co_id = "";
	String proj_id = "All";	
	String branch_id = "";
	String bank_acct_id = "";
	String status_id = "";

	SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");	

	public DepositListing() {
		super(title, false, true, false, true);
		initGUI();
	}

	public DepositListing(String title) {
		super(title);
		initGUI();
	}

	public DepositListing(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		this.setPreferredSize(new java.awt.Dimension(560, 385));
		this.setLayout(new BorderLayout());
		this.addAncestorListener(this);
		{
			pnlMain = new JPanel(new BorderLayout(5, 5));
			getContentPane().add(pnlMain, BorderLayout.WEST);
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			pnlMain.setPreferredSize(new java.awt.Dimension(557, 246));

			{
				pnlNorth = new JPanel(new BorderLayout(5, 5));
				pnlMain.add(pnlNorth, BorderLayout.NORTH);
				pnlNorth.setPreferredSize(new java.awt.Dimension(547, 184));
				pnlNorth.setBorder(components.JTBorderFactory.createTitleBorder("Comp. / Project Option" ));// XXX

				{
					pnlNorthWest = new JPanel(new GridLayout(5,1, 5, 5));
					pnlNorth.add(pnlNorthWest, BorderLayout.WEST);
					pnlNorthWest.setPreferredSize(new java.awt.Dimension(142, 100));

					{
						lblCompany = new JLabel("Company*", JLabel.TRAILING);
						pnlNorthWest.add(lblCompany);
					}
					{
						lookupCompany = new _JLookup(null, "Company");
						pnlNorthWest.add(lookupCompany);
						lookupCompany.setReturnColumn(0);
						lookupCompany.setLookupSQL(SQL_COMPANY());
						lookupCompany.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){

									co_id = (String) data[0];	
									company		= (String) data[1];	
									company_logo = (String) data[3];

									String name = (String) data[1];						
									txtCompany.setText(name);
									lookupProject.setLookupSQL(CheckStatusMonitoring.sql_project((String) data[0]));
									
									lblColl_dateFr.setEnabled(false);		
									dteColl_dateFr.setEnabled(false);
									lblColl_depTo.setEnabled(false);	
									dateColl_dateTo.setEnabled(false);

									KEYBOARD_MANAGER.focusNextComponent();
									btnCancel.setEnabled(true);
									btnPreview.setEnabled(true);
								}
							}
						});
					}
					{
						lblBranch = new JLabel("Branch", JLabel.TRAILING);
						pnlNorthWest.add(lblBranch);
						lblBranch.setEnabled(true);	
					}
					{
						lookupBranch = new _JLookup(null, "Branch");
						pnlNorthWest.add(lookupBranch);
						lookupBranch.setReturnColumn(0);
						lookupBranch.setEnabled(true);						
						lookupBranch.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){

									branch_id = (String) data[0];		
									String name = (String) data[1];						
									txtBranch.setText(name);

									KEYBOARD_MANAGER.focusNextComponent();
								}
							}
						});
					}
					{
						lblProject = new JLabel("Project", JLabel.TRAILING);
						pnlNorthWest.add(lblProject);
						lblProject.setEnabled(true);	
					}
					{
						lookupProject = new _JLookup(null, "Project");
						pnlNorthWest.add(lookupProject);
						lookupProject.setReturnColumn(0);
						lookupProject.setEnabled(true);
						lookupProject.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){

									proj_id = (String) data[0];		
									String name = (String) data[1];						
									txtProject.setText(name);
									btnPreview.setEnabled(true);

									KEYBOARD_MANAGER.focusNextComponent();
									btnCancel.setEnabled(true);
								}
							}
						});
					}
					{
						lblAccount = new JLabel("Account", JLabel.TRAILING);
						pnlNorthWest.add(lblAccount);
						lblAccount.setEnabled(true);	
					}
					{
						lookupAccount = new _JLookup(null, "Account");
						pnlNorthWest.add(lookupAccount);
						lookupAccount.setReturnColumn(0);
						lookupAccount.setEnabled(true);
						lookupAccount.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){

									bank_acct_id = (String) data[0];		
									String name = (String) data[1];						
									txtAccount.setText(name);

								}
							}
						});
					}
					{
						lblDepStatus = new JLabel("<html>Deposit<br><html>Status", JLabel.TRAILING);
						pnlNorthWest.add(lblDepStatus);
						lblDepStatus.setEnabled(true);
					}
					{
						lookupStatus = new _JLookup(null, "Status");
						pnlNorthWest.add(lookupStatus);
						lookupStatus.setReturnColumn(0);
						lookupStatus.setEnabled(true);
						lookupStatus.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){

									status_id = (String) data[0];		
									String name = (String) data[1];						
									txtStatus.setText(name);

								}
							}
						});
					}
				}

				pnlNorthEast = new JPanel(new GridLayout(5, 1, 5, 5));
				pnlNorth.add(pnlNorthEast, BorderLayout.CENTER);
				pnlNorthEast.setPreferredSize(new java.awt.Dimension(300, 159));
				{
					txtCompany = new JTextField();
					pnlNorthEast.add(txtCompany, BorderLayout.CENTER);
					txtCompany.setEditable(false);
				}
				{
					txtBranch = new JTextField();
					pnlNorthEast.add(txtBranch, BorderLayout.CENTER);
					txtBranch.setEditable(false);
					txtBranch.setEnabled(true);
				}
				{
					txtProject = new JTextField();
					pnlNorthEast.add(txtProject, BorderLayout.CENTER);
					txtProject.setEditable(false);
					txtProject.setEnabled(true);
				}
				{
					txtAccount = new JTextField();
					pnlNorthEast.add(txtAccount, BorderLayout.CENTER);
					txtAccount.setEditable(false);
					txtAccount.setEnabled(true);
				}
				{
					txtStatus = new JTextField();
					pnlNorthEast.add(txtStatus, BorderLayout.CENTER);
					txtStatus.setEditable(false);
					txtStatus.setEnabled(true);
				}
			}

			pnlCenter2 = new JPanel(new BorderLayout(5, 5));
			pnlMain.add(pnlCenter2, BorderLayout.CENTER);
			pnlCenter2.setPreferredSize(new java.awt.Dimension(499, 60));			

			{
				pnlCPF = new JPanel(new BorderLayout(0,0));
				pnlCenter2.add(pnlCPF, BorderLayout.CENTER);
				pnlCPF.setPreferredSize(new java.awt.Dimension(547, 169));				

				{
					pnlCenter = new JPanel(new GridLayout(1,1,0,0));
					pnlCPF.add(pnlCenter, BorderLayout.NORTH);
					pnlCenter.setPreferredSize(new java.awt.Dimension(547, 62));
					pnlCenter.setBorder(components.JTBorderFactory.createTitleBorder(""));					

					
					{
						rbtnDeposit = new JRadioButton();
						pnlCenter.add(rbtnDeposit);
						//rbtnDeposit.setText("<html>Deposit<br><html> Date");
						rbtnDeposit.setText("Deposit Date");
						rbtnDeposit.setBounds(277, 12, 77, 18);
						rbtnDeposit.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
						rbtnDeposit.setSelected(false);
						rbtnDeposit.setEnabled(true);
						rbtnDeposit.setPreferredSize(new java.awt.Dimension(220, 18));
						rbtnDeposit.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent ae){									
								if (rbtnDeposit.isSelected()==true)
								{
									lblDep_dateFr.setEnabled(true);
									dteDep_dateFr.setEnabled(true);
									lblDep_dateTo.setEnabled(true);	
									dateDep_dateTo.setEnabled(true);
									lblColl_dateFr.setEnabled(false);
									dteColl_dateFr.setEnabled(false);
									lblColl_depTo.setEnabled(false);	
									dateColl_dateTo.setEnabled(false);
									rbtnCollection.setSelected(false);
								}
								else 
								{
									lblDep_dateFr.setEnabled(false);
									dteDep_dateFr.setEnabled(false);
									lblDep_dateTo.setEnabled(false);	
									dateDep_dateTo.setEnabled(false);
									lblColl_dateFr.setEnabled(true);
									dteColl_dateFr.setEnabled(true);
									lblColl_depTo.setEnabled(true);	
									dateColl_dateTo.setEnabled(true);
									rbtnCollection.setSelected(true);
								}
							}});					
					}					
					{
						lblDep_dateFr = new JLabel("Date From :  ", JLabel.TRAILING);
						pnlCenter.add(lblDep_dateFr, BorderLayout.CENTER);
						lblDep_dateFr.setEnabled(false);							
					}
					{
						dteDep_dateFr = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
						pnlCenter.add(dteDep_dateFr, BorderLayout.CENTER);						
						dteDep_dateFr.setDate(null);
						dteDep_dateFr.setEnabled(false);
						dteDep_dateFr.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
					}	
					{
						lblDep_dateTo = new JLabel("Date To :  ", JLabel.TRAILING);
						pnlCenter.add(lblDep_dateTo);
						lblDep_dateTo.setEnabled(false);	
					}
					{
						dateDep_dateTo = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
						pnlCenter.add(dateDep_dateTo, BorderLayout.EAST);
						dateDep_dateTo.setBounds(485, 7, 125, 21);
						dateDep_dateTo.setDate(null);
						dateDep_dateTo.setEnabled(false);
						dateDep_dateTo.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
					}	
				}
				{
					pnlCenter_b = new JPanel(new GridLayout(1,1,0,0));
					pnlCPF.add(pnlCenter_b, BorderLayout.CENTER);
					pnlCenter_b.setPreferredSize(new java.awt.Dimension(480, 65));
					pnlCenter_b.setBorder(components.JTBorderFactory.createTitleBorder(""));

					{
						pnlCriteria2 = new JPanel(new GridLayout(1, 2, 3, 3));
						pnlCenter_b.add(pnlCriteria2, BorderLayout.WEST);					
						pnlCriteria2.setPreferredSize(new java.awt.Dimension(480, 63));

						{
							rbtnCollection = new JRadioButton();
							pnlCriteria2.add(rbtnCollection);							
							rbtnCollection.setText("<html>Collection<br><html>Date");
							rbtnCollection.setBounds(277, 12, 77, 18);
							rbtnCollection.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
							rbtnCollection.setSelected(true);
							rbtnCollection.setEnabled(true);
							rbtnCollection.setPreferredSize(new java.awt.Dimension(220, 18));
							rbtnCollection.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent ae){									
									if (rbtnCollection.isSelected()==true)
									{
										lblDep_dateFr.setEnabled(false);
										dteDep_dateFr.setEnabled(false);
										lblDep_dateTo.setEnabled(false);	
										dateDep_dateTo.setEnabled(false);
										lblColl_dateFr.setEnabled(true);
										dteColl_dateFr.setEnabled(true);
										lblColl_depTo.setEnabled(true);	
										dateColl_dateTo.setEnabled(true);
										rbtnDeposit.setSelected(false);
									}
									else 
									{
										lblDep_dateFr.setEnabled(true);
										dteDep_dateFr.setEnabled(true);
										lblDep_dateTo.setEnabled(true);	
										dateDep_dateTo.setEnabled(true);
										lblColl_dateFr.setEnabled(false);
										dteColl_dateFr.setEnabled(false);
										lblColl_depTo.setEnabled(false);	
										dateColl_dateTo.setEnabled(false);
										rbtnDeposit.setSelected(true);
									}
								}});					
						}
						{
							lblColl_dateFr = new JLabel("Date From  :", JLabel.TRAILING);
							pnlCriteria2.add(lblColl_dateFr, BorderLayout.CENTER);
							lblColl_dateFr.setEnabled(true);							
						}
						{
							dteColl_dateFr = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
							pnlCriteria2.add(dteColl_dateFr, BorderLayout.CENTER);						
							dteColl_dateFr.setDate(null);
							dteColl_dateFr.setEnabled(true);
							dteColl_dateFr.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
						}		
						{
							lblColl_depTo = new JLabel("Date To  :", JLabel.TRAILING);
							pnlCriteria2.add(lblColl_depTo);
							lblColl_depTo.setEnabled(true);	
						}
						{
							dateColl_dateTo = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
							pnlCriteria2.add(dateColl_dateTo, BorderLayout.EAST);
							dateColl_dateTo.setBounds(485, 7, 125, 21);
							dateColl_dateTo.setDate(null);
							dateColl_dateTo.setEnabled(true);
							dateColl_dateTo.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
						}
					}
				}			
			}

			{				
				pnlSouth = new JPanel(new GridLayout(1, 2,5, 5));
				pnlMain.add(pnlSouth, BorderLayout.SOUTH);
				pnlSouth.setPreferredSize(new Dimension(300, 30));

				{
					btnPreview = new JButton("Preview");
					pnlSouth.add(btnPreview);
					btnPreview.setAlignmentX(0.5f);
					btnPreview.setAlignmentY(0.5f);
					btnPreview.setEnabled(false);
					btnPreview.setMaximumSize(new Dimension(100, 30));
					btnPreview.setMnemonic(KeyEvent.VK_P);
					btnPreview.addActionListener(this);
				}
				{
					btnCancel = new JButton("Refresh");
					pnlSouth.add(btnCancel);
					btnCancel.setAlignmentX(0.5f);
					btnCancel.setAlignmentY(0.5f);
					btnCancel.setMaximumSize(new Dimension(100, 30));
					btnCancel.setMnemonic(KeyEvent.VK_R);
					btnCancel.setEnabled(false);
					btnCancel.addActionListener(this);
					
				}
			}
		} 
		this.setFocusTraversalPolicy(new FncFocusTraversalPolicy(lookupCompany, lookupBranch, lookupProject, lookupAccount, 
				lookupStatus, dteDep_dateFr, dateDep_dateTo, dteColl_dateFr, dateColl_dateTo, chkRep1, chkRep2, chkRep3, btnPreview));
		this.setBounds(0, 0, 560, 385);  //174

		//added 01/26/2016 - purpose - set CENQHOMES as default company
		initialize_comp();
	}

	
	//action
	@Override
	public void ancestorAdded(AncestorEvent event) {
		//lookupProject.requestFocus();
	}

	@Override
	public void ancestorMoved(AncestorEvent event) { }

	@Override
	public void ancestorRemoved(AncestorEvent event) { }

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getActionCommand().equals("Preview"))
		{
			if(info_complete()==false)
			{JOptionPane.showMessageDialog(getContentPane(), "Enter required information - Company and Branch.", "Incomplete Details", 
					JOptionPane.ERROR_MESSAGE);}
			else 
			{
				if (FncAcounting.EmpCanPreview(UserInfo.EmployeeCode, "11")==true)
				{
					previewReport1();
					previewReport2();
					previewReport3();
				}
				else if(FncAcounting.EmpCanPreview(UserInfo.EmployeeCode, "11")==false) 
				{JOptionPane.showMessageDialog(getContentPane(),"Sorry, you are not authorized to preview Deposit Listing.","Warning",JOptionPane.WARNING_MESSAGE); }
			}
		}
		
		else if (e.getActionCommand().equals("Refresh"))
		{
			initialize_comp();
			refreshFields();
		}
		
	}

	private void initialize_comp(){		

		co_id 		= "02";	
		company		= "CENQHOMES DEVELOPMENT CORPORATION";
		company_logo = RequestForPayment.sql_getCompanyLogo();			
		txtCompany.setText(company);

		lookupProject.setLookupSQL(CheckStatusMonitoring.sql_project(co_id));
		lookupBranch.setLookupSQL(DailyCollectionReport.getBranch());
		lookupAccount.setLookupSQL(getBankAccount());
		lookupStatus.setLookupSQL(getStatus());
		
		btnCancel.setEnabled(true);
		btnPreview.setEnabled(true);

		lookupCompany.setValue(co_id);
		lookupBranch.requestFocus();
	}
	
	private void refreshFields(){
		lookupBranch.setValue("");
		lookupProject.setValue("");
		lookupAccount.setValue("");
		lookupStatus.setValue("");
		txtBranch.setText("");
		txtProject.setText("");		
		txtAccount.setText("");
		txtStatus.setText("");
		
		rbtnDeposit.setSelected(false);
		lblDep_dateFr.setEnabled(false);
		dteDep_dateFr.setEnabled(false);
		lblDep_dateTo.setEnabled(false);	
		dateDep_dateTo.setEnabled(false);
		
		lblColl_dateFr.setEnabled(true);
		dteColl_dateFr.setEnabled(true);
		lblColl_depTo.setEnabled(true);	
		dateColl_dateTo.setEnabled(true);
		rbtnCollection.setSelected(true);
		
		proj_id = "All";	
		branch_id = "";
		bank_acct_id = "";
		status_id = "";
		
	}
	
	
	//preview
	private void previewReport1(){

		String date_used = "";
		Date date_from = null;
		Date date_to = null;
		if (rbtnDeposit.isSelected()==true)
		{
			date_used = "Deposit Date :";
			date_from = dteDep_dateFr.getDate();
			date_to   = dateDep_dateTo.getDate();
		}
		else
		{
			date_used = "Collection Date :";
			date_from = dteColl_dateFr.getDate();
			date_to   = dateColl_dateTo.getDate();
		}
		String proj_name = "All", branch_name = "All", status_name = "All";
		if (lookupProject.getText().toString().trim().equals("")){proj_name = "All";} else {proj_name = txtProject.getText();}
		if (lookupBranch.getText().toString().trim().equals("")){branch_name = "All";} else {branch_name = txtBranch.getText();}
		if (lookupStatus.getText().toString().trim().equals("")){status_name = "All";} else {status_name = txtStatus.getText();}
		
		
		String criteria = "Deposit Summary Report (Report1)";		
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());		

		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("company", company);
		mapParameters.put("co_id", co_id);
		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
		mapParameters.put("date_used", date_used);
		mapParameters.put("date_from", date_from);
		mapParameters.put("date_to", date_to);
		mapParameters.put("proj_name", proj_name);	
		mapParameters.put("proj_id", proj_id);			
		mapParameters.put("branch_id", branch_id);
		mapParameters.put("branch_name", branch_name);	
		mapParameters.put("status_id", status_id);
		mapParameters.put("status_name", status_name);
		mapParameters.put("prepared_name",  UserInfo.FullName);		
		
		FncReport.generateReport("/Reports/rptDepositSummary.jasper", reportTitle, company, mapParameters);	

	}
	
	private void previewReport2(){

		String date_used = "";
		Date date_from = null;
		Date date_to = null;
		if (rbtnDeposit.isSelected()==true)
		{
			date_used = "Deposit Date :";
			date_from = dteDep_dateFr.getDate();
			date_to   = dateDep_dateTo.getDate();
		}
		else
		{
			date_used = "Collection Date :";
			date_from = dteColl_dateFr.getDate();
			date_to   = dateColl_dateTo.getDate();
		}
		String proj_name = "All", branch_name = "All", status_name = "All";
		if (lookupProject.getText().toString().trim().equals("")){proj_name = "All";} else {proj_name = txtProject.getText();}
		if (lookupBranch.getText().toString().trim().equals("")){branch_name = "All";} else {branch_name = txtBranch.getText();}
		if (lookupStatus.getText().toString().trim().equals("")){status_name = "All";} else {status_name = txtStatus.getText();}
		
		
		String criteria = "Collection Summary Report (Report3)";		
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());		

		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("company", company);
		mapParameters.put("co_id", co_id);
		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
		mapParameters.put("date_used", date_used);
		mapParameters.put("date_from", date_from);
		mapParameters.put("date_to", date_to);
		mapParameters.put("proj_name", proj_name);	
		mapParameters.put("proj_id", proj_id);			
		mapParameters.put("branch_id", branch_id);
		mapParameters.put("branch_name", branch_name);	
		mapParameters.put("status_id", status_id);
		mapParameters.put("status_name", status_name);
		mapParameters.put("prepared_by",  UserInfo.FullName);		
		
		FncReport.generateReport("/Reports/rptCollectionSummaryReport.jasper", reportTitle, company, mapParameters);	

	}
	
	private void previewReport3(){

		String date_used = "";
		Date date_from = null;
		Date date_to = null;
		if (rbtnDeposit.isSelected()==true)
		{
			date_used = "Deposit Date :";
			date_from = dteDep_dateFr.getDate();
			date_to   = dateDep_dateTo.getDate();
		}
		else
		{
			date_used = "Collection Date :";
			date_from = dteColl_dateFr.getDate();
			date_to   = dateColl_dateTo.getDate();
		}
		String proj_name = "All", branch_name = "All", status_name = "All";
		if (lookupProject.getText().toString().trim().equals("")){proj_name = "All";} else {proj_name = txtProject.getText();}
		if (lookupBranch.getText().toString().trim().equals("")){branch_name = "All";} else {branch_name = txtBranch.getText();}
		if (lookupStatus.getText().toString().trim().equals("")){status_name = "All";} else {status_name = txtStatus.getText();}
		
		
		String criteria = "Deposit Summary Report (Report2)";			
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());		

		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("company", company);
		mapParameters.put("co_id", co_id);
		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
		mapParameters.put("date_used", date_used);
		mapParameters.put("date_from", date_from);
		mapParameters.put("date_to", date_to);
		mapParameters.put("proj_name", proj_name);	
		mapParameters.put("proj_id", proj_id);			
		mapParameters.put("branch_id", branch_id);
		mapParameters.put("branch_name", branch_name);	
		mapParameters.put("status_id", status_id);
		mapParameters.put("status_name", status_name);
		mapParameters.put("prepared_by",  UserInfo.FullName);		
		
		FncReport.generateReport("/Reports/rptDepositSummaryReport_2.jasper", reportTitle, company, mapParameters);	

	}


	//checking
	public Boolean info_complete(){

		boolean x = false;

		if (lookupCompany.getText().equals("")) { x = false;}
		else { x = true; }		

		return x;		
	}

	
	//SQL
	private String getBankAccount(){

		String sql = 
		"\r\n" + 
		"select \r\n" + 
		"\r\n" + 
		"a.bank_acct_id as \"Bank Acct. ID\" ,\r\n" + 
		"trim(a.bank_acct_no) as \"Bank Acct. No.\" ,\r\n" + 
		"trim(a.acct_desc) as \"Acct. Desc.\" ,\r\n" + 
		"trim(c.bank_alias) as \"Bank\" ,\r\n" + 
		"upper(trim(b.bank_branch_location)) as \"Bank Branch\" ,\r\n" + 
		"a.acct_type as \"Acct. type\"  ,\r\n" + 
		"a.acct_id as \"Acct. ID\" \r\n" + 
		"\r\n" + 
		"from mf_bank_account a\r\n" + 
		"left join mf_bank_branch b on a.bank_branch_id = b.bank_branch_id\r\n" + 
		"left join mf_bank c on b.bank_id = c.bank_id\r\n" + 
		"\r\n" + 
		"order by a.bank_acct_id " ;
		return sql;

	}	
	
	private String getStatus(){

		String sql = 
		"\r\n" + 
		"select 'A' as \"Status ID\", 'ACTIVE' as \"Status Desc\" union all " +
		"select 'P', 'POSTED' " ;
		return sql;

	}	
	
	
	
	
}
