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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.border.Border;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import org.jdesktop.swingx.JXPanel;

import Accounting.Collections.CheckStatusMonitoring;
import Accounting.Disbursements.RequestForPayment;
import Database.pgUpdate;
import DateChooser._JDateChooser;
import Functions.FncFocusTraversalPolicy;
import Functions.FncGlobal;
import Functions.FncReport;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;

import components._JInternalFrame;

public class Commission_Reports_FR extends _JInternalFrame implements ActionListener, AncestorListener, _GUI {

	private static final long serialVersionUID = 1162406788303915011L;

	static String title = "Commission Reports (FR)";
	static Dimension frameSize = new Dimension(500, 250);
	static Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	static Cursor handCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);

	private JPanel pnlMain;
	private JPanel pnlNorth;
	private JPanel pnlNorthWest;
	private JPanel pnlNorthEast;
	private JPanel pnlCenter2;
	private JPanel pnlSouth;
	private JPanel pnlCPFreport_type;
	
	private JLabel lblProject;
	private JLabel lblCompany;
	private JLabel lblDateFrom;
	private JLabel lblDateTo;
	private JLabel lblReport;
	
	private _JLookup lookupProject;
	private _JLookup lookupCompany;

	private JTextField txtProject;
	private JTextField txtCompany;

	private static JButton btnPreview;

	private static _JDateChooser dteDateFrom;
	private static _JDateChooser dateDateTo;

	private JComboBox cmbReportName;

	String company;
	String company_logo;	
	String co_id = "";
	String proj_id = "";	
	String project = "All";
	String phase_no 	= "All";
	String phase_code	= "";

	SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

	private JLabel lblPhase;
	private _JLookup lookupPhase;
	private JTextField txtPhase;
	
	private static int interval;
	private static Timer timer;

	private static String ip_address; 
	
	public Commission_Reports_FR() {
		super(title, false, true, false, true);
		initGUI();
	}

	public Commission_Reports_FR(String title) {
		super(title);
		initGUI();
	}

	public Commission_Reports_FR(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		this.setPreferredSize(new java.awt.Dimension(513, 307));
		this.setLayout(new BorderLayout());
		this.addAncestorListener(this);
		{
			pnlMain = new JPanel(new BorderLayout(5, 5));
			getContentPane().add(pnlMain, BorderLayout.WEST);
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			pnlMain.setPreferredSize(new java.awt.Dimension(509, 250));
			{
				pnlNorth = new JPanel(new BorderLayout(5, 5));
				pnlMain.add(pnlNorth, BorderLayout.NORTH);
				pnlNorth.setPreferredSize(new java.awt.Dimension(499, 128));
				pnlNorth.setBorder(components.JTBorderFactory.createTitleBorder("Comp. / Project Option" )); 
				{
					pnlNorthWest = new JPanel(new GridLayout(3,1, 5, 5));
					pnlNorth.add(pnlNorthWest, BorderLayout.WEST);
					pnlNorthWest.setPreferredSize(new java.awt.Dimension(142, 100));
					{
						{
							lblCompany = new JLabel("Company", JLabel.TRAILING);
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

										refreshFields();
										
										String name = (String) data[1];						
										txtCompany.setText(name);
										lookupProject.setLookupSQL(CheckStatusMonitoring.sql_project((String) data[0]));
										lblProject.setEnabled(true);	
										lookupProject.setText("");
										lookupProject.setEnabled(true);
										txtProject.setEnabled(true);
										txtProject.setText("");

										lblDateFrom.setEnabled(true);		
										dteDateFrom.setEnabled(true);
										lblDateTo.setEnabled(true);	
										dateDateTo.setEnabled(true);

										lblReport.setEnabled(true);		
										cmbReportName.setEnabled(true);
										
										btnPreview.setEnabled(true);

										KEYBOARD_MANAGER.focusNextComponent();
										btnPreview.setEnabled(true);
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
										String name = (String) data[1];	
										project = (String) data[1];	
										txtProject.setText(name);
										btnPreview.setEnabled(true);

										KEYBOARD_MANAGER.focusNextComponent();
										
										lblPhase.setEnabled(true);	
										lookupPhase.setEnabled(true);
										
										lookupPhase.setValue("");
										txtPhase.setText("");
									}
								}
							});
						}
						{
							lblPhase = new JLabel("Phase", JLabel.TRAILING);
							pnlNorthWest.add(lblPhase);
							lblPhase.setEnabled(false);	
						}
						{
							lookupPhase = new _JLookup(null, "Phase");
							pnlNorthWest.add(lookupPhase);
							lookupPhase.setReturnColumn(0);
							lookupPhase.setEnabled(false);
							lookupPhase.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent event) {
									Object[] data = ((_JLookup)event.getSource()).getDataSet();
									if(data != null){

										phase_no = (String) data[1];
										phase_code = (String) data[0];
										txtPhase.setText((String) data[1]);									
										KEYBOARD_MANAGER.focusNextComponent();
									}
								}
							});
						}	
					}
				}
				{
					pnlNorthEast = new JPanel(new GridLayout(3, 1, 5, 5));
					pnlNorth.add(pnlNorthEast, BorderLayout.CENTER);
					pnlNorthEast.setPreferredSize(new java.awt.Dimension(300, 159));
					{
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
							txtPhase.setEnabled(true);
						}	
					}	
				}
			}

			pnlCenter2 = new JPanel(new GridLayout(2, 1, 5, 5));
			pnlMain.add(pnlCenter2, BorderLayout.CENTER);
			pnlCenter2.setPreferredSize(new java.awt.Dimension(499, 60));
			pnlCenter2.setBorder(components.JTBorderFactory.createTitleBorder("Date Coverage"));
			{
				{
					JXPanel panDate = new JXPanel(new GridLayout(1, 2, 5, 5)); 
					pnlCenter2.add(panDate); 
					{
						{
							JXPanel panDateFrom = new JXPanel(new BorderLayout(5, 5)); 
							panDate.add(panDateFrom); 
							{
								{
									JLabel lblFrom = new JLabel("From: "); 
									panDateFrom.add(lblFrom, BorderLayout.LINE_START); 
									lblFrom.setHorizontalAlignment(JLabel.LEFT);
									lblFrom.setPreferredSize(new Dimension(75, 0));
								}
								{
									dteDateFrom = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
									panDateFrom.add(dteDateFrom, BorderLayout.CENTER);						
									dteDateFrom.setDate(null);
									dteDateFrom.setEnabled(false);
									dteDateFrom.setDate(FncGlobal.dateFormat("2016-01-01"));
								}
							}
						}
						{
							JXPanel panDateTo = new JXPanel(new BorderLayout(5, 5)); 
							panDate.add(panDateTo);
							{
								{
									JLabel lblTo = new JLabel("To: "); 
									panDateTo.add(lblTo, BorderLayout.LINE_START); 
									lblTo.setHorizontalAlignment(JLabel.CENTER);
									lblTo.setPreferredSize(new Dimension(75, 0));
								}
								{
									dateDateTo = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
									panDateTo.add(dateDateTo, BorderLayout.CENTER);
									dateDateTo.setDate(null);
									dateDateTo.setEnabled(false);
									dateDateTo.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
								}
							}
						}
					}
				}
				{
					pnlCPFreport_type = new JPanel(new BorderLayout(5, 5));
					pnlCenter2.add(pnlCPFreport_type, BorderLayout.CENTER);
					pnlCPFreport_type.setPreferredSize(new java.awt.Dimension(499, 46));
					{
						{
							JLabel lblReportName = new JLabel("Report: "); 
							pnlCPFreport_type.add(lblReportName, BorderLayout.LINE_START); 
							lblReportName.setHorizontalAlignment(JLabel.RIGHT);
							lblReportName.setPreferredSize(new Dimension(75, 0));
						}
						{
							String status[] = new String[] {
								"Commission Processed and Released (Active Accounts)",
								"Commission Processed and Released (Inactive Accounts)",
								"Promo/Incentives Processed and Released Summary"
							};
							
							cmbReportName = new JComboBox(status);
							pnlCPFreport_type.add(cmbReportName, BorderLayout.CENTER);
							cmbReportName.setSelectedItem(null);
							cmbReportName.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,13));
							cmbReportName.setEnabled(false);
							cmbReportName.setSelectedIndex(0);	
							cmbReportName.setPreferredSize(new java.awt.Dimension(418, 65));
							cmbReportName.addItemListener(new ItemListener() {
								public void itemStateChanged(ItemEvent evt) {
									
								}
							});
						}
					}
				}
			}
			{				
				pnlSouth = new JPanel(new BorderLayout(5, 5));
				pnlMain.add(pnlSouth, BorderLayout.SOUTH);
				pnlSouth.setPreferredSize(new Dimension(300, 30));
				{
					{
						btnPreview = new JButton("Preview");
						pnlSouth.add(btnPreview, BorderLayout.CENTER);
						btnPreview.setAlignmentX(0.5f);
						btnPreview.setAlignmentY(0.5f);
						btnPreview.setEnabled(false);
						btnPreview.setMaximumSize(new Dimension(100, 30));
						btnPreview.setMnemonic(KeyEvent.VK_P);
						btnPreview.addActionListener(this);
					}
				}
			}
		}
		
		this.setFocusTraversalPolicy(new FncFocusTraversalPolicy(lookupCompany, lookupProject, dteDateFrom, dateDateTo, btnPreview));
		this.setBounds(0, 0, 513, 307);  //174
		initialize_comp();
		 
		ReloadData(); 
	}

	public void ancestorAdded(AncestorEvent event) {
		lookupProject.requestFocus();
	}

	public void ancestorMoved(AncestorEvent event) {
		
	}

	public void ancestorRemoved(AncestorEvent event) {
		
	}

	public void refreshFields(){		
		lookupProject.setText("");
		txtProject.setText("");
		dteDateFrom.setDate(FncGlobal.dateFormat("2016-01-01"));
		dateDateTo.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
		cmbReportName.setSelectedIndex(0);	
	}

	private void previewCommProc_Rlsd_Active(){

		String strDate = "";
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String company_logo = RequestForPayment.sql_getCompanyLogo();
		
		if (dteDateFrom.getDate().equals(dateDateTo.getDate())) {
			strDate = df.format(dteDateFrom.getDate());
		} else {
			strDate = df.format(dteDateFrom.getDate()).toString() + " to " + df.format(dateDateTo.getDate()).toString();  
		}
		
		String criteria = "Commission Processed and Released (Active Accounts)";		
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());		

		System.out.println("company: "+company);
		System.out.println("co_id: "+co_id);
		System.out.println("proj_id: "+proj_id);
		System.out.println("project: "+project);
		System.out.println("UserInfo.Alias: "+UserInfo.Alias);
		System.out.println("phase_no: "+phase_no);
		System.out.println("dteDateFrom.getDate(): "+dteDateFrom.getDate());
		System.out.println("dateDateTo.getDate(): "+dateDateTo.getDate());
		System.out.println("strDate: "+strDate);
		System.out.println("PC: "+ip_address);
		
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("01_Company", company);
		mapParameters.put("02_CoID", co_id);
		mapParameters.put("03_ProID", proj_id);
		mapParameters.put("04_Project", project);
		mapParameters.put("05_User", UserInfo.Alias);
		mapParameters.put("06_Logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
		mapParameters.put("07_phase", phase_no);
		mapParameters.put("08_dateFrom", dteDateFrom.getDate());
		mapParameters.put("09_dateTo", dateDateTo.getDate());
		mapParameters.put("10_AsOfDate", strDate);
		mapParameters.put("11_status", "Active");
		mapParameters.put("12_pc", ip_address);
		
		if ((JOptionPane.showOptionDialog(null, "Select Report Type", "Generate", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, 
		new Object[] {"   Client   ", "   Broker   "}, JOptionPane.CANCEL_OPTION))==JOptionPane.YES_OPTION) {
			System.out.println("");
			System.out.println("Dumaan dito sa YES");
			FncReport.generateReport("/Reports/rpt_financialreport_commpractive_clients.jasper", reportTitle+" - Client", company, mapParameters);
		} else {
			System.out.println("");
			System.out.println("Dumaan dito sa NO");
			FncReport.generateReport("/Reports/rpt_financialreport_commpractive_broker.jasper", reportTitle+" - Broker", company, mapParameters);
		}			
	}
	
	private void previewCommProc_Rlsd_Inactive(){
		
		String criteria = "Commission Processed and Released (Inactive Accounts)";		
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());		

		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("co_id", co_id);
		mapParameters.put("company", company);	
		mapParameters.put("proj_id", proj_id);
		mapParameters.put("project", project);	
		mapParameters.put("phase_no", phase_no);
		mapParameters.put("phase_code", phase_code);
		mapParameters.put("prepared_by", UserInfo.Alias);
		mapParameters.put("prepared_by_name", UserInfo.FullName);
		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
		mapParameters.put("date_from", dteDateFrom.getDate());
		mapParameters.put("date_to", dateDateTo.getDate());		
		mapParameters.put("status", "Inactive");	

		FncReport.generateReport("/Reports/rptCommRptFR_CommActive.jasper", reportTitle, company, mapParameters);		
	}
		
	private void previewPromoProc_Rlsd_Active(){

		String strDate = "";
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String company_logo = RequestForPayment.sql_getCompanyLogo();
		
		if (dteDateFrom.getDate().equals(dateDateTo.getDate())) {
			strDate = df.format(dteDateFrom.getDate());
		} else {
			strDate = df.format(dteDateFrom.getDate()).toString() + " to " + df.format(dateDateTo.getDate()).toString();  
		}
		
		String criteria = "Promo Processed and Released";		
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());		

		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("01_Company", company);
		mapParameters.put("02_CoID", co_id);
		mapParameters.put("03_ProID", proj_id);
		mapParameters.put("04_Project", project);
		mapParameters.put("05_User", UserInfo.Alias);
		mapParameters.put("06_Logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
		mapParameters.put("07_phase", phase_no);
		mapParameters.put("08_dateFrom", dteDateFrom.getDate());
		mapParameters.put("09_dateTo", dateDateTo.getDate());
		mapParameters.put("10_AsOfDate", strDate);
		mapParameters.put("11_status", "Active");
		
		//FncReport.generateReport("/Reports/rptCommRptFR_Promo.jasper", reportTitle, company, mapParameters);		
		FncReport.generateReport("/Reports/rpt_financialreport_promo_summary.jasper", reportTitle, company, mapParameters);
	}
	
	public void initialize_comp(){		
		
		co_id 		= "02";	
		company		= "CENQHOMES DEVELOPMENT CORPORATION";
		company_logo = RequestForPayment.sql_getCompanyLogo();		
		txtCompany.setText(company);

		refreshFields();		
					
		txtCompany.setText(company);
		lookupProject.setLookupSQL(CheckStatusMonitoring.sql_project(co_id));
		lookupPhase.setLookupSQL(getPhase());
		lblProject.setEnabled(true);	
		lookupProject.setText("");
		lookupProject.setEnabled(true);
		txtProject.setEnabled(true);
		txtProject.setText("");

		dteDateFrom.setEnabled(true);	
		dateDateTo.setEnabled(true);

		cmbReportName.setEnabled(true);
		
		btnPreview.setEnabled(true);
		
		KEYBOARD_MANAGER.focusNextComponent();
		btnPreview.setEnabled(true);
		
		lookupCompany.setValue(co_id);
	}

	private String getPhase() {
		return "select trim(sub_proj_id) as \"Phase ID\", trim(phase) as \"Phase\" \n" + 
		"from mf_sub_project \n" +
		"WHERE status_id = 'A' \n"+//ADDED STATUS ID BY LESTER DCRF 2718
		"order by sub_proj_id";

	}
	
	/*
	private void Prime() {
		
		FncGlobal.startProgress("Priming report.. Please wait...");
		
		pgUpdate dbExec = new pgUpdate(); 
		dbExec.executeUpdate("delete from tmp_comm_processed", false);
		dbExec.executeUpdate("delete from tmp_comm_released", false);
		dbExec.commit();
		
		
		dbExec = new pgUpdate();
		dbExec.executeUpdate("insert into tmp_comm_processed \n" + 
				"select bx.agent_code, ax.pbl_id, ax.seq_no::int, (sum(ax.applied_amt) + sum(ax.wtax_amt) + sum(ax.caliq_amt)) as comm_processed \n" + 
				"from cm_cdf_dl ax \n" + 
				"inner join cm_cdf_hd bx on ax.cdf_no = bx.cdf_no \n" + 
				"left join rf_pv_header cx on bx.rplf_no = cx.pv_no \n" + 
				"where ((bx.cdf_date::date between '"+dteDateFrom.getDate()+"'::date and '"+dateDateTo.getDate()+"'::date) or (cx.pv_date::date between '"+dteDateFrom.getDate()+"'::date and '"+dateDateTo.getDate()+"'::date)) \n" + 
				"and exists(select * from cm_schedule_dl x where x.cdf_no = ax.cdf_no) and ax.tran_type = 'AA' \n" + 
				"and not exists(select * from rf_cv_header x where x.cv_no = cx.cv_no and x.date_paid is not null and x.status_id != 'I') and ax.tran_type = 'AA' \n" + 
				"group by bx.agent_code, ax.pbl_id, ax.seq_no::int", true);
		dbExec.commit();

		dbExec = new pgUpdate();
		dbExec.executeUpdate("insert into tmp_comm_released \n" + 
				"select bx.agent_code, ax.pbl_id, ax.seq_no, (sum(ax.applied_amt) + sum(ax.wtax_amt) + sum(ax.caliq_amt)) as comm_released \n" + 
				"from cm_cdf_dl ax \n" + 
				"inner join cm_cdf_hd bx on ax.cdf_no = bx.cdf_no \n" + 
				"left join rf_pv_header cx on bx.rplf_no = cx.pv_no \n" + 
				"where ((bx.cdf_date::date between '"+dteDateFrom.getDate()+"'::date and '"+dateDateTo.getDate()+"'::date) or (cx.pv_date::date between '"+dteDateFrom.getDate()+"'::date and '"+dateDateTo.getDate()+"'::date)) \n" + 
				"and exists(select * from rf_cv_header x where x.cv_no = cx.cv_no and x.date_paid is not null and x.status_id != 'I') \n" + 
				"and exists(select * from cm_schedule_dl x where x.cdf_no = ax.cdf_no) and ax.tran_type = 'AA' \n" + 
				"group by bx.agent_code, ax.pbl_id, ax.seq_no", true);
		dbExec.commit();
		
		FncGlobal.stopProgress();

	}
	*/
	
    public static void Prime() {
    	SwingWorker sw = new SwingWorker() {

			protected Object doInBackground() throws FileNotFoundException, IOException, InterruptedException {
				FncGlobal.startProgress("Updating data.. Please wait..");
				
				InetAddress inetAddress = InetAddress.getLocalHost();
				ip_address = inetAddress.getHostName(); 
				
				btnPreview.setEnabled(false);
				
				pgUpdate dbExec = new pgUpdate(); 
				
				dbExec.executeUpdate("insert into tmp_comm_data \n" + 
						"select bx.agent_code, ax.pbl_id, ax.seq_no::int, (sum(ax.applied_amt) + sum(ax.wtax_amt) + sum(ax.caliq_amt)) as comm_processed, \n" + 
						"ax.tran_type, coalesce(ax.promo_code, ''), ax.cdf_no, 'P' as status, '"+UserInfo.EmployeeCode+"', now(), ax.comm_type, 'O', '"+ip_address+"' \n" + 
						"from cm_cdf_dl ax \n" + 
						"inner join (select * from cm_cdf_hd where status_id != 'I') bx on ax.cdf_no = bx.cdf_no \n" + 
						"left join rf_pv_header cx on bx.rplf_no = cx.pv_no and bx.co_id = cx.co_id \n" + //Edited by Erick 2021-09-17 added co_id as filter
						"where ((bx.cdf_date::date between '"+dteDateFrom.getDate()+"'::date and '"+dateDateTo.getDate()+"'::date) or (cx.pv_date::date between '"+dteDateFrom.getDate()+"'::date and '"+dateDateTo.getDate()+"'::date)) \n" + 
						"and exists(select * from cm_schedule_dl x where x.cdf_no = ax.cdf_no) \n" + 
						"and not exists(select * from rf_cv_header x where x.cv_no = cx.cv_no  and x.date_paid is not null and x.status_id != 'I' and x.co_id = cx.co_id)\n" +//Edited by Erick 2021-09-17 added co_id as filter
						"and not (ax.applied_amt = 0.00 or coalesce(bx.rplf_no,'') = '')\n" +
						"group by bx.agent_code, ax.pbl_id, ax.seq_no::int, ax.tran_type, coalesce(ax.promo_code, ''), ax.cdf_no, ax.comm_type \n" + 
						"union all\n" + 
						"select bx.agent_code, ax.pbl_id, ax.seq_no, (sum(ax.applied_amt) + sum(ax.wtax_amt) + sum(ax.caliq_amt)) as comm_released, \n" + 
						"ax.tran_type, ax.promo_code, ax.cdf_no, 'R' as status, '"+UserInfo.EmployeeCode+"', now(), ax.comm_type, 'O', '"+ip_address+"'  \n" + 
						"from cm_cdf_dl ax \n" + 
						"inner join (select * from cm_cdf_hd where status_id != 'I') bx on ax.cdf_no = bx.cdf_no \n" + 
						"left join rf_pv_header cx on bx.rplf_no = cx.pv_no and bx.co_id = cx.co_id \n" + //Edited by Erick 2021-09-17 added co_id as filter
						"where ((bx.cdf_date::date between '"+dteDateFrom.getDate()+"'::date and '"+dateDateTo.getDate()+"'::date) or (cx.pv_date::date between '"+dteDateFrom.getDate()+"'::date and '"+dateDateTo.getDate()+"'::date)) \n" + 
						"and (exists(select * from rf_cv_header x where x.cv_no = cx.cv_no and x.date_paid is not null and x.status_id != 'I' and x.co_id = cx.co_id) or (ax.applied_amt = 0.00 or coalesce(bx.rplf_no,'') = ''))\n" + //Edited by Erick 2021-09-17 added co_id as filter
						"and exists(select * from cm_schedule_dl x where x.cdf_no = ax.cdf_no)\n" + 
						"group by bx.agent_code, ax.pbl_id, ax.seq_no, ax.tran_type, ax.promo_code, ax.cdf_no, ax.comm_type", true);
				
				dbExec.commit();
				
				dbExec = new pgUpdate(); 
				dbExec.executeUpdate("update tmp_comm_data \n" +
						"set status_id = 'I' \n" +
						"where pc_name = '"+ip_address+"' and status_id = 'A'", true);
				dbExec.commit();

				dbExec = new pgUpdate(); 
				dbExec.executeUpdate("update tmp_comm_data \n" +
						"set status_id = 'A' \n" +
						"where pc_name = '"+ip_address+"' and status_id = 'O'", true);
				dbExec.commit();
				
				dbExec = new pgUpdate(); 
				dbExec.executeUpdate("delete from tmp_comm_data where pc_name = '"+ip_address+"' and status_id = 'I'", true);
				dbExec.commit();
				
				FncGlobal.stopProgress();
				btnPreview.setEnabled(true);
				return null;
			}
    	}; 
    	sw.execute(); 
    }
    
    private void ReloadData() {
        int delay = 0;
        int period = 120000;
        
        timer = new Timer();
        interval = 60;
        System.out.println(interval);
        
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                Prime();
            }
        }, delay, period);
    }
    
	public void actionPerformed(ActionEvent e) {
		
		if (e.getActionCommand().equals("Preview") && cmbReportName.getSelectedIndex()==0) {
			System.out.println("");
			System.out.println("Dumaan dito sa Active");
			System.out.println("getSelectedIndex: "+cmbReportName.getSelectedIndex());
			previewCommProc_Rlsd_Active();
		}
		
		if (e.getActionCommand().equals("Preview") && cmbReportName.getSelectedIndex()==1) {
			System.out.println("");
			System.out.println("Dumaan dito sa Inactive");
			previewCommProc_Rlsd_Inactive();
		}
		
		if (e.getActionCommand().equals("Preview") && cmbReportName.getSelectedIndex()==2) {
			previewPromoProc_Rlsd_Active();
		}

		if (e.getActionCommand().equals("Cancel")) {			
			dteDateFrom.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
			dateDateTo.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
			lookupProject.setValue("");
			lookupPhase.setValue("");
			txtProject.setText("");
			txtPhase.setText("");
			btnPreview.setEnabled(false);
			proj_id = "";
			project = "All";
			phase_no= "All";
			phase_code	= "";
		}
	}
    
}