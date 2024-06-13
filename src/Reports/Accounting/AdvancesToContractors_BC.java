package Reports.Accounting;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
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

public class AdvancesToContractors_BC extends _JInternalFrame implements ActionListener, AncestorListener, _GUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1162406788303915011L;

	static String title = "Advances to Contractors - Backcharges";
	static Dimension frameSize = new Dimension(624, 300);
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
	
	private JLabel lblCompany;
	private static JLabel lblProject;
	private static JLabel lblContractor;
	private JLabel lblDateFrom;
	private JLabel lblDateTo;

	private static _JTagLabel tagProject;
	private static _JTagLabel tagContractor;

	public static _JLookup lookupCompany;
	private static _JLookup lookupProject;
	private static _JLookup lookupContractor;

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
	
	
	public AdvancesToContractors_BC() {
		super(title, true, true, true, true);
		initGUI();
	}

	public AdvancesToContractors_BC(String title) {
		super(title);
		initGUI();
	}

	public AdvancesToContractors_BC(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		this.setPreferredSize(frameSize);
		this.setLayout(new BorderLayout());
		this.addAncestorListener(this);
		this.setMinimumSize(frameSize);
		{
			pnlMain = new JPanel(new GridBagLayout());
			getContentPane().add(pnlMain, BorderLayout.CENTER);
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
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
					pnlMain.add(panel, c); 
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

					pnlMain.add(panel, c); 
				}
				{
					c.gridx = 0; 
					c.gridy = 2; 
					c.weightx = 1; 
					c.weighty = 0.5;	
					c.ipady = 20; 

					JPanel panel = new JPanel(new BorderLayout());
					//panel.setBorder(JTBorderFactory.createTitleBorder("Search Option", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
					panel.setBorder(JTBorderFactory.createTitleBorder("Search Option", FncGlobal.sizeLabel));
					panel.add(getsearchoptions(), BorderLayout.CENTER); 
					pnlMain.add(panel, c); 
				}
				{
					c.gridx = 0; 
					c.gridy = 3; 
					c.weightx = 2; 
					c.weighty = 0.5;	
					c.ipady = 15; 

					JPanel panel = new JPanel(new BorderLayout());
					//panel.setBorder(JTBorderFactory.createTitleBorder("", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
					//panel.setBorder(JTBorderFactory.createTitleBorder("", FncGlobal.sizeLabel));
					panel.add(getbutton(), BorderLayout.CENTER); 
					pnlMain.add(panel, c); 
				}
			}
		}
		

		this.setFocusTraversalPolicy(new FncFocusTraversalPolicy(lookupCompany, lookupProject,	btnPreview));
		//this.setBounds(0, 0, 624, 300);  //174

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
			String criteria = "Advances to Contractor - BC";		
			String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());

			if (proj_id.equals("")) {proj_name = "All";} else {}
			if (contractor_id.equals("")) {contractor_name = "All";} else {}
			if (chkRemoveZero.isSelected()==true) {remove_zer_bal = "yes";} else {remove_zer_bal = "no";}
			
			Date date_from = ((Date) dteDateFrom.getDate());	
			Date date_to = ((Date) dteDateTo.getDate());
			
			Map<String, Object> mapParameters = new HashMap<String, Object>();
			mapParameters.put("co_id", co_id);
			mapParameters.put("proj_id", proj_id);
			mapParameters.put("company", company);
			mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
			mapParameters.put("prepared_by", UserInfo.Alias);
			mapParameters.put("prepared_name", UserInfo.FullName);	
			mapParameters.put("project", proj_name);	
			mapParameters.put("contractor_id", contractor_id);	
			mapParameters.put("contractor_name", contractor_name);	
			mapParameters.put("remove_zer_bal", remove_zer_bal);
			mapParameters.put("date_from", date_from);
			mapParameters.put("date_to", date_to);

			System.out.println(date_from);
			System.out.println(date_to);


			FncReport.generateReport("/Reports/rptAdvancesToContractors-BC.jasper", reportTitle, "", mapParameters);
		}
		if(e.getActionCommand().equals("Cancel")){	
			refreshFields();
		}

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
			lookupCompany.setReturnColumn(1);
			lookupCompany.setFont(FncGlobal.sizeTextValue);
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

			lblDateFrom = new JLabel("Date From", JLabel.LEADING);
			lblDateFrom.setEnabled(true);
			lblDateFrom.setFont(FncGlobal.sizeLabel);
			//lblDateTo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			panbillingdate.add(lblDateFrom, c); 
		}
		{
			c.gridx = 1; 
			c.gridy = 0; 

			c.weightx = .5;
			c.weighty = 1;

			c.ipadx = 5; 

			dteDateFrom = new _JDateChooser("MM/dd/yyyy", "##/##/##",'_');
			dteDateFrom.setDate(null);
			dteDateFrom.setFont(FncGlobal.sizeTextValue);
			dteDateFrom.setEnabled(true);
			dteDateFrom.setDate(FncGlobal.dateFormat("2017-01-01"));

			panbillingdate.add(dteDateFrom, c); 
		}
		{
			c.gridx = 2; 
			c.gridy = 0; 

			c.weightx = .05;
			c.weighty = 1;

			c.ipadx = 5; 

			lblDateTo = new JLabel("Date To   ", JLabel.CENTER);
			lblDateTo.setEnabled(true);
			lblDateTo.setFont(FncGlobal.sizeLabel);
			panbillingdate.add(lblDateTo, c); 
		}
		{
			c.gridx = 3; 
			c.gridy = 0; 

			c.weightx = .5;
			c.weighty = 1;

			c.ipadx = 5; 

			dteDateTo = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
			dteDateTo.setDate(null);
			dteDateTo.setFont(FncGlobal.sizeTextValue);
			dteDateTo.setEnabled(true);
			dteDateTo.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));

			panbillingdate.add(dteDateTo, c); 
		}

		

		return panbillingdate; 
	}
	
	private JPanel getsearchoptions() {
		GridBagConstraints c = new GridBagConstraints(); 
		JPanel pansearchoptions = new JPanel(new GridBagLayout()); 
		c.fill = c.BOTH; 
		c.ipady = 7;
		c.insets = new Insets(3, 3, 3, 3);
		//column 0
		{
			c.gridx = 0; 
			c.gridy = 0; 

			c.weightx = .05;
			c.weighty = 1; 

			c.ipadx = 10;
			
			lblProject = new JLabel("Project", JLabel.TRAILING);
			lblProject.setFont(FncGlobal.sizeLabel);
			pansearchoptions.add(lblProject, c);
			lblProject.setEnabled(false);
		}	
		{
			c.gridx = 0; 
			c.gridy = 1; 

			c.weightx = .05;
			c.weighty = 1; 

			c.ipadx = 10;
			
			lblContractor = new JLabel("Contractor", JLabel.TRAILING);
			lblContractor.setFont(FncGlobal.sizeLabel);
			pansearchoptions.add(lblContractor, c);
			lblContractor.setEnabled(false);	
		}
		
		//column 1
		{
			c.gridx = 1; 
			c.gridy = 0; 

			c.weightx = 1;
			c.weighty = 1; 

			c.ipadx = 10;
			
			lookupProject = new _JLookup(null, "Request Type", 2, 2);
			pansearchoptions.add(lookupProject, c);
			lookupProject.setFont(FncGlobal.sizeTextValue);
			lookupProject.setReturnColumn(0);
			lookupProject.setEnabled(false);
			lookupProject.addLookupListener(new LookupListener() {
				public void lookupPerformed(LookupEvent event) {
					Object[] data = ((_JLookup)event.getSource()).getDataSet();
					if(data != null){		
						proj_id 	= (String) data[0];		
						proj_name 	= (String) data[1];			
						tagProject.setTag(proj_name);
					}
				}
			});
			
		}
		{
			c.gridx = 1; 
			c.gridy = 1; 

			c.weightx = 1;
			c.weighty = 1; 

			c.ipadx = 10;
			
			lookupContractor = new _JLookup(null, "Payee", 2, 2);
			pansearchoptions.add(lookupContractor, c);
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
		
		
		//column 2
		{
			c.gridx = 2; 
			c.gridy = 0; 

			c.weightx = 3;
			c.weighty = 1; 

			c.ipadx = 10;
			
			tagProject = new _JTagLabel("[ ]");
			tagProject.setFont(FncGlobal.sizeTextValue);
			pansearchoptions.add(tagProject, c);
			tagProject.setEnabled(false);	
		}
		{
			c.gridx = 2; 
			c.gridy = 1; 

			c.weightx = 1;
			c.weighty = 1; 

			c.ipadx = 10;
			
			tagContractor = new _JTagLabel("[ ]");
			tagContractor.setFont(FncGlobal.sizeTextValue);
			pansearchoptions.add(tagContractor, c);
			tagContractor.setEnabled(false);	
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

	public static void enabledFields(boolean x){

		lblProject.setEnabled(x);	
		lblContractor.setEnabled(x);	

		lookupProject.setEnabled(x);	
		lookupContractor.setEnabled(x);	

		tagProject.setEnabled(x);	
		tagContractor.setEnabled(x);	
		//txtProject.setEnabled(x);
		//txtContractor.setEnabled(x);

		chkRemoveZero.setEnabled(x);	
		btnCancel.setEnabled(x);
		btnPreview.setEnabled(x);
	}

	public void refreshFields(){

		lookupProject.setValue("");	
		lookupContractor.setValue("");	

		tagProject.setTag("");
		tagContractor.setTag("");
		//txtProject.setText("");
		//txtContractor.setText("");
		
		proj_id 	= "";
		proj_name	= "";
		contractor_id 		= "";
		contractor_name		= "";
		
		prepared_by_id	= "";
		prepared_by_name= "";
	}

	public void initialize_comp(){		
		
		co_id 		= "02";	
		company		= "CENQHOMES DEVELOPMENT CORPORATION";			
		company_logo = RequestForPayment.sql_getCompanyLogo();
							
		txtCompany.setText(company);

		enabledFields(true);
		lookupProject.setLookupSQL(CheckStatusMonitoring.sql_project(co_id));	
		lookupContractor.setLookupSQL(_NoticeToProceed.Contractor());
		
		lookupCompany.setValue(co_id);
	}

}
