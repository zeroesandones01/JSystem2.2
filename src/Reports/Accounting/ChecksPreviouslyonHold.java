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
import Functions.FncFocusTraversalPolicy;
import Functions.FncGlobal;
import Functions.FncReport;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;

import components._JInternalFrame;

public class ChecksPreviouslyonHold extends _JInternalFrame implements ActionListener, AncestorListener, _GUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1162406788303915011L;

	static String title = "Checks on Hold";
	static Dimension frameSize = new Dimension(500, 250);
	static Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	static Cursor handCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);

	private JPanel pnlMain;
	private JPanel pnlNorth;
	private JPanel pnlNorthWest;
	private JPanel pnlNorthEast;
	private JPanel pnlCenter2;
	private JPanel pnlCriteria2;
	
	private JLabel lblProject;
	private JLabel lblCompany;
	private JLabel lblDateFrom;
	private JLabel lblDateTo;
	
	private _JLookup lookupProject;
	private _JLookup lookupCompany;
	
	private JTextField txtProject;
	private JTextField txtCompany;
	
	private JPanel pnlSouth;
	private JButton btnPreview;
	private JButton btnCancel;
	
	String company;
	String company_logo;		

	private _JDateChooser dteDateFrom;
	private _JDateChooser dateDateTo;	

	String co_id = "";

	public ChecksPreviouslyonHold() {
		super(title, false, true, false, true);
		initGUI();
	}

	public ChecksPreviouslyonHold(String title) {
		super(title);
		initGUI();
	}

	public ChecksPreviouslyonHold(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		this.setPreferredSize(new java.awt.Dimension(560, 232));
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
				pnlNorth.setPreferredSize(new java.awt.Dimension(547, 160));
				pnlNorth.setBorder(components.JTBorderFactory.createTitleBorder("Check Details"));// XXX
				{
					pnlNorthWest = new JPanel(new GridLayout(2,1, 5, 5));
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
									String name = (String) data[1];						
									txtProject.setText(name);
												
									KEYBOARD_MANAGER.focusNextComponent();
									btnCancel.setEnabled(true);
								}
							}
						});
					}
				}
				pnlNorthEast = new JPanel(new GridLayout(2, 1, 5, 5));
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
				
				
				pnlCenter2 = new JPanel(new GridLayout(1,1,5, 5));
				pnlNorth.add(pnlCenter2, BorderLayout.SOUTH);
				pnlCenter2.setPreferredSize(new java.awt.Dimension(499, 60));
				pnlCenter2.setBorder(components.JTBorderFactory.createTitleBorder("Check Due Date"));
				{
					pnlCriteria2 = new JPanel(new GridLayout(1, 2, 3, 3));
					pnlCenter2.add(pnlCriteria2, BorderLayout.WEST);					
					
					{
						lblDateFrom = new JLabel("Date From   ", JLabel.TRAILING);
						pnlCriteria2.add(lblDateFrom, BorderLayout.CENTER);
						lblDateFrom.setEnabled(true);							
					}
					{
						dteDateFrom = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
						pnlCriteria2.add(dteDateFrom, BorderLayout.CENTER);						
						dteDateFrom.setDate(null);
						dteDateFrom.setEnabled(true);
						dteDateFrom.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
					}		
								
						lblDateTo = new JLabel("Date To   ", JLabel.TRAILING);
						pnlCriteria2.add(lblDateTo);
						lblDateTo.setEnabled(true);	
					}
					{
						dateDateTo = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
						pnlCriteria2.add(dateDateTo, BorderLayout.EAST);
						dateDateTo.setBounds(485, 7, 125, 21);
						dateDateTo.setDate(null);
						dateDateTo.setEnabled(true);
						dateDateTo.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
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
		this.setFocusTraversalPolicy(new FncFocusTraversalPolicy(lookupCompany, lookupProject,  
				 dteDateFrom, dateDateTo, btnPreview));
		this.setBounds(0, 0, 560, 232);  //174
		
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
			String criteria = "PDC Due for the Day";		
			String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());		
		
			Map<String, Object> mapParameters = new HashMap<String, Object>();
			mapParameters.put("company", company);
			mapParameters.put("proj_id", lookupProject.getValue());
			mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
			mapParameters.put("prepared_by", UserInfo.Alias);
			mapParameters.put("date_from", dteDateFrom.getDate());	
			mapParameters.put("date_to", dateDateTo.getDate());	

			FncReport.generateReport("/Reports/rptChecksOnHold.jasper", reportTitle, txtProject.getText(), mapParameters);
		}
		if(e.getActionCommand().equals("Cancel")){	
			lookupCompany.setText("");
			txtCompany.setText("");
			lblProject.setEnabled(false);	
			lookupProject.setEnabled(false);	lookupProject.setText("");
			txtProject.setEnabled(false);		txtProject.setText("");
			dteDateFrom.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
			dateDateTo.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
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
		
		KEYBOARD_MANAGER.focusNextComponent();
		btnCancel.setEnabled(true);
		
		lookupCompany.setValue(co_id);
}

	
}
