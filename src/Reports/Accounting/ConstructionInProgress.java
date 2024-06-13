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
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import Accounting.Collections.CheckStatusMonitoring;
import Accounting.Disbursements.RequestForPayment;
import Database.pgSelect;
import DateChooser._JDateChooser;
import Functions.FncGlobal;
import Functions.FncReport;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;

import components._JInternalFrame;

public class ConstructionInProgress extends _JInternalFrame implements ActionListener, AncestorListener, _GUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1162406788303915011L;

	static String title = "Construction In Progress Report";
	static Dimension frameSize = new Dimension(500, 275);
	static Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	static Cursor handCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);

	private JPanel pnlMain;
	private JPanel pnlNorth;
	private JPanel pnlNorthWest;
	private JPanel pnlNorthEast;
	
	private JLabel lblProject;
	private JLabel lblCompany;
	private JLabel lblContractor;
	private JLabel lblType;
	private JLabel lblAsOfDate;
	
	private _JLookup lookupProject;
	private _JLookup lookupCompany;
	private _JLookup lookupContractor;
	
	private JTextField txtProject;
	private JTextField txtCompany;
	private JTextField txtContractor;
	
	private JPanel pnlSouth;
	private JButton btnPreview;
	private JButton btnCancel;
	
	String company;
	String company_logo;	
	
	private _JDateChooser dateAsOf;	

	private JPanel pnlCenter2;
	private JPanel pnlCriteria2;

	private _JDateChooser dateFrom;

	private JComboBox cmbType;
	private JPanel pnlCenterNTPType;
	private JPanel pnlCenterNTPCmb;
	private JPanel pnlCriteria2a;
	private JPanel pnlCriteria2b;

	private String co_id = "";
	String proj_id 		= "";	
	String proj_name 	= "";

	public String ph_no;

	public JTextField txtphase;

	private JLabel lblphase;

	protected String phase;

	private static _JLookup lookuphase;
	
	public ConstructionInProgress() {
		super(title, false, true, false, true);
		initGUI();
	}

	public ConstructionInProgress(String title) {
		super(title);
		initGUI();
	}

	public ConstructionInProgress(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		//this.setPreferredSize(new java.awt.Dimension(563, 275));
		this.setPreferredSize(new java.awt.Dimension(563, 300));
		this.setLayout(new BorderLayout());
		this.addAncestorListener(this);
		{
			pnlMain = new JPanel(new BorderLayout(5, 5));
			getContentPane().add(pnlMain, BorderLayout.WEST);
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			pnlMain.setPreferredSize(new java.awt.Dimension(557, 300));
			{
				pnlNorth = new JPanel(new BorderLayout(5, 5));
				pnlMain.add(pnlNorth, BorderLayout.NORTH);
				pnlNorth.setPreferredSize(new java.awt.Dimension(547, 200));//200
				pnlNorth.setBorder(components.JTBorderFactory.createTitleBorder("Company Details"));// XXX
				{
					pnlNorthWest = new JPanel(new GridLayout(4,1, 5, 5));
					pnlNorth.add(pnlNorthWest, BorderLayout.WEST);
					pnlNorthWest.setPreferredSize(new java.awt.Dimension(162, 160));
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
									
									KEYBOARD_MANAGER.focusNextComponent();
									btnCancel.setEnabled(true);
								}
							}
						});
					}
					{
						lblProject = new JLabel("Project", JLabel.TRAILING);
						pnlNorthWest.add(lblProject);
					}
					{
						lookupProject = new _JLookup(null, "Project");
						pnlNorthWest.add(lookupProject);
						lookupProject.setReturnColumn(0);
						lookupProject.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){
									String name = (String) data[1];						
									txtProject.setText(name);												
									btnCancel.setEnabled(true);
									lookuphase.setEnabled(true);
									txtphase.setEnabled(true);
									lblphase.setEnabled(true);	
									lookuphase.setValue("");
									txtphase.setText("");
									lookuphase.setLookupSQL(getSubproject(lookupCompany.getValue(), lookupProject.getValue()));
									
									KEYBOARD_MANAGER.focusNextComponent();
								}
							}
						});
					}
					{
						lblphase = new JLabel("Phase", JLabel.TRAILING);
						pnlNorthWest.add(lblphase);
					}
					{
						lookuphase = new _JLookup(null,"Phase");
						pnlNorthWest.add(lookuphase);
						//lookuphase.setReturnColumn(1);
						lookuphase.setEnabled(false);
						lookuphase.addLookupListener(new LookupListener() {

							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){
									ph_no		= (String) data[0];
									phase = (String)data[1];
									lookuphase.setValue(phase);
									txtphase.setText(ph_no);
									lblContractor.setEnabled(true);	
									lookupContractor.setText("");
									lookupContractor.setEnabled(true);
									txtContractor.setEnabled(true);
									txtContractor.setText("");
									
								}
							}
						});
					}
					{
						lblContractor = new JLabel("Contractor", JLabel.TRAILING);
						pnlNorthWest.add(lblContractor);
					}
					{
						lookupContractor = new _JLookup(null, "Contractor");
						pnlNorthWest.add(lookupContractor);
						lookupContractor.setReturnColumn(0);
						lookupContractor.setFilterCardName(true);
						lookupContractor.setLookupSQL(sql_contractor());	
						lookupContractor.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){
									String name = (String) data[2];						
									txtContractor.setText(name);												
									btnCancel.setEnabled(true);
								}
							}
						});
					}
				}
				pnlNorthEast = new JPanel(new GridLayout(4, 1, 5, 5));
				pnlNorth.add(pnlNorthEast, BorderLayout.CENTER);
				pnlNorthEast.setPreferredSize(new java.awt.Dimension(280, 159));//159
				{
					txtCompany = new JTextField();
					pnlNorthEast.add(txtCompany, BorderLayout.CENTER);
					txtCompany.setEditable(false);
				}
				{
					txtProject = new JTextField();
					pnlNorthEast.add(txtProject, BorderLayout.CENTER);
					txtProject.setEditable(false);
				}
				{
					txtphase = new JTextField();
					pnlNorthEast.add(txtphase, BorderLayout.CENTER);
					txtphase.setEditable(false);
				}
				{
					txtContractor = new JTextField();
					pnlNorthEast.add(txtContractor, BorderLayout.CENTER);
					txtContractor.setEditable(false);
				}
				
				pnlCenter2 = new JPanel(new BorderLayout(5, 5));
				pnlNorth.add(pnlCenter2, BorderLayout.SOUTH);
				pnlCenter2.setPreferredSize(new java.awt.Dimension(499, 60));
				pnlCenter2.setBorder(components.JTBorderFactory.createTitleBorder("NTP Details"));
				
				pnlCenterNTPType = new JPanel(new GridLayout(1, 1, 5, 5));
				pnlCenter2.add(pnlCenterNTPType, BorderLayout.WEST);	
				pnlCenterNTPType.setPreferredSize(new java.awt.Dimension(69, 60));
				pnlCenterNTPType.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));		
				
				{
					lblType = new JLabel("NTP Type", JLabel.TRAILING);
					pnlCenterNTPType.add(lblType, BorderLayout.CENTER);
					lblType.setEnabled(true);							
					lblType.setPreferredSize(new java.awt.Dimension(71, 60));
				}
				
				pnlCenterNTPCmb = new JPanel(new GridLayout(1, 1, 5, 5));
				pnlCenter2.add(pnlCenterNTPCmb, BorderLayout.CENTER);	
				pnlCenterNTPCmb.setPreferredSize(new java.awt.Dimension(249, 60));
				pnlCenterNTPCmb.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
				
				{
					String status[] = {"ALL","LAND DEV","HOUSING","FACILITIES","SPCL PROJ","OSP","CSE","MERALCO","REPAIR"};					
					cmbType = new JComboBox(status);
					pnlCenterNTPCmb.add(cmbType);
					cmbType.setSelectedItem(null);
					cmbType.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,13));
					cmbType.setBounds(537, 15, 190, 21);	
					cmbType.setEnabled(true);
					cmbType.setPreferredSize(new java.awt.Dimension(217, 60));
					cmbType.setSelectedIndex(0);	
				}				
				{
					pnlCriteria2 = new JPanel(new BorderLayout(5, 5));
					pnlCenter2.add(pnlCriteria2, BorderLayout.EAST);	
					pnlCriteria2.setPreferredSize(new java.awt.Dimension(270, 60));
					
					pnlCriteria2a = new JPanel(new GridLayout(1, 4, 3, 3));
					pnlCriteria2.add(pnlCriteria2a, BorderLayout.WEST);	
					pnlCriteria2a.setPreferredSize(new java.awt.Dimension(78, 60));

					{
								
						lblAsOfDate = new JLabel("Date To   ", JLabel.TRAILING);
						pnlCriteria2a.add(lblAsOfDate);
						lblAsOfDate.setEnabled(true);	
						lblAsOfDate.setPreferredSize(new java.awt.Dimension(79, 60));
					}
					
					pnlCriteria2b = new JPanel(new GridLayout(1, 4, 3, 3));
					pnlCriteria2.add(pnlCriteria2b, BorderLayout.CENTER);	
					pnlCriteria2b.setPreferredSize(new java.awt.Dimension(270, 60));
					
					{
						dateAsOf = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
						pnlCriteria2b.add(dateAsOf, BorderLayout.EAST);
						dateAsOf.setBounds(485, 7, 125, 21);
						dateAsOf.setDate(null);
						dateAsOf.setEnabled(true);
						dateAsOf.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
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
		this.setBounds(0, 0, 563, 275);  //174
		
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
		
		String ntp_type = (String) cmbType.getSelectedItem();
		String ntp	    = "";
		if (ntp_type.equals("ALL")) {ntp="All";}
		else if (ntp_type.equals("LAND DEV")) {ntp="01";}
		else if (ntp_type.equals("HOUSING")) {ntp="02";}
		//else if (ntp_type.equals("FACILITIES")) {ntp="03";}
		else if (ntp_type.equals("FACILITIES")) {ntp="04";}
		//else if (ntp_type.equals("SPCL PROJ")) {ntp="04";}
		else if (ntp_type.equals("SPCL PROJ")) {ntp="05";}
		//else if (ntp_type.equals("OSP")) {ntp="05";}
		else if (ntp_type.equals("OSP")) {ntp="07";}
		//else if (ntp_type.equals("CSE")) {ntp="06";}
		else if (ntp_type.equals("CSE")) {ntp="08";}
		//else if (ntp_type.equals("MERALCO")) {ntp="07";}
		else if (ntp_type.equals("MERALCO")) {ntp="06";}
		//else if (ntp_type.equals("REPAIR")) {ntp="08";}
		else if (ntp_type.equals("REPAIR")) {ntp="03";}
		
		if(e.getActionCommand().equals("Preview")&& ntp_type.equals("HOUSING")){		
			String criteria = "CIP - Housing";		
			String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());		
		
			Map<String, Object> mapParameters = new HashMap<String, Object>();
			mapParameters.put("company", company);
			mapParameters.put("proj_id", lookupProject.getValue());
			mapParameters.put("project", txtProject.getText());
			mapParameters.put("co_id", lookupCompany.getValue());
			mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
			mapParameters.put("prepared_by", UserInfo.Alias);
			mapParameters.put("date_to", dateAsOf.getDate());
			mapParameters.put("contractor", lookupContractor.getValue());
			mapParameters.put("contractor_name", txtContractor.getText());
			mapParameters.put("phase", lookuphase.getValue());
			System.out.println("mapParameters_phase: "+phase);

			FncReport.generateReport("/Reports/rptCIP_housing.jasper", reportTitle, txtProject.getText(), mapParameters);
			
			String criteria2 = "CIP - Others";		
			String reportTitle2 = String.format("%s (%s)", title.replace(" Report", ""), criteria2.toUpperCase());		
		
			Map<String, Object> mapParameters2 = new HashMap<String, Object>();
			mapParameters2.put("company", company);
			mapParameters2.put("proj_id", lookupProject.getValue());
			mapParameters2.put("project", txtProject.getText());
			mapParameters2.put("co_id", lookupCompany.getValue());
			mapParameters2.put("ntp", ntp);
			mapParameters2.put("ntp_type", ntp_type);
			mapParameters2.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
			mapParameters2.put("prepared_by", UserInfo.Alias);
			mapParameters2.put("date_to", dateAsOf.getDate());	
			mapParameters2.put("contractor", lookupContractor.getValue());
			mapParameters2.put("contractor_name", txtContractor.getText());
			mapParameters2.put("phase", lookuphase.getValue());
			System.out.println("mapParameters2_phase: "+phase);
			
			FncReport.generateReport("/Reports/rptCIP_others.jasper", reportTitle2, txtProject.getText(), mapParameters2);
		}
		
		else if(e.getActionCommand().equals("Preview") && !ntp_type.equals("Housing") ){	
			
			System.out.println("Not Housing");
			System.out.printf("Display value of company_logo: %s%n", company_logo);
			
			String criteria2 = "CIP - Others";		
			String reportTitle2 = String.format("%s (%s)", title.replace(" Report", ""), criteria2.toUpperCase());		
		
			Map<String, Object> mapParameters2 = new HashMap<String, Object>();
			mapParameters2.put("company", company);
			mapParameters2.put("proj_id", lookupProject.getValue());
			mapParameters2.put("project", txtProject.getText());
			mapParameters2.put("co_id", lookupCompany.getValue());
			mapParameters2.put("ntp", ntp);
			mapParameters2.put("ntp_type", ntp_type);
			mapParameters2.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
			mapParameters2.put("prepared_by", UserInfo.Alias);
			mapParameters2.put("date_to", dateAsOf.getDate());	
			mapParameters2.put("contractor", lookupContractor.getValue());
			mapParameters2.put("contractor_name", txtContractor.getText());
			mapParameters2.put("phase", lookuphase.getValue());
			System.out.println("Not Housing");
			System.out.println("ntp_type :"+ntp_type);
			System.out.println("proj_id :"+lookupProject.getValue());
			System.out.println("ntp :"+ntp);
			System.out.println("contractor :"+lookupContractor.getValue());
			System.out.println("date_to :"+dateAsOf.getDate());
			System.out.println("phase :"+lookuphase.getValue());
			FncReport.generateReport("/Reports/rptCIP_others.jasper", reportTitle2, txtProject.getText(), mapParameters2);
		
			
		}
		else if(e.getActionCommand().equals("Preview") && ntp_type.equals("ALL") ){	
			
			String criteria2 = "All";		
			String reportTitle2 = String.format("%s (%s)", title.replace(" Report", ""), criteria2.toUpperCase());		
		
			Map<String, Object> mapParameters2 = new HashMap<String, Object>();
			mapParameters2.put("company", company);
			mapParameters2.put("proj_id", lookupProject.getValue());
			mapParameters2.put("project", txtProject.getText());
			mapParameters2.put("co_id", lookupCompany.getValue());
			mapParameters2.put("ntp", ntp);
			mapParameters2.put("ntp_type", ntp_type);
			mapParameters2.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
			mapParameters2.put("prepared_by", UserInfo.Alias);
			mapParameters2.put("date_to", dateAsOf.getDate());	
			mapParameters2.put("contractor", lookupContractor.getValue());
			mapParameters2.put("contractor_name", txtContractor.getText());
			FncReport.generateReport("/Reports/rptCIP_others.jasper", reportTitle2, txtProject.getText(), mapParameters2);
		
			
		}
		
		if(e.getActionCommand().equals("Cancel")){	
			lookupCompany.setText("");
			txtCompany.setText("");
			lblProject.setEnabled(false);	
			lookupProject.setEnabled(false);	
			lookupProject.setText("");
			txtProject.setEnabled(false);		
			txtProject.setText("");
			lblContractor.setEnabled(false);	
			lookupContractor.setEnabled(false);		
			lookupContractor.setText("");
			txtContractor.setEnabled(false);		
			txtContractor.setText("");
			lookuphase.setValue("");
			txtphase.setText("");
			lookuphase.setEnabled(false);
			txtphase.setEnabled(false);
			//dateFrom.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
			dateAsOf.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
		}
		
	}
	
	public void initialize_comp(){		
		
		co_id 		= "02";	
		company		= "CENQHOMES DEVELOPMENT CORPORATION";			
						
		txtCompany.setText(company);
		//proj_id 	= "015";	
		//proj_name	= "TERRAVERDE RESIDENCES";	
		lookupProject.setLookupSQL(CheckStatusMonitoring.sql_project(co_id));	
		txtProject.setText(proj_name);
		//lookupProject.setValue(proj_id);
		
		KEYBOARD_MANAGER.focusNextComponent();
		btnCancel.setEnabled(true);
		lookupCompany.setValue(co_id);
		company_logo = sql_getCompanyLogo(lookupCompany.getValue());	
		lookuphase.setValue("");
		
	}
	
	private String sql_getCompanyLogo(String co_id) {

		String a = "";

		String SQL = 
				"select company_logo from mf_company \n" + 
						"where co_id = '"+co_id+"' " ;

		System.out.printf("SQL #1: sql_getCompanyLogo", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			if((String) db.getResult()[0][0]==null||db.getResult()[0][0].equals("null")) {a = "";}
			else {a = (String) db.getResult()[0][0]; }

		}else{
			a = "";
		}

		return a;
	}
	
	public static String sql_contractor() {
		String SQL = "select entity_id as \"Entity ID\", entity_kind as \"Kind\", trim(entity_name) as \"Entity Name\", vat_registered as \"VAT\" from rf_entity where status_id = 'A' and entity_name != '' order by entity_name" ;
		return SQL;
	}
	public String getSubproject(String co_id, String proj_id){
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
			"left join mf_sub_project c on a.sub_proj_id = c.sub_proj_id and c.status_id = 'A' \r\n" +//ADDED STATUS ID BY LESTER DCRF 2718 
			"where co_id = '"+co_id+"' " +
			"and a.proj_id = '"+proj_id+"' " +
			"and a.sub_proj_id != ''   " +
			"and a.phase != ''\n"+
			"order by a.sub_proj_id" ;		
		
		System.out.println("co_id: "+co_id);
		System.out.println("proj_id: "+proj_id);
		System.out.println("getSubproject: "+sql);
		return sql;

	}
}
