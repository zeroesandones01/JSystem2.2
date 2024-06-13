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
import java.text.DateFormat;
import java.text.ParseException;
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
import Functions.FncReport;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import Projects.BiddingandAwarding._NoticeToProceed;

import components._JInternalFrame;
import components._JTagLabel;

public class ContractorsChangeOrder extends _JInternalFrame implements ActionListener, AncestorListener, _GUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1162406788303915011L;

	static String title = "Contractor's Change Order Report";
	static Dimension frameSize = new Dimension(500, 250);
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
	private static JLabel lblContract_No;
	private static JLabel lblContractor;

	private static _JTagLabel tagProject;
	private static _JTagLabel tagContract_No;
	private static _JTagLabel tagContractor;

	public static _JLookup lookupCompany;
	private static _JLookup lookupProject;
	private static _JLookup lookupContract_No;
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
	public String ntp_no 		= "";
	public String contract_no 	= "";
	public String contractor_id = "";
	public String contractor 	= "";
	public String contractor_name 	= "";
	public String prepared_by_id	= "";
	public String prepared_by_name	= "";
	public String remove_zer_bal	= "";

	private static JCheckBox chkRemoveZero;

	public ContractorsChangeOrder() {
		super(title, false, true, false, true);
		initGUI();
	}

	public ContractorsChangeOrder(String title) {
		super(title);
		initGUI();
	}

	public ContractorsChangeOrder(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		this.setPreferredSize(new java.awt.Dimension(624, 228));
		this.setLayout(new BorderLayout());
		this.addAncestorListener(this);
		{
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
										lookupContract_No.setLookupSQL(getLookupContractNo());
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
					pnlCenter_a = new JPanel(new GridLayout(3, 1, 0, 5));
					pnlCenter.add(pnlCenter_a, BorderLayout.WEST);	
					pnlCenter_a.setPreferredSize(new java.awt.Dimension(95, 185));

					{
						lblProject = new JLabel("Project", JLabel.TRAILING);
						pnlCenter_a.add(lblProject);
						lblProject.setEnabled(false);	
					}	
					{
						lblContract_No = new JLabel("Contract No.", JLabel.TRAILING);
						pnlCenter_a.add(lblContract_No);
						lblContract_No.setEnabled(false);	
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

					pnlDRFDtl_2a = new JPanel(new GridLayout(3, 1, 0, 5));
					pnlCenter_2.add(pnlDRFDtl_2a, BorderLayout.WEST);
					pnlDRFDtl_2a.setPreferredSize(new java.awt.Dimension(200, 185));
					pnlDRFDtl_2a.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

					{
						lookupProject = new _JLookup(null, "Project", 2, 2);
						pnlDRFDtl_2a.add(lookupProject);
						lookupProject.setBounds(20, 27, 60, 25);
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
								lookupContract_No.setLookupSQL(getLookupContractNo());
							}
						});	
					}	
					{
						lookupContract_No = new _JLookup(null, "Contract No", 2, 2);
						pnlDRFDtl_2a.add(lookupContract_No);
						lookupContract_No.setBounds(20, 27, 60, 25);
						lookupContract_No.setReturnColumn(0);
						lookupContract_No.setEnabled(false);	
						lookupContract_No.setPreferredSize(new java.awt.Dimension(157, 22));
						lookupContract_No.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){	
									contract_no=(String) data[0];	
									ntp_no 	= (String) data[1];	
									contractor_id = (String) data[2];
									contractor_name = (String) data[3];	


									lookupContract_No.setValue(String.valueOf(contract_no));
									tagContract_No.setTag(ntp_no);

									lookupContractor.setValue(String.valueOf(contractor_id));
									tagContractor.setTag(contractor_name);

								}else {
									lookupContractor.setLookupSQL(_NoticeToProceed.Contractor());

								}

							}
						});	
					}
					{
						lookupContractor = new _JLookup(null, "Contractor", 2, 2);
						pnlDRFDtl_2a.add(lookupContractor);
						lookupContractor.setBounds(20, 27, 60, 25);
						lookupContractor.setReturnColumn(1);
						lookupContractor.setEnabled(false);	
						lookupContractor.setPreferredSize(new java.awt.Dimension(157, 22));
						lookupContractor.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){	
									contractor_id=(String) data[0];	
									contractor=(String) data[1];	
									lookupContractor.setValue(String.valueOf(contractor_id));
									tagContractor.setTag(contractor);
								}
							}
						});	
					}

					pnlDRFDtl_2b = new JPanel(new GridLayout(3, 1, 0, 5));
					pnlCenter_2.add(pnlDRFDtl_2b, BorderLayout.CENTER);
					pnlDRFDtl_2b.setPreferredSize(new java.awt.Dimension(140, 118));
					pnlDRFDtl_2b.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

					{
						tagProject = new _JTagLabel("[ ]");
						pnlDRFDtl_2b.add(tagProject);
						tagProject.setBounds(250, 27, 700, 22);
						tagProject.setEnabled(false);	
						tagProject.setPreferredSize(new java.awt.Dimension(27, 33));
					}	
					{
						tagContract_No = new _JTagLabel("[ ]");
						pnlDRFDtl_2b.add(tagContract_No);
						tagContract_No.setBounds(250, 27, 700, 22);
						tagContract_No.setEnabled(false);	
						tagContract_No.setPreferredSize(new java.awt.Dimension(27, 33));
					}
					{
						tagContractor = new _JTagLabel("[ ]");
						pnlDRFDtl_2b.add(tagContractor);
						tagContractor.setBounds(250, 27, 700, 22);
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
		}

		this.setFocusTraversalPolicy(new FncFocusTraversalPolicy(lookupCompany, lookupProject,	btnPreview));
		this.setBounds(0, 0, 624, 260);  //174
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
			

			if (proj_id.equals("")) {
				proj_name = "All";
			}else {
				
			}

			if (contractor.equals("")) {
				System.out.println("rptContractorsChangeOrder");
				System.out.println("co_id: "+co_id);
				System.out.println("project: "+ proj_id);
				System.out.println("ntp_no: "+ ntp_no);
				System.out.println("contract_no: "+lookupContract_No.getValue());
				System.out.println("contractor: "+ contractor_id);
				
				contract_no = lookupContract_No.getValue();
				String criteria = "CONTRACT NO.";		
				String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());

				Map<String, Object> mapParameters = new HashMap<String, Object>();
				mapParameters.put("co_id", co_id);
				mapParameters.put("proj_id", proj_id);
				mapParameters.put("company", company);
				mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
				mapParameters.put("prepared_by", UserInfo.Alias);
				mapParameters.put("prepared_name", UserInfo.FullName);	
				mapParameters.put("project", proj_name);	
				mapParameters.put("ntp_no", ntp_no);
				mapParameters.put("contract_no", contract_no);
				mapParameters.put("contractor_id", contractor_id);
				//mapParameters.put("contractor_name", contractor);	

				FncReport.generateReport("/Reports/rptContractorsChangeOrder.jasper", reportTitle, "", mapParameters);

			} else if (contract_no.equals("") ) {
				System.out.println("rptContractorsChangeOrder_CT");
				System.out.println("co_id: "+co_id);
				System.out.println("project: "+ proj_id);
				System.out.println("ntp_no: "+ ntp_no);
				System.out.println("contract_no: "+lookupContract_No.getValue());
				System.out.println("contractor: "+ contractor_id);

				contractor_id = lookupContractor.getValue();
				String criteria = "CONTRACTOR";		
				String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());

				Map<String, Object> mapParameters = new HashMap<String, Object>();
				mapParameters.put("co_id", co_id);
				mapParameters.put("proj_id", proj_id);
				mapParameters.put("company", company);
				mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
				mapParameters.put("prepared_by", UserInfo.Alias);
				mapParameters.put("prepared_name", UserInfo.FullName);	
				mapParameters.put("project", proj_name);	
				mapParameters.put("ntp_no", ntp_no);
				//mapParameters.put("contract_no", contract_no);
				mapParameters.put("contractor_id", contractor_id);	


				FncReport.generateReport("/Reports/rptContractorsChangeOrder_CT.jasper", reportTitle, "", mapParameters);
			} else {
				contract_no = lookupContract_No.getValue();
				contractor_id = lookupContractor.getValue();
				ntp_no = tagContract_No.getText();

				String criteria = "";		
				String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());

				Map<String, Object> mapParameters = new HashMap<String, Object>();
				mapParameters.put("co_id", co_id);
				mapParameters.put("proj_id", proj_id);
				mapParameters.put("company", company);
				mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
				mapParameters.put("prepared_by", UserInfo.Alias);
				mapParameters.put("prepared_name", UserInfo.FullName);	
				mapParameters.put("project", proj_name);	
				mapParameters.put("ntp_no", ntp_no);
				mapParameters.put("contract_no", contract_no);
				mapParameters.put("contractor_name", contractor);	

				FncReport.generateReport("/Reports/rptContractorsChangeOrder.jasper", reportTitle, "", mapParameters);
			}
		}
		if(e.getActionCommand().equals("Cancel")){	
			refreshFields();
		}

	}

	public static void enabledFields(boolean x){

		lblProject.setEnabled(x);	
		lblContract_No.setEnabled(x);	
		lblContractor.setEnabled(x);	


		lookupProject.setEnabled(x);
		lookupContract_No.setEnabled(x);	
		lookupContractor.setEnabled(x);	

		tagProject.setEnabled(x);
		tagContract_No.setEnabled(x);	
		tagContractor.setEnabled(x);	

		chkRemoveZero.setEnabled(x);	
		btnCancel.setEnabled(x);
		btnPreview.setEnabled(x);
	}

	public void refreshFields(){

		lookupProject.setValue("");
		lookupContract_No.setValue("");	
		lookupContractor.setValue("");	

		tagProject.setTag("");
		tagContract_No.setTag("");
		tagContractor.setTag("");

		proj_id 	= "";
		proj_name	= "";
		contract_no = "";
		ntp_no		= "";
		contractor_id = "";
		contractor  = "";

		prepared_by_id	= "";
		prepared_by_name= "";
	}

	public void initialize_comp(){		

		co_id 		= "02";	
		company		= "CENQHOMES DEVELOPMENT CORPORATION";			
		company_logo = RequestForPayment.sql_getCompanyLogo();						
		txtCompany.setText(company);

		enabledFields(true);


		lookupCompany.setValue(co_id);
		lookupProject.setLookupSQL(CheckStatusMonitoring.sql_project(co_id));
		lookupContract_No.setLookupSQL(getLookupContractNo());
		lookupContractor.setLookupSQL(_NoticeToProceed.Contractor());

	}

	//select, lookup and get statements	
	public static String getLookupContractNo(){    
		return 
				"select \r\n" + 
				"a.contract_no as Contract_No, \r\n" + 
				"a.ntp_no as NTP_No, \r\n" + 
				"a.entity_id as Contractor_ID, \r\n" + 
				"b.entity_name as contractor_name," +
				"a.proj_id, \r\n" + 
				"c.proj_name as Project" +
				//"(case when a.with_surety_bond = false then 'YES' else 'NO' end) as with_surety_bond  \r\n" + 
				" \r\n" + 

			"from co_ntp_header a\r\n" + 
			"left join rf_entity b on a.entity_id = b.entity_id\r\n" + 
			"left join mf_project c on a.proj_id = c.proj_id\r\n" + 
			"order by ntp_no::integer desc \r\n"  ;

	}

}
