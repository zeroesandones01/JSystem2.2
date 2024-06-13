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
import DateChooser._JDateChooser;
import Functions.FncFocusTraversalPolicy;
import Functions.FncGlobal;
import Functions.FncReport;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components._JInternalFrame;

public class SummaryBouncedChecks extends _JInternalFrame implements ActionListener, AncestorListener, _GUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1162406788303915011L;

	static String title = "Summary of Bounced Checks";
	static Dimension frameSize = new Dimension(500, 250);
	static Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	static Cursor handCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);

	private JPanel pnlMain;
	private JPanel pnlNorth;
	private JPanel pnlNorthWest;
	private JPanel pnlCenter;
	private JPanel pnlCriteria;
	private JPanel pnlNorthEast;
	
	private JLabel lblProject;
	private JLabel lblCompany;
	private JLabel lblReason;
	private JLabel lblDateFrom;
	private JLabel lblDateTo;
	private JLabel lblCheckStatus;
	
	private _JLookup lookupProject;
	private _JLookup lookupCompany;
	private _JLookup lookupReason;
	
	private JTextField txtProject;
	private JTextField txtCompany;
	private JTextField txtReason;
	
	private JPanel pnlSouth;
	private JButton btnPreview;
	private JButton btnCancel;
	
	String company;
	String company_logo;		

	private _JDateChooser dteDateFrom;
	private _JDateChooser dateDateTo;	

	private JComboBox cmbStatus;
	
	String co_id = "02";
	String branch_id = "";

	private JLabel lblBranch;

	private _JLookup lookupBranch;

	private JTextField txtBranch;

	public SummaryBouncedChecks() {
		super(title, false, true, false, true);
		initGUI();
	}

	public SummaryBouncedChecks(String title) {
		super(title);
		initGUI();
	}

	public SummaryBouncedChecks(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		this.setPreferredSize(new java.awt.Dimension(571, 334));
		this.setLayout(new BorderLayout());
		this.addAncestorListener(this);
		{
			pnlMain = new JPanel(new BorderLayout(5, 5));
			getContentPane().add(pnlMain, BorderLayout.WEST);
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			pnlMain.setPreferredSize(new java.awt.Dimension(568, 800));
			{
				pnlNorth = new JPanel(new BorderLayout(5, 5));
				pnlMain.add(pnlNorth, BorderLayout.NORTH);
				pnlNorth.setPreferredSize(new java.awt.Dimension(558, 193));
				pnlNorth.setBorder(components.JTBorderFactory.createTitleBorder("Check Details"));// XXX
				{
					pnlNorthWest = new JPanel(new GridLayout(5, 2, 5, 5));
					pnlNorth.add(pnlNorthWest, BorderLayout.WEST);
					pnlNorthWest.setPreferredSize(new Dimension(210, 130));
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
					{
						lblReason = new JLabel("Reason of Bounce", JLabel.TRAILING);
						pnlNorthWest.add(lblReason);
					}
					{
						lookupReason = new _JLookup(null, "Status");
						pnlNorthWest.add(lookupReason);
						lookupReason.setReturnColumn(0);
						lookupReason.setLookupSQL("select reason_id, reason_desc from mf_check_bounce_reason");
						lookupReason.setPreferredSize(new java.awt.Dimension(62, 27));
						lookupReason.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){
									txtReason.setText((String) data[1]);
									btnCancel.setEnabled(true);
								}
							}
						});
					}
					{
						lblBranch = new JLabel("Branch", JLabel.TRAILING);
						pnlNorthWest.add(lblBranch);
						lblBranch.setEnabled(true);
					}
					{
						lookupBranch = new _JLookup(null, "Branch");
						pnlNorthWest.add(lookupBranch);
						lookupBranch.setReturnColumn(0);
						lookupBranch.setEnabled(true);
						//lookupBranch.setLookupSQL(SQL_COMPANY());
						lookupBranch.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup) event.getSource())
										.getDataSet();
								if (data != null) {

									String name = (String) data[1];
									txtBranch.setText(name);

									KEYBOARD_MANAGER.focusNextComponent();
									btnCancel.setEnabled(true);
								}
							}
						});
					}
					{
						lblCheckStatus = new JLabel("Check Type", JLabel.TRAILING);
						pnlNorthWest.add(lblCheckStatus);
						lblCheckStatus.setEnabled(true);	
					}
					String status[] = {"Both","Dated","PDC"};					
						cmbStatus = new JComboBox(status);
						pnlNorthWest.add(cmbStatus);
						cmbStatus.setSelectedItem(null);
						cmbStatus.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,13));
						cmbStatus.setBounds(537, 15, 160, 21);	
						cmbStatus.setEnabled(true);
						cmbStatus.setSelectedIndex(0);	
						cmbStatus.addItemListener(new ItemListener() {
						public void itemStateChanged(ItemEvent evt) 
						{								
						}
					});
				}
				pnlNorthEast = new JPanel(new GridLayout(5, 1, 5, 5));
				pnlNorth.add(pnlNorthEast, BorderLayout.CENTER);
				pnlNorthEast.setPreferredSize(new Dimension(400, 100));
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
					txtReason = new JTextField();
					pnlNorthEast.add(txtReason, BorderLayout.CENTER);
					txtReason.setEditable(false);
				}
				{
					txtBranch = new JTextField();
					pnlNorthEast.add(txtBranch, BorderLayout.CENTER);
					txtBranch.setEditable(true);
				}
				
			}	
			{
				pnlCenter = new JPanel(new GridLayout(1, 2,5, 5));
				pnlMain.add(pnlCenter, BorderLayout.CENTER);
				pnlCenter.setPreferredSize(new java.awt.Dimension(499, 100));
				pnlCenter.setBorder(components.JTBorderFactory.createTitleBorder("Check Date"));
				{
					pnlCriteria = new JPanel(new GridLayout(1, 4, 3, 3));
					pnlCenter.add(pnlCriteria, BorderLayout.WEST);					
					
					{
						lblDateFrom = new JLabel("Date From   ", JLabel.TRAILING);
						pnlCriteria.add(lblDateFrom, BorderLayout.CENTER);
						lblDateFrom.setEnabled(true);							
					}
					{
						dteDateFrom = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
						pnlCriteria.add(dteDateFrom, BorderLayout.CENTER);						
						dteDateFrom.setDate(null);
						dteDateFrom.setEnabled(true);
						dteDateFrom.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
					}		
								
						lblDateTo = new JLabel("Date To   ", JLabel.TRAILING);
						pnlCriteria.add(lblDateTo);
						lblDateTo.setEnabled(true);	
					}
					{
						dateDateTo = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
						pnlCriteria.add(dateDateTo, BorderLayout.EAST);
						dateDateTo.setBounds(485, 7, 125, 21);
						dateDateTo.setDate(null);
						dateDateTo.setEnabled(true);
						dateDateTo.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
					}
			}
			{				
				pnlSouth = new JPanel(new GridLayout(1, 2,5, 5));
				pnlMain.add(pnlSouth, BorderLayout.SOUTH);
				//pnlSouth.setLayout(new OverlayLayout(pnlSouth));
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
				lookupReason, dteDateFrom, dateDateTo, btnPreview));
		this.setBounds(0, 0, 571, 334);  //174
		
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
			String criteria = "Summary of Bounced Checks";		
			String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());
		
			Map<String, Object> mapParameters = new HashMap<String, Object>();
			mapParameters.put("company", company);
			mapParameters.put("proj_id", lookupProject.getValue());
			mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
			mapParameters.put("prepared_by", UserInfo.Alias);
			mapParameters.put("reason", txtReason.getText().trim());	
			mapParameters.put("date_from", dteDateFrom.getDate());	
			mapParameters.put("date_to", dateDateTo.getDate());	
			mapParameters.put("type", (String) cmbStatus.getSelectedItem());	
			System.out.printf("type :\n" + (String) cmbStatus.getSelectedItem());
			mapParameters.put("branch_id", lookupBranch.getText());	
			mapParameters.put("co_id", lookupCompany.getValue());	

			FncReport.generateReport("/Reports/rptSummaryofBouncedChecks.jasper", reportTitle, txtProject.getText(), mapParameters);
		}
		if(e.getActionCommand().equals("Cancel")){	
			//lookupCompany.setText("");
			//txtCompany.setText("");
			lblProject.setEnabled(false);	
			lookupProject.setEnabled(false);	lookupProject.setText("");
			txtProject.setEnabled(false);		txtProject.setText("");
			lookupReason.setText("");			txtReason.setText("");
			dteDateFrom.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
			dateDateTo.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
			cmbStatus.setSelectedIndex(0);	
			lookupBranch.setText("");
			txtBranch.setText("");
			branch_id = "";
		}
		
	}

	public void initialize_comp(){		
		
		co_id 		= "02";	
		company		= "CENQHOMES DEVELOPMENT CORPORATION";		
		company_logo = RequestForPayment.sql_getCompanyLogo();			
		txtCompany.setText(company);
		
		lookupProject.setLookupSQL(CheckStatusMonitoring.sql_project(co_id));
		lookupBranch.setLookupSQL(getBranch());
		lblProject.setEnabled(true);	
		lookupProject.setText("");
		lookupProject.setEnabled(true);
		txtProject.setEnabled(true);
		txtProject.setText("");
		
		KEYBOARD_MANAGER.focusNextComponent();
		btnCancel.setEnabled(true);
		
		lookupCompany.setValue(co_id);
		
	}
	
	static String getBranch() {
		return "SELECT branch_id, branch_name, branch_alias FROM mf_office_branch;";
	}
	
}
