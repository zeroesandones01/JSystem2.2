package Reports.ConstructionManagement;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.OverlayLayout;

import Functions.FncReport;
import Functions.FncSystem;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components._JInternalFrame;
import components._JXTextField;
import interfaces._GUI;

public class HouseAccomplishmentCMDApp extends _JInternalFrame implements _GUI, ActionListener {

	private static final long serialVersionUID = 7910894288006352443L;
	
	static String title = "Water Meter Installation Report";
	Dimension frameSize = new Dimension(500, 150);// 510, 250
	
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
	
	private JPanel pnlSouth;
	private JButton btnPreview;
	
	public HouseAccomplishmentCMDApp() {
		super(title, false, true, false, true);
		initGUI();
	}

	public HouseAccomplishmentCMDApp(String title) {
		super(title, false, true, false, true);
		initGUI();
	}

	public HouseAccomplishmentCMDApp(String title, boolean resizable, boolean closable,
			boolean maximizable, boolean iconifiable) {
		super(title, false, true, false, true);
		initGUI();
	}

	@Override
	public void initGUI() {
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
					pnlCenterLabel = new JPanel(new GridLayout(3, 1, 3, 3));
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
				}
				{
					pnlCenterComponents = new JPanel(new GridLayout(3, 1, 3, 3));
					pnlCenter.add(pnlCenterComponents, BorderLayout.CENTER);
					{
						JPanel pnlCompany = new JPanel(new BorderLayout(5, 5));
						pnlCenterComponents.add(pnlCompany);
						{
							lookupCompany = new _JLookup("Company");
							pnlCompany.add(lookupCompany, BorderLayout.WEST);
							lookupCompany.setPreferredSize(new Dimension(100, 0));
							lookupCompany.setFilterName(true);
							lookupCompany.setLookupSQL(SQL_COMPANY());
							lookupCompany.addLookupListener(new LookupListener() {

								@Override
								public void lookupPerformed(LookupEvent event) {
									Object [] data = ((_JLookup) event.getSource()).getDataSet();

									if(data != null) {
										String co_id = (String) data[0];
										String company_name = (String) data[1];
										lookupCompany.setValue(co_id);
										txtCompany.setText(company_name);
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
							lookupProject.addFocusListener(new FocusListener() {

								@Override
								public void focusLost(FocusEvent e) {
									// TODO Auto-generated method stub

								}

								@Override
								public void focusGained(FocusEvent e) {
									lookupProject.setLookupSQL(SQL_PROJECT(lookupCompany.getValue()));
								}
							});
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
							lookupPhase.addFocusListener(new FocusListener() {

								@Override
								public void focusLost(FocusEvent e) {
									// TODO Auto-generated method stub
								}

								@Override
								public void focusGained(FocusEvent e) {
									lookupPhase.setLookupSQL(SQL_PHASE(lookupProject.getValue()));
								}
							});
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

	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		
		if(actionCommand.equals("Preview")) {
			Map<String, Object> mapParameters = new HashMap<String, Object>();
			
			mapParameters.put("co_id", lookupCompany.getValue());
			mapParameters.put("proj_id", lookupProject.getValue());
			mapParameters.put("phase", lookupPhase.getValue());

			FncReport.generateReport("/Reports/rptHouseAccom_CMDApp.jasper","House Accomplishment Report (from CMD App)" , mapParameters);
		}

	}


}
