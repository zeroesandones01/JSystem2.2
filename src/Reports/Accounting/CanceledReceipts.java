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

public class CanceledReceipts extends _JInternalFrame implements ActionListener, AncestorListener, _GUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1162406788303915011L;

	static String title = "List of Canceled Receipts";
	static Dimension frameSize = new Dimension(500, 250);
	static Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	static Cursor handCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);

	private JPanel pnlMain;
	private JPanel pnlNorth;
	private JPanel pnlNorthWest;
	private JPanel pnlNorthEast;
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
	String branch_name	= "";
	String branch_id 	= "";
	String proj_id 		= "";	
	String proj_name 	= "";
	
	DateFormat df = new SimpleDateFormat("MM/dd/yyyy");

	private JPanel pnlCenter_b;

	private JLabel lblDateTo;

	public CanceledReceipts() {
		super(title, false, true, false, true);
		initGUI();
	}

	public CanceledReceipts(String title) {
		super(title);
		initGUI();
	}

	public CanceledReceipts(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		this.setPreferredSize(new java.awt.Dimension(545, 274));
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
				pnlNorth.setPreferredSize(new java.awt.Dimension(531, 133));
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
									
									lblDateFrom.setEnabled(true);				
									lblDateTo.setEnabled(true);				
									dteDateFrom.setEnabled(true);
									dateDateTo.setEnabled(true);

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
									proj_name = (String) data[1];						
									txtProject.setText(proj_name);

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
									branch_name  = (String) data[1];						
									txtBranch.setText(branch_name);

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

				{
					pnlCenter_b = new JPanel(new GridLayout(1, 2, 3, 3));
					pnlMain.add(pnlCenter_b, BorderLayout.CENTER);
					pnlCenter_b.setPreferredSize(new java.awt.Dimension(480, 65));	
					pnlCenter_b.setBorder(components.JTBorderFactory.createTitleBorder("Period (Date of Cancelation)"));// XXX

					{
						lblDateFrom = new JLabel("Date From  :", JLabel.TRAILING);
						pnlCenter_b.add(lblDateFrom, BorderLayout.CENTER);
						lblDateFrom.setEnabled(false);							
					}
					{
						dteDateFrom = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
						pnlCenter_b.add(dteDateFrom, BorderLayout.CENTER);						
						dteDateFrom.setDate(null);
						dteDateFrom.setEnabled(false);
						dteDateFrom.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
					}		
					{
						lblDateTo = new JLabel("Date To  :", JLabel.TRAILING);
						pnlCenter_b.add(lblDateTo);
						lblDateTo.setEnabled(false);	
					}
					{
						dateDateTo = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
						pnlCenter_b.add(dateDateTo, BorderLayout.EAST);
						dateDateTo.setBounds(485, 7, 125, 21);
						dateDateTo.setDate(null);
						dateDateTo.setEnabled(false);
						dateDateTo.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
					}
				}				
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
		this.setBounds(0, 0, 545, 274);  //174
		
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
		if (e.getActionCommand().equals("Preview") && FncAcounting.EmpCanPreview(UserInfo.EmployeeCode, "12")==true) {
			previewCancRcpt();
		} else if (e.getActionCommand().equals("Preview") && FncAcounting.EmpCanPreview(UserInfo.EmployeeCode, "12")==false) {
			JOptionPane.showMessageDialog(getContentPane(),"Sorry, you are not authorized to preview CRB entries.","Information",JOptionPane.INFORMATION_MESSAGE);
		}
		
		if (e.getActionCommand().equals("Cancel")){
			cancel();
		}
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
		
		lblDateFrom.setEnabled(true);				
		lblDateTo.setEnabled(true);				
		dteDateFrom.setEnabled(true);
		dateDateTo.setEnabled(true);

		KEYBOARD_MANAGER.focusNextComponent();
		btnCancel.setEnabled(true);
		btnPreview.setEnabled(true);
		
		lookupCompany.setValue(co_id);
}
	
	
	//preview
	public void previewCancRcpt(){
		
		String criteria = "List of Canceled Receipts";		
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());
				
		String branch   = lookupBranch.getText();
		if (branch.equals("")) {branch_name = "All";} 
		else {}
		
		String proj   = lookupProject.getText();
		if (proj.equals("")) {proj_name = "All";} 
		else {}
		
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("company", company);
		mapParameters.put("co_id", co_id);
		mapParameters.put("branch_id", branch_id);
		mapParameters.put("branch", branch_name);
		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
		mapParameters.put("prepared_name", UserInfo.FullName);
		mapParameters.put("date_from", dteDateFrom.getDate());
		mapParameters.put("date_to", dateDateTo.getDate());
		mapParameters.put("cdr_date_str", df.format(dteDateFrom.getDate()));
		mapParameters.put("posted_by", UserInfo.EmployeeCode );
		mapParameters.put("proj_id", proj_id);
		mapParameters.put("proj_name", proj_name);

		FncReport.generateReport("/Reports/rptListofCanceledReceipts.jasper", reportTitle, company, mapParameters);		
	}
	
	public void cancel(){		
		lookupProject.setText("");
		txtProject.setText("");		
		lookupBranch.setText("");
		txtBranch.setText("");
		dteDateFrom.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
		dateDateTo.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
		
		btnCancel.setEnabled(false);
		btnPreview.setEnabled(true);	
		
		branch_name	= "";
		branch_id 	= "";
		proj_id 		= "";	
		proj_name 	= "";
	}
}
