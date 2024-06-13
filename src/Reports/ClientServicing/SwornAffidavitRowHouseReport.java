package Reports.ClientServicing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.OverlayLayout;

import Functions.FncReport;
import Functions.FncSystem;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components._JInternalFrame;
import components._JXTextField;
import interfaces._GUI;

public class SwornAffidavitRowHouseReport extends _JInternalFrame implements _GUI, ActionListener {

	static String title = "List of Clients with & w/o sworn affidavit rowhouse";
	Dimension frameSize = new Dimension(500, 200);// 510, 250

	private JPanel pnlMain;
	private JPanel pnlCenter;
	private JPanel pnlCenterLabel;
	private JLabel lblCompany;
	private JLabel lblPhase;
	private JLabel lblProject;

	private JPanel pnlCenterComponents;
	private _JLookup lookupCompany;
	private _JXTextField txtCompany;
	private _JLookup lookupProject;
	private _JXTextField txtProject;
	private _JLookup lookupPhase;
	private _JXTextField txtPhase;
	private JComboBox cmbSubmittedIndex;

	private JPanel pnlSouth;
	private JButton btnPreview;

	public SwornAffidavitRowHouseReport() {
		super(title, false, true, false, true);
		initGUI();
	}

	public SwornAffidavitRowHouseReport(String title) {
		super(title, false, true, false, true);
		initGUI();
	}

	public SwornAffidavitRowHouseReport(String title, boolean resizable, boolean closable,
			boolean maximizable, boolean iconifiable) {
		super(title, false, true, false, true);
		initGUI();
	}

	@Override
	public void initGUI() {//XXX START OF INIT GUI
		this.setSize(frameSize);
		this.setPreferredSize(frameSize);
		{
			pnlMain = new JPanel(new BorderLayout(3, 3));
			this.add(pnlMain, BorderLayout.CENTER);
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			{
				pnlCenter = new JPanel(new BorderLayout(5, 5));
				pnlMain.add(pnlCenter, BorderLayout.CENTER);
				pnlCenter.setPreferredSize(new Dimension(0, 50));
				{
					pnlCenterLabel = new JPanel(new GridLayout(4, 1, 3, 3));
					pnlCenter.add(pnlCenterLabel, BorderLayout.WEST);
					{
						lblCompany = new JLabel("Company");
						pnlCenterLabel.add(lblCompany);
					}
					{
						lblProject = new JLabel("Project");
						pnlCenterLabel.add(lblProject);
					}
					{
						lblPhase = new JLabel("Phase");
						pnlCenterLabel.add(lblPhase);
					}
					{
						pnlCenterLabel.add(Box.createHorizontalBox());
					}
				}
				{
					pnlCenterComponents = new JPanel(new GridLayout(4, 1, 3, 3));
					pnlCenter.add(pnlCenterComponents, BorderLayout.CENTER);
					{
						JPanel pnlCompany = new JPanel(new BorderLayout(3, 3));
						pnlCenterComponents.add(pnlCompany);
						{
							lookupCompany = new _JLookup(null, "Select Company", 0);
							pnlCompany.add(lookupCompany, BorderLayout.WEST);
							lookupCompany.setPreferredSize(new Dimension(50, 0));
							lookupCompany.setLookupSQL(sqlCompany());
							lookupCompany.addLookupListener(new LookupListener() {

								public void lookupPerformed(LookupEvent event) {
									Object [] data = ((_JLookup) event.getSource()).getDataSet();

									
									if(data != null){
										String co_id = (String) data[0];
										String co_name = (String) data[1];

										txtCompany.setText(co_name);
									}
								}
							});
						}
						{
							txtCompany = new _JXTextField();
							pnlCompany.add(txtCompany, BorderLayout.CENTER);
						}
					}
					{
						JPanel pnlProject = new JPanel(new BorderLayout(3, 3));
						pnlCenterComponents.add(pnlProject);
						{
							lookupProject = new _JLookup(null, "Select Project", 0);
							pnlProject.add(lookupProject, BorderLayout.WEST);
							lookupProject.setPreferredSize(new Dimension(50, 0));
							lookupProject.setLookupSQL(sqlProject());
							lookupProject.addLookupListener(new LookupListener() {

								public void lookupPerformed(LookupEvent event) {
									Object [] data = ((_JLookup) event.getSource()).getDataSet();

									FncSystem.out("Display SQL for Project", lookupProject.getValue());
									if(data != null){
										String proj_id = (String) data[0];
										String proj_name = (String) data[1];

										txtProject.setText(proj_name);
										lookupPhase.setLookupSQL(SQL_PHASE_ALL(proj_id));
									}
								}
							});
						}
						{
							txtProject = new _JXTextField();
							pnlProject.add(txtProject, BorderLayout.CENTER);
						}
					}
					{
						JPanel pnlSalesGroup = new JPanel(new BorderLayout(3, 3));
						pnlCenterComponents.add(pnlSalesGroup);
						{
							lookupPhase = new _JLookup(null, "Select Phase", 0, "Please select project");
							pnlSalesGroup.add(lookupPhase, BorderLayout.WEST);
							lookupPhase.setPreferredSize(new Dimension(50, 0));
							//lookupSalesDivision.setLookupSQL(sqlSalesDivision());
							lookupPhase.addLookupListener(new LookupListener() {

								public void lookupPerformed(LookupEvent event) {
									Object [] data = ((_JLookup) event.getSource()).getDataSet();

									if(data != null){
										String sales_group = (String) data[1];

										txtPhase.setText(sales_group);
									}
								}
							});
						}
						{
							txtPhase = new _JXTextField();
							pnlSalesGroup.add(txtPhase);
						}
					}
					{
						cmbSubmittedIndex = new JComboBox(new DefaultComboBoxModel(
								new String[] {"with sworn affidavit for row house", "w/o sworn affidavit for row house"}));
						pnlCenterComponents.add(cmbSubmittedIndex);
					}
				}
			}
			{
				pnlSouth = new JPanel(new BorderLayout(3, 3));
				pnlMain.add(pnlSouth, BorderLayout.SOUTH);
				pnlSouth.setLayout(new OverlayLayout(pnlSouth));
				pnlSouth.setPreferredSize(new Dimension(388, 40));
				pnlSouth.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
				{
					btnPreview = new JButton("Preview");
					pnlSouth.add(btnPreview);
					btnPreview.setAlignmentX(0.5f);
					btnPreview.setAlignmentY(0.5f);
					btnPreview.setMaximumSize(new Dimension(100, 50));
					btnPreview.setMnemonic(KeyEvent.VK_P);
					btnPreview.addActionListener(this);
					btnPreview.setActionCommand(btnPreview.getText());
				}
			}
		}
	}//XXX END OF INIT GUI

	//SQL FOR PROJECT
	public static String sqlCompany(){
		return "SELECT co_id as \"ID\", \n" + 
			   "company_name as \"Company Name\", \n" + 
			   "company_alias as \"Alias\" \n" + 
			   "FROM mf_company;";
	}
	
	private String sqlProject(){
		return "SELECT \n" + 
				"proj_id as \"ID\", \n" + 
				"proj_name as \"Project\", \n" + 
				"proj_alias as \"Alias\"\n" + 
				"FROM mf_project\n" + 
				"WHERE status_id = 'A';";
	}
	
	public static String sqlSalesGroup(){
		return  "SELECT \r\n" + 
				"a.dept_code as \"Dept. ID\",\r\n" + 
				"a.dept_alias  as \"Dept. Alias\",\r\n" + 
				"b.division_name as \"Division\" \r\n" + 
				"FROM mf_department a\r\n" + 
				"LEFT JOIN mf_division b on a.division_code = b.division_code\r\n" + 
				"WHERE a.division_code in ('06','07','08','09','29') \n"+
				"AND a.status_id = 'A'";
	};
	
	public static String sqlSalesDivision(){
		return "SELECT \n" + 
				"division_code as \"ID\", division_name as \"Name\", \n" + 
				"division_alias as \"Alias\"\n" + 
				"from mf_division\n" + 
				"where division_code in ('06','07','08','09','29')";
	}
	
	public void actionPerformed(ActionEvent e) { 
		String actionCommand = e.getActionCommand();

		if(actionCommand.equals("Preview")){
				Map<String, Object> mapParameters = new HashMap<String, Object>();
				
				mapParameters.put("co_id", lookupCompany.getValue());
				mapParameters.put("proj_id", lookupProject.getValue());
				mapParameters.put("phase", lookupPhase.getValue());
				mapParameters.put("submitted_index", cmbSubmittedIndex.getSelectedIndex());

				FncReport.generateReport("/Reports/rptSwornAffidavit_Report.jasper","List of Clients with or w/o Sworn Affidavit for Row House" , mapParameters);
		}
	}

}
