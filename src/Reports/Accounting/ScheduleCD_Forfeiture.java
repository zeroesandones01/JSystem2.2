package Reports.Accounting;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import Accounting.Collections.CheckStatusMonitoring;
import Accounting.Disbursements.RequestForPayment;
import DateChooser._JDateChooser;
import Functions.FncGlobal;
import Functions.FncReport;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;

import components._JInternalFrame;

public class ScheduleCD_Forfeiture extends _JInternalFrame implements ActionListener, AncestorListener, _GUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1162406788303915011L;

	static String title = "Schedule of Customer Deposit Forfeiture";
	static Dimension frameSize = new Dimension(500, 250);
	static Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	static Cursor handCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);

	private JPanel pnlMain;
	private JPanel pnlNorth;
	private JPanel pnlNorthWest;
	private JPanel pnlNorthEast;
	
	private JLabel lblProject;
	private JLabel lblCompany;
	private JLabel lbl_dateTo;
	
	private _JLookup lookupProject;
	private _JLookup lookupCompany;
	
	private JTextField txtProject;
	private JTextField txtCompany;
	
	private JPanel pnlSouth;
	private JButton btnPreview;
	private JButton btnCancel;
	
	String company;
	String company_logo;	

	private JPanel pnlCenter2;
	private JPanel pnlCriteria2;

	private _JDateChooser dateFrom;

	private JPanel pnlCenterNTPType;
	private JPanel pnlCenterNTPCmb;
	private JPanel pnlCriteria2a;
	private JPanel pnlCriteria2b;

	private String co_id = "";
	private String proj_id = "";
	private String proj_name = "";
	private String phase_code = "";
	private String phase_no = "";

	private JLabel lbl_datefrom;
	private _JDateChooser date_to;
	private JLabel lblPhase;
	private _JLookup lookupPhase;
	private JTextField txtPhase;
	
	public ScheduleCD_Forfeiture() {
		super(title, false, true, false, true);
		initGUI();
	}

	public ScheduleCD_Forfeiture(String title) {
		super(title);
		initGUI();
	}

	public ScheduleCD_Forfeiture(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		this.setPreferredSize(new java.awt.Dimension(563, 272));
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
				pnlNorth.setPreferredSize(new java.awt.Dimension(547, 204));
				pnlNorth.setBorder(components.JTBorderFactory.createTitleBorder("Company Details"));// XXX
				{
					pnlNorthWest = new JPanel(new GridLayout(3,1, 5, 5));
					pnlNorth.add(pnlNorthWest, BorderLayout.WEST);
					pnlNorthWest.setPreferredSize(new java.awt.Dimension(142, 100));
					{
						lblCompany = new JLabel("Company", JLabel.TRAILING);
						pnlNorthWest.add(lblCompany);
					}
					{
						lookupCompany = new _JLookup(null, "Company");
						pnlNorthWest.add(lookupCompany);
						lookupCompany.setReturnColumn(0);
						lookupCompany.setLookupSQL(SummaryofDeposits.company());
						lookupCompany.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){
									
									company = (String) data[1];
									company_logo = (String) data[3];
									
									String name = (String) data[1];						
									txtCompany.setText(name);
									lookupProject.setLookupSQL(CheckStatusMonitoring.sql_project((String) data[0]));	
									
									lblProject.setEnabled(true);	
									lookupProject.setText("");
									lookupProject.setEnabled(true);
									txtProject.setEnabled(true);
									txtProject.setText("");
									
									lblPhase.setEnabled(false);	
									lookupPhase.setText("");
									lookupPhase.setEnabled(false);
									txtPhase.setEnabled(false);
									txtPhase.setText("");		
									
									KEYBOARD_MANAGER.focusNextComponent();
									btnCancel.setEnabled(true);
								}
							}
						});
					}
					{
						lblProject = new JLabel("Project", JLabel.TRAILING);
						pnlNorthWest.add(lblProject);
						lblProject.setEnabled(false);	
					}
					{
						lookupProject = new _JLookup(null, "Project");
						pnlNorthWest.add(lookupProject);
						lookupProject.setReturnColumn(0);
						lookupProject.setEnabled(false);
						lookupProject.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){
									proj_id = (String) data[0];
									proj_name = (String) data[1];						
									txtProject.setText(proj_name);												
									btnCancel.setEnabled(true);
									lookupPhase.setLookupSQL(getPhase());
									
									lblPhase.setEnabled(true);	
									lookupPhase.setText("");
									lookupPhase.setEnabled(true);
									txtPhase.setEnabled(true);
									txtPhase.setText("");		
								}
							}
						});
					}
					{
						lblPhase = new JLabel("Phase", JLabel.TRAILING);
						pnlNorthWest.add(lblPhase);
						lblPhase.setEnabled(true);	
					}
					{
						lookupPhase = new _JLookup(null, "Phase");
						pnlNorthWest.add(lookupPhase);
						lookupPhase.setReturnColumn(0);
						lookupPhase.setEnabled(true);
						lookupPhase.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){
									phase_code = (String) data[0];
									phase_no = (String) data[1];						
									txtPhase.setText(phase_no);												
									btnCancel.setEnabled(true);
								}
							}
						});
					}
				}
				pnlNorthEast = new JPanel(new GridLayout(3, 1, 5, 5));
				pnlNorth.add(pnlNorthEast, BorderLayout.CENTER);
				pnlNorthEast.setPreferredSize(new java.awt.Dimension(300, 159));
				{
					txtCompany = new JTextField();
					pnlNorthEast.add(txtCompany, BorderLayout.CENTER);
					txtCompany.setEditable(false);
				}
				{
					txtProject = new JTextField();
					pnlNorthEast.add(txtProject, BorderLayout.CENTER);
					txtProject.setEditable(false);
					txtProject.setEnabled(false);
				}
				{
					txtPhase = new JTextField();
					pnlNorthEast.add(txtPhase, BorderLayout.CENTER);
					txtPhase.setEditable(false);
					txtPhase.setEnabled(false);
				}
				
				
				pnlCenter2 = new JPanel(new BorderLayout(5, 5));
				pnlNorth.add(pnlCenter2, BorderLayout.SOUTH);
				pnlCenter2.setPreferredSize(new java.awt.Dimension(499, 60));
				pnlCenter2.setBorder(components.JTBorderFactory.createTitleBorder("Payment Date"));
				
				pnlCenterNTPType = new JPanel(new GridLayout(1, 1, 5, 5));
				pnlCenter2.add(pnlCenterNTPType, BorderLayout.WEST);	
				pnlCenterNTPType.setPreferredSize(new java.awt.Dimension(69, 60));
				pnlCenterNTPType.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));		
				
				{					
					lbl_datefrom = new JLabel("Date From", JLabel.TRAILING);
					pnlCenterNTPType.add(lbl_datefrom);
					lbl_datefrom.setEnabled(true);	
					lbl_datefrom.setPreferredSize(new java.awt.Dimension(79, 60));
				}
				
				pnlCenterNTPCmb = new JPanel(new GridLayout(1, 1, 5, 5));
				pnlCenter2.add(pnlCenterNTPCmb, BorderLayout.CENTER);	
				pnlCenterNTPCmb.setPreferredSize(new java.awt.Dimension(249, 60));
				pnlCenterNTPCmb.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
				
				{
					dateFrom = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
					pnlCenterNTPCmb.add(dateFrom, BorderLayout.EAST);
					dateFrom.setBounds(485, 7, 125, 21);
					dateFrom.setEnabled(true);
					dateFrom.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
				}		
				{
					pnlCriteria2 = new JPanel(new BorderLayout(5, 5));
					pnlCenter2.add(pnlCriteria2, BorderLayout.EAST);	
					pnlCriteria2.setPreferredSize(new java.awt.Dimension(270, 60));
					
					pnlCriteria2a = new JPanel(new GridLayout(1, 4, 3, 3));
					pnlCriteria2.add(pnlCriteria2a, BorderLayout.WEST);	
					pnlCriteria2a.setPreferredSize(new java.awt.Dimension(78, 60));

					{
								
						lbl_dateTo = new JLabel("Date To   ", JLabel.TRAILING);
						pnlCriteria2a.add(lbl_dateTo);
						lbl_dateTo.setEnabled(true);	
						lbl_dateTo.setPreferredSize(new java.awt.Dimension(79, 60));
					}
					
					pnlCriteria2b = new JPanel(new GridLayout(1, 4, 3, 3));
					pnlCriteria2.add(pnlCriteria2b, BorderLayout.CENTER);	
					pnlCriteria2b.setPreferredSize(new java.awt.Dimension(270, 60));
					
					{
						date_to = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
						pnlCriteria2b.add(date_to, BorderLayout.EAST);
						date_to.setBounds(485, 7, 125, 21);
						date_to.setDate(null);
						date_to.setEnabled(true);
						date_to.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
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
					btnPreview.setMaximumSize(new Dimension(100, 30));
					btnPreview.setMnemonic(KeyEvent.VK_P);
					btnPreview.addActionListener(this);
				}
				{
					btnCancel = new JButton("Cancel");
					pnlSouth.add(btnCancel);
					btnCancel.setAlignmentX(0.5f);
					btnCancel.setAlignmentY(0.5f);
					btnCancel.setMaximumSize(new Dimension(100, 30));
					btnCancel.setMnemonic(KeyEvent.VK_P);
					btnCancel.setEnabled(false);
					btnCancel.addActionListener(this);
				}
			}
		}
		}
		/*this.setFocusTraversalPolicy(new FncFocusTraversalPolicy(lookupCompany, lookupProject,  
				 dateFrom, dateAsOf, btnPreview));*/
		this.setBounds(0, 0, 563, 272);  //174
		
		//added 01/26/2016 - purpose - set CENQHOMES as default company
		initialize_comp();
	}
			
	@Override
	public void ancestorAdded(AncestorEvent event) {
		lookupProject.requestFocus();
	}

	@Override
	public void ancestorMoved(AncestorEvent event) { }

	@Override
	public void ancestorRemoved(AncestorEvent event) { }

	@Override
	public void actionPerformed(ActionEvent e) {
		
		
		if(e.getActionCommand().equals("Preview")){		
			
			String criteria = "Contractors Billing Summary";		
			String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());		
		
			if (phase_code.equals("")){phase_no = "All";}else{}
			if (proj_id.equals("")){proj_name = "All";}else{}
			
			Map<String, Object> mapParameters = new HashMap<String, Object>();
			mapParameters.put("company", company);
			mapParameters.put("proj_id", lookupProject.getValue());
			mapParameters.put("project", proj_name);
			mapParameters.put("phase_code", lookupPhase.getValue());
			mapParameters.put("phase_no", phase_no);
			mapParameters.put("co_id", lookupCompany.getValue());
			mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
			mapParameters.put("prepared_by", UserInfo.Alias);
			mapParameters.put("date_from", dateFrom.getDate());	
			mapParameters.put("date_to", date_to.getDate());	


			FncReport.generateReport("/Reports/rptScheduleofCD_forfeitures.jasper", reportTitle, txtProject.getText(), mapParameters);		
		}
		
		if(e.getActionCommand().equals("Cancel")){				
			
			lblProject.setEnabled(false);	
			
			lblPhase.setEnabled(false);	
			lookupPhase.setText("");
			lookupPhase.setEnabled(false);
			txtPhase.setEnabled(false);
			txtPhase.setText("");
			
			dateFrom.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
			date_to.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
						
			phase_code = "";
			phase_no = "";
			
			co_id 		= "02";	
			company		= "CENQHOMES DEVELOPMENT CORPORATION";			
			company_logo = RequestForPayment.sql_getCompanyLogo();					
			txtCompany.setText(company);
			
		}
		
	}
	
	public void initialize_comp(){		
		
		co_id 		= "02";	
		company		= "CENQHOMES DEVELOPMENT CORPORATION";			
		company_logo = RequestForPayment.sql_getCompanyLogo();					
		txtCompany.setText(company);
		
		lookupProject.setLookupSQL(CheckStatusMonitoring.sql_project(co_id));	
		lookupPhase.setLookupSQL(getPhase());
		
		lblProject.setEnabled(true);	
		lookupProject.setText("");
		lookupProject.setEnabled(true);
		txtProject.setEnabled(true);
		txtProject.setText("");
		
		lblPhase.setEnabled(false);	
		lookupPhase.setText("");
		lookupPhase.setEnabled(false);
		txtPhase.setEnabled(false);
		txtPhase.setText("");		
		
		KEYBOARD_MANAGER.focusNextComponent();
		btnCancel.setEnabled(true);
		
		lookupCompany.setValue(co_id);
}
	
	private String getPhase(){//used
		return 
		"select\r\n" + 
		"\r\n" + 
		"trim(sub_proj_id) as \"Phase ID\",\r\n" + 
		"trim(phase) as \"Phase\"\r\n" + 
		"\r\n" + 
		"from mf_sub_project \r\n" + 
		"where (case when '"+proj_id+"' = '' then true else proj_id = '"+proj_id+"' end) and status_id = 'A' \r\n" + //ADDED STATUS ID BY LESTER DCRF 2718
		"order by sub_proj_id "  ;

	}
}