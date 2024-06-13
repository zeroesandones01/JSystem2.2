package Reports.PropertyManagement;

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
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import org.jdesktop.swingx.JXPanel;

import Accounting.Collections.CheckStatusMonitoring;
import Accounting.Disbursements.RequestForPayment;
import DateChooser._JDateChooser;
import Functions.FncGlobal;
import Functions.FncReport;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components._JInternalFrame;
import interfaces._GUI;

public class pmd_agingreport extends _JInternalFrame implements ActionListener, AncestorListener, _GUI {

	private static final long serialVersionUID = 1162406788303915011L;

	static String title = "PMD Aging Report";
	static Dimension frameSize = new Dimension(680, 295);
	static Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	static Cursor handCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);

	private JPanel pnlMain;
	private JPanel pnlNorth;
	private JPanel pnlNorthWest;
	private JPanel pnlNorthEast;
	private JPanel pnlCenter2;
	private JPanel pnlCenter_b;
	private JPanel pnlSouth;
	private JPanel pnlCPFdate;
	private JPanel pnlInclude;
	
	private JLabel lblCompany;
	private JLabel lblProject;
	private JLabel lblPhase;
	private JLabel lblDateFrom;
	private JLabel lblDateTo;

	private _JLookup lookupCompany;
	private _JLookup lookupProject;
	private _JLookup lookupPhase;

	private JTextField txtCompany;
	private JTextField txtProject;
	private JTextField txtPhase;
	
	private _JDateChooser dteDateFrom;
	private _JDateChooser dateDateTo;

	private JCheckBox chkInclude;

	private JButton btnPreview;
	private JButton btnCancel;

	private Boolean include_all;
	
	String company;
	String company_logo;	
	String co_id = "";
	String proj_id = "";	
	String project = "";
	String phase_no = "";
	String phase = "All";

	SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");


	public pmd_agingreport() {
		super(title, true, true, true, true);
		initGUI();
	}

	public pmd_agingreport(String title) {
		super(title);
		initGUI();
	}

	public pmd_agingreport(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		this.setLayout(new BorderLayout());
		this.addAncestorListener(this);
		{
			pnlMain = new JPanel(new BorderLayout(5, 5));
			getContentPane().add(pnlMain, BorderLayout.CENTER);
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			{
				JXPanel pnlCenter = new JXPanel(new GridBagLayout());
				pnlMain.add(pnlCenter,BorderLayout.CENTER);
				{
					GridBagConstraints gbc = new GridBagConstraints();
					gbc.fill = GridBagConstraints.BOTH;
					{
						gbc.weightx = 1;
						gbc.weighty = 1;
						
						gbc.gridx = 0;
						gbc.gridy = 0;
						
						pnlNorth = new JPanel(new GridBagLayout());
						pnlCenter.add(pnlNorth,gbc);
						pnlNorth.setBorder(components.JTBorderFactory.createTitleBorder("Company/ Project" ));
						{
							GridBagConstraints bagOne = new GridBagConstraints();
							bagOne.fill = GridBagConstraints.BOTH;
							bagOne.insets = new Insets(5,3,5,3);
							{
								bagOne.weightx = 0;
								bagOne.weighty = 1;
								
								bagOne.gridx = 0;
								bagOne.gridy = 0;
								
								lblCompany = new JLabel("Company", JLabel.TRAILING);
								pnlNorth.add(lblCompany,bagOne);
								lblCompany.setFont(FncGlobal.sizeLabel);
							}
							{
								bagOne.weightx = 0.5;
								bagOne.weighty = 1;
								
								bagOne.gridx = 1;
								bagOne.gridy = 0;
								
								lookupCompany = new _JLookup(null, "Company");
								pnlNorth.add(lookupCompany,bagOne);
								lookupCompany.setReturnColumn(0);
								lookupCompany.setLookupSQL(SQL_COMPANY());
								lookupCompany.setFont(FncGlobal.sizeTextValue);
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
											
											lblPhase.setEnabled(true);	
											lookupPhase.setText("");
											lookupPhase.setEnabled(true);
											txtPhase.setEnabled(true);
											txtPhase.setText("");
											
											lblDateFrom.setEnabled(true);		
											dteDateFrom.setEnabled(true);
											lblDateTo.setEnabled(true);	
											dateDateTo.setEnabled(true);

											dteDateFrom.setDate(FncGlobal.dateFormat("2017-01-01"));

											
											btnPreview.setEnabled(true);

											KEYBOARD_MANAGER.focusNextComponent();
											btnCancel.setEnabled(true);
										}
									}
								});
							}
							{
								bagOne.weightx = 1.25;
								bagOne.weighty = 1;
								
								bagOne.gridx = 2;
								bagOne.gridy = 0;
								
								txtCompany = new JTextField();
								pnlNorth.add(txtCompany, bagOne);
								txtCompany.setEditable(false);
								txtCompany.setFont(FncGlobal.sizeTextValue);
							}
							{
								bagOne.weightx = 0;
								bagOne.weighty = 1;
								
								bagOne.gridx = 0;
								bagOne.gridy = 1;
								
								lblProject = new JLabel("Project", JLabel.TRAILING);
								pnlNorth.add(lblProject,bagOne);
								lblProject.setEnabled(true);
								lblProject.setFont(FncGlobal.sizeLabel);
							}
							{
								bagOne.weightx = 0.5;
								bagOne.weighty = 1;
								
								bagOne.gridx = 1;
								bagOne.gridy = 1;
								
								lookupProject = new _JLookup(null, "Project");
								pnlNorth.add(lookupProject,bagOne);
								lookupProject.setReturnColumn(0);
								lookupProject.setEnabled(true);
								lookupProject.setFont(FncGlobal.sizeTextValue);
								lookupProject.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup)event.getSource()).getDataSet();
										if(data != null){

											proj_id = (String) data[0];		
											String name = (String) data[1];	
											project = (String) data[1];	
											txtProject.setText(name);
											
											
											lookupPhase.setLookupSQL(SQL_PHASE((String) data[0]));
											lookupPhase.setValue(null);
											txtPhase.setText("");
											lblPhase.setEnabled(true);	
											lookupPhase.setEnabled(true);
											
											
											btnPreview.setEnabled(true);
											
											KEYBOARD_MANAGER.focusNextComponent();
											btnCancel.setEnabled(true);
										}
									}
								});
							}
							{
								bagOne.weightx = 1.25;
								bagOne.weighty = 1;
								
								bagOne.gridx = 2;
								bagOne.gridy = 1;
								
								txtProject = new JTextField();
								pnlNorth.add(txtProject,bagOne);
								txtProject.setEditable(false);
								txtProject.setFont(FncGlobal.sizeTextValue);
							}
							{
								bagOne.weightx = 0;
								bagOne.weighty = 1;
								
								bagOne.gridx = 0;
								bagOne.gridy = 2;
								
								lblPhase = new JLabel("Phase", JLabel.TRAILING);
								pnlNorth.add(lblPhase,bagOne);
								lblPhase.setEnabled(true);
								lblPhase.setFont(FncGlobal.sizeLabel);
							}
							{
								bagOne.weightx = 0.5;
								bagOne.weighty = 1;
								
								bagOne.gridx = 1;
								bagOne.gridy = 2;
								
								lookupPhase = new _JLookup(null, "Phase");
								pnlNorth.add(lookupPhase,bagOne);
								lookupPhase.setReturnColumn(0);
								lookupPhase.setLookupSQL(SQL_PHASE("015"));						
								lookupPhase.setEnabled(true);
								lookupPhase.setFont(FncGlobal.sizeTextValue);
								lookupPhase.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup) event.getSource()).getDataSet();
										if (data != null) {

											txtPhase.setText(String.format("Phase %s", (String) data[0]));	
											//lookupPhase.setLookupSQL(SQL_PHASE((String) data[0]));		
											
											KEYBOARD_MANAGER.focusNextComponent();
											btnCancel.setEnabled(true);
										}
									}
								});
							}
							{
								bagOne.weightx = 1.25;
								bagOne.weighty = 1;
								
								bagOne.gridx = 2;
								bagOne.gridy = 2;
								
								txtPhase = new JTextField();
								pnlNorth.add(txtPhase,bagOne);
								txtPhase.setEditable(false);
								txtPhase.setFont(FncGlobal.sizeTextValue);
							}
						}
					}
					{
						gbc.weightx = 1;
						gbc.weighty = 1;
						
						gbc.gridx = 0;
						gbc.gridy = 1;
						
						pnlCenter2 = new JPanel(new GridBagLayout());
						pnlCenter.add(pnlCenter2, gbc);
						pnlCenter2.setBorder(components.JTBorderFactory.createTitleBorder("Report Period"));
						{
							GridBagConstraints bagTwo = new GridBagConstraints();
							bagTwo.fill = GridBagConstraints.BOTH;
							bagTwo.insets = new Insets(5, 3, 5, 3);
							{
								bagTwo.weightx = 0.5;
								bagTwo.weighty = 1;
								
								bagTwo.gridx = 0;
								bagTwo.gridy = 0;
								
								lblDateFrom = new JLabel("Period From :", JLabel.TRAILING);
								pnlCenter2.add(lblDateFrom,bagTwo);
								lblDateFrom.setEnabled(false);
								lblDateFrom.setHorizontalAlignment(JTextField.LEADING);	
								lblDateFrom.setFont(FncGlobal.sizeLabel);
							}
							{
								bagTwo.weightx = 1;
								bagTwo.weighty = 1;
								
								bagTwo.gridx = 1;
								bagTwo.gridy = 0;
								
								dteDateFrom = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
								pnlCenter2.add(dteDateFrom,bagTwo);
								dteDateFrom.setDate(null);
								dteDateFrom.setEnabled(false);
								dteDateFrom.setDate(FncGlobal.dateFormat("2017-01-01"));
								dteDateFrom.setFont(FncGlobal.sizeTextValue);
							}
							{
								bagTwo.weightx = 0;
								bagTwo.weighty = 1;
								
								bagTwo.gridx = 2;
								bagTwo.gridy = 0;
								
								lblDateTo = new JLabel("Period To :", JLabel.TRAILING);
								pnlCenter2.add(lblDateTo,bagTwo);
								lblDateTo.setEnabled(false);
								lblDateTo.setHorizontalAlignment(JTextField.LEADING);	
								lblDateTo.setFont(FncGlobal.sizeLabel);
							}
							{
								bagTwo.weightx = 1;
								bagTwo.weighty = 1;
								
								bagTwo.gridx = 3;
								bagTwo.gridy = 0;
								
								dateDateTo = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
								pnlCenter2.add(dateDateTo,bagTwo);
								dateDateTo.setDate(null);
								dateDateTo.setEnabled(false);
								dateDateTo.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
								dateDateTo.setFont(FncGlobal.sizeTextValue);
							}
						
							
							{
								bagTwo.weightx = 1;
								bagTwo.weighty = 1;
								
								bagTwo.gridx = 1;
								bagTwo.gridy = 1;
								
								chkInclude = new JCheckBox("Include All");
								pnlCenter2.add(chkInclude,bagTwo);
								chkInclude.setHorizontalAlignment(JTextField.CENTER);
								chkInclude.setSelected(false);
								chkInclude.setFont(FncGlobal.sizeLabel);
								chkInclude.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent arg0) {
										if (chkInclude.isSelected()) {
										}
									}
								});
							}
						}
					}
				}
				
			
			{
			

					{

						pnlSouth = new JPanel(new GridLayout(1, 2, 5, 5));
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
							btnPreview.setFont(FncGlobal.sizeControls);
						}
						{
							btnCancel = new JButton("Cancel");
							pnlSouth.add(btnCancel);
							btnCancel.setAlignmentX(0.5f);
							btnCancel.setAlignmentY(0.5f);
							btnCancel.setActionCommand("Cancel");
							btnCancel.setMaximumSize(new Dimension(100, 30));
							btnCancel.setMnemonic(KeyEvent.VK_P);
							btnCancel.addActionListener(this);		
							btnCancel.setFont(FncGlobal.sizeControls);

						}				
					}
				}
			}					
		}
	}

	@Override
	public void ancestorAdded(AncestorEvent event) {
			lookupCompany.requestFocus();
		}

	@Override
	public void ancestorMoved(AncestorEvent event) { }

	@Override
	public void ancestorRemoved(AncestorEvent event) { }

	@Override
	public void actionPerformed(ActionEvent act) {

		String actionCommand = act.getActionCommand();
	
		if(actionCommand.equals("Preview")){
				view_pmd_aging_rpt();
			}
		if (actionCommand.equals("Cancel")){
			lookupCompany.setValue("");
			txtCompany.setText("");
			
			lookupProject.setValue("");
			txtProject.setText("");
		
			lookupPhase.setValue("");
			txtPhase.setText("");
			
			dteDateFrom.setDate(FncGlobal.dateFormat("2017-01-01"));
			dateDateTo.setDate(null);
		}				
	}
	
	
	//enable, disable
	public void refreshFields(){
		
		lookupProject.setText("");
		txtProject.setText("");
		lookupPhase.setText("");
		txtPhase.setText("");

		dteDateFrom.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
		dateDateTo.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
	}
	
	
	//preview
	private void view_pmd_aging_rpt(){
	
		include_all = chkInclude.isSelected();

		if(chkInclude.isSelected()==true) {include_all = true;} else {include_all = false;}
		
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("company_name", company);	
		mapParameters.put("prepared_by", UserInfo.Alias);
		mapParameters.put("prepared_by_name", UserInfo.FullName);
		mapParameters.put("report_date", dateFormat.format(FncGlobal.dateFormat(FncGlobal.getDateSQL())));
		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
		mapParameters.put("date_fr",dteDateFrom.getDate());
		mapParameters.put("date_to", dateDateTo.getDate());
		mapParameters.put("proj_id", lookupProject.getValue());
		mapParameters.put("co_id", lookupCompany.getValue());
		mapParameters.put("phase_code", lookupPhase.getValue());
		mapParameters.put("include_all", include_all);


		FncReport.generateReport("/Reports/rptPMD_AgingReport.jasper", company, mapParameters);
		
	}

	//company
	public void initialize_comp(){		
		
		co_id 		= "02";	
		company		= "CENQHOMES DEVELOPMENT CORPORATION";
		company_logo = RequestForPayment.sql_getCompanyLogo();		
		txtCompany.setText(company);

		refreshFields();		
					
		txtCompany.setText(company);
		
		lookupCompany.setValue(co_id);
		lookupProject.setLookupSQL(CheckStatusMonitoring.sql_project(co_id));
		lookupPhase.setLookupSQL(getPhase());
		
		lblProject.setEnabled(true);	
		lookupProject.setText("");
		lookupProject.setEnabled(true);
		txtProject.setEnabled(true);
		txtProject.setText("");

		lblDateFrom.setEnabled(true);		
		dteDateFrom.setEnabled(true);
		lblDateTo.setEnabled(true);	
		dateDateTo.setEnabled(true);
	
		
		btnPreview.setEnabled(true);
		btnCancel.setEnabled(true);
		
		KEYBOARD_MANAGER.focusNextComponent();
		btnCancel.setEnabled(true);
		btnPreview.setEnabled(true);
		
	}

	
	//SQL
	private String getPhase(){//used
		return 
		"select\r\n" + 
		"\r\n" + 
		"trim(sub_proj_id) as \"Phase ID\",\r\n" + 
		"trim(phase) as \"Phase\"\r\n" + 
		"\r\n" + 
		"from mf_sub_project\r\n" + 
		"\r\n" + 
		"order by sub_proj_id "  ;

	}
}