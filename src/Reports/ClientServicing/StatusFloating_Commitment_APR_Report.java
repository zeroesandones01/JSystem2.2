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
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.OverlayLayout;

import DateChooser._JDateChooser;
import Functions.FncReport;
import Functions.FncSystem;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components._JInternalFrame;
import components._JXTextField;
import interfaces._GUI;

public class StatusFloating_Commitment_APR_Report extends _JInternalFrame implements _GUI, ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7709247102465521183L;
	static String title = "Status of Floating/Commitment/APR";
	Dimension frameSize = new Dimension(550, 210);// 510, 250

	private JPanel pnlMain;
	private JPanel pnlCenter;
	private JPanel pnlCenterLabel;
	private JLabel lblCompany;
	private JLabel lblProject;
	private JLabel lblPhase;
	private JLabel lblDateFrom;


	private JPanel pnlCenterComponents;
	private _JLookup lookupCompany;
	private _JXTextField txtCompany;
	private _JLookup lookupProject;
	private _JXTextField txtProject;
	private _JLookup lookupPhase;
	private _JXTextField txtPhase;
	private _JDateChooser dteFrom;
	private _JDateChooser dteTo;

	private JPanel pnlSouth;
	private JButton btnPreview;

	public StatusFloating_Commitment_APR_Report() {
		super(title, false, true, false, true);
		initGUI();
	}

	public StatusFloating_Commitment_APR_Report(String title) {
		super(title, false, true, false, true);
		initGUI();
	}

	public StatusFloating_Commitment_APR_Report(String title, boolean resizable, boolean closable,
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
				pnlCenter.setPreferredSize(new Dimension(0, 20));
				{
					pnlCenterLabel = new JPanel(new GridLayout(4, 1, 3, 3));
					pnlCenter.add(pnlCenterLabel, BorderLayout.WEST);
					{
						lblCompany = new JLabel("Company", JLabel.TRAILING);
						pnlCenterLabel.add(lblCompany);
					}
					{
						lblProject = new JLabel("Project", JLabel.TRAILING);
						pnlCenterLabel.add(lblProject);
					}
					{
						lblPhase = new JLabel("Phase", JLabel.TRAILING);
						pnlCenterLabel.add(lblPhase);
					}
					{
						lblDateFrom = new JLabel("Date From", JLabel.TRAILING);
						pnlCenterLabel.add(lblDateFrom);
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
							lookupCompany.setLookupSQL(sqlCompany());
							lookupCompany.setPreferredSize(new Dimension(50, 0));
							lookupCompany.addLookupListener(new LookupListener() {

								public void lookupPerformed(LookupEvent event) {
									Object [] data = ((_JLookup) event.getSource()).getDataSet();

									FncSystem.out("Display SQL for lookup of Company", lookupCompany.getValue());
									if(data != null){
										String co_id = (String) data[0];
										String company_name = (String) data[1];

										txtCompany.setText(company_name);
										lookupProject.setLookupSQL(sqlProject(co_id));
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
							lookupProject = new _JLookup(null, "Select Project", 0, "Please select company first");
							pnlProject.add(lookupProject, BorderLayout.WEST);
							lookupProject.setPreferredSize(new Dimension(50, 0));
							//lookupProject.setValue("015");
							lookupProject.addLookupListener(new LookupListener() {

								public void lookupPerformed(LookupEvent event) {
									Object [] data = ((_JLookup) event.getSource()).getDataSet();

									FncSystem.out("Display SQL for Project", lookupProject.getValue());
									if(data != null){
										String proj_id = (String) data[0];
										String proj_name = (String) data[1];

										txtProject.setText(proj_name);
										lookupPhase.setLookupSQL(SQL_PHASE(proj_id));
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
						JPanel pnlPhase = new JPanel(new BorderLayout(3, 3));
						pnlCenterComponents.add(pnlPhase);
						{
							lookupPhase = new _JLookup(null, "Select Phase", 0);
							pnlPhase.add(lookupPhase, BorderLayout.WEST);
							lookupPhase.setPreferredSize(new Dimension(50, 0));
							lookupPhase.setLookupSQL(SQL_PHASE(lookupProject.getValue()));
							lookupPhase.addLookupListener(new LookupListener() {

								public void lookupPerformed(LookupEvent event) {
									Object [] data = ((_JLookup) event.getSource()).getDataSet();

									FncSystem.out("Display SQL for Project", lookupPhase.getValue());
									if(data != null){
										String sub_proj_id = (String) data[0];
										String phase = (String) data[1];

										txtPhase.setText(phase);
									}
								}
							});
						}
						{
							txtPhase = new _JXTextField();
							pnlPhase.add(txtPhase);
						}
					}
					{
						JPanel pnlDateFromTo = new JPanel(new BorderLayout(3, 3));
						pnlCenterComponents.add(pnlDateFromTo);
						{
							dteFrom = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
							pnlDateFromTo.add(dteFrom, BorderLayout.WEST);
							dteFrom.setPreferredSize(new Dimension(150, 0));
						}
						{
							JPanel pnlDateTo = new JPanel(new BorderLayout(3, 3));
							pnlDateFromTo.add(pnlDateTo, BorderLayout.CENTER);
							{
								JLabel lblDateTo = new JLabel("Date to", JLabel.TRAILING);
								pnlDateTo.add(lblDateTo, BorderLayout.WEST);
							}
							{
								dteTo = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
								pnlDateTo.add(dteTo);
								dteTo.setPreferredSize(new Dimension(150, 0));
							}
						}
					}
				}
			}
			{
				pnlSouth = new JPanel(new BorderLayout(3, 3));
				pnlMain.add(pnlSouth, BorderLayout.SOUTH);
				pnlSouth.setLayout(new OverlayLayout(pnlSouth));
				pnlSouth.setPreferredSize(new Dimension(388, 50));
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
		setDefaults();
	}//XXX END OF INIT GUI

	private void setDefaults(){
		
		lookupCompany.setValue("02");
		txtCompany.setText("CENQHOMES DEVELOPMENT CORPORATION");
		lookupProject.setLookupSQL(sqlProject(lookupCompany.getValue()));
		
		/*lookupProject.setValue("015");
		txtProject.setText("TERRAVERDE RESIDENCES");*/
	
	}

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

	public void actionPerformed(ActionEvent e) { 
		String actionCommand = e.getActionCommand();

		if(actionCommand.equals("Preview")){
			if(dteFrom.getDate() == null || dteTo.getDate() == null){
				JOptionPane.showInternalConfirmDialog(StatusFloating_Commitment_APR_Report.this.getTopLevelAncestor(), "Please select date range", "Preview", JOptionPane.WARNING_MESSAGE);
				
			}else{
				Map<String, Object> mapParameters = new HashMap<String, Object>();
				
				mapParameters.put("company_name", txtCompany.getText());
				mapParameters.put("co_id", lookupCompany.getValue());
				mapParameters.put("proj_id", lookupProject.getValue());
				mapParameters.put("phase", lookupPhase.getValue());
				mapParameters.put("date_from", dteFrom.getDate());
				mapParameters.put("date_to", dteTo.getDate());

				FncReport.generateReport("/Reports/rptFloating_Commitment_APR_Status.jasper","Status Floating/Commitment/ARP" , mapParameters);
			}
		}
	}
}
