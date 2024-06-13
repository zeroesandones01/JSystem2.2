/**
 * 
 */
package Reports.LoansAndReceivable;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.OverlayLayout;

import Functions.FncGlobal;
import Functions.FncReport;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components._JInternalFrame;
import components._JTabbedPane;
import components._JXTextField;

/**
 * @author John Lester Fatallo
 */
public class MRI_Reports extends _JInternalFrame implements _GUI,
ActionListener {


	private static final long serialVersionUID = 6284724534597154757L;
	static String title = "MRI Reports";
	Dimension frameSize = new Dimension(550, 180);// 510, 250

	private JPanel pnlMain;
	private _JTabbedPane tabMain;
	private JPanel pnlEnrolledAccounts;

	private JPanel pnlEALabel;
	private JLabel lblEACompany;
	private JLabel lblEAProject;
	private JLabel lblEARPLF;
	private JCheckBox chkRPLFNo;

	private JPanel pnlEAComponent;
	private JPanel pnlEACompany;
	private _JLookup lookupEACompany;
	private _JXTextField txtEACompany;

	private JPanel pnlEAProject;
	private _JLookup lookupEAProject;
	private _JXTextField txtEAProject;

	private JPanel pnlEARPLF;
	private _JLookup lookupEARPLF;
	
	private JPanel pnlMRIMonitoring;
	private JPanel pnlMonitoringLabel;
	private JLabel lblMonitoringCompany;
	private JLabel lblMonitoringProject;
	private JPanel pnlMonitoringComponent;
	private JPanel pnlMonitoringCompany;
	private _JLookup lookupMonitoringCompany;
	private _JXTextField txtMonitoringCompany;
	private JPanel pnlMonitoringProject;
	private _JLookup lookupMonitoringProject;
	private _JXTextField txtMonitoringProject;

	private JPanel pnlSouth;
	private JButton btnPreview;

	public MRI_Reports() {
		super(title, false, true, false, true);
		initGUI();
	}

	public MRI_Reports(String title) {
		super(title, false, true, false, true);
		initGUI();
	}

	public MRI_Reports(String title, boolean resizable,
			boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, false, true, false, true);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setSize(frameSize);
		this.setPreferredSize(frameSize);
		{
			tabMain = new _JTabbedPane();;
			this.add(tabMain, BorderLayout.CENTER);
			tabMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			/*{
				pnlEnrolledAccounts = new JPanel(new BorderLayout(3, 3));
				tabMain.add("Enrolled Accounts", pnlEnrolledAccounts);
				pnlEnrolledAccounts.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
				{
					pnlEALabel = new JPanel(new GridLayout(3, 1, 3, 3));
					pnlEnrolledAccounts.add(pnlEALabel, BorderLayout.WEST);
					{
						lblEACompany = new JLabel("Company");
						pnlEALabel.add(lblEACompany);
					}
					{
						lblEAProject = new JLabel("Project");
						pnlEALabel.add(lblEAProject);
					}
					{
						lblEARPLF = new JLabel("RPLF No");
						pnlEALabel.add(lblEARPLF);
					}
				}
				{
					pnlEAComponent = new JPanel(new GridLayout(3, 1, 3, 3));
					pnlEnrolledAccounts.add(pnlEAComponent, BorderLayout.CENTER);
					{
						pnlEACompany = new JPanel(new BorderLayout(3, 3));
						pnlEAComponent.add(pnlEACompany);
						{
							lookupEACompany = new _JLookup(null, "Select Company", 0);
							pnlEACompany.add(lookupEACompany, BorderLayout.WEST);
							lookupEACompany.setPreferredSize(new Dimension(50, 0));
							lookupEACompany.setLookupSQL(sqlCompany());
							lookupEACompany.addLookupListener(new LookupListener() {

								public void lookupPerformed(LookupEvent event) {
									Object [] data = ((_JLookup) event.getSource()).getDataSet();

									if(data != null){
										String co_id = (String) data[0];
										String company_name = (String) data[1];

										lookupEAProject.setLookupSQL(sqlProject(co_id));

										txtEACompany.setText(company_name);
									}
								}
							});
						}
						{
							txtEACompany = new _JXTextField();
							pnlEACompany.add(txtEACompany, BorderLayout.CENTER);
						}
					}
					{
						pnlEAProject = new JPanel(new BorderLayout(3, 3));
						pnlEAComponent.add(pnlEAProject);
						{
							lookupEAProject = new _JLookup(null, "Select Project", 0);
							pnlEAProject.add(lookupEAProject, BorderLayout.WEST);
							lookupEAProject.setPreferredSize(new Dimension(50, 0));
							lookupEAProject.addLookupListener(new LookupListener() {

								public void lookupPerformed(LookupEvent event) {
									Object [] data = ((_JLookup) event.getSource()).getDataSet();

									if(data != null){
										String proj_name = (String) data[1];

										txtEAProject.setText(proj_name);

									}
								}
							});
						}
						{
							txtEAProject = new _JXTextField();
							pnlEAProject.add(txtEAProject, BorderLayout.CENTER);
						}
					}
					{
						pnlEARPLF = new JPanel(new BorderLayout(3, 3));
						pnlEAComponent.add(pnlEARPLF);
						{
							lookupEARPLF = new _JLookup(null, "RPLF No.", 0);
							pnlEARPLF.add(lookupEARPLF, BorderLayout.WEST);
							lookupEARPLF.setLookupSQL(sqlRPLF());
							lookupEARPLF.setPreferredSize(new Dimension(150, 0));
						}
					}
				}
			}*/
			{
				pnlMRIMonitoring = new JPanel(new BorderLayout(3, 3));
				tabMain.add("MRI Monitoring", pnlMRIMonitoring);
				pnlMRIMonitoring.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
				{
					pnlMonitoringLabel = new JPanel(new GridLayout(2, 1, 3, 3));
					pnlMRIMonitoring.add(pnlMonitoringLabel, BorderLayout.WEST);
					{
						lblMonitoringCompany = new JLabel("Company");
						pnlMonitoringLabel.add(lblMonitoringCompany);
					}
					{
						lblMonitoringProject = new JLabel("Project");
						pnlMonitoringLabel.add(lblMonitoringProject);
					}
				}
				{
					pnlMonitoringComponent = new JPanel(new GridLayout(2, 1, 3, 3));
					pnlMRIMonitoring.add(pnlMonitoringComponent, BorderLayout.CENTER);
					{
						pnlMonitoringCompany = new JPanel(new BorderLayout(3, 3));
						pnlMonitoringComponent.add(pnlMonitoringCompany);
						{
							lookupMonitoringCompany = new _JLookup(null, "Select Company", 0);
							pnlMonitoringCompany.add(lookupMonitoringCompany, BorderLayout.WEST);
							lookupMonitoringCompany.setPreferredSize(new Dimension(50, 0));
							lookupMonitoringCompany.setLookupSQL(sqlCompany());
							lookupMonitoringCompany.addLookupListener(new LookupListener() {
								
								public void lookupPerformed(LookupEvent event) {
									Object [] data = ((_JLookup) event.getSource()).getDataSet();
									
									if(data != null){
										String co_id = (String) data[0];
										String company_name = (String) data[1];
										
										lookupMonitoringProject.setLookupSQL(sqlProject(co_id));
										txtMonitoringCompany.setText(company_name);
									}
								}
							});
						}
						{
							txtMonitoringCompany = new _JXTextField();
							pnlMonitoringCompany.add(txtMonitoringCompany, BorderLayout.CENTER);
						}
					}
					{
						pnlMonitoringProject = new JPanel(new BorderLayout(3, 3));
						pnlMonitoringComponent.add(pnlMonitoringProject);
						{
							lookupMonitoringProject = new _JLookup(null, "Select Project", 0, "Please select Company First");
							pnlMonitoringProject.add(lookupMonitoringProject, BorderLayout.WEST);
							lookupMonitoringProject.setPreferredSize(new Dimension(50, 0));
							lookupMonitoringProject.addLookupListener(new LookupListener() {
								
								public void lookupPerformed(LookupEvent event) {
									Object [] data = ((_JLookup) event.getSource()).getDataSet();
									
									if(data != null){
										String proj_name = (String) data[1];
										
										txtMonitoringProject.setText(proj_name);
									}
								}
							});
						}
						{
							txtMonitoringProject = new _JXTextField();
							pnlMonitoringProject.add(txtMonitoringProject, BorderLayout.CENTER);
						}
					}
				}
			}
		}
		{
			pnlSouth = new JPanel(new BorderLayout(3, 3));
			this.add(pnlSouth, BorderLayout.SOUTH);
			pnlSouth.setLayout(new OverlayLayout(pnlSouth));
			pnlSouth.setPreferredSize(new Dimension(388, 30));
			pnlSouth.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			{
				btnPreview = new JButton("Preview");
				pnlSouth.add(btnPreview);
				btnPreview.setAlignmentX(0.5f);
				btnPreview.setAlignmentY(0.5f);
				btnPreview.setMaximumSize(new Dimension(100, 30));
				btnPreview.setMnemonic(KeyEvent.VK_P);
				btnPreview.addActionListener(this);
				btnPreview.setActionCommand(btnPreview.getText());
			}
		}
	}//XXX END OF INIT GUI

	//SQL FOR COMPANY
	private String sqlCompany(){
		return "SELECT TRIM(co_id) as \"ID\", \n" + 
				"TRIM(company_name) as \"Company\", \n" + 
				"TRIM(company_alias) as \"Alias\"\n" + 
				"FROM mf_company;";
	}

	//SQL FOR PROJECT
	private String sqlProject(String co_id){
		return "SELECT TRIM(proj_id) as \"ID\",\n" + 
				"TRIM(proj_name) as \"Project\", \n" + 
				"TRIM(proj_alias) as \"Alias\"\n" + 
				"FROM mf_project \n" + 
				"WHERE co_id = '"+co_id+"';";
	}

	//SQL FOR RPLF NO
	private String sqlRPLF(){
		return "SELECT TRIM(rplf_no) as \"RPLF No\"\n" + 
				"FROM rf_mri_details\n" + 
				"WHERE rplf_no IS NOT NULL\n" + 
				"AND date_terminated IS NULL;";
	}

	public void actionPerformed(ActionEvent e) { 
		String actionCommand = e.getActionCommand();

		if(actionCommand.equals("Preview")){
			
			if(tabMain.getSelectedIndex() == 0){
				
				Map<String, Object> mapParameters = new HashMap<String, Object>();
				
				mapParameters.put("date_cut_off", FncGlobal.getDateToday()); 
				mapParameters.put("co_id", lookupMonitoringCompany.getValue());
				mapParameters.put("proj_id", lookupMonitoringProject.getValue());
				
				FncReport.generateReport("/Reports/rptMRI_Monitoring.jasper", "MRI Monitoring Report", mapParameters);
				
				
			}
			
		}
	}

}
