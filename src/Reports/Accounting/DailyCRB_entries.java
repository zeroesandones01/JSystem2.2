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
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import Accounting.Collections.CheckStatusMonitoring;
import Accounting.Disbursements.RequestForPayment;
import Database.pgSelect;
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

public class DailyCRB_entries extends _JInternalFrame implements ActionListener, AncestorListener, _GUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1162406788303915011L;

	static String title = "CRB (Daily Entries)";
	static Dimension frameSize = new Dimension(500, 250);
	static Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	static Cursor handCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);

	private JPanel pnlMain;
	private JPanel pnlNorth;
	private JPanel pnlNorthWest;
	private JPanel pnlNorthEast;
	private JPanel pnlCenter2;
	private JPanel pnlCenter2a;
	private JPanel pnlCenter2b;
	private JPanel pnlSouth;

	private JLabel lblProject;
	private JLabel lblCompany;
	private JLabel lblDateFrom;
	private JLabel lblBranch;

	private _JLookup lookupProject;
	private _JLookup lookupCompany;
	private _JLookup lookupBranch;

	private JTextField txtProject;
	private JTextField txtCompany;
	private JTextField txtBranch;	
	
	private JButton btnPreview;
	private JButton btnCancel;
	private _JDateChooser dteDateFrom;
	private _JDateChooser dateDateTo;

	String company;
	String company_logo;	
	String co_id 		= "";
	String branch_id 	= "null";
	String proj_id 		= "null";	
	
	DateFormat df = new SimpleDateFormat("MM/dd/yyyy");

	public DailyCRB_entries() {
		super(title, false, true, false, true);
		initGUI();
	}

	public DailyCRB_entries(String title) {
		super(title);
		initGUI();
	}

	public DailyCRB_entries(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		this.setPreferredSize(new java.awt.Dimension(542, 274));
		this.setLayout(new BorderLayout());
		this.addAncestorListener(this);
		{
			pnlMain = new JPanel(new BorderLayout(5, 5));
			getContentPane().add(pnlMain, BorderLayout.WEST);
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			pnlMain.setPreferredSize(new java.awt.Dimension(541, 249));
			{
				pnlNorth = new JPanel(new BorderLayout(5, 5));
				pnlMain.add(pnlNorth, BorderLayout.NORTH);
				pnlNorth.setPreferredSize(new java.awt.Dimension(537, 203));
				pnlNorth.setBorder(components.JTBorderFactory.createTitleBorder("CRB Details"));// XXX
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
						lookupCompany.setLookupSQL(SQL_COMPANY());
						lookupCompany.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){

									co_id 		= (String) data[0];	
									company		= (String) data[1];	
									company_logo = (String) data[3];

									String name = (String) data[1];						
									txtCompany.setText(name);
									lookupProject.setLookupSQL(CheckStatusMonitoring.sql_project((String) data[0]));
								
									lblProject.setEnabled(true);	
									lookupProject.setText("");
									lookupProject.setEnabled(true);
									txtProject.setEnabled(true);
									txtProject.setText("");									

									lblBranch.setEnabled(true);	
									lookupBranch.setText("");
									lookupBranch.setEnabled(true);
									txtBranch.setEnabled(true);
									txtBranch.setText("");

									KEYBOARD_MANAGER.focusNextComponent();
									btnCancel.setEnabled(true);
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
									txtProject.setText(name);

									KEYBOARD_MANAGER.focusNextComponent();
									btnCancel.setEnabled(true);
								}
							}
						});
					}
					{
						lblBranch = new JLabel("Branch", JLabel.TRAILING);
						pnlNorthWest.add(lblBranch);
						lblBranch.setEnabled(false);	
					}
					{
						lookupBranch = new _JLookup(null, "Branch");
						pnlNorthWest.add(lookupBranch);
						lookupBranch.setReturnColumn(0);
						lookupBranch.setEnabled(false);
						lookupBranch.setLookupSQL(SQL_OFFICE_BRANCH());
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
					txtBranch = new JTextField();
					pnlNorthEast.add(txtBranch, BorderLayout.CENTER);
					txtBranch.setEditable(false);
					txtBranch.setEnabled(false);
				}


				pnlCenter2 =new JPanel(new BorderLayout(5, 5));
				pnlNorth.add(pnlCenter2, BorderLayout.SOUTH);
				pnlCenter2.setPreferredSize(new java.awt.Dimension(409, 66));
				pnlCenter2.setBorder(components.JTBorderFactory.createTitleBorder("Transaction Date"));
				
				pnlCenter2a = new JPanel(new GridLayout(1, 2, 3, 3));
				pnlCenter2.add(pnlCenter2a, BorderLayout.WEST);
				pnlCenter2a.setPreferredSize(new java.awt.Dimension(278, 66));				
				
				{
					/*pnlCriteria2 = new JPanel(new GridLayout(1, 2, 3, 3));
					pnlCenter2a.add(pnlCriteria2, BorderLayout.WEST);				*/	

					{
						lblDateFrom = new JLabel("Date ", JLabel.TRAILING);
						pnlCenter2a.add(lblDateFrom, BorderLayout.CENTER);
						lblDateFrom.setEnabled(true);							
					}
					{
						dteDateFrom = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
						pnlCenter2a.add(dteDateFrom, BorderLayout.CENTER);						
						dteDateFrom.setDate(null);
						dteDateFrom.setEnabled(true);
						dteDateFrom.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
					}	
				}
				
				pnlCenter2b = new JPanel(new BorderLayout(5, 5));
				pnlCenter2.add(pnlCenter2b, BorderLayout.CENTER);
				pnlCenter2b.setPreferredSize(new java.awt.Dimension(174, 60));		
			}				
			{				
				pnlSouth = new JPanel(new GridLayout(1, 2,5, 5));
				pnlMain.add(pnlSouth, BorderLayout.SOUTH);
				pnlSouth.setPreferredSize(new java.awt.Dimension(413, 30));
				{
					btnPreview = new JButton("Preview");
					pnlSouth.add(btnPreview);
					btnPreview.setAlignmentX(0.5f);
					btnPreview.setAlignmentY(0.5f);
					btnPreview.setActionCommand("Preview");
					btnPreview.setMaximumSize(new Dimension(100, 30));
					btnPreview.setMnemonic(KeyEvent.VK_P);
					btnPreview.addActionListener(this);
					btnPreview.setEnabled(false);
				}
				{
					btnCancel = new JButton("Cancel");
					pnlSouth.add(btnCancel);
					btnCancel.setAlignmentX(0.5f);
					btnCancel.setAlignmentY(0.5f);
					btnCancel.setActionCommand("Cancel");
					btnCancel.setMaximumSize(new Dimension(100, 30));
					btnCancel.setMnemonic(KeyEvent.VK_P);
					btnCancel.setEnabled(false);
					btnCancel.addActionListener(this);
				}
			}
		}
		this.setFocusTraversalPolicy(new FncFocusTraversalPolicy(lookupCompany, lookupProject,  
				dteDateFrom, dateDateTo, btnPreview));
		this.setBounds(0, 0, 542, 274);  //174
		
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

		if (e.getActionCommand().equals("Preview") && FncAcounting.EmpCanPreview(UserInfo.EmployeeCode, "12")==true ) { previewCRB(); previewCRB_summary(); }
		else if (e.getActionCommand().equals("Preview") && FncAcounting.EmpCanPreview(UserInfo.EmployeeCode, "12")==false ) 
		{ JOptionPane.showMessageDialog(getContentPane(),"Sorry, you are not authorized to preview CRB entries.","Information",JOptionPane.INFORMATION_MESSAGE); }
		
		if (e.getActionCommand().equals("Cancel")){ cancel(); }
		
	}
	
	public void initialize_comp(){		
		
		co_id 		= "02";	
		company		= "CENQHOMES DEVELOPMENT CORPORATION";	
		company_logo = RequestForPayment.sql_getCompanyLogo();				
		txtCompany.setText(company);
		lookupProject.setLookupSQL(CheckStatusMonitoring.sql_project(co_id));
	
		lblProject.setEnabled(true);	
		lookupProject.setText("");
		lookupProject.setEnabled(true);
		txtProject.setEnabled(true);
		txtProject.setText("");									

		lblBranch.setEnabled(true);	
		lookupBranch.setText("");
		lookupBranch.setEnabled(true);
		txtBranch.setEnabled(true);
		txtBranch.setText("");

		KEYBOARD_MANAGER.focusNextComponent();
		btnCancel.setEnabled(true);
		btnPreview.setEnabled(true);
		
		lookupCompany.setValue(co_id);
}
	
	
	//preview
	public void previewCRB(){
		
		String criteria = "Daily CRB Entries";		
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());
		
		//Double cdr_amt	= Double.parseDouble(modelSample2Total.getValueAt(0,12).toString());	
		String branch   = lookupBranch.getText();
		if (branch.equals("")) {
			branch = "";
		} else {
			
		}
		
		String proj = lookupProject.getText();
		if (proj.equals("")) {
			proj = "";
		} else {
			
		}
		
		System.out.println("");
		System.out.println("company: " + company);
		System.out.println("co_id: " + co_id);
		System.out.println("branch: " + branch);
		System.out.println("logo: " + this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
		System.out.println("prepared_by: " + UserInfo.FullName);
		System.out.println("cdr_date: " + dteDateFrom.getDate());
		System.out.println("cdr_date_str: " + df.format(dteDateFrom.getDate()));	
		System.out.println("status: " + "POSTED");
		System.out.println("posted_by: " + UserInfo.EmployeeCode );
		System.out.println("proj_id: " + proj);
		
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("company", company);
		mapParameters.put("co_id", co_id);
		mapParameters.put("branch", branch);
		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
		mapParameters.put("prepared_by", UserInfo.FullName);
		mapParameters.put("cdr_date", dteDateFrom.getDate());
		mapParameters.put("cdr_date_str", df.format(dteDateFrom.getDate()));	
		mapParameters.put("status", "POSTED");
		mapParameters.put("posted_by", UserInfo.EmployeeCode );
		mapParameters.put("proj_id", proj);
		mapParameters.put("phase", "");

		FncReport.generateReport("/Reports/rptCRB_daily_preview.jasper", reportTitle, company, mapParameters);		
	}
	
	public void previewCRB_summary(){
		
		String criteria2 = "Daily CRB Entries Summary";		
		String reportTitle2 = String.format("%s (%s)", title.replace(" Report", ""), criteria2.toUpperCase());
		
		//Double cdr_amt	= Double.parseDouble(modelSample2Total.getValueAt(0,12).toString());	
		String branch   = lookupBranch.getText();
		if (branch.equals("")) {
			branch = "";
		} else {
			
		}
		
		String proj   = lookupProject.getText();
		if (proj.equals("")) {
			proj = "";
		} else {
			
		}
		
		String particular = "To record daily collection for " + df.format(dteDateFrom.getDate()) + ".";
		
		Map<String, Object> mapParameters2 = new HashMap<String, Object>();
		mapParameters2.put("company", company);
		mapParameters2.put("co_id", co_id);
		mapParameters2.put("branch", branch);
		mapParameters2.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
		mapParameters2.put("prepared_by", UserInfo.FullName);
		//mapParameters2.put("prepared_by", sql_getPreparedby(df.format(dteDateFrom.getDate()), lookupBranch.getText()));
		mapParameters2.put("cdr_date", dteDateFrom.getDate());
		mapParameters2.put("cdr_date_str", df.format(dteDateFrom.getDate()));
		mapParameters2.put("status", "POSTED");
		//mapParameters2.put("posted_by", sql_getPreparedby(df.format(dteDateFrom.getDate()),lookupBranch.getText()));
		mapParameters2.put("proj_id", proj);
		mapParameters2.put("particular", particular);
		mapParameters2.put("phase", "");

		FncReport.generateReport("/Reports/rptCRB_daily_preview_summary.jasper", reportTitle2, company, mapParameters2);		
	}

	public void cancel(){		
		
		lookupProject.setText("");
		txtProject.setText("");		
		lookupBranch.setText("");
		txtBranch.setText("");
		dteDateFrom.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
		
		btnCancel.setEnabled(false);
		btnPreview.setEnabled(false);

		
	}

	private String sql_getPreparedby(String date_str, String branch_id) {//XXX Phase

		String prep_by = "";

		String SQL = 
			"select string_agg(issued_by,' & ') \n" + 
			"from (select distinct on (get_employee_name(issued_by)) get_employee_name(issued_by) as issued_by \n" + 
			"		from (select * from rf_crb_header where issued_date::date = '"+date_str+"'\n" + 
			"		and (rb_id, doc_id) in (\n" + 
			"			select or_no, (case when or_doc_id is null then '03' else or_doc_id end) from rf_payments " +
			"			where (case when '"+branch_id+"' = '' or branch_id is null then true else branch_id = '"+branch_id+"' end)  \n" + 
			"			union all \n" + 
			"			select receipt_no, receipt_type from rf_tra_header " +
			"			where (case when '"+branch_id+"' = '' or branch_id is null then true else branch_id = '"+branch_id+"' end)\n" + 
			"			) \n" + 
			"		) a ) a";

		System.out.printf("sql_getBuyerType :"+ SQL + "\n");

		pgSelect db = new pgSelect();
		db.select(SQL);

		if (db.isNotNull()) {
			if (db.getResult()[0][0] == null || db.getResult()[0][0].equals("null")) {prep_by = "";} 
			else {prep_by = db.getResult()[0][0].toString();}
		} else {}

		return prep_by;
	}

}
