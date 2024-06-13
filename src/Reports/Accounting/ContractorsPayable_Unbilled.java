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
import Functions.FncFocusTraversalPolicy;
import Functions.FncGlobal;
import Functions.FncReport;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import Projects.BiddingandAwarding._NoticeToProceed;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JTagLabel;

public class ContractorsPayable_Unbilled extends _JInternalFrame implements ActionListener, AncestorListener, _GUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1162406788303915011L;

	static String title = "Contractor's Unbilled Progress Billing";
	//static Dimension frameSize = new Dimension(500, 250);
	static Dimension frameSize = new Dimension(624, 250);
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

	private JLabel lblCompany;
	private static JLabel lblProject;
	private static JLabel lblContractor;

	private static _JTagLabel tagProject;
	private static _JTagLabel tagContractor;

	public static _JLookup lookupCompany;
	private static _JLookup lookupProject;
	private static _JLookup lookupContractor;

	public static JTextField txtCompany;

	private static JButton btnPreview;
	private static JButton btnCancel;

	String company;
	public static String company_logo;	
	
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");

	public String co_id 		= "";
	public String proj_id 		= "";
	public String proj_name		= "";
	public String contractor_id 	= "";
	public String contractor_name	= "";
	public String contract_no 		= "";
	public String prepared_by_id	= "";
	public String prepared_by_name	= "";
	public String remove_zer_bal	= "";

	private static JCheckBox chkRemoveZero;

	public ContractorsPayable_Unbilled() {
		super(title, true, true, true, true);
		initGUI();
	}

	public ContractorsPayable_Unbilled(String title) {
		super(title);
		initGUI();
	}

	public ContractorsPayable_Unbilled(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		//this.setPreferredSize(new java.awt.Dimension(624, 228));
		this.setPreferredSize(new java.awt.Dimension(frameSize));
		this.setLayout(new BorderLayout());
		this.addAncestorListener(this);
		{
			pnlMain = new JPanel(new GridBagLayout());
			getContentPane().add(pnlMain, BorderLayout.CENTER);
			pnlMain.setBorder(new EmptyBorder(5, 5, 5, 5));
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
					panel.setBorder(JTBorderFactory.createTitleBorder("Company", FncGlobal.sizeLabel));
					panel.add(getCompany(), BorderLayout.CENTER); 

					pnlMain.add(panel, c); 
				}
				{
					c.gridx = 0; 
					c.gridy = 1; 
					c.weightx = 1; 
					c.weighty = 3; 
					c.ipady = 70; 

					JPanel panel = new JPanel(new BorderLayout());
					panel.setBorder(JTBorderFactory.createTitleBorder("Search Options", FncGlobal.sizeLabel));
					panel.add(getsearchoptions(), BorderLayout.CENTER);
					pnlMain.add(panel, c); 
				}
				{
					c.gridx = 0; 
					c.gridy = 2; 
					c.weightx = 1; 
					c.weighty = 0.5;
					c.ipady = 15; 

					JPanel panel = new JPanel(new BorderLayout());
					//panel.setBorder(JTBorderFactory.createTitleBorder("Button",FncGlobal.sizeLabel));
					panel.add(getbutton(), BorderLayout.CENTER);
					pnlMain.add(panel, c); 
				}
			}
		}
		
		/*{
			pnlMain = new JPanel(new BorderLayout(5, 5));
			getContentPane().add(pnlMain, BorderLayout.WEST);
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			pnlMain.setPreferredSize(new java.awt.Dimension(621, 359));

			{
				pnlNorth = new JPanel(new BorderLayout(5, 5));
				pnlMain.add(pnlNorth, BorderLayout.NORTH);
				pnlNorth.setPreferredSize(new java.awt.Dimension(611, 55));

				{
					pnlNorth_comp = new JPanel(new BorderLayout(5, 5));
					pnlNorth.add(pnlNorth_comp, BorderLayout.NORTH);
					pnlNorth_comp.setPreferredSize(new java.awt.Dimension(547, 63));
					pnlNorth_comp.setBorder(components.JTBorderFactory.createTitleBorder("Company"));// XXX

					{
						pnlNorthWest = new JPanel(new GridLayout(1,1, 5, 5));
						pnlNorth_comp.add(pnlNorthWest, BorderLayout.WEST);
						pnlNorthWest.setPreferredSize(new java.awt.Dimension(142, 100));
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
						}
					}
					pnlNorthEast = new JPanel(new GridLayout(1, 1, 5, 5));
					pnlNorth_comp.add(pnlNorthEast, BorderLayout.CENTER);
					pnlNorthEast.setPreferredSize(new java.awt.Dimension(300, 159));
					{
						txtCompany = new JTextField();
						pnlNorthEast.add(txtCompany, BorderLayout.CENTER);
						txtCompany.setEditable(false);
					}
				}		
			}	
			{
				pnlCenter =  new JPanel(new BorderLayout(5, 5));
				pnlMain.add(pnlCenter, BorderLayout.CENTER);
				pnlCenter.setPreferredSize(new java.awt.Dimension(499, 50));
				pnlCenter.setBorder(components.JTBorderFactory.createTitleBorder("Search Options"));

				{		
					pnlCenter_a = new JPanel(new GridLayout(2, 1, 0, 5));
					pnlCenter.add(pnlCenter_a, BorderLayout.WEST);	
					pnlCenter_a.setPreferredSize(new java.awt.Dimension(95, 185));

					{
						lblProject = new JLabel("Project", JLabel.TRAILING);
						pnlCenter_a.add(lblProject);
						lblProject.setEnabled(false);	
					}	
					{
						lblContractor = new JLabel("Contractor", JLabel.TRAILING);
						pnlCenter_a.add(lblContractor);
						lblContractor.setEnabled(false);	
					}		

					pnlCenter_2 = new JPanel(new BorderLayout(5,0));
					pnlCenter.add(pnlCenter_2, BorderLayout.CENTER);
					pnlCenter_2.setPreferredSize(new java.awt.Dimension(203, 118));
					pnlCenter_2.setBorder(BorderFactory.createEmptyBorder(0,5,0,0));

					pnlDRFDtl_2a = new JPanel(new GridLayout(2, 1, 0, 5));
					pnlCenter_2.add(pnlDRFDtl_2a, BorderLayout.WEST);
					pnlDRFDtl_2a.setPreferredSize(new java.awt.Dimension(100, 185));
					pnlDRFDtl_2a.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

					{
						lookupProject = new _JLookup(null, "Request Type", 2, 2);
						pnlDRFDtl_2a.add(lookupProject);
						lookupProject.setBounds(20, 27, 20, 25);
						lookupProject.setReturnColumn(0);
						lookupProject.setEnabled(false);
						//lookupProject.setEditable(false);
						lookupProject.setPreferredSize(new java.awt.Dimension(157, 22));
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
						lookupContractor = new _JLookup(null, "Payee", 2, 2);
						pnlDRFDtl_2a.add(lookupContractor);
						lookupContractor.setBounds(20, 27, 20, 25);
						lookupContractor.setReturnColumn(1);
						lookupContractor.setEnabled(false);	
						//lookupContractor.setEditable(false);
						lookupContractor.setPreferredSize(new java.awt.Dimension(157, 22));
						lookupContractor.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){	
									contractor_id 	= (String) data[0];		
									contractor_name = (String) data[1];	
									lookupContractor.setValue(String.valueOf(contractor_id));
									tagContractor.setTag(contractor_name);
								}
							}
						});	
					}

					pnlDRFDtl_2b = new JPanel(new GridLayout(2, 1, 0, 5));
					pnlCenter_2.add(pnlDRFDtl_2b, BorderLayout.CENTER);
					pnlDRFDtl_2b.setPreferredSize(new java.awt.Dimension(140, 118));
					pnlDRFDtl_2b.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

					{
						tagProject = new _JTagLabel("[ ]");
						pnlDRFDtl_2b.add(tagProject);
						tagProject.setBounds(209, 27, 700, 22);
						tagProject.setEnabled(false);	
						tagProject.setPreferredSize(new java.awt.Dimension(27, 33));
					}	
					{
						tagContractor = new _JTagLabel("[ ]");
						pnlDRFDtl_2b.add(tagContractor);
						tagContractor.setBounds(209, 27, 700, 22);
						tagContractor.setEnabled(false);	
						tagContractor.setPreferredSize(new java.awt.Dimension(27, 33));
					}	

				}
			}

			{				
				pnlSouth = new JPanel(new GridLayout(1, 2,5, 5));
				pnlMain.add(pnlSouth, BorderLayout.SOUTH);
				pnlSouth.setPreferredSize(new Dimension(300, 30));

				{
					chkRemoveZero = new JCheckBox("Remove zero balance? ");
					pnlSouth.add(chkRemoveZero);
					chkRemoveZero.setEnabled(false);	
					chkRemoveZero.setHorizontalAlignment(JTextField.CENTER);	
					chkRemoveZero.setPreferredSize(new java.awt.Dimension(383, 26));
				}
				{
					btnPreview = new JButton("Preview");
					pnlSouth.add(btnPreview);
					btnPreview.setAlignmentX(0.5f);
					btnPreview.setEnabled(false);
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
		}*/

		this.setFocusTraversalPolicy(new FncFocusTraversalPolicy(lookupCompany, lookupProject,	btnPreview));
		//this.setBounds(10, 10, 624, 228);  //174
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
			String criteria = "";		
			String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());

			if (proj_id.equals("")) {proj_name = "All";} else {}
			if (contractor_id.equals("")) {contractor_name = "All";} else {}
			if (chkRemoveZero.isSelected()==true) {remove_zer_bal = "yes";} else {remove_zer_bal = "no";}			
			
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

			FncReport.generateReport("/Reports/rptContractorUnbilledBilling.jasper", reportTitle, "", mapParameters);
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

		chkRemoveZero.setEnabled(x);	
		btnCancel.setEnabled(x);
		btnPreview.setEnabled(x);
	}

	public void refreshFields(){

		lookupProject.setValue("");	
		lookupContractor.setValue("");	

		tagProject.setTag("");
		tagContractor.setTag("");
		
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

	//lookup

}
