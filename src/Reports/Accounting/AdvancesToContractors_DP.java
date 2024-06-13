package Reports.Accounting;

import interfaces._GUI;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import Accounting.Collections.CheckStatusMonitoring;
import Accounting.Disbursements.RequestForPayment;
import DateChooser._JDateChooser;
import Functions.FncFocusTraversalPolicy;
import Functions.FncGlobal;
import Functions.FncLookAndFeel;
import Functions.FncReport;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import Projects.BiddingandAwarding._NoticeToProceed;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JTagLabel;
import java.awt.event.WindowListener;
public class AdvancesToContractors_DP extends _JInternalFrame implements ActionListener, AncestorListener, _GUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1162406788303915011L;

	static String title = "Advances to Contractors - Downpayment";
	//static Dimension frameSize = new Dimension(624, 320);
	static Dimension frameSize = new Dimension(620, 440);
	static Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	static Cursor handCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);

	private JPanel pnlMain;
	private JPanel pnlNorth;
	private JPanel pnlNorthWest;
	private JPanel pnlCenter;
	private JPanel pnlNorthEast;
	private JPanel pnlNorth_comp;
	private JPanel pnlDRFDtl_2a;
	private JPanel pnlDRFDtl_2b;
	private JPanel pnlCenter_a;
	private JPanel pnlCenter_2;
	private JPanel pnlSouth;
	private JPanel pnlNorth_comp_a;
	private JPanel pnlCenter2;
	private JPanel pnlCriteria2;	

	private static JLabel lblProject;
	private static JLabel lblNTP_no;
	private static JLabel lblContractor;
	private JLabel lblDateFrom;
	private JLabel lblDateTo;

	private static _JTagLabel tagProject;
	private static _JTagLabel tagContractor;
	private static _JTagLabel tagNTP_no;

	public static _JLookup lookupCompany;
	private static _JLookup lookupProject;
	private static _JLookup lookupContractor;
	private static _JLookup lookupNTP_no;	

	public static JTextField txtCompany;

	private static JButton btnPreview;
	private static JButton btnCancel;

	private _JDateChooser dteDateFrom;	
	private _JDateChooser dteDateTo;

	private static JCheckBox chkRemoveZero;

	String company;
	public static String company_logo;	

	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");

	public String co_id 		= "";
	public String proj_id 		= "";
	public String proj_name		= "";
	public String contractor_id 	= "";
	public String contractor_name	= "";
	public String ntp_no 		= "";
	public String ntp 		    = "";
	public String contract_no 		= "";
	public String prepared_by_id	= "";
	public String prepared_by_name	= "";
	public String remove_zer_bal	= "";	
	public String ph_code = "";
	public String ph_no = "";

	DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

	private static JLabel lblPhase;

	private static _JLookup lookupPhase;

	private static _JTagLabel tagPhase;

	protected _JInternalFrame frame = new _JInternalFrame();

	private JPanel pnlbillingdate;

	private JPanel pnlcompany;

	public AdvancesToContractors_DP() {
		super(title, false, true, true, true);
		initGUI();
	}

	public AdvancesToContractors_DP(String title) {
		super(title);
		initGUI();
	}

	public AdvancesToContractors_DP(String title, boolean resizable, boolean closable,  boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();

	}

	@Override
	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		this.setPreferredSize(frameSize);//170
		this.setLayout(new BorderLayout());
		this.setResizable(true);
		this.addAncestorListener(this);
		this.setMinimumSize(frameSize);//500

		{
			JPanel panMain = new JPanel(new GridBagLayout()); 
			this.add(panMain, BorderLayout.CENTER);
			panMain.setBorder(new EmptyBorder(5, 5, 5, 5));
			{
				GridBagConstraints c = new GridBagConstraints(); 
				c.fill = c.BOTH; 

				{
					c.gridx = 0; 
					c.gridy = 0; 
					c.weightx = 1; 
					c.weighty = 0.5;	
					c.ipady = 15; 

					JPanel panel = new JPanel(new BorderLayout());
					//panel.setBorder(JTBorderFactory.createTitleBorder("Company", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
					panel.setBorder(JTBorderFactory.createTitleBorder("", FncGlobal.sizeLabel));
					panel.add(getCompany(), BorderLayout.CENTER); 

					panMain.add(panel, c); 
				}
				{
					c.gridx = 0; 
					c.gridy = 1; 
					c.weightx = 1; 
					c.weighty = 0.5; 
					c.ipady = 15; 

					JPanel panel = new JPanel(new BorderLayout());
					//panel.setBorder(JTBorderFactory.createTitleBorder("Billing Date", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
					panel.setBorder(JTBorderFactory.createTitleBorder("Billing Date", FncGlobal.sizeLabel));
					panel.add(getbillingdate(), BorderLayout.CENTER); 
					panMain.add(panel, c); 
				}
				{
					c.gridx = 0; 
					c.gridy = 2; 
					c.weightx = 1; 
					c.weighty = 3; 
					c.ipady = 70; 

					JPanel panel = new JPanel(new BorderLayout());
					//panel.setBorder(JTBorderFactory.createTitleBorder("Search Options", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
					panel.setBorder(JTBorderFactory.createTitleBorder("Search Options", FncGlobal.sizeLabel));
					panel.add(getsearchoptions(), BorderLayout.CENTER);
					panMain.add(panel, c); 
				}
				{
					c.gridx = 0; 
					c.gridy = 3; 
					c.weightx = 1; 
					c.weighty = 0.5;
					c.ipady = 15; 

					JPanel panel = new JPanel(new BorderLayout());
					//panel.setBorder(JTBorderFactory.createTitleBorder("Button", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
					panel.add(getbutton(), BorderLayout.CENTER);
					panMain.add(panel, c); 
				}
			}
		}


				this.setFocusTraversalPolicy(new FncFocusTraversalPolicy(lookupCompany, lookupProject,	btnPreview));
				this.setBounds(0, 0, 624, 367);  //174
		
				//added 01/26/2016 - purpose - set CENQHOMES as default company
				initialize_comp();
	}
	@Override
	public void ancestorAdded(AncestorEvent event) {

	}

	@Override
	public void ancestorMoved(AncestorEvent event) { }

	@Override
	public void ancestorRemoved(AncestorEvent event) { }

	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getActionCommand().equals("Preview")){		
			String criteria = "Advances to Contractor - DP";		
			String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());

			if (proj_id.equals("")) {proj_name = "All";} else {}
			if (ph_no.equals("")) {ph_no = "All";} else {}
			if (contractor_id.equals("")) {contractor_name = "All";} else {}
			if (ntp_no.equals("")) {ntp = "All";} else {ntp = ntp_no;}			
			if (chkRemoveZero.isSelected()==true) {remove_zer_bal = "yes";} else {remove_zer_bal = "no";}			

			Map<String, Object> mapParameters = new HashMap<String, Object>();
			mapParameters.put("co_id", co_id);
			mapParameters.put("proj_id", proj_id);
			mapParameters.put("company", company);
			mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
			mapParameters.put("prepared_by", UserInfo.Alias);
			mapParameters.put("prepared_name", prepared_by_name);	
			mapParameters.put("project", proj_name);	
			mapParameters.put("contractor_id", contractor_id);	
			mapParameters.put("contractor_name", contractor_name);	
			mapParameters.put("ntp_no", ntp_no);	
			mapParameters.put("ntp", ntp);	
			//mapParameters.put("date_from", df.format(dteDateFrom.getDate()));
			mapParameters.put("date_to", df.format(dteDateTo.getDate()));
			mapParameters.put("ph_code", ph_code);		
			mapParameters.put("ph_no", ph_no);
			mapParameters.put("remove_zer_bal", remove_zer_bal);
			
			
			System.out.println("Display Report function:\n"+ "select * from view_sched_advances_to_contr_dp('"+co_id+"','"+proj_id+"','"+ph_code+"','"+contractor_id+"','"+ntp_no+"','"+df.format(dteDateTo.getDate())+"','"+remove_zer_bal+"')");
			System.out.printf("Display value of co_id: (%s)%n", co_id);
			System.out.printf("Display proj_id: (%s)%n", proj_id);
			System.out.printf("Display value of ph_code: (%s)%n", ph_code);
			System.out.printf("Display value of contractor_id: (%s)%n", contractor_id);
			System.out.printf("Display ntp no: (%s)%n", ntp_no);
			//System.out.printf("Date from: (%s)%n", df.format(dteDateFrom.getDate()));
			System.out.printf("Date from: (%s)%n", df.format(dteDateTo.getDate()));
			System.out.printf("Display zero balance: (%s)%n", remove_zer_bal);

			FncReport.generateReport("/Reports/rptAdvancesToContractors-DP.jasper", reportTitle, "", mapParameters);
		}
		
		if(e.getActionCommand().equals("Cancel")){	
			refreshFields();
		}

	}

	public static void enabledFields(boolean x){

		lblProject.setEnabled(x);	
		lblContractor.setEnabled(x);	
		lblNTP_no.setEnabled(x);
		lblPhase.setEnabled(x);

		lookupProject.setEnabled(x);	
		lookupContractor.setEnabled(x);	
		lookupNTP_no.setEnabled(x);	
		lookupPhase.setEnabled(x);	

		tagProject.setEnabled(x);	
		tagContractor.setEnabled(x);	
		tagNTP_no.setEnabled(x);
		tagPhase.setEnabled(x);	

		chkRemoveZero.setEnabled(x);	
		btnCancel.setEnabled(x);
		btnPreview.setEnabled(x);
	}

	public void refreshFields(){

		lookupProject.setValue("");	
		lookupContractor.setValue("");	
		lookupNTP_no.setValue("");	
		lookupPhase.setValue("");	

		tagProject.setTag("");
		tagContractor.setTag("");
		tagNTP_no.setTag("");	
		tagPhase.setTag("");	

		proj_id 	= "";
		proj_name	= "";
		contractor_id 		= "";
		contractor_name		= "";
		ntp_no 		= "";
		contract_no 	= "";
		prepared_by_id	= "";
		prepared_by_name= "";

		ph_code = "";
		ph_no = "";
	}

	public void initialize_comp(){		

				co_id 		= "02";	
				company		= "CENQHOMES DEVELOPMENT CORPORATION";		
				company_logo = RequestForPayment.sql_getCompanyLogo();							
				txtCompany.setText(company);
		
				enabledFields(true);
				lookupProject.setLookupSQL(CheckStatusMonitoring.sql_project(co_id));	
				lookupContractor.setLookupSQL(_NoticeToProceed.Contractor());	
				lookupNTP_no.setLookupSQL(_NoticeToProceed.NTPNo());	
		
				lookupCompany.setValue(co_id);
	}

	public String getSubproject(){
		String sql = 
				"select \r\n" + 
						"distinct on (a.proj_id, a.sub_proj_id)\r\n" + 
						"\r\n" + 
						"c.sub_proj_alias  as \"Alias\",\r\n" + 
						"a.phase as \"Phase\",  \r\n" + 
						"a.proj_id as \"Proj Code\",\r\n" + 
						"b.proj_name as \"Proj Name\"  \r\n" + 

			"from mf_unit_info a\r\n" + 
			"left join mf_project b on a.proj_id = b.proj_id \r\n" + 
			"left join mf_sub_project c on a.sub_proj_id = c.sub_proj_id and c.status_id = 'A'\r\n" +//ADDED STATUS ID BY LESTER DCRF 2718 
			"where co_id = '"+co_id+"' " +
			"and a.proj_id = '"+proj_id+"' " +
			"and a.sub_proj_id != ''   " +
			"order by a.sub_proj_id" ;		
		return sql;

	}

	private JPanel getCompany() {
		GridBagConstraints c = new GridBagConstraints(); 
		JPanel panCompany = new JPanel(new GridBagLayout()); 
		c.fill = c.BOTH; 
		c.ipady = 10;
		c.insets = new Insets(0, 3, 0, 3); 

		{
			c.gridx = 0; 
			c.gridy = 0; 

			c.weightx = .05;
			c.weighty = 1; 

			c.ipadx = 5;

			JLabel lblCompany = new JLabel("Company");
			lblCompany.setFont(FncGlobal.sizeLabel);
			panCompany.add(lblCompany, c); 
			//lblCompany.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		}
		{
			c.gridx = 1; 
			c.gridy = 0; 

			c.weightx = .5;
			c.weighty = 1;

			c.ipadx = 5; 

			lookupCompany = new _JLookup(null, "Company");
			lookupCompany.setFont(FncGlobal.sizeTextValue);
			lookupCompany.setReturnColumn(0);
			lookupCompany.setLookupSQL(SQL_COMPANY());
			lookupCompany.addLookupListener(new LookupListener() {
				public void lookupPerformed(LookupEvent event) {
					Object[] data = ((_JLookup)event.getSource()).getDataSet();
					if(data != null){

						co_id = (String) data[0];
						company = (String) data[1];
						company_logo = (String) data[3];

						String name = (String) data[1];						
						txtCompany.setText(name);

						enabledFields(true);
						lookupProject.setLookupSQL(CheckStatusMonitoring.sql_project((String) data[0]));	
						lookupContractor.setLookupSQL(_NoticeToProceed.Contractor());	
						lookupNTP_no.setLookupSQL(_NoticeToProceed.NTPNo());	
					}
				}
			});

			panCompany.add(lookupCompany, c); 
		}

		{
			c.gridx = 4; 
			c.gridy = 0; 

			c.weightx = 2;
			c.weighty = 1;

			c.ipadx = 50; 

			txtCompany = new JTextField();
			txtCompany.setFont(FncGlobal.sizeTextValue);
			txtCompany.setEditable(false);

			panCompany.add(txtCompany, c); 
		}

		return panCompany; 
	}
	
	private JPanel getbillingdate() {
		GridBagConstraints c = new GridBagConstraints(); 
		JPanel panbillingdate = new JPanel(new GridBagLayout()); 
		c.fill = c.BOTH; 
		c.ipady = 10;
		c.insets = new Insets(0, 3, 0, 3);

		{
			c.gridx = 0; 
			c.gridy = 0; 

			c.weightx = .05;
			c.weighty = 1; 

			c.ipadx = 5;

			lblDateTo = new JLabel("As of:    ", JLabel.CENTER);
			lblDateTo.setFont(FncGlobal.sizeLabel);
			//lblDateTo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			panbillingdate.add(lblDateTo, c); 
		}
		{
			c.gridx = 1; 
			c.gridy = 0; 

			c.weightx = .5;
			c.weighty = 1;

			c.ipadx = 5; 

			dteDateTo = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
			dteDateTo.setFont(FncGlobal.sizeTextValue);
			dteDateTo.setDate(null);
			dteDateTo.setEnabled(true);
			dteDateTo.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));

			panbillingdate.add(dteDateTo, c); 
		}
		{
			c.gridx = 4; 
			c.gridy = 0; 

			c.weightx = 2;
			c.weighty = 1;

			c.ipadx = 50; 

			JLabel lblbillingdate_extra = new JLabel("");
			panbillingdate.add(lblbillingdate_extra, c); 
		}

		

		return panbillingdate; 
	}
	private JPanel getsearchoptions() {
		GridBagConstraints c = new GridBagConstraints(); 
		JPanel pansearchoptions = new JPanel(new GridBagLayout()); 
		c.fill = c.BOTH; 
		c.ipady = 10;
		c.insets = new Insets(3, 3, 3, 3);
		
			
			//column 0
			{
				c.gridx = 0; 
				c.gridy = 0; 

				c.weightx = .05;
				c.weighty = 1; 

				c.ipadx = 10;
				
				lblProject = new JLabel("Project", JLabel.TRAILING);
				pansearchoptions.add(lblProject,c);
				lblProject.setFont(FncGlobal.sizeLabel);
				//lblProject.setBorder(BorderFactory.createLineBorder(Color.black));
				lblProject.setEnabled(false);
			}	
			{
				c.gridx = 0; 
				c.gridy = 1; 

				c.weightx = .05;
				c.weighty = 1; 

				c.ipadx = 10;
				
				lblPhase = new JLabel("       Phase", JLabel.TRAILING);
				lblPhase.setFont(FncGlobal.sizeLabel);
				pansearchoptions.add(lblPhase,c);
				lblPhase.setEnabled(false);	
			}
			{
				c.gridx = 0; 
				c.gridy = 2; 

				c.weightx = .05;
				c.weighty = 1; 

				c.ipadx = 10;
				lblContractor = new JLabel("Contractor", JLabel.TRAILING);
				lblContractor.setFont(FncGlobal.sizeLabel);
				pansearchoptions.add(lblContractor,c);
				lblContractor.setEnabled(false);	
			}	
			{
				c.gridx = 0; 
				c.gridy = 3; 

				c.weightx = .05;
				c.weighty = 1; 

				c.ipadx = 10;
				lblNTP_no= new JLabel("NTP No.", JLabel.TRAILING);
				lblNTP_no.setFont(FncGlobal.sizeLabel);
				pansearchoptions.add(lblNTP_no,c);
				lblNTP_no.setEnabled(false);	
			}
			
			//column 1
			{
				c.gridx = 1; 
				c.gridy = 0; 

				c.weightx = .5;
				c.weighty = 1; 

				c.ipadx = 10;
				
				
				lookupProject = new _JLookup(null, "Request Type", 2, 2);
				pansearchoptions.add(lookupProject,c);
				lookupProject.setFont(FncGlobal.sizeTextValue);
				lookupProject.setReturnColumn(0);
				lookupProject.setEnabled(false);
				lookupProject.setPreferredSize(new java.awt.Dimension(157, 22));
				lookupProject.addLookupListener(new LookupListener() {
					public void lookupPerformed(LookupEvent event) {
						Object[] data = ((_JLookup)event.getSource()).getDataSet();
						if(data != null){		
							proj_id 	= (String) data[0];		
							proj_name 	= (String) data[1];			
							tagProject.setTag(proj_name);
							lookupPhase.setLookupSQL(getSubproject());
						}
					}
				});	
				
			}
			{
				c.gridx = 1; 
				c.gridy = 1; 

				c.weightx = .5;
				c.weighty = 1; 

				c.ipadx = 10;
				
				lookupPhase = new _JLookup(null, "Phase",0,2);
				pansearchoptions.add(lookupPhase,c);
				lookupPhase.setFont(FncGlobal.sizeTextValue);
				lookupPhase.setReturnColumn(0);
				lookupPhase.setEnabled(false);	
				lookupPhase.addLookupListener(new LookupListener() {
					public void lookupPerformed(LookupEvent event) {
						Object[] data = ((_JLookup)event.getSource()).getDataSet();
						if(data != null){

							ph_code		= (String) data[0];	
							ph_no		= (String) data[1];	
							tagPhase.setTag(ph_no);
						}
					}
				});
			}
			{
				c.gridx = 1; 
				c.gridy = 2; 

				c.weightx = .5;
				c.weighty = 1; 

				c.ipadx = 10;
				
				lookupContractor = new _JLookup(null, "Payee", 2, 2);
				pansearchoptions.add(lookupContractor,c);
				lookupContractor.setFont(FncGlobal.sizeTextValue);
				lookupContractor.setReturnColumn(1);
				lookupContractor.setEnabled(false);	
				lookupContractor.addLookupListener(new LookupListener() {
					public void lookupPerformed(LookupEvent event) {
						Object[] data = ((_JLookup)event.getSource()).getDataSet();
						if(data != null){	
							contractor_id 	= (String) data[0];		
							contractor_name 	= (String) data[1];
							lookupContractor.setValue(String.valueOf(contractor_id));
							tagContractor.setTag(contractor_name);
						}
					}
				});
			}
			{
				c.gridx = 1; 
				c.gridy = 3; 

				c.weightx = .5;
				c.weighty = 1; 

				c.ipadx = 10;
				
				lookupNTP_no = new _JLookup(null, "Availer", 2, 2);
				pansearchoptions.add(lookupNTP_no,c);
				lookupNTP_no.setFont(FncGlobal.sizeTextValue);
				lookupNTP_no.setReturnColumn(0);
				lookupNTP_no.setEnabled(false);	
				lookupNTP_no.addLookupListener(new LookupListener() {
					public void lookupPerformed(LookupEvent event) {
						Object[] data = ((_JLookup)event.getSource()).getDataSet();
						if(data != null){						
							ntp_no 		= (String) data[0];		
							contract_no 	= (String) data[1];			
							tagNTP_no.setTag(contract_no);
						}
					}
				});	
			}
			
			//column 2
			{
				c.gridx = 2; 
				c.gridy = 0; 

				c.weightx = 3;
				c.weighty = 1; 

				c.ipadx = 10;
				
				tagProject = new _JTagLabel("[ ]");
				pansearchoptions.add(tagProject,c);
				tagProject.setFont(FncGlobal.sizeTextValue);
				//tagProject.setBorder(BorderFactory.createLineBorder(Color.black));
				tagProject.setEnabled(false);	
			}
			{
				c.gridx = 2; 
				c.gridy = 1; 

				c.weightx = 1;
				c.weighty = 1; 

				c.ipadx = 10;
				
				tagPhase = new _JTagLabel("[ ]");
				pansearchoptions.add(tagPhase,c);
				tagPhase.setFont(FncGlobal.sizeTextValue);
				tagPhase.setEnabled(false);	
			}
			{
				c.gridx = 2; 
				c.gridy = 2; 

				c.weightx = 1;
				c.weighty = 1; 

				c.ipadx = 10;
				
				tagContractor = new _JTagLabel("[ ]");
				pansearchoptions.add(tagContractor,c);
				tagContractor.setFont(FncGlobal.sizeTextValue);
				tagContractor.setEnabled(false);	
			}
			{
				c.gridx = 2; 
				c.gridy = 3; 

				c.weightx = 1;
				c.weighty = 1; 

				c.ipadx = 10;
				
				tagNTP_no = new _JTagLabel("[ ]");
				pansearchoptions.add(tagNTP_no,c);
				tagNTP_no.setFont(FncGlobal.sizeTextValue);
				tagNTP_no.setEnabled(false);	
			}

		return pansearchoptions; 
	}
	
	private JPanel getbutton() {
		GridBagConstraints c = new GridBagConstraints(); 
		JPanel panbutton = new JPanel(new GridBagLayout()); 
		c.fill = c.BOTH; 
		c.ipady = 10;
		c.insets = new Insets(0, 5, 0, 5);
		
				
			{
				c.gridx = 0; 
				c.gridy = 0; 

				c.weightx = 1;
				c.weighty = 1; 

				c.ipadx = 10;
				chkRemoveZero = new JCheckBox("Remove zero balance? ");
				panbutton.add(chkRemoveZero, c);
				chkRemoveZero.setFont(FncGlobal.sizeLabel);
				chkRemoveZero.setEnabled(false);	
				chkRemoveZero.setHorizontalAlignment(JTextField.CENTER);	
			}
			{
				c.gridx = 1; 
				c.gridy = 0; 

				c.weightx = 1;
				c.weighty = 1; 

				c.ipadx = 10;
				btnPreview = new JButton("Preview");
				panbutton.add(btnPreview, c);
				btnPreview.setFont(FncGlobal.sizeControls);
				btnPreview.setAlignmentX(0.5f);
				btnPreview.setEnabled(false);
				btnPreview.setAlignmentY(0.5f);
				btnPreview.setMnemonic(KeyEvent.VK_P);
				btnPreview.addActionListener(this);
			}
			{
				c.gridx = 2; 
				c.gridy = 0; 

				c.weightx = 1;
				c.weighty = 1; 

				c.ipadx = 10;
				btnCancel = new JButton("Cancel");
				panbutton.add(btnCancel,c);
				btnCancel.setFont(FncGlobal.sizeControls);
				btnCancel.setAlignmentX(0.5f);
				btnCancel.setAlignmentY(0.5f);
				btnCancel.setMnemonic(KeyEvent.VK_P);
				btnCancel.setEnabled(false);
				btnCancel.addActionListener(this);
			}
				
			

		return panbutton; 
	}

}
